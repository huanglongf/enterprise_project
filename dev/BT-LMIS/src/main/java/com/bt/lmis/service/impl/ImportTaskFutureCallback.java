package com.bt.lmis.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bt.lmis.dao.FileImportTaskMapper;
import com.bt.lmis.model.FileImportTask;
import com.google.common.util.concurrent.FutureCallback;

/**
* Title: ImportTaskFutureCallback
* Description: 导入任务回调服务类
* Company: baozun
* @author jindong.lin
* @date 2018年1月26日
*/
@Service
public class ImportTaskFutureCallback implements FutureCallback<String> {

	// 定义日志
	private static final Logger logger=Logger.getLogger(ImportTaskFutureCallback.class);

	@Override
	public void onSuccess(String importTaskMessage) {
		logger.error(importTaskMessage);
	}

	@Override
	public void onFailure(Throwable t) {
		logger.error(t.getMessage());
	}

}
