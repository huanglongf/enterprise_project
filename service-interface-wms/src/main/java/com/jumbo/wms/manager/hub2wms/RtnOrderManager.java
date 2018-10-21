package com.jumbo.wms.manager.hub2wms;

import java.util.Date;

import com.jumbo.rmi.warehouse.BaseResult;
import com.jumbo.rmi.warehouse.PackageResult;
import com.jumbo.wms.model.hub2wms.WmsPackageHead;
import com.jumbo.wms.model.hub2wms.WmsRtnInOrderQueue;
import com.jumbo.wms.model.hub2wms.WmsSalesOrderQueue;
import com.jumbo.wms.model.warehouse.AdPackageLineDealDto;

import loxia.dao.Pagination;

public interface RtnOrderManager {
    /**
     * 创建作业单
     * 
     * @param wmsRtnInOrderQueue
     */
    public void createRtnOrder(WmsSalesOrderQueue orderQueue, WmsRtnInOrderQueue wmsRtnInOrderQueue);

    public void deleteRtnOutOrder(WmsSalesOrderQueue orderQueue);

    public void createRtnOrder(WmsRtnInOrderQueue wmsRtnInOrderQueue);

    /**
     * 异常包裹接口查询
     * 
     * @param start
     * @param pagesize
     * @param systemKey
     * @param starteTime
     * @param endTime
     * @return
     */
    public Pagination<WmsPackageHead> getRtnInOrderByTime(int start, int pagesize, String systemKey, Date starteTime, Date endTime);

    /**
     * 异常包裹反馈接口
     * 
     * @param result
     * @return
     */
    public BaseResult RtnPackageResult(PackageResult result);


    BaseResult returnOrderUpdate(String returnInstruction, String transferNum, String status);
    
    /**
     * ad异常包裹反馈给wms
     */
    
    BaseResult adReturnOrderUpdate(AdPackageLineDealDto adPackageLineDealDto);
    BaseResult adReturnOrderUpdateTest();

}
