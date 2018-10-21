package com.lmis.pos.whsSurplusSc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.whsSurplusSc.model.PosWhsSurplusSc;

/** 
 * @ClassName: PosWhsSurplusScMapper
 * @Description: TODO(仓库剩余入库能力分析DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-30 17:16:40
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosWhsSurplusScMapper<T extends PosWhsSurplusSc> extends BaseMapper<T> {
	
    /**
     * 获取当前库的剩余能力
     * @author xuyisu
     * @date  2018年5月31日
     * 
     * @param batchid
     * @param type
     * @param whscode
     * @return
     */
    List<PosWhsSurplusSc> getPosWhsSurplusScInfo(@Param("dateid") String batchid,@Param("type") String type,@Param("whscode") String whscode);

	List<T> retrieveByOther(T t);

	int updatePlannedInAbilityForFirstDay(T t);

	int updatePlannedInAbilityForDayRight(T t);

	int updatePlannedInAbilityForOtherDay(T t);

    List<T> selectPosWhsSurplusScList(T t);

    Map selectPosWhsSurplusSumBySku(T t);

	int flushThisWhsOtherBuThisDayInJobsEnable(T t);
}
