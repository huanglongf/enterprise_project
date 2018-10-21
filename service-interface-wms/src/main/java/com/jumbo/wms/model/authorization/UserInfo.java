package com.jumbo.wms.model.authorization;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7765213184158010067L;

    private Long id;

    private String loginName;

    private String userName;

    private String password;

    private Date createTime;

    private Date latestUpdateTime;

    private String email;

    private String phone;

    private String operationName;

    private String operationTypeName;

    private String jobNumber;

    private Boolean isenabled;

    /**
     * 用户关联的仓库
     */
    private Long whOuId;


    public Boolean getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(Boolean isenabled) {
        this.isenabled = isenabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLatestUpdateTime() {
        return latestUpdateTime;
    }

    public void setLatestUpdateTime(Date latestUpdateTime) {
        this.latestUpdateTime = latestUpdateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationTypeName() {
        return operationTypeName;
    }

    public void setOperationTypeName(String operationTypeName) {
        this.operationTypeName = operationTypeName;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Long getWhOuId() {
        return whOuId;
    }

    public void setWhOuId(Long whOuId) {
        this.whOuId = whOuId;
    }


}
