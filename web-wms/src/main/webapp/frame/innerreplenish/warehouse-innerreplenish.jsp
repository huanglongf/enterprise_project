<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/innerreplenish/warehouse-innerreplenish.js"' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div>
		<span class="label" style="font-size:15px">申请库内补货</span>
		<br/>
		<br/>
		<form id="dataForm">
		<span class="label">类型:</span>
		<select id="wrType" name="wr.type" loxiaType="select" style="width:100px">
			<option value="NORMAL">库内补货</option>
			<option value="PICKING_FAILED">配货失败补货</option>
		</select>
		<br/>
		<br/>
		<span class="label">库内补货安全警戒线<font style="color:#f00">(1-100)</font>：</span>
		<input id="wrWarningPre" loxiaType="number" min="0" max="100" style="width:100px" name="wr.warningPre"/><span class="label" style="color:#f00">%</span>
		<br/>
		<br/>
		</form>
		<button id="apply" loxiaType="button" class="confirm">申请</button>
		<br/>
		<br/>
		<table id="wh_re_table"></table>
		<div id="pager_query"></div>
	</div>
	<iframe id="export" name="export" style="display:none;"></iframe>
</body>
</html>