<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="title.cache_ticket_print"/></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="../scripts/frame/cache-ticket-print.js"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<form id="quert-form" method="post">
		<table width="90%">
			<tr>
				<td class="label" width="13%">作业单编号 </td>
				<td><input loxiaType="input" trim="true" name="sta.code" id="staCode"/></td>
				
				<td class="label" width="13%">创建时间 </td>
				<td width="20%"><input loxiaType="date" name="sta.createTime" showTime="true"/></td>
				<td class="label" align="center"  width="13%">到 </td>
				<td width="20%"><input loxiaType="date" name="sta.endCreateTime" showTime="true"/></td>
			</tr>
			<tr>
				<td class="label" width="13%">相关单据号</td>
				<td>
					<input loxiaType="input" trim="true" name="sta.refSlipCode" id="refSlipCode"/>
				</td>
				<td class="label" width="13%">作业单状态 </td>
				<td>
					<select id="statusInt" name="sta.intStaStatus" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
						<option value="3">已核对</option>
						<option value="4">已转出</option>
						<option value="10">已完成</option>
					</select>
				</td>
				<td class="label" width="13%">作业单类型 </td>
				<td>
					<select id="statusInt" name="sta.intStaType" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
						<option value="21">销售出库</option>
						<option value="41">退货入库</option>
						<option value="42">换货出库</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" id="query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
		<button type="button" id="reset" loxiaType="button"><s:text name="button.reset"></s:text> </button>
	</div>
	<br />
	<table id="tbl-StaList"></table>
	<div id="pager"></div>

</body>
</html>