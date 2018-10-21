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
		  			<td width="10%" align="right"><label>运单号:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="sku_code" name="express_number"  style='width:100%;height:34px' >
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
		</div>
<div id="page_view">
		<div class= "title_div" id= "sc_title">
			<table class= "table table-striped" style= "table-layout: fixed;overflow-x:show;" >
		   		<thead>
			  		<tr class= "table_head_line"  >
                            <td class="td_cs">序号</td> 
			  			    <td class="td_cs">运单号</td>
			  			    <td class="td_cs">快递公司</td>
			  			    <td class="td_cs">产品类型</td>
			  			    <td class="td_cs">重量</td>
			  			    <td class="td_cs">长</td>
			  			    <td class="td_cs">宽</td>
			  			    <td class="td_cs">高</td>
			  			    <td class="td_cs">体积</td>
			  			    <td class="td_cs">标准运费</td>
			  			    <td class="td_cs">是否保价</td>
			  			    <td class="td_cs">保价费用</td>
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
		
		function find() {
			 var param='';
			 if($("input[name='express_number']").val()==''){
				 alert('运单号不能为空');
				 return;
			 }
			 loadingStyle();
			 param=$('#main_search').serialize();
			 param+='&lastPage='+$('#lastPage').val()+'&lastTotalCount='+$('#lastTotalCount').val();
			// alert(param+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
		 	$.ajax({
					type: "POST",
		           	url:'${root}/control/WarehouseExpressDataSettleController/pageQuery.do?'+param,
		           	dataType: "text",
		           	data:'',
		    		cache:false,
		    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		           	success: function (data){
		           		cancelLoadingStyle();
		           		
		              $("#page_view").html(data);
		              
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
