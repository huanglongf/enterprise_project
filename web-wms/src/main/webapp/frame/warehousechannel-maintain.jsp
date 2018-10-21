<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>

<title><s:text name="title.warehouse.channel.modify"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse-channel-maintain.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>

<body contextpath="<%=request.getContextPath() %>">	
<div id="wh-channel-container" class="custom1" dirty="false">
		<ul>
			<!-- -->
		</ul>			
		<!-- -->
</div>
</body>
</html>
