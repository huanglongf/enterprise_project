<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="title_div" id="sc_title1">		
	<table class="table table-striped" style="table-layout:fixed;">
   		<thead>
	  		<tr class="table_head_line">
	  			<td class="td_ch" ><input type="checkbox" disabled="disabled"/></td>
	  			<td class="td_cs" title="SKU条码" style="width:25%;">SKU条码</td>
	  			<td class="td_cs" title="条形码" style="width:25%;">条形码</td>
	  			<td class="td_cs" title="商品名称" style="width:25%;">商品名称</td>
	  			<td class="td_cs" title="扩展属性" style="width:15%;">扩展属性</td>
	  			<td class="td_cs" title="商品数量" style="width:10%;">商品数量</td>
	  		</tr>  	
		</thead>
	</table>
</div>

<div class="tree_div"></div>
<div style="height:323px;overflow:auto;overflow:scroll;border:solid #F2F2F2 1px;" id="sc_content1" onscroll="init_td('sc_title1', 'sc_content1')">
	<table class="table table-hover table-striped" style="table-layout:fixed;">
  		<tbody align="center">
	  		<c:forEach items="${packageDetail }" var="res">
	  			<tr>
	  				<td class="td_ch"><input type="checkbox" value="${res.id }" disabled="disabled" ${fns:contains(packageId, res.id) ?"checked='checked'":'' }/></td>
		  			<td class="td_cs" title="${res.sku_number }" style="width:25%;">${res.sku_number }</td>
		  			<td class="td_cs" title="${res.barcode }" style="width:25%;">${res.barcode }</td>
		  			<td class="td_cs" title="${res.item_name }" style="width:25%;">${res.item_name }</td>
		  			<td class="td_cs" title="${res.extend_pro }" style="width:15%;">${res.extend_pro }</td>
		  			<td class="td_cs" title="${res.qty }" style="width:10%;">${res.qty }</td>
		  		</tr>
	  		</c:forEach>
  		</tbody>
	</table>
</div>