<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp"%>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="<%=basePath%>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath%>daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath%>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath%>daterangepicker/daterangepicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>validator/js/Validform_v5.3.2_min.js"></script>
		<script type="text/javascript" src="<%=basePath%>lmis/express_contract/js/express_contract_form.js"></script>
		<script type="text/javascript" src="<%=basePath%>lmis/trans_moudle/js/trans.js"></script>
		<script type="text/javascript" src="<%=basePath%>lmis/sosp/js/sosp.js"></script>
		<!-- BT-JS工具 -->
		<script type="text/javascript" src="<%=basePath%>js/common.js"></script>
		<!--Validform  -->
		<link type="text/css" href="<%=basePath%>validator/css/style.css" rel="stylesheet" media="all" />
		<link type="text/css" href="<%=basePath%>validator/css/demo.css" rel="stylesheet" />
		<link type="text/css" href="<%=basePath%>css/table.css" rel="stylesheet" />
		<link href="<%=basePath%>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link href="<%=basePath%>/css/table.css" type="text/css" rel="stylesheet" />
	</head>
	<body>
<div  class="moudle_dashed_border_show">
	<div class="div_margin">
		<input id="dbf_id" type="hidden" value="${pP.id }"/>
		打包价包含：
		<input id="carrier_charge_switch" type="checkbox" class="ace ace-switch ace-switch-5" 
	 		onchange="cancelActive('carrierCharge');switchShow('carrierCharge');cancelActive('tabCarrierCharge');" <c:if test="${pP.carrier_charge==0 }">checked = "checked"</c:if>/>
	 	<span class="lbl"></span>
	 	<span>运费</span>
	 	<input id="storage_switch" type="checkbox" class="ace ace-switch ace-switch-5" 
	 		onchange="cancelActive('storage');switchShow('storage');cancelActive('tabCCF');" <c:if test="${pP.storage==0 }">checked = "checked"</c:if> />
	 	<span class="lbl"></span>
	 	<span>仓储费</span>
	 	<input id="operation_switch" type="checkbox" class="ace ace-switch ace-switch-5" 
	 		onchange="cancelActive('operation');switchShow('operation');cancelActive('tabCZF');" <c:if test="${pP.operation==0 }">checked = "checked"</c:if> />
	 	<span class="lbl"></span>
	 	<span>操作费</span>
	 	<input id="consumable_switch" type="checkbox" class="ace ace-switch ace-switch-5" 
	 		onchange="cancelActive('consumable');switchShow('consumable');cancelActive('tabHCF');" <c:if test="${pP.consumable==0 }">checked = "checked"</c:if> />
	 	<span class="lbl"></span>
	 	<span>耗材费</span>
	</div>
	<div class="div_margin_border">
		<div class="div_margin">
			<span style="color: red"> 销售出库打包价 = 销售出库单数 * 单价 </span>
		</div>
		<div class="div_margin">
			单价：<input id="unit_price_dbf" type="text" value="${pP.unit_price }" />
			单位：
			<select id="unit_price_uom_dbf" >
				<option value="0" ${pP.unit_price_uom == 0? "selected= 'selected'": "" } >元/单</option>
			</select>
		</div>
		<div class="div_margin">
			<span>计算公式 : 保价费 = 订单金额 * 百分比</span>
		</div>
