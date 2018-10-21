<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>取消订单状态查询</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/vmi-outbound-order-cancel.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
   
	<div id="divHead">
	<input type="hidden" id="detialId" name="staId" />
	
	<form id="query_form" >
		<table width="80%">
			
			<tr>
				<td class="label" width="10%">
					创建日期
				</td>
				<td width="15%">
					<input id="q_startDate" loxiaType="date" trim="true" showTime="true"/>
				</td>
				<td class="label" width="10%">
				到
				</td>
				<td width="15%">
					<input id="q_endDate" loxiaType="date" trim="true" showTime="true"/>
				</td>
				<td class="label" id="store1">
					状态</td>
				<td>
					<select loxiaType="select" id='selectId'>
					   <option value="66" >请选择</option>
					   <option value="1">新建</option>
					   <option value="0">失败</option>
					   <option value="2">已发送</option>
					   <option value="10">完成</option>
					</select>
				</td>
			</tr>

			<tr>
				<td class="label">相关单据号</td>
				<td>
					<input id="q_slipCode" loxiaType="input" trim="true" />
				</td>
				<td class="label">
					作业单号
				</td>
				<td>
					<input id="q_staCode" loxiaType="input" trim="true" />
				</td>
				
				<td class="label">
					
				</td>
				<td>
				</td>
				<td class="label">
				</td>
				<td>
				</td>
			</tr>
		</table>
		</form>
		<div class="buttonlist">
			<button id="query" loxiaType="button" class="confirm">查询</button>
			<button id="reset" loxiaType="button">重置</button>
		</div>
		<table id="tbl_rec"></table>
		<div id="tbl_rec_page"></div>
		
	</div>
</body>
</html>