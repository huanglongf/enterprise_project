package com.bt.lmis.dao;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;

/**
* @ClassName: StoreWithoutInsuranseMapper
* @Description: TODO(不结算保价费店铺)
* @author Ian.Huang
* @date 2016年10月18日 下午3:24:06
*
* @param <T>
*/
public interface StoreWithoutInsuranseMapper<T> extends BaseMapper<T> {

	/**
	* @Title: judgeExist
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param @param store_code
	* @param @param store_name
	* @param @return    设定文件
	* @return Integer    返回类型
	* @throws
	*/ 
	public Integer judgeExist(@Param("store_code")String store_code, @Param("store_name")String store_name);
}
