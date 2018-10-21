<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="title.inventory.query"/></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="../scripts/frame/vmi-esprit-invoice-percentage-bd.js"></script>
<style type="text/css">
.divFloat{
		/** float: left; margin-left: 230px; margin-top: -25px; */
		float: left;
		margin-right: 10px;
	}
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<form action="" id='queryForm' name='queryForm'>
		<table id='query_tab'>
			<tr>
				<td class="label">PO:</td>
				<td ><input loxiaType="input" trim="true" name="po" style="width: 200px" /></td>
				<td class="label">发票号:</td>
				<td ><input loxiaType="input" trim="true" name='invoiceNum' style="width: 200px" /></td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="query" >查询</button>
		<button type="button" loxiaType="button" class="confirm" id="reset" >重置</button>
	</div>
	<table id="tbl-pbPo"></table>
	<div id="pager"></div>
	<br>
	<table>
		<tr>
			<td class="label">PO:</td>
			<td ><input loxiaType="input" trim="true" name="po" id="po" style="width: 200px" /></td>
			<td class="label">发票号:</td>
			<td ><input loxiaType="input" trim="true" name="invoiceNum" id='invoiceNum' style="width: 200px" /></td>
			<td colspan="2"><button type="button" loxiaType="button" class="confirm" id="bdPo" >绑定</button></td>
		</tr>
	</table>
</body>
</html>