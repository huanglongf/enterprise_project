<%@ page language= "java" contentType= "text/html; charset=UTF-8" pageEncoding= "UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	$(function(){
		$("#table_content tbody tr").dblclick(function(){toProductTypeForm($(this));});
		
	});
</script>
<button class= "btn btn-xs btn-yellow" onclick= "toProductTypeForm('')" >
	<i class= "icon-hdd" ></i>新增
</button>&nbsp;
<button class= "btn btn-xs btn-inverse" onclick= "del();" >
	<i class= "icon-trash" ></i>删除
</button>&nbsp;
<button class= "btn btn-xs btn-success" onclick= "refresh();" >
	<i class= "icon-refresh" ></i>刷新
</button>&nbsp;
<div class= "title_div no-margin-top" id= "sc_title" style= "height: 85px;" >		
	<table class= "table table-striped" style= "table-layout: fixed;" >
   		<thead>
	  		<tr class= "table_head_line" >
	  			<td class= "td_ch" ><input type= "checkbox" id= "checkAll" onclick= "inverseCkb('ckb')" /></td>
	  			<td class= "td_cs" >产品类型编码</td>
	  			<td class= "td_cs" >产品类型名称</td>
	  			<td class= "td_cs" >状态</td>
	  		</tr>
	  		<tr>
	  			<td class= "td_ch" />
	  			<td class= "td_cs" ><input id= "product_type_code_query" name= "product_type_code_query" type= "text" value= "${queryParam.product_type_code }" onblur= "find();" onkeydown= "keydown_find(window.event);" /></td>
	  			<td class= "td_cs" ><input id= "product_type_name_query" name= "product_type_name_query" type= "text" value= "${queryParam.product_type_name }" onblur= "find();" onkeydown= "keydown_find(window.event);" /></td>
	  			<td class= "td_cs" >
	  				<select id= "status_query" name= "status_query" onchange= "find();" >
						<option value= "-1" >---请选择---</option>
						<option value= "1" ${queryParam.status == 1? "selected= 'selected'": "" } >启用</option>
						<option value= "0" ${queryParam.status == 0? "selected= 'selected'": "" } >停用</option>
					</select>
	  			</td>
	  		</tr>
		</thead>
	</table>
</div>
<div class= "tree_div" style= "height: 85px; margin-top: -85px;" ></div>
<div style= "height: 300px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
	<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		<tbody align= "center" >
			<c:if test= "${empty pageView.records }" >
				<tr>
					<td class= "td_ch" />
					<td class= "td_cs" />
					<td class= "td_cs" />
					<td class= "td_cs" />
				</tr>
			</c:if>
			<c:forEach items= "${pageView.records }" var= "res" >
				<tr>
					<td class= "td_ch" ><input id= "ckb" name= "ckb" type= "checkbox" value= "${res.id }" ></td>
					<td class= "td_cs" title= "${res.product_type_code }" >${res.product_type_code }</td>
					<td class= "td_cs" title= "${res.product_type_name }" >${res.product_type_name }</td>
					<td class= "td_cs" title= "${res.status }" >${res.status }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<!-- 分页添加 -->
<div style= "margin-right: 30px; margin-top: 20px;" >${pageView.pageView }</div>
