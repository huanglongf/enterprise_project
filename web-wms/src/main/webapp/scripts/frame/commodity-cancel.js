$j(document).ready(function (){
	
	$j("#sn_import").click(function(){
			if(!/^.*\.xls$/.test($j("#file").val())){
				jumbo.showMsg(i18(CORRECT_FILE_PLEASE));
				return;
			}
			loxia.lockPage();
			$j("#importForm").attr("action",$j("body").attr("contextpath") + "/warehouse/particularCommoditySkuImport.do");
			loxia.submitForm("importForm");
			//获取执行结果，失败的sku条码
			loxia.unlockPage();
		});
});






