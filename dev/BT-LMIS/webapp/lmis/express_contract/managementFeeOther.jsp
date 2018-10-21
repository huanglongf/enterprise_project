<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp"%>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
		</script>
		<![endif]-->

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
			function show_box(id) {
			 jQuery('.widget-box.visible').removeClass('visible');
			 jQuery('#'+id).addClass('visible');
			}
			function login(){
				window.location.href="${root}lmis/center.jsp";
			}

		</script>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link href="<%=basePath %>assets/css/bootstrap.min.css" rel="stylesheet" />
		<link href="<%=basePath %>assets/css/font-awesome.min.css" rel="stylesheet" />
		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=basePath %>assets/css/font-awesome-ie7.min.css" />
		<![endif]-->
		<!-- page specific plugin styles -->
		<!-- fonts -->
		<link rel="stylesheet" href="<%=basePath %>assets/css/ace-fonts.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="<%=basePath %>assets/css/ace.min.css" />
		<link rel="stylesheet" href="<%=basePath %>assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="<%=basePath %>assets/css/ace-skins.min.css" />
		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=basePath %>assets/css/ace-ie.min.css" />
		<![endif]-->
		<!-- inline styles related to this page -->
		<!-- ace settings handler -->
		<script src="<%=basePath %>assets/js/ace-extra.min.js"></script>
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!--[if lt IE 9]>
		<script src="<%=basePath %>assets/js/html5shiv.js"></script>
		<script src="<%=basePath %>assets/js/respond.min.js"></script>
		<![endif]-->
		
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->

		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		
		
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
      	<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
	    <link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
	    <script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
	    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
	    <script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
	    <script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script>
		<script type="text/javascript" src="<%=basePath %>validator/js/Validform_v5.3.2_min.js"></script>
		<script type="text/javascript" src="<%=basePath %>lmis/express_contract/js/express_contract_form.js"></script>
		<script type="text/javascript" src="<%=basePath %>lmis/trans_moudle/js/trans.js"></script>
		<script type="text/javascript" src="<%=basePath %>lmis/express_contract/js/managementFeeOther.js"></script>
	    <!-- BT-JS工具 -->
	    <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<!--Validform  -->
		<link type="text/css" href="<%=basePath %>validator/css/style.css" rel="stylesheet" media="all" />
		<link type="text/css" href="<%=basePath %>validator/css/demo.css" rel="stylesheet" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link href="<%=basePath %>/css/table.css" type="text/css" rel="stylesheet" />
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=basePath %>assets/js/jquery-2.0.3.min.js'>" + "<"+"/script>");
		</script>
		<style type="text/css">
		body{
			text-align: center;
		}
		div{
			text-align: center;
			margin-left: auto;
			margin-right: auto;
		}
		
		</style>
	</head>
	<body style="background: rgba(0,0,0,0);">
		<div class="div_margin">
			<div class="div_margin" style="">
				<span style="color: red">计算公式 : 管理费 = 总费用 * 收费比率</span>
			</div>
			<div>
				<div class="div_margin" style="text-align: center;">
					<label class="control-label blue">
						---已维护规则---
					</label>
				</div>
				<table id="manECList_other" class="with-border" border="1" style="margin-left: auto;margin-right: auto;">
			   		<thead>
				  		<tr class="title">
				  			<td>序号</td>
				  			<td>阶梯方式</td>
				  			<td>区间</td>
				  			<td>单位</td>
							<td>收费率</td>
				  			<td>操作</td>
				  		</tr>
			  		</thead>
			  		<tbody>
			  		</tbody>
				</table>
			</div>
			<div class="add_button">
				<button class="btn btn-xs btn-yellow" onclick='javascript:$("#add_1_other").show();' >
		       		<i class="icon-hdd" ></i>新增
		      	</button>
			</div>
		</div>
		<div id="add_1_other" class="moudle_dashed_border_width_90">
			<div class="div_margin">
				<label class="control-label blue">
					阶梯设置&nbsp;:
					<input id="id" name="id" type="hidden" value="${cb_id }" />
					<input id="management_fee_type" name="management_fee_type" type="hidden" value="${management_fee_type}" />
				</label>
				<select id="ladder_type_man_other" class="selectpicker" style="width:30%" onchange="shiftPage_5Other()">
					<option selected="selected" value="-1">请选择</option>
					<option value="0">无阶梯</option>
					<option value="1">总费用阶梯价</option>
					<option value="2">超出部分阶梯价</option>
				</select>
			</div>
			<div id="div_10_other" class="moudle_dashed_border_width_90_red">
				<div id="div_11_other" style="display: none">
					<div class="div_margin">
						<label class="control-label blue">
							条件&nbsp;: 
						</label>
						<select id="compare_1_man_other" class="selectpicker">
							<option selected="selected" value="0">></option>
							<option value="1">>=</option>
						</select>
						<label class="control-label black" for="payAll_1_man_other">
							总费用&nbsp; 
						</label>
						<input id="payAll_1_man_other" name="payAll_1_man" type="text"/>
						<select id="payAll_1_uom_man_other" class="selectpicker">
							<option selected="selected" value="0">元</option>
						</select>
						<select id="rel_man_other" class="selectpicker">
							<option selected="selected" value="0">并且</option>
						</select>
					</div>
					<div class="div_margin">
						<label class="control-label blue">
							条件&nbsp;:
						</label>
						<select id="compare_2_man_other" class="selectpicker">
							<option selected="selected" value="2"><</option>
							<option value="3"><=</option>
						</select>
						<label class="control-label black" for="payAll_2_man_other">
							总费用&nbsp; 
						</label>
						<input id="payAll_2_man_other" name="payAll_2_man" type="text"/>
						<select id="payAll_2_uom_man_other" class="selectpicker">
							<option selected="selected" value="0">元</option>
						</select>
					</div>
				</div>
				<div class="div_margin">
					<label class="control-label blue" for="charge_percent_man_other">
						收费率&nbsp;: 
					</label>
					<input id="charge_percent_man_other" name="charge_percent_man" type="text"/>
					<select id="charge_percent_uom_man_other" class="selectpicker">
						<option selected="selected" value="0">%</option>
					</select>
				</div>
				<div class="div_margin">
					<button class="btn btn-xs btn-yellow" onclick="saveManagementECOther('${management_fee_type}');" >
		           		<i class="icon-save" ></i>保存
		           	</button>
		       		<button class="btn btn-xs btn-grey" type="button" onclick="returnManagementOther();">
						<i class="icon-undo" ></i>返回
		   			</button>
				</div>
			</div>
		</div>
</body>