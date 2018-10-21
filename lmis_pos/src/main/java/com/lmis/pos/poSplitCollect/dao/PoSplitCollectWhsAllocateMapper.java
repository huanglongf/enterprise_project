package com.lmis.pos.poSplitCollect.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.pos.common.dao.BaseExcelMapper;
import com.lmis.pos.common.model.PosPurchaseOrderMain;
import com.lmis.pos.common.model.PosWhsAllocate;
import com.lmis.pos.poSplitCollect.model.ViewPosSplitAllocateTableFour;
import com.lmis.pos.poSplitCollect.model.ViewPosSplitAllocateTableThree;
import com.lmis.pos.poSplitCollect.model.ViewPosSplitAllocateTableTwo;

/**
 * @ClassName: PosWhsAllocateMapper
 * @Description: TODO(PO拆分汇总结果DAO映射接口)
 * @author codeGenerator
 * @date 2018-05-30 13:26:28
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PoSplitCollectWhsAllocateMapper<T extends PosWhsAllocate> extends BaseExcelMapper<T>{

    /**
     * 根据传入的参数查询表格2的数据
     * 
     * @param posPurchaseOrderMain
     *            页面参数
     * @return
     */
    ViewPosSplitAllocateTableTwo getTableTwoData(PosPurchaseOrderMain posPurchaseOrderMain);

    /**
     * 根据传入的参数查询表格3的数据
     * 
     * @param posPurchaseOrderMain
     *            页面参数
     * @return
     */
    List<ViewPosSplitAllocateTableThree> getTableThreeData(PosPurchaseOrderMain posPurchaseOrderMain);

    /**
     * 根据传入的参数查询表格4的数据
     * 
     * @param posPurchaseOrderMain
     * @return
     */
    List<ViewPosSplitAllocateTableFour> getTableFourData(PosPurchaseOrderMain posPurchaseOrderMain);

}
