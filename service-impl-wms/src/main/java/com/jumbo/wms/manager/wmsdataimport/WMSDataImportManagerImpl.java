package com.jumbo.wms.manager.wmsdataimport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import loxia.dao.Pagination;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.task.CommonConfigDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.SkuSnLogDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.wmsdataimport.SnAndValidDateSkuFlowDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.dataimport.ShopDataSource;
import com.jumbo.wms.model.dataimport.SnAndValidDateSkuFlow;
import com.jumbo.wms.model.dataimport.WmsInBound;
import com.jumbo.wms.model.dataimport.WmsInBoundLine;
import com.jumbo.wms.model.dataimport.WmsInBoundTransportMgmt;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.task.CommonConfig;
import com.jumbo.wms.model.warehouse.InboundStoreMode;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * 
 * @author henghui.zhang wms3.0数据导出
 */
// @Transactional
@Service("wmsDataImportManager")
public class WMSDataImportManagerImpl implements WMSDataImportManager {
    private static final long serialVersionUID = 7630745492771736552L;
    protected static final Logger log = LoggerFactory.getLogger(WMSDataImportManagerImpl.class);

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SnAndValidDateSkuFlowDao snAndValidDateSkuFlowDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private SkuSnLogDao skuSnLogDao;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private CommonConfigDao commonConfigDao;

    /**
     * 查询文件生成状态
     * 
     */
    @Override
    public String getFileCreateStatus() {
        List<ChooseOption> ops = chooseOptionDao.findAllOptionListByCategoryCode(Constants.WMS_DATA_IMPORT_GROUP);
        Map<String, String> res = new HashMap<String, String>();
        if (ops != null && !ops.isEmpty()) {
            for (ChooseOption op : ops) {
                res.put(op.getOptionKey(), op.getOptionValue());
            }
            return net.sf.json.JSONObject.fromObject(res).toString();
        }
        return null;
    }

