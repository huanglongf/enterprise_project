package com.jumbo.wms.manager.ymt2hub;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.baozun.bh.util.JSONUtil;

import com.baozun.bizhub.manager.logisticsroute.LogisticsOrderManager;
import com.baozun.bizhub.model.logisticsroute.yamato.YamatoLogisticsOrder;
import com.baozun.bizhub.model.logisticsroute.yamato.YamatoLogisticsOrders;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.YamatoConfirmOrderQueueDao;
import com.jumbo.dao.warehouse.YamatoConfirmOrderQueueLogDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.YamatoConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.YamatoConfirmOrderQueueLog;

/**
 * 
 * @author jinggang.chen
 *
 */
@Transactional
@Service("yamatoTransToHubManager")
public class YamatoTransToHubManagerImpl extends BaseManagerImpl implements YamatoTransToHubManager {

    private static final long serialVersionUID = 2003413198196424307L;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private LogisticsOrderManager logisticsOrderManager;
    @Autowired
    private YamatoConfirmOrderQueueDao yamatoConfirmOrderQueueDao;
    @Autowired
    private YamatoConfirmOrderQueueLogDao yamatoConfirmOrderQueueLogDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private PackageInfoDao packageInfoDao;

    @Override
    public void yamatoTransInfoToHub(List<YamatoConfirmOrderQueue> orderList) {

        YamatoLogisticsOrders ylo = new YamatoLogisticsOrders();
        List<YamatoLogisticsOrder> orderListToHub = new ArrayList<YamatoLogisticsOrder>();
        for (YamatoConfirmOrderQueue order : orderList) {
            SimpleDateFormat dateFomat = new SimpleDateFormat("ddMMyyyy");

            YamatoLogisticsOrder orderToHub = new YamatoLogisticsOrder();
            // 获取作业单信息
            StockTransApplication sta = staDao.getByPrimaryKey(order.getStaId());
            StaDeliveryInfo df = staDeliveryInfoDao.getByPrimaryKey(order.getStaId());
            BiChannel biChannel = biChannelDao.getByCode(sta.getOwner());
            PackageInfo packinfo = packageInfoDao.findByTrackingNo(df.getTrackingNo());
            orderToHub.setHandlingBranchCode("0");// 取件门店代码
            orderToHub.setArticle("0");// 注意事项
            if (df.getIsCod()) {
                orderToHub.setCODamountGSTinclusive(sta.getTotalActual() + "");// 货到付款金额(客户购买的商品价格,必填)
            } else {
                orderToHub.setCODamountGSTinclusive("0");// 货到付款金额 (必填)
            }
            orderToHub.setCustomerReferenceNo(packinfo.getWeight() + "");// 客户的管理编号(传重量字段)
            orderToHub.setPrStringDate("0");// 预留1
            orderToHub.setStringerimStorageServiceCategory("0");
            orderToHub.setStringerimStorageOfficeCode("0");
            orderToHub.setNumberofIssuing("0");
            orderToHub.setFreightfareNo("01");// 运费管理号码(必填)，01只为发货
            orderToHub.setSortingCode("0");// 货物分拣代码(必填) ，0此处值无效
            orderToHub.setItemType("0");// 商品种类(必填) ，0代表室温
            orderToHub.setItemName1("0");// 物品
            orderToHub.setItemName2("0");
            orderToHub.setItemCode1("0");// 物品编码
            orderToHub.setItemCode2("0");
            orderToHub.setBillingRegisterNoCategory("");// 付款方分类代码
            orderToHub.setBillingRegisterNo("0");// 付款方代码(必填)，
            orderToHub.setReceptionDate(dateFomat.format(new Date()));// 预定发货日 (必填)
            orderToHub.setPlannedDeliveryDate("0");// 预定收件日(必填)
            orderToHub.setConsigneesName(df.getReceiver());// 收件人(必填)
            orderToHub.setConsigneesPhoneNo(df.getTelephone());// 收件人电话(必填)
            orderToHub.setConsigneesPostalCode(df.getZipcode());// 收件人邮编(必填)
            orderToHub.setConsigneesAddress1(df.getAddress());// 收件人地址1(必填)
            orderToHub.setConsigneeRegisterNo(" ");// 收件人分机号 (数字或空格)
            orderToHub.setConsigneesAddress2("0");
            orderToHub.setConsigneesAddress3("0");
            orderToHub.setConsigneesAddress4("0");
            orderToHub.setConsigneesAddress5("0");
            orderToHub.setConsigneeCode("0");// 收件人编号
            orderToHub.setShippersName(biChannel.getName());// 寄件人(必填)
            orderToHub.setShippersPhoneNo(biChannel.getTelephone());// 寄件人电话(必填)
            orderToHub.setShippersRegisterNo(biChannel.getTelephone());// 寄件人分机号（必填）
            orderToHub.setShippersPostalCode(biChannel.getZipcode());// 寄件人邮政编码 （必填）
            orderToHub.setShippersAddress1(biChannel.getAddress());// 寄件人地址 （必填）
            orderToHub.setShippersAddress2("0");
            orderToHub.setShippersAddress3("0");
            orderToHub.setTimeZoneDelivery("0");// 派件时段
            orderToHub.setShipmentHandling1("0");// 特殊运送货物1
            orderToHub.setShipmentHandling2("0");
            orderToHub.setTrackingNo(df.getTrackingNo().replace("a", ""));// 运单编号(必填)
            if (df.getIsCod()) {
                orderToHub.setLabelType("05");// 运单种类 (必填),05货到付款服务
            } else {
                orderToHub.setLabelType("01");// 运单种类 (必填)，01发货人付款
            }
            orderListToHub.add(orderToHub);
        }
        /*
         * YamatoLogisticsOrder orderToHub = new YamatoLogisticsOrder();
         * orderToHub.setArticle("test"); orderListToHub.add(orderToHub);
         */
        ylo.setList(orderListToHub);
        // JSONUtil.beanToJson(ylo);
        // 向hub推送物流订单信息
        String result = "";
        try {
            result = logisticsOrderManager.sendOrder("YAMATO", "yamato", JSONUtil.beanToJson(ylo), "");
        } catch (Exception e) {
            log.error(JSONUtil.beanToJson(ylo) + "发送失败", "调用hub接口异常");
        }
        // 发送成功把物流信息从待反馈列表中删除，添加到yamato反馈日志表中
        if (result.equals("1")) {
            for (YamatoConfirmOrderQueue orderLog : orderList) {
                YamatoConfirmOrderQueueLog log = new YamatoConfirmOrderQueueLog();
                log.setCreateTime(orderLog.getCreateTime());
                log.setFinishTime(new Date());
                log.setMailno(orderLog.getMailno());
                log.setStaCode(orderLog.getStaCode());
                yamatoConfirmOrderQueueLogDao.save(log);
            }
            yamatoConfirmOrderQueueDao.deleteAll(orderList);
            // 发送失败增加错误次数
        } else if (result.equals("0")) {
            for (YamatoConfirmOrderQueue order : orderList) {
                order.setExeCount(order.getExeCount() + 1L);
                yamatoConfirmOrderQueueDao.save(order);
            }
        }
    }
}
