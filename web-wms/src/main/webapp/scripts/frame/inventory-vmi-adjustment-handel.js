$j.extend(loxia.regional['zh-CN'],{
	INVNEOTRY_CODE: "VMI调整编码",
	CREATETIME: "创建时间",
	STATUS: "状态",
	
	INVCHECK_OPERATION: "VMI库存调整",
	
	INVCHECK_LOCATION_LIST:"差异调整表",

	SKUCODE: "商品JMSKUCODE",
	
	
	INVCHECK_DIFFERENCE: "调整差异",
	INVCHECK_STATUS: "库存状态",
	
	INVCHECK_RESULT_LIST: "库存调整列表",
	
	CHOOSE_RIGHT_EXCEL: "请选择正确的Excel导入文件",
	LOCATIONCODE: "库位编码",
	SUCCESS: "操作成功",
	ERROR: "操作失败",
	INVCHECK_ISNULL: "VMI库存调整批为空",
	SLIPCODE :"相关单据号",
	
	SKU_CODE : "SKU编码",
	SKUBARCODE: "商品条形码",
	SKUNAME: "商品名称",
	S_SKUCODE : "原SKU编码",
	S_SKUBAR_CODE : "原商品条形码",
	S_SKUNAME : "原商品名称",
	TYPE : "类型",
	SKU_QUANTITY : "商品数量",
	CANCELORNOT: "是否确定取消?",
	INVCHECK_ISNULL: "调整单为空"
});
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function reloadTable(){
	var id = $j("#invcheckid").val();
	var baseUrl = $j("body").attr("contextpath");
	if(id != ''){
		$j("#tbl_import_detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinvcheckdiffline.json?invcheckid="+id)}).trigger("reloadGrid");		
	}
	$j("#file").val("");
	$j("#newStatusDiv").addClass("hidden");
	$j("#invinfo").removeClass("hidden");
	$j("#canelStatusDiv").removeClass("hidden");
	$j("#divImport").removeClass("hidden");
	$j("#import").removeClass("hidden");
	loxia.unlockPage();
}

function showMsg(s){
	jumbo.showMsg(s);
}

