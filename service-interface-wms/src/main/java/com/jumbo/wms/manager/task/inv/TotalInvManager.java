package com.jumbo.wms.manager.task.inv;



/**
 * 
 * @author xiaolong.fei
 * 
 */
public interface TotalInvManager {
    /**
     * 全量库存同步
     * 
     * @return
     */
    public boolean salesInventory();

    /**
     * 库存同步邮件推送
     * 
     * @param type
     */
    public void emailNotice(String type);

    /**
     * 生成库存文件ZIP包
     */
    public boolean createInvFile();

    /**
     * 解压库存文件
     */
    public boolean createZipAndCheck();

    /**
     * 备份库存文件
     */
    public void copyFile(boolean isAll);

    /**
     * 删除同名文件
     * 
     * @param zipFileName
     * @return
     */
    public boolean deleteSameFile();

    /**
     * 上传ftp文件
     * 
     * @return
     */
    public boolean uploadFileToFtp();
    /**
     * 上传ftp文件
     * cheng.su
     * @return
     */
    public boolean fileToFtp();

    /**
     * 获取上传目录的文件名
     * 
     * @return
     */
    public String getFileName();
}
