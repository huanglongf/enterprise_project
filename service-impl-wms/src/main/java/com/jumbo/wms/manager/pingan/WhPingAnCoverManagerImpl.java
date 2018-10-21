package com.jumbo.wms.manager.pingan;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuCategoriesDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.pingan.WhPingAnCoverDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.wms.model.pingan.WhPingAnCover;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;


@Transactional
@Service("whPingAnCoverManager")
public class WhPingAnCoverManagerImpl implements WhPingAnCoverManager {


    private static final long serialVersionUID = -4183092559613347372L;

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WhPingAnCoverDao whPingAnCoverDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private SkuCategoriesDao skuCategoriesDao;

    /**
     * 创建平安投保数据
     * 
     * @param staid 作业单ID
     * @param ouid 仓库ID
     * @param trackingNumber 运单号
     */
    @Override
    public void createPingAnCover(Long staid, Long ouid, String trackingNumber, String expressCode) {
        StockTransApplication sta = staDao.getByPrimaryKey(staid);
        WhPingAnCover pa = new WhPingAnCover();
        pa.setTransSerialNo(trackingNumber);
        pa.setStaCode(sta.getCode());
        pa.setConveyanceNo(trackingNumber);
        pa.setConsignorDate(new Date());
        pa.setSigningOddDate(new Date());
        pa.setMerit(sta.getTotalActual());
        pa.setExpressCode(expressCode);
        List<StaLine> line = staLineDao.findByStaId(staid);
        // 货物介绍
        String cargoInfo = "";
        // 货物描述
        String cargoRiskDepict = "";
        boolean b = true;
        for (StaLine l : line) {
            Sku sku = skuDao.getByPrimaryKey(l.getSku().getId());
            String c = "【" + sku.getBarCode() + "-" + sku.getSupplierCode() + "-" + sku.getName() + "-" + l.getQuantity() + "】";
            cargoInfo += c;
            cargoRiskDepict += "【" + sku.getName() + "】";
            if (b) {
                // 需要验证大类和细类
                b = checkBigTypeAndDetailType(pa, sku.getSkuCategoriesId());
            }
        }
        if (cargoInfo.length() > 200) {
            pa.setCargoInfo(cargoInfo.substring(0, 190));
        } else {
            pa.setCargoInfo(cargoInfo);
        }
        // 超过200 截取字符串
        if (cargoRiskDepict.length() > 200) {
            pa.setCargoRiskDepict(cargoRiskDepict.substring(0, 190));
        } else {
            pa.setCargoRiskDepict(cargoRiskDepict);
        }
        whPingAnCoverDao.save(pa);
    }

    /**
     * 验证货物大类和货物细类
     */
    private Boolean checkBigTypeAndDetailType(WhPingAnCover pa, Long skuCategoriesId) {
        boolean b = true;
        if (skuCategoriesId == null) {
            pa.setCargoBigType(WhPingAnCover.cargoBigTypeX);
            pa.setCargoDetailType(WhPingAnCover.cargoDetailTypeX);
        } else {
            SkuCategories s = skuCategoriesDao.getByPrimaryKey(skuCategoriesId);
            if (s == null) {
                pa.setCargoBigType(WhPingAnCover.cargoBigTypeX);
                pa.setCargoDetailType(WhPingAnCover.cargoDetailTypeX);
            } else {
                if (s.getSkuCategoriesName().equals("鞋子类")) {
                    // 如果包含鞋子类 直接12-6603
                    pa.setCargoBigType(WhPingAnCover.cargoBigTypeX);
                    pa.setCargoDetailType(WhPingAnCover.cargoDetailTypeX);
                    b = false;
                } else if (s.getSkuCategoriesName().equals("衣服类") || s.getSkuCategoriesName().equals("配件类")) {
                    // 衣服类 配件类 11-5407
                    pa.setCargoBigType(WhPingAnCover.cargoBigTypeY);
                    pa.setCargoDetailType(WhPingAnCover.cargoDetailTypeY);
                } else {
                    // 非鞋子类 衣服类 配件类 12-6603
                    pa.setCargoBigType(WhPingAnCover.cargoBigTypeX);
                    pa.setCargoDetailType(WhPingAnCover.cargoDetailTypeX);
                }
            }
        }
        return b;
    }

}
