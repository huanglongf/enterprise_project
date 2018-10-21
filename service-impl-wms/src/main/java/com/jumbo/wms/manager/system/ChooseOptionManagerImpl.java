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
package com.jumbo.wms.manager.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.defaultData.VmiRtoDao;
import com.jumbo.dao.warehouse.AdPackageDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.rmi.warehouse.vmi.VmiRto;
import com.jumbo.util.TimeHashMap;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.AdPackage;
import com.jumbo.wms.model.baseinfo.LicensePlate;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.command.WarehouseCommand;
import com.jumbo.wms.model.system.ChooseOption;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

@Transactional
@Service("chooseOptionManager")
public class ChooseOptionManagerImpl extends BaseManagerImpl implements ChooseOptionManager {

    private static final Logger log = LoggerFactory.getLogger(ChooseOptionManagerImpl.class);

    /**
     * 
     */
    private static final long serialVersionUID = -3548631174158597031L;

    static TimeHashMap<String, ChooseOption> categoryCodeAndKeyMap = new TimeHashMap<String, ChooseOption>();

    static TimeHashMap<String, List<ChooseOption>> categoryCodeMap = new TimeHashMap<String, List<ChooseOption>>();

    /**
     * 
     */
    @Autowired
    private ChooseOptionDao chooseOptionDao;

    @Autowired
    private VmiRtoDao vmiRtoDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private AdPackageDao adPackageDao;



    /**
     * 根据常量集编码categoryCode查找 ChooseOption List
     * 
     * @param categoryCode
     * @return
     */
    public List<ChooseOption> findOptionListByCategoryCode(String categoryCode) {
        String mapKey = categoryCode;
        List<ChooseOption> co = categoryCodeMap.get(mapKey);
        if (null == co) {
            co = chooseOptionDao.findOptionListByCategoryCode(categoryCode);
            categoryCodeMap.put(mapKey, co, 5 * 60 * 1000);
        }
        return co;
    }

    public List<String> findByCategoryCode(String categoryCode) {
        return chooseOptionDao.findByCategoryCodeA(categoryCode);
    }

    public List<VmiRto> initPumaToOrderCode() {
        return vmiRtoDao.initPumaToOrderCode(new BeanPropertyRowMapper<VmiRto>(VmiRto.class));
    }


    /**
     * 根据常量集编码categoryCode查找 ChooseOption List(esprit)
     * 
     * @param categoryCode
     * @return
     */
    @Override
    public List<ChooseOption> getChooseOptionByCodeEsprit(String categoryCode) {
        return chooseOptionDao.getChooseOptionByCodeEsprit(categoryCode, new BeanPropertyRowMapper<ChooseOption>(ChooseOption.class));
    }

    public ChooseOption findPdaCode(String pdaCode) {
        if (!StringUtils.hasText(pdaCode)) {
            throw new BusinessException(ErrorCode.PDA_IS_LIMIT, new Object[] {""});
        }
        ChooseOption co = chooseOptionDao.findByCategoryCodeAndKey(ChooseOption.CATEGORY_CODE_PDA_MACHINE_CODE, pdaCode);
        if (co == null) {
            throw new BusinessException(ErrorCode.PDA_IS_LIMIT, new Object[] {pdaCode});
        } else {
            return co;
        }
    }

    @Override
    public Map<String, String> getOptionByCategoryCode(String categoryCode) {
        Map<String, String> optionValue = new HashMap<String, String>();
        List<ChooseOption> chooseList = chooseOptionDao.findAllOptionListByCategoryCode(categoryCode);
        if (chooseList != null && chooseList.size() > 0) {
            for (ChooseOption c : chooseList) {
                optionValue.put(c.getOptionKey(), c.getOptionValue());
            }
        }
        return optionValue;
    }

    /**
     * 根据物流商code获取detail
     * 
     * @param expCode
     * @param rowMap
     * @return
     */
    public List<TransportatorCommand> findTransportator(String expCode, RowMapper<TransportatorCommand> rowMap) {
        return chooseOptionDao.findTransportator(expCode, rowMap);
    }

    /**
     * 根据物流商code获取detail
     */
    @Override
    public List<TransportatorCommand> findTransportator(String expCode) {
        return findTransportator(expCode, new BeanPropertyRowMapper<TransportatorCommand>(TransportatorCommand.class));
    }


    public String queryByOuId(Long ouId) {
        String msg = "";
        Warehouse warehouse = warehouseDao.getByOuId(ouId);
        if (null != warehouse) {
            if (null != warehouse.getIsTestWh() && warehouse.getIsTestWh()) {
                msg = "yes";
            } else {
                msg = "no";
            }
        } else {
            msg = "error";
        }
        return msg;
    }

