var $j = jQuery.noConflict();

$j.extend(loxia.regional['zh-CN'], {
	ORGANIZATION 			: "组织",
	ROLE 					: "角色",
	ORGANIZATION_TYPE 		: "具体的组织类型",
	ORGANIZATION_DETAIL 	: "具体的组织",
	ROLE_DETAIL 			: "具体的角色",
	ROLE_MANIPULATE 		: "具体操作的角色数据",
	INFO_SELECT 			: "请选择{0}",

	ROLE_REPEAT_ERROR 		: "该角色已经加进去了，不需要重复加入",
	ROLE_SELECT_ERROR 		: "请您至少选择一个角色",

	USER_CREATE_CONFIRM 	: "是否确定创建用户",
	USER_CREATE_SUCCESS 	: "创建用户成功",
	USER_IS_EXISTS 			: "该用户和工号都已存在",
	USER_IS_EXISTS1 		: "该用户存在，但工号不同",
	NAME_AGAIN 				: "登入名称已被使用，请重新输入",
	USER_CREATE_FAIL 		: "创建用户失败",

	LOGINNAME 				: "登入名字",
	PASSWORD 				: "密码",
	USERNAME 				: "真实姓名",
	JOBNUMBER 				: "工号",
	PHONE 					: "电话号码",
	EMAIL 					: "email",
	REMARK	 				: "备注",
	INPUT_VALIDATE 			: "请输入合法的{0}",

	REMARK_WARNING 			: "备注大于255个字符，请重新输入",
	VALIDATE_LENGTH_LONGER 	: "{0}长度大于{1}，请重新输入",
	VALIDATE_LENGTH_SHORT 	: "{0}长度小于{1}，请重新输入",
	
	ROLEID : "角色ID",
	OUID : "组织ID",
	ISDEFAULT : "是否默认",
	ROLE_LIST : "角色列表"
});

function i18(msg,args){
	return loxia.getLocaleMsg(msg,args);
}

/**
 * 如果chechmaster返回错误push到arr
 * @param arr 数组
 * @param res loxia.SUCCESS错误提示
 * @return
 */
function pushCheckmasterRes(arr, res) {
	if (res != loxia.SUCCESS) {
		arr.push(res);
	}
}
//checkmasters
/**
 * 
 * @param value
 * @param obj
 * @return
 */
function checkLoginName(value, obj) {
	var v = $j.trim(value);
	if (v == "") {
		return i18("INVALID_EMPTY_DATA", value);
	} else if (v.length < 6) {
		return i18("VALIDATE_LENGTH_SHORT", [i18("LOGINNAME"), 6 ]);
	} else if (v.length > 50) {
		return i18("VALIDATE_LENGTH_LONGER", [i18("LOGINNAME"), 50 ]);
	}
	return loxia.SUCCESS;
}

function checkPassword(n) {
	if (n == '' || n == null) {
		return i18("INVALID_ENTITY_EMPTY", i18("PASSWORD"));
	} else if (n.length > 20) {
		return i18("VALIDATE_LENGTH_LONGER", [i18("PASSWORD"), 20 ]);
	} else if (n.length < 6) {
		return i18("VALIDATE_LENGTH_SHORT", [i18("PASSWORD"), 6 ]);
	}
	return loxia.SUCCESS;
}

function checkUserName(n) {
	if (n == '' || n == null) {
		return i18("INVALID_EMPTY_DATA", i18("USERNAME"));
	} else if (n.length > 100) {
		return i18("VALIDATE_LENGTH_LONGER", [i18("USERNAME"), 100 ]);
	}
	return loxia.SUCCESS;
}

function checkJobNumber(n) {
	if (n == '' || n == null) {
		loxia.SUCCESS
	} else if (n.length > 10) {
		return i18("VALIDATE_LENGTH_LONGER", [i18("JOBNUMBER"), 10 ]);
	}
	return loxia.SUCCESS;
}
/*
 function checkisNull(n, name, size) {
 if (n == '' || n == null) {
 return false;
 } else if (n.length > size) {
 return i18("VALIDATE_LENGTH_LONGER", name, size);
 return true;
 }
 return loxia.SUCCESS;
 }
 * */

function checkPhone(n) {
	if (n == null || n == '') {
		return loxia.SUCCESS;
	} else if (!checkNumber(n)) {
		return loxia
				.getLocaleMsg("INPUT_VALIDATE", i18("PHONE"));
		return true;
	}
	return loxia.SUCCESS;
}
function checkNumber(s) {
	if (s != null && s != "") {
		return !isNaN(s);
	}
	return false;
}

