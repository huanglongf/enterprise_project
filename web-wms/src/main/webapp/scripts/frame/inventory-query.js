$j.extend(loxia.regional['zh-CN'],{
	JMCODE : "商品编码",
	KEYPROPERTIES : "扩展属性",
	SKUNAME : "商品名称",
	SKUCODE: "SKU编码",
	BARCODE : "条形码",
	SUPPLIERSKUCODE : "货号",
	INVOWNER : "所属店铺",
	QUANTITY : "实际库存量（件）",
	LOCKQTY : "已占用库存量（件）",
	AVAILQTY : "可用库存量（件）",
	SALESAVAILQTY : "销售可用量（件）",
	INVENTORY_QUERY_LIST : "库存查询列表",
	LOCATIONCODE: "库位编号",
	BATCHCODE : "批次号",
	DISTRICTCODE : "库区",
	LOCATIONCODE : "库位",
	INVSTATUSNAME : "库存状态",
	QUANTITY : "数量",
	LOCKQTY : "占用量",
	EXTENSION_CODE1:"平台对接编码",
	INPUT_POSITIVE_INTEGER:"请输入大于零的整数!"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

/**
 * 是否为正整数
 * @param s
 * @returns
 */
function isPositiveNum(s){  
    var re = /^[0-9]*[1-9][0-9]*$/ ;  
    return re.test(s)  
} 
/**
 * 判断是否为空
 * @param data
 * @returns
 */
function isNull(data){ 
	return (data == "" || data == undefined || data == null || data.length == 0) ? true : false; 
}

$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	
	initShopQuery("invOwner","innerShopCode");
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});

	
	jumbo.loadShopList("invOwner");
	
	$j("#query").click(function(){
		var numberUp = $j("#numberUp").val();	
		var AmountTo = $j("#AmountTo").val();	
		var urlx = loxia.getTimeUrl(baseUrl+"/findCurrentInventory.json");
		var postData=loxia._ajaxFormToObj("quert-form");
	   
		if($j("#showZero").attr("checked")){
			postData["inventoryCommand.isShowZero"] = true;
		}else{
			postData["inventoryCommand.isShowZero"] = false;
		}
		// 数量条件仅允许录入正整数，允许只录入上限或者下限
		if (isNull(numberUp) && isNull(AmountTo)) {
			jQuery("#tbl-inventory-query").jqGrid('setGridParam',{url:urlx,postData:postData}).trigger("reloadGrid",[{page:1}]);
			jumbo.bindTableExportBtn("tbl-inventory-query",{},urlx,postData);
		}else if ((!isNull(numberUp) && isPositiveNum(numberUp)) && isNull(AmountTo)) {
			jQuery("#tbl-inventory-query").jqGrid('setGridParam',{url:urlx,postData:postData}).trigger("reloadGrid",[{page:1}]);
			jumbo.bindTableExportBtn("tbl-inventory-query",{},urlx,postData);
		}else if ((!isNull(AmountTo) && isPositiveNum(AmountTo)) && isNull(numberUp)) {
			jQuery("#tbl-inventory-query").jqGrid('setGridParam',{url:urlx,postData:postData}).trigger("reloadGrid",[{page:1}]);
			jumbo.bindTableExportBtn("tbl-inventory-query",{},urlx,postData);
		}else if ((!isNull(numberUp) && isPositiveNum(numberUp)) && (!isNull(AmountTo) && isPositiveNum(AmountTo))) {
			jQuery("#tbl-inventory-query").jqGrid('setGridParam',{url:urlx,postData:postData}).trigger("reloadGrid",[{page:1}]);
			jumbo.bindTableExportBtn("tbl-inventory-query",{},urlx,postData);
		}else {
			jumbo.showMsg(i18("INPUT_POSITIVE_INTEGER"));
		}
	});
	
	$j("#reset").click(function(){
		$j("#quert-form input").val("");
		$j("#quert-form select").val("");
	});
	
	$j("#tbl-inventory-query").jqGrid({
		//url:baseUrl+"/findCurrentInventory.json",
		postData : loxia._ajaxFormToObj("quert-form"),
		datatype: "json",

		//colNames: ["skuIdOwner","skuId","skuCode","商品编码","条形码","货号","商品名称","所属店铺","实际库存量（件）","已占用库存量（件）","可用库存量（件）","销售可用量（件）"],
		colNames: ["skuIdOwner","skuId",i18("SKUCODE"),i18("JMCODE"),i18("KEYPROPERTIES"),i18("BARCODE"),i18("SUPPLIERSKUCODE"),i18("SKUNAME"),"品牌对接编码",
		           i18("INVOWNER"),i18("INVOWNER"),"品牌名称",i18("EXTENSION_CODE1"),i18("QUANTITY"),i18("LOCKQTY"),i18("AVAILQTY"),i18("SALESAVAILQTY")],

		colModel: [ {name: "skuIdOwner", index: "skuIdOwner", width: 100, hidden: true},
		           {name: "skuId", index: "skuId", width: 100, hidden: true},
		           {name: "skuCode", index: "skuCode", width: 100,  resizable: true},
		           {name: "jmCode", index: "jmCode", width: 100, resizable: true},
		           {name: "keyProperties", index: "keyProperties", width: 100, resizable: true},
		           {name: "barCode", index: "barCode", width: 100, resizable: true},
		           {name: "supplierSkuCode", index: "supplierSkuCode", width:150, resizable: true},
		           {name: "skuName", index: "skuName", width: 80, resizable: true},
		           {name: "extCode2", index: "extCode2", width: 80, resizable: true},
		           {name: "invOwner",  width: 80, resizable: true,hidden:true},
		           {name: "shopName", index: "ch.name", width: 80, resizable: true},
		           {name: "brandName", index: "brandName", width: 110, resizable: true},
		           {name: "extCode1", index: "extCode1", width: 80, resizable: true},
		           {name: "quantity", index: "quantity", width: 120, resizable: true},
		           {name: "lockQty", index: "lockQty", width: 110, resizable: true},
		           {name: "availQty", index: "availQty", width: 110, resizable: true},
		          
		           {name: "salesAvailQty", index: "salesAvailQty", width: 110, resizable: true}],
		caption: i18("INVENTORY_QUERY_LIST"),//库存查询列表
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'invOwner,jmCode',
	    pager: '#pager2',
	    multiselect: false,
		sortorder: "desc", 
		height:jumbo.getHeight(),
		jsonReader: { repeatitems : false, id: "0" },
		viewrecords: true,
   		rownumbers:true,
		subGrid : true,
		subGridRowExpanded: function(subgrid_id, row_id) {
			var postData = {};
			postData["inventoryCommand.skuId"] = $j("#tbl-inventory-query tr[id='"+row_id+"'] td[aria-describedby='tbl-inventory-query_skuId']").html();
			postData["inventoryCommand.owner"] = $j("#tbl-inventory-query tr[id='"+row_id+"'] td[aria-describedby='tbl-inventory-query_invOwner']").html().replace('&nbsp;','');
			var ind = $j("#tbl-inventory-query tr[id='"+row_id+"']").index()+1;
			var subTable="subTableKJL"+ind;
			$j("#tbl-inventory-query>tbody>tr").eq(ind).children("td").children("div").html("<table id='"+subTable+"'></table>");
		    $j("#"+subTable).jqGrid({
		          url: baseUrl + "/findInventoryDetial.json",
		          postData : postData,
		          datatype: "json",
				  //colNames:['id','批次号','库区','库位','库存状态','数量','占用量'],
		          colNames:['id',i18('BATCHCODE'),"占用编码",i18('DISTRICTCODE'),i18('LOCATIONCODE'),i18('INVSTATUSNAME'),i18('QUANTITY'),i18('LOCKQTY')],
		          colModel: [{name: "skuId", index: "skuId", hidden: true},
		            {name: "batchCode", index: "batchCode", width:100},
		            {name: "occupationCode", index: "occupationCode", width:100},
		            {name: "districtCode", index: "districtCode", width:100},
		            {name: "locationCode", index: "locationCode", width:100},
		            {name: "invStatusName", index: "invStatusName", width:100},
		            {name: "quantity", index: "quantity", width:100},
		            {name: "lockQty", index: "lockQty", width:130}],
		        	sortname: 'skuId',
		        	height: "100%",
		            multiselect: false,
		        	sortorder: "desc",
		        	rowNum:-1,
		        	jsonReader: { repeatitems : false, id: "0" }
		       })}
	});
	jumbo.bindTableExportBtn("tbl-inventory-query");
});
 

