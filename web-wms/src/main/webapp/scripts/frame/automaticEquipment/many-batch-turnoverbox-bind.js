var slipCode="";
var barCode="";
var zoonCode="";
$j(document).ready(function(){
	$j("#slipCode").focus();
	$j("#slipCode").keypress(function(event){
		if(event.keyCode == 9){
			event.preventDefault();
			setFocusOn("zoonCode");
		}
		if(event.keyCode === 13){
			slipCode=$j("#slipCode").val();
			if(slipCode==""){
				loxia.tip(this,"请扫描拣货单上面的条码!");
			}else{
				setFocusOn("zoonCode");
			}
		}
	});
	$j("#zoonCode").keypress(function(event){
		if(event.keyCode == 9){
			event.preventDefault();
			setFocusOn("barCode");
		}
		if(event.keyCode === 13){
			slipCode=$j("#zoonCode").val();
			if(slipCode==""){
				loxia.tip(this,"请填写仓储区域编码!");
			}else{
				setFocusOn("barCode");
			}
		}
	});
	$j("#barCode").keypress(function(event){
		if(event.keyCode == 9){
			event.preventDefault();
			setFocusOn("slipCode");
		}
		if(event.keyCode === 13){
			barCode=$j("#barCode").val();
			if(barCode==""){
				loxia.tip(this,"请扫描集装箱条码!");
			}else{
				slipCode=$j("#slipCode").val();
				zoonCode=$j("#zoonCode").val();
				if(slipCode==""){
					loxia.tip($j("#slipCode"),"请扫描拣货单上面的条码!");
					setFocusOn("slipCode");
				}else if(zoonCode==""){
					loxia.tip($j("#zoonCode"),"请扫描拣货单上面的条码!");
					setFocusOn("zoonCode");
				}else{
					//校验是否可以绑定并提示结果
					var rs = loxia.syncXhr($j("body").attr("contextpath") + "/auto/bindManyBatchAndTurnbox.do",{"slipCode":slipCode,"code":barCode,"zoonCode":zoonCode});
					if(rs!=null&&rs.result=="success"){
						jumbo.showMsg("绑定成功！");
						setFocusOn("slipCode");
					}else{
						jumbo.showMsg(rs.msg);
						setFocusOn("slipCode");
					}
				}
			}
		}
	});
});
function setFocusOn(flag){
	if(flag=="barCode"){
		barCode=""
	}else{
		slipCode="";
	}
	$j("#"+flag).val("");
	$j("#"+flag).focus();
}