package com.jumbo.wms.model.warehouse;

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

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.baseinfo.Sku;
/**
 * 退仓残次明细
 * 
 */
@Entity
@Table(name = "t_wh_imperfect_carton_line")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class ImperfectCartonLine extends BaseModel {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 3702436635131971289L;

	/**
     * PK
     */
    private Long id;
   
    /**
     * 商品
     */
    private Sku sku;
    /**
     * 箱头
     */
    private Carton carton;
    /**
     * 箱明细
     */
    private CartonLine cartonLine;
    /**
     * 残次品编码
     */
    private SkuImperfect imperfect;
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_IMPERFECT_CARTON_LINE", sequenceName = "S_T_WH_IMPERFECT_CARTON_LINE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IMPERFECT_CARTON_LINE")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sku_id")
	public Sku getSku() {
		return sku;
	}
	public void setSku(Sku sku) {
		this.sku = sku;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carton_id")
	public Carton getCarton() {
		return carton;
	}
	public void setCarton(Carton carton) {
		this.carton = carton;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartonLine_id")
	public CartonLine getCartonLine() {
		return cartonLine;
	}
	public void setCartonLine(CartonLine cartonLine) {
		this.cartonLine = cartonLine;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "imperfect_id")
	public SkuImperfect getImperfect() {
		return imperfect;
	}
	public void setImperfect(SkuImperfect imperfect) {
		this.imperfect = imperfect;
	}
    
    

}
