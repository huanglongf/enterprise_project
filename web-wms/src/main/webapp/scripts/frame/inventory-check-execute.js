$j.extend(loxia.regional['zh-CN'],{
	INVNEOTRY_CODE: "盘点批编码",
	CREATETIME: "创建时间",
	STATUS: "状态",
	CREATOR: "创建人",
	INVCHECK_OPERATION: "盘点作业单",
	DISTRICTCODE: "库区编码",
	DISTRICTNAME: "库区名称",
	LOCATIONCODE: "库位编码",
	INVCHECK_LOCATION_LIST:"盘点库位列表",
	SKUCODE: "商品JMSKUCODE",
	INVCHECK_DIFFERENCE: "盘点差异",
	INVCHECK_RESULT_LIST: "盘点结果列表",
	CHOOSE_RIGHT_EXCEL: "请选择正确的Excel导入文件",
	SUCCESS: "操作成功",
	ERROR: "操作失败",
	INVCHECK_ISNULL: "盘点批为空",
	SKU_CODE : "SKU编码",
	SKUBARCODE: "商品条形码",
	SKUNAME: "商品名称",
	S_SKUCODE : "原SKU编码",
	S_SKUBAR_CODE : "原商品条形码",
	S_SKUNAME : "原商品名称",
	TYPE : "类型",
	CANCELORNOT: "是否确定取消?",
	INVCHECK_DIFFERENCE_LIST_CHECK :"库存盘点调整数据",
	INVCHECK_COUNT :"数量" ,
	STACODE : "相关调整单",
	EXPIREDATE : "过期时间"
});
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function showDetial(){
	$j("#importOverageFile").val("");
	var baseUrl = $j("body").attr("contextpath");
	var id = $j("#invcheckid").val();
	$j("#tbl_import_detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinvcheckdiffline.json?invcheckid="+id)}).trigger("reloadGrid");
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
	var tr = $j(tag).parents("tr");
	var data=$j("#tbl_pd_list").jqGrid("getRowData",id);
	var rs = loxia.syncXhrPost(baseUrl + "/findinvcheckinfobyid.json",{"invcheckid":id});
	
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
	$j("#invinfo").removeClass("hidden");
	$j("#canelStatusDiv").removeClass("hidden");
	$j("#divImport").removeClass("hidden");
	if(rs && rs.invcheck){
		$j("#code").html(rs.invcheck.code);
		$j("#createTime").html(rs.invcheck.createTime);
		$j("#creator").html(rs.invcheck.creatorName);
		$j("#status").html(map[rs.invcheck.intStatus]);
	} 
	$j("#tbl_import_detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinvcheckdiffline.json?invcheckid="+id)}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_import_detial",{"intStatus":"inventoryCheckStatus"},
			baseUrl + "/findinvcheckdiffline.json",
			{"invcheckid":id});
	$j("#tbl_import_check").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinvcheckmoveLine.json?invcheckid="+id)}).trigger("reloadGrid");
} 

