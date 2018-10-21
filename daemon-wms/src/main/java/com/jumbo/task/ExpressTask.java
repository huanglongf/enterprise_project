package com.jumbo.task;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.bizhub.manager.bps.Wms2BpsManager;
import com.jumbo.webservice.express.ExpressGetMailNosRequest;
import com.jumbo.webservice.express.ExpressGetMailNosResponse;
import com.jumbo.webservice.express.ExpressGetMailNosResponse.TransNo;
import com.jumbo.webservice.express.ExpressOrderConfirmResponse;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.task.TransInfoManager;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.trans.TransInfo;
import com.jumbo.wms.model.warehouse.ExpressConfirmOrderQueue;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;
import com.jumbo.wms.newInventoryOccupy.ExpressGetNoThread;

public class ExpressTask {

    protected static final Logger log = LoggerFactory.getLogger(ExpressTask.class);

    @Autowired
    private TransInfoManager transInfoManager;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private Wms2BpsManager wms2BpsManager;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private ThreadPoolService threadPoolService;

    /**
     * 物流商获取运单号
     */
    public void exeGetMailNos() {
        List<TransInfo> transList = transInfoManager.getAvailableTransInfo();
        for (TransInfo transInfo : transList) {
            // 是否向物流商获取运单号
            if (transInfo.getIsOltransno() != null && transInfo.getIsOltransno() && !transInfo.getLpcode().contains("YL_")) {
                log.info(">>>get {} transno begin<<<", transInfo.getLpcode());
                long count = transInfoManager.getTranNoNumberByLpCodeAndRegionCode(transInfo.getLpcode(), transInfo.getRegionCode());
                if (count < 5000) {
                    for (int i = 0; i < 100; i++) {
                        try {
                            int getMailsnoCount = 200; // 获取运单数量
                            ExpressGetMailNosRequest req = new ExpressGetMailNosRequest();
                            req.setAccount(transInfo.getAccount());
                            req.setCheckword(transInfo.getPassword());
                            req.setBatchId(sequenceManager.getTransNoCode(transInfo.getLpcode()));
                            req.setQty(getMailsnoCount);
                            req.setRegionCode(transInfo.getRegionCode());
                            req.setLpCode(transInfo.getLpcode());
                            String json = transInfoManager.getObjectToJson(req);
                            log.info("GetMailNos send:" + json);
                            // 调用hub接口
                            String resJson = wms2BpsManager.getMailNos(transInfo.getSystemKey(), json);
                            log.info("GetMailNos return:" + resJson);
                            if (resJson != null) {
                                ExpressGetMailNosResponse res = transInfoManager.getJsonToObject(resJson, ExpressGetMailNosResponse.class);
                                if (res != null && res.getIsSuccess()) {
                                    if (req.getBatchId().equals(res.getBatchId())) {
                                        List<TransNo> mailnos = res.getMailnos();
                                        if (mailnos != null) {
                                            for (TransNo transNo : mailnos) {
                                                WhTransProvideNo bean = new WhTransProvideNo();
                                                bean.setCreateTime(new Date());
                                                bean.setLpcode(transInfo.getLpcode());
                                                bean.setTransno(transNo.getMailno());
                                                bean.setRegionCode(transInfo.getRegionCode());
                                                transInfoManager.saveMailNos(bean);
                                            }
                                        }
                                    }
                                } else {
                                    log.error(transInfo.getLpcode() + " getMailsNo interface Fail:{}", res.getMsg());
                                }
                            } else {
                                log.error(transInfo.getLpcode() + " getMailsNo interface Fail");
                            }
                        } catch (Exception e) {
                            log.error(transInfo.getLpcode() + " getMailsNo interface Error:", e);
                        }
                    }
                }
                log.info(">>>get {} transno end<<<", transInfo.getLpcode());
            }
        }
    }

