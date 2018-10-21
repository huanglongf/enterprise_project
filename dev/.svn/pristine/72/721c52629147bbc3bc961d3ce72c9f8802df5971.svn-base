<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/lmis/yuriy.jsp"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
<link type="text/css" rel="stylesheet" href="<%=basePath %>My97DatePicker/skin/WdatePicker.css">

<script type="text/javascript" src="<%=basePath %>assets/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
<script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
<script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

	$(function(){
		
		$('#date').daterangepicker(null, function(start, end, label) {
	    	console.log(start.toISOString(), end.toISOString(), label);
	  	});
		
		$('#date_2').daterangepicker(null, function(start, end, label) {
	    	console.log(start.toISOString(), end.toISOString(), label);
	  	});
		
	});

	/**
	 * checkbox(全选&反选)
	 * 
	 * items 复选框的name
	 */
	function inverseCkb(items) {
		$('[name=' + items + ']:checkbox').each(function() {
			this.checked = !this.checked;
		});
	}

	/**
	 * 查询
	 */
	function find() {
		var name = $("#name").val();
		var menuname = $("#menuname").val();
		openDiv('${root}control/powerController/powerList.do?name=' + name
				+ '&menu_name=' + menuname);
	}
	
	function pageJump() {
		openDiv('${root}control/contractController/list.do?startRow='
				+ $("#startRow").val() + '&endRow=' + $("#startRow").val()
				+ "&pageIndex=" + $("#pageIndex").val() + "&pageSize="
				+ $("#pageSize").val());
	}
	
</script>
</head>

