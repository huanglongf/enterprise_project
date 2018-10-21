$j.extend(loxia.regional['zh-CN'],{
	STA_LIST : "作业单列表",
	STA_LINE_LIST : "作业单明细",
	STA_CODE : "作业单号",
	STA_SLIP_CODE : "相关单据号",
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
	
	DETAIL_QUERY:"操作明细查询",
	
	OUT_WAREHOUSE:"出库",
	IN_WAREHOUSE:"入库",
	SN:"SN号",
	DIRECT:"出入库方向",
	SN_DETAIL:"SN号明细",
	DELIVERYINFO:"物流面单打印中，请等待..."
		
		
		
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function showDetail(obj){
	$j("#detail_tabs").tabs();
	// 
	$j("#tbl-order-detail tr:gt(0)").remove();
	
	$j("#query-order-list").addClass("hidden");
	$j("#order-detail").removeClass("hidden");
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
//	staid = id;
	$j("#searchDetail").attr("staId",id);
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-order-detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/gethistoricalOrderDetailList.json?sta.id=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-order-detail",{},baseUrl + "/gethistoricalOrderDetailList.json",{"sta.id":id});

	$j("#tbl-order-detail_operate").jqGrid('setGridParam',{page:1,url:baseUrl + "/gethistoricalOrderDetailOperateList.json?sta.id=" + id,postData:loxia._ajaxFormToObj("query_detail_form")}).trigger("reloadGrid");
	
	var tbpdata = loxia._ajaxFormToObj("query_detail_form");
	tbpdata["sta.id"]=id;
	jumbo.bindTableExportBtn("tbl-order-detail_operate",{},baseUrl + "/gethistoricalOrderDetailOperateList.json",tbpdata);
	
	var pl=$j("#tbl-orderList").jqGrid("getRowData",id);
	
	$j("#staid").html(id.toString());//获取staid

	
	$j("#createTime").text(pl["createTime"]);
	$j("#finishTime").text(pl["finishTime"]);
	$j("#sta_memo").text(pl["memo"]);
	$j("#code").text(pl["code"]);
	$j("#refSlipCode").text(pl["refSlipCode"]);
	$j("#type").text(tr.find("td[aria-describedby$='intType']").text());
	
	//bin.hu
	$j("#zyCode").html(pl["code"].toString());//获取作业单号code
	$j("#intStatus").html(pl["intStatus"].toString());//获取状态
	$j("#intType").html(pl["intType"].toString());//获取作业类型名称
	$j("#ipCode").html(pl["lpcode"].toString());//获取物流服务
	$j("#pickListId").html(pl["pickListId"].toString());//批次号
	
	
	if(pl["intType"] == 11){
		$j("#exportPoReport").removeClass("hidden");
		$j("#printPoReport").removeClass("hidden");
	}else{
		$j("#exportPoReport").addClass("hidden");
		$j("#printPoReport").addClass("hidden");
	}
	
	
	
	
	$j("#status").text(tr.find("td[aria-describedby$='intStatus']").text());
	$j("#shopId").text(pl["owner"]);
	$j("#lpcode").text(tr.find("td[aria-describedby$='lpcode']").text());
	$j("#trackingNo").text(pl["trackingNo"]);
	$j("#operator").text(pl["operator"]);
	
	// --------------
	$j("#tbl-sn-detail tr:gt(0)").remove();

	$j("#tbl-sn-detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/showsndetail.json?sta.id=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-sn-detail",{},
			baseUrl + "/showsndetail.json",{"sta.id":id});
	
	//查询快递单号fanht
	var transportNos =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTransportNos.do",{"staId":id});
	$j("#trackingNo").text(transportNos.value);
	
	//配货中不可以打印物流面单
	if("配货中" == tr.find("td[aria-describedby$='intStatus']").text()){
		$j("#printDeliveryInfo").addClass("hidden");
	}else{
		$j("#printDeliveryInfo").removeClass("hidden");
	}
}
var isEmsOlOrder = false;
$j(document).ready(function (){
	$j("#detail_tabs").tabs();
	initShopQuery("companyshop","innerShopCode");
	
	$j("#tbl-sn-detail").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKUCODE"),i18("BARCODE"),i18("JMCODE"),i18("SKU_NAME"),i18("KEY_PROPS"),i18("SN"),i18("DIRECT")],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"skuCode",index:"skuCode",width:120,resizable:true},
					{name:"barCode",index:"barCode",width:120,resizable:true},
					{name:"jmcode", index:"jmcode" ,width:120,resizable:true},
					{name:"skuName", index:"skuName",width:120,resizable:true},
					{name:"keyProperties",index:"keyProperties",width:80,resizable:true},
					{name:"sn", index:"sn",width:150,resizable:true},
					{name:"direction", index:"direction",width:120,resizable:true}],
		caption: i18("SN_DETAIL"),
	   	sortname: 'sn',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerSn",
		sortorder: "asc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl-sn-detail").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-sn-detail").jqGrid('getRowData',ids[i]);
				var tra = "";
				if(datas["direction"]=="2"){
					tra= "出库";
				} else if(datas["direction"]=="1") {
					tra = "入库";
				}
				$j("#tbl-sn-detail").jqGrid('setRowData',ids[i],{"direction":tra});
			}
		}
	});
	
	jumbo.loadShopList("companyshop");
	
