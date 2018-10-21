package com.jumbo.web.action.warehouse;

import loxia.dao.Pagination;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.manager.warehouse.ConsumptiveMaterialUseQueryCommandManager;
import com.jumbo.wms.model.command.ConsumptiveMaterialUseQueryCommand;

/**
 * 耗材使用情况查询控制器
 * 
 * @author jinlong.ke
 * 
 */
public class ConsumptiveMaterialUseQueryAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 496387826273566330L;
    @Autowired
    private ConsumptiveMaterialUseQueryCommandManager cmManager;
    private ConsumptiveMaterialUseQueryCommand cm;
    /**
     * 开始时间
     */
    private String fromDate;
    /**
     * 结束时间
     */
    private String toDate;

    /**
     * 实现简单页面跳转
     * 
     * @return
     */
    public String redirect() {
        return SUCCESS;
    }

    /**
     * 分页查询耗材使用情况
     * 
     * @return
     */
    public String findAllCMUserList() {
        setTableConfig();
        Pagination<ConsumptiveMaterialUseQueryCommand> cmList =
                cmManager.findCmUseList(tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.getDate(fromDate), FormatUtil.getDate(toDate), cm, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(cmList));
        return JSON;
    }

    public ConsumptiveMaterialUseQueryCommand getCm() {
        return cm;
    }

    public void setCm(ConsumptiveMaterialUseQueryCommand cm) {
        this.cm = cm;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}
