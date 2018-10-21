package com.jumbo.web.action.expressRadar;

import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.expressRadar.ExpressOocRadarManager;
import com.jumbo.wms.model.command.expressRadar.RadarTimeRuleCommand;
import com.jumbo.wms.model.command.expressRadar.RadarTransNoCommand;
import com.jumbo.wms.model.command.expressRadar.RadarWarningReasonCommand;
import com.jumbo.wms.model.command.expressRadar.RadarWarningReasonLineCommand;


public class ExpressOocRadarAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 4290580853578025159L;

    private String lpCode;
    private String province;
    private RadarTimeRuleCommand rc;
    private List<Long> ids;
    private Long id;
    private RadarWarningReasonCommand rwr;
    private RadarWarningReasonLineCommand rwrl;
    private RadarTransNoCommand rdcmd;
    private String exceprtionStatus;
    private Long alge;
    private String warehouseName;
    private String lpcode;

    @Autowired
    private ExpressOocRadarManager expressOocRadarManager;


    public String manager() {
        return SUCCESS;
    }

    /**
     * 快递雷达汇总
     * 
     * @return
     */
    public String expressCountInfo() {
        return SUCCESS;
    }

    /**
     * 快递雷达 省市明细
     * 
     * @return
     */
    public String findExpressDetailList() {
        setTableConfig();
        Pagination<RadarTransNoCommand> list = expressOocRadarManager.findExpressDetailList(tableConfig.getStart(), tableConfig.getPageSize(), province, rdcmd);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 查询快递雷达汇总信息
     * 
     * @return
     */
    public String findExpressInfoCount() {
        setTableConfig();
        Pagination<RadarTransNoCommand> list = expressOocRadarManager.findExpressInfoCount(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), rdcmd);
        request.put(JSON, toJson(list));
        return JSON;

    }

    /**
     * 统计 汇总数量
     * 
     * @return
     */
    public String getExpreessTotal() {
        setTableConfig();
        Pagination<RadarTransNoCommand> list = expressOocRadarManager.getExpreessTotal(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), rdcmd);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 异常件 详细信息
     * 
     * @return
     */
    public String findAllExpressExceptionInfo() {
        setTableConfig();
        Pagination<RadarTransNoCommand> list = expressOocRadarManager.findAllExpressExceptInfo(tableConfig.getStart(), tableConfig.getPageSize(), province, lpcode, warehouseName, rdcmd);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 初始化 仓库
     * 
     * @return
     */
    public String getexpressWarehouse() {
        request.put(JSON, JsonUtil.collection2json(expressOocRadarManager.getexpressWarehouse()));
        return JSON;
    }

    /**
     * 动态加载列名
     * 
     * @return
     */
    public String findExpressStatusInfo() {
        setTableConfig();
        Pagination<RadarTransNoCommand> list = expressOocRadarManager.findExpressStatusInfo(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), province, rdcmd, exceprtionStatus);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 加载省
     * 
     * @return
     */
    public String getExpressProvince() {
        request.put(JSON, JsonUtil.collection2json(expressOocRadarManager.getExpressProvince()));
        return JSON;

    }

    /**
     * 初始化店铺
     * 
     * @return
     */
    public String getExpressOwner() {
        request.put(JSON, JsonUtil.collection2json(expressOocRadarManager.getExpressOwner()));
        return JSON;
    }

    /**
     * 通过物流商编码获得对应物理仓
     * 
     * @return
     */
    public String findPhyWarehouseByLpcode() {
        request.put(JSON, JsonUtil.collection2json(expressOocRadarManager.findPhyWarehouseByLpcode(lpCode)));
        return JSON;
    }

    /**
     * 获取 快递雷达区域范围(省)
     * 
     * @return
     */
    public String findRadarAreaProvince() {
        request.put(JSON, JsonUtil.collection2json(expressOocRadarManager.findRadarAreaProvince()));
        return JSON;
    }

    /**
     * 通过省获取 快递雷达区域范围(市)
     * 
     * @return
     */
    public String findRadarAreaCity() {
        request.put(JSON, JsonUtil.collection2json(expressOocRadarManager.findRadarAreaCity(province)));
        return JSON;
    }

    /**
     * 添加快递时效设置
     * 
     * @return
     * @throws Exception
     */
    public String saveRadarTimeRule() throws Exception {
        JSONObject json = new JSONObject();
        try {
            expressOocRadarManager.saveRadarTimeRule(rc, this.userDetails.getUser().getId());
            json.put("msg", "success");
        } catch (Exception e) {
            json.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 查询对应快递时效信息
     * 
     * @return
     */
    public String findRadarTimeRule() {
        setTableConfig();
        Pagination<RadarTimeRuleCommand> rrList = expressOocRadarManager.findRadarTimeRule(tableConfig.getStart(), tableConfig.getPageSize(), rc, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(rrList));
        return JSON;
    }

    /**
     * 删除快递时效信息
     * 
     * @return
     * @throws Exception
     */
    public String deleteRadarTimeRule() throws Exception {
        JSONObject json = new JSONObject();
        try {
            expressOocRadarManager.deleteRadarTimeRule(ids);
            json.put("msg", "success");
        } catch (Exception e) {
            json.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 根据ID查找时效明细
     * 
     * @return
     * @throws Exception
     */
    public String getRadarTimeRuleById() throws Exception {
        JSONObject json = new JSONObject();
        try {
            RadarTimeRuleCommand rr = expressOocRadarManager.getRadarTimeRuleById(id);
            if (rr != null) {
                json.put("msg", JsonUtil.obj2json(rr));
            }
        } catch (Exception e) {
            json.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 修改快递时效信息
     * 
     * @return
     * @throws Exception
     */
    public String updateRadarTimeRule() throws Exception {
        JSONObject json = new JSONObject();
        try {
            expressOocRadarManager.updateRadarTimeRule(rc, this.userDetails.getUser().getId());
            json.put("msg", "success");
        } catch (Exception e) {
            json.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 获取预警类型
     * 
     * @return
     */
    public String findRdErrorCode() {
        request.put(JSON, JsonUtil.collection2json(expressOocRadarManager.findRdErrorCode()));
        return JSON;
    }

    /**
     * 获取预警级别
     * 
     * @return
     */
    public String findRdWarningLv() {
        request.put(JSON, JsonUtil.collection2json(expressOocRadarManager.findRdWarningLv()));
        return JSON;
    }

    /**
     * 保存快递雷达预警信息
     * 
     * @return
     * @throws Exception
     */
    public String saveRadarWarningReason() throws Exception {
        JSONObject json = new JSONObject();
        String flag = null;
        try {
            flag = expressOocRadarManager.saveRadarWarningReason(rwr, this.userDetails.getUser().getId());
            if ("SUCCESS".equals(flag)) {
                json.put("msg", "success");
            } else if ("EXIST".equals(flag)) {
                json.put("msg", "exist");
            } else {
                json.put("msg", "error");
            }
        } catch (Exception e) {
            json.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 查询快递预警类型列表
     * 
     * @return
     */
    public String findRadarWarningReason() {
        setTableConfig();
        Pagination<RadarWarningReasonCommand> rrList = expressOocRadarManager.findRadarWarningReason(tableConfig.getStart(), tableConfig.getPageSize(), rwr, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(rrList));
        return JSON;
    }

    /**
     * 通过快递预警初始级别判断快递雷达预警原因明细预警级别初始值
     * 
     * @return
     */
    public String findRdWarningLvByLv() {
        request.put(JSON, JsonUtil.collection2json(expressOocRadarManager.findRdWarningLvByLv(rwr.getId())));
        return JSON;
    }

    /**
     * 删除快递预警信息
     * 
     * @return
     * @throws Exception
     */
    public String deleteRadarWarningReason() throws Exception {
        JSONObject json = new JSONObject();
        try {
            expressOocRadarManager.deleteRadarWarningReason(ids, this.userDetails.getUser().getId());
            json.put("msg", "success");
        } catch (Exception e) {
            json.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 新增时效预警明细
     * 
     * @return
     * @throws Exception
     */
    public String saveRadarWarningReasonLine() throws Exception {
        JSONObject json = new JSONObject();
        try {
            expressOocRadarManager.saveRadarWarningReasonLine(rwrl);
            json.put("msg", "success");
        } catch (Exception e) {
            json.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 根据时效预警类型查找明细
     * 
     * @return
     */
    public String findRadarWarningReasonLine() {
        setTableConfig();
        request.put(JSON, toJson(expressOocRadarManager.findRadarWarningReasonLine(rwr.getId())));
        return JSON;
    }

    /**
     * 删除时效预警类型明细
     * 
     * @return
     * @throws Exception
     */
    public String deleteRadarWarningReasonLine() throws Exception {
        JSONObject json = new JSONObject();
        try {
            expressOocRadarManager.deleteRadarWarningReasonLine(id);
            json.put("msg", "success");
        } catch (Exception e) {
            json.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 验证是否有重复的信息
     * 
     * @return
     * @throws Exception
     */
    public String checkRadarTimeRule() throws Exception {
        JSONObject json = new JSONObject();
        try {
            String result = expressOocRadarManager.checkRadarTimeRule(rc);
            json.put("msg", "success");
            json.put("result", result);
        } catch (Exception e) {
            json.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, json);
        return JSON;
    }


    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public RadarTimeRuleCommand getRc() {
        return rc;
    }

    public void setRc(RadarTimeRuleCommand rc) {
        this.rc = rc;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RadarWarningReasonCommand getRwr() {
        return rwr;
    }

    public void setRwr(RadarWarningReasonCommand rwr) {
        this.rwr = rwr;
    }

    public RadarWarningReasonLineCommand getRwrl() {
        return rwrl;
    }

    public void setRwrl(RadarWarningReasonLineCommand rwrl) {
        this.rwrl = rwrl;
    }

    public RadarTransNoCommand getRdcmd() {
        return rdcmd;
    }

    public void setRdcmd(RadarTransNoCommand rdcmd) {
        this.rdcmd = rdcmd;
    }

    public String getExceprtionStatus() {
        return exceprtionStatus;
    }

    public void setExceprtionStatus(String exceprtionStatus) {
        this.exceprtionStatus = exceprtionStatus;
    }

    public Long getAlge() {
        return alge;
    }

    public void setAlge(Long alge) {
        this.alge = alge;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }


}