//	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
//	for(var idx in result){
//		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#select"));
//	}
	var whinfo = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getWarehouseInfo.json");
	if(whinfo.isEmsOlOrder){
		isEmsOlOrder = true;
	}
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-orderList").jqGrid({
		//url:baseUrl + "/gethistoricalOrderList.json",
		datatype: "json",
		//colNames: ["ID","作业单号","相关单据号","作业类型名称","作业单状态","平台店铺名称","物流服务商","快递单号","创建时间","完成时间","操作人","计划执行总数量"],
		colNames: ["ID",i18("STA_CODE"),i18("STA_SLIP_CODE"),i18("STA_TYPE"),i18("STA_STATUS"),i18("OWNER"),"是否锁定","目标店铺",
						    "原始仓库","目标仓库",i18("LPCODE"),i18("TRACKING_NO"),i18("PRODUCT_SIZE"), i18("TRACKING_AND_SKU"),i18("CRAETE_TIME"),
						    i18("FINISH_TIME"),i18("OPERATOR"),"计划执行量","MEMO","批次号"],
		colModel: [
							{name: "id", index: "id", hidden: true},
							{name:"code", index:"code", formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},
							{name:"refSlipCode",index:"refSlipCode",width:80,resizable:true},
							{name:"intType", index:"intType" ,width:70,resizable:true,formatter:'select',editoptions:staType},
							{name:"intStatus",index:"intStatus",width:70,resizable:true,formatter:'select',editoptions:staStatus},
							{name:"owner", index:"owner",width:130,resizable:true},
							{name:"isLocked",index:"isLocked",width:80,resizable:true,formatter:'select',editoptions:trueOrFalse},
							{name:"addiOwner", index:"sta.addiOwner",width:130,resizable:true},
							{name:"mainName", width:130,resizable:true},
							{name:"addiName", index:"sta.addiName",width:130,resizable:true},
							{name:"lpcode", index:"lpcode",width:80,resizable:true,formatter:'select',editoptions:trasportName},
							{name:"trackingNo", index:"tracking_no",width:120,resizable:true},
							{name:"productSize", index:"productSize",width:120,resizable:true},
							{name:"trackingAndSku", index:"trackingAndSku",width:120,resizable:true},
							{name:"createTime",index:"create_time",width:130,resizable:true},
							{name:"finishTime",index:"finish_time",width:130,resizable:true},
							{name:"operator", index:"operator",width:100,resizable:true},
							{name:"skuQty", index:"skuQty",width:100,resizable:true},
							{name:"memo", index:"memo",width:100,hidden:true},
							{name:"pickListId", index:"pickListId",width:100,hidden:true}//批次号bin.hu
					 	],
		caption: i18("STA_LIST"),
	   	sortname: 'data.create_time',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl-orderList",{"intType":"whSTAType","intStatus":"whSTAStatus"});
	$j("#tbl-order-detail").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKUCODE"),i18("BARCODE"),i18("JMCODE"),i18("SKU_NAME"),i18("KEY_PROPS"),i18("PLAN_QTY"),i18("COMFIRMED_QTY")],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"skuCode",index:"skuCode",width:80,resizable:true},
					{name:"barCode",index:"barCode",width:80,resizable:true},
					{name:"jmcode", index:"jmcode" ,width:80,resizable:true},
					{name:"skuName", index:"skuName",width:120,resizable:true},
					{name:"keyProperties",index:"keyProperties",width:80,resizable:true},
					{name:"quantity", index:"quantity",width:80,resizable:true},
					{name:"completeQuantity", index:"completeQuantity",width:120,resizable:true}],
		caption: i18("STA_LINE_LIST"),
	   	sortname: 'ID',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerDetail",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0"}
	});
	
	$j("#tbl-order-detail_operate").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKUCODE"),i18("BARCODE"),i18("SKU_NAME"),i18("OWNER"),i18("COMFIRMED_QTY"),i18("DIRECTION"),i18("TYPE_NAME"),i18("LOCATION_CODE"),i18("INV_STATUS"),i18("CRAETE_TIME"),i18("FINISH_TIME"),i18("CREATER")],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"skuCode",index:"skuCode",width:80,resizable:true},
					{name:"barCode",index:"barCode",width:80,resizable:true},
					{name:"skuName", index:"skuName",width:120,resizable:true},
					{name:"owner", index:"owner" ,width:80,resizable:true},
					{name:"quantity", index:"quantity",width:50,resizable:true},
					{name:"directionInt",index:"directionInt",width:50,resizable:true},
					{name:"typeName",index:"typeName",width:80,resizable:true},
					{name:"locationCode",index:"locationCode",width:80,resizable:true},
					{name:"intInvstatusName", index:"intInvstatusName",width:80,resizable:true},
					{name:"createDate",index:"createDate",width:130,resizable:true},
					{name:"finishDate",index:"finishDate",width:130,resizable:true},
					{name:"creater", index:"creater",width:80,resizable:true}],
		caption: i18("DETAIL_QUERY"),
	   	sortname: 'ID',
	    multiselect: false,
	    height:"auto",
	    width:"800px",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerDetail_operate",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl-order-detail_operate").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-order-detail_operate").jqGrid('getRowData',ids[i]);
				var tra = i18("OUT_WAREHOUSE");
				if(datas["directionInt"]=="1"){
					tra= i18("IN_WAREHOUSE");
				}
				$j("#tbl-order-detail_operate").jqGrid('setRowData',ids[i],{"directionInt":tra});
			}
		}
	});
	
	$j("#exportPoReport").click(function(){
		$j("#upload").attr("src",$j("body").attr("contextpath") + "/warehouse/exportPoConfirmReport.do?sta.id="+$j("#searchDetail").attr("staId"));
	});
	
	// 打印装箱清单：
	$j("#printPoReport").click(function(){
		loxia.lockPage();
		var url = $j("body").attr("contextpath") + "/printPoConfirmReport.json?sta.id="+$j("#searchDetail").attr("staId");
		printBZ(loxia.encodeUrl(url),true);				
		loxia.unlockPage();
	});
		
	$j("#back").click(function(){
		$j("#query-order-list").removeClass("hidden");
		$j("#order-detail").addClass("hidden");
	});
	
	//作业单查询-打印物流面单bin.hu
	$j("#printDeliveryInfo").click(function(){
		var staid = $j("#staid").text();//ID
		var zyCode = $j("#zyCode").text();//作业单号
		var intStatus = $j("#intStatus").text();//状态
		var intType = $j("#intType").text();//作业类型名称
		var ipCode = $j("#ipCode").text();//物流服务
		var pickListId = $j("#pickListId").text();//批次号ID
//		alert("statid："+staid+" zyCode："+zyCode+" intStatus："+intStatus+" intType："+intType+" ipCode："+ipCode+" pickListId："+pickListId);
		loxia.lockPage();
		jumbo.showMsg(i18("DELIVERYINFO"));
//		if(isEmsOlOrder && ('EMS' == ipCode || 'EMSCOD' == ipCode)){
//			jumbo.emsprint(zyCode,pickListId);
//		}else{
			var url = $j("body").attr("contextpath") + "/printSingleOrderDetail.json?stap.id=" + staid;
			printBZ(loxia.encodeUrl(url),true);
//		}
		loxia.unlockPage();
	});
	 
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
		$j("#isMorePackage").attr("checked",false);
	});
	
	$j("#search").click(function(){
		//是否分包 fanht
		if($j("#isMorePackage").attr("checked")){
			$j("#morePackageValue").val(1)
		}else{
			$j("#morePackageValue").val(0)
		}
		var url = baseUrl + "/gethistoricalOrderList.json?noLike=true";
		$j("#tbl-orderList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-orderList",{"intType":"whSTAType","intStatus":"whSTAStatus"},url,loxia._ajaxFormToObj("queryForm"));
	});
	
	$j("#searchDetail").click(function(){
		var id = $j(this).attr("staId");
		$j("#tbl-order-detail_operate").jqGrid('setGridParam',{page:1,url:baseUrl + "/gethistoricalOrderDetailOperateList.json?sta.id=" + id,postData:loxia._ajaxFormToObj("query_detail_form")}).trigger("reloadGrid");
		var vdata = loxia._ajaxFormToObj("query_detail_form")
		vdata["sta.id"]=id;
		jumbo.bindTableExportBtn("tbl-order-detail_operate",{},baseUrl + "/gethistoricalOrderDetailOperateList.json",vdata);
	});
	
	$j("#resetDetail").click(function(){
		$j("#query_detail_form input").val("");
		$j("#query_detail_form select").val("");
	});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});

	$j("#btnShopFormRest").click(function(){
		$j("#shopQueryDialogForm input").val("");
	});
	
});