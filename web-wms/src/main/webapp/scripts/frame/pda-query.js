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
	
	DETAIL_QUERY:"操作明细查询",
	
	OUT_WAREHOUSE:"出库",
	IN_WAREHOUSE:"入库",
	SN:"SN号",
	DIRECT:"出入库方向",
	SN_DETAIL:"SN号明细",
	DELIVERYINFO:"物流面单打印中，请等待...",
	OPERATE_LOG:"操作日志",
	OPERATE_LOG_LIST:"操作日志列表"
		
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
	//$j("#searchDetail").attr("staId",id);
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-order-detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/getPdaDetail.json?sta.id=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-order-detail",{},baseUrl + "/getPdaDetail.json",{"sta.id":id});

	
	
	$j("#tbl-order-detail_operate").jqGrid('setGridParam',{page:1,url:baseUrl + "/getPdaSnDetail.json?sta.id=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-order-detail_operate",{},baseUrl + "/getPdaSnDetail.json",{"sta.id":id});
	
	var pl=$j("#tbl-orderList").jqGrid("getRowData",id);
	
	$j("#staid").html(id.toString());//获取staid

	$j("#shopId").text(pl["owner"]);
	$j("#createTime").text(pl["createTime"]);
	$j("#finishTime").text(pl["finishTime"]);
	$j("#sta_memo").text(pl["memo"]);
	$j("#code").text(pl["code"]);
	$j("#refSlipCode").text(pl["refSlipCode"]);
	$j("#slipCode1").text(pl["slipCode1"]);
	$j("#type").text(tr.find("td[aria-describedby$='intType']").text());
	
	
	//bin.hu
	/*$j("#zyCode").html(pl["code"].toString());//获取作业单号code
	$j("#intStatus").html(pl["intStatus"].toString());//获取状态
	$j("#intType").html(pl["intType"].toString());//获取作业类型名称
	$j("#ipCode").html(pl["lpcode"].toString());//获取物流服务
	$j("#pickListId").html(pl["pickListId"].toString());//批次号
*/	

	$j("#status").text(tr.find("td[aria-describedby$='intStatus']").text());
	$j("#shopId").text(pl["owner"]);
	$j("#qty1").text(pl["quantity"]);
	$j("#qty2").text(pl["qty"]);
	$j("#qty3").text(pl["qty2"]);
	
	// --------------
	$j("#tbl-sn-detail tr:gt(0)").remove();

	$j("#tbl-sn-detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/showShelvesDetail.json?sta.id=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-sn-detail",{},
			baseUrl + "/showShelvesDetail.json",{"sta.id":id});
	

	$j("#tbl-operate-log tr:gt(0)").remove();
	$j("#tbl-operate-log").jqGrid('setGridParam',{page:1,url:baseUrl + "/showShelvesSNDetail.json?sta.id="+ id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-operate-log",{},
			baseUrl + "/showShelvesSNDetail.json",{"sta.id":id});
}
var isEmsOlOrder = false;
$j(document).ready(function (){
	$j("#detail_tabs").tabs();
	initShopQuery("companyshop","innerShopCode");
	
	$j("#tbl-sn-detail").jqGrid({
		datatype: "json",
		colNames: ["箱号","SKU编码","SKU条码","商品名称","货号","关键属性","库存状态","生产日期","失效日期","上架数量","上架库位编码","操作人","操作时间"],
		colModel: [
                    {name:"cartonCode",index:"cartonCode",width:80,resizable:true},
                    {name:"skuCode",index:"skuCode",width:80,resizable:true},
                    {name:"barCode",index:"barCode",width:80,resizable:true},
                    {name:"name",index:"name",width:80,resizable:true},
                    {name:"supplierCode",index:"supplierCode",width:80,resizable:true},
                    {name:"keyProperties",index:"keyProperties",width:80,resizable:true},
		           	{name:"statusName",index:"statusName",width:80,resizable:true},
					{name:"productionDate",index:"productionDate",width:80,resizable:true},
					{name:"expDate", index:"expDate" ,width:80,resizable:true},
					{name:"qty", index:"qty",width:80,resizable:true},
					{name:"locationCode", index:"locationCode",width:80,resizable:true},
					{name:"userName",index:"userName",width:80,resizable:true},
					{name:"createTime", index:"createTime",width:150,resizable:true}],
		caption:"上架明细",
	   	sortname: 'create_time',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerSn",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		
	});
	
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	$j("#tbl-operate-log").jqGrid({
		datatype: "json",
		colNames: ["箱号","SKU编码","SKU条码","商品名称","货号","关键属性","库存状态","生产日期","失效日期","上架数量","残次类型","残次原因","残次条码","上架库位编码","操作人","操作时间"],
		colModel: [
                    {name:"cartonCode",index:"cartonCode",width:80,resizable:true},
                    {name:"skuCode",index:"skuCode",width:80,resizable:true},
                    {name:"barCode",index:"barCode",width:80,resizable:true},
                    {name:"name",index:"name",width:80,resizable:true},
                    {name:"supplierCode",index:"supplierCode",width:80,resizable:true},
                    {name:"keyProperties",index:"keyProperties",width:80,resizable:true},
		           	{name:"statusName",index:"statusName",width:80,resizable:true},
					{name:"productionDate",index:"productionDate",width:80,resizable:true},
					{name:"expDate", index:"expDate" ,width:80,resizable:true},
					{name:"qty", index:"qty",width:80,resizable:true},
					{name:"dmgType", index:"dmgType",width:80,resizable:true},
					{name:"dmgReason", index:"dmgReason",width:80,resizable:true},
					{name:"dmgCode", index:"dmgCode",width:80,resizable:true},
					{name:"locationCode", index:"locationCode",width:80,resizable:true},
					{name:"userName",index:"userName",width:80,resizable:true},
					{name:"createTime", index:"createTime",width:150,resizable:true}],
		caption: "上架SN和残次明细",
	    multiselect: false,
	    sortname: 'create_time',
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerOperateLog",
		viewrecords: true,
   		rownumbers:true,
   		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" },
	});

	jumbo.loadShopList("companyshop");
	
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#select"));
	}
	var whinfo = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getWarehouseInfo.json");
	if(whinfo.isEmsOlOrder){
		isEmsOlOrder = true;
	}
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-orderList").jqGrid({
		//url:baseUrl + "/gethistoricalOrderList.json",
		datatype: "json",
		//colNames: ["ID","作业单号","相关单据号","作业类型名称","作业单状态","平台店铺名称","物流服务商","快递单号","创建时间","完成时间","操作人","计划执行总数量"],
		colNames: ["ID","作业单号","相关单据号","辅助相关单据号","店铺","作业类型","作业状态","计划收货数量","已收货数量","已上架数量","作业开始时间","作业结束时间"],
		colModel: [
                            {name: "id", index: "id", hidden: true},
							{name:"code", index:"code", formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},
							{name:"refSlipCode",index:"refSlipCode",width:100,resizable:true},
							{name:"slipCode1",index:"slipCode1",width:100,resizable:true},
							{name:"owner", index:"owner",width:130,resizable:true},
							{name:"intType", index:"intType" ,width:70,resizable:true,formatter:'select',editoptions:staType},
							{name:"intStatus",index:"intStatus",width:70,resizable:true,formatter:'select',editoptions:staStatus},
							{name:"quantity", index:"quantity",width:70,resizable:true},
							{name: "qty", index: "qty", width: 120, resizable: true},
							{name:"qty2",index:"qty2",width:80,resizable:true},
							{name:"createTime",index:"create_time",width:130,resizable:true},
							{name:"finishTime",index:"finish_time",width:130,resizable:true}
					 	],
		caption: "PDA收货上架作业查询",
	   	sortname: 'a.create_time',
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
		//url:baseUrl + "/getPdaDetail.json",
		datatype: "json",
		colNames: ["箱号","SKU编码","SKU条码","商品名称","货号","关键属性","库存状态","生产日期","失效日期","收货数量","操作人","操作时间","操作"],
		colModel: [
                    {name:"cartonCode",index:"cartonCode",width:80,resizable:true},
                    {name:"skuCode",index:"skuCode",width:80,resizable:true},
                    {name:"barCode",index:"barCode",width:80,resizable:true},
                    {name:"name",index:"name",width:80,resizable:true},
                    {name:"supplierCode",index:"supplierCode",width:80,resizable:true},
                    {name:"keyProperties",index:"keyProperties",width:80,resizable:true},
		           	{name:"statusName",index:"statusName",width:80,resizable:true},
					{name:"productionDate",index:"productionDate",width:80,resizable:true},
					{name:"expDate", index:"expDate" ,width:80,resizable:true},
					{name:"qty", index:"qty",width:80,resizable:true},
					{name:"userName",index:"userName",width:80,resizable:true},
					{name:"createTime", index:"createTime",width:150,resizable:true},
					{name:"idForBtn", index:"idForBtn", formatter:"buttonFmatter",formatoptions:{"buttons":{label:"补打货箱标签", onclick:"printDeliveryInfo()"}}, width:100, resizable:true}],

		caption: "收货明细",
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
		colNames: ["箱号","SKU编码","SKU条码","商品名称","货号","关键属性","库存状态","生产日期","失效日期","收货数量","残次类型","残次原因","残次条码","SN号","操作人","操作时间","操作"],
		colModel: [
                    {name:"cartonCode",index:"cartonCode",width:80,resizable:true},
                    {name:"skuCode",index:"skuCode",width:60,resizable:true},
                    {name:"barCode",index:"barCode",width:60,resizable:true},
                    {name:"name",index:"name",width:60,resizable:true},
                    {name:"supplierCode",index:"supplierCode",width:60,resizable:true},
                    {name:"keyProperties",index:"keyProperties",width:60,resizable:true},
		           	{name:"statusName",index:"statusName",width:50,resizable:true},
					{name:"productionDate",index:"productionDate",width:80,resizable:true},
					{name:"expDate", index:"expDate" ,width:100,resizable:true},
					{name:"qty", index:"qty",width:30,resizable:true},
					{name:"dmgType",index:"dmgType",width:80,resizable:true},
					{name:"dmgReason",index:"dmgReason",width:80,resizable:true},
					{name:"dmgCode",index:"dmgCode",width:80,resizable:true},
					{name:"sn",index:"sn",width:80,resizable:true},
					{name:"userName",index:"userName",width:80,resizable:true},
					{name:"createTime", index:"createTime",width:140,resizable:true},
					{name:"idForBtn", index:"idForBtn", formatter:"buttonFmatter",formatoptions:{"buttons":{label:"补打货箱标签", onclick:"printDeliveryInfo()"}}, width:100, resizable:true}],
		caption: "收货SN和残次明细",
	   	sortname: 'qty',
	    multiselect: false,
	    height:"auto",
	    width:"1500px",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerDetail_operate",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
	});
	
