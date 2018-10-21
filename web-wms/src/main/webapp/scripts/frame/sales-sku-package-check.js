var ERROR = "异常";
var barcodeList;
var ERROR_CANCEL = "取消";
$j.extend(loxia.regional['zh-CN'],{
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
var pgsku = {};
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function getFmtValue(tableId,rowId,column){
	return $j("#"+tableId+" tr[id="+rowId+"] td[aria-describedby='"+tableId+"_"+column+"']").attr("title");
}
var fileName = "";

function showDetail(obj){
	var plCode;
	fileName = "";
	if (obj == null) {
		plCode = $j("#quickPl").val();
	} else {
		plCode = $j(obj).text();
	}
	var pl = loxia.syncXhrPost($j("body").attr("contextpath")+ "/findSlipCodeByid.json", {"code" : plCode});
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
		$j("#verifyShipStaCount").html(pl['pl']['shipStaCount']);
		$j("#verifyCheckedSkuQty").html(pl['pl']['checkedSkuQty']);
		$j("#verifyShipSkuQty").html(pl['pl']['shipSkuQty']);
		
		var resultPsku = loxia.syncXhrPost(window.$j("body").attr("contextpath")
				+ "/getpackagemainsku.json?pid=" + row);
		if (resultPsku && resultPsku.result && resultPsku.result == 'success') {
			pgsku["barcode"] = resultPsku.barcode;
			pgsku["qty"] = resultPsku.qty;
			pgsku["skuid"] = resultPsku.skuid;
			pgsku["skuname"] = resultPsku.skuname;
		} 
		$j("#plDiv").addClass("hidden");
		$j("#checkDiv").removeClass("hidden");
		$j("#quickPl").val("");
		$j("#tbl-sta-list").jqGrid(
				'setGridParam',
				{
					url : loxia.getTimeUrl($j("body").attr("contextpath")+ "/getocpstalforpgskubypl.json"),	postData : {"plCmd.id" : row,pgSkuId : pgsku["skuid"]}
				}).trigger("reloadGrid");
		$j("#skuBarCode").focus();
		var date = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getWarehouseInfo.json");
		if(date.isMqInvoice){
			lineNo = jumbo.getAssemblyLineNo();
		}
	} else {
		jumbo.showMsg("未找到配货批次！");
		return;
	}
}
function isCheckedFinish(){
	var data = $j("#tbl-sta-list").jqGrid('getRowData');
	var isAllChecked = true;
	var isError = false;
	$j.each(data,function(i, stal){
		if(stal["trackingNo"] == ERROR){
			isError = true;
		}
		if(stal["trackingNo"] != ERROR && stal["trackingNo"] != ERROR_CANCEL ){
			if(isAllChecked && stal["quantity"] != stal["completeQuantity"]){
				isAllChecked = false;
			}
		}
	});
	if(!isError && isAllChecked){
		$j("#divAllChecked").removeClass("hidden");
		$j("#divAllChecked1").removeClass("hidden");
		return true;
	}else{
		$j("#divAllChecked").addClass("hidden");
		$j("#divAllChecked1").addClass("hidden");
		$j("#skuBarCode").focus();
		return false;
	}
}

function getCheckPostData(){
	var postdata = {};
//	var data = $j("#tbl-sta-list").jqGrid('getRowData');
//	var isError = false;
//	var pgIndex;
	postdata["sta.id"] = $j("#hidStaId").val();
	postdata["sta.trackingNo"] = $j("#txtTkNo").val();
	return postdata;
}

function showErrorMsg(msg){
	$j("#errorMsg").html(msg);
	$j("#dialog_error").dialog("open");
	$j("#confirmBarCode1").focus();
}


var stastatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.json",{"categoryCode":"whSTAStatus"});
var statype=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.json",{"categoryCode":"whSTAType"});
var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.json");

var lineNo;
var isLineNoError = "";
var userName = "";
var dateValue = "";
var fileUrl = "";
var ouName = "";
$j(document).ready(function (){
	userName = $j("#userinfo", window.parent.document).attr("lgname");
	dateValue = $j("#userinfo", window.parent.document).attr("sysdate");
	ouName = $j("#userinfo", window.parent.document).attr("ouName");
	$j("#dialog_msg").dialog({title: "提示信息", modal:true, autoOpen: false, width: 1000,closeOnEscape: false,open: function(event, ui){$j(".ui-dialog-titlebar-close").hide();}});
	$j("#dialog_error").dialog({title: "错误信息", modal:true, autoOpen: false, width: 650});
	$j("#dialog_success_msg").dialog({title: "提示信息", modal:true, autoOpen: false, width: 650,closeOnEscape: false,open: function(event, ui){$j(".ui-dialog-titlebar-close").hide();}});
	
	$j("#quickPl").focus();
	
	var plstatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"pickingListStatus"});
	if(!plstatus.exception){
		$j("#tbl-dispatch-list").jqGrid({
			url:$j("body").attr("contextpath") + "/findskupackagepickinglist.json",
			postData:loxia._ajaxFormToObj("plForm"),
			datatype: "json",//"已发货单据数","已发货商品件数",
			//colNames: ["ID","配货批次号","状态","计划配货单据数","已核对单据数","已发货单据数","计划配货商品件数","已核对商品件数","已发货商品件数","最后核对时间","最后发货时间","开始配货时间"],
			colNames: ["ID",i18("CODE"),i18("INTSTATUS"),i18("PLANBILLCOUNT"),i18("CHECKEDBILLCOUNT"),i18("SHIPSTACOUNT"),i18("PLANSKUQTY"),i18("CHECKEDSKUQTY"),i18("SHIPSKUQTY"),
				i18("CREATE_TIME"),i18("CHECKEDTIME"),i18("EXECUTEDTIME"),i18("PICKINGTIME")],
			colModel: [{name: "id", index: "pl.id", hidden: true},
						{name:"code",index:"pl.code",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:100,resizable:true},
						{name:"intStatus", index:"pl.status" ,width:40,resizable:true,formatter:'select',editoptions:plstatus},
						{name:"planBillCount", index:"pl.plan_bill_count",width:100,resizable:true},
						{name:"checkedBillCount", index:"pl.checked_bill_count", width:90, resizable:true},
						{name:"shipStaCount",index:"pl.send_bill_count",width:90,resizable:true},
						{name:"planSkuQty",index: "pl.plan_sku_qty",width:120,resizable:true},
						{name:"checkedSkuQty",index:"pl.checked_sku_qty",width:100,resizable:true},
						{name:"shipSkuQty",index:"pl.send_sku_count",width:100,resizable:true},
						{name:"createTime",index:"pl.CREATE_TIME",width:150,resizable:true},
						{name:"checkedTime",index:"pl.CHECK_TIME",width:150,resizable:true},
						{name:"executedTime",index:"pl.last_outbound_time",width:150,resizable:true},
		               {name:"pickingTime",index:"pl.PICKING_TIME",width:150,resizable:true}],
			caption: i18("PICKING_LIST"),//配货清单列表
		   	sortname: 'pl.CREATE_TIME',
		    multiselect: false,
			sortorder: "desc",
			pager:"#pager",
			width:"auto",
			height:"auto",
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			/*rowNum: "1",
			rowList:[1,4,12],*/
			height:jumbo.getHeight(),
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
	}else{
		jumbo.showMsg(i18("INIT_SYSTEM_DATA_EXCEPTION"));//初始化系统参数异常
	}
	$j("#search").click(function(){
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/findskupackagepickinglist.json"),
			postData:loxia._ajaxFormToObj("plForm")}).trigger("reloadGrid",[{page:1}]);
	});
	
	$j("#reset").click(function(){
		document.getElementById("plForm").reset();
	});
	
	$j("#tbl-sta-list").jqGrid({
		datatype: "json",
		colNames: ["ID","箱号",i18("STA_CODE"),i18("REFSLIPCODE"),"状态",i18("OWNER"),i18("LPCODE"),"商品名称","商品编码","商品条码","计划量","核对量","电子运单号","快递单号"],
		colModel: [{name: "id", index: "id", hidden: true,sortable:false},
		            {name :"pgIndex",width:20,resizable:true,sortable:false},
					{name :"staCode",width:90,resizable:true,sortable:false},
					{name: "staSlipCode",width:90,resizable:true,sortable:false},
					{name:"intStatus",width:30,resizable:false,formatter:'select',editoptions:stastatus},
					{name:"owner",width:60,resizable:true,sortable:false},
					{name:"lpcode",width:50,resizable:true,sortable:false,formatter:'select',editoptions:trasportName},
					{name:"skuName",width:100,resizable:true,sortable:false},
					{name:"skuCode",width:60,resizable:true,sortable:false},
					{name:"barCode",width:80,resizable:true,sortable:false},
	                {name:"quantity",width:40,resizable:true,sortable:false},
	                {name:"completeQuantity",width:40,resizable:true,sortable:false},
	                {name:"trackingNo1",width:100,resizable:true,sortable:false},
	                {name:"trackingNo",width:100,resizable:true,sortable:false}
					],
		caption: i18("WAITTING_CHECKED_LIST"),//待核对列表
	   	sortname: 'sta.pg_index',
	    multiselect: false,
		sortorder: "asc",
		width:"auto",
		height:"auto",
		rowNum:-1,
		viewrecords: true,
		jsonReader: { repeatitems : false, id: "0" },
		loadComplete:function(){
			loxia.initContext($j("#tbl-sta-list"));
			var postData = {};
			$j("#tbl-sta-list tr:gt(0)>td[aria-describedby$='barCode']").each(function (i,tag){
				postData["skuBarcodes[" + i + "]"] = $j(tag).html();
			});
			barcodeList = loxia.syncXhr($j("body").attr("contextpath") + "/findskuBarcodeList.json",postData);
		}
	});

	$j("#idSlipCode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var slipCode = $j("#idSlipCode").val();
			if(slipCode == $j("#tbSlipCode").html() || slipCode ==  $j("#tbStaCode").html()){
				$j("#txtTkNo").focus();
				$j("#tknoErrorMsg").html("");
			}else{
				$j("#tknoErrorMsg").html("作业单不正确");
			}
		}
	});
	
	$j("#idPgskuBarcode").keydown(function(evt){
		if(evt.keyCode === 13){
			var barcpde =pgsku["barcode"];
			var bval = $j("#idPgskuBarcode").val();
			if(bval != "" && bval == barcpde){
				if($j("#idScnQty").html() == ""){
					$j("#idScnQty").html("0");
				}
				var intQty = parseInt($j("#idScnQty").html(),10);
				$j("#idScnQty").html(intQty + 1);
				$j("#tknoErrorMsg").html("");
			}else{
			    $j("#tknoErrorMsg").html("套装商品条码错误!");
			}
			if(parseInt($j("#idScnQty").html(),10) == parseInt(pgsku["qty"],10)){
				$j("#idSlipCode").focus();
			}
		}
	});
				 
	$j("#idresetQty").click(function(){
		$j("#idScnQty").html("");
	});
	
	$j("#skuBarCode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var barcode = $j("#skuBarCode").val().trim();
			if(barcode == ""){
				return ;
			}
			var data = $j("#tbl-sta-list").jqGrid('getRowData');
			var isChecked = false;
			$j.each(data,function(i, stal){
				if(stal["trackingNo"] != ERROR && stal["trackingNo"] != ERROR_CANCEL ){
					if(!isChecked && stal["barCode"] == barcode && stal["quantity"] != stal["completeQuantity"]){
						stal["completeQuantity"] = stal["completeQuantity"] == null ? 1 : (stal["completeQuantity"] + 1);
						$j("#tbl-sta-list").jqGrid('setRowData',stal.id,stal);
						loxia.initContext($j("#tbl-sta-list"));
						$j("#tbSlipCode").html(stal["staSlipCode"]);
						$j("#tbStaCode").html(stal["staCode"]);
						$j("#tbPgIndex").html(stal["pgIndex"]);
						playMusic($j("body").attr("contextpath") +"/recording/"+stal["pgIndex"]+".wav");
						$j("#hidStaId").val(stal["id"]);
						$j("#txtTkNo").val("");
						$j("#txtTkNo").attr("sf",stal["trackingNo1"]);
						$j("#dialog_msg").dialog("open");
						$j("#idSlipCode").val("");
						$j("#idScnQty").html("");
						$j("#idPgskuBarcode").val("");
						$j("#idPgSkuName").html(pgsku["skuname"]);
						$j("#idPgskuBarcode").focus();
						isChecked = true;
					}
				}
			});
			//增加'00'条码验证
			if(!isChecked){
				$j.each(data,function(i, stal){
					if(stal["trackingNo"] != ERROR && stal["trackingNo"] != ERROR_CANCEL ){
						if(!isChecked && (stal["barCode"]) == '00'+ barcode && stal["quantity"] != stal["completeQuantity"]){
							stal["completeQuantity"] = stal["completeQuantity"] == null ? 1 : (stal["completeQuantity"] + 1);
							$j("#tbl-sta-list").jqGrid('setRowData',stal.id,stal);
							loxia.initContext($j("#tbl-sta-list"));
							$j("#tbSlipCode").html(stal["staSlipCode"]);
							$j("#tbStaCode").html(stal["staCode"]);
							$j("#tbPgIndex").html(stal["pgIndex"]);
							playMusic($j("body").attr("contextpath") +"/recording/"+stal["pgIndex"]+".wav");
							$j("#hidStaId").val(stal["id"]);
							$j("#txtTkNo").val("");
							$j("#txtTkNo").attr("sf",stal["trackingNo1"]);
							$j("#dialog_msg").dialog("open");
							$j("#idSlipCode").val("");
							$j("#idScnQty").html("");
							$j("#idPgskuBarcode").val("");
							$j("#idPgSkuName").html(pgsku["skuname"]);
							$j("#idPgskuBarcode").focus();
							isChecked = true;
						}
					}
				});	
			}
			if(!isChecked){
				$j.each(data,function(i, stal){
					if(stal["trackingNo"] != ERROR && stal["trackingNo"] != ERROR_CANCEL ){
						if(!isChecked && stal["quantity"] != stal["completeQuantity"]){
							var bc = stal["barCode"];
							for(var v in barcodeList[bc]){
								if(barcodeList[bc][v] == barcode || barcodeList[bc][v] == ('00'+barcode)){
									stal["completeQuantity"] = stal["completeQuantity"] == null ? 1 : (stal["completeQuantity"] + 1);
									$j("#tbl-sta-list").jqGrid('setRowData',stal.id,stal);
									loxia.initContext($j("#tbl-sta-list"));
									$j("#tbSlipCode").html(stal["staSlipCode"]);
									$j("#tbStaCode").html(stal["staCode"]);
									$j("#tbPgIndex").html(stal["pgIndex"]);
									playMusic($j("body").attr("contextpath") +"/recording/"+stal["pgIndex"]+".wav");
									$j("#hidStaId").val(stal["id"]);
									$j("#txtTkNo").val("");
									$j("#txtTkNo").attr("sf",stal["trackingNo1"]);
									$j("#dialog_msg").dialog("open");
									$j("#idSlipCode").val("");
									$j("#idSlipCode").focus();
									isChecked=true;
								}
							}
						}
					}
				});
			}
			if(!isChecked){
				playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
				showErrorMsg(barcode + " 不在计划核对量中");
			}
			if(isChecked){
				var pNo = $j.trim($j("#verifyCode").html());
				var staNo = $j.trim($j("#tbStaCode").html());
				var timestamp=new Date().getTime();
				try{
					cameraPhoto(pNo, staNo, barcode,timestamp);//拍照
					fileUrl = dateValue +"/" +ouName+ "/" + pNo + "/" + staNo+"/"+barcode + "_" + userName+"_"+timestamp;
					cameraPaintJs(fileUrl);
				} catch (e) {
//					jumbo.showMsg(i18("拍照失败！"));//初始化系统参数异常
				};
			}
			$j("#skuBarCode").val("");
		}
	});
	
	//执行核对
	$j("#btnConfirm").click(function(){
		//核对数量
		if($j("#idScnQty").html() == ""){
			$j("#idScnQty").html("0");
		}
		var qty = parseInt($j("#idScnQty").html(),10);
		if(parseInt(pgsku["qty"]) != qty){
			$j("#tknoErrorMsg").html("套装商品数量不正确");
			return;
		}
		if($j("#txtTkNo").val().trim() == ""){
			$j("#tknoErrorMsg").html("请输入快递单号");
			$j("#txtTkNo").focus();
			return;
		}
		var sftkno = $j("#txtTkNo").attr("sf");
		var tk = $j("#txtTkNo").val();
		if(sftkno != "" && sftkno != null &&sftkno != tk){
			$j("#txtTkNo").val("");
			$j("#tknoErrorMsg").html("请输入正确快递单号");
			$j("#txtTkNo").focus();
			return;
		}
		var errMsg = $j("#tknoErrorMsg").html();
		if(errMsg != ""){
			return;
		}
		var postData = getCheckPostData();
		if(postData != null){
			postData["lineNo"]= lineNo;
			var rs = loxia.syncXhr($j("body").attr("contextpath") + "/checkstalist.json",postData);
			if(rs && rs.result && rs.result == "success"){
				var data=$j("#tbl-sta-list").jqGrid("getRowData",$j("#hidStaId").val());
				data["trackingNo"] = $j("#txtTkNo").val();
				$j("#tbl-sta-list").jqGrid('setRowData',$j("#hidStaId").val(),data);
				loxia.initContext($j("#tbl-sta-list"));
				$j("#tknoErrorMsg").html("");
				$j("#dialog_msg").dialog("close");
				$j("#confirmBarCode").val("");
				$j("#dialog_success_msg").dialog("open");
//				try{
//					var pNo = $j.trim($j("#verifyCode").html());
//					var staCode = $j.trim($j("#tbStaCode").html());
//					var timestamp=new Date().getTime();
//					creatZipJs(dateValue + "/" + pNo + "/" + staCode,timestamp);//打包图片
//					updateHttpJs(dateValue + "/" + pNo + "/" + staCode,timestamp);//发送图片zip http服务器
//					loxia.syncXhr($j("body").attr("contextpath") + "/creatStvCheckImg.json",{"staCode":staCode,"fileUrl":"/"+dateValue + "/" + pNo + "/" + staCode});//保存STV图片路径
//				} catch (e) {
//				};
				$j("#confirmBarCode4").focus();
			}else{
				var data=$j("#tbl-sta-list").jqGrid("getRowData",$j("#hidStaId").val());
				if(rs && rs.isCancel == true){
					data["trackingNo"] = ERROR_CANCEL;
				}else{
					data["trackingNo"] = ERROR;
				}
				$j("#tbl-sta-list").jqGrid('setRowData',$j("#hidStaId").val(),data);
				loxia.initContext($j("#tbl-sta-list"));
				$j("#dialog_msg").dialog("close");
				$j("#confirmBarCode").val("");
				if(rs&& rs.msg){
					$j("#errorMsg").html(rs.msg);
				}else{
					$j("#confirmBarCode1").html("核对异常");
				}
				$j("#dialog_error").dialog("open");
				$j("#confirmBarCode1").focus();
				isLineNoError = rs.msg;
			}
		}
	});
	
	$j("#confirmBarCode").keyup(function(evt){
		if(BARCODE_CONFIRM == $j("#confirmBarCode").val().trim()){
			$j("#btnConfirm").trigger("click");
		}
	});
	
	$j("#confirmBarCode4").keyup(function(evt){
		if(BARCODE_CONFIRM == $j("#confirmBarCode4").val().trim()){
			$j("#btnConfirm4").trigger("click");
		}
	});
	
	$j("#confirmBarCode1").keyup(function(evt){
		if(BARCODE_CONFIRM == $j("#confirmBarCode1").val().trim()){
			$j("#btnConfirm1").trigger("click");
		}
	});
	
	//错误确认
	$j("#btnConfirm1").click(function(){
		$j("#dialog_error").dialog("close");
		$j("#confirmBarCode1").val("");
		if(isCheckedFinish()){
			$j("#idBack").trigger("click");
		}
		if(isLineNoError == "流水开票号未找到"){
			jumbo.removeAssemblyLineNo();
			lineNo = jumbo.getAssemblyLineNo();
			isLineNoError = "";
		}
	});
	
	//完成确认
	$j("#btnConfirm4").click(function(){
		$j("#dialog_success_msg").dialog("close");
		$j("#confirmBarCode4").val("");
		if(isCheckedFinish()){
			$j("#idBack").trigger("click");
		}
	});
	
	//核对快递单号
	$j("#txtTkNo").keydown(function(evt){
		if(evt.keyCode === 13){
//			var errMsg = $j("#tknoErrorMsg").html();
//			if(errMsg != ""){
//				$j("#txtTkNo").val("");
//				return;
//			}
			var tkNo = $j("#txtTkNo").val().trim();
			
			if(tkNo != ""){
				var sftkno = $j("#txtTkNo").attr("sf");
				if(sftkno != "" && sftkno != tkNo){
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
			    
				var data ={trackingNo:$j("#txtTkNo").val().trim()};
				data["sta.id"]=$j("#hidStaId").val();
				var rs = loxia.syncXhr($j("body").attr("contextpath") + "/checktrackingno.json",data);
				if(rs && rs.result=='success'){
					//判断是否存在重复
					var allData = $j("#tbl-sta-list").jqGrid('getRowData');
					var isExists = false;
					$j.each(allData,function(i, stal){
						if(stal["trackingNo"] == tkNo){
							isExists = true;
							playMusic($j("body").attr("contextpath") +"/recording/"+stal["pgIndex"]+".wav");
							$j("#tknoErrorMsg").html("快递单号已经被扫描 ，箱号：" + stal["pgIndex"]);
						}
					});
					if(!isExists){
						$j("#confirmBarCode").focus();
						$j("#tknoErrorMsg").html("");
					}
				}else{
					$j("#tknoErrorMsg").html("快递单号格式不正确");
				}
			}
			isCheckedFinish();
		}
	});
	
	$j("#btnTryError").click(function(){
		var data = $j("#tbl-sta-list").jqGrid('getRowData');
		var isFound = false;
		$j.each(data,function(i, stal){
			if(!isFound && stal["trackingNo"] == ERROR){
				//显示核对
				$j("#tbSlipCode").html(stal["staSlipCode"]);
				$j("#tbStaCode").html(stal["staCode"]);
				$j("#tbPgIndex").html(stal["pgIndex"]);
				$j("#hidStaId").val(stal["id"]);
				$j("#txtTkNo").val("");
				$j("#dialog_msg").dialog("open");
				$j("#txtTkNo").focus();
			}
		});
	});
	
	//快速核对进去详细页
	$j("#quickPl").keydown(function(evt){
		if (evt.keyCode === 13) {
			var plcode = $j("#quickPl").val().trim();
			if (plcode == "") {
				jumbo.showMsg("输入单据号码不能为空!");
				return;
			}
			showDetail(null);
		}
	});
	
	//反回
	$j("#idBack").click(function(){
		$j("#checkDiv").addClass("hidden");
		$j("#plDiv").removeClass("hidden");
		$j("#quickPl").focus();
	});
	
	//重拍
	$j("#newCamera").click(function() {
		var pNo = $j.trim($j("#verifyCode").html());
		var staNo = $j.trim($j("#tbStaCode").html());
		var timestamp=new Date().getTime();	
		try{
			cameraPhotoJs(dateValue +"/" +ouName+ "/" + pNo + "/" + staNo, userName+"_"+timestamp);
			fileUrl = dateValue +"/" +ouName+ "/" + pNo + "/" + staNo+"/"+userName+"_"+timestamp;
			cameraPaintJs(fileUrl);
		} catch (e) {
//			jumbo.showMsg(i18("重拍失败！"));//初始化系统参数异常
		};
		$j("#dialog_msg").dialog("close");
		$j("#dialog_msg").dialog("open");
		$j("#idPgskuBarcode").focus();
//		loxia.syncXhr($j("body").attr("contextpath") + "/creatStvCheckImg.json",{"staCode":staNo,"fileUrl":"/"+dateValue + "/" + pNo + "/" + staNo});//保存STV图片路劲
//		creatZip(dateValue + "/" + pNo + "/" + staNo);
	});
});
//拍照bin.hu
function cameraPhoto(pNo, staNo, barCode,timestamp) {
//	var timestamp=new Date().getTime();
	cameraPhotoJs(dateValue +"/" +ouName+ "/" + pNo + "/" + staNo, barCode + "_" + userName+"_"+timestamp);
	fileName += barCode + "_"+userName+"_"+timestamp+".jpg"+"/";
//	setTimeout(cameraPaintJs(dateValue + "/" + pNo + "/" + staNo+"/"+barCode + "_" + userName+"_"+timestamp),1000);
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