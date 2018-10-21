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
	LPCODE : "物流服务商",
	CREATETIME : "创建时间",
	CHECKTIME : "核对时间",
	STVTOTAL : "计划执行总件数",
	WAITTING_CHECKED_LIST : "待核对列表",
	
	CHECKED_LIST : "已核对列表",
	PACKAGED_LIST : "核对记录列表",
	SKUCODE : "SKU编码",
	LOCATIONCODE : "库位编码预览",
	SKUNAME : "商品名称",
	JMSKUCODE : "商品编码",
	BARCODE : "条形码",
	QUANTITY : "计划执行数量",
	COMPLETEQUANTITY : "已执行数量",
	
	INPUT_CODE : "请输入批次号",
	NO_CODE : "指定批次号不存在！",
	BARCODE_NOT_EXISTS : "条形码不存在",
	TRACKINGNO_EXISTS : "快递单号已经存在",
	DELETE : "删除",
	TRACKINGNO_RULE_ERROR : "快递单号格式不对",
	SURE_OPERATE : "确定执行本次操作",
	QUANTITY_ERROR : "数量错误",
	WEIGHT_RULE_ERROR : "已执行量大于计划执行量，如果继续执行核对，将直接按照计划执行量执行核对。是否继续？",
	INPUT_TRACKINGNO : "请输入快递单号",
	EXIST_TRACKINGNO : "本次核对只能输入一个快递单号",
	OPERATE_FAILED : "执行核对失败！",
	EXISTS_QUANTITY_GQ_EXECUTEQTY : "请检查核对数量,存在商品未核对",
	
	SN_CODE : "SN序列号",
	VIEW_SN : "查看",
	TRANSNO : "快递单号",
	PACKAGEDSKUQTY : "本次核对单据数",
	OPERATOR : "操作",
	PRING_PACKAGED : "打印本次核对装箱清单"
});
var startTime = 1;
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function getFmtValue(tableId,rowId,column){
	return $j("#"+tableId+" tr[id="+rowId+"] td[aria-describedby='"+tableId+"_"+column+"']").attr("title")
}
function reloadSta(plId){
	var 	status = "2";
	$j("#tbl-showDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/getO2OQSPickCheckList.json"),
			postData:{"cmd.id":plId,"status":status}}).trigger("reloadGrid",[{page:1}]);
	status = "3,4,10";
	//已核对装箱列表
	$j("#tbl-showPackaged").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/getO2OQSPackCheckList.json"),
		postData:{"cmd.id":plId,"status":status}}).trigger("reloadGrid",[{page:1}]);
	//已核对列表
	//$j("#tbl-showReady").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/getO2OQSPickCheckList.json"),
	//		postData:{"cmd.id":plId,"status":status}}).trigger("reloadGrid",[{page:1}]);
	//取消列表
	status = "15,17";
	$j("#tbl-showCancel").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/getO2OQSPickCheckList.json"),
		postData:{"cmd.id":plId,"status":status}}).trigger("reloadGrid",[{page:1}]);
}

function showDetail(obj, pl){
	try {
		closeGrabberJs();
	} catch (e) {
		// jumbo.showMsg(i18("关闭摄像头失败！"));//初始化系统参数异常
	}
	try {
		openCvPhotoImgJs();// 打开照相功能
		// jumbo.showMsg(i18("打开照相功能成功！"));
	} catch (e) {
		jumbo.showMsg(i18("打开照相功能失败！"));// 初始化系统参数异常
	}
	$j("#doCheckDetail").removeClass("hidden");
	$j("#tbl-express-order tr:gt(0)").remove();
	$j("#tbl-trank-list tr:gt(0)").remove();  
	$j("#showList").addClass("hidden");
	$j("#showDetail").removeClass("hidden");
	//$j("#billsId-2").focus();
	$j("#barCode").focus();
	var row = -1;
	if(null == obj && null != pl){
		row = pl["id"];
	}else{
		row=$j(obj).parent().parent().attr("id"),pl=$j("#tbl-dispatch-list").jqGrid("getRowData",row);
	}
	$j("#verifyPlId").val(row);
	$j("#showDetail").attr("plId",row);
	$j("#showDetail").attr("plpId",pl['pickingListPackageId']);
	$j("#backTarget").val("refSlipCode");
	$j("#lpcode").val(pl['lpcode']);
	$j("#verifyCode").html(pl['code']);
	$j("#verifyStatus").html(getFmtValue("tbl-dispatch-list",row,"intStatus"));
	$j("#verifyPlanBillCount").html(pl['planBillCount']);
	$j("#verifyCheckedBillCount").html(pl['checkedBillCount']);
	$j("#verifyShipStaCount").html(pl['shipStaCount']);
	$j("#verifyPlanSkuQty").html(pl['planSkuQty']);
	$j("#verifyCheckedSkuQty").html(pl['checkedSkuQty']);
	$j("#verifyShipSkuQty").html(pl['shipSkuQty']);
	$j("#verifyTrackingNo").html(pl['trackingNo']);
	$j("#plpId").val(pl['pickingListPackageId']);
	$j("#trackingNo").val(pl['trackingNo']);
	reloadSta(row);
}

function doCheck(obj){
	$j("#showDetail").addClass("hidden");
	$j("#file").val("");
	$j("#checkBill").removeClass("hidden");
	$j("#barCode").focus();
	var row=$j(obj).parent().parent().attr("id"),sta=$j("#tbl-showDetail").jqGrid("getRowData",row);
	$j("#staCode").html(sta['code']);
	$j("#staCreateTime").html(sta['createTime']);
	$j("#tbl-express-order tr:gt(0)").remove();
	$j("#verifyPlId").val($j("#showDetail").attr("plId"));
	$j("#backTarget").val("refSlipCode2");
	var transName = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
	var lpcode = "";
	for(var idx in transName){
		if(transName[idx].code == sta['lpcode']){
			lpcode  = transName[idx].name;
			break;
		}
	}
	
	$j("#staDelivery").html(lpcode);
	$j("#staTranType").html(getFmtValue("tbl-showDetail",row,"intType"));
	$j("#staRefCode").html(sta['refSlipCode']);
	$j("#staStatus").html(getFmtValue("tbl-showDetail",row,"intStatus"));
	$j("#staComment").html(sta['memo']);
	$j("#staTotal").html(sta['stvTotal']);
	$j("#staTransNo").html(sta['transNo']);
	$j("#staId").val(sta['id']);
	var date = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getWarehouseInfo.json");
	if(date.isMqInvoice){
		lineNo = jumbo.getAssemblyLineNo();
	}
	$j("#tbl-billView").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/staLineInfoByStaId.json"),
			postData:{"sta.id":row}}).trigger("reloadGrid",[{page:1}]);
	
}


