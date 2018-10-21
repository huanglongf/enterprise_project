$j.extend(loxia.regional['zh-CN'],{
	INPUT_FROM_TIME:	"输入开始时间",
	INPUT_END_TIME:		"输入结束时间"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
} 
$j(document).ready(function (){
		//导出
	$j("#btnSoInvoice").click(function(){ 
		var url = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/exportskuDistributionOfFailureInfo.do");
		$j("#frmSoInvoice").attr("src",url);
	});
});