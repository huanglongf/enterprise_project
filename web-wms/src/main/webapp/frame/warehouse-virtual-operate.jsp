<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/common/meta.jsp"%>
<title><s:text name="title.warehouse.virtual"/></title>
<style>
	.ui-loxia-table tr.error{background-color: #FFCC00;}
	.divFloat{
		float: left;
		margin-right: 10px;
	}
</style>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse-virtual-operate.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="tabs">
		<ul>
			<li><a href="#tabs-1">物理仓创建</a></li>
			<li><a href="#tabs-2">物理仓虚拟仓关联维护</a></li>
		</ul>
		<div id="tabs-1" >
				<form id="newform" name="newform" method="post">
				<table width="30%">
					<tr>
						<td class="label" width="30%">仓库名称：</td>
						<td><input loxiaType="input" name="wname" trim="true" id="wname" mandatory="true" value="" maxlength="50"/></td>
					</tr>
					<tr>
						<td></td>
						<td><button type="button" loxiaType="button" class="confirm" id="saveBtn">保存</button></td>
					</tr>
				</table>
				</form>
		</div>
		<div id="tabs-2">
				<div style="float:left;width:450px;padding-right:20px;border-right:1px solid #9bb3cd">
					<table width="100%">
						<tr>
							<td class="label" width="50px;">物理仓</td>
							<td>
								<select loxiaType="select" id="pwsele">
								</select>
							</td>
						</tr>
					</table>
					<div class="label" style="text-align:left;margin-top:25px;">已关联虚拟仓列表<font style="color:#f00;font-size:12px">(*双击将删除关联)</font></div>
					<select multiple="true" loxiaType="select" id="phyware" style="height:300px;display:block;width:100%;">
					</select>
					<button loxiaType="button" class="confirm" style="margin-top:10px;" id="dere">删除关联</button>
				</div>
				<div style="float:left;margin-left:20px;width:300px">
					<table width="100%">
						<tr>
							<td class="label" width="50px;">公司</td>
							<td>
								<select loxiaType="select" id="comsele">
								</select>
							</td>
						</tr>
						<tr>
							<td class="label" width="50px;">运营中心</td>
							<td>
								<select loxiaType="select" id="censele">	
								</select>
							</td>
						</tr>
					</table>
					<div class="label" style="text-align:left">仓库列表<font style="color:#f00;font-size:12px">(*双击仓库列表内容添加关联)</font></div>
					<select multiple="true" id="waresele" loxiaType="select" style="height:300px;display:block;width:300px;" disabled="disabled">
					</select>
					<button loxiaType="button"  class="confirm" style="margin-top:10px;" id="addre">添加关联</button>
				</div>
				<div style="float:left;width:100%">
					<button loxiaType="button"  class="confirm" id="saveall" style="margin-top:10px;">保存</button>
				</div>
		</div>
	</div>
</body>
</html>