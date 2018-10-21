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
package com.jumbo.wms.manager.task.vmi.ext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.PumaTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.vmi.Default.VmiDefaultManager;
import com.jumbo.wms.model.vmi.Default.VmiAdjCommand;
import com.jumbo.wms.model.vmi.Default.VmiAdjDefault;
import com.jumbo.wms.model.vmi.Default.VmiRsnCommand;
import com.jumbo.wms.model.vmi.Default.VmiRsnDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtwCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtwDefault;

/**
 * @author lichuan
 * 
 */
public class PumaTaskImpl extends BaseManagerImpl implements PumaTask {

    private static final long serialVersionUID = -1473852724843705624L;

    @Autowired
    private VmiDefaultManager vmiDefaultManager;

    /**
     * puma同步反馈给HUB
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void uploadVmiToHubExt() {
        List<VmiRsnCommand> vrList = vmiDefaultManager.findVmiRsnAllExt(Constants.PUMA_VMI_CODE);
        // 同步收货反馈给HUB
        try {
            vmiDefaultManager.uploadVmiRsnToHubPumaExt(vrList);
        } catch (Exception e) {
            log.error("", e);
            List<Long> ids = new ArrayList<Long>();
            for (VmiRsnCommand vr : vrList) {
                if (!ids.contains(vr.getId())) {
                    ids.add(vr.getId());
                }
            }
            vmiDefaultManager.updateVmiErrorCountExt(VmiRsnDefault.vmirsn, ids);
        }

        List<VmiRtwCommand> vwList = vmiDefaultManager.findPumaVmiRtwAllExt(Constants.PUMA_VMI_CODE);
        // 同步退大仓反馈给HUB
        try {
            vmiDefaultManager.uploadVmiRtwToHubPumaExt(vwList);
        } catch (Exception e) {
            log.error("", e);
            List<Long> ids = new ArrayList<Long>();
            for (VmiRtwCommand vw : vwList) {
                if (!ids.contains(vw.getId())) {
                    ids.add(vw.getId());
                }
            }
            vmiDefaultManager.updateVmiErrorCountExt(VmiRtwDefault.vmirtw, ids);
        }

        List<VmiAdjCommand> vaList = vmiDefaultManager.findVmiAdjAllExt(Constants.PUMA_VMI_CODE);
        // 同步库存调整反馈给HUB
        try {
            vmiDefaultManager.uploadVmiAdjToHubPumaExt(vaList);
        } catch (Exception e) {
            log.error("", e);
            List<Long> ids = new ArrayList<Long>();
            for (VmiAdjCommand va : vaList) {
                if (!ids.contains(va.getId())) {
                    ids.add(va.getId());
                }
            }
            vmiDefaultManager.updateVmiErrorCountExt(VmiAdjDefault.vmiadj, ids);
        }

    }

    /**
     * puma同步全量库存快照给HUB
     */
    @Override
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void totalInvToHubExt() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String batchNo = sdf.format(date);
        vmiDefaultManager.insertTotalInvPumaExt(date, batchNo, Constants.PUMA_VMI_CODE);
        vmiDefaultManager.uploadTotalInvToHubPumaExt(date, batchNo, Constants.PUMA_VMI_CODE);
    }

}
