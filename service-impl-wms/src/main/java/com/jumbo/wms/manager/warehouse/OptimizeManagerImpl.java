package com.jumbo.wms.manager.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.warehouse.StaErrorLineDao;
import com.jumbo.wms.model.warehouse.StaErrorLine;

/**
 * 系统优化过程中新增的接口
 * 
 * @author fanht
 * 
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service("optimizeManager")
public class OptimizeManagerImpl implements OptimizeManager {
    private static final long serialVersionUID = -5613281314064161821L;

    @Autowired
    private StaErrorLineDao staErrorLineDao;

    /**
     * 配货失败，记录错误表
     * 
     * @param staErrorLine
     */
    @Override
    public void insertStaErrorLine(StaErrorLine staErrorLine) {
        // 先删除
        staErrorLineDao.deleteByStaErrorLine(staErrorLine.getStaId(), staErrorLine.getSkuId());
        // 再插入
        staErrorLineDao.save(staErrorLine);
    }

}
