package com.jumbo.wms.model.boc;



public class VmiInventorySnapshotDataCommand extends VmiInventorySnapshotData {

    /**
     * 
     */
    private static final long serialVersionUID = -2562301990329875921L;

    private Long inventoryStatusId;

    public Long getInventoryStatusId() {
        return inventoryStatusId;
    }

    public void setInventoryStatusId(Long inventoryStatusId) {
        this.inventoryStatusId = inventoryStatusId;
    }


}
