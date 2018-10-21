
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/base/yuriy.jsp" %>
		<title>快递信息查询及新建</title>
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
		//loadingCenterPanel('${root}control/orderPlatform/transportVenderController/page.do')
}

	function pageJump() {
		 var param='';
		 param=$('#form_table').serialize();
		 $.ajax({
				type: "POST",
	           	url:'${root}control/orderPlatform/transportVenderController/page.do?'+param+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val(),
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
		  	 	$.post('${root}/control/orderPlatform/transportVenderController/delte.do?ids='+idsStr,
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
		$.post("${root}/control/orderPlatform/transportVenderController/toUpdate.do?id="+uuid,function(data){
			$('#express_name').val(data.vender.express_name);
			$('#contacts').val(data.vender.contacts);
			$('#phone').val(data.vender.phone);
			$('#express_id').val(uuid);
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
	
	function Update(){
		$("#express_update").modal('show');
		$.post("${root}/control/orderPlatform/transportVenderController/toUpdate.do?id="+uuid,function(data){
			$('#express_name').val(data.vender.express_name);
			$('#express_code').val(data.vender.express_code);
			$('#contacts').val(data.vender.contacts);
			$('#phone').val(data.vender.phone);
			$('#express_id').val(uuid);
		})
	}
	
	function updateExpress(){
		var express_name=document.getElementById("express_name").value;
		if(express_name=="" ||express_name==null ||express_name==undefined){
			alert("快递公司名称为必填项，请重新输入");
			return false;
		}
		var phone=document.getElementById("phone").value;
		if(phone=="" ||phone==null ||phone==undefined){
			alert("联系方式为必填项，请重新输入");
			return false;
		}
		var contacts=document.getElementById("contacts").value;
		if(contacts=="" ||contacts==null ||contacts==undefined){
			alert("联系人为必填项，请重新输入");
			return false;
		}
		var param =$("#updateForm").serialize();
		$.post('${root}/control/orderPlatform/transportVenderController/Update.do?'+param,function(data){
			if(data.data==1){
				alert('操作成功');
				$("#express_update").modal('hide');
				}else{
		       alert('操作失败');	
			$("#express_update").modal('hide');
			}
			setTimeout("loadingCenterPanel('${root}control/orderPlatform/transportVenderController/initail.do')",200);
		});	
	}
	function addExpress(){
		var express_name=document.getElementById("express_name1").value;
		if(express_name=="" ||express_name==null ||express_name==undefined){
			alert("快递公司名称为必填项，请重新输入");
			return false;
		}
		var express_code1=document.getElementById("express_code1").value;
		if(express_code1=="" ||express_code1==null ||express_code1==undefined){
			alert("快递公司代码为必填项，请重新输入");
			return false;
		}
		var phone=document.getElementById("phone1").value;
		if(phone=="" ||phone==null ||phone==undefined){
			alert("联系方式为必填项，请重新输入");
			return false;
		}
		var contacts=document.getElementById("contacts1").value;
		if(contacts=="" ||contacts==null ||contacts==undefined){
			alert("联系人为必填项，请重新输入");
			return false;
		}
		var param =$("#addForm").serialize();
		$.post('${root}/control/orderPlatform/transportVenderController/add.do?'+param,function(data){
			if(data.code==1){
				alert('操作成功');
				$("#express_add").modal('hide');
				}else{
		       alert('操作失败');	
			$("#express_add").modal('hide');
			}
			setTimeout("loadingCenterPanel('${root}control/orderPlatform/transportVenderController/initail.do')",200);
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
		<div class="page-header"><h1 style='margin-left:20px'>快递信息查询及新建</h1></div>	
		<div style="margin-top: 20px;margin-left: 30px;margin-bottom: 20px;">
		  <form id='form_table'>
			<table>
		  	 <tr>
		  	 <td align="left" width="7%"><label class="blue" >快递公司&nbsp;:</label></td> 
					<td width="20%"><select id="express_code" name="express_code"  class='select'   data-edit-select="1">
						<option value=''>---请选择---</option>
						<c:forEach items="${venders}" var = "vender" >
							<option  value="${vender.express_code}">${vender.express_name}</option>
						</c:forEach>	
					</select></td>	
				 <td align="left" width="7%"><label class="blue" >&nbsp;</label></td> 
					<td width="20%"></td>	
					 <td align="left" width="7%"><label class="blue" >&nbsp;</label></td> 
					<td width="20%"></td>			
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
		<div style="height:500px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;margin-top: 22px;width:70%;margin-left:20px">
			<table class="table table-striped" >
		   		<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td>序号</td>
			  			<td>快递公司名称</td>
			  			<td>快递公司代码</td>
			  			<td>联系人</td>
			  			<td>联系人手机</td>
			  			<td>是否需要上门打印面单</td>
			  		</tr>  	
		  			
		  		</thead>
		  		<tbody  align="center">
		  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr ondblclick='toUpdate("${power.id}")'>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
			  			<td>${status.count}</td>
			  			<td>${power.express_name }</td>
			  			<td>${power.express_code }</td>
			  			<td>${power.contacts }</td>
			  			<td>${power.phone }</td>
			  			<td ${power.is_docall }>
			  				<c:if test="${power.is_docall=='1'}">是</c:if>
		   					<c:if test="${power.is_docall=='0'}">否</c:if>
			  			</td>
			  			
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
		  		    <input type='text' id='express_id' name='id' style='display:none'>	
		  			<td width="10%" align="right"><label>快递公司名称:</label></td>
		  			<td width="20%" align="left">
		  			<input type='text' id='express_name' name='express_name'>
		  			</td>
		  			</tr>
		  		<tr>
		  			<td width="10%" align="right"><label>联系人:</label></td>
		  			<td width="20%" align="left">
		  			 <input type='text' id='contacts' name='contacts'>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td width="10%" align="right"><label>联系方式:</label></td>
		  			<td width="20%" align="left">
		  			 <input id="phone" style="IME-MODE: disabled; WIDTH: 159px; HEIGHT: 28px" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="20" size="14" name="phone" type="text" />
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
		  			<td width="10%" align="right"><label>快递公司名称:</label></td>
		  			<td width="20%" align="left">
		  			<input type='text' id='express_name1' name='express_name'>
		  			</td>
		  			</tr>
		  			<tr> 
		  			<td width="10%" align="right"><label>快递公司代码:</label></td>
		  			<td width="20%" align="left">
		  			<input type='text' id='express_code1' name='express_code'>
		  			</td>
		  			</tr>
		  		<tr>
		  			<td width="10%" align="right"><label>联系人:</label></td>
		  			<td width="20%" align="left">
		  			 <input type='text' id='contacts1' name='contacts'>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td width="10%" align="right"><label>联系方式:</label></td>
		  			<td width="20%" align="left">
		  			 <input id="phone1" style="IME-MODE: disabled; WIDTH: 159px; HEIGHT: 28px" onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" maxlength="20" size="14" name="phone" type="text" />
		  			</td>
		  		</tr>
			</table>
			</form>
				</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss= "modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="addExpress();" >
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
