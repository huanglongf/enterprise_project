var ERROR = "异常";
var barcodeList;
var ERROR_CANCEL = "取消";
$j.extend(loxia.regional['zh-CN'], {
	CODE : "配货批次号",
	INTSTATUS : "状态",
	PLANBILLCOUNT : "计划配货单据数",
	CHECKEDBILLCOUNT : "已核对单据数",
	SHIPSTACOUNT : "已发货单据数",
	PLANSKUQTY : "计划配货商品件数",
	CHECKEDSKUQTY : "已核对商品件数",
	SHIPSKUQTY : "已发货商品件数",
	CHECKEDTIME : "最后核对时间",
	EXECUTEDTIME : "最后发货时间",
	PICKINGTIME : "开始配货时间",
	PICKING_LIST : "配货清单列表",
	CREATE_TIME : "创建时间",
	INIT_SYSTEM_DATA_EXCEPTION : "初始化系统参数异常",
	KEY_PROPS : "扩展属性",
	MEMO : "备注",
	STA_CODE : "作业单号",
	INTSTATUS : "状态",
	REFSLIPCODE : "相关单据号",
	INTTYPE : "作业类型名称",
	OWNER : "相关店铺",
	LPCODE : "物流服务商简称",
	CREATETIME : "创建时间",
	STVTOTAL : "计划执行总件数",
	WAITTING_CHECKED_LIST : "待核对列表",

	CHECKED_LIST : "已核对列表",
	SKUCODE : "SKU编码",
	LOCATIONCODE : "库位编码预览",
	SKUNAME : "商品名称",
	JMSKUCODE : "商品编码",
	BARCODE : "条形码",
	QUANTITY : "计划执行数量",
	COMPLETEQUANTITY : "已执行数量",

	INPUT_CODE : "请输入相关单据号",
	NO_CODE : "指定单据号的作业单不在待核对的列表！",
	BARCODE_NOT_EXISTS : "条形码不存在",
	TRACKINGNO_EXISTS : "快递单号已经存在",
	DELETE : "删除",
	TRACKINGNO_RULE_ERROR : "快递单号格式不对",
	SURE_OPERATE : "确定执行本次操作",
	QUANTITY_ERROR : "数量错误",
	WEIGHT_RULE_ERROR : "已执行量大于计划执行量，如果继续执行核对，将直接按照计划执行量执行核对。是否继续？",
	INPUT_TRACKINGNO : "请输入快递单号",
	OPERATE_FAILED : "执行核对失败！"
});

function i18(msg, args) {
	return loxia.getLocaleMsg(msg, args);
}

function getFmtValue(tableId, rowId, column) {
	return $j("#" + tableId + " tr[id=" + rowId + "] td[aria-describedby='"+ tableId + "_" + column + "']").attr("title")
}

var fileName = "";

function showDetail(obj) {
	var plCode;
	fileName = "";
	if (obj == null) {
		plCode = $j("#quickPl").val();
	} else {
		plCode = $j(obj).text();
	}
	var pl = loxia.syncXhrPost($j("body").attr("contextpath")+ "/getsinglecheckorder.json", {"code" : plCode});
	if (pl && pl["pl"]) {
		var data ={};
		data["plCmd.code"]=plCode;
		var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/checkPickingisOver.json",data);
		if(rs && rs.result=='success'){
			// do nothing
		}else{
			jumbo.showMsg("批次号"+plCode+"未拣货完成，不能直接操作！ 请至[拣货批次开始]页面操作");
			return;
		}
	    try{
			closeGrabberJs();				
		}catch (e) {
//			jumbo.showMsg(i18("关闭摄像头失败！"));//初始化系统参数异常
		}
		try{
				openCvPhotoImgJs();//打开照相功能
//				jumbo.showMsg(i18("打开照相功能成功！"));
			} catch (e) {
				jumbo.showMsg(i18("打开照相功能失败！"));//初始化系统参数异常
			};
		var row = pl['pl']['id'];
		$j("#verifyCode").html(pl['pl']['code']);
		if (pl['pl']['intStatus'] == '2') {
			$j("#verifyStatus").html('配货中');
		}
		if (pl['pl']['intStatus'] == '8') {
			$j("#verifyStatus").html('部分完成');
		}
		$j("#verifyPlanBillCount").html(pl['pl']['planBillCount']);
		$j("#verifyCheckedBillCount").html(pl['pl']['checkedBillCount']);
		$j("#verifyPlanSkuQty").html(pl['pl']['planSkuQty']);
		$j("#verifyCheckedSkuQty").html(pl['pl']['checkedSkuQty']);
		$j("#isBkCheckInteger").val(pl['pl']['isBkCheckInteger']);
		if(true == pl['pl']['isPostpositionPackingPage']){
			$j("#isPostpositionPackingPage").val(1);
			$j("#printPackingPage").show();
		}else{
			$j("#isPostpositionPackingPage").val(0);
			$j("#printPackingPage").hide();
		}
		if(true == pl['pl']['isPostpositionExpressBill']){
			$j("#isPostpositionExpressBill").val(1);
			$j("#printExpressBill").hide();//$j("#printExpressBill").show();
		}else{
			$j("#isPostpositionExpressBill").val(0);
			$j("#printExpressBill").hide();
		}
		if(true == pl['pl']['isPostpositionPackingPage'] || true == pl['pl']['isPostpositionExpressBill']){
			$j("#plId").val(row);
		}else{
			$j("#plId").val("");
		}
		$j("#plDiv").addClass("hidden");
		$j("#checkDiv").removeClass("hidden");
		$j("#quickPl").val("");
		$j("#tbl-sta-list").jqGrid('setGridParam',{	url : loxia.getTimeUrl($j("body").attr("contextpath")+ "/checkSingleGetOcpStalByPl.json"),
					postData : {"cmd.id" : row}
				}).trigger("reloadGrid");
		$j("#skuBarCode").focus();
		initCheck();
	} else {
		jumbo.showMsg("未找到配货批次！");
		return;
	}
}
function isCheckedFinish() {
	var data = $j("#tbl-sta-list").jqGrid('getRowData');
	var isAllChecked = true;
	var isError = false;
	$j.each(data, function(i, stal) {
		if (stal["trackingNo"] == ERROR) {
			isError = true;
		}
		if (stal["trackingNo"] != ERROR && stal["trackingNo"] != ERROR_CANCEL) {
			if (isAllChecked && stal["quantity"] != stal["completeQuantity"]) {
				isAllChecked = false;
			}
		}
	});
	if (!isError && isAllChecked) {
		$j("#divAllChecked").removeClass("hidden");
		$j("#divAllChecked1").removeClass("hidden");
		return true;
	} else {
		$j("#divAllChecked").addClass("hidden");
		$j("#divAllChecked1").addClass("hidden");
		$j("#skuBarCode").focus();
		return false;
	}
}

function getCheckPostData() {
	var postdata = {};
	postdata["sta.id"] = $j("#hidStaId").val();
	postdata["sta.trackingNo"] = $j("#txtTkNo").val().trim();
	postdata["lineNo"] = lineNo;
	postdata["sn"] = $j("#hidSn").val();
	return postdata;
}

function showErrorMsg(msg) {
	$j("#errorMsg").html(msg);
	$j("#dialog_error").dialog("open");
	$j("#confirmBarCode1").focus();
}

function errorTipOK(text) {
	$j("#error_text_ok").html("<font style='text-align:center;font-size:36px' color='red'>"+ text + "</font>");
	$j("#dialog_error_ok").dialog("open");
}

