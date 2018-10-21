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
	VIEW_SN : "查看",
	
	GIFT_PRINT : "特殊打印中，请等待...",
	
	GIFT_TYPE_NIKE_GIFT : "10", // NIKE赠送礼品卡(需打印)
	GIFT_TYPE_COACH_CARD : "20", // Coach保修卡(需打印)
	GIFT_TYPE_GIFT_PACKAGE : "30",// 商品特殊包装
	GIFT_TYPE_GIFT_PRINT : "50", // 商品特殊印制
	HAAGENDAZS_TICKET:"60",// 哈根达斯券处理
		
	STA_SPECIAL_EXECUTE_TYPE_COACH:1,
	STA_SPECIAL_EXECUTE_TYPE_BB_OUT:3,
	STA_SPECIAL_EXECUTE_TYPE_BB_RETURN:5,
	STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT:8,
	STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT_B:9,
	STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT_C:10
});

var originMap=null;
var isSingleOrigin=false;
var lastSkuCode="";
var skuOrigin="";

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
	originMap=loxia.syncXhr($j("body").attr("contextpath") + "/json/findSkuOriginByPlId.json",{"plId":row});
	if(originMap!=null){
		originMap=$j.parseJSON(originMap.originMap);
	}
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
//须要特殊处理的数据
var giftList;
var isOK;
// STA 特殊处理类型
var staSpecialExecute;
var barcodeList;
var rs0;
var lineNo;
var isNotLineNo;
//记录SN号
var snArray = new Array();
//当前选中行
var lineData;
// 核对去除重复
//var keyShow = "";
var isNotError = false;
var userName = "";
var dateValue = "";
var fileUrl = "";
var fileName = "";
var ouName = "";
var isJump8Type="";
var startTime = 1;
$j(document).ready(function (){
	userName = $j("#userinfo", window.parent.document).attr("lgname");
	dateValue = $j("#userinfo", window.parent.document).attr("sysdate");
	ouName = $j("#userinfo", window.parent.document).attr("ouName");
	rs0 = loxia.syncXhr($j("body").attr("contextpath") + "/json/checkIsNeedBarcode.json",null);
	
	$j("#dialog_error").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog_error_ok").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog_error_recheck").dialog({title: "错误信息", modal:true, autoOpen: false, width: 400});
	$j("#dialog-sns").dialog({title: "SN序列号", modal:true, autoOpen: false, width: 600});
	$j("#dialog_gift").dialog({title: "特殊处理信息", modal:true,closeOnEscape:false, autoOpen: false, width: 320});
	$j("#dialog_staSpecialExecute").dialog({title: "特殊处理", modal:true,closeOnEscape:false, autoOpen: false, width: 520});
	
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
			url:$j("body").attr("contextpath") + "/searchCheckList.json",
			postData:loxia._ajaxFormToObj("plForm"),
			datatype: "json",//"已发货单据数","已发货商品件数",今天是1月24日 
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
		$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/searchCheckList.json"),
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
	//		url:$j("body").attr("contextpath") + "/staoccupiedlist.json",
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
		colNames: ["ID","SKU_ID","是否SN商品",i18("SKUCODE"),i18("LOCATIONCODE"),i18("SKUNAME"),i18("JMSKUCODE"),"货号",i18("KEY_PROPS"),"特殊处理",i18("BARCODE"),i18("QUANTITY"),i18("COMPLETEQUANTITY"),"重置特殊行",i18("SN_CODE")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "skuId", index: "skuId", hidden: true},//是否SN商品
		            {name: "isSnSku", index: "isSnSku", hidden: true},//是否SN商品
					{name:"skuCode", index:"skuCode" ,width:150,resizable:true,sortable:false},
					{name :"locationCode",index:"locationCode",width:120,resizable:true,hidden: true},
					{name:"skuName", index:"skuName" ,width:150,resizable:true,sortable:false},
					{name: "jmcode", index:"jmcode",width:150,resizable:true,sortable:false},
					{name: "supplierCode", index:"sku.supplier_code",width:150,resizable:true,sortable:false},
					{name: "keyProperties", index:"keyProperties",width:60,resizable:true,sortable:false},
					{name: "isGift", index:"isGift",width:60,resizable:true,sortable:false},
					{name:"barCode", index:"barCode", width:90,hidden: true, resizable:true},
					{name:"quantity",index:"quantity",width:60,resizable:true,sortable:false},
	                {name:"completeQuantity",index:"completeQuantity",width:60,resizable:true,sortable:false},
	                {name: "gift", width: 80, formatter:"buttonFmatter",sortable:false, formatoptions:{"buttons":{label:"重新操作",onclick:"resetGift(this)"}}},
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
				//$j(tag).children("td[aria-describedby$='completeQuantity']").html($j(tag).children("td[aria-describedby$='quantity']").html());
				var isGift= $j(tag).children("td[aria-describedby$='tbl-billView_isGift']").attr("title");
				if(isGift == 'false'){
					$j(tag).children("td[aria-describedby$='tbl-billView_isGift']").html("否");
					$j(tag).children("td[aria-describedby$='tbl-billView_gift']").html("");
				} else {
					$j(tag).children("td[aria-describedby$='tbl-billView_isGift']").html("是");
				}
			});
			var postData = {};
			$j("#tbl-billView tr:gt(0)>td[aria-describedby$='barCode']").each(function (i,tag){
				var sku = $j(tag).html();
				postData["skuBarcodes[" + i + "]"] = sku;
			});
			barcodeList = loxia.syncXhr($j("body").attr("contextpath") + "/findskuBarcodeList.json",postData);
			loxia.initContext($j(this));
		},
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#refSlipCode").focus();
	$j("#snDiv").hide();
	
	$j("#iptPlCode").keydown(function(evt){
		if(evt.keyCode === 13){
			$j(".refSlipCode").trigger("keydown");
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
					if(data&&data.id && data.intCheckMode == 3){
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
							$j("#tbl-billView").jqGrid('setGridParam',{url:loxia.getTimeUrl($j("body").attr("contextpath") + "/json/staLineInfoByStaId.json"),
								postData:{"sta.id":data.id}}).trigger("reloadGrid",[{page:1}]);
							var postData = {};
							postData["sta.id"]=data.id;
							giftList = loxia.syncXhr($j("body").attr("contextpath") + "/findstagiftlist.json",postData);
							$j("#staCode").html(data['code']);
							$j("#staId").val(data['id']);
							$j("#staCreateTime").html(data['createTime']);
							$j("#verifyPlId").val(data['pickingList.id']);
							$j("#tbl-express-order tr:gt(0)").remove();
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
							//当明细界面打开后判断作业单特殊处理列表中包含装“1”时，弹出对话框COACH小票打印，打印数据通过OMS获取，考虑扩展，可支持多种类型流程处理。
							var sse = loxia.syncXhr($j("body").attr("contextpath") + "/querystaspecialexecute.json",postData);
							staSpecialExecute = sse.data;
							
							$j("#dialog_coach_print").addClass("hidden");
							$j("#dialog_burberry_out_print").addClass("hidden");
							$j("#dialog_burberry_return_print").addClass("hidden");
							$j("#dialog_nike_gift_card_print").addClass("hidden");
							$j("#show_dialog_nike_gift_card_print_ABC").addClass("hidden");
							$j("#dialog_gucci_gift_card_print").addClass("hidden");
							$j("#show_dialog_gucci_gift_card_print").addClass("hidden");
							$j("#dialog_mk_packaging_common").addClass("hidden");
							$j("#dialog_mk_packaging_gift").addClass("hidden");
							$j("#dialog_mk_packaging_staff").addClass("hidden");
							$j("#dialog_mk_gift_manual").addClass("hidden");
							var isShow = false;
							for(var index in staSpecialExecute){
								if(index != 'contains'){
									if(staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_COACH")){
										$j("#dialog_coach_print").removeClass("hidden");
										isShow = true;
									}else if(staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_BB_OUT")){
										$j("#dialog_burberry_out_print").removeClass("hidden");
										isShow = true;
									}else if(staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_BB_RETURN")){
										$j("#dialog_burberry_return_print").removeClass("hidden");
										isShow = true;
									}else if(staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT") ||
											staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT_B") ||
											staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT_C")){
										$j("#dialog_nike_gift_card_print").removeClass("hidden");
										$j("#show_dialog_nike_gift_card_print_ABC").removeClass("hidden");
										$j("#ABC_TYPE").html(staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT") ? "[A] " :(staSpecialExecute[index].intType == i18("STA_SPECIAL_EXECUTE_TYPE_NIKE_GIFT_B") ? "[B] ":"[C]"));
										$j("#ABC_VALUE").html(staSpecialExecute[index].memo);
										isShow = true;
										isJump8Type=staSpecialExecute[index].intType;
									}else if(staSpecialExecute[index].intType ==45 ){
										$j("#dialog_mk_packaging_common").removeClass("hidden");
										isShow = true;
									}else if(staSpecialExecute[index].intType ==46 ){
										$j("#dialog_mk_packaging_gift").removeClass("hidden");
										isShow = true;
									}else if(staSpecialExecute[index].intType == 47){
										$j("#dialog_mk_packaging_staff").removeClass("hidden");
										isShow = true;
									}else if(staSpecialExecute[index].intType == 50){
										$j("#show_dialog_gucci_gift_card_print").removeClass("hidden");
										$j("#dialog_gucci_gift_card_print").removeClass("hidden");
										$j("#GUCCI_GIFT_CARD_VALUE").html(staSpecialExecute[index].memo);
										isShow = true;
									}  
								}
							}
							for(glID in giftList){
						    	for(i in giftList[glID]){
						    		if(giftList[glID][i].intType==80){
						    			$j("#dialog_mk_gift_manual").removeClass("hidden");
						    			isShow = true;
						    			break;
						    		}
						    	}
						    }
							
							if(isShow){
								$j("#dialog_staSpecialExecute").prev().find("a").addClass("hidden");
								$j("#dialog_staSpecialExecute").dialog("open");
							}
							$j("#barCode").focus();
							var isPostpositionPackingPage = data['isPostpositionPackingPage'];
							var isPostpositionExpressBill = data['isPostpositionExpressBill'];
							if(1 == isPostpositionExpressBill && 0 == isPostpositionPackingPage){
								$j("#printDeliveryInfo").removeClass("hidden");
								$j("#isPostpositionExpressBill").val(1);
								$j("#isPostpositionPackingPage").val(0);
								$j("#express_order").val(data['transNo']);
								var e = $j.Event("keydown");
					            e.keyCode =13;
					            isBarCode = true;
					            $j("#express_order").trigger(e);
							}
							else{
								$j("#printDeliveryInfo").addClass("hidden");
								$j("#isPostpositionExpressBill").val(0);
								$j("#isPostpositionPackingPage").val(0);
								$j("#express_order").val("").attr("disabled", "");
								$j("#tbl-express-order").show();
							}
							initInput();
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
	
	$j("#barcodeCheck").click(function(){
		if($j("#barCode").val().trim() == ""){
			showErrorMsg("请扫描商品条码");
			return false;
		}
	    var bc = $j("#barCode").val().trim();
	    
	    $j(document).scrollTop(0);
		$j(document).scrollTop($j(document).scrollTop() + 1);
		$j("#flag0").val("barCode");
		evt.preventDefault();
		var rowid = 0;
		//bc=$j.trim($j(this).val());
		rs=null;
		if(bc.length==0)
			return false;
		var data = $j("#tbl-billView").jqGrid('getRowData');
		var msg = "";
		var isSnSku = false;
		$j.each(data,function(i, item){
			var flag = false;
			var bc1=item.barCode;
			if(bc1==bc||bc1=="00"+bc){
				flag=true;
			}else{
				for(var v in barcodeList[bc1]){
					if(barcodeList[bc1][v] == bc || barcodeList[bc1][v] == ('00'+bc) ){
						flag=true;
					}
				}
			}
			//00 条形码
			if(rs != true && flag){
				var num =  item.completeQuantity;
				if(num==null||num=='')num=0;
				//数量判断
				if(parseInt(num)+1>item.quantity){
					initCheck();
					removeCursor();
					msg = item.skuName+" 您要核对量大于计划执行量，请重新核对！";
					rs=false;
				} else{
					var pNo = $j.trim($j("#verifyCode").html());
					var barcode = $j.trim($j("#barCode").val());
					var staNo = $j.trim($j("#staCode").html());
					var timestamp=new Date().getTime();
					try{
						cameraPhoto(pNo, staNo, barcode,timestamp);//拍照
						fileUrl = dateValue + "/" + ouName + "/" + pNo + "/" + staNo+"/"+barcode + "_" + userName+"_"+timestamp;
						cameraPaintMultiJs(fileUrl);
					} catch (e) {
//						jumbo.showMsg(i18("拍照失败！"));//初始化系统参数异常
					};
					$j(document).scrollTop($j(document).scrollTop() - 1); 
					//保税仓
					if(originMap!=null && originMap[item.skuCode].length>0){
						if(lastSkuCode!=item.skuCode){
							//加载产地
							loadOriginMap(item);
							return false;
						}
						var origin=$j("#origin").val();
						if(origin==null||origin==''){
							
							return false;
						}
					}
					if(item.isSnSku==1){
						$j("#snDiv").show();
						$j("#snCode").focus();
						lineData = item;
						rs=true;
						return;
					} else {
						
						setCheck(item.id);
						if(item.isGift=="是"){
							if(parseInt(num)+parseInt($j("#inQty").val()) == item.quantity){
								executeGift(item.id,false);
							}
						}
						rs=true;
						var isPostpositionPackingPage = $j("#isPostpositionPackingPage").val();
						var isPostpositionExpressBill = $j("#isPostpositionExpressBill").val();
						if('1' == isPostpositionExpressBill && '0' == isPostpositionPackingPage){
							postprint();//后置打印面单
						}
						return;
					}
				}
			}
		});
		if(rs == null){
			errorTipOK(i18("BARCODE_NOT_EXISTS"));
			return;
		}else if(!rs){
			errorTipOK(msg);
		}
	});
	
	$j("#barCode").keydown(function(evt){
		if(evt.keyCode == 9){
			evt.preventDefault();
			$j("#express_order").select();
		}
		
		if(evt.keyCode === 13){
			$j("#barcodeCheck").trigger("click");
		}
	});
	
	//加载产地
	function loadOriginMap(l){
		$("#origin").empty();
		var originS=originMap[l.skuCode];
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
		lastSkuCode=l.skuCode;
	}
	
	$j("#express_order").keydown(function(evt){
		if(evt.keyCode === 13){
			evt.preventDefault();
			var transNo = $j("#staTransNo").html();
			if(transNo != "" && transNo != $j.trim($j(this).val())){
				errorTipOK("快递单号不匹配！");
				return;
			}
			
			//获取配货清单编码 || 运单号，不能输入OK条码、配货清单编码以及订单相关单据号。
		    var staCode=$j("#staCode").html(); 
		    var tkNo = $j("#express_order").val().trim();  //获取输入的快递单号
		    var pl = loxia.syncXhrPost($j("body").attr("contextpath")+ "/findSlipCodeAndPickingListCodeByStaCode.json", {"code" : staCode});
		    if(tkNo!="" && tkNo==pl["pls"]["supplierCode"] || tkNo==pl["pls"]["slipCode"] || tkNo.toUpperCase()=="OK".toUpperCase()){
//		       errorTipOK("非正确的快递单号!");
		    	 jumbo.showMsg("非正确的快递单号!");
		    	return;
		    }
			
			var billId = $j.trim($j(this).val());
			if(billId == ""){
				 $j("#flag0").val("express_order");
				 errorTipOK("快递单号不能为空！");
				 return;
			}
			var ch = $j("#"+billId);
			if(ch.length!=0){
				 $j("#flag0").val("express_order");
				 errorTipOK(i18("TRACKINGNO_EXISTS"));
				 return;
			}else{
				loxia.tip(this);
			}
			//校验快递单号的合法性
			var staId = $j.trim($j("#staId").val());
			var data ={trackingNo:billId};
			data["sta.id"]=staId;
			var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/checktrackingno.json",data);
			if(rs && rs.result=='success'){
				if(billId != ""&&billId != null){
					var str = "<tr id=\""+billId+"\"><td class='label' height='36px' style='text-align: center;font-size: 36px' >"+billId+"</td><td>" +
					"<button style='width: 200px;height: 45px;font-size=36px' onclick=\"doDelete('"+billId+"')\">"+i18("DELETE")+"</button></td></tr>";//删除	
					$j("#tbl-express-order").append(str);			
				}
				loxia.tip(this);
				$j(this).val("");
				$j(this).focus();
				$j("#barCode1").focus();
			}else {
				$j("#flag0").val("express_order");
				errorTipOK(i18("TRACKINGNO_RULE_ERROR"));
			}
			
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
			if(isOver){
				$j("#barCode1").focus();
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
		hashMap.removeAll();
		loxia.tip($j("#barCode"));
		loxia.tip($j("#express_order"));
		var trslength= $j("#tbl-trank-list").find("tr").length;
		for(var i=trslength;i>=1;i--) //保留最前面两行！
		{
		$j("#tbl-trank-list").find("tr").eq(i).remove();
		}
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
		//清空输入框
		$j("#express_order").val("");
		$j("#barCode0").val("");
		$j("#barCode1").val("");
		initInput();
		
	});
	
	//执行核对
	$j("#doCheck").click(function(){
		
		var startTime1 = new Date().getTime();
		if(startTime1 - startTime < 500) return;
		startTime = startTime1;
		
		hashMap.removeAll();
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
				errorTipOK(i18(msg));
				flag = false;
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
	    data["skuOrigin"] = skuOrigin;
	    //SN号数据提交
	    for(var i = 0 ; i < snArray.length;i++){
	    	var sn = snArray[i].split(",");
			data["snlist["+ i +"]"] = sn[1];
	    }
	    var j = 0;
	    for(glID in giftList){
	    	for(i in giftList[glID]){
	    		if(giftList[glID][i].intType==i18("GIFT_TYPE_COACH_CARD")){
	    			data["glList["+j+"].id"]=giftList[glID][i].id;
	    			data["glList["+j+"].sanCardCode"]=giftList[glID][i].sanCardCode;
	    			j++;
	    			break;
	    		}
	    	}
	    }
//	    try{
//			var pNo = $j.trim($j("#verifyCode").html());
//			var staCode = $j.trim($j("#staCode").html());
//			var timestamp=new Date().getTime();
//			creatZipJs(dateValue + "/" + pNo + "/" + staCode,timestamp);//打包图片
//			updateHttpJs(dateValue + "/" + pNo + "/" + staCode,timestamp);//发送图片zip http服务器
//			loxia.syncXhr($j("body").attr("contextpath") + "/creatStvCheckImg.json",{"staCode":staCode,"fileUrl":"/"+dateValue + "/" + pNo + "/" + staCode,"fileName":fileName});//保存STV图片路径
//		} catch (e) {
//		};
	    var rs=loxia.syncXhrPost($j("body").attr("contextpath") + "/json/staSortingCheck.json",data);
		if(rs.result=='success'){
			skuOrigin="";//重置产地
			$j("#Back").trigger("click");
		}else {
			$j("#flag0").val("barCode1");
			var msg = rs.msg;
			if('' != msg && msg.indexOf("无法流水开票") > 0){
				isNotLineNo='true';
			}
			//errorTip(i18("OPERATE_FAILED")+"<br />"+rs.msg);
			errorTipOK(i18("OPERATE_FAILED")+"<br />"+rs.msg);
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
			if(BARCODE_CONFIRM ==barCode1){
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
	
	//特殊处理打印
	$j("#gift_print").click(function(){
		loxia.lockPage();
		jumbo.showMsg(i18("GIFT_PRINT"));
		var url = $j("body").attr("contextpath") + "/warehouse/printgift.json?wid=" + $j("#giftDivLineId_lineId").val();
		printBZ(loxia.encodeUrl(url),true);
		loxia.unlockPage();
		
	});
	//特殊处理确认
	$j(".gift_confirm").click(function(){
		giftConfirm($j(this).attr("id"));
	});
	
	
	$j("#dialog_gift").keydown(function(evt){
		if(evt.keyCode === 13){
			if(isOK){
				giftConfirm("gift_confirm1");
			}
		}
	});
	
	$j("#barCode_gift_confirm").keydown(function(evt){
		if(evt.keyCode === 13){
			if(BARCODE_CONFIRM == $j("#barCode_gift_confirm").val()){
				giftConfirm("gift_confirm1");
				$j("#barCode_gift_confirm").val("");
			}
		}
	});
	
	//特殊处理跳过
	$j("#gift_skip").click(function(){
		giftSkip();
	});
	
	//跳过条码
	$j("#barCode_gift_skip").keydown(function(evt){
		var barCode1 = $j("#barCode_gift_skip").val();
		if(BARCODE_CONFIRM ==barCode1){
			$j("#barCode_gift_skip").val("");
			giftSkip();
		}
	});
	
	$j("#dialog_coach_print").click(function(){
		specialPackagingprint(1,$j("#showDetail").attr("staId"));
	});
	
	$j("#dialog_burberry_out_print").click(function(){
		specialPackagingprint(3,$j("#showDetail").attr("staId"));
	});
	
	$j("#dialog_burberry_return_print").click(function(){
		specialPackagingprint(5,$j("#showDetail").attr("staId"));
	});
	
	$j("#dialog_nike_gift_card_print").click(function(){
		specialPackagingprint(isJump8Type,$j("#showDetail").attr("staId"));
	});
	
	$j("#dialog_gucci_gift_card_print").click(function(){
		specialPackagingprint(50,$j("#showDetail").attr("staId"));
	});
	
	$j("#dialog_printSuccess").click(function(){
		$j("#dialog_staSpecialExecute").dialog("close");
		$j("#barCode").focus();
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
	
	//新增运单号
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
	
});

function giftConfirm(id){
	coaCode = $j("#caochWarrantyCard").val();
	if(id == "gift_confirm2"){
		if(id == "gift_confirm2" && coaCode == ""){
			jumbo.showMsg("请填写保修卡条码");
			return;
		}
		if(hashMap.get(coaCode) == null){
			hashMap.put(coaCode, coaCode);
		}else{
			jumbo.showMsg("不允许一个单据里面录入相同的保修卡号码");
			return;
		}
	}
	if(!showGiftDate(false,true)){
		if(!showGiftDate(true,false)) {
			giftCheck();
			$j("#dialog_gift").dialog("close");
			$j("#barCode").val("");
			$j("#barCode").focus();
		}else{
			$j("#barCode_gift_skip").focus();
		}
	}else{
		$j("#barCode_gift_skip").focus();
	}
}

function specialPackagingprint(seType,staId){
	loxia.lockPage();
	jumbo.showMsg(i18("GIFT_PRINT"));
	var url = $j("body").attr("contextpath") + "/bysptypeprint.json?seType="+seType+"&staId=" + staId;
	printBZ(loxia.encodeUrl(url),true);
	loxia.unlockPage();
}

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
/******************商品SN导入 end**********************************************************************************************/
//SN判断
function checkSn(){
	var bc = $j("#snCode").val().trim();
	if(bc==""){
		return;
	}
	//SN号校验
	var result = loxia.syncXhr($j("body").attr("contextpath") + "/findsnbyskuid.json",{"sn":bc,"skuId":lineData.skuId});
	var isG = false;
	if(result && result.result == "success"){
		$j(document).scrollTop(0);
		$j(document).scrollTop($j(document).scrollTop() + 1);
		if(result.skuId==lineData.skuId){
			if(snArray.contains(lineData.barCode+","+bc)){
				snCheck();
				errorTipOK(i18("该SN已经扫描过:"+bc));
				removeCursor();
				return;
			}
			var data = $j("#tbl-billView").jqGrid('getRowData',lineData.id);
			var num = data.completeQuantity;
			if(num==null||num=='')num=0;
			var inQty = parseInt($j("#inQty").val());
			data.completeQuantity = parseInt(num)+inQty;
			
			//保税仓
			if(originMap!=null && originMap[data.skuCode].length>0){
				//记录作业单+商品+产地
				skuOrigin=skuOrigin+data.skuCode+"."+origin+";";
				
				if(!isSingleOrigin){
					$("#origin").find("option[text='请选择']").attr("selected",true);
				}
			}else{
				jumbo.showMsg("商品产地未维护，请先维护："+data.skuCode);
				return;
			}
			
			$j("#tbl-billView tr[id="+data.id+"]").children("td[aria-describedby$='tbl-billView_completeQuantity']").text(data.completeQuantity);
			$j("#tbl-billView tr[id="+data.id+"]").children("td[aria-describedby$='tbl-billView_completeQuantity']").attr("title",data.completeQuantity);
			loxia.initContext($j("#tbl-billView tr[id="+data.id+"]"));
			$j("#inQty").val(1);
			loxia.tip(this);
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
			//如果计划量等于执行量则光标跳到快递单输入框
			snArray.push(data.barCode+","+bc);
			if(data.completeQuantity == data.quantity){
				initCheck();
				if(data.isGift == "是"){
					isG = true;
					executeGift(data.id,false);
				}
			} else {
				snCheck();
			}
		}else{
			errorTipOK(i18("SN对应的条形码和之前扫描的不匹配"));
			snCheck();
		}
	}else{
		errorTipOK(i18(lineData.barCode + "对应SN:" +bc+" 不存在"));
		snCheck();
	}
	//如果计划量等于执行量则光标跳到快递单输入框
	if(isG == false){
		removeCursor();
	}
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
	window.setTimeout(function(){$j("#dialog_error_ok").dialog("open");},100);
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

//处理须要特殊处理的商品
function executeGift(id,isShowSkip){
	$j("#giftDivLineId").val(id);
	if(isShowSkip){
		$j("#gift_skip").removeClass("hidden");
		$j("#td_barCode_gift_skip").removeClass("hidden");
	} else {
		$j("#gift_skip").addClass("hidden");
		$j("#td_barCode_gift_skip").addClass("hidden");
	}
	if(showGiftDate(true,false)){
		console.log($j("#"+id).find("td[aria-describedby$='tbl-billView_supplierCode']").text());
		$j("#dialog_sku").text($j("#"+id).find("td[aria-describedby$='tbl-billView_supplierCode']").text());
		$j("#dialog_gift").prev().find("a").addClass("hidden");
		$j("#dialog_gift").dialog("open");
		if(isShowSkip){
			$j("#barCode_gift_skip").focus();
		} else {
			$j("#barCode_gift_confirm").focus();
		}
	} else {
		if(!isNotError){
			errorTip(i18("未找到特殊处理的商品信息！"));
		}else {
			$j("#barCode").val("");
			$j("#barCode").focus();
		}
	}
}

/**
 * 显示特殊处理数据
 * @param isOneShow 从第一个开始判断 只要未处理项的就显示
 * @param isSetHandles 是否设置当前项为完成
 * @return
 */
function showGiftDate(isOneShow,isSetHandles){
	var keyShow = "";
	var lineId = $j("#giftDivLineId").val();
	var shopType = $j("#giftDivLineId_type").val();
	var shopLineId = $j("#giftDivLineId_lineId").val();
	var isShow = false;
	isNotError = false;
	if(isOneShow){
		isShow = true;
	}
	for(i in giftList[lineId]){
		if(i != 'contains'){
			var gift = giftList[lineId][i];
			if(isSetHandles && (shopLineId == gift.id)){
				gift.isHandle = true;
				if(gift.intType == i18("GIFT_TYPE_COACH_CARD")){
					gift.sanCardCode = $j("#caochWarrantyCard").val();
				}
			}
			if(isShow){
				//表示已经处理过的特殊数据
				if(gift.isHandle == true){
					continue;
				}
				$j("#giftDivLineId_type").val(gift.intType);
				$j("#giftDivLineId_lineId").val(gift.id);
				var type = "";
				if(gift.intType == i18("GIFT_TYPE_COACH_CARD")){
					type = "Coach 保修卡";
					$j("#tr_memo").addClass("hidden");
					$j("#gift_print").addClass("hidden");
					$j("#gift_confirm1").addClass("hidden");
					$j("#barCode_gift_confirm").addClass("hidden");
					$j("#tr_sku").removeClass("hidden");
					$j("#tr_expirationTime").removeClass("hidden");
					$j("#tr_caochWarrantyCard").removeClass("hidden");
					$j("#gift_confirm2").removeClass("hidden");
					//保修卡过期时间
					$j("#expirationTime").text(gift.memo);
					//保修卡
					$j("#caochWarrantyCard").val("");
					isOK = false;
				} else {
					$j("#tr_sku").addClass("hidden");
					$j("#tr_expirationTime").addClass("hidden");
					$j("#tr_caochWarrantyCard").addClass("hidden");
					$j("#gift_confirm2").addClass("hidden");
					$j("#tr_memo").removeClass("hidden");
					$j("#gift_confirm1").removeClass("hidden");
					$j("#barCode_gift_confirm").removeClass("hidden");
					$j("#barCode_gift_confirm").focus();
					isOK = true;
					if(gift.intType == i18("GIFT_TYPE_NIKE_GIFT")){
						$j("#gift_print").removeClass("hidden");
						type = "礼品卡打印";
					} else if(gift.intType == i18("GIFT_TYPE_GIFT_PACKAGE")){
						$j("#gift_print").addClass("hidden");
						type = "商品特殊包装";
					} else if(gift.intType == i18("GIFT_TYPE_GIFT_PRINT")){
						$j("#gift_print").addClass("hidden");
						type = "商品特殊印制";
					} else if(gift.intType == i18("HAAGENDAZS_TICKET")){
						$j("#gift_print").addClass("hidden");
						type = "哈根达斯券处理";
						var tempKey = gift.memo;
						if(keyShow == tempKey){
							gift.isHandle = true;
							isNotError = true;
							continue;
						} else {
							keyShow = tempKey;
						}
					} 
					if(gift.memo == null){
						$j("#gift_memo").html("　　"+type);
					} else {
						$j("#gift_memo").html("　　"+gift.memo);
					}
				}
				$j("#gift_type").html(type);
				return true;
			} else {
				if(gift.intType == shopType){
					isShow = true;
				}
			}
		}
	}
    return false;
}
//特殊核对处理完成判断
function giftCheck(){
	var lineId = $j("#giftDivLineId").val();
	if(lineId == '') return; 
	var isCheck = true;
	//判断当前行的特殊处理信息都已经处理过的
	for(i in giftList[lineId]){
		if(i != 'contains'){
			if(!(giftList[lineId][i].isHandle == true)){
				isCheck = false;
				break;
			}
		}
	}
	if(isCheck){
		//setCheck(lineId);
	}
}

function giftSkip(){
	if(!showGiftDate(false,false)){
		showGiftDate(true,false);
	}
	$j("#barCode_gift_skip").focus();
}

//设置核对数量
function setCheck(id){
	var d = $j("#tbl-billView").jqGrid('getRowData',id);
	var num =  d.completeQuantity;
	if(num==null||num=='')num=0;
	var inQty = parseInt($j("#inQty").val());
	d.completeQuantity = parseInt(num)+inQty;
	//保税仓
	if(originMap!=null && originMap[d.skuCode].length>0){
		//记录作业单+商品+产地
		skuOrigin=skuOrigin+d.skuCode+"."+origin+";";
		
		if(!isSingleOrigin){
			$("#origin").find("option[text='请选择']").attr("selected",true);
		}
	}
	
	$j("#inQty").val(1);
	$j("#tbl-billView").jqGrid('setRowData',id,d);
	$j("#barCode").val("");
	$j("#barCode").focus();
	loxia.tip(this);
	//如果计划量等于执行量则光标跳到快递单输入框
	removeCursor();
	initCoutextTB(id);
}
//重置特殊处理数据
function resetGift(tag){
	var row=$j(tag).parent().parent().attr("id");
	var line=$j("#tbl-billView").jqGrid("getRowData",row);
	if(line.completeQuantity != line.quantity){
		errorTip("未做过特殊处理！无需重置！");
		return ;
	}
	$j("#barCode_gift_skip").focus();
	for(i in giftList[line.id]){
		if(i != 'contains'){
			giftList[line.id][i].isHandle = false;
		}
	}
	executeGift(line.id,true);
	//line.completeQuantity = 0;
	//$j("#tbl-billView").jqGrid('setRowData',row,line);
	//initCoutextTB(row);
}

//更改后做修改
function initCoutextTB(id){
	$j("#tbl-billView tr[id="+id+"]").children("td:last").html("");
	var isGift= $j("#tbl-billView tr[id="+id+"]").children("td[aria-describedby$='tbl-billView_isGift']").attr("title");
	if(isGift == '否'){
		$j("#tbl-billView tr[id="+id+"]").children("td[aria-describedby$='tbl-billView_gift']").html("");
	}
	loxia.initContext($j("#tbl-billView tr[id="+id+"]"));
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
//打印面单
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
//自定义HashMap
function HashMap(){ 
	this.map = {};
} 
HashMap.prototype = {
		put : function(key , value){
			this.map[key] = value;
		},
		get : function(key){
			if(this.map.hasOwnProperty(key)){
				return this.map[key];
			}
			return null;
		}, 
		remove : function(key){
			if(this.map.hasOwnProperty(key)){
				return delete this.map[key];
			}
			return false;
		}, 
		removeAll : function(){
			this.map = {};
		},
		keySet : function(){
			var _keys = [];
			for(var i in this.map){
				_keys.push(i);
			} 
			return _keys;
		}
	};
HashMap.prototype.constructor = HashMap;  

var hashMap = new HashMap();