function checkEmail(n){
	  var pattern=/\w+@\w+\.[a-z]+/;
       if(pattern.test(n)){
        return true; }
		else{return false;}	 
  }
  
  function checkEmailisNullorRule(email){
  		if(email==null||email==''){
            return i18("INVALID_ENTITY_EMPTY",i18("EMAIL"));
		 }else if(!checkEmail(email)){
           return i18("INPUT_VALIDATE",i18("EMAIL"));
		 }
  		return loxia.SUCCESS;
  }
  
  function checkRemark(memo){
	  if(memo.length>0){
        	if(memo.length>255){
        		return i18("REMARK_WARNING");
        	}
        }
	  return loxia.SUCCESS;
  }

$j(document).ready(function (){
	   $j(":radio:checked").live("click", function(){
		     $j(this).val("true");
			  var rs = $j(":radio");
              $j.each(rs, function(i, item) {
			   var chc = item.checked;
			   if(chc==false){
                item.value=false;
			   }
			  });			
	     });
	 
	 
	var userid = $j("#userid").val();
	$j("#tbl-userlist").jqGrid({
		// url:"/wms/json/queryRoleListJson.do?user.id="+userid,
		datatype: "json",
		//colNames: ["ID","角色","角色ID","组织","组织ID","是否默认"],
		colNames: ["ID",i18("ROLE"),i18("ROLEID"),i18("ORGANIZATION"),i18("OUID"),i18("ISDEFAULT")],
		colModel: [{name: "id", index: "id", hidden: true},
		            {name: "rolename", index: "rolename", width: 200, resizable: true},
		            {name: "roleid", index: "roleid", width: 100, hidden: true},
		            {name: "ouname", index: "ouname", width: 200, resizable: true},
		            {name: "ouid", index: "ouid", width: 150, hidden: true},
				    {name: "isDefault", index: "isDefault", width: 150, resizable: true,formatter:'select',editoptions:{value:"true:<input type=\"radio\" id=\"isDefault\" value=\"true\" name=\"isDefault\" checked=\"checked\">;false:<input type=\"radio\" value=\"false\" id=\"isDefault\" name=\"isDefault\" >"}}
				   ],
		caption: i18("ROLE_LIST"),//角色列表
		rowNum:-1,
		height:"auto",
   	//rowList:[10,20,30],
   	sortname: 'role',
    postData:{"columns":"id,rolename,ouname"},
	sortorder: "desc", 
	multiselect: true,
	
	jsonReader: { repeatitems : false, id: "0" }
	});
	
});

