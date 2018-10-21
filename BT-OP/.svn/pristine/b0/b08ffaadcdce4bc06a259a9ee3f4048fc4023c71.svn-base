package com.bt.orderPlatform.quartz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.common.util.BigExcelExport;
import com.bt.common.util.DateUtil;
import com.bt.common.util.Mail;
import com.bt.interf.btins.BTINSInterface;
import com.bt.interf.sf.SfInterface;
import com.bt.interf.zto.ZTOInterface;
import com.bt.orderPlatform.controller.form.WaybillMasterQueryParam;
import com.bt.orderPlatform.ftpupload.FTPFileUpload;
import com.bt.orderPlatform.model.InterfaceRouteinfo;
import com.bt.orderPlatform.model.PcodeMappering;
import com.bt.orderPlatform.model.WaybilDetail;
import com.bt.orderPlatform.model.WaybillMaster;
import com.bt.orderPlatform.service.InterfaceRouteinfoService;
import com.bt.orderPlatform.service.PcodeMapperingService;
import com.bt.orderPlatform.service.WaybilDetailService;
import com.bt.orderPlatform.service.WaybillMasterService;

/**
 * @Description:
 * @author Hanery:
 * @date 2017年6月9日 上午9:09:33
 */
@Service
public class RouteQuartz{

    private static Logger logger = Logger.getLogger(RouteQuartz.class);

    @Resource(name = "waybillMasterServiceImpl")
    private WaybillMasterService<WaybillMaster> waybillMasterService;

    @Resource(name = "waybilDetailServiceImpl")
    private WaybilDetailService<WaybilDetail> waybilDetailService;

    @Resource(name = "pcodeMapperingServiceImpl")
    private PcodeMapperingService<PcodeMappering> pcodeMapperingService;

    //private static final ObjectMapper MAPPER = new ObjectMapper();
//    @Autowired
//    private YlInterface ylInterface;
//
//    @Autowired
//    private RFDInterface rfdInterface;

    @Autowired
    private SfInterface sfInterface;

    @Autowired
    private ZTOInterface zTOInterface;

    @Autowired
    private FTPFileUpload fTPFileUpload;

    @Autowired
    private BTINSInterface bTINSInterface;

    @Resource(name = "interfaceRouteinfoServiceImpl")
    private InterfaceRouteinfoService<InterfaceRouteinfo> interfaceRouteinfoService;

