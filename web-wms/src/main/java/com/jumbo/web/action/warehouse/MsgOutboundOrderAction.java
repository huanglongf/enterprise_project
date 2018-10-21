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
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.DateUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseOutBoundManager;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundCommand2;

public class MsgOutboundOrderAction extends BaseJQGridProfileAction {


    /**
	 * 
	 */
    private static final long serialVersionUID = 9055643243887549137L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private DropDownListManager dropDownListManager;
    @Autowired
    private WareHouseOutBoundManager warehouseOutBoundManager;

    private MsgOutboundOrderCommand msgCmd;

    private MsgRtnOutboundCommand2 msgRtnCmd;

    private List<ChooseOption> msgStatus;

    private String startDate;

    private String endDate;

    private Long ouId;

    /**
     * 批次号
     */
    private String batchCode;

    /**
     * 页面跳转
     * 
     * @return
     */
    public String pageRedirect() {
        return SUCCESS;
    }

    public String msgOutboundOrderQueryEntry() {
        msgStatus = this.dropDownListManager.findMsgOutboundOrderStatusOptionList();
        return SUCCESS;
    }

    /**
     * 查询信息
     * 
     * @return
     * @throws Exception
     */
    public String findCurrentMsgOutboundOrder() throws ParseException {
        setTableConfig();
        Long whId = userDetails.getCurrentOu().getId();
        msgCmd.setStartDate(dateFormat(startDate));
        msgCmd.setEndDate(dateFormat(endDate));
        Pagination<MsgOutboundOrderCommand> msgList = wareHouseManager.findCurrentMsgOutboundOrderByPage(tableConfig.getStart(), tableConfig.getPageSize(), msgCmd, whId, tableConfig.getSorts());
        request.put(JSON, toJson(msgList));
        return JSON;
    }

    /**
     * 查询信息(集团下)
     * 
     * @return
     * @throws Exception
     */
    public String findCurrentMsgOutboundOrderRoot() throws ParseException {
        setTableConfig();
        // Long whId = userDetails.getCurrentOu().getId();
        msgCmd.setStartDate(dateFormat(startDate));
        msgCmd.setEndDate(dateFormat(endDate));
        Pagination<MsgOutboundOrderCommand> msgList = wareHouseManager.findCurrentMsgOutboundOrderByPageRoot(tableConfig.getStart(), tableConfig.getPageSize(), msgCmd, ouId, tableConfig.getSorts());
        request.put(JSON, toJson(msgList));
        return JSON;
    }

    /**
     * 查询信息
     * 
     * @return
     * @throws Exception
     */
    public String findCurrentMsgRtnOutbound() throws ParseException {
        setTableConfig();
        Long whId = userDetails.getCurrentOu().getId();
        msgRtnCmd.setStartDate(dateFormat(startDate));
        msgRtnCmd.setEndDate(dateFormat(endDate));
        Pagination<MsgRtnOutboundCommand2> msgList = wareHouseManager.findCurrentMsgRtnOutboundByPage(tableConfig.getStart(), tableConfig.getPageSize(), msgRtnCmd, whId, tableConfig.getSorts());
        request.put(JSON, toJson(msgList));
        return JSON;
    }


    /**
     * 查询信息
     * 
     * @return
     * @throws Exception
     */
    public String findCurrentMsgRtnOutboundRoot() throws ParseException {
        setTableConfig();
        // Long whId = userDetails.getCurrentOu().getId();
        msgRtnCmd.setStartDate(dateFormat(startDate));
        msgRtnCmd.setEndDate(dateFormat(endDate));
        Pagination<MsgRtnOutboundCommand2> msgList = wareHouseManager.findCurrentMsgRtnOutboundByPageRoot(tableConfig.getStart(), tableConfig.getPageSize(), msgRtnCmd, ouId, tableConfig.getSorts());
        request.put(JSON, toJson(msgList));
        return JSON;
    }

    /**
     * 查询日期format
     * 
     * @return
     */
    private Date dateFormat(String dateStr) {
        if (null == dateStr || "".equals(dateStr)) {
            return null;
        }
        Date sDat;
        try {
            sDat = FormatUtil.stringToDate(dateStr);
        } catch (ParseException e) {
            return null;
        }
        boolean flag = DateUtil.isYearDate(dateStr);
        if (flag) {
            // 日期为"yyy-mm-dd"默认补加一天
            sDat = DateUtil.addDays(sDat, 1);
        }
        return sDat;
    }

    /**
     * LEVIS销售接口卡单重置批次号 0：查无此批次号；1：重置成功 ；2：非配货状态的批次不能重置！
     * 
     * @return
     */
    public String resetMsgOutboundOrderList() {
        JSONObject result = new JSONObject();
        try {
            if (org.apache.commons.lang3.StringUtils.isBlank(batchCode)) {
                result.put("msg", "输入的批次号不能为空！");
            }
            try {
                Long.parseLong(batchCode);
            } catch (Exception e) {
                result.put("msg", "输入的批次号格式不正确！");
            }
            int status = warehouseOutBoundManager.resetMsgOutboundOrderStatus(batchCode);
            switch (status) {
                case 0:
                    result.put("msg", "查无此批次号！");
                    break;
                case 1:
                    result.put("msg", "重置成功！");
                    break;
                case 2:
                    result.put("msg", "该批次号匹配的波次存在非已发送的状态，不能重置！");
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            log.error("LEVIS销售接口卡单重置批次号失败！", e);
            try {
                result.put("msg", "重置批次状态失败！");
            } catch (Exception e1) {
                // e1.printStackTrace();
                if (log.isErrorEnabled()) {
                    log.error("resetMsgOutboundOrderList error:" + batchCode, e);
                };
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    public MsgOutboundOrderCommand getMsgCmd() {
        return msgCmd;
    }

    public void setMsgCmd(MsgOutboundOrderCommand msgCmd) {
        this.msgCmd = msgCmd;
    }

    public List<ChooseOption> getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(List<ChooseOption> msgStatus) {
        this.msgStatus = msgStatus;
    }

    public MsgRtnOutboundCommand2 getMsgRtnCmd() {
        return msgRtnCmd;
    }

    public void setMsgRtnCmd(MsgRtnOutboundCommand2 msgRtnCmd) {
        this.msgRtnCmd = msgRtnCmd;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

}
