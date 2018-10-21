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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.model.vmi.Default.RsnType;
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
 * @author lzb
 * 
 */
public class VmiSpeedoExt extends VmiDefaultExt {

    /**
     * 
     */
    private static final long serialVersionUID = 570475499808281576L;
    @Autowired
    private StaLineDao staLineDao;

    /** 
     *
     */
    @Override
    public void generateInboundStaSetHeadAspect(StockTransApplication head, ExtParam extParam) {}

    /**
     * 品牌获取渠道信息扩展
     */
    @Override
    public ExtReturn findVmiDefaultTbiChannelExt(ExtParam extParam) {
        return null;
    }


    /**
     * 将收货指令中ext_memo设置到sta
     */
    @Override
    public void generateVmiInboundStaAspect(StockTransApplication sta, ExtParam extParam) {}

    /**
     * 将收货指令明细中extMemo设置到staLine
     */
    @Override
    public void generateVmiInboundStaLineAspect(StaLine staLine, ExtParam extParam) {

        /*
         * if (null == extParam) { return; } List<VmiAsnLineCommand> valList =
         * extParam.getValList(); String extCode2 = (null != staLine ? (null != staLine.getSku() ?
         * (staLine.getSku().getExtensionCode2()) : "") : ""); if (StringUtil.isEmpty(extCode2)) {
         * return; } if (null != valList) { for (VmiAsnLineCommand val : valList) { String sku =
         * val.getUpc(); String extMemo = val.getExtMemo(); if (extCode2.equals(sku)) {
         * staLine.setExtMemo(extMemo); break; } } }
         */
    }

    /**
     * 上架时将sta上的extMemo设置到rsn
     */
    @Override
    public void generateReceivingWhenShelvRsnAspect(VmiRsnDefault vrd, ExtParam extParam) {}

    /**
     * 上架时将staLine上的extMemo设置到rsnLine
     */
    @Override
    public void generateReceivingWhenShelvRsnLineAspect(VmiRsnLineDefault vrdLine, ExtParam extParam) {
        if (null == extParam) {
            return;
        }
        // 判断sta_line 是否含有相同的skuid
        List<StaLine> sLine = staLineDao.findByOwnerAndStatus(extParam.getSta().getId(), extParam.getSkuCmd().getId(), null, null);
        if (sLine.size() > 1) {
            log.error("sta_line含有相同的skuid" + extParam.getSta().getId() + "," + extParam.getSkuCmd().getId());
            throw new BusinessException("");
        }
        String rsnType = extParam.getRsnType();
        if (rsnType.equals(String.valueOf(RsnType.TYPETWO.getValue()))) {
            for (StaLine staLine : sLine) {
                vrdLine.setExtMemo(staLine.getExtMemo());
            }
        }
    }

    /**
     * 退大仓出库时将sta上的extMemo设置到rtw
     */
    @Override
    public void generateRtnWhRtwAspect(VmiRtwDefault rtw, ExtParam extParam) {}

    /**
     * 退大仓出库时将staLine上的extMemo设置到rtwLine
     */
    @Override
    public void generateRtnWhRtwLineAspect(VmiRtwLineDefault rtwLine, ExtParam extParam) {}

    /**
     * 转店退仓tfx处理面
     */
    @Override
    public void generateRtnWhTfxAspect(VmiTfxDefault tfx, ExtParam extParam) {}

    /**
     * 转店退仓tfxLine处理面
     */
    @Override
    public void generateRtnWhTfxLineAspect(VmiTfxLineDefault tfxLine, ExtParam extParam) {

    }

    /** 
    *
    */
    @Override
    public void generateVMIReceiveInfoByInvCkAdjAspect(VmiAdjDefault adj, ExtParam extParam) {}

    /** 
    *
    */
    @Override
    public void generateVMIReceiveInfoByInvCkAdjLineAspect(VmiAdjLineDefault adjLine, ExtParam extParam) {

    }

    /**
     * vmi退仓处理面
     */
    @Override
    public void createStaForVMIReturnAspect(ExtParam extParam) {}

    /**
     * 转店tfx处理面
     */
    @Override
    public void generateReceivingTransferTfxAspect(VmiTfxDefault tfx, ExtParam extParam) {}

    /**
     * 转店tfxLine处理面
     */
    @Override
    public void generateReceivingTransferTfxLineAspect(VmiTfxLineDefault tfxLine, ExtParam extParam) {

    }

    /**
     * 转店rsn处理面
     */
    @Override
    public void generateReceivingTransferRsnAspect(VmiRsnDefault vrd, ExtParam extParam) {}

    /**
     * 转店rsnLine处理面
     */
    @Override
    public void generateReceivingTransferRsnLineAspect(VmiRsnLineDefault vrdLine, ExtParam extParam) {

    }

}
