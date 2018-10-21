<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@ include file="/lmis/yuriy.jsp"%>
<link type="text/css" href="<%=basePath %>css/table.css"
	rel="stylesheet" />
<link type="text/css"
	href="<%=basePath %>daterangepicker/font-awesome.min.css"
	rel="stylesheet" />
<style type="text/css">
label {
	margin-right: 15px;
	font-size: 14px;
}
</style>
</head>
<body>
	<input id="lastPage" name="lastPage" style='display: none'
		value='${queryParam.maxResult}'>
	<input id="lastTotalCount" name="lastTotalCount" style='display: none'
		value='${totalSize}'>
	<div
		style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px; margin-top: 22px;">
		<table id='table_content' class="table table-striped">
			<thead align="center">
				<tr>
					<td style='width: 50px'>
						<!-- <input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> -->
					</td>
					<td class="td_cs">承运商</td>
					<td class="td_cs">合同名称</td>
					<td class="td_cs">账单周期</td>
					<td class="td_cs">账单名称</td>
					<td class="td_cs">备注</td>
					<td class="td_cs">账单状态</td>
					<td class="td_cs">操作</td>
				</tr>
			</thead>
			<tbody id='tbody' align="center">
				<c:forEach items="${pageView.records}" var="power"
					varStatus='status'>
					<tr>
						<td class="td_ch"><input id="ckb" name="ckb" type="checkbox"
							value="${power.id}"></td>
						<td class="td_cs">${power.express_name}</td>
						<td class="td_cs">${power.contract_name}</td>
						<td class="td_cs">${power.billingCycle}</td>
						<td class="td_cs">${power.bill_name}</td>
						<td class="td_cs">${power.remarks }</td>
						<td class="td_cs"><c:if test='${power.status==0}'>
			  			 已关账
			  			</c:if> <c:if test='${power.status==1}'>
			  			 未关账
			  			</c:if> 
			  			<c:if test='${power.status==4 }'>
			  			 转换失败
			  			</c:if>
			  			<c:if test='${power.status==2 }'>
			  			 处理中
			  			</c:if>
			  			<c:if test="${power.status==8}">
						 处理中
						</c:if>
			  			</td>
			  			<td>
			  			<c:if test="${power.status==1}">
			  			<a id='hx' class="btn btn-xs btn-grey"
						href='javascript:closeAccount(${power.id})'>关账</a></c:if>
			  			</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- 分页添加 -->
	<div style="margin-right: 5%; margin-top: 20px; margin-bottom: 10%;">${pageView.pageView}</div>
	<!-- 分页添加 -->
</body>
<script type="text/javascript">
	  
	$("#table_content tbody tr").dblclick(function(){
		var  tr=$(this);
		$(window).scrollTop(0);
		loadingStyle();
		openDiv('${root}control/lmis/expressbillMasterController/tablist.do?id='+tr.find('input[name="ckb"]').val());
		cancelLoadingStyle();
	});
	
	
	function pageJump() {
		loadingStyle();
		 var param='';
		 loadingStyle();
		 param=$('#main_search').serialize();
		// alert(param+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	 	$.ajax({
				type: "POST",
	           	url:'${root}/control/lmis/expressbillMasterController/pageQuery.do?'+param+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val(),
	           	dataType: "text",
	           	data:'',
	    		cache:false,
	    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
	           	success: function (data){
	           		cancelLoadingStyle();
	              $("#page_view").html(data);
	              cancelLoadingStyle();
	           	}
		  	});  
		  	
		  	//$.post('${root}/control/expressReturnStorageController/query.do?storeCode=tst&warehouseCode=erreer',function(){});
	    }
	
	function closeAccount(id){
		$(window).scrollTop(0);
		loadingStyle();
		$.post('${root}control/lmis/expressbillMasterController/close.do?id='+id,function(data){
			
			cancelLoadingStyle();
			
			if(data.code==1){
				alert('操作成功！！！');
				pageJump();
			}else{
				alert('操作失败！！！');
				
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

.modal-header {
	height: 50px;
}
</style>