var barcodeList;
var rs0;
var lineNo;
var isNotLineNo;
//记录SN号
var snArray = new Array();
//当前选中行
var lineData;
var userName = "";
var dateValue = "";
var fileUrl = "";
var fileName = "";
var ouName = "";
var staCodeList;
var isBarCode = false;
$j(document).ready(function (){
	$j("#doCheckDetail").addClass("hidden");
	userName = $j("#userinfo", window.parent.document).attr("lgname");
	dateValue = $j("#userinfo", window.parent.document).attr("sysdate");
	ouName = $j("#userinfo", window.parent.document).attr("ouName");
//	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransportator.do");
//	for(var idx in result){
//		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#selLpcode"));
//	}
//	$j("#selLpcode").val("SF");
	rs0 = loxia.syncXhr($j("body").attr("contextpath") + "/json/checkIsNeedBarcode.json",null);
	
	$j("#dialog_error").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog_error_ok").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog_error_recheck").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog-sns").dialog({title: "SN序列号", modal:true, autoOpen: false, width: 600});
	$j("#close").click(function(){
		$j("#dialog-sns").dialog("close");
	});
	$j("#dialog_error_close_ok").click(function(){
		$j("#dialog_error_ok").dialog("close");
	});
	
	$j("#whName").html(window.parent.$j("#orgname").html());
	var plstatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"pickingListStatus"});
	if(!plstatus.exception){
		$j("#tbl-dispatch-list").jqGrid({
			url:$j("body").attr("contextpath") + "/searchO2OQSPickingList.json",
			postData:loxia._ajaxFormToObj("plForm"),
			datatype: "json",//"已发货单据数","已发货商品件数",
			//colNames: ["ID","配货批次号","状态","计划配货单据数","已核对单据数","已发货单据数","计划配货商品件数","已核对商品件数","已发货商品件数","最后核对时间","最后发货时间","开始配货时间"],
			colNames: ["ID",i18("CODE"),i18("INTSTATUS"),i18("PLANBILLCOUNT"),i18("CHECKEDBILLCOUNT"),i18("SHIPSTACOUNT"),i18("PLANSKUQTY"),i18("CHECKEDSKUQTY"),i18("SHIPSKUQTY"),
				i18("CREATE_TIME"),i18("CHECKEDTIME"),i18("EXECUTEDTIME"),i18("PICKINGTIME"),"lpcode","pickingListPackageId","trackingNo"],
			colModel: [{name: "id", index: "pl.id", hidden: true},
						{name:"code",index:"pl.code",formatter:"linkFmatter",formatoptions:{onclick:"showDetail"},width:100,resizable:true},
						{name:"intStatus", index:"pl.status" ,width:60,resizable:true,formatter:'select',editoptions:plstatus},
						{name:"planBillCount", index:"pl.plan_bill_count",width:100,resizable:true},
						{name:"checkedBillCount", index:"pl.checked_bill_count", width:90, resizable:true},
						{name:"shipStaCount",index:"pl.send_bill_count",width:90,resizable:true,hidden:true},
						{name:"planSkuQty",index: "pl.plan_sku_qty",width:120,resizable:true},
						{name:"checkedSkuQty",index:"pl.checked_sku_qty",width:100,resizable:true},
						{name:"shipSkuQty",index:"pl.send_sku_count",width:100,resizable:true,hidden:true},
						{name:"createTime",index:"pl.CREATE_TIME",width:150,resizable:true},
						{name:"checkedTime",index:"pl.CHECK_TIME",width:150,resizable:true},
						{name:"executedTime",index:"pl.last_outbound_time",width:150,resizable:true,hidden:true},
		                {name:"pickingTime",index:"pl.PICKING_TIME",width:150,resizable:true},
		                {name: "lpcode", index: "pl.lpcode", hidden: true},
		                {name: "pickingListPackageId", index: "pl.pickingListPackageId", hidden: true},
		                {name: "trackingNo", index: "pl.trackingNo", hidden: true}],
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
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/searchO2OQSPickingList.json"),
			postData:loxia._ajaxFormToObj("plForm")}).trigger("reloadGrid",[{page:1}]);
	});
	$j("#reset").click(function(){
		document.getElementById("plForm").reset();
	});
	var stastatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.json",{"categoryCode":"whSTAStatus"}),
		statype=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.json",{"categoryCode":"whSTAType"});
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.json");
	if(!stastatus.exception&&!stastatus.statype){
		$j("#tbl-showDetail").jqGrid({
			datatype: "json",//"已发货单据数","已发货商品件数",
			//colNames: ["ID","备注","作业单号","状态","相关单据号","作业类型名称","相关店铺","物流服务商简称","商品名称","SKU编码","商品条码","计划量","核对量","创建时间","快递单号"],
			colNames: ["ID","staId",i18("MEMO"),i18("STA_CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("OWNER"),i18("LPCODE"),i18("SKUNAME"),i18("SKUCODE"),i18("BARCODE"),i18("QUANTITY"),i18("COMPLETEQUANTITY"),i18("CREATETIME"),i18("TRANSNO")],
			colModel: [{name: "staLineId", index: "staLineId", hidden: true},
			            {name: "id", index: "id", hidden: true},
						{name: "memo", index: "memo", hidden: true},
						{name :"code",index:"code",formatter:"linkFmatter",formatoptions:{},width:100,resizable:true},
						{name:"intStatus", index:"intStatus" ,width:80,resizable:true,
							formatter:'select',editoptions:stastatus},
						{name: "refSlipCode", index:"refSlipCode",width:120,resizable:true},
						{name:"intType", index:"intType", width:90, resizable:true,
							formatter:'select',editoptions:statype},
						{name:"owner",index:"owner",width:120,resizable:true},
						{name:"lpcode",index: "lpcode",width:100,resizable:true,formatter:'select',editoptions:trasportName, hidden:true},
						{name:"skuName",index:"skuName",width:70,resizable:true},
						{name:"skuCode",index:"skuCode",width:70,resizable:true},
						{name:"barCode",index:"barCode",width:70,hidden: true},
		                {name:"quantity",index:"quantity",width:70,resizable:true},
		                {name:"completeQuantity",index:"completeQuantity",width:70,resizable:true},
		                {name:"createTime",index:"sta.CREATE_TIME",width:120,resizable:true},
		                {name:"transNo",index:"delinfo.TRACKING_NO",width:90,resizable:true, hidden:true}
						],
			caption: i18("WAITTING_CHECKED_LIST"),//待核对列表
		   	sortname: 'sta.CREATE_TIME',
		    multiselect: false,
			sortorder: "desc",
			width:"auto",
			height:"auto",
			rowNum:-1,
			viewrecords: true,
   			rownumbers:true,
   			gridComplete: function() {
   				var postData = {};
   				$j("#tbl-showDetail tr:gt(0)>td[aria-describedby$='barCode']").each(function (i,tag){
   					postData["skuBarcodes[" + i + "]"] = $j(tag).html();
   				});
   				barcodeList = loxia.syncXhr($j("body").attr("contextpath") + "/findskuBarcodeList.json",postData);
   				staCodeList = [];
   				$j("#tbl-showDetail tr:gt(0)>td[aria-describedby='tbl-showDetail_code']").each(function (i,tag){
   					var code = $j(tag).html();
   					var flag = true;
   					if(0 == staCodeList.length){
   						flag = false;
   					} else {
   						flag = false;
   						for(var i in staCodeList){
   							var c = staCodeList[i];
   							if(code == c){
   								flag = true;
   							}
   						}
   					}
   					if(false == flag){
   						staCodeList.push($j.trim(code));
   					}
   				});
   			},
			jsonReader: { repeatitems : false, id: "0" }
		});
		$j("#tbl-showPackaged").jqGrid({
			datatype: "json",
			//colNames: ["ID","物流服务商","快递单号","本次核对单据数","核对量","创建时间"],
			colNames: ["ID",i18("LPCODE"),i18("TRANSNO"),i18("PACKAGEDSKUQTY"),i18("PLANBILLCOUNT"),"plId",i18("OPERATOR")],
			colModel: [{name: "id", index: "id", hidden: true},
						{name:"lpcode",index: "lpcode",width:100,resizable:true,formatter:'select',editoptions:trasportName},
						{name:"trackingNo",index:"trackingNo",width:100,resizable:true},
		                {name:"checkQty",index:"checkQty",width:120,resizable:true},
		                {name:"planQty",index:"planQty",width:120,resizable:true},
		                {name: "plId", index: "plId", hidden: true},
		                {name:"operator",index:"sta.CREATE_TIME",width:190,resizable:true}
						],
			caption: i18("PACKAGED_LIST"),
		   	sortname: 'sta.status',
		    multiselect: false,
			sortorder: "desc",
			width:"auto",
			height:"auto",
			rowNum:-1,
			viewrecords: true,
   			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" },
			gridComplete: function(){
				var btn = '<div><button type="button" style="width:170px;" name="btnExecute" loxiaType="button" onclick="printPackaged(this);">'+i18("PRING_PACKAGED")+'</button></div>';
				var ids = $j("#tbl-showPackaged").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					$j("#tbl-showPackaged").jqGrid('setRowData',ids[i],{"operator":btn});
				}
				loxia.initContext($j(this));
			}
		});
		//已核对列表
		/*$j("#tbl-showReady").jqGrid({
			datatype: "json",
			//colNames: ["ID","备注","作业单号","状态","相关单据号","作业类型名称","相关店铺","物流服务商简称","商品名称","SKU编码","商品条码","计划量","核对量","创建时间","快递单号"],
			colNames: ["ID",i18("MEMO"),i18("STA_CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("OWNER"),i18("LPCODE"),i18("SKUNAME"),i18("SKUCODE"),i18("BARCODE"),i18("QUANTITY"),i18("COMPLETEQUANTITY"),i18("CREATETIME"),i18("CHECKTIME"),i18("TRANSNO")],
			colModel: [{name: "id", index: "id", hidden: true},
						{name: "memo", index: "memo", hidden: true},
						{name :"code",index:"code",formatter:"linkFmatter",formatoptions:{},width:100,resizable:true},
						{name:"intStatus", index:"intStatus" ,width:80,resizable:true,
							formatter:'select',editoptions:stastatus},
						{name: "refSlipCode", index:"refSlipCode",width:120,resizable:true},
						{name:"intType", index:"intType", width:90, resizable:true,
							formatter:'select',editoptions:statype},
						{name:"owner",index:"owner",width:120,resizable:true},
						{name:"lpcode",index: "lpcode",width:100,resizable:true,formatter:'select',editoptions:trasportName, hidden:true},
						{name:"skuName",index:"skuName",width:70,resizable:true},
						{name:"skuCode",index:"skuCode",width:70,resizable:true},
						{name:"barCode",index:"barCode",width:70,hidden: true},
		                {name:"quantity",index:"quantity",width:70,resizable:true},
		                {name:"completeQuantity",index:"completeQuantity",width:70,resizable:true},
		                {name:"createTime",index:"sta.CREATE_TIME",width:120,resizable:true},
		                {name:"checkTime",index:"checkTime",width:100,resizable:true},
		                {name:"transNo",index:"delinfo.TRACKING_NO",width:90,resizable:true, hidden:true}
						],
			caption: i18("CHECKED_LIST"),//已核对列表
		   	sortname: 'sta.status',
		    multiselect: false,
			sortorder: "desc",
			width:"auto",
			height:"auto",
			rowNum:-1,
			viewrecords: true,
   			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});*/
		//取消列表
		$j("#tbl-showCancel").jqGrid({
			datatype: "json",
			//colNames: ["ID","备注","作业单号","状态","相关单据号","作业类型名称","相关店铺","物流服务商简称","商品名称","SKU编码","商品条码","计划量","核对量","创建时间","快递单号"],
			colNames: ["ID",i18("MEMO"),i18("STA_CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("OWNER"),i18("LPCODE"),i18("SKUNAME"),i18("SKUCODE"),i18("BARCODE"),i18("QUANTITY"),i18("COMPLETEQUANTITY"),i18("CREATETIME"),i18("CHECKTIME"),i18("TRANSNO")],
			colModel: [{name: "id", index: "id", hidden: true},
						{name: "memo", index: "memo", hidden: true},
						{name :"code",index:"code",formatter:"linkFmatter",formatoptions:{},width:100,resizable:true},
						{name:"intStatus", index:"intStatus" ,width:80,resizable:true,
							formatter:'select',editoptions:stastatus},
						{name: "refSlipCode", index:"refSlipCode",width:120,resizable:true},
						{name:"intType", index:"intType", width:90, resizable:true,
							formatter:'select',editoptions:statype},
						{name:"owner",index:"owner",width:120,resizable:true},
						{name:"lpcode",index: "lpcode",width:100,resizable:true,formatter:'select',editoptions:trasportName, hidden:true},
						{name:"skuName",index:"skuName",width:70,resizable:true},
						{name:"skuCode",index:"skuCode",width:70,resizable:true},
						{name:"barCode",index:"barCode",width:70,hidden: true},
		                {name:"quantity",index:"quantity",width:70,resizable:true},
		                {name:"completeQuantity",index:"completeQuantity",width:70,resizable:true},
		                {name:"createTime",index:"sta.CREATE_TIME",width:120,resizable:true},
		                {name:"checkTime",index:"checkTime",width:100,resizable:true},
		                {name:"transNo",index:"delinfo.TRACKING_NO",width:90,resizable:true, hidden:true}
						],
			caption: i18("取消列表"),//取消列表
		   	sortname: 'sta.status',
		    multiselect: false,
			sortorder: "desc",
			width:"auto",
			height:"auto",
			rowNum:-1,
			viewrecords: true,
   			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
	}else{
		jumbo.showMsg(i18("INIT_SYSTEM_DATA_EXCEPTION"));//初始化系统参数异常
	}
	
	$j("#reloadSta").click(function(){
		//$j("#tbl-showReady").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/pllistforverify.json"),postData:{"plCmd.id":0}}).trigger("reloadGrid",[{page:1}]);
		showDetail($j("#tbl-dispatch-list").jqGrid("getRowData",$j("#showDetail").attr("plId"))['code']);
		reloadSta($j("#showDetail").attr("plId"));
	});
	$j("#printDeliveryInfo").click(function(){
		    loxia.lockPage();
			var staId = $j("#showDetail").attr("staId");
			var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?sta.id=" + staId;
			printBZ(loxia.encodeUrl(url),true);					
		    loxia.unlockPage();
    });
	$j("#tbl-billView").jqGrid({
		//url:"dispatch-list-view.json",
		datatype: "json",
		//colNames: ["ID","库位编码预览","商品名称","商品编码","条形码","计划执行数量","已执行数量"],
		colNames: ["ID","是否SN商品",i18("SKUCODE"),i18("LOCATIONCODE"),i18("SKUNAME"),i18("JMSKUCODE"),"货号",i18("KEY_PROPS"),i18("BARCODE"),i18("QUANTITY"),i18("COMPLETEQUANTITY"),i18("SN_CODE")],
		colModel: [{name: "id", index: "id", hidden: true},
		            {name: "isSnSku", index: "isSnSku", hidden: true},//是否SN商品
					{name:"skuCode", index:"skuCode" ,width:150,resizable:true,sortable:false},
					{name :"locationCode",index:"locationCode",width:120,resizable:true,hidden: true},
					{name:"skuName", index:"skuName" ,width:150,resizable:true,sortable:false},
					{name: "jmcode", index:"jmcode",width:150,resizable:true,sortable:false},
					{name: "supplierCode", index:"sku.supplier_code",width:150,resizable:true,sortable:false},
					{name: "keyProperties", index:"keyProperties",width:60,resizable:true,sortable:false},
					{name:"barCode", index:"barCode", width:90,hidden: true, resizable:true},
					{name:"quantity",index:"quantity",width:120,resizable:true,sortable:false},
	                {name:"completeQuantity",index:"completeQuantity",width:100,resizable:true,sortable:false},
					{name: "idForBtn", width: 80, formatter:"buttonFmatter",sortable:false, formatoptions:{"buttons":{label:i18("VIEW_SN"), onclick:"viewSn(this)"}}}],
		caption: i18("CHECKED_LIST"),//已核对列表
	   	sortname: 'sku.code',
	    multiselect: false,
		sortorder: "desc",
		rownumbers:true,
		rowNum: -1,
		height:"auto",
		gridComplete: function() {
			$j("#tbl-billView tr:gt(0)").each(function(i,tag){
				var isSn = $j(tag).children("td:eq(2)").attr("title");
				if(isSn==0){
					$j(tag).children("td:last").html("");
				}
			});
			var postData = {};
			$j("#tbl-billView tr:gt(0)>td[aria-describedby$='barCode']").each(function (i,tag){
				postData["skuBarcodes[" + i + "]"] = $j(tag).html();
			});
			barcodeList = loxia.syncXhr($j("body").attr("contextpath") + "/findskuBarcodeList.json",postData);
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#iptPlCode").focus();
	$j("#snDiv").hide();
	
	$j("#iptPlCode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			$j("#backTarget").val(this.id);
			//var _this=$j(".refSlipCode");
			var _this=this;
			var plCode = $j.trim(this.value);
			if(plCode==""){
				loxia.tip(_this,i18("INPUT_CODE"));//请输入批次号
				return;
			}
			var sortingModeInt = $j("#sortingModeInt").val();
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/json/plCodeCheck.json",
				{"cmd.code":plCode,
				"cmd.sortingModeInt":sortingModeInt},
				{success:function (data) {
					loxia.unlockPage();
					if(data&&data.id){
						showDetail(null, data);
					}else{
						loxia.tip(_this,i18("NO_CODE"));//指定批次号不存在！
						initInput();
					}
				}
			});
		  loxia.unlockPage();
		}
	});
	
	$j(".refSlipCode").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			$j("#backTarget").val(this.id);
			//var _this=$j(".refSlipCode");
			var _this=this;
			var refSlipCode = this.value;
			if(this.id == 'iptPlCode'){
				refSlipCode = $j("#refSlipCode").val();
				$j("#backTarget").val("refSlipCode");
			}
			if(refSlipCode==""){
				loxia.tip(_this,i18("INPUT_CODE"));//请输入相关单据号
				return;
			}
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/json/staSlipCodeCheck.json",
				{"sta.refSlipCode":refSlipCode,
				 "sta.pickingCode":$j("#iptPlCode").val()},
				{success:function (data) {
					loxia.unlockPage();
					if(data&&data.id){
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
						if(data['hasCancelUndoSta'] == 'true'){
							//在所有批次中存在取消未处理订单，要求输入批次号
							jumbo.showMsg("请输入批次号");
							$j("#iptPlCode").focus();
						}else{
							//进入核对界面
							var ext = data['extTransOrderId'];
							var isCod = data['isCod'];
							var lpCode = data['lpcode'];
							if(ext != "" && ext != null && lpCode != "JD" && isCod != 1){
								$j("#tbl-trank-list").removeClass("hidden");
								$j("#tbl-trank-button").removeClass("hidden");
							}else{
								$j("#tbl-trank-list").addClass("hidden");
								$j("#tbl-trank-button").addClass("hidden");
							}
							
							$j("#tbl-billView").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/staLineInfoByStaId.json"),
								postData:{"sta.id":data.id}}).trigger("reloadGrid",[{page:1}]);
							$j("#staCode").html(data['code']);
							$j("#staId").val(data['id']);
							$j("#staCreateTime").html(data['createTime']);
							$j("#verifyPlId").val(data['pickingList.id']);
							$j("#tbl-express-order tr:gt(0)").remove();
							$j("#tbl-trank-list tr:gt(0)").remove();
							var transName = loxia.syncXhrPost($j("body").attr("contextpath")+"/json/getTrasportName.json",{"optionKey":data['lpcode']});
							var stastatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/optionvalue.json",{"categoryCode":"whSTAStatus","optionKey":data["intStatus"]}),
								statype=loxia.syncXhr($j("body").attr("contextpath") + "/json/optionvalue.json",{"categoryCode":"whSTAType","optionKey":data["intType"]});
							$j("#staDelivery").html(transName.value.length>0?transName.value.split(":")[1]:'');
							$j("#staTranType").html(statype.optionValue);
							$j("#staRefCode").html(data['refSlipCode']);
							$j("#staStatus").html(stastatus.optionValue);
							$j("#staComment").html(data['memo']);
							 $j("#staTransNo").html(data['transNo']);
							$j("#staTotal").html(data['stvTotal']);
							$j("#showList").addClass("hidden");
							$j("#showDetail").addClass("hidden");
							$j("#showDetail").attr("plId",data['pickingList.id']);
							$j("#showDetail").attr("staId",data.id);
							$j("#file").val("");
							$j("#checkBill").removeClass("hidden");
							$j("#barCode").focus();
							var isPostpositionPackingPage = data['isPostpositionPackingPage'];
							var isPostpositionExpressBill = data['isPostpositionExpressBill'];
							if(1 == isPostpositionExpressBill && 0 == isPostpositionPackingPage){
								$j("#isPostpositionExpressBill").val(1);
								$j("#isPostpositionPackingPage").val(0);
								$j("#express_order").val(data['transNo']);
								var e = $j.Event("keydown");
					            e.keyCode =13;
					            isBarCode = true;
					            $j("#express_order").trigger(e);
								$j("#express_order").attr("disabled", "disabled");
								$j("#tbl-express-order").hide();
								$j("#express_order").val(data['transNo']);
							}
							else{
								$j("#isPostpositionExpressBill").val(0);
								$j("#isPostpositionPackingPage").val(0);
								$j("#express_order").val("").attr("disabled", "");
								$j("#tbl-express-order").show();
							}
							initInput();
//							if(data['intCheckMode'] == 5){
//								errorTip("请使用配货批核对模式加快核对效率");
//							}
						}
					}else{
						loxia.tip(_this,i18("NO_CODE"));//指定单据号的作业单不在待核对的列表！
						initInput();
					}
				}
			});
		}
	});
	
	$j("#doCheck_recheck").click(function(){
		var data = $j("#tbl-billView").jqGrid('getRowData');
		$j.each(data,function(i, item){
			var d = $j("#tbl-billView").jqGrid('getRowData',item.id);
			d.completeQuantity = 0;
			$j("#tbl-billView").jqGrid('setRowData',item.id,d);
		});
		$j("#dialog_error_recheck").dialog("close");
		$j("#barCode").focus();
		$j("#doCheck_recheck").val("");
	});
	
	$j("#barCode_recheck").keydown(function(evt){
		var barCode1 = $j("#barCode_recheck").val();
		if(BARCODE_CONFIRM ==barCode1){
			$j("#doCheck_recheck").trigger("click");
		}
	});
	
	$j("#barCode").keydown(function(evt){
		if(evt.keyCode == 9){
			evt.preventDefault();
			$j("#express_order").select();
		}
		if(evt.keyCode === 13){
			var barCode = $j("#barCode").val();
			if(barCode == "RPHOTO"){
				$j("#newCamera").click();
				$j("#barCode").val("");
				
				return ;
			}
			$j(document).scrollTop(0);
			$j(document).scrollTop($j(document).scrollTop() + 1);
			$j("#flag0").val("barCode");
			evt.preventDefault();
			var rowid = 0;
			bc=$j.trim($j(this).val());
			rs=false;
			if(bc.length==0)
				return false;
			var data = $j("#tbl-showDetail").jqGrid('getRowData');
			var item;
			var temp = null;
			var bc1;
			$j.each(data,function(i, item1){
				item = item1;
				bc1=item1.barCode;
				var num = item.completeQuantity;
				if(num==null||num=='')num=0;
				//00 条形码
				if(bc1==bc||bc1=="00"+bc){
					temp = item;
					if(parseInt(num)+parseInt($j("#inQty").val())<=item.quantity){
						rs=true;
						return false;
					}
				}else{
					for(var v in barcodeList[bc1]){
						if(barcodeList[bc1][v] == bc || barcodeList[bc1][v] == ('00'+bc) ){
							temp = item;
							if(parseInt(num)+parseInt($j("#inQty").val())<=item.quantity){
								rs=true;
								return false;
							}
						}
					}
				}
			});
			if(!rs && temp != null){
				initCheck();
				removeCursor();
				var msg = temp.skuName+" 您要核对量大于计划执行量，请重新核对！";
				errorTipOK(i18(msg));
				rs=true;
				return;
			}
			if(rs){
				var num = item.completeQuantity;
				if(num==null||num=='')num=0;
				//数量判断
				if(item.isSnSku==1){
					if(parseInt(num)+1>item.quantity){
						initCheck();
						removeCursor();
						var msg = item.skuName+" 您要核对量大于计划执行量，请重新核对！";
						errorTipOK(i18(msg));
						rs=true;
						return;
					}else{
						$j("#snDiv").show();
						$j("#snCode").focus();
						lineData = item;
						rs=true;
						return;
					}
				}else{
					if(parseInt(num)+parseInt($j("#inQty").val())>item.quantity){
						initCheck();
						removeCursor();
						var msg = item.skuName+" 您要核对量大于计划执行量，请重新核对！";
						errorTipOK(i18(msg));
						rs=true;
						return;
					}else{
						var pNo = $j.trim($j("#verifyCode").html());
						var barcode = $j.trim($j("#barCode").val());
						var staNo = $j.trim($j("#staCode").html());
						var timestamp=new Date().getTime();
						try{
							cameraPhoto(pNo, staNo, barcode,timestamp);//拍照
							fileUrl = dateValue + "/" + ouName + "/" + pNo + "/" + staNo+"/"+barcode + "_" + userName+"_"+timestamp;
							cameraPaintMultiJs(fileUrl);
						} catch (e) {
//							jumbo.showMsg(i18("拍照失败！"));//初始化系统参数异常
						};
						$j(document).scrollTop($j(document).scrollTop() - 1); 
						var d = $j("#tbl-showDetail").jqGrid('getRowData',item.staLineId);
						var num =  d.completeQuantity;
						if(num==null||num=='')num=0;
						var inQty = parseInt($j("#inQty").val());
						d.completeQuantity = parseInt(num)+inQty;
						$j("#inQty").val(1);
						$j("#tbl-showDetail").jqGrid('setRowData',item.staLineId,d);
						rs=true;
						$j("#barCode").val("");
						$j("#barCode").focus();
						loxia.tip(this);
						//如果计划量等于执行量则光标跳到快递单输入框
						removeCursor();
						var isPostpositionPackingPage = $j("#isPostpositionPackingPage").val();
						var isPostpositionExpressBill = $j("#isPostpositionExpressBill").val();
						if('1' == isPostpositionExpressBill && '0' == isPostpositionPackingPage){
							postprint();//后置打印面单
						}
						//$j("#tbl-billView tr[id="+item.id+"]").children("td:last").html("");
						return;
					}
				}
			}else{
				playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
				errorTipOK(i18("BARCODE_NOT_EXISTS"));
				return;
			}
		}
	});
	
	$j("#express_order").keydown(function(evt){
		var express_order = $j("#express_order").val();
		if(express_order == "RPHOTO"){
			$j("#newCamera").click();
			$j("#express_order").val("");
			
			return ;
		}
		if(evt.keyCode === 13){
			evt.preventDefault();
			//检查快递单号是否已扫
			var ids = $j("#tbl-express-order tr:gt(0)");
			if(ids.length==1){
				errorTipOK(i18("EXIST_TRACKINGNO"));
				$j("#express_order").val("");
				return false;
			}
			var transNo = $j("#trackingNo").val();
			if(transNo != "" && transNo != $j.trim($j(this).val())){
				//errorTip("快递单号不匹配！");
				errorTipOK("快递单号不匹配！");
				return;
			}
			//获取待核对列表数据
			var datasource = $j("#tbl-showDetail").jqGrid('getRowData');
			var transNo = $j("#verifyCode").html();  //获取批次号
			var inputCode= $j("#express_order").val();  //获取输入的快递单号
			var flag = false;
			$j.each(datasource,function(i, item) {
				var slipCode=item.refSlipCode;
				if(inputCode!="" && inputCode == slipCode){
					flag=true;
				}
			});
			//快递单号，不能输入OK条码、配货清单编码以及订单相关单据号。
			if(flag==true || transNo==inputCode || inputCode.toUpperCase()=="OK".toUpperCase()){
				errorTipOK("非正确的快递单号！");
				return;
		
			}
			
			var billId = $j.trim($j(this).val());
			var ch = $j("#"+billId);
			if(ch.length!=0){
				 //loxia.tip(this,i18("TRACKINGNO_EXISTS"));//快递单号已经存在
				 $j("#flag0").val("express_order");
				 //errorTip(i18("TRACKINGNO_EXISTS"));
				 errorTipOK(i18("TRACKINGNO_EXISTS"));
//				 $j(this).select();
				 return;
			}else{
				loxia.tip(this);
			}
			//校验快递单号的合法性
			var isCheck = true;
			var lpcode = $j.trim($j("#lpcode").val());
			if(null == lpcode || '' == lpcode){
				isCheck = false;
			}
			var data ={trackingNo:billId};
			data["lpcode"]=lpcode;
			var rs = {};
			if(true == isCheck){
				rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/plchecktrackingno.json",data);
			}else{
				rs.result = 'success';
			}
			if(rs && rs.result=='success'){
				if(billId != ""&&billId != null){
					var str = "<tr id=\""+billId+"\"><td class='label' height='36px' style='text-align: center;font-size: 36px' >"+billId+"</td><td>" +
					"<button style='width: 200px;height: 45px;font-size=36px' onclick=\"doDelete('"+billId+"')\">"+i18("DELETE")+"</button></td></tr>";//删除	
					$j("#tbl-express-order").append(str);			
				}
				loxia.tip(this);
				$j(this).val("");
				$j(this).focus();
			}else {
				$j("#flag0").val("express_order");
//				errorTip(i18("TRACKINGNO_RULE_ERROR"));
				errorTipOK(i18("TRACKINGNO_RULE_ERROR"));
				//loxia.tip(this,i18("TRACKINGNO_RULE_ERROR"));//快递单号格式不对
//				$j(this).select();
				return ;
			}
			//var trankNo = $j("#staTransNo").html();
			if(!isBarCode){
				$j("#barCode1").focus();
			}else{
				$j("#barCode").focus();
			}
			
		}});
	
	$j("#backToPlList").click(function(){
		//window.location=loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/plverifyentry.do");
		try{
			closeGrabberJs();				
		}catch (e) {
//						jumbo.showMsg(i18("关闭摄像头失败！"));//初始化系统参数异常
		}
		$j("#showDetail").attr("plId");
		$j("#showList").removeClass("hidden");
		$j("#showDetail").addClass("hidden");
		$j("#checkBill").addClass("hidden");
		//清空输入框
		$j("#express_order").val("");
		$j("#barCode0").val("");
		$j("#barCode1").val("");
		initInput();
		$j("#lpcode").val("");
		$j("#search").click();
		$j("#iptPlCode").focus();
	});
	
	$j("#Back").click(function(){
		try{
			closeGrabberJs();				
		}catch (e) {
//						jumbo.showMsg(i18("关闭摄像头失败！"));//初始化系统参数异常
		}
		//$j("#tbl-dispatch-list").jqGrid().trigger("reloadGrid",[{page:1}]);
		/*loxia.tip($j("#barCode"));
		loxia.tip($j("#express_order"));
		if($j("#backTarget").val()=="refSlipCode"){
			$j("#showList").removeClass("hidden");
			$j("#checkBill").addClass("hidden");
			$j("#refSlipCode").focus();
		}else{
			//$j("tr[id="+$j("#verifyPlId").val()+"] a").trigger("click");
			//reloadSta($j("#verifyPlId").val());
			$j("#showDetail").attr("plId");
			$j("#showList").addClass("hidden");
			$j("#showDetail").removeClass("hidden");
			$j("#checkBill").addClass("hidden");
		}*/
		$j("#showDetail").attr("plId");
		$j("#showList").removeClass("hidden");
		$j("#showDetail").addClass("hidden");
		$j("#checkBill").addClass("hidden");
		//清空输入框
		$j("#express_order").val("");
		$j("#barCode0").val("");
		$j("#barCode1").val("");
		initInput();
		$j("#lpcode").val("");
		$j("#search").click();
		$j("#iptPlCode").focus();
		//$j("#backToPlList").click();
		
	});
	
	$j("#addTrank").click(function(){
		var data = {};
		var staId = $j("#staId").val();
		data["sta.id"]=staId;
		var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/staAddTrankNo.json",data);
		if(rs && rs.msg == 'success'){
			var temp = rs.trankCode;
			var trankCode = temp.substring(0,temp.indexOf(","));
			var packId = temp.substring(temp.indexOf(",")+1,temp.length);
			var str = "<tr id=\""+trankCode+"\"><td class='label' height='36px' style='text-align: center;font-size: 36px' >"+trankCode+"</td><td>" +
			"<button style='width: 200px;height: 45px;font-size=36px' onclick=\"printTrank('"+staId+"','"+packId+"')\">"+"打印面单"+"</button></td></tr>";//删除	
			$j("#tbl-trank-list").append(str);		
		}else{
			if(rs.errMsg != null){
				jumbo.showMsg(rs.errMsg);
			}
		}
	});
	
	//执行核对
	$j("#doCheck").click(function(){
		var startTime1 = new Date().getTime();
		if(startTime1 - startTime < 500) return;
		startTime = startTime1;
		//验证计划量和执行量
		var isCheck = false;
		var flag = true;
		var staCodeCheckList = [];
		var staCheckList = [];
		var sta = {};
		var staFlag = true;
		var datax = $j("#tbl-showDetail").jqGrid('getRowData');
		if(datax.length == 0){
			jumbo.showMsg("本单已全部核对完成，请继续下一步操作！");
			return;
		}
		for(var i in staCodeList){
			var currentCode = $j.trim(staCodeList[i]);
			$j.each(datax,function(i, item) {
				var staCode = $j.trim(item.code);
				if(currentCode == staCode) {
					var n1= parseInt(item.quantity);
					var cq = item.completeQuantity;
					if(cq==''||cq==null){
						 cq='0';
					}
					var n2= parseInt(cq);
					if(0 != n2){
						isCheck = true;
						if(n2<n1){
							initCheck();
							var msg = currentCode+"、"+item.skuName+" 已执行量小于计划执行量，请继续核对！";
							//errorTip(i18(msg));
							errorTipOK(i18(msg));
							flag = false;
							return;
						}
						if(n2>n1){
							initCheck();
							var msg = currentCode+"、"+item.skuName+" 已执行量大于计划执行量，请重新核对！";
							//errorTip(i18(msg));
							errorTipOK(i18(msg));
							//核对数量归零
							item["completeQuantity"] = 0;
							$j("#tbl-showDetail").jqGrid('setRowData',item.id,item);
							loxia.initContext($j("#tbl-showDetail"));
							flag = false;
							return;
						}
						if(false == flag){
							return;
						}
						staFlag = true;
	   					if(0 == staCodeCheckList.length){
	   						staFlag = false;
	   					} else {
	   						staFlag = false;
	   						for(var i in staCodeCheckList){
	   							var c = staCodeCheckList[i];
	   							if(currentCode == c){
	   								staFlag = true;
	   							}
	   						}
	   					}
	   					if(false == staFlag){
	   						staCodeCheckList.push(currentCode);
	   						sta = {};
	   						sta.id = item.id;
	   						sta.code = currentCode;
	   						sta.refSlipCode = item.refSlipCode;
	   						staCheckList.push(sta);
	   						
	   					}
					}
				}
			});	
		}
			if(false == isCheck){
				$j("#barCode").focus();
				return;
			}
			flag = true;
			$j.each(datax,function(i, item) {
				var staCode = $j.trim(item.code);
				var n1= parseInt(item.quantity);
				var cq = item.completeQuantity;
				if(cq==''||cq==null){
					 cq='0';
				}
				var n2= parseInt(cq);
				if(0 == n2){
					for(var i in staCodeCheckList){
						var currentCode = $j.trim(staCodeCheckList[i]);
						if(currentCode == staCode){
							if(n2<n1){
								initCheck();
								var msg = currentCode+"、"+item.skuName+" 已执行量小于计划执行量，请继续核对！";
								//errorTip(i18(msg));
								errorTipOK(i18(msg));
								flag = false;
								return;
							}
						}
					}
					if(false == flag){
						return;
					}
					
				}
			});
			if(!flag){
				$j("#flag0").val("barCode");
				return ;
			}
			//检查快递单号
			var ids = $j("#tbl-express-order tr:gt(0)");
			if(ids.length != 1){
				if(ids.length==0){
					$j("#flag0").val("express_order");
					errorTipOK(i18("INPUT_TRACKINGNO"));
					return false;
				}
				if(ids.length>1){
					errorTipOK(i18("EXIST_TRACKINGNO"));
					$j("#express_order").val("");
					return false;
				}
			}
			var isTransNoLegal = true;
			var data={};
			$j.each(ids,function(i, item) {
				 var id =  item.id;
				 if("" == $j.trim(id) || null == id){
					 isTransNoLegal = false;
					 return;
				 }
				  data["packageInfos[" + i + "].trackingNo"] = id;
			});
			if(false == isTransNoLegal){
				errorTipOK(i18("INPUT_TRACKINGNO"));
				return;
			}
		    //var staId = $j("#staId").val();
		    //data["sta.id"]=staId;
			var plId = $j.trim($j("#verifyPlId").val());
			data["pickinglistId"] = plId;
			var plpId = $j.trim($j("#plpId").val());
			data["pickinglistPackageId"] = plpId;
		    $j.each(staCheckList,function(i, item) {
		    	//作业单号
		    	data["staList[" + i + "].code"] = item.code;
		    	//相关单据号
		    	data["staList[" + i + "].refSlipCode"] = item.refSlipCode;
		    	//主键
		    	data["staList[" + i + "].id"] = item.id;
		    });
		    data["lineNo"] = lineNo;
		    //SN号数据提交
		    for(var i = 0 ; i < snArray.length;i++){
		    	var sn = snArray[i].split(",");
    			data["snlist["+ i +"]"] = sn[1];
		    }
		    if(('' == plId || null == plId) || ('' == plpId || null == plpId)) {
		    	jumbo.showMsg("系统参数异常无法执行核对，请刷新页面");
		    	return;
			}
//		    try{
//				var pNo = $j.trim($j("#verifyCode").html());
//				var staCode = $j.trim($j("#staCode").html());
//				var timestamp=new Date().getTime();
//				creatZipJs(dateValue + "/" + pNo + "/" + staCode,timestamp);//打包图片
//				updateHttpJs(dateValue + "/" + pNo + "/" + staCode,timestamp);//发送图片zip http服务器
//				loxia.syncXhr($j("body").attr("contextpath") + "/creatStvCheckImg.json",{"staCode":staCode,"fileUrl":"/"+dateValue + "/" + pNo + "/" + staCode,"fileName":fileName});//保存STV图片路径
//			} catch (e) {
//			};
		    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/o2oqsSortingCheck.json",data);
			if(rs.result=='success'){
				//$j("#Back").trigger("click");
				//刷新数据
//				var plId = $j("#verifyPlId").val();
//				var data = {};
//				data.id = plId;
//				showDetail(null, data);
				var sortingModeInt = $j("#sortingModeInt").val();
				var plCode = $j.trim($j("#verifyCode").html());
				loxia.asyncXhrPost($j("body").attr("contextpath") + "/json/plCodeCheck.json",
					{"cmd.code":plCode,
					"cmd.sortingModeInt":sortingModeInt},
					{success:function (data) {
						if(data&&data.id){
							showDetail(null, data);
						}else{
							loxia.tip(_this,i18("NO_CODE"));//指定批次号不存在！
						}
					}
				});
			}else {
				$j("#flag0").val("barCode1");
				if(rs){
					var msg = rs.msg;
					if('' != msg && msg.indexOf("无法流水开票") > 0){
						isNotLineNo='true';
					}
					//errorTip(i18("OPERATE_FAILED")+"<br />"+rs.msg);
					errorTipOK(rs.msg);
				}
			}
	});
	
	loxia.byId("inQty").setEnable(false);
	
	$j("#btnInQty").click(function(){
		loxia.byId("inQty").setEnable(true);
		$j("#inQty").select(); 
	});
	
	$j("#inQty").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			if(loxia.byId("inQty").check() == false){
				$j("#inQty").select(); 
			}else{
				loxia.byId("inQty").setEnable(false);
				$j("#barCode").select(); 
			}
		}
	});
	
	$j("#barCode0").keyup(function(evt){
		expressCommon();
	});
	
	//确认按钮
	$j("#doCheck0").click(function(){
		expressCommon0();
	});
	
	
	$j("#barCode1").keyup(function(evt){
			var barCode1 = $j("#barCode1").val();
				if(barCode1 == "RPHOTO"){
					$j("#newCamera").click();
					$j("#barCode1").val("");
					return ;
				}
			
			if(BARCODE_CONFIRM ==barCode1){
			$j("#barCode1").val("");
			//执行核对
			$j('#doCheck').trigger('click');
			}
			
	});
	
	//扫描SN号后
	$j("#snCode").keydown(function(evt){
		if(evt.keyCode === 13){
			$j("#flag0").val("snCode");
			evt.preventDefault();
			checkSn();
		}
	});
	
		//sn号导入
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/salesoutsnimport.do?sta.id="+$j.trim($j("#staId").val()));
		$j("#importForm").submit();
	});
	
	//重拍
	$j("#newCamera").click(function() {
		$j(document).scrollTop(0);
		$j(document).scrollTop($j(document).scrollTop() + 1);
		var pNo = $j.trim($j("#verifyCode").html());
		var staNo = $j.trim($j("#staCode").html());
		var timestamp=new Date().getTime();
		try{
			cameraPhotoJs(dateValue + "/" + ouName + "/" + pNo + "/" + staNo, userName+"_"+timestamp);
			fileUrl = dateValue + "/" + ouName + "/" + pNo + "/" + staNo+"/"+userName+"_"+timestamp;
			fileName += userName+"_"+timestamp+".jpg"+"/";
			cameraPaintMultiJs(fileUrl);
		} catch (e) {
//			jumbo.showMsg(i18("重拍失败！"));//初始化系统参数异常
		};
		$j(document).scrollTop($j(document).scrollTop() - 1);
		$j("#barCode").val("");
		$j("#barCode").focus();
	});
});

