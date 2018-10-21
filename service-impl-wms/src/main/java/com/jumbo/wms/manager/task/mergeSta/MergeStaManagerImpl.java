package com.jumbo.wms.manager.task.mergeSta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuCategoriesDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.BiChannelCombineRefDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.SkuSizeConfigDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaInvoiceDao;
import com.jumbo.dao.warehouse.StaInvoiceLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.expressDelivery.logistics.TransAliWaybill;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.SkuCategories;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.command.SkuCategoriesCommand;
import com.jumbo.wms.model.warehouse.BiChannelCombineRefCommand;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;
import com.jumbo.wms.model.warehouse.StaInvoice;
import com.jumbo.wms.model.warehouse.StaInvoiceLine;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationPickingType;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.TransDeliveryType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;

@Transactional
@Service("mergeStaManager")
public class MergeStaManagerImpl extends BaseManagerImpl implements MergeStaManager {

    private static final long serialVersionUID = -7012929311926370477L;

    @Autowired
    private BiChannelCombineRefDao biChannelCombineRefDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StaInvoiceDao staInvoiceDao;
    @Autowired
    private StaInvoiceLineDao staInvoiceLineDao;
    @Autowired
    private SkuCategoriesDao skuCategoriesDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;
    @Autowired
    private SkuSizeConfigDao skuSizeConfigDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private TransAliWaybill transAliWaybill;

    /**
     * 定时合并STA信息 bin.hu
     */
    @Override
    public void mergeStaTask(Long whOuId) {
        // 获取T_BI_CHANNEL_COMBINE_REF表中相同主渠道+仓库的子渠道ID
        log.info("=========mergeStaTask:" + whOuId);
        List<BiChannelCombineRefCommand> bccr = biChannelCombineRefDao.findBiChannelCombineRefToExpTime(whOuId, new BeanPropertyRowMapperExt<BiChannelCombineRefCommand>(BiChannelCombineRefCommand.class));
        for (BiChannelCombineRefCommand b : bccr) {
            BiChannel biCode = biChannelDao.getByPrimaryKey(b.getcId());// 获取主渠道的CODE
            String[] channelCodeList = b.getChildChannelIdList().split(",");
            List<Long> channelCodeId = new ArrayList<Long>();// 封装ID进行查询
            for (int i = 0; i < channelCodeList.length; i++) {
                channelCodeId.add(Long.parseLong(channelCodeList[i]));
            }
            // 通过渠道ID获取渠道CODE
            List<String> channelCode = biChannelDao.getChannelCode(channelCodeId, new SingleColumnRowMapper<String>(String.class));
            /**
             * 获取所有能合并STA信息 店铺基于合并店铺逻辑计算、同快递、同收货人、联系方式、送货地址， 只有多件、单件、团购订单查与合并订单计算， 只有配货中状态作业单参与合并计算
             * 所有合并过的订单允许再次合并， 单日达、次日达、订单不参与计算， 特殊处理订单不参与计算， 换货出库订单不参与计算 所有外包仓订单不参与计算，跨渠道合併必須有相同的
             * 主渠道和相同的对应仓库 cod作业单不能被合并
             */
            List<BiChannelCombineRefCommand> staIdList = biChannelCombineRefDao.findMergerStaId(whOuId, channelCode, new BeanPropertyRowMapperExt<BiChannelCombineRefCommand>(BiChannelCombineRefCommand.class));
            boolean isMerge = true;
            for (BiChannelCombineRefCommand s : staIdList) {
                // 得到STA信息进行合并
                try {
                    isMerge = true;
                    List<Long> staIdListLong = new ArrayList<Long>();// 封装STAID进行合并
                    String[] staId = s.getStaIdList().split(",");
                    for (int i = 0; i < staId.length; i++) {
                        staIdListLong.add(Long.parseLong(staId[i]));
                        // 如是SF/YTO电子面单且没有快递单号，则不允许加入合并订单
                        StockTransApplication sta = staDao.getByPrimaryKey(Long.parseLong(staId[i]));
                        if (sta != null && (sta.getStaDeliveryInfo().getLpCode().equals("SF") || sta.getStaDeliveryInfo().getLpCode().equals("YTO"))) {
                            if (sta.getStaDeliveryInfo().getTrackingNo() == null || "".equals(sta.getStaDeliveryInfo().getTrackingNo())) {
                                Long whId = sta.getMainWarehouse().getId();
                                Warehouse house = warehouseDao.getByOuId(whId);
                                if ((house.getIsSfOlOrder() != null && house.getIsSfOlOrder()) || (house.getIsYtoOlOrder() != null && house.getIsYtoOlOrder())) {
                                    isMerge = false;
                                }
                            }
                        }
                    }
                    if (!isMerge) {
                        log.info("=========mergeStaTask trackingNo is null:" + s.getId());
                        continue;
                    }
                    creatNewSta(staIdListLong, biCode.getCode());// 合并新订单
                } catch (Exception e) {
                    if (log.isErrorEnabled()) {
                        log.error("mergeStaTask:" + whOuId, e);
                    }
                }
            }
        }
    }

