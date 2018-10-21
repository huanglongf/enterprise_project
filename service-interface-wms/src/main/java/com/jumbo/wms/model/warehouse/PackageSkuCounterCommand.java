package com.jumbo.wms.model.warehouse;

/**
 * 
 * @author jinlong.ke
 * 
 */
public class PackageSkuCounterCommand extends PackageSkuCounter {

    /**
     * 
     */
    private static final long serialVersionUID = 8684037043347706699L;
    /**
     * 仓库组织节点Id
     */
    private Long ouId;
    /**
     * 商品ID
     */
    private Long skuId;

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

}
