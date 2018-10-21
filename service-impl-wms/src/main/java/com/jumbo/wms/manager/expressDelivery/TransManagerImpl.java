package com.jumbo.wms.manager.expressDelivery;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.PhysicalWarehouseDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.warehouse.WhTransAreaNoDao;
import com.jumbo.dao.warehouse.WhTransProvideNoCommandDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.util.StringUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOnLineFactory;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.UserCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.WhTransAreaNo;
import com.jumbo.wms.model.warehouse.WhTransAreaNoCommand;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;
import com.jumbo.wms.model.warehouse.WhTransProvideNoCommand;

@Transactional
@Service("transManager")
public class TransManagerImpl extends BaseManagerImpl implements TransManager {

    private static final long serialVersionUID = -4970861224916485359L;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private WhTransAreaNoDao transAreaNoDao;
    @Autowired
    private WhTransProvideNoCommandDao whTransProvideNoCommandDao;
    @Autowired
    private WhTransAreaNoDao whTransAreaNoDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private PhysicalWarehouseDao physicalWarehouseDao;
    @Autowired
    private TransOnLineFactory transOnLineFactory;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;

    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;

    @Resource(name = "transProvideNoReader")
    private ExcelReader transProvideNoReader;
    @Resource(name = "transProvinceNoReader")
    private ExcelReader transProvinceNoReader;
    @Resource(name = "userWarehouseReader")
    private ExcelReader userWarehouseReader;

    // 物流服务商接口Map,默认5分钟过期,key=lpcode_whouid
    private TimeHashMap<String, TransOlInterface> tranInterfaceMap = new TimeHashMap<String, TransOlInterface>(5 * 60 * 1000);
    // 仓库基础信息配置缓存列表，默认5分钟过期，key=whouid
    private TimeHashMap<Long, Warehouse> whTimeMap = new TimeHashMap<Long, Warehouse>(5 * 60 * 1000);

    /**
     * 物流省份编码缓存
     */
    public static HashMap<String, List<WhTransAreaNo>> transAreaCache = new HashMap<String, List<WhTransAreaNo>>();



