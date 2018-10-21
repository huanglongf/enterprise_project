$j.extend(loxia.regional['zh-CN'],{
	INPUT_FILE_ERROR	:"请选择正确的Excel导入文件"
});


function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function showMsg(msg){
	jumbo.showMsg(msg);
}

function setInit(){
	$j("#importForm input").val("");
	$j("#importForm select").val("");
	$j("#memo").val("");
}


$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");
	initShopQuery("companyshop","innerShopCode");
	jumbo.loadShopList("companyshop");
	
	$j("#reset").click(function(){
		setInit();
	});
	
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#save").click(function(){
		if($j("#companyshop").val() == ""){
			jumbo.showMsg("店铺不能为空");
			return false;
		}
		if($j("#type").val() == ""){
			jumbo.showMsg("作业类型不能为空");
			return false;
		}
		if($j("#address").val() == ""){
			jumbo.showMsg("送货地址不能为空");
			return false;
		}
		if($j("#receiver").val() == ""){
			jumbo.showMsg("收货人不能为空");
			return false;
		}
		if($j("#telephone").val() == "" && $j("#mobile").val() == "" ){
			jumbo.showMsg("联系电话和手机必须至少填写一个");
			return false;
		}
		var file = $j("#staFile").val();
		var errors = [];
		if(file == ""){
			errors.push(i18("INPUT_FILE_ERROR"));//请选择正确的Excel导入文件
		}else{
			var postfix = file.split(".")[1];
			if(postfix != "xls" && postfix != "xlsx"){
				errors.push(i18("INPUT_FILE_ERROR"));//请选择正确的Excel导入文件
			}
		}
		if(errors.length > 0){
			jumbo.showMsg(errors.join("<br />&nbsp; &nbsp; &nbsp;"));
			return false;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",loxia.getTimeUrl(baseUrl + "/warehouse/createOperationOut.do"));
		$j("#importForm").submit();
	});

});
