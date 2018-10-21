$j(document).ready(function (){
	initShopQuery("companyshop","innerShopCode");
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#lpCode"));
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#newlpCode"));
	}
	jumbo.loadShopList("companyshop");
	$j("#tbl-delivery-change-list").jqGrid({
		datatype:"json",
		colNames:["ID","作业单号","相关单据号","平台订单号","店铺","原物流商","原快递单号","现物流商","现快递单号","更改时间"],	
		colModel:[
		          {name:"id",index:"id",hidden:true},
		          {name:"staCode",index:"staCode",width: 150,resizable: true},
		          {name:"slipCode",index:"slipCode",width: 150,resizable: true},
		          {name:"slipcode1",index:"slipcode1",width: 150,resizable: true},
		          {name:"channel",index:"channel",width: 150,resizable: true},
		          {name:"lpcode",index:"lpcode",width: 150,resizable: true},
		          {name:"trackingNo",index:"trackingNo",width: 150,resizable: true},
		          {name:"newLpcode",index:"newLpcode",width: 150,resizable: true},
		          {name:"newTrackingNo",index:"newTrackingNo",width: 150,resizable: true},
		          {name:"createTime",index:"createTime",width: 150,resizable: true},
		          ],
		caption:"销售出库转物流变更查询",
		multiselect: false,
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		width:"auto",
		height:"auto",
		pager:"#pager_query",	   
		sortorder: "createTime desc",
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#search").click(function(){
		var url = $j("body").attr("contextpath") + "/findDeliveryChangeLogList.json";
		$j("#tbl-delivery-change-list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("searchForm")}).trigger("reloadGrid");
	});
	
	$j("#reset").click(function(){
		$j("#companyshop").val("");
		$j("#staCode").val("");
		$j("#slipCode").val("");
		$j("#slipCode1").val("");
		$j("#lpCode").val("");
		$j("#trackingNo").val("");
		$j("#newlpCode").val("");
		$j("#newTrackingNo").val("");
		$j("#c1").val("");
		$j("#e1").val("");
	});
	
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#btnShopFormRest").click(function(){
		$j("#shopQueryDialogForm input").val("");
	});
});