/******************商品SN导入 begin*********************/
function importInit(snList){
	var datas = $j("#tbl-billView").jqGrid('getRowData');
	var errors = "";
	for(var index in snList){
		var barcode = snList[index].barcode;
		var sn= snList[index].sn;
		if(snArray.contains(barcode+","+sn)){
			errors = errors + "该SN已经扫描过:" + sn;
			continue;
		}
		var isAddNum = false;
	
		for(var i = 0; i < datas.length; i++){
			var data = datas[i];
			if(data["barCode"] == barcode){
				var num =  data["completeQuantity"];
				if(num==null||num=='')num=0;
				if(parseInt(num)+1 <= data["quantity"]){
					data["completeQuantity"] = parseInt(num)+1;
					isAddNum = true;
					break;
				}
			}
		}
		if(!isAddNum){
			errors = errors + "该SKU["+barcode+"]已经超出计划量";
		}
	}
	if(errors == ""){
		for(var index in snList){
			snArray.push(snList[index].barcode+","+snList[index].sn);
		}
		for(var i in datas){
			if(i == 'contains') break;
			$j("#tbl-billView").jqGrid('setRowData',datas[i]["id"],{"completeQuantity": datas[i]["completeQuantity"]});
		}
		loxia.initContext($j("#tbl-billView"));
		jumbo.showMsg("SN序列号导入成功");
	} else {
		jumbo.showMsg(errors);
	}
	//如果计划量等于执行量则光标跳到快递单输入框
	removeCursor();
}
/******************商品SN导入 end*********************/

