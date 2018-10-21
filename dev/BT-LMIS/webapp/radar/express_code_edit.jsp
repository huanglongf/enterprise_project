<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>快递状态代码修改</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
	</head>
	<body>
		<div class="page-header"><h3 style='margin-left:20px'>快递状态代码修改</h3></div>	
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;" class="clearfix form-actions">
           <form class="registerform" id="formRoute" role="form">
			<div style="width: 100%;" align="center">
				<table style="width: 80%;" class="form_table">			
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1"> 物流服务商&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"> 
							<select id="routecode" name="transport_code"  class='selectpicker' style='width:159px;height:28px;'>
							<option value= -1>---请选择---</option>
							<c:forEach items="${trans_names}" var = "trans_name" >
							<option  value="${trans_name.transport_code}">${trans_name.transport_name}</option>
						    </c:forEach>	
							</select>
						</td>
						<td nowrap="nowrap" class="left"></td>
					</tr>
						<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1">状态代码&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="status_code" name="status_code"  name='status_code' type="text" id="form-field-1" placeholder="" value="${record.status_code}" datatype="*1-10"  errormsg="信息至少1个字符,最多10个字符！" style='width:160px;height:30px' /></td>
						<td nowrap="nowrap" class="left"></td>
					</tr>
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1">状态描述&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="status" name="status" type="text" id="form-field-1" placeholder="" value="${record.status}" datatype="*1-10"  errormsg="信息至少1个字符,最多10个字符！"  style='width:160px;height:30px'/></td>
						<td nowrap="nowrap" class="left"></td>
					</tr>
					<!-- <tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1">标识&nbsp;: </label></td>
						<td nowrap="nowrap" class="left">
							<select id="flag" name="flag"  class='selectpicker' style='width:159px;height:28px;'>
							<option value= -1>---请选择---</option>
							<option value= 0>揽件</option>
							<option value= 1>结束</option>
							<option value= 2>中转中</option>
							</select>
							<span class="red" style="font-size: 10px;"></span>
						</td>
						<td nowrap="nowrap" class="left"></td>
					</tr> -->
					<tr>
						<td nowrap="nowrap" class="right"><label class="no-padding-right blue" for="form-field-1">备注&nbsp;: </label></td>
						<td nowrap="nowrap" class="left"><input id="remark" name="remark" type="text" id="form-field-1" placeholder="" value="${record.remark}" datatype="*1-10"  errormsg="信息至少1个字符,最多10个字符！"  style='width:160px;height:30px'/></td>
						<td nowrap="nowrap" class="left"><input type='text' style='display:none'/>
						<input type='text' name ='id' style='display:none' value='${record.id}'/>
						<input type='text'  style='display:none'name='create_time' value='<fmt:formatDate value="${record.create_time}" pattern="yyyy-MM-dd HH:mm:ss "/>'></input>
						<input type='text' name ='create_user' style='display:none' value='${record.create_user}'/>
						</td>
					</tr>								
				</table>
				
							<div class="clearfix form-actions">
								
									<button class="btn btn-info" type="button" id="subins" name="subins">
										<i class="icon-ok bigger-110"></i> 提交
									</button>
								&nbsp; &nbsp; &nbsp;
								<button class="btn" type="reset" onclick="back()">
									<i class="icon-undo bigger-110"></i> 返回
								</button>
							</div>				
			      </div>
			</from>
		</div>	
	</body>
</html>
<script>

  var  flag='${record.flag}';
  var  transport_code='${record.transport_code}';
  $('#routecode').val(transport_code);
  $('#flag').val(flag);
  function find(){
		var route_code =  $("#route_code").find("option:selected").val();
		var status_code = $("#status_code").val();;
		var status = $('#status').val();
		var flag = $('#flag').val();

		
  }
  function back(){
	  
	  openDiv('${root}/control/radar/routecodeController/query.do');

	  
  }
  
  $(function(){
	  $("#subins").bind('click',function(){
		  var route_code =  $("#routecode").find("option:selected").val();
			var status_code = $("#status_code").val();;
			var status = $('#status').val();
			var flag = $('#flag').find("option:selected").val();
			var remark = $('#remark').val();
			if(route_code==-1){alert('物流服务商信息不能为空！');return;}
			if(status_code==''){alert('状态代码信息不能为空！');return;}
			if(status==''){alert('状态描述信息不能为空！');return;}
			if(remark.length>99){alert('备注信息不能超过99个字符！');return;}
			var param =$('#formRoute').serialize();
		 $.post('${root}/control/radar/routecodeController/update.do?'+param,	 
				 function(data){
			 if(data.toString()=='[object XMLDocument]'){
				  window.location='/BT-LMIS';
					return;
			  };
			 if(data.code==1){
				 alert('操作成功！');
				 openDiv('${root}/control/radar/routecodeController/query.do');
			 }else{
				 alert(data.code);
				 alert('操作失败！'); 
			 }
		 });
	  })
	  
	  
	  
  })
  
  
  
  
</script>