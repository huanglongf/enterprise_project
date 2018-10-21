$j.extend(loxia.regional['zh-CN'],{
	PROVINCE : "省",
	STAY_LANSHOU : "待揽收",
	HAS_LANSHOU : "已揽收",
	IN_THE_TRANSIT : "中转中 ",
	EXCEPTION_PIECE : "异常件",
	TOTAL : "合计",
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

//合计 待揽收
function showStayTotal(obj){
	$j("#showList").addClass("hidden");
	$j("#express_info").addClass("hidden");
	$j("#tran_info").removeClass("hidden");
	$j("#hasLanshou_info").addClass("hidden");
	$j("#inTheTransif_info").addClass("hidden");
	$j("#exception_info").addClass("hidden");
	$j("#expressStatus").html("待揽收");
	var province =$j(obj).parents("tr").attr("id");
	if(province =='合计'){
		$j("#StayProvince").html("");
	}else{
		$j("#StayProvince").html(province);
	}
	var postData = loxia._ajaxFormToObj("query-form"); 
	var exceprtionStatus='001';
	postData["province"]="";
	postData["exceprtionStatus"]=exceprtionStatus;
	$j("#tbl-tran-Detail").jqGrid('setGridParam',{            
		url:loxia.getTimeUrl($j("body").attr("contextpath")+"/findExpressStatusInfo.json?dt=" + new Date().getTime()),
		postData:postData,
		page:1
	}).trigger("reloadGrid");
	
}
//合计已揽收
function showHasTotal(obj){
	$j("#showList").addClass("hidden");
	$j("#express_info").addClass("hidden");
	$j("#tran_info").addClass("hidden");
	$j("#hasLanshou_info").removeClass("hidden");
	$j("#inTheTransif_info").addClass("hidden");
	$j("#exception_info").addClass("hidden");
	$j("#hasLanshouStatus").html("已揽收");
	var province =$j(obj).parents("tr").attr("id");
	if(province =='合计'){
		$j("#hasLanshouProvince").html("");
	}else{
		$j("#hasLanshouProvince").html(province);
	}
	var postData = loxia._ajaxFormToObj("query-form"); 
	var exceprtionStatus='010';
	postData["province"]="";
	postData["exceprtionStatus"]=exceprtionStatus;
	$j("#tbl-hasLanshou-Detail").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath")+"/findExpressStatusInfo.json?dt=" + new Date().getTime()),
		postData:postData,
		page:1
	}).trigger("reloadGrid");
	
}
//合计中转中 
function showIntheTotal(obj){
	$j("#showList").addClass("hidden");
	$j("#express_info").addClass("hidden");
	$j("#tran_info").addClass("hidden")
	$j("#hasLanshou_info").addClass("hidden");
	$j("#inTheTransif_info").removeClass("hidden");
	$j("#exception_info").addClass("hidden");
	$j("#inTheTransifStatus").html("中转中");
	var province =$j(obj).parents("tr").attr("id");
	if(province =='合计'){
		$j("#inTheTransifProvince").html("");
	}else{
		$j("#inTheTransifProvince").html(province);
	}
	var postData = loxia._ajaxFormToObj("query-form"); 
	var exceprtionStatus='050';
	postData["province"]="";
	postData["exceprtionStatus"]=exceprtionStatus;
	$j("#tbl-inTheTransif-Detail").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath")+"/findExpressStatusInfo.json?dt=" + new Date().getTime()),
		postData:postData,
		page:1
	}).trigger("reloadGrid");
	
}

