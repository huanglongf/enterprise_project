<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
	    <link rel="stylesheet" href="<%=basePath %>/css/tree.css" type="text/css">
	    <link rel="stylesheet" href="<%=basePath %>/css/metroStyle/metroStyle.css" type="text/css">
	    <script type="text/javascript" src="<%=basePath %>/js/jquery-1.4.4.min.js"></script>
	    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.excheck.js"></script>
	    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.exedit.js"></script>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>
	</head>
	
	<body>
		<div class="page-header">
			<h1>
				权限编辑
			</h1>
		</div>
			<div class="col-xs-12">
			
				<c:if test="${empty role.id}">
					<form class="form-horizontal" id="roleform" role="form" action="${root}control/powerController/add.do">
				</c:if>
				
				<c:if test="${not empty role.id}">
					<form class="form-horizontal" id="menuform" role="form" action="${root}control/powerController/update.do">
				</c:if>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 权限名称&nbsp;: </label>
						<div class="col-sm-9">
							<input type="hidden" id="id" name="id" value="${power.id}" />
							<input id="name" name="name" type="text" id="form-field-1" placeholder="如：权限名称" class="col-xs-10 col-sm-5"  value="${power.name}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 备注&nbsp;: </label>
						<div class="col-sm-9">
							<input id="remark" name="remark" type="text" id="form-field-1" placeholder="如：详细描述" class="col-xs-10 col-sm-5" value="${power.remark}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 排序&nbsp;: </label>
						<div class="col-sm-9">
							<input id="sort" name="sort" type="text" id="form-field-1" placeholder="如：2001" class="col-xs-10 col-sm-5" value="${power.sort}"/>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 菜单&nbsp;: </label>
						<div class="col-sm-9">
							<div class="content_wrap">
								<input type="hidden" id="mid" name="mid" value="${mid}" />
							    <div class="zTreeDemoBackground left">
									<ul id="treeDemo" class="ztree"></ul>
							    </div>
							</div>
						</div>
					</div>
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<c:if test="${empty power.id}">
								<button class="btn btn-info" type="button" id="subins" name="subins">
									<i class="icon-ok bigger-110"></i>
									提交
								</button>
							</c:if>
							<c:if test="${not empty power.id}">
								<button class="btn btn-info" type="button" id="subup" name="subup">
									<i class="icon-ok bigger-110"></i>
									提交
								</button>
							</c:if>
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset" onclick="openDiv('${root}control/powerController/powerList.do');">
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
		var setting = {
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "level"
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		var zNodes =${menu};
		 		
		var code;		
 		function setCheck() {
 			var type = $("#level").attr("checked")? "level":"all";
 			setting.check.radioType = type;
 			showCode('setting.check.radioType = "' + type + '";');
 			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
 		}
 		function showCode(str) {
 			if (!code) code = $("#code");
 			code.empty();
 			code.append("<li>"+str+"</li>");
 		}
 		
	$(document).ready(function(){
		setCheck();			
		$("#level").bind("change", setCheck);
		$("#all").bind("change", setCheck);
	});

	var reqtimes=0;
	$("#subins").click(function(){
		
		if ($('#name').val() == '') {
			alert("请填写权限名称");
			return;
		}
		
		if (confirm("确认提交吗？") && reqtimes==0) {
			reqtimes++;
			$.ajax({  
	           type: "POST",  
	           url: root+"control/powerController/add.do?",  
	           //我们用text格式接收  
	           dataType: "text",   
	           //json格式接收数据  
	           //dataType: "json",  
	      	   data:  "name="+$('#name').val()+"&remarks="+$('#remark').val()+"&menuid="+getTreeIds(),
	           success: function (jsonStr) {  
	               alert(jsonStr);  
	           }  
		    }); 
		}
	});
	$("#subup").click(function(){
		
		if ($('#name').val() == '') {
			alert("请填写权限名称");
			return;
		}
		
		if (confirm("确认提交吗？")) {
			
			if (reqtimes!=0) {
				alert("表单不能重复提交，请刷新页面");
				return;
			}
			
			reqtimes++;
			$.ajax({  
		           type: "POST",  
		           url: root+"control/powerController/update.do?",  
		           //我们用text格式接收  
		           dataType: "text",   
		           data:  "id="+$('#id').val()+"&name="+$('#name').val()+"&remarks="+$('#remark').val()+"&menuid="+getTreeIds(),
		           success: function (jsonStr) {  
		               alert(jsonStr);  
		           }  
		    }); 
		}
	});

	function getTreeIds(){
		var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
        nodes=treeObj.getCheckedNodes(true),
        v="";
        for(var i=0;i<nodes.length;i++){
        	if(i+1==nodes.length){
        		v+=nodes[i].id
        	}else{
        		v+=nodes[i].id + ",";
        	}
        	
        }
        return v;
	}
	
	

	</script>
</html>
