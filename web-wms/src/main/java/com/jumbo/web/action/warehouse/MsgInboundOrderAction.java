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
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class MsgInboundOrderAction extends BaseJQGridProfileAction {


    /**
	 * 
	 */
    private static final long serialVersionUID = 9055643243887549137L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private DropDownListManager dropDownListManager;

    private MsgInboundOrderCommand msgCmd;

    private MsgRtnInboundOrderCommand msgRtnCmd;

    private List<ChooseOption> msgStatus;

    private List<ChooseOption> msgRtnStatus;

    private Long rtnInId;
    private Long ouId;

    public String msgInboundOrderQueryEntry() {
        msgStatus = this.dropDownListManager.findMsgOutboundOrderStatusOptionList();
        msgRtnStatus = this.dropDownListManager.findMsgInboundOrderStatusOptionList();
        return SUCCESS;
    }

    /**
     * 查询信息
     * 
     * @return
     * @throws Exception
     */
    public String findCurrentMsgInboundOrder() throws ParseException {
        setTableConfig();
        Long whId = userDetails.getCurrentOu().getId();
        if (msgCmd != null) {
            msgCmd.setStartDate(FormatUtil.getDate(msgCmd.getStartDate1()));
            msgCmd.setEndDate(FormatUtil.getDate(msgCmd.getEndDate1()));
        }
        Pagination<MsgInboundOrderCommand> msgList = wareHouseManager.findCurrentMsgInboundOrderByPage(tableConfig.getStart(), tableConfig.getPageSize(), msgCmd, whId, tableConfig.getSorts());
        request.put(JSON, toJson(msgList));
        return JSON;
    }
    
    /**
     * 查询信息
     * 
     * @return
     * @throws Exception
     */
    public String findCurrentMsgInboundOrderRoot() throws ParseException {
        setTableConfig();
//        Long whId = userDetails.getCurrentOu().getId();
        if (msgCmd != null) {
            msgCmd.setStartDate(FormatUtil.getDate(msgCmd.getStartDate1()));
            msgCmd.setEndDate(FormatUtil.getDate(msgCmd.getEndDate1()));
        }
        Pagination<MsgInboundOrderCommand> msgList = wareHouseManager.findCurrentMsgInboundOrderByPageRoot(tableConfig.getStart(), tableConfig.getPageSize(), msgCmd, ouId, tableConfig.getSorts());
        request.put(JSON, toJson(msgList));
        return JSON;
    }

    /**
     * 查询信息
     * 
     * @return
     * @throws Exception
     */
    public String findCurrentMsgRtnInbound() throws ParseException {
        setTableConfig();
        Long whId = userDetails.getCurrentOu().getId();
        if (msgRtnCmd != null) {
            msgRtnCmd.setStartDate(FormatUtil.getDate(msgRtnCmd.getStartDate1()));
            msgRtnCmd.setEndDate(FormatUtil.getDate(msgRtnCmd.getEndDate1()));
        }
        Pagination<MsgRtnInboundOrderCommand> msgList = wareHouseManager.findCurrentMsgRtnInboundByPage(tableConfig.getStart(), tableConfig.getPageSize(), msgRtnCmd, whId, tableConfig.getSorts());
        request.put(JSON, toJson(msgList));
        return JSON;
    }
    
    /**
     * 查询信息
     * 
     * @return
     * @throws Exception
     */
    public String findCurrentMsgRtnInboundRoot() throws ParseException {
        setTableConfig();
        if (msgRtnCmd != null) {
            msgRtnCmd.setStartDate(FormatUtil.getDate(msgRtnCmd.getStartDate1()));
            msgRtnCmd.setEndDate(FormatUtil.getDate(msgRtnCmd.getEndDate1()));
        }
        Pagination<MsgRtnInboundOrderCommand> msgList = wareHouseManager.findCurrentMsgRtnInboundByPageRoot(tableConfig.getStart(), tableConfig.getPageSize(), msgRtnCmd, ouId, tableConfig.getSorts());
        request.put(JSON, toJson(msgList));
        return JSON;
    }

    /**
     * 根据id获取反馈清单信息
     * 
     * @return
     */
    public String findRtnInboundDetailById() {
        JSONObject json = new JSONObject();
        try {
            json.put("rtnInbounDetail", JsonUtil.obj2json(wareHouseManager.getMsgRtnInboundOrder(rtnInId)));
        } catch (Exception e) {
            log.error("",e);
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 查询信息
     * 
     * @return
     * @throws Exception
     */
    public String findMsgRtnInboundLineById() throws ParseException {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findRtnLineByRtnListId(rtnInId, tableConfig.getSorts())));
        return JSON;
    }

    public MsgInboundOrderCommand getMsgCmd() {
        return msgCmd;
    }

    public void setMsgCmd(MsgInboundOrderCommand msgCmd) {
        this.msgCmd = msgCmd;
    }

    public List<ChooseOption> getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(List<ChooseOption> msgStatus) {
        this.msgStatus = msgStatus;
    }

    public MsgRtnInboundOrderCommand getMsgRtnCmd() {
        return msgRtnCmd;
    }

    public void setMsgRtnCmd(MsgRtnInboundOrderCommand msgRtnCmd) {
        this.msgRtnCmd = msgRtnCmd;
    }

    public Long getRtnInId() {
        return rtnInId;
    }

    public void setRtnInId(Long rtnInId) {
        this.rtnInId = rtnInId;
    }

    public List<ChooseOption> getMsgRtnStatus() {
        return msgRtnStatus;
    }

    public void setMsgRtnStatus(List<ChooseOption> msgRtnStatus) {
        this.msgRtnStatus = msgRtnStatus;
    }

	public Long getOuId() {
		return ouId;
	}

	public void setOuId(Long ouId) {
		this.ouId = ouId;
	}
    
}
