package com.jumbo.wms.manager.task.ems;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.TransEmsInfoDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.webservice.ems.EmsServiceClient;
import com.jumbo.webservice.ems.EmsServiceClient2;
import com.jumbo.webservice.ems.command.EmsOrderBillNo;
import com.jumbo.webservice.ems.command.EmsOrderBillNoResponse;
import com.jumbo.webservice.ems.command.EmsOrderBillNoResponseAssignId;
import com.jumbo.webservice.ems.command.EmsOrderBillNoResponseAssignIds;
import com.jumbo.webservice.ems.command.New.EmsSystemParam;
import com.jumbo.webservice.ems.command.New.NewEmsOrderBillNoResponse;
import com.jumbo.webservice.ems.command.New.NewEmsOrderPrivilegeResponse;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.EmsTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.model.baseinfo.TransEmsInfo;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.system.ChooseOption;

@Service("emsTask")
public class EmsTaskImpl extends BaseManagerImpl implements EmsTask {

    /**
     * 
     */
    private static final long serialVersionUID = 3842123753733120307L;
    @Autowired
    private EmsTaskManager emsTaskManager;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private TransEmsInfoDao transEmsInfoDao;
    /*
     * @Autowired private TransEmsInfoNewDao transEmsInfoNewDao;
     */
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private TransOlManager transOlManager;
    @Autowired
    private ChooseOptionDao chooseOptionDao;

    /**
     * 根据authorization来刷新flashToken 或根据flashToken来刷authorization 最终获取authorization
     * 
     * @throws ParseException
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    @Override
    public void EmsAuthorizationOrflashToken() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<TransEmsInfo> ls = transEmsInfoDao.queryTransEmsInfo(1, new BeanPropertyRowMapperExt<TransEmsInfo>(TransEmsInfo.class));
        for (TransEmsInfo transEmsInfo : ls) {
            Map<String, String> params = new HashMap<String, String>();
            EmsSystemParam en = EmsServiceClient2.initEmsSystemParam(null, null, "V3.01", "ems.permission.user.permit.refresh.token", null, "json", transEmsInfo.getKey(), "UTF-8", transEmsInfo.getAuthorization(), transEmsInfo.getSecret());
            NewEmsOrderPrivilegeResponse re = EmsServiceClient2.getBillPrivilege("03", en, params, EMS_NEW_URL);
            if ("T".equals(re.getSuccess())) {
                if (transEmsInfo.getAccountName().equals(re.getCustomerCode())) {
                    Date flashTokenExpireTime = sdf.parse(re.getFlashTokenExpireTime());
                    transEmsInfoDao.updateTransEmsInfo(re.getCustomerCode(), re.getFlashToken(), flashTokenExpireTime);
                } else {
                    log.error(transEmsInfo.getAccountName() + "不等");
                }
            }
        }
        //
        List<TransEmsInfo> ls2 = transEmsInfoDao.queryTransEmsInfo2(1, new BeanPropertyRowMapperExt<TransEmsInfo>(TransEmsInfo.class));
        for (TransEmsInfo transEmsInfo : ls2) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("flashToken", transEmsInfo.getFlashToken());
            EmsSystemParam en = EmsServiceClient2.initEmsSystemParam(null, null, "V3.01", "ems.permission.user.permit.refresh.authorization", null, "json", transEmsInfo.getKey(), "UTF-8", null, transEmsInfo.getSecret());
            NewEmsOrderPrivilegeResponse re = EmsServiceClient2.getBillPrivilege("02", en, params, EMS_NEW_URL);
            if ("T".equals(re.getSuccess())) {
                if (transEmsInfo.getAccountName().equals(re.getCustomerCode())) {
                    Date expireTime = sdf.parse(re.getExpireTime());
                    transEmsInfoDao.updateTransEmsInfo2(re.getCustomerCode(), re.getAuthorization(), expireTime);
                } else {
                    log.error(transEmsInfo.getAccountName() + "不等2");
                }
            }
        }
    }

    /**
     * 获取EMS 单号
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void toEmsGetOrderCode() {
        Boolean bEms = true;// 用老的
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(EmsServiceClient2.EMS_NEW_SWITCH_CODE, EmsServiceClient2.EMS_NEW_SWITCH_KEY);
        if (op != null && op.getOptionValue() != null) {
            bEms = false;// 用新的
        }
        if (bEms) {// 老的
            // 关于 EMS COD 快递单号获取
            getEmsOrderBillNoResponse(true);
            // 关于 EMS 非COD快递单号获取
            getEmsOrderBillNoResponse(false);
        } else {// 新的
            // 关于 EMS COD 快递单号获取
            getEmsOrderBillNoResponse2(true);
            // 关于 EMS 非COD快递单号获取
            getEmsOrderBillNoResponse2(false);
        }



    }

    /**
     * 设置EMS快点单号
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void setEmsStaTrasnNo() {
        Long count = transProvideNoDao.getTranNoNumberByLpCode(Transportator.EMS, null, new SingleColumnRowMapper<Long>(Long.class));
        // 快递单号如果没有了 就不执行此代码
        if (count > 0) {
            List<Long> idList = warehouseDao.getAllEMSWarehouse(new SingleColumnRowMapper<Long>(Long.class));
            for (Long id : idList) {
                try {
                    List<String> lpList = new ArrayList<String>();
                    lpList.add(Transportator.EMS);
                    // lpList.add(Transportator.EMS_COD);
                    List<Long> staList = staDao.findStaByOuIdAndStatus(id, lpList, new SingleColumnRowMapper<Long>(Long.class));
                    for (Long staId : staList) {
                        // EMS下单
                        try {
                            // 设置EMS单据号
                            transOlManager.matchingTransNo(staId);
                        } catch (Exception e) {
                            log.error("", e);
                        }
                    }
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * EMS 出库通知
     */
    // @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    // public void exeEmsConfirmOrderQueue() {
    // List<EMSConfirmOrderQueue> qs =
    // emsConfirmOrderQueueDao.findExtOrder(Constants.SF_QUEUE_TYP_COUNT);
    // for (EMSConfirmOrderQueue q : qs) {
    // try {
    // emsTaskManager.exeEmsConfirmOrder(q);
    // } catch (Exception e) {
    // log.error("", e);
    // }
    // }
    // }

