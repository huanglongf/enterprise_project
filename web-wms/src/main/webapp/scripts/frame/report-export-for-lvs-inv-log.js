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
	
	var invs = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findinvstatusbyshop.json");
	for(var i in invs){
		$j("<option value='"+invs[i].id+"'>"+invs[i].name+"</option>").appendTo($j("#invStatus"));
	}
	var trans = loxia.syncXhrPost(window.$j("body").attr("contextpath")+"/findtranstypebyshop.json");
	initSelect("transType",trans);
	
	
	$j("#export").click(function(){
		var errorMsg = loxia.validateForm("exportform");
		if(errorMsg.length != 0){
			jumbo.showMsg(errorMsg);
			return ;
		}
		
		$j("#exportform").attr("action",loxia.getTimeUrl($j("body").attr("contextpath") + "/report/lvsInventoryLogreport.do"));
		$j("#exportform").submit();
	});

});
