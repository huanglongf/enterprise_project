package com.jumbo.wms.manager.lmis;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.lmis.warehouse.*;
import com.jumbo.dao.pda.StaCartonInfoDao;
import com.jumbo.dao.warehouse.*;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.TotalZeroInvTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.lmis.*;
import com.jumbo.wms.model.mongodb.StaCartonInfo;
import com.jumbo.wms.model.warehouse.*;
import loxia.dao.Pagination;
import loxia.dao.support.BeanPropertyRowMapperExt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

@SuppressWarnings("unused")
@Service("lmisManager")
public class LmisManagerImpl extends BaseManagerImpl implements LmisManager {
    /**
     * 
     */
    private static final long serialVersionUID = -2068393798489453729L;
    protected static final Logger log = LoggerFactory.getLogger(LmisManager.class);
    @Autowired
    private OperatingCostDao operatingCostDao;
    @Autowired
    private MaterialFeePurchaseDetailsDao materialFeePurchaseDetailsDao;
    @Autowired
    private MaterialFeeActualUsageDao materialFeeActualUsageDao;
    @Autowired
    private ExpressWaybillDao expressWaybillDao;
    @Autowired
    private OrderDetailDao orderDetailDao;
    @Autowired
    private WarehouseChargesDao warehouseChargesDao;
    @Autowired
    private TotalZeroInvTask totalZeroInvTask;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private ReturnStorageDao returnStorageDao;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private RocketMQProducerServer producerServer;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private BiChannelDao shopDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private OperationUnitDao opDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private PickingListDao pickingListDao;

    @Autowired
    private StaCartonInfoDao staCartonInfoDao;

    @Autowired
    private BiChannelDao biChannelDao;

