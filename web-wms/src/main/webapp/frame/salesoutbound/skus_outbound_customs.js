var BASE_URL;				//基础URL
var KEY_CONFIRM = "OK";		//确认条码
//var stastatus;			//作业单状态列表;
var isWeight = true;		//是否称重
var isHo = true;			//是否交接
var isCheck = false;		//是否复核
var isHaveShortButton=false;//是否可以操作缺货
var isMate = false;			//是否扫描耗材
var isMustCM = false;       //是否强制使用推荐耗材
var isManualWeight = false;	//是否手动称重
var toCheckData;			//待核对数据
var checkedData;			//已核对数据
var curStep = "";			//当前步骤光标
var staId;					//核对当前作业单ID
var isSpPackige = false;	//当前是否核对中称重
var errMsg = "";            //全局错误信息
var nowOrder = null;        //记录当前核对的作业单信息
var userName = null;        //记录当前用户名
var dateValue = null;       //当前时间
var ouName = null;          //仓库名称
var maxHoList = 50;			//最大未交接单据数
var msgType = 0;            //辅助数字，用来确认框OK之后需要操作的事情（以不同类型区分弹框的时机决定做不同的事情)  1:单件核对  2:多件核对 3:拆包称重 4：最终出库 
var isTurn = false;			//是否需要跳转，因为不是所有的异常都要跳转，单号扫描错误等允许重新扫描
var currentWeight = 0;      //缓存当前重量
var canPartlyOutbound = false;//是否可以部分出库
var toSpecialBrand=false;//特殊显示跳转标志
toSpecialBrand2=false;//特殊显示跳转标志2
var originMap=null;
var isSingleOrigin=false;
var lastSkuCode="";
var skuOrigin="";
$j.extend(loxia.regional['zh-CN'], {
});

/**********************************************************************/
/** 组件方法 														  */
/**********************************************************************/
/**
 * 隐藏所有子元素
 */
function hiddenAll(){
	$j("div[step]").addClass("hidden");
	$j("div[type=error]").addClass("hidden");
}

/**
 * 隐藏块
 * @param pane jquery对象
 */
function hiddenPane(pane) {
	pane.addClass("hidden");
}

/**
 * 显示块
 * @param pane jquery对象
 */
function showPane(pane) {
	pane.removeClass("hidden");
}


/**
 * 打开错误对话框
 * @param msg	错误消息
 */
function showErrorMsg(msg){
	if(msg){
		var contentStr = "";
		if(loxia.isString(msg)){
			contentStr = msg;
		}else if($j.isArray(msg)){
			for(var i=0, c;(c=msg[i]);i++)
				contentStr += c;
		}
		msg = contentStr;
	}
	$j("#errorMsg").html("").html(msg);
	errMsg = "";
	$j("#dialogMsg").dialog("open");
	$j("#txtErrorOk").focus();
}

/**
 * 打开消息对话框
 * @param msg	消息
 */
function showInfoMsg(msg){
	$j("#infoMsg").html(msg);
	$j("#dialogInfoMsg").dialog("open");
	$j("#txtInfoOk").focus();
}



/**
 * 光标聚焦输入框元素，所有文本输入框定义index数据的值
 * @param idx	步骤
 * 		
 */
function nextFource(idx){
	$j("input[index=" + idx+ "]").val("");
	$j("input[index=" + idx+ "]").focus();
	setTimeout('getFourceId("'+idx+'");',300);
	$("input[index=" + idx+ "]").blur();
	$j("input[index=" + idx+ "]").focus();
}

function getFourceId(idx){
	 var act = window.document.activeElement;
	 $("input[index=" + idx+ "]").blur();
	 $j("input[index=" + idx+ "]").focus();
}

/**
 * 初始化交接清单数据
 */
function initHoList(){
	$j("#tbHo tr:gt(0)").remove();
	var hl = loxia.syncXhr(BASE_URL+"/getneedhandoverordersummary.json");
	if(hl){
		var sum =0;
		for(var i=0;i<hl.length;i++){
			var tr  = "<tr>";
			tr += "<td><input type=\"checkbox\" /></td>";
			tr += "<td><label name=\"labLpcode\" class=\"font-M\">"+hl[i].lpCode+"</label></td>";
			tr += "<td><label name=\"labToHoQty\" class=\"font-M\">"+hl[i].id+"</label></td>";
			tr += "</tr>";
			$j("#tbHo").append($j(tr));
			sum+=hl[i].id;
		}
		$j("#labAllHo").html(sum);
		if(sum>=maxHoList){
			$j("div[step]").addClass("hidden");
			$j("#step3").removeClass("hidden");
		}
	}
}

/**
 * 清空拆包运单号
 */
function clearSplitPg(){
	$j("#tabTrans tr:gt(0)").remove();
	hiddenPane($j("#tabTrans"));
}

/**
 * 初始化界面流程
 */
function init(){
	//初始化系统参数,根据用户组确认该界面能够操作的流程深度，根据仓库基础信息确认是否计算耗材，是否允许手动称重等
	initParam();
	//清空数据
	clearData();
	//隐藏全部
	hiddenAll();
	restart();
	if(isCheck){
		//判断是否复核,复核进入1_1
		step1_1();
	}else if(isWeight){
		//判断是否称重,称重进入2_1
		step2_1();
	}
	//判断是否交接
	if(isHo){
		showPane($j("#step3"));
	}else{
		hiddenPane($j("#step3"));
	}
}
/**
 * 初始化系统的这种配置参数
 */
function initParam(){
	//1、初始化照相相关参数
	userName = $j("#userinfo", window.parent.document).attr("lgname");
	dateValue = $j("#userinfo", window.parent.document).attr("sysdate");
	ouName = $j("#userinfo", window.parent.document).attr("ouName");
	//2、流程深度初始化
	//3、仓库参数初始化
	var rswh = loxia.syncXhrPost(loxia.getTimeUrl(BASE_URL + "/findwarehousebaseinfo.json"));
	if(rswh && rswh.warehouse){
		if (rswh.warehouse.isNeedWrapStuff){
			isMate = true;
		}
		if (rswh.warehouse.isManualWeighing){
			isManualWeight = true;
		}
		if (rswh.warehouse.isCheckConsumptiveMaterial){
			isMustCM = true;
		}
		if(rswh.warehouse.handLimit!=null){
			maxHoList = rswh.warehouse.handLimit;
		}
	}else{
		showErrorMsg("系统初始化错误!");
		return false;
	}
}
/**
 * 清空页面所有数据
 */
function clearData(){
	isSpPackige = false;
	staId = "";
	$j("#labMate").html("");
	$j("#labPicking").html("");
	$j("#labSlipCode").html("");
	$j("#labSlipCode1").html("");
	$j("#labPgIndex").html("");
	$j("#labTransNo").html("");
	$j("#labLpcode").html("");
	$j("#labWeight").html("");
	//$j("#labAllHo").html("0");
	$j("#labScanBarcode").html("");
	//清空表格
	$j("#to_check_list" ).clearGridData(); 
	$j("#checked_list" ).clearGridData(); 
	
	$j("#labWeightSlipCode").html("");
	$j("#labWeightTransNo").html("");
	$j("#labWeightLpcode").html("");
	$j("#labMate25").html("");
	$j("#txtScanMate").val("");
	$j("#txtScanSku").val("");
	$j("#txtScanSn").val("");
	$j("#txtInputWeight").val("");
	$j("#txtScanWeight").val("");
	$j("#isHaoCai").val("");
	//清空拆包裹运单号
	clearSplitPg();
	toCheckData = {};
	checkedData = {};
}

