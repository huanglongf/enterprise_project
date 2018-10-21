package com.bt.common.base;

/**
 * @Title:LoadingType
 * @Description: TODO(主页面加载类型) 
 * @author Ian.Huang 
 * @date 2017年4月10日下午5:41:56
 */
public enum LoadingType {
	
	// 主页面加载（主页面刷新）
	MAIN("MAIN"),
	// 数据加载（数据刷新）
	DATA("DATA");
	
	private final String value;
	
	private LoadingType(String val) {  
        this.value = val;
    }
	public String toString() {  
        return this.value;  
    }  
	public static LoadingType get(String str) {  
        for (LoadingType lt : values()) {  
            if(lt.toString().equals(str)) {  
                return lt;  
            }  
        }  
        return null;  
    }  
	
}
