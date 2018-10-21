$j.extend(loxia.regional['zh-CN'],{
	ORDERCODE:"订单号",
	SHOPPINGCODE : "单据号",
	TYPE : "类型",
	STATUS : "状态",
	SYSTEMKEY : "对接平台",
	CREATE_TIME : "创建时间",
	LOG_TIME: "记录时间"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}


$j(document).ready(function (){
	$j("#clear").addClass("hidden");
	$j("#reset").click(function(){
		$j("#queryForm input").val("");
	});
	
	$j("#search").click(function(){
		var ouId=$j("#ouId").val();
		if(null==ouId||ouId==''){
			jumbo.showMsg("请输入ouId");
			return;
		}
		var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/queryCountByOuId.do",{"ouId":ouId});
		if(null!=staType["msg"]){
			if("yes"==staType["msg"]){
				$j("#clear").removeClass("hidden");
				return;
			}else if("no"==staType["msg"]){
				jumbo.showMsg("不是压测仓库");
				return;
			}else if("error"==staType["msg"]){
				jumbo.showMsg("仓库不存在");
				return;
			}
		}
	});
	
	$j("#clear").click(function(){
		var ouId=$j("#ouId").val();
		if(null==ouId||ouId==''){
			jumbo.showMsg("请输入ouId");
			return;
		}
		jumbo.showMsg("正在清理 请稍等....");
		$j("#clear").addClass("hidden");
		var staType=loxia.syncXhr($j("body").attr("contextpath") + "/json/clearStaByOuId.do",{"ouId":ouId});
	});
	
});