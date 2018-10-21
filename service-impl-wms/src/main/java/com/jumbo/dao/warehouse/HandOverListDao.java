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

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.automaticEquipment.CountPackageCommand;
import com.jumbo.wms.model.warehouse.HandOverList;
import com.jumbo.wms.model.warehouse.HandOverListCommand;

@Transactional
public interface HandOverListDao extends GenericEntityDao<HandOverList, Long> {

    /**
     * 根据条件查询交接清单
     * 
     * @param trackingNo
     * @param code
     * @param status
     * @param lpcode
     * @param partyAOperator
     * @param partyBOassport
     * @param partyBOperator
     * @param createStartTime
     * @param createEndTime
     * @param handOverStartTime
     * @param handOverEndTime
     * @param sorts
     * @param rowMap
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<HandOverListCommand> findHoList(int start, int pageSize, @QueryParam("ouid") Long ouid, @QueryParam("trackingNo") String trackingNo, @QueryParam("code") String code, @QueryParam("status") Integer status,
            @QueryParam("lpcode") String lpcode, @QueryParam("partyAOperator") String partyAOperator, @QueryParam("partyBOassport") String partyBOassport, @QueryParam("partyBOperator") String partyBOperator,
            @QueryParam("paytyAMobile") String paytyAMobile, @QueryParam("createStartTime") Date createStartTime, @QueryParam("createEndTime") Date createEndTime, @QueryParam("handOverStartTime") Date handOverStartTime,
            @QueryParam("handOverEndTime") Date handOverEndTime, Sort[] sorts, RowMapper<HandOverListCommand> rowMap);


    @NativeQuery
    List<HandOverListCommand> queryCheckHandOverList(int start, int pageSize, @QueryParam(value = "ouid") Long ouid, RowMapper<HandOverListCommand> rowMapper, Sort[] sorts);

    /**
     * 
     * @param ouid
     * @param beanPropertyRowMapperExt
     * @param sorts
     * @return
     */
    @NativeQuery
    List<HandOverListCommand> findCurrHandOverListTotal(@QueryParam("ouid") Long ouid, BeanPropertyRowMapperExt<HandOverListCommand> beanPropertyRowMapperExt, Sort[] sorts);

    /**
     * 根据当前的sta 查询关联的新建未交接的物流交接单
     * 
     * @param staId
     * @param i
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findHandOverListByStaId(@QueryParam("staId") Long staId, @QueryParam("status") int i, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    List<HandOverListCommand> findEdwTwhStaHoList(RowMapper<HandOverListCommand> rowMapper);

    @NativeQuery
    HandOverList findEdwTwhStaHoListId(@QueryParam("id") Long Id, RowMapper<HandOverList> rowMapper);

    /**
     * 根据交接清单头id删除交接清单仓库
     * 
     * @param id
     * @param status
     */
    @NativeUpdate
    void deleteByHoIdWareList(@QueryParam("hoId") Long hoId, @QueryParam("wId") Long wId);

    /**
     * 根据物流商统计自动化仓下有多少包裹需要创建交接清单
     * 
     * @param lpCodeList
     * @param rowMap
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<CountPackageCommand> countPackageByLpcode(int start, int pageSize, @QueryParam(value = "lpCodeList") String lpCodeList, RowMapper<CountPackageCommand> rowMap);

    @NativeQuery
    Integer findIsPrintPackageDetail(@QueryParam("holid") Long holid, @QueryParam("ouid") Long ouid, SingleColumnRowMapper<Integer> singleColumnRowMapper);

}
