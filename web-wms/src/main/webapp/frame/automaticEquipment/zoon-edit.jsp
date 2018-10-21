<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>仓库区域维护</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/automaticEquipment/zoon-edit.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
					<td class="label" >编码:</td>
					<td>
						<td><input loxiaType="input" trim="true" name="zoonCommand.code" id="code" maxlength="80"/></td>
					</td>
					<td class="label" >名称:</td>
					<td>
						<td><input loxiaType="input" trim="true" name="zoonCommand.name" id="name" maxlength="80"/></td>
					</td>
					<!-- <td class="label" >仓库列表:</td>
					<td>
						<select id="wh" loxiaType="select" name="zoonCommand.ouId">
							<option value="">请选择</option>
						</select>
					</td> -->
				</tr>
				
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">查询</button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<table id="tbl-inbound-purchase"></table>
		<div id="pager"></div>
		<div class="buttonlist">
				<button loxiaType="button" class="confirm" id="delete">禁用选中</button>
				<button loxiaType="button" class="confirm" id="recover">取消禁用</button>
		</div>
	</div>
	<div id="dialog_create">
		<div class="buttonlist">
			<form id="form_save">
			<table>
				<tr>
					
					<td class="label" >编码:</td>
					<td>
						<td><input loxiaType="input" trim="true" name="zoonCommand.code" id="codeS" maxlength="80"/></td>
					</td>
					<td class="label" >名称:</td>
					<td>
						<td><input loxiaType="input" trim="true" name="zoonCommand.name" id="nameS" maxlength="80"/></td>
					</td>
					<!-- <td class="label" >仓库列表:</td>
					<td>
						<select id="whS" loxiaType="select" name="zoonCommand.ouId">
							<option value="">请选择</option>
						</select>
					</td> -->
					<td >
						<button loxiaType="button" class="confirm" id="save">新增</button>
					</td>
				</tr>
				
				
			</table>
		</form>
		</div>
		</div>
	<div id="dialog_update"  class="hidden">
		<div class="buttonlist">
			<form id="form_update">
			<table>
				
				<tr>
					
					<td class="label" width="20%">类型名称:</td>
					<td><input loxiaType="input" trim="true" id="name"/></td>
					<td>
						<button loxiaType="button" class="confirm" id="back">返回</button>
					</td>
				</tr>
			</table>
		</form>
		</div>
		</div>
</body>
</html>