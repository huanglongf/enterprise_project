package com.jumbo.wms.model.hub2wms.threepl;

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
 * 菜鸟订单原始属性
 * 
 * @author hui.li
 * 
 */
@Entity
@Table(name = "T_WH_CN_ORDER_PROPERTY")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnOrderProperty extends BaseModel {

    private static final long serialVersionUID = 5047883818957540087L;

    private Long id;// 主键

    /**
     * 单据编码
     */
    private String orderCode;

    /**
     * 上游仓库编码
     */
    private String storeCode;

    /**
     * 单据类型
     */
    private String orderType;

    /**
     * 系统编码
     */
    private String systemKey;

    /**
     * 物流商编码
     */
    private String lpCode;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CN_COP", sequenceName = "S_T_WH_CN_ORDER_PROPERTY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CN_COP")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDER_CODE")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Column(name = "STORE_CODE")
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "ORDER_TYPE")
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Column(name = "SYSTEM_KEY")
    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    @Column(name = "LPCODE")
    public String getLpCode() {
        return lpCode;
    }

    public void setLpCode(String lpCode) {
        this.lpCode = lpCode;
    }

}
