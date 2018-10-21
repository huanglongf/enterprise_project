package com.jumbo.wms.manager.task.vmi;

import java.io.File;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.daemon.EspritTask;
import com.jumbo.wms.manager.util.SpringUtil;



public class ReadInvDataFile implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ReadInvDataFile.class);


    private EspritTask espritTask;

    private File filePathsList;

    private String localPath;
    private String localBackupPath;
    private String fileStart;

    public ReadInvDataFile(File filePathsList, String localPath, String localBackupPath, String fileStart) {
        this.filePathsList = filePathsList;
        this.localPath = localPath;
        this.localBackupPath = localBackupPath;
        this.fileStart = fileStart;
        espritTask = (EspritTask) SpringUtil.getBean("espTask");
        if (espritTask == null) {
            Log.info("SpringUtil.getBean失败！");
            espritTask = (EspritTask) SpringUtil.getBean("espTask", EspritTask.class);
        }

    }

    /*
     * @Override public void run() { File file = null; try { while (index < filePathsList.size()) {
     * // synchronized (this) { if (index >= filePathsList.size()) { continue; } file =
     * filePathsList.get(index); index++; // } System.out.println(espritTask);
     * espritTask.analyzeInvData(file, localPath, localBackupPath, fileStart);
     * System.out.println("当前使用的线程是：" + Thread.currentThread().getName() + ",正在读文件:" +
     * file.getName()); } Thread.sleep(3000); } catch (InterruptedException e) {
     * e.printStackTrace(); } catch (Exception e1) { e1.printStackTrace(); } }
     */


    public String getLocalPath() {
        return localPath;
    }

    public File getFilePathsList() {
        return filePathsList;
    }

    public void setFilePathsList(File filePathsList) {
        this.filePathsList = filePathsList;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getLocalBackupPath() {
        return localBackupPath;
    }

    public void setLocalBackupPath(String localBackupPath) {
        this.localBackupPath = localBackupPath;
    }

    public String getFileStart() {
        return fileStart;
    }

    public void setFileStart(String fileStart) {
        this.fileStart = fileStart;
    }

    @Override
    public void run() {
        try {
            espritTask.analyzeInvData(filePathsList, localPath, localBackupPath, fileStart);
        } catch (Exception e) {
            logger.error("ReadInvDataFile" + e);
        }
    }

}
