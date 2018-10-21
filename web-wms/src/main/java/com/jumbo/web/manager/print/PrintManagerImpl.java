package com.jumbo.web.manager.print;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.manager.channel.packingPage.PackingPageFactory;
import com.jumbo.manager.channel.packingPage.PackingPageInterface;
import com.jumbo.pac.manager.extsys.wms.rmi.model.SalesTicketLineResp;
import com.jumbo.pac.manager.extsys.wms.rmi.model.SalesTicketResp;
import com.jumbo.pac.manager.extsys.wms.rmi.model.SalesTicketResult;
import com.jumbo.pac.manager.extsys.wms.rmi.model.SalesTicketTenderResp;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.TimeHashMap;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.manager.pda.PdaPickingManager;
import com.jumbo.wms.manager.print.PrintCustomizeManager;
import com.jumbo.wms.manager.print.data.WarehousePrintData;
import com.jumbo.wms.manager.sn.SnManager;
import com.jumbo.wms.manager.vmi.gucciData.GucciManager;
import com.jumbo.wms.manager.warehouse.AutoOutboundTurnboxManager;
import com.jumbo.wms.manager.warehouse.RtwDieKingLineManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.automaticEquipment.WhContainer;
import com.jumbo.wms.model.automaticEquipment.Zoon;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.wms.model.baseinfo.TransSfInfo;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.StaSpecialExecutedCommand;
import com.jumbo.wms.model.jasperReport.ImportEntryListLinesObj;
import com.jumbo.wms.model.jasperReport.ImportEntryListObj;
import com.jumbo.wms.model.jasperReport.ImportHGDEntryListObj;
import com.jumbo.wms.model.jasperReport.InboundDetailObj;
import com.jumbo.wms.model.jasperReport.InventoryOccupay;
import com.jumbo.wms.model.jasperReport.OutBoundPackageInfoObj;
import com.jumbo.wms.model.jasperReport.OutBoundPackingObj;
import com.jumbo.wms.model.jasperReport.OutBoundSendInfo;
import com.jumbo.wms.model.jasperReport.PackingLineObj;
import com.jumbo.wms.model.jasperReport.PackingObj;
import com.jumbo.wms.model.jasperReport.PackingSummaryForNike;
import com.jumbo.wms.model.jasperReport.PickingListObj;
import com.jumbo.wms.model.jasperReport.PoConfirmObj;
import com.jumbo.wms.model.jasperReport.PrintPackingPageParam;
import com.jumbo.wms.model.jasperReport.ProductBarcodeObj;
import com.jumbo.wms.model.jasperReport.ReturnPackingObj;
import com.jumbo.wms.model.jasperReport.SnCardErrorObj;
import com.jumbo.wms.model.jasperReport.SpecialPackagingData;
import com.jumbo.wms.model.jasperReport.SpecialPackagingLineObj;
import com.jumbo.wms.model.jasperReport.SpecialPackagingObj;
import com.jumbo.wms.model.lf.StaLfCommand;
import com.jumbo.wms.model.print.PrintCustomize;
import com.jumbo.wms.model.warehouse.Carton;
import com.jumbo.wms.model.warehouse.GiftLine;
import com.jumbo.wms.model.warehouse.HandOverList;
import com.jumbo.wms.model.warehouse.HandOverListLineCommand;
import com.jumbo.wms.model.warehouse.ImperfectStvCommand;
import com.jumbo.wms.model.warehouse.ImportPrintData;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.PoCommand;
import com.jumbo.wms.model.warehouse.ReturnPackageCommand;
import com.jumbo.wms.model.warehouse.RtwDieking;
import com.jumbo.wms.model.warehouse.SkuImperfectCommand;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StaSpecialExecuteType;
import com.jumbo.wms.model.warehouse.StoProCode;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.TransPackageCommand;
import com.jumbo.wms.model.warehouse.TransTimeType;
import com.jumbo.wms.model.warehouse.VMIBackOrder;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WhInfoTimeRef;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.WhTransAreaNo;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrderCommand;

