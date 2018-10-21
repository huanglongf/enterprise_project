/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.wms.manager.warehouse;



import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseDistrictType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;


public interface WareHouseLocationManager extends BaseManager {
    String LOCATION_CODE = "CODE";
    String LOCATION_BAR_CODE = "BARCODE";
    String SKU_CODE = "CODE";
    String SKU_JM_CODE = "JMCODE";
    String SKU_BAR_CODE = "BARCODE";
    String SKU_KEYPROPERTIES = "KEYPROPERTIES";
    String SKU_NAME = "NAME";
    String SKU_ID = "ID";

    /**
     * 更新
     * 
     * @param entity 更新库区
     * @param locations 新增加的库位
     */
    void updateWarehouseDistrict(WarehouseDistrict entity, List<WarehouseLocation> locations);

    /**
     * check指定库位是否能被禁用,找不到库存则可以被禁用
     * 
     * @param locationId
     * @return
     */
    boolean checkInventoryByDistrictOrLocation(Long districtId, Long locationId);

    /**
     * check location.code/barCode exist in the db, not in=true,else false
     * 
     * @param locationCodeCache location.Map<code,id>
     * @param location
     * @param errors
     * @param index
     * @param local 批量操作,是否是本地cache,数据库cache校验时=false
     * @return
     */
    boolean locationCodeBarCodeValidate(Map<String, Map<String, Long>> locationMapCache, WarehouseLocation location, List<BusinessException> errors, Long index, boolean local);

    /**
     * Get all the location.map<code,id>,location.map<barCode,id> by warehouseId
     * 
     * @param warehouseId
     * @return
     */
    Map<String, Map<String, Long>> findLocationCodeMapByWarehouse(Long warehouseId);

    /**
     * 库区的所有库位
     * 
     * @param id
     * @return
     */
    Pagination<WarehouseLocation> findLocationsListByDistrictId(int start, int pageSize, Long id, Sort[] sorts);

    /**
     * 库区的所有库位,包含绑定的作业类型的数量
     * 
     * @param id
     * @return
     */
    Pagination<WarehouseLocation> findLocationsTransCountListByDistrictId(int start, int pageSize, Long id, Sort[] sorts);
    
    Pagination<WarehouseLocation> findLocationsTransCountListByDistrictAndLocId(int start, int pageSize, Long id, Long locId, Sort[] sorts);

    /**
     * 库位绑定作业类型
     * 
     * @param locationIds 库位id
     * @param transIds 作业类型id
     */
    void createTransactionTypeForLocations(List<Long> locationIds, List<Long> transIds);

    /**
     * 库位撤销绑定作业类型
     * 
     * @param locationIds 库位id
     * @param transIds 作业类型id
     */
    void deleteTransactionTypeForLocations(List<Long> locationIds, List<Long> transIds);

    /**
     * 根据库区绑定库位的作业作业类型
     * 
     * @param districtId 库位id
     * @param transIds 作业类型id
     */
    void createTransactionTypeByDistrict(Long districtId, List<Long> transIds);

    /**
     * 根据库区撤销库位的作业作业类型
     * 
     * @param districtId 库位id
     * @param transIds 作业类型id
     */
    void deleteTransactionTypeByDistrict(Long districtId, List<Long> transIds);

    /**
     * 更新库位的容积容积率
     * 
     * @param locations
     */
    void updateCapacityOfLocations(List<WarehouseLocation> locations);

    /**
     * 修改库区的拣货模式
     * 
     * @param list
     */
    void updataWarehouseLocationListForSortingMode(List<WarehouseLocation> list);

    /**
     * 根据组织ID获取库区
     * 
     * @param ouId
     * @return
     */
    List<WarehouseDistrict> findDistrictListByOuId(Long ouId);

    List<WarehouseDistrict> findDistrictListByType(Long ouId, WarehouseDistrictType type);

    List<WarehouseDistrict> findDistrictListByOuId(Long ouId, Sort[] sort);

    WarehouseDistrict findDistrictById(Long id);

    /**
     * 
     * 方法说明：根据库位编码,状态,锁定情况验证库位
     * 
     * @author LuYingMing
     * @date 2016年8月31日 下午8:28:51
     * @param appointStorage
     * @param ouId
     * @return
     */
    String verifyLocationByCondition(String appointStorage,Long ouId);
}
