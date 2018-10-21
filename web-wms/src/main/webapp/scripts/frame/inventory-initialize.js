$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE : "请选择正确的Excel导入文件",
	JMCODE: "商品编码",
	KEY_PROPS : "扩展属性",
	BARCODE : "条形码",
	SKU_NAME : "商品名称",
	OWNER : "所属店铺",
	SUPPLIER_CODE : "货号",
	LOCATION_CODE : "库位",
	INNVENTORY_STATUS : "库存状态",
	QTY : "数量",
	SKU_COST : "单位成本",
	CURRENT_INVENTORY : "当前库存列表",
	OU_CAN_NOT_INITIALIZE : "该仓库存在库存不能进行初始化工作"
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

function showMsg(msg){
	jumbo.showMsg(msg);
}

function reloadTable(){
	$j("#tbl-invList").jqGrid('setGridParam',{
			url:loxia.getTimeUrl($j("body").attr("contextpath") + "/findAllInventory.json")
		}).trigger("reloadGrid",[{page:1}]);
}

$j(document).ready(function (){
	$j("#tbl-invList").jqGrid({
		url:$j("body").attr("contextpath") + "/findAllInventory.json",
		datatype: "json",
		colNames: ["ID",i18("JMCODE"),i18("KEY_PROPS"),i18("BARCODE"),i18("SKU_NAME"),i18("OWNER"),i18("SUPPLIER_CODE"),i18("LOCATION_CODE"),i18("INNVENTORY_STATUS"),i18("QTY")],
		colModel: [
	            {name: "id", index: "id", hidden: true},		         
	            {name:"jmCode",index:"sku.jm_code",width:100,resizable:true},
				{name:"keyProperties",index:"key_properties",width:100,resizable:true},
				{name:"barCode", index:"bar_code", width:100, resizable:true},
				{name:"skuName",index:"skuName",width:150,resizable:true},
				{name:"invOwner", index:"invOwner" ,width:150,resizable:true},
				{name:"supplierSkuCode",index:"supplierSkuCode",width:150,resizable:true},
				{name:"locationCode",index:"locationCode",width:150,resizable:true},
				{name:"invStatusName",index:"invStatusName",width:80,resizable:true},
				{name:"quantity",index:"quantity",width:80,resizable:true}],
		caption: i18("CURRENT_INVENTORY"),
		sortname: 'sku.jm_code',
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	pager:"#pager",
	   	height:jumbo.getHeight(),
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});
	
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/importInitializeInventory.do");
		$j("#importForm").submit();
	});
	
});
