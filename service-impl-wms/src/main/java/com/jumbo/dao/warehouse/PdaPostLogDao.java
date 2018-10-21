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
import java.util.Map;

import loxia.annotation.DynamicQuery;
import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import com.jumbo.wms.model.warehouse.PdaPostLog;
import com.jumbo.wms.model.warehouse.PdaPostLogCommand;

@Transactional
public interface PdaPostLogDao extends GenericEntityDao<PdaPostLog, Long> {

    /**
     * 根据STA查找PDA记录
     * 
     * @param code
     * @param param
     * @param sorts
     * @param r
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<PdaPostLogCommand> findByStaCode(int start, int pageSize, @QueryParam("code") String code, @QueryParam Map<String, Object> param, Sort sorts[], RowMapper<PdaPostLogCommand> r);

    /**
     * 删除单据未执行所有PDA日志
     * 
     * @param code
     * @param userid
     */
    @NativeUpdate
    void deleteAllLogByCode(@QueryParam("code") String code, @QueryParam("userid") Long userid);


    @DynamicQuery
    List<PdaPostLog> findPdaPostLogByCode(@QueryParam("code") String code, @QueryParam("pdaCode") String pdaCode);

    /**
     * 完成单据未执行所有PDA日志
     * 
     * @param code
     * @param userid
     */
    @NativeUpdate
    void finishLogByCode(@QueryParam("code") String code, @QueryParam("pdaCode") String pdaCode);

    /**
     * 查询单据PDA扫描统计结果
     * 
     * @param code
     * @return
     */
    @NativeQuery
    List<PdaPostLogCommand> findPdaLogwithGroup(@QueryParam("code") String code, RowMapper<PdaPostLogCommand> r);

    @NamedQuery
    PdaPostLog findSavedLog(@QueryParam("code") String code, @QueryParam("pdaCode") String pdaCode, @QueryParam("locCode") String locCode, @QueryParam("skuBarcode") String skuBarcode, @QueryParam("createTime") Date createTime);

    // 处理
    @NativeUpdate
    void updateLocationPostPdaLog(@QueryParam("staCode") String staCode, @QueryParam("ouid") Long ouid);

    // 处理
    @NativeUpdate
    void updateSkuPostPdaLog(@QueryParam("staCode") String staCode);

    // 处理
    @NativeUpdate
    void updateAddSkuBarCodePostPdaLog(@QueryParam("staCode") String staCode);

    // 处理
    @NativeUpdate
    void updateSkuBySNPostPdaLog(@QueryParam("staCode") String staCode);

    // 删除
    @NativeUpdate
    void deletePostPdaErrorLog(@QueryParam("staCode") String staCode);

    // 导出
    @NativeQuery
    List<PdaPostLog> findPdaErrorLogByStaCode(@QueryParam("staCode") String staCode, BeanPropertyRowMapperExt<PdaPostLog> beanPropertyRowMapperExt);

    // 查询pdacode
    @NativeQuery
    List<PdaPostLogCommand> queryPdaCodeByStaId(@QueryParam("staId") Long staId, RowMapper<PdaPostLogCommand> r);


}
