package com.jumbo.wms.model.vmi.warehouse;

public class MsgRtnInboundOrderLineCommand extends MsgRtnInboundOrderLine {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2067677473257735807L;

    private Long invStatusId;

    private Long msgRtnInorderId;

    public Long getInvStatusId() {
        return invStatusId;
    }

    public void setInvStatusId(Long invStatusId) {
        this.invStatusId = invStatusId;
    }

    public Long getMsgRtnInorderId() {
        return msgRtnInorderId;
    }

    public void setMsgRtnInorderId(Long msgRtnInorderId) {
        this.msgRtnInorderId = msgRtnInorderId;
    }

}
