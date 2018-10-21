var extBarcode = null;
var outboundSn = null;
var getValues = null;
$j.extend(loxia.regional['zh-CN'],{
	ENTITY_INMODE				:	"上架批次处理模式",
	ENTITY_EXCELFILE			:	"正确的excel导入文件",
	ENTITY_STA_CREATE_TIME		:	"作业创建时间",
	ENTITY_STA_ARRIVE_TIME		:	"预计到货日期",
	ENTITY_STA_CODE				:	"作业单号",
	ENTITY_STA_PROCESS			:	"执行情况",
	ENTITY_STA_UPDATE_TIME		:	"上次执行时间",
	ENTITY_STA_PO				:	"相关单据号",
	ENTITY_STA_OWNER			:	"店铺名称",
	ENTITY_STA_SUP				:	"供应商名称",
	ENTITY_STA_SEND_MODE		:	"送货方式",
	ENTITY_STA_REMANENT			:	"剩余计划入库量（件）",
	ENTITY_STA_COMMENT			:	"相关单据备注",
	// "SKU条形码","SKU JMCODE","SKU扩展属性","SKU
	// 名称","SKU供应商编码","计划量执行量","已执行量","本次执行量"],
	ENTITY_SKU_BARCODE			:	"条形码",
	IMPERFECT_BARCODE			:	"残次条码",
	IMPERFECT_TYPE			    :	"残次类型",
	IMPERFECT_WHY			    :	"残次原因",
	ENTITY_SKU_JMCODE			:	"商品编码",
	ENTITY_SKU_KEYPROP			:	"扩展属性",
	ENTITY_SKU_NAME				:	"商品名称",
	ENTITY_SKU_SUPCODE			:	"货号",
	ENTITY_STALINE_TOTAL		:	"计划量执行量",
	TRUNKPACKINGLISTINFO:"装箱清单打印中，请等待...",
	ENTITY_STALINE_COMPLETE		:	"已执行量",
	ENTITY_STALINE_CURRENT		:	"本次执行量",
	ENTITY_LOCATION				:	"库位",
	ENTITY_LOCATION_CAPACITY	:	"库容",
	ENTITY_LOCATION_CURRENT		:	"当前库存数",
	ENTITY_LOCATION_ADD			:	"上架数",			
	ENTITY_INVENTORY_STATUS		:	"库存状态",			
	INVALID_QTY					:	"第{0}行的收货数量不是有效的数字类型!",
	INVALID_QTY_G				:	"第{0}行的本次执行量大于未收货的数量,请核对每个商品[或许存在相同的商品]的本次执行量!",
	LABEL_ENPTY					:	"无",
	LABEL_BARCODE				:	"切换为使用条码方式录入",
	LABEL_JMCODE				:	"切换为使用商品编码方式录入",
	LABEL_TOTAL					:	"总计：",
	LABEL_LINE					:	"第{0}行",
	INVALID_NOT_EXIST 			:	"{0}不正确/不存在",
	MSG_CONFIRM_CLOSESTA		:	"您确定要关闭本次采购收货吗?",
	MSG_NO_ACTION				:	"本次收货数量为0!",
	MSG_CLOSESTA_FAILURE		:	"关闭作业申请单失败!",
	MSG_CONFIRM_CANCEL_STV		:	"如果返回，前面的操作将全部作废。您确定要返回吗?",
	MSG_ERROR_INMODE			:	"当前库位不满足单批隔离上架模式!",
	MSG_ERROR_LOCATION_QTY		:	"当前库位库容不能满足当前上架数量!",
	MSG_ERROR_STALINE_QTY		:	"实际上架数量≠计划量",
	MSG_WARN_STALINE_QTY		:	"实际输入的上架数量小于计划量,差异的数据录入到虚拟仓!",
	TABLE_CAPTION_STA			:	"退换货待收货列表",
	TABLE_CAPTION_STALINE		:	"退换货待收货明细表",
	MSG_LOCATION_CAPACITY		:	"不限",
	MSG_INVSTATUS_ERROR			:	"获取库存状态列表异常",
	ENTITY_STALINE_SNS			:	"SN序列号",			
	LABEL_ADD_SNS				:	"维护序列号",
	INVALID_SN					:	"请按正确的格式填写SN序列号",
	INVALID_SN_LINE				:	"请按正确的格式填写第{0}行SN序列号",
	OPERATING 					:	"商品上架情况打印中，请等待...",
	CORRECT_FILE_PLEASE         :   "请选择正确的Excel导入文件",
	
	WARRANTY_CARD_TYPE_NO		:	"1",
	WARRANTY_CARD_TYPE_YES_NO_CHECK	:	"3",
	
    COACH_OWNER1				:	"1coach官方旗舰店",
    COACH_OWNER2				:	"1Coach官方商城",
	
	LPCODE 						: 	"物流服务商",
	TRACKING_NO					:	"快递单号", 
	IS_NEED_INVOICE				: 	"是否需要发票",
	ORDER_CREATE_TIME			:	"原订单创建时间",
	RETURN_REASON_MEMO			:   "退换货备注"
	
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
function initShopQuery(selId,valCode){
	INCLUDE_SEL_ID = selId;
	INCLUDE_SEL_VAL_CODE = valCode;
	if(valCode == 'innerShopCode'){
		INCLUDE_SEL_VAL_CODE = 'code';
	}
}


$j(document).ready(function (){
	$j("#search").click(function(){
		var url = $j("body").attr("contextpath")+"/queryByReport.json";
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:url}).trigger("reloadGrid");
	});
		// STA作业申请单
		$j("#tbl-inbound-purchase").jqGrid({
			url:$j("body").attr("contextpath")+"/queryByReport.json",
			datatype: "json",
			colNames: ["店铺","0-5小时","5-18小时","18-24小时","24-42小时","42-48小时","48-66小时","72小时以后","合计"],
			colModel: [
			           {name: "owner", index: "owner",sortable: false, width: 200, resizable: true},
			           {name: "qty1", index: "qty1",sortable: false, width: 100, resizable: true},
			           {name: "qty2", index: "qty2",sortable: false, width: 100, resizable: true},
			           {name: "qty3", index: "qty3",sortable: false, width: 100, resizable: true},
			           {name: "qty4", index: "qty4",sortable: false, width: 100, resizable: true},
			           {name: "qty5", index: "qty5",sortable: false, width: 100, resizable: true},
			           {name: "qty6", index: "qty6",sortable: false, width: 100, resizable: true},
			           {name: "qty7", index: "qty7",sortable: false, width: 100, resizable: true},
			           {name: "qty8", index: "qty8",sortable: false, width: 100, resizable: true}],
			caption: "未完成单据数量",// 作业单列表
			rowNum:-1,
		   	sortname: 'id',
		   	pager:"#pager",
		   	height:"auto",
		    rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
		   	multiselect: false,
		    rownumbers:true,
		    viewrecords: true,
			hidegrid:false,
			sortorder: "asc",
			gridComplete : function(){
				loxia.initContext($j(this));
			},
			jsonReader: { repeatitems : false, id: "0" }
			  
		});
});
