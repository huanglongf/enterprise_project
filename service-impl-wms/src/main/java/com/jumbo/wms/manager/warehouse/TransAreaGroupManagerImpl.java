package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;
import loxia.support.excel.impl.DefaultReadStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.baseinfo.TransportatorLogDao;
import com.jumbo.dao.warehouse.TransAreaGroupDao;
import com.jumbo.dao.warehouse.TransAreaGroupDetialDao;
import com.jumbo.dao.warehouse.TransportServiceAreaDao;
import com.jumbo.dao.warehouse.TransportServiceDao;
import com.jumbo.dao.warehouse.TransportServiceLogDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.DefaultLifeCycleStatus;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.TransportatorLog;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.trans.TransAreaGroup;
import com.jumbo.wms.model.trans.TransAreaGroupCommand;
import com.jumbo.wms.model.trans.TransAreaGroupDetial;
import com.jumbo.wms.model.trans.TransAreaGroupStatus;
import com.jumbo.wms.model.trans.TransportService;
import com.jumbo.wms.model.trans.TransportServiceArea;
import com.jumbo.wms.model.trans.TransportServiceLog;
import com.jumbo.wms.model.trans.TransportServiceStatus;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransType;

/**
 * 配送范围
 * 
 * @author xiaolong.fei
 * 
 */
@Transactional
@Service("transAreaGroupManager")
public class TransAreaGroupManagerImpl extends BaseManagerImpl implements TransAreaGroupManager {

    private static final long serialVersionUID = -2433849937629967243L;
    @Autowired
    private TransAreaGroupDao transAreaGroupDao;
    @Autowired
    private TransAreaGroupDetialDao transAreaGroupDetialDao;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private TransportServiceDao transportServiceDao;
    @Autowired
    private TransportServiceLogDao transportServiceLogDao;
    @Autowired
    private TransportServiceAreaDao transportServiceAreaDao;
    @Autowired
    private TransportatorLogDao transportatorLogDao;

    @Resource(name = "transAreaReader")
    private ExcelReader transAreaReader;

    @Resource(name = "transServiceReader")
    private ExcelReader transServiceReader;

    @Override
    public Pagination<TransAreaGroupCommand> findTransArea(int start, int pageSize, Sort[] sorts, Long cstmId) {
        return transAreaGroupDao.findTransAreaList(start, pageSize, cstmId, new BeanPropertyRowMapper<TransAreaGroupCommand>(TransAreaGroupCommand.class), sorts);
    }

    @Override
    public Pagination<TransAreaGroupCommand> updateFindTransArea(int start, int pageSize, Sort[] sorts, Long cstmId) {
        return transAreaGroupDao.findUpdateTransAreaList(start, pageSize, cstmId, new BeanPropertyRowMapper<TransAreaGroupCommand>(TransAreaGroupCommand.class), sorts);
    }

