<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/meta.jsp"%>
		<title>渠道信息管理</title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/sku-modify-log.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
		<style type="text/css">
			.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 40px; font-weight: bold;
			}
		</style>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<form id="channelForm" name="channelForm">
		<div id="bichannel-list">
				<table width="80%" id="queryTable">
					<tr>
						<td class="label" width="10%">SKU码：</td>
						<td width="20%"><input loxiaType="input" name = "code"  id="code" trim="true"/></td>
						<td class="label" width="10%">条形码：</td>
						<td width="20%"><input loxiaType="input" name = "barCode" id="barCode" trim="true"/></td>	
					</tr>
					<tr>	
						<td class="label" width="10%">起始时间：</td>
						<td width="20%"><input loxiaType="date" name = "beginDate" id="beginDate" trim="true" showTime="false"/></td>
						<td class="label" width="10%">截至时间：</td>
						<td width="20%"><input loxiaType="date" name = "endDate" id="endDate" trim="true" showTime="false"/></td>
					</tr>
				</table>
				<div class="buttonlist">
					<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
					<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
				</div>
				<table id="tbl-modify-list" ></table>
				<div id="pager"></div>
		</div>
		</form>
	</body>
</html>