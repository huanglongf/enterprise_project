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
			<div style="margin-left: 20px;margin-bottom: 20px;">
				<table>
			  		<tr>
			  			<td width="10%">登录名:</td>
			  			<td width="20%"><input id="username" name="username" type="text" value="${findData.username}" /></td>
			  			<td width="10%">昵称:</td>
			  			<td width="20%"><input id="name" name="name" type="text"  value="${findData.name}"/></td>
			  		</tr>
			  		<tr>
			  		    <td width="10%">邮箱地址:</td>
			  			<td width="20%"><input id="email" name="email" type="text" value="${findData.email}" /></td>
			  			<td width="10%">手机号:</td>
			  			<td width="20%"><input id="iphone" name="iphone" type="text" value="${findData.iphone}" /></td>
			  			
			  		</tr>
				</table>
			</div>
		</div>
		<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find('${menuid}','${ret}');">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			<c:forEach items="${session_power}" var="pp">
				<c:if test="${pp.mpid=='35'}">
					&nbsp;
					<button class="btn btn-xs btn-yellow" onclick="openDiv('${root}control/employeeController/toForm.do?employeeid=${employeeid}');">
						<i class="icon-hdd"></i>新增
					</button>
				</c:if>
				<c:if test="${pp.mpid=='31'}">
					&nbsp;
					<button class="btn btn-xs btn-primary" onclick="up();">
						<i class="icon-edit"></i>修改
					</button>
				</c:if>
			</c:forEach>
			&nbsp;
<%-- 			<button class="btn btn-xs btn-inverse" onclick="del('${employeeid}','${ret}');"> --%>
<!-- 				<i class="icon-trash"></i>删除 -->
<!-- 			</button>&nbsp; -->
		</div>
		<div>
			<table class="table table-striped">
		   		<thead>
			  		<tr>
			  			<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> 
			  			<td>登录名</td>
			  			<td>昵称</td>
			  			<td>邮箱地址</td>
			  			<td>手机号</td>
			  			<td>创建时间</td>
			  			<td>状态</td>
			  		</tr>
		  		</thead>
		  		<tbody>

		  		<c:forEach items="${employee}" var="employee">
			  		<tr>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${employee.id}"></td>
			  			<td>${employee.username}</td>
			  			<td>${employee.name}</td>
			  			<td>${employee.email}</td>
			  			<td>${employee.iphone}</td>
			  			<td>${employee.createtime}</td>
			  			<td>
			  				<c:if test="${employee.status==0}">已停用</c:if>
			  				<c:if test="${employee.status==1}">已启用</c:if>
			  				|
			  				<c:if test="${employee.status==1}"><button class="btn btn-xs btn-info" onclick="upStatus('${employee.id}','${id}','0',${ret});">停用</button></c:if>
			  				<c:if test="${employee.status==0}"><button class="btn btn-xs btn-pink" onclick="upStatus('${employee.id}','${id}','1',${ret});">启用</button></c:if>
			  			</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
	</body>
	<script type="text/javascript">
	  	/**
	   	* checkbox(全选&反选)
	  	* 
	   	* items 复选框的name
	   	*/
	  	function inverseCkb(items){
			$('[name='+items+']:checkbox').each(function(){
				this.checked=!this.checked;
			});
		}
	  	
	  	/**
	   	* 跳转修改页面Form
	   	*/
	  	function up(){

			if($("input[name='ckb']:checked").length>=1){
				if($("input[name='ckb']:checked").length>1){
					alert("只能选择一行!");
			  	}else{
					openDiv('${root}control/employeeController/toForm.do?id='+$("input[name='ckb']:checked").val());
			  	}
			}else{
				alert("请选择一行!");
			}
	  	}
	  	
	  	/**
	   	* 批量删除
	   	*/
	  	function del(toid,ret){
			var str='';
	  		if($("input[name='ckb']:checked").length>=1){
				var priv_ids =[];
				// 遍历每一个name为priv_id的复选框，其中选中的执行函数
			  	$("input[name='ckb']:checked").each(function(){
			  		// 将选中的值添加到数组priv_ids中
					priv_ids.push($.trim($(this).val()));	
			  		str=str+'登录名 ：'+$(this).parent().next().html()+' ';
			  	});
			  	 var result=  	confirm('是否删除！'+str); 
				if(result){
			  	$.ajax({
					type: "POST",
		           	url: root+"/control/employeeController/del.do?",
		           	dataType: "text",
		           	data:  "privIds="+priv_ids,
		           	success: function (data) {
						if(data=='true'){
							alert("删除成功!");
			      			openDiv('${root}control/employeeController/employeeList.do?toid='+toid+'&ret='+ret);
		        	   	}else if(data=='false'){
		        			alert("节点下有菜单未删除!");
		        	   	}else{
		        			alert("删除异常!");
		        	   	}
		           	}  
		    	});}
			}else{
				alert("请选择一行!");
			}
	  	}
	  
	  	/**
	   	* 修改菜单状态
	   	*/
	  	function upStatus(id,menuid,status,ret){
			$.ajax({
	   			type: "POST",  
	           	url: root+"/control/employeeController/upStatus.do?",
	           	//我们用text格式接收
	           	dataType: "text",
	           	data:  "id="+id+"&status="+status,
	           	success: function (jsonStr) {
  					openDiv('${root}control/employeeController/employeeList.do?toid='+menuid+'&ret='+ret);
	         	}  
			}); 
	  	}
	  	
	  	/**
	   	* 查询
	   	*/
	  	function find(menuid,ret){
	  		var username = $("#username").val();
	  		var name = $("#name").val();
	  		var email = $("#email").val();
	  		var iphone = $("#iphone").val();
	  		openDiv('${root}control/employeeController/employeeList.do?username='+username+'&name='+name+'&email='+email+'&iphone='+iphone);
	  	}
	</script>
</html>
