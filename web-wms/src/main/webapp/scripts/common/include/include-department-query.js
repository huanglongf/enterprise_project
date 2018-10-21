var INCLUDE_SEL_ID = "";
var INCLUDE_SEL_VAL_CODE = "";
function initShopQuery(selId,valCode){
	INCLUDE_SEL_ID = selId;
	INCLUDE_SEL_VAL_CODE = valCode;
	if(valCode == 'innerShopCode'){
		INCLUDE_SEL_VAL_CODE = 'code'
	}
}

$j(document).ready(function (){
	$j("#departQueryDialog").dialog({title: "行政部门查询", modal:true, autoOpen: false, width: 680});
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_depart_query_dialog").jqGrid({
		url:$j("body").attr("contextpath") + "/json/getChooseOptionByCodePage.do?categoryCode=departmentType",
		datatype: "json",
		colNames: ["id","innerShopCode","行政部门Key","行政部门名称"],
		colModel: [ {name: "id", width: 100,hidden: true},
		           {name: "code",width: 100,hidden: true},
		           {name: "optionKey", index: "optionKey", width: 120, resizable: true},
		           {name: "optionValue", index: "optionValue", width: 250,  resizable: true}
		           ],
		caption: "行政部门列表",
		rowNum: 10,
		rowList:[10,20,40],
	   	sortname: 'id',
	    pager: '#tbl_depart_query_dialog_pager',
	    multiselect: false,
		sortorder: "desc", 
		height:'auto',
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
//	$j("#tbl_depart_query_dialog").jqGrid({
//		datatype: "json",
//		url:$j("body").attr("contextpath") + "/json/getChooseOptionByCodePage.do?categoryCode=departmentType",
//		colNames: ["id","innerShopCode","行政部门Key","行政部门名称"],
//		colModel: [ {name: "id", index: "id",width: 100,hidden: true},
//		           {name: "code", index: "code",width: 100,hidden: true},
//		           {name: "optionKey", index: "optionKey", width: 120, resizable: true},
//		           {name: "optionValue", index: "optionValue", width: 250,  resizable: true}
//		           ],
//		caption: "行政部门列表",
//	   	sortname: 'id',
//	    multiselect: false,
//	    height:"auto",
//	    rowNum: 10,
//	    rowList:[10,20,40],
//	    pager:"#tbl_depart_query_dialog_pager",
//		sortorder: "asc",
//		viewrecords: true,
//   		rownumbers:true,
//		jsonReader: { repeatitems : false, id: "0" }
//	});

	$j("#btnDepartQueryClose").click(function(){
		$j("#departQueryDialog").hide();
		$j("#departQueryDialog").dialog("close");
	});
	
	$j("#btnDepartQuery").click(function(){
		var url = $j("body").attr("contextpath") + "/json/getChooseOptionByCodePage.do?categoryCode=departmentType";
		$j("#tbl_depart_query_dialog").jqGrid('setGridParam',
				{url:url,page:1,postData:loxia._ajaxFormToObj("departQueryDialogForm")}).trigger("reloadGrid");
	});
	
	$j("#btnDepartQueryConfirm").click(function(){
		var id = $j('#tbl_depart_query_dialog').jqGrid('getGridParam','selrow');
		var data=$j("#tbl_depart_query_dialog").jqGrid("getRowData",id);
		$j("#departmentshop").val(data["optionKey"]);
		$j("#departmentshop").trigger("change");
		$j("#departQueryDialog").dialog("close");
	});
	
	$j("#btnDepartFormRest").click(function(){
		$j("#departQueryDialogForm input,#departQueryDialogForm select").val("");
	});
});