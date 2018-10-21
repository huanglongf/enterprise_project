
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@include file="/common/meta.jsp"%>
		<title>渠道后置打印配置</title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/channel-post-print-conf.js' includeParams='none' encode='false'/>"></script>
		<style type="text/css">
			.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 40px; font-weight: bold;
			}
		</style>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="dialog">
		<div id="channel-ref-list">
			<table id="tbl-channel-ref-list"></table>
			<div id="pager"></div>
		</div>
		<div class="buttonlist">
			<button id="save" type="button" loxiaType="button"
				class="confirm">保存</button>
		</div>
	</div>
</body>
</html>