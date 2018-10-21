<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport"    content="width=device-width, initial-scale=1.0" />
		<link type="text/css"  href="<%=basePath %>css/table.css" rel="stylesheet" />
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
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>	</head>
	<body>
		<div class="page-header">
		<h1>账单核销</h1>
	</div>	
		<form id="main_search">
		<!--  <div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">-->
			<table>
		  		<tr>
		  			<!-- <td width="6%" align="right"><label>承运商:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="express_code" name="express_code"  class='select' data-edit-select="1">
						<option value=''>---请选择---</option>
						<c:forEach items="${transportVenders}" var = "vender" >
							<option  value="${vender.transport_code}">${vender.transport_name}</option>
						</c:forEach>	
					</select>
		  			</td> -->
		  			<td width="6%" align="right"><label>合同:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="con_id" name="con_id"  class='select' data-edit-select="1" >
						<option value=''>---请选择---</option>
						<c:forEach items="${expressContracts}" var = "expressContract" >
							<option  value="${expressContract.id}">${expressContract.contract_name}</option>
						</c:forEach>	
					</select>
		  			</td>
		  			<td width="10%" align="right"><label>账单周期:</label></td>
		  			<td width="20%" align="left">
		  			  <input id= "billingCycle" name= "billingCycle" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM'})" />
		  			</td>
		  		</tr>
		  		<tr>
		  			<td width="6%" align="right"><label>账单名称:</label></td>
		  			<td width="20%" align="left">
		  			   <input id="bill_name" name="bill_name"  style='width:100%;height:34px'>
		  			</td>
		  			<td width="6%" align="right"><label>账单状态:</label></td>
		  			<td width="20%" align="left">
		  			  <select id="status"  name="status" style="width: 60%;" class='select' data-edit-select="1">
		  			     <option value="">---请先选择账单状态---</option>
		  			      <option value="0">---已关账---</option>
		  			      <option value="1">---未关账---</option>
		  			  </select>
		  			</td>
		  		</tr>
			</table>
		<!-- </div> -->
		</form>
		
		<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find()">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;&nbsp;
			&nbsp;
			<button class="btn btn-xs btn-pink" onclick="add();">
				<i class="icon-search icon-on-right bigger-110"></i>新增
			</button>
			&nbsp;&nbsp;
			&nbsp;
			<button class="btn btn-xs btn-inverse" onclick="deletevfc();">
				<i class="icon-trash"></i>删除
			</button>
		</div>
		<div id="page_view">
		<div style="height:400px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;margin-top: 22px;">
			<table class="table table-striped">
		   		<thead  align="center">
			  		<tr>
			  			<td style='width:50px'><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td class="td_cs">承运商</td>
			  			<td class="td_cs">合同名称</td>
			  			<td class="td_cs">账单周期</td>
			  			<td class="td_cs">账单名称</td>
			  			<td class="td_cs">备注</td>
			  			<td class="td_cs">账单状态</td>
			  		</tr>  			  			
		  		</thead>
		  		<tbody  align="center">
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
      <div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>	
		</div>	
		
		
		
		
		<div id= "new_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" ></h4>
					</div>
					<div class= "modal-body" >
		 <form id="addForm">			
			<table width='80%'>
		  		<tr>
		  			<td width="10%" align="right"><label>承运商合同:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="con_id" name="express_code"  class='select' data-edit-select="1" >
						<option value=''>---请选择---</option>
						<c:forEach items="${expressContracts}" var = "vender" >
							<option  value="${vender.contract_owner}">${vender.contract_name}</option>
						</c:forEach>	
					</select>
		  			</td>
		  		<tr>	
		  			<td width="10%" align="right"><label>账单周期:</label></td>
		  			<td width="20%" align="left">
		  			   <input id= "billingCycle_add" name= "billingCycle" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM'})" />
		  			</td>
		  			</tr>
		  			<tr>
		  			<td width="10%" align="right"><label>账单名称:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="bill_name_add" name="bill_name"  style='width:100%;height:34px'>
		  			</td>
		  		</tr>
		  		<tr align="center">
		  			<td width="10%" align="right"><label>备注:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="remark_add" name="remarks"  style='width:100%;height:34px'>
		  			</td></tr>
			</table>
			</form>
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss= "modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="toAdd();" >
		     				<i class= "icon-save" aria-hidden= "true" ></i>保存
		     			</button>
					</div>
				</div>
			</div>
		</div>
		
	</body>
	
	<script type='text/javascript'>
	loadingStyle();
	function add(){
		
		$("#new_form").modal();
		
	}
	
	function toAdd(){
		loadingStyle();
		var param =$('#addForm').serialize();
		 $.ajax({
				type: "POST",
	           	url:'${root}/control/lmis/expressbillMasterController/add.do?'+param,
	           	dataType: "json",
	           	data:'',
	    		cache:false,
	           	success: function (data){
	           		if(data.toString()=='[object XMLDocument]'){
	      			  window.location='/BT-LMIS';
	      				return;
	      		  }
	           		if(data.code==1){
	           			alert('添加成功！！');
	           		}else{
	           			alert('操作失败！！');
	           		}
	           		
	           		$("#new_form").modal('hide');
	           		cancelLoadingStyle();
	               find();
	           	},
	           	error:function(){
	           		if(data.toString()=='[object XMLDocument]'){
	      			  window.location='/BT-LMIS';
	      				return;
	      		  }
	           		alert('系统错误！');
	           		cancelLoadingStyle();
	           	}
		  	});
		
		
	}
	
	
	function pageJump() {
		$(window).scrollTop(0);
		loadingStyle();
		var data=$("#search_td").serialize();
		openDiv('${root}control/warehouseExpressDataController/pageQuery.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	}

	function find(){
		$(window).scrollTop(0);
		loadingStyle();
		pageJumpZ();
	}
	
	
	function pageJumpZ(){
		 var param='';
		 loadingStyle();
		 param=$('#main_search').serialize();
	 	 $.ajax({
				type: "POST",
	           	url:'${root}/control/lmis/expressbillMasterController/pageQuery.do?'+param,
	           	dataType: "text",
	           	data:'',
	    		cache:false,
	    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
	           	success: function (data){
	           		cancelLoadingStyle();
	              $("#page_view").html(data);
	           //   $("#load_load").css("display","none");
	           	}
		  	});  
		
		
	}
	
	
	$(document).ready(function() {
		 $("#transport_code").bind("change",function(){
				ExpressCodeChange("transport_code","itemtype_code","");
		 });
		 cancelLoadingStyle();
			});	

	
	function ExpressCodeChange(upper, lower,third){
		upper = "#" + upper;
		lower = "#" +lower;
		var transport_code = $(upper).val();
		if(transport_code == ""){
			$(lower).children(":first").siblings().remove();
			$(lower).val("");
			$(lower).next().val("");
			$(lower).next().attr("placeholder", "---请选择---");
			$(lower).next().attr("disabled", "disabled");
		} else {
			$.ajax({
				url : root + "/control/radar/expressinfoMasterController/getRouteStatus.do",
				type : "post",
				data : {
					"transport_code" : transport_code
				},
				dataType : "json",
				async : false, 
				success : function(result) {
					$(lower).next().attr("disabled", false);
					$(lower).children(":first").siblings().remove();
					$(lower).siblings("ul").children(":first").siblings().remove();
						var content1 = '';
					var content2 = '';
					for(var i =0; i < result.products.length; i++){
						content1 += 
							'<option value="' 
							+ result.products[i].product_type_code 
							+ '">'
							+result.products[i].product_type_name 
							+'</option>'
						content2 += 
							'<li class="m-list-item" data-value="'
							+ result.products[i].product_type_code
							+ '">'
							+ result.products[i].product_type_name
							+ '</li>'
					}
					$(lower + " option:eq(0)").after(content1);
					$(lower + " option:eq(0)").attr("selected", true);
					$(lower).siblings("ul").children(":first").after(content2);
					$(lower).siblings("ul").children(":first").addClass("m-list-item-active");
				},
				error : function(result) {
					if(result.responseText.indexOf('window.top.location.href')!=-1&&result.responseText.indexOf('BT-LMIS')!=-1)
					window.top.location.href='/BT-LMIS';
				}
			});
		}
	}
	
	
	function download(){
		 var param='';
		 loadingStyle();
		 param=$('#main_search').serialize();
		$.post(root +'/control/warehouseExpressDataController/download.do?'+param,function(data){
			cancelLoadingStyle();
			window.open('${root}/DownloadFile/'+data.data);
			
		});
		
		
		
	}
	
	
	function deletevfc() {
		if($("input[name='ckb']:checked").length==1){
	  		 var result=  	confirm('是否删除！'); 
	  		if(result){
	  			 $(window).scrollTop(0);
	  			loadingStyle();
				var id=$("input[name='ckb']:checked");
				var idsStr="";
				$.each(id,function(index,item){
					idsStr=this.value;
				});
				$.post('${root}/control/lmis/expressbillMasterController/deletevfc.do?ids='+idsStr,
		        		function(data){
					cancelLoadingStyle();
		        	            if(data.data==0){
		        	            	alert("操作成功！");
		        	            }else if(data.data==2){
		        	            	alert("操作失败！");
		        	             }else if(data.data==1){
		        	            	alert("操作进行中，不能删除！");
		        	             }
		        	            $(window).scrollTop(0);
	        					loadingStyle();
	        					pageJumpZ();
		                        }
		  		      ); 
	  		}	
		}else{
			alert("请选择一行!");
		} 
	}
	
    </script>
    <style>
    .select {
    padding: 3px 4px;
    height: 36px;
    width: 230px;
    }
    
    </style>
    
</html>
