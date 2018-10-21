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
	SN_CODE : "SN号",
	TBL_SN_EX : "SN号盘点差异表",
	TBL_SN_SN_EX : "SN号盘点商品差异表",
	CONFIRM_USER: "请输入财务确认人姓名 ",
	CANCELORNOT: "是否确定取消?"
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
function reloadTable(){
	var baseUrl = $j("body").attr("contextpath");
	var id = $j("#invcheckid").val();
	if(id != ''){
		$j("#tbl_import_detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinvcheckdiffline.json?invcheckid="+id)}).trigger("reloadGrid");		
	}
	$j("#file").val("");
	$j("#newStatusDiv").addClass("hidden");
	$j("#invinfo").removeClass("hidden");
	$j("#canelStatusDiv").removeClass("hidden");
	$j("#divImport").removeClass("hidden");
	$j("#import").addClass("hidden");
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
	if(data["intStatus"] == '1'){// 新建 CREATED
		$j("#canelStatusDiv").addClass("hidden");
		$j("#divImport").removeClass("hidden");
		$j("#invinfo").removeClass("hidden");
		$j("#newStatusDiv").removeClass("hidden");
		$j("#import").removeClass("hidden");
		if(rs && rs.invcheck){
			$j("#code").html(rs.invcheck.code);
			$j("#createTime").html(rs.invcheck.createTime);
			$j("#creator").html(rs.invcheck.creatorName);
			$j("#status").html(map[rs.invcheck.intStatus]);
		}
		$j("#tbl_detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinvchecklinedetial.json?invcheckid="+id)}).trigger("reloadGrid");

		jumbo.bindTableExportBtn("tbl_detial",{"intStatus":"inventoryCheckStatus"},
				baseUrl + "/findinvchecklinedetial.json",
				{"invcheckid":id});
	}else if(data["intStatus"] == '5'){// 差异未处理  UNEXECUTE
		$j("#newStatusDiv").addClass("hidden");
		$j("#invinfo").removeClass("hidden");
		$j("#canelStatusDiv").removeClass("hidden");
		$j("#divImport").removeClass("hidden");
		$j("#confirmUser").val("");
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
		$j("#tbl_inv_count").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinvcheckcount.json?invcheckid="+id)}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_inv_count",{"intStatus":"inventoryCheckStatus"}, baseUrl + "/findinvcheckcount.json", {"invcheckid":id});
	}
} 

