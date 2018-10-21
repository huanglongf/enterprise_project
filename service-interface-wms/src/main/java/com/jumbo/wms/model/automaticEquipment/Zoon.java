package com.jumbo.wms.model.automaticEquipment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.authorization.OperationUnit;

/**
 * @author lihui
 *
 * @createDate 2016年1月19日 下午7:21:46
 */
@Entity
@Table(name = "T_WH_ZOON")
public class Zoon extends BaseModel {

    private static final long serialVersionUID = -517007945461464933L;

    private Long id;
    private String code;
    private String name;
    private OperationUnit operationUnit;
    private Boolean status;
    private String seq;// 排序

    public String getSeq() {
        return seq;
    }

    @Column(name = "seq")
    public void setSeq(String seq) {
        this.seq = seq;
    }

    @Id
    @SequenceGenerator(name = "S_T_WH_ZOON_ID_GENERATOR", sequenceName = "S_T_WH_ZOON", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_WH_ZOON_ID_GENERATOR")
    @Column(name = "ID", unique = true, nullable = false, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "code", length = 1)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ou_id")
    public OperationUnit getOperationUnit() {
        return operationUnit;
    }

    public void setOperationUnit(OperationUnit operationUnit) {
        this.operationUnit = operationUnit;
    }

    @Column(name = "status", length = 1)
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }



}
