$j(document).ready(function (){
	$j("#div-list2").addClass("hidden");
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findPlatformList.json");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#lpCode1"));
	}
	
	result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findAdOrderType.json");
	for(var idx in result){
		$j("<option value='" + result[idx].adName + "'>"+ result[idx].adName +"</option>").appendTo($j("#adOrderType1"));
	}
	$j("#tbl-ad-package-deal").jqGrid({
		datatype: "json",
		url:$j("body").attr("contextpath")+"/json/adPackageList.json",
		colNames:["ID","800ts工单编号","退货作业单号","退货指令","物流商","快递单号","WMS事件编号","SKU编码","创建时间","更新时间","800ts工单类型","800ts处理结果","状态","是否已操作","创建人"],
		colModel:[
		          {name: "id", index: "id", hidden: true},	
		          {name: "adOrderId", index: "adOrderId", width: 100, formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true,sortable:false},
		          {name: "extended", index: "extended", width: 100, resizable: true,sortable:false},
		          {name: "returnInstruction", index: "returnInstruction", width: 100, resizable: true,sortable:false},
		          {name: "lpCode", index: "lpCode", width: 100, resizable: true,sortable:false},
		          {name: "trankNo", index: "trankNo", width: 100, resizable: true,sortable:false},
		          {name: "wmsOrderId", index: "wmsOrderId", width: 100, resizable: true,sortable:false},
		          {name: "skuId", index: "skuId", width: 100, resizable: true,sortable:false},
		          {name: "createTime", index: "createTime", width: 150, resizable: true,sortable:false},
		          {name: "lastUpdateTime", lastUpdateTime: "owner", width: 150, resizable: true,sortable:false},
		          {name: "adOrderType", index: "adOrderType", width: 100, resizable: true,sortable:false},
		          {name: "adStatus", index: "adStatus", width: 100, resizable: true,sortable:false},
		          {name: "statusName", index: "statusName", width: 100, resizable: true,sortable:false},
		          {name: "opStatusName", index: "opStatusName", width: 100, resizable: true,sortable:false},
		          {name: "opUser", index: "opUser", width: 100, resizable: true,sortable:false}
		          ],
	    caption: "异常包裹处理列表",
	   	sortname: 'id',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pager",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0"}
	}); 
	jumbo.bindTableExportBtn("tbl-ad-package-deal");
	
	$j("#search").click(function(){
		var postData  = {}; 
		postData['adOrderId1']=$j("#adOrderId1").val();
		postData['wmsOrderId1']=$j("#wmsOrderId1").val();
		postData['extended1']=$j("#extended1").val();
		postData['adOrderType1']=$j("#adOrderType1").val();
		postData['status1']=$j("#status1").val();
		postData['trankNo1']=$j("#trankNo1").val();
		postData['lpCode1']=$j("#lpCode1").val();
		postData['fromTime']=$j("#fromTime").val();
		postData['endTime']=$j("#endTime").val();
		postData['opStatus1']=$j("#opStatus1").val();
		postData['skuId1']=$j("#skuId1").val();
		var url = $j("body").attr("contextpath")+"/json/adPackageList.json";
		$j("#tbl-ad-package-deal").jqGrid('setGridParam',{url:url,page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-ad-package-deal",{},url,postData);
	});
	
	
	$j("#tbl-ad-package-deal-log").jqGrid({
		datatype: "json",
		colNames:["ID","800ts工单编号","800ts工单类型","800ts处理结果","800ts反馈备注","WMS工单类型","WMS处理结果","WMS反馈备注","更新时间","数据来源"],
		colModel:[
		          {name: "id", index: "id", hidden: true},	
		          {name: "adOrderId", index: "adOrderId", width: 100, resizable: true,sortable:false},
		          {name: "adOrderType", index: "adOrderType", width: 100, resizable: true,sortable:false},
		          {name: "adStatus", index: "adStatus", width: 100, resizable: true,sortable:false},
		          {name: "adRemark", index: "adRemark", width: 100, resizable: true,sortable:false},
		          {name: "wmsOrderType", index: "wmsOrderType", width: 100, resizable: true,sortable:false},
		          {name: "wmsStatus", index: "wmsStatus", width: 100, resizable: true,sortable:false},
		          {name: "remark", lastUpdateTime: "remark", width: 100, resizable: true,sortable:false},
		          {name: "lastUpdateTime", index: "lastUpdateTime", width: 150, resizable: true,sortable:false},
		          {name: "typeName", index: "typeName", width: 150, resizable: true,sortable:false}
		          ],
	    caption: "操作日志",
	   	sortname: 'id',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pager2",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0"}
	}); 
	
	
	
	$j("#reset").click(function(){
		$j("#adOrderId1").val("");
		$j("#wmsOrderId1").val("");
		$j("#extended1").val("");
		$j("#adOrderType1").val("");
		$j("#status1").val(0);
		$j("#trankNo1").val("");
		$j("#lpCode1").val("");
		$j("#fromTime").val("");
		$j("#endTime").val("");
		$j("#opStatus1").val(3);
		$j("#skuId1").val("");
		
	});
	
	$j("#back").click(function(){
		$j("#div-list1").removeClass("hidden");
		$j("#div-list2").addClass("hidden");
	});
	
	$j("#commit").click(function(){
		var adOrderIdCommit=$j("#adOrderIdCommit").val();
		var wmsStatusCommit=$j("#wmsStatus2").val();
		var remarkCommit=$j("#remark2").val();
		if(confirm("确定要提交吗？")){
			var res = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/adPackageCommit.json",{"adOrderIdCommit":adOrderIdCommit,"wmsStatusCommit":wmsStatusCommit,"remarkCommit":remarkCommit});
		    if(null != res && res.result == 'success'){
				jumbo.showMsg("提交成功!");
				$j("#div-list1").removeClass("hidden");
				$j("#div-list2").addClass("hidden");
				var postData  = {}; 
				postData['adOrderId1']=$j("#adOrderId1").val();
				postData['wmsOrderType1']=$j("#wmsOrderType1").val();
				postData['extended1']=$j("#extended1").val();
				postData['adOrderType1']=$j("#adOrderType1").val();
				postData['status1']=$j("#status1").val();
				postData['trankNo1']=$j("#trankNo1").val();
				postData['lpCode1']=$j("#lpCode1").val();
				postData['fromTime']=$j("#fromTime").val();
				postData['endTime']=$j("#endTime").val();
				postData['opStatus1']=$j("#opStatus1").val();
				var url = $j("body").attr("contextpath")+"/json/adPackageList.json";
				$j("#tbl-ad-package-deal").jqGrid('setGridParam',{url:url,page:1,postData:postData}).trigger("reloadGrid");
				jumbo.bindTableExportBtn("tbl-ad-package-deal",{},url,postData);
				
		    }else {
		    	jumbo.showMsg("提交失败或系统异常,请联系管理员");
		    }
		}
	});
});


