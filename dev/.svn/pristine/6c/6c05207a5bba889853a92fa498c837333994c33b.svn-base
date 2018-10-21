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
			<div style="margin-left: 20px;">
				<form action="" id="searchForm">
					<table>
				  		<tr>
				  			<td width="10%">角色名称:</td>
				  			<td width="20%"><input id="name" name="name" type="text" value="${roleSelective.name }" /></td>
				  			<td width="10%">备注:</td>
				  			<td width="20%"><input id="remark" name="remark" type="text" value="${roleSelective.remark }" /></td>
				  		</tr>
					</table>
				</form>
			</div>
		</div>
		<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;&nbsp;
			<button class="btn btn-xs btn-yellow" onclick="openDiv('${root}/control/roleController/toForm.do');">
				<i class="icon-hdd"></i>新增
			</button>
			&nbsp;
			<button class="btn btn-xs btn-primary" onclick="up();">
				<i class="icon-edit"></i>修改
			</button>
			&nbsp;
			<button class="btn btn-xs btn-inverse" onclick="del();">
				<i class="icon-trash"></i>删除
			</button
			>&nbsp;
		</div>
		<div>
			<table class="table table-striped">
		   		<thead>
			  		<tr>
			  			<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> 
			  			<td>角色名称</td>
			  			<td>备注</td>
			  			<td>状态</td>
			  		</tr>
		  		</thead>
		  		<tbody>
		  		<c:forEach items="${role}" var="role">
			  		<tr>
			  			<td><input id="ckb" name="ckb" type="checkbox" data-status="${role.status}" value="${role.id}"></td>
			  			<td>${role.name}</td>
			  			<td>${role.remark}</td>
			  			<td>
			  				<c:if test="${role.status==0}">已停用</c:if>
			  				<c:if test="${role.status==1}">已启用</c:if>
			  				|
			  				<c:if test="${role.status==1}"><button class="btn btn-xs btn-info" onclick="upStatus('${role.id}','0');">停用</button></c:if>
			  				<c:if test="${role.status==0}"><button class="btn btn-xs btn-pink" onclick="upStatus('${role.id}','1');">启用</button></c:if>
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
					openDiv('${root}control/roleController/toForm.do?id='+$("input[name='ckb']:checked").val());
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
	  		var isSubmit = true;
			if($("input[name='ckb']:checked").length>=1){
				var priv_ids =[];
				// 遍历每一个name为priv_id的复选框，其中选中的执行函数
			  	$("input[name='ckb']:checked").each(function(){
			  		//验证是否一起用，已启用禁止删除
			  		if ($(this).data("status") == "1") {
			  			priv_ids=[];
			  			str = '';
			  			alert("角色：" + $(this).parent().next().html() + "已启用，禁止删除");
			  			isSubmit=false;
			  			return;
			  		}
			  		
			  		// 将选中的值添加到数组priv_ids中
					priv_ids.push($.trim($(this).val()));
					str=str+'角色名称 ：'+$(this).parent().next().html()+' ';
			  	});
				
				if(isSubmit && confirm('是否删除！'+str)){
				  	$.ajax({
						type: "POST",
			           	url: root+"/control/roleController/del.do?",
			           	dataType: "text",
			           	data:  "privIds="+priv_ids,
			           	success: function (data) {
							if(data=='true'){
								alert('删除成功！');
				      			openDiv('${root}control/roleController/list.do');
			        	   	}else if(data=='false'){
			        			alert("该角色已绑定用户，无法删除!");
			        	   	}else{
			        			alert("删除异常!");
			        	   	}
			           	}  
			    	});
				}
			}else{
				alert("请选择一行!");
			}
	  	}
	  
	  	/**
	   	* 修改菜单状态
	   	*/
	  	function upStatus(id,status){
			$.ajax({
	   			type: "POST",  
	           	url: root+"/control/roleController/upStatus.do?",
	           	//我们用text格式接收
	           	dataType: "text",
	           	data:  "id="+id+"&status="+status,
	           	success: function (jsonStr) {
  					openDiv('${root}control/roleController/list.do');
	         	}  
			}); 
	  	}
	  	
	  	/**
	   	* 查询
	   	*/
	  	function find(){
	  		openDiv('${root}control/roleController/list.do?'+$("#searchForm").serialize());
	  	}
	</script>
</html>
