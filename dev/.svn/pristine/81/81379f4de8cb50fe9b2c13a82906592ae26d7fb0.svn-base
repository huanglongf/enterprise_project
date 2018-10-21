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
	<div class="col-xs-12">
		<div class="clearfix form-group no-margin-bottom no-padding-bottom no-border-bottom">
			<div class="no-margin-bottom no-padding-bottom no-border-bottom" style="width: 100%;" align="center">
				<input id="detail_id" type="hidden" value="${timelinessDetail.id }"/>
				<table style="width: 100%;" class="form_table">
					<tr>
						<td class="right" nowrap="nowrap" width="30%">
							<label class="no-padding-right blue" for="number">
								节点序号&nbsp;: 
							</label>
						</td>
						<td class="left" nowrap="nowrap" width="30%">
							<input id="number" name="number" readonly="readonly" value="${timelinessDetail.number }"/>
						</td>
			  			<td class="left" nowrap="nowrap" width="30%">自生成</td>
					</tr>
					<tr>
						<td class="right" nowrap="nowrap" width="30%">
							<label class="no-padding-right blue" for="node_province">
								到达省&nbsp;: 
							</label>
						</td>
						<td class= "left" nowrap= "nowrap" width= "30%" >
							<select id= "node_province" name= "node_province" style= "width: 100%;" data-edit-select= "1" onchange= "shiftArea(1, 'node_province', 'node_city', 'node_state', 'node_street');" >
			  					<option value= "">---请选择---</option>
								<c:forEach items= "${node_province }" var= "node_province" >
									<option value= "${node_province.area_code }" ${timelinessDetail.province_code == node_province.area_code? "selected = 'selected'": "" } >
										${node_province.area_name }
									</option>
								</c:forEach>
							</select>
						</td>
			  			<td class= "right" nowrap= "nowrap" width= "30%" />
					</tr>
					<tr>
						<td class="right" nowrap="nowrap" width="30%">
							<label class="blue" for="node_city">
								到达市&nbsp;:
							</label>
						</td>
			  			<td class= "left" nowrap= "nowrap" width= "30%" >
			  				<select id= "node_city" name= "node_city" style= "width: 100%;" data-edit-select= "1" onchange= "shiftArea(2, '', 'node_city', 'node_state', 'node_street');" >
			  					<option value= "">---请选择---</option>
			  					<c:forEach items= "${citys }" var= "city" >
									<option value= "${city.area_code }" ${timelinessDetail.city_code == city.area_code? "selected = 'selected'": "" } >
										${city.area_name }
									</option>
								</c:forEach>
							</select>				
			  			</td>
			  			<td class= "right" nowrap= "nowrap" width= "30%" />
					</tr>
					<tr>
						<td class= "right" nowrap= "nowrap" width= "25%" >
							<label class= "no-padding-right blue" for= "node_state" >
								到达区县&nbsp;: 
							</label>
						</td>
						<td class= "left" nowrap= "nowrap" width= "15%">
							<select id= "node_state" name= "node_state" style= "width: 100%;" data-edit-select= "1" onchange= "shiftArea(3, '', '', 'node_state', 'node_street');" >
			  					<option value= "" >---请选择---</option>
			  					<c:forEach items= "${states }" var= "state" >
									<option value="${state.area_code }" ${timelinessDetail.state_code == state.area_code? "selected= 'selected'": "" } >
										${state.area_name }
									</option>
								</c:forEach>
							</select>
						</td>
			  			<td class= "right" nowrap= "nowrap" width= "30%" />
					</tr>
					<tr>
						<td class="right" nowrap="nowrap" width="15%">
							<label class="blue" for="node_street">
								到达街道&nbsp;:
							</label>
						</td>
			  			<td class= "left" nowrap= "nowrap" width= "15%" >
			  				<select id="node_street" name= "node_street" style= "width: 100%;" data-edit-select= "1" >
			  					<option value= "" >---请选择---</option>
			  					<c:forEach items= "${streets }" var= "street" >
									<option value= "${street.area_code }" ${timelinessDetail.state_code == street.area_code? "selected = 'selected'": "" } >
										${street.area_name }
									</option>
								</c:forEach>
							</select>
			  			</td>
			  			<td class= "right" nowrap= "nowrap" width= "30%" />
					</tr>
					<tr>
						<td class="right" nowrap="nowrap" width="30%">
							<label class="blue" for="efficiency">
								时效&nbsp;:
							</label>
						</td>
			  			<td class= "left" nowrap= "nowrap" width= "30%" >
			  				<input id= "efficiency" name= "efficiency" value= "${timelinessDetail.efficiency }" />
			  				<select id= "efficiency_unit" name="efficiency_unit">
			  					<option value= "0" ${timelinessDetail.efficiency_unit == "0"? "selected= 'selected'": "" } >小时</option>
			  					<option value= "1" ${timelinessDetail.efficiency_unit == "1"? "selected= 'selected'": "" } >天</option>
			  				</select>
			  			</td>
			  			<td class= "right" nowrap= "nowrap" width= "30%" />
					</tr>
					<tr>
						<td class= "right" nowrap= "nowrap" width= "30%" >
							<label class= "blue" for= "warningtype_code" >
								预警类型&nbsp;:
							</label>
						</td>
			  			<td class= "left" nowrap= "nowrap" width= "30%" >
			  				<select id= "warningtype_code" name= "warningtype_code" style= "width: 100%;" data-edit-select= "1" >
			  					<option value="">---请选择---</option>
			  					<c:forEach items="${warninginfoMaintainMasters }" var="warninginfoMaintainMaster">
									<option value="${warninginfoMaintainMaster.warningtype_code }" ${timelinessDetail.warningtype_code == warninginfoMaintainMaster.warningtype_code? "selected = 'selected'": "" } >
										${warninginfoMaintainMaster.warningtype_name }
									</option>
								</c:forEach>
			  				</select>
			  			</td>
			  			<td class="right" nowrap="nowrap" width="30%"/>
					</tr>
					<tr>
						<td width="30%" />
						<td class="left" width="30%">
							<div style="width : 100%" class="form-actions no-margin-bottom no-padding-bottom no-border-bottom" align="center">
								<button class="btn btn-info" type="button" onclick = "saveDetail('${pid }');">
									<i class="icon-ok bigger-110"></i> 提交
								</button>
								&nbsp;
								<button class="btn" type="reset" onclick="backDetail('${pid }');">
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
