package com.jumbo.wms.manager.vmi;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.cathKidstonData.CKReceiveConfrimDao;
import com.jumbo.dao.vmi.cathKidstonData.CKReceiveDao;
import com.jumbo.dao.vmi.cathKidstonData.CKTransferOutDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockReceiveDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.CartonDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.cathKidstonData.CKReceive;
import com.jumbo.wms.model.vmi.cathKidstonData.CKReceiveConfrim;
import com.jumbo.wms.model.vmi.cathKidstonData.CKTransferOut;
import com.jumbo.wms.model.vmi.cathKidstonData.CathKidstonStatus;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.CartonLine;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

public class VmiCathKidston extends VmiBaseBrand {

    private static final long serialVersionUID = 1238385323509895093L;
    protected static final Logger log = LoggerFactory.getLogger(BaseManagerImpl.class);
    @Autowired
    private CKReceiveDao ckReceiveDao;
    @Autowired
    private StockTransApplicationDao stockTransApplicationDao;
    @Autowired
    private NikeStockReceiveDao nikeStockReceiveDao;
    @Autowired
    private CKReceiveConfrimDao ckReceiveConfrimDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private BiChannelDao biCannelDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private CartonDao cartonDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private CKTransferOutDao ckTransferOutDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StockTransApplicationDao staDao;


    public List<String> generateInboundStaGetAllOrderCode() {
        return ckReceiveDao.findNikeVmiStockIn(new SingleColumnRowMapper<String>(String.class));
    }

    /**
     * 创建入库作业单-根据唯一编码获取一单的入库单数据
     * 
     * @param slipCode
     * @return key:sku extcode2,value:qty
     */
    @Override
    public Map<String, Long> generateInboundStaGetDetial(String slipCode) {
        List<CKReceive> list = ckReceiveDao.getByReferenceNo(slipCode);
        Map<String, Long> detials = new HashMap<String, Long>();
        for (CKReceive ck : list) {
            Long val = detials.get(ck.getUpc());
            if (val == null) {
                detials.put(ck.getUpc(), ck.getQuantity());
            } else {
                detials.put(ck.getUpc(), val + ck.getQuantity());
            }
        }
        return detials;
    }

    /**
     * 创建入库作业单成功后回调方法,用于各个品牌特殊处理
     * 
     * @param slipCode
     * @param staId
     * @param lineDetial 明细行 ,key:extcode2,value:staline id
     */
    @Override
    public void generateInboundStaCallBack(String slipCode, Long staId, Map<String, Long> lineDetial) {
        List<CKReceive> list = ckReceiveDao.getByReferenceNo(slipCode);
        for (CKReceive ck : list) {
            ck.setStaId(stockTransApplicationDao.getByPrimaryKey(staId));
            ck.setLastModifyTime(new Date());
            ckReceiveDao.save(ck);
        }
    }

