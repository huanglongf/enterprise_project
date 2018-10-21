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
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.PickingListObjRowMapper;
import com.jumbo.wms.model.jasperReport.PickingListObj;
import com.jumbo.wms.model.jasperReport.SpecialPackagingData;
import com.jumbo.wms.model.warehouse.AllocateCargoOrderCommand;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.PickingListStatus;
import com.jumbo.wms.model.warehouse.PickingMode;
import com.jumbo.wms.web.commond.CheckInfoCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;


@Transactional
public interface PickingListDao extends GenericEntityDao<PickingList, Long> {

    @NamedQuery
    List<PickingList> findOperatorPickingListByStatus(@QueryParam(value = "operatorId") Long operatorId, @QueryParam(value = "status") PickingListStatus status, @QueryParam(value = "ouId") Long ouId, Sort[] sorts);

    /**
     * 获取税控发票导出文件名
     * 
     * @param ouid
     * @return
     */
    @NativeQuery
    String findExportFileName(@QueryParam(value = "pickingListId") Long pickingListId, SingleColumnRowMapper<String> s);


    /**
     * 查询配货后形单列表 配货清单状态为：待配货与失败
     * 
     * @param pickingMode
     * @param operatorId
     * @param ouId
     * @param sorts
     * @return
     */
    @NamedQuery
    List<PickingList> findAfDeLiveryList(@QueryParam(value = "pickingMode") PickingMode pickingMode, @QueryParam(value = "ouId") Long ouId, Sort[] sorts);


    @NamedQuery
    List<PickingList> findByStatus(@QueryParam(value = "pickingMode") PickingMode pickingMode, @QueryParam(value = "status") PickingListStatus status, @QueryParam(value = "ouId") Long ouId, Sort[] sorts);


    /**
     * 打印配货出库单-Old
     * 
     * @param pickingListId
     * @param ouid
     * @return
     */
    @NativeQuery
    List<PickingListCommand> findPickingListByPickingId(@QueryParam(value = "pickingListId") Long pickingListId, @QueryParam(value = "pickZoneId") Integer pickZoneId, @QueryParam(value = "ouid") Long ouid, RowMapper<PickingListCommand> rowMapper);

    @NativeQuery
    List<PickingListCommand> findPickingListByPickingIdNew(@QueryParam(value = "pickingListId") Long pickingListId, @QueryParam(value = "pickZoneId") Integer pickZoneId, @QueryParam(value = "ouid") Long ouid, RowMapper<PickingListCommand> rowMapper);

    @NativeQuery
    List<PickingListCommand> findPickingListByPickingId3(@QueryParam(value = "pickingListId") Long pickingListId, @QueryParam(value = "pickZoneId") Integer pickZoneId, @QueryParam(value = "ouid") Long ouid, RowMapper<PickingListCommand> rowMapper);