//SN判断
function checkSn(){
	var bc = $j("#snCode").val().trim();
	if(bc==""){
		return;
	}
	//SN号校验
	var result = loxia.syncXhr($j("body").attr("contextpath") + "/findBarcodeBySn.json",{"sn":bc});
	if(result && result.result == "success"){
		$j(document).scrollTop(0);
		$j(document).scrollTop($j(document).scrollTop() + 1);
		if(result.barcode==lineData.barCode){
			if(snArray.contains(lineData.barCode+","+bc)){
				snCheck();
				//errorTip(i18("该SN已经扫描过:"+bc));
				errorTipOK(i18("该SN已经扫描过:"+bc));
				removeCursor();
				return;
			}
			var data=$j("#tbl-billView").jqGrid("getRowData",lineData.id);
			var num =  data.completeQuantity;
			if(num==null||num=='')num=0;
			if(parseInt(num)+1>data.quantity){
				initCheck();
				removeCursor();
				return;
			}
			data.completeQuantity = parseInt(num)+1;
			$j("#tbl-billView").jqGrid('setRowData',lineData.id,data);
			loxia.initContext($j("#tbl-billView"));
			snArray.push(lineData.barCode+","+bc);
			var pNo = $j.trim($j("#verifyCode").html());
			var barcode = $j.trim($j("#barCode").val());
			var staNo = $j.trim($j("#staCode").html());
			var timestamp=new Date().getTime();
			try{
				cameraPhoto(pNo, staNo, barcode,timestamp);//拍照
				fileUrl = dateValue + "/" + ouName + "/" + pNo + "/" + staNo+"/"+barcode + "_" + userName+"_"+timestamp;
				cameraPaintMultiJs(fileUrl);
			} catch (e) {
//				jumbo.showMsg(i18("拍照失败！"));//初始化系统参数异常
			};
			$j(document).scrollTop($j(document).scrollTop() - 1); 
			//SN需要继续扫描
			if(parseInt(num)+1<data.quantity){
				snCheck();
			}else{
				initCheck();
			}
			
		}else{
			snCheck();
			//errorTip(i18("SN对应的条形码和之前扫描的不匹配"));
			errorTipOK(i18("SN对应的条形码和之前扫描的不匹配"));
		}
	}else{
		snCheck();
		//errorTip(i18("SN号不存在"));
		playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
		errorTipOK(i18("SN号不存在"));
	}
	//如果计划量等于执行量则光标跳到快递单输入框
	removeCursor();
	var isPostpositionPackingPage = $j("#isPostpositionPackingPage").val();
	var isPostpositionExpressBill = $j("#isPostpositionExpressBill").val();
	if('1' == isPostpositionExpressBill && '0' == isPostpositionPackingPage){
		postprint();//后置打印面单
	}
}

