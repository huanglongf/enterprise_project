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
import java.util.List;
import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.commandMapper.OutBoundPackageInfoObjRowMapper;
import com.jumbo.wms.model.jasperReport.OutBoundPackageInfoObj;
import com.jumbo.wms.model.jasperReport.PackingSummaryForNike;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.CartonCommand;
import com.jumbo.wms.model.warehouse.CartonLine;

@Transactional
public interface CartonDao extends GenericEntityDao<Carton, Long> {

    /**
     * 根据状态及相关条件查询箱号
     * 
     * @param start
     * @param pageSize
     * @param statusList
     * @param staStatuses
     * @param param
     * @param sorts
     * @param r
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<CartonCommand> findRtnStaCartonList(int start, int pageSize, @QueryParam("ouid") Long ouid, @QueryParam("statusList") List<Integer> statusList, @QueryParam("staStatus") List<Integer> staStatuses, @QueryParam Map<String, Object> param,
            Sort[] sorts, RowMapper<CartonCommand> r);

    @NativeQuery
    List<CartonCommand> findTrunkDetailInfoNoPage(@QueryParam(value = "staid") Long staid, RowMapper<CartonCommand> beanPropertyRowMapper, Sort[] sorts);

    /**
     * 根据staId查找Carton
     * 
     * @param staid
     * @return
     */
    @NamedQuery
    List<Carton> findCartonByStaId(@QueryParam(value = "staId") Long staid);

    /**
     * 根据Id查找CartonLine
     * 
     * @param Id
     * @return
     */
    @NamedQuery
    List<CartonLine> findCartonLineByCarId(@QueryParam(value = "Id") Long Id);

    @NativeQuery
    BigDecimal generateCartonCode(SingleColumnRowMapper<BigDecimal> rowMap);



    /**
     * 生成SEQNO
     * 
     * @param staid
     * @param r
     * @return
     */
    @NativeQuery
    String generateCartonSeqNo(@QueryParam(value = "staid") Long staid, SingleColumnRowMapper<String> r);

    /**
     * 验证唯一性
     */
    @NativeQuery
    List<Carton> checkCartonSeqNoStaId(@QueryParam(value = "staid") Long staid, @QueryParam(value = "seqNo") String seqNo, RowMapper<Carton> beanPropertyRowMapper);



    /**
     * 删除所有新建状态箱
     * 
     * @param staid
     */
    @NativeUpdate
    void deleteCreatedCartonBySta(@QueryParam(value = "staid") Long staid);

    @NativeQuery
    CartonCommand findPrintCartonDetailInfo(@QueryParam(value = "cid") Long cid, RowMapper<CartonCommand> beanPropertyRowMapper);

    /**
     * new退仓装箱单打印 bin.hu
     * 
     * @param cid
     * @param op
     * @return
     */
    @NativeQuery
    Map<Long, OutBoundPackageInfoObj> findPrintCartonDetailInfo2(@QueryParam(value = "cid") Long cid, OutBoundPackageInfoObjRowMapper op);

    @NativeQuery
    Map<Long, OutBoundPackageInfoObj> findPrintCartonDetailInfo3(@QueryParam(value = "cid") Long cid, OutBoundPackageInfoObjRowMapper op);

    @NativeQuery
    List<Carton> checkCartonstatus(@QueryParam(value = "staid") Long staid, RowMapper<Carton> beanPropertyRowMapper);

    @NativeQuery
    PackingSummaryForNike findPackingSummaryForNike(@QueryParam(value = "staId") Long staId, RowMapper<PackingSummaryForNike> beanPropertyRowMapper);

    @NativeQuery
    PackingSummaryForNike findPackingSummaryForNike2(@QueryParam(value = "staId") Long staId, @QueryParam(value = "cartonId") Long cartonId, RowMapper<PackingSummaryForNike> beanPropertyRowMapper);


    @NativeQuery
    Long findPackingCheckCount(@QueryParam(value = "staId") Long staId, SingleColumnRowMapper<Long> r);
    
    /**
     * 按箱统计每箱商品总件数
     * @param Id
     * @param r
     * @return
     */
    @NativeQuery
    Long findCartonQtyById(@QueryParam(value = "Id") Long Id, SingleColumnRowMapper<Long> r);
    
    @NamedQuery
    List<Carton> findCartonByStaIdSort(@QueryParam(value = "staId") Long staid);
    
}
