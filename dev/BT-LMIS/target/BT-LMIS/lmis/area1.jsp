<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	</head>
	<link rel="stylesheet" href="<%=basePath %>/css/tree.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath %>/css/metroStyle/metroStyle.css" type="text/css">
<!--	<link href="<%=basePath %>assets/css/bootstrap.min.css" rel="stylesheet"> 
	<link rel="stylesheet" type="text/css" href="<%=basePath %>assets/css/default.css">-->
	<body>
		<div class="page-header">
		</div>
          <div class="form-group">
						<div class="col-sm-9">
							<div class="content_wrap">
		  &nbsp;&nbsp;<button id='add' class='btn btn-warning' style='margin-top:50px'>新增</button>&nbsp;
          <button  id='update' class='btn btn-primary' style='margin-top:50px'>修改</button>&nbsp; 
							    <div class="zTreeDemoBackground right">
							        <ul id="menuTree" class="ztree"></ul>
							    </div>
							<!--     <form style='float:right'>
							   区域代码      <input type='text' />
							   区域名称        <input type='text' />
							 上一个节点     <input type='text' />
							    </form> --> 
							</div>
						</div>
					</div>
         <script type="text/javascript" src="<%=basePath %>/js/jquery-1.4.4.min.js"></script>
	    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.core.js"></script>
	    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.excheck.js"></script>
	    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.exedit.js"></script>
        <script type="text/javascript">
        var gId='';
        var gName='';
        //var treeObj = $.fn.zTree.getZTreeObj("tree");
        //var nodes = treeObj.getCheckedNodes(true);
    	function zTreeOnClick(event, treeId, treeNode) {
    	  //  alert(treeNode.tId + ", " + treeNode.name);
    	  gId=treeNode.id;
    	  gName=treeNode.name;
    	};
        
        
	  		$(function() {

	  			var setting = {
	  		           view: {
	  		               addHoverDom: false,
	  		               removeHoverDom: false,
	  		               selectedMulti: false
	  		           },
	  		           check: {
	  		               enable: false
	  		           },
	  		           data: {
	  		               simpleData: {
	  		                   enable: true
	  		               }
	  		           },
	  		           edit: {
	  		               enable: false
	  		           }
	  		         ,
	  	           callback: {
	  	       		onClick: zTreeOnClick
	  	       	}
	  				};
	  			
  		var zNodes =${menu};
		$(document).ready(function(){
			$.fn.zTree.init($("#menuTree"), setting, zNodes);
	   	});

        
	  		});
	  		
	  		function getTreeId(){
				var treeObj=$.fn.zTree.getZTreeObj("menuTree"),
		        nodes=treeObj.getCheckedNodes(true),
		        v="";
				if(nodes.length>1){
					alert("您只能选择一个节点进行操作！！");
					return v;
				}else if(nodes.length>1){
					alert("请选择一个节点进行操作！！");
					return v;
				}
		        v=node[0].id;
		        return v;
			}
	  		$("#add").click(function(){
			
				if(gId==''){alert("请选择一项作为你要添加节点的父节点！");return;}
				openDiv(root+"/control/lmis/areaController/findOneaddArea_form1.do?id="+gId);
	  			 
			});
			$("#update").click(function(){
				if(gId==''){alert("请选择一项！");return;}
				openDiv(root+"/control/lmis/areaController/findOneupdateArea_form1.do?id="+gId);
	  			 	
			});
			
			$("#delete").click(function(){
				if(gId==''){
					alert("请选择一项！"); return ;
				}
				
				if(window.confirm('您确定要删除 "'+gName+'" 此节点吗？')){
					 $.ajax({  
				           type: "POST",  
				           url: root+"/control/lmis/areaController/operate.do?",  
				           //我们用text格式接收  
				           dataType: "text",   
				           data:  "operationType=0&id="+gId,
				           success: function (jsonStr) {  
				        	   var obj = $.parseJSON(jsonStr);
				        	   if(obj.code=='1'){
				            	   alert("操作成功");
				               }else{
				            	   alert("操作失败！")
				               }
				               openDiv('/BT-LMIS//control/lmis/areaController/toForm1.do');;
				           }  
				    }); 
				}
				/* */
			});
        </script>
        

    </body>
</html>
<style>

ul.ztree {
    margin-top: 10px;
    border: 1px solid #617775;
    background: #f0f6e4;
    width: 300px;
    height: 400px;
    overflow-y: scroll;
    overflow-x: auto;
}

</style>