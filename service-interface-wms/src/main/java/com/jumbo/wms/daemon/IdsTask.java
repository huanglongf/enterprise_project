package com.jumbo.wms.daemon;

import java.util.Calendar;
import java.util.Map;

public interface IdsTask {

    /**
     * 反馈信息任务
     */
    public void idsFeedbackTask();

    public void vimIDSSaleOutboundRtn();

    /**
     * 抓取文件
     */
    public void download(Map<String, String> config, Calendar c);

    /**
     * 定时任务 对MsgOutboundOrder解锁
     * 
     * @param sta
     */
    public void msgUnLocked();

    /**
     * 定时任务邮件发送
     */
    public void mailIds();

    /**
     * 推送推送利丰销售单和换货单失败定时任务预警邮件发送
     */
    public void salesOrderSendToLFFailedMailIds();
}
