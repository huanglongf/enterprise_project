<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>上架弹出口维护</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/automaticEquipment/popUpArea-edit.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
					<td class="label">编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="popUpAreaCommand.code" id="code" maxlength="80"/>
					</td>
					<td class="label">条码:</td>
					<td>
						<input loxiaType="input" trim="true" name="popUpAreaCommand.barcode" id="barcode" maxlength="80"/>
					</td>
					<td class="label" >名称:</td>
					<td>
						<input loxiaType="input" trim="true" name="popUpAreaCommand.name" id="name" maxlength="80"/>
					</td>
					<td class="label" >WCS编码:</td>
					<td>
						<td><input loxiaType="input" trim="true" name="popUpAreaCommand.wscPopCode" id="wscPopCode" maxlength="80"/></td>
					</td>
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
				<button loxiaType="button" class="confirm" id="update">修改</button>
		</div>
	</div>
	<div id="dialog_create">
		<div class="buttonlist">
			<form id="form_save">
			<table>
				<tr>
					
					<td class="label">编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="popUpAreaCommand.code" id="codeS" maxlength="80"/>
					</td>
					<td class="label">条码:</td>
					<td>
						<input loxiaType="input" trim="true" name="popUpAreaCommand.barcode" id="barcodeS" maxlength="80"/>
					</td>
					<td class="label" >名称:</td>
					<td>
						<input loxiaType="input" trim="true" name="popUpAreaCommand.name" id="nameS" maxlength="80"/>
					</td>
					<td class="label" >排序:</td>
					<td>
						<input loxiaType="input" trim="true" name="popUpAreaCommand.sort" id="sortS" maxlength="6"/>
					</td>
					<td class="label" >WCS编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="popUpAreaCommand.wscPopCode" id="wscPopCodeS" maxlength="80"/>
					</td>
					<td >
						<button loxiaType="button" class="confirm" id="save">新增</button>
					</td>
				</tr>
				
				
			</table>
		</form>
		</div>
		</div>
	<div id="dialog_update">
			<table>
				
				<tr>
					<td class="label">编码:</td>
					<td>
						<input type="hidden" name="popUpAreaCommand.id" id="idU" maxlength="80"/>
						<input loxiaType="input" trim="true" name="popUpAreaCommand.code" id="codeU" disabled="disabled" maxlength="80"/>
					</td>
					<td class="label">条码:</td>
					<td>
						<input loxiaType="input" trim="true" name="popUpAreaCommand.barcode" id="barcodeU" disabled="disabled" maxlength="80"/>
					</td>
					<td class="label" >名称:</td>
					<td>
						<input loxiaType="input" trim="true" name="popUpAreaCommand.name" id="nameU" maxlength="80"/>
					</td>
				</tr>
				<tr>	
					<td class="label" >排序:</td>
					<td>
						<input loxiaType="input" trim="true" name="popUpAreaCommand.sort" id="sortU" maxlength="6"/>
					</td>
					<td class="label" >WCS编码:</td>
					<td>
						<input loxiaType="input" trim="true" name="popUpAreaCommand.wscPopCode" id="wscPopCodeU" maxlength="80"/>
					</td>
					<td colspan="2">
						<button loxiaType="button" class="confirm" id="saveU">保存</button>
					</td>
				</tr>
			</table>
		</div>
</body>
</html>