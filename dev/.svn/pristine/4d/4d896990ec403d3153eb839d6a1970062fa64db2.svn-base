<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>修改密码</title>
		<link href="<%=basePath %>css/bootstrap/bootstrap.min.css" rel="stylesheet">
		<link href="<%=basePath %>css/bootstrap/bootstrap-multiselect.css" rel="stylesheet">
		<link href="<%=basePath %>css/bootstrap/bootstrap-select.css" rel="stylesheet">
		<link href="<%=basePath %>css/table.css" rel="stylesheet">
		<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript">
		   function savePassword(){
				var newpass = $("#newpassword").val();
				var repass = $("#repassword").val();
			   if(newpass == ''){
				   alert('新密码不能为空！！');
				   return;
			   }
			   if(newpass != repass){
				   alert('两次密码输入不一致！！');
				   return;
			   }
			   if(newpass.length<6){
				   alert('密码不能小于6位！！');
				   return;
			   }
			   $.ajax({
					type:"POST",
			        url:"/BT-LMIS/loginController/resetPassword.do",
			        data:{
			        	"newPassword":newpass,
			        },
			        success: function(result) {
			        	 if(result.code=='1'){
			  			   alert('操作成功！！！');
			  			   window.location.href="/BT-LMIS/loginController/lmis_login.do";
				  		  }else{
				  			   alert('操作失败！！！');
				  		   }
			        }
		   
				})
		   }
		</script>
	</head>
	<body>
		<div class="page-header"><h1 style="text-align: center;">修改密码</h1></div>	
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
			<table style='margin-left:25%'>
		  		<tr>
		  			<td width="30%" align="center"><label class="blue" >新密码&nbsp;:</label></td>
					<td width="30%" align="center"><input id='newpassword' name='newpassword' type='password'/></td>	
		  		</tr>
		  		<tr>
		  			<td style="height:50px"></td>
		  		</tr>
		  		<tr>
		  			<td width="30%" align="center"><label class="blue" >确认密码&nbsp;:</label></td>
					<td width="30%" align="center"><input id='repassword' name='repassword'  type='password'/></td>
		  		</tr>
			</table>
		</div>
		<div style="margin-top: 10px;margin-left: 50%;margin-bottom: 10px;">
		   <button class="btn btn-info" type="button" onclick = "savePassword();">
										<i class="icon-ok bigger-110"></i> 提交
									</button>
		</div>
	</body>
</html>

