package com.jumbo.wms.manager.task.hub4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.baozun.ecp.ip.command.pinganInsurance.Response;
import com.baozun.ecp.ip.command.response.ErpResponse;
import com.baozun.ecp.ip.manager.wms3.Wms3AdapterInteractManager;
import com.jumbo.dao.baoshui.CustomsDeclarationDao;
import com.jumbo.dao.baoshui.CustomsDeclarationLineDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.lf.StaLfDao;
import com.jumbo.dao.odo.OdoDao;
import com.jumbo.dao.odo.OdoLineDao;
import com.jumbo.dao.pingan.WhPingAnCoverDao;
import com.jumbo.dao.warehouse.SkuDeclarationDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.wmsInterface.IntfcCfmDao;
import com.jumbo.dao.wmsInterface.IntfcInvoiceCfmDao;
import com.jumbo.dao.wmsInterface.IntfcInvoiceLineCfmDao;
import com.jumbo.dao.wmsInterface.IntfcLineCfmDao;
import com.jumbo.wms.manager.hub4.WmsOrderServiceToHub4Manager;
import com.jumbo.wms.manager.hub4.WmsOrderToHub4Task;
import com.jumbo.wms.manager.odo.OdoManager;
import com.jumbo.wms.manager.warehouse.CustomsDeclarationManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuDeclarationCommand;
import com.jumbo.wms.model.odo.Odo;
import com.jumbo.wms.model.odo.OdoLine;
import com.jumbo.wms.model.pingan.WhPingAnCover;
import com.jumbo.wms.model.pingan.WhPingAnCoverCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclarationCommand;
import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclarationLineCommand;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcCfm;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcCfmCommand;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcInvoiceCfm;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcInvoiceCfmCommand;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcInvoiceLineCfm;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcInvoiceLineCfmCommand;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcLineCfm;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcLineCfmCommand;

import loxia.dao.support.BeanPropertyRowMapperExt;

@Service("wmsOrderToHub4Task")
public class WmsOrderToHub4TaskImpl implements WmsOrderToHub4Task {

    private static final long serialVersionUID = 6742174425858626942L;
    private static final Logger log = LoggerFactory.getLogger(WmsOrderToHub4TaskImpl.class);

    @Autowired
    private IntfcCfmDao intfcCfmDao;
    @Autowired
    private IntfcLineCfmDao intfcLineCfmDao;
    @Autowired
    private IntfcInvoiceCfmDao intfcInvoiceCfmDao;
    @Autowired
    private IntfcInvoiceLineCfmDao intfcInvoiceLineCfmDao;
    @Autowired
    private Wms3AdapterInteractManager adapterManager;
    @Autowired
    private WmsOrderServiceToHub4Manager wmsOrderServiceToHub4Manager;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private WhPingAnCoverDao pinganConverDao;
    @Autowired
    private StockTransApplicationDao staDao;

    @Autowired
    private StaLfDao staLfDao;
    @Autowired
    private OdoDao odoDao;
    @Autowired
    private CustomsDeclarationDao customsDeclarationDao;
    @Autowired
    private CustomsDeclarationManager customsDeclarationManager;
    @Autowired
    private CustomsDeclarationLineDao customsDeclarationLineDao;

    @Autowired
    private OdoLineDao odoLineDao;
    @Autowired
    private OdoManager odoManager;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private SkuDeclarationDao skuDeclarationDao;

    @Override
    public void sendResponseInfoToHub4() {
        List<String> dsList = intfcCfmDao.findIntfcCfmDataSource(new SingleColumnRowMapper<String>(String.class));
        if (dsList != null && dsList.size() > 0) {
            for (String dataSource : dsList) {
                try {
                    if (dataSource != null && dataSource.equals("WMS3")) {
                        sendWMSData(dataSource);
                    } else if (dataSource != null && dataSource.equals("other")) {
                    }else {
                        sendData(dataSource);
                    }
                } catch (Exception e) {
                    log.error("sendResponseInfoToHub4 error:", e);
                }

            }
        }

    }

