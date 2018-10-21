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
package com.jumbo.dao.vmi.defaultData;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.rmi.warehouse.vmi.VmiRto;
import com.jumbo.wms.model.vmi.Default.VmiRtoCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoDefault;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

/**
 * @author lichuan
 * 
 */
@Transactional
public interface VmiRtoDao extends GenericEntityDao<VmiRtoDefault, Long> {

    /**
     * 
     * @param uuid
     * @param rowMapper
     * @return VmiRtoCommand
     * @throws
     */
    @NativeQuery
    VmiRtoCommand findVmiRtoByUuid(@QueryParam("uuid") String uuid, RowMapper<VmiRtoCommand> rowMapper);



    @NativeQuery
    List<VmiRto> initPumaToOrderCode(RowMapper<VmiRto> rowMapper);


    /**
     * 
     * @param vmicode
     * @param orderCode
     * @param rowMapper
     * @return List<VmiRtoCommand>
     * @throws
     */
    @NativeQuery
    List<VmiRtoCommand> findVmiRtoByOrder(@QueryParam("vmicode") String vmicode, @QueryParam("orderCode") String orderCode, RowMapper<VmiRtoCommand> rowMapper);

    /**
     * 
     * @param vmicode
     * @param orderCode
     * @param rowMapper
     * @return List<VmiRtoCommand>
     * @throws
     */
    @NativeQuery
    List<VmiRtoCommand> findPumaVmiRtoByOrder(@QueryParam("vmicode") String vmicode, @QueryParam("orderCode") String orderCode, RowMapper<VmiRtoCommand> rowMapper);


    /**
     * 
     * @param start
     * @param pageSize
     * @param startDate
     * @param endDate
     * @param intStatus
     * @param orderCode
     * @param vmiCodeList
     * @param rowMapper
     * @return Pagination<VmiRtoCommand>
     * @throws
     */
    @NativeQuery(pagable = true)
    Pagination<VmiRtoCommand> findVmiRtoListByPage(int start, int pageSize, @QueryParam("startDate") Date startDate, @QueryParam("endDate") Date endDate, @QueryParam("intStatus") Integer intStatus, @QueryParam("orderCode") String orderCode,
            @QueryParam("vmiCodeList") List<String> vmiCodeList, RowMapper<VmiRtoCommand> rowMapper);

    /**
     * @param vmicode
     * @param status
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<VmiRtoCommand> findVmiRtoByVmiCodeAndStatus(@QueryParam("vmicode") String vmicode, @QueryParam("status") Integer status, RowMapper<VmiRtoCommand> rowMapper);

    /**
     * @param vmicode
     * @param orderCode
     * @param status
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<VmiRtoCommand> findVmiRtoByVmiCodeAndOrderCode(@QueryParam("vmicode") String vmicode, @QueryParam("orderCode") String orderCode, @QueryParam("status") Integer status, RowMapper<VmiRtoCommand> rowMapper);

    /**
     * gucci退仓指令
     * 
     * @param startTime
     * @param endTime
     * @param toLoction
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<VmiRtoCommand> findVmiRtoByGucci(int start, int pageSize, @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, @QueryParam("slipCode") String slipCode, @QueryParam("toLoction") String toLoction,
            RowMapper<VmiRtoCommand> rowMapper);

    /**
     * 获取可以创单的退大仓指令
     * 
     * @param status
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<VmiRtoCommand> findVmiRtoByCreGucciRtn(@QueryParam("status") Integer status, RowMapper<VmiRtoCommand> rowMapper);
}
