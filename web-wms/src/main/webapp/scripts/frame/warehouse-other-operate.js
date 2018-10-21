
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
	SAVE_SUCCESS : "保存成功",
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件!"
});


function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function showMsg(msg){
	jumbo.showMsg(msg);
}


function checklocationCode(value,obj){
	var tr = obj.element.parents("tr");
	if(!$j.trim(value)){
		return loxia.SUCCESS;
	}else{
		 var postData = {};
		 postData["locationCode"] = value;
		 var baseUrl = $j("body").attr("contextpath");
		 var rs = loxia.syncXhrPost(baseUrl+"/findlocationbycode.json", postData);
		 if(rs){
			if(rs.result == "success"){
				tr.find("input[name='stvLineCmd.location.id']").val(rs.location.id);
				//此库位是否有选择作业类型
                var opTypeId = $j("#opType").val();
                var postData = {};
                 postData["locationId"] = rs.location.id;
                 postData["transactionId"] = opTypeId;
                 var baseUrl = $j("body").attr("contextpath");
                 var rs = loxia.syncXhrPost(baseUrl+"/findLocationTranstype.json", postData);
                 if (rs.result == 'error')return i18("LOCATION_NOT_BIND_TRANTYPE",value); // "输入的库位 ' "+value+" '， 未绑定选中作业类型";
                 
                 var dicection = $j("#dircetion").val();
                 var opTypeId = $j("#opType").val();
                 if(dicection == 2){
          			var locationCode = $j.trim(tr.find("input[name='stvLineCmd.locationCode']").val());
          			var skuid = tr.find("input[name='stvLineCmd.skuId']").val();
          			if (skuid != '') loadinvStatuss(tr,opTypeId,locationCode,skuid);
          		}
			}else if (rs.result == 'error'){
				return i18("LOCATION_NOT_FOUNDED", value) //"输入的库位 ' "+value+" '  系统不存在";
			}else{
				return i18("DATA_ERROR"); // 数据错误
			}
		 }
	}
	return loxia.SUCCESS;
}

function checkbarcode(value, obj){
		var tr = obj.element.parents("tr");
		if(!$j.trim(value)){
			return loxia.SUCCESS;
		}else{
			 var postData = {};
			 postData["barcode"] = value;
			 var baseUrl = $j("body").attr("contextpath");
			 var rs = loxia.syncXhrPost(baseUrl+"/json/findSkuByParameter.json", postData);
			 if(rs.result == "success"){
				 tr.find("input[name='stvLineCmd.skuId']").val(rs.sku.id);
			}else{
				return i18("SKU_NOTFOUNDED_PROD",value); // "输入的商品条形码"+value+"，未找对应商品信息。";
			}
		}
		var dicection = $j("#dircetion").val();
		var opTypeId = $j("#opType").val();
		if(dicection == 2){
			var locationCode = $j.trim(tr.find("input[name='stvLineCmd.locationCode']").val());
			var skuid = tr.find("input[name='stvLineCmd.skuId']").val();
			if (locationCode != '') loadinvStatuss(tr,opTypeId,locationCode,skuid);
		}
		return loxia.SUCCESS;
}

