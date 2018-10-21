/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.jumbo.task;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.webservice.ids.manager.IdsManager;
import com.jumbo.webservice.ids.manager.IdsManagerProxy;
import com.jumbo.wms.daemon.AutoPickingTask;
import com.jumbo.wms.daemon.IdsTask;
import com.jumbo.wms.daemon.MsgOrderCancelTask;
import com.jumbo.wms.daemon.TaskCreateSta;
import com.jumbo.wms.daemon.TaskItochuUA;
import com.jumbo.wms.manager.MongoDBSfLogisticsManager;
import com.jumbo.wms.manager.listener.StaListenerManager;
import com.jumbo.wms.manager.task.archive.ArchiveTask;
import com.jumbo.wms.manager.task.autoPicking.AutoPickingManagerProxy;
import com.jumbo.wms.manager.warehouse.QueueStaManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;


/**
 * @author SJ
 * 
 */
@ContextConfiguration(locations = {"classpath*:loxia-hibernate-context.xml", "classpath*:loxia-service-context.xml", "classpath*:spring.xml"})
public class UATaskTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    TaskItochuUA taskItochuUA;
    @Autowired
    IdsManager idsManager;
    @Autowired
    TaskCreateSta createSta;
    @Autowired
    QueueStaManagerProxy managerProxy;
    @Autowired
    InventoryCheckDao checkDao;
    @Autowired
    IdsTask idsTask;
    @Autowired
    com.jumbo.wms.daemon.TaskManagerProxy taskManagerProxy;
    @Autowired
    com.jumbo.wms.daemon.EmsTask EmsTask;
    @Autowired
    InventoryCheckDao dao;
    @Autowired
    WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    WareHouseManager houseManager;
    @Autowired
    AutoPickingTask autoPickingTask;
    @Autowired
    AutoPickingManagerProxy autoPickingManagerProxy;
    @Autowired
    IdsManagerProxy idsManagerProxy;
    @Autowired
    com.jumbo.wms.manager.vmi.levisData.LevisManager levisManager;
    @Autowired
    MsgOrderCancelTask cancelTask;
    @Autowired
    StaListenerManager listenerManager;
    @Autowired
    MongoDBSfLogisticsManager dbSfLogisticsManager;
    @Autowired
    ArchiveTask archiveTask;

    @Test
    public void task() throws Exception {

        // archiveTask.archiveCreateSta();
        // archiveTask.archiveOutSta();
        // List<StockTransApplication> stas = staDao.getStaListBySlipCode1("0080749916");
        // for (StockTransApplication sta : stas) {
        // converReceiveManager.generateVMIReceiveInfoBySlipCode1(sta);
        // }

        // }
        // hubWmsService.wmsCancelOrder("toms", "S600007419227", null);
        // MongoDBSfLogistics dbSfLogistics = dbSfLogisticsManager.getLogistics("", "");
        // dbSfLogistics.getCity();
        // itTask.generateSta();
        // dbSfLogisticsManager.initMongoDBLogistics();
        // hubWmsService.wmsCancelOrderConfirm(1, 10, "toms", null, null);
        // msgOrderCancelDao.wmsCancelOrderConfirm(start, size, startTime, endTime, systemKey,
        // rowMapper)
        // hubWmsService.wmsCancelOrder("toms", "S600007828117", "");
        // String a = "21326601," + "21326599," + "21326589," + "21326588," + "21326593," +
        // "21326597";
        // String b[] = a.split(",");
        // for (int i = 0; i < b.length; i++) {
        // StockTransApplication sta = staDao.getByPrimaryKey(Long.parseLong(b[i]));
        // MsgOrderCancel orderCancel = new MsgOrderCancel();
        // orderCancel.setCreateTime(new Date());
        // orderCancel.setIsCanceled(true);
        // orderCancel.setMsg("物流不可达");
        // orderCancel.setSource(sta.getStaDeliveryInfo().getLpCode());
        // orderCancel.setStaCode(sta.getCode());
        // orderCancel.setStatus(MsgOutboundOrderCancelStatus.FINISHED);
        // orderCancel.setUpdateTime(new Date());
        // orderCancel.setSystemKey(sta.getSystemKey());
        // msgOrderCancelDao.save(orderCancel);
        // }
        // createStaTaskManager.setFlagToOrder(19844152, ouId);
        // levisTask.generateInboundSta();
        // converseTask.outputReceiveFile();
        // converseTask.outputTransferOutFile();
        // listenerManager.transferOmsOutBound(61305533l, 8387466l);
        // sfOrderTaskManager.sfIntefaceByWarehouse(881l);
        // orderTask.createRtnOrder();
        // hubWmsService.wmsCancelOrder("toms", "S600007349601", null);
        // rmiService.closeProcurement("P600007328694");
        // idsTask.idsFeedbackTask();
        // proxy.msgCancelTask();
        // cancelTask.createMsgOrder();
        // StockTransVoucher stv = stvDao.findStvBySta(20878816l, new
        // BeanPropertyRowMapper<StockTransVoucher>(StockTransVoucher.class));
        // List<StockTransTxLogCommand> stockTransTxLogs =
        // stockTransTxLogDao.findLogByStvId(stv.getId(), new
        // BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
        // Map<String, List<SkuSnLogCommand>> map = new HashMap<String, List<SkuSnLogCommand>>();
        // for (StockTransTxLogCommand logCommand : stockTransTxLogs) {
        // WmsInfoLogOms infoLogOms = new WmsInfoLogOms();
        // InventoryStatus status = inventoryStatusDao.getByPrimaryKey(logCommand.getStatusId());
        // Sku sku = skuDao.getByPrimaryKey(logCommand.getSkuId());
        // infoLogOms.setSku(sku.getCustomerSkuCode());
        // infoLogOms.setBtachCode(logCommand.getBatchCode());
        // infoLogOms.setTranTime(logCommand.getTranTime());
        // infoLogOms.setOwner(logCommand.getOwner());
        // if (status.getName().equals("良品")) {
        // infoLogOms.setInvStatus("normal");
        // } else if (status.getName().equals("残次品")) {
        // infoLogOms.setInvStatus("damage");
        // } else {
        // infoLogOms.setInvStatus("pending");
        // }
        // OperationUnit operationUnit = operationUnitDao.getByPrimaryKey(logCommand.getWhId());
        // infoLogOms.setQty(logCommand.getQuantity());
        // infoLogOms.setWarehouceCode(operationUnit.getCode());
        // sku.setIsSnSku(sku.getIsSnSku() == null ? false : sku.getIsSnSku());
        // if (sku.getIsSnSku()) {
        // List<SkuSnLogCommand> mapstvcmd = map.get(sku.getId().toString());
        // if (mapstvcmd != null) {
        // Long qty = logCommand.getQuantity();
        // for (int i = 0; i < mapstvcmd.size(); i++) {
        // if (qty == mapstvcmd.size()) {
        // if (infoLogOms.getSns() == null) {
        // infoLogOms.setSns(mapstvcmd.get(i).getSn());
        // } else {
        // infoLogOms.setSns(infoLogOms.getSns() + "," + mapstvcmd.get(i).getSn());
        // }
        // } else {
        // if (qty == 0) {
        // for (int j = 0; j <= i; j++) {
        // mapstvcmd.remove(0);
        // }
        // map.put(sku.getId().toString(), mapstvcmd);
        // break;
        // }
        // if (infoLogOms.getSns() == null) {
        // infoLogOms.setSns(mapstvcmd.get(i).getSn());
        // --qty;
        // } else {
        // infoLogOms.setSns(infoLogOms.getSns() + "," + mapstvcmd.get(i).getSn());
        // --qty;
        // }
        // }
        // }
        //
        // } else {
        // List<SkuSnLogCommand> stvcmd = skuSnLogDao.findOutboundSnBySkuId(stv.getId(),
        // sku.getId(), new BeanPropertyRowMapper<SkuSnLogCommand>(SkuSnLogCommand.class));
        // map.put(sku.getId().toString(), stvcmd);
        // Long qty = logCommand.getQuantity();
        // for (int i = 0; i < stvcmd.size(); i++) {
        // if (qty == stvcmd.size()) {
        // if (infoLogOms.getSns() == null) {
        // infoLogOms.setSns(stvcmd.get(i).getSn());
        // } else {
        // infoLogOms.setSns(infoLogOms.getSns() + "," + stvcmd.get(i).getSn());
        // }
        // } else {
        // if (infoLogOms.getSns() == null) {
        // infoLogOms.setSns(stvcmd.get(i).getSn());
        // --qty;
        // } else {
        // infoLogOms.setSns(infoLogOms.getSns() + "," + stvcmd.get(i).getSn());
        //
        // --qty;
        // }
        // }
        // if (qty == 0) {
        // for (int j = 0; j <= i; j++) {
        // stvcmd.remove(0);
        // }
        // map.put(sku.getId().toString(), stvcmd);
        // break;
        // }
        //
        // }
        // }
        // }
        // //
        // }


        // hubWmsService.wmsCancelOrder("toms", "S600007267466", null);
        // 标杆出库反馈执行
        // wareHouseManagerProxy.outboundToBzProxyTask();
        // wareHouseManagerProxy.callVmiSalesStaOutBound(4268221l);
        // idsManager.executeVmiReturnOutBound(msgRtnReturnDao.getByPrimaryKey(4268220l));

        // iDSInterfaceTask.exeAEOSalesOrderToLFTask();
        // hubWmsService.wmsCancelOrder("toms", "S600007251544", null);
        // hubWmsService.wmsOrderFinish(1, 10, "toms", null, null);
        // idsManagerProxy.idsInventorySyn("IDS");

        // List<String> batchCode = inOrderQueueDao.findBatchCodeByDetial(new
        // SingleColumnRowMapper<String>(String.class));
        // for (int i = 0; i < batchCode.size(); i++) {
        // System.out.println(batchCode.get(i));

        // }
        // try {
        // cancelManager.cancelSalesStaBySlipCode("TBZR1509000027");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // impl.createRtnOrder();
        // cancelManager.cancelSalesStaBySlipCode("JDDG1508000394");

        // bocTask.executeRtnInboundOrderTask();
        // String a =
        // "463445," + "463458," + "463456," + "463443," + "463442," + "463447," + "463438," +
        // "463450," + "463441," + "463460," + "463459," + "463452," + "463449," + "463473," +
        // "463439," + "463440," + "463462," + "463451," + "463468," + "463457,"
        // + "463444," + "463842,";
        // String b[] = a.split(",");
        // for (int i = 0; i < b.length; i++) {
        // MsgRtnInboundOrder msgOrderNewInfo = msgRtnInboundOrderDao.getByPrimaryKey(463842l);
        // wareHouseManagerProxy.msgInBoundWareHouse(msgOrderNewInfo);
        // }



        // int start = 1;
        // int pagesize = 5;
        // String systemKey = "tmos";
        //
        // iDSInterfaceTask.inventoryCheckAEO();

        // hubWmsService.wmsOrderFinish(start, pagesize, systemKey, null, null);
        // impl.createRtnOrder();
        // impl2.createRtnOrderBatchCode("8");
        // levisTask.generateInboundSta();
        // String a =
        // "56263068," + "56263263," + "56263264," + "56263265," + "56263266," + "56263267," +
        // "56263278," + "56263279," + "56263472," + "56263479," + "56263480," + "56263673," +
        // "56263674," + "56263675," + "56263848," + "56263849," + "56263850,"
        // + "56264043," + "56264052," + "56264053," + "56264054," + "56264055," + "56264218," +
        // "56264220," + "56264221," + "56264228," + "56264229," + "56264423," + "56264432," +
        // "56264433," + "56264434," + "56264435," + "56264436,"
        // + "56272940," + "56272953," + "56272954," + "56272955," + "56273002," + "56273003," +
        // "56273028," + "56273081," + "56273096," + "56273097," + "56273148," + "56273176," +
        // "56273177," + "56273178," + "56273179," + "56273180,"
        // + "56273181," + "56273232," + "56273233," + "56273246," + "56273247," + "56273248," +
        // "56273249," + "56273250," + "56273251," + "56273281," + "56273282," + "56273304," +
        // "56273305," + "56273306," + "56389884," + "56389938,";
        //
        //
        // String b[] = a.split(",")31
        // for (int i = 0; i < b.length; i++) {
        // Long c = Long.parseLong(b[i]);
        // StockTransApplication sta = staDao.getByPrimaryKey(c);
        // converReceiveManager.generateVMIReceiveInfoBySlipCode1(sta);
        // }

        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Date d = sdf.parse("2015-08-18");
        // hubWmsService.wmsCancelOrderConfirm(1, 10, "TM", d, new Date());
        // invTask2.totalInv();
        // idsManagerProxy.idsInventorySyn(Constants.VIM_WH_SOURCE_IDS);
        // levisManager.createStkrData();
        // invTask.salesInventory();
        // rmiService.closeProcurement("PO2015080004");
        // task.upgradeEmail();
        // cchTask.generateInbountSta();
        // jdOrderTask.jdInterfaceByWarehouse();
        // levisTask.generateInboundSta();
        // invTask.salesInventoryOms();
        // rmiService.cancelOrder("TBEP1506019249");
        // cchTask.mqSendStockReturn();
        // cchTask.mqSendASNReceive();
        // 库存调整
        // InventoryCheck ic=dao.getByPrimaryKey(567689l);
        // idsManager.executionVmiAdjustment(ic);

        // idsManagerProxy.wmsOrderToIds("af","af");

        // wareHouseManagerProxy.msgInBoundWareHouse(msgRtnInbonundOrderDao.getByPrimaryKey(436078l));

        // houseManager.removeTransFialedSalesSta(253166l);

        // jdOrderTask.jdInterfaceByWarehouse();
        // EmsTask.setEmsStaTrasnNo();
        // jdOrderTask.jdReceiveOrder();
        // rmi4Wms.cancelOperationBill("", 1);
        // taskEbsManager.salesInventoryOms("1429601960002");
        // taskItochuUA.uaReadItochuRtnInvToDB();
        // idsManager.vimIdsnoticeCancelOrderBound();
        // taskItochuUA.uaInOutBoundNotify();
        // transSuggestManager.suggestTransService(100068768L);
        // cchTask.mqSendASNReceive();
        // idsManager
        // queueStaManager.createStabyBatchCode(121l, "3044771");
        // manager1.createStabyBatchCode(121l, "3044779");
        // T_WH_INV_CHECK
        // InventoryCheck ic=checkDao.getByPrimaryKey(456978l);
        // idsManager.executionVmiAdjustment(ic);
        // managerProxy.createStaByQueue(881l);
        // taskItochuUA.uaInOutBoundRtn();
        // idsTaskImpl.msgUnLocked();
        // idsTaskImpl.mailIds();
        // autoPickingTask.autoPicking();
        // autoPickingManagerProxy.createCreateByWh(881l, false);

        // transOlManager.matchingTransNo(55302251l , "YTO", 881L);



        // taskItochuUA.uaSaveCheckInventory();
        // 外包仓取消
        // manager.vimExecuteCancelOrder(191625l);
        // taskManagerProxy.msgCancelTask();
        // idsTask.idsFeedbackTask();
    }
    // @Test
    // public void buildQuarzSql3(){
    // PrintWriter out = null;
    // BufferedReader in = null;
    // String result = "";
    //
    //
    // try {
    // String type="logistic_order_status";
    // String
    // data="<?xml version='1.0' encoding='UTF-8'?><WMSSHP><BatchID>0004495496</BatchID><SHPHD><StorerKey>ANF</StorerKey><Facility>BS13</Facility><ExternOrderKey>S200045510661</ExternOrderKey><M_Company>910670853094084</M_Company><SOStatus>0</SOStatus><ShipperKey>SF</ShipperKey><LoadKey/><EffectiveDate>20150331213101</EffectiveDate></SHPHD></WMSSHP>";
    // SimpleDateFormat sf = new SimpleDateFormat("yyyyMMDDHHMMss");
    // String requestTime = sf.format(new Date());
    // URL realUrl = new URL("http://10.8.12.151:8001/wms/rest/ids.json");
    // // 打开和URL之间的连接
    // URLConnection conn = realUrl.openConnection();
    // // 设置通用的请求属性
    // conn.setRequestProperty("accept", "*/*");
    // conn.setRequestProperty("connection", "Keep-Alive");
    // conn.setRequestProperty("Content-Type", "application/xml");
    // // conn.setRequestProperty("user-agent",
    // // "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
    // // 发送POST请求必须设置如下两行
    // conn.setDoOutput(true);
    // conn.setDoInput(true);
    // // 获取URLConnection对象对应的输出流
    // out = new PrintWriter(conn.getOutputStream());
    // // 发送请求参数
    // String param1 ="Key=IDS_ANF_TEST&RequestTime=" + requestTime +
    // "&Sign=0Mk4Li4Mu7Mn4Ca7Vn2Mx0Et7Sm4Sk8H&Version=1.0&ServiceType=" + type;
    // String secretKey = AppSecretUtil.generateSecret(param1);
    // String param = "Key=IDS_ANF_TEST&RequestTime=" + requestTime + "&Sign=" + secretKey +
    // "&Version=1.0&ServiceType=" + type + "&Data=" + data;
    // out.print(param);
    //
    // // flush输出流的缓冲
    // out.flush();
    // // 定义BufferedReader输入流来读取URL的响应
    // in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    // String line;
    // while ((line = in.readLine()) != null) {
    // result += line;
    // }
    // System.out.println("result"+result);
    // } catch (Exception e) {
    // System.out.println("发送 POST 请求出现异常！" + e);
    // e.printStackTrace();
    // }
    //
    // // 使用finally块来关闭输出流、输入流
    // finally {
    // try {
    // if (out != null) {
    // out.close();
    // }
    // if (in != null) {
    // in.close();
    // }
    // } catch (IOException ex) {
    // ex.printStackTrace();
    // }
    // }
    // System.out.println(result);
    //
    // }
}
