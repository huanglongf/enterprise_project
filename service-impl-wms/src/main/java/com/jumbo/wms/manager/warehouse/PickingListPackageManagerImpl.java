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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.warehouse.PickingListPackageDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.warehouse.PickingListPackage;
import com.jumbo.wms.model.warehouse.PickingListPackageCommand;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("pickingListPackageManager")
public class PickingListPackageManagerImpl extends BaseManagerImpl implements PickingListPackageManager {

    private static final long serialVersionUID = -8698978930623668255L;
    @Autowired
    private PickingListPackageDao pickingListPackageDao;

    /**
     * 根据plId与status查询配货批包裹
     */
    @Override
    public List<PickingListPackageCommand> findByPlIdAndStatus(Long plId, DefaultStatus status) {
        List<PickingListPackage> list = pickingListPackageDao.findByPlIdAndStatus(plId, status);
        List<PickingListPackageCommand> result = new ArrayList<PickingListPackageCommand>();
        for (PickingListPackage plp : list) {
            PickingListPackageCommand plpc = new PickingListPackageCommand();
            org.springframework.beans.BeanUtils.copyProperties(plp, plpc);
            plpc.setSkuId(null);
            plpc.setPickingListId(null);
            result.add(plpc);
        }
        return result;
    }

}
