<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title><s:text name="title.login" /></title>

<style>
body {
	TEXT-ALIGN: center;
}

.error {
	color: red;
}

.hide {
	display: none;
}
</style>
</head>

<body contextpath="<%=request.getContextPath()%>">
	<div class="error ${param.error == true ? '' : 'hide'}">
		登陆失败<br>
			${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message} 
	</div>
	<div align="center">
		<form
			action="${pageContext.request.contextPath}/j_spring_security_check"
			method="post" style="width: 260px; text-align: center;">
			<fieldset style="text-align: center;">
				<legend>登陆</legend>
				用户： <input type="text" name="j_username" style="width: 150px;"
					value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" /><br />
				密码： <input type="password" name="j_password" style="width: 150px;" /><br />
				<input type="checkbox" name="_spring_security_remember_me" />两周之内不必登陆<br />
				<input type="submit" value="登陆" /> <input type="reset" value="重置" />
			</fieldset>
		</form>
	</div>
</body>
</html>