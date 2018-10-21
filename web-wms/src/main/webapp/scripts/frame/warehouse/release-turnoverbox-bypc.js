$j(document).ready(function(){
	$j("#dialogInfoMsg").dialog({
		title : "操作确认",
		modal : true,
		autoOpen : false,
		open : function(event, ui) {$j(".ui-dialog-titlebar-close").hide();	},
		width : 600
	});
	$j("#btnInfoOk").click(function(){
		$j("#dialogInfoMsg").dialog("close");
		$j("#txtInfoOk").val("");
		$j("#pickingListCode").val("");
		$j("#pickingListCode").focus();
	});
	
	$j("#txtInfoOk").keypress(function(evt){
		if (evt.keyCode === 13) {
			var key = $j("#txtInfoOk").val().trim().toUpperCase();
			if("OK" == key || "" == key){
				$j("#btnInfoOk").trigger("click");	
			}else{
				$j("#txtInfoOk").val("");
			}
		}
	});
	$j("#pickingListCode").keypress(function(event){
		if(event.keyCode == 9){
			event.preventDefault();
		}
		if(event.keyCode === 13){
			var pickingListCode = $j("#pickingListCode").val().trim();
			if(pickingListCode==""){
				loxia.tip(this,"请扫描拣货单上的波次号！");
			}else{
				var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/releaseturnboxbypc.json",{"plCode":pickingListCode});
				if(rs!=null&&rs.result=="success"){
					$j(this).val("");
					$j(this).focus();
				}else{
					showInfoMsg(rs.msg);
				}
				
			}
		}
	});
});

function showInfoMsg(msg){
	$j("#infoMsg").html(msg);
	$j("#dialogInfoMsg").dialog("open");
	$j("#txtInfoOk").focus();
}
