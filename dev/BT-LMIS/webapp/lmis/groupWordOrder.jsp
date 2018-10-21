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
				queryLogistics($("#gId").val());//物流商
				if($("#if_allotJS").val()==1){
					document.getElementById("if_allot").checked = true;
				}
				if($("#instructionJS").val()==''||$("#instructionJS").val()==null){	
					
				}else{
					document.getElementById("instruction").innerHTML =  $("#instructionJS").val();
				}
			});
			//物流
			document.getElementById("two").style.display="none";//隐藏
			//店铺
			document.getElementById("storeTwo").style.display="none";//隐藏


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
				openDiv(root+'control/groupMessageController/listGroup.do');
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
		<input type="hidden" id="process_controlJS" name="process_controlJS" value="${obj.process_control}"></input>
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
		  			              <input name="group_name" style="width: 40%;"id="group_name" value="${obj.group_name}"></input>
		  			              <input type="checkbox" id="if_allot"  name="if_allot" value="${obj.if_allot}"/>自动分配工单
		  			              <input type="checkbox" id="process_control"  name="process_control" ${obj.process_control=='1'?"checked='checked'":""}/>不控制处理权限
		  			          </td>
		  		          </tr>				  			          
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
		  			              <input name="group_name" style="width: 40%;" id="group_name" value="${obj.group_name}"></input>
		  			              <input type="checkbox" id="if_allot"  name="if_allot" value="${obj.if_allot}"/>自动分配工单
		  			              <input type="checkbox" id="process_control"  name="process_control" ${obj.process_control=='1'?"checked='checked'":""}/>不控制处理权限
								  <input type="checkbox" id="is_qa"  name="is_qa" />是否QA
		  			          </td>
		  		          </tr>		  		          
		  			  <%}%>
		  		
		  		<tr>
		  			<td width="10%">上级组别:</td>
		  			<td width="60%">
		  			  <div style="width:40%;">
		  			  <select id="superior" name="superior"  data-edit-select="1" >
			  					<option value= "">---请选择---</option>
			  					<c:forEach items= "${list}" var= "model" >
									<option value= "${model.id }" ${model.id == modelSup.superior ? "selected= 'selected'": "" } >
										${model.group_name}
									</option>
								</c:forEach>
						</select>
						</div>
		  			</td>

		  		</tr>

		  		<tr>
		  			<td width="10%">启用状态:</td>
		  			<td width="60%" nowrap= "nowrap">
		  			<div style="width:40%;">
		  			    <select id="status"  name="status" data-edit-select="1">
		  			       <option value="1" <c:if test="${obj.status==true}">selected='selected'</c:if>>启用</option>
		  			       <option value="0" <c:if test="${obj.status==false}">selected='selected'</c:if>>禁用</option>
		  			    </select>
		  			</div>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td width="10%">说明:</td>
		  			<td width="20%">
		  			  <textarea name="instruction" cols ="50" maxlength="200" rows = "3" id="instruction" value="${obj.instruction}"></textarea>
		  			</td>
		  		</tr>		  		
			</table>
		</form>
		<div style="width: 100%;margin-left: 10%;margin-top: 5px;">
			<button class= "btn btn-xs btn-yellow" onclick= "groupSubmit()" >
			<i class= "icon-hdd" ></i>提交
		    </button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 	<button class= "btn btn-xs btn-yellow" onclick= "backDress()" >
			<i class= "icon-hdd" ></i>返回
		    </button>&nbsp;&nbsp;&nbsp;&nbsp;
		</div>
		</div>
		
   <div>
  <div style="width: 30%;margin-left:1%;">
	<h3 class="header blue lighter smaller">
		<i class="icon-list-alt smaller-90"></i>
		物流商
				<a class="btn btn-link" href="javascript:;" onclick= "toLogistics()" >
		<i class="icon-plus-sign bigger-120 green"></i>
			添加物流商
	</a>
	</h3>
   </div>
    
    <div style="margin-left: 10px;">
            <logistics></logistics>
    </div>
    <div class= "page-header"></div>	
  </div>
  <div>
  
  
   <div style="width: 40%;margin-left:1%;">
	<h3 class="header blue lighter smaller">
		<i class="icon-list-alt smaller-90"></i>
		店铺
				<a class="btn btn-link" href="javascript:;" onclick= "toStore()" >
		<i class="icon-plus-sign bigger-120 green"></i>
			添加店铺
	</a>
	
	<a class="btn btn-link" href="javascript:;" onclick= "downloadStore()" >
		<i class="icon-plus-sign bigger-120 green"></i>
			下载模版
	</a>
	<a class="btn btn-link" href="javascript:selectUploadModel();">
		<i class="icon-plus-sign bigger-120 green"></i>
			上传模版
	</a>
	</h3>
   </div>
   
   
    <div style="margin-left: 10px;">
             <store></store>
    </div>
  </div>
  <div>
  </div>
		<!-- 物流 -->
		<div id= "type_logistics" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" style= "width: 65%;" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" id="divLeven">
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >物流</h4>
					</div>
					<div class= "modal-body" >
						<input id= "store_id" name= "store_id" type= "hidden" />
					<table>
						<tr align="center">
						  <td style="width: 20%">请选择物流&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						  <td id="one">
		  			         <select id="carrierEd" name="carrierEd"
								style="width: 60%;" 
								data-edit-select="1"
								>
									<option value="">---请选择---</option>
									<c:forEach items="${listLog}" var="model">
										<option value="${model.transport_code }">${model.transport_name}</option>
									</c:forEach>
							  </select>
						  </td>
						  <td id="two">
		  			         <select id="carrierEds" name="carrierEds"
								style="width: 60%;">
									<option value="">---请选择---</option>
									<c:forEach items="${listLog}" var="model">
										<option value="${model.transport_code }">${model.transport_name}</option>
									</c:forEach>
							  </select>
						  </td>
						</tr>
						<tr align="center">
						  <td style="width: 20%">可处理工单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						  <td style="width: 120%">
						      <table id="gd">
						        <c:forEach items= "${listWL}" var= "model" >
						        <tr>
						           <td colspan="10">
						           <input type="checkbox"  name="logistics" id="${model.orderCode}_father"  value="${model.orderCode}" onchange="checkChange('${model.orderCode}')"/>${model.orderName}
						           </td>
						        </tr>
						        <tr id="jbCheckBox">
			                      <c:if test= "${not empty model.levelOne }" >
				                      <td style="width: 10%">&nbsp;&nbsp;<input type="checkbox" onchange="changeChild('${model.orderCode}')"  name="level" id="${model.levelOne}_${model.orderCode}_child"  value="${model.orderCode}_${model.levelOne}"/>0${model.levelOne}级别</td>
			                      </c:if>
			                       <c:if test= "${not empty model.levelTwo }" >
				                      <td style="width: 10%">&nbsp;&nbsp;<input type="checkbox" onchange="changeChild('${model.orderCode}')" name="level" id="${model.levelTwo}_${model.orderCode}_child"  value="${model.orderCode}_${model.levelTwo}"/>0${model.levelTwo}级别</td>
			                      </c:if>
			                       <c:if test= "${not empty model.levelThree }" >
				                      <td style="width: 10%">&nbsp;&nbsp;<input type="checkbox" onchange="changeChild('${model.orderCode}')" name="level" id="${model.levelThree}_${model.orderCode}_child"  value="${model.orderCode}_${model.levelThree}"/>0${model.levelThree}级别</td>
			                      </c:if>
			                       <c:if test= "${not empty model.levelFour }" >
				                      <td style="width: 10%">&nbsp;&nbsp;<input type="checkbox" onchange="changeChild('${model.orderCode}')" name="level" id="${model.levelFour}_${model.orderCode}_child"  value="${model.orderCode}_${model.levelFour}"/>0${model.levelFour}级别</td>
			                      </c:if>
			                       <c:if test= "${not empty model.levelFive }" >
				                      <td style="width: 10%">&nbsp;&nbsp;<input type="checkbox" onchange="changeChild('${model.orderCode}')" name="level" id="${model.levelFive}_${model.orderCode}_child"  value="${model.orderCode}_${model.levelFive}"/>0${model.levelFive}级别</td>
			                      </c:if>
			                       <c:if test= "${not empty model.levelSix }" >
				                      <td style="width: 10%">&nbsp;&nbsp;<input type="checkbox" onchange="changeChild('${model.orderCode}')" name="level" id="${model.levelSix}_${model.orderCode}_child"  value="${model.orderCode}_${model.levelSix}"/>0${model.levelSix}级别</td>
			                      </c:if>
			                       <c:if test= "${not empty model.levelSeven }" >
				                      <td style="width: 10%">&nbsp;&nbsp;<input type="checkbox" onchange="changeChild('${model.orderCode}')" name="level" id="${model.levelSeven}_${model.orderCode}_child"  value="${model.orderCode}_${model.levelSeven}"/>0${model.levelSeven}级别</td>
			                      </c:if>
			                       <c:if test= "${not empty model.levelEight }" >
				                      <td style="width: 10%">&nbsp;&nbsp;<input type="checkbox" onchange="changeChild('${model.orderCode}')" name="level" id="${model.levelEight}_${model.orderCode}_child"  value="${model.orderCode}_${model.levelEight}"/>0${model.levelEight}级别</td>
			                      </c:if>
			                       <c:if test= "${not empty model.levelNine }" >
				                      <td style="width: 10%">&nbsp;&nbsp;<input type="checkbox" onchange="changeChild('${model.orderCode}')" name="level" id="${model.levelNine}_${model.orderCode}_child"  value="${model.orderCode}_${model.levelNine}"/>0${model.levelNine}级别</td>
			                      </c:if>
			                      <c:if test= "${not empty model.levelTen }" >
				                      <td style="width: 10%">&nbsp;&nbsp;<input type="checkbox" onchange="changeChild('${model.orderCode}')" name="level" id="${model.levelTen}_${model.orderCode}_child"  value="${model.orderCode}_${model.levelTen}"/>${model.levelTen}级别</td>
			                      </c:if>
						        </tr>
						        </c:forEach>
						      </table>
						  </td>
						</tr>
					</table>
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back3" type= "button" class= "btn btn-default" data-dismiss="modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="saveLogistics();" >
		     				<i class="icon-save" aria-hidden= "true" ></i><span id="btn_type_text_logistics">保存</span>
		     			</button>
					</div>
				</div>
			</div>
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
						<tr>
						  <td style="width: 40%" align="right"><input type="checkbox"  id="warehouseTypeOne"  name="warehouseType" value="0" >&nbsp;&nbsp;&nbsp; 自营仓</td>
						  <td align="center"><input type="checkbox" id="warehouseTypeTwo" name="warehouseType" value="1" >&nbsp;&nbsp;&nbsp;外包仓 </td>
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
		<!--店铺批量上传  -->
		<div id= "batch_store" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >店铺批量上传</h4>
					</div>
					<div class= "modal-body" >
					<table>
						<tr align="center">
						<input id="file1" type="file" name='file' style='display:none'>  
					    	<div style='text-align: center;'>  
						    	<input id="photoCover" class="input-large" type="text" style="height:30px;">  
						   		<a class="btn" onclick="$('input[id=file1]').click();">浏览</a> 
						    	<a  id='upload' class="btn"  href='javascript:void(0)'>上传</a>
							</div>  		
						</tr>
					</table>
						
					</div>
					<div class= "modal-footer" >
						<!-- <button id= "btn_back2" type= "button" class= "btn btn-default" data-dismiss="modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="saveStoreGroupBatch();" >
		     				<i class="icon-save" aria-hidden= "true" ></i><span id="btn_type_text_store">保存</span>
		     			</button> -->
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
	    function loadLevel(){
	    	 var obj=document.getElementsByName('logistics');
		     var log='';
		     for(var i=0; i<obj.length; i++){
		        if(obj[i].checked){
		        	log+=obj[i].value+','; //如果选中，将value添加到变量s中
		        }
		     } 
		     var oneLevel = document.getElementById("levelEvaluate").value;
		     var twoLevel = document.getElementById("levelEvaluateNot").value;
		     
		     if(oneLevel==''){
		    	 document.getElementById("levelEvaluate").value=log; //选中
			 }else{
				 document.getElementById("levelEvaluateNot").value = document.getElementById("levelEvaluate").value;
			 }
		     if(twoLevel==''){
		    	 document.getElementById("levelEvaluateNot").value=log; //选中
			 }else{
				 document.getElementById("levelEvaluate").value = log;
			 }
		     var strThree;
		     var strThreeSelect;
		     var bjThree;
		     var levelEvaluateOne = $("#levelEvaluate").val();
			 var levelEvaluateTwo = $("#levelEvaluateNot").val();
			 if($("#levelEvaluate").val()==''){
				 var strQkDy= new Array(); //数组 
				 strQkDy = levelEvaluateTwo.split(",");
				 for (i=0;i<strQkDy.length-1 ;i++ ){
					 selectLevelQK(strQkDy[i]);
				 }
			 }else{
				 if(levelEvaluateOne!=levelEvaluateTwo){
					 var strBjOne= new Array(); //数组 
					 strBjOne = levelEvaluateOne.split(",");
					 var strBjTwo= new Array(); //数组 
					 strBjTwo = levelEvaluateTwo.split(",");
					 if(strBjOne.length<strBjTwo.length){
						 strThree = compareList(levelEvaluateOne,levelEvaluateTwo);
						 var newStrThree = strThree+",";
						 var strQk= new Array(); //数组 
						 strQk = newStrThree.split(",");
						 for (i=0;i<strQk.length-1 ;i++ ){
							 selectLevelQK(strQk[i]);
						 }
					 }else{
						 strThreeSelect = compareList(levelEvaluateOne,levelEvaluateTwo);
						 var newStrThreeSelect = strThreeSelect+",";
						 var strSelect= new Array(); //定义一数组 
						 strSelect = newStrThreeSelect.split(",");
						 for (i=0;i<strSelect.length-1 ;i++ ){
							selectLevel(strSelect[i]);
						 } 
						 /* bjThree = compareListJg(levelEvaluateOne,strThreeSelect);
						 var newBjThree = bjThree+","
						
						 strQkToo = newBjThree.split(",");
						 for (i=0;i<strQkToo.length-1 ;i++ ){
							 selectLevelQK(strQkToo[i]);
							 VerificationQKLogistics(strQkToo[i]);
						 } */
					 }
				 }else{
					 var strLev = $("#levelEvaluate").val();
					 var strToo = $("#levelEvaluateTwo").val();
					 var strNull= new Array(); //定义一数组 
					 strNull = strLev.split(",");
					 for (i=0;i<strNull.length-1 ;i++ ){
						selectLevel(strNull[i]);
					 }  
				 } 
			 }	 
		}



		
	    //比较两个数组不同数据
		function compareListJg(levelEvaluateOne,strThreeSelect){
			var newStrThreeSelect = strThreeSelect+","
			var strO= new Array(); //定义一数组 
			strO = levelEvaluateOne.split(",");
			var strT= new Array(); //定义二数组 
			strT = newStrThreeSelect.split(",");
			var result= new Array(); 
			for(var i=0;i<strT.length-1;i++){
				if(strO.indexOf(strT[i]) < 0){
					result.push(strT[i]);
				}
			}
			for(var j=0;j<strO.length-1;j++){
				 if(strT.indexOf(strO[j]) < 0 ){
					 result.push(strO[j]);
			     }
			}
			return result;
		}
		
		//比较两个数组不同数据
		function compareList(levelEvaluateOne,levelEvaluateTwo){
			var strOne= new Array(); //一数组 
			strOne = levelEvaluateOne.split(",");
			var strTwo= new Array(); //二数组 
			strTwo = levelEvaluateTwo.split(",");
		
			var strThree= new Array(); //三数组 
			for(var i=0;i<strTwo.length-1;i++){
				if(strOne.indexOf(strTwo[i]) < 0){
					strThree.push(strTwo[i]);
				}
			}
			for(var j=0;j<strOne.length-1;j++){
				 if(strTwo.indexOf(strOne[j]) < 0 ){
					 strThree.push(strOne[j]);
			     }
			}
			return strThree;
		}
		//初始化数据
		function selectLevel(strVal){
			 var levelArray = new Array("1","2","3","4","5","6","7","8","9","10");
             var newLog;
			 for(var i = 1;i <= levelArray.length; i++) {
				 newLog = strVal+"_"+i;
				 Verification(newLog); 
			 }
		}
		//初始化数据
		function selectLevelQK(strValQK){
			var levelArray = new Array("1","2","3","4","5","6","7","8","9","10");
            var newLogQK;
			for(var i = 1;i <= levelArray.length; i++) {
				newLogQK = strValQK+"_"+i;
				VerificationQK(newLogQK); 
			}
		}
		
	   function VerificationQK(newLogQK){
	    	$("#gd input[name=level]").each(function(){ //遍历table里的全部checkbox
	          if($(this).val()==newLogQK) {//如果被选中
	        	$("input:checkbox[value='"+newLogQK+"']").prop('checked',false);
			  }
	        }); 
		}

	   function VerificationQKLogistics(newLogQK){
	    	$("#gd input[name=logistics]").each(function(){ //遍历table里的全部checkbox
	          if($(this).val()==newLogQK) {//如果被选中
	        	$("input:checkbox[value='"+newLogQK+"']").prop('checked',false);
			  }
	        }); 
		}
		
		//根据条件让checkbox选中
	    function Verification(newLog){
	    	$("#gd input[name=level]").each(function(){ //遍历table里的全部checkbox
	          if($(this).val()==newLog) {//如果被选中
	        	$("input:checkbox[value='"+newLog+"']").prop('checked','true');
			  }
	        });
		}
		function groupSubmit() {
			var instr = $("#instruction").val();
			var groupName = $("#group_name").val();
			var groupCode = $("#group_code").val();
			if(group_code==''){
				alert("填写组别编码！");
				return;
			}
            if(groupName==''){
            	alert("填写组别名称");
				return;
			}
            if(groupCode.length >= 10){
            	alert("组别编码过长,请重新输入！");
            	$("#group_code").val("");
				return;
			}
			if(groupName.length >= 10){
				alert("组别名称过长,请重新输入！");
				$("#group_name").val("");
				return;
			}
            
			if (instr.length >= 200) {
				alert("说明填写过长，请控制在200字符以内！");
				return;
			}
			var ifAllot;
			if ($("#if_allot").is(':checked')) {
				ifAllot = 1;
			} else {
				ifAllot = 0;
			}
			var processControl;
			if ($("#process_control").is(':checked')) {
				processControl = 1;
			} else {
				processControl = 0;
			}
            var isQa;
            if ($("#is_qa").is(':checked')) {
                isQa = 1;
            } else {
                isQa = 0;
            }
			if ($("#gId").val() == "") {
				var url = root
						+ "/control/groupMessageController/saveFromGroup.do?groupType=1";
			} else {
				var url = root
						+ "/control/groupMessageController/saveFromGroup.do?groupType=2&id="
						+ $("#gId").val();
			}
			var dFlag = 1;
			var create_by = 1;
			var update_by = 1;
			var data = "group_code=" + $('#group_code').val() + "&group_name="
					+ $('#group_name').val() + "&instruction="
					+ $('#instruction').val() + "&status=" + $('#status').val()
					+ "&if_allot=" + ifAllot + "&process_control=" + processControl + "&is_qa="+isQa+"&dFlag=" + dFlag
					+ "&create_by=" + create_by + "&update_by=" + update_by;
			if ($('#superior').val() == '' || $('#superior').val() == null) {

			} else {
				data = data + "&superior=" + $('#superior').val();
			}
			$.ajax({
				type : "POST",
				url : url,
				//我们用text格式接收  
				dataType : "json",
				data : data,
				success : function(jsonStr) {
					alert(jsonStr.out_result_reason);
					if(jsonStr.out_result_reason=='保存成功'){
						$('#group_code').attr("disabled",true); 
						//$('#group_name').attr("disabled",true); 
						$("#gId").val(jsonStr.g_id);
					}else{
						//清空文本框
						document.getElementById("group_code").value="";
						//document.getElementById("group_name").value="";
						$("#superior option:first").prop("selected", 'selected');
						$("input[name='if_allot']").each(function(){this.checked=false;}); 
						document.getElementById('instruction').value = '';
						$("#status option:first").prop("selected", 'selected');
					}
				}
			});
		}
		//店铺
		function queryStore(gId) {
			var url = root+ "/control/groupMessageController/queryStoreList.do?g_id="+gId;
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
		           	url:'/BT-LMIS/control/groupMessageController/queryStoreList.do?g_id='+$('#gId').val()+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val(),
		           	dataType: "text",
		           	data:'',
		    		cache:false,
		    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		           	success: function (data){
		              $("store").html(data);
		           	}
			  	});
				 }
		 
		//物流公司
		function queryLogistics(gId) {
			var url = root
					+ "/control/groupMessageController/queryLogistics.do?g_id="
					+ gId;
			var htm_td = "";
			$
					.ajax({
						type : "POST",
						url : url,
						data : "",
						dataType : "json",
						success : function(jsonStr) {
							var text = "<table id='sample-table-1' class='table table-striped table-bordered table-hover'>"
                                    + "<thead class='center'>"
								    + "<tr>"
									+ "<th class='center'>序号</th><th class='center'>物流商</th>"
									+ "<th class='center'>操作</th></tr></thead>";
							for (i = 0; i < jsonStr.length; i++) {
								htm_td = htm_td
										+ "<tbody><tr class='center'><td>"
										+ (i + 1)
										+ "</td><td>"
										+ jsonStr[i].transport_name
										+ "</td><td><button class='btn btn-xs btn-info' onclick='up_tab_vendor(\""
										+ jsonStr[i].carrier + "\",\""
										+ jsonStr[i].group
										+ "\")'>修改</button>|<button class='btn btn-xs btn-info'  onclick='del_tab_vendor(\""
										+ jsonStr[i].carrier
										+ "\",\""
										+ jsonStr[i].group
										+ "\")'>删除</button></td></tr>"
							}
							$("logistics").html(text + htm_td + "</tbody></table>");
						}
					});
		}

		function toWkLevel() {
			var gId = $("#gId").val();
			if ($("#gId").val() == "" || $("#gId").val() == null) {
				alert("添加主信息");
				return;
			}
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
								+ '<option value='+jsonStr[i].CODE+'>'
								+ jsonStr[i].name + '</option>';
					}
					$("#workName").html(htmlStr);
				}
			});
			$("#type_wk").modal('show');
			$("#btn_type_text_wk").text("提交");
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
			var obj = document.getElementsByName('warehouseType');
			var depot = '';
			for (var i = 0; i < obj.length; i++) {
				if (obj[i].checked) {
					if (i == obj.length - 1) {
						depot += obj[i].value;
					} else {
						depot += obj[i].value + ','; //如果选中，将value添加到变量s中
					}
				}
			}
			if (code == null || code == '') {
				alert("请选择店铺！")
				return;
			}
			if (depot == null || depot == '') {
				alert("请选择仓库！")
				return;
			}
			var url = root + "/control/groupMessageController/addStoreGroup.do";
			var data = "store=" + code + "&g_id=" + gId + "&depot=" + depot;
			if ($("#btn_type_text_store").text() == "确认修改") {
				data = data + "&storeType=2&id=" + $("#st_id").val();
			} else {
				data = data + "&storeType=1"
			}
			$.ajax({
				type : "POST",
				url : url,
				data : data,
				dataType : "json",
				success : function(jsonStr) {
					alert(jsonStr.out_result_reason);
					if (jsonStr.out_result == 1) {
						queryStore(gId);
						$("#btn_back2").click();
					}else{
						queryStore(gId);
						//$("#btn_back2").click();
					}
				}
			});
		}
		function saveLogistics() {
			var log = "";
			var leven = "";
			$("input:checkbox[name='logistics']:checked").each(function() {
				log += $(this).val() + ",";
			});
			$("input:checkbox[name='level']:checked").each(function() {
				leven += $(this).val() + ",";
			});
			var transportCode = $('#carrierEd').val();
			var transportCodeNew = $('#transportCode').val();
			
			var gId = $('#gId').val();
			if (transportCode == null || transportCode == '') {
				if (transportCodeNew == null || transportCodeNew == '') {
					alert("请选择物流商");
					return;
				} else {
					transportCode = transportCodeNew;
				}
			}
			var obj=document.getElementsByName('level');
		    var len=0;;
		    for(var i=0; i<obj.length; i++){
		        if(obj[i].checked){
		        	len+=1;
			    }
		     }
			if(len==0){
				alert("请勾选级别！");
				return;
			}
			var url = root
					+ "/control/groupMessageController/addTransportVendor.do";
			var data = "t_code=" + transportCode + "&g_id=" + gId + "&log="
					+ log + "&leven=" + leven;

			if ($("#btn_type_text_logistics").text() == "确认修改") {
				data = data + "&logType=2&id=" + $("#vendorId").val();
			} else {
				data = data + "&logType=1"
			}
			$.ajax({
				type : "POST",
				url : url,
				data : data,
				dataType : "json",
				success : function(jsonStr) {
					alert(jsonStr.out_result_reason);
					if (jsonStr.out_result == 1) {
						queryLogistics(gId);
						$("#btn_back3").click();
					}else{
						queryLogistics(gId);
						//$("#btn_back3").click();
					}
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
						content1+= '>'　+　store[i].store_name +　'</option>';
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
						content1+= '>'　+　transport[i].transport_name +　'</option>';
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

        function downloadStore(){
        $.post('${root}/control/groupMessageController/downloadModelOfStore.do',function(data){
        		if(data.code==1){
        			 window.open('${root}/DownloadFile/store_model.xls');
        		}else{
        			
        			alert('操作失败！！！');
        		}
        		
        	});
        }
        
       function selectUploadModel(){
    	   
    	   $("#batch_store").modal('show');
       } 
       
       $(function () {
		    $("#upload").click(function () {
		    	//loadingStyle();
		    	
		        ajaxFileUpload();
		    });
	    
	    	$('input[id=file1]').change(function() { 
		        $('#photoCover').val($(this).val());
	        }); 
		})
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
       

	</script> 
</html>
