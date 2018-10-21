<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	$(function(){
		$("#table_detail_content tbody tr").dblclick(function(){
			showDialogPage1('500', '/BT-LMIS/controller/receiveDeadlineController/toDetailForm.do?pid='
			+ $(this).children(":first").children(":first").val()
			+'&id=' 
			+ $(this).children().eq(1).children(":first").val())
			
		});
		
		
	});
</script>
<hr>
<div align="left">
	<h4>
		时效明细维护列表
		<button class="btn btn-xs btn-yellow" onclick="openDetail();">
			<i class="icon-hdd"></i>新增
		</button>&nbsp;
	</h4>
</div>
<div class="title_div" id="sc_title">		
	<table class="table table-striped" style="table-layout: fixed;">
   		<thead>
	  		<tr class="table_head_line">
	  			<td class="td_cs" style="width : 100px">节点序号</td>
	  			<td class="td_cs" style="width : 100px">到达省</td>
	  			<td class="td_cs" style="width : 100px">到达市</td>
	  			<td class="td_cs" style="width : 100px">到达区县</td>
	  			<td class="td_cs" style="width : 100px">到达街道</td>
	  			<td class="td_cs" style="width : 100px">时效</td>
	  			<td class="td_cs" style="width : 100px">预警类型</td>
	  			<td class="td_cs" style="width : 100px">有效性</td>
	  			<td class="td_cs" style="width : 200px">操作</td>
	  		</tr>  	
		</thead>
	</table>
</div>
<div class= "tree_div" ></div>
<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
	<table id= "table_detail_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
  		<tbody align= "center" >
	  		<c:forEach items= "${pageView.records}" var= "res" >
		  		<tr>
		  			<td style="display : none"><input value="${receiveDeadline.id }" /></td>
		  			<td style="display : none"><input value="${res.id }" /></td>
		  			<td class="td_cs" style="width : 100px" title="${res.number}">${res.number}</td>
		  			<td class="td_cs" style="width : 100px" title="${res.province_name}">${res.province_name}</td>
		  			<td class="td_cs" style="width : 100px" title="${res.city_name}">${res.city_name}</td>
		  			<td class="td_cs" style="width : 100px" title="${res.state_name}">${res.state_name}</td>
		  			<td class="td_cs" style="width : 100px" title="${res.street_name}">${res.street_name}</td>
		  			<td class="td_cs" style="width : 100px" title="${res.efficiency}">${res.efficiency}</td>
		  			<td class="td_cs" style="width : 100px" title="${res.warningtype}">${res.warningtype}</td>
		  			<td class="td_cs" style="width : 100px" title="${res.valibity}">${res.valibity}</td>
		  			<td class="td_cs" style="width : 200px">
		  				<button 
		  				<c:if test="${res.number == 1}">disabled='disabled'</c:if>
		  				onclick="move('${res.id}', 'up');"
		  				>
							↑
	  					</button>&nbsp;
		  				<button 
		  				<c:if test="${res.number == totalLength}">disabled='disabled'</c:if>
		  				onclick="move('${res.id}', 'down');"
		  				>
		  					↓
		  				</button>&nbsp;
		  				<c:if test="${res.valibity == '无效'}">
		  					<button class="btn btn-xs btn-success" onclick="shiftDetailStatus('${res.id}', '1');">
								<i class="icon-hdd"></i>启用
							</button>&nbsp;
		  				</c:if>
		  				<c:if test="${res.valibity == '有效'}">
		  					<button class="btn btn-xs btn-inverse" onclick="shiftDetailStatus('${res.id}', '0');">
								<i class="icon-pause"></i>停用
							</button>&nbsp;
		  				</c:if>
		  				<button class="btn btn-xs btn-danger" onclick="delDetail('${res.id}');">
							<i class="icon-trash"></i>删除
						</button>&nbsp;
						
		  			</td>
		  		</tr>
	  		</c:forEach>
  		</tbody>
	</table>
</div>
<div style="margin-right : 1%; margin-top : 20px;">${pageView.pageView}</div>