<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/pickinglist-logquery.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	
<div id="div1">
	<!-- <form id="queryForm" name="queryForm"> -->
		<table id="filterTable">
			<tr>
				<td class="label" >创建时间</td>
				<td>
				<input  id="createTime" loxiaType="date"  name="whPickingBatchCommand.createTime"  trim="true" showTime="true"/>
				</td>
				<td class="label">到</td>
				<td>
					<input id="createTime2" loxiaType="date"  trim="true" showTime="true"/>
				</td>
				<td class="label" >拣货开始时间</td>
				<td>
				<input  id="startTime" loxiaType="date"  trim="true" showTime="true"/>
				</td>
				<td class="label">到</td>
				<td>
					<input id="startTime2" loxiaType="date" trim="true" showTime="true"/>
				</td>
			</tr>
			<tr>
			<td class="label">配货批次号</td>
				<td>
					<input id="pCode" loxiaType="input"  trim="true"/>
				</td>
			<td class="label">登录账号</td>
				<td>
					<input id="userName" loxiaType="input"  trim="true"/>
				</td>
			</tr>
		</table>
<!-- 	</form> -->
	<div class="buttonlist">
		<button type="button" id="btn-query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
			<font class="label" style="color:red">默认查询近三个月的记录，否则请加上时间条件！</font>
	</div>
	<%-- <span>输入配货批次号回车进入明细页面</span>
	<table width="25%">
		<tr><td class="label" style="color:red" width="100px;">配货批次号：</td><td><input loxiaType="input" id="pCode" trim="true"/></td></tr>
	</table> --%>
	<div id="div-waittingList">
		<table id="tbl-waittingList"></table>
		<div id="pager_query"></div>
	</div>
</div>

<div class="hidden">
	<OBJECT ID='emsObject' name='emsObject' CLASSID='CLSID:53C732B2-2BEA-4BCD-9C69-9EA44B828C7F' align=center hspace=0 vspace=0></OBJECT>
</div>
<iframe id="frmSoInvoice" class="hidden"></iframe>
<iframe id="exportFrame" class="hidden" target="_blank"></iframe>
<%-- 
<jsp:include page="/common/include/include-offline-reweight.jsp"></jsp:include>
<jsp:include page="/common/include/include-shop-query2.jsp"></jsp:include>
<jsp:include page="/common/include/include-department-query.jsp"></jsp:include> --%>
</body>
</html>