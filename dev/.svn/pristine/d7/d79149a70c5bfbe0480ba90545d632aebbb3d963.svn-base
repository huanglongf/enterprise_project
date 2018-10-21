<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
$(function(){
	$("#table_detail_content tbody tr").dblclick(function(){openDetail($(this)); });
	
});
</script>
<hr>
<div align="left">
	<h4>
		逻辑仓列表
		<button class="btn btn-xs btn-yellow" onclick="openDetail('');">
			<i class="icon-hdd"></i>新增
		</button>&nbsp;
		<button class="btn btn-xs btn-danger" onclick="delDetail();">
			<i class="icon-trash"></i>删除
		</button>&nbsp;
	</h4>
</div>
<div class="title_div" id="sc_title">		
	<table class="table table-striped" style="table-layout: fixed;">
   		<thead>
	  		<tr class="table_head_line">
	  			<td class="td_ch"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/></td>
	  			<td class="td_cs" style="width : 250px">逻辑仓代码</td>
	  			<td class="td_cs" style="width : 400px">逻辑仓名称</td>
	  		</tr>  	
		</thead>
	</table>
</div>
<div class="tree_div"></div>
<div style="height : 400px; overflow : auto; overflow : scroll; border : solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title', 'sc_content')">
	<table id="table_detail_content" class="table table-hover table-striped" style="table-layout: fixed;">
  		<tbody align="center">
	  		<c:forEach items="${pageView.records}" var="res">
		  		<tr>
		  			<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${res.id}"></td>
		  			<td class="td_cs" style="width : 300px" title="${res.logic_code}">${res.logic_code}</td>
		  			<td class="td_cs" style="width : 500px" title="${res.warehouse_name}">${res.warehouse_name}</td>
		  		</tr>
	  		</c:forEach>
  		</tbody>
	</table>
</div>
<div style="margin-right : 1%; margin-top : 20px;">${pageView.pageView}</div>