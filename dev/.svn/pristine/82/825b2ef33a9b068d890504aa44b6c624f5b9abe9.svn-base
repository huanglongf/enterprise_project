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
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css"  />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/loadingStyle.css" />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/common.css" />
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<script type="text/javascript" src="<%=basePath %>lmis/warehouse_management/js/warehouse.js"></script>
	    <script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js"></script>
		<script type="text/javascript" src="<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js"></script>
	</head>
	<body>
		<div class= "page-header" align= "left" >
			<h1>逻辑仓管理</h1>
		</div>
		<div class= "page-header" style= "margin-left: 20px; margin-bottom: 20px;" >
			<table>
		  		<tr>
		  			<td align= "right" width= "10%"><label class= "blue" for= "warehouse_name">仓库名称&nbsp;:</label></td>
		  			<td width= "20%"><input id= "warehouse_name" name= "warehouse_name" type="text" value= "${warehouseQueryParam.warehouse_name }" /></td>
		  			<td align= "right" width= "10%"></td>
		  			<td width= "20%"></td>
		  		</tr>  		
			</table>
		</div>	
		<div style= "margin-top: 10px; margin-bottom: 10px; margin-left: 20px;" >
			<button class= "btn btn-xs btn-pink" onclick="query();" >
				<i class= "icon-search icon-on-right bigger-110" ></i>查询
			</button>
			&nbsp;
			<button class= "btn btn-xs btn-yellow" onclick= "toForm_add();" >
				<i class= "icon-edit" ></i>新增
			</button>
			&nbsp;
			<button class="btn btn-xs btn-primary" onclick="toForm_update();">
				<i class="icon-edit"></i>修改
			</button>
			&nbsp;
			<button class="btn btn-xs btn-purple" onclick="toForm_download();">
				<i class="icon-edit"></i>导出
			</button>
			<%-- <button class="btn btn-xs btn-inverse" onclick="del('${employeeid}','${ret}');">
				<i class="icon-trash"></i>删除
			</button>
			&nbsp; --%>
		</div>
		<div class= "title_div" id= "sc_title" >
			<table class= "table table-striped" style= "table-layout: fixed;" >
		   		<thead>
			  		<tr class= "table_head_line">
			  			<td><input type= "checkbox" id= "checkAll" onclick= "inverseCkb('ckb')" /></td>
						<td class= "td_cs" style= "width: 200px" title= "仓库代码">仓库代码</td>
						<td class= "td_cs" style= "width: 200px" title= "仓库名称">仓库名称</td>
						<td class= "td_cs" style= "width: 200px" title= "所在省">所在省</td>
						<td class= "td_cs" style= "width: 200px" title= "所在市">所在市</td>
						<td class= "td_cs" style= "width: 200px" title= "所在区">所在区</td>
						<td class= "td_cs" style= "width: 200px" title= "所在街道">所在街道</td>
						<td class= "td_cs" style= "width: 200px" title= "详细地址">详细地址</td>
						<td class= "td_cs" style= "width: 200px" title= "联系人">联系人</td>
						<td class= "td_cs" style= "width: 200px" title= "联系电话">联系电话</td>
						<td class= "td_cs" style= "width: 200px" title= "仓库类型">仓库类型</td>	
						<td class= "td_cs" style= "width: 200px" title= "操作">操作</td>	
						<td class= "td_cs" style= "width: 100px" title= "是否结算">是否结算</td>	
			  		</tr>	
				</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div style="height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_detail_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  		<tbody align= "center" >
			  		<c:forEach items= "${pageView.records}" var= "records" >
				  		<tr>
				  			<td><input id= "ckb" name= "ckb" type= "checkbox" value= "${records.id }" ></td>
				  			<td class= "td_cs" style= "width: 200px" title= "${records.warehouse_code }" >${records.warehouse_code }</td>
				  			<td class= "td_cs" style= "width: 200px" title= "${records.warehouse_name }" >${records.warehouse_name }</td>
				  			<td class= "td_cs" style= "width: 200px" title= "${records.province_name }" >${records.province_name }</td>
				  			<td class= "td_cs" style= "width: 200px" title= "${records.city_name }" >${records.city_name }</td>
				  			<td class= "td_cs" style= "width: 200px" title= "${records.state_name }" >${records.state_name }</td>
				  			<td class= "td_cs" style= "width: 200px" title= "${records.street_name }" >${records.street_name }</td>
				  			<td class= "td_cs" style= "width: 200px" title= "${records.address }" >${records.address }</td>
				  			<td class= "td_cs" style= "width: 200px" title= "${records.contact }" >${records.contact }</td>
				  			<td class= "td_cs" style= "width: 200px" title= "${records.phone }" >${records.phone }</td>
				  			<td class= "td_cs" style= "width: 200px" title= "${records.warehouse_type }" >${records.warehouse_type }</td>
				  			<td class= "td_cs" style= "width: 200px" >
				  				<c:if test= "${records.validity == 1 }">已启用</c:if>
				  				<c:if test= "${records.validity == 0 }">已停用</c:if>
				  				|
				  				<c:if test= "${records.validity == 1}" ><button class= "btn btn-xs btn-info" onclick= "shiftStatus('${records.id }', '0');" >停用</button></c:if>
				  				<c:if test= "${records.validity == 0}" ><button class= "btn btn-xs btn-pink" onclick= "shiftStatus('${records.id }', '1');" >启用</button></c:if>			  			
				  			</td>
				  			<td class= "td_cs" style= "width: 100px" title= "${records.need_balance }" >${records.need_balance }</td>
				  		</tr>
			  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style= "margin-right: 1%; margin-top: 20px; margin-bottom: 10%;">${pageView.pageView }</div>		
		<div class= "space-4" ></div>
	</body>
</html>
