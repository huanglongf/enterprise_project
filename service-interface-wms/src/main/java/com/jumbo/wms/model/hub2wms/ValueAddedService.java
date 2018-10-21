package com.jumbo.wms.model.hub2wms;

import com.jumbo.wms.model.BaseModel;

/**
 * HUB2WMS过仓
 * 
 * @author jinlong.ke
 * 
 */
public class ValueAddedService extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 9122939201992868903L;

    /*
     * 类型 
     */
    private int type;
    /*
     * 卡号
     */
    private String cardCode;
    /*
     * 备注
     */
    private String memo;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


}
