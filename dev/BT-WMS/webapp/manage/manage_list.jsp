<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/yuriy.jsp"%>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
	<h1>容器查询</h1><br><br><hr>
	容器号:<input type="text" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	容器状态:
	<select>
		<option value="1">已释放</option>
		<option value="2">未释放</option>
	</select>
	<br><hr>
	<button>查询</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<button>重置</button>&nbsp;&nbsp;&nbsp;&nbsp;
	<button>导出</button>
	<br><br><hr>
	<table border="1" style="width: 100%">
		<tr>
			<td>容器号</td>
			<td>容器状态</td>
			<td>库存件数</td>
		</tr>
	</table>
</body>
</html>
