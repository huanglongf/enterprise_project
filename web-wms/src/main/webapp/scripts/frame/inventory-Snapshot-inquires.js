
$j.extend(loxia.regional['zh-CN'],{
	INPUT_EMPTY : "输入不能为空",
	WAREHOUSENAME : "仓库",
	JMCODE : "商品编码",
	KEYPROPERTIES : "扩展属性",
	BARCODE : "条形码",
	BRANDNAME : "品牌",
	SKUNAME : "商品名称",
	INBOWNER : "所属店铺",
	SUPPLIERSKUCODE : "货号",
	INITQTY : "期初库存",
	QUANTITY : "期末库存",
	CHANGEQTY : "变化量",
	INVENTORY_SNAPSHOT_QUEYR_LIST : "历史库存查询",
	INV_CK_CODE : "盘点批编码",
	JMCODE : "商品编码",
	STACODE : "作业单号",
	DISTRICTCODE : "库区",
	LOCATINCODE : "库位",
	TRANSACTIONTYPENAME : "作业类型",
	OWNER : "店铺",
	TRANSACTIONTIME : "操作时间",
	INQTY : "入库数量",
	OUTQTY : "出库数量",
	INVENTORY_OPERATE_LOG : "库存操作日志",
	
	DATE_RULE : "起始时间必须小于结束时间！",
	ERROR_MASSAGE : "提交数据时发现错误：共发现 {0} 处域填写问题[请点击边框为红色的文本框查看出错详细信息]",
	SKUCODE: "SKU编码"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

var sDate;
var eDate;
var whOuId;
function checkStartDate(obj){
	if(obj == ""){
		//loxia.tip($j("#startDate"),"输入不能为空");
		return i18("INPUT_EMPTY");//输入不能为空
	}
	return loxia.SUCCESS;
}

function checkEndDate(obj){
	if(obj == ""){
		//loxia.tip($j("#endDate"),"输入不能为空");
		return i18("INPUT_EMPTY");//输入不能为空
	}
	return loxia.SUCCESS;
}

function showinventorydetail(tag){
	var rowm = $j(tag).parents("tr").attr("id");
	var data=$j("#tbl-inventory-list").jqGrid("getRowData",rowm);
	var postData = {};
	postData["startDate"] = sDate;
	postData["endDate"] = eDate;
	postData["endDate"] = eDate;
	postData["skuId"] = data["skuId"];
	postData["inventory.whOuId"] = whOuId;
	if(data["invOwner"] != null){
		postData["owner"] = data["invOwner"];
	}
	var baseUrl = $j("body").attr("contextpath");
	$j("#tbl-details").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findStTxLogPageBySku.json"),page:1,postData:postData}).trigger("reloadGrid");
	jumbo.bindTableExportBtn("tbl-details",{},loxia.getTimeUrl(baseUrl + "/findStTxLogPageBySku.json"),postData);
	$j("#detail").removeClass("hidden");
	$j('#detail').dialog({width : 850});
}

function getDate(strDate){
	var s = new Date(strDate.substring(0,10));
	s.setHours(strDate.substring(11,13));
	s.setMinutes(strDate.substring(11,13));
	s.setSeconds(strDate.substring(11,13));
	return s;
}

$j(document).ready(function (){
	
	var baseUrl = $j("body").attr("contextpath");
	var result = loxia.syncXhrPost(baseUrl+"/getWarehouseDatabyOrganise.json");
	for(var idx in result){
		$j("<option value='" + result[idx].value + "'>"+ result[idx].name +"</option>").appendTo($j("#selWhOuId"));
	}

	jumbo.loadShopList("companyshop",null,true);
	$j("#tbl-inventory-list").jqGrid({
		datatype: "json",
		//colNames: ["ID","仓库","商品编码","扩展属性","条形码","品牌","商品名称","所属店铺","供应商编码","期初库存","期末库存","变化量"],
		colNames: ["id","SKUID",i18("WAREHOUSENAME"),i18("SKUCODE"),i18("JMCODE"),i18("KEYPROPERTIES"),i18("BARCODE"),i18("SUPPLIERSKUCODE"),i18("SKUNAME"),i18("INBOWNER"),i18("BRANDNAME"),i18("INITQTY"),i18("QUANTITY"),i18("CHANGEQTY")],
		colModel: [{name: "rowm", index: "rowm", hidden: true},
		            {name: "skuId", index: "skuId", hidden: true},
		            {name:"warehouseName", index:"warehouseName", width:80, resizable:true},
		            {name:"skuCode", index:"skuCode", width:80, resizable:true},
					{name:"jmCode",index:"jmCode",formatter:"linkFmatter", formatoptions:{onclick:"showinventorydetail"},width:80,resizable:true},
					{name:"keyProperties", index:"keyProperties", width:60, resizable:true},
					{name:"barCode", index:"barCode", width:80, resizable:true},
					{name:"supplierSkuCode",index:"supplierSkuCode",width:100,resizable:true},
					{name:"skuName",index:"skuName",width:80,resizable:true},
					{name:"invOwner", index:"invOwner" ,width:100,resizable:true},
					{name:"brandName", index:"brandName", width:60, resizable:true},
					{name:"initQty",index:"initQty",width:60,resizable:true},
					{name:"quantity", index:"quantity" ,width:60,resizable:true},
					{name:"changeQty", index:"changeQty",width:60,resizable:true}],
		caption: i18("INVENTORY_SNAPSHOT_QUEYR_LIST"),//库存快照查询列表
	   	sortname: 'jmCode',
	    multiselect: false,
	    rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	height:jumbo.getHeight(),
	   	viewrecords: true,
   		rownumbers:true,
	   	pager:"#pager",
		sortorder: "desc",
		jsonReader: { repeatitems : false, id: "0" }
	});
		
	$j("#tbl-details").jqGrid({
	    datatype: "json",
		//colNames: ['id','商品编码','作业单号','库区','库位','作业类型','店铺','操作时间','入库数量','出库数量'],
	    colNames: ['id',i18('JMCODE'),i18('STACODE'),i18("INV_CK_CODE"),i18('DISTRICTCODE'),i18('LOCATINCODE'),i18('TRANSACTIONTYPENAME'),i18('OWNER'),i18('TRANSACTIONTIME'),i18('INQTY'),i18('OUTQTY')],
		colModel: [
		  	{name: "id", index: "id", hidden: true},
		    {name:"jmCode",index:"jmCode",width:80},
		  	{name:"staCode",index:"staCode",width:100},
		  	{name:"inventoryCheckCode",index:"inventoryCheckCode",width:80},
		  	{name:"districtCode", index:"districtCode",width:60},
		    {name:"locationCode",index:"locationCode",width:80},
		    {name:"transactionTypeName",index:"transactionTypeName",width:60},
		    {name:"owner",index:"owner",width:80,sortable:false},
		    {name:"transactionTime",index:"transactionTime",width:100},
		    {name:"inQty",index:"inQty",width:60},
		    {name:"outQty",index:"outQty",width:60}],
		caption: i18("INVENTORY_OPERATE_LOG"),//库存操作日志
		sortname: 'lg.id',
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
   		pager:"#pater-details",
		multiselect: false,
		sortorder: "desc",
		viewrecords: true,
   		rownumbers:true,
		height:jumbo.getHeight(),
		jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#search").click(function(){
		var errors=loxia.validateForm("modifyForm");
		
		var st = getDate($j("#startDate").val());
		var ed = getDate($j("#endDate").val());
		if(st > ed){
			jumbo.showMsg(i18("DATE_RULE"));//起始时间必须小于结束时间！
			return false;
		}
		sDate = $j("#startDate").val();
		eDate = $j("#endDate").val();
		whOuId = $j("#selWhOuId").val();
		var i = 0;
		if(sDate == ""){
			loxia.tip($j("#startDate"),i18("INPUT_EMPTY"));//输入不能为空
			$j("#startDate").addClass("ui-loxia-error");
			i++;
		}else{
			$j("#startDate").removeClass("ui-loxia-error");
		}
		
		if(eDate == ""){
			loxia.tip($j("#endDate"),i18("INPUT_EMPTY"));//输入不能为空
			$j("#endDate").addClass("ui-loxia-error");
			i++
		}else{
			$j("#endDate").removeClass("ui-loxia-error");
		}
		
		if(errors.length>0){
			jumbo.showMsg(errors);
			return false;
		}else if(i>0){
			jumbo.showMsg(i18("ERROR_MASSAGE",i));//"提交数据时发现错误：共发现 "+i+" 处域填写问题[请点击边框为红色的文本框查看出错详细信息]"
			return false;
		}
		var postData = loxia._ajaxFormToObj("queryInv");
		$j("#tbl-inventory-list").jqGrid('setGridParam',{url:loxia.getTimeUrl(baseUrl + "/findInventorySnapshot.json"),postData:postData,page:1}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-inventory-list",{},loxia.getTimeUrl(baseUrl + "/findInventorySnapshot.json"),postData);
	//	$j("#detail").addClass("hidden");
	});
	
	$j("#reset").click(function(){
		document.getElementById("queryInv").reset();
	});
});