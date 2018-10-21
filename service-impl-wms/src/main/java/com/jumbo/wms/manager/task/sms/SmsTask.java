package com.jumbo.wms.manager.task.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.system.SmsQueue;
import com.jumbo.util.HttpClientUtil;

/**
 * 
 * @author jinlong.ke
 * 
 */
public class SmsTask extends BaseManagerImpl {

    /**
	 * 
	 */
    private static final long serialVersionUID = -841505014696660448L;
    // private static final String USERNAME="WRGF";
    // private static final String PASSWORD="008899";
    @Autowired
    private SmsTaskManager smsTaskManager;

    public void smsSendTask() {
        sendSms();
    }

    private void sendSms() {
        Map<String, BiChannel> map = getShopMsgAccountAndPassword();
        List<SmsQueue> smsList = smsTaskManager.getNeedSendSmsList();
        sendTheMessage(smsList, map);
    }

    /**
     * 得到所有具有短信平台账户的店铺信息(inner_shop_id,msg_account,msg_password)
     * 
     * @return
     */
    private Map<String, BiChannel> getShopMsgAccountAndPassword() {
        List<BiChannel> shopListAccount = smsTaskManager.getShopAccountList();
        Map<String, BiChannel> shopAccountMap = new HashMap<String, BiChannel>();
        for (BiChannel shop : shopListAccount) {
            shopAccountMap.put(shop.getCode(), shop);
        }
        return shopAccountMap;
    }

    /**
     * 循环信息中间表，调用短信接口，请求失败提示 请求成功，获取反馈msgID,更新对应信息 遇到异常，更新失败次数。
     * 
     * @param smsList
     * @param userName
     * @param password
     */
    private void sendTheMessage(List<SmsQueue> smsList, Map<String, BiChannel> map) {
        for (int i = 0; i < smsList.size(); i++) {
            if (smsList.get(i).getOwner() == null || map.get(smsList.get(i).getOwner()) == null) {
                smsTaskManager.updateTheQueueSetFailedById(smsList.get(i).getId());// 如果不存在账户名，密码默认直接设置短信不再发送
            } else {
//                CompanyShop cs = map.get(smsList.get(i).getOwner());
                try {
//                    Date d = new Date();
//                    SimpleDateFormat sfm = new SimpleDateFormat("MMddHHmmss");
//                    String time = sfm.format(d);
//                    String passwd = AppSecretUtil.getMD5Str(cs.getMsgAccount() + cs.getMsgPassword() + time);
                    String url = null;
//                    url = "http://api.nashikuai.cn/sendsms.aspx?user=" + cs.getMsgAccount() + "&passwd=" + passwd + "&mobs=" + smsList.get(i).getMobile() + "&msg=" + URLEncoder.encode(smsList.get(i).getSmsContent(), "UTF-8") + "&ts=" + time;
                    String rs = HttpClientUtil.httpGet(url);
                    if (rs != null) {
                        if (rs.startsWith("Error:")) {
                            log.info("当发送信息" + smsList.get(i).getId() + "时请求失败！");
                            throw new BusinessException("Request Failed");
                        } else {
                            // 返回结果包含了换行和空格，截取后放入
                            smsTaskManager.updateSmsQueueMsgId(smsList.get(i), rs.substring(0, 18));
                        }
                    }
                } catch (Exception e) {
                    smsTaskManager.updateErrorCountById(smsList.get(i).getId());
                    log.info("Send the msg:" + smsList.get(i).getId() + "failed!");
                    continue;
                }
            }

        }
    }

    /**
     * 查询状态报告，更新发送结果到log表，删除原始中间表数据
     */
    public void getReport() {
        Map<String, BiChannel> map = getShopMsgAccountAndPassword();
        Map<String, List<SmsQueue>> shopMsg = smsTaskManager.getSmsQueueHaveMsgIdList();
        for (Entry<String, List<SmsQueue>> entry : shopMsg.entrySet()) {
            String key = entry.getKey();
            List<SmsQueue> sl = shopMsg.get(key);
            if (key.equals("error") || map.get(key) == null) {
                for (SmsQueue sq : sl) {
                    smsTaskManager.deleteFromInsertTo(sq, 1, null);
                }
            } else {
//                CompanyShop cs = map.get(key);
//                Date d = new Date();
//                Date b = new Date(d.getTime() - 24 * 60 * 60 * 1000);
//                SimpleDateFormat sfm = new SimpleDateFormat("MMddHHmmss");
//                SimpleDateFormat sfm1 = new SimpleDateFormat("yyyyMMddHHmm");
//                String time = sfm.format(d);
//                String begin = sfm1.format(b);
//                String end = sfm1.format(d);
//                String passwd = AppSecretUtil.getMD5Str(cs.getMsgAccount() + cs.getMsgPassword() + time);
                String url = null;
//                url = "http://api.nashikuai.cn/Report.aspx?user=" + cs.getMsgAccount() + "&passwd=" + passwd + "&ts=" + time + "&begin=" + begin + "&end=" + end;
                log.info(url);
                String rs = HttpClientUtil.httpGet(url);
                String[] result = rs.split("\n");
                Map<String, Integer> statusMap = new HashMap<String, Integer>();
                Map<String, String> timeMap = new HashMap<String, String>();
                for (int i = 0; i < result.length; i++) {
                    if (i >= 1) {
                        statusMap.put(result[i].split(",")[0], Integer.parseInt(result[i].split(",")[2]));
                        timeMap.put(result[i].split(",")[0], result[i].split(",")[3]);
                    }
                }
                // for(int j=0;j<smsList.size();j++){
                // String msgId = smsList.get(j).getMsgId();
                // try{
                // if(msgId==null){
                // smsTaskManager.deleteFromInsertTo(smsList.get(j),1,null);
                // }else{
                // if(statusMap.get(msgId)!=null){
                // smsTaskManager.deleteFromInsertTo(smsList.get(j),statusMap.get(msgId),timeMap.get(msgId));
                // }
                // }
                // }catch(Exception e){
                // continue;
                // }
                // }
                for (SmsQueue sq : sl) {
                    try {
                        if (statusMap.get(sq.getMsgId()) != null) {
                            smsTaskManager.deleteFromInsertTo(sq, statusMap.get(sq.getMsgId()), timeMap.get(sq.getMsgId()));
                        }
                    } catch (Exception e) {
                        continue;
                    }
                    // smsTaskManager.deleteFromInsertTo(sq,1,null);
                }
            }
        }
    }
}
