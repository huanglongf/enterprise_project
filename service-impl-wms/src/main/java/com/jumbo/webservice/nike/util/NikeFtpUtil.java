package com.jumbo.webservice.nike.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;

public class NikeFtpUtil {

    protected static final Logger log = LoggerFactory.getLogger(NikeFtpUtil.class);

    private static final Integer FTP_SERVER_TIMEOUT = 60000;

    /**
     * Description: 向FTP服务器上传文件
     * 
     * @param path FTP服务器保存目录
     * @param localPath 上传到FTP服务器上的文件名
     * @return 成功返回true，否则返回false
     * @throws IOException
     */
    public static boolean sendFile(String serverName, String port, String username, String password, String path, String localPath, String localBackupPath) {
        boolean success = false;
        // 得到文件名称
        if (localPath == null || "".equals(localPath)) {
            return success;
        }
        File f = new File(localPath);
        FTPClient ftpClient = null;
        try {
            ftpClient = connectServer(serverName, Integer.parseInt(port), username, password, "", Boolean.TRUE);
            try {
                boolean r1 = ftpClient.changeWorkingDirectory(path);
                if (!r1) {
                    createDir(path, ftpClient);
                    boolean t = ftpClient.changeWorkingDirectory(path);
                    log.debug("change Working Directory :" + t);
                }
                log.debug("change Working Directory :" + r1);
            } catch (IOException e1) {
                if (log.isErrorEnabled()) {
                    log.error("nikeFTPUtil sendFile Exception:", e1);
                }
            }
            for (File toFile : f.listFiles()) {
                if (!toFile.isDirectory()) {
                    FileInputStream input = null;
                    try {
                        input = new FileInputStream(toFile);
                        log.debug("ready to send file!");
                        if (ftpClient != null && ftpClient.isConnected()) {
                            boolean tmp = ftpClient.storeFile(toFile.getName(), input);
                            log.debug("send file :" + tmp);
                        }
                        log.debug("send file success");
                        success = true;
                    } catch (IOException e) {
                        log.error("upload error:IOException,cause:{},msg:{}", new Object[] {e.getCause(), e.getMessage()});
                        success = false;
                    } finally {
                        try {
                            if (input != null) {
                                input.close();
                            }
                        } catch (IOException e) {
                            log.error("close iostream IOException,cause:{},msg:{}", new Object[] {e.getCause(), e.getMessage()});
                        }
                    }
                    try {
                        FileUtils.copyFileToDirectory(toFile, new File(localBackupPath), true);
                        toFile.delete();
                    } catch (IOException e) {
                        log.error("", e);
                    }
                }
            }
        } finally {
            if (ftpClient != null) {
                disconnectServer(ftpClient);
            }
        }
        return success;
    }

