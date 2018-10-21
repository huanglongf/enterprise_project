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
	WH_NAME : "仓库",
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
		// ["ID","作业单号","状态","相关单据号","作业类型名称","淘宝店铺ID","物流服务商简称","创建时间","计划执行总件数"],
		colNames: ["ID","序列","status",i18("CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("SHOPID"),"物流服务",i18("CREATETIME"),"计划总件数",i18("WH_NAME"),"仓库id",i18("OPETION")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "index",index:"index",sortable: false,width:50},
		           {name: "status",hidden: true},
		           {name: "code", index: "code", width: 100, sortable: false,resizable: true},
		           {name: "intStatus", index: "intStatus", width: 40, sortable: false,resizable: true,formatter:'select',editoptions:intStatus},
		           {name: "refSlipCode", index: "refSlipCode",width: 100, sortable: false,resizable: true},
		           {name: "intType", index: "intType", width: 90, sortable: false,resizable: true,formatter:'select',editoptions:staType},
		           {name: "shopId", index: "shopId", width: 120, sortable: false,resizable: true},
		           {name: "lpcode", index: "lpcode", width: 70,sortable: false, resizable: true,formatter:'select',editoptions:trasportName},
	               {name:"createTime",index:"createTime",width:120,sortable: false,resizable:true},
	               {name: "stvTotal", index: "stvTotal", width: 60,sortable: false, resizable: true},
	               {name: "mainName", index: "mainName", width: 120, sortable: false,resizable: true},
	               {name: "wcenid", index: "wcenid", width: 120,sortable: false,hidden:true},
	               {name: "idForBtn", width: 80,resizable:true, sortable: false,formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("PRINGT"), onclick:"printSingleDelivery(this,event);"}}}],
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
	var whid = $j(tag).parents("tr").children("td:eq(11)").attr("title");
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
	var isEmsOlOrder = $j("#div2").attr("ems");
	if(isEmsOlOrder == "true" && ('EMS' == data['lpcode'] || 'EMSCOD' == data['lpcode'])){
		var staCodes = [];
		staCodes[0] = staCode;
		var plid = $j("#pickingListId").html();
		jumbo.emsprint(staCodes,plid);
	}else{
		var url = $j("body").attr("contextpath") + "/printsingledeliverymode1Outopc.json?sta.id=" + id+"&ou.id="+whid;
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
	var code = $j(tag).text();
	var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getpickinglistbycode.json",{"pickingList.code":code});
	$j("#whid").val(rs["pickingList"]["wid"]);
	var isEmsOlOrder = rs["pickingList"]["isEmsOlOrder"];
	$j("#div2").attr("ems",isEmsOlOrder);
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
	var sortingMode = 0;
	if(rs && rs["pickingList"]){
		$j("#dphCode").html( rs["pickingList"]["code"]);
		$j("#planBillCount").html( rs["pickingList"]["planBillCount"]);
		$j("#planSkuQty").html( rs["pickingList"]["planSkuQty"]);
		$j("#pickingTime").html( rs["pickingList"]["pickingTime"]);
		$j("#isSn").html(rs["pickingList"]["isSn"]==true?"是":"否")
		$j("#checkedBillCount").html( rs["pickingList"]["checkedBillCount"]);
		$j("#checkedTime").html( rs["pickingList"]["checkedTime"]);
		$j("#checkedSkuQty").html( rs["pickingList"]["checkedSkuQty"]);
		$j("#creator").html(rs["pickingList"]["crtUserName"]);
		$j("#operator").html(rs["pickingList"]["operUserName"]);
		$j("#sendSkuQty").html( rs["pickingList"]["sendSkuQty"]);
		$j("#sendStaQty").html( rs["pickingList"]["sendStaQty"]);
		$j("#lastSendTime").html( rs["pickingList"]["lastSendTime"]);
		sortingMode = rs["pickingList"]["sortingModeInt"];
	}
	$j("#dphStatus").html(tr.find("td[aria-describedby='tbl-waittingList_intStatus']").html());
	$j("#tdIdLpcode").html( data["lpcode"]);
	orderDetail($j("body").attr("contextpath") + "/detialListopc.json?pickingListId="+id);
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
		$j("#errTransBtnlist").removeClass("hidden");//快递无法送达
		$j("#btnlist").addClass("hidden");//配货中，或者部分完成
		$j("#errBtnlist").addClass("hidden");//配货失败，待配货
	}else if(data["intStatus"] == '20'||data["intStatus"] == '1'){
		$j("#btnlist").addClass("hidden");
		$j("#errTransBtnlist").addClass("hidden");
		$j("#errBtnlist").removeClass("hidden");
	}else{
		if(sortingMode==1){
			$j("#printPickingList").addClass("confirm");
			$j("#printPickingListNew").removeClass("confirm");
		}else{
			$j("#printPickingListNew").addClass("confirm");
			$j("#printPickingList").removeClass("confirm");
		}
		$j("#errBtnlist").addClass("hidden");
		$j("#errTransBtnlist").addClass("hidden");
		$j("#btnlist").removeClass("hidden");
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
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	//根据运营中心仓库查询
	var result = loxia.syncXhrPost($j("body").attr("contextpath")+"/selectbyopc.json");
	for(var idx in result.warelist){
		$j("<option value='" + result.warelist[idx].id + "'>"+ result.warelist[idx].name +"</option>").appendTo($j("#selTrans"));
	}
	$j("#pCode").focus();
	var plStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"pickingListStatus"});
	$j("#tbl-waittingList").jqGrid({
		url:$j("body").attr("contextpath") + "/getallpickinglistbywid.json",
		datatype: "json",
		colNames: ["ID","outputCount",i18("BATCHNO"),i18("CREATETIME"),i18("PLANBILLCOUNT"),i18("PLAYSKUQTY"),i18("INTSTATUS"),"物流","含SN订单","所属仓库","仓库id"],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "outputCount", index: "outputCount", hidden: true},
		           {name: "code", index: "code", formatter:"linkFmatter", formatoptions:{onclick:"showDetail"}, width:120,resizable:true},         
		           {name: "createTime", index: "createTime", width: 150, resizable: true},
		           {name: "planBillCount", index: "planBillCount", width: 120, resizable: true},
		           {name: "planSkuQty", index: "planSkuQty", width: 120, resizable: true},
	               {name:"intStatus",index:"status",width:50,resizable:true,formatter:'select',editoptions:plStatus},
		           {name:"lpcode", index: "lpcode",width: 150, resizable: true,formatter:'select',editoptions:trasportName},
		           {name:"isSn",index:"isSn",width:80,resizable:true,formatter:'select',editoptions:trueOrFalse},
		           {name:"wname", index: "wname",width: 120, resizable: true},
		           {name:"whid", index: "whid",width: 120, resizable: true,hidden:true}
		           ],
		caption: i18("WAITING_LIST"),// 待配货清单列表
		pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:true,
	    viewrecords: true,
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
		$j("#div2").attr("ems",false);
		$j("#div2").attr("plid","");
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
			
	// 重新配货
	$j("#occupiedInv,#tryTrans").click(function(){
		var id = $j("#pickingListId").html();
		var postData = {};
		postData['plList[0]']=$j("#div2").attr("plId");
		var baseUrl = $j("body").attr("contextpath");
		//var rs = occupiedInv(baseUrl + "/reOccupiedInv.json",postData);
		var rs = occupiedInv(baseUrl+"/reOccupiedInvOut.json",postData);
		if(rs){
			$j("#errBtnlist").addClass("hidden");
			$j("#btnlist").removeClass("hidden");
			$j("#tbl-waittingList").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/getallpickinglistbywid.json"}).trigger("reloadGrid");
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
		var rs = loxia.syncXhrPost(baseUrl + "/removeTransFailedStaOut.json",postData);
		if(rs && rs.result == "success"){
			//成功
			$j("#errBtnlist").addClass("hidden");
			$j("#btnlist").removeClass("hidden");
			$j("#tbl-waittingList").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/getallpickinglistbywid.json"}).trigger("reloadGrid");
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
		orderDetail($j("body").attr("contextpath") + "/detialListopc.json?pickingListId="+id);
	});	
	
	// 转物流并删除
	$j("#btnChgTrans").click(function(){
		var id = $j("#div2").attr("plId");
		var postData = {};
		postData['plList[0]']= id;
		postData['pickingListId']= id;
		postData['lpcode']= $j("#selLpcode").val();
		var baseUrl = $j("body").attr("contextpath");
		//var rs = loxia.syncXhrPost(baseUrl + "/chgLpcodeRemoveFailedSta.json",postData);
		var rs = loxia.syncXhrPost(baseUrl + "/chgLpcodeRemoveFailedStaOut.json",postData);
		if(rs && rs.result == "success"){
			//成功
			$j("#errBtnlist").addClass("hidden");
			$j("#btnlist").removeClass("hidden");
			$j("#tbl-waittingList").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/getallpickinglistbywid.json"}).trigger("reloadGrid");
			$j("#dphStatus").text("配货中");
			var id = $j("#pickingListId").html();
			orderDetail($j("body").attr("contextpath") + "/detialListopc.json?pickingListId="+id);
			
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
		orderDetail($j("body").attr("contextpath") + "/detialListopc.json?pickingListId="+id);
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
			orderDetail($j("body").attr("contextpath") + "/detialListopc.json?pickingListId="+rs["pickingList"].id);
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

	// 打印配货出库单：
	$j("#excelPickingList").click(function(){
		if(beforePrintValidate()){
			var plid = $j("#div2").attr("plId");
			var plcode = $j("#dphCode").html();
			var plcount = $j("#planBillCount").html();
			var plskuqty = $j("#planSkuQty").html();
			var ploper = $j("#operator").html();
			var cenid=$j("#whid").val();
			var url = $j("body").attr("contextpath") + "/warehouse/pickingListMode1Exportopc.do?plCmd.id="+ plid+"&ou.id="+cenid;  
			url += "&plCmd.code=" + plcode + "&plCmd.planBillCount=" + plcount;
			url += "&plCmd.planSkuQty=" + plskuqty + "&plCmd.operator.userName=" + ploper;
			$j("#exportFrame").attr("src",url);
		}
	});
	
	
	// 打印拣货单模式一      按钮功能
	$j("#printPickingList").click(function(){
		loxia.lockPage();
		if(beforePrintValidate()){
			var plid = $j("#div2").attr("plId");
			var plcode = $j("#dphCode").html();
			var plcount = $j("#planBillCount").html();
			var plskuqty = $j("#planSkuQty").html();
			var ploper = $j("#operator").html();	
			var cenid=$j("#whid").val();
			jumbo.showMsg(i18("OPERATING"));// 配货清单打印中，请等待...
			var url = $j("body").attr("contextpath") + "/printpickinglistmode1opc.json?cmd.id="+ plid+"&ou.id="+cenid;  
			url += "&cmd.code=" + plcode + "&cmd.planBillCount=" + plcount;
			url += "&cmd.planSkuQty=" + plskuqty + "&cmd.operator.userName=" + ploper;
	   	printBZ(loxia.encodeUrl(url),true);
		}
		loxia.unlockPage();
	});
	//打印拣货单模式二 按钮功能
	$j("#printPickingListNew").click(function(){
		loxia.lockPage();
		if(beforePrintValidate()){
			var plid = $j("#div2").attr("plId");
			var cenid=$j("#whid").val();
			jumbo.showMsg(i18("OPERATING"));// 配货清单打印中，请等待...
			var url = $j("body").attr("contextpath") + "/printpickinglistnewmode1opc.json?pickingListId="+ plid+"&ou.id="+cenid;
			printBZ(loxia.encodeUrl(url),true);
		}
		loxia.unlockPage();
	});
	
	// 打印装箱清单按钮功能
	$j("#printTrunkPackingList").click(function(){
		loxia.lockPage();
		if(beforePrintValidate()){
			var plid = $j("#div2").attr("plId");
			var cenid=$j("#whid").val();
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/queryDispatchListOutputCount.json",{"plCmd.id":plid});
			if(rs && rs["msg"] == 'success' && parseInt(rs["count"]) == 0){
				var plcode = $j("#dphCode").html();
				jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
				var url = $j("body").attr("contextpath") + "/printtrunkpackinglistmode1Outopc.json?cmd.id=" + plid + "&cmd.code=" + plcode+"&ou.id="+cenid;
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
	// 导出物流面单按钮功能
	$j("#exportDeliveryInfo").click(function(){
		if(beforePrintValidate()){
			var id = $j("#div2").attr("plId");
			var cenid=$j("#whid").val();
			$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/exportdeliveryinfoopc.do?pickingListId=" + id+"&ou.id="+cenid);
		}
	});
	
	// 物流面单导出
	$j("#exportDeliveryInfoZip").click(function(){
		if(beforePrintValidate()){
			var id = $j("#div2").attr("plId");
			var cenid=$j("#whid").val();
			$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/exportdeliveryinfozipopc.do?pickingListId=" + id+"&ou.id="+cenid);
		}
	});
	
	// 打印物流面单
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
				var lpCode = $j("#tdIdLpcode").text();
				if(lpCode==""){
					printBZ(loxia.encodeUrl(url),false)
				}else{
					printBZ(loxia.encodeUrl(url),true)
				}			
			}
			loxia.unlockPage();
		}
	});
	
	// 税控发票导出
	$j("#btnSoInvoice").click(function(){
		var id = $j("#div2").attr("plId");
		$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/exportSoInvoice.do?pickingListId=" + id);
	});
	
	// 税控发票导出压缩文件
	$j("#btnSoInvoiceZip").click(function(){
		var id = $j("#div2").attr("plId");
		$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/exportSoInvoiceZip.do?pickingListId=" + id);
	});
	//查询按钮功能 根据作业单号和状态查询
	$j("#btn-query").click(function(){
		var postData = loxia._ajaxFormToObj("query-form");
		var cenid=$j("#selTrans").val();
//		if($j("#picklistcode").val()==""){
//			$j("#tbl-waittingList").jqGrid('setGridParam',{
//				url:window.$j("body").attr("contextpath")+"/json/getallpickinglistopc.do",
//				postData:postData,
//				page:1
//			}).trigger("reloadGrid");
//		}else if(cenid==""){
//			$j("#tbl-waittingList").jqGrid('setGridParam',{
//				url:window.$j("body").attr("contextpath")+"/json/pickingListForPickOutbycode.do",
//				postData:postData,
//				page:1
//			}).trigger("reloadGrid");
//		}else{
		postData["ou.id"]=cenid;
		$j("#tbl-waittingList").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/json/getallpickinglistbywid.do",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
//		}
	});
	//初始化时进入光标定位文本框，按enter执行相应的工作
	$j("#pCode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			showDetail1($j("#pCode").val());
		}
	});
});
function showDetail1(pCode){
	if(pCode.length==0){
		loxia.tip($j("#pCode"),"请输入配货批次号！");
	}else{
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getpickinglistbycode.json",{"pickingList.code":pCode});
		if(rs&&rs["pickingList"]){
			$j("#div2").attr("ems",rs["pickingList"]["isEmsOlOrder"]);
			$j("#divTbDetial").removeClass("hidden");
			$j("#div1").addClass("hidden");
			$j("#div2").removeClass("hidden");
			$j("#div2").attr("plId",rs["pickingList"]["id"]);
			$j("#pickingListId").html(rs["pickingList"]["id"]);
				$j("#dphCode").html( rs["pickingList"]["code"]);
				$j("#planBillCount").html( rs["pickingList"]["planBillCount"]);
				$j("#planSkuQty").html( rs["pickingList"]["planSkuQty"]);
				$j("#pickingTime").html( rs["pickingList"]["pickingTime"]);
				$j("#isSn").html(rs["pickingList"]["isSn"]==true?"是":"否")
				$j("#checkedBillCount").html( rs["pickingList"]["checkedBillCount"]);
				$j("#checkedTime").html( rs["pickingList"]["checkedTime"]);
				$j("#checkedSkuQty").html( rs["pickingList"]["checkedSkuQty"]);
				
				$j("#sendSkuQty").html( rs["pickingList"]["sendSkuQty"]);
				$j("#sendStaQty").html( rs["pickingList"]["sendStaQty"]);
				$j("#lastSendTime").html( rs["pickingList"]["lastSendTime"]);
			//}
			//$j("#dphStatus").html(tr.find("td[aria-describedby='tbl-waittingList_intStatus']").html());
			$j("#creator").html(rs["pickingList"]["crtUserName"]);
			$j("#operator").html(rs["pickingList"]["operUserName"]);
			$j("#tdIdLpcode").html( rs["pickingList"]["lpcode"]);
			orderDetail($j("body").attr("contextpath") + "/detialListopc.json?pickingListId="+rs["pickingList"]["id"]);
			$j("#gbox_tbl-waittingList").appendTo($j("#div-waittingList"));
			$j("#hidden-waittingList").addClass("hidden");
			
			if(rs["pickingList"]["statusInt"] == '19'){
				$j("#dphStatus").html("快递无法送达");
				var transRs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getTransByPlId.json?pickingListId=" + rs["pickingList"]["id"]);
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
				$j("#errTransBtnlist").removeClass("hidden");//快递无法送达
				$j("#btnlist").addClass("hidden");//配货中，或者部分完成
				$j("#errBtnlist").addClass("hidden");//配货失败，待配货
			}else if(rs["pickingList"]["statusInt"] == '20'||rs["pickingList"]["statusInt"] == '1'){
				if(rs["pickingList"]["statusInt"]=='20'){
					$j("#dphStatus").html("配货失败");
				}else{
					$j("#dphStatus").html("等待配货");
				}
				$j("#btnlist").addClass("hidden");
				$j("#errTransBtnlist").addClass("hidden");
				$j("#errBtnlist").removeClass("hidden");
			}else{
				if(rs["pickingList"]["statusInt"]=='2'){
					$j("#dphStatus").html("配货中");
				}else{
					$j("#dphStatus").html("部分完成");
				}
				if(rs["pickingList"]["sortingModeInt"]==0){
					$j("#printPickingList").addClass("confirm");
					$j("#printPickingListNew").removeClass("confirm");
				}else{
					$j("#printPickingListNew").addClass("confirm");
					$j("#printPickingList").removeClass("confirm");
				}
				$j("#errBtnlist").addClass("hidden");
				$j("#errTransBtnlist").addClass("hidden");
				$j("#btnlist").removeClass("hidden");
			}
		}else{
			loxia.tip($j("#pCode"),"你输入的批次号不在范围内，首先确保单据号存在，状态在选择范围内");
		}
	}
}

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
