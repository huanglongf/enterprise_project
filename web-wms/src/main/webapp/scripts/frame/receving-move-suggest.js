$j.extend(loxia.regional['zh-CN'],{
	
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}



$j(document).ready(function (){
	var baseUrl = $j("body").attr("contextpath");

	$j("#suggest").click(function(){
		$j("#suggestForm").attr("action",baseUrl+"/warehouse/exportrecevingmovesuggest.do");
		$j("#suggestForm").submit();
	});
	
	$j("#reset").click(function(){
		$j("#suggestForm input").val("");
	});
	
});