<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
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
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>	</head>
	
	<body>
		<div class="page-header">
		</div>	
		<form id="main_search">
		<!--  <div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">-->
			<table>
		  		<tr>
		  			<td width="6%" align="right"><label>品牌:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="client_code" name="client_code"  class='select' data-edit-select="1" >
						<option value=''>---请选择---</option>
						<c:forEach items="${clientList}" var = "client" >
							<option  value="${client.client_code}">${client.client_name}</option>
						</c:forEach>	
					</select>
		  			</td>
		  			<td width="6%" align="right"><label>店铺:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="store_code" name="store_code"  class='select' data-edit-select="1" >
						<option value=''>---请选择---</option>
						<c:forEach items="${stores}" var = "store" >
							<option  value="${store.store_code}">${store.store_name}</option>
						</c:forEach>	
					</select>
		  			</td>
		  			<td width="10%" align="right"><label>快递单号:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="express_number" name="express_number"  style='width:100%;height:34px'>
					</input>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td width="6%" align="right"><label>快递公司:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="transport_code" name="transport_code"  class='select' data-edit-select="1">
						<option value=''>---请选择---</option>
						<c:forEach items="${transportVenders}" var = "vender" >
							<option  value="${vender.transport_code}">${vender.transport_name}</option>
						</c:forEach>	
					</select>
		  			</td>
		  			<td width="6%" align="right"><label>产品类型:</label></td>
		  			<td width="20%" align="left">
		  			  <select id="itemtype_code"  name="itemtype_code" style="width: 60%;" class='select' data-edit-select="1">
		  			     <option value="">---请先选择快递公司---</option>
		  			  </select>
		  			</td>
		  			<td width="10%" align="right"><label>出库前置单据号:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="epistatic_order" name="epistatic_order"  style='width:100%;height:34px' />
					</input>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td width="6%" align="right"><label>出库时间起:</label></td>
		  			<td width="20%" align="left">
		  			    <input id= "start_express_time" name= "start_express_time" type="text"  style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
		  			</td>
		  			<td width="6%" align="right"><label>出库时间止:</label></td>
		  			<td width="20%" align="left">
		  			  <input id= "end_express_time" name= "end_express_time" type="text" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
		  			</td>
		  			<td width="10%" align="right"><label>平台订单号:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="delivery_order" name="delivery_order"  style='width:100%;height:34px'  />
		  			</td>
		  		</tr>
		  		<tr>
		  			<td width="6%" align="right"><label>是否COD:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="cod_flag" name="cod_flag"  class='select' data-edit-select="1">
						<option value=''>---请选择---</option>
							<option  value="0">否</option>
							<option  value="1">是</option>
					</select>
		  			</td>
		  			<td width="6%" align="right"><label>仓库:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="warehouse_code" name="warehouse_code"  class='select' data-edit-select="1">
						<option value=''>---请选择---</option>
						<c:forEach items="${warehouseList}" var = "warehouse" >
							<option  value="${warehouse.warehouse_code}">${warehouse.warehouse_name}</option>
						</c:forEach>	
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
			<button class="btn btn-xs btn-inverse" onclick="download();">
				<i class="icon-trash"></i>导出
			</button
			>&nbsp;
		</div>
		<div id="page_view">
		<div style="height:400px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;margin-top: 22px;">
			<table class="table table-striped">
		   		<thead  align="center">
			  		<tr>
			  			<td style='width:270px'><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td class="td_cs">运单号</td>
			  			<td class="td_cs">快递公司</td>
			  			<td class="td_cs">产品类型</td>
			  			<td class="td_cs">成本中心</td>
			  			<td class="td_cs">店铺</td>
			  			<td class="td_cs">系统仓</td>
			  			<td class="td_cs">重量</td>
			  			<td class="td_cs">长</td>
			  			<td class="td_cs">宽</td>
			  			<td class="td_cs">高</td>
			  			<td class="td_cs">体积</td>
			  			<td class="td_cs">前置单据号</td>
			  			<td class="td_cs">平台订单号</td>
			            <td class="td_cs">订单金额</td>
			            <td class="td_cs">代收货款金额</td>
			            <td class="td_cs">订单类型</td>
			            <td class="td_cs">出库时间</td>
			            <td class="td_cs">始发地</td>
			             <td class="td_cs">目的省</td>
			              <td class="td_cs">目的市</td>
			               <td class="td_cs">目的区</td>
			               <td class="td_cs">是否保价</td>
			               <td class="td_cs">是否COD</td>
			               <td class="td_cs">SKU_No</td>
			  		</tr>  	
		<%-- 		  		<tr>
			  		     <td></td>
			  		     <% for(int i=1;i<=13;i++){ %>
		  	<c:forEach items="${power}" var="power">
                        <td nowrap="nowrap">
						  <span class="input-icon input-icon-right">
						    <select style="text-align:center;">
						    <option value="0">≈</option>
						    <option value="1">=</option>
						    <option value="2">></option>
						    <option value="3">>=</option>
						    <option value="4"><</option>
						    <option value="5"><=</option>
						    </select>
							<input type="text" id="form-field-icon-2" style="width:80px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						 </span>
						</td>
						 <%} %>
     	  		</c:forEach> 
			  		</tr>--%>			  			
		  		</thead>
		  		<tbody  align="center">
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
      <div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>	
		</div>	
	</body>
	
	<script type='text/javascript'>
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
	           	url:'${root}/control/warehouseExpressDataController/pageQuery.do?'+param,
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
	
    </script>
    <style>
    .select {
    padding: 3px 4px;
    height: 36px;
    width: 230px;
    }
    
    </style>
    
</html>
