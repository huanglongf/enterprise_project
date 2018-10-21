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
	WH_NAME:"所属仓库",
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

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function getFmtValue(tableId,rowId,column){
	return $j("#"+tableId+" tr[id="+rowId+"] td[aria-describedby='"+tableId+"_"+column+"']").attr("title")
}
function showDetail(obj){
	var plCode;
	if(obj==null){
		plCode = $j("#quickPl").val();
	}else{
		plCode = $j(obj).text();
	}
	var pl = loxia.syncXhrPost($j("body").attr("contextpath") + "/getsinglecheckorder.json",{"code":plCode});
	if(pl && pl["pl"]){
		var row = pl['pl']['id'];
		$j("#verifyCode").html(pl['pl']['code']);
		if(pl['pl']['intStatus'] == '2'){
			$j("#verifyStatus").html('配货中');
		}
		if(pl['pl']['intStatus'] == '8'){
			$j("#verifyStatus").html('部分完成');
		}
		$j("#verifyPlanBillCount").html(pl['pl']['planBillCount']);
		$j("#verifyCheckedBillCount").html(pl['pl']['checkedBillCount']);
		$j("#verifyPlanSkuQty").html(pl['pl']['planSkuQty']);
		$j("#verifyCheckedSkuQty").html(pl['pl']['checkedSkuQty']);
		$j("#plDiv").addClass("hidden");
		$j("#checkDiv").removeClass("hidden");
		$j("#quickPl").val("");
		$j("#tbl-sta-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/checkSingleGetOcpStalByPl.json"),
			postData:{"cmd.id":row}}).trigger("reloadGrid");
		$j("#skuBarCode").focus();
		initCheck();
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
	postdata["sta.id"] = $j("#hidStaId").val();
	postdata["sta.trackingNo"] = $j("#txtTkNo").val().trim();
	postdata["lineNo"]= lineNo;
	postdata["sn"] = $j("#hidSn").val();
	return postdata;
}

function showErrorMsg(msg){
	$j("#errorMsg").html(msg);
	$j("#dialog_error").dialog("open");
	$j("#confirmBarCode1").focus();
}

function errorTipOK(text) {
	$j("#error_text_ok").html("<font style='text-align:center;font-size:36px' color='red'>"+text+"</font>");
	$j("#dialog_error_ok").dialog("open");
}


var stastatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.json",{"categoryCode":"whSTAStatus"});
var statype=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.json",{"categoryCode":"whSTAType"});
var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.json");

