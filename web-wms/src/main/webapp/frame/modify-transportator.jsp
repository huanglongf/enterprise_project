<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/modify-transportator.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
<title><s:text name="title.warehouse.location.transtype"/></title>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div>
	<br />
		<table width="60%">
			<tr>
				<td class="label" width="25%"><s:text name="快递单号"></s:text></td>
				<td width="25%"><input loxiaType="input" mandatory="true" trim="true" id="expressOrder1" name="trackingNo1" /></td>
				<td><b><p style="color: red">*支持分包裹转物流 </p></b></td>
				<td></td>
			</tr>
			<tr>
				<td class="label" width="25%"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text> </td>
				<td width="25%"><input loxiaType="input" mandatory="true" trim="true" id="expressOrder" name="trackingNo" /></td>
				<td width="25%">&nbsp;</td>
				<td width="25%">&nbsp;</td>
			</tr>
				<tr>
				<td class="label"><s:text name="label.warehouse.pl.refcode"></s:text> </td>
				<td id="aboutOrder"></td>
				<td class="label"><s:text name="label.warehouse.sta.shop"></s:text> </td>
				<td id="aboutStore">&nbsp;</td>
			</tr>
		</table>
		<p id="expressNo" class="hidden"><span class="label"><s:text name="label.warehouse.pl.sta.delivery.code"></s:text>：</span><span id="expressNos"></span></p>
		<table id="tbl-orderInfo"></table>
		<br/>
		<table id="tbl-logisticsChange"></table>
		</br>
		<table id="tbl-logisticsChangeNew"></table>
		</br>
		<table>
			<tr>
				<td class="label"><b><s:text name="原始面单"></s:text></b></td>
				<td><input loxiaType="input" mandatory="true" trim="true" id="expressOrder2" name="oldTrackingNo" /></td>
				<td class="label"><b><s:text name="新面单"></s:text></b></td>
				<td><input loxiaType="input" mandatory="true" trim="true" id="expressOrder3" name="NewTrackingNo" /></td>
			</tr>
		</table>
	</div>
	
	<div id="new" class="hidden">
	
		<p><span class="label"><s:text name="label.wahrhouse.sta.lpcode.new"></s:text> </span>
			<select loxiaType="select" name="lpcode" id="select" style="width:200px">
				<option value="" selected="selected"><s:text name="label.wahrhouse.sta.select"></s:text> </option>
			</select>
		</p>
		
		<form id="logistics" name="logistics">
		<div id="edittable" loxiaType="edittable" style="width:350px;">
			<table operation="add,delete" append="1" id="tbl-newExpressInfo">
				<thead>
					<tr>
						<th><input type="checkbox"></th>
						<th><s:text name="label.wahrhouse.sta.trackingno.new"></s:text> </th>
						<th><s:text name="label.warehouse.package.weight"></s:text> </th>
					</tr>
				</thead>
				<tbody></tbody>
				<tbody>
					<tr>
						<td><input type="checkbox"/></td>
						<td><input type="text" loxiaType="input" class="newExpress" mandatory="true" trim="true" checkmaster="checkTrackingNo"/></td>
						<td><input type="text" loxiaType="input" class="weight" mandatory="true" trim="true" checkmaster="checkWeight"/></td>
					</tr>
				</tbody>
			</table>
		</div>
		<br/>
		</form>
		<button loxiaType="button" class="confirm" id="turnLogistics"><s:text name="button.sta.modify.trans"></s:text> </button>
	</div>
</body>
</html>