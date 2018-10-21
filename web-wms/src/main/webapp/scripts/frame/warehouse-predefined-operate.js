
$j.extend(loxia.regional['zh-CN'],{
	CODE_INSTALL_ERROR_BARCODE : "行{0}:设置商品条形码或商品编码或商品编码+扩展属性！",
	CODE_INSTALL_ERROR_KEYPR : "行{0}:设置商品编码！",
	LOCATION_NOT_BIND_TRANTYPE : "输入的库位  {0} 未绑定选中作业类型",
	DATA_ERROR : "数据错误",	
	LOCATION_NOT_FOUNDED : "输入的库位  {0} 系统不存在",
	SKU_NOTFOUNDED_PROD : "输入的商品条形码   {0} 未找到对应商品信息",
	JMCODE_NOT_FOUNDED_SKU: "请填写商品编码与扩展属性或商品条形码",
	SELECT_INNER_SHOP: "请选择店铺",
	SELECT_TRANSACTION_TYPE : "请选择作业类型",
	INPUT_TRANSACTION_TYPE : "请添加作业类型数据", 
	SELECT_INV_STATUS : "请选择库存状态",    
	INPUT_BARCODE_JMCODE_KEPPROPERTIES : "请输入商品条形码或商品编码与扩展属性",   
	INNER_BOUND : "入库",
	OUT_BOUND : "出库",
	NOT_FOUNDED_SKU : "未找到商品信息",
	INPUT_JMCODE : "请填写对应商品编码",
	NOT_FOUNDED_SKU : "未找到商品信息",
	JMCODE_NOT_UNIQUE : "商品编码不是唯一，填写对应扩展属性",
	JMCODE_NOT_FOUNDED_SKU : "请填写商品编码与扩展属性或商品条形码",
	SUCCESS : "操作成功",
	EXECUTE_OR_NOT : "是否确定执行？",
	SAVE_SUCCESS : "保存成功"
});


function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function showMsg(msg){
	jumbo.showMsg(msg);
}

function checkbarcode(value, obj){
		var tr = obj.element.parents("tr");
		if(!$j.trim(value)){
			return loxia.SUCCESS;
		}else{
			 var postData = {};
			 postData["barcode"] = value;
			 var baseUrl = $j("body").attr("contextpath");
			 var rs = loxia.syncXhrPost(baseUrl+"/json/findSkuInfoByParameter.json", postData);
			 if(rs.result == "success"){
				 tr.find("input[name='staLineCmd.skuId']").val(rs.sku.id);
			}else{
				return i18("SKU_NOTFOUNDED_PROD",value); // "输入的商品条形码"+value+"，未找对应商品信息。";
			}
		}
		var dicection = $j("#dircetion").val();
		var opTypeId = $j("#opType").val();
		return loxia.SUCCESS;
}

function checkjmcode(value, obj){
	var tr = obj.element.parents("tr");
	var keyProperties = tr.find("input[name='staLineCmd.keyProperties']").val();
	if(!$j.trim(value) && $j.trim(keyProperties)){
		return i18("JMCODE_NOT_FOUNDED_SKU");// "请填写商品编码与扩展属性或商品条形码";
	}else if(!$j.trim(value)){
		return loxia.SUCCESS;
	}else{
		 var postData = {};
		 postData["jmcode"] = value;
		 if($j.trim(keyProperties)){
			 postData["keyProperties"] = keyProperties;
			 loxia.byId(tr.find("input[name='staLineCmd.keyProperties']")).val(keyProperties);
		 }
		 var baseUrl = $j("body").attr("contextpath");
		 //是否有重复值
		 var rs = loxia.syncXhrPost(baseUrl+"/json/findCountSkuByParameter.json", postData);
		 if(rs.result == "success"){
			 if(rs.count>1) return i18("JMCODE_NOT_UNIQUE")// "商品编码不是唯一，填写对应扩展属性";
		 }
		 //获取对应SKU
		var rs = loxia.syncXhrPost(baseUrl+"/json/findSkuInfoByParameter.json", postData);
	    if(rs.result == "success"){
			 tr.find("input[name='staLineCmd.skuId']").val(rs.sku.id);
			 var dicection = $j("#dircetion").val();
			 var opTypeId = $j("#opType").val();
		}else{
			return i18("NOT_FOUNDED_SKU");// "未找到商品信息";
		}
	}
	return loxia.SUCCESS;
}

function checkKeyProperties(value, obj){
	var tr = obj.element.parents("tr");
	var jmcode = tr.find("input[name='staLineCmd.jmCode']").val();
	if(!$j.trim(value)){
		return loxia.SUCCESS;
	}else if(!$j.trim(jmcode)){
		return i18("INPUT_JMCODE")// "请填写对应商品编码";
	}else{
		 loxia.byId(tr.find("input[name='staLineCmd.jmCode']")).state(true);
		 var postData = {};
		 postData["jmcode"] = jmcode;
		 postData["keyProperties"] = value;
		 var baseUrl = $j("body").attr("contextpath");
		 var rs = loxia.syncXhrPost(baseUrl+"/findSkuInfoByParameter.json", postData);
		 if(rs.result == "success"){
			 tr.find("input[name='staLineCmd.skuId']").val(rs.sku.id);
		}else{
			return i18("NOT_FOUNDED_SKU"); //"未找到商品信息";
		}
	}
	return loxia.SUCCESS;
}

