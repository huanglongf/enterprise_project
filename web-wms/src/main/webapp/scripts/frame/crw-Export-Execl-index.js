$j.extend(loxia.regional['zh-CN'],{
});
var $j = jQuery.noConflict();
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

$j(document).ready(function (){
	$j("#tabs").tabs();
	var baseUrl = $j("body").attr("contextpath");
	var staType=loxia.syncXhr(baseUrl + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr(baseUrl + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	/*$j("#dialog_change").dialog({title: "提示信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog_msg").dialog({title: "提示信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog_barcode_error").dialog({title: "提示信息", modal:true, autoOpen: false, width: 400});
	$j("button[action=add]").addClass("hidden");*/
	var shopLikeQuerys = loxia.syncXhrPost(window.$j("body").attr("contextpath") + "/findshoplistbycompanyid.json");
	$j("#shopLikeQuery").append("<option></option>");
	for(var idx in shopLikeQuerys){
		$j("#shopLikeQuery").append("<option value='"+ shopLikeQuerys[idx].code +"'>"+shopLikeQuerys[idx].name+"</option>");
	}
    $j("#shopLikeQuery").flexselect();
    $j("#shopLikeQuery").val("");
   
 
    $j("#tbl_sta_package").jqGrid({
		url:baseUrl + "/getoutboundpackageByStalist.json",
		datatype: "json",
		colNames: ["ID","作业单号","作业类型","状态","店铺","计划发货时间","LOAD KEY","LF orderkey","NIKE单据编号"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"code", index:"code", width:100, resizable:true},
					{name:"intType", index:"type" ,width:80,resizable:true,formatter:'select',editoptions:staType},
					{name:"intStatus",index:"status",width:80,resizable:true,formatter:'select',editoptions:staStatus},
					{name:"owner", index:"sta.owner",width:125,resizable:true},
					{name:"planOutboundTime", index:"planOutboundTime",width:125,resizable:true},
					{name:"slipCode2",index:"sta.slip_code2",width:100,resizable:true},
					{name:"refSlipCode",index:"sta.slip_code",width:100,resizable:true},
					{name:"slipCode1",index:"sta.slip_code1",width:100,resizable:true},
					
				  ],
		caption: "作业单列表",
	   	sortname: 'id',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#tbl_sta_package_page",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl_sta_package",{"intType":"whSTAType","intStatus":"whSTAStatus"},baseUrl + "/getoutboundpackagestalist.json");
	
	
	//查询按钮功能
	$j("#query").click(function(){
		var url = baseUrl + "/getoutboundpackageByStalist.json";
		$j("#tbl_sta_package").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("formQuery")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_sta_package",{"intType":"whSTAType","intStatus":"whSTAStatus"},
			url,loxia._ajaxFormToObj("formQuery"));
	});
	
	$j("#btnReset").click(function(){
		$j("#formQuery input,select").val("");
	});
	
	
	$j("#expDiff").click(function(){
		var url=$j("body").attr("contextpath") + "/warehouse/exportCRW.do?staCmd.startCreateTime1=" + $j("#startCreateTime1").val() + "&staCmd.endCreateTime1=" + $j("#endCreateTime1").val()+"&staCmd.owner="+$j("#shopLikeQuery").val()+"&staCmd.refSlipCode="+$j("#refSlipCode").val()+"&staCmd.slipCode1="+$j("#slip_code1").val()+"&staCmd.slipCode2="+$j("#slip_code2").val();
		$j("#exportFrame").attr("src",url);
		
	});
});
