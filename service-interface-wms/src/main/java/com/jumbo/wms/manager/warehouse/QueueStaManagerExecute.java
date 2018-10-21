package com.jumbo.wms.manager.warehouse;


import java.util.List;
import java.util.Map;

import com.jumbo.pac.manager.extsys.wms.rmi.model.StaCreatedResponse;
import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.warehouse.QueueStaCommand;
import com.jumbo.wms.model.warehouse.QueueStaLineCommand;

import loxia.dao.Pagination;

public interface QueueStaManagerExecute extends BaseManager {

    void createsta(Long qStaId);

    String sendCreateResultToOms(StaCreatedResponse createdResponse, Long qstaId);

    void addErrorCountForQsta(Long qStaId, int addCount);

    void removeQstaAddLog(Long qstaId);

    void createsta(Long qStaId, List<QueueStaLineCommand> lineCommand);
    
    /**
     * 方法说明：查询(或根据参数) QueueStaCommand
     * @author LuYingMing
     * @date 2016年7月25日 下午2:10:23 
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<QueueStaCommand> findQueueStaByParams(int start, int pageSize, Map<String, Object> params);
    
    /**
     * 方法说明：重置为0
     * @author LuYingMing
     * @date 2016年7月25日 下午5:16:04 
     * @param queueStaId
     */
    void resetToZero(Long queueStaId);
    /**
     * 方法说明：重置为99
     * @author LuYingMing
     * @date 2016年7月25日 下午5:16:35 
     * @param queueStaId
     */
    void resetTo99(Long queueStaId);

    /**
     * PAC反馈创单确认结果
     * 
     * @param msg
     */
    public void excuteOrderCreateResponse(String msg);

}
