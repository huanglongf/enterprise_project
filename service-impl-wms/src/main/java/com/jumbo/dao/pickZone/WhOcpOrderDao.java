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
package com.jumbo.dao.pickZone;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.pickZone.WhOcpOrderCommand;
import com.jumbo.wms.model.pickZone.WhOcpOrder;



/**
 * @author lihui
 * 
 * @createDate 2015年7月14日 下午5:49:00
 */
@Transactional
public interface WhOcpOrderDao extends GenericEntityDao<WhOcpOrder, Long> {
    @NativeQuery
    List<WhOcpOrderCommand> findOcpOrderByParams(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "status") List<Integer> status, RowMapper<WhOcpOrderCommand> rowMapper);

    @NativeQuery
    List<WhOcpOrderCommand> findOcpOrderStatus(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "status") Integer status, RowMapper<WhOcpOrderCommand> rowMapper);

    /**
     * 根据状态获取所有的占用批ID
     * 
     * @param ouId
     * @param status
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findOcpOrderIdsByStatus(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "status") Integer status, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据占用批编码更新占用批状态
     * 
     * @param ocpCode
     * @param status
     */
    @NativeUpdate
    void updateOcpOrderStatusByOcpCode(@QueryParam(value = "ocpCode") String ocpCode, @QueryParam(value = "status") Integer status);

    /**
     * 获取异常的占用批
     * 
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<WhOcpOrderCommand> findExceptionOcpOrder(RowMapper<WhOcpOrderCommand> rowMapper);

    @NativeQuery
    List<Long> findOcpStaIdByOcpCode(@QueryParam(value = "ouId") Long ouId, @QueryParam(value = "wooCode") String wooCode, SingleColumnRowMapper<Long> sinleColum);

    @NativeQuery
    Long queryInventoryByOcpCode(@QueryParam(value = "wooCode") String wooCode, SingleColumnRowMapper<Long> single);

    @NativeQuery
    List<String> findOcpAreaByOuId(@QueryParam(value = "ouId") Long ouId, SingleColumnRowMapper<String> single);


}
