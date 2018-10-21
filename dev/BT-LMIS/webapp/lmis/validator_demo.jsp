<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>Demo - Validform - 一行代码搞定整站的表单验证！ &copy;瑞金佬的前端路</title>
		<link rel="stylesheet" href="<%=basePath %>validator/css/style.css" type="text/css" media="all" />
		<link href="<%=basePath %>validator/css/demo.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript" src="<%=basePath %>validator/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>validator/js/Validform_v5.3.2_min.js"></script>
		<script type="text/javascript">
			$(function(){
				$(".registerform").Validform({
					tiptype:2
				});
			})
		</script>
	</head>


	<body>
		<div class="page-header">
			<h1>
				用户编辑
			</h1>
		</div>
			<div class="col-xs-12">
			
				<c:if test="${empty employee.id}">
					<form class="registerform" id="menuform" role="form" action="${root}control/menuController/add.do">
				</c:if>
				
				<c:if test="${not empty employee.id}">
					<form class="registerform" id="menuform" role="form" action="${root}control/menuController/update.do">
				</c:if>
		            <table width="100%" style="table-layout:fixed;">
		                <tr>
		                    <td class="need" style="width:10px;">*</td>
		                    <td style="width:70px;">昵称：</td>
		                    <td style="width:205px;"><input type="text" value="" name="name" class="inputxt" datatype="s6-18" ajaxurl="demo/valid.php" errormsg="昵称至少6个字符,最多18个字符！" /></td>
		                    <td><div class="Validform_checktip">昵称为6~18个字符</div></td>
		                </tr>
		                <tr>
		                    <td class="need">*</td>
		                    <td>密码：</td>
		                    <td><input type="password" value="" name="userpassword" class="inputxt" datatype="*6-16" nullmsg="请设置密码！" errormsg="密码范围在6~16位之间！" /></td>
		                    <td><div class="Validform_checktip">密码范围在6~16位之间</div></td>
		                </tr>
		                <tr>
		                    <td class="need">*</td>
		                    <td>确认密码：</td>
		                    <td><input type="password" value="" name="userpassword2" class="inputxt" datatype="*" recheck="userpassword" nullmsg="请再输入一次密码！" errormsg="您两次输入的账号密码不一致！" /></td>
		                    <td><div class="Validform_checktip">两次输入密码需一致</div></td>
		                </tr>
		            </table>
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<c:if test="${empty employee.id}">
								<button class="btn btn-info" type="button" id="subins" name="subins">
									<i class="icon-ok bigger-110"></i>
									提交
								</button>
							</c:if>
							<c:if test="${not empty employee.id}">
								<button class="btn btn-info" type="button" id="subup" name="subup">
									<i class="icon-ok bigger-110"></i>
									提交
								</button>
							</c:if>
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset" onclick="openDiv('${root}control/employeeController/employeeList.do');">
				 			<i class="icon-undo bigger-110"></i>
								返回
							</button>
						</div>
					</div>
		        </form>
			</div>
		<div class="space-4"></div>
	</body>
</html>