function expressCommon(){
	var barCode1 = $j("#barCode0").val();
	if(BARCODE_CONFIRM ==barCode1){
		expressCommon0()
	}
}

function expressCommon0(){
	$j("#dialog_error").dialog("close");
	$j("#express_order").val("");
	var flag0 = $j("#flag0").val();
	if(flag0=='express_order'){
		$j("#express_order").val("");
	    $j("#express_order").focus();
	}
	else if(flag0=='barCode0'){
		$j("#barCode0").val("");
		$j("#barCode0").focus();
	}else if(flag0=='barCode1'){
		$j("#barCode1").val("");
		$j("#barCode1").focus();
	}else if(flag0=='barCode'){
		$j("#barCode").val("");
		$j("#barCode").focus();
	}
	$j("#barCode1").val("");
	if(isNotLineNo == "true"){
		jumbo.removeAssemblyLineNo();
		lineNo = jumbo.getAssemblyLineNo();
	}
	if(flag0='snCode'){
		$j("#snCode").val("");
		$j("#snCode").focus();
	}
}

function doDelete(id){
	$j("#"+id).remove();
}

//执行光标移动
function removeCursor(){
	var datax = $j("#tbl-showDetail").jqGrid('getRowData');
	var flag = true;
	$j.each(datax,function(i, item) {
		var n1= parseInt(item.quantity);
		var cq = item.completeQuantity;
		if(cq==''||cq==null){
			 cq='0';
		}
		var n2= parseInt(cq);
		if(n2<n1){
			flag = false
		}
	});
	if(flag){
	 $j("#doCheckDetail").removeClass("hidden");
	 $j("#express_order").focus();
	}
}

