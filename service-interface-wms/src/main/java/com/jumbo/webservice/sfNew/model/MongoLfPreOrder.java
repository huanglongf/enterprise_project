package com.jumbo.webservice.sfNew.model;

import com.jumbo.wms.model.BaseModel;

/**
 * 顺风下单实体Mongo
 * 
 * 
 */

public class MongoLfPreOrder extends BaseModel {

    private static final long serialVersionUID = 8085697596655551077L;
    private String Id;

    private String batchId;

    private String msg;
    
    private String source;

    private String type;// 1:发送 2：反馈


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
