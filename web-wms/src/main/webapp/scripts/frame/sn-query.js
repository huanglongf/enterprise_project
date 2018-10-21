$j.extend(loxia.regional['zh-CN'],{
	
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}



$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var cardStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"skuCardStatus"});
	$j("#tbl-orderList").jqGrid({
		datatype: "json",
		colNames: ["ID","SN号/卡号","商品条码","商品编码","商品名称","卡券状态","作业单号","前置单据号","作业类型",
		           "盘点编号"],
		colModel: [{name: "id", index: "id", hidden: true},
		            {name:"sn", index:"sn",width:90,resizable:true},
		            {name:"barcode", index:"barcode",width:80,resizable:true},
		            {name:"skuCode",index:"skuCode",width:80,resizable:true}, 
					{name:"skuName",index:"skuName",width:120,resizable:true},
					{name:"intCardStatus", index:"intCardStatus",width:100,resizable:true,formatter:'select',editoptions:cardStatus},
					{name:"staCode", index:"staCode",width:100,resizable:true},
					{name:"slipCode", index:"slipCode",width:100,resizable:true},
					{name:"staType", index:"staType",width:100,resizable:true,formatter:'select',editoptions:staType},
					{name:"invCkCode", index:"invCkCode",width:100,resizable:true}
				],
		caption: "SN列表",
	   	sortname: "brandName,skuCode",
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
	jumbo.bindTableExportBtn("tbl-orderList",{});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	$j("#search").click(function(){
		var url = baseUrl + "/findPoSoLog.json";
		$j("#tbl-orderList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-orderList",{},url,loxia._ajaxFormToObj("queryForm"));
	});
});