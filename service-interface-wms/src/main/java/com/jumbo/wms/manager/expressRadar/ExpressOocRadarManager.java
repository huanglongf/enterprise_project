package com.jumbo.wms.manager.expressRadar;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.command.expressRadar.RadarTimeRuleCommand;
import com.jumbo.wms.model.command.expressRadar.RadarTransNoCommand;
import com.jumbo.wms.model.command.expressRadar.RadarWarningReasonCommand;
import com.jumbo.wms.model.command.expressRadar.RadarWarningReasonLineCommand;
import com.jumbo.wms.model.expressRadar.RadarTransNo;
import com.jumbo.wms.model.expressRadar.SysRadarArea;
import com.jumbo.wms.model.expressRadar.SysRadarErrorCode;
import com.jumbo.wms.model.expressRadar.SysRadarWarningLv;

public interface ExpressOocRadarManager extends BaseManager {

    List<RadarTimeRuleCommand> findPhyWarehouseByLpcode(String lpCode);

    List<SysRadarArea> findRadarAreaProvince();

    List<SysRadarArea> findRadarAreaCity(String province);

    void saveRadarTimeRule(RadarTimeRuleCommand r, Long uid);

    Pagination<RadarTimeRuleCommand> findRadarTimeRule(int start, int pageSize, RadarTimeRuleCommand r, Long id, Sort[] sorts);

    void deleteRadarTimeRule(List<Long> ids);

    RadarTimeRuleCommand getRadarTimeRuleById(Long id);

    void updateRadarTimeRule(RadarTimeRuleCommand r, Long uid);

    /**
     * 快递汇总信息查询
     * 
     * @param start
     * @param size
     * @param sorts
     * @return
     */
    Pagination<RadarTransNoCommand> findExpressInfoCount(int start, int pageSize, Sort[] sorts, RadarTransNoCommand rdcmd);

    /**
     * 快递雷达省市详细信息
     * 
     * @param start
     * @param size
     * @param sorts
     * @return
     */
    Pagination<RadarTransNoCommand> findExpressDetailList(int start, int pageSize, String province, RadarTransNoCommand rdcmd);

    /**
     * 统计 快递汇总信息查询数据
     * 
     * @param rowMapper
     * @return
     */
    Pagination<RadarTransNoCommand> getExpreessTotal(int start, int pageSize, Sort[] sorts, RadarTransNoCommand rdcmd);

    /**
     * 异常详细信息
     * 
     * @param rowMapper
     * @return
     */
    Pagination<RadarTransNoCommand> findAllExpressExceptInfo(int start, int pageSize, String province, String lpcode, String warehouseName, RadarTransNoCommand rdcmd);

    /**
     * 初始化仓库
     * 
     * @return
     */
    List<RadarTransNoCommand> getexpressWarehouse();

    /**
     * 快递雷达状态详情
     * 
     * @return
     */
    Pagination<RadarTransNoCommand> findExpressStatusInfo(int start, int pageSize, Sort[] sorts, String province, RadarTransNoCommand rdcmd, String exceprtionStatus);

    /**
     * 获取目的地省
     * 
     * @return
     */
    List<RadarTransNo> getExpressProvince();

    /**
     * 初始化店铺
     * 
     * @return
     */
    List<RadarTransNo> getExpressOwner();

    List<SysRadarErrorCode> findRdErrorCode();

    List<SysRadarWarningLv> findRdWarningLv();

    String saveRadarWarningReason(RadarWarningReasonCommand r, Long uid);

    Pagination<RadarWarningReasonCommand> findRadarWarningReason(int start, int pageSize, RadarWarningReasonCommand r, Long id, Sort[] sorts);

    List<SysRadarWarningLv> findRdWarningLvByLv(Long id);

    void deleteRadarWarningReason(List<Long> ids, Long uid);

    void saveRadarWarningReasonLine(RadarWarningReasonLineCommand r);

    List<RadarWarningReasonLineCommand> findRadarWarningReasonLine(Long id);

    void deleteRadarWarningReasonLine(Long id);

    List<SysRadarErrorCode> findOrderErrorCode();

    String checkRadarTimeRule(RadarTimeRuleCommand r);
}