    public void pullRoutingInfo() throws Exception{
        // TODO Auto-generated method stub
        logger.error("路由信息拉取开始");
        String status = "2,5,6,8,9";
        List<WaybillMaster> list = waybillMasterService.queryByStatus(status);

        InterfaceRouteinfo interfaceRouteinfo = null;
        PcodeMappering pcodeMappering = null;
        try{
            List<WaybillMaster> listsf = new ArrayList<WaybillMaster>();
            List<WaybillMaster> listsf1 = new ArrayList<WaybillMaster>();
            List<WaybillMaster> listsf2 = new ArrayList<WaybillMaster>();
            for (WaybillMaster waybillMaster : list){
                //logger.error(waybillMaster.getWaybill());
                if (waybillMaster.getExpressCode().equals("SF")){
                    listsf.add(waybillMaster);
                    if (waybillMaster.getCustid().equals("0217928502")){
                        listsf1.add(waybillMaster);
                    }else{
                        listsf2.add(waybillMaster);
                    }
                }else if (waybillMaster.getExpressCode().equals("ZTO")){
                    //list1.add(waybillMaster);
                    zTOInterface.queryOrder(waybillMaster);
                }else if (waybillMaster.getExpressCode().equals("SFSM")){
                    List<WaybillMaster> list1 = new ArrayList<WaybillMaster>();
                    list1.add(waybillMaster);
                    sfInterface.getRoute(list1);
                    interfaceRouteinfo = interfaceRouteinfoService.queryByWayBillAndTime(waybillMaster.getWaybill());
                    if (interfaceRouteinfo != null){
                        if (interfaceRouteinfo.getRoute_opcode() != null){
                            pcodeMappering = pcodeMapperingService.queryBySfopcode(interfaceRouteinfo.getRoute_opcode());
                            if (pcodeMappering != null){
                                waybillMasterService.setstatus(waybillMaster.getWaybill(), pcodeMappering.getBz_code());
                            }
                        }
                    }
                }
                waybillMaster = null;
            }
            //旧接口地址查询
            for (int i = 0; i <= listsf1.size(); i = i + 10){
                if (listsf1.size() < i + 10){
                    sfInterface.getRoute(listsf1.subList(i, listsf1.size()));
                }else{
                    sfInterface.getRoute(listsf1.subList(i, i + 10));
                }
            }
            //新接口地址查询
            for (int i = 0; i <= listsf2.size(); i = i + 10){
                if (listsf2.size() < i + 10){
                    sfInterface.getRoute(listsf2.subList(i, listsf2.size()));
                }else{
                    sfInterface.getRoute(listsf2.subList(i, i + 10));
                }
            }
            //修改运单状态
            for (int i = 0; i < listsf.size(); i++){
                interfaceRouteinfo = interfaceRouteinfoService.queryByWayBillAndTime(listsf.get(i).getWaybill());
                if (interfaceRouteinfo != null){
                    if (interfaceRouteinfo.getRoute_opcode() != null){
                        pcodeMappering = pcodeMapperingService.queryBySfopcode(interfaceRouteinfo.getRoute_opcode());
                        if (pcodeMappering != null){
                            waybillMasterService.setstatus(listsf.get(i).getWaybill(), pcodeMappering.getBz_code());
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            status = null;
            list = null;
            interfaceRouteinfo = null;
            pcodeMappering = null;
        }

        logger.error("路由信息拉取结束");

        //  exec.execute(thread);  
        //   exec.shutdown();
    }

    public void pullFTP() throws Exception{
        try{
            fTPFileUpload.FTPFile();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void generateClient() throws Exception{
        List<WaybillMaster> sflist = waybillMasterService.queryByStatusSF();
        WaybillMaster waybillMaster = null;
        try{
            if (sflist.size() > 0){
                for (int i = 0; i < sflist.size(); i++){
                    waybillMaster = sflist.get(i);
                    sfInterface.placeOrder(waybillMaster);
                    Thread.sleep(1000 * 60 * 5);
                    WaybillMaster selectById = waybillMasterService.selectById(waybillMaster.getId());
                    if (selectById.getStatus().equals("2")){
                        sfInterface.CancelOrder(waybillMaster);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            sflist = null;
            waybillMaster = null;
        }
    }

    public void pullStatus() throws Exception{
        String status = "5,6,7,8,9";
        List<WaybillMaster> list = waybillMasterService.queryByStatusAndStartDate(status);
        InterfaceRouteinfo interfaceRouteinfo = null;
        try{
            for (WaybillMaster waybillMaster : list){
                interfaceRouteinfo = interfaceRouteinfoService.queryByWayBillAndASCTime(waybillMaster.getWaybill());
                if (interfaceRouteinfo != null){
                    if (interfaceRouteinfo.getRoute_time() != null){
                        waybillMasterService.setStartDate(waybillMaster.getWaybill(), interfaceRouteinfo.getRoute_time());
                    }
                }
            }
            list = null;
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            interfaceRouteinfo = null;
            status = null;
            list = null;
        }
    }

    public void sendWaybillInfoEmail(){
        // TODO Auto-generated method stub
        Date now = new Date();
        int hour = now.getHours();
        /*
         * if(!(hour==10||hour==17)){
         * return;
         * }
         */
        Date start = null;
        Date end = DateUtil.MoveDate(now, -1);
        String sStr = "";
        String eEnd = DateUtil.format(end);
        if (hour == 10){
            start = DateUtil.MoveDate(end, -1);
            sStr = DateUtil.format(start);
            sStr = sStr.split(" ")[0] + " 00:00:00";
        }else{
            sStr = eEnd.split(" ")[0] + " 23:59:59";
        }
        String fileName = DateUtil.formatSS(now);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("start", sStr);
        param.put("end", eEnd);
        param.put("path", fileName);
        WaybillMasterQueryParam queryParam = null;
        List<Map<String, Object>> list = waybillMasterService.selectByQuery(queryParam);
        LinkedHashMap<String, String> cMap = null;
        try{
            cMap = new LinkedHashMap<String, String>();
            cMap.put("create_time", "制单日期");
            cMap.put("status", "运单状态");
            cMap.put("order_id", "运单号");
            cMap.put("customer_number", "客户单号");
            cMap.put("from_orgnization", "发货机构");
            cMap.put("express_name", "快递公司");
            cMap.put("producttype_name", "快递业务类型");
            cMap.put("waybill", "快递单号");
            cMap.put("pay_path", "支付方式");
            cMap.put("memo", "备注");
            cMap.put("amount_flag", "是否保价");
            cMap.put("total_amount", "保价金额");
            cMap.put("cost_center", "成本中心");
            cMap.put("from_contacts", "发货联系人");
            cMap.put("from_phone", "发货联系电话");
            cMap.put("from_address", "发货地址");
            cMap.put("to_orgnization", "收货机构");
            cMap.put("to_province", "省");
            cMap.put("to_city", "市");
            cMap.put("to_state", "区");
            cMap.put("to_street", "街道");
            cMap.put("to_contacts", "收货联系人");
            cMap.put("to_phone", "收货联系电话");
            cMap.put("to_address", "收货地址");
            cMap.put("total_qty", "总件数");
            cMap.put("total_weight", "总毛重");
            cMap.put("total_volumn", "总体积");
            cMap.put("total_amount", "总金额");
            cMap.put("order_time", "下单日期");
        }catch (Exception e){
            e.printStackTrace();
        }

        String smtp = "smtp.baozun.com";
        String from = "bin.wang@baozun.com";//邮箱  
        String to = "bin.wang@baozun.com"; //邮箱 

        String copyto = "";//邮箱  
        String subject = "WLJ" + DateUtil.format(now);
        String content = "无揽件 谢谢";
        String username = "bin.wang@baozun.com";//邮箱  
        String password = "#wb1362bzyx";
        String filename = "";
        //setp 1  造文件夹
        String path = "";
        path = path + fileName;

        File f = new File(path);
        //  System.out.println(f.exists());
        if (!f.exists()){
            f.mkdir();
        }
        //setp 2  写文件 
        try{
            BigExcelExport.excelDownLoadDatab(list, cMap, fileName + "运单信息表.xlsx");
        }catch (IOException e){}
        Mail.send(smtp, from, to, subject, content, username, password, path + "运单信息表.xlsx", copyto);
    }

    public void QuartzBTINS(){

        int notSendType = 0;//0 默认值 没有发送过的 1 已发送的
        int count = 500;//条数
        List<WaybillMaster> list = waybillMasterService.queryBySendTypeAndCount(notSendType,count);
        int i = 0;
        for (WaybillMaster waybillMaster : list){
            List<WaybilDetail> queryOrderByOrderId = waybilDetailService.queryOrderByOrderId(waybillMaster.getOrder_id());
            i = bTINSInterface.insertByObj(waybillMaster, queryOrderByOrderId);
            if (i > 0){
                i = waybillMasterService.updateSendTypeById(1, waybillMaster.getId());//0 默认值 没有发送过的 1 已发送的
                if (i == 0){
                    throw new RuntimeException("waybillMaster_id=" + waybillMaster.getId() + "sendType状态更新失败");
                }
            }
        }
    }

}
