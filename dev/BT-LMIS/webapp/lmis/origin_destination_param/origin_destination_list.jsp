<%@ page language= "java" import= "java.util.*" pageEncoding= "utf-8" %>
<!DOCTYPE html>
<html>
	<head lang= "en" >
		<meta charset= "UTF-8" >
		<%@ include file= "../../lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name= "description" content= "overview &amp; stats" />
		<meta name= "viewport" content= "width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>css/table.css" rel= "stylesheet" />
		
		<script type= "text/javascript" src= "<%=basePath %>js/common.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>lmis/origin_destination_param/js/origin_destination_param.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js"></script>
	</head>
	<body>
		<div class= "page-header" align= "left" >
			<h1>地址参数配置</h1>
		</div>
		<div class= "page-header" style= "margin-left: 20px; margin-bottom: 20px;" >
			<table>
		  		<tr>
		  			<td align= "right" width= "10%"><label class= "blue" for= "contract_id" >合同名称&nbsp;:</label></td>
		  			<td width= "20%">
		  				<select id= "contract_id" name= "contract_id" style= "width: 100%;" data-edit-select= "1" >
		  					<option value="-1">---请选择---</option>
							<c:forEach items= "${contract_name }" var= "contract_name" >
								<option value= "${contract_name.id }" ${queryParam.contract_id == contract_name.id? "selected= 'selected'": "" } >
										${contract_name.contract_name }
									</option>
							</c:forEach>
						</select>
		  			</td>
		  			<td align= "right" width= "10%"><label class= "blue" for= "carrier_code" >物流商名称&nbsp;:</label></td>
		  			<td width= "20%" >
		  				<select id= "carrier_code" name= "carrier_code" style= "width: 100%;" data-edit-select= "1" onchange= "shiftCarrier();" >
		  					<option value= "">---请选择---</option>
							<c:forEach items= "${carrier_name }" var= "carrier_name" >
								<option value= "${carrier_name.transport_code }" ${queryParam.carrier_code == carrier_name.transport_code? "selected= 'selected'": "" } >
										${carrier_name.transport_name }
									</option>
							</c:forEach>
						</select>
		  			</td>
					<td align= "right" width= "10%"><label class= "blue" for= "itemtype_code" >产品类型&nbsp;:</label></td>
					<td width= "20%">
						<select id= "itemtype_code" name= "itemtype_code" style= "width: 100%;" data-edit-select= "1" >
							<option value= "">---请选择---</option>
						</select>
					</td>
		  		</tr>
		  		<tr>
		  			<%--<td align= "right" width= "10%"><label class= "blue" for= "origination" >始发地&nbsp;:</label></td>--%>
		  			<%--<td width= "20%" >--%>
		  				<%--<select id= "origination" name= "origination" style= "width: 100%;" data-edit-select= "1" >--%>
		  					<%--<option value= "">---请选择---</option>--%>
							<%--<c:forEach items= "${origination }" var= "origination" >--%>
								<%--<option value= "${origination.area_name }" ${queryParam.origination == origination.area_name? "selected= 'selected'": "" } >--%>
										<%--${origination.area_name }--%>
									<%--</option>--%>
							<%--</c:forEach>--%>
						<%--</select>--%>
		  			<%--</td>--%>
						<td align= "right" width= "10%"><label class= "blue" for= "province_destination" >始发地省&nbsp;:</label></td>
						<td width= "20%">
							<select id= "province_origin" name= "province_origin" style= "width: 100%;" data-edit-select= "1" onchange= "shiftAreaLocal(1, 'province_origin', 'city_origin', 'state_origin');" >
								<option value= "">---请选择---</option>
								<c:forEach items= "${province_destination }" var= "province_destination" >
									<option value= "${province_destination.area_code }" ${queryParam.province_destination == province_destination.area_name? "selected= 'selected'": "" } >
											${province_destination.area_name }
									</option>
								</c:forEach>
							</select>
						</td>
						<td align= "right" width= "10%"><label class= "blue" for= "city_destination" >始发地市&nbsp;:</label></td>
						<td width= "20%" >
							<select id= "city_origin" name= "city_origin" style= "width: 100%;" data-edit-select= "1" onchange= "shiftAreaLocal(2, '', 'city_origin', 'state_origin');" >
								<option value= "">---请选择---</option>
							</select>
						</td>
						<td align= "right" width= "10%"><label class= "blue" for= "state_origin" >始发地区&nbsp;:</label></td>
						<td width= "20%">
							<select id= "state_origin" name= "state_origin" style= "width: 100%;" data-edit-select= "1" >
								<option value= "">---请选择---</option>
							</select>
						</td>
		  		</tr>
		  		<tr>
		  			<td align= "right" width= "10%"><label class= "blue" for= "province_destination" >目的地省&nbsp;:</label></td>
		  			<td width= "20%">
		  				<select id= "province_destination" name= "province_destination" style= "width: 100%;" data-edit-select= "1" onchange= "shiftAreaLocal(1, 'province_destination', 'city_destination', 'district_destination');" >
		  					<option value= "">---请选择---</option>
		  					<c:forEach items= "${province_destination }" var= "province_destination" >
								<option value= "${province_destination.area_code }" ${queryParam.province_destination == province_destination.area_name? "selected= 'selected'": "" } >
										${province_destination.area_name }
									</option>
							</c:forEach>
						</select>
		  			</td>
		  			<td align= "right" width= "10%"><label class= "blue" for= "city_destination" >目的地市&nbsp;:</label></td>
		  			<td width= "20%" >
		  				<select id= "city_destination" name= "city_destination" style= "width: 100%;" data-edit-select= "1" onchange= "shiftAreaLocal(2, '', 'city_destination', 'district_destination');" >
		  					<option value= "">---请选择---</option>
							
						</select>
		  			</td>
					<td align= "right" width= "10%"><label class= "blue" for= "district_destination" >目的地区&nbsp;:</label></td>
					<td width= "20%">
						<select id= "district_destination" name= "district_destination" style= "width: 100%;" data-edit-select= "1" >
							<option value= "">---请选择---</option>
						</select>
					</td>
		  		</tr>
			</table>
		</div>
		<div style= "margin-top: 10px; margin-bottom: 10px; margin-left: 20px;" >
			<button class= "btn btn-xs btn-pink" onclick= "query();" >
				<i class= "icon-search icon-on-right bigger-110" ></i>查询
			</button>&nbsp;
			<button class= "btn btn-xs btn-primary" onclick= "toForm();" >
				<i class= "icon-edit" ></i>修改
			</button>&nbsp;
			<button class= "btn btn-xs btn-inverse" onclick= "del();" >
				<i class= "icon-trash" ></i>删除
			</button>&nbsp;
			<button class= "btn btn-xs btn-success" onclick= "refresh();" >
				<i class= "icon-refresh" ></i>刷新
			</button>&nbsp;
			<button id= "btn_export" class= "btn btn-xs btn-purple" onclick= "toExport();" >
				<i class= "icon-arrow-down" ></i>导出
			</button>&nbsp;
		</div>
		<div id= "list" >
			<jsp:include page= "/lmis/origin_destination_param/origin_destination_list_div.jsp" flush= "true" />
		</div>
	</body>
</html>
