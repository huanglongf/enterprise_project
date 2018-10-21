package com.lmis.pos.poSplitCollect.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.pos.common.model.PosPurchaseOrderMain;
import com.lmis.pos.poSplitCollect.model.PosSplitAllocateTableOne;

/** 
 * @ClassName: PosPurchaseOrderMainMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-30 13:41:07
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PoSplitCollectOrderMainMapper<T extends PosPurchaseOrderMain> extends BaseMapper<T> {
    
    /**
     * 根据前端条件查询拆分汇总表格1数据
     * @param posPurchaseOrderMain
     * @return
     */
    public PosSplitAllocateTableOne selectPoOrderMainCollect(PosPurchaseOrderMain posPurchaseOrderMain);
	
}
