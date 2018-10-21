$j.extend(loxia.regional['zh-CN'],{
	SKUCODE : "SKU编码",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	SKU_NAME : "商品名称",
	KEY_PROPS : "扩展属性",
	PLAN_QTY : "计划量",
	COMFIRMED_QTY : "执行量",
	INV_STATUS : "库存状态",
	SKU_COST : "库存成本"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function showMsg(msg){
	jumbo.showMsg(msg);
}
function showDetail(obj){
	var baseUrl = $j("body").attr("contextpath");
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	$j("#tabs").tabs();
	$j("#divMain").addClass("hidden");
	$j("#divDetail").removeClass("hidden");
	$j("#staid").val(id);
	var data = $j("#tbl_sta").jqGrid("getRowData",id);
	var staCode=data["code"];
	
	$j("#staCode_").text(data["code"]);
	$j("#staSlipCode").text(data["slipCode"]);
	$j("#staStatus").text(tr.find("td[aria-describedby$='intStatus']").text());
	
	$j("#staOwner").text(data["owner"]);
	$j("#staSkuQty").text(data["skuQty"]);
	$j("#staSkuTypeNum").text(data["skuTypeNum"]);
	$j("#staPickAreasNum").text(data["pickAreasNum"]);
	$j("#staWarehouseAreasNum").text(data["warehouseAreasNum"]);
	$j("#createTime").text(data["createTime"]);
	if(data["intStatus"]!=2 && data["intStatus"]!=8){
		$j("#addPickingTask").attr("disabled",true);
		$j("#addPickingTask").removeClass("confirm")
	}else{
		$j("#addPickingTask").attr("disabled",false);
		$j("#addPickingTask").addClass("confirm")
	}
	$j("tbl_sta_detail_collection tr:gt(0)").remove();
	$j("#tbl_sta_detail_collection").jqGrid('setGridParam',{page:1,url:baseUrl + "/getOutboundDetailListCollection.json?staid=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_detail_collection",{},
			baseUrl + "/getOutboundDetailListCollection.json",{"staid":id});
	
	$j("tbl_sta_pick_task tr:gt(0)").remove();
	$j("#tbl_sta_pick_task").jqGrid('setGridParam',{page:1,url:baseUrl + "/getOutboundDickingTaskDetailList.json?staid=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_pick_task",{},
			baseUrl + "/getOutboundDickingTaskDetailList.json",{"staid":id});
	
	$j("tbl_sta_pick_zzx_detail tr:gt(0)").remove();
	$j("#tbl_sta_pick_zzx_detail").jqGrid('setGridParam',{page:1,url:baseUrl + "/getOutboundDickingZzxDetailList.json?staCode=" + staCode}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_pick_zzx_detail",{},
			baseUrl + "/getOutboundDickingZzxDetailList.json",{"staCode":staCode});
	
	$j("tbl_sta_line tr:gt(0)").remove();
	$j("#tbl_sta_line").jqGrid('setGridParam',{page:1,url:baseUrl + "/getOutboundDetailList.json?staid=" + id}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_line",{},
			baseUrl + "/getOutboundDetailList.json",{"staid":id});
	
	//var result=loxia.syncXhr(loxia.getTimeUrl($j("body").attr("contextpath") + "/findOutBound.json?staid="+id));
	//var result=loxia.syncXhr($j("body").attr("contextpath") + "/findOutBound.json",{"staid":id});
	var result=loxia.syncXhr($j("body").attr("contextpath")+"/json/findOutBound.do",{"staid":id});
	if(null!=result){
		if(result["result"]=="success"){
			$j("#transportPra").text(result["msg"]["transportPra"]);
			$j("#crd").val(result["msg"]["crd"]);
			$j("#nfsStoreCode").val(result["msg"]["nfsStoreCode"]);
			$j("#city").val(result["msg"]["city"]);
			$j("#zip").val(result["msg"]["zip"]);
			$j("#address1").val(result["msg"]["address1"]);
			$j("#address2").val(result["msg"]["address2"]);
			$j("#address3").val(result["msg"]["address3"]);
			$j("#address4").val(result["msg"]["address4"]);
			$j("#companyName").text(result["msg"]["companyName"]);
			$j("#nikePo").text(result["msg"]["nikePo"]);
			$j("#vasCode").text(result["msg"]["vasCode"]);
			$j("#divisionCodeTranslation").text(result["msg"]["divisionCodeTranslation"]);
			$j("#slip_code1").text(result["msg"]["slipcode1"]);
			$j("#slip_code2").text(result["msg"]["slipcode2"]);
			$j("#lfId").val(result["msg"]["lfId"]);
			$j("#planDateTime").text(result["msg"]["planDateTime"]);
			
			
		}
		
	}
	/*jumbo.bindTableExportBtn("tbl_sta_package",{},
			baseUrl + "/trunkdetailinfo.json",{"staid":id});
	*/
	//$j("#pmFile").val("");
	//$j("#div-sta-detail").attr("staId",id);
	//$j("#imp_staId").val(id);
	//var baseUrl = $j("body").attr("contextpath");
	//$j("#tbl-sta-line").jqGrid('setGridParam',{page:1,url:baseUrl + "/gethistoricalOrderDetailList.json?sta.id=" + id}).trigger("reloadGrid");
	//var pl=$j("#tbl-staList").jqGrid("getRowData",id);
	/*if(pl["intStaType"]==61){
		$j("#canceled").addClass("hidden");
	} else {
		$j("#canceled").removeClass("hidden");
	}
	if(pl["intStaStatus"] == 2 && (pl["intStaType"] == 62 || pl["intStaType"] == 64)) {
		$j("#packing").removeClass("hidden");
	} else {
		$j("#packing").addClass("hidden");
	}
	if(pl["intStaStatus"] == 8 && (pl["intStaType"] == 62 || pl["intStaType"] == 64)) {
		$j("#cancelPacking").removeClass("hidden");
	} else {
		$j("#cancelPacking").addClass("hidden");
	}
	$j("#div-sta-detail").attr("staType",pl["intStaType"]);
	$j("#div-sta-detail").attr("staStatus",pl["intStaStatus"]);
	initButtion(pl["intStaStatus"],"");
	$j("#createTime").text(pl["createTime"]);
	$j("#finishTime").text(pl["finishTime"]);
	$j("#code").text(pl["code"]);
	$j("#slipCode").text(pl["slipCode"]);
	$j("#shopId").text(pl["channelName"]);
	$j("#sta_memo").text(pl["memo"]);
	$j("#operator").text(pl["creater"]);
	$j("#receiver").text(pl["receiver"]);
	$j("#telephone").text(pl["telephone"]);
	$j("#mobile").text(pl["mobile"]);
	$j("#address").text(pl["address"]);
	$j("#status").text(tr.find("td[aria-describedby$='intStaStatus']").text());
	$j("#sta_type").text(tr.find("td[aria-describedby$='intStaType']").text());
	$j("#tnFile").val("");
	$j("#memo").val("");*/
}

function initButtion(status,statusName){
	if(status == 1){
		$j("#tr_II").addClass("hidden");
		$j("#tr_I").removeClass("hidden");
		$j("#canceled").removeClass("hidden");
	} else if(status == 2){
		$j("#tr_II").removeClass("hidden");
		$j("#tr_I").removeClass("hidden");
		$j("#canceled").removeClass("hidden");
	} else if(status == 8){
		$j("#tr_II").removeClass("hidden");
		$j("#tr_I").removeClass("hidden");
		$j("#canceled").removeClass("hidden");
	}
	else {
		$j("#tr_II").addClass("hidden");
		$j("#tr_I").addClass("hidden");
		$j("#canceled").removeClass("hidden").addClass("hidden");
	}
	if(statusName != ""){
		$j("#status").text(statusName);
	}
}

function refreshStaList(){
	var url = $j("body").attr("contextpath") + "/queryPredefinedOutStaList.json";
	$j("#tbl-staList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
}

$j(document).ready(function (){
	//initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	var baseUrl = $j("body").attr("contextpath");
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	$j("#tbl_sta").jqGrid({
		datatype: "json",
		url:baseUrl + "/getoutboundPickingTasklist.json?sta.intStaStatus=2",
		colNames: [ "ID","作业单号","创建时间" ,"相关单据号","作业类型","店铺","计划出库数量","SKU种类数","拣货区域数量","仓库区域数量","作业单状态"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"code", index:"code", formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:120, resizable:true},
					{name:"createTime",index:"createTime",width:140,resizable:true},
					{name:"slipCode",index:"slipCode",width:150,resizable:true},
					{name:"intType", index:"intType" ,width:100,resizable:true,formatter:'select',editoptions:staType},
					
					{name:"owner", index:"owner",width:150,resizable:true},
					{name:"skuQty", index:"skuQty",width:90,resizable:true},
					{name:"skuTypeNum",index:"skuTypeNum",width:90,resizable:true},
					{name:"pickAreasNum", index:"pickAreasNum",width:90,resizable:true},
					{name:"warehouseAreasNum", index:"warehouseAreasNum",width:90,resizable:true},
					{name:"intStatus",index:"intStatus",width:90,resizable:true,formatter:'select',editoptions:staStatus}],
					
					//{name:"receiver",index:"receiver",width:100,resizable:true},
					//{name:"telephone",index:"telephone",hidden: true},
					//{name:"mobile", index:"mobile" ,hidden: true},
					//{name:"address", index:"address",hidden: true},
					//{name:"memo", index:"memo",width:100,hidden:true}],
		caption: "出库作业单",
	   	sortname: 'sta.create_time',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#tbl_sta_page",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	
	$j("#tbl_sta_detail_collection").jqGrid({
		datatype: "json",
		colNames: ["ID","拣货区域编码","仓库区域编码","计划出库数量","实际拣货数量"],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"diekingAreaCode",index: "diekingAreaCode", width:150,resizable:true},
		           	{name:"whAreaCode",index:"whAreaCode",width:150,resizable:true},
					{name:"planQuantity",index:"planQuantity",width:150,resizable:true},
					{name:"realityQuantity", index:"realityQuantity" ,width:150,resizable:true}
					/*{name:"skuName", index:"skuName",width:120,resizable:true},
					{name:"keyProperties",index:"keyProperties",width:120,resizable:true},
					{name:"intInvstatusName",index:"intInvstatusName",width:120,resizable:true},
					{name:"quantity", index:"quantity",width:80,resizable:true}
					*/
					],
		caption: "出库明细汇总",
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#tbl_sta_detail_collection_page",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		/*loadComplete:function(){
			loxia.initContext($j("#tbl_sta_detail_collection"));
			//判断是否显示导入SN
			//var b = false;
			$j("#tbl_sta_detail_collection tr[id]").each(function(i,tr){
				var rowData=$j("#tbl-sta-line").jqGrid("getRowData",$j(tr).attr("id"));
				if(rowData!=null){
					$j("#addPickingTask").attr("disabled",true);
					$j("#addPickingTask").removeClass("confirm")
				}
			});
			
		}*/
	});
	$j("#tbl_sta_pick_task").jqGrid({
		datatype: "json",
		colNames: ["ID","拣货任务号","创建时间","计划出库数量","实际拣货数量","拣货状态","是否VAS拣货任务"],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"batchCode",index: "batchCode", width:150,resizable:true},
		           	{name:"createTime",index:"createTime",width:150,resizable:true},
					{name:"planQuantity",index:"planQuantity",width:120,resizable:true},
					{name:"realityQuantity", index:"realityQuantity" ,width:120,resizable:true},
					{name:"taskStatus", index:"taskStatus",width:120,resizable:true},
					{name:"vas", index:"vas",width:120,resizable:true}
					/*{name:"keyProperties",index:"keyProperties",width:120,resizable:true},
					{name:"intInvstatusName",index:"intInvstatusName",width:120,resizable:true},
					{name:"quantity", index:"quantity",width:80,resizable:true}
					*/
					],
		caption: "拣货任务",
	   	sortname: 'dk.create_time',
	    multiselect: true,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#tbl_sta_pick_task_page",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete:function(){
			loxia.initContext($j("#tbl-sta-line"));
			//判断是否显示导入SN
			var b = false;
			$j("#tbl-sta-line tr[id]").each(function(i,tr){
				var rowData=$j("#tbl-sta-line").jqGrid("getRowData",$j(tr).attr("id"));
				if(rowData["isSnSku"]==1){
					b = true;
				}
			});
			if(b){
				$j("#divSnImport").removeClass("hidden");
			} else {
				$j("#divSnImport").addClass("hidden");
			}
		}
	});
	$j("#tbl_sta_pick_zzx_detail").jqGrid({
		datatype: "json",
		colNames: ["周转箱编码","拣货任务号","商品编码","商品条码","商品名称","货号","关键属性","库存状态","拣货数量"],
		colModel: [ {name:"boxCode",index: "boxCode", width:100,resizable:true},
		           	{name:"batchCode",index:"batchCode",width:100,resizable:true},
					{name:"skuCode",index:"skuCode",width:100,resizable:true},
					{name:"skuBarCode", index:"skuBarCode" ,width:100,resizable:true},
					{name:"skuName", index:"skuName",width:150,resizable:true},
					{name:"skuSupplierCode",index:"skuSupplierCode",width:100,resizable:true},
					{name:"skuKeyProperties",index:"skuKeyProperties",width:100,resizable:true},
					{name:"skuInvStatus", index:"skuInvStatus",width:80,resizable:true},
					{name:"diekingQuantity", index:"diekingQuantity",width:80,resizable:true}
					
					],
		caption: "拣货周转箱明细",
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#tbl_sta_pick_zzx_detail_page",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete:function(){
			loxia.initContext($j("#tbl-sta-line"));
			//判断是否显示导入SN
			var b = false;
			$j("#tbl-sta-line tr[id]").each(function(i,tr){
				var rowData=$j("#tbl-sta-line").jqGrid("getRowData",$j(tr).attr("id"));
				if(rowData["isSnSku"]==1){
					b = true;
				}
			});
			if(b){
				$j("#divSnImport").removeClass("hidden");
			} else {
				$j("#divSnImport").addClass("hidden");
			}
		}
	});
	$j("#tbl_sta_line").jqGrid({
		datatype: "json",
		colNames: ["ID","SKU编码","SKU条码","商品名称","库存状态","扩展属性","数量"],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"skuCode",index: "skuCode", width:150,resizable:true},
		           	{name:"barCode",index:"barCode",width:150,resizable:true},
					{name:"skuName",index:"skuName",width:150,resizable:true},
					{name:"intInvstatusName", index:"intInvstatusName" ,width:130,resizable:true},
					{name:"keyProperties", index:"keyProperties",width:130,resizable:true},
					{name:"quantity",index:"quantity",width:120,resizable:true}
					/*{name:"intInvstatusName",index:"intInvstatusName",width:120,resizable:true},
					{name:"quantity", index:"quantity",width:80,resizable:true}
					*/
					
					],
		caption: "出库明细",
	   	sortname: 'ID',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#tbl_sta_line_page",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete:function(){
			loxia.initContext($j("#tbl-sta-line"));
			//判断是否显示导入SN
			var b = false;
			$j("#tbl-sta-line tr[id]").each(function(i,tr){
				var rowData=$j("#tbl-sta-line").jqGrid("getRowData",$j(tr).attr("id"));
				if(rowData["isSnSku"]==1){
					b = true;
				}
			});
			if(b){
				$j("#divSnImport").removeClass("hidden");
			} else {
				$j("#divSnImport").addClass("hidden");
			}
		}
	});
	
	
	//查询
	$j("#query").click(function(){
		var url = baseUrl + "/getoutboundPickingTasklist.json";
		$j("#tbl_sta").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("formQuery")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_sta",{"intType":"whSTAType","intStatus":"whSTAStatus"},
			url,loxia._ajaxFormToObj("formQuery"));
	});
	
