var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	 
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}



$j(document).ready(function(){
	loxia.init({debug: true, region: 'zh-CN'});
	var baseUrl = $j("body").attr("contextpath");
	
	$j("#expSummary").click(function(){
		loxia.lockPage();
		var pathDate = '?';
		var outboundTime = $j("#outboundTime").val();
		if(outboundTime != ""){
			pathDate += 'outboundTime='+outboundTime;
		}
		var endOutboundTime = $j("#endOutboundTime").val();
		if(endOutboundTime != ""){
			if(pathDate != '?') {
				pathDate += '&endOutboundTime='+endOutboundTime
			}else {
				pathDate += 'endOutboundTime='+endOutboundTime;
			}
		}
		var iframe = document.createElement("iframe");
		if(pathDate != '?'){
			iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/base/exportSalesReportForm.do"+pathDate);
		} else {
			iframe.src = loxia.getTimeUrl($j("body").attr("contextpath") + "/base/exportSalesReportForm.do");
		}
		iframe.style.display = "none";
		$j("#download").append($j(iframe));
		loxia.unlockPage();
	});
	
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
	});
});
 