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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.pms.model.command.cond.PgPackageCreateCommand;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PackageInfoCommand;
import com.jumbo.wms.web.commond.TransWeightOrderCommand;

@Transactional
public interface PackageInfoDao extends GenericEntityDao<PackageInfo, Long> {

    /**
     * 根据sta查询所有包裹
     * 
     * @param staId
     * @return
     */
    @NamedQuery
    List<PackageInfo> findByStaId(@QueryParam(value = "staId") Long staId);

    @NativeQuery
    List<PackageInfo> findByStaIdSql(@QueryParam(value = "staid") Long staId, RowMapper<PackageInfo> r);

    /**
     * 获取包裹总重量跟By作业单ID
     */
    @NativeQuery
    PackageInfo findByStaIdWeight(@QueryParam(value = "staId") Long staId, RowMapper<PackageInfo> r);
    

    /**
     * 获取耗材总重量跟By作业单ID
     */
    @NativeQuery
    PackageInfo findByStaIdWeightAddLine(@QueryParam(value = "staId") Long staId, RowMapper<PackageInfo> r);
    
    
    /**
     * 根据快递单号查询
     * 
     * @param trackingNo
     * @return
     */
    @NamedQuery
    PackageInfo findByTrackingNo(@QueryParam(value = "trackingNo") String trackingNo);


    @NamedQuery
    PackageInfo findPackageByOldTrackingNo(@QueryParam(value = "trackingNo") String trackingNo);

    /**
     * check是否是AD预售
     */
    @NativeQuery
    PackageInfo checkAdTrackingNo(@QueryParam("trackingNo") String trackingNo, BeanPropertyRowMapperExt<PackageInfo> beanPropertyRowMapper);


    @NativeQuery(pagable = true)
    Pagination<PackageInfoCommand> findByTrackingNoAndLpCode(int start, int size, @QueryParam(value = "trackingNo") String trackingNo, @QueryParam(value = "lpCode") String lpCode, RowMapper<PackageInfoCommand> rowMapper, Sort[] sorts);

    @NativeQuery(pagable = true)
    Pagination<PackageInfo> findPreByTrackingNoAndLpCode(int start, int size, @QueryParam(value = "trackingNo") String trackingNo, @QueryParam(value = "lpCode") String lpCode, @QueryParam(value = "ouId") Long ouId, RowMapper<PackageInfo> rowMapper,
            Sort[] sorts);

    @NativeQuery
    PackageInfo findByPackageInfoByLpcode(@QueryParam("trackingNo") String trackingNo, @QueryParam("lpCode") String lpCode, BeanPropertyRowMapperExt<PackageInfo> beanPropertyRowMapper);

    /**
     * 根据快递单号查询
     * 
     * @param trackingNo
     * @return
     */
    @NamedQuery
    PackageInfo findByTrackingNoIsHandover(@QueryParam(value = "trackingNo") String trackingNo);

    /**
     * 根据快递单号查询
     * 
     * @param trackingNo
     * @return
     */
    @NamedQuery
    PackageInfo findByTrackingNos(@QueryParam(value = "trackingNo") String trackingNo);

    @NamedQuery
    PackageInfo findByTrackingNoAndStaID(@QueryParam(value = "trackingNo") String trackingNo, @QueryParam(value = "staId") Long staId);

    @NativeQuery
    List<PackageInfo> findAllPackageInfoByTrackingNo(@QueryParam(value = "trackingNo") String trackingNo, RowMapper<PackageInfo> r);

    /**
     * 根据快递单号列表查询 被拆包Sta 非整体操作的 sta Id
     * 
     * @param trackingNoList
     * @param whOuId
     * @return
     */
    @NativeQuery
    List<BigDecimal> findErrorSplitpackStaId(@QueryParam(value = "trackingNoList") List<String> trackingNoList, @QueryParam(value = "whOuId") Long whOuId, SingleColumnRowMapper<BigDecimal> rw);

