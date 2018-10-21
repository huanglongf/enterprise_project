package com.jumbo.wms.model.warehouse;

public class WhReplenishLineCommand extends WhReplenishLine{

    /**
     * 
     */
    private static final long serialVersionUID = 2819162023528736943L;
    /*
     * 商品条码
     */
    private String barCode;
    public String getBarCode() {
        return barCode;
    }
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
   

}
