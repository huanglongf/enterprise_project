<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	</head>
	
	<body>
	   <div class="page-header">
		</div>
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
			<table>
		  		<tr>
		  			<td width="10%">权限名称:</td>
		  			<td width="20%"><input id="name" name="name" type="text" value="${findData.name}" /></td>
		  			<td width="10%">菜单名称:</td>
		  			<td width="20%"><input id="menuname" name="menu_name" type="text"  value="${findData.menu_name}"/></td>
		  		</tr>
			</table>
		</div>
		<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;&nbsp;
			<button class="btn btn-xs btn-yellow" onclick="openDiv('${root}control/powerController/toForm.do');">
				<i class="icon-hdd"></i>新增
			</button>
<!-- 			&nbsp; -->
<!-- 			<button class="btn btn-xs btn-primary" onclick="up();"> -->
<!-- 				<i class="icon-edit"></i>修改 -->
<!-- 			</button> -->
			&nbsp;
			<button class="btn btn-xs btn-inverse" onclick="del('${employeeid}','${ret}');">
				<i class="icon-trash"></i>删除
			</button
			>&nbsp;
		</div>
		<div style="height:400px;width:100%;overflow:auto;border:solid #F2F2F2 1px;margin-top: 22px;">
			<table class="table table-striped">
		   		<thead  align="center">
			  		<tr >
			  			<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td>名称</td>
			  			<td>菜单名称</td>
			  			<td>创建时间</td>
			  			<td>创建人</td>
			  		</tr>  	
			  		<tr>
			  		     <td></td>
			  		     <% for(int i=0;i<4;i++){ %>
<%-- 			  	<c:forEach items="${power}" var="power"> --%>
                        <td>
						  <span class="input-icon input-icon-right">
						    <select style="text-align:center;">
						    <option value="0">≈</option>
						    <option value="1">=</option>
						    <option value="2">></option>
						    <option value="3">>=</option>
						    <option value="4"><</option>
						    <option value="5"><=</option>
						    </select>
							<input type="text" id="form-field-icon-2" style="width:80px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						 </span>
						</td>
						 <%} %>
<%-- 		  		</c:forEach> --%>
			  		</tr>  				  			
		  		</thead>
		  		
		  		<tbody  align="center">
		  		<c:forEach items="${power}" var="power">
			  		<tr>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
			  			<td>${power.name}</td>
			  			<td>${power.menu_name}</td>
			  			<td>${power.create_date}</td>
			  			<td>${power.create_person}</td>
			  		</tr>
		  		</c:forEach>
		 
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
      	<div style="margin-right: 30px;margin-top:20px;">${pageView.pageView}</div>		
	</body>
	
	<script type='text/javascript'>
	function pageJump() {
      openDiv('${root}control/otherController/pagination.do?startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&pageIndex="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	}
    </script>
</html>
