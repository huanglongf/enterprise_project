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
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
<%-- 		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script> --%>
<%-- 		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> --%>
	</head>
<div  class="title_div" id="sc_title">		
<table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr>
<!-- 			  			<td class="td_ch"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/></td> -->
			  			<td class="td_cs">店铺</td>
			  			<td class="td_cs">自营仓</td>
			  			<td class="td_cs">外包仓</td>
			  			<td class="td_cs">操作</td>
			  		</tr>  	
		  		</thead>

</table>
</div>
<div class="tree_div"></div>

<div style="border:solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">
		 <tbody  align="center">
		  		<c:forEach items="${pageView.records}" var="res">
			  		<tr>
<%-- 			  			<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${res.id}"></td> --%>
			  			<td class="td_cs" title="${res.store_name}">${res.store_name}</td>
			  			<td class="td_cs" title="${res.selfwarehouse}">${res.selfwarehouse}</td>
			  			<td class="td_cs">${res.outsourcedwarehouse}</td>
			  			<td class="td_cs"><button class="btn btn-xs btn-info" onclick="up_tab_store('${res.id}','${res.store}','${res.outsourcedwarehouse}','${res.selfwarehouse}','${res.group}');">修改</button>|
			  			                 <button class="btn btn-xs btn-info" onclick="del_tab_store('${res.id}');">删除</button></td>
			  		</tr>
		  		</c:forEach>
		 </tbody>
</table>
<div style="margin-right: 10%;margin-top:10px;margin-bottom: 10%;">${pageView.pageView}</div>		
</div>
