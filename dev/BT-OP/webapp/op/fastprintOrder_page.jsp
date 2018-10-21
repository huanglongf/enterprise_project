<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/base/yuriy.jsp"%>
<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
<script type='text/javascript'>
function fastprintbyId(id) {
	$.post('${root}/control/orderPlatform/waybillMasterController/confirmfastprintbyId.do?id='+id,
		function(data){
            if(data.data==1){
            	if(data.code==4){
            		alert("暂时无法接单，请稍后再试！");
            	}else if(data.code==5){
            		 var result1=  	confirm('该运单已打印，是否打印！');
	           		 if(result1){
	           			//window.open('${root }/control/orderPlatform/waybillMasterController/print.do?ids='+data.id,'_blank','height=680,width=400,status=yes,toolbar=yes,fullscreen=no, menubar=no,location=no'); 
	           			 $.post('${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='+idsStr,
	     	            			function(data){
	     	            				 if(data.out_result==1){
	     	            					 window.open(root + data.path);
	     	            				 }else if(data.out_result==2){
	     	            					 alert("操作失败!");
	     	            				 }
	     	            			});
	           		 }
                }else{
                	//window.open('${root }/control/orderPlatform/waybillMasterController/print.do?ids='+data.id,'_blank','height=680,width=400,status=yes,toolbar=yes,fullscreen=no, menubar=no,location=no');
           		 $.post('${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='+idsStr,
	            			function(data){
	            				 if(data.out_result==1){
	            					 window.open(root + data.path);
	            				 }else if(data.out_result==2){
	            					 alert("操作失败!");
	            				 }
	            			});
                }
            }else if(data.data==2){
            	alert("操作失败！");
            }else if(data.data==8){
            	alert("下单失败！");
            }
	});  
	}
</script>
<body>
	<!-- <div class= "modal-dialog modal-lg" role= "document" > -->
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" style='height:50px' >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >运单信息列表</h4>
					</div>
					<div class= "modal-body" >
		 <form id="updateForm">			
			<table class="table table-striped" >
		   		<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td>序号</td>
			  			<td>客户单号</td>
			  			<td>状态</td>
			  			<td>备注</td>
			  			<td>收货人</td>
			  			<td>收货联系电话</td>
			  			<td style="width:200px;">收货地址</td>
			  		</tr>  	
		  		</thead>
		  		<tbody  align="center">
		  		<c:forEach items="${list}" var="power" varStatus='status'>
			  		<tr ondblclick='fastprintbyId("${power.id}")'>
			  			<td  title="${status.count }">${status.count }</td>
			  			<td title="${power.customer_number }">
			  			<div style="width:10px;text-overflow: ellipsis; white-space: nowrap; overflow: hidden; ">
			  				${power.customer_number}
			  			</div>
			  			</td>
			  			<td >
							<c:if test="${power.status=='1'}">已录单</c:if>
   							<c:if test="${power.status=='0'}">已作废</c:if>
   							<c:if test="${power.status=='2'}">待揽收</c:if>
   							<c:if test="${power.status=='4'}">已下单</c:if>
   							<c:if test="${power.status=='5'}">已揽收</c:if>
   							<c:if test="${power.status=='6'}">已发运</c:if>
   							<c:if test="${power.status=='7'}">已签收</c:if>
   							<c:if test="${power.status=='8'}">签收失败</c:if>
   							<c:if test="${power.status=='9'}">已退回</c:if>
   							<c:if test="${power.status=='10'}">已取消</c:if>
   							<c:if test="${power.status=='11'}">下单失败</c:if>
						</td>
			  			<td title="${power.memo }">
			  			<div style="width:30px;text-overflow: ellipsis; white-space: nowrap; overflow: hidden; ">
			  				${power.memo }
			  			</div>
			  			</td>
			  			<td title="${power.to_contacts}">${power.to_contacts }</td>
			  			<td title="${power.to_phone  }">${power.to_phone }</td>
			  			<td title="${power.to_address }" >
			  			<div style="width:200px;text-overflow: ellipsis; white-space: nowrap; overflow: hidden; ">
			  				${power.to_address }
			  			</div>
			  			</td>
			  			
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
			</form>
				</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default" data-dismiss= "modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
					</div>
				</div>
			<!-- </div> -->
</body>
</html>
<script>
</script>
<style>
.select {
	padding: 3px 4px;
	height: 30px;
	width: 160px;
	text-align: enter;
}

.title_div td {
	font-size: 15px;
}

.m-input-select {
	display: inline-block;
	position: relative;
	-webkit-user-select: none;

}
</style>
