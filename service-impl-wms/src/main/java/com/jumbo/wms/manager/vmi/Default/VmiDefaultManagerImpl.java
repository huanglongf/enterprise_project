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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.defaultData.VmiAdjDao;
import com.jumbo.dao.vmi.defaultData.VmiAdjLineDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnLineDao;
import com.jumbo.dao.vmi.defaultData.VmiInBoundDao;
import com.jumbo.dao.vmi.defaultData.VmiInBoundLineDao;
import com.jumbo.dao.vmi.defaultData.VmiInventoryDao;
import com.jumbo.dao.vmi.defaultData.VmiOutBoundDao;
import com.jumbo.dao.vmi.defaultData.VmiOutBoundLineDao;
import com.jumbo.dao.vmi.defaultData.VmiRsnDao;
import com.jumbo.dao.vmi.defaultData.VmiRsnLineDao;
import com.jumbo.dao.vmi.defaultData.VmiRtoDao;
import com.jumbo.dao.vmi.defaultData.VmiRtoLineDao;
import com.jumbo.dao.vmi.defaultData.VmiRtwDao;
import com.jumbo.dao.vmi.defaultData.VmiRtwLineDao;
import com.jumbo.dao.vmi.defaultData.VmiStatusAdjDao;
import com.jumbo.dao.vmi.defaultData.VmiStatusAdjLineDao;
import com.jumbo.dao.vmi.defaultData.VmiTfxDao;
import com.jumbo.dao.vmi.defaultData.VmiTfxLineDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.rmi.warehouse.WmsResponse;
import com.jumbo.rmi.warehouse.vmi.VmiAdj;
import com.jumbo.rmi.warehouse.vmi.VmiAdjLine;
import com.jumbo.rmi.warehouse.vmi.VmiInBound;
import com.jumbo.rmi.warehouse.vmi.VmiInBoundLine;
import com.jumbo.rmi.warehouse.vmi.VmiInventory;
import com.jumbo.rmi.warehouse.vmi.VmiOutBound;
import com.jumbo.rmi.warehouse.vmi.VmiOutBoundLine;
import com.jumbo.rmi.warehouse.vmi.VmiRsn;
import com.jumbo.rmi.warehouse.vmi.VmiRsnLine;
import com.jumbo.rmi.warehouse.vmi.VmiRtw;
import com.jumbo.rmi.warehouse.vmi.VmiRtwLine;
import com.jumbo.rmi.warehouse.vmi.VmiTfx;
import com.jumbo.rmi.warehouse.vmi.VmiTfxLine;
import com.jumbo.service.HubService;
import com.jumbo.util.MailUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.vmi.VmiFactory;
import com.jumbo.wms.manager.vmi.VmiInterface;
import com.jumbo.wms.manager.vmi.ext.ExtParam;
import com.jumbo.wms.manager.vmi.ext.ExtReturn;
import com.jumbo.wms.manager.vmi.ext.VmiExtFactory;
import com.jumbo.wms.manager.vmi.ext.VmiInterfaceExt;
import com.jumbo.wms.manager.vmi.vmiInventory.VmiInventoryManager;
import com.jumbo.wms.manager.warehouse.vmi.VmiStaCreateManager;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.Default.AsnOrderType;
import com.jumbo.wms.model.vmi.Default.VmiAdjCommand;
import com.jumbo.wms.model.vmi.Default.VmiAdjDefault;
import com.jumbo.wms.model.vmi.Default.VmiAdjLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiAsnCommand;
import com.jumbo.wms.model.vmi.Default.VmiAsnDefault;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.vmi.Default.VmiInBoundCommand;
import com.jumbo.wms.model.vmi.Default.VmiInBoundDefault;
import com.jumbo.wms.model.vmi.Default.VmiInBoundLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiInventoryCommand;
import com.jumbo.wms.model.vmi.Default.VmiInventoryDefault;
import com.jumbo.wms.model.vmi.Default.VmiOutBoundCommand;
import com.jumbo.wms.model.vmi.Default.VmiOutBoundDefault;
import com.jumbo.wms.model.vmi.Default.VmiOutBoundLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiRsnCommand;
import com.jumbo.wms.model.vmi.Default.VmiRsnDefault;
import com.jumbo.wms.model.vmi.Default.VmiRsnLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtoCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtwCommand;
import com.jumbo.wms.model.vmi.Default.VmiRtwDefault;
import com.jumbo.wms.model.vmi.Default.VmiRtwLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiStatusAdjCommand;
import com.jumbo.wms.model.vmi.Default.VmiStatusAdjDefault;
import com.jumbo.wms.model.vmi.Default.VmiStatusAdjLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiTfxCommand;
import com.jumbo.wms.model.vmi.Default.VmiTfxDefault;
import com.jumbo.wms.model.vmi.Default.VmiTfxLineDefault;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;

@Transactional
@Service("vmiDefaultManager")
public class VmiDefaultManagerImpl extends BaseManagerImpl implements VmiDefaultManager {

    private static final long serialVersionUID = -1268148440633128717L;

    private static final String categoryCode = "SYS_EMAIL";

    private static final String outBound = "outbound";

    private static final String inBound = "inbound";

    private static final String String = null;


    @Autowired
    private VmiAsnDao vmiAsnDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private VmiAsnLineDao vmiAsnLineDao;
    @Autowired
    private VmiRsnDao vmiRsnDao;
    @Autowired
    private VmiRsnLineDao vmiRsnLineDao;
    @Autowired
    private HubService hubService;
    @Autowired
    private VmiStaCreateManager vmiStaCreateManager;
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private VmiFactory vmiFactory;
    @Autowired
    private VmiTfxDao vmiTfxDao;
    @Autowired
    private VmiTfxLineDao vmiTfxLineDao;
    @Autowired
    private VmiRtwDao vmiRtwDao;
    @Autowired
    private VmiRtwLineDao vmiRtwLineDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private VmiAdjDao vmiAdjDao;
    @Autowired
    private VmiAdjLineDao vmiAdjLineDao;
    @Autowired
    private VmiInventoryDao vmiInventoryDao;
    @Autowired
    private VmiExtFactory vmiExtFactory;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private VmiOutBoundDao vmiOutBoundDao;
    @Autowired
    private VmiOutBoundLineDao vmiOutBoundLineDao;
    @Autowired
    private VmiInBoundDao vmiInBoundDao;
    @Autowired
    private VmiInBoundLineDao vmiInBoundLineDao;
    @Autowired
    private VmiInventoryManager vmiInventoryManager;
    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private VmiRtoDao vmiRtoDao;
    @Autowired
    private VmiRtoLineDao vmiRtoLineDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private VmiStatusAdjLineDao vmiStatusAdjLineDao;
    @Autowired
    private VmiStatusAdjDao vmiStatusAdjDao;
    @Autowired
    private SkuDao skudao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private SkuDao skuDao;

