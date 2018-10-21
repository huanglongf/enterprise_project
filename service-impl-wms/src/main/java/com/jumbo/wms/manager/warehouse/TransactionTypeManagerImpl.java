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
package com.jumbo.wms.manager.warehouse;


import java.util.ArrayList;
import java.util.List;

import loxia.dao.Sort;
import loxia.utils.PropListCopyable;
import loxia.utils.PropertyUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.TransactionTypeRowMapper;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.command.TransactionTypeCommand;
import com.jumbo.wms.model.warehouse.TransactionType;


@Transactional
@Service("transactionTypeManager")
public class TransactionTypeManagerImpl extends BaseManagerImpl implements TransactionTypeManager {
    private static final long serialVersionUID = -351907111542990435L;
    @Autowired
    private TransactionTypeDao transactionTypeDao;

    public List<TransactionType> findTranstypelist(TransactionType transactionType, Long ouTypeId, Sort[] sorts) {
        if (transactionType.getIsSystem()) {
            ouTypeId = null;
        }
        List<TransactionType> list = transactionTypeDao.findList(true, transactionType.getIsSystem(), ouTypeId, sorts);
        return list;
    }

    /**
     * 当前库位绑定的作业类型,未指定库位=所有作业类型
     * 
     * @param ou 仓库,根据仓库找当前仓库所在的运营中心
     * @param locationId 库位
     * @param sorts 排序
     * @return
     */
    public List<TransactionType> findTransactionTypeList(OperationUnit ou, Long locationId, Sort[] sorts) {
        return transactionTypeDao.findTransactionTypeList(ou.getId(), locationId, sorts, new TransactionTypeRowMapper());
    }

    public TransactionType createorModifyTransactionType(TransactionType transactionType) {
        // 需要重构,逻辑是1,修改;2,创建,都需要校验作业类型编码的唯一性
        // 校验的规则
        TransactionType transType = transactionTypeDao.findByCode(transactionType.getCode());
        TransactionType t = null;
        if (transactionType.getId() == null) {
            if (transType != null) {
                return null;
            }
            t = transactionTypeDao.save(transactionType);
        } else {
            TransactionType t1 = transactionTypeDao.getByPrimaryKey(transactionType.getId());

            if (this.checkTransactionType(t1, transType)) {
                return null;
            }
            try {
                PropertyUtil.copyProperties(transactionType, t1, new PropListCopyable(new String[] {"code", "name", "description", "isInCost", "direction", "control", "isAvailable", "isSynchTaobao"}));
                t = transactionTypeDao.save(t1);
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return t;
    }

    /**
     * 检查修改的时候，作业编码是否重复
     * 
     * @param t1
     * @param list
     * @return
     */
    private Boolean checkTransactionType(TransactionType t1, TransactionType source) {
        if (source != null) {
            TransactionType t2 = source;
            if (t1.getId().equals(t2.getId())) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public List<TransactionType> findListByOu(Long ouId, Boolean isSystem, Boolean notIgnoreIsSystem) {
        return transactionTypeDao.findList(notIgnoreIsSystem, notIgnoreIsSystem, ouId, null);
    }

    public List<TransactionType> findListByOu(Long ouId, Boolean isSystem) {// ok
        List<TransactionType> list = transactionTypeDao.findListByOuId(isSystem, ouId, null);
        List<TransactionType> result = new ArrayList<TransactionType>();
        for (TransactionType each : list) {
            TransactionTypeCommand coc = new TransactionTypeCommand();
            try {
                org.springframework.beans.BeanUtils.copyProperties(each, coc);
                result.add(coc);
            } catch (Exception e) {
                log.error("Copy Bean properties error for TransactionType");
                log.error("", e);
                throw new RuntimeException("Copy Bean properties error for TransactionType");
            }
        }
        return result;

    }

    /**
     * 查询本运营中心下的所有作业类型及系统预定义的作业类型
     * 
     * @param id 运营中心id
     * @return
     */
    public List<TransactionType> findTransactionListByOu(Long ouid) {
        return transactionTypeDao.findTransactionListByOu(ouid);
    }

    public TransactionType findTransactionById(Long id) {
        TransactionType transactionType = transactionTypeDao.getByPrimaryKey(id);
        if (transactionType == null) {
            throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND);
        }
        return transactionType;
    }
}
