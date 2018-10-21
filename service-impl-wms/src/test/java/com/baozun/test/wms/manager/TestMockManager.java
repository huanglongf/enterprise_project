package com.baozun.test.wms.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.TransDeliveryType;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.TransType;


/**
 * 测试样例类，测试manager定义
 * 
 * @author jingkai
 *
 */
@Service("testMockManager")
@Transactional
public class TestMockManager {
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StaLineDao staLineDao;
    @Resource
    private WareHouseManager wareHouseManager;

    public Boolean mockManager(Long id) {
        return wareHouseManager.isCodByStaId(id);
    }

    public Long testMock(Long a) {
        return a;
    }

    public StockTransApplication testMockStaDao(Long id) {
        return staDao.getByPrimaryKey(id);
    }


    /**
     * 创建测试作业单
     * 
     * @author jingkai
     * @param whouId 仓库
     * @param owner 店铺
     * @param skus 商品及数量
     */
    public StockTransApplication crateSalesOutboundSta(Long whouId, String owner, Map<String, Long> skus) {
        // 创建STA
        StockTransApplication sta = new StockTransApplication();
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        OperationUnit ou1 = operationUnitDao.getByPrimaryKey(whouId);
        String code = "ST" + System.currentTimeMillis();
        sta.setRefSlipCode(code);
        sta.setSlipCode1(code);
        sta.setSlipCode2(code);
        sta.setSlipCode3(code);
        sta.setOwner(owner);
        sta.setStorecode(owner);
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setLastModifyTime(new Date());
        sta.setType(StockTransApplicationType.OUTBOUND_SALES);
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setVersion(1);
        sta.setChannelList(owner);
        sta.setIsNeedOccupied(true);
        sta.setMainWarehouse(ou1);
        sta.setTotalActual(new BigDecimal(1000));
        sta.setOrderCreateTime(new Date());
        sta.setIsSpecialPackaging(false);
        sta.setOrderTotalActual(new BigDecimal(1000));
        sta.setOrderTransferFree(new BigDecimal(0));
        staDao.save(sta);

        staDao.flush();
        // 配送信息
        StaDeliveryInfo di = new StaDeliveryInfo();
        di.setProvince("上海");
        di.setCity("上海市");
        di.setDistrict("静安区");
        di.setAddress("上海 上海市 静安区 XXXXXXXXXXX");
        di.setTelephone("66666666");
        di.setMobile("1300000000");
        di.setReceiver("张三");
        di.setZipcode("200000");
        di.setIsCod(false);
        di.setLpCode("SF");
        di.setTransferFee(new BigDecimal(0));
        di.setTransType(TransType.ORDINARY);
        di.setTransTimeType(TransTimeType.ORDINARY);
        di.setId(sta.getId());
        di.setLastModifyTime(new Date());
        sta.setDeliveryType(TransDeliveryType.ORDINARY);
        staDeliveryInfoDao.save(di);
        sta.setStaDeliveryInfo(di);
        staDao.flush();

        // 创建单据明细
        for (Entry<String, Long> skuData : skus.entrySet()) {
            Sku sku = skuDao.getByCode(skuData.getKey());
            StaLine sl = new StaLine();
            sl.setSta(sta);
            sl.setSku(sku);
            sl.setOwner(owner);
            sl.setQuantity(skuData.getValue());
            sl.setVersion(1);
            sl.setSkuName(sku.getName());
            sl.setTotalActual(new BigDecimal(100));
            sl.setUnitPrice(new BigDecimal(100));
            staLineDao.save(sl);
        }
        staDao.flush();
        return sta;
    }


    public StockTransApplication getSta() {
        return null;
    }

    /**
     * 清理测试单据
     * 
     * @author jingkai
     * @param staCode
     */
    public void removeTestData(String staCode) {
        StockTransApplication sta = staDao.getByCode(staCode);
        // 删除明细行
        staLineDao.deleteStaLineByStaId(sta.getId());
        staDeliveryInfoDao.deleteByPrimaryKey(sta.getId());
        staDao.deleteByPrimaryKey(sta.getId());
    }
}