    // 1、操作费接口服务
    @Override
    public String getOperatingCost(String requestParam) {
        LmisResponse<OperatingCost> response = new LmisResponse<OperatingCost>();
        // System.out.println("getOperatingCost:" + requestParam);
        if (StringUtils.isBlank(requestParam)) {
            response.setStatus("0");
            response.setMsg("请求参数为空！");
            return net.sf.json.JSONObject.fromObject(response).toString();
        }
        try {
            loxia.support.json.JSONObject inObj = new loxia.support.json.JSONObject(requestParam);
            String start_time = inObj.get("start_time").toString();
            String end_time = inObj.get("end_time").toString();

            String order_type = inObj.has("order_type") ? (inObj.get("order_type").toString().equals("") ? null : inObj.get("order_type").toString()) : null;// 订单类型
            String store_code = inObj.has("store_code") ? (inObj.get("store_code").toString().equals("") ? null : inObj.get("store_code").toString()) : null;// 所属店铺编号
            String job_type = inObj.has("job_type") ? (inObj.get("job_type").toString().equals("") ? null : inObj.get("job_type").toString()) : null;// 作业类型名称

            String page = inObj.has("page") ? inObj.get("page").toString() : null;// 页号
            String pageSize = inObj.has("pageSize") ? inObj.get("pageSize").toString() : null;// 每页大小

            // 请求参数关键参数不能为空校验
            if (StringUtils.isBlank("start_time") || StringUtils.isBlank("end_time")) {
                response.setStatus("0");
                response.setMsg("请求参数中关键字段start_time或start_time不能为空！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            }
            // 时间格式校验
            try {
                DateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");
                Date startTime = df.parse(start_time);
                Date endTime = df.parse(end_time);
            } catch (Exception e) {
                response.setStatus("0");
                response.setMsg("请求参数中start_time或end_time格式不正确！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            }
            start_time = start_time.substring(0, 14) + "." + start_time.substring(14);
            end_time = end_time.substring(0, 14) + "." + end_time.substring(14);

            // 查询并封装数据
            List<OperatingCost> list = new ArrayList<OperatingCost>();
            if (StringUtils.isBlank(page) && StringUtils.isBlank(pageSize)) {
                // list = operatingCostDao.findOperatingCostByTime(start_time, end_time, order_type,
                // store_code, job_type);
                // response.setTotal(-1l);
                // response.setMsg("非分页数据！");
                response.setStatus("0");
                response.setMsg("请使用分页查询！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            } else {
                if (Integer.parseInt(pageSize) > 5000) {
                    response.setStatus("0");
                    response.setMsg("每页设置的size过大！");
                    return net.sf.json.JSONObject.fromObject(response).toString();
                }
                Pagination<OperatingCost> pagination =
                        operatingCostDao.findOperatingCostByTime((Integer.parseInt(page) - 1) * Integer.parseInt(pageSize), Integer.parseInt(pageSize), start_time, end_time, order_type, store_code, job_type, new BeanPropertyRowMapper<OperatingCost>(
                                OperatingCost.class), null);
                list = pagination.getItems();
                response.setTotal(pagination.getCount());
                response.setMsg("分页数据！");
            }
            response.setStatus("1");
            response.setData(list);
            if (list != null && list.isEmpty()) {
                response.setMsg("输入的条件无匹配结果！");
            }
            // System.out.println(net.sf.json.JSONObject.fromObject(response).toString());
            return net.sf.json.JSONObject.fromObject(response).toString();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("LMIS系统对接,操作费接口服务异常！", e);
            }
            response.setStatus("0");
            response.setMsg("WMS系统异常！");
            return net.sf.json.JSONObject.fromObject(response).toString();
        }
    }

    // 2、耗材费-采购明细接口服务
    @Override
    public String getMaterialFeePurchaseDetails(String requestParam) {
        LmisResponse<MaterialFeePurchaseDetails> response = new LmisResponse<MaterialFeePurchaseDetails>();
        // System.out.println("getMaterialFeePurchaseDetails:" + requestParam);
        if (StringUtils.isBlank(requestParam)) {
            response.setStatus("0");
            response.setMsg("请求参数为空！");
            return net.sf.json.JSONObject.fromObject(response).toString();
        }
        try {
            loxia.support.json.JSONObject inObj = new loxia.support.json.JSONObject(requestParam);
            String start_time = inObj.get("start_time").toString();// 起始时间
            String end_time = inObj.get("end_time").toString();// 结束时间

            String page = inObj.has("page") ? inObj.get("page").toString() : null;// 页号
            String pageSize = inObj.has("pageSize") ? inObj.get("pageSize").toString() : null;// 每页大小

            if (StringUtils.isBlank("start_time") || StringUtils.isBlank("end_time")) {
                response.setStatus("0");
                response.setMsg("请求参数中关键字段start_time或start_time不能为空！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            }
            // 时间格式校验
            try {
                DateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");
                Date startTime = df.parse(start_time);
                Date endTime = df.parse(end_time);
            } catch (Exception e) {
                response.setStatus("0");
                response.setMsg("请求参数中start_time或end_time格式不正确！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            }
            start_time = start_time.substring(0, 14) + "." + start_time.substring(14);
            end_time = end_time.substring(0, 14) + "." + end_time.substring(14);

            // 查询并封装数据
            List<MaterialFeePurchaseDetails> list = new ArrayList<MaterialFeePurchaseDetails>();
            if (StringUtils.isBlank(page) && StringUtils.isBlank(pageSize)) {
                // list =
                // materialFeePurchaseDetailsDao.findMaterialFeePurchaseDetailsByTime(start_time,
                // end_time);
                // response.setTotal(-1l);
                // response.setMsg("非分页数据！");
                response.setStatus("0");
                response.setMsg("请使用分页查询！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            } else {
                if (Integer.parseInt(pageSize) > 5000) {
                    response.setStatus("0");
                    response.setMsg("每页设置的size过大！");
                    return net.sf.json.JSONObject.fromObject(response).toString();
                }
                Pagination<MaterialFeePurchaseDetails> pagination =
                        materialFeePurchaseDetailsDao.findMaterialFeePurchaseDetailsByTime((Integer.parseInt(page) - 1) * Integer.parseInt(pageSize), Integer.parseInt(pageSize), start_time, end_time, new BeanPropertyRowMapper<MaterialFeePurchaseDetails>(
                                MaterialFeePurchaseDetails.class), null);
                list = pagination.getItems();
                response.setTotal(pagination.getCount());
                response.setMsg("分页数据！");
            }
            response.setStatus("1");
            response.setData(list);
            if (list != null && list.isEmpty()) {
                response.setMsg("输入的条件无匹配结果！");
            }
            // System.out.println(net.sf.json.JSONObject.fromObject(response).toString());
            return net.sf.json.JSONObject.fromObject(response).toString();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("LMIS系统对接,耗材费-采购明细接口服务异常！", e);
            }
            response.setStatus("0");
            response.setMsg("WMS系统异常！");
            return net.sf.json.JSONObject.fromObject(response).toString();
        }
    }

    // 3、耗材费-实际使用量接口服务
    @Override
    public String getMaterialFeeActualUsage(String requestParam) {
        LmisResponse<MaterialFeeActualUsage> response = new LmisResponse<MaterialFeeActualUsage>();
        // System.out.println("getMaterialFeeActualUsage:" + requestParam);
        if (StringUtils.isBlank(requestParam)) {
            response.setStatus("0");
            response.setMsg("请求参数为空！");
            return net.sf.json.JSONObject.fromObject(response).toString();
        }
        try {
            loxia.support.json.JSONObject inObj = new loxia.support.json.JSONObject(requestParam);
            String start_time = inObj.get("start_time").toString();// 起始时间
            String end_time = inObj.get("end_time").toString();// 结束时间

            String page = inObj.has("page") ? inObj.get("page").toString() : null;// 页号
            String pageSize = inObj.has("pageSize") ? inObj.get("pageSize").toString() : null;// 每页大小

            String store_code = inObj.has("store_code") ? (inObj.get("store_code").toString().equals("") ? null : inObj.get("store_code").toString()) : null;// 店铺编码

            if (StringUtils.isBlank("start_time") || StringUtils.isBlank("end_time")) {
                response.setStatus("0");
                response.setMsg("请求参数中关键字段start_time或start_time不能为空！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            }
            // 时间格式校验
            try {
                DateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");
                Date startTime = df.parse(start_time);
                Date endTime = df.parse(end_time);
            } catch (Exception e) {
                response.setStatus("0");
                response.setMsg("请求参数中start_time或end_time格式不正确！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            }
            start_time = start_time.substring(0, 14) + "." + start_time.substring(14);
            end_time = end_time.substring(0, 14) + "." + end_time.substring(14);

            // 查询并封装数据
            List<MaterialFeeActualUsage> list = new ArrayList<MaterialFeeActualUsage>();
            if (StringUtils.isBlank(page) && StringUtils.isBlank(pageSize)) {
                // list = materialFeeActualUsageDao.findMaterialFeeActualUsageByTime(start_time,
                // end_time, store_code);
                // response.setTotal(-1l);
                // response.setMsg("非分页数据！");
                response.setStatus("0");
                response.setMsg("请使用分页查询！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            } else {
                if (Integer.parseInt(pageSize) > 5000) {
                    response.setStatus("0");
                    response.setMsg("每页设置的size过大！");
                    return net.sf.json.JSONObject.fromObject(response).toString();
                }
                Pagination<MaterialFeeActualUsage> pagination =
                        materialFeeActualUsageDao.findMaterialFeeActualUsageByTime((Integer.parseInt(page) - 1) * Integer.parseInt(pageSize), Integer.parseInt(pageSize), start_time, end_time, store_code, new BeanPropertyRowMapper<MaterialFeeActualUsage>(
                                MaterialFeeActualUsage.class), null);
                list = pagination.getItems();
                response.setTotal(pagination.getCount());
                response.setMsg("分页数据！");
            }
            response.setStatus("1");
            response.setData(list);
            if (list != null && list.isEmpty()) {
                response.setMsg("输入的条件无匹配结果！");
            }
            // System.out.println(net.sf.json.JSONObject.fromObject(response).toString());
            return net.sf.json.JSONObject.fromObject(response).toString();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("LMIS系统对接,耗材费-实际使用量接口服务异常！", e);
            }
            response.setStatus("0");
            response.setMsg("WMS系统异常！");
            return net.sf.json.JSONObject.fromObject(response).toString();
        }
    }

    // 4、快递运单接口服务
    @Override
    public String getExpressWaybill(String requestParam) {
        LmisResponse<ExpressWaybill> response = new LmisResponse<ExpressWaybill>();
        // System.out.println("getExpressWaybill:" + requestParam);
        if (StringUtils.isBlank(requestParam)) {
            response.setStatus("0");
            response.setMsg("请求参数为空！");
            return net.sf.json.JSONObject.fromObject(response).toString();
        }
        try {
            loxia.support.json.JSONObject inObj = new loxia.support.json.JSONObject(requestParam);
            log.error("getExpressWaybill_1"+inObj);
            String start_time = inObj.get("start_time").toString();// 起始时间
            String end_time = inObj.get("end_time").toString();// 结束时间
            String ownCode=null;//店鋪編碼
            String whCode=null;//倉庫編碼
            try {
                whCode = inObj.get("whCode").toString();
            } catch (Exception e) {
                log.error("getExpressWaybill_1:"+ownCode+";"+whCode);
            }
            
            try {
                ownCode = inObj.get("ownCode").toString();
            } catch (Exception e) {
                log.error("getExpressWaybill_3:"+ownCode+";"+whCode);
            }



            String page = inObj.has("page") ? inObj.get("page").toString() : null;// 页号
            String pageSize = inObj.has("pageSize") ? inObj.get("pageSize").toString() : null;// 每页大小

            // time_type若为出库时间 则time_type="0"
            String time_type = inObj.has("time_type") ? (inObj.get("time_type").toString().equals("") ? "0" : inObj.get("time_type").toString()) : "0";// 时间类型(默认出库时间)
            String express_number = inObj.has("express_number") ? (inObj.get("express_number").toString().equals("") ? null : inObj.get("express_number").toString()) : null;// 运单号

            if (StringUtils.isBlank("start_time") || StringUtils.isBlank("end_time")) {
                response.setStatus("0");
                response.setMsg("请求参数中关键字段start_time或start_time不能为空！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            }
            // 时间格式校验
            try {
                DateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");
                Date startTime = df.parse(start_time);
                Date endTime = df.parse(end_time);
            } catch (Exception e) {
                response.setStatus("0");
                response.setMsg("请求参数中start_time或end_time格式不正确！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            }

            start_time = start_time.substring(0, 14) + "." + start_time.substring(14);
            end_time = end_time.substring(0, 14) + "." + end_time.substring(14);

            // 查询并封装数据
            List<ExpressWaybill> list = new ArrayList<ExpressWaybill>();
            if (StringUtils.isBlank(page) && StringUtils.isBlank(pageSize)) {
                response.setStatus("0");
                response.setMsg("请使用分页查询！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            } else {
                if (Integer.parseInt(pageSize) > 5000) {
                    response.setStatus("0");
                    response.setMsg("每页设置的size过大！");
                    return net.sf.json.JSONObject.fromObject(response).toString();
                }
                Pagination<ExpressWaybill> pagination =
                        expressWaybillDao.findExpressWaybillByTime((Integer.parseInt(page) - 1) * Integer.parseInt(pageSize), Integer.parseInt(pageSize), start_time, end_time, time_type, express_number,ownCode,whCode, new BeanPropertyRowMapper<ExpressWaybill>(
                                ExpressWaybill.class), null);
                list = pagination.getItems();
                response.setTotal(pagination.getCount());
                response.setMsg("分页数据！");
            }
            // 查询并封装商品明细
            if (list != null && !list.isEmpty()) {
                // 开启多线程获取明细数据
                getExpressWaybillData(list);
            }
            response.setStatus("1");
            response.setData(list);
            if (list != null && list.isEmpty()) {
                response.setMsg("输入的条件无匹配结果！");
            }
            // System.out.println(net.sf.json.JSONObject.fromObject(response).toString());
            return net.sf.json.JSONObject.fromObject(response).toString();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("LMIS系统对接,快递运单接口服务异常！", e);
            }
            response.setStatus("0");
            response.setMsg("WMS系统异常！");
            return net.sf.json.JSONObject.fromObject(response).toString();
        }
    }

    // 开启多线程获取运单分页数据
    private void getExpressWaybillData(List<ExpressWaybill> list) {
        if (log.isInfoEnabled()) {
            log.info("LmisManager getExpressWaybillData ...Enter");
        }
        Integer num = null;
        try {
            num = chooseOptionManager.getSystemThreadValueByKey(Constants.EXPRESSWAYBILL_THREAD);
        } catch (Exception e) {
            log.error("获取配置运单线程数失败！", e);
        }
        if (num == null || num == 0) {
            num = 5;
        }
        ExecutorService pool = Executors.newFixedThreadPool(num);
        for (ExpressWaybill p : list) {
            if (p.getStaId() != null) {
                String threadName = "getExpressWaybillData:" + p.getStaId();
                LmisThread t = new LmisThread(threadName);
                t.setId(Long.parseLong(p.getStaId()));
                t.setExpressWaybill(p);
                t.setName("getExpressWaybillData");
                Thread t1 = new Thread(t, threadName);
                pool.execute(t1);
            }
        }
        pool.shutdown();
        boolean isFinish = false;
        while (!isFinish) {
            isFinish = pool.isTerminated();
            try {
                Thread.sleep(1 * 1000L);
            } catch (InterruptedException e) {
                if (log.isErrorEnabled()) {
                    log.error("LmisThread-InterruptedException", e);
                }
            }
        }
        if (log.isInfoEnabled()) {
            log.info("LmisManager getExpressWaybillData ...End");
        }
    }

    // 5、仓储费接口服务
    @Override
    public String getWarehouseCharges(String requestParam) {
        return "请到相应SFTP服务器上获取数据!";
        // LmisResponse<WarehouseCharges> response = new LmisResponse<WarehouseCharges>();
        // try {
        // loxia.support.json.JSONObject inObj = new loxia.support.json.JSONObject(requestParam);
        // if (requestParam != null) {
        // String start_time = inObj.has("start_time") ?
        // (inObj.get("start_time").toString().equals("") ? null :
        // inObj.get("start_time").toString()) : null;
        // // 大类(0:自营仓；1：外包仓)
        // String warehouse_type = inObj.has("warehouse_type") ?
        // (inObj.get("warehouse_type").toString().equals("") ? null :
        // inObj.get("warehouse_type").toString()) : null;
        // // 店铺编号
        // String store_code = inObj.has("store_code") ?
        // (inObj.get("store_code").toString().equals("") ? null :
        // inObj.get("store_code").toString()) : null;
        // // 仓库编号
        // String warehouse_code = inObj.has("warehouse_code") ?
        // (inObj.get("warehouse_code").toString().equals("") ? null :
        // inObj.get("warehouse_code").toString()) : null;
        // // 区域代码
        // String area_code = inObj.has("area_code") ? (inObj.get("area_code").toString().equals("")
        // ? null : inObj.get("area_code").toString()) : null;
        // // 时间格式校验
        // Date historyDate = null;
        // try {
        // DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        // historyDate = df.parse(start_time);
        // } catch (Exception e) {
        // response.setStatus("0");
        // response.setMsg("请求参数中时间格式不正确！参考格式：2099-10-01");
        // return net.sf.json.JSONObject.fromObject(response).toString();
        // }
        // if (warehouse_type != null) {
        // totalZeroInvTask.saveFile(historyDate, DateUtils.addDays(historyDate, 1),
        // Integer.parseInt(warehouse_type), store_code, warehouse_code, area_code);
        // } else {
        // totalZeroInvTask.saveFile(historyDate, DateUtils.addDays(historyDate, 1), null,
        // store_code, warehouse_code, area_code);
        // }
        // } else {
        // totalZeroInvTask.saveFile(DateUtils.addDays(new Date(), -1), new Date(), null, null,
        // null, null);
        // }
        // totalZeroInvTask.uploadZeroInv();
        // totalZeroInvTask.backupsFile();
        // response.setStatus("1");
        // response.setMsg("生成对应日期的零点库存文件成功！");
        // return net.sf.json.JSONObject.fromObject(response).toString();
        // } catch (Exception e) {
        // log.error("LMIS系统对接,生成对应日期的零点库存文件异常！", e);
        // response.setStatus("0");
        // response.setMsg("WMS系统异常！");
        // return net.sf.json.JSONObject.fromObject(response).toString();
        // } finally {
        // try {
        // totalZeroInvTask.clear();
        // } catch (Exception e2) {
        // log.error("清除缓存数据失败！", e2);
        // }
        // }
    }

    // 6、退货入库接口服务
    @Override
    public String getReturnStorageData(String requestParam) {
        // System.out.println("getReturnStorageData:" + requestParam);
        LmisResponse<ReturnStorage> response = new LmisResponse<ReturnStorage>();
        if (StringUtils.isBlank(requestParam)) {
            response.setStatus("0");
            response.setMsg("请求参数为空！");
            return net.sf.json.JSONObject.fromObject(response).toString();
        }
        try {
            loxia.support.json.JSONObject inObj = new loxia.support.json.JSONObject(requestParam);
            String start_time = inObj.get("start_time").toString();// 入库完成起始时间
            String end_time = inObj.get("end_time").toString();// 入库完成结束时间

            String page = inObj.has("page") ? inObj.get("page").toString() : null;// 页号
            String pageSize = inObj.has("pageSize") ? inObj.get("pageSize").toString() : null;// 每页大小

            String related_no = inObj.has("related_no") ? (inObj.get("related_no").toString().equals("") ? null : inObj.get("related_no").toString()) : null;// PACS退货相关单号

            if (StringUtils.isBlank("start_time") || StringUtils.isBlank("end_time")) {
                response.setStatus("0");
                response.setMsg("请求参数中关键字段start_time或start_time不能为空！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            }
            // 时间格式校验
            try {
                DateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");
                Date startTime = df.parse(start_time);
                Date endTime = df.parse(end_time);
            } catch (Exception e) {
                response.setStatus("0");
                response.setMsg("请求参数中start_time或end_time格式不正确！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            }
            start_time = start_time.substring(0, 14) + "." + start_time.substring(14);
            end_time = end_time.substring(0, 14) + "." + end_time.substring(14);

            // 查询并封装数据
            List<ReturnStorage> list = new ArrayList<ReturnStorage>();
            if (StringUtils.isBlank(page) && StringUtils.isBlank(pageSize)) {
                response.setStatus("0");
                response.setMsg("请使用分页查询！");
                return net.sf.json.JSONObject.fromObject(response).toString();
            } else {
                if (Integer.parseInt(pageSize) > 5000) {
                    response.setStatus("0");
                    response.setMsg("每页设置的size过大！");
                    return net.sf.json.JSONObject.fromObject(response).toString();
                }
                Pagination<ReturnStorage> pagination =
                        returnStorageDao.findreturnStorageDataByTime((Integer.parseInt(page) - 1) * Integer.parseInt(pageSize), Integer.parseInt(pageSize), start_time, end_time, related_no, new BeanPropertyRowMapper<ReturnStorage>(ReturnStorage.class),
                                null);
                list = pagination.getItems();
                response.setTotal(pagination.getCount());
                response.setMsg("分页数据！");
            }
            response.setStatus("1");
            response.setData(list);
            if (list != null && list.isEmpty()) {
                response.setMsg("输入的条件无匹配结果！");
            }
            // System.out.println(net.sf.json.JSONObject.fromObject(response).toString());
            return net.sf.json.JSONObject.fromObject(response).toString();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("LMIS系统对接,退货入库接口服务异常！", e);
            }
            response.setStatus("0");
            response.setMsg("WMS系统异常！");
            return net.sf.json.JSONObject.fromObject(response).toString();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public String sendOrderOutBoundData(File file,Long ouId) {
        List<OrderOutBoundInfo> orderOutBoundList = new ArrayList<OrderOutBoundInfo>();
        List<OrderOutBoundInfo> orderOutBoundLists = new ArrayList<OrderOutBoundInfo>();
        Map<String, Object> resultMap = excelReadManager.orderOutBoundInfoImport(file);
        String errorMsg = "";
        int errorCount = 0;
        // 获取Excel导入数据
        orderOutBoundList = (List<OrderOutBoundInfo>) resultMap.get("orderOutBound");
        if (orderOutBoundList != null && orderOutBoundList.size() > 0) {
            // 导入作业单校验
            for (OrderOutBoundInfo orderOutBoundInfo : orderOutBoundList) {
                String slipCode = null;
                if (orderOutBoundInfo.getRelatedNo() != null && !orderOutBoundInfo.getRelatedNo().equals("")) {
                    slipCode = orderOutBoundInfo.getRelatedNo().trim();
                }
                List<StockTransApplication> staList = staDao.findBySlipCodeOuId(slipCode,ouId);
                StockTransApplication sta = null;
                if (staList.size() == 0) {
                    errorCount++;
                } else {
                    sta = staList.get(0);
                }
                if (sta == null) {
                    errorMsg = errorMsg + "[ 相关单据号:" + orderOutBoundInfo.getRelatedNo() + " 不存在 ]";
                    errorCount++;
                    // 状态校验
                } else if (sta.getStatus().getValue() == 1 || sta.getStatus().getValue() == 3 || sta.getStatus().getValue() == 15 || sta.getStatus().getValue() == 17) {
                    errorMsg = errorMsg + "[ 相关单据号:" + orderOutBoundInfo.getRelatedNo() + "状态为" + staStatusname(sta.getStatus().getValue()) + ",禁止同步  ]";
                    errorCount++;
                    // 重复校验
                } else if (orderOutBoundInfo.getWareHouse() == null || "".equals(orderOutBoundInfo.getWareHouse())) {
                    errorMsg = errorMsg + "[ 相关单据号:" + orderOutBoundInfo.getRelatedNo() + "的仓库信息必填 ]";
                    errorCount++;
                } else {
                    int repeatCount = 0;
                    for (OrderOutBoundInfo orderOutBoundInfo2 : orderOutBoundList) {
                        if (orderOutBoundInfo.getRelatedNo().equals(orderOutBoundInfo2.getRelatedNo()) && !errorMsg.contains(orderOutBoundInfo2.getRelatedNo())) {
                            repeatCount++;
                        }
                        if (repeatCount >= 2) {
                            errorMsg = errorMsg + "[ 相关单据号:" + orderOutBoundInfo.getRelatedNo() + " 存在重复 ]";
                            errorCount++;
                        }
                    }
                }
            }
            // 校验通过，封装list
            if (errorCount == 0) {
                for (OrderOutBoundInfo orderOutBoundInfo : orderOutBoundList) {
                    StockTransApplication sta = staDao.findBySlipCodeOuId(orderOutBoundInfo.getRelatedNo().trim(),ouId).get(0);
                    Long pid = staDao.getPickingListIdByCode(sta.getCode(), new SingleColumnRowMapper<Long>(Long.class));
                    PickingList p = pickingListDao.getByPrimaryKey(pid);
                    List<StaLine> staLineList = staLineDao.findByStaId(sta.getId());
                    BiChannel shop = shopDao.getByCode(sta.getOwner());
                    OperationUnit op = opDao.getByPrimaryKey(sta.getMainWarehouse().getId());
                    StaDeliveryInfo sd = staDeliveryInfoDao.getByPrimaryKey(sta.getStaDeliveryInfo().getId());
                    // 出库单头信息
                    orderOutBoundInfo.setStoreCode(sta.getOwner());
                    orderOutBoundInfo.setStoreName(shop.getName());
                    orderOutBoundInfo.setRelatedNo(sta.getRefSlipCode());
                    orderOutBoundInfo.setSlipCode1(sta.getSlipCode1());
                    if (p != null) {
                        orderOutBoundInfo.setBatchNo(p.getCode());
                    }
                    orderOutBoundInfo.setCreateTime(sta.getCancelTime());
                    orderOutBoundInfo.setLpCode(sd.getLpCode());
                    orderOutBoundInfo.setReceiver(sd.getReceiver());
                    orderOutBoundInfo.setReceiverAddress(sd.getAddress());
                    orderOutBoundInfo.setTotalQty(sta.getSkuQty() + "");
                    orderOutBoundInfo.setTotalAmount(sta.getTotalActual() + "");
                    orderOutBoundInfo.setOrderAmount(sta.getTotalActual() + "");
                    orderOutBoundInfo.setTransferFee(sd.getTransferFee() + "");
                    orderOutBoundInfo.setDiliveryWhName(op.getName());
                    orderOutBoundInfo.setRtnReceiver(shop.getReturnDefaultOwner());
                    orderOutBoundInfo.setQrCode(sd.getReturnTrackingNo());
                    List<OrderOutBoundLineInfo> orderOutBoundLineList = new ArrayList<OrderOutBoundLineInfo>();
                    for (StaLine staLine : staLineList) {
                        // 商品明细
                        OrderOutBoundLineInfo orderOutBoundLineInfo = new OrderOutBoundLineInfo();
                        Sku sku = skuDao.getByPrimaryKey(staLine.getSku().getId());
                        orderOutBoundLineInfo.setBarCode(sku.getBarCode());
                        orderOutBoundLineInfo.setSkuCode(sku.getCode());
                        orderOutBoundLineInfo.setQty(staLine.getQuantity() + "");
                        orderOutBoundLineInfo.setUnitPrice(staLine.getUnitPrice() + "");
                        orderOutBoundLineInfo.setSkuName(sku.getName());
                        orderOutBoundLineInfo.setSupplierSkuCode(sku.getSupplierCode());
                        orderOutBoundLineList.add(orderOutBoundLineInfo);
                    }
                    orderOutBoundInfo.setLine(orderOutBoundLineList);
                    orderOutBoundLists.add(orderOutBoundInfo);
                }
            }
            try {
                // 校验通过，发送MQ
                if (errorCount == 0) {
                    String reqJson = JsonUtil.writeValue(orderOutBoundList);
                    MessageCommond mc = new MessageCommond();
                    mc.setIsMsgBodySend(true);
                    mc.setMsgId(System.currentTimeMillis() + UUIDUtil.getUUID());
                    mc.setMsgType("com.jumbo.wms.manager.lmis.LmisManagerImpl.sendOrderOutBoundData");
                    mc.setMsgBody(reqJson);
                    mc.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    producerServer.sendDataMsgConcurrently(MQ_WMS2LMIS_ORDER_OUTBOUND, mc);
                    errorMsg = "success";// 发送成功
                }
            } catch (Exception e) {
                throw new BusinessException("发送MQ失败,请联系wms_service");
            }
        }
        return errorMsg;
    }

    @Override
    public void sendStaCartonInfo() {
        // 查询纸箱数据
        List<StaCartonInfo> staCartonInfoList = staCartonInfoDao.findByStatus(1, new BeanPropertyRowMapperExt<>(StaCartonInfo.class));
        if (staCartonInfoList == null) {
            log.error("staCartonInfoList is null");
            return;
        }

        JobCartonInfo jobCartonInfo = new JobCartonInfo();
        jobCartonInfo.setDatasource("WMS3");

        // 组装消息数据
        MessageCommond mc = new MessageCommond();
        mc.setIsMsgBodySend(true);
        mc.setMsgType("wmsToLmisSocRmq");

        for (StaCartonInfo staCartonInfo : staCartonInfoList) {
            if (staCartonInfo.getErrorCount() >= 5) {
                continue;
            }
            StockTransApplication sta = staDao.getByPrimaryKey(staCartonInfo.getStaId());
            if (sta == null) {
                log.error("sta is null, staId:{}", staCartonInfo.getStaId());
                continue;
            }
            BiChannel biChannel = biChannelDao.getByCode(sta.getOwner());
            if (biChannel == null) {
                log.error("biChannel is null, sta-Owner:{}", sta.getOwner());
                continue;
            }

            jobCartonInfo.setUpperId((int) (System.currentTimeMillis() / 1000));
            jobCartonInfo.setStoreId(sta.getOwner());
            jobCartonInfo.setStoreName(biChannel.getName());

            OperationUnit operationUnit = opDao.getByPrimaryKey(sta.getMainWarehouse().getId());
            if (operationUnit == null) {
                log.error("operationUnit is null, staId:{}", sta.getId());
                continue;
            }
            jobCartonInfo.setWhsId(operationUnit.getCode());
            jobCartonInfo.setWhsName(operationUnit.getName());

            jobCartonInfo.setJobOrder(sta.getCode());
            jobCartonInfo.setJobType(sta.getType().name());
            jobCartonInfo.setJobStatus(sta.getIntStatus());
            jobCartonInfo.setJobCreateTime(sta.getCreateTime());

            // sta的type为退换货申请-退货入库
            if (sta.getType().equals(StockTransApplicationType.INBOUND_RETURN_REQUEST)) {
                jobCartonInfo.setCartonNum(1);
            } else {
                jobCartonInfo.setCartonNum(sta.getCartonNum());
            }

            mc.setMsgBody(JsonUtil.writeValue(jobCartonInfo));

            staCartonInfo.setStatus(2);
            staCartonInfoDao.save(staCartonInfo);
            try {
                mc.setMsgId(sta.getCode() + UUIDUtil.getUUID());
                mc.setTags(operationUnit.getName());
                mc.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                producerServer.sendDataMsgConcurrently(MQ_WMS2LMIS_SOC_RMQ, mc);
            } catch (Exception e) {
                log.error("发送MQ失败,请联系wms_service", e);
                staCartonInfo.setErrorCount(staCartonInfo.getErrorCount() + 1);
                staCartonInfoDao.save(staCartonInfo);
            }

            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
                log.warn("sleep fail");
            }
        }
    }

    private String staStatusname(int value) {
        switch (value) {
            case 1:
                return "新建";
            case 2:
                return "已核对";
            case 17:
                return "取消已处理";
            case 15:
                return "取消未处理";
            default:
                return "";
        }
    }
}
