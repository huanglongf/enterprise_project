package com.jumbo.wms.manager.expressRadar;

import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.PhysicalWarehouseDao;
import com.jumbo.dao.authorization.UserDao;
import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.expressRadar.RadarAreaDao;
import com.jumbo.dao.expressRadar.RadarTimeRuleDao;
import com.jumbo.dao.expressRadar.RadarTimeRuleLogDao;
import com.jumbo.dao.expressRadar.RadarTransNoDao;
import com.jumbo.dao.expressRadar.RadarWarningReasonDao;
import com.jumbo.dao.expressRadar.RadarWarningReasonLineDao;
import com.jumbo.dao.expressRadar.RadarWarningReasonLogDao;
import com.jumbo.dao.expressRadar.SysRadarErrorCodeDao;
import com.jumbo.dao.expressRadar.SysRadarWarningLvDao;
import com.jumbo.dao.warehouse.TransportServiceDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.command.expressRadar.RadarTimeRuleCommand;
import com.jumbo.wms.model.command.expressRadar.RadarTransNoCommand;
import com.jumbo.wms.model.command.expressRadar.RadarWarningReasonCommand;
import com.jumbo.wms.model.command.expressRadar.RadarWarningReasonLineCommand;
import com.jumbo.wms.model.expressRadar.RadarTimeRule;
import com.jumbo.wms.model.expressRadar.RadarTimeRuleLog;
import com.jumbo.wms.model.expressRadar.RadarTransNo;
import com.jumbo.wms.model.expressRadar.RadarWarningReason;
import com.jumbo.wms.model.expressRadar.RadarWarningReasonLine;
import com.jumbo.wms.model.expressRadar.RadarWarningReasonLog;
import com.jumbo.wms.model.expressRadar.SysRadarArea;
import com.jumbo.wms.model.expressRadar.SysRadarErrorCode;
import com.jumbo.wms.model.expressRadar.SysRadarWarningLv;
import com.jumbo.wms.model.trans.TransportService;
import com.jumbo.wms.model.trans.TransportServiceStatus;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransType;

@Transactional
@Service("expressOocRadarManager")
public class ExpressOocRadarManagerImpl extends BaseManagerImpl implements ExpressOocRadarManager {


    private static final long serialVersionUID = -6867615155895134909L;

    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private RadarTimeRuleDao radarTimeRuleDao;
    @Autowired
    private RadarAreaDao radarAreaDao;
    @Autowired
    private PhysicalWarehouseDao physicalWarehouseDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TransportServiceDao transportServiceDao;
    @Autowired
    private RadarTimeRuleLogDao radarTimeRuleLogDao;
    @Autowired
    private SysRadarErrorCodeDao sysRadarErrorCodeDao;
    @Autowired
    private SysRadarWarningLvDao sysRadarWarningLvDao;
    @Autowired
    private RadarWarningReasonDao radarWarningReasonDao;
    @Autowired
    private RadarWarningReasonLogDao radarWarningReasonLogDao;
    @Autowired
    private RadarWarningReasonLineDao radarWarningReasonLineDao;
    @Autowired
    private RadarTransNoDao radarTransNoDao;

    @Override
    public List<RadarTimeRuleCommand> findPhyWarehouseByLpcode(String lpCode) {
        Transportator t = transportatorDao.findByCode(lpCode);
        return radarTimeRuleDao.findPhyWarehouseByLpcode(t.getId(), new BeanPropertyRowMapper<RadarTimeRuleCommand>(RadarTimeRuleCommand.class));
    }

    @Override
    public List<SysRadarArea> findRadarAreaProvince() {
        return radarAreaDao.findRadarAreaProvince(new BeanPropertyRowMapper<SysRadarArea>(SysRadarArea.class));
    }

    @Override
    public List<SysRadarArea> findRadarAreaCity(String province) {
        List<SysRadarArea> s = radarAreaDao.findRadarAreaCity(province, new BeanPropertyRowMapper<SysRadarArea>(SysRadarArea.class));
        return s;
    }