    /**
     * 根据运单号、sta状态查询包裹信息
     * 
     * @param trackingNoList
     * @param staStatusList
     * @param rw
     * @return
     */
    @NativeQuery
    List<PackageInfoCommand> findByTrackingNoList(@QueryParam(value = "trackingNoList") List<String> trackingNoList, @QueryParam(value = "staStatusList") List<Integer> staStatusList, @QueryParam(value = "whOuId") Long whOuId,
            @QueryParam("idList") List<Long> idList, RowMapper<PackageInfoCommand> rw);


    @NativeQuery
    Long findUnCheckedPackageBySta(@QueryParam(value = "staId") Long staId, SingleColumnRowMapper<Long> r);

    /**
     * 删除关系快递单号
     * 
     * @param staId
     */
    @NativeUpdate
    void deletePackageInfoByStaId(@QueryParam("staId") Long staId);

    @NativeUpdate
    void insertPackageInfo(@QueryParam("lpcode") String lpcode, @QueryParam("trackingNo") String trackingNo, @QueryParam("staId") Long staId);


    /**
     * 物流对账信息导出
     * 
     * @param deliveryid
     * @param ouid
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<PackageInfoCommand> findDeliveryInfoList(@QueryParam("oulist") List<Long> oulist, @QueryParam("deliveryid") Long deliveryid, @QueryParam("ouid") Long ouid, @QueryParam("starttime") Date starttime, @QueryParam("endtime") Date endtime,
            BeanPropertyRowMapperExt<PackageInfoCommand> beanPropertyRowMapperExt);

    /**
     * 统计 物流对账信息导出的数量
     * 
     * @param packageIds
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long findDeliveryInfoCount(@QueryParam("oulist") List<Long> oulist, @QueryParam("deliveryid") Long deliveryid, @QueryParam("ouid") Long ouid, @QueryParam("starttime") Date starttime, @QueryParam("endtime") Date endtime, SingleColumnRowMapper<Long> r);

    @NativeQuery
    Long countQtyByTrackingNo(@QueryParam("lpcode") String lpcode, @QueryParam("trackingNo") String trackingNo, @QueryParam("staId") Long staId, SingleColumnRowMapper<Long> r);

    @NativeQuery
    List<String> findTrackingNosById(@QueryParam("packageIds") List<Long> packageIds, SingleColumnRowMapper<String> singleColumnRowMapper);

    /**
     * 查询出所有预售订单
     * 
     * @param packageIds
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<String> findPreSaleTrackingNosById(@QueryParam("packageIds") List<Long> packageIds, SingleColumnRowMapper<String> singleColumnRowMapper);


    /*
     * 通过快递运单号list查出仓库id
     */

