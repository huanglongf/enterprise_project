<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/sn-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.sta.query"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<form id="queryForm" name="queryForm">
		<table width="80%">
			<tr>
				
				<td width="13%" class="label">
					商品JMSKUCODE
				</td>
				<td width="20%"><input loxiaType="input" trim="true" name="cmd.skuCode" /></td>
				<td width="13%" class="label">
					商品名称
				</td>
				<td width="20%"><input loxiaType="input" trim="true" name="cmd.skuName" /></td>
			</tr>
			<tr>
				<td class="label">
					商品条码
				</td>
				<td ><input loxiaType="input" trim="true" name="cmd.barcode" /></td>
				<td class="label">
					sn号/卡号
				</td>
				<td><input loxiaType="input" trim="true" name="cmd.sn" /></td>
			</tr>
			<tr>
			<td class="label">卡券状态</td>
				<td width="10%">
						<select loxiaType="select" id="statusList" name="cmd.intCardStatus">
							<option value="" selected="selected">-请选择-</option>
							<option value="0">未激活 </option>
							<option value="1">激活成功</option>
							<option value="21">已冻结 </option>
							<option value="25">已解冻</option>
							<option value="99">已作废 </option>
						</select>
					</td>
				</tr>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="search" ><s:text name="button.search"></s:text> </button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
	</div>
	<table id="tbl-orderList" width="100%"></table>
	<div id="pager"></div>
</body>
</html>