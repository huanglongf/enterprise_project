package com.jumbo.wms.model.baseinfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;

/**
 * 外包仓品牌仓库关联
 * 
 */
@Entity
@Table(name = "t_wh_msg_sku_wh_ref")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class SkuWarehouseRef extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7627818207333415691L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    /**
     * 相关品牌
     */
    private Brand brand;

    /**
     * 外包仓编码
     */
    private String source;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRAND_ID")
    @Index(name = "IDX_BRAND_ID")
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Column(name = "SOURCE", length = 100)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


}
