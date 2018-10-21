<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title></title>
<script type="text/javascript"
	src="<s:url value='/scripts/frame/warehouse/priority_city_config.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.showborder {
	border: thin;
}

.divFloat {
	float: left;
	margin-right: 10px;
}
</style>

</head>
<body style="background-color: #f2f2f2;"
	contextpath="<%=request.getContextPath()%>">
	<!-- 这里是页面内容区 -->

	<div id="div-sta-list">
		<form id="form_query" method="post">
			<table>
				<tr>
					<td class="label">城市名称</td>
					<td><input loxiaType="input" trim="true"
						name="psccCommand.cityName" id="cityName"
						maxlength="80" style="width: 137%;" /></td>
<!-- 					<span style="float: right; margin-right: 900px; padding-top: 5px; color: red; font-weight: bold;"> -->
<!-- 						注意：填写请不要包含"市"字样 便于后续的数据匹配逻辑 (北京市，仅录入北京) -->
<!-- 					</span> -->
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="add">新增</button>
		</div>
		<table id="tbl-inbound-purchase"></table>
		<div id="pagerCity"></div>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="remove">删除</button>
		</div>
	</div>

</body>
</html>