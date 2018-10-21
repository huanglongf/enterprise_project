var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'], {
	ORGANIZATION : "组织",
	ROLE : "角色",
	ORGANIZATION_TYPE : "具体的组织类型",
	ORGANIZATION_DETAIL : "具体的组织",
	ROLE_DETAIL : "具体的角色",
	ROLE_MANIPULATE : "具体操作的角色数据",
	INFO_SELECT : "请选择{0}",

	CONCRETE_OPERATIONS : "请具体操作数据",
	ROLE_REPEAT_ERROR : "该角色已经加进去了，不需要重复加入",
	ROLE_SELECT_ERROR : "请您至少选择一个角色",
	URSER_MODIFY_CREATE : "{0}修改用户成功",
	USER_MODIFY_ERROR : "用户修改失败",
	USER_PWD_MODIFY_ERROR : "修改初始密码需输入管理员密码",
	USER_PWD_MODIFY_ERROR1 : "找不到用户",
	USER_PWD_MODIFY_ERROR2 : "超级用户密码错误",


	LOGINNAME : "登入名字",
	PASSWORD : "密码",
	USERNAME : "真实姓名",
	JOBNUMBER : "工号",
	PHONE : "电话号码",
	EMAIL : "email",
	REMARK : "备注",
	INPUT_VALIDATE : "请输入合法的{0}",

	REMARK_WARNING : "备注大于255个字符，请重新输入",
	VALIDATE_LENGTH_LONGER : "{0}长度大于{1}，请重新输入",
	VALIDATE_LENGTH_SHORT : "{0}长度小于{1}，请重新输入",
	
	ROLE_ID : "角色ID",
	OU_ID : "组织ID",
	ISDEFAULT : "是否默认",
	ROLE_LIST : "角色列表"
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
     
function checkLoginName(value, obj) {
	var v = $j.trim(value);
	if (v == "") {
		return loxia.getLocaleMsg("INVALID_EMPTY_DATA", value);
	} else if (v.length < 6) {
		return loxia.getLocaleMsg("VALIDATE_LENGTH_SHORT", [
				loxia.getLocaleMsg("LOGINNAME"), 6 ]);
	} else if (v.length > 50) {
		return loxia.getLocaleMsg("VALIDATE_LENGTH_LONGER", [
				loxia.getLocaleMsg("LOGINNAME"), 50 ]);
	}
	return loxia.SUCCESS;
}

function checkPassword(n) {
	if (n == '' || n == null) {
		return loxia.SUCCESS;
	} else if (n.length > 20) {
		return loxia.getLocaleMsg("VALIDATE_LENGTH_LONGER", [
				loxia.getLocaleMsg("PASSWORD"), 20 ]);
	} else if (n.length < 6) {
		return loxia.getLocaleMsg("VALIDATE_LENGTH_SHORT", [
				loxia.getLocaleMsg("PASSWORD"), 6 ])
	}
	return loxia.SUCCESS;
}

/*
function checkUserName(n) {
	if (n == '' || n == null) {
		return loxia.getLocaleMsg("INVALID_EMPTY_DATA", loxia
				.getLocaleMsg("USERNAME"));
		return true;
	} else if (n.length > 100) {
		return loxia.getLocaleMsg("VALIDATE_LENGTH_LONGER", [
				loxia.getLocaleMsg("USERNAME"), 100 ]);
		return true;
	}
	return loxia.SUCCESS;
}

function checkJobNumber(n) {
	if (n == '' || n == null) {
		return loxia.getLocaleMsg("INVALID_EMPTY_DATA", loxia
				.getLocaleMsg("JOBNUMBER"));
		return true;
	} else if (n.length > 10) {
		return loxia.getLocaleMsg("VALIDATE_LENGTH_LONGER", [
				loxia.getLocaleMsg("JOBNUMBER"), 10 ]);
		return true;
	}
	return loxia.SUCCESS;
}
*/

function checkRemark(memo,obj){
	if(memo.length>255){
    	return loxia.getLocaleMsg("REMARK_WARNING");
	}
	return loxia.SUCCESS;
}
 
 function checkPhone(n){
	 if(n.length > 0){
	    if(!checkNumber(n)){
           return loxia.getLocaleMsg("INPUT_VALIDATE",loxia.getLocaleMsg("PHONE"));
		}
	 }
	    return loxia.SUCCESS;
 }
 
  function checkNumber(s){
      if (s!=null && s!=""){
        return !isNaN(s);
     }
      return false;
  }
  
  function checkNumber2(s){
    if(s==null || s==''){}else{
    var pattern=/^[0-9]*$/;
	    if(!pattern.test(s)){
	    	return loxia.getLocaleMsg("如果填写，请填写数字!");
	    }
    }
    return loxia.SUCCESS;
  }
  
  
  function checkEmail(n){
	  var pattern=/\w+@\w+\.[a-z]+/;
       if(pattern.test(n)){
        return true; }
		else{return false;}	 
  }
  
    function checkEmailisNullorRule(email){
  		if(email==null||email==''){
            return loxia.getLocaleMsg("INVALID_ENTITY_EMPTY",loxia.getLocaleMsg("EMAIL"));
		 }else if(!checkEmail(email)){
           return loxia.getLocaleMsg("INPUT_VALIDATE",loxia.getLocaleMsg("EMAIL"));
		 }
  		return loxia.SUCCESS;
  }
  

