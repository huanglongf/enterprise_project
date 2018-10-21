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
package com.jumbo.wms.manager.channel;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.ConstantsFtpConifg;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.BiChannelSpecialActionDao;
import com.jumbo.dao.baseinfo.ChannelWhRefLogDao;
import com.jumbo.dao.baseinfo.ChannelWhRefRefDao;
import com.jumbo.dao.baseinfo.CustomerDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.TransSfInfoDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.commandMapper.PackingObjRowMapper;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.task.CommonConfigDao;
import com.jumbo.dao.warehouse.ApplyInvoiceRequestCommandDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.BiChannelGroupDao;
import com.jumbo.dao.warehouse.BiChannelImperfectDao;
import com.jumbo.dao.warehouse.BiChannelImperfectLineDao;
import com.jumbo.dao.warehouse.BiChannelLogDao;
import com.jumbo.dao.warehouse.ImperfectStvDao;
import com.jumbo.dao.warehouse.ImperfectStvLogDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.SfCloudWarehouseConfigDao;
import com.jumbo.dao.warehouse.SkuImperfectDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StaSpecialExecutedDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.util.Md5Util;
import com.jumbo.util.QrCodeUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.StringUtils;
import com.jumbo.util.TimeHashMap;
import com.jumbo.util.qrcode.InvoiceUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.pda.PdaReceiveManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.BiChannelLimitType;
import com.jumbo.wms.model.baseinfo.BiChannelLog;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialAction;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialActionType;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.baseinfo.ChannelWhRefLog;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.TransSfInfo;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.OperationUnitCommand;
import com.jumbo.wms.model.command.StaSpecialExecutedCommand;
import com.jumbo.wms.model.command.WarehouseCommand;
import com.jumbo.wms.model.jasperReport.PackingLineObj;
import com.jumbo.wms.model.jasperReport.PackingObj;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.task.CommonConfig;
import com.jumbo.wms.model.warehouse.ApplyInvoiceRequest;
import com.jumbo.wms.model.warehouse.ApplyInvoiceRequestCommand;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfect;
import com.jumbo.wms.model.warehouse.BiChannelImperfectCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfectLine;
import com.jumbo.wms.model.warehouse.BiChannelImperfectLineCommand;
import com.jumbo.wms.model.warehouse.BiChannelRef;
import com.jumbo.wms.model.warehouse.BiChannelSpecialActionCommand;
import com.jumbo.wms.model.warehouse.ChannelWhRefCommand;
import com.jumbo.wms.model.warehouse.ImperfectStv;
import com.jumbo.wms.model.warehouse.ImperfectStvCommand;
import com.jumbo.wms.model.warehouse.ImperfectStvLog;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.SfCloudWarehouseConfigCommand;
import com.jumbo.wms.model.warehouse.SkuImperfect;
import com.jumbo.wms.model.warehouse.StaInvoiceCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StaSpecialExecuteType;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONObject;

@Transactional
@Service("channelManager")
public class ChannelManagerImpl extends BaseManagerImpl implements ChannelManager {

    private static final long serialVersionUID = -1598252586812598808L;

    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private BiChannelSpecialActionDao biChannelSpecialActionDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ChannelWhRefRefDao cwrrDao;
    @Autowired
    private BiChannelGroupDao bgDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private InventoryCheckDao invCheckDao;
    @Autowired
    private StaInvoiceDao staInvoiceDao;
    @Autowired
    private BiChannelLogDao biChannelLogDao;
    @Autowired
    private BiChannelImperfectDao imperfectDao;
    @Autowired
    private BiChannelImperfectLineDao imperfectLineDao;
    @Autowired
    private ImperfectStvDao imperfectStvDao;
    @Autowired
    private ImperfectStvLogDao imperfectStvLogDao;
    @Autowired
    private InventoryStatusDao statusDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private SkuImperfectDao skuImperfectDao;
    @Autowired
    private ChannelWhRefLogDao channelWhRefLogDao;
    @Resource(name = "addSkuImperfect")
    private ExcelReader userWarehouseReader;
    @Resource(name = "addSkuImperfectWhy")
    private ExcelReader addSkuImperfectWhy;
    @Autowired
    private BiChannelImperfectDao biChannelImperfectDao;
    @Autowired
    private BiChannelImperfectLineDao biChannelImperfectLineDao;
    @Autowired
    private PdaReceiveManager pdaReceiveManager;
    @Autowired
    private SfCloudWarehouseConfigDao sfCloudWarehouseConfigDao;
    @Autowired
    private TransSfInfoDao transSfInfoDao;
    @Autowired
    private StaSpecialExecutedDao staSpecialExecutedDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private CommonConfigDao commonConfigDao;
    @Value("${ralphLauren.url}")
    private String ralphLaurenPath;
    @Autowired
    private ApplyInvoiceRequestCommandDao applyInvoiceRequestDao;

    TimeHashMap<String, ChooseOption> chooseOptionCache = new TimeHashMap<String, ChooseOption>();


    /**
     * 通过公司组织ID获取所有公司下关联渠道信息
     * 
     * @param comouid
     * @return
     */
    public List<BiChannel> getAllWhRefChannelByCmpouId(Long comouid) {
        List<BiChannel> channelList = new ArrayList<BiChannel>();
        List<OperationUnit> whoulist = operationUnitDao.getAllWhByCmpOuId(comouid);
        for (OperationUnit whou : whoulist) {
            channelList.addAll(biChannelDao.getAllRefShopByWhOuId(whou.getId()));
        }
        return channelList;
    }

    /**
     * find all channel in the customer by wh ou id
     * 
     * @param whouid
     * @return
     */
    public List<BiChannel> findtAllChannelByWhouid(Long whouid) {
        Warehouse wh = warehouseDao.getByOuId(whouid);
        if (wh != null) {
            if (wh.getCustomer() != null) {
                return biChannelDao.findAllByCustomer(wh.getCustomer().getId());
            }
            // else {
            // return biChannelDao.findAllByCustomer(null);
            // }
        }
        return biChannelDao.findAllByCustomer(null);

    }

    public List<BiChannel> findAllChannelByWhouids(Long startWarehouseId, Long endWarehouseId) {
        Warehouse startWh = null;
        if (startWarehouseId != null) {
            startWh = warehouseDao.getByOuId(startWarehouseId);
        }
        Warehouse endWh = null;
        if (endWarehouseId != null) {
            endWh = warehouseDao.getByOuId(endWarehouseId);
        }
        List<BiChannel> bclist = null;
        if (startWh != null && endWh != null) {
            if (startWh.getCustomer() != null && endWh.getCustomer() != null) {
                bclist = biChannelDao.findAllByCustomers(startWarehouseId, endWarehouseId);
            } else if (startWh.getCustomer() == null && endWh.getCustomer() != null) {
                bclist = biChannelDao.findAllByCustomers(null, endWh.getCustomer().getId());
            } else if (startWh.getCustomer() != null && endWh.getCustomer() == null) {
                bclist = biChannelDao.findAllByCustomers(startWh.getCustomer().getId(), null);
            } else {
                bclist = biChannelDao.findAllByCustomers(null, null);
            }
        } else if (startWh == null && endWh != null) {
            bclist = biChannelDao.findAllByCustomers(null, endWh.getCustomer().getId());
        } else if (startWh != null && endWh == null) {
            bclist = biChannelDao.findAllByCustomers(startWh.getCustomer().getId(), null);
        }
        return bclist;
    }

    /**
     * 根据不同的组织类型查询渠道信息
     * 
     * @param start
     * @param pagesize
     * @param type
     * @param shopName
     * @param ouid
     * @param sorts
     * @return
     */
    public Pagination<BiChannel> findBiChannelByOuid(int start, int pagesize, String ouTypeName, String shopName, Long ouid, Sort[] sorts) {
        if (OperationUnitType.OUTYPE_COMPANY.equals(ouTypeName)) {
            // 公司
            return biChannelDao.findRefShopByCmpOuId(start, pagesize, ouid, shopName, new BeanPropertyRowMapperExt<BiChannel>(BiChannel.class), sorts);
        } else if (OperationUnitType.OUTYPE_OPERATION_CENTER.equals(ouTypeName)) {
            // 运营中心
            return biChannelDao.findRefShopByOcOuId(start, pagesize, ouid, shopName, new BeanPropertyRowMapperExt<BiChannel>(BiChannel.class), sorts);
        } else if (OperationUnitType.OUTYPE_WAREHOUSE.equals(ouTypeName)) {
            // 仓库
            return biChannelDao.findRefShopByWhOuId(start, pagesize, ouid, shopName, new BeanPropertyRowMapperExt<BiChannel>(BiChannel.class), sorts);
        } else
            return null;
    }

    public Pagination<BiChannel> findBiChannelByPage(int start, int pagesize, String shopName, Sort[] sorts) {
        if (shopName == null) {
            return biChannelDao.getBiChannelListByPage(start, pagesize, sorts);
        } else {
            return biChannelDao.getBiChannelListByPageAndShopName(start, pagesize, "%" + shopName + "%", sorts);
        }
    }

    public String findPickingListTemplateType(Long plid, Long staid) {
        String templateType = biChannelSpecialActionDao.findTemplateType(plid, staid, new SingleColumnRowMapper<String>(String.class));
        return templateType;

    }

