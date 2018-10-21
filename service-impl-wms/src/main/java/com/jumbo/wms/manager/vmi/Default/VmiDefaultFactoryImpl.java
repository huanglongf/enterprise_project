/**
 * EventObserver * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
package com.jumbo.wms.manager.vmi.Default;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jumbo.util.StringUtil;
import com.jumbo.wms.daemon.VmiDefaultFactory;
import com.jumbo.wms.daemon.VmiDefaultInterface;

/**
 * 通用品牌定制逻辑
 * 
 * @author bin.hu
 * 
 */
@Service("vmiDefaultFactory")
public class VmiDefaultFactoryImpl implements VmiDefaultFactory {

    @Resource(name = "vmiDefaultJohnson")
    private VmiDefaultInterface johnson;

    @Resource(name = "vmiDefaultStarbucks")
    private VmiDefaultInterface starbucks;

    @Resource(name = "vmiDefaultMarkwins")
    private VmiDefaultInterface markwins;

    @Resource(name = "vmiDefaultSpeedo")
    private VmiDefaultInterface speedo;

    @Resource(name = "vmiDefaultMK")
    private VmiDefaultInterface mk;

    @Resource(name = "vmiDefaultPaulFrank")
    private VmiDefaultInterface paulFrank;

    @Resource(name = "vmiDefaultCK")
    private VmiDefaultInterface ck;

    @Resource(name = "vmiDefaultGucci")
    private VmiDefaultInterface gucci;

    @Resource(name = "vmiDefaultRalph")
    private VmiDefaultInterface ralph;

    @Override
    public VmiDefaultInterface getVmiDefaultInterface(String code) {
        if (!StringUtil.isEmpty(code)) {
            if (DEFAULT_JOHNSON.equals(code)) {
                return johnson;
            }
            if (DEFAULT_STARBUCK.equals(code)) {
                return starbucks;
            }
            if (DEFAULT_MARKWINS.equals(code)) {
                return markwins;
            }
            if (DEFAULT_SPEEDO.equals(code)) {
                return speedo;
            }
            if (DEFAULT_PAULFRANK.equals(code)) {
                return paulFrank;
            }
            if (DEFAULT_MK.equals(code)) {
                return mk;
            }
            if (DEFAULT_CK.equals(code)) {
                return ck;
            }
            if (DEFAULT_GUCCI.equals(code)) {
                return gucci;
            }
            if (DEFAULT_RALPH.equals(code)) {
                return ralph;
            }
        }
        return null;
    }
}
