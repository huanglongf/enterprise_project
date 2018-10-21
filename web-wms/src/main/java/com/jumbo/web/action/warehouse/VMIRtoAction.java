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

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.VMIRtoManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.vmi.Default.VmiRtoCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckCommand;

/**
 * 
 * @author lichuan
 * 
 */
public class VMIRtoAction extends BaseJQGridProfileAction implements ServletResponseAware {

    private static final long serialVersionUID = -8490931042580985736L;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private VMIRtoManager vmiRtoManager;

    private Long invcheckid;
    private InventoryCheckCommand iccommand;
    private VmiRtoCommand cmd;

    public String vmiRtoQueryEntry() {
        return SUCCESS;
    }

    public String findVMIAllInventoryCheckList() {
        setTableConfig();
        if (iccommand != null) {
            if (iccommand.getStartDate1() != null) {
                iccommand.setStartDate(FormatUtil.getDate(iccommand.getStartDate1()));
            }
            if (iccommand.getEndDate1() != null) {
                iccommand.setEndDate(FormatUtil.getDate(iccommand.getEndDate1()));
            }
        }
        Pagination<InventoryCheckCommand> inventoryCheckList = wareHouseManagerQuery.findAllVMIInventoryCheckList(tableConfig.getStart(), tableConfig.getPageSize(), iccommand, this.userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(inventoryCheckList));
        return JSON;
    }

    public String findVmiRtoList() {
        setTableConfig();
        if (cmd != null) {
            if (cmd.getStartDate1() != null) {
                cmd.setStartDate(FormatUtil.getDate(cmd.getStartDate1()));
            }
            if (cmd.getEndDate1() != null) {
                cmd.setEndDate(FormatUtil.getDate(cmd.getEndDate1()));
            }
        }
        Pagination<VmiRtoCommand> list = vmiRtoManager.findVmiRtoList(tableConfig.getStart(), tableConfig.getPageSize(), cmd, this.userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String findVmiRtoLineList() {
        setTableConfig();
        List<VmiRtoLineCommand> list = vmiRtoManager.findVmiRtoLineList(cmd);
        // request.put(JSON, JsonUtil.collection2json(list));
        request.put(JSON, toJson(list));
        return JSON;
    }

    public Long getInvcheckid() {
        return invcheckid;
    }

    public void setInvcheckid(Long invcheckid) {
        this.invcheckid = invcheckid;
    }

    public InventoryCheckCommand getIccommand() {
        return iccommand;
    }

    public void setIccommand(InventoryCheckCommand iccommand) {
        this.iccommand = iccommand;
    }

    public VmiRtoCommand getCmd() {
        return cmd;
    }

    public void setCmd(VmiRtoCommand cmd) {
        this.cmd = cmd;
    }

}
