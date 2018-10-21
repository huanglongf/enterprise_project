/**
 * 
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
 * 
 */
package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baoshui.CustomsDeclarationDao;
import com.jumbo.dao.baoshui.CustomsDeclarationLineDao;
import com.jumbo.dao.baseinfo.LicensePlateDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockInDao;
import com.jumbo.dao.warehouse.PackageInfoDao;
import com.jumbo.dao.warehouse.SkuDeclarationDao;
import com.jumbo.dao.warehouse.SkuOriginDeclarationDao;
import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.model.baseinfo.LicensePlate;
import com.jumbo.wms.model.baseinfo.MongoCarService;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuDeclaration;
import com.jumbo.wms.model.baseinfo.SkuOriginDeclaration;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.nikeData.NikeVmiStockInCommand;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransTxLogCommand;
import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclaration;
import com.jumbo.wms.model.warehouse.baoShui.CustomsDeclarationLine;
import com.mongodb.WriteResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Transactional
@Service("customsDeclarationManager")
public class CustomsDeclarationManagerImpl extends BaseManagerImpl implements CustomsDeclarationManager {

    /**
     * 
     */
    private static final long serialVersionUID = -832216268526546918L;
    
    @Autowired
    private LicensePlateDao licensePlateDao;
    @Autowired
    private SequenceManager sequenceManager;

    @Autowired
    private StaLineDao staLineDao;

    @Autowired
    private CustomsDeclarationDao customsDeclarationDao;
    @Autowired
    private CustomsDeclarationLineDao customsDeclarationLineDao;

    @Autowired
    private StockTransTxLogDao stockTransTxLogDao;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private PackageInfoDao packageInfoDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private NikeStockInDao nikeStockInDao;
    @Autowired
    private SkuDeclarationDao skuDeclarationDao;
    @Autowired
    private SkuOriginDeclarationDao skuOriginDeclarationDao;


