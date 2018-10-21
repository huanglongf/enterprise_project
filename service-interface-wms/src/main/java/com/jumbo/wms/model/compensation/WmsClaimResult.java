package com.jumbo.wms.model.compensation;

import java.io.Serializable;


/**
 *  索赔单明细
 *  @author lihui
 */
public class WmsClaimResult implements Serializable{

    private static final long serialVersionUID = -1477932627919520620L;

    public static final int ACCEPT_SUCCESS = 1;// 接收成功
    public static final int ACCEPT_FAIL = 0;// 接收失败
    public static final int DISPOSE_SUCCESS = 10;// 处理成功
    public static final int DISPOSE_FAIL = 8001;// 处理失败
    public static final int DATA_REPETITION = 8100;// 处理失败


    private String source;
    private String systemCode;
    private String memo;
    private int status;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }



}