/**
 * 检查重量是否合理
 * @param w
 */
function checkWeight(w){
	var w = w.trim();
	var fw = parseFloat(w);
	if(isNaN(fw)){
		showErrorMsg("请输入正确重量：" + w);
		return false;
	}else if(fw <= 0 || fw >= 80){
		showErrorMsg("重量超范围：" + fw);
		return false;
	}
	return true;
}
/**********************************************************************/
/** 页面流程 														  */
/**********************************************************************/

/**
 * 进入[复核]第1.1步处理场景
 * 扫描批次OR单据号
 */
function step1_1(){
	hiddenAll();
	showPane($j("#step1"));
	nextFource("1_1");
	curStep = "1_1";
}

/**
 * 进入[复核]第1.2步处理场景
 * 扫描商品
 */
function step1_2(){
	$j("#step1_2 p").addClass("hidden");
	$j("#P_1").removeClass("hidden");
	showPane($j("#step1_2"));
	$j("#txtScanSku").val("");
	$j("#txtScanSn").val("");
	nextFource("1_2");
	curStep = "1_2";
}

/**
 * 进入[复核]操作
 * 扫描SN号
 * @param skuInfo 			当前要赋值的内容
 * @param hiddenElement 	要隐藏的元素
 * @param valueElement 		当前要赋值的对象
 * @param showElement 		要显示的元素
 * @param nextFocus 		光标定位位置
 */
function step1_3(skuInfo,hiddenElement,valueElement,showElement,nextFocus){
	if(skuInfo!=null){
		$j(valueElement).html(skuInfo);
	}
	if(hiddenElement!=null){
		$j(hiddenElement).addClass("hidden");
	}
	if(showElement!=null){
		$j(showElement).removeClass("hidden");
	}
	if(nextFocus!=null){
		nextFource(nextFocus);
		curStep = nextFocus;
	}
}
/**
 * 进入[复核]第1.4步处理场景
 * 扫描运单号
 */
function step1_4(){
	hiddenPane($j("#step1_2,#step1_3"));
	showPane($j("#step1_4"));
	nextFource("1_4");
	curStep = "1_4";
}

/**
 * 进入[称重]第2.1步处理场景
 * 扫描运单号
 */
function step2_1(){
	hiddenPane($j("#step1"));
	showPane($j("#step2"));
	nextFource("2_1");
	curStep = "2_1";
}

/**
 * 进入[称重]第2.2,进入称重界面
 * 显示数据，进入下一步操作
 */
function step2_2(){
	hiddenAll();
	showPane($j("#step2_1"));
	$j("#step2_4 div").addClass("hidden");
	showPane($j("#step2_4"));
	if(isMate){
		showPane($j("#step2_4_0"));
		nextFource("2_3");
		curStep = "2_3";
	}else{
		//判断是否允许手动称重
		if(isManualWeight){
			step1_3(null,null,null,"#step2_4_2","2_4_2");
		}else{
			step1_3(null,null,null,"#step2_4_5","2_4_5");
		}
	}
}


/**
 * 进入[称重]第2.4,确认重量信息
 */
function step2_4(mateBarcode){
	if(isMate){
		if(mateBarcode){
			step1_3(mateBarcode,"#step2_4_0","#labMate","#step2_4_1",null);
		}
	}
	//是否允许手动称重
	if(isManualWeight){
		step1_3(null,null,null,"#step2_4_2","2_4_2");
	}else{
		step1_3(null,null,null,"#step2_4_5","2_4_5");
	}
}

function step2_5(weight){
	if(checkWeight(weight)){
		outbound(weight);
	}else{
		return false;
	}
}
/**
 * 校验扫描的条码是否是耗材
 * @param mate
 */
function checkIsCm(mate){
	var result = loxia.syncXhrPost(loxia.getTimeUrl(BASE_URL+"/getSkuTypeByBarCode.json?barCode="+mate));
	if (!result || result.value != 1){
		$j("#txtScanMate").val("");
		showErrorMsg("该包材不存在或为非耗材!");
		return false;
	}
	return true;
}
/**
 * 打印运单号
 * @param staId	作业单ID
 * @param packId 新增运单ID
 */
function printTransNo(staId,packId){
	if((staId == null || staId == "") && (packId == null || packId == "")){
		//playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
		showErrorMsg("打印失败：系统异常！");
		return false;
	}else{
		loxia.lockPage();
		jumbo.showMsg("面单打印中，请等待...");
		var url = $j("body").attr("contextpath") + "/printSingleDeliveryMode2Out.json?sta.id="+staId+"&trankCode="+packId;
		var result = printBZ(loxia.encodeUrl(url),true);
		if(typeof(result)!="undefined"){
			var i=0;
			while(i<3&&result=="ERROR"){
				result = printBZ(loxia.encodeUrl(url),true);
				i++;
			}
		}
		loxia.unlockPage();
	}
}



/**********************************************************************/
/** 业务方法 														  */
/**********************************************************************/
/**
 * 封装表格数据，如果传递行标识ID则只封装该行数据
 * @param checkTableData action反馈的json数据模型
 * @param lineId	行号唯一标识ID
 */
function formateTableData(checkTableData,lineId){
	var data = [];
	var idx = 0;
	for(var i=0;i<checkTableData.orders.length;i++){
		var order = checkTableData.orders[i];
		for(var j=0;j<order.lines.length;j++){
			var lineData = {};				//分装后行数据
			var line = order.lines[j];
			//如果指定行号则其余行数据均跳过
			if(lineId && lineId != line.uniqueId ){
				continue;
			}
			//复制数据
			lineData.uniqueId = line.uniqueId;
			lineData.id = line.uniqueId;
			lineData.staCode = order.staCode;
			lineData.pgIndex = order.pgIndex;
			lineData.staCode = order.staCode;
			lineData.orderCode = order.orderCode;
			lineData.status = order.status;
			lineData.owner = order.owner;
			lineData.transNo = order.transNo;
			lineData.lpcode = order.lpcode;
			lineData.qty = line.qty;
			lineData.isGift=line.isGift;//是否是特殊处理
			lineData.cQty = line.cQty;
			lineData.skuName = line.sku.name;
			lineData.barCode = line.sku.barcode;
			lineData.skuCode = line.sku.code;
			data[idx] = lineData;
			idx++;
		}
	}
	//指定行则返回1行数据
	if(lineId){
		return data[0];
	}else{
		return data;
	}
	
}
/**
 * 核对扫描单据，进入商品核对界面 
 * @param orderCode	单据号
 */
function initStep1_2(orderCode){
	$j("#btnPartly").addClass("hidden");
	if(orderCode.trim() == ""){
		showErrorMsg("请扫描单据号");
		return false;
	}
	/*
	 * 根据单号校验数据有效性,本界面目前可以操作单件、多件、团购、套装组合
	 * 1、单件可以扫描配货清单号、周转箱号
	 * 2、多件、团购、套装组合必须扫描作业单号
	 * 该方法成功之后需要反馈单据匹配类型
	 */
	var typeFlag = 0;
	var checkOrder = loxia.syncXhr(BASE_URL+"/checkordercode.json",{"orderCode":orderCode});
	if(checkOrder&&checkOrder.result=="success"){
		typeFlag = checkOrder.typeFlag;
	}else{
		showErrorMsg("扫描的单号不正确！（单件：周转箱/波次号 多件：订单号）");
		return false;
	}
	//加载待核对数据
	var orderList=loxia.syncXhr(BASE_URL + "/getcheckinfo.json",{"orderCode":orderCode,"typeFlag":typeFlag});
	//验证数据是否加载成功
	if(orderList && orderList.plId){
		//originMap=orderList.originMap;
		originMap=loxia.syncXhr($j("body").attr("contextpath") + "/json/findSkuOriginByPlId.json",{"plId":orderList.plId});
		if(originMap!=null){
			originMap=$j.parseJSON(originMap.originMap);
		}
		var isHaveReportMissing = orderList.isHaveReportMissing=="null"?false:orderList.isHaveReportMissing;
		if(isHaveReportMissing&&canPartlyOutbound){
			$j("#btnPartly").removeClass("hidden");
		}
		hiddenAll();
		clearData();
		showPane($j("#step1_1"));
		step1_2();
		toCheckData = orderList;
		$j("#labPicking").html(orderList["plCode"]);
		//初始化表格
		var tableData = formateTableData(orderList);
		for(var i = 0;i<tableData.length;i++){
			$j("#to_check_list").jqGrid('addRowData',tableData[i].uniqueId,tableData[i]);
		}
	}else{
		showErrorMsg("单据信息不存在：" + orderCode);
	}
}


