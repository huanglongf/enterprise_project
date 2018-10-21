<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
		<link type= "text/css" href= "<%=basePath %>daterangepicker/font-awesome.min.css" rel= "stylesheet" />
		<link type= "text/css" href= "<%=basePath %>daterangepicker/daterangepicker-bs3.css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
		<script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/ajaxfileupload.js"></script>
        <style type="text/css">
         label{
          margin-right: 15px;
          font-size: 14px;
         }
        </style>
	</head>
<body>
		<div class="page-header" style="margin-left : 20px;">
		<form id= "main_search">
			<table >
		  		<tr>
		  			<td width="10%" align="right"><label>系统仓:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="warehouse_code" name="warehouse_code"  class='select' data-edit-select="1" >
						<option value=''>---请选择---</option>
						<c:forEach items="${warehouses}" var = "warehouse" >
							<option  value="${warehouse.warehouse_code}">${warehouse.warehouse_name}</option>
						</c:forEach>
					</select>
		  			</td>
		  			<td width="10%" align="right"><label>货位类型:</label></td>
		  			<td width="20%" align="left">
		  			   <input id="location_type" name="location_type"  style='width:100%;height:34px' >
					</select>
		  			</td>
		  			<td width="10%" align="right"><label>库区编码:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="reservoir_code" name="reservoir_code"  style='width:100%;height:34px' >
					</input>
		  			</td>
		  		</tr>
		  		<tr align="center">
		  			<td width="10%" align="right"><label>通道:</label></td>
		  			<td width="20%" align="left">
		  			 <input id="passageway_code" name="passageway_code"  style='width:100%;height:34px' >
		  			</td>
			</table>
		</form>
		</div>
		<div class="div_margin" style="margin-left : 20px;">
			<button class="btn btn-xs btn-pink" onclick="storagelocationQuery();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			<button class= "btn btn-xs btn-primary" onclick= "selectUploadModel();" >
				<i class= "icon-hdd" ></i>导入
			</button>
			<button class= "btn btn-xs btn-primary" onclick= "download();" >
				<i class= "icon-hdd" ></i>下载导入模版
			</button>
			<button class= "btn btn-xs btn-inverse" onclick= "deleteRecords();" >
				<i class= "icon-trash" ></i>删除
			</button>
		</div>
<div id="page_view">
		<div class='title_div' style="height : 520px; overflow : auto; overflow : scroll; border : solid #F2F2F2 1px;"  >
			<table class="table table-striped" overflow-x：show>
		   		<thead  align="center">
			  		<tr class='table_head_line' >
				  		<td class="td_ch"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/></td>
                        <td class="td_cs">序号</td> 
			  			<td class="td_cs">系统仓</td>
			  			<td class="td_cs">货位类型</td>
			  			<td class="td_cs">库区编码</td>
			  			<td class="td_cs">通道</td>
			  			<td class="td_cs">组</td>
			  			<td class="td_cs">行</td>
			  			<td class="td_cs">格</td>
			  			<td class="td_cs">单个库位面积</td>
			  			<td class="td_cs">单个库位体积</td>
			  			<td class="td_cs">总占地面积</td>
			  			<td class="td_cs">库位数量</td>
			  			</tr>		  			
		  		</thead>
		  		<tbody id='tbody' align="center">
		  		
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
     <div id='botton_page' style="margin-right: 30px;margin-top:20px;">