    @Override
    public void createStaForVmiDefault(String vmiCode, String vmiSource) {
        // 创建作业单
        BiChannelCommand shop = companyShopDao.findVmiDefaultTbiChannel(vmiCode, vmiSource, null, new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        if (shop == null) {
            log.error("shop not found by vmiCode [{}]", vmiCode);
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        VmiInterfaceExt vmiBrandExt = null;
        vmiBrandExt = vmiExtFactory.getBrandVmi(shop.getVmiCode());// 品牌逻辑定制
        if (null != vmiBrandExt) {
            ExtParam extParam = new ExtParam();
            extParam.setVmiCode(vmiCode);
            extParam.setVmiSource(vmiSource);
            ExtReturn extReturn = vmiBrandExt.findVmiDefaultTbiChannelExt(extParam);// 定制逻辑，获取创指令的默认创单渠道
            if (null != extReturn && null != extReturn.getShopCmd()) {
                shop = extReturn.getShopCmd();// 替换为默认入库渠道
            }
        }
        VmiInterface vmiBrand = vmiFactory.getBrandVmi(shop.getVmiCode());
        if (vmiBrand != null) {
            List<String> orders = vmiBrand.generateInboundStaGetAllOrderCodeDefault(vmiCode, vmiSource, shop.getAsnTypeString());
            for (String orderCode : orders) {
                try {
                    vmiStaCreateManager.generateVmiInboundStaBySlipCodeDefault(vmiCode, vmiSource, orderCode, shop);
                } catch (Exception e) {
                    if (e instanceof BusinessException) {
                        log.error("generate vmi invound sta error : error orderCode is [{}], error code is [{}]", orderCode, ((BusinessException) e).getErrorCode());
                    } else {
                        log.error("", e);
                    }
                }
            }
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void updateVmiAsnStatus(String vmiCode, String vmiSource) {
        BiChannelCommand shop = companyShopDao.findVmiDefaultTbiChannel(vmiCode, vmiSource, null, new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        if (shop == null) {
            if (log.isInfoEnabled()) {
                log.info("shop not found by vmiCode [{}]", vmiCode);
            }
            throw new BusinessException(ErrorCode.SHOP_NOT_FOUND);
        }
        VmiInterfaceExt vmiBrandExt = null;
        vmiBrandExt = vmiExtFactory.getBrandVmi(shop.getVmiCode());// 品牌逻辑定制
        if (null != vmiBrandExt) {
            ExtParam extParam = new ExtParam();
            extParam.setVmiCode(vmiCode);
            extParam.setVmiSource(vmiSource);
            ExtReturn extReturn = vmiBrandExt.findVmiDefaultTbiChannelExt(extParam);// 定制逻辑，获取创指令的默认创单渠道
            if (null != extReturn && null != extReturn.getShopCmd()) {
                shop = extReturn.getShopCmd();// 替换为默认渠道
            }
        }
        List<VmiAsnCommand> vList = vmiAsnDao.findVmiAsnAll(vmiCode, vmiSource, new BeanPropertyRowMapper<VmiAsnCommand>(VmiAsnCommand.class));
        for (VmiAsnCommand v : vList) {
            try {
                if (shop.getAsnTypeString().equals(String.valueOf(AsnOrderType.TYPETWO.getValue()))) {
                    // 按箱 创建完作业单后 判断vmiasn对应的vmiasnline的数据是否全部成功
                    // 如果部分成功则vmiasn状态为5, 不然为10
                    int countError = vmiAsnLineDao.findVmiAsnLineErrorCount(v.getId(), new SingleColumnRowMapper<Integer>(Integer.class));// 失败数据
                    int countOk = vmiAsnLineDao.findVmiAsnLineOkCount(v.getId(), new SingleColumnRowMapper<Integer>(Integer.class));// 成功数据
                    if (countError > 0 && countOk > 0) {
                        // 部分完成
                        vmiAsnDao.updateVmiAsnStatus(v.getId(), VmiGeneralStatus.PARTFINISH.getValue());
                    }
                    if (countError == 0 && countOk > 0) {
                        // 全部完成
                        vmiAsnDao.updateVmiAsnStatus(v.getId(), VmiGeneralStatus.FINISHED.getValue());
                    }
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }

    @SuppressWarnings("static-access")
    @Override
    public void updateVmiRsnStatus(String vmiCode, String vmiSource) {
        List<VmiAsnCommand> vList = vmiAsnDao.findVmiAsnAll(vmiCode, vmiSource, new BeanPropertyRowMapper<VmiAsnCommand>(VmiAsnCommand.class));
        // 判断是否转店入库数据
        for (VmiAsnCommand v : vList) {
            BiChannelCommand shop = companyShopDao.findVmiDefaultTbiChannel(v.getStoreCode(), v.getVmiSource(), null, new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
            if (shop == null) {
                log.debug("shop not found by vmiCode [{}]", v.getStoreCode());
                continue;
            }
            VmiInterfaceExt vmiBrandExt = null;
            vmiBrandExt = vmiExtFactory.getBrandVmi(shop.getVmiCode());// 品牌逻辑定制
            if (null != vmiBrandExt) {
                ExtParam extParam = new ExtParam();
                extParam.setVmiCode(vmiCode);
                extParam.setVmiSource(vmiSource);
                ExtReturn extReturn = vmiBrandExt.findVmiDefaultTbiChannelExt(extParam);// 定制逻辑，获取创指令的默认创单渠道
                if (null != extReturn && null != extReturn.getShopCmd()) {
                    shop = extReturn.getShopCmd();// 替换为默认渠道
                }
            }
            if (shop.getAsnTypeString().equals(String.valueOf(AsnOrderType.TYPEONE.getValue()))) {
                // 按单 查询是否存在转店入库作业单
                int count = staDao.findVmiAsnBySlipCode(v.getOrderCode(), shop.getCode(), StockTransApplicationType.VMI_OWNER_TRANSFER.getValue(), new SingleColumnRowMapper<Integer>(Integer.class));
                if (count > 0) {
                    // 存在转店入库作业单 直接修改vmiAsn状态为10
                    VmiAsnDefault vad = vmiAsnDao.getByPrimaryKey(v.getId());
                    vad.setFinishTime(new Date());
                    vad.setStatus(VmiGeneralStatus.FINISHED);
                    vmiAsnDao.save(vad);
                    vmiAsnLineDao.updateVmiAsnLineStatus(v.getId(), VmiGeneralStatus.FINISHED.getValue());// 修改vmiasnline状态为10
                    vmiRsnDao.updateVmiRsnStatusByVmiAsn(vad.getOrderCode(), vad.getStoreCode(), VmiGeneralStatus.NEW.getValue());// 修改vmirsn状态为1
                    vmiAsnDao.flush();
                    continue;
                }
            }
        }
    }

    /**
     * 同步收货反馈给HUB
     */
    @Override
    public void uploadVmiRsnToHub(VmiRsnCommand vr) {
        if (vr.getErrorCount() > 5) {
            // 失败5次发送失败邮件
            VmiRsnDefault vrd = vmiRsnDao.getByPrimaryKey(vr.getId());
            vrd.setStatus(VmiGeneralStatus.FAILED);
            vrd.setFinishTime(new Date());
            vmiRsnDao.save(vrd);
        } else {
            VmiRsn v = new VmiRsn();
            VmiRsnLine[] rsnLines = new VmiRsnLine[] {};
            List<VmiRsnLine> vmiList = new ArrayList<VmiRsnLine>();
            List<VmiRsnLineDefault> vrld = vmiRsnLineDao.findVmiRsnLineByRsnId(vr.getId(), new BeanPropertyRowMapper<VmiRsnLineDefault>(VmiRsnLineDefault.class));
            v.setUuid(uuidString());
            v.setStoreCode(vr.getStoreCode());
            v.setOrderCode(vr.getOrderCode());
            v.setReceiveDate(vr.getReceiveDate());
            v.setFromLocation(vr.getFromLocation());
            v.setToLocation(vr.getToLoaction());
            v.setWhCode(vr.getWhCode());
            v.setExtMemo(vr.getExtMemo());
            v.setType(vr.getType());
            for (VmiRsnLineDefault vList : vrld) {
                VmiRsnLine vrl = new VmiRsnLine();
                vrl.setUpc(vList.getUpc());
                vrl.setQty(vList.getQty());
                vrl.setCartonNo(vList.getCartonNo());
                vrl.setCartonStatus(vList.getCartonStatus());
                vrl.setLineSeq(vList.getLineSeq());
                vrl.setExtMemo(vList.getExtMemo());
                vmiList.add(vrl);
            }
            VmiRsnLine[] objArray = vmiList.toArray(rsnLines);
            v.setRsnLines(objArray);
            WmsResponse w = hubService.deliveryNoteReceiptConfirm(v);
            if (w.getStatus() == WmsResponse.STATUS_SUCCESS) {
                // 成功更新状态10
                VmiRsnDefault vmiRsnDefault = vmiRsnDao.getByPrimaryKey(vr.getId());
                vmiRsnDefault.setStatus(VmiGeneralStatus.FINISHED);
                vmiRsnDefault.setFinishTime(new Date());
                vmiRsnDao.save(vmiRsnDefault);
            }
            if (w.getStatus() == WmsResponse.STATUS_ERROR) {
                log.debug("VmiDefault uploadVmiRsnToHub to Hub Error msg:" + w.getMsg());
                updateVmiErrorCount(VmiRsnDefault.vmirsn, vr.getId());// 修改error次数
            }
        }
    }

    @Override
    public void uploadVmiRsnToHubPumaExt(List<VmiRsnCommand> vrList) {
        List<VmiRsn> sycList = new ArrayList<VmiRsn>();
        for (VmiRsnCommand vr : vrList) {
            if (vr.getErrorCount() > 5) {
                // 失败5次发送失败邮件
                VmiRsnDefault vrd = vmiRsnDao.getByPrimaryKey(vr.getId());
                vrd.setStatus(VmiGeneralStatus.FAILED);
                vrd.setFinishTime(new Date());
                vmiRsnDao.save(vrd);
            } else {
                VmiRsn v = new VmiRsn();
                VmiRsnLine[] rsnLines = new VmiRsnLine[] {};
                List<VmiRsnLine> vmiList = new ArrayList<VmiRsnLine>();
                List<VmiRsnLineDefault> vrld = vmiRsnLineDao.findVmiRsnLineByRsnId(vr.getId(), new BeanPropertyRowMapper<VmiRsnLineDefault>(VmiRsnLineDefault.class));
                v.setUuid(uuidString());
                v.setStoreCode(vr.getStoreCode());
                v.setOrderCode(vr.getOrderCode());
                v.setReceiveDate(vr.getReceiveDate());
                v.setFromLocation(vr.getFromLocation());
                v.setToLocation(vr.getToLoaction());
                v.setWhCode(vr.getWhCode());
                v.setExtMemo(vr.getExtMemo());
                v.setType(vr.getType());
                for (VmiRsnLineDefault vList : vrld) {
                    VmiRsnLine vrl = new VmiRsnLine();
                    vrl.setUpc(vList.getUpc());
                    vrl.setQty(vList.getQty());
                    vrl.setCartonNo(vList.getCartonNo());
                    vrl.setCartonStatus(vList.getCartonStatus());
                    vrl.setLineSeq(vList.getLineSeq());
                    vrl.setExtMemo(vList.getExtMemo());
                    vmiList.add(vrl);
                }
                VmiRsnLine[] objArray = vmiList.toArray(rsnLines);
                v.setRsnLines(objArray);
                sycList.add(v);
            }
        }
        if (sycList.size() > 0) {
            WmsResponse w = hubService.pumaRec(sycList);
            if (w.getStatus() == WmsResponse.STATUS_SUCCESS) {
                // 成功更新状态10
                for (VmiRsnCommand vr : vrList) {
                    if (null != vr.getId()) {
                        VmiRsnDefault vmiRsnDefault = vmiRsnDao.getByPrimaryKey(vr.getId());
                        vmiRsnDefault.setStatus(VmiGeneralStatus.FINISHED);
                        vmiRsnDefault.setFinishTime(new Date());
                        vmiRsnDao.save(vmiRsnDefault);
                    }
                }
            }
            if (w.getStatus() == WmsResponse.STATUS_ERROR) {
                log.debug("VmiDefault uploadVmiRsnToHub to Hub Error msg:" + w.getMsg());
                for (VmiRsnCommand vr : vrList) {
                    if (null != vr.getId()) {
                        updateVmiErrorCount(VmiRsnDefault.vmirsn, vr.getId());// 修改error次数
                    }
                }
            }
        }
    }

    /**
     * 同步转出反馈给HUB
     */
    @Override
    public void uploadVmiTfxToHub(VmiTfxCommand vr) {
        if (vr.getErrorCount() > 5) {
            // 失败5次发送失败邮件
            VmiTfxDefault vtd = vmiTfxDao.getByPrimaryKey(vr.getId());
            vtd.setStatus(VmiGeneralStatus.FAILED);
            vtd.setFinishTime(new Date());
            vmiTfxDao.save(vtd);
        } else {
            VmiTfx v = new VmiTfx();
            VmiTfxLine[] tfxLines = new VmiTfxLine[] {};
            List<VmiTfxLine> vmiList = new ArrayList<VmiTfxLine>();
            List<VmiTfxLineDefault> vrld = vmiTfxLineDao.findVmiTfxLineByTfxId(vr.getId(), new BeanPropertyRowMapper<VmiTfxLineDefault>(VmiTfxLineDefault.class));
            v.setUuid(uuidString());
            v.setStoreCode(vr.getStoreCode());
            v.setOrderCode(vr.getOrderCode());
            v.setReturnDate(vr.getReturnDate());
            v.setFromLocation(vr.getFromLocation());
            v.setToLocation(vr.getToLoaction());
            v.setWhCode(vr.getWhCode());
            v.setExtMemo(vr.getExtMemo());
            for (VmiTfxLineDefault vList : vrld) {
                VmiTfxLine vrl = new VmiTfxLine();
                vrl.setUpc(vList.getUpc());
                vrl.setQty(vList.getQty());
                vrl.setCartonNo(vList.getCartonNo());
                vrl.setLineSeq(vList.getLineSeq());
                vrl.setExtMemo(vList.getExtMemo());
                vrl.setInvStatus(vList.getInvStatus());
                vmiList.add(vrl);
            }
            VmiTfxLine[] objArray = vmiList.toArray(tfxLines);
            v.setTfxLines(objArray);
            WmsResponse w = hubService.transferOutConfirm(v);
            if (w.getStatus() == WmsResponse.STATUS_SUCCESS) {
                // 成功更新状态10
                VmiTfxDefault vmiTfxDefault = vmiTfxDao.getByPrimaryKey(vr.getId());
                vmiTfxDefault.setStatus(VmiGeneralStatus.FINISHED);
                vmiTfxDefault.setFinishTime(new Date());
                vmiTfxDao.save(vmiTfxDefault);
            }
            if (w.getStatus() == WmsResponse.STATUS_ERROR) {
                log.debug("VmiDefault uploadVmiTfxToHub to Hub Error msg:" + w.getMsg());
                updateVmiErrorCount(VmiTfxDefault.vmitfx, vr.getId());// 修改error次数
            }
        }
    }

    /**
     * 同步退仓信息给HUB
     */
    @Override
    public void uploadVmiRtwToHub(VmiRtwCommand vr) {
        if (vr.getErrorCount() > 5) {
            // 失败5次发送失败邮件
            VmiRtwDefault vrd = vmiRtwDao.getByPrimaryKey(vr.getId());
            vrd.setStatus(VmiGeneralStatus.FAILED);
            vrd.setFinishTime(new Date());
            vmiRtwDao.save(vrd);
        } else {
            VmiRtw v = new VmiRtw();
            VmiRtwLine[] rtwLines = new VmiRtwLine[] {};
            List<VmiRtwLine> vmiList = new ArrayList<VmiRtwLine>();
            List<VmiRtwLineDefault> vrld = vmiRtwLineDao.findVmiRtwLineByRtwId(vr.getId(), new BeanPropertyRowMapper<VmiRtwLineDefault>(VmiRtwLineDefault.class));
            v.setUuid(uuidString());
            v.setStoreCode(vr.getStoreCode());
            v.setOrderCode(vr.getOrderCode());
            v.setReturnDate(vr.getReturnDate());
            v.setWhCode(vr.getWhCode());
            if (null != vr.getStoreCode() && "006422".equals(vr.getStoreCode())) {
                VmiRtwDefault vrd = vmiRtwDao.getByPrimaryKey(vr.getId());
                v.setExtMemo(vrd.getStaId().getActivitySource());
            } else {
                v.setExtMemo(vr.getExtMemo());
            }

            // 查询合并模式配置信息
            ChooseOption op = chooseOptionDao.findByCategoryCodeAndKey(Constants.VMI_RT_TO_HUB, Constants.VMI_RT_VMI_CODES);
            Boolean flag = false;
            if (op != null && op.getOptionValue() != null) {
                String[] vmiCodes = StringUtils.split(op.getOptionValue(), ",");
                List<String> list = Arrays.asList(vmiCodes);
                BiChannel shop = companyShopDao.getByVmiCode(vr.getStoreCode());
                if (shop != null && shop.getVmiCode() != null && list.contains(shop.getVmiCode())) {
                    flag = true;
                }
            }
            for (VmiRtwLineDefault vList : vrld) {
                String upc = vList.getUpc();
                String invStatus = StringUtil.isEmpty(vList.getInvStatus()) ? "" : vList.getInvStatus();
                String boxNum = StringUtil.isEmpty(vList.getCartonNo()) ? "" : vList.getCartonNo();
                if (0 < vmiList.size()) {
                    boolean isExist = false;
                    if (StringUtil.isEmpty(upc)) {
                        throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR);
                    }
                    ListIterator<VmiRtwLine> iter = vmiList.listIterator();
                    // 合并重复明细行
                    while (iter.hasNext()) {
                        VmiRtwLine l = iter.next();
                        String rtwUpc = l.getUpc();
                        String rtwInvStatus = StringUtil.isEmpty(l.getInvStatus()) ? "" : l.getInvStatus();
                        String rtwBoxNum = StringUtil.isEmpty(l.getCartonNo()) ? "" : l.getCartonNo();
                        if (!flag) {
                            // 不区分箱号进行合并
                            if (upc.equals(rtwUpc) && invStatus.equals(rtwInvStatus)) {
                                isExist = true;
                                Long oldQty = (null == l.getQty()) ? 0L : l.getQty();
                                Long newQty = (null == vList.getQty()) ? 0L : vList.getQty();
                                Long reQty = oldQty + newQty;
                                l.setQty(reQty);
                                iter.set(l);
                                break;
                            }
                        } else {
                            // 区分箱号进行合并
                            if (upc.equals(rtwUpc) && invStatus.equals(rtwInvStatus) && StringUtils.equals(rtwBoxNum, boxNum)) {
                                isExist = true;
                                Long oldQty = (null == l.getQty()) ? 0L : l.getQty();
                                Long newQty = (null == vList.getQty()) ? 0L : vList.getQty();
                                Long reQty = oldQty + newQty;
                                l.setQty(reQty);
                                iter.set(l);
                                break;
                            }
                        }
                    }
                    if (false == isExist) {
                        VmiRtwLine vrl = new VmiRtwLine();
                        vrl.setUpc(vList.getUpc());
                        vrl.setQty(vList.getQty());
                        vrl.setCartonNo(vList.getCartonNo());
                        vrl.setLineSeq(vList.getLineSeq());
                        if (null != vr.getStoreCode() && "006422".equals(vr.getStoreCode())) {
                            Warehouse warehouse = warehouseDao.getByOuId(operationUnitDao.getByCode(vr.getWhCode()).getId());
                            Sku sku = skudao.getByExtCode2AndCustomerId(vList.getUpc(), warehouse.getCustomer().getId());
                            if (null != sku && !"".equals(sku)) {
                                vrl.setExtMemo(sku.getBarCode());
                            }

                        } else {
                            vrl.setExtMemo(vList.getExtMemo());
                        }

                        vrl.setInvStatus(vList.getInvStatus());
                        vrl.setLineNo(vList.getLineNo());
                        vrl.setConsigneeKey(vList.getConsigneeKey());
                        vrl.setOriginalQty(vList.getOriginalQty());
                        vrl.setUom(vList.getUom());
                        vmiList.add(vrl);
                    }
                } else {
                    VmiRtwLine vrl = new VmiRtwLine();
                    vrl.setUpc(vList.getUpc());
                    vrl.setQty(vList.getQty());
                    vrl.setCartonNo(vList.getCartonNo());
                    vrl.setLineSeq(vList.getLineSeq());
                    if (null != vr.getStoreCode() && "006422".equals(vr.getStoreCode())) {
                        Warehouse warehouse = warehouseDao.getByOuId(operationUnitDao.getByCode(vr.getWhCode()).getId());
                        Sku sku = skudao.getByExtCode2AndCustomerId(vList.getUpc(), warehouse.getCustomer().getId());
                        if (null != sku && !"".equals(sku)) {
                            vrl.setExtMemo(sku.getBarCode());
                        }
                    } else {
                        vrl.setExtMemo(vList.getExtMemo());
                    }
                    vrl.setInvStatus(vList.getInvStatus());
                    vrl.setLineNo(vList.getLineNo());
                    vrl.setConsigneeKey(vList.getConsigneeKey());
                    vrl.setOriginalQty(vList.getOriginalQty());
                    vrl.setUom(vList.getUom());
                    vmiList.add(vrl);
                }
            }

            VmiRtwLine[] objArray = vmiList.toArray(rtwLines);
            v.setRtwLines(objArray);
            // System.out.println(net.sf.json.JSONObject.fromObject(v).toString());
            WmsResponse w = hubService.returnToVendor(v);
            if (w.getStatus() == WmsResponse.STATUS_SUCCESS) {
                // 成功更新状态10
                VmiRtwDefault vmiRtwDefault = vmiRtwDao.getByPrimaryKey(vr.getId());
                vmiRtwDefault.setStatus(VmiGeneralStatus.FINISHED);
                vmiRtwDefault.setFinishTime(new Date());
                vmiRtwDao.save(vmiRtwDefault);
            }
            if (w.getStatus() == WmsResponse.STATUS_ERROR) {
                log.debug("VmiDefault uploadVmiRtwToHub to Hub Error msg:" + w.getMsg());
                updateVmiErrorCount(VmiRtwDefault.vmirtw, vr.getId());// 修改error次数
            }
        }
    }

    @Override
    public void uploadVmiRtwToHubPumaExt(List<VmiRtwCommand> vrList) {
        List<VmiRtw> sycList = new ArrayList<VmiRtw>();
        for (VmiRtwCommand vr : vrList) {
            if (vr.getErrorCount() > 5) {
                // 失败5次发送失败邮件
                VmiRtwDefault vrd = vmiRtwDao.getByPrimaryKey(vr.getId());
                vrd.setStatus(VmiGeneralStatus.FAILED);
                vrd.setFinishTime(new Date());
                vmiRtwDao.save(vrd);
            } else {
                VmiRtw v = new VmiRtw();
                VmiRtwLine[] rtwLines = new VmiRtwLine[] {};
                List<VmiRtwLine> vmiList = new ArrayList<VmiRtwLine>();
                List<VmiRtwLineDefault> vrld = vmiRtwLineDao.findVmiRtwLineByRtwId(vr.getId(), new BeanPropertyRowMapper<VmiRtwLineDefault>(VmiRtwLineDefault.class));
                v.setUuid(uuidString());
                v.setStoreCode(vr.getStoreCode());
                v.setOrderCode(vr.getOrderCode());
                v.setReturnDate(vr.getReturnDate());
                v.setWhCode(vr.getWhCode());
                v.setExtMemo(vr.getExtMemo());
                for (VmiRtwLineDefault vList : vrld) {
                    /*
                     * String upc = vList.getUpc(); String invStatus =
                     * StringUtil.isEmpty(vList.getInvStatus()) ? "" : vList.getInvStatus(); if (0 <
                     * vmiList.size()) { boolean isExist = false; if (StringUtil.isEmpty(upc)) {
                     * throw new BusinessException(ErrorCode.VMI_SKU_CODE_ERROR); }
                     * ListIterator<VmiRtwLine> iter = vmiList.listIterator(); // 合并重复明细行 while
                     * (iter.hasNext()) { VmiRtwLine l = iter.next(); String rtwUpc = l.getUpc();
                     * String rtwInvStatus = StringUtil.isEmpty(l.getInvStatus()) ? "" :
                     * l.getInvStatus(); if (upc.equals(rtwUpc) && invStatus.equals(rtwInvStatus)) {
                     * isExist = true; Long oldQty = (null == l.getQty()) ? 0L : l.getQty(); Long
                     * newQty = (null == vList.getQty()) ? 0L : vList.getQty(); Long reQty = oldQty
                     * + newQty; l.setQty(reQty); iter.set(l); break; } } if (false == isExist) {
                     * VmiRtwLine vrl = new VmiRtwLine(); vrl.setUpc(vList.getUpc());
                     * vrl.setQty(vList.getQty()); vrl.setCartonNo(vList.getCartonNo());
                     * vrl.setLineSeq(vList.getLineSeq()); vrl.setExtMemo(vList.getExtMemo());
                     * vrl.setInvStatus(vList.getInvStatus()); vrl.setLineNo(vList.getLineNo());
                     * vrl.setConsigneeKey(vList.getConsigneeKey());
                     * vrl.setOriginalQty(vList.getOriginalQty()); vrl.setUom(vList.getUom());
                     * vmiList.add(vrl); } } else {
                     */
                    VmiRtwLine vrl = new VmiRtwLine();
                    vrl.setUpc(vList.getUpc());
                    vrl.setQty(vList.getQty());
                    vrl.setCartonNo(vList.getCartonNo());
                    vrl.setLineSeq(vList.getLineSeq());
                    vrl.setExtMemo(vList.getExtMemo());
                    vrl.setInvStatus(vList.getInvStatus());
                    vrl.setLineNo(vList.getLineNo());
                    vrl.setConsigneeKey(vList.getConsigneeKey());
                    vrl.setOriginalQty(vList.getOriginalQty());
                    vrl.setUom(vList.getUom());
                    vmiList.add(vrl);
                    /* } */
                }
                VmiRtwLine[] objArray = vmiList.toArray(rtwLines);
                v.setRtwLines(objArray);
                sycList.add(v);
            }
        }
        if (sycList.size() > 0) {
            WmsResponse w = hubService.pumaShp(sycList);// puma发送退大仓反馈数据
            if (w.getStatus() == WmsResponse.STATUS_SUCCESS) {
                // 成功更新状态10
                for (VmiRtwCommand vr : vrList) {
                    if (null != vr.getId()) {
                        VmiRtwDefault vmiRtwDefault = vmiRtwDao.getByPrimaryKey(vr.getId());
                        vmiRtwDefault.setStatus(VmiGeneralStatus.FINISHED);
                        vmiRtwDefault.setFinishTime(new Date());
                        vmiRtwDao.save(vmiRtwDefault);
                    }
                }
            }
            if (w.getStatus() == WmsResponse.STATUS_ERROR) {
                log.debug("VmiDefault uploadVmiRsnToHub to Hub Error msg:" + w.getMsg());
                for (VmiRtwCommand vr : vrList) {
                    if (null != vr.getId()) {
                        updateVmiErrorCount(VmiRtwDefault.vmirtw, vr.getId());// 修改error次数
                    }
                }
            }
        }
    }

    /**
     * 同步VMI库存调整反馈数据给HUB
     */
    @Override
    public void uploadVmiAdjToHub(VmiAdjCommand vr) {
        if (vr.getErrorCount() > 5) {
            // 失败5次发送失败邮件
            VmiAdjDefault vad = vmiAdjDao.getByPrimaryKey(vr.getId());
            vad.setStatus(VmiGeneralStatus.FAILED);
            vad.setFinishTime(new Date());
            vmiAdjDao.save(vad);
        } else {
            VmiAdj v = new VmiAdj();
            VmiAdjLine[] adjLines = new VmiAdjLine[] {};
            List<VmiAdjLine> vmiList = new ArrayList<VmiAdjLine>();
            List<VmiAdjLineDefault> vald = vmiAdjLineDao.findVmiAdjLineByAdjId(vr.getId(), new BeanPropertyRowMapper<VmiAdjLineDefault>(VmiAdjLineDefault.class));
            v.setUuid(uuidString());
            v.setStoreCode(vr.getStoreCode());
            v.setOrderCode(vr.getOrderCode());
            v.setAdjDate(vr.getAdjDate());
            v.setWhCode(vr.getWhCode());
            v.setExtMemo(vr.getExtMemo());
            v.setAdjReason(vr.getAdjReason());
            for (VmiAdjLineDefault vList : vald) {
                VmiAdjLine val = new VmiAdjLine();
                val.setUpc(vList.getUpc());
                val.setQty(vList.getQty());
                val.setExtMemo(vList.getExtMemo());
                val.setInvStatus(vList.getInvStatus());
                vmiList.add(val);
            }
            VmiAdjLine[] objArray = vmiList.toArray(adjLines);
            v.setAdjLines(objArray);
            WmsResponse w = hubService.inventoryAdjustment(v);
            if (w.getStatus() == WmsResponse.STATUS_SUCCESS) {
                // 成功更新状态10
                VmiAdjDefault vmiAdjDefault = vmiAdjDao.getByPrimaryKey(vr.getId());
                vmiAdjDefault.setStatus(VmiGeneralStatus.FINISHED);
                vmiAdjDefault.setFinishTime(new Date());
                vmiAdjDao.save(vmiAdjDefault);
            }
            if (w.getStatus() == WmsResponse.STATUS_ERROR) {
                log.debug("VmiDefault uploadVmiAdjToHub to Hub Error msg:" + w.getMsg());
                updateVmiErrorCount(VmiAdjDefault.vmiadj, vr.getId());// 修改error次数
            }
        }
    }

    @Override
    public void uploadVmiAdjToHubPumaExt(List<VmiAdjCommand> vaList) {
        List<VmiAdj> sycList = new ArrayList<VmiAdj>();
        for (VmiAdjCommand vr : vaList) {
            if (vr.getErrorCount() > 5) {
                // 失败5次发送失败邮件
                VmiRtwDefault vrd = vmiRtwDao.getByPrimaryKey(vr.getId());
                vrd.setStatus(VmiGeneralStatus.FAILED);
                vrd.setFinishTime(new Date());
                vmiRtwDao.save(vrd);
            } else {
                VmiAdj v = new VmiAdj();
                VmiAdjLine[] adjLines = new VmiAdjLine[] {};
                List<VmiAdjLine> vmiList = new ArrayList<VmiAdjLine>();
                List<VmiAdjLineDefault> vald = vmiAdjLineDao.findVmiAdjLineByAdjId(vr.getId(), new BeanPropertyRowMapper<VmiAdjLineDefault>(VmiAdjLineDefault.class));
                v.setUuid(uuidString());
                v.setStoreCode(vr.getStoreCode());
                v.setOrderCode(vr.getOrderCode());
                v.setAdjDate(vr.getAdjDate());
                v.setWhCode(vr.getWhCode());
                v.setExtMemo(vr.getExtMemo());
                v.setAdjReason(vr.getAdjReason());
                for (VmiAdjLineDefault vList : vald) {
                    VmiAdjLine val = new VmiAdjLine();
                    val.setUpc(vList.getUpc());
                    val.setQty(vList.getQty());
                    val.setExtMemo(vList.getExtMemo());
                    val.setInvStatus(vList.getInvStatus());
                    vmiList.add(val);
                }
                VmiAdjLine[] objArray = vmiList.toArray(adjLines);
                v.setAdjLines(objArray);
                sycList.add(v);
            }
        }
        if (sycList.size() > 0) {
            WmsResponse w = hubService.pumaItr(sycList);
            if (w.getStatus() == WmsResponse.STATUS_SUCCESS) {
                // 成功更新状态10
                for (VmiAdjCommand vr : vaList) {
                    if (null != vr.getId()) {
                        VmiAdjDefault vmiAdjDefault = vmiAdjDao.getByPrimaryKey(vr.getId());
                        vmiAdjDefault.setStatus(VmiGeneralStatus.FINISHED);
                        vmiAdjDefault.setFinishTime(new Date());
                        vmiAdjDao.save(vmiAdjDefault);
                    }
                }
            }
            if (w.getStatus() == WmsResponse.STATUS_ERROR) {
                log.debug("VmiDefault uploadVmiRsnToHub to Hub Error msg:" + w.getMsg());
                for (VmiAdjCommand vr : vaList) {
                    if (null != vr.getId()) {
                        updateVmiErrorCount(VmiAdjDefault.vmiadj, vr.getId());// 修改error次数
                    }
                }
            }
        }

    }

    @Override
    public List<VmiAsnCommand> findVmiAsnAll() {
        return vmiAsnDao.findVmiAsnStoreCodeVmiSource(new BeanPropertyRowMapper<VmiAsnCommand>(VmiAsnCommand.class));
    }

    @Override
    public List<VmiRsnCommand> findVmiRsnAll() {
        return vmiRsnDao.findVmiRsnAll(new BeanPropertyRowMapper<VmiRsnCommand>(VmiRsnCommand.class));
    }

    @Override
    public List<VmiRsnCommand> findVmiRsnAllExt(String vmiCode) {
        return vmiRsnDao.findVmiRsnAllExt(vmiCode, new BeanPropertyRowMapper<VmiRsnCommand>(VmiRsnCommand.class));
    }

    @Override
    public List<VmiTfxCommand> findVmiTfxAll() {
        return vmiTfxDao.findVmiTfxAll(new BeanPropertyRowMapper<VmiTfxCommand>(VmiTfxCommand.class));
    }

    @Override
    public List<VmiRtwCommand> findVmiRtwAll() {
        return vmiRtwDao.findVmiRtwAll(new BeanPropertyRowMapper<VmiRtwCommand>(VmiRtwCommand.class));
    }

    @Override
    public List<VmiRtwCommand> findVmiRtwAllExt(String vmiCode) {
        return vmiRtwDao.findVmiRtwAllExt(vmiCode, new BeanPropertyRowMapper<VmiRtwCommand>(VmiRtwCommand.class));
    }

    @Override
    public List<VmiRtwCommand> findPumaVmiRtwAllExt(String vmiCode) {
        try {
            matchingPumaLineNoAndRelatedInfo(vmiCode);
        } catch (Exception e) {
            log.error("puma rtw matching rto lineno exception:", e);
        }
        return vmiRtwDao.findPumaVmiRtwAllExt(vmiCode, new BeanPropertyRowMapper<VmiRtwCommand>(VmiRtwCommand.class));
    }

    private void matchingPumaLineNoAndRelatedInfo(String vmiCode) {
        List<VmiRtwCommand> rtwList = vmiRtwDao.findPumaNotHasLineNoVmiRtwAllExt(vmiCode, new BeanPropertyRowMapper<VmiRtwCommand>(VmiRtwCommand.class));
        if (null != rtwList && rtwList.size() > 0) {
            for (VmiRtwCommand rtw : rtwList) {
                Long staId = rtw.getsId();
                if (null == staId) {
                    continue;
                }
                StockTransApplication sta = staDao.getByPrimaryKey(staId);
                String slipCode = rtw.getOrderCode();
                // 查询退仓指令
                List<VmiRtoCommand> rtoList = vmiRtoDao.findPumaVmiRtoByOrder(vmiCode, slipCode, new BeanPropertyRowMapper<VmiRtoCommand>(VmiRtoCommand.class));
                Long rtoId = null;
                VmiRtoCommand rto = null;
                // 校验指令数据
                if (null != rtoList && 1 == rtoList.size()) {
                    rto = rtoList.get(0);
                    rtoId = rto.getId();
                }
                if (null == rtoId) {
                    continue;
                }
                VmiRtoDefault vmiRto = null;
                if (null != rtoId) {
                    vmiRto = vmiRtoDao.getByPrimaryKey(rtoId);
                }
                // 退仓指令接收明细
                List<VmiRtoLineCommand> lineList = vmiRtoLineDao.findRtoLineListByRtoId(rtoId, new BeanPropertyRowMapper<VmiRtoLineCommand>(VmiRtoLineCommand.class));
                Long rtwId = rtw.getId();
                List<VmiRtwLineDefault> rtwLineList = vmiRtwLineDao.findVmiRtwLineByRtwId(rtwId, new BeanPropertyRowMapper<VmiRtwLineDefault>(VmiRtwLineDefault.class));
                for (VmiRtwLineDefault rtwLine : rtwLineList) {
                    Long rtwLineId = rtwLine.getId();
                    VmiRtwLineDefault rtwl = vmiRtwLineDao.getByPrimaryKey(rtwLineId);
                    String upc = rtwl.getUpc();
                    Long lineId = null;
                    for (VmiRtoLineCommand rtoLine : lineList) {
                        String code = rtoLine.getUpc();
                        if (upc.equals(code)) {
                            lineId = rtoLine.getId();
                            rtwl.setLineNo(rtoLine.getLineNo());
                            rtwl.setConsigneeKey(rtoLine.getConsigneeKey());
                            rtwl.setOriginalQty(rtoLine.getOriginalQty());
                            rtwl.setUom(rtoLine.getUom());
                            rtwl.setExtMemo(rtoLine.getExtMemo());
                            vmiRtwLineDao.save(rtwl);
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
                // 更新指令头状态
                if (null == vmiRto || null == vmiRto.getId()) continue;
                vmiRto.setStatus(VmiGeneralStatus.FINISHED);
                vmiRto.setSta(sta);
                vmiRtoDao.save(vmiRto);
            }
            vmiRtwLineDao.flush();
        }
    }

    @Override
    public List<VmiRtwCommand> findPumaNotHasLineNoVmiRtwAllExt(String vmiCode) {
        return vmiRtwDao.findPumaNotHasLineNoVmiRtwAllExt(vmiCode, new BeanPropertyRowMapper<VmiRtwCommand>(VmiRtwCommand.class));
    }

    @Override
    public List<VmiAdjCommand> findVmiAdjAll() {
        return vmiAdjDao.findVmiAdjAll(new BeanPropertyRowMapper<VmiAdjCommand>(VmiAdjCommand.class));
    }

    @Override
    public List<VmiAdjCommand> findVmiAdjAllExt(String vmiCode) {
        return vmiAdjDao.findVmiAdjAllExt(vmiCode, new BeanPropertyRowMapper<VmiAdjCommand>(VmiAdjCommand.class));
    }

    /**
     * 如果收货反馈失败更新vmiErrorCount数量
     */
    @Override
    public void updateVmiErrorCount(String vmiType, Long id) {
        if (vmiType.equals(VmiRsnDefault.vmirsn)) {
            // 修改vmiRsnErrorCount数量
            VmiRsnDefault v = vmiRsnDao.getByPrimaryKey(id);
            v.setErrorCount(v.getErrorCount() + 1);
            v.setFinishTime(new Date());
            vmiRsnDao.save(v);
        }
        if (vmiType.equals(VmiTfxDefault.vmitfx)) {
            // 修改vmiTfxErrorCount数量
            VmiTfxDefault v = vmiTfxDao.getByPrimaryKey(id);
            v.setErrorCount(v.getErrorCount() + 1);
            v.setFinishTime(new Date());
            vmiTfxDao.save(v);
        }
        if (vmiType.equals(VmiRtwDefault.vmirtw)) {
            // 修改vmiRtwErrorCount数量
            VmiRtwDefault v = vmiRtwDao.getByPrimaryKey(id);
            v.setErrorCount(v.getErrorCount() + 1);
            v.setFinishTime(new Date());
            vmiRtwDao.save(v);
        }
        if (vmiType.equals(VmiAdjDefault.vmiadj)) {
            // 修改vmiAdjErrorCount数量
            VmiAdjDefault v = vmiAdjDao.getByPrimaryKey(id);
            v.setErrorCount(v.getErrorCount() + 1);
            v.setFinishTime(new Date());
            vmiAdjDao.save(v);
        }
        if (vmiType.equals(VmiInventoryDefault.vmiinv)) {
            // 修改vmiInventoryErrorCount数量
            VmiInventoryDefault v = vmiInventoryDao.getByPrimaryKey(id);
            v.setErrorCount(v.getErrorCount() + 1);
            v.setFinishTime(new Date());
            vmiInventoryDao.save(v);
            vmiInventoryDao.flush();
        }
    }

    @Override
    public void updateVmiErrorCountExt(String vmiType, List<Long> idList) {
        for (Long id : idList) {
            if (null != id) {
                updateVmiErrorCount(vmiType, id);
            }
        }
    }

    /**
     * 生成UUID
     */
    private String uuidString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 发送asn异常数据
     */
    @Override
    public void vmiAsnErrorEmailInform() {
        List<VmiAsnCommand> vList = vmiAsnDao.findVmiAsnErrorAll(new BeanPropertyRowMapper<VmiAsnCommand>(VmiAsnCommand.class));
        if (vList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            StringBuffer pumaSb = new StringBuffer();
            ChooseOption c = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, "ASN_ERROR");
            if (!StringUtil.isEmpty(c.getOptionValue())) {
                // 传人邮件模板的CODE -- 查询String类型可用的模板
                MailTemplate template = mailTemplateDao.findTemplateByCode("VMI_ASN_NOTICE");
                if (template != null) {
                    boolean isSend = false;
                    sb.append(template.getMailBody() + " \n");
                    for (VmiAsnCommand v : vList) {
                        if ("puma".equals(v.getStoreCode())) {} else {
                            sb.append("VMI_CODE：" + v.getStoreCode() + "   收货单据号：" + v.getOrderCode() + " \n");
                            isSend = true;
                        }
                    }
                    if (isSend) {

                        boolean bool = false;
                        bool = MailUtil.sendMail(template.getSubject(), c.getOptionValue(), "", sb.toString(), false, null);
                        if (bool) {
                            log.debug("邮件通知成功！");
                        } else {
                            log.debug("邮件通知失败,请联系系统管理员！");
                        }
                    }

                } else {
                    log.debug("邮件模板不存在或被禁用！");
                }

            } else {
                log.debug("邮件通知失败,收件人为空！");
            }
            ChooseOption pumaCo = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, "ASN_ERROR_PUMA");
            if (!StringUtil.isEmpty(pumaCo.getOptionValue())) {

                MailTemplate pumaTemplate = mailTemplateDao.findTemplateByCode("VMI_ASN_NOTICE");
                if (pumaTemplate != null) {
                    boolean isSend = false;
                    pumaSb.append(pumaTemplate.getMailBody() + " \n");
                    for (VmiAsnCommand v : vList) {
                        if ("puma".equals(v.getStoreCode())) {
                            pumaSb.append("VMI_CODE：" + v.getStoreCode() + "   收货单据号：" + v.getOrderCode() + " \n");
                            isSend = true;
                        }
                    }
                    if (isSend) {

                        boolean bool2 = false;
                        bool2 = MailUtil.sendMail(pumaTemplate.getSubject(), pumaCo.getOptionValue(), "", pumaSb.toString(), false, null);
                        if (bool2) {
                            log.debug("邮件通知成功！");
                        } else {
                            log.debug("邮件通知失败,请联系系统管理员！");
                        }
                    }
                } else {
                    log.debug("邮件模板不存在或被禁用！");
                }
            }
        }
    }

    /**
     * 发送rsn异常数据
     */
    @Override
    public void vmiRsnErrorEmailInform() {
        List<VmiRsnCommand> vList = vmiRsnDao.findVmiRsnErrorAll(new BeanPropertyRowMapper<VmiRsnCommand>(VmiRsnCommand.class));
        if (vList.size() > 0) {
            ChooseOption c = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, "RSN_ERROR");
            if (!StringUtil.isEmpty(c.getOptionValue())) {
                // 传人邮件模板的CODE -- 查询String类型可用的模板
                MailTemplate template = mailTemplateDao.findTemplateByCode("VMI_RSN_NOTICE");
                if (template != null) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(template.getMailBody() + " \n");
                    for (VmiRsnCommand v : vList) {
                        sb.append("VMI_CODE：" + v.getStoreCode() + "   收货单据号：" + v.getOrderCode() + " \n");
                    }
                    boolean bool = false;
                    bool = MailUtil.sendMail(template.getSubject(), c.getOptionValue(), "", sb.toString(), false, null);
                    if (bool) {
                        // 邮件通知成功 修改对应状态为邮件通知成功，待处理
                        for (VmiRsnCommand v : vList) {
                            VmiRsnDefault vr = vmiRsnDao.getByPrimaryKey(v.getId());
                            vr.setFinishTime(new Date());
                            vr.setStatus(VmiGeneralStatus.EMAILFINISH);
                            vmiRsnDao.save(vr);
                        }
                        log.debug("邮件通知成功！");
                    } else {
                        log.debug("邮件通知失败,请联系系统管理员！");
                    }
                } else {
                    log.debug("邮件模板不存在或被禁用！");
                }
            } else {
                log.debug("邮件通知失败,收件人为空！");
            }
        }
    }

    /**
     * 发送tfx异常数据
     */
    @Override
    public void vmiTfxErrorEmailInform() {
        List<VmiTfxCommand> vList = vmiTfxDao.findVmiTfxErrorAll(new BeanPropertyRowMapper<VmiTfxCommand>(VmiTfxCommand.class));
        if (vList.size() > 0) {
            ChooseOption c = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, "TFX_ERROR");
            if (!StringUtil.isEmpty(c.getOptionValue())) {
                // 传人邮件模板的CODE -- 查询String类型可用的模板
                MailTemplate template = mailTemplateDao.findTemplateByCode("VMI_TFX_NOTICE");
                if (template != null) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(template.getMailBody() + " \n");
                    for (VmiTfxCommand v : vList) {
                        sb.append("VMI_CODE：" + v.getStoreCode() + "   相关单据号：" + v.getOrderCode() + " \n");
                    }
                    boolean bool = false;
                    bool = MailUtil.sendMail(template.getSubject(), c.getOptionValue(), "", sb.toString(), false, null);
                    if (bool) {
                        // 邮件通知成功 修改对应状态为邮件通知成功，待处理
                        for (VmiTfxCommand v : vList) {
                            VmiTfxDefault vt = vmiTfxDao.getByPrimaryKey(v.getId());
                            vt.setFinishTime(new Date());
                            vt.setStatus(VmiGeneralStatus.EMAILFINISH);
                            vmiTfxDao.save(vt);
                        }
                        log.debug("邮件通知成功！");
                    } else {
                        log.debug("邮件通知失败,请联系系统管理员！");
                    }
                } else {
                    log.debug("邮件模板不存在或被禁用！");
                }
            } else {
                log.debug("邮件通知失败,收件人为空！");
            }
        }
    }

    /**
     * 发送adj异常数据
     */
    @Override
    public void vmiAdjErrorEmailInform() {
        List<VmiAdjCommand> vList = vmiAdjDao.findVmiAdjErrorAll(new BeanPropertyRowMapper<VmiAdjCommand>(VmiAdjCommand.class));
        if (vList.size() > 0) {
            ChooseOption c = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, "ADJ_ERROR");
            if (!StringUtil.isEmpty(c.getOptionValue())) {
                // 传人邮件模板的CODE -- 查询String类型可用的模板
                MailTemplate template = mailTemplateDao.findTemplateByCode("VMI_ADJ_NOTICE");
                if (template != null) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(template.getMailBody() + " \n");
                    for (VmiAdjCommand v : vList) {
                        sb.append("VMI_CODE：" + v.getStoreCode() + "   相关单据号：" + v.getOrderCode() + " \n");
                    }
                    boolean bool = false;
                    bool = MailUtil.sendMail(template.getSubject(), c.getOptionValue(), "", sb.toString(), false, null);
                    if (bool) {
                        // 邮件通知成功 修改对应状态为邮件通知成功，待处理
                        for (VmiAdjCommand v : vList) {
                            VmiAdjDefault va = vmiAdjDao.getByPrimaryKey(v.getId());
                            va.setFinishTime(new Date());
                            va.setStatus(VmiGeneralStatus.EMAILFINISH);
                            vmiAdjDao.save(va);
                        }
                        log.debug("邮件通知成功！");
                    } else {
                        log.debug("邮件通知失败,请联系系统管理员！");
                    }
                } else {
                    log.debug("邮件模板不存在或被禁用！");
                }
            } else {
                log.debug("邮件通知失败,收件人为空！");
            }
        }
    }

    /**
     * 发送rtw异常数据
     */
    @Override
    public void vmiRtwErrorEmailInform() {
        List<VmiRtwCommand> vList = vmiRtwDao.findVmiRtwErrorAll(new BeanPropertyRowMapper<VmiRtwCommand>(VmiRtwCommand.class));
        if (vList.size() > 0) {
            ChooseOption c = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, "RTW_ERROR");
            if (!StringUtil.isEmpty(c.getOptionValue())) {
                // 传人邮件模板的CODE -- 查询String类型可用的模板
                MailTemplate template = mailTemplateDao.findTemplateByCode("VMI_RTW_NOTICE");
                if (template != null) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(template.getMailBody() + " \n");
                    for (VmiRtwCommand v : vList) {
                        sb.append("VMI_CODE：" + v.getStoreCode() + "   相关单据号：" + v.getOrderCode() + " \n");
                    }
                    boolean bool = false;
                    bool = MailUtil.sendMail(template.getSubject(), c.getOptionValue(), "", sb.toString(), false, null);
                    if (bool) {
                        // 邮件通知成功 修改对应状态为邮件通知成功，待处理
                        for (VmiRtwCommand v : vList) {
                            VmiRtwDefault va = vmiRtwDao.getByPrimaryKey(v.getId());
                            va.setFinishTime(new Date());
                            va.setStatus(VmiGeneralStatus.EMAILFINISH);
                            vmiRtwDao.save(va);
                        }
                        log.debug("邮件通知成功！");
                    } else {
                        log.debug("邮件通知失败,请联系系统管理员！");
                    }
                } else {
                    log.debug("邮件模板不存在或被禁用！");
                }
            } else {
                log.debug("邮件通知失败,收件人为空！");
            }
        }
    }

    /** 
     *
     */
    @Override
    public void insertTotalInvPumaExt(Date date, String batchNo, String vmiCode) {
        vmiInventoryDao.insertTotalInvExt(date, batchNo, vmiCode);
    }

    /** 
     *
     */
    @Override
    public void uploadTotalInvToHubPumaExt(Date date, String batchNo, String vmiCode) {
        boolean isSuccess = false;
        for (int i = 0; i <= 6; i++) {
            if (true == isSuccess) {
                break;
            }
            List<VmiInventory> sycLists = new ArrayList<VmiInventory>();
            List<VmiInventory> sycList = new ArrayList<VmiInventory>();
            List<VmiInventoryCommand> viList = vmiInventoryDao.findTotalInvAllExt(date, null, vmiCode, new BeanPropertyRowMapper<VmiInventoryCommand>(VmiInventoryCommand.class));
            for (VmiInventoryCommand vi : viList) {
                if (vi.getErrorCount() > 5) {
                    VmiInventoryDefault vid = vmiInventoryDao.getByPrimaryKey(vi.getId());
                    vid.setStatus(VmiGeneralStatus.FAILED);
                    vid.setFinishTime(new Date());
                    vmiInventoryDao.save(vid);
                } else {
                    VmiInventory inv = new VmiInventory();
                    inv.setUuid(uuidString());
                    inv.setBatchNo(vi.getBatchNo());
                    inv.setBlockQty(vi.getBlockQty());
                    inv.setCreateTime(vi.getCreateTime());
                    inv.setExtMemo(vi.getExtMemo());
                    inv.setInvStatus(vi.getInvStatus());
                    inv.setQty(vi.getQty());
                    inv.setStoreCode(vi.getStoreCode());
                    inv.setUpc(vi.getUpc());
                    inv.setVmiSource(vi.getVmiSource());
                    inv.setWhCode(vi.getWhCode());
                    sycLists.add(inv);
                }
            }
            for (VmiInventory v : sycLists) {
                String upc = v.getUpc();
                Long qty = v.getQty();
                Long blockQty = v.getBlockQty();
                String whCode = (StringUtil.isEmpty(v.getWhCode()) ? "" : v.getWhCode());
                boolean isExist = false;
                if (sycList.size() > 0) {
                    Iterator<VmiInventory> iter = sycList.listIterator();
                    VmiInventory inv = null;
                    while (iter.hasNext()) {
                        inv = iter.next();
                        String sku = inv.getUpc();
                        String ou = inv.getWhCode();
                        if (!StringUtil.isEmpty(sku) && sku.equals(upc) && whCode.equals(ou)) {
                            inv.setQty((null != inv.getQty() ? inv.getQty() : 0L) + qty);
                            inv.setBlockQty((null != inv.getBlockQty() ? inv.getBlockQty() : 0L) + blockQty);
                            isExist = true;
                            break;
                        }
                    }
                }
                if (false == isExist) {
                    sycList.add(v);
                }
            }
            if (sycList.size() > 0) {
                try {
                    WmsResponse w = hubService.pumaSoh(sycList);
                    if (w.getStatus() == WmsResponse.STATUS_SUCCESS) {
                        isSuccess = true;
                        // 成功更新状态10
                        for (VmiInventoryCommand vi : viList) {
                            if (null != vi.getId()) {
                                VmiInventoryDefault vid = vmiInventoryDao.getByPrimaryKey(vi.getId());
                                vid.setStatus(VmiGeneralStatus.FINISHED);
                                vid.setFinishTime(new Date());
                                vmiInventoryDao.save(vid);
                            }
                        }
                    }
                    if (w.getStatus() == WmsResponse.STATUS_ERROR) {
                        log.debug("VmiDefault uploadVmiInventoryToHub to Hub Error msg:" + w.getMsg());
                        for (VmiInventoryCommand vi : viList) {
                            if (null != vi.getId()) {
                                updateVmiErrorCount(VmiInventoryDefault.vmiinv, vi.getId());// 修改error次数
                            }
                        }
                        Thread.sleep(120000L);
                    }
                } catch (Exception e) {
                    log.error("VmiDefault uploadVmiInventoryToHub to Hub Exception");
                    for (VmiInventoryCommand vi : viList) {
                        if (null != vi.getId()) {
                            updateVmiErrorCount(VmiInventoryDefault.vmiinv, vi.getId());// 修改error次数
                        }
                    }
                    try {
                        Thread.sleep(120000L);
                    } catch (InterruptedException e1) {}
                }
            } else {
                isSuccess = true;
            }
        }
    }

    /**
     * 出库信息同步HUB
     */
    @Override
    public void uploadVmiOutBoundToHub(String defaultCode) {
        List<String> vmiCodeList = biChannelDao.findBiChannelByDefaultCode(defaultCode, new SingleColumnRowMapper<String>(String.class));
        List<VmiOutBoundCommand> obList = vmiOutBoundDao.findVmiOutBoundByVmiCode(vmiCodeList, new BeanPropertyRowMapper<VmiOutBoundCommand>(VmiOutBoundCommand.class));
        List<VmiOutBound> outBoundList = new ArrayList<VmiOutBound>();
        for (VmiOutBoundCommand ob : obList) {
            VmiOutBound o = new VmiOutBound();
            VmiOutBoundLine[] obLines = new VmiOutBoundLine[] {};
            List<VmiOutBoundLine> obLineList = new ArrayList<VmiOutBoundLine>();
            List<VmiOutBoundLineDefault> oboundLineList = vmiOutBoundLineDao.findVmiOutBoundLineByObid(ob.getId(), new BeanPropertyRowMapper<VmiOutBoundLineDefault>(VmiOutBoundLineDefault.class));
            o.setUuid(uuidString());
            o.setCustomerCode(ob.getCustomerCode());
            o.setCustomerRef(ob.getCustomerRef());
            o.setExtMemo(ob.getExtMemo());
            o.setOrderCode(ob.getOrderCode());
            o.setOrderTime(ob.getOrderTime());
            o.setOutBoundTime(ob.getOutBoundTime());
            o.setStoreCode(ob.getStoreCode());
            for (VmiOutBoundLineDefault vob : oboundLineList) {
                VmiOutBoundLine v = new VmiOutBoundLine();
                v.setQty(vob.getQty());
                v.setUpc(vob.getUpc());
                v.setExtMemo(vob.getExtMemo());
                obLineList.add(v);
            }
            VmiOutBoundLine[] objArray = obLineList.toArray(obLines);
            o.setVmiOutBoundLine(objArray);
            outBoundList.add(o);
        }
        if (outBoundList.size() > 0) {
            boolean check = uploadVmiInOutBoundToHub(outBoundList, null, outBound);
            if (check) {
                // 成功
                updateVmiInOutBoundStatus(obList, new ArrayList<VmiInBoundCommand>(), VmiGeneralStatus.FINISHED);
            }
        }
    }

    /**
     * 退换货入信息同步HUB
     */
    @Override
    public void uploadVmiInBoundToHub(String defaultCode) {
        List<String> vmiCodeList = biChannelDao.findBiChannelByDefaultCode(defaultCode, new SingleColumnRowMapper<String>(String.class));
        List<VmiInBoundCommand> ibList = vmiInBoundDao.findVmiInBoundByVmiCode(vmiCodeList, new BeanPropertyRowMapper<VmiInBoundCommand>(VmiInBoundCommand.class));
        List<VmiInBound> inBoundList = new ArrayList<VmiInBound>();
        for (VmiInBoundCommand ib : ibList) {
            VmiInBound i = new VmiInBound();
            VmiInBoundLine[] ibLines = new VmiInBoundLine[] {};
            List<VmiInBoundLine> ibLineList = new ArrayList<VmiInBoundLine>();
            List<VmiInBoundLineDefault> iboundLineList = vmiInBoundLineDao.findVmiInBoundLineByIbid(ib.getId(), new BeanPropertyRowMapper<VmiInBoundLineDefault>(VmiInBoundLineDefault.class));
            i.setUuid(uuidString());
            i.setCustomerCode(ib.getCustomerCode());
            i.setCustomerRef(ib.getCustomerRef());
            i.setExtMemo(ib.getExtMemo());
            i.setOrderCode(ib.getOrderCode());
            i.setOrderTime(ib.getOrderTime());
            i.setInBoundTime(ib.getInBoundTime());
            i.setStoreCode(ib.getStoreCode());
            for (VmiInBoundLineDefault vib : iboundLineList) {
                VmiInBoundLine v = new VmiInBoundLine();
                v.setQty(vib.getQty());
                v.setUpc(vib.getUpc());
                v.setExtMemo(vib.getExtMemo());
                ibLineList.add(v);
            }
            VmiInBoundLine[] objArray = ibLineList.toArray(ibLines);
            i.setVmiInBoundLine(objArray);
            inBoundList.add(i);
        }
        if (inBoundList.size() > 0) {
            boolean check = uploadVmiInOutBoundToHub(null, inBoundList, inBound);
            if (check) {
                // 成功
                updateVmiInOutBoundStatus(new ArrayList<VmiOutBoundCommand>(), ibList, VmiGeneralStatus.FINISHED);
            }
        }
    }

    public boolean uploadVmiInOutBoundToHub(List<VmiOutBound> oblist, List<VmiInBound> iblist, String type) {
        boolean success = false;
        // 调用6次
        for (int i = 0; i <= 6; i++) {
            if (success) {
                break;
            }
            try {
                WmsResponse w = null;
                if (type.equals(outBound)) {
                    w = hubService.transferVmiOutBound(oblist);
                }
                if (type.equals(inBound)) {
                    w = hubService.transferVmiInBound(iblist);
                }
                if (w.getStatus() == WmsResponse.STATUS_SUCCESS) {
                    success = true;
                } else {
                    success = false;
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return success;
    }

    /**
     * 修改出库/退换货入反馈状态
     * 
     * @param list
     * @param status
     */
    public void updateVmiInOutBoundStatus(List<VmiOutBoundCommand> oblist, List<VmiInBoundCommand> iblist, VmiGeneralStatus status) {
        for (VmiOutBoundCommand ob : oblist) {
            VmiOutBoundDefault o = vmiOutBoundDao.getByPrimaryKey(ob.getId());
            o.setFinishTime(new Date());
            o.setStatus(status);
            vmiOutBoundDao.save(o);
        }
        for (VmiInBoundCommand ib : iblist) {
            VmiInBoundDefault i = vmiInBoundDao.getByPrimaryKey(ib.getId());
            i.setFinishTime(new Date());
            i.setStatus(status);
            vmiInBoundDao.save(i);
        }
    }

    /**
     * 强生非销售出库的数据给HUB
     */
    @Override
    public void pushNotSalesOutboundDataToHub(String vmiCode) {
        BiChannelCommand b = biChannelDao.getBiChannelByDefaultCode(vmiCode, new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        List<String> biChannelCodeList = vmiInventoryManager.getBiChannelByVmiCode(b.getVmiCode());
        List<StockTransTxLogCommand> logList = stockTransTxLogDao.findLogPushOutboundData(biChannelCodeList, new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
        List<VmiOutBound> outList = new ArrayList<VmiOutBound>();
        for (StockTransTxLogCommand comm : logList) {
            VmiOutBoundLine line = new VmiOutBoundLine();
            VmiOutBoundLine[] obLines = new VmiOutBoundLine[] {};
            List<VmiOutBoundLine> lineList = new ArrayList<VmiOutBoundLine>();
            VmiOutBound vmiout = new VmiOutBound();
            vmiout.setOrderCode(comm.getStaCode());
            vmiout.setStoreCode(comm.getStoreCode());
            vmiout.setInvStatus(comm.getInvStatus());
            vmiout.setBatchCode(comm.getBatchCode());
            vmiout.setWhCode(comm.getWhouCode());
            line.setQty(comm.getQuantity());
            line.setUpc(comm.getExtensionCode2());
            vmiout.setOutBoundTime(comm.getStaOutboundTime());
            lineList.add(line);
            VmiOutBoundLine[] arr = lineList.toArray(obLines);
            vmiout.setVmiOutBoundLine(arr);
            outList.add(vmiout);
        }
        if (outList.size() > 0) {
            uploadVmiInOutBoundToHub(outList, null, outBound);
        }

    }

    /**
     * 强生非vmi入库和非退换货入库的数据给HUB
     */
    @Override
    public void pushNotSalesInboundDataToHub(String vmiCode) {
        BiChannelCommand b = biChannelDao.getBiChannelByDefaultCode(vmiCode, new BeanPropertyRowMapper<BiChannelCommand>(BiChannelCommand.class));
        List<String> biChannelCodeList = vmiInventoryManager.getBiChannelByVmiCode(b.getVmiCode());
        List<StockTransTxLogCommand> logList = stockTransTxLogDao.findLogPushInboundData(biChannelCodeList, new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
        List<VmiInBound> inList = new ArrayList<VmiInBound>();
        for (StockTransTxLogCommand lgs : logList) {
            VmiInBound vmiIn = new VmiInBound();
            VmiInBoundLine line = new VmiInBoundLine();
            List<VmiInBoundLine> list = new ArrayList<VmiInBoundLine>();
            VmiInBoundLine[] ibLines = new VmiInBoundLine[] {};
            vmiIn.setOrderCode(lgs.getStaCode());
            vmiIn.setInvStatus(lgs.getInvStatus());
            vmiIn.setInBoundTime(lgs.getStaInboundTime());
            vmiIn.setStoreCode(lgs.getStoreCode());
            vmiIn.setWhCode(lgs.getWhouCode());
            line.setQty(lgs.getQuantity());
            line.setUpc(lgs.getExtensionCode2());
            list.add(line);
            VmiInBoundLine[] arr = list.toArray(ibLines);
            vmiIn.setBatchCode(lgs.getBatchCode());
            vmiIn.setVmiInBoundLine(arr);
            inList.add(vmiIn);
        }
        if (inList.size() > 0) {
            uploadVmiInOutBoundToHub(null, inList, inBound);
        }

    }

    @Override
    public void uploadVmiAdjToHubSpeedoExt(VmiAdjCommand vr) {
        if (vr.getErrorCount() > 5) {
            // 失败5次发送失败邮件
            VmiAdjDefault vad = vmiAdjDao.getByPrimaryKey(vr.getId());
            vad.setStatus(VmiGeneralStatus.FAILED);
            vad.setFinishTime(new Date());
            vmiAdjDao.save(vad);
        } else {
            VmiAdj v = new VmiAdj();
            VmiAdjLine[] adjLines = new VmiAdjLine[] {};
            List<VmiAdjLine> vmiList = new ArrayList<VmiAdjLine>();
            List<VmiAdjLineDefault> vald = vmiAdjLineDao.findVmiAdjLineByAdjId(vr.getId(), new BeanPropertyRowMapper<VmiAdjLineDefault>(VmiAdjLineDefault.class));
            v.setUuid(uuidString());
            v.setStoreCode(vr.getStoreCode());
            v.setOrderCode(vr.getOrderCode());
            v.setAdjDate(vr.getAdjDate());
            v.setWhCode(vr.getWhCode());
            v.setExtMemo(vr.getExtMemo());
            v.setAdjReason(vr.getAdjReason());
            List<SkuCommand> skulist = skudao.findSkuStatusByorderCode(vr.getInvckId().toString(), new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
            for (VmiAdjLineDefault vList : vald) {
                for (SkuCommand skuCommand : skulist) {
                    if (skuCommand.getDpProp1().equals(vList.getUpc())) {
                        InventoryStatus status = inventoryStatusDao.findStatusNameByid(skuCommand.getInvStatus(), new BeanPropertyRowMapper<InventoryStatus>(InventoryStatus.class));
                        VmiAdjLine val = new VmiAdjLine();
                        // 可卖品到可卖品(盘盈)
                        if (status.getName().equals("良品") && vList.getQty() > 0) {
                            // 宝尊系统初始库存状态
                            val.setFromStatus("ATS");
                            // 宝尊系统调整后的库存状态
                            val.setToStatus("ATS");
                            // 映射到speedo系统中的状态
                            val.setTransactionType("INCREASE_STOCK");
                            // 不可卖品到不可卖品&残次品到残次品
                        } else if (status.getName().equals("良品不可销售") && vList.getQty() > 0) {
                            val.setFromStatus("NATS");
                            val.setToStatus("NATS");
                            val.setTransactionType("INCREASE_BLOCK");
                            // 残次到残次，数量增加
                        } else if (!status.getName().equals("良品") && !status.getName().equals("良品不可销售") && vList.getQty() > 0) {
                            val.setFromStatus("DAMAGES");
                            val.setToStatus("DAMAGES");
                            val.setTransactionType("INCREASE_BLOCK");
                        }
                        // 可卖品到可卖品(盘亏)
                        else if (status.getName().equals("良品") && vList.getQty() < 0) {
                            val.setFromStatus("ATS");
                            val.setToStatus("ATS");
                            val.setTransactionType("REDUCE_STOCK");
                            // 不可卖品到不可卖品&残次品到残次品
                        } else if (status.getName().equals("良品不可销售") && vList.getQty() < 0) {
                            val.setFromStatus("NATS");
                            val.setToStatus("NATS");
                            val.setTransactionType("REDUCE_BLOCK");
                            // 残次到残次，数量减少
                        } else if (!status.getName().equals("良品") && !status.getName().equals("良品不可销售") && vList.getQty() < 0) {
                            val.setFromStatus("DAMAGES");
                            val.setToStatus("DAMAGES");
                            val.setTransactionType("REDUCE_BLOCK");
                        }
                        val.setUpc(vList.getUpc());
                        val.setQty(Math.abs(vList.getQty()));
                        val.setExtMemo(vList.getExtMemo());
                        val.setInvStatus(vList.getInvStatus());
                        vmiList.add(val);
                    }
                }
            }
            VmiAdjLine[] objArray = vmiList.toArray(adjLines);
            v.setAdjLines(objArray);
            WmsResponse w = hubService.inventoryAdjustment(v);
            if (w.getStatus() == WmsResponse.STATUS_SUCCESS) {
                // 成功更新状态10
                VmiAdjDefault vmiAdjDefault = vmiAdjDao.getByPrimaryKey(vr.getId());
                vmiAdjDefault.setStatus(VmiGeneralStatus.FINISHED);
                vmiAdjDefault.setFinishTime(new Date());
                vmiAdjDao.save(vmiAdjDefault);
            }
            if (w.getStatus() == WmsResponse.STATUS_ERROR) {
                log.debug("VmiDefault uploadVmiAdjToHubSpeedoExt to Hub Error msg:" + w.getMsg());
                updateVmiErrorCount(VmiAdjDefault.vmiadj, vr.getId());// 修改error次数
            }
        }
    }

    @Override
    public void uploadStatusAdjToHubSpeedoExt(VmiStatusAdjCommand sa) {
        VmiAdj v = new VmiAdj();
        boolean b = true;
        VmiAdjLine[] adjLines = new VmiAdjLine[] {};
        List<VmiAdjLine> vmiList = new ArrayList<VmiAdjLine>();
        List<VmiStatusAdjLineDefault> vald = vmiStatusAdjLineDao.findVmiStatusAdjLineByAdjId(sa.getId(), new BeanPropertyRowMapper<VmiStatusAdjLineDefault>(VmiStatusAdjLineDefault.class));
        v.setUuid(uuidString());
        v.setStoreCode(sa.getStoreCode());
        v.setOrderCode(sa.getOrderCode());
        v.setAdjDate(sa.getAdjDate());
        v.setWhCode(sa.getWhCode());
        v.setExtMemo(sa.getExtMemo());
        v.setAdjReason(sa.getAdjReason());
        for (VmiStatusAdjLineDefault vList : vald) {
            VmiAdjLine val = new VmiAdjLine();
            InventoryStatus fromstatus = inventoryStatusDao.findStatusNameByid(vList.getFromStatus(), new BeanPropertyRowMapper<InventoryStatus>(InventoryStatus.class));
            InventoryStatus tostatus = inventoryStatusDao.findStatusNameByid(vList.getToStatus(), new BeanPropertyRowMapper<InventoryStatus>(InventoryStatus.class));
            if (("良品".equals(fromstatus.getName()) && "良品不可销售".equals(tostatus.getName())) || ("良品不可销售".equals(fromstatus.getName()) && "良品".equals(tostatus.getName()))
                    || ("良品".equals(fromstatus.getName()) && (!"良品".equals(tostatus.getName()) && !"良品不可销售".equals(tostatus.getName())))) {
                val.setUpc(vList.getUpc());
                val.setQty(Math.abs(vList.getQty()));
                val.setExtMemo(vList.getExtMemo());
                // val.setInvStatus(vList.getInvStatus());
                // 宝尊系统初始库存库存状态
                if ("良品".equals(fromstatus.getName())) {
                    val.setFromStatus("ATS");
                } else if ("良品不可销售".equals(fromstatus.getName())) {
                    val.setFromStatus("NATS");
                } else if (!"良品".equals(fromstatus.getName()) && !"良品不可销售".equals(fromstatus.getName())) {
                    val.setFromStatus("DAMAGES");
                }
                // 宝尊系统调整后的库存状态
                // val.setToStatus(tostatus.getName());
                if ("良品".equals(tostatus.getName())) {
                    val.setToStatus("ATS");
                } else if ("良品不可销售".equals(tostatus.getName())) {
                    val.setToStatus("NATS");
                } else if (!"良品".equals(tostatus.getName()) && !"良品不可销售".equals(tostatus.getName())) {
                    val.setToStatus("DAMAGES");
                }
                val.setTransactionType("");
                vmiList.add(val);
            } else {
                b = false;
                VmiStatusAdjDefault adjDefault = vmiStatusAdjDao.getByPrimaryKey(sa.getId());
                // 不需要反馈的数据，状态改为33
                adjDefault.setStatus(VmiGeneralStatus.NO_FEEDBACK_INTERFACE);
                vmiStatusAdjDao.save(adjDefault);

            }
        }
        if (b) {
            VmiAdjLine[] objArray = vmiList.toArray(adjLines);
            v.setAdjLines(objArray);

            WmsResponse w = hubService.inventoryAdjustment(v);

            if (w.getStatus() == WmsResponse.STATUS_SUCCESS) {
                // 成功更新状态10
                VmiStatusAdjDefault vmiAdjDefault = vmiStatusAdjDao.getByPrimaryKey(sa.getId());
                vmiAdjDefault.setStatus(VmiGeneralStatus.FINISHED);
                vmiAdjDefault.setFinishTime(new Date());
                vmiStatusAdjDao.save(vmiAdjDefault);
            }
            if (w.getStatus() == WmsResponse.STATUS_ERROR) {
                log.debug("VmiDefault uploadVmiAdjToHubSpeedoExt to Hub Error msg:" + w.getMsg());
                updateVmiErrorCount(VmiStatusAdjDefault.vmiadj, sa.getId());// 修改error次数
            }
        }
    }

    @Override
    public List<StockTransTxLogCommand> findAdjLog(String staCode, int direction) {
        return stockTransTxLogDao.findLogListByOwner(direction, staCode, new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
    }

    public void summaryInventoryToEmail() {
        List<SkuCommand> skuList = skuDao.summaryInventoryToEmail(new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
        if (null != skuList && skuList.size() > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append("WMS3.0系统出现SKU库存为负数据列表:\n");
            sb.append("==========================================================\n");
            for (SkuCommand sku : skuList) {
                sb.append("仓库:   " + sku.getAuName() + "  店铺:    " + sku.getOwner() + " sku编码:    " + sku.getCode() + " barCode:    " + sku.getBarCode() + " 库存       " + sku.getSalesQty() + "\n");

            }
            sb.append("==========================================================\n");
            sb.append("WMS3.0系统出现SKU库存为负订单数据列表:\n");
            for (SkuCommand sku : skuList) {
                Long count = Math.abs(sku.getSalesQty());
                // 退货出
                List<StockTransApplication> staList = staDao.findSlipCodeBySkuOuIdOwner(sku.getSkuId(), sku.getOuId(), sku.getOwnerCode(), 42, new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
                if (null != staList && staList.size() > 0) {
                    for (StockTransApplication sta : staList) {
                        count = count - sta.getSkuQty();
                        sb.append("订单号:      " + sta.getCode() + "  店铺:     " + sku.getOwner() + " sku编码:    " + sku.getCode() + " barCode:    " + sku.getBarCode() + " sku数量   " + sta.getSkuQty() + " \n");
                        if (count <= 0) {
                            break;
                        }
                    }
                }
                if (count > 0) {
                    List<StockTransApplication> stoList = staDao.findSlipCodeBySkuOuIdOwner(sku.getSkuId(), sku.getOuId(), sku.getOwnerCode(), 21, new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
                    if (null != stoList && stoList.size() > 0) {
                        for (StockTransApplication sta : stoList) {
                            count = count - sta.getSkuQty();
                            sb.append("订单号:       " + sta.getCode() + "  店铺:    " + sku.getOwner() + " sku编码:    " + sku.getCode() + " barCode:    " + sku.getBarCode() + " sku数量      " + sta.getSkuQty() + " \n");
                            if (count <= 0) {
                                break;
                            }
                        }
                    }
                }
            }
            if (null != sb && sb.length() > 0) {
                ChooseOption c = chooseOptionDao.findByCategoryCodeAndKey(categoryCode, "Inventory");
                if (!StringUtil.isEmpty(c.getOptionValue())) {
                    // 传人邮件模板的CODE -- 查询String类型可用的模板
                    MailTemplate template = mailTemplateDao.findTemplateByCode("summaryInventory");
                    if (template != null) {
                        boolean bool = false;
                        bool = MailUtil.sendMail(template.getSubject(), c.getOptionValue(), "", sb.toString(), false, null);
                        if (!bool) {
                            log.error("summaryInventoryToEmailError");
                        }
                    } else {
                        log.debug("邮件模板不存在或被禁用!");
                    }
                } else {
                    log.debug("邮件通知失败,收件人为空!");
                }
            }
        }
    }
}
