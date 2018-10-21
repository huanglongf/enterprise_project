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
				<table>
			  		<tr>
			  			<td width="10%">菜单名称:</td>
			  			<td width="20%"><input id="name" name="name" type="text" value="${findMenu.name}" /></td>
			  			<td width="10%">URL:</td>
			  			<td width="20%"><input id="url" name="url" type="text"  value="${findMenu.url}"/></td>
			  		</tr>
			  		<tr>
			  			<td width="10%">ICON:</td>
			  			<td width="20%"><input id="icon" name="icon" type="text" value="${findMenu.icon}" /></td>
			  			<td width="10%">备注:</td>
			  			<td width="20%"><input id="remarks" name="remarks" type="text" value="${findMenu.remarks}" /></td>
			  		</tr>
				</table>
			</div>
		</div>
		<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find('${menuid}','${ret}');">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;&nbsp;
			<button class="btn btn-xs btn-yellow" onclick="openDiv('${root}control/menuController/toForm.do?menuid=${menuid}');">
				<i class="icon-hdd"></i>新增
			</button>
			&nbsp;
			<button class="btn btn-xs btn-primary" onclick="up();">
				<i class="icon-edit"></i>修改
			</button>
			&nbsp;
			<button class="btn btn-xs btn-inverse" onclick="del('${menuid}','${ret}');">
				<i class="icon-trash"></i>删除
			</button
			>&nbsp;
		</div>
		<div>
			<table class="table table-striped">
		   		<thead>
			  		<tr>
			  			<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> 
			  			<td>菜单名称</td>
			  			<td>URL</td>
			  			<td>ICON</td>
			  			<td>排序</td>
			  			<td>备注</td>
			  			<td>状态</td>
			  		</tr>
		  		</thead>
		  		<tbody>
		  		<c:if test="${menuid!=0}">
		  			<tr>
			  			<td colspan="7">
			  				<a href="javascript:void(0);" onclick="openDiv('${root}control/menuController/toRet.do?toid='+${menuid}+'&ret='+${ret});">返回上级...</a>
		  				</td>
			  		</tr>
		  		</c:if>
		  		
		  		<c:forEach items="${menu}" var="menu">
			  		<tr>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${menu.id}"></td>
			  			<td>
			  				<c:if test="${menu.node==0}">
								<a href="javascript:void(0);" onclick="openDiv('${root}control/menuController/toList.do?toid='+${menu.id}+'&ret='+${menu.pid});">${menu.name}</a>
			  				</c:if>
			  				<c:if test="${menu.node==1}">
								${menu.name}
			  				</c:if>
						</td>
			  			<td>${menu.url}</td>
			  			<td>${menu.icon}</td>
			  			<td>${menu.sort}</td>
			  			<td>${menu.remarks}</td>
			  			<td>
			  				<c:if test="${menu.status==0}">已停用</c:if>
			  				<c:if test="${menu.status==1}">已启用</c:if>
			  				|
			  				<c:if test="${menu.status==1}"><button class="btn btn-xs btn-info" onclick="upStatus('${menu.id}','${menuid}','0',${ret});">停用</button></c:if>
			  				<c:if test="${menu.status==0}"><button class="btn btn-xs btn-pink" onclick="upStatus('${menu.id}','${menuid}','1',${ret});">启用</button></c:if>
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
					openDiv('${root}control/menuController/toForm.do?menuid=${menuid}&id='+$("input[name='ckb']:checked").val());
			  	}
			}else{
				alert("请选择一行!");
			}
	  	}
	  
	  	/**
	   	* 批量删除
	   	*/
	  	function del(toid,ret){
	  		var  str='';
			if($("input[name='ckb']:checked").length>=1){
				var priv_ids =[];
				// 遍历每一个name为priv_id的复选框，其中选中的执行函数
			  	$("input[name='ckb']:checked").each(function(){
			  		// 将选中的值添加到数组priv_ids中
					priv_ids.push($.trim($(this).val()));	  
					str=str+'菜单名称 ：'+$(this).parent().next().children(0).html()+' ';
			  	});
			  	 var result=  	confirm('是否删除！'+str); 
			  	 if(result){
			  	$.ajax({
					type: "POST",
		           	url: root+"/control/menuController/del.do?",
		           	dataType: "text",
		           	data:  "privIds="+priv_ids,
		           	success: function (data) {
						if(data=='true'){
							alert('删除成功！')
			      			openDiv('${root}control/menuController/toList.do?toid='+toid+'&ret='+ret);
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
	           	url: root+"/control/menuController/upStatus.do?",
	           	//我们用text格式接收
	           	dataType: "text",
	           	data:  "id="+id+"&status="+status,
	           	success: function (jsonStr) {
  					openDiv('${root}control/menuController/toList.do?toid='+menuid+'&ret='+ret);
	         	}  
			}); 
	  	}
	  	
	  	/**
	   	* 查询
	   	*/
	  	function find(menuid,ret){
	  		var name = $("#name").val();
	  		var url = $("#url").val();
	  		var icon = $("#icon").val();
	  		var remarks = $("#remarks").val();
	  		openDiv('${root}control/menuController/toList.do?toid='+menuid+'&ret='+ret+'&name='+name+'&url='+url+'&icon='+icon+'&remarks='+remarks);
	  	}
	</script>
</html>
