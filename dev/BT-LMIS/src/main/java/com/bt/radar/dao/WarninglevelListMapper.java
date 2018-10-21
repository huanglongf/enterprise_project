package com.bt.radar.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.code.BaseMapper;
import com.bt.radar.controller.form.WarninglevelListQueryParam;
import com.bt.radar.model.WarninglevelList;

/**
* @ClassName: WarninglevelListMapper
* @Description: TODO(WarninglevelListMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface WarninglevelListMapper<T> extends BaseMapper<T> {


	List<T> findByWType(WarninglevelListQueryParam param);
	 
	void updateWll(WarninglevelListQueryParam param);

	
	/**
	 * 
	 * @Description: TODO
	 * @param levelup_level
	 * @param warningtype_code
	 * @return
	 * @return: List<WarninglevelList>  
	 * @author Ian.Huang 
	 * @date 2016年9月2日下午5:57:51
	 */
	public List<WarninglevelList> selectRecord(@Param("levelup_level")String levelup_level, @Param("warningtype_code")String warningtype_code);
	/**
	 * 
	 * @Description: TODO
	 * @param warninglevelList
	 * @return
	 * @return: List<WarninglevelList>  
	 * @author Ian.Huang 
	 * @date 2016年9月2日下午4:32:46
	 */
	public List<WarninglevelList> selectRecords(WarninglevelList warninglevelList);
	
	public List<Map<String,Object>> ExistsLevel(Map param);
	
	public List<Map<String,Object>> checkLevel(Map param);
	
	public List<Map<String,Object>> getBigANDLittle(Map param);

	Map analyzeLevel(Map params);
	
   void	updateLevel(Map<String,String> params);

     WarninglevelList findLittlestLevel(WarninglevelListQueryParam param);

	void deleteByCon(Map dparam);
}
