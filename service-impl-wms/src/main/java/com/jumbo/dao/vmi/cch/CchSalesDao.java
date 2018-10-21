package com.jumbo.dao.vmi.cch;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import com.jumbo.wms.model.vmi.cch.CchSales;

@Transactional
public interface CchSalesDao extends GenericEntityDao<CchSales, Long> {

    @NativeUpdate
    public void insertDataBySO(@QueryParam("shopId") Long shopId, @QueryParam("shopCode") String shopCode, @QueryParam("deliveryDate") Date deliveryDate, @QueryParam("batchNum") String batchNum);

    @NamedQuery
    public List<CchSales> findSalesByBatchNum(@QueryParam("batchNum") String batchNum, @QueryParam("status") Integer status);

    @NativeUpdate
    public void updateStatusByBatchNum(@QueryParam("fromStatus") Integer fromStatus, @QueryParam("toStatus") Integer toStatus, @QueryParam("batchNum") String batchNum);
}
