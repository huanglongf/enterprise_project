<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<script type="text/javascript" src="<%=basePath %>validator/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/table.css" />
	</head>
	<body>
		<div class="page-header">
		</div>
		<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="pageJump();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
		</div>
		
		
<div  class="title_div" id="sc_title">		
<table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr>
			  			<td class="td_ch"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/></td>
			  			<td class="td_cs">运单号</td>
			  			<td class="td_cs">店铺编码</td>
			  			<td class="td_cs">仓库编码</td>
			  			<td class="td_cs">仓库名称</td>
			  			<td class="td_cs">承运商代码</td>
			  			<td class="td_cs">承运商名称</td>
			  			<td class="td_cs">目的地省</td>
			  			<td class="td_cs">目的地市</td>
			  			<td class="td_cs">长</td>
			  			<td class="td_cs">宽</td>
			  			<td class="td_cs">高</td>
			  			<td class="td_cs">错误信息</td>
			  			<td class="td_cs">操作</td>
			  		</tr>  	
		  		</thead>

</table>
</div>
<div class="tree_div"></div>

<div style="height:400px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">
		  		<tbody  align="center">
		  		<c:forEach items="${pageView.records}" var="res">
			  		<tr>
			  			<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${res.id}"></td>
			  			<td class="td_cs">${res.express_number}</td>
			  			<td class="td_cs" title="">${res.store_code}</td>
			  			<td class="td_cs">${res.warehouse_code}</td>
			  			<td class="td_cs">${res.warehouse_name}</td>
			  			<td class="td_cs">${res.transport_code}</td>
			  			<td class="td_cs">${res.transport_name}</td>
			  			<td class="td_cs">${res.province}</td>
			  			<td class="td_cs">${res.city}</td>
			  			<td class="td_cs">${res.itemtype_code}</td>
			  			<td class="td_cs">${res.itemtype_name}</td>
			  			<td class="td_cs">${res.province}</td>
			  			<td class="td_cs">${res.city}</td>
			  			<td class="td_cs">${res.state}</td>
			  			<td class="td_cs">
			  			<button class="btn btn-xs btn-info" onclick="upStatus('${res.id}','0');">重新转入</button>
			  			</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
</table>
</div>
		<!-- 分页添加 -->
      	<div style="margin-right: 10%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>		
	</body>
	
	<script type="text/javascript">

		function upStatus(id,status){
			var url=root+"/control/JkErrorController/updateErrornData.do?id="+id;
		    $.ajax({
				type : "POST",
				url: url,  
				data:"",
				dataType: "json",  
				success : function(jsonStr) {
					alert(jsonStr.out_result_reason);
					pageJump();
				}
			});
		}
		
		
		 
		 function pageJump() {
			 var data=$("#search_form").serialize();
			 openDiv('/BT-LMIS/control/JkErrorController/errorlist.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	 }		 
	</script>
</html>
