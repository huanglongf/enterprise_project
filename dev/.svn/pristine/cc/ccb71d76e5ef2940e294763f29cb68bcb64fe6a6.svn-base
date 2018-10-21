<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>快递状态代码查询</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type='text/javascript'>
	function pageJump() {
     //alert('${root}control/radar/routecodeController/query.do?startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val()+'status=${queryParam.status}&status_code=${queryParam.status_code}&transport_code=${queryParam.transport_code}&flag=${queryParam.flag}');
      openDiv('${root}control/radar/routecodeController/query.do?startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val()+'&status=${queryParam.status}&status_code=${queryParam.status_code}&transport_code=${queryParam.transport_code}&flag=${queryParam.flag}');
	}
	
	/**
	 * 查询
	 */

	
	function toUpdate() {
		if($("input[name='route_id']:checked").length>=1){
			if($("input[name='route_id']:checked").length>1){
				alert("只能选择一行!");
		  	}else{
		  		openDiv('${root}/control/shopController/toForm.do?id='+$("input[name='route_id']:checked").val());
		  	}
		}else{
			alert("请选择一行!");
		}
	}

	function del(){
		if($("input[name='route_id']:checked").length>=1){
			if($("input[name='route_id']:checked").length>1){
				alert("只能选择一行!");
		  	}else{
		  		var result= confirm('确定要删除以下记录？'); 
		  		if(result){
		  		$.post('${root}/control/radar/routecodeController/delete.do?id='+$("input[name='route_id']:checked").val(),
		        		function(data){
		  			if(data.toString()=='[object XMLDocument]'){
						  window.location='/BT-LMIS';
							return;
					  };
		        	            if(data.code=1){
		        	            	alert("操作成功！");
		        	            }else{
		        	            	alert("操作失败！");
		        	             }
		        	            openDiv('${root}/control/radar/routecodeController/query.do');
  
		                        }
		  		      );	
		  	}
		  		
		  	}
		}else{
			alert("请选择一行!");
		}
	}
	//var params='&transport_code'+${transport_code}+'&status_code='+${status_code}+'&status='+${status}+'&flag='+${flag};
	//$('#params').val(params);
	
	$(document).ready(function() {
		var transport_code='${queryParam.transport_code}';
		var flag='${queryParam.flag}';
		if(flag!=''&&flag!=-1){
			$('#flag').val(flag);
		}
		if(transport_code!=''&&transport_code!=-1){
			$('#route_code').val(transport_code);
		}
	});
    </script>
	</head>
	<body>
		<div class="page-header"><h1 style='margin-left:20px'>快递状态代码查询</h1></div>	
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
			<table>
		  		<tr>
		  		<td align="left" width="10%"><label class="blue" >物流服务商&nbsp;:</label></td>
					<td width="30%"><select id="route_code" name="route_code" data-edit-select="1"  class='select'  style='width:160px;height:30px'>
						<option value=''>---请选择---</option>
						<c:forEach items="${trans_names}" var = "trans_name" >
							<option  value="${trans_name.transport_code}">${trans_name.transport_name}</option>
						</c:forEach>	
					</select></td>	
					<td align="left" width="10%"><label class="blue" >状态代码&nbsp;:</label></td>
					<td width="20%">
					<input id='status_code' type='text' value='${queryParam.status_code}'  style='height: 30px;width:160px'/>
					</td>
		  		</tr>
		  		<tr>
		  			<td align="left" width="10%"><label class="blue" >状态描述&nbsp;:</label></td>
					<td width="20%">
					<input id='status' type='text' value='${queryParam.status}'  style='height: 30px;width:160px'/></td>
					<!-- <td align="left" width="10%"><label class="blue" >标识&nbsp;:</label></td>
					<td width="20%"><select id="flag" name="flag"  class='select'>
						<option value=''>---请选择---</option>
						<option value= 0>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;揽件</option>
						<option value= 2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中转中</option>
						<option value= 1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;结束</option>
					</select></td>-->
				</tr>
			</table>
		</div>
		<div style="margin-top: 10px;margin-left: 20px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;&nbsp;
			<button class="btn btn-xs btn-yellow" onclick="openDiv('${root}/control/radar/routecodeController/addEdit.do');">
				<i class="icon-hdd"></i>新增
			</button>
<!-- 			&nbsp; -->
<!-- 			<button class="btn btn-xs btn-primary" onclick="up();"> -->
<!-- 				<i class="icon-edit"></i>修改 -->
<!-- 			</button> -->
			&nbsp;
				<!-- <button class="btn btn-xs btn-primary" onclick="toUpdate();">
				<i class="icon-edit"></i>修改
			</button>&nbsp; -->
			<button class="btn btn-xs btn-inverse" onclick="del()">
				<i class="icon-trash"></i>删除
			</button>&nbsp;
		</div>
		<div class= "title_div" id= "sc_title">
			<table class= "table table-striped" style= "table-layout: fixed;overflow-x:show;margin-bottom:0px;" >
		   		<thead>
			  			<tr class='table_head_line'>
			  			<td class='td_cs'></td>
			  			<td class='td_cs'>序号</td>
			  			<td class='td_cs'>物流服务商</td>
			  			<td class='td_cs'>状态代码</td>
			  			<td class='td_cs'>状态描述</td>
			  		</tr>  
				</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  		<tbody align= "center">
			  		<c:forEach items="${pageView.records}" var="route"  varStatus="status">
			  			<tr>
			  			<td class= "td_cs" nowrap="nowrap"><input id="ckb" name="route_id" type="checkbox" value="${route.id}"></td>
			  			<td class= "td_cs" nowrap="nowrap">${status.count}</td>
			  			<td class= "td_cs" nowrap="nowrap"><a href='javascript:update("${route.id}","${route.transport_code}","${route.status}","${route.status_code}")'>${route.transport_name }</a></td>
			  			<td class= "td_cs" nowrap="nowrap"><a href='javascript:update("${route.id}")'>${route.status_code}</a></td>
			  			<td class= "td_cs" nowrap="nowrap"><a href='javascript:update("${route.id}")'>${route.status }</a></td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
      	<div style="margin-right: 300px;margin-top:20px;">${pageView.pageView}</div>		
	</body>
</html>
<script>
  function find(){
		var route_codef =  $("#route_code").find("option:selected").val();
		var status_codef = $("#status_code").val();;
		var statusf = $('#status').val();
		openDiv('${root}/control/radar/routecodeController/query.do?transport_code=' + route_codef + '&status_code='+status_codef + '&status='+statusf);

  }
  function update(id,transport_code,status,status_code){
	  openDiv('${root}/control/radar/routecodeController/toupdate.do?id='+id);
  }
</script>
<style>

.select {
    padding: 3px 4px;
    height: 30px;
    width: 160px;
   text-align: enter;
}

.table thead>tr>th, .table tbody>tr>th, .table tfoot>tr>th, .table thead>tr>td, .table tbody>tr>td, .table tfoot>tr>td {
    padding: 8px;
    line-height: 1.428571429;
    vertical-align: top;
    text-align: center;
    border-top: 1px solid #ddd;
}
.m-input-select {
  display: inline-block;
  position: relative;
  -webkit-user-select: none;
  width: 160px;
}
</style>
