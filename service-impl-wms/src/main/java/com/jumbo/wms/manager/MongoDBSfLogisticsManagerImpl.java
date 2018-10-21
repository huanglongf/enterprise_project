package com.jumbo.wms.manager;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.warehouse.MongoDBSfLogisticsDao;
import com.jumbo.wms.model.MongoDBSfLogistics;

@Service("mongoDBSfLogisticsManager")
public class MongoDBSfLogisticsManagerImpl extends BaseManagerImpl implements MongoDBSfLogisticsManager {

    /**
     * 
     */
    private static final long serialVersionUID = 1623131409245662525L;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    MongoDBSfLogisticsDao mongoDBSfLogisticsDao;

    @Override
    public void initMongoDBLogistics() {
        List<MongoDBSfLogistics> dbSfLogistics = mongoDBSfLogisticsDao.findAllLogistics(new BeanPropertyRowMapper<MongoDBSfLogistics>(MongoDBSfLogistics.class));
        for (MongoDBSfLogistics md : dbSfLogistics) {
            mongoOperation.save(md);
        }
    }

    @Override
    public MongoDBSfLogistics getLogistics(String province, String city) {
        return mongoOperation.findOne(new Query(Criteria.where("province").regex(".*?" + province + ".*").and("city").regex(".*?" + city + ".*")), MongoDBSfLogistics.class);
    }
}