var stastatus = loxia.syncXhr($j("body").attr("contextpath")+ "/json/formateroptions.json", {"categoryCode" : "whSTAStatus"});
var statype = loxia.syncXhr($j("body").attr("contextpath")+ "/json/formateroptions.json", {"categoryCode" : "whSTAType"});
var trasportName = loxia.syncXhr($j("body").attr("contextpath")+ "/json/getTrasportName.json");
var starbucksIcePackage=loxia.syncXhr($j("body").attr("contextpath") + "/findStarbucksIcePackage.json");
var lineNo;
var isLineNoError = "";
var userName = "";
var dateValue = "";
var ouName = "";
//条形码选中的行
var lineStal;
var fileUrl = "";
$j(document).ready(function() {
	initAesKey();
	userName = $j("#userinfo", window.parent.document).attr("lgname");
	dateValue = $j("#userinfo", window.parent.document).attr("sysdate");
	ouName = $j("#userinfo", window.parent.document).attr("ouName");
	$j("#dialog_msg").dialog({
		title : "提示信息",
		modal : true,
		autoOpen : false,
		width : 1000,
		position:"left",
		closeOnEscape : false,
		open : function(event, ui) {$j(".ui-dialog-titlebar-close").hide();}
	});
	$j("#dialog_error").dialog({
		title : "错误信息",
		modal : true,
		autoOpen : false,
		width : 650
	});
	$j("#dialog_success_msg").dialog( {
		title : "提示信息",
		modal : true,
		autoOpen : false,
		width : 650,
		closeOnEscape : false,
		open : function(event, ui) {$j(".ui-dialog-titlebar-close").hide();}
	});
	$j("#quickPl").focus();
	$j("#whName").html(window.parent.$j("#orgname").html());

	$j("#dialog_error_ok").dialog({
		title : "错误信息",
		modal : true,
		autoOpen : false,
		width : 400
	});
	$j("#dialog_error_close_ok").click(function() {
		$j("#dialog_error_ok").dialog("close");
		initCheck();
	});
	$j("#closeCancel").keydown(function(evt) {
		if (evt.keyCode === 13) {
			if (BARCODE_CONFIRM == $j("#closeCancel").val().trim()) {
				$j("#dialog_error_ok").dialog("close");
				initCheck();
			}
		}
	});

	var plstatus = loxia.syncXhr($j("body").attr("contextpath")+ "/formateroptions.json", {	"categoryCode" : "pickingListStatus"});
	if (!plstatus.exception) {
		$j("#tbl-dispatch-list").jqGrid({
			url : $j("body").attr("contextpath")+ "/searchCheckList.json",	postData : loxia._ajaxFormToObj("plForm"),
			datatype : "json",//"已发货单据数","已发货商品件数",
			//colNames: ["ID","配货批次号","状态","计划配货单据数","已核对单据数","已发货单据数","计划配货商品件数","已核对商品件数","已发货商品件数","最后核对时间","最后发货时间","开始配货时间"],
			colNames : ["ID", i18("CODE"),i18("INTSTATUS"),i18("PLANBILLCOUNT"),i18("CHECKEDBILLCOUNT"),i18("SHIPSTACOUNT"),i18("PLANSKUQTY"),i18("CHECKEDSKUQTY"),i18("SHIPSKUQTY"),i18("CREATE_TIME"),i18("CHECKEDTIME"),	i18("EXECUTEDTIME"),	i18("PICKINGTIME"),''],
			colModel : [
			            {name : "id",index : "pl.id",hidden : true}, 
						{name : "code",index : "pl.code",formatter : "linkFmatter",formatoptions : {onclick : "showDetail"},width : 100,resizable : true}, 
						{name : "intStatus",	index : "pl.status",width : 40,resizable : true,	formatter : 'select',editoptions : plstatus	},
						{name : "planBillCount",index : "pl.plan_bill_count",width : 100, resizable : true},
						{name : "checkedBillCount",index : "pl.checked_bill_count",width : 90,	resizable : true},
						{name : "shipStaCount",	index : "pl.send_bill_count",width : 90,resizable : true,hidden : true	},
						{name : "planSkuQty",	index : "pl.plan_sku_qty",width : 120,	resizable : true},
						{name : "checkedSkuQty",	index : "pl.checked_sku_qty",width : 100,resizable : true},
						{name : "shipSkuQty",	index : "pl.send_sku_count",width : 100,resizable : true,hidden : true}, 
						{name : "createTime",	index : "pl.CREATE_TIME",	width : 150,resizable : true	},
						{name : "checkedTime",index : "pl.CHECK_TIME",width : 150,	resizable : true},
						{name : "executedTime",	index : "pl.last_outbound_time",width : 150,resizable : true,hidden : true},
						{name : "pickingTime",index : "pl.PICKING_TIME",	width : 150,resizable : true	},
						{name : "isBkCheckInteger",index : "isBkCheckInteger",hidden : true}
			],
			caption : i18("PICKING_LIST"),//配货清单列表
			sortname : 'pl.CREATE_TIME',
			multiselect : false,
			sortorder : "desc",
			pager : "#pager",
			width : "auto",
			height : "auto",
			rowNum : jumbo.getPageSize(),
			rowList : jumbo.getPageSizeList(),
			height : jumbo.getHeight(),
			viewrecords : true,
			rownumbers : true,
			jsonReader : {repeatitems : false,id : "0"}
		});
	} else {
		jumbo.showMsg(i18("INIT_SYSTEM_DATA_EXCEPTION"));//初始化系统参数异常
	}

	$j("#search").click(function() {
		$j("#tbl-dispatch-list").jqGrid('setGridParam',	{url : loxia.getTimeUrl($j("body").attr("contextpath")	+ "/json/searchCheckList.json"),postData : loxia._ajaxFormToObj("plForm")})
								.trigger("reloadGrid",[{page : 1}]);
	});

	$j("#reset").click(function() {
		document.getElementById("plForm").reset();
	});
	var barCodesList1  = new Array();//run
	$j("#tbl-sta-list").jqGrid({
		datatype : "json",
		colNames : [ "ID", "是否SN","耗材条码","箱号",i18("STA_CODE"),i18("REFSLIPCODE"), "状态",i18("OWNER"), i18("LPCODE"),"商品名称", "SKU编码", "商品条码", "计划量","核对量", "SN号", "电子运单号", "快递单号",'是否后端SKU','','',''],
		colModel : [ 
		            {name : "id",index : "id",hidden : true,	sortable : false},
		            {name : "isSnSku",index : "isSnSku",hidden : true,sortable : false}, //是否SN
					{name : "packageBarCode",hidden:true,sortable:false},
					{name : "pgIndex",width : 30,	resizable : true,sortable : false},
					{name : "staCode",	width : 90,resizable : true,	sortable : false}, 
					{name : "staSlipCode",width : 90,resizable : true,	sortable : false},
					{name : "intStatus",	index : "intStatus",width : 40,	resizable : true,formatter : 'select',editoptions : stastatus,sortable : false}, 
					{name : "owner",	width : 80,resizable : true,	sortable : false},
					{name : "lpcode",	width : 80,resizable : true,	sortable : false,formatter : 'select',	editoptions : trasportName}, 
					{name : "skuName",width : 120,	resizable : true,sortable : false}, 
					{name : "skuCode",	width : 100,resizable : true,sortable : false},
					{name : "barCode",	width : 100,	resizable : true,sortable : false,	hidden : true}, 
					{name : "quantity",width : 60,	resizable : true,sortable : false}, 
					{name : "completeQuantity",width : 60,resizable : true,sortable : false}, 
					{name : "snCode",width : 120,resizable : true,sortable : false},
					{name : "trackingNo1",width : 120,	resizable : true,sortable : false},
					{name : "trackingNo",width : 120,resizable : true,	sortable : false},
					{name : "isBkcheckString",hidden : true}, 
					{name : "customerId",hidden : true}, 
					{name : "snType",hidden : true},
					{name : "spType",hidden : true}
		],
		caption : i18("WAITTING_CHECKED_LIST"),//待核对列表
		sortname : 'sta.pg_index',
		multiselect : false,
		sortorder : "asc",
		width : "auto",
		height : "auto",
		rowNum : -1,
		viewrecords : true,
		jsonReader : {repeatitems : false,	id : "0"},
		loadComplete : function() {
			loxia.initContext($j("#tbl-sta-list"));
			var postData = {};
			
			
			$j("#tbl-sta-list tr:gt(0)>td[aria-describedby$='barCode']").each(function(i, tag) {postData["skuBarcodes["+ i + "]"] = $j(tag).html();});
			barcodeList = loxia.syncXhr($j("body").attr("contextpath")	+ "/findskuBarcodeList.json",postData);
		
		var data = $j("#tbl-sta-list").jqGrid('getDataIDs');
			for(var i = 0;i<data.length;i++){
				var datas = $j("#tbl-sta-list").jqGrid('getRowData',data[i]);
				var spType = $j("#tbl-sta-list tr[id="+datas["id"]+"] td[aria-describedby$='spType']").html();
				var barcode = $j("#tbl-sta-list tr[id="+datas["id"]+"] td[aria-describedby$='barCode']").html();
				if(spType == '10'){
					barCodesList1.push(barcode);//run
				}
			}
			
		}
	});
	
	$j("#idSlipCode").keydown(function(evt) {
		if (evt.keyCode === 13) {
			evt.preventDefault();
			var slipCode = $j("#idSlipCode").val();
			if (slipCode == $j("#tbSlipCode").html()	|| slipCode == $j("#tbStaCode").html()) {
				$j("#txtTkNo").focus();
				var isPostpositionExpressBill = $j("#isPostpositionExpressBill").val();
				if("1" == isPostpositionExpressBill){
					$j("#confirmBarCode").focus();
					var isPostpositionPackingPage = $j("#isPostpositionPackingPage").val();
					if("1" != isPostpositionPackingPage){
						$j("#btnConfirm").trigger("click");
					}
				}
				$j("#tknoErrorMsg").html("");
			}else if(slipCode == "RPHOTO"){
				$j("#newCamera").click();
				$j("#idSlipCode").val("");
			} else {
				$j("#idSlipCode").val("");
				$j("#tknoErrorMsg").html("作业单不正确");
			}
		}
	});

	$j("#skuBarCode").keydown(function(evt) {
		
		var barCode2 = $j("#skuBarCode").val();
		 var isBarCodesList =false;
		//alert(barCodesList);//run
		$j.each(barCodesList1, function(i){
			if(barCode2 ==barCodesList1[i]){
				isBarCodesList=true;
				$j("#skuBarCode").val("");
				jumbo.showMsg("请输入SN");
				return;
			}
			});
		if(isBarCodesList){
			return;
		}
		
		if (evt.keyCode === 13) {
			evt.preventDefault();
			var barcode = $j("#skuBarCode").val().trim();
			//星巴克星享卡解密
			if(barcode.substring(0,4)=="SQR|"){
				try{
					barcode="6"+Decrypt(barcode.substring(4));
				}catch (e) {
					jumbo.showMsg("解密失败！02|Invalid Key");//解密失败
					return;
				};
				if(barcode.length!=16){
					jumbo.showMsg("解密后的星享卡长度不为16！02|Invalid Key");//长度不为16
					return;
				}
				$j("#skuBarCode").val(barcode);
			}		
			var pNo = $j("#verifyCode").html();
			$j("#tknoErrorMsg").html("");
			var bc = barcode.split('=');
			if(bc.length > 0){
				barcode = bc[0];
				$j("#skuBarCode").val(bc[0]);
			}
			if (barcode == "") {
				return;
			}
			var data = $j("#tbl-sta-list").jqGrid('getRowData');
			var isChecked = false;
			var stal = "";
			var staNo = "";
			$j.each(data,function(i, stal1) {
				stal = stal1;
				staNo = stal["staCode"];
				if(stal["snType"] != "3"){
					if (stal["trackingNo"] != ERROR	&& stal["trackingNo"] != ERROR_CANCEL) {
						if (!isChecked	&& stal["quantity"] != stal["completeQuantity"]) {
							if (stal["barCode"] == barcode|| stal["barCode"] == "00"+ barcode) {
								isChecked = true;
								return false;
							} else {
								var bc1 = stal["barCode"];
								for (var v in barcodeList[bc1]) {
									if (barcodeList[bc1][v] == barcode|| barcodeList[bc1][v] == ('00' + barcode)) {
										isChecked = true;
										return false;
									}
								}
							}
						}
					}															
				}
			});
			
			
			var starbucksDetail=loxia.syncXhr($j("body").attr("contextpath") + "/findStarbucksDetail.json",{"starbuckCode":stal["staCode"]});
			if(starbucksDetail.owner=="星巴克官方旗舰店"){
				$j("#icePackageLabel").show();
				$j("#icePackage").show();
				for(var i = 0;i<starbucksIcePackage.sList.length;i++){
					if(starbucksDetail.reCity.indexOf(starbucksIcePackage.sList[i].region)>=0){
						$j("#icePackage").html(starbucksIcePackage.sList[i].num);
					}
				}
			}else{
				$j("#icePackageLabel").hide();
				$j("#icePackage").hide();
			}
			
			
			if (isChecked) {
				var timestamp=new Date().getTime();
				try{
					cameraPhoto(pNo, staNo, barcode,timestamp);//拍照
				} catch (e) {
					//jumbo.showMsg(i18("拍照失败！"));
				};
				if (stal["isSnSku"] == '1') {
					$j(document).scrollTop($j(document).scrollTop() - 1);
					$j("#opencvImgMultiDiv").show();
					$j("#snDiv").show();
					$j("#snCode").focus();
					lineStal = stal;
					fileUrl = dateValue +"/" +ouName+ "/" + pNo + "/" + staNo+"/"+barcode + "_" + userName+"_"+timestamp;
					document.getElementById('camerImgMultiButton').click();
					$j(document).scrollTop($j(document).scrollTop() + 1);
					$j("#opencvImgDiv").dialog("close");
					$j("#opencvImgDiv").dialog("open");
					return;
				} else {
					playMusic($j("body").attr("contextpath") +"/recording/"+stal["pgIndex"]+".wav");
					$j("#opencvImgMultiDiv").hide();
					$j("#snDiv").hide();
					$j("#tbSlipCode").html(stal["staSlipCode"]);
					$j("#tbStaCode").html(stal["staCode"]);
					$j("#tbPgIndex").html(stal["pgIndex"]);
					var resultss = loxia.syncXhr($j("body").attr("contextpath")+ "/findpaperSkuByBarCode.json", {"barcode" : barcode,"staId":stal["id"]});
					if(null!=resultss&&resultss.result=="success"&&null!=resultss.msg){
						$j("#recommandSku").html(resultss.msg);
					}else{
						$j("#recommandSku").html(stal["packageBarCode"]);
					}
					
					$j("#hidStaId").val(stal["id"]);
					$j("#txtTkNo").val("");
					$j("#txtTkNo").attr("sf",stal["trackingNo1"]);
					$j("#dialog_msg").dialog("open");
					$j("#idSlipCode").val("");
					$j("#idSlipCode").focus();
					var isPostpositionPackingPage = $j("#isPostpositionPackingPage").val();
					var isPostpositionExpressBill = $j("#isPostpositionExpressBill").val();
					if('1' == isPostpositionPackingPage){
						$j("#idSlipCode").val(stal["staSlipCode"]);
						$j("#txtTkNo").focus();
					}
					if('1' == isPostpositionExpressBill){
						$j("#txtTkNo").val(stal["trackingNo1"]);
					}
					if('1' == isPostpositionPackingPage && '1' == isPostpositionExpressBill){
						$j("#confirmBarCode").focus();
					}
					if('0' == isPostpositionPackingPage && '0' == isPostpositionExpressBill){
						$j("#idSlipCode").focus();
					}
					fileUrl = dateValue + "/" + ouName + "/" + pNo + "/" + staNo + "/" + barcode + "_" + userName + "_" + timestamp;
					document.getElementById('camerImgButton').click();
					return;
				}
			} else {
				var isbkcheck = $j("#isBkCheckInteger").val();
				if(isbkcheck == "1") {
					//后端处理卡号
					var result = "";
					var isbCheck = false;
					result = loxia.syncXhr($j("body").attr("contextpath")+ "/findBarcodeBySn.json", {"sn" : barcode});
					if(result && result.result == "error") {
						//没有SN号 判断卡号是否正确
						var postData = {};
						$j.each(data,function(i, stal1) {
							stal = stal1;
							if (stal["trackingNo"] != ERROR	&& stal["trackingNo"] != ERROR_CANCEL) {
								if(stal['isBkcheckString'] == '1') {
									//获取所有是后端处理作业单的SKU_CODE
									postData["barCodeList[" + i + "]"] = stal1['barCode']+","+stal1['customerId'];
									isbCheck = true;
								}
							}
						});
						if(isbCheck) {
							postData['sn'] = barcode;
							var bar = loxia.syncXhr($j("body").attr("contextpath")+ "/formatCardToSn.json", postData);
							if (bar && bar.result == "success") {
								if(bar.rs == "snok"){
									result = loxia.syncXhr($j("body").attr("contextpath")+ "/findBarcodeBySn.json", {"sn" : barcode});
								}else{
									//卡号不正确
									playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
									errorTipOK(bar.msg);
									$j("#skuBarCode").val("");	
									initCheck();
									return;
								}
							}else{
								playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
								jumbo.showMsg("系统异常");
								$j("#skuBarCode").val("");	
								initCheck();
								return;
							}
						}else{
							playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
							errorTipOK(barcode + " 不在计划核对量中");
							$j("#skuBarCode").val("");
							initCheck();
							return;	
						}
					}
					if (result && result.result == "success") {
						//有SN号
						if(result.snType == 'ok') {//验证是不是无条码SKU商品
							//$j("#skuBarCode").val(result.barcode);
							//$j("#snCode").val(result.sn);
							var postData = {};
							postData['sn'] = result.sn;
							var rs = loxia.syncXhr($j("body").attr("contextpath")+ "/checkSnStatus.json",postData);
							//验证卡号状态
							if (rs && rs.result && rs.result == "success") {
								//卡状态没问题
								//判断此卡号是否在列表中有对应的STA
								var rs1 = loxia.syncXhr($j("body").attr("contextpath")+ "/getStaIdBySnStv.json",postData);
								if (rs1 && rs1.result && rs1.result == "success") {
									var staid = rs1.staid;
									if(staid == 'null'){
										var cfCheck = false;
										//没有绑定对应stv
										$j.each(data,function(i, stal1) {
											stal = stal1;
											staNo = stal["staCode"];
											if (stal["trackingNo"] != ERROR	&& stal["trackingNo"] != ERROR_CANCEL) {
												if(stal['isBkcheckString'] == '1'){
													postData['sta.id'] = stal['id'];
													//判断次作业单是否已经绑定SN号
													var stvrs = loxia.syncXhr($j("body").attr("contextpath")+ "/checkStvBinding.json",postData);
													if(stvrs && stvrs.result && stvrs.result == "success"){
														if(stvrs.stv == 'null'){
															//过滤掉已经绑定过SN号的作业单
															if (!cfCheck	&& stal["quantity"] != stal["completeQuantity"]) {
																if (stal["barCode"] == result.barcode|| stal["barCode"] == "00"+ result.barcode) {
																	cfCheck = true;
																	return false;
																} else {
																	var bc1 = stal["barCode"];
																	for (var v in barcodeList[bc1]) {
																		if (barcodeList[bc1][v] == result.barcode|| barcodeList[bc1][v] == ('00' + result.barcode)) {
																			cfCheck = true;
																			return false;
																		}
																	}
																}
															}																																								
														}
													}
												}
											}
										});
										if (cfCheck) {
											$j(document).scrollTop($j(document).scrollTop() - 1);
											$j("#opencvImgMultiDiv").show();
		//																		$j("#snDiv").show();
		//																		$j("#snCode").focus();
											lineStal = stal;
											fileUrl = dateValue +"/" +ouName+ "/" + pNo + "/" + staNo+"/"+barcode + "_" + userName+"_"+timestamp;
											document.getElementById('camerImgMultiButton').click();
											$j(document).scrollTop($j(document).scrollTop() + 1);
											$j("#opencvImgDiv").dialog("close");
											$j("#opencvImgDiv").dialog("open");
											//卡片先绑定作业单
											postData['sta.id'] = stal['id'];
											var bd = loxia.syncXhr($j("body").attr("contextpath")+ "/cardBindingStv.json",postData);
											if (bd && bd.result && bd.result == "success") {
												//激活卡状态
												var kjh = loxia.syncXhr($j("body").attr("contextpath")+ "/activateCardStatus.json",postData);
												if (kjh && kjh.result && kjh.result == "success") {
													if(kjh.msg == 'ok'){
		//																					jumbo.showMsg("卡激活成功！");
														playMusic($j("body").attr("contextpath") +"/recording/"+stal["pgIndex"]+".wav");
														$j("#opencvImgMultiDiv").hide();
														$j("#snDiv").hide();
														$j("#tbSlipCode").html(stal["staSlipCode"]);
														$j("#tbStaCode").html(stal["staCode"]);
														$j("#tbPgIndex").html(stal["pgIndex"]);
														
														var resultSku = loxia.syncXhr($j("body").attr("contextpath")+ "/findpaperSkuByBarCode.json", {"barcode" : barcode,"staId":stal["id"]});
														if(null!=resultSku&&resultSku.result=="success"&&null!=resultSku.msg){
															$j("#recommandSku").html(resultSku.msg);
														}else{
															$j("#recommandSku").html(stal["packageBarCode"]);
														}
														
														
														$j("#hidStaId").val(stal["id"]);
														$j("#txtTkNo").val("");
														$j("#txtTkNo").attr("sf",stal["trackingNo1"]);
														$j("#dialog_msg").dialog("open");
														$j("#idSlipCode").val("");
														$j("#idSlipCode").focus();
														var isPostpositionPackingPage = $j("#isPostpositionPackingPage").val();
														var isPostpositionExpressBill = $j("#isPostpositionExpressBill").val();
														if('1' == isPostpositionPackingPage){
															$j("#idSlipCode").val(stal["staSlipCode"]);
															$j("#txtTkNo").focus();
														}
														if('1' == isPostpositionExpressBill){
															$j("#txtTkNo").val(stal["trackingNo1"]);
														}
														if('1' == isPostpositionPackingPage && '1' == isPostpositionExpressBill){
															$j("#confirmBarCode").focus();
														}
														if('0' == isPostpositionPackingPage && '0' == isPostpositionExpressBill){
															$j("#idSlipCode").focus();
														}
														fileUrl = dateValue +"/" +ouName+ "/" + pNo + "/" + staNo+"/"+barcode + "_" + userName+"_"+timestamp;
														document.getElementById('camerImgButton').click();
														return;			
													}else if(kjh.msg == 'ERRORNOTSN'){
														jumbo.showMsg("SN号缺失绑定关系！");
														$j("#skuBarCode").val("");	
														initCheck();
														return;
													}else{
														jumbo.showMsg("卡激活失败！");
														$j("#skuBarCode").val("");	
														printSnCardErrorList(stal['staCode']);
														errorTipOK("存在卡号激活失败，补拣货单已打印，请至激活失败卡券界面处理！");
														initCheck();
														return;
													}
												}else{
													 if(kjh.msg == 'unusual'){
														jumbo.showMsg("请使用该卡重新刷卡");
														$j("#skuBarCode").val("");	
														initCheck();
														return;
													}
													playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
													jumbo.showMsg("系统异常");
													$j("#skuBarCode").val("");	
													initCheck();
													return;
												}
											}else{
												playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
												jumbo.showMsg("系统异常");
												$j("#skuBarCode").val("");	
												initCheck();
												return;
											}
										}else{
											playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
											errorTipOK(barcode + " 不在计划核对量中");
											$j("#skuBarCode").val("");	
											initCheck();
											return;
										}
									}else{
										var ydCheck = false;
										$j.each(data,function(i, stal1) {
											if (stal["trackingNo"] != ERROR	&& stal["trackingNo"] != ERROR_CANCEL) {
												if (!ydCheck && stal["quantity"] != stal["completeQuantity"]) {
													if(stal1['id'] == staid){
														stal = stal1;
														staNo = stal["staCode"];
														ydCheck = true;
														return false;
													}
												}
											}
										});
										if(ydCheck){
											$j(document).scrollTop($j(document).scrollTop() - 1);
											$j("#opencvImgMultiDiv").show();
											//$j("#snDiv").show();
											//$j("#snCode").focus();
											lineStal = stal;
											fileUrl = dateValue +"/" +ouName+ "/" + pNo + "/" + staNo+"/"+barcode + "_" + userName+"_"+timestamp;
											document.getElementById('camerImgMultiButton').click();
											$j(document).scrollTop($j(document).scrollTop() + 1);
											$j("#opencvImgDiv").dialog("close");
											$j("#opencvImgDiv").dialog("open");
											//激活卡状态
											//激活卡状态
											postData['sta.id'] = stal['id'];
											var kjh = loxia.syncXhr($j("body").attr("contextpath")+ "/activateCardStatus.json",postData);
											if (kjh && kjh.result && kjh.result == "success") {
												if(kjh.msg == 'ok'){
													
													var timestamp=new Date().getTime();
													try{
														cameraPhoto(pNo, staNo, barcode,timestamp);//拍照
													} catch (e) {
														//jumbo.showMsg(i18("拍照失败！"));
													};
													
													playMusic($j("body").attr("contextpath") +"/recording/"+stal["pgIndex"]+".wav");
													$j("#opencvImgMultiDiv").hide();
													$j("#snDiv").hide();
													$j("#tbSlipCode").html(stal["staSlipCode"]);
													$j("#tbStaCode").html(stal["staCode"]);
													$j("#tbPgIndex").html(stal["pgIndex"]);
													
													var resultBarCode = loxia.syncXhr($j("body").attr("contextpath")+ "/findpaperSkuByBarCode.json", {"barcode" : barcode,"staId":stal["id"]});
													if(null!=resultBarCode&&resultBarCode.result=="success"&&null!=resultBarCode.msg){
														$j("#recommandSku").html(resultBarCode.msg);
													}else{
														$j("#recommandSku").html(stal["packageBarCode"]);
													}
													
													$j("#hidStaId").val(stal["id"]);
													$j("#txtTkNo").val("");
													$j("#txtTkNo").attr("sf",stal["trackingNo1"]);
													$j("#dialog_msg").dialog("open");
													$j("#idSlipCode").val("");
													$j("#idSlipCode").focus();
													var isPostpositionPackingPage = $j("#isPostpositionPackingPage").val();
													var isPostpositionExpressBill = $j("#isPostpositionExpressBill").val();
													if('1' == isPostpositionPackingPage){
														$j("#idSlipCode").val(stal["staSlipCode"]);
														$j("#txtTkNo").focus();
													}
													if('1' == isPostpositionExpressBill){
														$j("#txtTkNo").val(stal["trackingNo1"]);
													}
													if('1' == isPostpositionPackingPage && '1' == isPostpositionExpressBill){
														$j("#confirmBarCode").focus();
													}
													if('0' == isPostpositionPackingPage && '0' == isPostpositionExpressBill){
														$j("#idSlipCode").focus();
													}
													fileUrl = dateValue +"/" +ouName+ "/" + pNo + "/" + staNo+"/"+barcode + "_" + userName+"_"+timestamp;
													document.getElementById('camerImgButton').click();
													return;
												}else if(kjh.msg == 'ERRORNOTSN'){
													jumbo.showMsg("SN号缺失绑定关系！");
													$j("#skuBarCode").val("");	
													initCheck();
													return;
												}else{
													jumbo.showMsg("卡激活失败！");
													$j("#skuBarCode").val("");	
													printSnCardErrorList(stal['staCode']);
													errorTipOK("存在卡号激活失败，补拣货单已打印，请至激活失败卡券界面处理！");
													initCheck();
													return;
												}
											}else{
												 if(kjh.msg == 'unusual'){
													jumbo.showMsg("请使用该卡重新刷卡");
													$j("#skuBarCode").val("");	
													initCheck();
													return;
												}
												playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
												jumbo.showMsg("系统异常");
												$j("#skuBarCode").val("");	
												initCheck();
												return;
											}
										}else{
											//其他批次绑定了此卡
											playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
											errorTipOK("【"+barcode+"】" + " 卡片已被作业单：【"+rs1.stacode+"】 绑定使用！");
											$j("#skuBarCode").val("");
											initCheck();
											return;	
										}
									}
								}else{
									jumbo.showMsg("系统异常");
									initCheck();
									return;
								}
							}else{
								playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
								errorTipOK(rs.msg);
								$j("#skuBarCode").val("");
								initCheck();
								return;
							}
						}else{
							playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
							errorTipOK(barcode + " 不在计划核对量中");
							$j("#skuBarCode").val("");
							initCheck();
							return;													
						}
					}
				}else{
					playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
					errorTipOK(barcode + " 不在计划核对量中");
					$j("#skuBarCode").val("");											
					}
				}
			}
		});

	$j("#camerImgButton").click(function() {
		try{
			cameraPaintJs(fileUrl);
		} catch (e) {
			//jumbo.showMsg(i18("重拍失败！"));
		};
	});
	
	$j("#camerImgMultiButton").click(function() {
		try{
			cameraPaintMultiJs(fileUrl);
		} catch (e) {
			//jumbo.showMsg(i18("重拍失败！"));
		};
	});			
	
	$j("#btnSnCardError").click(function() {
		printSnCardErrorList("");
	});			
	//重拍
	$j("#newCamera").click(function() {
		var pNo = $j("#verifyCode").html();
		var staNo = $j("#tbStaCode").html();
		var timestamp=new Date().getTime();	
		try{
			cameraPhotoJs(dateValue +"/" +ouName+ "/" + pNo + "/" + staNo, userName+"_"+timestamp);
		} catch (e) {
			//jumbo.showMsg(i18("重拍失败！"));
		};
		fileUrl = dateValue +"/" +ouName+ "/" + pNo + "/" + staNo+"/"+userName+"_"+timestamp;
		fileName += userName+"_"+timestamp+".jpg"+"/";
		document.getElementById('camerImgButton').click();
		$j("#dialog_msg").dialog("close");
		$j("#dialog_msg").dialog("open");
		$j("#idSlipCode").focus();
		//loxia.syncXhr($j("body").attr("contextpath") + "/creatStvCheckImg.json",{"staCode":staNo,"fileUrl":"/"+dateValue + "/" + pNo + "/" + staNo});//保存STV图片路劲
		//creatZip(dateValue + "/" + pNo + "/" + staNo);
	});
	
	//执行核对
	$j("#btnConfirm").click(function() {
				//作业单号、相关单据号 fanht
					if ($j("#idSlipCode").val().trim() == "") {
						$j("#tknoErrorMsg").html("请输入相关单据号/作业单号 ");
						$j("#idSlipCode").focus();
						return;
					}
					//快递单号 fanht
					if ($j("#txtTkNo").val().trim() == "") {
						$j("#tknoErrorMsg").html("请输入快递单号");
						$j("#txtTkNo").focus();
						return;
					} else {
						var tkNo = $j("#txtTkNo").val().trim();
						var sftkno = $j("#txtTkNo").attr("sf");
						if (sftkno != "" && sftkno != tkNo) {
							$j("#txtTkNo").focus();
							$j("#txtTkNo").val("");
							$j("#tknoErrorMsg").html("非正确的快递单号!");
							return;
						}
					}
					var pNo = $j("#verifyCode").html();
					var timestamp=new Date().getTime();	
					//作业单检查
					var slipCode = $j("#idSlipCode").val();
					if (slipCode != $j("#tbSlipCode").html()	&& slipCode != $j("#tbStaCode").html()) {
						$j("#idSlipCode").focus();
						$j("#idSlipCode").val("");
						$j("#tknoErrorMsg").html("作业单不正确");
						return;
					}
					var postData = getCheckPostData();
					if (postData != null) {
						var rs = loxia.syncXhr($j("body").attr("contextpath")+ "/checkSingleCheckStaList.json",postData);
						if (rs && rs.result && rs.result == "success") {
							var isPostpositionPackingPage = $j("#isPostpositionPackingPage").val();
							var isPostpositionExpressBill = $j("#isPostpositionExpressBill").val();
							if('1' == isPostpositionPackingPage){
								postPrintPackingPage();
							}
							if('1' == isPostpositionExpressBill){
								postPrintExpressBill();
							}
							var data = $j("#tbl-sta-list").jqGrid("getRowData",$j("#hidStaId").val());
							data["trackingNo"] = $j("#txtTkNo").val().trim();
							data["snCode"] = $j("#snCode").val().trim();
							//核对成功 增加数量 fanht
							data["completeQuantity"] = data["completeQuantity"] == null ? 1: (data["completeQuantity"] + 1);
							$j("#tbl-sta-list").jqGrid('setRowData',$j("#hidStaId").val(), data);
							loxia.initContext($j("#tbl-sta-list"));
							$j("#dialog_msg").dialog("close");
							$j("#confirmBarCode").val("");
							//判断是否处理完
							if (isCheckedFinish()) {
								$j("#idBack").trigger("click");
							}
						} else {
							var data = $j("#tbl-sta-list").jqGrid("getRowData",$j("#hidStaId").val());
							if (rs && rs.isCancel == true) {
								data["trackingNo"] = ERROR_CANCEL;
							} else {
								data["trackingNo"] = ERROR;
							}
							$j("#tbl-sta-list").jqGrid('setRowData',$j("#hidStaId").val(), data);
							loxia.initContext($j("#tbl-sta-list"));
							$j("#dialog_msg").dialog("close");
							$j("#confirmBarCode").val("");
							if (rs && rs.msg) {
								//$j("#errorMsg").html(rs.msg);
								$j("#error_text_ok").html("<font style='text-align:center;font-size:36px' color='red'>"+ rs.msg+ "</font>");
							} else {
								$j("#error_text_ok").html("<font style='text-align:center;font-size:36px' color='red'>"+ "核对异常"+ "</font>");
								//$j("#confirmBarCode1").html("核对异常"); 
							}
							//$j("#dialog_error").dialog("open");
							$j("#dialog_error_ok").dialog("open");
							//$j("#confirmBarCode1").focus();
							isLineNoError = rs.msg;
						}
					}
					//初始化SN
					initCheck();
				});

	$j("#confirmBarCode").keyup(function(evt) {
		if (BARCODE_CONFIRM == $j("#confirmBarCode").val().trim()) {
			$j("#btnConfirm").trigger("click");
		}
	});

	$j("#confirmBarCode4").keyup(
			function(evt) {
				if (BARCODE_CONFIRM == $j("#confirmBarCode4").val().trim()) {
					$j("#btnConfirm4").trigger("click");
				}
			});

	$j("#confirmBarCode1").keyup(
			function(evt) {
				if (BARCODE_CONFIRM == $j("#confirmBarCode1").val().trim()) {
					$j("#btnConfirm1").trigger("click");
				}
			});

	//错误确认
	$j("#btnConfirm1").click(function() {
		$j("#dialog_error").dialog("close");
		$j("#confirmBarCode1").val("");
		if (isCheckedFinish()) {
			$j("#idBack").trigger("click");
		}
		if (isLineNoError == "流水开票号未找到") {
			jumbo.removeAssemblyLineNo();
			lineNo = jumbo.getAssemblyLineNo();
			isLineNoError = "";
		}
	});

	//完成确认
	$j("#btnConfirm4").click(function() {
		$j("#dialog_success_msg").dialog("close");
		$j("#confirmBarCode4").val("");
		if (isCheckedFinish()) {
			$j("#idBack").trigger("click");
		}
	});

	//核对快递单号
	$j("#txtTkNo").keydown(
					function(evt) {
						if (evt.keyCode === 13) {
							var tkNo = $j("#txtTkNo").val().trim();
							if (tkNo != "") {
								var sftkno = $j("#txtTkNo").attr("sf");
								if (sftkno != "" && sftkno != tkNo) {
									isExists = true;
									$j("#tknoErrorMsg").html("非正确的快递单号!");
									return;
								}
								
								//获取配货清单编码 || 运单号，不能输入OK条码、配货清单编码以及订单相关单据号。
							    var staCode=$j("#tbStaCode").html(); 
							    var pl = loxia.syncXhrPost($j("body").attr("contextpath")+ "/findSlipCodeAndPickingListCodeByStaCode.json", {"code" : staCode});
							    if(tkNo!="" && tkNo==pl["pls"]["supplierCode"] || tkNo==pl["pls"]["slipCode"] || tkNo.toUpperCase()=="OK".toUpperCase()){
							    	$j("#tknoErrorMsg").html("非正确的快递单号!");
							    	return;
							    }
								
								var data = {	trackingNo : $j("#txtTkNo").val().trim()
								};
								data["sta.id"] = $j("#hidStaId").val();
								var rs = loxia.syncXhr($j("body").attr("contextpath")	+ "/checktrackingno.json",data);
								if (rs && rs.result == 'success') {
									//判断是否存在重复
									var allData = $j("#tbl-sta-list")	.jqGrid('getRowData');
									var isExists = false;
									$j.each(allData,function(i,stal) {
														if (stal["trackingNo"] == tkNo) {
															isExists = true;
															$j("#txtTkNo").val("");
															$j("#tknoErrorMsg").html("快递单号已经被扫描 ，箱号："	+ stal["pgIndex"]);}
													});
									if (!isExists) {
										$j("#confirmBarCode").focus();
										$j("#tknoErrorMsg").html("");
									}
								} else {
									$j("#tknoErrorMsg").html("快递单号格式不正确");
									$j("#txtTkNo").val("");
								}
							}
							//isCheckedFinish();
						}
					});

	$j("#btnTryError").click(function() {
		var data = $j("#tbl-sta-list").jqGrid('getRowData');
		var isFound = false;
		var pNo = $j("#verifyCode").html();
		$j.each(data, function(i, stal) {
			if (!isFound && stal["trackingNo"] == ERROR) {
				var staNo = stal["staCode"];
				var barcode = stal["barCode"];
				var timestamp=new Date().getTime();
				try{
						cameraPhoto(pNo, staNo, barcode,timestamp);//拍照
				} catch (e) {
//												jumbo.showMsg(i18("拍照失败！"));//初始化系统参数异常
				};
				//显示核对
				if (stal["isSnSku"] == '1') {
					$j(document).scrollTop($j(document).scrollTop() - 1);
					$j("#skuBarCode").val(stal["barCode"]);
					$j("#opencvImgMultiDiv").show();
					$j("#snDiv").show();
					$j("#snCode").focus();
					lineStal = stal;
					fileUrl = dateValue +"/" +ouName+ "/" + pNo + "/" + staNo+"/"+barcode + "_" + userName+"_"+timestamp;
					document.getElementById('camerImgMultiButton').click();
					$j(document).scrollTop($j(document).scrollTop() + 1);
					$j("#opencvImgDiv").dialog("close");
					$j("#opencvImgDiv").dialog("open");
					$j("#skuBarCode").focus();
					return;
				} else {
					$j("#opencvImgMultiDiv").hide();
					$j("#snDiv").hide();
					$j("#tbSlipCode").html(stal["staSlipCode"]);
					$j("#tbStaCode").html(stal["staCode"]);
					$j("#tbPgIndex").html(stal["pgIndex"]);
					var resultError = loxia.syncXhr($j("body").attr("contextpath")+ "/findpaperSkuByBarCode.json", {"barcode" : barcode,"staId":stal["id"]});
					if(null!=resultError&&resultError.result=="success"&&null!=resultError.msg){
						$j("#recommandSku").html(resultError.msg);
					}else{
						$j("#recommandSku").html(stal["packageBarCode"]);
					}
					
					$j("#hidStaId").val(stal["id"]);
					$j("#txtTkNo").val("");
					$j("#txtTkNo").attr("sf", stal["trackingNo1"]);
					$j("#dialog_msg").dialog("open");
					$j("#idSlipCode").val("");
					$j("#idSlipCode").focus();
					fileUrl = dateValue +"/" +ouName+ "/" + pNo + "/" + staNo + "/" +barcode + "_" + userName+"_"+timestamp;
					document.getElementById('camerImgButton').click();
					return;
				}
			}
		});
	});

	//快速核对进去详细页
	$j("#quickPl").keydown(function(evt) {
		if (evt.keyCode === 13) {
			var plcode = $j("#quickPl").val().trim();
			if (plcode == "") {
				jumbo.showMsg("输入单据号码不能为空!");
				return;
			}
			showDetail(null);
		}
	});

	//返回
	$j("#idBack").click(function() {
		try{
			closeGrabberJs();				
		}catch (e) {
//						jumbo.showMsg(i18("关闭摄像头失败！"));//初始化系统参数异常
		}
		$j("#checkDiv").addClass("hidden");
		$j("#plDiv").removeClass("hidden");
		var trslength= $j("#tbl-trank-button").find("tr").length;
		for(var i=trslength;i>=1;i--) //保留最前面两行！
		{
			$j("#tbl-trank-button").find("tr").eq(i).remove();
		}
		$j("#tbl-trank-button").addClass("hidden");
		$j("#quickPl").focus();
	});

	//关闭核对页面
	$j("#close").click(function() {
		$j("#dialog_msg").dialog("close");
		initCheck();
		$j("#skuBarCode").focus();
	});

	//扫描SN号后
	$j("#snCode").keydown(function(evt) {
		if (evt.keyCode === 13) {
			evt.preventDefault();
			checkSn();
		}
	});
	$j("#printPackingPage").click(function(){
		var staId = $j("#hidStaId").val();
		if(null == staId || "" == staId){
//						playMusic("/recording/staError.mp3");
			playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
			jumbo.showMsg("参数异常");
			return false;
		}
		var dt = new Date().getTime();
		var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?rt="+ dt +"&sta.id=" + staId;
		printBZ(loxia.encodeUrl(url),false);
	});
	$j("#printExpressBill").click(function(){
		var plId = $j("#plId").val();
		if(null == plId || "" == plId){
//						playMusic("/recording/staError.mp3");
			playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
			jumbo.showMsg("参数异常");
			return false;
		}
//					var url = $j("body").attr("contextpath") + "/printdeliveryinfomode1.json?plCmd.id=" + plId;
//					printBZ(loxia.encodeUrl(url),false);
//					var plId = $j("#plId").val();
//					if(null == plId || "" == plId){
//						jumbo.showMsg("参数异常");
//						return false;
//					}
//					var url = $j("body").attr("contextpath") + "/printdeliveryinfomode1.json?plCmd.id=" + plId;
//					printBZ(loxia.encodeUrl(url),false);	
		postPrintExpressBill();
	});
	//跳过，不处理
	$j("#jump").click(function(){
		$j("#tbl-trank-button").removeClass("hidden");
		var skuName;
		var skuCode;
		var quantity;
		var alge=false;
		var staCode=$j("#tbStaCode").html();
		var data = $j("#tbl-sta-list").jqGrid('getRowData');
		$j.each(data,function(i, stal) {
			if (stal["staCode"]==staCode) {
				skuName=stal["skuName"];
				skuCode=stal["skuCode"];
				quantity=stal["quantity"];
				stal["completeQuantity"] = 1;
				$j("#tbl-sta-list").jqGrid('setRowData',stal.id,stal);
			}
		});
		$j("#tbl-trank-button").find("tr").each(function () {  
			if($j(this).find("td:eq(0)").text()==staCode){
				alge=true;
			}
		});
		if(alge==false){
            var str = "<tr id=\""+staCode+"\"><td style='text-align: center;'>"+staCode+"</td><td>" +skuCode+"</td><td>" +skuName+"</td><td>" +quantity+"</td><td>跳过,不处理</td></tr>";//删除	
					 $j("#tbl-trank-button").append(str);
					 $j("#skuBarCode").val("");
					 $j("#dialog_msg").dialog("close");
		}else{
			jumbo.showMsg("此单"+staCode+"已经跳过");
			 $j("#skuBarCode").val("");
			$j("#dialog_msg").dialog("close");
		}
	     
	});
	
	//星巴克刷SN卡
	$j("#getstarbucksSncode").click(function() {
		var snCode  = window.top.document.getElementById("starbucksSnCode").value;
		if(snCode!=null&&snCode!=''){
			$j("#skuBarCode").val(snCode.split("=")[0]);
		}
	});
});

