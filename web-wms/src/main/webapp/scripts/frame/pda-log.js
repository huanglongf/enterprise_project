$j.extend(loxia.regional['zh-CN'],{
	CODE : "单据号",
	STATUS : "状态",
	TYPE:"类型",
	MEMO:"执行情况",
	CREATETIME : "创建时间",
	FINISHTIME:"完成时间",
	IS_PDA:"是否PDA主动",
	TRANS_NO:"运单号",
	COUNT_SEARCH : "操作列表",
	
	DIRECTION:"事务方向",
	LOCATION:"库位编码",
	BAR_CODE:"条形码",
	INVSTATUS:"库存状态",
	QTY:"数量",
	DETAIL_INFO:"明细列表",
	
	TRANSNO:"快递单号"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
var customizationShopMap = {};//当前仓库定制打印模版店铺集合
$j(document).ready(function(){
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var pdaStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"pdaStatus"});
	var pdaType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"pdaType"});
	var directionName=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"directionName"}); 
	var typeName={"value":"1:入库-收货;3:入库-上架;11:出库;21:库内移动;23:主动移库出库;31:盘点;41:退仓;51:交接"};
	var statusName={"value":"0:取消;1:新建;2:已发送的;5:执行中;10:完成;20:不执行;-1:错误;"};
	var directionName1={"value":"1:入库;2:出库;"};
	//初始化列表
	$j("#tbl-staList-query").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("CODE"),i18("STATUS"),i18("TYPE"),i18("CREATETIME"),i18("FINISHTIME"),i18("IS_PDA"),i18("QTY"), i18("TRANS_NO"),"物流服务商编码",i18("MEMO")],
		colModel: [{name: "id", index: "id", hidden: true},		        
		           {name: "orderCode", index: "orderCode", width: 200,formatter:"linkFmatter",formatoptions:{onclick:"showStaLine"}, resizable: true},
		           {name: "intStatus", index: "intStatus",width: 50, resizable: true,formatter:'select',editoptions:statusName},
		           {name: "intType", index: "intType", width: 80, resizable: true,formatter:'select',editoptions:typeName},
		           {name: "createTime", index: "createTime", width: 120, resizable: true},
		           {name: "finishTime", index: "finishTime", width: 120, resizable: true},
		           {name: "isPDA", index: "isPDA", width: 100, resizable: true,formatter:'select',editoptions:trueOrFalse},
		           {name: "qty", index: "qty",  width: 80, resizable: true},
		           {name: "transNo", index: "transNo",  width: 80, resizable: true},
		           {name: "lpCode",index:"lpCode",width:80,resizable:true},
		           {name: "memo", index: "memo", width:400, resizable: true}],
		caption: i18("COUNT_SEARCH"),//待出库列表
	   	sortname: 'id',
	   	pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:"auto",
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc", 
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl-staList-query", {"intStatus":"pdaStatus", "isPDA":"trueOrFalse", "intType":"pdaType"}, 
			loxia.getTimeUrl($j("body").attr("contextpath")+"/json/getPdaLogList.json"));
	
	//PDA上传明细行除了物流交接
	$j("#tbl_detail_list").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("DIRECTION"),i18("LOCATION"),i18("BAR_CODE"),i18("INVSTATUS"),i18("QTY")],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
				   {name: "intDirection", index: "intDirection", width: 80, resizable: true,formatter:'select',editoptions:directionName1},
		           {name: "locationCode", index: "locationCode",width: 120, resizable: true},
		           {name: "skuCode", index: "skuCode", width: 140, resizable: true},
		           {name: "invStatus", index: "invStatus", width: 100, resizable: true},
		           {name: "qty", index: "qty", width: 120, resizable: true}],
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
	jumbo.bindTableExportBtn("tbl_detail_list", {}, 
			loxia.getTimeUrl($j("body").attr("contextpath")+"/json/findPdaLogLine.json"));
	
	//PDA上传明细行物流交接
	$j("#tbl_handover_detail_list").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("TRANSNO")],
		colModel: [
		           {name: "id", index: "id", hidden: true},		         
				   {name: "transNo", index: "transNo", width: 200, resizable: true}],
		caption: i18("DETAIL_INFO"),//详细信息
		rowNum: -1,
		height:"auto",
   		sortname: 'id',
     	multiselect: false,
		sortorder: "desc",
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	//返回按钮功能
	$j("#back").click(function(){
		$j("#staInfo").removeClass("hidden");
		$j("#staLineInfo").addClass("hidden");
	});
	//查询按钮功能
	$j("#search").click(function(){
		var postData = loxia._ajaxFormToObj("query-form");
		$j("#tbl-staList-query").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath")+"/json/getPdaLogList.do"),
			postData:postData,
			page:1
		}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-staList-query", {"intStatus":"pdaStatus", "isPDA":"trueOrFalse", "intType":"pdaType"}, 
				loxia.getTimeUrl($j("body").attr("contextpath")+"/json/getPdaLogList.json"),postData);
	});
	$j("#reset").click(function(){
		$j("#query-form input").val("");
		$j("#sstatus option:first").attr("selected",true);
		$j("#stype option:first").attr("selected",true);
	});
});
//作业单明细
function showStaLine(tag){
	var tr = $j(tag).parents("tr");
	var type = $j(tr).children("td:eq(4)").attr("title");
	var id = tr.attr("id");
	$j("#staInfo").addClass("hidden");
	$j("#staLineInfo").removeClass("hidden");
	var pda=$j("#tbl-staList-query").jqGrid("getRowData",id);
	$j("#s_code").html(pda["orderCode"]);
	$j("#s_type").html(type);
	var postData = {};
	postData["pda.id"] = id;
	if(type=="交接"){
		$j("#gbox_tbl_handover_detail_list").removeClass("hidden");
		$j("#gbox_tbl_detail_list").addClass("hidden");
		$j("#tbl_handover_detail_list").jqGrid('setGridParam',
				{url:window.$j("body").attr("contextpath")+"/findpdahandoverlogline.json",
				postData:postData,page:1}
				).trigger("reloadGrid");
	}else{
		$j("#gbox_tbl_detail_list").removeClass("hidden");
		$j("#gbox_tbl_handover_detail_list").addClass("hidden");
		$j("#tbl_detail_list").jqGrid('setGridParam',
				{url:window.$j("body").attr("contextpath")+"/findPdaLogLine.json",
				postData:postData,page:1}
			).trigger("reloadGrid");
	}
	jumbo.bindTableExportBtn("tbl_detail_list", {"intDirection":"directionName"}, 
			loxia.getTimeUrl($j("body").attr("contextpath")+"/json/findPdaLogLine.json"),postData);
}
