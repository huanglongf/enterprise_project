package com.jumbo.wms.model.vmi.order;

/**
 * 
 * @author wudan
 * 
 */
public class VMIOrderCommand extends VMIOrder {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6512081110484450629L;


    private String reserve1;

    private String reserve2;

    private String reserve3;

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3;
    }



}
