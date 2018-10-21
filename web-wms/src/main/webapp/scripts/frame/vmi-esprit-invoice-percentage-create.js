$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件",
	INVOICE_NUMBER:"发票号",
	DUTY_PERCENT:"税收系数",
	MISC_FEE_PERCENT:"其他系数",
	COMM_PRECENT:"佣金系数",
	CREATE_TIME:"创建时间",
	IP_QUERY:"发票号系数查询列表",
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件"
});
 
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}





$j(document).ready(function (){
	
	$j("#tbl_poType").jqGrid({
		datatype: "json",
		colNames: ["ID","PO号",i18("INVOICE_NUMBER"),/*i18("DUTY_PERCENT"),i18("MISC_FEE_PERCENT"),i18("COMM_PRECENT"),*/i18("CREATE_TIME")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name :"po",index:"po",width:100,resizable:true},
					{name :"invoiceNum",index:"invoiceNum",width:100,resizable:true},
					/*{name:"dutyPercentage", index:"dutyPercentage" ,width:70,resizable:true},
					{name:"miscFeePercentage", index:"miscFeePercentage" ,width:70,resizable:true},
					{name:"commPercentage", index:"commPercentage" ,width:70,resizable:true},*/
					{name:"version",index:"version",width:130,resizable:true}],
		caption: i18("IP_QUERY"),//"作业单导入成功列表",
		rownumbers:true,
		rowNum:-1,
		height:"auto",
	   	sortname: 'ID',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#query").click(function(){
		var postData = loxia._ajaxFormToObj("query_form");
		$j("#tbl_poType").jqGrid('setGridParam',{url: window.$j("body").attr("contextpath") + "/findInvoicePertentage.json",page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_poType",{},
			$j("body").attr("contextpath") + "/findInvoicePertentage.json",
			postData);
	});
	
	$j("#reset").click(function(){
		$j("#query_form input,select").val("");
	});
	
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importInvoicePertentage.do"));
		loxia.submitForm("importForm");
	});
});