//合计 异常件
function showExceptionTotal(obj){
	$j("#showList").addClass("hidden");
	$j("#express_info").addClass("hidden");
	$j("#tran_info").addClass("hidden");
	$j("#hasLanshou_info").addClass("hidden");
	$j("#inTheTransif_info").addClass("hidden");
	$j("#exception_info").removeClass("hidden");
	$j("#exception_dtail").addClass("hidden");
	$j("#exceptionStatus").html("异常件");
	var province =$j(obj).parents("tr").attr("id");
	if(province =='合计'){
		$j("#exceptionProvince").html("");
	}else{
		$j("#exceptionProvince").html(province);
	}
	var postData = loxia._ajaxFormToObj("query-form"); 
	postData["exceprtionStatus"]="";
	postData["province"]="";
	$j("#tbl-exception-Detail").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath")+"/findExpressStatusInfo.json?dt=" + new Date().getTime()),
		postData:postData,
		page:1
	}).trigger("reloadGrid");
	
}


//点击省 和合计
function showDetail(obj){
	$j("#showList").addClass("hidden");
	$j("#express_info").removeClass("hidden");
//	tbl-dispatch-list
	var province =$j(obj).parents("tr").attr("id");
	var lpcode=$j("#selLpcode").val();
	if(province =='合计'){
		$j("#province").html("");
		//合计数据
		 var baseUrl = $j("body").attr("contextpath");
			$j("#tbl-showDetail").jqGrid('setGridParam',{page:1,url:baseUrl + "/findExpressDetailList.json?",postData:loxia._ajaxFormToObj("query-form")}).trigger("reloadGrid");
	}else{
		$j("#province").html(province);
		//显示省市明细列表
	    var baseUrl = $j("body").attr("contextpath");
		$j("#tbl-showDetail").jqGrid('setGridParam',{page:1,url:baseUrl + "/findExpressDetailList.json?province="+province,postData:loxia._ajaxFormToObj("query-form")}).trigger("reloadGrid");
	}
	
}

//待揽收
function showStay(obj){
	$j("#showList").addClass("hidden");
	$j("#express_info").addClass("hidden");
	$j("#tran_info").removeClass("hidden");
	$j("#hasLanshou_info").addClass("hidden");
	$j("#inTheTransif_info").addClass("hidden");
	$j("#exception_info").addClass("hidden");
	
	var province =$j(obj).parents("tr").attr("id");
	if(province =='合计'){
		$j("#StayProvince").html("");
	}else{
		$j("#StayProvince").html(province);
	}	
	$j("#expressStatus").html("待揽收");
	var postData = loxia._ajaxFormToObj("query-form"); 
	var exceprtionStatus='001';
	postData["exceprtionStatus"]=exceprtionStatus;
	postData["province"]=province;
	$j("#tbl-tran-Detail").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath")+"/findExpressStatusInfo.json?dt=" + new Date().getTime()),
		postData:postData,
		page:1
	}).trigger("reloadGrid");
	        
}
//已揽收
function showHas(obj){
	$j("#showList").addClass("hidden");
	$j("#express_info").addClass("hidden");
	$j("#tran_info").addClass("hidden");
	
	$j("#hasLanshou_info").removeClass("hidden");
	$j("#inTheTransif_info").addClass("hidden");
	$j("#exception_info").addClass("hidden");
	var province =$j(obj).parents("tr").attr("id");
	if(province =='合计'){
		$j("#hasLanshouProvince").html("");
	}else{
		$j("#hasLanshouProvince").html(province);
	}
	$j("#hasLanshouStatus").html("已揽收");
	
	var postData = loxia._ajaxFormToObj("query-form"); 
	var exceprtionStatus='010';
	postData["temp"] = (new Date()).getTime();
	postData["exceprtionStatus"]=exceprtionStatus;
	postData["province"]=province;
	$j("#tbl-hasLanshou-Detail").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath")+"/findExpressStatusInfo.json?dt=" + new Date().getTime()),
		postData:postData,
		page:1
	}).trigger("reloadGrid");
	
	
}
//中转中
function showInthe (obj){
	$j("#showList").addClass("hidden");
	$j("#express_info").addClass("hidden");
	$j("#tran_info").addClass("hidden");
	
	$j("#hasLanshou_info").addClass("hidden");
	$j("#inTheTransif_info").removeClass("hidden");
	$j("#exception_info").addClass("hidden");
	var province =$j(obj).parents("tr").attr("id");
	if(province =='合计'){
		$j("#inTheTransifProvince").html("");
	}else{
		$j("#inTheTransifProvince").html(province);
	}
	$j("#inTheTransifStatus").html("中转中");
	var postData = loxia._ajaxFormToObj("query-form"); 
	var exceprtionStatus='050';
	postData["exceprtionStatus"]=exceprtionStatus;
	postData["province"]=province;
	$j("#tbl-inTheTransif-Detail").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath")+"/findExpressStatusInfo.json?dt=" + new Date().getTime()),
		postData:postData,
		page:1
	}).trigger("reloadGrid");
}
//异常件
function showException(obj){
	$j("#showList").addClass("hidden");
	$j("#express_info").addClass("hidden");
	$j("#tran_info").addClass("hidden");
	$j("#hasLanshou_info").addClass("hidden");
	$j("#inTheTransif_info").addClass("hidden");
	$j("#exception_info").removeClass("hidden");
	var province =$j(obj).parents("tr").attr("id");
	if(province =='合计'){
		$j("#exceptionProvince").html("");
	}else{
		$j("#exceptionProvince").html(province);
	}
	$j("#exceptionStatus").html("异常件");
	var postData={};
	postData = loxia._ajaxFormToObj("query-form");
	postData["exceprtionStatus"]="";
	postData["province"]=province;
	$j("#tbl-exception-Detail").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath")+"/findExpressStatusInfo.json?dt=" + new Date().getTime()),//?dt=" + new Date().getTime()
		postData:postData,
		page:1
	}).trigger("reloadGrid");
}

