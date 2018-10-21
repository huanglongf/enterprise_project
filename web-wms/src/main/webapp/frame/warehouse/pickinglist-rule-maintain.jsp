<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../../common/meta.jsp"%>
<title><s:text name="baseinfo.warehouse.sales.dispatchList.title"/></title>

<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/pickinglist-rule-maintain.js' includeParams='none' encode='false'/>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	
<div id="div1">
	<form method="post" id="query-form">
		<span class="label" style="font-size: 15px;" >基础条件</span>
		<table id="distributionRuleTable">
			<tr>
				<td class="label">规则名称：</td>
				<td style= "width:200px">
					<input loxiaType="input" name="distributionRule.name" trim="true"/>
				</td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" id="btn-query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
		<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
	</div>
	<div id="div-ruleList">
		<table id="tbl-ruleList"></table>
		<div id="pager_query"></div>
	</div>
	<div class="buttonlist">
		<button type="button" id="btn-newRule" loxiaType="button" class="confirm"><s:text name="新建规则"></s:text> </button>
	</div>
</div>

<div id="div2" class="hidden">
	<%@include file="./pickinglist-rule-maintain-detail.jsp"%>
</div>

</body>
</html>