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

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.TransEmsInfoDao;
import com.jumbo.webservice.ems.EmsServiceClient2;
import com.jumbo.wms.model.baseinfo.TransEmsInfo;
import com.jumbo.wms.model.command.TransEmsInfoCommand;
import com.jumbo.wms.model.system.ChooseOption;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("transEmsInfoManager")
public class TransEmsInfoManagerImpl implements TransEmsInfoManager {

    private static final long serialVersionUID = 3812958049726435708L;
    @Autowired
    TransEmsInfoDao transEmsInfoDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;

    /** 
     *
     */
    @Override
    public TransEmsInfoCommand findByCmp(Boolean isCod) {
        TransEmsInfo trans = null;
        Boolean bEms = true;// 用老的
        ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(EmsServiceClient2.EMS_NEW_SWITCH_CODE, EmsServiceClient2.EMS_NEW_SWITCH_KEY);
        if (op != null && op.getOptionValue() != null) {
            bEms = false;// 用新的
        }
        if (bEms) {// 老的
            trans = transEmsInfoDao.findByCmp(isCod, 0);
        } else {// 新的
            trans = transEmsInfoDao.findByCmp(isCod, 1);
        }

        TransEmsInfoCommand transCmd = new TransEmsInfoCommand();
        BeanUtils.copyProperties(trans, transCmd);
        return transCmd;
    }


}
