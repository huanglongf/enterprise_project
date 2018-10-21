package com.lmis.pos.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantCommon {
	public final static ExecutorService exector = Executors.newFixedThreadPool(5);
	
	/** 
	 * @Fields COMPENSATE_IDS : TODO(拆分时补偿ID) 
	 */
	public final static String COMPENSATE_IDS= "COMPENSATE_IDS";
	
	/** 
	 * @Fields ROLLBACK_IDS : TODO(取消时补偿ID) 
	 */
	public final static String ROLLBACK_IDS= "ROLLBACK_IDS";
	
	/**
	 * 数据导出数据定量查询大小
	 */
	public final static int excelExprotPageSize = 100000;
	
	/**
	 * 数据导出写入文件刷盘大小
	 */
	public final static int excelExprotFlushSize = 1000;

    /**
     * 默认数据源名
     */
    public final static String defaultDataSourceType = "dataSource";

	@Value("${spring.datasource.type}")
    private String type;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