//异常详细信息tbl-tran-Detail
function showDetailLine(obj){
	$j("#showList").addClass("hidden");
	$j("#express_info").addClass("hidden");
	$j("#tran_info").addClass("hidden"); 
	$j("#exception_info").addClass("hidden");
	$j("#excExpressStatus").html("异常件");
	$j("#exception_dtail").removeClass("hidden");
	
	var province= $j("#exceptionProvince").html();
	$j("#excProvince").html(province);
	var id =$j(obj).parents("tr").attr("id");
	var data=$j("#tbl-exception-Detail").jqGrid("getRowData",id);
	$j("#excLpcode").html(data["lpName"]);
	var lpcode=data["lpcode"];
	var warehouseName=data["warehouseName"];
    $j("#excWarehouse").html(warehouseName);
	if(province =='合计'){
		$j("#excProvince").html("");
	}else{
		$j("#excProvince").html(province);
	}
	$j("#excExpressStatus").html("异常件");
	var postData={};
	postData = loxia._ajaxFormToObj("query-form");
	postData["lpcode"]=lpcode;
	postData["province"]=province; 
	postData["warehouseName"]=warehouseName;
	$j("#tbl-exp-Detail").jqGrid('setGridParam',{
		url:loxia.getTimeUrl($j("body").attr("contextpath")+"/findAllExpressExceptionInfo.json?dt=" + new Date().getTime()),//?dt=" + new Date().getTime()
		postData:postData,
		page:1
	}).trigger("reloadGrid");
	
}
$j(document).ready(function(){
	//初始化物流商信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	//初始化省
	var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/getExpressProvince.json"));
	for(var idx in rs){
		$j("<option value='" + rs[idx].destinationProvince + "'>"+ rs[idx].destinationProvince +"</option>").appendTo($j("#destinationProvince"));
	}
	//初始化店铺
	var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/getExpressOwner.json"));
	for(var idx in rs){
		$j("<option value='" + rs[idx].owner + "'>"+ rs[idx].owner +"</option>").appendTo($j("#owner"));
	}
	//初始化仓库
	var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/getexpressWarehouse.json"));
	for(var idx in rs){
		$j("<option value='" + rs[idx].id + "'>"+ rs[idx].warehouseName +"</option>").appendTo($j("#warehouseName"));
	}
	//查询
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("query-form");  
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath")+"/findExpressInfoCount.json"),
			postData:postData,
			page:1
		}).trigger("reloadGrid");
