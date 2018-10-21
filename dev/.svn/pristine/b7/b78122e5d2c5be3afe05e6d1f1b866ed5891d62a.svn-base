<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- 		<script src="http://code.jquery.com/jquery-1.4.1.min.js"type="text/javascript"></script> -->
<%-- 		<script src="<%=basePath %>/assets/js/bootstrap.min.js?v=<%=new Date().getTime()%>"></script> --%>
<%-- 		<script src="<%=basePath %>/assets/js/fuelux/fuelux.spinner.min.js?v=<%=new Date().getTime()%>"></script> --%>
<%-- 		<script src="<%=basePath %>/assets/js/ace-elements.min.js?v=<%=new Date().getTime()%>"></script> --%>
<%-- 		<script src="<%=basePath %>/assets/js/ace.min.js"></script>		 --%>
	</head>
	
	<body>
		<div class="page-header">
			<h4>
				<a href="javascript:;" onclick="back()">合同管理</a>>>页面参数设置
			</h4>
		</div>
		    
			<div style="width: 47%;float: left;margin-left: 1%;overflow: auto; overflow:scroll; height: 600px;">
			   <h4 class="red">所有操作列：</h4>
			     <table class='with-border' border='1' >
					   <tr class='title'>
					     <td>字段名</td>
					     <td>操作</td>
					   </tr>			     
					<c:forEach items="${all_param}" var="all">
					   
					
					   <tr>
					     <td>${all.column_comment}</td>
					     <td width="50%">
					     <button class="btn btn-xs btn-primary" onclick="addParam('1','${all.column_name}','${all.column_comment}','${all.data_type}');">
				          <i class="icon-edit"></i>添加为显示列
			             </button>&nbsp;
			             
						<button class="btn btn-xs btn-pink" onclick="addParam('2','${all.column_name}','${all.column_comment}','${all.data_type}');">
							<i class="icon-search icon-on-right bigger-110"></i>添加为搜索项
						</button>&nbsp;	
						</td>		             
					   </tr>
					</c:forEach>
				 </table>
			</div>
			
			<div style="width: 47%;float:right;margin-right: 1%;overflow: auto; overflow:scroll; height: 300px;">
			     <h4 class="red">显示列：</h4>
			     <table class='with-border' border='1' id="showRow">
					   <tr class='title'>
					     <td>字段名</td>
					     <td>属性</td>
					     <td>显示次序</td>
					     <td>操作</td>
					   </tr>			     
					<c:forEach items="${current_param}" var="curent" varStatus="status">
					   <tr>
					     <td>${curent.field_value}</td>
					     
					      <td>${curent.field_type_name}</td>
					       <td>
					         <img alt="" src="<%=basePath %>assets/images/1.png" width="23" height="23" style="margin-bottom: 11px;" onclick="up_param2('${curent.id}','1')">
					      	第<input type="text" readonly="readonly" style="width: 60px;text-align: center;" id="spq_${curent.id}" value="${curent.weight}"/>列
							<img alt="" src="<%=basePath %>assets/images/2.png" width="23" height="23" style="margin-bottom: 11px;" onclick="up_param2('${curent.id}','2')">
					       </td>
					     <td>
					     <button class="btn btn-xs btn-primary" onclick="delParam('${curent.id}');">
				          <i class="icon-edit"></i>删除
			             </button>&nbsp;
					     </td>
					   </tr>
					</c:forEach>
				 </table>
			</div>

                <div style="width: 47%;float:right;margin-right: 1%;overflow: auto; overflow:scroll; height: 300px;">
			     <h4 class="red">搜索条件：</h4>
			     <table class='with-border' border='1' id="searchRow">
					   <tr class='title'>
					     <td>字段名</td>
					     <td>属性</td>
					     <td>显示次序</td>
					     <td>操作</td>
					   </tr>			     
					<c:forEach items="${current_param_search}" var="curent" varStatus="status">
					   <tr>
					     <td>${curent.field_value}</td>
					     
					      <td>${curent.field_type_name}</td>
					       <td>
					        <img alt="" src="<%=basePath %>assets/images/1.png" width="23" height="23" style="margin-bottom: 11px;" onclick="up_param('${curent.id}','1')">
					      	第<input type="text" readonly="readonly" style="width: 60px;text-align: center;" id="sp_${curent.id}" value="${curent.weight}"/>列
							<img alt="" src="<%=basePath %>assets/images/2.png" width="23" height="23" style="margin-bottom: 11px;" onclick="up_param('${curent.id}','2')">
					       </td>
					     <td>
					     <button class="btn btn-xs btn-primary" onclick="delParam('${curent.id}');">
				          <i class="icon-edit"></i>删除
			             </button>&nbsp;
					     </td>
					   </tr>
					</c:forEach>
				 </table>
			</div>
									
		<div class="space-4"></div>
	</body>
	<script type="text/javascript">
       function addParam(type,column_name,comment,data_type){
    	   var url=root+"/control/contractController/addParam.do";
    	   var data="field_type="+type+"&column_name="+column_name+"&comment="+comment+"&data_type="+data_type;
    	   $.ajax({
   			type : "POST",
   			url: url,  
   			data:data,
   			dataType: "json",  
   			success : function(jsonStr) {
   			alert(jsonStr.result_reason);
   			if(jsonStr.out_result=="1"){
   				openDiv('${root}control/contractController/toSetPage.do?page_id=1');
   			}
   			}
   		});
       }
 
       function delParam(id){
    	   if(!confirm("是否删除以下所选数据?")){
    		  	return;
    		  	
    		}
    	   var url=root+"/control/contractController/delParam.do?id="+id;
    	   $.ajax({
   			type : "POST",
   			url: url,  
   			data:"",
   			dataType: "json",  
   			success : function(jsonStr) {
   			alert(jsonStr.result_reason);
   			if(jsonStr.out_result=="1"){
   				toSetPage();
   			}
   			}
   		});
       }
	function up_param(id,type){
		var table =document.getElementById("searchRow");
		var rows = table.rows.length;
		if(type=='1'){
			if(parseInt($("#sp_"+id).val())==(parseInt(rows)-1)){
				return;
			}
			  $("#sp_"+id).val(parseInt($("#sp_"+id).val())+1);
		}else{
			if(parseInt($("#sp_"+id).val())==1){
				  $("#sp_"+id).val(1);
 			}
			else{
			  $("#sp_"+id).val(parseInt($("#sp_"+id).val())-1);
			}
		}
	
 	   var url=root+"/control/contractController/upParam.do?id="+id+"&value="+$("#sp_"+id).val();
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
			if(jsonStr.out_result=="1"){
// 				toSetPage();
			}
			}
		});		
	}

	function up_param2(id,type){
		var table =document.getElementById("showRow");
		var rows2 = table.rows.length;
		if(type=='1'){
			if(parseInt($("#spq_"+id).val())==(parseInt(rows2)-1)){
				return;
			}
			  $("#spq_"+id).val(parseInt($("#spq_"+id).val())+1);
		}else{
			if(parseInt($("#spq_"+id).val())==1){
				  $("#spq_"+id).val(1);
			}else{
			  $("#spq_"+id).val(parseInt($("#spq_"+id).val())-1);
			}
		}
	
 	   var url=root+"/control/contractController/upParam.do?id="+id+"&value="+$("#spq_"+id).val();
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
			if(jsonStr.out_result=="1"){
// 				toSetPage();
			}
			}
		});		
	}
	
	
