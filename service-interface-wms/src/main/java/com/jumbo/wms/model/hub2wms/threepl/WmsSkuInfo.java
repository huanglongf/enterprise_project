package com.jumbo.wms.model.hub2wms.threepl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * WMS外包仓 商品信息
 * 
 * @author xiaolong.fei
 * 
 */
public class WmsSkuInfo implements Serializable {
    private static final long serialVersionUID = -4830756192706112836L;

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

    private List<SnSample> snSampleList;

    public static class SnSample implements Serializable {
        private static final long serialVersionUID = -8090597996676386157L;
        /**
         * sn示例顺序
         */
        private String snSeq;
        /**
         * sn示例描述
         */
        private String sampleDesc;

        /**
         * 示例规则列表
         */
        private List<SampleRule> sampleRuleList;

        public static class SampleRule implements Serializable {
            private static final long serialVersionUID = 4987372371346658114L;
            /**
             * 规则描述
             */
            private String ruleDesc;
            /**
             * 规则正则表达式
             */
            private String ruleRegularExpression;
            /**
             * 规则对应图面url
             */
            private String ruleImgUrl;
            /**
             * 规则示例
             */
            private String ruleSample;

            public String getRuleDesc() {
                return ruleDesc;
            }

            public void setRuleDesc(String ruleDesc) {
                this.ruleDesc = ruleDesc;
            }

            public String getRuleRegularExpression() {
                return ruleRegularExpression;
            }

            public void setRuleRegularExpression(String ruleRegularExpression) {
                this.ruleRegularExpression = ruleRegularExpression;
            }

            public String getRuleImgUrl() {
                return ruleImgUrl;
            }

            public void setRuleImgUrl(String ruleImgUrl) {
                this.ruleImgUrl = ruleImgUrl;
            }

            public String getRuleSample() {
                return ruleSample;
            }

            public void setRuleSample(String ruleSample) {
                this.ruleSample = ruleSample;
            }

        }

        public String getSnSeq() {
            return snSeq;
        }

        public void setSnSeq(String snSeq) {
            this.snSeq = snSeq;
        }

        public String getSampleDesc() {
            return sampleDesc;
        }

        public void setSampleDesc(String sampleDesc) {
            this.sampleDesc = sampleDesc;
        }

        public List<SampleRule> getSampleRuleList() {
            if (this.sampleRuleList == null) {
                sampleRuleList = new ArrayList<WmsSkuInfo.SnSample.SampleRule>();
            }
            return sampleRuleList;
        }

        public void setSampleRuleList(List<SampleRule> sampleRuleList) {
            this.sampleRuleList = sampleRuleList;
        }

    }

    public String getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(String ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Long getItemVersion() {
        return itemVersion;
    }

    public void setItemVersion(Long itemVersion) {
        this.itemVersion = itemVersion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(Long approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    public Boolean getIsShelflife() {
        return isShelflife;
    }

    public void setIsShelflife(Boolean isShelflife) {
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

    public Boolean getIsSnMgt() {
        return isSnMgt;
    }

    public void setIsSnMgt(Boolean isSnMgt) {
        this.isSnMgt = isSnMgt;
    }

    public Boolean getIsHygroscopic() {
        return isHygroscopic;
    }

    public void setIsHygroscopic(Boolean isHygroscopic) {
        this.isHygroscopic = isHygroscopic;
    }

    public Boolean getIsDanger() {
        return isDanger;
    }

    public void setIsDanger(Boolean isDanger) {
        this.isDanger = isDanger;
    }

    public Boolean getIsPoMgt() {
        return isPoMgt;
    }

    public void setIsPoMgt(Boolean isPoMgt) {
        this.isPoMgt = isPoMgt;
    }

    public String getExtendFields() {
        return extendFields;
    }

    public void setExtendFields(String extendFields) {
        this.extendFields = extendFields;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<SnSample> getSnSampleList() {
        if (this.snSampleList == null) {
            this.snSampleList = new ArrayList<WmsSkuInfo.SnSample>();
        }
        return snSampleList;
    }

    public void setSnSampleList(List<SnSample> snSampleList) {
        this.snSampleList = snSampleList;
    }

}
