package com.jumbo.mongo.dao;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.jumbo.mongo.entry.Inventory;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {
    
    public Inventory findOneBySkuAndOwner(String sku,String owner);
}
