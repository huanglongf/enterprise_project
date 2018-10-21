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
				function updateuser(){
					 var param = "";
					 var param =$("#addForm").serialize();
					$.ajax({
						type: "POST",
					  	url: "${root}/control/login/userController/updateuser.do?"+param,
						dataType: "",
						data: '',
						success: function (data) {  
						    if(data.data==0){
			   	            	alert("操作成功！");
			   	            	loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/backToMain.do?time='+new Date().getTime());
			   	            }else if(data.data!=0){
			   	            	alert("操作失败！");
			   	            	alert(data.msg);
			   	             }
			                   }
					});	
				}
	
	</script>
	</head>
	<body>
		<div class="page-header">
			<h1 style='margin-left: 20px'>修改用户</h1>
		</div>
		<!-- 新增页面 -->
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
			<form id="addForm">
			<input id="id" type=text name="id" style="display:none" value="${queryParam.id}">
				<table> 
			   		<tr>
		  				<td width="160px" align="left"><label style="width: 95px;">用户编码</label></td>
		  				<td width="170px" align="left">
		  					<input type='text' id='user_code' name='user_code'   value="${queryParam.user_code }"  style="width: 160px;">
		  				</td>
		  				<td width="160px" align="left"><label style="width: 95px;">用户名称</label></td>
		  				<td width="170px" align="left">
		  					<input type='text' id='user_name' name='user_name' value="${queryParam.user_name }" style="width: 160px;">
		  				</td>
		  			</tr>
			   		<tr>
		  				<td width="160px" align="left"><label style="width: 95px;">手机</label></td>
		  				<td width="170px" align="left">
		  					<input type='text' id='user_phone' name='user_phone' value="${queryParam.user_phone }"    style="width: 160px;">
		  				</td>
		  				<td width="160px" align="left"><label style="width: 95px;">所属机构</label></td>
		  				<td width="170px" align="left">
		  					<!-- <input type='text' id='user_org_code' name='user_org_code' style="width: 160px;"> -->
		  					<select id="user_org_code" name="user_org_code"  data-edit-select="1">
			  				<option value= "">---请选择---</option>
			  				  <c:forEach items= "${org}" var= "street" >
			  					    <c:if test='${street.organization_code eq queryParam.user_org_code }'> 
									<option selected='selected' value="${street.organization_code}">${street.org_name}</option>
								 </c:if>
								 <c:if test='${street.organization_code ne queryParam.user_org_code }'> 
									<option  value="${street.organization_code}">${street.org_name}</option>
								 </c:if>
							 </c:forEach>  
						</select>
		  				</td>
		  			</tr>
			   		<tr>
		  				<td width="160px" align="left"><label style="width: 95px;">密码</label></td>
		  				<td width="170px" align="left">
		  					<input type='text' id='user_pwd' name='user_pwd'  value="${queryParam.user_pwd }"   style="width: 160px;">
		  				</td>
		  				<td width="160px" align="left"><label style="width: 95px;">确认密码</label></td>
		  				<td width="170px" align="left">
		  					<input type='text' id='user_pwd1' name='user_pwd1' value="${queryParam.user_pwd }" style="width: 160px;">
		  				</td>
		  			</tr>
			   		<tr>
		  				<td width="160px" align="left"><label style="width: 95px;">备注</label></td>
		  				<td width="170px" align="left">
		  					<input type='text' id='user_remark' name='user_remark'  value="${queryParam.user_remark }"   style="width: 160px;">
		  				</td>
		  			</tr>
			</table>
			</form>
			 </div>
			<div class= "modal-footer" >
				<button id= "btn_back" type= "button" class= "btn btn-default"  onclick="returntostart()">
					<i class= "icon-undo" aria-hidden= "true" ></i>返回
				</button>
     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="updateuser();" >
     				<i class= "icon-save" aria-hidden= "true" ></i>保存
     			</button>
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