function loadinvStatuss(tr,ttid,locCode,skuId){
	if(tr != '') tr.find("select[name='stvLineCmd.invStatus.id'] option:gt(0)").remove();
	var owner = $j("#selShopId").val();
	if(ttid == ''||locCode == ''||skuId == '' || owner == ''){
		return loxia.SUCCESS;
	}else{
			var postData = {};
			var barcode = $j.trim(tr.find("input[name='stvLineCmd.barCode']").val());
			var jmcode = $j.trim(tr.find("input[name='stvLineCmd.jmCode']").val())
			var keyProperties = $j.trim(tr.find("input[name='stvLineCmd.keyProperties']").val())
			
			if(ttid !='') postData["transactionId"] = ttid;
			if(locCode !='') postData["locationCode"] = locCode;
			if(skuId !='') postData["skuId"] = skuId;
			if(owner != '') postData["owner"] = owner;
			if(barcode != '')  postData["barcode"] = barcode;
			if(jmcode != '')  postData["jmcode"] = jmcode;
			if(keyProperties != '')  postData["keyProperties"] = keyProperties;
			
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/getInvStatusByInventory.json",postData);
			if(rs && rs.result == "success"){
				for(var idx in rs.list){
					$j('<option value="'+ rs.list[idx].id+'">' + rs.list[idx].name + '</option>').appendTo(tr.find("select[name='stvLineCmd.invStatus.id']"));
				}
				tr.find("select[name='stvLineCmd.invStatus.id']").removeClass("hidden");
				return loxia.SUCCESS;
			}else{
				jumbo.showMsg(rs.message);
				return ;
			}
	}
}

function checkSameRow(tr,locCode,skuId){
	var errMsg = [];
	if($j("#edittable1 table tbody:eq(0)").find("tr").length <= 1) return loxia.SUCCESS;
	$j("#edittable1 table tbody:eq(0)").find("tr").each(function(idx){
		var $td1 = $j("td:eq(1) input", this).val();
		var $td2 = $j("td:eq(2) input",this).val();
		var $td3 = $j("td:eq(3) input", this).val();
		
		if($j.trim($td1)=='' && ($j.trim($td2)=='' || $j.trim($td3) =='')){
			/*errMsg.push(loxia.getLocaleMsg("CODE_INSTALL_ERROR_BARCODE",(idx+1)));//行"+(idx+1)+":设置商品条形码或商品编码或商品编码+扩展属性！"
			$j(this).addClass("error");*/
			return loxia.SUCCESS;
		}else if( $j.trim($td3.val()) == "" && $j.trim($td4.val()) != ""){
			errMsg.push(loxia.getLocaleMsg("CODE_INSTALL_ERROR_KEYPR",(idx+1))); 
			$j(this).addClass("error");
		}
	});
	if(errMsg.length > 0){
		jumbo.showMsg(errMsg);
	}
}

function checkjmcode(value, obj){
	var tr = obj.element.parents("tr");
	var keyProperties = tr.find("input[name='stvLineCmd.keyProperties']").val();
	if(!$j.trim(value) && $j.trim(keyProperties)){
		return i18("JMCODE_NOT_FOUNDED_SKU");// "请填写商品编码与扩展属性或商品条形码";
	}else if(!$j.trim(value)){
		return loxia.SUCCESS;
	}else{
		 var postData = {};
		 postData["jmcode"] = value;
		 if($j.trim(keyProperties)){
			 postData["keyProperties"] = keyProperties;
			 loxia.byId(tr.find("input[name='stvLineCmd.keyProperties']")).val(keyProperties);
		 }
		 var baseUrl = $j("body").attr("contextpath");
		 //是否有重复值
		 var rs = loxia.syncXhrPost(baseUrl+"/json/findCountSkuByParameter.json", postData);
		 if(rs.result == "success"){
			 if(rs.count>1) return i18("JMCODE_NOT_UNIQUE")// "商品编码不是唯一，填写对应扩展属性";
		 }
		 //获取对应SKU
		var rs = loxia.syncXhrPost(baseUrl+"/json/findSkuByParameter.json", postData);
	    if(rs.result == "success"){
			 tr.find("input[name='stvLineCmd.skuId']").val(rs.sku.id);
			 var dicection = $j("#dircetion").val();
			 var opTypeId = $j("#opType").val();
			 if(dicection == 2){
			 	var locationCode = $j.trim(tr.find("input[name='stvLineCmd.locationCode']").val());
				var skuid = tr.find("input[name='stvLineCmd.skuId']").val();
				if (locationCode != '') loadinvStatuss(tr,opTypeId,locationCode,skuid);
			 }
		}else{
			return i18("NOT_FOUNDED_SKU");// "未找到商品信息";
		}
	}
	return loxia.SUCCESS;
}

