
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/base/yuriy.jsp" %>
		<title>快递业务信息查询及新建</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="<%=basePath %>plugin/daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>plugin/daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/daterangepicker.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>base/base.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/jquery.shCircleLoader.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		
		<script type='text/javascript'>
	/**
	 * 查询
	 */
	function find() {
		$(window).scrollTop(0);
    	jumpToPage(1);
}

	function pageJump() {
		 var param='';
		 param=$('#form_table').serialize();
		 $.ajax({
				type: "POST",
	           	url:'${root}control/orderPlatform/transportProductTypeController/page.do?'+param+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val(),
	           	dataType: "text",
	           	data:'',
	    		cache:false,
	    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
	           	success: function (data){
	              $("#page_view").html(data);
	           	}
		  	});  
	}
	
	function del(){
		 if($("input[name='ckb']:checked").length>=1){
		  		 var result=  	confirm('是否删除！'); 
					if(result){
						var ids=$("input[name='ckb']:checked");
						var idsStr='';
						$.each(ids,function(index,item){
							idsStr=idsStr+this.value+';';
						});
		  	 	$.post('${root}/control/orderPlatform/transportProductTypeController/delte.do?ids='+idsStr,
		        		function(data){
		  			/*    if(typeof(reValue) != "undefined"&&data.documentURI.indexOf('BT-LMIS')>0){
						window.location='/BT-LMIS';
						return;
					};    */
		        	            if(data.code==1){
		        	            	alert("操作成功！");
		        	            }else if(data.code==0){
		        	            	alert("操作失败！");
		        	            	alert(data.msg);
		        	             }
					    find();
		                        }
		  		      ); 
		  		
					}
		  	
		}else{
			alert("请选择一行!");
		} 
		 
		
	}
	function toUpdate(uuid){
		$("#express_update").modal('show');
		$.post("${root}/control/orderPlatform/transportProductTypeController/toUpdate.do?id="+uuid,function(data){
			alert(data.product.express_name);
			$('#express_code_update').val(data.product.express_name);
			$('#product_type_code_update').val(data.product.product_type_code);
			$('#product_type_name_update').val(data.product.product_type_name);
			$('#id_update').val(uuid);
		})
	}
	function toAdd(){
		$("#express_add").modal('show');
	}
	
	/* function toAdd1(){
		var param =$("#addForm").serialize();
		
		$.post('${root}/control/orderPlatform/transportVenderController/add.do?'+param,function(data){
			if(data.data==1){
				alert('操作成功');
				$("#express_add").modal('hide');
				}else{
		       alert('操作失败');	
			$("#express_add").modal('hide');
			}
			setTimeout("loadingCenterPanel('${root}control/orderPlatform/transportVenderController/initail.do')",200);
		});	
	} */
	