    public List<StockTransApplicationCommand> findPickingList(Long plid, Long staId) {
        List<StockTransApplicationCommand> stas = staDao.findByplIdAndStaId(plid, staId, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
        return stas;
    }


    public boolean isPostpositionPackingPage(Long plid, Long staid, boolean isPostPrint) {
        PickingList pl = null;
        if (plid != null) {
            // 增加打印次数
            pl = pickingListDao.getByPrimaryKey(plid);
            if (pl != null && staid == null) {
                pl.setOutputCount(pl.getOutputCount() == null ? 1 : pl.getOutputCount() + 1);
                pickingListDao.flush();
            }
        }
        if (pl != null && pl.getIsPostpositionPackingPage() != null) {
            if (pl.getIsPostpositionPackingPage() && isPostPrint) {
                return true;
            }
        }
        return false;
    }

    public Map<Long, PackingObj> findPackingPageNoLocation(Long plid, Long staid) {
        String sort = null;
        String templateType = biChannelSpecialActionDao.findTemplateType(plid, staid, new SingleColumnRowMapper<String>(String.class));
        if ("rogerGallet".equals(templateType)) {
            sort = "giftmemo";
        }
        if ("rl".equals(templateType)) {
            List<CommonConfig> cfg = commonConfigDao.findCommonConfigListByParaGroup(ConstantsFtpConifg.FTP_RALPH_LAUREN_QRCODE_URL);
            Map<Long, PackingObj> data = staLineDao.findPackingPageNoLocation(plid, staid, sort, new PackingObjRowMapper());
            String codeurl = cfg.get(0).getParaValue();
            String ralphLaurenChannelCode=cfg.get(1).getParaValue();
            for (Map.Entry<Long, PackingObj> entry : data.entrySet()) {
                PackingObj packingObj = entry.getValue();
                // String url = "https://www.ralphlauren.cn/zh-hans/authenticate?id=";
                String url = ralphLaurenPath;
                String [] channelCfg=ralphLaurenChannelCode.split(",");
                String shopCode = "";
                for(int i=0;i<channelCfg.length;i++){
                    if(packingObj.getPrintshopname2().trim().equals(channelCfg[i].split(":")[0].trim())){
                        shopCode= channelCfg[i].split(":")[1];
                        break;
                    }
                }
                /* if (packingObj.getPrintshopname2().equals("Polo Ralph Lauren京东旗舰店")) {
                    shopCode = "07302";
                } else if (packingObj.getPrintshopname2().equals("Polo Ralph Lauren天猫旗舰店")) {
                    shopCode = "07301";
                }else if(packingObj.getPrintshopname2().equals("Ralph Lauren官方商城")){
                    shopCode = "07304";
                }*/
                String encrypt = Md5Util.generateSecret(packingObj.getSoCode() + shopCode);
                String qrCode = url + encrypt;
                String fileName = QrCodeUtil.createQrCode(qrCode, 100, 100, codeurl, "png");
                packingObj.setQrCode(codeurl + fileName);
            }
            return data;
        }

        return staLineDao.findPackingPageNoLocation(plid, staid, sort, new PackingObjRowMapper());
    }

    public Map<Long, PackingObj> findPackagedPageNoLocation(Long plid, Long ppId, Long staid) {
        return staLineDao.findPackagedPageNoLocation(plid, ppId, staid, new PackingObjRowMapper());
    }

    public Map<Long, PackingObj> findPackingPageShowLocation(Long plid, Long staid) {
        String templateType = biChannelSpecialActionDao.findTemplateType(plid, staid, new SingleColumnRowMapper<String>(String.class));
        Map<Long, PackingObj> data = staLineDao.findPackingPageShowLocation(plid, staid, new PackingObjRowMapper());
        // puma电子发票二维码
        if ("puma1_1".equals(templateType) || "puma1_1_1".equals(templateType)) {
            List<CommonConfig> cfg = commonConfigDao.findCommonConfigListByParaGroup(ConstantsFtpConifg.FTP_PUMA1_QRCODE_URL);
            return pumaEVoicepama(plid, staid, cfg);
        }
        if ("puma2_2".equals(templateType) || "puma2_2_2".equals(templateType)) {
            List<CommonConfig> cfg = commonConfigDao.findCommonConfigListByParaGroup(ConstantsFtpConifg.FTP_PUMA2_QRCODE_URL);
            return pumaEVoicepama(plid, staid, cfg);
        }
        if ("puma3_3".equals(templateType) || "puma3_3_3".equals(templateType)) {
            List<CommonConfig> cfg = commonConfigDao.findCommonConfigListByParaGroup(ConstantsFtpConifg.FTP_PUMA3_QRCODE_URL);
            return pumaEVoicepama(plid, staid, cfg);
        }

        for (Map.Entry<Long, PackingObj> entry : data.entrySet()) {
            PackingObj packingObj = entry.getValue();
            if (packingObj.getReturnTranckingNo() != null) {
                // nikecpp修改模板二维码生成方式
                List<CommonConfig> cfg = commonConfigDao.findCommonConfigListByParaGroup(ConstantsFtpConifg.FTP_NIKE_CPP_QRCODE_URL);
                if (cfg != null && cfg.get(0) != null) {
                    String codeurl = cfg.get(0).getParaValue();
                    String fileName = QrCodeUtil.createQrCodeDelWhiteBound(packingObj.getReturnTranckingNo(), 80, 80, cfg.get(0).getParaValue(), "png");
                    packingObj.setReturnTransnoUrl(codeurl + fileName);
                }
            }
        }
        return data;
    }

    /**
     * 
     * puma装箱单电子发票二维码打印
     * 
     * @param plid
     * @param staid
     * @param cfg
     * @return
     */
    public Map<Long, PackingObj> pumaEVoicepama(Long plid, Long staid, List<CommonConfig> cfg) {
        try {
            Map<Long, PackingObj> data = staLineDao.findPackingPageShowLocation(plid, staid, new PackingObjRowMapper());
            String codeurl = cfg.get(0).getParaValue();
            for (Map.Entry<Long, PackingObj> entry : data.entrySet()) {
                PackingObj packingObj = entry.getValue();
                List<ApplyInvoiceRequestCommand> coms = applyInvoiceRequestDao.findApplyInvoiceRequest(entry.getKey(), new BeanPropertyRowMapper<ApplyInvoiceRequestCommand>(ApplyInvoiceRequestCommand.class));
                ApplyInvoiceRequestCommand com = null;
                if (coms != null && !coms.isEmpty()) {
                    com = coms.get(0);
                }
                // 订单金额单位为分
                ApplyInvoiceRequest applyInvoiceRequest = new ApplyInvoiceRequest(com.getOrderCode(), Integer.parseInt(com.getAmount()), null, com.getShopCode(), "1".equals(com.getIsCheckedInvoice()) ? true : false);
                if (com.getTime() != null) {
                    try {
                        Date orderDate = new SimpleDateFormat("yyyyMMddhhmmss").parse(com.getTime());
                        applyInvoiceRequest.setTime(orderDate.getTime());
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
                // 领取电子发票链接
                String applyInvoiceUrl = InvoiceUtil.genApplyInvoiceUrl(applyInvoiceRequest);
                // 领取电子发票二维码
                // byte[] qrCode = ApplyInvoiceUtil.genApplyInvoiceQRCode(applyInvoiceRequest);
                String fileName = QrCodeUtil.createQrCodeDelWhiteBound(applyInvoiceUrl, 80, 80, codeurl, "png");
                packingObj.setQrCode(codeurl + fileName);
            }
            return data;
        } catch (Exception e) {
            log.error("pumaEVoicepama error staid= " + staid + "plid" + plid, e);
            return staLineDao.findPackingPageShowLocation(plid, staid, new PackingObjRowMapper());
        }
    }

    public Map<Long, PackingObj> findPackingPageShowLocationNike(Long plid, Long staid) {
        try {
            Map<Long, PackingObj> data = staLineDao.findPackingPageShowLocationNike(plid, staid, new PackingObjRowMapper());
            for (Map.Entry<Long, PackingObj> entry : data.entrySet()) {
                PackingObj packingObj = entry.getValue();
                if (packingObj.getReturnTranckingNo() != null) {
                    // nikecpp修改模板二维码生成方式
                    List<CommonConfig> cfg = commonConfigDao.findCommonConfigListByParaGroup(ConstantsFtpConifg.FTP_NIKE_CPP_QRCODE_URL);
                    if (cfg != null && cfg.get(0) != null) {
                        String codeurl = cfg.get(0).getParaValue();
                        String fileName = QrCodeUtil.createQrCodeDelWhiteBound(packingObj.getReturnTranckingNo(), 80, 80, cfg.get(0).getParaValue(), "png");
                        packingObj.setReturnTransnoUrl(codeurl + fileName);
                    }
                }
            }
            return data;
        } catch (Exception e) {
            log.error("nikecpp print error staid= " + staid + "plid" + plid, e);
            return staLineDao.findPackingPageShowLocationNike(plid, staid, new PackingObjRowMapper());
        }
    }

    public Map<Long, PackingObj> findPackagedPageShowLocation(Long plid, Long ppId, Long staid) {
        return staLineDao.findPackagedPageShowLocation(plid, ppId, staid, new PackingObjRowMapper());
    }

    public void logPackingPagePrint(Long plid, Long userId, WhInfoTimeRefNodeType nodeType) {
        PickingList pl = null;
        if (plid != null) {
            pl = pickingListDao.getByPrimaryKey(plid);
        }
        if (pl != null && !StringUtil.isEmpty(pl.getCode())) {
            whInfoTimeRefDao.insertWhInfoTime(pl.getCode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), nodeType.getValue(), userId);
        }
    }

    public void logPackingPagePrint(String plCode, Long userId, WhInfoTimeRefNodeType nodeType) {
        if (StringUtils.hasText(plCode)) {
            pickingListDao.updateHavePrintFlag(plCode);
            whInfoTimeRefDao.insertWhInfoTime(plCode, WhInfoTimeRefBillType.STA_PICKING.getValue(), nodeType.getValue(), userId);
        }
    }

    /**
     * 获取渠道列表
     */
    @Override
    public Pagination<BiChannelCommand> getBiChannelList(int start, int pageSize, BiChannel biChannel, Long id, Sort[] sorts) {
        String code = null;
        String name = null;
        Long cId = null;
        String companyName = null;
        if (biChannel != null) {
            if (biChannel.getCode() != null && !biChannel.getCode().equals("")) {
                code = biChannel.getCode();
            }
            if (biChannel.getName() != null && !biChannel.getName().equals("")) {
                name = biChannel.getName();
            }
            if (biChannel.getCustomer().getId() != null) {
                cId = biChannel.getCustomer().getId();
            }
            if (biChannel.getCompanyName() != null && !biChannel.getCompanyName().equals("")) {
                companyName = biChannel.getCompanyName();
            }
        }
        return biChannelDao.getBiChannelList(start, pageSize, code, name, cId, companyName, new BeanPropertyRowMapperExt<BiChannelCommand>(BiChannelCommand.class), sorts);
    }

    /**
     * 获取公司名称
     * 
     * @param start
     * @param pageSize
     * @param biChannel
     * @param whId
     * @param sorts
     * @return
     */
    @Override
    public List<BiChannelCommand> findInvoiceCompany() {
        return biChannelDao.findInvoiceCompany(new BeanPropertyRowMapperExt<BiChannelCommand>(BiChannelCommand.class));
    }


    /**
     * 获取渠道列表
     */
    @Override
    public Pagination<BiChannelCommand> getBiChannelListByType(int start, int pageSize, BiChannel biChannel, Long whId, Sort[] sorts) {
        String name = null;
        if (biChannel != null) {
            if (biChannel.getName() != null && !biChannel.getName().equals("")) {
                name = biChannel.getName();
            }
        }
        return biChannelDao.getBiChannelListByType(start, pageSize, name, whId, new BeanPropertyRowMapperExt<BiChannelCommand>(BiChannelCommand.class), sorts);
    }

    /**
     * 根据调整单查寻渠道
     * 
     * @param icId
     * @return
     */
    public BiChannel findBichannelByInvCheck(Long icId) {
        if (icId == null) return null;
        InventoryCheck ic = invCheckDao.getByPrimaryKey(icId);
        if (ic != null && ic.getShop() != null) {
            BiChannel bian = biChannelDao.getByPrimaryKey(ic.getShop().getId());
            BiChannel cmd = new BiChannel();
            cmd.setAddress(bian.getAddress());
            cmd.setCode(bian.getCode());
            cmd.setCreateTime(bian.getCreateTime());
            cmd.setId(bian.getId());
            cmd.setIsInboundInvoice(bian.getIsInboundInvoice());
            cmd.setIsJdolOrder(bian.getIsJdolOrder());
            cmd.setIsMarger(bian.getIsMarger());
            cmd.setIsReturnNeedPackage(bian.getIsReturnNeedPackage());
            cmd.setIsSms(bian.getIsSms());
            cmd.setLastModifyTime(bian.getLastModifyTime());
            cmd.setMqASNReceive(bian.getMqASNReceive());
            cmd.setMqOrder(bian.getMqOrder());
            cmd.setMqRTV(bian.getMqRTV());
            cmd.setName(bian.getName());
            cmd.setRtnWarehouseAddress(bian.getRtnWarehouseAddress());
            cmd.setSfFlag(bian.getSfFlag());
            cmd.setShopCode(bian.getShopCode());
            cmd.setSmsTemplate(bian.getSmsTemplate());
            cmd.setStatus(bian.getStatus());
            cmd.setTelephone(bian.getTelephone());
            cmd.setType(bian.getType());
            cmd.setVmiCode(bian.getVmiCode());
            cmd.setVmiWHSource(bian.getVmiWHSource());
            cmd.setVmiWHSource1(bian.getVmiWHSource1());
            cmd.setZipcode(bian.getZipcode());
            return cmd;
        } else {
            return null;
        }
    }



    /**
     * 获取渠道列表
     */
    @Override
    public Pagination<BiChannelCommand> findBiChannelListByTypeAndExpT(int start, int pageSize, BiChannel biChannel, Long id, Sort[] sorts) {
        String code = null;
        String name = null;
        Long cId = null;
        if (biChannel != null) {
            if (biChannel.getCode() != null && !biChannel.getCode().equals("")) {
                code = biChannel.getCode();
            }
            if (biChannel.getName() != null && !biChannel.getName().equals("")) {
                name = biChannel.getName();
            }
            if (biChannel.getCustomer().getId() != null) {
                cId = biChannel.getCustomer().getId();
            }
        }
        return biChannelDao.getBiChannelListByTypeAndExpT(start, pageSize, code, name, cId, new BeanPropertyRowMapperExt<BiChannelCommand>(BiChannelCommand.class), sorts);
    }

    /**
     * 通过ID获取渠道信息
     */
    @Override
    public BiChannelCommand getBiChannelById(Long id) {
        return biChannelDao.findBiChannelById(id, new BeanPropertyRowMapperExt<BiChannelCommand>(BiChannelCommand.class));
    }

    /**
     * 通过渠道编码获取渠道信息
     */
    @Override
    public BiChannelCommand getBiChannelByCode(String code) {
        if (StringUtil.isEmpty(code)) return null;
        return biChannelDao.findBiChannelByCode(code, new BeanPropertyRowMapperExt<BiChannelCommand>(BiChannelCommand.class));
    }

    /**
     * 通过渠道ID获取渠道特殊行为类型为20的数据
     */
    @Override
    public BiChannelSpecialActionCommand getBiChannelSpecialActionByCidType(Long id, int type) {
        return biChannelSpecialActionDao.getBiChannelSpecialActionByCidType(id, type, new BeanPropertyRowMapperExt<BiChannelSpecialActionCommand>(BiChannelSpecialActionCommand.class));
    }

    /**
     * 通过渠道ID获取渠道特殊行为类型为运单定制的数据
     */
    @Override
    public BiChannel checkBiChannelByCodeOrName(String code, String name) {
        return biChannelDao.checkBiChannelByCodeOrName(code, name, new BeanPropertyRowMapperExt<BiChannel>(BiChannel.class));
    }

    /**
     * 修改渠道相关信息
     */
    @Override
    public void updateBiChannel(BiChannel biChannel, BiChannelSpecialAction bcsap, BiChannelSpecialAction bcsae, int deleteBCWR, Long userId) {
        Customer c = customerDao.getByPrimaryKey(biChannel.getCustomer().getId());
        BiChannel b = biChannelDao.getByPrimaryKey(biChannel.getId());
        // 修改渠道表
        b.setCustomer(c);
        b.setName(biChannel.getName());
        b.setTelephone(biChannel.getTelephone());
        b.setAddress(biChannel.getAddress());
        b.setRtnWarehouseAddress(biChannel.getRtnWarehouseAddress());
        b.setZipcode(biChannel.getZipcode());
        b.setIsMarger(biChannel.getIsMarger());
        b.setIsSupportNextMorning(biChannel.getIsSupportNextMorning());
        b.setTransErrorToEms(biChannel.getTransErrorToEms());
        b.setIsJdolOrder(biChannel.getIsJdolOrder());
        b.setIsCartonStaShop(biChannel.getIsCartonStaShop());
        b.setIsImperfect(biChannel.getIsImperfect());
        b.setIsReturnNeedPackage(biChannel.getIsReturnNeedPackage());
        b.setTransAccountsCode(biChannel.getTransAccountsCode());
        b.setSfJcustid(biChannel.getSfJcustid());
        b.setSfJcusttag(biChannel.getSfJcusttag());
        b.setIsImperfectPoId(biChannel.getIsImperfectPoId());
        b.setIsImperfectTime(biChannel.getIsImperfectTime());
        b.setSkuCategories(biChannel.getSkuCategories());
        b.setIsSupportNoCancelSta(biChannel.getIsSupportNoCancelSta());
        if (biChannel.getIsNotValInBoundBachCode() != null) {
            b.setIsNotValInBoundBachCode(biChannel.getIsNotValInBoundBachCode());
        }
        if (biChannel.getIsReturnCheckBatch() != null) {
            b.setIsReturnCheckBatch(biChannel.getIsReturnCheckBatch());
        }
        b.setSpecialType(biChannel.getSpecialType());
        b.setLastModifyTime(new Date());
        b.setCompanyName(biChannel.getCompanyName());
        // 设置销售出库取消状态限制类型
        if (biChannel.getLimitTypeInt().equals(BiChannelLimitType.INTRANSIT.getValue())) {
            b.setLimitType(BiChannelLimitType.INTRANSIT);
        }
        if (biChannel.getLimitTypeInt().equals(BiChannelLimitType.OCCUPIED.getValue())) {
            b.setLimitType(BiChannelLimitType.OCCUPIED);
        }
        if (biChannel.getLimitAmount() != null) {
            b.setLimitAmount(biChannel.getLimitAmount());
        }
        // 是否打印海关单
        b.setIsPrintMaCaoHGD(biChannel.getIsPrintMaCaoHGD());
        if (biChannel.getMoneyLmit() != null) {
            b.setMoneyLmit(biChannel.getMoneyLmit());
        }
        if (biChannel.getIsPickinglistByLoc() != null) {
            b.setIsPickinglistByLoc(biChannel.getIsPickinglistByLoc());
        }
        if (biChannel.getIsPda() != null) {
            b.setIsPda(biChannel.getIsPda());
        }
        try {
            BiChannelLog biChannelLog = new BiChannelLog();
            biChannelLog.setCode(biChannel.getCode());
            biChannelLog.setCustomer(c);
            biChannelLog.setName(biChannel.getName());
            biChannelLog.setTelephone(biChannel.getTelephone());
            biChannelLog.setAddress(biChannel.getAddress());
            biChannelLog.setRtnWarehouseAddress(biChannel.getRtnWarehouseAddress());
            biChannelLog.setZipcode(biChannel.getZipcode());
            biChannelLog.setIsMarger(biChannel.getIsMarger());
            biChannelLog.setIsSupportNextMorning(biChannel.getIsSupportNextMorning());
            biChannelLog.setIsJdolOrder(biChannel.getIsJdolOrder());
            biChannelLog.setIsReturnNeedPackage(biChannel.getIsReturnNeedPackage());
            biChannelLog.setTransAccountsCode(biChannel.getTransAccountsCode());
            biChannelLog.setSkuCategories(biChannel.getSkuCategories());
            if (biChannel.getIsNotValInBoundBachCode() != null) {
                biChannelLog.setIsNotValInBoundBachCode(biChannel.getIsNotValInBoundBachCode());
            }
            biChannelLog.setSpecialType(biChannel.getSpecialType());
            biChannelLog.setLastModifyTime(b.getLastModifyTime());
            biChannelLog.setCompanyName(biChannel.getCompanyName());
            biChannelLog.setOperateTime(new Date());
            biChannelLog.setOperateUserId(userId);
            biChannelLog.setIsPrintMaCaoHGD(biChannel.getIsPrintMaCaoHGD());
            if (biChannel.getMoneyLmit() != null) {
                biChannelLog.setMoneyLmit(biChannel.getMoneyLmit());
            }
            biChannelLogDao.save(biChannelLog);
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("updateBiChannel Exception", ex);
            }
        }
        biChannelDao.save(b);
        // 修改运单定制
        if (bcsap.getId() != null) {
            BiChannelSpecialAction p = biChannelSpecialActionDao.getByPrimaryKey(bcsap.getId());
            p.setChanncel(b);
            p.setShopName(bcsap.getShopName());
            p.setRtnAddress(bcsap.getRtnAddress());
            p.setContactsPhone(bcsap.getContactsPhone());
            p.setContactsPhone1(bcsap.getContactsPhone1());
            p.setContacts(bcsap.getContacts());
            p.setTransAddMemo(bcsap.getTransAddMemo());
            p.setIsInsurance(bcsap.getIsInsurance());
            p.setMinInsurance(bcsap.getMinInsurance());
            p.setMaxInsurance(bcsap.getMaxInsurance());
            biChannelSpecialActionDao.save(p);
        } else {
            BiChannelSpecialAction p = new BiChannelSpecialAction();
            p.setChanncel(b);
            p.setType(BiChannelSpecialActionType.PRINT_EXPRESS_BILL);
            p.setShopName(bcsap.getShopName());
            p.setRtnAddress(bcsap.getRtnAddress());
            p.setContactsPhone(bcsap.getContactsPhone());
            p.setContactsPhone1(bcsap.getContactsPhone1());
            p.setContacts(bcsap.getContacts());
            p.setTransAddMemo(bcsap.getTransAddMemo());
            p.setIsInsurance(bcsap.getIsInsurance());
            p.setMinInsurance(bcsap.getMinInsurance());
            p.setMaxInsurance(bcsap.getMaxInsurance());
            biChannelSpecialActionDao.save(p);
        }
        // 修改装箱清单定制
        if (bcsae.getId() != null) {
            BiChannelSpecialAction e = biChannelSpecialActionDao.getByPrimaryKey(bcsae.getId());
            e.setChanncel(b);
            e.setShopName(bcsae.getShopName());
            e.setRtnAddress(bcsae.getRtnAddress());
            e.setContactsPhone(bcsae.getContactsPhone());
            e.setContactsPhone1(bcsae.getContactsPhone1());
            e.setContacts(bcsae.getContacts());
            e.setIsNotDisplaySum(bcsae.getIsNotDisplaySum());
            e.setIsInsurance(bcsae.getIsInsurance());
            e.setMinInsurance(bcsae.getMinInsurance());
            e.setMaxInsurance(bcsae.getMaxInsurance());
            e.setCustomPrintCode(bcsae.getCustomPrintCode());
            biChannelSpecialActionDao.save(e);
        } else {
            BiChannelSpecialAction e = new BiChannelSpecialAction();
            e.setChanncel(b);
            e.setType(BiChannelSpecialActionType.PRINT_PACKING_PAGE);
            e.setShopName(bcsae.getShopName());
            e.setRtnAddress(bcsae.getRtnAddress());
            e.setContactsPhone(bcsae.getContactsPhone());
            e.setContactsPhone1(bcsae.getContactsPhone1());
            e.setContacts(bcsae.getContacts());
            e.setIsNotDisplaySum(bcsae.getIsNotDisplaySum());
            e.setIsInsurance(bcsae.getIsInsurance());
            e.setMinInsurance(bcsae.getMinInsurance());
            e.setMaxInsurance(bcsae.getMaxInsurance());
            biChannelSpecialActionDao.save(e);
        }
        if (deleteBCWR == 1) {
            // 如果渠道对应客户修改的话，渠道对应客户下的仓库一并删除
            cwrrDao.deleteChannelWhRefByChannelId(b.getId());
        }
    }

    /**
     * 新增渠道相关信息
     */
    @Override
    public void addBiChannel(BiChannel biChannel, BiChannelSpecialAction bcsap, BiChannelSpecialAction bcsae, Long userId) {
        Customer c = customerDao.getByPrimaryKey(biChannel.getCustomer().getId());
        BiChannel b = new BiChannel();
        BiChannelSpecialAction p = new BiChannelSpecialAction();
        BiChannelSpecialAction e = new BiChannelSpecialAction();
        // 修改渠道表
        b.setCustomer(c);
        b.setCode(biChannel.getName());
        b.setName(biChannel.getName());
        b.setTelephone(biChannel.getTelephone());
        b.setAddress(biChannel.getAddress());
        b.setRtnWarehouseAddress(biChannel.getRtnWarehouseAddress());
        b.setZipcode(biChannel.getZipcode());
        b.setIsMarger(biChannel.getIsMarger());
        b.setIsCartonStaShop(biChannel.getIsCartonStaShop());
        b.setIsReturnNeedPackage(biChannel.getIsReturnNeedPackage());
        b.setCreateTime(new Date());
        b.setIsImperfect(biChannel.getIsImperfect());
        b.setIsImperfectPoId(biChannel.getIsImperfectPoId());
        b.setIsImperfectTime(biChannel.getIsImperfectTime());
        b.setTransAccountsCode(biChannel.getTransAccountsCode());
        b.setSkuCategories(biChannel.getSkuCategories());
        if (biChannel.getIsNotValInBoundBachCode() != null) {
            b.setIsNotValInBoundBachCode(biChannel.getIsNotValInBoundBachCode());
        }
        if (biChannel.getIsReturnCheckBatch() != null) {
            b.setIsReturnCheckBatch(biChannel.getIsReturnCheckBatch());
        }
        b.setSpecialType(biChannel.getSpecialType());
        b.setLastModifyTime(new Date());
        b.setCompanyName(biChannel.getCompanyName());
        // 设置销售出库取消状态限制类型
        if (biChannel.getLimitTypeInt().equals(BiChannelLimitType.INTRANSIT.getValue())) {
            b.setLimitType(BiChannelLimitType.INTRANSIT);
        }
        if (biChannel.getLimitTypeInt().equals(BiChannelLimitType.OCCUPIED.getValue())) {
            b.setLimitType(BiChannelLimitType.OCCUPIED);
        }
        if (biChannel.getLimitAmount() != null) {
            b.setLimitAmount(biChannel.getLimitAmount());
        }
        // 是否打印海关单
        b.setIsPrintMaCaoHGD(biChannel.getIsPrintMaCaoHGD());
        if (biChannel.getMoneyLmit() != null) {
            b.setMoneyLmit(biChannel.getMoneyLmit());
        }
        if (biChannel.getIsPickinglistByLoc() != null) {
            b.setIsPickinglistByLoc(biChannel.getIsPickinglistByLoc());
        }
        if (biChannel.getIsPda() != null) {
            b.setIsPda(biChannel.getIsPda());
        }
        try {
            BiChannelLog biChannelLog = new BiChannelLog();
            biChannelLog.setCustomer(c);
            biChannelLog.setCode(biChannel.getName());
            biChannelLog.setName(biChannel.getName());
            biChannelLog.setTelephone(biChannel.getTelephone());
            biChannelLog.setAddress(biChannel.getAddress());
            biChannelLog.setRtnWarehouseAddress(biChannel.getRtnWarehouseAddress());
            biChannelLog.setZipcode(biChannel.getZipcode());
            biChannelLog.setIsMarger(biChannel.getIsMarger());
            biChannelLog.setIsReturnNeedPackage(biChannel.getIsReturnNeedPackage());
            biChannelLog.setCreateTime(b.getCreateTime());
            biChannelLog.setTransAccountsCode(biChannel.getTransAccountsCode());
            if (biChannel.getIsNotValInBoundBachCode() != null) {
                biChannelLog.setIsNotValInBoundBachCode(biChannel.getIsNotValInBoundBachCode());
            }
            biChannelLog.setSpecialType(biChannel.getSpecialType());
            biChannelLog.setLastModifyTime(b.getLastModifyTime());
            biChannelLog.setCompanyName(biChannel.getCompanyName());
            biChannelLog.setOperateTime(new Date());
            biChannelLog.setOperateUserId(userId);
            biChannelLog.setSkuCategories(biChannel.getSkuCategories());
            biChannelLog.setIsPrintMaCaoHGD(biChannel.getIsPrintMaCaoHGD());
            if (biChannel.getMoneyLmit() != null) {
                biChannelLog.setMoneyLmit(biChannel.getMoneyLmit());
            }
            biChannelLogDao.save(biChannelLog);
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("addBiChannel Exception", ex);
            }
        }
        biChannelDao.save(b);
        // 修改运单定制
        p.setChanncel(b);
        p.setType(BiChannelSpecialActionType.PRINT_EXPRESS_BILL);
        p.setShopName(bcsap.getShopName());
        p.setRtnAddress(bcsap.getRtnAddress());
        p.setContactsPhone(bcsap.getContactsPhone());
        p.setContactsPhone1(bcsap.getContactsPhone1());
        p.setContacts(bcsap.getContacts());
        p.setTransAddMemo(bcsap.getTransAddMemo());
        p.setIsInsurance(bcsae.getIsInsurance());
        p.setMinInsurance(bcsae.getMinInsurance());
        p.setMaxInsurance(bcsae.getMaxInsurance());
        biChannelSpecialActionDao.save(p);
        // 修改装箱清单定制
        e.setChanncel(b);
        e.setType(BiChannelSpecialActionType.PRINT_PACKING_PAGE);
        e.setShopName(bcsae.getShopName());
        e.setRtnAddress(bcsae.getRtnAddress());
        e.setContactsPhone(bcsae.getContactsPhone());
        e.setContactsPhone1(bcsae.getContactsPhone1());
        e.setContacts(bcsae.getContacts());
        e.setIsNotDisplaySum(bcsae.getIsNotDisplaySum());
        e.setIsInsurance(bcsae.getIsInsurance());
        e.setMinInsurance(bcsae.getMinInsurance());
        e.setMaxInsurance(bcsae.getMaxInsurance());
        biChannelSpecialActionDao.save(e);
    }

