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
    </script>
	</head>
	<body>
		<div class="page-header"><h1 style='margin-left:20px'>用户密码修改</h1></div>	
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
			<table style='margin-left:25%'>
		  		<tr>
		  		<td align="left" width="30%" ><label class="blue" >原密码&nbsp;:</label></td>
					<td width="30%"><input id='oldpassword' name='oldpassword'  type='password'/></td>
					<td width="30%"><button id='btn_show' class='	btn btn-info'>长按显示隐藏</button></td>		
		  		</tr>
		  		<tr>
		  		<td align="left" width="30%" style='margin-left:20%'><label class="blue" >新密码&nbsp;:</label></td>
					<td width="30%"><input id='newpassword' name='newpassword' type='password'/></td>	
		  		</tr>
		  		<tr>
		  		<td align="left" width="30%" style='margin-left:20%'><label class="blue" >确认密码&nbsp;:</label></td>
					<td width="30%"><input id='repassword' name='repassword'  type='password'/></td>
						
		  		</tr>
			</table>
		</div>
		<div style="margin-top: 10px;margin-left: 50%;margin-bottom: 10px;">
		   <button class="btn btn-info" type="button" onclick = "savePassword();">
										<i class="icon-ok bigger-110"></i> 提交
									</button>
		</div>
		<!-- 分页添加 -->
      	<div style="margin-right: 300px;margin-top:20px;">${pageView.pageView}</div>		
	</body>
</html>
<script>
   function savePassword(){
	   if(oldpass.value==''){
		   alert('原始密码不能为空！！');
		   return;
	   }
	   if(newpass.value==''){
		   alert('原始密码不能为空！！');
		   return;
	   }
	   if(newpass.value!=repass.value){
		   alert('两次密码输入不一致！！');
		   return;
	   }
	   if(newpass.value.length<6){
		   alert('密码不能小于6位！！');
		   return;
	   }
	   
	   $.ajax({
			type:"POST",
	        url:"/BT-LMIS/loginController/resetPass.do",
	        data:{
	        	"newPassword":newpass.value,
	        	"oldPass":oldpass.value
	        },
	        success: function(result) {
	        	 if(result.code=='1'){
	  			   alert('操作成功！！！');
	  			   window.location.href="/BT-LMIS/loginController/index.do";
		  		  }else{
		  			   alert('操作失败！！！');
		  		   }
	        }
   
		})
	   /* $.post('/BT-LMIS/loginController/resetPass.do?newPassword='+newpass.value+"&oldPass="+oldpass.value,function(data){
		   if(data.code=='1'){
			   alert('操作成功！！！');
			   window.location.href="/BT-LMIS/loginController/index.do";
		   }else{
			   alert('操作失败！！！');
		   }
	   }) */
   }

	                    var btn=document.getElementById("btn_show");
	                    var oldpass=document.getElementById("oldpassword");
	                    var newpass=document.getElementById("newpassword");
	                    var repass=document.getElementById("repassword");
	                   btn.onmousedown=function(){
	                	   oldpass.type="text";
	                	   newpass.type="text";
	                	   repass.type="text";
	                   };
	                    btn.onmouseup=btn.onmouseout=function(){
	                       oldpass.type="password";
	                	   newpass.type="password";
	                	   repass.type="password";
	                   }
	              
   

</script>
<style>

.select {
    padding: 3px 4px;
    height: 30px;
    width: 160px;
   text-align: enter;
}
.table_head_line td {
font-weight: bold;
font-size: 16px
}

.m-input-select {
  display: inline-block;
  position: relative;
  -webkit-user-select: none;
  width: 160px;
}
</style>
