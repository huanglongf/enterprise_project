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

package com.jumbo.dao.system;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.wms.model.AdPackage;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.LicensePlate;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.wms.model.command.TransportatorCommand;
import com.jumbo.wms.model.command.WarehouseCommand;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.system.ChooseOptionCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

@Transactional
public interface ChooseOptionDao extends GenericEntityDao<ChooseOption, Long> {
    @NamedQuery
    List<ChooseOption> findOptionListByCategoryCode(@QueryParam("categoryCode") String categoryCode);

    @NamedQuery
    List<String> findByCategoryCodeA(@QueryParam("categoryCode") String categoryCode);

    @NamedQuery
    List<ChooseOption> findAllOptionListByCategoryCode(@QueryParam("categoryCode") String categoryCode);


    @NamedQuery
    ChooseOption findByCategoryCodeAndKey(@QueryParam("categoryCode") String categoryCode, @QueryParam("key") String key);

    @NativeQuery
    List<String> optionrulelist(SingleColumnRowMapper<String> singleColumnRowMapper);

    @NamedQuery
    List<ChooseOption> findListByCategoryCodeAndKey(@QueryParam("categoryCode") String categoryCode, @QueryParam("key") String key);

    @NamedQuery
    List<ChooseOption> findListByPackageName(@QueryParam("packageName") String packageName);

    /**
     * 根据物流商code获取detail
     * 
     * @param expCode
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<TransportatorCommand> findTransportator(@QueryParam("expCode") String expCode, RowMapper<TransportatorCommand> rowMap);

    @NativeQuery
    List<WarehouseCommand> getVMIWarehouse(RowMapper<WarehouseCommand> rowMap);

    @NativeQuery
    Map<String, Long> findAllTransportator(MapRowMapper r);

    @NativeQuery
    List<TransportatorCommand> findInventoryStatus(@QueryParam("isAvailable") Boolean isAvailable, @QueryParam("whId") Long whId, RowMapper<TransportatorCommand> rowMap);


    @NativeQuery
    List<ChooseOption> queryPadcodeOperate(@QueryParam("code") String code, RowMapper<ChooseOption> rowMapper);

    @NativeQuery(pagable = true, withGroupby = true)
    Pagination<ChooseOptionCommand> findPreOption(int start, int pageSize, @QueryParam("flag") int flag, RowMapper<ChooseOptionCommand> r, Sort[] sorts);

    @NamedQuery
    ChooseOption findByCategoryCode(@QueryParam("categoryCode") String categoryCode);



    /**
     * 根据组织编号，获取行业列表
     * 
     * @param ouid
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<OperationUnit> findIndustryByOuid(@QueryParam("ouid") Long ouid, RowMapper<OperationUnit> rowMap);

    /**
     * 获取快递供应商平台编码、名称
     * 
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<TransportatorCommand> findPlatformList(RowMapper<TransportatorCommand> rowMap);

    /**
     * staId 获取快递单列表 fanht
     * 
     * @param staId
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<TransportatorCommand> getTransportNos(@QueryParam("staId") Long staId, RowMapper<TransportatorCommand> rowMap);

    /**
     * 获取商品分类配货下拉
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<SkuCategories> findCategories(BeanPropertyRowMapper<SkuCategories> beanPropertyRowMapper);

    /**
     * 获取OTO目的地编码
     * 
     * @return
     */
    @NativeQuery
    List<ChooseOption> findOtoLocation(@QueryParam("code") String code, RowMapper<ChooseOption> rowMapper);

    /**
     * 获取设置的SkuMaxLength值
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    ChooseOption findSkuMaxLength(BeanPropertyRowMapper<ChooseOption> beanPropertyRowMapper);

    @NativeQuery
    String findAllOptionListByOptionKey(@QueryParam("optionkey") String optionkey, @QueryParam("categorycode") String categoryCode, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    String findAllOptionListByOptionKeyAndDes(@QueryParam("optionkey") String optionkey, @QueryParam("categorycode") String categoryCode, @QueryParam("available") Boolean available, SingleColumnRowMapper<String> singleColumnRowMapper);

    // @NativeQuery(model = ChooseOption.class)
    // List<ChooseOption> findOptionListByLikeCode(@QueryParam("categoryCode") String categoryCode);

    @NativeUpdate
    void updateSortNo(@QueryParam("categoryCode") String categoryCode);

    @NamedQuery
    ChooseOption findByCategoryCodeAndValue(@QueryParam("categoryCode") String categoryCode, @QueryParam("value") String value);

    @NativeQuery
    List<String> findOptionKeyByCode(@QueryParam("categoryCode") String categoryCode, RowMapper<String> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<ChooseOption> getChooseOptionByCodePage(int start, int pagesize, @QueryParam("categoryCode") String categoryCode, @QueryParam("optionValue") String optionValue, RowMapper<ChooseOption> r, Sort[] sorts);


    @NativeQuery
    List<ChooseOption> getChooseOptionByCodeEsprit(@QueryParam("categoryCode") String categoryCode, RowMapper<ChooseOption> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<ChooseOption> findOptionListByOptionKey(int start, int pageSize, Sort[] sorts, RowMapper<ChooseOption> rowMapper);

    @NativeQuery
    List<AdPackage> findAdOrderType(RowMapper<AdPackage> rowMap);

    @NativeQuery
    List<AdPackage> findWmsResult(@QueryParam("wmsOrderType") String wmsOrderType, RowMapper<AdPackage> rowMap);
    @NativeQuery
    List<LicensePlate> findLicensePlateNumber(RowMapper<LicensePlate> rowMap);


}
