var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件"
});

function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
} 

var baseUrl = $j("body").attr("contextpath");
$j(document).ready(function(){
	loxia.init({debug: true, region: 'zh-CN'});
	
	var baseUrl = $j("body").attr("contextpath");
	
	$j("#export_1").click(function(){
		var iframe = document.createElement("iframe");
		//iframe.src = loxia.getTimeUrl(baseUrl + "/warehouse/skuprovideinfopickingdistrictexport.do");
		iframe.src = loxia.getTimeUrl(baseUrl + "/warehouse/skuprovideinfopickingdistrictexport1.do");
		iframe.style.display = "none";
		$j("#download").append($j(iframe));
	});
     $j("#export_2").click(function(){
    	 var iframe = document.createElement("iframe");
    	 //iframe.src = loxia.getTimeUrl(baseUrl + "/warehouse/skuprovideinfounmaintainexport.do");
    	 iframe.src = loxia.getTimeUrl(baseUrl + "/warehouse/skuprovideinfounmaintainexport1.do");
    	 iframe.style.display = "none";
    	 $j("#download").append($j(iframe));
     });
     $j("#export_3").click(function(){
    	 var iframe = document.createElement("iframe");
    	 //iframe.src = loxia.getTimeUrl(baseUrl + "/warehouse/unfinishedstaunmaintainproductexport.do");
    	 iframe.src = loxia.getTimeUrl(baseUrl + "/warehouse/unfinishedstaunmaintainproductexport1.do");
    	 iframe.style.display = "none";
    	 $j("#download").append($j(iframe));
     });
	
     $j("#import").click(function(){
 		if(!/^.*\.xls$/.test($j("#file").val())){
 			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
 			return;
 		}
 		loxia.lockPage();
 		$j("#importForm").attr("action",
 				loxia.getTimeUrl(baseUrl + "/warehouse/importskuprovidemaxmaintain.do"));
 		loxia.submitForm("importForm");
 	});
	
});

function reloadProductInfoList(){
	$j("#file").val("");
}
 