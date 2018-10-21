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
		<link type= "text/css" href= "<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
		<link type= "text/css" href= "<%=basePath %>daterangepicker/font-awesome.min.css" rel= "stylesheet" />
		<link type= "text/css" href= "<%=basePath %>daterangepicker/daterangepicker-bs3.css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>daterangepicker/moment.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
	    <script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
	    <script type="text/javascript" src="<%=basePath %>js/ajaxfileupload.js"></script>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
        <%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
        <script type="text/javascript">
			$(document).ready(function(){
				queryStore($("#gId").val());//店铺信息
				queryLogistics($("#gId").val());//组内成员
				queryWorkType($("#gId").val());//工单类型
				if($("#if_allotJS").val()==1){
					document.getElementById("if_allot").checked = true;
				}
				if($("#instructionJS").val()==''||$("#instructionJS").val()==null){	
					
				}else{
					document.getElementById("instruction").innerHTML =  $("#instructionJS").val();
				}
			});
			
		   function checkChange(fatherCode){
			   if($("#"+fatherCode+"_father").is(':checked')){
				   $("[id *=_"+fatherCode+"_child]").prop("checked",true);//全选   
				}else{
				   $("[id *=_"+fatherCode+"_child]").prop("checked",false);
				}
		   }

		   function changeChild(fatherCode){
			   $("#"+fatherCode+"_father").prop("checked",true);
			   }

			function backDress(){
				openDiv(root+'control/shopGroupController/search.do?pageName=search&tableName=tb_shop_group');
		}
         </script>
	</head>
