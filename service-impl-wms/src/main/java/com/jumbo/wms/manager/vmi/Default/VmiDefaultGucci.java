package com.jumbo.wms.manager.vmi.Default;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.pac.manager.extsys.wms.rmi.model.OperationBillLine;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.manager.vmi.gucciData.GucciManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.Default.VmiRsnLineDefault;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.StvLineCommand;

/**
 * Gucci品牌定制逻辑
 * 
 * 
 */
@Service("vmiDefaultGucci")
public class VmiDefaultGucci extends BaseVmiDefault {

    @Autowired
    private GucciManager gucciManager;

    @Override
    public void generateInboundStaSetHeadDefault(String slipCode, StockTransApplication head) {}

    @Override
    public void generateInboundStaSetSlipCodeOwner(BiChannelCommand shop, StockTransApplication sta) {}

    @Override
    public void generateReceivingWhenShelv(ExtParam ext, VmiRsnLineDefault v) {}

    @Override
    public void generateReceivingWhenClose(Long staid) {}

    @Override
    public void generateSaveSkuBatch(String batchCode, StockTransApplication sta, List<StvLine> stvlineList) {}

    @Override
    public void transferOmsOutBound(OperationBillLine line, StvLineCommand sc) {}

    @Override
    public void generateVmiInboundStaLine(StockTransApplication sta, StaLine line, String upc) throws Exception {}

    @Override
    public void generateInsertVmiInventory(ExtParam ext) {}

    @Override
    public boolean importForBatchReceiving() {
        return false;
    }

    @Override
    public void importForBatchReceivingSaveStvLine(StvLine stvLine, StockTransApplication sta, Sku sku) {}

    @Override
    public void generateVmiOutBound(ExtParam ext) {}

    /**
     * 生成Gucci退货入库反馈数据
     */
    @Override
    public void generateVmiInBound(ExtParam ext) {
        gucciManager.generateSaleReturnInboundData(ext.getSta(), ext.getStv());
    }

    @Override
    public List<InventoryCommand> findVmiInventoryByOwner(List<String> ownerList) {
        return new ArrayList<InventoryCommand>();
    }
}
