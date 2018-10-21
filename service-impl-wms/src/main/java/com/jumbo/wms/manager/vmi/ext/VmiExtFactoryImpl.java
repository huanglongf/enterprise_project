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
package com.jumbo.wms.manager.vmi.ext;

import org.springframework.transaction.annotation.Transactional;


/**
 * @author lichuan
 * 
 */
public class VmiExtFactoryImpl implements VmiExtFactory {
    private VmiInterfaceExt defaultExt;
    private VmiInterfaceExt pumaExt;
    private VmiInterfaceExt speedoExt;


    /** 
     *
     */
    @Override
    @Transactional
    public VmiInterfaceExt getBrandVmi(String vmiCode) {
        for (String code : pumaExt.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return pumaExt;
            }
        }
        for (String code : speedoExt.getVmiCode()) {
            if (vmiCode.equals(code)) {
                return speedoExt;
            }
        }
        return null;
    }

    public VmiInterfaceExt getSpeedoExt() {
        return speedoExt;
    }

    public void setSpeedoExt(VmiInterfaceExt speedoExt) {
        this.speedoExt = speedoExt;
    }

    public VmiInterfaceExt getDefaultExt() {
        return defaultExt;
    }

    public void setDefaultExt(VmiInterfaceExt defaultExt) {
        this.defaultExt = defaultExt;
    }

    public VmiInterfaceExt getPumaExt() {
        return pumaExt;
    }

    public void setPumaExt(VmiInterfaceExt pumaExt) {
        this.pumaExt = pumaExt;
    }

}
