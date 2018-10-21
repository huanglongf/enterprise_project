<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:url value='/scripts/common/include/include-department-query.js' includeParams='none' encode='false'/>"></script>
<div id="departQueryDialog" style="display: none;">
	<form id="departQueryDialogForm" name="departQueryDialogForm">
		<table width="95%">
			<tr>
				<td class="label">
					部门名称
				</td>
				<td>
					<input loxiaType="input" trim="true" name="optionValue"/>
				</td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="btnDepartQuery" >查询</button>
		<button type="button" loxiaType="button" id="btnDepartFormRest" >重置</button>
	</div>
	<table id="tbl_depart_query_dialog"></table>
	<div id="tbl_depart_query_dialog_pager"></div>
	<br/>
	<button type="button" loxiaType="button" class="confirm" id="btnDepartQueryConfirm" >确认</button>
	<button type="button" loxiaType="button" id="btnDepartQueryClose" >取消</button>
</div>

