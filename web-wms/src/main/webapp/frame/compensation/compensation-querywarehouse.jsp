<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/compensation/compensation-querywarehouse.js' includeParams='none' encode='false'/>"></script>
<div id="shopQueryDialog">
	<form id="shopQueryDialogForm" name="shopQueryDialogForm">
		<table width="95%">
			<tr>
				<td class="label">
					仓库名称
				</td>
				<td>
					<input loxiaType="input" trim="true" name="name"/>
				</td>
			</tr>
		</table>
	</form>
	<div class="buttonlist">
		<button type="button" loxiaType="button" class="confirm" id="btnShopQuery" >查询</button>
		<button type="button" loxiaType="button" id="btnShopFormRest" >重置</button>
	</div>
	<table id="tbl_shop_query_dialog"></table>
	<div id="tbl_shop_query_dialog_pager"></div>
	<br/>
	<button type="button" loxiaType="button" class="confirm" id="btnShopQueryConfirm" >确认</button>
	<button type="button" loxiaType="button" id="btnShopQueryClose" >取消</button>
</div>

