var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	 
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

//显示明细
function showDetail(obj){
	var baseUrl = $j("body").attr("contextpath");
	$j("#showList").addClass("hidden");
	$j("#showDetail").removeClass("hidden");
	 
	$j("#tabs").tabs();
	
	var tr = $j(obj).parents("tr[id]");
	var id=tr.attr("id");
	$j("#iCheck").val(id);
	
	var data = $j("#tbl_query").jqGrid("getRowData",id);
	
	$j("#code").text(data["code"]);
	$j("#createTime").text(data["createTime"]);
	$j("#finishTime").text(data["finishTime"]);
	$j("#user").text(data["creatorName"]);
	//$j("#status").text(data["intStatus"]);
	$j("#shop").text(data["owner"]);
	if (data["intStatus"] == 1){
		$j("#status").text("新建");
	} else if (data["intStatus"] == 5){
		$j("#status").text("差异未处理");
	} else if (data["intStatus"] == 10){
		$j("#status").text("完成");
	} else if (data["intStatus"] == 0){
		$j("#status").text("取消");
	}
	if (data["type"] == 'SKU_SPLIT'){
		$j("#invChecktype").text("拆分");
	}else if (data["type"] == 'SKU_MERGER'){
		$j("#invChecktype").text("组合");
	}
	
	var _status = data["intStatus"]; 
	/*if (_status == 10){
		$j("#exec").addClass("hidden");
	} else {
		$j("#exec").removeClass("hidden");
	}*/
	if (_status != 10 && _status != 0){
		$j("#exec").removeClass("hidden");
	}
	if (_status == 10 || _status == 0){
		$j("#exec, #cancel").addClass("hidden");
	} else {
		$j("#cancel").removeClass("hidden");
	}
	// tab1
	$j("#tbl_line_query tr:gt(0)").remove();
	$j("#tbl_line_query").jqGrid('setGridParam',{page:1,url:baseUrl + "/findskucollectinfo.json?id=" + id}).trigger("reloadGrid");
	
	jumbo.bindTableExportBtn("tbl_line_query",{},
			baseUrl + "/findskucollectinfo.json",{"id":id});
	// tab2
	$j("#tbl_line_detial_query tr:gt(0)").remove();
	$j("#tbl_line_detial_query").jqGrid('setGridParam',{page:1,url:baseUrl + "/findskuadjustdetailinfo.json?id=" + id}).trigger("reloadGrid");
	
	jumbo.bindTableExportBtn("tbl_line_detial_query",{},
			baseUrl + "/findskuadjustdetailinfo.json",{"id":id});
}


