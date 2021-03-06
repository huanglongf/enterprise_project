package com.bt.vims.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 服务端接收访客信息实体
 * @date 2018-05-07
 * @author liuqingqiang
 * @version V1.0
 *
 */
public class VimsData implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6821426649677652328L;

	private Integer id; //主键id

    private String visitorName; //拜访人姓名

    private String visitorPhone; //拜访人电话

    private String host; //被拜访人

    private String visitorCompanyName; //工作单位名称（访客类型为团体时必填）

    private String licensePlateNumber; // 车牌号

    private String content; //访问事由

    private String remark; // 备注

    private String photo; //照片

    private Date checkInTime; //迁入时间

    private String checkInUser; // 迁入人

    private String checkInPlcae; //迁入地址

    private String applyFor; //应聘职位

    private String visitorType; //访客类型

    private Integer visitorNum; //访客人数

    private Date createTime; //创建时间

    private Date updateTime; //更新时间

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getVisitorName(){
        return visitorName;
    }

    public void setVisitorName(String visitorName){
        this.visitorName = visitorName;
    }

    public String getVisitorPhone(){
        return visitorPhone;
    }

    public void setVisitorPhone(String visitorPhone){
        this.visitorPhone = visitorPhone;
    }

    public String getHost(){
        return host;
    }

    public void setHost(String host){
        this.host = host;
    }

    public String getVisitorCompanyName(){
        return visitorCompanyName;
    }

    public void setVisitorCompanyName(String visitorCompanyName){
        this.visitorCompanyName = visitorCompanyName;
    }

    public String getLicensePlateNumber(){
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber){
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getRemark(){
        return remark;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getPhoto(){
        return photo;
    }

    public void setPhoto(String photo){
        this.photo = photo;
    }

    public Date getCheckInTime(){
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime){
        this.checkInTime = checkInTime;
    }

    public String getCheckInUser(){
        return checkInUser;
    }

    public void setCheckInUser(String checkInUser){
        this.checkInUser = checkInUser;
    }

    public String getCheckInPlcae(){
        return checkInPlcae;
    }

    public void setCheckInPlcae(String checkInPlcae){
        this.checkInPlcae = checkInPlcae;
    }

    public String getApplyFor(){
        return applyFor;
    }

    public void setApplyFor(String applyFor){
        this.applyFor = applyFor;
    }

    public String getVisitorType(){
        return visitorType;
    }

    public void setVisitorType(String visitorType){
        this.visitorType = visitorType;
    }

    public Integer getVisitorNum(){
        return visitorNum;
    }

    public void setVisitorNum(Integer visitorNum){
        this.visitorNum = visitorNum;
    }

    public Date getCreateTime(){
        return createTime;
    }

    public void setCreateTime(Date createTime){
        this.createTime = createTime;
    }

    public Date getUpdateTime(){
        return updateTime;
    }

    public void setUpdateTime(Date updateTime){
        this.updateTime = updateTime;
    }

}
