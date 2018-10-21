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
	
	<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
	<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
	<script type="text/javascript" src="<%=basePath %>assets/js/date-time/bootstrap-timepicker.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/validateForm.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
	<script type="text/javascript" src="<%=basePath %>radar/physical_warehouse/js/physical_warehouse_form.js"></script>
</head>
<body>
	<div class="page-header" align="left">
		<h1>物理仓信息创建/编辑</h1>
	</div>
	<div class= "col-xs-12" >
		<div class= "clearfix form-group no-margin-bottom no-padding-bottom no-border-bottom" >
			<div class= "no-margin-bottom no-padding-bottom no-border-bottom" style= "width: 100%;" align= "center" >
				<input id= "id" type= "hidden" value= "${physicalWarehouse.id }" />
				<table style= "width: 100%;" class= "form_table" >
					<tr>
						<td class= "right" nowrap= "nowrap" width= "15%" >
							<label class= "no-padding-right blue" for= "physical_warehouse_code" >
								物理仓代码&nbsp;:
							</label>
						</td>
						<td class= "left" nowrap= "nowrap" width= "15%" >
						   <input id= "physical_warehouse_code" name= "physical_warehouse_code" value= "${physicalWarehouse.warehouse_code }" ${not empty physicalWarehouse.id? "readonly= 'readonly'": "" } />
						</td>
						<td class= "right" nowrap= "nowrap" width= "15%" >
							<label class= "no-padding-right blue" for= "physical_warehouse_name" >
								物理仓名称&nbsp;:
							</label>
						</td>
			  			<td class= "left" nowrap= "nowrap" width= "15%" >
					       <input id= "physical_warehouse_name" name= "physical_warehouse_name" value= "${physicalWarehouse.warehouse_name }" />
			  			</td>
			  			<td class= "right" nowrap= "nowrap" width= "30%" />
					</tr>
					<tr>
						<td class= "right" nowrap= "nowrap" width= "25%" >
							<label class="no-padding-right blue" for="province">
								省&nbsp;: 
							</label>
						</td>
						<td class= "left" nowrap= "nowrap" width= "15%" >
							<select id= "province" name= "province" style= "width: 100%;" data-edit-select= "1" onchange= "shiftArea(1, 'province', 'city', 'state', 'street');" >
			  					<option value="">---请选择---</option>
								<c:forEach items= "${province }" var= "province" >
									<option value= "${province.area_code }" ${physicalWarehouse.province_code == province.area_code? "selected= 'selected'": "" } >
										${province.area_name }
									</option>
								</c:forEach>
							</select>
						</td>
						<td class= "right" nowrap= "nowrap" width= "15%" >
							<label class= "no-padding-right blue" for= "city" >
								市&nbsp;:
							</label>
						</td>
			  			<td class= "left" nowrap= "nowrap" width= "15%" >
			  				<select id= "city" name= "city" style= "width: 100%;" data-edit-select= "1" onchange= "shiftArea(2, '', 'city', 'state', 'street');" >
			  					<option value= "" >---请选择---</option>
			  					<c:forEach items= "${citys }" var= "city" >
									<option value= "${city.area_code }" ${physicalWarehouse.city_code == city.area_code? "selected = 'selected'": "" } >
										${city.area_name }
									</option>
								</c:forEach>
							</select>				
			  			</td>
			  			<td class= "right" nowrap= "nowrap" width= "30%" />
					</tr>
					<tr>
						<td class= "right" nowrap= "nowrap" width= "25%" >
							<label class= "no-padding-right blue" for= "state" >
								区县&nbsp;: 
							</label>
						</td>
						<td class= "left" nowrap= "nowrap" width= "15%" >
							<select id= "state" name= "state" style= "width: 100%;" data-edit-select= "1" onchange= "shiftArea(3, '', '', 'state', 'street');" >
			  					<option value= "">---请选择---</option>
			  					<c:forEach items= "${states }" var= "state" >
									<option value= "${state.area_code }" ${physicalWarehouse.state_code == state.area_code? "selected = 'selected'": "" }>
										${state.area_name }
									</option>
								</c:forEach>
							</select>
						</td>
						<td class="right" nowrap="nowrap" width="15%">
							<label class="blue" for="street">
								街道&nbsp;:
							</label>
						</td>
			  			<td class= "left" nowrap= "nowrap" width= "15%" >
			  				<select id= "street" name= "street" style= "width: 100%;" data-edit-select= "1" >
			  					<option value= "" >---请选择---</option>
			  					<c:forEach items= "${streets }" var= "street" >
									<option value= "${street.area_code }" ${physicalWarehouse.state_code == street.area_code? "selected= 'selected'": "" } >
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
									<i class= "icon-ok bigger-110" ></i> 提交
								</button>
								&nbsp; &nbsp; &nbsp;
								<button class= "btn" type= "reset" onclick= "back();" >
									<i class= "icon-undo bigger-110" ></i> 返回
								</button>
							</div>		
						</td>
						<td class= "right" nowrap= "nowrap" width= "30%" />
					</tr>
				</table>
			</div>
		</div>
		<div id= "logic_list" class= "form-group" ${not empty physicalWarehouse.id? "": "style='width: 100%; display: none;'" } >
			<jsp:include page= "/radar/physical_warehouse/logic_warehouse_list.jsp" flush= "true" />
		</div>
		<jsp:include page= "/radar/physical_warehouse/logic_warehouse_form.jsp" flush= "true" />
	</div>
</body>
</html>
