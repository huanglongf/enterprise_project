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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.ChannelGroupDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.BiChannelGroup;
import com.jumbo.wms.model.warehouse.BiChannelGroupCommand;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("channelGroupManager")
public class ChannelGroupManagerImpl implements ChannelGroupManager {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ChannelGroupDao channelGroupDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private BiChannelDao biChannelDao;

    @Override
    public Pagination<BiChannelGroupCommand> findAllChannelGroupByOuId(int start, int pageSize, Long ouId, Sort[] sorts) {
        return channelGroupDao.findAllChannelGroupByOuId(start, pageSize, ouId, sorts, new BeanPropertyRowMapper<BiChannelGroupCommand>(BiChannelGroupCommand.class));
    }

    @Override
    public List<BiChannelGroupCommand> findChannelGroupByOuId(Long ouId, Sort[] sorts) {
        return channelGroupDao.findChannelGroupByOuId(ouId, sorts, new BeanPropertyRowMapper<BiChannelGroupCommand>(BiChannelGroupCommand.class));
    }

    @Override
    public boolean findChannelGroupExistByCode(String code) {
        boolean ret = false;
        String groupCode = code;
        if (null != groupCode || !"".equals(groupCode)) groupCode = groupCode.toUpperCase();
        long i = channelGroupDao.findChannelGroupCountByCode(groupCode, new SingleColumnRowMapper<Long>(Long.class));
        if (i > 0) ret = true;
        return ret;
    }

    @Override
    public boolean findChannelGroupExistByName(String code) {
        boolean ret = false;
        String groupName = code;
        long i = channelGroupDao.findChannelGroupCountByName(groupName, new SingleColumnRowMapper<Long>(Long.class));
        if (i > 0) ret = true;
        return ret;
    }

    @Override
    public void saveChannelGroup(BiChannelGroup group, Integer status, OperationUnit ou) {
        long id = group.getId();
        if (id > 0) {
            BiChannelGroup cg = channelGroupDao.getByPrimaryKey(id);
            if (null != cg) {
                cg.setName(group.getName());
                cg.setSort(group.getSort());
                channelGroupDao.save(cg);
                channelGroupDao.flush();
            } else {
                BiChannelGroup cGroup = new BiChannelGroup();
                String code = group.getCode();
                code = code.toUpperCase();
                cGroup.setCode(code);
                cGroup.setName(group.getName());
                cGroup.setSort(group.getSort());
                cGroup.setCreateTime(new Date());
                if (ou == null) {
                    throw new RuntimeException("Current OperationUnit is null...");
                }
                OperationUnit o = operationUnitDao.getByPrimaryKey(ou.getId());
                cGroup.setOu(o);
                channelGroupDao.save(cGroup);
                channelGroupDao.flush();
            }
        } else {
            BiChannelGroup cGroup = new BiChannelGroup();
            String code = group.getCode();
            code = code.toUpperCase();
            cGroup.setCode(code);
            cGroup.setName(group.getName());
            cGroup.setSort(group.getSort());
            cGroup.setCreateTime(new Date());
            if (ou == null) {
                throw new RuntimeException("Current OperationUnit is null...");
            }
            OperationUnit o = operationUnitDao.getByPrimaryKey(ou.getId());
            cGroup.setOu(o);
            channelGroupDao.save(cGroup);
            channelGroupDao.flush();
        }

    }

    @Override
    public void deleteChannelGroup(BiChannelGroup group) {
        Long groupId = -1L;
        if (null != group) groupId = group.getId();
        channelGroupDao.deleteChannelRefByGroupId(groupId);
        channelGroupDao.deleteByPrimaryKey(groupId);
    }

    @Override
    public BiChannelGroup findChannelGroupByCode(String code) {
        if (null != code && !"".equals(code)) code = code.toUpperCase();
        return channelGroupDao.findChannelGroupByCode(code);
    }

    @Override
    public void saveChannelRef(BiChannelGroup group, Integer status, OperationUnit ou, List<BiChannel> channelRef) {
        saveChannelGroup(group, status, ou);
        channelGroupDao.deleteChannelRefByGroupId(group.getId());
        if (null != channelRef) {
            for (BiChannel c : channelRef) {
                Long channelId = c.getId();
                BiChannel bc = biChannelDao.getByPrimaryKey(channelId);
                if (null == bc) {
                    throw new BusinessException();
                }
                Long groupId = group.getId();
                channelGroupDao.insertChannelRef(groupId, channelId);
            }
        }

    }

    @Override
    public List<BiChannelCommand> findAllChannelRefByGroupId(Long groupId) {
        if (null == groupId) groupId = -1L;
        return channelGroupDao.findAllChannelRefByGroupId(groupId, new BeanPropertyRowMapperExt<BiChannelCommand>(BiChannelCommand.class));
    }

    @Override
    public List<BiChannelCommand> findAllChannelRefByGroupCode(String code) {
        return channelGroupDao.findAllChannelRefByGroupCode(code, new BeanPropertyRowMapperExt<BiChannelCommand>(BiChannelCommand.class));
    }

    @Override
    public List<BiChannelGroupCommand> findAllChannelGroupByCIdAndOuId(Long channelId, OperationUnit ou) {
        if (ou == null) {
            throw new RuntimeException("Current OperationUnit is null...");
        }
        Long ouId = ou.getId();
        if (null == channelId) channelId = -1L;
        return channelGroupDao.findAllChannelGroupByCIdAndOuId(channelId, ouId, new BeanPropertyRowMapperExt<BiChannelGroupCommand>(BiChannelGroupCommand.class));
    }

    @Override
    public List<BiChannelGroupCommand> findAllChannelGroupByCIdAndOuId(Long channelId, Long groupId, OperationUnit ou) {
        if (ou == null) {
            throw new RuntimeException("Current OperationUnit is null...");
        }
        Long ouId = ou.getId();
        if (null == channelId) channelId = -1L;
        return channelGroupDao.findAllChannelGroupByCIdAndOuId(channelId, groupId, ouId, new BeanPropertyRowMapperExt<BiChannelGroupCommand>(BiChannelGroupCommand.class));
    }
}
