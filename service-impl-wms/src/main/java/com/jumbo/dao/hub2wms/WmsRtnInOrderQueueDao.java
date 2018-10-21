package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.WmsRtnInOrderQueue;

/**
 * 
 * 
 * @author cheng.su
 * 
 */
@Transactional
public interface WmsRtnInOrderQueueDao extends GenericEntityDao<WmsRtnInOrderQueue, Long> {
    /**
     * 根据前置单据查询非取消单据
     * 
     * @param code
     * @return
     */
    @NamedQuery
    public WmsRtnInOrderQueue findStaByOrderCodeNotCancel(@QueryParam("code") String code);

    /**
     * 获取单据批次号
     * 
     * @param s
     * @return
     */
    @NativeQuery
    public String queryBatchcode(SingleColumnRowMapper<String> s);

    /**
     * 更新退换货单据批次
     * 
     * @return
     */
    @NativeUpdate
    public int updateQstaBatchCodeByOuid(@QueryParam("barchCode") String barchCode, @QueryParam("num") Integer num);

    /**
     * 根据批次号查询列队单据ID
     * 
     * @param ouid
     * @param batchcode
     * @return
     */
    @NativeQuery
    public List<WmsRtnInOrderQueue> findByRtnInorderBatchcode(@QueryParam("batchcode") String batchcode, RowMapper<WmsRtnInOrderQueue> r);

    /**
     * 根据仓库ID批次号查询正常销售列队
     * 
     * @param ouid
     * @param batchcode
     * @return
     */
    @NativeQuery
    public List<String> findBatchCodeByDetial(SingleColumnRowMapper<String> singleColumnRowMapper);

}
