package com.jumbo.web.action.pda.receive;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.web.action.pda.base.PdaBaseAction;
import com.jumbo.web.manager.MsgToWcsThread;
import com.jumbo.web.manager.RecommendLocationThread;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.manager.pda.PdaReceiveManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.mongodb.AsnReceive;
import com.jumbo.wms.model.mongodb.OrderLine;
import com.jumbo.wms.model.mongodb.StaCarton;
import com.jumbo.wms.model.mongodb.StaCartonLineSn;
import com.jumbo.wms.model.pda.StaOpDetail;
import com.jumbo.wms.model.warehouse.BiChannelImperfectCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfectLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.RelationNike;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.StockTransApplication;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

public class PdaReceiveAction extends PdaBaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -2895592477382560956L;

    protected static final Logger logger = LoggerFactory.getLogger(PdaReceiveAction.class);


    @Autowired
    private PdaReceiveManager pdaReceiveManager;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private ChannelManager channelManager;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private WareHouseManager warehouseManger;


    private Long skuId;

    private String staCode;

    private String cartonBox;

    private String barCode;

    private Long qty;

    private String skuQty;

    private String dataList;

    private String quantitativeModel;

    private String inventoryStatus;

    private Long cartonId;

    private Long imperfectId;

    private String isPacking;

    private String expirationDate;

    private String productionDate;

    private String sn;

    private String tag;

    private String trackingNo;

    private String lpCode;


    private String locationCode;

    private String checkSkuBarCode;


    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

    public String getCheckSkuBarCode() {
        return checkSkuBarCode;
    }

    public void setCheckSkuBarCode(String checkSkuBarCode) {
        this.checkSkuBarCode = checkSkuBarCode;
    }

    public String menu() {
        return SUCCESS;
    }

    public String pageReceiveByBox() {
        return SUCCESS;
    }

    public String pageReceiveByBoxTag() {
        return SUCCESS;
    }

    public String statusName;

    public String owner;

    public String sendlocationCode;

    public String getSendlocationCode() {
        return sendlocationCode;
    }

    public void setSendlocationCode(String sendlocationCode) {
        this.sendlocationCode = sendlocationCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String pdaOutBoundHand() {
        JSONObject result = new JSONObject();
        String msg = pdaReceiveManager.createOutBoundHost(getUserDetails().getUser().getId(), getUserDetails().getCurrentOu().getId());
        try {
            result.put("msg", msg);
        } catch (Exception e) {
            logger.error("pdaOutBoundHand" + e.toString());
            // e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("pdaOutBoundHand" + e.toString(), e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String pdaOutBoundHandCurrency() {
        JSONObject result = new JSONObject();
        String msg = pdaReceiveManager.createOutBoundHostCurrency(getUserDetails().getUser().getId(), getUserDetails().getCurrentOu().getId());
        try {
            result.put("msg", msg);
        } catch (Exception e) {
            logger.error("pdaOutBoundHandCurrency" + e.toString());

        }
        request.put(JSON, result);
        return JSON;
    }


    public String pdaFindSku() {
        JSONObject result = new JSONObject();
        String msg = pdaReceiveManager.pdaFindSku(checkSkuBarCode, getUserDetails().getCurrentOu().getId(), locationCode, statusName, owner);
        if (null != msg) {
            try {
                result.put("msg", msg);
            } catch (JSONException e) {
                logger.error("pdaFindSku" + e.toString());
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    public String checkSkuBarCode() {
        JSONObject result = new JSONObject();
        String msg = pdaReceiveManager.checkSkuBarCode(checkSkuBarCode, getUserDetails().getCurrentOu().getId(), locationCode);
        try {
            if (msg.contains("-:")) {
                String[] list = msg.split("-:");
                result.put("msg", list[0]);
                result.put("key", list[1]);
                /*
                 * String res =
                 * pdaReceiveManager.isShelfSku(getUserDetails().getCurrentOu().getId(),
                 * checkSkuBarCode); if (null != res && res.equals("ture")) { String expireDate =
                 * pdaReceiveManager.findSkuExpireDate(checkSkuBarCode,
                 * getUserDetails().getCurrentOu().getId(), locationCode); if (null != expireDate) {
                 * result.put("expireDateList", expireDate); } } result.put("isShelf", res);
                 */
            } else {
                result.put("msg", msg);
            }

        } catch (Exception e) {
            logger.error("checkSkuBarCode" + e.toString());
        }
        request.put(JSON, result);
        return JSON;
    }

    public String checkLocation() {
        JSONObject result = new JSONObject();
        Boolean msg = pdaReceiveManager.checkLocation(locationCode, getUserDetails().getCurrentOu().getId());
        try {
            result.put("msg", msg);
        } catch (Exception e) {
            logger.error("checkLocation" + e.toString());
        }
        request.put(JSON, result);
        return JSON;
    }

    public String pdaOutBoundHandNum() {
        JSONObject result = new JSONObject();
        String msg = pdaReceiveManager.pdaOutBoundHandNum(getUserDetails().getUser().getId(), getUserDetails().getCurrentOu().getId());
        String choose = pdaReceiveManager.findChooseOption();
        try {
            result.put("msg", msg);
            result.put("num", choose);
        } catch (Exception e) {
            // logger.error("pdaOutBoundHandNum" + e.toString());
            // e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("pdaOutBoundHandNum" + e.toString(), e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String pdaOutBoundHandCurrencyNum() {
        JSONObject result = new JSONObject();
        String msg = pdaReceiveManager.pdaOutBoundHandCurrencyNum(getUserDetails().getUser().getId(), getUserDetails().getCurrentOu().getId());
        String choose = pdaReceiveManager.findChooseOption();
        try {
            result.put("msg", msg);
            result.put("num", choose);
        } catch (Exception e) {
            logger.error("pdaOutBoundHandCurrencyNum" + e.toString());
        }
        request.put(JSON, result);
        return JSON;
    }


    public String checkTrackingNo() throws JSONException {
        JSONObject result = new JSONObject();
        String msg = null;
        try {
            Boolean b = pdaReceiveManager.checkAdTrackingNo(trackingNo);
            if (b) {// Ad
                msg = pdaReceiveManager.checkTrackingNo2(trackingNo, getUserDetails().getCurrentOu().getId(), getUserDetails().getUser().getId());
            } else {// 非ad
                msg = pdaReceiveManager.checkTrackingNo(trackingNo, getUserDetails().getCurrentOu().getId(), getUserDetails().getUser().getId());
            }
            result.put("msg", msg);
        } catch (BusinessException e) {// 抛出业务异常
            if (e.getErrorCode() == -1) {
                result.put("msg", e.getMessage());
            } else {
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                result.put("msg", sb.toString());
            }
        } catch (Exception e) {
            // logger.error("checkTrackingNo" + trackingNo + e.toString());
            // e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("checkTrackingNo" + trackingNo + e.toString(), e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String checkTrackingNoCurrency() {
        JSONObject result = new JSONObject();
        try {
            String msg = pdaReceiveManager.checkTrackingNoCurrency(lpCode, trackingNo, getUserDetails().getCurrentOu().getId(), getUserDetails().getUser().getId());
            result.put("msg", msg);
        } catch (Exception e) {
            logger.error("checkTrackingNoCurrency" + trackingNo + e.toString());
        }
        request.put(JSON, result);
        return JSON;
    }

    public String pdaCheckSendlocationCode() {
        JSONObject result = new JSONObject();
        String msg = pdaReceiveManager.pdaCheckSendlocationCode(sendlocationCode, getUserDetails().getCurrentOu().getId(), checkSkuBarCode, locationCode, statusName, owner);
        try {
            result.put("msg", msg);
        } catch (JSONException e) {
            logger.error("pdaCheckSendlocationCode" + sendlocationCode + e.toString());
        }
        request.put(JSON, result);
        return JSON;
    }


    public String pdaExecuteTransitInner() {
        JSONObject result = new JSONObject();
        String msg =
                pdaReceiveManager.pdaExecuteTransitInner(sendlocationCode, getUserDetails().getCurrentOu().getId(), checkSkuBarCode, locationCode, statusName, owner, skuQty, getUserDetails().getUser().getId(), getUserDetails().getUser().getUserName());
        try {
            result.put("msg", msg);
        } catch (JSONException e) {
            logger.error("pdaExecuteTransitInner" + checkSkuBarCode + sendlocationCode + e.toString());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 按箱收货 扫标签
     * 
     * @return
     * @throws JSONException
     */

    public String checkTag() throws JSONException {
        Long carId = null;
        JSONObject result = new JSONObject();
        result.put("flag", SUCCESS);
        try {
            carId = pdaReceiveManager.checkTag(staCode, tag, getUserDetails().getCurrentOu());
        } catch (BusinessException e) {
            // e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("checkTag BusinessException" + staCode, e);
            };
            result.put("flag", ERROR);
            result.put("msg", e.getMessage());
        } catch (Exception e) {
            result.put("flag", ERROR);
            result.put("msg", "系统异常");
            // e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("checkTag Exception" + staCode, e);
            };
        }
        if (carId != null) {
            Long msgId = pdaReceiveManager.sendMsgToWcs(carId);
            if (msgId != null) {
                try {
                    if (msgId != null) {
                        MsgToWcsThread wt = new MsgToWcsThread();
                        wt.setMsgId(msgId);
                        wt.setType(WcsInterfaceType.SShouRongQi.getValue());
                        new Thread(wt).start();
                        result.put("flag", SUCCESS);
                    }
                } catch (Exception e) {
                    // e.printStackTrace();
                    if (logger.isErrorEnabled()) {
                        logger.error("MsgToWcsThread Exception" + staCode, e);
                    };
                    result.put("errorMsg", "");
                }
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    // pdaReceiveByBoxCheckSkus
    public String pdaReceiveByBoxCheckSkus() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("msg2", "");
        // 是否是SN商品or效期商品
        Boolean flasg = null;
        try {
            flasg = pdaReceiveManager.findSkuByStaLine(staCode, getUserDetails().getCurrentOu().getId());
        } catch (BusinessException e) {
            result.put("msg2", e.getMessage());
            flasg = false;
        }
        if (!flasg) {
            result.put("flag", "1");
        } else {
            String msg = pdaReceiveManager.verifySkuThreeDimensional(null, staCode, getUserDetails().getCurrentOu().getId(), true);
            if(!StringUtil.isEmpty(msg)) {
                result.put("flag", "3");
            }else {
                
                result.put("flag", "2");
            }
        }
        request.put(JSON, result);
        return JSON;
    }



    public String pdaReceiveByBox() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("msg2", "");
        logger.error("pdaReceiveByBox--start--" + staCode + "-------" + inventoryStatus);

        // 是否是SN商品or效期商品
        Boolean flasg = null;
        try {
            flasg = pdaReceiveManager.findSkuByStaLine(staCode, getUserDetails().getCurrentOu().getId());
        } catch (BusinessException e) {
            result.put("msg2", e.getMessage());
            flasg = false;
        }
        if (!flasg) {
            result.put("flag", INPUT);
        } else {

            String flag1 = pdaReceiveManager.isGroupSta(staCode, getUserDetails().getCurrentOu().getId());
            if ("success".equals(flag1)) {
                String msg = "";
                try {
                    msg = pdaReceiveManager.pdaReceiveByBox(staCode, getUserDetails().getUser(), getUserDetails().getCurrentOu(), tag, inventoryStatus);
                } catch (BusinessException e) {
                    result.put("msg2", e.getMessage());
                } catch (Exception e) {
                    // logger.error("pdaReceiveByBox报错" + staCode + e.getMessage());
                    // e.printStackTrace();
                    if (logger.isErrorEnabled()) {
                        logger.error("pdaReceiveByBox报错" + staCode + e.getMessage(), e);
                    };
                }
                logger.error("pdaReceiveByBox--end" + staCode);
                if ("success".equals(msg)) {
                    result.put("flag", msg);
                    logger.error("saveCartonAndLine --end" + staCode);
                    List<StaCarton> staCartonList = pdaReceiveManager.staCartonList(staCode, getUserDetails().getCurrentOu());
                    logger.error("推荐开始");
                    try {
                        RecommendLocationThread rlt = new RecommendLocationThread();
                        rlt.setStaCartonList(staCartonList);
                        rlt.setOuId(getUserDetails().getCurrentOu().getId());
                        rlt.setFlag("pdaReceiveByBox");
                        new Thread(rlt).start();
                    } catch (Exception e) {
                        logger.error("推荐库位异常：", e);
                    }
                    logger.error("推荐结束");
                    try {
                        if (null != staCartonList && staCartonList.size() > 0) {
                            pdaReceiveManager.updateStaCartonStatus(staCartonList);
                        }
                        pdaReceiveManager.insertStaOpDetailLog(staCode, getUserDetails().getCurrentOu().getId());
                    } catch (Exception e) {
                        logger.error("insertStaOpDetailLog报错" + staCode + e.getMessage());
                        result.put("flag", "err");
                    }
                } else {
                    result.put("flag", msg);
                }
            } else {
                result.put("flag", "error4");
            }

        }

        request.put(JSON, result);
        return JSON;
    }

    /**
     * Mongodb 缓存数据
     * 
     * @param staCode
     * @return
     */
    public String initMongodbWhReceiveInfo() throws JSONException {
        JSONObject result = new JSONObject();
        String flag = pdaReceiveManager.isGroupSta1(staCode, getUserDetails().getCurrentOu().getId());
        if ("false".equals(flag)) {
            result.put("flag", INPUT);
        } else {
            try {
                String msg = pdaReceiveManager.initMongodbWhReceiveInfo(staCode, getUserDetails().getCurrentOu());
                if (null != msg && msg.equals("success")) {
                    result.put("flag", SUCCESS);
                    StockTransApplication sto = pdaReceiveManager.findSta(staCode, getUserDetails().getCurrentOu());
                    result.put("sta", sto.getCode());
                } else {
                    result.put("flag", msg);
                }
            } catch (BusinessException e) {
                logger.error("initMongodbWhReceiveInfo" + e.getMessage());
                result.put("flag", ERROR);
                result.put("msg", "单据" + staCode + "未找到");
            }
        }

        request.put(JSON, result);
        return JSON;
    }

    /**
     * 获取所有物流供应商信息
     * 
     * @return
     * @throws Exception
     */
    public String getTransportatorPda() throws Exception {
        request.put(JSON, JsonUtil.collection2json(chooseOptionManager.findTransportator(null)));
        return JSON;
    }

    /**
     * 验证容器号
     * 
     * @param staCode
     * @param cartonBox
     * @return
     */
    public String initMongodbWhcartonBox() {
        JSONObject result = new JSONObject();
        try {
            String msg = pdaReceiveManager.initMongodbWhcartonBox(cartonBox, staCode, getUserDetails().getCurrentOu().getId());// barCode,
                                                                                                                               // isPacking,
                                                                                                                               // expirationDate,
            // productionDate
            result.put("flag", msg);
            request.put(JSON, result);

        } catch (Exception e) {
            logger.error("initMongodbWhcartonBox" + staCode + cartonBox + e.toString());
        }
        return JSON;
    }

    public String initMongodbWhcartonBox1() {
        JSONObject result = new JSONObject();
        try {
            String msg = pdaReceiveManager.initMongodbWhcartonBox(cartonBox, staCode, getUserDetails().getCurrentOu().getId());// barCode,
            result.put("flag", msg);
            if (null != msg && !"".equals(msg) && ("success".equals(msg) || "容器已被占用".equals(msg))) {

                Long skuId = null;
                AsnReceive asnReceive = pdaReceiveManager.findBySkuId(staCode);
                List<OrderLine> list = asnReceive.getOrderLine();
                Set<String> set = new HashSet<String>();
                loop: for (OrderLine skuIdList : list) {
                    set = skuIdList.getSkubarcode();
                    for (String s : set) {
                        if (s.equals(barCode)) {
                            skuId = skuIdList.getSkuId();
                            break loop;
                        }
                    }
                }

                StaOpDetail staOpDetail1 = pdaReceiveManager.findBySku(staCode, cartonBox, getUserDetails().getUser().getId(), getUserDetails().getCurrentOu().getId());
                if (null != staOpDetail1 && null != staOpDetail1.getCartonCode()) {
                    if (staOpDetail1.getSkuId().equals(skuId)) {
                        result.put("flag", SUCCESS);
                    } else {
                        result.put("flag", "拆箱的情况下不容许混SKU");
                    }
                } else {
                    StaOpDetail staOpDetail = pdaReceiveManager.findByNo(staCode, skuId, getUserDetails().getUser().getId(), getUserDetails().getCurrentOu().getId());
                    if (null != staOpDetail && null != staOpDetail.getCartonCode()) {
                        result.put("flag", SUCCESS);
                    }
                }
            }

            request.put(JSON, result);

        } catch (Exception e) {
            logger.error("initMongodbWhcartonBox" + staCode + cartonBox + e.toString());
        }
        return JSON;
    }

    /**
     * 验证sku
     * 
     * @param staCode
     * @param cartonBox
     * @return
     * @throws JSONException
     */
    public String verifySku() throws JSONException {
        JSONObject result = new JSONObject();
        Boolean msg = pdaReceiveManager.verifySku(staCode, barCode, qty);
        try {
            if (msg) {
                String msg2 = pdaReceiveManager.verifySkuThreeDimensionalBySku(staCode, getUserDetails().getCurrentOu().getId(), barCode);
                if (StringUtil.isEmpty(msg2)) {
                    result.put("flag", SUCCESS);
                }else{
                    result.put("flag", "SkuThreeDimensional");
                }
            } else {
                result.put("flag", NONE);
            }
        } catch (BusinessException e1) {
            logger.error("verifySku" + staCode + barCode + e1.toString());
            result.put("flag", ERROR);
            result.put("msg", "单据" + staCode + "未找到");
        } catch (JSONException e) {
            logger.error("verifySku111" + staCode + barCode + e.toString());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询sku
     * 
     * @param staCode
     * @param barCode
     * @return
     * @throws JSONException
     */
    public String findPDASkuByBarCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Sku sku = pdaReceiveManager.findPDASkuByBarCode(staCode, barCode);
            result.put("flag", JsonUtil.obj2jsonIncludeAll(sku));
        } catch (BusinessException e) {
            logger.error("findPDASkuByBarCode" + staCode + barCode + e.toString());

        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 更改剩余执行量
     * 
     * @param staCode
     * @param barCode
     * @param qty
     * @return
     * @throws JSONException
     */
    public String updateMogodbRestQty() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            pdaReceiveManager.updateMogodbRestQty(barCode, qty, staCode);
            result.put("flag", SUCCESS);
        } catch (Exception e) {
            logger.error("updateMogodbRestQty" + barCode + qty + staCode + e.toString());
            result.put("flag", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询剩余执行量
     * 
     * @param staCode
     * @param barCode
     * @param qty
     * @return
     * @throws JSONException
     */
    public String findMongodbByRestQty() {
        JSONObject result = new JSONObject();
        try {

            Boolean flag = pdaReceiveManager.findMongodbByRestQty(staCode);
            if (flag) {
                result.put("flag", SUCCESS); // 未完成
            } else {
                result.put("flag", NONE);
            }
        } catch (Exception e) {
            logger.error("findMongodbByRestQty" + staCode + e.toString());
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 查询剩余执行量
     * 
     * @param staCode
     * @param barCode
     * @param qty barcode
     * @return
     * @throws JSONException
     */
    public String findMongodbByRestQtyAndBarCode() {
        JSONObject result = new JSONObject();
        try {

            Boolean flag = pdaReceiveManager.findMongodbByRestQtyAndBarCode(staCode, barCode);
            if (flag) {
                result.put("flag", SUCCESS); // 未完成
            } else {
                result.put("flag", NONE);
            }
        } catch (Exception e) {
            logger.error("findMongodbByRestQty" + staCode + barCode + e.toString());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 收货完成
     * 
     * @param staCode
     * @param barCode
     * @param qty
     * @return
     * @throws JSONException
     */
    public String asnOver() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            logger.error("asnOver start--" + staCode);
            pdaReceiveManager.asnOver(staCode, cartonBox, inventoryStatus, getUserDetails().getCurrentOu().getId(), dataList, quantitativeModel, getUserDetails().getUser(), getUserDetails().getCurrentOu());
        } catch (Exception e) {
            logger.error("asnOver" + e.getMessage());
        }
        Boolean flase = pdaReceiveManager.findMongodbByRestQty(staCode);
        if (!flase) { // 生成3张表数据
            try {
                logger.error("saveCartonAndLine start--" + staCode);
                pdaReceiveManager.saveCartonAndLine(staCode, getUserDetails().getCurrentOu().getId(), inventoryStatus);
                result.put("flag", SUCCESS);
            } catch (Exception e) {
                logger.error("saveCartonAndLine--" + staCode + e.getMessage());
            }
            List<StaCarton> staCartonList = pdaReceiveManager.staCartonList(staCode, getUserDetails().getCurrentOu());
            logger.error("推荐  start--" + staCode);
            try {
                RecommendLocationThread rlt = new RecommendLocationThread();
                rlt.setStaCartonList(staCartonList);
                rlt.setOuId(getUserDetails().getCurrentOu().getId());
                rlt.setFlag("asnOver");
                rlt.setInventoryStatus(inventoryStatus);
                rlt.setStaCode(staCode);
                new Thread(rlt).start();
            } catch (Exception e) {
                logger.error("推荐库位异常：", e);
            }
            logger.error("insertStaOpDetailLog" + staCode);
            try {
                if (null != staCartonList && staCartonList.size() > 0) {
                    pdaReceiveManager.updateStaCartonStatus(staCartonList);
                }
                pdaReceiveManager.insertStaOpDetailLog(staCode, getUserDetails().getCurrentOu().getId());
                pdaReceiveManager.mongodbDeleteByStaCode(staCode);
            } catch (Exception e) {
                logger.error("insertStaOpDetailLog---" + staCode + e.getMessage());
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    public String cartonASNOk() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            logger.error("asnOver start--" + staCode);

            // pdaReceiveManager.updateContainerStatus(cartonBox);
            pdaReceiveManager.asnOver(staCode, cartonBox, inventoryStatus, getUserDetails().getCurrentOu().getId(), dataList, quantitativeModel, getUserDetails().getUser(), getUserDetails().getCurrentOu());
            // 生成3张表数据
            logger.error("saveCartonAndLine--" + staCode);
            pdaReceiveManager.saveCartonAndLine(staCode, getUserDetails().getCurrentOu().getId(), inventoryStatus);
            // pdaReceiveManager.insertStaOpDetailLog(staCode,
            // getUserDetails().getCurrentOu().getId());

            Boolean flase = pdaReceiveManager.findMongodbByRestQty(staCode);
            if (!flase) {
                logger.error("deleteMongodbASNdata--" + staCode);
                pdaReceiveManager.mongodbDeleteByStaCode(staCode);
            }
            logger.error("asnOver end--" + staCode);
            List<StaCarton> staCartonList = pdaReceiveManager.staCartonList(staCode, getUserDetails().getCurrentOu());
            try {
                RecommendLocationThread rlt = new RecommendLocationThread();
                rlt.setStaCartonList(staCartonList);
                rlt.setOuId(getUserDetails().getCurrentOu().getId());
                rlt.setFlag("cartonASNOk");
                rlt.setInventoryStatus(inventoryStatus);
                rlt.setStaCode(staCode);
                new Thread(rlt).start();
            } catch (Exception e) {
                logger.error("推荐库位异常：", e);
            }
            if (null != staCartonList && staCartonList.size() > 0) {
                pdaReceiveManager.updateStaCartonStatus(staCartonList);
            }

        } catch (Exception e) {
            logger.error("insertStaOpDetailLog--" + staCode + e.getMessage());
            result.put("flag", ERROR);
        }
        logger.error("insertStaOpDetailLog--" + staCode);
        pdaReceiveManager.insertStaOpDetailLog(staCode, getUserDetails().getCurrentOu().getId());
        request.put(JSON, result);
        return JSON;
    }

    public String mongodbfindQtyByCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Long qty = pdaReceiveManager.mongodbfindQtyByCode(staCode, barCode);
            result.put("flag", qty);
        } catch (Exception e) {
            result.put("flag", ERROR);
            // e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("mongodbfindQtyByCode" + staCode, e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * PDA收货，货箱弹出口通知WCS
     * 
     * @return
     * @throws JSONException
     * @throws CloneNotSupportedException
     */
    public String sendMsgToWcs() throws JSONException, CloneNotSupportedException {
        JSONObject result = new JSONObject();
        try {
            Long msgId = pdaReceiveManager.sendMsgToWcs(cartonId);
            if (msgId != null) {

                try {
                    if (msgId != null) {
                        MsgToWcsThread wt = new MsgToWcsThread();
                        wt.setMsgId(msgId);
                        wt.setType(WcsInterfaceType.SShouRongQi.getValue());
                        new Thread(wt).start();
                    }
                } catch (Exception e) {
                    result.put("errorMsg", "");
                    log.error("auto inbound error:" + e.getMessage());
                }
            } else {
                result.put("errorMsg", "");
            }
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            StringBuffer sb = new StringBuffer();
            if (e.getErrorCode() > 0) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            }
            BusinessException be = e;
            while ((be = be.getLinkedException()) != null) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
            }
            log.error(e.getMessage());
            result.put("message", sb.toString());
        }
        request.put(JSON, result);
        return JSON;
    }


    public String getImperfectByChannel() throws JSONException {
        JSONObject result = new JSONObject();
        BiChannel channel = pdaReceiveManager.findDefectType(staCode);
        List<BiChannelImperfectCommand> biChannelImperfectCommands = channelManager.findImperfect(channel.getId());
        if (null != biChannelImperfectCommands && biChannelImperfectCommands.size() > 0) {
            result.put("result", SUCCESS);
            result.put("object", JsonUtil.collection2json(biChannelImperfectCommands));
            request.put(JSON, result);
        } else {
            result.put("result", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    public String getImperfectByOuId() throws JSONException {
        JSONObject result = new JSONObject();
        List<BiChannelImperfectCommand> list = baseinfoManager.findWarehouseImperfectl(getUserDetails().getCurrentOu().getId());
        if (null != list && list.size() > 0) {
            result.put("result", SUCCESS);
            result.put("object", JsonUtil.collection2json(list));
            request.put(JSON, result);
        } else {
            result.put("result", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    public String getByImperfectLine() throws JSONException {
        JSONObject result = new JSONObject();
        List<BiChannelImperfectLineCommand> list = channelManager.findImperfectLine(imperfectId);
        if (null != list && list.size() > 0) {
            result.put("result", SUCCESS);
            result.put("object", JsonUtil.collection2json(list));
            request.put(JSON, result);
        } else {
            result.put("result", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }


    public String findBySn() throws JSONException {
        JSONObject result = new JSONObject();
        SkuSn skuSn = pdaReceiveManager.findSnByOuId(sn, getUserDetails().getCurrentOu().getId());
        if (null != skuSn && null != skuSn.getSn() && !"".equals(skuSn.getSn())) {
            result.put("result", ERROR);
        } else {
            StaCartonLineSn sns = pdaReceiveManager.findSnbysn(sn);
            if (null != sns && null != sns.getSn() && !"".equals(sns.getSn())) {
                result.put("result", ERROR);
            } else {
                StaOpDetail staOpDetail = pdaReceiveManager.findOpDetailBySn(sn);
                if (null != staOpDetail && null != staOpDetail.getSn() && !"".equals(staOpDetail.getSn())) {
                    result.put("result", ERROR);
                } else {
                    result.put("result", SUCCESS);
                }
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    public String packingfindbyNO() throws JSONException {
        JSONObject result = new JSONObject();
        Long skuId = null;
        AsnReceive asnReceive = pdaReceiveManager.findBySkuId(staCode);
        List<OrderLine> list = asnReceive.getOrderLine();
        Set<String> set = new HashSet<String>();
        loop: for (OrderLine skuIdList : list) {
            set = skuIdList.getSkubarcode();
            for (String s : set) {
                if (s.equals(barCode)) {
                    skuId = skuIdList.getSkuId();
                    break loop;
                }
            }
        }
        StaOpDetail staOpDetail = pdaReceiveManager.findByNo(staCode, skuId, getUserDetails().getUser().getId(), getUserDetails().getCurrentOu().getId());
        if (null != staOpDetail && null != staOpDetail.getCartonCode()) {
            result.put("flag", JsonUtil.obj2jsonIncludeAll(staOpDetail));
        } else {
            result.put("flag", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据库位编码/商品条码获取库存
     * 
     * @return
     * @throws JSONException
     */
    public String getInventoryQty() throws JSONException {
        JSONObject result = new JSONObject();
        List<InventoryCommand> list = pdaReceiveManager.getInventoryByBarCodeOrLocation(barCode, locationCode, getUserDetails().getCurrentOu().getId());
        result.put("result", JsonUtil.collection2json(list));
        request.put(JSON, result);
        return JSON;
    }
    
    /**
     * 按箱收货-nike
     * @return
     * @throws JSONException
     */
    public String pdaReceiveByBoxForNike() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("msg2", "");
        logger.error("pdaReceiveByBoxForNike--start--" + staCode + "---" + inventoryStatus);

        // 是否是SN商品or效期商品
        Boolean flasg = null;
        try {
            RelationNike nike = warehouseManger.getSysCaseNumber(staCode, getUserDetails().getCurrentOu().getId());
            if(nike!=null){
                flasg = pdaReceiveManager.findSkuByStaLine(nike.getSysPid(), getUserDetails().getCurrentOu().getId());
                staCode=nike.getSysPid();
            }else{
                flasg = false; 
            }
        } catch (BusinessException e) {
            result.put("msg2", e.getMessage());
            flasg = false;
        }
        if (!flasg) {
            result.put("flag", INPUT);
        } else {

            String flag1 = pdaReceiveManager.isGroupSta(staCode, getUserDetails().getCurrentOu().getId());
            if ("success".equals(flag1)) {
                String msg = "";
                try {
                    msg = pdaReceiveManager.pdaReceiveByBox(staCode, getUserDetails().getUser(), getUserDetails().getCurrentOu(), tag, inventoryStatus);
                } catch (BusinessException e) {
                    result.put("msg2", e.getMessage());
                } catch (Exception e) {
                    // logger.error("pdaReceiveByBox报错" + staCode + e.getMessage());
                    // e.printStackTrace();
                    if (logger.isErrorEnabled()) {
                        logger.error("pdaReceiveByBoxForNike报错" + staCode + e.getMessage(), e);
                    };
                }
                logger.error("pdaReceiveByBoxForNike--end" + staCode);
                if ("success".equals(msg)) {
                    result.put("flag", msg);
                    logger.error("saveCartonAndLine --end" + staCode);
                    List<StaCarton> staCartonList = pdaReceiveManager.staCartonList(staCode, getUserDetails().getCurrentOu());
                    logger.error("推荐开始");
                    try {
                        RecommendLocationThread rlt = new RecommendLocationThread();
                        rlt.setStaCartonList(staCartonList);
                        rlt.setOuId(getUserDetails().getCurrentOu().getId());
                        rlt.setFlag("pdaReceiveByBox");
                        new Thread(rlt).start();
                    } catch (Exception e) {
                        logger.error("推荐库位异常：", e);
                    }
                    logger.error("推荐结束");
                    try {
                        if (null != staCartonList && staCartonList.size() > 0) {
                            pdaReceiveManager.updateStaCartonStatus(staCartonList);
                        }
                        pdaReceiveManager.insertStaOpDetailLog(staCode, getUserDetails().getCurrentOu().getId());
                    } catch (Exception e) {
                        logger.error("insertStaOpDetailLog报错" + staCode + e.getMessage());
                        result.put("flag", "err");
                    }
                } else {
                    result.put("flag", msg);
                }
            } else {
                result.put("flag", "error4");
            }

        }

        request.put(JSON, result);
        return JSON;
    }
    
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getCartonBox() {
        return cartonBox;
    }

    public void setCartonBox(String cartonBox) {
        this.cartonBox = cartonBox;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public PdaReceiveManager getPdaReceiveManager() {
        return pdaReceiveManager;
    }

    public void setPdaReceiveManager(PdaReceiveManager pdaReceiveManager) {
        this.pdaReceiveManager = pdaReceiveManager;
    }

    public String getDataList() {
        return dataList;
    }

    public void setDataList(String dataList) {
        this.dataList = dataList;
    }

    public String getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(String inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    public String getQuantitativeModel() {
        return quantitativeModel;
    }

    public void setQuantitativeModel(String quantitativeModel) {
        this.quantitativeModel = quantitativeModel;
    }

    public Long getCartonId() {
        return cartonId;
    }

    public void setCartonId(Long cartonId) {
        this.cartonId = cartonId;
    }

    public Long getImperfectId() {
        return imperfectId;
    }

    public void setImperfectId(Long imperfectId) {
        this.imperfectId = imperfectId;
    }

    public String getIsPacking() {
        return isPacking;
    }

    public void setIsPacking(String isPacking) {
        this.isPacking = isPacking;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(String skuQty) {
        this.skuQty = skuQty;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }


}
