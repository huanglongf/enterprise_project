/**
 * \ * Copyright (c) 2010 Jumbomart All Rights Reserved.
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

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.GiftLineCommand;
import com.jumbo.wms.model.warehouse.GiftLine;

@Transactional
public interface GiftLineDao extends GenericEntityDao<GiftLine, Long> {
    /**
     * 根据STA查询 作业单中所有的特殊处理数据
     * 
     * @param staId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<GiftLineCommand> findGiftBySta(@QueryParam("staId") Long staId, @QueryParam("giftType") Integer giftType, RowMapper<GiftLineCommand> rowMapper);

    /**
     * 根据STALINEID查询 作业单中所有的特殊处理数据
     * 
     * @param staId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    GiftLineCommand findGiftByStaLine(@QueryParam("staLineId") Long staLineId, @QueryParam("giftType") Integer giftType, RowMapper<GiftLineCommand> rowMapper);

    /**
     * 根据作业单类型查询数据
     * 
     * @param staId
     * @param giftType
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<GiftLineCommand> findGiftByStaAndType(@QueryParam("staId") Long staId, @QueryParam("giftType") int giftType, RowMapper<GiftLineCommand> rowMapper);

    /**
     * 保修卡更新
     * 
     * @param glId
     */
    @NativeUpdate
    void updateSanCardCodeById(@QueryParam("glId") Long glId, @QueryParam("sanCardCode") String sanCardCode);


    /**
     * 删除
     * 
     * @param glId
     */
    @NativeUpdate
    void deleteGiftLineByStaId(@QueryParam("staId") Long staId);

    @NamedQuery
    List<GiftLine> getLinesByStalineId(@QueryParam("staId") Long staId);

    /**
     * 根据StaId查询礼品明细
     * 
     * @param start
     * @param size
     * @param staId
     * @param rowMapper
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<GiftLine> getPrintGiftLine(int start, int size, @QueryParam("staId") Long staId, RowMapper<GiftLine> rowMapper, Sort[] sorts);

    @NativeQuery
    GiftLineCommand findGiftLineByStaId(@QueryParam(value = "staId") Long staId, @QueryParam(value = "ext_code2") String ext_code2, RowMapper<GiftLineCommand> rowMapper);
    
    @NativeQuery
	GiftLine getGiftLineByStaLineIdAndType(@QueryParam(value = "staLineId") Long staLineId,@QueryParam(value = "type") Integer type, BeanPropertyRowMapper<GiftLine> r);
    
    @NativeQuery
	List<GiftLineCommand> getGiftLineByPackingIdAndStaId(@QueryParam(value = "plId") Long plid,@QueryParam(value = "staId") Long staid, BeanPropertyRowMapper<GiftLineCommand> beanPropertyRowMapper);

}
