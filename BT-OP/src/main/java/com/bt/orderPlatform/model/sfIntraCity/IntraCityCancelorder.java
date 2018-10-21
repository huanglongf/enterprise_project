package com.bt.orderPlatform.model.sfIntraCity;


public class IntraCityCancelorder extends IntraCityCommonElement{
     
    /** 订单ID**/
    private String order_id;
    
    /**查询订单ID类型 1、顺丰订单号 2、商家订单号**/
    private Integer order_type;
 
    private String cancel_reason;

    
    public String getOrder_id(){
        return order_id;
    }

    
    public void setOrder_id(String order_id){
        this.order_id = order_id;
    }

    
    public Integer getOrder_type(){
        return order_type;
    }

    
    public void setOrder_type(Integer order_type){
        this.order_type = order_type;
    }

    
    public String getCancel_reason(){
        return cancel_reason;
    }

    
    public void setCancel_reason(String cancel_reason){
        this.cancel_reason = cancel_reason;
    }
    
    


}
