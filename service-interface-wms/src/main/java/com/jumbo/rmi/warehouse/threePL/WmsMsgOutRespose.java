package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;
import java.util.List;

/**
 * 外包仓出库查询反馈
 * 
 * @author xiaolong.fei
 * 
 */
public class WmsMsgOutRespose implements Serializable {

    private static final long serialVersionUID = -4090296751995275796L;
    /**
     * 客户来源
     */
    private String customer;

    /**
     * 反馈消息
     */
    private MsgOutResponseMessage message;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public MsgOutResponseMessage getMessage() {
        return message;
    }

    public void setMessage(MsgOutResponseMessage message) {
        this.message = message;
    }

    public static class MsgOutResponseMessage implements Serializable {

        private static final long serialVersionUID = 3477102386561542542L;
        private int total; // 总条数
        private List<WmsOutBoundOrder> entry; // 外包仓出库信息

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<WmsOutBoundOrder> getEntry() {
            return entry;
        }

        public void setEntry(List<WmsOutBoundOrder> entry) {
            this.entry = entry;
        }
    }

}
