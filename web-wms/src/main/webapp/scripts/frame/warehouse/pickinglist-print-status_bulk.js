

$j.extend(loxia.regional['zh-CN'],{
	DISTRIBUTION_LIST_SELECT : "请选择配货清单",
	
	// colNames: ["ID",,],
	// colNames: ["ID","","","","","","","",""],
	CODE : "作业单号",
	INTSTATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	SHOPID : "淘宝店铺ID",
	LPCODE : "物流服务商",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	INT_LIST : "作业单列表",
	
	BATCHNO : "配货批次号",
	CREATETIME : "创建时间",
	PLAN_BILL_COUNT : "计划单数",
	PLAN_SKU_QTY : "计划件数",
	INTSTATUS : "状态",
	NEW_LIST : "新建配货清单列表",
	
	BATCHNO : "配货批次号",
	CREATETIME : "创建时间",
	PLANBILLCOUNT : "计划配货单据数", 
	PLAYSKUQTY : "计划配货商品件数",
	INTSTATUS : "状态",
	WAITING_LIST : "待配货清单列表",
	WORKER : "生成批次操作员",
	
	DISPATCH_FAILED : "由于您的配货单中所有作业单都失败，所以您的配货单被取消",
	ONLY_SUPPORT_SF : "O2O订单暂时只支持顺丰，不可转其它物流，请使用顺丰手写面单",
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
		colNames: ["ID","序列","status",i18("CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("SHOPID"),"物流服务",i18("CREATETIME"),"计划总件数",i18("OPETION")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name:"index",index:"index",width:50,reziable:true},
		           {name: "status",hidden: true},
		           {name: "code", index: "code",sortable: false, width: 100, resizable: true},
		           {name: "intStatus", index: "intStatus",sortable: false, width: 40, resizable: true,formatter:'select',editoptions:intStatus},
		           {name: "refSlipCode", index: "refSlipCode",sortable: false,width: 100, resizable: true},
		           {name: "intType", index: "intType",sortable: false, width: 90, resizable: true,formatter:'select',editoptions:staType},
		           {name: "shopId", index: "shopId",sortable: false, width: 120, resizable: true},
		           {name: "lpcode", index: "lpcode",sortable: false, width: 70, resizable: true,formatter:'select',editoptions:trasportName},
	               {name:"createTime",index:"createTime",sortable: false,width:120,resizable:true},
	               {name: "stvTotal", index: "stvTotal",sortable: false, width: 60, resizable: true},
	               {name: "idForBtn", width: 80,resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:i18("PRINGT"), onclick:"printSingleDelivery(this,event);"}}}],
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
//	if(isEmsOlOrder && ('EMS' == data['lpcode'] || 'EMSCOD' == data['lpcode'])){
//		var staCodes = [];
//		staCodes[0] = staCode;
//		var plid = $j("#pickingListId").html();
//		jumbo.emsprint(staCodes,plid);
//	}else{
		//var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?sta.id=" + id;
		var dt = new Date().getTime();
		var url = $j("body").attr("contextpath") + "/printsingledeliverymode1Out.json?rt="+ dt +"&sta.id=" + id;
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
	$j("#divTbDetial").removeClass("hidden");
	var baseUrl = $j("body").attr("contextpath");
	$j("#div1").addClass("hidden");
	$j("#div3").addClass("hidden");
	$j("#div2").removeClass("hidden");
	var id = $j(tag).parents("tr").attr("id");
	$j("#div2").attr("plId",id);
	$j("#pickingListId").html(id);
	$j("#selLpcode1").removeAttr("disabled");
	$j("#btnChgTrans").removeAttr("disabled");
	var tr = $j(tag).parents("tr");
	var data=$j("#tbl-waittingList").jqGrid("getRowData",id);
	var rs = loxia.syncXhrPost(baseUrl + "/getPickingList.json",{"pickingListId":id});
	var sortingMode = 0;
	var toLocation = null;
	var checkMode = null;
	if(rs && rs["pickingList"]){
		skusIdAndQty = rs["pickingList"]["packageSku"];
		//alert(skusIdAndQty);
		$j("#dphCode").html( rs["pickingList"]["code"]);
		$j("#planBillCount").html( rs["pickingList"]["planBillCount"]);
		$j("#planSkuQty").html( rs["pickingList"]["planSkuQty"]);
		$j("#pickingTime").html( rs["pickingList"]["pickingTime"]);
		$j("#checkedBillCount").html( rs["pickingList"]["checkedBillCount"]);
		$j("#checkedTime").html( rs["pickingList"]["checkedTime"]);
		$j("#checkedSkuQty").html( rs["pickingList"]["checkedSkuQty"]);
		$j("#isSn").html(rs["pickingList"]["isSn"]==true?"是":"否");
		$j("#sendSkuQty").html( rs["pickingList"]["sendSkuQty"]);
		$j("#sendStaQty").html( rs["pickingList"]["sendStaQty"]);
		$j("#lastSendTime").html( rs["pickingList"]["lastSendTime"]);
		sortingMode = rs["pickingList"]["sortingModeInt"];
		toLocation = rs["pickingList"]["toLocation"];
		checkMode = rs["pickingList"]["intCheckMode"];
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
		$j("#selLpcode1 option").remove();
		for(var idx in transRs){
			$j("<option value='" + transRs[idx].code + "'>"+ transRs[idx].name +"</option>").appendTo($j("#selLpcode1"));
			if(transRs[idx].isSupportCod == true){
				isCod = true;
			}
		}
		if(!isCod){
			$j("#selLpcode1").val("EMS");
		}
		$j("#errTransBtnlist").removeClass("hidden");//快递无法送达
//		$j("#btnlist").addClass("hidden");//配货中，或者部分完成
		$j("#errBtnlist").addClass("hidden");//配货失败，待配货
	}else if(data["intStatus"] == '20'||data["intStatus"] == '1'){
//		$j("#btnlist").addClass("hidden");
		$j("#errTransBtnlist").addClass("hidden");
		$j("#errBtnlist").removeClass("hidden");
	}else{
		if(sortingMode==2){
			$j("#printPickingList").addClass("confirm");
			$j("#printPickingListNew").removeClass("confirm");
		}else{
			$j("#printPickingListNew").addClass("confirm");
			$j("#printPickingList").removeClass("confirm");
		}
		$j("#errBtnlist").addClass("hidden");
		$j("#errTransBtnlist").addClass("hidden");
//		$j("#btnlist").removeClass("hidden");
	}
	
	//O2O暂时使用手写电子面单，如果是O2O+QS的配货批，则隐藏打印物流面单按钮
	if(toLocation && checkMode==6){
		$j("#printDeliveryInfo").addClass("hidden");
		$j("#selLpcode1").attr("disabled","disabled");
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
var skusIdAndQty = "";
$j(document).ready(function (){
	var arrStr1 = new Array();
	var arrStr2=new Array();
	$j("#priorityCity").multipleSelect({
		 width: 200,
		 filter:true,
		 placeholder: "请选择",
		 onOpen: function() {
			 //$j("#priorityCity ul li:first").remove();
			 $j(".ms-select-all").remove();
       },
       onClick: function(view) {
          var res = view.label;
   		arrStr1.push(res);
   		var result1 = arrStr1.indexOf("所有优先发货城市");
   		var result2 = arrStr1.indexOf("非优先发货城市");
   		//alert(arrStr1);
   		if (result1 != -1 && result2 != -1) {
   			$j(".ms-choice span").text('');
   			$j("#priorityCity").multipleSelect("uncheckAll");
   			arrStr1= [];
   			$j('#priorityCity').multipleSelect('refresh');
   			jumbo.showMsg("不能同时选择'所有优先发货城市'和'非优先发货城市', 请重新选择!");
			} else if (result1 != -1 && arrStr1.length > 1) {
				$j(".ms-choice span").text('');
				$j("#priorityCity").multipleSelect("uncheckAll");
   			$j('#priorityCity').multipleSelect('refresh');
   			arrStr1= [];
				jumbo.showMsg("不能同时多选!");
			} else if (result2 != -1 && arrStr1.length > 1) {
				$j(".ms-choice span").text('');
				$j("#priorityCity").multipleSelect("uncheckAll");
   			$j('#priorityCity').multipleSelect('refresh');
   			arrStr1= [];
				jumbo.showMsg("不能同时多选!");
			}
       }
	});
	
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
	//初始化物流商信息
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
	}
	//初始化O2O目的地编码
	var otoResult = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getallshopstoreforoption.do");
	for(var idx in otoResult){
		$j("<option value='" + otoResult[idx].code + "'>"+ otoResult[idx].name +"</option>").appendTo($j("#selToLocation"));
	}
	
	//初始化生成批次操作员的名字
	
	
	//初始化商品大小
	var cList = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findskusize.json");
	$j("#skuSizeSelect").append("<option value=''>--请选择--</option>");
	for(var i=0;i<cList.length;i++){
		$j("#skuSizeSelect").append("<option value="+cList[i].id+">"+cList[i].name+"</option>");
	}
	//初始化商品分类
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findCategories.do");
	for(var idx in result){
		$j("<option value='" + result[idx].id + "'>" + result[idx].skuCategoriesName+"</option>").appendTo($j("#categoryId"));
	}
	var whinfo = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getWarehouseInfo.json");
	if(whinfo.isEmsOlOrder){
		isEmsOlOrder = true;
	}
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	var trueOrFalse=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"trueOrFalse"});
	$j("#pCode").focus();
	var plStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"pickingListStatus"});
	$j("#tbl-waittingList").jqGrid({
		url:$j("body").attr("contextpath") + "/getallpickingliststatusBulk.json",
		datatype: "json",
		colNames: ["ID","outputCount",i18("BATCHNO"),i18("CREATETIME"),i18("PLANBILLCOUNT"),i18("PLAYSKUQTY"),i18("INTSTATUS"),"物流","是否sn","是否需要发票","优先发货城市","商品分类","商品大小件","是否是预售订单",i18("WORKER")],
		colModel: [
				   {name: "id", index: "id", hidden: true,sortable:false,sortable:false},
		           {name: "outputCount", index: "outputCount", hidden: true,sortable:false,sortable:false},
		           {name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showDetail"},width:120,resizable:true,sortable:false},         
		           {name: "createTime", index: "createTime", width: 150, resizable: true,sortable:false,sortable:false},
		           {name: "planBillCount", index: "planBillCount", width: 150, resizable: true,sortable:false},
		           {name: "planSkuQty", index: "planSkuQty", width: 150, resizable: true,sortable:false},
	               {name:"intStatus",index:"status",width:50,resizable:true,formatter:'select',editoptions:plStatus,sortable:false},
		           {name:"lpcode", index: "lpcode",width: 200, resizable: true,formatter:'select',editoptions:trasportName,sortable:false},
	               {name:"isSn",index:"isSn",width:80,resizable:true,formatter:'select',editoptions:trueOrFalse,sortable:false},
		           {name:"isInvoice",index:"isInvoice",width:80,resizable:true,formatter:'select',editoptions:trueOrFalse,sortable:false},
	               {name:"city",index:"city",width:80,resizable:true,sortable:false},
	               {name:"categoryName",index:"categoryName",width:80,sortable:false},
	               {name:"skuSizeName",index:"skuSizeName",width:80,sortable:false},
	               {name:"isPreSale",index:"isPreSale"},
	               {name:"worker",index:"worker",width:100,sortable:false}
	               ],
		caption: i18("WAITING_LIST"),// 待配货清单列表
		pager:"#pager_query",
	    //rowNum: 5,
		rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	   	sortname: 'id',
	   	height:"auto",
	   	sortorder: "asc",
	    multiselect: true,
	    rownumbers:true,
	    viewrecords: true,
		hidegrid:false,
		jsonReader: { repeatitems : false, id: "0" },
		
		// add by liubin 将打印的清单设置背景色 
		loadComplete: function(){
			var ids = $j("#tbl-waittingList").jqGrid('getDataIDs');
			var pickingIdList="";
			for(var i=0;i<ids.length;i++){
				var rowData = $j("#tbl-waittingList").getRowData(ids[i]);
				var outputCount = rowData['outputCount'];
				if (outputCount > 0) {
					$j("#" + ids[i]).addClass("printRow");
				}
				if(ids.length>0){
					if(i>0){
						pickingIdList=pickingIdList+","+ids[i];
					}else{
						pickingIdList=pickingIdList+ids[i];
					}
				}
			}
			
			//加载区域下拉框
			var result = loxia.syncXhr(window.$j("body").attr("contextpath")+"/json/getPickingDistrictList.do",{"pickingListArray":pickingIdList});
			for(var idx in result){
				$j("<option value='" + result[idx].id + "'>"+ result[idx].name +"</option>").appendTo($j("#pickZoneCode"));
			}
		}
	});
	
	$j("#back,#back2,#back3").click(function(){
		$j("#div2").addClass("hidden");
		$j("#div3").removeClass("hidden");
		$j("#div1").removeClass("hidden");
//		$j("#btnlist").addClass("hidden");
		$j("#errBtnlist").addClass("hidden");
		$j("#divTbDetial").addClass("hidden");
		$j("#metrobtn").addClass("hidden");
		$j("#printDeliveryInfo").removeClass("hidden");
	});

	$j("#exportAgv").click(function(){
		// var url=$j("body").attr("contextpath") + "/getExportAgv.json";
		 var areaCode =$j("#pickZoneCode").val();
		 var  plCode=$j("#dphCode").text();
		$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/getExportAgv.do?plCode=" + plCode+"&areaCode="+areaCode);
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
		var rs = occupiedInv(baseUrl+"/reOccupiedInvOut.json",postData);
		if(rs){
			$j("#errBtnlist").addClass("hidden");
//			$j("#btnlist").removeClass("hidden");
			$j("#tbl-waittingList").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/getallpickingliststatus.json"}).trigger("reloadGrid");
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
//			$j("#btnlist").removeClass("hidden");
			$j("#tbl-waittingList").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/getallpickingliststatus.json"}).trigger("reloadGrid");
			$j("#dphStatus").text("配货中");
			var id = $j("#pickingListId").html();
			orderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+id);
			
			$j("#errBtnlist").addClass("hidden");
			$j("#errTransBtnlist").addClass("hidden");
//			$j("#btnlist").removeClass("hidden");
		}else if(rs && rs.result == "remove"){
			//被删除
			$j("#div2").addClass("hidden");
			$j("#div1").removeClass("hidden");
			$j("#tbl-waittingList").find("tr[id='" + id + "']").remove();
			jumbo.showMsg(i18("DISPATCH_FAILED"));// 由于您的配货单中所有作业单都失败，所以您的配货单被取消
			
		}else if(rs&&rs.result == 'error'){
			//配货失败
			jumbo.showMsg(rs.msg);
//			$j("#btnlist").addClass("hidden");
			$j("#errTransBtnlist").addClass("hidden");
			$j("#errBtnlist").removeClass("hidden");
		}
		orderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+id);
	});	
	
	// 转物流并删除
	$j("#btnChgTrans").click(function(){
		var selLpcode1  = $j("#selLpcode1").attr("disabled");
		if(selLpcode1){
			$j("#btnChgTrans").attr("disabled","disabled");
			jumbo.showMsg(i18("ONLY_SUPPORT_SF"));
		}
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
//			$j("#btnlist").removeClass("hidden");
			$j("#tbl-waittingList").jqGrid('setGridParam',{url:$j("body").attr("contextpath") + "/getallpickingliststatus.json"}).trigger("reloadGrid");
			$j("#dphStatus").text("配货中");
			var id = $j("#pickingListId").html();
			orderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+id);
			
			$j("#errBtnlist").addClass("hidden");
			$j("#errTransBtnlist").addClass("hidden");
//			$j("#btnlist").removeClass("hidden");
		}else if(rs && rs.result == "remove"){
			//被删除
			$j("#div2").addClass("hidden");
			$j("#div1").removeClass("hidden");
			$j("#tbl-waittingList").find("tr[id='" + id + "']").remove();
			jumbo.showMsg(i18("DISPATCH_FAILED"));// 由于您的配货单中所有作业单都失败，所以您的配货单被取消
			
		}else if(rs&&rs.result == 'error'){
			//配货失败
			jumbo.showMsg(rs.msg);
//			$j("#btnlist").addClass("hidden");
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
//				$j("#btnlist").removeClass("hidden");
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
			var dt = new Date().getTime();
			var plid = $j("#div2").attr("plId");
			var plcode = $j("#dphCode").html();
			var plcount = $j("#planBillCount").html();
			var plskuqty = $j("#planSkuQty").html();
			var ploper = $j("#operator").html();		
			var url = $j("body").attr("contextpath") + "/warehouse/pickingListMode1Export.do?rt="+ dt +"&plCmd.id="+ plid;  
			url += "&plCmd.code=" + plcode + "&plCmd.planBillCount=" + plcount;
			url += "&plCmd.planSkuQty=" + plskuqty + "&plCmd.operator.userName=" + ploper;
			$j("#exportFrame").attr("src",url);
		}
	});
	
	
	// 打印拣货单模式一      按钮功能
	$j("#printPickingList").click(function(){
		var staIds = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
		if(staIds.length==0) {jumbo.showMsg("数据为空，数据出错..."); return false;}
		loxia.lockPage();
			var dt = new Date().getTime();
			var pickZoneId=$j("#pickZoneCode").val();
//			jumbo.showMsg(i18("OPERATING"));// 配货清单打印中，请等待...
			var url = $j("body").attr("contextpath") + "/printPickingListModeBulk1.json?rt="+ dt +"&plList="+ staIds+"&pickZoneId="+pickZoneId;
			$j("#printerChoose").attr("url",url);
			$j("#printerChoose").dialog("open");
//			printBZ(loxia.encodeUrl(url),true);
		loxia.unlockPage();
	});
	//打印拣货单模式二 按钮功能
	/*$j("#printPickingListNew").click(function(){
		loxia.lockPage();
		if(beforePrintValidate()){
			var dt = new Date().getTime();
			var plid = $j("#div2").attr("plId");
			jumbo.showMsg(i18("OPERATING"));// 配货清单打印中，请等待...
			var url = $j("body").attr("contextpath") + "/printpickinglistnewmode1.json?rt="+ dt +"&pickingListId="+ plid;
			printBZ(loxia.encodeUrl(url),true);
		}
		loxia.unlockPage();
	});*/
	//打印拣货单模式二 按钮功能...
	$j("#printPickingListNew2").click(function(){
		var staIds = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
		if(staIds.length==0) {jumbo.showMsg("数据为空，数据出错..."); return false;}
		loxia.lockPage();
			var dt = new Date().getTime();
			var pickZoneId=$j("#pickZoneCode").val();
//			jumbo.showMsg(i18("OPERATING"));// 配货清单打印中，请等待...
			var url = $j("body").attr("contextpath") + "/printPickingListModeBulkNew2.json?rt="+ dt +"&plList="+ staIds+"&pickZoneId="+pickZoneId;
			$j("#printerChoose").attr("url",url);
			$j("#printerChoose").dialog("open");
//			printBZ(loxia.encodeUrl(url),true);
		loxia.unlockPage();
	});
	
	/**
	 * PDA拣货条码打印
	 */
	$j("#printPdaBarCode").click(function(){
		var staIds = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
		loxia.lockPage();
		jumbo.showMsg("PDA拣货条码打印");
		var url = $j("body").attr("contextpath") + "/printPdaBarCodeS.json?plIds=" +staIds;
		printBZ(loxia.encodeUrl(url),false);
		loxia.unlockPage();
	});
	
	// 打印装箱清单按钮功能
	$j("#printTrunkPackingList").click(function(){
		loxia.lockPage();
			var dt = new Date().getTime();
			var staIds = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
			for (i=0;i<staIds.length ;i++ ) 
			{ 
				var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/queryDispatchListOutputCount.json?rt="+ dt,{"plCmd.id":staIds[i]});
				if(rs && rs["msg"] == 'success' && parseInt(rs["count"]) == 0){
					
				}else{
					jumbo.showMsg("装箱清单已打印，不能重复打印！");
					loxia.unlockPage();
					return;
				}
				
			} 
				var url = $j("body").attr("contextpath") + "/printTrunkPackingListMode1OutBulk.json?rt="+ dt +"&plList=" + staIds;
				$j("#printerChoose").attr("url",url);
				$j("#printerChoose").dialog("open");
//					jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
//					printBZ(loxia.encodeUrl(url),true);
					for (i=0;i<staIds.length ;i++ ) {
						$j("#" + staIds[i]).addClass("printRow");
					}
				
		loxia.unlockPage();
	});
	
	$j("#printerChoose").dialog({title: "默认打印机确认", modal:true, autoOpen: false, width: 450, height: 110});
	
	// 打印物流面单
	$j("#printDeliveryInfo").click(function(){
		var staIds = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
		if(staIds.length==0) {jumbo.showMsg("数据为空，数据出错..."); return false;}
			loxia.lockPage();
				var dt = new Date().getTime();
				var url = $j("body").attr("contextpath") + "/printdeliveryinfomode1Bulk.json?rt="+ dt +"&plList=" + staIds;
				$j("#printerChoose").attr("url",url);
				$j("#printerChoose").dialog("open");
//				printBZ(loxia.encodeUrl(url),true);
				
			loxia.unlockPage();
	});
	
	$j("#useDefaultPrinter").click(function(){
		$j("#printerChoose").dialog("close");
		var url = $j("#printerChoose").attr("url");
		var str=url.substring(5,url.indexOf('.'));
		if(str=='printdeliveryinfomode1Bulk'){
			jumbo.showMsg(i18("DELIVERYINFO"));
		}else if(str=='printTrunkPackingListMode1OutBulk'){
			jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
		}else if (str=='printPickingListModeBulk1'){
			jumbo.showMsg(i18("OPERATING"));
		}
		printBZ(loxia.encodeUrl(url),false);
		
		/**批量打印优化，js循环打印，减少内存消耗**/
//		$j("#printerChoose").dialog("close");
//		
//		var url = $j("#printerChoose").attr("url");
//		var str1 = url.indexOf("plList=");
//		var str2 = url.indexOf("&pickingZoneIds=");
//		var strEnd="";
//		if(str2 != -1){
//			strEnd = url.substring(str2,url.length);
//		}else{
//			var str3 = url.indexOf("&pickZoneId=");
//			if(str3 != -1){
//				strEnd = url.substring(str3,url.length);
//			}
//		}
//		var strIndex = url.substring(0,str1);
//		var ids = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
//		for(var i = 0;i<ids.length;i++){
//			url = strIndex+"plList="+ids[i]+strEnd;
//			setTimeout(function(){
//				printBZ(loxia.encodeUrl(url),false);
//			   }, 700);
//		}
//		var str=url.substring(5,url.indexOf('.'));
//		if(str=='printdeliveryinfomode1Bulk'){
//			jumbo.showMsg(i18("DELIVERYINFO"));
//		}else if(str=='printTrunkPackingListMode1OutBulk'){
//			jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
//		}else if (str=='printPickingListModeBulk1'){
//			jumbo.showMsg(i18("OPERATING"));
//		}
	});
	
	$j("#chooseDefaultPrinter").click(function(){
		$j("#printerChoose").dialog("close");
	});
	
	// 税控发票导出
	$j("#btnSoInvoice").click(function(){
		var staIds = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
		if(staIds.length>0){
//			var postData = {};
//			for(var i in staIds){
//				postData['plList[' + i + ']']=staIds[i];
//			}
			$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/exportSoInvoices.do?plList=" + staIds);
		}else{
			jumbo.showMsg(loxia.getLocaleMsg("DISTRIBUTION_LIST_SELECT"));//请选择作业申请单
		}
		
	});
	
	// 税控发票导出压缩文件
	$j("#btnSoInvoiceZip").click(function(){
		var id = $j("#div2").attr("plId");
		$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/exportSoInvoiceZip.do?pickingListId=" + id);
	});
	//查询按钮功能 根据作业单号和状态查询
	$j("#btn-query").click(function(){
		var postData = loxia._ajaxFormToObj("query-form");
		var priority=postData["priority"];
		postData["priority"]=priority.join(",");
		var city=postData["pickingList.city"];
		postData["pickingList.city"]=city.join(",");
		$j("#tbl-waittingList").jqGrid('setGridParam',{
			url:window.$j("body").attr("contextpath")+"/json/getallpickingliststatusBulk.do",
			postData:postData,
			page:1
		}).trigger("reloadGrid");
	});
	
	//打印完成按钮
	$j("#printDone").click(function(){
		if(confirm("确认已全部完成该批次打印工作？")){
			var staIds = $j("#tbl-waittingList").jqGrid('getGridParam','selarrrow');
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/updatesicklistwhstatusBulk.json?plList="+staIds);
			if(rs && rs["msg"] == 'SUCCESS'){
				jumbo.showMsg("打印完成操作成功！");
				window.location.href=window.location.href;
				$j("#div1").removeClass("hidden");
				$j("#div2").addClass("hidden");
			}else{
				jumbo.showMsg("打印完成操作失败！");
			}
		}
	});
	//重置
	$j("#reset").click(function(){
		$j("#query-form input,#query-form select").val("");

	});
	//初始化时进入光标定位文本框，按enter执行相应的工作
	$j("#pCode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			showDetail1($j("#pCode").val());
		}
	});
	$j('#priorityCity').multipleSelect('refresh');
});
function showDetail1(pCode){
	var toLocation = null;//判断配货批是否为O2O
	var checkMode = null;
	if(pCode.length==0){
		loxia.tip($j("#pCode"),"请输入配货批次号！");
	}else{
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getpickinglistbycode.json",{"pickingList.code":pCode,"pickingStarus":1});
		if(rs&&rs["pickingList"]){
			skusIdAndQty = rs["pickingList"]["packageSku"];
			$j("#divTbDetial").removeClass("hidden");
			$j("#div1").addClass("hidden");
			$j("#div2").removeClass("hidden");
			$j("#div2").attr("plId",rs["pickingList"]["id"]);
			$j("#pickingListId").html(rs["pickingList"]["id"]);
			$j("#dphCode").html( rs["pickingList"]["code"]);
			$j("#planBillCount").html( rs["pickingList"]["planBillCount"]);
			$j("#planSkuQty").html( rs["pickingList"]["planSkuQty"]);
			$j("#pickingTime").html( rs["pickingList"]["pickingTime"]);
			$j("#checkedBillCount").html( rs["pickingList"]["checkedBillCount"]);
			$j("#checkedTime").html( rs["pickingList"]["checkedTime"]);
			$j("#checkedSkuQty").html( rs["pickingList"]["checkedSkuQty"]);
			$j("#isSn").html(rs["pickingList"]["isSn"]==true?"是":"否");
			$j("#sendSkuQty").html( rs["pickingList"]["sendSkuQty"]);
			$j("#sendStaQty").html( rs["pickingList"]["sendStaQty"]);
			$j("#lastSendTime").html( rs["pickingList"]["lastSendTime"]);
			$j("#creator").html(rs["pickingList"]["crtUserName"]);
			$j("#operator").html(rs["pickingList"]["operUserName"]);
			$j("#tdIdLpcode").html( rs["pickingList"]["lpcode"]);
			orderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+rs["pickingList"]["id"]);
			$j("#gbox_tbl-waittingList").appendTo($j("#div-waittingList"));
			$j("#hidden-waittingList").addClass("hidden");
			toLocation = rs["pickingList"]["toLocation"];
			checkMode = rs["pickingList"]["intCheckMode"];
			$j("#selLpcode1").removeAttr("disabled");
			$j("#btnChgTrans").removeAttr("disabled");
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
				if(!isCod){
					$j("#selLpcode1").val("EMS");
				}
				$j("#errTransBtnlist").removeClass("hidden");//快递无法送达
//				$j("#btnlist").addClass("hidden");//配货中，或者部分完成
				$j("#errBtnlist").addClass("hidden");//配货失败，待配货
			}else if(rs["pickingList"]["statusInt"] == '20'||rs["pickingList"]["statusInt"] == '1'){
				if(rs["pickingList"]["statusInt"]=='20'){
					$j("#dphStatus").html("配货失败");
				}else{
					$j("#dphStatus").html("等待配货");
				}
//				$j("#btnlist").addClass("hidden");
				$j("#errTransBtnlist").addClass("hidden");
				$j("#errBtnlist").removeClass("hidden");
			}else{
				if(rs["pickingList"]["statusInt"]=='2'){
					$j("#dphStatus").html("配货中");
				}else{
					$j("#dphStatus").html("部分完成");
				}
				if(rs["pickingList"]["sortingModeInt"]==2){
					$j("#printPickingList").addClass("confirm");
					$j("#printPickingListNew").removeClass("confirm");
				}else{
					$j("#printPickingListNew").addClass("confirm");
					$j("#printPickingList").removeClass("confirm");
				}
				$j("#errBtnlist").addClass("hidden");
				$j("#errTransBtnlist").addClass("hidden");
//				$j("#btnlist").removeClass("hidden");
			}
		}else{
			loxia.tip($j("#pCode"),"你输入的批次号不在范围内，首先确保单据号存在，状态在选择范围内");
		}
	}
	//O2O暂时使用手写电子面单，如果是O2O+QS的配货批，则隐藏打印物流面单按钮
	if(toLocation && checkMode==6){
		$j("#printDeliveryInfo").addClass("hidden");
		$j("#selLpcode1").attr("disabled","disabled");
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
