package com.jumbo.wms.manager.vmi;

/**
 * 通用VMI读取文件并备份
 * 
 * @author jinlong.ke
 * 
 */
public interface VmiFileManager {

    boolean inBoundreadFileIntoDB(String localFileDir, String bakFileDir, String string, String channelVmicodeConverseYx);

}
