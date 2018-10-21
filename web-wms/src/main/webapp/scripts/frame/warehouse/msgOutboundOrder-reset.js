$j(document).ready(function (){
	//清除红字提示
		$j("#reset").click(function(){
			if($j("#batchCode").val()==""){
				$j("#tips").html("请输入批次号！");
				return;
			}else{
				$j("#tips").html("");
			}
		var batchCode=$j("#batchCode").val();
	    var info= loxia.syncXhrPost($j("body").attr("contextpath") + "/warehouse/msgOutboundOrderReset.do",{"batchCode":batchCode});
	    $j("#tips").html(info.msg);
	});
});
//清除红字提示
function clearTips(){
	$j("#tips").html("");
}

