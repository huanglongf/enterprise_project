package com.jumbo.webservice.ems.command.New;

public class Item {// 商品

    private String itemName;// 商品名称
    private int number;// 商品数量
    private long itemValue;// 商品单价（单位：分）

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getItemValue() {
        return itemValue;
    }

    public void setItemValue(long itemValue) {
        this.itemValue = itemValue;
    }



}