function getFormDate(form){
	var postDate = {};
	$j(form + " table tbody tr").each(function (i,tr){
		postDate["staLineCmd["+i+"].skuId"] = $j(tr).find("input[name='staLineCmd.skuId']").val();
		var skuCost = $j(tr).find("input[name='staLineCmd.skuCost']").val();
		if($j.trim(skuCost) != ""){
			postDate["staLineCmd["+i+"].skuCost"] = $j(tr).find("input[name='staLineCmd.skuCost']").val();
		}
		postDate["staLineCmd["+i+"].quantity"] = $j(tr).find("input[name='staLineCmd.quantity']").val();
		postDate["staLineCmd["+i+"].invStatus.id"] = $j(tr).find("select[name='staLineCmd.invStatus.id']").val();		
	});
	return postDate;
}


function showErrorRow(div){
	var isError = false;
	var msg = "";
	$j(div + " table tbody tr").each(function(i,tr){
		if(!isError && $j(tr).find("input.ui-loxia-error").length > 0){
			isError = true;
		}
		if(!isError && $j(tr).find("input[name='staLineCmd.barCode']").val() == "" && $j(tr).find("input[name='staLineCmd.jmCode']").val() == ""){
			msg = i18("INPUT_BARCODE_JMCODE_KEPPROPERTIES"); // "请输入商品条形码或商品编码与扩展属性";
			isError = true;
		}
		if(!isError && $j(tr).find("select[name='staLineCmd.invStatus.id']").val() == "" ){
			msg = i18("SELECT_INV_STATUS");// "请选择库存状态";
			isError = true;
		}
		if(isError){
			$j(tr).addClass("error");
		}else{
			$j(tr).removeClass("error");
		}
	});
	
	if($j(div + " table tbody").children().length == 0){
		msg = i18("INPUT_TRANSACTION_TYPE"); // "请添加作业类型数据";
		isError = true;
	}
	if(isError){
		jumbo.showMsg(msg);
	}
	return isError;
}

$j(document).ready(function (){
	$j("#tabs").tabs();
	
	initShopQuery("selShopId","innerShopCode");
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	
	jumbo.loadShopList("selShopId");
	
	$j("#confirm").click(function(){//保存动作
		if($j("#opType").val() == ""){
			jumbo.showMsg(i18("SELECT_TRANSACTION_TYPE")); //请选择作业类型
			return false;
		}
		if($j("#selShopId").val() == ""){
			jumbo.showMsg(i18("SELECT_INNER_SHOP")); //请选择店铺
			return false;
		}
		//入库
		var errors=loxia.validateForm("op_form");
		isError = showErrorRow("#edittable");
		if(errors.length>0){
			jumbo.showMsg(errors);
			return false;
		}
		if(isError){
			return false;
		}
		if(!window.confirm(i18("EXECUTE_OR_NOT"))){ // 您确定要执行？ 
			return false;
		}
		var postData = {};

		postData = getFormDate("#op_form");
		postData["sta.owner"] = $j("#selShopId").val();
		postData["sta.memo"] = $j("#txtRmk").val();
		postData["transactionType"] = $j("#opType").val();
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/createPredefinedSta.json",postData);
		
		if(rs && rs.msg == 'success'){
			//执行成功
			$j("#edittable tbody tr").remove();
			$j("#edittable button[action='add']").trigger("click");
			$j("#opType").val("");
			$j("#selShopId").val("");
			$j("#txtRmk").val("");
			jumbo.showMsg(i18("SAVE_SUCCESS")); // 保存成功！ 
		}else{
			jumbo.showMsg(rs.msg);
		}
	});
	
	$j("#import").click(function(){
		var type = $j("#opType").val();
		var invStatus = $j("#invStatus").val();
		var owner = $j("#selShopId").val();
		if(type == ""){
			jumbo.showMsg(i18("SELECT_TRANSACTION_TYPE")); //请选择作业类型
			return false;
		}
		if(invStatus == ""){
			jumbo.showMsg("请选择库存状态"); //请选择库存状态
			return false;
		}
		if(owner == ""){
			jumbo.showMsg(i18("SELECT_INNER_SHOP")); //请选择店铺
			return false;
		}
		if($j("#file").val() == ""){
			jumbo.showMsg("请选择上传文件"); //请选择作业类型
			return false;
		}
		$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/prodefinedStaCreateImport.do?transactionType="+type+"&invStatus="+invStatus+"&owner="+owner);
		$j("#importForm").submit();
		loxia.lockPage();
	});
	$j("#edittable button[action='add']").trigger("click");
});

function importInit(){
	$j("#file").val("");
	$j("#importMemo").val("");
}

function getSnsData(){
	var sns=[];
	$j.each($j(window.frames["snsupload"].document).find(".sns"),function(i,e){
		sns.push($j(e).val());
	});
	return sns.join(";");
}