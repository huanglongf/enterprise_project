$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE				:	"请选择正确的Excel导入文件",
	ENTITY_INMODE					:	"上架批次处理模式",
	ENTITY_EXCELFILE				:	"正确的excel导入文件",
	ENTITY_STA_CREATE_TIME			:	"作业创建时间",
	ENTITY_STA_ARRIVE_TIME			:	"预计到货日期",
	ENTITY_STA_CODE					:	"作业单号",
	ENTITY_STA_PROCESS				:	"执行情况",
	ENTITY_STA_UPDATE_TIME			:	"上次执行时间",
	ENTITY_STA_PO					:	"PO单号",
	ENTITY_STA_OWNER				:	"店铺名称",
	ENTITY_STA_SUP					:	"供应商名称",
	ENTITY_STA_SEND_MODE			:	"送货方式",
	ENTITY_STA_REMANENT				:	"剩余计划入库量（件）",
	ENTITY_STA_COMMENT				:	"PO单备注",
	ENTITY_SKU_BARCODE				:	"条形码",
	ENTITY_SKU_JMCODE				:	"商品编码",
	ENTITY_SKU_KEYPROP				:	"扩展属性",
	ENTITY_SKU_NAME					:	"商品名称",
	ENTITY_SKU_SUPCODE				:	"货号",
	ENTITY_STALINE_TOTAL			:	"计划量执行量",
	ENTITY_STALINE_COMPLETE			:	"已执行量",
	ENTITY_STALINE_CURRENT			:	"本次执行量",
	ENTITY_LOCATION					:	"库位",
	ENTITY_LOCATION_CAPACITY		:	"库容",
	ENTITY_LOCATION_CURRENT			:	"当前库存数",
	ENTITY_LOCATION_ADD				:	"上架数",			
	ENTITY_STALINE_SNS				:	"SN序列号",
	SKUCODE :"商品编码",
	BARCODE:"商品条码",
	JMCODE:"商品货号",
	SKU_NAME:"商品名称",
	KEY_PROPS:"商品关键属性",
	SN:"商品SN",
	DIRECT:"事务方向"
});
var extBarcode = null;
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
function showMsg(msg){
	jumbo.showMsg(msg);
}
var $j = jQuery.noConflict();
function showdetail(obj){
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	$j("#sta_id").val(id);
	var pl=$j("#tbl_sta_list").jqGrid("getRowData",id);
	$j("#sta_code").text(pl["code"]);
	$j("#sta_refSlipCode").text(pl["refSlipCode"]);
	$j("#sta_type").text(tr.find("td[aria-describedby$='intStaType']").text());
	$j("#sta_status").text(tr.find("td[aria-describedby$='intStaStatus']").text());
	$j("#sta_slipCode1").text(pl["slipCode1"]);
	$j("#sta_createTime").text(pl["createTime"]);
	
	if(pl["intStaType"] == 11){
		$j("#exportPoReport").removeClass("hidden");
		$j("#printPoReport").removeClass("hidden");
	}else{
		$j("#exportPoReport").addClass("hidden");
		$j("#printPoReport").addClass("hidden");
	}
	$j("#sta_memo").text(pl["memo"]);
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_inbound_line_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findInBoundLine.json?sta.id=" + id}).trigger("reloadGrid");
	$j("#tbl_inbound_sn").jqGrid('setGridParam',{page:1,url:baseUrl + "/findInBoundSN.json?sta.id=" + id}).trigger("reloadGrid");
	
	$j("#divHead").addClass("hidden");
	$j("#divDetial").removeClass("hidden");
}
var staType;
var staStatus;
$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	loxia.init({debug: true, region: 'zh-CN'});
	$j("#detail_tabs").tabs();
	
	staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});
	$j("#tbl_sta_list").jqGrid({
		url:baseUrl+"/findInBoundStaQuery.json",
		datatype: "json",
		colNames: ["ID","作业单号","相关单据号","辅助相关单据号","状态","作业类型","店铺","CHANNELCODE","创建时间","完成时间","备注"],
		colModel: [
		           {name: "id", index: "id", hidden: true},	
		           {name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 110, resizable: true,sortable:false},
		           {name: "refSlipCode", index: "refSlipCode", width: 100, resizable: true},
		           {name: "slipCode1", index: "slipCode1", width: 100, resizable: true},
		           {name: "intStaStatus", index: "intStaStatus", width: 80, resizable: true,formatter:'select',editoptions:staStatus},
	               {name: "intStaType", index: "intStaType", width: 120, resizable: true,formatter:'select',editoptions:staType},
	               {name: "channelName", index: "channelName", width: 120, resizable: true},
	               {name: "owner", index: "owner", width: 120, resizable: true,hidden:true},
	               {name: "createTime", index: "createTime", width: 150, resizable: true},
	               {name: "finishTime", index: "finishTime", width: 150, resizable: true},
	               {name: "memo", index: "memo", width: 150, resizable: true}
	               ],
		caption: "入库作业单",
	   	sortname: 'sta.id',
		pager:"#pager",
	    multiselect: false,
		sortorder: "desc",
		height:"auto",
		rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			
		}
	});

	//商品明细
	$j("#tbl_inbound_line_list").jqGrid({
		datatype: "json",
		colNames: ["ID","STAID","SKUID","OWNER","skuCost",i18("ENTITY_SKU_BARCODE"),i18("ENTITY_SKU_JMCODE"),i18("ENTITY_SKU_KEYPROP"),
			i18("ENTITY_SKU_NAME"),i18("ENTITY_STA_SUP"),"店铺","库存状态",i18("ENTITY_STALINE_TOTAL"),i18("ENTITY_STALINE_COMPLETE"),"本次计划确认量"],
		colModel: [
		           {name: "id", index: "id", hidden: true,sortable:false},	
		           {name: "sta.id", index: "sta.id", hidden: true,sortable:false},	
		           {name: "skuId", index: "sku.id", hidden: true,sortable:false},	
		           {name: "owner", index: "owner", hidden: true,sortable:false},	
		           {name: "skuCost", index: "skuCost", hidden: true,sortable:false},	
		           {name: "barCode", index: "sku.BAR_CODE", width: 100, resizable: true,sortable:false},
		           {name: "jmcode", index: "sku.JM_CODE", width: 100, resizable: true,sortable:false},
		           {name: "keyProperties", index: "sku.KEY_PROPERTIES", width: 120, resizable: true,sortable:false},
		           {name: "skuName", index: "sku.name", width: 120, resizable: true,sortable:false},
		           {name: "jmskuCode", index: "sku.SUPPLIER_CODE", width: 120, resizable: true,sortable:false},
		           {name: "channelName", index: "channelName", width: 120, resizable: true,sortable:false},
		           {name: "intInvstatusName", index: "intInvstatusName", width: 80, resizable: true,sortable:false},
		           {name: "quantity", index: "quantity", width: 100, resizable: true,sortable:false},
		           {name: "completeQuantity", index: "completeQuantity", width: 100, resizable: true,sortable:false},
		           {name: "receiptQty", index: "receiptQty", width: 100, resizable: true,sortable:false}
	               ],
		caption: "入库明细单",
	   	sortname: 'sku.bar_Code',
	    multiselect: false,
		sortorder: "desc", 
		height:"auto",
		pager:"#pager1",
		viewrecords: true,
   		rownumbers:true,
		rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_inbound_sn").jqGrid({
		datatype: "json",
		colNames: ["ID",i18("SKUCODE"),i18("BARCODE"),i18("JMCODE"),i18("SKU_NAME"),i18("KEY_PROPS"),i18("SN"),i18("DIRECT")],
		colModel: [{name: "id", index: "id", hidden: true},
		           	{name:"skuCode",index:"skuCode",width:120,resizable:true},
					{name:"barCode",index:"barCode",width:120,resizable:true},
					{name:"jmcode", index:"jmcode" ,width:120,resizable:true},
					{name:"skuName", index:"skuName",width:120,resizable:true},
					{name:"keyProperties",index:"keyProperties",width:80,resizable:true},
					{name:"sn", index:"sn",width:150,resizable:true},
					{name:"direction", index:"direction",width:120,resizable:true}],
		caption: "SN入库记录",
	   	sortname: 'sn',
	    multiselect: false,
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#pagerSn",
		sortorder: "asc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		gridComplete: function(){
			var ids = $j("#tbl-sn-detail").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var datas = $j("#tbl-sn-detail").jqGrid('getRowData',ids[i]);
				var tra = "";
				if(datas["direction"]=="2"){
					tra= "出库";
				} else if(datas["direction"]=="1") {
					tra = "入库";
				}
				$j("#tbl-sn-detail").jqGrid('setRowData',ids[i],{"direction":tra});
			}
		}
	});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#search").click(function(){
		var url = $j("body").attr("contextpath") + "/findInBoundStaQuery.json";
		$j("#tbl_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl_sta_list",{"intStaType":"whSTAType","intStaStatus":"whSTAStatus"},
			url,loxia._ajaxFormToObj("queryForm"));
	});
	
	$j("#exportPoReport").click(function(){
		$j("#upload").attr("src",$j("body").attr("contextpath") + "/warehouse/exportPoConfirmReport.do?sta.id="+$j("#sta_id").val());
	});
	
	$j("#printPoReport").click(function(){
		loxia.lockPage();
		var url = $j("body").attr("contextpath") + "/printPoConfirmReport.json?sta.id="+$j("#sta_id").val();
		printBZ(loxia.encodeUrl(url),true);				
		loxia.unlockPage();
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	$j("#back").click(function(){
		$j("#divDetial").addClass("hidden");
		$j("#divHead").removeClass("hidden");
	});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
});