$j(document).ready(function (){
	$j("#dtl_tabs").tabs();
	var lType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckDiffSnLineStatus"});
	var baseUrl = $j("body").attr("contextpath");
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckStatus"});
	$j("#tbl_pd_list").jqGrid({
			url: baseUrl + "/findinventorychecklist.json",
			datatype: "json",
			//colNames: ["ID","盘点批编码","创建时间","状态","创建人"],
			colNames: ["ID",i18("INVNEOTRY_CODE"),i18("CREATETIME"),i18("STATUS"),i18("CREATOR")],
			colModel: [
		            {name: "id", index: "id", hidden: true},		         
		            {name:"code",index:"code",width:150,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true},
					{name:"createTime",index:"createTime",width:150,resizable:true},
					{name:"intStatus",index:"intStatus",width:70,resizable:true,formatter:'select',editoptions:intStatus},
					{name:"creator.userName",index:"creator",width:150,resizable:true}
					],
			caption: i18("INVCHECK_OPERATION"), // 盘点作业单
			height:"auto",
			multiselect: false,
			sortname: "createTime",
			sortorder: "desc",
			rowNum: -1,
			jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#tbl_detial").jqGrid({
		datatype: "json",
		//colNames: ["ID","库位编码","库区编码","库区名称"],
		colNames: ["ID",i18("LOCATIONCODE"),i18("DISTRICTCODE"),i18("DISTRICTNAME")],
		colModel: [
				{name: "id", index: "id", hidden: true},		         
				{name:"locationCode",index:"locationCode",width:200,resizable:true},
				{name:"districtCode",index:"districtCode",width:200,resizable:true},
				{name:"districtName",index:"districtName",width:200,resizable:true}
				],
		caption: i18("INVCHECK_LOCATION_LIST"), // 盘点库位列表
		height:"auto",
		multiselect: false,
		sortname: "locationCode",
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
		colNames: ["ID",i18("SKU_CODE"),i18("SKUBARCODE"),i18("SKUNAME"),i18("LOCATIONCODE"),i18("INVCHECK_DIFFERENCE"),"店铺","库存成本","过期时间","状态"],
		colModel: [
	            {name: "id", index: "id", hidden: true},
	            {name:"skuCode",index:"skuCode",width:90,resizable:true},
	            {name:"barcode",index:"barcode",width:90,resizable:true},
	            {name:"skuName",index:"skuName",width:170,resizable:true},
				{name:"locationCode",index:"locationCode",width:90,resizable:true},
				{name:"quantity",index:"quantity",width:70,resizable:true},
				{name:"owner",index:"owner",width:120,resizable:true},
				{name:"skuCost",index:"skuCost",width:70,resizable:true},
				{name:"sexpireDate",index:"sexpireDate",width:70,resizable:true},
				{name:"invStatusName",index:"invStatusName",width:100,resizable:true}
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
	$j("#tbl_inv_count").jqGrid({
	datatype: "json",
		colNames: ["ID","商品JMSKUCODE","商品条形码","商品名称","数量"],
		colNames: ["ID",i18("SKU_CODE"),i18("SKUBARCODE"),i18("SKUNAME"),"数量"],
		colModel: [
				{name: "id", index: "id", hidden: true},
				{name:"skuCode",index:"skuCode",width:110,resizable:true},
				{name:"barcode",index:"barcode",width:100,resizable:true},
				{name:"skuName",index:"skuName",width:170,resizable:true},
				{name:"quantity",index:"quantity",width:70,resizable:true}],
		caption: "盘点差异列表-数量", // "盘点差异列表-数量",
		height:"auto",
		width:"auto",
		multiselect: false,
		sortname: "skuCode",
		sortorder: "asc",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pageCount",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	// 新建状态-返回
	$j("#toBack").click(function(){
		$j("#divPd").removeClass("hidden");
		$j("#newStatusDiv").addClass("hidden");
		$j("#invinfo").addClass("hidden");
		$j("#divDetialList").trigger("click");
		$j("#import").addClass("hidden");
		$j("#tbl_pd_list").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinventorychecklist.json")}).trigger("reloadGrid");
	});
	// 差异未处理状态-返回
	$j("#toBackDetial").click(function(){
		$j("#divPd").removeClass("hidden");
		$j("#invinfo").addClass("hidden");
		$j("#canelStatusDiv").addClass("hidden");
		$j("#newStatusDiv").addClass("hidden");
		$j("#import").addClass("hidden");
		$j("#tbl_pd_list").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinventorychecklist.json")}).trigger("reloadGrid");
	});

	// 导入
	$j("#importFile").click(function(){
		if((!/^.*\.xls$/.test($j("#file").val())) && (!/^.*\.xlsx$/.test($j("#file").val()))){
			jumbo.showMsg(i18("CHOOSE_RIGHT_EXCEL")); // 请选择正确的Excel导入文件
			return false;
		}
		var invcheckid = $j("#invcheckid").val();
		if(invcheckid != ''){
			loxia.lockPage();
			$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/importinvcheck.do?invcheckid=" + invcheckid);
			$j("#importForm").submit();		
		}
	});
	$j("#importFile1").click(function(){
		if((!/^.*\.xls$/.test($j("#file1").val()))&&(!/^.*\.xlsx$/.test($j("#file1").val())) ){
			jumbo.showMsg(i18("CHOOSE_RIGHT_EXCEL")); // 请选择正确的Excel导入文件
			return false;
		}
		var invcheckid = $j("#invcheckid").val();
		if(invcheckid != ''){
			loxia.lockPage();
			$j("#importForm1").attr("action",$j("body").attr("contextpath") + "/warehouse/importinvcheck.do?invcheckid=" + invcheckid);
			$j("#importForm1").submit();		
		}
	});
	// 导出盘点表
	$j("#exportinvcheck, #export").click(function(){
		var id = $j("#invcheckid").val();
		if(id != ''){
			$j("#exportInventoryCheck").attr("src",$j("body").attr("contextpath") + "/warehouse/exportinventorycheck.do?invcheckid=" + id);
		}else{
			jumbo.showMsg(i18("INVCHECK_ISNULL")); // 盘点批为空
		}
	});
	// 取消
	$j("#cancel,#canelBtn").click(function(){
		if(!confirm(i18("CANCELORNOT"))) return loxia.SUCCESS; // 是否确定取消
		var id = $j("#invcheckid").val();
		if(id != ''){
			var postData = {}; 
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/cancelinventorycheck.json?invcheckid=" + id,postData)
			if(rs && rs.rs=='success'){
				// 操作成功
				jumbo.showMsg(i18("SUCCESS"));
				 location.reload();
			}else jumbo.showMsg(rs.msg);
		}else{
			jumbo.showMsg(i18("INVCHECK_ISNULL")); // 盘点批为空
		}
	});
	// 差异确认
	$j("#confirm").click(function(){
		loxia.lockPage();
		var id = $j("#invcheckid").val();
		var user = $j("#confirmUser").val();
		if(user == ''){
			jumbo.showMsg(i18("CONFIRM_USER")); // 请输入财务确认人姓名 
			loxia.unlockPage();
			return loxia.SUCCESS;
			
		}
		if(id != ''){
			var postData = {}; 
			postData['invcheck.id'] = id;
			postData['invcheck.confirmUser'] = user;
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/confirminventorycheck.json",postData);
			if(rs && rs.rs=='success'){
				loxia.unlockPage();
				jumbo.showMsg(i18("SUCCESS")); // 操作成功
				 location.reload();
			}
			else {
				loxia.unlockPage();
				jumbo.showMsg(rs.msg);
			}
		}else{
			loxia.unlockPage();
			jumbo.showMsg(i18("INVCHECK_ISNULL")); // 盘点批为空
		}
	});
	
	// 状态修改
	$j("#confirmType").click(function(){
		var baseUrl = $j("body").attr("contextpath");
		var InventoryCheck = $j("#code").text();
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/changestatus.json?code="+$j("#code").text());
		if(rs && rs.rs=='success'){
			jumbo.showMsg("盘点确认成功！");
			 //location.reload();
			window.location.href=baseUrl+"/warehouse/invckhandel.do"; 
		}else{
			jumbo.showMsg("处理失败！");
		}
	});
	$j("#exportOverage").click(function(){
		var iframe = document.createElement("iframe");
		iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/exportCheckOverage.do?invcheckid="+$j("#invcheckid").val()+"&code="+$j("#code").text());
		iframe.style.display = "none";
		$j("#downloadOverage").append($j(iframe));
	});

	$j("#importOverage").click(function(){
		if((!/^.*\.xls$/.test($j("#importOverageFile").val()))&& (!/^.*\.xlsx$/.test($j("#importOverageFile").val()))){
			jumbo.showMsg(i18("CHOOSE_RIGHT_EXCEL")); // 请选择正确的Excel导入文件
			return false;
		}
		var invcheckid = $j("#invcheckid").val();
		if(invcheckid != ''){
			loxia.lockPage();
			$j("#importOverageForm").attr("action",$j("body").attr("contextpath") + "/warehouse/importCheckOverage.do?invcheckid=" + invcheckid);
			$j("#importOverageForm").submit();		
		}
	});
});
