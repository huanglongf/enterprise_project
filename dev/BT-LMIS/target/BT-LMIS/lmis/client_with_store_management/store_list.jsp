<%@ page language= "java" contentType= "text/html; charset=UTF-8" pageEncoding= "UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions"%>
<script>
	$(function(){
		$("#table_content tbody tr").dblclick(function(){toForm($(this));});
		
	});
</script>
<hr>
<h4>
	客户下店铺列表
	<button class= "btn btn-xs btn-yellow" onclick= "toForm('')" >
		<i class= "icon-hdd" ></i>新增
	</button>&nbsp;
	<button class= "btn btn-xs btn-inverse" onclick= "del();" >
		<i class= "icon-trash" ></i>删除
	</button>&nbsp;
	<button class= "btn btn-xs btn-success" onclick= "refresh();" >
		<i class= "icon-refresh" ></i>刷新
	</button>&nbsp;
</h4>
<div class= "title_div no-margin-top" id= "sc_title" style= "height: 85px;" >		
	<table class= "table table-striped" style= "table-layout: fixed;" >
   		<thead>
	  		<tr class= "table_head_line" >
	  			<td class= "td_ch" ><input type= "checkbox" id= "checkAll" onclick= "inverseCkb('ckb')" /></td>
	  			<td class= "td_cs" >成本中心</td>
	  			<td class= "td_cs" >店铺编码</td>
	  			<td class= "td_cs" >店铺名称</td>
	  			<td class= "td_cs" >店铺类型</td>
	  			<td class= "td_cs" >结算方式</td>
	  			<td class= "td_cs" >联系人</td>
	  			<td class= "td_cs" >联系电话</td>
	  			<td class= "td_cs" >联系地址 </td>
	  			<td class= "td_cs" >开票公司 </td>
	  			<td class= "td_cs" >发票类型 </td>
	  			<td class= "td_cs" >发票信息 </td>
	  			<td class= "td_cs" >备注</td>
	  			<td class= "td_cs" >有效性</td>
	  		</tr>
	  		<tr>
	  			<td class= "td_ch" />
	  			<td class= "td_cs" ><input id= "cost_center_query" name= "cost_center_query" type= "text" value= "${queryParam.cost_center }" onblur= "find();" onkeydown= "keydown_find(window.event);" /></td>
	  			<td class= "td_cs" ><input id= "store_code_query" name= "store_code_query" type= "text" value= "${queryParam.store_code }" onblur= "find();" onkeydown= "keydown_find(window.event);" /></td>
	  			<td class= "td_cs" ><input id= "store_name_query" name= "store_name_query" type= "text" value= "${queryParam.store_name }" onblur= "find();" onkeydown= "keydown_find(window.event);" /></td>
	  			<td class= "td_cs" >
	  				<select id= "store_type_query" name= "store_type_query" onchange="find();" >
						<option value= "" >---请选择---</option>
						<option value= "0" ${queryParam.store_type == "0"? "selected= 'selected'": "" } >A类</option>
						<option value= "1" ${queryParam.store_type == "1"? "selected= 'selected'": "" } >B类</option>
						<option value= "2" ${queryParam.store_type == "2"? "selected= 'selected'": "" } >其它</option>
					</select>
	  			</td>
	  			<td class= "td_cs" >
	  				<select id= "store_settlement_method_query" name= "store_settlement_method_query" onchange="find();" >
						<option value= "-1" >---请选择---</option>
						<option value= "0" ${queryParam.settlement_method == 0? "selected= 'selected'": "" } >代销</option>
						<option value= "1" ${queryParam.settlement_method == 1? "selected= 'selected'": "" } >结算经销</option>
						<option value= "2" ${queryParam.settlement_method == 2? "selected= 'selected'": "" } >付款经销</option>
					</select>
	  			</td>
	  			<td class= "td_cs" />
	  			<td class= "td_cs" />
	  			<td class= "td_cs" />
	  			<td class= "td_cs" />
	  			<td class= "td_cs" >
	  				<select id= "store_invoice_type_query" name= "store_invoice_type_query" onchange="find();" >
						<option value= "" >---请选择---</option>
						<option value= "0" ${queryParam.invoice_type == "0"? "selected= 'selected'": "" } >手写发票</option>
						<option value= "1" ${queryParam.invoice_type == "1"? "selected= 'selected'": "" } >机打发票</option>
						<option value= "2" ${queryParam.invoice_type == "2"? "selected= 'selected'": "" } >普通发票</option>
						<option value= "3" ${queryParam.invoice_type == "3"? "selected= 'selected'": "" } >增值税发票</option>
						<option value= "4" ${queryParam.invoice_type == "4"? "selected= 'selected'": "" } >其它发票</option>
					</select>
	  			</td>
	  			<td class= "td_cs" />
	  			<td class= "td_cs" />
	  			<td class= "td_cs" >
	  				<select id= "store_validity_query" name= "store_validity_query" onchange="find();" >
						<option value= "-1" >---请选择---</option>
						<option value= "1" ${queryParam.validity == 1? "selected= 'selected'": "" } >有效</option>
						<option value= "0" ${queryParam.validity == 0? "selected= 'selected'": "" } >无效</option>
					</select>
	  			</td>
	  		</tr>
		</thead>
	</table>
</div>
<div class= "tree_div" style= "height: 85px; margin-top: -85px;" ></div>
<div style= "height: 200px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
	<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		<tbody align= "center" >
			<c:if test= "${empty pageView.records }" >
				<tr>
					<td class= "td_ch" />
					<td class= "td_cs" />
					<td class= "td_cs" />
					<td class= "td_cs" />
					<td class= "td_cs" />
					<td class= "td_cs" />
					<td class= "td_cs" />
					<td class= "td_cs" />
					<td class= "td_cs" />
					<td class= "td_cs" />
					<td class= "td_cs" />
					<td class= "td_cs" />
					<td class= "td_cs" />
					<td class= "td_cs" />
				</tr>
			</c:if>
			<c:forEach items= "${pageView.records }" var= "res" >
				<tr>
					<td class= "td_ch" ><input id= "ckb" name= "ckb" type= "checkbox" value= "${res.id }" ></td>
					<td class= "td_cs" title= "${res.cost_center }" >${res.cost_center }</td>
					<td class= "td_cs" title= "${res.store_code }" >${res.store_code }</td>
					<td class= "td_cs" title= "${res.store_name }" >${res.store_name }</td>
					<td class= "td_cs" title= "${res.store_type }" >${res.store_type }</td>
					<td class= "td_cs" title= "${res.settlement_method }" >${res.settlement_method }</td>
					<td class= "td_cs" title= "${res.contact }" >${res.contact }</td>
					<td class= "td_cs" title= "${res.phone }" >${res.phone }</td>
					<td class= "td_cs" title= "${res.address }" >${res.address }</td>
					<td class= "td_cs" title= "${res.company }" >${res.company }</td>
					<td class= "td_cs" title= "${res.invoice_type }" >${res.invoice_type }</td>
					<td class= "td_cs" title= "${res.invoice_info }" >${res.invoice_info }</td>
					<td class= "td_cs" title= "${res.remark }" >${res.remark }</td>
					<td class= "td_cs" title= "${res.validity }" >${res.validity }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<!-- 分页添加 -->
<div style= "margin-right: 30px; margin-top: 20px;" >${pageView.pageView }</div>