    /**
     * MQ实现作业单获取运单号
     * 
     * @author jingkai
     * @param staId
     * @param staCode
     * @param lpcode
     * @param whouId
     * @param currentCount 当前获取运单号次数
     */
    public void takeTransNoBySta(Long staId, String staCode, String lpcode, Long whouId, int currentCount) {
        if (log.isDebugEnabled()) {
            log.debug("get trans no by sta,sta : {}, lpcode : {},whouid :{}", staId, lpcode, whouId);
        }
        // 使用内存缓存获取运单号获取接口配置
        TransOlInterface trans = tranInterfaceMap.get(lpcode + "_" + whouId);
        if (trans == null) {
            trans = transOnLineFactory.getTransOnLine(lpcode, whouId);
            tranInterfaceMap.put(lpcode + "_" + whouId, trans);
        }
        if (log.isDebugEnabled()) {
            log.debug("get trans no by sta,get trans success,sta : {}, lpcode : {},whouid :{}", staId, lpcode, whouId);
        }
        try {
            if (trans != null) {
                // 如果使用电子面单则获取电子面单
                StaDeliveryInfo sd = trans.matchingTransNo(staId);
                if (sd != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("get trans no by sta success,sta : {}, lpcode : {},whouid :{},tran no:{}", staId, lpcode, whouId, sd.getTrackingNo());
                    }
                    // 如果电子面单获取成功,则判断是否外包仓，外包仓解锁通知单据数据
                    Warehouse wh = whTimeMap.get(whouId);
                    if (wh == null) {
                        wh = warehouseDao.getByOuId(whouId);
                        whTimeMap.put(whouId, wh);
                    }
                    if (StringUtils.hasText(wh.getVmiSource())) {
                        // 外包仓解锁
                        MsgOutboundOrder mo = msgOutboundOrderDao.getByStaCode(staCode);
                        if (mo != null && mo.getIsLocked() != null && mo.getIsLocked()) {
                            // 解锁的同时设置msgOutBoundOrder的TransNo，SfCityCode
                            mo.setTransNo(sd.getTrackingNo());
                            mo.setSfCityCode(sd.getTransCityCode());
                            mo.setCreateTime(new Date());
                            // 解锁
                            mo.setIsLocked(false);
                            msgOutboundOrderDao.save(mo);
                        }
                    }
                }
            } else {
                // 非电子面单,设置不再获取
                wareHouseManagerExecute.updateNextGetTransTime(staId);
            }
        } catch (BusinessException e) {
            log.warn("fail to get Trans no,sta : {}, lpcode : {},whouid :{},error code : {}", staId, lpcode, whouId, e.getErrorCode());
            // 数据丢入MQ当前次数+1
            if (currentCount <= 10) {
                reputTakeTransNoToMq(staId, staCode, lpcode, whouId, currentCount + 1);
            }
        } catch (Exception e) {
            log.error("fail to get Trans no,sta : {}, lpcode : {},whouid :{},", staId, lpcode, whouId, e);
            if (currentCount <= 10) {
                reputTakeTransNoToMq(staId, staCode, lpcode, whouId, currentCount + 1);
            }
        }
    }

    /**
     * 再次获取运单号，将数据丢入MQ队列
     * 
     * @author jingkai
     * @param staId
     * @param staCode
     * @param lpcode
     * @param whouId
     * @param currentCount
     */
    private void reputTakeTransNoToMq(Long staId, String staCode, String lpcode, Long whouId, int currentCount) {
        // FIXME 将数据丢入MQ，再次获取运单号
    }


    public Pagination<WhTransProvideNoCommand> findProvide(int start, int pageSize, WhTransProvideNoCommand provide, Long id, Sort[] sorts) {
        Pagination<WhTransProvideNoCommand> whTransProvideNoCommand = new Pagination<WhTransProvideNoCommand>();
        String lpcode = null;
        if (provide != null) {
            if (!provide.getLpcode().trim().equals("") && provide.getLpcode() != null) {
                lpcode = provide.getLpcode();
            }
        }
        whTransProvideNoCommand = whTransProvideNoCommandDao.findProvide(start, pageSize, lpcode, new BeanPropertyRowMapperExt<WhTransProvideNoCommand>(WhTransProvideNoCommand.class), sorts);
        if (null != whTransProvideNoCommand.getItems() && whTransProvideNoCommand.getItems().size() > 0) {
            List<WhTransProvideNoCommand> whTransProvideNoCommandList = whTransProvideNoCommand.getItems();
            for (WhTransProvideNoCommand list : whTransProvideNoCommandList) {
                Long qty = whTransProvideNoCommandDao.findProvideByLpcode(list.getLpcode(), list.getOwner(), list.getCheckboxSf(), list.getJcustid(), new SingleColumnRowMapper<Long>(Long.class));
                if (null != qty) {
                    list.setQty(qty);
                } else {
                    list.setQty(0L);
                }
            }

        }
        return whTransProvideNoCommand;
    }

    public Pagination<WhTransAreaNoCommand> findProvince(int start, int pageSize, WhTransAreaNoCommand areaNo, Sort[] sorts) {
        String lpcode = null;
        String province = null;
        String areaNumber = null;
        if (areaNo != null) {
            if (!StringUtil.isEmpty(areaNo.getLpcode())) {
                lpcode = areaNo.getLpcode();
            }
            if (!StringUtil.isEmpty(areaNo.getProvince())) {
                province = areaNo.getProvince();
            }
            if (!StringUtil.isEmpty(areaNo.getAreaNumber())) {
                areaNumber = areaNo.getAreaNumber();
            }
        }
        return whTransAreaNoDao.findProvince(start, pageSize, lpcode, province, areaNumber, new BeanPropertyRowMapperExt<WhTransAreaNoCommand>(WhTransAreaNoCommand.class), sorts);
    }

    @SuppressWarnings("unchecked")
    public ReadStatus transProvideImport(File file, String lpcode, String jcustid, Boolean checkboxSf) {
        log.debug("===========transProvideImport start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<WhTransProvideNo> noList = null;
        ReadStatus rs = null;
        try {
            rs = transProvideNoReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                return rs;
            }
            noList = (List<WhTransProvideNo>) beans.get("data");
            transProvideImport(noList, lpcode, jcustid, checkboxSf);
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("transProvideImport Exception:" + lpcode, ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public void transProvideImport(List<WhTransProvideNo> noList, String lpcode, String jcustid, Boolean checkboxSf) {
        Map<String, WhTransProvideNo> map = new HashMap<String, WhTransProvideNo>();
        String checkNo = "";
        int tempCount = 0;
        for (WhTransProvideNo wo : noList) {
            if (wo.getTransno() != null && !wo.getTransno().equals("")) {
                if (wo.getTransno().contains(".")) {
                    throw new BusinessException(ErrorCode.EXCEL_IMPORT_TRANS_NO4);
                } else {
                    if (map.containsKey(wo.getTransno())) {
                        throw new BusinessException(ErrorCode.EXCEL_IMPORT_TRANS_NO);
                    } else {
                        WhTransProvideNo wp = transProvideNoDao.getByTranNo(wo.getTransno());
                        if (wp == null) {
                            WhTransProvideNo no = new WhTransProvideNo();
                            no.setJcustid(jcustid);
                            no.setCheckboxSf(checkboxSf == null ? false : checkboxSf);
                            no.setLpcode(lpcode);
                            no.setTransno(wo.getTransno());
                            no.setVersion(0);
                            if (Transportator.JD.equals(lpcode)) {
                                Date createTime = new Date();
                                no.setCreateTime(createTime);
                                // 75天后过期
                                long cTime = createTime.getTime();
                                long eTime = cTime + 1000 * 60 * 60 * 24 * 75;
                                Date expTime = new Date(eTime);
                                no.setExpTime(expTime);
                            }
                            map.put(wo.getTransno(), no);
                        } else {
                            tempCount++;
                            if (tempCount == 1) { // 当商品不存在时，把编码累加
                                checkNo = wo.getTransno();
                            } else {
                                if (!checkNo.contains(wo.getTransno())) {
                                    checkNo += "/" + wo.getTransno();
                                }
                            }
                        }
                    }
                }
            } else {
                throw new BusinessException(ErrorCode.EXCEL_IMPORT_TRANS_NO3);
            }
        }
        if (tempCount > 0) {
            throw new BusinessException(ErrorCode.EXCEL_IMPORT_TRANS_NO2, new Object[] {checkNo});
        }
        for (WhTransProvideNo whTransProvideNo : map.values()) {
            transProvideNoDao.save(whTransProvideNo);
        }
        transProvideNoDao.flush();
    }

    @SuppressWarnings("unchecked")
    public ReadStatus transProvinceImport(File file, String lpcode, Long updateMode, Long userId) {
        log.debug("===========transProvideImport start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<WhTransAreaNo> noList = null;
        ReadStatus rs = null;
        try {
            rs = transProvinceNoReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                return rs;
            }
            noList = (List<WhTransAreaNo>) beans.get("data");
            transProvinceImport(noList, lpcode, updateMode, userId);
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("transProvideImport Exception:" + lpcode, ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public void transProvinceImport(List<WhTransAreaNo> noList, String lpcode, Long updateMode, Long userId) {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, WhTransAreaNo> map2 = new HashMap<String, WhTransAreaNo>();
        List<WhTransAreaNo> list = transAreaNoDao.getTransAreaByLpcode(lpcode, new BeanPropertyRowMapperExt<WhTransAreaNo>(WhTransAreaNo.class));
        if (noList.size() == 0) {
            throw new BusinessException(ErrorCode.INFO_ERROR_NOT_SAVE);
        }
        if (updateMode == 1) { // 部分更新。保留原有数据，将新增数据插入，根据编码去重复。
            for (WhTransAreaNo whTransAreaNo : list) {
                map.put(whTransAreaNo.getProvince(), whTransAreaNo.getProvince()); // 将原数据放入map
            }
            for (WhTransAreaNo each : noList) {
                if (StringUtil.isEmpty(each.getAreaNumber())) {
                    throw new BusinessException(ErrorCode.EXCEL_IMPORT_AREA_NO1);
                }
                if (StringUtil.isEmpty(each.getProvince())) {
                    throw new BusinessException(ErrorCode.EXCEL_IMPORT_AREA_NO2);
                }
                if (map.containsKey(each.getProvince())) {
                    // 存在，删除原来的,插入新数据。
                    transAreaNoDao.deleteAreaByAera(lpcode, each.getProvince());
                    map2.put(each.getProvince(), each);
                } else {
                    map2.put(each.getProvince(), each);
                }
            }
            for (WhTransAreaNo area : map2.values()) {
                WhTransAreaNo no = new WhTransAreaNo();
                no.setAreaNumber(area.getAreaNumber());
                no.setCreateId(userId);
                no.setCreateTime(new Date());
                no.setLpcode(lpcode);
                no.setProvince(area.getProvince());
                transAreaNoDao.save(no);
            }
            transAreaNoDao.flush();
            List<WhTransAreaNo> lists = transAreaNoDao.getTransAreaByLpcode(lpcode, new BeanPropertyRowMapperExt<WhTransAreaNo>(WhTransAreaNo.class));
            transAreaCache.clear();
            transAreaCache.put(lpcode, lists);
        } else {// 全部更新。删除原有数据，将新数据插入。
            transAreaNoDao.deleteAreaByLpcode(lpcode);
            for (WhTransAreaNo area : noList) {
                if (StringUtil.isEmpty(area.getAreaNumber())) {
                    throw new BusinessException(ErrorCode.EXCEL_IMPORT_AREA_NO1);
                }
                if (StringUtil.isEmpty(area.getProvince())) {
                    throw new BusinessException(ErrorCode.EXCEL_IMPORT_AREA_NO2);
                }
                WhTransAreaNo no = new WhTransAreaNo();
                no.setAreaNumber(area.getAreaNumber());
                no.setCreateId(userId);
                no.setCreateTime(new Date());
                no.setLpcode(lpcode);
                no.setProvince(area.getProvince());
                transAreaNoDao.save(no);
            }
            transAreaNoDao.flush();
            List<WhTransAreaNo> lists = transAreaNoDao.getTransAreaByLpcode(lpcode, new BeanPropertyRowMapperExt<WhTransAreaNo>(WhTransAreaNo.class));
            transAreaCache.clear();
            transAreaCache.put(lpcode, lists);
        }
    }

    /**
     * 根据ID删除数据
     */
    public void deleteProvince(List<Long> ids) {
        if (ids != null && ids.size() != 0) {
            for (Long id : ids) {
                transAreaNoDao.deleteByPrimaryKey(id);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public ReadStatus userWarehouseRefImport(File file) {
        log.debug("===========userWarehouseRefImport start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<UserCommand> userList = null;
        ReadStatus rs = null;
        try {
            rs = userWarehouseReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                return rs;
            }
            userList = (List<UserCommand>) beans.get("data");
            userWarehouseImport(userList);
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("userWarehouseRefImport", ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 用户仓库导入
     * 
     * @param user
     */
    private void userWarehouseImport(List<UserCommand> users) {
        // 验证用户
        Map<Long, Long> map = new HashMap<Long, Long>();
        for (UserCommand user : users) {
            if (user.getLoginName() == null || user.getLoginName().trim().equals("")) {
                throw new BusinessException(ErrorCode.USER_LOGIN_NAME_IS_NULL, new Object[] {"null"});
            }
            User us = userDao.findByLoginName(user.getLoginName());
            if (us == null) {
                throw new BusinessException(ErrorCode.USER_LOGIN_NAME_IS_NULL, new Object[] {user.getLoginName()});
            }
            PhysicalWarehouse ware = physicalWarehouseDao.getPhyWarehouseByName(user.getWarehouseName());
            if (ware == null) {
                throw new BusinessException(ErrorCode.OU_WHAREHOUSE_NOT_FOUNT, new Object[] {user.getWarehouseName()});
            }
            map.put(us.getId(), ware.getId());
        }
        // 插入数据
        Set<Long> ketSet = map.keySet();
        for (Long id : ketSet) {
            Long userId = id;
            Long ouId = map.get(id);
            warehouseDao.deleteUserWhRef(userId);
            warehouseDao.saveUserWhRef(id, ouId);
        }
    }
}
