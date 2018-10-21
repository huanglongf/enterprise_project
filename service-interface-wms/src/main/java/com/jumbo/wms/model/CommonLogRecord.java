package com.jumbo.wms.model;

/**
 * 
 * @author jinlong.ke
 * 
 */
public class CommonLogRecord extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -5566397319599423963L;
    public static final String AD_PARAM = "AD_PARAM";// AD加密前参数
    public static final String AD_URL_DATA = "AD_URL_DATA";// AD加密后URL
    public static final String AD_RESPONSE = "AD_RESPONSE";// AD反馈报文
    public static final String LV_SO_RESONSE = "LV_SO_RESONSE";// LEVIS抓单反馈
    public static final String LV_SO_CONFIRM = "LV_SO_CONFIRM";// LEVIS主单确认
    /**
     * 查询关键字 作业单，前置单据，id等一些信息，根据实际情况定义
     */
    private String keyWord;
    /**
     * 存储类型 比如SF下单报文/反馈报文 自己定义一个见名知意的代码
     */
    private String storeType;
    /**
     * 存储的内容，xml,json等对接的关键信息
     */
    private String content;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
