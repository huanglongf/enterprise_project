package com.lmis.pos.whsRateLoadin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.whs.model.PosWhs;
import com.lmis.pos.whsAllocate.model.PosWhsAllocateRate;
import com.lmis.pos.whsRateLoadin.model.PosWhsRateLoadin;
import com.lmis.pos.whsRateLoadin.model.PosWhsRateLoadinProportion;

/** 
 * @ClassName: PosWhsRateLoadinMapper
 * @Description: TODO(新货商品分配仓库比例分析DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-30 17:12:37
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosWhsRateLoadinMapper<T extends PosWhsRateLoadin> extends BaseMapper<T> {
	
    /**
     * @Title: listPosWhsRateLoadin
     * @Description: TODO(获取新品分仓比率)
     * @param vnumber
     * @param size
     * @param posWhs
     * @return: List<PosWhsAllocateRate>
     * @author: Ian.Huang
     * @date: 2018年6月19日 下午11:45:31
     */
    List<PosWhsAllocateRate> listPosWhsRateLoadin(@Param("vnumber") String vnumber, @Param("size") String size, @Param("posWhs") List<PosWhs> posWhs);
  
    /**
     * 仓库推荐比例分析查询
     * @return
     */
    List<PosWhsRateLoadinProportion> selectPosWhsRateLoadinProportion(PosWhsRateLoadinProportion posWhsRateLoadinProportion);
}
