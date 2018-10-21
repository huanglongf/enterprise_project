package com.bt.lmis.basis.service;

import java.util.List;

import com.bt.lmis.basis.model.Consumer;

/** 
 * @ClassName: ConsumerManagerService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月31日 下午1:14:52 
 * 
 * @param <T>
 */
public interface ConsumerManagerService<T> {

	/**
	 * @Title: listValidConsumer
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return: List<Consumer>
	 * @author: Ian.Huang
	 * @date: 2017年7月31日 下午1:12:25
	 */
	List<Consumer> listValidConsumer();
	
}
