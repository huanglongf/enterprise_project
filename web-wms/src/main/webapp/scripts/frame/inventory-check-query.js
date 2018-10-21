$j.extend(loxia.regional['zh-CN'],{
	INVCHECK_CODE: "盘点批编码",
	CREATETIME: "创建时间",
	STATUS: "状态",
	CREATOR: "创建人",
	CONFIRM_USER: "财务确认人",

	INVCHECK_OPERATION: "盘点作业单",
	
	DISTRICTCODE: "库区编码",
	DISTRICTNAME: "库区名称",
	LOCATIONCODE: "库位编码",
	INVCHECK_DETAIL_LIST:"盘点明细列表",

	SKU_CODE : "SKU编码",
	SKUBARCODE: "商品条形码",
	SKUNAME: "商品名称",
	S_SKUCODE : "原SKU编码",
	S_SKUBAR_CODE : "原商品条形码",
	S_SKUNAME : "原商品名称",
	TYPE : "类型",	
	SKUCODE: "商品JMSKUCODE",
	SKUBARCODE: "商品条形码",
	SKUNAME: "商品名称",	
	INVCHECK_DIFFERENCE: "盘点差异",
	INVCHECK_DIFFERENCE_LIST: "盘点差异列表",
	INVCHECK_COUNT :"数量" ,
	INVCHECK_DIFFERENCE_LIST_COUNT :"盘点差异列表-数量",
	INVCHECK_DIFFERENCE_LIST_CHECK :"库存盘点调整数据",
	STACODE : "相关调整单",
	EXPIREDATE : "过期时间"
});
 
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function showDetail(tag){
	var baseUrl = $j("body").attr("contextpath");
	var id = $j(tag).parents("tr").attr("id");
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
	$j("#tbl_detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinvchecklinedetial.json?invcheckid="+id)}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_detial",{"intStatus":"inventoryCheckStatus"},
			baseUrl + "/findinvchecklinedetial.json",
			{"invcheckid":id});
	$j("#tbl_ex_detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinvcheckdiffline.json?invcheckid="+id)}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_ex_detial",{"intStatus":"inventoryCheckStatus"},
			baseUrl + "/findinvcheckdiffline.json",
			{"invcheckid":id});
	
	$j("#tbl_inv_count").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinvcheckcount.json?invcheckid="+id)}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_inv_count",{"intStatus":"inventoryCheckStatus"}, baseUrl + "/findinvcheckcount.json", {"invcheckid":id});	
	$j("#code").html(rs.invcheck.code);
	$j("#createTime").html(rs.invcheck.createTime);
	$j("#creator").html(rs.invcheck.creatorName);
	$j("#confirmUser").html(rs.invcheck.confirmUser);
	$j("#status").html(map[rs.invcheck.intStatus]);
	$j("#divPd").addClass("hidden");
	$j("#divList").removeClass("hidden");
	
	$j("#tbl_inv_check").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinvcheckmoveLine.json?invcheckid="+id)}).trigger("reloadGrid");
}

$j(document).ready(function (){
	$j("#dtl_tabs").tabs();
	$j("#tabs").tabs();
	var baseUrl = $j("body").attr("contextpath");
	var lType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckDiffSnLineStatus"});
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckStatus"});
	
	$j("#tbl_list").jqGrid({
			url: baseUrl + "/findallinventorychecklist.json",
			datatype: "json",
			//colNames: ["ID","盘点批编码","创建时间","状态","创建人","财务确认人"],
			colNames: ["ID",i18("INVCHECK_CODE"),i18("CREATETIME"),i18("STATUS"),i18("CREATOR"),i18("CONFIRM_USER")],
			colModel: [
			           {name: "id", index: "id", hidden: true},		         
			            {name:"code",index:"code",width:150,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true},
						{name:"createTime",index:"createTime",width:150,resizable:true},
						{name:"intStatus",index:"intStatus",width:70,resizable:true,formatter:'select',editoptions:intStatus},
						{name:"creatorName",index:"creatorName",width:150,resizable:true},
						{name:"confirmUser",index:"confirmUser",width:150,resizable:true}
					],
			caption: i18("INVCHECK_OPERATION"), // "盘点作业单",
			height:jumbo.getHeight(),
			rowNum: jumbo.getPageSize(),
			rowList:jumbo.getPageSizeList(),
		   	sortname: 'id',
		    pager: '#pager',
		    sortname: 'createTime',
			sortorder: "desc", 
			multiselect: false,
			viewrecords: true,
   			rownumbers:true,
			jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl_list",{"intStatus":"inventoryCheckStatus"});
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
		caption: i18("INVCHECK_DETAIL_LIST"), //  "盘点明细列表",
		height:"auto",
		multiselect: false,
		sortname: "locationCode",
		sortorder: "asc",
		pager : "#pager1",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#tbl_ex_detial").jqGrid({
		datatype: "json",
		//colNames: ["ID","商品JMSKUCODE","商品条形码","商品名称","库位编码","盘点差异","状态"],
		colNames: ["ID",i18("SKU_CODE"),i18("SKUBARCODE"),i18("SKUNAME"),i18("LOCATIONCODE"),i18("INVCHECK_DIFFERENCE"),"店铺","库存成本"],
		colModel: [
				{name: "id", index: "id", hidden: true},
				{name:"skuCode",index:"skuCode",width:110,resizable:true},
				{name:"barcode",index:"barcode",width:100,resizable:true},
				{name:"skuName",index:"skuName",width:170,resizable:true},
				{name:"locationCode",index:"locationCode",width:100,resizable:true},
				{name:"quantity",index:"quantity",width:70,resizable:true},
				{name:"owner",index:"owner",width:120,resizable:true},
				{name:"skuCost",index:"skuCost",width:70,resizable:true}],
		caption: i18("INVCHECK_DIFFERENCE_LIST"), // "盘点差异列表",
		height:"auto",
		width:"auto",
		multiselect: false,
		sortname: "skuCode",
		sortorder: "asc",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pagerEx",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#tbl_inv_count").jqGrid({
		datatype: "json",
		//colNames: ["ID","商品JMSKUCODE","商品条形码","商品名称","数量"],
		colNames: ["ID",i18("SKU_CODE"),i18("SKUBARCODE"),i18("SKUNAME"),i18("INVCHECK_COUNT")],
		colModel: [
				{name: "id", index: "id", hidden: true},
				{name:"skuCode",index:"skuCode",width:110,resizable:true},
				{name:"barcode",index:"barcode",width:100,resizable:true},
				{name:"skuName",index:"skuName",width:170,resizable:true},
				{name:"quantity",index:"quantity",width:70,resizable:true}],
		caption: i18("INVCHECK_DIFFERENCE_LIST_COUNT"), // "盘点差异列表-数量",
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
	$j("#tbl_inv_check").jqGrid({
		datatype: "json",
		//colNames: ["ID","商品JMSKUCODE","商品条形码","商品名称","数量"],
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
	
	$j("#query").click(function(){
		var postData = loxia._ajaxFormToObj("query_form");
		$j("#tbl_list").jqGrid('setGridParam',{url: window.$j("body").attr("contextpath") + "/findallinventorychecklist.json",page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_list",{"intStatus":"inventoryCheckStatus"},
			$j("body").attr("contextpath") + "/findallinventorychecklist.json",
			postData);
	});
	$j("#reset").click(function(){
		$j("#query_form input,select").val("");
	});
	$j("#back").click(function(){
		$j("#divPd").removeClass("hidden");
		$j("#divList").addClass("hidden");
	});
});