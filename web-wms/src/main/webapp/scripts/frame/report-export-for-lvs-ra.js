$j.extend(loxia.regional['zh-CN'],{
	ENTITY_EXCELFILE				:	"正确的excel导入文件"
});

function i18(msg, args){return loxia.getLocaleMsg(msg,args);}

function initSelect(id,data){
	for(var i in data){
		$j("<option value='"+data[i]+"'>"+data[i]+"</option>").appendTo($j("#"+id));
	}
}


$j(document).ready(function (){
	
	var cg = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findlvsqueryselect.json",{code:'consumerGroup'});
	initSelect("consumerGroup",cg);
	var pl = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findlvsqueryselect.json",{code:'productLine'});
	initSelect("productLine",pl);
	var pc = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findlvsqueryselect.json",{code:'productCategory'});
	initSelect("productCategory",pc);
	
	
	$j("#export").click(function(){
		var errorMsg = loxia.validateForm("exportform");
		if(errorMsg.length != 0){
			jumbo.showMsg(errorMsg);
			return ;
		}
		$j("#exportform").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/report/lvsrareportExport.do"));
		$j("#exportform").submit();
	});

});
