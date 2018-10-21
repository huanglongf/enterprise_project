<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file= "/lmis/yuriy.jsp" %>
<link type= "text/css" href= "<%=basePath %>uploadify/uploadify.css" rel= "stylesheet" >
<script type= "text/javascript" src= "<%=basePath %>uploadify/jquery.uploadify.js" ></script>
<script type= "text/javascript" src= "<%=basePath %>uploadify/JQueryUploadHelper.js" ></script>
<script type= "text/javascript" >
	JQueryUploadHelper.SESSIONID = "<%=session.getId()%>";
	$(document).ready(JQueryUploadHelper.init);
	$(function(){
		if($("#judgeType").val() == "process" && $("#woProcessStatus_woDisplay").text() == "处理中" && $("#waybillGenerated").children().length==0) {
			$("#btn_op").show();
			
		} else {
			if($("#btn_op").css("display")!="none") {
				$("#btn_op").hide();
				
			}
			
		}
		
	})
</script>
<style>
	.button{
		display:block;
		width: 120px;
		height: 34px;	
		border-width:1px;
		background-color: #e5e5e5;
		color: black;
		border: 1px solid white;
		-moz-border-radius: 15px;      /* Gecko browsers */
		-webkit-border-radius: 15px;   /* Webkit browsers */
		border-radius:15px;            /* W3C syntax */
		line-height: 34px;
		text-decoration: none;
	}
	#fileDiv a:hover{
  		text-decoration:none;
	}
</style>		
<input id= "fileName_common" type= "hidden" />		
<div id= "fileDiv" style= "width: 100%; height: 150px; overflow: auto; border: 1px solid #e5e5e5;" >
	<div id= "fileQueue" style= "width:100%;" ></div>	
	<ims></ims>
</div>
<div id= "btn_op" style= "display: none;" >
	<input id= "myFile" name= "myFile" type= "file" multiple= "true" onselect= "" />
	<hr>
   	<!-- <button onclick= "javascript:$('#myFile').uploadify('cancel',$('.uploadifive-queue-item').first().data('file'))" class= "button" style= "float: right" >取消上传</button>
  	<button onclick= "javascript:$('#myFile').uploadify('upload','*')" class= "button" style= "float: right" >上传</button> -->
</div>