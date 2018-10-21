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
		//clearStaByOuId
	//	var rs=loxia.syncXhr($j("body").attr("contextpath") + "/warehouse/updateStaRestore.do",{"staCode":staCode});
	//	var rs = loxia.syncXhr($j("body").attr("contextpath") + "/warehouse/json/updateStaRestore.do?staCode=" + staCode);
		var rs=loxia.syncXhr($j("body").attr("contextpath") + "/json/updateStaRestore.do",{"staCode":staCode});
		if(!rs||rs["msg"] == '0'){
			$j("#staCode").val("");
			jumbo.showMsg("还原失败！");
		}else if(rs && rs["msg"] == '1'){
			$j("#staCode").val("");
			jumbo.showMsg("还原完成！");
		}
		else if(rs && rs["msg"] == '2'){
			$j("#staCode").val("");
			jumbo.showMsg("没有该单据"+staCode);
		}
		else if(rs && rs["msg"] == '3'){
			$j("#staCode").val("");
			jumbo.showMsg(staCode+"已还原");
		}
		else{
			$j("#staCode").val("");
			jumbo.showMsg(rs["msg"] );
		}
	});
	
		
});