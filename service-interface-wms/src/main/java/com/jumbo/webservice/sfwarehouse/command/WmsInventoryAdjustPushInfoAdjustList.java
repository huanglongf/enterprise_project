package com.jumbo.webservice.sfwarehouse.command;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 
 * @author jinlong.ke
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class WmsInventoryAdjustPushInfoAdjustList implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -3858887526547289147L;
    private List<WmsInventoryAdjustPushInfoAdjust> adjust;

    public List<WmsInventoryAdjustPushInfoAdjust> getAdjust() {
        return adjust;
    }

    public void setAdjust(List<WmsInventoryAdjustPushInfoAdjust> adjust) {
        this.adjust = adjust;
    }
}
