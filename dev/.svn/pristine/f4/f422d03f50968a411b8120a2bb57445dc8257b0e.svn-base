package com.bt.lmis.model;

import java.util.List;

import com.bt.utils.Constant;

/**
 * @Title:SimpleMasterSlaveReport
 * @Description: TODO(简单主从报表实体)
 * @author Ian.Huang 
 * @date 2017年3月17日下午6:57:31
 */
public class SimpleMasterSlaveReport {
	// 文件生成路径 如：/home/lmis/balance/report/
	private String fileGenerationPath;
	// 报表名 如：201704sf balance report
	private String reportName;
	// 报表文件类型后缀 如：xlsx
	private String postfix;
	// 报表文件路径 如：/home/lmis/balance/report/201704sf balance report.xlsx
	private String filePath;
	// 报表文件所含表
	private List<SimpleTable> tables;
	public SimpleMasterSlaveReport() {}
	public SimpleMasterSlaveReport(String fileGenerationPath, String reportName, String postfix, List<SimpleTable> tables) {
		this.fileGenerationPath = fileGenerationPath;
		this.reportName = reportName;
		this.postfix = postfix;
		this.filePath = fileGenerationPath + reportName + Constant.SEPARATE_POINT + postfix;
		this.tables = tables;		
	}
	public String getFileGenerationPath() {
		return fileGenerationPath;
	}
	public void setFileGenerationPath(String fileGenerationPath) {
		this.fileGenerationPath = fileGenerationPath;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getPostfix() {
		return postfix;
	}
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public List<SimpleTable> getTables() {
		return tables;
	}
	public void setTables(List<SimpleTable> tables) {
		this.tables = tables;
	}
	
}