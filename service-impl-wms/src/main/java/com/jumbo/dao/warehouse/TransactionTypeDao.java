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

package com.jumbo.dao.warehouse;

import java.math.BigDecimal;
import java.util.List;

import loxia.annotation.DynamicQuery;
import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Page;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.warehouse.TransactionType;

@Transactional
public interface TransactionTypeDao extends GenericEntityDao<TransactionType, Long> {

    /**
     * 根据ouTypeId查询作业类型,如无ouTypeId查询所有作业类型
     * 
     * @param notIgnoreIsSystem 是否忽略isSystem
     * @param isSystem
     * @param ouTypeId
     * @param sorts
     * @return
     */
    @DynamicQuery
    List<TransactionType> findList(@QueryParam("notIgnoreSystem") Boolean notIgnoreIsSystem, @QueryParam("isSystem") Boolean isSystem, @QueryParam("ouTypeId") Long ouTypeId, Sort[] sorts);

    /**
     * 根据ouTypeId查询作业类型
     * 
     * @param isSystem
     * @param ouTypeId
     * @param sorts
     * @return
     */
    @DynamicQuery
    List<TransactionType> findListByOuId(@QueryParam("isSystem") Boolean isSystem, @QueryParam("ouTypeId") Long ouTypeId, Sort[] sorts);

    @NamedQuery
    TransactionType findByCode(@QueryParam("code") String code);

    /**
     * 根据sta 类型查询事物作业类型
     * 
     * @param staType
     * @return
     */
    @NativeQuery
    BigDecimal findByStaType(@QueryParam("staType") Integer staType, SingleColumnRowMapper<BigDecimal> r);

    /**
     * 
     * @param code
     * @param ouid 运营中心ou
     * @return
     */
    @NamedQuery
    TransactionType findByCodeInOu(@QueryParam("code") String code, @QueryParam("ouid") Long ouid);

    /**
     * 当前库位绑定的作业类型,未指定库位=所有作业类型
     * 
     * @param locationId
     * @return
     */
    @NativeQuery
    List<TransactionType> findTransactionTypeList(@QueryParam("ouId") Long ouId, @QueryParam("locationId") Long locationId, Sort[] sorts, RowMapper<TransactionType> rowMapper);

    /**
     * 查询店铺关联的所有作业类型
     * 
     * @return
     */
    @NativeQuery
    List<String> findNameByShop(@QueryParam("ouid") Long ouid, RowMapper<String> rowMapper);

    /**
     * 查询本运营中心下的所有作业类型及系统预定义的作业类型
     * 
     * @param id 运营中心id
     * @return
     */
    @DynamicQuery
    List<TransactionType> findTransactionListByOu(@QueryParam("ouid") Long ouid);

    /**
     * 根据 TransactionType Code 查询 作业类型
     * 
     * @param transTypeCodes
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<TransactionType> findByCodes(@QueryParam(value = "code") List<String> code, BeanPropertyRowMapperExt<TransactionType> beanPropertyRowMapper);
    
	/**
	 * 查询作业类型
	 * @param page
	 * @return
	 */
    @NamedQuery(pagable=true)
    public Pagination<TransactionType> getTransActionType(Page page);
}
