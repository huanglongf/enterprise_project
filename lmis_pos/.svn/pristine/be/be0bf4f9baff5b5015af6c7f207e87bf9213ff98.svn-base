package com.lmis.pos.whsRateFillin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.whs.model.PosWhs;
import com.lmis.pos.whsAllocate.model.PosWhsAllocateRate;
import com.lmis.pos.whsRateFillin.model.PosWhsRateFillin;

/** 
 * @ClassName: PosWhsRateFillinMapper
 * @Description: TODO(补货商品分配仓库比例分析DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-30 17:03:06
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosWhsRateFillinMapper<T extends PosWhsRateFillin> extends BaseMapper<T> {
	
    /**
     * @Title: listPosWhsRateFillin
     * @Description: TODO(获取补货分仓比率)
     * @param skuCode
     * @param posWhs
     * @return: List<PosWhsAllocateRate>
     * @author: Ian.Huang
     * @date: 2018年6月19日 下午11:41:55
     */
    List<PosWhsAllocateRate> listPosWhsRateFillin(@Param("skuCode") String skuCode, @Param("posWhs") List<PosWhs> posWhs);
    
}
