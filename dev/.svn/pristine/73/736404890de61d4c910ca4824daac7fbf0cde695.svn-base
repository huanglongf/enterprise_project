<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>assets/js/bootstrap.min.js" ></script>
	</head>
	<body>
		<div class="page-header">
			<h1>
				用户编辑
			</h1>
		</div>
			<div class="col-xs-12">
			<c:if test="${empty employee.id}">
				<form class="form-horizontal" id="menuform" role="form" action="${root}control/menuController/add.do">
			</c:if>
			<c:if test="${not empty employee.id}">
				<form class="form-horizontal" id="menuform" role="form" action="${root}control/menuController/update.do">
			</c:if>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="employee_number"> 工号&nbsp;<span class="red">*</span>: </label>
						<div class="col-sm-9">
							<input type="hidden" id="id" name="id" value="${employee.id}" />
                            <input type="hidden" id="status" name="status" value="${employee.status}" />
							<input id="employee_number" name="employee_number" type="text" placeholder="如：工号" class="col-xs-10 col-sm-5"   value="${employee.employee_number }"/> &nbsp;<dtusername></dtusername>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="username"> 登录名&nbsp;<span class="red">*</span>: </label>
						<div class="col-sm-9">
							<input id="username" name="username" type="text" placeholder="如：登录名" class="col-xs-10 col-sm-5"   value="${employee.username }"/> &nbsp;<dtusername></dtusername>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="name"> 昵称&nbsp;: </label>
						<div class="col-sm-9">
							<input id="name" name="name" type="text" placeholder="如：张三" class="col-xs-10 col-sm-5" value="${employee.name }"/>&nbsp;<dtname></dtname>
						</div>
					</div>
					<c:if test="${empty employee.id}">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="password"> 密码&nbsp;<span class="red">*</span>: </label>
						<div class="col-sm-9">
							<input id="password" name="password" type="password" placeholder="如：axc_123" value="" class="col-xs-10 col-sm-5" />&nbsp;<dtpwd></dtpwd>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="dpassword">再次输入密码&nbsp;<span class="red">*</span>: </label>
						<div class="col-sm-9">
							<input id="dpassword" name="dpassword" type="password" placeholder="如：axc_123" value="" class="col-xs-10 col-sm-5" />&nbsp;<dtpwd></dtpwd>
						</div>
					</div>					   
					</c:if>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="email"> 邮箱地址&nbsp;<span class="red">*</span>: </label>
						<div class="col-sm-9">
							<input id="email" name="email" type="text" placeholder="如：123456@163.com" class="col-xs-10 col-sm-5" value="${employee.email }" /> &nbsp;<dtemail></dtemail>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="iphone"> 手机号码&nbsp;: </label>
						<div class="col-sm-9">
							<input id="iphone" name="iphone" type="text" placeholder="如：12345678901" class="col-xs-10 col-sm-5" value="${employee.iphone }"/>&nbsp;<dtiphone></dtiphone>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="project_id"> 用户类型&nbsp;: </label>
						<div class="col-sm-9">
							<select id="project_id" name="project_id" class="col-xs-10 col-sm-5">
								<c:forEach items="${employee_project_id_list }" var="project_id">
									<option value="${project_id.dictValue }" <c:if test="${project_id.dictValue==employee.project_id}">selected="selected"</c:if>>${project_id.dictLabel }</option>
								</c:forEach>
							</select>&nbsp;
							<dtusername></dtusername>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 角色&nbsp;: </label>
						<div class="col-sm-9">
							<input id="roleid" name="roleid" type="hidden" id="form-field-1"  class="col-xs-10 col-sm-3" value="${erMap.id}"/>
							<input id="rolename" name="rolename" type="text" id="form-field-1"  readonly="readonly" class="col-xs-10 col-sm-3" value="${erMap.name}"/>&nbsp;<dtiphone></dtiphone>
							<div id="roleDrop" class="btn-group">
								<button data-toggle="dropdown" class="btn btn-inverse btn-xs dropdown-toggle">
									选择角色
									<span class="icon-caret-down icon-on-right"></span>
								</button>
								<ul class="dropdown-menu dropdown-inverse">
									<c:forEach items="${menu}" var="menu">
										<li><a href="javascript:void(0);" onclick="checkMenu('${menu.id}','${menu.name}');">${menu.name}</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<c:if test="${empty employee.id}">
								<button class="btn btn-info" type="button" id="subins" name="subins">
									<i class="icon-ok bigger-110"></i>
									提交
								</button>
							</c:if>
							<c:if test="${not empty employee.id}">
								<button class="btn btn-info" type="button" id="subup" name="subup">
									<i class="icon-ok bigger-110"></i>
									提交
								</button>
							</c:if>
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset" onclick="openDiv('${root}control/employeeController/employeeList.do');">
				 			<i class="icon-undo bigger-110"></i>
								返回
							</button>
						</div>
					</div>
				</form>
			</div>
		<div class="space-4"></div>
	</body>
	<script type="text/javascript">
		$('#roleDrop .dropdown-toggle').dropdown();
		$("#subins").click(function(){
			if($('#employee_number').val() == "") {
				alert("工号为空！");
				return;
			}
			if($('#email').val() == "") {
				alert("邮箱不能为空！");
				return;
			}
			if(!isEmail($('#email').val())){
				alert("邮箱格式不对!");
				return;
			}
			if($('#roleid').val() == ""){
				alert("角色不能为空！");
				return;
			}
			if(!checkpwd()){
				return 
			}
			var groups = "";
			var i = 1;
			$("#addedGroup").find("tr").each(function(){
				groups = groups + $(this).children().eq(0).find("input").val();
				if(i < $("#addedGroup tr").length){
					groups = groups + ",";
					i = i + 1;
				}
			})
			$.ajax({
				type: "POST",  
				url: root+"/control/employeeController/add.do?",  
				//我们用text格式接收  
				dataType: "json",   
				data:
					$("#menuform").serialize()
					+ "&groups="
					+ groups,
				success: function (obj) {  
				    alert(obj.msg);  
				    openDiv('${root}control/employeeController/toForm.do');
				}  
			}); 
		});
		$("#subup").click(function(){
			if($('#employee_number').val() == "") {
				alert("工号为空！");
				return;
			}
			if($('#email').val() == "") {
				alert("邮箱不能为空！");
				return;
			}
			if(!isEmail($('#email').val())){
				alert("邮箱格式不对!");
				return;
			}
			if($('#roleid').val() == ""){
				alert("角色不能为空！");
				return;
			}
			if(!checkpwd()){
				return 
			}
			var groups = "";
			var i = 1;
			$("#addedGroup").find("tr").each(function(){
				groups = groups + $(this).children().eq(0).find("input").val();
				if(i < $("#addedGroup tr").length){
					groups = groups + ",";
					i = i + 1;
				}
			})
			$.ajax({  
				type: "POST",  
				url: root+"/control/employeeController/update.do?",  
				dataType: "json",   
				data:
					$("#menuform").serialize()
					+ "&status=1"
					+ "&groups="
					+ groups,
				success: function (result) {  
					alert(result.result_content);
					
				}  
			}); 
		});
		function addErrorInfo(id,div,mes){
			if($("#"+id).val()==""){
				$(div).html("<span style='color:red'>"+mes+"</span>");
				return false;
			}else{
				$(div).html("");
				return true;
			}
		}
		function checkpwd(){
			if($("#password").val()!=$("#dpassword").val()){
				alert("两次密码输入不一致，请检查!");
				return false;
			}
			return true;
		}
		function checkMenu(roleid,rolename){
			 $("#roleid").val(roleid);
			 $("#rolename").val(rolename);
		}
		function addGroup(group, group_name){
			if($("#" + group).length > 0){
				alert("所选组别已添加！");
				return false;
				
			}
			$("#addedGroup").append(
					'<tr id="'+ group +'"><td width="10%" style="vertical-align:top"><input id="' 
					+ group
					+ '_input" type="hidden" value="'
					+ group 
					+ '" />'
					+ group_name 
					+ '</td><td width="10%"><button type="button" class="btn btn-xs btn-pink" onclick="delGroup('
					+ "'"+ group + "'"
					+ ');"><i class="icon-save"></i>退出</button></td></tr>');
			
		}
		function delGroup(group){
			$("#"+ group).remove();
			
		}
		 function isEmail(str){
		       var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
		       return reg.test(str);
		   }
	</script>
</html>