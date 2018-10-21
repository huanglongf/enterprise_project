<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/expressRadar/express-warning-info.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
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
					<td class="label" width="20%">预警类型:</td>
					<td>
						<select name="lpcode" id="selLpcode" loxiaType="select">
							<option value="">请选择</option>
							<option value="STO">申通</option>
							<option value="SF">顺丰</option>
							<option value="EMS">EMS</option>
						</select>
					</td>
					<td class="label" width="20%">预警级别:</td>
					<td><select name="code" id="code" loxiaType="select">
							<option value="">请选择</option>
							<option value="STO">001</option>
							<option value="SF">110</option>
							<option value="EMS">123</option>
						</select>
					</td>
				</tr>
				
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search">新增</button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<table id="tbl-inbound-purchase"></table>
		<div id="pager"></div>
		<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
	</div>
	
	<div id="dialog_create">
	<div class="buttonlist">
			<button loxiaType="button" id="delete">删除选中</button>
			<form id="form_query">
			<table>
				<tr>
					<td class="label" width="20%">预警类型:</td>
					<td>
						<select name="lpcode" id="selLpcode" loxiaType="select">
							<option value="">请选择</option>
							<option value="STO">申通</option>
							<option value="SF">顺丰</option>
							<option value="EMS">EMS</option>
						</select>
					</td>
					<td class="label" width="20%">预警级别:</td>
					<td>
						<select name="code" id="code" loxiaType="select">
							<option value="">请选择</option>
							<option value="STO">001</option>
							<option value="SF">110</option>
							<option value="EMS">123</option>
						</select>
					</td>
					<td class="label" width="20%">备注:</td>
					<td><input loxiaType="input" trim="true" name="sta.refSlipCode" /></td>
				</tr>
				
			</table>
			<button loxiaType="button" class="confirm" id="search">创建</button>
		</form>
		</div>
	</div>
	<div id="div-warning-detail" class="hidden">
		<div>
		<table>
				<tr>
					<td class="label" width="20%">预警类型:</td>
					<td>
						呵呵
					</td>
					<td class="label" width="20%">初始预警级别:</td>
					<td>
						1级
					</td>
					<td class="label" width="20%">备注:</td>
					<td></td>
				</tr>
				
		</table>
		</div>
		<div id="settingListtable">
			<!-- <table id="tb2-warning-setting"></table> -->
		</div>
		<div class="buttonlist">
			<table>
				<tr>
					<td><button loxiaType="button" id="delete">确认设置</button></td>
					<td><button loxiaType="button" id="delete">返回</button></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>