function showDetail(obj){
	$j("#wmsStatus2").empty();
	$j("<option value=''>请选择</option>").appendTo($j("#wmsStatus2"));
	$j("#remark2").val("");
	$j("#div-list1").addClass("hidden");
	$j("#div-list2").removeClass("hidden");
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	var detail = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/adPackageDetail.json", {"id" : id});
	
	$j("#adOrderId2").val(detail.adOrderId);
	$j("#extended2").val(detail.extended);
	$j("#lpCode2").val(detail.lpCode);
	$j("#adOrderType2").val(detail.adOrderType);
	$j("#adStatus2").val(detail.adStatus);
	$j("#trankNo2").val(detail.trankNo);
	$j("#skuId2").val(detail.skuId);
	$j("#quantity2").val(detail.quantity);
	$j("#adRemark2").val(detail.adRemark);
	$j("#wmsOrderId2").val(detail.wmsOrderId);
	$j("#wmsOrderType2").val(detail.wmsOrderType);
	$j("#returnInstruction2").val(detail.returnInstruction);
	if(detail.opStatus==1){
		$j("#commit").addClass("hidden");
		$j("#wmsStatus2").attr("disabled","disabled");
		$j("#remark2").attr("disabled","disabled");
		$j("#wmsStatus2").empty();
		$j("<option value='" + detail.wmsStatus + "'>"+ detail.wmsStatus +"</option>").appendTo($j("#wmsStatus2"));
		$j("#remark2").val(detail.remark);
	}else{
		$j("#commit").removeClass("hidden");
		$j("#wmsStatus2").removeAttr("disabled");
		$j("#remark2").removeAttr("disabled");
		var wmsResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findWmsResult.json", {"wmsOrderType" : detail.adOrderType});
		for(var idx in wmsResult){
			$j("<option value='" + wmsResult[idx].adResult + "'>"+ wmsResult[idx].adResult +"</option>").appendTo($j("#wmsStatus2"));
		}
		
	}
	
	var url = $j("body").attr("contextpath")+"/json/adPackageLog.json";
	var postData  = {}; 
	postData['adOrderIdLog']=detail.adOrderId;
	$j("#tbl-ad-package-deal-log").jqGrid('setGridParam',{url:url,page:1,postData:postData}).trigger("reloadGrid");
	
	$j("#adOrderIdCommit").val(id);
}
	