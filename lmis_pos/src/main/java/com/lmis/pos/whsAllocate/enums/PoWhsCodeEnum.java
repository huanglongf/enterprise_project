package com.lmis.pos.whsAllocate.enums;

/**
 * PO单号中，仓库编码与系统仓库的对照关系（不拆单应用）
 * @author xuyisu
 * @date  2018年6月1日
 *
 */
public enum PoWhsCodeEnum{
    YD("YD", "YD"), BZ("BZ", "YD"), GZ("GZ", "GZBZ"), BJ("BJ", "BJBZ"),CD("CD", "CDBZ"),SH("SH", "YD"),WH("WH", "YD");
    // 成员变量
    private String name;
    private String index;

    // 构造方法
    private PoWhsCodeEnum(String index, String name) {
        this.name = name;
        this.index = index;
    }

    // 普通方法
    public static String getName(String index) {
        for (PoWhsCodeEnum c : PoWhsCodeEnum.values()) {
            if (c.getIndex() .equals(index)) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
    
    public static void main(String[] args){
        System.out.println("20180525FSHWNSW10".substring(10, 12));
    }
    
}
