<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	</head>
	
	<body>
		<div class="page-header">
		</div>	
		<from id="search_td">
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
			<table>
		  		<tr>
		  			<td width="10%">店铺名称:</td>
		  			<td width="20%"><input id="storeName" name="name" type="text" value="${queryParam.storeName}" /></td>
		  			<td width="10%">运输名称:</td>
		  			<td width="20%"><input id="transportName" name="transportName" type="text"  value="${queryParam.transportName}"/></td>
		  		</tr>
			</table>
		</div>
		</from>
		<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find()">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			&nbsp;&nbsp;
<%-- 			<button class="btn btn-xs btn-yellow" onclick="openDiv('${root}control/shopController/toForm.do');"> --%>
<!-- 				<i class="icon-hdd"></i>新增 -->
<!-- 			</button> -->
<!-- 			&nbsp; -->
<!-- 			<button class="btn btn-xs btn-primary" onclick="up();"> -->
<!-- 				<i class="icon-edit"></i>修改 -->
<!-- 			</button> -->
			&nbsp;
			<button class="btn btn-xs btn-inverse" onclick="del('${employeeid}','${ret}');">
				<i class="icon-trash"></i>删除
			</button
			>&nbsp;
		</div>
		<div style="height:400px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;margin-top: 22px;">
			<table class="table table-striped">
		   		<thead  align="center">
			  		<tr>
			  			<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td>运输代码</td>
			  			<td>运输名称</td>
			  			<td>运输类别</td>
			  			<td>订单日期</td>
			  			<td>运单号</td>
			  			<td>店铺代码</td>
			  			<td>店铺名称</td>
			  			<td>始发地</td>
			  			<td>目的地</td>
			  			<td>送货件数</td>
			  			<td>实际重量(kg)</td>
			  			<td>体积立方(m3)</td>
			  			<td>订单金额</td>
			
			  		</tr>  	
			  		<tr>
			  		     <td></td>
			  		     <% for(int i=1;i<=13;i++){ %>
<%-- 			  	<c:forEach items="${power}" var="power"> --%>
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
<%-- 		  		</c:forEach> --%>
			  		</tr>  				  			
		  		</thead>
		  		
		  		
		  		<tbody  align="center">
		  		<c:forEach items="${pageView.records}" var="res">
			  		<tr>
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${res.id}"></td>
			  			<td>${res.transport_code}</td>
			  			<td nowrap="nowrap">${res.transport_name}</td>
			  			<td nowrap="nowrap">${res.transport_type}</td>
			  			<td>${res.bookbus_time}</td>
			  			<td>${res.expressno}</td>
			  			<td>${res.store_code}</td>
			  			<td>${res.store_name}</td>
			  			<td nowrap="nowrap">${res.originating_place}</td>
			  			<td nowrap="nowrap">${res.privince_name}/${res.city_name}/${res.state_name}/${res.street_name}/${res.street_name}/${res.delivery_address}</td>
			  			<td>${res.delivery_number}</td>
			  			<td>${res.real_weight}</td>
			  			<td>${res.volumn_cubic}</td>
			  			<td>${res.order_price}</td>
			  		</tr>
		  		</c:forEach>
		 
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
      <div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>		
	</body>
	
	<script type='text/javascript'>
	function pageJump() {
		var data=$("#search_td").serialize();
		openDiv('${root}control/transOrderController/queryRawData.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	}

	function find() {
		var storeName = $("#storeName").val();
		var transportName = $("#transportName").val();
		openDiv('${root}control/transOrderController/queryRawData.do?storeName=' + storeName + '&transportName=' + transportName);
	}
	
    </script>
</html>
