var INCLUDE_SEL_ID = "";
var INCLUDE_SEL_VAL_CODE = "";
function initBrandQuery(selId,name){
	INCLUDE_SEL_ID = selId;
	INCLUDE_SEL_VAL_CODE = name;
}

$j(document).ready(function (){
	$j("#BrandNameQueryDialog").dialog({title: "品牌查询", modal:true, autoOpen: false, width: 600});
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_brandname_query_dialog").jqGrid({
		url:$j("body").attr("contextpath") + "/findBrandByPage.json",
		datatype: "json",
		colNames: ["id","code","品牌名称"],
		colModel: [ {name: "id", width: 100,hidden:true},
		            {name: "code", index: "code", width: 100,  resizable: true},
		           {name: "name", index: "name", width: 350,  resizable: true}
		           ],
		caption: "品牌列表",
		rowNum: 10,
		rowList:[10,20,40],
	   	sortname: 'id',
	    pager: '#tbl_brandname_query_dialog_pager',
	    multiselect: false,
		sortorder: "desc", 
		height:'auto',
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#btnBrandNameQueryClose").click(function(){
		$j("#BrandNameQueryDialog").dialog("close");
	});
	
	$j("#btnBrandNameQuery").click(function(){
		var url = $j("body").attr("contextpath") + "/findBrandByPage.json";
		$j("#tbl_brandname_query_dialog").jqGrid('setGridParam',
				{url:url,page:1,postData:loxia._ajaxFormToObj("brandNameQueryDialogForm")}).trigger("reloadGrid");
	});
	
	$j("#btnBrandNameQueryConfirm").click(function(){
		var id = $j('#tbl_brandname_query_dialog').jqGrid('getGridParam','selrow');
		var data=$j("#tbl_brandname_query_dialog").jqGrid("getRowData",id);
		$j("#" + INCLUDE_SEL_ID).val(data[INCLUDE_SEL_VAL_CODE]);
		$j("#" + INCLUDE_SEL_ID).trigger("change");
		$j("#BrandNameQueryDialog").dialog("close");
	});
	
	$j("#btnBrandNameFormRest").click(function(){
		$j("#brandNameQueryDialogForm input,#brandNameQueryDialogForm select").val("");
	});
});