    /**
     * 通过客户ID获取所有对应仓库信息
     */
    @Override
    public List<OperationUnitCommand> findBiChannelWhRefList(Long customerId) {
        return operationUnitDao.findBiChannelWhRefList(customerId, new BeanPropertyRowMapperExt<OperationUnitCommand>(OperationUnitCommand.class));
    }

    @Override
    public String findChannelWhRefListByChannelId(Long channelId) {
        String whRefValue = "";
        List<ChannelWhRefCommand> wrList = cwrrDao.findChannelWhRefListByChannelId(channelId, new BeanPropertyRowMapperExt<ChannelWhRefCommand>(ChannelWhRefCommand.class));
        for (ChannelWhRefCommand wr : wrList) {
            whRefValue += wr.getOuId() + " " + wr.getIsDefaultWh() + " " + wr.getIsPostPage() + " " + wr.getIsPostBill() + " " + wr.getSfJcustid() + " " + wr.getReceivePrefix() + " " + wr.getIsPostQs() + " " + wr.getIsPrintPackageDetail() +" "+wr.getIsPinganCover() +",";
        }
        return whRefValue;
    }

    @Override
    public List<ChannelWhRefCommand> findChannelListByChannelId(Long channelId) {
        return cwrrDao.findChannelWhRefListByChannelId(channelId, new BeanPropertyRowMapperExt<ChannelWhRefCommand>(ChannelWhRefCommand.class));
    }

