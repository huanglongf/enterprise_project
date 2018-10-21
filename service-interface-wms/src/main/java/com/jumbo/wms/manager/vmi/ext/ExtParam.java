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
 */
package com.jumbo.wms.manager.vmi.ext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineCommand;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLineCommand;

/**
 * @author lichuan
 * 
 */
public class ExtParam implements Serializable {

    private static final long serialVersionUID = 7267383690188118613L;
    private String vmiCode;
    private String vmiSource;
    private String asnOrderType;// 收货方式
    private String rsnType;// 反馈方式
    private String orderCode;// 指令订单号
    private String slipCode;
    private StockTransApplication sta;
    private StaLine staLine;
    private List<VmiAsnLineCommand> valList;
    private SkuCommand skuCmd;
    private List<StaLine> staLineList;
    private BiChannel shop;
    private BiChannelCommand shopCmd;
    private Map<String, InventoryStatus> invStatusMap;
    private Map<String, StaLine> staLineMap = new HashMap<String, StaLine>();
    private String strType;
    private BiChannelCommand bi;
    private Sku sku;
    private InventoryCommand inventoryCommand;
    private BiChannel biChannel;
    private List<StvLineCommand> stvLineList;
    private StockTransVoucher stv;


    public String getVmiCode() {
        return vmiCode;
    }

    public void setVmiCode(String vmiCode) {
        this.vmiCode = vmiCode;
    }

    public String getVmiSource() {
        return vmiSource;
    }

    public void setVmiSource(String vmiSource) {
        this.vmiSource = vmiSource;
    }

    public String getAsnOrderType() {
        return asnOrderType;
    }

    public void setAsnOrderType(String asnOrderType) {
        this.asnOrderType = asnOrderType;
    }

    public String getRsnType() {
        return rsnType;
    }

    public void setRsnType(String rsnType) {
        this.rsnType = rsnType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public StaLine getStaLine() {
        return staLine;
    }

    public void setStaLine(StaLine staLine) {
        this.staLine = staLine;
    }

    public List<VmiAsnLineCommand> getValList() {
        return valList;
    }

    public void setValList(List<VmiAsnLineCommand> valList) {
        this.valList = valList;
    }

    public SkuCommand getSkuCmd() {
        return skuCmd;
    }

    public void setSkuCmd(SkuCommand skuCmd) {
        this.skuCmd = skuCmd;
    }

    public List<StaLine> getStaLineList() {
        return staLineList;
    }

    public void setStaLineList(List<StaLine> staLineList) {
        this.staLineList = staLineList;
    }

    public BiChannel getShop() {
        return shop;
    }

    public void setShop(BiChannel shop) {
        this.shop = shop;
    }

    public BiChannelCommand getShopCmd() {
        return shopCmd;
    }

    public void setShopCmd(BiChannelCommand shopCmd) {
        this.shopCmd = shopCmd;
    }

    public Map<String, InventoryStatus> getInvStatusMap() {
        return invStatusMap;
    }

    public void setInvStatusMap(Map<String, InventoryStatus> invStatusMap) {
        this.invStatusMap = invStatusMap;
    }

    public Map<String, StaLine> getStaLineMap() {
        return staLineMap;
    }

    public void setStaLineMap(Map<String, StaLine> staLineMap) {
        this.staLineMap = staLineMap;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public BiChannelCommand getBi() {
        return bi;
    }

    public void setBi(BiChannelCommand bi) {
        this.bi = bi;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public InventoryCommand getInventoryCommand() {
        return inventoryCommand;
    }

    public void setInventoryCommand(InventoryCommand inventoryCommand) {
        this.inventoryCommand = inventoryCommand;
    }

    public BiChannel getBiChannel() {
        return biChannel;
    }

    public void setBiChannel(BiChannel biChannel) {
        this.biChannel = biChannel;
    }

    public List<StvLineCommand> getStvLineList() {
        return stvLineList;
    }

    public void setStvLineList(List<StvLineCommand> stvLineList) {
        this.stvLineList = stvLineList;
    }

    public StockTransVoucher getStv() {
        return stv;
    }

    public void setStv(StockTransVoucher stv) {
        this.stv = stv;
    }


}
