package com.jumbo.wms.model.vmi.warehouse;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

@Entity
@Table(name = "T_WH_MSG_RTN_RETURN")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MsgRtnReturn extends BaseModel {
    /**
	 * 
	 */
    private static final long serialVersionUID = 9106816894038248908L;
    /**
     * PK
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date createTime = new Date();
    /**
     * 状态
     */
    private DefaultStatus status;
    /**
     * 来源
     */
    private String source;
    /**
     * 来源仓库
     */
    private String sourceWh;
    /**
     * Sta 编码
     */
    private String staCode;
    /**
     * 作业单类型
     */
    private String type;

    private int version;

    /**
     * 前置单据编码
     */
    private String refSlipCode;

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MSGO", sequenceName = "S_T_WH_MSG_RTN_RETURN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MSGO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SLIP_CODE", length = 100)
    public String getRefSlipCode() {
        return refSlipCode;
    }

    public void setRefSlipCode(String refSlipCode) {
        this.refSlipCode = refSlipCode;
    }

    @Column(name = "STA_CODE", length = 100)
    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "SOURCE", length = 30)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "SOURCE_WH", length = 30)
    public String getSourceWh() {
        return sourceWh;
    }

    public void setSourceWh(String sourceWh) {
        this.sourceWh = sourceWh;
    }



    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "TYPE", length = 30)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
