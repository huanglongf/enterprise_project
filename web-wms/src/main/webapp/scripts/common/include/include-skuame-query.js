
$j(document).ready(function (){
	$j("#shopQueryDialog").dialog({title: "SKU查询", modal:true, autoOpen: false, width: 680});
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_shop_query_dialog").jqGrid({
		url:baseUrl+ "/querySkuList.json",
		datatype: "json",
		colNames: ["id","商品编码","商品名称","货号"],
		colModel: [ {name: "id", width: 100,hidden: true},
		           {name: "code",index: "code",width:80, resizable:true},
		           {name: "name", index: "name", width: 120, resizable: true},
		           {name: "supplierCode", index: "supplierCode", width: 120, resizable: true}
		           ],
		caption: "SKU列表",
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
		var url = $j("body").attr("contextpath") + "/querySkuList.json";
		$j("#tbl_shop_query_dialog").jqGrid('setGridParam',
				{url:url,page:1,postData:loxia._ajaxFormToObj("shopQueryDialogForm")}).trigger("reloadGrid");
	});
	
	$j("#btnShopQueryConfirm").click(function(){
		var id = $j('#tbl_shop_query_dialog').jqGrid('getGridParam','selrow');
		var data=$j("#tbl_shop_query_dialog").jqGrid("getRowData",id);
		$j("#skuName").val(data["name"]);
		$j("#skuName2").val(data["name"]);
		$j("#shopQueryDialog").dialog("close");
	});
	
	$j("#btnShopFormRest").click(function(){
		$j("#shopQueryDialogForm input,#shopQueryDialogForm select").val("");
	});
});