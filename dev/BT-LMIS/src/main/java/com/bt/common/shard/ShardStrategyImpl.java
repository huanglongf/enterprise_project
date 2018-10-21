package com.bt.common.shard;

import java.util.Calendar;

import com.bt.common.model.ShardTest;
import com.bt.utils.CommonUtils;
import com.google.code.shardbatis.strategy.ShardStrategy;

/** 
 * @ClassName: ShardStrategyImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年6月28日 下午10:04:11 
 * 
 */
public class ShardStrategyImpl implements ShardStrategy {
	
	 /**
      * 得到实际表名
      * @param baseTableName 逻辑表名,一般是没有前缀或者是后缀的表名
      * @param params        mybatis执行某个statement时使用的参数
      * @param mapperId      mybatis配置的statement id
      * @return
      */
	@Override
	public String getTargetTableName(String baseTableName, Object params, String mapperId) {
		String targetTableName=null;
		if(CommonUtils.checkExistOrNot(params)) {
			Calendar cal=Calendar.getInstance();
			cal.setTime(((ShardTest) params).getTime());
			int month=cal.get(Calendar.MONTH)+1;
			targetTableName=baseTableName+cal.get(Calendar.YEAR)+(month>=10?month:"0"+month);
			
		}
		return targetTableName;
	}

}
