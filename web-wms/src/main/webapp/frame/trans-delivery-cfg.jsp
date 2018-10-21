<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>仓库物流维护</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/trans-delivery-cfg.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="divTrans">
		<table id="tbl_trans"></table>
	</div>
	<div id="divDetial" class="hidden">
		<table width="50%">
			<tr>
				<td class="label" width="20%"><input type="hidden" ID="transId" />物流供应商</td>
				<td width="30%" id="td_expCode"></td>
				<td class="label" width="20%">简称</td>
				<td width="30%" id="td_fullName"></td>
			</tr>
			<tr>
				<td class="label" width="20%">当日已发货量</td>
				<td width="30%" id="td_sendQty" id="td_sendQty"></td>
				<td class="label" width="20%">当日计划发货量</td>
				<td width="30%" ><input name="qty" id=qty loxiaType="number" trim="true"/></td>
			</tr>
			<!-- 
			<tr>
				<td class="label">评分(0-100)</td>
				<td><input loxiaType="number" trim="true" min="0" max="100" style="width: 80px;" mandatory="true" /></td>
				<td class="label">评分比重系数(0-1)</td>
				<td><input loxiaType="input" trim="true" style="width: 80px;" checkmaster="checkCoefficient" mandatory="true" /></td>
			</tr>
			 -->
		</table>
	
		<div class="buttonlist">
			<button id="detialSave" type="button" loxiaType="button" class="confirm">保存</button>
			<button id="back" type="button" loxiaType="button">返回</button>
		</div>
	</div>
</body>
</html>