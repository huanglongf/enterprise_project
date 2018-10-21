<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/lmis/yuriy.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id= "data" class= "form-group" >
<div  class="title_div" id="sc_title">		
<table class="table table-striped" style="table-layout: fixed;">
	<thead>
	  		<tr class= "table_head_line" >
<!-- 			    <td class="td_ch"></td> -->
			    <td class="td_cs">运单号</td>	
			    <td class="td_cs">结果</td>			
			  	<td class="td_cs">批次号</td>			  		    
			  	<td class="td_cs">店铺名称</td>
			  	<td class="td_cs">仓库名称</td>
			  	<td class="td_cs">快递代码</td>
			  	<td class="td_cs">快递名称</td>
			  	<td class="td_cs">平台订单号</td>
			  	<td class="td_cs">始发地</td>
			  	<td class="td_cs">目的地省份</td>
			  	<td class="td_cs">目的地城市</td>
			  	<td class="td_cs">目的地区县</td>
			</tr>
	</thead>
	
</table>
</div>		  		
<div class="tree_div"></div>


<div class="content_div" id="sc_content" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">	 		
		  		<tbody>
		  			<c:forEach items="${pageView.records}" var="list">
			  		<tr> 
<%-- 			  		    <td class="td_ch"><input id="ckb"  name="ckb" type="checkbox" value= "${list.bat_id }" ></td> --%>
			  		    <td class="td_cs">${list.express_number}</td>
			  		    <td class="td_cs">${list.pro_remark}</td>
			  			<td class="td_cs">${list.bat_id}</td>
			  			<td class="td_cs">${list.store_name}</td>
			  			<td class="td_cs">${list.warehouse_name}</td>
			  			<td class="td_cs">${list.transport_code}</td>
			  			<td class="td_cs">${list.transport_name}</td>
			  			<td class="td_cs">${list.delivery_address}</td>
			  			<td class="td_cs">${list.province}</td>
			  			<td class="td_cs">${list.city}</td>
			  			<td class="td_cs">${list.state}</td>
			  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style="margin-right: 5%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>
		<input value="${queryParam.bat_id}" type="hidden"  id="bat_id_param"/>
</div>
	