    /**
     * 创建报关信息
     * 
     * @param sta
     */
    public void newCustomsDeclaration(StockTransApplication sta, Boolean recommendCar) {
        if (customsDeclarationDao.getCustomsDeclarationByStaCode(sta.getCode()) != null) {
            return;
        }
        Map<String, Object> infoMap = findCustomsDeclarationInfo(sta.getId());

        BigDecimal vehicleVolume = infoMap.get("vehicleVolume") == null ? null : (BigDecimal) infoMap.get("vehicleVolume");
        BigDecimal grossWt = infoMap.get("grossWt") == null ? null : (BigDecimal) infoMap.get("grossWt");
        BigDecimal netWt = infoMap.get("netWt") == null ? null : (BigDecimal) infoMap.get("netWt");
        Integer qty = infoMap.get("qty") == null ? null : (Integer) infoMap.get("qty");
        String code = infoMap.get("code") == null ? null : (String) infoMap.get("code");
        String lpn = null;
        // 是否需要推荐车辆
        if (vehicleVolume != null && (vehicleVolume.compareTo(BigDecimal.ZERO) > 0) && recommendCar) {
            // 推荐车辆信息
            lpn = updateCarvehicleVolume(vehicleVolume);
        }

        Integer type = 1;
        if (sta.getType() == StockTransApplicationType.OUTBOUND_SALES || sta.getType() == StockTransApplicationType.VMI_RETURN || sta.getType() == StockTransApplicationType.OUTBOUND_RETURN_REQUEST) {
            type = 2;
        } else {// 入库单据按计划量算
            qty = sta.getSkuQty().intValue();
        }

        List<StaLine> staLineList2 = staLineDao.findByStaId(sta.getId());
        CustomsDeclaration customsDeclaration = new CustomsDeclaration();// 保税仓头
        customsDeclaration.setCreateId(null);
        customsDeclaration.setCreateTime(new Date());
        customsDeclaration.setCreateUser(null);
        customsDeclaration.setFromIdSource(code);
        customsDeclaration.setGrossWt(grossWt);// 毛重
        customsDeclaration.setIsToModify(false);// 申报数量是否变更
        customsDeclaration.setLicensePlateNumber(lpn);// 车牌号
        customsDeclaration.setMainWhId(sta.getMainWarehouse().getId());
        customsDeclaration.setNetWt(netWt);// 净重
        customsDeclaration.setOwner(sta.getOwner());
        customsDeclaration.setPackNo(qty);// 总件数
        customsDeclaration.setPlatFromCode(sta.getSlipCode1());
        // customsDeclaration.setPrestowageNo("配载单号");//配载单号
        customsDeclaration.setSlipCode(sta.getRefSlipCode());
        customsDeclaration.setStatus(1);// 状态 1:未同步 2:同步成功 3:同步失败
        customsDeclaration.setVersion(0);
        customsDeclaration.setWmsCode(sta.getCode());
        customsDeclaration.setWmsStatus("已转出");
        customsDeclaration.setWmsType(sta.getType().toString());
        customsDeclaration.setWrapType("2");// 包装种类
        customsDeclaration.setType(type);
        customsDeclaration = customsDeclarationDao.save(customsDeclaration);
        List<CustomsDeclarationLine> cdlList = customsDeclarationLineDao.findNewCustomsDeclarationLineBySalesStaId(sta.getId(), new BeanPropertyRowMapper<CustomsDeclarationLine>(CustomsDeclarationLine.class));
        if (cdlList == null || cdlList.size() == 0 && type == 2) {
            cdlList = customsDeclarationLineDao.findNewCustomsDeclarationLineByStaId(sta.getId(), new BeanPropertyRowMapper<CustomsDeclarationLine>(CustomsDeclarationLine.class));
        }
        if (cdlList != null && cdlList.size() > 0) {
            for (CustomsDeclarationLine cdl : cdlList) {
                cdl.setIsManualAdd(false);
                cdl.setGunit("件");
                customsDeclarationLineDao.save(cdl);
            }

        } else {

            CustomsDeclarationLine customsDeclarationLine = null;
            for (StaLine staLine : staLineList2) {
                Sku sku = skuDao.getByPrimaryKey(staLine.getSku().getId());
                List<SkuDeclaration> sdList = skuDeclarationDao.findSkuDeclarationMoreLocation(sku.getCode(), new BeanPropertyRowMapper<SkuDeclaration>(SkuDeclaration.class));
                for (SkuDeclaration skuDeclaration : sdList) {
                    BigDecimal declPrice = skuDeclaration.getDeclPrice();
                    if (skuDeclaration.getIsDiscount() == 1) {
                        declPrice.multiply(new BigDecimal("1.17"));
                    } else {
                        declPrice.multiply(new BigDecimal("1.7")).multiply(new BigDecimal("1.17"));
                    }

                    customsDeclarationLine = new CustomsDeclarationLine();// 报税仓明细
                    // CustomsDeclarationLine.setCurr("币制");
                    customsDeclarationLine.setCustomsDeclarationId(customsDeclaration.getId());
                    customsDeclarationLine.setDeclPrice(declPrice);
                    customsDeclarationLine.setPlanQty(staLine.getQuantity());
                    customsDeclarationLine.setGqty(staLine.getCompleteQuantity());
                    customsDeclarationLine.setGunit("件");
                    customsDeclarationLine.setNotes(null);
                    // CustomsDeclarationLine.setSkuDeclarationId(staLine.getSku().getId());
                    customsDeclarationLine.setCountryOfOrigin(null);
                    customsDeclarationLine.setSkuCode(sku.getCode());
                    customsDeclarationLine.setUpc(sku.getExtensionCode2());
                    customsDeclarationLine.setIsManualAdd(false);
                    customsDeclarationLine.setHsCode(skuDeclaration.getHsCode());
                    customsDeclarationLineDao.save(customsDeclarationLine);
                }
            }
        }
    }

    /**
     * 更新车辆剩余载重量
     * 
     * @return
     */
    public synchronized String updateCarvehicleVolume(BigDecimal vehicleVolume) {
        List<LicensePlate> lpList = licensePlateDao.findLicensePlateByDay(new BeanPropertyRowMapper<LicensePlate>(LicensePlate.class));
        for (LicensePlate lp : lpList) {
            MongoCarService mcs = mongoOperation.findOne(new Query(Criteria.where("id").is(lp.getId())), MongoCarService.class);
            if (mcs == null) {
                continue;
            }
            if (mcs.getVehicleVolume().compareTo(vehicleVolume) < 0) {
                continue;
            }

            // mongoOperation.findAndModify(new Query(Criteria.where("id").is(lp.getId())), new
            // Update().inc("vehicleVolume",mcs.getVehicleVolume().subtract(weight)) , new
            // FindAndModifyOptions().upsert(false).returnNew(true), MongoCarService.class);
            WriteResult wr = mongoOperation.updateFirst(new Query(Criteria.where("id").is(lp.getId()).and("vehicleVolume").gte(vehicleVolume)), new Update().inc("vehicleVolume", mcs.getVehicleVolume().subtract(vehicleVolume)), MongoCarService.class);
            if (wr.getN() > 0) {
                return lp.getLicensePlateNumber();
            } else {
                continue;
            }
        }

        return null;
    }

