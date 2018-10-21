package com.bt.lmis.balance.common;

import java.io.Serializable;

public class Constant implements Serializable {

	/** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 2807383013816387901L;
	// 结算返回信息
	// 异常信息
	public static final String ERROR_MSG="ERROR_MSG";
	// 费用项字典
	public static final String EXPRESS_EXPENDITURE="快递支出";
	public static final String EXPRESS_FREIGHT="快递运费";
	public static final String CARRIER_FEE="承运商费用（总运费折扣|管理费）";
	public static final String STORAGE_FEE="仓储费";
	public static final String LABOUR_FEE_OR_HANDLING_CHARGE="操作费";
	public static final String CONSUMABLE_FEE="耗材费";
	public static final String VAS_FEE="增值服务费";
	public static final String BUYOUT_FEE="打包费";
	// 承运商合同所属字典
	public static final String SF="SF";
	public static final String EMS="EMS";
	public static final String YTO="YTO";
	public static final String STO="STO";
	// 快递结算明细表名
	public static final String EXPRESS_SETTLEMENT_DETAIL="tb_warehouse_express_data_settlement";
	public static final String EXPRESS_SETTLEMENT_DETAIL_TEMP="tb_warehouse_express_data_settlement_temp";
	// 标点符号
	public static final String SYM_UNDERLINE="_";
	public static final String SYM_SUBTRACT="-";
	public static final String SYM_POINT=".";
	public static final String SYM_OPEN_BRACKET="[";
	public static final String SYM_CLOSE_BRACKET="]";
	// 文件类型后缀
	public static final String FILE_TYPE_SUFFIX_XLSX="xlsx";
	public static final String FILE_TYPE_SUFFIX_ZIP="zip";
	// 导出excel单sheet最大数据行数
	public static final Integer SINGLE_SHEET_MAX_NUM=300000;
	// 批次号标志码
	public static final String PERFIX_ESTIMATE="BE";
	// 批次状态 FIN-已完成 CAN-已取消 WAI-等待中 ING-进行中 ERR-异常
	public static final String BATCH_STATUS_FIN="FIN";
	public static final String BATCH_STATUS_CAN="CAN";
	public static final String BATCH_STATUS_WAI="WAI";
	public static final String BATCH_STATUS_ING="ING";
	public static final String BATCH_STATUS_ERR="ERR";
	// 预估操作类型
	public static final String ESTIMATE_OPERATION_ADD="add";
	public static final String ESTIMATE_OPERATION_DEL="delete";
	public static final String ESTIMATE_OPERATION_CAN="cancel";
	public static final String ESTIMATE_OPERATION_RES="restart";
	
}