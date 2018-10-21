package com.lmis.pos.skuBase.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.pos.skuBase.model.PosSkuBase;

/** 
 * @ClassName: PosSkuBaseMapper
 * @Description: TODO(DAO映射接口)
 * @author codeGenerator
 * @date 2018-06-11 16:43:53
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface PosSkuBaseMapper {
    /**
     * 查询商品信息
     * @param posSkuBase
     * @return
     */
    List<PosSkuBase> executeSelect(PosSkuBase posSkuBase);
}
