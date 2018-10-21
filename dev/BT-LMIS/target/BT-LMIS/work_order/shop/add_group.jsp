<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<style type="text/css">
            textarea{ resize:none; }
        </style>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
        <%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
        <script type="text/javascript">
			function backDress(){
				openDiv(root+'control/shopGroupController/search.do?pageName=search&tableName=tb_shop_group');
	         	}
			
			  function addgroupSubmit(){
				  var if_share=0;
				  var errorFlag = 0;
				  var group_code=$("#group_code").val();
				  var group_name=$("#group_name").val();
				  var instruction=$("#instruction").val();
				  var groupType=$("#group_type").val();
				  var email=$("#email").val();
				  var isWoEmail = 0;
					if ($("input[id='isWoEmail']").is(':checked')) {
						isWoEmail = 1;
					}
				  if(groupType == -1){
						alert("请选择组别类型！")
						return;
					}
				  if(group_code==''){
						alert("填写组别编码！");
						return;
					}
		            if(group_name==''){
		            	alert("填写组别名称");
						return;
					}
		            if(isWoEmail==1){
			            if(email==''){
			            	alert("填写组别邮箱");
							return;
						}
			            if(!isEmail(email)){
			            	alert("邮箱格式不正确");
							return;
						}
		            }
		            if(group_code.length > 20){
		            	alert("组别编码过长,请重新输入！");
		            	$("#group_code").val("");
						return;
					}
					if(group_name.length >20){
						alert("组别名称过长,请重新输入！");
						$("#group_name").val("");
						return;
					}
					if (instruction.length > 200) {
						alert("说明填写过长，请控制在200字符以内！");
						return;
					}
				  if ($("input[id='if_allot']").is(':checked')) {
						if_share = 1;
				  } 
				  if ($("input[id='error_flag']").is(':checked')) {
						errorFlag = 1;
					}
				var dat="group_code="+group_code+"&group_name="+group_name+"&instruction="+instruction+"&if_share="+if_share+ "&group_type=" 
					+groupType+ "&error_flag=" + errorFlag+"&email="+email+"&isWoEmail="+isWoEmail;
				var urls = "/BT-LMIS/control/shopGroupController/addGroup.do";
				$.ajax({
					type : "POST",
					url : urls,
				  	dataType: "json",
				  	data:dat,
		    		cache:false,
		    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		           	success: function (result){
		           		alert(result.out_result_reason);
		           		if(result.out_result=="1"){
		           			backDress();
		           		}
					}
				});
				
				 }
			  function isEmail(str){
			       var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
			       return reg.test(str);
			   }
         </script>
	</head>
<body>

		<div class="page-header" style="margin-left : 20px;">
		<form id= "main_search">
			<table>
		  			      <tr> 
		  			          <td width="10%">组别编码:</td>
		  			          <td width="60%">
		  			             <input name="group_code" style="width: 40%;" id="group_code" value=""  placeholder="最长为20个字符"></input> 
		  			          </td>

		  		          </tr>
		  			      <tr> 
		  		          	 <td width="10%">组别名称:</td>
		  			          <td width="60%">
		  			            <div style="width:40%;">
		  			              <input name="group_name" style="width: 40%;" id="group_name" value="" placeholder="最长为20个字符"></input>
		  			              <!-- <input type="checkbox" id="if_allot"  name="if_allot" value=""/>是否组内共享 -->
		  			           </div>
		  			          </td>
		  		          </tr>
		  		          <tr> 
		  		          	 <td width="10%">组别邮箱:</td>
		  			          <td width="60%">
		  			              <input name="email" type="email" style="width: 40%;"id="email" value="${obj.email}" <c:if test="${menu_role.menu_1009==null}">readonly="readonly"</c:if>></input>
		  			          </td>
		  		          	</tr>
		  		          <tr> 
		  			          <td width="10%">组别类型:</td>
		  			          <td width="60%">
		  			           <select id= "group_type" name= "group_type" style="width: 40%;" >
									<option  value= "-1" >---请选择---</option>
									<option  value= "0"  >店铺客服</option>
									<option  value= "1"  >销售运营</option>
								</select>
		  			          </td>
		  			      <tr> 
		  			      
		  			       <tr> 
		  			          <td width="10%">是否组内共享:</td>
		  			          <td width="60%">
		  			          	 <input id= "if_allot" name= "if_allot" type= "checkbox" class= "ace ace-switch ace-switch-5"  />
								 <span class= "lbl" ></span>
		  			          </td>
		  			      <tr>	
		  			      		  			          
		  		          <tr> 
		  			          <td width="10%">是否异常组:</td>
		  			          <td width="60%">
		  			          	 <input id= "error_flag" name= "error_flag" type= "checkbox" class= "ace ace-switch ace-switch-5"  />
								 <span class= "lbl" ></span>
		  			          </td>
		  			      <tr>	
		  			      <tr> 
		  			          <td width="10%">开启邮件通知:</td>
		  			          <td width="60%">
		  			          	 <input id= "isWoEmail" name= "isWoEmail" type= "checkbox" class= "ace ace-switch ace-switch-5"  />
								 <span class= "lbl" ></span>
		  			          </td>
		  			      <tr>	  		          
		  		<tr>
		  			<td width="10%">说明:</td>
		  			<td width="20%">
		  			  <textarea name="instruction" cols ="50" maxlength="200" rows = "3" id="instruction"  placeholder="最长为200个字符"></textarea>
		  			</td>
		  		</tr>		  		
			</table>
		</form>
		<div style="width: 100%;margin-left: 10%;margin-top: 5px;">
			<button class= "btn btn-xs btn-yellow" onclick= "addgroupSubmit()" >
			<i class= "icon-hdd" ></i>提交
		    </button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 	<button class= "btn btn-xs btn-yellow" onclick= "backDress()" >
			<i class= "icon-hdd" ></i>返回
		    </button>&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		</div>
</html>
