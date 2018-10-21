$j.extend(loxia.regional['zh-CN'],{
	INPUT_EMPTY : "输入不能为空",
	
	WAREHOUSENAME : "仓库",
	JMCODE : "商品编码",
	KEYPROPERTIES : "扩展属性",
	BARCODE : "条形码",
	BRANDNAME : "品牌",
	SKUNAME : "商品名称",
	INVOWNER : "所属店铺",
	SUPPLIERSKUCODE : "货号",
	QUANTITY : "历史库存",
	HISTORICAL_INVENTORY_QUERY : "库存快照查询列表",
	ERROR_MASSAGE : "提交数据时发现错误：共发现 {0} 处域填写问题[请点击边框为红色的文本框查看出错详细信息]",
	SKUCODE: "SKU编码"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function checkDate(obj){
	if(obj == ""){
		//loxia.tip($j("#startDate"),"输入不能为空");
		return i18("INPUT_EMPTY");//输入不能为空
	}
	return loxia.SUCCESS;
}

$j(document).ready(function (){
	initShopQuery("companyshop","innerShopCode");
	

	
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	
	jumbo.loadShopList("companyshop");
	
	$j("#companyshop").flexselect();
	$j("#companyshop").val("");
	
	
	var baseUrl = $j("body").attr("contextpath");
	var result = loxia.syncXhrPost(baseUrl+"/getWarehouseDatabyOrganise.json");
	for(var idx in result){
		$j("<option value='" + result[idx].value + "'>"+ result[idx].name +"</option>").appendTo($j("#selTrans"));
	}
	$j("#tbl-inventory-list").jqGrid({
		datatype: "json",
		//colNames: ["ID","仓库","商品编码","扩展属性","条形码","品牌","商品名称","所属店铺","供应商编码","历史库存"],
		colNames: ["ID",i18("WAREHOUSENAME"),i18("SKUCODE"),i18("JMCODE"),i18("KEYPROPERTIES"),i18("BARCODE"),i18("SUPPLIERSKUCODE"),i18("SKUNAME"),i18("INVOWNER"),i18("BRANDNAME"),i18("QUANTITY")],
		colModel: [{name: "skuId", index: "skuId", hidden: true},
		            {name:"warehouseName", index:"warehouseName", width:80, resizable:true},
		            {name:"skuCode",index:"skuCode",width:80,resizable:true},
					{name:"jmCode",index:"jmCode,skuId,invOwner",width:80,resizable:true},
					{name:"keyProperties",index:"keyProperties",width:60,resizable:true},
					{name:"barCode", index:"barCode", width:80, resizable:true},
					{name:"supplierSkuCode",index:"supplierSkuCode",width:80,resizable:true},
					{name:"skuName",index:"skuName",width:80,resizable:true},
					{name:"invOwner", index:"invOwner" ,width:100,resizable:true,sortable:false},
					{name:"brandName", index:"brandName", width:60, resizable:true},
					{name:"quantity", index:"quantity",width:60,resizable:true}],
		caption: i18("HISTORICAL_INVENTORY_QUERY"),//历史库存查询
	   	sortname: 'jmCode,invOwner',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:"auto",
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	$j("#search").click(function(){
		var errors=loxia.validateForm("modifyForm");
		var obj = $j("#startDate").val();
		
		var i = 0;
		if(obj == ""){
			loxia.tip($j("#startDate"),i18("INPUT_EMPTY"));//输入不能为空
			$j("#startDate").addClass("ui-loxia-error");
			i++;
		}else{
			$j("#startDate").removeClass("ui-loxia-error");
		}
		
		if(errors.length>0){
			jumbo.showMsg(errors);
			return false;
		}else if(i>0){
			jumbo.showMsg(i18("ERROR_MASSAGE",i));//提交数据时发现错误：共发现 "+i+" 处域填写问题[请点击边框为红色的文本框查看出错详细信息]
			return false;
		}
		
		var postData = loxia._ajaxFormToObj("form_query");
		if($j("#showZero").attr("checked")){
			postData["inventory.isShowZero"] = true;
		}else{
			postData["inventory.isShowZero"] = false;
		}
		$j("#tbl-inventory-list").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/historicalInventoryQurey.json"),postData:postData,page:1}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-inventory-list",{},loxia.getTimeUrl(baseUrl + "/historicalInventoryQurey.json"),postData);	
	});
	
	//加载店铺下拉列表
	function loadShop(){
		var startWarehouse = $j("#selTrans").val();
		if(startWarehouse != null){
			var postData = {};			
//			postData = getTableDate();
			postData["blmcmd.startWarehouseId"] = startWarehouse;
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/findallshoplists.json",postData);
			for(var idx in rs){
				$j("<option value='" + rs[idx].id + "'>"+ rs[idx].name +"</option>").appendTo($j("#companyshop"));
			}
		}
	}
	$j("#companyshop").click(function(){
		var startWarehouse = $j("#selTrans").val();
		if(startWarehouse == ""){
			alert("请先选择仓库");
			return;
		}
	});
	
	$j("#selTrans").blur(function(){
		var startWarehouse = $j("#selTrans").val();
		if(startWarehouse != ""){
			loadShop();
		}
	});
	
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val("");
	});
	
});