function drawAccountSetSelect(data){
	var isIe = $j.browser.msie;
	$j("#accountsets option").remove();
	for(var i=0; i< data.length; i++){
		if(!isIe){
			$j('<option value="' + data[i]["id"] + '">' + data[i]["name"] + '</option>').appendTo($j("#accountsets"));
		}else{
			var o = document.createElement('option');
			o.text = data[i]["name"];
			o.value = "" + data[i]["id"];
			$j("#accountsets")[0].add(o);
		}
	}
}
function validatePwdWidget(widget){
	if (!widget){
		return -1;
	}
	if(widget.length<6||widget.length>20){
		//return "密码长度[6,20]";
		return 1;
	}
	return 0;
}
function loginFormValidate(){
	var username=$j.trim($j("#username").val());
	var password=$j.trim($j("#pwd").val());
	var newPassword=$j.trim($j("#newPwd").val());
	var confirmpassword=$j.trim($j("#confirmPwd").val());
	var vCode=$j.trim($j("#validateCode").val());
	
	var msg = validatePwdWidget(username);
	if (msg == -1){
		alert("用户名不能不空");
		$j("#username").focus();
		return false;
	}
	msg = validatePwdWidget(password);
	if (msg == -1){
		alert("原始密码不能为空");
		$j("#pwd").focus();
		return false;
	} 
	msg = validatePwdWidget(newPassword);
	if (msg == -1){
		alert("新密码不能为空");
		$j("#newPwd").focus();
		return false;
	} 
	msg = validatePwdWidget(confirmpassword);
	if (msg == -1){
		alert("确认密码不能为空");
		$j("#confirmPwd").focus();
		return false;
	} 
	if (newPassword != confirmpassword){
		alert("两次密码输入不一样");
		$j("#confirmPwd").focus();
		return false;
	}
	msg = validatePwdWidget(vCode);
	if (msg == -1){
		alert("验证码不能为空");
		$j("#validateCode").focus();
		return false;
	} 
	return loxia.SUCCESS;
}
function refreshCode(){
	$j("#imageCode").attr("src", loxia.getTimeUrl($j("body").attr("contextpath") + "/imagecode.do"));
	return ;
}
function resetWidget(){
	$j("#username").val("");
	$j("#pwd").val("");
	$j("#newPwd").val("");
	$j("#confirmPwd").val("");
	$j("#validateCode").val("");
	return ;
}

$j(document).ready(function (){	 
	$j("#imageCode").click(function(){
		refreshCode();
	});
	resetWidget();
	$j("#btn_login").click(function(){
		if(loginFormValidate()){
//			$j("#loginForm").submit();
			var postData = {};
			postData['user.loginName'] = $j.trim($j("#username").val());
			postData['user.password'] = $j.trim($j("#pwd").val());
			postData['user.newPassword'] = $j.trim($j("#newPwd").val());
			postData['user.confirmPassword'] = $j.trim($j("#confirmPwd").val());
			postData['user.userName'] = $j.trim($j("#validateCode").val());
			var rs = loxia.syncXhrPost($j("body").attr("contextpath") + "/pwdchange.do", postData);
			if(rs && rs["result"]){
				if (rs["result"] == "success"){
					alert("密码修改成功");
					resetWidget();
				}else if (rs["result"] == "error"){
					alert("密码修改失败，请重新操作           " + rs["message"]);
				}
			}else {
				alert("操作失败");
			}
			refreshCode();
		}
	});
	$j(document).bind("keypress",function(e){
		var ev=e;
		if(!ev){ ev=window.event; }  
		if(13==ev.keyCode){
			$j("#btn_login").trigger("click");
			return false;
		}
	});
});

