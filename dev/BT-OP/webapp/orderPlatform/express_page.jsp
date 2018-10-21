<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/base/yuriy.jsp" %>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<link href="<%=basePath %>/css/table.css" type="text/css" rel="stylesheet" />
	<body>
		<div style="height:400px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;margin-top: 22px;width:70%;margin-left:20px">
			<table class="table table-striped" overflow-x：show>
		   		<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td>序号</td>
			  			<td>快递公司名称</td>
			  			<td>联系人</td>
			  			<td>联系人手机</td>
			  			<td>是否需要上门打印面单</td>
			  		</tr>  	  			
		  		</thead>
		  		<tbody id='tbody' align="center">
		  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr ondblclick="toUpdate('${power.id}')">
			  			<td><input id="ckb" name="ckb" type="checkbox" value="${power.id}"></td>
			  			<td>${status.count}</td>
			  			<td>${power.express_name }</td>
			  			<td>${power.contacts }</td>
			  			<td>${power.phone }</td>
			  			<td ${power.is_docall }>
			  				<c:if test="${power.is_docall=='1'}">是</c:if>
		   					<c:if test="${power.is_docall=='0'}">否</c:if>
			  			</td>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
  <div style="margin-right:350px;margin-top:20px;">${pageView.pageView}</div>	    	 
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

