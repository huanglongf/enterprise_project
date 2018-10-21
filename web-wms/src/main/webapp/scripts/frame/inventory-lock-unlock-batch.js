var INCLUDE_SEL_ID = "";
var INCLUDE_SEL_VAL_CODE = "";
function initShopQuery(selId,valCode){
	INCLUDE_SEL_ID = selId;
	INCLUDE_SEL_VAL_CODE = valCode;
	if(valCode == 'innerShopCode'){
		INCLUDE_SEL_VAL_CODE = 'code'
	}
}
var $j = jQuery.noConflict();
$j(document).ready(function (){
	$j("#invLockBatchDialog").dialog({title: "批量添加锁定库存", modal:true, autoOpen: false, width: 1400,height:"auto"});
	var baseUrl = $j("body").attr("contextpath");
	jumbo.loadShopList("shopShow","id");
	//库存商品状态
	var result = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/findInventoryStatus.do");
	for(var idx in result){
		$j("<option value='" + result[idx].statusId + "'>"+ result[idx].statusName +"</option>").appendTo($j("#inventoryStatusId"));
	}
	
	$j("#tbl_inventory_lock_batch_list").jqGrid({
		datatype: "json",
		colNames: ["indexId","SKUID","locationId","statusId","shopId","SKU编码","商品名称","商品编码","货号","扩展属性","店铺","库位","库存状态","可锁定数量","本次锁定数量"],
		colModel: [
					            {name:"indexId", index: "indexId", hidden: true},		         
					            {name:"skuId", index: "skuId", hidden: true},		         
					            {name:"locationId", index: "locationId", hidden: true},		         
					            {name:"statusId", index: "statusId", hidden: true},		         
					            {name:"shopId", index: "shopId", hidden: true},		         
					            {name:"skuCode", index: "skuCode", width:160,resizable: true},		         
					            {name:"skuName",index:"skuName",width:160,resizable: true},
					            {name:"jmCode",index:"jmCode",width:160,resizable:true},
					            {name:"supplierCode", index: "supplierCode", width:160,resizable:true},
					            {name:"keyProperties",index:"keyProperties",width:100,resizable:true},
					            {name:"invOwner",index:"invOwner",width:130,resizable:true},
								{name:"locationCode",index:"locationCode",width:160,resizable:true},
								{name:"invStatusName",index:"invStatusName",width:80,resizable:true},
								{name:"quantity",index:"quantity",width:80,resizable:true},
								{name:"lockQty",index:"lockQty",formatter:"loxiaInputFmatter", formatoptions:{loxiaType:"number",min:"0"},width: 80, resizable: true,sortable:false}
						   ],
		caption: "可锁定库存列表",
		multiselect: true,
		height:"auto",
		rowNum: -1,
		viewrecords: true,
		rownumbers:true,
		gridComplete : function(){loxia.initContext($j(this));},
		jsonReader: {repeatitems : false, id: "0"}
	});
	
	$j("#cancel").click(function(){
		$j("#invLockBatchDialog").dialog("close");
	});
	
	$j("#batchQuery").click(function(){
		var idsList = $j("#idsList").val();
		var postData = {};
		var shop = $j("#shopShow").val();
		var locationCode = $j("#locationCodeBatch").val();
		var skuCodeBatch = $j("#skuCodeBatch").val();
		var productionDate = $j("#productionDate").val();
		var expireDate = $j("#expireDate").val();
		var inventoryStatusId = $j("#inventoryStatusId").val();
		if("" == shop || null == shop){
			jumbo.showMsg("请选择店铺");
			return;
		}
		if((""==skuCodeBatch||null==skuCodeBatch)&&(""==locationCode||null==locationCode)){
			jumbo.showMsg("SKU编码和库位编码必须填写一个");
			return;
		}
		if (productionDate && productionDate.length > 0) {
			productionDate = productionDate.substring(0,10);
		}
		if (expireDate && expireDate.length > 0) {
			expireDate = expireDate.substring(0,10);
		}
		postData["shopId"]= shop;
		postData["skuCode"]= skuCodeBatch;
		postData["locationCode"]= locationCode;
		postData["productionDate"]= productionDate;
		postData["expireDate"]= expireDate;
		postData["inventoryStatusId"]= inventoryStatusId;
		$j("#tbl_inventory_lock_batch_list").jqGrid("clearGridData");
		var data = loxia.syncXhr(baseUrl+"/json/findInventoryBatchLockList.json",postData);
		if(data){
//			if(""==idsList||null==idsList){
//				for(var i in data.list){
//					$j("#tbl_inventory_lock_batch_list").jqGrid('addRowData',data.list[i].id,data.list[i]);							
//				}
//			}else{
				for(var i in data.list){
//					alert(idsList+" "+data.list[i].id);
//					alert(idsList.indexOf(data.list[i].id));
//					if(idsList.indexOf(data.list[i].id) ==-1){
						$j("#tbl_inventory_lock_batch_list").jqGrid('addRowData',data.list[i].indexId,data.list[i]);					
//					}
//				}
			}	
		}else{
			jumbo.showMsg("数据操作异常");
		}
	});
	
	$j("#addInvBatch").click(function(){
		var idsList = $j("#idsList").val();
		var ids = $j("#tbl_inventory_lock_batch_list").jqGrid('getGridParam','selarrrow');
		if(ids.length==0){
			jumbo.showMsg("请选择需要锁定的库存");
			return;
		}
		var re = /^[1-9]+[0-9]*]*$/;
		var index = ids.toString().split(",");
		for (var int = 0; int < index.length; int++) {
			var tr = $j("#tbl_inventory_lock_batch_list tr[id=" + index[int] + "]").children('td').eq(0).html();
			var values = $j("#tbl_inventory_lock_batch_list").jqGrid('getRowData',index[int]);
			var quantity = values["quantity"];
			var lockQty = values["lockQty"];
		    if (!re.test(lockQty)){
		    	jumbo.showMsg("数量不正确，必须大于0的整数!");
				return;
			}
			if(parseInt(quantity)<parseInt(lockQty)){
				jumbo.showMsg("行序号: "+tr+" 超出实际数量范围");
				return;
			}
		}
		for (var int = 0; int < index.length; int++) {
			var values = $j("#tbl_inventory_lock_batch_list").jqGrid('getRowData',index[int]);
			var locationCode = values["locationCode"];
			var skuCode = values["skuCode"];
			var skuId = values["skuId"];
			var statusId = values["statusId"];
			var shopId = values["shopId"];
			var lockQty = values["lockQty"];
			querySkuQtyBatch(locationCode,skuCode,skuId,statusId,shopId,lockQty);
		}
		$j("#invLockBatchDialog").dialog("close");
//		var postData = {};
//		var check = "0";
//		loxia.lockPage();
//		var data = loxia.syncXhr(baseUrl+"/json/findInventoryBatchLockListByInvIds.json?invIds="+ids,postData);
//		if(data){
//			for(var i in data.list){
//				$j("#tbl_temp_list").jqGrid('addRowData',data.list[i].indexId,data.list[i]);
//				check = "1";
//			}
//			if(""==idsList||null==idsList){
//				$j("#idsList").val(ids);
//			}else{
//				var idss = idsList+","+ids;
//				$j("#idsList").val(idss);
//			}
//		} else {
//			jumbo.showMsg("数据操作异常");
//		}
//		loxia.unlockPage();
//		if(check == "1"){
//			$j("#searchOne").hide();
//			$j("#searchTwo").hide();
//		}
//		$j("#invLockBatchDialog").dialog("close");
	});
	

});

