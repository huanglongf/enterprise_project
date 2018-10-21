<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/crw-Export-Execl-index.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style type="text/css">
	#d{margin-left:   }
</style>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="divMain">
		<form id="formQuery" name="formQuery">
			<table width="70%">
				<tr>
				    <td class="label">店铺</td>
					<td>
					    <div style="float: left">
							<select id="shopLikeQuery" name="staCmd.owner" loxiaType="select" data-placeholder="*请输入店铺名称">
							</select>
						</div>						
					</td>
					<td class="label">计划发货时间</td>
					<td><input loxiaType="date" name="staCmd.startCreateTime1" id="startCreateTime1" showTime="true"></input></td>
					<td class="label" style="text-align: center;"><s:text name="label.warehouse.pl.to"></s:text> </td>
					<td><input loxiaType="date" name="staCmd.endCreateTime1" id="endCreateTime1" showTime="true"/></td>
					
				</tr>	
				<tr>	
				   <td class="label">LOAD KEY</td>
					<td><input loxiaType="input" name="staCmd.slipCode2" id="slip_code2" trim="true"/></td>
					<td class="label">LF orderkey</td>
					<td><input loxiaType="input" name="staCmd.refSlipCode" id="refSlipCode" trim="true"/></td>
					<td class="label">NIKE单据编号</td>
					<td><input loxiaType="input" name="staCmd.slipCode1" id="slip_code1" trim="true"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button name="query" id="query" type="button" loxiaType="button" class="confirm">查询</button>
			<button id="btnReset" type="button" loxiaType="button" >重置</button>
			<!-- <form method="post" enctype="multipart/form-data" id="importMergeForm" name="importMergeForm" target="upload">
				  <input type="file" loxiaType="input" id="mergeFile" name="file" style="width: 200px;"/>
			</form> -->
			<button loxiaType="button" id="expDiff">导出</button>
			
		</div>
		<table id="tbl_sta_package"></table>
		<div id="tbl_sta_package_page"></div>
	</div>
	
<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
</body>
</html>