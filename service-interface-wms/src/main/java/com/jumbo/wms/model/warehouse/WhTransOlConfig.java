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
 * 快递电子运单接口帐号配置信息
 */
@Entity
@Table(name = "T_WH_TRANS_OL_CONFIG")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class WhTransOlConfig extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -7937522422038018545L;

    /**
     * ID
     */
    private Long id;

    /**
     * 始发地
     */
    private String departure;
   
    /**
     * 用户
     */
    private String username;

    /**
     * 扩展1
     */
    private String ext1;

    /**
     * 扩展2
     */
    private String ext2;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 物流商
     */
    private String lpcode;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 类型(1.普通,4.非COD类型,5.COD类型)
     */
    private Integer type;

    public WhTransOlConfig() {}

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TRANS_OL_CONFIG", sequenceName = "S_T_WH_TRANS_OL_CONFIG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANS_OL_CONFIG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "DEPARTURE")
    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "EXT1")
    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    @Column(name = "EXT2")
    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    @Column(name = "PWD")
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Column(name = "LPCODE")
    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    @Column(name = "TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
