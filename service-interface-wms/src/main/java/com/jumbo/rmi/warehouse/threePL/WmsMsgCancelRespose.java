package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;
import java.util.List;

/**
 * 外包仓取消查询反馈
 * 
 * @author xiaolong.fei
 * 
 */
public class WmsMsgCancelRespose implements Serializable {

    private static final long serialVersionUID = -4090296751995275796L;
    /**
     * 客户来源
     */
    private String customer;

    /**
     * 反馈消息
     */
    private MsgCancelResponseMessage message;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public MsgCancelResponseMessage getMessage() {
        return message;
    }

    public void setMessage(MsgCancelResponseMessage message) {
        this.message = message;
    }

    public static class MsgCancelResponseMessage implements Serializable {

        private static final long serialVersionUID = 3477102386561542542L;
        private int total; // 总条数
        private List<wmsOrderCancel> entry; // 外包仓取消集合

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<wmsOrderCancel> getEntry() {
            return entry;
        }

        public void setEntry(List<wmsOrderCancel> entry) {
            this.entry = entry;
        }
    }

}
