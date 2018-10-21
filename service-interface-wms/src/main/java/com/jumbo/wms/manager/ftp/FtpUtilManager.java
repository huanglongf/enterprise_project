package com.jumbo.wms.manager.ftp;

public interface FtpUtilManager {
    /**
     * FPT下载文件
     * 
     * @param groupName FTP组配置
     * @param remotePackageName 服务器跟目录后的子目录名，主要用于需要下载多层目录结构
     * @param localPackageName 本地目录
     */
    void downloadFile(String groupName, String remotePackageName, String localPackageName);
}