<!-- 		<div class="div_margin"> -->
<!-- 			保价费阶梯类型： -->
<!-- 			<select id= "DBFJT" style= "width: 280px;" onchange= "showDBFJT();loadLadder(1);" > -->
<!-- 				<option value= "0" >请选择</option> -->
<%-- 				<option value= "1" ${pP.insurance == 1? "selected= 'selected'": "" } >无阶梯</option> --%>
<%-- 				<option value= "2" ${pP.insurance == 2? "selected= 'selected'": "" } >总费用阶梯</option> --%>
<%-- 				<option value= "3" ${pP.insurance == 3? "selected= 'selected'": "" } >超出部分阶梯</option> --%>
<!-- 			</select> -->
<!-- 			<div id="DBFJT1" style="display: none;"> -->
<!-- 				<div class="div_margin"> -->
<!-- 					<input id="dbf_ladder_id_1" type="hidden"/> -->
<!-- 					<span>订单金额百分比:<input id="percent_dbf_1" name="percent_dbf_1" type="text" /></span>  -->
<!-- 					<select id="percent_uom_dbf_1"> -->
<!-- 						<option value="0">%</option> -->
<!-- 					</select> -->
<!-- 				</div> -->
<!-- 				<div class="div_margin"> -->
<!-- 					<input id="charge_min_flag_dbf_1" type="checkbox" /> -->
<!-- 					<span class="lbl">最低金额:</span> <input id="charge_min_dbf_1" name="charge_min_dbf_1" type="text" /> -->
<!-- 					 <select id="charge_min_uom_dbf_1"> -->
<!-- 						<option value="0">元</option> -->
<!-- 					</select> -->
<!-- 				</div> -->
<!-- 				<div class="div_margin"> -->
<!-- 					<a data-toggle="tab" href="#tab_add"> -->
<!-- 						<button class="btn btn-xs btn-yellow" onclick="save_package_price_ladder(1);"> -->
<!-- 							<i class="icon-hdd"></i>保存 -->
<!-- 						</button> -->
<!-- 					</a> -->
<!-- 				</div>		 -->
<!-- 			</div> -->
<!-- 			<div id="DBFJT2" style="display: none;"> -->
<!-- 				<div class="div_margin"> -->
<!-- 					<span>---已维护的阶梯----</span> -->
<!-- 				</div> -->
<!-- 				<div> -->
<!-- 					<table id="ladder_region_1" border="1" class="with-border"> -->
<!-- 						<thead>	 -->
<!-- 							<tr class="title"> -->
<!-- 								<td>区间</td> -->
<!-- 								<td>收费比率/收费</td> -->
<!-- 								<td>操作</td> -->
<!-- 							</tr> -->
<!-- 						</thead> -->
<!-- 						<tbody> -->
<!-- 						</tbody> -->
<!-- 					</table> -->
<!-- 				</div> -->
<!-- 				<div class="add_button"> -->
<!-- 					<a data-toggle="tab" href="#tab_add"> -->
<!-- 						<button class="btn btn-xs btn-yellow" onclick="changeShow('DBFJTALL')"> -->
<!-- 							<i class="icon-hdd"></i>新增 -->
<!-- 						</button> -->
<!-- 					</a> -->
<!-- 				</div> -->
<!-- 				<div id="DBFJTALL" style="display: none;"> -->
<!-- 					<span style="color: red">计算公式 : 保价费 = 订单金额 * 收费比率</span><br /> -->
<!-- 					<span style="color: red">公式说明：折扣为阶梯区间，需要在下方设置</span><br /> -->
<!-- 					<div class="div_margin"> -->
<!-- 						<span>---阶梯设置---</span> -->
<!-- 					</div> -->
<!-- 					<div class="div_margin"> -->
<!-- 						条件 <select id="compare_1_dbf_1"> -->
<!-- 							<option value="0">></option> -->
<!-- 							<option value="1">>=</option> -->
<!-- 						</select> -->
<!-- 						 订单金额<input id="num_1_dbf_1" type="text" /> -->
<!-- 						<select id="uom_1_dbf_1"><option value="0">元</option></select> -->
<!-- 					</div> -->
<!-- 					<div class="div_margin"> -->
<!-- 						组合方式:<select id="rel_dbf_1"> -->
<!-- 							     <option value="0">并且</option> -->
<!-- 					 		   </select> -->
<!-- 					</div> -->
<!-- 					<div class="div_margin"> -->
<!-- 						条件 <select id="compare_2_dbf_1"> -->
<!-- 							<option value="2"><</option> -->
<!-- 							<option value="3"><=</option> -->
<!-- 						</select> -->
<!-- 						 订单金额<input id="num_2_dbf_1" type="text" /> -->
<!-- 						<select id="uom_2_dbf_1"><option value="0">元</option></select> -->
<!-- 					</div> -->
<!-- 					<div class="div_margin"> -->
<!-- 						 <input id="radio_0" type="radio"  name="radio_0" checked="checked"/>收费比率:<input id="charge_percent_dbf_1" type="text" />&nbsp;<select id="charge_percent_uom_dbf_1"><option value="0">%</option></select> -->
<!-- 					     &nbsp;&nbsp;&nbsp; -->
<!-- 					     <input id="radio_1" type="radio"  name="radio_0" />&nbsp;收费:<input id="charge_dbf_1" type="text" />&nbsp;<select  id="charge_uom_dbf_1"><option value="0">元</option></select> -->
<!-- 					</div> -->
<!-- 					<div class="div_margin"> -->
<!-- 						<a data-toggle="tab" href="#tab_add"> -->
<!-- 							<button class="btn btn-xs btn-yellow" onclick="changeShow('DBFJTALL');save_package_price_ladder(1);"> -->
<!-- 								<i class="icon-hdd"></i>保存 -->
<!-- 							</button> -->
<!-- 						</a> -->
<!-- 					</div>	 -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div id="DBFJT3" style="display: none;"> -->
<!-- 				<div class="div_margin"> -->
<!-- 					<span>---已维护的阶梯----</span> -->
<!-- 				</div> -->
<!-- 				<div> -->
<!-- 					<table id="ladder_region_2" border="1" class="with-border"> -->
<!-- 						<thead>	 -->
<!-- 							<tr class="title"> -->
<!-- 								<td>区间</td> -->
<!-- 								<td>收费比率/收费</td> -->
<!-- 								<td>操作</td> -->
<!-- 							</tr> -->
<!-- 						</thead> -->
<!-- 						<tbody> -->
<!-- 						</tbody> -->
<!-- 					</table> -->
<!-- 				</div> -->
<!-- 				<div class="add_button"> -->
<!-- 					<a data-toggle="tab" href="#tab_add"> -->
<!-- 						<button class="btn btn-xs btn-yellow" onclick="changeShow('DBFJTCCBF')"> -->
<!-- 							<i class="icon-hdd"></i>新增 -->
<!-- 						</button> -->
<!-- 					</a> -->
<!-- 				</div> -->
<!-- 				<div id="DBFJTCCBF" style="display: none;"> -->
<!-- 					<span style="color: red">计算公式 : 保价费 = 订单金额 * 收费比率</span><br /> -->
<!-- 					<span style="color: red">公式说明：折扣为阶梯区间，需要在下方设置</span><br /> -->
<!-- 					<div class="div_margin"> -->
<!-- 						<span>---阶梯设置---</span> -->
<!-- 					</div> -->
<!-- 					<div class="div_margin"> -->
<!-- 						条件 <select id="compare_1_dbf_2"> -->
<!-- 							<option value="0">></option> -->
<!-- 							<option value="1">>=</option> -->
<!-- 						</select> -->
<!-- 						 订单金额<input id="num_1_dbf_2" type="text" /> -->
<!-- 						<select id="uom_1_dbf_2"><option value="0">元</option></select> -->
<!-- 					</div> -->
<!-- 					<div class="div_margin"> -->
<!-- 						组合方式:<select id="rel_dbf_2"> -->
<!-- 							     <option value="0">并且</option> -->
<!-- 					 		   </select> -->
<!-- 					</div> -->
<!-- 					<div class="div_margin"> -->
<!-- 						条件 <select id="compare_2_dbf_2"> -->
<!-- 							<option value="2"><</option> -->
<!-- 							<option value="3"><=</option> -->
<!-- 						</select> -->
<!-- 						 订单金额<input id="num_2_dbf_2" type="text" /> -->
<!-- 						<select id="uom_2_dbf_2"><option value="0">元</option></select> -->
<!-- 					</div> -->
<!-- 					<div class="div_margin"> -->
<!-- 						 <input id="radio_2" type="radio"  name="radio_1" checked="checked"/>收费比率:<input id="charge_percent_dbf_2" type="text" />&nbsp;<select id="charge_percent_uom_dbf_2"><option value="0">%</option></select> -->
<!-- 					     &nbsp;&nbsp;&nbsp; -->
<!-- 					     <input id="radio_3" type="radio"  name="radio_1" />&nbsp;收费:<input id="charge_dbf_2" type="text" />&nbsp;<select  id="charge_uom_dbf_2"><option value="0">元</option></select> -->
<!-- 					</div> -->
<!-- 					<div class="div_margin"> -->
<!-- 						<a data-toggle="tab" href="#tab_add"> -->
<!-- 							<button class="btn btn-xs btn-yellow" onclick="changeShow('DBFJTCCBF');save_package_price_ladder(1);"> -->
<!-- 								<i class="icon-hdd"></i>保存 -->
<!-- 							</button> -->
<!-- 						</a> -->
<!-- 					</div>	 -->
<!-- 				</div>	 -->
<!-- 			</div> -->
<!-- 			<hr> -->
<!-- 		</div> -->
		<div class="div_margin">
			<span style="color: red"> 退货 =（销售出库打包价+委托取件服务费）* 退货单数 </span>
		</div>
		<div class="div_margin">
			退货单价：<input id="return_unit_price_dbf" type="text" value="${pP.return_unit_price }" />
			单位：
			<select id= "return_unit_price_uom_dbf" >
				<option value= "0" ${pP.return_unit_price_uom == 0? "selected= 'selected'": "" } >元/单</option>
			</select>
		</div>
		<div class="div_margin">
			委托取件服务费单价：<input id="delegated_pickup_unit_price_dbf" type="text" value="${pP.delegated_pickup_unit_price }"/>
			单位：
			<select id= "delegated_pickup_unit_price_uom_dbf" >
				<option value= "0" ${pP.delegated_pickup_unit_price_uom == 0? "selected= 'selected'": "" } >元/单</option>
			</select>
		</div>
		<div class="div_margin">
			退货保价费阶梯类型：
			<select id= "DBFJTTH" style= "width: 280px;" onchange= "showDBFJTTH(); loadLadder(0);" >
				<option value= "0">请选择</option>
				<option value= "1" ${pP.return_insurance == 1? "selected= 'selected'": "" } >无阶梯</option>
				<option value= "2" ${pP.return_insurance == 2? "selected= 'selected'": "" } >总费用阶梯</option>
				<option value= "3" ${pP.return_insurance == 3? "selected= 'selected'": "" } >超出部分阶梯</option>
			</select>
			<div id="DBFJTTH1" style="display: none;">
				<div class="div_margin">
					<input id="dbf_ladder_id_2" type="hidden"/>
					<span>订单金额百分比:<input id="percent_dbf_2" name="percent_dbf_2" type="text" value="" /></span> 
					<select id="percent_uom_dbf_2">
						<option value="0">%</option>
					</select>
				</div>
				<div class="div_margin">
					<input id="charge_min_flag_dbf_2" type="checkbox" />
					<span class="lbl">最低金额:</span> <input id="charge_min_dbf_2" name="charge_min_dbf_2" type="text" />
					 <select id="charge_min_uom_dbf_2">
						<option value="0">元</option>
					</select>
				</div>
				<div class="div_margin">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="save_package_price_ladder(0);">
							<i class="icon-hdd"></i>保存
						</button>
					</a>
				</div>	
			</div>
			<div id="DBFJTTH2" style="display: none;">
				<div class="div_margin">
					<span>---已维护的阶梯----</span>
				</div>
				<div>
					<table id="ladder_region_3" border="1" class="with-border">
						<thead>
							<tr class="title">
								<td>区间</td>
								<td>收费比率/收费</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<div class="add_button">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="changeShow('DBFJTTHALL')">
							<i class="icon-hdd"></i>新增
						</button>
					</a>
				</div>
				<div id="DBFJTTHALL" style="display: none;">
					<span style="color: red">计算公式 : 保价费 = 订单金额 * 收费比率</span><br />
					<span style="color: red">公式说明：折扣为阶梯区间，需要在下方设置</span><br />
					<div class="div_margin">
						<span>---阶梯设置---</span>
					</div>
					<div class="div_margin">
						条件 <select id="compare_1_dbf_3">
							<option value="0">></option>
							<option value="1">>=</option>
						</select>
						 订单金额<input id="num_1_dbf_3" type="text" />
						<select id="uom_1_dbf_3"><option value="0">元</option></select>
					</div>
					<div class="div_margin">
						组合方式:<select id="rel_dbf_3">
							     <option value="0">并且</option>
					 		   </select>
					</div>
					<div class="div_margin">
						条件 <select id="compare_2_dbf_3">
							<option value="2"><</option>
							<option value="3"><=</option>
						</select>
						 订单金额<input id="num_2_dbf_3" type="text" />
						<select id="uom_2_dbf_3"><option value="0">元</option></select>
					</div>
					<div class="div_margin">
						 <input id="radio_4" type="radio"  name="radio_2" checked="checked"/>收费比率:<input id="charge_percent_dbf_3" type="text" />&nbsp;<select id="charge_percent_uom_dbf_3"><option value="0">%</option></select>
					     &nbsp;&nbsp;&nbsp;
					     <input id="radio_5" type="radio"  name="radio_2" />&nbsp;收费:<input id="charge_dbf_3" type="text" />&nbsp;<select  id="charge_uom_dbf_3"><option value="0">元</option></select>
					</div>
					<div class="div_margin">
						<a data-toggle="tab" href="#tab_add">
							<button class="btn btn-xs btn-yellow" onclick="changeShow('DBFJTTHALL');save_package_price_ladder(0);">
								<i class="icon-hdd"></i>保存
							</button>
						</a>
					</div>	
				</div>	
			</div>
			<div id="DBFJTTH3" style="display: none;">
				<div class="div_margin">
					<span>---已维护的阶梯----</span>
				</div>
				<div>
					<table id="ladder_region_4" border="1" class="with-border">
						<thead>
							<tr class="title">
								<td>区间</td>
								<td>收费比率/收费</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<div class="add_button">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="changeShow('DBFJTTHCCBF')">
							<i class="icon-hdd"></i>新增
						</button>
					</a>
				</div>
				<div id="DBFJTTHCCBF" style="display: none;">
					<span style="color: red">计算公式 : 保价费 = 订单金额 * 收费比率</span><br />
					<span style="color: red">公式说明：折扣为阶梯区间，需要在下方设置</span><br />
					<div class="div_margin">
						<span>---阶梯设置---</span>
					</div>
					<div class="div_margin">
						条件 <select id="compare_1_dbf_4">
							<option value="0">></option>
							<option value="1">>=</option>
						</select>
						 订单金额<input id="num_1_dbf_4" type="text" />
						<select id="uom_1_dbf_4"><option value="0">元</option></select>
					</div>
					<div class="div_margin">
						组合方式:<select id="rel_dbf_4">
							     <option value="0">并且</option>
					 		   </select>
					</div>
					<div class="div_margin">
						条件 <select id="compare_2_dbf_4">
							<option value="2"><</option>
							<option value="3"><=</option>
						</select>
						 订单金额<input id="num_2_dbf_4" type="text" />
						<select id="uom_2_dbf_4"><option value="0">元</option></select>
					</div>
					<div class="div_margin">
						 <input id="radio_6" type="radio"  name="radio_3" checked="checked"/>收费比率:<input id="charge_percent_dbf_4" type="text" />&nbsp;<select id="charge_percent_uom_dbf_4"><option value="0">%</option></select>
					     &nbsp;&nbsp;&nbsp;
					     <input id="radio_7" type="radio"  name="radio_3" />&nbsp;收费:<input id="charge_dbf_4" type="text" />&nbsp;<select  id="charge_uom_dbf_4"><option value="0">元</option></select>
					</div>
					<div class="div_margin">
						<a data-toggle="tab" href="#tab_add">
							<button class="btn btn-xs btn-yellow" onclick="changeShow('DBFJTTHCCBF');save_package_price_ladder(0);">
								<i class="icon-hdd"></i>保存
							</button>
						</a>
					</div>	
				</div>
			</div>
			<hr>
		</div>
		<div class="div_margin">
			<a data-toggle="tab" href="#tab_add">
				<button class="btn btn-xs btn-yellow" onclick="save_package_price();">
					<i class="icon-hdd"></i>保存
				</button>
			</a>
		</div>	
	</div>
	<div>
		<div class="div_margin">
			<label class="control-label blue">
				管理费
			</label>
			<input 
			id="cb_managementFee_other_dbf" 
			type="checkbox" 
			class="ace ace-switch ace-switch-5" ${dbfFeeFlag == true ? 'checked="checked"' : '' } 
			onchange="managementOtherShow('dbf');"/>
			<span class="lbl"></span>
		</div>
		<!-- 管理费 -->
		<div id="managementFeeOther_dbf" class="moudle_dashed_border" style="width:100%;border:0px;${dbfFeeFlag == true ? 'display:block;' : '' }">
			<iframe id="iframe_dbf" class="with-border" style="overflow: visible; border:0px;margin:0px;padding:0px;background: rgba(0,0,0,0);width: 100%;" src="${root }control/expressContractController/toManagementFeeOther.do?management_fee_type=dbf&cb_id=${cb.id }"></iframe>
		</div>
	</div>
</div>
</body>
</html>