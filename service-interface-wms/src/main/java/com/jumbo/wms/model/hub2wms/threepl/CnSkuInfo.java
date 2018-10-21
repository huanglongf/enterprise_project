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
 * 菜鸟商品中间表
 * 
 * @author hui.li
 * 
 */
@Entity
@Table(name = "T_WH_CN_SKU_INFO")
@org.hibernate.annotations.Entity(optimisticLock = OptimisticLockType.VERSION, dynamicUpdate = true)
public class CnSkuInfo extends BaseModel {

    /**
     * 
     */
    private static final long serialVersionUID = -381597604127892620L;

    private Long id;// 主键

    /**
     * 货主ID
     */
    private String ownerUserId;
    /**
     * 商品ID
     */
    private String itemId;
    /**
     * 商品编码
     */
    private String itemCode;
    /**
     * 条形码，多条码请用”;”分隔；仓库入库扫码使用
     */
    private String barCode;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 操作类型：ADD-新增 MODIFY-修改DELETE-删除 (删除需校验是否还有库存，没库存可删除并修改商品唯一信息)
     */
    private String actionType;
    /**
     * 商品版本 要求入库单中货品不存在时 或者 入库单版本不一致时进行更新，反查菜鸟
     */
    private Long itemVersion;
    /**
     * 商品类型:NORMAL-普通商品、 PACKING_MATERIALS-包材、 CONSUMPTIVE_MATERIALS-耗材
     */
    private String type;
    /**
     * 商品类别编码
     */
    private String category;
    /**
     * 商品类别名称
     */
    private String categoryName;
    /**
     * 品牌编码
     */
    private String brand;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 规格
     */
    private String specification;
    /**
     * 颜色
     */
    private String color;
    /**
     * 尺码
     */
    private String size;
    /**
     * 毛重，单位克
     */
    private Long grossWeight;
    /**
     * 净重，单位克
     */
    private Long netWeight;
    /**
     * 长度，单位毫米
     */
    private Long length;
    /**
     * 长度，单位毫米
     */
    private Long width;
    /**
     * 长度，单位毫米
     */
    private Long height;
    /**
     * 体积，单位立方厘米
     */
    private Long volume;
    /**
     * 箱规
     */
    private Long pcs;
    /**
     * 批准文号
     */
    private Long approvalNumber;
    /**
     * 是否启用批次管理-保质期管理
     */
    private Boolean isShelflife;
    /**
     * 商品保质期天数
     */
    private Integer lifecycle;
    /**
     * 保质期禁收天数
     */
    private Integer rejectLifecycle;
    /**
     * 保质期禁售天数
     */
    private Integer lockupLifecycle;
    /**
     * 保质期临期预警天数
     */
    private Integer adventLifecycle;
    /**
     * 是否启用序列号管理
     */
    private Boolean isSnMgt;
    /**
     * 是否易碎品
     */
    private Boolean isHygroscopic;
    /**
     * 是否危险品
     */
    private Boolean isDanger;
    /**
     * 是否启用批次管理-PO管理
     */
    private Boolean isPoMgt;
    /**
     * 拓展属性数据
     */
    private String extendFields;
    /**
     * 商品计量单位：件、箱、KG等
     */
    private String unit;

    /**
     * 是否操作完成，0，未完成，1，完成
     */
    private Integer status;

    /**
     * systemKey
     */
    private String systemKey;

    /**
     * 宝尊商品编码
     */
    private String skuCode;

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_CN_SKU_INFO", sequenceName = "S_T_WH_CN_SKU_INFO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CN_SKU_INFO")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "OWNER_USER_ID")
    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    @Column(name = "ITEM_ID")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(name = "ITEM_CODE")
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Column(name = "BAR_CODE")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ACTION_TYPE")
    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @Column(name = "ITEM_VERSION")
    public Long getItemVersion() {
        return itemVersion;
    }

    public void setItemVersion(Long itemVersion) {
        this.itemVersion = itemVersion;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "CATEGORY")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(name = "CATEGORY_NAME")
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Column(name = "BRAND")
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column(name = "BRAND_NAME")
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Column(name = "SPECIFICATION")
    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    @Column(name = "COLOR")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "SKU_SIZE")
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Column(name = "GROSS_WEIGHT")
    public Long getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(Long grossWeight) {
        this.grossWeight = grossWeight;
    }

    @Column(name = "NET_WEIGHT")
    public Long getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Long netWeight) {
        this.netWeight = netWeight;
    }

    @Column(name = "LENGTH")
    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    @Column(name = "WIDTH")
    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    @Column(name = "HEIGHT")
    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    @Column(name = "VOLUME")
    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    @Column(name = "PCS")
    public Long getPcs() {
        return pcs;
    }

    public void setPcs(Long pcs) {
        this.pcs = pcs;
    }

    @Column(name = "APPROVAL_NUMBER")
    public Long getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(Long approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    @Column(name = "IS_SHELFLIFE")
    public Boolean getIsShelflife() {
        return isShelflife;
    }

    public void setIsShelflife(Boolean isShelflife) {
        this.isShelflife = isShelflife;
    }

    @Column(name = "LIFECYCLE")
    public Integer getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(Integer lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Column(name = "REJECT_LIFECYCLE")
    public Integer getRejectLifecycle() {
        return rejectLifecycle;
    }

    public void setRejectLifecycle(Integer rejectLifecycle) {
        this.rejectLifecycle = rejectLifecycle;
    }

    @Column(name = "LOCKUP_LIFECYCLE")
    public Integer getLockupLifecycle() {
        return lockupLifecycle;
    }

    public void setLockupLifecycle(Integer lockupLifecycle) {
        this.lockupLifecycle = lockupLifecycle;
    }

    @Column(name = "ADVENT_LIFECYCLE")
    public Integer getAdventLifecycle() {
        return adventLifecycle;
    }

    public void setAdventLifecycle(Integer adventLifecycle) {
        this.adventLifecycle = adventLifecycle;
    }

    @Column(name = "IS_SN_MGT")
    public Boolean getIsSnMgt() {
        return isSnMgt;
    }

    public void setIsSnMgt(Boolean isSnMgt) {
        this.isSnMgt = isSnMgt;
    }

    @Column(name = "IS_HYGROSCOPIC")
    public Boolean getIsHygroscopic() {
        return isHygroscopic;
    }

    public void setIsHygroscopic(Boolean isHygroscopic) {
        this.isHygroscopic = isHygroscopic;
    }

    @Column(name = "IS_DANGER")
    public Boolean getIsDanger() {
        return isDanger;
    }

    public void setIsDanger(Boolean isDanger) {
        this.isDanger = isDanger;
    }

    @Column(name = "IS_PO_MGT")
    public Boolean getIsPoMgt() {
        return isPoMgt;
    }

    public void setIsPoMgt(Boolean isPoMgt) {
        this.isPoMgt = isPoMgt;
    }

    @Column(name = "EXTEND_FIELDS")
    public String getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(String extendFields) {
        this.extendFields = extendFields;
    }

    @Column(name = "UNIT")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "SYSTEM_KEY")
    public String getSystemKey() {
        return systemKey;
    }

    public void setSystemKey(String systemKey) {
        this.systemKey = systemKey;
    }

    @Column(name = "SKU_CODE")
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }



}
