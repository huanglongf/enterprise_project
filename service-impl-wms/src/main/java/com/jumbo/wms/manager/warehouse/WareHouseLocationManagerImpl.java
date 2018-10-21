/**
 * EventObserver * Copyright (c) 2010 Jumbomart All Rights Reserved.
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BaseRowMapper;
import loxia.utils.PropListCopyable;
import loxia.utils.PropertyUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.automaticEquipment.PopUpAreaDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.WarehouseDistrictDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.automaticEquipment.PopUpArea;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseDistrictType;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

@Transactional
@Service("wareHouseLocationManager")
public class WareHouseLocationManagerImpl extends BaseManagerImpl implements WareHouseLocationManager {

    /**
     * 
     */
    private static final long serialVersionUID = 6106368271174613386L;
    @Autowired
    private WarehouseDistrictDao warehouseDistrictDao;
    @Autowired
    private WarehouseLocationDao warehouseLocationDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private PopUpAreaDao popUpAreaDao;

    public void updateWarehouseDistrict(WarehouseDistrict from, List<WarehouseLocation> locations) {
        if (!from.getIsAvailable() && checkInventoryByDistrictOrLocation(from.getId(), null)) {
            throw new BusinessException(ErrorCode.WH_DISTRICT_HAS_INVENTORY, new Object[] {from.getName()});
        }
        List<BusinessException> errors = new ArrayList<BusinessException>();
        WarehouseDistrict entity = warehouseDistrictDao.getByPrimaryKey(from.getId());
        try {
            PropertyUtil.copyProperties(from, entity, new PropListCopyable(new String[] {"name", "isAvailable", "type"}));
        } catch (Exception e) {
            log.error("", e);
            log.error("Copy Bean properties error for WarehouseDistrict");
            throw new RuntimeException("Copy Bean properties error for WarehouseDistrict");
        }
        entity = warehouseDistrictDao.save(entity);
        List<WarehouseLocation> updateLocations = from.getLocations();
        ChooseOption chooseOption = chooseOptionDao.findByCategoryCodeAndKey("whLocationDiff", "wh");
        Map<String, Map<String, Long>> locationMapCache = findLocationCodeMapByWarehouse(from.getOu().getId());
        Map<String, Map<String, Long>> localCache = new HashMap<String, Map<String, Long>>();
        localCache.put(LOCATION_CODE, new HashMap<String, Long>());
        localCache.put(LOCATION_BAR_CODE, new HashMap<String, Long>());
        Long index = 0L;
        // update the locations of the current district
        for (WarehouseLocation location : updateLocations) {
            index++;
            if (locationCodeBarCodeValidate(locationMapCache, location, errors, index, false)) {
                if (!location.getIsAvailable() && checkInventoryByDistrictOrLocation(null, location.getId())) {
                    log.debug("......The location is not available, but there is inventory,");
                    errors.add(new BusinessException(ErrorCode.WH_LOCATION_HAS_INVENTORY, new Object[] {location.getCode(), location.getBarCode()}));
                    continue;
                }
                WarehouseLocation get = warehouseLocationDao.getByPrimaryKey(location.getId());
                try {
                    PropertyUtil.copyProperties(location, get, new PropListCopyable(new String[] {"code", "manualCode", "sysCompileCode", "dimX", "dimY", "dimZ", "dimC", "barCode", "isAvailable", "memo"}));
                } catch (Exception e) {
                    log.error("", e);
                    log.error("Copy Bean properties error for WarehouseLocation");
                    throw new RuntimeException("Copy Bean properties error for WarehouseDistrict");
                }
                if (errors.isEmpty()) {
                    get.setLastModifyTime(new Date());
                    warehouseLocationDao.save(get);
                }
            }
        }
        // save the new locations
        if (locations != null && !locations.isEmpty()) {
            for (WarehouseLocation location : locations) {
                index++;
                if (location == null || !StringUtils.hasLength(location.getCode())) {
                    continue;
                }
                if (!StringUtils.hasText(location.getManualCode())) {
                    location.setCode(from.getCode() + location.getCode());
                    // location.setSysCompileCode(from.getCode() + location.getSysCompileCode());
                } /*
                   * else { location.setSysCompileCode(from.getCode() + location.getCode()); }
                   */


                if (0 == chooseOption.getSortNo()) {
                    location.setSysCompileCode(from.getCode() + location.getCode());
                } else {
                    location.setSysCompileCode(location.getCode());
                }


                if (!locationCodeBarCodeValidate(localCache, location, errors, index, true) | !locationCodeBarCodeValidate(locationMapCache, location, errors, index, false)) {
                    continue;
                }

                if (location.getPopUpAreaCode() != null && !"".equals(location.getPopUpAreaCode())) {
                    PopUpArea popUpArea = popUpAreaDao.getPopUpAreaByCodeAndOuid(location.getPopUpAreaCode());
                    if (popUpArea != null) {
                        location.setPopUpArea(popUpArea);
                    } else {
                        errors.add(new BusinessException(ErrorCode.WH_LOCATION_POPUPAREACODE_ERROR, new Object[] {location.getPopUpAreaCode()}));
                    }
                }
                if (errors.isEmpty()) {
                    location.setDistrict(entity);
                    location.setOu(entity.getOu());
                    location.setIsLocked(false);
                    location.setIsAvailable(true);
                    location.setLastModifyTime(new Date());
                    location.setCreateTime(new Date());
                    warehouseLocationDao.save(location);
                }
            }
        }
        businessExceptionPost(errors);
    }

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
    public boolean locationCodeBarCodeValidate(Map<String, Map<String, Long>> locationMapCache, WarehouseLocation location, List<BusinessException> errors, Long index, boolean local) {
        if (locationMapCache == null || locationMapCache.isEmpty()) {
            return true;
        }
        return locationCodeValidate(locationMapCache.get(LOCATION_CODE), location, errors, index, local) & locationBarCodeValidate(locationMapCache.get(LOCATION_BAR_CODE), location, errors, index, local);
    }

    private boolean locationBarCodeValidate(Map<String, Long> barCodeCache, WarehouseLocation location, List<BusinessException> errors, Long index, boolean local) {
        if (barCodeCache == null || barCodeCache.isEmpty()) return true;
        boolean rs = true;
        // new location, if cache contains the key, then false
        if (location.getId() == null) {
            if (StringUtils.hasLength(location.getBarCode()) && barCodeCache.containsKey(location.getBarCode().toUpperCase())) {
                if (local) {
                    errors.add(new BusinessException(ErrorCode.WH_LOCATION_BAR_CODE_EXISTS_REDUPLICATE, new Object[] {index, location.getBarCode(), barCodeCache.get(location.getBarCode())}));
                } else {
                    errors.add(new BusinessException(ErrorCode.WH_LOCATION_BAR_CODE_EXISTS, new Object[] {index, location.getBarCode()}));
                }

                rs = false;
            }
        } else {
            // update location, if not same id, then false
            if (barCodeCache.containsKey(location.getBarCode().toUpperCase()) && StringUtils.hasLength(location.getBarCode()) && !location.getId().equals(barCodeCache.get(location.getBarCode().toUpperCase()))) {
                errors.add(new BusinessException(ErrorCode.WH_LOCATION_BAR_CODE_EXISTS, new Object[] {index, location.getBarCode()}));
                rs = false;
            }
        }
        return rs;
    }

    private boolean locationCodeValidate(Map<String, Long> codeCache, WarehouseLocation location, List<BusinessException> errors, Long index, boolean local) {
        if (codeCache == null || codeCache.isEmpty()) return true;
        boolean rs = true;
        // new location, if cache contains the key, then false
        if (location.getId() == null) {
            if (codeCache.containsKey(location.getCode().toUpperCase())) {
                if (local) {
                    errors.add(new BusinessException(ErrorCode.WH_LOCATION_SYSCOMPILECODE_EXISTS_REDUPLICATE, new Object[] {index, location.getCode(), codeCache.get(location.getCode())}));
                } else {
                    errors.add(new BusinessException(ErrorCode.WH_LOCATION_SYSCOMPILECODE_EXISTS, new Object[] {index, location.getCode()}));
                }
                rs = false;
            }
        } else {
            // update location, if not same id, then false
            if (codeCache.containsKey(location.getCode().toUpperCase()) && !location.getId().equals(codeCache.get(location.getCode().toUpperCase()))) {
                errors.add(new BusinessException(ErrorCode.WH_LOCATION_SYSCOMPILECODE_EXISTS, new Object[] {index, location.getCode()}));
                rs = false;
            }
        }
        return rs;
    }

    /**
     * Get all the location.map<code,id>,location.map<barCode,id> by warehouseId
     * 
     * @param warehouseId
     * @return
     */
    public Map<String, Map<String, Long>> findLocationCodeMapByWarehouse(Long warehouseId) {
        return warehouseLocationDao.findLocationCodeMapByWarehouseSql(warehouseId, new BaseRowMapper<Map<String, Map<String, Long>>>() {
            private Map<String, Map<String, Long>> result = new HashMap<String, Map<String, Long>>();

            public Map<String, Map<String, Long>> mapRow(ResultSet rs, int rowNum) throws SQLException {
                Map<String, Long> codeMap = result.get(LOCATION_CODE), barCodeMap = result.get(LOCATION_BAR_CODE);
                Long id = getResultFromRs(rs, "ID", Long.class);
                if (result.get(LOCATION_CODE) == null) {
                    codeMap = new HashMap<String, Long>();
                }
                codeMap.put(getResultFromRs(rs, LOCATION_CODE, String.class).toUpperCase(), id);
                if (result.get(LOCATION_BAR_CODE) == null) {
                    barCodeMap = new HashMap<String, Long>();
                }
                String barCode = getResultFromRs(rs, LOCATION_BAR_CODE, String.class);
                if (StringUtils.hasLength(barCode)) {
                    barCodeMap.put(barCode.toUpperCase(), id);
                }
                result.put(LOCATION_CODE, codeMap);
                result.put(LOCATION_BAR_CODE, barCodeMap);
                return result;
            }

        });
    }

    /**
     * true代表有库存，false代表无
     * 
     * @param districtId
     * @param locationId
     * @return
     */
    public boolean checkInventoryByDistrictOrLocation(Long districtId, Long locationId) {
        if (locationId != null) {
            return inventoryDao.findInventoryByLocation(locationId) != null;
        } else {
            return inventoryDao.findInventoryByDistrict(districtId) != null;
        }
    }

    public Pagination<WarehouseLocation> findLocationsListByDistrictId(int start, int pageSize, Long id, Sort[] sorts) {
        Pagination<WarehouseLocation> page = warehouseLocationDao.findLocationsListByDistrictId(start, pageSize, id, sorts);
        List<WarehouseLocation> pageItem = new ArrayList<WarehouseLocation>();
        for (WarehouseLocation loc : page.getItems()) {
            WarehouseLocation tmpLoc = new WarehouseLocation();
            try {
                PropertyUtil.copyProperties(loc, tmpLoc);
                tmpLoc.setTransactionTypes(null);
                tmpLoc.setDistrict(null);
                tmpLoc.setOu(null);
                if (loc.getPopUpArea() != null) {
                    tmpLoc.setPopUpAreaCode(loc.getPopUpArea().getCode());
                    tmpLoc.setPopUpArea(null);
                }
                pageItem.add(tmpLoc);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        page.setItems(pageItem);
        return page;
    }

    /**
     * 库区的所有库位,包含绑定的作业类型的数量
     * 
     * @param id
     * @return
     */
    public Pagination<WarehouseLocation> findLocationsTransCountListByDistrictId(int start, int pageSize, Long id, Sort[] sorts) {
        return warehouseLocationDao.findLocationsTransCountListByDistrictId(start, pageSize, id, sorts, new BeanPropertyRowMapper<WarehouseLocation>(WarehouseLocation.class));
    }

    /** 
	 *
	 */
    @Override
    public Pagination<WarehouseLocation> findLocationsTransCountListByDistrictAndLocId(int start, int pageSize, Long id, Long locId, Sort[] sorts) {
        return warehouseLocationDao.findLocationsTransCountListByDistrictAndLocId(start, pageSize, id, locId, sorts, new BeanPropertyRowMapper<WarehouseLocation>(WarehouseLocation.class));
    }

    /**
     * 库位绑定作业类型
     * 
     * @param locationIds 库位id
     * @param transIds 作业类型id
     */
    public void createTransactionTypeForLocations(List<Long> locationIds, List<Long> transIds) {
        warehouseLocationDao.createTransactionTypeForLocations(locationIds, transIds);
    }

    /**
     * 库位撤销绑定作业类型
     * 
     * @param locationIds 库位id
     * @param transIds 作业类型id
     */
    public void deleteTransactionTypeForLocations(List<Long> locationIds, List<Long> transIds) {
        warehouseLocationDao.deleteTransactionTypeForLocations(locationIds, transIds);
    }

    public void createTransactionTypeByDistrict(Long districtId, List<Long> transIds) {
        warehouseLocationDao.createTransactionTypeByDistrict(districtId, transIds);
    }

    public void deleteTransactionTypeByDistrict(Long districtId, List<Long> transIds) {
        warehouseLocationDao.deleteTransactionTypeByDistrict(districtId, transIds);
    }

    public void updateCapacityOfLocations(List<WarehouseLocation> locations) {
        if (locations != null && !locations.isEmpty()) {
            for (WarehouseLocation location : locations) {
                WarehouseLocation entity = warehouseLocationDao.getByPrimaryKey(location.getId());
                entity.setCapacity(location.getCapacity() <= 0 ? null : location.getCapacity());
                entity.setCapaRatio(location.getCapaRatio());
                entity.setLastModifyTime(new Date());
                warehouseLocationDao.save(entity);
            }
        }
    }

    public void updataWarehouseLocationListForSortingMode(List<WarehouseLocation> list) {
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            WarehouseLocation whl = list.get(i);
            WarehouseLocation whl2 = warehouseLocationDao.getByPrimaryKey(whl.getId());
            whl2.setSortingMode(whl.getSortingMode());
            whl2.setLastModifyTime(new Date());
            warehouseLocationDao.save(whl2);
        }
    }

    public List<WarehouseDistrict> findDistrictListByOuId(Long ouId) {
        List<WarehouseDistrict> list = warehouseDistrictDao.findDistrictListByOuId(ouId, new Sort[] {new Sort("i.code asc")});
        List<WarehouseDistrict> tmplist = new ArrayList<WarehouseDistrict>();
        for (WarehouseDistrict d : list) {
            WarehouseDistrict tmpd = new WarehouseDistrict();
            try {
                PropertyUtil.copyProperties(d, tmpd);
            } catch (Exception e) {
                log.error("", e);
            }
            tmpd.setLocations(null);
            tmplist.add(tmpd);
        }
        return tmplist;
    }

    public List<WarehouseDistrict> findDistrictListByType(Long ouId, WarehouseDistrictType type) {
        return warehouseDistrictDao.findDistrictListByType(ouId, type, new Sort[] {new Sort("i.code asc")});
    }

    @Transactional(readOnly = true)
    public List<WarehouseDistrict> findDistrictListByOuId(Long ouId, Sort[] sort) {
        List<WarehouseDistrict> list = warehouseDistrictDao.findDistrictListByOuId(ouId, sort);
        List<WarehouseDistrict> tmplist = new ArrayList<WarehouseDistrict>();
        for (WarehouseDistrict d : list) {
            WarehouseDistrict tmpd = new WarehouseDistrict();
            BeanUtils.copyProperties(d, tmpd);
            tmpd.setLocations(null);
            tmplist.add(tmpd);
        }
        return tmplist;
    }

    @Transactional(readOnly = true)
    public WarehouseDistrict findDistrictById(Long id) {
        WarehouseDistrict wd = warehouseDistrictDao.getByPrimaryKey(id);
        WarehouseDistrict tmp = new WarehouseDistrict();
        try {
            PropertyUtil.copyProperties(wd, tmp);
            tmp.setLocations(null);
            tmp.setOu(null);
        } catch (Exception e) {
            log.error("", e);
        }
        return tmp;
    }

    /**
     * 
     * @author LuYingMing
     * @date 2016年8月31日 下午8:29:40
     * @see com.jumbo.wms.manager.warehouse.WareHouseLocationManager#verifyLocationByCondition(java.lang.String,
     *      java.lang.Long)
     */
    @Override
    public String verifyLocationByCondition(String appointStorage, Long ouId) {
        String result = null;
        WarehouseLocation entity = warehouseLocationDao.findwhLocationByCode(appointStorage, ouId);
        if (null != entity) {
            WarehouseLocation entity1 = warehouseLocationDao.findByLocationCode(appointStorage, ouId);
            if (null != entity1) {
                WarehouseLocation entity2 = warehouseLocationDao.findLocationByCode(appointStorage, ouId);
                if (null != entity2) {
                    result = "SUCCESS";
                } else {
                    result = Constants.LOCATION_LOCKED;
                }
            } else {
                result = Constants.LOCATION_STATUS_INVALID;
            }
        } else {
            result = Constants.LOCATION_CODE_NOT_EXIST;
        }
        return result;
    }
}
