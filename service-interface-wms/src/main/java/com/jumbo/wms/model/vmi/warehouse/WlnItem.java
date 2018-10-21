package com.jumbo.wms.model.vmi.warehouse;

import java.io.Serializable;

public class WlnItem implements Serializable {



    private static final long serialVersionUID = 4931942864976371659L;
    private String trade_code;
    private String sub_trade_code;
    private String item_id;
    private String item_code;
    private String item_quantity;
    private String item_price;
    private String inventory_type;
    private String owner_user_nick;
    private String flag;

    public String getTrade_code() {
        return trade_code;
    }

    public void setTrade_code(String tradeCode) {
        trade_code = tradeCode;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String itemCode) {
        item_code = itemCode;
    }

    public String getInventory_type() {
        return inventory_type;
    }

    public void setInventory_type(String inventoryType) {
        inventory_type = inventoryType;
    }

    public String getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(String itemQuantity) {
        item_quantity = itemQuantity;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String itemPrice) {
        item_price = itemPrice;
    }

    public String getOwner_user_nick() {
        return owner_user_nick;
    }

    public void setOwner_user_nick(String ownerUserNick) {
        owner_user_nick = ownerUserNick;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String itemId) {
        item_id = itemId;
    }

    public String getSub_trade_code() {
        return sub_trade_code;
    }

    public void setSub_trade_code(String subTradeCode) {
        sub_trade_code = subTradeCode;
    }


}