    /**
     * 创建入库作业单-设置作业单头特殊信息
     * 
     * @param slipCode
     * @param head
     */
    @Override
    public void generateInboundStaSetHead(String slipCode, StockTransApplication head) {
        List<CKReceive> ck = ckReceiveDao.getByReferenceNo(slipCode);
        if (ck.size() > 0) {
            head.setFromLocation(ck.get(0).getFromLocation());
            head.setToLocation(ck.get(0).getToLocation());
        }
    }


    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (sta != null && stv != null) {
            if (sta.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                // ck只接受一次收获反馈 作业单状态必须为完成
                if (!sta.getVmiRCStatus()) {
                    List<StaLine> staline = staLineDao.findByStaId(sta.getId());
                    for (StaLine line : staline) {
                        CKReceiveConfrim ck = new CKReceiveConfrim();
                        Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                        ck.setCreateTime(new Date());
                        ck.setDeliveryNo(sta.getRefSlipCode());
                        ck.setFromLocation(sta.getFromLocation());
                        ck.setToLocation(sta.getToLocation());
                        ck.setStaId(sta);
                        ck.setLastModifyTime(new Date());
                        ck.setStatus(CathKidstonStatus.NEW);
                        ck.setStore(CKReceive.vmiCode);
                        ck.setUpc(sku.getExtensionCode2());
                        ck.setDateTime(sdf.format(new Date()));
                        ck.setQuantity(line.getCompleteQuantity());
                        ckReceiveConfrimDao.save(ck);
                    }
                }
                nikeStockReceiveDao.updateStatusByStaId(sta.getId(), 1);
            }
        }
    }

    /***
     * 入库反馈
     */
    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (sta != null && stv != null) {
            if (sta.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                // ck只接受一次收获反馈 作业单状态必须为完成
                if (!sta.getVmiRCStatus()) {
                    List<StaLine> staline = staLineDao.findByStaId(sta.getId());
                    for (StaLine line : staline) {
                        CKReceiveConfrim ck = new CKReceiveConfrim();
                        Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                        ck.setCreateTime(new Date());
                        ck.setDeliveryNo(sta.getRefSlipCode());
                        ck.setFromLocation(sta.getFromLocation());
                        ck.setToLocation(sta.getToLocation());
                        ck.setStaId(sta);
                        ck.setLastModifyTime(new Date());
                        ck.setStatus(CathKidstonStatus.NEW);
                        ck.setStore(CKReceive.vmiCode);
                        ck.setUpc(sku.getExtensionCode2());
                        ck.setDateTime(sdf.format(new Date()));
                        ck.setQuantity(line.getCompleteQuantity());
                        ckReceiveConfrimDao.save(ck);
                    }
                }
                nikeStockReceiveDao.updateStatusByStaId(sta.getId(), 1);
            }
        }
    }


    @Override
    public void generateReceivingWhenFinished(StockTransApplication sta) {}

    @Override
    public SkuCommand findSkuCommond(String skuCode) {
        return null;
    }

    /**
     * Cath Kidston 退大仓反馈
     */
    @Override
    public void generateRtnWh(StockTransApplication sta) {
        sta = stockTransApplicationDao.getByPrimaryKey(sta.getId());
        if (sta.getType().equals(StockTransApplicationType.VMI_RETURN)) {
            // 现在只支持退大仓
            BiChannel sh = biCannelDao.getByCode(sta.getOwner());
            SimpleDateFormat s = new SimpleDateFormat("yyyyMMdd");
            StockTransVoucher stv = stvDao.findStvByStaId(sta.getId());
            // 生成退仓反馈信息
            List<Carton> cList = cartonDao.findCartonByStaId(sta.getId());// 通过STA查询箱号数据
            for (Carton c : cList) {
                List<CartonLine> clList = cartonDao.findCartonLineByCarId(c.getId());// 查询箱号下的明细
                for (CartonLine cl : clList) {
                    Sku sku = skuDao.getByPrimaryKey(cl.getSku().getId());
                    if (sku == null) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                    // 通过stvid 和 skuid查询 对应stvline中商品的状态
                    Long invS = stvDao.getInvStatus(stv.getId(), sku.getId(), new SingleColumnRowMapper<Long>(Long.class));
                    if (invS == null) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                    CKTransferOut ck = new CKTransferOut();
                    ck.setDeliveryNo(sta.getRefSlipCode());
                    ck.setStaId(sta);
                    ck.setDateTime(s.format(new Date()));
                    ck.setFromLocation(CKReceive.from_Loaction);
                    ck.setToLocation(CKReceive.to_Loaction);
                    ck.setStatus(CathKidstonStatus.NEW);
                    ck.setLastModifyTime(new Date());
                    ck.setCreateTime(new Date());
                    ck.setStore(sh.getVmiCode());
                    String upc = sku.getExtensionCode2();
                    if (StringUtil.isEmpty(upc)) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                    ck.setUpc(upc);
                    ck.setQuantity(cl.getQty());
                    ck.setCartonId(c.getCode());// 箱号
                    InventoryStatus i = inventoryStatusDao.getByPrimaryKey(invS);
                    if (i == null) {
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    }
                    if (i.getName().equals("良品")) {
                        ck.setInvStatus("1");
                    } else {
                        ck.setInvStatus("0");
                    }
                    ckTransferOutDao.save(ck);
                }
            }
        }
    }

    /**
     * CathKidston退大仓出库生成slipCode
     */
    @Override
    public String generateRtnStaSlipCode(String vmiCode, StockTransApplicationType type) {
        String slipCode = "CDT" + String.valueOf(staDao.findCathKidstonRDSEQ(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)));
        return slipCode;
    }

    @Override
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {}
}
