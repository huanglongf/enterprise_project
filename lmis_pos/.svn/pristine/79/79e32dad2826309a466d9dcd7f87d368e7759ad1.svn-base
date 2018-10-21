package com.lmis.pos.skuMaster.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.pos.common.dao.BaseExcelMapper;
import com.lmis.pos.skuMaster.model.PosSkuMaster;


@Mapper
@Repository
public interface PosSkuMasterMapper extends BaseExcelMapper{
    
    List<PosSkuMaster> getPosSkuMasterInfo(String prodCd);
    
    // 清空表中数据
    Integer deleteAll();
    
    // 导入数据
    Integer savePosSkuMasterInfo(List<Map<String, Object>> dataList);
    
    // 查询表中数据条数
    Integer queryCount();
}
