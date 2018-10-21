<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">
	$(function(){
		var i= 0;
		if($("input[name='ckb']").length == 0) {
			$("#checkAll").prop("disabled", true);
			
		} else {
			$("input[name='ckb']").each(function(){
				if($("#tr_" + $.trim($(this).val()).split(",")[0]).length > 0) {
					$(this).prop("disabled", true);
					
				} else {
					i++;
					
				}
				
			});
			if(i == 0) {
				$("#checkAll").prop("disabled", true);
				
			}
			
		}
		
	})
	
</script>
<div class= "title_div no-margin-top no-margin-bottom" id= "sc_title" style= "height: 55px;" >		
	<table class="table table-striped" style= "table-layout: fixed; width: 100%;" >
   		<thead>
	  		<tr class= "table_head_line no-padding-top no-padding-bottom" >
	  			<td class= "td_ch no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 4%;" ><input type= "checkbox" id= "checkAll" onclick= "inverseCkb_s('ckb')" /></td>
	  			<td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 32%;" >组别</td>
	  			<td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 32%;" >工号</td>
	  			<td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 32%;" >姓名</td>
	  		</tr>
	  		<tr class= "no-padding-top no-padding-bottom" >
	  			<td class= "td_ch no-padding-top no-padding-bottom no-padding-left no-padding-right" />
	  			<td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" ><input id= "group_name" name= "group_name" type= "text" style= "width: 80%;" value= "${queryParam.group_name }" onblur= "find();" onkeydown= "keydown_find(window.event);" /></td>
	  			<td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" ><input id= "employee_number" name= "employee_number" type= "text" style= "width: 80%;" value="${queryParam.employee_number }" onblur= "find();" onkeydown= "keydown_find(window.event);" /></td>
	  			<td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" ><input id= "name" name= "name" type= "text" style= "width: 80%;" value="${queryParam.name }" onblur= "find();" onkeydown= "keydown_find(window.event);" /></td>
	  		</tr>
		</thead>
	</table>
</div>
<div class= "tree_div" style= "height: 55px; margin-top: -55px;" ></div>
<div style= "height: 200px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')">
	<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
  		<tbody align= "center" >
  			<c:forEach items= "${pageView.records }" var= "res" >
		  		<tr>
		  			<td class= "td_ch no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 4%;" ><input name= "ckb" type= "checkbox" value= "${res.id },${res.group_name },${res.employee_number },${res.name }" ></td>
		  			<td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 32%;" title= "${res.group_name }" >${res.group_name }</td>
		  			<td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 32%;" title= "${res.employee_number }" >${res.employee_number }</td>
		  			<td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 32%;" title= "${res.name }" >${res.name }</td>
		  		</tr>
	  		</c:forEach>
  		</tbody>
	</table>
</div>
<div style= "margin-right: 1%; margin-top: 20px;" >${pageView.pageView }</div>