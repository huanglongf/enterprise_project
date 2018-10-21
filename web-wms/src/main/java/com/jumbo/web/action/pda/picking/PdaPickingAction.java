package com.jumbo.web.action.pda.picking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.web.action.pda.base.PdaBaseAction;
import com.jumbo.web.manager.MsgToWcsThread;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.pda.PdaPickingManager;
import com.jumbo.wms.manager.warehouse.AutoOutboundTurnboxManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.mongodb.MongoDBWhPicking;
import com.jumbo.wms.model.mongodb.StaCheckRecord;
import com.jumbo.wms.model.pda.PdaSortPickingCommand;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.RtwDieking;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.web.commond.GoodsCollectionCommand;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

public class PdaPickingAction extends PdaBaseAction {

    private static final long serialVersionUID = -8253500790030675929L;

    @Autowired
    private PdaPickingManager pdaPickingManager;

    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private AutoOutboundTurnboxManager autoOutboundTurnboxManager;

    /*
     * 拣货编码
     */
    private String code;

    private String skuBarCode;

    private Long pickingId;

    private String cCode;

    private Integer status;

    private String barCode;

    private PdaSortPickingCommand pdaSortPickingCommand;

    private String startDateStr;

    private String endDateStr;

    private String pickingStatus;

    private String collectionCode;

    private Boolean force = false;

    private String slipCode;

    private Long staLineId;

    private Boolean isNeedCheck = true;

    private Boolean isTwoPickingOver = false;

    private Boolean isReturnWareHouse = false;

    /**
     * 初始页面跳转
     * 
     * @return
     */
    public String pageRedirect() {
        return SUCCESS;
    }

    public String toCollectionMove() {
        Map<String, String> map = pdaPickingManager.findCollectionQty(getUserDetails().getCurrentOu().getId());
        request.put("collectionQty", map.get("collectionQty"));
        request.put("collectionOverQty", map.get("collectionOverQty"));
        request.put("collectionOverCode", map.get("collectionOverCode"));
        return SUCCESS;
    }