function setFAQTwo(obj) {
	var oneSelect = obj;
	var optId = oneSelect.options[oneSelect.selectedIndex].value;
	    loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/findoperationunitbyoutypeid.do",
				{
				"user.ou.ouType.id":optId
				},
				{
			   success:function (data) {
				var rs = data.oulist;
				var rs1 = data.rolelist;
			getRoleList(rs1);
		   var twoSelect = $j("#OperationUnitId");
		    $j("#OperationUnitId option").remove();
		    if(rs.length>0){
		    $j("#OperationUnitId").append("<option value=''>" + i18("INFO_SELECT",i18("ORGANIZATION")) + "</option>");
		    }
		    $j.each(rs, function(i, item) {
		 	$j("#OperationUnitId").append("<option value='" + item.id + "'>" + item.name+ "</option>");
			});
			 }
			});
   }


    function getRoleList(rs){      
       var twoSelect = $j("#roleId");
		    $j("#roleId option").remove();
		    if(rs.length>0){
		    $j("#roleId").append("<option value=''>" + i18("INFO_SELECT",i18("ROLE")) + "</option>");
		    }
		    $j.each(rs, function(i, item) {
		 	$j("#roleId").append("<option value='" + item.id + "'>" + item.name+ "</option>");
			})   
    }
   
 
     var userRoleJSON=jQuery.parseJSON('{}');
     var gi=1;
    function baddUserRole(){
    	 if($j("#addOuType").val()==''){
    	  jumbo.showMsg(i18("INFO_SELECT",i18("ORGANIZATION_TYPE")));
        	return ; 
    	 }
         var ouid = $j("#OperationUnitId").val();
          if(ouid==''){
        	 jumbo.showMsg(i18("INFO_SELECT",i18("ORGANIZATION_DETAIL")));
        	return ; 
         }
         var ouname = $j("#OperationUnitId option:selected").text() ;
         var roleid = $j("#roleId").val();
         if(roleid==''){
        	 jumbo.showMsg(i18("INFO_SELECT",i18("ROLE_DETAIL")));
        	 return ;
         }
         var rolename = $j("#roleId option:selected").text() ;
         var userid = $j("#userid").val();       
         var isDefine; 
           if($j("#isDefinexxx").get(0).checked==true){
            isDefine=true;
           }else{
           isDefine=false;
           }
         if(checkData(ouid,roleid)){
          jumbo.showMsg(i18("ROLE_REPEAT_ERROR"));
          return;
         }            
         userRoleJSON.rolename=rolename;
         userRoleJSON.roleid=roleid;
         userRoleJSON.ouname=ouname;
         userRoleJSON.ouid=ouid; 
         userRoleJSON.isDefault=isDefine;    
          userRoleJSON.id=gi; 
       $j("#tbl-userlist" ).addRowData(gi,userRoleJSON,"first");   
         gi++; 
    }
    function checkData(ouid,roleid){
       var datalist = $j("#tbl-userlist" ).getRowData();
       for(var i=0,d;(d=datalist[i]);i++){
    	  	var r = d.roleid;
    		var ox = d.ouid;
    		if(r==roleid&&ox==ouid){
    		  return true;
    		}
    	} 
    }
    function addUserandRole(){   
       var rs =  checkForm("myform");
	     if(rs){
			 return;
		 }	     
       var loginName = $j("#loginName").val();
       var userName = $j("#userName").val();
       var email = $j("#email").val();
       var phone = $j("#phone").val();
       var jobNumber = $j("#jobNumber").val();
       var password = $j("#password").val();
        var loginName = $j("#loginName").val();
         var memo = $j("#memo").val();
    	var datalist = $j("#tbl-userlist" ).getRowData();
    	 // 检查角色是否有
    	 if(datalist.length==0){
    	 jumbo.showMsg(i18("ROLE_SELECT_ERROR"));	
    		 	return;
    	 }    
    	var data = {};
    	for(var i=0,d;(d=datalist[i]);i++){
    		data["userRoleList[" + i + "].role.id"] = d.roleid;
    		data["userRoleList[" + i + "].ou.id"] = d.ouid;
    		data["userRoleList[" + i + "].isDefault"] = $j("#isDefault").val();
    	}
    	    data["user.loginName"]=ztrim(loginName);
    	    data["user.password"]=ztrim(password);
    	    data["user.userName"]=ztrim(userName);
    	    data["user.jobNumber"]=ztrim(jobNumber);
    	    data["user.email"]=email;
    	    data["user.memo"]=memo;
    	    data["user.phone"]=ztrim(phone);
    	    data["user.userGroup.id"]=$j("#groupId").val();
    	       if(window.confirm(i18("USER_CREATE_CONFIRM"))){	
              loxia.asyncXhrPost(window.parent.$j("body").attr("contextpath")+"/json/adduseranduserrolelist.json",
              data,
				{
			   success:function (data) {
				  if(data &&  data.result == "isNotSubmit"){
					 jumbo.showMsg(i18("USER_CREATE_FAIL"));
				  }else if(data &&  data.result == "userIsExists"){
            	  jumbo.showMsg(i18("USER_IS_EXISTS"));	            	   
				  }else if( data &&  data.result == "userIsExists1"){
					  jumbo.showMsg(i18("USER_IS_EXISTS1"));	            	   
				  }else if( data.rs=='true'){
					  jumbo.showMsg(i18("USER_CREATE_SUCCESS"));	            	   
            	  }else {
            		jumbo.showMsg(i18("NAME_AGAIN"));
				  }
			   },
               error:function(){
				 jumbo.showMsg(i18("USER_CREATE_FAIL"));	
               }
		    });
              }
    }
    
    function delUserRole(){
        var gr = $j("#tbl-userlist").jqGrid('getGridParam','selarrrow');
        if(gr.length==0){
        	jumbo.showMsg(i18("ROLE_MANIPULATE"));
        	retutn;
        }
	   if(gr != null ) {
		   var num=gr.length;
	    for(var i=0;i<num;i++){
	    $j("#tbl-userlist").jqGrid('delRowData', gr[0]);
	    }
	   }else {
	    jumbo.showMsg(i18("ROLE_MANIPULATE"));
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
			//}
		}
		return false;
        /* if(checkLogName(loginName)){
        	 return true;
         }
         if(checkisNullandSize(loginName,i18("LOGINNAME"),50)){
           return true;
		 }
		  if(checkPassword(password,i18("PASSWORD"),6,20)){
           return true;
		 }
         if(checkisNullandSize(userName,i18("USERNAME"),50)){
           return true;
		 }
          if(checkisNullandSize(jobNumber,i18("JOBNUMBER"),20)){
           return true;
		 }
		 if(checkPhone(phone)){
			 return true;
		 } 
	  
		 if(email==null||email==''){
            jumbo.showMsg(i18("INVALID_ENTITY_EMPTY",email));
			return true;
		 }else if(!checkEmail(email)){
           jumbo.showMsg(i18("INPUT_VALIDATE",mail));
		   return true;
		 }
        if(memo.length>0){
        	if(memo.length>255){
        		alert(i18("REMARK_WARNING"));
        		return true;
        	}
        }*/
		
	}
  
    
    
    
    
    