    /**
     * 工作单设置物流商的运单号
     */
    public void exeSetMailNos() {
        log.info("set trans no start");
        Boolean flag = true;
//        ChooseOption co = chooseOptionManager.findChooseOptionByCategoryCodeAndKey(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD, ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_GET_EXPRESS_TRANS_NO);
//        Integer threadPoolQty = Integer.parseInt(co.getOptionValue());
        // 根据参数创建线程池
        while (flag) {
//            ExecutorService pool = Executors.newFixedThreadPool(threadPoolQty);
//            ThreadPoolExecutor tx = (ThreadPoolExecutor) pool;
            List<Long> staList = transInfoManager.findStaByTransInfoLpCode();
            if (staList.size() < 10000) {
                flag = false;
            }
            for (Long id : staList) {
                try {
                    Thread t = new ExpressGetNoThread(id);
//                    tx.execute(t);
                    threadPoolService.executeThread(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_GET_EXPRESS_TRANS_NO, t);
                } catch (Exception e) {
                    log.error("", e);
                }
//                while (true) {
//                    long todoTotal = tx.getTaskCount() - tx.getCompletedTaskCount();
//                    if (todoTotal >= 100) {
//                        try {
//                            Thread.sleep(500L);
//                            log.error("get trans no thread todoTotal is " + todoTotal);
//                        } catch (InterruptedException e) {
//                            log.error("sleep error");
//                        }
//                    } else {
//                        break;
//                    }
//                }
            }
//            tx.shutdown();
//            boolean isFinish = false;
//            while (!isFinish) {
//                isFinish = pool.isTerminated();
//                try {
//                    Thread.sleep(1000L);
//                } catch (InterruptedException e) {
//                    log.error("InterruptedException majorThread error");
//                }
//            }
            threadPoolService.waitToFinish(ChooseOption.CATEGORY_CODE_SYSTEM_THREAD_POOL_GET_EXPRESS_TRANS_NO);
        }
        log.info("set trans no end");
    }

    /**
     * 出库确认
     */
    public void exeConfirmOrder() {
        log.info("exeExpressConfirmOrderQueue start-------------------");
        Boolean flag = true;
        while (flag) {
            List<Long> qIds = transInfoManager.findExtOrderIdSeo();
            if (qIds.size() < 5000) {
                flag = false;
            }
            for (Long qId : qIds) {
                try {
                    ExpressConfirmOrderQueue q = transInfoManager.getExpressConfirmOrder(qId);
                    String json = transInfoManager.getConfirmOrderJson(q);
                    log.info("ConfirmOrder send:" + json);
                    // 调用hub接口
                    String systemKey = "";
                    if (q.getLpCode().contains("YL")) {
                        systemKey = q.getLpCode().substring(0, 2);
                        Boolean isSuccess = transInfoManager.orderConfrimToLogistics(q);
                        if (isSuccess) {
                            transInfoManager.saveLogAndDeleteQueue(q);
                        } else {
                            transInfoManager.addErrorCount(qId);
                        }
                        // 银联接口，调用快递服务by fxl TODO
                    } else {
                        systemKey = q.getLpCode();
                        String resJson = wms2BpsManager.orderConfirm(systemKey, json);
                        log.info("ConfirmOrder return:" + resJson);
                        ExpressOrderConfirmResponse res = transInfoManager.getJsonToObject(resJson, ExpressOrderConfirmResponse.class);
                        if (res == null) {
                            throw new BusinessException(ErrorCode.EXPRESS_INTERFACE_ERROR, q.getStaCode());
                        } else {
                            if (res.getIsSuccess()) {
                                transInfoManager.saveLogAndDeleteQueue(q);
                            } else {
                                transInfoManager.addErrorCount(qId);
                            }
                        }
                    }
                } catch (Exception e) {
                    transInfoManager.addErrorCount(qId);
                    log.error("exeExpressConfirmOrderQueue error " + e.toString());
                }
            }
        }
        log.info("exeExpressConfirmOrderQueue end-------------------");
    }
}
