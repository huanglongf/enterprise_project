<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="/common/meta.jsp"%>
<title><s:text name="betweenlibary.move.query"/></title>
<script type="text/javascript" src="<s:url value='/scripts/frame/vmi-return-binding.js' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
 <div id="divPd">
		<form name="form_query" id="form_query">
			<table width="70%">
				<tr>
					<td class="label" width="15%">
						创建时间
					</td>
					<td width="15%">
						<input loxiaType="date" name="startTime" showTime="true" style="width:150px;"/>
					</td>
					<td class="label" width="15%">
						到
					</td>
					<td width="15%">
						<input loxiaType="date" name="endTime" showTime="true" style="width:150px;"/>
					</td>
					<td class="label" width="15%">
						状态
					</td>
					<td width="15%">
						<select  name="status" loxiaType="select">
							<option value="">请选择</option>
							<option value="1">已创建</option>
							<option value="2">配货中</option>
							<option value="4">已转出</option>
							<option value="8">装箱中</option>
							<option value="10">已完成</option>
							<option value="17">取消已处理</option>
							<option value="25">冻结</option>
						</select>
					</td>			
				</tr>
				<tr>
					<td class="label">
						作业单号
					</td>
					<td>
						<input name="staCommand.code" loxiaType="input" trim="true" style="width:150px;"/>
					</td>
					<td class="label">
						店铺
					</td>
					<td>
						<select id="owner" name="staCommand.owner" loxiaType="select" style="width: 200px;">
							<option value="">--请选择店铺--</option>
						</select>
					</td>
					 
					<td class="label">
						相关单据号
					</td>
					<td>
						<input name="staCommand.refSlipCode" loxiaType="input" trim="true" style="width:150px;"/>
					</td>
				</tr>
			</table>
		</form>
		<div class="buttonlist">
			<button id="query" loxiaType="button" class="confirm">查询</button>
			<button id="reset" loxiaType="button" class="confirm">重置</button>
		</div>
		<table id="tbl-query-info"></table>
		<div id="pager"></div>
	</div>
	<div id="divList" class="hidden">
		<table width="60%">
			<tr>
				<td class="label" width="20%%">作业单号：</td>
				<td width="25%" id="staCode"></td>
				<td class="label">创建时间</td>
				<td id="createTime"></td>
			</tr>
			<tr>
				<td class="label">品牌退仓指令：</td>
				<td><input name="staCommand.code" loxiaType="input" trim="true"	style="width: 150px;" id="brandOrder"/></td>
				<td class="label">店铺：</td>
				<td id="shop"></td>
			</tr>
		</table>
			<button id="binding" loxiaType="button" class="confirm">绑定</button>
			<button id="removeBinding" loxiaType="button" class="confirm">解除绑定</button>
			<button id="back" loxiaType="button">返回</button>
	</div>
	<iframe id="upload" name="upload" class="hidden"></iframe>
</body>
</html>