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
	<div class= "title_div" id= "sc_title">
			<table class= "table table-striped" style= "table-layout: fixed;">
			<thead>
			  		<tr class= "table_head_line">
					<td  class="td_ch" style='width: 50px'>
						<!-- <input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> -->
					</td>
					<td class="td_cs">序号</td>
							<td class="td_cs">导入时间</td>
							<td class="td_cs">导入批次号</td>
							<td class="td_cs">总数量</td>
							<td class="td_cs">成功数量</td>
							<td class="td_cs">失败数量</td>
							<td class="td_cs">状态</td>
							<td class="td_cs">错误原因下载</td>
				</tr>
			</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
			<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  		<tbody align= "center">
				<c:forEach items="${pageView.records}" var="power"
					varStatus='status'>
					<tr>
						<td class="td_ch"><input id="ckb111" name="ckb111" type="checkbox"
							value="${power.id}"></td>
						<td class="td_cs">${status.count}</td>
						<td class="td_cs"><fmt:formatDate
								value="${power.create_time}" type="both"
								pattern=" yyyy-MM-dd HH:mm:ss" /></td>
						<td class="td_cs">${power.batch_id}</td>
						<td class="td_cs">${power.total_num}</td>
						<td class="td_cs">${power.success_num}</td>
						<td class="td_cs">${power.fail_num }</td>
						<td class="td_cs"><c:if test="${power.status==0}">
						 未上传
						</c:if>
						<c:if test="${power.status==1}">
						 已上传
						</c:if>
						<c:if test="${power.status==4}">
						 转换失败
						</c:if>
						<c:if test="${power.status==3}">
						 已转换
						</c:if>
						</td>
						 <td><a  class="td_cs" class="btn btn-xs btn-grey"
						 onclick="uploade_diff_error('${power.batch_id}')">下载</a></td>
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
		
		openDiv('${root}control/lmis/expressbillMasterController/tablist.do?id='+tr.find('input[name="ckb"]').val());
		
	});
	
	
	function pageJump() {
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

.modal-header {
	height: 50px;
}
</style>
