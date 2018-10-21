<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>css/table.css" rel= "stylesheet" />
		
		<script type= "text/javascript" src= "<%=basePath %>js/common.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>radar/receive_deadline/js/receive_deadline.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script>
	</head>
	<body>
		<div class= "page-header" align= "left" >
			<h1>快递时效信息查询</h1>
		</div>
		<div class= "page-header" style= "margin-left: 20px; margin-bottom: 20px;" >
			<table>
		  		<tr>
		  			<td align= "right" width= "10%" ><label class= "blue" for= "express">物流服务商&nbsp;:</label></td>
		  			<td width= "20%">
		  				<select id= "express" name= "express" style= "width: 100%;" data-edit-select= "1" onchange= "shiftExpress();" >
		  					<option value="">---请选择---</option>
							<c:forEach items= "${expressList }" var= "expressList" >
								<option value= "${expressList.transport_code }" ${queryParam.express_code == expressList.transport_code? "selected= 'selected'": "" } >
									${expressList.transport_name }
								</option>
							</c:forEach>
						</select>
		  			</td>
		  			<td align= "right" width= "10%"><label class= "blue" for= "product_type" >产品类型&nbsp;:</label></td>
		  			<td width="20%" >
		  				<select id= "product_type" name= "product_type" style= "width: 100%;" data-edit-select= "1" >
		  					<option value= "" >---请选择---</option>
						</select>
		  			</td>
		  			<td align= "right" width= "10%"><label class= "blue" for= "warehouse" >揽件仓库&nbsp;:</label></td>
		  			<td width= "20%">
		  				<select id= "warehouse" name= "warehouse" style= "width: 100%;" data-edit-select= "1" >
		  					<option value= "" >---请选择---</option>
							<c:forEach items= "${warehouses }" var= "warehouse" >
								<option value= "${warehouse.warehouse_code }" ${queryParam.warehouse_code == warehouse.warehouse_code? "selected= 'selected'": "" } >
									${warehouse.warehouse_name }
								</option>
							</c:forEach>
						</select>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td align= "right" width= "10%" ><label class= "blue" for= "destination_province" >到达省&nbsp;:</label></td>
		  			<td width= "20%" >
		  				<select id= "destination_province" name= "destination_province" style= "width: 100%;" data-edit-select= "1" onchange= "shiftArea(1, 'destination_province', 'destination_city', 'destination_state', 'destination_street');" >
		  					<option value= "" >---请选择---</option>
							<c:forEach items= "${destination_province }" var= "destination_province" >
								<option value= "${destination_province.area_code }" ${queryParam.province_code == destination_province.area_code? "selected= 'selected'": "" } >
									${destination_province.area_name }
								</option>
							</c:forEach>
						</select>
		  			</td>
		  			<td align= "right" width= "10%"><label class= "blue" for= "destination_city" >到达市&nbsp;:</label></td>
		  			<td width= "20%" >
		  				<select id= "destination_city" name= "destination_city" style= "width: 100%;" data-edit-select= "1" onchange= "shiftArea(2, '', 'destination_city', 'destination_state', 'destination_street');" >
		  					<option value= "" >---请选择---</option>
						</select>
		  			</td>
		  			<td align= "right" width= "10%" ><label class= "blue" for= "destination_state" >到达区县&nbsp;:</label></td>
		  			<td width= "20%" >
		  				<select id= "destination_state" name= "destination_state" style= "width: 100%;" data-edit-select= "1" onchange= "shiftArea(3, '', '', 'destination_state', 'destination_street');" >
		  					<option value= "" >---请选择---</option>
						</select>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td align= "right" width= "10%" ><label class= "blue" for= "destination_street" >到达街道&nbsp;:</label></td>
		  			<td width= "20%" >
		  				<select id= "destination_street" name= "destination_street" style= "width: 100%;" data-edit-select= "1" >
		  					<option value= "" >---请选择---</option>
						</select>
		  			</td>
		  		</tr>
			</table>
		</div>
		<div style="margin-top: 10px; margin-bottom: 10px; margin-left: 20px;" >
			<button class="btn btn-xs btn-pink" onclick="pageJump();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>&nbsp;
			<button class="btn btn-xs btn-yellow" onclick="toForm();">
				<i class="icon-hdd"></i>新增
			</button>&nbsp;
			<button class="btn btn-xs btn-inverse" onclick="del();">
				<i class="icon-trash"></i>删除
			</button>&nbsp;	
			<button class="btn btn-xs btn-danger" onclick="submitBatch();">
				<i class=" icon-thumbs-up"></i>提交
			</button>&nbsp;			
		</div>
		<div id="partDiv">
			<%@ include file="/radar/receive_deadline/receive_deadline_list_div.jsp" %>
		</div>
	</body>
</html>