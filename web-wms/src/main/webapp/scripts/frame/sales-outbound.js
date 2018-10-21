$j.extend(loxia.regional['zh-CN'],{
	INPUT_WEIGHT : "请输入货物重量",
	NUMBER_RULE : "货物不是一个合法的数字或精度要求不符合要求",
	WEIGHT_LAGGER : "货物重量不得超过150KG",
	
	SKUNAME : "商品名称",
	JMCODE : "商品编码",
	KEYPROPERTIES : "扩展属性",
	BARCODE : "条形码",
	QUANTITY : "计划执行数量",
	COMPLETEQUANTITY : "已执行数量",
	ORDER_DETAIL : "作业单相关明细",
	
	NO_ORDER : "该作业单不存在或已经出库",
	INPUT_TRACKINGNO : "请输入快递单号",
	OUTBOUND : "出库成功",
	ORDER_CANCELED : "出库失败: 作业单含有未出库包裹",
	OUTBOUND_ERROR : "出库失败: 执行出库异常"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

//checkmaster 
function doExecute(value,obj){
	if(value == ""){
		//showOutBoundInfo(i18("INPUT_WEIGHT"));
		return i18("INPUT_WEIGHT");//请输入货物重量
	}
	if(!/^[0-9]*\.{0,1}[0-9]*$/.test(value)){
		//showOutBoundInfo(i18("NUMBER_RULE"));
		return i18("NUMBER_RULE");//不是一个合法的数字或精度要求不符合要求
	}
	value = parseFloat(value);
	if(value == 0){
		//showOutBoundInfo(i18("INPUT_WEIGHT"));
		return i18("INPUT_WEIGHT");//请输入货物重量
	}else if(value > 150){
		//showOutBoundInfo(i18("WEIGHT_LAGGER"));
		return i18("WEIGHT_LAGGER");//货物重量不得超过150KG
	}	
	//errObj = "#goodsWeigth";
	return loxia.SUCCESS;
}


var reload = false;
function loadTableData(staId){
	var baseUrl = $j("body").attr("contextpath");
	var postData = {};
	var url = loxia.getTimeUrl(baseUrl + "/getStaLineByTrackingNo.json");
	postData["staId"] = staId;
	var baseUrl = $j("body").attr("contextpath");
	if(!reload){
		$j("#tbl-billView").jqGrid({
//			url:url,
			postData:postData,
			datatype: "json",
			//colNames: ["ID","商品名称","商品编码","扩展属性","条形码","计划执行数量","已执行数量"],
			colNames: ["ID",i18("SKUNAME"),i18("JMCODE"),i18("KEYPROPERTIES"),i18("BARCODE"),i18("QUANTITY"),i18("COMPLETEQUANTITY")],
			colModel: [{name: "id", index: "id", hidden: true},
			{name:"skuName", index:"skuName" ,width:150,resizable:true},
			{name:"jmcode", index:"jmcode",width:150,resizable:true},
			{name: "keyProperties", index: "keyProperties", width: 100, resizable: true},
			{name:"barCode", index:"barCode", width:90, resizable:true},
			{name:"quantity",index:"quantity",width:120,resizable:true},
			{name:"completeQuantity",index:"completeQuantity",width:100,resizable:true}],
			caption: i18("ORDER_DETAIL"),//作业单相关明细
			sortname: 'ID',
			multiselect: false,
			rowNum: jumbo.getPageSize(),
		    rowList:jumbo.getPageSizeList(),
		    pager:"#pager",
			sortorder: "desc",
			height:"auto",
			rowNum:-1,
			jsonReader: { repeatitems : false, id: "0" }
		});
		reload = true;
	}else{
		$j("#tbl-billView").jqGrid('setGridParam',{url:url,postData:postData}).trigger("reloadGrid");
	}
}

function clearWeight(){
	if($j("#autoWeigth").attr("clear") != "true"){
		$j("#autoWeigth").attr("clear","true");
		//关秤
		appletStop();
		//清除单号
		$j("#staDiv").attr("trackingNo","");
		$j("#soCode").html("");
		$j("#shopId").html("");
		$j("#lpcode").html("");
		$j("#staDiv").attr("");
		$j("#staDiv").attr("staCode","");
		$j("#billsId").val("");
		//$j("#billsId").focus();
		$j("#goodsWeigth").val("");
		//$j("#tbl-billView tr[id]").remove();
		//$j("#executeOutbound").addClass("hidden");
		//清除重量
		$j("#autoWeigth").val("");
		loxia.byId("goodsWeigth").val("").state(null);
		$j("#wrapStuff").val("");
	}
}

function startWeight(){
	if($j("#autoWeigth").attr("clear") === "true"){
		$j("#autoWeigth").attr("clear","false");
		//开秤
		appletStart();
	}
}

function addBarCode(){
	var varCode = jQuery.trim($j("#wrapStuff").val());
	if(($j("#wrapStuff").val() == ''||$j("#wrapStuff").val() == null) && $j("#barCode_tab tbody tr").length > 0){
		$j("#goodsWeigth").focus();
	}
	if(varCode != ""){
		// 判断是否为耗材
		var result = loxia.syncXhrPost(loxia.getTimeUrl(window.$j("body").attr("contextpath")+"/getSkuTypeByBarCode.json?barCode="+varCode));
		if (!result || result.value != 1){
			$j("#wrapStuff").val("");
			jumbo.showMsg("该包材不存在或为非耗材!");
			return;
		}
		var isAdd = false;
		$j("#barCode_tab tbody tr").each(function (i,tag){
			if(varCode == $j(tag).find("td:eq(1) :input").val()){
				var index = $j(tag).index;
				$j(tag).find("td:eq(2) :input").val(parseInt($j(tag).find("td:eq(2) :input").val())+1);
				isAdd = true;
			}
		});
		if(isAdd == false){
			$j("button[action=add]").click();
			var length = $j("#barCode_tab tbody tr").length-1;
			$j("#barCode_tab tbody tr:eq("+length+") td:eq(1) :input").val(varCode);
			$j("#barCode_tab tbody tr:eq("+length+") td:eq(2) :input").val(1);
		}
	}
	if($j("#barCode_tab tbody tr").length > 0){
		$j("#wrapStuff").val("");
		//$j("#goodsWeigth").focus();
		var isManualWeighing = $j("#goodsWeigth").attr("isManualWeighing");
		// true  自动称
		if(isManualWeighing != 'true'){
			$j("#confirmWeightInput").focus();
		}else {
			$j("#goodsWeigth").focus();	
		}
	}else {
		//showOutBoundInfo("包材条码不能为空");
		showErrorInfoDialog("包材条码不能为空");
		//errObjs.push("#wrapStuff");
		if(whichWidgetIsError("#wrapStuff")){
			errObjs.push("#wrapStuff");
		}
		//errObj = "#wrapStuff";
	}
}

var rswh = null;
var errObjs = [];
var c_success = 0, c_failed = 0;
var success_transMap = {},failed_transMap = {};
var lineNo;
$j(document).ready(function (){
//	loxia.byId("autoWeigth").setEnable(false);
	
	$j("#outbound_dialog").dialog({title: "出库确认信息", modal:true, autoOpen: false, width: 600, height: 290});
	$j("#outbound_result_dialog").dialog({title: "出库结果信息", modal:true, autoOpen: false, width: 600, height: 290});
	$j("#show_error_dialog").dialog({title: "出库信息", modal:true, autoOpen: false, width: 500, height: 200});
	$j("#weight_input_dialog").dialog({title: "重量确认", modal:true, autoOpen: false, width: 500, height: 200});
	
	$j("#autoWeigth").css("color","black");
	$j("button[action=add]").addClass("hidden");
	var baseUrl = $j("body").attr("contextpath");
	//loadTableData("");
	
	// var rswh = loxia.syncXhrPost(loxia.getTimeUrl(baseUrl + "/findwarehousebaseinfo.json"));
	rswh = loxia.syncXhrPost(loxia.getTimeUrl(baseUrl + "/findwarehousebaseinfo.json"));
	if(rswh && rswh.warehouse){
		if (rswh.warehouse.isNeedWrapStuff){
			$j("#p1").removeClass("hidden");
			$j("#p2").removeClass("hidden");
			$j("#p_tr").removeClass("hidden");
		}else{
			$j("#p1").addClass("hidden");
			$j("#p2").addClass("hidden");
			$j("#p_tr").addClass("hidden");
		}
	}
	
	$j("#billsId").keydown(function(event){
		if(event.keyCode == 13){
			var code = $j("#billsId").val().trim();
			var postData = {};
			postData["trackingNo"]=code;
			var rs = loxia.syncXhrPost(baseUrl + "/findStaByTrackingNo.json",postData);
			clearWeight();
			if(rs && rs.result=="success"){
				$j("#billsId").val(code);
				$j("#soCode").html(rs.sta["refSlipCode"]);
				$j("#shopId").html(rs.sta["shopId"]);
				$j("#staDiv").attr("staId",rs.sta["id"]);
				$j("#staDiv").attr("staCode",rs.sta["code"]);
				$j("#staDiv").attr("trackingNo",code);
				$j("#goodsWeigth").attr("isManualWeighing",rs.sta["isManualWeighing"]);
				//$j("#executeOutbound").removeClass("hidden");
				var lpcode = rs.sta["lpcode"];
				var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do",{"optionKey":lpcode});
				var value = trasportName.value.split(":")[1];
				$j("#lpcode").html(value);
				//loadTableData(rs.sta["id"]);
				//$j("#goodsWeigth").val("");
				if (rswh.warehouse.isNeedWrapStuff){
					$j("#wrapStuff").focus();
				}else {
					var isManualWeighing = $j("#goodsWeigth").attr("isManualWeighing");
					// true  自动称
					if(isManualWeighing != 'true'){
						$j("#confirmWeightInput").focus();
					}else {
						$j("#goodsWeigth").focus();	
					}
				}
				//open weight interface
				startWeight();
			}else{
				//jumbo.showMsg(i18("NO_ORDER"));//该作业单不存在或已经出库
				//showOutBoundInfo(i18("NO_ORDER"),"#billsId");
				//showOutBoundInfo(i18("NO_ORDER"));
				showErrorInfoDialog(i18("NO_ORDER"));
				//errObjs.push("#billsId");
				if(whichWidgetIsError("#billsId")){
					errObjs.push("#billsId");
				}
				$j("#staDiv").attr("trackingNo","");
				$j("#soCode").html("");
				$j("#shopId").html("");
				$j("#lpcode").html("");
				$j("#staDiv").attr("");
				$j("#staDiv").attr("staCode","");
				//$j("#tbl-billView tr[id]").remove();
				$j("#goodsWeigth").attr("isManualWeighing","");
				//$j("#executeOutbound").addClass("hidden");
				//loadTableData("");
			}
		}
	});
	
	$j("#goodsWeigth").keyup(function(event){
		var isManualWeighing = $j("#goodsWeigth").attr("isManualWeighing");
		var key = event.keyCode;
		if(key != 13 && key != 8 && isManualWeighing != 'true'){
			$j("#goodsWeigth").val("");
		}
	});
	
	
	$j("#wrapStuff").keydown(function(event){
			if(event.keyCode == 13){
				event.preventDefault();
				if ($j("#wrapStuff").val() != null){
					addBarCode();
				}else{
					$j("#wrapStuff").focus();
				}
			}
		}); 
	
	$j("#confirmWeightInput").keydown(function(event){
			var errorArray = validateData();
			//alert("confirmWeightInput  errorArray:  " + errorArray.toString());
			if(event.keyCode == 13){
				if ($j("#confirmWeightInput").val() == BARCODE_CONFIRM){
					$j("#goodsWeigth").val($j("#autoWeigth").val());
					openOutBoundDialog();
					/*
					if(errorArray == null || errorArray.length == 0){
						$j("#executeOutbound").trigger("click");	
					}
					closeDialog1(errObjs);
					*/				
				}else if($j("#confirmWeightInput").val() != "" ) {
					showWeightInputErrDialog("输入重量确认条码不正确，请重新输入.");
					$j("#confirmWeightInput").val("");
					//$j("#confirmWeightInput").focus();
				}else if($j("#confirmValue").val() == "" ) {
					showWeightInputErrDialog("请输入重量确认条码.");
					$j("#confirmWeightInput").val("");
					//$j("#confirmWeightInput").focus();
				}
			}
	});
	
	$j("#goodsWeigth").keydown(function(event){
		if(event.keyCode == 13){
			event.preventDefault();
			
			var value = $j("#goodsWeigth").val();
			if(value == ""){
				//showOutBoundInfo(i18("INPUT_WEIGHT"));
				showErrorInfoDialog(i18("INPUT_WEIGHT"));
				if(whichWidgetIsError("#goodsWeigth")){
					errObjs.push("#goodsWeigth");
				}
				//errObjs.push("#goodsWeigth");
				return;
			}
			if(!/^[0-9]*\.{0,1}[0-9]*$/.test(value)){
				//showOutBoundInfo(i18("NUMBER_RULE"));
				showErrorInfoDialog(i18("NUMBER_RULE"));
				if(whichWidgetIsError("#goodsWeigth")){
					errObjs.push("#goodsWeigth");
				}
				return;
			}
			value = parseFloat(value);
			if(value == 0){
				//showOutBoundInfo(i18("INPUT_WEIGHT"));
				showErrorInfoDialog(i18("INPUT_WEIGHT"));
				if(whichWidgetIsError("#goodsWeigth")){
					errObjs.push("#goodsWeigth");
				}
				return;
			}else if(value > 150){
				//showOutBoundInfo(i18("WEIGHT_LAGGER"));
				showErrorInfoDialog(i18("WEIGHT_LAGGER"));
				if(whichWidgetIsError("#goodsWeigth")){
					errObjs.push("#goodsWeigth");
				}
				return;
			}	
			
			if(loxia.byId("goodsWeigth").state() === true && loxia.byId("goodsWeigth").val() != null){
				//$j("#executeOutbound").trigger("click");
				openOutBoundDialog();
			}else if($j("#goodsWeigth").val() != ""){
				loxia.byId("goodsWeigth").check();
				if(loxia.byId("goodsWeigth").state() === true && loxia.byId("goodsWeigth").val() != null)
					openOutBoundDialog();
					//$j("#executeOutbound").trigger("click");
			}else{
				loxia.byId("goodsWeigth").val($j("#autoWeigth").val());
			}
		}
	});

	
	
	
	$j("#executeOutbound").click(function(){
		//if (stuffBarCode != null && stuffBarCode !='') postData["stuffCode"]=$j.trim($j("#wrapStuff").val());
		/*if(!(value && doExecute(value) == "success")){
			jumbo.showMsg(doExecute($j("#goodsWeigth").val()));
			return ;
		}else{*/			
			/*if($j("#staDiv").attr("trackingNo") == ""){
				jumbo.showMsg(i18("INPUT_TRACKINGNO"));//请输入快递单号
				return ;
			}*/
		
			/*var errors = validateData();
			if(errors.length>0){
				jumbo.showMsg(errors);
				return false;
			}*/
			$j("#showOutBoundMsg").html("");
			//var value = loxia.byId("goodsWeigth").val();
			var value = $j("#goodsWeigth").val();
			var barcode,qty;
			var transNo = $j("#staDiv").attr("trackingNo");
			var postData={};
			$j("#barCode_tab tbody:eq(0) tr").each(function (i,tag){ 
				barcode = $j(tag).find("td:eq(1) input").val();
				qty = $j(tag).find("td:eq(2) input").val();
				postData["saddlines[" + i + "].sku.barCode"] = barcode;
				postData["saddlines[" + i + "].quantity"] =  qty;
			});
			postData["trackingNo"]= transNo;
			postData["weight"]=value;
			//postData["weight"]=value;
			postData["staId"]=$j("#staDiv").attr("staId");
			
			/*var stuffBarCode = $j.trim($j("#wrapStuff").val());
			var postData = {};
			postData["trackingNo"]=$j("#staDiv").attr("trackingNo");
			postData["weight"]=value;
			postData["staId"]=$j("#staDiv").attr("staId");
			if (stuffBarCode != null && stuffBarCode !='') postData["stuffCode"]=$j.trim($j("#wrapStuff").val());*/
			postData["lineNo"]=lineNo;
			var rs = loxia.syncXhrPost(baseUrl + "/outBound.json",postData);
			var t=false;
			var outboundResult = [];
			/***
			 if(rs){
				if(rs["result"] == "success"){
					outboundResult.push(i18("OUTBOUND")+ "<br/>");
				}else if(rs["result"] == "error"){
					if(rs["message"]){
						outboundResult.push(rs["message"]+ "<br/>");
					}else{
						outboundResult.push(i18("ORDER_CANCELED")+ "<br/>");
					}
				}
			}else{
				outboundResult.push(i18("OUTBOUND_ERROR") + "<br/>");
			}
			 */
			if(rs){
				if(rs["result"] == "success"){
					c_success = c_success + 1;
					success_transMap[transNo] = transNo;
					$j("#success").html(c_success);
					if (existsInFailedMap(transNo)){
						c_failed = c_failed - 1;
						$j("#failed").html(c_failed);
					}
					//outboundResult.push(i18("OUTBOUND")+ "<br/>");
					$j("<tr><td>" + $j("#soCode").html() + "</td><td>"+i18("OUTBOUND")+"</td></tr>").appendTo($j("#rsSuccess"));//出库
				}else if(rs["result"] == "error"){
					if (!existsInFailedMap(transNo)){
						c_failed = c_failed + 1;
						failed_transMap[transNo] = transNo;
					}
					$j("#failed").html(c_failed);
					if(rs["message"]){
						outboundResult.push(rs["message"]+ "<br/>");
						$j("<tr><td>" + $j("#soCode").html() + "</td><td>"+rs["message"]+"</td></tr>").appendTo($j("#rsError"));//作业但已取消或含有未出库包裹
					}else{
						outboundResult.push(i18("ORDER_CANCELED")+ "<br/>");
						$j("<tr><td>" + $j("#soCode").html() + "</td><td>"+i18("ORDER_CANCELED")+"</td></tr>").appendTo($j("#rsError"));//作业但已取消或含有未出库包裹
					}
				}
			}else{
				if (!existsInFailedMap(transNo)){
					c_failed = c_failed + 1;
					failed_transMap[transNo] = transNo;
				}
				outboundResult.push(i18("OUTBOUND_ERROR") + "<br/>");
				$j("<tr><td>" + $j("#soCode").html() + "</td><td>"+i18("OUTBOUND_ERROR")+"</td></tr>").appendTo($j("#rsError"));//执行出库异常
				jumbo.showMsg(i18("OUTBOUND_ERROR"));//执行出库异常
			}
			if (outboundResult.length > 0){
				$j("#outbound_result_dialog").dialog("open");
				$j("#showOutBoundMsg").html(outboundResult.toString());
				$j("#confirmResultValue").focus();	
			} 
			$j("#barCode_tab tbody:eq(0) tr").remove();
			clearWeight();
			//widgetFocus("#billsId");		
			/*errObjs = [];
			loxia.initLoxiaWidget($j("#billsId"));
			$j("#billsId").focus();
			 * */
		//}
	});
	
	$j("#billsId").focus();
	
	clearWeight();
	
	restart();
	
	// dialog 1
	$j("#confirmOutBound").click(function(){
		var errorArray = validateData();
		if(errorArray == null || errorArray.length == 0){
			$j("#executeOutbound").trigger("click");	
		}
		$j("#confirmValue").val("");
		$j("#outbound_dialog").dialog("close"); 
		errObjs = [];
		$j("#billsId").focus();
	});
	
	$j("#confirmClose").click(function(){
		// clearData();
		/*$j(errObj).val("")
		$j("#confirmValue").val("");
		$j("#outbound_dialog").dialog("close"); 
		$j(errObj).focus();*/
		//closeDialog1();
		var errorArray = validateData();
		closeDialog1(errObjs);
	});
	
	$j("#confirmValue").keydown(function(event){
	//$j("#confirmValue").keyup(function(event){
		var value = $j("#confirmValue").val();
		var errorArray = validateData();
		if(event.keyCode == 13){
			if (value == BARCODE_CONFIRM){
				if(errorArray == null || errorArray.length == 0){
					$j("#executeOutbound").trigger("click");	
				}
				closeDialog1(errObjs);
				errObjs = [];
				$j("#billsId").focus();
			}else if(value != "" ) {
				$j("#confirmValue").val("");
				$j("#showError").html("输入的确认条码不正确，请重新输入.");
			}else if(value == "" ) {
				$j("#confirmValue").val("");
				$j("#showError").html("请输入确认条码.");
			}
		}
	});
	
	// dialog 2
	$j("#confirmResultClose").click(function(){
		$j("#confirmResultValue").val(""); 
		$j("#outbound_result_dialog").dialog("close"); 
		$j("#billsId").focus();
		$j("#confirmWeightInput").val("");
	});
	$j("#confirmResultValue").keydown(function(event){
//$j("#confirmResultValue").keyup(function(event){
		var value = $j("#confirmResultValue").val();
		if(event.keyCode == 13){
			if (value == BARCODE_CONFIRM){
				$j("#confirmResultValue").val("");
				$j("#outbound_result_dialog").dialog("close");
				$j("#billsId").focus();
				$j("#confirmWeightInput").val("");
			}else if(value != "" ) {
				$j("#confirmResultValue").val("");
				$j("#showOutBoundMsg").html("输入的确认条码不正确，请重新输入.");
			} else if(value == "" ) {
				$j("#confirmResultValue").val("");
				$j("#showOutBoundMsg").html("请输入确认条码.");
			} 
		}
	});
	
	// 错误提示信息  dialog 4
	$j("#confirmErrorInfoClose").click(function(){
		$j("#confirmErrorInfoValue").val(""); 
		$j("#confirmWeightInput").val(""); 
		$j("#show_error_dialog").dialog("close");
		//========================== 
		var isManualWeighing = $j("#goodsWeigth").attr("isManualWeighing");
		if(hasThisWidget("#goodsWeigth")){
			// true  自动称
			if(isManualWeighing != 'true'){
				$j("#confirmWeightInput").focus();	
			}else {
				$j("#goodsWeigth").focus();	
			}
		}else {
			$j(errObjs[0]).focus();		
		}
		// ============
		//$j(errObjs[0]).focus();
		$j(errObjs[0]).val("")
		errObjs.length = 0;
	});
	$j("#confirmErrorInfoValue").keydown(function(event){
		var value = $j("#confirmErrorInfoValue").val();
		if(event.keyCode == 13){
			if (value == BARCODE_CONFIRM){
				$j("#confirmErrorInfoValue").val("");
				$j("#confirmWeightInput").val(""); 
				$j("#show_error_dialog").dialog("close");
				//========================== 
				var isManualWeighing = $j("#goodsWeigth").attr("isManualWeighing");
				if(hasThisWidget("#goodsWeigth")){
					// true  自动称
					if(isManualWeighing != 'true'){
						$j("#confirmWeightInput").focus();	
					}else {
						$j("#goodsWeigth").focus();	
					}
				}else {
					$j(errObjs[0]).focus();		
				}
				$j(errObjs[0]).val("")				
				errObjs.length = 0;
			}else if(value != "" ) {
				$j("#confirmErrorInfoValue").val("");
				$j("#showErrorInfo").html("输入的确认条码不正确，请重新输入.");
			} else if(value == "" ) {
				$j("#confirmErrorInfoValue").val("");
				$j("#showErrorInfo").html("请输入确认条码.");
			} 
		}
	});
	
	// dialog 3 自动称重 确认条码
	$j("#confirmWeightInfoClose").click(function(){
		$j("#confirmWeightInfoValue").val(""); 
		$j("#weight_input_dialog").dialog("close"); 
		$j("#confirmWeightInput").val("");
		$j("#confirmWeightInput").focus();
	});
	$j("#confirmWeightInfoValue").keydown(function(event){
		var value = $j("#confirmWeightInfoValue").val();
		if(event.keyCode == 13){
			if (value == BARCODE_CONFIRM){
				$j("#confirmWeightInfoValue").val(""); 
				$j("#weight_input_dialog").dialog("close"); 
				$j("#confirmWeightInput").val("");
				$j("#confirmWeightInput").focus();
			}else if(value != "" ) {
				$j("#confirmWeightInfoValue").val("");
				$j("#showWeightInfo").html("输入的确认条码不正确，请重新输入.");
			}else if(value == "" ) {
				$j("#confirmWeightInfoValue").val("");
				$j("#showWeightInfo").html("请输入确认条码.");
			}
		}
	});
	
	
	$j("#restWeight").click(function(){
		restart();
	});
	
	var date = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getWarehouseInfo.json");
	if(date.isMqInvoice){
		lineNo = jumbo.getAssemblyLineNo();
	}
	
});


function existsInFailedMap(code){
	var flag = false;
	for(var index in failed_transMap){
		if (failed_transMap[code]){
			flag = true;
			break;
		}
	}
	return flag;
}

function hasThisWidget(widget){
	var flag = false;
	for(var index in errObjs){
		if (errObjs[index] == widget){flag = true; break; }
	}
	return flag;
}

function openOutBoundDialog(){
	$j("#showError").html("");
	$j("#showOutBoundTable").removeClass("hidden");
	
	$j("#outbound_dialog").dialog("open");
	$j("#confirmValue").focus();
	
	var transportNo = $j("#billsId").val();
	var weight_ = $j("#autoWeigth").val() == "" ? $j("#goodsWeigth").val():$j("#autoWeigth").val();
	$j("#transportNo").html(transportNo);
	$j("#weight_").html(weight_);
	$j("#transportName").html($j("#lpcode").html());
	$j("#relativeCode").html($j("#soCode").html());
	$j("#shopName").html($j("#shopId").html());
	
	var errorArray = validateData();
	if(errorArray != null && errorArray.length > 0){
		$j("#confirmOutBound").addClass("hidden");
		$j("#confirmClose").removeClass("hidden");
		$j("#showOutBoundTable").addClass("hidden");
		$j("#showError").html(errorArray.toString());
		return;
	}else {
		$j("#confirmOutBound").removeClass("hidden");
		$j("#showOutBoundTable").removeClass("hidden");
		$j("#confirmClose").addClass("hidden");
	}
}

function showOutBoundInfo(msg){
	$j("#showError").html("");
	$j("#showOutBoundTable").addClass("hidden");
	$j("#outbound_dialog").dialog("open");
	$j("#showError").html(msg);
	$j("#confirmOutBound").addClass("hidden");
	$j("#confirmClose").removeClass("hidden");
	$j("#confirmValue").focus();
}

// show_error_dialog 显示错误信息dialog
function showErrorInfoDialog(msg){
	$j("#showErrorInfo").html("");
	$j("#show_error_dialog").dialog("open");
	$j("#showErrorInfo").html(msg);
	$j("#confirmErrorInfoValue").focus();
}
// confirmWeightInput
function showWeightInputErrDialog(msg){
	$j("#showWeightInfo").html("");
	$j("#weight_input_dialog").dialog("open");
	$j("#showWeightInfo").html(msg);
	$j("#confirmWeightInfoValue").focus();
}

function clearData(){
	$j("#billsId").val("");
	$j("#wrapStuff").val("");
	$j("#goodsWeigth").val("");
}

function clearDataExcludeBills(){
	$j("#wrapStuff").val("");
	$j("#goodsWeigth").val("");
}
function closeDialog1(objs){
	$j("#confirmValue").val("");
	$j("#confirmWeightInput").val(""); 
	$j("#outbound_dialog").dialog("close");
	$j(errObjs[0]).val("");
	//========================== 
	var isManualWeighing = $j("#goodsWeigth").attr("isManualWeighing");
	if(hasThisWidget("#goodsWeigth")){
		// true  自动称
		if(isManualWeighing != 'true'){
			$j("#confirmWeightInput").focus();	
		}else {
			$j("#goodsWeigth").focus();	
		}
	}else {
		$j(errObjs[0]).focus();		
	}
	errObjs.length = 0;
}


function validateData(){
	var errors = [], canAdd = true;
	if(rswh && rswh.warehouse){
		if (rswh.warehouse.isNeedWrapStuff){
			if(!$j("#barCode_tab tbody:eq(0) tr").length > 0){
				if(whichWidgetIsError("#wrapStuff")){
					errObjs.push("#wrapStuff");
				}
				errors.push("请输入包材编码<br/>");
			}
		}
	}
	var weight = $j.trim($j("#goodsWeigth").val());
	var msg = doExecute($j("#goodsWeigth").val());
	if (msg && msg != 'success'){
		errors.push(msg);
		if(whichWidgetIsError("#goodsWeigth")){
			errObjs.push("#goodsWeigth");
		}
	}
	if(!$j.trim($j("#billsId").val())) {
		if(whichWidgetIsError("#billsId")){
			errObjs.push("#billsId");
		}
		errors.push("请输入快递单号<br/>");
	}
	return errors;
}

function whichWidgetIsError(widget){
	var canAdd = true;
	for(var index in errObjs){
		if (errObjs[index] == widget){canAdd = false; break; }
	}
	return canAdd;
}
 