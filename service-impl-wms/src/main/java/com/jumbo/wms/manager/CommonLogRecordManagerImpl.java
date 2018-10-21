package com.jumbo.wms.manager;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.CommonLogRecord;

/**
 * MongoDB库存
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("commonLogRecordManager")
public class CommonLogRecordManagerImpl extends BaseManagerImpl implements CommonLogRecordManager {
    /**
     * 
     */
    private static final long serialVersionUID = 2291151635758947804L;
    protected static final Logger log = LoggerFactory.getLogger(CommonLogRecordManagerImpl.class);
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;

    @Override
    public void saveLog(String keyWord, String storeType, String content) {
        CommonLogRecord cr = new CommonLogRecord();
        cr.setContent(content);
        cr.setKeyWord(keyWord);
        cr.setStoreType(storeType);
        mongoOperation.save(cr);
    }



}
