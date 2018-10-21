package com.bt.common.base;

import org.springframework.core.convert.converter.Converter;

import com.bt.utils.CommonUtils;

public class StringToLoadingTypeConverter implements Converter<String, LoadingType> {

	@Override
	public LoadingType convert(String source) {
		if(CommonUtils.checkExistOrNot(source.trim())) {
			return LoadingType.get(source);
			
		}
		return null;
			
	}

}