$j(document).ready(function() {
					
					$j(":radio:checked").live("click", function() {
						$j(this).val("true");
						var rs = $j(":radio");
						$j.each(rs, function(i, item) {
							var chc = item.checked;
							if (chc == false) {
								item.value = false;
							}
						});
					});

					$j("#button3").live("click", function() {
						$j("#tbl-opb-line_1").css("display", "none");
					});
					var userid = $j("#userid").val();
					$j("#tbl-userrole").jqGrid({
						url : window.parent.$j("body").attr("contextpath")+ "/json/findrolelistbyid.json?user.id="+ userid,
						datatype : "json",
						//colNames : [ "ID", "角色", "角色ID", "组织","组织ID", "是否默认" ],
						colNames : [ "ID", i18("ROLE"), i18("ROLE_ID"), i18("ORGANIZATION"),i18("OU_ID"), i18("ISDEFAULT") ],
						colModel : [{name : "id",index : "id",hidden : true},
									{name : "roleName",width : 180,resizable : true,sortable : false},
									{name : "roleid",index : "roleid",width : 100,hidden : true,sortable : false},
									{name : "ouName",index : "ouname",width : 180,resizable : true,sortable : false},
									{name : "ouid",index : "ouid",width : 150,hidden : true,sortable : false},
									{name : "isDefault",index : "isDefault",sortable : false,width : 150,resizable : true,
										formatter : 'select',
													editoptions : {
														value : "true:<input type=\"radio\" value=\"true\" name=\"isDefault\" checked=\"checked\">;false:<input type=\"radio\" value=\"false\" name=\"isDefault\" >"
													}}
									],
									caption : i18("ROLE_LIST"),//角色列表
									rownumbers:true,
									height:'auto',
									rowNum: -1,
									sortname : 'role',
									postData : {"columns" : "id,roleName,roleid,ouid,ouName,isDefault"},
									sortorder : "desc",
									multiselect : true,
									jsonReader : {repeatitems : false,id : "0"}
						});

				});

function setFAQTwo(obj) {
	var oneSelect = obj;
	var optId = oneSelect.options[oneSelect.selectedIndex].value;
	loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")
			+ "/json/findoperationunitbyoutypeid.json", {
		"user.ou.ouType.id" : optId
	}, {
		success : function(data) {
			var rs = data.oulist;
			var rs1 = data.rolelist;
			getRoleList(rs1);
			var twoSelect = $j("#OperationUnitId");
			$j("#OperationUnitId option").remove();
			if (rs.length > 0) {
				$j("#OperationUnitId").append(
						"<option value=''>"
								+ loxia.getLocaleMsg("INFO_SELECT", loxia
										.getLocaleMsg("ORGANIZATION"))
								+ "</option>");//"请选择组织"
		}
		$j.each(rs, function(i, item) {
			$j("#OperationUnitId").append(
					"<option value='" + item.id + "'>" + item.name
							+ "</option>");
		});
	}
}	);
}

function getRoleList(rs) {
	var twoSelect = $j("#roleId");
	$j("#roleId option").remove();
	if (rs.length > 0) {
		$j("#roleId").append(
				"<option value=''>"
						+ loxia.getLocaleMsg("INFO_SELECT", loxia
								.getLocaleMsg("ROLE")) + "</option>");//"请选择角色"
	}
	$j.each(rs, function(i, item) {
		$j("#roleId").append(
				"<option value='" + item.id + "'>" + item.name + "</option>");
	});

}

