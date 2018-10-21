package com.jumbo.wms.model.vmi.af;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;


@Entity
@Table(name = "t_wh_inv_lock")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class InvLock extends BaseModel {
    /**
     * 
     */
    private static final long serialVersionUID = 118620296095752990L;

    private Long id;

    private String source;
    /**
     * 创建时间
     */
    private String time;

    private Integer brand; // null 0 ：执行失败 1：执行ok

    private Date createTime;// 创建时间



    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WHL", sequenceName = "s_t_wh_inv_lock", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WHL")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "time")
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Column(name = "brand")
    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
