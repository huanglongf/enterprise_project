package com.jumbo.webservice.zto;

import java.util.List;

public class ZtoCommand {
    private String id;
    private String type;
    private String tradeid;
    private String mailno;
    private String seller;
    private String buyer;
    private double weight;
    private String size;
    private String quantity;
    private double price;
    private double freight;
    private double premium;
    private double pack_charges;
    private double other_charges;
    private double order_sum;
    private String collect_moneytype;
    private double collect_sum;
    private String remark;
    private List<Item> items;
    private Receiver receiver;
    private Sender sender;


    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
    public Receiver getReceiver() {
        return receiver;
    }
    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
    public Sender getSender() {
        return sender;
    }
    public void setSender(Sender sender) {
        this.sender = sender;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTradeid() {
        return tradeid;
    }
    public void setTradeid(String tradeid) {
        this.tradeid = tradeid;
    }
    public String getMailno() {
        return mailno;
    }
    public void setMailno(String mailno) {
        this.mailno = mailno;
    }
    public String getSeller() {
        return seller;
    }
    public void setSeller(String seller) {
        this.seller = seller;
    }
    public String getBuyer() {
        return buyer;
    }
    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getQuantity() {
        return quantity;
    }
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getFreight() {
        return freight;
    }
    public void setFreight(double freight) {
        this.freight = freight;
    }
    public double getPremium() {
        return premium;
    }
    public void setPremium(double premium) {
        this.premium = premium;
    }
    public double getPack_charges() {
        return pack_charges;
    }
    public void setPack_charges(double pack_charges) {
        this.pack_charges = pack_charges;
    }
    public double getOther_charges() {
        return other_charges;
    }
    public void setOther_charges(double other_charges) {
        this.other_charges = other_charges;
    }
    public double getOrder_sum() {
        return order_sum;
    }
    public void setOrder_sum(double order_sum) {
        this.order_sum = order_sum;
    }
    public String getCollect_moneytype() {
        return collect_moneytype;
    }
    public void setCollect_moneytype(String collect_moneytype) {
        this.collect_moneytype = collect_moneytype;
    }
    public double getCollect_sum() {
        return collect_sum;
    }
    public void setCollect_sum(double collect_sum) {
        this.collect_sum = collect_sum;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    
}
