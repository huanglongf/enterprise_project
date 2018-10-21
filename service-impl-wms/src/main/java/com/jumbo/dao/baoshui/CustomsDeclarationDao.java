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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclaration;
import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclarationCommand;
import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclarationDto;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;


@Transactional
public interface CustomsDeclarationDao extends GenericEntityDao<CustomsDeclaration, Long> {

    /**
     * 查询报税出库
     * @param start
     * @param size
     * @param params
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<CustomsDeclarationDto> queryBaoShuiOutStaList(int start, int size, @QueryParam Map<String, Object> params, RowMapper<CustomsDeclarationDto> rowMapper, Sort[] sorts);

    @NamedQuery
    CustomsDeclaration getCustomsDeclarationByStaCode(@QueryParam("staCode") String staCode);
	
	@NativeQuery
    CustomsDeclaration queryCustomsDeclaration(@QueryParam("trackingNo") String trackingNo, RowMapper<CustomsDeclaration> rowMapper);

    /**
     * 获取单据的净重
     * 
     * @return
     */
    @NativeQuery
    BigDecimal findOrderNetWt(@QueryParam("staId") Long staId, SingleColumnRowMapper<BigDecimal> scrm);
    
    /**
     * 计算净重毛重
     * @return
     */
    @NativeUpdate
    int countCustomsDeclarationWeight(@QueryParam("ouId")Long ouId);
    
    @NativeQuery
    List<CustomsDeclaration> findCustomsDeclarationLackSku(@QueryParam("ouId")Long ouId,RowMapper<CustomsDeclaration> rowMapper);
    
    @NativeQuery
    List<CustomsDeclarationCommand> findCustomsDeclarationByNeedSend(RowMapper<CustomsDeclarationCommand> rowMapper);

}
