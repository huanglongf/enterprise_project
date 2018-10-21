<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
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
		  			<td width="10%" align="right"><label>店铺:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="store_id" name="store_id"  class='select' data-edit-select="1" >
						<option value=''>---请选择---</option>
						<c:forEach items="${stores}" var = "store" >
							<option  value="${store.id}">${store.store_name}</option>
						</c:forEach>	
					</select>
		  			</td>
		  			<td width="10%" align="right"><label>sku编码:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="sku_number" name="sku_number"  style='width:100%;height:34px' >
					</input>
		  			</td>
		  		</tr>

		  		<tr align="center">
		  		<td width="10%" align="right" ><label>操作时间(开始):</label></td>
		  			<td width="20%" align="left">
		  			 <div style="width:100%;" >
		                 <input id= "begin_operation_time" name= "begin_operation_time" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>		
		  		</td>
		  		<td width="10%" align="right" ><label>操作时间(结束):</label></td>
		  			<td width="20%" align="left">
		  			 <div style="width:100%;" >
		                 <input id= "end_operation_time" name= "end_operation_time" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>		
		  		</td>
		  		<td width="10%" align="right"><label>订单类型:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="order_type" name="order_type"  style='width:100%;height:34px' >
					</input>
		  			</td>
		  		</tr>
		  	<tr align="center">
		  			<td width="10%" align="right"><label>前置单据号:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="epistatic_order" name="epistatic_order"  style='width:100%;height:34px' >
					</input>
		  			</td>	
		  		<td width="10%" align="right"><label>平台订单号:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="platform_order" name="platform_order"  style='width:100%;height:34px' >
					</input>
		  			</td>
		  			<td width="10%" align="right"><label>作业类型:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="job_type" name="job_type"  style='width:100%;height:34px' >
					</input>
		  			</td>		
		  				</tr>
		  		
			</table>
		</form>
		</div>
		<div class="div_margin" style="margin-left : 20px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			<button class= "btn btn-xs btn-primary" onclick= "download();" >
				<i class= "icon-hdd" ></i>导出
			</button>
		</div>
<div id="page_view">
		<div class= "title_div" id= "sc_title">
			<table class= "table table-striped" style= "table-layout: fixed;overflow-x:show;" >
		   		<thead>
			  		<tr class= "table_head_line"  >
                            <td class="td_cs">序号</td> 
			  			    <td class="td_cs">操作时间</td><td class="td_cs">订单类型</td>
			  			    <td class="td_cs">店铺</td>
			  			    <td class="td_cs">仓库</td>
			  			    <td class="td_cs">wms单号</td>
			  			    <td class="td_cs">前置单据号</td><td class="td_cs">平台订单号</td>
			  			    <td class="td_cs">作业类型</td>
			  			    <td class="td_cs">入库数量</td>
			  			    <td class="td_cs">出库数量</td>
			  			    <td class="td_cs">商品编码</td>
			  			    <td class="td_cs">SKU编码</td>
			  			    <td class="td_cs">货号</td>
                            <td class="td_cs">商品名称</td>
			  		</tr>  	
				</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  		<tbody align= "center">
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
     <div id='botton_page' style="margin-right: 30px;margin-top:20px;">
<div style="margin-right: 30px;margin-top:20px;"></div>	
      	 </div>
      	 </div> 		
	</body>
	<script type="text/javascript">
	  
		$(document).ready(function() {
			
			
		});
		
		function  find(){
			$(window).scrollTop(0);
			pageJump();
			
		}
		
		function pageJump() {
			 var param='';
			loadingStyle();
			 param=$('#main_search').serialize();
		 	$.ajax({
					type: "POST",
		           	url:'${root}/control/OperationfeeDataDetailController/page.do?'+param,
		           	dataType: "text",
		           	data:'',
		    		cache:false,
		    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		           	success: function (data){
		              $("#page_view").html(data);
		              cancelLoadingStyle();
		           	}
			  	});  
		    }
		
		function download(){
			$(window).scrollTop(0);
			if($("#begin_operation_time").val()==''||$("#end_operation_time").val()=='')
				{alert('导出时间不能为空！');return;}
			param=$('#main_search').serialize();
			var begin=$("#begin_operation_time").val();
			var end =$("#end_operation_time").val();
			var bd=new Date(begin);
			var ed=new Date(end);
			var date3 = ed.getTime() - bd.getTime();
			var leave = date3 % (12 * 30 * 24 * 3600 * 1000);
			var days = Math.floor(leave / (24 * 3600 * 1000));
		     if(days>30){
		    	 alert('导出时间 间隔不能超过30天！');return; 	 
		     }
			loadingStyle();
			$.ajax({
				type: "POST",
	           	url:'${root}/control/OperationfeeDataDetailController/download.do?'+param,
	           	dataType: "json",
	           	data:'',
	           	success: function (data){
	           	 cancelLoadingStyle();
	           	 if(data.code==0){alert("操作失败！"+data.msg)}
	           	 else{alert('操作成功!');
	             	window.open('${root}/DownloadFile/'+data.url);
	           	 }
				    //   $("#load_load").css("display","none");
	           	}
		  	});  
			
			
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
