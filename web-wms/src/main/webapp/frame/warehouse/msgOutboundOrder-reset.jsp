<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title>LEVIS重置批次号</title>
<script type="text/javascript"
	src="<s:url value='/scripts/frame/warehouse/msgOutboundOrder-reset.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>">
</script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div>
		<span class="label" style="margin-left: 50px;">请输入批次号:</span><input id="batchCode" onfocus="clearTips()" name="batchCode" loxiaType="input" style="width: 150px;height:25px" trim="true"/> 
	    <button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
	    <span style="color:red" id="tips"></span>
	</div>
</body>
</html>