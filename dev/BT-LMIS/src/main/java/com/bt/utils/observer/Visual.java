package com.bt.utils.observer;

import java.util.Observable;

/**
 * 
* @ClassName: Visual 
* @Description: TODO(被观察者) 
* @author likun
* @date 2017年1月18日 下午1:11:46 
*
 */
public class Visual extends Observable{
	   
	   private String woId =null;    
	      
	   public String getData(){     
	       return woId;    
	   }    
	   
	   
		public void setData(String data){
			this.woId = data;
			this.setChanged();//标记此 Observable对象为已改变的对象
	        this.notifyObservers();//通知所有的观察者
	        this.deleteObservers();
		}
}
