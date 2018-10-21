package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;
import java.util.List;

/**
 * 外包仓销售出库查询反馈
 * 
 * @author xiaolong.fei
 * 
 */
public class WmsMsgSaleOutRespose implements Serializable {

    private static final long serialVersionUID = -4090296751995275796L;
    /**
     * 客户来源
     */
    private String customer;

    /**
     * 反馈消息
     */
    private MsgSaleOutRequsetMessage message;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public MsgSaleOutRequsetMessage getMessage() {
        return message;
    }

    public void setMessage(MsgSaleOutRequsetMessage message) {
        this.message = message;
    }

    public static class MsgSaleOutRequsetMessage implements Serializable {

        private static final long serialVersionUID = 3477102386561542542L;
        private int total; // 总条数
        private List<WmsSalesOrder> entry; // 外包仓销售出库信息

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<WmsSalesOrder> getEntry() {
            return entry;
        }

        public void setEntry(List<WmsSalesOrder> entry) {
            this.entry = entry;
        }
    }

}
