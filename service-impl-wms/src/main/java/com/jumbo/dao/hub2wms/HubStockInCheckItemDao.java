package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.bizhub.model.taobao.cainiao.HubStockInOrderConfirmRequest.HubStockInCheckItem;

@Transactional
public interface HubStockInCheckItemDao extends GenericEntityDao<HubStockInCheckItem, Long> {
    /**
     * 通过 作业单号查询菜鸟入库确认订单商品校验信息
     * 
     * @param orderCode
     * @return BeanPropertyRowMapper<MaterialFeePurchaseDetails> r
     */
    @NativeQuery
    List<HubStockInCheckItem> getCheckItemByStaId(@QueryParam(value = "staId") long staId, BeanPropertyRowMapper<HubStockInCheckItem> rowMapper);
}
