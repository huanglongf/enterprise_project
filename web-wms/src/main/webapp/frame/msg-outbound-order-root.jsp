<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/msg-outbound-order-root.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.sta.query"></s:text></title>
<style type="text/css">
	.divFloat{
		float: left;
		margin-right: 10px;
	}
	.divForm{
	   clear:both
	}
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath()%>">
<iframe id="upload" name="upload" class="hidden"></iframe>

<div id="tabs">
	<ul>
		<li><a href="#tab-1"><s:text name="title.warehouse.msg.outbound.notice"/></a></li>
		<li><a href="#tab-2"><s:text name="title.warehouse.msg.outbound.rtn"/></a></li>
	</ul>
	<div id="tab-1">
		<div class="divForm">
		  <s:text id="pselect" name="label.please.select"/>
			<form id="quert-form" method="post">
					<table width="90%">
						<tr>
							<td class="label" width="13%"><s:text name="label.warehouse.msg.stacode"/></td>
							<td width="20%"><input loxiaType="input" trim="true" name="msgCmd.staCode" id="staCode" /></td>
							<td class="label" width="13%"><s:text name="label.warehouse.msg.slipcode"/></td>
							<td width="20%"><input loxiaType="input" trim="true" name="msgCmd.slipCode" id="slipCode"/></td>
						</tr>
						<tr>
							<td class="label" width="13%">省</td>
							<td width="20%"><input loxiaType="input" trim="true" name="msgCmd.province" id="staCode" /></td>
							<td class="label" width="13%">市</td>
							<td width="20%"><input loxiaType="input" trim="true" name="msgCmd.city" id="slipCode"/></td>
						</tr>
						<tr>
				            <td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
							<td colspan="3">
								<input loxiaType="date" style="width: 150px" showtime="true" name="startDate" id="startDate" />
								<s:text name="label.warehouse.pl.to"/>
								<input loxiaType="date" style="width: 150px" showtime="true" name="endDate" id="endDate" />
							</td>
						</tr>
						<tr>
						    <td class="label" width="10%"><s:text name="label.warehouse.pl.status"/></td>
							<td width="20%"><s:select list="msgStatus" loxiaType="select" listKey="optionKey" listValue="optionValue" name="msgCmd.intStatus" id="status" headerKey="-2" headerValue="%{pselect}"/></td>
							<td colspan="2"></td>
							<td class="label" width="10%">选择仓库</td>
							<td><select id="selVMIWarehouse" name="ouId" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select></td>
						</tr>		
				</table>
			</form>
			<div class="buttonlist">
				<button type="button" id="query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
				<button type="button" id="reset" loxiaType="button"><s:text name="button.reset"></s:text> </button>
				<br /><br />
				<table id="tbl-inventory-query"></table>
				<div id="pager1"></div>
			</div>
		</div>
	</div>
	<div id="tab-2">
		<div class="divForm">
			<form id="quert-form2" method="post">
					<table width="90%">
						<tr>
							<td class="label" width="13%"><s:text name="label.warehouse.msg.stacode"/></td>
							<td width="20%"><input loxiaType="input" trim="true" name="msgRtnCmd.staCode" id="rtnStaCode" /></td>
							<td class="label" width="13%"><s:text name="label.warehouse.msg.slipcode"/></td>
							<td width="20%"><input loxiaType="input" trim="true" name="msgRtnCmd.slipCode" id="rtnSlipCode"/></td>
						</tr>	
						<tr>
				            <td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
							<td colspan="3">
								<input loxiaType="date" style="width: 150px" name="startDate" showtime="true" id="rtnStartDate"/>
								<s:text name="label.warehouse.pl.to"/>
								<input loxiaType="date" style="width: 150px" name="endDate" showtime="true" id="rtnEndDate"/>
							</td>
						</tr>
						<tr>
						    <td class="label" width="10%"><s:text name="label.warehouse.pl.status"/></td>
							<td width="20%"><s:select list="msgStatus" loxiaType="select" listKey="optionKey" listValue="optionValue" name="msgRtnCmd.intStatus" id="rtnStatus" headerKey="-2" headerValue="%{pselect}"/></td>
							<td class="label" width="10%">选择仓库</td>
							<td><select id="selVMIWarehouse2" name="ouId" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select></td>
						</tr>		
				</table>
			</form>
			<div class="buttonlist">
				<button type="button" id="query2" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
				<button type="button" id="reset2" loxiaType="button"><s:text name="button.reset"></s:text> </button>
				<br /><br />
				<table id="tbl-inventory-query2"></table>
				<div id="pager2"></div>
			</div>
		</div>
	</div>
</div>
</body>
</html>