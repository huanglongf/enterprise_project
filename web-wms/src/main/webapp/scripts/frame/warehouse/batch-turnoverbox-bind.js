var slipCode="";
var code="";
var pickingListCode="";
var curStep="";
var pId =0;
var flag = false;
$j(document).ready(function(){
	$j("#div2").addClass("hidden");
	$j("#div3").addClass("hidden");
	$j("#pickingListCode").keypress(function(event){
		if(event.keyCode == 9){
			event.preventDefault();
		}
		if(event.keyCode === 13){
			pickingListCode = $j("#pickingListCode").val().trim();
			if(pickingListCode==""){
				loxia.tip(this,"请扫描拣货单上的波次号！");
			}else{
				var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/checkbindpickinglist.json",{"plCode":pickingListCode});
				if(rs!=null&&rs.result=="success"){
					pId = rs.pId;
					$j("#div1").addClass("hidden");
					if(rs.pType==5){//单件
						$j("#div2").removeClass("hidden");
						setFocusOn("slipCode");
					}else if(rs.pType==1 || rs.pType==3||rs.pType==10){//多件
						$j("#div3").removeClass("hidden");
						setFocusOn("zoonCode");
					}
				}else{
					showInfoMsg(rs.msg);
				}
			}
		}
	});
	$j("#slipCode").keypress(function(event){
		if(event.keyCode == 9){
			event.preventDefault();
			code="";
			setFocusOn("code");
		}
		if(event.keyCode === 13){
			slipCode=$j("#slipCode").val().trim();
			if(slipCode==""){
				loxia.tip(this,"请扫描箱子中任意一个单据的相关单据号!");
			}else{
				code="";
				setFocusOn("code");
			}
		}
	});
	$j("#code").keypress(function(event){
		if(event.keyCode == 9){
			event.preventDefault();
			slipCode="";
			setFocusOn("slipCode");
		}
		if(event.keyCode === 13){
			code=$j("#code").val();
			if(code==""){
				loxia.tip(this,"请扫描集装箱条码!");
			}else{
				slipCode=$j("#slipCode").val().trim();
				if(slipCode==""){
					loxia.tip($j("#slipCode"),"请扫描箱子中任意一个单据的相关单据号!");
					slipCode="";
					setFocusOn("slipCode");
				}else{
					//校验是否可以绑定并提示结果
					var rs = loxia.syncXhr($j("body").attr("contextpath") + "/json/bindbatchandturnbox.json",{"slipCode":slipCode,"code":code,"pickId":pId});
					if(rs!=null&&rs.result=="success"){
						$j("#infomsg").html("绑定成功!");
						if(rs.flag="true"){
							flag = true;
							showInfoMsg("当前批次已经完成全部绑定!");
						}else{
							clear();
							setFocusOn("slipCode");
						}
					}else{
						$j("#infomsg").html(rs.msg);
						curStep = "slipCode";
						showInfoMsg(rs.msg);					
					}
				}
			}
		}
	});
	
	$j("#zoonCode").keypress(function(event){
		if(event.keyCode == 9){
			event.preventDefault();
			code="";
			setFocusOn("boxCode");
		}
		if(event.keyCode === 13){
			slipCode=$j("#zoonCode").val().trim();
			if(slipCode==""){
				loxia.tip(this,"请扫描仓库区域编码!");
			}else{
				code="";
				setFocusOn("boxCode");
			}
		}
	});
	$j("#boxCode").keypress(function(event){
		if(event.keyCode == 9){
			event.preventDefault();
			slipCode="";
			setFocusOn("zoonCode");
		}
		if(event.keyCode === 13){
			code=$j("#boxCode").val();
			if(code==""){
				loxia.tip(this,"请扫描周转箱条码!");
			}else{
				slipCode=$j("#zoonCode").val().trim();
				if(slipCode==""){
					loxia.tip($j("#zoonCode"),"请扫描仓库区域编码!");
					slipCode="";
					setFocusOn("zoonCode");
				}else{
					$j('#zoonCode').attr("disabled",true); 
					if(code=="OK"){
							$j("#dialogInfoMsg_More").dialog("open");
							$j("#txtInfoOk_More").focus();
					}else if(code != ""){
						addBarCode();
					}
					
				}
			}
		}
	});
	
	$j("#btnInfoOk").click(function(){
		$j("#dialogInfoMsg").dialog("close");
		$j("#txtInfoOk").val("");
		if(flag){
			flag = false;
			$j("#reback").trigger("click");
		}else{
			clear();
			setFocusOn(curStep);
		}
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
	$j("#btnInfoOk_More").click(function(){
		$j("#dialogInfoMsg_More").dialog("close");
		$j("#txtInfoOk_More").val("");
		$j("#boxCode").val("");
		$j("#zoonOver").trigger("click");	
		
	});
	
	$j("#txtInfoOk_More").keypress(function(evt){
		if (evt.keyCode === 13) {
			var key = $j("#txtInfoOk_More").val().trim().toUpperCase();
			if("OK" == key){
				$j("#btnInfoOk_More").trigger("click");	
			}else{
				$j("#txtInfoOk_More").val("");
			}
		}
	});
	$j("#dialogInfoMsg").dialog({
		title : "操作确认",
		modal : true,
		autoOpen : false,
		open : function(event, ui) {$j(".ui-dialog-titlebar-close").hide();	},
		width : 600
	});
	$j("#dialogInfoMsg_More").dialog({
		title : "操作确认",
		modal : true,
		autoOpen : false,
		open : function(event, ui) {},
		width : 600
	});
	$j("#reback").click(function(){
		code="";
		slipCode="";
		clear();
		curStep="pickingListCode";
		$j("#div2").addClass("hidden");
		$j("#div3").addClass("hidden");
		$j("#div1").removeClass("hidden");
		setFocusOn(curStep);
	});
	$j("#reback2").click(function(){
		$j("#reback").trigger("click");	
	});
	curStep="pickingListCode";
	$j("#pickingListCode").focus();
	
	$j("#close_More").click(function(){
		$j("#dialogInfoMsg_More").dialog("close");
		$j("#boxCode").focus();
	});
	
	$j("#zoonOver").click(function(){
		if(!$j("#barCode_tab tbody tr").length > 0){
			jumbo.showMsg("请输入周转箱编码.");
			$j("#boxCode").focus();
			
		}else{
			var postData={};
			$j("#barCode_tab tbody:eq(0) tr").each(function (i,tag){ 
				var boxCode = $j(tag).find("td:eq(1) input").val();
				postData['boxCode['+i+']'] = boxCode;
			});
			postData["zoonCode"] = slipCode;
			postData["pickId"]=pId;
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/auto/pickingListAndZoneOver.do",postData);
			if(rs!=null&&rs.result=="success"){
				if(rs.over!=""&&rs.over!=null){
					jumbo.showMsg(rs.over);
				}else{
					jumbo.showMsg("绑定成功！");
				}
			}else{
				jumbo.showMsg(rs.msg);
			}
			
			$j("#barCode_tab tbody tr").remove();
			$j('#zoonCode').removeAttr("disabled"); 
			$j("#zoonCode").val("");
			$j("#boxCode").val("");
			$j("#pickingListCode").val("");
			$j("#reback").trigger("click");	
			setFocusOn("pickingListCode");
			
		}
	});
	
	
});
function showInfoMsg(msg){
	$j("#infoMsg").html(msg);
	$j("#dialogInfoMsg").dialog("open");
	$j("#txtInfoOk").focus();
}
function clear(){
	$j("input").val("");
	$j("#barCode_tab tbody tr").remove();
	$j('#zoonCode').removeAttr("disabled"); 
}
function setFocusOn(flag){
	$j("#"+flag).val("");
	$j("#"+flag).focus();
}

function addBarCode(){
	var varCode = jQuery.trim($j("#boxCode").val());
	if(varCode != ""){
		var isAdd = true;
		$j("#barCode_tab tbody tr").each(function (i,tag){
			if(varCode == $j(tag).find("td:eq(1) :input").val()){
				jumbo.showMsg("周转箱编码已存在.");
				isAdd = false;
			}
		});
		if(isAdd){
			$j("button[action=add]").click();
			var length = $j("#barCode_tab tbody tr").length-1;
			$j("#barCode_tab tbody tr:eq("+length+") td:eq(1) :input").val(varCode);
		}
	}
	if($j("#barCode_tab tbody tr").length > 0){
		$j("#boxCode").val("");
		setFocusOn("boxCode");
	}else {
		jumbo.showMsg("周转箱编码不能为空");
	}
}
