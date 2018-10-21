$j.extend(loxia.regional['zh-CN'],{
	ENTITY_INMODE					:	"上架批次处理模式",
	ENTITY_EXCELFILE				:	"正确的excel导入文件",
	ENTITY_SKU_BARCODE				:	"条形码",
	ENTITY_SKU_JMCODE				:	"商品编码",
	ENTITY_SKU_KEYPROP				:	"扩展属性",
	ENTITY_SKU_NAME					:	"商品名称",
	ENTITY_SKU_SUP					:	"供应商名称",
	ENTITY_STALINE_TOTAL			:	"计划量执行量",
	ENTITY_STALINE_COMPLETE			:	"已执行量",
	ENTITY_SKU_SUPCODE				:	"货号"
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
	$j("#staFile").val("");
	var pl=$j("#tbl_sta_list").jqGrid("getRowData",id);
	$j("#sta_code").text(pl["code"]);
	$j("#sta_refSlipCode").text(pl["refSlipCode"]);
	$j("#sta_slipCode1").text(pl["slipCode1"]);
	$j("#sta_type").text(tr.find("td[aria-describedby$='intStaType']:first").text());
	$j("#sta_status").text(tr.find("td[aria-describedby$='intStaStatus']").text());
	$j("#sta_createTime").text(pl["createTime"]);
	$j("#sta_memo").text(pl["memo"]);
	$j("#stv_id").val(pl["stvId"]);
	if(pl["intStaType"] == "55"){
		$j("#inGI").addClass("hidden");
	} else {
		$j("#inGI").removeClass("hidden");
	}
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl_sta_line_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findInboundStvLine.json?sta.stvId=" + pl["stvId"]}).trigger("reloadGrid");
	
	$j("#divHead").addClass("hidden");
	$j("#divDetial").removeClass("hidden");
}

function queryStaList(){
	var url = $j("body").attr("contextpath") + "/findInboundShelvesSta.json";
	$j("#tbl_sta_list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_sta_list",{"intStaType":"whSTAType","intStaStatus":"whSTAStatus"},
		url,loxia._ajaxFormToObj("queryForm"));
}

function importReturn(){
	jumbo.showMsg("入库上架导入执行成功！");
	queryStaList();
	$j("#divDetial").addClass("hidden");
	$j("#divMergeDetial").addClass("hidden");
	$j("#divHead").removeClass("hidden");
}

$j(document).ready(function (){
	loxia.init({debug: true, region: 'zh-CN'});
	
	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	
	$j("#dialogPdaLog").dialog({title: "PDA操作日志", modal:true, autoOpen: false, width: 830});
	
	var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAType"});
	var staStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"whSTAStatus"});

	$j("#tbl_sta_list").jqGrid({
		url:baseUrl + "/findInboundShelvesSta.json",
		datatype: "json",
		colNames: ["ID","STVID","作业单号","相关单据号","辅助相关单据号","状态","作业类型","staType","店铺","CHANNELCODE","核对人","核对数量","创建时间","备注"],
		colModel: [
					{name: "id", index: "id", hidden: true},
					{name: "stvId", index: "stvId", hidden: true},
					{name: "code", index: "code",formatter:"linkFmatter", formatoptions:{onclick:"showdetail"}, width: 120, resizable: true,sortable:false},
					{name: "refSlipCode", index: "refSlipCode", width: 120, resizable: true},
					{name: "slipCode1", index: "slipCode1", width: 120, resizable: true},
					{name: "intStaStatus", index: "intStaStatus", width: 80, resizable: true,formatter:'select',editoptions:staStatus},
					{name: "intStaType", index: "intStaType", width: 120, resizable: true,formatter:'select',editoptions:staType},
					{name: "intStaType", index: "staType",hidden: true},
					{name: "channelName", index: "channelName", width: 120, resizable: true},
					{name: "owner", index: "owner", width: 120, resizable: true,hidden:true},
					{name: "affirmor", index: "affirmor", width: 120, resizable: true},
					{name: "receiptNumber", index: "receiptNumber", hidden: true},
					{name: "createTime", index: "createTime", hidden: true},
					{name: "memo", index: "memo", hidden: true}
	               ],
		caption: "已核对收货单列表",
		rowNum:10,
	   	sortname: 'id',
		pager:"#pager",
	    multiselect: false,
		viewrecords: true,
   		rownumbers:true,
		sortorder: "desc",
		width:800,
		height:"auto",
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_sta_line_list").jqGrid({
		datatype: "json",
		colNames: ["ID","商品条码","SKU商品编码-","商品编码-","扩展属性-","商品名称","货号","平台对接编码-","店铺-","CHANNELCODE","剩余上架数量-"],
		colModel: [
					{name: "id", index: "id", hidden: true,sortable:false},	
					{name: "barCode", index: "sku.BAR_CODE", width: 150, resizable: true,sortable:false},
					{name: "skuCode", index: "sku.code", width: 150, resizable: true,sortable:false},
					{name: "jmCode", index: "sku.JM_CODE", width: 150, resizable: true,sortable:false},
					{name: "keyProperties", index: "sku.KEY_PROPERTIES", width: 150, resizable: true,sortable:false},
					{name: "skuName", index: "sku.name", width: 150, resizable: true,sortable:false},
					{name: "supplierCode", index: "sku.SUPPLIER_CODE", width: 120, resizable: true,sortable:false},
					{name: "extCode1", index: "extCode1", width: 120, resizable: true,sortable:false},
					{name: "shopName", index: "shopName", width: 150, resizable: true,sortable:false},
					{name: "owner", index: "owner", width: 150, resizable: true,sortable:false,hidden:true},
					{name: "overplusAddedQty", index: "overplusAddedQty", width: 80, resizable: true,sortable:false}
	               ],
		caption: "确认上架明细",
		rowNum:10,
	   	sortname: 'id',
		pager:"#pager1",
	    multiselect: false,
		sortorder: "desc",
		width:800,
		height:"auto",
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#tbl_merge_list").jqGrid({
		datatype: "json",
		colNames: ["ID","STVID","作业单号","相关单据号","辅助相关单据号","状态","作业类型","staType","店铺","核对人","核对数量","创建时间","备注"],
		colModel: [
					{name: "id", index: "id", hidden: true},
					{name: "stvId", index: "stvId", hidden: true},
					{name: "code", index: "code", width: 120, resizable: true,sortable:false},
					{name: "refSlipCode", index: "refSlipCode", width: 120, resizable: true},
					{name: "slipCode1", index: "slipCode1", width: 120, resizable: true},
					{name: "intStaStatus", index: "intStaStatus", width: 80, resizable: true,formatter:'select',editoptions:staStatus},
					{name: "intStaType", index: "intStaType", width: 120, resizable: true,formatter:'select',editoptions:staType},
					{name: "intStaType", index: "staType", hidden: true},
					{name: "owner", index: "owner", width: 120, resizable: true},
					{name: "affirmor", index: "affirmor", width: 120, resizable: true},
					{name: "receiptNumber", index: "receiptNumber", hidden: true},
					{name: "createTime", index: "createTime", hidden: true},
					{name: "memo", index: "memo", hidden: true}
	               ],
		caption: "确认合并上架明细",//库存查询列表
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'code',
	    pager: '#pager2',
	    multiselect: false,
		sortorder: "desc", 
		height:jumbo.getHeight(),
		jsonReader: { repeatitems : false, id: "0" },
		viewrecords: true,
   		rownumbers:true,
		subGrid : true,
		subGridRowExpanded: function(subgrid_id, row_id) {
			var subgrid_table_id;
			subgrid_table_id = subgrid_id+"_t";
			var stvId = $j('#'+row_id+' td[aria-describedby="tbl_sta_list_stvId"]').text();
			$j("#"+subgrid_id).html("<table id='"+subgrid_table_id+"'></table>");
			$j("#"+subgrid_table_id).jqGrid({
				url: baseUrl + "/findInboundStvLine.json?sta.stvId="+stvId,
				datatype: "json",
				colNames: ["ID","商品条码","SKU商品编码","商品编码","商品名称","店铺","剩余上架数量"],
				colModel: [
					{name: "id", index: "id", hidden: true,sortable:false},	
					{name: "barCode", index: "sku.BAR_CODE", width: 120, resizable: true,sortable:false},
					{name: "skuCode", index: "sku.code", width: 120, resizable: true,sortable:false},
					{name: "jmCode", index: "sku.JM_CODE", width: 120, resizable: true,sortable:false},
					{name: "skuName", index: "sku.name", width: 150, resizable: true,sortable:false},
					{name: "owner", index: "owner", width: 150, resizable: true,sortable:false},
					{name: "overplusAddedQty", index: "overplusAddedQty", width: 80, resizable: true,sortable:false}
				],
				sortname: 'bar_Code',
				height: "100%",
				multiselect: false,
				sortorder: "desc",
				rowNum:-1,
				jsonReader: { repeatitems : false, id: "0" }
			})
		}
	});
	jumbo.bindTableExportBtn("tbl_merge_list");
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#search").click(function(){
		queryStaList();
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	
	$j("#back").click(function(){
		$j("#divDetial").addClass("hidden");
		$j("#divHead").removeClass("hidden");
	});
	
	$j("#showPdaLog").click(function(){
		$j("#dialogPdaLog").dialog("open");
	});
	
	$j("#expDiff").click(function(){
		$j("#upload").attr("src",$j("body").attr("contextpath") + "/warehouse/exportInboundShelves.do?sta.stvId=" + $j("#stv_id").val() + "&sta.code=" + $j("#sta_code").text());	
	});
	
	$j("#impDiff").click(function(){
		var file=$j.trim($j("#staFile").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importInboundShelves.do?sta.stvId="+$j("#stv_id").val()));
			loxia.submitForm("importForm");
		}
	});
	
	$j("#close").click(function(){
		$j("#dialogPdaLog").dialog("close");
	});
	
	$j("#GIinbound").click(function(){
		loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+ "/json/inboundShelvesGI.json",
				{'sta.stvId':$j("#stv_id").val()},
				{
				success:function(data){
					if(data){
						if(data.result=="success"){
							jumbo.showMsg("已上架到GI库区！");
							queryStaList();
							$j("#divDetial").addClass("hidden");
							$j("#divHead").removeClass("hidden");
						}else if(data.result=="error"){
							jumbo.showMsg(data.msg);
						}
					} else {
						jumbo.showMsg("数据操作异常");
					}
				},
				error:function(){jumbo.showMsg("数据操作异常");}//操作数据异常
		});
	});
	
	
	$j("#createMergeShelves").click(function(){
		var ids = $j("#tbl_sta_list").jqGrid('getGridParam','selarrrow');
		if(ids.length > 1){
			var postData = {};
			for(var i in ids){
				postData["stvList["+i+"]"] = ($j('#'+ids[i]+' td[aria-describedby="tbl_sta_list_stvId"]').text());
			}
			$j("#tbl_merge_list").jqGrid('setGridParam',{page:1,url:baseUrl + "/findMergeInboundStvLine.json",postData:postData}).trigger("reloadGrid");
			$j("#divHead").addClass("hidden");
			$j("#divMergeDetial").removeClass("hidden");
		}else if(ids.length == 1){
			jumbo.showMsg("合并上架至少须要两个收货单才能做合并!");
		} else {
			jumbo.showMsg("请勾选你须要合并上架的单据!");
		}
	});
	
	$j("#mergeBack").click(function(){
		$j("#divMergeDetial").addClass("hidden");
		$j("#divHead").removeClass("hidden");
	});
	
	$j("#mergeExpDiff").click(function(){
		var ids = $j("#tbl_merge_list").jqGrid('getDataIDs');
		var data = ''
		for(var i in ids){
			data += $j('#'+ids[i]+' td[aria-describedby="tbl_sta_list_stvId"]').text() + ",";
		}
		data += '-1';
		$j("#upload").append();
		$j("#upload").attr("src",$j("body").attr("contextpath") + "/warehouse/exportMergeInboundShelves.do?ids="+data);	
	});
	
	$j("#mergeImpDiff").click(function(){
		var file=$j.trim($j("#mergeFile").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			var ids = $j("#tbl_merge_list").jqGrid('getDataIDs');
			var data = ''
			for(var i in ids){
				data += $j('#'+ids[i]+' td[aria-describedby="tbl_sta_list_stvId"]').text() + ",";
			}
			$j("#importMergeForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importMergeInboundShelves.do?ids="+data));
			loxia.submitForm("importMergeForm");
		}
	});
});