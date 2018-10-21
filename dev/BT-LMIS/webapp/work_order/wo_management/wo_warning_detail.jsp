<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="title_div" id="sc_title3">		
	<table class="table table-striped" style="table-layout:fixed;">
   		<thead>
	  		<tr class="table_head_line">
	  			<td class="td_cs" title="预警时间" style="width:20%;">预警时间</td>
	  			<td class="td_cs" title="预警类型" style="width:10%;">预警类型</td>
	  			<td class="td_cs" title="预警级别" style="width:10%;">预警级别</td>
	  			<td class="td_cs" title="预警状态" style="width:10%;">预警状态</td>
	  			<td class="td_cs" title="时效时间" style="width:10%;">时效时间</td>
	  			<td class="td_cs" title="预警来源" style="width:10%;">预警来源</td>
	  			<td class="td_cs" title="预警原因" style="width:30%;">预警原因</td>
	  		</tr>  	
		</thead>
	</table>
</div>
<div class="tree_div"></div>
<div style="height:323px;overflow:auto;overflow:scroll;border:solid #F2F2F2 1px;" id="sc_content3" onscroll="init_td('sc_title3', 'sc_content3')">
	<table class="table table-hover table-striped" style="table-layout:fixed;">
  		<tbody align="center">
	  		<c:forEach items="${warningDetail }" var="res">
	  			<tr>
		  			<td class="td_cs" title="${res.happen_time }" style="width:20%;">${res.happen_time }</td>
		  			<td class="td_cs" title="${res.warningtype_name }" style="width:10%;">${res.warningtype_name }</td>
		  			<td class="td_cs" title="${res.warning_level }" style="width:10%;">${res.warning_level }</td>
		  			<td class="td_cs" title="${res.del_flag }" style="width:10%;">${res.del_flag }</td>
		  			<td class="td_cs" title="${res.efficient_time }" style="width: 10%;" >${res.efficient_time }</td>
		  			<td class="td_cs" title="${res.source }" style="width:10%;">${res.source }</td>
		  			<td class="td_cs" title="${res.reason }" style="width:30%;">${res.reason }</td>
		  		</tr>
	  		</c:forEach>
  		</tbody>
	</table>
</div>