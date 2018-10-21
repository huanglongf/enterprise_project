package com.jumbo.wms.manager.print;

import org.springframework.stereotype.Service;


@Service("warehousePrintManager")
public class WarehousePrintManagerImpl extends BasePrintManagerImpl implements WarehousePrintManager {



    /**
     * 
     */
    private static final long serialVersionUID = 1034166310244552375L;

    // public JasperPrint printGift(Long giftLineId) throws Exception {
    // GiftLine gl = giftLineDao.getByPrimaryKey(giftLineId);
    // Map<String, Object> map = new HashMap<String, Object>();
    // map.put("memo", gl.getMemo());
    // String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "nike_gift.jasper";
    // ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
    // cjp.initializeReport(map, null);
    // JasperPrint jpJasperPrint = cjp.print();
    // return jpJasperPrint;
    // }



    /**
     * 特殊包装打印
     * 
     * @param lpId
     * @param staId
     * @return
     * @throws Exception
     */
    // public JasperPrint bySPTypePrint(Long staId, StaSpecialExecuteType type) throws Exception {
    // if (staId == null || type == null) return null;
    // String reportPath = null, basePath = null;
    // List<SpecialPackagingObj> dataList = null;
    // if (StaSpecialExecuteType.COACH_PRINT.equals(type)) {// Coach小票打印移动到此处
    // reportPath = Constants.PRINT_TEMPLATE_FLIENAME + Constants.SP_PRINT_COACH_MAIN;
    // basePath = Constants.PRINT_TEMPLATE_FLIENAME + Constants.SP_PRINT_COACH_DETAIL;
    // dataList = getCoachSpecial(staId);
    // } else if (StaSpecialExecuteType.BURBERRY_OUT_PRINT.equals(type)) {
    // reportPath = Constants.PRINT_TEMPLATE_FLIENAME + Constants.SP_PRINT_BURBERRY_MAIN;
    // basePath = Constants.PRINT_TEMPLATE_FLIENAME + Constants.SP_PRINT_BURBERRY_DETAIL;
    // dataList = initPrintObj(pickingListDao.findBurberryPrintInfo(staId, new
    // BeanPropertyRowMapper<SpecialPackagingData>(SpecialPackagingData.class)));
    // } else if (StaSpecialExecuteType.BURBERRY_RETURN_PRINT.equals(type)) {
    // reportPath = Constants.PRINT_TEMPLATE_FLIENAME + Constants.SP_PRINT_BURBERRY_RETURN_MAIN;
    // basePath = Constants.PRINT_TEMPLATE_FLIENAME + Constants.SP_PRINT_BURBERRY_RETURN_DETAIL;
    // dataList = initPrintObj(pickingListDao.findBurberryPrintInfo(staId, new
    // BeanPropertyRowMapper<SpecialPackagingData>(SpecialPackagingData.class)));
    // } else {
    // return null;
    // }
    // Map<String, Object> map = new HashMap<String, Object>();
    // JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
    // map.put("SUBREPORT_DIR", basePath);
    // map.put("logImage", "print_img/burberry_logo.png");
    // ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
    // cjp.initializeReport(map, dataSource);
    // return cjp.print();
    // }
    //
    // /**
    // * Coach 小票打印 数据获取
    // * @param staId
    // * @return
    // * @throws Exception
    // */
    // public List<SpecialPackagingObj> getCoachSpecial(Long staId) throws Exception {
    // StockTransApplication sta = staDao.getByPrimaryKey(staId);
    // int type = -1;
    // if (StockTransApplicationType.OUTBOUND_SALES.equals(sta.getType())) {
    // type = 1;
    // } else if (StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType())) {
    // type = 2;
    // } else if (StockTransApplicationType.OUTBOUND_RETURN_REQUEST.equals(sta.getType())) {
    // type = 3;
    // } else {
    // return null;
    // }
    // SpecialPackagingObj printObj = new SpecialPackagingObj();
    // SalesTicketResult resultDate = rmi4Wms.getSalesTicket(sta.getRefSlipCode(), type);
    // if (SalesTicketResult.RESULT_STATUS_SUCCESS == resultDate.getStatus()) {
    // SalesTicketResp stResp = resultDate.getSalesTicket();
    // if (stResp != null) {
    // printObj.setOrderCode(stResp.getTicketNo());
    // printObj.setOrderDate(FormatUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
    // printObj.setMemo(stResp.getRemark());
    // printObj.setCustomer(stResp.getMemberRealName());
    // printObj.setSeller(stResp.getSeller());
    // printObj.setShopName(stResp.getShopNo());
    // if (stResp.getQuantity() != null) {
    // printObj.setCommodityQty(stResp.getQuantity());
    // }
    // printObj.setVipCode(stResp.getVipCode());
    // // 订单 支付金额
    // BigDecimal actual;
    // if (stResp.getTotalActual() == null) {
    // actual = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
    // } else {
    // actual = stResp.getTotalActual().setScale(2, BigDecimal.ROUND_HALF_UP);
    // }
    // // 订单金额
    // printObj.setPayActual(actual.toString());
    // printObj.setTotalActual(actual.toString());
    //
    // // 支付方式
    // List<SalesTicketTenderResp> listResp = stResp.getTenderList();
    // printObj.setPayType(listResp != null && listResp.size() > 0 ? "" :
    // listResp.get(0).getPaymentType());
    // List<SpecialPackagingLineObj> lines = new ArrayList<SpecialPackagingLineObj>();
    // for (SalesTicketLineResp l : stResp.getTicketLines()) {
    // SpecialPackagingLineObj line = new SpecialPackagingLineObj();
    // // 明细备注
    // // line.setDetail();
    // if (l.getDiscountRate() != null) {
    // line.setDiscountRate(l.getDiscountRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString() +
    // "%");
    // }
    // if (l.getUnitPrice() != null) {
    // line.setUnitPrice(l.getUnitPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    // }
    // if (l.getTotalActual() != null) {
    // line.setTotalActual(l.getTotalActual().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    // }
    // if (l.getQuantity() != null) {
    // line.setQty(l.getQuantity());
    // }
    // line.setSkuName(l.getProductName());
    // line.setSupplierSkuCode(l.getProSupplierCode());
    // lines.add(line);
    // }
    // printObj.setLines(lines);
    // } else {
    // log.error("printCoachSpecial OMSRmi[getSalesTicket] return error! STASlipCode[" +
    // sta.getRefSlipCode() + "]");
    // }
    // } else {
    // log.debug("printCoachSpecial OMSRmi[getSalesTicket] return not fand! STASlipCode[" +
    // sta.getRefSlipCode() + "]");
    // }
    // List<SpecialPackagingObj> result = new ArrayList<SpecialPackagingObj>();
    // result.add(printObj);
    // return result;
    // }
    //
    // private List<SpecialPackagingObj> initPrintObj(List<SpecialPackagingData> dataList) {
    // List<SpecialPackagingObj> result = new ArrayList<SpecialPackagingObj>();
    // Map<Long, SpecialPackagingObj> map = new HashMap<Long, SpecialPackagingObj>();
    // SpecialPackagingObj bean;
    // for (SpecialPackagingData data : dataList) {
    // Long key = data.getStaId();
    // if (map.containsKey(key)) {
    // bean = map.get(key);
    // SpecialPackagingLineObj line = new SpecialPackagingLineObj();
    // line.setSkuName(data.getSkuName());
    // line.setSkuCode(data.getSkuCode());
    // line.setBarCode(data.getBarCode());
    // line.setQty(data.getQuantity());
    // line.setColor(data.getColor());
    // line.setSize(data.getSkuSize());
    // line.setUnitPrice(data.getUnitPrice());
    // bean.getLines().add(line);
    // } else {
    // bean = new SpecialPackagingObj();
    // bean.setAddress(data.getAddress());
    // bean.setOrderCode(data.getOrderCode());
    // bean.setOrderDate(data.getOrderDate());
    // bean.setOutboundDate(data.getOutboundDate());
    // bean.setCustomer(data.getCustomer());
    // bean.setCountry(data.getCountry());
    // bean.setProvince(data.getProvince());
    // bean.setCity(data.getCity());
    // bean.setDistrict(data.getDistrict());
    // bean.setTotalActual(data.getTotalActual().toString());
    // List<SpecialPackagingLineObj> lines = new ArrayList<SpecialPackagingLineObj>();
    // SpecialPackagingLineObj line = new SpecialPackagingLineObj();
    // line.setSkuName(data.getSkuName());
    // line.setSkuCode(data.getSkuCode());
    // line.setBarCode(data.getBarCode());
    // line.setQty(data.getQuantity());
    // line.setColor(data.getColor());
    // line.setSize(data.getSkuSize());
    // line.setUnitPrice(data.getUnitPrice());
    // lines.add(line);
    // bean.setLines(lines);
    // result.add(bean);
    // map.put(key, bean);
    // }
    // }
    // return result;
    // }
}