    /**
     * 计算报关数据
     * 
     * @param staId
     * @return
     */
    private Map<String, Object> findCustomsDeclarationInfo(Long staId) {
        Map<String, Object> infoMap = new HashMap<String, Object>();
        BigDecimal vehicleVolume = new BigDecimal("0");

        Integer qty = 0;
        String code = null;
        // 计算总体积
        List<StaAdditionalLine> salList = staAdditionalLineDao.findByStaId(staId);
        if (salList != null && salList.size() > 0) {
            for (StaAdditionalLine sal : salList) {
                Sku sku = skuDao.getByPrimaryKey(sal.getSku().getId());
                vehicleVolume = vehicleVolume.add(sku.getLength().multiply(sku.getHeight()).multiply(sku.getWidth()));


            }
            infoMap.put("vehicleVolume", vehicleVolume);
        }

        // 计算出库数量
        List<StockTransTxLogCommand> stcList = stockTransTxLogDao.findStaLogByStaId(staId, new BeanPropertyRowMapper<StockTransTxLogCommand>(StockTransTxLogCommand.class));
        if (stcList != null && stcList.size() > 0) {
            for (StockTransTxLogCommand stc : stcList) {
                qty += stc.getInQty().intValue();
            }
            infoMap.put("qty", qty);
        }
        // 获取重量
        Map<String, BigDecimal> weightMap = findWeight(staId);
        if (weightMap != null) {
            infoMap.putAll(weightMap);
        }
        // 获取code
        code = sequenceManager.getCustomsDeclarationCode();
        infoMap.put("code", code);

        return infoMap;
    }

