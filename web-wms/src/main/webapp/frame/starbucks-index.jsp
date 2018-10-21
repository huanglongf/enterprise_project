<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/common/meta.jsp"%>
<script type="text/javascript" src="<s:url value='/scripts/frame/starbucks-index.js"' includeParams='none' encode='false'><s:param name="version"><s:property value="#VERSION" /></s:param></s:url>"></script>
</head>
<body>
	<div>
	<form name="searchForm" id="searchForm">
		<table>
			<tr>
				<td class="label"><s:text name="SKU编码"></s:text></td> 
				<td><input loxiaType="input" trim="true" id="code" name="code"/></td> 
				<td class="label"><s:text name="订单类型"></s:text></td>
				<td><select id="msr" loxiaType="select">
							<option value="" selected="selected">--请选择--</option>
							<option value="MSR定制订单">MSR定制订单</option>
					</select></td>
			</tr>
		</table>
	</form>
	<button  loxiaType="button" class="confirm" id="search"><s:text name="查询"></s:text> </button>
	<button  loxiaType="button" id="reset"><s:text name="重设"></s:text> </button>
	</br>
	</br>
	<table id="tbl-timedtaskList"></table>
	<div id="pager_query"></div>
	</div>
	</br>
	</br>
	<div>
	<form>
		<table>
			<tr>
				
				
				<td class="label"><s:text name="SKU编码"></s:text></td>
				<td><input loxiaType="input" trim="true"  id="code1" name="code1"/></td>
				<td class="label"><s:text name="订单类型"></s:text></td>
				<td><select id="msr" name="msr" loxiaType="select">
							<option value="" >--请选择--</option>
							<option value="MSR定制订单">MSR定制订单</option>
					</select>
				</td>
			</tr>
		</table>
	</form>
	</br>
	<button  loxiaType="button" class="confirm" id="update"><s:text name="绑定"></s:text> </button>
	</div>
</body>
</html>