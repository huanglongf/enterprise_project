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

import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.model.vmi.Default.VmiAdjDefault;
import com.jumbo.wms.model.vmi.Default.VmiAdjLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiRsnDefault;
import com.jumbo.wms.model.vmi.Default.VmiRsnLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtwDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtwLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiTfxDefault;
import com.jumbo.wms.model.vmi.Default.VmiTfxLineDefault;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * @author lichuan
 * 
 */
public interface VmiInterfaceExt extends VmiInterface {
    /**
     * vmi入库指令创单sta头信息处理面
     * 
     * @param sta
     * @param extParam void
     * @throws
     */
    void generateInboundStaSetHeadAspect(StockTransApplication head, ExtParam extParam);

    /**
     * 获取vmi入库指令明细行
     * 
     * @param extParam
     * @return ExtReturn
     * @throws
     */
    ExtReturn generateInboundStaGetDetialExt(ExtParam extParam);
    
    /**
     * 品牌获取渠道信息扩展
     * 
     * @param extParam
     * @return ExtReturn
     * @throws
     */
    ExtReturn findVmiDefaultTbiChannelExt(ExtParam extParam);

    /**
     * vmi入库指令创单sta处理面
     * 
     * @param sta
     * @param extParam void
     * @throws
     */
    void generateVmiInboundStaAspect(StockTransApplication sta, ExtParam extParam);

    /**
     * vmi入库指令创单staLine处理面
     * 
     * @param staLine
     * @param extParam void
     * @throws
     */
    void generateVmiInboundStaLineAspect(StaLine staLine, ExtParam extParam);

    /**
     * vmi入库反馈rsn处理面
     * 
     * @param vrd
     * @param extParam void
     * @throws
     */
    void generateReceivingWhenShelvRsnAspect(VmiRsnDefault vrd, ExtParam extParam);

    /**
     * vmi入库反馈rsnLine处理面
     * 
     * @param vrdLine
     * @param extParam void
     * @throws
     */
    void generateReceivingWhenShelvRsnLineAspect(VmiRsnLineDefault vrdLine, ExtParam extParam);

    /**
     * vmi退大仓rtw处理面
     * 
     * @param rtw
     * @param extParam void
     * @throws
     */
    void generateRtnWhRtwAspect(VmiRtwDefault rtw, ExtParam extParam);

    /**
     * vmi退大仓rtwLine处理面
     * 
     * @param rtwLine
     * @param extParam void
     * @throws
     */
    void generateRtnWhRtwLineAspect(VmiRtwLineDefault rtwLine, ExtParam extParam);

    /**
     * vmi退仓转店tfx处理面
     * 
     * @param tfx
     * @param extParam void
     * @throws
     */
    void generateRtnWhTfxAspect(VmiTfxDefault tfx, ExtParam extParam);

    /**
     * vmi退仓转店tfxLine处理面
     * 
     * @param tfxLine
     * @param extParam void
     * @throws
     */
    void generateRtnWhTfxLineAspect(VmiTfxLineDefault tfxLine, ExtParam extParam);

    /**
     * vmi库存调整adj处理面
     * 
     * @param adj
     * @param extParam void
     * @throws
     */
    void generateVMIReceiveInfoByInvCkAdjAspect(VmiAdjDefault adj, ExtParam extParam);

    /**
     * vmi库存调整adjLine处理面
     * 
     * @param adjLine
     * @param extParam void
     * @throws
     */
    void generateVMIReceiveInfoByInvCkAdjLineAspect(VmiAdjLineDefault adjLine, ExtParam extParam);

    /**
     * vmi退仓处理面
     * 
     * @param extParam void
     * @throws
     */
    void createStaForVMIReturnAspect(ExtParam extParam);

    /**
     * 转店tfx处理面
     * 
     * @param tfx
     * @param extParam void
     * @throws
     */
    void generateReceivingTransferTfxAspect(VmiTfxDefault tfx, ExtParam extParam);

    /**
     * 转店tfxLine处理面
     * 
     * @param tfxLine
     * @param extParam void
     * @throws
     */
    void generateReceivingTransferTfxLineAspect(VmiTfxLineDefault tfxLine, ExtParam extParam);
    
    /**
     * 转店rsn处理面
     * 
     * @param vrd
     * @param extParam void
     * @throws
     */
    void generateReceivingTransferRsnAspect(VmiRsnDefault vrd, ExtParam extParam);

    /**
     * 转店rsnLine处理面
     * 
     * @param vrdLine
     * @param extParam void
     * @throws
     */
    void generateReceivingTransferRsnLineAspect(VmiRsnLineDefault vrdLine, ExtParam extParam);

}
