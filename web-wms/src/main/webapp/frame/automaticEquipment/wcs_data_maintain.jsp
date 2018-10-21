<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/automaticEquipment/wcs_data_maintain.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.showborder {
	border: thin;
}
.divFloat{
	float: left;
	margin-right: 10px;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->


	<div id="div-sta-list">
		<form id="form_query">
			<table>
				
				<tr>
					<td class="label">批次号:</td>
					<td>
						<input loxiaType="input" trim="true" name="msgToWcsCommand.pickingListCode" id="pickingListCode" maxlength="80"/>
					</td>
					<td class="label">容器号:</td>
					<td>
						<input loxiaType="input" trim="true" name="msgToWcsCommand.containerCode" id="containerCode" maxlength="80"/>
					</td>
				</tr>
				
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
			<button loxiaType="button" class="confirm" id="resetAll">一键重置</button>
		</div>
		<table id="tbl-inbound-purchase"></table>
		<div id="pager"></div>
		<!-- <div class="buttonlist">
				<button loxiaType="button" class="confirm" id="delete">删除</button>
				
		</div> -->
	</div>
</body>
</html>