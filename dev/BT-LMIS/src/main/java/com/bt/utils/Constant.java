package com.bt.utils;

/**
 * @Title:Constant
 * @Description: TODO  
 * @author Ian.Huang 
 * @date 2016年11月30日下午5:20:00
 */
public class Constant {
	
	public static final Integer SINGLE_SHEET_MAX_NUM= 300000;
	
	public static final String SEPARATE_POINT= ".";
	
	public static final String SUF_XLS= "xls";
	public static final String SUF_XLSX= "xlsx";
	public static final String SUF_CSV= "csv";
	
	public static final String SUFFIX_XLS= ".xls";
	public static final String SUFFIX_XLSX= ".xlsx";
	public static final String SUFFIX_ZIP= ".zip";
	public static final String SUFFIX_CSV= ".csv";
	
	public static final String jdbc_property = "config.properties";
	
	public static final String PERFIX_MAPPER_ODP= "originDestParam";
	public static final String PERFIX_MAPPER_JBPC= "jbpcEx";
	public static final String PERFIX_MAPPER_PF= "pricingFormula";
	public static final String PERFIX_MAPPER_NPI= "nextPriceInternal";
	public static final String PERFIX_MAPPER_IEC= "insuranceEC";
	public static final String PERFIX_MAPPER_SSE= "specialServiceEx";
	
	// 工单号
	public static final String PERFIX_WO= "WO";
	// 索赔编码
	public static final String PERFIX_WOC= "WOC";
	
	public static final String SHORT_BAR= "-";
	
	// 工单来源
	public static final String WO_SOURCE_MAN= "人工创建";
	public static final String WO_SOURCE_OMS= "OMS";
	public static final String WO_SOURCE_ER= "快递雷达";
	
	// 工单分配状态
	public static final String WO_ALLOC_STATUS_WAA= "WAA";//待自动
	public static final String WO_ALLOC_STATUS_WMA= "WMA";//待手动
	public static final String WO_ALLOC_STATUS_ALD= "ALD"; //已分配
	
	// 工单处理状态
	public static final String WO_PROCESS_STATUS_NEW= "NEW";
	public static final String WO_PROCESS_STATUS_PRO= "PRO";
	public static final String WO_PROCESS_STATUS_PAU= "PAU";
	public static final String WO_PROCESS_STATUS_CCD= "CCD";
	public static final String WO_PROCESS_STATUS_FIN= "FIN";
	
	// 工单事件
	// 创建
	public static final String WO_EVENT_CREATED= "CREATED";
	// 预计完成时间
	public static final String WO_EVENT_ETC= "ETC";
	// 手动分配
	public static final String WO_EVENT_ALLOC_M= "ALLOC_M";
	// 自动分配
	public static final String WO_EVENT_ALLOC_A= "ALLOC_A";
	// 取消分配
	public static final String WO_EVENT_CANCEL_ALLOC= "CANCEL_ALLOC";
	// 开始处理
	public static final String WO_EVENT_START= "START";
	// 
	public static final String WO_EVENT_UPDATE= "UPDATE";
	// 挂起
	public static final String WO_EVENT_PAUSE= "PAUSE";
	// 取消挂起
	public static final String WO_EVENT_RECOVER= "RECOVER";
	// 取消
	public static final String WO_EVENT_CANCEL= "CANCEL";
	// 已处理
	public static final String WO_EVENT_FIN= "FIN";
	
	// 工单类型
	public static final String WO_TYPE_CPTO= "CPTO";
	public static final String WO_TYPE_DTO= "DTO";
	public static final String WO_TYPE_REJ= "REJ";
	public static final String WO_TYPE_HDTSD= "HDTSD";
	public static final String WO_TYPE_LODA= "LODA";
	public static final String WO_TYPE_CR= "CR";
	public static final String WO_TYPE_OMSC= "OMSC";
	public static final String WO_TYPE_NOMSC= "NOMSC";
	
}
