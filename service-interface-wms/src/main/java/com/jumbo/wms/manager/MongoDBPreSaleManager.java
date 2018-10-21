package com.jumbo.wms.manager;

public interface MongoDBPreSaleManager {
    /**
     * 记录是否允许发货
     */
    void initMongoDBAdvanceOrderAddInfo();

    /**
     * 记录修改地址
     */
    void initMongoDBRecieverInfo();
}