    /**
     * 下载文件
     * 
     * @param serverName 服务地址
     * @param port 端口
     * @param userName 用户名称
     * @param password 密码
     * @param remotePath --服务器上目录
     * @param localPath --本地文件目录
     * @param isDel --是否删除原文件
     * @return 0：下载成功，1：下载失败 2： 文件正在上传
     */
    public static int readFile(String serverName, String port, String userName, String password, String copySplitStr, String remotePath, String localPath, boolean isDel) {
        int readIsSuccess = 0;
        long remoteSize;
        int readNum = 2;
        if (remotePath == null || "".equals(remotePath)) {
            readIsSuccess = 1;
            return readIsSuccess;
        }
        FTPClient ftpClient = connectServer(serverName, Integer.parseInt(port), userName, password, "", Boolean.TRUE);
        // 首先判断文件是否正在上传
        try {
            FTPFile[] files = ftpClient.listFiles(remotePath);
            for (int j = 0; j < files.length; j++) {
                if (!files[j].isDirectory()) {
                    boolean isRead = true;
                    // 先判断文件名称的合法性 如果不合法就不继续下面的工作
                    if (!files[j].getName().startsWith(copySplitStr)) {
                        isRead = false;
                    } else {
                        isRead = judgeFilesName(files[j].getName());
                        if (isRead) {
                            // 然后判断文件是否完整
                            remoteSize = files[j].getSize();
                            for (int i = 0; i < readNum; i++) {
                                if (remoteSize != files[j].getSize()) {
                                    readIsSuccess = 2; // 不相等文件则正在上传
                                    isRead = false;
                                }
                            }
                        }
                    }
                    // 如果条件匹配就进行文件的下载
                    if (isRead) {
                        // 文件正常则开始下载文件
                        BufferedOutputStream buffOut = null;
                        try {
                            String filename = files[j].getName();
                            // localPath = localPath.lastIndexOf("/") > -1 ? localPath : localPath +
                            // "/";
                            // FileUtil.dynamicCreateDir(filePath);
                            String filePath = localPath + "/" + filename;
                            buffOut = new BufferedOutputStream(new FileOutputStream(filePath));
                            boolean flag = ftpClient.retrieveFile(remotePath + "/" + filename, buffOut);
                            buffOut.close();
                            if (!flag) {
                                readIsSuccess = 1;
                            } else {
                                // deleteFile
                                if (isDel) {
                                    deleteFile(remotePath + "/" + filename, ftpClient);
                                }
                            }
                        } catch (Exception e) {
                            readIsSuccess = 1;
                            log.error("", e);
                            log.error("download error IOException,cause:{},msg:{}", new Object[] {e.getCause(), e.getMessage()});
                        } finally {
                            try {
                                if (buffOut != null) {
                                    buffOut.close();
                                }
                            } catch (Exception e) {
                                log.error("", e);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return readIsSuccess;
    }

    /**
     * 判断文件名称是否符合匹配字符
     * 
     * @param fileName 文件名称
     * @param splitStr 匹配符
     * @return
     * 
     *         private static boolean judgeFilesName(String fileName, String splitStr) { boolean
     *         flag = false; if ("".equals(splitStr) && splitStr == null) { flag = true; return
     *         flag; } else if (fileName.indexOf(splitStr) > -1) { flag = true; return flag; }
     * 
     *         return false; }
     */

    /**
     * 判断文件名称是否符合匹配字符
     * 
     * @param fileName 文件名称
     * @param splitStr 匹配符
     * @return
     */
    private static boolean judgeFilesName(String fileName) {
        boolean flag = true;
        if ("".equals(fileName) && fileName == null) {
            flag = false;
            return flag;
        }

        String[] name = fileName.split("\\.");
        if (name.length == 0) {
            flag = false;
            return flag;
        }
        for (int i = 0; i < name.length; i++) {
            if (name[i] == null || name[i].equals("")) {
                flag = false;
                return flag;
            }
        }

        return flag;
    }

    /**
     * 删除一个文件
     */
    public static boolean deleteFile(String filename, FTPClient ftpClient) {
        boolean flag = true;
        if (ftpClient == null || !ftpClient.isConnected()) {
            return false;
        }
        try {
            flag = ftpClient.deleteFile(new String(filename.getBytes(), ftpClient.getControlEncoding()));
        } catch (IOException e) {
            log.error("", e);
        }
        return flag;
    }

    /**
     * 连接到服务器
     * 
     * @return true 连接服务器成功，false 连接服务器失败
     */
    public static FTPClient connectServer(String serverName, Integer port, String userName, String password, String ENcoding, Boolean isPassiveMode) {
        FTPClient ftpClient = null;
        int reply;
        try {
            ftpClient = new FTPClient();
            if (ENcoding != null && !ENcoding.equals("")) {
                ftpClient.setControlEncoding(ENcoding);
            }
            if (port == null) {
                ftpClient.setDefaultPort(21);
            } else {
                ftpClient.setDefaultPort(port);
            }
            if (serverName == null) {
                log.error("serverName is null");
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            ftpClient.connect(serverName, port);
            if (!"".equals(userName) && !"".equals(password)) {
                ftpClient.login(userName, password);
            }
            reply = ftpClient.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                log.debug("FTP refuse connect！");
            }
            ftpClient.setDataTimeout(FTP_SERVER_TIMEOUT);
            if (isPassiveMode) {
                reply = ftpClient.pasv();
                log.debug("passive reply : " + reply);
            }

        } catch (SocketException e) {
            log.error("", e);
            log.error("ftp error SocketException,cause:{},msg:{}", new Object[] {e.getCause(), e.getMessage()});
        } catch (IOException e) {
            log.error("", e);
            log.error("enter ftp server  " + serverName + " error ");
            log.error("ftp error IOException,cause:{},msg:{}", new Object[] {e.getCause(), e.getMessage()});
        }
        return ftpClient;
    }

    public static void disconnectServer(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                log.error("", e); // To change body of catch statement use
                // File | Settings | File Templates.
                log.error("DISCONNECT ERROR");
            }
        }
    }

    public static Boolean createDir(String dir, FTPClient ftpClient) {
        try {
            boolean flag = ftpClient.changeWorkingDirectory(dir);
            if (!flag) {
                ftpClient.makeDirectory(dir);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean dirExist(String dir, FTPClient ftpClient) {
        boolean flag = false;

        return flag;
    }

    // 递归生成嵌套文件夹
    public static boolean createDirecroty(String remote, FTPClient ftpClient) throws IOException {
        boolean success = true;
        if (remote.indexOf("/") == -1) {
            remote = remote.replace("\\\\", "\\");
            remote = remote.replace("\\", "/");
        }
        if (!remote.endsWith("/")) {
            remote += "/";
        }

        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = remote.indexOf("/", start);
            while (true) {
                String subDirectory = new String(remote.substring(start, end));
                if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                    if (ftpClient.makeDirectory(subDirectory)) {
                        ftpClient.changeWorkingDirectory(subDirectory);
                    } else {
                        log.debug("create dir error");
                        success = false;
                        return success;
                    }
                }
                start = end + 1;
                end = remote.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }

        }
        return success;
    }

    public static List<FTPFile> getEffectFile(FTPClient ftpClient, String remotePath) {
        List<FTPFile> fileList = new ArrayList<FTPFile>();
        try {
            FTPFile[] files = ftpClient.listFiles(remotePath);
            for (FTPFile file : files) {
                if (!file.isDirectory()) {
                    fileList.add(file);
                }
            }
        } catch (IOException e) {
            log.error("", e);
        }

        return fileList;
    }
}
