package com.jumbo.wms.model.command;

import com.jumbo.wms.model.BaseModel;

/**
 * 接口反馈信息结构
 * 
 * @author sjk
 * 
 */
public class ActionMsgCommand extends BaseModel {


    private static final long serialVersionUID = 442523025234408370L;

    private String code;

    private String result;

    private String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
