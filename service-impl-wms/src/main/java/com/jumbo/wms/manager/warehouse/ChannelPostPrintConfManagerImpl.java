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

import java.util.Date;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.BiChannelSpecialActionDao;
import com.jumbo.dao.baseinfo.ChannelWhRefLogDao;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.ChannelPostPrintConfDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialActionType;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.baseinfo.ChannelWhRefLog;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.BiChannelSpecialActionCommand;
import com.jumbo.wms.model.warehouse.ChannelWhRefCommand;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("channelPostPrintConfManager")
public class ChannelPostPrintConfManagerImpl implements ChannelPostPrintConfManager {
    protected static final Logger log = LoggerFactory.getLogger(ChannelPostPrintConfManagerImpl.class);
    private static final long serialVersionUID = 1L;
    @Autowired
    private ChannelPostPrintConfDao channelPostPrintConfDao;
    @Autowired
    private ChannelWhRefRefDao channelWhRefRefDao;
    @Autowired
    private BiChannelSpecialActionDao biChannelSpecialActionDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private ChannelWhRefLogDao channelWhRefLogDao;
    @Autowired
    private OperationUnitDao operationUnitDao;

    /**
     * 查询仓库关联的所有渠道列表(包括跨渠道合并的虚拟主渠道)
     */
    @Override
    public Pagination<BiChannelCommand> findAllChannelRefByOuId(int start, int pageSize, Long ouId, Sort[] sorts) {

        return channelPostPrintConfDao.findAllChannelRefByOuId(start, pageSize, ouId, sorts, new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
    }

    /**
     * 更新后置打印配置
     */
    @Override
    public void updatePostPrintConf(List<ChannelWhRef> channelWhRefList, Long ouId, Long userId, List<ChannelWhRefCommand> channelList) {
        channelWhRefRefDao.updatePostPrintConf(null, ouId, 0, 0);
        int num = 0;
        StringBuilder sb = new StringBuilder();
        if (null != channelWhRefList) {
            for (ChannelWhRef ref : channelWhRefList) {
                int isPostPackingPage = 0;
                int isPostExpressBill = 0;
                if (true == ref.getIsPostPackingPage()) {
                    isPostPackingPage = 1;
                }
                if (true == ref.getIsPostExpressBill()) {
                    isPostExpressBill = 1;
                }
                if (null == ref.getShop() || null == ref.getShop().getId() || null == ouId) {
                    throw new BusinessException(ErrorCode.PDA_SYS_ERROR);
                }
                Long channelId = ref.getShop().getId();
                BiChannelSpecialActionCommand action =
                        biChannelSpecialActionDao.getBiChannelSpecialActionByCidType(channelId, BiChannelSpecialActionType.PRINT_PACKING_PAGE.getValue(), new BeanPropertyRowMapperExt<BiChannelSpecialActionCommand>(BiChannelSpecialActionCommand.class));
                BiChannel c = biChannelDao.getByPrimaryKey(channelId);
                if (1 == isPostPackingPage && null != action && (null != action.getTemplateType() && !"".equals(action.getTemplateType()))) {
                    isPostPackingPage = 0;// 不允许维护后置打印装箱清单
                    if (0 == num) {
                        sb.append(c.getCode());
                    } else {
                        sb.append(",").append(c.getCode());
                    }
                    num++;
                }
                try {
                    ChannelWhRefLog channelWhRefLog = new ChannelWhRefLog();
                    channelWhRefLog.setShop(c);
                    channelWhRefLog.setWh(operationUnitDao.getByPrimaryKey(ouId));
                    if (1 == isPostPackingPage) {
                        channelWhRefLog.setIsPostPackingPage(true);
                    } else {
                        channelWhRefLog.setIsPostPackingPage(false);
                    }
                    if (1 == isPostExpressBill) {
                        channelWhRefLog.setIsPostExpressBill(true);
                    } else {
                        channelWhRefLog.setIsPostExpressBill(false);
                    }

                    channelWhRefLog.setOperateTime(new Date());
                    channelWhRefLog.setOperateUserId(userId);
                    channelWhRefLogDao.save(channelWhRefLog);
                } catch (Exception ex) {
                    if (log.isErrorEnabled()) {
                        log.error("updatePostPrintConf Exception:", ex);
                    }
                }
                channelWhRefRefDao.updatePostPrintConf(channelId, ouId, isPostPackingPage, isPostExpressBill);
            }
        }
        if (null != channelList && channelList.size() > 0) {
            for (ChannelWhRefCommand list : channelList) {
                try {
                    ChannelWhRefLog channelWhRefLog = new ChannelWhRefLog();
                    BiChannel c = biChannelDao.getByPrimaryKey(list.getChannelId());
                    channelWhRefLog.setShop(c);
                    channelWhRefLog.setWh(operationUnitDao.getByPrimaryKey(ouId));
                    channelWhRefLog.setIsPostPackingPage(false);
                    channelWhRefLog.setIsPostExpressBill(false);
                    channelWhRefLog.setOperateTime(new Date());
                    channelWhRefLog.setOperateUserId(userId);
                    channelWhRefLogDao.save(channelWhRefLog);
                } catch (Exception ex) {
                    if (log.isErrorEnabled()) {
                        log.error("updatePostPrintConf Exception:", ex);
                    }
                }
            }
        }
        if (null != sb && !"".equals(sb.toString())) {
            throw new BusinessException(ErrorCode.CHANNEL_FORBID_POST_PACKING_PAGE, new Object[] {sb.toString()});
        }
    }

    /**
     * 根据仓库id查询所有后置打印配置
     */
    @Override
    public List<ChannelWhRefCommand> findAllPostPrintConfByOuId(Long ouId) {
        return channelWhRefRefDao.findAllPostPrintConfByOuId(ouId, new BeanPropertyRowMapperExt<ChannelWhRefCommand>(ChannelWhRefCommand.class));
    }

}
