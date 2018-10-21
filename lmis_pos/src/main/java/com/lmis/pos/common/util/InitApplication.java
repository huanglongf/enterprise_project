package com.lmis.pos.common.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import com.lmis.pos.common.job.DataSourceAsyncJob;

@Configuration
public class InitApplication implements ApplicationListener<ContextRefreshedEvent> {
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());  
	
	@Value("${lmis_pos.podata.excel_import_file_download_path}")
	private String excelImportFileDownloadPath; 
	
	@Value("${lmis_pos.podata.excel_import_file_upload_path}")
	private String excelImportFileUploadPath;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		File uploadFile = new File(excelImportFileUploadPath);
		File downloadFile = new File(excelImportFileDownloadPath);
		if(!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		logger.error(excelImportFileUploadPath + "路径已创建。");
		if(!downloadFile.exists()) {
			downloadFile.mkdirs();
		} 
		logger.error(excelImportFileDownloadPath + "路径已创建。");
		DataSourceAsyncJob.initJobContext();
		logger.error("DataSourceAsyncJob已初始化。");
		logger.error("应用环境初始化完毕");
	}

}
