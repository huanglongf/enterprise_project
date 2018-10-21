package com.jumbo.wms.model.boc;

import java.util.Date;

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
import javax.persistence.Version;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.warehouse.InventoryStatus;

/**
 * 库存快照
 * 
 * @author wudan
 * 
 */
@Entity
@Table(name = "T_VMI_INV_SNAPSHOT_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class VmiInventorySnapshotData extends BaseModel {

    private static final long serialVersionUID = 5770078555518134018L;

    private Long id;

    private int version;

    /**
     * 创建日期
     */
    private Date createData;

    /**
     * 仓库
     */
    private String warehouse;

    /**
     * STORE CODE
     */
    private String storeCode;

    /**
     * 操作日期
     */
    private String eDate;

    /**
     * UPC
     */
    private String upc;

    /**
     * 实际库存
     */
    private Long onhandQty;

    /**
     * 相关库存状态
     */
    private InventoryStatus inventoryStatus;

    /**
     * 执行状态
     */
    private DefaultStatus vmiStatus;


    /**
     * 相关作业单号
     */
    private String staCode;

    /**
     * 来源
     */
    private String source;
    
    /**
     * 文件名字
     */
    private String fileName;

    
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_VMI_INV_SNAPSHOT_DATA", sequenceName = "S_T_VMI_INV_SNAPSHOT_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_VMI_INV_SNAPSHOT_DATA")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Column(name = "VERSION")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "CREATE_TIME")
    public Date getCreateData() {
        return createData;
    }

    public void setCreateData(Date createData) {
        this.createData = createData;
    }

    @Column(name = "WAREHOUSE", length = 20)
    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Column(name = "STORE_CODE", length = 20)
    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    @Column(name = "EDATE")
     public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    @Column(name = "UPC", length = 30)
    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Column(name = "ONHAND_QTY", precision = 10)
    public Long getOnhandQty() {
        return onhandQty;
    }

    public void setOnhandQty(Long onhandQty) {
        this.onhandQty = onhandQty;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVENTORY_STATUS")
    @Index(name = "FK_STAID")
    public InventoryStatus getInventoryStatus() {
        return inventoryStatus;
    }

    public void setInventoryStatus(InventoryStatus inventoryStatus) {
        this.inventoryStatus = inventoryStatus;
    }

    @Column(name = "VMI_STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getVmiStatus() {
        return vmiStatus;
    }

    public void setVmiStatus(DefaultStatus vmiStatus) {
        this.vmiStatus = vmiStatus;
    }

    @Column(name = "STA_CODE", length = 100)
    public String getStaCode() {
        return staCode;
    }



    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    @Column(name = "source", length = 100)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    @Column(name = "FILE_NAME", length = 100)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