function initStep2_2(transNo){
	if(transNo.trim() == ""){
		showErrorMsg("请扫描运单号");
		return false;
	}
	$j("#isHaoCai").val("");
	//显示数据
	var order=loxia.syncXhr(BASE_URL + "/gettransweightinfo.json",{"transNo":transNo,"isSpPacking":isSpPackige});
	if(order && order.pgId){
		//加载数据
		$j("#labWeightSlipCode").html(order.orderCode).attr("data",order.staId);
		$j("#labWeightTransNo").html(order.transNo).attr("data",(order.barCode==null?"":order.barCode));
		$j("#labWeightLpcode").html(order.lpcode).attr("data",order.pgId);
		if(order.isPreSale =="1"){
					$j("#dialog_is_pre").show();
					$j("#dialog_is_pre2").val("1");
				}else{
					$j("#dialog_is_pre").hide();
					$j("#dialog_is_pre2").val("0");
				}
		//加载耗材
		$j("#isHaoCai").val(order.isHaoCai);
		step2_2();
	}else{
		showErrorMsg("运单号未找到：" + transNo);
		return;
	}
}


/**
 * 出库后增加待交接数量
 * @lpcode 物流商
 */
function addOutboundHoQty(lpcode){
	var isPre=$j("#dialog_is_pre2").val();
	//alert(isPre);
	if("1" == isPre){//是预售
	}else{
	var isAdd = false;
	var sum = 1;
	$j("#tabTrans tr:gt(0)").each(function(idx,tag){
		var labels = $j(tag).find("label");
		if($j(labels[0]).html()!="--"){
			sum++;
		}
	});
	//查找表格中是否存在物流商，存在则增加
	$j("#tbHo tr:gt(0)").each(function(){
		var tdLpcode = $j(this).find("label[name=labLpcode]").html();
		if(tdLpcode == lpcode){
			var qty = $j(this).find("label[name=labToHoQty]").html();
			$j(this).find("label[name=labToHoQty]").html(parseInt(qty) + sum);
			isAdd = true;
		}
	});
	//未找到，添加新行
	if(!isAdd){
		var tr  = "<tr>";
			tr += "<td><input type=\"checkbox\" /></td>";
			tr += "<td><label name=\"labLpcode\" class=\"font-M\">"+lpcode+"</label></td>";
			tr += "<td><label name=\"labToHoQty\" class=\"font-M\">"+sum+"</label></td>";
			tr += "</tr>";
		$j("#tbHo").append($j(tr));
	}
	//总数增加1
	var tqty = parseInt($j("#labAllHo").html());
	tqty=tqty+sum;
	$j("#labAllHo").html(tqty);
	}
}


/**
 * 待查数据中查询商品
 * @param line	行数据
 * @param barcode	商品条码
 * @param sn	
 * @returns {Number}	-1:异常数据;0:未找到;1:找到
 */
function checkScanSkuFinsSku(line,barcode,sn){
	for(var k=0;k<line.sku.barcodes.length;k++){
		var bc = line.sku.barcodes[k];
		if(barcode == bc){
			//检测到商品
			if(line.sku.isSn && sn == ""){
				return -1;
			}else{
				if(line.sku.isSn && sn!=""){
					//检验当前扫入的SN是否存在已经核对的列表内，如果存在则抛出异常
					var flag = false;
					for(var s=0;s<line.sns.length;s++){
						if(sn==line.sns[s]){
							flag = true;
							break;
						}
					}
					if(flag){
						errMsg = "该SN号该单已经扫描过，请不要重复扫描";
						return -1;
					}
					//此处校验当前商品和SN号，因为商品条码不能唯一确认一个SKU，所有此处传递uniqueId(即staLine id)后台可以通过此id 匹配SKUID 唯一确定一个Sku
					var snStatus=loxia.syncXhr(BASE_URL + "/checksnstatus.json",{"barCode":barcode,"sn":sn,"uniqueId":line.uniqueId});
					if(snStatus&&snStatus.result=="success"){
						line.sns.push(sn);
					}else{
						errMsg = snStatus.msg;
						return -1;
					}
				}
				//找到SKU
				return 1;
			}
		}
	}
	//未找到
	return 0;
}

/**
 * 特殊处理显示
 * @param id	行ID
 */

function disposeSpecialShow(id){
	var rowData = $j("#to_check_list").jqGrid("getRowData",id);
	if("是"==rowData.isGift){
		var postData = {};
		postData["staLineId"]=id;
	    var	rs = loxia.syncXhr($j("body").attr("contextpath") + "/findstagiftlistline.json",postData);
		
	    if(rs && rs["msg"]=="yes"){
			$j("#gift_memo").html(rs["giftLine"]["memo"]);
			code128();
	    }else{
	    	showErrorMsg("特殊处理显示失败中间表无数据：" + rowData.barCode);
			return false;
	    }
			$j("#dialog_gift").dialog("open");
		}
			return true;
}


/**
 * 待核对表格指定行商品数量，如果数量为0则删除行
 * @param id	行ID
 * @param qty 数量
 */
function updateToCheckTableLineSkuQty(id,qty){
	var rowData = $j("#to_check_list").jqGrid("getRowData",id);
	if(qty <= 0){
		disposeSpecialShow(id);//特殊处理
		//删除行
		$j("#to_check_list").jqGrid("delRowData", id);    
	}else{
		//修改数量
		rowData.qty = qty;
		 $j("#to_check_list").jqGrid('setRowData',id, rowData);
	}
}

/**
 * 已核对表格指定行商品数量，如果行ID 不存在则添加1行
 * @param id	行ID
 * @param data	数据
 */
function updateCheckEdTableLineSkuQty(id,data,qty){
	var rowData = $j("#checked_list").jqGrid("getRowData",id);
	if(!(rowData && rowData.id)){
		//添加行
		var tableData = formateTableData(data,id);
		$j("#checked_list").jqGrid('addRowData',id,tableData);
	}else{
		//修改数量
		rowData.cQty = qty;
		 $j("#checked_list").jqGrid('setRowData',id, rowData);
	}
}
/**
 * 拍照
 */
function takePicture(pNo, staNo, barCode,timestamp) {
	try{
		closeGrabberJs();
	}catch(e){
//		console.log("closeGrabberJs"+e);
	}
	try{
		openCvPhotoImgJs();
	}catch(e){
//		console.log("openCvPhotoImgJs"+e);
	}
	try{
		cameraPhotoJs(dateValue + "/" + ouName + "/" + pNo + "/" + staNo, barCode + "_" + userName+"_"+timestamp);
	}catch(e){
//		console.log("cameraPhotoJs"+e);
	}
	var fileUrl = dateValue + "/" + ouName + "/" + pNo + "/" + staNo+"/"+barCode + "_" + userName+"_"+timestamp;
	console.log("fileUrl"+fileUrl);
	try{
		cameraPaintMultiJs(fileUrl);
	}catch(e){
//		console.log("cameraPaintMultiJs"+e);
	}
}
/**
 * 核对检查核对商品
 * @param barcode
 * @param sn
 * @returns {Boolean} 是否整单核对成功
 */
