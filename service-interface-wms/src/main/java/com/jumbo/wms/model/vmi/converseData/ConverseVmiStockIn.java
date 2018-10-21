package com.jumbo.wms.model.vmi.converseData;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.OptimisticLockType;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;


@Entity
@Table(name = "T_CONVERSE_VMI_STOCKIN")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ConverseVmiStockIn extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3535772920691208736L;

    private Long id;

    private String transferNO;

    private Date receiveDate;

    private String fromLocation;

    private String toLocation;

    private String itemEanUpcCode;

    private Double quantity;

    private Double lineSequenceNO;

    private String cartonNo;

    private StockTransApplication sta;

    private StaLine staLine;

    private Date version;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CONVERSE_VMI_STOCKIN", sequenceName = "S_T_CONVERSE_VMI_STOCKIN", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONVERSE_VMI_STOCKIN")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "TRANSFER_NO", length = 10)
    public String getTransferNO() {
        return transferNO;
    }

    public void setTransferNO(String transferNO) {
        this.transferNO = transferNO;
    }

    @Column(name = "RECEIVE_DATE")
    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    @Column(name = "FROM_LOCATION", length = 50)
    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    @Column(name = "TO_LOCATION", length = 50)
    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    @Column(name = "ITEM_EAN_UPC_CODE", length = 20)
    public String getItemEanUpcCode() {
        return itemEanUpcCode;
    }

    public void setItemEanUpcCode(String itemEanUpcCode) {
        this.itemEanUpcCode = itemEanUpcCode;
    }

    @Column(name = "QUANTITY")
    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @Column(name = "LINE_SEQUENCE_NO")
    public Double getLineSequenceNO() {
        return lineSequenceNO;
    }

    public void setLineSequenceNO(Double lineSequenceNO) {
        this.lineSequenceNO = lineSequenceNO;
    }

    @Column(name = "CARTON_NO")
    public String getCartonNo() {
        return cartonNo;
    }

    public void setCartonNo(String cartonNo) {
        this.cartonNo = cartonNo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_CON_VMI_STA")
    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    @OneToOne
    public StaLine getStaLine() {
        return staLine;
    }

    public void setStaLine(StaLine staLine) {
        this.staLine = staLine;
    }

    @Column(name = "version")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

}