var lineNo;
var isLineNoError = "";
//条形码选中的行
var lineStal;
$j(document).ready(function (){
	$j("#dialog_msg").dialog({title: "提示信息", modal:true, autoOpen: false, width: 650,closeOnEscape: false,open: function(event, ui){$j(".ui-dialog-titlebar-close").hide();}});
	$j("#dialog_error").dialog({title: "错误信息", modal:true, autoOpen: false, width: 650});
	$j("#dialog_success_msg").dialog({title: "提示信息", modal:true, autoOpen: false, width: 650,closeOnEscape: false,open: function(event, ui){$j(".ui-dialog-titlebar-close").hide();}});
	$j("#quickPl").focus();
	$j("#whName").html(window.parent.$j("#orgname").html());
	
	$j("#dialog_error_ok").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog_error_close_ok").click(function(){
		$j("#dialog_error_ok").dialog("close");
	});
	
	//根据运营中心仓库查询
	var result = loxia.syncXhrPost($j("body").attr("contextpath")+"/selectbyopc.json");
	for(var idx in result.warelist){
		$j("<option value='" + result.warelist[idx].id + "'>"+ result.warelist[idx].name +"</option>").appendTo($j("#selTrans"));
	}
	var plstatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"pickingListStatus"});
	if(!plstatus.exception){
		$j("#tbl-dispatch-list").jqGrid({
			url:$j("body").attr("contextpath") + "/searchCheckListopc.json",
			postData:loxia._ajaxFormToObj("plForm"),
			datatype: "json",//"已发货单据数","已发货商品件数",
			//colNames: ["ID","配货批次号","状态","计划配货单据数","已核对单据数","已发货单据数","计划配货商品件数","已核对商品件数","已发货商品件数","最后核对时间","最后发货时间","开始配货时间"],
			colNames: ["ID",i18("CODE"),i18("INTSTATUS"),i18("PLANBILLCOUNT"),i18("CHECKEDBILLCOUNT"),i18("SHIPSTACOUNT"),i18("PLANSKUQTY"),i18("CHECKEDSKUQTY"),i18("SHIPSKUQTY"),
				i18("CREATE_TIME"),i18("CHECKEDTIME"),i18("EXECUTEDTIME"),i18("PICKINGTIME"),i18("WH_NAME")],
			colModel: [{name: "id", index: "pl.id", hidden: true},
						{name:"code",index:"pl.code",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:100,resizable:true},
						{name:"intStatus", index:"pl.status" ,width:40,resizable:true,formatter:'select',editoptions:plstatus},
						{name:"planBillCount", index:"pl.plan_bill_count",width:100,resizable:true},
						{name:"checkedBillCount", index:"pl.checked_bill_count", width:90, resizable:true},
						{name:"shipStaCount",index:"pl.send_bill_count",width:90,resizable:true,hidden:true},
						{name:"planSkuQty",index: "pl.plan_sku_qty",width:120,resizable:true},
						{name:"checkedSkuQty",index:"pl.checked_sku_qty",width:100,resizable:true},
						{name:"shipSkuQty",index:"pl.send_sku_count",width:100,resizable:true,hidden:true},
						{name:"createTime",index:"pl.CREATE_TIME",width:120,resizable:true},
						{name:"checkedTime",index:"pl.CHECK_TIME",width:120,resizable:true},
						{name:"executedTime",index:"pl.last_outbound_time",width:120,resizable:true,hidden:true},
		               {name:"pickingTime",index:"pl.PICKING_TIME",width:120,resizable:true},
		               {name:"wname",index:"",width:120,resizable:true}],
			caption: i18("PICKING_LIST"),//配货清单列表
		   	sortname: 'pl.CREATE_TIME',
		    multiselect: false,
			sortorder: "desc",
			pager:"#pager",
			width:"auto",
			height:"auto",
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
			height:jumbo.getHeight(),
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
	}else{
		jumbo.showMsg(i18("INIT_SYSTEM_DATA_EXCEPTION"));//初始化系统参数异常
	}

	$j("#search").click(function(){
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/searchCheckListopc.json"),
			postData:loxia._ajaxFormToObj("plForm")}).trigger("reloadGrid",[{page:1}]);
	});
	$j("#reset").click(function(){
		document.getElementById("plForm").reset();
	});
	
	$j("#tbl-sta-list").jqGrid({
		datatype: "json",
		colNames: ["ID","是否SN","箱号",i18("STA_CODE"),i18("REFSLIPCODE"),"状态",i18("OWNER"),i18("LPCODE"),"商品名称","SKU编码","商品条码","计划量","核对量","SN号","电子运单号","快递单号"],
		colModel: [{name: "id", index: "id", hidden: true,sortable:false},
		            {name: "isSnSku", index: "isSnSku", hidden: true,sortable:false}, //是否SN
		            {name :"pgIndex",width:30,resizable:true,sortable:false},
					{name :"staCode",width:90,resizable:true,sortable:false},
					{name: "staSlipCode",width:90,resizable:true,sortable:false},
					{name:"intStatus", index:"intStatus" ,width:40,resizable:true,formatter:'select',editoptions:stastatus,sortable:false},
					{name:"owner",width:80,resizable:true,sortable:false},
					{name:"lpcode",width:80,resizable:true,sortable:false,formatter:'select',editoptions:trasportName},
					{name:"skuName",width:120,resizable:true,sortable:false},
					{name:"skuCode",width:100,resizable:true,sortable:false},
					{name:"barCode",width:100,resizable:true,sortable:false,hidden:true},
	                {name:"quantity",width:60,resizable:true,sortable:false},
	                {name:"completeQuantity",width:60,resizable:true,sortable:false},
	                {name:"snCode",width:120,resizable:true,sortable:false},
	                {name:"trackingNo1",width:120,resizable:true,sortable:false},
	                {name:"trackingNo",width:120,resizable:true,sortable:false}
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
				$j("#idSlipCode").val("");
				$j("#tknoErrorMsg").html("作业单不正确");
			}
		}
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
					if(!isChecked && (stal["barCode"] == barcode || stal["barCode"] == "00"+barcode) && stal["quantity"] != stal["completeQuantity"]){
						//判断 是否SN
						if(stal["isSnSku"]=='1'){
							$j("#snDiv").show();
							$j("#snCode").focus();
							isChecked = true;
							lineStal = stal;
							return;
						}else{
							$j("#snDiv").hide();
							$j("#tbSlipCode").html(stal["staSlipCode"]);
							$j("#tbStaCode").html(stal["staCode"]);
							$j("#tbPgIndex").html(stal["pgIndex"]);
							$j("#hidStaId").val(stal["id"]);
							$j("#txtTkNo").val("");
							$j("#txtTkNo").attr("sf",stal["trackingNo1"]);
							$j("#dialog_msg").dialog("open");
							$j("#idSlipCode").val("");
							$j("#idSlipCode").focus();
							isChecked = true;
							return ;
						}
					}
				}
			});
			
			if(!isChecked){
				//showErrorMsg(barcode + " 不在计划核对量中");
				errorTipOK(barcode + " 不在计划核对量中");
				$j("#skuBarCode").val("");
			}
		}
	});
	
	//执行核对
	$j("#btnConfirm").click(function(){
		//作业单号、相关单据号 fanht
		if($j("#idSlipCode").val().trim() == ""){
			$j("#tknoErrorMsg").html("请输入相关单据号/作业单号 ");
			$j("#idSlipCode").focus();
			return;
		}
		//快递单号 fanht
		if($j("#txtTkNo").val().trim() == ""){
			$j("#tknoErrorMsg").html("请输入快递单号");
			$j("#txtTkNo").focus();
			return;
		}else{
			var tkNo = $j("#txtTkNo").val().trim();
			var sftkno = $j("#txtTkNo").attr("sf");
			if(sftkno != "" && sftkno != tkNo){
				$j("#txtTkNo").focus();
				$j("#txtTkNo").val("");
				$j("#tknoErrorMsg").html("非正确的快递单号!");
				return;
			}
		}
		//作业单检查
		var slipCode = $j("#idSlipCode").val();
		if(slipCode != $j("#tbSlipCode").html() && slipCode !=  $j("#tbStaCode").html()){
			$j("#idSlipCode").focus();
			$j("#idSlipCode").val("");
			$j("#tknoErrorMsg").html("作业单不正确");
			return;
		}
		
		var postData = getCheckPostData();
		if(postData != null){
			var rs = loxia.syncXhr($j("body").attr("contextpath") + "/checkSingleCheckStaList.json",postData);
			if(rs && rs.result && rs.result == "success"){
				var data=$j("#tbl-sta-list").jqGrid("getRowData",$j("#hidStaId").val());
				data["trackingNo"] = $j("#txtTkNo").val().trim();
				data["snCode"] = $j("#snCode").val().trim();
				//核对成功 增加数量 fanht
				data["completeQuantity"] = data["completeQuantity"] == null ? 1 : (data["completeQuantity"] + 1);
				$j("#tbl-sta-list").jqGrid('setRowData',$j("#hidStaId").val(),data);
				loxia.initContext($j("#tbl-sta-list"));
				$j("#dialog_msg").dialog("close");
				$j("#confirmBarCode").val("");
				//判断是否处理完
				if(isCheckedFinish()){
					$j("#idBack").trigger("click");
				}
			}else{
				var data=$j("#tbl-sta-list").jqGrid("getRowData",$j("#hidStaId").val());
				if(rs && rs.isCancel == true){
					data["trackingNo"] = ERROR_CANCEL
				}else{
					data["trackingNo"] = ERROR;
				}
				$j("#tbl-sta-list").jqGrid('setRowData',$j("#hidStaId").val(),data);
				loxia.initContext($j("#tbl-sta-list"));
				$j("#dialog_msg").dialog("close");
				$j("#confirmBarCode").val("");
				if(rs&& rs.msg){
					//$j("#errorMsg").html(rs.msg);
					$j("#error_text_ok").html("<font style='text-align:center;font-size:36px' color='red'>"+rs.msg+"</font>");
				}else{
					//$j("#confirmBarCode1").html("核对异常");
					$j("#error_text_ok").html("<font style='text-align:center;font-size:36px' color='red'>"+ "核对异常" +"</font>");
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
			var tkNo = $j("#txtTkNo").val().trim();
			if(tkNo != ""){
				var sftkno = $j("#txtTkNo").attr("sf");
				if(sftkno != "" && sftkno != tkNo){
					isExists = true;
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
							$j("#txtTkNo").val("");
							$j("#tknoErrorMsg").html("快递单号已经被扫描 ，箱号：" + stal["pgIndex"]);
						}
					});
					if(!isExists){
						$j("#confirmBarCode").focus();
						$j("#tknoErrorMsg").html("");
					}
				}else{
					$j("#tknoErrorMsg").html("快递单号格式不正确");
					$j("#txtTkNo").val("");
				}
			}
			//isCheckedFinish();
		}
	});
	
	$j("#btnTryError").click(function(){
		var data = $j("#tbl-sta-list").jqGrid('getRowData');
		var isFound = false;
		$j.each(data,function(i, stal){
			if(!isFound && stal["trackingNo"] == ERROR){
				//显示核对
				if(stal["isSnSku"]=='1'){
					$j("#skuBarCode").val(stal["barCode"]);
					$j("#snDiv").show();
					$j("#snCode").focus();
					lineStal = stal;
					return;
				}else{
					$j("#snDiv").hide();
					$j("#tbSlipCode").html(stal["staSlipCode"]);
					$j("#tbStaCode").html(stal["staCode"]);
					$j("#tbPgIndex").html(stal["pgIndex"]);
					$j("#hidStaId").val(stal["id"]);
					$j("#txtTkNo").val("");
					$j("#txtTkNo").attr("sf",stal["trackingNo1"]);
					$j("#dialog_msg").dialog("open");
					$j("#idSlipCode").val("");
					$j("#idSlipCode").focus();
					return;
				}
			}
		});
	});
	
	//快速核对进去详细页
	$j("#quickPl").keydown(function(evt){
		if(evt.keyCode === 13){
			var plcode = $j("#quickPl").val().trim();
			if(plcode == ""){
				jumbo.showMsg("输入单据号码不能为空!");
				return ;
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
	
	//关闭核对页面
	$j("#close").click(function(){
		$j("#dialog_msg").dialog("close");
		initCheck();
		$j("#skuBarCode").focus();
	});
	
	//扫描SN号后
	$j("#snCode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			checkSn();
		}
	});
	
});

