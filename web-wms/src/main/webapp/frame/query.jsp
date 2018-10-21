<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="title.warehouse.inventory.snapshot"></s:text></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath()%>">
	<div>
		<table id="tbl-inventory-list"></table>
		<div id="pager"></div>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search">
				<s:text name="button.search"></s:text>
			</button>
			<button type="reset" loxiaType="button" id="reset">
				<s:text name="button.reset"></s:text>
			</button>
		</div>
		<div style="float: left; width: 200px; height: 400px; overflow: auto; margin-right: 10px;">
			<b>快速模版</b>
			<ul>
				<li>模版1--作业单查询</li>
				<li>模版2--运单信息查询</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
				<li>模版*--************</li>
			</ul>
		</div>
		<div style="float: left; width: 700px;">
			<b>查询逻辑</b>
			<textarea rows="23" cols="150"></textarea>
		</div>

	</div>
</body>
</html>