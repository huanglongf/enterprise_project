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
 */
package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.automaticEquipment.ZoonDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.PickingReplenishCfgDao;
import com.jumbo.wms.model.automaticEquipment.Zoon;
import com.jumbo.wms.model.command.automaticEquipment.ZoonCommand;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.SkuReplenishmentCommand;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("pickingListManager")
public class PickingListManagerImpl implements PickingListManager {

    private static final long serialVersionUID = -2273942474459573930L;

    @Autowired
    PickingListDao pickingListDao;
    @Autowired
    PickingReplenishCfgDao pickingReplenishCfgDao;
    @Autowired
    private ZoonDao zoonDao;

    /**
     * 获取配货清单的核对模式
     */
    @Override
    public Long findPickingListByPickId(Long pickId) {
        return pickingListDao.findPickingListByPickId(pickId, new SingleColumnRowMapper<Long>(Long.class));
    }

    /**
     * 配货清单id，是否特定
     */
    @Override
    public Long findPickingListByPickIdS(Long pickId) {
        return pickingListDao.findPickingListByPickIdS(pickId, new SingleColumnRowMapper<Long>(Long.class));
    }

    /**
     * 获取打印次数
     */
    @Override
    public BigDecimal findOutputCount(Long plId) {
        return pickingListDao.findOutputCount(plId, new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
    }

    /**
     * 根据主键获取配货清单
     */
    @Override
    public PickingListCommand getByPrimaryKey(Long plId) {
        PickingList pl = pickingListDao.getByPrimaryKey(plId);
        PickingListCommand plCmd = new PickingListCommand();
        BeanUtils.copyProperties(pl, plCmd);
        plCmd.setHandOverList(null);
        plCmd.setCreator(null);
        plCmd.setStaList(null);
        return plCmd;
    }

    @Override
    public List<Zoon> findPickingDistrictByPickingId(Long pickingId, Long ouId) {
        return zoonDao.findPickingDistrictByPickingId(pickingId, ouId, new BeanPropertyRowMapper<ZoonCommand>(ZoonCommand.class));

    }

    @Override
    public List<Zoon> findPickingDistrictByPickingListId(List<Long> pickingListId, Long ouId) {

        return zoonDao.findPickingDistrictByPickingListId(pickingListId, ouId, new BeanPropertyRowMapper<ZoonCommand>(ZoonCommand.class));
    }

    @Override
    public List<PickingListCommand> findDetailInfoById(Long plId) {
        return pickingListDao.findDetailInfoById(plId, new BeanPropertyRowMapperExt<PickingListCommand>(PickingListCommand.class));
    }

    @Override
    public List<SkuReplenishmentCommand> findReplenishSummary(Long ouId, Map<String, Object> skuInfoMap) {
        return pickingReplenishCfgDao.findReplenishSummary(ouId, skuInfoMap, new BeanPropertyRowMapperExt<SkuReplenishmentCommand>(SkuReplenishmentCommand.class));
    }

    @Override
    public List<SkuReplenishmentCommand> findReplenishSummaryForPickingFailed(Long ouid, Map<String, Object> skuInfoMap) {
        return pickingReplenishCfgDao.findReplenishSummaryForPickingFailed(ouid, skuInfoMap, new BeanPropertyRowMapperExt<SkuReplenishmentCommand>(SkuReplenishmentCommand.class));
    }

    @Override
    public List<SkuReplenishmentCommand> findReplenishInv(Long ouId, Long skuId, Map<String, Object> skuInfoMap) {
        return pickingReplenishCfgDao.findReplenishInv(ouId, skuId, skuInfoMap, new BeanPropertyRowMapperExt<SkuReplenishmentCommand>(SkuReplenishmentCommand.class));
    }

    @Override
    public List<SkuReplenishmentCommand> findReplenishInvDetaile(Long ouId, Long skuId, Map<String, Object> skuInfoMap) {
        return pickingReplenishCfgDao.findReplenishInvDetaile(ouId, skuId, skuInfoMap, new BeanPropertyRowMapperExt<SkuReplenishmentCommand>(SkuReplenishmentCommand.class));
    }

    @Override
    public List<PickingListCommand> findPickingListByPickingId(Long pickingListId, Integer pickZoneId, Long ouid) {
        return pickingListDao.findPickingListByPickingId(pickingListId, pickZoneId, ouid, new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
    }

    /**
     * 
     * @author LuYingMing
     * @date 2016年9月6日 下午9:23:18
     * @see com.jumbo.wms.manager.warehouse.PickingListManager#haha(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<Long> findZoonIds(Long pickingId, Long ouId) {
        return zoonDao.findZoonIds(pickingId, ouId, new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public PickingListCommand findPickListByPickCode(String pickingCode, Long ouId) {
        PickingListCommand p = pickingListDao.findSinglePickListByCode(pickingCode, ouId, new BeanPropertyRowMapperExt<PickingListCommand>(PickingListCommand.class));
        return p;
    }


}
