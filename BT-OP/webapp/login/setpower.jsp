<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/base/yuriy.jsp" %>
		<title>OP</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		
		
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type='text/javascript'>
		
	
	</script>
	</head>
	<body>
		<div class="page-header">
			<h1 style='margin-left: 20px'>角色功能权限设置</h1>
		</div>
		<!-- 新增页面 -->
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
			<form id="addForm">
				<table> 
			   		<tr>
		  				<td width="160px" align="left"><label style="width: 95px;">角色名称</label></td>
		  				<td width="170px" align="left">
		  					<input type='text' id='role_name' name='role_name'     style="width: 160px;">
		  				</td>
		  			</tr>
			   		<tr>
		  				<td width="160px" align="left"><label style="width: 95px;">数据权限</label></td>
		  				<td width="170px" align="left">
		  					<input type='text' id='customer_number' name='customer_number' style="width: 160px;">
		  				</td>
		  			</tr>
			   		<tr>
		  				<td width="160px" align="left"><label style="width: 95px;">菜单</label></td>
		  				<td width="170px" align="left">
		  					<input type='text' id='status' name='status'     style="width: 160px;">
		  				</td>
		  			</tr>
			</table>
			</form>
			 </div>
			
			<script type="text/javascript">
			jQuery(function($) {
				$('.accordion').on('hide', function (e) {
					$(e.target).prev().children(0).addClass('collapsed');
				})
				$('.accordion').on('show', function (e) {
					$(e.target).prev().children(0).removeClass('collapsed');
				})
			});
		</script>		
	</body>
</html>
<style>

.divclass{
border:5px solid #E0EEEE} 

.select {
    padding: 3px 4px;
    height: 30px;
    width: 160px;
   text-align: enter;
}

.table_head_line td {
font-weight: bold;
font-size: 16px
}

.m-input-select {
    display: inline-block;
    position: relative;
    -webkit-user-select: none;
    width: 160px;
    }
   .accordion-style2.panel-group .panel-heading .accordion-toggle {
   background-color: #edf3f7;
   border: 2px solid #6eaed1;
   border-width: 0 0 0 2px;
}
    
</style>
