<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title></title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type='text/javascript'>
			function editEmp(){
				$("#edit_btn").hide();
				$("#save_btn").show();
				$("#back_btn").show();
				$("input[name='inputAll']").removeAttr("disabled");
			}
			
			function saveEmp(){
				var userName = $("#userName").val();
				var name = $("#name").val();
				var email = $("#email").val();
				if(userName==''||name==''||email==''){
					alert("昵称，邮箱都不能为空");
					return;
				}
				if(!isEmail(email)){
					alert("邮箱格式不正确");
					return;
				}
				var isWoEmail = 0;
				if ($("input[id='isWoEmail']").is(':checked')) {
					isWoEmail = 1;
				}
				$.ajax({
					type:"POST",
			        url:"/BT-LMIS/control/employeeController/saveEmp.do",
			        data:{
			        	"username":userName,
			        	"name":name,
			        	"email":email,
			        	"isWoEmail":isWoEmail
			        },
			        success: function(result) {
			        	alert(result.msg);
			        	if(result.code==200){
				        	$("input[name='inputAll']").attr("disabled","disabled");
							$("#save_btn").hide();
							$("#back_btn").hide();
							$("#edit_btn").show();
			        	}
			        }
				}) 
			}
			 function isEmail(str){
			       var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
			       return reg.test(str);
			   }
			 function toProfile(){
					openDiv('${root}/loginController/toProfile.do');
				}
    	</script>
	</head>
	<body>
		<div class="page-header"><h1 style='margin-left:20px'>PROFILE</h1></div>	
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
			<table style='margin-left:25%'>
		  		<tr>
		  			<td align="left" width="30%" ><label class="blue" >登录名&nbsp;:</label></td>
					<td width="30%"><input id='userName' name="inputAll1" value="${user.username}" disabled='disabled'/></td>
		  		</tr>
		  		<tr>
		  			<td align="left" width="30%" style='margin-left:20%'><label class="blue" >昵称&nbsp;:</label></td>
					<td width="30%"><input id='name' name="inputAll" value="${user.name}" disabled='disabled'/></td>	
		  		</tr>
		  		<tr>
		  			<td align="left" width="30%" style='margin-left:20%'><label class="blue" >邮箱&nbsp;:</label></td>
					<td width="30%"><input id='email' name="inputAll" value="${user.email}" disabled='disabled' type="email"/></td>
		  		</tr>
		  		<tr>
		  			<td align="left" width="30%" style='margin-left:20%'><label class="blue" >开启邮件通知&nbsp;:</label></td>
					<td width="30%">
						<input id="isWoEmail" type="checkbox" name="inputAll" class= "ace ace-switch ace-switch-5" ${user.isWoEmail == 1 ? "checked='checked' disabled='disabled'" : "" }/>
						<span class= "lbl" ></span>
					</td>
		  		</tr>
			</table>
		</div>
		<div style="margin-top: 10px;margin-left: 50%;margin-bottom: 10px;">
		   <button id="edit_btn" class="btn btn-info" type="button" onclick = "editEmp();">
										<i class="icon-certificate bigger-110"></i>编辑
									</button>
		</div>
		<div style="margin-top: 10px;margin-left: 50%;margin-bottom: 10px;">
		   <button id="save_btn" class="btn btn-info" type="button" onclick = "saveEmp();" style="display: none">
										<i class="icon-ok bigger-110"></i>提交
									</button>
		   <button id="back_btn" class="btn btn-info" type="button" onclick = "toProfile();" style="display: none">
										<i class="icon-remove bigger-110"></i>返回
									</button>
		</div>
	</body>
</html>
