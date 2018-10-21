package com.jumbo.wms.daemon;


public interface VmiInventoryTask {

    void vmiInventoryJNJ();

    void vmiInventoryStarbucks();

    void vmiInventorySpeedo();

    void vmiInventorySpeedoExt();// 库存调整反馈

    void StatusInventorySpeedoExt();// 库存状态调整反馈

    void vmiInventoryPaulFrank(); // PaulFrank 库存快照

    void vmiInventoryMK();

    void vmiInventoryCK();

    void vmiInventoryRalph();

}
