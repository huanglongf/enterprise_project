<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/return-reason-type.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script> 
<title><s:text name="title.warehouse.sta.query"></s:text></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<!-- 这里是页面内容区 -->
	<div id="div-sta-list">
		<form id="form_query">
			<table>
				<tr>
					<td class="label">作业单编码</td>
					<td><input loxiaType="input" trim="true" name="sta.code" id='code'/></td>
					<td class="label">退货原因</td>
					<td width="30%" >
					<select name="returnReasonType" id="returnReasonType" loxiaType="select">
						<option value=""><s:text name="label.wahrhouse.sta.select"></s:text></option> 
						<option value="1">7天无理由退/换货</option>
						<option value="2">商品质量问题</option>
						<option value="3">实物与订购商品不符</option>
						<option value="4">非COD订单客户拒收</option>
						<option value="5">COD订单客户拒收</option>
						<option value="6">三十天无理由退货</option>
						<option value="7">COD正常退货</option>
						<option value="99">其他</option>
					</select>
			</td>
				</tr>
				<tr>
					<td class="label" style="color:blue">相关单据号（申请单号）</td>
					<td width="30%"><input loxiaType="input" trim="true" name="sta.slipCode" id='slipCode'/></td>
					<td class="label" width="20%">退货运单号</td>
					<td width="30%"><input loxiaType="input" trim="true" name="sta.trackingNo" id='trackingNo'/></td>
				</tr>
				<tr>
					<td class="label" width="20%">订单号（相关单据1）</td>
					<td width="30%"><input loxiaType="input" trim="true" name="sta.slipCode1" id='slipCode1'/></td>
					<td class="label" width="22%" style="color:blue">平台单据号（相关单据2）</td>
					<td width="28%"><input loxiaType="input" trim="true" name="sta.slipCode2" id='slipCode2'/></td>
				</tr>
				<tr>
					<td class="label">创建时间</td>
					<td>											  
						<input loxiaType="date" trim="true" name="createTime" showTime="true" id='createTime'/></td>
					<td class="label" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
					<td><input loxiaType="date" trim="true" name="endCreateTime" showTime="true" id='endCreateTime'/></td>
				</tr>
				<!-- <tr>
					<td class="label" width="20%">退货运单号</td>
					<td>
						<input loxiaType="input" trim="true" name="trackingNo" />
					</td>
				</tr> -->
	
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<table id="tbl-orderList"></table>
		<div id="pager"></div>
		
</body>
</html>