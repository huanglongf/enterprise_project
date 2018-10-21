$j.extend(loxia.regional['zh-CN'],{
	SN:"SN号",
	STACODE : "作业单",
	SKUCODE : "SKU编码",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	SKU_NAME : "商品名称",
	INV_CK_CODE: "盘点号",
	DIRECTION: "作业方向",
	DIRECTION_TIME: "作业时间",
	BATCH_CODE:"批次号"
		
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}


$j(document).ready(function (){
	$j("#tbl-snLogList").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SN"),i18("BATCH_CODE"),i18("STACODE"),i18("SKUCODE"),i18("BARCODE"),i18("JMCODE"),i18("SKU_NAME"),
		           i18("INV_CK_CODE"),i18("DIRECTION_TIME"),i18("DIRECTION")],
		colModel: [
					{name: "id", index: "id", hidden: true},
					{name:"sn",index:"sn",width:150,resizable:true},
					{name:"batchCode",index:"batchCode",width:100,resizable:true},
					{name:"staCode",index:"staCode",width:120,resizable:true},
					{name:"skuCode",index:"skuCode",width:120,resizable:true},
					{name:"barcode",index:"barcode",width:120,resizable:true},
					{name:"jmCode", index:"jmCode" ,width:120,resizable:true},
					{name:"skuName", index:"skuName",width:120,resizable:true},
					{name:"invCkCode", index:"invCkCode", width:80, resizable:true },
					{name:"transactionTime", index:"transactionTime",width:120,resizable:true},
					{name:"directionString", index:"directionString",width:100,resizable:true}
				],
		caption: "SN日志列表",
		sortname: "transactionTime",
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl-snLogList",{});
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	$j("#search").click(function(){
		var url = $j("body").attr("contextpath") + "/querySnLog.json";
		$j("#tbl-snLogList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-snLogList",{},url,loxia._ajaxFormToObj("queryForm"));
	});
});