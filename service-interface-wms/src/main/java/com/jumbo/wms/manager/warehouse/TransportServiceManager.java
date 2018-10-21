package com.jumbo.wms.manager.warehouse;

import java.util.List;

import loxia.dao.Sort;

import com.jumbo.wms.model.baseinfo.TransEmsInfo;
import com.jumbo.wms.model.trans.TransportServiceArea;
import com.jumbo.wms.model.trans.TransportServiceCommand;

/**
 * 物流服务
 * 
 * @author xiaolong.fei
 * 
 */
public interface TransportServiceManager {

    List<TransportServiceCommand> findTransServiceList(Sort[] sorts);

    List<TransportServiceCommand> findTransServiceListById(String transId, Sort[] sorts);

    List<TransportServiceArea> findTransServiceAreaListById(String transId, Sort[] sorts);

    public TransEmsInfo findByCmp(Boolean isCod);
    /**
     * 通过物流商code来查询时效类型
     */

    public List<TransportServiceCommand> getTransportServiceByCode(String expcode);

}
