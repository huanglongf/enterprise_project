package com.jumbo.wms.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.MongoDBInventoryDao;
import com.jumbo.wms.manager.hub2wms.CreateStaTaskManager;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.model.MongoDBInventory;
import com.jumbo.wms.model.MongoDBInventoryOcp;
import com.jumbo.wms.model.MongoDBInventoryPac;
import com.jumbo.wms.model.S2MongoDBInventory;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;

/**
 * MongoDB库存
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("mongoDBInventoryManager")
public class MongoDBInventoryManagerImpl extends BaseManagerImpl implements MongoDBInventoryManager {
    private static final Logger log = LoggerFactory.getLogger(MongoDBInventoryManagerImpl.class);

    /**
     * 
     */
    private static final long serialVersionUID = -8303583849852970637L;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private MongoDBInventoryDao mongoDBInventoryDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private CreateStaTaskManager createStaTaskManager;
    @Autowired
    OperationUnitDao operationUnitDao;

    @Override
    public void updateMongoDBInventory(List<MongoDBInventory> ml) {
        for (MongoDBInventory mi : ml) {
            updateMongoDBAvailQty(mi.getWhCode(), mi.getOwner(), mi.getSkuId(), -mi.getAvailQty());
        }
    }

    /**
     * 更新MongoDB库存 需要判断更新是否成功 如果有库存失败 返还前面更新成功的库存
     */
    @Override
    public boolean updateMongoDBInventoryNew(List<MongoDBInventory> ml) {
        boolean check = true;
        // 保存扣减成功的数据
        Map<String, Long> map = new HashMap<String, Long>();
        for (MongoDBInventory mi : ml) {
            boolean b = updateMongoDBAvailQtyNew(mi.getWhCode(), mi.getOwner(), mi.getSkuId(), -mi.getAvailQty());
            if (!b) {
                // 更新失败 返还被扣除的库存
                check = false;
                break;
            }
            String key = mi.getWhCode() + "-" + mi.getOwner() + "-" + mi.getSkuId();
            if (map.get(key) != null) {
                // 有相同的KEY 累加VALUES
                map.put(key, map.get(key) + mi.getAvailQty());
            } else {
                map.put(key, mi.getAvailQty());
            }
        }
        if (!check) {
            // 有失败返还更新成功的库存
            if (map.size() > 0) {
                for (String key : map.keySet()) {
                    String[] keys = key.split("-");
                    Long qty = Long.parseLong(map.get(key).toString());
                    try {
                        // 增加mongoDB库存
                        updateMongoDBAvailQty(keys[0], keys[1], Long.parseLong(keys[2]), qty);
                    } catch (Exception e) {
                        // 失败只记录LOG
                        log.error("MongoDBInventoryManagerImpl.updateMongoDBInventoryNew add inv error key: " + key + " value: " + qty);
                    }
                }
            }
        }
        return check;
    }

    @Override
    public void updateMongoDBInventoryPac(List<MongoDBInventoryPac> ml) {
        for (MongoDBInventoryPac mi : ml) {
            updateMongoDBAvailQtyPac(mi.getWhCode(), mi.getOwner(), mi.getSkuCode(), -mi.getAvailQty());
        }
    }

    @Override
    public MongoDBInventory getInventory(String warehouseCode, String owner, Long skuId) {
        // 当标示需要每次初始化一批库存，并且该方法由直连过仓调用时,会查询单据的库存
        return mongoOperation.findOne(new Query(Criteria.where("whCode").is(warehouseCode).and("owner").is(owner).and("skuId").is(skuId)), MongoDBInventory.class);
    }

    @Override
    public List<MongoDBInventory> getInventoryOwner(String owner, Long skuId) {
        return mongoOperation.find(new Query(Criteria.where("owner").is(owner).and("skuId").is(skuId)), MongoDBInventory.class);
    }

    @Override
    public void updateMongoDBAvailQty(String warehouseCode, String owner, Long skuId, Long variation) {
        mongoOperation.updateFirst(new Query(Criteria.where("whCode").is(warehouseCode).and("owner").is(owner).and("skuId").is(skuId)), new Update().inc("availQty", variation), MongoDBInventory.class);
    }

    @Override
    public boolean updateMongoDBAvailQtyNew(String warehouseCode, String owner, Long skuId, Long variation) {
        WriteResult wr = mongoOperation.updateFirst(new Query(Criteria.where("whCode").is(warehouseCode).and("owner").is(owner).and("skuId").is(skuId).and("availQty").gt(variation)), new Update().inc("availQty", variation), MongoDBInventory.class);
        // 如果成功返回true
        // System.out.println(wr.getN());
        if (wr.getN() > 0) {
            return true;
        }
        return false;
    }

    public void updateMongoDBAvailQtyPac(String warehouseCode, String owner, String skuCode, Long variation) {
        mongoOperation.updateFirst(new Query(Criteria.where("whCode").is(warehouseCode).and("owner").is(owner).and("skuCode").is(skuCode)), new Update().inc("availQty", variation), "S2MongoDBInventory" + warehouseCode);
    }

    @Override
    public List<Long> getAllNeedWarehouseId() {
        return warehouseDao.getAllWarehouse(new SingleColumnRowMapper<Long>(Long.class));
    }

    @Override
    public Integer getSystemThreadValueByKey(String key) {
        return chooseOptionManager.getSystemThreadValueByKey(key);
    }

    @Override
    public void deleteInventoryForOnceUse() {
        mongoOperation.dropCollection(MongoDBInventory.class);
    }

    @Override
    public void deleteInventoryForOuId() {
        List<String> warehouseCodelist = createStaTaskManager.findInitInventoryWarehouseCodeListDelete();
        for (String string : warehouseCodelist) {
            mongoOperation.dropCollection("S2MongoDBInventory" + string);
        }

    }


    @Override
    public void createTableForMongoInv() {
        DBCollection dc = mongoOperation.createCollection(MongoDBInventory.class);
        BasicDBObject keys = new BasicDBObject();
        keys.put("skuId", 1);
        keys.put("whCode", 1);
        keys.put("owner", 1);
        BasicDBObject options = new BasicDBObject();
        options.put("unique", true);
        options.put("dropDups", true);
        options.put("name", "index_query_inventory");
        dc.createIndex(keys, options);
    }

    @Override
    public void initInventoryForOnceUseByOwner(String owner) {
        log.error("initInventoryForOnceUseByOwner start" + owner);
        if ("1".equals(IS_TOMS_INVENTORY)) {
            // 根据店铺查询出中间表 所有商品
            Map<String, Long> map = new HashMap<String, Long>();
            String dName = mongoOperation.getCollectionName(MongoDBInventory.class);
            List<Long> skuIds = mongoDBInventoryDao.findAllSkuByOwner(owner, new SingleColumnRowMapper<Long>(Long.class));
            String key = "";
            for (Long skuId : skuIds) {
                List<MongoDBInventory> skuInventoryList = mongoDBInventoryDao.findSkuInventoryByOwnerSkuId(owner, skuId, new BeanPropertyRowMapper<MongoDBInventory>(MongoDBInventory.class));
                for (MongoDBInventory mongoDBInventory : skuInventoryList) {
                    // 仓库code_店铺code_skuId :数量
                    key = mongoDBInventory.getWhCode() + ";" + mongoDBInventory.getOwner() + ";" + mongoDBInventory.getSkuId();
                    if (map.get(key) == null) {
                        map.put(key, mongoDBInventory.getAvailQty());
                    } else {
                        map.put(key, map.get(key) + mongoDBInventory.getAvailQty());
                    }
                }
            }
            String key2 = "";
            for (Long skuId : skuIds) {
                List<MongoDBInventory> staInventoryList = mongoDBInventoryDao.findStaInventoryByOwnerSkuId(owner, skuId, new BeanPropertyRowMapper<MongoDBInventory>(MongoDBInventory.class));
                for (MongoDBInventory mongoDBInventory : staInventoryList) {
                    // 仓库code_店铺code_skuId :数量
                    key2 = mongoDBInventory.getWhCode() + ";" + mongoDBInventory.getOwner() + ";" + mongoDBInventory.getSkuId();
                    if (map.get(key2) != null) {
                        map.put(key2, map.get(key2) - mongoDBInventory.getAvailQty());
                    }
                }
            }
            List<MongoDBInventory> newlist = new ArrayList<MongoDBInventory>();
            MongoDBInventory md = null;
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                md = new MongoDBInventory();
                String whCode = entry.getKey().split(";")[0];// 仓库
                Long skuId = Long.parseLong(entry.getKey().split(";")[2]);// SKUID
                Long availQty = entry.getValue();// availQty
                md.setOwner(owner);
                md.setAvailQty(availQty);
                md.setSkuId(skuId);
                md.setWhCode(whCode);
                newlist.add(md);
            }
            for (MongoDBInventory newMd : newlist) {
                mongoOperation.save(newMd, dName);
            }
            log.error("initInventoryForOnceUseByOwner end" + owner);
        } else {
            List<MongoDBInventory> list = mongoDBInventoryDao.findInventoryForOnceUseByOwner(owner, new BeanPropertyRowMapper<MongoDBInventory>(MongoDBInventory.class));
            log.error(System.currentTimeMillis() + "-2");
            String dName = mongoOperation.getCollectionName(MongoDBInventory.class);
            // System.out.println("dName: " + dName);
            // MongoDBInventory m1 = new MongoDBInventory();
            // m1.setSkuId(8558515L);
            // m1.setOwner("1多乐士官方旗舰店");
            // m1.setAvailQty(10L);
            // m1.setWhCode("多乐士中铁仓001");
            // mongoOperation.save(m1, dName);
            for (MongoDBInventory md : list) {
                mongoOperation.save(md, dName);
            }
        }

    }

    /**
     * 区域占用库存初始化
     */
    public void initInventoryForOcpAreaByOwner(MongoDBInventoryOcp ocp, Long ouId) {
        mongoOperation.save(ocp, "AreaInventoryOcp" + ouId);
        if (log.isDebugEnabled()) {
            log.debug("AreaInventoryOcp save:" + ouId + "," + ocp.getOwner() + "," + ocp.getSkuId() + "," + ocp.getZoonCode() + "," + ocp.getAvailQty());
        }
    }

    public void createTableForMongoAreaOcpInv(Long ouId) {
        DBCollection dc = mongoOperation.createCollection("AreaInventoryOcp" + ouId);
        BasicDBObject keys = new BasicDBObject();
        keys.put("skuId", 1);
        keys.put("ouId", 1);
        keys.put("owner", 1);
        keys.put("zoonCode", 1);
        BasicDBObject options = new BasicDBObject();
        options.put("unique", true);
        options.put("dropDups", true);
        options.put("name", "ocp_index_query_inv" + ouId);
        dc.createIndex(keys, options);
    }

    public void deleteInventoryForAreaOcpInv(Long ouId) {
        mongoOperation.dropCollection("AreaInventoryOcp" + ouId);
    }

    @Override
    public void initInventoryByOuId(String warehouseCode) {
        log.info("initInventoryByOuId_start:");
        DBCollection dc = mongoOperation.createCollection("S2MongoDBInventory" + warehouseCode);
        BasicDBObject keys = new BasicDBObject();
        keys.put("skuCode", 1);
        keys.put("skuId", 1);
        keys.put("whCode", 1);
        keys.put("extCode", 1);
        keys.put("owner", 1);
        BasicDBObject options = new BasicDBObject();
        options.put("unique", true);
        options.put("dropDups", true);
        options.put("name", "index_query_inventory" + warehouseCode);
        dc.createIndex(keys, options);
        OperationUnit ou1 = operationUnitDao.getByCode(warehouseCode);
        Warehouse warehouse = warehouseDao.getByOuId(ou1.getId());
        List<MongoDBInventoryPac> list = null;
        if (warehouse != null && warehouse.getVmiSource() != null && warehouse.getVmiSource().equals("af")) {
            list = mongoDBInventoryDao.findInventoryForOnceUseBywhCodeAF(warehouseCode, new BeanPropertyRowMapper<MongoDBInventoryPac>(MongoDBInventoryPac.class));
        } else if ("1".equals(IS_PACS_INVENTORY)) {
            log.info("initInventoryByOuId_start1:" + warehouseCode);
            // 根据店铺查询出中间表 所有商品
            Map<String, Long> map = new HashMap<String, Long>();
            List<MongoDBInventoryPac> skuCodes = mongoDBInventoryDao.findAllSkuByOwnerPacs(null, warehouseCode, new BeanPropertyRowMapper<MongoDBInventoryPac>(MongoDBInventoryPac.class));
            String key = "";
            for (MongoDBInventoryPac en : skuCodes) {
                List<MongoDBInventoryPac> skuInventoryList = mongoDBInventoryDao.findSkuInventoryByOwnerSkuIdPacs(warehouseCode, en.getSkuCode(), new BeanPropertyRowMapper<MongoDBInventoryPac>(MongoDBInventoryPac.class));
                for (MongoDBInventoryPac mongoDBInventory : skuInventoryList) {
                    // 仓库code_店铺code_skuId :数量
                    key = mongoDBInventory.getWhCode() + ";" + mongoDBInventory.getOwner() + ";" + mongoDBInventory.getSkuCode() + ";" + mongoDBInventory.getExtCode();
                    if (map.get(key) == null) {
                        map.put(key, mongoDBInventory.getAvailQty());
                    } else {
                        map.put(key, map.get(key) + mongoDBInventory.getAvailQty());
                    }
                }
            }

            String key2 = "";
            for (MongoDBInventoryPac en : skuCodes) {
                List<MongoDBInventoryPac> staInventoryList = mongoDBInventoryDao.findStaInventoryByOwnerSkuIdPacs(warehouseCode, en.getSkuCode(), new BeanPropertyRowMapper<MongoDBInventoryPac>(MongoDBInventoryPac.class));
                for (MongoDBInventoryPac mongoDBInventory : staInventoryList) {
                    // 仓库code_店铺code_skuId :数量
                    key = mongoDBInventory.getWhCode() + ";" + mongoDBInventory.getOwner() + ";" + mongoDBInventory.getSkuCode() + ";" + mongoDBInventory.getExtCode();
                    if (map.get(key2) != null) {
                        map.put(key2, map.get(key2) - mongoDBInventory.getAvailQty());
                    }
                }
            }
            list = new ArrayList<MongoDBInventoryPac>();
            MongoDBInventoryPac md = null;
            for (Map.Entry<String, Long> entry : map.entrySet()) {
                md = new MongoDBInventoryPac();
                String whCode = entry.getKey().split(";")[0];// 仓库
                String skuId = entry.getKey().split(";")[2];// SKUCODE
                Long availQty = entry.getValue();// availQty
                md.setOwner(entry.getKey().split(";")[1]);// 店铺
                md.setAvailQty(availQty);
                md.setSkuCode(skuId);
                md.setWhCode(whCode);
                md.setExtCode(entry.getKey().split(";")[3]);// 商品的extCode
                list.add(md);
            }
            log.info("initInventoryByOuId_start2:" + warehouseCode);
        } else {
            list = mongoDBInventoryDao.findInventoryForOnceUseBywhCode(warehouse.getOu().getId(), new BeanPropertyRowMapper<MongoDBInventoryPac>(MongoDBInventoryPac.class));
        }
        log.info("initInventoryByOuId_end:" + warehouseCode);
        for (MongoDBInventoryPac md : list) {
            mongoOperation.save(md, dc.getName());
        }

    }

    @Override
    public List<MongoDBInventoryPac> getInventoryPac(String warehouseCode, String owner, String skuCode) {
        String[] channlList = owner.split(";");
        List<String> owners = new ArrayList<String>();
        for (int i = 0; i < channlList.length; i++) {
            owners.add(channlList[i]);
        }
        return (List<MongoDBInventoryPac>) mongoOperation.find(new Query(Criteria.where("whCode").is(warehouseCode).and("owner").in(owners).and("skuCode").is(skuCode)), MongoDBInventoryPac.class, "S2MongoDBInventory" + warehouseCode);
    }

    @Override
    public List<MongoDBInventoryPac> getInventoryExtCode3Pac(String warehouseCode, String owner, String extCode3) {
        return (List<MongoDBInventoryPac>) mongoOperation.find(new Query(Criteria.where("whCode").is(warehouseCode).and("owner").is(owner).and("extCode").is(extCode3)), MongoDBInventoryPac.class, "S2MongoDBInventory" + warehouseCode);
    }

    @Override
    public MongoDBInventoryPac getInventoryPacOwner(String warehouseCode, String owner, String skuCode) {
        // 当标示需要每次初始化一批库存，并且该方法由直连过仓调用时,会查询单据的库存
        return mongoOperation.findOne(new Query(Criteria.where("whCode").is(warehouseCode).and("owner").is(owner).and("skuCode").is(skuCode)), S2MongoDBInventory.class, "S2MongoDBInventory" + warehouseCode);
    }

    public List<MongoDBInventoryOcp> getOcpInventoryOwner(String owner, Long ouId, List<Long> skuId, List<String> areaList) {
        return mongoOperation.find(new Query(Criteria.where("owner").is(owner).and("ouId").is(ouId).and("skuId").in(skuId).and("zoonCode").in(areaList)), MongoDBInventoryOcp.class, "AreaInventoryOcp" + ouId);
    }

    public MongoDBInventoryOcp findOneOcpInv(String owner, Long ouId, Long skuId, String areaList) {
        // 查询MongDb库存
        return mongoOperation.findOne(new Query(Criteria.where("owner").is(owner).and("ouId").is(ouId).and("skuId").is(skuId).and("zoonCode").is(areaList)), MongoDBInventoryOcp.class, "AreaInventoryOcp" + ouId);
    }

    /**
     * 修改占用库存的mongDb库存
     */
    public int updateOcpMongoDBAvailQty(Long ouId, String owner, String zoonCode, Long skuId, Integer quantity) {
        if (quantity < 0) {
            WriteResult r =
                    mongoOperation.updateFirst(new Query(Criteria.where("ouId").is(ouId).and("owner").is(owner).and("skuId").is(skuId).and("zoonCode").is(zoonCode).and("availQty").gte(-quantity)), new Update().inc("availQty", quantity),
                            "AreaInventoryOcp" + ouId);
            // System.out.println(r.getN());
            return r.getN();
        } else {
            // 新增库存
            WriteResult r = mongoOperation.updateFirst(new Query(Criteria.where("ouId").is(ouId).and("owner").is(owner).and("skuId").is(skuId).and("zoonCode").is(zoonCode)), new Update().inc("availQty", quantity), "AreaInventoryOcp" + ouId);
            return r.getN();
        }
    }
}
