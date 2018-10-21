package com.jumbo.manager;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.manager.channel.invoice.InvoiceFactory;
import com.jumbo.manager.channel.invoice.InvoiceTypeInterface;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.manager.pda.PdaPickingManager;
import com.jumbo.wms.manager.pickZone.PickZoneEditManager;
import com.jumbo.wms.manager.sku.SkuManager;
import com.jumbo.wms.manager.warehouse.DistriButionAreaManager;
import com.jumbo.wms.manager.warehouse.OperationUnitManager;
import com.jumbo.wms.manager.warehouse.PickingListManager;
import com.jumbo.wms.manager.warehouse.SfNextMorningConfigManager;
import com.jumbo.wms.manager.warehouse.SkuSizeConfigManager;
import com.jumbo.wms.manager.warehouse.TransAreaGroupManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WhReplenishManager;
import com.jumbo.wms.model.AdPackage;
import com.jumbo.wms.model.DefaultLifeCycleStatus;
import com.jumbo.wms.model.LitreSingle;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSnType;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.command.StaDeliverCommand;
import com.jumbo.wms.model.command.pickZone.WhPickZoneInfoCommand;
import com.jumbo.wms.model.compensation.WhCompensationCommand;
import com.jumbo.wms.model.pda.PdaSortPickingCommand;
import com.jumbo.wms.model.trans.TransAreaGroupDetial;
import com.jumbo.wms.model.vmi.Default.VmiRtoLineCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgInvoice;
import com.jumbo.wms.model.warehouse.ConvenienceStoreOrderInfo;
import com.jumbo.wms.model.warehouse.DwhDistriButionAreaLocCommand;
import com.jumbo.wms.model.warehouse.HandOverListLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.InventoryCheckDifTotalLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCheckDifferenceLineCommand;
import com.jumbo.wms.model.warehouse.InventoryCommand;
import com.jumbo.wms.model.warehouse.PackageInfoCommand;
import com.jumbo.wms.model.warehouse.PdaPostLog;
import com.jumbo.wms.model.warehouse.PdaPostLogCommand;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.PickingListStatus;
import com.jumbo.wms.model.warehouse.PoCommand;
import com.jumbo.wms.model.warehouse.SfCloudWarehouseConfigCommand;
import com.jumbo.wms.model.warehouse.SfNextMorningConfig;
import com.jumbo.wms.model.warehouse.SkuReplenishmentCommand;
import com.jumbo.wms.model.warehouse.SkuSnLogCommand;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StaErrorLineCommand;
import com.jumbo.wms.model.warehouse.StaInvoiceCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherCommand;
import com.jumbo.wms.model.warehouse.StvLineCommand;
import com.jumbo.wms.model.warehouse.WhReplenishLineCommand;

import loxia.dao.Sort;
import loxia.support.excel.ExcelWriter;
import loxia.support.excel.WriteStatus;
import loxia.utils.DateUtil;
import loxia.utils.PropListCopyable;
import loxia.utils.PropertyUtil;


