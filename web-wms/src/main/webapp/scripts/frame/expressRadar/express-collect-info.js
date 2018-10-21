var extBarcode = null;
var outboundSn = null;
var getValues = null;
$j.extend(loxia.regional['zh-CN'],{
	
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}


$j(document).ready(function (){
	
	
	// 初始化物流商信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	
		
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("form_query");  
		postData['sta.intType'] = 41;
		var url = $j("body").attr("contextpath")+"/json/expressOrderQuery.json";
		$j("#tbl-inbound-purchase").jqGrid('setGridParam',{
			url:url,postData:postData}).trigger("reloadGrid");
	});
		
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val(0);
	});
	
		$j("#tbl-inbound-purchase").jqGrid({
			url:$j("body").attr("contextpath")+"/json/expressOrderQuery.json",
			datatype: "json",
			colNames: ["ID","省","待揽收","已揽收","中转中","异常件","合计"],
			colModel: [
				{name: "id", index: "id", hidden: true},
				{name:"wname",index: "wname",width:120,resizable:true},
				{name :"code",index:"lpcode",formatter:"linkFmatter",formatoptions:{onclick:"cityDetail"},width:100,resizable:true},
				{name:"refSlipCode", index:"refSlipCode" ,formatter:"linkFmatter",formatoptions:{onclick:"logisticsDetail"},width:100,resizable:true},
				{name: "intStatus", index: "intStatus", formatter:"linkFmatter",formatoptions:{onclick:"logisticsDetail"},width:100,resizable:true},
				{name:"shopId", index:"shopId", wformatter:"linkFmatter",formatoptions:{onclick:"logisticsDetail"},width:100,resizable:true},
				{name: "lpcode", index: "lpcode",formatter:"linkFmatter",formatoptions:{onclick:"logisticsDetail"},width:100,resizable:true},
			           ],
			caption: '目的地快递部分状态汇总列表',
		   	sortname: 'id',
		   	sortorder: "desc",
		   	multiselect: true,
		    rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
		   	pager:"#pager",
		   	height:jumbo.getHeight(),
			viewrecords: true,
	   		rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		  
			});

});

function cityDetail(row){
	$j("#div-sta-list").addClass("hidden");
	$j("#div-logistics-detail").addClass("hidden");
	$j("#div-exception-detail").addClass("hidden");
	$j("#div-city-detail").removeClass("hidden");
	
	$j("#tb2-city-detail").jqGrid({
		url:$j("body").attr("contextpath")+"/json/expressOrderQuery.json",
		datatype: "json",
		colNames: ["ID","市","待揽收","已揽收","中转中","异常件","合计"],
		colModel: [
			{name: "id", index: "id", hidden: true},
			{name:"wname",index: "wname",width:120,resizable:true},
			{name :"code",index:"lpcode",formatter:"linkFmatter",formatoptions:{onclick:"logisticsDetail"},width:100,resizable:true},
			{name:"refSlipCode", index:"refSlipCode" ,formatter:"linkFmatter",formatoptions:{onclick:"logisticsDetail"},width:100,resizable:true},
			{name: "intStatus", index: "intStatus", formatter:"linkFmatter",formatoptions:{onclick:"logisticsDetail"},width:100,resizable:true},
			{name:"shopId", index:"shopId", wformatter:"linkFmatter",formatoptions:{onclick:"logisticsDetail"},width:100,resizable:true},
			{name: "lpcode", index: "lpcode",formatter:"linkFmatter",formatoptions:{onclick:"logisticsDetail"},width:100,resizable:true},
		           ],
		caption: '目的地快递部分状态汇总列表',
	   	sortname: 'id',
	   	sortorder: "desc",
	   	multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	  
		});
	
}
function logisticsDetail(row){
	$j("#div-sta-list").addClass("hidden");
	$j("#div-city-detail").addClass("hidden");
	$j("#div-exception-detail").addClass("hidden");
	$j("#div-logistics-detail").removeClass("hidden");
	
	$j("#tb3-logistics-detail").jqGrid({
		url:$j("body").attr("contextpath")+"/json/expressOrderQuery.json",
		datatype: "json",
		colNames: ["ID","物流服务商","吴江仓","广州仓","香港仓","合计"],
		colModel: [
			{name: "id", index: "id", hidden: true},
			{name:"wname",index: "wname",width:120,resizable:true},
			{name :"code",index:"lpcode",formatter:"linkFmatter",formatoptions:{onclick:"exceptionDetail"},width:100,resizable:true},
			{name:"refSlipCode", index:"refSlipCode" ,formatter:"linkFmatter",formatoptions:{onclick:"exceptionDetail"},width:100,resizable:true},
			{name: "intStatus", index: "intStatus", formatter:"linkFmatter",formatoptions:{onclick:"exceptionDetail"},width:100,resizable:true},
			{name: "lpcode", index: "lpcode",formatter:"linkFmatter",formatoptions:{onclick:"exceptionDetail"},width:100,resizable:true},
		           ],
		caption: '物流服务商统计明细列表',
	   	sortname: 'id',
	   	sortorder: "desc",
	   	multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	  
		});
	
}
function exceptionDetail(row){
	$j("#div-sta-list").addClass("hidden");
	$j("#div-logistics-detail").addClass("hidden");
	$j("#div-city-detail").addClass("hidden");
	$j("#div-exception-detail").removeClass("hidden");
	
	$j("#tb4-exception-detail").jqGrid({
		url:$j("body").attr("contextpath")+"/json/expressOrderQuery.json",
		datatype: "json",
		colNames: ["ID","物流服务商","揽件超时","配送超时","客户原因","送货上门变自提","拒收","货损","货差","合计"],
		colModel: [
			{name: "id", index: "id", hidden: true},
			{name:"wname",index: "wname",width:120,resizable:true},
			{name :"code",index:"lpcode",formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"},width:100,resizable:true},
			{name:"refSlipCode", index:"refSlipCode" ,formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"},width:100,resizable:true},
			{name: "intStatus", index: "intStatus", formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"},width:100,resizable:true},
			{name:"shopId", index:"shopId", formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"},width:100,resizable:true},
			{name:"shopId", index:"shopId", formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"},width:100,resizable:true},
			{name:"shopId", index:"shopId", formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"},width:100,resizable:true},
			{name:"shopId", index:"shopId", formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"},width:100,resizable:true},
			{name: "lpcode", index: "lpcode",formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"},width:100,resizable:true},
		           ],
		caption: '异常件统计明细列表',
	   	sortname: 'id',
	   	sortorder: "desc",
	   	multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	  
		});
	
}