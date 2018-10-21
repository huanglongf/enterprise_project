<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="baseinfo.company.modify.title"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/report-export-for-sales.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>														

<body contextpath="<%=request.getContextPath() %>">	 
	<iframe id="upload" name="upload" class="hidden"></iframe>
	<div id="download" style="clear: both;"></div>
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">汇总报表</a></li>
			<li><a href="#tabs-2">明细报表</a></li>
		</ul>
		<div id="tabs-1">
			<div>
				<form method="post" id="exportform" name="exportform" target="upload">
					<table width="70%">
						<tr>
							<td class="label" width="20%">开始时间</td>
							<td width="20%">
								<input loxiaType="date" mandatory="true" name="startTime" />
							</td>
							<td class="label" width="20%">结束时间</td>
							<td width="20%">
								<input loxiaType="date" mandatory="true" name="endTime"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="buttonlist">
				<button type="button" loxiaType="button" id="export" class="confirm">汇总报表导出</button>
			</div>
			<div class="clear"></div>
		</div>
		<div id="tabs-2">
			<div>
				<form method="post" id="exportform1" name="exportform1" target="upload">
					<table width="70%">
						<tr>
							<td width="20%" class="label">开始时间</td>
							<td width="20%">
								<input loxiaType="date" mandatory="true" name="startTime" />
							</td>
							<td width="20%" class="label">结束时间</td>
							<td width="20%">
								<input loxiaType="date" mandatory="true" name="endTime"/>
							</td>
						</tr>
						<tr>
							<td class="label">货号</td>
							<td>
								<input loxiaType="input" name="supplierSkuCode" />
							</td>
							<td class="label">促销编码</td>
							<td>
								<input loxiaType="input"" name="pomotionCode"/>
							</td>
						</tr>
						<tr>
							<td class="label">大类/Product Category</td>
							<td>
								<select loxiaType="select" name="cmd.productCate" id="productCategory">
									<option value="">请选择</option>
								</select>
							</td>
							<td class="label">
								小类/Product Line
							</td>
							<td>
								<select loxiaType="select" name="cmd.productLine" id="productLine">
									<option value="">请选择</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="label">
								性别/Consumer Group
							</td>
							<td>
								<select loxiaType="select" name="cmd.consumerGroup" id="consumerGroup">
									<option value="">请选择</option>
								</select>
							</td>
							<td></td>
							<td>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="buttonlist">
				<button type="button" loxiaType="button" id="export1" class="confirm">明细报表导出</button>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>
