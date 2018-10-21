package com.jumbo.web.action.warehouse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.sn.SnManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.PackageInfoCommand;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

public class SalesSnCardCheckAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 4068966380902973999L;

    @Autowired
    private DropDownListManager dropDownListManager;
    @Autowired
    private SnManager snManager;
    @Autowired
    private WareHouseManagerQuery whQuery;
    @Autowired
    private PrintManager printManager;

    private PickingListCommand cmd;

    private List<ChooseOption> plStatus;

    private String plCode;

    private String staCode;

    private Long plid;

    private Long snid;

    private Long staid;

    private String sn;

    public String salesSnCardCheckEntry() {
        plStatus = dropDownListManager.findPickingListStatusForVerify();
        return SUCCESS;
    }

    /**
     * 获取有激活失败的配货清单列表
     * 
     * @return
     * @throws JSONException
     */
    public String searchSnCardCheckList() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(snManager.searchSnCardCheckList(tableConfig.getStart(), tableConfig.getPageSize(), cmd, tableConfig.getSorts(), userDetails.getCurrentOu().getId())));
        return JSON;
    }

    /**
     * 通过批次号/作业单号获取对应失败的卡号
     * 
     * @return
     * @throws JSONException
     */
    public String findSnCardErrorList() throws JSONException {
        setTableConfig();
        request.put(JSON, toJson(snManager.findSnCardErrorList(plCode, staCode)));
        return JSON;
    }

    /**
     * 通过作业单号查找激活失败卡号信息
     * 
     * @return
     * @throws Exception
     */
    public String findSnCardErrorByStaCode() throws Exception {
        JSONObject result = new JSONObject();
        StockTransApplicationCommand sta = snManager.findSnCardErrorByStaCode(staCode, userDetails.getCurrentOu().getId());
        if (sta != null) {
            result.put("sta", JsonUtil.obj2json(sta));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 通过批次ID获取对应批次信息
     * 
     * @return
     * @throws Exception
     */
    public String getSnCardErrorPl() throws Exception {
        JSONObject result = new JSONObject();
        PickingListCommand p = snManager.getSnCardErrorPl(plid);
        if (p != null) {
            result.put("p", JsonUtil.obj2json(p));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 用新卡替换STA上原本激活失败的卡
     * 
     * @return
     * @throws Exception
     */
    public String shiftCardBindingStv() throws Exception {
        JSONObject result = new JSONObject();
        try {
            snManager.shiftCardBindingStv(snid, staid, sn);
            result.put("result", SUCCESS);
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 获取对应作业单信息
     * 
     * @return
     * @throws Exception
     */
    public String findSnCardCheckSta() throws Exception {
        JSONObject result = new JSONObject();
        try {
            List<StaLineCommand> list = snManager.findSnCardCheckSta(staid);
            result.put("sta", JsonUtil.obj2json(list.get(0)));
            result.put("result", SUCCESS);
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 获取订单的拆包运单号
     * 
     * @return
     * @throws Exception
     */
    public String findPackAgeInfoForSta() throws Exception {
        JSONObject result = new JSONObject();
        try {
            List<PackageInfoCommand> p = snManager.findPackAgeInfoForSta(staid);
            result.put("info", JsonUtil.collection2json(p));
            result.put("result", SUCCESS);
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 获取多件作业单的明细
     * 
     * @return
     * @throws Exception
     */
    public String findSnCardGiftStaLineListByStaId() throws Exception {
        JSONObject result = new JSONObject();
        try {
            List<StaLineCommand> sta = whQuery.findGiftStaLineListByStaId(staid);
            result.put("staline", JsonUtil.collection2json(sta));
            result.put("result", SUCCESS);
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }


    public String checkSnCardErrorList() throws Exception {
        JSONObject result = new JSONObject();
        try {
            List<StockTransApplicationCommand> sta = snManager.findSnCardErrorList(plCode, staCode);
            if (sta.size() > 0) {
                result.put("rs", "ok");
            } else {
                result.put("rs", "notok");
            }
            result.put("result", SUCCESS);
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 获取作业单渠道的拆包金额限制
     * 
     * @return
     * @throws Exception
     */
    public String getBiChannelLimitAmount() throws Exception {
        JSONObject result = new JSONObject();
        try {
            BigDecimal b = snManager.getBiChannelLimitAmount(staid);
            if (b != null) {
                result.put("rs", "notnull");
                result.put("la", b);
            } else {
                result.put("rs", "null");
            }
            result.put("result", SUCCESS);
        } catch (Exception e) {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 打印激活失败卡信息
     * 
     * @return
     */
    public String printSnCardErrorList() {
        try {
            List<JasperPrint> jp = new ArrayList<JasperPrint>();
            Long cmpOuid = userDetails.getCurrentOu().getParentUnit().getParentUnit().getId();
            jp = printManager.printSnCardErrorList(staCode, plCode, cmpOuid);
            return printObject(jp);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }


    public PickingListCommand getCmd() {
        return cmd;
    }

    public void setCmd(PickingListCommand cmd) {
        this.cmd = cmd;
    }

    public List<ChooseOption> getPlStatus() {
        return plStatus;
    }

    public void setPlStatus(List<ChooseOption> plStatus) {
        this.plStatus = plStatus;
    }

    public String getPlCode() {
        return plCode;
    }

    public void setPlCode(String plCode) {
        this.plCode = plCode;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public Long getPlid() {
        return plid;
    }

    public void setPlid(Long plid) {
        this.plid = plid;
    }

    public Long getSnid() {
        return snid;
    }

    public void setSnid(Long snid) {
        this.snid = snid;
    }

    public Long getStaid() {
        return staid;
    }

    public void setStaid(Long staid) {
        this.staid = staid;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


}
