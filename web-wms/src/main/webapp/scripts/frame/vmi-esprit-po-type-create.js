$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件",
	PO_NUMBER:"PO单号",
	PO_TYPE:"PO类型",
	CREATE_TIME:"创建时间",
	PO_QUERY:"PO类型查询列表"
});
 
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}





$j(document).ready(function (){
	
	$j("#tbl_poType").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("PO_NUMBER"),i18("PO_TYPE"),'箱号','发票号',i18("CREATE_TIME")],
		colModel: [{name: "id", index: "id", hidden: true},
					{name :"po",index:"po",width:100,resizable:true},
					{name:"typeName", index:"typeName" ,width:70,resizable:true},
					{name:"poStyle", index:"poStyle" ,width:70,resizable:true},
					{name:"invoiceNumber", index:"invoiceNumber" ,width:70,resizable:true},
					{name:"version",index:"version",width:130,resizable:true}],
		caption: i18("PO_QUERY"),//"PO类型查询列表",
		sortname: 'id',
   		sortorder: "desc",
		multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#query").click(function(){
		var postData = loxia._ajaxFormToObj("query_form");
		$j("#tbl_poType").jqGrid('setGridParam',{url: window.$j("body").attr("contextpath") + "/findPotypeList.json",page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_poType",{},
			$j("body").attr("contextpath") + "/findPotypeList.json",postData);
//		
//		jumbo.bindTableExportBtn("tbl_poType");
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
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importVMIPOType.do"));
		loxia.submitForm("importForm");
	});
});