public class ExcelExportManagerImpl implements ExcelExportManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -1264036970244325272L;

    protected static final Logger log = LoggerFactory.getLogger(ExcelExportManagerImpl.class);
    @Autowired
    private InvoiceFactory invoiceFactory;
    @Autowired
    private ChannelManager channelManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private SkuSizeConfigManager skuSizeConfigManager;
    @Autowired
    private PickingListManager pickingListManager;
    @Autowired
    private TransAreaGroupManager transAreaGroupManager;
    @Autowired
    private SkuManager skuManager;
    @Autowired
    private OperationUnitManager operationUnitManager;
    @Autowired
    private WhReplenishManager whReplenishManager;
    @Autowired
    private PickZoneEditManager pickZoneEditManager;
    @Autowired
    private SfNextMorningConfigManager sfNextMorningConfigManager;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private PdaPickingManager pdaPickingManager;
    @Autowired
    private DistriButionAreaManager distriButionAreaManager;

    @Resource(name = "skuProvidePickingMaintainWriter1")
    private ExcelWriter skuProvidePickingMaintainWriter1;
    @Resource(name = "skuProvideUnMaintainWriter1")
    private ExcelWriter skuProvideUnMaintainWriter1;
    @Resource(name = "pickingListWriter")
    private ExcelWriter pickingListWriter;
    @Resource(name = "skuReplenishmentWriter")
    private ExcelWriter skuReplenishmentWriter;
    @Resource(name = "exportSkuReplenishWriter")
    private ExcelWriter exportSkuReplenishWriter;
    @Resource(name = "staWriterForPurchase")
    private ExcelWriter staWriterForPurchase;
    @Resource(name = "staWriterForPredefined")
    private ExcelWriter staWriterForPredefined;
    @Resource(name = "externalOutWHWriter")
    private ExcelWriter externalOutWHWriter;
    @Resource(name = "locationWriter")
    private ExcelWriter locationWriter;
    @Resource(name = "handOverListExportWriter")
    private ExcelWriter handOverListExportWriter;
    @Resource(name = "vmiInvoiceExportWriter")
    private ExcelWriter vmiInvoiceExportWriter;
    @Resource(name = "handOverLineExportWriter")
    private ExcelWriter handOverLineExportWriter;
    @Resource(name = "deliveryInfoExportWriter")
    private ExcelWriter deliveryInfoExportWriter;
    @Resource(name = "deliveryInfoExportForAccountWriter")
    private ExcelWriter deliveryInfoExportForAccountWriter;
    @Resource(name = "recevingMoveSuggestWriter")
    private ExcelWriter recevingMoveSuggestWriter;
    @Resource(name = "pdaErrorLogExportWriter")
    private ExcelWriter pdaErrorLogExportWriter;
    @Resource(name = "inventoryCheckExportExportWriter")
    private ExcelWriter inventoryCheckExportExportWriter;
    @Resource(name = "checkOverageExportWriter")
    private ExcelWriter checkOverageExportWriter;
    @Resource(name = "poConfirmWriter")
    private ExcelWriter poConfirmWriter;
    @Resource(name = "pdaLogExportWriter")
    private ExcelWriter pdaLogExportWriter;
    @Resource(name = "pickingListMode1ExportWriter")
    private ExcelWriter pickingListMode1ExportWriter;
    @Resource(name = "vmiAdjustmentInvCheckWriter")
    private ExcelWriter vmiAdjustmentInvCheckWriter;
    @Resource(name = "vmiOpcExpWriter")
    private ExcelWriter vmiOpcExpWriter;
    @Resource(name = "predefinedOutWriter")
    private ExcelWriter predefinedOutWriter;
    @Resource(name = "invWriterForPurchase")
    private ExcelWriter invWriterForPurchase;
    @Resource(name = "skuProvidePickingMaintainWriter")
    private ExcelWriter skuProvidePickingMaintainWriter;
    @Resource(name = "skuProvideUnMaintainWriter")
    private ExcelWriter skuProvideUnMaintainWriter;
    @Resource(name = "unfinishedStaUnMaintainProductWriter")
    private ExcelWriter unfinishedStaUnMaintainProductWriter;
    @Resource(name = "unfinishedStaUnMaintainProductWriter1")
    private ExcelWriter unfinishedStaUnMaintainProductWriter1;
    @Resource(name = "salesSendOutInfoWriter")
    private ExcelWriter salesSendOutInfoWriter;
    @Resource(name = "returnRegisterInfoWriter")
    private ExcelWriter returnRegisterInfoWriter;
    @Resource(name = "inventoryReportkWriter")
    private ExcelWriter inventoryReportkWriter;
    @Resource(name = "transAreaGDetialWriter")
    private ExcelWriter transAreaGDetialWriter;
    @Resource(name = "salesReportFormWriter")
    private ExcelWriter salesReportFormWriter;
    @Resource(name = "staDeliverWriter")
    private ExcelWriter staDeliverWriter;
    @Resource(name = "staGIWriter")
    private ExcelWriter staGIWriter;// 收货暂存区模版导出
    @Resource(name = "inboundWriter")
    private ExcelWriter inboundWriter;// 入库数量确认模版导出
    @Resource(name = "confirmDiversityWriter")
    private ExcelWriter confirmDiversityWriter;// 入库审核差异调整导出
    @Resource(name = "proInfoMaintainWriter")
    private ExcelWriter proInfoMaintainWriter;// 商品基础信息导出
    @Resource(name = "virtualInBoundShelvesWriter")
    private ExcelWriter virtualInBoundShelvesWriter; // 虚拟仓收货导出
    @Resource(name = "cartonListByCRWWriter")
    private ExcelWriter cartonListByCRWWriter; // NIKE CRW导出

    @Resource(name = "agvWriter")
    private ExcelWriter agvWriter; // AGV导出

    @Resource(name = "inBoundShelvesWriter")
    private ExcelWriter inBoundShelvesWriter;// 入库审核差异调整导出
    @Resource(name = "whReplenishWriter")
    private ExcelWriter whReplenishWriter;
    @Resource(name = "soInvoiceExportWriter")
    private ExcelWriter soInvoiceExportWriter; // 销售|换货出库发票导出
    @Resource(name = "convenienceStoreOrderInfoWriter")
    private ExcelWriter convenienceStoreOrderInfoWriter;
    @Resource(name = "failureInfoExportWriter")
    private ExcelWriter failureInfoExportWriter; // 配货失败 缺货信息导出
    @Resource(name = "skuInvnetoryWriter")
    private ExcelWriter skuInvnetoryWriter; // sku批量导入获取库存
    @Resource(name = "pickZoneExportWriter")
    private ExcelWriter pickZoneExportWriter; // 库区导出
    @Resource(name = "inBoundShelvesWriterImperfect")
    private ExcelWriter inBoundShelvesWriterImperfect;// 入库审核差异调整导出
    @Resource(name = "procurementrenurninboudputawayWriter")
    private ExcelWriter procurementrenurninboudputawayWriter; // 导出负向采购退货入库上架
    @Resource(name = "sfNextMorningConfigWriter")
    private ExcelWriter sfNextMorningConfigWriter;// 导出SF次晨达区域配置
    @Resource(name = "sfConfigWriter")
    private ExcelWriter sfConfigWriter;// 导出SF时效配置
    @Resource(name = "pdaSortPickingWriter")
    private ExcelWriter pdaSortPickingWriter;

    @Resource(name = "importStaByOwnerWriter")
    private ExcelWriter importStaByOwner;

    @Resource(name = "exportSkuReplenishSuggestWriter")
    private ExcelWriter exportSkuReplenishSuggestWriter;// 补货建议导出

    public ExcelWriter getSfConfigWriter() {
        return sfConfigWriter;
    }

    public void setSfConfigWriter(ExcelWriter sfConfigWriter) {
        this.sfConfigWriter = sfConfigWriter;
    }


    @Resource(name = "nikeTodaySendConfigWriter")
    private ExcelWriter nikeTodaySendConfigWriter;// 导出NIKE当日达区域配置
    @Resource(name = "claimantInfo")
    private ExcelWriter claimantInfo; // 导出索赔信息

    @Resource(name = "gucciRtoLineInfo")
    private ExcelWriter gucciRtoLineInfo;// 导出gucci退大仓明细

     
    
    //导出区域绑定库位
    @Resource(name="distriButionWriter")
    private ExcelWriter distriButionWriter;
    


    @Resource(name = "adPackageExport")
    private ExcelWriter adPackageExport;

    @Resource(name = "threeDimensionalSkuInfo")
    private ExcelWriter threeDimensionalSkuInfo; // 导出索赔信息

    @Resource(name = "noThreeDimensionalSku")
    private ExcelWriter noThreeDimensionalSku;


    /**
     * 发票导出
     */
    public WriteStatus exportSoInvoiceByPickingList(OutputStream os, Long plId, Long staId) {
        // 获取发票类型
        String invoiceType = channelManager.findInvoiceType(plId, staId);
        // 获取发票数据
        InvoiceTypeInterface icType = invoiceFactory.getInvoice(invoiceType);
        List<StaInvoiceCommand> data = icType.getInvoiceData(plId, staId);
        if (data == null) {
            data = new ArrayList<StaInvoiceCommand>();
            data.add(new StaInvoiceCommand());
        } else {
            // 更新导出次数
            channelManager.addInvoiceExecuteCountByPlId(plId, staId);
        }
        return icType.writeExcel(data, os);
    }

    /**
     * 发票导出
     */
    public WriteStatus exportSoInvoiceByPickingLists(OutputStream os, List<Long> plIds, Long staId) {
        // 获取发票类型
        String invoiceType = channelManager.findInvoiceType(plIds.get(0), staId);
        // 获取发票数据
        InvoiceTypeInterface icType = invoiceFactory.getInvoice(invoiceType);
        List<StaInvoiceCommand> data = channelManager.findDefaultInvoices(plIds);
        if (data == null) {
            data = new ArrayList<StaInvoiceCommand>();
            data.add(new StaInvoiceCommand());
        } else {
            // 更新导出次数
            channelManager.addInvoiceExecuteCountByPlIds(plIds);
        }
        return icType.writeExcel(data, os);
    }

    public WriteStatus skuProvideInfoPickingDistrictExport1(OutputStream os, Long ouid) {
        Warehouse wh = wareHouseManager.getByOuId(ouid);
        Map<String, Object> beans = new HashMap<String, Object>();
        List<SkuCommand> list = skuManager.findSkuProvideInfoPickingDistrict(ouid, wh.getIsShare());
        if (list == null) {
            list = new ArrayList<SkuCommand>();
        }
        beans.put("skus", list);
        return skuProvidePickingMaintainWriter1.write(SKU_PROVIDE_PICKING_EXPORT1, os, beans);
    }

    /**
     * 仓库未完成补货信息商品导出
     */
    public WriteStatus skuProvideInfoUnMaintainExport1(OutputStream os, Long ouid) {
        Warehouse wh = wareHouseManager.getByOuId(ouid);
        Map<String, Object> beans = new HashMap<String, Object>();
        List<SkuCommand> list = skuManager.findSkuProvideInfoUnMaintain(ouid, wh.getIsShare());
        if (list == null) {
            list = new ArrayList<SkuCommand>();
        }
        beans.put("skus", list);
        return skuProvideUnMaintainWriter1.write(SKU_PROVIDE_UN_MAINTAIN_EXPORT1, os, beans);
    }

    /**
     * 仓库未完成入库作业单 未维护补货信息商品导出
     */
    public WriteStatus unfinishedStaUnMaintainProductExport1(OutputStream os, Long ouid) {
        Warehouse wh = wareHouseManager.getByOuId(ouid);
        Map<String, Object> beans = new HashMap<String, Object>();
        List<Integer> staTypes = new ArrayList<Integer>();
        staTypes.add(StockTransApplicationType.INBOUND_PURCHASE.getValue());
        staTypes.add(StockTransApplicationType.INBOUND_SETTLEMENT.getValue());
        staTypes.add(StockTransApplicationType.INBOUND_OTHERS.getValue());
        staTypes.add(StockTransApplicationType.INBOUND_CONSIGNMENT.getValue());
        staTypes.add(StockTransApplicationType.INBOUND_GIFT.getValue());
        staTypes.add(StockTransApplicationType.INBOUND_MOBILE.getValue());
        staTypes.add(StockTransApplicationType.TRANSIT_CROSS.getValue());
        staTypes.add(StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue());
        staTypes.add(StockTransApplicationType.VMI_ADJUSTMENT_INBOUND.getValue());
        staTypes.add(StockTransApplicationType.VMI_OWNER_TRANSFER.getValue());
        staTypes.add(StockTransApplicationType.SAME_COMPANY_TRANSFER.getValue());
        staTypes.add(StockTransApplicationType.VMI_RETURN.getValue());
        staTypes.add(StockTransApplicationType.VMI_TRANSFER_RETURN.getValue());
        List<Integer> staStatuses = new ArrayList<Integer>();
        staStatuses.add(StockTransApplicationStatus.CREATED.getValue());
        List<SkuCommand> list = skuManager.unfinishedStaUnMaintainProductExport(ouid, wh.getIsShare(), staTypes, staStatuses);
        if (list == null) {
            list = new ArrayList<SkuCommand>();
        }
        beans.put("skus", list);
        return unfinishedStaUnMaintainProductWriter1.write(UNFINISHED_STA_SKU_PROVIDE_UN_MAINTAIN_EXPORT1, os, beans);
    }

    /**
     * 配货失败补货 fanht
     * 
     * @param os
     * @param sta
     * @return
     */
    public WriteStatus exportSkuReplenishmentInfo(Long ouId, SkuReplenishmentCommand com, OutputStream os) {
        Map<String, Object> beans = new HashMap<String, Object>();
        com = com == null ? new SkuReplenishmentCommand() : com;
        // 判断是否共享仓
        Warehouse w = wareHouseManager.getByOuId(ouId);
        Long terms = null;
        if (w.getIsShare() != true) {
            terms = w.getId();
        }
        // 查询配货失败作业单的数据
        List<SkuReplenishmentCommand> result = wareHouseManager.findReplenishSummaryForPickingFailed(ouId, com.getQueryLikeParam(), terms);
        // 组织汇总数据
        List<SkuReplenishmentCommand> summary = new ArrayList<SkuReplenishmentCommand>();
        // 组织补货明细出入库
        List<SkuReplenishmentCommand> detail = new ArrayList<SkuReplenishmentCommand>();
        Long skuId = new Long(-1);
        int qyt = 0;
        for (SkuReplenishmentCommand bean : result) {
            bean.setSkuSize(skuSizeConfigManager.findSizeBySkuQty(bean.getMaxnumber()));
            // 组织汇总数据
            if (bean.getSkuId().intValue() != skuId.intValue()) {
                summary.add(bean);
                skuId = bean.getSkuId();
                qyt = bean.getReplenishmentQty().intValue();
            }
            // 组织补货明细出入库
            if (qyt == 0) {
                continue;
            }
            // --根据SKUID查日志表最近三个月的数据当前仓库的销售出库的 库位可用没有被锁定的 库位
            StockTransApplication sta = wareHouseManager.getByCode(bean.getStaCode());
            if (sta != null) {
                if (sta.getType() == StockTransApplicationType.OUTBOUND_SALES) {
                    SkuReplenishmentCommand object = skuSizeConfigManager.findCodeBySkuId(skuId);
                    if (object != null) {
                        bean.setGroomCode(object.getGroomCode());
                    }
                }
            }
            SkuReplenishmentCommand obj = skuSizeConfigManager.findstockBySkuId(bean.getSkuId(), bean.getLocationCode());
            if (obj != null) {
                bean.setStock(obj.getStock());
            } else {
                bean.setStock(0l);
            }
            if (bean.getMaxReplenishmentQty().intValue() >= qyt) {
                bean.setMaxReplenishmentQty(new Long(qyt));
                qyt = 0;
                detail.add(bean);
            } else {
                detail.add(bean);
                qyt = qyt - bean.getMaxReplenishmentQty().intValue();
            }

        }
        beans.put("summary", summary);
        beans.put("detail", detail);

        if (terms == null) {
            return exportSkuReplenishWriter.write(EXPORT_SKU_REPLENISHB, os, beans);
        } else {
            return exportSkuReplenishWriter.write(EXPORT_SKU_REPLENISH, os, beans);
        }
    }

    /**
     * STA确认收货导出
     */
    public WriteStatus staExportForPurchase(OutputStream os, StockTransApplication sta) {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("createTime", DateUtil.format(sta.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        beans.put("code", sta.getCode());
        if (sta.getType().getValue() != StockTransApplicationType.INBOUND_SETTLEMENT.getValue() && sta.getType().getValue() != StockTransApplicationType.INBOUND_CONSIGNMENT.getValue()
                && sta.getType().getValue() != StockTransApplicationType.INBOUND_GIFT.getValue() && sta.getType().getValue() != StockTransApplicationType.INBOUND_MOBILE.getValue()
                && sta.getType().getValue() != StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue() && sta.getType().getValue() != StockTransApplicationType.SAMPLE_INBOUND.getValue()
                && sta.getType().getValue() != StockTransApplicationType.REAPAIR_INBOUND.getValue()) {
            beans.put("refSlipCode", sta.getRefSlipCode());
        }
        List<StaLineCommand> list = wareHouseManager.findStaLineListByStaIdWithSn(sta.getId(), true, false, true);
        if (list == null) list = new ArrayList<StaLineCommand>();
        if (list != null && list.size() > 0) {
            for (StaLine staLine : list) {
                staLine.setQuantity(staLine.getQuantity() - staLine.getCompleteQuantity());
            }
        }
        beans.put("staLines", list);
        List<StaLineCommand> list1 = wareHouseManager.findStaLineListByStaIdWithSn(sta.getId(), true, true, true);
        if (list1 == null) list1 = new ArrayList<StaLineCommand>();
        if (list1 != null && list1.size() > 0) {
            for (StaLine staLine : list1) {
                staLine.setQuantity(staLine.getQuantity() - staLine.getCompleteQuantity());
            }
        }
        beans.put("staLinesSheet2", list1);
        if (sta.getType().getValue() == StockTransApplicationType.INBOUND_SETTLEMENT.getValue() || sta.getType().getValue() == StockTransApplicationType.INBOUND_CONSIGNMENT.getValue()
                || sta.getType().getValue() == StockTransApplicationType.INBOUND_GIFT.getValue() || sta.getType().getValue() == StockTransApplicationType.INBOUND_MOBILE.getValue()
                || sta.getType().getValue() == StockTransApplicationType.VMI_INBOUND_CONSIGNMENT.getValue() || sta.getType().getValue() == StockTransApplicationType.SAMPLE_INBOUND.getValue()
                || sta.getType().getValue() == StockTransApplicationType.REAPAIR_INBOUND.getValue()) {
            return staWriterForPredefined.write(STA_TEMPLATE_FOR_PREDEFINED, os, beans);
        }
        return staWriterForPurchase.write(STA_TEMPLATE_FOR_PURCHASE, os, beans);
    }

    /**
     * 配货批次导出
     */
    public WriteStatus exportPickingList(OutputStream os, PickingList pl) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<PickingListCommand> list = pickingListManager.findDetailInfoById(pl.getId());
        if (list == null) {
            list = new ArrayList<PickingListCommand>();
        }
        wareHouseManager.updatePLExputCountByPlId(pl.getId());
        beans.put("list", list);
        return pickingListWriter.write(PICKING_LIST_EXPORT, os, beans);
    }

    /**
     * 配送范围导出
     */
    public WriteStatus expTransAreaDetail(OutputStream os, Long groupId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<TransAreaGroupDetial> tagd = transAreaGroupManager.findTransAreaGDetiaByGId(groupId);
        if (tagd == null) {
            tagd = new ArrayList<TransAreaGroupDetial>();
        }
        beans.put("data", tagd);
        return transAreaGDetialWriter.write(TRANS_AREA_EXP, os, beans);
    }

    public WriteStatus exportSkuReplenishmentInfo(int type, Long ouId, SkuReplenishmentCommand com, OutputStream os) {
        Map<String, Object> beans = new HashMap<String, Object>();
        try {
            com = com == null ? new SkuReplenishmentCommand() : com;
            List<SkuReplenishmentCommand> result = null;
            if (type == 1) {
                // 查询出须要补货的总的数据
                result = pickingListManager.findReplenishSummary(ouId, com.getQueryLikeParam());
            } else if (type == 2) {
                // 查询配货失败作业单的数据
                result = pickingListManager.findReplenishSummaryForPickingFailed(ouId, com.getQueryLikeParam());
            }
            if (result == null) {
                result = new ArrayList<SkuReplenishmentCommand>();
            }
            // 获取汇总数据
            List<SkuReplenishmentCommand> summary = new ArrayList<SkuReplenishmentCommand>();
            Map<String, SkuReplenishmentCommand> mapS = new HashMap<String, SkuReplenishmentCommand>();
            for (SkuReplenishmentCommand bean : result) {
                String key = bean.getDistrictCode() + bean.getSkuId();
                if (!mapS.containsKey(key)) {
                    SkuReplenishmentCommand temp = bean.clone();
                    mapS.put(key, temp);
                    summary.add(temp);
                }
            }
            // 库区建议补货汇总
            findReplenishmentSummary(ouId, com, summary);
            beans.put("summary", summary);

            // 补货明细入库
            List<SkuReplenishmentCommand> detaileIn = new ArrayList<SkuReplenishmentCommand>();
            beans.put("detailIn", detaileIn);
            // 标记补货建议 所占用 的 Map
            Map<String, Long> skuQty = new HashMap<String, Long>();
            for (SkuReplenishmentCommand bean : result) {
                String key = bean.getDistrictCode() + bean.getSkuId();
                // 排除掉 未在补货建议汇总里面的商品 或 建议补货量 已经被别的须要补货的库位所占用了
                if (mapS.containsKey(key) && !skuQty.containsKey(key)) {
                    // 获取建议商品
                    SkuReplenishmentCommand sommaryBean = mapS.get(key);
                    // 获取建议补货数量
                    Long replenishmentQty = sommaryBean.getReplenishmentQty();
                    // 排除补货汇总里面 者没有 补货建议 或 建议补货数量为 0 的商品
                    if (replenishmentQty != null && replenishmentQty != 0) {
                        // 设置补货建议
                        bean.setReplenishmentQty(replenishmentQty);
                        // 添加到补货明细入库
                        detaileIn.add(bean);
                        // 标记此条 补货建议量 已经被占用
                        skuQty.put(key, replenishmentQty);
                    }
                }
            }

            // 补货明细出库
            beans.put("detailOut", findReplenishmentDetaile(detaileIn, com, ouId));
        } catch (Exception e) {
            log.debug("Export SKU Replenishment Summary Error!");
            log.error("", e);
            beans.put("detailOut", null);
            beans.put("detailIn", null);
            beans.put("summary", null);
        }
        return skuReplenishmentWriter.write(SKU_REPLENISHMENT, os, beans);
    }

    /**
     * 补货 查询建议补货量
     * 
     * @param ouId
     * @param comm
     * @param summary
     * @return
     */
    private List<SkuReplenishmentCommand> findReplenishmentSummary(Long ouId, SkuReplenishmentCommand comm, List<SkuReplenishmentCommand> summary) {
        if (summary == null || summary.size() == 0) {
            // 为找到须要补货的商品
            return summary;
        }
        // 查找建议补货数量
        // 判断处理方式.(300 以下 做 一条一条补货，300 以上批量补货)
        if (summary.size() > 300) {
            // 查寻批量 库存
            List<SkuReplenishmentCommand> repList = pickingListManager.findReplenishInv(ouId, null, comm.getQueryLikeParam());
            if (repList != null && repList.size() > 0) {
                Map<Long, SkuReplenishmentCommand> mapSR = new HashMap<Long, SkuReplenishmentCommand>();
                for (SkuReplenishmentCommand bean : repList) {
                    mapSR.put(bean.getSkuId(), bean);
                }
                // 补货过程
                for (SkuReplenishmentCommand bean : summary) {
                    calculationReplenishment(bean, mapSR);
                }
            }
        } else {
            Map<Long, SkuReplenishmentCommand> skuMap = new HashMap<Long, SkuReplenishmentCommand>();
            for (SkuReplenishmentCommand bean : summary) {
                if (!skuMap.containsKey(bean.getSkuId())) {
                    List<SkuReplenishmentCommand> repList = pickingListManager.findReplenishInv(ouId, bean.getSkuId(), null);
                    skuMap.put(bean.getSkuId(), repList != null ? (repList.size() > 0 ? repList.get(0) : null) : null);
                }
                calculationReplenishment(bean, skuMap);
            }
        }
        return summary;
    }

    /**
     * 出库明细
     * 
     * @param detaileIn 入库明细
     * @param comm 库存查寻条件
     * @param ouId ...仓库OU
     * @return
     */
    private List<SkuReplenishmentCommand> findReplenishmentDetaile(List<SkuReplenishmentCommand> detaileIn, SkuReplenishmentCommand comm, Long ouId) {
        List<SkuReplenishmentCommand> detaileOut = new ArrayList<SkuReplenishmentCommand>();
        Map<String, List<SkuReplenishmentCommand>> invMap = new HashMap<String, List<SkuReplenishmentCommand>>();
        // 获取库存的方式 （300 以下 做 一条一条查询，300 以上批量查询）
        boolean intType = detaileIn.size() > 300;
        if (intType) {
            // 获取补货库存 并组装成Map数据
            List<SkuReplenishmentCommand> invList = pickingListManager.findReplenishInvDetaile(ouId, null, comm.getQueryLikeParam());
            for (SkuReplenishmentCommand bean : invList) {
                List<SkuReplenishmentCommand> beanList = invMap.get(bean.getSkuId() + bean.getDistrictCode());
                if (beanList == null) {
                    beanList = new ArrayList<SkuReplenishmentCommand>();
                    beanList.add(bean);
                    invMap.put(bean.getSkuId() + bean.getDistrictCode(), beanList);
                } else {
                    beanList.add(bean);
                }
            }
        }
        for (SkuReplenishmentCommand bean : detaileIn) {
            List<SkuReplenishmentCommand> skuInv = null;
            // 判断获取库存方式
            if (intType) {
                skuInv = invMap.get(bean.getSkuId() + bean.getDistrictCode());
            } else {
                skuInv = pickingListManager.findReplenishInvDetaile(ouId, bean.getSkuId(), null);
            }
            if (skuInv != null) {
                // 须要补货数量
                long qty = bean.getReplenishmentQty();
                for (int i = 0; i < skuInv.size(); i++) {
                    SkuReplenishmentCommand inv = skuInv.get(i);
                    SkuReplenishmentCommand outBean = bean.clone();
                    outBean.setLocationCode(inv.getLocationCode());
                    outBean.setDistrictCode(inv.getDistrictCode());
                    // 处理 补货后的商品不计入重复补货
                    if (inv.getInvQty() > qty) {
                        outBean.setReplenishmentQty(qty);
                        inv.setInvQty(inv.getInvQty() - qty);
                        qty = 0;
                    } else {
                        outBean.setReplenishmentQty(inv.getInvQty());
                        qty -= inv.getInvQty();
                        skuInv.remove(i--);
                    }
                    if (outBean.getReplenishmentQty() == 0L) {
                        continue;
                    }
                    detaileOut.add(outBean);
                }
                // 实际补货是否满足建议补货数量
                if (qty != 0) {
                    throw new BusinessException(ErrorCode.SKU_MOVE_SUGGEST_QTY_ERROR);
                }
            } else {
                throw new BusinessException(ErrorCode.SKU_MOVE_SUGGEST_QTY_ERROR);
            }
        }
        return detaileOut;
    }

    /**
     * 计算 建议补货量
     * 
     * @param skuR 须要补货商品
     * @param skuInt 提供补货的库存
     * @return
     */
    private SkuReplenishmentCommand calculationReplenishment(SkuReplenishmentCommand skuR, Map<Long, SkuReplenishmentCommand> skuInv) {
        if (skuInv.containsKey(skuR.getSkuId())) {
            // 获取仓库库存数量
            SkuReplenishmentCommand skuQty = skuInv.get(skuR.getSkuId());
            if (skuQty == null) {
                skuR.setReplenishmentQty(0L);
                return skuR;
            }
            // 计算最大补货数量（最大补货量 = 库区最大补货量 >= 仓库最大库存量 ? 仓库最大库存量 : 库区最大补货量）
            long replenishmentQty = 0L;
            if (skuR.getMaxReplenishmentQty() >= skuQty.getInvQty()) {
                replenishmentQty = skuQty.getInvQty();
                skuQty.setInvQty(0L);
                // skuInv.remove(skuR.getSkuId());
            } else {
                replenishmentQty = skuR.getMaxReplenishmentQty();
                skuQty.setInvQty(skuQty.getInvQty() - skuR.getMaxReplenishmentQty());
            }
            if (replenishmentQty == 0 || skuR.getBoxQty() == null) {
                skuR.setReplenishmentQty(replenishmentQty);
                return skuR;
            }
            // 建议补货量 = 最大补货量/整箱数 > 1 ? 取整(最大补货量/整箱数) * 整箱数 : 最大补货量
            long boxQty = replenishmentQty / skuR.getBoxQty();
            skuR.setReplenishmentQty(boxQty > 1 ? boxQty * skuR.getBoxQty() : replenishmentQty);
        }
        return skuR;
    }

    /**
     * 收货暂存区移库建议
     */
    public WriteStatus recevingMoveSuggestExport(OutputStream os, StvLineCommand cmd, Long ouId) {
        // stvLineDao
        String districtCode = null;
        String locationCode = null;
        String skuCode = null;
        String barCode = null;
        String supplierCode = null;
        if (cmd != null) {
            if (StringUtils.hasText(cmd.getDistrictCode())) {
                districtCode = cmd.getDistrictCode();
            }
            if (StringUtils.hasText(cmd.getLocationCode())) {
                locationCode = cmd.getLocationCode();
            }
            if (StringUtils.hasText(cmd.getSkuCode())) {
                skuCode = cmd.getSkuCode();
            }
            if (StringUtils.hasText(cmd.getBarCode())) {
                barCode = cmd.getBarCode();
            }
            if (StringUtils.hasText(cmd.getSupplierCode())) {
                supplierCode = cmd.getSupplierCode();
            }
        }
        List<StvLineCommand> list = wareHouseManager.findRecevingMoveOutboundSuggest(ouId, districtCode, locationCode, skuCode, barCode, supplierCode);
        Map<String, Object> beans = new HashMap<String, Object>();

        Map<Long, Long> skuQtyMap = new HashMap<Long, Long>(); // 收货暂存区商品总计,key skuId,value qty,最终
        // qty被扣减为0
        Map<Long, List<StvLineCommand>> pickingSkuMap = new HashMap<Long, List<StvLineCommand>>(); // 拣货区补货量
        List<StvLineCommand> pickingMoveSkuTmp = new ArrayList<StvLineCommand>();
        // 计算移动拣货区数量
        for (StvLineCommand line : list) {
            Long qty = skuQtyMap.get(line.getSkuId());
            if (qty == null) {
                qty = 0L;
            }
            qty += line.getQuantity();
            List<StvLineCommand> pskus = pickingSkuMap.get(line.getSkuId());
            if (pskus == null) {
                pskus = wareHouseManager.findPickingReplenishQtyBySku(ouId, line.getSkuId());
            }
            if (pskus != null && pskus.size() > 0) {
                // 移动至拣货区
                for (StvLineCommand tmp : pskus) {
                    log.debug("sku id is {}", tmp.getSkuId());
                    if (tmp.getQuantity() == 0) {
                        continue;
                    }
                    if (tmp.getQuantity() >= qty) {
                        // 拣货区补货量 >= 移动数量 ,移动量为 qty
                        StvLineCommand cptmp = new StvLineCommand();
                        try {
                            PropertyUtil.copyProperties(tmp, cptmp, new PropListCopyable("skuId", "districtCode", "skuCode", "barCode", "supplierCode", "quantity"));
                        } catch (IllegalAccessException e) {
                            log.error("", e);
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {e.getMessage()});
                        } catch (InvocationTargetException e) {
                            log.error("", e);
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {e.getMessage()});
                        } catch (NoSuchMethodException e) {
                            log.error("", e);
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {e.getMessage()});
                        }
                        cptmp.setQuantity(qty);
                        pickingMoveSkuTmp.add(cptmp);
                        tmp.setQuantity(tmp.getQuantity() - qty);
                        qty = 0L;
                    } else {
                        // 拣货区补货量 < 移动数量 ,移动量为 拣货区补货量
                        StvLineCommand cptmp = new StvLineCommand();
                        try {
                            PropertyUtil.copyProperties(tmp, cptmp, new PropListCopyable("skuId", "districtCode", "skuCode", "barCode", "supplierCode", "quantity"));
                        } catch (NoSuchMethodException e) {
                            log.error("", e);
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {e.getMessage()});
                        } catch (IllegalAccessException e) {
                            log.error("", e);
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {e.getMessage()});
                        } catch (InvocationTargetException e) {
                            log.error("", e);
                            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {e.getMessage()});
                        }
                        pickingMoveSkuTmp.add(cptmp);
                        qty -= tmp.getQuantity();
                        tmp.setQuantity(0L);
                    }
                }
            } else {
                pskus = new ArrayList<StvLineCommand>();
            }
            pickingSkuMap.put(line.getSkuId(), pskus);
            skuQtyMap.put(line.getSkuId(), qty);
        }
        // 按商品、库区，合并收入拣货区数量
        List<StvLineCommand> pickingMoveSku = new ArrayList<StvLineCommand>();
        for (StvLineCommand tmp : pickingMoveSkuTmp) {
            boolean isExist = false;
            for (StvLineCommand lcmd : pickingMoveSku) {
                if (lcmd.getSkuId().equals(tmp.getSkuId()) && lcmd.getDistrictCode().equals(tmp.getDistrictCode())) {
                    lcmd.setQuantity(lcmd.getQuantity() + tmp.getQuantity());
                    isExist = true;
                }
            }
            if (!isExist) {
                pickingMoveSku.add(tmp);
            }
        }
        // 查询存货区库区库位
        for (Entry<Long, Long> ent : skuQtyMap.entrySet()) {
            if (ent.getValue().longValue() == 0L) {
                continue;
            }
            // 查询同SKU
            StvLineCommand tmp = wareHouseManager.findStockSuggestBySku(ouId, ent.getKey());
            if (tmp != null && StringUtils.hasText(tmp.getDistrictCode())) {
                tmp.setQuantity(ent.getValue());
                pickingMoveSku.add(tmp);
            } else {
                // 查询同PRODUCT
                tmp = wareHouseManager.findStockSuggestByProduct(ouId, ent.getKey());
                tmp.setQuantity(ent.getValue());
                pickingMoveSku.add(tmp);
            }
        }
        beans.put("outbound", list);
        beans.put("inbound", pickingMoveSku);
        return recevingMoveSuggestWriter.write(RECEVING_MOVE_SUGGEST_EXCEL, os, beans);
    }

    public WriteStatus predefinedOutExport(OutputStream os, StockTransApplication sta) {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("createTime", DateUtil.format(sta.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        beans.put("code", sta.getCode());
        List<StaLineCommand> list = wareHouseManager.findStaLineByStaIdAndNotSNSku(sta.getId(), false);
        if (list == null) {
            list = new ArrayList<StaLineCommand>();
        }
        beans.put("outStvl", list);
        return predefinedOutWriter.write(PREDEFINED_OUT_EXCEL, os, beans);
    }

    public WriteStatus externalOutOutport(OutputStream os, StockTransApplication sta) {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("createTime", DateUtil.format(sta.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        beans.put("code", sta.getCode());
        List<InventoryCommand> list = wareHouseManager.findFlittingOutInfoByStaId(sta.getId());
        if (list == null) list = new ArrayList<InventoryCommand>();
        beans.put("data", list);
        return vmiOpcExpWriter.write(EXTERNAL_IN_INFO_EXCEL, os, beans);
    }

    /**
     * PDA日志导出
     */
    public WriteStatus exportPdaLogReport(OutputStream os, String code) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<PdaPostLogCommand> list = wareHouseManager.findPdaLogwithGroup(code);
        try {
            StockTransApplication sta = wareHouseManager.findStaByCode(code);
            StockTransVoucher stv = wareHouseManager.findStvCreatedByStaId(sta.getId());
            List<StvLineCommand> stvls = wareHouseManager.findPlanExeQtyByPda(stv.getId(), code);
            beans.put("stvls", stvls);
        } catch (Exception e) {
            log.error("", e);
        }
        beans.put("code", code);
        beans.put("logs", list);

        return pdaLogExportWriter.write(PDA_POST_LOG_EXPORT, os, beans);
    }

    /**
     * 盘点 盘盈 需处理数据
     */
    public WriteStatus exportCheckOverage(OutputStream os, Long invCheckId, OperationUnit whou) {
        if (invCheckId == null) {
            throw new BusinessException(ErrorCode.INV_CHECK_IS_NULL);
        }
        InventoryCheck ic = wareHouseManager.getInventoryCheckById(invCheckId);
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("createTime", DateUtil.format(ic.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        beans.put("code", ic.getCode());
        List<InventoryCheckDifferenceLineCommand> list = wareHouseManager.findCheckOverageByInvCk(invCheckId, whou.getId(), whou.getParentUnit().getParentUnit().getId());
        beans.put("invCk", list);
        return checkOverageExportWriter.write(CHECK_OVERAGE, os, beans);
    }


    public WriteStatus importStaByOwner(Long id, OutputStream outputStream) {
        Map<String, Object> beans = new HashMap<String, Object>();
        String exc = "excel/tplt_export_sta.xls";
        List<StockTransApplicationCommand> list = wareHouseManager.importStaByOwner(id);
        if (list == null) {
            list = new ArrayList<StockTransApplicationCommand>();
        }
        beans.put("staList", list);
        return importStaByOwner.write(exc, outputStream, beans);

    }


    /**
     * 库间移动入库 模版导出
     */
    public WriteStatus externalInExport(OutputStream os, StockTransApplication sta) {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("createTime", DateUtil.format(sta.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        beans.put("code", sta.getCode());
        List<StaLineCommand> list = wareHouseManager.findkuByStaIdAndIsSn(sta.getId(), false);
        if (list == null) list = new ArrayList<StaLineCommand>();
        beans.put("outStvl", list);
        List<SkuSnLogCommand> list1 = wareHouseManager.findSNBySta(sta.getId());
        beans.put("outSkuSN", list1);
        return externalOutWHWriter.write(EXTERNAL_IN_INFO_EXCEL, os, beans);
    }

    /***
     * 物流面单导出
     */
    public WriteStatus exportDeliveryInfo(OutputStream outputStream, Long pickingListId, Long ouid) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<StaDeliveryInfoCommand> list = wareHouseManager.findStaDeliveryInfoListByPlid(pickingListId, ouid);
        if (list == null || list.get(0).getLpCode() == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        String lpCode = list.get(0).getLpCode().toUpperCase();
        String ownerproduct = null;
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                StaDeliveryInfoCommand cmd = list.get(i);
                cmd.setCount(list.size());
                ownerproduct = cmd.getOwner() + "商品*" + cmd.getQuantity() + "件";
                cmd.setOwnerproduct(ownerproduct.toString());
                cmd.setRefSlipCode("订单：" + cmd.getRefSlipCode());
                String p = _hasLength(cmd.getProvince());
                String c = _hasLength(cmd.getCity());
                String d = _hasLength(cmd.getDistrict());
                String a = _hasLength(cmd.getAddress());
                StringBuilder temp = new StringBuilder();
                temp = temp.append(p + " ").append(c + " ").append(d + " ");
                if (!a.contains(temp.toString())) {
                    String address = temp.toString() + a;
                    cmd.setAddress(address);
                }
                if (cmd.getAmount() != null) {
                    try {
                        cmd.setAmount(cmd.getAmount().setScale(0, RoundingMode.DOWN));
                    } catch (Exception e) {
                        log.error("", e);
                        log.error(e.getMessage());
                    }
                    if ("EMSCOD".equalsIgnoreCase(lpCode)) {
                        int amount = cmd.getAmount().intValue();
                        String strAmount = formatIntToChinaBigNumStr(amount);
                        cmd.setStrAmount(strAmount);
                    }
                }
            }
        }
        beans.put("deliverylist", list);
        return deliveryInfoExportWriter.write(DELIVERY_INFO_EXCEL, outputStream, beans);
    }


    public WriteStatus invExportForPurchase(OutputStream os, StockTransApplication sta) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<StvLineCommand> info = wareHouseManager.findStvLineCmdByStaId(sta.getId());
        beans.put("stvLineSheet", info);
        return invWriterForPurchase.write(INV_TEMPLATE_FOR_PURCHASE, os, beans);
    }

    public WriteStatus pickingListMode1Export(PickingListCommand plCmd, Long warehouseOuid, OutputStream os, String psize) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<PickingListCommand> pickingList = wareHouseManager.findPickingListByPickingId(plCmd.getId(), plCmd.getPickZoneId(), warehouseOuid);
        if (pickingList != null) {
            long i = 0;
            for (PickingListCommand cmd : pickingList) {
                cmd.setExcelIndex(++i);
            }
        }
        beans.put("pl", pickingList);
        OperationUnit WhOu = operationUnitManager.getOperationUnitById(warehouseOuid);
        PickingList pl = pickingListManager.getByPrimaryKey(plCmd.getId());
        PickingList plList = new PickingList();
        OperationUnit ou = new OperationUnit();
        try {
            PropertyUtil.copyProperties(pl, plList);
            PropertyUtil.copyProperties(WhOu, ou);
        } catch (Exception e) {
            log.error("", e);
        }
        plList.setCreator(null);
        plList.setHandOverList(null);
        plList.setWarehouse(null);
        plList.setStaList(null);
        ou.setChildrenUnits(null);
        ou.setOuType(null);
        ou.setParentUnit(null);
        beans.put("whName", ou.getName());
        beans.put("batchNumber", plList.getCode());
        beans.put("staCount", plCmd.getPlanBillCount());
        beans.put("skuNumber", plCmd.getPlanSkuQty());
        if (plCmd.getOperator() != null) {
            User u = pl.getOperator();
            u.setOu(null);
            beans.put("user", u.getUserName());
        }
        beans.put("createTime", plList.getCreateTime());

        return pickingListMode1ExportWriter.write(PICKINGLIST_EXPORT_MODE1, os, beans);
    }

    public WriteStatus exportVMIInvoiceRecord(OutputStream os, Long ouid, Date _fromDate, Date _endDate) {
        Map<String, Object> beans = new HashMap<String, Object>();
        Date fromDate = null, endDate = null;
        if (_fromDate != null) {
            fromDate = _fromDate;
        }
        if (_endDate != null) {
            endDate = _endDate;
        }
        Warehouse wh = wareHouseManager.getByOuId(ouid);
        List<MsgInvoice> list = wareHouseManager.findVMIInvoice(fromDate, endDate, wh.getVmiSource());
        if (list == null) {
            list = new ArrayList<MsgInvoice>();
        }
        beans.put("invoiceList", list);
        return vmiInvoiceExportWriter.write(VMI_INVOICE_TAX, os, beans);
    }

    // 交接清单导出
    public WriteStatus findHandOverListExportReturnOrderRecord(OutputStream os, Date _fromDate, Date _endDate, String lpCode, List<Long> idList, Long ouId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        Date fromDate = null, endDate = null;
        if (_fromDate != null) {
            fromDate = _fromDate;
        }
        if (_endDate != null) {
            endDate = _endDate;
        }
        if (!StringUtils.hasText(lpCode)) {
            lpCode = null;
        }
        // 原始数据
        List<HandOverListLineCommand> list2 = wareHouseManager.findHandOverListExport2(fromDate, endDate, lpCode, idList, ouId);
        if (list2.size() > 0) {
            beans.put("handOverLineList", list2);
            return handOverListExportWriter.write(HAND_OVER_LIST_EXPORT, os, beans);
        }
        List<HandOverListLineCommand> list = wareHouseManager.findHandOverListExport(fromDate, endDate, lpCode, idList, ouId);
        if (list == null) {
            list = new ArrayList<HandOverListLineCommand>();
            list.add(new HandOverListLineCommand());
        } else if (list.isEmpty()) {
            list.add(new HandOverListLineCommand());
        }
        beans.put("handOverLineList", list);
        return handOverListExportWriter.write(HAND_OVER_LIST_EXPORT, os, beans);
    }

    public WriteStatus deliveryInfoExport(ServletOutputStream os, List<Long> oulist, Long deliveryid, Long ouid, Date starttime, Date endtime) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<PackageInfoCommand> list = wareHouseManager.findDeliveryInfoList(oulist, deliveryid, ouid, starttime, endtime);
        beans.put("packageInfoList", list);
        return deliveryInfoExportForAccountWriter.write(DELIVERY_INFO_EXPORT_FOR_ACCOUNT, os, beans);

    }

    public WriteStatus pdaSortPickingExport(ServletOutputStream os, Map<String, Object> params) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<PdaSortPickingCommand> zoonList = pdaPickingManager.findSortPickingList(params);
        beans.put("pdaSortPickingList", zoonList);
        return pdaSortPickingWriter.write(PDA_SORT_PICKING_EXCEL, os, beans);
    }



    public WriteStatus exportPoConfirmReport(OutputStream os, Long staId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        PoCommand po = wareHouseManager.findPoInfo(staId);
        beans.put("applyTime", po.getApplyTime());
        beans.put("shopName", po.getShopName());
        beans.put("paymentDate", po.getPaymentDate());
        beans.put("whName", po.getWhName());
        beans.put("planTime", po.getPlanTime());
        beans.put("supportName", po.getSupportName());
        beans.put("arrivalType", po.getArrivalType());
        beans.put("code", po.getCode());
        beans.put("paymentType", po.getPaymentType());

        List<StvLineCommand> list = wareHouseManager.findPoConfirmStvLineBySta(staId);
        if (list == null) {
            list = new ArrayList<StvLineCommand>();
        }
        beans.put("stvl", list);

        long plqty = 0;
        long totalQty = 0;
        long qty = 0;
        for (StvLineCommand l : list) {
            if (StringUtils.hasText(l.getBatchCode())) {
                beans.put("batchCode", l.getBatchCode());
            }
            plqty += l.getPlanQty().longValue();
            totalQty += l.getTotalQty().longValue();
            qty += l.getQuantity().longValue();
        }
        beans.put("plqty", plqty);
        beans.put("totalQty", totalQty);
        beans.put("qty", qty);

        return poConfirmWriter.write(PO_CONFRIM_REPORT, os, beans);
    }

    public WriteStatus exportInfoByHoListId(OutputStream os, Long hoListId, String fileName) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<HandOverListLineCommand> list2 = wareHouseManager.findExportInfoByHoListId2(hoListId, DefaultLifeCycleStatus.CREATED.getValue());
        if (list2.size() > 0) {// 原始数据
            beans.put("handOverLine", list2);
            return handOverLineExportWriter.write(HAND_OVER_LINE, os, beans);
        }
        List<HandOverListLineCommand> list = wareHouseManager.findExportInfoByHoListId(hoListId, DefaultLifeCycleStatus.CREATED.getValue());
        if (list == null) {
            list = new ArrayList<HandOverListLineCommand>();
        }
        beans.put("handOverLine", list);
        return handOverLineExportWriter.write(HAND_OVER_LINE, os, beans);
    }

    // 导出
    public WriteStatus exportPdaErrorLog(ServletOutputStream os, Long staid) {
        StockTransApplication sta = wareHouseManager.getStaByPrimaryKey(staid);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        List<PdaPostLog> padlogs = wareHouseManager.findPdaErrorLogByStaCode(sta.getCode());
        if (padlogs == null) {
            log.debug("padlogs  is null");
            padlogs = new ArrayList<PdaPostLog>();
        }
        beans.put("padlogs", padlogs);
        return pdaErrorLogExportWriter.write(PAD_POST_ERROR_LOG, os, beans);
    }

    public WriteStatus exportVMIInventoryCheck(OutputStream os, Long invCkId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<InventoryCheckDifTotalLineCommand> list = wareHouseManager.findvmiicLineByInvCheckIdAndQty(invCkId, true);
        if (list == null) {
            list = new ArrayList<InventoryCheckDifTotalLineCommand>();
        }
        beans.put("vmiAdjustInbound", list);
        List<InventoryCheckDifTotalLineCommand> list1 = wareHouseManager.findvmiicLineByInvCheckIdAndQty(invCkId, false);
        if (list1 == null) {
            list1 = new ArrayList<InventoryCheckDifTotalLineCommand>();
        }
        beans.put("vmiAdjustOutbound", list1);
        return vmiAdjustmentInvCheckWriter.write(VMI_ADJUSTMENT_INVCHECK, os, beans);
    }

    public WriteStatus exportInventoryCheck(OutputStream os, Long invCkId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<InventoryCommand> list = wareHouseManager.findByInventoryCheckId(invCkId, null);
        if (list == null) {
            list = new ArrayList<InventoryCommand>();
        }
        beans.put("inventory", list);
        return inventoryCheckExportExportWriter.write(INVENTORY_CHECK, os, beans);
    }


    public WriteStatus locationTPLExport(OutputStream os) {
        return locationWriter.write(LOCATION_TEMPLATE_FOR_IMPORT, os, null);
    }


    /**
     * 销售（换货）发货表导出
     * 
     * @param outputStream
     * @param whOuId
     * @return
     */
    public WriteStatus salesSendOutInfoExport(OutputStream outputStream, Long plId) {
        PickingList pl = pickingListManager.getByPrimaryKey(plId);
        if (!pl.getStatus().equals(PickingListStatus.PACKING)) {
            throw new BusinessException(ErrorCode.PICKING_LIST_STSTUS_ERROR, new Object[] {pl.getCode()});
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        List<StockTransApplicationCommand> list = wareHouseManager.findExportSalesSendOutInfo(plId);
        beans.put("staList", list);
        return salesSendOutInfoWriter.write(SALES_SEND_OUT_INFO_EXPORT, outputStream, beans);
    }

    /**
     * 销售（换货）发货表导出
     * 
     * @param outputStream
     * @param whOuId
     * @return
     */
    public WriteStatus returnRegisterInfoExport(OutputStream outputStream, Long whOuId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<StockTransApplicationCommand> list = wareHouseManager.findExportreturnRegisterInfo(whOuId);
        Map<String, Map<String, String>> staMap = new HashMap<String, Map<String, String>>();

        // 瓶装SN号
        for (int i = 0; i < list.size(); i++) {
            StockTransApplicationCommand cmd = list.get(i);
            String staMapKey = cmd.getCode();
            if (staMap.containsKey(staMapKey)) {
                Map<String, String> snMap = staMap.get(staMapKey);
                cmd.setSn(snMap.get(staMapKey + "_" + cmd.getBarCode()));
            } else {
                List<SkuSnLogCommand> snList = wareHouseManager.findOutboundSnByStaSlipCode(cmd.getSoCode());
                if (snList != null && snList.size() > 0) {
                    Map<String, String> snMap = staMap.get(staMapKey);
                    if (snMap == null) {
                        snMap = new HashMap<String, String>();
                    }
                    for (SkuSnLogCommand sn : snList) {
                        String key = staMapKey + "_" + sn.getBarcode();
                        if (snMap.containsKey(key)) {
                            snMap.put(key, snMap.get(key) + "," + sn.getSn());
                        } else {
                            snMap.put(staMapKey, sn.getSn());
                        }
                    }
                    cmd.setSn(snMap.get(staMapKey + "_" + cmd.getBarCode()));
                }
            }
        }
        beans.put("staList", list);
        return returnRegisterInfoWriter.write(RETURN_REGISTER_INFO_EXPORT, outputStream, beans);
    }

    /**
     * 库存报表导出
     * 
     * @param outputStream
     * @param whOuId
     * @return
     */
    public WriteStatus inventoryReportkExport(OutputStream outputStream, Long whOuId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<InventoryCommand> list = wareHouseManager.findInventoryReportk(whOuId);
        beans.put("invList", list);
        return inventoryReportkWriter.write(INVENTORY_REPORT_EXPORT, outputStream, beans);
    }


    private String _hasLength(String entity) {
        return StringUtils.hasLength(entity) ? entity : "";
    }

    private String formatIntToChinaBigNumStr(int num) {
        if (num < 0) num = 0;
        String bigLetter[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        String si = num + "";
        char cc[] = new char[5];
        if (si.length() < 5) {
            StringBuilder t = new StringBuilder(si);
            for (int n = 0; n < 5 - si.length(); n++) {
                t = new StringBuilder("0").append(t);
            }
            cc = t.toString().toCharArray();
        } else if (si.length() == 5) {
            cc = si.toCharArray();
        } else {
            log.error("amount is too big");
            return "";
        }
        StringBuilder tt = new StringBuilder();
        for (int j = 0; j < cc.length; j++) {
            tt.append(bigLetter[cc[j] - 48] + "   ");
        }
        return tt.toString();
    }

    /**
     * 销售订单报表----
     */
    public WriteStatus exportSalesReportForm(Date outboundTime, Date endOutboundTime, OutputStream os) {
        Map<String, Object> beans = new HashMap<String, Object>();
        if (outboundTime != null && endOutboundTime != null) {
            beans.put("outboundTime", outboundTime);
            beans.put("to", "到");
            beans.put("endOutboundTime", endOutboundTime);
        } else if (outboundTime != null) {
            beans.put("outboundTime", outboundTime);
            beans.put("to", "至今");
        } else if (endOutboundTime != null) {
            beans.put("to", "截止至");
            beans.put("endOutboundTime", endOutboundTime);
        } else {
            beans.put("to", "所有订单");
        }
        beans.put("SRF", wareHouseManager.findSalesReportForm(outboundTime, endOutboundTime));
        return salesReportFormWriter.write(SKU_SALES_REPORT_FROM, os, beans);
    }

    /**
     * 确认数量模版导出
     */
    @Override
    public WriteStatus exportStaDeliver(ServletOutputStream outs, PickingList pickingList) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<StaDeliverCommand> list = wareHouseManager.findAllStaByPickingListId(pickingList.getId());
        beans.put("staDelivery", list);
        return staDeliverWriter.write(STA_DELIVER_TEMPLATE, outs, beans);
    }

    /**
     * STA收货暂存区模版导出
     */
    public WriteStatus staExportForGI(OutputStream os, StockTransApplication sta) {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("createTime", DateUtil.format(sta.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
        beans.put("code", sta.getCode());
        beans.put("refSlipCode", sta.getRefSlipCode());
        List<StaLineCommand> list = wareHouseManager.findStaLineListByStaIdWithSn(sta.getId(), true, false, false, new Sort[] {new Sort("sku.bar_Code asc")});
        if (list == null) list = new ArrayList<StaLineCommand>();
        if (list != null && list.size() > 0) {
            for (StaLine staLine : list) {
                staLine.setQuantity(staLine.getQuantity() - staLine.getCompleteQuantity());
            }
        }
        beans.put("staLines", list);
        return staGIWriter.write(STA_TEMPLATE_FOR_PURCHASE, os, beans);
    }

    /**
     * 确认数量模版导出
     */
    public WriteStatus exportInBoundNumber(OutputStream outputStream, StockTransApplicationCommand sta) {
        Map<String, Object> beans = new HashMap<String, Object>();
        Boolean condition = true;
        StockTransApplication stock = wareHouseManager.getStaByPrimaryKey(sta.getId());
        if (stock.getType().equals(StockTransApplicationType.TRANSIT_CROSS) || stock.getType().equals(StockTransApplicationType.SAME_COMPANY_TRANSFER) || stock.getType().equals(StockTransApplicationType.DIFF_COMPANY_TRANSFER)) {
            condition = null;
        }
        List<StaLineCommand> list = wareHouseManager.findInBoundStaLine(sta.getId(), condition);
        beans.put("staLineList", list);
        beans.put("staCode", sta.getCode());
        return inboundWriter.write(STA_TEMPLATE_IN_BOUND, outputStream, beans);
    }

    /**
     * 审核差异调整
     */
    public WriteStatus exportConfirmDiversity(OutputStream outputStream, StockTransApplicationCommand sta) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<StvLineCommand> list = wareHouseManager.findConfirmDiversity(sta.getStvId());
        beans.put("stvLineList", list);
        beans.put("staCode", sta.getCode());
        return confirmDiversityWriter.write(STA_TEMPLATE_CONFIRM_DIVERSIT, outputStream, beans);
    }

    /**
     * 商品基础信息导出
     */
    public WriteStatus exportSKUinfo(OutputStream outputStream, StockTransApplicationCommand sta) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<Sku> list = skuManager.findSkuByStvId(sta.getStvId());
        beans.put("product", list);
        return proInfoMaintainWriter.write(STA_TEMPLATE_PRO_INFO_MAINTAIN, outputStream, beans);
    }

    /**
     * 上架模版导出
     */
    public WriteStatus exportInboundShelves(OutputStream outputStream, StockTransApplicationCommand sta) {
        Map<String, Object> beans = new HashMap<String, Object>();
        if (sta.getId() == null) { // 上架导出模板
            StockTransVoucherCommand stv = wareHouseManager.findIsSnByStvId(sta.getStvId());
            List<StvLineCommand> list = null;
            List<StvLineCommand> snlist = null;
            if (stv.getIsSnSku() != null && stv.getIsSnSku() == 1 && stv.getSnType() != null && stv.getSnType() == SkuSnType.NO_BARCODE_SKU.getValue()) {
                list = wareHouseManager.findInBoundIsSN(sta.getStvId(), null, false);
                snlist = wareHouseManager.findInBoundIsSN(sta.getStvId(), null, true);
            } else {
                list = wareHouseManager.findInBoundIsSN(sta.getStvId(), null, false);
                snlist = wareHouseManager.findInBoundIsSN(sta.getStvId(), null, true);
            }
            beans.put("stvLineList", list);
            beans.put("stvLineSNList", snlist);
            beans.put("staCode", sta.getCode());
            return inBoundShelvesWriter.write(STA_TEMPLATE_IN_BOUND_SHELVES, outputStream, beans);
        } else { // 虚拟仓收货导出模板
            List<StvLineCommand> list = wareHouseManager.findInventInBoundIsSN(sta.getId(), null, false);
            beans.put("stvLines", list);
            List<StvLineCommand> snlist = wareHouseManager.findInventInBoundIsSN(sta.getId(), null, true);
            beans.put("stvLinesn", snlist);
            return virtualInBoundShelvesWriter.write(STA_TEMPLATE_VIR_IN_BOUND_SHELVES, outputStream, beans);
        }
    }


    public WriteStatus exportNikeCRWCatonLine(OutputStream outputStream, StockTransApplicationCommand sta, Long ouId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<StockTransApplicationCommand> cartonList = wareHouseManager.findNikeCRWCartonLine(sta, ouId);
        beans.put("cartonList", cartonList);
        List<StockTransApplicationCommand> cartonReportList = wareHouseManager.findNikeCRWCartonLine1(sta, ouId);
        beans.put("cartonReportList", cartonReportList);
        return cartonListByCRWWriter.write(NIKE_CRW_CARTON_LINE, outputStream, beans);

    }

    /**
     * 合并上架模版导出
     */
    public WriteStatus exportMergeInboundShelves(OutputStream outputStream, String ids) {
        List<Long> stvList = new ArrayList<Long>();
        if (StringUtil.isEmpty(ids)) {
            throw new BusinessException();
        }
        for (String s : ids.split(",")) {
            try {
                Long temp = Long.valueOf(s);
                if (temp > 0) {
                    stvList.add(temp);
                }
            } catch (Exception e) {
                throw new BusinessException();
            }
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        List<StvLineCommand> list = wareHouseManager.findInBoundIsSN(null, stvList, false);
        beans.put("stvLineList", list);
        List<StvLineCommand> snlist = wareHouseManager.findInBoundIsSN(null, stvList, true);
        beans.put("stvLineSNList", snlist);
        beans.put("staCode", null);
        return inBoundShelvesWriter.write(STA_TEMPLATE_IN_BOUND_SHELVES, outputStream, beans);
    }

    /**
     * 导出补货报表
     */
    @Override
    public WriteStatus exportreplenishorder(ServletOutputStream outs, Long wrId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<WhReplenishLineCommand> list = whReplenishManager.findWhReplenishLienById(wrId);
        beans.put("wrLineList", list);
        return whReplenishWriter.write(WH_REPLENISH_EXPROT_TPL, outs, beans);
    }

    /**
     * 销售|换货出库发票导出
     */
    public WriteStatus exportsoinvoiceforsalesreturnorder(OutputStream os, Long ouid, String _fromDate, String _endDate) {
        Map<String, Object> beans = new HashMap<String, Object>();
        String fromDate = null, endDate = null;
        if (_fromDate != null) {
            fromDate = _fromDate;
        }
        if (_endDate != null) {
            endDate = _endDate;
        }
        List<StaInvoiceCommand> list = wareHouseManager.findSalesOutBoundInvoiceimport(ouid, fromDate, endDate);
        if (list == null) {
            list = new ArrayList<StaInvoiceCommand>();
        }
        beans.put("invoiceList", list);
        return soInvoiceExportWriter.write(STA_EXCEL_FILE_FULL_PATH, os, beans);
    }

    @Override
    public WriteStatus exportConvenienceStoreOrderInfo(ServletOutputStream os, Long ouid, Date _fromDate, Date _endDate) {
        Map<String, Object> beans = new HashMap<String, Object>();
        Date fromDate = null, endDate = null;
        if (_fromDate != null) {
            fromDate = _fromDate;
        }
        if (_endDate != null) {
            endDate = _endDate;
        }
        List<ConvenienceStoreOrderInfo> list = wareHouseManager.findConvenienceStoreOrderInfo(fromDate, endDate, ouid);
        if (list == null) {
            list = new ArrayList<ConvenienceStoreOrderInfo>();
        }
        beans.put("orderInfo", list);
        return convenienceStoreOrderInfoWriter.write(CONVENIENCE_STORE_ORDER_INFO_EXCEL, os, beans);
    }

    /**
     * 配货失败 缺货商品信息导出
     */
    @Override
    public WriteStatus exportStaDistributionOfFailureInfo(OutputStream outputStream, Long id) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<StaErrorLineCommand> list = wareHouseManager.findStaFailure(id);
        if (list == null) {
            list = new ArrayList<StaErrorLineCommand>();

        }
        beans.put("failureInfo", list);
        return failureInfoExportWriter.write(STA_DISTRIBUTION_FAILURE_INFO_EXPORT, outputStream, beans);
    }

    /**
     * sku批量导入获取库存
     */
    @Override
    public WriteStatus exportInventorySku(OutputStream os, List<InventoryCommand> cmmd) {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("command", cmmd);
        return skuInvnetoryWriter.write(INVENTORY_SKU, os, beans);
    }

    /**
     * 库位导出
     */
    @Override
    public WriteStatus exportLocationByParam(OutputStream os, String district, String location, String pickZoneName, String pickZoneCode, Long ouId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<WhPickZoneInfoCommand> pickZoneInfoList = pickZoneEditManager.findPickZoneInfoList2Excel(district, location, pickZoneName, pickZoneCode, ouId);
        if (pickZoneInfoList == null || pickZoneInfoList.size() == 0) {
            pickZoneInfoList = new ArrayList<WhPickZoneInfoCommand>();
        }
        beans.put("pickZoneInfoList", pickZoneInfoList);
        WriteStatus ws = pickZoneExportWriter.write(PICK_ZONE, os, beans);
        // return pickZoneExportWriter.write(PICK_ZONE, os, beans);
        return ws;
    }

    @Override
    public WriteStatus exportSoInvoiceByBatchNo(OutputStream os, String batchNo, List<Long> wioIdList) {
        // 获取发票类型
        String invoiceType = channelManager.exportSoInvoiceByBatchNo(batchNo, wioIdList);
        // 获取发票数据
        InvoiceTypeInterface icType = invoiceFactory.getInvoice(invoiceType);
        List<StaInvoiceCommand> data = icType.getReplenishInvoiceData(batchNo, wioIdList);
        if (data == null) {
            data = new ArrayList<StaInvoiceCommand>();
            data.add(new StaInvoiceCommand());
        } else {
            // 更新导出次数
            // channelManager.addInvoiceExecuteCountByBatchNo(batchNo, wioIdList);
        }
        return icType.writeExcel(data, os);
    }

    @Override
    public WriteStatus exportInboundShelvesImperfect(OutputStream os, List<Long> stvIds) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<StvLineCommand> lists = new ArrayList<StvLineCommand>();
        for (Long stvId : stvIds) {
            List<StvLineCommand> list = null;
            list = wareHouseManager.findInBound(stvId, null);
            lists.addAll(list);
        }
        beans.put("stvLineList", lists);
        return inBoundShelvesWriterImperfect.write(STA_TEMPLATE_IN_BOUND_SHELVES_IMPERFECT, os, beans);
    }

    @Override
    public WriteStatus exportProcurementReturnInboud(OutputStream os, List<Long> stvListId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<StvLineCommand> list = wareHouseManager.findProcurementReturnInboundByStvListId(stvListId);
        if (list == null) {
            list = new ArrayList<StvLineCommand>();
        }
        beans.put("stvLineList", list);
        return procurementrenurninboudputawayWriter.write(PROCUREMENT_RETURN_INBOUND_PUTAWAY, os, beans);
    }

    @Override
    public WriteStatus exportSfNextMorningConfigByOuId(Long id, ServletOutputStream os) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<SfNextMorningConfig> list = sfNextMorningConfigManager.findConfigByOuId(id);
        if (list == null) {
            list = new ArrayList<SfNextMorningConfig>();
        }
        beans.put("configList", list);
        return sfNextMorningConfigWriter.write(SF_NEXT_MORNING_CONFIG, os, beans);
    }


    @Override
    public WriteStatus exportSfConfigByOuId(Long ouId, Long cId, ServletOutputStream os) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<SfCloudWarehouseConfigCommand> list = sfNextMorningConfigManager.findSfConfigByOuId(ouId, cId);
        if (list == null) {
            list = new ArrayList<SfCloudWarehouseConfigCommand>();
        }
        beans.put("configList", list);
        return sfConfigWriter.write(SF_TIME_TYPE_CONFIG, os, beans);
    }

    @Override
    public WriteStatus exportNikeTodaySendConfigByOuId(Long id, ServletOutputStream os) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<LitreSingle> list = baseinfoManager.findLitreSingleByOuId(id);
        if (list == null) {
            list = new ArrayList<LitreSingle>();
        }
        beans.put("configList", list);
        return nikeTodaySendConfigWriter.write(NIKE_TODAY_SEND_CONFIG, os, beans);
    }

    @Override
    public WriteStatus exportClaimantInfo(ServletOutputStream os, List<WhCompensationCommand> CompensationList) {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("claimantList", CompensationList);
        return claimantInfo.write(CLAIMANT_INFO, os, beans);
    }

    @Override
    public WriteStatus exportGucciRtoLineInfo(ServletOutputStream os, List<VmiRtoLineCommand> gucciRtoLineList) {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("gucciRtoLineList", gucciRtoLineList);
        return gucciRtoLineInfo.write(GUCCI_RTO_LINE_INFO, os, beans);
    }

    @Override
    public WriteStatus getExportAgv(ServletOutputStream os, String plCode, String areaCode) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<PickingListCommand> pickingListCommand = wareHouseManager.getExportAgv(plCode, areaCode);
        beans.put("pickingListCommand", pickingListCommand);
        return agvWriter.write(AGV, os, beans);
    }

    @Override
    public WriteStatus exportSkuReplenishmentSuggest(Long ouId, String staCode, OutputStream os) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<SkuReplenishmentCommand> result = wareHouseManager.findReplenishSuggest(ouId,staCode);
        beans.put("list", result);
        return exportSkuReplenishSuggestWriter.write(SKU_REPLENISHMENT_SUGGEST, os, beans);
    }


    /**
     * 导出库位
     */
    @Override
    public WriteStatus exportDistriBution(Long mainWhid,ServletOutputStream outs,String locCodeName,String locCode,String locDistriButionAreaCode,String locDistriButionAreaName) {
        Map<String, Object> beans = new HashMap<String, Object>();
        if("".equals(locCode)) {
           locCode = null;
        }
        if("".equals(locCodeName)) {
            locCodeName = null;
        }else {
            locCode = "%" + locCode + "%";
        }
        if("".equals(locDistriButionAreaName)) {
            locDistriButionAreaName = null;
        }
        if("".equals(locDistriButionAreaCode)) {
            locDistriButionAreaCode = null;
        }
        List<DwhDistriButionAreaLocCommand> list =  distriButionAreaManager.exportDistriButionArea(mainWhid,locCodeName,locCode,locDistriButionAreaCode,locDistriButionAreaName);
        beans.put("dwhDistriButionAreaLocWriteList",list);
        return distriButionWriter.write(DWH_DISTRI_BUTION_AREA_LOC, outs, beans);
    }
    
    @Override
    public WriteStatus exportAdPackage(Long id, ServletOutputStream os) {
        Map<String, Object> beans = new HashMap<String, Object>();
        List<AdPackage> list = baseinfoManager.findAdPackageByOuId(id);
        if (list == null) {
            list = new ArrayList<AdPackage>();
        }
        beans.put("adPackage", list);
        return adPackageExport.write(AD_PACKAGE, os, beans);
    }
    
    public WriteStatus exportThreeDimensionalSkuInfo(ServletOutputStream os, List<SkuCommand> skuList) {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("skuList", skuList);
        return threeDimensionalSkuInfo.write(THREE_DIMENSIONAL_SKU_INFO, os, beans);

    }

    @Override
    public WriteStatus exportNoThreeDimensional(ServletOutputStream os, List<SkuCommand> skuList) {
        Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("skuList", skuList);
        return noThreeDimensionalSku.write(NO_THREE_DIMENSIONAL_SKU, os, beans);
    }

}
