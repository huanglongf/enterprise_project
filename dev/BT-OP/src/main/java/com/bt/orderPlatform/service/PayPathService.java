package com.bt.orderPlatform.service;

import java.util.List;

import com.bt.orderPlatform.model.PayPath;

public interface PayPathService<T> {

	List<PayPath> queryAll();

	PayPath selectByname(String pay_path);

}