    public void sendWMSData(String dataSource) {
        // 状态为5或者10
        List<IntfcCfm> icList = intfcCfmDao.findIntfcCfmByNeedSend(dataSource, 81, new BeanPropertyRowMapperExt<IntfcCfm>(IntfcCfm.class));
        if (icList != null && icList.size() != 0) {
            try {
                sendWMSInBoundData(icList);
            } catch (Exception e) {
                log.error("sendWMSData ", e);
            }
        }

        icList = intfcCfmDao.findIntfcCfmByNeedSend(dataSource, 101, new BeanPropertyRowMapperExt<IntfcCfm>(IntfcCfm.class));
        if (icList != null && icList.size() != 0) {
            try {
                sendWMSOutBoundData(icList);

            } catch (Exception e) {
                log.error("sendWMSOutBoundData ", e);
            }
        }
    }

    private void sendData(String dataSource) {
        List<IntfcCfm> icList = intfcCfmDao.getIntfcCfmByNeedSend(dataSource, new BeanPropertyRowMapperExt<IntfcCfm>(IntfcCfm.class));
        if (icList == null || icList.size() == 0) {
            return;
        }

        List<IntfcCfmCommand> outBoundList = new ArrayList<IntfcCfmCommand>();// 出库
        List<IntfcCfmCommand> inBoundList = new ArrayList<IntfcCfmCommand>();// 入库

        List<IntfcCfm> outBoundIcList = new ArrayList<IntfcCfm>();// 出库
        List<IntfcCfm> inBoundIcList = new ArrayList<IntfcCfm>();// 入库

        // 封装明细
        for (IntfcCfm ic : icList) {
            IntfcCfmCommand icc = new IntfcCfmCommand();
            BeanUtils.copyProperties(ic, icc);
            // StockTransApplication sta = staDao.getByCode(ic.getWmsCode());
            // StaLf sl = staLfDao.getStaLfByStaId(sta.getId());

            // 封装商品明细
            List<IntfcLineCfm> ilcList = intfcLineCfmDao.getIntfcLineCfmByIcId(ic.getId());
            if (ilcList != null && ilcList.size() > 0) {
                List<IntfcLineCfmCommand> ilccList = new ArrayList<IntfcLineCfmCommand>();
                for (IntfcLineCfm ilc : ilcList) {
                    IntfcLineCfmCommand ilcc = new IntfcLineCfmCommand();
                    BeanUtils.copyProperties(ilc, ilcc);
                    Sku sku = skuDao.getByCode(ilc.getUpc());
                    ilcc.setSkuCode(sku.getCode());
                    ilcc.setUpc(sku.getExtensionCode2());
                    ilcc.setExtCode1(sku.getExtensionCode1());
                    ilcc.setExtCode3(sku.getExtensionCode3());
                    // if (sl != null) {
                    // String s = sku.getExtensionCode1();
                    // ilcc.setBarCode(StringUtil.isEmpty(s) ? null : s.replace("-", ""));
                    // } else {
                    ilcc.setBarCode(sku.getBarCode());
                    // }
                    ilccList.add(ilcc);
                }
                icc.setIlcList(ilccList);
            }

            // 封装发票
            List<IntfcInvoiceCfm> iicList = intfcInvoiceCfmDao.getIntfcInvoiceCfmByIcId(ic.getId());
            if (iicList != null && iicList.size() > 0) {
                List<IntfcInvoiceCfmCommand> invoiceList = new ArrayList<IntfcInvoiceCfmCommand>();
                for (IntfcInvoiceCfm iic : iicList) {
                    IntfcInvoiceCfmCommand iicc = new IntfcInvoiceCfmCommand();
                    BeanUtils.copyProperties(iic, iicc);
                    // 封装发票明细行
                    List<IntfcInvoiceLineCfm> iilList = intfcInvoiceLineCfmDao.getIntfcInvoiceLineCfmByIicId(iic.getId());
                    if (iilList != null && iilList.size() > 0) {
                        List<IntfcInvoiceLineCfmCommand> invoiceLineList = new ArrayList<IntfcInvoiceLineCfmCommand>();
                        for (IntfcInvoiceLineCfm iilc : iilList) {
                            IntfcInvoiceLineCfmCommand iilcc = new IntfcInvoiceLineCfmCommand();
                            BeanUtils.copyProperties(iilc, iilcc);
                            invoiceLineList.add(iilcc);
                        }
                        iicc.setIilcList(invoiceLineList);
                    }

                    invoiceList.add(iicc);
                }
                icc.setInvoiceList(invoiceList);
            }

            if (icc.getTransactionType().equals(1)) {
                outBoundList.add(icc);
                outBoundIcList.add(ic);
            } else {
                inBoundList.add(icc);
                inBoundIcList.add(ic);
            }

        }

        // 出库反馈
        if (outBoundList != null && outBoundList.size() > 0) {
            ErpResponse res = adapterManager.outBoundConfirm(outBoundList);
            if (res != null && res.getResult() != null && res.getResult() == 1) {
                modifyIntfcCfmStatus(outBoundIcList, 2, null);
            } else {
                // log.error(res.toString());
                modifyIntfcCfmStatus(outBoundIcList, 3, "ErrCode: " + res.getErrorCode() + ",Msg: " + res.getErrorMsg());
            }
        }

        // 入库反馈
        if (inBoundList != null && inBoundList.size() > 0) {
            ErpResponse res = adapterManager.inBoundConfirm(inBoundList);
            if (res != null && res.getResult() != null && res.getResult() == 1) {
                modifyIntfcCfmStatus(inBoundIcList, 2, null);
            } else {
                // log.error(res.toString());
                modifyIntfcCfmStatus(inBoundIcList, 3, "ErrCode: " + res.getErrorCode() + ",Msg: " + res.getErrorMsg());
            }
        }
    }

