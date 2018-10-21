package com.jumbo.dao.pda;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.mongodb.StaCartonLine;
import com.jumbo.wms.model.mongodb.StaCartonLineCommand;


/**
 * 
 * @author lizaibiao
 * 
 */
@Transactional
public interface StaCartonLineDao extends GenericEntityDao<StaCartonLine, Long> {

    /**
     * 查询LIST
     */
    @NativeQuery(model = StaCartonLine.class)
    List<StaCartonLine> getByCid(@QueryParam("cId") long cId);

    /**
     * 根据货箱ID获取货箱明细
     * 
     * @param cartonId
     * @return
     */
    @NamedQuery
    List<StaCartonLine> getCartonLineByCartonId(@QueryParam(value = "cartonId") Long cartonId);


    /**
     * 根据货箱ID获取货箱明细2
     * 
     * @param cartonId
     * @return
     */
    @NativeQuery
    List<StaCartonLineCommand> getCartonLineByCartonId2(@QueryParam(value = "cartonId") Long cartonId, RowMapper<StaCartonLineCommand> rowMapper);

    /**
     * 根据货箱ID获取货箱明细3
     * 
     * @param cartonId
     * @return
     */
    @NativeQuery
    List<StaCartonLineCommand> getCartonLineByCartonId3(@QueryParam(value = "cartonId") Long cartonId, RowMapper<StaCartonLineCommand> rowMapper);


    /**
     * 根据货箱ID获取货箱明细
     * 
     * @param cartonId
     * @return
     */
    @NativeQuery
    List<StaCartonLine> findRecommendFaildCartonLine(@QueryParam(value = "cartonId") Long cartonId, RowMapper<StaCartonLine> rowMapper);

    @NativeQuery
    StaCartonLine findStaCartonLineByCid(@QueryParam(value = "cId") List<Long> id, RowMapper<StaCartonLine> rowMapper);

    @NativeQuery
    StaCartonLine findStaCartonLineBySkuId(@QueryParam(value = "cId") List<Long> id, @QueryParam(value = "skuId") Long skuId, RowMapper<StaCartonLine> rowMapper);

    @NativeQuery
    StaCartonLine findStaQtyByCid(@QueryParam(value = "staId") Long id, @QueryParam(value = "skuId") Long skuId, RowMapper<StaCartonLine> rowMapper);

    @NativeQuery
    StaCartonLine findQtyByStaId(@QueryParam(value = "staId") Long id, @QueryParam(value = "skuId") Long skuId, RowMapper<StaCartonLine> rowMapper);

}