    @Override
    public void saveRadarTimeRule(RadarTimeRuleCommand rr, Long uid) {
        RadarTimeRule r = new RadarTimeRule();
        RadarTimeRuleLog rl = new RadarTimeRuleLog();
        User user = userDao.getByPrimaryKey(uid);
        // 查询可用物流商服务类型
        Long sid = transportServiceDao.getTransportServiceByTTE(rr.getLpCode(), rr.getWlService(), rr.getDateTimeType(), new SingleColumnRowMapper<Long>(Long.class));
        r.setPwh(physicalWarehouseDao.getByPrimaryKey(rr.getPhyid()));
        rl.setPwhId(rr.getPhyid());
        r.setCreateUser(user);
        rl.setCreateUserId(user.getId());
        r.setLastModifyUser(user);
        rl.setLastModifyUserId(user.getId());
        r.setCreateTime(new Date());
        rl.setCreateTime(new Date());
        r.setLastModifyTime(new Date());
        rl.setLastModifyTime(new Date());
        r.setStandardDate(Integer.parseInt(rr.getDateTime()));
        rl.setStandardDate(Integer.parseInt(rr.getDateTime()));
        r.setOverTakingTime(rr.getHour() + ":" + rr.getMinute());
        rl.setOverTakingTime(rr.getHour() + ":" + rr.getMinute());
        r.setVersion(0);
        rl.setVersion(0);
        if (TransTimeType.valueOf(rr.getDateTimeType()) == TransTimeType.ORDINARY) {
            // 快递时效类型是普通的才有配送区域
            SysRadarArea sysRadarArea = radarAreaDao.findRadarAreaByProvinceCity(rr.getProvince(), rr.getCity(), new BeanPropertyRowMapper<SysRadarArea>(SysRadarArea.class));
            r.setSra(sysRadarArea);
            rl.setSraId(sysRadarArea.getId());
        }
        if (sid == null) {
            // 没有物流服务类型，插入物流商信息
            Transportator ts = transportatorDao.findByCode(rr.getLpCode());
            TransportService t = new TransportService();
            t.setCreateTime(new Date());
            t.setStatus(TransportServiceStatus.NORMAL);
            t.setTrans(ts);
            t.setName(ts.getName());
            t.setTimeType(TransTimeType.valueOf(rr.getDateTimeType()));
            t.setType(TransType.valueOf(rr.getWlService()));
            transportServiceDao.save(t);
            r.setTs(t);
            rl.setTsId(t.getId());
        } else {
            r.setTs(transportServiceDao.getByPrimaryKey(sid));
            rl.setTsId(sid);
        }
        radarTimeRuleDao.save(r);
        radarTimeRuleLogDao.save(rl);
    }

    @Override
    public Pagination<RadarTimeRuleCommand> findRadarTimeRule(int start, int pageSize, RadarTimeRuleCommand r, Long id, Sort[] sorts) {
        String lpCode = null;
        Integer wlService = null;
        Integer dateTimeType = null;
        Long fjWh = null;
        String province = null;
        String city = null;
        if (r.getLpCode() != null && !r.getLpCode().equals("")) {
            lpCode = r.getLpCode();
        }
        if (r.getWlService() != null) {
            wlService = r.getWlService();
        }
        if (r.getDateTimeType() != null) {
            dateTimeType = r.getDateTimeType();
        }
        if (r.getPhyid() != null) {
            fjWh = r.getPhyid();
        }
        if (r.getProvince() != null && !r.getProvince().equals("")) {
            province = r.getProvince();
        }
        if (r.getCity() != null && !r.getCity().equals("")) {
            city = r.getCity();
        }
        return radarTimeRuleDao.findRadarTimeRule(start, pageSize, lpCode, wlService, dateTimeType, fjWh, province, city, new BeanPropertyRowMapper<RadarTimeRuleCommand>(RadarTimeRuleCommand.class), sorts);
    }

