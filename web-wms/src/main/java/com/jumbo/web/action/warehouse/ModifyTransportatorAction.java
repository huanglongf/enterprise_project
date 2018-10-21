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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import loxia.util.CollectionsUtil;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOnLineFactory;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerCancel;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.DeliveryChangeConfigure;
import com.jumbo.wms.model.warehouse.DeliveryChanngeLog;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

/**
 * 
 * @author yitaofen
 * 
 */

public class ModifyTransportatorAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3270477530065973243L;


    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerCancel wareHouseManagerCancel;
    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private TransOlManager transOlManager;
    @Autowired
    private TransOnLineFactory transOnLineFactory;
    @Autowired
    private PrintManager printManager;
    private String trackingNo;

    private String newTrackingNo;

    private String stacode;

    private Long staId;

    private String lpCode;

    private String newLpcode;

    private DeliveryChangeConfigure deliveryChangeConfigure;

    private DeliveryChanngeLog deliveryChanngeLog;

    private String idList;

    private String packId;



    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }

    public String getNewTrackingNo() {
        return newTrackingNo;
    }

    public void setNewTrackingNo(String newTrackingNo) {
        this.newTrackingNo = newTrackingNo;
    }

    public DeliveryChanngeLog getDeliveryChanngeLog() {
        return deliveryChanngeLog;
    }

    public void setDeliveryChanngeLog(DeliveryChanngeLog deliveryChanngeLog) {
        this.deliveryChanngeLog = deliveryChanngeLog;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    private List<PackageInfo> packageInfo;


    public String getNewLpcode() {
        return newLpcode;
    }

    public void setNewLpcode(String newLpcode) {
        this.newLpcode = newLpcode;
    }

    public List<PackageInfo> getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(List<PackageInfo> packageInfo) {
        this.packageInfo = packageInfo;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public String getStacode() {
        return stacode;
    }

    public void setStacode(String stacode) {
        this.stacode = stacode;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }


    public DeliveryChangeConfigure getDeliveryChangeConfigure() {
        return deliveryChangeConfigure;
    }

    public void setDeliveryChangeConfigure(DeliveryChangeConfigure deliveryChangeConfigure) {
        this.deliveryChangeConfigure = deliveryChangeConfigure;
    }

    public String modifyTransportatorEntry() {
        return SUCCESS;
    }

    public String deliveryChangeConfigureEntry() {
        return SUCCESS;
    }

    /**
     * 获取所有物流供应商信息
     * 
     * @return
     * @throws Exception
     */
    public String getTransportator() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.findTransportator(null)));
        return JSON;
    }

    /**
     * 可操作Sta列表
     * 
     * @return
     */
    public String getStaListByTrackingNo() throws JSONException {
        List<StockTransApplicationCommand> stas;
        if (userDetails.getCurrentOu().getOuType().getName().equals("OperationCenter")) {
            List<OperationUnit> ouList = authorizationManager.findOperationUnitList(userDetails.getCurrentOu().getId());
            List<Long> idList = new ArrayList<Long>();
            for (OperationUnit ou : ouList) {
                idList.add(ou.getId());
            }
            stas = wareHouseManager.findStaListByTrackingNo(null, idList, trackingNo, null);
        } else {
            stas = wareHouseManager.findStaListByTrackingNo(userDetails.getCurrentOu().getId(), null, trackingNo, null);
        }
        JSONObject jo = new JSONObject();
        if (stas.size() > 0) {
            StockTransApplicationCommand st = stas.get(0);
            jo.put("refSlipCode", st.getRefSlipCode());
            jo.put("shopId", st.getShopId());
            jo.put("stacode", st.getCode());
            jo.put("staId", st.getId());
        }
        request.put(JSON, jo);
        return JSON;
    }

    /**
     * 根据作业单查询关联快递单号
     * 
     * @param stacode
     * @return
     */
    public String findRelevanceTrackingno() throws JSONException {
        List<String> trackingnos = wareHouseManager.findRelevanceTrackingno(stacode);
        JSONObject jo = new JSONObject();
        jo.put("result", CollectionsUtil.joinList(trackingnos, ","));
        request.put(JSON, jo);
        return JSON;
    }

    /**
     * 根据staId查询staline
     * 
     * @param staId
     * @param sorts
     * @return
     */
    public String findStaLineListByStaId() {
        setTableConfig();
        Pagination<StaLineCommand> staLineList = wareHouseManager.findStaLineCommandListByStaId(tableConfig.getStart(), tableConfig.getPageSize(), staId, tableConfig.getSorts());
        request.put(JSON, toJson(staLineList));
        return JSON;
    }

    public String toTurnLogistics() throws JSONException {
        JSONObject jo = new JSONObject();
        Boolean bl = true;
        for (PackageInfo info : packageInfo) {
            bl = wareHouseManager.checkTrackingNoByLpcode(lpCode, info.getTrackingNo());
            if (!bl) {
                jo.put("tracking", info.getTrackingNo());
            }
        }
        String msg = "";
        if (!bl) {
            msg = "NEW_EXPRESSNO_ERROR";
        } else {
            Long userId = userDetails.getUser().getId();
            try {
                wareHouseManagerCancel.modifyTransport(staId, userId, lpCode, packageInfo, userDetails.getCurrentOu().getId());
                msg = "MODIFY_SUCCESS";
            } catch (BusinessException e) {
                msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            } catch (Exception e) {
                // e.printStackTrace();
                if (log.isErrorEnabled()) {
                    log.error("toTurnLogistics error:", e);
                };
                msg = "MODIFY_FAILED";
            }
        }
        jo.put("msg", msg);
        request.put(JSON, jo);
        return JSON;
    }

    public String increaseDeliverChConfig() throws JSONException {
        if (deliveryChangeConfigure == null) {
            return JSON;
        }
        JSONObject ob = new JSONObject();
        deliveryChangeConfigure.setCreateUser(userDetails.getUser().getUserName());
        deliveryChangeConfigure.setCreateTime(new Date());
        try {
            wareHouseManager.insertDeliveryChConfing(deliveryChangeConfigure);
            ob.put("message", "success");
        } catch (Exception e) {
            ob.put("message", "error");
            log.error(e.getLocalizedMessage());
        }
        request.put(JSON, ob);
        return JSON;
    }

    public String getDeliverChConfigList() {
        setTableConfig();
        List<DeliveryChangeConfigure> DDCList = wareHouseManager.findDCCByLpcode(deliveryChangeConfigure);
        request.put(JSON, toJson(DDCList));
        return JSON;
    }

    public String deleteDeliverChConfig() throws JSONException {
        JSONObject ob = new JSONObject();
        String[] idLsit1 = idList.split(",");
        for (String id : idLsit1) {
            try {
                wareHouseManager.deleteDeliveryChConfing(Long.parseLong(id));
                ob.put("message", "success");
            } catch (NumberFormatException e) {
                ob.put("message", "error");
                log.error(e.getMessage());
            }
        }
        request.put(JSON, ob);
        return JSON;
    }

    /**
     * 电子面单根据快递单号查询物流信息并完成转物流
     * 
     * @return
     * @throws Exception
     */
    public String getDeliveryByTNo() throws Exception {
        JSONObject ob = new JSONObject();

        // 根据trackingNo获取作业单信息
        List<Long> idList = new ArrayList<Long>();
        if (userDetails.getCurrentOu().getOuType().getName().equals("OperationCenter")) {
            List<OperationUnit> ouList = authorizationManager.findOperationUnitList(userDetails.getCurrentOu().getId());
            for (OperationUnit ou : ouList) {
                idList.add(ou.getId());
            }
        }
        try {
            JSONObject msg = transOlManager.changeLpCode(trackingNo, idList, userDetails.getCurrentOu().getId(), userDetails.getUsername());
            request.put(JSON, msg);
        } catch (Exception e1) {
            log.error("change LpCode error:" + trackingNo, e1);
            ob.put("newTrackingNo", "");
            request.put(JSON, ob);
        }
        return JSON;
    }

    /**
     * 根据快递单号获取物流变更日志
     * 
     * @return
     */
    public String getDeliveryChangeLogByTNo() {
        setTableConfig();
        List<DeliveryChanngeLog> DCLList = new ArrayList<DeliveryChanngeLog>();
        DeliveryChanngeLog log = wareHouseManager.findDeliveryChanngeLogByTrNo(trackingNo);
        DCLList.add(log);
        request.put(JSON, toJson(DCLList));
        return JSON;
    }

    /**
     * 手动扫入新面单完成物流变更
     * 
     * @return
     */
    public String modifyDeliveryByTrackingNo() {
        if (newTrackingNo != null) {
            JSONObject ob = new JSONObject();
            List<StockTransApplicationCommand> stas;
            DeliveryChangeConfigure configure = null;
            DeliveryChanngeLog changeLog = new DeliveryChanngeLog();
            // 获取作业单信息
            if (userDetails.getCurrentOu().getOuType().getName().equals("OperationCenter")) {
                List<OperationUnit> ouList = authorizationManager.findOperationUnitList(userDetails.getCurrentOu().getId());
                List<Long> idList = new ArrayList<Long>();
                for (OperationUnit ou : ouList) {
                    idList.add(ou.getId());
                }
                stas = wareHouseManager.findStaListByTrackingNo1(null, idList, trackingNo, null);
            } else {
                stas = wareHouseManager.findStaListByTrackingNo1(userDetails.getCurrentOu().getId(), null, trackingNo, null);
            }
            try {
                StockTransApplicationCommand st = stas.get(0);
                // 根据面单号获取包裹信息
                PackageInfo packageInfo = wareHouseManager.findPackByTrackingNo(trackingNo);
                // 获取物流变更配置信息

                configure = wareHouseManager.findDCCByLpcode1(packageInfo.getLpCode());
                if (configure == null) {
                    ob.put("result", "error");
                    request.put(JSON, ob);
                    return JSON;
                }
                // 仅支持作业类型为销售出库且状态为已转出和已完成的作业单
                if (st.getType().getValue() == 21 && (st.getStatus().getValue() == 4 || st.getStatus().getValue() == 10)) {
                    try {
                        // 更新物流面单号
                        wareHouseManager.updateStaDeliveryById(packageInfo.getStaDeliveryInfo().getId(), newTrackingNo, configure.getNewLpcode());
                    } catch (Exception e) {}
                    try {
                        // 更新包裹物流面单号
                        wareHouseManager.updatePackageInfoById(packageInfo.getId(), newTrackingNo);
                        // 记录变更信息到日志表里
                        changeLog.setCreateTime(new Date());
                        changeLog.setCreateUser(userDetails.getUser().getUserName());
                        changeLog.setStaId(st.getId());
                        changeLog.setLpcode(configure.getLpcode());
                        changeLog.setNewLpcode(configure.getNewLpcode());
                        changeLog.setTrackingNo(trackingNo);
                        changeLog.setNewTrackingNo(newTrackingNo);
                        wareHouseManager.insertDeliverChangeLog(changeLog);
                        ob.put("result", "success");
                        request.put(JSON, ob);
                    } catch (Exception e) {}
                    // 同步物流商发货接口
                    try {
                        // wareHouseManager.synchroDeliveryInfo(configure.getNewLpcode(),
                        // userDetails.getCurrentOu().getId(),st);
                    } catch (Exception e) {}
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return JSON;
    }

    public String printDeliveryInfoByStaId() {
        // 打印电子面单
        JasperPrint jp = new JasperPrint();
        try {
            jp = printManager.printExpressBillByTrankNo1(staId, true, packId, userDetails.getUser().getId(), newLpcode);
            return printObject(jp);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("");
        }
        return null;
    }

}
