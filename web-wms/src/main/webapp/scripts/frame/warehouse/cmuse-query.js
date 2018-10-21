$j.extend(loxia.regional['zh-CN'],{
	APPLY_FORM_SELECT : "请选择作业申请单",
	CM_USE_LIST:"耗材使用情况列表",
	SKU_LIST:"商品列表",
	OPERATOR:"操作",
	DELETE:"删除"
});
function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
$j(document).ready(function(){
	$j("#tbl_cm_list").jqGrid({
		url:$j("body").attr("contextpath") + "/findallcmuserlist.json",
		datatype: "json",
		colNames: ["ID","出库时间","相关单据号","推荐箱型","实用箱型","匹配情况","复核者","称重者"],
		colModel: [
				   {name: "id", index: "id", hidden: true},
		           {name: "outboundTime", index: "outboundTime", width:200,resizable:true},         
		           {name: "slipCode", index: "slipCode", width: 100, resizable: true},
		           {name: "recommandSku", index: "recommandSku", width: 100, resizable: true},
		           {name: "usedSku", index: "usedSku", width: 100, resizable: true},
		           {name: "isMatch", index: "isMatch", width: 100, resizable: true},
		           {name: "checkUser", index: "checkUser", width: 100, resizable: true},
		           {name: "outboundUser", index: "outboundUser", width:100, resizable: true}
		           ],
		caption: i18("CM_USE_LIST"),// 耗材使用情况列表
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		pager:"pager",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
	});
	$j("#btn-query").click(function(){
		var postData = loxia._ajaxFormToObj("queryForm");
		$j("#tbl_cm_list").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath")+"/json/findallcmuserlist.do"),
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
});
