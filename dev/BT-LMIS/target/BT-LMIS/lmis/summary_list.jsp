<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
	</head>
	<body>
		<div style="height: 400px; width: 100%; overflow: auto; overflow:scroll; border: solid #F2F2F2 1px;">
			<table class="table table-striped table-hover">
		   		<thead>
			  		<tr class="table_head_line">
			  			<td colspan="13" style="text-align: center;"><h1>客户&店铺 结算汇总</h1> </td>
			  		</tr>
			  		<tr class="table_head_line">
			  			<td><h5></h5></td>
			  			<td><h5>1月</h5></td>
			  			<td><h5>2月</h5></td>
			  			<td><h5>3月</h5></td>
			  			<td><h5>4月</h5></td>
			  			<td><h5>5月</h5></td>
			  			<td><h5>6月</h5></td>
			  			<td><h5>7月</h5></td>
			  			<td><h5>8月</h5></td>
			  			<td><h5>9月</h5></td>
			  			<td><h5>10月</h5></td>
			  			<td><h5>11月</h5></td>
			  			<td><h5>12月</h5></td>
			  		</tr>
		  		</thead>
		  		<tbody>
		  			<c:forEach items="${years}" var="years">
			  		
			  		<tr class="table_head_line">
		  				<td>${years.years}</td>
			  			<td><a href="javascript:void(0);" onclick="atc('${years.years}-1')">客户</a>｜<a href="javascript:void(0);" onclick="ats('${years.years}-1')">店铺</a></td>
		              	<td><a href="javascript:void(0);" onclick="atc('${years.years}-2')">客户</a>｜<a href="javascript:void(0);" onclick="ats('${years.years}-2')">店铺</a></td>
		             	<td><a href="javascript:void(0);" onclick="atc('${years.years}-3')">客户</a>｜<a href="javascript:void(0);" onclick="ats('${years.years}-3')">店铺</a></td>
		           	   	<td><a href="javascript:void(0);" onclick="atc('${years.years}-4')">客户</a>｜<a href="javascript:void(0);" onclick="ats('${years.years}-4')">店铺</a></td>
		           	  	<td><a href="javascript:void(0);" onclick="atc('${years.years}-5')">客户</a>｜<a href="javascript:void(0);" onclick="ats('${years.years}-5')">店铺</a></td>
						<td><a href="javascript:void(0);" onclick="atc('${years.years}-6')">客户</a>｜<a href="javascript:void(0);" onclick="ats('${years.years}-6')">店铺</a></td>
						<td><a href="javascript:void(0);" onclick="atc('${years.years}-7')">客户</a>｜<a href="javascript:void(0);" onclick="ats('${years.years}-7')">店铺</a></td>
						<td><a href="javascript:void(0);" onclick="atc('${years.years}-8')">客户</a>｜<a href="javascript:void(0);" onclick="ats('${years.years}-8')">店铺</a></td>
						<td><a href="javascript:void(0);" onclick="atc('${years.years}-9')">客户</a>｜<a href="javascript:void(0);" onclick="ats('${years.years}-9')">店铺</a></td>
						<td><a href="javascript:void(0);" onclick="atc('${years.years}-10')">客户</a>｜<a href="javascript:void(0);" onclick="ats('${years.years}-10')">店铺</a></td>
						<td><a href="javascript:void(0);" onclick="atc('${years.years}-11')">客户</a>｜<a href="javascript:void(0);" onclick="ats('${years.years}-11')">店铺</a></td>
						<td><a href="javascript:void(0);" onclick="atc('${years.years}-12')">客户</a>｜<a href="javascript:void(0);" onclick="ats('${years.years}-12')">店铺</a></td>
			  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<script type="text/javascript">
			function pageJump() {
			 	var data=$("#serarch_td").serialize();
		 		openDiv('${root}control/summaryController/list.do?data='+data+'startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
			};
		</script>
	</body>
	<script type="text/javascript">
	  	/**
	   	* 查询
	   	*/
	  	function atc(ym){
	  		openDiv("${root}control/summaryController/lists.do?ym="+ym);
	  	}
	</script>
</html>
