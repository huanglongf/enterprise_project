$j.extend(loxia.regional['zh-CN'],{
	APPLY_FORM_SELECT : "请选择作业申请单",
	CODE : "作业单号",
	STATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	SHOPID : "相关店铺",
	LPCODE : "物流服务商简称",
	ISNEEDINVOICE : "是否需要发票",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	COUNT_SEARCH : "待出库列表",
	
	SKUNAME : "商品名称",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	KEYPROPERTIES : "扩展属性",
	QUANTITY : "计划执行量",
	DETAIL_INFO : "详细信息",
	CORRECT_FILE_PLEASE:"请选择正确的Excel文件进行导入"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
//var customizationShopMap = {};//当前仓库定制打印模版店铺集合
$j(document).ready(function(){
	$j("#import").click(function(){
		var file=$j.trim($j("#snsfile").val());
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
			return;
		}
		$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/caiNiaoWhSnImport.do"));
		loxia.submitForm("importForm");
	});

});
