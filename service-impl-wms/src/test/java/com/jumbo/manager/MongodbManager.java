package com.jumbo.manager;

import java.util.List;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.jumbo.mongo.dao.InventoryRepository;
import com.jumbo.mongo.entry.Inventory;
import com.jumbo.wms.manager.BaseManagerImpl;

@Service("mongodbManager")
public class MongodbManager extends BaseManagerImpl {
    /**
     * 
     */
    private static final long serialVersionUID = -1234612909624369089L;

//    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
//    @Autowired
    InventoryRepository inventoryRepository;

    public void saveData() {
        Inventory inv = new Inventory();
        inv.setOcpQty(10L);
        inv.setQty(30L);
        inv.setOwner("OWNER");
        inv.setSku("SKU1");
        inv.setWhCode("WHCODE");
        inventoryRepository.save(inv);
    }

    public void getDataListByMongoOperations() {
        List<Inventory> invs = mongoOperation.find(new Query(Criteria.where("qty").gt(20)), Inventory.class);
        if (invs != null) {
            for (Inventory inv : invs) {
                System.out.println(inv.getSku() + " " + inv.getQty() + " " + inv.getOcpQty());
            }
        }
    }

    public void updateDataListByMongoOperations() {
        List<Inventory> invs = mongoOperation.find(new Query(Criteria.where("qty").gt(20)), Inventory.class);
        if (invs != null) {
            for (Inventory inv : invs) {
                System.out.println(inv.getSku() + " " + inv.getQty() + " " + inv.getOcpQty());
            }
        }
    }

    public void updateIncDataListByMongoOperations() {
        mongoOperation.updateMulti(new Query(Criteria.where("sku").is("SKU")), new Update().inc("qty", 50), Inventory.class);
        List<Inventory> invs = mongoOperation.find(new Query(Criteria.where("qty").gt(20)), Inventory.class);
        if (invs != null) {
            for (Inventory inv : invs) {
                System.out.println(inv.getSku() + " " + inv.getQty() + " " + inv.getOcpQty());
            }
        }
    }

    public void getDataList() {
        List<Inventory> invs = inventoryRepository.findAll();
        if (invs != null) {
            for (Inventory inv : invs) {
                System.out.println(inv.getSku() + " " + inv.getQty() + " " + inv.getOcpQty());
            }
        }
    }

    public void getData() {
        Inventory inv = inventoryRepository.findOneBySkuAndOwner("SKU", "OWNER");
        System.out.println(inv.getSku() + " " + inv.getQty() + " " + inv.getOcpQty());
    }


}
