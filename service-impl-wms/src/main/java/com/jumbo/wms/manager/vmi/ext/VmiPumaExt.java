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
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnDao;
import com.jumbo.dao.vmi.defaultData.VmiRtoDao;
import com.jumbo.dao.vmi.defaultData.VmiRtoLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.Default.AsnOrderType;
import com.jumbo.wms.model.vmi.Default.RsnType;
import com.jumbo.wms.model.vmi.Default.VmiAdjDefault;
import com.jumbo.wms.model.vmi.Default.VmiAdjLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiAsnCommand;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineCommand;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.vmi.Default.VmiRsnDefault;
import com.jumbo.wms.model.vmi.Default.VmiRsnLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtoCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtwDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtwLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiTfxDefault;
import com.jumbo.wms.model.vmi.Default.VmiTfxLineDefault;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;

/**
 * @author lichuan
 * 
 */
public class VmiPumaExt extends VmiDefaultExt {

    private static final long serialVersionUID = -495590503639088716L;
    @Autowired
    private VmiAsnDao vmiAsnDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private VmiRtoDao vmiRtoDao;
    @Autowired
    private VmiRtoLineDao vmiRtoLineDao;
    @Autowired
    private BiChannelDao companyShopDao;

    /** 
     *
     */
    @Override
    public void generateInboundStaSetHeadAspect(StockTransApplication head, ExtParam extParam) {
        if (null == extParam) {
            return;
        }
        String orderCode = extParam.getOrderCode();
        String vmiSource = extParam.getVmiSource();
        if (StringUtil.isEmpty(orderCode)) {
            return;
        }
        List<VmiAsnCommand> list = vmiAsnDao.findVmiAsnList(orderCode, vmiSource, new BeanPropertyRowMapper<VmiAsnCommand>(VmiAsnCommand.class));
        if (list.size() > 0) {
            String extMemo = list.get(0).getExtMemo();
            head.setExtMemo(extMemo);
        }
    }