    @Override
    public void deleteRadarTimeRule(List<Long> ids) {
        for (Long l : ids) {
            radarTimeRuleDao.deleteByPrimaryKey(l);
        }
    }

    /**
     * 加载目的地省
     * 
     * @return
     */
    @Override
    public List<RadarTransNo> getExpressProvince() {
        return radarTransNoDao.getExpressProvince(new BeanPropertyRowMapper<RadarTransNo>(RadarTransNo.class));
    }

    /**
     * 初始化仓库
     * 
     * @return
     */
    @Override
    public List<RadarTransNoCommand> getexpressWarehouse() {

        return radarTransNoDao.getexpressWarehouse(new BeanPropertyRowMapper<RadarTransNoCommand>(RadarTransNoCommand.class));
    }

    /**
     * 快递省市详细信息
     * 
     * @param start
     * @param size
     * @param sorts
     * @return
     */
    @Override
    public Pagination<RadarTransNoCommand> findExpressDetailList(int start, int pageSize, String province, RadarTransNoCommand rdcmd) {
        String lpcode = null;
        String owner = null;
        Long physicalWarehouseId = null;
        if (rdcmd != null) {
            if (StringUtils.hasText(rdcmd.getLpcode())) {
                lpcode = rdcmd.getLpcode();
            }
            if (StringUtils.hasText(rdcmd.getOwner())) {
                owner = rdcmd.getOwner();
            }
            if (rdcmd.getPhysicalWarehouseId() != null) {
                physicalWarehouseId = rdcmd.getPhysicalWarehouseId();
            }
        }
        return radarTransNoDao.findExpressDetailList(start, pageSize, province, lpcode, owner, physicalWarehouseId, new BeanPropertyRowMapper<RadarTransNoCommand>(RadarTransNoCommand.class));

    }

    /**
     * 快递汇总信息查询
     * 
     * @param start
     * @param size
     * @param sorts
     * @return
     */
    public Pagination<RadarTransNoCommand> findExpressInfoCount(int start, int size, Sort[] sorts, RadarTransNoCommand rdcmd) {
        String lpcode = null;
        String owner = null;
        String province = null;
        Long physicalWarehouseId = null;
        if (rdcmd != null) {
            if (StringUtils.hasText(rdcmd.getLpcode())) {
                lpcode = rdcmd.getLpcode();
            }
            if (StringUtils.hasText(rdcmd.getOwner())) {
                owner = rdcmd.getOwner();
            }
            if (StringUtils.hasText(rdcmd.getDestinationProvince())) {
                province = rdcmd.getDestinationProvince();
            }
            if (rdcmd.getPhysicalWarehouseId() != null) {
                physicalWarehouseId = rdcmd.getPhysicalWarehouseId();
            }
        }
        return radarTransNoDao.findExpressInfoCount(start, size, sorts, lpcode, owner, province, physicalWarehouseId, new BeanPropertyRowMapper<RadarTransNoCommand>(RadarTransNoCommand.class));

    }

    /**
     * 异常详细信息
     * 
     * @param rowMapper
     * @return
     */
    public Pagination<RadarTransNoCommand> findAllExpressExceptInfo(int start, int pageSize, String province, String lpcode, String warehouseName, RadarTransNoCommand rdcmd) {
        // if (StringUtil.isEmpty(warehouseName)) warehouseName = null;
        String owner = null;
        if (rdcmd != null) {
            if (StringUtils.hasText(rdcmd.getOwner())) {
                owner = rdcmd.getOwner();
            }
        }
        if (StringUtil.isEmpty(province)) province = null;
        return radarTransNoDao.findAllExpressExceptInfo(start, pageSize, province, lpcode, warehouseName, owner, new BeanPropertyRowMapperExt<RadarTransNoCommand>(RadarTransNoCommand.class));
    }