//	$j("#exportPoReport").click(function(){
//		$j("#upload").attr("src",$j("body").attr("contextpath") + "/warehouse/exportPoConfirmReport.do?sta.id="+$j("#searchDetail").attr("staId"));
//	});
//	
//	// 打印装箱清单：
//	$j("#printPoReport").click(function(){
//		loxia.lockPage();
//		var url = $j("body").attr("contextpath") + "/printPoConfirmReport.json?sta.id="+$j("#searchDetail").attr("staId");
//		printBZ(loxia.encodeUrl(url),true);				
//		loxia.unlockPage();
//	});
		
	$j("#back").click(function(){
		$j("#query-order-list").removeClass("hidden");
		$j("#order-detail").addClass("hidden");
	});
	
//	//作业单查询-打印物流面单bin.hu
//	$j("#printDeliveryInfo").click(function(){
//		var staid = $j("#staid").text();//ID
//		var zyCode = $j("#zyCode").text();//作业单号
//		var intStatus = $j("#intStatus").text();//状态
//		var intType = $j("#intType").text();//作业类型名称
//		var ipCode = $j("#ipCode").text();//物流服务
//		var pickListId = $j("#pickListId").text();//批次号ID
////		alert("statid："+staid+" zyCode："+zyCode+" intStatus："+intStatus+" intType："+intType+" ipCode："+ipCode+" pickListId："+pickListId);
//		loxia.lockPage();
//		jumbo.showMsg(i18("DELIVERYINFO"));
//		if(isEmsOlOrder && ('EMS' == ipCode || 'EMSCOD' == ipCode)){
//			jumbo.emsprint(zyCode,pickListId);
//		}else{
//			var url = $j("body").attr("contextpath") + "/printSingleOrderDetail.json?stap.id=" + staid;
//			printBZ(loxia.encodeUrl(url),true);
//		}
//		loxia.unlockPage();
//	});
	 
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
		$j("#isMorePackage").attr("checked",false);
		$j("#isMergeInt").attr("checked",false);
		$j("#isQueryHis").attr("checked",false);
		$j("#isShowMerge").attr("checked",false);
	});
	
	$j("#printBoxTag").click(function(){
		var staId=$j("#staid").html();
		loxia.lockPage();
		jumbo.showMsg("货箱条码打印");
		var url = $j("body").attr("contextpath") + "/printContainerCode.json?type=print&sta.id=" + staId;
		printBZ(loxia.encodeUrl(url),false);
		loxia.unlockPage();
	});
	
	$j("#printSlipCode").click(function(){
		var staId=$j("#staid").html();
		loxia.lockPage();
		jumbo.showMsg("作业单号打印");
		var url = $j("body").attr("contextpath") + "/printAutoSlipCode.json?sta.id=" + staId;
		printBZ(loxia.encodeUrl(url),false);
		loxia.unlockPage();
	});
	
	
	
	$j("#search").click(function(){
		var url = baseUrl + "/pdaQueryOrderList.json";
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
	
	//初始化O2O目的地编码
	var otoResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getallshopstoreforoption.do");
	for(var idx in otoResult){
		$j("<option value='" + otoResult[idx].code + "'>"+ otoResult[idx].name +"</option>").appendTo($j("#selToLocation"));
	}
	
	
});

function printDeliveryInfo(){
	var staId=$j("#staid").html();
	loxia.lockPage();
	jumbo.showMsg("货箱条码打印");
	var url = $j("body").attr("contextpath") + "/printContainerCode.json?type=print&sta.id=" + staId;
	printBZ(loxia.encodeUrl(url),false);
	loxia.unlockPage();
};
