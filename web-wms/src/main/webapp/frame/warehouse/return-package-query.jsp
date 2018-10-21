<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>

<title>退换货快递登记查询</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/return-package-query.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id = "div1">
		<form id="queryForm" name="queryForm">
			<table  cellpadding='10%'>
				<tr>
					<td width="15%" class="label">登记时间</td>
					<td width="20%"><input loxiaType="date" name="comm.createTime" showTime="true"></input></td>
					<td width="10%" class="label" style="text-align: center;">至 </td>
					<td width="20%"><input loxiaType="date" name="comm.endCreateTime" showTime="true"/></td>
					<td width="15%" class="label">目标仓库</td>
					<td width="20%" >
						<input loxiaType="input" id="warehouseName" name="comm.warehouseName" >
					</td>
				</tr>
				<tr>
					<td width="15%" class="label">作业单号</td>
					<td width="20%"><input loxiaType="input" name='comm.staCode' trim="true" id="staCode" /></td>
					<td class="label">快递单号:</td>
					<td>
						<input loxiaType="input" trim="true" name="comm.trackingNo" id="trackingNo" />
					</td>
					<td class="label" style="font-size: 14px">批次号:</td>
					<td style="font-size: 14px">
						<input loxiaType="input" trim="true" name="comm.batchCode" id="batchCode" />
					</td>
				</tr>
				<tr>
					<td class="label" style="font-size: 14px">物流商:</td>
					<td style="font-size: 14px">
						<select loxiaType="select" name='comm.lpcode' id="lpcode">
							<option value="" selected="selected">请选择</option>
						</select>
					</td>
					<td class="label">状态 </td>
					<td >
						<select id='rejection_reasons' name = 'comm.intStatus' loxiaType="select" >
							<option value="" selected="selected">请选择</option>
							<option value="0">已拒收</option>
							<option value="1">已收件</option>
							<option value="5">处理中</option>
							<option value="10">已入库</option>
						</select>
					</td>
					<td class="label">拒收原因</td>
					<td >
						<select id='rejection_reasons' name="comm.rejectionReasons" loxiaType="select" id="lpcode">
							<option value="" selected="selected">请选择</option>
							<option value="空包裹" >空包裹</option>
							<option value="非本公司商品" >非本公司商品</option>
							<option value="其他" >其他</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">用户名:</td>
					<td>
						<input loxiaType="input" trim="true" name="comm.userName" id="userName" />
					</td>
					<td class="label">登记物理仓:</td>
					<td width="180px"><select id="selTransOpc"  loxiaType="select" loxiaType="select" name="comm.whName">
							<option value="">--请选择仓库--</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button id="search" loxiaType="button" class="confirm" staId="">查询</button>
			<button type="button" loxiaType="button" id="reset">重置 </button>
		</div>
		<table id="return_package_list"></table>
		<div id="pager"></div>
	</div>
	<div id = "div2">
		<table>
			<tr>
				<td class="label" style="font-size: 14px">运单号:</td>
				<td id="trackNoId" width="60"></td>
			</tr>
			<tr>
				<td width="10%" class="label" style="font-size: 14px">登记类型:</td>
				<td width="30%" style="font-size: 14px">
							<label><input  name="type" id = "radio1" type="radio" value="已录入" />录入</label>   
							<label><input  name="type" id = "radio2"type="radio" value="已拒收" />拒收</label> 
				</td>
			</tr>
			<tr>
				<td id="returnId" width="60" hidden = "true"></td>
			</tr>
		</table>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="save" >保存</button>
		<button type="button" loxiaType="button" id="toback" ><s:text name="button.toback"/></button>
	</div>
	</div>
</body>
</html>