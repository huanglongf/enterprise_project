package com.jumbo.web.action.warehouse;

import loxia.dao.Pagination;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 取消订单提交至外包仓状态查询
 * 
 * @author wudan
 * 
 */
public class MsgOutboundOrderCancelAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 362252373640943964L;

    @Autowired
    private WareHouseManager wareHouseManager;

    private MsgOutboundOrderCancelCommand msgout;
    
    private Long ouId;

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public String findOutboundOrderCancelList() {
        setTableConfig();
        if (msgout != null) {
            msgout.setStarteDate(FormatUtil.getDate(msgout.getStarteDate1()));
            msgout.setEndDate(FormatUtil.getDate(msgout.getEndDate1()));
        }
        Pagination<MsgOutboundOrderCancelCommand> o = wareHouseManager.findOutboundOrderCancelList(this.userDetails.getCurrentOu().getId(), msgout, tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts());
        request.put(JSON, toJson(o));
        return JSON;
    }
    
    public String findOutboundOrderCancelListRoot() {
        setTableConfig();
        if (msgout != null) {
            msgout.setStarteDate(FormatUtil.getDate(msgout.getStarteDate1()));
            msgout.setEndDate(FormatUtil.getDate(msgout.getEndDate1()));
        }
        Pagination<MsgOutboundOrderCancelCommand> o = wareHouseManager.findOutboundOrderCancelListRoot(msgout.getOuId(), msgout, tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts());
        request.put(JSON, toJson(o));
        return JSON;
    }

    public MsgOutboundOrderCancelCommand getMsgout() {
        return msgout;
    }

    public void setMsgout(MsgOutboundOrderCancelCommand msgout) {
        this.msgout = msgout;
    }

	public Long getOuId() {
		return ouId;
	}

	public void setOuId(Long ouId) {
		this.ouId = ouId;
	}
    
}