    /**
     * EXL导入
     */
    @SuppressWarnings("unchecked")
    public ReadStatus transAreaImport(File file, String code, String name, Long areaId, Long status, Long cstmId) {
        log.debug("===========transAreaImport start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<TransAreaGroupDetial> noList = null;
        ReadStatus rs = null;
        TransAreaGroup group = transAreaGroupDao.findTransAreaByCode(code);
        if (group != null) {
            throw new BusinessException(ErrorCode.TRANS_AREA_EXIST);
        }
        try {
            rs = transAreaReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                return rs;
            }
            noList = (List<TransAreaGroupDetial>) beans.get("data");
            transAreaImport(noList, code, name, areaId, status, cstmId);
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("transAreaImport Exception:" + code, ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    public ReadStatus transAreaImport2(File file, String maId, String tranId, String serviceName, String serviceType, String timeType, String statuss, User user) {
        Transportator tor = null;
        ReadStatus rs = null;
        TransportService service = null;
        if (!maId.equals("") && maId != null) {
            tor = transportatorDao.getByPrimaryKey(Long.parseLong(maId));
        }
        if (tor == null) {
            throw new BusinessException(ErrorCode.PDA_SYS_ERROR);

        }
        if ("ADD".endsWith(tranId)) {
            // 保存物流服务
            service = new TransportService();
            service.setName(serviceName);
            service.setStatus(TransportServiceStatus.valueOf(Integer.parseInt(statuss)));
            service.setTimeType(TransTimeType.valueOf(Integer.parseInt(timeType)));
            service.setType(TransType.valueOf(Integer.parseInt(serviceType)));
            service.setLastModifyId(user);
            service.setTrans(tor);
            service.setCreateTime(new Date());
            transportServiceDao.save(service);

        } else {
            // 保存物流服务
            service = transportServiceDao.getByPrimaryKey(Long.parseLong(tranId));
            service.setName(serviceName);
            service.setStatus(TransportServiceStatus.valueOf(Integer.parseInt(statuss)));
            service.setTimeType(TransTimeType.valueOf(Integer.parseInt(timeType)));
            service.setType(TransType.valueOf(Integer.parseInt(serviceType)));
            service.setLastModifyId(user);
            service.setTrans(tor);
            service.setCreateTime(new Date());
            transportServiceDao.save(service);
        }
        // 保存日志
        TransportServiceLog serviceLog = new TransportServiceLog();
        serviceLog.setName(serviceName);
        serviceLog.setStatus(TransportServiceStatus.valueOf(Integer.parseInt(statuss)));
        serviceLog.setTimeType(TransTimeType.valueOf(Integer.parseInt(timeType)));
        serviceLog.setType(TransType.valueOf(Integer.parseInt(serviceType)));
        serviceLog.setLastModifyId(user);
        serviceLog.setTrans(tor);
        serviceLog.setCreateTime(new Date());
        transportServiceLogDao.save(serviceLog);
        transportServiceLogDao.flush();
        if (file == null) {
            rs = new DefaultReadStatus();
            rs.setStatus(ReadStatus.STATUS_SUCCESS);
        } else {
            log.debug("===========transAreaImport start============");
            Map<String, Object> beans = new HashMap<String, Object>();
            List<TransportServiceArea> noList = null;
            try {
                rs = transServiceReader.readSheet(new FileInputStream(file), 0, beans);
                if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                    rs.setStatus(-2);
                    rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                    return rs;
                }
                noList = (List<TransportServiceArea>) beans.get("data");
                transServiceImport(noList, service, user);
                return rs;
            } catch (BusinessException ex) {
                throw ex;
            } catch (Exception ex) {
                if (log.isErrorEnabled()) {
                    log.error("transAreaImport2 Exception:" + tranId, ex);
                }
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
        }
        return rs;
    }

    public void transServiceImport(List<TransportServiceArea> noList, TransportService service, User user) {
        if (noList.size() == 0) {
            throw new BusinessException(ErrorCode.TRANS_IS_NULL);
        } else {
            transportServiceAreaDao.deleteTransAareByGId(service.getId());
            for (TransportServiceArea gpd : noList) {
                if (gpd.getProvince() == null) {// 当省为空时报错
                    throw new BusinessException(ErrorCode.TRANS_PRO_IS_NULL);
                } else {
                    if (gpd.getProvince().trim().equals("")) {
                        throw new BusinessException(ErrorCode.TRANS_PRO_IS_NULL);
                    }
                }
                if (gpd.getCity() == null && gpd.getDistrict() != null) {// 当区不为空时市为空报错
                    throw new BusinessException(ErrorCode.TRANS_CITY_NULL);
                }
                if (gpd.getCity() != null && gpd.getDistrict() != null) {
                    if (gpd.getCity().trim().equals("") && !gpd.getDistrict().trim().equals("")) {
                        throw new BusinessException(ErrorCode.TRANS_CITY_NULL);
                    }
                }
                TransportServiceArea groupd = new TransportServiceArea();
                groupd.setCity(gpd.getCity());
                groupd.setDistrict(gpd.getDistrict());
                groupd.setProvince(gpd.getProvince());
                groupd.setTransServiceId(service);
                groupd.setLastModifyId(user);
                groupd.setCreateTime(new Date());
                transportServiceAreaDao.save(groupd);
                transportServiceAreaDao.flush();
            }
        }
    }


    public void transAreaImport(List<TransAreaGroupDetial> noList, String code, String name, Long areaId, Long status, Long cstmId) {
        if (noList.size() == 0) {
            throw new BusinessException(ErrorCode.TRANS_IS_NULL);
        } else {
            if (status != null) {// 当code为空，修改数据
                transAreaGroupDao.updateTransArea(areaId, name, status);// 修改配送范围数据
                transAreaGroupDetialDao.deleteTransADetialByGId(areaId);// 删除配送范围明细数据
                TransAreaGroup gp2 = new TransAreaGroup();
                gp2.setId(areaId);
                for (TransAreaGroupDetial gpd : noList) {
                    if (gpd.getProvince() == null) {// 当省为空时报错
                        throw new BusinessException(ErrorCode.TRANS_PRO_IS_NULL);
                    } else {
                        if (gpd.getProvince().trim().equals("")) {
                            throw new BusinessException(ErrorCode.TRANS_PRO_IS_NULL);
                        }
                    }
                    if (gpd.getCity() == null && gpd.getDistrict() != null) {// 当区不为空时市为空报错
                        throw new BusinessException(ErrorCode.TRANS_CITY_NULL);
                    }
                    if (gpd.getCity() != null && gpd.getDistrict() != null) {
                        if (gpd.getCity().trim().equals("") && !gpd.getDistrict().trim().equals("")) {
                            throw new BusinessException(ErrorCode.TRANS_CITY_NULL);
                        }
                    }
                    TransAreaGroupDetial groupd = new TransAreaGroupDetial();
                    groupd.setCity(gpd.getCity());
                    groupd.setDistrict(gpd.getDistrict());
                    groupd.setProvince(gpd.getProvince());
                    groupd.setGroup(gp2);
                    transAreaGroupDetialDao.save(groupd);
                }
            } else {// 保存数据
                TransAreaGroup gp = new TransAreaGroup();
                gp.setCode(code);
                gp.setName(name);
                Customer cu = new Customer();
                cu.setId(cstmId);
                gp.setCustomer(cu);
                gp.setStatus(TransAreaGroupStatus.NORMAL);
                gp.setCreateTime(new Date());
                transAreaGroupDao.save(gp);
                TransAreaGroup newGroup = transAreaGroupDao.findTransAreaByCode(code);
                for (TransAreaGroupDetial gpd : noList) {
                    if (StringUtil.isEmpty(gpd.getProvince())) {
                        throw new BusinessException(ErrorCode.TRANS_PRO_IS_NULL);
                    }
                    if (StringUtil.isEmpty(gpd.getCity()) && !StringUtil.isEmpty(gpd.getDistrict())) {
                        throw new BusinessException(ErrorCode.TRANS_CITY_NULL);
                    }
                    TransAreaGroupDetial groupd = new TransAreaGroupDetial();
                    groupd.setCity(gpd.getCity());
                    groupd.setDistrict(gpd.getDistrict());
                    groupd.setProvince(gpd.getProvince());
                    groupd.setGroup(newGroup);
                    transAreaGroupDetialDao.save(groupd);
                }
            }
        }
        transAreaGroupDao.flush();
        transAreaGroupDetialDao.flush();
    }


    public Long saveMaTransport(TransportatorCommand ma, User user) {
        Transportator tran = null;
        if (ma.getId() != null && !ma.getId().equals("")) {
            tran = transportatorDao.getByPrimaryKey(ma.getId());
            tran.setName(ma.getName());
            tran.setCode(ma.getCode());
            tran.setExpCode(ma.getExpCode());
            tran.setPlatformCode(ma.getPlatformCode());
            tran.setFullName(ma.getFullName());
            tran.setLastModifyTime(new Date());
            tran.setLifeCycleStatus(DefaultLifeCycleStatus.valueOf(Integer.parseInt(ma.getStatusId())));
            tran.setK3Code(ma.getK3Code());
            tran.setIsSupportCod(ma.getIsSupportCod());
            tran.setCodMaxAmt(ma.getCodMaxAmt());
            tran.setJasperOnLine(ma.getJasperOnLine());
            tran.setJasperNormal(ma.getJasperNormal());
            if (ma.getIsAfter().equals("1")) {
                tran.setIsTransAfter(true);
            } else {
                tran.setIsTransAfter(false);
            }
            if (ma.getIsReg().equals("1")) {
                tran.setIsRegion(true);
            } else {
                tran.setIsRegion(false);
            }
            tran.setLastModifyTime(new Date());
            transportatorDao.save(tran);

        } else {
            tran = new Transportator();
            tran.setCreateTime(new Date());
            tran.setName(ma.getName());
            tran.setCode(ma.getCode());
            tran.setExpCode(ma.getExpCode());
            tran.setPlatformCode(ma.getPlatformCode());
            tran.setFullName(ma.getFullName());
            tran.setLifeCycleStatus(DefaultLifeCycleStatus.valueOf(Integer.parseInt(ma.getStatusId())));
            tran.setK3Code(ma.getK3Code());
            if (ma.getIsSupportCod().equals("1")) {
                tran.setIsSupportCod(true);
            } else {
                tran.setIsSupportCod(false);
            }
            tran.setCodMaxAmt(ma.getCodMaxAmt());
            tran.setJasperOnLine(ma.getJasperOnLine());
            tran.setJasperNormal(ma.getJasperNormal());
            if (ma.getIsAfter().equals("1")) {
                tran.setIsTransAfter(true);
            } else {
                tran.setIsTransAfter(false);
            }
            if (ma.getIsReg().equals("1")) {
                tran.setIsRegion(true);
            } else {
                tran.setIsRegion(false);
            }
            tran.setLastModifyTime(new Date());
            transportatorDao.save(tran);
        }

        // 保存日志
        TransportatorLog log = new TransportatorLog();
        log.setCreateTime(new Date());
        log.setName(tran.getName());
        log.setCode(tran.getCode());
        log.setExpCode(tran.getExpCode());
        log.setPlatformCode(tran.getPlatformCode());
        log.setFullName(tran.getFullName());
        log.setLifeCycleStatus(tran.getLifeCycleStatus());
        log.setIsSupportCod(tran.getIsSupportCod());
        log.setJasperOnLine(tran.getJasperOnLine());
        log.setJasperNormal(tran.getJasperNormal());
        log.setIsTransAfter(tran.getIsTransAfter());
        log.setIsRegion(tran.getIsRegion());
        log.setLastModifyTime(new Date());
        log.setUserId(user);
        transportatorLogDao.save(log);
        return tran.getId();
    }

    @Override
    public void updateTransArea(Long id, String name, Long status) {
        transAreaGroupDao.updateTransArea(id, name, status);
    }

    public ExcelReader getTransAreaReader() {
        return transAreaReader;
    }

    public void setTransAreaReader(ExcelReader transAreaReader) {
        this.transAreaReader = transAreaReader;
    }

    public List<TransAreaGroupDetial> findTransAreaGDetiaByGId(Long groupId) {
        return transAreaGroupDetialDao.findTransAreaGDetiaByGId(groupId, new BeanPropertyRowMapper<TransAreaGroupDetial>(TransAreaGroupDetial.class));
    }

}
