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
function loginFormValidate(){
	var name=$j.trim($j("#name").val());
	var pwd=$j("#pwd").val();
	var reg=/[a-z0-9_@]*/i;
	if("ADMIN"!=name.toUpperCase()&&"ROOT"!=name.toUpperCase()&&name.length<6||name.length>50||!(reg.test(name))){
		alert("用户名，支持字母，数字，下划线和@。 不区分大小写，长度[6，50]");
		return false;
	}
	if(pwd.length<6||pwd.length>20){
		alert("密码长度[6,20]");
		return false;
	}
	return loxia.SUCCESS;
}
$j(document).ready(function (){	
//	loxia.asyncXhrPost($j("body").attr("contextpath") + "/accountsetlist.json",{},{
//		success: function(data){
//			drawAccountSetSelect(data);
//		}
//	});

	$j("#dialog-forgetuser").dialog({title: "忘记用户名", modal:true, autoOpen: false, width: 400});
	$j("#dialog-forgetpwd").dialog({title: "忘记密码", modal:true, autoOpen: false, width: 400});
	$j("#btn_login").click(function(){
		if(loginFormValidate()){
			$j("#loginForm").submit();
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