function checkScanSku(barcode,sn){//run
	var isSku = false;			//是否查询到SKU
	var isAllCheck = false; 	//当前单据是否已经核对完成
	//查询商品,包含商品多条码
	for(var i=0;i<toCheckData.orders.length;i++){
		var order = toCheckData.orders[i];
		//已经核对单据直接跳过
		if(order.skuQty <= 0){
			continue;
		}
		for(var j=0;j<toCheckData.orders[i].lines.length;j++){
			var line = order.lines[j];
			//如果行计划量等于执行量则跳过
			if(!line.cQty){
				line.cQty = 0;
			}
			if(line.qty - line.cQty <= 0 ){
				continue;
			}
			var rs = checkScanSkuFinsSku(line, barcode, sn);
			if(rs == -1){
				if(errMsg != ""){
					showErrorMsg(errMsg);
					return false;
				}
				//SN号商品但是SN号未输入,显示SN号扫描流程,计算结束
				var skuInfo = line.sku.supCode + "|" + line.sku.name;
				step1_3(skuInfo,"#P_1","#labScanBarcode","#P_2,#P_3","1_3");
				return false;
			}else if(rs == 0){
				//未查到继续
				continue;
			}else if(rs == 1){
				if(originMap!=null && originMap[line.sku.code].length>0){
					if(lastSkuCode!=line.sku.code){
						//加载产地
						loadOriginMap(line);
						return false;
					}
					var origin=$j("#origin").val();
					if(origin==null||origin==''){
						
						return false;
					}
				}else{
					showErrorMsg("商品产地未维护，请先维护："+line.sku.code);
					return;
				}
				
				if(disposeSpecialShow(line.uniqueId)){//验证特殊处理显示
				}else{
					return false;
				}
				//查询到SKU，行执行量(cQty)增加，单据待核对量(skuQty)减少
				line.cQty = line.cQty + 1;
				order.skuQty = order.skuQty -1;
				//刷新表格数据,待处理表格减少1件，如果为0则删除行
				updateToCheckTableLineSkuQty(line.uniqueId,line.qty - line.cQty);
				//已核对表格增加1件，如没有行则新增行
				updateCheckEdTableLineSkuQty(line.uniqueId,toCheckData,line.cQty);
				if(originMap!=null && originMap[line.sku.code].length>0){
					//记录作业单+商品+产地
					skuOrigin=skuOrigin+line.sku.code+"."+origin+";";
					
					if(!isSingleOrigin){
						$("#origin").find("option[html='请选择']").attr("selected",true);
						$("#origin").get(0).selectedIndex=0
					}
				}
				//更新其他数据
				$j("#labSlipCode1").html(order.slipCode1).attr("data",order.staCode);
				$j("#labSlipCode").html(order.orderCode).attr("data",order.staId);
				$j("#labTransNo").html(order.transNo);
				$j("#labPgIndex").html(order.pgIndex);
				$j("#labLpcode").html(order.lpcode);
				if(order.pickingType==1){
					$j("#btnSplitPg,#btnPrintPick").addClass("hidden");
				}else{
					$j("#btnSplitPg,#btnPrintPick").removeClass("hidden");
				}
				staId = order.staId;//设置当前核对单据
				isSku = true;
				try{
					takePicture(toCheckData.plCode, order.staCode, barcode,new Date().getTime());//拍照
				} catch (e) {
					alert(e);
				};
				if(order.skuQty <= 0){
					//if(order.pickingType==1){
						var skuInfo = line.sku.supCode + "|" + line.sku.name;
						step1_3(skuInfo,"#P_1","#labScanBarcode","#P_2",null);
					//}
					nowOrder = order;
					isAllCheck = true;
					if("1"==line.isGift){
				    	toSpecialBrand=false;
				    	toSpecialBrand2=true;
					}else{
				    	toSpecialBrand=true;
					}
				}
				break;
			}
		}
		//查询到SKU，结束所有循环校验
		if(isSku){
			break;
		}
	}
	
	//未找到商品提示信息 
	if(!isSku){
		showErrorMsg("未找到商品条码：" + barcode);
		return false;
	}
	//检查是否已经完全核对成功,如全都核对则继续，否则继续扫商品
	if(isAllCheck){
		
		isSpPackige = false;
		//默认将SN号输入框结果赋值到sn号文本框（可能为空的）
		step1_3(sn,null,"#labScanSn",null,null);
		if(nowOrder.pickingType==1){
			step1_3(nowOrder.pgIndex,null,"#labScanIndex",null,null);
			playMusic(BASE_URL +"/recording/"+nowOrder.pgIndex+".wav");
			step1_3(nowOrder.cmCode,"#P_3","#labScanCM","#P_4,#P_5,#P_6,#P_7","1_4");
			if(sn==""){
				//隐藏SN号文本框
				step1_3(null,"#P_4",null,null,null);
			}
			var resultPackage = loxia.syncXhr($j("body").attr("contextpath")+ "/findpaperSkuByBarCode.json", {"barcode" : barcode,"staId":staId});
			if(null!=resultPackage&&resultPackage.result=="success"&&null!=resultPackage.msg){
				$j("#labScanCM").html(resultPackage.msg);
			}else{
				$j("#labScanCM").html(nowOrder.cmCode);
			}
			return false;
		}
		var transNo = $j("#labTransNo").html();
		//如果作业单没有运单号信息，则为手写面单，需要核对运单号
		if(transNo==""){
			step1_3(null,"#P_3",null,"#P_4,#P_9","1_5");
			if(sn==""){
				//隐藏SN号文本框
				step1_3(null,"#P_4",null,null,null);
			}
		}
		//如果有运单号，直接触发核对操作
		else{
              if(toSpecialBrand){
      		    checkOrder(nowOrder);//run核对
              }			
		}
	}else{
		step1_2();
		return false;
	}
	//判断是否需要称重，需要则跳转去称重，不需要则反馈初始界面继续操作
}

//加载产地
function loadOriginMap(l){
	$("#origin").empty();
	var originS=originMap[l.sku.code];
	if(originS.length==0){
		$j("<option value=''>请选择</option>").appendTo($j("#origin"));
		return false;
	}else if(originS.length==1){
		$j("<option value='" + originS[0] + "' selected=true>"+ originS[0] +"</option>").appendTo($j("#origin"));
		isSingleOrigin=true;
	}else{
		$j("<option value=''>请选择</option>").appendTo($j("#origin"));
		for(var idx in originS){
			$j("<option value='" + originS[idx] + "'>"+ originS[idx] +"</option>").appendTo($j("#origin"));
		}
		isSingleOrigin=false;
	}
	lastSkuCode=l.sku.code;
}

/**
 * 播放提示音
 * @param url 声音文件位置
 */
function playMusic(url){
    var audio = document.createElement('audio');
    var source = document.createElement('source');   
    source.type = "audio/mpeg";
    source.type = "audio/mpeg";
    source.src = url;   
    source.autoplay = "autoplay";
    source.controls = "controls";
    audio.appendChild(source); 
    audio.play();
 }
/**
 * 根据当前页面核对订单信息，封装待核对数据
 * @param nowOrder
 */
