<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<script src="http://code.jquery.com/jquery-1.4.1.min.js"type="text/javascript"></script>
	</head>
	
	<body>
		<div class="page-header">
			<h1>
				菜单编辑
			</h1>
		</div>
			<div class="col-xs-12">
			
				<c:if test="${empty menu.pid}">
					<form class="form-horizontal" id="menuform" role="form" action="${root}control/menuController/add.do">
				</c:if>
				
				<c:if test="${not empty menu.pid}">
					<form class="form-horizontal" id="menuform" role="form" action="${root}control/menuController/update.do">
				</c:if>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 菜单名称&nbsp;: </label>
						<div class="col-sm-9">
							<input type="hidden" id="id" name="id" value="${menu.id}" />
							<c:if test="${empty menu.pid}">
								<input type="hidden" id="pid" name="pid" value="${menuid}" />
							</c:if>
							<c:if test="${not empty menu.pid}">
								<input type="hidden" id="pid" name="pid" value="${menu.pid}" />
							</c:if>
							<input id="name" name="name" type="text" id="form-field-1" placeholder="如：菜单名称" class="col-xs-10 col-sm-5"  value="${menu.name }"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> URL&nbsp;: </label>
						<div class="col-sm-9">
							<input id="url" name="url" type="text" id="form-field-1" placeholder="如：index/list.do" class="col-xs-10 col-sm-5" value="${menu.url }"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> ICON&nbsp;: </label>
						<div class="col-sm-9">
							<input id="icon" name="icon" type="text" id="form-field-1" placeholder="如：icon-help" value="${menu.icon }" class="col-xs-10 col-sm-5" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 排序&nbsp;: </label>
						<div class="col-sm-9">
							<input id="sort" name="sort" type="text" id="form-field-1" placeholder="如：100" class="col-xs-10 col-sm-5" value="${menu.sort }" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 备注&nbsp;: </label>
						<div class="col-sm-9">
							<input id="remarks" name="remarks" type="text" id="form-field-1" placeholder="如：详细描述" class="col-xs-10 col-sm-5" value="${menu.remarks }"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1">  </label>
						<div class="col-xs-3">
							<div class="radio">
								<label>
									<input id="node" name="node" name="form-field-radio" <c:if test="${menu.node==0 }">checked="checked" </c:if> type="radio" class="ace" value="0"  />
									<span class="lbl"> 根节点</span>
								</label>
								<label>
									<input  id="node" name="node"   name="form-field-radio" <c:if test="${menu.node==1 }">checked="checked" </c:if> type="radio" class="ace" value="1" />
									<span class="lbl"> 页面</span>
								</label>
							</div>
						</div>
					</div>
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<c:if test="${empty menu.pid}">
								<button class="btn btn-info" type="button" id="subins" name="subins">
									<i class="icon-ok bigger-110"></i>
									提交
								</button>
							</c:if>
							<c:if test="${not empty menu.pid}">
								<button class="btn btn-info" type="button" id="subup" name="subup">
									<i class="icon-ok bigger-110"></i>
									提交
								</button>
							</c:if>
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset" onclick="openDiv('${root}control/menuController/toList.do?toid='+${menuid});">
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
	var reqtimes=0;
	$("#subins").click(function(){
		   /* $.ajax({
			   	url : root+"/control/menuController/add.do?name="+$("#name").val()+"&pid="+$("#pid").val()+"&node="+$("#node").val(),
				data : null,
				type : "post",
				success : function(data) {
					alert();
				},error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert(XMLHttpRequest.status);
					alert(XMLHttpRequest.readyState);
					alert(XMLHttpRequest.errorThrown);
					alert(textStatus);
				}
			}); */
			
			if ($('#name').val() == '') {
				alert("菜单名称不能为空");	
				return;
			}
			
			if ($('#url').val() == '') {
				alert("URL不能为空");
				return;
			}


			if (!confirm("确认要提交么？")) {
				return;				
			}
			
			if (reqtimes!=0) {
				alert("表单不能重复提交，请刷新页面");
				return;
			}
			
			reqtimes++;
			
			$.ajax({  
		           type: "POST",  
		           url: root+"/control/menuController/add.do?",  
		           //我们用text格式接收  
		           dataType: "text",   
		           //json格式接收数据  
		           //dataType: "json",  
		           data:  "name="+$('#name').val()+"&pid="+$('#pid').val()+"&node="+$('#node:checked').val()+"&sort="+$('#sort').val()+"&icon="+$('#icon').val()+"&remarks="+$('#remarks').val()+"&url="+$('#url').val(),
		           success: function (jsonStr) {  
		               //实例2个字符串变量来拼接下拉列表  
		               alert(jsonStr);  
		               //使用jquery解析json中的数据  
		               /* var ruleListTemp = "<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";  
		                 
		                $.each(jsonStr, function (n, value) {  
		                 //alert(value.ruleId);  
		                   ruleListTemp += ("<tr><td>" + value.ruleName);  
		                   ruleListTemp += ("</td></tr>");   
		               });  
		                ruleListTemp += ("</table>");  
		  
		               $("#ruleList").html(ruleListTemp); */   
		           }  
		    }); 
		});
	$("#subup").click(function(){
		
		if ($('#name').val() == '') {
			alert("菜单名称不能为空");	
			return;
		}
		
		if ($('#url').val() == '') {
			alert("URL不能为空");
			return;
		}

		if (!confirm("确认要提交么？") && reqtimes!=0) {
			return;				
		}
		
		reqtimes++;
		
		$.ajax({  
	           type: "POST",  
	           url: root+"/control/menuController/update.do?",  
	           //我们用text格式接收  
	           dataType: "text",   
	           data:  "id="+$('#id').val()+"&name="+$('#name').val()+"&pid="+$('#pid').val()+"&node="+$('#node:checked').val()+"&sort="+$('#sort').val()+"&icon="+$('#icon').val()+"&remarks="+$('#remarks').val()+"&url="+$('#url').val(),
	           success: function (jsonStr) {  
	               //实例2个字符串变量来拼接下拉列表  
	               alert(jsonStr);  
	           }  
	    }); 
	});
	</script>
</html>