    @NativeQuery
    List<Long> findWareHousesById(@QueryParam("packageIds") List<Long> packageIds, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据交接清单号 更新关联的packageInfo的状态
     * 
     * @param status
     * 
     * @param handOverListId
     */
    @NativeUpdate
    void updateStatusByHandOverListId(@QueryParam("handId") Long handOverListId, @QueryParam("status") int status);

    /**
     * 根据交接清单号 更新关联的packageInfo的状态 原始
     * 
     * @param status
     * 
     * @param handOverListId
     */
    @NativeUpdate
    void updateStatusByHandOverListId2(@QueryParam("handId") Long handOverListId, @QueryParam("status") int status);

    /**
     * 根据给定的packageInfo Id 查询已经加入交接单的packageInfo Id KJL
     * 
     * @param tnList
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findErrorPackageInfo(@QueryParam("tnList") List<Long> tnList, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据给定的作业单查询其中已经完成交接的包裹
     * 
     * @param id
     * @param status
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findFinishPackBySta(@QueryParam("staId") Long id, @QueryParam("status") int status, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据作业单删除未完成交接的包裹
     * 
     * @param id
     * @param status
     */
    @NativeUpdate
    void deleteByStaIdAndStatus(@QueryParam("staId") Long id, @QueryParam("status") int status);

    /**
     * 根据sta查询完成交接的packageInfo
     * 
     * @param id
     * @return
     */
    @NamedQuery
    List<PackageInfo> getByStaId(@QueryParam("staId") Long id);

    /**
     * 修改物流新增物流单号 KJL
     * 
     * @param lpcode
     * @param trackingNo
     * @param weight
     * @param id
     * @param b
     * @param value
     */
    @NativeUpdate
    void newPackageInfo(@QueryParam("lpcode") String lpcode, @QueryParam("transNo") String trackingNo, @QueryParam("weight") BigDecimal weight, @QueryParam("staId") Long id, @QueryParam("isChecked") boolean b, @QueryParam("status") int value,
            @QueryParam("wId") Long wId);

    /**
     * 查询包裹信息 KJL
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<PackageInfoCommand> getPackageInfoByStaId(@QueryParam("staId") Long id, BeanPropertyRowMapper<PackageInfoCommand> beanPropertyRowMapper);

    @NativeUpdate
    void updateTrackingNo(@QueryParam("pId") Long pid, @QueryParam("trackingNo") String trackingNo);

    @NativeQuery
    List<PackageInfoCommand> findEdwTwhPackageInfo(RowMapper<PackageInfoCommand> rowMapper);

    /**
     * 根据物流信息ID获取所有包裹信息
     * 
     * @param dId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<PackageInfoCommand> getpackageInfoByDeliveryInfo(@QueryParam("dId") Long dId, @QueryParam("lpCode") String lpCode, RowMapper<PackageInfoCommand> rowMapper);

    @NativeQuery
    List<String> findTrackNoByStaCode(@QueryParam("staCode") String staCode, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    Long findPackageQtyByStaIdSql(@QueryParam("staId") Long staId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /*
     * 根据运单号查询包裹信息2
     */
    @NativeQuery
    List<PackageInfoCommand> findByTrackingNoList2(@QueryParam(value = "trackingNoList") List<String> trackingNoList, @QueryParam(value = "ouid") Long ouid, @QueryParam("idList") List<Long> idList, RowMapper<PackageInfoCommand> rw);

    /**
     * 根据交接清单头id查询出包裹信息
     * 
     * @param id
     * @return
     */
    @NativeQuery
    List<PackageInfo> findPgByHoId(@QueryParam("hoId") Long hoId, RowMapper<PackageInfo> rowMapper);

    /**
     * 根据交接清单头id查询出包裹Command信息
     * 
     * @param id
     * @return
     */

    @NativeQuery
    List<PackageInfoCommand> findPgCommandByHoIdOrStaId(@QueryParam("hoId") Long hoId, @QueryParam("staId") Long staId, RowMapper<PackageInfoCommand> rowMapper);

    /**
     * 根据staid查询出包裹Command信息
     * 
     * @param id
     * @return
     */

    @NativeQuery
    List<PackageInfoCommand> findPgCommandByStaId(@QueryParam("staId") Long staId, RowMapper<PackageInfoCommand> rowMapper);



    /**
     * 根据交接清单行id查询出包裹信息
     */
    @NativeQuery
    PackageInfo findPgByLineId(@QueryParam("lineId") Long lineId, RowMapper<PackageInfo> rowMapper);

    /**
     * 根据运单号查询需称重订单信息
     * 
     * @param transNo
     * @param isSpPacking
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    TransWeightOrderCommand findInfoByTransNo(@QueryParam("transNo") String transNo, @QueryParam("isSpPacking") Boolean isSpPacking, BeanPropertyRowMapper<TransWeightOrderCommand> beanPropertyRowMapper);

    @NativeQuery(model = PgPackageCreateCommand.class)
    List<PgPackageCreateCommand> findByParams(@QueryParam("lpCode") String lpCode, @QueryParam("trackingNo") String trackingNo, @QueryParam("omsOrderCode") String omsOrderCode);

    @NativeQuery
    String findPgCountByStaId(@QueryParam("staId") Long staId, SingleColumnRowMapper<String> r);

    @NamedQuery
    PackageInfo findTrackingNoByStaDeId(@QueryParam("staDeliveryInfoId") Long Id);

    @NativeQuery
    Integer checkPreSale(@QueryParam("pId") Long pId, SingleColumnRowMapper<Integer> r);
    
    /**
     * 根据快递单号更新物流商
     */
   @NativeUpdate
    void updatelpCodeByTrackingNo(@QueryParam("lpCode")String lpCode,@QueryParam("trackingNo")String trackingNo);
}
