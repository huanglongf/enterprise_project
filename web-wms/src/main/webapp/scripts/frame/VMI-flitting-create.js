$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE : "请选择正确的Excel导入文件",
	JMCODE: "商品编码",
	SKUCODE: "SKU编码",
	KEY_PROPS : "扩展属性",
	BARCODE : "条形码",
	SKU_NAME : "商品名称",
	OWNER : "所属店铺",
	SUPPLIER_CODE : "货号",
	LOCATION_CODE : "库位",
	INNVENTORY_STATUS : "库存状态",
	MOVEQTY : "转移数量",
	QTY : "数量",
	SKU_COST : "单位成本",
	BETWEENLIBARY_LIST : "VMI调拨列表",
	OU_CAN_NOT_INITIALIZE : "该仓库存在库存不能进行初始化工作",
	CHOOSE_MAIN_WAREEHOUSE : "请选择源头仓库",
	CHOOSE_ADDI_WAREEHOUSE : "请选择目标仓库",
	MAIN_ADDI_WAREEHOUSE_NOTEQUAR : "源头仓库和目标仓库不能相同",
	CONFIRM_EXEC : "您确定要执行？",
	SAVE_SUCCE : "保存成功！",
	BETWEENLIBRARY_NOT_NULL : "VMI调拨列表不能为空",
	BETWEENLIBRARY_APPLICATION_IMPORT : "VMI调拨申请单导入"
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

function showMsg(msg){
	jumbo.showMsg(msg);
}

function getTableDate(){
	var count =0;
	var postDate = {};
	$j("#tbl-invList tbody tr").each(function (i,tr){
		if(i > 0){
			--i;
		postDate["staLineCmd["+i+"].id"] = $j(tr).attr("id");
		postDate["staLineCmd["+i+"].code"] = $j(tr).find(":gt(1)").html();
		postDate["staLineCmd["+i+"].barCode"] = $j(tr).find(":gt(2)").html();
		postDate["staLineCmd["+i+"].jmCode"] = $j(tr).find(":gt(3)").html();
		postDate["staLineCmd["+i+"].keyProperties"] = $j(tr).find(":gt(4)").html();
		postDate["staLineCmd["+i+"].name"] = $j(tr).find(":gt(5)").html();
		postDate["staLineCmd["+i+"].moveQuantity"] = $j(tr).find(":gt(6)").html();
		}
		
	}); 
	return postDate;
}

$j(document).ready(function (){
	jumbo.loadShopList("selMainShopId",'innerShopCode',false);
	jumbo.loadShopList("selTargetShopId",'innerShopCode',false);
	initShopQuery("selMainShopId","innerShopCode");
	initShopQuery("selTargetShopId","innerShopCode");
	$j("#btnSearchMainShop").click(function(){
		initShopQuery("selMainShopId","innerShopCode");
		$j("#shopQueryDialog").dialog("open");
	});
	$j("#btnSearchTargetShop").click(function(){
		initShopQuery("selTargetShopId","innerShopCode");
		$j("#shopQueryDialog").dialog("open");
	});
	$j("#tbl-invList").jqGrid({
	//	url:"orderNumber.json",
		datatype: "json",
		colNames: ["ID",i18("SKUCODE"), i18("BARCODE"),i18("JMCODE"),i18("KEY_PROPS"),i18("SKU_NAME"),i18("MOVEQTY")],
		colModel: [
		           {name: "id", index: "id",width: 100, hidden: true},	
		           {name: "code", index: "code", width: 100, resizable: true},
		           {name: "barCode", index: "barCode", width: 100, resizable: true},
		           {name: "jmCode", index: "jmCode", width: 100, resizable: true},
		           {name: "keyProperties", index: "keyProperties", width: 120, resizable: true},
		           {name: "name", index: "name", width: 180, resizable: true},
	               {name: "moveQuantity", index: "moveQuantity", width: 100, resizable: true}],
		caption: i18("BETWEENLIBARY_LIST"),
		rowNum:10,
  // 	sortname: 'skuCode',
     	multiselect: false,
	viewrecords: true,
   	rownumbers:true,
	sortorder: "desc", 
	jsonReader: { repeatitems : false, id: "0" }
	});

	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		var startWarehouse = $j("#betwenMoveCmd_startWarehouseId").val();
		
		if(startWarehouse == ""){
			jumbo.showMsg(i18("CHOOSE_MAIN_WAREEHOUSE"));
			return false;
		}
		var owner = $j("#selMainShopId").val();
		if(owner == ""){
			jumbo.showMsg(i18("请选择源头店铺"));
			return false;
		}
		$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/importVMIFlittingApplication.do?comd.startWarehouseId="+startWarehouse+"&comd.owner="+owner);
		$j("#importForm").submit();
		loxia.lockPage();
	});
	
	$j("#export").click(function(){
		var iframe = document.createElement("iframe");
		iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/excelDownload.do?fileName="+i18("BETWEENLIBRARY_APPLICATION_IMPORT")+".xls&inputPath=tplt_import_transit_cross.xls");
		iframe.style.display = "none";
		$j("#download").append($j(iframe));
	});
	
	$j("#confirm").click(function(){//保存动作
		var startWarehouse = $j("#betwenMoveCmd_startWarehouseId").val();
		var endWarehouse = $j("#betwenMoveCmd_endWarehouseId").val();
		if(startWarehouse == ""){
			jumbo.showMsg(i18("CHOOSE_MAIN_WAREEHOUSE"));
			return false;
		}
		if(endWarehouse == ""){
			jumbo.showMsg(i18("CHOOSE_ADDI_WAREEHOUSE"));
			return false;
		}
		if(startWarehouse == endWarehouse){
			jumbo.showMsg(i18("MAIN_ADDI_WAREEHOUSE_NOTEQUAR"));
			return false;
		}
		var startOwner = $j("#selMainShopId").val();
		var endOwner = $j("#selTargetShopId").val();
		if(startOwner == ""){
			jumbo.showMsg("请选择源头店铺");
			return false;
		}
		if(endOwner == ""){
			jumbo.showMsg("请选择目标店铺");
			return false;
		}
		if(startOwner == endOwner){
			jumbo.showMsg("源头店铺和目标店铺不能相同");
			return false;
		}
		if($j("#keyProps").val() == ""){
			jumbo.showMsg("请选择库存状态");
			return false;
		}
		if($j("#tbl-invList tbody").children().length == 1){
			jumbo.showMsg(i18("BETWEENLIBRARY_NOT_NULL"));
			return false;
		}
		if(!window.confirm(i18("CONFIRM_EXEC"))){
			return false;
		}
		
		var postData = {};
		postData = getTableDate();
		postData["comd.startWarehouseId"] = startWarehouse;
		postData["comd.endWarehouseId"] = endWarehouse;
		postData["comd.owner"] = startOwner;
		postData["comd.addOwner"] = endOwner;
		postData["comd.invStatusId"] = $j("#keyProps").val();
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/createVMIFlittingSta.json",postData);
		if(rs && rs.msg == 'success'){
			//执行成功
			$j("#tbl-invList tr[id]").remove();
			$j("#file").val(""); 
			jumbo.showMsg(i18("SAVE_SUCCE"));
		}else{
			if(rs.errMsg != null){
			var msg = rs.errMsg;
			var s = '';
			for(var x in msg){
				s +=msg[x] + '<br/>';
			}
				jumbo.showMsg(s);
			}else{
				jumbo.showMsg(rs.msg);
			}
		}
	});
	
	
});