package com.jumbo.webservice.ems.command.New;

public class NewEmsOrderBillNo {

    private String count;// 获取数量
    private String bizcode;// 运单号代码类型 （ 非邮业务：05 标准快递：06 经济快递：07）

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getBizcode() {
        return bizcode;
    }

    public void setBizcode(String bizcode) {
        this.bizcode = bizcode;
    }



}
