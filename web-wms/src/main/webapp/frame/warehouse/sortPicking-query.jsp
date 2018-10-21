<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/sortPicking-query.js"' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<form id="queryForm">
			<table id="filterTable">
				<tr>
					<td class="label">PDA拣货条码:</td>
					<td  ><input id="code" name="pdaSortPickingCommand.code" loxiaType="input" trim="true"/></td>
					<td colspan="6"></td>
				</tr>
				<tr>
					<td class="label">配货批次号:</td>
					<td  ><input id="pickingCode" name="pdaSortPickingCommand.pickingCode" loxiaType="input" trim="true"/></td>
					<td class="label">拣货区域:</td>
					<td  ><input id="pickingZoonCode" name="pdaSortPickingCommand.pickingZoonCode" loxiaType="input" trim="true"/></td>
					<td class="label">商品名称:</td>
					<td  ><input id="skuName" name="pdaSortPickingCommand.skuName" loxiaType="input" trim="true"/></td>
					<td class="label">拣货时间起:</td>
					<td  ><input id="startDateStr" loxiaType="date" name="startDateStr" showtime="true"/><font color="red">默认查询5天以内的数据</font></td>
				</tr>
				<tr>
					<td class="label">货号:</td>
					<td  ><input id="supplierCode" name="pdaSortPickingCommand.supplierCode" loxiaType="input" trim="true"/></td>
					<td class="label">商品编码:</td>
					<td  ><input id="skuCode" name="pdaSortPickingCommand.skuCode" loxiaType="input" trim="true"/></td>
					<td class="label">条形码:</td>
					<td  ><input id="barCode" name="pdaSortPickingCommand.barCode" loxiaType="input" trim="true"/></td>
					<td class="label">拣货时间至:</td>
					<td  ><input id="endDateStr" loxiaType="date" name="endDateStr" showtime="true"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" id="btn-query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
			<button type="button" id="btn-export" loxiaType="button"><s:text name="button.export.result"></s:text> </button>
			<br /><br />
			<table id="tbl_cm_list"></table>
			<div id="pager"></div>
		</div>
<iframe id="sortPickingExport" class="hidden"></iframe>
</body>
</html>