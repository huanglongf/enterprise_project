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
	
		<script type="text/javascript">
			$(document).ready(function() {
				showZZFWFJT($("#id").val());
			});
			//增值服务费保存按钮
			function saveZZFWF() {
				var id = $("#id").val(); //合同ID
				var params = $("#zzfwfForm").serialize();
				$.ajax({
					url : "${root}control/operatingCostController/saveZZFWF.do?id="
							+ id,
					type : "POST",
					data : params,
					dataType : "json",
					success : function(data) {
						alert(data.info);
					},
					error : function(XMLHttpRequest) {
						alert("服务器异常Error: " + XMLHttpRequest.responseText);
					}
				});
			};
			//增值服务费 阶梯
			function showZZFWFJYT() {
				var divId = $("#ZZFWFJT").val();
				var divObj = $("div[id^='ZZFWFJT']");
				for (i = 0; i < divObj.length; i++) {
					if (i == divId) {
						$("#ZZFWFJT" + i).css("display", "block");
					} else {
						$("#ZZFWFJT" + i).css("display", "none");
					}
				}
			};
			function showZZFWFJT(id) {
				var url = root + "/control/operatingCostController/showZZFWFJT.do?tbid=" + id;
				var htm_td = "";
				$.ajax({
					type : "POST",
					url : url,
					data : "",
					dataType : "json",
					success : function(data) {
						var text = "<table class='with-border' border='1'><tr class='title'><td>序号</td><td>区间</td><td>折扣</td><td>操作</td></tr>";
						if (data.status == 'y') {
							for (i = 0; i < data.list.length; i++) {
								htm_td = htm_td + "<tr><td>" + (i + 1)
										+ "</td><td>"
										+ data.list[i].vasd_section
										+ "</td><td>" + data.list[i].vasd_value
										+ "</td><td><a href='javascript:void(0);' onclick='delete8("+ data.list[i].vasd_id+ ");'>删除</a></td></tr>";
							}
							$("zzfwfjtTABLE").html(text + htm_td + "</table>");
						} else {
							$("zzfwfjtTABLE").html(text + "</table>");
						}
					}
				});
			};

			function save8(){
				var id=$("#id").val();			//合同ID
				var params = $("#zzfwfForm").serialize();
				$.ajax({
		            url: "${root}control/operatingCostController/save8.do?cbid="+id,
		            type: "POST",
		            data: params,
		            dataType: "json",
		            success: function(data){
						alert(data.info);
						showZZFWFJT($("#id").val());
		            },error: function(XMLHttpRequest){  
		                alert("服务器异常Error: " + XMLHttpRequest.responseText);  
		            } 
		        });
			};

		    function delete8(id){
		    	if(!confirm("是否删除以下所选数据?")){
		    	  	return;
		    	  	
		    	}
		    	$.ajax({
					type : "POST",
					url: root+"/control/operatingCostController/delete8.do?id="+id,  
					data:null,
					dataType: "json",  
					success : function(data) {
						if(data.status=='y'){
							alert(data.info);
							showZZFWFJT($("#id").val());
						}else{
							alert(data.info);
						}
					}
				});
		    };
		</script>
	</head>
	
	<body>
		<div id="zzfwf_form">
			<form id="zzfwfForm" name="zzfwfForm">
				<hr>
				<span>增值服务费:</span> <input id="osr_addservice_fee"
					name="osr_addservice_fee" type="checkbox"
					<c:if test="${osr.osr_addservice_fee==1}">checked="checked"</c:if>
					class="ace ace-switch ace-switch-5" onchange="changeShow('ZZFWF')" />
				<span class="lbl"></span>
				<div id="ZZFWF"
					<c:if test="${osr.osr_addservice_fee==1}">style="display: block;"</c:if>
					<c:if test="${osr.osr_addservice_fee!=1}">style="display: none;"</c:if>
					align="center" class="moudle_dashed">
					<div class="div_margin_border">
						<span>QC费用：</span> <input id="osr_qc_fee" name="osr_qc_fee"
							type="checkbox"
							<c:if test="${osr.osr_qc_fee==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5" onchange="changeShow('QCFY')" />
						<span class="lbl"></span>
						<div id="QCFY"
							<c:if test="${osr.osr_qc_fee==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_qc_fee!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								单价<input type="text" id="osr_qc_pieceprice"
									name="osr_qc_pieceprice" value="${osr.osr_qc_pieceprice}" /> <select
									id="osr_qc_pieceprice_unit" name="osr_qc_pieceprice_unit">
									<option value="0">元/件</option>
									<option value="1">元/单</option></select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>放赠品费用：</span> <input id="osr_gift_fee" name="osr_gift_fee"
							type="checkbox"
							<c:if test="${osr.osr_gift_fee==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5" onchange="changeShow('FZPFY')" />
						<span class="lbl"></span>
						<div id="FZPFY"
							<c:if test="${osr.osr_gift_fee==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_gift_fee!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								单数单价<input type="text" id="osr_gift_billprice"
									name="osr_gift_billprice" value="${osr.osr_gift_billprice }" />
								<select id="osr_gift_billprice_unit"
									name="osr_gift_billprice_unit"><option value="0">元/单</option></select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>测量尺寸：</span> <input id="osr_mesurement" name="osr_mesurement"
							type="checkbox"
							<c:if test="${osr.osr_mesurement==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5" onchange="changeShow('CLCC')" />
						<span class="lbl"></span>
						<div id="CLCC"
							<c:if test="${osr.osr_mesurement==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_mesurement!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								件数单价<input type="text" id="osr_mesurement_price"
									name="osr_mesurement_price" value="${osr.osr_mesurement_price}" />
								<select id="osr_mesurement_price_unit"
									name="osr_mesurement_price_unit"><option value="0">元/件</option></select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>代贴防伪码：</span> <input id="osr_security_code"
							name="osr_security_code" type="checkbox"
							<c:if test="${osr.osr_security_code==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5" onchange="changeShow('JSDJ')" />
						<span class="lbl"></span>
						<div id="JSDJ"
							<c:if test="${osr.osr_security_code==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_security_code!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								件数单价<input type="text" id="osr_security_code_price"
									name="osr_security_code_price"
									value="${osr.osr_security_code_price }" /> <select
									id="osr_security_code_price_unit"
									name="osr_security_code_price_unit"><option value="0">元/件</option></select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>代贴条码：</span> <input id="osr_itemnum" name="osr_itemnum"
							type="checkbox"
							<c:if test="${osr.osr_itemnum==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5" onchange="changeShow('DTTM')" />
						<span class="lbl"></span>
						<div id="DTTM"
							<c:if test="${osr.osr_itemnum==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_itemnum!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								件数单价<input type="text" id="osr_itemnum_price"
									name="osr_itemnum_price" value="${osr.osr_itemnum_price }" /> <select
									id="osr_itemnum_price_unit" name="osr_itemnum_price_unit"><option
										value="0">元/件</option></select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>吊牌拍照：</span> <input id="osr_drop_photo" name="osr_drop_photo"
							type="checkbox"
							<c:if test="${osr.osr_drop_photo==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5" onchange="changeShow('DPPZ')" />
						<span class="lbl"></span>
						<div id="DPPZ"
							<c:if test="${osr.osr_drop_photo==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_drop_photo!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								件数单价<input type="text" id="osr_drop_photo_price"
									name="osr_drop_photo_price" value="${osr.osr_drop_photo_price}" />
								<select id="osr_drop_photo_price_unit"
									name="osr_drop_photo_price_unit"><option value="0">元/件</option></select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>短信服务费：</span> <input id="osr_message" name="osr_message"
							type="checkbox"
							<c:if test="${osr.osr_message==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5" onchange="changeShow('DXFWF')" />
						<span class="lbl"></span>
						<div id="DXFWF"
							<c:if test="${osr.osr_message==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_message!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								单价<input type="text" id="osr_message_price"
									name="osr_message_price" value="${osr.osr_message_price}" /> <select
									id="osr_message_price_unit" name="osr_message_price_unit"><option
										value="0">元/单</option></select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>额外税费及管理费税：</span> <input id="osr_extra_fee"
							name="osr_extra_fee" type="checkbox"
							<c:if test="${osr.osr_extra_fee==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5" onchange="changeShow('EWSF')" />
						<span class="lbl"></span>
						<div id="EWSF"
							<c:if test="${osr.osr_extra_fee==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_extra_fee!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								单价<input type="text" id="osr_extra_fee_price"
									name="osr_extra_fee_price" value="${osr.osr_extra_fee_price}" />
								<select id="osr_extra_fee_price_unit"
									name="osr_extra_fee_price_unit"><option value="0">元</option></select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>更改包装：</span> <input id="osr_change_package"
							name="osr_change_package" type="checkbox"
							<c:if test="${osr.osr_change_package==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5" onchange="changeShow('GGBZ')" />
						<span class="lbl"></span>
						<div id="GGBZ"
							<c:if test="${osr.osr_change_package==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_change_package!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								件数单价<input type="text" id="osr_change_package_price"
									name="osr_change_package_price"
									value="${osr.osr_change_package_price }" /> <select
									id="osr_change_package_price_unit"
									name="osr_change_package_price_unit"><option value="0">元/件</option></select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>非工作时间作业：</span> <input id="osr_notworkingtime"
							name="osr_notworkingtime" type="checkbox"
							<c:if test="${osr.osr_notworkingtime==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5"
							onchange="changeShow('FGZSJZY')" /> <span class="lbl"></span>
						<div id="FGZSJZY"
							<c:if test="${osr.osr_notworkingtime==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_notworkingtime!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								非工作日<input type="text" id="osr_notworkingtime_price"
									name="osr_notworkingtime_price"
									value="${osr.osr_notworkingtime_price}" /> <select
									id="osr_notworkingtime_price_unit"
									name="osr_notworkingtime_price_unit"><option value="0">元/件</option></select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>货票同行：</span> <input id="osr_waybillpeer"
							name="osr_waybillpeer" type="checkbox"
							<c:if test="${osr.osr_waybillpeer==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5" onchange="changeShow('HPTH')" />
						<span class="lbl"></span>
						<div id="HPTH"
							<c:if test="${osr.osr_waybillpeer==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_waybillpeer!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								单量单价<input type="text" id="osr_waybillpeer_price"
									name="osr_waybillpeer_price"
									value="${osr.osr_waybillpeer_price}" /> <select
									id="osr_waybillpeer_price_unit"
									name="osr_waybillpeer_price_unit"><option value="0">元/单</option></select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>紧急收货：</span> <input id="osr_emergency_receipt"
							name="osr_emergency_receipt" type="checkbox"
							<c:if test="${osr.osr_emergency_receipt==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5" onchange="changeShow('JJSH')" />
						<span class="lbl"></span>
						<div id="JJSH"
							<c:if test="${osr.osr_emergency_receipt==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_emergency_receipt!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								件数单价<input type="text" id="osr_emergency_receipt_price"
									name="osr_emergency_receipt_price"
									value="${osr.osr_emergency_receipt_price}" /> <select
									id="osr_emergency_receipt_price_unit"
									name="osr_emergency_receipt_price_unit"><option
										value="0">元/件</option></select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>取消订单拦截：</span> <input id="osr_cancelorder"
							name="osr_cancelorder" type="checkbox"
							<c:if test="${osr.osr_cancelorder==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5"
							onchange="changeShow('QXDDLJ')" /> <span class="lbl"></span>
						<div id="QXDDLJ"
							<c:if test="${osr.osr_cancelorder==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_cancelorder!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								单价<input type="text" id="osr_cancelorder_price"
									name="osr_cancelorder_price"
									value="${osr.osr_cancelorder_price}" /> <select
									id="osr_cancelorder_price_unit"
									name="osr_cancelorder_price_unit"><option value="0">元/件</option>
									<option value="1">元/单</option></select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>条码制作人工费：</span> <input id="osr_makebarfee"
							name="osr_makebarfee" type="checkbox"
							<c:if test="${osr.osr_makebarfee==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5"
							onchange="changeShow('TMZZRGF')" /> <span class="lbl"></span>
						<div id="TMZZRGF"
							<c:if test="${osr.osr_makebarfee==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_makebarfee!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								件数单价<input type="text" id="osr_makebarfee_price"
									name="osr_makebarfee_price" value="${osr.osr_makebarfee_price}" />
								<select id="osr_makebarfee_price_unit"
									name="osr_makebarfee_price_unit">
									<option value="0">元/件</option>
								</select>
							</div>
						</div>
					</div>
					<div class="div_margin_border">
						<span>卸货费：</span> <input id="osr_landfee" name="osr_landfee"
							type="checkbox"
							<c:if test="${osr.osr_landfee==1}">checked="checked"</c:if>
							class="ace ace-switch ace-switch-5" onchange="changeShow('XHF')" />
						<span class="lbl"></span>
						<div id="XHF"
							<c:if test="${osr.osr_landfee==1}">style="display: block;"</c:if>
							<c:if test="${osr.osr_landfee!=1}">style="display: none;"</c:if>
							align="center">
							<div class="div_margin">
								立方单价<input type="text" id="osr_landfee_price"
									name="osr_landfee_price" value="${osr.osr_landfee_price}" /> <select
									id="osr_landfee_price_util" name="osr_landfee_price_util"><option
										value="0">元</option></select>
							</div>
						</div>
					</div>
				</div>
				<hr>
				<div class="moudle_dashed_border_show">
					<div>
						结算方式 :
						<select id= "ZZFWFJT" name= "ZZFWFJT" style= "width: 280px;" onchange= "showZZFWFJYT();" >
							<option>请选择结算方式</option>
							<option ${osr.osr_setttle_method == 0? "selected= 'selected'": "" } value= "0" >无阶梯</option>
							<option ${osr.osr_setttle_method == 1? "selected= 'selected'": "" } value= "1" >超过部分阶梯</option>
							<option ${osr.osr_setttle_method == 2? "selected= 'selected'": "" } value= "2" >总占用阶梯</option>
						</select>
					</div>
					<div id="ZZFWFJT0" class="moudle_dashed_border">无阶梯,开发中...</div>
					<div id="ZZFWFJT1" class="moudle_dashed_border">超过部分阶梯,开发中...</div>
					<div id="ZZFWFJT2" class="moudle_dashed_border">
						阶梯价计算逻辑：增值服务费总费用*折扣<br> ---已维护的阶梯---
						<zzfwfjtTABLE></zzfwfjtTABLE>
						<div class="add_button">
							<a data-toggle="tab" href="#tab_add">
								<button class="btn btn-xs btn-yellow"
									onclick="changeShow('ZZFWFJTDIV')">
									<i class="icon-hdd"></i>新增
								</button>
							</a>
						</div>
						<!-- 增值服务费阶梯 -->
						<div class="moudle_dashed_border_width_90_red" id="ZZFWFJTDIV">
							<div>
								<span>阶梯设置：</span>
							</div>
							<div class="div_margin">
								条件: <select id="zzfwf_alls_mark1" name="zzfwf_alls_mark1">
									<option value=">">></option>
									<option value=">=">>=</option>
								</select> 金额<input type="text" id="zzfwf_alls_mark4" name="zzfwf_alls_mark4" />
								<select><option>元</option></select>
							</div>
							<div class="div_margin">
								组合方式: <select id="zzfwf_alls_mark2" name="zzfwf_alls_mark2">
									<option value="1">并且</option>
								</select>
							</div>
							<div class="div_margin">
								条件:<select id="zzfwf_alls_mark3" name="zzfwf_alls_mark3">
									<option value="<"><</option>
									<option value="<="><=</option>
								</select> 金额<input type="text" id="zzfwf_alls_mark5" name="zzfwf_alls_mark5" />
								<select><option>元</option></select>
							</div>
							<div>
								折扣:
								<input type="text" id="vasd_value" name="vasd_value" />
								<select id="vasd_value_unit" name="vasd_value_unit">
								<option value="0"></option></select>
							</div>
							<div class="div_margin">
								<a data-toggle="tab" href="#tab_add">
									<button class="btn btn-xs btn-yellow" onclick="save8();">
										<i class="icon-hdd"></i>保存
									</button>
								</a>
							</div>
	
						</div>
					</div>
				</div>
				<div class="div_margin">
					<a data-toggle="tab" href="#tab_add">
						<button class="btn btn-xs btn-yellow" onclick="saveZZFWF();">
							<i class="icon-hdd"></i>保存
						</button>
					</a>
				</div>
			</form>
			<div class="moudle_dashed_border" style="display: block;">
				<div class="div_margin">
					<label class="control-label blue">
						管理费
					</label>
					<input 
					id="cb_managementFee_other_zzfwf" 
					type="checkbox" 
					class="ace ace-switch ace-switch-5" ${zzfwfFeeFlag == true ? 'checked="checked"' : '' } 
					onchange="managementOtherShow('zzfwf');"/>
					<span class="lbl"></span>
				</div>
				<!-- 管理费 -->
				<div id="managementFeeOther_zzfwf" class="moudle_dashed_border" style="width:100%;border:0px;${zzfwfFeeFlag == true ? 'display:block;' : '' }">
					<%-- <%@ include file="/lmis/express_contract/managementFee.jsp" %> --%>
					<iframe id="iframe_zzfwf" class="with-border" style="overflow: visible; border:0px;margin:0px;padding:0px;background: rgba(0,0,0,0);width: 100%;" src="${root }control/expressContractController/toManagementFeeOther.do?management_fee_type=zzfwf&cb_id=${cb.id }"></iframe>
				</div>
			</div>
		</div>
	</body>
</html>
