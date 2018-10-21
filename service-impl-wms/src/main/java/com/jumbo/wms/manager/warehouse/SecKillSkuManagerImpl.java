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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.command.SecKillSkuCommandDao;
import com.jumbo.dao.warehouse.SecKillSkuCounterDao;
import com.jumbo.dao.warehouse.SecKillSkuDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SecKillSkuCommand;
import com.jumbo.wms.model.warehouse.SecKillSku;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("secKillSkuManager")
public class SecKillSkuManagerImpl extends BaseManagerImpl implements SecKillSkuManager {

    /**
     * 
     */
    private static final long serialVersionUID = 5424544135751860595L;
    @Autowired
    private SecKillSkuDao secKillSkuDao;
    @Autowired
    private SecKillSkuCommandDao secKillSkuCommandDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SecKillSkuCounterDao secKillSkuCounterDao;

    @Override
    public void addNewSecKillSku(OperationUnit currentOu, Integer skuQty, List<Long> idList, List<String> titleList, Date expireDate) {
        Map<Long, String> map = new HashMap<Long, String>();
        for (int i = 0; i < idList.size(); i++) {
            map.put(idList.get(i), titleList.get(i));
        }
        List<String> idList1 = new ArrayList<String>();
        for (int i = 0; i < idList.size(); i++) {
            idList1.add(idList.get(i).toString());
        }
        Collections.sort(idList1);
        String skus = "";
        for (int i = 0; i < idList1.size(); i++) {
            skus += map.get(Long.parseLong(idList1.get(i)));
        }
        skus = skuQty + ":" + skus;
        SecKillSku sks = secKillSkuDao.getSecKillSkuByOuAndName(currentOu.getId(), skus.substring(0, skus.length() - 1), new BeanPropertyRowMapper<SecKillSku>(SecKillSku.class));
        if (sks != null) {
            if (sks.getIsSystem()) {
                secKillSkuDao.updateIsSystemById(sks.getId());
                return;
            } else {
                throw new BusinessException("该秒杀商品已存在!");
            }
        }
        SecKillSku ss = new SecKillSku();
        ss.setOu(currentOu);
        ss.setSkus(skus.substring(0, skus.length() - 1));
        ss.setExpirationTime(expireDate);
        ss.setCreateTime(new Date());
        ss.setIsSystem(false);
        secKillSkuDao.save(ss);
        staDao.updateSomeStaToSecKillOrder(skus.substring(0, skus.length() - 1), currentOu.getId());
    }

    @Override
    public List<SecKillSkuCommand> selectAllSecKillSkuByOu(Long id, Sort[] sorts) {
        List<SecKillSkuCommand> list = secKillSkuCommandDao.selectAllSecKillSkuByOuId(id, sorts, new BeanPropertyRowMapper<SecKillSkuCommand>(SecKillSkuCommand.class));
        for (SecKillSkuCommand sc : list) {
            setScValue(sc);
        }
        return list;
    }

    private void setScValue(SecKillSkuCommand sc) {
        String skusString = "";
        String[] slist = sc.getSkus().split(":")[1].split(",");
        Map<Long, Integer> map = new HashMap<Long, Integer>();
        List<Long> idList = new ArrayList<Long>();
        for (String ss : slist) {
            map.put(Long.parseLong(ss.split(";")[0]), Integer.parseInt(ss.split(";")[1]));
            idList.add(Long.parseLong(ss.split(";")[0]));
        }
        List<Sku> skuList = skuDao.getSkuByIdList(idList, new BeanPropertyRowMapper<Sku>(Sku.class));
        int sum = 0;
        for (Sku s : skuList) {
            skusString += s.getName() + "|" + map.get(s.getId()) + "/";
            sum += map.get(s.getId());
        }
        skusString = skusString.substring(0, skusString.length() - 1);
        sc.setKind(Integer.parseInt(sc.getSkus().split(":")[0]));
        sc.setSkusString(skusString);
        sc.setSecKillString("共" + sc.getKind() + "种，" + sum + "件：" + sc.getSkusString());

    }

    @Override
    public void deleteSecKillSkuByIdAndSkus(String skus, Long secKillId, Long id) {
        secKillSkuCounterDao.rebackSecKillCounterBySkus(skus, id);
        staDao.updateStaTypeSecKill(skus, id, false);
        secKillSkuDao.deleteByPrimaryKey(secKillId);
    }

}