    /**
     * 更新渠道仓库绑定关系
     */
    @Override
    public void updateChannelWhRef(List<ChannelWhRef> c, Long channelId, Long userId, List<ChannelWhRefCommand> channelList) {
        cwrrDao.deleteChannelWhRefByChannelId(channelId);
        BiChannel bi = biChannelDao.getByPrimaryKey(channelId);
        for (ChannelWhRef cwrList : c) {
            ChannelWhRef cwr = new ChannelWhRef();
            OperationUnit ou = operationUnitDao.getByPrimaryKey(cwrList.getWh().getId());
            cwr.setShop(bi);
            cwr.setWh(ou);
            cwr.setIsDefaultInboundWh(cwrList.getIsDefaultInboundWh());
            cwr.setIsPostExpressBill(cwrList.getIsPostExpressBill());
            cwr.setIsPostPackingPage(cwrList.getIsPostPackingPage());
            cwr.setSfJcustid(cwrList.getSfJcustid());
            cwr.setReceivePrefix(cwrList.getReceivePrefix());
            cwr.setIsPostQs(cwrList.getIsPostQs());// qs
            cwr.setIsPrintPackageDetail(cwrList.getIsPrintPackageDetail());
            cwr.setIsPinganCover(cwrList.getIsPinganCover());
            cwrrDao.save(cwr);
            try {
                ChannelWhRefLog channelWhRefLog = new ChannelWhRefLog();
                channelWhRefLog.setShop(bi);
                channelWhRefLog.setWh(ou);
                channelWhRefLog.setIsDefaultInboundWh(cwrList.getIsDefaultInboundWh());
                channelWhRefLog.setIsPostPackingPage(cwrList.getIsPostPackingPage());
                channelWhRefLog.setIsPostExpressBill(cwrList.getIsPostExpressBill());
                cwr.setSfJcustid(cwrList.getSfJcustid());
                channelWhRefLog.setSfJcustid(cwrList.getSfJcustid());
                channelWhRefLog.setOperateTime(new Date());
                channelWhRefLog.setOperateUserId(userId);
                channelWhRefLog.setIsPostQs(cwrList.getIsPostQs());
                channelWhRefLog.setIsPostPackingPage(cwrList.getIsPrintPackageDetail());
                channelWhRefLogDao.save(channelWhRefLog);
            } catch (Exception ex) {
                if (log.isErrorEnabled()) {
                    log.error("updateChannelWhRef Exception", ex);
                }
            }
        }
        if (null != channelList && channelList.size() > 0) {
            for (ChannelWhRefCommand list : channelList) {
                try {
                    ChannelWhRefLog channelWhRefLog = new ChannelWhRefLog();
                    channelWhRefLog.setShop(bi);
                    OperationUnit ou = operationUnitDao.getByPrimaryKey(list.getOuId());
                    channelWhRefLog.setWh(ou);
                    channelWhRefLog.setIsDefaultInboundWh(false);
                    channelWhRefLog.setIsPostPackingPage(false);
                    channelWhRefLog.setIsPostExpressBill(false);
                    channelWhRefLog.setOperateTime(new Date());
                    channelWhRefLog.setOperateUserId(userId);
                    channelWhRefLogDao.save(channelWhRefLog);
                } catch (Exception ex) {
                    if (log.isErrorEnabled()) {
                        log.error("updateChannelWhRef Exception", ex);
                    }
                }
            }
        }
    }

