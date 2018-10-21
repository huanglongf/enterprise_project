package com.jumbo.web.action.baseinfo;


import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class CompanyShopModifyAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 3227307671262927589L;

    private Long ouid;
    private OperationUnit ou;
    private BiChannel companyShop;
    private String shopName;
    private OperationUnit operationUnit;

    public String execute() {
        return SUCCESS;
    }


    public Long getOuid() {
        return ouid;
    }

    public void setOuid(Long ouid) {
        this.ouid = ouid;
    }

    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    public BiChannel getCompanyShop() {
        return companyShop;
    }

    public void setCompanyShop(BiChannel companyShop) {
        this.companyShop = companyShop;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public OperationUnit getOperationUnit() {
        return operationUnit;
    }

    public void setOperationUnit(OperationUnit operationUnit) {
        this.operationUnit = operationUnit;
    }

}
