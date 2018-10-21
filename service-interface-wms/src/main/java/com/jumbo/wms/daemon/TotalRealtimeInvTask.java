package com.jumbo.wms.daemon;


/**
 * Lmis仓储费接口--凌晨4点实时库存数据上传FTP任务
 * 
 */
public interface TotalRealtimeInvTask {

    /**
     * 每日凌晨4点实时库存统计
     */
    void totalRealtimeInv();

    /**
     * 将数据临时保存在服务器上
     */
    boolean saveFile();

    /**
     * 上传库存统计文件
     * 
     * @return
     */
    public boolean uploadRealtimeInv();

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
