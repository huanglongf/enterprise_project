package com.jumbo.wms.manager.lmis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.lmis.warehouse.OrderDetailDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.lmis.ExpressWaybill;
import com.jumbo.wms.model.lmis.OrderDetail;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;

@Service("lmisThreadTaskManager")
public class LmisThreadTaskManagerImpl implements LmisThreadTaskManager {
    protected static final Logger log = LoggerFactory.getLogger(LmisThreadTaskManagerImpl.class);
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Autowired
    private SkuDao skuDao;

    @Override
    public void getExpressWaybillDetailData(ExpressWaybill expressWaybill) {
        // 查询并封装线上运单商品耗材信息
        try {
            if (StringUtils.equals(expressWaybill.getPackage_type(), "0")) {
                List<StaAdditionalLine> list = staAdditionalLineDao.findByStaId(Long.parseLong(expressWaybill.getStaId()));
                if (list != null) {
                    for (StaAdditionalLine l : list) {
                        Sku sku = skuDao.getByPrimaryKey(l.getSku().getId());
                        if (sku != null && sku.getLength() != null) {
                            expressWaybill.setSku_number(sku.getBarCode());
                            Double length = sku.getLength().doubleValue();
                            expressWaybill.setLength(length);
                            Double width = sku.getWidth().doubleValue();
                            expressWaybill.setWidth(width);
                            Double higth = sku.getHeight().doubleValue();
                            expressWaybill.setHigth(higth);
                            expressWaybill.setVolumn(length * width * higth);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("lmis get online packageType error!tranNo= " + expressWaybill.getExpress_number(), e);
        }
        // 查询并封装商品明细
        List<Long> skuIds = new ArrayList<Long>();
        String skus = expressWaybill.getSkus();
        if (null != skus && !StringUtils.equals("转店退仓", expressWaybill.getOrder_type())) {
            skus = skus.substring(skus.indexOf(":") + 1);
            String[] strArr = StringUtils.split(skus, ",");
            for (int i = 0; i < strArr.length; i++) {
                String skuId = strArr[i].substring(0, strArr[i].indexOf(";"));
                skuIds.add(Long.parseLong(skuId));
            }
            List<OrderDetail> orderDetails = orderDetailDao.findOrderDetailsByIds(skuIds, expressWaybill.getExpress_number(), Long.parseLong(expressWaybill.getStaId()));
            // 明细中添加运单号字段
            if (orderDetails != null && !orderDetails.isEmpty()) {
                for (OrderDetail orderDetail : orderDetails) {
                    orderDetail.setExpress_number(expressWaybill.getExpress_number());
                }
            }
            expressWaybill.setOrder_details(orderDetails);
        } else if (StringUtils.equals("转店退仓", expressWaybill.getOrder_type())) {
            List<OrderDetail> orderDetails2 = orderDetailDao.findCartonDetialOrderDetailsByIds(expressWaybill.getExpress_number(), Long.parseLong(expressWaybill.getStaId()));
            expressWaybill.setOrder_details(orderDetails2);
        } else {
            expressWaybill.setOrder_details(null);
        }
    }

}
