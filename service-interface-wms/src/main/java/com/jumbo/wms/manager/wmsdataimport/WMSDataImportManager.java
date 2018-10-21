package com.jumbo.wms.manager.wmsdataimport;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.dataimport.ShopDataSource;
import com.jumbo.wms.model.dataimport.SnAndValidDateSkuFlow;

public interface WMSDataImportManager extends BaseManager {

    /**
     * 生成退货入库尚未完成的SN或者有效期商品相关数据
     * 
     */
    public void generateSnAndValidDateSkuFlow(Date createTime, Date endCreateTime, String postshopInput, String slipcodes);

    /**
     * 生成单个店铺的SnAndValidDateSkuFlow数据--线程任务
     * 
     */
    public Boolean createEachFile(Date createTime, Date endCreateTime, String owner, List<String> slipCode1s, String path, ExecutorService executorService);

    /**
     * 封装sn和过期时间数据--子线程任务
     * 
     */
    public void packagingSnAndExpDate(SnAndValidDateSkuFlow snAndValidDateSkuFlow, List<SnAndValidDateSkuFlow> list);

    /**
     * 查询文件生成状态
     * 
     */
    public String getFileCreateStatus();

    /**
     * 尚未完成品牌入库指令数据文件生成
     * 
     */
    public void generateBrandUnFinishedInstructions(String postshopInput, List<ShopDataSource> addList);

    /**
     * 尚未完成品牌入库指令数据文件生成--线程任务
     * 
     */
    public Boolean packagingBrandUnFinishedIns(String owner, String path, String dataSource);

    /**
     * 获取下载文件的输入流
     * 
     */
    public String backupFile(String file);

}