    /**
     * 获取快递单号
     * 
     * @param isCod
     */
    private void getEmsOrderBillNoResponse(boolean isCod) {
        List<TransEmsInfo> lists = transEmsInfoDao.findByAllCmp(isCod, 0);

        Integer isCodNum = null;
        Integer isNotCodNum = null;

        try {
            isCodNum = Integer.parseInt(chooseOptionDao.findAllOptionListByOptionKey(Constants.EMS_ISCOD_NUM, Constants.EMS_ISCOD_NUM, new SingleColumnRowMapper<String>(String.class)));
            isNotCodNum = Integer.parseInt(chooseOptionDao.findAllOptionListByOptionKey(Constants.EMS_ISNOTCOD_NUM, Constants.EMS_ISNOTCOD_NUM, new SingleColumnRowMapper<String>(String.class)));
        } catch (Exception e) {
            isCodNum = 5000;
            isNotCodNum = 10000;
        }

        for (TransEmsInfo list : lists) {
            long count = transProvideNoDao.getTranNoNumberByLpCodeAndAccount(Transportator.EMS, isCod, list.getAccount(), new SingleColumnRowMapper<Long>(Long.class));
            if ((isCod && count <= isCodNum) || (!isCod && count <= isNotCodNum)) {
                // 获取100次 每次获取100单(接口一次最多100单)
                for (int i = 0; i < 300; i++) {
                    try {
                        TransEmsInfo emsInfo = transEmsInfoDao.getByPrimaryKey(list.getId());
                        if (emsInfo == null) {
                            throw new BusinessException(ErrorCode.NO_EMS_ACCOUNT);
                        }
                        EmsOrderBillNo ems = new EmsOrderBillNo();
                        ems.setSysAccount(emsInfo.getAccount());
                        ems.setPassWord(emsInfo.getPassword());
                        if (isCod) {
                            ems.setAppKey(emsInfo.getAppKey());
                            ems.setBusinessType(EmsServiceClient.BUSINESS_TYPE_ISCOD);
                        } else {
                            ems.setBusinessType(EmsServiceClient.BUSINESS_TYPE_NORMAL);
                        }
                        ems.setBillNoAmount("100");
                        EmsOrderBillNoResponse response = EmsServiceClient.getBillNo(ems);
                        if (response.getIsSuccess()) {
                            EmsOrderBillNoResponseAssignIds assignIds = response.getAssignIds();
                            if (assignIds != null && assignIds.getAssignId() != null) {
                                for (EmsOrderBillNoResponseAssignId trsasNo : assignIds.getAssignId()) {
                                    try {
                                        emsTaskManager.saveEmsNo(trsasNo.getBillno(), isCod, emsInfo.getAccount());
                                    } catch (Exception e) {
                                        log.error("EMS TransNo Error no:" + trsasNo.getBillno() + " isCod:" + isCod);
                                    }
                                }
                            } else {
                                log.debug("EMS interface Error: transNo is null!");
                            }
                        } else {
                            log.info("EMS interface Error:" + emsInfo.getAccount() + response.getErrorDesc());
                        }
                    } catch (Exception e) {
                        log.error("EMS interface Error sys:" + e.getMessage());
                    }
                }
            }
        }
        // long count = transProvideNoDao.getTranNoNumberByLpCode(Transportator.EMS, isCod, new
        // SingleColumnRowMapper<Long>(Long.class));

    }

