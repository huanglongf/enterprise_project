package com.jumbo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPUtil {
    protected static final Log log = LogFactory.getLog(SFTPUtil.class);

    public static void uploadLocalPath(String host, String port, String username, String password, String localPath, String remotePath, String localBakPath) {
        int intPort = Integer.parseInt(port);
        ChannelSftp sftp = SFTPUtil.connect(host, intPort, username, password);
        File f = new File(localPath);
        for (File file : f.listFiles()) {
            if (file.isDirectory()) {
                continue;
            }
            boolean flag = SFTPUtil.sendFile(remotePath, file.getAbsolutePath(), sftp);
            if (flag) {
                try {
                    FileUtils.copyFileToDirectory(file, new File(localBakPath));
                    file.delete();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * 连接sftp服务器
     * 
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password
     * 
     * 
     *        密码
     * @return
     */
    public static ChannelSftp connect(String host, int port, String username, String password) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            Session sshSession = jsch.getSession(username, host, port);
            log.debug("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.setTimeout(600000);
            sshSession.connect();
            log.debug("Session connected.");
            log.debug("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            log.debug("Connected to " + host + ".");
        } catch (Exception e) {
            log.error("SFTP connect error!", e);
        }
        return sftp;
    }

    /**
     * 上传文件
     * 
     * @param directory 上传的目录
     * @param uploadFile 要上传的文件
     * @param sftp
     */
    public static void upload(String directory, String uploadFile, ChannelSftp sftp) {
        createDir(directory, sftp);
        File file = new File(uploadFile);
        if (!file.exists()) {
            return;
        }
        FileInputStream fs = null;
        try {
            sftp.cd(directory);
            fs = new FileInputStream(file);
            sftp.put(fs, file.getName());

        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * 下载文件
     * 
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param saveFile 存在本地的路径
     * @param sftp
     */
    public static boolean download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            FileOutputStream os = new FileOutputStream(file);
            sftp.get(downloadFile, os);
            os.close();
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
        return true;
    }

    /**
     * 删除文件
     * 
     * @param directory 要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @param sftp
     */
    public static void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * Disconnect with server
     */
    public static void disconnect(ChannelSftp sftp) {
        if (sftp != null) {
            try {
                sftp.exit();
                sftp.getSession().disconnect();
            } catch (JSchException e) {
                log.error("", e);
            }
            if (sftp.isConnected()) {
                sftp.disconnect();
            } else if (sftp.isClosed()) {
                log.debug("sftp is closed already");
            }
        }

    }

    /**
     * 列出目录下的文件
     * 
     * @param directory 要列出的目录
     * @param sftp
     * @return
     * @throws SftpException
     */
    @SuppressWarnings("unchecked")
    public static Vector<LsEntry> listFiles(String directory, ChannelSftp sftp) throws SftpException {
        return sftp.ls(directory);
    }

    /**
     * Description: 向FTP服务器上传文件
     * 
     * @param path FTP服务器保存目录
     * @param localFileName 上传到FTP服务器上的文件名
     * @return 成功返回true，否则返回false
     * @throws IOException
     */
    public static boolean sendFile(String remotePath, String localFileName, ChannelSftp sftp) {
        // 得到文件名称
        if (localFileName == null || "".equals(localFileName)) {
            return false;
        }
        // 如果路径是\\转化成/
        if (localFileName.indexOf("/") == -1) {
            localFileName = localFileName.replace("\\\\", "\\");
            localFileName = localFileName.replace("\\", "/");
        }
        log.debug("remote path :" + remotePath + ":localFileName:" + localFileName);
        String toFileName = localFileName.substring(localFileName.lastIndexOf("/") == -1 ? 0 : localFileName.lastIndexOf("/") + 1, localFileName.length());
        log.debug("remote path :" + remotePath + ":toFileName:" + toFileName);
        FileInputStream fs = null;
        try {
            createDir(remotePath, sftp);
            File file = new File(localFileName);
            if (!file.exists()) {
                return false;
            }
            fs = new FileInputStream(file);
            sftp.put(fs, file.getName());

        } catch (Exception e) {
            log.error("", e);
            return false;
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return true;
    }

    public static boolean sendFile(String serverName, String port, String username, String password, String path, String localPath, String localBackupPath) {
        boolean success = false;
        // 得到文件名称
        if (localPath == null || "".equals(localPath)) {
            return success;
        }
        File f = new File(localPath);
        ChannelSftp sftp = null;
        try {
            sftp = connect(serverName, Integer.parseInt(port), username, password);
            if (sftp == null) {
                for (int i = 0; i < 20; i++) {
                    sftp = connect(serverName, Integer.parseInt(port), username, password);
                    if (sftp != null) {
                        break;
                    }
                }
            }
            createDir(path, sftp);
            for (File toFile : f.listFiles()) {
                if (!toFile.isDirectory()) {
                    FileInputStream input = null;
                    try {
                        input = new FileInputStream(toFile);
                        log.debug("ready to send file!");
                        try {
                            sftp.put(input, toFile.getName());
                        } catch (SftpException e) {
                            log.error("send file failed");
                            log.error("", e);
                        }
                        log.debug("send file success");
                        success = true;
                    } catch (IOException e) {
                        success = false;
                    } finally {
                        try {
                            if (input != null) {
                                input.close();
                            }
                        } catch (IOException e) {
                            log.error("", e);
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
            disconnect(sftp);
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
     * @param remoteBakPath <br>
     *        如果该参数不为空，则将文件备份到该路径
     * @param isDel --是否删除原文件
     * @return 0：下载成功，1：下载失败 2： 文件正在上传
     */
    public static int readFile(String serverName, String port, String userName, String password, String remotePath, String localPath, String remoteBakPath, boolean isDel) {
        return readFile(serverName, port, userName, password, remotePath, localPath, remoteBakPath, isDel, null);
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
     * @param remoteBakPath <br>
     *        如果该参数不为空，则将文件备份到该路径
     * @param isDel --是否删除原文件
     * @param copySplitStr <br>
     *        只拷贝文件中含有对应字符的文件
     * @return 0：下载成功，1：下载失败 2： 文件正在上传
     */
    public static int readFile(String serverName, String port, String userName, String password, String remotePath, String localPath, String remoteBakPath, boolean isDel, String copySplitStr) {
        int readIsSuccess = 0;
        if (remotePath == null || "".equals(remotePath)) {
            readIsSuccess = 1;
            return readIsSuccess;
        }
        ChannelSftp sftp = connect(serverName, Integer.parseInt(port), userName, password);
        // 首先判断文件是否正在上传
        if (sftp != null) {
            try {
                Vector<LsEntry> directorys = listFiles(remotePath, sftp);
                if (directorys.size() > 0) {
                    for (int k = 0; k < directorys.size(); k++) {
                        LsEntry lsEntry = directorys.get(k);
                        if (null != lsEntry) {
                            boolean isRead = true;
                            // 先判断文件名称的合法性 如果不合法就不继续下面的工作
                            if (StringUtil.isEmpty(copySplitStr)) {
                                isRead = judgeFilesName(lsEntry.getFilename());
                            } else {
                                isRead = judgeFilesName(lsEntry.getFilename(), copySplitStr);
                            }

                            // 如果条件匹配就进行文件的下载
                            if (isRead) {
                                try {
                                    String filename = lsEntry.getFilename();
                                    if (".".equals(filename) || "..".equals(filename)) {
                                        continue;
                                    }
                                    localPath = localPath.lastIndexOf("/") > -1 ? localPath : localPath + "/";
                                    FileUtil.dynamicCreateDir(localPath);
                                    boolean flag = download(remotePath, remotePath + "/" + filename, localPath + File.separator + filename, sftp);
                                    if (!flag) {
                                        log.error("directory:" + remotePath + ",downloadFile:" + remotePath + "/" + filename + ",saveFile:" + (localPath + File.separator + filename));
                                        readIsSuccess = 1;
                                    } else {
                                        // 复制文件
                                        if (!StringUtil.isEmpty(remoteBakPath)) {
                                            upload(remoteBakPath, localPath + "/" + filename, sftp);
                                        }
                                        // deleteFile
                                        if (isDel) {
                                            delete(remotePath, filename, sftp);
                                        }
                                    }
                                } catch (Exception e) {
                                    readIsSuccess = 3;
                                    log.error("", e);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e1) {
                readIsSuccess = 2;
                log.error("", e1);
            } finally {
                disconnect(sftp);
            }
        } else {
            readIsSuccess = 1;
        }
        return readIsSuccess;
    }

    public static int downloadForIT(String remotePath, String localPath, ChannelSftp sftp) {
        int readIsSuccess = 0;
        if (remotePath == null || "".equals(remotePath)) {
            readIsSuccess = 5;
            return readIsSuccess;
        }
        // 首先判断文件是否正在上传
        if (sftp != null) {
            try {
                Vector<LsEntry> directorys = listFiles(remotePath, sftp);
                if (directorys.size() > 0) {
                    for (int k = 0; k < directorys.size(); k++) {
                        LsEntry lsEntry = directorys.get(k);
                        if (null != lsEntry) {
                            // 先判断文件名称的合法性 如果不合法就不继续下面的工作
                            boolean isRead = judgeFilesName(lsEntry.getFilename());
                            // 如果条件匹配就进行文件的下载
                            if (isRead) {
                                try {
                                    String filename = lsEntry.getFilename();
                                    if (".".equals(filename) || "..".equals(filename)) {
                                        continue;
                                    }
                                    localPath = localPath.lastIndexOf("/") > -1 ? localPath : localPath + "/";
                                    FileUtil.dynamicCreateDir(localPath);
                                    boolean flag = download(remotePath, remotePath + "/" + filename, localPath + File.separator + filename, sftp);
                                    if (!flag) {
                                        log.error("directory:" + remotePath + ",downloadFile:" + remotePath + "/" + filename + ",saveFile:" + (localPath + File.separator + filename));
                                        readIsSuccess = 1;
                                    }
                                } catch (Exception e) {
                                    readIsSuccess = 3;
                                    log.error("", e);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e1) {
                readIsSuccess = 2;
                log.error("", e1);
            }
        } else {
            readIsSuccess = 4;
        }
        return readIsSuccess;
    }

    /**
     * create Directory
     * 
     * @param filepath
     * @param sftp
     */
    private static void createDir(String filepath, ChannelSftp sftp) {
        boolean bcreated = false;
        boolean bparent = false;
        File file = new File(filepath);
        String ppath = file.getParent().replace("\\", "//");
        try {
            sftp.cd(ppath);
            bparent = true;
        } catch (SftpException e1) {
            bparent = false;
        }
        try {
            if (bparent) {
                try {
                    sftp.cd(filepath);
                    bcreated = true;
                } catch (Exception e) {
                    bcreated = false;
                }
                if (!bcreated) {
                    sftp.mkdir(filepath);
                    bcreated = true;
                }
                return;
            } else {
                createDir(ppath, sftp);
                sftp.cd(ppath);
                sftp.mkdir(filepath);
            }
        } catch (SftpException e) {
            // System.out.println("mkdir failed :" + filepath);
            log.error("", e);
        }

        try {
            sftp.cd(filepath);
        } catch (SftpException e) {
            log.error("", e);
            // System.out.println("can not cd into :" + filepath);
        }

    }

    /**
     * 判断文件名称是否符合匹配字符
     * 
     * @param fileName 文件名称
     * @param splitStr 匹配符
     * @return
     */
    public static boolean judgeFilesName(String fileName, String splitStr) {
        boolean flag = false;
        if (fileName.indexOf(splitStr) > -1) {
            flag = true;
            return flag;
        }
        Pattern pattern = Pattern.compile(splitStr);
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.matches()) {
            flag = true;
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
     * 判断文件名称是否符合匹配字符
     * 
     * @param fileName 文件名称
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

    public static void main(String[] args) {
        ChannelSftp sftp = SFTPUtil.connect("10.88.13.220", 22, "crmuser", "root1234");
        System.out.println("连接成功！！！");
        SFTPUtil.disconnect(sftp);
    }
}
