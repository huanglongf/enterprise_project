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

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.util.DateUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 推荐物流
 * 
 */
public class SalesTransSupportAction extends BaseJQGridProfileAction implements ServletResponseAware {

    private static final long serialVersionUID = -6392582782404665856L;
    @Autowired
    private WareHouseManager wareHouseManager;

    private List<Long> staIds;

    /**
     * 开始时间
     */
    private String fromDate;
    /**
     * 结束时间
     */
    private String toDate;

    private String shopId;

    private StockTransApplication sta;

    private String province;

    public String salesTransSupportEntry() {
        return SUCCESS;
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
            log.error("",e);
            log.error(e.getMessage());
        }
        return s;
    }

    /**
     * 可操作Sta列表
     * 
     * @return
     * @throws ParseException
     */
    public String staSupportTransPendingList() {
        Date s = getDate(fromDate);
        Date end = getDate(toDate);

        setTableConfig();
        Pagination<StockTransApplicationCommand> stas =
                wareHouseManager.findToTransSupportSalesPendingStaListPage(tableConfig.getStart(), tableConfig.getPageSize(), province, null, s, end, shopId, null, sta, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(stas));
        return JSON;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<Long> getStaIds() {
        return staIds;
    }

    public void setStaIds(List<Long> staIds) {
        this.staIds = staIds;
    }

}
