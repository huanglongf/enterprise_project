package com.jumbo.wms.manager.vmi;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.nikeDate.NikeCheckReceiveDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockInDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockReceiveDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryCheckDifTotalLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.webservice.nike.manager.NikeManager;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

public class VmiNike2 extends VmiBaseBrand {
    private static final long serialVersionUID = -3176887352008501982L;
    protected static final Logger log = LoggerFactory.getLogger(BaseManagerImpl.class);
    @Autowired
    private NikeStockInDao nikeStockInDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private NikeCheckReceiveDao nikeCheckReceiveDao;
    @Autowired
    private InventoryCheckDifTotalLineDao icLineDao;
    @Autowired
    private NikeManager nikeManager;
    @Autowired
    private NikeStockReceiveDao nikeStockReceiveDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private BiChannelDao companyShopDao;

    /**
     * 根据中间表创建入库单据
     */
    @Override
    public void generateInboundSta() {}

    /***
     * 入库反馈
     */
    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {}

    public void createVMIReceiveInfoByInvCk(InventoryCheck inv) {}

    /**
     * Vmi调整单据反馈
     * 
     * @param inv
     */
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {}

    /****
     * 转店出库反馈 = 转店退仓， 转店入库反馈
     */
    @Transactional
    public void generateReceivingTransfer(StockTransApplication stockTransApplication) {}

    /**
     * 入库作业当上架时反馈
     * 
     * @param sta
     * @param stv
     */
    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {
        // DO NOTHING
    }

    public void createInboundSta() {}

    public void createReceivingTransfer(StockTransApplication sta) {}

    public void createReceivingWithReceive(StockTransApplication sta, StockTransVoucher stv) {}

    public void createVMITranscationWH() {}

    /**
     * NIKE 退仓反馈 100为A品，120为B品，101为C品 100 CC 良品 120 DR 待处理 101 SS 残次品
     */
    public void generateRtnWh(StockTransApplication stockTransApplication) {

    }

    @Override
    public void generateReceivingWhenFinished(StockTransApplication sta) {}

    /**
     * 创建入库作业单成功后回调方法,用于各个品牌特殊处理
     * 
     * @param slipCode
     * @param staId
     * @param lineDetial 明细行 ,key:extcode2,value:staline id
     */
    @Override
    public void generateInboundStaCallBack(String slipCode, Long staId, Map<String, Long> lineDetial) {}

    /**
     * 创建入库作业单-设置作业单头特殊信息
     * 
     * @param slipCode
     * @param head
     */
    @Override
    public void generateInboundStaSetHead(String slipCode, StockTransApplication head) {}



}
