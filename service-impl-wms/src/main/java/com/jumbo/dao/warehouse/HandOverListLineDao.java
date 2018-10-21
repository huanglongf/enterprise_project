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

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.HandOverListLine;
import com.jumbo.wms.model.warehouse.HandOverListLineCommand;

@Transactional
public interface HandOverListLineDao extends GenericEntityDao<HandOverListLine, Long> {

    @NativeQuery
    List<HandOverListLineCommand> findLineByHoListId(@QueryParam("hoListId") Long hoListId, @QueryParam("status") Integer status, Sort[] sorts, RowMapper<HandOverListLineCommand> rowMap);

    @NamedQuery
    HandOverListLine findByTrackingNo(@QueryParam("trackingNo") String trackingNo);

    @NamedQuery
    List<HandOverListLine> findByStaId(@QueryParam("staId") Long staId);

    @NativeQuery
    List<HandOverListLineCommand> findLineDetailByHoListId(@QueryParam("holid") Long holid, BeanPropertyRowMapper<HandOverListLineCommand> beanPropertyRowMapper);

    @NativeQuery
    List<HandOverListLineCommand> findLineDetailByHoListIdAD(@QueryParam("holid") Long holid, BeanPropertyRowMapper<HandOverListLineCommand> beanPropertyRowMapper);


    @NativeQuery
    List<HandOverListLineCommand> findExportInfoByHoListId(@QueryParam("hoListId") Long hoListId, @QueryParam("status") Integer status, RowMapper<HandOverListLineCommand> rowMap);

    @NativeQuery
    List<HandOverListLineCommand> findExportInfoByHoListId2(@QueryParam("hoListId") Long hoListId, @QueryParam("status") Integer status, RowMapper<HandOverListLineCommand> rowMap);

    @NativeUpdate
    void updateCancelLine(@QueryParam("holId") Long holId, @QueryParam("userId") Long userId, @QueryParam("cancelReason") String cancelReason);

    /**
     * 根据交接单号 删除明细 KJL
     * 
     * @param id
     */
    @NativeUpdate
    void deleteByHandId(@QueryParam("handId") Long id);

    /**
     * 根据作业单Id删除关联的物流交接单明细行 KJL
     * 
     * @param staId
     * @param status
     */
    @NativeUpdate
    void deleteHandOverListLineByStaId(@QueryParam("staId") Long staId, @QueryParam("status") int status);

    // 交接清单导出
    @NativeQuery
    List<HandOverListLineCommand> findHandOverListExport(@QueryParam("fromDate") Date fromDate, @QueryParam("endDate") Date endDate, @QueryParam("lpCode") String lpCode, @QueryParam("idList") List<Long> idList, @QueryParam(value = "whOuId") Long ouId,
            BeanPropertyRowMapperExt<HandOverListLineCommand> beanPropertyRowMapperExt);

    // 交接清单导出原始
    @NativeQuery
    List<HandOverListLineCommand> findHandOverListExport2(@QueryParam("fromDate") Date fromDate, @QueryParam("endDate") Date endDate, @QueryParam("lpCode") String lpCode, @QueryParam("idList") List<Long> idList, @QueryParam(value = "whOuId") Long ouId,
            BeanPropertyRowMapperExt<HandOverListLineCommand> beanPropertyRowMapperExt);

    @NativeQuery
    List<HandOverListLineCommand> findEdwTwhStaHoListLine(@QueryParam("hoid") Long hoid, RowMapper<HandOverListLineCommand> rowMapper);

    @NativeQuery
    List<HandOverListLine> findEdwTwhStaHoListLine1(@QueryParam("hoid") Long hoid, RowMapper<HandOverListLine> rowMapper);

    @NativeQuery
    HandOverListLine findByTrackingNo2(@QueryParam("trackingNo") String trackingNo, RowMapper<HandOverListLine> rowMapper);
}