/* 	function Update(){
		$("#express_update").modal('show');
		$.post("${root}/control/orderPlatform/transportProductTypeController/toUpdate.do?id="+uuid,function(data){
			$('#express_name').val(data.vender.express_name);
			$('#contacts').val(data.vender.contacts);
			$('#phone').val(data.vender.phone);
			$('#express_id').val(uuid);
		})
	} */
	
	function updateExpress(){
		var param =$("#updateForm").serialize();
		$.post('${root}/control/orderPlatform/transportProductTypeController/Update.do?'+param,function(data){
			if(data.data==1){
				alert('操作成功');
				$("#express_update").modal('hide');
				}else{
		       alert('操作失败');	
			$("#express_update").modal('hide');
			}
			setTimeout("loadingCenterPanel('${root}control/orderPlatform/transportProductTypeController/initail.do')",200);
		});	
	}
	function addExpressProduct(){
		var product_type_code=document.getElementById("product_type_code").value;
		if(product_type_code=="" ||product_type_code==null ||product_type_code==undefined){
			alert("快递业务类型代码为必填项，请重新输入");
			return false;
		}
		var product_type_name=document.getElementById("product_type_name").value;
		if(product_type_name=="" ||product_type_name==null ||product_type_name==undefined){
			alert("快递业务类型名称为必填项，请重新输入");
			return false;
		}
		var express_code=document.getElementById("express_code").value;
		if(express_code=="" ||express_code==null ||express_code==undefined){
			alert("快递公司名称为必填项，请重新输入");
			return false;
		}
		var param =$("#addForm").serialize();
		$.post('${root}/control/orderPlatform/transportProductTypeController/add.do?'+param,function(data){
			if(data.code==1){
				alert('操作成功');
				$("#express_add").modal('hide');
				}else{
		       alert('操作失败');	
			$("#express_add").modal('hide');
			}
			setTimeout("loadingCenterPanel('${root}control/orderPlatform/transportProductTypeController/initail.do')",200);
		});	
	}
	
	
	
	$(document).ready(function() {
		
	});
	
	var Select = {
			del : function(obj,e){
			if((e.keyCode||e.which||e.charCode) == 8){
			var opt = obj.options[0];
			opt.text = opt.value = opt.value.substring(0, opt.value.length>0?opt.value.length-1:0);
			}
			},
			write : function(obj,e){
			if((e.keyCode||e.which||e.charCode) == 8)return ;
			var opt = obj.options[0];
			opt.selected = "selected";
			opt.text = opt.value += String.fromCharCode(e.charCode||e.which||e.keyCode);
			}
			}
	
	

    </script>
	</head>
	
	<body>
		<div class="page-header"><h1 style='margin-left:20px'>快递业务信息查询及新建</h1></div>	
		<div style="margin-top: 20px;margin-left: 30px;margin-bottom: 20px;">
		  <form id='form_table'>
			<table>
		  	 <tr>
		  	 <td align="left" width="5%"><label class="blue" >快递业务类型&nbsp;:</label></td> 
					<td width="10%"><select id="express_code" name="express_code"  class='select'  data-edit-select="1">
						<option value=''>---请选择---</option>
						<c:forEach items="${venders}" var = "vender" >
							<option  value="${vender.express_code}">${vender.express_name}</option>
						</c:forEach>	
					</select></td>
					<td align="left" width="7%"><label class="blue" ></label></td> 
					<td width="10%"></td>	
					<td align="left" width="7%"><label class="blue" ></label></td> 
					<td width="10%"></td>			
		  		</tr>
			</table>
			</form>
		</div>
		<div style="margin-top: 10px;margin-left: 20px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;&nbsp;
			<button class="btn btn-xs btn-yellow" onclick="toAdd();">
				<i class="icon-hdd"></i>新增
			</button>
			&nbsp;
			<button class="btn btn-xs btn-inverse" onclick="del();">
				<i class="icon-trash"></i>删除
			</button
			>&nbsp;
		</div>
		<div id="page_view">
		<div style="height:400px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;margin-top: 22px;width:70%;margin-left:20px">
			<table class="table table-striped" >
		   		<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td>序号</td>
			  			<td>快递公司类型名称</td>
			  			<td>快递业务类型代码</td>
			  			<td>快递业务类型名称</td>
			  		</tr>  	
		  		</thead>
		  		<tbody  align="center">
		  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr ondblclick='toUpdate(${power.id})'>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
			  			<td>${status.count}</td>
			  			<td>${power.express_name }</td>
			  			<td>${power.product_type_code }</td>
			  			<td>${power.product_type_name }</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
      	<div style="margin-right:350px;margin-top:20px;">${pageView.pageView}</div>		</div>
      	<!-- 修改页面 -->
      	<div id= "express_update" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" style='height:50px' >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >修改页面</h4>
					</div>
					<div class= "modal-body" >
		 <form id="updateForm">			
			<table width='80%'>  
		  		    <tr> 
		  			<td width="15%" align="right"><label>快递公司名称:</label></td>
		  			<td width="20%" align="left">
		  			<input type='text' id='id_update' name='id' style='display:none'>
		            <input type='text' id='express_code_update' readonly="readonly">
		  			</td>
		  			</tr>
		  			<tr> 
		  			<td width="15%" align="right"><label>快递业务类型代码:</label></td>
		  			<td width="20%" align="left">
		  			<input type='text' id='product_type_code_update' name='product_type_code'>
		  			</td>
		  			</tr>
		  		<tr>
		  			<td width="15%" align="right"><label>快递业务类型名称:</label></td>
		  			<td width="20%" align="left">
		  			 <input type='text' id='product_type_name_update' name='product_type_name'>
		  			</td>
		  		</tr>
			</table>
			</form>
				</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss= "modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="updateExpress();" >
		     				<i class= "icon-save" aria-hidden= "true" ></i>保存
		     			</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 新增页面 -->
		<div id= "express_add" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" style='height:50px' >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >新增页面</h4>
					</div>
					<div class= "modal-body" >
		 <form id="addForm">			
			<table width='80%'> 
			   <tr> 
		  			<td width="15%" align="right"><label>快递公司名称:</label></td>
		  			<td width="20%" align="left">
		  			<select id="express_code" name="express_code"  class='select' onkeypress="Select.write(this,event)" onkeydown="Select.del(this,event)">
						<option value=''>---请选择---</option>
						<c:forEach items="${venders}" var = "vender" >
							<option  value="${vender.express_code}">${vender.express_name}</option>
						</c:forEach>	
					</select>
		  			</td>
		  			</tr>
		  			<tr> 
		  			<td width="15%" align="right"><label>快递业务类型代码:</label></td>
		  			<td width="20%" align="left">
		  			<input type='text' id='product_type_code' name='product_type_code'>
		  			</td>
		  			</tr>
		  		<tr>
		  			<td width="15%" align="right"><label>快递业务类型名称:</label></td>
		  			<td width="20%" align="left">
		  			 <input type='text' id='product_type_name' name='product_type_name'>
		  			</td>
		  		</tr>
			</table>
			</form>
				</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss= "modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="addExpressProduct();" >
		     				<i class= "icon-save" aria-hidden= "true" ></i>保存
		     			</button>
					</div>
				</div>
			</div>
		</div>
		
		
		<div class="modal fade" id="loadingModal">
	<div style="width: 200px;height:20px; z-index: 20000; position: absolute; text-align: center; left: 50%; top: 50%;margin-left:-100px;margin-top:-10px">
		<div class="progress progress-striped active" style="margin-bottom: 0;">
			<div class="progress-bar" style="width: 100%;"></div>
		</div>
		<h5>正在加载...</h5>
	</div>
</div>
		
		
	</body>
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
</style>
