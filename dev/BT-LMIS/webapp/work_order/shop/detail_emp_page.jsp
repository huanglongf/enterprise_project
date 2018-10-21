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
	</head>
<div  class="title_div" id="sc_title">		
<table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr>
			  			<td class="td_cs">成员名称</td>
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
			  			<td class="td_cs" title="${res.name}">${res.name}</td>
			  		</tr>
		  		</c:forEach>
		 </tbody>
</table>
<div style="margin-right: 10%;margin-top:10px;margin-bottom: 10%;">${pageView.pageView}</div>		
</div>