    /**
     * 统计快递汇总信息
     * 
     * @param start
     * @param size
     * @param sorts
     * @return
     */
    public Pagination<RadarTransNoCommand> getExpreessTotal(int start, int size, Sort[] sorts, RadarTransNoCommand rdcmd) {
        String lpcode = null;
        String owner = null;
        String province = null;
        Long physicalWarehouseId = null;
        if (rdcmd != null) {
            if (StringUtils.hasText(rdcmd.getLpcode())) {
                lpcode = rdcmd.getLpcode();
            }
            if (StringUtils.hasText(rdcmd.getOwner())) {
                owner = rdcmd.getOwner();
            }
            if (StringUtils.hasText(rdcmd.getDestinationProvince())) {
                province = rdcmd.getDestinationProvince();
            }
            if (rdcmd.getPhysicalWarehouseId() != null && rdcmd.getPhysicalWarehouseId() == 0) {
                physicalWarehouseId = rdcmd.getPhysicalWarehouseId();
            }
        }
        return radarTransNoDao.getExpreessTotal(start, size, sorts, lpcode, owner, province, physicalWarehouseId, new BeanPropertyRowMapper<RadarTransNoCommand>(RadarTransNoCommand.class));
    }

    /**
     * 初始化店铺
     * 
     * @return
     */
    @Override
    public List<RadarTransNo> getExpressOwner() {
        return radarTransNoDao.getExpressOwner(new BeanPropertyRowMapper<RadarTransNo>(RadarTransNo.class));
    }

    /**
     * 快递雷达状态详情
     * 
     * @return
     */
    @Override
    public Pagination<RadarTransNoCommand> findExpressStatusInfo(int start, int pageSize, Sort[] sorts, String province, RadarTransNoCommand rdcmd, String exceprtionStatus) {
        String lpcode = null;
        String owner = null;
        if (rdcmd != null) {
            if (StringUtils.hasText(rdcmd.getLpcode())) {
                lpcode = rdcmd.getLpcode();
            }
            if (StringUtils.hasText(rdcmd.getOwner())) {
                owner = rdcmd.getOwner();
            }

        }
        if (StringUtil.isEmpty(exceprtionStatus)) exceprtionStatus = null;
        if (StringUtil.isEmpty(province)) province = null;
        return radarTransNoDao.findExpressStatusInfo(start, pageSize, sorts, province, lpcode, exceprtionStatus, owner, new BeanPropertyRowMapper<RadarTransNoCommand>(RadarTransNoCommand.class));
    }

    @Override
    public RadarTimeRuleCommand getRadarTimeRuleById(Long id) {
        return radarTimeRuleDao.getRadarTimeRuleById(id, new BeanPropertyRowMapper<RadarTimeRuleCommand>(RadarTimeRuleCommand.class));
    }

