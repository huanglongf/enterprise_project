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

package com.jumbo.dao.baoshui;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclarationLine;
import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclarationLineCommand;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;


@Transactional
public interface CustomsDeclarationLineDao extends GenericEntityDao<CustomsDeclarationLine, Long> {
    
    /**
     * 获取入库报关明细
     * @param id
     * @param skuCode
     * @param upc
     * @param r
     * @return
     */
    @NativeQuery
    List<CustomsDeclarationLineCommand> getCustomsDeclarationLine(@QueryParam("id")Long id,@QueryParam("skuCode")String skuCode,@QueryParam("upc")String upc,RowMapper<CustomsDeclarationLineCommand> r);
    
    @NativeUpdate
    void deleteCustomsDeclarationLineByCusId(@QueryParam("id")Long id);

    @NativeQuery
    List<CustomsDeclarationLine> findNewCustomsDeclarationLineByStaId(@QueryParam("staId") Long staId, RowMapper<CustomsDeclarationLine> r);

    @NativeQuery
    List<CustomsDeclarationLine> findNewCustomsDeclarationLineBySalesStaId(@QueryParam("staId") Long staId, RowMapper<CustomsDeclarationLine> r);

    @NativeQuery
    List<CustomsDeclarationLineCommand> findCustomsDeclarationLineByCdId(@QueryParam("cdId") Long cdId, RowMapper<CustomsDeclarationLineCommand> r);

}
