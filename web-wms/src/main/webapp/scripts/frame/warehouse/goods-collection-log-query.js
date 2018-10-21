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
		url:$j("body").attr("contextpath") + "/findGoodsCollectionLog.json",
		datatype: "json",
		colNames: ["ID","集货区域编码","批次号","周转箱编码","周转箱集货状态","操作时间","操作人"],
		colModel: [
				   {name:"id",index:"id",hidden:true},
				   {name: "collectionCode", index: "collectionCode", width: 100, resizable: true},
		           {name: "pickingCode", index: "pickingCode", width:200,resizable:true},         
		           {name: "containerCode", index: "containerCode", width: 100, resizable: true},
		           {name: "status", index: "status", width: 100, resizable: true},
		           {name: "opTime", index: "opTime", width: 100, resizable: true},
		           {name: "loginName", index: "loginName", width: 100, resizable: true}
		           ],
		caption: '人工集货日志列表',
	   	sortname: 'id',
	   	height:"auto",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	    rownumbers:true,
	    viewrecords: true,
		sortorder: "desc",
		pager:"pager",
		jsonReader: { repeatitems : false, id: "0" }
	});
	/*jumbo.bindTableExportBtn("tbl_cm_list", {}, 
			loxia.getTimeUrl($j("body").attr("contextpath")+"/json/findGoodsCollectionLog.json"));
	*/
	jumbo.bindTableExportBtn("tbl_cm_list", {}, 
			loxia.getTimeUrl($j("body").attr("contextpath")+"/json/findGoodsCollectionLog.json"));
	
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
	});
	$j("#btn-query").click(function(){
		var postData = loxia._ajaxFormToObj("queryForm");
		$j("#tbl_cm_list").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath")+"/json/findGoodsCollectionLog.do"),
			postData:postData,
			page:1
		}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_cm_list", {}, 
				loxia.getTimeUrl($j("body").attr("contextpath")+"/json/findGoodsCollectionLog.json"),postData);
	});
	
});
