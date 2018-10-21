package com.jumbo.wms.daemon;

public interface OcpOrderEmailInform {
    /**
     * 将超过一定占用失败次数的占用批以邮件的形式通知相关人员
     */
    void exceptionOcpOrderInformEmail();
}
