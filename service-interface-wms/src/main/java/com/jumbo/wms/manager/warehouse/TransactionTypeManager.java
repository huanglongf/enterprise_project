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

import java.util.List;

import loxia.dao.Sort;

import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.TransactionType;

/**
 * 作业类型
 * 
 * @author wanghua
 * 
 * 
 */
public interface TransactionTypeManager {
    /**
     * 根据TransactionType的IsSystem（目前就是这个条件，需要其他条件，重新写实现方法）， 和组织类型ID查询TransactionType的List
     * 
     * @param transactionType
     * @param ouTypeId
     * @return
     */
    List<TransactionType> findTranstypelist(TransactionType transactionType, Long ouTypeId, Sort[] sorts);

    /**
     * 当前库位绑定的作业类型,未指定库位=所有作业类型
     * 
     * @param ou 仓库,根据仓库找当前仓库所在的运营中心
     * @param locationId 库位
     * @param sorts 排序
     * @return
     */
    List<TransactionType> findTransactionTypeList(OperationUnit ou, Long locationId, Sort[] sorts);

    /**
     * 
     * @param transactionType
     * @return
     */
    TransactionType createorModifyTransactionType(TransactionType transactionType);

    /**
     * 查询作业类型
     * 
     * @param ouId
     * @param isSystem
     * @param notIgnoreIsSystem
     * @return
     */
    List<TransactionType> findListByOu(Long ouId, Boolean isSystem, Boolean notIgnoreIsSystem);

    /**
     * 查询作业类型
     * 
     * @param ouId
     * @param isSystem
     * @return
     */
    List<TransactionType> findListByOu(Long ouId, Boolean isSystem);

    /**
     * 查询本运营中心下的所有作业类型及系统预定义的作业类型
     * 
     * @param id 运营中心id
     * @return
     */
    List<TransactionType> findTransactionListByOu(Long ouid);


    /**
     * 根据ID获取作业类型对象
     * 
     * @param id
     * @return
     */
    TransactionType findTransactionById(Long id);
}
