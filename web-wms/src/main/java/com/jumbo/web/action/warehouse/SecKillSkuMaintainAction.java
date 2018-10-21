package com.jumbo.web.action.warehouse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.SecKillSkuManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 秒杀订单商品维护控制逻辑
 * 
 * @author jinlong.ke
 * 
 */
public class SecKillSkuMaintainAction extends BaseJQGridProfileAction {

	/**
     * 
     */
	private static final long serialVersionUID = 3216439140195401675L;
	@Autowired
	private SecKillSkuManager secKillSkuManager;
	@Autowired
	private WareHouseManager wareHouseManager;
	@Autowired
	private WareHouseManagerQuery wareHouseManagerQuery;
	/**
	 * 商品条码
	 */
	private String barCode;
	/**
	 * 商品数量
	 */
	private Integer skuQty;
	/**
	 * 秒杀Skus
	 */
	private String skus;
	/**
	 * 过期时间
	 */
	private String expireDate;
	/**
	 * 秒杀商品Id
	 */
	private Long secKillId;
	/**
	 * idList
	 */
	private List<Long> idList;
	/**
	 * titleList
	 */
	private List<String> titleList;
	/**
	 * 是否套装组合商品 根据条码查询商品时 sn号不能秒杀 但是可以套装组合
	 */
	private String isPackage;

	/**
	 * 跳转到秒杀订单商品维护界面
	 * 
	 * @return
	 */
	public String secKillSkuMaintain() {
		return SUCCESS;
	}

	/**
	 * 根据输入的条形码查询商品
	 * 
	 * @return
	 * @throws JSONException
	 */
	public String getSkuInfoByBarCode() throws JSONException {
		JSONObject result = new JSONObject();
		Long customerId = wareHouseManagerQuery.getCustomerByWhouid(userDetails.getCurrentOu().getId());
		Sku sku = wareHouseManager.getSkuByBarcode(barCode, customerId);
		if (sku == null) {
			result.put("rs", "error");
			// 没有找到该条码对应的商品，请核对后重新输入！
			String errorMsg = getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.SECKILLSKU_FAILED), new Object[] { "" });
			result.put("errorMsg", errorMsg);
		} else {
			if (sku.getIsSnSku() != null && sku.getIsSnSku() && (isPackage == null)) {
				result.put("rs", "error");
				// SN号商品不能计入秒杀
				String errorMsg = getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.SECKILLSKU_SNNOT), new Object[] { "" });
				result.put("errorMsg", errorMsg);
			} else {
				sku.setVersion(skuQty);
				result = new JSONObject(sku);
			}
		}
		request.put(JSON, result);
		return JSON;
	}

	/**
	 * 插入秒杀商品
	 * 
	 * @return
	 * @throws JSONException
	 */
	public String addSeckillSku() throws JSONException {
		JSONObject result = new JSONObject();
		try {
			result.put("rs", "error");
			if (FormatUtil.getDate(expireDate) != null) {
				if (FormatUtil.getDate(expireDate).before(new Date())) {
					// 过期时间不能小于创建时间
					throw new BusinessException(ErrorCode.PACKAGESKU_EXPIRETIMEBIG);
				}
			}
			Boolean b = wareHouseManager.getByOuId(userDetails.getCurrentOu().getId()).getIsSupportSecKill();
			if (b == null || !b) {
				String errorMsg = getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.NOT_SUPPORT_SECKILLSKU), new Object[] { "" });
				result.put("errorMsg", errorMsg);
			} else {
				secKillSkuManager.addNewSecKillSku(userDetails.getCurrentOu(), skuQty, idList, titleList, FormatUtil.getDate(expireDate));
				result.put("rs", "success");
			}
		} catch (BusinessException e) {
			String errorMsg = getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), new Object[] { "" });
			result.put("errorMsg", errorMsg);
			log.debug("SecKillSkuMaintainAction.addSecKillSku--------------------------->BusinessException");
			log.error("", e);
		} catch (Exception e) {
			// 创建秒杀商品失败
			String errorMsg = getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.SECKILLSKU_ERROR), new Object[] { "" });
			result.put("errorMsg", errorMsg);
			log.debug("SecKillSkuMaintainAction.addSecKillSku--------------------------->Exception");
			log.error("", e);
		}
		request.put(JSON, result);
		return JSON;
	}

	/**
	 * 
	 * @return
	 */
	public String selectAllSecKillSkuByOuId() {
		setTableConfig();
		request.put(JSON, toJson(secKillSkuManager.selectAllSecKillSkuByOu(userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
		return JSON;
	}

	/**
	 * 删除秒杀商品
	 * 
	 * @return
	 * @throws JSONException
	 */
	public String deleteSecKillSkuById() throws JSONException {
		JSONObject result = new JSONObject();
		try {
			secKillSkuManager.deleteSecKillSkuByIdAndSkus(skus, secKillId, userDetails.getCurrentOu().getId());
			result.put("rs", "success");
		} catch (Exception e) {
			result.put("rs", "error");
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

	public Integer getSkuQty() {
		return skuQty;
	}

	public void setSkuQty(Integer skuQty) {
		this.skuQty = skuQty;
	}

	public String getSkus() {
		return skus;
	}

	public void setSkus(String skus) {
		this.skus = skus;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		if (expireDate == null || expireDate.equals("")) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, 1);
			expireDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
		}
		this.expireDate = expireDate;
	}

	public Long getSecKillId() {
		return secKillId;
	}

	public void setSecKillId(Long secKillId) {
		this.secKillId = secKillId;
	}

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	public List<String> getTitleList() {
		return titleList;
	}

	public void setTitleList(List<String> titleList) {
		this.titleList = titleList;
	}

	public String getIsPackage() {
		return isPackage;
	}

	public void setIsPackage(String isPackage) {
		this.isPackage = isPackage;
	}

}