function checkKeyProperties(value, obj){
	var tr = obj.element.parents("tr");
	var jmcode = tr.find("input[name='stvLineCmd.jmCode']").val();
	if(!$j.trim(value)){
		return loxia.SUCCESS;
	}else if(!$j.trim(jmcode)){
		return i18("INPUT_JMCODE")// "请填写对应商品编码";
	}else{
		 loxia.byId(tr.find("input[name='stvLineCmd.jmCode']")).state(true);
		 var postData = {};
		 postData["jmcode"] = jmcode;
		 postData["keyProperties"] = value;
		 var baseUrl = $j("body").attr("contextpath");
		 var rs = loxia.syncXhrPost(baseUrl+"/findSkuByParameter.json", postData);
		 if(rs.result == "success"){
			 tr.find("input[name='stvLineCmd.skuId']").val(rs.sku.id);
		}else{
			return i18("NOT_FOUNDED_SKU"); //"未找到商品信息";
		}
	}
	return loxia.SUCCESS;
}

/**
 * 获取执行动作 1入库 2出库
 * @return
 */
function syncOpType(opTypeId){
	var dicection = "";
	
	if($j.trim(opTypeId)){
	   var postData = {};
	   postData["transactionId"] = opTypeId;
	   var baseUrl = $j("body").attr("contextpath");
	   try{
	   var rs = loxia.syncXhrPost(baseUrl+"/getTransactionType.json", postData);
	   if(rs.result == "success"){
		   dicection = rs.opType.intDirection;
		}else{
			jumbo.showMsg(rs.message);
		//throw new Error('arguments are not numbers');
		}
	   }catch(e){
		   jumbo.showMsg("error sync optype");
	   }
	}
	return dicection;
}

function initForm(dicection){
	if(dicection == 1){
		$j("#divOp").removeClass("hidden");
		$j("#divOp1").addClass("hidden");
		$j("#btnList").removeClass("hidden");
		$j("#tblOwner").removeClass("hidden");
		$j("#divRemork").removeClass("hidden");
		$j("#txtRmk").val("");
		removeTableTr("#edittable");
	}else if(dicection == 2){
		$j("#divOp1").removeClass("hidden");
		$j("#divOp").addClass("hidden");
		$j("#btnList").removeClass("hidden");
		$j("#tblOwner").removeClass("hidden");
		$j("#divRemork").removeClass("hidden");
		$j("#txtRmk").val("");
		removeTableTr("#edittable1");
	}else{
		$j("#divOp").addClass("hidden");
		$j("#divOp1").addClass("hidden");
		$j("#btnList").addClass("hidden");
		$j("#tblOwner").removeClass("hidden");
		$j("#divRemork").addClass("hidden");
		$j("#txtRmk").val("");
	}
}

function initDirection(){
	var opTypeId = $j("#opType").val();
	var dicection = syncOpType(opTypeId);//返回作业类型
	var shopid = $j("#selShopId").val();
	$j("#dircetion").val(dicection);
	if(opTypeId == '') {
		$j("#oprType").html("");
		$j("#fromTab tr[ids='Out_bound_address']").addClass("hidden");
	}
	if(dicection == 1){
		$j("#oprType").html(i18("INNER_BOUND")); // 入库 
		if(shopid != ""){
			initForm(dicection);
		}
		$j("#fromTab tr[ids='Out_bound_address']").addClass("hidden");
	}else if(dicection == 2){
		$j("#oprType").html(i18("OUT_BOUND"));  // 出库
		if(shopid != ""){
			initForm(dicection);
		}
		$j("#fromTab tr[ids='Out_bound_address']").removeClass("hidden");
	} 
}
function removeTableTr(table_div){
	$j(table_div + " table tbody tr").each(function (i,tr){
		 $j(tr).remove();
	});

	$j(table_div + " button[action='add']").trigger("click");
	
//	$j("#edittable table tbody tr:eq(0) td:eq(1)").find("input[name='stvLineCmd.locationCode']").attr("aclist","ffffff");
	//$j("#edittable table tbody tr:eq(0) td:eq(1)").find("input[name='stvLineCmd.locationCode']").attr("aclist","ffffff");
//	$j("#edittable").attr("loxiaType","edittable");
//	loxia.initContext($j("#edittable table tbody tr:eq(0) td:eq(1)").find("input[name='stvLineCmd.locationCode']"));
	
}

