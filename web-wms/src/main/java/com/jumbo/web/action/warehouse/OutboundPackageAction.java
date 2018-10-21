/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.web.action.warehouse;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.print.PrintCustomizeManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.model.baseinfo.TransportatorWeigth;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.print.PrintCustomize;
import com.jumbo.wms.model.warehouse.CartonCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StvLineCommand;

import loxia.dao.Pagination;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * 
 * @author jumbo
 * 
 */
public class OutboundPackageAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2027050281779401552L;
    private static Logger log = LoggerFactory.getLogger(OutboundPackageAction.class);
    private Long staid;
    private StockTransApplication sta;

    private StockTransApplicationCommand staCmd;

    private SkuCommand skuCmd;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private PrintManager printManager;
    @Autowired
    private PrintCustomizeManager printCustomizeManager;

    private Long cartonId;

    private String expCode;

    private String maxWeight;

    private String minWeight;

    public String getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(String minWeight) {
        this.minWeight = minWeight;
    }

    private String weightDifferencePercent;

    private TransportatorWeigth transportatorWeigth;

    private String lpCodeWeigthId;


    public String outboundPackageCreateEntry() {
        return SUCCESS;
    }

    public String findTransportatorListByWeight() {
        setTableConfig();
        Pagination<TransportatorWeigth> list =
                wareHouseManager.findTransportatorListByWeight(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), transportatorWeigth == null ? null : transportatorWeigth.getExpCode(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String saveTransportatorWeigth() {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.saveTransportatorWeight(expCode, maxWeight, minWeight, weightDifferencePercent, lpCodeWeigthId, userDetails.getCurrentOu().getId());
            result.put("result", SUCCESS);
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("saveTransportatorWeigth error:" + expCode, e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String updateTransportatorWeigth() {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.updateTransportatorWeigth(expCode, maxWeight, minWeight, weightDifferencePercent, lpCodeWeigthId, userDetails.getCurrentOu().getId());
            result.put("result", SUCCESS);
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("updateTransportatorWeigth error:" + expCode, e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String checkexpCode() {
        JSONObject result = new JSONObject();
        try {
            if (wareHouseManager.checkExpCode(expCode)) {
                result.put("result", SUCCESS);
            } else {
                result.put("result", ERROR);
            }
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("checkexpCode error:" + expCode, e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String getOutboundPackageStaList() {
        setTableConfig();
        if (staCmd != null) {
            staCmd.setStartCreateTime(FormatUtil.getDate(staCmd.getStartCreateTime1()));
            staCmd.setEndCreateTime(FormatUtil.getDate(staCmd.getEndCreateTime1()));
        }

        Pagination<StockTransApplicationCommand> outboundPackageStaList = wareHouseManager.findOutboundPackageStaList(tableConfig.getStart(), tableConfig.getPageSize(), staCmd, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(outboundPackageStaList));
        return JSON;
    }


    public String getoutboundpackageByStalist() {
        setTableConfig();
        if (staCmd != null) {
            staCmd.setStartCreateTime(FormatUtil.getDate(staCmd.getStartCreateTime1()));
            staCmd.setEndCreateTime(FormatUtil.getDate(staCmd.getEndCreateTime1()));
        }

        Pagination<StockTransApplicationCommand> outboundPackageStaList = wareHouseManager.getoutboundpackageByStalist(tableConfig.getStart(), tableConfig.getPageSize(), staCmd, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(outboundPackageStaList));
        return JSON;
    }

    /**
     * 箱号列表数据
     * 
     * @return
     */
    public String trunkDetailInfo() {
        setTableConfig();
        List<CartonCommand> trunkDetailInfoList = wareHouseManager.findTrunkDetailInfoNoPage(staid, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(trunkDetailInfoList));
        return JSON;
    }

    /**
     * 填充计划执行明细数据 KJL 修改添加剩余计划量
     * 
     * @return
     */
    public String planExecuteDetailInfo() {
        setTableConfig();
        Pagination<SkuCommand> planExecuteDetailInfoList = wareHouseManager.findPlanExecuteDetailInfo(tableConfig.getStart(), tableConfig.getPageSize(), staid, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(planExecuteDetailInfoList));
        return JSON;
    }

    /**
     * 已装箱明细数据
     * 
     * @return
     */
    public String completeDetailInfo() {
        setTableConfig();
        Pagination<SkuCommand> completeDetailInfoList = wareHouseManager.findCompleteDetailInfo(tableConfig.getStart(), tableConfig.getPageSize(), staid, skuCmd, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(completeDetailInfoList));
        return JSON;
    }

    /**
     * 差异明细数据
     * 
     * @return
     */
    public String cancelDetailInfo() {
        setTableConfig();
        Pagination<StvLineCommand> cancelDetailInfoList = wareHouseManager.findCancelDetailInfo(tableConfig.getStart(), tableConfig.getPageSize(), staid, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(cancelDetailInfoList));
        return JSON;
    }

    /**
     * 新建箱号
     * 
     * @return
     * @throws JSONException
     */
    public String generateCartonByStaId() throws JSONException {
        JSONObject result = new JSONObject();
        if (staid == null) {
            result.put("result", ERROR);
            result.put("message", "sta id is null ...");
            return ERROR;
        }
        try {
            wareHouseManager.generateCartonByStaId(staid);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (JSONException e) {
            result.put("result", ERROR);
            result.put("message", "invalid.");
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    public String printOutBoundPackageInfo() throws Exception {
        log.debug("Begin print...");
        JasperPrint jpJasperPrint = new JasperPrint();
        try {
            jpJasperPrint = printManager.printOutBoundPackageInfo(staid);
            return printObject(jpJasperPrint);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }

    }

    /**
     * Nike装箱汇总 kai.zhu
     */
    public String printOutBoundPackageMain() throws Exception {
        log.debug("Begin print...");
        JasperPrint jpJasperPrint = new JasperPrint();
        try {
            jpJasperPrint = printManager.printPackingSummaryForNike(staid, cartonId);
            return printObject(jpJasperPrint);
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("printOutBoundPackageMain error:" + staid, e);
            };
            log.error("", e);
            return null;
        }

    }

    /**
     * 通过sta.code查找sta
     * 
     * @return
     */
    public String queryStaByCode() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            StockTransApplication sta = wareHouseManager.queryStaByCode(this.sta.getCode());
            if (sta != null) {
                obj.put("result", SUCCESS);
                obj.put("sta", JsonUtil.obj2json(sta));
                obj.put("staType", sta.getType().getValue());
            }
        } catch (BusinessException e) {
            obj.put("result", ERROR);
        }
        request.put(JSON, obj);
        return JSON;
    }

    /**
     * 汇总打印
     * 
     * @return
     * @throws Exception
     */
    public String printOutBoundPackingIntegrity() throws Exception {
        log.debug("Begin print...");
        JasperPrint jpJasperPrint = new JasperPrint();
        try {
            jpJasperPrint = printManager.printOutBoundPackingIntegrity(staid);
            return printObject(jpJasperPrint);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }

    }

    /**
     * 执行部分出库
     * 
     * @return
     * @throws JSONException
     */
    public String executePartlyOutbound() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManagerExecute.partlyOutbound(staid, this.userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("", e);
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.SYSTEM_ERROR + " : " + e.getMessage()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 单张退仓单物流面单的打印
     * 
     * @return
     */
    public String printSingleVmiDeliveryMode1Out() {
        JasperPrint jp;
        try {
            Long id = wareHouseManager.getVmiReturnStaIdByCarton(sta.getId());
            jp = printManager.printSingleVmiDelivery(id, null, userDetails.getUser().getId());
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

    /**
     * 根据箱号打印单张退仓单物流面单
     * 
     * @return
     */
    public String printSingleVmiDeliveryMode1OutByCarton() {
        JasperPrint jp;
        try {
            Long id = wareHouseManager.getVmiReturnStaIdByCarton(cartonId);
            jp = printManager.printSingleVmiDeliveryByCarton(id, cartonId, null, userDetails.getUser().getId());
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

    /**
     * NIKE CRW pod标签
     * 
     * @return
     */
    public String printNikeCrwPod() {
        JasperPrint jp;
        try {
            jp = printManager.printNikeCrwPod(staid, cartonId);
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

    /**
     * NIKE CRW 箱标签
     * 
     * @return
     */
    public String printNikeCrwLabel() {
        JasperPrint jp;
        try {
            jp = printManager.printNikeCrwLabel(cartonId);
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

    public String printPackingBoxLabel() {
        JasperPrint jp;
        try {
            sta = wareHouseManager.findSta(sta.getId());
            PrintCustomize pc = printCustomizeManager.getPrintCustomizeByOwnerAndType(sta.getOwner(), 11);
            if (pc != null) {
                jp = printManager.printBoxLabel(cartonId, pc);
                return printObject(jp);
            }
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

    public Long getStaid() {
        return staid;
    }

    public void setStaid(Long staid) {
        this.staid = staid;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public SkuCommand getSkuCmd() {
        return skuCmd;
    }

    public void setSkuCmd(SkuCommand skuCmd) {
        this.skuCmd = skuCmd;
    }

    public StockTransApplicationCommand getStaCmd() {
        return staCmd;
    }

    public void setStaCmd(StockTransApplicationCommand staCmd) {
        this.staCmd = staCmd;
    }

    public Long getCartonId() {
        return cartonId;
    }

    public void setCartonId(Long cartonId) {
        this.cartonId = cartonId;
    }

    public String getExpCode() {
        return expCode;
    }

    public void setExpCode(String expCode) {
        this.expCode = expCode;
    }

    public String getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(String maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getWeightDifferencePercent() {
        return weightDifferencePercent;
    }

    public void setWeightDifferencePercent(String weightDifferencePercent) {
        this.weightDifferencePercent = weightDifferencePercent;
    }

    public TransportatorWeigth getTransportatorWeigth() {
        return transportatorWeigth;
    }

    public void setTransportatorWeigth(TransportatorWeigth transportatorWeigth) {
        this.transportatorWeigth = transportatorWeigth;
    }

    public String getLpCodeWeigthId() {
        return lpCodeWeigthId;
    }

    public void setLpCodeWeigthId(String lpCodeWeigthId) {
        this.lpCodeWeigthId = lpCodeWeigthId;
    }


}
