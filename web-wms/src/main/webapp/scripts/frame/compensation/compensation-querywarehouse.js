var INCLUDE_SEL_ID = "";
var INCLUDE_SEL_VAL_CODE = "";
function initShopQuery(selId,valCode){
	INCLUDE_SEL_ID = selId;
	INCLUDE_SEL_VAL_CODE = valCode;
	if(valCode == 'name'){
		INCLUDE_SEL_VAL_CODE = 'id'
	}
	
}

$j(document).ready(function (){
	$j("#shopQueryDialog").dialog({title: "仓库查询", modal:true, autoOpen: false, width: 680});
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_shop_query_dialog").jqGrid({
		url:$j("body").attr("contextpath") + "/json/getSendWarehouseforPage.do",
		datatype: "json",
		colNames: ["id","仓库名称"],
		colModel: [ {name: "id", width: 100,hidden: true},
		           {name: "name", index: "name", width: 450,  resizable: true}
		           ],
		caption: "仓库列表",
		rowNum: 10,
		rowList:[10,20,40],
	   	sortname: 'id',
	    pager: '#tbl_shop_query_dialog_pager',
	    multiselect: false,
		sortorder: "desc", 
		height:'auto',
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#btnShopQueryClose").click(function(){
		$j("#shopQueryDialog").dialog("close");
	});
	
	$j("#btnShopQuery").click(function(){
		var url = $j("body").attr("contextpath") + "/json/getSendWarehouseforPage.do";
		$j("#tbl_shop_query_dialog").jqGrid('setGridParam',
				{url:url,page:1,postData:loxia._ajaxFormToObj("shopQueryDialogForm")}).trigger("reloadGrid");
	});
	
	$j("#btnShopQueryConfirm").click(function(){
		var id = $j('#tbl_shop_query_dialog').jqGrid('getGridParam','selrow');
		var data=$j("#tbl_shop_query_dialog").jqGrid("getRowData",id);
		$j("#"+INCLUDE_SEL_ID).val(data[INCLUDE_SEL_VAL_CODE]);
		$j("#"+INCLUDE_SEL_ID).trigger("change");
		$j("#shopQueryDialog").dialog("close");
	});
	
	$j("#btnShopFormRest").click(function(){
		$j("#shopQueryDialogForm input,#shopQueryDialogForm select").val("");
	});
});