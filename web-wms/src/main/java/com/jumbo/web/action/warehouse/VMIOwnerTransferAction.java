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
import java.util.ArrayList;
import java.util.List;

import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;

import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class VMIOwnerTransferAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3174070359613322211L;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private ExcelReadManager excelReadManager;

    private File file;
    // 转出店铺
    private Long ownerid;
    // 转入店铺
    private Long addiownerid;
    // 库存状态id
    private Long invstatusId;

    private List<InventoryStatus> invstatus;

    // vmi转店-创建
    public String createEntry() {
        Long companyOuId = this.userDetails.getCurrentOu().getParentUnit().getParentUnit().getId();
        invstatus = wareHouseManager.findInvStatusListByCompany(companyOuId);
        return SUCCESS;
    }

    /**
     * vmi 转店
     * 
     * @return
     */
    public String vmiTransferImport() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                Long cmpOuid = userDetails.getCurrentOu().getParentUnit().getParentUnit().getId();
                StockTransApplication sta = new StockTransApplication();
                sta.setType(StockTransApplicationType.VMI_OWNER_TRANSFER);
                ReadStatus rs = excelReadManager.createStaForVMITransfer(sta, file, ownerid, addiownerid, userDetails.getCurrentOu().getId(), cmpOuid, userDetails.getUser().getId(), invstatusId);
                if (rs != null) if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    request.put("msg", list);
                } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = new ArrayList<String>();
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException be = (BusinessException) ex;
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
                        }
                    }
                    request.put("msg", list);
                }
            } catch (BusinessException e) {
                request.put("msg", "error");
                log.error("",e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("msg", "error");
                log.error("",e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    // vmi转店 - 执行
    public String executeEntry() {
        return SUCCESS;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(Long ownerid) {
        this.ownerid = ownerid;
    }

    public Long getAddiownerid() {
        return addiownerid;
    }

    public void setAddiownerid(Long addiownerid) {
        this.addiownerid = addiownerid;
    }

    public List<InventoryStatus> getInvstatus() {
        return invstatus;
    }

    public void setInvstatus(List<InventoryStatus> invstatus) {
        this.invstatus = invstatus;
    }

    public Long getInvstatusId() {
        return invstatusId;
    }

    public void setInvstatusId(Long invstatusId) {
        this.invstatusId = invstatusId;
    }
}
