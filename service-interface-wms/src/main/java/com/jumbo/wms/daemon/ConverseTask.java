package com.jumbo.wms.daemon;

import java.io.File;
import java.util.Map;

import com.jumbo.wms.model.vmi.itData.ConverseTaskType;

public interface ConverseTask {

	public void conversSendFile();

	public void startConverseTasks();

	/**
	 * 创入库单
	 */
	public void generateSta();

	/**
	 * 导出收货确认文件
	 */
	public void outputReceiveFile();

	/**
	 * 导出转店退仓反馈
	 */
	public void outputTransferOutFile();

	/**
	 * 导出退仓反馈
	 */
	public void outputRTV();

	/**
	 * 导出调整数据
	 */
	public void outputAdjustmentFile();

	/**
	 * 导出库存状态调整文档
	 */
	public void outputInvStatusChangeFile();

	/**
	 * 导出库存快照
	 */
	public void outputInvSnapshot();

	public void uploadSalesFile();

	/**
	 * 导入入库指令文件
	 */
	public void inputASN();

	/**
	 * 导入入库指令文件
	 */
	public void inputProductInfo();

	/**
	 * 导入基础信息
	 */
	public void inputMastData();

	/**
	 * 导入价格调整
	 */
	public void inputPriceChangeData();

	/**
	 * 吊牌价格修改
	 */
	public void inputListPriceChange();

	public void changeListPrice();

	/**
	 * 导入货运单据
	 */
	public void inputEverGreenData();

	/**
	 * 抓取文件
	 */
	public void download(String remotePath, String localPath, String remoteBakPath, Map<String, String> config);

	/**
	 * 上传
	 */
	public void upload(String remotePath, String localPath, String localBakPath, Map<String, String> config);

	/**
	 * 导入指令文件到数据库
	 */
	public void importConverseTaskFile(String dirpath, String bakDir, ConverseTaskType taskType);

	/**
	 * 判断文件名称是否正确
	 */
	public boolean importTaskFile(File file, String bakDir, ConverseTaskType taskType) throws Exception;
}
