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
import java.util.List;

import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.warehouse.ChannelManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.WarehouseChannel;
import com.jumbo.wms.model.warehouse.WarehouseChannelType;
import com.jumbo.web.action.BaseProfileAction;

public class ChannelAction extends BaseProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2204980356252296470L;

    @Autowired
    private DropDownListManager dropDownListManager;
    @Autowired
    private ChannelManager channelManager;

    // 收货通道
    private static final int INT_IN_CHANNEL_TYPE = WarehouseChannelType.INBOUND.getValue();
    // 发货通道
    private static final int INT_OUT_CHANNEL_TYPE = WarehouseChannelType.OUTBOUND.getValue();

    /**
     * 数据库已有的收发货通道信息
     */
    private List<WarehouseChannel> warehouseChannelList;

    /**
     * 新增的收发货通道信息
     */
    private List<WarehouseChannel> addList;

    private WarehouseChannel warehouseChannel;
    private OperationUnit ou;

    @Override
    public String execute() {
        ou = getCurrentOu();
        request.put("statusOptionList", dropDownListManager.findStatusChooseOptionList());
        return SUCCESS;
    }

    /**
     * 收获通道列表
     * 
     * @return
     */
    public String inChannel() {
        request.put("statusOptionList", dropDownListManager.findStatusChooseOptionList());
        warehouseChannelList = findWarehouseChannelList(INT_IN_CHANNEL_TYPE, this.getCurrentOu().getId());
        return SUCCESS;
    }

    /**
     * 发货通道列表
     * 
     * @return
     */
    public String outChannel() {
        request.put("statusOptionList", dropDownListManager.findStatusChooseOptionList());
        warehouseChannelList = findWarehouseChannelList(INT_OUT_CHANNEL_TYPE, this.getCurrentOu().getId());
        return SUCCESS;
    }

    /**
     * 收获通道信息的添加、修改
     * 
     * @return
     * @throws Exception
     */
    public String addInChannel() throws Exception {
        List<WarehouseChannel> failures = new ArrayList<WarehouseChannel>();
        failures = channelManager.createUpdateChannelList(getCurrentOu(), INT_IN_CHANNEL_TYPE, warehouseChannelList, addList);
        if (failures != null && !failures.isEmpty()) {
            request.put(JSON, new JSONObject().put("result", failures));
        } else {
            request.put(JSON, new JSONObject().put("result", "修改成功"));
        }
        return JSON;
    }

    /**
     * 发获通道信息的添加、修改
     * 
     * @return
     * @throws Exception
     */
    public String addOutChannel() throws Exception {
        List<WarehouseChannel> failures = new ArrayList<WarehouseChannel>();
        failures = channelManager.createUpdateChannelList(getCurrentOu(), INT_OUT_CHANNEL_TYPE, warehouseChannelList, addList);
        if (failures != null && !failures.isEmpty()) {
            request.put(JSON, new JSONObject().put("result", failures));
        } else {
            request.put(JSON, new JSONObject().put("result", "修改成功"));
        }
        return JSON;
    }



    private List<WarehouseChannel> findWarehouseChannelList(int intType, Long ouid) {
        return channelManager.findWarehouseChannelList(intType, ouid);
    }

    public WarehouseChannel getWarehouseChannel() {
        return warehouseChannel;
    }

    public void setWarehouseChannel(WarehouseChannel warehouseChannel) {
        this.warehouseChannel = warehouseChannel;
    }

    public List<WarehouseChannel> getWarehouseChannelList() {
        return warehouseChannelList;
    }


    public void setWarehouseChannelList(List<WarehouseChannel> warehouseChannelList) {
        this.warehouseChannelList = warehouseChannelList;
    }

    public List<WarehouseChannel> getAddList() {
        return addList;
    }

    public void setAddList(List<WarehouseChannel> addList) {
        this.addList = addList;
    }

    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }
}