    @Override
    public Pagination<BiChannelImperfectCommand> findBiChanneImperfectlList(int start, int pageSize, Long channelId, Long id, Sort[] sorts) {
        return imperfectDao.getBiChannelImperfectlList(start, pageSize, channelId, new BeanPropertyRowMapperExt<BiChannelImperfectCommand>(BiChannelImperfectCommand.class), new Sort[] {new Sort("ct.id asc")});
    }

    @Override
    public BiChannelRef checkBiChannelRefByBiChannel(String code) {
        BiChannel bi = biChannelDao.getByCode(code);
        return bgDao.getBiChannelGroupByBiChannelID(bi.getId());
    }

    /**
     * 根据ID获取BiChannel
     * 
     * @param channelId
     * @return
     */
    public BiChannel getBiChannel(Long channelId) {
        return biChannelDao.getByPrimaryKey(channelId);
    }

    public String findInvoiceType(Long plId, Long staId) {
        return biChannelSpecialActionDao.findInvoiceType(plId, staId, new SingleColumnRowMapper<String>());
    }

    public void addInvoiceExecuteCountByPlId(Long plId, Long staId) {
        if (plId != null) {
            staInvoiceDao.addExecuteCountByPlId(plId);
        }
        if (staId != null) {
            staInvoiceDao.addExecuteCountByPlId(staId);
        }
    }

    @Override
    public void addInvoiceExecuteCountByPlIds(List<Long> plIds) {
        staInvoiceDao.addExecuteCountByPlIds(plIds);

    }

    public List<StaInvoiceCommand> findCoachInvoice(Long plId, Long staId) {
        return staInvoiceDao.findCoachInvoice(plId, staId);
    }

    public List<StaInvoiceCommand> findBurberryInvoice(Long plId, Long staId) {
        if (staId != null) {
            return staInvoiceDao.findBurberryInvoiceByStaid(staId);
        } else {
            return staInvoiceDao.findBurberryInvoiceByPlid(plId);
        }
    }

    public List<StaInvoiceCommand> findDefaultInvoice(Long plId, Long staId) {
        if (staId != null) {
            return staInvoiceDao.findInvoiceByStaid(staId);
        } else {
            return staInvoiceDao.findInvoiceByPlid(plId);
        }
    }

