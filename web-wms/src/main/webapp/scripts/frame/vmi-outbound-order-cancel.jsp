<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>取消订单状态查询</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/vmi-outbound-order-cancel.js' includeParams='none' encode='false'/>"></script>
</head>

<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
   
	<div id="divHead">
	<input type="hidden" id="detialId" name="staId" />
	
	<form id="query_form" >
		<table width="80%">
			
			<tr>
				<td class="label" width="10%">
					开始日期
				</td>
				<td width="15%">
					<input id="q_startDate" loxiaType="date" trim="true"/>
				</td>
				<td class="label" width="10%">
				到
				</td>
				<td width="15%">
					<input id="q_startDate2" loxiaType="date" trim="true"/>
				</td>
				<td class="label" width="10%">
					结束日期
				</td>
				<td width="15%">
					<input id="q_endDate" loxiaType="date" trim="true"/>
				</td>
				<td class="label" width="10%">
				到
				</td>
				<td width="15%">
					<input id="q_endDate2" loxiaType="date" trim="true"/>
				</td>
			</tr>

			<tr>
				<td class="label">相关单据号</td>
				<td>
					<input id="q_code" loxiaType="input" trim="true" />
				</td>
				<td class="label">
					作业单号
				</td>
				<td>
					<input id="q_code" loxiaType="input" trim="true" />
				</td>
				<td class="label" id="store1">
					状态</td>
				<td>
					<select loxiaType="select" id='selectId' class="hidden">
					   <option value="" >请选择</option>
					</select>
				</td>
				</div>
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
	<div id="divDetial" class="hidden">
		<table width="60%">
			<tr>
				<td width="20%" class="label">
					帐期开始时间
				</td>
				<td width="20%" id="starteDate">
				</td>
				<td width="20%" class="label">
					帐期结束时间
				</td>
				<td width="20%" id="endDate">
				</td>
			</tr>
			<tr>				
				<td class="label">
					对帐单编码
				</td>			
				<td id="code">
				</td>
				<td class="label">
					店铺名称
				</td>
				<td id="shopname">
				</td>
			</tr>
		</table>
		<br/>
		<div id="tabs_view">
			<%@include file="/frame/include-reconcile.jsp"%> 
		</div>
		<div class="buttonlist">
			<button id="back" class="confirm" loxiaType="button">返回</button>
		</div>
		<br/>
		<table id="tbl_so"></table>
		<div id="tbl_so_page"></div>
	</div>
</body>
</html>