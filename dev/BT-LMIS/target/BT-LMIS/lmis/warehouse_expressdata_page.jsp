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
                       <input id="lastPage" name="lastPage"  style='display:none' value='${queryParam.maxResult}' >
		  			   <input id="lastTotalCount" name="lastTotalCount"  style='display:none' value='${totalSize}'>
		<div style="height:400px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;margin-top: 22px;">
			<table class="table table-striped">
		   		<thead  align="center">
			  		<tr>
			  			<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td >序号</td>
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
		  		</thead>
		  		<tbody id='tbody' align="center">
		  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr>
			  			<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
                        <td class="td_cs">${status.index+1}</td> 
			  			<td class="td_cs">${power.express_number} </td>
			  			<td class="td_cs">${power.transport_name} </td>
			  			<td class="td_cs">${power.itemtype_name}</td>
			  			<td class="td_cs">${power.cost_center}</td>
			  			<td class="td_cs">${power.store_name} </td>
			  			<td class="td_cs">${power.warehouse}</td>
			  			<td class="td_cs">${power.weight} </td>
			  			<td class="td_cs">${power.length} </td>
			  			<td class="td_cs">${power.width}</td>
			  			<td class="td_cs">${power.higth}</td>
			  			<td class="td_cs">${power.volumn}</td>
			  			<td class="td_cs">${power.epistatic_order}</td>
			  			<td class="td_cs">${power.delivery_order }</td>
			  			<td class="td_cs">${power.order_amount }</td>
			  			<td class="td_cs">${power.collection_on_delivery }</td>
			  			<td class="td_cs">${power.order_type }</td>
			  			<td class="td_cs">
			  			<fmt:formatDate value="${power.transport_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
			  			<td class="td_cs">${power.delivery_address }</td>
			  			<td class="td_cs">${power.province }</td>
			  			<td class="td_cs">${power.city }</td>
			  			<td class="td_cs">${power.state }</td>
			  			<td class="td_cs">
			  			<c:if test="${power.insurance_flag==0}">否</c:if>
			  			<c:if test="${power.insurance_flag==1}">是</c:if>
			  			</td>
			  			<td class="td_cs">
			  			<c:if test="${power.cod_flag==0}">否</c:if>
			  			<c:if test="${power.cod_flag==1}">是</c:if>
			  			</td>
			  			<td class="td_cs">${power.sku_number }</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
      <div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>
		<!-- 分页添加 -->
	</body>
	<script type="text/javascript">
	  
	function pageJump() {
		 var param='';
		 loadingStyle();
		 param=$('#main_search').serialize();
		 param+='&lastPage='+$('#lastPage').val()+'&lastTotalCount='+$('#lastTotalCount').val();
		// alert(param+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	 	$.ajax({
				type: "POST",
	           	url:'${root}/control/warehouseExpressDataController/pageQuery.do?'+param+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val(),
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