function getPostData(nowOrder){
	var p = {};
	p["checkOrder.staId"]=nowOrder.staId;
	p["checkOrder.transNo"]=nowOrder.transNo;
	p["checkOrder.pickingType"]=nowOrder.pickingType;
	p["checkOrder.skuOrigin"]=skuOrigin;
	for(var i=0;i<nowOrder.lines.length;i++){
		p["checkOrder.lines["+i+"].uniqueId"]=nowOrder.lines[i].uniqueId;
		for(var j=0;j<nowOrder.lines[i].sns.length;j++){
			p["checkOrder.lines["+i+"].sns["+j+"]"]=nowOrder.lines[i].sns[j];
		}
	}
	return p;
}
/**
 * 核对作业单
 * @pram checkStaId 作业单ID
 */
function checkOrder(nowOrder){
	var postData = getPostData(nowOrder);
	var checkResult = loxia.syncXhr(BASE_URL+ "/docheckbysta.json",postData);
	if (checkResult && checkResult.result && checkResult.result == "success") {
		skuOrigin="";//重置产地
		//TODO:打印操作
		if(isWeight){
			//进入称重界面
			var transNo = $j("#labTransNo").html();
			initStep2_2(transNo);
		}else{
			if(toCheckData.isPostpositionExpressBill){
				var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?sta.id=" + nowOrder.staId+"&isOnlyParent=true";
				var result = printBZ(loxia.encodeUrl(url),false);
				if(typeof(result)!="undefined"){
					var i=0;
					while(i<3&&result=="ERROR"){
						result = printBZ(loxia.encodeUrl(url),false);
						i++;
					}
				}
			}
			if(nowOrder.pickingType==1){
				msgType = 1;
			}else{
				msgType = 2;
			}
			successCallBack();
		}
	}else{
		if(nowOrder.pickingType==1){
			msgType = 1;
			isTurn=true;
		}else{
			msgType = 2;
			isTurn=true;
		}
		showErrorMsg(checkResult.msg);
		//核对异常后续处理。
		//1、单件核对异常分两种情况
		//	a)中间单子异常，确认后跳过该单
		//  b)最后一单异常，确认后返回核对首页
		//2、多件核对异常
		//  a)确认后跳回核对首页
		return false;
	}
}
/**
 * 出库
 */
