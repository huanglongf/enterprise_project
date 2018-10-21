package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * ad异常包裹处理
 * 
 * @author qian.Wang
 * 
 */

public class AdPackageLineDealDto extends AdPackageLineDeal{
    private static final long serialVersionUID = -3909252824545611972L;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOpStatusName() {
        return opStatusName;
    }

    public void setOpStatusName(String opStatusName) {
        this.opStatusName = opStatusName;
    }

    private String statusName;
    
    private String opStatusName;



    private String skuCode;

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }
   
}
