package com.jumbo.web.action.warehouse;

import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.warehouse.PrePackagedSkuRefManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 预包装商品维护相关控制
 * @author jinlong.ke
 *
 */
public class PrePackagedRefMaintainAction extends BaseJQGridProfileAction{

    /**
     * 
     */
    private static final long serialVersionUID = 5540499455838301149L;
    @Autowired
    private PrePackagedSkuRefManager prePackagedSkuRefManager;
    private String barCode;
    private Long skuId;
    private Long subSkuId;
    private Long qty;
    public String toCheckIndex(){
        return SUCCESS;
    }
    
    /**
     * 现有预包装商品列表
     * @return
     */
    public String getAllPrePackagedSku() {
        setTableConfig();
        Pagination<Sku> skuList = prePackagedSkuRefManager.findAllPrePackagedSkuByOu(tableConfig.getStart(), tableConfig.getPageSize(), barCode,userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(skuList));
        return JSON;
    }
    public String findSubSkuByIdAndOu(){
        setTableConfig();
        List<SkuCommand> skuList = prePackagedSkuRefManager.findSubSkuByIdAndOu(tableConfig.getStart(), tableConfig.getPageSize(), skuId,userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(skuList));
        return JSON;
    }
    public String findPrePackagedSkuByBarCode() throws JSONException{
        JSONObject result = new JSONObject();
        try {
            Sku plc = prePackagedSkuRefManager.findPrePackagedSkuByBarCode(barCode,userDetails.getCurrentOu().getId());
            if (plc != null) {
                result.put("result", "success");
                result.put("id", plc.getId());
            }else{
                result.put("result", "error");
            }
        } catch (JSONException e) {
            result.put("result", "error");
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据商品条码和组织ID添加预包装商品列表
     * @return
     * @throws JSONException
     */
   public String addPrepackagedSkuByBarCode() throws JSONException {
       JSONObject result = new JSONObject();
		try {
			prePackagedSkuRefManager.addPrepackagedSkuByBarCode(barCode, userDetails.getCurrentOu().getId());
			result.put("result", "success");
		} catch (JSONException e) {
			result.put("result", "error");
			log.error("", e);
		}
       request.put(JSON, result);
       return JSON;
    }
   
   /**
    * 根据主商品条码和组织ID删除预包装商品列表
    * @return
    * @throws JSONException
    */
   public String deletePrepackagedSkuByMainSkuId() throws JSONException {
       JSONObject result = new JSONObject();
		try {
			Long mainSkuId = skuId;
			prePackagedSkuRefManager.deletePrepackagedSkuByMainSkuId(mainSkuId, userDetails.getCurrentOu().getId());
			result.put("result", "success");
		} catch (JSONException e) {
			result.put("result", "error");
			log.error("", e);
		}
       request.put(JSON, result);
       return JSON;
    }
   
   /**
    * 根据主商品SkuId和子商品条形码插入预包装商品
    * @return
    * @throws JSONException
    */
   public String insertPrepackagedSkuBySkuIdAndSubBarCode() throws JSONException{
	   JSONObject result = new JSONObject();
		try {
			Long mainSkuId = skuId;
			prePackagedSkuRefManager.insertPrepackagedSkuBySkuIdAndSubBarCode(mainSkuId, barCode, qty, userDetails.getCurrentOu().getId());
			result.put("result", "success");
		} catch (JSONException e) {
			result.put("result", "error");
			log.error("", e);
		}
      request.put(JSON, result);
      return JSON;
   }
    
   
   /**
    * 根据主商品SkuId和子商品SkuId删除预包装商品
    * @return
    * @throws JSONException
    */
   public String deletePrepackagedSkuByMainSkuIdAndSubSkuId() throws JSONException {
       JSONObject result = new JSONObject();
		try {
			Long mainSkuId = skuId;
			prePackagedSkuRefManager.deletePrepackagedSkuByMainSkuIdAndSubSkuId(mainSkuId, subSkuId, userDetails.getCurrentOu().getId());
			result.put("result", "success");
		} catch (JSONException e) {
			result.put("result", "error");
			log.error("", e);
		}
       request.put(JSON, result);
       return JSON;
    }
   
   /**
    * 通过BarCode获得Sku是否存在
    * @return
    * @throws JSONException
    */
   public String getSkuByBarCode() throws JSONException {
       JSONObject result = new JSONObject();
		try {
			Sku sku = prePackagedSkuRefManager.getSkuByBarCode(barCode);
			if(sku != null){
				result.put("result", "success");
			}else{
                result.put("result", "error");
            }
		} catch (JSONException e) {
			result.put("result", "error");
			log.error("", e);
		}
       request.put(JSON, result);
       return JSON;
    }
   
   /**
    * 根据MainSkuId删除空的预包装商品
    * @return
    * @throws JSONException
    */
   public String deleteEmptyPrepackagedSkuByMainSkuId() throws JSONException {
       JSONObject result = new JSONObject();
		try {
			Long mainSkuId = skuId;
			prePackagedSkuRefManager.deleteEmptyPrepackagedSkuByMainSkuId(mainSkuId);
			result.put("result", "success");
		} catch (JSONException e) {
			result.put("result", "error");
			log.error("", e);
		}
       request.put(JSON, result);
       return JSON;
    }
   
   
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

	public Long getQty() {
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public Long getSubSkuId() {
		return subSkuId;
	}

	public void setSubSkuId(Long subSkuId) {
		this.subSkuId = subSkuId;
	}
    

}
