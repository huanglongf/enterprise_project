<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/district-location-print.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.outbound.package"/></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<div id="tabs">
			<ul>
				<li><a href="#tabs_1">库位条码打印</a></li>
				<li><a href="#tabs_2">库区条码打印</a></li>
			</ul>
			<div id="tabs_1">
				<form id="locationForm" name="locationForm">
					<table width="40%">
						<tr>
							<td class="label" width="13%">库位条码</td>
							<td width="20%"><input loxiaType="input"  trim="true" name="locCode" id="locCode"/></td>
						</tr>
					</table>
				</form>
				<div class="buttonlist">
					<button name="locQuery" id="locQuery" type="button" loxiaType="button" class="confirm">查询</button>
					<button id="locReset" type="button" loxiaType="button" >重置</button>
				</div>
				<table id="tb_loc"></table>
				<table id="tb_loc_page"></table>
			</div>
			<div id="tabs_2">
				<form id="districtForm" name="districtForm">
					<table width="40%">
						<tr>
							<td class="label" width="13%">库区条码</td>
							<td width="20%"><input loxiaType="input"  trim="true" name="dirstictCode" id="dirstictCode"/></td>
						</tr>
					</table>
				</form>
				<div class="buttonlist">
					<button name="districtQuery" id="districtQuery" type="button" loxiaType="button" class="confirm">查询</button>
					<button id="districtReset" type="button" loxiaType="button" >重置</button>
				</div>
				<table id="tb_district"></table>
				<table id="tb_district_page"></table>
			</div>
		</div>
</body>
</html>