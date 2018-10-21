
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@include file="/common/meta.jsp"%>
		<title><s:text name="title.warehouse.pl.verify"/></title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/picking_sku_suggestion.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
		<style type="text/css">
			.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 24px; font-weight: bold;
			}
		</style>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<div id="picking-list">
				<table width="80%" id="queryTable">
					<tr>
						<td class="label" width="10%">批次号：</td>
						<td width="20%"><input loxiaType="input" name="plCmd.code" id="pickinglistCode" trim="true"/></td>
						<td class="label" width="10%">创建时间 从：</td>
						<td width="20%"><input loxiaType="date" name="plCmd.createTime1" id="createTimeStart" showTime="true"/></td>
						<td class="label" width="10%">至：</td>
						<td width="20%"><input loxiaType="date" name="plCmd.pickingTime1" id="createTimeEnd" showTime="true"/></td>
					</tr>
				</table>
				<div class="buttonlist">
					<button id="search" type="button" loxiaType="button" class="confirm"><s:text name="button.query"/></button>
					<button id="reset" type="reset" loxiaType="button"><s:text name="button.reset"/></button>
				</div>
				
				<span class="label">快速查询 批次号输入：</span><input loxiaType="input" name="plCmd.code" id="pickinglistCodeQuery" trim="true" style="width: 150px;"/>
				
				<table id="tbl-dispatch-list"></table>
				<div id="page1"></div>
		</div>
		<input type="hidden" id="plid" />
		<input type="hidden" id="staPgindex" />
		
		<div id="detail" class="hidden">
			<table width="80%">
				<tr>
					<td width="20%" class="label">批次号：</td>
					<td width="30%" id="plCode"></td>
					<td width="20%" class="label">创建时间：</td>
					<td width="30%" id="plCreateTime"></td>
				</tr>
				<tr>
					<td class="label">创建人：</td>
					<td id="plCreateUser"></td>
					<td class="label">计划单据数：</td>
					<td id="plBillCount"></td>
				</tr>
				<tr>
					<td class="label">SKU条码：</td>
					<td><input id="barCode" loxiaType="input" style="width: 150px" trim="true" /></td>
					<td></td><td></td>
				</tr>
			</table>
			
			<table id="tbl-detail-list"></table>
			<div class="buttonlist">
				<button id="back" type="button" loxiaType="button" class="confirm">返回</button>
			</div>
		</div>
		<div id="pl_check_dialog" style="text-align: center;">
		    <div id="showinfo" class="tips"></div>
		    <div id="inputBarCode">SKU条码：<input id="barCode0" loxiaType="input" style="width:240px; height: 35px; line-height: 35px; font-size: 24px;" trim="true"/></div>
		    <button loxiaType="button" class="confirm hidden" id="doCheck0">确认</button>
		    <input id="checkValue" loxiaType="input" style="width:100px; height: 35px; line-height: 35px; font-size: 24px;" trim="true" class="hidden"/>
		  </div>
	</body>
</html>