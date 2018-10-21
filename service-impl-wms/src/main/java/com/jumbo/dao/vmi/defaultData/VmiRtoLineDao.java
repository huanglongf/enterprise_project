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

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiRtoLineCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineDefault;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

/**
 * @author lichuan
 * 
 */
@Transactional
public interface VmiRtoLineDao extends GenericEntityDao<VmiRtoLineDefault, Long> {

    /**
     * 
     * @param rtoId
     * @param rowMapper
     * @return List<VmiRtoLineCommand>
     * @throws
     */
    @NativeQuery
    List<VmiRtoLineCommand> findRtoLineListByRtoId(@QueryParam("rtoId") Long rtoId, RowMapper<VmiRtoLineCommand> rowMapper);
    
    
   /**
    * 
    * @param rtoId
    * @param rowMapper
    * @return List<VmiRtoLineCommand>
    * @throws
    */
   @NativeQuery
   List<VmiRtoLineCommand> findRtoLineListByRtoIdAndOrderCode(@QueryParam("storeCode") String storeCode,@QueryParam("orderCode") String orderCode, RowMapper<VmiRtoLineCommand> rowMapper);

    /**
     * 查询退货指令明细
     * 
     * @param start
     * @param pageSize
     * @param rtoId
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<VmiRtoLineCommand> findRtoLinePageByRtoId(int start, int pageSize, @QueryParam("rtoId") Long rtoId, RowMapper<VmiRtoLineCommand> rowMapper);
}
