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
	$j("#shopQueryDialog").dialog({title: "渠道查询", modal:true, autoOpen: false, width: 680});
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_shop_query_dialog").jqGrid({
		url:$j("body").attr("contextpath") + "/getbichannelinfo.json",
		datatype: "json",
		colNames: ["id","innerShopCode","渠道编码","渠道名称"],
		colModel: [ {name: "id", width: 100,hidden: true},
		           {name: "code", width: 100,hidden: true},
		           {name: "shopCode", index: "shop_code", width: 120, resizable: true},
		           {name: "name", index: "name", width: 250,  resizable: true}
		           ],
		caption: "店铺列表",
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
		var url = $j("body").attr("contextpath") + "/getbichannelinfo.json";
		$j("#tbl_shop_query_dialog").jqGrid('setGridParam',
				{url:url,page:1,postData:loxia._ajaxFormToObj("shopQueryDialogForm")}).trigger("reloadGrid");
	});
	
	$j("#btnShopQueryConfirm").click(function(){
		var id = $j('#tbl_shop_query_dialog').jqGrid('getGridParam','selrow');
		var data=$j("#tbl_shop_query_dialog").jqGrid("getRowData",id);
		$j("#" + INCLUDE_SEL_ID).val(data[INCLUDE_SEL_VAL_CODE]);
		$j("#" + INCLUDE_SEL_ID).trigger("change");
		$j("#shopQueryDialog").dialog("close");
	});
	
	$j("#btnShopFormRest").click(function(){
		$j("#shopQueryDialogForm input,#shopQueryDialogForm select").val("");
	});
});