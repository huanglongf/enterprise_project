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
		  		<tbody id='tbody' align="center">
		  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr>
			  			<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
                        <td class="td_cs">${status.index+1}</td> 
			  			<td class="td_cs">${power.express_number} </td>
			  			<td class="td_cs">${power.transport_name} </td>
			  			<td class="td_cs">${power.itemtype_name}</td>
			  			<td class="td_cs">${power.weight} </td>
			  			<td class="td_cs">${power.length} </td>
			  			<td class="td_cs">${power.width}</td>
			  			<td class="td_cs">${power.higth}</td>
			  			<td class="td_cs">${power.volumn}</td>
			  			<td class="td_cs">${power.standard_freight}</td>
			  			<td class="td_cs">
			  			<c:if test="${power.insurance_flag==false}">否</c:if>
			  			<c:if test="${power.insurance_flag==true}">是</c:if>
			  			</td>
			  			<td class="td_cs">${power.insurance_fee }</td>
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
	           	url:'${root}/control/WarehouseExpressDataSettleController/pageQuery.do?'+param+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val(),
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
