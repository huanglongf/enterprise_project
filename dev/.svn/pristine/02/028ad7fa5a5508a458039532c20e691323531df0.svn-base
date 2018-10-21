<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file= "/yuriy.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<script type="text/javascript">
	var skus=[];
	function lower_next(){
		$("#msg").html("");
		var sku = $("#sku").val();
		if(sku.indexOf("-") >= 0 ){
			$("#msg").html("SKU错误!");
			playMusic();
		}else{
			if(sku.length<18){
				if (sku == null || sku == undefined || sku == ''){
			    	$("#msg").html("库位号为空!");
			    	document.getElementById("sku").focus();
			    	playMusic();
			    }else{
			    	$("#sku").attr("disabled","disabled");
					$("#sku").val("");
			    	$("#sku").removeAttr("disabled");
					var flag = false;
			    	for (var i in skus){
						if(skus[i].sku==sku){
							flag=true;
							skus[i].num=skus[i].num+1;
						}
			    	}
			    	if(!flag){
			    		var ifsku = {"sku":sku,"num":1};
				    	skus.push(ifsku);
			    	}
			    	$("#container_number").html(parseInt($("#container_number").html())+1);
			    	$("#bodys").val(JSON.stringify(skus));
			    }
			}else{
				$("#msg").html("长度不能超过18位!");
				playMusic();
			}
	    	document.getElementById("sku").focus();
		}
	};
	
	function back(){
		document.getElementById("backbt").disabled=true; 
		document.getElementById("closebt").disabled=true; 
		$.ajax({
	           type: "POST",  
	           url: root+"control/newMainController/return_localhost.do",  
	           //我们用text格式接收  
	           //json格式接收数据  
	           dataType: "json",  
	           data:  "main_body="+$("#bodys").val()+ "&opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val()+"&localhost_code="+$("#localhost_code").val(),
	           success: function (jsonStr) {
	        	   	if(jsonStr.code==200){
	        	   		window.location.href="${root}control/mainController/setContainer.do?opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val();
	     	   		}else{
	     	   			$("#msg").html(jsonStr.message);
	     	   			playMusic();
	     	   		}
		    		$("#sku").val("");
		    		skus=[];
			    	document.getElementById("sku").focus();
	           }  
	    });
		document.getElementById("backbt").disabled=false; 
		document.getElementById("closebt").disabled=false; 
	}
	function lower_close(){
		$.ajax({
	           type: "POST",  
	           url: root+"control/newMainController/return_localhost.do",  
	           //我们用text格式接收  
	           //json格式接收数据  
	           dataType: "json",  
	           data:  "main_body="+$("#bodys").val()+ "&opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val()+"&localhost_code="+$("#localhost_code").val(),
	           success: function (jsonStr) {
	        	   	if(jsonStr.code==200){
	        	   		window.location.href="${root}control/mainController/toSetContainer.do?opertion_type=1&close=close";
	     	   		}else{
	     	   			$("#msg").html(jsonStr.message);
	     	   			playMusic();
	     	   		}
		    		$("#sku").val("");
		    		skus=[];
			    	document.getElementById("sku").focus();
	           }  
	    });
	}
	$(document).ready(function(){$('input[type=text]:first').focus();});

	$(function(){
		$("#sku").keydown(function(e){
		    if(e.keyCode==13) {
		    	lower_next();
		    }
		});
		if($("#msg").html()!=''){
			playMusic();
		}
	});
	function playMusic(){
		var box = document.getElementById('play-frame-2');
		var str = "<embed allowFullScreen='true' id='embedid' hidden='true' quality='high'  src='<%=basePath %>2.wav' ></embed>";
		box.innerHTML = str;
	};
</script>
<body>
	<div align="center">
		<table style="width: 280px;">
			<tr>
				<td width="40%"><span style="font-size: 12px;">WMS|下架(逐件)</span><div id="play-frame-2"></div></td>
				<td><span style="font-size: 11px;">WelCome,[${session_employee.username }]&nbsp;&nbsp;|&nbsp;&nbsp;<a href="${root}control/userController/outLogin.do" >注销</a></span></td>
			</tr>
			<tr>
				<td colspan="2">
					<span style="font-size: 12px;">容器号:[${container_code}]</span><br>
					<span style="font-size: 12px;">货位号:[${localhost_code}]</span>
					<input type="hidden" id="opertion_type" name="opertion_type" value="${opertion_type}" />
					<input type="hidden" id="container_code" name="container_code" value="${container_code}" />
					<input type="hidden" id="localhost_code" name="localhost_code" value="${localhost_code}" />
				</td>
			</tr>
			<tr>
				<td colspan="2" height="5px;"><span style="font-size: 10px;">容器内 (<span id="container_number">${container_number}</span>)</span></td>
			</tr>
			<tr>
				<td colspan="2"><span style="font-size: 10px;">SKU:<input type="text" id="sku" name="sku" value="" /></span></td>
			</tr>
			<tr>
				<td colspan="2"><span id="msg" style="color: red;font-size: 10px;">${message}</span></td>
			</tr>
			<tr>
				<td colspan="2">
					<button data-theme="a" onclick="lower_next();">&nbsp;&nbsp;确&nbsp;&nbsp;认&nbsp;&nbsp;</button>&nbsp;&nbsp;
				  	<button id="backbt" name="backbt" data-theme="a" onclick="back();">&nbsp;&nbsp;返&nbsp;&nbsp;回&nbsp;&nbsp;</button>&nbsp;&nbsp;
				  	<button id="closebt" name="closebt" data-theme="a" onclick="lower_close();">&nbsp;&nbsp;结束容器&nbsp;&nbsp;</button>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<textarea id="bodys" name="bodys" rows="" cols="" style="width: 200px;height: 40px;display:none"></textarea>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
