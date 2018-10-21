$j.extend(loxia.regional['zh-CN'],{
	DISTRIBUTION_LIST_SELECT : "请选择配货清单",
	CODE : "作业单号",
	INTSTATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	SHOPID : "淘宝店铺ID",
	LPCODE : "物流服务商简称",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	INT_LIST : "作业单列表",
	
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
	FAILED_DISABLE_PRINT : "当前订单状态为‘配货失败’不能打印",
	CORRECT_FILE_PLEASE:"请选择正确的Excel"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}
function showMsg(msg){
	jumbo.showMsg(msg);
}
function loadOrderDetail(url){
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var status=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	
	$j("#tbl-orderDetail").jqGrid({
		url:url,
		datatype: "json",
		colNames: ["ID","序列","status",i18("CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("SHOPID"),"物流服务",i18("CREATETIME"),"计划总件数",i18("OPETION")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name:"index",index:"index",sortable: false,width:50},
		           {name: "status",hidden: true},
		           {name: "code", index: "code", width: 100,sortable: false, resizable: true},
		           {name: "intStatus", index: "intStatus", width: 40,sortable: false, resizable: true,formatter:'select',editoptions:intStatus},
		           {name: "refSlipCode", index: "refSlipCode",width: 100, sortable: false,resizable: true},
		           {name: "intType", index: "intType", width: 120,sortable: false, resizable: true,formatter:'select',editoptions:staType},
		           {name: "shopId", index: "shopId", width: 120,sortable: false, resizable: true},
		           {name: "lpcode", index: "lpcode", width: 70,sortable: false, resizable: true,formatter:'select',editoptions:trasportName},
	               {name:"createTime",index:"createTime",width:120,sortable: false,resizable:true},
	               {name: "stvTotal", index: "stvTotal", width: 60,sortable: false, resizable: true},
	               {name: "idForBtn", width: 80,sortable: false, resizable:true, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("PRINGT"), onclick:"printSingleDelivery(this,event);"}}}],
		caption: i18("INT_LIST"),// 作业单列表
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: '"index"',
	   	height:"auto",
	    multiselect: false,
	    rownumbers:false,
	    viewrecords: true,
		sortorder: "asc",
		pager: "#pager",
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
//	if(isEmsOlOrder && ('EMS' == data['lpcode'] || 'EMSCOD' == data['lpcode'])){
//		var staCodes = [];
//		staCodes[0] = staCode;
//		var plid = $j("#pickingListId").html();
//		jumbo.emsprint(staCodes,plid);
//	}else{
		//var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?sta.id=" + id;
		var url = $j("body").attr("contextpath") + "/printsingledeliverymode1Out.json?sta.id=" + id;
		printBZ(loxia.encodeUrl(url),true);
//	}
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
	showDetail1(code);
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
var isEmsOlOrder= false;
$j(document).ready(function (){
	var arrStr2=new Array();
	$j("#priorityId").multipleSelect({
		 width: 200,
		 filter:true,
		 placeholder: "请选择",
		 onOpen: function() {
			 //$j("#priorityCity ul li:first").remove();
			 $j(".ms-select-all").remove();
       },
       onClick: function(view) {
          var res = view.label;
   		arrStr2.push(res);
   		var result1 = arrStr2.indexOf("所有优先发货省份");
   		var result2 = arrStr2.indexOf("非优先发货省份");
   		//alert(arrStr1);
   		if (result1 != -1 && result2 != -1) {
   			$j(".ms-choice span").text('');
   			$j("#priorityId").multipleSelect("uncheckAll");
   			arrStr2= [];
   			$j('#priorityId').multipleSelect('refresh');
   			jumbo.showMsg("不能同时选择'所有优先发货省份'和'非优先发货省份', 请重新选择!");
			} else if (result1 != -1 && arrStr2.length > 1) {
				$j(".ms-choice span").text('');
				$j("#priorityId").multipleSelect("uncheckAll");
   			$j('#priorityId').multipleSelect('refresh');
   			arrStr2= [];
				jumbo.showMsg("不能同时多选!");
			} else if (result2 != -1 && arrStr2.length > 1) {
				$j(".ms-choice span").text('');
				$j("#priorityId").multipleSelect("uncheckAll");
   			$j('#priorityId').multipleSelect('refresh');
   			arrStr2= [];
				jumbo.showMsg("不能同时多选!");
			}
       }
	});
	var whinfo = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getWarehouseInfo.json");
	if(whinfo.isEmsOlOrder){
		isEmsOlOrder = true;
	}
	$j("#tabs").tabs();//初始化tab面板
	$j("#pCode").focus();
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var plStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"pickingListStatus"});
	//初始化待配货清单列表数据
	$j("#tbl-waittingList").jqGrid({
		url:$j("body").attr("contextpath") + "/getallpickingliststatus.json",
		postData:{"pickingList.checkMode":"PICKING_SECKILL"},
		datatype: "json",
		colNames: ["ID","outputCount", i18("BATCHNO"),i18("CREATETIME"),i18("PLANBILLCOUNT"),i18("PLAYSKUQTY"),"是否是预售订单",i18("INTSTATUS"),"物流"],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "outputCount", index: "outputCount", hidden: true},
		           {name: "code", index: "code", formatter:"linkFmatter", formatoptions:{onclick:"showDetail"}, width:120,resizable:true},         
		           {name: "createTime", index: "createTime", width: 150, resizable: true},
		           {name: "planBillCount", index: "planBillCount", width: 150, resizable: true},
		           {name: "planSkuQty", index: "planSkuQty", width: 150, resizable: true},
		           {name: "isPreSale", index: "isPreSale",width:100,resizable:true},
	               {name:"intStatus",index:"status",width:50,resizable:true,formatter:'select',editoptions:plStatus},
		           {name:"lpcode", index: "lpcode",width: 150, resizable: true,formatter:'select',editoptions:trasportName}
		           
		           ],
		caption: i18("WAITING_LIST"),// 待配货清单列表
		pager:"#pager_query",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	   	height:"auto",
	    multiselect: true,
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
	//查询按钮功能 根据作业单号和状态查询
	$j("#btn-query").click(function(){
		var postData = loxia._ajaxFormToObj("query-form");
		postData["pickingList.checkMode"]="PICKING_SECKILL";
		var priority=postData["priority"];
		postData["priority"]=priority.join(",");
		$j("#tbl-waittingList").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/json/getallpickingliststatus.do",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	//初始化时进入光标定位文本框，按enter执行相应的工作
	$j("#pCode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			showDetail1($j("#pCode").val());
		}
	});
	//返回按钮功能
	$j("#back,#back2,#back3,#backto").click(function(){
		$j("#div2").addClass("hidden");
		$j("#div1").removeClass("hidden");
		$j("#div3").removeClass("hidden");
		$j("#btnlist").addClass("hidden");
		$j("#errBtnlist").addClass("hidden");
		$j("#divTbDetial").addClass("hidden");
		$j("#metrobtn").addClass("hidden");
	});
	
	$j("#exportAgv").click(function(){
		// var url=$j("body").attr("contextpath") + "/getExportAgv.json";
		 var areaCode =$j("#pickZoneCode").val();
		 var  plCode=$j("#dphCode").text();
		$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/getExportAgv.do?plCode=" + plCode+"&areaCode="+areaCode);
	});
			
	// 重新配货
	$j("#occupiedInv,#tryTrans").click(function(){
		var id = $j("#pickingListId").html();
		var postData = {};
		postData['plList[0]']=$j("#div2").attr("plId");
		var baseUrl = $j("body").attr("contextpath");
		var rs = occupiedInv(baseUrl+"/reOccupiedInvOut.json",postData);
		if(rs){
			$j("#errBtnlist").addClass("hidden");
			$j("#btnlist").removeClass("hidden");
			$j("#tbl-waittingList").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/getallpickingliststatus.json",postData:{"pickingList.checkMode":"PICKING_SECKILL"}}).trigger("reloadGrid");
			$j("#dphStatus").text("配货中");
		}
		orderDetail($j("body").attr("contextpath") + "/detailstalist.json?pickingListId="+id);
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
			$j("#tbl-waittingList").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/getallpickingliststatus.json",postData:{"pickingList.checkMode":"PICKING_SECKILL"}}).trigger("reloadGrid");
			$j("#dphStatus").text("配货中");
			var id = $j("#pickingListId").html();
			orderDetail($j("body").attr("contextpath") + "/detailstalist.json?pickingListId="+id);
			
			$j("#errBtnlist").addClass("hidden");
			$j("#errTransBtnlist").addClass("hidden");
			$j("#btnlist").removeClass("hidden");
			$j("#sum").html("0");
			$j("#sum1").html("0");
			$j("#detailTable tr").remove();
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
		orderDetail($j("body").attr("contextpath") + "/detailstalist.json?pickingListId="+id);
	});	
	
	// 转物流并删除
	$j("#btnChgTrans").click(function(){
		var id = $j("#div2").attr("plId");
		var postData = {};
		postData['plList[0]']= id;
		postData['pickingListId']= id;
		postData['lpcode']= $j("#selLpcode1").val();
		var baseUrl = $j("body").attr("contextpath");
		var rs = loxia.syncXhrPost(baseUrl + "/chgLpcodeRemoveFailedSta.json",postData);
		if(rs && rs.result == "success"){
			//成功
			$j("#errBtnlist").addClass("hidden");
			$j("#btnlist").removeClass("hidden");
			$j("#tbl-waittingList").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/getallpickingliststatus.json",postData:{"pickingList.checkMode":"PICKING_SECKILL"}}).trigger("reloadGrid");
			$j("#dphStatus").text("配货中");
			var id = $j("#pickingListId").html();
			orderDetail($j("body").attr("contextpath") + "/detailstalist.json?pickingListId="+id);
			
			$j("#errBtnlist").addClass("hidden");
			$j("#errTransBtnlist").addClass("hidden");
			$j("#btnlist").removeClass("hidden");
			$j("#sum").html("0");
			$j("#sum1").html("0");
			$j("#detailTable tr").remove();
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
		orderDetail($j("body").attr("contextpath") + "/detailstalist.json?pickingListId="+id);
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
			orderDetail($j("body").attr("contextpath") + "/detailstalist.json?pickingListId="+rs["pickingList"].id);
			// 2配货成功
			if(rs["pickingList"].intStatus == '2' ){
				$j("#errBtnlist").addClass("hidden");
				$j("#btnlist").removeClass("hidden");
				$j("#sum").html("0");
				$j("#sum1").html("0");
				$j("#detailTable tr").remove();
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
	
	//打印拣货单模式一      按钮功能
	$j("#printPickingList").click(function(){
		loxia.lockPage();
		if(beforePrintValidate()){
			var plid = $j("#div2").attr("plId");
			var plcode = $j("#dphCode").html();
			var plcount = $j("#planBillCount").html();
			var plskuqty = $j("#planSkuQty").html();
			var ploper = $j("#operator").html();	
			var pickZoneId =$j("#pickZoneCode").val();
			jumbo.showMsg(i18("OPERATING"));// 配货清单打印中，请等待...
			var url = $j("body").attr("contextpath") + "/printpickinglistmode1.json?plCmd.id="+ plid+"&plCmd.pickZoneId="+pickZoneId;  
			url += "&plCmd.code=" + plcode + "&plCmd.planBillCount=" + plcount;
			url += "&plCmd.planSkuQty=" + plskuqty + "&plCmd.operator.userName=" + ploper;
			printBZ(loxia.encodeUrl(url),true);
		}
		loxia.unlockPage();
	});
	//打印拣货单模式二 按钮功能
	$j("#printPickingListNew").click(function(){
		loxia.lockPage();
		if(beforePrintValidate()){
			var plid = $j("#div2").attr("plId");
			var pickZoneId =$j("#pickZoneCode").val();
			jumbo.showMsg(i18("OPERATING"));// 配货清单打印中，请等待...
			var url = $j("body").attr("contextpath") + "/printpickinglistnewmode1.json?pickingListId="+ plid+"&pickZoneId="+pickZoneId;  
			//var url = $j("body").attr("contextpath") + "/outboundprintpickinglistmodel2.json?pickingListId="+ plid;
			printBZ(loxia.encodeUrl(url),true);
		}
		loxia.unlockPage();
	});
	
	/**
	 * PDA拣货条码打印
	 */
	$j("#printPdaBarCode").click(function(){
		loxia.lockPage();
		jumbo.showMsg("PDA拣货条码打印");
		var url = $j("body").attr("contextpath") + "/printPdaBarCode.json?plCmd.id=" +$j("#div2").attr("plId");;
		printBZ(loxia.encodeUrl(url),true);
		loxia.unlockPage();
	});
	
	/**
	 * PDA拣货条码打印
	 */
	$j("#printPdaBarCodeS").click(function(){
		var ids = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
		loxia.lockPage();
		jumbo.showMsg("PDA拣货条码打印");
		var url = $j("body").attr("contextpath") + "/printPdaBarCodeS.json?plIds=" +ids;
		printBZ(loxia.encodeUrl(url),false);
		loxia.unlockPage();
	});
	
	// 打印装箱清单按钮功能
	$j("#printTrunkPackingList").click(function(){
		loxia.lockPage();
		if(beforePrintValidate()){
			var plid = $j("#div2").attr("plId");
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/queryDispatchListOutputCount.json",{"plCmd.id":plid});
			//var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/queryDispatchListOutputCountOut.json",{"plCmd.id":plid});
			if(rs && rs["msg"] == 'success' && parseInt(rs["count"]) == 0){
				var plcode = $j("#dphCode").html();
				jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
				var url = $j("body").attr("contextpath") + "/printtrunkpackinglistmode1.json?plCmd.id=" + plid + "&plCmd.code=" + plcode;
				//var url = $j("body").attr("contextpath") + "/printtrunkpackinglistmode1Out.json?plCmd.id=" + plid + "&plCmd.code=" + plcode;
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
	// 导出物流面单按钮功能
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
	
		//打印完成按钮
	$j("#printDone").click(function(){
		if(confirm("确认已全部完成该批次打印工作？")){
			var plid = $j("#div2").attr("plId");
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/updatesicklistwhstatus.json",{"pickingListId":plid});
			if(rs && rs["msg"] == 'SUCCESS'){
				jumbo.showMsg("打印完成操作成功！");
				var postData = loxia._ajaxFormToObj("query-form");
				postData["pickingList.checkMode"]="PICKING_SECKILL";
				$j("#tbl-waittingList").jqGrid('setGridParam',{
					url:window.$j("body").attr("contextpath")+"/json/getallpickingliststatus.do",
					postData:postData,
					page:1
				}).trigger("reloadGrid");
				$j("#div1").removeClass("hidden");
				$j("#div2").addClass("hidden");
			}else{
				jumbo.showMsg("打印完成操作失败！");
			}
		}
	});
	
	$j("#printerChoose").dialog({title: "默认打印机确认", modal:true, autoOpen: false, width: 450, height: 110});
	
	// 打印物流面单
	$j("#printDeliveryInfo").click(function(){
		var ems = $j("#tdIdLpcode").html();
//		if(isEmsOlOrder && (ems == 'EMS' || ems == 'EMSCOD')){
//			$j("#emsPrintAll").trigger("click");
//		}else{
			loxia.lockPage();
			if(beforePrintValidate()){
				var plid = $j("#div2").attr("plId");
				//jumbo.showMsg(i18("DELIVERYINFO"));
				var url = $j("body").attr("contextpath") + "/printdeliveryinfomode1.json?plCmd.id=" + plid;
				//printBZ(loxia.encodeUrl(url),true);			
				$j("#postPrintInfo").addClass("hidden");
				$j("#printerChoose").attr("url",url);
				$j("#printerChoose").dialog("open");
			}
			loxia.unlockPage();
//		}
	});
	
	$j("#useDefaultPrinter").click(function(){
		$j("#printerChoose").dialog("close");
		jumbo.showMsg(i18("DELIVERYINFO"));
		var url = $j("#printerChoose").attr("url");
		printBZ(loxia.encodeUrl(url),false);
	});
	
	$j("#chooseDefaultPrinter").click(function(){
		$j("#printerChoose").dialog("close");
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
	$j("#exportTrans").click(function(){
		var id = $j("#pickingListId").html();
		var code = $j("#dphCode").html();
		$j("#upload").attr("src",$j("body").attr("contextpath") + "/warehouse/exportstadeliver.do?pickingList.id=" + id + "&pickingList.code=" + code);	
	});
	$j("#sdSlipCode").keydown(function(evt){
		if(evt.keyCode == 13){
			evt.preventDefault();
			var b = validateSlipCode();
			if(b){
				$j("#transNo").focus();
			}
		}
	});
	$j("#transNo").keydown(function(evt){
		if(evt.keyCode == 13){
			evt.preventDefault();
			validateStaTransNo();
		}
	});
	$j("#confirm").click(function(){
		validateStaTransNo();
	});
	$j("#checkDetail").click(function(){
		$j("#td1").addClass("hidden");
		$j("#td2").removeClass("hidden");
	});
	$j("#reback").click(function(){
		$j("#td2").addClass("hidden");
		$j("#td1").removeClass("hidden");
	});
	$j("#saveScan,#saveStaTrans").click(function(){
		if($j("#sum").html()==0){
			jumbo.showMsg("物流明细不存在!");
		}else{
			var postData={};
			var j = 0;
			$j("#detailTable tr").each(function(i,tag){
				postData["std["+j+"].slipCode"]=$j(tag).children("td:eq(0)").html();
				postData["std["+j+"].transNo"]=$j(tag).children("td:eq(1)").html();
				j++;
			});
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/updatetransnobyslipcode.json",postData);
			if(rs&&rs.rs=="success"){
				jumbo.showMsg("物流面单维护成功!");
				$j("#sum").html("0");
				$j("#sum1").html("0");
				$j("#detailTable tr").remove();
			}else{
				jumbo.showMsg("物流面单维护失败!");
			}
		}
	});
	$j("#inportTrans").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		$j("#importForm").attr("action",window.parent.$j("body").attr("contextpath") + "/warehouse/importstadeliveryinfo.do");
		$j("#importForm").submit();
	});
});
//验证相关单据号和快递单号
function validateStaTransNo(){
	var b = validateSlipCode();
	if(b) validateTransNo();
}
//验证相关单据号
function validateSlipCode(){
	var slipCode = $j("#sdSlipCode").val();
	if(slipCode == ""){
		loxia.tip($j("#sdSlipCode"),"请输入相关单据号！");
		return false;
	}else{
		var b = false;
		$j("#detailTable tr").each(function(i,tag){
			if(slipCode==$j(tag).children("td:eq(0)").html()){
				b = true;
				return;
			}
		});
		if(b){
			loxia.tip($j("#sdSlipCode"),"该单据号已维护!");
			return false;
		}else{
			var pId = $j("#pickingListId").html();
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/findstabyslipcodeandpid.json",{"pickingList.code":slipCode,"pickingList.id":pId});
			if(rs&&rs["sta"]){
				$j("#staId").val(rs["sta"]["id"]);
				return true;
			}else{
				loxia.tip($j("#sdSlipCode"),"请输入正确的相关单据号!");
				return false;
			}
		}
	}
}
//验证快递单号
function validateTransNo(){
	var transNo = $j("#transNo").val();
	if(transNo == ""){
		loxia.tip($j("#transNo"),"请输入快递单号！");
	}else{
		var b = false;
		$j("#detailTable tr").each(function(i,tag){
			if(transNo==$j(tag).children("td:eq(1)").html()){
				b = true;
				return;
			}
		});
		if(b){
			loxia.tip($j("#transNo"),"该快递单号已被绑定");
		}else{
			var staId = $j.trim($j("#staId").val());
			var data ={trackingNo:transNo};
			data["sta.id"]=staId;
			var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/checktrackingno.json",data);
			if(rs && rs.result=='success'){
				var str = "<tr><td width='120px'>"+$j("#sdSlipCode").val()+"</td><td>"+$j("#transNo").val()+"</td></tr>";
				$j("#detailTable").append(str);
				$j("#sum").html(parseInt($j("#sum").html())+1);
				$j("#sum1").html(parseInt($j("#sum1").html())+1);
				$j("#sdSlipCode").val("");
				$j("#transNo").val("");
				$j("#sdSlipCode").focus();
			}else{
				loxia.tip($j("#transNo"),"快递单号格式不正确!");
			}
		}
	}
}
function showDetail1(pCode){
	if(pCode.length==0){
		loxia.tip($j("#pCode"),"请输入配货批次号！");
	}else{
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getpickinglistbycode.json",{"pickingList.code":pCode,"pickingList.checkMode":"PICKING_SECKILL","pickingStarus":1});
		if(rs&&rs["pickingList"]){
			$j("#divTbDetial").removeClass("hidden");
			$j("#div1").addClass("hidden");
			$j("#div2").removeClass("hidden");
			$j("#div3").addClass("hidden");
			$j("#pickZoneCode option:not(:first)").remove();
			$j("#div2").attr("plId",rs["pickingList"]["id"]);
			$j("#pickingListId").html(rs["pickingList"]["id"]);
			var id=rs["pickingList"]["id"]
			$j("#piid").val(rs["pickingList"]["id"]);
			$j("#dphCode").html( rs["pickingList"]["code"]);
			$j("#planBillCount").html( rs["pickingList"]["planBillCount"]);
			$j("#planSkuQty").html( rs["pickingList"]["planSkuQty"]);
			$j("#pickingTime").html( rs["pickingList"]["pickingTime"]);
			$j("#tdIdLpcode").html(rs["pickingList"]["lpcode"])
			$j("#checkedBillCount").html( rs["pickingList"]["checkedBillCount"]);
			$j("#checkedTime").html( rs["pickingList"]["checkedTime"]);
			$j("#checkedSkuQty").html( rs["pickingList"]["checkedSkuQty"]);
			$j("#lpName").html(rs["pickingList"]["deliveryCompany"]);
			$j("#lastSendTime").html( rs["pickingList"]["executedTime"]);
			$j("#creator").html(rs["pickingList"]["crtUserName"]);
			$j("#operator").html(rs["pickingList"]["operUserName"]);
			orderDetail($j("body").attr("contextpath") + "/detailstalist.json?pickingListId="+rs["pickingList"]["id"]);
			//加载区域下拉框
			var result = loxia.syncXhr(window.$j("body").attr("contextpath")+"/json/getPickingDistrict.do",{"pickingListId":id});
			for(var idx in result){
				$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#pickZoneCode"));
			}
			if(rs["pickingList"]["statusInt"] == '19'){
				$j("#dphStatus").html("快递无法送达");
				var transRs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getTransByPlId.json?pickingListId=" + rs["pickingList"]["id"]);
				var isCod = false;
				$j("#selLpcode1 option").remove();
				for(var idx in transRs){
					$j("<option value='" + transRs[idx].code + "'>"+ transRs[idx].name +"</option>").appendTo($j("#selLpcode1"));
					if(transRs[idx].isSupportCod == true){
						isCod = true;
					}
				}
				$j("#selLpcode1").show();
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
				$j("#errBtnlist").addClass("hidden");
				$j("#errTransBtnlist").addClass("hidden");
				$j("#btnlist").removeClass("hidden");
				$j("#sum").html("0");
				$j("#sum1").html("0");
				$j("#detailTable tr").remove();
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
