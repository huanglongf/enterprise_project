$j.extend(loxia.regional['zh-CN'],{
	STATUSNEW :		"新建",
	STATUSOCCUPIED: "配货中",
	STATUSFINISHED:	"已完成",
	STATUSCANCELED: "取消已处理",
	
	INVCHECK_CODE: "VMI调整编码",
	CREATETIME: "创建时间",
	STATUS: "状态",
	CONFIRM_USER: "财务确认人",

	INVCHECK_OPERATION: "VMI库存调整",
	
	INVCHECK_DETAIL_LIST:"差异调整表",

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
	INVCHECK_DIFFERENCE: "调整差异",
	INVCHECK_DIFFERENCE_LIST: "调整差异列表",
	DISTRICTCODE :"库位编码",
	INVCHECK_STATUS: "库存状态",
	SKU_QUANTITY : "商品数量",
	SLIPCODE :"相关单据号"
});
 
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function showDetail(tag){
	var baseUrl = $j("body").attr("contextpath");
	var id = $j(tag).parents("tr").attr("id");
	var data=$j("#tbl_list").jqGrid("getRowData",id);
	
	$j("#tbl_ex_detial").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findVMIinvCheckLineDetial.json?invcheckid="+id)}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_ex_detial",{"intStatus":"inventoryCheckStatus"},
			baseUrl + "/findVMIinvCheckLineDetial.json",
			{"invcheckid":id});
	
	$j("#tbl_ex_detial2").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findinvcheckdiffline.json?invcheckid="+id)}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_ex_detial2",{"intStatus":"inventoryCheckStatus"},
			baseUrl + "/findinvcheckdiffline.json",
			{"invcheckid":id});
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
	
	$j("#slipCode").text(data['slipCode']);
	$j("#code").text(data['code']);
	$j("#createTime").text(data['createTime']);
	$j("#creator").text(data['creator']);
	$j("#confirmUser").text(data['confirmUser']);
	$j("#status").text(map[data['intStatus']]);
	$j("#slipCode").text(data['slipCode']);
	
	$j("#divPd").addClass("hidden");
	$j("#divList").removeClass("hidden");
}

$j(document).ready(function (){
	$j("#tabs").tabs();
	var baseUrl = $j("body").attr("contextpath");
	//var lType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckDiffSnLineStatus"});
	var status = loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckStatus"});

	var result = loxia.syncXhrPost(loxia.getTimeUrl(baseUrl + "/findShopInfoByDefaultOuId.json"));
	for(var idx in result){
		$j('<option value="' + result[idx].id + '">' + result[idx].name +'</option>').appendTo($j("#companyshop"));
	}
	
	$j("#tbl_list").jqGrid({
			url: baseUrl + "/findallvmiinventorychecklist.json",
			datatype: "json",
			//colNames: ["ID","VMI调整编码","创建时间","状态","相关单据号"],
			colNames: ["ID",i18("INVCHECK_CODE"),i18("CREATETIME"),i18("STATUS"),i18("SLIPCODE"),"店铺名称","调整原因"],
			colModel: [
			           {name: "id", index: "id", hidden: true},		         
			           {name:"code",index:"code",width:100,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true},
			           {name:"createTime",index:"createTime",width:160,resizable:true},
			           {name:"intStatus",index:"status",width:100,resizable:true,formatter:'select',editoptions:status},
			           {name:"slipCode",index:"slipCode",width:100,resizable:true},
			           {name:"owner",index:"owner",width:260,resizable:true},
			           {name:"reasonCode",index:"reasonCode",width:260,resizable:true}
					],
			caption: i18("INVCHECK_OPERATION"), // "VMI库存调整",
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
			jsonReader: { repeatitems : false, id: "0" },
			
			gridComplete: function(){
				$j("#tbl_list").closest(".ui-jqgrid-bdiv").css({ 'overflow-x': 'hidden' });
			}
	}).setGridWidth(1000);
	
	jumbo.bindTableExportBtn("tbl_list",{"intStatus":"inventoryCheckStatus"});

	$j("#tbl_detial").jqGrid({
		datatype: "json",
		//colNames: ["ID","库位编码","库区编码","库区名称"],
		colNames: ["ID",i18("LOCATIONCODE"),i18("DISTRICTCODE"),i18("DISTRICTNAME")],
		colModel: [
				{name: "id", index: "id", hidden: true},		         
				{name:"locationCode",index:"locationCode",width:200,resizable:true},
				{name:"districtCode",index:"districtCode",width:250,resizable:true},
				{name:"districtName",index:"districtName",width:200,resizable:true}
				],
		caption: i18("INVCHECK_DETAIL_LIST"), //  "差异调整表",
		height:"auto",
		multiselect: false,
		sortname: "locationCode",
		sortorder: "asc",
		pager : "#pager1",
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	//	viewrecords: true,
   	//	rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});

	
	$j("#tbl_ex_detial").jqGrid({
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
		caption: i18("INVCHECK_DIFFERENCE_LIST"), // 差异调整表
		height:"auto",
		multiselect: false,
		sortname: "skuCode",
		sortorder: "asc",
		pager : "#pager1",
		//height:jumbo.getHeight(),
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#tbl_ex_detial2").jqGrid({
		datatype: "json",
		//colNames: ["ID","商品JMSKUCODE","商品条形码","商品名称","库位编码","盘点差异","状态"],
		colNames: ["ID",i18("SKU_CODE"),i18("SKUBARCODE"),i18("SKUNAME"),"库位编码",i18("INVCHECK_DIFFERENCE"),i18("INVCHECK_STATUS")],
		colModel: [
				{name: "id", index: "id", hidden: true},
				{name:"skuCode",index:"skuCode",width:150,resizable:true},
				{name:"barcode",index:"barcode",width:130,resizable:true},
				{name:"skuName",index:"skuName",width:200,resizable:true},
				{name:"locationCode",index:"locationCode",width:100,resizable:true},
				{name:"quantity",index:"quantity",width:70,resizable:true},
				{name:"invStatusName",index:"invStatusName",width:70,resizable:true}],
		caption: i18("INVCHECK_DIFFERENCE_LIST"), // "盘点差异列表",
		height:"auto",
		width:"auto",
		multiselect: false,
		sortname: "skuCode",
		sortorder: "asc",
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager2",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
	
		
		gridComplete: function(){
			var ids = $j("#tbl_list").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl_list").jqGrid('getRowData',ids[i]);
				var statusVal= "";
				if(datas["status"]=="CREATED"){
					statusVal= i18("STATUSNEW");
				}else if(datas["status"]=="OCCUPIED"){
					statusVal= i18("STATUSOCCUPIED");
				}else if(datas["status"]=="FINISHED"){
					statusVal= i18("STATUSFINISHED");
				}else if(datas["status"]=="CANCELED"){
					statusVal= i18("STATUSCANCELED");
				}else {
				}
				$j("#tbl-query-info").jqGrid('setRowData',ids[i],{"status":statusVal});
				$j("#tbl-query-info").jqGrid('setRowData',ids[i],{"intStaType":i18("STATYPE")});
			}
		}
	
	});
	
	$j("#query").click(function(){
		var postData = loxia._ajaxFormToObj("query_form");
		$j("#tbl_list").jqGrid('setGridParam',{url: window.$j("body").attr("contextpath") + "/findallvmiinventorychecklist.json",page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_list",{"intStatus":"inventoryCheckStatus"},
			$j("body").attr("contextpath") + "/findallvmiinventorychecklist.json",
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