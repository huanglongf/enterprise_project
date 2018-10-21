<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	$(function(){$("#table_content_sp tbody tr").dblclick(function(){toStorePowerForm($(this))});});
</script>
<div class= "title_div" id= "sc_title_sp" >		
	<table class= "table table-striped" style= "table-layout: fixed;" >
   		<thead>
	  		<tr class= "table_head_line">
	  			<td class= "td_cs" title= "店铺" style= "width: 40%;" >店铺</td>
	  			<td class= "td_cs" title= "自营仓" style= "width: 20%;" >自营仓</td>
	  			<td class= "td_cs" title= "外包仓" style= "width: 20%;" >外包仓</td>
	  			<td class= "td_cs" title= "操作" style= "width: 20%;" >操作</td>
	  		</tr>  	
		</thead>
	</table>
</div>
<div class= "tree_div" ></div>
<div style= "height: 200px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content_sp" onscroll= "init_td('sc_title_sp', 'sc_content_sp')" >
	<table id= "table_content_sp" class= "table table-hover table-striped" style= "table-layout: fixed;" >
  		<tbody align= "center" >
	  		<c:forEach items= "${storePower }" var= "res" >
	  			<tr style= "cursor: pointer;" >
	  				<td style= "display: none;" >${res.id }</td>
		  			<td class= "td_cs" title= "${res.store }（双击进入）" style= "width: 40%;" >${res.store }</td>
		  			<td class= "td_cs" title= "${res.selfwarehouse }（双击进入）" style= "width: 20%;" >${res.selfwarehouse }</td>
		  			<td class= "td_cs" title= "${res.outsourcedwarehouse }（双击进入）" style= "width: 20%;" >${res.outsourcedwarehouse }</td>
		  			<td class= "td_cs" style= "width: 20%;" >
		  				<button class= "btn btn-xs btn-inverse" onclick="delPower('sp', '${res.id }');"><i class="icon-trash"></i>删除</button>
		  			</td>
		  		</tr>
	  		</c:forEach>
  		</tbody>
	</table>
</div>
<button type= "button" class= "btn btn-sm btn-primary" onclick= "addPower('store');" >
	<i class= "icon-plus" aria-hidden= "true" ></i>新增
</button>