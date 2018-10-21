package com.bt.lmis.basis.service.impl;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.basis.dao.ConsumerManagerMapper;
import com.bt.lmis.basis.model.Consumer;
import com.bt.lmis.basis.service.ConsumerManagerService;

/** 
 * @ClassName: ConsumerManagerServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年7月31日 下午1:09:28 
 * 
 */
@Service
public class ConsumerManagerServiceImpl implements ConsumerManagerService<T> {

	@Autowired
	private ConsumerManagerMapper<T> consumerManagerMapper;

	@Override
	public List<Consumer> listValidConsumer() {
		return consumerManagerMapper.listValidConsumer();
	}
	
}
