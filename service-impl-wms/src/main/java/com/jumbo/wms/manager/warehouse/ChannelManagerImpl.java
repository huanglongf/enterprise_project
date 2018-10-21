/**
 * 
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
package com.jumbo.wms.manager.warehouse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.warehouse.WarehouseChannelDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.WarehouseChannel;
import com.jumbo.wms.model.warehouse.WarehouseChannelType;

@Transactional
@Service("whChannelManager")
public class ChannelManagerImpl extends BaseManagerImpl implements ChannelManager {
    /**
	 * 
	 */
    private static final long serialVersionUID = 9031203153833883942L;
    @Autowired
    private WarehouseChannelDao warehouseChannelDao;

    /**
     * int 1 收获通道枚举类型 int 2 发获通道枚举类型
     * 
     * @param intType
     * @return
     */
    private WarehouseChannelType getChannelType(int intType) {
        return WarehouseChannelType.valueOf(intType);
    }

    /**
     * 根据通道类型查找仓库收发货通道列表
     * 
     * @param type
     * @return
     */
    @Transactional(readOnly = true)
    public List<WarehouseChannel> findWarehouseChannelList(int intChannelType, Long ouid) {
        if (intChannelType == 0) return warehouseChannelDao.findWarehouseChannelList(null, ouid);
        return warehouseChannelDao.findWarehouseChannelList(getChannelType(intChannelType), ouid);
    }

    /**
     * 新增保存收发货通道信息
     * 
     * @param ou
     * @param channelType
     * @param warehouseChannelList
     * @param addList
     * @return
     */
    @Transactional
    public List<WarehouseChannel> createUpdateChannelList(OperationUnit ou, int channelType, List<WarehouseChannel> warehouseChannelList, List<WarehouseChannel> addList) {
        List<WarehouseChannel> failures = new ArrayList<WarehouseChannel>();
        WarehouseChannelType wcType = null;
        if (addList != null && !addList.isEmpty()) {
            for (Iterator<WarehouseChannel> it = addList.iterator(); it.hasNext();) {
                WarehouseChannel w = it.next();
                if (w == null) {
                    it.remove();
                } else {
                    w.setOu(ou);
                }
            }
            setWarehouseChannelType(channelType, addList);
        }
        if (channelType == WarehouseChannelType.INBOUND.getValue()) {
            wcType = WarehouseChannelType.OUTBOUND;
        } else if (channelType == WarehouseChannelType.OUTBOUND.getValue()) {
            wcType = WarehouseChannelType.INBOUND;
        }
        if (warehouseChannelList != null && !warehouseChannelList.isEmpty()) {
            setWarehouseChannelType(channelType, warehouseChannelList);
        }
        List<WarehouseChannel> updateFailures = warehouseChannelList == null || warehouseChannelList.isEmpty() ? null : upadteWarehouseChannelList(wcType, ou, warehouseChannelList);
        List<WarehouseChannel> addFailures = addList == null || addList.isEmpty() ? null : createWarehouseChannelList(ou, addList);
        if (updateFailures != null && !updateFailures.isEmpty()) failures.addAll(updateFailures);
        if (addFailures != null && !addFailures.isEmpty()) failures.addAll(addFailures);
        return failures;
    }

    /**
     * 通道类型的设置
     * 
     * @param channelType
     * @param warehouseChannelList
     * @return
     */

    private List<WarehouseChannel> setWarehouseChannelType(int channelType, List<WarehouseChannel> warehouseChannelList) {
        for (WarehouseChannel wc : warehouseChannelList) {
            wc.setType(WarehouseChannelType.valueOf(channelType));
        }
        return warehouseChannelList;
    }

    /**
     * 保存收发货通道信息
     * 
     * @param addList
     * @return
     */
    @Transactional
    public List<WarehouseChannel> createWarehouseChannelList(OperationUnit ou, List<WarehouseChannel> addList) {
        if (addList == null || addList.isEmpty()) return addList;
        List<WarehouseChannel> addFailures = new ArrayList<WarehouseChannel>();
        for (int i = 0; i < addList.size(); i++) {
            WarehouseChannel each = addList.get(i);
            if (each.getCode().trim() == null) continue;
            WarehouseChannel wc = warehouseChannelDao.findWarehouseChannelByCodeAndOu(each.getCode(), ou.getId(), null);;
            if (wc != null) {
                addFailures.add(each);
                continue;
            } else {
                try {
                    warehouseChannelDao.save(each);
                } catch (Exception e) {
                    addFailures.add(each);
                }
            }
        }
        return addFailures;
    }

    /**
     * 更新收发货通道信息
     * 
     * @param updateList
     * @return
     */
    @Transactional
    public List<WarehouseChannel> upadteWarehouseChannelList(WarehouseChannelType wcType, OperationUnit ou, List<WarehouseChannel> updateList) {
        if (updateList == null || updateList.isEmpty()) return updateList;
        List<WarehouseChannel> updateFailures = new ArrayList<WarehouseChannel>();
        for (int i = 0; i < updateList.size(); i++) {
            WarehouseChannel each = updateList.get(i);
            if (each.getCode().trim() == null) continue;
            WarehouseChannel wc = warehouseChannelDao.findWarehouseChannelByCodeAndOu(each.getCode(), ou.getId(), wcType);
            if (wc != null) {
                updateFailures.add(each);
                continue;
            } else {
                try {
                    WarehouseChannel w = warehouseChannelDao.getByPrimaryKey(each.getId());
                    w.setCode(each.getCode().trim());
                    w.setName(each.getName().trim());
                    w.setDescription(each.getDescription().trim());
                    w.setIsAvailable(each.getIsAvailable());
                    warehouseChannelDao.save(w);
                } catch (Exception e) {
                    updateFailures.add(each);
                }
            }
        }
        return updateFailures;
    }
}