import loxia.support.jasperreport.ClasspathJasperPrinter;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class PrintManagerImpl implements PrintManager {

    protected static final Logger log = LoggerFactory.getLogger(PrintManager.class);

    private static final long serialVersionUID = -7499549249444743155L;
    @Autowired
    private ChannelManager channelManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private PackingPageFactory packingPageFactory;
    @Autowired
    private WarehousePrintData warehousePrintData;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private SnManager snManager;
    @Autowired
    private WareHouseManagerQuery whQuery;
    @Autowired
    private AutoOutboundTurnboxManager autoOutboundTurnboxManager;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private PdaPickingManager pdaPickingManager;
    @Autowired
    private GucciManager gucciManager;
    @Autowired
    private RtwDieKingLineManager rtwDieKingLineManager;
    @Autowired
    private PrintCustomizeManager printCustomizeManager;
    /**
     * 月结账号缓存
     */
    static TimeHashMap<String, String> sfJcustIdMap = new TimeHashMap<String, String>();
    static TimeHashMap<String, String> sfMACAOJcustIdMap = new TimeHashMap<String, String>();

    /**
     * 打印配货清单 销售模式2
     * 
     * @throws Exception
     */
    public JasperPrint printPickingListMode2(PickingListCommand plCmd, Long warehouseOuid, String psize) throws Exception {
        // return printPickingListMode1(plCmd, warehouseOuid);
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<Object> fieldList = new ArrayList<Object>();
        List<PickingListCommand> pickingList = warehousePrintData.findPickingListByPickingId(plCmd.getId(), plCmd.getPickZoneId(), warehouseOuid, null);
        parameterMap = setPickingListHead(plCmd, warehouseOuid, null);
        if (pickingList == null || pickingList.isEmpty() || pickingList.size() == 0) {
            log.debug("pickingList is null");
            pickingList = new ArrayList<PickingListCommand>();
            pickingList.add(new PickingListCommand());
        }
        fieldList = setPickingListDetail(pickingList);
        JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(Constants.PRINT_TEMPLATE_FLIENAME + "pickingList3.jasper");
        cjp.initializeReport(parameterMap, ds);
        return cjp.print();
    }

    public JasperPrint printPickingListMode1(PickingListCommand plCmd, Long warehouseOuid, Long userId, String flag) throws Exception {
        try {
            // 重置配货批中有取消订单时需要拣货的数量
            pdaPickingManager.resetPickingQtyByCancelSta(plCmd.getId());
        } catch (Exception e) {
            log.error(plCmd.getId() + "", e);
        }
        if (plCmd.getPackingType() != null && plCmd.getPackingType() == 1) {
            // 打印拣货模式3
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            List<Object> fieldList = new ArrayList<Object>();
            List<PickingListCommand> pickingList = warehousePrintData.findPickingListByPickingId3(plCmd.getId(), plCmd.getPickZoneId(), warehouseOuid, flag);
            parameterMap = setPickingListHead(plCmd, warehouseOuid, null);
            if (pickingList == null || pickingList.isEmpty()) {
                log.debug("pickingList is null");
                pickingList = new ArrayList<PickingListCommand>();
                pickingList.add(new PickingListCommand());
            }
            channelManager.logPackingPagePrint(plCmd.getCode(), userId, WhInfoTimeRefNodeType.PRING_PICKING3);
            fieldList = setPickingListDetail3(pickingList, plCmd.getId());
            JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
            ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(Constants.PRINT_TEMPLATE_FLIENAME + "pickingList3.jasper");
            cjp.initializeReport(parameterMap, ds);
            return cjp.print();
        } else {
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            List<Object> fieldList = new ArrayList<Object>();
            List<PickingListCommand> pickingList = warehousePrintData.findPickingListByPickingId(plCmd.getId(), plCmd.getPickZoneId(), warehouseOuid, flag);
            String whZoonCode = null;
            if (plCmd.getPickZoneId() != null && pickingList != null && pickingList.size() > 0) {
                whZoonCode = pickingList.get(0).getWhZone();
            }
            parameterMap = setPickingListHead(plCmd, warehouseOuid, whZoonCode);
            if (pickingList == null || pickingList.isEmpty()) {
                log.debug("pickingList is null");
                pickingList = new ArrayList<PickingListCommand>();
                pickingList.add(new PickingListCommand());
            }
            channelManager.logPackingPagePrint(plCmd.getCode(), userId, WhInfoTimeRefNodeType.PRING_PICKING1);

            fieldList = setPickingListDetail(pickingList);
            JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
            ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(Constants.PRINT_TEMPLATE_FLIENAME + "pickingList1.jasper");
            cjp.initializeReport(parameterMap, ds);
            return cjp.print();
        }
    }

    /**
     * 配货清单打印，填充iReport Details数据
     * 
     * @param pickingList
     * @return
     */
    private List<Object> setPickingListDetail(List<PickingListCommand> pickingList) {
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = null;
        for (PickingListCommand p : pickingList) {
            map = new HashMap<String, Object>();
            map.put("idx1", p.getIdx1());//
            map.put("location", p.getLocationcode());
            map.put("pickZone", p.getPickZoneCode());
            map.put("strExpireDate", p.getStrExpireDate());
            map.put("jmskucode", p.getJmskucode());
            map.put("barCode", p.getBarcode());
            map.put("barCode1", p.getBarcode().substring(0, p.getBarcode().length() - 4));
            map.put("barCode2", p.getBarcode().substring(p.getBarcode().length() - 4, p.getBarcode().length()));
            map.put("jmCode", p.getSupplierCode());
            map.put("SKUName", p.getSkuname());
            map.put("keyProperty", p.getKeyproperties());
            map.put("quantity", p.getQuantity());
            map.put("isCod", p.getIsCod());
            // map.put("whZone", p.getWhZone());
            list.add(map);
            // 拣货单商品多条码打印
            if (!StringUtil.isEmpty(p.getOtherBarcode())) {
                String[] barCodes = p.getOtherBarcode().split(",");
                for (String code : barCodes) {
                    map = new HashMap<String, Object>();
                    map.put("barCode", code);
                    map.put("barCode1", code.substring(0, code.length() - 4));
                    map.put("barCode2", code.substring(code.length() - 4, code.length()));
                    list.add(map);
                }
            }
        }
        return list;
    }

    public Warehouse queryTotalPickingList(Long ouId) {
        return wareHouseManagerQuery.queryTotalPickingList(ouId);
    }

    /**
     * vmi退仓拣货单，填充iReport Details数据
     * 
     * @param vmiList
     * @return
     */
    private List<Object> setVMIListDetail(List<VMIBackOrder> vmiList) {
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = null;
        for (VMIBackOrder vmi : vmiList) {
            map = new HashMap<String, Object>();
            map.put("district", vmi.getDistrict());// 出库库区
            map.put("locationcode", vmi.getLocationcode());// 出库库位
            map.put("pickzonecode", vmi.getPickzonecode());// 拣货区域编码
            map.put("skucode", vmi.getSkucode());// sku编码
            map.put("barcode", vmi.getBarcode());// 条形码
            map.put("barCode1", vmi.getBarcode().substring(0, vmi.getBarcode().length() - 4));
            map.put("barCode2", vmi.getBarcode().substring(vmi.getBarcode().length() - 4, vmi.getBarcode().length()));
            map.put("jmcode", vmi.getJmcode());// 商品货号
            map.put("keyword", vmi.getKeyproperties());// 关键属性
            map.put("strexpiredate", vmi.getStrexpiredate());// 过期时间
            map.put("quantity", vmi.getQuantity());// 数量
            map.put("vas", vmi.getVas());// 数量
            list.add(map);
        }
        return list;
    }

    /**
     * 拣货单打印3，填充iReport Details数据
     * 
     * @param pickingList
     * @return
     */
    private List<Object> setPickingListDetail3(List<PickingListCommand> pickingList, Long pkId) {
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = null;
        Map<String, String> validateMap = new HashMap<String, String>();
        List<StaLineCommand> memoList = wareHouseManagerQuery.findGiftMemoByPkId(pkId);
        List<PickingListCommand> pick = new ArrayList<PickingListCommand>();
        // 设置备注
        while (true) {
            // 遍历stv_line列表
            for (PickingListCommand p : pickingList) {
                // 遍历giftLine列表，每一行数量都是1
                for (int i = 0; i < memoList.size(); i++) {
                    // 判断giftLine属于哪一个作业单的。 因为stv_line没法和sta_line关联，所以做个中间查询
                    List<StaLineCommand> lineList = wareHouseManagerQuery.findStaLineByGiftMemo(p.getSlipCode(), memoList.get(i).getId());
                    if (lineList == null || lineList.size() == 0) {
                        continue;
                    }
                    // stvLine列表中的sku有特殊处理
                    if (memoList.get(i).getSkuId().equals(p.getSkuId())) {
                        // 列表中数量大于1，则扣减数量，设置备注，复制对象到新的集合里。剔除giftLine集合跳出循环，重新遍历
                        if (p.getQuantity() - 1 > 0) {
                            p.setQuantity(p.getQuantity() - 1);
                            p.setMemo(memoList.get(i).getExtMemo());
                            memoList.remove(i);
                            PickingListCommand cp = new PickingListCommand();
                            org.springframework.beans.BeanUtils.copyProperties(p, cp);
                            cp.setQuantity(1);
                            pick.add(cp);
                            break;
                        } else {
                            // 列表中数量等于1，设置备注，复制对象到新的集合里。剔除stvLine集合，剔除giftLine集合，跳出循环，重新遍历
                            p.setMemo(memoList.get(i).getExtMemo());
                            PickingListCommand cp = new PickingListCommand();
                            org.springframework.beans.BeanUtils.copyProperties(p, cp);
                            pick.add(cp);
                            memoList.remove(i);
                            pickingList.remove(p);
                            break;

                        }
                    }
                }
                break;
            }
            // 当giftLine列表都被剔除完，则把剩余的 stv_line 复制到新的集合里。
            if (memoList.size() == 0) {
                for (PickingListCommand p : pickingList) {
                    PickingListCommand cp = new PickingListCommand();
                    org.springframework.beans.BeanUtils.copyProperties(p, cp);
                    pick.add(cp);
                    pickingList.remove(p);
                }
            }
            // 当 stv_line 集合为空，则跳出循环
            if (pickingList == null || pickingList.size() == 0) {
                break;
            }
        }
        // for (PickingListCommand p : pickingList) {
        // // 备注逻辑： 查询出批次下所有备注。 按照商品和数量匹配，逐行扣减备注
        // String temp = p.getSkuId() + "_" + p.getQuantity();
        // for (int i = 0; i < memoList.size(); i++) {
        // String temp2 = memoList.get(i).getId() + "_" + memoList.get(i).getQuantity();
        // if (temp.equals(temp2)) {
        // p.setMemo(memoList.get(i).getExtMemo());
        // memoList.remove(i);
        // break;
        // }
        // }
        // }
        // 根据拣货区域编码，库位编码，memo排序

        Collections.sort(pick, new Comparator<PickingListCommand>() {
            public int compare(PickingListCommand o1, PickingListCommand o2) {
                if (o1.getPickZoneCode() == null) {
                    return 1;
                } else if (o2.getPickZoneCode() == null) {
                    return -1;
                } else {
                    return o1.getPickZoneCode().compareTo(o2.getPickZoneCode());
                }
            }
        });

        Collections.sort(pick, new Comparator<PickingListCommand>() {
            public int compare(PickingListCommand o1, PickingListCommand o2) {
                if (o1.getLocationcode() == null) {
                    return 1;
                } else if (o2.getLocationcode() == null) {
                    return -1;
                } else {
                    return o1.getLocationcode().compareTo(o2.getLocationcode());
                }
            }

        });
        Collections.sort(pick, new Comparator<PickingListCommand>() {
            public int compare(PickingListCommand o1, PickingListCommand o2) {
                if (o1.getMemo() == null) {
                    return 1;
                } else if (o2.getMemo() == null) {
                    return -1;
                } else {
                    return o1.getMemo().compareTo(o2.getMemo());
                }
            }
        });

        Collections.sort(pick, new Comparator<PickingListCommand>() {
            public int compare(PickingListCommand o1, PickingListCommand o2) {
                return o1.getSlipCode().compareTo(o2.getSlipCode());
            }
        });

        // 赋值
        for (PickingListCommand p : pick) {
            map = new HashMap<String, Object>();
            if (validateMap.containsKey(p.getSlipCode())) {
                map.put("slipCode", "");
                // map.put("slipCode", p.getSlipCode());
            } else {
                map.put("slipCode", p.getSlipCode());
                validateMap.put(p.getSlipCode(), p.getMemo());
            }
            map.put("remark", p.getMemo());
            map.put("location", p.getLocationcode());
            map.put("pickZone", p.getPickZoneCode());
            map.put("strExpireDate", p.getStrExpireDate());
            map.put("jmskucode", p.getJmskucode());
            map.put("barCode", p.getBarcode());
            map.put("barCode1", p.getBarcode().substring(0, p.getBarcode().length() - 4));
            map.put("barCode2", p.getBarcode().substring(p.getBarcode().length() - 4, p.getBarcode().length()));
            map.put("jmCode", p.getSupplierCode());
            map.put("SKUName", p.getSkuname());
            map.put("keyProperty", p.getKeyproperties());
            map.put("quantity", p.getQuantity());
            map.put("isCod", p.getIsCod());
            // map.put("whZone", p.getWhZone());
            list.add(map);
        }
        return list;
    }

    /**
     * 配货清单打印，填充iReport Head数据
     * 
     * @return
     * @throws Exception
     */
    private Map<String, Object> setPickingListHead(PickingListCommand plCmd, Long warehouseOuid, String whZoonCode) throws Exception {
        String psizes;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long pcheckmode = wareHouseManager.findPickingListCheckmodeByPickId(plCmd.getId());
        Long special = wareHouseManager.findPickingListSpecialTypeByPickId(plCmd.getId());
        if (pcheckmode == PickingListCheckMode.PICKING_SECKILL.getValue()) {
            psizes = "秒";
        } else if (pcheckmode == PickingListCheckMode.PICKING_GROUP.getValue()) {
            psizes = "团";
        } else if (pcheckmode == PickingListCheckMode.PICKING_PACKAGE.getValue()) {
            psizes = "套";
        } else {
            if (special == null) {
                psizes = wareHouseManager.findPickingListPSizesByPickId(plCmd.getId());
            } else {
                if (special == 1) {
                    psizes = wareHouseManager.findPickingListPSizesByPickId(plCmd.getId());
                    psizes += "/特";
                } else {
                    psizes = wareHouseManager.findPickingListPSizesByPickId(plCmd.getId());
                    psizes += "/包";
                }
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        OperationUnit whou = warehousePrintData.getOperationUnitById(warehouseOuid);
        PickingList pl = warehousePrintData.getPickingListById(plCmd.getId());
        WhInfoTimeRef wtr = wareHouseManager.findWhInfoTimeRefById(pl.getCode(), WhInfoTimeRefBillType.STA_PICKING);
        String packageSku = "";
        if (wtr != null) {
            calendar.setTime(wtr.getExecutionTime());
            map.put("printTime", df.format(wtr.getExecutionTime()));
        } else {
            calendar.setTime(new Date());
            map.put("printTime", df.format(new Date()));
        }
        // 获取套装主商品条码
        if (!StringUtil.isEmpty(plCmd.getPackageSku())) {
            // 解析字段，重新拼接字符串
            String array[] = plCmd.getPackageSku().split(",");
            for (int i = 0; i < array.length; i++) {
                Long skuId = Long.parseLong(array[i].substring(0, array[i].indexOf(";")));// 商品ID
                String barcode = wareHouseManager.findSkuBarcodeById(skuId);
                if (barcode != null) {
                    String qty = array[i].substring(array[i].indexOf(";") + 1, array[i].length());// 商品数量
                    packageSku += barcode + "【" + qty + "】" + "|";
                }
            }
            packageSku = "主商品：" + packageSku.substring(0, packageSku.length() - 1);
            map.put("packageSku", packageSku);
        } else {
            map.put("packageSku", "");
        }
        calendar.add(Calendar.HOUR, 2);// 创建时间+2小时
        String pickingBatchBarCode = null;
        if (whZoonCode != null) {
            map.put("isShow", true);
            map.put("whZoneBarCode", whZoonCode);
            map.put("whZone", whZoonCode);
            // if (plCmd.getPickZoneId() != null && plCmd.getPickZoneId() != 0) {
            pickingBatchBarCode = autoOutboundTurnboxManager.pickingBatchBarCode(pl.getId(), whZoonCode);
            // }
        } else {
            map.put("isShow", false);
            map.put("whZone", "");
        }
        map.put("outWarehouse", whou.getName());
        if (pickingBatchBarCode != null && !pickingBatchBarCode.equals(pl.getCode())) {
            map.put("whZone1", "#" + whZoonCode);
            map.put("dphCode", pickingBatchBarCode);
        } else {
            map.put("dphCode", pl.getCode());
        }
        map.put("dphCode1", pl.getCode().substring(0, pl.getCode().length() - 4));
        map.put("dphCode2", pl.getCode().substring(pl.getCode().length() - 4, pl.getCode().length()));
        map.put("planBillCount", pl.getPlanBillCount() + "/");
        map.put("planSkuQty", pl.getPlanSkuQty());
        map.put("lpcode", pl.getLpcode() + "/");
        map.put("isBigBox", psizes);
        map.put("isPreSale", pl.getIsPreSale());

        map.put("createTime", (null != pl.getCreateTime() ? df.format(pl.getCreateTime()) : ""));
        map.put("doneTime", df.format(calendar.getTime()));
        map.put("city", pl.getCity() == null ? "" : pl.getCity() + "/");

        // oto
        String toLocation = pl.getToLocation();
        if (toLocation != null && !"".equalsIgnoreCase(toLocation)) {
            toLocation = wareHouseManager.findAllOptionListByOptionKey(toLocation, "otoDestination");
            if (toLocation != null) {
                map.put("toLocation", toLocation);
            } else {
                log.debug("Oto order store code without maintenance");
                map.put("toLocation", pl.getToLocation() == null ? "" : pl.getToLocation() + "");
            }
        } else {
            map.put("toLocation", pl.getToLocation() == null ? "" : pl.getToLocation() + "");
        }

        if (pl.getIsPostpositionExpressBill()) {
            map.put("isPEB", "后面/");
        }
        if (!pl.getIsPostpositionExpressBill()) {
            map.put("isPEB", "");
        }
        if (pl.getIsPostpositionPackingPage()) {
            map.put("isPPP", "后装");
        }
        if (!pl.getIsPostpositionPackingPage()) {
            map.put("isPPP", "");
        }
        map.put("invoiceNum", pl.getInvoiceNum() == null ? "" : pl.getInvoiceNum() + "");// 发票数量
        // 商品大小
        if (pl.getSkuSizeId() != null) {
            SkuSizeConfig skusize = wareHouseManager.getSkuSizeConfigById(pl.getSkuSizeId());
            map.put("skuSize", skusize.getName().subSequence(0, 1) + "-");
        } else {
            map.put("skuSize", "");
        }
        // 商品分配
        if (pl.getCategoryId() != null) {
            String skucName = wareHouseManager.findSkuCategoriesNameById(pl.getCategoryId());
            map.put("skuCategory", skucName + "/");
        } else {
            map.put("skuCategory", "");
        }
        /*
         * 包装 QS：isSpecialPackaging = true，specialType为空 礼品卡：isSpecialPackaging为false或空，specialType
         * = 1 QS+礼品卡：isSpecialPackaging = true，specialType = 1
         */

        if (pl.getIsSpecialPackaging() != null && pl.getIsSpecialPackaging() && pl.getSpecialType() == null) {
            if (pl.getPickModleType() != null) {// QS
                String codeStr = getCodeStr(pl, warehouseOuid, null);
                map.put("isBigBox", "跨区QS" + codeStr + "/");
            } else {
                map.put("isBigBox", "QS/");
            }
        }

        if (pl.getSpecialType() != null) {
            if ((pl.getIsSpecialPackaging() == null || !pl.getIsSpecialPackaging()) && pl.getSpecialType().getValue() == PickingListCheckMode.DEFAULE.getValue()) {
                if (pl.getPickModleType() != null && pl.getPickModleType() == 6) {// 特殊处理
                    String codeStr = getCodeStr(pl, warehouseOuid, null);
                    map.put("isBigBox", "跨区特" + codeStr + "/");
                } else {
                    map.put("isBigBox", "特/");
                }
            } else if (pl.getIsSpecialPackaging() != null && pl.getIsSpecialPackaging() && pl.getSpecialType().getValue() == PickingListCheckMode.DEFAULE.getValue()) {
                if (pl.getPickModleType() != null && pl.getPickModleType() == 6) {// QS 特殊处理
                    String codeStr = getCodeStr(pl, warehouseOuid, null);
                    map.put("isBigBox", "跨区特+QS" + codeStr + "/");
                } else {
                    map.put("isBigBox", "特+QS/");
                }
            }
        } else if ((pl.getIsSpecialPackaging() == null || !pl.getIsSpecialPackaging()) && pl.getSpecialType() == null) {
            if (pl.getCheckMode() == PickingListCheckMode.DEFAULE) {
                if (pl.getPickModleType() != null && pl.getPickModleType() == 1) {// 多件
                    String codeStr = "";
                    List<StockTransApplication> staList = wareHouseManager.findStaByPickingList(pl.getId(), warehouseOuid);
                    Map<String, String> whList = new HashMap<String, String>();
                    for (StockTransApplication sta : staList) {
                        if (StringUtils.hasLength(sta.getWhZoonList())) {
                            if (sta.getWhZoonList().contains("|")) {
                                String tt = sta.getWhZoonList().replace("|", ",");
                                String[] arrays = tt.split(",");
                                for (String string : arrays) {
                                    whList.put(string, string);
                                }
                            } else {
                                whList.put(sta.getWhZoonList(), sta.getWhZoonList());
                            }
                        }
                    }
                    for (String whId : whList.keySet()) {
                        Long zoonId = Long.parseLong(whId);
                        Zoon zo = wareHouseManager.findZoonById(zoonId);
                        codeStr += zo.getCode() + "-";
                    }
                    if (!"".equals(codeStr)) {
                        codeStr = "(" + codeStr.substring(0, codeStr.length() - 1) + ")";
                    }
                    map.put("isBigBox", "跨区多件" + codeStr + "/");
                } else {
                    map.put("isBigBox", "多件/");
                }
            } else if (pl.getCheckMode() == PickingListCheckMode.PICKING_CHECK) {
                /*
                 * if (pl.getPickModleType() != null && pl.getPickModleType() == 2) {// 单件 String
                 * codeStr = getCodeStr(pl, warehouseOuid, null); map.put("isBigBox", "跨区单件" +
                 * codeStr + "/"); } else { map.put("isBigBox", "单件/"); }
                 */
                map.put("isBigBox", "单件/");
            } else if (pl.getCheckMode() == PickingListCheckMode.PICKING_SECKILL) {
                if (pl.getPickModleType() != null && pl.getPickModleType() == 3) {// 秒杀
                    String codeStr = getCodeStr(pl, warehouseOuid, null);
                    map.put("isBigBox", "跨区秒杀" + codeStr + "/");
                } else {
                    map.put("isBigBox", "秒杀/");
                }
            } else if (pl.getCheckMode() == PickingListCheckMode.PICKING_PACKAGE) {
                if (pl.getPickModleType() != null && pl.getPickModleType() == 5) {// 套装
                    String codeStr = getCodeStr(pl, warehouseOuid, null);
                    map.put("isBigBox", "跨区套装" + codeStr + "/");
                } else {
                    map.put("isBigBox", "套装/");
                }
            } else if (pl.getCheckMode() == PickingListCheckMode.PICKING_GROUP) {
                if (pl.getPickModleType() != null && pl.getPickModleType() == 4) {// 团购
                    String codeStr = getCodeStr(pl, warehouseOuid, null);
                    map.put("isBigBox", "跨区团购" + codeStr + "/");
                } else {
                    map.put("isBigBox", "团购/");
                }
            } else {
                map.put("isBigBox", "");
            }
        }

        // 陆运
        if (pl.getIsRailway() != null) {
            if (pl.getIsRailway()) {
                map.put("isRailway", "陆运");
            } else {
                map.put("isRailway", "");
            }
        } else {
            map.put("isRailway", "");
        }
        // 当日次日
        if (pl.getTransTimeType() != null) {
            if (pl.getTransTimeType() == TransTimeType.SAME_DAY) {
                map.put("transTimeType", "当日");
            } else if (pl.getTransTimeType() == TransTimeType.THE_NEXT_DAY) {
                map.put("transTimeType", "次日");
            } else if (pl.getTransTimeType() == TransTimeType.TIMELY) {
                map.put("transTimeType", "即时达");
            } else if (pl.getTransTimeType() == TransTimeType.THE_NEXT_MORNING) {
                map.put("transTimeType", "次晨达");
            } else {
                map.put("transTimeType", "");
            }
        } else {
            map.put("transTimeType", "");
        }

        map.put("specialPackaging", "");
        map.put("wayBillType", "");
        // if (pl.getTransTimeType() == null) {
        // } else {
        // if (pl.getTransTimeType().getValue() == 1) {
        // map.put("wayBillType", "陆运");
        // } else {
        // if (TransTimeType.SAME_DAY.getValue() ==
        // pl.getTransTimeType().getValue()) {
        // map.put("wayBillType", "当日");
        // } else {
        // map.put("wayBillType", "次日");
        // }
        // }
        // }
        if (pl.getOperator() != null) map.put("operator", pl.getOperator().getUserName());
        return map;
    }

    /**
     * 拣货区域公共方法
     */

    public String getCodeStr(PickingList pl, Long ouid, String type) {
        String codeStr = "";
        List<StockTransApplication> staList = wareHouseManager.findStaByPickingList(pl.getId(), ouid);
        Map<String, String> whList = new HashMap<String, String>();
        for (StockTransApplication sta : staList) {
            if (StringUtils.hasLength(sta.getWhZoonList())) {
                if (sta.getWhZoonList().contains("|")) {
                    String tt = sta.getWhZoonList().replace("|", ",");
                    String[] arrays = tt.split(",");
                    for (String string : arrays) {
                        whList.put(string, string);
                    }
                } else {
                    whList.put(sta.getWhZoonList(), sta.getWhZoonList());
                }
            }
        }
        for (String whId : whList.keySet()) {
            Long zoonId = Long.parseLong(whId);
            Zoon zo = wareHouseManager.findZoonById(zoonId);
            codeStr += zo.getCode() + "-";
        }
        if (!"".equals(codeStr)) {
            codeStr = "(" + codeStr.substring(0, codeStr.length() - 1) + ")";
        }
        // map.put("isBigBox", "跨区多件" + codeStr + "/");
        return codeStr;
    }


    /***
     * 打印装箱订单
     * 
     * @param plCmd
     * @return
     */
    public JasperPrint printPackingPage(Long plid, Long staid, Long userId, Boolean isPostPrint, String paperType) {
        if (isPostPrint == null) {
            isPostPrint = false;
        }
        String templateType = channelManager.findPickingListTemplateType(plid, staid);
        // 获取自定义打印配置
        String customPringCode = channelManager.findPickingListCustomPrintCode(plid, staid);
        if (log.isDebugEnabled()) {
            log.debug("=====find tmplateType=====" + templateType);
        }
        if (paperType != null && !"".equals(paperType)) {
            templateType = paperType;
        }
        PackingPageInterface pp = packingPageFactory.getPackingPage(templateType);
        boolean isPostpositionPage = channelManager.isPostpositionPackingPage(plid, staid, isPostPrint);
        if (log.isDebugEnabled()) {
            log.debug("=====find is isPostpositionPage=========" + isPostpositionPage);
        }
        String mainTemplate = "";
        String subTemplate = "";
        if (isPostpositionPage) {// 获取 前置 OR 后置模板
            // 如果是后置装详清单
            // 获取主模板
            mainTemplate = pp.getPostpositionalMainTemplate();
            // 获取子模板
            subTemplate = pp.getPostpositionalSubTemplate();
        } else {
            // 自定义配置不为空，获取打印模板（默认为前置打印）
            if (customPringCode != null && !"".equals(customPringCode)) {
                PrintCustomize pc = printCustomizeManager.findPrintCustomizeByPrintCode(customPringCode);
                // 获取主模板
                mainTemplate = pc.getMasterTemplet();
                // 获取子模板
                subTemplate = pc.getSubTemplet();
            } else {
                // 获取主模板
                mainTemplate = pp.getMainTemplate();
                // 获取子模板
                subTemplate = pp.getSubTemplate();
            }
        }
        List<PackingObj> dataList = new ArrayList<PackingObj>();

        if (isPostpositionPage) {
            Map<Long, PackingObj> tmpList = channelManager.findPackingPageNoLocation(plid, staid);
            if (tmpList != null) {
                dataList.addAll(tmpList.values());
            }
        } else {
            // 获取打印信息
            if (!pp.isSpecialCustom()) { // 不是特殊定制
                if (pp.isDetialShowLocation()) { // 是否显示库位
                    Map<Long, PackingObj> tmpList = channelManager.findPackingPageShowLocation(plid, staid);
                    if (tmpList != null) {
                        dataList.addAll(tmpList.values());
                    }
                } else {
                    Map<Long, PackingObj> tmpList = channelManager.findPackingPageNoLocation(plid, staid);
                    if (tmpList != null) {
                        dataList.addAll(tmpList.values());
                    }
                }
            } else {
                // 特殊定制
                PrintPackingPageParam param = new PrintPackingPageParam();
                param.setPlId(plid);
                param.setStaId(staid);
                Map<Long, PackingObj> tmpList = pp.getSpecialCustomValue(param);
                if (tmpList != null) {
                    dataList.addAll(tmpList.values());
                }
            }
        }
        if (dataList == null || dataList.isEmpty()) {
            dataList.add(new PackingObj());
        } else {

            greAntiFake(dataList);// 防伪
        }

        channelManager.logPackingPagePrint(plid, userId, WhInfoTimeRefNodeType.PRINT_PICKING_LIST);
        JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        Map<String, Object> map = new HashMap<String, Object>();
        if (isPostpositionPage) {
            map.put("subReport", subTemplate);
        } else if (!isPostpositionPage && (customPringCode != null && !"".equals(customPringCode))) {
            map.put("subReport", subTemplate);
        } else {
            InputStream is;
            try {
                is = this.getClass().getClassLoader().getResource(subTemplate).openStream();
                map.put("subReport", is);
            } catch (IOException e) {
                log.error("", e);
            }
            pp.setUserDefined(map);
        }
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(mainTemplate);
        com.jumbo.web.manager.print.ClasspathJasperPrinter cjps = null;
        if (customPringCode != null && !"".equals(customPringCode)) {
            cjps = new com.jumbo.web.manager.print.ClasspathJasperPrinter(mainTemplate);
            try {
                cjps.initializeReport(map, dataSource);
            } catch (JasperReportNotFoundException e) {
                log.error("", e);
            }
        }
        try {
            if (customPringCode == null || "".equals(customPringCode)) {
                cjp.initializeReport(map, dataSource);
            }
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        }
        try {
            if (cjps != null) {
                return cjps.print();
            } else {
                return cjp.print();
            }
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        }
        return null;

    }

    /***
     * 打印装箱订单
     * 
     * @param plCmd
     * @return
     */
    public JasperPrint printPackingPage(Long plid, Long ppId, Long staid, Long userId, Boolean isPostPrint, Boolean isSpecial, String template) {
        if (log.isDebugEnabled()) {
            log.debug("---O2OQS printPackingPage---" + staid);
        }
        if (isPostPrint == null) {
            isPostPrint = false;
        }
        if (null == isSpecial) {
            isSpecial = false;
        }
        String templateType = "";
        if (true == isSpecial) {
            if (StringUtil.isEmpty(template)) throw new BusinessException();
            templateType = template;
        } else {
            templateType = channelManager.findPickingListTemplateType(plid, staid);
        }
        PackingPageInterface pp = packingPageFactory.getPackingPage(templateType);
        boolean isPostpositionPage = channelManager.isPostpositionPackingPage(plid, staid, isPostPrint);
        String mainTemplate = "";
        String subTemplate = "";
        // 获取 前置 OR 后置模板
        if (isPostpositionPage) {
            // 如果是后置装详清单
            // 获取主模板
            mainTemplate = pp.getPostpositionalMainTemplate();
            // 获取子模板
            subTemplate = pp.getPostpositionalSubTemplate();
        } else {
            // 获取主模板
            mainTemplate = pp.getMainTemplate();
            // 获取子模板
            subTemplate = pp.getSubTemplate();
        }
        List<PackingObj> dataList = new ArrayList<PackingObj>();
        if (isPostpositionPage) {
            Map<Long, PackingObj> tmpList = channelManager.findPackagedPageNoLocation(plid, ppId, staid);
            if (tmpList != null) {
                dataList.addAll(tmpList.values());
            }
        } else {
            // 获取打印信息
            if (pp.isDetialShowLocation()) {
                Map<Long, PackingObj> tmpList = channelManager.findPackagedPageShowLocation(plid, ppId, staid);
                if (tmpList != null) {
                    dataList.addAll(tmpList.values());
                }
            } else {
                Map<Long, PackingObj> tmpList = channelManager.findPackagedPageNoLocation(plid, ppId, staid);
                if (tmpList != null) {
                    dataList.addAll(tmpList.values());
                }
            }
        }
        if (dataList == null || dataList.isEmpty()) {
            dataList.add(new PackingObj());
        }
        channelManager.logPackingPagePrint(plid, userId, WhInfoTimeRefNodeType.PRINT_PICKING_LIST);
        JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        Map<String, Object> map = new HashMap<String, Object>();
        if (isPostpositionPage) {
            map.put("subReport", subTemplate);
        } else {
            InputStream is;
            try {
                is = this.getClass().getClassLoader().getResource(subTemplate).openStream();
                map.put("subReport", is);
            } catch (IOException e) {
                log.error("", e);
            }
            pp.setUserDefined(map);
        }
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(mainTemplate);
        try {
            cjp.initializeReport(map, dataSource);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        }
        try {
            return cjp.print();
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        }
        return null;

    }

    /**
     * 打印配货清单-拣货单-New
     * 
     * @throws Exception
     */
    public JasperPrint printPickingListNewMode1(Long plid, Integer pickZoneId, Long ouid, String psize, Long userId, String flag) throws Exception {
        String whZoonCode = null; // 自动化仓库区域编码
        List<PickingListObj> dataList = generatePickingListObjByPlId(plid, pickZoneId, ouid, psize, flag);
        if (dataList.size() > 0) {
            whZoonCode = dataList.get(0).getWhZone();
        }
        PickingList pl = warehousePrintData.getPickingListById(plid);
        if (dataList == null || dataList.isEmpty() || dataList.size() == 0) {
            log.debug("data is null");
            dataList = new ArrayList<PickingListObj>();
            dataList.add(new PickingListObj());
        }

        String basePath = Constants.PRINT_TEMPLATE_FLIENAME;
        String reportPath = basePath + "pickingList1_new.jasper";
        String lineReportPath = basePath + "pickingList1_new_detail.jasper";
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            InputStream lineReport = null;
            lineReport = this.getClass().getClassLoader().getResource(lineReportPath).openStream();
            map.put("lineReport", lineReport);
            if (pl.getTransTimeType() == null) {
                map.put("wayBillType", "陆运");
            } else {
                if (pl.getTransTimeType().getValue() == 1) {
                    map.put("wayBillType", "陆运");
                } else {
                    if (TransTimeType.SAME_DAY.getValue() == pl.getTransTimeType().getValue()) {
                        map.put("wayBillType", "当日");
                    } else {
                        map.put("wayBillType", "次日");
                    }
                }
            }

            // oto
            String toLocation = pl.getToLocation();
            if (toLocation != null && !"".equalsIgnoreCase(toLocation)) {
                toLocation = wareHouseManager.findAllOptionListByOptionKey(toLocation, "otoDestination");
                if (toLocation != null) {
                    map.put("toLocation", toLocation);
                } else {
                    log.debug("Oto order store code without maintenance");
                    map.put("toLocation", pl.getToLocation() == null ? "" : pl.getToLocation() + "");
                }
            } else {
                map.put("toLocation", pl.getToLocation() == null ? "" : pl.getToLocation() + "");
            }

            if (pl.getIsPostpositionExpressBill()) {
                map.put("isPEB", "后面/");
            }
            if (!pl.getIsPostpositionExpressBill()) {
                map.put("isPEB", "");
            }
            if (pl.getIsPostpositionPackingPage()) {
                map.put("isPPP", "后装");
            }
            if (!pl.getIsPostpositionPackingPage()) {
                map.put("isPPP", "");
            }
            map.put("invoiceNum", pl.getInvoiceNum() == null ? "" : pl.getInvoiceNum() + "");// 发票数量
            // 商品大小
            if (pl.getSkuSizeId() != null) {
                SkuSizeConfig skusize = warehousePrintData.getSkuSizeConfigById(pl.getSkuSizeId());
                map.put("skuSize", skusize.getName().subSequence(0, 1) + "-");
            } else {
                map.put("skuSize", "");
            }
            // 商品分配
            if (pl.getCategoryId() != null) {
                SkuCategories skuc = warehousePrintData.getSkuCategoriesById(pl.getCategoryId());
                map.put("skuCategory", skuc.getSkuCategoriesName() + "/");
            } else {
                map.put("skuCategory", "");
            }
            // 陆运
            if (pl.getIsRailway() != null) {
                if (pl.getIsRailway()) {
                    map.put("isRailway", "陆运");
                } else {
                    map.put("isRailway", "");
                }
            } else {
                map.put("isRailway", "");
            }
            /*
             * 包装 QS：isSpecialPackaging = true，specialType为空
             * 礼品卡：isSpecialPackaging为false或空，specialType = 1 QS+礼品卡：isSpecialPackaging =
             * true，specialType = 1
             */

            if (pl.getIsSpecialPackaging() != null && pl.getIsSpecialPackaging() && pl.getSpecialType() == null) {
                if (pl.getPickModleType() != null) {// QS
                    String codeStr = getCodeStr(pl, ouid, null);
                    map.put("isBigBox", "跨区QS" + codeStr + "/");
                } else {
                    map.put("isBigBox", "QS/");
                }
            }

            if (pl.getSpecialType() != null) {
                if ((pl.getIsSpecialPackaging() == null || !pl.getIsSpecialPackaging()) && pl.getSpecialType().getValue() == PickingListCheckMode.DEFAULE.getValue()) {
                    if (pl.getPickModleType() != null && pl.getPickModleType() == 6) {// 特殊处理
                        String codeStr = getCodeStr(pl, ouid, null);
                        map.put("isBigBox", "跨区特" + codeStr + "/");
                    } else {
                        map.put("isBigBox", "特/");
                    }
                } else if (pl.getIsSpecialPackaging() != null && pl.getIsSpecialPackaging() && pl.getSpecialType().getValue() == PickingListCheckMode.DEFAULE.getValue()) {
                    if (pl.getPickModleType() != null && pl.getPickModleType() == 6) {// QS 特殊处理
                        String codeStr = getCodeStr(pl, ouid, null);
                        map.put("isBigBox", "跨区特+QS" + codeStr + "/");
                    } else {
                        map.put("isBigBox", "特+QS/");
                    }
                }
            } else if ((pl.getIsSpecialPackaging() == null || !pl.getIsSpecialPackaging()) && pl.getSpecialType() == null) {
                if (pl.getCheckMode() == PickingListCheckMode.DEFAULE) {
                    if (pl.getPickModleType() != null && pl.getPickModleType() == 1) {// 多件2
                        String codeStr = "";
                        List<StockTransApplication> staList = wareHouseManager.findStaByPickingList(pl.getId(), ouid);
                        Map<String, String> whList = new HashMap<String, String>();
                        for (StockTransApplication sta : staList) {
                            if (StringUtils.hasLength(sta.getWhZoonList())) {
                                if (sta.getWhZoonList().contains("|")) {
                                    String tt = sta.getWhZoonList().replace("|", ",");
                                    String[] arrays = tt.split(",");
                                    for (String string : arrays) {
                                        whList.put(string, string);
                                    }
                                } else {
                                    whList.put(sta.getWhZoonList(), sta.getWhZoonList());
                                }
                            }
                        }
                        for (String whId : whList.keySet()) {
                            Long zoonId = Long.parseLong(whId);
                            Zoon zo = wareHouseManager.findZoonById(zoonId);
                            codeStr += zo.getCode() + "-";
                        }
                        if (!"".equals(codeStr)) {
                            codeStr = "(" + codeStr.substring(0, codeStr.length() - 1) + ")";
                        }
                        map.put("isBigBox", "跨区多件" + codeStr + "/");
                    } else {
                        map.put("isBigBox", "多件/");
                    }
                } else if (pl.getCheckMode() == PickingListCheckMode.PICKING_CHECK) {
                    /*
                     * if (pl.getPickModleType() != null && pl.getPickModleType() == 2) {// 单件
                     * String codeStr = getCodeStr(pl, ouid, null); map.put("isBigBox", "跨区单件" +
                     * codeStr + "/"); } else { map.put("isBigBox", "单件/"); }
                     */
                    map.put("isBigBox", "单件/");
                } else if (pl.getCheckMode() == PickingListCheckMode.PICKING_SECKILL) {
                    if (pl.getPickModleType() != null && pl.getPickModleType() == 3) {// 秒杀
                        String codeStr = getCodeStr(pl, ouid, null);
                        map.put("isBigBox", "跨区秒杀" + codeStr + "/");
                    } else {
                        map.put("isBigBox", "秒杀/");
                    }
                } else if (pl.getCheckMode() == PickingListCheckMode.PICKING_PACKAGE) {
                    if (pl.getPickModleType() != null && pl.getPickModleType() == 5) {// 套装
                        String codeStr = getCodeStr(pl, ouid, null);
                        map.put("isBigBox", "跨区套装" + codeStr + "/");
                    } else {
                        map.put("isBigBox", "套装/");
                    }
                } else if (pl.getCheckMode() == PickingListCheckMode.PICKING_GROUP) {
                    if (pl.getPickModleType() != null && pl.getPickModleType() == 4) {// 团购
                        String codeStr = getCodeStr(pl, ouid, null);
                        map.put("isBigBox", "跨区团购" + codeStr + "/");
                    } else {
                        map.put("isBigBox", "团购/");
                    }
                } else {
                    map.put("isBigBox", "");
                }
            }
            // 当日次日
            if (pl.getTransTimeType() != null) {
                if (pl.getTransTimeType() == TransTimeType.SAME_DAY) {
                    map.put("transTimeType", "当日");
                } else if (pl.getTransTimeType() == TransTimeType.THE_NEXT_DAY) {
                    map.put("transTimeType", "次日");
                } else if (pl.getTransTimeType() == TransTimeType.TIMELY) {
                    map.put("transTimeType", "即时达");
                } else if (pl.getTransTimeType() == TransTimeType.THE_NEXT_MORNING) {
                    map.put("transTimeType", "次晨达");
                } else {
                    map.put("transTimeType", "");
                }
            } else {
                map.put("transTimeType", "");
            }
            String pickingBatchBarCode = null;
            // 自动化仓库区域
            if (whZoonCode != null && pickZoneId != null) {
                map.put("isShow", true);
                map.put("whZoneBarCode", whZoonCode);
                map.put("whZone", whZoonCode);
                pickingBatchBarCode = autoOutboundTurnboxManager.pickingBatchBarCode(pl.getId(), whZoonCode);
            } else {
                map.put("isShow", false);
                map.put("whZone", "");
            }
            if (pickingBatchBarCode != null && !pickingBatchBarCode.equals(pl.getCode())) {
                if (dataList != null && dataList.size() > 0) {
                    for (PickingListObj po : dataList) {
                        po.setDphCode(pickingBatchBarCode);
                    }
                }
                map.put("whZone1", "#" + whZoonCode);
            }
            map.put("dphCode1", pl.getCode().substring(0, pl.getCode().length() - 4));
            map.put("dphCode2", pl.getCode().substring(pl.getCode().length() - 4, pl.getCode().length()));
            WhInfoTimeRef wtr = warehousePrintData.getFirstPrintDate(pl.getCode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), WhInfoTimeRefNodeType.PRING_PICKING1.getValue());
            if (wtr != null) {
                calendar.setTime(wtr.getExecutionTime());
                map.put("printTime", df.format(wtr.getExecutionTime()));
            } else {
                calendar.setTime(new Date());
                map.put("printTime", df.format(new Date()));
            }
            calendar.add(Calendar.HOUR, 2);// 创建时间+2小时
            map.put("createTime", (null != pl.getCreateTime() ? df.format(pl.getCreateTime()) : ""));
            map.put("doneTime", df.format(calendar.getTime()));
            map.put("city", pl.getCity() == null ? "" : pl.getCity() + "/");
            map.put("planBillCount", pl.getPlanBillCount() + "/");
            map.put("planSkuQty", pl.getPlanSkuQty());
            map.put("lpcode", pl.getLpcode() + "/");
        } catch (FileNotFoundException e) {
            log.error("", e);
            throw new BusinessException(1, e.getMessage());
        } catch (IOException e) {
            log.error("", e);
        }
        channelManager.logPackingPagePrint(pl.getCode(), userId, WhInfoTimeRefNodeType.PRING_PICKING3);
        JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(map, dataSource);
        return cjp.print();
    }

    private List<PickingListObj> generatePickingListObjByPlId(Long plid, Integer pickZoneId, Long ouid, String psize, String flag) {
        Long mergeSta = warehousePrintData.getPickListMergeSta(plid, ouid);
        Map<Long, PickingListObj> map = null;
        if (mergeSta > 0) {
            map = warehousePrintData.findPickingListByPlid(plid, pickZoneId, ouid, psize, flag);
        } else {
            map = warehousePrintData.findPickingListByPlid1(plid, pickZoneId, ouid, psize, flag);
        }
        List<PickingListObj> list = new ArrayList<PickingListObj>();
        if (map != null && !map.isEmpty()) list.addAll(map.values());
        return list;
    }

    /**
     * 采购上架打印sku
     * 
     * @throws Exception
     */
    @Override
    public JasperPrint printPurchaseInfo(StockTransVoucher stv, Long ouid) throws Exception {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<Object> fieldList = new ArrayList<Object>();
        List<StvLineCommand> purchaseSkuInfoList = new ArrayList<StvLineCommand>();
        OperationUnit WhOu = warehousePrintData.getOperationUnitById(ouid);
        parameterMap.put("warehouseName", WhOu.getName());
        purchaseSkuInfoList = warehousePrintData.findPurchaseSkuInfo(stv.getId(), ouid);
        log.debug("----------purchaseSkuInfoList.size(): " + purchaseSkuInfoList.size());
        if (!purchaseSkuInfoList.isEmpty() && purchaseSkuInfoList.size() != 0) {
            fieldList = setPurchaseSkuInfoDetail(purchaseSkuInfoList);
            JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
            JasperPrint jpJasperPrint = null;
            try {
                ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(Constants.PRINT_TEMPLATE_FLIENAME + "purchaseInboundInfo.jasper");
                cjp.initializeReport(parameterMap, ds);
                jpJasperPrint = cjp.print();
                // JasperPrintManager.printReport(jpJasperPrint, true);
            } catch (JasperReportNotFoundException e) {
                log.debug("purchaseInboundInfo.jasper file not found");
                log.error("", e);
                return null;
            } catch (JasperPrintFailureException e) {
                log.error("", e);
                return null;
            }
            return jpJasperPrint;
        } else {
            return null;
        }
    }

    private List<Object> setPurchaseSkuInfoDetail(List<StvLineCommand> purchaseSkuInfoList) {
        if (purchaseSkuInfoList == null || purchaseSkuInfoList.isEmpty() || purchaseSkuInfoList.size() <= 0) return null;
        Map<String, Object> map = null;
        List<Object> list = new ArrayList<Object>();
        for (StvLineCommand entrty : purchaseSkuInfoList) {
            map = new HashMap<String, Object>();
            map.put("skuName", entrty.getSkuName());
            map.put("skuProperties", entrty.getKeyProperties());
            map.put("skuBarcode", entrty.getBarCode());
            map.put("skuJmcode", entrty.getJmCode());
            map.put("skuSupplierCode", entrty.getSupplierCode());
            map.put("locationCode", entrty.getLocationCode());
            list.add(map);
        }
        return list;
    }

    /**
     * 物流交接单打印-打印状态为有效的交接清单明细
     * 
     * @param handOverList
     * @param ouid
     * @return
     * @throws Exception
     */
    public JasperPrint printHandOverList(Long holid, Long ouid) throws Exception {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<Object> fieldList = new ArrayList<Object>();
        List<HandOverListLineCommand> hollist = warehousePrintData.findLineDetailByHoListId(holid);
        OperationUnit warehouse = warehousePrintData.getOperationUnitById(ouid);
        HandOverList hol = warehousePrintData.getHandOverListById(holid);
        if (hol == null) throw new BusinessException(ErrorCode.HAND_OVER_NOT_FOUND);
        Integer totoalSkuCount = warehousePrintData.findTotalSkuCountByHoId(hol.getId());
        String expName = warehousePrintData.findExpNameByLpcode(hol.getLpcode());
        parameterMap = setHandOverListHead(hol, expName, warehouse.getName(), totoalSkuCount);
        if (!hollist.isEmpty() && hollist.size() != 0) {
            fieldList = setHandOverListDetail(hollist);
            JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
            try {
                ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(Constants.PRINT_TEMPLATE_FLIENAME + "handOverMenu.jasper");
                cjp.initializeReport(parameterMap, ds);
                JasperPrint jpJasperPrint;
                jpJasperPrint = cjp.print();
                // JasperPrintManager.printReport(jpJasperPrint, true);
                return jpJasperPrint;
            } catch (JasperReportNotFoundException e) {
                log.debug("handOverMenu.jasper file not found");
                log.error("", e);
                return null;
            } catch (JasperPrintFailureException e) {
                log.error("", e);
            }
        } else {
            return null;
        }
        return null;
    }

    // 物流交接1
    private Map<String, Object> setHandOverListHead(HandOverList hol, String expName, String warehouseName, Integer totalCount) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sender", hol.getSender());
        map.put("receiver", expName);
        map.put("warehouse", warehouseName);
        map.put("partyAOperator", hol.getPartyAOperator());
        map.put("partyBOperator", hol.getPartyBOperator());
        map.put("packageCount", hol.getPackageCount().toString() + " 件");
        map.put("totalWeight", hol.getTotalWeight().toString() + " kg");
        map.put("code", hol.getCode());
        map.put("partyAMobile", hol.getPaytyAMobile());
        map.put("partyBMobile", hol.getPaytyBMobile());
        map.put("totalSkuCount", totalCount.toString() + " 件");
        return map;
    }

    // 物流交接2
    private List<Object> setHandOverListDetail(List<HandOverListLineCommand> hollist) {
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map;
        HandOverListLineCommand h = new HandOverListLineCommand();
        for (int i = 0; i < hollist.size(); i++) {
            h = hollist.get(i);
            map = new HashMap<String, Object>();
            map.put("column_count", i + 1);
            map.put("trackingNo", h.getTrackingNo());
            // map.put("barcode", h.getBarcode());
            // map.put("refCode", h.getRefSlipCode());
            // map.put("receiveUser", h.getReceiver());
            map.put("weight", h.getWeight().toString());
            map.put("operatorTime", h.getOutboundTime() == null ? null : h.getOutboundTime().toString());
            // map.put("skuCount", h.getQuantity().toString());
            list.add(map);
        }
        return list;
    }

    private List<Object> setHandOverListDetailAD(List<HandOverListLineCommand> hollist) {
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map;
        HandOverListLineCommand h = new HandOverListLineCommand();
        for (int i = 0; i < hollist.size(); i++) {
            h = hollist.get(i);
            map = new HashMap<String, Object>();
            map.put("column_count", i + 1);
            map.put("trackingNo", h.getTrackingNo());
            map.put("barcode", h.getBarcode());
            map.put("refCode", h.getRefSlipCode());
            // map.put("receiveUser", h.getReceiver());
            map.put("weight", h.getWeight().toString());
            map.put("operatorTime", h.getOutboundTime() == null ? null : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(h.getOutboundTime()));
            // map.put("skuCount", h.getQuantity().toString());
            list.add(map);
        }
        return list;
    }

    // 新增物流交接打印方案
    public JasperPrint newPrintHandOverList(Long holid, Long ouid) throws Exception {
        // 查询是否配置过AD定制打印包裹明细交接清单
        Integer count = warehousePrintData.findIsPrintPackageDetail(holid, ouid);
        if (count != null && count == 0) {
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            List<Object> fieldList = new ArrayList<Object>();
            List<HandOverListLineCommand> hollist = warehousePrintData.findLineDetailByHoListId(holid);
            OperationUnit warehouse = warehousePrintData.getOperationUnitById(ouid);
            HandOverList hol = warehousePrintData.getHandOverListById(holid);
            if (hol == null) throw new BusinessException(ErrorCode.HAND_OVER_NOT_FOUND);
            Integer totoalSkuCount = warehousePrintData.findTotalSkuCountByHoId(hol.getId());
            String expName = warehousePrintData.findExpNameByLpcode(hol.getLpcode());
            parameterMap = setHandOverListHead(hol, expName, warehouse.getName(), totoalSkuCount);
            if (!hollist.isEmpty() && hollist.size() != 0) {
                fieldList = setHandOverListDetail(hollist);
                JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
                try {
                    ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(Constants.PRINT_TEMPLATE_FLIENAME + "newHandOverMenu.jasper");
                    cjp.initializeReport(parameterMap, ds);
                    JasperPrint jpJasperPrint;
                    jpJasperPrint = cjp.print();
                    // JasperPrintManager.printReport(jpJasperPrint, true);
                    return jpJasperPrint;
                } catch (JasperReportNotFoundException e) {
                    log.debug("newHandOverMenu.jasper file not found");
                    log.error("", e);
                    return null;
                } catch (JasperPrintFailureException e) {
                    log.error("", e);
                }
            } else {
                return null;

            }
            return null;
        } else {
            // 定制打印包裹明细交接单
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            List<Object> fieldList = new ArrayList<Object>();
            List<HandOverListLineCommand> hollist = warehousePrintData.findLineDetailByHoListIdAD(holid);
            OperationUnit warehouse = warehousePrintData.getOperationUnitById(ouid);
            HandOverList hol = warehousePrintData.getHandOverListById(holid);
            if (hol == null) throw new BusinessException(ErrorCode.HAND_OVER_NOT_FOUND);
            Integer totoalSkuCount = warehousePrintData.findTotalSkuCountByHoId(hol.getId());
            String expName = warehousePrintData.findExpNameByLpcode(hol.getLpcode());
            parameterMap = setHandOverListHead(hol, expName, warehouse.getName(), totoalSkuCount);
            if (!hollist.isEmpty() && hollist.size() != 0) {
                fieldList = setHandOverListDetailAD(hollist);
                JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
                try {
                    ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(Constants.PRINT_TEMPLATE_FLIENAME + "handOverMenuAD.jasper");
                    cjp.initializeReport(parameterMap, ds);
                    JasperPrint jpJasperPrint;
                    jpJasperPrint = cjp.print();
                    return jpJasperPrint;
                } catch (JasperReportNotFoundException e) {
                    log.debug("handOverMenuAD.jasper file not found");
                    log.error("", e);
                    return null;
                } catch (JasperPrintFailureException e) {
                    log.error("", e);
                }
            } else {
                return null;
            }
            return null;
        }
    }

    @Override
    public List<JasperPrint> newPrintHandOverList2(String hoId, Long wId) throws JasperReportNotFoundException, JasperPrintFailureException {
        // 查询是否配置过AD定制打印包裹明细交接清单
        Boolean flag = false;
        List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
        String hoIds[] = hoId.split(",");
        for (int i = 0; i < hoIds.length; i++) {
            // 查询是否配置过AD定制打印包裹明细交接清单
            Integer count = warehousePrintData.findIsPrintPackageDetail(Long.parseLong(hoIds[i]), wId);
            if (count != null && count > 0) {
                flag = true;
                break;
            }
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            List<Object> fieldList = new ArrayList<Object>();
            List<HandOverListLineCommand> hollist = warehousePrintData.findLineDetailByHoListId(Long.parseLong(hoIds[i]));
            HandOverList hol = null;
            OperationUnit warehouse = null;
            try {
                warehouse = warehousePrintData.getOperationUnitById(wId);
                hol = warehousePrintData.getHandOverListById(Long.parseLong(hoIds[i]));
            } catch (Exception e) {
                log.debug("warehouse hol  not found");
                log.error("", e);
            }
            if (hol == null) throw new BusinessException(ErrorCode.HAND_OVER_NOT_FOUND);
            Integer totoalSkuCount = warehousePrintData.findTotalSkuCountByHoId(hol.getId());
            String expName = warehousePrintData.findExpNameByLpcode(hol.getLpcode());
            parameterMap = setHandOverListHead(hol, expName, warehouse.getName(), totoalSkuCount);
            if (!hollist.isEmpty() && hollist.size() != 0) {
                fieldList = setHandOverListDetail(hollist);
                JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
                try {
                    ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(Constants.PRINT_TEMPLATE_FLIENAME + "newHandOverMenu.jasper");
                    cjp.initializeReport(parameterMap, ds);
                    jasperPrints.add(cjp.print());
                } catch (JasperReportNotFoundException e) {
                    log.debug("newHandOverMenu.jasper file not found");
                    log.error("", e);
                    return null;
                } catch (JasperPrintFailureException e) {
                    log.error("", e);
                }
            }
        }
        if (!flag) {
            return jasperPrints;
        }
        // 定制打印包裹明细交接单
        if (flag) {
            List<JasperPrint> jasperPrints2 = new ArrayList<JasperPrint>();
            for (int i = 0; i < hoIds.length; i++) {
                Map<String, Object> parameterMap = new HashMap<String, Object>();
                List<Object> fieldList = new ArrayList<Object>();
                Long holid = Long.parseLong(hoIds[i]);
                List<HandOverListLineCommand> hollist = warehousePrintData.findLineDetailByHoListIdAD(holid);
                OperationUnit warehouse = null;
                try {
                    warehouse = warehousePrintData.getOperationUnitById(wId);
                } catch (Exception e1) {
                    log.error("warehouse not found", e1);
                }
                HandOverList hol = null;
                try {
                    hol = warehousePrintData.getHandOverListById(holid);
                } catch (Exception e2) {
                    log.error("HandOverList not found", e2);
                }
                if (hol == null) throw new BusinessException(ErrorCode.HAND_OVER_NOT_FOUND);
                Integer totoalSkuCount = warehousePrintData.findTotalSkuCountByHoId(hol.getId());
                String expName = warehousePrintData.findExpNameByLpcode(hol.getLpcode());
                parameterMap = setHandOverListHead(hol, expName, warehouse.getName(), totoalSkuCount);
                if (!hollist.isEmpty() && hollist.size() != 0) {
                    fieldList = setHandOverListDetailAD(hollist);
                    JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
                    try {
                        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(Constants.PRINT_TEMPLATE_FLIENAME + "handOverMenuAD.jasper");
                        cjp.initializeReport(parameterMap, ds);
                        JasperPrint jpJasperPrint;
                        jpJasperPrint = cjp.print();
                        jasperPrints2.add(jpJasperPrint);
                        return jasperPrints2;
                    } catch (JasperReportNotFoundException e) {
                        log.debug("handOverMenuAD.jasper file not found");
                        log.error("", e);
                        return null;
                    } catch (JasperPrintFailureException e) {
                        log.error("", e);
                    }
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    // 库间移动 占用打印
    @Override
    public JasperPrint PrintInventoryOccupay(Long staid, Long ouid, boolean other) {
        List<InventoryOccupay> dataList = constructInvOccupay(staid, ouid, other);
        Iterator<InventoryOccupay> invs = dataList.iterator();
        InventoryOccupay inventoryOccupay = null;
        while (invs.hasNext()) {
            inventoryOccupay = invs.next();
            if (inventoryOccupay == null) continue;
            switch (Integer.parseInt(inventoryOccupay.getExecuteStatus())) {
                case 10:
                    inventoryOccupay.setExecuteStatus("已完成");
                    break;
                case 1:
                    inventoryOccupay.setExecuteStatus("已创建");
                    break;
                case 2:
                    inventoryOccupay.setExecuteStatus("配货中");
                    break;
                case 17:
                    inventoryOccupay.setExecuteStatus("取消已处理");
                    break;
                case 20:
                    inventoryOccupay.setExecuteStatus("配货失败");
                    break;
                default:
                    break;
            }
        }
        JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "inventoryoccupaydetail_main.jasper";
        String subReportPath = Constants.PRINT_TEMPLATE_FLIENAME + "inventoryoccupaydetail_sub.jasper";
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            InputStream is = null;
            is = this.getClass().getClassLoader().getResource(subReportPath).openStream();
            map.put("subReport", is);
        } catch (FileNotFoundException e) {
            log.error("", e);
            throw new BusinessException(1, e.getMessage());
        } catch (IOException e) {
            log.error("", e);
        }
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        try {
            cjp.initializeReport(map, dataSource);
            return cjp.print();
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        }
        return null;
    }

    private List<InventoryOccupay> constructInvOccupay(Long staid, Long ouid, boolean other) {
        List<InventoryOccupay> list = new ArrayList<InventoryOccupay>();
        InventoryOccupay inventoryoccupay = null;
        if (other) {
            // vmi
            inventoryoccupay = warehousePrintData.findVmiReturnOccupyInventoryByStaId(staid, ouid);
        } else {
            // 判断是否有目标仓库
            inventoryoccupay = warehousePrintData.findInventoryOccupay(staid, ouid);
            // StockTransApplication stockTransApplication = staDao.getByPrimaryKey(staid);
            // if (stockTransApplication.getAddiWarehouse() == null) {
            // // old
            // inventoryoccupay = staDao.findOutOfCossStaNotFinishedListByStaId(staid, ouid, new
            // BeanPropertyRowMapperExt<InventoryOccupay>(InventoryOccupay.class));
            // inventoryoccupay.setTargetWarehouse("");
            // } else {
            // inventoryoccupay = staDao.findOutOfAddWhIdNotFinishedListByStaId(staid, ouid, new
            // BeanPropertyRowMapperExt<InventoryOccupay>(InventoryOccupay.class));
            // }
        }
        // int pageNum = 0;
        if (inventoryoccupay != null) {
            inventoryoccupay = warehousePrintData.findInventoryOccLineList(staid, inventoryoccupay);
            // List<StvLine> stvline = this.findStvLineListByStaId(staid);
            // List<InventoryOccLine> line = new ArrayList<InventoryOccLine>();
            // for (StvLine stvl : stvline) {
            // InventoryOccLine inv = new InventoryOccLine();
            // inv.setDistrict(stvl.getDistrict().getName());
            // inv.setLocation(stvl.getLocation().getCode());
            //
            // inv.setBarCode(stvl.getSku().getBarCode());
            // inv.setJmCode(stvl.getSku().getJmCode());
            // inv.setKeyProperty(stvl.getSku().getKeyProperties());
            // inv.setSkuName(stvl.getSku().getName());
            // inv.setQuantity(stvl.getQuantity());
            // inv.setInvStatus(stvl.getInvStatus().getName());
            // line.add(inv);
            // }
            // inventoryoccupay.setLines(line);
        }
        list.add(inventoryoccupay);
        return list;
    }

    @Override
    public JasperPrint printVmiReturnInfo(Long staid, Long ouid) {
        return PrintInventoryOccupay(staid, ouid, true);
    }

    /**
     * 商品条码打印
     * 
     * @param skuId
     * @throws Exception
     */
    @Override
    public JasperPrint printSkuBarcode(Long skuId) throws Exception {
        if (skuId == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Sku sku = warehousePrintData.getSkuById(skuId);
        if (sku == null) {
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        }
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "SKU_BARCODE.jasper";
        List<ProductBarcodeObj> dataList = new ArrayList<ProductBarcodeObj>();
        ProductBarcodeObj pbc = new ProductBarcodeObj();
        pbc.setBarcode(sku.getBarCode());
        dataList.add(pbc);
        JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(new HashMap<String, Object>(), dataSource);
        return cjp.print();
    }

    @Override
    public JasperPrint printStaCode(Long staid) throws Exception {
        StockTransApplication sta = warehousePrintData.getStaById(staid);
        if (sta == null) throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        List<Object> fieldList = new ArrayList<Object>();
        fieldList.add(new HashMap<String, Object>().put("staCode", "staCode"));
        JRDataSource ds = new JRBeanCollectionDataSource(fieldList);

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("stacode", sta.getCode());

        String printTemplateFile = "jasperprint/printpdpurchasestacode.jasper";
        JasperPrint jpJasperPrint = new JasperPrint();
        try {
            ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(printTemplateFile);
            cjp.initializeReport(paramMap, ds);
            jpJasperPrint = cjp.print();
            return jpJasperPrint;
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        }
        return jpJasperPrint;
    }

    @Override
    public JasperPrint printPoConfirmReport(Long staId) throws JasperPrintFailureException, JRException, JasperReportNotFoundException {
        List<StvLineCommand> dataList = warehousePrintData.findPoConfirmStvLineBySta(staId);
        List<PoConfirmObj> poConfirmList = new ArrayList<PoConfirmObj>();
        for (int i = 0; i < dataList.size(); i++) {
            StvLineCommand da = dataList.get(i);
            PoConfirmObj pfo = new PoConfirmObj();
            pfo.setBrand(da.getBrand());
            pfo.setIndex(da.getIndex());
            pfo.setPlanQty(da.getPlanQty());
            pfo.setQuantity(Integer.parseInt(String.valueOf(da.getQuantity())));
            pfo.setSkuCode(da.getSkuCode());
            pfo.setSkuName(da.getSkuName());
            pfo.setBarCode(da.getSupplierCode());
            pfo.setTotalQty(da.getTotalQty());
            pfo.setUnitName("个");
            poConfirmList.add(pfo);
        }
        if (poConfirmList == null || poConfirmList.isEmpty()) {
            log.debug("data is null");
            poConfirmList = new ArrayList<PoConfirmObj>();
            poConfirmList.add(new PoConfirmObj());
        }
        JRDataSource dataSource = new JRBeanCollectionDataSource(poConfirmList);
        String basePath = Constants.PRINT_TEMPLATE_FLIENAME;
        String reportPath = basePath + "po_received_confirm_main.jasper";
        Map<String, Object> beans = new HashMap<String, Object>();
        PoCommand po = warehousePrintData.findPoInfo(staId);
        beans.put("poCteateTime", po.getApplyTime());
        beans.put("shopName", po.getShopName());
        if (po.getPaymentDate() == null) {
            beans.put("creditTime", po.getPaymentDate());
        } else {
            beans.put("creditTime", po.getPaymentDate().toString());
        }
        beans.put("warehouseName", po.getWhName());
        if (po.getPlanTime() == null) {
            beans.put("arriveTime", po.getPlanTime());
        } else {
            beans.put("arriveTime", po.getPlanTime().toString());
        }
        beans.put("supplierName", po.getSupportName());
        beans.put("pickUpType", po.getArrivalType());
        beans.put("poCode", po.getCode());
        beans.put("paymentType", po.getPaymentType());
        long plqty = 0;
        long totalQty = 0;
        long qty = 0;
        for (StvLineCommand l : dataList) {
            if (StringUtils.hasText(l.getBatchCode())) {
                beans.put("storeCount", Integer.parseInt(l.getBatchCode()));
            }
            plqty += l.getPlanQty().longValue();
            totalQty += l.getTotalQty().longValue();
            qty += l.getQuantity().longValue();
        }
        beans.put("amount", plqty);
        beans.put("actualAmount", totalQty);
        beans.put("todayAmount", qty);
        beans.put("memo", po.getRemark());
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(beans, dataSource);
        JasperPrint jp = null;
        try {
            jp = cjp.print();
        } catch (Throwable e) {
            log.error("", e);
        }
        return jp;
    }

    /**
     * 打印收货单详情
     */
    /**
     * 收货确认单Jasper打印
     * 
     * @throws Exception
     */
    public JasperPrint printInboundDetailReport(Long staId) throws Exception {
        List<StaLineCommand> dataList = warehousePrintData.findInBoundStaLineForPrint(staId);
        List<InboundDetailObj> inboundDetailList = new ArrayList<InboundDetailObj>();
        for (int i = 0; i < dataList.size(); i++) {
            StaLineCommand da = dataList.get(i);
            InboundDetailObj ido = new InboundDetailObj();
            ido.setCode(da.getCode());
            ido.setIndex(da.getIndex());
            ido.setJmskucode(da.getJmskuCode());
            ido.setJmCode(da.getJmcode());
            ido.setStatus(da.getStatus());
            ido.setBarCode(da.getBarCode());
            ido.setKeyProperties(da.getKeyProperties());
            ido.setOwner(da.getOwner());
            ido.setSkuCode(da.getSkuCode());
            ido.setSkuName(da.getSkuName());
            inboundDetailList.add(ido);
        }
        if (inboundDetailList == null || inboundDetailList.isEmpty()) {
            log.debug("data is null");
            inboundDetailList = new ArrayList<InboundDetailObj>();
            inboundDetailList.add(new InboundDetailObj());
        }
        JRDataSource dataSource = new JRBeanCollectionDataSource(inboundDetailList);
        String basePath = Constants.PRINT_TEMPLATE_FLIENAME;
        String reportPath = basePath + "inbound_detail_print.jasper";
        Map<String, Object> map = new HashMap<String, Object>();
        StockTransApplication sta = warehousePrintData.getStaById(staId);
        map.put("code", sta.getCode());
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(map, dataSource);
        return cjp.print();
    }

    @Override
    /***
     * 移库出库送货信息打印 - 送货使用
     * 
     * @return
     */
    public JasperPrint printOutBoundSendInfo(Long staid, Long ouid, PrintCustomize pc) throws Exception {
        if (staid == null || ouid == null) return null;
        List<OutBoundSendInfo> outBoundSendInfos = constructOutBoundSendInfo(staid, ouid);
        JRDataSource dataSource = new JRBeanCollectionDataSource(outBoundSendInfos);
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "outbound_send_infor_main.jasper";
        String subReportPath = Constants.PRINT_TEMPLATE_FLIENAME + "outbound_send_infor_sub.jasper";
        String[] strs = gucciManager.outboundJasper(staid);
        if (strs != null) {
            reportPath = strs[0];
            subReportPath = strs[1];
            outBoundSendInfos = constructGucciOutBoundSendInfo(staid, ouid);
            if (outBoundSendInfos != null && !outBoundSendInfos.isEmpty()) {
                for (OutBoundSendInfo info : outBoundSendInfos) {
                    info.setPickingListNumber(strs[2]);
                }
            }
            dataSource = new JRBeanCollectionDataSource(outBoundSendInfos);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            InputStream is = null;
            is = this.getClass().getClassLoader().getResource(subReportPath).openStream();
            map.put("subReport", is);
        } catch (FileNotFoundException e) {
            log.error("", e);
            throw new BusinessException(1, e.getMessage());
        } catch (IOException e) {
            log.error("", e);
        }
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        try {
            cjp.initializeReport(map, dataSource);
            return cjp.print();
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        }
        return null;
    }

    private List<OutBoundSendInfo> constructOutBoundSendInfo(Long staid, Long ouid) throws Exception {
        StockTransApplication sta = warehousePrintData.getStaById(staid);
        if (sta == null) throw new BusinessException(ErrorCode.STA_IS_NULL);
        List<OutBoundSendInfo> list = new ArrayList<OutBoundSendInfo>();
        OutBoundSendInfo outBoundSendInfo = warehousePrintData.findOutBoundSendInfo(staid);
        // if (sta.getType().getValue() == StockTransApplicationType.TRANSIT_CROSS.getValue()) {
        // // 库间移动
        // outBoundSendInfo = staDao.findOutOfCossOutBoundSendInfoByStaId(staid, new
        // BeanPropertyRowMapperExt<OutBoundSendInfo>(OutBoundSendInfo.class));
        // } else {
        // outBoundSendInfo = staDao.findVmiReturnOutBoundSendInfoByStaId(staid, new
        // BeanPropertyRowMapperExt<OutBoundSendInfo>(OutBoundSendInfo.class));
        // }
        if (outBoundSendInfo != null) {
            outBoundSendInfo = warehousePrintData.findOutBoundSendInfoLine(staid, outBoundSendInfo);
            // List<StaLine> stalines = staLineDao.findByStaId(staid);
            // log.debug("========================= stalines size : {} =====================",
            // stalines.size());
            // List<OutBoundSendInfoLine> line = new ArrayList<OutBoundSendInfoLine>();
            // int index = 1;
            // for (StaLine staline : stalines) {
            // OutBoundSendInfoLine sendInfo = new OutBoundSendInfoLine();
            // sendInfo.setOrdinal(index++);
            // sendInfo.setBarCode(staline.getSku().getBarCode());
            // sendInfo.setJmskuCode(staline.getSku().getCode());
            // sendInfo.setSkuName(staline.getSku().getName());
            // sendInfo.setSupplyCode(staline.getSku().getSupplierCode());
            // sendInfo.setInventoryStatus(staline.getInvStatus().getName());
            // sendInfo.setQuantity(staline.getQuantity());
            // line.add(sendInfo);
            // }
            // outBoundSendInfo.setLines(line);
        }
        list.add(outBoundSendInfo);
        return list;
    }

    private List<OutBoundSendInfo> constructGucciOutBoundSendInfo(Long staid, Long ouid) throws Exception {
        StockTransApplication sta = warehousePrintData.getStaById(staid);
        if (sta == null) throw new BusinessException(ErrorCode.STA_IS_NULL);
        List<OutBoundSendInfo> list = new ArrayList<OutBoundSendInfo>();
        OutBoundSendInfo outBoundSendInfo = warehousePrintData.findOutBoundSendInfo(staid);
        if (outBoundSendInfo != null) {
            outBoundSendInfo = warehousePrintData.findGucciOutBoundSendInfoLine(staid, outBoundSendInfo);
        }
        list.add(outBoundSendInfo);
        return list;
    }

    /**
     * 退仓装箱单 bin.hu
     * 
     * 
     */
    public JasperPrint printOutBoundPackageInfo(Long cartonid) throws Exception {
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "outbond_package_main.jasper";// 主模板
        String subReportPath = Constants.PRINT_TEMPLATE_FLIENAME + "outbond_package_detail.jasper";// 循环数据模板
        List<OutBoundPackageInfoObj> opoList = printOutBoundPackageInfoList(cartonid);
        String[] strs = gucciManager.outBoundPackageInfoJasper(cartonid);
        if (strs != null) {
            reportPath = strs[0];
            subReportPath = strs[1];
            if (opoList != null && !opoList.isEmpty()) {
                for (OutBoundPackageInfoObj info : opoList) {
                    info.setPickingListNumber(strs[2]);
                    info.setCode(strs[3]);
                }
            }
        }
        if (opoList == null || opoList.isEmpty()) {
            opoList = new ArrayList<OutBoundPackageInfoObj>();
            opoList.add(new OutBoundPackageInfoObj());
        }
        // 判断是否是toms定制
        if (opoList.size() > 0) {
            String shopName = opoList.get(0).getOwner();
            if ("toms旗舰店".equals(shopName) || "1toms旗舰店".equals(shopName)) {
                reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "outbond_package_main_toms.jasper";
                subReportPath = Constants.PRINT_TEMPLATE_FLIENAME + "outbond_package_detail_toms.jasper";
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        JRDataSource dataSource = new JRBeanCollectionDataSource(opoList);
        map.put("subReport", subReportPath);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(map, dataSource);
        return cjp.print();
    }

    /**
     * 自动化仓打印交接清单汇总
     * 
     * @param hoId
     * @param wId
     * @return
     * @throws JasperReportNotFoundException
     * @throws JasperPrintFailureException
     */
    public List<JasperPrint> printAutoWhHandOverList(String hoId, Long wId) throws JasperReportNotFoundException, JasperPrintFailureException {
        List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
        String hoIds[] = hoId.split(",");
        for (int i = 0; i < hoIds.length; i++) {
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            List<Object> fieldList = new ArrayList<Object>();
            Map<String, Object> map = new HashMap<String, Object>();// 随便写几个值
            map.put("column_count", 1);
            map.put("trackingNo", "1");
            map.put("weight", "0.1");
            map.put("operatorTime", "20110101");
            // map.put("skuCount", h.getQuantity().toString());
            fieldList.add(map);
            HandOverList hol = null;
            OperationUnit warehouse = null;
            try {
                warehouse = warehousePrintData.getOperationUnitById(wId);
                hol = warehousePrintData.getHandOverListById(Long.parseLong(hoIds[i]));
            } catch (Exception e) {
                log.debug("warehouse hol  not found");
                log.error("", e);
            }
            if (hol == null) throw new BusinessException(ErrorCode.HAND_OVER_NOT_FOUND);
            Integer totoalSkuCount = warehousePrintData.findTotalSkuCountByHoId(hol.getId());
            String expName = warehousePrintData.findExpNameByLpcode(hol.getLpcode());
            parameterMap = setHandOverListHead(hol, expName, warehouse.getName(), totoalSkuCount);
            try {
                JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
                ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(Constants.PRINT_TEMPLATE_FLIENAME + "newHandOverMenu.jasper");
                cjp.initializeReport(parameterMap, ds);
                jasperPrints.add(cjp.print());
            } catch (JasperReportNotFoundException e) {
                log.debug("newHandOverMenu.jasper file not found");
                log.error("", e);
                return null;
            } catch (JasperPrintFailureException e) {
                log.error("", e);
            }

        }
        return jasperPrints;
    }

    /**
     * 退仓装箱单 bin.hu
     * 
     * @param cartonid
     * @return
     */
    private List<OutBoundPackageInfoObj> printOutBoundPackageInfoList(Long cartonid) {
        Carton carton = warehousePrintData.findCartonById(cartonid);
        StockTransApplication sto = warehousePrintData.queryStaById(carton.getSta().getId());
        Map<Long, OutBoundPackageInfoObj> map = null;
        if (null != sto && null != sto.getOwner()) {
            String owner = sto.getOwner().toUpperCase();
            if (owner.indexOf("CONVERSE") > -1) {
                map = warehousePrintData.findPrintCartonDetailInfo3(cartonid);
            } else {
                map = warehousePrintData.findPrintCartonDetailInfo2(cartonid);
            }

        } else {
            map = warehousePrintData.findPrintCartonDetailInfo2(cartonid);
        }
        List<OutBoundPackageInfoObj> list = new ArrayList<OutBoundPackageInfoObj>();
        if (map != null && !map.isEmpty()) {
            list.addAll(map.values());
        }
        return list;
    }

    /**
     * 退仓装箱汇总信息打印
     */
    public JasperPrint printOutBoundPackingIntegrity(Long staid) throws Exception {
        String reportPath, subReportPath;
        String basePath = Constants.PRINT_TEMPLATE_FLIENAME;
        List<OutBoundPackingObj> dataList = null;
        reportPath = basePath + "out_bound_packing_main.jasper";
        subReportPath = basePath + "out_bound_packing_detail.jasper";
        dataList = generateOutBoundPackingInfoBystaid(staid);
        if (dataList == null || dataList.isEmpty()) {
            dataList = new ArrayList<OutBoundPackingObj>();
            dataList.add(new OutBoundPackingObj());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        try {
            InputStream is = null;
            is = this.getClass().getClassLoader().getResource(subReportPath).openStream();
            map.put("subReport", is);
        } catch (FileNotFoundException e) {
            log.error("", e);
            throw new BusinessException(1, e.getMessage());
        } catch (IOException e) {
            log.error("", e);
        }
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(map, dataSource);
        return cjp.print();
    }

    private List<OutBoundPackingObj> generateOutBoundPackingInfoBystaid(Long staid) {
        List<OutBoundPackingObj> packings = new ArrayList<OutBoundPackingObj>();
        packings = warehousePrintData.findOutBoundPackageByStaid(staid);
        List<StvLineCommand> lines = warehousePrintData.findOutBoundPackageLineByStaid(staid);
        if (packings != null && !packings.isEmpty()) {
            packings.get(0).setLines(lines);
        }
        return packings;
    }

    public JasperPrint printLocationBarCode(Long id) throws Exception {
        if (id == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        String code = warehousePrintData.findLocationCodeByid(id);
        if (!StringUtils.hasLength(code)) {
            throw new BusinessException(ErrorCode.WAREHOUSELOCATION_IS_NULL);
        }
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "warehouse_location_code.jasper";
        List<ProductBarcodeObj> dataList = new ArrayList<ProductBarcodeObj>();
        ProductBarcodeObj pbc = new ProductBarcodeObj();
        pbc.setBarcode(code);
        dataList.add(pbc);
        JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(new HashMap<String, Object>(), dataSource);
        return cjp.print();
    }

    public JasperPrint printDistrictCode(Long id) throws Exception {
        if (id == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        String code = warehousePrintData.findDistrictCodeByid(id);
        if (!StringUtils.hasLength(code)) {
            throw new BusinessException(ErrorCode.WAREHOUSE_DISTRICT_NOT_FOUND);
        }
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "warehosue_district_code.jasper";
        List<ProductBarcodeObj> dataList = new ArrayList<ProductBarcodeObj>();
        ProductBarcodeObj pbc = new ProductBarcodeObj();
        pbc.setBarcode(code);
        dataList.add(pbc);
        JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(new HashMap<String, Object>(), dataSource);
        return cjp.print();
    }

    public JasperPrint printDistrictRelativeLocations(Long id) throws Exception {
        List<ProductBarcodeObj> dataList = new ArrayList<ProductBarcodeObj>();
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "warehosue_district_locations_codes.jasper";
        WarehouseDistrict district = warehousePrintData.getWarehouseDistrictById(id);
        if (district == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_DISTRICT_NOT_FOUND);
        }
        List<String> locs = warehousePrintData.findAllAvailLocationsByDistrictId(id);
        if (locs == null) {
            locs = new ArrayList<String>();
            locs.add(new String());
        }
        if (locs.isEmpty()) {
            locs.add(new String());
        } else {
            for (String code : locs) {
                ProductBarcodeObj e = new ProductBarcodeObj();
                e.setBarcode(code);
                dataList.add(e);
            }
        }
        JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        Map<String, Object> map = new HashMap<String, Object>();
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(map, dataSource);
        return cjp.print();
    }

    @Override
    public JasperPrint printGift(Long giftLineId) throws Exception {
        String reportPath = null;
        GiftLine gl = warehousePrintData.getGiftLineById(giftLineId);
        Map<String, Object> map = new HashMap<String, Object>();
        String memo = (null != gl ? gl.getMemo() : "");
        memo += Constants.PRINT_PLACEHOLDER;
        map.put("memo", memo);
        String slipCode1 = (null != gl ? (null != gl.getStaLine() ? (null != gl.getStaLine().getSta() ? gl.getStaLine().getSta().getSlipCode1() : "") : "") : "");
        String extCode1 = (null != gl ? (null != gl.getStaLine() ? (null != gl.getStaLine().getSku() ? gl.getStaLine().getSku().getExtensionCode1() : "") : "") : "");
        String size = (null != gl ? (null != gl.getStaLine() ? (null != gl.getStaLine().getSku() ? gl.getStaLine().getSku().getSkuSize() : "") : "") : "");
        String owner = (null != gl ? (null != gl.getStaLine() ? gl.getStaLine().getOwner() : "") : "");
        map.put("slipCode1", (StringUtil.isEmpty(slipCode1) ? "" : slipCode1));
        map.put("extCode1", (StringUtil.isEmpty(extCode1) ? "" : extCode1));
        map.put("size", (StringUtil.isEmpty(size) ? "" : size));
        if ("5香港NIKE官方商城".equals(owner)) {
            // if("香港nike中国官方商城".equals(owner)){
            reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "nike_hk_gift_new.jasper";
        } else {
            reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "nike_gift_new.jasper";
        }
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(map, null);
        JasperPrint jpJasperPrint = cjp.print();
        return jpJasperPrint;
    }


    @Override
    public JasperPrint printReturnPackage(String batchCode) throws Exception {
        List<ReturnPackageCommand> rpList = whQuery.findReturnPackageList(batchCode);
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "return_package_main.jasper";// 主模板
        String subReportPath = Constants.PRINT_TEMPLATE_FLIENAME + "return_package_detail.jasper";// 循环数据模板
        List<ReturnPackingObj> dataList = new ArrayList<ReturnPackingObj>();
        ReturnPackingObj obj = new ReturnPackingObj();
        obj.setBatchCode(batchCode);
        obj.setLines(rpList);
        dataList.add(obj);
        Map<String, Object> map = new HashMap<String, Object>();
        JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        map.put("subReport", subReportPath);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(map, dataSource);
        return cjp.print();

    }


    /**
     * 特殊包装打印
     * 
     * @param lpId
     * @param staId
     * @return
     * @throws Exception
     */
    public JasperPrint bySPTypePrint(Long staId, StaSpecialExecuteType type) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("begin bySPTypePrint.......");
        }
        if (staId == null || type == null) return null;
        String reportPath = null, basePath = null;
        List<SpecialPackagingObj> dataList = null;
        if (log.isDebugEnabled()) {
            log.debug("-----StaSpecialExecuteType-----" + type);
        }
        if (StaSpecialExecuteType.COACH_PRINT.equals(type)) {// Coach小票打印移动到此处
            reportPath = Constants.PRINT_TEMPLATE_FLIENAME + Constants.SP_PRINT_COACH_MAIN;
            basePath = Constants.PRINT_TEMPLATE_FLIENAME + Constants.SP_PRINT_COACH_DETAIL;
            dataList = getCoachSpecial(staId);
        } else if (StaSpecialExecuteType.BURBERRY_OUT_PRINT.equals(type)) {
            reportPath = Constants.PRINT_TEMPLATE_FLIENAME + Constants.SP_PRINT_BURBERRY_MAIN;
            basePath = Constants.PRINT_TEMPLATE_FLIENAME + Constants.SP_PRINT_BURBERRY_DETAIL;
            dataList = initPrintObj(warehousePrintData.findBurberryPrintInfo(staId));
        } else if (StaSpecialExecuteType.BURBERRY_RETURN_PRINT.equals(type)) {
            reportPath = Constants.PRINT_TEMPLATE_FLIENAME + Constants.SP_PRINT_BURBERRY_RETURN_MAIN;
            basePath = Constants.PRINT_TEMPLATE_FLIENAME + Constants.SP_PRINT_BURBERRY_RETURN_DETAIL;
            dataList = initPrintObj(warehousePrintData.findBurberryPrintInfo(staId));
        } else if (StaSpecialExecuteType.NIKE_GIFT_CARD_PRINT.equals(type) || StaSpecialExecuteType.NIKE_GIFT_CARD_B_PRINT.equals(type) || StaSpecialExecuteType.NIKE_GIFT_CARD_C_PRINT.equals(type)) {
            List<StaSpecialExecutedCommand> ssecList = wareHouseManagerQuery.queryStaSpecialExecute(staId);
            Map<String, Object> map = new HashMap<String, Object>();
            for (StaSpecialExecutedCommand ssec : ssecList) {
                if (StaSpecialExecuteType.NIKE_GIFT_CARD_PRINT.getValue() == ssec.getIntType() || StaSpecialExecuteType.NIKE_GIFT_CARD_B_PRINT.getValue() == ssec.getIntType() || StaSpecialExecuteType.NIKE_GIFT_CARD_C_PRINT.getValue() == ssec.getIntType()) {
                    map.put("memo", ssec.getMemo());
                    break;
                }
            }
            String path = Constants.PRINT_TEMPLATE_FLIENAME + "nike_hk_gift.jasper";
            ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(path);
            cjp.initializeReport(map, null);
            JasperPrint jpJasperPrint = cjp.print();
            return jpJasperPrint;
        } else if (StaSpecialExecuteType.GUCCI_GIFT_CARD_PRINT.equals(type)) {
            List<StaSpecialExecutedCommand> ssecList = wareHouseManagerQuery.queryStaSpecialExecute(staId);
            Map<String, Object> map = new HashMap<String, Object>();
            for (StaSpecialExecutedCommand ssec : ssecList) {
                if (StaSpecialExecuteType.GUCCI_GIFT_CARD_PRINT.getValue() == ssec.getIntType()) {
                    map.put("gucciGiftValue", ssec.getMemo());
                    break;
                }
            }
            String path = Constants.PRINT_TEMPLATE_FLIENAME + "gucci_gift_card.jasper";
            ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(path);
            cjp.initializeReport(map, null);
            JasperPrint jpJasperPrint = cjp.print();
            return jpJasperPrint;
        } else {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        map.put("SUBREPORT_DIR", basePath);
        map.put("logImage", "print_img/burberry_logo.png");
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(map, dataSource);
        return cjp.print();
    }

    /**
     * Coach 小票打印 数据获取
     * 
     * @param staId
     * @return
     * @throws Exception
     */
    public List<SpecialPackagingObj> getCoachSpecial(Long staId) throws Exception {
        StockTransApplication sta = warehousePrintData.getStaById(staId);
        int type = warehousePrintData.getCoachSpecialStaType(staId);
        // if (StockTransApplicationType.OUTBOUND_SALES.equals(sta.getType())) {
        // type = 1;
        // } else if (StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(sta.getType())) {
        // type = 2;
        // } else if (StockTransApplicationType.OUTBOUND_RETURN_REQUEST.equals(sta.getType())) {
        // type = 3;
        // } else {
        // return null;
        // }
        SpecialPackagingObj printObj = new SpecialPackagingObj();
        SalesTicketResult resultDate = warehousePrintData.getSalesTicket(sta.getRefSlipCode(), type);
        if (SalesTicketResult.RESULT_STATUS_SUCCESS == resultDate.getStatus()) {
            SalesTicketResp stResp = resultDate.getSalesTicket();
            if (stResp != null) {
                printObj.setOrderCode(stResp.getTicketNo());
                printObj.setOrderDate(FormatUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
                printObj.setMemo(stResp.getRemark());
                printObj.setCustomer(stResp.getMemberRealName());
                printObj.setSeller(stResp.getSeller());
                printObj.setShopName(stResp.getShopNo());
                if (stResp.getQuantity() != null) {
                    printObj.setCommodityQty(stResp.getQuantity());
                }
                printObj.setVipCode(stResp.getVipCode());
                // 订单 支付金额
                BigDecimal actual;
                if (stResp.getTotalActual() == null) {
                    actual = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
                } else {
                    actual = stResp.getTotalActual().setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                // 订单金额
                printObj.setPayActual(actual.toString());
                printObj.setTotalActual(actual.toString());

                printObj.setImgSemacode("print_img/qr_code/coach_qr_code.jpg");// 二维码地址

                // 支付方式
                List<SalesTicketTenderResp> listResp = stResp.getTenderList();
                printObj.setPayType(listResp != null && listResp.size() > 0 ? "" : listResp.get(0).getPaymentType());
                List<SpecialPackagingLineObj> lines = new ArrayList<SpecialPackagingLineObj>();
                for (SalesTicketLineResp l : stResp.getTicketLines()) {
                    SpecialPackagingLineObj line = new SpecialPackagingLineObj();
                    // 明细备注
                    // line.setDetail();
                    if (l.getDiscountRate() != null) {
                        line.setDiscountRate(l.getDiscountRate().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%");
                    }
                    if (l.getUnitPrice() != null) {
                        line.setUnitPrice(l.getUnitPrice().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    }
                    if (l.getTotalActual() != null) {
                        line.setTotalActual(l.getTotalActual().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                    }
                    if (l.getQuantity() != null) {
                        line.setQty(l.getQuantity());
                    }
                    line.setSkuName(l.getProductName());
                    line.setSupplierSkuCode(l.getProSupplierCode());
                    lines.add(line);
                }
                printObj.setLines(lines);
            } else {
                log.error("printCoachSpecial OMSRmi[getSalesTicket] return error! STASlipCode[" + sta.getRefSlipCode() + "]");
            }
        } else {
            log.debug("printCoachSpecial OMSRmi[getSalesTicket] return not fand! STASlipCode[" + sta.getRefSlipCode() + "]");
        }
        List<SpecialPackagingObj> result = new ArrayList<SpecialPackagingObj>();
        result.add(printObj);
        return result;
    }

    private List<SpecialPackagingObj> initPrintObj(List<SpecialPackagingData> dataList) {
        List<SpecialPackagingObj> result = new ArrayList<SpecialPackagingObj>();
        Map<Long, SpecialPackagingObj> map = new HashMap<Long, SpecialPackagingObj>();
        SpecialPackagingObj bean;
        for (SpecialPackagingData data : dataList) {
            Long key = data.getStaId();
            if (map.containsKey(key)) {
                bean = map.get(key);
                SpecialPackagingLineObj line = new SpecialPackagingLineObj();
                line.setSkuName(data.getSkuName());
                line.setSkuCode(data.getSkuCode());
                line.setBarCode(data.getBarCode());
                line.setQty(data.getQuantity());
                line.setColor(data.getColor());
                line.setSize(data.getSkuSize());
                line.setUnitPrice(data.getUnitPrice());
                bean.getLines().add(line);
            } else {
                bean = new SpecialPackagingObj();
                bean.setAddress(data.getAddress());
                bean.setOrderCode(data.getOrderCode());
                bean.setOwner(data.getOwner());
                bean.setOrderDate(data.getOrderDate());
                bean.setOutboundDate(data.getOutboundDate());
                bean.setCustomer(data.getCustomer());
                bean.setCountry(data.getCountry());
                bean.setProvince(data.getProvince());
                bean.setCity(data.getCity());
                bean.setDistrict(data.getDistrict());
                bean.setTotalActual(data.getTotalActual().toString());
                if (data.getMemo() == null) {
                    bean.setMemo("");
                } else {
                    bean.setMemo(data.getMemo());
                }
                List<SpecialPackagingLineObj> lines = new ArrayList<SpecialPackagingLineObj>();
                SpecialPackagingLineObj line = new SpecialPackagingLineObj();
                line.setSkuName(data.getSkuName());
                line.setSkuCode(data.getSkuCode());
                line.setBarCode(data.getBarCode());
                line.setQty(data.getQuantity());
                line.setColor(data.getColor());
                line.setSize(data.getSkuSize());
                line.setUnitPrice(data.getUnitPrice());
                lines.add(line);
                bean.setLines(lines);
                result.add(bean);
                map.put(key, bean);
            }
        }
        return result;
    }

    @Override
    public JasperPrint printExpressBillByPickingListLpCode(Long pickingListId, Boolean isOline, Long userId) throws Exception {
        PickingList p = warehousePrintData.getPickingListById(pickingListId);
        if (p == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<StaDeliveryInfoCommand> datas = warehousePrintData.findPrintExpressBillData(pickingListId);
        for (StaDeliveryInfoCommand command : datas) {
            command.setTransTimeTypeName(warehousePrintData.findAllOptionListByOptionKey(command.getTransTimeTypeB(), "transTimeType"));
            command.setTransTypeName(warehousePrintData.findAllOptionListByOptionKey(command.getTransTypeB(), "transType"));
        }
        return setExpressBillData(p.getCode(), p.getLpcode(), WhInfoTimeRefBillType.STA_PICKING.getValue(), WhInfoTimeRefNodeType.PRINT_TRANK.getValue(), p.getWarehouse().getId(), userId, datas, isOline);
    }

    /**
     * 模板数据特殊处理
     * 
     * @param lpcode
     * @param whOuId
     * @param datas
     * @param isOline 是否指定面单类型，null系统判断,true电子面单,false标准面单
     * @return
     * @throws Exception
     */
    private JasperPrint setExpressBillData(String slipCode, String lpcode, int billType, int nodeType, Long whOuId, Long userId, List<StaDeliveryInfoCommand> datas, Boolean isOline) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("======begin setExpressBillData====" + slipCode);
        }
        if (datas == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 查询模板
        Transportator trans = warehousePrintData.findByCode(lpcode);
        Warehouse wh = warehousePrintData.getByOuId(whOuId);
        if (wh == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        String jpPath = trans.getJasperNormal();
        if (log.isDebugEnabled()) {
            log.debug("=======find is lpcode=======" + lpcode);
        }
        if (isOline == null) {
            // 顺丰电子面单判断
            if (wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder() && (Transportator.SF.equals(lpcode) || Transportator.SFCOD.equals(lpcode) || Transportator.SFDSTH.equals(lpcode) || Transportator.SFHK.equals(lpcode))) {
                if (wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            } else if (wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
                if (wh.getIsOlSto() != null && wh.getIsOlSto()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // Zto电子面单判断
            if (wh.getIsZtoOlOrder() != null && wh.getIsZtoOlOrder() && (Transportator.ZTOOD.equals(lpcode) || Transportator.ZTO.equals(lpcode))) {
                if (wh.getIsZtoOlOrder() != null && wh.getIsZtoOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // EMS电子面单判断
            if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder() && (Transportator.EMS.equals(lpcode) || Transportator.EMS_COD.equals(lpcode))) {
                if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder()) {
                    jpPath = returnJPPatchEMS(datas);
                }
            }
            // 天天快递电子面单判断
            if (wh.getIsTtkOlOrder() != null && wh.getIsTtkOlOrder() && Transportator.TTKDEX.equals(lpcode)) {
                if (wh.getIsTtkOlOrder() != null && wh.getIsTtkOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // YTO电子面单判断
            if (wh.getIsYtoOlOrder() != null && wh.getIsYtoOlOrder() && Transportator.YTO.equals(lpcode)) {
                if (wh.getIsYtoOlOrder() != null && wh.getIsYtoOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // 万象快递电子面单判断
            if (wh.getIsWxOlOrder() != null && wh.getIsWxOlOrder() && (Transportator.WX.equals(lpcode) || Transportator.CS100.equals(lpcode))) {
                if (wh.getIsWxOlOrder() != null && wh.getIsWxOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // CXC快递电子面单判断
            if (wh.getIsCxcOlOrder() != null && wh.getIsCxcOlOrder() && Transportator.CXC.equals(lpcode)) {
                if (wh.getIsCxcOlOrder() != null && wh.getIsCxcOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // RFD快递电子面单判断
            if (wh.getIsRfdOlOrder() != null && wh.getIsRfdOlOrder() && Transportator.RFD.equals(lpcode)) {
                jpPath = trans.getJasperOnLine();
            }
        } else if (isOline != null && isOline) {
            // EMS电子面单判断
            if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder() && (Transportator.EMS.equals(lpcode) || Transportator.EMS_COD.equals(lpcode))) {
                if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder()) {
                    jpPath = returnJPPatchEMS(datas);
                }
            } else {
                // 使用电子免单
                jpPath = trans.getJasperOnLine();
            }
        } else {
            if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder() && (Transportator.EMS.equals(lpcode) || Transportator.EMS_COD.equals(lpcode))) {
                if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder()) {
                    jpPath = returnJPPatchEMS(datas);
                }
            } else {
                // 使用普通免单
                jpPath = trans.getJasperNormal();
            }
        }
        // STO电子面单增加省份编码
        HashMap<String, List<WhTransAreaNo>> transAreaCache = warehousePrintData.transAreaCache();
        if (wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
            if (transAreaCache.isEmpty()) {
                List<WhTransAreaNo> list = warehousePrintData.getTransAreaByLpcode(lpcode);
                transAreaCache.put(lpcode, list);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("=======find datas.size======" + datas.size());
        }
        // 如果是申通 配置省编码
        List<StoProCode> stoList = null;
        if (Transportator.STO.equals(lpcode)) {
            stoList = warehousePrintData.queryStoProCode();
        }
        for (StaDeliveryInfoCommand cmd : datas) {
            if (stoList != null) {
                for (StoProCode stoProCode : stoList) {
                    if (cmd.getProvince() != null) {
                        if (cmd.getProvince().contains(stoProCode.getPro())) {
                            cmd.setProCode(stoProCode.getCode());
                            break;
                        }
                    } else {
                        log.info("======cmd.getProvince() is null====TrackingNo:" + cmd.getTrackingNo());
                    }
                }
            }
            String sn = cmd.getQuantity();

            // NIKE 香港SF 显示格式
            if (trans.getExpCode().equals(Transportator.SFHK)) {
                BigDecimal transFree = (null != cmd.getOrderTransferFree() ? cmd.getOrderTransferFree() : new BigDecimal(0));
                BigDecimal ordertotalactual = (null != cmd.getOrdertotalactual() ? cmd.getOrdertotalactual() : new BigDecimal(0));
                ordertotalactual.add(transFree);
                cmd.setQuantity(sn);
                cmd.setOrdertotalactual(ordertotalactual);
            } else if (trans.getExpCode().equals(Transportator.SFGJ)) {// //SFGJ运单
                List<StaDeliveryInfoCommand> staDList = warehousePrintData.printCategeryAndQtyDeliveryByStaId(cmd.getId());
                for (int i = 0; i < staDList.size(); i++) {
                    if (i == 0) {
                        cmd.setCategoryName1(staDList.get(i).getCategoryName());
                        cmd.setCategoryQty1(staDList.get(i).getCategoryTotal());
                    } else if (i == 1) {
                        cmd.setCategoryName2(staDList.get(i).getCategoryName());
                        cmd.setCategoryQty2(staDList.get(i).getCategoryTotal());
                    } else if (i == 2) {
                        cmd.setCategoryName3(staDList.get(i).getCategoryName());
                        cmd.setCategoryQty3(staDList.get(i).getCategoryTotal());
                    }
                }
            } else {
                cmd.setQuantity("商品" + sn + "件");
            }
            // 匹配物流,增加省份编码
            if (!transAreaCache.isEmpty() && wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
                Set<String> keys = transAreaCache.keySet();
                Iterator<String> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    List<WhTransAreaNo> list = transAreaCache.get(key);
                    for (WhTransAreaNo whTransAreaNo : list) {
                        if (whTransAreaNo.getProvince().contains(cmd.getProvince()) || cmd.getProvince().contains(whTransAreaNo.getProvince())) {
                            cmd.setAreaNumber(whTransAreaNo.getAreaNumber());
                        }
                    }
                }
            }
            // 打字处理
            if (cmd.getBigAddress() != null) {
                if (cmd.getBigAddress().endsWith("-")) {
                    cmd.setBigAddress(cmd.getBigAddress().substring(0, cmd.getBigAddress().length() - 1));
                }
            }

            if (wh.getIsThirdPartyPaymentSF() != null && wh.getIsThirdPartyPaymentSF()) {
                cmd.setPayMethod("转第三方付");
            } else {
                cmd.setPayMethod("寄付月结");
            }
            // SF电子面单电话号码屏蔽后5位
            if (wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder() && (Transportator.SF.equals(lpcode) || Transportator.SFCOD.equals(lpcode) || Transportator.SFDSTH.equals(lpcode))) {
                // cmd.setMobile(FormatUtil.shieldinMobile(cmd.getMobile()));
                // cmd.setTelephone(FormatUtil.shieldinPhoneNumber(cmd.getTelephone()));
                cmd.setLogoImg(Transportator.SF_LOGO_IMAGE);
                // SF 月结账号设置
                String jcustId = sfJcustIdMap.get(whOuId + "-" + cmd.getChannelId());
                if (jcustId == null) {
                    // 根据店铺配置制定SF月结账号和客户编码
                    ChannelWhRef channelWhRef = channelManager.findChannelRefByWhIdAndShopId(whOuId, cmd.getChannelId());
                    if (channelWhRef != null && StringUtils.hasText(channelWhRef.getSfJcustid())) {
                        jcustId = channelWhRef.getSfJcustid();
                        sfJcustIdMap.put(whOuId + "-" + cmd.getChannelId(), jcustId, 30 * 60 * 1000);
                    } else {
                        BiChannel shop = channelManager.getBiChannel(cmd.getChannelId());
                        if (StringUtil.isEmpty(shop.getSfJcustid())) {
                            // 原逻辑
                            jcustId = sfJcustIdMap.get(whOuId + "-" + cmd.getChannelId());
                            if (jcustId == null) {
                                TransSfInfo result = whQuery.findTransSfInfoDefault();
                                jcustId = result.getjCustid();
                                sfJcustIdMap.put(whOuId + "-" + cmd.getChannelId(), result.getjCustid(), 30 * 60 * 1000);
                            }
                        } else {
                            // 店铺配置逻辑
                            jcustId = shop.getSfJcustid();
                            sfJcustIdMap.put(whOuId + "-" + cmd.getChannelId(), jcustId, 30 * 60 * 1000);
                        }
                    }
                }
                if(!StringUtil.isEmpty(jcustId)) {
                	if(jcustId.length()<3) {
                		jcustId="***"+jcustId;
                	}else {
                		jcustId="***"+jcustId.substring(jcustId.length()-3, jcustId.length());
                	}
                }
                cmd.setJcustid(jcustId);
                if (Transportator.SF.equals(lpcode) || Transportator.SFCOD.equals(lpcode)) {
                    cmd.setLpCode(Transportator.SF);
                }
            } else if (wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
                cmd.setLogoImg(Transportator.STO_LOGO_IMAGE);
                cmd.setQuantity("商品" + sn + "件");
                // wh.getIsZtoOlOrder() != null && wh.getIsZtoOlOrder() &&
            } else if ((Transportator.ZTO.equals(lpcode))) {
                cmd.setLogoImg(Transportator.ZTO_LOGO_IMAGE);
            }
            // 设置中文金额
            if (cmd.getAmount() != null) {
                // cmd.setStrAmount(formatIntToChinaBigNumStr(cmd.getAmount().intValue()));
                cmd.setStrAmount(StringUtil.getCHSNumber(cmd.getAmount().intValue() + ".0"));
            }
            // 设置详细地址,出去重复省市区，如果不存在则拼接
            String address = cmd.getAddress();
            address = StringUtil.getRealAddress(address, cmd.getProvince(), cmd.getCity(), cmd.getDistrict(), true);
            address += "                                                                                                                #";
            cmd.setAddress(address);
            // 设置发运地
            if (StringUtils.hasText(wh.getDeparture())) {
                cmd.setDeparture(wh.getDeparture());
            }
            // 设置保价金额
            BigDecimal insurance = warehousePrintData.getInsurance(cmd.getChannelCode(), cmd.getInsuranceAmount());
            if (null != insurance)
                cmd.setInsuranceAmount(insurance);// 保价
            else
                cmd.setInsuranceAmount(null);// 非保价

            // 拼接配货信息

            Map<String, Long> lineMap = wareHouseManager.findStalAndBarcodeByStaid(cmd.getId());

            String skuList = "";
            int i = 0;
            for (String barCode : lineMap.keySet()) {
                if (i == (lineMap.size() - 1)) {
                    skuList = skuList + barCode + "【" + lineMap.get(barCode) + "】";
                } else if (i == 5) {
                    skuList = skuList + barCode + "【" + lineMap.get(barCode) + "】...";
                    break;
                } else {
                    skuList = skuList + barCode + "【" + lineMap.get(barCode) + "】/";
                }
                i++;
            }

            String owner = cmd.getOwner();
            // ck店铺 京东物流 面单上备注写死：不得拆内包装验货
            if ("CK".equalsIgnoreCase(owner) && Transportator.JD.equals(lpcode)) {
                jpPath = "jasperprint/CKJDKD.jasper";
            }
            // 澳门件定制SF物流运单
            if (!StringUtil.isEmpty(slipCode) && org.apache.commons.lang3.StringUtils.equals(Transportator.SFMACAO, lpcode)) {
                // wareHouseManager.queryIsMacaoOrder(slipCode)
                jpPath = trans.getJasperNormal();
                // 商品类型设置
                String totalCategories = warehousePrintData.queryTotalCatrgories(slipCode);
                if (totalCategories != null) {
                    cmd.setTotalCategories(totalCategories);
                } else {
                    cmd.setTotalCategories(cmd.getSkuCategories());
                }
                // SF月结账号设置
                String jcustId = sfMACAOJcustIdMap.get(whOuId + "-" + cmd.getChannelId());
                if (jcustId == null) {
                    // 根据店铺配置制定SF月结账号和客户编码
                    ChannelWhRef channelWhRef = channelManager.findChannelRefByWhIdAndShopId(whOuId, cmd.getChannelId());
                    if (channelWhRef != null && StringUtils.hasText(channelWhRef.getSfJcustid())) {
                        jcustId = channelWhRef.getSfJcustid();
                        sfMACAOJcustIdMap.put(whOuId + "-" + cmd.getChannelId(), jcustId, 30 * 60 * 1000);
                    } else {
                        BiChannel shop = channelManager.getBiChannel(cmd.getChannelId());
                        if (StringUtil.isEmpty(shop.getSfJcustid())) {
                            // 原逻辑
                            jcustId = sfMACAOJcustIdMap.get(whOuId + "-" + cmd.getChannelId());
                            if (jcustId == null) {
                                TransSfInfo result = whQuery.findTransSfInfoDefault();
                                jcustId = result.getjCustid();
                                sfMACAOJcustIdMap.put(whOuId + "-" + cmd.getChannelId(), result.getjCustid(), 30 * 60 * 1000);
                            }
                        } else {
                            // 店铺配置逻辑
                            jcustId = shop.getSfJcustid();
                            sfMACAOJcustIdMap.put(whOuId + "-" + cmd.getChannelId(), jcustId, 30 * 60 * 1000);
                        }
                    }
                }
                // if(!StringUtil.isEmpty(jcustId)) {
                // if(jcustId.length()<3) {
                // jcustId="***"+jcustId;
                // }else {
                // jcustId="***"+jcustId.substring(jcustId.length()-3, jcustId.length());
                // }
                // }
                cmd.setJcustid(jcustId);
            }
            BiChannel shop = channelManager.getBiChannel(cmd.getChannelId());
            if (Transportator.SF.equals(lpcode) && ("5Nike-Global Inline官方商城".equalsIgnoreCase(shop.getCode()) || "5Nike-Global Swoosh 官方商城".equalsIgnoreCase(shop.getCode()))) {
                jpPath = "jasperprint/SFNKDNIKE_C.jasper";
                if (null != cmd.getIsNikePick() && !"".equals(cmd.getIsNikePick()) && cmd.getIsNikePick()) {
                    cmd.setIsNikePick(true);
                } else {
                    cmd.setIsNikePick(null);
                }

            }
            cmd.setSkuList(skuList);
            cmd.setOrderPrice(warehousePrintData.queryStaById(cmd.getId()).getTotalActual());
        }
        if (log.isDebugEnabled()) {
            log.debug("========end datas=========");
        }
        if (!StringUtil.isEmpty(slipCode)) {
            warehousePrintData.insertWhInfoTime(slipCode, billType, nodeType, userId, whOuId);
        }
        // 填写模板数据 jasperprint/ZTO.jasper
        if (log.isDebugEnabled()) {
            log.debug("========find JRDataSource=======");
        }
        JRDataSource dataSource = new JRBeanCollectionDataSource(datas);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jpPath);
        try {
            cjp.initializeReport(new HashMap<String, Object>(), dataSource);
            return cjp.print();
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 判断使用哪个EMS物流面单
     */
    public String returnJPPatchEMS(List<StaDeliveryInfoCommand> datas) {
        if (datas.size() > 0) {
            String ISC = datas.get(0).getIsSupportCod();
            if ("是".equals(ISC)) {
                // 代收货款
                return "jasperprint/EMSNKD_COD.jasper";
            } else {
                // 标准快递
                return "jasperprint/EMSNKD_STAN.jasper";
            }
        } else {
            // 标准快递
            return "jasperprint/EMSNKD_STAN.jasper";
        }
    }

    /**
     * 线下包裹模板数据特殊处理
     * 
     * @param lpcode
     * @param whOuId
     * @param datas
     * @param isOline 是否指定面单类型，null系统判断,true电子面单,false标准面单
     * @return slipCode：前置单据编码；lpcode:物流供应商编码
     *         billType:WhInfoTimeRefBillType.STA.getValue()(作业单1)；whOuId
     *         :仓库id；userId:用户id；datas：物流面单信息 isOline：false；
     * @throws Exception
     */
    private JasperPrint setOffLineExpressBillData(String slipCode, String lpcode2, int billType, int nodeType, Long whOuId, Long userId, TransPackageCommand pack, Boolean isOline) throws Exception {
        pack.setReceiverTel(pack.getReceiverTel().replace("null", ""));
        pack.setSenderTel(pack.getSenderTel().replaceAll("null", ""));
        List<StaDeliveryInfoCommand> datas = new ArrayList<StaDeliveryInfoCommand>();
        StaDeliveryInfoCommand staDeliveryInfo = new StaDeliveryInfoCommand();
        // TransPackageCommand pack = new TransPackageCommand();
        staDeliveryInfo.setExpName(pack.getTransportatorCode());// 物流供应商名称
        // staDeliveryInfo.setWarehouseName(pack.getOrder().getOpUnit().getName());// 仓库名称
        // staDeliveryInfo.setWarehouseName(pack.getWareName());// 仓库名称
        staDeliveryInfo.setSender(pack.getSender());// 寄件人
        staDeliveryInfo.setSenderAddress(pack.getSenderAddress());// 寄件人地址
        staDeliveryInfo.setStaCreateTime(new Date());// 创建时间
        staDeliveryInfo.setReceverTel(pack.getReceiverTel());// 收件人电话
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String printTime = sdf.format(date);
        staDeliveryInfo.setPrintTime(printTime);// 打印时间
        staDeliveryInfo.setInsuranceAmount(pack.getInsuranceAmount());// 保价金额
        staDeliveryInfo.setPic("");// 发货人
        staDeliveryInfo.setProvinceF("");// 发货省
        staDeliveryInfo.setCityF("");// 发货市
        staDeliveryInfo.setWarehouseId(whOuId);// 仓库ID
        staDeliveryInfo.setPackageId(pack.getId());// 包裹Id
        staDeliveryInfo.setTrackingNo(pack.getTransNo());// 面單號
        staDeliveryInfo.setSenderTel(pack.getSenderTel());// 寄件人联系方式
        staDeliveryInfo.setRefSlipCode(pack.getOrder().getCode());// 订单号
        staDeliveryInfo.setTransTimeTypeB(pack.getOrder().getTimeType() == null ? null : pack.getOrder().getTimeType().toString());// 设置时效类型
        if ("SF".equals(pack.getOrder().getTransportatorCode())) {
            staDeliveryInfo.setTransTypeB("1");// 普通 航运 电商特惠
        } else if ("SFDSTH".equals(pack.getOrder().getTransportatorCode())) {
            staDeliveryInfo.setTransTypeB("7");// 电商特惠
        }
        if (0 == pack.getIsnotLandTrans()) {
            staDeliveryInfo.setIsRailway(false);// 陆运否
        } else {
            staDeliveryInfo.setIsRailway(true);// 陆运是
        }
        staDeliveryInfo.setCountry(pack.getReceiverCountry());// 收货国家
        staDeliveryInfo.setProvince(pack.getReceiverProvince());// 收货省
        staDeliveryInfo.setCity(pack.getReceiverCity());// 收货市
        staDeliveryInfo.setDistrict(pack.getReceiverArea());// 收货区
        staDeliveryInfo.setAddress(pack.getReceiverAddress());// 收货详细地址
        staDeliveryInfo.setTelephone(pack.getReceiverTel());// 收货人电话
        staDeliveryInfo.setReceiver(pack.getReceiver());// 收货人
        staDeliveryInfo.setAmount(new BigDecimal(0));
        datas.add(staDeliveryInfo);

        String lpcode = pack.getTransportatorCode();// 物流编号
        if (log.isDebugEnabled()) {
            log.debug("======begin setExpressBillData====" + slipCode);// ??
        }

        // 查询模板
        Transportator trans = warehousePrintData.findByCode(pack.getTransportatorCode());
        Warehouse wh = warehousePrintData.getByOuId(whOuId);
        // Long ou1 = wareHouseManager.getCompanyIdByWarehouseOuId2(whOuId);
        // TransSfInfo sfInfo = wareHouseManager.findTransSfInfo(ou1);// 父仓库id
        BiChannel shop = wareHouseManager.getByCode2(pack.getCostCenterDetail());// 店铺code

        if (wh == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        String jpPath = trans.getJasperNormal();
        if (log.isDebugEnabled()) {
            log.debug("=======find is lpcode=======" + lpcode);
        }
        if (isOline == null) {
            // 顺丰电子面单判断
            if (wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder() && (Transportator.SF.equals(lpcode) || Transportator.SFCOD.equals(lpcode) || Transportator.SFDSTH.equals(lpcode) || Transportator.SFHK.equals(lpcode))) {
                if (wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            } else if (wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
                if (wh.getIsOlSto() != null && wh.getIsOlSto()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // Zto电子面单判断
            if (wh.getIsZtoOlOrder() != null && wh.getIsZtoOlOrder() && (Transportator.ZTOOD.equals(lpcode) || Transportator.ZTO.equals(lpcode))) {
                if (wh.getIsZtoOlOrder() != null && wh.getIsZtoOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // EMS电子面单判断
            if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder() && (Transportator.EMS.equals(lpcode) || Transportator.EMS_COD.equals(lpcode))) {
                if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // 天天快递电子面单判断
            if (wh.getIsTtkOlOrder() != null && wh.getIsTtkOlOrder() && Transportator.TTKDEX.equals(lpcode)) {
                if (wh.getIsTtkOlOrder() != null && wh.getIsTtkOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // YTO电子面单判断
            if (wh.getIsYtoOlOrder() != null && wh.getIsYtoOlOrder() && Transportator.YTO.equals(lpcode)) {
                if (wh.getIsYtoOlOrder() != null && wh.getIsYtoOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // 万象快递电子面单判断
            if (wh.getIsWxOlOrder() != null && wh.getIsWxOlOrder() && Transportator.WX.equals(lpcode)) {
                if (wh.getIsWxOlOrder() != null && wh.getIsWxOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
        } else if (isOline != null && isOline) {
            // 使用电子免单
            jpPath = trans.getJasperOnLine();
        } else {
            // 使用普通免单
            jpPath = trans.getJasperNormal();
        }
        // STO电子面单增加省份编码
        HashMap<String, List<WhTransAreaNo>> transAreaCache = warehousePrintData.transAreaCache();
        if (wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
            if (transAreaCache.isEmpty()) {
                List<WhTransAreaNo> list = warehousePrintData.getTransAreaByLpcode(lpcode);
                transAreaCache.put(lpcode, list);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("=======find datas.size======" + datas.size());// ??
        }

        for (StaDeliveryInfoCommand cmd : datas) {
            // 设置
            if (0 == pack.getIsnotLandTrans()) {
                cmd.setIsRailway(false);
            } else {
                cmd.setIsRailway(true);
            }
            cmd.setLpCode(pack.getOrder().getTransportatorCode());
            // String sn = cmd.getQuantity();
            String sn = "1";

            // NIKE 香港SF 显示格式
            if (trans.getExpCode().equals(Transportator.SFHK)) {
                BigDecimal transFree = (null != cmd.getOrderTransferFree() ? cmd.getOrderTransferFree() : new BigDecimal(0));
                BigDecimal ordertotalactual = (null != cmd.getOrdertotalactual() ? cmd.getOrdertotalactual() : new BigDecimal(0));
                ordertotalactual.add(transFree);
                cmd.setQuantity(sn);
                cmd.setOrdertotalactual(ordertotalactual);
            } else if (trans.getExpCode().equals(Transportator.SFGJ)) {// //SFGJ运单
                List<StaDeliveryInfoCommand> staDList = warehousePrintData.printCategeryAndQtyDeliveryByStaId(cmd.getId());
                for (int i = 0; i < staDList.size(); i++) {
                    if (i == 0) {
                        cmd.setCategoryName1(staDList.get(i).getCategoryName());
                        cmd.setCategoryQty1(staDList.get(i).getCategoryTotal());
                    } else if (i == 1) {
                        cmd.setCategoryName2(staDList.get(i).getCategoryName());
                        cmd.setCategoryQty2(staDList.get(i).getCategoryTotal());
                    } else if (i == 2) {
                        cmd.setCategoryName3(staDList.get(i).getCategoryName());
                        cmd.setCategoryQty3(staDList.get(i).getCategoryTotal());
                    }
                }
            } else {
                cmd.setQuantity("商品" + sn + "件");
            }
            // 匹配物流,增加省份编码
            if (!transAreaCache.isEmpty() && wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
                Set<String> keys = transAreaCache.keySet();
                Iterator<String> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    List<WhTransAreaNo> list = transAreaCache.get(key);
                    for (WhTransAreaNo whTransAreaNo : list) {
                        if (whTransAreaNo.getProvince().contains(cmd.getProvince()) || cmd.getProvince().contains(whTransAreaNo.getProvince())) {
                            cmd.setAreaNumber(whTransAreaNo.getAreaNumber());
                        }
                    }
                }
            }
            // 打字处理
            if (cmd.getBigAddress() != null) {
                if (cmd.getBigAddress().endsWith("-")) {
                    cmd.setBigAddress(cmd.getBigAddress().substring(0, cmd.getBigAddress().length() - 1));
                }
            }

            if (wh.getIsThirdPartyPaymentSF() != null && wh.getIsThirdPartyPaymentSF()) {
                cmd.setPayMethod("转第三方付");
            } else {
                cmd.setPayMethod("寄付月结");
            }
            // SF电子面单电话号码屏蔽后5位
            if (Transportator.SF.equals(lpcode) || Transportator.SFCOD.equals(lpcode) || Transportator.SFDSTH.equals(lpcode)) {
                cmd.setLogoImg(Transportator.SF_LOGO_IMAGE);
                // SF 月结账号设置
                String jcustId = null;
                if (shop == null) {
                    TransSfInfo result = whQuery.findTransSfInfoDefault();
                    jcustId = result.getjCustid();
                } else {
                    jcustId = sfJcustIdMap.get(whOuId + "-" + shop.getId());
                    if (jcustId == null) {
                        // 根据店铺配置制定SF月结账号和客户编码
                        ChannelWhRef channelWhRef = channelManager.findChannelRefByWhIdAndShopId(whOuId, shop.getId());
                        if (channelWhRef != null && StringUtils.hasText(channelWhRef.getSfJcustid())) {
                            jcustId = channelWhRef.getSfJcustid();
                            sfJcustIdMap.put(whOuId + "-" + shop.getId(), jcustId, 30 * 60 * 1000);
                        } else {
                            if (StringUtil.isEmpty(shop.getSfJcustid())) {
                                // 原逻辑
                                jcustId = sfJcustIdMap.get(whOuId + "-" + shop.getId());
                                if (jcustId == null) {
                                    TransSfInfo result = whQuery.findTransSfInfoDefault();
                                    jcustId = result.getjCustid();
                                    sfJcustIdMap.put(whOuId + "-" + shop.getId(), result.getjCustid(), 30 * 60 * 1000);
                                }
                            } else {
                                // 店铺配置逻辑
                                jcustId = shop.getSfJcustid();
                                sfJcustIdMap.put(whOuId + "-" + shop.getId(), jcustId, 30 * 60 * 1000);
                            }
                        }
                    }
                }
                cmd.setJcustid(jcustId);
            } else if (wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
                cmd.setLogoImg(Transportator.STO_LOGO_IMAGE);
                cmd.setQuantity("商品" + sn + "件");
            } else if ((Transportator.ZTO.equals(lpcode))) {
                cmd.setLogoImg(Transportator.ZTO_LOGO_IMAGE);
            }
            // 设置中文金额
            if (cmd.getAmount() != null) {
                cmd.setStrAmount(StringUtil.getCHSNumber(cmd.getAmount().intValue() + ".0"));
            }
            // 设置详细地址,出去重复省市区，如果不存在则拼接
            String address = cmd.getAddress();
            address = StringUtil.getRealAddress(address, cmd.getProvince(), cmd.getCity(), cmd.getDistrict(), true);
            address += "                                                                                                                #";
            cmd.setAddress(address);
            // 设置发运地
            if (StringUtils.hasText(wh.getDeparture())) {
                cmd.setDeparture(wh.getDeparture());
            }
            // 设置保价金额
            // BigDecimal insurance = warehousePrintData.getInsurance(cmd.getChannelCode(),
            // cmd.getInsuranceAmount());
            // if (null != insurance)
            // cmd.setInsuranceAmount(insurance);// 保价
            // else
            // cmd.setInsuranceAmount(null);// 非保价

            // 拼接配货信息

            // Map<String, Long> lineMap = wareHouseManager.findStalAndBarcodeByStaid(cmd.getId());

            // String skuList = "";
            // int i = 0;
            // for (String barCode : lineMap.keySet()) {
            // if (i == (lineMap.size() - 1)) {
            // skuList = skuList + barCode + "【" + lineMap.get(barCode) + "】";
            // } else if (i == 5) {
            // skuList = skuList + barCode + "【" + lineMap.get(barCode) + "】...";
            // break;
            // } else {
            // skuList = skuList + barCode + "【" + lineMap.get(barCode) + "】/";
            // }
            // i++;
            // }
            String owner = cmd.getOwner();
            // ck店铺 京东物流 面单上备注写死：不得拆内包装验货
            if ("CK".equalsIgnoreCase(owner) && Transportator.JD.equals(lpcode)) {
                jpPath = "jasperprint/CKJDKD.jasper";
            }
            // cmd.setSkuList(skuList);
        }
        JRDataSource dataSource = new JRBeanCollectionDataSource(datas);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jpPath);
        try {
            cjp.initializeReport(new HashMap<String, Object>(), dataSource);
            return cjp.print();
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 按配货清单连打面单
     * 
     * @param pickingListId
     * @param isOline 是否指定面单类型，null系统判断,true电子面单,false标准面单
     * @return
     * @throws Exception
     */
    public List<JasperPrint> printExpressBillByPickingList(Long pickingListId, Boolean isOline, Long userId) throws Exception {
        PickingList p = warehousePrintData.getPickingListById(pickingListId);
        if (p == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        List<Long> staList = warehousePrintData.findAllStaByPickingList(p.getId());
        for (Long id : staList) {
            JasperPrint jp = printExpressBillBySta(id, false, null, userId);
            list.add(jp);
        }
        return list;
    }

    /**
     * 线下包裹按作业单打面单
     * 
     * @param isOline 是否指定面单类型，null系统判断,true电子面单,false标准面单
     * @param staId
     * @return
     * @throws Exception
     */
    public JasperPrint printOffLineExpressBillBySta(TransPackageCommand pack, Boolean isParent, Boolean isOline, Long userId, Long wid) throws Exception {
        // StockTransApplication sta = warehousePrintData.getStaById(staId);
        // if (sta == null) {
        // throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        // }
        // EMS:22574664L
        // STO:21461462L
        // Long wid = warehousePrintData.getMainWarehouseId(staId);
        // List<StaDeliveryInfoCommand> datas =
        // warehousePrintData.findPrintExpressBillData(isParent, null, 21461462L);
        return setOffLineExpressBillData(null, pack.getTransportatorCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.PRINT_TRANK.getValue(), wid, userId, pack, isOline);
    }


    /**
     * 按作业单打面单
     * 
     * @param isOline 是否指定面单类型，null系统判断,true电子面单,false标准面单
     * @param staId
     * @return
     * @throws Exception
     */
    public JasperPrint printExpressBillBySta(Long staId, Boolean isParent, Boolean isOline, Long userId) throws Exception {
        StockTransApplication sta = warehousePrintData.getStaById(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Long wid = warehousePrintData.getMainWarehouseId(staId);
        List<StaDeliveryInfoCommand> datas = warehousePrintData.findPrintExpressBillData(isParent, null, staId);
        return setExpressBillData(sta.getRefSlipCode(), sta.getStaDeliveryInfo().getLpCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.PRINT_TRANK.getValue(), wid, userId, datas, isOline);
    }

    public List<JasperPrint> printExpressBillBySta5(String id, Boolean isParent, Boolean isOline, Long userId) throws Exception {
        List<PackageInfo> list = warehousePrintData.findByPackageInfoByStaId(Long.parseLong(id));
        if (null != list && list.size() > 0) {
            return printExpressBillBySta1(list.get(0).getId().toString(), isParent, isOline, userId);
        } else {
            return null;
        }

    }

    public List<JasperPrint> printExpressBillBySta1(String id, Boolean isParent, Boolean isOline, Long userId) throws Exception {
        String[] array = id.split(",");
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        for (int i = 0; i < array.length; i++) {
            list.add(printExpressBillBySta3(Long.parseLong(array[i]), isParent, isOline, userId));
        }
        return list;
    }



    public JasperPrint printExpressBillBySta3(Long id, Boolean isParent, Boolean isOline, Long userId) throws Exception {
        PackageInfo packageInfo = warehousePrintData.findByPackageInfoByLpcode(id);
        if (packageInfo == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        StockTransApplication sta = warehousePrintData.getStaById(packageInfo.getStaDeliveryInfo().getId());
        if (sta == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Long wid = warehousePrintData.getMainWarehouseId(sta.getId());
        // System.out.println(222);
        List<StaDeliveryInfoCommand> datas = warehousePrintData.findPrintExpressBillData2(null, sta.getId(), id.toString());
        // List<StaDeliveryInfoCommand> datas =
        // warehousePrintData.findPrintExpressBillData(isParent, null, sta.getId());

        return setExpressBillData(sta.getRefSlipCode(), sta.getStaDeliveryInfo().getLpCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.PRINT_TRANK.getValue(), wid, userId, datas, isOline);

    }

    /**
     * 按作业单、运单号打面单
     * 
     * @param isOline 是否指定面单类型，null系统判断,true电子面单,false标准面单
     * @param staId
     * @return
     * @throws Exception
     */
    public JasperPrint printExpressBillByTrankNo(Long staId, Boolean isOline, String packId, Long userId) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("start printExpressBillByTrankNo.....");
        }
        StockTransApplication sta = warehousePrintData.getStaById(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Long wid = warehousePrintData.getMainWarehouseId(staId);
        List<StaDeliveryInfoCommand> datas = warehousePrintData.findPrintExpressBillData2(null, staId, packId);
        return setExpressBillData(sta.getRefSlipCode(), sta.getStaDeliveryInfo().getLpCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.PRINT_TRANK.getValue(), wid, userId, datas, isOline);
    }

    @Override
    public JasperPrint printImportInfo() {
        List<ImportPrintData> listInfo = warehousePrintData.selectAllData();
        warehousePrintData.deleteAll(listInfo);
        JRDataSource dataSource = new JRBeanCollectionDataSource(listInfo);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter("jasperprint/SFDZ.jasper");
        try {
            cjp.initializeReport(new HashMap<String, Object>(), dataSource);
            return cjp.print();
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public JasperPrint printSingleVmiDelivery(Long staId, Boolean isOline, Long userId) throws Exception {
        StockTransApplication sta = warehousePrintData.getStaById(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Long wid = warehousePrintData.getMainWarehouseId(staId);
        List<StaDeliveryInfoCommand> datas = warehousePrintData.printSingleVmiDelivery(staId, null);
        return setExpressBillData(sta.getRefSlipCode(), sta.getStaDeliveryInfo().getLpCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.PRINT_TRANK.getValue(), wid, userId, datas, isOline);
    }


    /**
     * 根据箱号打印物流面单
     * 
     * @param staId
     * @param cartonId
     * @param isOline
     * @param userId
     * @return
     * @throws Exception
     */
    public JasperPrint printSingleVmiDeliveryByCarton(Long staId, Long cartonId, Boolean isOline, Long userId) throws Exception {

        StockTransApplication sta = warehousePrintData.getStaById(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Long wid = warehousePrintData.getMainWarehouseId(staId);
        List<StaDeliveryInfoCommand> datas = warehousePrintData.printSingleVmiDelivery(staId, cartonId);
        return setExpressBillData(sta.getRefSlipCode(), sta.getStaDeliveryInfo().getLpCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.PRINT_TRANK.getValue(), wid, userId, datas, isOline);
    }

    /**
     * 打印激活卡失败信息
     */
    @Override
    public List<JasperPrint> printSnCardErrorList(String staCode, String plCode, Long cmpOuid) throws Exception {
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "sncard_error_main.jasper";// 主模板
        String subReportPath = Constants.PRINT_TEMPLATE_FLIENAME + "sncard_error_detail.jasper";// 循环数据模板
        List<StockTransApplicationCommand> staList = snManager.printSnCardStaList(staCode, plCode);
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        for (StockTransApplicationCommand sta : staList) {
            List<SnCardErrorObj> sceList = new ArrayList<SnCardErrorObj>();
            SnCardErrorObj sce = snManager.printSnCardError(sta, cmpOuid);
            if (sce != null) {
                sceList.add(sce);
            } else {
                sceList = new ArrayList<SnCardErrorObj>();
                sceList.add(new SnCardErrorObj());
            }
            Map<String, Object> map = new HashMap<String, Object>();
            JRDataSource dataSource = new JRBeanCollectionDataSource(sceList);
            map.put("subReport", subReportPath);
            ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
            cjp.initializeReport(map, dataSource);
            list.add(cjp.print());
        }
        return list;
    }

    /**
     * 打印报关清单
     */
    @Override
    public List<JasperPrint> printImportEntryList(Long pickingListId) throws Exception {
        PickingList p = warehousePrintData.getPickingListById(pickingListId);
        if (p == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        List<Long> staList = warehousePrintData.findAllStaByPickingList(p.getId());
        for (Long id : staList) {
            JasperPrint jp = printImportEntryList1(id);
            list.add(jp);
        }
        return list;
    }

    public JasperPrint printImportEntryList1(Long staid) throws Exception {
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "import_entry_main.jasper";// 主模板
        String subReportPath = Constants.PRINT_TEMPLATE_FLIENAME + "import_entry_detail.jasper";// 循环数据模板
        List<ImportEntryListObj> ieList = new ArrayList<ImportEntryListObj>();
        Map<Long, ImportEntryListObj> ie = snManager.printImportEntryList(staid);
        if (ie != null) {
            ieList.addAll(ie.values());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        JRDataSource dataSource = new JRBeanCollectionDataSource(ieList);
        map.put("subReport", subReportPath);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(map, dataSource);
        return cjp.print();
    }

    /**
     * vmi退仓拣货单打印
     * 
     * @throws JasperReportNotFoundException
     * @throws JasperPrintFailureException
     */
    @Override
    public List<JasperPrint> vmiBackPrint(String staid) throws JasperReportNotFoundException, JasperPrintFailureException {
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        VMIBackOrder vmiHanderInfo = new VMIBackOrder();
        List<Object> fieldList = new ArrayList<Object>();
        List<VMIBackOrder> vmiList = warehousePrintData.findBackListByStaId(staid);
        // 填充iReport Head数据
        vmiHanderInfo = wareHouseManager.findBackPrintHanderInfo(staid);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.setTime(new Date());
        parameterMap.put("printTime", df.format(new Date()));// 打印时间
        parameterMap.put("outWarehouse", vmiHanderInfo.getHousename());// 仓库名称
        String staCode = vmiHanderInfo.getStacode();// 作业单号
        parameterMap.put("staCode", staCode);
        parameterMap.put("staCode1", staCode.substring(0, staCode.length() - 4));
        parameterMap.put("staCode2", staCode.substring(staCode.length() - 4, staCode.length()));
        parameterMap.put("planBillCount", vmiHanderInfo.getSkyqty());// 计划量
        parameterMap.put("createTime", (null != vmiHanderInfo.getCreatetime() ? df.format(vmiHanderInfo.getCreatetime()) : ""));// 创建时间
        if (vmiList == null || vmiList.isEmpty()) {
            log.debug("pickingList is null");
            vmiList = new ArrayList<VMIBackOrder>();
            vmiList.add(new VMIBackOrder());
        }
        fieldList = setVMIListDetail(vmiList);
        JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(Constants.PRINT_TEMPLATE_FLIENAME + "vmi_back_picking.jasper");
        cjp.initializeReport(parameterMap, ds);
        list.add(cjp.print());
        return list;
    }

    @Override
    public List<JasperPrint> printPickingBulkListMode1(List<PickingListCommand> commands, Integer pickZoneId, Long warehouseOuid, Long userId) throws JasperPrintFailureException, JRException, JasperReportNotFoundException, Exception {
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        for (PickingListCommand command : commands) {
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            List<Object> fieldList = new ArrayList<Object>();

            try {
                // 重置配货批中有取消订单时需要拣货的数量
                pdaPickingManager.resetPickingQtyByCancelSta(command.getId());
            } catch (Exception e) {
                log.error(command.getId() + "", e);
            }

            List<PickingListCommand> pickingList = warehousePrintData.findPickingListByPickingId(command.getId(), pickZoneId, warehouseOuid, null);
            if (null == pickingList || pickingList.size() == 0) {
                continue;
            }
            String whZoonCode = null;
            if (pickZoneId != null && pickingList != null && pickingList.size() > 0) {
                whZoonCode = pickingList.get(0).getWhZone();
            }
            // 配货清单打印，填充iReport Head数据
            parameterMap = setPickingListHead(command, warehouseOuid, whZoonCode);
            if (pickingList == null || pickingList.isEmpty()) {
                log.debug("pickingList is null");
                pickingList = new ArrayList<PickingListCommand>();
                pickingList.add(new PickingListCommand());
            }
            channelManager.logPackingPagePrint(command.getCode(), userId, WhInfoTimeRefNodeType.PRING_PICKING1);
            fieldList = setPickingListDetail(pickingList);
            JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
            ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(Constants.PRINT_TEMPLATE_FLIENAME + "pickingList1.jasper");
            cjp.initializeReport(parameterMap, ds);
            list.add(cjp.print());
        }
        return list;
    }

    @Override
    public List<JasperPrint> printPackingPageBulk(List<Long> plids, Long staid, Long userId, Boolean isPostPrint) {
        List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
        for (Long plid : plids) {
            jasperPrints.add(printPackingPage(plid, staid, userId, isPostPrint, null));
        }
        return jasperPrints;
    }

    @Override
    public List<JasperPrint> printExpressBillByWioListId(List<Long> wioList, String batchNo, Long ouId, Long userId) throws Exception {
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        for (Long id : wioList) {
            JasperPrint jp = printExpressBillByInvoiceOrder(id, false, null, ouId, userId);
            list.add(jp);
        }
        return list;
    }

    @Override
    public List<JasperPrint> printExpressBillByPickingBulkList(List<Long> plIds, Boolean isOline, Long userId) throws Exception {
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        for (int i = 0; i < plIds.size(); i++) {
            PickingList p = warehousePrintData.getPickingListById(plIds.get(i));
            if (p == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            List<Long> staList = warehousePrintData.findAllStaByPickingList(p.getId());
            for (Long id : staList) {
                JasperPrint jp = printExpressBillBySta(id, false, null, userId);
                list.add(jp);
            }
        }
        return list;
    }

    @Override
    public JasperPrint printExpressBillByInvoiceOrder(Long wioId, Boolean isParent, Boolean isOline, Long ouId, Long userId) throws Exception {
        WmsInvoiceOrder w = warehousePrintData.getWmsInvoiceOrderById(wioId);
        if (w == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<WmsInvoiceOrderCommand> datas = warehousePrintData.findWmsInvoiceOrderBillData(isParent, null, wioId);
        return setExpressBillData1(w.getLpCode(), WhInfoTimeRefBillType.INVOICE_ORDER.getValue(), WhInfoTimeRefNodeType.PRINT_TRANK.getValue(), ouId, userId, datas, isOline);
    }

    @Override
    public List<JasperPrint> printExpressBillForInvoiceOrder(List<Long> list) {
        List<JasperPrint> jl = new ArrayList<JasperPrint>();
        for (Long id : list) {
            JasperPrint jp = getJpByInvoiceOrder(id);
            jl.add(jp);
        }
        return jl;
    }

    @Override
    public JasperPrint getJpByInvoiceOrder(Long id) {
        WmsInvoiceOrder w = warehousePrintData.getWmsInvoiceOrderById(id);
        if (w == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<StaDeliveryInfoCommand> datas = warehousePrintData.findWmsInvoiceOrderBillData(id);
        return setExpressBillDataByInvoice(w.getLpCode(), datas);

    }

    private JasperPrint setExpressBillDataByInvoice(String lpcode, List<StaDeliveryInfoCommand> datas) {
        if (datas == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 查询模板
        Transportator trans = warehousePrintData.findByCode(lpcode);
        String jpPath = trans.getJasperOnLine();
        // STO电子面单增加省份编码
        HashMap<String, List<WhTransAreaNo>> transAreaCache = warehousePrintData.transAreaCache();
        if (Transportator.STO.equals(lpcode)) {
            if (transAreaCache.isEmpty()) {
                List<WhTransAreaNo> list = warehousePrintData.getTransAreaByLpcode(lpcode);
                transAreaCache.put(lpcode, list);
            }
        }
        for (StaDeliveryInfoCommand cmd : datas) {
            String sn = cmd.getQuantity();


            cmd.setQuantity("商品" + sn + "件");

            // 匹配物流,增加省份编码
            if (!transAreaCache.isEmpty() && Transportator.STO.equals(lpcode)) {
                Set<String> keys = transAreaCache.keySet();
                Iterator<String> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    List<WhTransAreaNo> list = transAreaCache.get(key);
                    for (WhTransAreaNo whTransAreaNo : list) {
                        if (whTransAreaNo.getProvince().contains(cmd.getProvince()) || cmd.getProvince().contains(whTransAreaNo.getProvince())) {
                            cmd.setAreaNumber(whTransAreaNo.getAreaNumber());
                        }
                    }
                }
            }
            // 打字处理
            if (cmd.getBigAddress() != null) {
                if (cmd.getBigAddress().endsWith("-")) {
                    cmd.setBigAddress(cmd.getBigAddress().substring(0, cmd.getBigAddress().length() - 1));
                }
            }


            cmd.setPayMethod("寄付月结");

            // SF电子面单电话号码屏蔽后5位
            if (Transportator.SF.equals(lpcode)) {
                cmd.setLogoImg(Transportator.SF_LOGO_IMAGE);
            } else if (Transportator.STO.equals(lpcode)) {
                cmd.setLogoImg(Transportator.STO_LOGO_IMAGE);
                cmd.setQuantity("商品" + sn + "件");
            }
            // 设置中文金额
            if (cmd.getAmount() != null) {
                // cmd.setStrAmount(formatIntToChinaBigNumStr(cmd.getAmount().intValue()));
                cmd.setStrAmount(StringUtil.getCHSNumber(cmd.getAmount().intValue() + ".0"));
            }
            // 设置详细地址,出去重复省市区，如果不存在则拼接
            String address = cmd.getAddress();
            address = StringUtil.getRealAddress(address, cmd.getProvince(), cmd.getCity(), cmd.getDistrict(), true);
            address += "                                                                                                                #";
            cmd.setAddress(address);
            // 设置发运地
            // if (StringUtils.hasText(wh.getDeparture())) {
            // cmd.setDeparture(wh.getDeparture());
            // }
            // 设置保价金额
            BigDecimal insurance = warehousePrintData.getInsurance(cmd.getChannelCode(), cmd.getInsuranceAmount());
            if (null != insurance)
                cmd.setInsuranceAmount(insurance);// 保价
            else
                cmd.setInsuranceAmount(null);// 非保价

            // 拼接配货信息
            // String[] staBarCode = cmd.getBarCode().split(",");
            // String[] staQuentity = cmd.getStaQuantity().split(",");
            String skuList = "";
            // if (staBarCode.length > 6) {
            // for (int i = 0; i < 6; i++) {
            // if (i == 5) {
            // skuList = skuList + staBarCode[i] + "【" + staQuentity[i] + "】...";
            // } else {
            // skuList = skuList + staBarCode[i] + "【" + staQuentity[i] + "】/";
            // }
            // }
            // } else {
            // for (int i = 0; i < staBarCode.length; i++) {
            // if (i == staBarCode.length - 1) {
            // skuList = skuList + staBarCode[i] + "【" + staQuentity[i] + "】";
            // } else {
            // skuList = skuList + staBarCode[i] + "【" + staQuentity[i] + "】/";
            // }
            // }
            // }
            cmd.setSkuList(skuList);
        }
        // 填写模板数据 jasperprint/ZTO.jasper
        JRDataSource dataSource = new JRBeanCollectionDataSource(datas);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jpPath);
        try {
            cjp.initializeReport(new HashMap<String, Object>(), dataSource);
            return cjp.print();
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 模板数据特殊处理
     * 
     * @param isOline 是否指定面单类型，null系统判断,true电子面单,false标准面单
     * @return
     * @throws Exception
     */
    private JasperPrint setExpressBillData1(String lpcode, int billType, int nodeType, Long whOuId, Long userId, List<WmsInvoiceOrderCommand> datas, Boolean isOline) throws Exception {
        if (datas == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 查询模板
        Transportator trans = warehousePrintData.findByCode(lpcode);
        Warehouse wh = warehousePrintData.getByOuId(whOuId);
        if (wh == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        String jpPath = trans.getJasperNormal();
        if (isOline == null) {
            // 顺丰电子面单判断
            if (wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder() && (Transportator.SF.equals(lpcode) || Transportator.SFCOD.equals(lpcode) || Transportator.SFDSTH.equals(lpcode) || Transportator.SFHK.equals(lpcode))) {
                if (wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            } else if (wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
                if (wh.getIsOlSto() != null && wh.getIsOlSto()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // Zto电子面单判断
            if (wh.getIsZtoOlOrder() != null && wh.getIsZtoOlOrder() && (Transportator.ZTOOD.equals(lpcode) || Transportator.ZTO.equals(lpcode))) {
                if (wh.getIsZtoOlOrder() != null && wh.getIsZtoOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // EMS电子面单判断
            if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder() && (Transportator.EMS.equals(lpcode) || Transportator.EMS_COD.equals(lpcode))) {
                if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // 天天快递电子面单判断
            if (wh.getIsTtkOlOrder() != null && wh.getIsTtkOlOrder() && Transportator.TTKDEX.equals(lpcode)) {
                if (wh.getIsTtkOlOrder() != null && wh.getIsTtkOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // YTO电子面单判断
            if (wh.getIsYtoOlOrder() != null && wh.getIsYtoOlOrder() && Transportator.YTO.equals(lpcode)) {
                if (wh.getIsYtoOlOrder() != null && wh.getIsYtoOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // 万象快递电子面单判断
            if (wh.getIsWxOlOrder() != null && wh.getIsWxOlOrder() && Transportator.WX.equals(lpcode)) {
                if (wh.getIsWxOlOrder() != null && wh.getIsWxOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
        } else if (isOline != null && isOline) {
            // 使用电子免单
            jpPath = trans.getJasperOnLine();
        } else {
            // 使用普通免单
            jpPath = trans.getJasperNormal();
        }
        // STO电子面单增加省份编码
        HashMap<String, List<WhTransAreaNo>> transAreaCache = warehousePrintData.transAreaCache();
        if (wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
            if (transAreaCache.isEmpty()) {
                List<WhTransAreaNo> list = warehousePrintData.getTransAreaByLpcode(lpcode);
                transAreaCache.put(lpcode, list);
            }
        }
        // 填写模板数据 jasperprint/ZTO.jasper
        JRDataSource dataSource = new JRBeanCollectionDataSource(datas);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jpPath);
        try {
            cjp.initializeReport(new HashMap<String, Object>(), dataSource);
            return cjp.print();
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public JasperPrint skuImperfect(Long imperfectId) throws JasperReportNotFoundException, JasperPrintFailureException {
        String jasper = "";
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<Object> fieldList = new ArrayList<Object>();
        SkuImperfectCommand commands = warehousePrintData.findSkuImperfect(imperfectId);
        parameterMap = setSkuImperfect(commands);
        if (commands == null) {
            log.debug("pickingList is null");
            commands = new SkuImperfectCommand();
        }
        fieldList = setSkuImperfectDetail(commands);
        JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
        if (commands.getOwner().contains("puma") || commands.getOwner().contains("PUMA") || commands.getOwner().contains("Puma") || commands.getOwner().contains("彪马")) {// puma模板
            jasper = Constants.PRINT_TEMPLATE_FLIENAME + "puma_sku_imperfect.jasper";
            parameterMap.put("logImage", "print_img/puma_logo.jpg");
        } else {// 默认模板
            jasper = Constants.PRINT_TEMPLATE_FLIENAME + "sku_imperfect.jasper";
        }
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jasper);
        cjp.initializeReport(parameterMap, ds);
        return cjp.print();
    }

    @Override
    public List<JasperPrint> skuImperfects(String Ids) throws JasperReportNotFoundException, JasperPrintFailureException {
        String jasper = "";
        List<JasperPrint> jasperPrints = new ArrayList<JasperPrint>();
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        String imperfectIds[] = Ids.split(",");
        for (int i = 0; i < imperfectIds.length; i++) {
            List<Object> fieldList = new ArrayList<Object>();
            SkuImperfectCommand commands = warehousePrintData.findSkuImperfect(Long.parseLong(imperfectIds[i]));
            parameterMap = setSkuImperfect(commands);
            if (commands == null) {
                log.debug("pickingList is null");
                commands = new SkuImperfectCommand();
            }
            fieldList = setSkuImperfectDetail(commands);
            JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
            if (commands.getOwner().contains("puma") || commands.getOwner().contains("PUMA") || commands.getOwner().contains("Puma") || commands.getOwner().contains("彪马")) {// puma模板
                jasper = Constants.PRINT_TEMPLATE_FLIENAME + "puma_sku_imperfect.jasper";
                parameterMap.put("logImage", "print_img/puma_logo.jpg");
            } else {// 默认模板
                jasper = Constants.PRINT_TEMPLATE_FLIENAME + "sku_imperfect.jasper";
            }
            ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jasper);
            cjp.initializeReport(parameterMap, ds);
            jasperPrints.add(cjp.print());
        }
        return jasperPrints;
    }

    /**
     * 
     * 
     * @param pickingList
     * @return
     */
    private List<Object> setSkuImperfectDetail(SkuImperfectCommand calendar) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();
        map.put("owner", calendar.getOwner());
        if (StringUtils.hasText(calendar.getStaCode())) {
            map.put("staCode", calendar.getStaCode());
            map.put("staCode1", calendar.getStaCode());
        } else {
            map.put("staCode", calendar.getStaCode() == null ? "" : calendar.getStaCode());
        }
        map.put("skuCode", calendar.getSkuCode());
        map.put("barCode", calendar.getBarCode());
        map.put("supplierCode", calendar.getSupplierCode());
        map.put("skuName", calendar.getSkuName());
        map.put("size", calendar.getSize());
        map.put("defectType", calendar.getDefectType());
        map.put("defectWhy", calendar.getDefectWhy());
        map.put("defectCode", calendar.getDefectCode());
        map.put("memo", calendar.getMemo() == null ? "" : calendar.getMemo());
        map.put("poId", calendar.getPoId() == null ? "" : calendar.getPoId());
        map.put("factoryCode", calendar.getFactoryCode() == null ? "" : calendar.getFactoryCode());
        map.put("size", calendar.getSize() == null ? "" : calendar.getSize());
        map.put("createTime", f.format(calendar.getCreateTime()));
        // map.put("logImage","print_img/nike_logo.jpg");
        // map.put("logImage","print_img/puma_logo.jpg");
        list.add(map);
        return list;
    }

    public Map<String, Object> setSkuImperfect(SkuImperfectCommand calendar) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("owner", calendar.getOwner() == null ? "" : calendar.getOwner());
        if (StringUtils.hasText(calendar.getStaCode())) {
            map.put("staCode", calendar.getStaCode());
            map.put("staCode1", calendar.getStaCode());
        } else {
            map.put("staCode", calendar.getStaCode() == null ? "" : calendar.getStaCode());
        }
        map.put("skuCode", calendar.getSkuCode() == null ? "" : calendar.getSkuCode());
        map.put("barCode", calendar.getBarCode() == null ? "" : calendar.getBarCode());
        map.put("supplierCode", calendar.getSupplierCode() == null ? "" : calendar.getBarCode());
        map.put("skuName", calendar.getSkuName() == null ? "" : calendar.getSkuName());
        map.put("size", calendar.getSize() == null ? "" : calendar.getSize());
        map.put("defectType", calendar.getDefectType() == null ? "" : calendar.getDefectType());
        map.put("defectWhy", calendar.getDefectWhy() == null ? "" : calendar.getDefectWhy());
        map.put("defectCode", calendar.getDefectCode() == null ? "" : calendar.getDefectCode());
        map.put("memo", calendar.getMemo() == null ? "" : calendar.getMemo());
        map.put("poId", calendar.getPoId() == null ? "" : calendar.getPoId());
        map.put("factoryCode", calendar.getFactoryCode() == null ? "" : calendar.getFactoryCode());
        map.put("size", calendar.getSize() == null ? "" : calendar.getSize());
        map.put("createTime", f.format(calendar.getCreateTime()));
        // map.put("logImage","print_img/nike_logo.jpg");
        // map.put("logImage","print_img/puma_logo.jpg");
        return map;
    }

    public Map<String, Object> setSkuImperfectStv(ImperfectStvCommand calendar) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("owner", calendar.getOwner());
        if (StringUtils.hasText(calendar.getStaCode())) {
            map.put("staCode", calendar.getStaCode());
            map.put("staCode1", calendar.getStaCode());
        } else {
            map.put("staCode", calendar.getStaCode() == null ? "" : calendar.getStaCode());
        }
        map.put("skuCode", calendar.getSkuCode());
        map.put("barCode", calendar.getBarCode());
        map.put("supplierCode", calendar.getSupplierCode());
        map.put("skuName", calendar.getSkuName());
        map.put("size", calendar.getKeyProperties());
        map.put("defectType", calendar.getDefectType());
        map.put("defectWhy", calendar.getDefectWhy());
        map.put("defectCode", calendar.getDefectCode());
        map.put("memo", calendar.getMemo() == null ? "" : calendar.getMemo());
        map.put("poId", calendar.getPoId() == null ? "" : calendar.getPoId());
        map.put("factoryCode", calendar.getFactoryCode() == null ? "" : calendar.getFactoryCode());
        map.put("size", calendar.getSize() == null ? "" : calendar.getSize());
        return map;
    }

    private List<Object> setSkuImperfectStvDetail(ImperfectStvCommand calendar) {
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = null;
        map = new HashMap<String, Object>();
        map.put("owner", calendar.getOwner());
        if (StringUtils.hasText(calendar.getStaCode())) {
            map.put("staCode", calendar.getStaCode());
            map.put("staCode1", calendar.getStaCode());
        } else {
            map.put("staCode", calendar.getStaCode() == null ? "" : calendar.getStaCode());
        }
        map.put("skuCode", calendar.getSkuCode());
        map.put("barCode", calendar.getBarCode());
        map.put("supplierCode", calendar.getSupplierCode());
        map.put("skuName", calendar.getSkuName());
        map.put("size", calendar.getKeyProperties());
        map.put("defectType", calendar.getDefectType());
        map.put("defectWhy", calendar.getDefectWhy());
        map.put("defectCode", calendar.getDefectCode());
        map.put("memo", calendar.getMemo() == null ? "" : calendar.getMemo());
        map.put("poId", calendar.getPoId() == null ? "" : calendar.getPoId());
        map.put("factoryCode", calendar.getFactoryCode() == null ? "" : calendar.getFactoryCode());
        map.put("size", calendar.getSize() == null ? "" : calendar.getSize());
        list.add(map);
        return list;
    }

    @Override
    public JasperPrint skuImperfectStv(Long imperfectStvId) throws JasperReportNotFoundException, JasperPrintFailureException {
        String jasper = "";
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<Object> fieldList = new ArrayList<Object>();
        ImperfectStvCommand stv = warehousePrintData.findSkuImperfectStv(imperfectStvId);
        parameterMap = setSkuImperfectStv(stv);
        if (stv == null) {
            log.debug("pickingList is null");
            stv = new ImperfectStvCommand();
        }
        fieldList = setSkuImperfectStvDetail(stv);
        JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
        if (stv.getOwner().contains("puma") || stv.getOwner().contains("PUMA") || stv.getOwner().contains("Puma") || stv.getOwner().contains("彪马")) {// puma模板
            jasper = Constants.PRINT_TEMPLATE_FLIENAME + "puma_sku_imperfect.jasper";
            parameterMap.put("logImage", "print_img/puma_logo.jpg");
        } else {// 默认模板
            jasper = Constants.PRINT_TEMPLATE_FLIENAME + "sku_imperfect.jasper";
        }
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jasper);
        cjp.initializeReport(parameterMap, ds);
        return cjp.print();
    }

    // public Map<String, Object> setSkuImperfect(ImperfectStvCommand stv) {
    // Map<String, Object> map = new HashMap<String, Object>();
    // map.put("owner", calendar.getOwner());
    // map.put("staCode", calendar.getStaCode());
    // map.put("skuCode", calendar.getSkuCode());
    // map.put("barCode", calendar.getBarCode());
    // map.put("supplierCode", calendar.getSupplierCode());
    // map.put("skuName", calendar.getSkuName());
    // map.put("size", calendar.getSize());
    // map.put("defectType", calendar.getDefectType());
    // map.put("defectWhy", calendar.getDefectWhy());
    // map.put("defectCode", calendar.getDefectCode());
    // return map;
    // }

    @Override
    public List<JasperPrint> printTurnoverBoxBarCode(Long tId) throws Exception {
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        WhContainer gl = autoOutboundTurnboxManager.getWhContainerById(tId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memo", gl.getCode());
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "container.jasper";
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(map, null);
        JasperPrint jpJasperPrint = cjp.print();
        list.add(jpJasperPrint);
        list.add(jpJasperPrint);
        list.add(jpJasperPrint);
        return list;
    }

    /**
     * 打印报关清单
     */
    @Override
    public List<JasperPrint> printImportEntryLists(Long pickingListId) throws Exception {
        PickingList p = warehousePrintData.getPickingListById(pickingListId);
        if (p == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        List<Long> staList = warehousePrintData.findAllStaByPickingList(p.getId());
        for (Long id : staList) {
            JasperPrint jp = printImportEntryList2(id);
            list.add(jp);
        }
        return list;
    }

    /**
     * 打印澳门海关单
     */
    @Override
    public List<JasperPrint> printImportHGDEntryLists(Long pickingListId, Long staId) throws Exception {
        if (pickingListId != null) {
            PickingList p = warehousePrintData.getPickingListById(pickingListId);
            if (p == null) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            List<JasperPrint> list = new ArrayList<JasperPrint>();
            List<Long> staList = warehousePrintData.findAllStaByPickingList(p.getId());
            for (Long id : staList) {
                JasperPrint jp = printImportHGDEntryList(id);
                list.add(jp);
            }
            return list;
        } else if (staId != null) {
            List<JasperPrint> list = new ArrayList<JasperPrint>();
            JasperPrint jp = printImportHGDEntryList(staId);
            list.add(jp);
            return list;
        } else {
            return null;
        }
    }

    public JasperPrint printImportEntryList2(Long staid) throws Exception {
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "import_Macaoentry_main.jasper";// 主模板
        String subReportPath = Constants.PRINT_TEMPLATE_FLIENAME + "import_Macaoentry_detail.jasper";// 循环数据模板
        List<ImportEntryListObj> ieList = new ArrayList<ImportEntryListObj>();
        Map<Long, ImportEntryListObj> ie = snManager.printImportMacaoEntryList(staid);
        StockTransApplicationCommand sto = snManager.findPackageCount(staid);
        if (ie != null) {
            ieList.addAll(ie.values());
        }
        Integer num = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (ImportEntryListObj ies : ieList) {
            List<ImportEntryListLinesObj> importlist = ies.getLines();
            for (ImportEntryListLinesObj importEntryListLines : importlist) {
                num = num + Integer.parseInt(importEntryListLines.getQty());
                totalPrice = totalPrice.add(importEntryListLines.getPrice());
            }
            ies.setNum(num);
            ies.setTotalPrice(totalPrice);
            if (null != sto && !"".equals(sto) && null != sto.getCompleteQuantity() && !"".equals(sto.getCompleteQuantity()) && 0 != sto.getCompleteQuantity()) {
                ies.setPackageCount(sto.getCompleteQuantity().toString());
            } else {
                ies.setPackageCount("1");
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        JRDataSource dataSource = new JRBeanCollectionDataSource(ieList);
        map.put("subReport", subReportPath);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(map, dataSource);
        return cjp.print();
    }

    public JasperPrint printImportHGDEntryList(Long staid) throws Exception {
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "import_Macao_Hgd_main.jasper";// 主模板
        String subReportPath = Constants.PRINT_TEMPLATE_FLIENAME + "import_Macao_Hgd_detail.jasper";// 循环数据模板
        List<ImportHGDEntryListObj> ieList = new ArrayList<ImportHGDEntryListObj>();
        Map<Long, ImportHGDEntryListObj> ie = warehousePrintData.printImportMacaoHGDEntryList(staid);
        if (ie != null) {
            ieList.addAll(ie.values());
        }
        Map<String, Object> map = new HashMap<String, Object>();
        JRDataSource dataSource = new JRBeanCollectionDataSource(ieList);
        map.put("subReport", subReportPath);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(map, dataSource);
        return cjp.print();
    }

    /**
     * Nike装箱汇总 kai.zhu
     * 
     * @param cartonid
     * @return
     * @throws Exception
     */
    @Override
    public JasperPrint printPackingSummaryForNike(Long staId, Long cartonId) throws Exception {
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + "return_create_main.jasper";
        // List<OutBoundPackageInfoObj> opoList = printOutBoundPackageInfoList(staId);// 数据
        PackingSummaryForNike nike = warehousePrintData.findPackingSummaryForNike(staId, cartonId);
        Map<String, Object> params = new HashMap<String, Object>();
        if (nike != null) {
            params.put("staCode", nike.getCode() == null ? "" : nike.getCode());
            params.put("indexBox", nike.getSeqNo() == null ? "" : nike.getSeqNo());
            params.put("outAddress", nike.getName() == null ? "" : nike.getName());
            params.put("inAddress", nike.getToLocation() == null ? "" : nike.getToLocation());
            params.put("handover", "杨孝广");// 固定
            params.put("countBox", "");
        }
        Long count = warehousePrintData.findPackingCheckCount(staId);
        if (count == 0) {
            Carton carton = warehousePrintData.findCartonById(cartonId);
            if (carton != null) {
                params.put("countBox", carton.getSeqNo());
            }
        }
        List<Object> list = new ArrayList<Object>();
        list.add(params);
        JRDataSource dataSource = new JRBeanCollectionDataSource(list);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        cjp.initializeReport(params, dataSource);
        return cjp.print();
    }

    @Override
    public JasperPrint printSlipCode(String slipCode) throws JasperReportNotFoundException, JasperPrintFailureException {
        String jasper = "";
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        List<Object> fieldList = new ArrayList<Object>();
        parameterMap.put("slipCode", slipCode);
        parameterMap.put("slipCode1", slipCode);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("slipCode", slipCode);
        map.put("slipCode1", slipCode);
        fieldList.add(map);
        JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
        jasper = Constants.PRINT_TEMPLATE_FLIENAME + "slipCode.jasper";
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jasper);
        cjp.initializeReport(parameterMap, ds);
        return cjp.print();
    }

    /**
     * 打印货箱编码
     * 
     * @param staId
     * @return
     * @throws Exception
     */
    public List<JasperPrint> printContainerCode(Long staId, String type) throws Exception {
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        String jasper = "";
        StockTransApplication sta = warehousePrintData.getStaById(staId);
        int i = 1;
        String cc = sta.getContainerCode();
        if (type != null && "in".equals(type)) {
            // i = 0;
            cc = wareHouseManagerExecute.containerCodeSetting(staId);
        }

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("memo", cc);
        jasper = Constants.PRINT_TEMPLATE_FLIENAME + "container.jasper";
        for (; i < 2; i++) {
            ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jasper);
            cjp.initializeReport(parameterMap, null);
            list.add(cjp.print());
        }

        return list;
    }

    /**
     * 打印相关单据号
     * 
     * @param staId
     * @return
     * @throws Exception
     */
    public List<JasperPrint> printSlipCode(Long staId) throws Exception {
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        String jasper = "";
        StockTransApplication sta = warehousePrintData.getStaById(staId);
        String cc = sta.getCode();

        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("memo", cc);
        jasper = Constants.PRINT_TEMPLATE_FLIENAME + "autoSlipCode.jasper";
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jasper);
        cjp.initializeReport(parameterMap, null);
        list.add(cjp.print());

        return list;
    }

    /**
     * PDA拣货条码打印
     * 
     * @return
     */
    public List<JasperPrint> printPdaBarCode(Long plId) throws Exception {
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        String jasper = "";
        // pdaPickingManager.resetPickingQtyByCancelSta(plId);
        List<String> bcs = pdaPickingManager.findPickingBatchBarCode(plId);
        jasper = Constants.PRINT_TEMPLATE_FLIENAME + "batchBarCode.jasper";
        for (String b : bcs) {
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("memo", b);
            ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jasper);
            cjp.initializeReport(parameterMap, null);
            list.add(cjp.print());
        }

        return list;
    }

    /**
     * PDA拣货条码打印
     * 
     * @return
     */
    public List<JasperPrint> printPdaBarCodeS(String[] plIds) throws Exception {
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        String jasper = "";
        String ids = null;
        for (int i = 0; i < plIds.length; i++) {
            if (ids == null) {
                ids = plIds[i];
            } else {
                ids = ids + "," + plIds[i];
            }
            // pdaPickingManager.resetPickingQtyByCancelSta(Long.parseLong(plIds[i]));
        }
        List<String> bcs = pdaPickingManager.findPickingBatchBarCodeS(ids);
        jasper = Constants.PRINT_TEMPLATE_FLIENAME + "batchBarCode.jasper";
        for (String b : bcs) {
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("memo", b);
            ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jasper);
            cjp.initializeReport(parameterMap, null);
            list.add(cjp.print());
        }
        return list;
    }

    @Override
    public JasperPrint printExpressBillByTrankNo1(Long staId, Boolean isOline, String packId, Long userId, String newLpcode) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("start printExpressBillByTrankNo.....");
        }
        StockTransApplication sta = warehousePrintData.getStaById(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        Long wid = warehousePrintData.getMainWarehouseId(staId);
        List<StaDeliveryInfoCommand> datas = warehousePrintData.findPrintExpressBillData2(null, staId, packId);
        return setExpressBillData(sta.getRefSlipCode(), newLpcode, WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.PRINT_TRANK.getValue(), wid, userId, datas, isOline);
    }

    @Override
    public JasperPrint printPackingPageReturn(Long plid, Long staid, Long userId, Boolean isPostPrint) {
        if (isPostPrint == null) {
            isPostPrint = false;
        }
        String templateType = channelManager.findPickingListTemplateType(plid, staid);
        if (log.isDebugEnabled()) {
            log.debug("=====find tmplateType=====" + templateType);
        }
        PackingPageInterface pp = packingPageFactory.getPackingPage(templateType);
        boolean isPostpositionPage = channelManager.isPostpositionPackingPage(plid, staid, isPostPrint);
        if (log.isDebugEnabled()) {
            log.debug("=====find is isPostpositionPage=========" + isPostpositionPage);
        }
        String mainTemplate = "";
        String subTemplate = "";
        // 获取 前置 OR 后置模板
        if (isPostpositionPage) {
            // 如果是后置装详清单
            // 获取主模板
            mainTemplate = pp.getPostpositionalMainTemplate();
            // 获取子模板
            subTemplate = pp.getPostpositionalSubTemplate();
        } else {
            // 获取主模板
            mainTemplate = "jasperprint/packing_list_nike_main_new1.jasper";
            // 获取子模板
            subTemplate = "jasperprint/packing_list_nike_gw_new1.jasper";
        }
        List<PackingObj> dataList = new ArrayList<PackingObj>();
        Map<Long, PackingObj> tmpList = channelManager.findPackingPageShowLocationNike(plid, staid);
        if (tmpList != null) {
            dataList.addAll(tmpList.values());
        }
        if (dataList == null || dataList.isEmpty()) {
            dataList.add(new PackingObj());
        }
        channelManager.logPackingPagePrint(plid, userId, WhInfoTimeRefNodeType.PRINT_PICKING_LIST);
        JRDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        Map<String, Object> map = new HashMap<String, Object>();
        if (isPostpositionPage) {
            map.put("subReport", subTemplate);
        } else {
            InputStream is;
            try {
                is = this.getClass().getClassLoader().getResource(subTemplate).openStream();
                map.put("subReport", is);
            } catch (IOException e) {
                log.error("", e);
            }
            pp.setUserDefined(map);
        }
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(mainTemplate);
        try {
            cjp.initializeReport(map, dataSource);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        }
        try {
            return cjp.print();
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        }
        return null;
    }



    private void greAntiFake(List<PackingObj> dataList) {
        for (PackingObj po : dataList) {
            String skuMsg = po.getSoCode() + "/";
            for (PackingLineObj plo : po.getLines()) {
                skuMsg += plo.getSkuCode() + "/";
                skuMsg += plo.getQty() + "/";
            }
            String afMsg = warehousePrintData.greAntiFakeCode(skuMsg, Constants.CODE_KEY);
            po.setAntiFake(afMsg);

        }
    }


    @Override
    public List<JasperPrint> diekingLinePrint(String staid, String plList) throws JasperReportNotFoundException, JasperPrintFailureException {
        String[] ids = plList.split(",");
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        for (String sid : ids) {
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            Long id = Long.parseLong(sid);
            RtwDieking rtw = rtwDieKingLineManager.getRtwDiekingById(id);
            VMIBackOrder vmiHanderInfo = new VMIBackOrder();
            List<Object> fieldList = new ArrayList<Object>();
            List<VMIBackOrder> vmiList = warehousePrintData.findDiekingLineListByDiekingId(id, Long.parseLong(staid));
            // 填充iReport Head数据
            vmiHanderInfo = wareHouseManager.findBackPrintHanderInfo(staid);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            calendar.setTime(new Date());
            parameterMap.put("printTime", df.format(new Date()));// 打印时间
            parameterMap.put("outWarehouse", vmiHanderInfo.getHousename());// 仓库名称
            String staCode = vmiHanderInfo.getStacode();// 作业单号
            parameterMap.put("staCode", staCode);
            parameterMap.put("staCode1", staCode.substring(0, staCode.length() - 4));
            parameterMap.put("staCode2", staCode.substring(staCode.length() - 4, staCode.length()));
            parameterMap.put("planBillCount", rtw.getPlanQuantity().toString());// 计划量
            parameterMap.put("createTime", (null != vmiHanderInfo.getCreatetime() ? df.format(vmiHanderInfo.getCreatetime()) : ""));// 创建时间
            if (vmiList == null || vmiList.isEmpty()) {
                log.debug("pickingList is null");
                vmiList = new ArrayList<VMIBackOrder>();
                vmiList.add(new VMIBackOrder());
            }
            fieldList = setVMIListDetail(vmiList);
            JRDataSource ds = new JRBeanCollectionDataSource(fieldList);
            ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(Constants.PRINT_TEMPLATE_FLIENAME + "vmi_back_picking.jasper");
            cjp.initializeReport(parameterMap, ds);
            list.add(cjp.print());
        }
        return list;
    }

    /**
     * PDA退仓拣货条码打印
     * 
     * @return
     */
    public List<JasperPrint> printPdaBarCodeRtw(String[] plIds) throws Exception {
        List<JasperPrint> list = new ArrayList<JasperPrint>();
        String jasper = "";
        jasper = Constants.PRINT_TEMPLATE_FLIENAME + "batchBarCode.jasper";
        for (int i = 0; i < plIds.length; i++) {
            RtwDieking r = rtwDieKingLineManager.getRtwDiekingById(Long.parseLong(plIds[i]));
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            parameterMap.put("memo", r.getBatchCode());
            ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(jasper);
            cjp.initializeReport(parameterMap, null);
            list.add(cjp.print());
        }
        return list;
    }

    @Override
    public JasperPrint printReturnWarehousePackingInfo(Long staId, Long ouId, PrintCustomize pc) throws Exception {
        if (staId == null || ouId == null) return null;
        String reportPath = null;
        String subReportPath = null;
        if (pc != null) {
            reportPath = Constants.PRINT_TEMPLATE_FLIENAME + pc.getMasterTemplet();
            subReportPath = Constants.PRINT_TEMPLATE_FLIENAME + pc.getSubTemplet();
        }
        List<OutBoundSendInfo> outInfo = null;
        if (pc.getDataType() != null && pc.getDataType().equals("BY_CARTON")) {
            outInfo = constructRWPackingInfo(staId, ouId);
        }
        JRDataSource dataSource = new JRBeanCollectionDataSource(outInfo);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subReport", subReportPath);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        try {
            cjp.initializeReport(map, dataSource);
            return cjp.print();
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        }
        return null;
    }

    private List<OutBoundSendInfo> constructRWPackingInfo(Long staId, Long ouId) throws Exception {
        StockTransApplication sta = warehousePrintData.getStaById(staId);
        if (sta == null) throw new BusinessException(ErrorCode.STA_IS_NULL);
        List<OutBoundSendInfo> outInfoList = new ArrayList<OutBoundSendInfo>();
        OutBoundSendInfo outBoundSendInfo = warehousePrintData.findRetrnWhPickingInfo(staId);
        SimpleDateFormat d = new SimpleDateFormat("YYYY-MM-dd");
        outBoundSendInfo.setReturnDate(d.format(new Date()));
        if (outBoundSendInfo != null) {
            outBoundSendInfo = warehousePrintData.findRetrnWhPickingInfoLine(staId, outBoundSendInfo);
        }
        outInfoList.add(outBoundSendInfo);
        return outInfoList;
    }

    public JasperPrint printNikeCrwLabel(Long cartonId) throws Exception {
        if (cartonId == null) return null;

        PrintCustomize pc = printCustomizeManager.findPrintCustomizeByCartonIdAndType(cartonId, 12);
        if (pc == null) {
            return null;
        }
        List<StaLfCommand> outBoundSendInfos = new ArrayList<StaLfCommand>();
        JRDataSource dataSource = new JRBeanCollectionDataSource(outBoundSendInfos);
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + pc.getMasterTemplet();
        StaLfCommand slc = warehousePrintData.findNikeOutBoundLabel(cartonId);
        outBoundSendInfos.add(slc);
        dataSource = new JRBeanCollectionDataSource(outBoundSendInfos);
        Map<String, Object> map = new HashMap<String, Object>();
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        try {
            cjp.initializeReport(map, dataSource);
            return cjp.print();
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        }
        return null;
    }


    public JasperPrint printNikeCrwPod(Long staId, Long cartonId) throws Exception {
        if (staId == null && cartonId == null) {
            return null;
        }
        PrintCustomize pc = null;
        if (cartonId != null) {
            pc = printCustomizeManager.findPrintCustomizeByCartonIdAndType(cartonId, 13);
        }
        if (staId != null) {
            pc = printCustomizeManager.findPrintCustomizeByStaIdAndType(staId, 13);
        }
        if (pc == null) {
            return null;
        }
        List<StaLfCommand> outBoundSendInfos = new ArrayList<StaLfCommand>();
        JRDataSource dataSource = new JRBeanCollectionDataSource(outBoundSendInfos);
        String reportPath = Constants.PRINT_TEMPLATE_FLIENAME + pc.getMasterTemplet();
        StaLfCommand slc = warehousePrintData.printNikeCrwPod(staId, cartonId);
        outBoundSendInfos.add(slc);
        dataSource = new JRBeanCollectionDataSource(outBoundSendInfos);
        Map<String, Object> map = new HashMap<String, Object>();
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        try {
            cjp.initializeReport(map, dataSource);
            return cjp.print();
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        }
        return null;
    }


    @Override
    public JasperPrint printBoxLabel(Long cartonId, PrintCustomize pc) throws Exception {
        if (cartonId == null) return null;
        String reportPath = null;
        String subReportPath = null;
        if (pc != null) {
            reportPath = Constants.PRINT_TEMPLATE_FLIENAME + pc.getMasterTemplet();
            subReportPath = Constants.PRINT_TEMPLATE_FLIENAME + pc.getSubTemplet();
        }
        List<OutBoundSendInfo> outInfo = null;
        outInfo = warehousePrintData.findBoxLabelByCartonId(cartonId);
        JRDataSource dataSource = new JRBeanCollectionDataSource(outInfo);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subReport", subReportPath);
        ClasspathJasperPrinter cjp = new ClasspathJasperPrinter(reportPath);
        try {
            cjp.initializeReport(map, dataSource);
            return cjp.print();
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        }
        return null;
    }
}
