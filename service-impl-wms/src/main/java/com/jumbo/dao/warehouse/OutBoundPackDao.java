/**
 * \ * Copyright (c) 2013 Jumbomart All Rights Reserved.
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

import java.util.List;
import java.util.Map;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BaseRowMapper;
import loxia.dao.support.BeanPropertyRowMapperExt;

import com.jumbo.wms.model.warehouse.OutBoundPack;

/**
 * 出库&交接合并后，对于已出库未交接表的操作
 * 
 * @author fanht
 * 
 */
public interface OutBoundPackDao extends GenericEntityDao<OutBoundPack, Long> {

    /**
     * 出库成功后，插入中间表
     * 
     * @param lpcode
     * @param trackingNo
     * @param ouId
     * @param creatorId
     * @param packageId
     */
    @NativeUpdate
    void insertOutBoundPack(@QueryParam("lpcode") String lpcode, @QueryParam("trackingNo") String trackingNo, @QueryParam("ouId") Long ouId, @QueryParam("creatorId") Long creatorId, @QueryParam("packageId") Long packageId,
            @QueryParam("isPreSale") Boolean isPreSale);

    /**
     * 生成交接批成功后，update中间表
     * 
     * @param lpcode
     * @param trackingNo
     * @param WarehouseId
     * @param ouId
     * @param creatorId
     * @param packageIdList
     */
    @NativeUpdate
    void deleteOutBoundPack(@QueryParam("lpcode") String lpcode, @QueryParam("trackingNo") String trackingNo, @QueryParam("ouId") Long ouId, @QueryParam("creatorId") Long creatorId, @QueryParam("packageIdList") List<Long> packageIdList);

    /**
     * 初始化未交接单
     * 
     * @param creatorId
     * @param ouId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<OutBoundPack> initOutBoundPack(@QueryParam("creatorId") Long creatorId, @QueryParam("ouId") Long ouId, @QueryParam("lpCode") String lpCode, BeanPropertyRowMapperExt<OutBoundPack> beanPropertyRowMapperExt);

    // /**
    // * 初始化未交接单
    // *
    // * @param creatorId
    // * @param WarehouseId
    // * @param beanPropertyRowMapperExt
    // * @return
    // */
    // @NativeQuery
    // List<OutBoundPack> initOutBoundPack1(@QueryParam("creatorId") Long creatorId,
    // @QueryParam("opcId") Long opcId, BeanPropertyRowMapperExt<OutBoundPack>
    // beanPropertyRowMapperExt);

    /**
     * 初始化未交接单，check
     * 
     * @param creatorId
     * @param ouId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<OutBoundPack> initOutBoundPackCheck(@QueryParam("creatorId") Long creatorId, @QueryParam("ouId") Long ouId, BeanPropertyRowMapperExt<OutBoundPack> beanPropertyRowMapperExt);

    // /**
    // * KJL
    // * @param creatorId
    // * @param opcId
    // * @param beanPropertyRowMapperExt
    // * @return
    // */
    // @NativeQuery
    // List<OutBoundPack> initOutBoundPackCheck1(@QueryParam("creatorId") Long creatorId,
    // @QueryParam("opcId") Long opcId, BeanPropertyRowMapperExt<OutBoundPack>
    // beanPropertyRowMapperExt);
    /**
     * 删除包裹号
     */
    @NativeUpdate
    void deleteInfoByStaAndPackage(@QueryParam("staId") Long id, @QueryParam("status") int value);

    /**
     * 根据作业单查找出库操作未交接的包裹
     * 
     * @param staId
     * @return
     */
    @NativeQuery
    Map<String, Long> findTrankNoByStaId(@QueryParam("staId") Long staId, BaseRowMapper<Map<String, Long>> baseRowMapper);

    /**
     * 用户下未交接清单
     * 
     * @param creatorId
     * @param ouId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<OutBoundPack> oneOutBoundPack(@QueryParam("creatorId") Long creatorId, @QueryParam("ouId") Long ouId, BeanPropertyRowMapperExt<OutBoundPack> beanPropertyRowMapperExt);

    /**
     * 作废交接清单明细行，删除中间表
     * 
     * @param lpcode
     * @param trackingNo
     * @param WarehouseId
     * @param ouId
     * @param creatorId
     * @param packageIdList
     */
    @NativeUpdate
    void deleteOneOutBoundPack(@QueryParam("pId") Long pId);

    /**
     * 根据交接清单插入交接清单明细
     * 
     * @param maxlimit
     * @param hoId
     * @return
     */
    @NativeUpdate
    int insertAutoWhHandOverList(@QueryParam("maxlimit") Integer maxlimit, @QueryParam("lpCode") String lpCode, @QueryParam("hoId") Long hoId);

    /**
     * 更新明细信息到交接清单中
     * 
     * @param hoId
     * @return
     */
    @NativeUpdate
    int updateHandOverListByLine(@QueryParam("hoId") Long hoId);

    /**
     * 保存交接清单仓库
     * 
     * @param hoId
     * @return
     */
    @NativeUpdate
    int insertAutoWareHandOverList(@QueryParam("hoId") Long hoId);


    /**
     * 更新包裹的交接清单明细
     * 
     * @param hoId
     * @return
     */
    @NativeUpdate
    int updatePackageInfoByHoList(@QueryParam("hoId") Long hoId);


    /**
     * 自动化仓生成交接批成功后，设置中间表 已交接
     * 
     * @param hoId
     * @return
     */
    @NativeUpdate
    int deleteAutoWhOutBoundPack(@QueryParam("hoId") Long hoId);

    /**
     * 自动化仓交接，作业单状态刷完成
     * 
     * @param hoId
     * @return
     */
    @NativeUpdate
    int updateStaFinishByHoList(@QueryParam("hoId") Long hoId);

    /**
     * 自动化仓记录 订单触发时间
     * 
     * @param hoId
     * @return
     */
    @NativeUpdate
    int insertTimeRefByHoList(@QueryParam("hoId") Long hoId, @QueryParam("createId") Long createId);

    /**
     * 自动化仓交接，合并订单状态刷完成
     * 
     * @param hoId
     * @return
     */
    @NativeUpdate
    int updateMergeStaFinishByHoList(@QueryParam("hoId") Long hoId);

    /**
     * 自动化仓记录 合并订单触发时间
     * 
     * @param hoId
     * @return
     */
    @NativeUpdate
    int insertMergeTimeRefByHoList(@QueryParam("hoId") Long hoId, @QueryParam("createId") Long createId);
}
