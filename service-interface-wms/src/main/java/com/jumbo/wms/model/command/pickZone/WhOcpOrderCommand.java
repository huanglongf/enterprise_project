package com.jumbo.wms.model.command.pickZone;

import java.io.Serializable;
import java.util.List;

import com.jumbo.wms.model.pickZone.WhOcpOrder;

public class WhOcpOrderCommand extends WhOcpOrder implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2586271153858380419L;
    /**
     * 仓库组织
     */
    private Long ouId;

    private List<WhOcpOrderLineCommand> woolcList;

    private List<Long> woolIds;

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public List<WhOcpOrderLineCommand> getWoolcList() {
        return woolcList;
    }

    public void setWoolcList(List<WhOcpOrderLineCommand> woolcList) {
        this.woolcList = woolcList;
    }

    public List<Long> getWoolIds() {
        return woolIds;
    }

    public void setWoolIds(List<Long> woolIds) {
        this.woolIds = woolIds;
    }


	
}