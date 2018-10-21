package com.jumbo.dao.warehouse;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;

import java.util.List;

import loxia.dao.GenericEntityDao;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.PickingListPackageCommand;

/**
 * O2O箱操作DAO
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
public interface PickingListPackageDao extends GenericEntityDao<PickingListPackage, Long> {
	
	/**
	 * 通过配合单ID获得O2O+QS配货清单箱
	 * @param plId
	 * @return
	 */
	@NamedQuery
	PickingListPackage findByPickingListId(@QueryParam(value = "plId") Long plId);
	
	/**
	 * 通过快递单号获取O2O+QS配货清单箱
	 * @param trackingNo
	 * @return
	 */
	@NamedQuery
	PickingListPackage findByTrackingNo(@QueryParam(value = "trackingNo") String trackingNo);
	
	@NamedQuery
    PickingListPackage findByTrackingNoAndStatus(@QueryParam(value = "trackingNo") String trackingNo, @QueryParam(value = "status") DefaultStatus status);

    @NamedQuery
    List<PickingListPackage> findByPlIdAndStatus(@QueryParam(value = "plId") Long plId, @QueryParam(value = "status") DefaultStatus status);
    
    @NamedQuery
    PickingListPackage findByIdAndStatus(@QueryParam(value = "plpId") Long plpId, @QueryParam(value = "plId") Long plId, @QueryParam(value = "status") DefaultStatus status);
    
    @NativeQuery
    List<PickingListPackageCommand> findO2OQSPackListForVerifyByPickingList(@QueryParam(value = "plcode") String plcode, @QueryParam(value = "plId") Long plId, @QueryParam(value = "status") Integer[] status,
            @QueryParam(value = "pickingMode") Integer pickingMode, @QueryParam(value = "checkMode") Integer checkMode, @QueryParam(value = "ouId") Long ouId, Sort[] sorts, RowMapper<PickingListPackageCommand> rowMapper);

    /**
     * 根据快递单号获取O2O+QS批次信息
     * @param wh_ou_id
     * @param trackingNo
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    PickingListPackageCommand findPickingListPackageByTrackingNo(@QueryParam("trackingNo") String trackingNo, BeanPropertyRowMapper<PickingListPackageCommand> beanPropertyRowMapper);

}
