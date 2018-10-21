$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件"
});
 
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function showMsg(msg){
	jumbo.showMsg(msg);
}

function getFormDate(tb){
	/*var postDate = {};
	$j(tb + " tbody tr:gt(0)").each(function (i,tr){
		postDate["icclist["+i+"].paramId"] =  $j(tr).find("td:eq(2)").html();
		postDate["icclist["+i+"].lineType"] = $j(tr).find("td:eq(3)").html();
	});	 
	postDate["remark"] = $j("#txtRmk").val();
	return postDate;*/
}

function clear(){
	$j("#owner").val("");
	$j("#file").val("");
}
var pumaFlag = false;

$j(document).ready(function (){
	jumbo.loadShopList("owner");
	
	//initShopQuery("companyshop","innerShopCode");
	initShopQuery("owner","innerShopCode");
	initEspritToLocGLN();
	
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	$j("#toLocation").addClass("hidden");
	$j("#toLocationName").addClass("hidden");
	$j("#orderCode").addClass("hidden");
	$j("#pumatip").addClass("hidden");
	$j("#pumaOrderCodeDiv").addClass("hidden");
	$j("#orderCodeLabel").addClass("hidden");
	
	
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getChooseOptionByCode.do",{"categoryCode":"converseRTVReason"});
	for(var idx in result){
		$j("<option value='" + result[idx].optionKey + "'>"+ result[idx].optionValue +"</option>").appendTo($j("#reasonCode"));
	}
	
	
	$j("#returnType").change(function(){
		var channel = $j("#owner").val();
		var type = $j("#returnType").val();
		if (null != channel &&(channel != "5ESPRIT EC总仓店" && channel != "1esprit官方旗舰店" && channel != "1ESPRIT官方购物网站") && 102 == type){
			$j("#toLocation").removeClass("hidden");
			$j("#toLocationName").removeClass("hidden");
			$j("#espritLocGLNDiv").addClass("hidden");
			$j("#zdtcerror").removeClass("hidden");
			//$j("#selectTd").removeClass("hidden");
			$j("#espritTransferFlag").val(false);
			$j("#selectFreightMode").removeClass("hidden");
		}else if(null != channel &&(channel == "5ESPRIT EC总仓店" || channel == "1esprit官方旗舰店" || channel == "1ESPRIT官方购物网站") && 102 == type){
			$j("#toLocation").addClass("hidden");
			$j("#toLocationName").removeClass("hidden");
			$j("#espritLocGLNDiv").removeClass("hidden");
			$j("#zdtcerror").removeClass("hidden");
			//$j("#selectTd").removeClass("hidden");
			$j("#espritTransferFlag").val(true);
			$j("#selectFreightMode").removeClass("hidden");
		}else {
			$j("#toLocation").addClass("hidden");
			$j("#toLocationName").addClass("hidden");
			$j("#espritLocGLNDiv").addClass("hidden");
			$j("#pumaOrderCodeDiv").addClass("hidden");
			$j("#zdtcerror").addClass("hidden");
			$j("#selectTd").addClass("hidden");
			$j("#espritTransferFlag").val(false);
			$j("#selectFreightMode").addClass("hidden");
			$j("#selectFreightMode option:first").attr("selected","selected");
		}
		// 店铺是Nike时的处理
		if (null != channel && ("1Nike官方旗舰店" == channel 
				|| "1NIKE中国官方商城" == channel || "1(UAT)NIKE店铺" == channel 
				|| "香港nike中国官方商城" == channel || "1NIKE-GLOBLE官方旗舰店" == channel
				|| "5Nike-Global Swoosh 官方商城" == channel || "2Nike-Global Swoosh 官方商城" == channel
				|| "3Nike-Global Swoosh 官方商城" == channel || "Nike-Global Inline 官方商城" == channel ||"5Nike-Global Inline官方商城" == channel)) {
			$j("#toLocation").removeClass("hidden");
			$j("#toLocationName").removeClass("hidden");
			$j("#espritLocGLNDiv").addClass("hidden");
			$j("#espritTransferFlag").val(false);
			$j("#selectFreightMode").removeClass("hidden");
		}
		if('' == channel || null == channel){
			$j("#orderCodeLabel").addClass("hidden");
			$j("#orderCode").addClass("hidden");
			$j("#pumatip").addClass("hidden");
			pumaFlag = false;
			return;
		}
		var ch = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getBiChannelByCode.json",{"biChannel.code":channel});//渠道信息
		if(ch['vmiCode'] == 'puma' && 101 == type){
			//$j("#orderCodeLabel").removeClass("hidden");
			$j("#orderCode").removeClass("hidden");
			$j("#pumatip").removeClass("hidden");
			$j("#pumaOrderCodeDiv").removeClass("hidden");
			$j("#orderCodeLabel").addClass("hidden");
			initPumaToOrderCode();
			pumaFlag = true;
		}else{
			$j("#orderCodeLabel").addClass("hidden");
			$j("#orderCode").addClass("hidden");
			$j("#pumatip").addClass("hidden");
			$j("#pumaOrderCodeDiv").addClass("hidden");
			pumaFlag = false;
		}
	});
	
	$j("#freightMode").change(function(){
		
		var type = $j("#freightMode").val();
		if(20==type){
			$j("#selectTd").removeClass("hidden");
		}else{
			$j("#selectTd").addClass("hidden");
		}
		
	});
	
	$j("#importpl").click(function(){//批量导入
		var owner = $j("#owner").val();
		var returnType = $j("#returnType").val();
		var freightMode = $j("#freightMode").val();
		if(owner == ''){
			 alert("请选择店铺");
			 return;
		}
		 
		if(returnType == ''){
			alert("请选择退仓类型");
			return;
		}
		if(returnType != '101'){
			alert("请选择退大仓类型");
			return;
		}
		if(freightMode == '20'){
			alert("收货方式请选择上门自取类型");
			return;
		}

		$j("#importplForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/vmireturnplimport.do?owner="+owner)
		);
			loxia.submitForm("importplForm");
	});
		
	$j("#import").click(function(){
		var activitySource = $j("#activitySource").val().trim();
		var owner = $j("#owner").val(); 
		var reasonCode = $j("#reasonCode").val(); 
		var slipCode1 = $j("#slipCode1").val(); 
		var returnType = $j("#returnType").val();
		var toLocation = null;
		var flag = $j("#espritTransferFlag").val();
		var espritChannel = $j("#espritChannelFlag").val();
		if("true"  == flag){
			toLocation = $j.trim($j("#espritLocGLN").val());
			if(102 != returnType){
				flag = false;
			}
		}else{
			toLocation = $j.trim($j("#toLocation").val());
		}
		var _p = $j.trim($j("#province").val()); 
		var _c = $j.trim($j("#city").val());
		var _d = $j.trim($j("#district").val());
		var _u = $j.trim($j("#username").val());
		var _a = $j.trim($j("#address").val());
		var _t = $j.trim($j("#telphone").val());
		var freightMode= "";
		var lpcode = "";
		var orderCode = $j.trim($j("#orderCode").val());
		var pumaRtoOrderCode=$j.trim($j("#pumaRtoOrderCode").val());
		var errs = [];
		if(true == pumaFlag && ('' == pumaRtoOrderCode || null == pumaRtoOrderCode)){
			errs.push("PUMA必须输入指令单号");
			//return false;
		}else if(true == pumaFlag){
			orderCode=pumaRtoOrderCode;
		}
		if(returnType == 102){
			freightMode= $j("#freightMode").val();
			if(freightMode==20){
				lpcode = $j("#select").val();
				if(""==lpcode||lpcode==null){
					jumbo.showMsg("请选择物流商！");
					return false;
				}
			}
		}
		var imperfectType = $j("#imperfectType").val(); 
		if(owner == ''){
			errs.push("请选择店铺");
		}
		if(returnType == ''){
			errs.push("请选择退仓类型");
		}
		var channel = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getChannelVmiCode.do",{"code":owner});//渠道信息
		if(channel['vmiCode'] == '5751' && returnType == 102){
			errs.push(owner+" 店铺不支持VMI转店退仓");
		}
		if (returnType == 102 && !toLocation){
			errs.push("请输入VMI入库目标地址");
		}
		if (returnType == 102 && !_p){
			errs.push("请输入省");
		}
		if (returnType == 102 && !_c){
			errs.push("请输入市");
		}
		if (returnType == 102 && !_d){
			errs.push("请输入区");
		}
		if (returnType == 102 && !_u){
			errs.push("请输入联系人");
		}
		if (returnType == 102 && !_a){
			errs.push("请输入联系地址");
		}
		if (returnType == 102 && !_t){
			errs.push("请输入联系电话");
		}
		if("true" == espritChannelFlag){
			if(102 != returnType){
				flag = "false";
			}
		}
		//CONVERSE必须填写
		if(owner == '1CONVERSE官方商城' && reasonCode == ''){
			errs.push("CONVERSE必须填写调整原因");
		}
		if(owner == '1CONVERSE官方商城' && slipCode1 == ''){
			errs.push("CONVERSE必须填写退货原因编码");
		}
		var shop = $j("#owner").val();
		if(("1Nike官方旗舰店" == shop || "1NIKE中国官方商城" == shop || "1(UAT)NIKE店铺" == shop
				|| "香港nike中国官方商城" == shop || "1NIKE-GLOBLE官方旗舰店" == shop || "5Nike-Global Swoosh 官方商城" == shop 
				|| "2Nike-Global Swoosh 官方商城" == shop || "3Nike-Global Swoosh 官方商城" == shop || "Nike-Global Inline 官方商城" == shop || "5Nike-Global Inline官方商城" == shop) 
				&& toLocation == ""){
			errs.push("Nike品牌必须填写入库目标地址");
		}
		if(!/^.*\.xls$/.test($j("#file").val())){
			errs.push(i18("CORRECT_FILE_PLEASE"));
		}
		if(errs.length>0) {
			jumbo.showMsg(errs);
			return;
		}
		var _url = "innerShopCode="+owner+"&intType="+returnType + "&toLocation="+toLocation + "&province=" + _p + "&city=" + _c  +"&imperfectType="+ imperfectType +
		 	"&district=" + _d + "&receiver=" + _u + "&address=" + _a + "&telphone=" + _t+"&slipCode1="+slipCode1+"&espritFlag="+espritChannel+"&espritTransferFlag="+flag+"&lpCode="+lpcode
		 	+"&activitySource="+activitySource+"&freightMode="+freightMode + "&orderCode=" + orderCode;
		loxia.lockPage();
		/*$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/vmireturnimport.do?" +
						"innerShopCode="+owner+"&intType="+returnType + "&toLocation="+toLocation));*/
			$j("#importForm").attr("action",
					loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/vmireturnimport.do?" + _url+"&reasonCode="+reasonCode)
			);
		loxia.submitForm("importForm");
		/**
		$j("#importForm").submit();**/
	});
	$j("#owner").change(function(){
		var channel = $j("#owner").val();
		console.log(channel);
		var type = $j("#returnType").val();
		if (null != channel &&(channel != "5ESPRIT EC总仓店" && channel != "1esprit官方旗舰店" && channel != "1ESPRIT官方购物网站") && 102 == type){
			$j("#toLocation").removeClass("hidden");
			$j("#toLocationName").removeClass("hidden");
			$j("#espritLocGLNDiv").addClass("hidden");
			$j("#espritTransferFlag").val(false);
		}else if(null != channel &&(channel == "5ESPRIT EC总仓店" || channel == "1esprit官方旗舰店" || channel == "1ESPRIT官方购物网站") && 102 == type){
			$j("#toLocation").addClass("hidden");
			$j("#toLocationName").removeClass("hidden");
			$j("#espritLocGLNDiv").removeClass("hidden");
			$j("#espritTransferFlag").val(true);
		}else {
			$j("#toLocation").addClass("hidden");
			$j("#toLocationName").addClass("hidden");
			$j("#espritLocGLNDiv").addClass("hidden");
			$j("#imperfectTypeName").addClass("hidden");
			$j("#imperfectType").addClass("hidden");
			$j("#espritTransferFlag").val(false);
		}
		// 店铺是Nike时的处理
		if (null != channel && ("1Nike官方旗舰店" == channel 
				|| "1NIKE中国官方商城" == channel || "1(UAT)NIKE店铺" == channel || "5香港NIKE官方商城" == channel 
				|| "香港nike中国官方商城" == channel || "1NIKE-GLOBLE官方旗舰店" == channel
				|| "5Nike-Global Swoosh 官方商城" == channel || "2Nike-Global Swoosh 官方商城" == channel
				|| "3Nike-Global Swoosh 官方商城" == channel || "Nike-Global Inline 官方商城" == channel|| "5Nike-Global Inline官方商城" == channel)) {
			$j("#toLocation").removeClass("hidden");
			$j("#toLocationName").removeClass("hidden");
			$j("#espritLocGLNDiv").addClass("hidden");
			$j("#espritTransferFlag").val(false);
			$j("#selectFreightMode").removeClass("hidden");
			$j("#imperfectTypeName").removeClass("hidden");
			$j("#imperfectType").removeClass("hidden");
			$j("#pumaOrderCodeDiv").addClass("hidden");
			
			
		}
		if(null != channel &&(channel == "5ESPRIT EC总仓店" || channel == "1esprit官方旗舰店" || channel == "1ESPRIT官方购物网站")){
			$j("#espritChannelFlag").val(true);
		}else {
			$j("#espritChannelFlag").val(false);
		}
		if('' == channel || null == channel){
			$j("#orderCodeLabel").addClass("hidden");
			$j("#orderCode").addClass("hidden");
			$j("#pumatip").addClass("hidden");
			$j("#pumaOrderCode").addClass("hidden");
			pumaFlag = false;
			return;
		}
		var ch = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getBiChannelByCode.json",{"biChannel.code":channel});//渠道信息
		if(ch['vmiCode'] == 'puma' && 101 == type){
			$j("#orderCodeLabel").removeClass("hidden");
			//$j("#orderCode").removeClass("hidden");
			$j("#pumatip").removeClass("hidden");
			$j("#pumaRtoOrderCode").val("");
			initPumaToOrderCode();
			$j("#pumaOrderCodeDiv").removeClass("hidden");
			pumaFlag = true;
		}else{
			$j("#orderCodeLabel").addClass("hidden");
			$j("#orderCode").addClass("hidden");
			$j("#pumatip").addClass("hidden");
			$j("#pumaOrderCodeDiv").addClass("hidden");
			pumaFlag = false;
		}
	});
});

function initEspritToLocGLN(){
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getChooseOptionByCode.do",{"categoryCode":"espritToLocGLN"});
	$j("#espritLocGLN").append("<option></option>");
	for(var idx in result){
		$j("<option value='" + result[idx].optionKey + "'>"+ result[idx].optionValue +"</option>").appendTo($j("#espritLocGLN"));
	}
	$j("#espritLocGLN").flexselect();
    $j("#espritLocGLN").val("");
};

function initPumaToOrderCode(){
	var ch = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/initPumaToOrderCode.json");
	$j("#pumaRtoOrderCode").append("<option></option>");
	for(var idx in ch){
		$j("<option value='" + ch[idx].orderCode + "'>"+ ch[idx].orderCode +"</option>").appendTo($j("#pumaRtoOrderCode"));
	}
	$j("#pumaRtoOrderCode").flexselect();
    $j("#pumaRtoOrderCode").val("");
}