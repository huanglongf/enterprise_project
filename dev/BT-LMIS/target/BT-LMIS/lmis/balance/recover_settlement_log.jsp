<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="title_div no-margin-top" id="sc_title">
	<table class="table table-striped" style="table-layout:fixed;">
   		<thead>
	  		<tr class="table_head_line">
	  			<td class="td_cs" title="发起时间">发起时间</td>
	  			<td class="td_cs" title="发起人">发起人</td>
	  			<td class="td_cs" title="处理状态">处理状态</td>
	  			<td class="td_cs" title="操作类型">还原类型</td>
	  			<td class="td_cs" title="合同">合同</td>
	  			<td class="td_cs" title="开始日期">开始日期</td>
	  			<td class="td_cs" title="结束日期">结束日期</td>
	  			<td class="td_cs" title="产生影响">产生影响</td>
	  		</tr>  	
		</thead>
	</table>
</div>
<div class="tree_div"></div>
<div style="height:400px;overflow:auto;overflow:scroll;border:solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title', 'sc_content')">
	<table class="table table-hover table-striped" style="table-layout:fixed;">
  		<tbody align="center">
	  		<c:forEach items="${recoverProcess }" var="res">
	  			<tr>
		  			<td class="td_cs" title="${res.create_time }">${res.create_time }</td>
		  			<td class="td_cs" title="${res.create_by }">${res.create_by }</td>
		  			<td class="td_cs" title="${res.status }">${res.status }</td>
		  			<td class="td_cs" title="${res.move }">${res.move }</td>
		  			<td class="td_cs" title="${res.contract }">${res.contract }</td>
		  			<td class="td_cs" title="${res.recover_start_date }">${res.recover_start_date }</td>
		  			<td class="td_cs" title="${res.recover_end_date }">${res.recover_end_date }</td>
		  			<td class="td_cs" title="${res.log }">${res.log }</td>
		  		</tr>
	  		</c:forEach>
  		</tbody>
	</table>
</div>