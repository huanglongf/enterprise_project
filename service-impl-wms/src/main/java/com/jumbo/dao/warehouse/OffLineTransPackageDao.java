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

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.TransPackage;
import com.jumbo.wms.model.warehouse.TransPackageCommand;
import com.jumbo.wms.model.warehouse.TransStaRecord;

@Transactional
public interface OffLineTransPackageDao extends GenericEntityDao<TransPackage, Long> {

    @NativeQuery(pagable = true)
    Pagination<TransPackageCommand> getTransPackagePage(int start, int pagesize, @QueryParam("ouId") long ouId, @QueryParam("transNo") String transNo, @QueryParam("costCenterType") String costCenterType,
            @QueryParam("transportatorCode") String transportatorCode, @QueryParam("userName") String userName, @QueryParam("businessType") String businessType, @QueryParam("costCenterDetail") String costCenterDetail,
            @QueryParam("startTime") Date startTime, @QueryParam("endTime") Date endTime, @QueryParam("receiverProvince") String receiverProvince, @QueryParam("receiverCity") String receiverCity, @QueryParam("receiverArea") String receiverArea,
            @QueryParam("staType") Integer staType, @QueryParam("staCode") String staCode, @QueryParam("slipCode")String slipCode,RowMapper<TransPackageCommand> r, Sort[] sorts);

    /**
     * 查询出状态为0，order_id且第一个运单号的TransPackage实体
     */
    @NativeQuery(model = TransPackage.class)
    TransPackage getTransPackage(@QueryParam("orderId") Long orderId, RowMapper<TransPackage> r);

    /**
     * 根据运单号跟新TransPackage实体
     */
    @NativeUpdate
    int updateTransPackage(@QueryParam("id") Long id, @QueryParam("skuId") long skuId, @QueryParam("packageWeight") Double packageWeight, @QueryParam("volume") Double volume);

    /**
     * 根据orderId获得指令明细List
     */
    @NativeQuery(model = StockTransApplicationCommand.class)
    List<StockTransApplicationCommand> queryBatchSta(@QueryParam("orderId") long orderId, Sort[] sorts);

    /**
     * 根据transNo,id获得包裹实体
     */
    @NativeQuery(model = TransPackage.class)
    TransPackageCommand getOneTransPackage(@QueryParam("transNo") String transNo, @QueryParam("id") long id, RowMapper<TransPackageCommand> r);

    /**
     * 根据transNo,id获得包裹实体明细
     */
    @NativeQuery(model = TransPackage.class)
    TransPackageCommand getOneTransPackageDetail(@QueryParam("transNo") String transNo, @QueryParam("id") long id, RowMapper<TransPackageCommand> r);

    /**
     * 根据仓库id查询出月结账号
     */
    @NativeQuery(model = TransPackage.class)
    StaDeliveryInfoCommand getStaDeliveryInfoCommand(@QueryParam("whOuId") long whOuId, RowMapper<StaDeliveryInfoCommand> r);

    /**
     * 根据transNo,id获得包裹实体
     */
    @NativeQuery(model = TransPackage.class)
    TransPackageCommand getOneTransPackage2(@QueryParam("transNo") String transNo, RowMapper<TransPackageCommand> r);

    /**
     * 根据耗材barCode来获得实体
     */
    @NativeQuery(model = Sku.class)
    Sku getVolumnByBarCode(@QueryParam("skuId") String skuId, RowMapper<com.jumbo.wms.model.baseinfo.Sku> r);

    /**
     * 根据作业code来获取第一个sta实体
     */
    @NativeQuery(model = TransStaRecord.class)
    TransStaRecord getOneStaRecord(@QueryParam("code") String code, RowMapper<TransStaRecord> r);

    /**
     * 获取未完成的运单数
     */
    @NativeQuery(model = TransPackage.class)
    TransPackageCommand getNoNum(@QueryParam("userId") Long userId, RowMapper<TransPackageCommand> r);

    /**
     * 查询序列
     */
    @NativeQuery(model = TransPackage.class)
    Long getOrderCode(SingleColumnRowMapper<Long> r);
}
