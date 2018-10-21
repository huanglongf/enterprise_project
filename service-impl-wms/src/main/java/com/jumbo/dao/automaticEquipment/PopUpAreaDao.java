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

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.PopUpArea;
import com.jumbo.wms.model.command.automaticEquipment.PopUpAreaCommand;


/**
 * @author lihui
 * 
 * @createDate 2016年1月19日 下午9:00:16
 */
@Transactional
public interface PopUpAreaDao extends GenericEntityDao<PopUpArea, Long> {
    @NativeQuery(pagable = true)
    Pagination<PopUpAreaCommand> findPopUpAreaByParams(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<PopUpAreaCommand> rowMapper);

    @NamedQuery
    PopUpArea getPopUpAreaByCode(@QueryParam(value = "code") String code);

    @NamedQuery
    PopUpArea getPopUpAreaByCodeAndOuid(@QueryParam(value = "code") String code);

    @NamedQuery
    PopUpArea getPopUpAreaByBarCode(@QueryParam(value = "barcode") String barcode);

    @NativeQuery
    List<PopUpArea> findPopByOuId(BeanPropertyRowMapper<PopUpArea> beanPropertyRowMapper);

    /**
     * 根据收货信息推荐弹出口
     * 
     * @param skuId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    PopUpArea recommandLocationBySku(@QueryParam("skuId") Long skuId, @QueryParam("skuTypeId") Long skuTypeId, @QueryParam("owner") String owner, BeanPropertyRowMapper<PopUpArea> beanPropertyRowMapper);

    @NativeQuery
    List<PopUpArea> recommandLocationListBySku(@QueryParam("skuId") Long skuId, @QueryParam("skuTypeId") Long skuTypeId, @QueryParam("owner") String owner, BeanPropertyRowMapper<PopUpArea> beanPropertyRowMapper);


}
