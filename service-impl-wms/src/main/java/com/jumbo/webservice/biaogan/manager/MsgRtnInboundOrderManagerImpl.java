package com.jumbo.webservice.biaogan.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;

@Transactional
public class MsgRtnInboundOrderManagerImpl {

    @Autowired
    private SkuDao skuDao;

    @Autowired
    private MsgRtnInboundOrderLineDao msgLineDao;


    public void updateInOrderSkuId(List<MsgRtnInboundOrderLine> rtnorderLine) {
        // 更新SKU_ID

        for (MsgRtnInboundOrderLine line : rtnorderLine) {
            Sku sku = skuDao.getByCode(line.getSkuCode());
            msgLineDao.updateSkuId(line.getId(), sku.getId());
        }

    }

    public void Insert(Long staid) {

    }


}
