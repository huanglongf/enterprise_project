<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title><s:text name="title.inventory.query"/></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="../scripts/frame/adidas-batch-outbound.js"></script>
<style type="text/css">

</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">

<div id="tabs">
		<div id="tab_1">
		
				<table align="center">
				<tr>
					<td class="label"  style="width: 200px;">配货批次号:</td>
					<td><input loxiatype="input"  name="" id="batchCode" style="width: 200px;"/></td>
				</tr>
				<tr>
					<td class="label" style="width: 200px;">耗材:</td>
					<td><input loxiatype="input"  name="" id="consumables" style="width: 200px;"/></td>
					<td><button loxiaType="button" class="confirm" id="outbound"><s:text name="出库"/></td>
					<td><button loxiaType="button" class="search" id="search"><s:text name="查询"/></td>
				</tr>
				<tr>
					<td class="label" style="width: 200px;">重量:</td>
					<td><input loxiatype="input"  name="" id="weight" style="width: 200px;"/></td>
				</tr>
				</table>
		</div>
		<div style="font-size: 40px;" id="div1">
			取消单据数量:<span id="div2" style="font-size: 60px;color: red" ></span>
		</div>
		<div id="tab_2">
		<table id="tab_cancel_sta_list" ></table>
		<div id="pager"></div>
		</div>
		<iframe id="upload" name="upload" class="hidden"></iframe>
	</div>
</body>
</html>