// 	jQuery(function($) {
// 		var s=$('[id^=spinner]').length;
// 		for(i=0;i<s;i++){
// 		var values=parseInt($("#sp_value_"+i).val());
// // 		var id=$("#sp_id_"+i).val();
		
// 		$('#spinner_'+i).ace_spinner({value:values,min:0,max:200,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'})
// 		.on('change', function(){
// 			var ids=this.id;
//  			up_param($("#sp_id_"+ids.split("_")[1]).val(),this.value);
// 		});
// 		}
// 	});	
	
// 	jQuery(function($) {
// 		var s=$('[id^=spinners]').length;
// 		for(i=0;i<s;i++){
// 		var values=parseInt($("#sp_values_"+i).val());
// // 		var id=$("#sp_id_"+i).val();
		
// 		$('#spinners_'+i).ace_spinner({value:values,min:0,max:200,step:1, btn_up_class:'btn-info' , btn_down_class:'btn-info'})
// 		.on('change', function(){
// 			var ids=this.id;
//  			up_param($("#sp_ids_"+ids.split("_")[1]).val(),this.value);
// 		});
// 		}
// 	});		
	
  	function toSetPage(){
  		openDiv('${root}control/contractController/toSetPage.do?page_id=1');
  	}
  	
  	function back(){
  		openDiv('${root}control/contractController/list.do');
  	}
	</script>
</html>