$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件",
	OPERATE_EXEPTION	:"数据操作异常",
	SUCCESS_INNER :"商品创建成功",
	SLIPCODE_NULL:"请先填写相关单据号，再创商品！"
});
 
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
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
	$j("#tab_2 select").val("");
	$j("#tab_2 input").val("");
}

var isCheck = false;
var vmiCode = "";
$j(document).ready(function (){

	jumbo.loadShopList("owner","id");

	initShopQuery("owner","innerShopCode");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getChooseOptionByCode.do",{"categoryCode":"converseADJReason"});
	for(var idx in result){
		$j("<option value='" + result[idx].optionKey + "'>"+ result[idx].optionValue +"</option>").appendTo($j("#reasonCode"));
	}
	
	$j("#import").click(function(){
		var ownerid = $j("#owner").val(); 
		var slipCode = $j("#slipCode").val(); 
		var reasonCode = $j("#reasonCode").val();
		var errs = [];
		if(ownerid == ''){errs.push("请选择转出店铺");}
		//CONVERSE必须填写
		if(ownerid == '2306' && reasonCode == ''){
			errs.push("CONVERSE必须填写调整原因");
		}
		if(vmiCode == 'puma' && reasonCode == ''){
			errs.push("PUMA必须填写调整原因");
		}
		if(vmiCode == 'SST_TMALL' && '' == reasonCode){
			errs.push("STARBUCKS必须填写调整原因");
		}
		if(errs.length>0) {
			jumbo.showMsg(errs);
			return;
		}
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
			return;
		}
		var postDate = "?ownerid="+ownerid+"&slipCode="+slipCode+"&reasonCode="+reasonCode;
		loxia.lockPage();
		$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/vmioutboundadjustimport.do"+postDate));
		loxia.submitForm("importForm");
	});
	
//	$j("#owner").change(function(){
//		/**
//		var shop = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/json/getShopInfoBy.json?ownerid="+$j("#owner").val());
//		if(shop.isInboundInvoice == true){
//			isInboundInvoice = 1;
//			$j("#invoice_POtype1").removeClass("hidden");
//			$j("#invoice_POtype2").removeClass("hidden");
//			$j("#invoiceNumber").val("");
//			initInvoicePOtype();
//		} else {
//			isInboundInvoice = 0;
//			$j("#invoice_POtype1").addClass("hidden");
//			$j("#invoice_POtype2").addClass("hidden");
//			$j("#invoiceNumber").val("");
//			initInvoicePOtype();
//		}
//		**/
//	});
	
	
	$j("#createsku").click(function(){
		var poNum = $j("#slipCode").val(); 
		var ownerid = $j("#owner").val(); 
		var errs = [];
		if(ownerid == ''){errs.push("请选择转出店铺");}
		if(poNum == null || poNum == ''){
			errs.push(i18("SLIPCODE_NULL"));
		}
		if(errs.length>0) {
			jumbo.showMsg(errs);
			return;
		}
		var postData = {};
		
		loxia.lockPage();
		
		
		loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/generateSKU.json?slipCode="+poNum+"&ownerid="+ownerid,
				postData,
				{
						success:function(data){
						if(data){
							if(data.result=="success"){
								jumbo.showMsg(i18("SUCCESS_INNER"));
							}else if(data.result=="error"){
								jumbo.showMsg(data.message);
							}
						} else {
							jumbo.showMsg(i18("OPERATE_EXEPTION"));
						}
						},
						error:function(data){
						jumbo.showMsg(i18("OPERATE_EXEPTION"));	
		               }
				}); 
		loxia.unlockPage();
	});
	
	//判断店铺是否有特殊的退仓原因
	$j("#owner").change(function() {
		vmiCode = "";
		var owner = $j("#owner").val();
		var channel = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/getChannelVmiCodeById.json",{"id":owner});//渠道信息
		if(channel['vmiCode'] == 'puma'){
			isCheck = true;
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getChooseOptionByCode.do",{"categoryCode":"pumaAdjReason"});
			$j("#reasonCode").empty();
			for(var idx in result){
				$j("<option value='" + result[idx].optionKey + "'>"+ result[idx].optionValue +"</option>").appendTo($j("#reasonCode"));
			}
			vmiCode = "puma";
		}else if(channel['vmiCode'] == 'BZ_CB'){
			//	Columbia
			isCheck = true;
			var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getChooseOptionByCode.do",{"categoryCode":"converseADJReasonToColumbia"});
			$j("#reasonCode").empty();
			for(var idx in result){
				$j("<option value='" + result[idx].optionKey + "'>"+ result[idx].optionValue +"</option>").appendTo($j("#reasonCode"));
			}
			vmiCode = "BZ_CB";
		}else{
			if(isCheck){
				isCheck = false;
				var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getChooseOptionByCode.do",{"categoryCode":"converseADJReason"});
				$j("#reasonCode").empty();
				$j("<option value=''>请选择</option>").appendTo($j("#reasonCode"));
				for(var idx in result){
					$j("<option value='" + result[idx].optionKey + "'>"+ result[idx].optionValue +"</option>").appendTo($j("#reasonCode"));
				}				
			}
			vmiCode = channel['vmiCode'];
		}
	});
	
});

