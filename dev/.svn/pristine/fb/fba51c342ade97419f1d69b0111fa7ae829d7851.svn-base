<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	$(function(){$("#table_content_wp tbody tr").dblclick(function(){toWorkPowerForm($(this))});});
</script>
<div class= "title_div" id= "sc_title_wp" >		
	<table class= "table table-striped" style= "table-layout: fixed;" >
   		<thead>
	  		<tr class= "table_head_line">
	  			<td class= "td_cs" title= "物流商" style= "width: 70%;" >物流商</td>
	  			<td class= "td_cs" title= "操作" style= "width: 30%;" >操作</td>
	  		</tr>  	
		</thead>
	</table>
</div>
<div class= "tree_div" ></div>
<div style= "height: 200px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content_wp" onscroll= "init_td('sc_title_wp', 'sc_content_wp')" >
	<table id= "table_content_wp" class= "table table-hover table-striped" style= "table-layout: fixed;" >
  		<tbody align= "center" >
	  		<c:forEach items= "${workPower }" var= "res" >
	  			<tr style= "cursor: pointer;" >
	  				<td style= "display: none;" >${res.id }</td>
		  			<td class= "td_cs" title= "${res.carrier }" style= "width: 70%;" >${res.carrier }</td>
		  			<td class= "td_cs" style= "width: 30%;" >
		  				<button class= "btn btn-xs btn-inverse" onclick="delPower('wp', '${res.id }');"><i class="icon-trash"></i>删除</button>
		  			</td>
		  		</tr>
	  		</c:forEach>
  		</tbody>
	</table>
</div>
<button type= "button" class= "btn btn-sm btn-primary" onclick= "addPower('work');" >
	<i class= "icon-plus" aria-hidden= "true" ></i>新增
</button>