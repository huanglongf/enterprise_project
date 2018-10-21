<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<input type="hidden" name="districtId" value="<s:property value="#request.districtId"/>" id="districtId" />
<input type="hidden" name="msg" value="<s:property value="#request.msg"/>" id="msg" />
<script type="text/javascript" src="<s:url value='/scripts/frame/district-location-upload-result.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

</html>