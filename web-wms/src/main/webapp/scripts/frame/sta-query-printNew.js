$j.extend(loxia.regional['zh-CN'],{
	STA_LIST : "作业单列表",
	STA_LINE_LIST : "作业单明细",
	STA_CODE : "作业单号",
	STA_SLIP_CODE : "相关单据号",
	STA_TYPE : "作业类型名称",
	STA_STATUS : "作业单状态",
	OWNER : "平台店铺名称",
	LPCODE: "物流服务商",
	TRACKING_NO : "快递单号",
	CRAETE_TIME : "创建时间",
	FINISH_TIME : "完成时间",
	OPERATOR : "操作人",
	PLAN_COUNT : "计划执行总数量",
	
	SKUCODE : "SKU编码",
	BARCODE : "条形码",
	JMCODE : "商品编码",
	SKU_NAME : "商品名称",
	KEY_PROPS : "扩展属性",
	PLAN_QTY : "计划量",
	COMFIRMED_QTY : "执行量",
	SKU_COST : "库存成本",
	TRACKING_AND_SKU : "快递单号及耗材",
	PRODUCT_SIZE : "商品大小",
	
	DIRECTION:"作用方向",
	TYPE_NAME:"作业类型",
	LOCATION_CODE:"库位编码",
	INV_STATUS:"库存状态",
	CREATER:"创建人",
	
	DETAIL_QUERY:"操作明细查询",
	
	OUT_WAREHOUSE:"出库",
	IN_WAREHOUSE:"入库",
	SN:"SN号",
	DIRECT:"出入库方向",
	SN_DETAIL:"SN号明细",
	DELIVERYINFO:"物流面单打印中，请等待..."
		
		
		
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

var isEmsOlOrder = false;
$j(document).ready(function (){
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getTransactionTypeName.do");
	for(var idx in result){
		$j("<option value='" + result[idx].code + "'>"+ result[idx].name +"</option>").appendTo($j("#select"));
	}
	
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-orderList").jqGrid({
		//url:baseUrl + "/gethistoricalOrderList.json",
		datatype: "json",
		colNames: ["id","物流商","快递单号","操作"],
		colModel: [         {name: "id", index: "id", hidden: true},
		                    {name:"lpCode", index:"lpCode", width:200, resizable:true},
							{name:"trackingNo", index: "trackingNo", width:200},
							{name:"idForBtn",index:"idForBtn", width: 250,resizable:true,sortable: false, formatter:"buttonFmatter", formatoptions:{"buttons":{label:"打印物流面单", onclick:"printDeliveryInfo(this)"}}}
					 	],
		caption: "数据列表",
	    multiselect: true,
	    sortname: 'ID',
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	pager:"#pager",	   
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
		// 打印装箱清单：
	
		
	$j("#reset").click(function(){
		$j("#queryForm input,#queryForm select").val("");
	});
	 
	
	$j("#search").click(function(){
		var url = baseUrl + "/findTrackingNo.json";
		$j("#tbl-orderList").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("queryForm")}).trigger("reloadGrid");
	
	});
	
	$j("#printAll").click(function(){
		var ids = $j("#tbl-orderList").jqGrid('getGridParam','selarrrow');
		if(null != ids && ids.length > 0&&ids.length<=5){
			var ids = ids.join(',');
		    loxia.lockPage();
		    jumbo.showMsg(i18("DELIVERYINFO"));
		    var url = $j("body").attr("contextpath") + "/printSingleOrderDetail1.json?id=" +ids;
		    printBZ(loxia.encodeUrl(url),false);
		    loxia.unlockPage();
		}else if(null != ids && ids.length > 0){
			jumbo.showMsg("打印的过多");
		}else{
			jumbo.showMsg("请勾选需要打印的数据");
		}
	});
	
});



function printDeliveryInfo(obj){
	var ids = $j(obj).parent().siblings().eq(2).text();
	if (null != ids && ids.length > 0) {
		    loxia.lockPage();
		    jumbo.showMsg(i18("DELIVERYINFO"));
			var ids = ids+",";
			var url = $j("body").attr("contextpath") + "/printSingleOrderDetail1.json?id=" +ids;
			printBZ(loxia.encodeUrl(url),true);
			loxia.unlockPage();
	}

};