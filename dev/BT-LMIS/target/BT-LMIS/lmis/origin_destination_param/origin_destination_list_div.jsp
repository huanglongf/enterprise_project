<%@ page language= "java" contentType= "text/html; charset=UTF-8" pageEncoding= "UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class= "title_div" id= "sc_title" >		
	<table class= "table table-striped" style= "table-layout: fixed;" >
		<thead>
			<tr class= "table_head_line" >
	  			<td class= "td_ch" ><input type= "checkbox" id= "checkAll" onclick= "inverseCkb('ckb')" /></td>
	  			<td class= "td_cs" >合同名称</td>
	  			<td class= "td_cs" >版本号</td>
	  			<td class= "td_cs" >物流商名称</td>
	  			<td class= "td_cs" >产品类型</td>
	  			<td class= "td_cs" >始发地省</td>
				<td class= "td_cs" >始发地市</td>
				<td class= "td_cs" >始发地区</td>
	  			<td class= "td_cs" >目的地省</td>
	  			<td class= "td_cs" >目的地市</td>
	  			<td class= "td_cs" >目的地区</td>
	  			<td class= "td_cs" >公式</td>
	  			<td class= "td_cs" >首重(KG)</td>
	  			<td class= "td_cs" >首重价格(元)</td>
	  			<td class= "td_cs" >续重区间</td>
	  			<td class= "td_cs" >续重价格(元)</td>
	  			<td class= "td_cs" >操作(启用/停用)</td>
	  		</tr>  	
  		</thead>
	</table>
</div>
<div class= "tree_div" ></div>
<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
	<table class= "table table-striped" style="table-layout: fixed;">
  		<tbody  align= "center" >
	  		<c:forEach items= "${pageView.records }" var= "res" >
		  		<tr>
		  			<td class= "td_ch" ><input id= "ckb" name= "ckb" type= "checkbox" value= "${res.id }" ></td>
		  			<td class= "td_cs" title= "${res.contract_name }" >${res.contract_name }</td>
		  			<td class= "td_cs" >${res.contract_version }</td>
		  			<td class= "td_cs" >${res.carrier_name }</td>
		  			<td class= "td_cs" >${res.itemtype_name }</td>
		  			<%--<td class= "td_cs" >${res.origination }</td>--%>
					<td class= "td_cs" >${res.province_origin }</td>
					<td class= "td_cs" >${res.city_origin }</td>
					<td class= "td_cs" >${res.state_origin }</td>
		  			<td class= "td_cs" >${res.province_destination }</td>
		  			<td class= "td_cs" >${res.city_destination }</td>
		  			<td class= "td_cs" >${res.district_destination }</td>
		  			<td class= "td_cs" title="${res.pricing_formula_name }" >${res.pricing_formula_name }</td>
		  			<td class= "td_cs" >${res.szxz_sz }</td>
		  			<td class= "td_cs" >${res.szxz_price }</td>
		  			<td class= "td_cs" >${res.internal }</td>
		  			<td class= "td_cs" >${res.price }</td>
		  			<td class= "td_cs" >
 				 		<c:if test= "${res.status == 0 }">已停用</c:if>
			  			<c:if test= "${res.status == 1 }">已启用</c:if>
			  			|
		  				<c:if test= "${res.status == 1 }"><button class= "btn btn-xs btn-info" onclick= "upStatus('${res.id }','0');" >停用</button></c:if>
		  				<c:if test= "${res.status == 0 }"><button class= "btn btn-xs btn-pink" onclick= "upStatus('${res.id }','1');" >启用</button></c:if>	  			
	  				</td>
		  		</tr>
	  		</c:forEach>
  		</tbody>
	</table>
</div>
<!-- 分页添加 -->
<div style= "margin-right: 1%; margin-top: 20px;" >${pageView.pageView }</div>