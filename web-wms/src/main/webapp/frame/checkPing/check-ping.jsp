<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title>测试访问service时间</title>

<script type="text/javascript"
	src="<s:url value='/scripts/frame/checkPing/check-ping.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;"
	contextpath="<%=request.getContextPath()%>">
	<!-- 这里是页面内容区 -->
		<DIV style="OVERFLOW-Y: auto; OVERFLOW-X:auto; float:left;width:50%;" id="webPing">
		</DIV>
		<DIV style="OVERFLOW-Y: auto; OVERFLOW-X:auto;float:left;width:50%;" id="servicePing">
			
		</DIV>
</body>
</html>