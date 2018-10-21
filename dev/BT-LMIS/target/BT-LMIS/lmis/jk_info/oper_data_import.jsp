<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<title>Packaged Composite System</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	</head>
	<body>
		
<div class="page-header">
			<div style="margin-left: 20px;">
							<button id="imps" name="imps" class="btn btn-xs btn-yellow" onclick="exportData();">
								<i class="icon-hdd"></i>导出
							</button>
						<button class="btn btn-xs btn-yellow" onclick="back()">
							<i class="icon-download"></i>返回
						</button>		
			</div>
		</div>


<div  class="title_div" id="sc_title">		
<table class="table table-striped" style="table-layout: fixed;">
	<thead>
	  		<tr class= "table_head_line" >
			    <td class="td_ch"></td>
			    <td class="td_cs">店铺名称</td>	
			    <td class="td_cs">仓库名称</td>			
			  	<td class="td_cs">作业单号</td>			  		    
			  	<td class="td_cs">相关单号</td>
			  	<td class="td_cs">作业类型名称</td>
			  	<td class="td_cs">商品编码</td>
			  	<td class="td_cs">商品名称</td>
			  	<td class="td_cs">是否为耗材</td>
			  	<td class="td_cs">品牌对接编码</td>
			  	<td class="td_cs">条形码</td>
			  	<td class="td_cs">转换标记</td>
			</tr>
	</thead>
</table>
</div>		  		
<div class="tree_div"></div>


<div class="content_div" id="sc_content" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">	 		
		  		<tbody>
		  			<c:forEach items="${pageView.records}" var="list">
			  		<tr ondblclick="detail('${list.bat_id }')"> 
			  		    <td class="td_ch"><input id="ckb"  name="ckb" type="checkbox" value= "${list.bat_id }" ></td>
			  		    <td class="td_cs">${list.store_name}</td>
			  		    <td class="td_cs">${list.warehouse_name}</td>
			  			<td class="td_cs">${list.job_orderno}</td>
			  			<td class="td_cs">${list.related_orderno}</td>
			  			<td class="td_cs">${list.job_type}</td>
			  			<td class="td_cs">${list.sku_number}</td>
			  			<td class="td_cs">${list.item_name}</td>
			  			<td class="td_cs">${list.is_consumable_name}</td>
			  			<td class="td_cs">${list.brand_docking_code}</td>
			  			<td class="td_cs">${list.barcode}</td>
			  			<td class="td_cs">${list.pro_remark}</td>
			  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>
		<input value="${queryParam.bat_id}" type="hidden"  id="bat_id_param"/>
	</body>
	
	<script type="text/javascript">
	function back(){
		openDiv( root+"control/JkErrorController/import_main.do");
	}

	 function exportData(){
	    var url=root+"control/JkErrorController/exportData.do";
		window.location.href=url;
	 }
	  	function pageJump() {
	  		 openDiv(root+'control/JkErrorController/toUpload.do?bat_id='+$("#bat_id_param").val()+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	  	}
	</script>
</html>
