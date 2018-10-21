<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
	<meta charset="UTF-8">
	<%@ include file="/lmis/yuriy.jsp"%>
	<title>LMIS</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="description" content="overview &amp; stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>assets/css/bootstrap-timepicker.css" />
	<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/table.css" />
	<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/dialog.css" />
	
    <script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
	<script type="text/javascript" src="<%=basePath %>validator/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
	<script type="text/javascript" src="<%=basePath %>assets/js/date-time/bootstrap-timepicker.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
	<script type="text/javascript" src="<%=basePath %>radar/receive_deadline/js/receive_deadline.js"></script>
	<script type="text/javascript" src="<%=basePath %>radar/receive_deadline/js/receive_deadline_form.js"></script>
</head>
<body>
	<div class= "page-header" align= "left" >
		<h1>快递时效信息创建/编辑</h1>
	</div>
	<div class= "col-xs-12" >
		<div class= "clearfix form-group no-margin-bottom no-padding-bottom no-border-bottom" >
			<div class= "no-margin-bottom no-padding-bottom no-border-bottom" style= "width: 100%;" align= "center" >
				<input id= "id" type= "hidden" value= "${receiveDeadline.id }" />
				<table style= "width: 100%;" class= "form_table" >
					<tr>
						<td class="right" nowrap="nowrap" width="25%">
							<label class="no-padding-right blue" for="express">
								物流服务商&nbsp;: 
							</label>
						</td>
						<td class="left" nowrap="nowrap" width="15%">
							<select id="express" name="express" style="width: 100%;" data-edit-select="1" onchange="shiftExpress();">
			  					<option value= "">---请选择---</option>
								<c:forEach items= "${expressList }" var= "expressList" >
									<option value="${expressList.transport_code }" ${receiveDeadline.express_code == expressList.transport_code? "selected= 'selected'": "" } >
										${expressList.transport_name }
									</option>
								</c:forEach>
							</select>
						</td>
						<td class="right" nowrap="nowrap" width="15%">
							<label class="no-padding-right blue" for="product_type">
								产品类型&nbsp;:
							</label>
						</td>
			  			<td class="left" nowrap="nowrap" width="15%">
			  				<select id="product_type" name="product_type" style="width: 100%;" data-edit-select="1">
			  					<option value= "">---请选择---</option>
		  						<c:forEach items= "${productType }" var= "productType" >
									<option value= "${productType.product_type_code }" ${receiveDeadline.producttype_code == productType.product_type_code? "selected= 'selected'": "" } >
										${productType.product_type_name }
									</option>
								</c:forEach>
							</select>
			  			</td>
			  			<td class="right" nowrap="nowrap" width="30%"/>
					</tr>
					<tr>
						<td class="right" nowrap="nowrap" width="25%">
							<label class="no-padding-right blue" for="warehouse">
								揽件仓库&nbsp;: 
							</label>
						</td>
						<td class="left" nowrap="nowrap" width="15%">
							<select id="warehouse" name="warehouse" style="width: 100%;" data-edit-select="1">
			  					<option value= "" >---请选择---</option>
								<c:forEach items= "${warehouses }" var= "warehouse" >
									<option value= "${warehouse.warehouse_code }" ${receiveDeadline.warehouse_code == warehouse.warehouse_code? "selected= 'selected'": "" } >
										${warehouse.warehouse_name }
									</option>
								</c:forEach>
							</select>
						</td>
						<td class="right" nowrap="nowrap" width="15%">
							<label class="no-padding-right blue" for="receive_deadline_input">
								揽件截止时间&nbsp;:
							</label>
						</td>
			  			<td class="left" nowrap="nowrap" width="15%">
			  				<div class="input-group bootstrap-timepicker">
								<input id="receive_deadline_input" type="text" class="form-control" value = "${receiveDeadline.embrace_time }" />
								<span class="input-group-addon">
									<i class="icon-time bigger-110"></i>
								</span>
							</div>
			  			</td>
			  			<td class="right" nowrap="nowrap" width="30%"/>
					</tr>
					<tr>
						<td class="right" nowrap="nowrap" width="25%">
							<label class="no-padding-right blue" for="destination_province">
								到达省&nbsp;: 
							</label>
						</td>
						<td class="left" nowrap="nowrap" width="15%">
							<select id= "destination_province" name= "destination_province" style= "width: 100%;" data-edit-select= "1" onchange= "shiftArea(1, 'destination_province', 'destination_city', 'destination_state', 'destination_street');" >
			  					<option value= "">---请选择---</option>
								<c:forEach items= "${destination_province }" var= "destination_province" >
									<option value= "${destination_province.area_code }" ${receiveDeadline.province_code == destination_province.area_code? "selected= 'selected'": "" } >
										${destination_province.area_name }
									</option>
								</c:forEach>
							</select>
						</td>
						<td class="right" nowrap="nowrap" width="15%">
							<label class="no-padding-right blue" for="destination_city">
								到达市&nbsp;:
							</label>
						</td>
			  			<td class="left" nowrap="nowrap" width="15%">
			  				<select id= "destination_city" name= "destination_city" style= "width: 100%;" data-edit-select= "1" onchange= "shiftArea(2, '', 'destination_city', 'destination_state', 'destination_street');" >
			  					<option value= "" >---请选择---</option>
			  					<c:forEach items= "${citys }" var= "city" >
									<option value= "${city.area_code }" ${receiveDeadline.city_code == city.area_code? "selected= 'selected'": "" } >
										${city.area_name }
									</option>
								</c:forEach>
							</select>				
			  			</td>
			  			<td class= "right" nowrap= "nowrap" width= "30%" />
					</tr>
					<tr>
						<td class="right" nowrap="nowrap" width="25%">
							<label class="no-padding-right blue" for="destination_state">
								到达区县&nbsp;: 
							</label>
						</td>
						<td class= "left" nowrap= "nowrap" width= "15%" >
							<select id= "destination_state" name= "destination_state" style= "width: 100%;" data-edit-select= "1" onchange= "shiftArea(3, '', '', 'destination_state', 'destination_street');" >
			  					<option value= "" >---请选择---</option>
			  					<c:forEach items= "${states }" var= "state" >
									<option value= "${state.area_code }" ${receiveDeadline.state_code == state.area_code? "selected= 'selected'" : "" } >
										${state.area_name }
									</option>
								</c:forEach>
							</select>
						</td>
						<td class= "right" nowrap= "nowrap" width= "15%" >
							<label class= "no-padding-right blue" for= "destination_street" >
								到达街道&nbsp;:
							</label>
						</td>
			  			<td class= "left" nowrap= "nowrap" width= "15%">
			  				<select id= "destination_street" name= "destination_street" style= "width: 100%;" data-edit-select= "1" >
			  					<option value= "" >---请选择---</option>
			  					<c:forEach items= "${streets }" var= "street" >
									<option value= "${street.area_code }" ${receiveDeadline.state_code == street.area_code? "selected= 'selected'": "" } >
										${street.area_name }
									</option>
								</c:forEach>
							</select>
			  			</td>
			  			<td class= "right" nowrap= "nowrap" width= "30%" />
					</tr>
					<tr>
						<td width= "25%" />
						<td class= "left" width= "45%" colspan= "3" >
							<div style= "width: 100%" class= "form-actions no-margin-bottom no-padding-bottom no-border-bottom" align= "center" >
								<button class= "btn btn-info" type= "button" onclick= "save();" >
									<i class= "icon-ok bigger-110" ></i>提交
								</button>
								&nbsp;&nbsp;&nbsp;
								<button class= "btn" type= "reset" onclick= "back();" >
									<i class= "icon-undo bigger-110" ></i>返回
								</button>
							</div>		
						</td>
						<td class="right" nowrap="nowrap" width="30%"/>
					</tr>
				</table>
			</div>
		</div>
		<div id= "timeliness_detail_list" class= "form-group" style= "width: 100%; display: none;" >
			<%@ include file= "/radar/receive_deadline/timeliness_detail_list.jsp" %>
		</div>
	</div>
</body>
</html>