//弹出
function errorTip(text0){
	$j("#errorText").html("<font style='text-align:center;font-size:36px' color='red'>"+text0+"</font>");
	$j("#barCode0").val("");
	$j("#dialog_error").dialog("open");
	$j("#barCode0").focus();
}

function errorTipOK(text) {
	$j("#error_text_ok").html("<font style='text-align:center;font-size:36px' color='red'>"+text+"</font>");
	$j("#dialog_error_ok").dialog("open");
}

function snCheck(){
	$j("#snCode").val("");
	$j("#snCode").focus();
}

function initCheck(){
	$j("#barCode").val("");
	$j("#snCode").val("");
	$j("#snDiv").hide();
	$j("#barCode").focus();
}

//初始化输入框
function initInput(){
	$j("#barCode").val("");
	$j("#snCode").val("");
	snArray = new Array();
	$j("#snDiv").hide();
	$j("#refSlipCode2").val("");
	$j("#refSlipCode").val("");
	$j("#iptPlCode").val("");
}

//SN查看
function viewSn(tag){
	$j("#dialog-sns").dialog("open");
	$j("#divSn div.snDiv").remove();
	//var data = $j(tag).parents("tr").data("sn")
	var row=$j(tag).parent().parent().attr("id");
	sta=$j("#tbl-billView").jqGrid("getRowData",row);
	var barCode = sta.barCode;
	for(var d in snArray){
		var sn = "";
		if(snArray.length != 0){
			sn = snArray[d].split(",");
		}
		if(barCode==sn[0]){
			$j("#divClear").before("<div class='snDiv'>" + sn[1] + "</div>");
		}
	}
}

