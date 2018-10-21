<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="<s:url value='/scripts/common/include/include-sta-show.js' includeParams='none' encode='false'/>"></script>
<div id="staQueryDialog">
<input type="text" id="orderId"/>
		<table width="95%">
			<tr>
				<td class="label">
					批次号
				</td>
				<td>
					<input loxiaType="input" trim="true" id="code"/>
				</td>
			</tr>
			<tr>
				<td class="label">
					业务类型
				</td>
				<td>
					<input loxiaType="input" trim="true" id="businessType2"/>
				</td>
			</tr>
		</table>
	<table id="tbl_sta_query_dialog"></table>
	<div id="tbl_sta_query_dialog_pager"></div>
	<br/>
<!-- 	<button type="button" loxiaType="button" class="confirm" id="btnShopQueryConfirm" >确认</button> -->
	<button type="button" loxiaType="button" id="btnStaQueryClose" >取消</button>
</div>

