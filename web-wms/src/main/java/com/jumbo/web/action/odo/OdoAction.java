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
package com.jumbo.web.action.odo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.odo.OdoManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.odo.OdoCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;

import loxia.dao.Pagination;
import loxia.support.json.JSONObject;

/**
 * 临时库间移动
 * 
 * @author hui.li
 *
 */
public class OdoAction extends BaseJQGridProfileAction {
    /**
     * 
     */
    private static final long serialVersionUID = 2353098582790545212L;

    protected static final Logger logger = LoggerFactory.getLogger(OdoAction.class);

    private OdoCommand odoCommand;
    @Autowired
    private OdoManager odoManager;

    private File file;

    private Long ouId;

    private Long targetId;

    private Long invStatusId;

    @Autowired
    private ExcelReadManager excelReadManager;

    /**
     * 仓库下拉列表
     */
    private List<OperationUnit> whList;
    /**
     * 
     */
    private List<InventoryStatus> invList;

    @Autowired
    private DropDownListManager dropDownManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;

    private String owner;

    private Long odoId;

    public String initOdOQueryPage() {
        invList = dropDownManager.findInvStatusListByCompany(userDetails.getCurrentOu().getId(), null);
        // whList = dropDownManager.findWarehouseOuListByComOuId(userDetails.getCurrentOu().getId(),
        // null);
        whList = wareHouseManagerQuery.fandOperationUnitsByType("Warehouse");
        return SUCCESS;
    }

    public String findWarehouseOuListByOuId() {
        List<OperationUnit> ouList = odoManager.findWarehouseOuListByOuId(ouId);
        request.put(JSON, JsonUtil.collection2json(ouList));
        return JSON;
    }

    public String findInvStatusListByCompany() {
        List<InventoryStatus> isList = odoManager.findInvStatusListByCompany(ouId);
        request.put(JSON, JsonUtil.collection2json(isList));
        return JSON;
    }

    public String findOdoByParams() {
        setTableConfig();
        Map<String, Object> params = new HashMap<String, Object>();
        if (odoCommand != null) {
            if (!"".equals(odoCommand.getCode())) {
                params.put("code", odoCommand.getCode());
            }
        }


        Pagination<OdoCommand> zoonList = odoManager.findOdoByParams(tableConfig.getStart(), tableConfig.getPageSize(), params);
        request.put(JSON, toJson(zoonList));
        return JSON;
    }


    public String importOdoLine() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        }
        try {
            String msg = excelReadManager.importOdoLine(file, ouId, targetId, owner, this.userDetails.getUser().getId(), invStatusId, odoId);

            if (StringUtil.isEmpty(msg)) {
                request.put("result", SUCCESS);
                request.put(Constants.IMPORT_EXL_RESULT, "导入成功！");
            } else {
                request.put("result", ERROR);
                errorMsg.add(msg);
            }
        } catch (BusinessException e) {
            request.put("result", ERROR);
            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            errorMsg.add(msg);
            log.error(msg);
        } catch (Exception e) {
            request.put("result", ERROR);
            errorMsg.add(e.getMessage());
            log.error("importOdoLine error", e);
        }
        request.put("message", JsonUtil.collection2json(errorMsg));
        return SUCCESS;
    }

    public String createOutStaByOdoId() throws Exception {
        JSONObject result = new JSONObject();
        try {

            String msg = odoManager.createOdoOutStaById(odoId);
            if (StringUtil.isEmpty(msg)) {
                result.put("msg", SUCCESS);
            } else {
                result.put("msg", msg);

            }
        } catch (Exception e) {
            result.put("msg", e.getMessage());
            logger.error("createOutStaByOdoId error:", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public OdoCommand getOdoCommand() {
        return odoCommand;
    }

    public void setOdoCommand(OdoCommand odoCommand) {
        this.odoCommand = odoCommand;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<OperationUnit> getWhList() {
        return whList;
    }

    public void setWhList(List<OperationUnit> whList) {
        this.whList = whList;
    }

    public List<InventoryStatus> getInvList() {
        return invList;
    }

    public void setInvList(List<InventoryStatus> invList) {
        this.invList = invList;
    }

    public Long getOdoId() {
        return odoId;
    }

    public void setOdoId(Long odoId) {
        this.odoId = odoId;
    }



}
