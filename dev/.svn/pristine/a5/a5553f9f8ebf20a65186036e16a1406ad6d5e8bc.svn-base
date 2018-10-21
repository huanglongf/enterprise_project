package com.bt.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.service.DictService;
import com.bt.workOrder.model.WkType;
import com.bt.workOrder.service.WkTypeService;

//字典转换util
public class DictUtil {

	private static final Logger logger = Logger.getLogger(DictUtil.class);
	
	private static DictService dictService = (DictService)SpringUtils.getBean("dictServiceImpl");
	
	private static WkTypeService wkTypeService = (WkTypeService)SpringUtils.getBean("wkTypeServiceImpl");
	
    public static String getDictLabel(Object dictType, Object dictValue) {
    	String dictLabel = null;
    	try {
    		dictLabel = dictService.findByTypeValue(dictType.toString(),dictValue.toString());
    		if (StringUtils.isBlank(dictLabel)) {
    			return dictValue.toString();
    		}
    	} catch (NullPointerException e) {
    		return "";
    	}
        return dictLabel;  
    }  
    
    public static String getWkTypeNameByCode(Object wkTypeCode){
    	String wkTypeName = null;
    	try {
    		wkTypeName = wkTypeService.getWkTypeByCode(wkTypeCode.toString());
    		if (StringUtils.isBlank(wkTypeName)) {
    			return wkTypeCode.toString();
    		}
    	} catch (NullPointerException e) {
        	return "";
    	}
    	return wkTypeName;
    }
    
    public static String getNginxDownloadPreFix(){
    	return CommonUtils.getAllMessage("config", "NGINX_FILE_DOWNLOAD");
    }
    
    public static String getNginxUploadPreFix(){
    	return CommonUtils.getAllMessage("config", "NGINX_FILE_UPLOAD");
    }
}
