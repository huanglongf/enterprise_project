<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/goods-collection-log-query.js"' includeParams='none' encode='false'/>"></script>
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
					<td class="label">集货区域编码:</td>
					<td  ><input id="code" name="goodsCollectionLog.collectionCode" loxiaType="input" trim="true"/></td>
					<td class="label">批次号:</td>
					<td  ><input id="pickingCode" name="goodsCollectionLog.pickingCode" loxiaType="input" trim="true"/></td>
					<td class="label">周转箱编码:</td>
					<td  ><input id="pickingZoonCode" name="goodsCollectionLog.containerCode" loxiaType="input" trim="true"/></td>
					<td class="label">周转箱集货状态:</td>
					<td  >
						<select loxiaType="select" name="goodsCollectionLog.status">
							<option value="">全部</option>
							<option value="集货">集货</option>
							<option value="释放">释放</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">操作时间起:</td>
					<td  ><input id="startDateStr" loxiaType="date" name="startDateStr" showtime="true"/></td>
					<td class="label">至:</td>
					<td  ><input id="endDateStr" loxiaType="date" name="endDateStr" showtime="true"/></td>
					<td class="label">操作人:</td>
					<td  ><input id="supplierCode" name="goodsCollectionLog.loginName" loxiaType="input" trim="true"/></td>
					<td></td><td><font color="red">默认查询30天以内的数据</font></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" id="btn-query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
			<br /><br />
			<table id="tbl_cm_list"></table>
			<div id="pager"></div>
		</div>
<iframe id="sortPickingExport" class="hidden"></iframe>
</body>
</html>