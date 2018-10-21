<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.inpurchase"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/vmi-return-gucci.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
<!-- 这里是页面内容区 -->
	<div id="div-sta-list" >
		<form id="form_query">
			<table>
				<tr>
					<td class="label">创建时间：</td>
					<td>
						<input loxiaType="date" trim="true" name="startTime" showTime="true"/></td>
					<td class="label" style="text-align:center">到</td>
					<td><input loxiaType="date" trim="true" name="endTime" showTime="true"/></td>
				</tr>
				<tr>
					<td class="label">相关单据号：</td>
					<td><input loxiaType="input" trim="true" name="slipCode" /></td>
					<td class="label">目标地址：</td>
					<td><input loxiaType="input" trim="true" name="toLoction" /></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<table id="tbl-inbound-purchase"></table>
		<div id="pager"></div>
	</div>
	<div id="order-detail" class="hidden">
		<table>
				<tr>
					<td width="30%" class="label">
						<div class="buttonlist">
							<form method="post" enctype="multipart/form-data" id="importForm" name="importForm" target="upload">
								<span class="label"><s:text name="label.warehouse.choose.file"></s:text>:</span>
								<input type="file" name="file" loxiaType="input" id="file" style="width:200px"/>
							</form>
						</div>
					</td>
					<td width="40%" colspan="1">
						<input id="rtoId" type="hidden" name="rtoId" />
						<button loxiaType="button" class="confirm" id="import"><s:text name="button.import"></s:text></button>
						<button loxiaType="button" id="expDiff">导出退仓明细</button>
					</td>
					<td width="10%" class="label">
					</td>
					
				</tr>
				
		</table>
		<br />
		<div id="detail_tabs">
				<table id="tbl-order-detail"></table>
				<div id="pagerDetail"></div>
		</div>
		<div class="buttonlist">
		    <button loxiaType="button"  id="back"><s:text name="button.back"></s:text></button>
		</div>
		<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>