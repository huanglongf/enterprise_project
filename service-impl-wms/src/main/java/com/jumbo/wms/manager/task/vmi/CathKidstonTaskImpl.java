package com.jumbo.wms.manager.task.vmi;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.util.FileUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.daemon.CathKidstonTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.vmi.CathKidstonData.CathKidstonManager;
import com.jumbo.wms.model.vmi.cathKidstonData.CKReceive;
import com.jumbo.wms.model.vmi.cathKidstonData.CKReceiveConfrimCommand;
import com.jumbo.wms.model.vmi.cathKidstonData.CKTransferOutCommand;
import com.jumbo.wms.model.vmi.cathKidstonData.CathKidstonStatus;

public class CathKidstonTaskImpl extends BaseManagerImpl implements CathKidstonTask {

	private static final long serialVersionUID = -3261019221672360144L;

	@Autowired
	private CommonConfigManager configManager;
	@Autowired
	private CathKidstonManager cathKidstonManager;

	/**
	 * 下载收货指令文件
	 */
	@SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
	public void downloadCkReveiceFile() {
        log.debug("======================CathKidstonTask downloadCkReveiceFile begin!");
		// 下载ORDER下所有文件
		Map<String, String> config = configManager.getCommonFTPConfig(Constants.CATHKIDSTON_GROUP);
		String localDownloadPath = config.get(Constants.CATHKIDSTON_FTP_DOWN_LOCALPATH);// 下载后保存到本地的路径
		String localDownloadBackupPath = config.get(Constants.CATHKIDSTON_FTP_DOWN_LOCALPATH_BK);// 下载后保存到本地的备份路径
		String remoteDownloadOrderPath = config.get(Constants.CATHKIDSTON_FTP_DOWNPATH);// FTP服务器上的下载相对路径
		try {
			SFTPUtil.readFile(config.get(Constants.CATHKIDSTON_FTP_URL), config.get(Constants.CATHKIDSTON_FTP_PORT), config.get(Constants.CATHKIDSTON_FTP_USERNAME), config.get(Constants.CATHKIDSTON_FTP_PASSWORD), remoteDownloadOrderPath,
					localDownloadPath, null, true, config.get(Constants.CATHKIDSTON_RD_FTP_DOWNLOAD_FILE_START_STR));
		} catch (Exception e) {
			log.error("", e);
			return;
		}
		// 读取所有order至数据库
		saveCkReveiceToTable(localDownloadPath, localDownloadBackupPath);
        log.debug("======================CathKidstonTask downloadCkReveiceFile end!");
	}

	/**
	 * 上传收货反馈指令
	 */
	@SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
	public void uploadCkReveiceFile() {
        log.debug("======================CathKidstonTask uploadCkReveiceFile begin!");
		// 上传文件
		Map<String, String> config = configManager.getCommonFTPConfig(Constants.CATHKIDSTON_GROUP);
		String locationUploadPath = config.get(Constants.CATHKIDSTON_FTP_UP_LOCALPATH);
		// 上传文件
		String remoteUpPath = config.get(Constants.CATHKIDSTON_FTP_UPPATH);
		String locationUploadBackupPath = config.get(Constants.CATHKIDSTON_FTP_UP_LOCALPATH_BK);
		String remoteUpPathReceving = remoteUpPath;// FTP服务器上的上传相对路径
		String locationUploadPathReceiving = locationUploadPath + "/deliverynote";// 上传后保存到本地的路径
		String locationUploadBackupPathReceving = locationUploadBackupPath + "/deliverynote/backup";// 上传后保存到本地的备份路径
		List<CKReceiveConfrimCommand> ckList = saveReceivingFile(locationUploadPathReceiving);
		if (ckList != null) {
			try {
                log.debug("CathKidstonTask uploadCkReveiceFile file : send file to sftp");
				SFTPUtil.sendFile(config.get(Constants.CATHKIDSTON_FTP_URL), config.get(Constants.CATHKIDSTON_FTP_PORT), config.get(Constants.CATHKIDSTON_FTP_USERNAME), config.get(Constants.CATHKIDSTON_FTP_PASSWORD), remoteUpPathReceving,
						locationUploadPathReceiving, locationUploadBackupPathReceving);
                log.debug("CathKidstonTask uploadCkReveiceFile file : send file to sftp success");
			} catch (Exception e) {
				log.error("", e);
				cathKidstonManager.updateCKReceiveConfrimStatus(ckList, CathKidstonStatus.FAILED);
				return;
			}
			// 上传成功修改对应状态
			cathKidstonManager.updateCKReceiveConfrimStatus(ckList, CathKidstonStatus.FINISHED);
		}
        log.debug("======================CathKidstonTask uploadCkReveiceFile end!");
	}

