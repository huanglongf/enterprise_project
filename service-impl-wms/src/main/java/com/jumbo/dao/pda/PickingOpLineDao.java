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
package com.jumbo.dao.pda;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pda.PickingOpLine;


/**
 * @author lihui
 *
 * @createDate 2016年1月19日 下午9:00:16
 */
@Transactional
public interface PickingOpLineDao extends GenericEntityDao<PickingOpLine, Long> {

    /**
     * 插入操作记录
     * 
     * @param pickingLineId
     * @param cCode
     * @param qty
     * @param userId
     * @return
     */
    @NativeUpdate
    int insertPickingOpLine(@QueryParam(value = "pickingLineId") Long pickingLineId, @QueryParam(value = "cCode") String cCode, @QueryParam(value = "qty") Long qty, @QueryParam(value = "userId") Long userId);

    /**
     * 获取绑定的周转箱
     * 
     * @param pbId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<String> findBoxCodeByPbId(@QueryParam(value = "pbId") Long pbId, RowMapper<String> rowMapper);


    /**
     * 获取操作记录
     * 
     * @param plId
     * @return
     */
    @NamedQuery
    List<PickingOpLine> getPolListByPickingLineId(@QueryParam("plId") Long plId);
    
    /**
     * 获取退仓拣货记录
     * @param barCode
     * @return
     */
    @NativeQuery
    List<PickingOpLine> getPickingOpLineByDiekingCode(@QueryParam(value="barCode")String barCode,RowMapper<PickingOpLine> rowMapper); 
    
    /**
     * 获取退仓拣货操作日志
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<PickingOpLine> getPickingOpLineByDiekingCode1(@QueryParam(value="barCode")String barCode,RowMapper<PickingOpLine> rowMapper);
    
    @NamedQuery
    List<PickingOpLine> getPickingOpLineByRDCode(@QueryParam("batchCode")String batchCode);
    
    /**
     * 获取退仓拣货周转箱
     * @param barCode
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<PickingOpLine> getPickingOpLineByDiekingCode2(@QueryParam(value="barCode")String barCode,RowMapper<PickingOpLine> rowMapper);
    
}