    /**
     * 生成退货入库尚未完成的SN或者有效期商品相关数据
     */
    @Override
    public void generateSnAndValidDateSkuFlow(Date createTime, Date endCreateTime, String postshopInput, String slipcodes) {
        // 店铺集合
        if (StringUtils.isBlank(postshopInput) && StringUtils.isBlank(slipcodes)) {
            throw new BusinessException(ErrorCode.CHANNEL_OR_SLIPCODES_NOT_EXIST);
        }
        String[] owners = null;
        // List<String> ownerList = new ArrayList<String>();
        if (!StringUtils.isBlank(postshopInput)) {
            owners = StringUtils.split(postshopInput, ";");
            // ownerList = Arrays.asList(owners);
        }
        // 平台单据号集合
        List<String> slipcode1List = null;
        if (!StringUtils.isBlank(slipcodes)) {
            if (slipcodes.contains("；")) {
                // 不能包含中文分号
                throw new BusinessException(ErrorCode.CONTAINS_ZH_SEMICOLON);
            }
            String[] slipcode1s = StringUtils.split(slipcodes, ";");
            slipcode1List = Arrays.asList(slipcode1s);
        }
        if (slipcode1List != null && !slipcode1List.isEmpty()) {
            for (String slipcode1 : slipcode1List) {
                List<StockTransApplication> stas = staDao.findBySlipCode1AndFinished(slipcode1);
                if (stas == null || (stas != null && stas.isEmpty())) {
                    // 单号{}对应作业单不存
                    throw new BusinessException(ErrorCode.STA_NOT_EXIST, slipcode1);
                }
            }
        }
        // 创建目录
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.WMS_SHARE_ROOT_DIR);
        String rootShareDir = null;
        if (configs != null && !configs.isEmpty()) {
            rootShareDir = configs.get(0).getParaValue();
        }
        String aimFile = createDir("snOrValidDateSkuFlow", rootShareDir);
        String tempDir = rootShareDir + File.separator + "dataImport" + File.separator + "temporaryFile";
        if (log.isInfoEnabled()) {
            log.info("WMSDataImportManager getsnOrValidDateSkuFlow ...Enter");
        }
        // 设置文件生成标识
        setFileGenerateFlag(Constants.WMS_DATA_IMPORT_GROUP, Constants.WMS_DATA_IMPORT_SN_EXPDATE, 0);
        Integer num = null;
        // Integer numSun = null;
        try {
            num = chooseOptionManager.getSystemThreadValueByKey(Constants.SN_VALIDDATESKU_SHOP_THREAD_NO);
            // numSun =
            // chooseOptionManager.getSystemThreadValueByKey(Constants.Packaging_Sn_ExpDate_THREAD_NO);
        } catch (Exception e) {
            log.error("获取配置线程数失败！", e);
        }
        if (num == null || num == 0) {
            num = 5;
        }
        // if (numSun == null || numSun == 0) {
        // numSun = 3;
        // }
        // 开启多线程，一店铺一个线程
        ExecutorService pool = Executors.newFixedThreadPool(num);
        // 子线程池
        // ExecutorService poolSun = Executors.newFixedThreadPool(num);
        int threadId = 1;
        if (owners != null) {
            for (String owner : owners) {
                String threadName = "getSnOrValidDateSkuFlow:" + owner;
                WMSDataImportThread t = new WMSDataImportThread(threadName);
                t.setId(Long.parseLong("" + threadId++));
                t.setOwner(owner);
                t.setSlipCode1List(slipcode1List);
                t.setPath(tempDir);
                t.setStartTime(createTime);
                t.setEndTime(endCreateTime);
                // t.setExecutorService(poolSun);
                t.setExecutorService(null);
                t.setName("getSnOrValidDateSkuFlow");
                Thread t1 = new Thread(t, threadName);
                pool.execute(t1);
            }
        } else {
            String threadName = "getSnOrValidDateSkuFlow: no owner";
            WMSDataImportThread t = new WMSDataImportThread(threadName);
            t.setId(Long.parseLong("" + threadId++));
            t.setSlipCode1List(slipcode1List);
            t.setPath(tempDir);
            t.setStartTime(createTime);
            t.setEndTime(endCreateTime);
            // t.setExecutorService(poolSun);
            t.setExecutorService(null);
            t.setName("getSnOrValidDateSkuFlow: no owner");
            Thread t1 = new Thread(t, threadName);
            pool.execute(t1);
        }
        // 关闭主线程池
        pool.shutdown();
        boolean isFinish = false;
        while (!isFinish) {
            isFinish = pool.isTerminated();
            try {
                Thread.sleep(1 * 1000L);
            } catch (InterruptedException e) {
                if (log.isErrorEnabled()) {
                    log.error("generateSnAndValidDateSkuFlow Thread InterruptedException:", e);
                }
            }
        }
        // 关闭子线程池
        // poolSun.shutdown();
        // boolean sunIsFinish = false;
        // while (!sunIsFinish) {
        // isFinish = poolSun.isTerminated();
        // try {
        // Thread.sleep(1 * 1000L);
        // } catch (InterruptedException e) {
        // }
        // }
        if (log.isInfoEnabled()) {
            log.info("WMSDataImportManager getsnOrValidDateSkuFlow ...End");
        }
        // 文件合并
        if (owners != null) {
            for (String owner : owners) {
                try {
                    File fileSource = new File(tempDir + File.separator + owner + ".txt");
                    if (fileSource.exists()) {
                        com.jumbo.util.FileUtils fileUtil = new com.jumbo.util.FileUtils();
                        fileUtil.copyWithChannels(fileSource, new File(aimFile), true);
                        // FileUtils.copyFile(fileSource, new File(aimFile), false);
                        fileSource.delete();
                    }
                } catch (Exception e) {
                    // log.error("合并文件异常！");
                    if (log.isErrorEnabled()) {
                        log.error("合并文件异常！", e);
                    }
                }
            }
        } else {
            try {
                File fileSource = new File(tempDir + File.separator + "noOwner.txt");
                if (fileSource.exists()) {
                    FileUtils.copyFile(fileSource, new File(aimFile), true);
                    fileSource.delete();
                }
            } catch (IOException e) {
                // log.error("合并文件异常！");
                if (log.isErrorEnabled()) {
                    log.error("合并文件异常！", e);
                }
            }
        }
        // 设置成功标识
        setFileGenerateFlag(Constants.WMS_DATA_IMPORT_GROUP, Constants.WMS_DATA_IMPORT_SN_EXPDATE, 1);
    }

    /**
     * 生成单个店铺的SnAndValidDateSkuFlow数据--线程任务
     */
    @Override
    public Boolean createEachFile(Date createTime, Date endCreateTime, String owner, List<String> slipCode1s, String path, ExecutorService executorService) {
        File file = null;
        if (owner != null) {
            file = new File(path + File.separator + owner + ".txt");
        } else {
            file = new File(path + File.separator + "noOwner.txt");
        }
        FileWriter fileWriter = null;
        try {
            file.createNewFile();
            int pageSize = 1000;
            Pagination<SnAndValidDateSkuFlow> pagination =
                    snAndValidDateSkuFlowDao.getSnAndValidDateSkuFlowDate(0, pageSize, createTime, endCreateTime, owner, slipCode1s, new BeanPropertyRowMapper<SnAndValidDateSkuFlow>(SnAndValidDateSkuFlow.class), null);
            int totalPages = pagination.getTotalPages();
            // 未查询到数据直接返回
            if (pagination.getCount() == 0l) {
                log.info("Pagination<SnAndValidDateSkuFlow> size:0 " + owner);
                return true;
            }
            fileWriter = new FileWriter(file, true);
            for (int i = 0; i < totalPages; i++) {
                // 不重复查询第一页
                if (i == 0) {
                    List<SnAndValidDateSkuFlow> list = pagination.getItems();
                    // 重新封装并返回的list
                    List<SnAndValidDateSkuFlow> newList = new ArrayList<SnAndValidDateSkuFlow>();
                    // sn号和过期时间封装
                    rearchAndPackagingSnAndExpDate(list, newList, executorService);
                    if (newList.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < newList.size(); j++) {
                            sb.append(newList.get(j).toString() + "\r\n");
                        }
                        fileWriter.write(sb.toString());
                        fileWriter.flush();
                    }
                    continue;
                }
                // 剩余页数据
                Pagination<SnAndValidDateSkuFlow> temp =
                        snAndValidDateSkuFlowDao.getSnAndValidDateSkuFlowDate(i * pageSize, pageSize, createTime, endCreateTime, owner, slipCode1s, new BeanPropertyRowMapper<SnAndValidDateSkuFlow>(SnAndValidDateSkuFlow.class), null);
                List<SnAndValidDateSkuFlow> list = temp.getItems();
                // 重新封装并返回的list
                List<SnAndValidDateSkuFlow> newList = new ArrayList<SnAndValidDateSkuFlow>();
                // sn号和过期时间封装
                rearchAndPackagingSnAndExpDate(list, newList, executorService);
                if (newList.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < newList.size(); j++) {
                        sb.append(newList.get(j).toString() + "\r\n");
                    }
                    fileWriter.write(sb.toString());
                    fileWriter.flush();
                }
            }
            return true;
        } catch (Exception e) {
            log.error("文件生成失败,owner:" + owner, e);
            return false;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error("createEachFile-IOException！", e);
                    }
                    return false;
                }
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error("createEachFile-close-IOException！", e);
                    }
                    return false;
                }
            }
        }
    }

    /**
     * SnAndValidDateSkuFlow中的sn号和过期时间封装,开启子线程// list:原始list //newList：重新封装的list(size>=前者)
     */
    private void rearchAndPackagingSnAndExpDate(List<SnAndValidDateSkuFlow> list, List<SnAndValidDateSkuFlow> newList, ExecutorService pool) {
        Integer numSun = null;
        try {
            numSun = chooseOptionManager.getSystemThreadValueByKey(Constants.Packaging_Sn_ExpDate_THREAD_NO);
        } catch (Exception e) {
            log.error("获取配置子线程数失败！", e);
        }
        if (numSun == null || numSun == 0) {
            numSun = 3;
        }
        // 子线程池
        ExecutorService sunPool = Executors.newFixedThreadPool(numSun);
        if (log.isDebugEnabled()) {
            log.debug("WMSDataImportManager packagingSnAndExpDate ...start");
        }
        int sunthreadId = 1;
        if (list != null) {
            for (SnAndValidDateSkuFlow snAndValidDateSkuFlow : list) {
                String threadName = "packagingSnAndExpDate";
                WMSDataImportThread t = new WMSDataImportThread(threadName);
                t.setSnAndValidDateSkuFlow(snAndValidDateSkuFlow);
                t.setList(newList);
                t.setId(Long.parseLong("" + sunthreadId++));
                t.setName("packagingSnAndExpDate");
                Thread t1 = new Thread(t, threadName);
                sunPool.execute(t1);
            }
        }
        sunPool.shutdown();
        boolean isFinish = false;
        while (!isFinish) {
            isFinish = sunPool.isTerminated();
            try {
                Thread.sleep(1 * 1000L);
            } catch (InterruptedException e) {
                if (log.isErrorEnabled()) {
                    log.error("rearchAndPackagingSnAndExpDate-Thread-InterruptedException！", e);
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("WMSDataImportManager packagingSnAndExpDate ...End");
        }
    }

    /**
     * 封装sn和过期时间数据--子线程任务
     */
    @Override
    public void packagingSnAndExpDate(SnAndValidDateSkuFlow snAndValidDateSkuFlow, List<SnAndValidDateSkuFlow> newList) {
        // sn+非效期
        if (snAndValidDateSkuFlow.getIsSn().booleanValue() && !org.apache.commons.lang3.StringUtils.equals(InboundStoreMode.SHELF_MANAGEMENT.getValue() + "", snAndValidDateSkuFlow.getStoreMode())) {
            Long totaLQty = snAndValidDateSkuFlow.getQty();
            List<String> sns = skuSnLogDao.findSnFromSnLog(snAndValidDateSkuFlow.getStaId(), snAndValidDateSkuFlow.getSkuId(), new SingleColumnRowMapper<String>(String.class));
            if (sns != null && !sns.isEmpty()) {
                // staLine中数量和snLog表中数量一致
                if (Long.parseLong(sns.size() + "") == totaLQty.longValue()) {
                    for (String ss : sns) {
                        SnAndValidDateSkuFlow bean = new SnAndValidDateSkuFlow();
                        BeanUtils.copyProperties(snAndValidDateSkuFlow, bean, new String[] {"sn", "qty"});
                        bean.setSn(ss);
                        bean.setQty(1l);
                        newList.add(bean);
                    }
                } else {
                    // 部分sn商品的库存sn号可能未成功写入snLog表
                    for (String ss : sns) {
                        SnAndValidDateSkuFlow bean = new SnAndValidDateSkuFlow();
                        BeanUtils.copyProperties(snAndValidDateSkuFlow, bean, new String[] {"sn", "qty"});
                        bean.setSn(ss);
                        bean.setQty(1l);
                        newList.add(bean);
                    }
                    // 剩余snlog中无sn库存流水数量
                    SnAndValidDateSkuFlow bean = new SnAndValidDateSkuFlow();
                    BeanUtils.copyProperties(snAndValidDateSkuFlow, bean, new String[] {"sn", "qty"});
                    bean.setQty(totaLQty - sns.size());
                    newList.add(bean);
                }
            } else {
                newList.add(snAndValidDateSkuFlow);
            }
        }
        // 非sn+效期
        if (!snAndValidDateSkuFlow.getIsSn().booleanValue() && org.apache.commons.lang3.StringUtils.equals(InboundStoreMode.SHELF_MANAGEMENT.getValue() + "", snAndValidDateSkuFlow.getStoreMode())) {
            // 出库总数量
            Long totaLQty = snAndValidDateSkuFlow.getQty();
            // 日志中记录总数量
            Long totalLogQty = 0l;
            List<String> logQtyAndExpDate = stockTransTxLogDao.findExpDateFromStockTransTxLog(snAndValidDateSkuFlow.getStaId(), snAndValidDateSkuFlow.getSkuId(), new SingleColumnRowMapper<String>(String.class));
            // 合并效期数据
            logQtyAndExpDate = mergeQtyAndExpDateDate(logQtyAndExpDate);
            if (logQtyAndExpDate != null && !logQtyAndExpDate.isEmpty()) {
                for (String ss : logQtyAndExpDate) {
                    String[] strs = StringUtils.split(ss, ",");
                    totalLogQty = +Long.parseLong(strs[0]);
                }
                // staLine中数量和stLog表中数量一致
                if (totalLogQty.longValue() == totaLQty.longValue()) {
                    for (String ss : logQtyAndExpDate) {
                        String[] strs = StringUtils.split(ss, ",");
                        Long qty = Long.parseLong(strs[0]);
                        String expDate = strs[1];
                        SnAndValidDateSkuFlow bean = new SnAndValidDateSkuFlow();
                        BeanUtils.copyProperties(snAndValidDateSkuFlow, bean, new String[] {"expDate", "qty"});
                        bean.setQty(qty);
                        bean.setExpDate(expDate);
                        newList.add(bean);
                    }
                } else {
                    // 部分效期商品的库存过期时间可能未成功写入stLog表
                    for (String ss : logQtyAndExpDate) {
                        String[] strs = StringUtils.split(ss, ",");
                        Long qty = Long.parseLong(strs[0]);
                        String expDate = strs[1];
                        SnAndValidDateSkuFlow bean = new SnAndValidDateSkuFlow();
                        BeanUtils.copyProperties(snAndValidDateSkuFlow, bean, new String[] {"expDate", "qty"});
                        bean.setQty(qty);
                        bean.setExpDate(expDate);
                        newList.add(bean);
                    }
                    // 剩余snlog中无sn库存流水数量
                    SnAndValidDateSkuFlow bean = new SnAndValidDateSkuFlow();
                    BeanUtils.copyProperties(snAndValidDateSkuFlow, bean, new String[] {"expDate", "qty"});
                    bean.setQty(totaLQty - totalLogQty);
                    newList.add(bean);
                }
            } else {
                newList.add(snAndValidDateSkuFlow);
            }
        }
        // 同为sn+效期
        if (snAndValidDateSkuFlow.getIsSn().booleanValue() && org.apache.commons.lang3.StringUtils.equals(InboundStoreMode.SHELF_MANAGEMENT.getValue() + "", snAndValidDateSkuFlow.getStoreMode())) {
            List<SnAndValidDateSkuFlow> tempList = new ArrayList<SnAndValidDateSkuFlow>();
            // 第一步，先封装效期时间
            // 出库总数量
            Long totaLQty = snAndValidDateSkuFlow.getQty();
            // 日志中记录总数量
            Long totalLogQty = 0l;
            List<String> logQtyAndExpDate = stockTransTxLogDao.findExpDateFromStockTransTxLog(snAndValidDateSkuFlow.getStaId(), snAndValidDateSkuFlow.getSkuId(), new SingleColumnRowMapper<String>(String.class));
            // 合并效期数据
            logQtyAndExpDate = mergeQtyAndExpDateDate(logQtyAndExpDate);
            if (logQtyAndExpDate != null && !logQtyAndExpDate.isEmpty()) {
                for (String ss : logQtyAndExpDate) {
                    String[] strs = StringUtils.split(ss, ",");
                    totalLogQty = +Long.parseLong(strs[0]);
                }
                // staLine中数量和stLog表中数量一致
                if (totalLogQty.longValue() == totaLQty.longValue()) {
                    for (String ss : logQtyAndExpDate) {
                        String[] strs = StringUtils.split(ss, ",");
                        Long qty = Long.parseLong(strs[0]);
                        String expDate = strs[1];
                        SnAndValidDateSkuFlow bean = new SnAndValidDateSkuFlow();
                        BeanUtils.copyProperties(snAndValidDateSkuFlow, bean, new String[] {"expDate", "qty"});
                        bean.setQty(qty);
                        bean.setExpDate(expDate);
                        tempList.add(bean);
                    }
                } else {
                    // 部分效期商品的库存过期时间可能未成功写入stLog表
                    for (String ss : logQtyAndExpDate) {
                        String[] strs = StringUtils.split(ss, ",");
                        Long qty = Long.parseLong(strs[0]);
                        String expDate = strs[1];
                        SnAndValidDateSkuFlow bean = new SnAndValidDateSkuFlow();
                        BeanUtils.copyProperties(snAndValidDateSkuFlow, bean, new String[] {"expDate", "qty"});
                        bean.setQty(qty);
                        bean.setExpDate(expDate);
                        tempList.add(bean);
                    }
                    // 剩余snlog中无sn库存流水数量
                    SnAndValidDateSkuFlow bean = new SnAndValidDateSkuFlow();
                    BeanUtils.copyProperties(snAndValidDateSkuFlow, bean, new String[] {"expDate", "qty"});
                    bean.setQty(totaLQty - totalLogQty);
                    tempList.add(bean);
                }
            } else {
                tempList.add(snAndValidDateSkuFlow);
            }
            // 第二步，封装sn号
            List<String> sns = skuSnLogDao.findSnFromSnLog(snAndValidDateSkuFlow.getStaId(), snAndValidDateSkuFlow.getSkuId(), new SingleColumnRowMapper<String>(String.class));
            if (sns != null && !sns.isEmpty()) {
                for (SnAndValidDateSkuFlow ss : tempList) {
                    // staLine中数量和snLog表以及stLog中数量一致(sn商品效期一致)
                    if (Long.parseLong(sns.size() + "") == ss.getQty().longValue() && tempList.size() == 1) {
                        for (String ss2 : sns) {
                            SnAndValidDateSkuFlow bean = new SnAndValidDateSkuFlow();
                            BeanUtils.copyProperties(ss, bean, new String[] {"sn", "qty"});
                            bean.setSn(ss2);
                            bean.setQty(1l);
                            newList.add(bean);
                        }
                    } else {
                        // 部分sn商品的效期时间不同，需要进行拆分
                        Long qtyTemp = 0l;// 循环中已经拆分的sn总数
                        for (int i = 0; i < sns.size(); i++) {
                            SnAndValidDateSkuFlow bean = new SnAndValidDateSkuFlow();
                            BeanUtils.copyProperties(ss, bean, new String[] {"sn", "qty"});
                            bean.setSn(sns.get(i));
                            bean.setQty(1l);
                            newList.add(bean);
                            // 计算本轮消耗了多少sn
                            qtyTemp++;
                            // 移除此次listTemp中的sn
                            sns.remove(i);
                            i--;
                            // 分配达到数量或者listTemp中的sn消耗殆尽
                            if (qtyTemp.longValue() == ss.getQty().longValue() || sns.size() == 0) break;
                        }
                        // 剩余效期商品无sn记录
                        if (ss.getQty().longValue() - qtyTemp > 0) {
                            SnAndValidDateSkuFlow bean = new SnAndValidDateSkuFlow();
                            BeanUtils.copyProperties(snAndValidDateSkuFlow, bean, new String[] {"sn", "qty"});
                            bean.setQty(ss.getQty().longValue() - qtyTemp);
                            newList.add(bean);
                        }
                    }
                }
            } else {
                newList.add(snAndValidDateSkuFlow);
            }
        }
    }

    /**
     * 合并效期数据
     * 
     * @param logQtyAndExpDate
     * @return
     */
    private List<String> mergeQtyAndExpDateDate(List<String> logQtyAndExpDate) {
        List<String> list = new ArrayList<String>();
        Map<String, Long> result = new HashMap<String, Long>();
        Map<String, Long> outBound = new HashMap<String, Long>();// 销售出
        Map<String, Long> rtnInBound = new HashMap<String, Long>();// 退货入
        Map<String, Long> rtnOutBound = new HashMap<String, Long>();// 换货出
        if (logQtyAndExpDate != null && !logQtyAndExpDate.isEmpty()) {
            for (String ss : logQtyAndExpDate) {
                String[] strs = StringUtils.split(ss, ",");
                Long qty = Long.parseLong(strs[0]);
                String expDate = strs[1];
                int type = Integer.parseInt(strs[2]);
                switch (type) {
                    case 0:// 销售出
                        outBound.put(expDate, qty);
                        break;
                    case 1:// 换货出
                        rtnOutBound.put(expDate, qty);
                        break;
                    case -1:// 退货入
                        rtnInBound.put(expDate, qty);
                        break;
                    default:
                        break;
                }
            }
            // 统计退货入总量
            Long totalQty = 0l;
            for (Map.Entry<String, Long> m : rtnInBound.entrySet()) {
                totalQty = +m.getValue();
            }
            // 销售出数据剔除退货入数据
            Long leftQty = 0l;
            for (Map.Entry<String, Long> m : outBound.entrySet()) {
                leftQty = m.getValue() - totalQty;
                if (leftQty >= 0) {
                    outBound.put(m.getKey(), leftQty);
                    break;
                } else {
                    totalQty = leftQty;
                    continue;
                }
            }
            // 新销售出数据合并换货出数据
            for (Map.Entry<String, Long> m1 : outBound.entrySet()) {
                result.put(m1.getKey(), m1.getValue());
                for (Map.Entry<String, Long> m2 : rtnOutBound.entrySet()) {
                    result.put(m2.getKey(), m2.getValue());
                    if (org.apache.commons.lang3.StringUtils.equals(m1.getKey(), m2.getKey())) {
                        result.put(m1.getKey(), m1.getValue() + m2.getValue());
                    }
                }
            }
            for (Map.Entry<String, Long> m : result.entrySet()) {
                if (m.getValue().longValue() == 0l) {
                    result.remove(m.getKey());
                }
                list.add(m.getValue() + "," + m.getKey());
            }

        }
        return list;
    }

    /**
     * 尚未完成品牌入库指令数据文件生成
     */
    @Override
    public void generateBrandUnFinishedInstructions(String postshopInput, List<ShopDataSource> addList) {
        // 店铺集合
        if (StringUtils.isBlank(postshopInput)) {
            throw new BusinessException(ErrorCode.TRANSPORTATOR_REF_SHOP_IS_NULL);
        }
        String[] owners = null;
        if (!StringUtils.isBlank(postshopInput)) {
            owners = StringUtils.split(postshopInput, ";");
        }
        // dataSource集合
        Map<String, String> sources = new HashMap<String, String>();
        if (addList != null && !addList.isEmpty()) {
            for (ShopDataSource shopDataSource : addList) {
                if (!StringUtils.isBlank(shopDataSource.getDataSourceShopCode())) {
                    sources.put(shopDataSource.getDataSourceShopCode(), shopDataSource.getDatasource());
                } else if (StringUtils.isBlank(shopDataSource.getDataSourceShopCode()) && !StringUtils.isBlank(shopDataSource.getDataSourceShopName())) {
                    BiChannel shop = companyShopDao.getByName(shopDataSource.getDataSourceShopName());
                    if (shop != null) {
                        sources.put(shop.getCode(), shopDataSource.getDatasource());
                    }
                }
            }
        }
        // 创建目录
        List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.WMS_SHARE_ROOT_DIR);
        String rootShareDir = null;
        if (configs != null && !configs.isEmpty()) {
            rootShareDir = configs.get(0).getParaValue();
        }
        String aimFile = createDir("brandUnFinishedInstructions", rootShareDir);
        String tempDir = rootShareDir + File.separator + "dataImport" + File.separator + "temporaryFile";
        if (log.isInfoEnabled()) {
            log.info("WMSDataImportManager generateBrandUnFinishedInstructions ...Enter");
        }
        // 设置文件生成标识
        setFileGenerateFlag(Constants.WMS_DATA_IMPORT_GROUP, Constants.WMS_DATA_BRAND_UNFINISHED_INS, 0);
        Integer num = null;
        try {
            num = chooseOptionManager.getSystemThreadValueByKey(Constants.IMPORT_BRAND_UNFINISHED_INS);
        } catch (Exception e) {
            log.error("获取配置线程数失败！", e);
        }
        if (num == null || num == 0) {
            num = 5;
        }
        // 开启多线程，一店铺一个线程
        ExecutorService pool = Executors.newFixedThreadPool(num);
        int threadId = 1;
        if (owners != null) {
            for (String owner : owners) {
                String threadName = "brandUnFinishedInstructions:" + owner;
                WMSDataImportThread t = new WMSDataImportThread(threadName);
                t.setId(Long.parseLong("" + threadId++));
                t.setOwner(owner);
                t.setPath(tempDir);
                t.setDataSource(sources.get(owner));
                t.setName("brandUnFinishedInstructions");
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
                    log.error("rearchAndPackagingSnAndExpDate-Thread-InterruptedException！", e);
                }
            }
        }
        if (log.isInfoEnabled()) {
            log.info("WMSDataImportManager generateBrandUnFinishedInstructions ...End");
        }
        // 文件合并
        if (owners != null) {
            for (String owner : owners) {
                try {
                    File fileSource = new File(tempDir + File.separator + owner + "[2].txt");
                    if (fileSource.exists()) {
                        com.jumbo.util.FileUtils fileUtil = new com.jumbo.util.FileUtils();
                        fileUtil.copyWithChannels(fileSource, new File(aimFile), true);
                        // FileUtils.copyFile(fileSource, new File(aimFile), true);
                        fileSource.delete();
                    }
                } catch (Exception e) {
                    // log.error("合并文件异常！");
                    if (log.isErrorEnabled()) {
                        log.error("合并文件异常！", e);
                    }
                }
            }
        }
        // 设置成功标识
        setFileGenerateFlag(Constants.WMS_DATA_IMPORT_GROUP, Constants.WMS_DATA_BRAND_UNFINISHED_INS, 1);
    }

    /**
     * 尚未完成品牌入库指令数据文件生成--线程任务
     * 
     */
    @Override
    public Boolean packagingBrandUnFinishedIns(String owner, String path, String dataSource) {
        File file = null;
        if (owner != null) {
            file = new File(path + File.separator + owner + "[2].txt");
        }
        FileWriter fileWriter = null;
        try {
            file.createNewFile();
            int pageSize = 1000;
            Pagination<StockTransApplication> pagination = staDao.findAllUnFinishedBrandIns(0, pageSize, owner, null);
            int totalPages = pagination.getTotalPages();
            // 未查询到数据直接返回
            if (pagination.getCount() == 0l) {
                log.info("Pagination<StockTransApplication> size:0 " + owner);
                return true;
            }
            fileWriter = new FileWriter(file, true);
            for (int i = 0; i < totalPages; i++) {
                // 不重复查询第一页
                if (i == 0) {
                    List<StockTransApplication> list = pagination.getItems();
                    // 重新组装数据
                    List<WmsInBound> data = buitWmsInBound(list, dataSource);
                    if (list.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (int j = 0; j < list.size(); j++) {
                            String json = net.sf.json.JSONObject.fromObject(data.get(j)).toString();
                            sb.append(json + "\r\n");
                        }
                        fileWriter.write(sb.toString());
                        fileWriter.flush();
                    }
                    continue;
                }
                // 剩余页数据
                Pagination<StockTransApplication> temp = staDao.findAllUnFinishedBrandIns(i * pageSize, pageSize, owner, null);
                List<StockTransApplication> list = temp.getItems();
                // 重新组装数据
                List<WmsInBound> data = buitWmsInBound(list, dataSource);
                if (list.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int j = 0; j < list.size(); j++) {
                        String json = net.sf.json.JSONObject.fromObject(data.get(j)).toString();
                        sb.append(json + "\r\n");
                    }
                    fileWriter.write(sb.toString());
                    fileWriter.flush();
                }
            }
            return true;
        } catch (Exception e) {
            log.error("文件生成失败,owner:" + owner, e);
            return false;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error("fileWriter.flush IOException！", e);
                    }
                    return false;
                }
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    if (log.isErrorEnabled()) {
                        log.error("fileWriter.close IOException！", e);
                    }
                    return false;
                }
            }
        }
    }

    /**
     * 组装品牌数据未完成指令数据
     * 
     * @param list
     * @return
     */
    private List<WmsInBound> buitWmsInBound(List<StockTransApplication> list, String dataSource) {
        List<WmsInBound> inBounds = new ArrayList<WmsInBound>();
        if (list != null && !list.isEmpty()) {
            for (StockTransApplication sta : list) {
                // 头信息
                WmsInBound inbound = new WmsInBound();
                inbound.setUuid(sta.getId() + "");
                inbound.setExtPoCode(sta.getRefSlipCode());
                inbound.setExtCode(sta.getSlipCode1());
                inbound.setStoreCode(sta.getOwner());
                BiChannel channel = companyShopDao.getByCode(sta.getOwner());
                if (channel != null && channel.getCustomer() != null) {
                    Customer customer = customerDao.getByPrimaryKey(channel.getCustomer().getId());
                    inbound.setCustomerCode(customer.getCode());
                }
                inbound.setFromLocation(sta.getFromLocation());
                inbound.setToLocation(sta.getToLocation());
                OperationUnit ou = ouDao.getByPrimaryKey(sta.getMainWarehouse().getId());
                if (ou != null) {
                    inbound.setWhCode(ou.getCode());
                }
                inbound.setPoType(sta.getType().getValue() + "");
                inbound.setExtPoType(sta.getRefSlipType() == null ? "" : sta.getRefSlipType().getValue() + "");
                inbound.setIsIqc(false);
                inbound.setExtMemo(sta.getExtMemo());
                inbound.setDataSource(dataSource);
                inBounds.add(inbound);
                // 明细信息
                List<WmsInBoundLine> wmsInBoundLines = new ArrayList<WmsInBoundLine>();
                List<StaLine> lines = staLineDao.findByStaId(sta.getId());
                if (lines != null && !lines.isEmpty()) {
                    for (StaLine staLine : lines) {
                        WmsInBoundLine l = new WmsInBoundLine();
                        Sku sku = skuDao.getByPrimaryKey(staLine.getSku().getId());
                        if (sku != null) {
                            l.setUpc(sku.getExtensionCode2());
                            l.setQty(Double.parseDouble(staLine.getQuantity() + ""));
                            l.setLineSeq(staLine.getId() + "");
                            InventoryStatus invStatus = inventoryStatusDao.getByPrimaryKey(staLine.getInvStatus().getId());
                            if (invStatus != null) {
                                l.setInvStatus(invStatus.getName());
                            }
                            l.setCountryOfOrigin(sku.getCountryOfOrigin());
                        } else {
                            throw new BusinessException(ErrorCode.SKU_NOT_EXIST, staLine.getId() + "");
                        }
                        wmsInBoundLines.add(l);
                    }
                }
                inbound.setWmsInBoundLines(wmsInBoundLines);
                // 物流相关信息
                WmsInBoundTransportMgmt transport = new WmsInBoundTransportMgmt();
                StaDeliveryInfo deliveryInfo = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
                if (deliveryInfo != null) {
                    transport.setTransportServiceProvider(deliveryInfo.getLpCode());
                    transport.setTrackingNumber(deliveryInfo.getTrackingNo());
                }
                inbound.setWmsInBoundTransportMgmt(transport);
            }
        }
        return inBounds;
    }

    /**
     * 文件生成标识
     * 
     * @param wmsDataImportGroup
     * @param dataType
     * @param i
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void setFileGenerateFlag(String wmsDataImportGroup, String dataType, int i) {
        ChooseOption flag = chooseOptionDao.findByCategoryCodeAndKey(wmsDataImportGroup, dataType);
        if (flag != null) {
            flag.setOptionValue("" + i);
            chooseOptionDao.save(flag);
            chooseOptionDao.flush();
        }
    }

    /**
     * 共享目录下建立相关文件目录
     * 
     * @param fileName
     * @return
     */
    public String createDir(String fileName, String rootShareDir) {
        // 拼接示范：/home/vmuser/wms/dataImport/底层目录
        File dir = new File(rootShareDir + File.separator + "dataImport" + File.separator + fileName);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs(); // 创建文件备份目录
        }
        String parentDir = dir.getParent();
        File tempDir = new File(parentDir + File.separator + "temporaryFile");
        if (!tempDir.exists() && !tempDir.isDirectory()) {
            tempDir.mkdirs(); // 创建临时目录
        }
        String fileFullName = File.separator + fileName + ".txt";// 文件名
        String downloadFileName = dir + fileFullName;// 最终文件
        if (new File(downloadFileName).exists()) {
            new File(downloadFileName).delete();
        }
        return downloadFileName;
    }

    /**
     * 备份的下载文件
     */
    @Override
    public String backupFile(String fileName) {
        if (!StringUtils.isBlank(fileName)) {
            try {
                List<CommonConfig> configs = commonConfigDao.findCommonConfigListByParaGroup(Constants.WMS_SHARE_ROOT_DIR);
                String rootShareDir = null;
                if (configs != null && !configs.isEmpty()) {
                    rootShareDir = configs.get(0).getParaValue();
                }
                // 备份
                String dir = rootShareDir + File.separator + "dataImport";
                File fileSource = new File(dir + File.separator + fileName + File.separator + fileName + Constants.FILE_EXTENSION_TXT);
                String backPath = dir + File.separator + "backup" + File.separator + fileName;
                File backDir = new File(backPath);
                if (!backDir.exists() && !backDir.isDirectory()) {
                    backDir.mkdirs();// 创建备份目录
                }
                String date = new SimpleDateFormat("yyyy-MM-dd-HHmmss").format(new Date());
                File aimFile = new File(backPath + File.separator + fileName + date + Constants.FILE_EXTENSION_TXT);
                aimFile.createNewFile();
                if (fileSource.exists()) {
                    FileUtils.copyFile(fileSource, aimFile, true);
                    log.info("备份文件成功！filePath=" + fileName);
                }
                return dir + File.separator + fileName + File.separator + fileName + Constants.FILE_EXTENSION_TXT;
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return null;
    }
}
