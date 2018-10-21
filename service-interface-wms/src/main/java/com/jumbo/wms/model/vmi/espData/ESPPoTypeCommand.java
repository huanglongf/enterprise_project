package com.jumbo.wms.model.vmi.espData;

import java.util.Date;

import com.jumbo.util.StringUtils;

public class ESPPoTypeCommand extends ESPPoType {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5280665367326878379L;

    private Date startTime;

    private Date endTime;

    private String typeName;
    /*
     * 新增String类型日期，辅助完成查询时间精确到时分秒
     */
    private String startTime1;
    private String endTime1;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setQueryLikeParam() {
        if (this.getStartTime() == null) {
            this.setStartTime(null);
        }
        if (this.getEndTime() == null) {
            this.setEndTime(null);
        }
        if (StringUtils.hasText(this.getPo())) {
            this.setPo("%" + this.getPo() + "%");
        } else {
            this.setPo(null);
        }
        if (StringUtils.hasText(this.getTypeName())) {
            this.setTypeName(this.getTypeName());
        } else {
            this.setTypeName(null);
        }
    }

    public String getStartTime1() {
        return startTime1;
    }

    public void setStartTime1(String startTime1) {
        this.startTime1 = startTime1;
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

}
