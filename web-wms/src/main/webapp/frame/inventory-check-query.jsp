<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/inventory-check-query.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.inventory.check.query"/></title>
<style>
.clear{
	clear:both;
	height:0;
    line-height:0;
}
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="divPd">
		<form id="query_form" name="query_form">
			<table width="65%">
				<tr>
					<td class="label" width="15%">
						<s:text name="label.warehouse.pl.createtime"/>
					</td>
					<td width="15%">
						<input loxiaType="date" name="iccommand.startDate1" showTime="true"/>
					</td>
					<td class="label" width="15%">
						<s:text name="label.warehouse.inventory.check.to"/>
					</td>
					<td width="15%">
						<input loxiaType="date" name="iccommand.endDate1" showTime="true"/>
					</td>
					<td class="label" width="15%">
						<s:text name="label.warehouse.pl.status"/>
					</td>
					<td width="15%">
						<s:select loxiaType="select" list="invckStatuslist" name="iccommand.intStatus" id="intStatus" headerKey="" headerValue="请选择" listKey="optionKey" listValue="optionValue"></s:select>
					</td>			
				</tr>
				<tr>
					<td class="label">
						<s:text name="label.warehouse.inventory.check.code"/>
					</td>
					<td>
						<input name="iccommand.code" loxiaType="input" trim="true"/>
					</td>
					<td class="label">
						<s:text name="label.wahrhouse.sta.creater"/>
					</td>
					<td>
						<input name="iccommand.creatorName" loxiaType="input" trim="true"/>
					</td>
					<td class="label">
						<s:text name="label.warehouse.inventory.check.confirm.user"/>
					</td>
					<td>
						<input name="iccommand.confirmUser" loxiaType="input" trim="true"/>
					</td>
				</tr>
			</table>
		</form>			
		<div class="buttonlist">
			<button id="query" loxiaType="button" class="confirm"><s:text name="button.search"/></button>&nbsp;&nbsp;
			<button id="reset" loxiaType="button" class=""><s:text name="button.reset"/></button>
			<div class="clear"></div>
		</div>
		<table id="tbl_list"></table>
		<div id="pager"></div>
		<div class="clear"></div>
	</div>
	<div id="divList" class="hidden">
		<table width="65%">
			<tr>
				<td class="label">
					<s:text name="label.warehouse.inventory.check.code"/>
				</td>
				<td id="code"></td>
				<td class="label">
					<s:text name="label.warehouse.pl.status"/>
				</td>
				<td id="status">
				</td>
			</tr>
			<tr>
				<td class="label">
					<s:text name="label.wahrhouse.sta.creater"/>
				</td>
				<td id="creator">
				</td>
				<td class="label">
					<s:text name="label.warehouse.inventory.check.confirm.user"/>
				</td>
				<td id="confirmUser">
				</td>
			</tr>
			<tr>
				<td class="label">
					<s:text name="label.warehouse.pl.createtime"/>
				</td>
				<td id="createTime">
				</td>
				<td colspan="2"></td>
			</tr>
		</table>
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1"><s:text name="label.warehouse.inventoryCheck.location"></s:text> </a></li>
				<li><a href="#tabs-2"><s:text name="label.warehouse.inventoryCheck.result"></s:text> </a></li>
			</ul>
			<div id="tabs-1">
				<table id="tbl_detial"></table>
				<div id="pager1"></div>
			</div>
			<div id="tabs-2">
				<div id="dtl_tabs">
					<ul>
						<li><a href="#dtl_tabs1">库存盘点差异</a></li>
						<li><a href="#dtl_tabs4">库存盘点差异-数量</a></li>
						<li><a href="#dtl_tabs5">库存盘点调整数据</a></li>
					</ul>
					<div id="dtl_tabs1">
						<table id="tbl_ex_detial"></table>
						<div id="pagerEx"></div>
					</div>
					<div id="dtl_tabs4">
						<table id="tbl_inv_count"></table>
						<div id="pageCount"></div>
					</div>
						<div id="dtl_tabs5">
						<table id="tbl_inv_check"></table>
						<div id="pageCount"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="buttonlist">
			<button id="back" loxiaType="button" class="confirm"><s:text name="button.back"/></button>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>