$j.extend(loxia.regional['zh-CN'],{
	DISTRIBUTION_LIST_SELECT : "请选择配货清单",
	
	// colNames: ["ID",,],
	// colNames: ["ID","","","","","","","",""],
	CODE : "作业单号",
	INTSTATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	SHOPID : "淘宝店铺ID",
	LPCODE : "物流服务商简称",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	INT_LIST : "作业单列表",
//	OUTPUTCOUNT : "打印次数",
	
	BATCHNO : "配货批次号",
	CREATETIME : "创建时间",
	PLAN_BILL_COUNT : "计划配货单据数",
	PLAN_SKU_QTY : "计划配货商品件数",
	INTSTATUS : "状态",
	NEW_LIST : "新建配货清单列表",
	
	BATCHNO : "配货批次号",
	CREATETIME : "创建时间",
	PLANBILLCOUNT : "计划配货单据数", 
	PLAYSKUQTY : "计划配货商品件数",
	INTSTATUS : "状态",
	WAITING_LIST : "待配货清单列表",
	
	DISPATCH_FAILED : "由于您的配货单中所有作业单都失败，所以您的配货单被取消",
	DIAPATCH_CANCEL : "配货清单已取消",
	OPERATING : "配货清单打印中，请等待...",
	TRUNKPACKINGLISTINFO:"装箱清单打印中，请等待...",
	DELIVERYINFO:"物流面单打印中，请等待...",
	OPETION : "操作",
	PRINGT : "打印",
	CREATE_DISABLE_PRINT : "当前订单状态为‘新建状态’不能打印",
	CANEL_DISABLE_PRINT : "当前订单状态为‘取消已处理’不能打印",
	FAILED_DISABLE_PRINT : "当前订单状态为‘配货失败’不能打印"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

function loadOrderDetail(url){
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	
	$j("#tbl-orderDetail").jqGrid({
		url:url,
		datatype: "json",
		// colNames:
		// ["ID","序列","作业单号","状态","相关单据号","作业类型名称","淘宝店铺ID","物流服务商简称","创建时间","计划执行总件数"],
		colNames: ["ID","status","序列",i18("CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("SHOPID"),"物流服务",i18("CREATETIME"),"计划总件数",i18("OPETION")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "status",hidden: true},
		           {name: "index", index: "index", width: 20, resizable: true},
		           {name: "code", index: "code", width: 100, resizable: true},
		           {name: "intStatus", index: "intStatus", width: 40, resizable: true,formatter:'select',editoptions:intStatus},
		           {name: "refSlipCode", index: "refSlipCode",width: 100, resizable: true},
		           {name: "intType", index: "intType", width: 90, resizable: true,formatter:'select',editoptions:staType},
		           {name: "shopId", index: "shopId", width: 120, resizable: true},
		           {name: "lpcode", index: "lpcode", width: 70, resizable: true,formatter:'select',editoptions:trasportName},
	               {name: "createTime",index:"createTime",width:120,resizable:true},
	               {name: "stvTotal", index: "stvTotal", width: 60, resizable: true},
	               {name: "idForBtn", width: 80,resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("PRINGT"), onclick:"printSingleDelivery(this,event);"}}}],
		caption: i18("INT_LIST"),// 作业单列表
		rowNum:-1,
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
		sortorder: "asc",
		gridComplete : function(){
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
}

function reloadOrderDetail(url,postData){
	$j("#tbl-orderDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl(url)}).trigger("reloadGrid");
}
 
// 单张物流面单的打印 （新建&&取消已处理&&配货失败） 三个不打印
function printSingleDelivery(tag,event){
	var id = $j(tag).parents("tr").attr("id");
	var data=$j("#tbl-orderDetail").jqGrid("getRowData",id);
	var intStatus = data["intStatus"];
	if(intStatus == 1){
		jumbo.showMsg(i18("CREATE_DISABLE_PRINT"));
		return;
	} else if (intStatus == 17){
		jumbo.showMsg(i18("CANEL_DISABLE_PRINT"));
		return;
	}else if(intStatus == 20){
		jumbo.showMsg(i18("FAILED_DISABLE_PRINT"));
		return;
	}
	loxia.lockPage();
	var staCode = data['code'];
	if(isEmsOlOrder && ('EMS' == data['lpcode'] || 'EMSCOD' == data['lpcode'])){
		var staCodes = [];
		staCodes[0] = staCode;
		var plid = $j("#pickingListId").html();
		jumbo.emsprint(staCodes,plid);
	}else{
		var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?sta.id=" + id;
		printBZ(loxia.encodeUrl(url),true);
	}
	loxia.unlockPage();
} 

var isInitOrderDetail = true;
function orderDetail(url){
	if(isInitOrderDetail){
		loadOrderDetail(url);
		isInitOrderDetail = false;
	}else{
		reloadOrderDetail(url);
	}
}

function showDetail(tag){
	$j("#divTbDetial").removeClass("hidden");
	var baseUrl = $j("body").attr("contextpath");
	$j("#div1").addClass("hidden");
	$j("#div2").removeClass("hidden");
	var id = $j(tag).parents("tr").attr("id");
	$j("#div2").attr("plId",id);
	$j("#pickingListId").html(id);
	var tr = $j(tag).parents("tr");
	var data=$j("#tbl-waittingList").jqGrid("getRowData",id);
	var rs = loxia.syncXhrPost(baseUrl + "/getPickingList.json",{"pickingListId":id});
	if(rs && rs["pickingList"]){
		$j("#dphCode").html( rs["pickingList"]["code"]);
		$j("#planBillCount").html( rs["pickingList"]["planBillCount"]);
		$j("#planSkuQty").html( rs["pickingList"]["planSkuQty"]);
		$j("#pickingTime").html( rs["pickingList"]["pickingTime"]);
		
		$j("#checkedBillCount").html( rs["pickingList"]["checkedBillCount"]);
		$j("#checkedTime").html( rs["pickingList"]["checkedTime"]);
		$j("#checkedSkuQty").html( rs["pickingList"]["checkedSkuQty"]);
		
		$j("#sendSkuQty").html( rs["pickingList"]["sendSkuQty"]);
		$j("#sendStaQty").html( rs["pickingList"]["sendStaQty"]);
		$j("#lastSendTime").html( rs["pickingList"]["lastSendTime"]);
	}
	$j("#dphStatus").html(tr.find("td[aria-describedby='tbl-waittingList_intStatus']").html());
	$j("#creator").html(data["creator.userName"]);
	$j("#operator").html(data["operator.userName"]);
	$j("#tdIdLpcode").html( data["lpcode"]);
	orderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+id);
	$j("#gbox_tbl-waittingList").appendTo($j("#div-waittingList"));
	$j("#hidden-waittingList").addClass("hidden");
	
	if(data["intStatus"] == '19'){
		var transRs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getTransByPlId.json?pickingListId=" + id);
		var isCod = false;
		$j("#selLpcode option").remove();
		for(var idx in transRs){
			$j("<option value='" + transRs[idx].code + "'>"+ transRs[idx].name +"</option>").appendTo($j("#selLpcode"));
			if(transRs[idx].isSupportCod == true){
				isCod = true;
			}
		}
		if(!isCod){
			$j("#selLpcode").val("EMS");
		}
		$j("#errTransBtnlist").removeClass("hidden");
		$j("#btnlist").addClass("hidden");
		$j("#errBtnlist").addClass("hidden");
	}else if(data["intStatus"] == '20'){
		$j("#btnlist").addClass("hidden");
		$j("#errTransBtnlist").addClass("hidden");
		$j("#errBtnlist").removeClass("hidden");
	}else{
		$j("#errBtnlist").addClass("hidden");
		$j("#errTransBtnlist").addClass("hidden");
		$j("#btnlist").removeClass("hidden");
	}
	
	if (data["intStatus"] == '19' || data["intStatus"] == '20'){
		$j("#metrobtn").addClass("hidden");
	}else{
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/findstacountformetroshopbyplid.json",{"pickingListId":id});
		if(rs){
			var count = rs.stacount;
			if (count >0){
				$j("#metrobtn").removeClass("hidden");
			}else{
				$j("#metrobtn").addClass("hidden");
			}
		}
	}
}

// 占用库存
function occupiedInv(url,postData){
	var rs = loxia.syncXhrPost(url,postData);
	if(rs&&rs.result == 'error'){
		jumbo.showMsg(rs.msg);
		return false;
	}
	return true;
}

function getStatusName(status,intStatus){
	for(var x in status["value"].split(";")){
		var str = status["value"].split(";")[x];
		var currStr = str.split(":");
		if(currStr[0] == intStatus){
			return currStr[1];
		}
	}
}

var isEmsOlOrder = false;
$j(document).ready(function (){
	var plStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"pickingListStatus"});
	var whinfo = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getWarehouseInfo.json");
	if(whinfo.isEmsOlOrder){
		isEmsOlOrder = true;
	}
	
	$j("#tbl-newDistribution").jqGrid({
		url:$j("body").attr("contextpath") + "/salesdispatchlist.json",
		datatype: "json",
		// colNames: ["ID","配货批次号","创建时间","计划配货单据数","计划配货商品件数","状态"],
		colNames: ["ID", i18("BATCHNO"),i18("CREATETIME"),i18("PLAN_BILL_COUNT"),i18("PLAN_SKU_QTY"),i18("INTSTATUS")],
		colModel: [{name: "id", index: "id", hidden: true},
		           
		           {name: "code", index: "code", width:120,resizable:true},         
		           {name: "createTime", index: "createTime", width: 150, resizable: true},
		           {name: "planBillCount", index: "planBillCount", width: 150, resizable: true},
		           {name: "planSkuQty", index: "planSkuQty", width: 150, resizable: true},
	               {name:"intStatus",index:"status",width:150,resizable:true,formatter:'select',editoptions:plStatus}],
		caption: i18("NEW_LIST"),// 新建配货清单列表
		rowNum:-1,
	   	// rowList:[5,10,15,20,25,30],
	   	sortname: 'createTime',
	    multiselect: true,
	    height:"auto",
		sortorder: "desc",
		postData:{"status":"0"},
		jsonReader: { repeatitems : false, id: "0" }
	});

		$j("#tbl-waittingList").jqGrid({
			url:$j("body").attr("contextpath") + "/operatorPlList.json",
			datatype: "json",
			// colNames:
			// ["ID","creator","opeator","planBillCount","checkedBillCount","planSkuQty","checkedSkuQty","checkedTime","pickingTime","配货批次号","创建时间","计划配货单据数","计划配货商品件数","状态"],
			colNames: ["ID","outputCount","creator","opeator","planBillCount","checkedBillCount","planSkuQty","checkedSkuQty","checkedTime","pickingTime",i18("BATCHNO"),i18("CREATETIME"),i18("PLANBILLCOUNT"),i18("PLAYSKUQTY"),i18("INTSTATUS"),"物流"],
			colModel: [{name: "id", index: "id", hidden: true},
			           {name: "outputCount", index: "outputCount", hidden: true},
			           {name: "creator.userName", index: "creator",hidden: true},
			           {name: "operator.userName", index: "creator",hidden: true},
			           {name: "planBillCount", index: "planBillCount",hidden: true},
			           {name: "checkedBillCount", index: "checkedBillCount",hidden: true},
			           {name: "checkedSkuQty", index: "checkedSkuQty",hidden: true},
			           {name: "checkedTime", index: "checkedTime",hidden: true},
			           {name: "pickingTime", index: "pickingTime",hidden: true},
			           {name: "planSkuQty", index: "planSkuQty",hidden: true},
			           {name: "code", index: "code", formatter:"linkFmatter", formatoptions:{onclick:"showDetail"}, width:120,resizable:true},         
			           {name: "createTime", index: "createTime", width: 150, resizable: true},
			           {name: "planBillCount", index: "planBillCount", width: 150, resizable: true},
			           {name: "planSkuQty", index: "planSkuQty", width: 150, resizable: true},
		               {name:"intStatus",index:"status",width:150,resizable:true,formatter:'select',editoptions:plStatus},
			           {name:"lpcode", index: "lpcode",width: 80, resizable: true}
			           ],
			caption: i18("WAITING_LIST"),// 待配货清单列表
			postData:{"columns":"id,outputCount, creator.userName,operator.userName,planBillCount,checkedBillCount,checkedSkuQty,"+
			"checkedTime,pickingTime,planSkuQty,code,createTime,planBillCount,planSkuQty,intStatus,lpcode","status":"1"},
			rowNum:-1,
	   	// rowList:[5,10,15,20,25,30],
	   	sortname: 'createTime',
	   	height:"auto",
	    multiselect: false,
//	    postData:{"status":"1"},
		sortorder: "desc",
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
		
		// add by liubin 将打印的清单设置背景色 
		loadComplete: function(){
			var ids = $j("#tbl-waittingList").jqGrid('getDataIDs');
			for(var i=0;i<ids.length;i++){
				var rowData = $j("#tbl-waittingList").getRowData(ids[i]);
				var outputCount = rowData['outputCount'];
				if (outputCount > 0) {
					$j("#" + ids[i]).addClass("printRow");
				}
			}
		}
		});

		$j("#back,#back2,#back3").click(function(){
			$j("#div2").addClass("hidden");
			$j("#div1").removeClass("hidden");
			$j("#btnlist").addClass("hidden");
			$j("#errBtnlist").addClass("hidden");
			$j("#divTbDetial").addClass("hidden");
			$j("#metrobtn").addClass("hidden");
		});

		$j("#addDistribution").click(function(){
			var plIds = $j("#tbl-newDistribution").jqGrid('getGridParam','selarrrow');
			if(plIds.length>0){
				var postData = {};
				for(var i in plIds){
					postData['plList[' + i + ']']=plIds[i];
				}
				var baseUrl = $j("body").attr("contextpath");
				occupiedInv(baseUrl + "/confirmPickingList.json",postData);
				
				$j("#tbl-newDistribution").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/salesdispatchlist.json")}).trigger("reloadGrid");
				$j("#tbl-waittingList").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/operatorPlList.json")}).trigger("reloadGrid");
				
			}else{
				jumbo.showMsg(loxia.getLocaleMsg("DISTRIBUTION_LIST_SELECT"));// 请选择配货清单
			}
		});

		$j("#changeCode").click(function(){
			$j("#gbox_tbl-waittingList").appendTo($j("#hidden-waittingList"));
			var attr = $j("#hidden-waittingList").attr("class");
			if(attr == "hidden"){
				$j("#hidden-waittingList").removeClass("hidden");
			}else{
				$j("#hidden-waittingList").addClass("hidden");
			}
			
		});
		
		$j("#cancel").click(function(){
			var plIds = $j("#tbl-newDistribution").jqGrid('getGridParam','selarrrow');
			if(plIds.length>0){
				var postData = {};
				for(var i in plIds){
					postData['plList[' + i + ']']=plIds[i];
				}
				loxia.syncXhrPost($j("body").attr("contextpath") + "/cancelPickingList.json",postData);
				var jsonurl = $j("body").attr("contextpath") + "/salesdispatchlist.json";
				$j("#tbl-newDistribution").jqGrid('setGridParam',{url:loxia.getTimeUrl(jsonurl)}).trigger("reloadGrid");
			}else{
				jumbo.showMsg(loxia.getLocaleMsg("DISTRIBUTION_LIST_SELECT"));// 请选择配货清单
			}
		});
		
		// 重新配货
		$j("#occupiedInv,#tryTrans").click(function(){
			var id = $j("#pickingListId").html();
			var postData = {};
			postData['plList[0]']=$j("#div2").attr("plId");
			var baseUrl = $j("body").attr("contextpath");
			var rs = occupiedInv(baseUrl + "/reOccupiedInv.json",postData);
			if(rs){
				$j("#errBtnlist").addClass("hidden");
				$j("#btnlist").removeClass("hidden");
				$j("#tbl-waittingList").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/operatorPlList.json"}).trigger("reloadGrid");
				$j("#dphStatus").text("配货中");
			}
			orderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+id);
		});		
		
		// 删除快递失败STA
		$j("#rmTransFailed").click(function(){
			var id = $j("#div2").attr("plId");
			var postData = {};
			postData['plList[0]']= id;
			postData['pickingListId']= id;
			var baseUrl = $j("body").attr("contextpath");
			var rs = loxia.syncXhrPost(baseUrl + "/removeTransFailedSta.json",postData);
			if(rs && rs.result == "success"){
				//成功
				$j("#errBtnlist").addClass("hidden");
				$j("#btnlist").removeClass("hidden");
				$j("#tbl-waittingList").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/operatorPlList.json"}).trigger("reloadGrid");
				$j("#dphStatus").text("配货中");
				var id = $j("#pickingListId").html();
				orderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+id);
				
				$j("#errBtnlist").addClass("hidden");
				$j("#errTransBtnlist").addClass("hidden");
				$j("#btnlist").removeClass("hidden");
			}else if(rs && rs.result == "remove"){
				//被删除
				$j("#div2").addClass("hidden");
				$j("#div1").removeClass("hidden");
				$j("#tbl-waittingList").find("tr[id='" + id + "']").remove();
				jumbo.showMsg(i18("DISPATCH_FAILED"));// 由于您的配货单中所有作业单都失败，所以您的配货单被取消
				
			}else if(rs&&rs.result == 'error'){
				//配货失败
				jumbo.showMsg(rs.msg);
				$j("#btnlist").addClass("hidden");
				$j("#errTransBtnlist").addClass("hidden");
				$j("#errBtnlist").removeClass("hidden");
			}
			orderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+id);
		});	
		
		// 转物流并删除
		$j("#btnChgTrans").click(function(){
			var id = $j("#div2").attr("plId");
			var postData = {};
			postData['plList[0]']= id;
			postData['pickingListId']= id;
			postData['lpcode']= $j("#selLpcode").val();
			var baseUrl = $j("body").attr("contextpath");
			var rs = loxia.syncXhrPost(baseUrl + "/chgLpcodeRemoveFailedSta.json",postData);
			if(rs && rs.result == "success"){
				//成功
				$j("#errBtnlist").addClass("hidden");
				$j("#btnlist").removeClass("hidden");
				$j("#tbl-waittingList").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/operatorPlList.json"}).trigger("reloadGrid");
				$j("#dphStatus").text("配货中");
				var id = $j("#pickingListId").html();
				orderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+id);
				
				$j("#errBtnlist").addClass("hidden");
				$j("#errTransBtnlist").addClass("hidden");
				$j("#btnlist").removeClass("hidden");
			}else if(rs && rs.result == "remove"){
				//被删除
				$j("#div2").addClass("hidden");
				$j("#div1").removeClass("hidden");
				$j("#tbl-waittingList").find("tr[id='" + id + "']").remove();
				jumbo.showMsg(i18("DISPATCH_FAILED"));// 由于您的配货单中所有作业单都失败，所以您的配货单被取消
				
			}else if(rs&&rs.result == 'error'){
				//配货失败
				jumbo.showMsg(rs.msg);
				$j("#btnlist").addClass("hidden");
				$j("#errTransBtnlist").addClass("hidden");
				$j("#errBtnlist").removeClass("hidden");
			}
			orderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+id);
		});	
		
		// 删除所有失败
		$j("#remFailed").click(function(){
			var id = $j("#div2").attr("plId");
			var baseUrl = $j("body").attr("contextpath");
			var rs = loxia.syncXhrPost(baseUrl + "/removeFailedSta.json",{"pickingListId":id});
			if(rs && rs["pickingList"]){
				$j("#div2").attr("plId",rs["pickingList"].id);
				$j("#dphCode").html(rs["pickingList"].code);
				$j("#planBillCount").html(rs["pickingList"].planBillCount);
				$j("#checkedBillCount").html(rs["pickingList"].checkedBillCount);
				$j("#planSkuQty").html(rs["pickingList"].planSkuQty);
				$j("#pickingTime").html(rs["pickingList"].pickingTime);
				$j("#checkedTime").html(rs["pickingList"].checkedTime);
				$j("#dphStatus").html(getStatusName(plStatus,rs["pickingList"].intStatus));
				var tr = $j("#tbl-waittingList").find("tr[id='"+id+"']");
				tr.find("td[aria-describedby='tbl-waittingList_intStatus']").html(getStatusName(plStatus,rs["pickingList"].intStatus));
				tr.find("td[aria-describedby='tbl-waittingList_planBillCount']").html(rs["pickingList"].planBillCount);
				tr.find("td[aria-describedby='tbl-waittingList_planSkuQty']").html(rs["pickingList"].planSkuQty);
				orderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+rs["pickingList"].id);
				// 2配货成功
				if(rs["pickingList"].intStatus == '2' ){
					$j("#errBtnlist").addClass("hidden");
					$j("#btnlist").removeClass("hidden");
				}
			}else{
				$j("#div2").addClass("hidden");
				$j("#div1").removeClass("hidden");
				$j("#tbl-waittingList").find("tr[id='" + id + "']").remove();
				jumbo.showMsg(i18("DISPATCH_FAILED"));// 由于您的配货单中所有作业单都失败，所以您的配货单被取消
			}
		});
		
		// 取消
		$j("#plCancel").click(function(){
			var id = $j("#div2").attr("plId");
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/deletePickingList.do",{"pickingListId":id});
			if(rs && rs["result"] == 'success'){
				$j("#tbl-waittingList").find("tr[id='" + id + "']").remove();
				$j("#div2").addClass("hidden");
				$j("#div1").removeClass("hidden");
				jumbo.showMsg(i18("DIAPATCH_CANCEL"));// 配货清单已取消
			}else if(rs && rs["result"] == 'error'){
				jumbo.showMsg(rs["message"]);
			}
		});
		
		// 打印配货出库单：
		$j("#excelPickingList").click(function(){
			if(beforePrintValidate()){
				var plid = $j("#div2").attr("plId");
				var plcode = $j("#dphCode").html();
				var plcount = $j("#planBillCount").html();
				var plskuqty = $j("#planSkuQty").html();
				var ploper = $j("#operator").html();		
				var url = $j("body").attr("contextpath") + "/warehouse/pickingListMode1Export.do?plCmd.id="+ plid;  
				url += "&plCmd.code=" + plcode + "&plCmd.planBillCount=" + plcount;
				url += "&plCmd.planSkuQty=" + plskuqty + "&plCmd.operator.userName=" + ploper;
				$j("#exportFrame").attr("src",url);
			}
		});
		
		
		// 打印配货出库单：
		$j("#printPickingList").click(function(){
			loxia.lockPage();
			if(beforePrintValidate()){
				var plid = $j("#div2").attr("plId");
				var plcode = $j("#dphCode").html();
				var plcount = $j("#planBillCount").html();
				var plskuqty = $j("#planSkuQty").html();
				var ploper = $j("#operator").html();			
				jumbo.showMsg(i18("OPERATING"));// 配货清单打印中，请等待...
				var url = $j("body").attr("contextpath") + "/printpickinglistmode1.json?plCmd.id="+ plid;  
				url += "&plCmd.code=" + plcode + "&plCmd.planBillCount=" + plcount;
				url += "&plCmd.planSkuQty=" + plskuqty + "&plCmd.operator.userName=" + ploper;
				printBZ(loxia.encodeUrl(url),true);
			}
			loxia.unlockPage();
		});
		
		$j("#printPickingListNew").click(function(){
			loxia.lockPage();
			if(beforePrintValidate()){
				var plid = $j("#div2").attr("plId");
				jumbo.showMsg(i18("OPERATING"));// 配货清单打印中，请等待...
				var url = $j("body").attr("contextpath") + "/printpickinglistnewmode1.json?pickingListId="+ plid;  
				printBZ(loxia.encodeUrl(url),true);
			}
			loxia.unlockPage();
		});
		
		// 打印装箱清单：
		$j("#printTrunkPackingList").click(function(){
			loxia.lockPage();
			if(beforePrintValidate()){
				var plid = $j("#div2").attr("plId");
				var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/queryDispatchListOutputCount.json",{"plCmd.id":plid});
				if(rs && rs["msg"] == 'success' && parseInt(rs["count"]) == 0){
					var plcode = $j("#dphCode").html();
					jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
					var url = $j("body").attr("contextpath") + "/printtrunkpackinglistmode1.json?plCmd.id=" + plid + "&plCmd.code=" + plcode;
					//var rs = loxia.syncXhrPost(url);
					printBZ(loxia.encodeUrl(url),true);
					
					// add by liubin 将打印的清单设置背景色 
					$j("#" + plid).addClass("printRow");
					
				} else if(rs && rs["msg"] == 'success'){
					jumbo.showMsg("装箱清单已打印，不能重复打印！");
				} else {
					jumbo.showMsg("获取打印次数失败！");
				}
			}
			loxia.unlockPage();
		});
		// 物流面单导出
		$j("#exportDeliveryInfo").click(function(){
			if(beforePrintValidate()){
				var id = $j("#div2").attr("plId");
				$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/exportdeliveryinfo.do?pickingListId=" + id);	
			}
		});
		
		// 物流面单导出
		$j("#exportDeliveryInfoZip").click(function(){
			if(beforePrintValidate()){
				var id = $j("#div2").attr("plId");
				$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/exportdeliveryinfozip.do?pickingListId=" + id);	
			}
		});
		
		// 物流面单打印
		$j("#printDeliveryInfo").click(function(){
			var ems = $j("#tdIdLpcode").html();
			if(isEmsOlOrder && (ems == 'EMS' || ems == 'EMSCOD')){
				$j("#emsPrintAll").trigger("click");
			}else{
				loxia.lockPage();
				if(beforePrintValidate()){
					var plid = $j("#div2").attr("plId");
					jumbo.showMsg(i18("DELIVERYINFO"));
					var url = $j("body").attr("contextpath") + "/printdeliveryinfomode1.json?plCmd.id=" + plid;
					printBZ(loxia.encodeUrl(url),true);					
				}
				loxia.unlockPage();
			}
		});

		// 发票
		$j("#btnSoInvoice").click(function(){
			var id = $j("#div2").attr("plId");
			$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/exportSoInvoice.do?pickingListId=" + id);
		});
		
		// 发票ZIP
		$j("#btnSoInvoiceZip").click(function(){
			var id = $j("#div2").attr("plId");
			$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/exportSoInvoiceZip.do?pickingListId=" + id);
		});
		
		$j("#emsPrintAll").click(function(){
			var staCodes = []; 
			var ids = $j("#tbl-orderDetail").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var data=$j("#tbl-orderDetail").jqGrid("getRowData",ids[i]);
				staCodes[i] = data['code'];
			}
			var plid = $j("#pickingListId").html();
			jumbo.emsprint(staCodes,plid);
		});
});

function beforePrintValidate(){
	var ids = $j("#tbl-orderDetail").jqGrid('getDataIDs');
	var num=0,size=0;
	var datas;
	if(ids.length==0) {jumbo.showMsg("数据为空，数据出错..."); return false;}
	for(var i=0;i < ids.length;i++){
		size++;
		datas = $j("#tbl-orderDetail").jqGrid('getRowData',ids[i]);
		if (datas['intStatus']==17){
			num++;
		}
	}
	if (size==num){
		jumbo.showMsg("当前订单已'取消已处理'，不能打印或导出..."); 
		return false;
	}
	return true;
}
