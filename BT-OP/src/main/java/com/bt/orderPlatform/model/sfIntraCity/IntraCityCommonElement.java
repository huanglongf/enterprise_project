package com.bt.orderPlatform.model.sfIntraCity;


public class IntraCityCommonElement{
    
    /**api开发者ID**/
    private Integer dev_id;
    
    /***店铺ID**/
    private String shop_id;
    
    /***店铺ID类型 1、顺丰店铺ID 2、接入方店铺ID  不填默认1**/
    private Integer shop_type;
 
    /**取消推送时间；秒级时间戳**/
    private Long push_time;

    
    public Integer getDev_id(){
        return dev_id;
    }

    
    public void setDev_id(Integer dev_id){
        this.dev_id = dev_id;
    }

    
    public String getShop_id(){
        return shop_id;
    }

    
    public void setShop_id(String shop_id){
        this.shop_id = shop_id;
    }

    
    public Integer getShop_type(){
        return shop_type;
    }

    
    public void setShop_type(Integer shop_type){
        this.shop_type = shop_type;
    }


    
    public Long getPush_time(){
        return push_time;
    }


    
    public void setPush_time(Long push_time){
        this.push_time = push_time;
    }

    

    
    
    
}
