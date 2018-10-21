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
	$j("#tabs").tabs();
	
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
		$j("#exportform").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/report/reportexportforsalessum.do"));
		$j("#exportform").submit();
	});
	
	$j("#export1").click(function(){
		var errorMsg = loxia.validateForm("exportform1");
		if(errorMsg.length != 0){
			jumbo.showMsg(errorMsg);
			return ;
		}
		$j("#exportform1").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/report/reportexportforsalesdetial.do"));
		$j("#exportform1").submit();
	});
	
	
});
