package com.jumbo.wms.manager;


/**
 * 通用记录日志到MongoDB接口
 * 
 * @author jinlong.ke
 * 
 */
public interface CommonLogRecordManager extends BaseManager {

    /**
     * @param keyWord
     * @param storeType
     * @param content
     */
    void saveLog(String keyWord, String storeType, String content);

}