//		var postData = loxia._ajaxFormToObj("query-form");  
		$j("#tbl-dispatch-total").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath")+"/getExpreessTotal.json"),
			postData:postData,
			page:1 
		}).trigger("reloadGrid");
	});
//	$j("#tbl-dispatch-total").jqGrid({
//		url:$j("body").attr("contextpath")+"/getExpreessTotal.json",
	//重置
	$j("#reset").click(function(){
		$j("#selLpcode").val("");
		$j("#owner").val("");
		$j("#destinationProvince").val("");
		$j("#warehouseName").val("");
		
	});
	//初始化列表
	var postData = loxia._ajaxFormToObj("query-form");
	$j("#tbl-dispatch-list").jqGrid({
		url:$j("body").attr("contextpath")+"/findExpressInfoCount.json",
//		postData:postData,
		datatype: "json",
		colNames: ["省","待揽收","已揽收","中转中","异常件","合计"],
		colModel: [
//				   {name: "id", index: "id", width:80,resizable:true},
		           {name: "destinationProvince", index: "destinationProvince",  formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},
		           {name: "stayLanshou", index: "stayLanshou", formatter:"linkFmatter",formatoptions:{onclick:"showStay"}, width:100, resizable:true},    
		           {name: "hasLanshou", index: "hasLanshou", formatter:"linkFmatter",formatoptions:{onclick:"showHas"}, width:100, resizable:true},
		           {name: "inTheTransif", index: "inTheTransif",  formatter:"linkFmatter",formatoptions:{onclick:"showInthe"}, width:100, resizable:true},
		           {name: "exceptionPiece", index: "exceptionPiece",  formatter:"linkFmatter",formatoptions:{onclick:"showException"}, width:100, resizable:true},
		           {name: "totalPiece", index: "totalPiece", width: 80, resizable: true},
		           ],
		caption: "目的地快递部分状态汇总列表",
		pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'stayLanshou',
	   	height:"auto",
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete:function(){
			loxia.initContext($j("#tbl-dispatch-list"));
		}
	});	
	//统计数据
	$j("#tbl-dispatch-total").jqGrid({
		url:$j("body").attr("contextpath")+"/getExpreessTotal.json",
		postData:postData,
		datatype: "json",
		colNames: ["合计","待揽收","已揽收","中转中","异常件","合计"],
		colModel: [
						{name: "destinationProvince", index: "province",  formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},
						{name: "stayLanshou", index: "stay", formatter:"linkFmatter",formatoptions:{onclick:"showStayTotal"}, width:100, resizable:true},    
						{name: "hasLanshou", index: "has", formatter:"linkFmatter",formatoptions:{onclick:"showHasTotal"}, width:100, resizable:true},
						{name: "inTheTransif", index: "inThe",  formatter:"linkFmatter",formatoptions:{onclick:"showIntheTotal"}, width:100, resizable:true},
						{name: "exceptionPiece", index: "piece",  formatter:"linkFmatter",formatoptions:{onclick:"showExceptionTotal"}, width:100, resizable:true},
						{name: "totalPiece", index: "totalPiece", width: 80, resizable: true},
				 ],
				caption: "统计汇总列表",
			    rowNum: jumbo.getPageSize(),
				rowList:jumbo.getPageSizeList(),
			   	sortname: 'destinationProvince',
			   	height:"auto",
			    rownumbers:true,
			    viewrecords: true,
				sortorder: "desc",
				hidegrid:false,
				jsonReader: { repeatitems : false, id: "0" },
				loadComplete:function(){
					loxia.initContext($j("#tbl-dispatch-list"));
				}
	});	

	//显示省市明细列表
	$j("#tbl-showDetail").jqGrid({
		datatype: "json",
		colNames: ["市","待揽收","已揽收","中转中","异常件","合计"],
		colModel: [
						{name: "city", index: "city",  width:100, resizable:true},//formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, 
						{name: "stayLanshou", index: "stay", width:100, resizable:true},    
						{name: "hasLanshou", index: "has", width:100, resizable:true},
						{name: "inTheTransif", index: "inThe",  width:100, resizable:true},
						{name: "exceptionPiece", index: "piece", width:100, resizable:true},
						{name: "totalPiece", index: "totalPiece", width: 80, resizable: true},
				  ],
		caption: "省市明细列表",
//	   	sortname: 'city',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerDetail",
//		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0"}
	});
	//物流商统计明细列表 待揽收 findExpressStatusInfo
	$j("#tbl-tran-Detail").jqGrid({
		datatype: "json",
		colNames: ["id","物流code","物流服务商","物流仓库","待揽收","已揽收","中转中","异常件"],
		colModel: [
		           	{name:"id",index:"id", hidden: true},
		           	{name:"lpcode",index:"lpcode", hidden: true},
		           	{name:"lpName",index:"lpName",width:80,resizable:true},
					{name:"warehouseName",index:"warehouseName",width:80,resizable:true},
					{name:"stayLanshou", index:"stayLanshou" ,width:80,resizable:true},
					{name:"hasLanshou", index:"hasLanshou" ,width:80,hidden:true},
					{name:"inTheTransif", index:"inTheTransif" ,width:80,hidden:true},
					{name:"exceptionPiece", index:"exceptionPiece" ,width:80,hidden:true},
					],
		caption: "物流商统计明细列表",
	   	sortname: 'warehouseName',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerTran",
		sortorder: "desc",             
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0"},

	
	});
	
	// 已揽收 findExpressStatusInfo
	$j("#tbl-hasLanshou-Detail").jqGrid({
		datatype: "json",
		colNames: ["id","物流code","物流服务商","物流仓库","待揽收","已揽收","中转中","异常件"],
		colModel: [
		           	{name:"id",index:"id", hidden: true},
		           	{name:"lpcode",index:"lpcode", hidden: true},
		           	{name:"lpName",index:"lpName",width:80,resizable:true},
					{name:"warehouseName",index:"warehouseName",width:80,resizable:true},
					{name:"stayLanshou", index:"stayLanshou" ,width:80,hidden:true},
					{name:"hasLanshou", index:"hasLanshou" ,width:80,resizable:true},
					{name:"inTheTransif", index:"inTheTransif" ,width:80,hidden:true},
					{name:"exceptionPiece", index:"exceptionPiece" ,width:80,hidden:true},
					],
		caption: "物流商统计明细列表",
	   	sortname: 'warehouseName',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerHasLanshou",
		sortorder: "desc",             
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0"},
	
	});
	
	//物流商统计明细列表 中转中
	$j("#tbl-inTheTransif-Detail").jqGrid({
		datatype: "json",
		colNames: ["id","物流code","物流服务商","物流仓库","待揽收","已揽收","中转中","异常件"],
		colModel: [
		            {name:"id",index:"id", hidden: true},
		           	{name:"lpcode",index:"lpcode", hidden: true},
		           	{name:"lpName",index:"lpName",width:80,resizable:true},
					{name:"warehouseName",index:"warehouseName",width:80,resizable:true},
					{name:"stayLanshou", index:"stayLanshou" ,width:80,hidden:true},
					{name:"hasLanshou", index:"hasLanshou" ,width:80,hidden:true},
					{name:"inTheTransif", index:"inTheTransif" ,width:80,resizable:true},
					{name:"exceptionPiece", index:"exceptionPiece" ,width:80,hidden:true},
					],
		caption: "物流商统计明细列表",
	   	sortname: 'warehouseName',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerInTheTransif",
		sortorder: "desc",             
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0"},
//		loadComplete : function() {
//			alert('bb');
//		}
	
	});
	//物流商统计明细列表 异常件 showExceptionTotal
	$j("#tbl-exception-Detail").jqGrid({
		datatype: "json",
		colNames: ["id","物流code","物流服务商","物流仓库","待揽收","已揽收","中转中","异常件"],
		colModel: [
		            {name:"id",index:"id", hidden: true},
		           	{name:"lpcode",index:"lpcode", hidden: true},
		           	{name:"lpName",index:"lpName",width:80,resizable:true},
					{name:"warehouseName",index:"warehouseName",width:80,resizable:true},
					{name:"stayLanshou", index:"stayLanshou" ,width:80,hidden:true},
					{name:"hasLanshou", index:"hasLanshou" ,width:80,hidden:true},
					{name:"inTheTransif", index:"inTheTransif" ,width:80,hidden:true},
					{name:"exceptionPiece", index:"exceptionPiece" , formatter:"linkFmatter",formatoptions:{onclick:"showDetailLine"}, width:100, resizable:true},
					],
		caption: "物流商统计明细列表",
	   	sortname: 'warehouseName',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerException",
		sortorder: "desc",             
		viewrecords: true,
   		rownumbers:true,
   		hidegrid:false,
		jsonReader: { repeatitems : false,id:"0"},
		
	});
	
	
	//异常件原因详细信息
	$j("#tbl-exp-Detail").jqGrid({
		datatype: "json",
		colNames: ["物流商Code","物流服务商","揽件超时","配送超时","客户原因","送货上门变只提","拒收","货损","货差","合计"],
		colModel: [
		           	{name:"lpcode",index:"lpcode", hidden: true},
		           	{name:"lpName",index:"lpName",width:80,resizable:true},
					{name:"lOverTime",index:"lOverTime",width:80,resizable:true},
					{name:"pOverTime", index:"pOverTime" ,width:80,resizable:true},
					{name:"customerReason", index:"customerReason",width:120,resizable:true},
					{name:"changeL",index:"changeL",width:80,resizable:true},
					{name:"rejects", index:"rejects",width:100, resizable:true},
					{name: "spillage", index: "spillage", width: 80, resizable: true},
					{name: "shortage", index: "shortage", width: 80, resizable: true},
					{name: "totalPiece", index: "totalPiece", width: 80, resizable: true},
					],
		caption: "异常件原因详细列表",
//	   	sortname: 'lpName',    
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerExp",
//		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0"},
		
	});

	//返回
	$j("#back").click(function(){
		$j("#showList").removeClass("hidden");
		$j("#express_info").addClass("hidden");
	});
	$j("#stayBack").click(function(){
		$j("#showList").removeClass("hidden");
		$j("#express_info").addClass("hidden");
		$j("#tran_info").addClass("hidden");
	});
	//异常返回
	$j("#backexe").click(function(){
		$j("#showList").addClass("hidden");
		$j("#express_info").addClass("hidden");
		$j("#exception_dtail").addClass("hidden");
		$j("#tran_info").addClass("hidden");
		$j("#hasLanshou_info").addClass("hidden");
		$j("#inTheTransif_info").addClass("hidden");
		$j("#exception_info").removeClass("hidden");
		
	});
	//已揽收
	$j("#hasLanshouBack").click(function(){
		$j("#showList").removeClass("hidden");
		$j("#express_info").addClass("hidden");
		$j("#tran_info").addClass("hidden");
		$j("#hasLanshou_info").addClass("hidden");
		$j("#inTheTransif_info").addClass("hidden");
		$j("#exception_info").addClass("hidden");
	});
	//中转中
	$j("#inTheTransifBack").click(function(){
		$j("#showList").removeClass("hidden");
		$j("#express_info").addClass("hidden");
		$j("#tran_info").addClass("hidden");
		$j("#hasLanshou_info").addClass("hidden");
		$j("#inTheTransif_info").addClass("hidden");
		$j("#exception_info").addClass("hidden");
	});
	//异常
	$j("#exceptionBack").click(function(){
		$j("#showList").removeClass("hidden");
		$j("#express_info").addClass("hidden");
		$j("#tran_info").addClass("hidden");
		$j("#hasLanshou_info").addClass("hidden");
		$j("#inTheTransif_info").addClass("hidden");
		$j("#exception_info").addClass("hidden");
	});
	

});




  













