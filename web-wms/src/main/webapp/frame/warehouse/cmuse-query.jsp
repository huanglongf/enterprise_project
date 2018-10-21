<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/cmuse-query.js"' includeParams='none' encode='false'/>"></script>
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
					<td class="label" width="13%">出库时间:</td>
					<td width="20%"><input loxiaType="date" name="fromDate" showtime="true"/></td>
					<td class="label" width="13%">到</td>
					<td width="20%"><input loxiaType="date" name="toDate" showtime="true"/></td>
					<td class="label" width="13%">相关单据号:</td>
					<td width="20%"><input name="cm.slipCode" loxiaType="input" trim="true"/></td>
				</tr>
				<tr>
					<td class="label" width="13%">推荐箱型:</td>
					<td width="20%"><input loxiaType="input" name="cm.recommandSku" trim="true"/></td>
					<td class="label" width="13%">实用箱型:</td>
					<td width="20%"><input loxiaType="input" name="cm.usedSku" trim="true"/></td>
					<td class="label" width="13%">匹配情况:</td>
					<td width="20%">
						<select loxiaType="select" name="cm.isMatch">
							<option value="">--默认无推荐耗材--</option>
							<option value="是">是</option>
							<option value="否">否</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="label" width="13%">复核者:</td>
					<td width="20%"><input loxiaType="input" name="cm.checkUser" trim="true"/></td>
					<td class="label" width="13%">称重者:</td>
					<td width="20%"><input loxiaType="input" name="cm.outboundUser" trim="true"/></td>
					<td colspan="2"></td>
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
</body>
</html>