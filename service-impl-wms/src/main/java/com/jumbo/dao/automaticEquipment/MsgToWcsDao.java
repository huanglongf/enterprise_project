package com.jumbo.dao.automaticEquipment;

import java.util.List;
import java.util.Map;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.MsgToWcs;
import com.jumbo.wms.model.command.automaticEquipment.MsgToWcsCommand;

/**
 * @author jinlong.ke
 * @date 2016年6月7日下午5:45:47
 * 
 */
@Transactional
public interface MsgToWcsDao extends GenericEntityDao<MsgToWcs, Long> {
    /**
     * WMS到WCS通讯消息记录
     * 
     * @param contexts 接口信息内容，JSON格式
     * @param iType 接口类型
     * @param staCode 作业单号
     * @param pickingListCode 配货波次号
     * @param containerCode 容器号
     * @param trackingNo 运单号
     */
    @NativeUpdate
    void insertMsgToWcs(@QueryParam(value = "contexts") String contexts, @QueryParam(value = "iType") Integer iType, @QueryParam(value = "staCode") String staCode, @QueryParam(value = "pickingListCode") String pickingListCode,
            @QueryParam(value = "containerCode") String containerCode, @QueryParam(value = "trackingNo") String trackingNo);

    /**
     * 查询2000单错误次数少于5的通知WCS
     * 
     * @param row
     * @return
     */
    @NativeQuery
    List<MsgToWcsCommand> findWcsList(RowMapper<MsgToWcsCommand> row);
    
    /**
     * 方法说明：查询 errorCount(错误次数)大于1 MsgToWcs实体 
     * @author LuYingMing
     * @date 2016年7月19日 下午5:04:47 
     * @param start
     * @param pageSize
     * @param m
     * @param rowMapper
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<MsgToWcsCommand> findMsgToWcsByParams(int start, int pageSize, @QueryParam Map<String, Object> m, RowMapper<MsgToWcsCommand> rowMapper);
    
    
    /**
     * 方法说明：重置（修改error_count>1的数据为error_count=0）
     * @author LuYingMing
     * @date 2016年7月21日 上午10:12:42 
     * @param id
     */
    @NativeUpdate
    void resetErrorCount();
    
}
