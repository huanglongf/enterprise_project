package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;

/**
 * 外包仓请求参数
 * 
 * @author xiaolong.fei
 * 
 */
public class WmsThreePlRequest implements Serializable {

    private static final long serialVersionUID = -4090296751995275796L;
    private String startTime; // 开始时间
    private String endTime; // 结束时间
    private int page; // 页号
    private int pageSize; // 每页大小

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
