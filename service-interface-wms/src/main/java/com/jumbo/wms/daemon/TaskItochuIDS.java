package com.jumbo.wms.daemon;

public interface TaskItochuIDS {
    /**
     * AEO库存
     */
    public void insertAeoInventory();

    public void insertAeoJDInventory();

    /**
     * AF库存
     */
    public void insertAFInventory();

    /**
     * NewLook库存
     */
    public void insertNewLookInventory();


    void insertConverseInventoryLog();

    /**
     * UaNba库存
     */
    public void insertUaNbaInventory();

    /**
     * NIKE库存
     */
    public void insertNikeInventory();

    /**
     * NIKE库存广州
     */
    public void insertNikeInventoryGZ();

    /**
     * NIKE库存
     */
    public void insertNikeInventoryTM();

    void insertIDSVSInventoryLog();

    /**
     * NIKE库存广州
     */
    public void insertNikeInventoryGZTM();


    /**
     * NewLook库存
     */
    void insertNewLookJDInventory();

    void insertNikeNewInventory();//WH_NIKE_WH_SF
    
    
    void insertNikeNewInventory2();//WH_OOCL


    void insertNikeCrwInventory();



}
