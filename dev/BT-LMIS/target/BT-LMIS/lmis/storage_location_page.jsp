<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%@ include file="/lmis/yuriy.jsp" %>
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>daterangepicker/font-awesome.min.css" rel= "stylesheet" />
		 <style type="text/css">
         label{
          margin-right: 15px;
          font-size: 14px;
         }
        </style>
	</head>
<body>
		<div class= "title_div" id= "sc_title">
			<table class= "table table-striped" style= "table-layout: fixed;overflow-x:show;" >
		   		<thead>
			  		<tr class= "table_head_line"  >
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
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  		<tbody align= "center">
			  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr class='table_head_line'   ondblclick='toUpdate("${power.id}")'>
				  		<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
                        <td class="td_cs">${status.index+1}</td> 
			  			<td class="td_cs">${power.warehouse_name}</td>
			  			<td class="td_cs">${power.location_type}</td>
			  			<td class="td_cs">${power.reservoir_code}</td>
			  			<td class="td_cs">${power.passageway_code}</td>
			  			<td class="td_cs">${power.group_code}</td>
			  			<td class="td_cs">${power.line_code}</td>
			  			<td class="td_cs">${power.cell_code}</td>
			  			<td class="td_cs">${power.single_square}</td>
			  			<td class="td_cs">${power.single_volumn}</td>
			  			<td class="td_cs">${power.all_square}</td>
			  			<td class="td_cs">${power.storage_number}</td>
			  			</tr>	
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		
		<!-- 分页添加 -->
     <div id='botton_page' style="margin-right: 30px;margin-top:20px;">
<div style="margin-right: 30px;margin-top:20px;">${pageView.pageView}</div>	
      	 </div>	
	</body>
	<script type="text/javascript">
		$(document).ready(function() {
		
			
		});
		
		
		function pageJumpZ(){
			 var param='';
			 param=$('#main_search').serialize();

		 	$.ajax({
					type: "POST",
		           	url:'${root}/control/expressReturnStorageController/query.do?'+param,
		           	dataType: "text",
		           	data:'',
		    		cache:false,
		    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		           	success: function (data){
		              $("#page_view").html(data);
		           //   $("#load_load").css("display","none");
		           	}
			  	});  
			
			
		}
		
		function pageJump() {
			 var param='';
			 param=$('#main_search').serialize();
			// alert(param+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
		 	$.ajax({
					type: "POST",
		           	url:'${root}/control/storageLocationController/pageQuery.do?'+param+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val(),
		           	dataType: "text",
		           	data:'',
		    		cache:false,
		    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		           	success: function (data){
		           	  cancelLoadingStyle();
		              $("#page_view").html(data);
		           //   $("#load_load").css("display","none");
		           	},
		    		error:function(data){
		    			cancelLoadingStyle();
		    		}
			  	});  
			  	
			  	//$.post('${root}/control/expressReturnStorageController/query.do?storeCode=tst&warehouseCode=erreer',function(){});
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
