package com.jumbo.wms.model.vmi.Default;

/**
 * 库存优先店铺配置
 */
public class TransferOwnerSourceCommand extends TransferOwnerSource {

    /**
     * 
     */
    private static final long serialVersionUID = -8701191519579265437L;

    private String ownerSourceName;
    private String targetOwnerName;
    private String priorityOwnerName;

    public String getOwnerSourceName() {
        return ownerSourceName;
    }

    public void setOwnerSourceName(String ownerSourceName) {
        this.ownerSourceName = ownerSourceName;
    }

    public String getTargetOwnerName() {
        return targetOwnerName;
    }

    public void setTargetOwnerName(String targetOwnerName) {
        this.targetOwnerName = targetOwnerName;
    }

    public String getPriorityOwnerName() {
        return priorityOwnerName;
    }

    public void setPriorityOwnerName(String priorityOwnerName) {
        this.priorityOwnerName = priorityOwnerName;
    }


}