	/**
	 * 上传退仓反馈指令
	 */
	@SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
	public void uploadCkTransferOutFile() {
        log.debug("======================CathKidstonTask uploadCkTransferOutFile begin!");
		// 上传文件
		Map<String, String> config = configManager.getCommonFTPConfig(Constants.CATHKIDSTON_GROUP);
		String locationUploadPath = config.get(Constants.CATHKIDSTON_FTP_UP_LOCALPATH);
		// 上传文件
		String remoteUpPath = config.get(Constants.CATHKIDSTON_FTP_UPPATH);
		String locationUploadBackupPath = config.get(Constants.CATHKIDSTON_FTP_UP_LOCALPATH_BK);
		String remoteUpPathReceving = remoteUpPath;// FTP服务器上的上传相对路径
		String locationUploadPathReceiving = locationUploadPath + "/transferout";// 上传后保存到本地的路径
		String locationUploadBackupPathReceving = locationUploadBackupPath + "/transferout/backup";// 上传后保存到本地的备份路径
		List<CKTransferOutCommand> ckList = saveTransferOutFile(locationUploadPathReceiving);
		if (ckList != null) {
			try {
                log.debug("CathKidstonTask uploadCkTransferOutFile file : send file to sftp");
				SFTPUtil.sendFile(config.get(Constants.CATHKIDSTON_FTP_URL), config.get(Constants.CATHKIDSTON_FTP_PORT), config.get(Constants.CATHKIDSTON_FTP_USERNAME), config.get(Constants.CATHKIDSTON_FTP_PASSWORD), remoteUpPathReceving,
						locationUploadPathReceiving, locationUploadBackupPathReceving);
                log.debug("CathKidstonTask uploadCkTransferOutFile file : send file to sftp success");
			} catch (Exception e) {
				log.error("", e);
				// 上传失败修改对应状态
				cathKidstonManager.updateTransferOutStatus(ckList, CathKidstonStatus.FAILED);
				return;
			}
			// 上传成功后修改对应状态
			cathKidstonManager.updateTransferOutStatus(ckList, CathKidstonStatus.FINISHED);
		}
        log.debug("======================CathKidstonTask uploadCkTransferOutFile end!");
	}

	public List<CKReceiveConfrimCommand> saveReceivingFile(String locationUploadPath) {
		List<CKReceiveConfrimCommand> ckList = null;
		try {
			ckList = cathKidstonManager.writeReceivingDataToFile(locationUploadPath);
			return ckList;
		} catch (Exception e) {
			log.error("", e);
			return null;
		}
	}

	public List<CKTransferOutCommand> saveTransferOutFile(String locationUploadPath) {
		List<CKTransferOutCommand> ctList = null;
		try {
			ctList = cathKidstonManager.writeTransferOutDataToFile(locationUploadPath);
			return ctList;
		} catch (Exception e) {
			log.error("", e);
			return null;
		}
	}

	/**
	 * 获取收货指令中间表数据创建STA
	 */
	@SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
	public void createStaForCathKidston() {
        log.debug("======================CathKidstonTask createStaForCathKidston begin!");
		cathKidstonManager.createStaForCathKidston(CKReceive.vmiCode);
        log.debug("======================CathKidstonTask createStaForCathKidston eng!");
	}

	public void downloadDispatchInfo() {
		String localDownloadPath = "D:/ck";
		String localDownloadBackupPath = "D:/ck";
		System.out.println("start download");
		saveCkReveiceToTable(localDownloadPath, localDownloadBackupPath);
	}

	private void saveCkReveiceToTable(String localPath, String localBackupPath) {
		File[] localInFileList = FileUtil.getDirFile(localPath);
		if (localInFileList == null) return;
		for (File file : localInFileList) {
			if (file.length() > 0) {
				if (!file.isDirectory()) {
					try {
						cathKidstonManager.saveCKreceive(file, localBackupPath);
					} catch (Exception e) {
						log.error("", e);
					}
				}
			}
		}
	}
}
