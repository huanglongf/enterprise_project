package com.jumbo.wms.manager.vmi.Default;

import java.util.Date;
import java.util.List;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.vmi.Default.VmiAdjCommand;
import com.jumbo.wms.model.vmi.Default.VmiAsnCommand;
import com.jumbo.wms.model.vmi.Default.VmiRsnCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtwCommand;
import com.jumbo.wms.model.vmi.Default.VmiStatusAdjCommand;
import com.jumbo.wms.model.vmi.Default.VmiTfxCommand;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;

public interface VmiDefaultManager extends BaseManager {



    void summaryInventoryToEmail();

    void createStaForVmiDefault(String vmiCode, String vmiSource);

    void uploadVmiRsnToHub(VmiRsnCommand vr);

    void uploadVmiRsnToHubPumaExt(List<VmiRsnCommand> vrList);

    List<VmiAsnCommand> findVmiAsnAll();

    void updateVmiRsnStatus(String vmiCode, String vmiSource);

    void updateVmiAsnStatus(String vmiCode, String vmiSource);

    List<VmiRsnCommand> findVmiRsnAll();

    List<VmiRsnCommand> findVmiRsnAllExt(String vmiCode);

    void updateVmiErrorCount(String vmiType, Long id);

    void updateVmiErrorCountExt(String vmiType, List<Long> idList);

    void uploadVmiTfxToHub(VmiTfxCommand vr);

    List<VmiTfxCommand> findVmiTfxAll();

    List<VmiRtwCommand> findVmiRtwAll();

    List<VmiRtwCommand> findVmiRtwAllExt(String vmiCode);

    List<VmiRtwCommand> findPumaVmiRtwAllExt(String vmiCode);

    List<VmiRtwCommand> findPumaNotHasLineNoVmiRtwAllExt(String vmiCode);

    void uploadVmiRtwToHub(VmiRtwCommand vr);

    void uploadVmiRtwToHubPumaExt(List<VmiRtwCommand> vrList);

    void uploadVmiAdjToHub(VmiAdjCommand vr);

    void uploadVmiAdjToHubPumaExt(List<VmiAdjCommand> vaList);

    List<VmiAdjCommand> findVmiAdjAll();

    List<VmiAdjCommand> findVmiAdjAllExt(String vmiCode);

    void vmiAsnErrorEmailInform();

    void vmiRsnErrorEmailInform();

    void vmiTfxErrorEmailInform();

    void vmiAdjErrorEmailInform();

    void vmiRtwErrorEmailInform();

    void insertTotalInvPumaExt(Date date, String batchNo, String vmiCode);

    void uploadTotalInvToHubPumaExt(Date date, String batchNo, String vmiCode);

    void uploadVmiOutBoundToHub(String defaultCode);

    void uploadVmiInBoundToHub(String defaultCode);

    void pushNotSalesOutboundDataToHub(String vmiCode);// 非销售出库数据

    void pushNotSalesInboundDataToHub(String vmiCode);// 非VMI入库以及退货入库数据

    /**
     * jinggang.chen
     * 
     * @param vr
     */
    void uploadVmiAdjToHubSpeedoExt(VmiAdjCommand vr);// speedo库存调整反馈

    void uploadStatusAdjToHubSpeedoExt(VmiStatusAdjCommand sa);// speedo库存状态调整反馈

    List<StockTransTxLogCommand> findAdjLog(String staCode, int direction);
}
