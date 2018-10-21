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

function addBarCodeFanht(){
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
//供应商编号 fanht
var selTrans;
//已出库的快递单主键，出库交接用
var packageIds = new Array();
//已出库的所有快递单号，出库交接检查用
var transNoList = new Array();
//记录未交接单
handoverCount = 0;



$j(document).ready(function (){	
	$j("#outbound_dialog").dialog({title: "出库确认信息", modal:true, autoOpen: false, width: 700, height: 300});
	$j("#outbound_result_dialog").dialog({title: "出库结果信息", modal:true, autoOpen: false, width: 600, height: 250});
	$j("#show_error_dialog").dialog({title: "出库信息", modal:true, autoOpen: false, width: 600, height: 300});
	$j("#weight_input_dialog").dialog({title: "重量确认", modal:true, autoOpen: false, width: 500, height: 200});
	$j("#autoWeigth").css("color","black");
	$j("button[action=add]").addClass("hidden");
	var baseUrl = $j("body").attr("contextpath");
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
	$j("#wrapStuff").keydown(function(event){
		if(event.keyCode == 13){
			event.preventDefault();
			if ($j("#wrapStuff").val() != null){
				addBarCodeFanht();
			}else{
				$j("#wrapStuff").focus();
			}
		}
	}); 

	$j("#confirmOutBound").click(function(){
		//检查快递单号
		
		var ttrans = $j("#tbl-express-order tr:gt(0)");
		if(ttrans.length==0){
			$j("#flag0").val("express_order");
			//errorTip(i18("INPUT_TRACKINGNO"));
			errorTipOK(i18("INPUT_TRACKINGNO"));
			return false;
		}
		var transNo = $j("#staTransNo").html();
		var data={};
		if(transNo==""){
			$j.each(ttrans,function(i, item) {
				 var id =  item.id;
				  data["packageInfos[" + i + "].trackingNo"] = id;
			});
		}else{
			var ids = $j("#tbl-trank-list tr:gt(0)");
			data["sta.trackingNo"]=$j("#trans_no").val();
			$j.each(ids,function(i, item) {
				 var id =  item.id;
				  data["packageInfos[" + i + "].trackingNo"] = id;
			});
		}
		$j("#barCode_tab tbody:eq(0) tr").each(function (i,tag){ 
			barcode = $j(tag).find("td:eq(1) input").val();
			qty = $j(tag).find("td:eq(2) input").val();
			data["saddlines[" + i + "].sku.barCode"] = barcode;
			data["saddlines[" + i + "].quantity"] =  qty;
		});
	    var staId = $j("#staId").val();
	    data["sta.id"]=staId;
	    data["sta.weight"] = $j("#goodsWeigth").val();
	    var datax = $j("#tbl-billView").jqGrid('getRowData');
	    $j.each(datax,function(i, item) {
	    	//完成数量
	    	data["staLineList[" + i + "].completeQuantity"] = item.quantity;
	    	//明细主键
	    	data["staLineList[" + i + "].id"] = item.id;
	    });
	    data["lineNo"] = lineNo;
	    //SN号数据提交
	    for(var i = 0 ; i < snArray.length;i++){
	    	var sn = snArray[i].split(",");
			data["snlist["+ i +"]"] = sn[1];
	    }
	    try{
			var pNo = $j.trim($j("#verifyCode").html());
			var staCode = $j.trim($j("#staCode").html());
			var timestamp=new Date().getTime();
			creatZipJs(dateValue + "/" + pNo + "/" + staCode,timestamp);//打包图片
			updateHttpJs(dateValue + "/" + pNo + "/" + staCode,timestamp);//发送图片zip http服务器
			loxia.syncXhr($j("body").attr("contextpath") + "/creatStvCheckImg.json",{"staCode":staCode,"fileUrl":"/"+dateValue + "/" + pNo + "/" + staCode,"fileName":fileName});//保存STV图片路径
		} catch (e) {
		};
	    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/staSortingCheckAndsalesStaOutBoundHand.json",data);
		if(rs.result=='success'){
			$j("#outbound_dialog").dialog("close");
			clearWeight();
			 window.location.reload();
		}else {
			$j("#flag0").val("barCode1");
			var msg = rs.msg;
			if('' != msg && msg.indexOf("无法流水开票") > 0){
				isNotLineNo='true';
			}
			errorTipOK(rs.msg);
		}
		
	});
	$j("#goodsWeigth").keyup(function(event){
		var isManualWeighing = $j("#goodsWeigth").attr("isManualWeighing");
		var key = event.keyCode;
		if(key != 13 && key != 8 && isManualWeighing != 'true'){
			$j("#goodsWeigth").val("");
		}
	});
	

	$j("#confirmWeightInput").keydown(function(event){
			if(event.keyCode == 13){
				if ($j("#confirmWeightInput").val() == BARCODE_CONFIRM){
					$j("#goodsWeigth").val($j("#autoWeigth").val());
					openOutBoundDialog();
				}else if($j("#confirmWeightInput").val() != "" ) {
					showWeightInputErrDialog("输入重量确认条码不正确，请重新输入.");
					$j("#confirmWeightInput").val("");
				}else if($j("#confirmValue").val() == "" ) {
					showWeightInputErrDialog("请输入重量确认条码.");
					$j("#confirmWeightInput").val("");
				}
			}
	});
	
	$j("#goodsWeigth").keydown(function(event){
		if(event.keyCode == 13){
				var flag1 = true;
				var datax = $j("#tbl-billView").jqGrid('getRowData');
				$j.each(datax,function(i, item) {
					var n1= parseInt(item.quantity);
					var cq = item.completeQuantity;
					if(cq==''||cq==null){
						 cq='0';
					}
					var n2= parseInt(cq);
					if(n2<n1){
						initCheck();
						var msg = item.skuName+" 已执行量小于计划执行量，请继续核对！";
						errorTipOK(i18(msg));
						flag1 = false;
						return;
					}
					if(n2>n1){
						initCheck();
						var msg = item.skuName+" 已执行量大于计划执行量，请重新核对！";
						errorTipOK(i18(msg));
						//核对数量归零
						item["completeQuantity"] = 0;
						$j("#tbl-billView").jqGrid('setRowData',item.id,item);
						loxia.initContext($j("#tbl-billView"));
						flag1 = false;
						return;
					}
				});	
				if(!flag1){
					return ;
				}
			event.preventDefault();
			var value = $j("#goodsWeigth").val();
			if(value == ""){
				showErrorInfoDialog(i18("INPUT_WEIGHT"));
				if(whichWidgetIsError("#goodsWeigth")){
					errObjs.push("#goodsWeigth");
				}
				return;
			}
			if(!/^[0-9]*\.{0,1}[0-9]*$/.test(value)){
				showErrorInfoDialog(i18("NUMBER_RULE"));
				if(whichWidgetIsError("#goodsWeigth")){
					errObjs.push("#goodsWeigth");
				}
				return;
			}
			value = parseFloat(value);
			if(value == 0){
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
				openOutBoundDialog();
			}else if($j("#goodsWeigth").val() != ""){
				loxia.byId("goodsWeigth").check();
				if(loxia.byId("goodsWeigth").state() === true && loxia.byId("goodsWeigth").val() != null)
					openOutBoundDialog();
			}else{
				loxia.byId("goodsWeigth").val($j("#autoWeigth").val());
			}
		}
	});

	
	$j("#executeOutbound").click(function(){
			$j("#showOutBoundMsg").html("");
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
			postData["staId"]=$j("#staDiv").attr("staId");
			postData["lineNo"]=lineNo;
			//出库&插入中间表
			var rs = loxia.syncXhrPost(baseUrl + "/outBoundHand.json",postData);
			var t=false;
			var outboundResult = [];
			if(rs){
				//每次出库成功，就记录快递信息主键 fanht
				if(rs["packageId"]!=null){
					packageIds.push(rs["packageId"]);
					transNoList.push(transNo);
					//显示未交接单数
					handoverCount++;
					$j("#handoverCount").html(handoverCount);
				}
				if(rs["result"] == "success"){
					$j("#confirmInputSpan").removeClass("hidden");
					$j("#confirmResultValue").removeClass("hidden");
					$j("#confirmResultValue").val("");
					c_success = c_success + 1;
					success_transMap[transNo] = transNo;
					$j("#success").html(c_success);
					if (existsInFailedMap(transNo)){
						c_failed = c_failed - 1;
						$j("#failed").html(c_failed);
					}
					$j("<tr><td>" + $j("#soCode").html() + "</td><td>"+i18("OUTBOUND")+"</td></tr>").appendTo($j("#rsSuccess"));//出库
				}else if(rs["result"] == "error"){
					$j("#confirmInputSpan").addClass("hidden");
					$j("#confirmResultValue").addClass("hidden");
					$j("#confirmResultValue").val("--");
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
				showErrorInfoDialog(i18("OUTBOUND_ERROR"));
				
			}
			if (outboundResult.length > 0){
				$j("#outbound_result_dialog").dialog("open");
				$j("#showOutBoundMsg").html(outboundResult.toString());
				$j("#confirmResultValue").focus();	
			} 
			$j("#barCode_tab tbody:eq(0) tr").remove();
			clearWeight();
	});
	
	//供应商选择 fanht
	$j("#selTrans").focus();
	
	clearWeight();
	
	restart();
	
	// dialog 1

	$j("#confirmValue").keydown(function(event){
		var value = $j("#confirmValue").val();
		var errorArray = validateData();
		if(event.keyCode == 13){
			if (value == BARCODE_CONFIRM){
				if(errorArray == null || errorArray.length == 0){
					$j("#confirmOutBound").trigger("click");	
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
		$j(errObjs[0]).val("");
		errObjs.length = 0;
	});
	$j("#confirmErrorInfoValue").keydown(function(event){
		var value = $j("#confirmErrorInfoValue").val();
		if(event.keyCode == 13){
			if (value == BARCODE_CONFIRM){
				$j("#confirmErrorInfoValue").val("");
				$j("#confirmWeightInput").val(""); 
				$j("#show_error_dialog").dialog("close");
				$j("#billsId").focus();	
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
				$j(errObjs[0]).val("");			
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
	
	var transportNo = $j("#trans_no").val();
	var weight_ = $j("#autoWeigth").val() == "" ? $j("#goodsWeigth").val():$j("#autoWeigth").val();
	$j("#transportNo").html(transportNo);
	$j("#weight_").html(weight_);
	$j("#transportName").html($j("#staDelivery").html());
	$j("#relativeCode").html($j("#staRefCode").html());
	$j("#shopName").html($j("#shopId").val());
	
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
	$j("#confirmErrorInfoValue").val("");
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
	if(!$j.trim($j("#trans_no").val())) {
		if(whichWidgetIsError("#trans_no")){
			errObjs.push("#trans_no");
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
 