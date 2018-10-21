$j.extend(loxia.regional['zh-CN'],{
	ORDERCODE:"订单号",
	SHOPPINGCODE : "单据号",
	TYPE : "类型",
	STATUS : "状态",
	SYSTEMKEY : "对接平台",
	CREATE_TIME : "创建时间",
	LOG_TIME: "记录时间"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}


$j(document).ready(function (){
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	
	$j("#tbl-wmsOrderList").jqGrid({
		url: loxia.getTimeUrl($j("body").attr("contextpath") + "/queryWmsConfirmOrderQueue.json"),
		datatype: "json",
		//colNames: ["ID",i18("ORDERCODE"),i18("SHOPPINGCODE"),i18("TYPE"),i18("STATUS"),i18("SYSTEMKEY"),i18("CREATE_TIME"),i18("LOG_TIME")],
		colNames:["ID","订单号","订单所属","订单类型","状态","对接平台","创建时间","备注"],
		colModel: [
					{name: "id", index: "id", hidden: true},
					{name:"orderCode",index:"orderCode",width:120,resizable:true},
					{name:"owner",index:"owner",width:120,resizable:true},
					{name:"orderType",index:"orderType",width:80,resizable:true,formatter:'select',editoptions:staType},
					{name:"status",index:"status",width:60,resizable:true},
					{name:"systemKey",index:"systemKey",width:80,resizable:true},
					{name:"createTime", index:"createTime" ,width:140,resizable:true},
					{name:"memo",index:"memo",width:120,resizable:true},
				],
		caption: "直连出库数据列表",
		sortname: "id",
	    multiselect: true,
	    rowNum: jumbo.getPageSize(),
		rowList:[20,40,100,200,500],
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm textarea").val("");
	});
	
	$j("#search").click(function(){
		var url = loxia.getTimeUrl($j("body").attr("contextpath") + "/queryWmsConfirmOrderQueue.json");
		$j("#tbl-wmsOrderList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-wmsOrderList",{},url,loxia._ajaxFormToObj("queryForm"));
	});
	
	// 推送
	$j("#send").click(function(){
		var ids = $j("#tbl-wmsOrderList").jqGrid('getGridParam','selarrrow');
		var postData = {};
		for(var i in ids){
			postData['idList[' + i + ']']=ids[i];
		}
		if (ids && ids.length>0) {
			var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/updateWmsConfirmOrderQueue.json"),postData);
			if (rs && rs.result == "success") {
				var url = loxia.getTimeUrl($j("body").attr("contextpath") + "/queryWmsOrder.json");
				$j("#tbl-wmsOrderList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
			}
			jumbo.showMsg(rs.message);
		} else {
			jumbo.showMsg("请选择需要推送的单据");
		}
	});
	
});