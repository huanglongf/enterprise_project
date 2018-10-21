<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="title.warehouse.other.query"/></title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/vmi-owner-transfer-execute.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<!-- 这里是页面内容区 -->
	<div id="searchCondition">
		<form id="form_query">
			<table>
				<tr>
					<td class="label" width="10%"><s:text name="label.warehouse.pl.sta"/></td>
					<td width="20%"><input loxiaType="input" trim="true" name="staCommand.code" /></td>
					<td class="label" width="10%"><s:text name="label.warehouse.inpurchase.shop"/></td>
					<td width="20%">
						<div style="float: left">
							<select id="companyshop" name="staCommand.owner" loxiaType="select">
								<option value=""><s:text name="label.wahrhouse.sta.select"></s:text> </option>
							</select>
						</div>
						<div style="float: left">
							<button type="button" loxiaType="button" class="confirm" id="btnSearchShop" >查询店铺</button>
						</div>
					</td>					
					<td class="label"><s:text name="label.warehouse.pl.status"/></td>
					<td>
						<select loxiaType="select" name="status" id="status">
							<option selected="selected" value=0><s:text name="label.wahrhouse.sta.select"/></option>
							<option value=1><s:text name="label.wahrhouse.handoverlist.status.created"/></option>
							<option value=2><s:text name="label.warehouse.sta.status.occupied"/></option>
							<option value=10><s:text name="label.warehouse.sta.status.finished"/></option>
							<option value=17><s:text name="label.warehouse.sta.status.canceled"/></option>
						</select>
					</td>					
				</tr>
				<tr>
					<td class="label"><s:text name="label.wahrhouse.sta.creater"/></td>
					<td><input loxiaType="input" trim="true" name="staCommand.creater" /></td>
					<td class="label"><s:text name="label.warehouse.other.operator"/></td>
					<td><input loxiaType="input" trim="true" name="staCommand.operator" /></td>
					<td></td><td></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
					<td>
						<input loxiaType="date" trim="true" name="createDate" showTime="true"/></td>
					<td class="label" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
					<td><input loxiaType="date" trim="true" name="endCreateDate" showTime="true"/></td>
					<%--<td class="label"><s:text name="label.warehouse.transaction.name"></s:text> </td>
					<td><input loxiaType="input" trim="true" name="staCommand.transactionTypeName"/></td> --%>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td class="label"><s:text name="label.warehouse.sta.finish"/></td>
					<td>
						<input loxiaType="date" trim="true" name="startDate" showTime="true"/></td>
						<td class="label" style="text-align:center"><s:text name="label.warehouse.pl.to"/></td>
						<td><input loxiaType="date" trim="true" name="offStartDate" showTime="true"/>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button loxiaType="button" class="confirm" id="search"><s:text name="button.search"/></button>
			<button loxiaType="button" id="reset"><s:text name="button.reset"/></button>
		</div>
		<br />
		<div>
			<table id="tbl-query-info"></table>
			<div id="page_tbl-query-info""></div>
		</div>
	</div>
	<br />
	<div id="details" class="hidden">
		<table>
			<tr>
				<td class="label" width="10%"><s:text name="label.warehouse.pl.sta"/></td>
				<td width="20%" id="code">&nbsp;</td>
				<td class="label" width="10%"><s:text name="label.warehouse.inpurchase.shop"/></td>
				<td width="20%" id="store">&nbsp;</td>
				<%--<td class="label" width="10%"><s:text name="label.warehouse.other.transaction"/></td>
				<td width="20%" id="Dtransaction">&nbsp;</td> --%>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.wahrhouse.sta.creater"/></td>
				<td id="creater">&nbsp;</td>
				<td class="label"><s:text name="label.warehouse.other.operator"/></td>
				<td id="operator">&nbsp;</td>
				<td class="label"><s:text name="label.warehouse.pl.status"/></td>
				<td id="Dstatus">&nbsp;</td>
			</tr>
			<tr>
				<td class="label"><s:text name="label.warehouse.pl.createtime"/></td>
				<td id="createTime">&nbsp;</td>
				<td class="label"><s:text name="label.warehouse.sta.finish"/></td>
				<td id="finishTime">&nbsp;</td>
				<td class="label">&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<br />
		<table id="tbl-details"></table>

		<p>                
			<input type="hidden" id="staID" />
			<!-- <span class="label"><s:text name="label.warehouse.location.comment"/></span><br />
			<div style="display:block; background-color: #FFFFFF; width: 560px; height: 80px;" id="memo"></div> -->
		</p>
		<div class="buttonlist">
			<button type="button" loxiaType="button" class="confirm" id="execute"><s:text name="button.execute"/></button>
			<button type="button" loxiaType="button" id="cancel"><s:text name="button.cancel"/></button>
			<button type="button" loxiaType="button" id="back"><s:text name="button.back"/></button>
		</div>
	</div>
	<!-- <iframe id="snsupload" name="snsupload" class="hidden"></iframe> -->
	<jsp:include page="/common/include/include-shop-query.jsp"></jsp:include>
</body>
</html>