$j(document).ready(function (){
	$j("#dtl_tabs").tabs();
	var lType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckDiffSnLineStatus"});
	var baseUrl = $j("body").attr("contextpath");
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckStatus"});
	var postData = {};
	postData['operatorType']="manager";
	postData['invcheck.status']="CONFIRMOMS";
	$j("#tbl_pd_list").jqGrid({

			url: baseUrl + "/findinventorychecklistmanager.json",
			datatype: "json",
			//colNames: ["ID","盘点批编码","创建时间","状态","创建人"],
			colNames: ["ID",i18("INVNEOTRY_CODE"),i18("CREATETIME"),i18("STATUS"),i18("CREATOR")],
			colModel: [
		            {name: "id", index: "id", hidden: true},		         
		            {name:"code",index:"code",width:150,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true},
					{name:"createTime",index:"createTime",width:150,resizable:true},
					{name:"intStatus",index:"intStatus",width:150,resizable:true,formatter:'select',editoptions:intStatus},
					{name:"creator.userName",index:"creator",width:150,resizable:true}
					],
			caption: i18("INVCHECK_OPERATION"), // 盘点作业单
			height:"auto",
			multiselect: false,
			sortname: "createTime",
			sortorder: "desc",
			rowNum: -1,
			jsonReader: { repeatitems : false, id: "0" },
			postData:postData
	});
	$j("#tbl_import_detial").jqGrid({
		datatype: "json",
		//colNames: ["ID","商品JMSKUCODE","商品条形码","商品名称","库位编码","盘点差异","状态"],
		colNames: ["ID",i18("SKU_CODE"),i18("SKUBARCODE"),"淘宝对接编码",i18("SKUNAME"),i18("LOCATIONCODE"),i18("INVCHECK_DIFFERENCE"),"店铺","库存成本"],
		colModel: [
	            {name: "id", index: "id", hidden: true},
	            {name:"skuCode",index:"skuCode",width:90,resizable:true},
	            {name:"barcode",index:"barcode",width:90,resizable:true},
	            {name:"extensionCode2",index:"extensionCode2",width:90,resizable:true},
	            {name:"skuName",index:"skuName",width:170,resizable:true},
				{name:"locationCode",index:"locationCode",width:90,resizable:true},
				{name:"quantity",index:"quantity",width:70,resizable:true},
				{name:"owner",index:"owner",width:120,resizable:true},
				{name:"skuCost",index:"skuCost",width:70,resizable:true}
				],
		caption: i18("INVCHECK_RESULT_LIST"), // 盘点结果列表
		height:"auto",
		rownumbers:true,
		multiselect: false,
		sortname: "skuCode",
		sortorder: "asc",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pagerEx",
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#tbl_import_check").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKU_CODE"),i18("SKUBARCODE"),i18("SKUNAME"),i18("INVCHECK_COUNT"),i18("STATUS"),i18("LOCATIONCODE"),i18("STACODE"),i18("EXPIREDATE")],
		colModel: [
				{name: "id", index: "id", hidden: true},
				{name:"skuCode",index:"skuCode",width:110,resizable:true},
				{name:"barCode",index:"barCode",width:100,resizable:true},
				{name:"skuName",index:"skuName",width:170,resizable:true},
				{name:"quantity",index:"quantity",width:70,resizable:true},
				{name:"invStatusName",index:"invStatusName",width:70,resizable:true},
				{name:"locationCode",index:"locationCode",width:70,resizable:true},
				{name:"staCode",index:"staCode",width:70,resizable:true},
				{name:"expireDate",index:"expireDate",width:70,resizable:true}],
				
		caption: i18("INVCHECK_DIFFERENCE_LIST_CHECK"), // "库存盘点调整数据",
		height:"auto",
		rownumbers:true,
		multiselect: false,
		sortname: "skuCode",
		sortorder: "asc",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pagerEx",
		jsonReader: { repeatitems : false, id: "0" }
	});
	// 差异未处理状态-返回
	$j("#toBackDetial").click(function(){
		$j("#divPd").removeClass("hidden");
		$j("#invinfo").addClass("hidden");
		$j("#canelStatusDiv").addClass("hidden");
		$j("#tbl_pd_list").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinventorychecklistmanager.json")}).trigger("reloadGrid");
	});
	// 取消
	$j("#canelBtn").click(function(){
		if(!confirm(i18("CANCELORNOT"))) return loxia.SUCCESS; // 是否确定取消
		var id = $j("#invcheckid").val();
		if(id != ''){
			var postData = {}; 
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/cancelInventoryCheckManager.json?invcheckid=" + id,postData)
			if(rs && rs.rs=='success')jumbo.showMsg(i18("SUCCESS")); // 操作成功
			else jumbo.showMsg(rs.msg);
		}else{
			jumbo.showMsg(i18("INVCHECK_ISNULL")); // 盘点批为空
		}
	});
	// 状态修改
	$j("#confirmType").click(function(){
		var postData = {};
		postData["code"] = $j("#code").text();
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/invcheckexecute.json",postData);
		if(rs && rs.rs=='success'){
			jumbo.showMsg("盘点确认成功！");
			 location.reload();
		}else{
			jumbo.showMsg(rs.msg);
		}
	});
	$j("#exportOverage").click(function(){
		var iframe = document.createElement("iframe");
		iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/exportCheckOverage.do?invcheckid="+$j("#invcheckid").val()+"&code="+$j("#code").text());
		iframe.style.display = "none";
		$j("#downloadOverage").append($j(iframe));
	});
	$j("#importOverage").click(function(){
		if(!/^.*\.xls$/.test($j("#importOverageFile").val())){
			jumbo.showMsg(i18("CHOOSE_RIGHT_EXCEL")); // 请选择正确的Excel导入文件
			return loxia.SUCCESS;
		}
		var invcheckid = $j("#invcheckid").val();
		if(invcheckid != ''){
			loxia.lockPage();
			$j("#importOverageForm").attr("action",$j("body").attr("contextpath") + "/warehouse/importCheckOverage.do?invcheckid=" + invcheckid);
			$j("#importOverageForm").submit();		
		}
	});
});