    @Override
    public void updateRadarTimeRule(RadarTimeRuleCommand rr, Long uid) {
        RadarTimeRule r = radarTimeRuleDao.getByPrimaryKey(rr.getId());
        RadarTimeRuleLog rl = new RadarTimeRuleLog();
        User user = userDao.getByPrimaryKey(uid);
        // 查询可用物流商服务类型
        Long sid = transportServiceDao.getTransportServiceByTTE(rr.getLpCode(), rr.getWlService(), rr.getDateTimeType(), new SingleColumnRowMapper<Long>(Long.class));
        r.setPwh(physicalWarehouseDao.getByPrimaryKey(rr.getPhyid()));
        rl.setPwhId(rr.getPhyid());
        rl.setCreateUserId(user.getId());
        r.setLastModifyUser(user);
        rl.setLastModifyUserId(user.getId());
        rl.setCreateTime(new Date());
        r.setLastModifyTime(new Date());
        rl.setLastModifyTime(new Date());
        r.setStandardDate(Integer.parseInt(rr.getDateTime()));
        rl.setStandardDate(Integer.parseInt(rr.getDateTime()));
        r.setOverTakingTime(rr.getHour() + ":" + rr.getMinute());
        rl.setOverTakingTime(rr.getHour() + ":" + rr.getMinute());
        rl.setVersion(0);
        if (TransTimeType.valueOf(rr.getDateTimeType()) == TransTimeType.ORDINARY) {
            // 快递时效类型是普通的才有配送区域
            SysRadarArea sysRadarArea = radarAreaDao.findRadarAreaByProvinceCity(rr.getProvince(), rr.getCity(), new BeanPropertyRowMapper<SysRadarArea>(SysRadarArea.class));
            r.setSra(sysRadarArea);
            rl.setSraId(sysRadarArea.getId());
        } else {
            r.setSra(null);
        }
        if (sid == null) {
            // 没有物流服务类型，插入物流商信息
            Transportator ts = transportatorDao.findByCode(rr.getLpCode());
            TransportService t = new TransportService();
            t.setCreateTime(new Date());
            t.setStatus(TransportServiceStatus.NORMAL);
            t.setTrans(ts);
            t.setName(ts.getName());
            t.setTimeType(TransTimeType.valueOf(rr.getDateTimeType()));
            t.setType(TransType.valueOf(rr.getWlService()));
            transportServiceDao.save(t);
            r.setTs(t);
            rl.setTsId(t.getId());
        } else {
            r.setTs(transportServiceDao.getByPrimaryKey(sid));
            rl.setTsId(sid);
        }
        radarTimeRuleDao.save(r);
        radarTimeRuleLogDao.save(rl);
    }

    @Override
    public List<SysRadarErrorCode> findRdErrorCode() {
        return sysRadarErrorCodeDao.findRdErrorCode(new BeanPropertyRowMapper<SysRadarErrorCode>(SysRadarErrorCode.class));
    }

    @Override
    public List<SysRadarWarningLv> findRdWarningLv() {
        return sysRadarWarningLvDao.findRdWarningLv(new BeanPropertyRowMapper<SysRadarWarningLv>(SysRadarWarningLv.class));
    }

    @Override
    public List<SysRadarErrorCode> findOrderErrorCode() {
        return sysRadarErrorCodeDao.findOrderErrorCode(new BeanPropertyRowMapper<SysRadarErrorCode>(SysRadarErrorCode.class));
    }

    @Override
    public String saveRadarWarningReason(RadarWarningReasonCommand rwr, Long uid) {
        SysRadarErrorCode sre = sysRadarErrorCodeDao.getByPrimaryKey(rwr.getEid());
        SysRadarWarningLv srw = sysRadarWarningLvDao.getByPrimaryKey(rwr.getLvid());
        String warningName = sre.getName();
        RadarWarningReason radarWarningReason = radarWarningReasonDao.findRadarWarningReasonByName(warningName, new BeanPropertyRowMapper<RadarWarningReason>(RadarWarningReason.class));
        if (null != radarWarningReason) {
            return "EXIST";
        }
        try {
            User user = userDao.getByPrimaryKey(uid);
            RadarWarningReason r = new RadarWarningReason();
            RadarWarningReasonLog rl = new RadarWarningReasonLog();
            r.setStatus(1);
            r.setCode(sre.getCode());
            rl.setCode(sre.getCode());
            rl.setErrorCode(sre.getCode());
            r.setName(sre.getName());
            rl.setName(sre.getName());
            rl.setErrorName(sre.getName());
            rl.setWarningName(sre.getName());
            r.setRec(sre);
            r.setSw(srw);
            rl.setWarningLv(srw.getLv());
            r.setCreateTime(new Date());
            rl.setCreareTime(new Date());
            r.setLastModifyTime(new Date());
            r.setCreateUser(user);
            rl.setCreateUserId(user.getId());
            r.setModifyUser(user);
            r.setVersion(0);
            r.setRemark(rwr.getRemark());
            rl.setRemark(rwr.getRemark());
            radarWarningReasonDao.save(r);
            radarWarningReasonLogDao.save(rl);
            return "SUCCESS";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "ERROR";
        }
    }

