package com.jumbo.wms.model.pda;

import java.util.Date;



/**
 * 
 * @author lizaibiao 作业单操作明细日志command
 * 
 */

public class StaOpLogCommand2 extends StaOpLog {
    private static final long serialVersionUID = 468893406460386150L;
    private Long cOuId;// 组织id
    private Long cStaId;// 作业单id
    private Long carId2;// 箱id
    private Long skuId2;// 商品id
    private String cartonCode2;// 箱号
    private Date expDate2;// 过期时间
    private Long qty2;// 数量

    public Long getcOuId() {
        return cOuId;
    }

    public void setcOuId(Long cOuId) {
        this.cOuId = cOuId;
    }

    public Long getcStaId() {
        return cStaId;
    }

    public void setcStaId(Long cStaId) {
        this.cStaId = cStaId;
    }

    public Long getCarId2() {
        return carId2;
    }

    public void setCarId2(Long carId2) {
        this.carId2 = carId2;
    }

    public Long getSkuId2() {
        return skuId2;
    }

    public void setSkuId2(Long skuId2) {
        this.skuId2 = skuId2;
    }

    public String getCartonCode2() {
        return cartonCode2;
    }

    public void setCartonCode2(String cartonCode2) {
        this.cartonCode2 = cartonCode2;
    }

    public Date getExpDate2() {
        return expDate2;
    }

    public void setExpDate2(Date expDate2) {
        this.expDate2 = expDate2;
    }

    public Long getQty2() {
        return qty2;
    }

    public void setQty2(Long qty2) {
        this.qty2 = qty2;
    }
}
