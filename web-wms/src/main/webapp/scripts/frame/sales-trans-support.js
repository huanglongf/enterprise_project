$j.extend(loxia.regional['zh-CN'],{
	APPLY_FORM_SELECT : "请选择作业申请单",
	CODE : "作业单号",
	STATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	SHOPID : "相关店铺",
	LPCODE : "物流服务商简称",
	ISNEEDINVOICE : "是否需要发票",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	COUNT_SEARCH : "待出库列表",
	SKUNAME : "商品名称",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	KEYPROPERTIES : "扩展属性",
	QUANTITY : "计划执行量",
	DETAIL_INFO : "详细信息"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function showinventorydetail(obj){
	var pos = $j(obj).offset(),
		w = $j(obj).width(),
		$ttsum = $j("#div-inventory-detail"),
		currLoc = $j(obj).parents("tr").find("td:eq(1)").text();
	if($ttsum.hasClass("hidden") || currLoc != $ttsum.attr("loc")){		
		$ttsum.removeClass("hidden").attr("loc",currLoc).css({
			position: "absolute",
			left: pos.left + w + 5,
			top: pos.top,
			zIndex: 9999
		});
	}else{
		$ttsum.addClass("hidden").css({
			left: "inherit",
			top: "inherit"
		}).attr("loc","");
	}
}

function showStaLine(tag){
	var tr = $j(tag).parents("tr");
	var id = tr.attr("id");
	$j("#staInfo").addClass("hidden");
	$j("#staLineInfo").removeClass("hidden");
	var sta=$j("#tbl-staList-query").jqGrid("getRowData",id);
	$j("#s_code").html(sta["code"]);
	$j("#s_createTime").html(sta["createTime"]);
	$j("#s_slipCode").html(sta["refSlipCode"]);
	$j("#s_owner").html(sta["shopId"]);
	$j("#s_status").html(tr.find("td[aria-describedby$='intStatus']").html());
	$j("#s_type").html(tr.find("td[aria-describedby$='intType']").html());
	$j("#s_trans").html(tr.find("td[aria-describedby$='lpcode']").html());
	$j("#s_inv").html(tr.find("td[aria-describedby$='isNeedInvoice']").html());
	var postData = {};
	postData["sta.id"] = id;
	$j("#tbl_detail_list").jqGrid('setGridParam',
			{url:window.$j("body").attr("contextpath")+"/findSalesStaLine.json",
			postData:postData,page:1}
		).trigger("reloadGrid");
}


$j(document).ready(function (){
	jumbo.loadShopList("shopId","shopId");
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	
	$j("#tbl-staList-query").jqGrid({
		url:window.$j("body").attr("contextpath")+"/json/staSupportTransPendingList.do",
		datatype: "json",
		//colNames: ["ID","作业单号","相关单据号","作业类型名称","相关店铺","物流服务商简称","是否需要发票","创建时间","计划执行总件数"],
		colNames: ["ID",i18("CODE"),i18("STATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("SHOPID"),i18("LPCODE"),i18("ISNEEDINVOICE"),i18("CREATETIME"),i18("STVTOTAL")],
		colModel: [{name: "id", index: "id", hidden: true},		         
		           {name: "code", index: "code", width: 120,formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"}, resizable: true},
		           {name: "intStatus", index: "intStatus", width: 120, resizable: true,formatter:'select',editoptions:staStatus},
		           {name: "refSlipCode", index: "refSlipCode",width: 120, resizable: true},
		           {name: "intType", index: "intType", width: 100, resizable: true,formatter:'select',editoptions:staType},
		           {name: "shopId", index: "shopId", width: 100, resizable: true},
		           {name: "lpcode", index: "lpcode", width: 120, resizable: true,formatter:'select',editoptions:trasportName},
		           {name: "isNeedInvoice", index: "isNeedInvoice",  width: 100, resizable: true,formatter:'select',editoptions:trueOrFalse},
		           {name: "createTime", index: "createTime", width: 150, resizable: true},
		           {name: "stvTotal", index: "stvTotal", width: 100, resizable: true}],
		caption: i18("COUNT_SEARCH"),//待出库列表
	   	sortname: 'id',
	   	pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
	    multiselect: true,
	    rownumbers:true,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_detail_list").jqGrid({
		datatype: "json",
		//colNames: ["ID","商品名称","条形码","商品编码","扩展属性","计划执行量"],
		colNames: ["ID",i18("SKUNAME"),i18("BARCODE"),i18("JMCODE"),i18("KEYPROPERTIES"),i18("QUANTITY")],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
				   {name: "skuName", index: "skuName", width: 120, resizable: true},
		           {name: "barCode", index: "barCode",width: 120, resizable: true},
		           {name: "jmcode", index: "jmcode", width: 100, resizable: true},
		           {name: "keyProperties", index: "keyProperties", width: 100, resizable: true},
		           {name: "quantity", index: "quantity", width: 120, resizable: true}],
		caption: i18("DETAIL_INFO"),//详细信息
		rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pager",
		height:"auto",
   		sortname: 'id',
     	multiselect: false,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#btn-query").click(function(){
		var postData = loxia._ajaxFormToObj("query-form");
		$j("#tbl-staList-query").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/json/staSupportTransPendingList.do",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	
	
	$j("#reset").click(function(){
		$j("#filterTable input").val("");
		$j("#filterTable select").val("");
	});
	
	$j("#back").click(function(){
		$j("#staInfo").removeClass("hidden");
		$j("#staLineInfo").addClass("hidden");
	});
	
});