//打印装箱明细
$j(document).ready(function(){
	loxia.init({debug: true, region: 'zh-CN'});

	$j("#tabs").tabs();
	
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	
	var baseUrl = $j("body").attr("contextpath");
	
	var intStatus=loxia.syncXhr($j("body").attr("contextpath") + "/json/formateroptions.do",{"categoryCode":"inventoryCheckStatus"});
	
	
	$j("#tbl_query").jqGrid({
		url:baseUrl + "/getskugroupichecklist.json",
		datatype: "json",
		colNames: ["ID","单据号","店铺","状态","创建人","创建时间","完成时间","作业类型"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"code", index:"code", width:100,formatter:"linkFmatter",formatoptions:{onclick:"showDetail"}, resizable:true},
					{name:"owner",index:"owner",width:100,resizable:true},
					{name:"intStatus", index:"intStatus" ,width:80,resizable:true ,formatter:'select',editoptions:intStatus},
					//{name:"intStatus", index:"intStatus" ,width:80,resizable:true },
					{name:"creatorName",index:"creatorName",width:100,resizable:true},
					{name:"createTime",index:"createTime",width:130,resizable:true},
					{name:"finishTime",index:"finishTime",width:130,resizable:true},
					{name:"type",index:"type",width:10,resizable:true,hidden:true},
				  ],
		caption: "作业单列表",
	   	sortname: 't.id',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#tbl_query_page",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl_query",{"intStatus":"inventoryCheckStatus"},baseUrl + "/getskugroupichecklist.json");


	// tab1
	$j("#tbl_line_query").jqGrid({
		// url: baseUrl + "findskuadjustdetailinfo.json",
		datatype: "json",
		colNames: ["ID","商品Sku编码","条码","货号","库存状态","成本","数量"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"skuCode", index:"skuCode" ,width:120,resizable:true},
					{name: "barCode", index:"barCode",width:120,resizable:true},
					{name:"supplierCode", index:"supplierCode", width:120, resizable:true},
					{name:"intInvstatusName", index:"intInvstatusName", width:120, resizable:true},
					{name:"skuCost",index:"skuCost",width:85,resizable:true},
					{name:"quantity",index:"quantity",width:80,resizable:true}
					],
		caption: "商品调整汇总",
	    height:"auto",
	    rowNum: jumbo.getPageSize(),
	    rowList:jumbo.getPageSizeList(),
	    pager:"#tbl_line_query_page",
	   	sortname: 'ID',
	    multiselect: false,
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl_line_query",{},
			baseUrl + "/findskuadjustdetailinfo.json",{"id":$j("#iCheck").val()});
	
	 // tab2
	 $j("#tbl_line_detial_query").jqGrid({
			//url: baseUrl + "findskucollectinfo.json",
			datatype: "json",
			colNames: ["ID","商品Sku编码","条码","货号","库位","库存状态","成本","数量"],
			colModel: [{name: "id", index: "id", hidden: true},
						{name:"skuCode", index:"skuCode" ,width:120,resizable:true},
						{name: "barCode", index:"barCode",width:120,resizable:true},
						{name:"supplierCode", index:"supplierCode", width:120, resizable:true},
						{name:"locationCode", index:"locationCode", width:120, resizable:true},
						{name:"intInvstatusName", index:"intInvstatusName", width:120, resizable:true},
						{name:"skuCost",index:"skuCost",width:85,resizable:true},
						{name:"quantity",index:"quantity",width:80,resizable:true}
						],
			caption: "商品调整明细",
		    height:"auto",
		    rowNum: jumbo.getPageSize(),
		    rowList:jumbo.getPageSizeList(),
		    pager:"#tbl_line_detial_query_page",
		   	sortname: 'ID',
		    multiselect: false,
			sortorder: "desc",
			jsonReader: { repeatitems : false, id: "0" }
		});
	 jumbo.bindTableExportBtn("tbl_line_detial_query",{},
				baseUrl + "/findskucollectinfo.json",{"id":$j("#iCheck").val()});
		
	  
		//查询
		$j("#search").click(function(){
			var url = baseUrl + "/getskugroupichecklist.json";
			$j("#tbl_query").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("formQuery")}).trigger("reloadGrid");
			jumbo.bindTableExportBtn("tbl_query",{"intStatus":"whSTAStatus"},
				url,loxia._ajaxFormToObj("formQuery"));
		});
		//查询重置
		$j("#reset").click(function(){
			$j("#formQuery input,select").val("");
		});
		// 返回
		$j("#back").click(function(){
			$j("#showList").removeClass("hidden");
			$j("#showDetail").addClass("hidden");
		});
	//执行
	$j("#exec").click(function(){
		var id = $j("#iCheck").val();
		if (id != null){
			var rs = loxia.syncXhrPost( baseUrl + "/skugroupexecution.json",{"id":id});
			if(rs && rs["result"] == 'success'){
				jumbo.showMsg("操作成功");
			}else if(rs && rs["result"] == 'error'){
				jumbo.showMsg("操作失败<br/>" + rs["message"]);
			}
		}
	});
	
	//取消
	$j("#cancel").click(function(){
		var id = $j("#iCheck").val();
		if (id != null){
			var rs = loxia.syncXhrPost( baseUrl + "/skugroupcancel.json",{"id":id});
			if(rs && rs["result"] == 'success'){
				/*$j("#exec, #cancel").removeClass("confirm");
				loxia.initContext($j("#exec"));
				loxia.initContext($j("#cancel"));*/
				jumbo.showMsg("操作成功");
			}else if(rs && rs["result"] == 'error'){
				jumbo.showMsg("操作失败<br/>" + rs["message"]);
			}
		}
	});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
});
 