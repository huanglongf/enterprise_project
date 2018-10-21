package com.jumbo.wms.manager.task.inventoryTransactionToOms;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.warehouse.InventoryTransactionToOmsDao;
import com.jumbo.dao.warehouse.InventoryTransactionToOmsLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.util.MailUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.listener.StvListenerManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.warehouse.InventoryTransactionToOms;
import com.jumbo.wms.model.warehouse.InventoryTransactionToOmsLog;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

@Service("inventoryTransactionToOmsTaskManager")
public class InventoryTransactionToOmsTaskManagerImpl extends BaseManagerImpl implements InventoryTransactionToOmsTaskManager {

    /**
     * 
     */
    private static final long serialVersionUID = -8217171000974077582L;
    private static final Logger log = LoggerFactory.getLogger(InventoryTransactionToOmsTaskManagerImpl.class);
    @Autowired
    private StvListenerManager stvListenerManager;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private InventoryTransactionToOmsDao ttoDao;
    @Autowired
    private InventoryTransactionToOmsLogDao invTTOLogDao;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private MailTemplateDao mailTemplateDao;

    @Override
    public List<InventoryTransactionToOms> getNeedExcuteData() {
        return ttoDao.getAllNeedExcuteData(new BeanPropertyRowMapper<InventoryTransactionToOms>(InventoryTransactionToOms.class));
    }

    @Override
    @SingleTaskLock
    public void noticePacsData(Long id, Long stvId) {
        StockTransVoucher stv = stvDao.getByPrimaryKey(stvId);
        InventoryTransactionToOms tto = ttoDao.getByPrimaryKey(id);
        try {
            stvListenerManager.inventoryTransactionToOms(stv, false);
            InventoryTransactionToOmsLog ttol = new InventoryTransactionToOmsLog();
            ttol.setCreateTime(tto.getCreateTime());
            ttol.setStaCode(tto.getStaCode());
            ttol.setStvCode(tto.getStvCode());
            ttol.setStvId(tto.getStvId());
            ttol.setLastModifyTime(new Date());
            invTTOLogDao.save(ttol);
            ttoDao.deleteByPrimaryKey(id);
        } catch (BusinessException e) {
            String s = applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            log.error(s);
            log.error("", e);
            tto.setMemo(s.length() > 1000 ? s.substring(0, 1000) : s);
            tto.setErrorCount(tto.getErrorCount() + 1);
            tto.setLastModifyTime(new Date());
            ttoDao.save(tto);
        } catch (Exception e) {
            log.error("", e);
            tto.setMemo("WMS系统执行异常!");
            tto.setLastModifyTime(new Date());
            tto.setErrorCount(tto.getErrorCount() + 1);
            ttoDao.save(tto);
        }
    }

    @Override
    public List<InventoryTransactionToOms> getNeedSendData() {
        return ttoDao.getNeedSendData(new BeanPropertyRowMapper<InventoryTransactionToOms>(InventoryTransactionToOms.class));
    }

    @Override
    @SingleTaskLock
    public void sendEmailForITTO(List<InventoryTransactionToOms> ilist) {
        Map<String, String> option = chooseOptionManager.getOptionByCategoryCode(Constants.INV_TO_OMS_EMAIL);
        MailTemplate template = mailTemplateDao.findTemplateByCode(Constants.INV_TO_OMS_EMAIL);
        StringBuffer mailBody = new StringBuffer("库存流水同步出错单据批次明细:\r\n");
        for (InventoryTransactionToOms ito : ilist) {
            mailBody.append(ito.getStaCode() + "\t" + ito.getStvCode() + "\t" + ito.getMemo() + "\r\n");
        }
        MailUtil.sendMail(template.getSubject(), option.get(Constants.RECEIVER_ADDRESS), option.get(Constants.CARBON_COPY), mailBody.toString(), true, null);
        for (InventoryTransactionToOms ito : ilist) {
            InventoryTransactionToOms itt = ttoDao.getByPrimaryKey(ito.getId());
            itt.setIsSend(true);
            ttoDao.save(itt);
        }
    }
}
