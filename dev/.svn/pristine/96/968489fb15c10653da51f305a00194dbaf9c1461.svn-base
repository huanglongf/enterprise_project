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
			&nbsp; 
 			<button class="btn btn-xs btn-primary" onclick="up();">
 				<i class="icon-edit"></i>修改 
 			</button> 
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
			  			<td>权限名称</td>
			  			<td>URL</td>
			  			<td>备注</td>
			  		</tr>  	
		  		</thead>
		  		
		  		<tbody  align="center">
			  		<c:forEach items="${power}" var="power">
				  		<tr>
				  			<td><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
				  			<td>${power.menu_name}</td>
				  			<td>${power.url}</td>
				  			<td>${power.remarks}</td>
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
					openDiv('${root}control/powerController/toForm.do?id='+$("input[name='ckb']:checked").val());
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
					str=str+'权限名称：'+$(this).parent().next().html()+' ';
			  	});
			  	 var result=  	confirm('是否删除！'+str); 
			 	if(result){
			  	$.ajax({
					type: "POST",
		           	url: root+"/control/powerController/del.do?",
		           	dataType: "text",
		           	data:  "privIds="+priv_ids,
		           	success: function (data) {
						if(data=='true'){
							alert('删除成功！');
			      			openDiv('${root}control/powerController/powerList.do?toid='+toid+'&ret='+ret);
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
  					openDiv('${root}control/powerController/powerList.do');
	         	}  
			}); 
	  	}
	  	
	  	/**
	   	* 查询
	   	*/
	  	function find(){
	  		var name = $("#name").val();
	  		openDiv('${root}control/powerController/powerList.do?name='+name);
	  	}
	  	
	</script>   
</html>
