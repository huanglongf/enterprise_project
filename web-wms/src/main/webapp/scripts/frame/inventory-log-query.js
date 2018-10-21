$j.extend(loxia.regional['zh-CN'],{
	TRANSACTIONTIME : "操作时间戳",
	STACODE : "作业单号",
	LOCATIONCODE : "库位编码",
	TRANSATIONTYPENAME : "作业类型名称",
	INQTY : "入库数量",
	OUTQTY : "出库数量",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	SKUNAME : "商品名称",
	KEYPROPERTIES : "扩展属性",
	INVSTATUS : "库存状态",
	OWNER : "店铺",
	INVENTORY_CHECK_CODE : "盘点批编码",
	OPERATOR : "操作员",
	INVENTORY_LOG_LIST : "库存操作日志列表",
	SKUCODE: "SKU编码",
	PRODUCTSIZE: "商品大小"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}
$j(document).ready(function (){
	initShopQuery("companyshop","innerShopCode");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	jumbo.loadShopList("companyshop",null,false);
	
	var transtype = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findtransactiontypelist.json");
	for(var idx in transtype){
		$j("<option value='" + transtype[idx].id + "'>"+ transtype[idx].name +"</option>").appendTo($j("#transType"));
	}
	//库存商品状态
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findInventoryStatus.do");
	for(var idx in result){
		$j("<option value='" + result[idx].statusId + "'>"+ result[idx].statusName +"</option>").appendTo($j("#inventoryStatusId"));
	}
	$j("#reset").click(function(){
		$j("#query_form input,#query_form select").val("");
	});
	$j("#query").click(function(){
		if($j("input[name='stock.stockStartTime1']").val().replace(/(^\s*)|(\s*$)/g, "") ==""){
			jumbo.showMsg("作业单起始日期不能为空！");
			return;
		}
		if($j("input[name='stock.stockEndTime1']").val().replace(/(^\s*)|(\s*$)/g, "") ==""){
			jumbo.showMsg("作业单截至日期 不能为空！");
			return;
		}
		var startTime = new Date(Date.parse(($j("input[name='stock.stockStartTime1']").val()).replace(/-/g, "/")));
		var endTime = new Date(Date.parse(($j("input[name='stock.stockEndTime1']").val()).replace(/-/g, "/")));
		var days = ((endTime.getTime() - startTime.getTime()) / (1000 * 60 * 60 * 24));
		if(186 <= days){
			jumbo.showMsg("库存日志查询只允许查询6个月内数据");
			return;
		}
		var postData = loxia._ajaxFormToObj("query_form");
		$j("#tbl-inv-log-list").jqGrid('setGridParam',{url: window.$j("body").attr("contextpath") + "/json/inventorylogdetail.json",page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-inv-log-list",{},loxia.getTimeUrl(window.$j("body").attr("contextpath") + "/json/inventorylogdetail.json"),postData);
	});
	$j("#tbl-inv-log-list").jqGrid({
		//url: window.$j("body").attr("contextpath") + "/json/inventorylogdetail.json",
		datatype: "json",
		//colNames: ["ID","操作时间戳","作业单号","作业类型名称","库位编码","入库数量","出库数量","条形码","JM商品编码","商品名称","扩展属性","库存状态","店铺","操作员"],
		colNames: ["ID",i18("TRANSACTIONTIME"),i18("OWNER"),i18("STACODE"),"相关单号",i18("INVENTORY_CHECK_CODE"),i18("TRANSATIONTYPENAME"),i18("LOCATIONCODE"),i18("INQTY"),
		           i18("OUTQTY"),i18("BARCODE"),i18("JMCODE"),i18("SKUCODE"),i18("KEYPROPERTIES"),"货号",i18("SKUNAME"),i18("PRODUCTSIZE"),i18("INVSTATUS"),i18("OPERATOR"),"商品效期"],
		colModel: [{name: "id", index: "id", hidden: true},
					{name:"transactionTime", index:"transactionTime",width:100,resizable:true},
					{name:"owner",index:"owner",width:100,resizable:true},
					{name:"staCode", index:"staCode",width:100,resizable:true},
					{name:"refSlipCode", index:"refSlipCode",width:80,resizable:true},
					{name:"inventoryCheckCode", index:"inventoryCheckCode",width:80,resizable:true},
					{name:"transactionTypeName", index:"transactionTypeName",width:60,resizable:true},
					{name:"locationCode", index:"locationCode",width:80,resizable:true},
					{name:"inQty", index:"inQty",width:60,resizable:true},
					{name:"outQty", index:"outQty",width:60,resizable:true},
					{name:"barCode", index:"barCode",width:80,resizable:true},
					{name:"jmCode", index:"jmCode",width:60,resizable:true},
					{name:"skuCode", index:"skuCode",width:100,resizable:true},
					{name:"keyProperties", index:"keyProperties",width:60,resizable:true},
					{name:"supplierCode", index:"supplierCode",width:60,resizable:true},
					{name:"skuName", index:"skuName",width:100,resizable:true},
					{name:"productSize",index:"productSize",width:60,resizable:true},
					{name:"invStatus", index:"invStatus",width:60,resizable:true},
					{name:"operator",index:"operator",width:60,resizable:true},
					{name:"expireDate",index:"expireDate",width:60,resizable:true}
					],
		caption: i18("INVENTORY_LOG_LIST"),//库存操作日志列表
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'transactionTime,jmCode',
	   	height:jumbo.getHeight(),
		pager: '#pager',
	    multiselect: false,
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" },
		cmTemplate: {sortable:false}
	});
	jumbo.bindTableExportBtn("tbl-inv-log-list");
}); 
