
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
$j(document).ready(function (){
	
	$j("#tbl-deliver-change").jqGrid({
		datatype: "json",
		colNames: ["ID","原始物流服务商","目标物流服务商"],
		colModel: [
		           {name: "id", index: "id", hidden: true},
		           {name: "lpcode", index: "lpcode", width: 150, resizable: true},
		           {name: "newLpcode", index: "newLpcode",width: 150, resizable: true}],
		caption: i18("转物流配置列表"),
		rowNum:-1,
	   	sortname: 'jmcode',
	   	height:"auto",
	   	rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pager",
	    multiselect:true,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#insert").click(function(){
	var postData={};
	var delivery=loxia._ajaxFormToObj("insertForm")
	var lpCode=delivery['deliveryChangeConfigure.lpcode'];
	var newLpCode=delivery['deliveryChangeConfigure.newLpcode'];
		if(lpCode == ""){
			jumbo.showMsg("请选择物流商");
			return;
		}
		if(newLpCode == ""){
			jumbo.showMsg("请选择目标物流商");
			return;
		}
		if(lpCode==newLpCode){
			jumbo.showMsg("原始物流商和目标物流商不能相同");
			return;
		}
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/increaseDeliverChConfig.json",loxia._ajaxFormToObj("insertForm"));
		if(result.message=="success"){
			jumbo.showMsg("添加成功");
			$j("#tbl-deliver-change").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/getDeliverChConfigList.json"),
			postData:loxia._ajaxFormToObj("searchForm")}).trigger("reloadGrid",[{page:1}]);
		}
		if(result.message=="error"){
			jumbo.showMsg("添加失败，一个原始物流商只能对应一个目标物流商");
		}
	});
	
	$j("#search").click(function(){
		var delivery=loxia._ajaxFormToObj("searchForm")
		var lpCode=delivery['deliveryChangeConfigure.lpcode'];
		var newLpCode=delivery['deliveryChangeConfigure.newLpcode'];
		$j("#tbl-deliver-change").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/getDeliverChConfigList.json"),
			postData:loxia._ajaxFormToObj("searchForm")}).trigger("reloadGrid",[{page:1}]);
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#searchForm select").val("");
		$j("#lpcode").attr("checked",false);
		$j("#newLpcode").attr("checked",false);
	});
	
	$j("#delete").click(function(){
		var idList = $j("#tbl-deliver-change").jqGrid('getGridParam','selarrrow');
		if(confirm("确认删除这些配置信息？")){
		var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/deleteDeliverChConfig.json?idList="+idList);
		if(result.message=="success"){
			jumbo.showMsg("删除成功！");
			$j("#tbl-deliver-change").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/getDeliverChConfigList.json"),
				postData:loxia._ajaxFormToObj("searchForm")}).trigger("reloadGrid",[{page:1}]);
		}else if (result.messsage=="error"){
			jumbo.showMsg("删除失败,请重试!");
			}
		}
	});
});