    public void clear(Long ouId) {
        try {
            String msg = queryByOuId(ouId);
            if ("yes".equals(msg)) {
                // inventoryDao.updateByOuId(ouId);
                Map<String, Object> invparams = new HashMap<String, Object>();
                invparams.put("ou_id", ouId);
                SqlParameter[] invSqlP = {new SqlParameter("ou_id", java.sql.Types.NUMERIC)};
                staDao.executeSp("clear_pressure_sta", invSqlP, invparams);
            }
        } catch (Exception e) {
            log.error("clear", e.toString());
        }
    }

    public ChooseOption findChooseOptionByCategoryCodeAndKey(String categoryCode, String key) {
        String mapKey = categoryCode + "⊥" + key;
        ChooseOption co = categoryCodeAndKeyMap.get(mapKey);
        if (null == co) {
            co = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, key);
            categoryCodeAndKeyMap.put(mapKey, co, 3 * 60 * 1000);
        }
        return co;
    }

    public List<ChooseOption> findChooseListByCategoryCodeAndKey(String categoryCode, String key) {
        return chooseOptionDao.findListByCategoryCodeAndKey(categoryCode, key);
    }

    public List<WarehouseCommand> getVMIWarehouse(RowMapper<WarehouseCommand> rowMap) {
        return chooseOptionDao.getVMIWarehouse(rowMap);
    }

    /** 
     *
     */
    @Override
    public List<WarehouseCommand> getVMIWarehouse() {
        return chooseOptionDao.getVMIWarehouse(new BeanPropertyRowMapper<WarehouseCommand>(WarehouseCommand.class));
    }

    public List<TransportatorCommand> findInventoryStatus(Boolean isAvailable, Long whId, RowMapper<TransportatorCommand> rowMap) {
        return chooseOptionDao.findInventoryStatus(isAvailable, whId, rowMap);
    }

    /** 
     *
     */
    @Override
    public List<TransportatorCommand> findInventoryStatus(Boolean isAvailable, Long whId) {
        return chooseOptionDao.findInventoryStatus(isAvailable, whId, new BeanPropertyRowMapper<TransportatorCommand>(TransportatorCommand.class));
    }

    /**
     * 获取快递供应商平台编码、名称
     * 
     * @param rowMap
     * @return
     */
    public List<TransportatorCommand> findPlatformList(RowMapper<TransportatorCommand> rowMap) {
        return chooseOptionDao.findPlatformList(rowMap);
    }

    /**
     * 获取快递供应商平台编码、名称
     */
    @Override
    public List<TransportatorCommand> findPlatformList() {
        return chooseOptionDao.findPlatformList(new BeanPropertyRowMapper<TransportatorCommand>(TransportatorCommand.class));
    }

    /**
     * staId 获取快递单列表 fanht
     * 
     * @param staId
     * @param rowMap
     * @return
     */
    public List<TransportatorCommand> getTransportNos(Long staId, RowMapper<TransportatorCommand> rowMap) {
        return chooseOptionDao.getTransportNos(staId, rowMap);
    }

    /** 
     *
     */
    @Override
    public List<TransportatorCommand> getTransportNos(Long staId) {
        return chooseOptionDao.getTransportNos(staId, new BeanPropertyRowMapper<TransportatorCommand>(TransportatorCommand.class));
    }

    /**
     * 获取商品分类配货下拉
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    public List<SkuCategories> findCategories(BeanPropertyRowMapper<SkuCategories> beanPropertyRowMapper) {
        return chooseOptionDao.findCategories(beanPropertyRowMapper);
    }

    /**
     * 获取商品分类配货下拉
     */
    @Override
    public List<SkuCategories> findCategories() {
        return chooseOptionDao.findCategories(new BeanPropertyRowMapper<SkuCategories>(SkuCategories.class));
    }

    /**
     * 获取OTO目的地编码
     */
    @Override
    public List<ChooseOption> findOtoLocation(String code) {
        return chooseOptionDao.findOtoLocation(code, new BeanPropertyRowMapperExt<ChooseOption>(ChooseOption.class));
    }

    @Override
    public Integer getSystemThreadValueByKey(String key) {
        return Integer.parseInt(chooseOptionDao.findAllOptionListByOptionKey(key, Constants.THREAD_OR_LIMIT_NUM, new SingleColumnRowMapper<String>(String.class)));
    }

    @Override
    public String getSystemThreadOptionValueByKey(String key) {
        return chooseOptionDao.findAllOptionListByOptionKey(key, Constants.THREAD_OR_LIMIT_NUM, new SingleColumnRowMapper<String>(String.class));
    }


    @Override
    public Integer getLFPiCiValueByKey(String key) {
        return Integer.parseInt(chooseOptionDao.findAllOptionListByOptionKey(key, Constants.LF_PICI_NUM, new SingleColumnRowMapper<String>(String.class)));
    }

    @Override
    public Integer getChooseOptionByCodeKey(String code, String key) {
        return Integer.parseInt(chooseOptionDao.findAllOptionListByOptionKey(key, code, new SingleColumnRowMapper<String>(String.class)));
    }

    @Override
    public Integer getSystemThreadValueByKeyAndDes(String key, Boolean optionDescription) {
        return Integer.parseInt(chooseOptionDao.findAllOptionListByOptionKeyAndDes(key, Constants.THREAD_OR_LIMIT_NUM, optionDescription, new SingleColumnRowMapper<String>(String.class)));
    }

    @Override
    public Pagination<ChooseOption> getChooseOptionByCodePage(int start, int pagesize, String categoryCode, String optionValue, Sort[] sorts) {
        return chooseOptionDao.getChooseOptionByCodePage(start, pagesize, categoryCode, optionValue, new BeanPropertyRowMapper<ChooseOption>(ChooseOption.class), sorts);
    }

    @Override
    public ChooseOption findByCategoryCodeAndValue(String categoryCode, String value) {
        return chooseOptionDao.findByCategoryCodeAndValue(categoryCode, value);
    }

    @Override
    public String omsChooseOptionUpdate(String optionValue) {
        String flag = "";
        ChooseOption chooseOption = chooseOptionDao.findByCategoryCodeAndKey("system_thread", "ORDER_LIMIT");
        if (chooseOption != null) {
            chooseOption.setOptionValue(optionValue);
            chooseOptionDao.save(chooseOption);
        } else {
            flag = "信息有误";
        }
        return flag;
    }

    @Override
    public String pacChooseOptionUpdate(String optionValue) {
        String flag = "";
        ChooseOption chooseOption = chooseOptionDao.findByCategoryCodeAndKey("system_thread", "ORDER_LIMIT_PAC");
        if (chooseOption != null) {
            chooseOption.setOptionValue(optionValue);
            chooseOptionDao.save(chooseOption);
        } else {
            flag = "信息有误";
        }
        return flag;
    }

    @Override
    public List<ChooseOption> getAllChooseOption() {
        return chooseOptionDao.findAllOptionListByCategoryCode("lf_pici_num");
    }

    @Override
    public String updateChooseOptionValue(Long staId, String optionValue) {
        String flag = "";
        ChooseOption chooseOption = chooseOptionDao.getByPrimaryKey(staId);
        if (chooseOption != null) {
            chooseOption.setOptionValue(optionValue);
            chooseOptionDao.save(chooseOption);
        } else {
            flag = "信息有误";
        }
        return flag;
    }

    @Override
    public Pagination<ChooseOption> getAllChooseOptionOcp(int start, int pageSize, Sort[] sorts) {
        return chooseOptionDao.findOptionListByOptionKey(start, pageSize, sorts, new BeanPropertyRowMapper<ChooseOption>(ChooseOption.class));
    }

    @Override
    public String updateChooseOptionValueOcp(Long staId, String optionValue) {
        String flag = "";
        ChooseOption chooseOption = chooseOptionDao.getByPrimaryKey(staId);
        if (chooseOption != null) {
            chooseOption.setOptionValue(optionValue);
            chooseOptionDao.save(chooseOption);
        } else {
            flag = "信息有误";
        }
        return flag;
    }

    @Override
    public ChooseOption buildOmsChooseOptionUpdate() {
        return chooseOptionDao.findByCategoryCodeAndKey("system_thread", "ORDER_LIMIT");
    }

    @Override
    public ChooseOption buildPacChooseOptionUpdate() {
        return chooseOptionDao.findByCategoryCodeAndKey("system_thread", "ORDER_LIMIT_PAC");
    }


    @Override
    public List<AdPackage> findAdPackageByOuIdByAdName(Long ouId) {
        return adPackageDao.findAdPackageByOuIdByAdName(ouId, new BeanPropertyRowMapper<AdPackage>(AdPackage.class));
    }


    @Override
    public List<AdPackage> findAdOrderType() {
        return chooseOptionDao.findAdOrderType(new BeanPropertyRowMapper<AdPackage>(AdPackage.class));
    }

    @Override
    public List<AdPackage> findWmsResult(String wmsOrderType) {
        return chooseOptionDao.findWmsResult(wmsOrderType, new BeanPropertyRowMapper<AdPackage>(AdPackage.class));
    }

    @Override
    public List<String> optionrulelist() {
        return chooseOptionDao.optionrulelist(new SingleColumnRowMapper<String>(String.class));
    }

    @Override
    public List<LicensePlate> findLicensePlateNumber() {
        // TODO Auto-generated method stub
        return chooseOptionDao.findLicensePlateNumber(new BeanPropertyRowMapper<LicensePlate>(LicensePlate.class));
    }

}
