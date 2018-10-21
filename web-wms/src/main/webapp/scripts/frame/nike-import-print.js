$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE : "请选择正确的Excel导入文件"
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

function showMsg(msg){
	jumbo.showMsg(msg);
}
function printInfo(){
	 var url = $j("body").attr("contextpath") + "/afterimportprint.json";
	 printBZ(loxia.encodeUrl(url),true);
	 loxia.unlockPage();
}
$j(document).ready(function(){
	$j("#import").click(function(){
		if(!/^.*\.xls$/.test($j("#file").val())){
			jumbo.showMsg(i18("CORRECT_FILE_PLEASE"));
			return;
		}
		$j("#importForm").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/warehouse/importthenprint.do"));
		loxia.submitForm("importForm");
	});
});
 