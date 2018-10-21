package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Item implements Serializable {

    private static final long serialVersionUID = 2677411772962186134L;
    /**
     * 商品ID
     */
    private Long itemId;
    /**
     * 商家对商品的编码
     */
    private String itemCode;
    /**
     * 版本号
     */
    private Integer version;
    /**
     * 商品名称
     */
    private String itemName;
    /**
     * 货主ID
     */
    private Long providerTpId;
    /**
     * 条形码，多条码请用”;”分隔；
     */
    private String barCode;
    /**
     * 仓库入库扫码使用
     */
    private String type;
    /**
     * 商品类型:NORMAL-普通商品、 PACKING_MATERIALS-包材、 CONSUMPTIVE_MATERIALS-耗材
     */
    private String brand;
    /**
     * 品牌编码
     */
    private String brandName;
    /**
     * 品牌名称
     */
    private String specification;
    /**
     * 规格
     */
    private String color;
    /**
     * 颜色
     */
    private String size;
    /**
     * 尺码
     */
    private String approvalNumber;
    /**
     * 批准文号
     */
    private Long grossWeight;
    /**
     * 毛重，单位克
     */
    private Long netWeight;
    /**
     * 净重，单位克
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
     * 箱规，1个箱子里存放多少个零头商品
     */
    private Long pcs;
    /**
     * 是否启用批次管理-保质期管理
     */
    boolean isShelflife;
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
    private boolean isSnMgt;
    /**
     * 是否危险品
     */
    private boolean isHygroscopic;
    /**
     * 是否易碎品
     */
    private boolean isDanger;
    /**
     * 是否启用批次管理-PO管理
     */
    private boolean isPoMsg;
    /**
     * 拓展属性
     */
    private Map<Object, Object> extendFields;
    /**
     * 商品计量单位：件、箱、KG等
     */
    private String unit;
    /**
     * 商品类别编码
     */
    private String category;
    /**
     * 商品类别名称
     */
    private String categoryName;
    /**
     * sn示例列表
     */
    private List<SnSample> snSampleList;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getProviderTpId() {
        return providerTpId;
    }

    public void setProviderTpId(Long providerTpId) {
        this.providerTpId = providerTpId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    public Long getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(Long grossWeight) {
        this.grossWeight = grossWeight;
    }

    public Long getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(Long netWeight) {
        this.netWeight = netWeight;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Long getPcs() {
        return pcs;
    }

    public void setPcs(Long pcs) {
        this.pcs = pcs;
    }

    public boolean isShelflife() {
        return isShelflife;
    }

    public void setShelflife(boolean isShelflife) {
        this.isShelflife = isShelflife;
    }

    public Integer getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(Integer lifecycle) {
        this.lifecycle = lifecycle;
    }

    public Integer getRejectLifecycle() {
        return rejectLifecycle;
    }

    public void setRejectLifecycle(Integer rejectLifecycle) {
        this.rejectLifecycle = rejectLifecycle;
    }

    public Integer getLockupLifecycle() {
        return lockupLifecycle;
    }

    public void setLockupLifecycle(Integer lockupLifecycle) {
        this.lockupLifecycle = lockupLifecycle;
    }

    public Integer getAdventLifecycle() {
        return adventLifecycle;
    }

    public void setAdventLifecycle(Integer adventLifecycle) {
        this.adventLifecycle = adventLifecycle;
    }

    public boolean isSnMgt() {
        return isSnMgt;
    }

    public void setSnMgt(boolean isSnMgt) {
        this.isSnMgt = isSnMgt;
    }

    public boolean isHygroscopic() {
        return isHygroscopic;
    }

    public void setHygroscopic(boolean isHygroscopic) {
        this.isHygroscopic = isHygroscopic;
    }

    public boolean isDanger() {
        return isDanger;
    }

    public void setDanger(boolean isDanger) {
        this.isDanger = isDanger;
    }

    public boolean isPoMsg() {
        return isPoMsg;
    }

    public void setPoMsg(boolean isPoMsg) {
        this.isPoMsg = isPoMsg;
    }

    public Map<Object, Object> getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(Map<Object, Object> extendFields) {
        this.extendFields = extendFields;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<SnSample> getSnSampleList() {
        return snSampleList;
    }

    public void setSnSampleList(List<SnSample> snSampleList) {
        this.snSampleList = snSampleList;
    }
}
