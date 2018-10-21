package com.jumbo.wms.manager;

import com.jumbo.wms.model.MongoDBSfLogistics;

public interface MongoDBSfLogisticsManager extends BaseManager {
    /**
     * 初始化库存
     */
    void initMongoDBLogistics();

    /**
     * 根据省市区查询对应城市编码
     * 
     * @return
     */
    MongoDBSfLogistics getLogistics(String province, String city);


}
