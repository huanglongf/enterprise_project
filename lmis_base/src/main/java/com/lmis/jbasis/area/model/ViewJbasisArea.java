package com.lmis.jbasis.area.model;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName: ViewJbasisArea
 * @Description: TODO(VIEW)
 * @author codeGenerator
 * @date 2018-04-11 14:23:33
 * 
 */
public class ViewJbasisArea {
    
    @ApiModelProperty(value = "主键")
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    @ApiModelProperty(value = "代码")
    private String areaCode;
    public String getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    @ApiModelProperty(value = "名称")
    private String areaName;
    public String getAreaName() {
        return areaName;
    }
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    
    @ApiModelProperty(value = "级别")
    private Integer level;
    public Integer getLevel() {
        return level;
    }
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    @ApiModelProperty(value = "父节点ID")
    private String pid;
    public String getPid() {
        return pid;
    }
    public void setPid(String pid) {
        this.pid = pid;
    }
    
    @ApiModelProperty(value = "存在子节点 存在-0 不存在-1")
    private Integer haschild;
    public Integer getHaschild() {
        return haschild;
    }
    public void setHaschild(Integer haschild) {
        this.haschild = haschild;
    }
    @ApiModelProperty(value = "父节点名称")
    private String pName;
    public String getpName() {
        return pName;
    }
    public void setpName(String pName) {
        this.pName = pName;
    }
    
//    @ApiModelProperty(value = "子节点数组")
//    private String[] flag1;
//
//    public String[] getFlag1(){
//        return flag1;
//    }
//    
//    public void setFlag1(String[] flag1){
//        this.flag1 = flag1;
//    }
//    
//    
//    @ApiModelProperty(value = "子节点")
//    private List<ViewJbasisArea> childArea;
//    
//    public List<ViewJbasisArea> getChildArea(){
//        return childArea;
//    }
//    
//    public void setChildArea(List<ViewJbasisArea> childArea){
//        this.childArea = childArea;
//    }
}