//数组判断包含
Array.prototype.contains = function(e){
	for(i=0;i<this.length;i++){
		if(this[i] == e)
		return true;
	}
	return false;
}

//拍照bin.hu
function cameraPhoto(pNo, staNo, barCode,timestamp) {
//	var timestamp=new Date().getTime();
	cameraPhotoJs(dateValue + "/" + ouName + "/" + pNo + "/" + staNo, barCode + "_" + userName+"_"+timestamp);
	fileName += barCode + "_" + userName+"_"+timestamp+".jpg"+"/";
//	setTimeout(cameraPaintJs(dateValue + "/" + pNo + "/" + staNo+"/"+barCode + "_" + userName+"_"+timestamp),1000);
}
//后置打印面单
function postprint(){
	var datax = $j("#tbl-billView").jqGrid('getRowData');
	var flag = true;
	$j.each(datax,function(i, item) {
		var n1= parseInt(item.quantity);
		var cq = item.completeQuantity;
		if(cq==''||cq==null){
			 cq='0';
		}
		var n2= parseInt(cq);
		if(n2<n1){
			flag = false
		}
	});
	if(flag){
		$j("#barCode1").focus();
		var staId = $j("#showDetail").attr("staId");
		if(null == staId || "" == staId){
			playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
			jumbo.showMsg("参数异常");
			return false;
		}
		var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?sta.id=" + staId;
		printBZ(loxia.encodeUrl(url),false);
	}
}

