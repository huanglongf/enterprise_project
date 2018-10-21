package com.jumbo.service;

import java.util.List;

import com.jumbo.rmi.warehouse.WmsResponse;
import com.jumbo.rmi.warehouse.vmi.VmiAdj;
import com.jumbo.rmi.warehouse.vmi.VmiInBound;
import com.jumbo.rmi.warehouse.vmi.VmiInventory;
import com.jumbo.rmi.warehouse.vmi.VmiOutBound;
import com.jumbo.rmi.warehouse.vmi.VmiRsn;
import com.jumbo.rmi.warehouse.vmi.VmiRtw;
import com.jumbo.rmi.warehouse.vmi.VmiTfx;

public interface HubService {

    public WmsResponse transferOutConfirm(VmiTfx vmiTfx);

    public WmsResponse deliveryNoteReceiptConfirm(VmiRsn vmiRsn);

    public WmsResponse inventoryAdjustment(VmiAdj vmiAdj);

    public WmsResponse returnToVendor(VmiRtw vmiRtw);
    
    /**
     * PUMA收货反馈
     * 
     * @return
     * @Description
     */
    public WmsResponse pumaRec(List<VmiRsn> vmiRsnList);

    /**
     * PUMA库存调整
     * 
     * @param vmiAdjList
     * @return
     * @Description
     */
    public WmsResponse pumaItr(List<VmiAdj> vmiAdjList);

    /**
     * PUMA退大仓
     * 
     * @param vmiRtwList
     * @return
     * @Description
     */
    public WmsResponse pumaShp(List<VmiRtw> vmiRtwList);

    /**
     * PUMA全量库存快照
     * 
     * @return
     * @Description
     */
    public WmsResponse pumaSoh(List<VmiInventory> vmiInvList);

    WmsResponse inventorySnapshot(List<VmiInventory> list);

    /**
     * 出库反馈通知HUB
     * 
     * @return
     */
    WmsResponse transferVmiOutBound(List<VmiOutBound> list);

    /**
     * 退换货入反馈通知HUB
     * 
     * @return
     */
    WmsResponse transferVmiInBound(List<VmiInBound> list);

}
