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
import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.mongodb.MongoDBWhPicking;
import com.jumbo.wms.model.pda.PdaSortPickingCommand;
import com.jumbo.wms.model.pda.PickingLine;


/**
 * @author lihui
 *
 * @createDate 2016年1月19日 下午9:00:16
 */
@Transactional
public interface PickingLineDao extends GenericEntityDao<PickingLine, Long> {

    @NativeUpdate
    void savePickingLineByPickingList(@QueryParam(value = "pId") Long pId);

    @NativeUpdate
    void savePickingLineByGeneral(@QueryParam(value = "pId") Long pId);

    @NativeUpdate
    void savePickingLineBySingle(@QueryParam(value = "pId") Long pId);

    @NativeUpdate
    void updatePickingLineQtyByPbId(@QueryParam(value = "pbId") Long pbId);

    @NativeQuery
    List<MongoDBWhPicking> findMongodbInfoByPbCode(@QueryParam(value = "pickingbarCode") String pickingbarCode, RowMapper<MongoDBWhPicking> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<PdaSortPickingCommand> findSortPicking(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<PdaSortPickingCommand> rowMapper);

    @NativeQuery
    List<PdaSortPickingCommand> findSortPickingList(@QueryParam Map<String, Object> m, RowMapper<PdaSortPickingCommand> rowMapper);

    /**
     * 获取配货批明细
     * 
     * @param pbId
     * @return
     */
    @NamedQuery
    List<Long> getPickingLineListByPbId(@QueryParam("pbId") Long pbId);

    @NativeUpdate
    void modifyPickingLineByPbId(@QueryParam("pbId") Long pbId);

    @NativeQuery
    Long findQtyByPickingLineId(@QueryParam("pickingLineId") Long pickingLineId, RowMapper<Long> rowMapper);

    @NativeQuery
    Long findPlQtyByPickingLineId(@QueryParam("pickingLineId") Long pickingLineId, RowMapper<Long> rowMapper);

    @NativeUpdate
    void updatePickingLineQtyByPlIdAndQty(@QueryParam(value = "plId") Long pbId, @QueryParam(value = "qty") Long qty);

    @NativeUpdate
    void deletePickingLineByPickingId(@QueryParam(value = "pId") Long pId);

    @NativeUpdate
    void deleteInvalidPickingBatchByPickingId(@QueryParam(value = "pId") Long pId);

    @NativeQuery
    List<Long> findInvalidPickingBatchByPickingId(@QueryParam(value = "pId") Long pId, SingleColumnRowMapper<Long> rowMapper);

    @NativeQuery
    Integer getPickingNum(@QueryParam(value = "code") String code, @QueryParam(value = "cCode") String cCode, RowMapper<Integer> rowMapper);

    @NativeUpdate
    void updatePickingStatus(@QueryParam(value = "code") String code);



    @NativeQuery
    List<String> findLocByOuIdAndOwner(@QueryParam("ouId") Long ouId, SingleColumnRowMapper<String> s);
}
