<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="title_div" id="sc_title2">	
	<table class="table table-striped" style="table-layout:fixed;">
   		<thead>
	  		<tr class="table_head_line">
	  			<td class="td_cs" title="路由时间" style="width:20%;">路由时间</td>
	  			<td class="td_cs" title="条形码" style="width:20%;">路由状态代码</td>
	  			<td class="td_cs" title="路由状态" style="width:20%;">路由状态</td>
	  			<td class="td_cs" title="描述" style="width:40%;">描述</td>
	  		</tr>
		</thead>
	</table>
</div>
<div class="tree_div"></div>
<div style="height:323px;overflow:auto;overflow:scroll;border:solid #F2F2F2 1px;" id="sc_content2" onscroll="init_td('sc_title2', 'sc_content2')">
	<table class="table table-hover table-striped" style="table-layout: fixed;">
  		<tbody align="center">
	  		<c:forEach items="${routeDetail }" var="res">
	  			<tr>
		  			<td class="td_cs" title="${res.route_time }" style="width:20%;">${res.route_time }</td>
		  			<td class="td_cs" title="${res.routestatus_code }" style="width:20%;">${res.routestatus_code }</td>
		  			<td class="td_cs" title="${res.status }" style="width:20%;">${res.status }</td>
		  			<td class="td_cs" title="${res.description }" style="width:40%;">${res.description }</td>
		  		</tr>
	  		</c:forEach>
  		</tbody>
	</table>
</div>