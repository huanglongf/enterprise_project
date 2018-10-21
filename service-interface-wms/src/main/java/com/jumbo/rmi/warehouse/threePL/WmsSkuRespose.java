package com.jumbo.rmi.warehouse.threePL;

import java.io.Serializable;
import java.util.List;

/**
 * 外包仓商品返回请求参数
 * 
 * @author xiaolong.fei
 * 
 */
public class WmsSkuRespose implements Serializable {

    private static final long serialVersionUID = -4090296751995275796L;
    /**
     * 请求
     */
    private MsgSkuRequsetMessage message;

    /**
     * 客户来源
     */
    private String customer;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public MsgSkuRequsetMessage getMessage() {
        return message;
    }

    public void setMessage(MsgSkuRequsetMessage message) {
        this.message = message;
    }

    public static class MsgSkuRequsetMessage implements Serializable {

        private static final long serialVersionUID = 3477102386561542542L;
        private int total; // 总条数
        private List<WmsSku> entry; // 商品信息集合

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<WmsSku> getEntry() {
            return entry;
        }

        public void setEntry(List<WmsSku> entry) {
            this.entry = entry;
        }
    }
}
