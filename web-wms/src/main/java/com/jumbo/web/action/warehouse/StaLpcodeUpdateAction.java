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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 
 * @author dly
 * 
 */
public class StaLpcodeUpdateAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7046618913102206899L;

    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    private String createTime;
    private String endCreateTime;
    private StockTransApplicationCommand sta;
    private List<Long> staIds;
    private String lpcode;
    private Boolean isSelectAll;
    private Long wid;
    private OperationUnit ou;

    public Long getWid() {
        return wid;
    }

    public void setWid(Long wid) {
        this.wid = wid;
    }

    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    public String staLpcodeUpdate() {
        return SUCCESS;
    }

    public String staLpcodeQuery() {
        setTableConfig();
        List<Long> lists = new ArrayList<Long>();
        if (ou == null || ou.getId() == null) {
            if (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.OUTYPE_OPERATION_CENTER)) {
                List<OperationUnit> listopc = authorizationManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
                for (int i = 0; i < listopc.size(); i++) {
                    OperationUnit opc = listopc.get(i);
                    lists.add(opc.getId());
                }
            } else {
                wid = userDetails.getCurrentOu().getId();
            }
        } else {
            wid = ou.getId();
        }
        Pagination<StockTransApplicationCommand> list = wareHouseManager.findCreateOutOrder(tableConfig.getStart(), tableConfig.getPageSize(), wid, getDateByString(createTime), getDateByString(endCreateTime), sta, tableConfig.getSorts(), lists);

        request.put(JSON, toJson(list));
        return JSON;
    }

    public String updateStaLpcode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            List<Long> lists = new ArrayList<Long>();
           if (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.OUTYPE_OPERATION_CENTER)) {
                List<OperationUnit> listopc = authorizationManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
                for (int i = 0; i < listopc.size(); i++) {
                    OperationUnit opc = listopc.get(i);
                    lists.add(opc.getId());
                }
            } else {
                wid = userDetails.getCurrentOu().getId();
            }
           String status = wareHouseManagerExe.updateStaLpcode(wid, lpcode, getDateByString(createTime), getDateByString(endCreateTime), sta, staIds, isSelectAll, lists);
           if(StringUtils.hasText(status)){
        	   result.put("result", ERROR);
        	   result.put("message", getMessage("单据已经存在运单号不允许修改"));
           }else{
        	   result.put("result", SUCCESS);
           }
           
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    public Date getDateByString(String date) {
        if (date != null && !"".equals(date)) {
            try {
                return FormatUtil.stringToDate(date);
            } catch (ParseException e) {
                log.error("", e);
            }
        }
        return null;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public StockTransApplicationCommand getSta() {
        return sta;
    }

    public void setSta(StockTransApplicationCommand sta) {
        this.sta = sta;
    }

    public List<Long> getStaIds() {
        return staIds;
    }

    public void setStaIds(List<Long> staIds) {
        this.staIds = staIds;
    }

    public Boolean getIsSelectAll() {
        return isSelectAll;
    }

    public void setIsSelectAll(Boolean isSelectAll) {
        this.isSelectAll = isSelectAll;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }
}
