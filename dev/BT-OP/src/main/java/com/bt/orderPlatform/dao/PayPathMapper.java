package com.bt.orderPlatform.dao;

import java.util.List;

import com.bt.orderPlatform.model.PayPath;

/**
* @ClassName: PayPathMapper
* @Description: TODO(PayPathMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface PayPathMapper<T> {

	List<PayPath> queryAll();

	PayPath selectByname(String pay_path);
	
	
}
