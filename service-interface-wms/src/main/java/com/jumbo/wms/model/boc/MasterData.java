package com.jumbo.wms.model.boc;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jumbo.wms.model.BaseModel;
import com.jumbo.wms.model.DefaultStatus;

/**
 * 商品主档数据
 * 
 * @author wudan
 * 
 */
@Entity
@Table(name = "T_VMI_MASTER_DATA")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION)
public class MasterData extends BaseModel {


    private static final long serialVersionUID = 3844540282001053345L;

    private Long id;

    private int version;

    private Date createTime;

    /**
     * 商品唯一编码
     */
    private String upc;

    /**
     * 商品条码
     */
    private String barCode;

    /**
     * 货号
     */
    private String articleNumber;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 中文名称
     */
    private String name;

    /**
     * 英文名称
     */
    private String enName;

    /**
     * 市场价
     */
    private BigDecimal listPrice;

    /**
     * 颜色
     */
    private String color;

    /**
     * 尺码1
     */
    private String size1;

    /**
     * 尺码2
     */
    private String size2;

    /**
     * 年份
     */
    private String year;

    /**
     * 季节
     */
    private String season;

    /**
     * 分类
     */
    private String category;

    /**
     * 长度
     */
    private BigDecimal length;

    /**
     * 宽度
     */
    private BigDecimal width;

    /**
     * 高度
     */
    private BigDecimal height;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 用户自定义1
     */
    private String userDefined1;

    /**
     * 用户自定义2
     */
    private String userDefined2;

    /**
     * 用户自定义3
     */
    private String userDefined3;

    /**
     * 用户自定义4
     */
    private String userDefined4;

    /**
     * 用户自定义5
     */
    private String userDefined5;


    /**
     * 状态
     */
    private DefaultStatus status;

    /**
     * 来源
     */
    private String source;
    
    /**
     * Mq confirmId
     * @return
     */
    private String confirmId;
    
    
    
    private String fileName;


    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_MD", sequenceName = "S_T_VMI_MASTER_DATA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MD")
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
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "UPC", length = 30)
    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    @Column(name = "BARCODE", length = 30)
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "ARTICLE_NUMBER", length = 50)
    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    @Column(name = "BRAND", length = 50)
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "NAME", length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "EN_NAME", length = 100)
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Column(name = "LIST_PRICE", precision = 10, scale = 2)
    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    @Column(name = "COLOR", length = 20)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "SIZE1", length = 20)
    public String getSize1() {
        return size1;
    }

    public void setSize1(String size1) {
        this.size1 = size1;
    }

    @Column(name = "SIZE2", length = 20)
    public String getSize2() {
        return size2;
    }

    public void setSize2(String size2) {
        this.size2 = size2;
    }

    @Column(name = "YEAR", length = 20)
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Column(name = "SEASON", length = 20)
    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    @Column(name = "CATEGORY", length = 50)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(name = "LENGTH", precision = 10, scale = 2)
    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    @Column(name = "WIDTH", precision = 10, scale = 2)
    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    @Column(name = "HEIGHT", precision = 10, scale = 2)
    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    @Column(name = "WEIGHT", precision = 10, scale = 2)
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Column(name = "USERDEFINED1", length = 100)
    public String getUserDefined1() {
        return userDefined1;
    }

    public void setUserDefined1(String userDefined1) {
        this.userDefined1 = userDefined1;
    }

    @Column(name = "USERDEFINED2", length = 100)
    public String getUserDefined2() {
        return userDefined2;
    }

    public void setUserDefined2(String userDefined2) {
        this.userDefined2 = userDefined2;
    }

    @Column(name = "USERDEFINED3", length = 100)
    public String getUserDefined3() {
        return userDefined3;
    }

    public void setUserDefined3(String userDefined3) {
        this.userDefined3 = userDefined3;
    }

    @Column(name = "USERDEFINED4", length = 100)
    public String getUserDefined4() {
        return userDefined4;
    }

    public void setUserDefined4(String userDefined4) {
        this.userDefined4 = userDefined4;
    }

    @Column(name = "USERDEFINED5", length = 100)
    public String getUserDefined5() {
        return userDefined5;
    }

    public void setUserDefined5(String userDefined5) {
        this.userDefined5 = userDefined5;
    }

    @Column(name = "STATUS", columnDefinition = "integer")
    @Type(type = "loxia.dao.support.GenericEnumUserType", parameters = {@Parameter(name = "enumClass", value = "com.jumbo.wms.model.DefaultStatus")})
    public DefaultStatus getStatus() {
        return status;
    }

    public void setStatus(DefaultStatus status) {
        this.status = status;
    }

    @Column(name = "SOURCE", length = 100)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "CONFIRMID", length = 50)
    public String getConfirmId() {
        return confirmId;
    }

    public void setConfirmId(String confirmId) {
        this.confirmId = confirmId;
    }

    @Column(name = "FILE_NAME", length = 50)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
}
