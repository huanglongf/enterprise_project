<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title><s:text name="betweenlibary.title.inwarehouse"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/shopstore-maintain.js"' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="list">
		<form id="queryForm">
			<table id="filterTable">
				<tr>
					<td class="label" width="100px">门店编码:</td>
					<td width="200px"><input loxiaType="input" name="code" showtime="true"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" id="btn-query" loxiaType="button" class="confirm"><s:text name="button.search"></s:text> </button>
			<button type="button" loxiaType="button" id="reset"><s:text name="button.reset"></s:text> </button>
			<br /><br />
			<table id="tbl_store_list"></table>
			<br/>
			<button type="button" id="add" loxiaType="button" class="confirm">添加门店信息</button>
		</div>
	</div>
	<div id="detail" class="hidden">
		<form id="storeDetail">
			<table>
				<tr>
					<td class="label" width="100px" id="textCode">门店编码:</td>
					<td width="200px" ><input loxiaType="input" id="storecode" name="ssi.code" showtime="true" readonly="true"/></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td class="label" width="100px">门店名称:</td>
					<td width="200px"><input loxiaType="input" id="name" name="ssi.name" showtime="true"/></td>
					<td class="label" width="100px">国家:</td>
					<td width="200px"><input loxiaType="input" id="country" name="ssi.country" showtime="true"/></td>
				</tr>
				<tr>
					<td class="label" width="100px">省:</td>
					<td width="200px"><input loxiaType="input" id="province" name="ssi.province" showtime="true"/></td>
					<td class="label" width="100px">市:</td>
					<td width="200px"><input loxiaType="input" id="city" name="ssi.city" showtime="true"/></td>
				</tr>
				<tr>
					<td class="label" width="100px">区:</td>
					<td width="200px"><input loxiaType="input" id="district" name="ssi.district" showtime="true"/></td>
					<td class="label" width="100px">联系人:</td>
					<td width="200px"><input loxiaType="input" id="receiver" name="ssi.receiver" showtime="true"/></td>
				</tr>
				<tr>
					<td class="label" width="100px">联系电话:</td>
					<td width="200px"><input loxiaType="input" id="telephone" name="ssi.telephone" showtime="true"/></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td class="label" width="100px">联系地址:</td>
					<td colspan="3"><input loxiaType="input" id="address" name="ssi.address" showtime="true"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button type="button" id="edit" loxiaType="button" class="confirm">修改</button>
			<button type="button" id="save" loxiaType="button" class="confirm hidden">保存</button>
			<button type="button" loxiaType="button" id="back">返回</button>
		</div>
	</div>
</body>
</html>