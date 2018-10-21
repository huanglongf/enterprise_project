package com.jumbo.wms.model.vmi.itData;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VMIITReceiveCommand implements Serializable {



    private static final long serialVersionUID = 6292990794838660872L;

    private String toLocation;

    private Date txDate;

    private String stdNO;

    private String userKo;

    private String tranid;

    private BigDecimal quantity;

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    public String getStdNO() {
        return stdNO;
    }

    public void setStdNO(String stdNO) {
        this.stdNO = stdNO;
    }

    public String getUserKo() {
        return userKo;
    }

    public void setUserKo(String userKo) {
        this.userKo = userKo;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }



}
