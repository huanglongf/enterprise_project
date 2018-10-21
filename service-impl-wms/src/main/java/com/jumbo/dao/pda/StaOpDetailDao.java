package com.jumbo.dao.pda;

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pda.StaOpDetail;
import com.jumbo.wms.model.pda.StaOpDetailCommand;


/**
 * 
 * @author lizaibiao
 * 
 */
@Transactional
public interface StaOpDetailDao extends GenericEntityDao<StaOpDetail, Long> {

    /**
     * 根据作业单Id,箱号code,组织id来获取收货明细（残次品）
     */

    @NativeQuery
    List<StaOpDetail> findByNoGoodsOpDetail(@QueryParam(value = "staId") Long staId, @QueryParam(value = "carCode") String carCode, @QueryParam(value = "ouId") Long ouId, RowMapper<StaOpDetail> s);

    /**
     * 根据作业单Id,箱号code,组织id来获取上架明细
     */

    @NativeQuery
    List<StaOpDetail> findByGoodsOpDetail2(@QueryParam(value = "staId") Long staId, @QueryParam(value = "carCode") String carCode, @QueryParam(value = "ouId") Long ouId, RowMapper<StaOpDetail> s);

    /**
     * 查询出该仓库下此箱号和此作业单下所有上架作业单操作明细
     */
    @NativeQuery
    List<StaOpDetail> findAllByStaIdCarCode(@QueryParam(value = "staId") Long staId, @QueryParam(value = "carCode") String carCode, @QueryParam(value = "ouId") Long ouId, RowMapper<StaOpDetail> s);

    @NativeQuery
    List<StaOpDetail> findAllByStaIdCarCode2(@QueryParam(value = "staId") Long staId, @QueryParam(value = "carCode") String carCode, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "skuId") Long skuId,
            @QueryParam(value = "locationCode") String locationCode, RowMapper<StaOpDetail> s);


    /**
     * 查询出该仓库下此箱号和此作业单下所有上架作业单操作明细（残次）
     */
    @NativeQuery
    List<StaOpDetail> findAllDmgCodeByStaIdCarCode(@QueryParam(value = "staId") Long staId, @QueryParam(value = "carCode") String carCode, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "locationCode") String locationCode,
            RowMapper<StaOpDetail> s);

    @NativeQuery
    List<StaOpDetailCommand> findGoodsOpDetailByStaId(@QueryParam(value = "staId") Long staId, @QueryParam(value = "ouId") Long ouId, RowMapper<StaOpDetailCommand> s);


    @NativeQuery
    List<StaOpDetailCommand> findGoodsOpDetailByStaIdAnd(@QueryParam(value = "staId") Long staId, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "cartonCode") String cartonCode, RowMapper<StaOpDetailCommand> s);


    @NativeQuery
    List<StaOpDetailCommand> findSnListByStaIdAndSkuId(@QueryParam(value = "staId") Long staId, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "cartonCode") String cartonCode, @QueryParam(value = "skuId") Long skuId,
            @QueryParam(value = "expDate") Date expDate, RowMapper<StaOpDetailCommand> s);

    @NativeUpdate
    public void deleteStaOpDetailLog(@QueryParam("staId") Long staId);

    @NativeUpdate
    public void deleteStaOpDetail(@QueryParam("staId") Long staId, @QueryParam("carId") Long carId, @QueryParam("ouId") Long ouId);



    @NativeQuery
    List<StaOpDetailCommand> StaOpDetailByCarId(@QueryParam("staId") Long staId, @QueryParam("carId") Long carId, @QueryParam("ouId") Long ouId, RowMapper<StaOpDetailCommand> s);

    @NativeQuery
    StaOpDetail findOpDetailBySn(@QueryParam(value = "sn") String sn, RowMapper<StaOpDetail> s);

    @NativeQuery
    StaOpDetail findByNo(@QueryParam(value = "staId") Long staId, @QueryParam(value = "skuId") Long skuId, @QueryParam(value = "userId") Long userId, @QueryParam(value = "ouId") Long ouId, RowMapper<StaOpDetail> s);

    @NativeQuery
    StaOpDetail findBySku(@QueryParam(value = "staId") Long staId, @QueryParam(value = "code") String code, @QueryParam(value = "userId") Long userId, @QueryParam(value = "ouId") Long ouId, RowMapper<StaOpDetail> s);

    @NativeQuery
    StaOpDetail findQtyBySta(@QueryParam(value = "code") String code, @QueryParam(value = "ouId") Long ouId, RowMapper<StaOpDetail> s);
}
