package com.jumbo.wms.model.vmi.espData;

import java.math.BigDecimal;
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
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;

@Entity
@Table(name = "T_ESPRIT_TRANSFER_ORDER")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class ESPTransferOrder extends BaseModel {

    /**
	 * 
	 */
    private static final long serialVersionUID = 7186757076648959636L;

    private Long id;

    private Long orderId;

    private String headerFromGLN;

    private String headerToGLN;

    private String headerFromNode;

    private String headerToNode;

    private String headerSequenceNumber;

    private String headerNumberOfrecords;

    private String headerGenerationDate;

    private String headerGenerationTime;

    private String odBuyingSeasonYear;

    private String odBuyingSeasonCode;

    private String odStyle;

    private String odPO;

    private String odFactory;

    private String odSupplier;

    private String odCountryoforigin;

    private String odCurrency;

    private String odExpectedDeliveryDate;

    private String odShippingMethod;

    private String odExfactoryDate;

    private String odPortoFloading;

    private String odFobinCurrency;

    private String odGlobalTransferPrice;

    private String odStatusineDifile;

    private String odFromNodeGLN;

    private String odToNodeGLN;

    private String odSku;

    private String odOrderQty;

    private Integer status;

    private BigDecimal batchID;

    private String odPoreference;

    private Date version;

    private StockTransApplication sta;

    private StaLine staLine;

    private StockTransApplication transferWHSta;

    private StaLine transferWHStaLine;

    private Date modifyTime;

    private InventoryCheck invCheck;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_ESPRIT_TRANSFER_ORDER", sequenceName = "S_T_ESPRIT_TRANSFER_ORDER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ESPRIT_TRANSFER_ORDER")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ORDER_ID")
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Column(name = "HEADER_FROMGLN", length = 20)
    public String getHeaderFromGLN() {
        return headerFromGLN;
    }

    public void setHeaderFromGLN(String headerFromGLN) {
        this.headerFromGLN = headerFromGLN;
    }

    @Column(name = "HEADER_TOGLN", length = 20)
    public String getHeaderToGLN() {
        return headerToGLN;
    }

    public void setHeaderToGLN(String headerToGLN) {
        this.headerToGLN = headerToGLN;
    }

    @Column(name = "HEADER_FROMNODE", length = 20)
    public String getHeaderFromNode() {
        return headerFromNode;
    }

    public void setHeaderFromNode(String headerFromNode) {
        this.headerFromNode = headerFromNode;
    }

    @Column(name = "HEADER_TONODE", length = 20)
    public String getHeaderToNode() {
        return headerToNode;
    }

    public void setHeaderToNode(String headerToNode) {
        this.headerToNode = headerToNode;
    }

    @Column(name = "HEADER_SEQUENCENUMBER", length = 20)
    public String getHeaderSequenceNumber() {
        return headerSequenceNumber;
    }

    public void setHeaderSequenceNumber(String headerSequenceNumber) {
        this.headerSequenceNumber = headerSequenceNumber;
    }

    @Column(name = "HEADER_NUMBEROFRECORDS", length = 10)
    public String getHeaderNumberOfrecords() {
        return headerNumberOfrecords;
    }

    public void setHeaderNumberOfrecords(String headerNumberOfrecords) {
        this.headerNumberOfrecords = headerNumberOfrecords;
    }

    @Column(name = "HEADER_GENERATIONDATE", length = 20)
    public String getHeaderGenerationDate() {
        return headerGenerationDate;
    }

    public void setHeaderGenerationDate(String headerGenerationDate) {
        this.headerGenerationDate = headerGenerationDate;
    }

    @Column(name = "HEADER_GENERATIONTIME", length = 20)
    public String getHeaderGenerationTime() {
        return headerGenerationTime;
    }

    public void setHeaderGenerationTime(String headerGenerationTime) {
        this.headerGenerationTime = headerGenerationTime;
    }

    @Column(name = "OD_BUYINGSEASONYEAR", length = 20)
    public String getOdBuyingSeasonYear() {
        return odBuyingSeasonYear;
    }

    public void setOdBuyingSeasonYear(String odBuyingSeasonYear) {
        this.odBuyingSeasonYear = odBuyingSeasonYear;
    }

    @Column(name = "OD_BUYINGSEASONCODE", length = 20)
    public String getOdBuyingSeasonCode() {
        return odBuyingSeasonCode;
    }

    public void setOdBuyingSeasonCode(String odBuyingSeasonCode) {
        this.odBuyingSeasonCode = odBuyingSeasonCode;
    }

    @Column(name = "OD_STYLE", length = 20)
    public String getOdStyle() {
        return odStyle;
    }

    public void setOdStyle(String odStyle) {
        this.odStyle = odStyle;
    }

    @Column(name = "OD_PO", length = 20)
    public String getOdPO() {
        return odPO;
    }

    public void setOdPO(String odPO) {
        this.odPO = odPO;
    }

    @Column(name = "OD_FACTORY", length = 20)
    public String getOdFactory() {
        return odFactory;
    }

    public void setOdFactory(String odFactory) {
        this.odFactory = odFactory;
    }

    @Column(name = "OD_SUPPLIER", length = 20)
    public String getOdSupplier() {
        return odSupplier;
    }

    public void setOdSupplier(String odSupplier) {
        this.odSupplier = odSupplier;
    }

    @Column(name = "OD_COUNTRYOFORIGIN", length = 20)
    public String getOdCountryoforigin() {
        return odCountryoforigin;
    }

    public void setOdCountryoforigin(String odCountryoforigin) {
        this.odCountryoforigin = odCountryoforigin;
    }

    @Column(name = "OD_CURRENCY", length = 20)
    public String getOdCurrency() {
        return odCurrency;
    }

    public void setOdCurrency(String odCurrency) {
        this.odCurrency = odCurrency;
    }

    @Column(name = "OD_EXPECTEDDELIVERYDATE", length = 20)
    public String getOdExpectedDeliveryDate() {
        return odExpectedDeliveryDate;
    }

    public void setOdExpectedDeliveryDate(String odExpectedDeliveryDate) {
        this.odExpectedDeliveryDate = odExpectedDeliveryDate;
    }

    @Column(name = "OD_SHIPPINGMETHOD", length = 20)
    public String getOdShippingMethod() {
        return odShippingMethod;
    }

    public void setOdShippingMethod(String odShippingMethod) {
        this.odShippingMethod = odShippingMethod;
    }

    @Column(name = "OD_EXFACTORYDATE", length = 20)
    public String getOdExfactoryDate() {
        return odExfactoryDate;
    }

    public void setOdExfactoryDate(String odExfactoryDate) {
        this.odExfactoryDate = odExfactoryDate;
    }

    @Column(name = "OD_PORTOFLOADING", length = 20)
    public String getOdPortoFloading() {
        return odPortoFloading;
    }

    public void setOdPortoFloading(String odPortoFloading) {
        this.odPortoFloading = odPortoFloading;
    }

    @Column(name = "OD_FOBINCURRENCY", length = 20)
    public String getOdFobinCurrency() {
        return odFobinCurrency;
    }

    public void setOdFobinCurrency(String odFobinCurrency) {
        this.odFobinCurrency = odFobinCurrency;
    }

    @Column(name = "OD_GLOBALTRANSFERPRICE", length = 20)
    public String getOdGlobalTransferPrice() {
        return odGlobalTransferPrice;
    }

    public void setOdGlobalTransferPrice(String odGlobalTransferPrice) {
        this.odGlobalTransferPrice = odGlobalTransferPrice;
    }

    @Column(name = "OD_STATUSINEDIFILE", length = 20)
    public String getOdStatusineDifile() {
        return odStatusineDifile;
    }

    public void setOdStatusineDifile(String odStatusineDifile) {
        this.odStatusineDifile = odStatusineDifile;
    }

    @Column(name = "OD_FROMNODEGLN", length = 20)
    public String getOdFromNodeGLN() {
        return odFromNodeGLN;
    }

    public void setOdFromNodeGLN(String odFromNodeGLN) {
        this.odFromNodeGLN = odFromNodeGLN;
    }

    @Column(name = "OD_TONODEGLN", length = 20)
    public String getOdToNodeGLN() {
        return odToNodeGLN;
    }

    public void setOdToNodeGLN(String odToNodeGLN) {
        this.odToNodeGLN = odToNodeGLN;
    }

    @Column(name = "OD_SKU", length = 20)
    public String getOdSku() {
        return odSku;
    }

    public void setOdSku(String odSku) {
        this.odSku = odSku;
    }

    @Column(name = "OD_ORDERQTY", length = 20)
    public String getOdOrderQty() {
        return odOrderQty;
    }

    public void setOdOrderQty(String odOrderQty) {
        this.odOrderQty = odOrderQty;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "BATCH_ID")
    public BigDecimal getBatchID() {
        return batchID;
    }

    public void setBatchID(BigDecimal batchID) {
        this.batchID = batchID;
    }

    @Column(name = "OD_POREFERENCE", length = 20)
    public String getOdPoreference() {
        return odPoreference;
    }

    public void setOdPoreference(String odPoreference) {
        this.odPoreference = odPoreference;
    }

    @Column(name = "VERSION")
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STA_ID")
    @Index(name = "IDX_ESP_TO_STA")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSFER_STA_ID")
    @Index(name = "IDX_WH_VMI_TRANSFER_STA")
    public StockTransApplication getTransferWHSta() {
        return transferWHSta;
    }

    public void setTransferWHSta(StockTransApplication transferWHSta) {
        this.transferWHSta = transferWHSta;
    }

    @OneToOne
    @JoinColumn(name = "TRANSFER_WH_STA_LINE_ID")
    public StaLine getTransferWHStaLine() {
        return transferWHStaLine;
    }

    public void setTransferWHStaLine(StaLine transferWHStaLine) {
        this.transferWHStaLine = transferWHStaLine;
    }

    @Column(name = "modify_Time")
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INV_CHECK")
    @Index(name = "IDX_WH_VMI_INV_CHECK")
    public InventoryCheck getInvCheck() {
        return invCheck;
    }

    public void setInvCheck(InventoryCheck invCheck) {
        this.invCheck = invCheck;
    }



}
