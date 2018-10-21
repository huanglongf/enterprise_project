<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@include file="/common/meta.jsp"%>
		<title><s:text name="title.auth.operationunit.view"/></title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/group-maintain.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<ul id="groupTree" animate="true"></ul>
		<s:if test="%{editAble}">
			<div class="buttonlist">
				<button type="button" loxiaType="button" class="confirm" id="toEdit">
					<s:text name="button.auth.operationunit.maintainentry"/>
				</button>
			</div>
		</s:if>
	</body>
</html>