function initInputblur(){
	$j("input[name='stvLineCmd.locationCode'], input[name='stvLineCmd.barCode'],input[name='stvLineCmd.jmCode'],input[name='stvLineCmd.keyProperties']").blur(function(){
		var tr = $j(this).parents("tr");
		var isOutbound = tr.parents("#edittable,#edittable1").length == 0;
		var ttid = $j.trim($j("#opType").val());
		var locationCode = $j.trim(tr.find("input[name='stvLineCmd.locationCode']").val());
		if(locationCode == "" && isOutbound){
			tr.find("select[name='stvLineCmd.invStatus.id']").addClass("hidden");
			return;
		}
		var barCode = $j.trim(tr.find("input[name='stvLineCmd.barCode']").val());
		if(barCode != ""){
			loxia.byId(tr.find("input[name='stvLineCmd.jmCode']")).val("");
			loxia.byId(tr.find("input[name='stvLineCmd.keyProperties']")).val("");
		}
		var jmCode = $j.trim(tr.find("input[name='stvLineCmd.jmCode']").val());
		var keyProperties = $j.trim(tr.find("input[name='stvLineCmd.keyProperties']").val());
		if(barCode != "" && keyProperties!= ""){
			loxia.byId(tr.find("input[name='stvLineCmd.jmCode']")).val("");
			loxia.byId(tr.find("input[name='stvLineCmd.keyProperties']")).val("");
		}
		if(barCode == "" && jmCode == "" && isOutbound){
			tr.find("select[name='stvLineCmd.invStatus.id']").addClass("hidden");
			return;
		}
	});
}

