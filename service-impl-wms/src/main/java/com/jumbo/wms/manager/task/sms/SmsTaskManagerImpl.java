package com.jumbo.wms.manager.task.sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.system.SmsLogDao;
import com.jumbo.dao.system.SmsQueueDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.system.SmsLog;
import com.jumbo.wms.model.system.SmsQueue;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public class SmsTaskManagerImpl extends BaseManagerImpl implements SmsTaskManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6085984010267883440L;
    @Autowired
    private SmsQueueDao smsQueueDao;
    @Autowired
    private SmsLogDao smsLogDao;

    @Override
    public List<SmsQueue> getNeedSendSmsList() {
        return smsQueueDao.getNeedSendSmsList(new BeanPropertyRowMapper<SmsQueue>(SmsQueue.class));
    }

    @Override
    public void deleteFromInsertTo(SmsQueue sQ, Integer status, String sendTime) {
        SmsLog sl = new SmsLog();
        sl.setStaCode(sQ.getStaCode());
        sl.setCreateTime(sQ.getCreateTime());
        sl.setMobile(sQ.getMobile());
        sl.setSmsContent(sQ.getSmsContent());
        sl.setMsgId(sQ.getMsgId());
        sl.setSendStatus(status);
        sl.setSendTime(sendTime);
        sl.setOwner(sQ.getOwner());
        smsLogDao.save(sl);
        smsQueueDao.delete(sQ);
    }

    @Override
    public void updateErrorCountById(Long id) {
        smsQueueDao.updateErrorCountById(id);
    }

    @Override
    public void updateSmsQueueMsgId(SmsQueue smsQueue, String rs) {
        smsQueue.setMsgId(rs);
        smsQueueDao.save(smsQueue);
    }

    @Override
    public Map<String, List<SmsQueue>> getSmsQueueHaveMsgIdList() {
        List<SmsQueue> smsList = smsQueueDao.getSmsQueueHaveMsgIdList(new BeanPropertyRowMapper<SmsQueue>(SmsQueue.class));
        Map<String, List<SmsQueue>> map = new HashMap<String, List<SmsQueue>>();
        for (SmsQueue sq : smsList) {
            if (sq.getOwner() == null || sq.getErrorCount() == 3) {
                if (map.get("error") == null) {
                    List<SmsQueue> sl = new ArrayList<SmsQueue>();
                    sl.add(sq);
                    map.put("error", sl);
                } else {
                    map.get("error").add(sq);
                }
            } else {
                if (map.get(sq.getOwner()) == null) {
                    List<SmsQueue> sl = new ArrayList<SmsQueue>();
                    sl.add(sq);
                    map.put(sq.getOwner(), sl);
                } else {
                    map.get(sq.getOwner()).add(sq);
                }
            }
        }
        return map;
    }

    @Override
    public List<BiChannel> getShopAccountList() {
        return null;
        // return companyShopDao.getShopAccountList(new
        // BeanPropertyRowMapper<CompanyShop>(CompanyShop.class));
    }

    @Override
    public void updateTheQueueSetFailedById(Long id) {
        smsQueueDao.updateTheQueueSetFailedById(id);
    }

}
