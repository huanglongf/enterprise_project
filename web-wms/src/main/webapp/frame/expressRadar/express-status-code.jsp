<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title>快递状态代码查询</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/expressRadar/express-status-code.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
					<td class="label" width="10%">物流服务商:</td>
					<td>
						<select id="lpcodeQ" loxiaType="select">
							<option value="">请选择</option>
						</select>
					</td>
					<td class="label"  width="10%">代码:</td>
					<td><select id="sysRscId" loxiaType="select">
							<option value="">请选择</option>

						</select>
					</td>
					<td class="label" width="10%">描述:</td>
					<td><input loxiaType="input" trim="true" id="describe"/></td>
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
				<button loxiaType="button" class="confirm" id="delete">删除选中</button>
		</div>
	</div>
	<div id="dialog_create">
		<div class="buttonlist">
			<form id="form_save">
			<table>
				<tr>
					<td class="label" width="20%">物流服务商:</td>
					<td>
						<select name="rrsrCommand.expCode" id="lpcodeS" loxiaType="select">
							<option value="">请选择</option>
						</select>
					</td>
					<td class="label" width="20%">代码:</td>
					<td>
						<select name="rrsrCommand.sysRscId" id="sysRscIdS" loxiaType="select">
							<option value="">请选择</option>
							
						</select>
					</td>
					<td rowspan="2">
						<button loxiaType="button" class="confirm" id="save">新增</button>
					</td>
				</tr>
				<tr>
					<td class="label" width="20%">描述:</td>
					<td><input loxiaType="input" trim="true" id="describeS" readonly="readonly" /></td>
					<td class="label" width="20%">备注:</td>
					<td><input loxiaType="input" trim="true" id="remarkS" name="rrsrCommand.remark"/></td>
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
					<td class="label" width="20%">物流服务商:</td>
					<td>
						<input type="text" id="lpcodeU" loxiaType="input" readonly="readonly"/>
							
					</td>
					<td class="label" width="20%">代码:</td>
					<td>
						<input type="text" id="sysRscIdU" loxiaType="input" readonly="readonly"/>
					</td>
					<td>
						<input type="hidden" id="idU"/>
						<button loxiaType="button" class="confirm" id="update">修改</button>
					</td>
				</tr>
				<tr>
					<td class="label" width="20%">描述:</td>
					<td><input loxiaType="input" id="describeU" trim="true" readonly="readonly"/></td>
					<td class="label" width="20%">备注:</td>
					<td><input loxiaType="input" trim="true" id="remarkU"/></td>
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