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
	
	STATUS_CREATE:"已创建",
	STATUS_OCCUPIED:"配货中",
	STATUS_CHECKED:"已核对",
	STATUS_INTRANSIT:"已转出",
	STATUS_FINISHED:"已完成"
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


$j(document).ready(function (){
	loxia.init({debug: true, region: 'zh-CN'});
	
	jumbo.loadShopList("companyshop");
	
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
});
