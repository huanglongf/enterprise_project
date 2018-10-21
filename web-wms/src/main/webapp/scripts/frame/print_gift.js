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
	TYPE:"类型",
	MEMO:"特殊信息备注",
	SANCARDCODE:"特殊处理",
	OPERACTION:"操作",
	PRINGT:"打印"
		
		
		
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function giftprint(obj){
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	var url = $j("body").attr("contextpath") + "/warehouse/printgift.json?wid=" + id;
	printBZ(loxia.encodeUrl(url),true);
	
};
function showDetail(obj){
	$j("#detail_tabs").tabs();
	$j("#tbl-order-detail tr:gt(0)").remove();
	
	$j("#query-order-list").addClass("hidden");
	$j("#order-detail").removeClass("hidden");
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	
	$j("#searchDetail").attr("staId",id);
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-order-detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/getPrintGiftLine.json?sta.id=" + id}).trigger("reloadGrid");
	
	var pl=$j("#tbl-orderList").jqGrid("getRowData",id);
	$j("#createTime").text(pl["createTime"]);
	$j("#finishTime").text(pl["finishTime"]);
	$j("#sta_memo").text(pl["memo"]);
	$j("#code").text(pl["code"]);
	$j("#refSlipCode").text(pl["refSlipCode"]);
	$j("#country").text(pl["country"]);
	$j("#city").text(pl["city"]);
	$j("#district").text(pl["district"]);
	$j("#address").text(pl["address"]);
	$j("#receiver").text(pl["receiver"]);
	$j("#telephone").text(pl["telephone"]);
	$j("#type").text(tr.find("td[aria-describedby$='intType']").text());
}

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
	
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#select"));
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
		colNames: ["ID","country","city","district","address","receiver","telephone",i18("STA_CODE"),i18("STA_SLIP_CODE"),i18("STA_TYPE"),i18("STA_STATUS"),i18("OWNER"),"是否锁定","目标店铺","原始仓库","目标仓库",i18("LPCODE"),i18("TRACKING_NO"),i18("PRODUCT_SIZE"), i18("TRACKING_AND_SKU"),i18("CRAETE_TIME"),i18("FINISH_TIME"),i18("OPERATOR"),"计划执行量","MEMO"],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name: "country", index: "country", hidden: true},
		           	{name: "city", index: "city", hidden: true},
		           	{name: "district", index: "district", hidden: true},
		           	{name: "address", index: "address", hidden: true},
		           	{name: "receiver", index: "receiver", hidden: true},
		           	{name: "telephone", index: "telephone", hidden: true},
		           	
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
					{name:"memo", index:"memo",width:100,hidden:true}],
		caption: i18("STA_LIST"),
	   	sortname: 'sta.create_time',
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
		colNames: ["ID",i18("TYPE"),i18("MEMO"),i18("SANCARDCODE"),i18("OPERATION")],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"TYPE",index:"type",width:80,resizable:true},
					{name:"memo",index:"memo",width:80,resizable:true},
					{name:"sanCardCode", index:"sanCardCode" ,width:80,resizable:true},
					{name: "idForBtn", width: 80,resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("PRINGT"), onclick:"giftprint(this)"}}}],
		caption: i18("STA_LINE_LIST"),
	   	sortname: 'gift.ID',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerDetail",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
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
	 
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
		$j("#isMorePackage").attr("checked",false);
	});
	
	$j("#search").click(function(){
		//是否分包 fanht
		var url = baseUrl + "/getPrintGift.json";
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
