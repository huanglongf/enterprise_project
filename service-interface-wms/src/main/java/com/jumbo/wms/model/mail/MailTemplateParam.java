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

package com.jumbo.wms.model.mail;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Index;
import com.jumbo.wms.model.BaseModel;

/**
 * 邮件模板对应参数，这些参数 只包含自定义参数和附件参数
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_MAIL_TEMPLATE_PARAM")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class MailTemplateParam extends BaseModel {

    private static final long serialVersionUID = 6357196546576469654L;
    /**
     * 邮件模板对应参数标识
     */
    private Long id;
    /**
     * 参数类型，这里只会有两种参数类型： 自定义参数：用于记录模板对应的自定义参数微调模板内容 附件参数：用于定义模板对应的附件，一个参数对应一个附件
     */
    private MailParamType type;
    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数描述
     */
    private String description;
    /**
     * 参数默认值，意义如下： 自定义参数：参数默认值，将用于替换邮件主题和内容中的相关参数
     * 附件参数：附件的文件名，邮件发送系统将根据文件后缀自动设置文件MIME类型，如果没找到则记为二进制文件
     */
    private String defaultValue;
    /**
     * 邮件模板
     */
    private MailTemplate mailTemplate;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MTP", sequenceName = "S_T_MAIL_TEMPLATE_PARAM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MTP")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", insertable = false, updatable = false)
    public MailParamType getType() {
        return type;
    }

    public void setType(MailParamType type) {
        this.type = type;
    }

    @Column(name = "name", length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "DESCRIPTION", length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "DEFAULE_VALUE", length = 100)
    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MAIL_TEMPLATE_ID")
    @Index(name = "IDX_MTP_MT_ID")
    public MailTemplate getMailTemplate() {
        return mailTemplate;
    }

    public void setMailTemplate(MailTemplate mailTemplate) {
        this.mailTemplate = mailTemplate;
    }

}
