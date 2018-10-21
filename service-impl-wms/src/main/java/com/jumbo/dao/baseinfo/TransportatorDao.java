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

package com.jumbo.dao.baseinfo;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.command.TransportatorCommand;

@Transactional
public interface TransportatorDao extends GenericEntityDao<Transportator, Long> {

    @NativeQuery
    List<TransportatorCommand> findTransportatorListByShop(@QueryParam(value = "shopId") Long shopId, RowMapper<TransportatorCommand> rowMapper);

    @NativeQuery
    List<TransportatorCommand> getTransByPlId(@QueryParam(value = "pickingListId") Long pickingListId, BeanPropertyRowMapperExt<TransportatorCommand> beanPropertyRowMapperExt);

    @NamedQuery
    List<Transportator> findAll();

    @NamedQuery
    Transportator findByCode(@QueryParam(value = "expCode") String expCode);

    @NamedQuery
    Transportator findTransportatorByNameAndId(@QueryParam(value = "name") String name, @QueryParam(value = "id") Long id);

    @NamedQuery
    Transportator findTransportatorByExpCodeAndId(@QueryParam(value = "expCode") String expCode, @QueryParam(value = "id") Long id);

    @NamedQuery
    Transportator findTransportatorByName(@QueryParam(value = "name") String name);

    @NamedQuery
    Transportator findTransportatorByExpCode(@QueryParam(value = "expCode") String name);

    @NamedQuery
    Transportator findTransportatorByCode(@QueryParam(value = "code") String code);

    @NamedQuery
    Transportator findTransportatorIsSupportCod(@QueryParam(value = "id") Long id);

    @NamedQuery
    List<Transportator> findByPlatformCode(@QueryParam(value = "platformCode") String platformCode);

    @NamedQuery
    Transportator findTransportatorIsSupportCodByCode(@QueryParam(value = "expCode") String expCode);

    @NativeQuery
    List<Transportator> findTransportatorList(BeanPropertyRowMapperExt<Transportator> beanPropertyRowMapperExt);

    @NativeQuery
    List<Transportator> findTransportatorListAll(BeanPropertyRowMapperExt<Transportator> beanPropertyRowMapperExt);
    

    @NativeQuery
    List<TransportatorCommand> findTransportatorListByAll(@QueryParam(value = "lpCode") String lpCode, @QueryParam(value = "lpName") String lpName, @QueryParam(value = "status") Integer status, @QueryParam(value = "isCod") Integer isCod,
            BeanPropertyRowMapperExt<TransportatorCommand> beanPropertyRowMapperExt);
    /**升单
     * cheng.su
     * @param lpCode
     * @param province
     * @param city
     * @param district
     * @param TimeType
     * @param isCod
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<TransportatorCommand> findGenerStaLsingle(@QueryParam(value = "lpCode") String lpCode, @QueryParam(value = "province") String province, @QueryParam(value = "city") String city, @QueryParam(value = "district") String district, @QueryParam(value = "timeType") int TimeType,
            BeanPropertyRowMapperExt<TransportatorCommand> beanPropertyRowMapperExt);
}
