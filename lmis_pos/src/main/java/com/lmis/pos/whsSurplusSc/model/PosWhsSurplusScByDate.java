package com.lmis.pos.whsSurplusSc.model;

import io.swagger.annotations.ApiModelProperty;

public class PosWhsSurplusScByDate extends PosWhsSurplusSc{

    /**
     * 
     */
    private static final long serialVersionUID = -6236580617341468065L;
    @ApiModelProperty(value = "开始时间")
    private String startDate;
    @ApiModelProperty(value = "结束时间")
    private String endDate;
    
    public String getStartDate(){
        return startDate;
    }
    
    public void setStartDate(String startDate){
        this.startDate = startDate;
    }
    
    public String getEndDate(){
        return endDate;
    }
    
    public void setEndDate(String endDate){
        this.endDate = endDate;
    }
    

}
