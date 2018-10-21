$j.extend(loxia.regional['zh-CN'],{
		
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

$j(document).ready(function (){
	
	$j("#resetStatus").click(function(){
		var staCode=$j("#staCode").val().trim();
		if(staCode==null||staCode==""){
			jumbo.showMsg("作业单号不可为空！");
		}
		var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/updateStaStatusToCreate.json?staCode=" + staCode);
		if(!rs||rs["msg"] == 'ERROR'){
			jumbo.showMsg("重置失败！");
		}else if(rs && rs["msg"] == 'SUCCESS'){
			jumbo.showMsg("重置完成！");
		}else{
			jumbo.showMsg(rs["msg"] );
		}
	});
	
		
});