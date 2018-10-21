$j.extend(loxia.regional['zh-CN'],{
	GET_DATA_ERROR		:"获取数据异常",

	STA_CODE : "作业单号",
	STA_SLIP_CODE : "相关单据号",
	STA_STATUS : "作业单状态",
	OWNER : "平台店铺名称",
	LPCODE: "物流服务商",
	TRACKING_NO : "快递单号",
	CRAETE_TIME : "创建时间",
	OPERATOR_TIME : "操作时间",
	OPERATOR : "操作人",
	PLAN_COUNT : "计划执行总数量",
	
	STA_LIST_TITLE:"销售作业单查询列表",
	RA_STA_LIST_TITLE:"退换货作业单查询列表",
	
	
	IN_INV:"退换货入库",
	OUT_INV:"换货出库",
	
	
	SKUCODE : "SKU编码",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	SKU_NAME : "商品名称",
	KEY_PROPS : "扩展属性",
	PLAN_QTY : "计划量",
	COMFIRMED_QTY : "执行量",
	SKU_COST : "库存成本",
	
	DETAIL_LIST_TITLE:"销售作业单明细",
	RA_DETAIL_LIST_TITLE:"退换货作业单明细",
	
	STATUS_CREATE:"已创建",
	STATUS_OCCUPIED:"配货中",
	STATUS_CHECKED:"已核对",
	STATUS_INTRANSIT:"已转出",
	STATUS_FINISHED:"已完成",
	STA_TYPE:"作业单类型",
	//====================================== tab3: 
	
	
	CODE : "作业单号",
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
	INT_LIST : "作业单列表",
	CREATE_TIME_RULE : "创建时间:起始时间必须小于结束时间！",
	PICKING_TIME_RULE : "配货时间:起始时间必须小于结束时间！",
	OUTBOUND_TIME_RULE : "发货时间:起始时间必须小于结束时间！",
	CHECKED_TIME_RULE : "最后核对时间:起始时间必须小于结束时间！",
	SHOPID			:"店铺"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

var baseUrl = "";
function showDetail(obj){
	loxia.lockPage();

	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	$j("#sta_id").val(id);
	$j("#tbl-order-detail").jqGrid('setGridParam',{
		url:loxia.getTimeUrl(baseUrl + "/queryStaDetail.json"),postData:{"staCmd.id":id}
		}).trigger("reloadGrid");
	
	
	var data=loxia.syncXhr(baseUrl + "/querySyaIsExport.json",{"staCmd.id":id});
	if(data){
		if(data.result=="success"){
			if(data.isNotExport){
				$j("#export").removeClass("hidden");
			} else {
				$j("#export").addClass("hidden");
			}
		}else{
			$j("#export").addClass("hidden");
			jumbo.showMsg(i18("GET_DATA_ERROR"));
		}
	}else{
		$j("#export").addClass("hidden");
		jumbo.showMsg(i18("GET_DATA_ERROR"));
	}
	var row=$j(obj).parent().parent().attr("id"),pl=$j("#tbl-orderList").jqGrid("getRowData",row);
	$j("#createTime").text(pl["createTime"]);
	$j("#finishTime").text(pl["finishTime"]);
	$j("#code").text(pl["code"]);
	$j("#refSlipCode").text(pl["refSlipCode"]);
	$j("#status").text(pl["intStatus"]);
	$j("#owner").text(pl["owner"]);
	$j("#lpcode").text(pl["lpcode"]);
	$j("#trackingNo").text(pl["trackingNo"]);
	$j("#operator").text(pl["operator"]);
	
	$j("#query-order-list").addClass("hidden");
	$j("#order-detail").removeClass("hidden");
	loxia.unlockPage();
}

// ======================= tab-2
function showDetail2(obj){
	loxia.lockPage();

	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	$j("#sta_id2").val(id);
	$j("#tbl-order-detail2").jqGrid('setGridParam',{
		url:loxia.getTimeUrl(baseUrl + "/queryExcStaDetail.json"),postData:{"staCmd.id":id}
		}).trigger("reloadGrid");
	
	
	var data=loxia.syncXhr(baseUrl + "/queryExcSyaIsExport.json",{"staCmd.id":id});
	if(data){
		if(data.result=="success"){
			if(data.isNotExport){
				$j("#export2").removeClass("hidden");
			} else {
				$j("#export2").addClass("hidden");
			}
		}else{
			$j("#export2").addClass("hidden");
			jumbo.showMsg(i18("GET_DATA_ERROR"));
		}
	}else{
		$j("#export2").addClass("hidden");
		jumbo.showMsg(i18("GET_DATA_ERROR"));
	}
	var row=$j(obj).parent().parent().attr("id"),pl=$j("#tbl-orderList2").jqGrid("getRowData",row);
	$j("#createTime2").text(pl["createTime"]);
	$j("#finishTime2").text(pl["finishTime"]);
	$j("#code2").text(pl["code"]);
	$j("#refSlipCode2").text(pl["refSlipCode"]);
	$j("#status2").text(pl["intStatus"]);
	$j("#owner2").text(pl["owner"]);
	$j("#lpcode2").text(pl["lpcode"]);
	$j("#trackingNo2").text(pl["trackingNo"]);
	$j("#operator2").text(pl["operator"]);
	
	$j("#query-order-list2").addClass("hidden");
	$j("#order-detail2").removeClass("hidden");
	loxia.unlockPage();
}

//=================================  tab3:
function getDate(strDate){
	var s = new Date(strDate.substring(0,10));
	s.setHours(strDate.substring(11,13));
	s.setMinutes(strDate.substring(11,13));
	s.setSeconds(strDate.substring(11,13));
	return s;
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
		colNames: ["ID","status",i18("CODE"),i18("INTSTATUS"),i18("REFSLIPCODE"),i18("INTTYPE"),i18("SHOPID"),i18("LPCODE"),i18("CREATETIME"),i18("STVTOTAL")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name: "status", index: "status",hidden: true},
		           {name: "code", index: "code", width: 120, resizable: true},
		           {name: "intStatus", index: "status", width: 80, resizable: true,formatter:'select',editoptions:intStatus},
		           {name: "refSlipCode", index: "slipCode",width: 120, resizable: true},
		           {name: "intType", index: "type", width: 100, resizable: true,formatter:'select',editoptions:staType},
		           {name: "shopId", index: "shopId", width: 120, resizable: true},
		           {name: "lpcode", index: "lpcode", width: 100, resizable: true,formatter:'select',editoptions:trasportName},
	               {name:"createTime",index:"createTime",width:150,resizable:true},
	               {name: "stvTotal", index: "stvTotal", width: 100, resizable: true}],
		caption: i18("INT_LIST"),// 作业单列表
		rowNum:-1,
	sortname: 'createTime',
	height:"auto",
 multiselect: false,
	sortorder: "desc",
	gridComplete : function(){
		loxia.initContext($j(this));
	},
	jsonReader: { repeatitems : false, id: "0" }
	});
}