<body>
		<div class="page-header" style="margin-left : 20px;">
		<input type="hidden" id="gId" name="gId" value="${obj.id}"></input>
		<input type="hidden" id="validateBox" name="validateBox"></input>
		<input type="hidden" id="validateBoxStore" name="validateBoxStore"></input>
		<input type="hidden" id="validateBoxLogistics" name="validateBoxLogistics"></input>
		<input type="hidden" id="superiorJs" name="superiorJs" value="${obj.superior}"></input>
		<input type="hidden" id="if_allotJS" name="if_allotJS" value="${obj.if_allot}"></input>
		<input type="hidden" id="instructionJS" name="instructionJS" value="${obj.instruction}"></input>
		<input type="hidden" id="st_id" name="st_id"></input>
		<input type="hidden" id="store" name="store"></input>
		<input type="hidden" id="selfwarehouse" name="selfwarehouse"></input>
		<input type="hidden" id="outsourcedwarehouse" name="outsourcedwarehouse"></input>
		<input type="hidden" id="g_id" name="g_id"></input>		
		<input type="hidden" id="transportCode" name="transportCode" value="${transportCode}"></input>
		<form id= "main_search">
			<table>
			          <% 
		  			   String  typeUpdate= (String)request.getAttribute("typeUpdate");
		  			  %>
		  			  <% if("2".equals(typeUpdate)){%>
		  			      <tr> 
		  			          <td width="10%">组别编码:</td>
		  			          <td width="60%">
		  			             <input name="group_code"  style="width: 40%;" id="group_code" data-edit-select="1" value="${obj.group_code}" readonly="readonly"></input> 
		  			          </td>
		  			      <tr> 
		  		          	 <td width="10%">组别名称:</td>
		  			          <td width="60%">
		  			              <input name="group_name" style="width: 40%;"id="group_name" value="${obj.group_name}" <c:if test="${menu_role.menu_1009==null}">readonly="readonly"</c:if>></input>
		  			              <%-- <input type="checkbox" id="if_allot"  name="if_allot" value="${obj.if_allot}"/>是否组内共享 --%>
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
										<option  value= "0" ${obj.group_type == "0"? "selected= 'selected'": "" } >店铺客服</option>
										<option  value= "1" ${obj.group_type == "1"? "selected= 'selected'": "" } >销售运营</option>
									</select>
			  			          </td>
			  			      <tr> 		  		
			  			      <tr> 
			  			          <td width="10%">是否组内共享:</td>
			  			          <td width="60%">
			  			          <c:choose>
				  			          <c:when test="${menu_role.menu_1009!=null && menu_role.menu_1009=='1009'}">
			  			          		 <input id= "if_allot" name= "if_allot" type= "checkbox" class= "ace ace-switch ace-switch-5"  ${obj.if_allot == "true"? "checked= 'checked'": "" } />
								  	 </c:when>
				  			         <c:otherwise>
				  			         	<input id= "if_allot" name= "if_allot" type= "checkbox" class= "ace ace-switch ace-switch-5" disabled="disabled" ${obj.if_allot == "true"? "checked= 'checked'": "" } />
				  			         </c:otherwise>
			  			          </c:choose>
									 <span class= "lbl" ></span>
			  			          </td>
			  			      <tr>
			  		          <tr> 
			  			          <td width="10%">是否异常组:</td>
			  			          <td width="60%">
			  			          <c:choose>
				  			          <c:when test="${menu_role.menu_1009!=null && menu_role.menu_1009=='1009'}">
			  			          		 <input id= "error_flag" name= "error_flag" type= "checkbox" class= "ace ace-switch ace-switch-5"  ${obj.error_flag == "true"? "checked= 'checked'": "" } />
								  	 </c:when>
				  			         <c:otherwise>
				  			         	<input id= "error_flag" name= "error_flag" type= "checkbox" class= "ace ace-switch ace-switch-5" disabled="disabled" ${obj.error_flag == "true"? "checked= 'checked'": "" } />
				  			         </c:otherwise>
			  			          </c:choose>
									 <span class= "lbl" ></span>
			  			          </td>
			  			      <tr>
			  		          <tr> 
			  			          <td width="10%">开启邮件通知:</td>
			  			          <td width="60%">
			  			          <c:choose>
				  			          <c:when test="${menu_role.menu_1009!=null && menu_role.menu_1009=='1009'}">
			  			          		 <input id= "isWoEmail" name= "isWoEmail" type= "checkbox" class= "ace ace-switch ace-switch-5"  ${obj.is_wo_email == "true"? "checked= 'checked'": "" } />
								  	 </c:when>
				  			         <c:otherwise>
				  			         	<input id= "isWoEmail" name= "isWoEmail" type= "checkbox" class= "ace ace-switch ace-switch-5" disabled="disabled" ${obj.is_wo_email == "true"? "checked= 'checked'": "" } />
				  			         </c:otherwise>
			  			          </c:choose>
									 <span class= "lbl" ></span>
			  			          </td>
			  			      <tr>
		  			  <%}else{%>
		  			      <tr> 
		  			          <td width="10%">组别编码:</td>
		  			          <td width="60%">
		  			             <input name="group_code" style="width: 40%;" id="group_code" value="${obj.group_code}"></input> 
		  			          </td>

		  		          </tr>
		  			      <tr> 
		  		          	 <td width="10%">组别名称:</td>
		  			          <td width="60%">
		  			            <div style="width:40%;">
		  			              <input name="group_name" style="width: 40%;" id="group_name" value="${obj.group_name}"></input>
		  			             <%--  <input type="checkbox" id="if_allot"  name="if_allot" value="${obj.if_allot}"/>是否组内共享 --%>
		  			           </div>
		  			          </td>
		  		          </tr>
		  		          <tr> 
		  			          <td width="10%">是否组内共享:</td>
		  			          	<td width="60%">
		  			          		<input id= "if_allot" name= "if_allot" type= "checkbox" class= "ace ace-switch ace-switch-5"  ${obj.if_allot == "true"? "checked= 'checked'": "" } />
								 	<span class= "lbl" ></span>
		  			          </td>
			  			  <tr>  		          
		  			  <%}%>
		  		<tr>
		  			<td width="10%">说明:</td>
		  			<td width="20%">
		  			  <textarea name="instruction" cols ="50" maxlength="200" rows = "3" id="instruction" value="${obj.instruction}"></textarea>
		  			</td>
		  		</tr>		  		
			</table>
		</form>
		<div style="width: 50%;margin-left: 10%;margin-top: 5px;">
			<button class= "btn btn-xs btn-yellow" onclick= "groupSubmit()" >
			<i class= "icon-hdd" ></i>提交
		    </button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 	<button class= "btn btn-xs btn-yellow" onclick= "backDress()" >
			<i class= "icon-hdd" ></i>返回
		    </button>&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		</div>
   <div>
  <div style="width: 80%;margin-left:1%;">
	<h3 class="header blue lighter smaller">
		<i class="icon-list-alt smaller-90"></i>
		组内成员
		<c:if test="${menu_role.menu_1001!=null && menu_role.menu_1001=='1001'}">
			 <a class="btn btn-link" href="javascript:;" onclick= "downloadStore('emp')" >
				<i class="icon-plus-sign bigger-120 green"></i>
					下载模版
			</a>
			<a class="btn btn-link" href="javascript:selectUploadModel();">
				<i class="icon-plus-sign bigger-120 green"></i>
					上传模版
			</a>		
		</c:if>
		<c:if test="${menu_role.menu_1004!=null && menu_role.menu_1004=='1004'}">
			<a class="btn btn-link" href="javascript:opEmp('1');">
				<i class="icon-plus-sign bigger-120 green"></i>
					启用
			</a>	
			<a class="btn btn-link" href="javascript:opEmp('0');">
				<i class="icon-plus-sign bigger-120 green"></i>
					禁用
			</a>
			
			<a class="btn btn-link" href="javascript:opAuto('1');">
				<i class="icon-plus-sign bigger-120 green"></i>
					开启自动分配
			</a>	
			<a class="btn btn-link" href="javascript:opAuto('0');">
				<i class="icon-plus-sign bigger-120 green"></i>
					关闭自动分配
			</a>									 
		</c:if>
		<c:if test="${menu_role.menu_1010!=null && menu_role.menu_1010=='1010'}">
			<a class="btn btn-link" href="javascript:delEmp();">
				<i class="icon-plus-sign bigger-120 green"></i>
					删除
			</a>
		</c:if>
		
	</h3>
   </div>
    
    <div style="margin-left: 10px;">
            <logistics></logistics>
    </div>
    <div class= "page-header"></div>	
  </div>
   <c:if test='${obj.group_type == "1"}'>
	  <div>
	   	<div style="width: 40%;margin-left:1%;">
			<h3 class="header blue lighter smaller">
				<i class="icon-list-alt smaller-90"></i>
				工单类型
				  <c:if test="${menu_role.menu_1007!=null && menu_role.menu_1007=='1007'}">
					 <a class="btn btn-link" href="javascript:;" onclick= "toWkType()" >
						<i class="icon-plus-sign bigger-120 green"></i>
							新增
					</a>
				 </c:if>
				<c:if test="${menu_role.menu_1008!=null && menu_role.menu_1008=='1008'}"> 
					<a class="btn btn-link" href="javascript:;" onclick= "del_tab_work_type()">
						<i class="icon-plus-sign bigger-120 green"></i>
							删除
					</a>	
				</c:if>	 
			</h3>
		  </div>
		  <div style="margin-left: 10px;">
	            <workType></workType>
	    </div>
	    <div class= "page-header"></div>	
	  	</div>
  	</c:if>
  	<div>
	  
	  <div id= "wk_type" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >工单类型</h4>
					</div>
					<div class= "modal-body" >
						<input id= "type_id" name= "type_id" type= "hidden" />
					<table>
						<tr align="center">
						  <td style="width: 40%" align="right">选择类型</td>
						  <td id="typeOne" align="left">
		  			         <select id="typeEd" name="typeEd" style="width: 60%;"data-edit-select="1">
									<option value="">---请选择---</option>
									<c:forEach items="${listWkType}" var="model">
										<option value="${model.type_code}" >
											${model.type_name}</option>
									</c:forEach>
							  </select>
							</td>
							
							<td id="typeTwo" align="left">
		  			         <select id="typeEds" name="typeEds"style="width: 60%;">
									<option value="">---请选择---</option>
									<c:forEach items="${listWkType}" var="model">
										<option value="${model.type_code}" >
											${model.type_name}</option>
									</c:forEach>
							  </select>
							</td>
						</tr>
					</table>
						
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back2" type= "button" class= "btn btn-default" data-dismiss="modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="saveWkTypeGroup();" >
		     				<i class="icon-save" aria-hidden= "true" ></i><span id="btn_type_text_wkType">保存</span>
		     			</button>
					</div>
				</div>
			</div>
		</div>
  
   <div style="width: 40%;margin-left:1%;">
	<h3 class="header blue lighter smaller">
		<i class="icon-list-alt smaller-90"></i>
		店铺
		 <c:if test="${menu_role.menu_1003!=null && menu_role.menu_1003=='1003'}">
			 <a class="btn btn-link" href="javascript:;" onclick= "toStore()" >
				<i class="icon-plus-sign bigger-120 green"></i>
					新增
			</a>
			<a class="btn btn-link" href="javascript:;" onclick="del_tab_store();">
				<i class="icon-plus-sign bigger-120 green"></i>
					删除
			</a>			 
			<a class="btn btn-link" href="javascript:;" onclick= "downloadStore('store')" >
				<i class="icon-plus-sign bigger-120 green"></i>
					下载模版
			</a>
			<a class="btn btn-link" href="javascript:selectUploadModel2();">
				<i class="icon-plus-sign bigger-120 green"></i>
					上传模版
			</a>		
		</c:if>
	</h3>
   </div>
    <div style="margin-left: 10px;">
             <store></store>
    </div>
  </div>
  <div>
  </div>
  
  <!-- 店铺 -->
		<div id= "type_store" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >店铺</h4>
					</div>
					<div class= "modal-body" >
						<input id= "store_id" name= "store_id" type= "hidden" />
					<table>
						<tr align="center">
						  <td style="width: 40%" align="right">选择店铺</td>
						  <td id="storeOne" align="left">
		  			         <select id="storeEd" name="storeEd" style="width: 60%;"data-edit-select="1">
									<option value="">---请选择---</option>
									<c:forEach items="${listDP}" var="model">
										<option value="${model.store_code}" >
											${model.store_name}</option>
									</c:forEach>
							  </select>
							</td>
							
							<td id="storeTwo" align="left">
		  			         <select id="storeEds" name="storeEds"style="width: 60%;">
									<option value="">---请选择---</option>
									<c:forEach items="${listDP}" var="model">
										<option value="${model.store_code}" >
											${model.store_name}</option>
									</c:forEach>
							  </select>
							</td>
						</tr>
					</table>
						
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back2" type= "button" class= "btn btn-default" data-dismiss="modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="saveStoreGroup();" >
		     				<i class="icon-save" aria-hidden= "true" ></i><span id="btn_type_text_store">保存</span>
		     			</button>
					</div>
				</div>
			</div>
		</div>
	 
  	<!--组内成员批量上传  -->
		<div id= "batch_store" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >组内成员批量上传</h4>
					</div>
					<div class= "modal-body" >
					<table>
						<tr align="center">
	  			<td width="15%" align="center">
							<button class="btn btn-xs btn-primary" onclick="$('input[id=file1]').click();">
								<i class="icon-edit"></i>选择文件
							</button>
						</td>
			  			<td width="30%" align="center" >
							<input id="file1" type="file" name='file' style="display:none;">  
							<input id="photoCover" class="input-large" type="text" style="height:30px;width: 100%;" readonly="readonly">  
		  				</td>
		  				<td align="center" width="10%">
							<button id="imps" name="imps" class="btn btn-xs btn-yellow" onclick="imp();">
								<i class="icon-hdd"></i>导入
							</button>
						</td>		
						</tr>
					</table>
						
					</div>
					<div class= "modal-footer" >
					</div>
				</div>
			</div>
		</div>
		<div id= "batch_store2" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >店铺批量绑定</h4>
					</div>
					<div class= "modal-body" >
					<table>
						<tr align="center">
		  					<td width="15%" align="center">
								<button class="btn btn-xs btn-primary" onclick="$('input[id=file2]').click();">
									<i class="icon-edit"></i>选择文件
								</button>
							</td>
				  			<td width="30%" align="center" >
								<input id="file2" type="file" name='file' style="display:none;">  
								<input id="photoCover2" class="input-large" type="text" style="height:30px;width: 100%;" readonly="readonly">  
			  				</td>
			  				<td align="center" width="10%">
								<button id="imps2" name="imps" class="btn btn-xs btn-yellow" onclick="impStore();">
									<i class="icon-hdd"></i>导入
								</button>
							</td>		
						</tr>
					</table>
						
					</div>
					<div class= "modal-footer" >
					</div>
				</div>
			</div>
		</div>
		
	</body>
	<input type="hidden" id="levelEvaluate" name="levelEvaluate"></input>
	<input type="hidden" id="levelEvaluateNot" name="levelEvaluateNot"></input>
	<script type="text/javascript">
	    $("#btn_back3").click(function(){
	    	$(" #levelEvaluate").val("");
	    	$(" #levelEvaluateNot").val("");
		});
	    $('#divLeven').click(function(){
	    	$(" #levelEvaluate").val("");
	    	$(" #levelEvaluateNot").val("");
	    });  
	
		//根据条件让checkbox选中
	    function Verification(newLog){
	    	$("#gd input[name=level]").each(function(){ //遍历table里的全部checkbox
	          if($(this).val()==newLog) {//如果被选中
	        	$("input:checkbox[value='"+newLog+"']").prop('checked','true');
			  }
	        });
		}
	    function isEmail(str){
		       var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
		       return reg.test(str);
		   }
		function groupSubmit() {
			var ifAllot=0;
			var instr = $("#instruction").val();
			var groupName = $("#group_name").val();
			var groupCode = $("#group_code").val();
			var groupType = $("#group_type").val();
			var errorFlag = 0;
			if(groupType == -1){
				alert("请选择组别类型！")
				return;
			}
			var isWoEmail = 0;
			if ($("input[id='isWoEmail']").is(':checked')) {
				isWoEmail = 1;
			}
			 var email=$("#email").val();
			 if(isWoEmail==1){
				 if(email==''){
		            	alert("填写组别邮箱");
						return;
					}
				 if(!isEmail(email)){
					 alert("邮箱格式不正确")
						return;
				 }
			 }
			 
// 			if(groupCode==''){
// 				alert("填写组别编码！");
// 				return;
// 			}
//             if(groupName==''){
//             	alert("填写组别名称");
// 				return;
// 			}
//             if(groupCode.length >= 20){
//             	alert("组别编码过长,请重新输入！");
//             	$("#group_code").val("");
// 				return;
// 			}
// 			if(groupName.length >= 10){
// 				alert("组别名称过长,请重新输入！");
// 				$("#group_name").val("");
// 				return;
// 			}
			if (instr.length >= 200) {
				alert("说明填写过长，请控制在200字符以内！");
				return;
			}
			if ($("input[id='if_allot']").is(':checked')) {
				ifAllot = 1;
			}
			if ($("input[id='error_flag']").is(':checked')) {
				errorFlag = 1;
			}
		  	var url = root + "/control/shopGroupController/saveFromGroup.do?id="+ $("#gId").val();
			var data = "instruction="+ $('#instruction').val()+ "&group_name="+$('#group_name').val()+"&if_allot=" + ifAllot+ "&group_type=" +groupType+ "&error_flag=" + errorFlag+"&email="+email+"&isWoEmail="+isWoEmail;

			$.ajax({
				type : "POST",
				url : url,
				//我们用text格式接收  
				dataType : "json",
				data : data,
				success : function(jsonStr) {
					alert(jsonStr.out_result_reason);
					$("#div_view").load('/BT-LMIS/control/shopGroupController/updateSgroup.do?id='+jsonStr.g_id);
					/* if(jsonStr.out_result_reason=='保存成功'){
						$('#group_code').attr("disabled",true); 
						//$('#group_name').attr("disabled",true); 
						$("#gId").val(jsonStr.g_id);
					}else{
						//清空文本框
						document.getElementById("group_code").value="";
						document.getElementById("group_name").value="";
						$("#superior option:first").prop("selected", 'selected');
						$("input[name='if_allot']").each(function(){this.checked=false;}); 
						document.getElementById('instruction').value = '';
						$("#status option:first").prop("selected", 'selected');
					} */
				}
			});
		}

		//工单类型
		function queryWorkType(gid) {
			var url = root+ "/control/shopGroupController/queryWorkType.do?gid="+gid;
			var htm_td = "";
			$.ajax({
						type : "POST",
						url : url,
						data : "",
						dataType : "json",
						success : function(jsonStr) {
							var text = "<table id='sample-table-1' class='table table-striped table-bordered table-hover'>"
                                    + "<thead class='center'>"
								    + "<tr>"
								    +"<th tr class='center'><input type='checkbox' id='checkAllWorkType' onclick='inverseCkbWorkType()'/></th>"
									+ "<th class='center'>序号</th><th class='center'>工单类型</th>";
							for (i = 0; i < jsonStr.length; i++) {
								htm_td = htm_td
										+ "<tbody><tr class='center'><td><input id='ckb' name='ckbWT' type='checkbox' value="+jsonStr[i].id+"></td><td>"
										+ (i + 1)
										+ "</td>"
										+"<td>"+ jsonStr[i].name+ "</td>"
										+"</tr>";
							}
							$("workType").html(text + htm_td + "</tbody></table>");
						}
					});
		}
		//店铺
		function queryStore(gId) {
			var url = root+ "/control/shopGroupController/queryStoreList.do?g_id="+gId;
			var htm_td = "";
			$.ajax({
					type : "POST",
					url : url,
				  	dataType: "text",
		           	data:'',
		    		cache:false,
		    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		           	success: function (data){
					  $("store").html(data);
					}
				});
		}
		

		 function pageJump() {
			 $.ajax({
					type: "POST",
		           	url:'/BT-LMIS/control/shopGroupController/queryStoreList.do?g_id='+$('#gId').val()+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val(),
		           	dataType: "text",
		           	data:'',
		    		cache:false,
		    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		           	success: function (data){
		              $("store").html(data);
		           	}
			  	});
				 }
		 


		function toStore() {
			document.getElementById("storeEds").disabled=false;
			$("#storeEd option:first").prop("selected", 'selected');
			document.getElementById("storeOne").style.display="block";//隐藏
			document.getElementById("storeTwo").style.display="none";//显示
			$("input[name='warehouseType']").each(function(){this.checked=false;}); 
			var gId = $("#gId").val();
			if ($("#gId").val() == "" || $("#gId").val() == null) {
				alert("添加主信息");
				return;
			}
			$("#type_store").modal('show');
			$("#btn_type_text_store").text("提交");
		}

		function toWkType() {
			document.getElementById("typeEds").disabled=false;
			$("#storeEd option:first").prop("selected", 'selected');
			document.getElementById("typeOne").style.display="block";//隐藏
			document.getElementById("typeTwo").style.display="none";//显示
			$("input[name='warehouseType']").each(function(){this.checked=false;}); 
			var gId = $("#gId").val();
			if ($("#gId").val() == "" || $("#gId").val() == null) {
				alert("添加主信息");
				return;
			}
			$("#wk_type").modal('show');
			$("#btn_type_text_wkType").text("提交");
		}
		function toLogistics() {
			document.getElementById("carrierEds").disabled = false;
			document.getElementById("one").style.display="block";//隐藏
			document.getElementById("two").style.display="none";//显示
			$("#carrierEd option:first").prop("selected", 'selected');
			var obj=document.getElementsByName('level');
			var selCheckBox='';
			for(var i=0; i<obj.length; i++){
			   if(obj[i].checked){
				   selCheckBox+=obj[i].value+','; //如果选中，将value添加到变量s中
			   } 
			} 
            var strQkSel= new Array(); //数组 
            strQkSel = selCheckBox.split(",");
            
			for (i=0;i<strQkSel.length-1 ;i++ ){
				var typeQp = strQkSel[i].split("_")[0];
				VerificationQKLogistics(typeQp);
				VerificationQK(strQkSel[i]);
			}
			var gId = $("#gId").val();
			if ($("#gId").val() == "" || $("#gId").val() == null) {
				alert("添加主信息");
				return;
			}
			$("#type_logistics").modal('show');
			$("#btn_type_text_logistics").text("提交");
		}

		function update_tab_workOrder(id, wk_code, level, g_id) {
			var url = root + "/control/groupMessageController/findWorkType.do?";
			var htm_td = "";
			$.ajax({
				type : "POST",
				url : url,
				data : "",
				dataType : "json",
				success : function(jsonStr) {
					var htmlStr = '<option value="">---请选择---</option>';
					for (i = 0; i < jsonStr.length; i++) {
						htmlStr = htmlStr
								+ '<option value='+jsonStr[i].name+'>'
								+ jsonStr[i].name + '</option>';
					}
					$("#workName").html(htmlStr);
				}
			});
			$("#type_wk").modal('show');
			$("#btn_type_text_wk").text("确认修改");
			$("#wrokId").val(id);
			$("#workName").val(wk_code);
			$("#level").val(level);
			$("#g_id").val(g_id);

			$("#workNameJs").val(wk_code);

			//让页面checkbox制空
			$("#gd input[name=level]").each(function() { //遍历table里的全部checkbox
				$("[name='level']").removeAttr("checked");//取消全选   
			});
			$("#gd input[name=logistics]").each(function() {
				$("[name='logistics']").removeAttr("checked");//取消全选   
			});
		}

		function saveStoreGroup() {
			var gId = $('#gId').val();
			var code = $('#storeEd').val();
			if (code == null || code == '') {
				alert("请选择店铺！")
				return;
			}
			var url = root + "/control/shopGroupController/addStoreGroup.do";
			$.ajax({
				type : "POST",
				url : url,
				data : {store:code, g_id:gId},
				dataType : "json",
				success : function(jsonStr) {
					alert(jsonStr.out_result_reason);
					if (jsonStr.out_result == 1) {
						initializeSelect('storeEd');
						queryStore(gId);
						$("#btn_back2").click();
					}
				}
			});
		}

		function saveWkTypeGroup() {
			var gId = $('#gId').val();
			var code = $('#typeEd').val();
			if (code == null || code == '') {
				alert("请选择工单类型！")
				return;
			}
			var url = root + "/control/shopGroupController/addWorkGroup.do";
			var data = "workType=" + code + "&g_id=" + gId;
			$.ajax({
				type : "POST",
				url : url,
				data : data,
				dataType : "json",
				success : function(jsonStr) {
					alert(jsonStr.out_result_reason);
					if (jsonStr.out_result == 1) {
						initializeSelect('typeEd');
						queryWorkType(gId);
						$("#btn_back2").click();
					}
				}
			});
		}
		
		function queryLogistics(gid) {
			var url = root+ "/control/shopGroupController/queryEmp.do?gid="+gid;
			var htm_td = "";
			$.ajax({
						type : "POST",
						url : url,
						data : "",
						dataType : "json",
						success : function(jsonStr) {
							var text = "<table id='sample-table-1' class='table table-striped table-bordered table-hover'>"
                                    + "<thead class='center'>"
								    + "<tr>"
								    +"<th tr class='center'><input type='checkbox' id='checkAll' onclick='inverseCkb()'/></th>"
									+ "<th class='center'>序号</th><th class='center'>工号</th><th class='center'>成员名称</th><th class='center'>登陆账户</th><th class='center'>是否自动分配</th><th class='center'>状态</th>";
							for (i = 0; i < jsonStr.length; i++) {
								htm_td = htm_td
										+ "<tbody><tr class='center'><td><input id='ckb' name='ckb' type='checkbox' value="+jsonStr[i].id+"></td><td>"
										+ (i + 1)
										+ "</td>"
										+"<td>"+ jsonStr[i].employee_number+ "</td>"
										+"<td>"+ jsonStr[i].name+ "</td>"
										+"<td>"+ jsonStr[i].username+ "</td>"
										+"<td>"+ jsonStr[i].isAutoAllc+ "</td>"
										+"<td>"+ jsonStr[i].status_name+ "</td>"
										+"</tr>";
							}
							$("logistics").html(text + htm_td + "</tbody></table>");
						}
					});
		}
		function del_tab_store(id) {
			var url = root
					+ "/control/groupMessageController/deleteStore.do?id=" + id;
			$.ajax({
				type : "POST",
				url : url,
				dataType : "json",
				data : "",
				success : function(jsonStr) {
					alert(jsonStr.out_result_reason);
					queryStore($("#gId").val());
				}
			});
		}


		function del_tab_work_type(id) {
			if ($("input[name='ckbWT']:checked").length >= 1) {
					var priv_ids = [];
					$("input[name='ckbWT']:checked").each(function() {
						priv_ids.push($.trim($(this).val()));
					});
					 if (!confirm("确定删除吗?")) {
						return;
					}
					$.ajax({
							type : "POST",
						    url : root + "/control/shopGroupController/deleteWorkType.do",
			  	 			dataType : "json",
					   		data : "privIds=" + priv_ids,
				 			success : function(data) {
					 			alert(data.out_result_reason);
					 			if(data.out_result==1){
						 			queryWorkType($("#gId").val());//工单类别
					 			}
							}
				}); 
				} else {
					alert("请至少选择一行!");
				}
		}
		function del_tab_store(id) {
			if ($("input[name='ckb1']:checked").length >= 1) {
					var priv_ids = [];
					$("input[name='ckb1']:checked").each(function() {
						priv_ids.push($.trim($(this).val()));
					});
					 if (!confirm("确定删除吗?")) {
						return;
					}
					$.ajax({
							type : "POST",
						    url : root + "/control/shopGroupController/deleteStore.do",
			  	 			dataType : "json",
					   		data : "privIds=" + priv_ids,
				 			success : function(data) {
					 			alert(data.out_result_reason);
					 			if(data.out_result==1){
					 				queryStore($("#gId").val());//工单类别
					 			}
							}
				}); 
				} else {
					alert("请至少选择一行!");
				}
		}
		function del_tab_vendor(carrier, group) {
			var url = root
					+ "/control/groupMessageController/deleteVendorGroup.do?carrier="
					+ carrier + "&group=" + group;
			$.ajax({
				type : "POST",
				url : url,
				dataType : "json",
				data : "",
				success : function(jsonStr) {
					alert(jsonStr.out_result_reason);
					queryLogistics($("#gId").val());
				}
			});
		}
		//修改数据
		function up_tab_store(id, store, outsourcedwarehouse, selfwarehouse,group) {
			document.getElementById("storeTwo").style.display="block";//隐藏
			document.getElementById("storeOne").style.display="none";//显示
			//店铺模糊查询处理
			var url = root+ "/control/groupMessageController/findtbStore.do?id="+ id;
			$.ajax({
				type : "POST",
				url : url,
				dataType : "json",
				data : "",
				success : function(result) {
					var store= result.store;
					var sp= result.storePower;
					$("#storeEds").children(":first").siblings().remove();
		        	$("#storeEds").siblings("ul").children(":first").siblings().remove();

		        	$("#storeEds").val(result.store_code);
		        	var content1= "";
		        	var content2= "";
		        	for(var i= 0; i< store.length; i++){
						content1+= '<option value="' + store[i].store_code + '" ';
						content2+= '<li class="m-list-item';
						if(store[i].store_code == sp.store) {
							content1+= 'selected= "selected" ';
							content2+= ' m-list-item-active'
							$("#storeEds").next().val(store[i].store_name);
							$("#storeEds").next().attr("placeholder", store[i].store_name);
						}
						content1+= '>'+store[i].store_name+'</option>';
						content2+= '" data-value="' + store[i].store_code + '">' + store[i].store_name + '</li>';
					}
		    		// 装上之后的值
					$("#storeEds option:eq(0)").after(content1);
					$("#storeEds").siblings("ul").children(":first").after(content2);
				}
			}); 
			document.getElementById("storeEds").disabled=true;
			/* if (store != '' && store != -1) {
				var opts = document.getElementById("storeEd");
				for (var i = 0; i < opts.options.length; i++) {
					if (store == opts.options[i].value) {
						opts.options[i].selected = 'selected';
						break;
					}
				}
			}  */
			if (outsourcedwarehouse == "是") {
				if (selfwarehouse == "是") {
					document.getElementById("warehouseTypeOne").checked = true;
					document.getElementById("warehouseTypeTwo").checked = true;
				} else {
					document.getElementById("warehouseTypeOne").checked = false;
					document.getElementById("warehouseTypeTwo").checked = true;
				}
			}
			if (selfwarehouse == "是") {
				if (outsourcedwarehouse == "是") {
					document.getElementById("warehouseTypeOne").checked = true;
					document.getElementById("warehouseTypeTwo").checked = true;
				} else {
					document.getElementById("warehouseTypeOne").checked = true;
					document.getElementById("warehouseTypeTwo").checked = false;
				}
			}
			$("#type_store").modal('show');
			$("#btn_type_text_store").text("确认修改");
			$("#st_id").val(id);
			$("#store").val(store);
			$("#storeEd").val(store);
			$("#selfwarehouse").val(selfwarehouse);
			$("#outsourcedwarehouse").val(outsourcedwarehouse);
			$("#g_id").val(group);

		}
		//修改数据
		function up_tab_vendor(carrier, group) {
			document.getElementById("two").style.display="block";//隐藏
			document.getElementById("one").style.display="none";//显示
			var url = root+ "/control/groupMessageController/queryTransportVendorSelect.do?carrier="+ carrier + "&group=" + group;
			$.ajax({
				type : "POST",
				url : url,
				dataType : "json",
				data : "",
				success : function(result) {
					var transport= result.transport;
					var sp= result.storePower;
					$("#carrierEds").children(":first").siblings().remove();
		        	$("#carrierEds").siblings("ul").children(":first").siblings().remove();

		        	$("#carrierEds").val(result.transport_code);
		        	var content1= "";
		        	var content2= "";
		        	for(var i= 0; i< transport.length; i++){
						content1+= '<option value="' + transport[i].transport_code + '" ';
						content2+= '<li class="m-list-item';
						if(transport[i].transport_code == sp.carrier) {
							content1+= 'selected= "selected" ';
							content2+= ' m-list-item-active'
							$("#carrierEds").next().val(transport[i].transport_name);
							$("#carrierEds").next().attr("placeholder", transport[i].transport_name);
						}
						content1+= '>'+transport[i].transport_name +'</option>';
						content2+= '" data-value="' + transport[i].transport_code + '">' + transport[i].transport_name + '</li>';
					}
		    		// 装上之后的值
					$("#carrierEds option:eq(0)").after(content1);
					$("#carrierEds").siblings("ul").children(":first").after(content2);
				}
			}); 
			
			var obj=document.getElementsByName('level');
			var selCheckBox='';
			for(var i=0; i<obj.length; i++){
			   if(obj[i].checked){
				   selCheckBox+=obj[i].value+','; //如果选中，将value添加到变量s中
			   } 
			} 
            var strQkSel= new Array(); //数组 
            strQkSel = selCheckBox.split(",");
           
		    if ($("input[type='checkbox']:checked").length > 0) {
				for (i = 0; i < strQkSel.length - 1; i++) {
					var typeQp = strQkSel[i].split("_")[0];
					VerificationQKLogistics(typeQp);
					VerificationQK(strQkSel[i]);
				}
			}
			/* if (carrier != '' && carrier != -1) {
				var opts = document.getElementById("carrierEd");
				for (var i = 0; i < opts.options.length; i++) {
					if (carrier == opts.options[i].value) {
						opts.options[i].selected = 'selected';
						break;
					}
				}
			} */ 
			document.getElementById("carrierEds").disabled = true;
			$("#type_logistics").modal('show');
			$("#btn_type_text_logistics").text("确认修改");
			var url = root
					+ "/control/groupMessageController/displayWorkOrder.do?carrier="
					+ carrier + "&group=" + group;
			$.ajax({
				type : "POST",
				url : url,
				data : "",
				dataType : "json",
				success : function(jsonStr) {
					for (i = 0; i < jsonStr.length; i++) {
						var newLevel = jsonStr[i].wo_level;
						var level = newLevel.replace("level", "");
						//级别比对不一样的数据
						var type = jsonStr[i].wo_type;
						obtainValueCheckBox(type, level);
						obtainValueCheckBoxType(type);
					}
				}
			});
			$("#transportCode").val(carrier);
			$("#g_id").val(group);
			
		}

		function obtainValueCheckBox(type, level) {
			if (level == '01') {
				level = '1';
			} else if (level == '02') {
				level = '2';
			} else if (level == '03') {
				level = '3';
			} else if (level == '04') {
				level = '4';
			} else if (level == '05') {
				level = '5';
			} else if (level == '06') {
				level = '6';
			} else if (level == '07') {
				level = '7';
			} else if (level == '08') {
				level = '8';
			} else if (level == '09') {
				level = '9';
			} else {
				level = '10';
			}
			var newStr = type + '_' + level;
			$("#gd input[name=level]").each(
					function() { //遍历table里的全部checkbox
						if ($(this).val() == newStr) {//如果被选中
							$("input:checkbox[value='" + newStr + "']").prop(
									'checked', 'true');
						}
					});
		}
		function obtainValueCheckBoxType(type) {
			$("#gd input[name=logistics]").each(
					function() {
						if ($(this).val() == type) {
							$("input:checkbox[value='" + type + "']").prop(
									'checked', 'true');
						}
					});
		}

        function downloadStore(obj){
         window.open('${root}/control/shopGroupController/downloadTemplete.do?downloadTemplete='+obj);
        }
        
       function selectUploadModel(){
    	   $("#batch_store").modal('show');
       } 
       
	   function selectUploadModel2(){
    	   $("#batch_store2").modal('show');
       } 
       
       $(function () {
		    $("#upload").click(function () {
		    	//loadingStyle();
		        ajaxFileUpload();
		    });
	    
	    	$('input[id=file1]').change(function() { 
		        $('#photoCover').val($(this).val());
	        }); 
	    	$('input[id=file2]').change(function() { 
		        $('#photoCover2').val($(this).val());
	        }); 
		})
		
		
		  	/**
	   	* 导入
	   	*/
	  	function imp(){
            if($("#photoCover").val().length==0){
               alert("请先选择文件");
               return;
            }
            loadingStyle();
	  		$("#imps").hide();
			$.ajaxFileUpload({
				//上传地址
				url: root+'control/shopGroupController/import.do?gid='+$('#gId').val(),
				//是否需要安全协议，一般设置为false
	            secureuri: false, 
	          	//文件上传域的ID
				fileElementId: 'file1', 
				contentType:'application/x-www-form-urlencoded;charset=UTF-8',
				//返回值类型 一般设置为json
				dataType: 'json', 
				//服务器成功响应处理函数
				success: function (data, status){
					alert(data.msg);
					alert(data.message);
					if(data.code == 200) {
						if(data.out_result==0){
                           if(confirm("是否下载导入异常的数据结果?")){
                               var url=root+"control/shopGroupController/exportExcel.do?bat_id="+data.bat_id;
                            	window.location.href=url;
                            }
						}
	                }
					queryLogistics($("#gId").val());//组内成员
				},error: function (data, status, e){
					//服务器响应失败处理函数
// 					openDiv(root+'control/shopGroupController/import.do','');
	           	}
			})
			cancelLoadingStyle();
			 $("#batch_store").modal('hide');
			$("#imps").show();
	        return;
	  	}
       
	  	function impStore(){
            if($("#photoCover2").val().length==0){
               alert("请先选择文件");
               return;
            }
            loadingStyle();
	  		$("#impStore").hide();
			$.ajaxFileUpload({
				//上传地址
				url: root+'control/shopGroupController/import2.do?gid='+$('#gId').val(),
				//是否需要安全协议，一般设置为false
	            secureuri: false, 
	          	//文件上传域的ID
				fileElementId: 'file2', 
				contentType:'application/x-www-form-urlencoded;charset=UTF-8',
				//返回值类型 一般设置为json
				dataType: 'json', 
				//服务器成功响应处理函数
				success: function (data, status){
					alert(data.msg);
					if(data.code == 200) {
						alert(data.message);
	                }
					queryStore($("#gId").val());//组内成员
				},
				error: function (data, status, e){
					//服务器响应失败处理函数
// 					openDiv(root+'control/shopGroupController/import.do','');
	           	}
			})
			cancelLoadingStyle();
			 $("#batch_store2").modal('hide');
			$("#impStore").show();
	        return;
	  	}

	  	
		function ajaxFileUpload() {
    	   loadingStyle();
			$.ajaxFileUpload({
				url: '${root}/control/groupMessageController/upload.do', //用于文件上传的服务器端请求地址
	            secureuri: false, //是否需要安全协议，一般设置为false
				fileElementId: 'file1', //文件上传域的ID
				dataType: 'json', //返回值类型 一般设置为json
				data:{'group':$('#gId').val()},
					//服务器成功响应处理函数
					success: function (data, status){
						//
					if(data.code==1){alert('操作成功');cancelLoadingStyle();
					queryStore($("#gId").val());
					}else{alert('操作失败');cancelLoadingStyle();}
					 $("#batch_store").modal('hide');
					 window.open('${root}/DownloadFile/'+data.path);
					},error: function (data, status, e){
						//服务器响应失败处理函数
						alert('操作失败');
						cancelLoadingStyle();
						 $("#batch_store").modal('hide');
		           	}
				})
		        return false;
			}
       
		function toStore() {
			document.getElementById("storeEds").disabled=false;
			$("#storeEd option:first").prop("selected", 'selected');
			document.getElementById("storeOne").style.display="block";//隐藏
			document.getElementById("storeTwo").style.display="none";//显示
			$("input[name='warehouseType']").each(function(){this.checked=false;}); 
			var gId = $("#gId").val();
			if ($("#gId").val() == "" || $("#gId").val() == null) {
				alert("添加主信息");
				return;
			}
			$("#type_store").modal('show');
			$("#btn_type_text_store").text("提交");
		}
		function inverseCkb(){
			
			var checklist = document.getElementsByName ("ckb");
			if(document.getElementById("checkAll").checked){
			   	for(var i=0;i<checklist.length;i++){
			      checklist[i].checked = 1;
			  } 
			}else{
			  for(var j=0;j<checklist.length;j++){
			     checklist[j].checked = 0;
			  }
			}
			
			/* $('[name=ckb]:checkbox').each(function(){
				this.checked=!this.checked;
			}); */
		}

		function inverseCkbWorkType(){
			var checklist = document.getElementsByName ("ckbWT");
			if(document.getElementById("checkAllWorkType").checked){
			   	for(var i=0;i<checklist.length;i++){
			      checklist[i].checked = 1;
			  } 
			}else{
			  for(var j=0;j<checklist.length;j++){
			     checklist[j].checked = 0;
			  }
			}
		}
		function inverseCkbStore(){
			var checklist = document.getElementsByName ("ckb1");
			if(document.getElementById("checkAllStore").checked){
			   	for(var i=0;i<checklist.length;i++){
			      checklist[i].checked = 1;
			  } 
			}else{
			  for(var j=0;j<checklist.length;j++){
			     checklist[j].checked = 0;
			  }
			}
		}

		
		function opEmp(status){
			if ($("input[name='ckb']:checked").length >= 1) {
				var priv_ids = [];
				$("input[name='ckb']:checked").each(function() {
					priv_ids.push($.trim($(this).val()));
				});
				if (!confirm("确定操作吗?")) {
					return;
				}
				$.ajax({
						type : "POST",
					    url : root + "/control/shopGroupController/opGroup.do?status="+status,
		  	 			dataType : "json",
				   		data : "privIds=" + priv_ids,
			 			success : function(data) {
				 			alert(data.out_result_reason);
				 			if(data.out_result==1){
					 		queryLogistics($("#gId").val());//组内成员
				 }
				}
			});
			} else {
				alert("请选择一行!");
			}
		}
		
		
		
		function opAuto(status){
			if ($("input[name='ckb']:checked").length >= 1) {
				var priv_ids = [];
				$("input[name='ckb']:checked").each(function() {
					priv_ids.push($.trim($(this).val()));
				});
				if (!confirm("确定操作吗?")) {
					return;
				}
				$.ajax({
						type : "POST",
					    url : root + "/control/shopGroupController/opAuto.do?status="+status,
		  	 			dataType : "json",
				   		data : "privIds=" + priv_ids +"&gId="+$("#gId").val(),
			 			success : function(data) {
				 			alert(data.out_result_reason);
				 			if(data.out_result==1){
					 		queryLogistics($("#gId").val());//组内成员
				 }
				}
			});
			} else {
				alert("请选择一行!");
			}
		}
		
		
		function delEmp(){
			if ($("input[name='ckb']:checked").length >= 1) {
				var priv_ids = [];
				$("input[name='ckb']:checked").each(function() {
					priv_ids.push($.trim($(this).val()));
				});
				if (!confirm("确定删除吗?")) {
					return;
				}
				$.ajax({
						type : "POST",
					    url : root + "/control/shopGroupController/delEmp.do?",
		  	 			dataType : "json",
		  	 			data : "privIds=" + priv_ids +"&gId="+$("#gId").val(),
			 			success : function(data) {
				 			alert(data.out_result_reason);
				 			if(data.out_result==1){
					 		queryLogistics($("#gId").val());//组内成员
				 }
				}
			});
			} else {
				alert("请选择一行!");
			}
		}
		
		
	</script> 
	
	
	
</html>
