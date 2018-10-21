<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file= "/yuriy.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<script type="text/javascript">
	var main_body = ${main_body};
	var skus=[];
	function upper_next(){
		$("#msg").html("");
		var sku = $("#sku").val();
		if(sku.indexOf("-") >= 0 ){
			$("#msg").html("SKU错误!");
			playMusic();
		}else{
			if(sku.length<18){
				if (sku == null || sku == undefined || sku == ''){
			    	$("#msg").html("SKU为空!");
			    	document.getElementById("sku").focus();
			    	playMusic();
			    }else{
			    	$("#sku").attr("disabled","disabled");
			    	var flag = false;
			    	for (var i in main_body){
						if(main_body[i].sku==sku){
							if(main_body[i].num!=0){
								main_body[i].num=main_body[i].num-1;
								flag=true;
							}
						}
			    	}
			    	if(!flag){
			    		$("#msg").html("SKU不存在!");
			    	}else{
			    		var flags = false;
				    	for (var i in skus){
							if(skus[i].sku==sku){
								flags=true;
								skus[i].num=skus[i].num+1;
							}
				    	}
				    	if(!flags){
				    		var ifsku = {"sku":sku,"num":1};
					    	skus.push(ifsku);
				    	}
			    		$("#bodys").val(JSON.stringify(skus));
			    		$("#upper_number").html(parseInt($("#upper_number").html())+1);
				    	$("#container_number").html(parseInt($("#container_number").html())-1);
				    	if($("#container_number").html()==0){
				    		$.ajax({
					 	           type: "POST",  
					 	           url: root+"control/newMainController/return_localhost_upper.do",  
					 	           //我们用text格式接收  
					 	           //json格式接收数据  
					 	           dataType: "json",  
					 	           data:  "main_body="+$("#bodys").val()+ "&opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val()+"&localhost_code="+$("#localhost_code").val(),
					 	           success: function (jsonStr) {
					 	        	   	if(jsonStr.code==201){
					 	        	   		window.location.href="${root}control/mainController/toSetContainer.do?opertion_type=2";
					 	     	   		}else if(jsonStr.code==200){
					 	     	   			
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
			    	}
			    	$("#sku").removeAttr("disabled");
		    		$("#sku").val("");
			    	document.getElementById("sku").focus();
			    }
			}else{
				$("#msg").html("长度不能超过18位!");
				playMusic();
			}
		}
	};
	function close_c(container_code){
		document.getElementById("backclo").disabled=true; 
		document.getElementById("backbt").disabled=true; 
		var a = confirm("还有未操作完的数据!");
		if(a){
			$.ajax({
		           type: "POST",  
		           url: root+"control/newMainController/close_c.do",  
		           //我们用text格式接收  
		           //json格式接收数据  
		           dataType: "json",  
		           data:  "main_body="+$("#bodys").val()+ "&opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val()+"&localhost_code="+$("#localhost_code").val(),
		           success: function (jsonStr) {
		        	   	if(jsonStr.code==200){
	 	        	   		window.location.href="${root}control/mainController/toSetContainer.do?opertion_type=2";
	        	   		}else if(jsonStr.code==500){
	        				$("#msg").html("系统错误!");
	        				playMusic();
	        	   		}
			    		$("#sku").val("");
			    		skus=[];
				    	document.getElementById("sku").focus();
						document.getElementById("backclo").disabled=false;
						document.getElementById("backbt").disabled=false;
		           }  
		    }); 
		}
	}
	function back(){
		document.getElementById("backclo").disabled=true; 
		document.getElementById("backbt").disabled=true;
		if($("#bodys").val()==''){
			window.location.href="${root}control/mainController/setContainer.do?opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val();
		}else{
			$.ajax({
		           type: "POST",  
		           url: root+"control/newMainController/return_localhost_upper.do",  
		           //我们用text格式接收  
		           //json格式接收数据  
		           dataType: "json",  
		           data:  "main_body="+$("#bodys").val()+ "&opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val()+"&localhost_code="+$("#localhost_code").val(),
		           success: function (jsonStr) {
		        	   	if(jsonStr.code==201){
		        	   		window.location.href="${root}control/mainController/toSetContainer.do?opertion_type=2";
		     	   		}else if(jsonStr.code==200){
		     	   			window.location.href="${root}control/mainController/setContainer.do?opertion_type="+$("#opertion_type").val()+"&container_code="+$("#container_code").val();
		     	   		}else{
		     	   			$("#msg").html(jsonStr.message);
		     	   			playMusic();
		     	   		}
			    		$("#sku").val("");
			    		skus=[];
				    	document.getElementById("sku").focus();
						document.getElementById("backclo").disabled=false;
						document.getElementById("backbt").disabled=false;
		           }  
		    });
		   	$("#sku").removeAttr("disabled");
	  		$("#sku").val("");
		   	document.getElementById("sku").focus();
		}
	}
	$(document).ready(function(){$('input[type=text]:first').focus();});

	$(function(){
		$("#sku").keydown(function(e){
		    if(e.keyCode==13) {
		    	upper_next();
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
				<td width="40%"><span style="font-size: 12px;">WMS|上架(逐件)</span><div id="play-frame-2"></div></td>
				<td><span style="font-size: 11px;">WelCome,[${session_employee.username }]&nbsp;&nbsp;|&nbsp;&nbsp;<a href="${root}control/userController/outLogin.do">注销</a></span></td>
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
				<td colspan="2"><span style="font-size: 10px;">已上架 (<span id="upper_number" >${upper_number}</span>) -容器内 (<span id="container_number" >${container_number}</span>)</span></td>
			</tr>
			<tr>
				<td colspan="2"><span style="font-size: 10px;">SKU:<input type="text" id="sku" name="sku" value="" /></span></td>
			</tr>
			<tr>
				<td colspan="2"><span id="msg" style="color: red;font-size: 10px;">${message}</span></td>
			</tr>
			<tr>
				<td colspan="2">
					<button data-theme="a" onclick="upper_next();">&nbsp;&nbsp;确&nbsp;&nbsp;认&nbsp;&nbsp;</button>&nbsp;&nbsp;
				  	<button id="backbt" name="backbt" data-theme="a" onclick="back();">&nbsp;&nbsp;返&nbsp;&nbsp;回&nbsp;&nbsp;</button>&nbsp;&nbsp;
				  	<button id="backclo" name="backclo"data-theme="a" onclick="close_c('${container_code}');">&nbsp;&nbsp;结&nbsp;&nbsp;束&nbsp;&nbsp;</button>
				  	<textarea id="bodys" name="bodys" style="display:none"></textarea>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
