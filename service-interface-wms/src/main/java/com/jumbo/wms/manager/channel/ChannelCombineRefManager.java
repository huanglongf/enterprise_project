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
package com.jumbo.wms.manager.channel;




import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.BiChannelCombineRefCommand;


public interface ChannelCombineRefManager extends BaseManager {
    /**
     * 查询合并发货渠道信息
     * @param start
     * @param pagesize
     * @param sorts
     * @return
     */
    Pagination<BiChannelCombineRefCommand> findBiChannelCombineRef(int start, int pagesize,Sort[] sorts,Long ouId);
    
    /**
     * 查询合并发货子渠道信息
     * @param start
     * @param pagesize
     * @param sorts
     * @return
     */
    List <BiChannelCombineRefCommand> findBiChannelCombineChildrenRef(Long id,Long whId,Sort[] sorts,Long ouId);
    
    /**
     * 新增单渠道
     * @param whId
     * @param expDate
     * @param chId
     */
    void addSingelChannels(Long whId,Date expDate,String chId);

    /**
     * 新增多渠道
     * @param defId
     * @param whId
     * @param expDate
     * @param code
     * @param name
     * @param chId
     */
    void addMoreChannels(Long defId,Long whId,Date expDate,String code,String name,String zxShopName,String ydShopName,String chId);
    
    /**
     * 修改单渠道
     * @param whId
     * @param expDate
     * @param chId
     */
    void updateSingelChannels(Long id,Long chanId,Long whId,Date expDate,Long isCombine);
    
    /**
     * 修改多渠道
     * @param whId
     * @param expDate
     * @param chId
     */
    void updateMoreChannels(Long id,Long chanId,Long whId,String childChannelIdList,Date expDate,Long isCombine);
    
}
