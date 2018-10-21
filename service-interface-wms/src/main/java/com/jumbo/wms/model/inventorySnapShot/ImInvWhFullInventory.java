package com.jumbo.wms.model.inventorySnapShot;

import java.util.List;

import com.jumbo.wms.model.BaseModel;


/**
 * IM发送指定全量库存实体
 * 
 * @author bin.hu
 *
 */
public class ImInvWhFullInventory extends BaseModel {


    /**
     * 
     */
    private static final long serialVersionUID = -9088426990808369803L;
    /** 库存主体代码 */
    private String ownerCode;
    /** 存货点代码 */
    private List<String> binCode;
    /** SKU编码 */
    private List<String> skuCode;

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public List<String> getBinCode() {
        return binCode;
    }

    public void setBinCode(List<String> binCode) {
        this.binCode = binCode;
    }

    public List<String> getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(List<String> skuCode) {
        this.skuCode = skuCode;
    }



}
