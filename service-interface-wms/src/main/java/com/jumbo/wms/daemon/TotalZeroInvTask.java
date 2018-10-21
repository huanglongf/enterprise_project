package com.jumbo.wms.daemon;

import java.util.Date;

/**
 * Lmis仓储费接口--0点库存数据上传FTP任务(此定时任务使用库存快照结合库存流水的方式，废除不用，有新的接口定义)
 * 
 */
public interface TotalZeroInvTask {

    /**
     * 每日零点库存统计，00:00执行
     */
    void totalInv();

    /**
     * 将数据临时保存在服务器上
     */
    boolean saveFile(Date historyDate, Date nextDate, Integer warehouseType, String storeCode, String warehouseCode, String areaCode);

    /**
     * 上传零点库存统计文件
     * 
     * @return
     */
    public boolean uploadZeroInv();

    /**
     * 备份文件
     * 
     * @return
     */
    boolean backupsFile();

    /**
     * 清空缓存目录
     * 
     * @return
     */
    public void clear();
}