    @Override
    public String getIsSpecialByStaAndSku(String staCode, String barCode, Long ouId) {
        // ChooseOption ch = chooseOptionDao.findByCategoryCodeAndKey("snOrExpDate", "1");
        ChooseOption ch = getChooseOptionCache("snOrExpDate");
        Boolean flag = null;
        if(ch!=null&&ch.getOptionValue().equals("1")) {
            flag = biChannelDao.getIsSpecialByStaAndSku1(staCode, barCode, NIKE_INBOUND_PRICE_LIMIT, ouId, new SingleColumnRowMapper<Boolean>(Boolean.class));
        } else {
            flag = biChannelDao.getIsSpecialByStaAndSku(staCode, barCode, NIKE_INBOUND_PRICE_LIMIT, new SingleColumnRowMapper<Boolean>(Boolean.class));
        }
        if (flag != null && flag) {
            staLineDao.updateDefaultStatus(staCode, barCode);
            return "true";
        }
        return "false";
    }

    public Boolean checkIsSpecialByStaAndSku(String staCode, String barCode) {
        Boolean msg = false;
        List<StockTransApplicationCommand> staList = staDao.findSkuByStaCode(staCode, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
        if (null != staList && staList.size() > 0) {
            for (StockTransApplicationCommand sta : staList) {
                if (barCode.equals(sta.getBarCode())) {
                    msg = staLineDao.findStaIsSpecailSku(sta.getSlipCode1(), sta.getSkuid(), new SingleColumnRowMapper<Boolean>(Boolean.class));
                    if (msg) {
                        break;
                    }
                }
            }
        }
        return msg;
    }

    @Override
    public List<BiChannel> findBiChannelByOuidAndCartonStaShop(Long ouid, Boolean isCartonStaShop) {
        return biChannelDao.findRefShopByWhOuIdAndCartonShop(ouid, isCartonStaShop, new BeanPropertyRowMapperExt<BiChannel>(BiChannel.class));
    }

    @Override
    public List<StaInvoiceCommand> findReplenishBurberryInvoice(String batchNo, List<Long> wioIdList) {
        return staInvoiceDao.findReplenishBurberryInvoice(batchNo, wioIdList);
    }

    @Override
    public List<StaInvoiceCommand> findReplenishCoachInvoice(String batchNo, List<Long> wioIdList) {
        return staInvoiceDao.findReplenishCoachInvoice(batchNo, wioIdList);
    }

    @Override
    public List<StaInvoiceCommand> findDefaultInvoice(String batchNo, List<Long> wioIdList) {
        return staInvoiceDao.findDefaultInvoice(batchNo, wioIdList);
    }

    @Override
    public String exportSoInvoiceByBatchNo(String batchNo, List<Long> wioIdList) {
        return biChannelSpecialActionDao.findInvoiceTypeByBatchNo(batchNo, wioIdList, new SingleColumnRowMapper<String>());
    }

    @Override
    public void addInvoiceExecuteCountByBatchNo(String batchNo, List<Long> wioIdList) {
        if (batchNo != null && wioIdList.size() > 0) {
            staInvoiceDao.addExecuteCountByBatchNo(batchNo, wioIdList);
        }
    }

    @Override
    public List<BiChannel> selectAllBiChannel() {
        List<BiChannel> list = biChannelDao.findAllBiChannel(new BeanPropertyRowMapper<BiChannel>(BiChannel.class));
        return list;
    }

    @Override
    public List<StaInvoiceCommand> findDefaultInvoices(List<Long> plIds) {
        return staInvoiceDao.findInvoiceByPlids(plIds);
    }

    @Override
    public void addImperfect(BiChannelImperfect biChannelImperfect) {
        String[] imperfect = biChannelImperfect.getCode().split(";");
        for (int i = 0; i < imperfect.length; i++) {
            String[] codeName = imperfect[i].split(":");
            BiChannelImperfect biImperfect = new BiChannelImperfect();
            biImperfect.setChannelId(biChannelImperfect.getChannelId());

            if (!StringUtils.hasText(codeName[0])) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            if (!StringUtils.hasText(codeName[1])) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            biImperfect.setCode(codeName[0]);
            biImperfect.setName(codeName[1]);
            biImperfect.setIsLocked(false);
            imperfectDao.save(biImperfect);
        }
    }

    @Override
    public Pagination<BiChannelImperfectLineCommand> findBiChanneImperfectlLineList(int start, int pageSize, Long imperfectId, Long id, Sort[] sorts) {
        return imperfectLineDao.findBiChanneImperfectlLineList(start, pageSize, imperfectId, new BeanPropertyRowMapperExt<BiChannelImperfectLineCommand>(BiChannelImperfectLineCommand.class), new Sort[] {new Sort("li.id asc")});
    }

    @Override
    public void addImperfectLine(BiChannelImperfectLine biChannelImperfectLine) {
        String[] imperfectLine = biChannelImperfectLine.getCode().split(";");
        for (int i = 0; i < imperfectLine.length; i++) {
            String[] codeName = imperfectLine[i].split(":");
            BiChannelImperfectLine channelImperfectLine = new BiChannelImperfectLine();
            channelImperfectLine.setCode(codeName[0]);
            channelImperfectLine.setImperfectId(biChannelImperfectLine.getImperfectId());
            channelImperfectLine.setName(codeName[1]);
            imperfectLineDao.save(channelImperfectLine);
        }
    }

    @Override
    public List<BiChannelImperfectCommand> findImperfect(Long channelId) {
        return imperfectDao.findImperfect(channelId, new BeanPropertyRowMapperExt<BiChannelImperfectCommand>(BiChannelImperfectCommand.class));
    }

    @Override
    public List<BiChannelImperfectLineCommand> findImperfectLine(Long imperfectId) {
        return imperfectLineDao.findImperfectLine(imperfectId, new BeanPropertyRowMapperExt<BiChannelImperfectLineCommand>(BiChannelImperfectLineCommand.class));
    }

    @Override
    public ImperfectStv addImperfectStv(ImperfectStv imperfectStv) {
        InventoryStatus status = statusDao.getByPrimaryKey(imperfectStv.getStatusId().getId());
        ImperfectStv stv = new ImperfectStv();
        stv.setBarCode(imperfectStv.getBarCode());
        stv.setJmCode(imperfectStv.getJmCode());
        if (status.getName().equals("残次品")) {
            stv.setDefectCode(getTimeMillisSequence());
        }
        stv.setName(imperfectStv.getName());
        stv.setSta(imperfectStv.getSta());
        stv.setAddQuantity(imperfectStv.getAddQuantity());
        stv.setQuantity(imperfectStv.getQuantity());
        stv.setCompleteQuantity(imperfectStv.getCompleteQuantity());
        stv.setImperfectLineName(imperfectStv.getImperfectLineName());
        stv.setImperfectName(imperfectStv.getImperfectName());
        stv.setMemo(imperfectStv.getMemo());
        stv.setSkuSn(imperfectStv.getSkuSn());
        stv.setStatusName(imperfectStv.getStatusName());
        stv.setSupplierCode(imperfectStv.getSupplierCode());
        stv.setCreateTime(new Date());
        stv.setStatusId(imperfectStv.getStatusId());
        stv.setPoId(imperfectStv.getPoId());
        stv.setFactoryCode(imperfectStv.getFactoryCode());
        stv.setSn(imperfectStv.getSn());
        imperfectStvDao.save(stv);
        return stv;

    }

    public static String getTimeMillisSequence() {
        long nanoTime = System.nanoTime();
        String preFix = "";
        if (nanoTime < 0) {
            preFix = "CC";// 负数补位A保证负数排在正数Z前面,解决正负临界值(如A9223372036854775807至Z0000000000000000000)问题。
            nanoTime = nanoTime + Long.MAX_VALUE + 1;
        } else {
            preFix = "C";
        }
        String nanoTimeStr = String.valueOf(nanoTime);

        int difBit = String.valueOf(Long.MAX_VALUE).length() - nanoTimeStr.length();
        for (int i = 0; i < difBit; i++) {
            preFix = preFix + "0";
        }
        nanoTimeStr = preFix + nanoTimeStr;

        return nanoTimeStr;
    }

    @Override
    public List<ImperfectStvCommand> findImperfectStvLine(Long staId) {
        return imperfectStvDao.findImperfectStvLine(staId, new BeanPropertyRowMapperExt<ImperfectStvCommand>(ImperfectStvCommand.class));
    }

    @Override
    public void saveImperfectStvLineLog(Long staId, OperationUnit operationUnit) {
        List<ImperfectStvCommand> commands = imperfectStvDao.findImperfectStvLine(staId, new BeanPropertyRowMapperExt<ImperfectStvCommand>(ImperfectStvCommand.class));
        for (ImperfectStvCommand imperfectStvCommand : commands) {
            ImperfectStvLog imperfectStvLog = new ImperfectStvLog();
            imperfectStvLog.setAddQuantity(imperfectStvCommand.getAddQuantity());
            imperfectStvLog.setBarCode(imperfectStvCommand.getBarCode());
            imperfectStvLog.setCompleteQuantity(1);
            imperfectStvLog.setImperfectLineName(imperfectStvCommand.getImperfectLineName());
            imperfectStvLog.setImperfectName(imperfectStvCommand.getImperfectName());
            imperfectStvLog.setJmCode(imperfectStvCommand.getJmCode());
            imperfectStvLog.setKeyProperties(imperfectStvCommand.getKeyProperties());
            imperfectStvLog.setMemo(imperfectStvCommand.getMemo());
            imperfectStvLog.setName(imperfectStvCommand.getSkuName());
            imperfectStvLog.setQuantity(imperfectStvCommand.getQuantity());
            imperfectStvLog.setSkuSn(imperfectStvCommand.getSkuSn());
            imperfectStvLog.setSta(imperfectStvCommand.getSta().getId());
            imperfectStvLog.setStatusName(imperfectStvCommand.getIntInvstatusName());
            imperfectStvLog.setSupplierCode(imperfectStvCommand.getJmskuCode());
            imperfectStvLog.setCreateTime(imperfectStvCommand.getCreateTime());
            imperfectStvLog.setLogTime(imperfectStvCommand.getCreateTime());
            imperfectStvLog.setSn(imperfectStvCommand.getSn());
            imperfectStvLog.setStatusId(0L);
            imperfectStvLogDao.save(imperfectStvLog);
            if (StringUtils.hasText(imperfectStvCommand.getDefectCode())) {
                SkuImperfect sku = new SkuImperfect();
                sku.setCreateTime(new Date());
                sku.setDefectCode(imperfectStvCommand.getDefectCode());
                sku.setDefectType(imperfectStvCommand.getImperfectName());
                sku.setDefectWhy(imperfectStvCommand.getImperfectLineName());
                sku.setOwner(imperfectStvCommand.getOwner());
                sku.setQty(imperfectStvCommand.getAddQuantity());
                Sku sku2 = skuDao.getSkuByBarcode(imperfectStvCommand.getBarCode());
                sku.setSku(sku2);
                sku.setSta(imperfectStvCommand.getSta());
                sku.setOuId(operationUnit);
                sku.setStatus(1);
                sku.setPoId(imperfectStvCommand.getPoId());
                sku.setFactoryCode(imperfectStvCommand.getFactoryCode());
                skuImperfectDao.save(sku);
            }
            ImperfectStv imperfectStv = new ImperfectStv();
            imperfectStv.setId(imperfectStvCommand.getId());
            imperfectStvDao.delete(imperfectStv);
        }
    }

    @Override
    public SkuImperfect addSkuImperfect(SkuImperfect imperfect, OperationUnit ouId) {
        SkuImperfect skuImperfect = new SkuImperfect();
        skuImperfect.setCreateTime(new Date());
        skuImperfect.setDefectCode(getTimeMillisSequence());
        skuImperfect.setDefectType(imperfect.getDefectType());
        skuImperfect.setDefectWhy(imperfect.getDefectWhy());
        skuImperfect.setOuId(ouId);
        skuImperfect.setOwner(imperfect.getOwner());
        skuImperfect.setQty(imperfect.getQty());
        skuImperfect.setSku(imperfect.getSku());
        skuImperfect.setStatus(1);
        skuImperfectDao.save(skuImperfect);
        return skuImperfect;
    }

    @Override
    public void updateSkuImperfect(SkuImperfect imperfect) {
        SkuImperfect skuImperfect = skuImperfectDao.getByPrimaryKey(imperfect.getId());
        skuImperfect.setDefectType(imperfect.getDefectType());
        skuImperfect.setDefectWhy(imperfect.getDefectWhy());
        skuImperfect.setFactoryCode(imperfect.getFactoryCode());
        skuImperfect.setPoId(imperfect.getPoId());
        skuImperfect.setMemo(imperfect.getMemo());
        skuImperfectDao.save(skuImperfect);
        pdaReceiveManager.updateCartonLineSn(skuImperfect.getDefectCode(), imperfect.getDefectWhy(), imperfect.getDefectType());

    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus addSkuImperfect(File file, Long cid) {
        log.debug("===========addSkuImperfect start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<BiChannelImperfectCommand> list = null;
        ReadStatus rs = null;
        try {
            rs = userWarehouseReader.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                return rs;
            }
            list = (List<BiChannelImperfectCommand>) beans.get("data");
            BiChannel biChannel = biChannelDao.getByPrimaryKey(cid);
            saveSkuImperfect(list, biChannel);
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("addSkuImperfect Exception:" + cid, ex);
            }
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public void saveSkuImperfect(List<BiChannelImperfectCommand> list, BiChannel biChannel) {
        for (BiChannelImperfectCommand biChannelImperfectCommand : list) {
            BiChannelImperfect biChannelImperfect = biChannelImperfectDao.findImperfectCode(biChannelImperfectCommand.getCode(), biChannel.getId());
            if (biChannelImperfect == null) {
                if (StringUtils.hasText(biChannelImperfectCommand.getCode())) {
                    biChannelImperfect = new BiChannelImperfect();
                    biChannelImperfect.setChannelId(biChannel);
                    biChannelImperfect.setCode(biChannelImperfectCommand.getCode());
                    biChannelImperfect.setName(biChannelImperfectCommand.getName());
                    biChannelImperfect.setIsLocked(false);
                } else {
                    throw new BusinessException(ErrorCode.IMPORT_SN_EXECL_ERROR);
                }
            } else {
                biChannelImperfect.setName(biChannelImperfectCommand.getName());
            }


            biChannelImperfectDao.save(biChannelImperfect);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus addSkuImperfectWhy(File file, Long channelId) {
        log.debug("===========addSkuImperfect start============");
        Map<String, Object> beans = new HashMap<String, Object>();
        List<BiChannelImperfectLineCommand> list = null;
        ReadStatus rs = null;
        try {
            rs = addSkuImperfectWhy.readSheet(new FileInputStream(file), 0, beans);
            if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
                rs.setStatus(-2);
                rs.addException(new BusinessException(ErrorCode.EXCEL_PARSE_ERROR));
                return rs;
            }
            list = (List<BiChannelImperfectLineCommand>) beans.get("data");
            BiChannelImperfect biChannelImperfect = biChannelImperfectDao.getByPrimaryKey(channelId);
            saveSkuImperfectWhy(list, biChannelImperfect);
            return rs;
        } catch (BusinessException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    public void saveSkuImperfectWhy(List<BiChannelImperfectLineCommand> list, BiChannelImperfect biChannelImperfect) {
        for (BiChannelImperfectLineCommand biChannelImperfectCommand : list) {
            BiChannelImperfectLine biChannelImperfectline = biChannelImperfectLineDao.findImperfectCode(biChannelImperfectCommand.getCode(), biChannelImperfect.getId());
            if (biChannelImperfectline == null) {
                if (StringUtils.hasText(biChannelImperfectCommand.getCode())) {
                    biChannelImperfectline = new BiChannelImperfectLine();
                    biChannelImperfectline.setImperfectId(biChannelImperfect);
                    biChannelImperfectline.setCode(biChannelImperfectCommand.getCode());
                    biChannelImperfectline.setName(biChannelImperfectCommand.getName());
                } else {
                    throw new BusinessException(ErrorCode.IMPORT_SN_EXECL_ERROR);
                }
            } else {
                biChannelImperfectline.setName(biChannelImperfectCommand.getName());
            }


            biChannelImperfectLineDao.save(biChannelImperfectline);
        }
    }

    @Override
    public ChannelWhRef findChannelRefByWhIdAndShopId(Long whId, Long shopId) {
        return cwrrDao.getChannelRef(whId, shopId);
    }

    @Override
    public Map<Long, PackingObj> getAdidasSpecialCustomValue(Long plId, Long staId) {
        Map<Long, PackingObj> data = staLineDao.getAdidasSpecialCustomValue(plId, staId, new PackingObjRowMapper());
        try {
            for (Map.Entry<Long, PackingObj> entry : data.entrySet()) {
                PackingObj packingObj = entry.getValue();
                List<PackingLineObj> line = packingObj.getLines();
                List<StaLineCommand> list = staLineDao.findCancelLineBySta(entry.getKey(), new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
                if (list != null && !list.isEmpty()) {
                    Map<Long, Long> cancelMap = new HashMap<Long, Long>();
                    for (StaLineCommand sc : list) {
                        Long quantity = cancelMap.get(sc.getSkuId());
                        if (quantity == null) {
                            if (sc.getQuantity() != null) {
                                cancelMap.put(sc.getSkuId(), sc.getQuantity());
                            }
                        } else {
                            cancelMap.put(sc.getSkuId(), sc.getQuantity() + quantity);
                        }
                    }
                    if (line != null) {
                        for (int i = 0; i < line.size(); i++) {
                            PackingLineObj obj = line.get(i);
                            Long num = cancelMap.get(obj.getSkuId());
                            if (num != null) {
                                Integer qty = obj.getQty() - num.intValue();
                                if (qty > 0) {
                                    obj.setQty(qty);
                                    cancelMap.remove(obj.getSkuId());
                                    packingObj.setTotalQty2(packingObj.getTotalQty2() - num.intValue());
                                } else if (qty == 0) {
                                    obj.setQty(0);
                                    cancelMap.remove(obj.getSkuId());
                                    line.remove(i);
                                    i--;
                                    packingObj.setTotalQty2(packingObj.getTotalQty2() - num.intValue());
                                } else {
                                    // bug修复 不够减时总数量只扣当前行数量
                                    packingObj.setTotalQty2(packingObj.getTotalQty2() - obj.getQty());
                                    obj.setQty(0);
                                    cancelMap.put(obj.getSkuId(), (long) -qty);
                                    line.remove(i);
                                    i--;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("adidas print PackingPage Error" + e);
            data = staLineDao.getAdidasSpecialCustomValue(plId, staId, new PackingObjRowMapper());
        }
        return data;
    }


    @Override
    public Map<Long, PackingObj> getMkSpecialCustomValue(Long plId, Long staId) {
        return staLineDao.getMkPackingPageNoLocation(plId, staId, new PackingObjRowMapper());
    }


    @Override
    public Pagination<SfCloudWarehouseConfigCommand> findSfTimeList(int start, int pageSize, Long ouId, Long cId, Sort[] sorts) {
        return sfCloudWarehouseConfigDao.findCloudConfigByOuByPage(start, pageSize, ouId, cId, new BeanPropertyRowMapperExt<SfCloudWarehouseConfigCommand>(SfCloudWarehouseConfigCommand.class), sorts);
    }

    @Override
    public List<WarehouseCommand> findAllWarehouse() {
        List<WarehouseCommand> list = warehouseDao.findAllWarehouse2(new BeanPropertyRowMapper<WarehouseCommand>(WarehouseCommand.class));
        return list;
    }

    @Override
    public String verifySfJcustid(List<ChannelWhRef> list) {// 验证T_WH_TRANS_SF是否有SfJcustid
        StringBuffer string = new StringBuffer();
        for (ChannelWhRef channelWhRef : list) {
            TransSfInfo sf = null;
            if (!StringUtil.isEmpty(channelWhRef.getSfJcustid())) {
                sf = transSfInfoDao.findTransSfInfoJCustid(channelWhRef.getSfJcustid());
                if (sf == null) {
                    string.append(channelWhRef.getSfJcustid() + ";");
                }
            }

        }
        return string.toString();
    }

    /**
     * 
     * @author LuYingMing
     * @see com.jumbo.wms.manager.channel.ChannelManager#getReebokSpecialCustomValue(java.lang.Long,
     *      java.lang.Long)
     */
    @Override
    public Map<Long, PackingObj> getReebokSpecialCustomValue(Long plId, Long staId) {
        Map<Long, PackingObj> data = staLineDao.getReebokSpecialCustomValue(plId, staId, new PackingObjRowMapper());
        return data;
    }

    /**
     * 
     * @author LuYingMing
     * @see com.jumbo.wms.manager.channel.ChannelManager#getGoProSpecialCustomValue(java.lang.Long,
     *      java.lang.Long)
     */
    @Override
    public Map<Long, PackingObj> getGoProSpecialCustomValue(Long plId, Long staId) {
        Map<Long, PackingObj> data = staLineDao.getGoProSpecialCustomValue(plId, staId, new PackingObjRowMapper());
        return data;
    }

    /**
     * 方法说明：Nike HK 装箱清单文案图片特殊定制
     */
    @Override
    public Map<Long, PackingObj> getNikeHKSpecialCustomValue(Long plId, Long staId) {
        if (plId != null && staId != null) {
            // 单个打印
            return printHKJson(plId, staId);
        } else {
            // 批量打印
            Map<Long, PackingObj> total = staLineDao.findPackingPageShowLocation(plId, staId, new PackingObjRowMapper());
            for (Map.Entry<Long, PackingObj> entry : total.entrySet()) {
                Map<Long, PackingObj> data = printHKJson(plId, entry.getKey());
                total.put(entry.getKey(), data.get(entry.getKey()));
            }
            return total;
        }
    }

    /**
     * 单个Nike-HK装箱清单文案json数据封装
     */
    private Map<Long, PackingObj> printHKJson(Long plId, Long staId) {
        Map<Long, PackingObj> data = staLineDao.findPackingPageShowLocation(plId, staId, new PackingObjRowMapper());
        // 判断是否有文案打印的特殊定制
        List<StaSpecialExecutedCommand> sp = staSpecialExecutedDao.findStaSpecialByStaId(staId, StaSpecialExecuteType.NIKE_ACTIVITY_PIC.getValue(), new BeanPropertyRowMapper<StaSpecialExecutedCommand>(StaSpecialExecutedCommand.class));
        if (sp == null || sp.isEmpty()) {
            // 按照原先显示库位的设定,返回数据
            return data;
        }
        String json = sp.get(0).getMemo();// 文案json
        if (org.apache.commons.lang3.StringUtils.isBlank(json)) {
            log.error("前端传递的json数据不存在！staId=" + staId);
            return data;
        }
        if (json.startsWith("[") && json.endsWith("]")) {
            try {
                json = json.substring(1, json.length() - 1);
            } catch (Exception e) {
                log.error("json截取异常！staId=" + staId);
                return data;
            }
        }
        // 解析json
        List<String> nikeHKList = new ArrayList<String>();
        try {
            JSONObject inObj = new JSONObject(json);
            String A = inObj.has("A") ? (inObj.get("A").toString()) : "";
            nikeHKList.add(A);
            String B = inObj.has("B") ? (inObj.get("B").toString()) : "";
            nikeHKList.add(B);
            String C = inObj.has("C") ? (inObj.get("C").toString()) : "";
            nikeHKList.add(C);
            String D = inObj.has("D") ? (inObj.get("D").toString()) : "";
            nikeHKList.add(D);
            String E = inObj.has("E") ? (inObj.get("E").toString()) : "";
            nikeHKList.add(E);
            String F = inObj.has("F") ? (inObj.get("F").toString()) : "";
            nikeHKList.add(F);
            String G = inObj.has("G") ? (inObj.get("G").toString()) : "";
            nikeHKList.add(G);
        } catch (Exception e) {
            log.error("前端传递的json解析异常！staId=" + staId, e);
            return data;
        }
        for (Map.Entry<Long, PackingObj> entry : data.entrySet()) {
            PackingObj packingObj = entry.getValue();
            packingObj.setNikeHKList(nikeHKList);
        }
        return data;
    }

    /**
     * 单个Nike-HK装箱清单文案图片数据封装（原始方案，不再使用）
     */
    private Map<Long, PackingObj> printHKPic(Long plId, Long staId) {
        Map<Long, PackingObj> data = staLineDao.findPackingPageShowLocation(plId, staId, new PackingObjRowMapper());
        // 判断是否有文案图片打印的特殊定制
        List<StaSpecialExecutedCommand> picNames = staSpecialExecutedDao.findStaSpecialByStaId(staId, StaSpecialExecuteType.NIKE_ACTIVITY_PIC.getValue(), new BeanPropertyRowMapper<StaSpecialExecutedCommand>(StaSpecialExecutedCommand.class));
        if (picNames == null || picNames.isEmpty()) {
            // 按照原先显示库位的设定,返回数据
            return data;
        }
        String picName = picNames.get(0).getMemo();// 文案图片名称
        if (org.apache.commons.lang3.StringUtils.isBlank(picName)) {
            log.error("前端传递的图片名称不存在！staId=" + staId);
            return data;
        }
        Map<String, String> cfg = configManager.getNikeHKFTPConfig();
        String localDir = cfg.get(Constants.NIKE_HK_FTP_DOWN_LOCALPATH);
        File file = new File(localDir + File.separator + picName);
        if (file.exists() && !file.isDirectory()) {
            for (Map.Entry<Long, PackingObj> entry : data.entrySet()) {
                PackingObj packingObj = entry.getValue();
                packingObj.setNikeHKPic(localDir + File.separator + picName);
            }
            return data;
        } else {
            try {
                int result = downloadFile(picName);
                if (result != 0) {
                    // 下载失败
                    log.info("{}… ………download file failed", new Object[] {new Date() + picName});
                    return data;
                }
                for (Map.Entry<Long, PackingObj> entry : data.entrySet()) {
                    PackingObj packingObj = entry.getValue();
                    packingObj.setNikeHKPic(localDir + File.separator + picName);
                }
                return data;
            } catch (Exception e) {
                log.error("Nike-HK获取SFTP装箱清单图片失败！图片名称：" + picName, e);
                return data;
            }
        }
    }

    /**
     * 下载Nike-HK 装箱清单文案图片
     */
    private int downloadFile(String picName) {
        Map<String, String> cfg = configManager.getNikeHKFTPConfig();
        String localDownFileDir = cfg.get(Constants.NIKE_HK_FTP_DOWN_LOCALPATH);// 本地下载目录
        File dir = new File(localDownFileDir);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs(); // 创建目录
        }
        String remotePath = cfg.get(Constants.NIKE_HK_FTP_DOWNPATH);// 远程下载资源目录
        // 下载
        int result = SFTPUtil.readFile(cfg.get(Constants.NIKE_HK_FTP_URL), cfg.get(Constants.NIKE_HK_FTP_PORT), cfg.get(Constants.NIKE_HK_FTP_USERNAME), cfg.get(Constants.NIKE_HK_FTP_PASSWORD), remotePath, localDownFileDir, null, false, picName);
        if (result == 0) {
            log.debug("{}… ………download file success", new Object[] {new Date() + picName});
            if (isImage(new File(localDownFileDir + File.separator + picName))) {
                return 0;
            } else {
                File file = new File(localDownFileDir + File.separator + picName);
                if (file.exists()) {
                    file.delete();
                }
                return result;
            }
        } else {
            log.info("{}… ………download file is not a image", new Object[] {new Date() + picName});
            return result;
        }
    }

    /**
     * 判断判断当前文件是否图片
     * 
     * @param imageFile
     * @return
     */
    public boolean isImage(File imageFile) {
        if (!imageFile.exists()) {
            return false;
        }
        Image img = null;
        try {
            img = ImageIO.read(imageFile);
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            img = null;
        }
    }

    @Override
    public String findPickingListCustomPrintCode(Long plid, Long staid) {
        return biChannelSpecialActionDao.findCustomPtintCode(plid, staid, new SingleColumnRowMapper<String>(String.class));
    }

    public ChooseOption getChooseOptionCache(String strCode) {
        if (StringUtil.isEmpty(strCode)) return null;
        ChooseOption result = chooseOptionCache.get(strCode);
        // 缓存中的数据不存在或者已过期
        if (result == null) {
            result = cacheChooseOption(strCode);
        }
        return result;
    }

    private synchronized ChooseOption cacheChooseOption(String strCode) {
        ChooseOption result = chooseOptionDao.findByCategoryCodeAndKey("snOrExpDate", "1");
        chooseOptionCache.put(strCode, result, 10 * 60 * 1000);
        return result;
    }

}