function reloadOrderDetail(url,postData){
	$j("#tbl-orderDetail").jqGrid('setGridParam',{url:loxia.getTimeUrl(url)}).trigger("reloadGrid");
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

function showDetail3(tag){
	$j("#divTbDetial").removeClass("hidden");
	var id = $j(tag).parents("tr").attr("id");
	$j("#pickinglistid").val(id);

	$j("#showList").addClass("hidden");
	$j("#div2").removeClass("hidden");

	var baseUrl = $j("body").attr("contextpath");
	var rs = loxia.syncXhrPost(baseUrl + "/getPickingList.json",{"pickingListId":id});
	if(rs && rs["pickingList"]){
		$j("#dphCode").html( rs["pickingList"]["code"]);
		$j("#planBillCount").html( rs["pickingList"]["planBillCount"]);
		$j("#planSkuQty").html( rs["pickingList"]["planSkuQty"]);
		$j("#pickingTime").html( rs["pickingList"]["pickingTime"]);
		
		$j("#checkedBillCount").html( rs["pickingList"]["checkedBillCount"]);
		$j("#checkedTime").html( rs["pickingList"]["checkedTime"]);
		$j("#checkedSkuQty").html( rs["pickingList"]["checkedSkuQty"]);
		
		$j("#sendSkuQty").html( rs["pickingList"]["sendSkuQty"]);
		$j("#sendStaQty").html( rs["pickingList"]["sendStaQty"]);
		$j("#lastSendTime").html( rs["pickingList"]["lastSendTime"]);
	}
	var rss = loxia.syncXhrPost(baseUrl + "/findPackingByBatchCode.json",{"allocateCargoCmd.id":id});
	if(rss && rss["pl"]){
		$j("#creator").html(rss["pl"]["crtUserName"]);
		$j("#operator3").html(rss["pl"]["operUserName"]);
	}
	var tr = $j(tag).parents("tr");
	$j("#dphStatus").html(tr.find("td[aria-describedby='tbl-dispatch-list_intStatus']").html());
	orderDetail($j("body").attr("contextpath") + "/detialList.json?pickingListId="+id);
	
	// show finish button
	var rs = loxia.syncXhrPost(baseUrl + "/findpackinglistbyid.json",{"pickingListId":id});
	if(rs && rs["pl"]){
		var status = rs["pl"]["intStatus"];
		if (status == 8 || status == 2){
			$j("#finish").removeClass("hidden");
		}else {
			$j("#finish").addClass("hidden");
		}
	}
}
$j(document).ready(function (){
	loxia.init({debug: true, region: 'zh-CN'});
	
	$j("#tabs").tabs();
	
	jumbo.loadShopList("companyshop");
	jumbo.loadShopList("companyshop2");
	
/* 这里编写必要的页面演示逻辑*/
	baseUrl = $j("body").attr("contextpath");
	var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
	$j("#tbl-orderList").jqGrid({
		//url:baseUrl + "/queryStaExport.json",
		datatype: "json",
		//colNames: ["ID","作业单号","相关单据号","作业单状态","平台店铺名称","物流服务商","快递单号","创建时间","操作时间","单据操作人员","计划执行总数量"],
		colNames: ["ID",i18("STA_CODE"),i18("STA_SLIP_CODE"),i18("STA_STATUS"),i18("OWNER"),i18("LPCODE"),i18("TRACKING_NO"),i18("CRAETE_TIME"),i18("OPERATOR_TIME"),i18("OPERATOR"),i18("PLAN_COUNT")],
		colModel: [
		        {name: "id", index: "id", hidden: true},
				{name:"code", index:"code", formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, width:100, resizable:true},
				{name:"refSlipCode",index:"refSlipCode",width:100,resizable:true},
				{name:"intStatus",index:"intStatus",width:80,resizable:true},
				{name:"owner", index:"owner",width:120,resizable:true},
				{name:"lpcode", index:"lpcode",width:80,resizable:true,formatter:'select',editoptions:trasportName},
				{name:"trackingNo", index:"trackingNo",width:120,resizable:true},
				{name:"createTime",index:"createTime",width:150,resizable:true},
				{name:"finishTime",index:"finishTime",width:150,resizable:true},
				{name:"operator", index:"operator",width:80,resizable:true},
				{name:"totalSkuQty", index:"totalSkuQty",width:80,resizable:true}],
		caption: i18("STA_LIST_TITLE"),
	   	sortname: 'ID',
	    multiselect: false,
	    rowNum:jumbo.getPageSize(),
	   	rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
		sortorder: "desc",
		viewrecords: true,
	   	rownumbers:true,
		height:jumbo.getHeight(),
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl-orderList").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-orderList").jqGrid('getRowData',ids[i]);
				var tra = "";
				if(datas["intStatus"]=="2"){
					tra= i18("STATUS_OCCUPIED");
				}
				else if(datas["intStatus"]=="3"){
					tra= i18("STATUS_CHECKED");
				}
				else if(datas["intStatus"]=="4"){
					tra= i18("STATUS_INTRANSIT");
				}
				else if(datas["intStatus"]=="10"){
					tra= i18("STATUS_FINISHED");
				}
				$j("#tbl-orderList").jqGrid('setRowData',ids[i],{"intStatus":tra});
			}
		}
	});
	
	$j("#tbl-order-detail").jqGrid({
		datatype: "json",
		//colNames: ["ID","SKU编码","条形码","商品编码","商品名称","扩展属性","计划量","执行量","库存成本"],
		colNames: ["ID",i18("SKUCODE"),i18("BARCODE"),i18("JMCODE"),i18("SKU_NAME"),i18("KEY_PROPS"),i18("PLAN_QTY"),i18("COMFIRMED_QTY"),i18("SKU_COST")],
		colModel: [{name: "id", index: "id", hidden: true},
		           {name:"skuCode",index:"skuCode",width:100,resizable:true},
					{name:"barCode",index:"barCode",width:100,resizable:true},
					{name:"jmCode", index:"type" ,width:100,resizable:true},
					{name:"skuName", index:"skuName",width:150,resizable:true},
					{name:"keyProperties",index:"keyProperties",width:80,resizable:true},
					{name:"quantity", index:"quantity",width:80,resizable:true},
					{name:"completeQuantity", index:"completeQuantity",width:120,resizable:true},
					{name:"skuCost",index:"skuCost",width:100,resizable:true}],
		caption: i18("DETAIL_LIST_TITLE"),
	   	sortname: 'ID',
	    multiselect: false,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#search").click(function (){
		$j("#tbl-orderList").jqGrid('setGridParam',{
			url:loxia.getTimeUrl(baseUrl + "/queryStaExport.json"),postData:loxia._ajaxFormToObj("queryForm")
			}).trigger("reloadGrid");
	});
	
	$j("#reset").click(function (){
		$j("#queryForm input,select").val("");
		$j("#staStatus").val("");
	});
	
	$j("#back").click(function(){
		$j("#query-order-list").removeClass("hidden");
		$j("#order-detail").addClass("hidden");
	});
	
	$j("#export").click(function(){
		var id = $j("#sta_id").val();
		$j("#frmSoInvoice").attr("src",baseUrl + "/warehouse/exportStaSoInvoice.do?staCmd.id=" + id);
		$j("#export").addClass("hidden");
	});
	
	$j("#resetSoZero").click(function(){
		var id = $j("#sta_id").val();
		if(id == '') {jumbo.showMsg("作业单id为空"); return;}
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/updatestainvoice.json",{"staid":id});
		if(rs && rs["result"] == 'success'){
			jumbo.showMsg("操作成功");
		}else if(rs && rs["result"] == 'error'){
			jumbo.showMsg("操作失败");
		}else{
			jumbo.showMsg("系统错误");
		}
	}); 
	
	//================================================================================
	/***
	 * tab-2
	 */

	/* 这里编写必要的页面演示逻辑*/
		baseUrl = $j("body").attr("contextpath");
		var trasportName =loxia.syncXhr($j("body").attr("contextpath")+"/json/getTrasportName.do");
		$j("#tbl-orderList2").jqGrid({
			//url:baseUrl + "/queryExcStaExport.json",
			datatype: "json",
			//colNames: ["ID","作业单号","相关单据号","作业单状态","作业单类型","平台店铺名称","物流服务商","快递单号","创建时间","操作时间","单据操作人员","计划执行总数量"],
			colNames: ["ID",i18("STA_CODE"),i18("STA_SLIP_CODE"),i18("STA_STATUS"),i18("STA_TYPE"),i18("OWNER"),i18("LPCODE"),
			           i18("TRACKING_NO"),i18("CRAETE_TIME"),i18("OPERATOR_TIME"),i18("OPERATOR"),i18("PLAN_COUNT")],
			colModel: [
			        {name: "id", index: "id", hidden: true},
					{name:"code", index:"code", formatter:"linkFmatter",formatoptions:{onclick:"showDetail2"}, width:100, resizable:true},
					{name:"refSlipCode",index:"refSlipCode",width:100,resizable:true},
					{name:"intStatus",index:"intStatus",width:80,resizable:true},
					{name:"intType",index:"intType",width:80,resizable:true},
					{name:"owner", index:"owner",width:120,resizable:true},
					{name:"lpcode", index:"lpcode",width:80,resizable:true,formatter:'select',editoptions:trasportName},
					{name:"trackingNo", index:"trackingNo",width:120,resizable:true},
					{name:"createTime",index:"createTime",width:150,resizable:true},
					{name:"finishTime",index:"finishTime",width:150,resizable:true},
					{name:"operator", index:"operator",width:80,resizable:true},
					{name:"totalSkuQty", index:"totalSkuQty",width:80,resizable:true}],
			caption: i18("RA_STA_LIST_TITLE"),
			
		   	sortname: 'ID',
		    multiselect: false,
		    rowNum:jumbo.getPageSize(),
		   	rowList:jumbo.getPageSizeList(),
		   	pager:"#pager2",
			sortorder: "desc",
			viewrecords: true,
		   	rownumbers:true,
			height:jumbo.getHeight(),
			jsonReader: { repeatitems : false, id: "0" },
			gridComplete: function(){
				var ids = $j("#tbl-orderList2").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					var datas = $j("#tbl-orderList2").jqGrid('getRowData',ids[i]);
					
					var tra = "";
					var type = i18("IN_INV");
					if(datas["intType"] == "42"){
					var type = i18("OUT_INV");
					}
					$j("#tbl-orderList2").jqGrid('setRowData',ids[i],{"intType":type});
					if(datas["intStatus"]=="1"){
						tra= i18("STATUS_CREATE");
					}
					else if(datas["intStatus"]=="2"){
						tra= i18("STATUS_OCCUPIED");
					}
					else if(datas["intStatus"]=="3"){
						tra= i18("STATUS_CHECKED");
					}
					else if(datas["intStatus"]=="4"){
						tra= i18("STATUS_INTRANSIT");
					}
					else if(datas["intStatus"]=="10"){
						tra= i18("STATUS_FINISHED");
					}
					$j("#tbl-orderList2").jqGrid('setRowData',ids[i],{"intStatus":tra});
				}
			}
		});
		
		$j("#tbl-order-detail2").jqGrid({
			datatype: "json",
			//colNames: ["ID","SKU编码","条形码","商品编码","商品名称","扩展属性","计划量","执行量","库存成本"],
			colNames: ["ID",i18("SKUCODE"),i18("BARCODE"),i18("JMCODE"),i18("SKU_NAME"),i18("KEY_PROPS"),i18("PLAN_QTY"),i18("COMFIRMED_QTY"),i18("SKU_COST")],
			colModel: [{name: "id", index: "id", hidden: true},
			           {name:"skuCode",index:"skuCode",width:100,resizable:true},
						{name:"barCode",index:"barCode",width:100,resizable:true},
						{name:"jmCode", index:"type" ,width:100,resizable:true},
						{name:"skuName", index:"skuName",width:150,resizable:true},
						{name:"keyProperties",index:"keyProperties",width:80,resizable:true},
						{name:"quantity", index:"quantity",width:80,resizable:true},
						{name:"completeQuantity", index:"completeQuantity",width:120,resizable:true},
						{name:"skuCost",index:"skuCost",width:100,resizable:true}],
			caption: i18("RA_DETAIL_LIST_TITLE"),
		   	sortname: 'ID',
		    multiselect: false,
			sortorder: "desc",
			jsonReader: { repeatitems : false, id: "0" }
		});
		
		$j("#search2").click(function (){
			$j("#tbl-orderList2").jqGrid('setGridParam',{
				url:loxia.getTimeUrl(baseUrl + "/queryExcStaExport.json"),postData:loxia._ajaxFormToObj("queryForm2")
				}).trigger("reloadGrid");
		});
		
		$j("#reset2").click(function (){
			$j("#queryForm2 input, select").val("");
			$j("#staStatus2").val("");
		});
		
		$j("#back2").click(function(){
			$j("#query-order-list2").removeClass("hidden");
			$j("#order-detail2").addClass("hidden");
		});
		
		$j("#export2").click(function(){
			var id = $j("#sta_id2").val();
			$j("#frmSoInvoice").attr("src",baseUrl + "/warehouse/exportStaSoInvoice.do?staCmd.id=" + id);
			$j("#export2").addClass("hidden");
		});
		
		$j("#resetRaZero").click(function(){
			var id = $j("#sta_id2").val();
			if(id == '') {jumbo.showMsg("作业单id为空"); return;}
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/updatestainvoice.json",{"staid":id});
			if(rs && rs["result"] == 'success'){
				jumbo.showMsg("操作成功");
			}else if(rs && rs["result"] == 'error'){
				jumbo.showMsg("操作失败");
			}else{
				jumbo.showMsg("系统错误");
			}
		});
		// ============================================================================== tab3:
		
		var plstatus=loxia.syncXhr($j("body").attr("contextpath") + "/formateroptions.json",{"categoryCode":"pickingListStatus"});
		if(!plstatus.exception){
			$j("#tbl-dispatch-list").jqGrid({
				//url:$j("body").attr("contextpath") + "/json/matchListForModelOne.json",
				datatype: "json",
				//["ID","配货批次号","状态","计划配货单据数","已核对单据数","已发货单据数","计划配货商品件数","已核对商品件数","已发货商品件数","创建时间","最后核对时间","最后发货时间","开始配货时间"]
				colNames: ["ID",i18("CODE"),i18("INTSTATUS"),i18("PLANBILLCOUNT"),i18("CHECKEDBILLCOUNT"),i18("SHIPSTACOUNT"),i18("PLANSKUQTY"),i18("CHECKEDSKUQTY"),i18("SHIPSKUQTY"),
						i18("CREATE_TIME"),i18("CHECKEDTIME"),i18("EXECUTEDTIME"),i18("PICKINGTIME")],
				colModel: [{name: "id", index: "id", hidden: true},
							{name :"code",index:"code",width:100,resizable:true,formatter:"linkFmatter",formatoptions:{onclick:"showDetail3"}},
							{name:"intStatus", index:"status" ,width:60,resizable:true,formatter:'select',editoptions:plstatus},
							{name: "planBillCount", index:"planBillCount",width:100,resizable:true},
							{name:"checkedBillCount", index:"checkedBillCount", width:90, resizable:true},
							{name:"shipStaCount",index:"shipStaCount",width:90,resizable:true},
							{name:"planSkuQty",index: "planSkuQty",width:120,resizable:true},
							{name:"checkedSkuQty",index:"checkedSkuQty",width:100,resizable:true},
							{name:"shipSkuQty",index:"shipSkuQty",width:100,resizable:true},
							{name:"createTime",index:"createTime",width:150,resizable:true},
							{name:"checkedTime",index:"checkedTime",width:150,resizable:true},
							{name:"executedTime",index:"executedTime",width:150,resizable:true},
			                {name:"pickingTime",index:"pickingTime",width:150,resizable:true}],
				caption: i18("PICKING_LIST"),
		   		sortname: 'ID',
		  		multiselect: false,
				sortorder: "desc",
				rowNum: jumbo.getPageSize(),
				rowList:jumbo.getPageSizeList(),
			   	height:jumbo.getHeight(),
			   	viewrecords: true,
		   		rownumbers:true,
			   	pager:"#pager3",
			   	jsonReader: { repeatitems : false, id: "0" }
			});
		}else{
			jumbo.showMsg(i18("INIT_SYSTEM_DATA_EXCEPTION"));//初始化系统参数异常
		}
		
		$j("#search3").click(function(){
			
			var formCrtime = getDate($j("#formCrtime").val());
			var toCrtime = getDate($j("#toCrtime").val());
			
			if(formCrtime > toCrtime){
				jumbo.showMsg(i18("CREATE_TIME_RULE"));//起始时间必须小于结束时间！
				return false;
			}
			
			var formPickingTime = getDate($j("#formPickingTime").val());
			var toPickingTime = getDate($j("#toPickingTime").val());
			if(formPickingTime > toPickingTime){
				jumbo.showMsg(i18("PICKING_TIME_RULE"));//起始时间必须小于结束时间！
				return false;
			}
			
			var formOutBoundTime = getDate($j("#formOutBoundTime").val());
			var toOutBoundTime = getDate($j("#toOutBoundTime").val());
			if(formOutBoundTime > toOutBoundTime){
				jumbo.showMsg(i18("OUTBOUND_TIME_RULE"));//起始时间必须小于结束时间！
				return false;
			}
			
			var formCheckedTime = getDate($j("#formCheckedTime").val());
			var toCheckedTime = getDate($j("#toCheckedTime").val());
			if(formCheckedTime > toCheckedTime){
				jumbo.showMsg(i18("CHECKED_TIME_RULE"));//起始时间必须小于结束时间！
				return false;
			}
			
			$j("#tbl-dispatch-list").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/queryinvoiceforpickinglist.json"),
				postData:loxia._ajaxFormToObj("plForm")}).trigger("reloadGrid");
		});
		
		
	/*	
	 * postData:loxia._ajaxFormToObj("plForm")}).trigger("reloadGrid",[{page:1}]);
	 * 
	 * $j("#search2").click(function (){
			$j("#tbl-orderList2").jqGrid('setGridParam',{
				url:loxia.getTimeUrl(baseUrl + "/queryExcStaExport.json"),postData:loxia._ajaxFormToObj("queryForm2")
				}).trigger("reloadGrid");
		});*/
		
		$j("#reset3").click(function(){
			document.getElementById("plForm").reset();
		});
		
		$j("#back3").click(function(){
			$j("#div2").addClass("hidden");
			$j("#showList").removeClass("hidden");
		});
		
		$j("#resetZeroByPickingList").click(function(){
			var id = $j("#pickinglistid").val();
			if(id == null) return;
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/updatestainvoice.json",{"plid":id});
			if(rs && rs["result"] == 'success'){
				jumbo.showMsg("操作成功");
			}else if(rs && rs["result"] == 'error'){
				jumbo.showMsg("操作失败");
			}else{
				jumbo.showMsg("系统错误");
			}
		}); 
});

