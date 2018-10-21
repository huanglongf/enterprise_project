var INCLUDE_SEL_ID = "";
var INCLUDE_SEL_VAL_CODE = "";
function initShopQuery(selId,valCode){
	INCLUDE_SEL_ID = selId;
	INCLUDE_SEL_VAL_CODE = valCode;
	if(valCode == 'innerShopCode'){
		INCLUDE_SEL_VAL_CODE = 'code'
	}
}

$j(document).ready(function (){
	$j("#upgradeDialog").dialog({title: "在库商品效期调整", modal:true, autoOpen: false, width: 680});
	var baseUrl = $j("body").attr("contextpath");

	/**
	 * 取消
	 */
	$j("#cancel").click(function(){
		$j("#productionDate").val("");
		$j("#expireDate").val("");
		$j("#selectId").val("");
		$j("#upgradeDialog").dialog("close");
	});
	
	/**
	 * 确认
	 */
	$j("#confirm").click(function(){
        var pDate = $j("#productionDate").val();
		var eDate = $j("#expireDate").val();
		if (!isNotNull(pDate) && !isNotNull(eDate)) {
			return;
		}
		if (isNotNull(pDate) && isNotNull(eDate)) {
			if (eDate > pDate) {
				var postData = packageData();
				postData["inventoryCommand.poductionDate"] = $j("#productionDate").val();
				postData["inventoryCommand.sexpireDate"] = $j("#expireDate").val();
			}else {
				jumbo.showMsg("失效日期必须大于生产日期");
				return;
			}
		}
		if (!isNotNull(pDate) && isNotNull(eDate)) {
			var postData = packageData();
			postData["inventoryCommand.sexpireDate"] = $j("#expireDate").val();
		}
		if (isNotNull(pDate) && !isNotNull(eDate)) {
			var postData = packageData();
			postData["inventoryCommand.poductionDate"] = $j("#productionDate").val();
		}
		var rs = loxia.syncXhrPost(loxia.getTimeUrl($j("body").attr("contextpath") + "/validityadjustmodify.json"), postData);
		if (rs && rs.result == "success") {
			$j("#upgradeDialog").dialog("close");
			jumbo.showMsg("效期更新成功");
			var url = loxia.getTimeUrl($j("body").attr("contextpath") + "/validityadjustquery.json");
			$j("#tbl_inventory_adjust_list").jqGrid('setGridParam',{url:url,page:1,postData:loxia._ajaxFormToObj("form_query")}).trigger("reloadGrid");
			
		}else if (rs && rs.result == "none") {
			jumbo.showMsg("查询结果无可操作的数据");
		}else {
			if(rs && rs.result == "exception"){
				jumbo.showMsg(rs.msg);
				return;
			}else {
				jumbo.showMsg("系统异常,请联系管理员");
			}
		}
		
	});
	
});

function isNotNull(data){
	var result = false;
	if (data != null && data != undefined && data != '') { 
		result =  true;
	} 
	return result;
}

function packageData() {
	var rowId = $j("#selectId").val();
	var obj =$j("#tbl_inventory_adjust_list").jqGrid("getRowData",rowId);
	var postData = {};
	postData["inventoryCommand.skuId"] = obj["skuId"];
	postData["inventoryCommand.locationCode"] = obj["locationCode"];
	postData["inventoryCommand.inventoryStatusName"] = obj["invStatusName"];
	postData["inventoryCommand.inventoryStatusId"] = obj["inventoryStatusId"];
	postData["inventoryCommand.invOwner"] = obj["invOwner"];
	postData["inventoryCommand.pDate"] = obj["productionDate"];
	postData["inventoryCommand.eDate"] = obj["expireDate"];
	return postData;
}