    /**
     * 品牌获取渠道信息扩展
     */
    @Override
    public ExtReturn findVmiDefaultTbiChannelExt(ExtParam extParam) {
        if (null == extParam) {
            return null;
        }
        ExtReturn extReturn = new ExtReturn();
        String vmiCode = extParam.getVmiCode();
        String vmiSource = extParam.getVmiSource();
        if (!StringUtil.isEmpty(vmiCode)) {
            BiChannelCommand shop = companyShopDao.findVmiDefaultTbiChannel(vmiCode, vmiSource, 1L, new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
            if (null == shop) {
                log.error("default shop not found by vmiCode [{}]", vmiCode);
                throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
            }
            extReturn.setShopCmd(shop);
            return extReturn;
        }
        return null;
    }


    /**
     * 将收货指令中ext_memo设置到sta
     */
    @Override
    public void generateVmiInboundStaAspect(StockTransApplication sta, ExtParam extParam) {
        if (null == extParam) {
            return;
        }
        StockTransApplication head = extParam.getSta();
        if (null == head) {
            return;
        }
        sta.setExtMemo(head.getExtMemo());
    }

    /**
     * 将收货指令明细中extMemo设置到staLine
     */
    @Override
    public void generateVmiInboundStaLineAspect(StaLine staLine, ExtParam extParam) {
        if (null == extParam) {
            return;
        }
        List<VmiAsnLineCommand> valList = extParam.getValList();
        String extCode2 = (null != staLine ? (null != staLine.getSku() ? (staLine.getSku().getExtensionCode2()) : "") : "");
        if (StringUtil.isEmpty(extCode2)) {
            return;
        }
        if (null != valList) {
            for (VmiAsnLineCommand val : valList) {
                String sku = val.getUpc();
                String extMemo = val.getExtMemo();
                if (extCode2.equals(sku)) {
                    staLine.setExtMemo(extMemo);
                    break;
                }
            }
        }
    }

    /**
     * 上架时将sta上的extMemo设置到rsn
     */
    @Override
    public void generateReceivingWhenShelvRsnAspect(VmiRsnDefault vrd, ExtParam extParam) {
        if (null == extParam) {
            return;
        }
        StockTransApplication sta = extParam.getSta();
        vrd.setExtMemo((null != sta ? sta.getExtMemo() : ""));
        vrd.setIsVmiExt(true);// 标记为定制反馈
        String asnOrderType = extParam.getAsnOrderType();
        if (!StringUtil.isEmpty(asnOrderType) && String.valueOf(AsnOrderType.TYPETWO.getValue()).equals(asnOrderType)) {
            // 按箱收货子单设置反馈单号与箱号，同为主入库单的单号与箱号
            if ((null != sta ? (null != sta.getGroupSta() ? (null != sta.getGroupSta().getId() ? true : false) : false) : false)) {
                vrd.setOrderCode(null == sta.getSlipCode2() ? "" : sta.getSlipCode2());
                vrd.setCartonNo(null == sta.getSlipCode1() ? "" : sta.getSlipCode1());
            }
        }

    }

    /**
     * 上架时将staLine上的extMemo设置到rsnLine
     */
    @Override
    public void generateReceivingWhenShelvRsnLineAspect(VmiRsnLineDefault vrdLine, ExtParam extParam) {
        if (null == extParam) {
            return;
        }
        String rsnType = extParam.getRsnType();
        if (rsnType.equals(String.valueOf(RsnType.TYPEONE.getValue()))) {
            StaLine staLine = extParam.getStaLine();
            vrdLine.setExtMemo((null != staLine ? staLine.getExtMemo() : ""));
        } else if (rsnType.equals(String.valueOf(RsnType.TYPETWO.getValue()))) {
            String extMemo = "";
            SkuCommand skuCmd = extParam.getSkuCmd();
            String extCode2 = (null != skuCmd ? skuCmd.getExtensionCode2() : "");
            List<StaLine> staLineList = extParam.getStaLineList();
            if (null != staLineList) {
                for (StaLine line : staLineList) {
                    Sku sku = skuDao.getByPrimaryKey(line.getSku().getId());
                    if (extCode2.equals(sku.getExtensionCode2())) {
                        extMemo = line.getExtMemo();
                        break;
                    }
                }
                vrdLine.setExtMemo(extMemo);
            }
        }
        String asnOrderType = extParam.getAsnOrderType();
        if (!StringUtil.isEmpty(asnOrderType) && String.valueOf(AsnOrderType.TYPETWO.getValue()).equals(asnOrderType)) {
            StockTransApplication sta = extParam.getSta();
            // 按箱收货子单设置反馈单号与箱号，同为主入库单的单号与箱号
            if ((null != sta ? (null != sta.getGroupSta() ? (null != sta.getGroupSta().getId() ? true : false) : false) : false)) {
                vrdLine.setCartonNo(null == sta.getSlipCode1() ? "" : sta.getSlipCode1());
            }
        }

    }

    /**
     * 退大仓出库时将sta上的extMemo设置到rtw
     */
    @Override
    public void generateRtnWhRtwAspect(VmiRtwDefault rtw, ExtParam extParam) {
        if (null == extParam) {
            return;
        }
        StockTransApplication sta = extParam.getSta();
        rtw.setIsVmiExt(true);// 标记为定制反馈
        BiChannelCommand shop = extParam.getShopCmd();
        String slipCode = StringUtils.trimWhitespace(sta.getRefSlipCode());
        if (StringUtil.isEmpty(slipCode)) {
            throw new BusinessException(ErrorCode.RETURN_ORDER_CODE_IS_NULL);
        }
        // 查询退仓指令
        List<VmiRtoCommand> rtoList = vmiRtoDao.findVmiRtoByOrder(shop.getVmiCode(), slipCode, new BeanPropertyRowMapper<VmiRtoCommand>(VmiRtoCommand.class));
        Long rtoId = null;
        // 校验指令数据
        if (null != rtoList && 1 == rtoList.size()) {
            rtoId = rtoList.get(0).getId();
        }
        // 更新指令头状态
        if (null != rtoId) {
            VmiRtoDefault vmiRto = vmiRtoDao.getByPrimaryKey(rtoId);
            vmiRto.setStatus(VmiGeneralStatus.FINISHED);
            vmiRto.setSta(sta);
            vmiRtoDao.save(vmiRto);
        }
    }

    /**
     * 退大仓出库时将staLine上的extMemo设置到rtwLine
     */
    @Override
    public void generateRtnWhRtwLineAspect(VmiRtwLineDefault rtwLine, ExtParam extParam) {
        SkuCommand skuCmd = extParam.getSkuCmd();
        String extCode2 = (null != skuCmd ? skuCmd.getExtensionCode2() : "");
        BiChannelCommand shop = extParam.getShopCmd();
        StockTransApplication sta = extParam.getSta();
        String slipCode = StringUtils.trimWhitespace(sta.getRefSlipCode());
        if (StringUtil.isEmpty(slipCode)) {
            throw new BusinessException(ErrorCode.RETURN_ORDER_CODE_IS_NULL);
        }
        // 查询退仓指令
        List<VmiRtoCommand> rtoList = vmiRtoDao.findVmiRtoByOrder(shop.getVmiCode(), slipCode, new BeanPropertyRowMapper<VmiRtoCommand>(VmiRtoCommand.class));
        Long rtoId = null;
        VmiRtoCommand rto = null;
        // 校验指令数据
        if (null != rtoList && 1 == rtoList.size()) {
            rto = rtoList.get(0);
            rtoId = rto.getId();
        }
        if (null == rtoId) {
            return;
        }
        Long lineId = null;
        // 退仓指令接收明细
        List<VmiRtoLineCommand> lineList = vmiRtoLineDao.findRtoLineListByRtoId(rtoId, new BeanPropertyRowMapper<VmiRtoLineCommand>(VmiRtoLineCommand.class));
        for (VmiRtoLineCommand rtoLine : lineList) {
            String code = rtoLine.getUpc();
            if (extCode2.equals(code)) {
                lineId = rtoLine.getId();
                // rtwLine.setLineNo(rtoLine.getLineNo());
                rtwLine.setConsigneeKey(rtoLine.getConsigneeKey());
                rtwLine.setOriginalQty(rtoLine.getOriginalQty());
                rtwLine.setUom(rtoLine.getUom());
                rtwLine.setExtMemo(rtoLine.getExtMemo());
                break;
            }
        }
        // 更新行状态
        if (null != lineId) {
            VmiRtoLineDefault vmiLine = vmiRtoLineDao.getByPrimaryKey(lineId);
            vmiLine.setStatus(VmiGeneralStatus.FINISHED);
            vmiRtoLineDao.save(vmiLine);
        }
    }

    /**
     * 转店退仓tfx处理面
     */
    @Override
    public void generateRtnWhTfxAspect(VmiTfxDefault tfx, ExtParam extParam) {
        tfx.setStatus(VmiGeneralStatus.NO_FEEDBACK_INTERFACE);// 由于puma没有转店退仓的反馈接口，反馈数据仅记录不反馈
    }

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
    public void generateVMIReceiveInfoByInvCkAdjAspect(VmiAdjDefault adj, ExtParam extParam) {
        adj.setIsVmiExt(true);// 标记为定制反馈
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
        /*
         * StockTransApplication sta = extParam.getSta(); BiChannel shop = extParam.getShop();
         * Map<String, StaLine> staMap = extParam.getStaLineMap(); String slipCode =
         * StringUtils.trimWhitespace(sta.getRefSlipCode()); String type = extParam.getStrType(); if
         * (StringUtil.isEmpty(slipCode)) { throw new
         * BusinessException(ErrorCode.RETURN_ORDER_CODE_IS_NULL); } // 查询退仓指令 List<VmiRtoCommand>
         * rtoList = vmiRtoDao.findVmiRtoByOrder(shop.getVmiCode(), slipCode, new
         * BeanPropertyRowMapper<VmiRtoCommand>(VmiRtoCommand.class)); Long rtoId = null;
         * VmiRtoCommand rto = null; // 校验指令数据 if (null != rtoList && 1 == rtoList.size()) { rto =
         * rtoList.get(0); rtoId = rto.getId(); if (null != rto.getStaId()) { throw new
         * BusinessException(ErrorCode.RETURN_ORDER_STA_IS_CREATED, new Object[] {slipCode}); } }
         * else { throw new BusinessException(ErrorCode.RETURN_ORDER_DATA_ERROR_CONTACT_IT, new
         * Object[] {slipCode}); } if (null == rtoId) { throw new
         * BusinessException(ErrorCode.RETURN_ORDER_DATA_ERROR_CONTACT_IT, new Object[] {slipCode});
         * } // 退仓指令接收明细与导入明细行是否匹配 List<VmiRtoLineCommand> lineList =
         * vmiRtoLineDao.findRtoLineListByRtoId(rtoId, new
         * BeanPropertyRowMapper<VmiRtoLineCommand>(VmiRtoLineCommand.class)); List<String> errorSku
         * = new ArrayList<String>(); for (String s : staMap.keySet()) { StaLine staLine =
         * staMap.get(s); String skuCode = null; Long skuNum = null; Sku sku = staLine.getSku(); if
         * (null != sku) { if (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) {
         * skuCode = StringUtil.isEmpty(sku.getBarCode()) ? "" : sku.getBarCode(); } else if
         * (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) { skuCode =
         * StringUtil.isEmpty(sku.getCode()) ? "" : sku.getCode(); } skuNum = (null ==
         * staLine.getQuantity()) ? 0L : staLine.getQuantity(); } if ("".equals(skuCode) || 0L ==
         * skuNum) { continue; } boolean isExist = false; boolean isEqual = false; for
         * (VmiRtoLineCommand rtoLine : lineList) { String code = ""; if
         * (Constants.BETWEENLIBARY_MOVE_SKU_BARCODE_DESCRIBE.equals(type)) { code =
         * rtoLine.getSkuBarcode(); } else if
         * (Constants.BETWEENLIBARY_MOVE_SKU_CODE_DESCRIBE.equals(type)) { code =
         * rtoLine.getSkuCode(); } Long num = rtoLine.getQty(); if (!StringUtil.isEmpty(code)) { if
         * (code.equals(skuCode)) { isExist = true; if (null != num && (0 == num.compareTo(skuNum)))
         * { isEqual = true; break; } } } } // 如果导入明细行中商品不存在与指令明细或商品数量与指令行数量不相等时视为异常商品 if ((false ==
         * isExist && false == isEqual) || (true == isExist && false == isEqual)) {
         * errorSku.add(skuCode); } } if (0 < errorSku.size()) { throw new
         * BusinessException(ErrorCode.RETURN_ORDER_LINE_NOT_EQUALS_IMPORT_LINE, new Object[]
         * {errorSku.toString()}); } // 更新指令头状态 if (null != rtoId) { VmiRtoDefault vmiRto =
         * vmiRtoDao.getByPrimaryKey(rtoId); vmiRto.setSta(sta); vmiRtoDao.save(vmiRto); } // 更新行状态
         * for (VmiRtoLineCommand vmiRtoLine : lineList) { Long lineId = vmiRtoLine.getId(); if
         * (null != lineId) { VmiRtoLineDefault vmiLine = vmiRtoLineDao.getByPrimaryKey(lineId);
         * vmiLine.setSta(sta); vmiRtoLineDao.save(vmiLine); } }
         */
    }

    /**
     * 转店tfx处理面
     */
    @Override
    public void generateReceivingTransferTfxAspect(VmiTfxDefault tfx, ExtParam extParam) {
        tfx.setStatus(VmiGeneralStatus.NO_FEEDBACK_INTERFACE);// 由于puma没有转店的反馈接口，反馈数据仅记录不反馈
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
        vrd.setStatus(VmiGeneralStatus.NO_FEEDBACK_INTERFACE);// 由于puma没有转店的反馈接口，反馈数据仅记录不反馈
    }

    /**
     * 转店rsnLine处理面
     */
    @Override
    public void generateReceivingTransferRsnLineAspect(VmiRsnLineDefault vrdLine, ExtParam extParam) {

    }

}