var userRoleJSON = jQuery.parseJSON('{}');
function baddUserRole() {
	if ($j("#addOuType").val() == '') {
		jumbo.showMsg(loxia.getLocaleMsg("INFO_SELECT", loxia
				.getLocaleMsg("ORGANIZATION_TYPE")));//"请选择具体的组织类型！"
		return;
	}
	var ouid = $j("#OperationUnitId").val();
	if (ouid == '') {
		jumbo.showMsg(loxia.getLocaleMsg("INFO_SELECT", loxia
				.getLocaleMsg("ORGANIZATION_DETAIL")));//"请选择具体的组织！"
		return;
	}
	var ouname = $j("#OperationUnitId option:selected").text();
	var roleid = $j("#roleId").val();
	if (roleid == '') {
		jumbo.showMsg(loxia.getLocaleMsg("INFO_SELECT", loxia
				.getLocaleMsg("ROLE_DETAIL")));//"请选择具体的角色"
		return;
	}
	var rolename = $j("#roleId option:selected").text();
	var userid = $j("#userid").val();
	var isDefine;
	if ($j("#isDefinexxx").get(0).checked == true) {
		isDefine = true;
	} else {
		isDefine = false;
	}
	if (checkData(ouid, roleid)) {
		jumbo.showMsg(loxia.getLocaleMsg("ROLE_REPEAT_ERROR"));//该角色已经加进去了，不需要重复加入！
		return;
	}
	userRoleJSON["roleName"] = rolename;
	userRoleJSON["roleid"] = roleid;
	userRoleJSON["ouName"] = ouname;
	userRoleJSON["ouid"] = ouid;
	userRoleJSON["isDefault"] = isDefine;
	$j("#tbl-userrole").addRowData("1", userRoleJSON, "first");

}
function addUserandRole() {
	if (checkForm("myform1")) {
		return;
	}
	var userid = $j("#myform1 #userid").val();
	var userName = $j("#myform1 #userName").val();
	var email = $j("#myform1 #email").val();
	var phone = $j("#myform1 #phone").val();
	var jobNumber = $j("#myform1 #jobNumber").val();
	var password = $j("#myform1 #password").val();
	var memo = $j("#myform1 #memo").val();
	var maxNum = $j("#myform1 #maxNum").val();
	var adminPwd = $j("#adminPwd").val();
	var datalist = $j("#tbl-userrole").getRowData();
	if (datalist.length == 0) {
		jumbo.showMsg(loxia.getLocaleMsg("ROLE_SELECT_ERROR")); //请您至少选择一个角色！
		return;
	}
	if(password!=""&&adminPwd==""){
		jumbo.showMsg(loxia.getLocaleMsg("USER_PWD_MODIFY_ERROR")); //
		return;
	}
	var data = {};
	for ( var i = 0, d; (d = datalist[i]); i++) {
		data["userRoleList[" + i + "].role.id"] = d["roleid"];
		data["userRoleList[" + i + "].ou.id"] = d["ouid"];
		data["userRoleList[" + i + "].isDefault"] =$j("input[name='isDefault']").get(i).checked;
	}
	data["user.password"] = ztrim(password);
	data["user.userName"] = ztrim(userName);
	data["user.jobNumber"] = ztrim(jobNumber);
	data["user.email"] = email;
	data["user.phone"] = ztrim(phone);
	data["user.memo"] = memo;
	data["user.maxNum"] = maxNum;
	data["user.id"] = userid;
	data["adminPwd"] = adminPwd;
	loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")
			+ "/json/modifyuseranduserrolelist.json", data, {
		success : function(data) {
			if (data.rs == 'true') {
				var d = {
					email : email,
					userName : userName
				};
				$j("#tbl-userlist").setRowData(userid, d);
				$j("#idmodify").css("display", "none");
				$j("#queryid").css("display", "block");
				jumbo.showMsg(loxia.getLocaleMsg("URSER_MODIFY_CREATE",
						userName));//修改用户成功！
			}else if(data.result=='userIsNotExists'){
				jumbo.showMsg(loxia.getLocaleMsg("USER_PWD_MODIFY_ERROR1")); //
			}else if(data.result=='adminPwdError'){
				jumbo.showMsg(loxia.getLocaleMsg("USER_PWD_MODIFY_ERROR2")); //
			}
		},
		error : function() {
			jumbo.showMsg(loxia.getLocaleMsg("USER_MODIFY_ERROR")); //用户修改失败！
	}
	});
}

function checkData(ouid, roleid) {
	var datalist = $j("#tbl-userrole").getRowData();
	for ( var i = 0, d; (d = datalist[i]); i++) {
		var r = d["role.id"];
		var ox = d["ou.id"];
		if (r == roleid && ox == ouid) {
			return true;
		}
	}
}

function delUserRole(){
     var gr = $j("#tbl-userrole").jqGrid('getGridParam','selarrrow');
        if(gr.length==0){
        	jumbo.showMsg(loxia.getLocaleMsg("INFO_SELECT",loxia.getLocaleMsg("ROLE_MANIPULATE")));//请选择具体操作的角色数据!
        	retutn;
        }
	   if(gr != null ) {
		   var num=gr.length;
	    for(var i=0;i<num;i++){
	    $j("#tbl-userrole").jqGrid('delRowData', gr[0]);
	    }
	   }else {
	    jumbo.showMsg(loxia.getLocaleMsg("CONCRETE_OPERATIONS"));//请具体操作数据!
	   }              
    }
    
     function checkForm(f){
        var loginName = $j("#"+f+" #loginName").val();
		 var userName = $j("#"+f+" #userName").val();
		var password = $j("#"+f+" #password").val();
		var jobNumber = $j("#"+f+" #jobNumber").val();
		var phone = $j("#"+f+" #phone").val();
		var email = $j("#"+f+" #email").val();
		var memo = $j("#"+f+" #memo").val();
		
		var errors=loxia.validateForm(f);
		
		if(errors.length>0){
			jumbo.showMsg(errors);
			return true;
		}
		return false;
	}