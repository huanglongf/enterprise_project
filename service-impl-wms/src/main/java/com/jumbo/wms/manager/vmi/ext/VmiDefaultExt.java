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
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.jumbo.dao.vmi.defaultData.VmiAsnLineDao;
import com.jumbo.wms.manager.vmi.VmiDefault;
import com.jumbo.wms.model.vmi.Default.AsnOrderType;
import com.jumbo.wms.model.vmi.Default.VmiAdjDefault;
import com.jumbo.wms.model.vmi.Default.VmiAdjLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineCommand;
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
public class VmiDefaultExt extends VmiDefault implements VmiInterfaceExt {

    private static final long serialVersionUID = 997435877287205578L;
    @Autowired
    private VmiAsnLineDao vmiAsnLineDao;

    /** 
    *
    */
    @Override
    public void generateInboundStaSetHeadAspect(StockTransApplication head, ExtParam extParam) {

    }

    /**
     * 获取收货指令明细
     */
    @Override
    public ExtReturn generateInboundStaGetDetialExt(ExtParam extParam) {
        if (null == extParam) {
            return null;
        }
        ExtReturn extReturn = new ExtReturn();
        String orderCode = extParam.getOrderCode();
        String slipCode = extParam.getSlipCode();
        String asnType = extParam.getAsnOrderType();
        // 分解slipCode 分辨按单按箱
        List<VmiAsnLineCommand> vList = null;
        /**
         * 如果是按单code[0]和code[1]=收货单号 如果是按箱code[0]=箱号 code[1]=收货单号
         */
        if (asnType.equals(String.valueOf(AsnOrderType.TYPEONE.getValue()))) {
            // 按单创建
            vList = vmiAsnLineDao.findVmiAsnLineList(orderCode, null, new BeanPropertyRowMapper<VmiAsnLineCommand>(VmiAsnLineCommand.class));
        }
        if (asnType.equals(String.valueOf(AsnOrderType.TYPETWO.getValue()))) {
            // 按箱创建
            vList = vmiAsnLineDao.findVmiAsnLineList(orderCode, slipCode, new BeanPropertyRowMapper<VmiAsnLineCommand>(VmiAsnLineCommand.class));
        }
        extReturn.setValList(vList);
        return extReturn;
    }

    /**
     * 品牌获取渠道信息扩展
     */
    @Override
    public ExtReturn findVmiDefaultTbiChannelExt(ExtParam extParam) {
        return null;
    }

    /** 
     *
     */
    @Override
    public void generateVmiInboundStaAspect(StockTransApplication sta, ExtParam extParam) {

    }

    /** 
     *
     */
    @Override
    public void generateVmiInboundStaLineAspect(StaLine staLine, ExtParam extParam) {

    }

    /** 
     *
     */
    @Override
    public void generateReceivingWhenShelvRsnAspect(VmiRsnDefault vrd, ExtParam extParam) {

    }

    /** 
     *
     */
    @Override
    public void generateReceivingWhenShelvRsnLineAspect(VmiRsnLineDefault vrdLine, ExtParam extParam) {

    }

    /** 
     *
     */
    @Override
    public void generateRtnWhRtwAspect(VmiRtwDefault rtw, ExtParam extParam) {

    }

    /** 
     *
     */
    @Override
    public void generateRtnWhRtwLineAspect(VmiRtwLineDefault rtwLine, ExtParam extParam) {

    }

    /** 
     *
     */
    @Override
    public void generateRtnWhTfxAspect(VmiTfxDefault tfx, ExtParam extParam) {

    }

    /** 
     *
     */
    @Override
    public void generateRtnWhTfxLineAspect(VmiTfxLineDefault tfxLine, ExtParam extParam) {

    }

    /** 
     *
     */
    @Override
    public void generateVMIReceiveInfoByInvCkAdjAspect(VmiAdjDefault adj, ExtParam extParam) {

    }

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
    public void createStaForVMIReturnAspect(ExtParam extParam) {

    }

    /**
     * 转店tfx处理面
     */
    @Override
    public void generateReceivingTransferTfxAspect(VmiTfxDefault tfx, ExtParam extParam) {

    }

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
    public void generateReceivingTransferRsnAspect(VmiRsnDefault vrd, ExtParam extParam) {

    }

    /**
     * 转店rsnLine处理面
     */
    @Override
    public void generateReceivingTransferRsnLineAspect(VmiRsnLineDefault vrdLine, ExtParam extParam) {

    }



}
