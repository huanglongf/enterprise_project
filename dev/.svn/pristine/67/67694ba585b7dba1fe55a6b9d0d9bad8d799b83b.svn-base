<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="title_div" id= "sc_title">		
	<table class="table table-striped" style="table-layout:fixed;">
   		<thead>
	  		<tr class="table_head_line">
	  			<td class="td_cs" title="发生时间" style="width:15%;">发生时间</td>
	  			<td class="td_cs" title="操作者" style="width:10%;">操作者</td>
	  			<td class="td_cs" title="事件说明" style="width:15%;">事件说明</td>
	  			<td class="td_cs" title="相关说明" style="width:60%;">相关说明</td>
	  		</tr>  	
		</thead>
	</table>
</div>
<div class="tree_div"></div>
<div style="height:323px;overflow:auto;overflow:scroll;border:solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title', 'sc_content')">
	<table class="table table-hover table-striped" style="table-layout:fixed;">
  		<tbody id="workOrderEventMonitor" align="center">
	  		<c:forEach items="${event_monitor }" var="res">
	  			<tr>
		  			<td class="td_cs" title="${res.create_time }" style="width:15%;">${res.create_time }</td>
		  			<td class="td_cs" title="${res.create_by }" style="width: 10%;">${res.create_by }</td>
		  			<td class="td_cs" title="${res.event_description }" style="width: 15%;">${res.event_description }</td>
		  			<td class="td_cs" title="${res.remark }" style="width: 60%;">${res.remark }</td>
		  		</tr>
	  		</c:forEach>
  		</tbody>
	</table>
</div>