//SN判断
function checkSn(){
	var stal = lineStal;
	var bc = $j("#snCode").val().trim();
	if(bc==""){
		return;
	}
	//SN号校验
	var result = loxia.syncXhr($j("body").attr("contextpath") + "/findBarcodeBySn.json",{"sn":bc});
	if(result && result.result == "success"){
		if(result.barcode==stal["barCode"]){
			$j("#tbSlipCode").html(stal["staSlipCode"]);
			$j("#tbStaCode").html(stal["staCode"]);
			$j("#tbPgIndex").html(stal["pgIndex"]);
			$j("#hidStaId").val(stal["id"]);
			$j("#hidSn").val(bc);
			$j("#txtTkNo").val("");
			$j("#txtTkNo").attr("sf",stal["trackingNo1"]);
			$j("#dialog_msg").dialog("open");
			$j("#idSlipCode").val("");
			$j("#idSlipCode").focus();
		}else{
			//showErrorMsg(i18("SN对应的条形码和之前扫描的不匹配"));
			errorTipOK(i18("SN对应的条形码和之前扫描的不匹配"));
			initCheck();
		}
	}else{
		//showErrorMsg(i18("SN号不存在"));
		errorTipOK(i18("SN号不存在"));
		initCheck();
		return;
	}
}
//初始化initCheck
function initCheck(){
	$j("#skuBarCode").val("");
	$j("#snDiv").hide();
	$j("#snCode").val("");
	$j("#hidSn").val("");
}