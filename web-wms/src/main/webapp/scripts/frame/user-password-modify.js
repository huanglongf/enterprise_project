var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'], {
	 
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

   /*
    * checkmaster
    * @param {Object} value
    * @param {Object} obj
    * @return {TypeName} 
    */
         

function validateForm (form){
	var errors = [];
	
	var password = $j("#"+form+" [name=usercmd.password]").val().trim();
	var newPassword = $j("#"+form+" [name=usercmd.newPassword]").val().trim();
	var confirmPassword = $j("#"+form+" [name=usercmd.confirmPassword]").val().trim();
	
	if(null == password || '' == password){errors.push('原始密码不能为空');}
	if(null == newPassword || '' == newPassword){errors.push('新密码不能为空');}
	if(null == confirmPassword || '' == confirmPassword){errors.push('确认密码不能为空');}

	if('' != newPassword &&(newPassword.length <6 || newPassword.length >20)) {errors.push('新密码长度不正确，密码长度在[6,20]之间');}
	if('' != confirmPassword &&(newPassword != confirmPassword)) {errors.push('两次输入密码不正确');}
	return errors;
}
$j(document).ready(function() {
			$j("#passwordmodify").click(function(){
				var errors = validateForm("passwordform");
				if(errors.length>0){
					jumbo.showMsg(errors);
					return false;
				}
				
				var postData = loxia._ajaxFormToObj("passwordform");
				var result = loxia.syncXhrPost($j("body").attr("contextpath") + "/passwordmodify.json",postData); // handOver
				if(result && result.result == "success"){
					var user = result.user;
					jumbo.showMsg("用户[" + user.loginName + "]密码修改成功");
				}else if(result && result.result == "error"){
					jumbo.showMsg("密码修改失败<br/>" + result.message);
				}else{
					jumbo.showMsg("密码修改失败");
				}
			});
	});

 