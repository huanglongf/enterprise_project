$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件",
	OPERATE_EXEPTION	:"数据操作异常",
	SUCCESS_INNER :"商品创建成功",
	SLIPCODE_NULL:"请先填写相关单据号，再创商品！",
	CHOOSE_SHOP:""
		
});
 
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}


function clear(){
	$j("#tab_2 select").val("");
	$j("#tab_2 input").val("");
}

$j(document).ready(function (){

	jumbo.loadShopList("owner","id");

	initShopQuery("owner","innerShopCode");
	$j("#btnSearchShop").click(function(){
		$j("#shopQueryDialog").dialog("open");
	});
	
	$j("#import").click(function(){
		var ownerid = $j("#owner").val(); 
		var slipCode = $j("#slipCode").val(); 
		
		var errs = [];
		if(ownerid == ''){errs.push("请选择入库店铺");}

		if(errs.length>0) {
			jumbo.showMsg(errs);
			return;
		}
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
			return;
		}
		loxia.lockPage();
		$j("#importForm").attr("action",
				loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/vmiInboundSkuImport.do?ownerid="+ownerid));
		loxia.submitForm("importForm");
	});
	
	
});

