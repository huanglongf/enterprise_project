<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	$(function(){$("#table_content tbody tr").dblclick(function(){up($(this ));});});
</script>
<div id= "group_list" class= "form-group" >
	<div class= "title_div" id= "sc_title" >
		<table class= "table table-striped" style= "table-layout: fixed;" >
	   		<thead>
	  			<tr class= "table_head_line" >
	  				<td class= "td_ch" ><input type= "checkbox" id= "checkAll" onclick= "inverseCkb('ckb')" /></td>
	  				<td class= "td_cs" style= "width: 10%;" >启用状态</td>
		  			<td class= "td_cs" style= "width: 10%;" >组别编码</td>
		  			<td class= "td_cs" style= "width: 20%;" >组别名称</td>
		  			<td class= "td_cs" style= "width: 20%;" >上级组别</td>
		  			<td class= "td_cs" style= "width: 10%;" >自动分配工单</td>
		  			<td class= "td_cs" style= "width: 30%;" >说明</td>
		  			
		  		</tr>
		  	</thead>
	 	</table>
	</div>
	<div class= "tree_div" ></div>
	<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
		<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
	  		<tbody align= "center" >
	  			<c:forEach items= "${pageView.records }" var= "pageView">
			  		<tr style= "cursor: pointer;" >
			  			<td class= "td_ch" ><input id= "ckb" name= "ckb" type= "checkbox" value= "${pageView.id }" ></td>
			  			<td class= "td_cs" style= "width: 10%;" title= "（双击进入）" >
			  				<c:if test= "${pageView.status == 'false' }">停用</c:if>
			  				<c:if test= "${pageView.status == 'true' }">启用</c:if>
			  				|
			  				<c:if test= "${pageView.status == 'true' }"><button class= "btn btn-xs btn-danger" onclick= "upStatus('${pageView.id }','false');">停用</button></c:if>
			  				<c:if test= "${pageView.status == 'false' }"><button class= "btn btn-xs btn-info" onclick= "upStatus('${pageView.id }','true');">启用</button></c:if>
			  			</td>
			  			<td class= "td_cs" style= "width: 10%;" title= "${pageView.group_code }（双击进入）" >${pageView.group_code }</td>
			  			<td class= "td_cs" style= "width: 20%;" title= "${pageView.group_name }（双击进入）" >${pageView.group_name }</td>
			  			<td class= "td_cs" style= "width: 20%;" title= "${pageView.superior }（双击进入）" >${pageView.superior }</td>
			  			<td class= "td_cs" style= "width: 10%;" title= "${pageView.autoAllocFlag }（双击进入）" >${pageView.autoAllocFlag }</td>
			  			<td class= "td_cs" style= "width: 30%;" title= "${pageView.remark }（双击进入）" >${pageView.remark }</td>
			  		</tr>
		  		</c:forEach>
	  		</tbody>
	  	</table>
 	</div>
	<div style= "margin-right: 1%; margin-top: 20px;">${pageView.pageView }</div>
</div>