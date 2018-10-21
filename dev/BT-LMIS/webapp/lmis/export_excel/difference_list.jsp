<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
	<meta charset="UTF-8">
	<%@ include file="/lmis/yuriy.jsp"%>
	<title>LMIS</title>
	<meta name= "description" content= "overview &amp; stats" />
	<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
	<link type= "text/css" href= "<%=basePath %>daterangepicker/daterangepicker-bs3.css" rel="stylesheet" media="all" />
	<link type= "text/css" href= "<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet" />
	<link type= "text/css" href= "<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
	<link type= "text/css" href= "<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
	<link type= "text/css" href= "<%=basePath %>validator/css/style.css" rel= "stylesheet" media= "all" />
	<link type= "text/css" href= "<%=basePath %>validator/css/demo.css" rel= "stylesheet" />
	<link type= "text/css" href= "<%=basePath %>/css/table.css" rel= "stylesheet" />
	<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
	<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
	<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
	<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
	<script type= "text/javascript" src= "<%=basePath %>daterangepicker/daterangepicker.js"></script>
	<script type= "text/javascript" src= "<%=basePath %>daterangepicker/moment.js"></script>
	<script type= "text/javascript" src= "<%=basePath %>js/ajaxfileupload.js"></script>
	<script type= "text/javascript" src= "<%=basePath %>validator/js/Validform_v5.3.2_min.js"></script>
	<script type= "text/javascript" src= "<%=basePath %>js/common.js" ></script>
	<script type="text/javascript">
		$(function () {
		    $("#upload").click(function () {
		    	if(!confirm("确认导入？")) {
		    		return;
		    		
		    	}
		    	var year= $("#year").val();
		    	var month= $("#month").val();
		    	var mb= $("#mb").val();
		    	if(year != '' && month != '' && mb != ''){
			        ajaxFileUpload(year, month, mb);
			        
		    	}else{
		    		alert("必填参数为空!");
		    		
		    	}
		    	
		    });
	    	$('input[id=file1]').change(function() { 
		        $('#photoCover').val($(this).val());
		        
	        }); 
	    	
		})
		
		function pageJump() {
	      openDiv('${root}control/differenceController/list.do?startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	      
		}
		$(document).ready(function() {
			$("#reservation").daterangepicker(null, function(start, end, label) {
		    	console.log(start.toISOString(), end.toISOString(), label);
		    	
		   	});
			
		});
		function jump() {
			if (event.keyCode == 13) {
				var data=$("#main_search").serialize();
				openDivs("${root}/control/differenceController/list.do",data);
				
			}
			
		}
		function c_index(){
			$.ajax({
				type: "POST",
	           	url: root+"/control/differenceController/create_index.do",
	           	dataType: "text",
	           	data:  null,
	           	success: function (data) {
	           	}
	    	});
		}
		function ajaxFileUpload(year, month, b) {
			$.ajaxFileUpload({
				url: '/BT-LMIS/control/differenceController/input_excel.do?year='+year+"&month="+month+"&mb="+b, //用于文件上传的服务器端请求地址
	            secureuri: false, //是否需要安全协议，一般设置为false
				fileElementId: 'file1', //文件上传域的ID
				dataType: 'json', //返回值类型 一般设置为json
				//服务器成功响应处理函数
				success: function (data, status){
					alert(data.msg);
					if(data.code == 200) {
						openDiv('/BT-LMIS/control/differenceController/list.do?msg=' + data.msg);
						
	                }
					
				},error: function (data, status, e){
					//服务器响应失败处理函数
					openDiv('/BT-LMIS/control/differenceController/list.do');
					
	           	}
					
			})
	        return false;
			
		}
		function del() {
			var str= '';
	  		if($("input[name='ckb']:checked").length == 1) {
				var priv_ids= [];
			  	$("input[name='ckb']:checked").each(function(){
			  		// 将选中的值添加到数组priv_ids中
					priv_ids.push($.trim($(this).val()));
			  		
			  	});
		  	 	var result = confirm('是否删除！'); 
				if(result){
					$.ajax({
						type: "POST",
			           	url: root+"/control/differenceController/del.do?",
			           	dataType: "text",
			           	data: "privIds="+priv_ids,
			           	success: function (data) {
							if(data == 'true'){
								alert("删除成功!");
				      			openDiv('${root}/control/differenceController/list.do');
			        	   	}else if(data == 'false') {
			        			alert("删除失败!");
			        			
			        	   	}else{
			        			alert("删除异常!");	
			        	   	}
			           	}
			    	});		
			  	}
				
			} else {
				alert("请选择一行!");
				
			}
	  	}
		function differentMatching(id){
			if(!confirm("是否导出差异报表？")) {
				return;
				
			}
			loadingStyle();
			$.ajax({
				type: "POST",
	           	url: root + "/control/differenceController/differentMatching.do?",
	           	dataType: "json",
	           	data:  {id: id },
	         	success: function (result) {
         			// 解除旋转
		      		cancelLoadingStyle();
		      		if(result.result_code == "SUCCESS") {
		      			window.open(root + result.path);
		      			
		      		} else {
         				alert(result.result_content);
		         			
	         		}
				         
	         	},
	         	error: function (data) {
	       			// 解除旋转
		       		cancelLoadingStyle();
	          		alert("操作异常");
		          		
	          	}
			           	
  			})
			
		}
		function imp(id){
			openDiv('${root}/control/differenceController/imp_list.do?id='+id);
			
		}
	</script>
</head>
<body>
	<div class="page-header" align="left">
		<h1>差异对比</h1>
	</div>
	${msg}
	<div style="margin-top: 10px; margin-left: 10px; margin-bottom: 10px;">
		<form id="poiImport" name="poiImport" action="${root}control/differenceController/input_excel.do" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td style="width: 260px;">账单周期:
						<select id="year" name="year">
							<option value="2015">2015</option>
							<option value="2016">2016</option>
							<option value="2017">2017</option>
						</select>年
						<select id="month" name="month">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
						</select>月
					<td style="width: 260px;">
						快递模版:
						<select id="mb" name="mb" style="height: 23px;">
							<option value="1">SF</option>
							<option value="2">EMS</option>
							<option value="3">YTO</option>
							<option value="4">STO</option>
							<option value="5">时效导入</option>
						</select>
					</td>
					<td style="width: 260px;">
						<input id="file1" type="file" name='file' style='display:none'>  
						<input id="photoCover" class="input-large" type="text" style="height:20px;width: 170px;">  
	   					<a class="btn" onclick="$('input[id=file1]').click();">浏览</a> 
					</td>
					<td>
	    				<a  id='upload' class="btn"  href='javascript:void(0)'>导入</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<hr>
	<button class="btn btn-xs btn-inverse" onclick="del();">
		<i class="icon-trash"></i>删除
	</button>&nbsp;
	<!-- <button class="btn btn-xs btn-yellow" onclick="c_index();">
		<i class="icon-hdd"></i>创建索引
	</button>&nbsp; -->
	<div style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px; margin-top: 22px;">
		<table class="table table-striped">
			<thead align="center">
				<tr>
					<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')" /></td>
					<td>导入时间</td>
					<td>快递模版</td>
					<td>账单周期</td>
					<td>导入文件名</td>
					<td>系统批次号</td>
					<td>运单量</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody align="center">
				<tr>
					<td colspan="2">
		            	<div class="controls">
							<div class="input-prepend input-group">
								<span class="add-on input-group-addon">
									<i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span>
									<input type="text" readonly  style="width: 170px;" name="reservation" id="reservation" class="form-control" value="${sdate} - ${edate}" />
							</div>
						</div>
					</td>
					<td>
						<select id="template" name="template" onkeydown="jump();">
							<option value="1">SF</option>
							<option value="2">EMS</option>
							<option value="3">YTO</option>
							<option value="4">STO</option>
							<option value="5">时效导入</option>
						</select>
					</td>
					<td><input id="cycle" name="cycle" type="text" onkeydown="jump();" style="width: 70px;" /></td>
					<td><input id="file_name" name="file_name" type="text" onkeydown="jump();" /></td>
					<td><input id="batch_id" name="batch_id" type="text" onkeydown="jump();" /></td>
					<td><input id="count" name="count" type="text" onkeydown="jump();" style="width: 50px;" /></td>
					<td></td>
				</tr>
		  		<c:forEach items="${pageView.records}" var="pageView">
					<tr>
						<td><input id="ckb" name="ckb" type="checkbox" value="${pageView.id }"></td>
						<td><fmt:formatDate value="${pageView.input_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<td>
							<c:if test="${pageView.template==1}">SF</c:if>
							<c:if test="${pageView.template==2}">EMS</c:if>
							<c:if test="${pageView.template==3}">YTO</c:if>
							<c:if test="${pageView.template==4}">STO</c:if>
							<c:if test="${pageView.template==5}">时效导入</c:if>
						</td>
						<td>${pageView.cycle}</td>
						<td>
							<c:if test="${pageView.template==5}"><a href="javascript:void();" onclick="imp('${pageView.id}');">${pageView.file_name}</a></c:if>
							<c:if test="${pageView.template!=5}">${pageView.file_name}</c:if>
						</td>
						<td>${pageView.batch_id}</td>
						<td>${pageView.count}</td>
						<td>
							<button class= "btn btn-xs btn-primary" onclick= "differentMatching('${pageView.id }');" ><i class= "icon-exchange" ></i>差异报表</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div style="margin-right: 1%; margin-top: 20px;">${pageView.pageView}</div>
</body>
</html>
