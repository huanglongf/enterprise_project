<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/pda-log.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="staInfo">
		<form id="query-form">
			<table>
				<tr>
					<td class="label">创建时间：</td>
					<td><input loxiaType="date" showtime="true" name="pc.beginTime"/></td>
					<td class="label">到</td>
					<td><input loxiaType="date" showtime="true" name="pc.beginTime1"/></td>
					<td class="label">完成时间：</td>
					<td><input loxiaType="date" showtime="true" name="pc.endTime"/></td>
					<td class="label">到</td>
					<td><input loxiaType="date" showtime="true" name="pc.endTime1"/></td>
					<td class="label">状态：</td>
					<td>
						<select loxiaType="select" style="width:120px" name="pc.status" id="sstatus">
							<option value="">--请选择状态--</option>
							<option value="CANCELED">取消</option>
							<option value="CREATED">新建</option>
							<option value="FINISHED">完成</option>
							<option value="ERROR">错误</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">单据号：</td>
					<td colspan="3"><input loxiaType="input" style="width:120px" name="pc.orderCode"/></td>
					<td class="label">作业类型：</td>
					<td colspan="5">
						<select loxiaType="select" style="width:120px" name="pc.type" id="stype">
							<option value="">--请选择类型--</option>
							<option value="Inbound">入库-收货</option>
							<option value="INBOUND_SHELEVES">入库-上架</option>
							<option value="InnerMove">库内移动</option>
							<option value="INITIATIVEMOVEOUT">主动移库出库</option>
							<option value="RETURN">退仓</option>
							<option value="HANDOVER">交接</option>
							<option value="INVENTORYCHECK">盘点</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset">重置</button>
		</div>
		<table id="tbl-staList-query"></table>
		<div id="pager_query"></div>
	</div>
	<div id="staLineInfo" class="hidden">
		<table id="filterTable">
			<tr>
				<td class="label" width="100">单据号：</td>
				<td width="100"><span id="s_code"></span></td>
				<td class="label" width="100">操作类型</td>
				<td width="100"><span id="s_type"></span></td>
			</tr>
		</table>
		<table id="tbl_detail_list"></table>
		<table id="tbl_handover_detail_list"></table>
		<div id="pager"></div>
		<div class="buttonlist">
			<button type="button" loxiaType="button" id="back" class="confirm"><s:text name="button.back"></s:text> </button>
		</div>
	</div>
</body>
</html>