    /**
     * 获取重量
     * 
     * @param staId
     * @return
     */
    public Map<String, BigDecimal> findWeight(Long staId) {
        Map<String, BigDecimal> infoMap = new HashMap<String, BigDecimal>();
        BigDecimal grossWt = new BigDecimal("0");
        BigDecimal netWt = null;
        // 计算总毛重
        List<PackageInfo> piList = packageInfoDao.getByStaId(staId);
        if (piList != null && piList.size() > 0) {
            for (PackageInfo pi : piList) {
                grossWt = grossWt.add(pi.getWeight());
            }
            infoMap.put("grossWt", grossWt);
        }
        // 计算总净重
        netWt = customsDeclarationDao.findOrderNetWt(staId, new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        if (netWt != null && (netWt.compareTo(BigDecimal.ZERO) > 0)) {
            infoMap.put("netWt", netWt);
        }
        return infoMap;
    }

    /**
     * 获取推荐车辆车牌号
     * 
     * @param staId
     * @return
     */
    public String findLicensePlateNumber(Long staId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        Warehouse wh = warehouseDao.getByOuId(sta.getMainWarehouse().getId());
        Boolean isBondedWarehouse = wh.getIsBondedWarehouse();
        if (isBondedWarehouse != null && isBondedWarehouse) {// 如果是保税仓
            CustomsDeclaration cd = customsDeclarationDao.getCustomsDeclarationByStaCode(sta.getCode());
            if (cd != null && !StringUtil.isEmpty(cd.getLicensePlateNumber())) {
                return cd.getLicensePlateNumber();
            }
            return "failed";

        }
        return null;
    }
    
    public List<NikeVmiStockInCommand> findNikeVmiStockInByDeclaration(String nikeVmiCode) {
        return nikeStockInDao.findNikeVmiStockInByDeclaration(nikeVmiCode, new BeanPropertyRowMapper<NikeVmiStockInCommand>(NikeVmiStockInCommand.class));
        
    }

    public StockTransApplication findStaById(Long staId) {
        return staDao.getByPrimaryKey(staId);
    }

    @Override
    public void changeIsCustomsDeclaration(StockTransApplication stockTransApplication, NikeVmiStockInCommand nikeVmiStockInCommand) {
        newCustomsDeclaration(stockTransApplication, false);
        nikeVmiStockInCommand = nikeStockInDao.getByPrimaryKey(nikeVmiStockInCommand.getId());
        nikeVmiStockInCommand.setIsCustomsDeclaration(true);
        nikeStockInDao.save(nikeVmiStockInCommand);
    }

    @Override
    public void changeCustomsDeclarationStatus(Long id, Integer status, String errorMsg) {
        CustomsDeclaration customsDeclaration = customsDeclarationDao.getByPrimaryKey(id);
        customsDeclaration.setStatus(status);
        customsDeclaration.setErrorMsg(errorMsg);
        if (status == 3) {
            customsDeclaration.setErrorCount(customsDeclaration.getErrorCount() == null ? 1 : customsDeclaration.getErrorCount() + 1);
        }
    }

    @Override
    public void changeSkuDeclarationStatus(Long id, Integer status, String errorMsg) {
        SkuDeclaration skuDeclaration = skuDeclarationDao.getByPrimaryKey(id);
        skuDeclaration.setStatus(status);
        skuDeclaration.setErrorMsg(errorMsg);
        if (status == 3) {
            skuDeclaration.setErrorCount(skuDeclaration.getErrorCount() == null ? 1 : skuDeclaration.getErrorCount() + 1);
        } else if (status == 2) {
            skuDeclaration.setIsPush(0);
        }
    }

    @Override
    public void changeCustomsDeclarationDeclarationStatus(Long id, Integer status) {
        CustomsDeclaration customsDeclaration = customsDeclarationDao.getByPrimaryKey(id);
        customsDeclaration.setDeclarationStatus(status);
    }

    @Override
    public void changeSkuDeclarationDeclarationStatus(List<SkuDeclaration> sdList, Integer status, Integer ciqStatus) {
        for (SkuDeclaration sd : sdList) {
            SkuDeclaration skuDeclaration = skuDeclarationDao.getByPrimaryKey(sd.getId());
            skuDeclaration.setDeclarationStatus(status);
            skuDeclaration.setCiqStatus(ciqStatus);
        }
    }

    @Override
    public void newSkuDeclaration(String msg) {
        /*
         * for (SkuDeclaration sd : sdList) { SkuDeclaration skuDeclaration =
         * skuDeclarationDao.getByPrimaryKey(sd.getId());
         * skuDeclaration.setDeclarationStatus(status); skuDeclaration.setCiqStatus(ciqStatus); }
         */

        JSONObject jsonObject = JSONObject.fromObject(msg);

        JSONArray js = jsonObject.getJSONArray("products");
        for (int i = 0; i < js.size(); i++) {
            Set<String> hsCodeSet = new HashSet<String>();// hsCode合并

            JSONObject product = js.getJSONObject(i);
            String productCd = product.getString("product_cd");
            String[] style = productCd.split("-");
            JSONArray components = product.getJSONArray("components");
            JSONObject component = components.getJSONObject(0);
            JSONObject classification = component.getJSONObject("classification");
            JSONArray htsSizes = classification.getJSONArray("hts_sizes");
            for (int j = 0; j < htsSizes.size(); j++) {
                JSONObject hs = htsSizes.getJSONObject(j);
                hsCodeSet.add(hs.getJSONObject("hts").getString("code"));
            }

            JSONArray sizes = product.getJSONArray("sizes");
            for (int y = 0; y < js.size(); y++) {
                JSONObject size = sizes.getJSONObject(y);
                for (String hsCode : hsCodeSet) {
                    SkuDeclaration sd = new SkuDeclaration();
                    // sd.setCode(code);
                    // sd.setSkuCode(skuCode);
                    sd.setUpc(size.getString("upc"));
                    sd.setHsCode(hsCode);
                    sd.setSkuName(component.getString("customs_description"));
                    sd.setSkuDescribe(component.getString("detailed_description"));
                    sd.setStyle(style[0]);
                    sd.setColor(style[1]);
                    sd.setSkuSize(size.getString("size"));
                    sd.setIsDiscount("ACT".equals(product.getString("lifecycle")) ? 0 : 1);
                    sd.setNetWt(new BigDecimal(size.getJSONObject("weight").getString("value")));
                    sd.setDeclPrice(new BigDecimal(size.getJSONObject("price").getString("value")));
                    sd.setIsPush(1);
                    sd.setStatus(1);
                    sd.setCreateTime(new Date());
                    sd.setVersion(0);
                    sd.setKsm(productCd + "-" + size.getString("size"));
                    sd.setgUnit("件");
                    skuDeclarationDao.save(sd);

                    // 新增产地
                    JSONArray manCtryOrigin = component.getJSONArray("man_ctry_origin");
                    for (int z = 0; z < manCtryOrigin.size(); z++) {
                        SkuOriginDeclaration sod = new SkuOriginDeclaration();
                        sod.setOrogin(manCtryOrigin.getString(z));
                        sod.setSkuDeclarationId(sd.getId());

                        skuOriginDeclarationDao.save(sod);
                    }
                }
            }



        }
    }

}
