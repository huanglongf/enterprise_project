package com.jumbo.wms.manager.expressDelivery.logistics.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhTransProvideNoDao;
import com.jumbo.util.MailUtil;
import com.jumbo.wms.manager.expressDelivery.logistics.TransOlInterface;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.WhTransProvideNo;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

/**
 * 
 * @author jinggang.chen 香港Nike仓yamato物流
 */
@Service("transOlYamato")
@Transactional
public class TransOlYamato implements TransOlInterface {

    protected static final Logger logger = LoggerFactory.getLogger(TransOlYamato.class);

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private WhTransProvideNoDao transProvideNoDao;

    @Override
    public StaDeliveryInfo matchingTransNoOffLine(TransOrder order, Long whOuId) {
        return null;
    }

    @Override
    public StaDeliveryInfo matchingTransNoEs(Long staId) throws Exception {
        return null;
    }

    @Override
    public synchronized StaDeliveryInfo matchingTransNo(Long staId) throws Exception {
        StaDeliveryInfo sd = staDeliveryInfoDao.getByPrimaryKey(staId);
        try {
            if (sd.getTrackingNo() != null) {
                return null;
            }
            WhTransProvideNo noCache1 = createYamatoNo(sd);
            if (noCache1 == null) {
                return null;
            }
            WhTransProvideNo noCache2 = transProvideNoDao.getByPrimaryKey(noCache1.getId());
            // 获取运单号并且绑定到T_WH_STA_DELIVERY_INFO表
            sd.setTrackingNo(noCache1.getTransno());
            sd.setLastModifyTime(new Date());
            noCache2.setStaid(sd.getId());
            staDeliveryInfoDao.save(sd);
            staDeliveryInfoDao.flush();
            transProvideNoDao.save(noCache2);
            transProvideNoDao.flush();
        } catch (Exception e) {
            logger.error("matchingTransNoZJB" + staId, e);
            throw e;
        }
        return sd;
    }

    @Override
    public StaDeliveryInfo matchingTransNoByPackId(Long packId, String type) {
        return null;
    }

    @Override
    public void setRegistConfirmOrder(StockTransApplication sta, String newTransNo) {

    }

    @Override
    public void setRegistConfirmInvoiceOrder(WmsInvoiceOrder wio) {

    }

    @Override
    public PickingListPackage matchingTransNoByPackage(Long plId) {
        return null;
    }

    @Override
    public WmsInvoiceOrder invoiceOrderMatchingTransNo(Long wioId) {
        return null;
    }

    @Override
    public WmsInvoiceOrder matchingTransNoForInvoiceOrder(Long id) {
        return null;
    }

    /**
     * yamato快递单号生成规则
     * 
     * @return
     */
    public WhTransProvideNo createYamatoNo(StaDeliveryInfo sd) {
        Long sum = 0L;
        Long cd = null;
        StringBuffer sb = new StringBuffer();
        // 初始化邮件分发码字符数组
        String[] mailCode = new String[] {"0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
        // 初始化权重码
        String[] wight = new String[] {"4", "6", "2", "3", "1", "5", "4", "6", "2", "3", "1"};
        String ymtTrackingNo = null;
        // 得到宝尊系统邮件分发码
        WhTransProvideNo whTransProvideNo = transProvideNoDao.getYamatoTranNoByLpcodeAndAccount(sd.getLpCode(), null, sd.getIsCod() == null ? 0 : (sd.getIsCod() ? 1 : 0), new BeanPropertyRowMapper<WhTransProvideNo>(WhTransProvideNo.class));
        // 缓存单号使用完毕，邮件通知开发手动维护
        if (whTransProvideNo == null) {
            MailUtil.sendMail("香港yamato缓存单号不足提醒", "jinggang.chen@baozun.cn", "", "黑猫物流缓存单号使用完毕，请手动维护" + (sd.getIsCod() == true ? "COD" : "非COD") + "。", false, null);
            return null;
        }
        whTransProvideNo = transProvideNoDao.getByPrimaryKey(whTransProvideNo.getId());
        String code = whTransProvideNo.getTransno();
        for (int i = 0; i < code.length(); i++) {
            mailCode[mailCode.length - 1 - i] = String.valueOf(code.charAt(code.length() - 1 - i));
        }
        // 得到邮件分发码和终止码之间的cd码，权重和邮件分发码对应位相乘相加在余7得到的数即cd码。
        for (int j = 0; j < wight.length; j++) {
            sum = sum + Integer.parseInt(mailCode[j]) * Integer.parseInt(wight[j]);
            sb.append(mailCode[j]);
        }
        cd = sum % 7;
        // 得到最终yamato运单号
        ymtTrackingNo = sb + String.valueOf(cd);
        whTransProvideNo.setTransno(ymtTrackingNo);
        return whTransProvideNo;
    }
}
