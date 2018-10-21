package com.jumbo.wms.model.warehouse;

import java.util.List;

public class WhTransProvideNoCommand extends WhTransProvideNo {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8470046941556246878L;
    private boolean result;
    private String remark;
    private List<String> list;
    private Long quantity;
    private Long qty;

    private String checkboxSf1;



    public String getCheckboxSf1() {
        return checkboxSf1;
    }

    public void setCheckboxSf1(String checkboxSf1) {
        this.checkboxSf1 = checkboxSf1;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
