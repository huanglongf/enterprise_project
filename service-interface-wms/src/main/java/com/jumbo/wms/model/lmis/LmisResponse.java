package com.jumbo.wms.model.lmis;

import java.io.Serializable;
import java.util.List;

public class LmisResponse<T> implements Serializable {

    private static final long serialVersionUID = 900519804884359142L;

    /**
     * status:1代表成功，0代表失败
     */
    private String status;
    /**
     * 失败时反馈的信息
     */
    private String msg;

    /**
     * 总记录数(如果需要分页的话)
     */
    private Long total;
    /**
     * 数据部分
     */
    private List<T> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "LmisResponse [status=" + status + ", msg=" + msg + ", total=" + total + ", data=" + data + "]";
    }
}