    /**
     * 更新状态
     * 
     * @param icList
     * @param status
     * @param msg
     */
    private void modifyIntfcCfmStatus(List<IntfcCfm> icList, Integer status, String msg) {
        // 更新状态
        for (IntfcCfm ic : icList) {
            try {
                wmsOrderServiceToHub4Manager.modifyIntfcCfmStatusById(ic.getId(), status, msg);
            } catch (Exception e) {
                try {
                    wmsOrderServiceToHub4Manager.modifyIntfcCfmStatusById(ic.getId(), 1, e.getMessage());
                } catch (Exception e2) {
                    log.error("modifyIntfcCfmStatusById error:", e2);
                }
            }
        }
    }

    @Override
    public synchronized void sendPinganConverToHub4() {
        List<WhPingAnCoverCommand> list = pinganConverDao.findPingAnCoverToHub4(new BeanPropertyRowMapper<WhPingAnCoverCommand>(WhPingAnCoverCommand.class));
        Response re = null;
        WhPingAnCover pa = null;
        for (WhPingAnCoverCommand whPingAnCoverCommand : list) {
            try {
                re = adapterManager.pingAnInsurance(whPingAnCoverCommand);
                if (re != null && re.getResult() == 1) {
                    pa = pinganConverDao.getByPrimaryKey(whPingAnCoverCommand.getId());// 投保成功，更新状态为2
                    pa.setStatus(2);
                    pa.setCoverNo(re.getOrderResponse().get(0).getPreOrderCode());
                    pa.setCoverMoney(re.getOrderResponse().get(0).getStoreCode());
                } else if (re == null) {
                    log.error("adapter response null:" + whPingAnCoverCommand.getStaCode());
                } else {
                    if (re != null && re.getErrorCode().equals("666666")) {
                        pa = pinganConverDao.getByPrimaryKey(whPingAnCoverCommand.getId());// 投保成功，更新状态为2
                        pa.setStatus(2);
                        pa.setCoverNo(re.getOrderResponse().get(0).getPreOrderCode());
                        pa.setCoverMoney(re.getOrderResponse().get(0).getStoreCode());
                    } else {
                        pa = pinganConverDao.getByPrimaryKey(whPingAnCoverCommand.getId());
                        pa.setCoverErrorCode(re.getErrorCode() + ":" + re.getErrorMsg());
                        if (pa.getCoverErrorNumber() == null) {
                            pa.setCoverErrorNumber(0);
                        }
                        if (pa.getCoverErrorNumber() < 3) {
                            pa.setCoverErrorNumber(pa.getCoverErrorNumber() + 1);// 增加错误次数
                            if (pa.getCoverErrorNumber() == 3) {
                                pa.setStatus(3);// 失败3次后不再继续投保
                            }
                        }
                        log.error("pingAn insurance failure: " + whPingAnCoverCommand.getStaCode() + "..." + re.getErrorMsg());
                    }
                }
                pinganConverDao.save(pa);
            } catch (Exception e) {
                log.error("pingAn insurance failure: " + whPingAnCoverCommand.getStaCode(), e);
            }
        }
    }