<div style="margin-right: 30px;margin-top:20px;"></div>	
      	 </div>
      	 </div> 	
      
      
      	 <!--批量上传  -->
		<div id= "batch_storeagelocation" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >库位信息批量上传</h4>
					</div>
					<div class= "modal-body" >
					<table>
						<tr align="center">
						<input id="file1" type="file" name='file' style='display:none'>  
					    	<div style='text-align: center;'>  
						    	<input id="photoCover" class="input-large" type="text" style="height:30px;">  
						   		<a class="btn" onclick="$('input[id=file1]').click();">浏览</a> 
						    	<a  id='upload' class="btn"  href='javascript:void(0)'>上传</a>
							</div>  		
						</tr>
					</table>
						
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back2" type= "button" class= "btn btn-default" data-dismiss="modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
					</div>
				</div>
			</div>
		</div>
			<!-- 修改页面 -->
		<div id= "toUpdate" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >库位信息修改</h4>
					</div>
					<div class= "modal-body" >
		 <form id="updateForm">			
			<table width='80%'>
		  		<tr>
		  			<td width="10%" align="right"><label>系统仓:</label></td>
		  			<td width="20%" align="left">
		  			    <input type='text' id="updateId" name ='id' style='display:none'/>
		  			    <input type='text' id="warehouse_name" name ='warehouse_name' style='display:none'/>
		  			    <select id="warehouse_update_code" name="warehouse_code"  class='select' data-edit-select="1" >
						<option value=''>---请选择---</option>
						<c:forEach items="${warehouses}" var = "warehouse" >
							<option  value="${warehouse.warehouse_code}">${warehouse.warehouse_name}</option>
						</c:forEach>
					</select>
					<input type='text' id='id_update' style='display:none'>
		  			</td></tr>
		  		<tr>	
		  			<td width="10%" align="right"><label>货位类型:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="location_update_type" name="location_type"  style='width:100%;height:34px' >
		  			</td>
		  			</tr>
		  		<tr align="center">
		  			<td width="10%" align="right"><label>库区编码:</label></td>
		  			<td width="20%" align="left">
		  			 <input id="reservoir_update_code" name="reservoir_code"  style='width:100%;height:34px' >
		  			</td></tr>
		  			<tr>
		  		<td width="5%" align="right" ><label>通道:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="passageway_update_code" name="passageway_code"  style='width:100%;height:34px' >
		  		</td>
		  		</tr>
		  		<tr>
		  		<td width="5%" align="right" ><label>组:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="group_update_code" name="group_code"  style='width:100%;height:34px' >
		  		</td>
		  		</tr>
                <tr>
		  		<td width="5%" align="right" ><label>行:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="line_update_code" name="line_code"  style='width:100%;height:34px' >
		  		</td>
		  		</tr>
		  		<tr>
		  		<td width="5%" align="right" ><label>格:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="cell_update_code" name="cell_code"  style='width:100%;height:34px' >
		  		</td>
		  		</tr>
		  		<tr>
		  		<td width="5%" align="right" ><label>单个库位面积:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="single_update_square" name="single_square"  style='width:100%;height:34px' style="IME-MODE: disabled;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="10" size="14" type="text"/>
		  		</td>
		  		</tr>
		  		<tr>
		  		<td width="5%" align="right" ><label>单个库位体积:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="single_update_volumn" name="single_volumn"  style='width:100%;height:34px' style="IME-MODE: disabled;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="10" size="14" type="text" >
		  		</td>
		  		</tr>
		  		<tr>
		  		<td width="5%" align="right" ><label>总占地面积:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="all_update_square" name="all_square"  style='width:100%;height:34px' style="IME-MODE: disabled;" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="10" size="14" type="text" >
		  		</td>
		  		</tr>
		  		<tr>
		  		<td width="5%" align="right" ><label>库位数量:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="storage_update_number" name="storage_number"  style='width:100%;height:34px' style="IME-MODE: disabled;" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="5" size="14" type="text" >
		  		</td>
		  		</tr>						
			</table>
			</form>
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss= "modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="updateLocation();" >
		     				<i class= "icon-save" aria-hidden= "true" ></i>保存
		     			</button>
					</div>
				</div>
			</div>
		</div>
			
	</body>
	<script type="text/javascript">
	  function updateLocation(){
		  var param =$('#updateForm').serialize();
		  $.post('${root}/control/storageLocationController/update.do?'+param,function(data){
			  if(data.code==0){alert(data.msg);}else if(data.code==1){
				  alert('操作成功！');
				  $("#toUpdate").modal('hide');
				  pageJump();
			  }
		  })
	  }
	
	  
	
	
		$(document).ready(function() {
			if('${queryParam.transport_code}'==''){
				$("#producttype_code").next().attr("disabled", "disabled");
		 }
			 $("#transport_code").bind("change",function(){
					ExpressCodeChange("transport_code","itemtype_code","");
				 
			 });
			 
			/*  $('#location_type').bind('click',function(){
				 flag=1;
			 });
			 $('#reservoir_code').bind('click',function(){
				 flag=1;
			 });
			 $('#passageway_code').bind('click',function(){
				 flag=1;
			 }); */
			 
			  $("#upload").click(function () {
			    	//loadingStyle();
			        ajaxFileUpload();
			    });
		    
		    	$('input[id=file1]').change(function() { 
			        $('#photoCover').val($(this).val());
		        }); 	 
			 
		    	 $("#warehouse_update_code").bind("change",function(){
		    		 $('#warehouse_name').val($('#warehouse_update_code').next().val());	 
				 });
		    	
			
		});
		
		
		
		function  storagelocationQuery(){
			$(window).scrollTop(0);
			pageJumpZyz();
			
		}
		
		function pageJumpZyz() {
			 var param='';
			loadingStyle();
			 param=$('#main_search').serialize();
		 	$.ajax({
					type: "POST",
		           	url:'${root}/control/storageLocationController/pageQuery.do?'+param,
		           	dataType: "text",
		           	data:'',
		    		cache:false,
		    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		           	success: function (data){
		           	  cancelLoadingStyle();
		              $("#page_view").html(data);
		           	}
			  	});  
			  	
			  	//$.post('${root}/control/expressReturnStorageController/query.do?storeCode=tst&warehouseCode=erreer',function(){});
		    }
		
		function download(){
			$(window).scrollTop(0);
			loadingStyle();
			$.ajax({
				type: "POST",
	           	url:'${root}/control/storageLocationController/downloadModel.do?',
	           	dataType: "json",
	           	success: function (data){
	           	 cancelLoadingStyle();
				 window.open('${root}/DownloadFile/'+data.path);
	           //   $("#load_load").css("display","none");
	           	}
		  	});  	
		}
		
		
		function ajaxFileUpload() {
			loadingStyle();
			$.ajaxFileUpload({
				url: '${root}/control/storageLocationController/upload.do', //用于文件上传的服务器端请求地址
	            secureuri: false, //是否需要安全协议，一般设置为false
				fileElementId: 'file1', //文件上传域的ID
				dataType: 'json', //返回值类型 一般设置为json
					//服务器成功响应处理函数
					success: function (data, status){
						if(data.code==1){
							alert('操作成功');
							 $("#batch_storeagelocation").modal('hide');
							 cancelLoadingStyle();
							pageJump();
						}else{
							 $("#batch_storeagelocation").modal('hide');
							
							alert('操作成功,请查看日志');
							 cancelLoadingStyle();
							 window.open('${root}/DownloadFile/'+data.path);
						}
						
					}
				})
		        return false;
			}
		
		
		function selectUploadModel(){
	    	   $("#batch_storeagelocation").modal('show');
	       } 
		
	 	 function deleteRecords(){
	 		 if(!confirm("确定要清除勾选数据吗？"))return;
	 		 
			var dIds=$("input[name='ckb']:checked");
			if($("input[name='ckb']:checked").length==0){
				alert('请至少选择一项！！');
				return;
			}
			$(window).scrollTop(0);
			loadingStyle();
		 	var id='';
			$.each(dIds,function(index,item){
				id=id+item.value+";";	
			}); 
			$.post('${root}/control/storageLocationController/delete.do?ids='+id,function(data){
				if(data.code==0){
					alert('操作失败！');
					cancelLoadingStyle();
				}else if(data.code==1){
					alert('操作成功！');
					cancelLoadingStyle();
					pageJump();
				}
			})
			
			
	  	}  

		 function toUpdate(id){
			 $("#toUpdate").modal('show');
			$.post('${root}/control/storageLocationController/toUpdate.do?id='+id,function(data){
				parseData(data);
			});
		 
		 }
		 
		 function parseData(data){
			 var result=data.result;
			   $("#warehouse_update_code option").each(function(index,item){
			    	if($(this).text()==result.warehouse_name){
			    		$("#warehouse_update_code").next().val(result.warehouse_name);
						$("#warehouse_update_code").next().attr(result.warehouse_code);
						$("#warehouse_update_code" + " option:eq("+index+")").attr("selected", true);
			    	}
			     });
			   $('#location_update_type').val(result.location_type);
			   $('#reservoir_update_code').val(result.reservoir_code);
			   $('#group_update_code').val(result.group_code);
			   $('#passageway_update_code').val(result.passageway_code);
			   $('#line_update_code').val(result.line_code);
			   $('#cell_update_code').val(result.cell_code);
			   $('#single_update_square').val(result.single_square);
			   $('#single_update_volumn').val(result.single_volumn);
			   $('#all_update_square').val(result.all_square);
			   $('#storage_update_number').val(result.storage_number);
			   $('#updateId').val(result.id);
			   
		 }
		 
	</script>	
</html>
<style>

.select {
    padding: 3px 4px;
    height: 30px;
    width: 160px;
   text-align: enter;
}
.table_head_line td {
font-weight: bold;
font-size: 16px
}
.modal-header{
height:50px;

}
</style>
