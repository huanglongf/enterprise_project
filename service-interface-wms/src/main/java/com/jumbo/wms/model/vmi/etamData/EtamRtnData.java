package com.jumbo.wms.model.vmi.etamData;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;


@Entity
@Table(name = "T_WH_ETAM_RTN")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class EtamRtnData extends BaseModel {


    /**
	 * 
	 */
    private static final long serialVersionUID = -7268973547078870583L;

    /**
     * 新建状态
     */
    public static final Integer CREATE_STATUS = 1;

    public static final Integer TODO_STATUS = 5;
    /**
     * 完成状态
     */
    public static final Integer FINISH_STATUS = 10;

    private Long id;
    /**
	 * 
	 */
    private int version;
    /**
     * 业务单据编号
     */
    private String billCode;
    /**
	 *  
	 */
    private Integer whCode;
    /**
     * 伊藤忠的仓库号
     */
    private Integer shopCode;
    /**
     * 箱号
     */
    private String boxNo;
    /**
     * 出库日期
     */
    private String outBoundTime;

    /**
     * 库存状态 CC|DD
     */
    private String invStatus;

    /**
     * 状态
     */
    private Integer status;

    private List<EtamRtnDataLine> etamRtnDataLine;

    /**
     * 创建时间
     * 
     * @return
     */
    private Date createTime;

    @Column(name = "CREATETIME")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_T_WH_ETAM_RTN", sequenceName = "S_T_WH_ETAM_RTN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WH_ETAM_RTN")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "Version")
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Column(name = "BILL_CODE", length = 25)
    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    @Column(name = "WH_CODE")
    public Integer getWhCode() {
        return whCode;
    }

    public void setWhCode(Integer whCode) {
        this.whCode = whCode;
    }

    @Column(name = "SHOP_CODE")
    public Integer getShopCode() {
        return shopCode;
    }

    public void setShopCode(Integer shopCode) {
        this.shopCode = shopCode;
    }

    @Column(name = "BOX_NO", length = 10)
    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    @Column(name = "OUT_BOUND_TIME", length = 25)
    public String getOutBoundTime() {
        return outBoundTime;
    }

    public void setOutBoundTime(String outBoundTime) {
        this.outBoundTime = outBoundTime;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    // @Transient
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "etamRtnData", orphanRemoval = true)
    @OrderBy("id")
    public List<EtamRtnDataLine> getEtamRtnDataLine() {
        return etamRtnDataLine;
    }

    public void setEtamRtnDataLine(List<EtamRtnDataLine> etamRtnDataLine) {
        this.etamRtnDataLine = etamRtnDataLine;
    }

    @Column(name = "INV_STATUS", length = 25)
    public String getInvStatus() {
        return invStatus;
    }

    public void setInvStatus(String invStatus) {
        this.invStatus = invStatus;
    }
}