    public void sendWMSInBoundData(List<IntfcCfm> icList) {
        if (icList == null || icList.size() == 0) {
            return;
        }

        List<IntfcLineCfm> list = null;
        for (IntfcCfm ic : icList) {
            List<IntfcLineCfm> ilcList = intfcLineCfmDao.findIntfcLineCfmByIcId(ic.getId(), new BeanPropertyRowMapperExt<IntfcLineCfm>(IntfcLineCfm.class));
            if (ilcList != null && ilcList.size() > 0) {
                Map<String, List<IntfcLineCfm>> cartonNoMap = new HashMap<String, List<IntfcLineCfm>>();
                // 差异异常
                Map<String, List<OdoLine>> odoLineMap = new HashMap<String, List<OdoLine>>();
                for (IntfcLineCfm ilc : ilcList) {
                    if (cartonNoMap.containsKey(ilc.getCartonNo())) {
                        list = cartonNoMap.get(ilc.getCartonNo());
                        list.add(ilc);
                    } else {
                        list = new ArrayList<IntfcLineCfm>();
                        list.add(ilc);
                        cartonNoMap.put(ilc.getCartonNo(), list);
                    }
                    list = null;
                }
                StockTransApplication sta = staDao.getByCode(ic.getWmsCode());
                String code = sta.getSlipCode1() != null ? sta.getSlipCode1() : sta.getSlipCode2();
                Odo odo = odoDao.findOdOByCode(code);
                if (odo != null) {
                    Integer status = null;
                    if (odo != null) {
                        status = odo.getStatus();
                        if (status == 4 || status == 5) {
                            status = 2;
                        } else if (status == 7) {
                            status = 3;
                        }
                    }
                    List<OdoLine> odoLineList = odoLineDao.findOdOLineByCodeOdOId(odo.getId(), status, new BeanPropertyRowMapper<OdoLine>(OdoLine.class));
                    if (odoLineList != null && odoLineList.size() > 0) {
                        for (OdoLine odoLine : odoLineList) {
                            if (cartonNoMap.containsKey(odoLine.getCode())) {
                                List<IntfcLineCfm> intfcLineCfmList = cartonNoMap.get(odoLine.getCode());
                                Sku sku = skuDao.getByPrimaryKey(odoLine.getSkuId());
                                for (IntfcLineCfm line : intfcLineCfmList) {
                                    if (line.getUpc().equals(sku.getCode())) {
                                        List<OdoLine> odoLineList1 = new ArrayList<OdoLine>();
                                        if (odoLineMap.containsKey(odoLine.getCode())) {
                                            odoLineList1 = odoLineMap.get(odoLine.getCode());
                                            odoLine.setQty(line.getActualQty());
                                            odoLineList1.add(odoLine);
                                        } else {
                                            odoLine.setQty(line.getActualQty());
                                            odoLineList1.add(odoLine);
                                            odoLineMap.put(odoLine.getCode(), odoLineList1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // 处理差异部分
                    if (!odoLineMap.isEmpty()) {
                        for (Map.Entry<String, List<OdoLine>> map : odoLineMap.entrySet()) {
                            OdoLine odoLine = new OdoLine();
                            if (status == 3) {
                                odoLine.setCode(map.getKey() + "_NOTFINISH");
                            } else {
                                odoLine.setCode(map.getKey() + "_DIFF");
                            }
                            List<OdoLine> odoLineList1 = map.getValue();
                            odoManager.saveOdoLine(odoLineList1, odoLine, odo);
                            /*
                             * for (OdoLine line : odoLineList1) { if (odo.getStatus() == 4 ||
                             * odo.getStatus() == 5) { odoLine.setType(3); } else if
                             * (odo.getStatus() == 7) { odoLine.setType(4); }
                             * odoLine.setSkuId(line.getSkuId()); odoLine.setQty(line.getQty());
                             * odoLine.setOdoId(odo.getId()); odoLine.setVersion(0);
                             * 
                             * //odoLineDao.save(odoLine); }
                             */
                        }
                        if (odo.getStatus() == 4 || odo.getStatus() == 5) {
                            odoManager.updateOdoStatus(odo.getCode(), 5, ic.getId());
                        } else if (odo.getStatus() == 7) {
                            odoManager.updateOdoStatus(odo.getCode(), 8, ic.getId());
                        }
                    } else {
                        List<OdoLine> odoLine = odoLineDao.findOdOLineByOdOId(odo.getId(), status, new BeanPropertyRowMapper<OdoLine>(OdoLine.class));
                        if (odoLine != null && odoLine.size() > 0) {
                            StockTransApplication sto = null;
                            Boolean flag = true;
                            for (OdoLine line : odoLine) {
                                sto = staDao.findBySlipCodeBySlipCode(line.getCode());
                                if (sto != null && !sto.getStatus().equals(StockTransApplicationStatus.FINISHED)) {
                                    flag = false;
                                    break;
                                } else {
                                    List<StaLineCommand> staLineList = staLineDao.findStaLineListByStaId2(sto.getId());
                                    if (staLineList != null && staLineList.size() > 0) {
                                        flag = false;
                                        break;
                                    }
                                }
                            }
                            if (flag) {
                                odoDao.updateOdoStatus(odo.getCode(), 10);
                            } else {
                                if (odo.getStatus() == 4 || odo.getStatus() == 5) {
                                    odoManager.updateOdoStatus(odo.getCode(), 5, ic.getId());
                                } else if (odo.getStatus() == 7) {
                                    odoManager.updateOdoStatus(odo.getCode(), 8, ic.getId());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 出库完成反馈
     * 
     * @param icList
     */
    public void sendWMSOutBoundData(List<IntfcCfm> icList) {
        if (icList == null || icList.size() == 0) {
            return;
        }

        Map<String, Long> skuIdMap = new HashMap<String, Long>();
        for (IntfcCfm ic : icList) {
            try {

                List<IntfcLineCfm> ilcList = intfcLineCfmDao.findIntfcLineCfmByIcId(ic.getId(), new BeanPropertyRowMapperExt<IntfcLineCfm>(IntfcLineCfm.class));
                if (ilcList != null && ilcList.size() > 0) {
                    StockTransApplication sta = staDao.getByCode(ic.getWmsCode());
                    Odo odo = odoDao.findOdOByCode(sta.getRefSlipCode());
                    if (odo != null) {
                        if (odo.getStatus() != 2) {
                            continue;
                        }

                        List<OdoLine> odoLineList = new ArrayList<OdoLine>();
                        for (IntfcLineCfm ilc : ilcList) {
                            OdoLine odoLine = new OdoLine();
                            String code = ilc.getOutboundBoxCode();
                            if (code == null) {
                                code = odo.getCode() + "IN";
                            }
                            odoLine.setCode(code);
                            odoLine.setOdoId(odo.getId());
                            odoLine.setQty(ilc.getActualQty());
                            Long skuId = skuIdMap.get(ilc.getUpc());
                            if (skuId == null) {
                                Sku sku = skuDao.getByCode(ilc.getUpc());
                                skuIdMap.put(ilc.getUpc(), sku.getId());
                                skuId = skuIdMap.get(ilc.getUpc());
                            }
                            odoLine.setSkuId(skuId);
                            odoLine.setType(2);
                            odoLine.setVersion(0);
                            odoLineList.add(odoLine);
                            // odoLineDao.save(odoLine);
                        }
                        // odo.setStatus(3);
                        // odoDao.save(odo);
                        odoManager.modifyOdoOutSendStatus(ic.getId(), odo.getId(), odoLineList);
                    }
                }
            } catch (Exception e) {
                log.error("sendWMSOutBoundData error id:" + ic.getId() + " msg:", e);
                wmsOrderServiceToHub4Manager.modifyIntfcCfmStatusById(ic.getId(), 3, "反馈失败：" + e.getMessage());
            }
        }
    }
    @Override
    public void sendQianHaiOrderToHub4() {
        List<CustomsDeclarationCommand> cdcList = customsDeclarationDao.findCustomsDeclarationByNeedSend(new BeanPropertyRowMapper<CustomsDeclarationCommand>(CustomsDeclarationCommand.class));
        if (cdcList == null || cdcList.size() == 0) {
            return;
        }
        for (CustomsDeclarationCommand cdc : cdcList) {
            List<CustomsDeclarationLineCommand> cdlcList = customsDeclarationLineDao.findCustomsDeclarationLineByCdId(cdc.getId(), new BeanPropertyRowMapper<CustomsDeclarationLineCommand>(CustomsDeclarationLineCommand.class));

            cdc.setCdlcList(cdlcList);
            try {
                com.baozun.bizhub.model.qianhai.response.Response re = adapterManager.pushQianHaiInOrOutOrderToHub4(cdc);
                if (re == null) {
                    customsDeclarationManager.changeCustomsDeclarationStatus(cdc.getId(), 3, "接口反馈NULL");
                } else if ("1".equals(re.getResult())) {
                    customsDeclarationManager.changeCustomsDeclarationStatus(cdc.getId(), 2, null);
                } else {
                    customsDeclarationManager.changeCustomsDeclarationStatus(cdc.getId(), 3, "errorCode:" + re.getErrorCode() + " ; errorMsg:" + re.getErrorMsg());
                }
            } catch (Exception e) {
                customsDeclarationManager.changeCustomsDeclarationStatus(cdc.getId(), 3, e.getMessage());
            }
        }

    }

    @Override
    public void sendQianHaiSkuInfoToHub4() {
        List<SkuDeclarationCommand> sdcList = skuDeclarationDao.findNeedPushSkuInfo(new BeanPropertyRowMapper<SkuDeclarationCommand>(SkuDeclarationCommand.class));
        if (sdcList == null || sdcList.size() == 0) {
            return;
        }
        for (SkuDeclarationCommand sdc : sdcList) {

            try {
                com.baozun.bizhub.model.qianhai.response.Response re = adapterManager.pushQianHaiSkuInfoToHub4(sdc);
                if (re == null) {
                    customsDeclarationManager.changeSkuDeclarationStatus(sdc.getId(), 3, "接口反馈NULL");
                } else if ("1".equals(re.getResult())) {
                    customsDeclarationManager.changeSkuDeclarationStatus(sdc.getId(), 2, null);
                } else {
                    customsDeclarationManager.changeSkuDeclarationStatus(sdc.getId(), 3, "errorCode:" + re.getErrorCode() + " ; errorMsg:" + re.getErrorMsg());
                }
            } catch (Exception e) {
                customsDeclarationManager.changeSkuDeclarationStatus(sdc.getId(), 3, e.getMessage());
            }
        }
    }

}
