<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/nike-order-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="label.warehouse.sku.query"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="divList">
		<span class="label"><s:text name="label.warehouse.sku.term"></s:text></span>
		<br />
		<form id="form_query" name="form_query">
		<table width="80%">
			<thead>
				<tr>
					<th>NIKE订单</th>
					<td><input loxiaType="input" trim="true" name="comd.slipCode" /></td>
					<th>状态</th>
					<td>
						<select loxiaType="select" id="typeList" name="comd.intStatus">
							<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text></option>
							<option value="0" >失败</option>
							<option value="20" >不执行</option>
							<!-- 
							<option value="1" selected="selected">新建</option>
							<option value="2" selected="selected">已发送</option>
							<option value="5" selected="selected">执行中</option>
							<option value="10" selected="selected">完成</option>
							<option value="-1" selected="selected">错误</option>
							 -->
						</select>
					</td>
				</tr>
			</thead>
		</table>
		</form>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="search"><s:text name="button.search"></s:text></button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text></button>
		</div>
		<table id="tbl-list-query"></table>
		<div id="pager"></div>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="updateStatus">已选择更新为不执行</button>
		</div>
	</div>
</body>
</html>