<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<%@include file="../../common/meta.jsp"%>
<title>批次周转箱绑定</title>
<script type="text/javascript" src="<s:url value='/scripts/frame/warehouse/create-turnoverbox.js' includeParams='none' encode='false'/>"></script>
<style>
.showborder {
	border: thin;
}
</style>

</head>
<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
	<div id="div1">
	   <form id="queryForm">
	   	   <span class="label" style="font-size:15px">查询条件</span><br/><br/>
		   <table>
		   		<tr>
		   			<td class="label">周转箱编码：</td>
		   			<td>
		   				<input loxiaType="input" name="code" trim="true" id="code"/>
		   				<input loxiaType="input" style="display:none;"/>
		   			</td>
		   			<td class="label">配货清单编码：</td>
		   			<td>
		   				<input loxiaType="input" name="plCode" trim="true" id="plCode"/>
		   			</td>
		   			<td class="label">仓库：</td>
		   			<td>
		   				<select id="ouId" loxiaType="select" name="ouId">
		   					<option value="">请选择</option>
		   				</select>
		   			</td>
		   			<td class="label">状态：</td>
		   			<td>
		   				<select id="status" loxiaType="select" name="status">
							<option value="">请选择</option>
							<option value="1">未占用</option>
							<option value="5">已占用</option>
						</select>
		   			</td>
		   		</tr>
		   </table>
	   </form>
	   <div class="buttonlist">
		   <button type="button" id="query" loxiaType="button" class="confirm">查询</button>
		   <button type="button" id="reset" loxiaType="button">重置</button>
		   <button type="button" id="new" loxiaType="button" class="confirm">新建周转箱</button>
	   </div>
	   <table id="tbl_turnoverboxlist"></table>
	   <div id="pager"></div>
   </div>
   <div id="div2" class="hidden">
   		<form id="createForm">
	   	   <span class="label" style="font-size:15px">周转箱创建</span><br/><br/>
		   <table>
		   		<tr>
		   			<td class="label">周转箱编码：</td>
		   			<td>
		   				<input loxiaType="input" name="code1" trim="true" id="tn1"/>
		   				<input loxiaType="input" style="display:none;"/>
		   			</td>
		   		</tr>
		   </table>
	   </form>
	   <div class="buttonlist">
		   <button type="button" id="save" loxiaType="button" class="confirm">保存</button>
		   <button type="button" id="back" loxiaType="button">返回</button>
	   </div>
   </div>
</body>
</html>