function showDetail(tag){
	var baseUrl = $j("body").attr("contextpath");
	$j("#divPd").addClass("hidden");
	var id = $j(tag).parents("tr").attr("id");
	$j("#invcheckid").val(id);
	$j("#file").val("");
	var tr = $j(tag).parents("tr");
	var data=$j("#tbl_pd_list").jqGrid("getRowData",id);
	var statusMap = loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckStatus"});
	var value=statusMap.value,map={};
	if(value&&value.length>0){
		var array=value.split(";");
		$j.each(array,function(i,e){
			if(e.length>0){
				var each=e.split(":");
				map[each[0]]=each[1];
			}
		});
	}
	$j("#code").html(data["code"]);
	$j("#createTime").html(data["createTime"]);
	$j("#creator").html(data["creator"]);
	$j("#status").html(map[data["intStatus"]]);
	$j("#slipCode").html(data["slipCode"]);
	if(data["intInvCheckStatus"] == '1'){// 新建 CREATED
		$j("#canelStatusDiv").addClass("hidden");
		$j("#divImport").addClass("hidden");
		$j("#invinfo").removeClass("hidden");
		$j("#newStatusDiv").removeClass("hidden");
		$j("#import").removeClass("hidden");
		$j("#tbl_detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findVMIinvCheckLineDetial.json?invcheckid="+id)}).trigger("reloadGrid");

		jumbo.bindTableExportBtn("tbl_detial",{"intStatus":"inventoryCheckStatus"},
				baseUrl + "/findVMIinvCheckLineDetial.json",
				{"invcheckid":id});
	}else if(data["intInvCheckStatus"] == '5'){// 差异未处理  UNEXECUTE
//		if(data["slipCode"] != ''){
//			var channel = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getbichannelinfobyic.json?icId="+id);
//			$j("#invoice").addClass("hidden");
//			if(channel && channel.isInboundInvoice == true){//测试341 正式322
//				invoiceInfoInit(1,"invoice",data["slipCode"]);
//			}
//		}
		$j("#newStatusDiv").addClass("hidden");
		$j("#invinfo").removeClass("hidden");
		$j("#canelStatusDiv").removeClass("hidden");
		$j("#divImport").removeClass("hidden");
		$j("#import").removeClass("hidden");
		$j("#tbl_import_detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinvcheckdiffline.json?invcheckid="+id)}).trigger("reloadGrid");

		jumbo.bindTableExportBtn("tbl_import_detial",{"intStatus":"inventoryCheckStatus"},
				baseUrl + "/findinvcheckdiffline.json",
				{"invcheckid":id});
	}

} 
//======================================================
$j(document).ready(function (){
	$j("#dtl_tabs").tabs();
	var lType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckDiffSnLineStatus"});
	var baseUrl = $j("body").attr("contextpath");
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckStatus"});
	$j("#tbl_pd_list").jqGrid({
			url: baseUrl + "/findvmiinventorychecklist.json",
			datatype: "json",
			//colNames: ["ID","VMI调整编码","创建时间","状态","SLIPCODE"],
			colNames: ["ID",i18("INVNEOTRY_CODE"),i18("CREATETIME"),i18("STATUS"),i18("SLIPCODE"),"店铺","调整原因"],
			colModel: [
		            {name: "id", index: "id", hidden: false},
		            {name:"code",index:"code",width:150,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true},
					{name:"createTime",index:"createTime",width:150,resizable:true},
					{name:"intInvCheckStatus",index:"intInvCheckStatus",width:70,resizable:true,formatter:'select',editoptions:intStatus},
					{name:"slipCode",index:"slipCode",width:150,resizable:true},
					{name:"owner",index:"owner",width:260,resizable:true},
					{name:"reasonCode",index:"reasonCode",width:260,resizable:true}
					],
			caption: i18("INVCHECK_OPERATION"), // VMI库存调整
			height:"auto",
			multiselect: false,
			sortname: "createTime",
			sortorder: "desc",
			rowNum: -1,
			jsonReader: { repeatitems : false, id: "0" }
	});
//=================================================
	$j("#tbl_detial").jqGrid({
		datatype: "json",
		//colNames: ["ID","商品编码","商品名称","商品数量"],
		colNames: ["ID",i18("SKU_CODE"),i18("SKUBARCODE"),i18("SKUNAME"),i18("SKU_QUANTITY")],
		colModel: [
				{name: "id", index: "id", hidden: true},	
				{name:"skuCode",index:"skuCode",width:90,resizable:true},
				{name:"skuBarCode",index:"skuBarCode",width:200,resizable:true},
				{name:"skuName",index:"skuName",width:200,resizable:true},
				{name:"quantity",index:"quantity",width:200,resizable:true}
				],
		caption: i18("INVCHECK_LOCATION_LIST"), // 差异调整表
		height:"auto",
		multiselect: false,
		sortname: "skuCode",
		sortorder: "asc",
		pager : "#pager1",
		//height:jumbo.getHeight(),
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		jsonReader: { repeatitems : false, id: "0" }
	});
	

	$j("#tbl_import_detial").jqGrid({
		datatype: "json",
		//colNames: ["ID","商品JMSKUCODE","商品条形码","商品名称","库位编码","盘点差异","状态"],
		colNames: ["ID",i18("SKUCODE"),i18("SKUBARCODE"),i18("SKUNAME"),"库位编码",i18("INVCHECK_DIFFERENCE"),i18("INVCHECK_STATUS")],
		colModel: [
	            {name: "id", index: "id", hidden: true},
	            {name:"skuCode",index:"skuCode",width:90,resizable:true},
	            {name:"barcode",index:"barcode",width:90,resizable:true},
	            {name:"skuName",index:"skuName",width:170,resizable:true},
				{name:"locationCode",index:"locationCode",width:90,resizable:true},
				{name:"quantity",index:"quantity",width:70,resizable:true},
				{name:"invStatusName",index:"invStatusName",width:70,resizable:true}
				],
		caption: i18("INVCHECK_RESULT_LIST"), // 库存调整列表
		height:"auto",
		multiselect: false,
		sortname: "skuCode",
		sortorder: "asc",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pagerEx",
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	// 新建状态-返回
	$j("#toBack").click(function(){
		$j("#divPd").removeClass("hidden");
		$j("#newStatusDiv").addClass("hidden");
		$j("#invinfo").addClass("hidden");
		$j("#divDetialList").trigger("click");
		$j("#import").addClass("hidden");
		$j("#tbl_pd_list").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findvmiinventorychecklist.json")}).trigger("reloadGrid");
	});
	// 差异未处理状态-返回
	$j("#toBackDetial").click(function(){
		$j("#divPd").removeClass("hidden");
		$j("#invinfo").addClass("hidden");
		$j("#canelStatusDiv").addClass("hidden");
		$j("#newStatusDiv").addClass("hidden");
		$j("#import").addClass("hidden");
		$j("#tbl_pd_list").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findvmiinventorychecklist.json")}).trigger("reloadGrid");
//		$j("#divImport").removeClass("hidden");
//		$j("#divDetialList").addClass("hidden");
	});

	// 导入
	$j("#importFile").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CHOOSE_RIGHT_EXCEL")); // 请选择正确的Excel导入文件
			return loxia.SUCCESS;
		}
		var invcheckid = $j("#invcheckid").val();
		if(invcheckid != ''){
			loxia.lockPage();
			$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/importVMIInvCheck.do?invcheckid=" + invcheckid);
			$j("#importForm").submit();		
		}
	});
	// 导出盘点表
	$j("#exportinvcheck, #export").click(function(){
		var id = $j("#invcheckid").val();
		if(id != ''){
			//$j("#importForm").attr("src",$j("body").attr("contextpath") + "/warehouse/exportinventorycheck.do?invcheckid=" + id);			
			$j("#exportInventoryCheck").attr("src",$j("body").attr("contextpath") + "/warehouse/exportVMIInventoryCheck.do?invcheckid=" + id);
		}else{
			jumbo.showMsg(i18("INVCHECK_ISNULL")); // 库存调整批为空
		}
	});
	
	// 取消
	$j("#cancel, #cancelInv").click(function(){
		if(!confirm(i18("CANCELORNOT"))) return loxia.SUCCESS; // 是否确定取消
		var id = $j("#invcheckid").val();
		if(id != ''){
			var postData = {}; 
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/cancelInvCheck.json?invcheckid=" + id,postData)
			if(rs && rs.rs=='success')jumbo.showMsg(i18("SUCCESS")); // 操作成功
			else jumbo.showMsg(rs.msg);
		}else{
			jumbo.showMsg(i18("INVCHECK_ISNULL")); // 盘点批为空
		}
	});
	
	// 差异确认
	$j("#confirm").click(function(){
		loxia.lockPage();
		var id = $j("#invcheckid").val();
		
		if(id != ''){
			var postData = {}; 
			postData['invcheck.id'] = id;
//			if(IS_INBOUND_INVOICE == 1){
//				if(IS_INVOICE_CODE == 1){
//					postData['invcheck.invoiceNumber'] =  $j("#invoice_invoiceNumber").val();
//					postData['invcheck.dutyPercentage'] = $j("#invoice_dutyPercentage").val();
//					postData['invcheck.miscFeePercentage'] = $j("#invoice_miscFeePercentage").val();
//				} else {
//					loxia.unlockPage();
//					jumbo.showMsg("请填写入正确的发票号！"+INVOICE_ERROR);
//					return;
//				}
//				
//			}
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/confirmVMIInvCKAdjustment.json",postData)
			if(rs && rs.rs=='success'){
				loxia.unlockPage();
				jumbo.showMsg(i18("SUCCESS")); // 操作成功
			}
			else {
				loxia.unlockPage();
				jumbo.showMsg(rs.msg);
			}
		}else{
			loxia.unlockPage();
			jumbo.showMsg(i18("INVCHECK_ISNULL")); //  库存调整批为空
		}
	});
	
});
