package com.jumbo.wms.model.baseinfo;

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

/**
 * 
 * 物流商配置
 * 
 * @author sjk
 * 
 */
@Entity
@Table(name = "T_MA_TRANSPORTATOR_WEIGTH")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class TransportatorWeigth extends BaseModel {

    


	/**
	 * 
	 */
	private static final long serialVersionUID = -4927158039636744252L;

	/**
     * PK
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 物流商名称
     */
    private String name;

   
    /**
     * 内部平台对接编码，全局唯一
     */
    private String expCode;

    /**
     * 最大的重量
     */
    private String maxWeight;
    
    /**
     * 最小的重量
     */
    private String minWeight;

    
    /**
     * 重量差异百分比
     */
    private String weightDifferencePercent;
    
    
    /**
     * 仓库id
     */
    private Long ouId;

    

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_TRANSPORTATOR_WEIGTH", sequenceName = "seq_t_ma_transportator_weigth", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANSPORTATOR_WEIGTH")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "EXP_CODE")
    public String getExpCode() {
        return expCode;
    }

    public void setExpCode(String expCode) {
        this.expCode = expCode;
    }

    @Column(name = "MAX_WEIGHT")
	public String getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(String maxWeight) {
		this.maxWeight = maxWeight;
	}

	@Column(name = "WEIGHT_DIFFERENCE_PERCENT")
	public String getWeightDifferencePercent() {
		return weightDifferencePercent;
	}

	public void setWeightDifferencePercent(String weightDifferencePercent) {
		this.weightDifferencePercent = weightDifferencePercent;
	}

	@Column(name = "OU_ID")
	public Long getOuId() {
		return ouId;
	}

	public void setOuId(Long ouId) {
		this.ouId = ouId;
	}

    @Column(name = "MIN_WEIGHT")
    public String getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(String minWeight) {
        this.minWeight = minWeight;
    }

	

}
