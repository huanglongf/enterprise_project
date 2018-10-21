
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>快递信息查询</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
		<link href="<%=basePath %>My97DatePicker/skin/WdatePicker.css" rel="stylesheet">
		<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link href="<%=basePath %>/css/table.css" type="text/css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
<%-- <script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script> --%>
		<script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type='text/javascript'>
			function pageJump() {
				query();
			}
			function query(){
				  openDiv("/BT-LMIS//control/radar/ageingDetailBackupsController/query.do?startRow="
					  + $("#startRow").val()
					  + "&endRow="
					  + $("#startRow").val()
					  + "&page="
					  + $("#pageIndex").val()
					  + "&pageSize="
					  + $("#pageSize").val()
					  + "&batId="
					  + $("#batId").val());
			}
			

            
            
    	</script>
	</head>
	<body>
	
		<div class="page-header"><h1 style='margin-left:20px'>导入时效明细信息查询</h1></div>	
		<div style="margin-top: 10px;margin-left: 20px;margin-bottom: 10px;">
		<input id= "batId" type= "hidden" value= "${queryParam.batId }" />
		</div>
		
		<div id="page_view">
		<div class='title_div' style="height : 520px; overflow : auto; overflow : scroll; border : solid #F2F2F2 1px;"  >
			<table class="table table-striped" overflow-x：show>
		   		<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td><input style="width: 50px" type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td class='td_cs' style="width: 50px">序号</td>
			  			<td class='td_cs' style="width: 200px">仓库名称</td>
			  			<td class='td_cs' style="width: 200px">省</td>
			  			<td class='td_cs' style="width: 200px">市</td>
			  			<td class='td_cs' style="width: 200px">区</td>
			  			<td class='td_cs' style="width: 200px">产品类型名称</td>
			  			<td class='td_cs' style="width: 200px">物流商名称</td>
			  			<td class='td_cs' style="width: 200px">揽件截止时间</td>
			  			<td class='td_cs' style="width: 200px">时效值/天</td>
			  			<td class='td_cs' style="width: 200px">错误原因</td>
			  		</tr>  	
		  		</thead>
		  		<tbody id='tbody' align="center">
		  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr  >
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
			  			<td nowrap="nowrap">${status.count }</td>
			  			<td nowrap="nowrap">${power.warehouseName}</td>
			  			<td nowrap="nowrap">${power.pName}</td>
			  			<td nowrap="nowrap">${power.cName}</td>
			  			<td nowrap="nowrap">${power.sName}</td>
			  			<td nowrap="nowrap">${power.productTypeName}</td>
			  			<td nowrap="nowrap">${power.expressName}</td>
			  			<td nowrap="nowrap">${power.embranceCalTime}</td>
			  			<td nowrap="nowrap">${power.ageingValue}</td>
			  			<td nowrap="nowrap" title="${power.error}">${power.error}</td>
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
</html>
<style>
.select {
    padding: 3px 4px;
    height: 30px;
    width: 160px;
   text-align: enter;
}
.title_div td{
	font-size: 15px;
}

.m-input-select {
    display: inline-block;
    position: relative;
    -webkit-user-select: none;
    width: 160px;
    }
</style>

