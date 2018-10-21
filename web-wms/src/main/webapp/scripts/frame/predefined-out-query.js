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
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	$j("#div-sta-list").addClass("hidden");
	$j("#div-sta-detail").removeClass("hidden");
	$j("#pmFile").val("");
	$j("#div-sta-detail").attr("staId",id);
	$j("#imp_staId").val(id);
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-sta-line").jqGrid('setGridParam',{page:1,url:baseUrl + "/gethistoricalOrderDetailList.json?sta.id=" + id}).trigger("reloadGrid");
	var pl=$j("#tbl-staList").jqGrid("getRowData",id);
	if(pl["intStaType"]==61){
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
	$j("#memo").val("");
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
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	var baseUrl = $j("body").attr("contextpath");
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	$j("#tbl-staList").jqGrid({
		datatype: "json",
		colNames: ["ID","作业单号","相关单据号","作业类型名称","作业单状态","平台店铺名称","创建时间","完成时间","创建人","计划执行总数量","收货人","联系电话","手机","收货地址","备注"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"code", index:"code", formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},
					{name:"slipCode",index:"slip_code",width:80,resizable:true},
					{name:"intStaType", index:"intStaType" ,width:70,resizable:true,formatter:'select',editoptions:staType},
					{name:"intStaStatus",index:"intStaStatus",width:70,resizable:true,formatter:'select',editoptions:staStatus},
					{name:"channelName", index:"channelName",width:130,resizable:true},
					{name:"createTime",index:"createTime",width:130,resizable:true},
					{name:"finishTime",index:"finishTime",width:130,resizable:true},
					{name:"creater", index:"creater",width:100,resizable:true},
					{name:"totalQty", index:"totalQty",width:70,resizable:true},
					{name:"receiver",index:"receiver",width:100,resizable:true},
					{name:"telephone",index:"telephone",hidden: true},
					{name:"mobile", index:"mobile" ,hidden: true},
					{name:"address", index:"address",hidden: true},
					{name:"memo", index:"memo",width:100,hidden:true}],
		caption: "预定义出库列表",
	   	sortname: 'sta.create_time',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	
	$j("#tbl-sta-line").jqGrid({
		datatype: "json",
		colNames: ["ID","isSnSku",i18("SKUCODE"),i18("BARCODE"),i18("JMCODE"),i18("SKU_NAME"),i18("KEY_PROPS"),i18("INV_STATUS"),i18("PLAN_QTY")],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"isSnSku",index: "isSnSku", hidden:true},
		           	{name:"skuCode",index:"skuCode",width:120,resizable:true},
					{name:"barCode",index:"barCode",width:120,resizable:true},
					{name:"jmcode", index:"jmcode" ,width:120,resizable:true},
					{name:"skuName", index:"skuName",width:120,resizable:true},
					{name:"keyProperties",index:"keyProperties",width:120,resizable:true},
					{name:"intInvstatusName",index:"intInvstatusName",width:120,resizable:true},
					{name:"quantity", index:"quantity",width:80,resizable:true}
					
					
					],
		caption: "作业明细单",
	   	sortname: 'ID',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerDetail",
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
	
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
		$j("#queryForm select").val("");
	});
	
	$j("#search").click(function(){
		refreshStaList();
	});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#break").click(function(){
		$j("#div-sta-list").removeClass("hidden");
		$j("#div-sta-detail").addClass("hidden");
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
});


function importReturn(){
	refreshStaList();
	initButtion(2,"配货中");
	jumbo.showMsg("导入占用执行成功");
}
