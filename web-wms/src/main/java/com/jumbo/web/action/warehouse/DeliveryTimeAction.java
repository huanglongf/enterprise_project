package com.jumbo.web.action.warehouse;


import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.CreatePickingListManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseOutBoundManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

/**
 * 
 * @author cheng.su
 * 
 */
public class DeliveryTimeAction extends BaseJQGridProfileAction implements ServletResponseAware {
    /**
	 * 
	 */
    private static final long serialVersionUID = 7798565686459022426L;
    @Autowired
    private WareHouseOutBoundManager warehouseOutBoundManager;
    @Autowired
    private CreatePickingListManagerProxy pickingListManagerProxy;
    /**
     * 组织
     */
    private OperationUnit ou;
    /**
     * 运单时限类型
     */
    private Integer transTimeType;
    /**
     * 需要配货作业单id集合
     */
    private List<Long> staIdList;
    private StockTransApplicationCommand sta;
    private Date fromDate;
    private Date toDate;



    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public StockTransApplicationCommand getSta() {
        return sta;
    }

    public void setSta(StockTransApplicationCommand sta) {
        this.sta = sta;
    }

    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    public Integer getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(Integer transTimeType) {
        this.transTimeType = transTimeType;
    }

    public List<Long> getStaIdList() {
        return staIdList;
    }

    public void setStaIdList(List<Long> staIdList) {
        this.staIdList = staIdList;
    }

    public String deliveryTimeList() {
        return SUCCESS;
    }

    /**
     * 查询待升单单据
     * 
     * @return
     */
    public String findStaLsingle() {
        setTableConfig();
        Long ouId = userDetails.getCurrentOu().getId();
        if (ou != null) {
            ouId = ou.getId();
        }
        Pagination<StockTransApplicationCommand> stas = warehouseOutBoundManager.findStaLsingle(tableConfig.getStart(), tableConfig.getPageSize(), sta, transTimeType, fromDate, toDate, ouId, tableConfig.getSorts());
        request.put(JSON, toJson(stas));
        return JSON;
    }

    /**
     * 手动升单
     * 
     * @return
     * @throws JSONException
     */
    public String generStaLsingle() throws JSONException {
        JSONObject result = new JSONObject();
        Long ouId = userDetails.getCurrentOu().getId();
        if (ou != null) {
            ouId = ou.getId(); // isCod
        }
        try {
            pickingListManagerProxy.generStaLsingle(staIdList, ouId, userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("message", errorMsg);
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 不升单
     * 
     * @return
     * @throws JSONException
     */
    public String outGenerStaLsingle() throws JSONException {
        JSONObject result = new JSONObject();
        Long ouId = userDetails.getCurrentOu().getId();
        if (ou != null) {
            ouId = ou.getId(); // isCod
        }
        try {
            pickingListManagerProxy.outGenerStaLsingle(staIdList, ouId, userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("message", errorMsg);
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

}
