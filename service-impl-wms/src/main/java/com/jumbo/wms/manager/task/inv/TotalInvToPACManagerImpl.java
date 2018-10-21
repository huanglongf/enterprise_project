package com.jumbo.wms.manager.task.inv;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpException;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.SystemLogDao;
import com.jumbo.dao.warehouse.WholeInventorySyncToPACDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.MailUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.WholeInventorySyncToPAC;
import com.jumbo.wms.model.warehouse.WholeInventorySyncToPACCommand;

/**
 * 
 * @author PUCK SHEN
 * 
 */
@Service("totalInvToPACManager")
public class TotalInvToPACManagerImpl extends BaseManagerImpl implements TotalInvToPACManager {

    /**
     * 
     */
    private static final long serialVersionUID = -887826777144345946L;
    private static String TOTAL_INVENTORY_ = "TOTAL_INVENTORY_";
    private static String blank = "	";
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private WholeInventorySyncToPACDao wholeInventorySyncToPACDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private SystemLogDao systemLogDao;
    @Autowired
    private TaskEbsManager taskEbsManager;

    private Date synDate; // 同步时间
    private Long lineCount; // 总行数
    private Long qtyCount; // 商品总数

    /**
     * 备份历史文件
     */
    private void backupFile(boolean isAll) {
        log.debug("TotalInventoryToPAC...............start");
        // 读取库存同步配置信息
        Map<String, String> cfg = configManager.getPACFTPConfig();
        // WMS ZIP文件生成目录
        String localDir = cfg.get(Constants.VMI_FTP_UP_ZIP_LOCALPATH);
        // 读取生成目录下所有文件，剪切到备份目录。
        File f = new File(localDir);
        if (!f.exists()) {
            f.mkdirs();
        }
        for (File toFile : f.listFiles()) {
            try {
                if (isAll) { // 备份生成目录下所有ZIP文件、库存文件
                    if (!toFile.isDirectory()) { //
                        FileUtils.copyFileToDirectory(toFile, new File(cfg.get(Constants.VMI_FTP_UP_ZIP_LOCALPATH_BACKUP)), true);
                        toFile.delete();
                    }

                } else {
                    // 只备份库存文件
                    if (!toFile.isDirectory()) { //
                        if (!(toFile.getName().substring(toFile.getName().length() - 4, toFile.getName().length())).equals(".zip")) {
                            FileUtils.copyFileToDirectory(toFile, new File(cfg.get(Constants.VMI_FTP_UP_ZIP_LOCALPATH_BACKUP)), true);
                            toFile.delete();
                        }
                    }
                }

            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error("totalInvToPACManager backupFile exception", e);
                }
                // log.error("", e);
            }
        }
    }

    /**
     * 全量库存同步
     * 
     * @param da
     * @return
     */
    public boolean totalInventory() {
        log.debug("totalInventory begin..");
        Date da = new Date();
        try {
            wholeInventorySyncToPACDao.totalInventorySynToPAC(da);// 全量库存同步至PAC
            log.debug("synInv Success.........!");
            systemLogDao.insertSystemLog("fullInventoryToPAC", "inventoryCalculations", "Success"); // (全量库存计算成功)
            // 查询时间点的库存数据，记录总行数和商品总数
            WholeInventorySyncToPACCommand obj = wholeInventorySyncToPACDao.findSyncInfo(da, new BeanPropertyRowMapper<WholeInventorySyncToPACCommand>(WholeInventorySyncToPACCommand.class));
            this.setSynDate(da);
            if (obj != null) {
                this.setLineCount(obj.getLineCount());
                this.setQtyCount(obj.getQtyCount());
            }
            return true;
        } catch (Exception e) {
            log.error("synInv Failed.........!");
            systemLogDao.insertSystemLog("fullInventoryToPAC", "inventoryCalculations", "Failed"); // (全量库存计算失败)
            // 补偿再次计算，当尝试5次均失败时结束当日全量库存，如果重新尝试需要基于新的时间戳计算
            log.error("synInv CM Execute.........!");
            systemLogDao.insertSystemLog("fullInventoryToPAC", "inventoryCalculationsCM", "Execute"); // (全量库存文件生成补偿执行)
            for (int i = 0; i < 5; i++) {
                Date das = new Date();
                try {
                    wholeInventorySyncToPACDao.totalInventorySynToPAC(das); // 补偿再次计算
                    log.error("synInv Success.........!");
                    systemLogDao.insertSystemLog("fullInventoryToPAC", "inventoryCalculationsCM", "Success"); // (全量库存补偿计算成功)
                    // 查询时间点的库存数据，记录总行数和商品总数
                    WholeInventorySyncToPACCommand obj = wholeInventorySyncToPACDao.findSyncInfo(da, new BeanPropertyRowMapper<WholeInventorySyncToPACCommand>(WholeInventorySyncToPACCommand.class));
                    this.setSynDate(das);
                    if (obj != null) {
                        this.setLineCount(obj.getLineCount());
                        this.setQtyCount(obj.getQtyCount());
                    }
                    return true;
                } catch (Exception e2) {
                    systemLogDao.insertSystemLog("fullInventoryToPAC", "inventoryCalculationsCM", "Failed"); // (全量库存补偿计算失败)
                }
            }
            emailNotice("SYN_LOSE_NOTICE_MAN");// 当日全量库存计算5次均失败时，邮件通知，引入人工处理流程。
            log.error("totalInventory end....!"); // 结束当日全量库存
            return false;
        }
    }

    /**
     * 生成库存文件ZIP包
     */
    public boolean createInvFile() {
        // 1.查询待同步数据中最新的时间戳的所有库存记录 （ 记录日志，失败5次邮件通知）
        List<WholeInventorySyncToPAC> deas = queryInvByDate();
        if (deas == null || deas.isEmpty()) {
            log.debug("totalInv is null.................app exit ");
            return false;
        } else {
            // 2.基于结果集生成库存文件 将结果集备份至日志表,删除结果集
            if (createInvFile(deas) && copyDataToLog()) {
                return true;
            } else {
                log.error("createInvFile or copyDataToLog Failed.................app exit ");
                return false;
            }
        }
    }

    /**
     * 将结果集备份至日志表，删除结果集
     */
    private boolean copyDataToLog() {
        try {
            taskEbsManager.copyInvDataToPACLog(); // 将结果集备份至日志表，删除结果集
            systemLogDao.insertSystemLog("fullInventoryToPAC", "dataArchive", "Success"); // (全量库存文件日志备份成功)
            return true;
        } catch (Exception e) {
            systemLogDao.insertSystemLog("fullInventoryToPAC", "dataArchive", "Failed"); // (全量库存文件日志备份失败)
            emailNotice("COPY_LOG_NOTICE_MAN"); // 邮件通知，引入人工处理流程
            return false;
        }

    }

    /**
     * 查询待同步数据中最新的时间戳的所有库存记录
     */
    private List<WholeInventorySyncToPAC> queryInvByDate() {
        // 查询待同步数据中最新的时间戳的所有库存记录,根据仓库分组
        List<WholeInventorySyncToPAC> deas = new ArrayList<WholeInventorySyncToPAC>();
        try {
            List<String> channelCode = wholeInventorySyncToPACDao.queryTotalInventoryChannelCode(this.getSynDate(), new SingleColumnRowMapper<String>(String.class));
            for (int i = 0; i < channelCode.size(); i++) {
                List<WholeInventorySyncToPAC> dea = wholeInventorySyncToPACDao.queryTotalInventory(this.getSynDate(), channelCode.get(i), new BeanPropertyRowMapperExt<WholeInventorySyncToPAC>(WholeInventorySyncToPAC.class));
                deas.addAll(dea);
            }
            systemLogDao.insertSystemLog("fullInventoryToPAC", "InventoryQuery", "Success"); // (全量库存待同步数据查询成功)
            return deas;
        } catch (Exception e) {
            systemLogDao.insertSystemLog("fullInventoryToPAC", "InventoryQuery", "Failed"); // (全量库存待同步数据查询失败)
            emailNotice("INV_QUERY_FAILE_NOTICE_MAN"); // 邮件通知，引入人工处理流程
            return null;
        }
    }

    /**
     * 基于结果集生成库存文件
     */
    private boolean createInvFile(List<WholeInventorySyncToPAC> deas) {
        Map<String, String> cfg = configManager.getPACFTPConfig(); // 查询配置信息
        String localDir = cfg.get(Constants.VMI_FTP_UP_ZIP_PATH);
        try {
            batchWriteDataFileForInvSync(deas, localDir); // 生成文件
            systemLogDao.insertSystemLog("fullInventoryToPAC", "fileGeneration", "Success"); // (全量库存文件生成成功)
            return true;
        } catch (Exception e) {
            log.error("create invFile Failed .........!");
            systemLogDao.insertSystemLog("fullInventoryToPAC", "fileGeneration", "Failed"); // (全量库存文件生成失败)
            systemLogDao.insertSystemLog("fullInventoryToPAC", "fileGenerationCm", "Execute"); // (全量库存文件生成补偿执行)
            for (int i = 0; i < 5; i++) {
                try {
                    deleteFile();// 删除之前生成的所有库存文件
                    batchWriteDataFileForInvSync(deas, localDir);
                    log.error("create invFile again Success .........!");
                    systemLogDao.insertSystemLog("fullInventoryToPAC", "fileGenerationCm", "Success"); // (全量库存文件生成补偿成功)
                    return true;
                } catch (Exception e2) {
                    log.error("create invFile again Failed .........!");
                    systemLogDao.insertSystemLog("fullInventoryToPAC", "fileGenerationCm", "Failed"); // (全量库存文件生成补偿失败)
                    log.error("", e);
                }
            }
            emailNotice("INV_CREATE_FAILED_NOTICE_MAN"); // 5次均失败邮件通知，引入人工处理流程
        }
        return false;
    }

    /**
     * 入库反馈写文件 - 批量将数据写入到本地文件
     * 
     * @throws IOException
     */
    private void batchWriteDataFileForInvSync(List<WholeInventorySyncToPAC> dea, String localDir) throws IOException {
        log.debug("batchWriteDataFileForInvSync begin....");
        String fileName = TOTAL_INVENTORY_ + String.valueOf(FormatUtil.formatDate(this.getSynDate(), "yyyyMMdd_HHmmss")); // 定义文件名
        BufferedWriter br = null;
        File f = new File(localDir);
        if (!f.exists()) {
            f.mkdirs();
        }
        try {
            br = new BufferedWriter(new FileWriter(new File(localDir + "/" + fileName)));
        } catch (IOException e1) {
            if (log.isErrorEnabled()) {
                log.error("batchWriteDataFileForInvSync InterruptedException:" + localDir, e1);
            }
        }
        int index = 0;
        String val = null;
        try {
            for (WholeInventorySyncToPAC invSync : dea) {
                val = buildDataForInvSync(invSync);
                br.write(val);
                br.newLine();
                index++;
            }
        } catch (IOException e) {
            log.error(" write file  failed, line:" + index + ";val:" + val);
            log.error("", e);
            throw e;
        } finally {
            try {
                br.flush();
                br.close();
            } catch (IOException e) {
                log.error("", e);
                throw e;
            }
        }
        log.debug("batchWriteDataFileForInvSync end..");
    }

    /**
     * 数据写入dat文件
     * 
     * @param invSync
     * @return
     */
    private String buildDataForInvSync(WholeInventorySyncToPAC invSync) {
        StringBuilder line = new StringBuilder();
        line.append(FormatUtil.formatDate(invSync.getCreateTime(), "yyyyMMddHHmmss"));
        line.append(blank);
        line.append(StringUtils.hasLength(invSync.getSkuCode()) ? invSync.getSkuCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(invSync.getQty().toString()) ? invSync.getQty() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(invSync.getChannelCode()) ? invSync.getChannelCode() : "");
        line.append(blank);
        line.append(StringUtils.hasLength(invSync.getMarketAbility().toString()) ? invSync.getMarketAbility() : "");
        line.append(blank);
        return line.toString();
    }

    /**
     * 删除之前失败生成的所有库存文件
     */
    private void deleteFile() throws Exception {
        log.debug("deleteFile...............start");
        // 读取库存同步配置信息
        Map<String, String> cfg = configManager.getPACFTPConfig();
        // WMS ZIP文件生成目录
        String localDir = cfg.get(Constants.VMI_FTP_UP_ZIP_LOCALPATH);
        // 读取生成目录下所有文件，剪切到备份目录。
        File f = new File(localDir);
        if (f.listFiles() != null) {
            for (File toFile : f.listFiles()) {
                try {
                    toFile.delete();
                } catch (Exception e) {
                    log.error("", e);
                    throw e;
                }
            }
        }

    }

    /**
     * 删除之前失败生成的zip文件
     */
    private void deleteZipFile() {
        log.debug("delete Zip File...............start");
        // 读取库存同步配置信息
        Map<String, String> cfg = configManager.getPACFTPConfig();
        // WMS ZIP文件生成目录
        String localDir = cfg.get(Constants.VMI_FTP_UP_ZIP_LOCALPATH);
        // 读取生成目录下所有文件，剪切到备份目录。
        File f = new File(localDir);
        for (File toFile : f.listFiles()) {
            try {
                if ((toFile.getName().substring(toFile.getName().length() - 4, toFile.getName().length())).equals(".zip")) {
                    toFile.delete();
                }
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("totalInvToPACManager deleteZipFile exception", e);
                }
            }
        }
    }

    /**
     * 备份库存文件
     */
    public void copyFile(boolean isAll) {
        try {
            backupFile(isAll); // 备份文件
            systemLogDao.insertSystemLog("fullInventoryToPAC", "fileArchive", "Success"); // 记录系统日志(全量库存文件备份成功)
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("totalInvToPACManager copyFile exception", e);
            }
            log.error("copy File Failure.........");
            systemLogDao.insertSystemLog("fullInventoryToPAC", "fileArchive", "Failed"); // (全量库存文件备份失败)
            emailNotice("COPY_LOSE_NOTICE_MAN"); // 邮件通知引入人工处理流程，后续任务正常执行
        }
    }

    /**
     * 邮件通知方法
     */
    public void emailNotice(String code) {
        // 查询常量表 邮件推送信息
        ChooseOption option = chooseOptionDao.findByCategoryCode("INV_SYNC_NOTICE_MAN");
        // 查询邮件模板
        MailTemplate template = mailTemplateDao.findTemplateByCode(code);
        // 查询系统常量表 收件人
        if (option != null && !StringUtil.isEmpty(option.getOptionValue())) {
            if (template != null) {
                String mailBody = template.getMailBody();// 邮件内容
                String subject = template.getSubject();// 标题
                String addressee = option.getOptionValue(); // 查询收件人
                boolean bool = false;
                bool = MailUtil.sendMail(subject, addressee, "", mailBody, false, null);
                if (bool) {
                    log.debug("邮件通知成功！");
                } else {
                    log.debug("邮件通知失败,请联系系统管理员！");
                }
            } else {
                log.debug("邮件模板不存在或被禁用");
            }
        } else {
            log.debug("邮件通知失败,收件人为空！");
        }
    }

    /**
     * 库存文件压缩ZIP包及校验
     * 
     * @param zipFolder 压缩目录的文件夹
     * @throws Exception
     */
    public boolean createZipAndCheck() {
        String zipFileName = "";
        try {
            // 压缩库存文件生成zip包
            zipFileName = zip();
            log.debug("Zip File Success.......!");
            systemLogDao.insertSystemLog("fullInventoryToPAC", "zipFile", "Success"); // (全量库存文件压缩成功)
            // 读取zip包校验库存文件
            readZipFile(zipFileName);
            log.debug("Zip File Check Success.......!");
            systemLogDao.insertSystemLog("fullInventoryToPAC", "zipFileCheck", "Success"); // (全量库存文件压缩校验成功)
            return true;
        } catch (Exception e) {
            if (zipFileName.equals("")) {
                log.error("Zip File Failed.......!");
                systemLogDao.insertSystemLog("fullInventoryToPAC", "zipFile", "Failed"); // (全量库存文件压缩失败)
            } else {
                log.error("Zip File Check Failed.......!");
                systemLogDao.insertSystemLog("fullInventoryToPAC", "zipFileCheck", "Failed"); // (全量库存文件压缩校验失败)
            }
            log.error("Zip File  CM Execute.......!");
            systemLogDao.insertSystemLog("fullInventoryToPAC", "zipFileCM", "Execute"); // (全量库存文件压缩补偿执行)
            for (int i = 0; i < 5; i++) {
                try {
                    deleteZipFile(); // 删除失败压缩文件
                    // 压缩库存文件生成zip包
                    zipFileName = zip(); //
                    // 读取zip包校验库存文件
                    readZipFile(zipFileName);
                    log.error("Zip File  CM Success.......!");
                    systemLogDao.insertSystemLog("fullInventoryToPAC", "zipFileCM", "Success"); // (全量库存文件压缩补偿成功)
                    return true;
                } catch (Exception e2) {
                    log.error("Zip File  CM Failed.......!");
                    systemLogDao.insertSystemLog("fullInventoryToPAC", "zipFileCM", "Failed"); // (全量库存文件压缩补偿失败)
                }
            }
            emailNotice("ZIP_FAILED_NOTICE_MAN"); // 5次均失败邮件通知，引入人工处理流程
            return false;
        }
    }

    /**
     * 解压库存文件
     * 
     * @throws Exception
     */
    private String zip() throws Exception {
        log.debug("zip begin...");
        DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
        DateFormat df2 = new SimpleDateFormat("HHmmss");
        Date d = this.getSynDate();
        String data1 = df1.format(d); // 年月日
        String data2 = df2.format(d); // 时分秒
        String zipFileName = "TOTAL_INVENTORY_" + data1 + "_" + data2; // 解压后生成的文件名
        zipFileName += "_" + this.getLineCount() + "_" + this.getQtyCount();
        log.debug("ZIP name : " + zipFileName + "..........!");
        Map<String, String> cfg = configManager.getPACFTPConfig();
        String localDir = cfg.get(Constants.VMI_FTP_UP_ZIP_PATH); // 要解压的目录
        zip(zipFileName, localDir);
        // 文件格式：解压后目录/文件固定说明_年月日_时分秒_文件总行数_文件库存总计.zip
        log.debug("zip end...");
        return localDir + "/" + zipFileName + ".zip";
    }

    /**
     * 
     * @param zipFileName 解压后文件名
     * @param inputFile 要解压的文件夹目录
     * @throws Exception
     */
    private void zip(String zipFileName, String localDir) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(localDir + "/" + zipFileName + ".zip"));
        File inputFile = new File(localDir);
        if (!inputFile.exists()) {
            inputFile.mkdirs();
        }
        zip(out, inputFile, "");
        out.close();
    }

    /**
     * 生成Zip
     * 
     * @param out
     * @param f
     * @param base
     * @throws Exception
     */
    private void zip(ZipOutputStream out, File f, String base) throws Exception {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            for (int i = 0; i < fl.length; i++) {
                if (!fl[i].isDirectory() && !(fl[i].getName().substring(fl[i].getName().length() - 4, fl[i].getName().length())).equals(".zip")) {
                    zip(out, fl[i], base + fl[i].getName()); // 递归调用
                }
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            FileInputStream in = new FileInputStream(f);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
        }
    }

    /**
     * 读取zip文件
     * 
     * @param file zip文件目录及文件名
     * @throws Exception
     */
    public void readZipFile(String file) throws Exception {
        ZipFile zf = new ZipFile(file);
        // System.out.println(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        ZipInputStream zin = new ZipInputStream(in);
        ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            log.debug(("file name: " + ze.getName()));
            BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(ze)));
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;
                if (count < 10) { // 读取前10行，判断文件是否可读
                    log.debug("line:" + line + "........");
                }
            }
            br.close();
        }
        zin.closeEntry();
        try {
            zin.close();
        } catch (Exception e) {
            log.error("", e);
        }

    }

    /**
     * 删除ftp上同名文件
     */
    public boolean deleteSameFile() {
        String fileName = getFileName(); // zip包文件名
        // 没有生成库存文件。 结束当然全量库存同步
        if (fileName.equals("")) {
            log.debug("Zip File Failed.......!");
            systemLogDao.insertSystemLog("fullInventoryToPAC", "zipFile", "Failed"); // (全量库存文件压缩失败)
            log.debug("totalInv end.........!");
            return false;
        }
        if (deleteFile(fileName)) {
            return true;
        } else {
            log.debug("SFTP File Delete Faile.......!");
            systemLogDao.insertSystemLog("fullInventoryToPAC", "sftpFileDelete", "Failed"); // (FTP文件删除失败)
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(120000); // 延迟两分钟
                } catch (InterruptedException e) {
                    log.error("SFTP File Delete CM Failed.......!");
                    systemLogDao.insertSystemLog("fullInventoryToPAC", "sftpFileDeleteCM", "Failed");// (FTP文件删除补偿执行失败)
                }
                log.debug("SFTP File Delete CM Execute.......!");
                systemLogDao.insertSystemLog("fullInventoryToPAC", "sftpFileDeleteCM", "Execute"); // (FTP文件删除补偿执行)
                if (deleteFile(fileName)) {
                    log.debug("SFTP File Delete CM Success.......!");
                    systemLogDao.insertSystemLog("fullInventoryToPAC", "sftpFileDeleteCM", "Success");// (FTP文件删除补偿执行成功)
                    return true;
                } else {
                    log.debug("SFTP File Delete CM Failed.......!");
                    systemLogDao.insertSystemLog("fullInventoryToPAC", "sftpFileDeleteCM", "Failed");// (FTP文件删除补偿执行失败)
                }
            }
        }
        log.debug("totalInv end.........!");
        emailNotice("ZIP_DELETE_FAILE_NOTICE_MAN"); // 5次均失败邮件通知，引入人工处理流程
        return false;
    }

    /**
     * 获取上传目录的zip包文件名
     * 
     * @return
     */
    public String getFileName() {
        String fileName = "";
        // 读取库存同步配置信息
        Map<String, String> cfg = configManager.getPACFTPConfig();
        // WMS ZIP文件生成目录
        String localDir = cfg.get(Constants.VMI_FTP_UP_ZIP_LOCALPATH);
        File f = new File(localDir);
        // 获取生成目录上的文件名。 库存文件备份后，应只剩下一个zip包。
        for (File toFile : f.listFiles()) {
            // 获取zip包文件名
            if ((toFile.getName().substring(toFile.getName().length() - 4, toFile.getName().length())).equals(".zip")) {
                fileName = toFile.getName();
            }
        }
        return fileName;
    }

    /**
     * 上传ftp文件
     */
    public boolean uploadFileToFtp() {
        if (uploadFile()) {
            log.debug("SFTP upLoad  Success.......!");
            systemLogDao.insertSystemLog("fullInventoryToPAC", "sftpUpload", "Success"); // (FTP文件上传成功)
            return true;
        } else {
            log.debug("SFTP upLoad Faile.......!");
            systemLogDao.insertSystemLog("fullInventoryToPAC", "sftpUpload", "Failed"); // (FTP文件上传失败)
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(120000); // 延迟两分钟
                } catch (InterruptedException e) {
                    log.error("SFTP upLoad CM Failed.......!");
                    systemLogDao.insertSystemLog("fullInventoryToPAC", "sftpUploadCM", "Failed");// (FTP文件删除补偿执行失败)
                }
                log.debug("SFTP upLoad CM Execute.......!");
                systemLogDao.insertSystemLog("fullInventoryToPAC", "sftpUploadCM", "Execute"); // (FTP文件删除补偿执行)
                if (uploadFile()) {
                    log.debug("SFTP upLoad  CM Success.......!");
                    systemLogDao.insertSystemLog("fullInventoryToPAC", "sftpUploadCM", "Success");// (FTP文件删除补偿执行成功)
                    return true;
                } else {
                    log.debug("SFTP upLoad CM Failed.......!");
                    systemLogDao.insertSystemLog("fullInventoryToPAC", "sftpUploadCM", "Failed");// (FTP文件删除补偿执行失败)
                }

            }
        }
        log.debug("totalInv end.........!");
        emailNotice("ZIP_UPLOAD_FAILE_NOTICE_MAN"); // 5次均失败邮件通知，引入人工处理流程
        return false;
    }

    /**
     * 上传文件
     * 
     * @return
     */
    private boolean uploadFile() {
        // 获取zip包文件名
        String fileName = getFileName();
        if (fileName.equals("")) {
            return false;
        }
        // 读取库存同步配置信息
        Map<String, String> cfg = configManager.getPACFTPConfig();
        // 连接sftp服务器
        ChannelSftp sftp = SFTPUtil.connect(cfg.get(Constants.VMI_FTP_URL), Integer.parseInt(cfg.get(Constants.VMI_FTP_PORT)), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD));
        if (sftp == null) {
            return false;
        }
        try {
            boolean isSucess = SFTPUtil.sendFile(cfg.get(Constants.VMI_FTP_PATH), cfg.get(Constants.VMI_FTP_UP_ZIP_LOCALPATH) + "/" + fileName, sftp);
            if (isSucess) {
                return true;
            } else {
                return false;
            }
        } finally {
            SFTPUtil.disconnect(sftp);
        }

    }

    /**
     * 删除同名文件
     * 
     * @param fileName
     * @return
     */
    private boolean deleteFile(String fileName) {
        // 读取库存同步配置信息
        Map<String, String> cfg = configManager.getPACFTPConfig();
        String ftpLocal = cfg.get(Constants.VMI_FTP_PATH); // ftp目录
        // 连接sftp服务器
        ChannelSftp sftp = SFTPUtil.connect(cfg.get(Constants.VMI_FTP_URL), Integer.parseInt(cfg.get(Constants.VMI_FTP_PORT)), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD));
        if (sftp == null) {
            return false;
        }
        try {
            Vector<LsEntry> LsEntry = SFTPUtil.listFiles(ftpLocal, sftp); // 获取ftp目录
            for (int i = 0; i < LsEntry.size(); i++) { // 遍历目录文件
                log.debug(LsEntry.get(i).getFilename());
                if (LsEntry.get(i).getFilename().equals(fileName)) { // 如果存在同名文件，则删除
                    if (deleteFtpFile(ftpLocal, fileName, sftp)) {
                        log.debug("SFTP File Delete Success.......!");
                        systemLogDao.insertSystemLog("fullInventoryToPAC", "sftpFileDelete", "Success"); // (FTP文件删除成功)
                        return true;
                    } else {
                        return false;
                    }
                }
            }
            return true;
        } catch (SftpException e) {
            log.error("", e);
        } finally {
            SFTPUtil.disconnect(sftp);
        }
        return false;
    }

    private boolean deleteFtpFile(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
            return true;
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
    }

    public Date getSynDate() {
        return synDate;
    }

    public void setSynDate(Date synDate) {
        this.synDate = synDate;
    }

    public Long getLineCount() {
        return lineCount;
    }

    public void setLineCount(Long lineCount) {
        this.lineCount = lineCount;
    }

    public Long getQtyCount() {
        return qtyCount;
    }

    public void setQtyCount(Long qtyCount) {
        this.qtyCount = qtyCount;
    }

}