<body>
	<div style="margin-left: 20px; margin-bottom: 20px;">
		<table>
			<tr>
				<td>成本中心编码:</td>
				<td class="col-xs-5">
					<select class="selectpicker" style="width:100%">
						<option>---请选择---</option>
						<option>01002019</option>
						<option>01002014</option>
					</select>
				</td>
				<td>系统仓:</td>
				<td class="col-xs-5">
					<select class="selectpicker" style="width:100%">
						<option>---请选择---</option>
						<option>吴江新地大件仓</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>日期:</td>
				<td class="col-xs-5">
             		<div class="input-prepend input-group" style="width: 100%">
               			<span class="add-on input-group-addon">
               				<i class="glyphicon glyphicon-calendar fa fa-calendar"/>
               			</span>
               			<input type="text" readonly name="date" id="date" class="form-control"/> 
             		</div>
				</td>
				<td>区域:</td>
				<td class="col-xs-5">
					<select class="selectpicker" style="width:100%">
						<option>---请选择---</option>
						<option>吴江－新地</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>物理仓:</td>
				<td class="col-xs-5">
					<select class="selectpicker" style="width:100%">
						<option>---请选择---</option>
						<option>1号库</option>
					</select>
				</td>
				<td>部门:</td>
				<td class="col-xs-5">
					<select class="selectpicker" style="width:100%">
						<option>---请选择---</option>
						<option>大件组</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>店铺:</td>
				<td class="col-xs-5">
					<select class="selectpicker" style="width:100%">
						<option>---请选择---</option>
						<option>OSIM傲胜官方旗舰店-京东商城</option>
					</select>
				</td>
				<td>增值服务类型:</td>
				<td class="col-xs-5">
					<select class="selectpicker" style="width:100%">
						<option>---请选择---</option>
						<option>取消订单拦截</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>店铺申请人:</td>
				<td class="col-xs-5">
					<select class="selectpicker" style="width:100%">
						<option>---请选择---</option>
						<option>店铺</option>
					</select>
				</td>
				<td>仓库确认人:</td>
				<td class="col-xs-5">
					<select class="selectpicker" style="width:100%">
						<option>---请选择---</option>
						<option>ANDON</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>结算标识:</td>
				<td class="col-xs-5">
					<select class="selectpicker" style="width:100%">
						<option>已结算</option>
						<option>未结算</option>
					</select>
				</td>
			</tr>
		</table>
	</div>
	<div style="margin-top: 10px; margin-left: 10px; margin-bottom: 10px;">
		<button class="btn btn-xs btn-pink" onclick="find();">
			<i class="icon-search icon-on-right bigger-110"></i>查询
		</button>
		&nbsp;
	</div>
	<div style="height: 400px; overflow: auto; overflow:scroll; border: solid #F2F2F2 1px; margin-top: 22px;">
		<table class="table table-striped">
			<thead align="center">
				<tr>
					<td nowrap="nowrap"><input type="checkbox" id="checkAll"onclick="inverseCkb('ckb')" /></td>
					<td nowrap="nowrap">成本中心编码</td>
					<td nowrap="nowrap">成本中心</td>
					<td nowrap="nowrap">系统仓</td>
					<td nowrap="nowrap">仓+成本中心</td>
					<td nowrap="nowrap">序号</td>
					<td nowrap="nowrap">日期</td>
					<td nowrap="nowrap">区域</td>
					<td nowrap="nowrap">物理仓</td>
					<td nowrap="nowrap">部门</td>
					<td nowrap="nowrap">店铺</td>
					<td nowrap="nowrap">增值服务类型</td>
					<td nowrap="nowrap">服务说明</td>
					<td nowrap="nowrap">WMS指令</td>
					<td nowrap="nowrap">单位</td>
					<td nowrap="nowrap">数量</td>
					<td nowrap="nowrap">单价</td>
					<td nowrap="nowrap">店铺申请人</td>
					<td nowrap="nowrap">仓库确认人</td>
					<td nowrap="nowrap">备注</td>
					<td nowrap="nowrap">结算标识</td>
					<td nowrap="nowrap">创建人</td>
					<td nowrap="nowrap">创建时间</td>
					<td nowrap="nowrap">更新人</td>
					<td nowrap="nowrap">更新时间</td>
				</tr>
				<tr>
					<td/>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right"> 
							<select style="text-align: center;">
								<option value="0">=</option>
								<option value="1">></option>
								<option value="2">>=</option>
								<option value="3"><</option>
								<option value="4"><=</option>
								<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							<select style="text-align: center;">
									<option value="0">=</option>
									<option value="1">></option>
									<option value="2">>=</option>
									<option value="3"><</option>
									<option value="4"><=</option>
									<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right"> 
							<select style="text-align: center;">
								<option value="0">=</option>
								<option value="1">></option>
								<option value="2">>=</option>
								<option value="3"><</option>
								<option value="4"><=</option>
								<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							<select style="text-align: center;">
									<option value="0">=</option>
									<option value="1">></option>
									<option value="2">>=</option>
									<option value="3"><</option>
									<option value="4"><=</option>
									<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							<select style="text-align: center;">
									<option value="0">=</option>
									<option value="1">></option>
									<option value="2">>=</option>
									<option value="3"><</option>
									<option value="4"><=</option>
									<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<div class="input-prepend input-group" style="width: 100%">
	               			<span class="add-on input-group-addon">
	               				<i class="glyphicon glyphicon-calendar fa fa-calendar"/>
	               			</span>
	               			<input type="text" readonly style="width: 200px"  
	               				name="date_2" id="date_2" class="form-control"/> 
	             		</div>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							<select style="text-align: center;">
									<option value="0">=</option>
									<option value="1">></option>
									<option value="2">>=</option>
									<option value="3"><</option>
									<option value="4"><=</option>
									<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							<select style="text-align: center;">
									<option value="0">=</option>
									<option value="1">></option>
									<option value="2">>=</option>
									<option value="3"><</option>
									<option value="4"><=</option>
									<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							<select style="text-align: center;">
									<option value="0">=</option>
									<option value="1">></option>
									<option value="2">>=</option>
									<option value="3"><</option>
									<option value="4"><=</option>
									<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							<select style="text-align: center;">
									<option value="0">=</option>
									<option value="1">></option>
									<option value="2">>=</option>
									<option value="3"><</option>
									<option value="4"><=</option>
									<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							<select style="text-align: center;">
									<option value="0">=</option>
									<option value="1">></option>
									<option value="2">>=</option>
									<option value="3"><</option>
									<option value="4"><=</option>
									<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							<select style="text-align: center;">
									<option value="0">=</option>
									<option value="1">></option>
									<option value="2">>=</option>
									<option value="3"><</option>
									<option value="4"><=</option>
									<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							<select style="text-align: center;">
									<option value="0">=</option>
									<option value="1">></option>
									<option value="2">>=</option>
									<option value="3"><</option>
									<option value="4"><=</option>
									<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							<select style="text-align: center;">
									<option value="0">=</option>
									<option value="1">></option>
									<option value="2">>=</option>
									<option value="3"><</option>
									<option value="4"><=</option>
									<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							<select style="text-align: center;">
									<option value="0">=</option>
									<option value="1">></option>
									<option value="2">>=</option>
									<option value="3"><</option>
									<option value="4"><=</option>
									<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right"> 
							<select style="text-align: center;">
								<option value="0">=</option>
								<option value="1">></option>
								<option value="2">>=</option>
								<option value="3"><</option>
								<option value="4"><=</option>
								<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right"> 
							<select style="text-align: center;">
								<option value="0">=</option>
								<option value="1">></option>
								<option value="2">>=</option>
								<option value="3"><</option>
								<option value="4"><=</option>
								<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right"> 
							<select style="text-align: center;">
								<option value="0">=</option>
								<option value="1">></option>
								<option value="2">>=</option>
								<option value="3"><</option>
								<option value="4"><=</option>
								<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right"> 
							<select style="text-align: center;">
								<option value="0">=</option>
								<option value="1">></option>
								<option value="2">>=</option>
								<option value="3"><</option>
								<option value="4"><=</option>
								<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right"> 
							<select style="text-align: center;">
								<option value="0">=</option>
								<option value="1">></option>
								<option value="2">>=</option>
								<option value="3"><</option>
								<option value="4"><=</option>
								<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right"> 
							<select style="text-align: center;">
								<option value="0">=</option>
								<option value="1">></option>
								<option value="2">>=</option>
								<option value="3"><</option>
								<option value="4"><=</option>
								<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							 <input type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							 	style="height:100%"/>
							 ～
							 <input type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							 	style="height:100%"/>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right"> 
							<select style="text-align: center;">
								<option value="0">=</option>
								<option value="1">></option>
								<option value="2">>=</option>
								<option value="3"><</option>
								<option value="4"><=</option>
								<option value="5">≈</option>
							</select>
							<input type="text" style="width: 70px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						</span>
					</td>
					<td nowrap="nowrap">
						<span class="input-icon input-icon-right">
							 <input type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							 	style="height:100%"/>
							 ～
							 <input type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							 	style="height:100%"/>
						</span>
					</td>
				</tr>
			</thead>
			<tbody align="center">
				<c:forEach items="${power}" var="power">
					<tr>
						<td><input id="ckb" name="ckb" type="checkbox"
							value="${power.id}"></td>
						<td>${power.name}</td>
						<td>${power.menu_name}</td>
						<td>${power.create_date}</td>
						<td>${power.create_person}</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
	<div style="margin-right: 30px; margin-top: 20px;">${pageView.pageView}</div>
</body>
</html>
