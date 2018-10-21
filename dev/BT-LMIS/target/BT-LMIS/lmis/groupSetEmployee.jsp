<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	    <link rel="stylesheet" href="<%=basePath %>/css/tree.css" type="text/css">
	    <link rel="stylesheet" href="<%=basePath %>/css/metroStyle/metroStyle.css" type="text/css">
	    <script type="text/javascript" src="<%=basePath %>/js/jquery-1.4.4.min.js"></script>
	    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.excheck.js"></script>
	    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.exedit.js"></script>
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
	</head>
	
	<body>
		<div class="page-header">
			<h1>
				绑定用户
			</h1>
		</div>
			<div class="col-xs-12">
				<form class="form-horizontal" id="menuform" role="form" action="${root}control/roleController/update.do">
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 组别名称&nbsp;: </label>
						<div class="col-sm-9" style="margin-top: 5px;">
							${group.group_code}
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 组别编码&nbsp;: </label>
						<div class="col-sm-9" style="margin-top: 5px;">
							${group.group_name}
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 说明&nbsp;: </label>
						<div class="col-sm-9" style="margin-top: 5px;">
							${group.instruction}
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 用户选择&nbsp;: <input type="hidden" value="${groupid}" id="groupid" name="groupid" /></label>
						<div class="col-sm-9">
							<div class="content_wrap">
							    <div class="zTreeDemoBackground left">
							        <ul id="menuTree" class="ztree"></ul>
							    </div>
							</div>
						</div>
					</div>
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="button" id="subins" name="subins">
								<i class="icon-ok bigger-110"></i>提交
							</button>
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset" onclick="openDiv('${root}control/groupMessageController/listGroup.do');">
				 				<i class="icon-undo bigger-110"></i>返回
							</button>
						</div>
					</div>
				</form>
			</div>
		<div class="space-4"></div>
	</body>
	<script type="text/javascript">
		var setting = {
           view: {
               addHoverDom: false,
               removeHoverDom: false,
               selectedMulti: false
           },
           check: {
               enable: true
           },
           data: {
               simpleData: {
                   enable: true
               }
           },
           edit: {
               enable: false
           }
		};
		//树_数据
   		var zNodes =${menu};
		$(document).ready(function(){
			$.fn.zTree.init($("#menuTree"), setting, zNodes);
	   	});
		function getTreeIds(){
			var treeObj=$.fn.zTree.getZTreeObj("menuTree"),
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
		$("#subins").click(function(){
			$.ajax({  
		           type: "POST",  
		           url: root+"/control/groupMessageController/setEMP.do?",  
		           //我们用text格式接收  
		           dataType: "text",   
		           data:  "ids="+getTreeIds()+"&groupid="+$('#groupid').val(),
		           success: function (jsonStr) {  
		               alert(jsonStr);  
		           }  
		    }); 
		});
	</script>
</html>
