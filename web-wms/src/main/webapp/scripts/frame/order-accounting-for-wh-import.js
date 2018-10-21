$j.extend(loxia.regional['zh-CN'],{
	ENTITY_EXCELFILE				:	"正确的excel导入文件"
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

$j(document).ready(function (){
	
	$j("#tabs").tabs();
	
	$j("#export").click(function(){
		var url = loxia.getTimeUrl($j("body").attr("contextpath") + "/finance/orderaccountingtemplateexport.do");
		$j("#upload").attr("src",url);
	});
	
	$j("#import").click(function(){
		var file=$j.trim($j("#orderSoModelfile").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			loxia.lockPage();
			$j("#importForm").attr("action", loxia.getTimeUrl($j("body").attr("contextpath") + "/finance/orderaccountingimport.do"));
			loxia.submitForm("importForm");
		}
	});
	
	$j("#export1").click(function(){
		var url = loxia.getTimeUrl($j("body").attr("contextpath") + "/finance/socountbaserateexport.do");
		$j("#upload").attr("src",url);
	});
	
	$j("#import1").click(function(){
		var file=$j.trim($j("#file1").val()),errors=[];
		if(file==""||file.indexOf("xls")==-1){
			errors.push(i18("INVALID_MAND",i18("ENTITY_EXCELFILE")));
		}
		if(errors.length>0){
			jumbo.showMsg(errors.join("<br/>"));
		}else{
			loxia.lockPage();
			$j("#importForm1").attr("action", loxia.getTimeUrl($j("body").attr("contextpath") + "/finance/socountbaserateimport.do"));
			loxia.submitForm("importForm1");
		}
	});
});
