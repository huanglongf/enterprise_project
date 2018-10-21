<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ taglib uri="/struts-tags" prefix="s" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>集货管理</title>
<%@include file="/common/meta.jsp"%>

<script type="text/javascript" src="<s:url value='/scripts/frame/automaticEquipment/shipping-point.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id = "div1">
		<form id="form_query">
			<table>
				<tr>
					<td class="label">集货口编码：</td>
					<td width="160px"><input loxiaType="input" name="code"   id="roleCode" trim="true"/></td>
					<td class="label">集货口名称：</td>
					<td width="160px"><input loxiaType="input" name="name" id="roleName" trim="true"/></td>
					<td class="label">WCS编码：</td>
					<td width="160px"><input loxiaType="input" name="wcsCode"  id="roleWsc" trim="true"/></td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<table>
				<tr>
					<button type="button" loxiaType="button" class="confirm" id="search">
						查询
					</button>
					<button type="button" loxiaType="button" id="reset">
						重置
					</button>
				</tr>
			</table>
		</div>
		<table id="tbl-shipping-point"></table>
		<div id="pager"></div> 
		<div class="buttonlist">
			<table>
				<button type="button" loxiaType="button"  class="confirm" id="add">
					新建
				</button>
				&nbsp;
				<button type="button" loxiaType="button"  class="confirm" id="batchRemove">
					删除
				</button>
			</table>
		</div>
	</div>
	<div id="div2" style="width: 850px" >
		<form id="form_info" >
			<table >
				<tr>
					<td class="label" width="80px">集货口编码：</td>
					<td width="180px">
					<input loxiaType="input" trim="true" id="roleCode1" name="code" maxlength="20" mandatory="true" onblur="getPointCode()"/>
					<label id = "roleCode2" ></label>
					</td>
					<td class="label" width="80px">集货口名称：</td>
					<td width="180px">
					<input loxiaType="input" trim="true" id="roleName1" maxlength="20" name="name" mandatory="true"/>
					</td>
					<td class="label" width="80px">WCS编码：</td>
					<td width="180px">
					<input loxiaType="input" trim="true" id="roleWsc1" maxlength="20" name="wcsCode"/>
					</td>
				</tr>
				<tr>
				    <td class="label" width="80px" >是否添加负载均衡集货口：</td>
				    <td ><input  type="checkbox" id="pointType" name="pointType" /></td>
				    <td id='labelMaxAssumeNumber' class="label" width="80px">单次循环最大负载量(单)：</td>
					<td id='inputNumber' width="180px" >
					<input loxiaType="input" trim="true" id="maxAssumeNumber" minlength="1"  name='maxAssumeNumber' value="1"/>
					</td>
				</tr>
			</table>
		</form>
		<table id="tbl-shipping-role"></table>
		<div id="pager1"></div> 
		<div class="buttonlist">
			<table>
				<button type="button" loxiaType="button"  class="confirm" id="saveRole">
					保存
				</button>
				<button type="button" loxiaType="button"  id="back">
					返回
				</button>
			</table>
		</div>
		<!-- 相关的负载均衡集货口 -->
		<div id="div3">
			<div class="buttonlist"></div>
			<form name="warehouseShippingpointsForm" id="warehouseShippingpointsForm" >
				<div loxiaType="edittable" class="warehouse-shipping">
					<table id="warehouseShippingpointsTable" width="100%" operation="add,delete" append="1">
					<thead>
						<tr>
							<th><input type="checkbox"/></th>
							<th>集货口编码</th>
							<th>集货口名称</th>
							<th>WCS编码</th>
							<th>单次循环最大负载量(单)</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
					<tbody>
						<tr class="add" index="(#)">
							<td width="15px"><input type="checkbox"/></td>
							<td><input loxiatype="input" mandatory="true" name="addList(#).code" /></td>
							<td><input loxiatype="input" mandatory="true" name="addList(#).name" /></td>
							<td><input loxiatype="input" mandatory="true" name="addList(#).wcsCode" /></td>
							<td><input loxiatype="number" mandatory="true" name="addList(#).maxAssumeNumber" /></td>
                            <td><input type='hidden' loxiatype='input' name="addList(#).refShippingPoint"  id="pointCodeHidden"/></td> 
						</tr>
					</tbody>
					<tfoot></tfoot>
				</table>
				</div>
			</form>
			<div class="buttonlist">
			<table>
				<button type="button" loxiaType="button"  class="confirm" id="refForm">
					保存
				</button>
				<button type="button" loxiaType="button"  id="back" onclick='back()'>
					返回
				</button>
			</table>
		</div>
		</div>
		<!-- 负载均衡集货口结束 -->
	</div>
	<div id="dialog_addRoleLine">
		<table id="tbl-shipping-role-line"></table>
	</div>
</body>
</html>