package com.bt.common.base;

import org.springframework.core.convert.converter.Converter;

import com.bt.utils.CommonUtils;

/**
 * @Title:StringToBooleanConverter
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2017年4月11日下午3:30:22
 */
public class StringToBooleanConverter implements Converter<String, Boolean> {

	@Override
	public Boolean convert(String source) {
		if(CommonUtils.checkExistOrNot(source.trim())) {
			return Boolean.parseBoolean(source);
			
		}
		return false;
			
	}

}
