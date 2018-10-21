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
	$j("#tbl_detail").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findVmiRtoLineList.json?cmd.id="+id)}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl_detail",{"intStatus":"status"},
			baseUrl + "/findVmiRtoLineList.json",
			{"cmd.id":id});
	
	var statusMap = loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"vmiOrderStatus"});
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
	$j("#orderCode").text(data['orderCode']);
	$j("#createTime").text(data['createTime']);
	$j("#status").text(map[data['intStatus']]);
	$j("#staCode").text(data['staCode']);
	$j("#divRtoList").addClass("hidden");
	$j("#divDetailList").removeClass("hidden");
}

$j(document).ready(function (){
	//jumbo.loadShopList("companyshop","id");
	//initShopQuery("companyshop","innerShopCode");
	var result = loxia.syncXhrPost(loxia.getTimeUrl(baseUrl + "/findShopInfoByDefaultOuId.json"));
	for(var idx in result){
		$j('<option value="' + result[idx].id + '">' + result[idx].name +'</option>').appendTo($j("#companyshop"));
	}
	var baseUrl = $j("body").attr("contextpath");
	//var lType=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckDiffSnLineStatus"});
	var status = loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"vmiOrderStatus"});

	$j("#tbl_list").jqGrid({
			url: baseUrl + "/findVmiRtoList.json",
			datatype: "json",
			colNames: ["ID","订单号",i18("CREATETIME"),i18("STATUS"),"作业单号"],
			colModel: [
			           {name: "id", index: "id", hidden: true},		         
			            {name:"orderCode",index:"orderCode",width:150,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable: true},
						{name:"createTime",index:"createTime",width:150,resizable:true},
						{name:"intStatus",index:"status",width:120,resizable:true,formatter:'select',editoptions:status},
						{name:"staCode",index:"staCode",width:150,resizable:true}
					],
			caption: 'VMI退仓接收指令', // "VMI库存调整",
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
	//jumbo.bindTableExportBtn("tbl_list",{"intStatus":"inventoryCheckStatus"});

	$j("#tbl_detail").jqGrid({
		datatype: "json",
		//colNames: ["ID","商品编码","商品名称","商品数量"],
		colNames: ["ID",i18("SKU_CODE"),i18("SKUBARCODE"),i18("SKUNAME"),"品牌对接码","行号",i18("SKU_QUANTITY")],
		colModel: [
				{name: "id", index: "id", hidden: true},	
				{name:"skuCode",index:"skuCode",width:100,resizable:true},
				{name:"skuBarcode",index:"skuBarcode",width:150,resizable:true},
				{name:"skuName",index:"skuName",width:150,resizable:true},
				{name:"upc",index:"upc",width:150,resizable:true},
				{name:"lineNo",index:"lineNo",width:150,resizable:true},
				{name:"qty",index:"qty",width:100,resizable:true}
				],
		caption: "指令明细列表", // 指令明细列表
		height:"auto",
		multiselect: false,
		sortname: "skuCode",
		sortorder: "asc",
		pager : "#pagerDetail",
		//height:jumbo.getHeight(),
		rowNum: -1,
		//rowList:jumbo.getPageSizeList(),
		viewrecords: true,
		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#query").click(function(){
		var postData = loxia._ajaxFormToObj("query_form");
		var cId = $j("#companyshop").val();
		postData["cmd.channelId"] = cId;
		$j("#tbl_list").jqGrid('setGridParam',{url: window.$j("body").attr("contextpath") + "/findVmiRtoList.json",page:1,postData:postData}).trigger("reloadGrid");
//		jumbo.bindTableExportBtn("tbl_list",{"intStatus":"inventoryCheckStatus"},
//			$j("body").attr("contextpath") + "/findallvmiinventorychecklist.json",
//			postData);
	});
	
	$j("#reset").click(function(){
		$j("#query_form input,select").val("");
	});
	
	$j("#back").click(function(){
		$j("#divRtoList").removeClass("hidden");
		$j("#divDetailList").addClass("hidden");
	});
	
	
});