function getFormDate(form){
	var postDate = {};
	$j(form + " table tbody tr").each(function (i,tr){
		postDate["stvLineCmd["+i+"].locationCode"] = $j(tr).find("input[name='stvLineCmd.locationCode']").val();
		postDate["stvLineCmd["+i+"].skuId"] = $j(tr).find("input[name='stvLineCmd.skuId']").val();
		var skuCost = $j(tr).find("input[name='stvLineCmd.skuCost']").val();
		if($j.trim(skuCost) != ""){
			postDate["stvLineCmd["+i+"].skuCost"] = $j(tr).find("input[name='stvLineCmd.skuCost']").val();
		}
		postDate["stvLineCmd["+i+"].quantity"] = $j(tr).find("input[name='stvLineCmd.quantity']").val();
		postDate["stvLineCmd["+i+"].invStatus.id"] = $j(tr).find("select[name='stvLineCmd.invStatus.id']").val();		
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
		if(!isError && $j(tr).find("input[name='stvLineCmd.barCode']").val() == "" && $j(tr).find("input[name='stvLineCmd.jmCode']").val() == ""){
			msg = i18("INPUT_BARCODE_JMCODE_KEPPROPERTIES"); // "请输入商品条形码或商品编码与扩展属性";
			isError = true;
		}
		if(!isError && $j(tr).find("select[name='stvLineCmd.invStatus.id']").val() == "" ){
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
	
	initInputblur();
	
	$j("#selShopId").change(function(){
		$j("#shopValue").html($j("#selShopId").val());
	});
	
	$j("#opType").change(function(){
		if($j("#opType").val == '') $j("#dircetion").val(""); 
		initDirection();
	});
	$j("#selShopId").change(function(){
		if($j("#selShopId").val() != ""){
			var dircetion = $j("#dircetion").val();
			initForm(dircetion);
			return;
		}else {
			initForm(dircetion);
			return;
		}
	});

	$j("#edittable,#edittable1").bind("rowchanged", function(event,data){
		initInputblur();
	});
	
	/**
	 * 联想查询入库 库位 code
	 * @param {Object} event
	 * @param {Object} data
	 */
	$j("#edittable").bind("rowappended", function(event,data){
		// var url=$j("body").attr("contextpath") + "/findAvailLocationForOther.json?transactionId="+$j("#opType").val();
		var baseUrl = $j("body").attr("contextpath");
		var url = baseUrl + "/findallavaillocations.json";
		var obj=$j("#edittable table tbody:eq(0) tr:last td:eq(1)").find("input[name='stvLineCmd.locationCode']");
		$j(obj).attr("aclist",url);
		$j(obj).attr("loxiaType","input");
		loxia.initLoxiaWidget($j(obj));
	});
	
	/**
	 * 联想查询出库 库位code
	 * @param {Object} event
	 * @param {Object} data
	 */
	$j("#edittable1").bind("rowappended", function(event,data){
		//var url=$j("body").attr("contextpath") + "/findAvailLocationByOwner.json?transactionId="+$j("#opType").val()+"&owner="+$j("#selShopId").val();
		var baseUrl = $j("body").attr("contextpath");
		var url = baseUrl + "/findallavaillocations.json";
		var obj=$j("#edittable1 table tbody:eq(0) tr:last td:eq(1)").find("input[name='stvLineCmd.locationCode']");
		$j(obj).attr("aclist",url);
		$j(obj).attr("loxiaType","input");
		loxia.initLoxiaWidget($j(obj));
	});
	
	$j("#confirm").click(function(){//保存动作
		if($j("#selShopId").val() == ""){
			jumbo.showMsg(i18("SELECT_INNER_SHOP")); //请选择店铺
			return false;
		}
		var isError = false;
		
		var opTypeId = $j("#opType").val();
		if(!$j.trim(opTypeId)){
			jumbo.showMsg(i18("SELECT_TRANSACTION_TYPE"));  // 请选择作业类型
			return false;
		}
		
		var dicectionn = syncOpType(opTypeId);//返回作业类型
		if(dicectionn == 1){
			//入库
			var errors=loxia.validateForm("op_form");
			isError = showErrorRow("#edittable");
			if(errors.length>0){
				jumbo.showMsg(errors);
				return false;
			}
		}else if(dicectionn == 2){
			//出库
			var errors=loxia.validateForm("op_form1");
			isError = showErrorRow("#edittable1");
			if(errors.length>0){
				jumbo.showMsg(errors);
				return false;
			}
		}
		if(isError){
			return false;
		}
		if(!window.confirm(i18("EXECUTE_OR_NOT"))){ // 您确定要执行？ 
			return false;
		}
		var postData = {};
		if(dicectionn == 1){
			//入库
			postData = getFormDate("#op_form");
		}else if(dicectionn == 2){
			//出库
			postData = getFormDate("#op_form1");
			postData["stadelivery.province"]=$j("#province").val();
			postData["stadelivery.city"] = $j("#city").val();
			postData["stadelivery.district"]= $j("#district").val();
			postData["stadelivery.receiver"]= $j("#username").val();
			postData["stadelivery.address"]=  $j("#address").val();
			postData["stadelivery.telephone"]= $j("#telphone").val();
		}
		postData["sta.owner"] = $j("#selShopId").val();
		postData["sta.memo"] = $j("#txtRmk").val();
		postData["transactionId"] = $j("#opType").val();
		postData["execute"] = false; //保存
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/createOthersSta.json",postData);
		
		if(rs && rs.msg == 'success'){
			//执行成功
			$j("#edittable tbody tr,#edittable1 tbody tr").remove();
			$j("#edittable button[action='add'],#edittable1 button[action='add']").trigger("click");
			$j("#opType").val("");
			$j("#selShopId").val("");
			$j("#txtRmk").val("");
			$j("#opType").trigger("change");
			jumbo.showMsg(i18("SAVE_SUCCESS")); // 保存成功！ 
		}else{
			jumbo.showMsg(rs.msg);
		}
	});
	
	$j("#execute").click(function(){//保存并执行
		if($j("#selShopId").val() == ""){
			jumbo.showMsg(i18("SELECT_INNER_SHOP")); // 请选择店铺
			return false;
		}
		var isError = false;
		
		var opTypeId = $j("#opType").val();
		if(!$j.trim(opTypeId)){
			jumbo.showMsg(i18("SELECT_TRANSACTION_TYPE")); // 请选择作业类型
			return false;
		}
		var dicectionn = syncOpType(opTypeId);//返回作业类型
		if(dicectionn == 1){
			//入库
			var errors=loxia.validateForm("op_form");
			isError = showErrorRow("#edittable");
			if(errors.length>0){
				jumbo.showMsg(errors);
				return false;
			}
		}else if(dicectionn == 2){
			//出库
			var errors=loxia.validateForm("op_form1");
			isError = showErrorRow("#edittable1");
			if(errors.length>0){
				jumbo.showMsg(errors);
				return false;
			}
		}
		if(isError){
			return false;
		}
		if(!window.confirm(i18("EXECUTE_OR_NOT"))){  //  您确定要执行？ 
			return false;
		}
		var postData = {};
		if(dicectionn == 1){
			//入库
			postData = getFormDate("#op_form");
		}else if(dicectionn == 2){
			//出库
			postData = getFormDate("#op_form1");
		}
		postData["sta.owner"] = $j("#selShopId").val();
		postData["sta.memo"] = $j("#txtRmk").val();
		postData["transactionId"] = $j("#opType").val();
		postData["execute"] = true; //保存并执行
		postData["sns"]=getSnsData();
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/createOthersSta.json",postData);
		
		if(rs && rs.msg == 'success'){
			//执行成功
			$j("#edittable tbody tr,#edittable1 tbody tr").remove();
			$j("#edittable button[action='add'],#edittable1 button[action='add']").trigger("click");
			$j("#opType").val("");
			$j("#selShopId").val("");
			$j("#txtRmk").val("");
			$j("#opType").trigger("change");
			jumbo.showMsg(i18("SUCCESS")); // 执行成功！
		}else{
			jumbo.showMsg(rs.msg);
		}
	});
	
	$j("#import").click(function(){
		var type = $j("#opType").val();
		var shop = $j("#selShopId").val();
		var slipCode = $j("#staSlipCode").val();
		if(type == ""){ 
			jumbo.showMsg(i18("SELECT_TRANSACTION_TYPE"));
			return false;
		}if(shop == ""){
			jumbo.showMsg(i18("SELECT_INNER_SHOP")); // 请选择店铺
			return false;
			
		}
		var url = $j("body").attr("contextpath") + "/warehouse/otherStaCreateImport.do?owner="+shop+"&transactionId="+type;
		url = url+ "&sta.refSlipCode=" + slipCode;
		if($j("#oprType").html() == i18("OUT_BOUND")){
			url = url+ "&stadelivery.province=" + $j("#province").val();
			url = url+ "&stadelivery.city=" + $j("#city").val();
			url = url+ "&stadelivery.district=" + $j("#district").val();
			url = url+ "&stadelivery.receiver=" + $j("#username").val();
			url = url+ "&stadelivery.address=" + $j("#address").val();
			url = url+ "&stadelivery.telephone=" + $j("#telphone").val();
		}
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));//请选择正确的Excel导入文件
			return false;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",url);
		$j("#importForm").submit();
		loxia.lockPage();
	});
});
function getSnsData(){
	var sns=[];
	$j.each($j(window.frames["snsupload"].document).find(".sns"),function(i,e){
		sns.push($j(e).val());
	});
	return sns.join(";");
}