    // 打印配货出库单-New
    @NativeQuery
    Map<Long, PickingListObj> findPickingListByPlid(@QueryParam(value = "plid") Long plid, @QueryParam(value = "pickZoneId") Integer pickZoneId, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "psize") String psize,
            PickingListObjRowMapper pickingListObjRowMapper);

    // 配货清单打印-new
    @NativeQuery
    Map<Long, PickingListObj> findPickingListByPlidNew(@QueryParam(value = "plid") Long plid, @QueryParam(value = "pickZoneId") Integer pickZoneId, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "psize") String psize,
            PickingListObjRowMapper pickingListObjRowMapper);

    /**
     * 核对配货清单 原始
     * 
     * @param start
     * @param pageSize
     * @param cmdMap
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> findPickingListForVerifyByCmd(int start, int pageSize, @QueryParam Map<String, Object> cmdMap, Sort[] sorts, RowMapper<PickingListCommand> rowMapper);

    /**
     * 核对配货清单 KJL
     * 
     * @param ouid
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> findPickingListForVerifyByCmd1(int start, int pageSize, @QueryParam(value = "whStatus") int whStatus, @QueryParam Map<String, Object> cmdMap, @QueryParam("checkList") List<Integer> checkList, Sort[] sorts,
            RowMapper<PickingListCommand> rowMapper);

    /**
     * O2O核对配货清单 KJL
     * 
     * @param ouid
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> findPickingListForVerifyByCmdOtwoo(int start, int pageSize, @QueryParam(value = "whStatus") int whStatus, @QueryParam Map<String, Object> cmdMap, @QueryParam("checkList") List<Integer> checkList, Sort[] sorts,
            RowMapper<PickingListCommand> rowMapper);


    @NativeQuery
    Integer queryPackageCount(@QueryParam(value = "code") String code, SingleColumnRowMapper<Integer> packageCount);

    /**
     * 核对配货清单多件核对（后置打印FXL）
     * 
     * @param ouid
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> findPickingListForVerifyByCmd2(int start, int pageSize, @QueryParam(value = "whStatus") int whStatus, @QueryParam Map<String, Object> cmdMap, @QueryParam("checkList") List<Integer> checkList, Sort[] sorts,
            RowMapper<PickingListCommand> rowMapper);

    /**
     * 多件核对(后置送货单)
     */
    @NativeQuery
    PickingListCommand findPickingListForVerifyByCodeId(@QueryParam(value = "whStatus") int whStatus, @QueryParam(value = "iptPlCode") String iptPlCode, @QueryParam Map<String, Object> cmdMap, @QueryParam("checkList") List<Integer> checkList,
            RowMapper<PickingListCommand> rowMapper);

    /**
     * 核对配货清单多件核对（后置打印）
     * 
     * @param ouid
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> findPickingListForVerifyByCmd3(int start, int pageSize, @QueryParam(value = "whStatus") int whStatus, @QueryParam Map<String, Object> cmdMap, @QueryParam("checkList") List<Integer> checkList, Sort[] sorts,
            RowMapper<PickingListCommand> rowMapper);

    /**
     * 核对配货清单O2OQS批次核对
     * 
     * @param ouid
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> findPickingListForVerifyByCmd4(int start, int pageSize, @QueryParam(value = "whStatus") int whStatus, @QueryParam Map<String, Object> cmdMap, @QueryParam("checkList") List<Integer> checkList, Sort[] sorts,
            RowMapper<PickingListCommand> rowMapper);

    /**
     * O2OQS批次号查询
     */
    @NativeQuery
    PickingListCommand findPLForVerifyByPickingListCode(@QueryParam(value = "plCode") String plCode, @QueryParam(value = "whStatus") int whStatus, @QueryParam Map<String, Object> cmdMap, @QueryParam("checkList") List<Integer> checkList,
            RowMapper<PickingListCommand> rowMapper);

    /**
     * 核对配货清单 KJL
     * 
     * @param ouid
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> findPickingListForVerifyByCmd1opc(int start, int pageSize, @QueryParam Map<String, Object> cmdMap, @QueryParam("checkList") List<Integer> checkList, Sort[] sorts, RowMapper<PickingListCommand> rowMapper,
            @QueryParam("wids") List<Long> wids);

    /**
     * 核对配货清单 KJL
     * 
     * @param ouid
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> findPickingListForVerifyByCmd1B(int start, int pageSize, @QueryParam Map<String, Object> cmdMap, @QueryParam("checkList") List<Integer> checkList, @QueryParam("wids") List<Long> wids, Sort[] sorts,
            RowMapper<PickingListCommand> rowMapper);

    /**
     * 配货清单查询
     * 
     * @param ouid
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<AllocateCargoOrderCommand> findPickingListForModel(int start, int pageSize, @QueryParam Map<String, Object> cmdMap, Sort[] sorts, @QueryParam("wouid") Long wouid, @QueryParam("wids") List<Long> wids,
            RowMapper<AllocateCargoOrderCommand> rowMapper);


    @NativeQuery
    PickingListCommand findById(@QueryParam(value = "plId") Long plId, RowMapper<PickingListCommand> rowMap);

    /**
     * 更新核对单数与数量
     */
    @NativeUpdate
    void addCheckedCount(@QueryParam(value = "plId") Long plId, @QueryParam(value = "skuQty") Integer skuQty);


    @NativeQuery
    List<PickingListCommand> findDetailInfoById(@QueryParam(value = "plId") Long plId, RowMapper<PickingListCommand> rowMap);

    /**
     * 跟新打印次数
     * 
     * @param plId
     */
    @NativeUpdate
    void updateOutputCount(@QueryParam(value = "plId") Long plId);

    /**
     * 查寻所有的配货清单
     * 
     * @param start
     * @param pageSize
     * @param cmdMap
     * @param sorts
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<AllocateCargoOrderCommand> findPickingListAll(int start, int pageSize, @QueryParam Map<String, Object> cmdMap, @QueryParam(value = "ouId") Long ouId, Sort[] sorts, RowMapper<AllocateCargoOrderCommand> rowMapper);

    @NativeQuery
    BigDecimal findOutputCount(@QueryParam(value = "plId") Long plId, SingleColumnRowMapper<BigDecimal> s);

    /**
     * 查找对应批次号创建人和修改人
     * 
     * @param plId
     * @param rowMap
     * @return
     */
    @NativeQuery
    PickingListCommand findPackingByBatchCode(@QueryParam(value = "id") Long id, RowMapper<PickingListCommand> rowMap);

    @NativeQuery
    PickingListCommand findPackinglistByCode(@QueryParam(value = "whStatus") int whStatus, @QueryParam Map<String, Object> cmdMap, RowMapper<PickingListCommand> rowMap);

    @NativeQuery
    List<PickingListCommand> findPickingListInfo(Sort[] sorts, @QueryParam(value = "code") String code, @QueryParam(value = "createTimeStart") Date createTimeStart, @QueryParam(value = "createTimeEnd") Date createTimeEnd,
            @QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapperExt<PickingListCommand> beanPropertyRowMapperExt);

    @NativeQuery
    List<PickingListCommand> findPickingListDieking(Sort[] sorts, @QueryParam(value = "code") String code, @QueryParam(value = "createTimeStart") Date createTimeStart, @QueryParam(value = "createTimeEnd") Date createTimeEnd,
            @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "addstatus") int addstatus, BeanPropertyRowMapperExt<PickingListCommand> beanPropertyRowMapperExt);

    @NativeQuery
    PickingList findPickListByCode(@QueryParam(value = "code") String code, @QueryParam(value = "ouId") Long ouId, BeanPropertyRowMapperExt<PickingList> beanPropertyRowMapperExt);

    @NativeQuery
    List<PickingListCommand> findPickingListInfoFast(Sort[] sorts, @QueryParam(value = "code") String code, @QueryParam(value = "createTimeStart") Date createTimeStart, @QueryParam(value = "createTimeEnd") Date createTimeEnd,
            BeanPropertyRowMapperExt<PickingListCommand> beanPropertyRowMapperExt);

    /**
     * 二次分拣意见 fanht
     * 
     * @param start
     * @param pageSize
     * @param plCmd
     * @param ouid
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> findPickingListInfo(int start, int pageSize, Sort[] sorts, @QueryParam(value = "code") String code, @QueryParam(value = "createTimeStart") Date createTimeStart, @QueryParam(value = "createTimeEnd") Date createTimeEnd,
            @QueryParam(value = "ouid") Long ouid, BeanPropertyRowMapper<PickingListCommand> BeanPropertyRowMapper);

    /**
     * 配货清单拣货列表
     * 
     * @param start
     * @param pageSize
     * @param plCmd
     * @param ouid
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> findPickingListDieking(int start, int pageSize, Sort[] sorts, @QueryParam(value = "code") String code, @QueryParam(value = "createTimeStart") Date createTimeStart,
            @QueryParam(value = "createTimeEnd") Date createTimeEnd, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "addstatus") int addstatus, BeanPropertyRowMapper<PickingListCommand> BeanPropertyRowMapper);

    /**
     * 二次分拣意见 fanht
     * 
     * @param start
     * @param pageSize
     * @param plCmd
     * @param ouid
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> findPickingListInfoB(int start, int pageSize, Sort[] sorts, @QueryParam(value = "code") String code, @QueryParam(value = "createTimeStart") Date createTimeStart, @QueryParam(value = "createTimeEnd") Date createTimeEnd,
            @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "wids") List<Long> wids, @QueryParam(value = "test") Long test, BeanPropertyRowMapper<PickingListCommand> BeanPropertyRowMapper);

    /**
     * 二次分拣意见 运营中心
     * 
     * @param start
     * @param pageSize
     * @param plCmd
     * @param ouid
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> findPickingListInfoopc(int start, int pageSize, Sort[] sorts, @QueryParam(value = "code") String code, @QueryParam(value = "createTimeStart") Date createTimeStart,
            @QueryParam(value = "createTimeEnd") Date createTimeEnd, @QueryParam(value = "ouid") List<Long> ouid, BeanPropertyRowMapper<PickingListCommand> BeanPropertyRowMapper);


    @NamedQuery
    PickingList getByCode(@QueryParam("code") String code);

    /**
     * 作业单id，查询其拣货逻辑
     * 
     * @param staId
     * @param beanPropertyRowMapperExt
     * @return
     */
    @NativeQuery
    PickingList findPickingListByStaid(@QueryParam(value = "staId") Long staId, BeanPropertyRowMapperExt<PickingList> beanPropertyRowMapperExt);

    /**
     * 作业单id，查询其作业单实体
     * 
     * @param staId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    Long findPickingListByPickId(@QueryParam(value = "pid") Long pickId, SingleColumnRowMapper<Long> pcheckmode);

    /**
     * 作业单id，是否特定
     * 
     * @param staId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    Long findPickingListByPickIdS(@QueryParam(value = "pid") Long pickId, SingleColumnRowMapper<Long> pcheckmode);

    /**
     * KJL 分页查询所有配货清单
     * 
     * @param start
     * @param pageSize
     * @param code
     * @param code2
     * @param isInvoice
     * @param skuSizeId
     * @param categoryId
     * @param value
     * @param lpCode
     * @param isSn
     * @param id
     * @param sorts
     * @param isPreSale1
     * @param isMacaoOrder
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingList> getAllPickingList(int start, int pageSize, @QueryParam("sendCity") String sendCity, @QueryParam("categoryId") Long categoryId, @QueryParam("skuSizeId") Long skuSizeId, @QueryParam("isInvoice") String isInvoice,
            @QueryParam("code") String code, @QueryParam("status") Integer value, @QueryParam("pickType") Integer pickType, @QueryParam("isSn") String isSn, @QueryParam("lpCode") String lpCode, @QueryParam("checkMode") Integer checkMode,
            @QueryParam("ouId") Long id, @QueryParam("worker") String worker, @QueryParam("toLocation") String toLocation, @QueryParam("isMacaoOrder") Integer isMacaoOrder, @QueryParam("isPrintMaCaoHGD") Integer isPrintMaCaoHGD, Sort[] sorts,
            @QueryParam("isPreSale") String isPreSale1, BeanPropertyRowMapper<PickingList> beanPropertyRowMapper);

    /**
     * KJL 分页查询所有配货清单
     * 
     * @param start
     * @param pageSize
     * @param code
     * @param code2
     * @param isInvoice
     * @param skuSizeId
     * @param categoryId
     * @param value
     * @param lpCode
     * @param isSn
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingList> getAllPickingQuery(int start, int pageSize, @QueryParam("sendCity") String sendCity, @QueryParam("categoryId") Long categoryId, @QueryParam("skuSizeId") Long skuSizeId, @QueryParam("isInvoice") String isInvoice,
            @QueryParam("code") String code, @QueryParam("status") Integer value, @QueryParam("isSn") String isSn, @QueryParam("lpCode") String lpCode, @QueryParam("checkMode") Integer checkMode, @QueryParam("ouId") Long id, Sort[] sorts,
            BeanPropertyRowMapper<PickingList> beanPropertyRowMapper);

    /**
     * 分页查询所有配货清单 (用于配货清单取消操作的查询)
     * 
     * @param start
     * @param pageSize
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingList> findAllPickingList(int start, int pageSize, @QueryParam("code") String code, @QueryParam("lpCode") String lpCode, @QueryParam("ouId") Long id, Sort[] sorts, BeanPropertyRowMapper<PickingList> beanPropertyRowMapper);

    @NativeQuery(pagable = true)
    Pagination<PickingList> getAllPickingListStatus(int start, int pageSize, @QueryParam("priorityList") List<String> cityList, @QueryParam("msg") Boolean msg, @QueryParam("categoryId") Long categoryId, @QueryParam("skuSizeId") Long skuSizeId, @QueryParam("isInvoice") String isInvoice,
            @QueryParam("code") String code, @QueryParam("status") Integer value, @QueryParam("pickType") Integer pickType, @QueryParam("isSn") String isSn, @QueryParam("lpCode") String lpCode, @QueryParam("checkMode") Integer checkMode,
            @QueryParam("ouId") Long id, @QueryParam("worker") String worker, @QueryParam("whstatus") int whstatus, @QueryParam("toLocation") String toLocation, Sort[] sorts, @QueryParam("isPreSale") String isPreSale, @QueryParam("flag") Boolean flag,
            @QueryParam("cityList") List<String> priorityList, BeanPropertyRowMapper<PickingList> beanPropertyRowMapper);

    @NativeQuery(pagable = true)
    Pagination<PickingList> getAllPickingListStatusBulk(int start, int pageSize, @QueryParam("priorityList") List<String> cityList, @QueryParam("msg") Boolean msg, @QueryParam("categoryId") Long categoryId, @QueryParam("skuSizeId") Long skuSizeId, @QueryParam("isInvoice") String isInvoice,
            @QueryParam("code") String code, @QueryParam("status") Integer value, @QueryParam("isSn") String isSn, @QueryParam("lpCode") String lpCode, @QueryParam("checkMode") Integer checkMode, @QueryParam("ouId") Long id,
            @QueryParam("worker") String worker, @QueryParam("whstatus") int whstatus, @QueryParam("toLocation") String toLocation, Sort[] sorts, @QueryParam("isPreSale") String isPreSale, @QueryParam("flag") Boolean flag,
            @QueryParam("cityList") List<String> priorityList, BeanPropertyRowMapper<PickingList> beanPropertyRowMapper);

    // AGV
    @NativeQuery
    List<PickingListCommand> getExportAgv(@QueryParam("areaCode") String areaCode, @QueryParam("plCode") String plCode, BeanPropertyRowMapper<PickingListCommand> beanPropertyRowMapper);



    /**
     * KJL 分页查询所有配货清单(查询仓库名称)
     * 
     * @param start
     * @param pageSize
     * @param code
     * @param value
     * @param lpCode
     * @param isSn
     * @param id
     * @param lists
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> getAllPickingListsingleopc(int start, int pageSize, @QueryParam("code") String code, @QueryParam("status") Integer value, @QueryParam("isSn") Boolean isSn, @QueryParam("lpCode") String lpCode,
            @QueryParam("checkMode") Integer checkMode, @QueryParam("ouId") Long id, @QueryParam("lists") List<Long> lists, Sort[] sorts, BeanPropertyRowMapper<PickingListCommand> beanPropertyRowMapper);

    /**
     * KJL 根据配货清单编码查询配货清单详细信息
     * 
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    PickingListCommand getPickingListByCode(@QueryParam("code") String code, @QueryParam("ouId") Long ouId, @QueryParam("checkMode") Integer checkMode, BeanPropertyRowMapper<PickingListCommand> beanPropertyRowMapper);

    @NativeQuery
    PickingListCommand getPickingListByCodeAndStatus(@QueryParam("code") String code, @QueryParam("ouId") Long ouId, @QueryParam("checkMode") Integer checkMode, @QueryParam("status") int status,
            BeanPropertyRowMapper<PickingListCommand> beanPropertyRowMapper);

    /**
     * 根据查询条件查询
     * 
     * @param date 创建时间下限
     * @param date2 创建时间上限
     * @param code 配货批次号
     * @param value 配货单状态
     * @param ouId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> getAllSecKillPickingListByStatus(int start, int pageSize, @QueryParam("fromTime") Date date, @QueryParam("toTime") Date date2, @QueryParam("code") String code, @QueryParam("status") int value,
            @QueryParam("ouId") Long ouId, Sort[] sorts, BeanPropertyRowMapper<PickingListCommand> beanPropertyRowMapper);

    /**
     * 根据查询条件查询
     * 
     * @param date 创建时间下限
     * @param date2 创建时间上限
     * @param code 配货批次号
     * @param value 配货单状态
     * @param ouId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> getAllSecKillPickingListByStatusopc(int start, int pageSize, @QueryParam("fromTime") Date date, @QueryParam("toTime") Date date2, @QueryParam("code") String code, @QueryParam("status") int value,
            @QueryParam("ouId") Long ouId, @QueryParam("wids") List<Long> wids, Sort[] sorts, BeanPropertyRowMapper<PickingListCommand> beanPropertyRowMapper);

    /**
     * 取消物流交接清单时更新配货批
     * 
     * @param hoId
     */
    @NativeUpdate
    void updatePickingListByHandOverList(@QueryParam("hoId") Long hoId);

    /**
     * 根据配货清单查询物流交接单
     * 
     * @param id
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Long getHandOverIdByPickingListId(@QueryParam("pId") Long id, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 根据产品id查找仓库id
     */
    @NativeQuery
    Long getWhidBypickid(@QueryParam("pcode") String pcode, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeUpdate
    void deleteBlankPickingById(@QueryParam("pId") Long id);

    /**
     * 根据配货批次号查询秒杀配货批基本信息
     * 
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    PickingListCommand findCheckoutPickingByCode(@QueryParam("code") String code, @QueryParam("whId") Long whId, @QueryParam("idList") List<Long> idList, BeanPropertyRowMapper<PickingListCommand> beanPropertyRowMapper);

    /**
     * 快捷进入查询配货批次号
     * 
     * @param ouId
     * @param idList
     * @param checkMode
     * @param statusList
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    PickingListCommand getSingleCheckOrder(@QueryParam("ouId") Long ouId, @QueryParam("ouList") List<Long> idList, @QueryParam("code") String code, @QueryParam("statusList") List<Integer> statusList, @QueryParam("checkMode") int checkMode,
            @QueryParam("whStatus") int whStatus, BeanPropertyRowMapper<PickingListCommand> beanPropertyRowMapper);

    /**
     * 查询Burberry 打印寄货单
     * 
     * @param plId
     * @param r
     * @return
     */
    @NativeQuery
    List<SpecialPackagingData> findBurberryPrintInfo(@QueryParam(value = "staId") Long staId, BeanPropertyRowMapper<SpecialPackagingData> beanPropertyRowMapper);

    @NativeQuery
    List<PickingListCommand> findPickListDetail(@QueryParam(value = "pcikId") Long pcId, BeanPropertyRowMapperExt<PickingListCommand> beanPropertyRowMapperExt);

    @NativeQuery
    Long getPickListMergeSta(@QueryParam(value = "plid") Long plid, @QueryParam(value = "ouid") Long ouid, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    Map<Long, PickingListObj> findPickingListByPlid1(@QueryParam(value = "plid") Long plid, @QueryParam(value = "pickZoneId") Integer pickZoneId, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "psize") String psize,
            PickingListObjRowMapper pickingListObjRowMapper);

    @NativeQuery
    Map<Long, PickingListObj> findPickingListByPlid1New(@QueryParam(value = "plid") Long plid, @QueryParam(value = "pickZoneId") Integer pickZoneId, @QueryParam(value = "ouid") Long ouid, @QueryParam(value = "psize") String psize,
            PickingListObjRowMapper pickingListObjRowMapper);

    @NativeQuery
    List<PickingListCommand> queryStaStatusByCode(@QueryParam(value = "code") String code, RowMapper<PickingListCommand> beanPropertyRowMapperExt);


    /**
     * 根据slip_code查询 YDX
     * 
     * @param id
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<AllocateCargoOrderCommand> getByslipCode(@QueryParam(value = "slipCode") String slipCode, RowMapper<AllocateCargoOrderCommand> r, Sort[] sorts);


    @NamedQuery
    PickingList findPickByCode(@QueryParam(value = "code") String code);

    @NativeQuery
    PickingListCommand findSlipCodeByid(@QueryParam("ouId") Long ouId, @QueryParam("code") String code, BeanPropertyRowMapper<PickingListCommand> beanPropertyRowMapper);

    /**
     * 配货批次分配 分页查询
     */
    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> findBatchAllocation(int start, int pageSize, @QueryParam("createTime") Date createTime, @QueryParam("endCreateDate") Date endCreateDate, @QueryParam("biginPickDate") Date biginPickDate,
            @QueryParam("endPickDate") Date endPickDate, @QueryParam("code") String code, @QueryParam("statusInt") Integer statusInt, @QueryParam("jobNumber") String jobNumber, @QueryParam("nodeType") String nodeType, Sort[] sorts,
            @QueryParam("ouId") Long ouId, RowMapper<PickingListCommand> beanPropertyRowMapper);

    @NativeQuery
    PickingListCommand findSlipCodeAndPickingListCodeByStaCode(@QueryParam("ouId") Long ouId, @QueryParam("code") String code, BeanPropertyRowMapper<PickingListCommand> beanPropertyRowMapper);


    @NativeQuery
    List<PickingListCommand> findEdwTwhStaPickingList(RowMapper<PickingListCommand> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<PickingListCommand> searchSnCardCheckList(int start, int pageSize, @QueryParam("pickingTime") Date pickingTime, @QueryParam("executedTime") Date executedTime, @QueryParam("slipCode") String slipCode, @QueryParam("staCode") String staCode,
            @QueryParam("status") Integer status, @QueryParam("code") String code, @QueryParam("ouid") Long ouid, Sort[] sorts, RowMapper<PickingListCommand> rowMapper);

    @NativeQuery
    PickingListCommand getSnCardErrorPl(@QueryParam("id") Long id, RowMapper<PickingListCommand> rowMapper);

    @NativeQuery
    PickingListCommand findPickListBySlipCode(@QueryParam("code") String code, BeanPropertyRowMapper<PickingListCommand> beanPropertyRowMapper);

    /**
     * 根据配货清单ID获取已取消的作业单的数量
     */
    @NativeQuery
    Long findCancelCountByPickId(@QueryParam("pickId") Long pickId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 获取所有批次升序
     * 
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<PickingList> findPlIds(@QueryParam("plLists") List<Long> plLists, BeanPropertyRowMapper<PickingList> beanPropertyRowMapper);

    /**
     * @param orderCode
     * @param ouId
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    PickingList getCheckOrderByCode(@QueryParam("orderCode") String orderCode, @QueryParam("ouId") Long ouId, BeanPropertyRowMapper<PickingList> beanPropertyRowMapper);

    /**
     * 根据配货清单查询待核对信息
     * 
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<CheckInfoCommand> findCheckInfoByPickingListCode(@QueryParam("code") String code, BeanPropertyRowMapper<CheckInfoCommand> beanPropertyRowMapper);

    /**
     * 根据周转箱查询待核对信息
     * 
     * @param code
     * @param pickingListCommandRowMapper
     * @return
     */
    @NativeQuery
    List<CheckInfoCommand> findCheckInfoByContainerCode(@QueryParam("code") String code, BeanPropertyRowMapper<CheckInfoCommand> beanPropertyRowMapper);

    /**
     * 根据作业单查询待核对信息
     * 
     * @param code
     * @param pickingListCommandRowMapper
     * @return
     */
    @NativeQuery
    List<CheckInfoCommand> findCheckInfoBySlipCode(@QueryParam("code") String code, BeanPropertyRowMapper<CheckInfoCommand> beanPropertyRowMapper);

    /**
     * 查询配货清单是否支持绑定周装箱操作
     * 
     * @param pCode
     * @param ouId
     * @param beanPropertyRowMapper
     * @return
     */
    @NamedQuery
    PickingList findIsCanCheckByPcode(@QueryParam("pCode") String pCode, @QueryParam("ouId") Long ouId);

    /**
     * @param id
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Boolean findIfExistPbNotBind(@QueryParam("pId") Long id, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    /**
     * @param code
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Boolean findIsHaveReportMissingByPickingListCode(@QueryParam("code") String code, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    /**
     * @param code
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Boolean findIsHaveReportMissingByContainerCode(@QueryParam("code") String code, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    /**
     * @param code
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    Boolean findIsHaveReportMissingBySlipCode(@QueryParam("code") String code, SingleColumnRowMapper<Boolean> singleColumnRowMapper);

    /**
     * 更新配货批已经打印过拣货单
     */
    @NativeUpdate
    void updateHavePrintFlag(@QueryParam("plCode") String plCode);
    
    @NativeQuery
    PickingListCommand findSinglePickListByCode(@QueryParam(value = "code") String code, @QueryParam(value = "ouId") Long ouId, BeanPropertyRowMapperExt<PickingListCommand> beanPropertyRowMapperExt);

    /**
     * AGV出库查询
     */
    @NativeQuery
    List<PickingListCommand> saveCommomAgvOutBound(@QueryParam(value = "pId") Long pId, BeanPropertyRowMapper<PickingListCommand> BeanPropertyRowMapper);


    @NativeUpdate
    void updatePickingListStatus(@QueryParam(value = "plId") Long plId);

}
