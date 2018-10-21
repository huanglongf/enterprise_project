$j.extend(loxia.regional['zh-CN'],{
	INPUT_FROM_TIME:	"输入开始时间",
	INPUT_END_TIME:		"输入结束时间"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
} 
$j(document).ready(function (){
	// 发票
	$j("#btnSoInvoice").click(function(){  
		var fromDate = $j.trim($j("#fromDate").val());
		var endDate = $j.trim($j("#endDate").val());
		if (!fromDate){
			jumbo.showMsg(i18("INPUT_FROM_TIME"));
			$j("#fromDate").focus();
			return false;
		}
		if (!endDate){
			jumbo.showMsg(i18("INPUT_END_TIME"));
			$j("#endDate").focus();
			return false;
		} 
		//销售换货出库发票导出
		var url = loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/exportsoinvoiceforsalesreturnorder.do?fromDate=" + fromDate + "&endDate=" + endDate);
		$j("#frmSoInvoice").attr("src",url);
	});
	
	$j("#reset").click(function(){
		$j("#fromDate").val("");
		$j("#endDate").val("");
	});
	
	// 发票ZIP
	$j("#btnSoInvoiceZip").click(function(){
		var id = $j("#div2").attr("plId");
		$j("#frmSoInvoice").attr("src",$j("body").attr("contextpath") + "/warehouse/exportSoInvoiceZip.do?pickingListId=" + id);
	});
});