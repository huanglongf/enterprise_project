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
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.util.DateUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.ParcelSortingMode;
import com.jumbo.wms.model.warehouse.PickingMode;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

/**
 * 
 * 作业单-生成配货清单
 * 
 * @author sjk
 * 
 */
public class SalesGenerationDispatchListAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4238985572898095145L;

    @Autowired
    private WareHouseManager wareHouseManager;

    /**
     * STA
     */
    private StockTransApplication sta;
    /**
     * 开始时间
     */
    private String fromDate;
    /**
     * 结束时间
     */
    private String toDate;
    /**
     * 店铺ID
     */
    private String shopId;
    /**
     * staId list
     */
    private List<Long> staIdList;
    /**
     * 是否需要发票
     */
    private Integer isNeedInvoice;
    private Integer limit;
    private Integer plCount;
    private String city;
    private String shoplist;
    private Boolean isMqInvoice;
    private Boolean isCod;
    /**
     * 商品
     */
    private Sku sku;

    private List<String> skuCodeList;

    /**
     * 是否包含大件商品
     */
    private Boolean isBigBox;

    /**
     * 作业单-生成配货清单
     * 
     * @return
     */
    public String genDispatchListEntry() {
        return SUCCESS;
    }

    /**
     * 可操作Sta列表
     * 
     * @return
     */

    public String staPendingList() {
        setTableConfig();
        Pagination<StockTransApplicationCommand> stas =
                wareHouseManager.findSalesPendingStaListPage(shoplist, getCityList(city), tableConfig.getStart(), tableConfig.getPageSize(), false, getDate(fromDate), getDate(toDate), null, isNeedInvoice, sta, sku, userDetails.getCurrentOu().getId(),
                        getTrimSkuCodeList(), tableConfig.getSorts());
        request.put(JSON, toJson(stas));
        return JSON;
    }

    /**
     * 查询sta line
     * 
     * @return
     */
    public String findSalesStaLine() {
        setTableConfig();
        Pagination<StaLineCommand> stals = wareHouseManager.findStaLineCommandListByStaId(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts());
        request.put(JSON, toJson(stals));
        return JSON;
    }

    /**
     * 查询仓库关联的店铺打印装箱清单是否按店铺定制
     * 
     * @return
     * @throws JSONException
     */
    public String warehosueIsRelateShopForPrint() throws JSONException {
        String flag = SUCCESS;
        JSONObject result = new JSONObject();
        List<String> trankNames = wareHouseManager.warehosueIsRelateShopForPrint(userDetails.getCurrentOu().getId());
        if (trankNames == null || trankNames.isEmpty()) {
            result.put("result", flag);
        } else {
            for (String s : trankNames) {
                if (StringUtils.hasLength(s)) {
                    flag = ERROR;
                    break;
                } else {
                    continue;
                }
            }
            result.put("result", flag);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询仓库关联的店铺打印装箱清单是否相同
     * 
     * @return
     * @throws JSONException
     */
    public String warehosueShopForPrintIsSame() throws JSONException {
        String flag = SUCCESS;
        JSONObject result = new JSONObject();
        List<String> trankNames = wareHouseManager.warehosueShopForPrintIsSame(userDetails.getCurrentOu().getId(), shoplist);
        if (trankNames == null || trankNames.isEmpty()) {
            result.put("result", flag);
        } else {
            for (String s : trankNames) {
                if (StringUtils.hasLength(s)) {
                    flag = ERROR;
                    result.put("tips", s);
                    break;
                } else {
                    continue;
                }
            }
            result.put("result", flag);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询仓库关联的店铺 - 是否选择了多个维护模版的店铺 - shoplist
     * 
     * @return
     * @throws JSONException
     */
    public String warehosueIsRelateMulitShop() throws JSONException {
        String flag = SUCCESS;
        JSONObject result = new JSONObject();
        Long count = wareHouseManager.warehosueIsRelateMulitShop(shoplist, userDetails.getCurrentOu().getId());
        if (count.intValue() > 1) {
            flag = ERROR;
            result.put("result", flag);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String customiztationTemplShop() throws JSONException {
        JSONObject result = new JSONObject();
        Warehouse house = wareHouseManager.getByOuId(userDetails.getCurrentOu().getId());
        List<String> innerShopCoders = wareHouseManager.customiztationTemplShop(userDetails.getCurrentOu().getId());
        if (innerShopCoders != null && !innerShopCoders.isEmpty()) {
            if (innerShopCoders != null && !innerShopCoders.isEmpty()) {
                result.put("returnVal", JsonUtil.collection2json(innerShopCoders));
            }
            request.put(JSON, result);
        }
        if (house != null) {
            result.put("isAuto", house.getIsAutoWh());
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 自动生成配货批 追加是否包含大件商品 isBigBox fanht
     * 
     * @return
     * @throws JSONException
     */
    public String generationPendingList() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            boolean isSpPg = false;
            if (sta != null) {
                isSpPg = sta.getIsSpecialPackaging() == null ? false : sta.getIsSpecialPackaging();
            }
            wareHouseManager.genPickingListNative(isSpPg, shoplist, getCityList(city), ParcelSortingMode.SECONDARY, PickingMode.MODE1, userDetails.getUser().getId(), null, limit, plCount, false, getDate(fromDate), getDate(toDate), null, // shopId
                    isNeedInvoice, sta, sku, userDetails.getCurrentOu().getId(), getTrimSkuCodeList(), isMqInvoice, isBigBox, isCod);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            BusinessException el = e.getLinkedException();
            if (el != null) {
                result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + el.getErrorCode(), el.getArgs()));
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 手动生成配货批 追加是否包含大件商品 isBigBox fanht
     * 
     * @return
     * @throws JSONException
     */
    public String generationPendingListByList() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.createPickingList(null, staIdList, ParcelSortingMode.SECONDARY, PickingMode.MODE1, userDetails.getCurrentOu().getId(), userDetails.getUser().getId(), isMqInvoice, isBigBox);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            BusinessException el = e.getLinkedException();
            if (el != null) {
                result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + el.getErrorCode(), el.getArgs()));
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    private List<String> getTrimSkuCodeList() {
        if (getSkuCodeList() == null) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        for (String tmp : getSkuCodeList()) {
            if (StringUtils.hasText(tmp)) {
                list.add(tmp);
            }
        }
        if (list.size() == 0) {
            return null;
        } else {
            return list;
        }
    }

    private List<String> getCityList(String str) {
        if (!StringUtils.hasText(str)) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        if (str != null && StringUtils.hasText(str)) {
            for (String s : str.split(",")) {
                list.add(s);
            }
        }
        return list;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public List<Long> getStaIdList() {
        return staIdList;
    }

    public void setStaIdList(List<Long> staIdList) {
        this.staIdList = staIdList;
    }

    public Integer getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Integer isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPlCount() {
        return plCount;
    }

    public void setPlCount(Integer plCount) {
        this.plCount = plCount;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    private Date getDate(String date) {
        Date s = null;
        try {
            if (StringUtils.hasText(date)) {
                s = FormatUtil.stringToDate(date);
                boolean b = DateUtil.isYearDate(date);
                if (b) {
                    s = DateUtil.addDays(s, 1);
                }
            }
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
        }
        return s;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public String getShoplist() {
        return shoplist;
    }

    public void setShoplist(String shoplist) {
        this.shoplist = shoplist;
    }

    public List<String> getSkuCodeList() {
        return skuCodeList;
    }

    public void setSkuCodeList(List<String> skuCodeList) {
        this.skuCodeList = skuCodeList;
    }

    public Boolean getIsMqInvoice() {
        return isMqInvoice;
    }

    public void setIsMqInvoice(Boolean isMqInvoice) {
        this.isMqInvoice = isMqInvoice;
    }

    public Boolean getIsBigBox() {
        return isBigBox;
    }

    public void setIsBigBox(Boolean isBigBox) {
        this.isBigBox = isBigBox;
    }

    public Boolean getIsCod() {
        return isCod;
    }

    public void setIsCod(Boolean isCod) {
        this.isCod = isCod;
    }

}