function outbound(weight){
	currentWeight = weight;
	//核对中称重只做记录重量操作
	var lb = $j("#labMate").html().trim();
	var trackingNo = $j("#labWeightTransNo").html();
	if(isSpPackige){
		var postData = {};
		postData["transNo"]=trackingNo;
		postData["weight"]=weight;
		postData["staffCode"]=lb;
		var rs = loxia.syncXhrPost(loxia.getTimeUrl(BASE_URL+"/recordweightfortrackingno.json"),postData);
		msgType = 3;
		if(rs!=null&&rs.result=="success"){
			if(isCheck){
				if(toCheckData.isPostpositionExpressBill){
					printTransNo($j("#labWeightSlipCode").attr("data"),$j("#labWeightLpcode").attr("data"));
				}
			}
			successCallBack();
			$j("#dialog_is_pre").hide();
		}else{
			showErrorMsg(rs.msg);
		}
	}
	//非核对中称重做出库操作
	else{
		var postData = {};
		if(lb != ""){
			postData["saddlines[0].sku.barCode"] =  lb;
			postData["saddlines[0].quantity"] =  1;
		}
		postData["trackingNo"]= trackingNo;
		postData["weight"]=weight;
		postData["staId"]=$j("#labWeightSlipCode").attr("data");
		if(isCheck){
			postData["allOutBound"]=true;
		}
		var rs = loxia.syncXhrPost(loxia.getTimeUrl(BASE_URL + "/outBoundHand.json"),postData);
		msgType = 4;
		if(rs!=null&&rs["result"]=="success"){
			if(rs["licensePlateNumber"]!=null&&rs["licensePlateNumber"]!=''){
				showErrorMsg(rs["licensePlateNumber"]);
			}
			if(isCheck){
				if(toCheckData.isPostpositionExpressBill){
					var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?sta.id=" + nowOrder.staId+"&isOnlyParent=true";
					var result=printBZ(loxia.encodeUrl(url),false);
					if(typeof(result)!="undefined"){
						var i=0;
						while(i<3&&result=="ERROR"){
							result = printBZ(loxia.encodeUrl(url),false);
							i++;
						}
					}
				}
			}
			successCallBack();
		}else{
			showErrorMsg(rs["message"]);
		}
	}
}
function successCallBack(){
	//如果是单件核对，判断当前核对页面是否还有单据未核对，如果有则留在当前页面
	if(msgType==1){
		msgType =0;
		var count =  $j("#to_check_list").jqGrid("getRowData").length;
		if(count>0){
			step1_3(null,"#step2_1,#step1_2 p",null,"#step1_1,#step1_2,#P_1","1_2");
		}else{
			init();
		}
		return false;
	}else if(msgType == 2){
		msgType =0;
		init();
		return false;
	}else if(msgType == 3){
		msgType=0;
		var transNo = $j("#labWeightTransNo").html();
		$j("#tabTrans tr:gt(0)").each(function(idx,tag){
			var labels = $j(tag).find("label");
			for(var i = 0;i<labels.length;i++){
				if(transNo == $j(labels[1]).html()){
					$j(labels[0]).html(currentWeight);
					break;
				}
			}
		});
		step1_3(null,"#step2_1,#step1_2 p",null,"#step1_1,#step1_2,#P_1","1_2");
		return false;
	}else if(msgType == 4){
		msgType=0;
		var lpcode = $j("#labWeightLpcode").html();
		//先判断有无交接
		if(isHo){
			addOutboundHoQty(lpcode);
			//判断是否达到交接上限
			var count = parseInt($j("#labAllHo").html());
			if(count>=maxHoList){
				step1_3(null,"#step2_1",null,"#step3",null);
				return false;
			}
		}
		if(isCheck){
			//判断是否有未完成的核对，主要单件，如果
			var count =  $j("#to_check_list").jqGrid("getRowData").length;
			if(count>0){
				step1_3(null,"#step2_1,#step1_2 p",null,"#step1_1,#step1_2,#P_1","1_2");
				return false;
			}else{
				step1_3(null,"#step2_1",null,"#step1","1_1");
				if(isHo){
					step1_3(null,null,null,"#step3",null);
					return false;
				}else{
					return false;
				}
			}
		}
		step1_3(null,"#step2_1",null,"#step2","2_1");
		if(isHo){
			step1_3(null,null,null,"#step3",null);
		}
		return false;
	}
}
$j(document).ready(function() {
	BASE_URL = $j("body").attr("contextpath");
	/******************************************************************************/
	/**对话框                                                                     */
	/******************************************************************************/
	//特殊处理
	$j("#dialog_gift").dialog({title: "特殊处理信息", modal:true,closeOnEscape: false,autoOpen: false, width: 450,		open : function(event, ui) {	$j(".ui-dialog-titlebar-close").hide();	},
});
	//错误信息对话框
	$j("#dialogMsg").dialog({
		title : "错误信息",
		modal : true,
		autoOpen : false,
		open : function(event, ui) {	$j(".ui-dialog-titlebar-close").hide();	},
		width : 600
	});
	
	$j("#btnErrorOk").click(function(){
		$j("#dialogMsg").dialog("close");
		$j("#txtErrorOk").val("");
		if(msgType == 1){
			msgType =0;
			if(isTurn==true){
				var count =  $j("#to_check_list").jqGrid("getRowData").length;
				if(count>0){
					//单件异常且存在未核对单子，跳过该单
					step1_3(null,"#step2_1,#step1_2 p",null,"#step1_1,#step1_2,#P_1","1_2");
				}else{
					//单件核对异常单为最后一单，返回核对首页
					init();
				}
				isTurn = false;
				return false;
			}
		}else if(msgType == 2||msgType == 6){
			if(msgType==6){
				var data = $j("#to_check_list").jqGrid('getRowData');
				var postData={};
				
				$j.each(data, function(i, stal) {
					postData["idList["+i+"]"]=stal.id;
				});
				var rs = loxia.syncXhrPost(BASE_URL + "/reportmissingwhencheck.json",postData);
				if(rs!=null&&rs.result=="success"){
					
				}else{
					msgType =0;
					isTurn = false;
					showErrorMsg(rs.msg);
					return false;
				}
			}
			msgType =0;
			if(isTurn==true){
				init();
				isTurn = false;
				return false;
			}
		}else if(msgType == 3){
			msgType = 0;
			init();
		}else if(msgType == 4){
			msgType =0;
			if(isCheck){
				//判断是否有未完成的核对，主要单件，如果
				var count =  $j("#to_check_list").jqGrid("getRowData").length;
				if(count>0){
					step1_3(null,"#step2_1,#step1_2 p",null,"#step1_1,#step1_2,#P_1","1_2");
					return false;
				}else{
					step1_3(null,"#step2_1",null,"#step1","1_1");
					if(isHo){
						step1_3(null,null,null,"#step3",null);
						return false;
					}else{
						return false;
					}
				}
			}
			step1_3(null,"#step2_1",null,"#step2","2_1");
			if(isHo){
				step1_3(null,null,null,"#step3",null);
			}
			return false;
		}else if(msgType==5){
			msgType = 0;
			var data = $j("#to_check_list").jqGrid('getRowData');
			var postData={};
			
			$j.each(data, function(i, stal) {
				postData["idList["+i+"]"]=stal.id;
			});
			var rs = loxia.syncXhrPost(BASE_URL + "/partlysalesoutbound.json",postData);
			if(rs!=null&&rs.result=="success"){
				init();
				return true;
			}else{
				showErrorMsg(rs.msg);
				return false;
			}
		}
		nextFource(curStep);
	});
	$j("#txtErrorOk").keypress(function(evt){
		if (evt.keyCode === 13) {
			var key = $j("#txtErrorOk").val().trim().toUpperCase();
			if(KEY_CONFIRM == key || "" == key){
				$j("#btnErrorOk").trigger("click");	
			}else{
				$j("#txtErrorOk").val("");
			}
		}
	});
	
	//操作成功对话框
	$j("#dialogInfoMsg").dialog({
		title : "操作完成",
		modal : true,
		autoOpen : false,
		open : function(event, ui) {	$j(".ui-dialog-titlebar-close").hide();	},
		width : 600
	});
	
	$j("#btnInfoOk").click(function(){
		$j("#dialogInfoMsg").dialog("close");
		$j("#txtInfoOk").val("");
		if(msgType==1){
			successCallBack();
		}
		nextFource(curStep);
	});
	
	$j("#txtInfoOk").keypress(function(evt){
		if (evt.keyCode === 13) {
			var key = $j("#txtInfoOk").val().trim().toUpperCase();
			if(KEY_CONFIRM == key || "" == key){
				$j("#btnInfoOk").trigger("click");	
			}else{
				$j("#txtInfoOk").val("");
			}
		}
	});
	/******************************************************************************/
	/**step1 相关扫描核对                                                          */
	/******************************************************************************/
	//【单据号】点击
	$j("#btnOrder").click(function(){
		//显示1-1
		initStep1_2($j("#txtOrder").val().trim());
	});
	
	//【单据号】扫描
	$j("#txtOrder").keypress(function(evt) {
		if (evt.keyCode === 13) {
			$j("#btnOrder").trigger("click");
		}
	});
	
	
	//【商品】点击
	$j("#btnScanSku").click(function(){
		if($j("#txtScanSku").val().trim() == ""){
			showErrorMsg("请扫描商品条码");
			return false;
		}
	    var barcode = $j("#txtScanSku").val().trim();
	    var sn = $j("#txtScanSn").val().trim();
	    //核对商品
	    checkScanSku(barcode,sn);
	});
	
	//【商品】扫描
	$j("#txtScanSku").keypress(function(evt){
		if (evt.keyCode === 13) {
			$j("#btnScanSku").trigger("click");
		}
	});
	
	
	//【SN】点击
	$j("#btnScanSn").click(function(){
		if($j("#txtScanSn").val().trim() == ""){
			showErrorMsg("请扫描商品SN号");
			return false;
		}
	    var barcode = $j("#txtScanSku").val().trim();
	    var sn = $j("#txtScanSn").val().trim();
		checkScanSku(barcode,sn);
	});
	//【SN】扫描
	$j("#txtScanSn").keypress(function(evt){
		if (evt.keyCode === 13) {
			$j("#btnScanSn").trigger("click");
		}
	});
	//【订单号】扫描
	$j("#txtScanSlipCode").keypress(function(evt){
		if(evt.keyCode === 13){
			var slipCode = $j("#txtScanSlipCode").val().trim();
			if(slipCode==""){
				showErrorMsg("请扫描订单号！");
				return false;
			}else{
				var sv = $j("#labSlipCode").html();
				var sc = $j("#labSlipCode1").attr("data");
				if(!(slipCode==sv||slipCode==sc)){
					showErrorMsg("目前扫描订单号不是当前核对的订单!");
					return false;
				}
			}
			var stn = $j("#labTransNo").html();
			if(stn!=""){
				checkOrder(nowOrder);//run核对
			}else{
				step1_3(slipCode,"#P_7","#labScanSlipCode","#P_8,#P_9","1_5");
			}
		}
	});
	//【快递单号】扫描
	$j("#txtScanTransNo").keypress(function(evt){
		if(evt.keyCode === 13){
			$j("#btnScanTranNo").trigger("click");
		}
	});
	//【快递单号】点击
	$j("#btnScanTranNo").click(function(evt){
		//var stn = $j("#labTransNo").html();
		var tst = $j("#txtScanTransNo").val().trim();
		if(tst==""){
			showErrorMsg("请扫描运单号!");
			return false;
		}
		var pl = loxia.syncXhrPost(BASE_URL+ "/findSlipCodeAndPickingListCodeByStaCode.json", {"code" : nowOrder.staCode});
	    if(tst==pl["pls"]["supplierCode"] || tst==pl["pls"]["slipCode"] || tst.toUpperCase()=="OK".toUpperCase()||tst==nowOrder.staCode){
	    	showErrorMsg("非正确的快递单号(可能误扫了OK,或者单据信息)!");
	    	return false;
	    }
		var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/checktrackingno.json",{"trackingNo":tst,"sta.id":nowOrder.staId});
		if(!(rs && rs.result=='success')){
			showErrorMsg("快递单号格式不正确");
			return false;
		}
		//@TODO:1、需不需要检验运单号跟当前界面运单号重复/2、核对完成一单要确然按钮吗，还是直接到出库
		$j("#labTransNo").html(tst);
		nowOrder.transNo=tst;
		checkOrder(nowOrder);//run核对
	});
	
	//【拆包】点击
	$j("#btnSplitPg").click(function(){
		if($j("#labSlipCode").html() == ""){
			showErrorMsg("请先扫描商品，当前不允许拆包裹");
			return false;
		}
		var rs=loxia.syncXhrPost(BASE_URL + "/json/staAddTrankNo.json",{"sta.id":staId});
		if(rs && rs.msg == 'success'){
			//光标定位到扫商品输入框
			$j("#txtScanSku").focus();
			//生成子运单号，跳转至称重界面称重，称重成功后返回复核界面
			//TODO to get tran no
			var temp = rs.trankCode;
			var transNo = temp.substring(0,temp.indexOf(","));
			var packId = temp.substring(temp.indexOf(",")+1,temp.length);
			var tr  = "<tr>";
				tr +="<td><label class='font-M'>--</label></td><td><label class='font-M'>"+transNo+"</label></td>";
				tr += "<td><button type='button' class='btn btn-info btn-sm'  onClick='printTransNo(\""+staId+"\",\""+packId+"\")' ><span class='glyphicon glyphicon-print'></span></button></td>";
				tr += "</tr>";
			$j("#tabTrans").append(tr);
			showPane($j("#tabTrans"));
			//TODO 自动打印面单
			if(!isWeight){
				if(toCheckData.isPostpositionExpressBill){
					printTransNo(staId,packId);
				}
			}
			//跳转至称重界面,标识需要返回原复核界面
			isSpPackige = true;
			if(isWeight){
				initStep2_2(transNo);
			}
		}else{
			alert(rs.errMsg);
			var b = rs.errMsg;
			showErrorMsg(b);
			return false;
		}
	});
	
	
	
	/******************************************************************************/
	/**step2 相关称重	                                                          */
	/******************************************************************************/
	//【运单号】点击
	$j("#btnTransNo").click(function(){
		//显示1-1
		initStep2_2($j("#txtTransNo").val().trim());
	});	
	//【运单号】扫描
	$j("#txtTransNo").keypress(function(evt) {
		if (evt.keyCode === 13) {
			$j("#btnTransNo").trigger("click");
		}
	});
	
	//【耗材】点击
	$j("#btnScanMate").click(function(){
		var mate = $j("#txtScanMate").val().trim();
		if(mate==""){
			showErrorMsg("扫描耗材为空！");
			return false;
		}
		var isHaoCai= $j("#isHaoCai").val();
		if(isMustCM){
			if(isHaoCai=="0"){}else{
					var cm = $j("#labWeightTransNo").attr("data").trim();
					if(cm != ""){
						if(cm != mate){
							showErrorMsg("扫描的耗材跟推荐耗材不一致!");
							return false;
						}
					}
			}
		}
		var isCm = checkIsCm(mate);
		if(isCm){
			step2_4(mate);
		}
	});
	//【耗材】扫描
	$j("#txtScanMate").keypress(function(evt){
		if (evt.keyCode === 13) {
			$j("#btnScanMate").trigger("click");
		}
	});
	
	//【重量】输入确认
	$j("#btnInputWeight").click(function(){
		//校验重量输入
		step2_5($j("#txtInputWeight").val());
	});
	//【重量】输入
	$j("#txtInputWeight").keypress(function(evt){
		if (evt.keyCode === 13) {
			$j("#btnInputWeight").trigger("click");
		}
	});
	
	//【重量】扫描确认
	$j("#btnWightOK").click(function(){
		var bc = $j("#txtWightOK").val().trim().toUpperCase();
		if(bc!=KEY_CONFIRM){
			showErrorMsg("扫描条码！");
			return false;
		}
		var weight = $j("#autoWeigth").val().trim();
		if(checkWeight(weight)){
			step1_3($j("#autoWeigth").val(),"#step2_4_5","#labEditWeight","#step2_4_3,#step2_4_4","2_5");
		}
	});
	
	//【自动称重重量OK】扫描
	$j("#txtWightOK").keypress(function(evt){
		if (evt.keyCode === 13) {
			$j("#btnWightOK").trigger("click");
		}
	});
	$j("#btnEditWeight").click(function(){
		step1_3("","#step2_4_3,#step2_4_4","#labEditWeight","#step2_4_5","2_4_5");
	});
	//【确认出库】确认
	$j("#btnOutboundOk").click(function(){
		var weight=$j("#labEditWeight").html().trim();
		step2_5(weight);
	});
	
	//【确认出库】扫描
	$j("#txtOutboundOk").keypress(function(evt){
		if (evt.keyCode === 13) {
			var key = $j("#txtOutboundOk").val().trim().toUpperCase();
			if(KEY_CONFIRM == key || "" == key){
				$j("#btnOutboundOk").trigger("click");	
			}else{
				$j("#txtOutboundOk").val("");
			}
		}
	});	
	
	/******************************************************************************/
	/**step1 相关交接	                                                          */
	/******************************************************************************/
	//交接checkbox全选功能
	$j("#selectAll").change(function(){
		$j("#tbHo td input[type=checkbox]").attr("checked",$j(this).attr("checked"));
	});
	
	//交接按钮，选中快递创建交接清单
	$j("#btnHo").click(function(){
		var eMsg = "";
		var sum=0;
		$j("#tbHo tr:gt(0)").each(function(){
			var isSelect = $j(this).find("input[type=checkbox]").attr("checked");
			if(isSelect){
				sum++;
			}
		});
		if(sum==0){
			showErrorMsg("没有选中要交接的物流商!");
		}
		$j("#tbHo tr:gt(0)").each(function(){
			var isSelect = $j(this).find("input[type=checkbox]").attr("checked");
			if(isSelect){
				var lpcode = $j(this).find("label[name=labLpcode]").html();
				var qty = $j(this).find("label[name=labToHoQty]").html();
				//TODO 创建交接清单
				var postData={};
				postData["lpcode"]=lpcode+"|";
				var rs = loxia.syncXhrPost(BASE_URL + "/createOneHandOverList.json",postData);
				if(rs["result"] == 'success'){
					 if(rs["brand"]=='0'){
						 //
					 }else{
						  var hoIds=rs["hoIds"];
						  var url = BASE_URL + "/json/printhandoverlist2.json?hoIds=" + hoIds;
						  printBZ(loxia.encodeUrl(url),true);
					 }
					 $j(this).remove();
				 }else{
					    eMsg+=lpcode;
				 }
			}
		});
		if(eMsg!=""){
			showErrorMsg("物流商【"+eMsg+"】交接失败!");
			return false;
		}
		init();
		if(isHo){
			initHoList();
		}
	});
	
	
	/******************************************************************************/
	/**表格数据			                                                          */
	/******************************************************************************/
	//单据明细信息
	$j("#to_check_list").jqGrid(
		{
			datatype : "json",
			cache : false,
			async : false,
			colNames : [ "ID","箱号","作业单","相关单据号", "状态","店铺","物流","商品名称", "SKU编码", "特殊处理显示","商品条码", "计划量", "快递单号"],
			colModel : [ 
			        {name : "id",index : "id",hidden : true,sortable : false},
					{name : "pgIndex",width : 30,resizable : true,sortable : false},
					{name : "staCode",	width : 90,resizable : true,	sortable : false},
					{name : "orderCode",width : 90,resizable : true,	sortable : false},
					{name : "status",	width : 40,resizable : true,sortable : false},
					{name : "owner",	width : 80,resizable : true,	sortable : false},
					{name : "lpcode",	width : 80,resizable : true,	sortable : false},
					{name : "skuName",width : 110,	resizable : true,sortable : false},
					{name : "skuCode",	width : 80,resizable : true,sortable : false},
					{name : "isGift",	width : 80,resizable : true,sortable : false},
					{name : "barCode",	width : 100,	resizable : true,sortable : false,	hidden : true},
					{name : "qty",width : 50,	resizable : true,sortable : false},
					{name : "transNo",width : 100,resizable : true,	sortable : false}
					],
			caption : '待核对清单',//待核对列表
			multiselect : false,
			width : 1250,
			height : 200,
			rowNum : -1,
			viewrecords : true,
			gridComplete: function() {
				$j("#to_check_list tr:gt(0)").each(function(i,tag){
					var isGift= $j(tag).children("td[aria-describedby$='to_check_list_isGift']").attr("title");
					if(isGift == '1' ){
						$j(tag).children("td[aria-describedby$='to_check_list_isGift']").html("是");
//						$j(tag).children("td[aria-describedby$='to_check_list_gift']").html("");
					} else {
						$j(tag).children("td[aria-describedby$='to_check_list_isGift']").html("否");
					}
				});
			},
			jsonReader : {repeatitems : false,	id : "0"}
		});
	
	
	//单据明细信息
	$j("#checked_list").jqGrid(
			{
				datatype : "json",
				cache : false,
				async : false,
				colNames : [ "ID","箱号","作业单","相关单据号", "状态","店铺","物流","商品名称", "SKU编码","特殊处理显示","商品条码", "核对量", "快递单号"],
				colModel : [ 
				        {name : "id",index : "id",hidden : true,sortable : false},
						{name : "pgIndex",width : 30,resizable : true,sortable : false},
						{name : "staCode",	width : 90,resizable : true,	sortable : false},
						{name : "orderCode",width : 90,resizable : true,	sortable : false},
						{name : "status",	width : 40,resizable : true,sortable : false},
						{name : "owner",	width : 80,resizable : true,	sortable : false},
						{name : "lpcode",	width : 80,resizable : true,	sortable : false},
						{name : "skuName",width : 110,	resizable : true,sortable : false},
						{name : "skuCode",	width : 80,resizable : true,sortable : false},
						{name : "isGift",	width : 80,resizable : true,sortable : false},
						{name : "barCode",	width : 100,	resizable : true,sortable : false,	hidden : true},
						{name : "cQty",width : 50,	resizable : true,sortable : false},
						{name : "transNo",width : 100,resizable : true,	sortable : false}
						],
				caption : '已核对清单',//待核对列表
				multiselect : false,
				width : 1250,
				height : 100,
				rowNum : -1,
				viewrecords : true,
				gridComplete: function() {
					$j("#checked_list tr:gt(0)").each(function(i,tag){
						var isGift= $j(tag).children("td[aria-describedby$='checked_list_isGift']").attr("title");
						if(isGift == '0'){
							$j(tag).children("td[aria-describedby$='checked_list_isGift']").html("否");
//							$j(tag).children("td[aria-describedby$='to_check_list_gift']").html("");
						} else {
							$j(tag).children("td[aria-describedby$='checked_list_isGift']").html("是");
						}
					});
				},
				jsonReader : {repeatitems : false,	id : "0"}
			});
	
	
	$j("#restartWeight").click(function(){
		restart();
	});
//	$j("#printParent").click(function(){
//		var staId = $j("#labSlipCode").attr("data");
//		var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?sta.id=" + staId+"&isOnlyParent=true";
//		printBZ(loxia.encodeUrl(url),false);
//	});
	/******************************************************************************/
	/**初始化加载			                                                      */
	/******************************************************************************/
	hiddenAll();
	/**
	 * 菜单选择事件
	 */
	var ug = loxia.syncXhrPost(loxia.getTimeUrl(BASE_URL + "/finduserprivilegeinfo.json")); 
	var isC = ug.isCheck=="null"?false:ug.isCheck;
	var isW = ug.isWeight=="null"?false:ug.isWeight;
	var isH = ug.isHo=="null"?false:ug.isHo;
	$j("#isC").attr("checked",isC);
	$j("#isW").attr("checked",isW);
	$j("#isH").attr("checked",isH);
	var isHaveShortButton = ug.isHaveShortButton=="null"?false:ug.isHaveShortButton;
	if(!isHaveShortButton){
		$j("#btnShort").addClass("hidden");
	}
	canPartlyOutbound = ug.isShortCheck=="null"?false:ug.isShortCheck;
	
	
	$j("#menuConfirm").click(function(){
		var isC = $j("#isC").is(":checked");
		var isW = $j("#isW").is(":checked");
		var isH = $j("#isH").is(":checked");
		if(isC&&isH&&!isW){
			showErrorMsg("该组合权限业务流程不支持！");
			return false;
		}
		if(!(isC||isW||isH)){
			showErrorMsg("请至少选择一个操作流程！");
			return false;
		}
		$j("#menuSelect").addClass("hidden");
		isWeight=isW;
		isCheck = isC;
		isHo = isH;
		init();
		if(isHo){
			initHoList();
		}
	});	
	$j("#btnShort").click(function(){
		msgType = 6;
		isTurn=true;
		showErrorMsg("确认缺货?");
	});
	$j("#btnSkip").click(function(){
		msgType = 1;
		showInfoMsg("确认跳过?");
	});
	$j("#btnPartly").click(function(){
		msgType = 5;
		isTurn = true;
		showErrorMsg("确认部分发货?");
	});
	$j("#btnPrintPick").click(function(){
		var url = BASE_URL + "/printtrunkpackinglistmode1.json?plCmd.id=" + toCheckData.plId + "&plCmd.staId=" + staId;
		printBZ(loxia.encodeUrl(url),true);				
		loxia.unlockPage();
	});
	/**
	 * 特殊处理显示进行核对_处理完成
	 */
	$j("#PASS").click(function(){
		C_PASS();
	});
	
	function C_PASS(){
		var transNo = $j("#labTransNo").html();
		if(transNo==""){}else{
		 if(toSpecialBrand2){
   		    checkOrder(nowOrder);
           }		
		}
		 $j("#dialog_gift").dialog("close");
	}
	/**
	 * 特殊处理显示进行核对_处理完成 扫描
	 */
	$j("#PASS1").keydown(function(event){
		var value = $j("#PASS1").val();
		if(event.keyCode == 13){
			if (value == 'PASS'){
				C_PASS();
				$j("#PASS1").val("");
			}else if(value != "" ) {
				 $j("#PASS1").val("");
				 jumbo.showMsg("输入的确认条码不正确，请重新输入 PASS");return;
			}else if(value == "" ) {
				 jumbo.showMsg("请输入确认条码. PASS");return;
			}
		}
	});

	/**
	 * 特殊处理显示进行核对_复制OK
	 */
	$j("#OK").click(function(){
		C_OK();
	});
	
function C_OK(){
		var c=document.getElementById("gift_memo");
		c.select(); // 选择对象
		document.execCommand("Copy"); // 执行浏览器复制命令
		var transNo = $j("#labTransNo").html();
		if(transNo==""){}else{
		 if(toSpecialBrand2){
   		    checkOrder(nowOrder);
           }		
		}
		 $j("#dialog_gift").dialog("close");
	}
	
//	 特殊处理显示进行核对_复制OK 扫描
	$j("#OK1").keydown(function(event){
		var value = $j("#OK1").val();
		if(event.keyCode == 13){
			if (value == 'OK'){
				C_OK();
				$j("#OK1").val("");
			}else if(value != "" ) {
				 $j("#OK1").val("");
				 jumbo.showMsg("输入的确认条码不正确，请重新输入 OK");return;
			}else if(value == "" ) {
				 jumbo.showMsg("请输入确认条码. OK");return;
			}
		}
	});
});

/**
 * 特殊处理显示条形码显示
 */
function code128(){
	$("#bcTarget").empty().barcode($("#gift_memo").val(), "code128",{barWidth:1, barHeight:30,showHRI:false});
}

