package com.jumbo.dao.vmi.espData;

//import java.math.BigDecimal;
//import java.util.List;

//import loxia.annotation.NativeQuery;
//import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

//import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.jumbo.wms.model.vmi.espData.ESPSales;

public interface ESPSalesDao extends GenericEntityDao<ESPSales, Long> {

//    @NativeQuery(model = ESPNorSendSoCommand.class)
//    List<ESPNorSendSoCommand> findSales(@QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate, @QueryParam("shopId") Long shopId, @QueryParam("statusSet") String statusSet);

//    @NativeQuery
//    String findMkdPrice(@QueryParam("fromDate") String fromDate, @QueryParam("skuCode") String skuCode, @QueryParam("netRetail") BigDecimal netRetail, @QueryParam("loca") String loca, SingleColumnRowMapper<String> rowMap);

//    @NativeQuery(model = ESPReInSoCommand.class)
//    List<ESPReInSoCommand> findReturnOrders(@QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate, @QueryParam("shopId") Long shopId, @QueryParam("raStatus") Integer raStatus, @QueryParam("roStatus") Integer roStatus);

//    @NativeQuery(model = ESPChangeInSoCommand.class)
//    List<ESPChangeInSoCommand> findExchangeInOrders(@QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate, @QueryParam("shopId") Long shopId, @QueryParam("raStatusStrs") String raStatus, @QueryParam("roStatus") Integer roStatus);

//    @NativeQuery(model = ESPChangeOutCommand.class)
//    List<ESPChangeOutCommand> findExchangeOutOrders(@QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate, @QueryParam("shopId") Long shopId, @QueryParam("raStatusStrs") String raStatus, @QueryParam("roStatus") Integer roStatus);

//    @NativeQuery
//    BigDecimal findNextVal(@QueryParam("seqName") String seqName, SingleColumnRowMapper<BigDecimal> rowMap);



}
