package com.bt.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.bt.JUnitAbstract;
import com.bt.common.dao.ShardTestMapper;
import com.bt.common.model.ShardTest;

/** 
 * @ClassName: ShardTestDemo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年6月28日 下午5:13:05 
 * 
 */
public class ShardTestDemo extends JUnitAbstract {

	@Autowired
	private ShardTestMapper<T> mapper;
	
	@Test
	// 标明此方法需使用事务  
    @Transactional
    // 标明使用完此方法后事务不回滚,true时为回滚
    @Rollback(false)
	public void insert() {
		try {
			mapper.insert(new ShardTest(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-05-18 18:18:18")));
			mapper.insert(new ShardTest(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-06-18 18:18:18")));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
}
