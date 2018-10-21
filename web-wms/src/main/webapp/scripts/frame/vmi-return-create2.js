$j.extend(loxia.regional['zh-CN'],{
	CORRECT_FILE_PLEASE:"请选择正确的Excel导入文件"
});
 
function i18(msg, args){
	return loxia.getLocaleMsg(msg,args);
}

function showMsg(msg){
	jumbo.showMsg(msg);
}

function getFormDate(tb){
	/*var postDate = {};
	$j(tb + " tbody tr:gt(0)").each(function (i,tr){
		postDate["icclist["+i+"].paramId"] =  $j(tr).find("td:eq(2)").html();
		postDate["icclist["+i+"].lineType"] = $j(tr).find("td:eq(3)").html();
	});	 
	postDate["remark"] = $j("#txtRmk").val();
	return postDate;*/
}

function clear(){
	$j("#owner").val("");
	$j("#file").val("");
}
var pumaFlag = false;

$j(document).ready(function (){
	
	//加载esprit店铺  
	var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/json/getChooseOptionByCodeEsprit.do",{"categoryCode":"esprit_store"});
	
	$j("#selectStore").append("<option value=''>请选择</option>");
	for(var i=0;i<result.length;i++){
	//	alert(result[i].optionKey);
		//$j("#selectStore").append("<option value="+result[i].optionKey+">"+result[i].optionValue+"</option>");
		$j("#selectStore").append("<option value='" + result[i].optionKey+ "'>"+ result[i].optionValue +'</option>');
	}
	

	$j("#import").click(function(){
		
	var	store = $j("#selectStore").val();
	if(""==store||store==null){
		jumbo.showMsg("请选择店铺！");
		return false;
	}
		
	var	lpcode = $j("#select").val();
		if(""==lpcode||lpcode==null){
			jumbo.showMsg("请选择物流商！");
			return false;
		}
		if(!/^.*\.xls$/.test($j("#file").val())){
			errs.push("请选择正确的Excel导入文件");
		}
		var _url = "intType=102&lpCode="+lpcode+"&owner="+store;
		loxia.lockPage();
			$j("#importForm").attr("action",
					loxia.getTimeUrl($j("body").attr("contextpath") + "/json/vmiReturnEspritImport.do?" + _url));

		loxia.submitForm("importForm");
		
	});

});
