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

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.manager.warehouse.BaseQueryThreadPoolManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.OccupationInfoCommand;

import loxia.dao.Pagination;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

/**
 * @author zhl
 * 
 */
public class InventoryQueryAction extends BaseJQGridProfileAction {

    @Autowired
    private ChannelManager channelManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private BaseinfoManager baseinfoManager;

    private static final long serialVersionUID = -6557588123114629891L;
    private InventoryCommand inventoryCommand;
    private OccupationInfoCommand occupationInfoCommand;

    private Warehouse warehouse;
    private List<BiChannel> companyShopList;
    private List<InventoryStatus> inventoryStatusList;
    @Autowired
    private ExcelExportManager excelExportManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private BaseQueryThreadPoolManager baseQueryThreadPoolManager;

    private File file;
    private String invOwner;

    public String inventoryQueryEntry() {
        // 根据页面字段确认是否需要再次查询
        // 仓库共享状态
        warehouse = baseinfoManager.findWarehouseByOuId(userDetails.getCurrentOu().getId());
        // 当前仓库所属的公司
        OperationUnit companyOu = baseinfoManager.findCompanyByWarehouse(userDetails.getCurrentOu().getId());
        // 仓库库存状态信息
        inventoryStatusList = wareHouseManager.findInvStatusListByCompany(companyOu.getId());
        // 公司下的所有店铺列表
        companyShopList = channelManager.getAllWhRefChannelByCmpouId(companyOu.getId());
        return SUCCESS;
    }

    public String findCurrentInventory() throws Exception {
        Long companyid = baseinfoManager.findCompanyByWarehouse(userDetails.getCurrentOu().getId()).getId();
        setTableConfig();
        // Pagination<InventoryCommand> invList =
        // wareHouseManagerQuery.findCurrentInventoryByPage(tableConfig.getStart(),
        // tableConfig.getPageSize(), inventoryCommand, userDetails.getCurrentOu().getId(),
        // companyid, tableConfig.getSorts());
        if (inventoryCommand.getIsShowZero() == true) {
            Pagination<InventoryCommand> invList = baseQueryThreadPoolManager.findCurrentInventoryZeroByPage(tableConfig.getStart(), tableConfig.getPageSize(), inventoryCommand, userDetails.getCurrentOu().getId(), companyid, tableConfig.getSorts());
            request.put(JSON, toJson(invList));
        } else {
            Pagination<InventoryCommand> invList = baseQueryThreadPoolManager.findCurrentInventoryByPage(tableConfig.getStart(), tableConfig.getPageSize(), inventoryCommand, userDetails.getCurrentOu().getId(), companyid, tableConfig.getSorts());
            request.put(JSON, toJson(invList));
        }

        return JSON;
    }

    public String findSkuInventory() throws Exception {
        if (file == null) {
            return ERROR;
        } else {
            String owner = "";
            if (inventoryCommand != null) {
                owner = inventoryCommand.getInvOwner();
            }
            Map<String, Object> maprs = wareHouseManager.findSkuInventory(file, userDetails.getCurrentOu().getId(), owner);
            ReadStatus rs = (ReadStatus) maprs.get("rs");
            if (rs.getStatus() == ReadStatus.STATUS_READ_FILE_ERROR) {
                @SuppressWarnings("unchecked")
                List<InventoryCommand> commands = (List<InventoryCommand>) maprs.get("command");
                response.setHeader("Content-Disposition", "attachment;filename=" + "Stock" + Constants.EXCEL_XLS);
                ServletOutputStream outs = null;
                try {
                    outs = response.getOutputStream();
                    excelExportManager.exportInventorySku(outs, commands);
                } catch (Exception e) {
                    log.error("", e);
                } finally {
                    if (outs != null) {
                        outs.flush();
                        outs.close();
                    }
                }
            } else if (rs.getStatus() == 0) {
                String msg = "导入SKU数量超过1000个";
                request.put("msg", msg);
            } else if (rs.getStatus() == 2) {
                String msg = "没有找到对应SKU的库存";
                request.put("msg", msg);
            }
            return SUCCESS;

        }
    }

    public String findInventoryDetial() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManagerQuery.findInventoryBySku(inventoryCommand.getSkuId(), inventoryCommand.getOwner(), userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 库存明细查寻
     * 
     * @return
     */
    public String inventoryDetailsQueryEntry() {
        return SUCCESS;
    }

    public String inventoryDetailsQuery() {
        Long companyid = baseinfoManager.findCompanyByWarehouse(userDetails.getCurrentOu().getId()).getId();
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findDetailsInventoryByPageShelfLife(tableConfig.getStart(), tableConfig.getPageSize(), inventoryCommand, userDetails.getCurrentOu().getId(), companyid, tableConfig.getSorts())));
        return JSON;
    }

    public String inventoryDetailsQuery1() throws JSONException {
        JSONObject result = new JSONObject();
        Long companyid = baseinfoManager.findCompanyByWarehouse(userDetails.getCurrentOu().getId()).getId();
        List<InventoryCommand> sku = wareHouseManager.findDetailsInventorySkuSum(inventoryCommand, userDetails.getCurrentOu().getId(), companyid);
        List<InventoryCommand> loc = wareHouseManager.findDetailsInventoryLocationSum(inventoryCommand, userDetails.getCurrentOu().getId(), companyid);
        InventoryCommand inv = wareHouseManager.findDetailsInventorySkuQty(inventoryCommand, userDetails.getCurrentOu().getId(), companyid);
        result.put("sku", sku.size());
        result.put("loc", loc.size());
        result.put("qty", inv.getQuantity());
        result.put("lockqty", inv.getLockQty());
        result.put("availqty", inv.getAvailQty());
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 平台对接编码下拉列表
     * 
     * @return
     */
    public String findExtCode1() {
        Long ouId = this.userDetails.getCurrentOu().getId();
        request.put(JSON, JsonUtil.collection2json(wareHouseManager.findExtCode1(ouId)));
        return JSON;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    // GETTER && SETTER
    public InventoryCommand getInventoryCommand() {
        return inventoryCommand;
    }

    public void setInventoryCommand(InventoryCommand inventoryCommand) {
        this.inventoryCommand = inventoryCommand;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public List<InventoryStatus> getInventoryStatusList() {
        return inventoryStatusList;
    }

    public void setInventoryStatusList(List<InventoryStatus> inventoryStatusList) {
        this.inventoryStatusList = inventoryStatusList;
    }

    public OccupationInfoCommand getOccupationInfoCommand() {
        return occupationInfoCommand;
    }

    public void setOccupationInfoCommand(OccupationInfoCommand occupationInfoCommand) {
        this.occupationInfoCommand = occupationInfoCommand;
    }

    public List<BiChannel> getCompanyShopList() {
        return companyShopList;
    }

    public void setCompanyShopList(List<BiChannel> companyShopList) {
        this.companyShopList = companyShopList;
    }

    public String getInvOwner() {
        return invOwner;
    }

    public void setInvOwner(String invOwner) {
        this.invOwner = invOwner;
    }


}
