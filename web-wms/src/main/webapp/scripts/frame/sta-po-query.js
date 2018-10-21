$j.extend(loxia.regional['zh-CN'],{
	STA_LIST : "作业单列表",
	STA_LINE_LIST : "作业单明细",
	STA_CODE : "作业单号",
	STA_SLIP_CODE : "相关单据号",
	SLIP_CODE1:"相关单据号1",
	SLIP_CODE2:"相关单据号2",
	STA_TYPE : "作业类型名称",
	STA_STATUS : "作业单状态",
	OWNER : "平台店铺名称",
	LPCODE: "物流服务商",
	TRACKING_NO : "快递单号",
	CRAETE_TIME : "创建时间",
	FINISH_TIME : "完成时间",
	OPERATOR : "操作人",
	PLAN_COUNT : "计划执行总数量",
	
	SKUCODE : "SKU编码",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	SKU_NAME : "商品名称",
	KEY_PROPS : "扩展属性",
	PLAN_QTY : "计划量",
	COMFIRMED_QTY : "执行量",
	SKU_COST : "库存成本",
	TRACKING_AND_SKU : "快递单号及耗材",
	PRODUCT_SIZE : "商品大小",
	
	DIRECTION:"作用方向",
	TYPE_NAME:"作业类型",
	LOCATION_CODE:"库位编码",
	INV_STATUS:"库存状态",
	CREATER:"创建人",
		
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
function showDetail(obj){
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	var baseUrl = $j("body").attr("contextpath");
	$j("#detail_tabs").removeClass("hidden");
	$j("#query-poOrder-list").addClass("hidden");
	var postData ={};
	postData["sta.id"]=id;
	$j("#tbl-po-order-detail").jqGrid('setGridParam',{url:baseUrl + "/getPoLineStaList.json",page:1,postData:postData}).trigger("reloadGrid");
	$j("#tbl-po-order-detail2").jqGrid('setGridParam',{url:baseUrl + "/getPoLineStaList.json",page:1,postData:postData}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-po-order-detail",{},baseUrl + "/getPoLineStaList.json",{"sta.id":id});
	jumbo.bindTableExportBtn("tbl-po-order-detail2",{},baseUrl + "/getPoLineStaList.json",{"sta.id":id});
}

$j(document).ready(function (){
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	initShopQuery("companyshop","innerShopCode");
	$j("#detail_tabs").tabs();
	jumbo.loadShopList("companyshop");
	$j("#tbl-poOrderList").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("STA_CODE"),i18("STA_SLIP_CODE"),i18("SLIP_CODE1"),i18("SLIP_CODE2"),"单据类型","仓库","店铺","状态"],
		colModel: [
							{name: "id", index: "id", hidden: true},
							{name:"code", index:"code", formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},
							{name:"refSlipCode",index:"refSlipCode",width:100,resizable:true},
							{name:"slipCode1",index:"slipCode1",width:100,resizable:true},
							{name:"slipCode2",index:"slipCode2",width:100,resizable:true},
							{name:"intType", index:"intType" ,width:70,resizable:true,formatter:'select',editoptions:staType},
							{name:"mainName", width:130,resizable:true},
							{name:"owner", index:"owner",width:130,resizable:true},
							{name:"intStatus",index:"intStatus",width:70,resizable:true,formatter:'select',editoptions:staStatus}
					 	],
		caption: "PO按箱收货明细",
	   	sortname: 'data.create_time',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
   		width:1000,
		jsonReader: { repeatitems : false, id: "0" },
	});
	jumbo.bindTableExportBtn("tbl-poOrderList");
	
	$j("#tbl-po-order-detail").jqGrid({
		datatype: "json",
		colNames: ["ID","SKU编码","条码","货号","色号","计划数量","执行数量"],
		colModel: [
							{name: "id", index: "id", hidden: true},
							{name:"skuCode", index:"skuCode",  width:100, resizable:true},
							{name:"barCode", index:"barCode",  width:100, resizable:true},
							{name:"supplierCode", index:"supplierCode",  width:100, resizable:true},
							{name:"color", index:"color",  width:100, resizable:true},
							{name:"quantity",index:"quantity",width:100,resizable:true},
							{name:"completeQuantity",index:"completeQuantity",width:100,resizable:true}
					 	],
		caption: "PO按箱收货商品明细",
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
		pager:"#pagerDetail",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
   		width:600,
		jsonReader: { repeatitems : false, id: "0" },
	});
	//jumbo.bindTableExportBtn("tbl-po-order-detail");
	
	$j("#tbl-po-order-detail2").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("STA_CODE"),i18("STA_SLIP_CODE"),"SKU编码","计划数量","执行数量"],
		colModel: [
							{name: "id", index: "id", hidden: true},
							{name:"code", index:"code",  width:100, resizable:true},
							{name:"slipCode",index:"slipCode",width:100,resizable:true},
							{name:"skuCode",index:"skuCode",width:100,resizable:true},
							{name:"quantity",index:"quantity",width:100,resizable:true},
							{name:"completeQuantity",index:"completeQuantity",width:100,resizable:true}
					 	],
		caption: "PO按箱收货箱明细",
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pagerDetail2",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
   		width:600,
		jsonReader: {repeatitems : false, id: "0" },
	});
	//jumbo.bindTableExportBtn("tbl-po-order-detail2");
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	$j("#search").click(function(){
		var url =null;
		var baseUrl = $j("body").attr("contextpath");
		url= baseUrl + "/getPoStaList.json";
		var postData = loxia._ajaxFormToObj("queryForm");
		$j("#tbl-poOrderList").jqGrid('setGridParam',{url:url,page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-poOrderList",{"intType":"whSTAType","intStatus":"whSTAStatus"},url,loxia._ajaxFormToObj("queryForm"));

	});
	
	$j("#back").click(function(){
		$j("#query-poOrder-list").removeClass("hidden");
		$j("#detail_tabs").addClass("hidden");
	});
});
