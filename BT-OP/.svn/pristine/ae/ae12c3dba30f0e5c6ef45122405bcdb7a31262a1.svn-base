<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file= "/base/base.jsp" %>
		<title>OP</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	    <link rel="stylesheet" href="<%=basePath %>assets/css/tree.css" type="text/css">
	    <link rel="stylesheet" href="<%=basePath %>assets/css/metroStyle/metroStyle.css" type="text/css">
	    <script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/jquery-1.8.3.min.js"></script>
	    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.excheck.js"></script>
	    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.exedit.js"></script>
	    <script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
	</head>
	
	<body>
		<div class="page-header">
			<h1>
				省市区县节点修改
			</h1>
		</div>
			<div class="col-xs-12">
			
					<form class="form-horizontal" id="menuform" role="form" action="${root}control/lmis/areaController/operate.do">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 节点名称&nbsp;: </label>
						<div class="col-sm-9">
							<input type="hidden" id="id" name="id" value="${role.id}" />
							<input id='name' name="name" type="text" id="form-field-1" placeholder="" class="col-xs-10 col-sm-5"  value="${area.area_name}"  />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 节点编码&nbsp;: </label>
						<div class="col-sm-9">
							<input id="code"  type="text" id="form-field-1" placeholder="" class="col-xs-10 col-sm-5" value="${area.area_code}"/>
						</div>
					</div>
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							
								<button class="btn btn-info" type="button" id="subup" name="subup">
									<i class="icon-ok bigger-110"></i>
									提交
								</button>
							
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset" onclick="loadingCenterPanel('${root}control/orderPlatform/areaController/toForm.do');">
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

	
	
		$("#subup").click(function(){
			$.ajax({  
		           type: "POST",  
		           url: root+"/control/orderPlatform/areaController/operate.do?",  
		           //我们用text格式接收  
		           dataType: "text",   
		           data:  "operationType=2&area_name="+$('#name').val()+"&area_code="+$('#code').val()+"&id="+'${area.id}',
		           success: function (jsonStr) {  
		        	   var obj = $.parseJSON(jsonStr);
		        	   if(obj.code=='1'){
		            	   alert("操作成功");
		               }else{
		            	   alert("操作失败！")
		               }
		        	   loadingCenterPanel('/BT-OP//control/orderPlatform/areaController/toForm.do');
		           }  
		    }); 
		});
	</script>
</html>