//SN判断
function checkSn() {
	var stal = lineStal;
	var bc = $j("#snCode").val().trim();
	var pNo = $j("#verifyCode").html();
	var staNo = lineStal["staCode"];
	
	var starbucksDetail2=loxia.syncXhr($j("body").attr("contextpath") + "/findStarbucksDetail.json",{"starbuckCode":staNo});
	if(starbucksDetail2.owner=="星巴克官方旗舰店"){
		$j("#icePackageLabel").show();
		$j("#icePackage").show();
		for(var i = 0;i<starbucksIcePackage.sList.length;i++){
			if(starbucksDetail2.reCity.indexOf(starbucksIcePackage.sList[i].region)>=0){
				$j("#icePackage").html(starbucksIcePackage.sList[i].num);
			}
		}
	}else{
		$j("#icePackageLabel").hide();
		$j("#icePackage").hide();
	}
	
	
	
	if (bc == "") {
		return;
	}
	//SN号校验
	var result = loxia.syncXhr($j("body").attr("contextpath")+ "/findBarcodeBySn.json", {"sn" : bc	});
	if (result && result.result == "success") {
		if (result.barcode == stal["barCode"]) {
			//星巴克MSR卡出库校验
			if(result.snTypeMSR == 7 &&result.staId != stal["id"]){
				jumbo.showMsg("该SN号绑定的作业单和当前作业单不匹配");
				initCheck(); 
				return;
			}
			var timestamp=new Date().getTime();
			try{
				cameraPhoto(pNo, staNo, bc,timestamp);//拍照
			} catch (e) {
//				jumbo.showMsg(i18("拍照失败！"));//初始化系统参数异常
			};
//			playMusic("/recording/"+stal["pgIndex"]+".mp3");
			playMusic($j("body").attr("contextpath") +"/recording/"+stal["pgIndex"]+".wav");
			$j("#tbSlipCode").html(stal["staSlipCode"]);
			$j("#tbStaCode").html(stal["staCode"]);
			$j("#tbPgIndex").html(stal["pgIndex"]);
			
			var resultSlipCode = loxia.syncXhr($j("body").attr("contextpath")+ "/findpaperSkuByBarCode.json", {"barcode" : stal["barCode"],"staId":stal["id"]});
			if(null!=resultSlipCode&&resultSlipCode.result=="success"&&null!=resultSlipCode.msg){
				$j("#recommandSku").html(resultSlipCode.msg);
			}else{
				$j("#recommandSku").html(stal["packageBarCode"]);
			}
			
			$j("#hidStaId").val(stal["id"]);
			$j("#hidSn").val(bc);
			$j("#txtTkNo").val("");
			$j("#txtTkNo").attr("sf", stal["trackingNo1"]);
			$j("#opencvImgMultiDiv").hide();
			$j("#dialog_msg").dialog("open");
			$j("#idSlipCode").val("");
			$j("#idSlipCode").focus();
			var isPostpositionPackingPage = $j("#isPostpositionPackingPage").val();
			var isPostpositionExpressBill = $j("#isPostpositionExpressBill").val();
			if('1' == isPostpositionPackingPage){
				$j("#idSlipCode").val(stal["staSlipCode"]);
				$j("#txtTkNo").focus();
				//postPrintPackingPage();
			}
			if('1' == isPostpositionExpressBill){
				$j("#txtTkNo").val(stal["trackingNo1"]);
				//postPrintExpressBill();
			}
			if('1' == isPostpositionPackingPage && '1' == isPostpositionExpressBill){
				$j("#confirmBarCode").focus();
			}
			if('0' == isPostpositionPackingPage && '0' == isPostpositionExpressBill){
				$j("#idSlipCode").focus();
			}
			fileUrl = dateValue +"/" +ouName+ "/" + pNo + "/" + staNo+"/"+bc + "_" + userName+"_"+timestamp;
			document.getElementById('camerImgButton').click();
		} else {
			//showErrorMsg(i18("SN对应的条形码和之前扫描的不匹配"));
			//errorTipOK(i18("SN对应的条形码和之前扫描的不匹配"));
			jumbo.showMsg("SN对应的条形码和之前扫描的不匹配");
			initCheck();
		}
	} else {
//		playMusic("/recording/barCodeError.mp3");
		playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
		//showErrorMsg(i18("SN号不存在"));
		//errorTipOK(i18("SN号不存在"));
		jumbo.showMsg("SN号不存在");
		initCheck();
		return;
	}
}
//初始化initCheck
function initCheck() {
	$j("#skuBarCode").val("");
	$j("#skuBarCode").focus();
	$j("#snDiv").hide();
	$j("#snCode").val("");
	$j("#hidSn").val("");
	$j("#closeCancel").focus(); 
	$j("#closeCancel").val("");
}

