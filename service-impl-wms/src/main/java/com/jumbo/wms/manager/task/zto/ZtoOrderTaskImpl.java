package com.jumbo.wms.manager.task.zto;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.webservice.zto.ZtoOrderClient;
import com.jumbo.wms.daemon.ZtoOrderTask;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;
import com.jumbo.wms.model.warehouse.WhTransProvideNoCommand;


public class ZtoOrderTaskImpl extends BaseManagerImpl implements ZtoOrderTask {
    private static final long serialVersionUID = 1557541729772643785L;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private PackageInfoDao piDao;
    @Autowired
    private StaDeliveryInfoDao sdiDao;
    @Autowired
    private TransOlManager transOlManager;

    /**
     * ZTO 申请电子运单号
     * 
     * @param number,lastno
     * @return
     * @throws UnsupportedEncodingException
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void ztoTransNo() {
        // 如果可用订单号小于500个，那么调用获取订单号的接口，每次获得1000
        log.debug("Get zto trans no trigger begin-----------");
        Long transNo = transProvideNoDao.getTranNoByLpcodeList(new SingleColumnRowMapper<Long>(Long.class));
        log.debug("Get zto trans no trigger begin-----------still have " + transNo + "NO");
        if (transNo < 40000) {
            for (int j = 0; j < 1000; j++) {
                try {
                    String lastNo = transProvideNoDao.getLastTransNo(new SingleColumnRowMapper<String>(String.class));
                    WhTransProvideNoCommand wtp1 = ZtoOrderClient.getZtoTransNo(lastNo, ZTO_USERNAME, ZTO_PASSWORD);
                    List<String> transNoList = wtp1.getList();
                    for (int i = 0; i < transNoList.size(); i++) {
                        try {

                            WhTransProvideNo whTransProvideNo = new WhTransProvideNo();
                            whTransProvideNo.setLpcode(Transportator.ZTO);
                            whTransProvideNo.setTransno(transNoList.get(i));
                            transProvideNoDao.save(whTransProvideNo);
                        } catch (Exception e) {
                            continue;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
    }

    // 设置ZTO单据号
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void ztoInterfaceByWarehouse() {
        Long count = transProvideNoDao.getTranNoNumberByLpCode(Transportator.ZTO, null, new SingleColumnRowMapper<Long>(Long.class));
        if (count > 0) {
            List<Long> idList = warehouseDao.getAllZTOWarehouse(new SingleColumnRowMapper<Long>(Long.class));
            for (Long id : idList) {
                try {
                    Warehouse wh = warehouseDao.getZtoWarehouseByOuId(id, new BeanPropertyRowMapper<Warehouse>(Warehouse.class));
                    if (wh == null) {
                        throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
                    }
                    Boolean flag = true;
                    while (flag) {
                        List<String> lpList = new ArrayList<String>();
                        lpList.add(Transportator.ZTO);
                        List<Long> staList = staDao.findStaByOuIdAndStatus(id, lpList, new SingleColumnRowMapper<Long>(Long.class));
                        if (staList.size() < 100) {
                            flag = false;
                        }
                        for (Long staId : staList) {
                            // ZTO下单
                            try {
                                // 设置ZTO单据号
                                transOlManager.matchingTransNo(staId);
                            } catch (Exception e) {
                                log.error("zto matching trans no error staid : {}", staId);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    // 重置取消订单占用的transno
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void reUsedTransNo() {
        List<Long> noList = transProvideNoDao.getCancelStaTransNo(new SingleColumnRowMapper<Long>(Long.class));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowdate = sdf.format(new Date());
        // 将T_WH_TRANS_PROVIDE_NO里的staId占用更新
        for (Long no : noList) {
            WhTransProvideNo wtp = transProvideNoDao.getByPrimaryKey(no);
            StaDeliveryInfo sdi = sdiDao.getByPrimaryKey(wtp.getStaid());
            String TransNo = sdi.getTrackingNo() + nowdate;
            Long staId = wtp.getStaid();
            wtp.setStaid(null);
            if (TransNo.length() < 35) sdi.setTrackingNo(TransNo);
            List<PackageInfo> pilist = piDao.getByStaId(staId);
            for (PackageInfo pi : pilist) {
                PackageInfo packageInfo = piDao.getByPrimaryKey(pi.getId());
                String newTransNo = packageInfo.getTrackingNo() + nowdate;
                if (newTransNo.length() < 35) piDao.updateTrackingNo(packageInfo.getId(), newTransNo);
            }
            sdiDao.save(sdi);
            transProvideNoDao.save(wtp);
        }
    }
}
