package com.bt.orderPlatform.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;


    /***
     * 
     * <b>类名：</b>OrderZDServiceResponseInfo.java<br>
     * @author <font color='blue'>chenkun</font> 
     * @date  2018年3月22日 下午3:48:13
     * @Description 回文信息
     */
    @XmlRootElement
    @XmlAccessorType(XmlAccessType.FIELD)
    public  class  OrderZDServiceResponseInfo implements Serializable{

    /** serialVersionUID */
    
    private static final long serialVersionUID = -180093999599979894L;
    
    
    /**客户订单号**/
    @XmlAttribute(name = "orderid")
    private String orderid;
    
    /**SF母单单号**/
    @XmlAttribute(name = "main_mailno")
    private String main_mailno;
  
    /**新增子单 逗号分隔**/
    @XmlAttribute(name = "mailno_zd")
    private String mailno_zd;
    
    public String getOrderid(){
        return orderid;
    }

    
    public void setOrderid(String orderid){
        this.orderid = orderid;
    }

    
    public String getMain_mailno(){
        return main_mailno;
    }

    
    public void setMain_mailno(String main_mailno){
        this.main_mailno = main_mailno;
    }

    
    public String getMailno_zd(){
        return mailno_zd;
    }

    
    public void setMailno_zd(String mailno_zd){
        this.mailno_zd = mailno_zd;
    }


    /***
     * 
     * <b>方法名：</b>：getZd<br>
     * <b>功能说明：</b>：TODO<br>
     * @author <font color='blue'>chenkun</font> 
     * @date  2018年3月22日 下午5:53:25
     * @return 获取子单集合
     */
    public String [] getZd(){
        if(mailno_zd!=null&&!mailno_zd.trim().isEmpty()){
            return mailno_zd.split(",");
        }
        return null;
    }

}   
