package com.jumbo.wms.model.warehouse;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.jumbo.wms.model.BaseModel;

@Entity
@Table(name = "T_Starbucks_ICE_PACKAGE")
public class StarbucksIcePackage extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = 6615398333821195375L;
    /*
     * PK
     */
    private Long id;
    // 区域
    private String region;
    // 冰袋数量
    private int num;
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "S_T_Starbucks_ICE_PACKAGE", sequenceName = "S_T_Starbucks_ICE_PACKAGE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_T_Starbucks_ICE_PACKAGE")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "REGION")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Column(name = "NUM")
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
