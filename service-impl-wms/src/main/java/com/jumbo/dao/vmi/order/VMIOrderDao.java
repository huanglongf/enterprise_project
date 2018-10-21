package com.jumbo.dao.vmi.order;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.order.VMIOrder;
import com.jumbo.wms.model.vmi.order.VMIOrderCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface VMIOrderDao extends GenericEntityDao<VMIOrder, Long> {

    @NativeQuery
    public List<VMIOrderCommand> findByShopANDLog(@QueryParam("opType") String opType, @QueryParam("shopId") Long shopId, RowMapper<VMIOrderCommand> rowMapper);

    @NamedQuery
    public List<VMIOrder> findByShopANDStatus(@QueryParam("status") Integer status, @QueryParam("shopId") Long shopId);

    @NativeUpdate
    public int updateOrderStatusById(@QueryParam("orderId") Long orderId, @QueryParam("toStatus") Integer toStatus);

    @NativeQuery
    public VMIOrderCommand findByShopANDOrderCode(@QueryParam("opType") String opType, @QueryParam("shopId") Long shopId, @QueryParam("orderCode") String orderCode, RowMapper<VMIOrderCommand> rowMapper);

}
