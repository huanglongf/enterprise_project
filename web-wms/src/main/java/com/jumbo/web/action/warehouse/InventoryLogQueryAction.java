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

import loxia.dao.Pagination;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.manager.warehouse.TransactionTypeManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;


public class InventoryLogQueryAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4701880373848536905L;

    @Autowired
    private WareHouseManager warehouseManager;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private TransactionTypeManager transactionTypeManager;
    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private ChannelManager channelManager;

    private StockTransTxLogCommand stock;
    private List<BiChannel> companyShopList;

    public String inventoryLogQuery() {
        return SUCCESS;
    }

    /***
     * 查找店铺信息
     * 
     * @return
     */
    public String findcompanyShopList() {
        // 公司组织
        OperationUnit companyOu = baseinfoManager.findCompanyByWarehouse(this.userDetails.getCurrentOu().getId());
        // 公司下的所有店铺列表
        request.put(JSON, JsonUtil.collection2json(channelManager.getAllWhRefChannelByCmpouId(companyOu.getId())));
        return JSON;
    }

    /**
     * 查找运营中心下的作业类型信息，及系统预定义的作业类型
     * 
     * @return
     */
    public String findTransactionTypeList() {
        // 运营中心组织
        OperationUnit operationCenter = authorizationManager.getOUByPrimaryKey(this.userDetails.getCurrentOu().getId()).getParentUnit();
        request.put(JSON, JsonUtil.collection2json(transactionTypeManager.findTransactionListByOu(operationCenter.getId())));
        return JSON;
    }

    /**
     * 分页查询库存操作日志信息
     * 
     * @return
     */
    public String inventoryLogDetail() {
        setTableConfig();
        Long whouid = this.userDetails.getCurrentOu().getId();
        Long companyid = authorizationManager.getOUByPrimaryKey(userDetails.getCurrentOu().getId()).getParentUnit().getParentUnit().getId();
        if (stock != null) {
            stock.setStockStartTime(FormatUtil.getDate(stock.getStockStartTime1()));
            stock.setStockEndTime(FormatUtil.getDate(stock.getStockEndTime1()));
        }
        Pagination<StockTransTxLogCommand> stocklist = warehouseManager.findStockTransTxLogByPage(tableConfig.getStart(), tableConfig.getPageSize(), stock, whouid, companyid, tableConfig.getSorts());
        request.put(JSON, toJson(stocklist));
        return JSON;
    }

    // GETTER && SETTER
    public StockTransTxLogCommand getStock() {
        return stock;
    }

    public void setStock(StockTransTxLogCommand stock) {
        this.stock = stock;
    }

    public List<BiChannel> getCompanyShopList() {
        return companyShopList;
    }

    public void setCompanyShopList(List<BiChannel> companyShopList) {
        this.companyShopList = companyShopList;
    }
}
