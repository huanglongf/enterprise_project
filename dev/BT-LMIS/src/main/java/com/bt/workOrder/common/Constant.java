package com.bt.workOrder.common;

import java.io.Serializable;

/** 
 * @ClassName: Constant
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月25日 下午3:58:25 
 * 
 */
public class Constant implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 4688849679966147825L;
	
	public static final String ACTION_SAVE="SAVE";
	public static final String ACTION_SUBMIT="SUBMIT";
	public static final String ACTION_OBTAIN="OBTAIN";
	public static final String ACTION_ALLOC="ALLOC";
	public static final String ACTION_FORWARD="FORWARD";
	public static final String ACTION_SPLIT="SPLIT";
	public static final String ACTION_ASSIST="ASSIST";
	public static final String ACTION_REPLY="REPLY";
	public static final String ACTION_OVER="OVER";
	public static final String ACTION_CANCLE="CANCLE";
	public static final String ACTION_ASSC="ASSC";
	public static final String ACTION_UPDATE="UPDATE";
	// 物流部
	public static final String LD_NODE="LD";
	// 店铺
	public static final String ST_NODE="ST";
	// 销售运营
	public static final String SO_NODE="SO";
	
	public static final String WO_SOURCE_ST="店铺创建";
	
	public static final String RESULT_SUCCESS_MSG="操作成功";
	public static final String RESULT_FAILURE_MSG="操作失败，失败原因：";
	public static final String RESULT_FAILURE_REASON_1="当前操作未指定店铺工单";
	public static final String RESULT_FAILURE_REASON_2="指定店铺工单不存在";
	public static final String RESULT_FAILURE_REASON_3="对应物流工单不存在";
	public static final String EXPORT_SUCCESS_MSG="导出成功";
	public static final String EXPORT_FAILURE_MSG="导出失败，失败原因：";
	
	public static final String PROCESS_REMARK_TITLE="处理意见：";
	public static final String ACCESSORY_TITLE="提交附件：";
	
	public static final String EXPORT_NAME="工单导出";
	
	public static final String DEFAULT_WAYBILL="-99999";
	
	public static final String WO_NODE_LD="物流中心";
	public static final String WO_NODE_SO="销售运营部";
	
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

}
