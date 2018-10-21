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
	OPERATE_FAILED : "执行核对失败！",
	EXISTS_QUANTITY_GQ_EXECUTEQTY : "请检查核对数量,存在商品未核对",
	
	SN_CODE : "SN序列号",
	VIEW_SN : "查看"
});
var startTime = 1;
var ecode = false;
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function getFmtValue(tableId,rowId,column){
	return $j("#"+tableId+" tr[id="+rowId+"] td[aria-describedby='"+tableId+"_"+column+"']").attr("title")
}
function reloadSta(plId){
	var 	status = "2";
	$j("#tbl-showDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/getPickCheckList.json"),
			postData:{"cmd.id":plId,"status":status}}).trigger("reloadGrid",[{page:1}]);
	status = "3,4,10";
	$j("#tbl-showReady").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/getPickCheckList.json"),
			postData:{"cmd.id":plId,"status":status}}).trigger("reloadGrid",[{page:1}]);
	//取消列表
	status = "15,17";
	$j("#tbl-showCancel").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/getPickCheckList.json"),
		postData:{"cmd.id":plId,"status":status}}).trigger("reloadGrid",[{page:1}]);
}

function showDetail(obj){
	var row=$j(obj).parent().parent().attr("id"),pl=$j("#tbl-dispatch-list").jqGrid("getRowData",row);
	var data ={};
	data["plCmd.code"]=pl['code'];
	if(data["plCmd.code"] != null){
		var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/checkPickingisOver.json",data);
		if(rs && rs.result=='success'){
			// do nothing
		}else{
			jumbo.showMsg("批次号"+pl['code']+"未拣货完成，不能直接操作！ 请至[拣货批次开始]页面操作");
			return;
		}
	}
	$j("#showList").addClass("hidden");
	$j("#showDetail").removeClass("hidden");
	//$j("#billsId-2").focus();
	$j("#refSlipCode2").focus();
	$j("#verifyPlId").val(row);
	$j("#showDetail").attr("plId",row);
	$j("#backTarget").val("refSlipCode");
	$j("#verifyCode").html(pl['code']);
	$j("#verifyStatus").html(getFmtValue("tbl-dispatch-list",row,"intStatus"));
	$j("#verifyPlanBillCount").html(pl['planBillCount']);
	$j("#verifyCheckedBillCount").html(pl['checkedBillCount']);
	$j("#verifyShipStaCount").html(pl['shipStaCount']);
	$j("#verifyPlanSkuQty").html(pl['planSkuQty']);
	$j("#verifyCheckedSkuQty").html(pl['checkedSkuQty']);
	$j("#verifyShipSkuQty").html(pl['shipSkuQty']);
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
var isBarCode = false;
var isTrankList = false;//判断是否能拆包裹
var laCount = 1;
var isPostpositionPackingPage = '-1'
var isPostpositionExpressBill = '-1';
var isCheckOver = false;
$j(document).ready(function (){
	initAesKey();
	userName = $j("#userinfo", window.parent.document).attr("lgname");
	dateValue = $j("#userinfo", window.parent.document).attr("sysdate");
	ouName = $j("#userinfo", window.parent.document).attr("ouName");
	rs0 = loxia.syncXhr($j("body").attr("contextpath") + "/json/checkIsNeedBarcode.json",null);
	var starbucksIcePackage=loxia.syncXhr($j("body").attr("contextpath") + "/findStarbucksIcePackage.json");
	//alert(starbucksIcePackage.sList[0].region);
	$j("#dialog_error").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog_error_ok").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
//	$j("#dialog_error_ok1").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog_error_recheck").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog-sns").dialog({title: "SN序列号", modal:true, autoOpen: false, width: 600});
	
	$j("#dialog_la_check").dialog({
		title : "提示信息",
		modal : true,
		autoOpen : false,
		width : 400,
		closeOnEscape : false,
		open : function(event, ui) {	$j(".ui-dialog-titlebar-close").hide();	}
	});
	
	$j("#close").click(function(){
		$j("#dialog-sns").dialog("close");
	});
	
	$j("#dialog_error_close_ok").click(function(){
		$j("#dialog_error_ok").dialog("close");
	});
	
	$j("#dialog_la_check_ok").click(function(){
		laCount = laCount + 1;
		$j("#dialog_la_check").dialog("close");
		$j("#barCode").focus();
	});
	
		
	$j("#whName").html(window.parent.$j("#orgname").html());
	var plstatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"pickingListStatus"});
	if(!plstatus.exception){
		$j("#tbl-dispatch-list").jqGrid({
			url:$j("body").attr("contextpath") + "/searchPickingList2.json",
			postData:loxia._ajaxFormToObj("plForm"),
			datatype: "json",//"已发货单据数","已发货商品件数",
			//colNames: ["ID","配货批次号","状态","计划配货单据数","已核对单据数","已发货单据数","计划配货商品件数","已核对商品件数","已发货商品件数","最后核对时间","最后发货时间","开始配货时间"],
			colNames: ["ID",i18("CODE"),i18("INTSTATUS"),i18("PLANBILLCOUNT"),i18("CHECKEDBILLCOUNT"),i18("SHIPSTACOUNT"),i18("PLANSKUQTY"),i18("CHECKEDSKUQTY"),i18("SHIPSKUQTY"),
				i18("CREATE_TIME"),i18("CHECKEDTIME"),i18("EXECUTEDTIME"),i18("PICKINGTIME")],
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
			height:jumbo.getHeight(),
			viewrecords: true,
		   	rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
		});
	}else{
		jumbo.showMsg(i18("INIT_SYSTEM_DATA_EXCEPTION"));//初始化系统参数异常
	}
	$j("#search").click(function(){
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/searchPickingList2.json"),
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
			//colNames: ["ID","备注","作业单号","状态","相关单据号","作业类型名称","相关店铺","物流服务商简称","创建时间","计划执行总件数"],
			colNames: ["ID",i18("MEMO"),i18("STA_CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("OWNER"),i18("LPCODE"),i18("CREATETIME"),i18("STVTOTAL"),"快递单号"],
			colModel: [{name: "id", index: "id", hidden: true},
						{name: "memo", index: "memo", hidden: true},
						{name :"code",index:"code",formatter:"linkFmatter",formatoptions:{},width:100,resizable:true},
						{name:"intStatus", index:"intStatus" ,width:80,resizable:true,
							formatter:'select',editoptions:stastatus},
						{name: "refSlipCode", index:"refSlipCode",width:120,resizable:true},
						{name:"intType", index:"intType", width:90, resizable:true,
							formatter:'select',editoptions:statype},
						{name:"owner",index:"owner",width:120,resizable:true},
						{name:"lpcode",index: "lpcode",width:100,resizable:true,formatter:'select',editoptions:trasportName},
						{name:"createTime",index:"sta.CREATE_TIME",width:120,resizable:true},
		                {name:"stvTotal",index:"stvTotal",width:70,resizable:true},
		                {name:"transNo",index:"delinfo.TRACKING_NO",width:90,resizable:true}
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
			jsonReader: { repeatitems : false, id: "0" }
		});
		
		$j("#tbl-showReady").jqGrid({
			datatype: "json",
			//colNames: ["ID","作业单号","状态","相关单据号","作业类型名称","相关店铺","物流服务商简称","创建时间","计划执行总件数"],
			colNames: ["ID",i18("STA_CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("OWNER"),i18("LPCODE"),i18("CREATETIME"),i18("STVTOTAL"),"核对时间"],
			colModel: [{name: "id", index: "id", hidden: true},
						{name :"code",index:"code",width:120,resizable:true},
						{name:"intStatus", index:"intStatus" ,width:80,resizable:true,
							formatter:'select',editoptions:stastatus},
						{name: "refSlipCode", index:"planBillCount",width:150,resizable:true},
						{name:"intType", index:"intType", width:90, resizable:true,
							formatter:'select',editoptions:statype},
						{name:"owner",index:"owner",width:120,resizable:true},
						{name:"lpcode",index: "lpcode",width:120,resizable:true,formatter:'select',editoptions:trasportName},
						{name:"createTime",index:"sta.CREATE_TIME",width:150,resizable:true},
		               {name:"stvTotal",index:"stvTotal",width:100,resizable:true},
		               {name:"checkTime",index:"checkTime",width:100,resizable:true}],
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
		});
		//取消列表
		$j("#tbl-showCancel").jqGrid({
			datatype: "json",
			//colNames: ["ID","作业单号","状态","相关单据号","作业类型名称","相关店铺","物流服务商简称","创建时间","计划执行总件数"],
			colNames: ["ID",i18("STA_CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("OWNER"),i18("LPCODE"),i18("CREATETIME"),i18("STVTOTAL"),"核对时间"],
			colModel: [{name: "id", index: "id", hidden: true},
						{name :"code",index:"code",width:120,resizable:true},
						{name:"intStatus", index:"intStatus" ,width:80,resizable:true,
							formatter:'select',editoptions:stastatus},
						{name: "refSlipCode", index:"planBillCount",width:150,resizable:true},
						{name:"intType", index:"intType", width:90, resizable:true,
							formatter:'select',editoptions:statype},
						{name:"owner",index:"owner",width:120,resizable:true},
						{name:"lpcode",index: "lpcode",width:120,resizable:true,formatter:'select',editoptions:trasportName},
						{name:"createTime",index:"sta.CREATE_TIME",width:150,resizable:true},
		               {name:"stvTotal",index:"stvTotal",width:100,resizable:true},
		               {name:"checkTime",index:"checkTime",width:100,resizable:true}],
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
			if('1' == isPostpositionExpressBill && '0' == isPostpositionPackingPage) return;
		    loxia.lockPage();
			var staId = $j("#showDetail").attr("staId");
			var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?sta.id=" + staId;
			printBZ(loxia.encodeUrl(url),true);					
		    loxia.unlockPage();
    });
	var barCodesList  = new Array();//run
	$j("#tbl-billView").jqGrid({
		//url:"dispatch-list-view.json",
		datatype: "json",
		//colNames: ["ID","库位编码预览","商品名称","商品编码","条形码","计划执行数量","已执行数量"],
		colNames: ["ID","是否SN商品",i18("SKUCODE"),i18("LOCATIONCODE"),i18("SKUNAME"),i18("JMSKUCODE"),"货号",i18("KEY_PROPS"),i18("BARCODE"),i18("QUANTITY"),i18("COMPLETEQUANTITY"),"","","","","",i18("SN_CODE")],
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
	                {name:"snType",index:"snType",hidden: true},      
	                {name:"spType",index:"spType",hidden: true},      
	                {name:"errorQuantity",index:"errorQuantity",hidden: true},      
	                {name:"customerId",index:"customerId",hidden: true},      
	                {name:"unitPrice",index:"unitPrice",hidden: true},      
					{name: "idForBtn", width: 80, formatter:"buttonFmatter",sortable:false, formatoptions:{"buttons":{label:i18("VIEW_SN"), onclick:"viewSn(this)"}}}
	                ],
		caption: i18("CHECKED_LIST"),//已核对列表
	   	sortname: 'sku.code',
	    multiselect: false,
		sortorder: "desc",
		rownumbers:true,
		rowNum: -1,
		height:"auto",
		gridComplete: function() {
			isCheckOver = false;
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
			// 通过barcode staid获取被绑定的卡号数量
			var data = $j("#tbl-billView").jqGrid('getDataIDs');
			for(var i = 0;i<data.length;i++){
				var staid = $j("#staId").val();
				var datas = $j("#tbl-billView").jqGrid('getRowData',data[i]);
				var barcode = $j("#tbl-billView tr[id="+datas["id"]+"] td[aria-describedby$='barCode']").html();
				var spType = $j("#tbl-billView tr[id="+datas["id"]+"] td[aria-describedby$='spType']").html();
				//alert(spType);
				if(spType == '10'){
					barCodesList.push(barcode);//run
				}
				var snType = $j("#tbl-billView tr[id="+datas["id"]+"] td[aria-describedby$='snType']").html();
				if(snType == '3'){
					postData['barCode'] = barcode;
					postData['sta.id'] = staid;
					postData['cardStatus'] = 1;//激活成功
					var rs = loxia.syncXhr($j("body").attr("contextpath") + "/getSnCountForStvSku.json",postData);
					if (rs && rs.result == "success") {
						$j("#tbl-billView tr[id="+datas["id"]+"] td[aria-describedby$='completeQuantity']").html(rs.count);
					}else{
						jumbo.showMsg("系统异常请刷新页面");
						return;
					}
					postData['cardStatus'] = 3;//激活失败
					var rserror = loxia.syncXhr($j("body").attr("contextpath") + "/getSnCountForStvSku.json",postData);
					if (rserror && rserror.result == "success") {
						$j("#tbl-billView tr[id="+datas["id"]+"] td[aria-describedby$='errorQuantity']").html(rserror.count);
					}else{
						jumbo.showMsg("系统异常请刷新页面");
						return;
					}
				}else{
					$j("#tbl-billView tr[id="+datas["id"]+"] td[aria-describedby$='completeQuantity']").html(0);
					$j("#tbl-billView tr[id="+datas["id"]+"] td[aria-describedby$='errorQuantity']").html(0);
				}
			}
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#refSlipCode").focus();
	$j("#snDiv").hide();
	
	$j("#btnTryError").click(function(){
		printSnCardErrorList();
	});
	
	$j("#iptPlCode").keypress(function(evt){
		if(evt.keyCode === 13){
			$j(".refSlipCode").trigger("keypress");
		}
	});
	
	$j(".refSlipCode").keypress(function(evt){
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
			
			var data ={};
			data["plCmd.code"]=$j("#verifyCode").html();
			data["plCmd.slipCode"]=refSlipCode;
			var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/checkPickingisOver.json",data);
			if(rs && rs.result=='success'){
				// do nothing
			}else{
				jumbo.showMsg("批次号"+data["plCmd.code"]+"未拣货完成，不能直接操作！ 请至[拣货批次开始]页面操作");
				return;
			}
			
			loxia.lockPage();
			loxia.asyncXhrPost($j("body").attr("contextpath") + "/json/staSlipCodeCheck.json",
				{"sta.refSlipCode":refSlipCode,
				 "sta.pickingCode":$j("#iptPlCode").val()},
				{success:function (data) {
					loxia.unlockPage();
					if(data&&data.id && data.status == "CANCEL_UNDO"){
						loxia.tip(_this,"单据号"+refSlipCode+"状态是：取消未处理，不允许核对!");//取消未处理，不允许核对!
						initInput();
						return;
					}
					if(data&&data.id && (data.intCheckMode == 1 ||  data.intCheckMode == 10 ||  data.intCheckMode == 2)){
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
							isCheckOver = false;
							var ext = data['extTransOrderId'];
							var ali = data['aliPackageNo'];
							var isCod = data['isCod'];
							var lpCode = data['lpcode'];
							$j("#plfh").addClass("hidden");
							if(((ext != "" && ext != null)||(ali != null && ali != "")) && lpCode != "JD" && isCod != 1){
								$j("#tbl-trank-list").removeClass("hidden");
								$j("#tbl-trank-button").removeClass("hidden");
								isTrankList = true;
							}else{
								$j("#tbl-trank-list").addClass("hidden");
								$j("#tbl-trank-button").addClass("hidden");
								isTrankList = false;
							}
							
							$j("#tbl-billView").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/staLineInfoByStaId.json"),
								postData:{"sta.id":data.id}}).trigger("reloadGrid",[{page:1}]);
							$j("#staCode").html(data['code']);
							$j("#isBkCheck").val(data['isBkCheck']);
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
							var starbucksDetail=loxia.syncXhr($j("body").attr("contextpath") + "/findStarbucksDetail.json",{"starbuckCode":data['code']});
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
							$j("#barCode").focus();
							isPostpositionPackingPage = data['isPostpositionPackingPage'];
							isPostpositionExpressBill = data['isPostpositionExpressBill'];
							if(1 == isPostpositionExpressBill && 0 == isPostpositionPackingPage){
								$j("#isPostpositionExpressBill").val(1);
								$j("#isPostpositionPackingPage").val(0);
								isPostpositionExpressBill = 1;
								isPostpositionPackingPage = 0;
								$j("#express_order").val(data['transNo']);
								var e = $j.Event("keypress");
								e.keyCode =13;
								isBarCode = true;
								$j("#express_order").trigger(e);
								//$j("#express_order").attr("disabled", "disabled");
								//$j("#tbl-express-order").hide();
								//$j("#express_order").val(data['transNo']);
							}
							else{
								$j("#isPostpositionExpressBill").val(0);
								$j("#isPostpositionPackingPage").val(0);
								isPostpositionPackingPage = 0;
								isPostpositionExpressBill = 0;
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
	
	$j("#barCode_recheck").keypress(function(evt){
		var barCode1 = $j("#barCode_recheck").val();
		if(BARCODE_CONFIRM ==barCode1){
			$j("#doCheck_recheck").trigger("click");
		}
	});
	
	$j("#barCode").keypress(function(evt){
		if(evt.keyCode == 9){
			evt.preventDefault();
			$j("#express_order").select();
		}
		if(evt.keyCode === 13){
			isCheckOver = false;
			var barCode = $j("#barCode").val();
			 var isBarCodesList =false;
			//alert(barCodesList);//run
			$j.each(barCodesList, function(i){
				if(barCode ==barCodesList[i]){
					isBarCodesList=true;
					$j("#barCode").val("");
					jumbo.showMsg("请输入SN");
					return;
				}
				});
			if(isBarCodesList){
				return;
			}
			
			//星巴克星享卡解密
			if(barCode.substring(0,4)=="SQR|"){
				try{
					barCode="6"+Decrypt(barCode.substring(4));
				}catch (e) {
					jumbo.showMsg("解密失败！02|Invalid Key");//解密失败
					return;
				};
				if(barCode.length!=16){
					jumbo.showMsg("解密后的星享卡长度不为16！02|Invalid Key");//长度不为16
					return;
				}
				$j("#skuBarCode").val(barCode);
			}		
			if(barCode == "RPHOTO"){
				$j("#newCamera").click();
				$j("#barCode").val("");
				return ;
			}
			var bc = barCode.split('=');
			if(bc.length > 0){
				barCode = bc[0];
				$j("#skuBarCode").val(bc[0]);
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
			var data = $j("#tbl-billView").jqGrid('getRowData');
			var item;
			var temp = null;
			var bc1;
			$j.each(data,function(i, item1){
				item = item1;
				bc1=item1.barCode;
				var num = item.completeQuantity;
				if(num==null||num=='')num=0;
				//00 条形码
				if(item1.snType != "3"){
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
				}
			});
			if(!rs && temp != null){
				initCheck();
				removeCursor();
				var msg = temp.skuName+" 您要核对量大于计划执行量，请重新核对！";
				errorTipOK(msg);
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
						errorTipOK(msg);
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
						var d = $j("#tbl-billView").jqGrid('getRowData',item.id);
						var num =  d.completeQuantity;
						if(num==null||num=='')num=0;
						var inQty = parseInt($j("#inQty").val());
						d.completeQuantity = parseInt(num)+inQty;
						$j("#inQty").val(1);
						$j("#tbl-billView").jqGrid('setRowData',item.id,d);
						rs=true;
						$j("#barCode").val("");
						$j("#barCode").focus();
						loxia.tip(this);
						var dataError = $j("#tbl-billView").jqGrid('getRowData');
						var dataCheck = true;
						var errorCheck = false;
						$j.each(dataError,function(i, datae){
							var znum = datae.quantity;
							var oknum =  datae.completeQuantity;
							var errornum = datae.errorQuantity;
							if(datae.snType == "3"){
								//判断是否激活失败+激活成功=计划执行数量
								var number = parseInt(oknum)+ parseInt(errornum);
								if(parseInt(znum) != parseInt(number)){
									dataCheck = false;
								}
								if(errornum > 0){
									errorCheck = true;
								}																		
							}
						});
						if(dataCheck && errorCheck){
							$j("#skuBarCode").val("");
							printSnCardErrorList();
							errorTipOK("存在卡号激活失败，补拣货单已打印，请至激活失败卡券界面处理！");
							initCheck();
							return;
						}
						//如果计划量等于执行量则光标跳到快递单输入框
						removeCursor();
						isPostpositionPackingPage = $j("#isPostpositionPackingPage").val();
						isPostpositionExpressBill = $j("#isPostpositionExpressBill").val();
						if('1' == isPostpositionExpressBill && '0' == isPostpositionPackingPage){
							postprint();//后置打印面单
						}
						$j("#tbl-billView tr[id="+item.id+"]").children("td:last").html("");
						return;
					}
				}
			}else{
				var isbkcheck = $j("#isBkCheck").val();
				if(isbkcheck == "true"){
					//后端处理作业单var result = "";
					var result = "";
					result = loxia.syncXhr($j("body").attr("contextpath")+ "/findBarcodeBySn.json", {"sn" : barCode});
					if(result && result.result == "error") {
						//没有SN号 判断卡号是否正确
						var postData = {};
						$j.each(data,function(i, stal1) {
							stal = stal1;
								if(stal['snType'] == '3') {
									//获取所有是后端处理作业单的SKU_CODE
									postData["barCodeList[" + i + "]"] = stal1['barCode']+","+stal1['customerId'];
									isChecked = true;
								}
						});
						if(isChecked) {
							postData['sn'] = barCode;
							var bar = loxia.syncXhr($j("body").attr("contextpath")+ "/formatCardToSn.json", postData);
							if (bar && bar.result == "success") {
								if(bar.rs == "snok"){
									result = loxia.syncXhr($j("body").attr("contextpath")+ "/findBarcodeBySn.json", {"sn" : barCode});
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
						if(result.snType == 'ok'){//验证是不是无条码SKU商品
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
										var isError = 0;
										$j.each(data,function(i, item1){
											// 星巴克合并订单bug修复
										if(item1.quantity != item1.completeQuantity){
											if(result.barcode == item1.barCode){
												isError--;
											}
											item = item1;
											bc1=item1.barCode;
											var num = item.completeQuantity;
											var errorn = item.errorQuantity;
											if(num==null||num=='')num=0;
											//00 条形码
											if(bc1==result.barcode||bc1=="00"+result.barcode){
												temp = item;
												if(parseInt(num)+parseInt(errorn)<=item.quantity){
													rs=true;
													return false;
												}
											}else{
												for(var v in barcodeList[bc1]){
													if(barcodeList[bc1][v] == result.barcode || barcodeList[bc1][v] == ('00'+result.barcode) ){
														temp = item;
														if(parseInt(num)+parseInt(errorn)<=item.quantity){
															rs=true;
															return false;
														}
													}
												}
											}
										}else{
											// 星巴克bug修复
											if(result.barcode == item1.barCode){
												isError++;
											}
										}});
										// 去重判断报错
										if(isError>0){
											var msg = "【" + barCode + "】不在计划核对量中，请确认录入！";
											errorTipOK(msg);
											return;
										}
										if(rs){
											var num = item.completeQuantity;
											var errorn = item.errorQuantity;
											if(num==null||num=='')num=0;
											//数量判断
											if(parseInt(num)+parseInt(errorn)+1>item.quantity){
												initCheck();
												removeCursor();
												var msg = item.skuName+" 您要核对量大于计划执行量，请重新核对！";
												errorTipOK(msg);
												rs=true;
												return;
											}else{
													//卡片先绑定作业单
													postData['sta.id'] = $j("#staId").val();
													var bd = loxia.syncXhr($j("body").attr("contextpath")+ "/cardBindingStv.json",postData);
													if (bd && bd.result && bd.result == "success") {
														if(isTrankList) {
															//如果支持拆包裹订单判断现在卡的金额是否>拆包金额限制
															postData['staid'] = $j("#staId").val();
															var up = (parseInt(item['completeQuantity'])+parseInt(item['errorQuantity'])+1) * item['unitPrice'];
															var lasum = loxia.syncXhr($j("body").attr("contextpath")+ "/getBiChannelLimitAmount.json",postData);
															if (lasum && lasum.result && lasum.result == "success"){
																if(lasum.rs == 'notnull') {
																	if(up>=(lasum.la * laCount)) {
																		laCheckOK(item['skuName']+" 商品的合计价格已经超过拆包限制金额，请进行拆包运单号操作！");
																	}																												
																}
															}
														}
														//激活卡状态
														var kjh = loxia.syncXhr($j("body").attr("contextpath")+ "/activateCardStatus.json",postData);
														if (kjh && kjh.result && kjh.result == "success") {
															if(kjh.msg == 'ok'){
																var d = $j("#tbl-billView").jqGrid('getRowData',item.id);											
																var num =  d.completeQuantity;
																if(num==null||num=='')num=0;
//																var inQty = parseInt($j("#inQty").val());
																$j("#tbl-billView tr[id="+d["id"]+"]  td[aria-describedby$='completeQuantity']").html(parseInt(num)+1);
																$j("#inQty").val(1);
																var dataError = $j("#tbl-billView").jqGrid('getRowData');
																var dataCheck = true;
																var errorCheck = false;
																$j.each(dataError,function(i, datae){
																	var znum = datae.quantity;
																	var oknum =  datae.completeQuantity;
																	var errornum = datae.errorQuantity;
																	if(datae.snType == "3"){
																		//判断是否激活失败+激活成功=计划执行数量
																		var number = parseInt(oknum)+ parseInt(errornum);
																		if(parseInt(znum) != parseInt(number)){
																			dataCheck = false;
																		}
																		if(errornum > 0){
																			errorCheck = true;
																		}																		
																	}
																});
																if(dataCheck && errorCheck){
																	$j("#skuBarCode").val("");
																	printSnCardErrorList();
																	errorTipOK("存在卡号激活失败，补拣货单已打印，请至激活失败卡券界面处理！");
																	initCheck();
																	return;
																}
															}else if(kjh.msg == 'ERRORNOTSN'){
																jumbo.showMsg("SN号缺失绑定关系！");
																$j("#skuBarCode").val("");	
																initCheck();
																return;
															}else{
																jumbo.showMsg("卡激活失败！");
																var es = $j("#tbl-billView").jqGrid('getRowData',item.id);
																var ernum = parseInt(es.errorQuantity)+1;
																$j("#tbl-billView tr[id="+es["id"]+"]  td[aria-describedby$='errorQuantity']").html(ernum);
																$j("#skuBarCode").val("");	
																initCheck();
																var dataError = $j("#tbl-billView").jqGrid('getRowData');
																var dataCheck = true;
																var errorCheck = false;
																$j.each(dataError,function(i, datae){
																	var znum = datae.quantity;
																	var oknum =  datae.completeQuantity;
																	var errornum = datae.errorQuantity;
																	//判断是否激活失败+激活成功=计划执行数量
																	var number = parseInt(oknum)+ parseInt(errornum);
																	if(parseInt(znum) != parseInt(number)){
																		dataCheck = false;
																	}
																	if(errornum > 0){
																		errorCheck = true;
																	}
																});
																if(dataCheck && errorCheck){
																	$j("#skuBarCode").val("");
																	printSnCardErrorList();
																	errorTipOK("存在卡号激活失败，补拣货单已打印，请至激活失败卡券界面处理！");
																	initCheck();
																	return;
																}
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
													var pNo = $j.trim($j("#verifyCode").html());
													var barcode = $j.trim($j("#barCode").val());
													var staNo = $j.trim($j("#staCode").html());
													var timestamp=new Date().getTime();
													try{
														cameraPhoto(pNo, staNo, barcode,timestamp);//拍照
														fileUrl = dateValue + "/" + ouName + "/" + pNo + "/" + staNo+"/"+barcode + "_" + userName+"_"+timestamp;
														cameraPaintMultiJs(fileUrl);
													} catch (e) {
//														jumbo.showMsg(i18("拍照失败！"));//初始化系统参数异常
													};
													$j(document).scrollTop($j(document).scrollTop() - 1); 
													var d = $j("#tbl-billView").jqGrid('getRowData',item.id);			
													$j("#tbl-billView").jqGrid('setRowData',item.id,d);
													rs=true;
													$j("#barCode").val("");
													$j("#barCode").focus();
													loxia.tip(this);
													//如果计划量等于执行量则光标跳到快递单输入框
													removeCursor();
													isPostpositionPackingPage = $j("#isPostpositionPackingPage").val();
													isPostpositionExpressBill = $j("#isPostpositionExpressBill").val();
													if('1' == isPostpositionExpressBill && '0' == isPostpositionPackingPage){
														postprint();//后置打印面单
													}
													$j("#tbl-billView tr[id="+item.id+"]").children("td:last").html("");
//													$j("#tbl-billView").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/staLineInfoByStaId.json"),
//														postData:{"sta.id":$j("#staId").val()}}).trigger("reloadGrid",[{page:1}]);
													return;
												}
										}else{
											playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
											errorTipOK(i18("BARCODE_NOT_EXISTS"));
											initCheck();
											return;
										}
									}else{
										if($j("#staId").val() == staid){
											postData['sta.id'] = staid;
											var kjh = loxia.syncXhr($j("body").attr("contextpath")+ "/activateCardStatus.json",postData);
											if (kjh && kjh.result && kjh.result == "success") {
												jumbo.showMsg("激活重复操作");
												$j("#skuBarCode").val("");	
												initCheck();
												return;	
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
											errorTipOK("【"+barCode+"】" + " 卡片已被作业单：【"+rs1.stacode+"】 绑定使用！");
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
								initCheck();
								return;
							}
						}else{
							playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
							errorTipOK(i18("BARCODE_NOT_EXISTS"));
							//initCheck();
							return;
						}
					}else{
						playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
						errorTipOK(i18("BARCODE_NOT_EXISTS"));
						//initCheck();
						return;
					}
				}else{
					playMusic($j("body").attr("contextpath") +"/recording/barCodeError.wav");
					errorTipOK(i18("BARCODE_NOT_EXISTS"));
					return;				
				}
			}
		}
	});
	
	$j("#express_order").keypress(function(evt){
		var express_order = $j("#express_order").val();
		if(express_order == "RPHOTO"){
			$j("#newCamera").click();
			$j("#express_order").val("");
			
			return ;
		}
		if(evt.keyCode === 13){
			evt.preventDefault();
			var transNo = $j("#staTransNo").html();
			if(!ecode){
				if(transNo != "" && transNo != $j.trim($j(this).val())){
					//errorTip("快递单号不匹配！");
					errorTipOK("快递单号不匹配！");
					return;
				}							
			}
			
			//获取配货清单编码 || 运单号，不能输入OK条码、配货清单编码以及订单相关单据号。
		    var staCode=$j("#staCode").html(); 
		    var tkNo = $j("#express_order").val().trim();  //获取输入的快递单号
		    var pl = loxia.syncXhrPost($j("body").attr("contextpath")+ "/findSlipCodeAndPickingListCodeByStaCode.json", {"code" : staCode});
		    if(tkNo!="" && tkNo==pl["pls"]["supplierCode"] || tkNo==pl["pls"]["slipCode"] || tkNo.toUpperCase()=="OK".toUpperCase()){
		       jumbo.showMsg("非正确的快递单号!");
//		    	alert("快递单号好正确")
//		    	errorTipOK("非正确的快递单号！");
		    	return;
		    }
		    var billId = $j.trim($j(this).val());
		    var ch = $j("#"+billId);
			if(!ecode){
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
			}else{
				loxia.tip(this);
			}
			//校验快递单号的合法性
			var staId = $j.trim($j("#staId").val());
			var data ={trackingNo:billId};
			data["sta.id"]=staId;
			var ids = $j("#tbl-express-order tr:gt(0)");
			var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/checktrackingno.json",data);
			if(rs && rs.result=='success'){
				if(billId != ""&&billId != null){
					if(ids.length==0){
						if(billId != ids){
							var str = "<tr id=\""+billId+"\"><td class='label' height='36px' style='text-align: center;font-size: 36px' >"+billId+"</td><td>" +
							"<button style='width: 200px;height: 45px;font-size=36px' onclick=\"doDelete('"+billId+"')\">"+i18("DELETE")+"</button></td></tr>";//删除	
							$j("#tbl-express-order").append(str);					
						}else{
							$j("#express_order").val("");
						}			
					}
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
			
			var datax = $j("#tbl-billView").jqGrid('getRowData');
			
			var isOver = true;
			$j.each(datax,function(i, item) {
				var n1= parseInt(item.quantity);
				var cq = item.completeQuantity;
				if(cq==''||cq==null){
					 cq='0';
				}
				var n2= parseInt(cq);
				if(n2<n1){
					isOver = false;
				}
			});
			if(isOver && isCheckOver){
				$j('#doCheck').trigger('click');
			}else{
				$j("#barCode").focus();
			}
			
		}});

	
	$j("#backToPlList").click(function(){
		//window.location=loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/plverifyentry.do");
		$j("#showList").removeClass("hidden");
		$j("#showDetail").addClass("hidden");
		initInput();
		$j("#refSlipCode").focus();
	});
	
	$j("#Back").click(function(){
		try{
			closeGrabberJs();				
		}catch (e) {
//						jumbo.showMsg(i18("关闭摄像头失败！"));//初始化系统参数异常
		}
		//$j("#tbl-dispatch-list").jqGrid().trigger("reloadGrid",[{page:1}]);
		//清空输入框
		$j("#express_order").val("");
		$j("#barCode0").val("");
		$j("#barCode1").val("");
		ecode = false;
		initInput();
		loxia.tip($j("#barCode"));
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
			$j("#refSlipCode2").focus();
		}
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
			laCount = laCount + 1;
		}else{
			if(rs.errMsg != null){
				jumbo.showMsg(rs.errMsg);
			}
		}
	});
	
	//执行核对
	$j("#doCheck").click(function(){
		$j("#backTarget").val("refSlipCode");
		var startTime1 = new Date().getTime();
		if(startTime1 - startTime < 500) return;
		startTime = startTime1;
		//验证计划量和执行量
		var flag = true;
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
				//errorTip(i18(msg));
				errorTipOK(i18(msg));
				flag = false;
				return;
			}
			if(n2>n1){
				initCheck();
				var msg = item.skuName+" 已执行量大于计划执行量，请重新核对！";
				//errorTip(i18(msg));
				errorTipOK(i18(msg));
				//核对数量归零
				item["completeQuantity"] = 0;
				$j("#tbl-billView").jqGrid('setRowData',item.id,item);
				loxia.initContext($j("#tbl-billView"));
				flag = false;
				return;
			}
		});	
		if(!flag){
			$j("#flag0").val("barCode");
			return ;
		}
		
			//检查快递单号
			var ids = $j("#tbl-express-order tr:gt(0)");
			if(ids.length==0){
				$j("#flag0").val("express_order");
				//errorTip(i18("INPUT_TRACKINGNO"));
				errorTipOK(i18("INPUT_TRACKINGNO"));
				return false;
			}
			
			var data={};
			$j.each(ids,function(i, item) {
				 var id =  item.id;
				  data["packageInfos[" + i + "].trackingNo"] = id;
			});
		    var staId = $j("#staId").val();
		    data["sta.id"]=staId;
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
//		    try{
//				var pNo = $j.trim($j("#verifyCode").html());
//				var staCode = $j.trim($j("#staCode").html());
//				var timestamp=new Date().getTime();
//				creatZipJs(dateValue + "/" + pNo + "/" + staCode,timestamp);//打包图片
//				updateHttpJs(dateValue + "/" + pNo + "/" + staCode,timestamp);//发送图片zip http服务器
//				loxia.syncXhr($j("body").attr("contextpath") + "/creatStvCheckImg.json",{"staCode":staCode,"fileUrl":"/"+dateValue + "/" + pNo + "/" + staCode,"fileName":fileName});//保存STV图片路径
//			} catch (e) {
//			};
		    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/staSortingCheck.json",data);
			if(rs.result=='success'){
				$j("#printDeliveryInfo").trigger("click");
				$j("#backTarget").val("refSlipCode");
				$j("#Back").trigger("click");
			}else {
				$j("#flag0").val("barCode1");
				var msg = rs.msg;
				if('' != msg && msg.indexOf("无法流水开票") > 0){
					isNotLineNo='true';
				}
				//errorTip(i18("OPERATE_FAILED")+"<br />"+rs.msg);
				errorTipOK(rs.msg);
			}
	});
	
	loxia.byId("inQty").setEnable(false);
	
	$j("#btnInQty").click(function(){
		loxia.byId("inQty").setEnable(true);
		$j("#inQty").select(); 
	});
	
	$j("#inQty").keypress(function(evt){
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
	
	$j("#barCode0").keypress(function(evt){
		expressCommon();
	});
	
	//确认按钮
	$j("#doCheck0").click(function(){
		expressCommon0();
	});
	
	
	$j("#barCode1").keypress(function(evt){
			var barCode1 = $j("#barCode1").val();
				if(barCode1 == "RPHOTO"){
					$j("#newCamera").click();
					$j("#barCode1").val("");
					return ;
				}
			
			if(BARCODE_CONFIRM ==barCode1){
			//执行核对
			$j('#doCheck').trigger('click');
			}
			
	});
	
	//扫描SN号后
	$j("#snCode").keypress(function(evt){
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
	
	//星巴克刷SN卡片
	$j("#getstarbucksSncode").click(function() {
		var snCode  = window.top.document.getElementById("starbucksSnCode").value;
		if(snCode!=null&&snCode!=''){
			$j("#barCode").val(snCode.split("=")[0]);
		}
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
	isPostpositionPackingPage = $j("#isPostpositionPackingPage").val();
	isPostpositionExpressBill = $j("#isPostpositionExpressBill").val();
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
	var datax = $j("#tbl-billView").jqGrid('getRowData');
	var flag = true;
	var transNo = $j("#staTransNo").html().trim();
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
		isCheckOver = true;
		//如果有快递号直接执行核对
		if(transNo != ""){
			ecode = true;
			$j("#express_order").val(transNo);
			var e = $j.Event("keypress");
            e.keyCode =13;
            $j("#express_order").trigger(e);
		}else{
			$j("#express_order").focus();			
		}
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

function laCheckOK(text) {
	$j("#la_check").html("<font style='text-align:center;font-size:28px' color='red'>"+text+"</font>");
	$j("#dialog_la_check").dialog("open");
}

//function errorClose(){
//	$j("#dialog_error_ok").dialog("close");
//}

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
			flag = false;
		}
	});
	if(flag){
//		$j("#barCode1").focus();
		var staId = $j("#showDetail").attr("staId");
		if(null == staId || "" == staId){
			playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
			jumbo.showMsg("参数异常");
			return false;
		}
		var url = $j("body").attr("contextpath") + "/printsingledeliverymode1.json?sta.id=" + staId+"&isOnlyParent=true";
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

//激活失败拣货单补打
function printSnCardErrorList(){
	var staCode = $j("#staCode").html().trim();
	var plCode = "";
	if(null == staCode || "" == staCode){
		playMusic($j("body").attr("contextpath") +"/recording/staError.wav");
		jumbo.showMsg("参数异常");
		return false;
	}
	loxia.lockPage();
	var url = $j("body").attr("contextpath") + "/printSnCardErrorList.json?staCode="+staCode+"&plCode="+plCode;
	printBZ(loxia.encodeUrl(url),false);
	loxia.unlockPage();
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
