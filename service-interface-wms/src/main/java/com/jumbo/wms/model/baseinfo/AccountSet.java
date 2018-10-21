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

package com.jumbo.wms.model.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

/**
 * 帐套信息
 * 
 * @author benjamin
 * 
 */
@Entity
@Table(name = "T_BI_ACCOUNT_SET")
public class AccountSet extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 333167933270634091L;

    /**
     * PK
     */
    private Long id;

    /**
     * 帐套名
     */
    private String name;

    /**
     * 帐套连接驱动类名称
     */
    private String driverClassName;

    /**
     * 帐套连接URL
     */
    private String url;

    /**
     * 数据库用户名
     */
    private String dbUserName;

    /**
     * 数据库密码
     */
    private String dbPassword;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_AS", sequenceName = "S_T_BI_ACCOUNT_SET", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AS")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "NAME", length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DB_USER_NAME", length = 50)
    public String getDbUserName() {
        return dbUserName;
    }

    public void setDbUserName(String dbUserName) {
        this.dbUserName = dbUserName;
    }

    @Column(name = "DB_PASSWORD", length = 50)
    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    @Column(name = "DRIVER_CLASS_NAME", length = 100)
    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    @Column(name = "DB_URL", length = 255)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
