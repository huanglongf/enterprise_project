<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/expressRadar/express-city-edit.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	
<div id="div1">
	<form method="post" id="query-form">
		<table id="filterTable">
			<tr>
				<td class="label" width="65">省：</td>
				<td>
					<input loxiaType="input" name="province" id="province" trim="true"/>
				</td>
				<td class="label" width="65">市：</td>
				<td>
					<input loxiaType="input" name="city" id="city" trim="true">
				</td>
				
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" id="btn-query" loxiaType="button" class="confirm">查询</button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
	</div>
	<div id="div-waittingList">
		<table id="tbl-waittingList"></table>
		<div id="pager_query"></div>
	</div>
	<div class="buttonlist">
		<button type="button" id="btn-del" loxiaType="button" class="confirm">选中删除</button>
	</div>
	<table id="filterTable1" width ="600px" style="margin-top: 10px;" > 
	    <tr><td><br/></td></tr>
		<tr>
		   
			<td class="label" width = "10%">省：</td>
			<td width = "20%">
				<input loxiaType="input" id = "addProvince" name="addProvince" trim="true"/>
			</td>
<!-- 			<td>
			<font id="addProvinceFont" color="red"></font>
			</td>
			 -->
			<td class="label" width = "10%">市：</td>
			<td width = "20%">
				<input loxiaType="input" id = "addCity" name="addCity" trim="true"/>
			</td>
			<td class="label" width = "20%"></td>
			<td>
				<button type="button" id="btn-add" loxiaType="button" class="confirm">确定</button>
			</td>
		</tr>
	</table>
</div>
<iframe id="frmSoInvoice" class="hidden"></iframe>
<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
</body>
</html>