function querySkuQtyBatch(locationCode,skuCode,skuId,statusId,shopId,qty){
	var postData = {};
	postData["skuId"] = skuId;
	postData["skuCode"] = skuCode;
	postData["locationCode"] = locationCode;
	postData["statusId"] = statusId;
	postData["shopId"] = shopId;
	postData["qty"] = qty;
	//postData["productionDate"] = productionDate;
	//postData["expireDate"] = expireDate;
	var data = loxia.syncXhr(baseUrl+"/json/querySkuQty.json",postData); // 根据条件获取商品数量
	if(data){
		/*for(var i in data.list){
			if(data.list[i].storeMode == 33){
				jumbo.showMsg("该商品为保质期商品,不支持手动创建！");
				return;
			}
		}*/
		for(var i in data.list){
			var ids = $j("#tbl_temp_list").jqGrid('getRowData',data.list[i].indexId);
			if(ids["indexId"]!=data.list[i].indexId){
				if(data.list[i].quantity >= qty){
					data.list[i].quantity = qty;
					$j("#tbl_temp_list").jqGrid('addRowData',data.list[i].indexId,data.list[i]);
					break;
				} else {
					$j("#tbl_temp_list").jqGrid('addRowData',data.list[i].indexId,data.list[i]);
					qty = qty - data.list[i].quantity;
				}
			}
		}
	} else {
		jumbo.showMsg("数据操作异常");
	}
}
