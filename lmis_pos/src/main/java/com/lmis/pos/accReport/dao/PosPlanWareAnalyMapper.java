package com.lmis.pos.accReport.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.lmis.pos.accReport.model.PosPlanWareAnalyAreaScale;
import com.lmis.pos.common.dao.BaseExcelMapper;

/**
 * 
 *@author jinggong.li
 *@date 2018年6月22日  
 * @param <T>
 */
@Mapper
@Repository
public interface PosPlanWareAnalyMapper<T extends PosPlanWareAnalyAreaScale> extends BaseExcelMapper{
    
	/**
     * 查询
     * @param posWhsOutPlanAreaScale
     * @return
     */
	public List<?>  selectPosPlanWareAnalyAreaScale(PosPlanWareAnalyAreaScale posPlanWareAnalyAreaScale);
	
	/**
	 * 查询仓库名称
	 * @param posPlanWareAnalyAreaScale
	 * @return
	 */
	public List<?>  selectWhsallocate();
	
	
	public List<?> exportPosPlanWareAnalyAreaScale(PosPlanWareAnalyAreaScale posPlanWareAnalyAreaScale);
}
