<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="title.warehouse.other.query"/></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/GI-query.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath()%>">
	<!-- 这里是页面内容区 -->
	<div id="GILocation">
		<form id="form_query">
			<table>
				<tr>
					<td class="label" width="20%"><s:text name="label.warehouse.pl.sta"/></td>
					<td width="20%"><input loxiaType="input" trim="true" name="loc.staCode" /></td>
					<td class="label" width="20%">相关单据号</td>
					<td width="20%"><input loxiaType="input" trim="true" name="loc.staSlipCode" /></td>
				</tr>
				<tr>
					<td class="label">存放时间</td>
					<td><input loxiaType="date" trim="true" name="loc.createDate1" showTime="true"/></td>
					<td class="label" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
					<td><input loxiaType="date" trim="true" name="loc.endCreateDate1" showTime="true"/></td>
				</tr>
				<tr>
					<td class="label">库位 </td>
					<td><input loxiaType="input" trim="true" name="loc.code"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<br />
		<div>
			<table id="tbl_gi_location"></table>
			<div id="pager"></div>
		</div>
	</div>
	<br />
	<div id="details" class="hidden">
		<table>
			<tr>
				<td class="label" width="10%">库位</td>
				<td width="20%" id="code">&nbsp;</td>
				<td class="label" width="10%">存放时间</td>
				<td width="20%" id="createDate">&nbsp;</td>
				<td><input type="hidden" id="locId" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td class="label">作业单</td>
				<td id="staCode">&nbsp;</td>
				<td class="label">前置单据</td>
				<td id="staSlipCode">&nbsp;</td>
				<td class="label">商品数量</td>
				<td id="skuQty">&nbsp;</td>
			</tr>
		</table>
		<br />
		<div>
			<table id="tbl_details"></table>
			<div id="parger1"></div>
		</div>
		<div class="buttonlist">
			<button type="button" loxiaType="button" id="createInStaDIV" class="confirm">创建入库上架单</button>
			<button type="button" loxiaType="button" id="back"><s:text name="button.back"/></button>
		</div>
	</div>
	<div id="download" style="clear: both;"></div>
	<iframe id="upload" name="upload" style="display:none;"></iframe>
</body>
</html>