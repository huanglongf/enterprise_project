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
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
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
			  			<td class="td_cs">物流商</td>
			  			<td class="td_cs">产品类型</td>
			  			<td class="td_cs">系统仓</td>
			  			<td class="td_cs">店铺名称</td>
			  			<td class="td_cs">运单号</td>
			  			<td class="td_cs">退货相关单号</td>
			  			<td class="td_cs">作业单创建时间</td>
			  			<td class="td_cs">作业单完成时间</td>
			  			<td class="td_cs">计划执行量</td>
			  			<td class="td_cs">实际执行量</td>
			  			<td class="td_cs">登记物理仓名称</td>
			  			<td class="td_cs">创建人</td>
			  			<td class="td_cs">解锁人</td>
			  			<td class="td_cs">备注</td>
			  			<td class="td_cs">出库前置单据号</td>
			  			<td class="td_cs">出库目的省</td>
			  			<td class="td_cs">出库目的市</td>
			  			<td class="td_cs">出库目的区县</td>
			  			<td class="td_cs">出库重量</td>
			  			<td class="td_cs">出库长</td>
			  			<td class="td_cs">出库宽</td>
			  			<td class="td_cs">出库高</td>
			  			<td class="td_cs">出库体积</td>
			  			<td class="td_cs">出库订单金额</td>
			  		</tr>  	
				</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  		<tbody align= "center">
			  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr >
			  			<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
                        <td class="td_cs">${status.index+1}</td> 
			  			<td class="td_cs">${power.transport_name} </td>
			  			<td class="td_cs">${power.itemtype_name}</td>
			  			<td class="td_cs">${power.warehouse_name}</td>
			  			<td class="td_cs">${power.store_name} </td>
			  			<td class="td_cs">${power.waybill} </td>
			  			<td class="td_cs">${power.related_no}</td>
			  			<td class="td_cs">${power.create_time}</td>
			  			<td class="td_cs">${power.finish_time}</td>
			  			<td class="td_cs">${power.plan_qty }</td>
			  			<td class="td_cs">${power.actual_qty }</td>
			  			<td class="td_cs">${power.physical_warehouse }</td>
			  			<td class="td_cs">${power.create_user }</td>
			  			<td class="td_cs">${power.deblock_user }</td>
			  			<td class="td_cs">${power.remark}</td>
			  			<td class="td_cs">${power.re_epistatic_order }</td>
			  			<td class="td_cs">${power.re_province }</td>
			  			<td class="td_cs">${power.re_city }</td>
			  			<td class="td_cs">${power.re_state }</td>
			  			<td class="td_cs">${power.re_weight}</td>
			  			<td class="td_cs">${power.re_length }</td>
			  			<td class="td_cs">${power.re_width }</td>
			  			<td class="td_cs">${power.re_higth }</td>
			  			<td class="td_cs">${power.re_volumn}</td>
			  			<td class="td_cs">${power.out_order} </td>
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
			if('${queryParam.transport_code}'==''){
				$("#producttype_code").next().attr("disabled", "disabled");
		 }
			 $("#transport_code").bind("change",function(){
					ExpressCodeChange("transport_code","producttype_code","");
				 
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
		
		 function  returnStorageQuery(){
			pageJumpZ();
			
		} 
		
		function pageJumpZ(){
			 var param='';
			 param=$('#main_search').serialize();
			 loadingStyle();
		 	$.ajax({
					type: "POST",
		           	url:'${root}/control/expressReturnStorageController/query.do?'+param,
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
		
		function pageJump() {
			 var param='';
			 loadingStyle();
			 param=$('#main_search').serialize();
			// alert(param+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
		 	$.ajax({
					type: "POST",
		           	url:'${root}/control/expressReturnStorageController/query.do?'+param+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val(),
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
