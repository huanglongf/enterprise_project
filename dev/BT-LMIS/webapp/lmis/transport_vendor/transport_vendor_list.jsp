<%@ page language= "java" import= "java.util.*" pageEncoding= "utf-8" %>
<!DOCTYPE html>
<html>
	<head lang= "en" >
		<meta charset= "UTF-8" >
		<%@ include file= "/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>/css/table.css" rel= "stylesheet" />
		
		<script type= "text/javascript" src= "<%=basePath %>js/common.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>lmis/transport_vendor/js/transport_vendor.js" ></script>
		<script type= "text/javascript" >
			$(function(){
				$("#table_content tbody tr").dblclick(function(){
					openDiv("/BT-LMIS/control/transportVendorController/toForm.do?id=" + $(this).children(":first").children(":first").val());
					
				});
				
			});
		</script>
	</head>
	<body>
		<div class= "page-header" align= "left" >
			<h1>物流商管理</h1>
		</div>
		<div class= "page-header" style= "margin-left: 20px; margin-bottom: 20px;" >
			<table>
		  		<tr>
		  			<td align= "right" width= "10%"><label class= "blue" for= "transport_code">物流商编码&nbsp;:</label></td>
		  			<td width= "20%"><input id= "transport_code" name= "transport_code" type= "text" value= "${queryParam.transport_code}" /></td>
		  			<td align= "right" width= "10%"><label class= "blue" for= "transport_name">物流商名称&nbsp;:</label></td>
		  			<td width= "20%"><input id= "transport_name" name= "transport_name" type= "text" value= "${queryParam.transport_name}"/></td>
		  		</tr>
			</table>
		</div>
		<div style= "margin-top: 10px; margin-bottom: 10px; margin-left: 20px;" >
			<button class= "btn btn-xs btn-pink" onclick= "find();" >
				<i class= "icon-search icon-on-right bigger-110" ></i>查询
			</button>&nbsp;
			<button class= "btn btn-xs btn-yellow" onclick= "openDiv('${root }control/transportVendorController/toForm.do');" >
				<i class= "icon-hdd" ></i>新增
			</button>&nbsp;
			<button class= "btn btn-xs btn-inverse" onclick= "del();" >
				<i class= "icon-trash" ></i>删除
			</button>&nbsp;
		</div>
		<div class= "title_div" id= "sc_title" >
			<table class= "table table-striped" style= "table-layout: fixed;" >
		   		<thead>
			  		<tr class= "table_head_line" >
			  			<td class= "td_ch" ><input type= "checkbox" id= "checkAll" onclick= "inverseCkb('ckb')" /></td>
			  			<td class= "td_cs" >物流商编码</td>
			  			<td class= "td_cs" >物流商名称</td>
			  			<td class= "td_cs" >物流商类型</td>
			  			<td class= "td_cs" >联系人</td>
			  			<td class= "td_cs" >联系电话</td>
			  			<td class= "td_cs" >是否有效</td>
			  		</tr>  	
				</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  		<tbody align= "center" >
			  		<c:forEach items= "${pageView.records }" var= "res" >
				  		<tr>
				  			<td class= "td_ch"><input id="ckb" name="ckb" type="checkbox" value="${res.id }" ></td>
				  			<td class= "td_cs" title= "${res.transport_code }" >${res.transport_code }</td>
				  			<td class= "td_cs" title= "${res.transport_name }" >${res.transport_name }</td>
				  			<td class= "td_cs" title= "${res.transport_type }" >${res.transport_type }</td>
				  			<td class= "td_cs" title= "${res.contact }" >${res.contact }</td>
				  			<td class= "td_cs" title= "${res.phone }" >${res.phone }</td>
				  			<td class= "td_cs" title= "${res.validity }" >${res.validity }</td>
				  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style= "margin-right: 1%; margin-top: 20px;" >${pageView.pageView }</div>	
	</body>
</html>
