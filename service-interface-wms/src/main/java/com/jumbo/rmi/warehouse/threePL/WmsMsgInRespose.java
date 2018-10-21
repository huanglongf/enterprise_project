package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;
import java.util.List;

/**
 * 外包仓入库查询返回请求参数
 * 
 * @author xiaolong.fei
 * 
 */
public class WmsMsgInRespose implements Serializable {

    private static final long serialVersionUID = -4090296751995275796L;
    /**
     * 客户来源
     */
    private String customer;

    /**
     * 请求
     */
    private MsgInRequsetMessage message;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public MsgInRequsetMessage getMessage() {
        return message;
    }

    public void setMessage(MsgInRequsetMessage message) {
        this.message = message;
    }

    public static class MsgInRequsetMessage implements Serializable {

        private static final long serialVersionUID = 3477102386561542542L;
        private int total; // 总条数
        private List<WmsInboundOrder> entry; // 外包仓入库信息

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<WmsInboundOrder> getEntry() {
            return entry;
        }

        public void setEntry(List<WmsInboundOrder> entry) {
            this.entry = entry;
        }
    }

}
