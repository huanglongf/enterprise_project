package com.jumbo.wms.model.command;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jumbo.wms.model.warehouse.InvQstaLineCommand;

public class CheckInventoryResaultCommand implements Serializable {

    private static final long serialVersionUID = -6029114005626222735L;
    private Long qstaId;
    private boolean resaultStatus;
    // private List<InvQstaLineCommand> invDetial;
    private Map<Long, List<InvQstaLineCommand>> invDetial;
    private List<InvQstaLineCommand> errorDetial;

    public Long getQstaId() {
        return qstaId;
    }

    public void setQstaId(Long qstaId) {
        this.qstaId = qstaId;
    }

    public boolean isResaultStatus() {
        return resaultStatus;
    }

    public void setResaultStatus(boolean resaultStatus) {
        this.resaultStatus = resaultStatus;
    }

    public Map<Long, List<InvQstaLineCommand>> getInvDetial() {
        return invDetial;
    }

    public void setInvDetial(Map<Long, List<InvQstaLineCommand>> invDetial) {
        this.invDetial = invDetial;
    }

    public List<InvQstaLineCommand> getErrorDetial() {
        return errorDetial;
    }

    public void setErrorDetial(List<InvQstaLineCommand> errorDetial) {
        this.errorDetial = errorDetial;
    }

}
