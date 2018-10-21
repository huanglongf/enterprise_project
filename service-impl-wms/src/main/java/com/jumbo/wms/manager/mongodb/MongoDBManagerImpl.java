/**
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
 */
package com.jumbo.wms.manager.mongodb;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.mongodb.MongoDBYtoBigWordDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.mongodb.MongoDBYtoBigWord;
import com.jumbo.wms.model.warehouse.StaDeliveryInfo;

/**
 * @author lichuan
 * 
 */
@Service("mongoDBManager")
public class MongoDBManagerImpl extends BaseManagerImpl implements MongoDBManager {

    private static final long serialVersionUID = 7021190236889896557L;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;
    @Autowired
    private MongoDBYtoBigWordDao mongoDBYtoBigWordDao;

    /**
     * 初始化圆通大头笔信息到MongoDB
     */
    @Override
    public synchronized void initYtoBigWordIntoMongoDB() {
        mongoOperation.dropCollection(mongoOperation.getCollectionName(MongoDBYtoBigWord.class));
        List<MongoDBYtoBigWord> list = mongoDBYtoBigWordDao.findAllYtoBigWord(new BeanPropertyRowMapper<MongoDBYtoBigWord>(MongoDBYtoBigWord.class));
        for (MongoDBYtoBigWord bw : list) {
            mongoOperation.save(bw);
        }
    }

    /**
     * 根据目的省和目的地查询模糊查找打包编码
     */
    @Override
    public MongoDBYtoBigWord findPackNo(String province, String district) {
        return mongoOperation.findOne(new Query(Criteria.where("province").regex(".*?" + province + ".*").and("district").regex(".*?" + district + ".*")), MongoDBYtoBigWord.class);
    }

    /**
     * 根据目的省和目的地查询模糊匹配打包编码
     */
    @Override
    public List<MongoDBYtoBigWord> matchingAllPackNo(StaDeliveryInfo sd) {
        String province = (null != sd.getProvince() ? (sd.getProvince()).replace("省", "").replace("市", "") : "");
        String city = (null != sd.getCity() ? (sd.getCity()).replace("市", "").replace("区", "") : "");
        if (city.contains("新")) {
            city = sd.getCity();
        }
        String district = (null != sd.getDistrict() ? (sd.getDistrict()).replace("市", "").replace("区", "") : "");
        if (district.contains("新")) {
            district = sd.getDistrict();
        }
        Criteria cr = new Criteria();
        return mongoOperation.find(
                new Query(cr.orOperator(Criteria.where("province").regex(".*?" + province + ".*").and("district").regex(".*?" + district + ".*"), Criteria.where("province").regex(".*?" + province + ".*").and("district").regex(".*?" + city + ".*"))),
                MongoDBYtoBigWord.class);
    }

    /**
     * 根据目的省和目的地查询模糊匹配打包编码
     */
    @Override
    public MongoDBYtoBigWord matchingPackNo(StaDeliveryInfo sd) {
        MongoDBYtoBigWord bw = new MongoDBYtoBigWord();
        List<MongoDBYtoBigWord> list = matchingAllPackNo(sd);
        if (null == list || 0 == list.size()) {
            return bw;
        }
        Collections.sort(list, new Comparator<MongoDBYtoBigWord>() {
            @Override
            public int compare(MongoDBYtoBigWord o1, MongoDBYtoBigWord o2) {
                return o1.getPriority() - o2.getPriority();
            }
        });
        /*
         * for(MongoDBYtoBigWord b : list){ System.out.println(b.getPackNo() + " " + b.getDistrict()
         * + " " + b.getProvince() + " " + b.getPriority()); }
         */
        bw = list.get(0);
        return bw;
    }

}
