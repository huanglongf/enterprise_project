package com.jumbo.web.action.pda.shelves;

import java.util.Map;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.pda.base.PdaBaseAction;
import com.jumbo.web.manager.MsgToWcsThread;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.pda.PdaShelvesManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.warehouse.RelationNike;

public class PdaShelvesAction extends PdaBaseAction {

    private static final long serialVersionUID = -8253500790030675929L;

    protected static final Logger logger = LoggerFactory.getLogger(PdaShelvesAction.class);


    @Autowired
    private PdaShelvesManager pdaShelvesManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    /*
     * 箱号
     */
    private String code;

    /**
     * 商品barCode
     */
    private String skuBarcode;

    /*
     * 库位code
     */
    private String locationCode;

    private Long num;

    private String eDate;// 失效日期

    private String sDate;// 生产日期

    private Boolean isNike=false;// 是否是nike主键上架


    public String pdaTransitInnerIndex() {
        return SUCCESS;
    }


    public String pdaTransitInnerIndexcheckLoc() {
        JSONObject result = new JSONObject();
        String msg = pdaShelvesManager.checkLocIsInv(getUserDetails().getCurrentOu().getId(), locationCode);
        try {
            result.put("msg", msg);
        } catch (JSONException e) {}
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 初始页面跳转良品逐渐
     * 
     * @return
     */
    public String pageRedirectGood() {
        return SUCCESS;
    }

    /**
     * 初始页面跳转良品批量
     * 
     * @return
     */
    public String pageRedirectGoodBatch() {
        return SUCCESS;
    }

    /**
     * 初始页面跳转良品逐渐(人)
     * 
     * @return
     */
    public String pageRedirectGoodRen() {
        return SUCCESS;
    }

    /**
     * 初始页面跳转良品批量(人)
     * 
     * @return
     */
    public String pageRedirectGoodBatchRen() {
        return SUCCESS;
    }

    /**
     * 初始页面跳转残次品上架
     * 
     * @return
     */
    public String pageRedirectIncomplete() {
        return SUCCESS;
    }

    /**
     * locationShelvesShou 手动上架
     */
    public String locationShelvesShou() throws Exception {
        logger.error("locationShelvesShou_start:" + skuBarcode + "," + code + "," + locationCode);
        JSONObject result = new JSONObject();
        try {
            if(isNike){
                RelationNike nike = wareHouseManager.getSysCaseNumber(code, getUserDetails().getCurrentOu().getId());
                if(nike!=null){
                    Map<String, Object> map = pdaShelvesManager.locationShelvesShou(num, skuBarcode, nike.getSysPid(), locationCode, eDate, getUserDetails().getCurrentOu(), getUserDetails().getUser(), null);
                    result.put("flag", SUCCESS);
                    result.put("brand", map.get("brand"));
                }else{
                    result.put("flag", ERROR); 
                    result.put("msg", "找不到系统箱号");
                }
            }else{
            Map<String, Object> map = pdaShelvesManager.locationShelvesShou(num, skuBarcode, code, locationCode, eDate, getUserDetails().getCurrentOu(), getUserDetails().getUser(), null);
            result.put("flag", SUCCESS);
            result.put("brand", map.get("brand"));
            }
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            if (e.getErrorCode() == -1) {
                result.put("msg", e.getMessage());
            } else if (e.getErrorCode() == 10007) {
                result.put("msg", "上架数量不正确，或已经超出");
            } else {
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                logger.error(e.getMessage());
                result.put("msg", sb.toString());
            }

        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 验证商品属性手动上架
     */
    public String checkSkuShouPro() throws JSONException {
        JSONObject result = new JSONObject();
        String isXq = null;
        try {
            if (isNike) {
                RelationNike nike = wareHouseManager.getSysCaseNumber(code, getUserDetails().getCurrentOu().getId());
                if (nike != null) {
                    isXq = pdaShelvesManager.checkSkuShouPro(num, nike.getSysPid(), skuBarcode, getUserDetails().getCurrentOu(), getUserDetails().getUser());
                    result.put("flag", SUCCESS);
                    result.put("isXq", isXq);
                } else {
                    result.put("flag", ERROR);
                }
            } else {
                isXq = pdaShelvesManager.checkSkuShouPro(num, code, skuBarcode, getUserDetails().getCurrentOu(), getUserDetails().getUser());
                result.put("flag", SUCCESS);
                result.put("isXq", isXq);
            }
        } catch (BusinessException e) {
            // e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("checkSkuShouPro BusinessException" + skuBarcode, e);
            };
            result.put("flag", ERROR);
            result.put("msg", e.getMessage());
        } catch (Exception e1) {
            // e1.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("checkSkuShouPro Exception" + skuBarcode, e1);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 验证商品手动上架
     */
    public String checkSkuShou() throws JSONException {
        JSONObject result = new JSONObject();
        String tip = null;
        try {
            if (isNike) {
                RelationNike nike = wareHouseManager.getSysCaseNumber(code, getUserDetails().getCurrentOu().getId());
                if(nike!=null){
                    tip = pdaShelvesManager.checkSkuShou(nike.getSysPid(), skuBarcode, num, eDate, sDate, getUserDetails().getCurrentOu(), getUserDetails().getUser());
                    result.put("flag", SUCCESS);
                    result.put("tip", tip);
                }else{
                    result.put("flag", ERROR);
                    result.put("msg", "找不到系统箱号"); 
                }
            } else {
                tip = pdaShelvesManager.checkSkuShou(code, skuBarcode, num, eDate, sDate, getUserDetails().getCurrentOu(), getUserDetails().getUser());
                result.put("flag", SUCCESS);
                result.put("tip", tip);
            }
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            result.put("msg", e.getMessage());
        } catch (Exception e1) {
            // logger.error("PdaShelvesAction.initMongodbWhShelvesInfo:箱号=" + code + ",用户id=" +
            // getUserDetails().getUser().getId());
            // e1.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("PdaShelvesAction.initMongodbWhShelvesInfo:箱号=" + code + ",用户id=" + getUserDetails().getUser().getId(), e1);
            };
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * Mongodb 缓存数据 手动 良品
     * 
     * @param staCode
     * @return
     */
    public String initMongodbWhShelvesInfoShou() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            if (isNike) {
                RelationNike nike = wareHouseManager.getSysCaseNumber(code, getUserDetails().getCurrentOu().getId());
                if (nike != null) {
                    pdaShelvesManager.initMongodbWhShelvesInfoShou(nike.getSysPid(), getUserDetails().getCurrentOu(), getUserDetails().getUser());
                    result.put("flag", SUCCESS);
                } else {
                    result.put("flag", ERROR);
                    result.put("msg", "找不到Nike系统箱号");
                }
            } else {
                pdaShelvesManager.initMongodbWhShelvesInfoShou(code, getUserDetails().getCurrentOu(), getUserDetails().getUser());
                result.put("flag", SUCCESS);
            }
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            result.put("msg", e.getMessage());
        } catch (Exception e1) {
            // logger.error("PdaShelvesAction.initMongodbWhShelvesInfo:箱号=" + code + ",用户id=" +
            // getUserDetails().getUser().getId());
            // e1.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("PdaShelvesAction.initMongodbWhShelvesInfo:箱号=" + code + ",用户id=" + getUserDetails().getUser().getId(), e1);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * Mongodb 缓存数据残次品
     * 
     * @param staCode
     * @return
     */
    public String initMongodbWhShelvesInfoIncomplete() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            pdaShelvesManager.initMongodbWhShelvesInfoIncomplete(code, getUserDetails().getCurrentOu(), getUserDetails().getUser());
            result.put("flag", SUCCESS);
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            result.put("msg", e.getMessage());
        } catch (Exception e1) {
            // logger.error("PdaShelvesAction.initMongodbWhShelvesInfo:箱号=" + code + ",用户id=" +
            // getUserDetails().getUser().getId());
            // e1.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("PdaShelvesAction.initMongodbWhShelvesInfo:箱号=" + code + ",用户id=" + getUserDetails().getUser().getId(), e1);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * Mongodb 缓存数据良品
     * 
     * @param staCode
     * @return
     */
    public String initMongodbWhShelvesInfo() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Map<String, Object> map = pdaShelvesManager.initMongodbWhShelvesInfo(code, getUserDetails().getCurrentOu(), getUserDetails().getUser());
            try {
                String msgs = (String) map.get("msgs");
                String[] ls = msgs.split(",");
                for (String s : ls) {
                    if (!"".equals(s)) {
                        MsgToWcsThread wt = new MsgToWcsThread();
                        wt.setMsgId(Long.valueOf(s));
                        wt.setType(WcsInterfaceType.SShouRongQi.getValue());
                        new Thread(wt).start();
                    }

                }
            } catch (Exception e) {
                result.put("errorMsg", "");
                logger.error("auto inbound error:" + e.getMessage());
            }

            result.put("tip", map.get("tip"));
            result.put("flag", SUCCESS);
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            result.put("msg", e.getMessage());
        } catch (Exception e1) {
            // logger.error("PdaShelvesAction.initMongodbWhShelvesInfo:箱号=" + code + ",用户id=" +
            // getUserDetails().getUser().getId());
            // e1.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("PdaShelvesAction.initMongodbWhShelvesInfo:箱号=" + code + ",用户id=" + getUserDetails().getUser().getId(), e1);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 验证sku并推荐库位
     * 
     * @return
     */
    public String verifyAndRecommend() throws JSONException {
        logger.error("verifyAndRecommend_start:" + skuBarcode + "," + code);
        JSONObject result = new JSONObject();
        try {
            Map<String, Object> map = pdaShelvesManager.verifyAndRecommend(skuBarcode, code, getUserDetails().getCurrentOu(), getUserDetails().getUser());
            result.put("line", JsonUtil.obj2json(map.get("line")));// 扫描商品时，推荐库位;
            result.put("barCode", map.get("barCode"));// 商品编码
            result.put("flag", SUCCESS);
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            result.put("msg", e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 取消还原商品（mondb与ora）
     */
    public String cancelSku() throws JSONException {
        logger.error("cancelSku_start:" + skuBarcode + "," + code + "," + locationCode);
        JSONObject result = new JSONObject();
        try {
            pdaShelvesManager.cancelSku(skuBarcode, code, locationCode, getUserDetails().getCurrentOu(), getUserDetails().getUser());
            result.put("flag", SUCCESS);
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            result.put("msg", e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 取消还原商品（残次品）
     */
    public String cancelSkuIncomplete() throws JSONException {
        logger.error("cancelSkuIncomplete_start:" + skuBarcode + "," + code + "," + locationCode);
        JSONObject result = new JSONObject();
        try {
            pdaShelvesManager.cancelSkuIncomplete(skuBarcode, code, locationCode, getUserDetails().getCurrentOu(), getUserDetails().getUser());
            result.put("flag", SUCCESS);
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            result.put("msg", e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 库位上架
     * 
     * @throws Exception
     */
    public String locationShelves() throws Exception {
        logger.error("locationShelves_start:" + skuBarcode + "," + code + "," + locationCode);
        JSONObject result = new JSONObject();
        try {
            Map<String, Object> map = pdaShelvesManager.locationShelves(skuBarcode, code, locationCode, getUserDetails().getCurrentOu(), getUserDetails().getUser(), null);
            result.put("isOver", map.get("isOver"));
            result.put("flag", SUCCESS);
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            if (e.getErrorCode() == -1) {
                result.put("msg", e.getMessage());
            } else if (e.getErrorCode() == 10007) {
                result.put("msg", "上架数量不正确，或已经超出");
            } else {
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                logger.error(e.getMessage());
                result.put("msg", sb.toString());
            }

        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 库位上架(残次品)
     * 
     * @throws Exception
     */
    public String locationShelvesDmgCode() throws Exception {
        logger.error("locationShelvesDmgCode_start:" + skuBarcode + "," + code + "," + locationCode);
        JSONObject result = new JSONObject();
        try {
            Map<String, Object> map = pdaShelvesManager.locationShelvesDmgCode(skuBarcode, code, locationCode, getUserDetails().getCurrentOu(), getUserDetails().getUser(), null);
            result.put("isOver", map.get("isOver"));
            result.put("flag", SUCCESS);
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            if (e.getErrorCode() == -1) {
                result.put("msg", e.getMessage());
            } else if (e.getErrorCode() == 10007) {
                result.put("msg", "上架数量不正确，或已经超出");
            } else {
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                logger.error(e.getMessage());
                result.put("msg", sb.toString());
            }

        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 扫描商品插入操作明细(良品逐渐)
     * 
     * @return
     * @throws Exception
     */
    public String scanSku() throws Exception {
        logger.error("scanSku_start:" + skuBarcode + "," + code + "," + locationCode);
        JSONObject result = new JSONObject();
        try {
            Map<String, Object> map = pdaShelvesManager.scanSku(skuBarcode, code, locationCode, getUserDetails().getCurrentOu(), getUserDetails().getUser());
            result.put("line", JsonUtil.obj2json(map.get("line")));// 扫描商品时，推荐库位;
            result.put("isOver", map.get("isOver"));
            result.put("flag", SUCCESS);
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            if (e.getErrorCode() == -1) {
                result.put("msg", e.getMessage());
            } else if (e.getErrorCode() == 10007) {
                result.put("msg", "上架数量不正确，或已经超出");
            } else {
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                logger.error(e.getMessage());
                result.put("msg", sb.toString());
            }

        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 扫描商品插入操作明细(残次品)
     * 
     * @return
     * @throws JSONException
     * @throws Exception
     */
    public String scanSkuIncomplete() throws JSONException {
        logger.error("scanSkuIncomplete_start:" + skuBarcode + "," + code + "," + locationCode);
        JSONObject result = new JSONObject();
        try {
            Map<String, Object> map = pdaShelvesManager.scanSkuIncomplete(skuBarcode, code, locationCode, getUserDetails().getCurrentOu(), getUserDetails().getUser());
            result.put("line", JsonUtil.obj2json(map.get("line")));// 扫描商品时，推荐库位;
            result.put("isOver", map.get("isOver"));
            result.put("flag", SUCCESS);
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            if (e.getErrorCode() == -1) {
                result.put("msg", e.getMessage());
            } else if (e.getErrorCode() == 10007) {
                result.put("msg", "上架数量不正确，或已经超出");
            } else {
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                logger.error(e.getMessage());
                result.put("msg", sb.toString());
            }
        } catch (Exception e) {
            result.put("flag", ERROR);
            result.put("msg", e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 扫描商品插入操作明细(良品批量)
     * 
     * @return
     * @throws Exception
     */
    public String scanSku2() throws Exception {
        logger.error("scanSku2_start:" + skuBarcode + "," + code + "," + locationCode);
        JSONObject result = new JSONObject();
        try {
            Map<String, Object> map = pdaShelvesManager.scanSku2(skuBarcode, code, locationCode, getUserDetails().getCurrentOu(), getUserDetails().getUser(), num);
            result.put("line", JsonUtil.obj2json(map.get("line")));// 扫描商品时，推荐库位;
            result.put("isOver", map.get("isOver"));
            result.put("flag", SUCCESS);
        } catch (BusinessException e) {
            result.put("flag", ERROR);
            if (e.getErrorCode() == -1) {
                result.put("msg", e.getMessage());
            } else if (e.getErrorCode() == 10007) {
                result.put("msg", "上架数量不正确，或已经超出");
            } else {
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                logger.error(e.getMessage());
                result.put("msg", sb.toString());
            }

        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 验证库位
     * 
     * @return
     * @throws JSONException
     * @throws Exception
     */
    public String verifyLocation() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Map<String, Object> map = pdaShelvesManager.verifyLocation(locationCode, getUserDetails().getCurrentOu(), getUserDetails().getUser());
            result.put("location", map.get("location"));
            result.put("flag", SUCCESS);
        } catch (Exception e) {
            // e.printStackTrace();
            if (logger.isErrorEnabled()) {
                logger.error("verifyLocation", e);
            };
            result.put("flag", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }
   
    /**
     * 校验作业单状态
     * @return
     * @throws JSONException 
     */
    public String checkStaStatus() throws JSONException{
        JSONObject result = new JSONObject();
        String re = pdaShelvesManager.checkStaStatus(code, getUserDetails().getCurrentOu().getId());
        result.put("re", re);
        request.put(JSON, result);
        return JSON;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(String skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }


    public Boolean getIsNike() {
        return isNike;
    }


    public void setIsNike(Boolean isNike) {
        this.isNike = isNike;
    }


}