function printTrank(staId ,packId){
	if((staId == null || staId == "") && (packId == null || packId == "")){
		playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
		jumbo.showMsg("打印失败：系统异常！");
	}else{
		loxia.lockPage();
		jumbo.showMsg("面单打印中，请等待...");
		var url = $j("body").attr("contextpath") + "/printSingleDeliveryMode2Out.json?sta.id="+staId+"&trankCode="+packId;
		printBZ(loxia.encodeUrl(url),true);				
		loxia.unlockPage();
	}
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
//    playMusic("../../wms/images/windows.wav");
 }

function beforePrintValidate(){
/*	var ids = $j("#tbl-orderDetail").jqGrid('getDataIDs');
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
	}*/
	return true;
}

function printPackaged(obj){
	var row = $j(obj).parent().parent().parent().attr("id");
	var packObj = $j("#tbl-showPackaged").jqGrid("getRowData",row);
	var plid = packObj["plId"];
	var ppid = packObj["id"];
	//alert(plId + " " + ppId);
	loxia.lockPage();
	if(beforePrintValidate()){
		var dt = new Date().getTime();
		var plcode = $j("#dphCode").html();
		//alert(plid+" "+plcode);
		//var url = $j("body").attr("contextpath") + "/printtrunkpackinglistmode1.json?plCmd.id=" + plid + "&plCmd.code=" + plcode;
		var url = $j("body").attr("contextpath") + "/printo2oqspackinglistmode1Out.json?rt="+ dt +"&plCmd.id=" + plid + "&plCmd.pickingListPackageId=" + ppid + "&plCmd.code=" + plcode;
		//var rs = loxia.syncXhrPost(url);
		var pl = loxia.syncXhrPost($j("body").attr("contextpath") + "/getPickingList.json?rt="+ dt,{"pickingListId":plid});
		var isPostpositionPackingPage = false;
		if(pl && pl["pickingList"]){
			isPostpositionPackingPage = pl["pickingList"]["isPostpositionPackingPage"];
		}
		if(true == isPostpositionPackingPage){
			if(confirm("该配货清单是后置打印装箱清单，确认打印？")){
				//jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
				printBZ(loxia.encodeUrl(url),true);
			}
		}else{
			//jumbo.showMsg(i18("TRUNKPACKINGLISTINFO"));
			printBZ(loxia.encodeUrl(url),true);
		}
		// add by liubin 将打印的清单设置背景色 
		//$j("#" + ppId).addClass("printRow");
	}
	loxia.unlockPage();
	
}