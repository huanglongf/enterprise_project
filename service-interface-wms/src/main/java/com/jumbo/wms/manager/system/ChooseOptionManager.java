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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.jumbo.rmi.warehouse.vmi.VmiRto;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.AdPackage;
import com.jumbo.wms.model.baseinfo.LicensePlate;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.command.WarehouseCommand;
import com.jumbo.wms.model.system.ChooseOption;

import loxia.dao.Pagination;
import loxia.dao.Sort;

/**
 * chooseOption
 * 
 * @author jumbo
 * 
 */
public interface ChooseOptionManager extends BaseManager {
    /**
     * 根据常量集编码categoryCode查找 ChooseOption List
     * 
     * @param categoryCode
     * @return
     */
    List<ChooseOption> findOptionListByCategoryCode(String categoryCode);


    public List<String> findByCategoryCode(String categoryCode);

    /**
     * 查询PDA机器编码
     * 
     * @param pdaCode
     * @return
     */
    ChooseOption findPdaCode(String pdaCode);

    void clear(Long ouId);

    /**
     * 
     * @param categoryCode
     * @return
     */
    Map<String, String> getOptionByCategoryCode(String categoryCode);

    public Integer getSystemThreadValueByKeyAndDes(String key, Boolean optionDescription);

    /**
     * 根据物流商code获取detail
     * 
     * @param expCode
     * @param rowMap
     * @return
     */
    @Deprecated
    public List<TransportatorCommand> findTransportator(String expCode, RowMapper<TransportatorCommand> rowMap);

    /**
     * 根据物流商code获取detail
     * 
     * @param expCode
     * @return List<TransportatorCommand>
     * @throws
     */
    public List<TransportatorCommand> findTransportator(String expCode);


    /**
     * 查找所有OTO目的地编码
     * 
     * @return
     */
    public List<ChooseOption> findOtoLocation(String code);

    public ChooseOption findChooseOptionByCategoryCodeAndKey(String categoryCode, String key);

    public List<ChooseOption> findChooseListByCategoryCodeAndKey(String categoryCode, String key);

    public List<String> optionrulelist();

    List<VmiRto> initPumaToOrderCode();

    String queryByOuId(Long ouId);

    @Deprecated
    public List<WarehouseCommand> getVMIWarehouse(RowMapper<WarehouseCommand> rowMap);

    public List<WarehouseCommand> getVMIWarehouse();

    @Deprecated
    public List<TransportatorCommand> findInventoryStatus(Boolean isAvailable, Long whId, RowMapper<TransportatorCommand> rowMap);

    public List<TransportatorCommand> findInventoryStatus(Boolean isAvailable, Long whId);

    /**
     * 获取快递供应商平台编码、名称
     * 
     * @param rowMap
     * @return
     */
    @Deprecated
    public List<TransportatorCommand> findPlatformList(RowMapper<TransportatorCommand> rowMap);

    /**
     * 获取快递供应商平台编码、名称
     * 
     * @return List<TransportatorCommand>
     * @throws
     */
    public List<TransportatorCommand> findPlatformList();


    public List<AdPackage> findAdOrderType();

    public List<AdPackage> findWmsResult(String wmsOrderType);

    String getSystemThreadOptionValueByKey(String key);


    /**
     * staId 获取快递单列表 fanht
     * 
     * @param staId
     * @param rowMap
     * @return
     */
    @Deprecated
    public List<TransportatorCommand> getTransportNos(Long staId, RowMapper<TransportatorCommand> rowMap);

    /**
     * staId 获取快递单列表
     * 
     * @param staId
     * @return List<TransportatorCommand>
     * @throws
     */
    public List<TransportatorCommand> getTransportNos(Long staId);

    /**
     * 获取商品分类配货下拉
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @Deprecated
    public List<SkuCategories> findCategories(BeanPropertyRowMapper<SkuCategories> beanPropertyRowMapper);

    /**
     * 获取商品分类配货下拉
     * 
     * @return List<SkuCategories>
     * @throws
     */
    public List<SkuCategories> findCategories();

    /**
     * 根据给定的线程分类标识查询系统设定的线程数
     * 
     * @return
     */
    public Integer getSystemThreadValueByKey(String key);

    /**
     * 
     * 设置
     * 
     * @return
     */
    public Integer getLFPiCiValueByKey(String key);
    
    public Integer getChooseOptionByCodeKey(String code,String key);

    
    /**
     * 分页查询行政部门
     */
    Pagination<ChooseOption> getChooseOptionByCodePage(int start, int pagesize, String categoryCode, String optionValue, Sort[] sorts);

    List<ChooseOption> getChooseOptionByCodeEsprit(String categoryCode);

    /**
     * 过仓直连维护
     * 
     * @return
     */
    String omsChooseOptionUpdate(String optionValue);

    /**
     * 过仓非直连维护
     * 
     * @return
     * @throws Exception
     */
    String pacChooseOptionUpdate(String optionValue);

    /**
     * 查询外包仓信息
     * 
     * @return
     * @throws Exception
     */
    List<ChooseOption> getAllChooseOption();

    /**
     * 设置立峰批次数
     * 
     * @return
     * @throws Exception
     */
    String updateChooseOptionValue(Long staId, String optionValue);

    /**
     * 查询占用库存数据
     * 
     * @return
     * @throws Exception
     */
    Pagination<ChooseOption> getAllChooseOptionOcp(int start, int pageSize, Sort[] sorts);

    /**
     * 维护占用库存数
     * 
     * @return
     * @throws Exception
     */
    String updateChooseOptionValueOcp(Long staId, String optionValue);

    /**
     * 过仓直连维护
     * 
     * @return
     */
    ChooseOption buildOmsChooseOptionUpdate();

    /**
     * 过仓非直连维护
     * 
     * @return
     * @throws Exception
     */
    ChooseOption buildPacChooseOptionUpdate();

    ChooseOption findByCategoryCodeAndValue(String categoryCode, String value);


    List<AdPackage> findAdPackageByOuIdByAdName(Long id);

    public List<LicensePlate> findLicensePlateNumber();

}
