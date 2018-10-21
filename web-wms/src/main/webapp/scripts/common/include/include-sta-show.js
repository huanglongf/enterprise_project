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
	$j("#staQueryDialog").dialog({title: "指令明细", modal:true, autoOpen: false, width: 680});
//	var baseUrl = $j("body").attr("contextpath");

	
	$j("#btnStaQueryClose").click(function(){
		$j("#staQueryDialog").dialog("close");
	});
	
	$j("#btnShopQuery").click(function(){
		var url = $j("body").attr("contextpath") + "/getbichannelinfo.json";
		$j("#tbl_sta_query_dialog").jqGrid('setGridParam',
				{url:url,page:1,postData:loxia._ajaxFormToObj("staQueryDialogForm")}).trigger("reloadGrid");
	});
	
	$j("#btnStaQueryConfirm").click(function(){
		var id = $j('#tbl_sta_query_dialog').jqGrid('getGridParam','selrow');
		var data=$j("#tbl_sta_query_dialog").jqGrid("getRowData",id);
		$j("#" + INCLUDE_SEL_ID).val(data[INCLUDE_SEL_VAL_CODE]);
		$j("#" + INCLUDE_SEL_ID).trigger("change");
		$j("#staQueryDialog").dialog("close");
	});
	
	$j("#btnStaFormRest").click(function(){
		$j("#staQueryDialogForm input,#staQueryDialogForm select").val("");
	});
});