//	bindTableExportBtn("tbl_sta",{"staType":"whSTAType"});
	
	
	//查询重置
	$j("#btnReset").click(function(){
		$j("#formQuery input,select").val("");
		$j("#status").val("2");
	});
	
	
	/*
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
		$j("#queryForm select").val("");
	});
	
	$j("#search").click(function(){
		refreshStaList();
	});
	*/
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#btnBack").click(function(){
		$j("#divMain").removeClass("hidden");
		$j("#divDetail").addClass("hidden");
	});
	
	/***模版导出***/
	$j("#output").click(function(){
		var iframe = document.createElement("iframe");
		iframe.src = loxia.getTimeUrl(baseUrl + "/warehouse/predefinedOutExport.do?sta.id="+$j("#div-sta-detail").attr("staId"));
		iframe.style.display = "none";
		$j("#download").append($j(iframe));
	});
	
	
	/**
	 * 导入占用
	 */
	$j("#input").click(function(){
		var file = $j("#tnFile").val();
		var postfix = file.split(".")[1];
		if(postfix != "xls" && postfix != "xlsx"){
			jumbo.showMsg(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
			return;
		}
		loxia.lockPage();
		$j("#importForm").attr("action", loxia.getTimeUrl(baseUrl + "/warehouse/predefinedOutImport.do?sta.id="+$j("#div-sta-detail").attr("staId")));
		loxia.submitForm("importForm");
	});
	
	//系统自动占用
	$j("#occupation").click(function(){
		if(confirm("您确定要执行系统自动占用？")){
			loxia.lockPage();
			loxia.asyncXhrPost(baseUrl + "/predefinedOutOccupation.json",
				{'sta.id':$j("#div-sta-detail").attr("staId")},
				{
				success:function (data) {
					loxia.unlockPage();
					if(data&&data.success){
						refreshStaList();
						initButtion(2,"配货中");
						jumbo.showMsg("系统自动占用成功！");
					} else if(data && data.error) {
						jumbo.showMsg(data.msg);
					} else {
						jumbo.showMsg("操作异常");
					}
				   },
				error:function(data){
					loxia.unlockPage();
					jumbo.showMsg("操作异常");
					window.location=loxia.getTimeUrl(entry);
				}
				});
		}
	});
	
	//取消
	$j("#canceled").click(function (){
		if($j("#memo").val() == ""){
			jumbo.showMsg("备注不能为空");
			return false;
		}
		
		if(confirm("您确认要取消此作业单？")){
			loxia.lockPage();
			loxia.asyncXhrPost(baseUrl + "/predefinedOutCanceled.json",
				{'sta.id':$j("#div-sta-detail").attr("staId"),'sta.memo':$j('#memo').val()},
				{
					success:function (data) {
					loxia.unlockPage();
					if(data&&data.success){
						refreshStaList();
						initButtion(-1,"取消已处理");
						jumbo.showMsg("作业单取消成功！");
					} else if(data && data.error) {
						jumbo.showMsg(data.msg);
					} else {
						jumbo.showMsg("操作异常");
					}
				   },
				error:function(data){
					loxia.unlockPage();
					jumbo.showMsg("操作异常");
					window.location=loxia.getTimeUrl(entry);
				}
				});
		}
	});
	
	// 装箱
	$j("#packing").click(function(){ 
		var id = $j("#div-sta-detail").attr("staId");
		if (id != null){
			var rs = loxia.syncXhrPost( baseUrl + "/vmireturnpacking.json",{"staID":id});
			if(rs && rs["result"] == 'success'){
				refreshStaList();
				$j("#break").click();
				jumbo.showMsg("操作成功");
			}else if(rs && rs["result"] == 'error'){
				jumbo.showMsg("操作失败<br/>" + rs["message"]);
			}
		}
	});
	// 取消装箱
	$j("#cancelPacking").click(function(){ 
		if(confirm("取消装箱将删除所有已装箱信息，确认是否执行？")){
			var id = $j("#div-sta-detail").attr("staId");
			if (id != null){
				var rs = loxia.syncXhrPost( baseUrl + "/cancelReturnPacking.json",{"staID":id});
				if(rs && rs["result"] == 'success'){
					refreshStaList();
					$j("#break").click();
					jumbo.showMsg("操作成功");
				}else if(rs && rs["result"] == 'error'){
					jumbo.showMsg("操作失败<br/>" + rs["message"]);
				}
			}
		}
	});
	
	//执行
	$j("#execution").click(function (){
		var isNeedImpSn = !$j("#divSnImport").hasClass("hidden");;
		
		if(isNeedImpSn){
			//查询是否已经导入SN
			var rs = loxia.syncXhr($j("body").attr("contextpath") + "/findIsImportSn.json",{"staId":$j("#div-sta-detail").attr("staId")});
			if(rs.result == "success" && rs.msg == false){
				jumbo.showMsg("请导入出库商品SN号!");//请导入出库商品SN号!
				return ;
			}
		}
		var staType = $j("#div-sta-detail").attr("staType");
		var staStatus = $j("#div-sta-detail").attr("staStatus");
		if((staType == 62 || staType == 64) && staStatus == 8){
			if(window.confirm("确认执行出库？如有差异系统也将执行，差异库存将被释放！")){
				var id = $j("#div-sta-detail").attr("staId")
				if (id != null){
					var rs = loxia.syncXhrPost( baseUrl + "/executepartlyoutbound.json",{"staid":id});
					if(rs && rs["result"] == 'success'){
						jumbo.showMsg("操作成功");
						$j("#break").click();
					}else if(rs && rs["result"] == 'error'){
						jumbo.showMsg("操作失败<br/>" + rs["message"]);
					}
				}
			}
		}else{
			if(confirm("您确认要执行此作业单？")){
				loxia.lockPage();
				loxia.asyncXhrPost(baseUrl + "/predefinedOutExecution.json",
					{'sta.id':$j("#div-sta-detail").attr("staId"),'sta.code':$j("#code").text()},
					{
						success:function (data) {
						loxia.unlockPage();
						if(data&&data.success){
							refreshStaList();
							if(data.sta==4){
								initButtion(-1,"已转出");
							}else{
								initButtion(-1,"已完成");
							}
							jumbo.showMsg("系统执行出库成功！");
							var baseUrl = $j("body").attr("contextpath");
							
							$j("#tbl-sta-line").jqGrid('setGridParam',{page:1,url:baseUrl + "/gethistoricalOrderDetailList.json?sta.id=" + data.id}).trigger("reloadGrid");
						} else if(data && data.error) {
							jumbo.showMsg(data.msg);
						} else {
							jumbo.showMsg("操作异常");
						}
					   },
					error:function(data){
						loxia.unlockPage();
						jumbo.showMsg("操作异常");
						window.location=loxia.getTimeUrl(entry);
					}
					});
			}
		}
	});
	
	$j("#break").click(function(){
		$j("#divMain").removeClass("hidden");
		$j("#divDetail").addClass("hidden");
	});
	
	$j("#addPickingTask").click(function(){
		var splitRule = $j("#select").val();
		var taskNum = $j("#taskNum").val();
		var staCode=$j("#staCode_").text();
		var staid=$j("#staid").val();
		var ids = $j("#tbl_sta_pick_task").jqGrid('getDataIDs');
		if(ids.length>0){
			 jumbo.showMsg("任务已生成,不可再生成任务！");
			return;
		}
		 if(taskNum!=""){
		      if(!((/^(\+|-)?\d+$/.test( taskNum ))&& taskNum>0)){  
			        
			        jumbo.showMsg("数量中请输入正整数！");
			        $j("#taskNum").val("")  
			        return false;  
			   }  
		 }
		/*if(splitRule == ""){
			jumbo.showMsg("请选择拆分规则！");
			return;
		}*/
		/*var ids = $j("#tbl_sta").jqGrid('getGridParam','selarrrow');*/
		/*if(ids.length>0){
			var postData = {};
			for(var i in ids){
				postData['staIds[' + i + ']']=ids[i];
			}
			postData['lpcode']=lpcode;
			postData['taskNum']=taskNum;
			postData['isSelectAll']=false;
			operatorPost(baseUrl + "/updateStaLpcode.json",postData);
		} else {
			jumbo.showMsg("请选择好须要修改的作业单！");
		}*/
		var postData = {};
		postData['splitRule']=splitRule;
		postData['taskNum']=taskNum;
		postData['staCode']=staCode;
		postData['staid']=staid;
		var rs = loxia.syncXhrPost(baseUrl + "/savePickingTask.json",postData);
		if(rs){
			if(rs.result == 'success'){
				jumbo.showMsg("拣货任务生成成功！");
				window.location.reload();//刷新当前页面
			}else {
				jumbo.showMsg(rs.message);
			}
		} else {
			jumbo.showMsg("数据操作异常");
		}
		return false;
		
	});
	
	/**
	 * PDA拣货条码打印
	 */
	$j("#printPdaBarCode").click(function(){
		var ids = $j("#tbl_sta_pick_task").jqGrid('getGridParam','selarrrow');
		if(ids.length==0){
			jumbo.showMsg("请选择拣货任务");
			return;
		}
		loxia.lockPage();
		jumbo.showMsg("PDA拣货条码打印");
		var url = $j("body").attr("contextpath") + "/printPdaBarCodeRtw.json?plIds=" +ids;
		printBZ(loxia.encodeUrl(url),false);
		loxia.unlockPage();
	});
	/**
	 * 拣货单打印
	 */
	$j("#printDiekingLine").click(function(){
		var staid=$j("#staid").val();
		var staCode=$j("#staCode_").text();
		var ids = $j("#tbl_sta_pick_task").jqGrid('getGridParam','selarrrow');
		if(ids.length==0){
			jumbo.showMsg("请选择拣货任务");
			return;
		}
		loxia.lockPage();
		var url=window.$j("body").attr("contextpath")+"/diekingLinePrint.json?staid="+staid+"&plList="+ids;
		printBZ(loxia.encodeUrl(url),true);
		loxia.unlockPage();
	});
	
	
	//sn号导入
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg("请选择正确的Excel导入文件");//请选择正确的Excel导入文件
			return false;
		}
		loxia.lockPage();
		$j("#importFormSN").attr("action",$j("body").attr("contextpath") + "/warehouse/outboundSnImport.do");
		$j("#importFormSN").submit();
	});
	
	$j("#outputInfo").click(function (){
		var id = $j("#div-sta-detail").attr("staId");
		var url = baseUrl + "/printinventoryoccupay.json?sta.id=" + id;
		printBZ(loxia.encodeUrl(url),true);
	});
	
	$j("#printSendInfo").click(function(){
		loxia.lockPage();
		var id = $j("#div-sta-detail").attr("staId");
		var url = $j("body").attr("contextpath") + "/printoutboundsendinfo.json?sta.id=" + id;
		printBZ(loxia.encodeUrl(url),true);
		loxia.unlockPage();
	});
	
	//---------------包材导入-----
	$j("#inputPM").click(function (){
		var file = $j("#pmFile").val();
		var postfix = file.split(".")[1];
		if(postfix != "xls" && postfix != "xlsx"){
			jumbo.showMsg("请选择导入包材数据");
			return;
		}
		loxia.lockPage();
		$j("#importPMForm").attr("action", loxia.getTimeUrl(baseUrl + "/warehouse/packagingMaterialsImport.do?sta.id="+$j("#div-sta-detail").attr("staId")));
		loxia.submitForm("importPMForm");
	});
	
	var warehouse = loxia.syncXhr($j("body").attr("contextpath") + "/getWHByOuId.json");
	if(!warehouse.exception){
		if(warehouse.warehouse.isNeedWrapStuff){
			$j("#packaging_materials").removeClass("hidden");
		} 
	}
	
	$j("#save").click(function (){
		var result=loxia.syncXhr($j("body").attr("contextpath")+"/json/findCrwStaByStaId.do",{"staid":$j("#staid").val()});
	    if(null!=result&&result["result"]){
	    	var result1=loxia.syncXhr($j("body").attr("contextpath")+"/json/saveCrwStaByStaId.do",
	    			{"staid":$j("#staid").val(),"lfId":$j("#lfId").val(),"crd":$j("#crd").val(),
	    		     "nfsStoreCode":$j("#nfsStoreCode").val(),"city":$j("#city").val(),"zip":$j("#zip").val(),
	    		     "address1":$j("#address1").val(),"address2":$j("#address2").val(),"address3":$j("#address3").val(),
	    		     "address4":$j("#address4").val()
	    	});
	    	if(null!=result1&&result1["result"]){
	    		jumbo.showMsg("保存成功");
				return;
	    	}else{
	    		jumbo.showMsg("保存失败");
				return;
	    	}
	    }else{
	    	jumbo.showMsg("装箱后不能修改");
			return;
	    }
	});
	
});


function importReturn(){
	refreshStaList();
	initButtion(2,"配货中");
	jumbo.showMsg("导入占用执行成功");
}
function operatorPost(url,postData){
	var rs = loxia.syncXhrPost(url,postData);
	if(rs){
		if(rs.result == 'success'){
			jumbo.showMsg("修改成功！");
			var url = $j("body").attr("contextpath") + "/staLpcodeQuery.json";
			$j("#tbl-orderList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
			jumbo.bindTableExportBtn("tbl-orderList",{"intType":"whSTAType","intStatus":"whSTAStatus"},url,loxia._ajaxFormToObj("queryForm"));
		}else {
			jumbo.showMsg(rs.message);
		}
	} else {
		jumbo.showMsg("数据操作异常");
	}
	return false;
}



