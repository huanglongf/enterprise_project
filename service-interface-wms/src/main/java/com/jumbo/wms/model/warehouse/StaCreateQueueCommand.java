package com.jumbo.wms.model.warehouse;

import java.util.ArrayList;
import java.util.List;



public class StaCreateQueueCommand extends StaCreateQueue {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5962742340425458961L;

    private Integer intStatus;

    private Integer intType;

    private List<Integer> intStatusList = new ArrayList<Integer>();

    public Integer getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }

    public Integer getIntType() {
        return intType;
    }

    public void setIntType(Integer intType) {
        this.intType = intType;
    }

    public List<Integer> getIntStatusList() {
        return intStatusList;
    }

    public void setIntStatusList(List<Integer> intStatusList) {
        this.intStatusList = intStatusList;
    }

    public void initValues() {
        if (getSlipCode() != null && "".equals(getSlipCode())) {
            setSlipCode(null);
        }
    }
}