    /**
     * 合并新订单
     */
    public StockTransApplication creatNewSta(List<Long> staOldIdList, String newOwner) {
        if (staOldIdList != null && staOldIdList.size() > 1) {
            String result = checkCreatNewSta(staOldIdList);// 验证这些STA数据是否合并过新STA
            if (!result.equals("OK")) {
                // 需要新建STA
                BigDecimal skuMaxLength = biChannelCombineRefDao.getSkuMaxLength(staOldIdList, new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
                StockTransApplication staOld = null;// 原STA信息
                StaDeliveryInfo diOld = null;// 原物流信息
                BigDecimal totalAmount = new BigDecimal(0);// 采购(采购总金额)/销售(销售单实际总金额)相加
                BigDecimal orderTotalAmount = new BigDecimal(0);// 订单商品金额相加
                BigDecimal orderTransferFree = new BigDecimal(0);// 订单运费相加
                BigDecimal transferFree = new BigDecimal(0);// 物流运费相加
                BigDecimal insuranceAmount = new BigDecimal(0);// 物流保价金额相加
                Long skuQty = 0L;// 商品数量相加
                String slipCode1 = "";
                String slipCode2 = "";

                Map<String, Long> aliNo = new HashMap<String, Long>();// 需要取消的云栈单号

                for (Long staLong : staOldIdList) {
                    // 对原STA一些字段进行合并
                    // slip_code1 slip_code2 Total_Actual
                    staOld = staDao.getByPrimaryKey(staLong);// 原STA信息
                    if (staOld == null) {
                        return null;
                    }
                    diOld = staDeliveryInfoDao.getByPrimaryKey(staLong);// 原物流信息
                    if (diOld == null) {
                        return null;
                    }
                    if (staOld.getTotalActual() != null) {
                        totalAmount = totalAmount.add(staOld.getTotalActual());// 采购(采购总金额)/销售(销售单实际总金额)相加
                    }
                    if (staOld.getOrderTotalActual() != null) {
                        orderTotalAmount = orderTotalAmount.add(staOld.getOrderTotalActual());// 订单商品金额相加
                    }
                    if (diOld.getTransferFee() != null) {
                        transferFree = transferFree.add(diOld.getTransferFee());// 订单运费相加
                    }
                    if (diOld.getInsuranceAmount() != null) {
                        insuranceAmount = insuranceAmount.add(diOld.getInsuranceAmount());// 物流运费相加
                    }
                    if (staOld.getOrderTransferFree() != null) {
                        orderTransferFree = orderTransferFree.add(staOld.getOrderTransferFree());// 物流保价金额相加
                    }
                    if (!StringUtil.isEmpty(staOld.getSlipCode1())) {
                        slipCode1 += staOld.getSlipCode1() + ",";
                    }
                    if (!StringUtil.isEmpty(staOld.getSlipCode2())) {
                        slipCode2 += staOld.getSlipCode2() + ",";
                    }
                    if (diOld.getAliPackageNo() != null && !"".equals(diOld.getAliPackageNo())) {
                        aliNo.put(diOld.getTrackingNo(), staLong);
                    }
                    skuQty += staOld.getSkuQty();
                }
                // 创建新的STA
                StockTransApplication staNew = new StockTransApplication();
                staNew.setType(StockTransApplicationType.OUTBOUND_SALES);
                String slipCode = sequenceManager.getCode(StockTransApplication.class.getName(), staNew);
                staNew.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
                staNew.setCreateTime(new Date());
                staNew.setLastModifyTime(new Date());
                staNew.setOrderCreateTime(staOld.getOrderCreateTime());
                staNew.setCode(slipCode);
                staNew.setRefSlipCode(slipCode);
                if (!slipCode1.equals("")) {
                    staNew.setSlipCode1(slipCode1.substring(0, slipCode1.length() - 1));
                }
                if (!slipCode2.equals("")) {
                    staNew.setSlipCode2(slipCode2.substring(0, slipCode2.length() - 1));
                }
                staNew.setSkuMaxLength(skuMaxLength);
                staNew.setVersion(1);
                staNew.setIsNeedOccupied(false);
                staNew.setIsMerge(true);
                staNew.setCurrency(staOld.getCurrency());
                staNew.setTotalActual(totalAmount);
                staNew.setMainWarehouse(staOld.getMainWarehouse());
                staNew.setOrderTotalActual(orderTotalAmount);
                staNew.setChannelList(staOld.getChannelList());
                staNew.setDeliveryType(staOld.getDeliveryType());
                staNew.setStatus(StockTransApplicationStatus.OCCUPIED);
                staNew.setRefSlipType(staOld.getRefSlipType());
                staNew.setOrderTransferFree(orderTransferFree);
                staNew.setIsBkCheck(staOld.getIsBkCheck());// 设置原来作业单， 是否后端核对SKU
                staNew.setOrderTotalBfDiscount(staOld.getOrderTotalBfDiscount());// 折前金额设置
                if (StringUtil.isEmpty(newOwner)) {
                    staNew.setOwner(staOld.getOwner());
                }
                if (!StringUtil.isEmpty(newOwner)) {
                    staNew.setOwner(newOwner);
                }
                Long ouId = staOld.getMainWarehouse().getId();
                whInfoTimeRefDao.insertWhInfoTime2(slipCode, WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CREATE.getValue(), null, ouId);
                staNew.setOwner(newOwner);
                staNew.setIsSpecialPackaging(staOld.getIsSpecialPackaging());
                staNew.setSkuQty(skuQty);
                staNew.setPickingType(StockTransApplicationPickingType.SKU_MANY);
                staDao.save(staNew);
                updateOldSta(staOldIdList, staNew);// 更新原订单信息
                // 创建新的STA
                // 创建新的STALINE
                for (Long staLineLong : staOldIdList) {
                    List<StaLine> oldLineList = staLineDao.findByStaId(staLineLong);
                    for (StaLine staLine : oldLineList) {
                        StaLine newStaLine = new StaLine();
                        newStaLine.setSta(staNew);
                        newStaLine.setOwner(staLine.getOwner());
                        newStaLine.setQuantity(staLine.getQuantity());
                        newStaLine.setTotalActual(staLine.getTotalActual());
                        newStaLine.setSku(staLine.getSku());
                        newStaLine.setUnitPrice(staLine.getUnitPrice());
                        newStaLine.setOrderTotalActual(staLine.getOrderTotalActual());
                        newStaLine.setOrderTotalBfDiscount(staLine.getOrderTotalBfDiscount());
                        newStaLine.setListPrice(staLine.getListPrice());
                        staLineDao.save(newStaLine);
                    }
                }
                // 创建新的STALINE
                // 创建物流信息
                StaDeliveryInfo diNew = new StaDeliveryInfo();
                diNew.setId(staNew.getId());
                diNew.setAddress(diOld.getAddress());
                diNew.setCity(diOld.getCity());
                diNew.setCountry(diOld.getCountry());
                diNew.setDistrict(diOld.getDistrict());
                diNew.setLpCode(diOld.getLpCode());
                diNew.setMobile(diOld.getMobile());
                diNew.setProvince(diOld.getProvince());
                diNew.setReceiver(diOld.getReceiver());
                diNew.setRemark(diOld.getRemark());
                diNew.setStoreComInvoiceTitle(diOld.getStoreComInvoiceTitle());
                diNew.setTelephone(diOld.getTelephone());
                diNew.setTrackingNo(diOld.getTrackingNo());
                diNew.setTransferFee(transferFree);
                diNew.setZipcode(diOld.getZipcode());
                diNew.setIsCod(diOld.getIsCod());
                diNew.setExtTransOrderId(diOld.getExtTransOrderId());
                diNew.setTransTimeType(diOld.getTransTimeType());
                diNew.setTransType(diOld.getTransType());
                diNew.setInsuranceAmount(insuranceAmount);
                diNew.setStoreComIsNeedInvoice(diOld.getStoreComIsNeedInvoice());
                diNew.setTransCityCode(diOld.getTransCityCode());
                diNew.setTransAccount(diOld.getTransAccount());
                if (diNew.getTrackingNo() != null && aliNo.containsKey(diNew.getTrackingNo())) {
                    aliNo.remove(diNew.getTrackingNo());
                    diNew.setAliPackageNo(diOld.getAliPackageNo());
                }
                staDeliveryInfoDao.save(diNew);
                // 创建物流信息
                // 创建新发票信息
                for (Long staLong : staOldIdList) {
                    List<StaInvoice> siOldList = staInvoiceDao.getBySta(staLong);
                    for (StaInvoice siOld : siOldList) {
                        // 发票头
                        StaInvoice siNew = new StaInvoice();
                        siNew.setSta(staNew);
                        siNew.setInvoiceDate(siOld.getInvoiceDate());
                        siNew.setPayer(siOld.getPayer());
                        siNew.setItem(siOld.getItem());
                        siNew.setQty(siOld.getQty());
                        siNew.setUnitPrice(siOld.getUnitPrice());
                        siNew.setAmt(siOld.getAmt());
                        siNew.setMemo(siOld.getMemo());
                        siNew.setPayee(siOld.getPayee());
                        siNew.setDrawer(siOld.getDrawer());
                        siNew.setAddress(siOld.getAddress());
                        siNew.setIdentificationNumber(siOld.getIdentificationNumber());
                        siNew.setTelephone(siOld.getTelephone());

                        staInvoiceDao.save(siNew);
                        List<StaInvoiceLine> siLineOldList = staInvoiceLineDao.getByStaInvoiceId(siOld.getId());
                        for (StaInvoiceLine siLineOld : siLineOldList) {
                            // 发票明细
                            StaInvoiceLine siLineNew = new StaInvoiceLine();
                            siLineNew.setStaInvoice(siNew);
                            siLineNew.setItem(siLineOld.getItem());
                            siLineNew.setUnitPrice(siLineOld.getUnitPrice());
                            siLineNew.setQty(siLineOld.getQty());
                            siLineNew.setAmt(siLineOld.getAmt());
                            staInvoiceLineDao.save(siLineNew);
                        }
                    }
                }
                // 创建新发票信息
                staDao.flush();
                updateStaSkuInfo(staNew);

                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(staNew.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), null, staOld.getMainWarehouse().getId());
                staNew.setLastModifyTime(new Date());
                // 创建STV
                creatNewStaStaLine(staOldIdList, staNew);

                // 取消多余的云栈单号
                try {
                    for (Long staId : aliNo.values()) {
                        transAliWaybill.cancelTransNoByStaId(staId);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }

                return staNew;
            }
        }
        return null;
    }

    /**
     * 更新作业单过仓后的基本信息
     * 
     * @param staId
     */
    private void updateStaSkuInfo(StockTransApplication sta) {
        // 更新 作业单过后所需基本信息 //获取作业单商品公共分类
        Long scId = findStaSkuCategories(sta.getId());
        if (scId != null) {
            SkuCategories sc = skuCategoriesDao.getByPrimaryKey(scId);
            sta.setSkuCategoriesId(sc);
        }
        String skus = staDao.getSkusByStaId(sta.getId(), ":", new SingleColumnRowMapper<String>(String.class));
        // Long skuQty = staDao.getSkuQtyByStaId(sta.getId(), new
        // SingleColumnRowMapper<Long>(Long.class));
        Boolean isSn = staDao.getIsSnByStaId(sta.getId(), new SingleColumnRowMapper<Boolean>(Boolean.class));
        // BigDecimal skuMaxLength = staDao.getSkuMaxLengthByStaId(sta.getId(), new
        // SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        Integer isRailWay = staDao.getIsRailWayByStaId(sta.getId(), new SingleColumnRowMapper<Integer>(Integer.class));
        sta.setSkus(skus);
        // sta.setSkuQty(skuQty);
        sta.setIsSn(isSn);
        // sta.setSkuMaxLength(skuMaxLength);
        sta.setDeliveryType(TransDeliveryType.valueOf(isRailWay));
        // 盘点订单是否是团购
        // 设置作业单配货类型单件多件团购
        Long groupConfig = skuSizeConfigDao.findConfigBySkuQty(sta.getSkuMaxLength(), new SingleColumnRowMapper<Long>(Long.class));
        if (sta.getSkuQty().compareTo(groupConfig) >= 0 && (sta.getIsSpecialPackaging() == null || !sta.getIsSpecialPackaging())) {
            sta.setPickingType(StockTransApplicationPickingType.GROUP);
        }
        staDao.save(sta);
    }

    /**
     * 获取作业单商品的公共分类
     * 
     * @param staId
     * @return 公共分类节点ID
     */
    private Long findStaSkuCategories(Long staId) {
        // 返回结果
        Long result = null;
        // 查出所有作业单商品的所属分类信息（包括所属分类所有父节点）
        List<SkuCategoriesCommand> list = skuCategoriesDao.findSkuCategoryBySta(staId, new BeanPropertyRowMapperExt<SkuCategoriesCommand>(SkuCategoriesCommand.class));
        // 保存 每个商品里面的分类信息（包括所属分类所有父节点）
        List<List<SkuCategoriesCommand>> skuList = new ArrayList<List<SkuCategoriesCommand>>();
        // 是否是头信息
        boolean isHand = true;
        // 零时保存单个商品的所属分类已经所有父节点信息
        List<SkuCategoriesCommand> temp = null;
        // 将 商品的所属分类以及分类的所有父节点 按照每个商品 所分开来
        for (SkuCategoriesCommand com : list) {
            if (isHand) {
                temp = new ArrayList<SkuCategoriesCommand>();
                skuList.add(temp);
                isHand = false;
            }
            temp.add(com);
            if (com.getParentId() == null || com.getParentId() < 0) {
                isHand = true;
            }
        }
        // 如果 =1 证明作业单只存在一种商品 如果是一种商品 取商品分类 就取商品所属分类
        if (skuList.size() == 1) {
            result = skuList.get(0).get(0).getId();
        } else {
            // 从2开始 是因为 排除掉商品总类的遍历
            int index = 2;
            // 零时公共分类 默认赋值 商品总类
            Long tempSkuC = skuList.get(0).get(skuList.get(0).size() - 1).getId();
            do {
                long skucId = -1;
                for (List<SkuCategoriesCommand> tempList : skuList) {
                    // 判断是否前一次的遍历就是 当前SKU的所属分类节点
                    if (tempList.size() < index) {
                        result = tempSkuC;
                        break;
                    }
                    // 获取当前商品的当前分类节点信息
                    SkuCategoriesCommand bean = tempList.get(tempList.size() - index);
                    if (skucId == -1) {// 判断是否是遍历的第一个商品的分类信息
                        skucId = bean.getId();
                    } else if (skucId != bean.getId()) {// 当铺商品 同一级别的商品所属分类 与别的商品分类信息不同
                        // 所以取当前所属分类级别的上一级做公共
                        // 取上一级的商品分类节点
                        result = tempSkuC;
                        break;
                    }
                }
                // 在没有确定出公共分类时 确保下一轮遍历
                if (result == null) {
                    // ++是为了下一轮遍历下一级的分类节点信息
                    index++;
                    // 保存此次遍历中所遍历过的 分类节点信息
                    tempSkuC = skucId;
                }
            } while (result == null);
        }
        return result;
    }

    /**
     * 判断是否已经有创建过
     */
    public String checkCreatNewSta(List<Long> staIdList) {
        String result = "";
        List<StockTransApplication> staList = biChannelCombineRefDao.findOldStaList(staIdList, new BeanPropertyRowMapperExt<StockTransApplication>(StockTransApplication.class));
        if (staIdList.size() != staList.size()) {
            // 要重新生成新的STA
            if (staList.size() == 0) {
                // 没有合并后的订单信息
                result = "NOUPDATE";
            } else {
                // 有合并过的订单信息需要修改状态为17
                result = "UPDATE";
                StockTransApplication s = staDao.getByPrimaryKey(staList.get(0).getGroupSta().getId());
                // if (s.getPickingList() == null) {// 主订单没有加入配货批的才可以再次合并订单
                s.setStatus(StockTransApplicationStatus.CANCELED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(s.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, s.getMainWarehouse() == null ? null : s.getMainWarehouse().getId());
                s.setMemo(s.getMemo() + "|| 取消标识,点X.");
                s.setCancelTime(new Date());
                s.setLastModifyTime(new Date());
            } /*
               * else { result = "OK"; } }
               */
        }
        if (staIdList.size() == staList.size()) {
            // 不需要重新生成新的STA
            result = "OK";
        }
        return result;
    }


    /**
     * 更新原订单信息
     */
    public void updateOldSta(List<Long> staId, StockTransApplication sta) {
        for (Long staLong : staId) {
            StockTransApplication staOld = staDao.getByPrimaryKey(staLong);
            if (staOld.getStatus().equals(StockTransApplicationStatus.CANCELED) || staOld.getStatus().equals(StockTransApplicationStatus.CANCEL_UNDO)) {
                throw new BusinessException(ErrorCode.STA_CANCELED);
            }
            staOld.setGroupSta(sta);
            staDao.save(staOld);
        }
    }

    /**
     * 创建合并作业单的stv stvline
     */
    @Override
    public void creatNewStaStaLine(List<Long> staId, StockTransApplication sta) {
        StockTransVoucher oldStv = stvDao.findStvCreatedByStaId(staId.get(0).longValue());
        StockTransVoucher newStv = new StockTransVoucher();
        BigDecimal biSeqNo = stvDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        newStv.setBusinessSeqNo(biSeqNo.longValue());
        newStv.setCode(stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>(String.class)));
        newStv.setCreateTime(new Date());
        newStv.setLastModifyTime(new Date());
        newStv.setDirection(oldStv.getDirection());
        newStv.setOwner(oldStv.getOwner());
        newStv.setStatus(oldStv.getStatus());
        newStv.setVersion(0);
        newStv.setCreator(oldStv.getCreator());
        newStv.setSta(sta);
        newStv.setTransactionType(oldStv.getTransactionType());
        newStv.setWarehouse(oldStv.getWarehouse());
        stvDao.save(newStv);
        for (Long s : staId) {
            List<StvLine> oldStvLineList = stvLineDao.findAllByStaId(s, new BeanPropertyRowMapperExt<StvLine>(StvLine.class));
            for (StvLine oldStvLine : oldStvLineList) {
                StvLine stvLine = new StvLine();
                stvLine.setBatchCode(oldStvLine.getBatchCode());
                stvLine.setDirection(oldStvLine.getDirection());
                stvLine.setOwner(oldStvLine.getOwner());
                stvLine.setQuantity(oldStvLine.getQuantity());
                stvLine.setSkuCost(oldStvLine.getSkuCost());
                stvLine.setVersion(0);
                stvLine.setDistrict(oldStvLine.getDistrict());
                stvLine.setInvStatus(oldStvLine.getInvStatus());
                stvLine.setLocation(oldStvLine.getLocation());
                stvLine.setSku(oldStvLine.getSku());
                stvLine.setStaLine(oldStvLine.getStaLine());
                stvLine.setStv(newStv);
                stvLine.setTransactionType(oldStvLine.getTransactionType());
                stvLine.setWarehouse(oldStvLine.getWarehouse());
                stvLine.setInBoundTime(oldStvLine.getInBoundTime());
                stvLine.setProductionDate(oldStvLine.getProductionDate());
                stvLine.setValidDate(oldStvLine.getValidDate());
                stvLine.setExpireDate(oldStvLine.getExpireDate());
                stvLineDao.save(stvLine);
            }
        }
    }

    /**
     * 配货清单子订单部分占用库存成功
     * 
     * @throws Exception
     */
    @Override
    public void newStaNEoldSta(List<Long> staS, List<Long> staE, StockTransApplication sta, PickingList p) {
        try {
            String staMemo = "合并订单配货失败取消，库存占用成功订单：";
            // 此处子作业单有占用失败信息
            if (staS.size() > 1) {
                for (Long stas : staS) {
                    // 占用成功的子作业单和主作业也取消关联，准备合成新的作业单
                    StockTransApplication staes = staDao.getByPrimaryKey(stas);
                    staes.setGroupSta(null);
                    staMemo += staes.getCode() + ",";
                    staDao.save(staes);
                    staDao.flush();
                }
                // 此处重新合并新的作业单
                StockTransApplication newSta = creatNewSta(staS, sta.getOwner());
                sta.setLastModifyTime(new Date());
                sta.setCancelTime(new Date());
                sta.setMemo(staMemo);
                sta.setStatus(StockTransApplicationStatus.CANCELED);// 原合并的新作业单变成取消状态
                sta.setPickingList(null);// 原合并作业单和配货清单取消关联
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                // sta.setIndex(null);
                newSta.setPickingList(p);// 新合并的作业单关联配货清单
                newSta.setStatus(StockTransApplicationStatus.OCCUPIED);
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(newSta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), null, newSta.getMainWarehouse() == null ? null : newSta.getMainWarehouse().getId());
                newSta.setLastModifyTime(new Date());
                newSta.setIndex(sta.getIndex());
                staDao.save(sta);
                staDao.save(newSta);
                for (Long stae : staE) {
                    // 占用失败的子作业单取消和主作业单的关联
                    setStaGroupStaNull(stae);
                }
                // 为合并作业单创建STV和STVLINE
                creatNewStaStaLine(staS, newSta);
            }
            if (staS.size() == 1) {
                // 此处子作业单只有1单占用成功
                StockTransApplication oldSta = staDao.getByPrimaryKey(staS.get(0).longValue());
                oldSta.setPickingList(p);// 只有这单关联配货清单表
                oldSta.setGroupSta(null);// 取消和主作业单的关联
                oldSta.setIndex(sta.getIndex());
                sta.setLastModifyTime(new Date());
                sta.setCancelTime(new Date());
                sta.setMemo(staMemo + "_此处子作业单只有1单占用成功 StaId:" + staS.get(0));
                sta.setStatus(StockTransApplicationStatus.CANCELED);// 原合并的新作业单变成取消状态
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                sta.setPickingList(null);// 原合并作业单和配货清单取消关联
                sta.setIndex(null);
                staDao.save(sta);
                staDao.save(oldSta);
                for (Long stae : staE) {
                    // 占用失败的子作业单取消和主作业单的关联
                    setStaGroupStaNull(stae);
                }
            }
            if (staS.size() == 0) {
                // 此处整个合并作业单子作业单库存不足&占用失败
                // isError = true;
                sta.setLastModifyTime(new Date());
                sta.setCancelTime(new Date());
                sta.setMemo(staMemo + "_此处整个合并作业单子作业单库存不足");
                sta.setStatus(StockTransApplicationStatus.CANCELED);// 原合并的新作业单变成取消状态
                // 订单状态与账号关联
                whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.CANCELED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
                sta.setPickingList(null);// 原合并作业单和配货清单取消关联
                sta.setIndex(null);
                staDao.save(sta);
                for (Long stae : staE) {
                    // 占用失败的子作业单取消和主作业单的关联
                    setStaGroupStaNull(stae);
                }
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }

    private void setStaGroupStaNull(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        sta.setGroupSta(null);
        staDao.save(sta);
        StaDeliveryInfo di = staDeliveryInfoDao.getByPrimaryKey(sta.getId());
        di.setTrackingNo(null);
        staDeliveryInfoDao.save(di);
    }

    /**
     * 配货清单子订单全部占用库存成功
     * 
     * @throws Exception
     */
    @Override
    public void newStaEqualOldSta(List<Long> staS, StockTransApplication sta) {
        try {
            // 此处合并作业单所有子作业单库存占用成功
            sta.setStatus(StockTransApplicationStatus.OCCUPIED);// 把合并的主作业单状态修改成库存占用
            // 订单状态与账号关联
            whInfoTimeRefDao.insertWhInfoTime2(sta.getRefSlipCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.OCCUPIED.getValue(), null, sta.getMainWarehouse() == null ? null : sta.getMainWarehouse().getId());
            sta.setLastModifyTime(new Date());
            staDao.save(sta);
            // 为合并作业单创建STV和STVLINE
            creatNewStaStaLine(staS, sta);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }
}
