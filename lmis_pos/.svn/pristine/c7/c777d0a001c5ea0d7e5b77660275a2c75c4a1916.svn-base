package com.lmis.pos.whsOutPlan.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.common.dao.BaseExcelMapper;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlan;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlanAreaScale;
import com.lmis.pos.whsOutPlan.model.PosWhsOutPlanLog;

/**
 * 
 *@author jinggong.li
 *@date 2018年6月22日  
 * @param <T>
 */
@Mapper
@Repository
public interface PosWhsOutAreaScaleMapper<T extends PosWhsOutPlanAreaScale> extends BaseExcelMapper{
    /**
     * 查询
     * @param posWhsOutPlanAreaScale
     * @return
     */
	public List<?>  selectPosWhsOutAreaScale(PosWhsOutPlanAreaScale posWhsOutPlanAreaScale);
	
	
	// 导入数据
    public Integer savePosWhsOutAreaScaleInfo(List<Map<String, Object>> dataList);
  
}
