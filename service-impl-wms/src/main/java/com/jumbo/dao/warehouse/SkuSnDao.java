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

import java.util.List;
import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.MapRowMapper;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.SkuSnCommand;
import com.jumbo.wms.model.warehouse.SkuSnStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

@Transactional
public interface SkuSnDao extends GenericEntityDao<SkuSn, Long> {
    @NativeUpdate
    void updateSNStatusByStvIdSql(@QueryParam(value = "stvid") Long stvId, @QueryParam(value = "status") int status);

    @NativeUpdate
    void unbindInvalidCard(@QueryParam(value = "stvid") Long stvId);

    @NativeUpdate
    void updateInboundByStvIdSql(@QueryParam(value = "stvid") Long stvId, @QueryParam(value = "status") int status, @QueryParam(value = "batchCode") String batchCode);

    @NativeUpdate
    void updateInboundByStvIdSql2(@QueryParam(value = "oldStvId") Long oldStvId, @QueryParam(value = "newStvId") Long newStvId, @QueryParam(value = "status") int status, @QueryParam(value = "batchCode") String batchCode);

    @NativeUpdate
    void updateTransitCrossInByStvIdSql(@QueryParam(value = "stvid") Long stvid, @QueryParam(value = "status") Integer status);

    @NativeUpdate
    void updateTransitCrossInByStvIdSql2(@QueryParam(value = "oldStvId") Long oldStvid, @QueryParam(value = "newStvId") Long newStvid, @QueryParam(value = "status") Integer status);


    /**
     * 根据STV查询导入sn批次号
     * 
     * @param stvId
     * @param r
     * @return
     */
    @NativeQuery
    String findBatchCodeByStv(@QueryParam(value = "stvid") Long stvId, SingleColumnRowMapper<String> r);

    @NativeUpdate
    void deleteSNByStvIdSql(@QueryParam(value = "stvid") Long stvId);

    /**
     * 
     * 方法说明：根据ID删
     * 
     * @author LuYingMing
     * @param id
     */
    @NativeUpdate
    void deleteSkuSnById(@QueryParam(value = "skuSnId") Long skuSnId);

    @NativeUpdate
    void updateStatusBySn(@QueryParam(value = "sn") String sn, @QueryParam(value = "status") int status, @QueryParam(value = "stvid") Long stvId);

    @NativeUpdate
    void updateStatusBySnAndSta(@QueryParam(value = "sn") String sn, @QueryParam(value = "status") int status, @QueryParam(value = "stvid") Long stvId, @QueryParam(value = "staId") Long staId);

    /**
     * 
     * @param stvId
     */
    @NativeUpdate
    void updateOutBoundSnRevertByStv(@QueryParam(value = "stvid") Long stvId);