    public String initMongodbWhPickingInfo() throws JSONException {
        JSONObject result = new JSONObject();
        String msg = null;
        boolean me = false;
        try {
            // 退仓拣货
            if (isReturnWareHouse) {
                pdaPickingManager.updatePickingQtyByBarcode(barCode, getUserDetails().getCurrentOu().getId());
                msg = pdaPickingManager.initMongodbWhPickingInfoRTWareHouse(barCode, getUserDetails().getUser().getId(), getUserDetails().getCurrentOu().getId());
                if (StringUtil.isEmpty(msg)) {
                    Boolean isVas = pdaPickingManager.orderIsVas(barCode);
                    result.put("isVas", isVas);
                }
                me = true;
            } else {
                pdaPickingManager.resetPickingQtyByBarcode(barCode);
                msg = pdaPickingManager.initMongodbWhPickingInfo(barCode, getUserDetails().getUser().getId(), getUserDetails().getCurrentOu().getId());
                me = pdaPickingManager.initMongodbWhPickingInfo2(barCode, getUserDetails().getCurrentOu().getId());
            }
            result.put("flag", SUCCESS);
            result.put("msg", msg);
            result.put("me", me);
            request.put(JSON, result);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("flag", ERROR);
            result.put("msg", errorMsg);
            request.put(JSON, result);
            log.error(barCode, e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("flag", ERROR);
            result.put("msg", "");
            request.put(JSON, result);
        }
        return JSON;
    }


    /**
     * 二次分拣明细查询
     * 
     * @return
     * @throws JSONException
     */
    @SuppressWarnings("unchecked")
    public String pdaFindStaLineBySuggestion() throws JSONException {
        JSONObject result = new JSONObject();
        Map<String, Object> map = null;
        try {
            map = wareHouseManagerQuery.findStaLineBySuggestion(null, barCode);
            if (StringUtil.isEmpty((String) map.get("pickingCodeError"))) {
                if (StringUtil.isEmpty((String) map.get("pickingErrorStatus"))) {
                    result.put("staLineCheckList", JsonUtil.collection2json((List<StaCheckRecord>) map.get("staLineCheckList")));
                    result.put("staLineList", JsonUtil.collection2json((List<StaLineCommand>) map.get("staLineList")));
                    result.put("barcodeMap", net.sf.json.JSONObject.fromObject((Map<String, String>) map.get("barcodeMap")));
                    result.put("ruleCodeMap", net.sf.json.JSONObject.fromObject(map.get("ruleCodeMap")).toString());
                    result.put("partCheck", net.sf.json.JSONObject.fromObject(map.get("partCheck")));
                    result.put("pickingData", JsonUtil.obj2json((PickingListCommand) map.get("pickingData")));

                } else {
                    result.put("pickingErrorStatus", (String) map.get("pickingErrorStatus"));
                }

            } else {
                result.put("pickingCodeError", ERROR);
            }
            result.put("result", SUCCESS);

        } catch (Exception e) {
            result.put("msg", ERROR);
            log.error("findStaLineBySuggestion error:", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String pdaTwicePickingByBarcode() throws Exception {
        JSONObject json = new JSONObject();
        try {
            Map<String, Object> map = wareHouseManager.twicePickingByBarcode(pickingId, staLineId, skuBarCode, getUserDetails().getUser().getId(), isNeedCheck);
            json.put("data", map);
            json.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            json.put("result", ERROR);
            json.put("msg", errorMsg);
        } catch (Exception e) {
            json.put("result", ERROR);
            log.error(e.getMessage(), e);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 释放周转箱及人工集货库位
     * 
     * @return
     */
    public String pdaResetBoxAndCollection() {
        try {
            wareHouseManager.resetBoxAndCollection(pickingId, getUserDetails().getUser().getId(),getUserDetails().getCurrentOu().getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return JSON;
    }

    /**
     * 释放周转箱
     * 
     * @return
     * @throws JSONException
     */
    public String pdaResetBox() throws JSONException {
        JSONObject json = new JSONObject();
        try {
            String msg = autoOutboundTurnboxManager.resetTurnoverBoxStatusByBoxCode(cCode, getUserDetails().getUser().getId());
            if (StringUtil.isEmpty(msg)) {
                json.put("flag", SUCCESS);
            } else {
                json.put("msg", msg);
                json.put("flag", ERROR);
            }
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            json.put("flag", ERROR);
            json.put("msg", errorMsg);
            log.error(cCode, e);
        } catch (Exception e) {
            json.put("flag", ERROR);
            log.error(e.getMessage(), e);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 校验拣货编码是否可用
     * 
     * @param code
     * @return
     */
    public String checkPickingCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            String msg = pdaPickingManager.checkPickingCode(code);
            result.put("flag", SUCCESS);
            result.put("msg", msg);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 根据拣货编码获取当前需要拣货的信息
     * 
     * @param code
     * @return
     */
    public String findLocationByPickingCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            MongoDBWhPicking wp = pdaPickingManager.findLocationByPickingCode(barCode, status);
            result.put("flag", SUCCESS);
            if (wp == null) {
                // result.put("wp", null);
            } else {
                result.put("wp", JsonUtil.obj2jsonIncludeAll(wp));
            }
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    public String findBindBoxByPickingCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            List<String> boxList = pdaPickingManager.findBindBoxByPickingCode(code);
            result.put("flag", SUCCESS);
            result.put("boxList", JsonUtil.collection2json(boxList));
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 根据拣货编码获取当前需要拣货的信息
     * 
     * @param code
     * @return
     */
    public String pickingSku() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            String msg = pdaPickingManager.pickingSku(pickingId, cCode, getUserDetails().getUser().getId());
            result.put("flag", SUCCESS);
            result.put("msg", msg);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(pickingId + "", e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 拣货周转箱补绑
     * 
     * @param code
     * @return
     */
    public String bindBoxByBoxCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            String msg = pdaPickingManager.bindBoxByBoxCode(code, cCode);
            result.put("flag", SUCCESS);
            result.put("msg", msg);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(pickingId + "", e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 校验条码是否正确
     * 
     * @param code
     * @return
     */
    public String checkSkuBarCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            MongoDBWhPicking msg = pdaPickingManager.checkSkuBarCode(skuBarCode, pickingId);
            result.put("flag", SUCCESS);
            if (msg == null) {
                result.put("msg", "条码不正确！");
            } else {
                result.put("msg", "");
                Long qty = pdaPickingManager.updateMongoDBWhPickingByPicking(pickingId, cCode, getUserDetails().getUser().getId(), isReturnWareHouse);
                if (qty < 0) {
                    result.put("msg", "插入操作明细失败！");
                }
                result.put("qty", qty);
            }

            request.put(JSON, result);
        } catch (Exception e) {
            log.error(pickingId + "", e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 修改WP状态
     * 
     * @return
     * @throws JSONException
     */
    public String updateWhPickingStatusByPicking() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            pdaPickingManager.updateWhPickingStatusByPicking(pickingId, status, code, pickingStatus);
            result.put("flag", SUCCESS);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(pickingId + "", e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 二级批次拣货完成
     * 
     * @return
     * @throws JSONException
     */
    public String pickingBatchOver() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            // 二级批次拣货完成-退仓拣货
            if (isReturnWareHouse) {
                pdaPickingManager.returnWhousePickingBatchOver(code, getUserDetails().getUser().getId(), getUserDetails().getCurrentOu().getId());
            }
            List<Long> msgIds = pdaPickingManager.pickingBatchOver(code, getUserDetails().getUser().getId());
            try {
                if (msgIds != null && msgIds.size() > 0) {
                    if ("-1".equals(msgIds.get(0).toString())) {
                        result.put("msg", "自动化仓单据必须拣货后才可以设置拣货完成！");
                    } else if ("-2".equals(msgIds.get(0).toString())) {
                        result.put("over", "批次拣货完成！\r\n 无法推荐，请自行分拣和复核！");
                    } else {
                        if (msgIds.size() > 1) {
                            for (int i = 1; i < msgIds.size(); i++) {

                                MsgToWcsThread mt = new MsgToWcsThread();
                                mt.setMsgId(msgIds.get(i));
                                mt.setType(msgIds.get(0).intValue());
                                Thread t = new Thread(mt);
                                t.start();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Interface OShouRongQi container Code: " + code + " error");
                log.error("", e);
            }
            try {
                Boolean b = pdaPickingManager.checkAutoSingle(code);
                result.put("autoSingle", b);
            } catch (Exception e) {}
            result.put("flag", SUCCESS);
            request.put(JSON, result);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("flag", ERROR);
            result.put("msg", errorMsg);
            request.put(JSON, result);
            log.error(code, e);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            result.put("msg", "");
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 根据拣货编码获取未拣货完成的信息
     * 
     * @param code
     * @return
     */
    public String findShortByPickingCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            List<MongoDBWhPicking> wpList = pdaPickingManager.findShortByPickingCode(code);
            List<JSONObject> list = new ArrayList<JSONObject>();
            if (wpList != null && wpList.size() > 0) {
                for (MongoDBWhPicking w : wpList) {
                    list.add(JsonUtil.obj2jsonIncludeAll(w));
                }
            }
            result.put("wp", list);
            result.put("flag", SUCCESS);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 
     * @return
     * @throws JSONException
     */
    public String checkBox() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            String msg = pdaPickingManager.checkBox(code, cCode);
            result.put("flag", SUCCESS);
            result.put("msg", msg);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 
     * @return
     * @throws JSONException
     */
    public String getPickingNum() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            Integer msg = pdaPickingManager.getPickingNum(code, cCode);
            if (msg == null) {
                msg = 0;
            }
            result.put("flag", SUCCESS);
            result.put("msg", msg);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            result.put("msg", 0);
            request.put(JSON, result);
        }
        return JSON;
    }


    /**
     * 
     * @return
     * @throws JSONException
     */
    public String checkCollectionBox() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            String msg = pdaPickingManager.checkCollectionBox(code);
            result.put("flag", SUCCESS);
            result.put("msg", msg);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 周转箱集货
     * 
     * @return
     * @throws JSONException
     */
    public String collectionBox() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            String msg = pdaPickingManager.collectionBox(code, getUserDetails().getCurrentOu().getId(), getUserDetails().getUser().getId());
            result.put("flag", SUCCESS);
            result.put("msg", msg);
            request.put(JSON, result);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("flag", ERROR);
            result.put("msg", errorMsg);
            request.put(JSON, result);
            log.error(code, e);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 移走集货批次
     * 
     * @return
     * @throws JSONException
     */
    public String moveCollectionBox() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            Long ouId = null;
            if (getUserDetails() != null && getUserDetails().getCurrentOu() != null) {
                ouId = getUserDetails().getCurrentOu().getId();
            }
            String msg = pdaPickingManager.moveCollectionBox(barCode, collectionCode, force, ouId, getUserDetails().getUser().getId());

            if (ouId != null) {
                Map<String, String> map = pdaPickingManager.findCollectionQty(getUserDetails().getCurrentOu().getId());
                result.put("collectionQty", map.get("collectionQty"));
                result.put("collectionOverQty", map.get("collectionOverQty"));
                result.put("collectionOverCode", map.get("collectionOverCode"));
            }
            if ("1".equals(msg)) {
                result.put("flag", "1");
            } else {
                result.put("flag", SUCCESS);
            }
            result.put("msg", msg);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 移走集货批次2
     * 
     * @return
     * @throws JSONException
     */
    public String moveCollectionBox2() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            String msg = pdaPickingManager.moveCollectionBox2(barCode, collectionCode, false, getUserDetails().getCurrentOu().getId(), getUserDetails().getUser().getId());

            Map<String, String> map = pdaPickingManager.findCollectionQty(getUserDetails().getCurrentOu().getId());
            result.put("collectionQty", map.get("collectionQty"));
            result.put("collectionOverQty", map.get("collectionOverQty"));
            result.put("collectionOverCode", map.get("collectionOverCode"));
            result.put("flag", SUCCESS);
            result.put("msg", msg);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 查询集货批次状态
     * 
     * @return
     * @throws JSONException
     */
    public String queryCollectionBoxStatus() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            Long ouId = null;
            if (getUserDetails() != null && getUserDetails().getCurrentOu() != null) {
                ouId = getUserDetails().getCurrentOu().getId();
            }
            Map<String, Object> msgMap = pdaPickingManager.queryCollectionBoxStatus(barCode, collectionCode, ouId);

            result.put("flag", SUCCESS);
            result.put("msg", msgMap.get("msg"));
            result.put("wcList", msgMap.get("wcList"));
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }


    /**
     * 集货区域重新推荐
     * 
     * @return
     * @throws JSONException
     */
    public String anewRecommendCollection() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            List<Long> msgIds = pdaPickingManager.anewRecommendCollection(code);
            try {
                if (msgIds != null && msgIds.size() > 0) {

                    if (msgIds.size() > 1) {
                        for (int i = 1; i < msgIds.size(); i++) {

                            MsgToWcsThread mt = new MsgToWcsThread();
                            mt.setMsgId(msgIds.get(i));
                            mt.setType(msgIds.get(0).intValue());
                            Thread t = new Thread(mt);
                            t.start();
                        }
                    }
                }
            } catch (Exception e) {
                log.error("anewRecommendCollection container Code: " + code + " error");
                log.error("", e);
            }
            result.put("flag", SUCCESS);
            request.put(JSON, result);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("flag", ERROR);
            result.put("msg", errorMsg);
            request.put(JSON, result);
            log.error(code, e);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            result.put("msg", "");
            request.put(JSON, result);
        }
        return JSON;
    }


    /**
     * 移走集货批次2
     * 
     * @return
     * @throws JSONException
     */
    public String checkStaAndBatch() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            Boolean msg = pdaPickingManager.checkStaAndBatch(slipCode, code, getUserDetails().getCurrentOu().getId());

            result.put("flag", SUCCESS);
            result.put("msg", msg);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }


    /**
     * 查询已经二次分拣完成的集货库位
     * 
     * @return
     * @throws JSONException
     */
    public String findTwoPickingOver() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            List<GoodsCollectionCommand> gcList = pdaPickingManager.findTwoPickingOver(getUserDetails().getCurrentOu().getId());
            List<JSONObject> list = new ArrayList<JSONObject>();
            if (gcList != null && gcList.size() > 0) {
                for (GoodsCollectionCommand w : gcList) {
                    list.add(JsonUtil.obj2jsonIncludeAll(w));
                }
            }
            result.put("gcList", list);
            result.put("flag", SUCCESS);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 二次分拣完成，释放集货库位
     * 
     * @return
     * @throws JSONException
     */
    public String moveCollectionBoxByPickingOver() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            Long ouId = null;
            if (getUserDetails() != null && getUserDetails().getCurrentOu() != null) {
                ouId = getUserDetails().getCurrentOu().getId();
            }
            String msg = pdaPickingManager.moveCollectionBoxByPickingOver(barCode, collectionCode, ouId, getUserDetails().getUser().getId());

            result.put("flag", SUCCESS);
            result.put("msg", msg);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 
     * @return
     * @throws JSONException
     */
    public String returnWhCheckBox() throws JSONException {
        JSONObject result = new JSONObject();
        try {

            String msg = pdaPickingManager.returnWhCheckBox(code, cCode);
            result.put("flag", SUCCESS);
            result.put("msg", msg);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 退仓批次拣货完成
     * 
     * @return
     * @throws JSONException
     */
    public String returnWhousePickingBatchOver() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            // 二级批次拣货完成-退仓拣货
            if (isReturnWareHouse) {
                pdaPickingManager.returnWhousePickingBatchOver(code, getUserDetails().getUser().getId(), getUserDetails().getCurrentOu().getId());
            }
            result.put("flag", SUCCESS);
            request.put(JSON, result);
        } catch (Exception e) {
            log.error(code, e);
            result.put("flag", ERROR);
            result.put("msg", "");
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 根据拣货批次号获取作业单code
     * 
     * @return
     */
    public String getStaCodeBypbBatchCode() {
        JSONObject ob = new JSONObject();
        RtwDieking rtw = pdaPickingManager.getRtwDiekingByBatchCode(code);
        if (rtw != null) {
            try {
                ob.put("staCode", rtw.getStaCode());
                request.put(JSON, ob);
            } catch (JSONException e) {}
        }
        return JSON;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSkuBarCode() {
        return skuBarCode;
    }

    public void setSkuBarCode(String skuBarCode) {
        this.skuBarCode = skuBarCode;
    }

    public Long getPickingId() {
        return pickingId;
    }

    public void setPickingId(Long pickingId) {
        this.pickingId = pickingId;
    }

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public PdaSortPickingCommand getPdaSortPickingCommand() {
        return pdaSortPickingCommand;
    }

    public void setPdaSortPickingCommand(PdaSortPickingCommand pdaSortPickingCommand) {
        this.pdaSortPickingCommand = pdaSortPickingCommand;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public String getPickingStatus() {
        return pickingStatus;
    }

    public void setPickingStatus(String pickingStatus) {
        this.pickingStatus = pickingStatus;
    }


    public String getCollectionCode() {
        return collectionCode;
    }

    public void setCollectionCode(String collectionCode) {
        this.collectionCode = collectionCode;
    }

    public Boolean getForce() {
        return force;
    }

    public void setForce(Boolean force) {
        this.force = force;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public Long getStaLineId() {
        return staLineId;
    }

    public void setStaLineId(Long staLineId) {
        this.staLineId = staLineId;
    }

    public Boolean getIsNeedCheck() {
        return isNeedCheck;
    }

    public void setIsNeedCheck(Boolean isNeedCheck) {
        this.isNeedCheck = isNeedCheck;
    }

    public Boolean getIsTwoPickingOver() {
        return isTwoPickingOver;
    }

    public Boolean getIsReturnWareHouse() {
        return isReturnWareHouse;
    }

    public void setIsReturnWareHouse(Boolean isReturnWareHouse) {
        this.isReturnWareHouse = isReturnWareHouse;
    }

    public void setIsTwoPickingOver(Boolean isTwoPickingOver) {
        this.isTwoPickingOver = isTwoPickingOver;
    }



}
