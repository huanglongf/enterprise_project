
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<%@include file="/common/meta.jsp"%>
		<title>仓库配货清单自动创建规则维护</title>
		<script type="text/javascript" src="<s:url value='/scripts/frame/add-counter-maintain.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
		<style type="text/css">
			.tips{
				width:100%; height: 50%; text-align:center; color:red; font-size: 40px; font-weight: bold;
			}
		</style>
	</head>
	<body style="background-color: #f2f2f2;" contextpath="<%=request.getContextPath() %>">
		<div id="role-list">
			<table id="tbl-role-list"></table>
			<div id="pager"></div>
		</div>
		<div id="order-List" class="hidden">
			<table >
				<tr>
					<td width="100px" class="label">常量集编码</td>
					<td width="100px" id="categoryCode"></td>
					<td width="100px" class="label">常量集名称</td>
					<td width="200px" id="categoryName"></td>
					<!-- <td width="200px" colspan="2">&nbsp;</td> -->
				</tr>
				<tr>
					<td width="100px" class="label">常量值</td>
					<td width="100px"><input loxiaType="input" trim="true" id="optionValue"/></td>
				</tr>
			</table>
			
		</div>
		<div id="btn" class="hidden">
			<div class="buttonlist">
					<button id="search" type="button" loxiaType="button" class="confirm">修改</button>
					<button id="back" type="reset" loxiaType="button">返回</button>
		    </div>
	     </div>
		
</body>
</html>