    @Override
    public Long getTransNoNumber() {
        return transProvideNoDao.getTranNoNumberByLpCode(Transportator.EMS, null, new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public List<Long> getAllEmsWarehouse() {
        return warehouseDao.getAllEMSWarehouse(new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public List<Long> findStaByOuIdAndStatus(Long id, List<String> lpList) {
        return staDao.findStaByOuIdAndStatus(id, lpList, new SingleColumnRowMapper<Long>(Long.class));
    }


    /**
     * 获取快递单号2
     * 
     * @param isCod
     */
    private void getEmsOrderBillNoResponse2(boolean isCod) {
        List<TransEmsInfo> lists = transEmsInfoDao.findByAllCmp(isCod, 1);
        // List<TransEmsInfoNew> lists = transEmsInfoNewDao.findByAllCmp(isCod);
        Integer isCodNum = null;
        Integer isNotCodNum = null;

        try {
            isCodNum = Integer.parseInt(chooseOptionDao.findAllOptionListByOptionKey(Constants.EMS_ISCOD_NUM, Constants.EMS_ISCOD_NUM, new SingleColumnRowMapper<String>(String.class)));
            isNotCodNum = Integer.parseInt(chooseOptionDao.findAllOptionListByOptionKey(Constants.EMS_ISNOTCOD_NUM, Constants.EMS_ISNOTCOD_NUM, new SingleColumnRowMapper<String>(String.class)));
        } catch (Exception e) {
            isCodNum = 5000;
            isNotCodNum = 10000;
        }

        for (TransEmsInfo list : lists) {
            long count = transProvideNoDao.getTranNoNumberByLpCodeAndAccount(Transportator.EMS, isCod, list.getAccount(), new SingleColumnRowMapper<Long>(Long.class));
            if ((isCod && count <= isCodNum) || (!isCod && count <= isNotCodNum)) {
                // 获取100次 每次获取100单(接口一次最多100单)
                for (int i = 0; i < 300; i++) {
                    try {
                        TransEmsInfo emsInfo = transEmsInfoDao.getByPrimaryKey(list.getId());
                        if (emsInfo == null) {
                            throw new BusinessException(ErrorCode.NO_EMS_ACCOUNT);
                        }
                        // new 获取运单号//////////////////////
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("count", "100");
                        if (isCod) {
                            params.put("bizcode", "08");// cod
                        } else {
                            params.put("bizcode", "06");// 标准
                        }
                        EmsSystemParam en = EmsServiceClient2.initEmsSystemParam(null, null, "V3.01", "ems.inland.mms.mailnum.apply", null, "json", list.getKey(), "UTF-8", list.getAuthorization(), list.getSecret());
                        NewEmsOrderBillNoResponse re = EmsServiceClient2.getBillNo("04", en, params, EMS_NEW_URL);
                        if ("T".equals(re.getSuccess())) {
                            if (re.getMailnums() != null) {
                                List<String> mailnums = re.getMailnums();
                                if (mailnums != null && mailnums.size() > 0) {
                                    for (String string : mailnums) {
                                        try {
                                            emsTaskManager.saveEmsNo(string, isCod, emsInfo.getAccount());
                                        } catch (Exception e) {
                                            log.error("EMS2_saveEmsNo_" + emsInfo.getAccount(), e);
                                        }
                                    }
                                } else {
                                    log.debug("EMS2 interface Error: mailnums is null!");
                                }
                            } else {
                                log.debug("EMS2 interface Error: getResponse is null!");
                            }

                        } else {
                            log.info("EMS2 interface Error:" + emsInfo.getAccount() + re.getErrorMsg());
                        }
                        // ///////////////end//////////////
                    } catch (Exception e) {
                        log.error("EMS2 interface Error sys", e);
                    }
                }
            }
        }
    }


}
