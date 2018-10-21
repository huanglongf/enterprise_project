package com.bt.lmis.api.model;

public enum ProductTypeEnum {

    SFCR(1,"1","顺丰次日"),
    SFGR(2,"2","顺丰隔日"),
    SFCC(5,"5","顺丰次晨"),
    SFJR(6,"6","顺丰即日"),
    YCCR(37,"37","运仓即日"),
    YCGR(38,"38","运仓隔日");

    private int index;
    private String code;
    private String name;

    ProductTypeEnum(){}
    ProductTypeEnum(int index, String code, String name){
        this.index=index;
        this.code=code;
        this.name=name;
    }
    public  static ProductTypeEnum queryByName(String name){
        ProductTypeEnum[] productTypeEnums= ProductTypeEnum.values();
        for(ProductTypeEnum typeEnum:productTypeEnums){
            if(name.equals(typeEnum.name)){
                return typeEnum;
            }
        }
        return  null;
    }
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
