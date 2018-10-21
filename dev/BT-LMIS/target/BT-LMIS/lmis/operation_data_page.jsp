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
		
<div id="page_view">
		<div class= "title_div" id= "sc_title">
			<table class= "table table-striped" style= "table-layout: fixed;overflow-x:show;" >
		   		<thead>
			  		<tr class= "table_head_line"  >
                             <td class="td_cs">序号</td> 
			  			    <td class="td_cs">操作时间</td>
			  			    <td class="td_cs">订单类型</td>
			  			    <td class="td_cs">店铺</td>
			  			    <td class="td_cs">仓库</td>
			  			    <td class="td_cs">wms单号</td>
			  			    <td class="td_cs">前置单据号</td>
			  			    <td class="td_cs">平台订单号</td>
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
			  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr>
			  			<td class= "td_cs" nowrap="nowrap">${status.count }</td>
			  			<td class= "td_cs" nowrap="nowrap" title='${power.operation_time}'>${power.operation_time}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.order_type}</td>	
			  			<td class= "td_cs" nowrap="nowrap">${power.store_name} </td>
			  			<td class= "td_cs" nowrap="nowrap">${power.warehouse_name}</td>
			  			
			  			<td class= "td_cs" nowrap="nowrap"> ${power.related_orderno}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.epistatic_order}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.platform_order}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.job_type}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.in_num}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.out_num}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.item_number}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.sku_number}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.art_no}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.item_name}</td>
	  			</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
     <div id='botton_page' style="margin-right: 30px;margin-top:20px;">
<div style="margin-right: 30px;margin-top:20px;">${pageView.pageView}</div>	
      	 </div>
      	 </div> 		
	</body>
	<script type="text/javascript">
	  
		$(document).ready(function() {
			if('${queryParam.transport_code}'==''){
				$("#producttype_code").next().attr("disabled", "disabled");
		 }
			 $("#transport_code").bind("change",function(){
					ExpressCodeChange("transport_code","itemtype_code","");
				 
			 });
			
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
						alert(result);
					}
				});
			}
		}
		
		
		
		function pageJump() {
			 var param='';
			 loadingStyle();
			 param=$('#main_search').serialize();
			// alert(param+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
		 	$.ajax({
					type: "POST",
		           	url:'${root}/control/OperationfeeDataDetailController/page.do?'+param+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val(),
		           	dataType: "text",
		           	data:'',
		    		cache:false,
		    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		           	success: function (data){
		              $("#page_view").html(data);
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
