/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.wms.model.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * 
 * @author xiaolong.fei
 * 
 * 
 */
@Entity
@Table(name = "t_wh_import_file_log")
public class ImportFileLog extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1959734968930605731L;
    private Long id;
    /**
     * 作业单
     */
    private String staCode;
    /**
     * 仓库ID
     */
    private Long whId;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 操作人
     */
    private String userName;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后修改时间
     */
    private Date lastModifyTime;
    /**
     * 备注
     */
    private String memo;
    /**
     * 备用字段1
     */
    private String colum1;
    /**
     * 备用字段2
     */
    private String colum2;

    private Long createId;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "seq_t_wh_import_file_log", sequenceName = "s_t_wh_import_file_log", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_t_wh_import_file_log")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "sta_code")
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "wh_id")
    public Long getWhId() {
        return whId;
    }

    public void setWhId(Long whId) {
        this.whId = whId;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "file_name")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "last_modify_time")
    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    @Column(name = "memo")
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "colum1")
    public String getColum1() {
        return colum1;
    }

    public void setColum1(String colum1) {
        this.colum1 = colum1;
    }

    @Column(name = "colum2")
    public String getColum2() {
        return colum2;
    }

    public void setColum2(String colum2) {
        this.colum2 = colum2;
    }

    @Column(name = "create_id")
    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }



}
