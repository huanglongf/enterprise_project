package com.jumbo.wms.model.warehouse;

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
 * 接口/类说明：顺丰订单确认队列 (超长)运单分存 关联表
 * 
 * @ClassName: SfMailNoRemainRelation
 * @author LuYingMing
 */
@Entity
@Table(name = "T_SF_MAILNO_REMAIN_RELATION")
public class SfMailNoRemainRelation extends BaseModel {


    private static final long serialVersionUID = -7377592359104621744L;

    /**
     * PK
     */
    private Long id;
    /**
     * 截取运单号
     */
    private String splitMailno;
    /**
     * 关联表ID
     */
    private Long refId;
    /**
     * 运单号类型
     */
    private Integer type;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_SFMRR", sequenceName = "S_T_SF_MAILNO_REMAIN_RELATION", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SFMRR")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SPLIT_MAILNO", length = 3000)
    public String getSplitMailno() {
        return splitMailno;
    }

    public void setSplitMailno(String splitMailno) {
        this.splitMailno = splitMailno;
    }

    @Column(name = "REF_ID")
    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
