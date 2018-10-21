package com.jumbo.wms.manager.warehouse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.PackageSkuDao;
import com.jumbo.dao.warehouse.PackageSkuLogDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.PackageSku;
import com.jumbo.wms.model.warehouse.PackageSkuCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationPickingType;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;

/**
 * 
 * @author jinlong.ke
 *
 */
@Transactional
@Service("packageSkuManager")
public class PackageSkuManagerImpl extends BaseManagerImpl implements PackageSkuManager {

    /**
     * 
     */
    private static final long serialVersionUID = 1727357380506053810L;
    @Autowired
    private PackageSkuDao packageSkuDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private PackageSkuLogDao packageSkuLogDao;

    @Override
    public void addNewPackageSku(OperationUnit currentOu, Long staTotalSkuQty, Long skuQty, List<Long> idList, List<String> titleList, Date date) {
        String skus1 = null;
        String skus2 = null;
        String skus3 = null;
        Map<Long, String> map = new HashMap<Long, String>();
        for (int i = 0; i < idList.size(); i++) {
            map.put(idList.get(i), titleList.get(i));
        }
        List<String> idList1 = new ArrayList<String>();
        for (int i = 0; i < idList.size(); i++) {
            idList1.add(idList.get(i).toString());
        }
        Collections.sort(idList1);// 排序添加
        if (idList1.size() > 0) {
            skus1 = map.get(Long.parseLong(idList1.get(0)));
        }
        if (idList1.size() > 1) {
            skus2 = map.get(Long.parseLong(idList1.get(1)));
        }
        if (idList1.size() > 2) {
            skus3 = map.get(Long.parseLong(idList1.get(2)));
        }
        PackageSku ps = packageSkuDao.findHaveSkuByContent(currentOu.getId(), staTotalSkuQty, skuQty, skus1, skus2, skus3, new BeanPropertyRowMapper<PackageSku>(PackageSku.class));
        if (ps != null) {
            if (ps.getIsSystem()) {
                packageSkuDao.updateIsSystemById(ps.getId());
                return;
            } else {
                // 该套装组合商品已存在
                throw new BusinessException(ErrorCode.PACKAGESKU_HAVE_EXISTS);
            }
        }
        PackageSku psku = new PackageSku();
        psku.setStaTotalSkuQty(staTotalSkuQty);
        psku.setSkuQty(skuQty);
        psku.setCreateTime(new Date());
        psku.setExpTime(date);
        psku.setSkus1(skus1);
        psku.setSkus2(skus2);
        psku.setSkus3(skus3);
        psku.setOu(currentOu);
        psku.setIsSystem(false);
        packageSkuDao.save(psku);
        packageSkuDao.flush();
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(StockTransApplicationStatus.CREATED.getValue());
        statusList.add(StockTransApplicationStatus.OCCUPIED.getValue());
        statusList.add(StockTransApplicationStatus.FAILED.getValue());
        String split = ":";
        if (skus1 != null) {
            skus1 = "%" + skus1 + "%";
        }
        if (skus2 != null) {
            skus2 = "%" + skus2 + "%";
        }
        if (skus3 != null) {
            skus3 = "%" + skus3 + "%";
        }
        staDao.updateSomeStaToPackageSku(split, psku.getId(), staTotalSkuQty, skuQty, skus1, skus2, skus3, currentOu.getId(), StockTransApplicationPickingType.SKU_MANY.getValue(), statusList, StockTransApplicationPickingType.SKU_PACKAGE.getValue());
    }

    @Override
    public List<PackageSkuCommand> selectAllPackageSkuByOu(Long id, Sort[] sorts) {
        List<PackageSkuCommand> list = packageSkuDao.selectAllPackageSkuByOu(id, sorts, new BeanPropertyRowMapper<PackageSkuCommand>(PackageSkuCommand.class));
        for (PackageSkuCommand psc : list) {
            editPackageSkuCommand(psc);
        }
        return list;
    }

    private void editPackageSkuCommand(PackageSkuCommand psc) {
        Long sku1Id = null;
        Long sku2Id = null;
        Long sku3Id = null;
        String skusName = "共" + psc.getSkuQty() + "种" + psc.getStaTotalSkuQty() + "件商品且包含:";
        String skusIdAndQty = "";// 记录商品ID和数量，多商品以|拼接
        if (psc.getSkus1() != null) {
            sku1Id = Long.parseLong(psc.getSkus1().split(";")[0]);
            Sku sku1 = skuDao.getByPrimaryKey(sku1Id);
            psc.setSkus1BarCode(sku1.getBarCode());
            psc.setSkus1SupportCode(sku1.getSupplierCode() + (sku1.getKeyProperties() == null ? "" : sku1.getKeyProperties()));
            psc.setSkus1Qty(Long.parseLong(psc.getSkus1().split(";")[1]));
            skusName += sku1.getName() + ";" + Long.parseLong(psc.getSkus1().split(";")[1]) + "|";
            skusIdAndQty += psc.getSkus1() + ",";
        }
        if (psc.getSkus2() != null) {
            sku2Id = Long.parseLong(psc.getSkus2().split(";")[0]);
            Sku sku2 = skuDao.getByPrimaryKey(sku2Id);
            psc.setSkus2BarCode(sku2.getBarCode());
            psc.setSkus2SupportCode(sku2.getSupplierCode() + (sku2.getKeyProperties() == null ? "" : sku2.getKeyProperties()));
            psc.setSkus2Qty(Long.parseLong(psc.getSkus2().split(";")[1]));
            skusName += sku2.getName() + ";" + Long.parseLong(psc.getSkus2().split(";")[1]) + "|";
            skusIdAndQty += psc.getSkus2() + ",";
        }
        if (psc.getSkus3() != null) {
            sku3Id = Long.parseLong(psc.getSkus3().split(";")[0]);
            Sku sku3 = skuDao.getByPrimaryKey(sku3Id);
            psc.setSkus3BarCode(sku3.getBarCode());
            psc.setSkus3SupportCode(sku3.getSupplierCode() + (sku3.getKeyProperties() == null ? "" : sku3.getKeyProperties()));
            psc.setSkus3Qty(Long.parseLong(psc.getSkus3().split(";")[1]));
            skusName += sku3.getName() + ";" + Long.parseLong(psc.getSkus3().split(";")[1]) + "|";
            skusIdAndQty += psc.getSkus3() + ",";
        }
        psc.setSkusName(skusName.substring(0, skusName.length() - 1));
        psc.setSkusIdAndQty(skusIdAndQty.substring(0, skusIdAndQty.length() - 1));
    }

    @Override
    public void deletePackageSkuById(Long packageSkuId, Long ouId) {
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(StockTransApplicationStatus.CREATED.getValue());
        statusList.add(StockTransApplicationStatus.FAILED.getValue());
        statusList.add(StockTransApplicationStatus.OCCUPIED.getValue());
        // 更新对应的套装组合商品作业单为普通多件作业单
        staDao.updateStaPackageToMany(packageSkuId, StockTransApplicationPickingType.SKU_PACKAGE.getValue(), StockTransApplicationPickingType.SKU_MANY.getValue(), statusList, ouId);
        // 插入新的packageSkuLog
        packageSkuLogDao.newPackageSkuLog(packageSkuId);
        // 根据Id删除对应的套装组合商品
        packageSkuDao.deleteByPrimaryKey(packageSkuId);
    }

    public String findSkus1ByPackingListId(Long pid) {
        return packageSkuDao.findSkus1ByPackingListId(pid, new SingleColumnRowMapper<String>());
    }

}
