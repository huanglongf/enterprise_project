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
	
	<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/table.css" />
	
    <script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
	<script type="text/javascript" src="<%=basePath %>validator/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
	<script type="text/javascript" src="<%=basePath %>assets/js/date-time/bootstrap-timepicker.min.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
	<script type="text/javascript" src="<%=basePath %>lmis/warehouse_management/js/warehouse.js"></script>
	<script>
		if($("#province").val() == ""){
			$("#city").next().attr("disabled", "disabled");
		}
		if($("#city").val() == ""){
			$("#state").next().attr("disabled", "disabled");
		}
		if($("#state").val() == ""){
			$("#street").next().attr("disabled", "disabled");
		}
	</script>
</head>
<body>
	<div class="page-header" align="left">
		<h1>逻辑仓信息创建/编辑</h1>
	</div>
	<div class="col-xs-12">
		<div class="clearfix form-group no-margin-bottom no-padding-bottom no-border-bottom">
			<div class="no-margin-bottom no-padding-bottom no-border-bottom" style="width: 100%;" align="center">
				<input id="id" type="hidden" value="${whs.id }"/>
				<table style="width: 100%;" class="form_table">
					<tr>
						<td class="right" nowrap="nowrap" width="25%">
							<label class="no-padding-right blue" for="warehouse_code">
								逻辑仓代码&nbsp;: 
							</label>
						</td>
						<td class="left" nowrap="nowrap" width="15%">
							<input id="warehouse_code" name="warehouse_code" value="${whs.warehouse_code }"/>
						</td>
						<td class="right" nowrap="nowrap" width="15%">
							<label class="no-padding-right blue" for="warehouse_name">
								逻辑仓名称&nbsp;:
							</label>
						</td>
			  			<td class="left" nowrap="nowrap" width="15%">
			  				<input id="warehouse_name" name="warehouse_name" value="${whs.warehouse_name }"/>
			  			</td>
			  			<td class="right" nowrap="nowrap" width="30%"/>
					</tr>
					<tr>
						<td class="right" nowrap="nowrap" width="25%">
							<label class="no-padding-right blue" for="province">
								所在地省&nbsp;: 
							</label>
						</td>
						<td class="left" nowrap="nowrap" width="15%">
							<select 
							id="province" 
							name="province" 
							style="width: 100%;" 
							data-edit-select="1" 
							onchange="shiftArea(1, 'province', 'city', 'state', 'street');">
			  					<option value= "" >---请选择---</option>
								<c:forEach items= "${province }" var= "province" >
									<option value= "${province.area_code }" ${whs.province == province.area_code? "selected= 'selected'": "" } >
										${province.area_name }
									</option>
								</c:forEach>
							</select>
						</td>
						<td class="right" nowrap="nowrap" width="15%">
							<label class="no-padding-right blue" for="city">
								所在地市&nbsp;:
							</label>
						</td>
			  			<td class="left" nowrap="nowrap" width="15%">
			  				<select 
			  				id="city" 
			  				name="city" 
			  				style="width: 100%;" 
			  				data-edit-select="1" 
			  				onchange="shiftArea(2, '', 'city', 'state', 'street');">
			  					<option value= "" >---请选择---</option>
			  					<c:forEach items= "${citys }" var= "city" >
									<option value= "${city.area_code }" ${whs.city == city.area_code? "selected = 'selected'": "" } >
										${city.area_name }
									</option>
								</c:forEach>
							</select>				
			  			</td>
			  			<td class="right" nowrap="nowrap" width="30%"/>
					</tr>
					<tr>
						<td class="right" nowrap="nowrap" width="25%">
							<label class="no-padding-right blue" for="state">
								所在地区县&nbsp;: 
							</label>
						</td>
						<td class="left" nowrap="nowrap" width="15%">
							<select 
							id="state" 
							name="state" 
							style="width: 100%;" 
							data-edit-select="1" 
							onchange="shiftArea(3, '', '', 'state', 'street');">
			  					<option value= "" >---请选择---</option>
			  					<c:forEach items= "${states }" var= "state" >
									<option value="${state.area_code }" ${whs.state == state.area_code? "selected = 'selected'": "" } >
										${state.area_name}
									</option>
								</c:forEach>
							</select>
						</td>
						<td class="right" nowrap="nowrap" width="15%">
							<label class="blue" for="street">
								所在地街道&nbsp;:
							</label>
						</td>
			  			<td class="left" nowrap="nowrap" width="15%">
			  				<select 
			  				id="street" 
			  				name="street" 
			  				style="width: 100%;" 
			  				data-edit-select="1">
			  					<option value= "">---请选择---</option>
			  					<c:forEach items= "${streets }" var= "street" >
									<option value= "${street.area_code }" ${whs.street == street.area_code? "selected= 'selected'": "" } >
										${street.area_name}
									</option>
								</c:forEach>
							</select>
			  			</td>
			  			<td class="right" nowrap="nowrap" width="30%"/>
					</tr>
					<tr>
						<td class="right" nowrap="nowrap" width="25%">
							<label class="no-padding-right blue" for="address">
								详细地址&nbsp;: 
							</label>
						</td>
						<td class="left" nowrap="nowrap" width="15%">
							<input id="address" name="address" style="width: 100%;" value="${whs.address }"/>
						</td>
						<td class="right" nowrap="nowrap" width="15%">
							<label class="blue" for="street">
								联系人&nbsp;:
							</label>
						</td>
			  			<td class="left" nowrap="nowrap" width="15%">
			  				<input id="contact" name="contact" style="width: 100%;" value="${whs.contact }"/>
			  			</td>
			  			<td class="right" nowrap="nowrap" width="30%"/>
					</tr>
					<tr>
						<td class="right" nowrap="nowrap" width="25%">
							<label class="no-padding-right blue" for="phone">
								联系电话&nbsp;: 
							</label>
						</td>
						<td class="left" nowrap="nowrap" width="15%">
							<input id="phone" name="phone" style="width: 100%;" value="${whs.phone }"/>
						</td>
						<td class="right" nowrap="nowrap" width="15%">
							<label class="blue" for="street">
								仓库类型&nbsp;:
							</label>
						</td>
			  			<td class= "left" nowrap= "nowrap" width= "15%" >
			  				<select id= "warehouse_type" name= "warehouse_type" style= "width: 100%;" >
			  					<option value= "0" ${whs.warehouse_type == "0"? "selected= 'selected'": "" } >自营仓</option>
			  					<option value= "1" ${whs.warehouse_type == "1"? "selected= 'selected'": "" } >外包仓</option>
			  				</select>
			  			</td>
			  			<td class= "right" nowrap= "nowrap" width= "30%" />
					</tr>
					<tr>
						<td class="right" nowrap="nowrap" width="25%">
							<label class="no-padding-right blue" for="need_balance">
								是否结算&nbsp;: 
							</label>
						</td>
						<td class="left" nowrap="nowrap" width="15%">
							<select id= "need_balance" name= "need_balance" style= "width: 100%;" >
							    <option value= "" >---请选择---</option>
			  					<option value= "0" ${whs.need_balance == "0"? "selected= 'selected'": "" }>不结算</option>
			  					<option value= "1" ${whs.need_balance == "1"? "selected= 'selected'": "" }>需要结算</option>
			  				</select>
						</td>
					</tr>
					<tr>
						<td width="25%" />
						<td class="left" width="45%" colspan="3">
							<div style="width : 100%" class="form-actions no-margin-bottom no-padding-bottom no-border-bottom" align="center">
								<button class="btn btn-info" type="button" onclick = "save();">
									<i class="icon-ok bigger-110"></i> 提交
								</button>
								&nbsp; &nbsp; &nbsp;
								<button class="btn" type="reset" onclick="back();">
									<i class="icon-undo bigger-110"></i> 返回
								</button>
							</div>		
						</td>
						<td class="right" nowrap="nowrap" width="30%"/>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
