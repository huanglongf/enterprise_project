package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;

public class DeliverRequirements implements Serializable {

    private static final long serialVersionUID = -4295343546159894408L;

    /**
     * 投递时延要求(1-工作日 2-节假日 101当日达102次晨达103次日达 111 活动标 104 预约达 )
     */
    private Integer scheduleType;
    /**
     * 送达日期（格式为 YYYYMMDD)
     */
    private String scheduleDay;
    /**
     * 送达开始时间（格式为 hh:mm:ss）
     */
    private String scheduleStart;
    /**
     * 送达结束时间（格式为 hh:mm:ss）
     */
    private String scheduleEnd;

    public Integer getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(Integer scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getScheduleDay() {
        return scheduleDay;
    }

    public void setScheduleDay(String scheduleDay) {
        this.scheduleDay = scheduleDay;
    }

    public String getScheduleStart() {
        return scheduleStart;
    }

    public void setScheduleStart(String scheduleStart) {
        this.scheduleStart = scheduleStart;
    }

    public String getScheduleEnd() {
        return scheduleEnd;
    }

    public void setScheduleEnd(String scheduleEnd) {
        this.scheduleEnd = scheduleEnd;
    }

}
