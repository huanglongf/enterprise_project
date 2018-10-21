package com.jumbo.web.action.warehouse;

import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONObject;


import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.model.pda.PdaHandOverLine;
import com.jumbo.wms.model.pda.PdaOrder;
import com.jumbo.wms.model.pda.PdaOrderCommand;
import com.jumbo.wms.model.pda.PdaOrderLine;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.webservice.pda.manager.PdaOperationManager;

/**
 * PDA操作日志逻辑控制
 * 
 * @author jinlong.ke
 * 
 */
public class PDALogAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -656325704116312908L;
    @Autowired
    private PdaOperationManager pdaOperationManagerImpl;
    private PdaOrder pda;
    private PdaOrderCommand pc;

    /**
     * 跳转页面
     * 1、PDA操作日志查询页面
     * 2、PDA失败调整
     * 
     * @return
     */
    public String pageRedirect() {
        return SUCCESS;
    }

    /**
     * 查询Pda操作日志列表
     * 
     * @return
     */
    public String getPdaLogList() {
        setTableConfig();
        Pagination<PdaOrderCommand> stas = pdaOperationManagerImpl.findStaForPickingByModel(tableConfig.getStart(), tableConfig.getPageSize(),
        		FormatUtil.getDate(pc.getBeginTime()),FormatUtil.getDate(pc.getBeginTime1()),FormatUtil.getDate(pc.getEndTime()),
        		FormatUtil.getDate(pc.getEndTime1()),pc.getStatus(),pc.getType(),pc.getOrderCode(),userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(stas));
        return JSON;
    }

    /**
     * 查询Pda操作日志明细行
     * 
     * @return
     */
    public String findPdaLogLine() {
        setTableConfig();
        Pagination<PdaOrderLine> stas = pdaOperationManagerImpl.findPdaLogLine(tableConfig.getStart(), tableConfig.getPageSize(), pda.getId(), tableConfig.getSorts());
        request.put(JSON, toJson(stas));
        return JSON;
    }
    /**
     * 查询pda上传物流交接明细行数据
     * @return
     */
    public String findPdaHandOverLogLine(){
        setTableConfig();
        List<PdaHandOverLine> stas = pdaOperationManagerImpl.findPdaHandOverLogLine(pda.getId(),tableConfig.getSorts());
        request.put(JSON, toJson(stas));
        return JSON;
    }
    /**
     * 查询pda出错单据
     * @return
     */
    public String getErrorPdaLogList(){
        setTableConfig();
        Pagination<PdaOrder> pos = pdaOperationManagerImpl.getErrorPdaLogList(tableConfig.getStart(),tableConfig.getPageSize(),FormatUtil.getDate(pc.getBeginTime()),FormatUtil.getDate(pc.getBeginTime1()),pc.getOrderCode(),userDetails.getCurrentOu().getId(),tableConfig.getSorts());
        request.put(JSON, toJson(pos));
        return JSON;
    }
    /**
     * 更新PDA错误订单状态为新建
     * @return
     */
    public String updatePdaOrderStatus(){
        JSONObject result = new JSONObject();
        try{
            result.put("flag", "false");
            pdaOperationManagerImpl.updatePdaOrderStatus(pc.getId());
            result.put("flag", "true");
        }catch(Exception e){
            log.error(e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }
    /**
     * 获得错误单据明细行
     * @return
     */
    public String getErrorPdaLogDetailList(){
        setTableConfig();
        List<PdaOrderLine> plList = pdaOperationManagerImpl.getErrorPdaLogDetailList(pc.getId(),tableConfig.getSorts());
        request.put(JSON, toJson(plList));
        return JSON;
    }
    /**
     * 
     * @return
     */
    public String updateErrorPdaLineLocation(){
        JSONObject result = new JSONObject();
        try{
            result.put("result", "error");
            pdaOperationManagerImpl.updateErrorPdaLineLocation(pc.getId(),pc.getOrderCode());
            result.put("result", "success");
        }catch(Exception e){
            log.error("",e);
        }
        request.put(JSON, result);
        return JSON;
    }
    public PdaOrder getPda() {
        return pda;
    }

    public void setPda(PdaOrder pda) {
        this.pda = pda;
    }

    public PdaOrderCommand getPc() {
        return pc;
    }

    public void setPc(PdaOrderCommand pc) {
        this.pc = pc;
    }

}