    @Override
    public Pagination<RadarWarningReasonCommand> findRadarWarningReason(int start, int pageSize, RadarWarningReasonCommand r, Long id, Sort[] sorts) {
        Long eid = null;
        Long lvid = null;
        if (r.getEid() != null) {
            eid = r.getEid();
        }
        if (r.getLvid() != null) {
            lvid = r.getLvid();
        }
        return radarWarningReasonDao.findRadarWarningReason(start, pageSize, eid, lvid, new BeanPropertyRowMapper<RadarWarningReasonCommand>(RadarWarningReasonCommand.class), sorts);
    }

    @Override
    public List<SysRadarWarningLv> findRdWarningLvByLv(Long id) {
        return sysRadarWarningLvDao.findRdWarningLvByLv(id, new BeanPropertyRowMapper<SysRadarWarningLv>(SysRadarWarningLv.class));
    }

    @Override
    public void deleteRadarWarningReason(List<Long> ids, Long uid) {
        User user = userDao.getByPrimaryKey(uid);
        for (Long l : ids) {
            RadarWarningReason r = radarWarningReasonDao.getByPrimaryKey(l);
            r.setModifyUser(user);
            r.setLastModifyTime(new Date());
            r.setStatus(0);
            radarWarningReasonDao.save(r);
        }
    }

    @Override
    public void saveRadarWarningReasonLine(RadarWarningReasonLineCommand rwrl) {
        RadarWarningReason rwr = radarWarningReasonDao.getByPrimaryKey(rwrl.getWrId());
        SysRadarWarningLv lv = sysRadarWarningLvDao.getByPrimaryKey(rwrl.getLvId());
        RadarWarningReasonLine r = new RadarWarningReasonLine();
        r.setWr(rwr);
        r.setSw(lv);
        r.setWarningHour(rwrl.getWarningHour());
        r.setMemo(rwrl.getMemo());
        radarWarningReasonLineDao.save(r);
    }

    @Override
    public List<RadarWarningReasonLineCommand> findRadarWarningReasonLine(Long id) {
        return radarWarningReasonLineDao.findRadarWarningReasonLine(id, new BeanPropertyRowMapper<RadarWarningReasonLineCommand>(RadarWarningReasonLineCommand.class));
    }

    @Override
    public void deleteRadarWarningReasonLine(Long id) {
        radarWarningReasonLineDao.deleteByPrimaryKey(id);
    }

    @Override
    public String checkRadarTimeRule(RadarTimeRuleCommand rr) {
        String result = "";
        Long sid = transportServiceDao.getTransportServiceByTTE(rr.getLpCode(), rr.getWlService(), rr.getDateTimeType(), new SingleColumnRowMapper<Long>(Long.class));
        if (sid == null) {
            result = "null";
            return result;
        }
        SysRadarArea sysRadarArea = null;
        Long sraid = null;
        if (TransTimeType.valueOf(rr.getDateTimeType()) == TransTimeType.ORDINARY) {
            // 快递时效类型是普通的才有配送区域
            sysRadarArea = radarAreaDao.findRadarAreaByProvinceCity(rr.getProvince(), rr.getCity(), new BeanPropertyRowMapper<SysRadarArea>(SysRadarArea.class));
            sraid = sysRadarArea.getId();
        }
        RadarTimeRuleCommand rtr = radarTimeRuleDao.checkRadarTimeRule(rr.getPhyid(), sraid, sid, new BeanPropertyRowMapperExt<RadarTimeRuleCommand>(RadarTimeRuleCommand.class));
        if (rtr == null) {
            result = "null";
        } else {
            result = "notnull";
        }
        return result;
    }
}
