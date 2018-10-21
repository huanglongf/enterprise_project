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
		url:$j("body").attr("contextpath") + "/findSortPicking.json",
		datatype: "json",
		colNames: ["配货批次号","拣货区域","短拣库位","商品名称","商品编码","货号","关键属性","条形码","计划数量","实际拣货数量","短拣数量","拣货时间","操作人"],
		colModel: [
		           {name: "pickingCode", index: "pickingCode", width:200,resizable:true},         
		           {name: "pickingZoonCode", index: "pickingZoonCode", width: 100, resizable: true},
		           {name: "locCode", index: "locCode", width: 100, resizable: true},
		           {name: "skuName", index: "skuName", width: 100, resizable: true},
		           {name: "skuCode", index: "skuCode", width: 100, resizable: true},
		           {name: "supplierCode", index: "supplierCode", width: 100, resizable: true},
		           {name: "keyProperties", index: "keyProperties", width: 100, resizable: true},
		           {name: "barCode", index: "barCode", width: 100, resizable: true},
		           {name: "planQty", index: "planQty", width: 100, resizable: true},
		           {name: "qty", index: "qty", width: 100, resizable: true},
		           {name: "sortQty", index: "sortQty", width: 100, resizable: true},
		           {name: "executionTime", index: "executionTime", width: 100, resizable: true},
		           {name: "loginName", index: "loginName", width: 100, resizable: true},
		           ],
		caption: 'PDA短拣查询',
	   	sortname: 'pickingCode',
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
			url:loxia.getTimeUrl($j("body").attr("contextpath")+"/json/findSortPicking.do"),
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	//导出查询数据
	$j("#btn-export").click(function(){
		var code = $j("#code").val();
		var pickingCode = $j("#pickingCode").val();
		var pickingZoonCode = $j("#pickingZoonCode").val();
		var skuName = $j("#skuName").val();
		var startDateStr = $j("#startDateStr").val();
		var supplierCode = $j("#supplierCode").val();
		var skuCode = $j("#skuCode").val();
		var barCode = $j("#barCode").val();
		var endDateStr = $j("#endDateStr").val();
		
		var baseUrl = $j("body").attr("contextpath");
		var url = baseUrl + "/json/padSortPickingExcel.do?pdaSortPickingCommand.code="+code+"&pdaSortPickingCommand.pickingCode="+pickingCode+"&pdaSortPickingCommand.pickingZoonCode="+pickingZoonCode+"&pdaSortPickingCommand.skuName="+skuName+"&startDateStr="+startDateStr+"&pdaSortPickingCommand.supplierCode="+supplierCode+"&pdaSortPickingCommand.skuCode="+skuCode+"&pdaSortPickingCommand.barCode="+barCode+"&endDateStr="+endDateStr;
		$j("#sortPickingExport").attr("src",url);
	});
});
