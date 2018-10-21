package com.jumbo.wms.daemon;

import java.util.List;
import com.jumbo.wms.model.vmi.cathKidstonData.CKReceiveConfrimCommand;
import com.jumbo.wms.model.vmi.cathKidstonData.CKTransferOutCommand;

public interface CathKidstonTask {

	/**
	 * 下载收货指令文件
	 */
	void downloadCkReveiceFile();

	/**
	 * 上传收货反馈指令
	 */
	void uploadCkReveiceFile();

	/**
	 * 上传退仓反馈指令
	 */
	void uploadCkTransferOutFile();

	List<CKReceiveConfrimCommand> saveReceivingFile(String locationUploadPath);

	List<CKTransferOutCommand> saveTransferOutFile(String locationUploadPath);

	/**
	 * 获取收货指令中间表数据创建STA
	 */
	void createStaForCathKidston();

	void downloadDispatchInfo();

}
