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
 */
package com.jumbo.dao.automaticEquipment;

import java.util.List;
import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.automaticEquipment.Zoon;
import com.jumbo.wms.model.command.automaticEquipment.ZoonCommand;


/**
 * @author lihui
 * 
 * @createDate 2016年1月19日 下午9:00:16
 */
@Transactional
public interface ZoonDao extends GenericEntityDao<Zoon, Long> {
    @NativeQuery(pagable = true)
    Pagination<ZoonCommand> findZoonByParams(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<ZoonCommand> rowMapper);


    @NativeQuery(pagable = true)
    Pagination<ZoonCommand> findZoonSortByParams(int start, int pageSize, @QueryParam("ouId") Long id, @QueryParam("name") String name, @QueryParam("code") String code, RowMapper<ZoonCommand> rowMapper);


    @NativeQuery
    List<ZoonCommand> findZoonByParams2(@QueryParam("ouId") Long ouId, RowMapper<ZoonCommand> rowmap);



    /**
     * 获取区域下拉框数据
     * 
     * @param pickingId
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<Zoon> findPickingDistrictByPickingId(@QueryParam("pickingId") Long pickingId, @QueryParam("ouId") Long ouId, RowMapper<ZoonCommand> rowMap);

    /**
     * 获取区域下拉框数据
     * 
     * @param pickingId
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<Zoon> findPickingDistrictByPickingListId(@QueryParam("pickingListId") List<Long> pickingListId, @QueryParam("ouId") Long ouId, RowMapper<ZoonCommand> rowMap);

    @NativeQuery
    List<OperationUnit> findAutoWh(RowMapper<OperationUnit> rowMapper);

    @NamedQuery
    Zoon getZoonByCode(@QueryParam(value = "code") String code);

    /**
     * 
     * 方法说明：通过配货批ID查找ZOON ID
     * 
     * @author LuYingMing
     * @date 2016年9月6日 下午9:17:22
     * @param pickingListId
     * @param ouId
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<Long> findZoonIds(@QueryParam("pickingListId") Long pickingListId, @QueryParam("ouId") Long ouId, RowMapper<Long> rowMap);

    @NativeQuery
    List<String> findAllZoonCode(@QueryParam(value = "ouId") Long ouId, SingleColumnRowMapper<String> singleColumnRowMapper);
    
    /**
     * 获取下拉列表
     * @param ouId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<Zoon> findZoonByOuId(@QueryParam("ouId")Long ouId,RowMapper<Zoon> rowMapper);
}
