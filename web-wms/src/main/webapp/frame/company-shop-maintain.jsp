<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>

<script type="text/javascript" src="<s:url value='/scripts/frame/company-shop-maintain.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title>公司关联店铺信息维护</title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="shopList"><table id="tbl-shopDetail"></table></div>
	<div id="btnlist" class="buttonlist" >
		<button loxiaType="button" class="confirm" id="addToCompany">关联选中</button>
		<button loxiaType="button" class="confirm" id="cancelToCompany">取消关联选中</button>
	</div><br/>
	
</body>
</html>