    @NativeQuery
    SkuSnCommand findBySn(@QueryParam(value = "sn") String sn, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "status") Integer status, RowMapper<SkuSnCommand> r);

    @NativeQuery
    SkuSnCommand findSnByOuId(@QueryParam(value = "sn") String sn, @QueryParam(value = "ouid") Long ouid, RowMapper<SkuSnCommand> r);


    @NativeQuery
    SkuSnCommand findBySkuId(@QueryParam(value = "skuId") Long skuId, @QueryParam(value = "sn") String sn, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "status") Integer status, RowMapper<SkuSnCommand> r);


    @NamedQuery
    SkuSn findSkuSnBySn(@QueryParam(value = "sn") String sn, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "status") SkuSnStatus status);

    @NamedQuery
    SkuSn findSkuSnBySnAndStaId(@QueryParam(value = "sn") String sn, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "staid") Long staid, @QueryParam(value = "status") SkuSnStatus status);

    @NamedQuery
    List<SkuSn> findSkuSnListByStv(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "status") SkuSnStatus status);

    @NamedQuery
    SkuSn findSkuSnBySkuSn(@QueryParam(value = "skuId") Long skuId, @QueryParam(value = "sn") String sn, @QueryParam(value = "ouid") Long ouid);

    @NamedQuery
    SkuSn findSkuSnBySnSingle(@QueryParam(value = "sn") String sn);

    /**
     * 获取所有stv中需要sn商品的sn号
     * 
     * @param stvId
     * @param r
     * @return key:sn;value:Skuid
     */
    @NativeQuery
    Map<String, Long> findAllSnByStv(@QueryParam(value = "stvid") Long stvId, @QueryParam(value = "ouid") Long ouid, MapRowMapper r);

    /**
     * 获取所有stv中需要sn商品的sn号
     * 
     * @param stvId
     * @param r
     * @return key:sn;value:Skuid
     */
    @NativeQuery
    Map<String, Long> findAllSnByStvWithStatus(@QueryParam(value = "stvid") Long stvId, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "status") Integer status, MapRowMapper r);

    /**
     * 获取所有stv中需要sn商品的sn号
     * 
     * @param stvId
     * @param r
     * @return key:sn;value:Skuid
     */
    @NativeQuery
    Map<String, Long> findAllSnByStvWithStatusExt(@QueryParam(value = "stvid") Long stvId, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "status") Integer status, MapRowMapper r);

    /**
     * 获取仓库中所有SN号
     * 
     * @param skuId
     * @param ouId
     * @param r
     * @return
     */
    @NativeQuery
    Map<String, Long> findAllByOu(@QueryParam(value = "ouId") Long ouId, MapRowMapper r);

    @NativeUpdate
    void deleteByIc(@QueryParam(value = "icid") Long icid);

    @NativeUpdate
    void createByIc(@QueryParam(value = "icid") Long icid, @QueryParam(value = "batchCode") String batchCode);

    @NativeQuery
    Map<String, Long> findIsSnSkuQtyByStvId(@QueryParam(value = "stvid") Long stvid, @QueryParam(value = "ouid") Long ouid, MapRowMapper mapRowMapper);

    @NativeQuery
    Long findSnSkuQtyByStaId(@QueryParam(value = "staID") Long staID, @QueryParam(value = "ouid") Long ouid, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    Long findSnImpCountByStv(@QueryParam(value = "stvid") Long stvid, SingleColumnRowMapper<Long> r);


    /**
     * 获取所有stv中所有sn
     * 
     * @param stvId
     * @param r
     * @return key:sn;value:Skuid
     */
    @NativeQuery
    List<SkuSnCommand> findAllSnListByStvWithStatus(@QueryParam(value = "stvid") Long stvId, @QueryParam(value = "status") Integer status, RowMapper<SkuSnCommand> r);

    @NativeQuery
    List<SkuSnCommand> findAllSnListByStaWithStatus(@QueryParam(value = "staId") Long staId, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "status") Integer status, RowMapper<SkuSnCommand> r);

    @NativeUpdate
    void createSnByInboudImport(@QueryParam(value = "sn") String sn, @QueryParam(value = "status") int status, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "skuid") Long skuid, @QueryParam(value = "stvid") Long stvid);

    @NativeUpdate
    void createSnImport(@QueryParam(value = "sn") String sn, @QueryParam(value = "status") int status, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "skuid") Long skuid);

    @NativeUpdate
    void updateSn(@QueryParam(value = "sn") String sn, @QueryParam(value = "skuid") Long skuid);

    @NativeUpdate
    void createSnBysnNumberImport(@QueryParam(value = "sn") String sn, @QueryParam(value = "code") String code, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "supplierCode") String supplierCode, @QueryParam(value = "name") Long name);

    @NativeUpdate
    void deleteSnByStvAndSku(@QueryParam(value = "ouid") Long ouid, @QueryParam(value = "skuid") Long skuid, @QueryParam(value = "stvid") Long stvid);

    /**
     * 库间移动
     * 
     * @param sn
     * @param status
     * @param ouid
     * @param skuid
     * @param stvid
     */
    @NativeUpdate
    void createInSnByOutStv(@QueryParam(value = "staId") Long staId, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "stvId") Long stvId);

    @NativeUpdate
    void updateSNStvIdByStvId(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "newStvId") Long newStvId);

    /**
     * 查询给定仓库查询可用的Sn号
     * 
     * @param string
     * 
     * @param parseLong
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<SkuSn> getSkuSnByWhId(@QueryParam("skuCode") String string, @QueryParam("whId") Long whId, BeanPropertyRowMapper<SkuSn> beanPropertyRowMapper);

    /**
     * 查询给定SKU查询Sn号
     * 
     * @param string
     * 
     * @param parseLong
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<SkuSnCommand> getSkuSnBySku(@QueryParam("skuCode") String string, @QueryParam("whId") Long whId, BeanPropertyRowMapperExt<SkuSnCommand> beanPropertyRowMapperExt);

    /**
     * 查询给定SKU查询Sn号2
     * 
     * @param string
     * 
     * @param parseLong
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    List<SkuSnCommand> getSkuSnBySku2(@QueryParam("skuCode") String string, @QueryParam("whId") Long whId, BeanPropertyRowMapper<SkuSnCommand> beanPropertyRowMapper);

    /**
     * 跟新sn号信息
     * 
     * @param stvId
     * @param ouId
     * @param poId
     */
    @NativeUpdate
    void updateSnByOuAndStv(@QueryParam("stvId") Long id, @QueryParam("ouId") Long ouId, @QueryParam("poId") Long poId);

    /**
     * 根据StvId 批量占用SN
     * 
     * @param id
     * @param value
     * @param snList
     */
    @NativeUpdate
    void occupiedSnByStvId(@QueryParam("id") Long id, @QueryParam("status") int value, @QueryParam("snList") List<String> snList);

    /**
     * 香港站新增
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<SkuSn> getErrorSn(@QueryParam("staId") Long id, BeanPropertyRowMapper<SkuSn> beanPropertyRowMapper);

    /**
     * 香港站新增
     * 
     * @param id
     */
    @NativeUpdate
    void batchUpdateSn(@QueryParam("staId") Long id);

    /**
     * 香港站新增
     * 
     * @param id
     * @param stvId
     * @param ouId
     * @param batchCode
     */
    @NativeUpdate
    void batchInsertSn(@QueryParam("inId") Long id, @QueryParam("stvId") Long stvId, @QueryParam("ouId") Long ouId, @QueryParam("bc") String batchCode);

    @NativeQuery
    StockTransApplication getStaIdBySnStv(@QueryParam(value = "ouid") Long ouid, @QueryParam(value = "sn") String sn, RowMapper<StockTransApplication> r);

    @NativeQuery
    SkuSnCommand checkStvBinding(@QueryParam(value = "staid") Long staid, RowMapper<SkuSnCommand> r);

    @NativeQuery
    SkuSnCommand activateCardStatus(@QueryParam(value = "staid") Long staid, @QueryParam(value = "sn") String sn, RowMapper<SkuSnCommand> r);

    @NativeQuery
    Long getSnCountForStvSku(@QueryParam(value = "staid") Long staid, @QueryParam(value = "barcode") String barcode, @QueryParam(value = "cardStatus") Integer cardStatus, SingleColumnRowMapper<Long> r);

    @NativeQuery
    List<StockTransApplicationCommand> printSnCardErrorStaList(@QueryParam(value = "staCode") String staCode, @QueryParam(value = "plCode") String plCode, RowMapper<StockTransApplicationCommand> r);

    @NativeQuery
    List<SkuSnCommand> getSnCardErrorListSkuCount(@QueryParam(value = "staid") Long staid, RowMapper<SkuSnCommand> r);

    @NativeQuery
    List<SkuSnCommand> getSnCardListByStaIdAndCardStatus(@QueryParam(value = "staid") Long staid, @QueryParam(value = "cardstatus") Integer cardstatus, @QueryParam(value = "skuid") Long skuid, RowMapper<SkuSnCommand> r);

    /**
     * 根据inLineId来查询所有的SN记录
     * 
     * @param inlineid
     * @param r
     * @return
     */
    @NativeQuery
    List<SkuSnCommand> getSkuSnsByInLineId(@QueryParam(value = "inLineId") Long inlineid, RowMapper<SkuSnCommand> r);

    /**
     * 根据outId来查询所有的SN记录
     * 
     * @param outid
     * @param r
     * @return
     */
    @NativeQuery
    List<SkuSnCommand> getSkuSnsByOutId(@QueryParam(value = "outId") Long outid, RowMapper<SkuSnCommand> r);

    /**
     * 根据SN号来删除SkuSn数据
     * 
     * @param sn
     */
    @NativeUpdate
    void deleteSkuSnBySn(@QueryParam(value = "sn") String sn);

    /**
     * 根据作业单ID查找未核对的SN， 星巴克
     * 
     * @param staID
     * @param ouid
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long findSnQtyByStaIdAndSn(@QueryParam(value = "staId") Long staID, @QueryParam(value = "sn") String sn, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据作业单ID查找未核对的SN， 星巴克
     * 
     * @param staID
     * @param ouid
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long findisCheckSnQtyBySn(@QueryParam(value = "staId") Long staID, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "sn") String sn, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    SkuSn findSkusnByStaAndSn(@QueryParam(value = "staid") Long staid, @QueryParam(value = "sn") String sn, @QueryParam(value = "ouId") Long ouId, RowMapper<SkuSn> r);

    /**
     * 计算当前sn的商品的总数
     * 
     * @param staID
     * @param sn
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long findNoCheckSNCount(@QueryParam(value = "staId") Long staID, @QueryParam(value = "sn") String sn, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据skuId stvId查询SN号
     * 
     * @param stvId
     * @param sn
     * @return
     */
    @NamedQuery
    List<SkuSn> getbyStvIdSkuIdStatus(@QueryParam(value = "stvId") Long stvId, @QueryParam(value = "skuId") Long sn);

    /**
     * 
     * 
     * @param uniqueId
     * @param sn
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    SkuSn findIfCanUseBySn(@QueryParam("staLineId") Long uniqueId, @QueryParam("sn") String sn, BeanPropertyRowMapper<SkuSn> beanPropertyRowMapper);


    /**
     * 方法说明：根据SKUID和SN查询 SkuSn
     * 
     * @author LuYingMing
     * @date 2016年9月19日 下午4:05:47
     * @param skuId
     * @param sn
     * @param r
     * @return
     */
    @NativeQuery
    SkuSnCommand findBySkuIdAndSn(@QueryParam(value = "skuId") Long skuId, @QueryParam(value = "sn") String sn, RowMapper<SkuSnCommand> r);

    /**
     * 方法说明：根据绑定staId和SN查询 SkuSn
     * 
     */
    @NativeQuery
    SkuSnCommand findByStaIdAndSn(@QueryParam(value = "staId") Long staId, @QueryParam(value = "sn") String sn, RowMapper<SkuSnCommand> r);
}