//拍照bin.hu
function cameraPhoto(pNo, staNo, barCode,timestamp) {
//	var timestamp=new Date().getTime();
	cameraPhotoJs(dateValue +"/" +ouName+ "/" + pNo + "/" + staNo, barCode + "_" + userName+"_"+timestamp);
	fileName += barCode + "_"+userName+"_"+timestamp+".jpg"+"/";
//	setTimeout(cameraPaintJs(dateValue + "/" + pNo + "/" + staNo+"/"+barCode + "_" + userName+"_"+timestamp),1000);
}
//后置打印面单
function postPrintExpressBill(){
	var staId = $j("#hidStaId").val();
	if(null == staId || "" == staId){
//		playMusic("/recording/staError.mp3");
		playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
		jumbo.showMsg("参数异常");
		return false;
	}
	var dt = new Date().getTime();
	var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?rt="+ dt +"&sta.id=" + staId;
	printBZ(loxia.encodeUrl(url),false);
}
//后置打印装箱清单
function postPrintPackingPage(){
	var plId = $j("#plId").val();
	var staId = $j("#hidStaId").val();
	if(null == staId || "" == staId || null == plId || "" == plId){
//		playMusic("/recording/staError.mp3");
		playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
		jumbo.showMsg("参数异常");
		return false;
	}
	var dt = new Date().getTime();
	var url = $j("body").attr("contextpath") + "/printtrunkpackinglistmode1Out.json?rt="+ dt +"&plCmd.id=" + plId + "&plCmd.staId=" + staId + "&isPostPrint=true";
	printBZ(loxia.encodeUrl(url),false);
}

//播放提示音
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

//激活失败拣货单补打
function printSnCardErrorList(code){
	var staCode = code;
	var plCode = $j("#verifyCode").html().trim();
	if(staCode != ""){
		plCode = "";
	}
	if(null == staCode && "" == staCode && null == plCode && "" == plCode){
		playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
		jumbo.showMsg("参数异常");
		return false;
	}
	loxia.lockPage();
	var url = $j("body").attr("contextpath") + "/printSnCardErrorList.json?staCode="+staCode+"&plCode="+plCode;
	printBZ(loxia.encodeUrl(url),false);
	loxia.unlockPage();
}


