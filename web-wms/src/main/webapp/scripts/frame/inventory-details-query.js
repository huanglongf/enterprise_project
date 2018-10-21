$j.extend(loxia.regional['zh-CN'],{
	JMCODE 			:"商品编码",
	BARCODE			:"条形码",
	KEYPROPERTIES	:"扩展属性",
	SUPPLIERSKUCODE	:"货号",
	SKUNAME			:"商品名称",
	LOCATIONCODE	:"库位编码",
	INVSTATUSNAME	:"库存状态",
	INVOWNER		:"所属店铺",
	QUANTITY		:"实际库存量",
	LOCKQTY			:"占用库存量",
	AVAILQTY		:"可用库存量",
	TABLETITLE		:"库存明细查询",
	SKUCODE			:"SKU编码",
	PRODUCTSIZE		:"商品大小"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

var $j = jQuery.noConflict();
function getCurrentTime() { 
	$j(".ui-datepicker-current").click();
}   

$j(document).ready(function (){
	initShopQuery("companyshop","innerShopCode");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	jumbo.loadShopList("companyshop");
	var baseUrl = $j("body").attr("contextpath")+"/json";
	//库存商品状态
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findInventoryStatus.do");
	for(var idx in result){
		$j("<option value='" + result[idx].statusId + "'>"+ result[idx].statusName +"</option>").appendTo($j("#inventoryStatusId"));
	}
	$j("#tbl-inventory-list").jqGrid({
		//url:baseUrl+"/inventorydetailsquery.json",
		postData:loxia._ajaxFormToObj("form_query"),
		datatype: "json",
		//colNames: ["ID","SKU编码","商品编码","条形码","商品名称","货号","库位编码","库存状态","所属店铺","实际库存量","占用库存量","可用库存量","商品大小"],
		colNames: ["ID",i18("SKUCODE"),i18("BARCODE"),i18("SKUNAME"),"品牌对接编码",i18("SUPPLIERSKUCODE"),i18("LOCATIONCODE"),i18("INVSTATUSNAME"),
		           i18("INVOWNER"),i18("QUANTITY"),i18("LOCKQTY"),i18("AVAILQTY"),i18("PRODUCTSIZE"),i18("JMCODE"),i18("KEYPROPERTIES"),"品牌名称","生产日期","过期时间","有效期天数"],
		colModel: [
			{name: "id", index: "id", hidden: true},
			{name:"skuCode",index:"skuCode",width:80,resizable:true},
			{name:"barCode",index:"barCode",width:80,resizable:true},
			{name:"skuName", index:"skuName", width:60, resizable:true},
			{name:"extCode2", index:"extCode2", width:80, resizable:true},
			{name:"supplierSkuCode", index:"supplierSkuCode", width:60, resizable:true},
			{name:"locationCode",index:"locationCode",width:80,resizable:true},
			{name:"invStatusName", index:"invStatusName" ,width:60,resizable:true},
			{name:"invOwner", index:"invOwner" ,width:100,resizable:true},
			{name:"quantity", index:"quantity" ,width:50,resizable:true},
			{name:"lockQty",index:"lockQty",width:50,resizable:true},
			{name:"availQty",index:"availQty",width:50,resizable:true},
			{name:"productSize",index:"productSize",width:40,resizable:true},
			{name:"jmCode",index:"jmCode",width:60,resizable:true},
			{name:"keyProperties",index:"keyProperties",width:60,resizable:true},
			{name:"brandName",index:"brandName",width:60,resizable:true},
			{name:"pDate",index:"pDate",width:80,resizable:true},
			{name:"eDate",index:"eDate",width:80,resizable:true},
			{name:"validDate",index:"validDate",width:80,resizable:true}
			],
		caption: i18("TABLETITLE"),
		rowNum: jumbo.getPageSize(),
		rowList:jumbo.getPageSizeList(),
	   	sortname: 'locationCode',
	    pager: '#pager',
	    width:1000,
	    multiselect: false,
		sortorder: "desc", 
		height:jumbo.getHeight(),
		viewrecords: true,
   		rownumbers:true,
		jsonReader: { repeatitems : false, id: "0" }
	});
	jumbo.bindTableExportBtn("tbl-inventory-list");	
	$j("#search").click(function(){
		var warningDate = $j("#warningDate").val();
		var st = getDate($j("#startDate").val());
		var ed = getDate($j("#endDate").val());
		var isZeroInventory;
		if($j("#isZeroInventory").attr("checked")){
			isZeroInventory=2;
		}else{
			isZeroInventory=1;	
		}
		
		if(warningDate!=""){
			if(!(/^[0-9]+$/.test(warningDate))){
				jumbo.showMsg("预警天数不是合法数字！");
 				return;
			}
		}
		if(st > ed){
			jumbo.showMsg(i18("起始时间必须小于结束时间！"));
			return false;
		}
		var urlx = loxia.getTimeUrl(baseUrl+"/inventorydetailsquery.json");
		var postData=loxia._ajaxFormToObj("form_query");
		postData["inventoryCommand.isZeroInventory"] = isZeroInventory;
		jQuery("#tbl-inventory-list").jqGrid('setGridParam',{url:urlx,page:1,postData:postData}).trigger("reloadGrid");
		jumbo.bindTableExportBtn("tbl-inventory-list",{},urlx,postData);	
		var rs = loxia.syncXhrPost(loxia.getTimeUrl(baseUrl+"/inventorydetailsquery1.json"),postData);
		$j("#sku").html(rs.sku);
		$j("#loc").html(rs.loc);
		$j("#invavail").html(rs.qty);
		$j("#invsales").html(rs.lockqty);
		$j("#invoccupy").html(rs.availqty);
	});
	
	$j("#reset").click(function(){
		$j("#form_query input").val("");
		$j("#form_query select").val("");
		$j("#wdDiv").addClass('hidden');
	});
	
	//保质期选项选中事件
	$j("#shelfLife").change(function(){
		var shelfLife = $j("#shelfLife").val();
		if(shelfLife == '1'){
			$j("#wdDiv").removeClass('hidden');
			$j("#warningDate").val("");
		}else{
			$j("#wdDiv").addClass('hidden');
			$j("#warningDate").val("");
		}
	}); 
});

	function getDate(strDate){
	var s = new Date(strDate.substring(0,10));
	s.setHours(strDate.substring(11,13));
	s.setMinutes(strDate.substring(11,13));
	s.setSeconds(strDate.substring(11,13));
	return s;
}