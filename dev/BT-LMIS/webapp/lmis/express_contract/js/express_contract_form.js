/**
 * 
 */
$(function(){

	$(".registerform").Validform({
		tiptype:2,
	});
		
	$('#contractCycle').daterangepicker(null, function(start, end, label) {
       	console.log(start.toISOString(), end.toISOString(), label);
	});
	if($("#id").val() != ""){
		$("#contractCycle").prop("disabled", "true");
		$("#carrier_contract_config").show();
	}
	// 初始化页面
	loadData();
});

function del_wD(id){
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	if(!confirm("是否删除以下所选数据?")){
	  	return;
	  	
	}
	
	$.ajax({
		url: root + "/control/expressContractController/delWD.do",
		type: "post",
		data: {
			"id" : id,
			"con_id" : con_id,
			"belong_to" : $("#contractOwner").val()
		},
		dataType: "json",
		success: function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnWaybillDiscount();
				$("#wDList tbody").empty();
				if(result.result_null_wD == "false") {
					var wD = "";
					for(var i =0;i < result.wDListShow.length;i++){
						wD += 
							'<tr><td>'　+　result.wDListShow[i].no +　'</td>'
							+ '<td>' +　result.wDListShow[i].product_type_name + '</td>'
							+ '<td>' +　result.wDListShow[i].region + '</td>'
							+ '<td>' +　result.wDListShow[i].discount + '</td>'
							+ '<td><a onclick="del_wD(' + result.wDListShow[i].id +')" >删除</a></td>'
							+'</tr>';
					}
					$("#wDList tbody").append(wD);
				} 
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error: function(result) {
			alert(result.result_content);
		}
	});
}

function save_waybill_discount_ec(){
	var con_id = $("#id").val();
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	var json = {
			"con_id" : con_id,
			"belong_to" : $("#contractOwner").val(),
			"product_type" : $("#product_type_waybill").val()};
	var num_1 = $("#num_1_waybill").val();
	var num_2 = $("#num_2_waybill").val();
	if(num_1=="" && num_2==""){
		alert("请设定区间！");
		return false;
	}
	if(num_1 == ""){
		alert("区间下限未填写！");
		return false;
	}
	if(isNaN(num_1) || num_1 < 0){
		alert("区间下限输入不合法！");
		return false;
	}
	if(isNaN(num_2) || num_2 < 0){
		alert("区间上限输入不合法！");
		return false;
	}
	if(num_2 == ""){
		json["compare_1"]=$("#mark_1_waybill").val();
		json["num_1"]=num_1;
		num_2 = num_1 + 1;
		json["compare_2"]="2";
	} else {
		json["compare_1"]=$("#mark_1_waybill").val();
		json["num_1"]=num_1;
		json["compare_2"]=$("#mark_2_waybill").val();
		json["num_2"]=num_2;
	}
	if(parseInt(num_1) > parseInt(num_2)){
		alert("区间赋值异常：下限大于上限！");
		return false;
	}
	if(parseInt(num_1) == parseInt(num_2)){
		alert("区间赋值异常：上限等于下限！");
		return false;
	}
	var discount = $("#waybillDiscount_percent").val();
	if(discount == ""){
		alert("单运单折扣率未填写！");
		return false;
	}
	if(isNaN(discount) || discount < 0){
		alert("单运单折扣率输入不合法！");
		return false;
	}
	json["discount"] = discount;
	json["discount_uom"] = $("#waybillDiscount_percent_uom").val();
	json["uom_1"] = $("#num_1_uom_waybill").val();
	json["rel"] = $("#rel_waybill").val();
	json["uom_2"] = $("#num_2_uom_waybill").val();
	$.ajax({
		url : root + "/control/expressContractController/saveWaybillDiscount.do",
		type : "post",
		data : json,
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnWaybillDiscount();
				$("#wDList tbody").empty();
				var tFD = "";
				for(var i =0;i < result.wDListShow.length;i++){
					tFD += 
						'<tr><td>'　+　result.wDListShow[i].no +　'</td>'
						+ '<td>' +　result.wDListShow[i].product_type_name + '</td>'
						+ '<td>' +　result.wDListShow[i].region + '</td>'
						+ '<td>' +　result.wDListShow[i].discount + '</td>'
						+ '<td><a onclick="del_wD(' + result.wDListShow[i].id +')" >删除</a></td>'
						+'</tr>';
				}
				$("#wDList tbody").append(tFD);
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error :　function(result) {
			alert(result.result_content);
		}
	});
}

function returnWaybillDiscount(){
	$("#add_waybill_condition").hide();
	initializeWaybillDiscount();
}

function initializeWaybillDiscount(){
	$("#waybillDiscount_percent").val("");
	$("#waybillDiscount_percent_uom option:eq(0)").prop("selected","selected");
	$("#mark_1_waybill option:eq(0)").prop("selected","selected");
	$("#num_1_waybill").val("");
	$("#num_1_uom_waybill option:eq(0)").prop("selected","selected");
	$("#rel_waybill option:eq(0)").prop("selected","selected");
	$("#mark_2_waybill option:eq(0)").prop("selected","selected");
	$("#num_2_waybill").val("");
	$("#num_2_uom_waybill option:eq(0)").prop("selected","selected");
	$("#product_type_waybill option:eq(0)").prop("selected","selected");
}

function waybillDiscountShow(){
	if($("#cb_waybill").prop("checked") == true){
		$("#waybillDiscountDiv").show();
		var con_id = $("#id").val();
		if(con_id != ""){
			$.ajax({
				url : root + "/control/expressContractController/loadWaybillDiscount.do",
				type : "post",
				data : {
					"con_id" : con_id,
					"belong_to" : $("#contractOwner").val()
					},
				dataType : "json",
				success : function(result) {
					$("#wDList tbody").empty();
					if(result.result_null_wD == "false"){
						var wD = "";
						for(var i = 0; i < result.wDListShow.length; i++){
							wD += 
								'<tr><td>'　+　result.wDListShow[i].no +　'</td>'
								+ '<td>' +　result.wDListShow[i].product_type_name + '</td>'
								+ '<td>' +　result.wDListShow[i].region + '</td>'
								+ '<td>' +　result.wDListShow[i].discount + '</td>'
								+ '<td><a onclick="del_wD(' + result.wDListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#wDList tbody").append(wD);
					}
				},
				error :　function(result) {
					alert(result.result_content);
				}
			});
		}
	} else {
		$("#waybillDiscountDiv").hide();
		returnWaybillDiscount();
	}
}

function checkUniqueValidity(){
	if(checkedOrNot("cb_contractValidity") == 0	){
		var arr = new Array();
		arr = $("#contractCycle").val().split(" - ");
		var temp = new Date(arr[0]);
		var start_date = new Date(temp.getFullYear() + '-' + temp.getMonth() + '-' + temp.getDate());
		var temp = new Date(arr[1]);
		var end_date = new Date(temp.getFullYear() + '-' + temp.getMonth() + '-' + temp.getDate());
		var date = new Date();
		var today = new Date(date.getFullYear() + '-' + date.getMonth() + '-' + date.getDate());
		if(today < start_date || today > end_date){
			alert("依所填合同周期此合同无效！");
			$("#cb_contractValidity").prop("checked", false);
			return false;
		}
		var con_id = $("#id").val(); 
		if (con_id == 0 || con_id == null){
			alert("无对应合同主信息！");
			return false;
		}
		var flag = 0;
		$.ajax({
			url : root + "/control/expressContractController/checkUniqueValidity.do",
			type : "post",
			data : {
				"con_id" : con_id,
				"belong_to" : $("#contractOwner").val(),
				"contractType" : $("#contractType").val(),
				"contractOwner" : $("#contractOwner").val()
			},
			dataType : "json",
			async: false,
			success : function(result) {
				if(result.result_code=="FAILURE") {
					alert(result.result_content);
					$("#cb_contractValidity").prop("checked", false);
					flag = 1;
				}
			},
			error : function(result) {
				alert(result);
			}
		});
		if(flag == 1){
			return false;
		}
	}
}

function del_iEC(id){
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	$.ajax({
		url : root + "/control/expressContractController/delIEC.do",
		type : "post",
		data : {
			"id" : id,
			"con_id" : con_id,
			"belong_to" : $("#contractOwner").val()
		},
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnIEC();
				$("#iECList tbody").empty();
				if(result.result_null_iEC =="false") {
					var iEC = "";
					for(var i =0;i < result.iECListShow.length;i++){
						iEC += 
							'<tr><td>'　+　result.iECListShow[i].no +　'</td>'
							+ '<td>' +　result.iECListShow[i].product_type_name + '</td>'
							+ '<td>' +　result.iECListShow[i].ladder_type + '</td>'
							+ '<td>' +　result.iECListShow[i].charge_min + '</td>'
							+ '<td>' +　result.iECListShow[i].region + '</td>'
							+ '<td>' +　result.iECListShow[i].charge_percent + '</td>'
							+ '<td>' +　result.iECListShow[i].charge + '</td>'
							+ '<td><a onclick="del_iEC(' + result.iECListShow[i].id +')" >删除</a></td>'
							+'</tr>';
					}
					$("#iECList tbody").append(iEC);
				} 
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error : function(result) {
			alert(result.result_content);
		}
	});
}

function save_iEC(){
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	var ladder_type = $("#ladder_type_iF").val();
	if(ladder_type == 0){
		alert("请选择阶梯类型！");
		return false;
	}
	var json = {
			"con_id" : con_id,
			"belong_to" : $("#contractOwner").val(),
			"product_type" :　$("#product_type_iF").val(), 
			"ladder_type" :　ladder_type
			};
	if(ladder_type == 1){
		var charge_percent = $("#charge_percent_iF_2").val();
		if(charge_percent == ""){
			alert("收费率未填写！");
			return false;
		}
		if(isNaN(charge_percent) || charge_percent < 0){
			alert("收费率输入不合法！");
			return false;
		}
		json["charge_percent"] = charge_percent;
		json["charge_percent_uom"] = $("#charge_percent_iF_2_uom").val();
		if(checkedOrNot("cb_2") == 0) {
			var charge_min = $("#charge_min_iF").val();
			if(charge_min == ""){
				alert("最低收费未填写！");
				return false;
			}
			if(isNaN(charge_min) || charge_min < 0){
				alert("最低收费输入不合法！");
				return false;
			}
			json["charge_min"] = charge_min;
			json["charge_min_uom"] = $("#charge_min_iF_uom").val();
		}
	} else {
		var num_1 = $("#num_1_iF").val();
		var num_2 = $("#num_2_iF").val();
		if(num_1=="" && num_2==""){
			alert("请设定区间！");
			return false;
		}
		if(num_1 == ""){
			alert("区间下限未填写！");
			return false;
		}
		if(isNaN(num_1) || num_1 < 0){
			alert("区间下限输入不合法！");
			return false;
		}
		if(isNaN(num_2) || num_2 < 0){
			alert("区间上限输入不合法！");
			return false;
		}
		if(num_2 == ""){
			json["compare_1"]=$("#compare_1_iF").val();
			json["num_1"]=num_1;
			num_2 = num_1 + 1;
			json["compare_2"]="2";
		} else {
			json["compare_1"]=$("#compare_1_iF").val();
			json["num_1"]=num_1;
			json["compare_2"]=$("#compare_2_iF").val();
			json["num_2"]=num_2;
		}
		if(parseInt(num_1) > parseInt(num_2)){
			alert("区间赋值异常：下限大于上限！");
			return false;
		}
		json["uom_1"] = $("#num_1_iF_uom").val();
		json["rel"] = $("#rel_iF").val();
		json["uom_2"] = $("#num_2_iF_uom").val();
		if($("#radio0").prop("checked")){
			var charge_percent = $("#charge_percent_iF").val();
			if(charge_percent == ""){
				alert("收费率未填写！");
				return false;
			}
			if(isNaN(charge_percent) || charge_percent < 0){
				alert("收费率输入不合法！");
				return false;
			}
			json["charge_percent"] = charge_percent;
			json["charge_percent_uom"] = $("#charge_percent_iF_uom").val();
		} else if($("#radio1").prop("checked")){
			var charge = $("#charge_iF").val();
			if(charge == ""){
				alert("收费金额未填写！");
				return false;
			}
			if(isNaN(charge) || charge < 0){
				alert("收费金额输入不合法！");
				return false;
			}
			json["charge"] = charge;
			json["charge_uom"] = $("#charge_iF_uom").val();
		}
	}
	$.ajax({
		url: root +　"/control/expressContractController/saveIEC.do",
		type :　"post",
		data : json,
		dataType :　"json",
		success :　function(result){
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnIEC();
				$("#iECList tbody").empty();
				var iEC = "";
				for(var i =0;i < result.iECListShow.length;i++){
					iEC += 
						'<tr><td>'　+　result.iECListShow[i].no +　'</td>'
						+ '<td>' +　result.iECListShow[i].product_type_name + '</td>'
						+ '<td>' +　result.iECListShow[i].ladder_type + '</td>'
						+ '<td>' +　result.iECListShow[i].charge_min + '</td>'
						+ '<td>' +　result.iECListShow[i].region + '</td>'
						+ '<td>' +　result.iECListShow[i].charge_percent + '</td>'
						+ '<td>' +　result.iECListShow[i].charge + '</td>'
						+ '<td><a onclick="del_iEC(' + result.iECListShow[i].id +')" >删除</a></td>'
						+'</tr>';
				}
				$("#iECList tbody").append(iEC);
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error :　function(result){
			alert(result.result_content);
		}
	});
}

function del_tFD(id){
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	$.ajax({
		url : root + "/control/expressContractController/delTFD.do",
		type : "post",
		data : {
			"id" : id,
			"con_id" : con_id,
			"belong_to" : $("#contractOwner").val()
		},
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnDiscount();
				$("#tFDList tbody").empty();
				if(result.result_null_tFD == "false") {
					var tFD = "";
					for(var i =0;i < result.tFDListShow.length;i++){
						tFD += 
							'<tr><td>'　+　result.tFDListShow[i].no +　'</td>'
							+ '<td>' +　result.tFDListShow[i].product_type_name + '</td>'
							+ '<td>' +　result.tFDListShow[i].ladder_type + '</td>'
							+ '<td>' +　result.tFDListShow[i].region + '</td>'
							+ '<td>' +　result.tFDListShow[i].discount + '</td>'
							+ '<td>' +　result.tFDListShow[i].insurance_contain + '</td>'
							+ '<td><a onclick="del_tFD(' + result.tFDListShow[i].id +')" >删除</a></td>'
							+'</tr>';
					}
					$("#tFDList tbody").append(tFD);
				} 
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error : function(result) {
			alert(result.result_content);
		}
	});
}

function save_discount_ec(){
	var con_id = $("#id").val();
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	var ladder_type= $("#ladder_type_fr").val();
	if(ladder_type == 0){
		alert("请选择阶梯类型！");
		return false;
	}
	var product_type = $("#product_type_fr").val();
	var json = {
			"con_id" : con_id,
			"belong_to" : $("#contractOwner").val(),
			"insurance_contain" : checkedOrNot("cb_insurance_contain"),
			"product_type" : product_type, 
			"ladder_type" : ladder_type};
	if(ladder_type != 1){
		var num_1 = $("#payAll").val();
		var num_2 = $("#payAll_0").val();
		if(num_1=="" && num_2==""){
			alert("请设定区间！");
			return false;
		}
		if(num_1 == ""){
			alert("区间下限未填写！");
			return false;
		}
		if(isNaN(num_1) || num_1 < 0){
			alert("区间下限输入不合法！");
			return false;
		}
		if(isNaN(num_2) || num_2 < 0){
			alert("区间上限输入不合法！");
			return false;
		}
		if(num_2 == ""){
			json["compare_1"]=$("#mark_1").val();
			json["num_1"]=num_1;
			num_2 = num_1 + 1;
			json["compare_2"]="2";
		} else {
			json["compare_1"]=$("#mark_1").val();
			json["num_1"]=num_1;
			json["compare_2"]=$("#mark_2").val();
			json["num_2"]=num_2;
		}
		if(parseInt(num_1) > parseInt(num_2)){
			alert("区间赋值异常：下限大于上限！");
			return false;
		}
		if(parseInt(num_1) == parseInt(num_2)){
			alert("区间赋值异常：上限等于下限！");
			return false;
		}
		json["uom_1"]=$("#payAll_uom").val();
		json["rel"]=$("#rel").val();
		json["uom_2"]=$("#payAll_uom_0").val();
	}
	var discount_percent = $("#discount_percent").val();
	if(discount_percent == ""){
		alert("折扣率未填写！");
		return false;
	}
	if(isNaN(discount_percent) || discount_percent < 0){
		alert("折扣率输入不合法！");
		return false;
	}
	json["discount"]=discount_percent;
	json["discount_uom"]=$("#dp_uom").val();
	$.ajax({
		url : root + "/control/expressContractController/saveTotalFreightDiscount.do",
		type : "post",
		data : json,
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnDiscount();
				$("#tFDList tbody").empty();
				var tFD = "";
				for(var i =0;i < result.tFDListShow.length;i++){
					tFD += 
						'<tr><td>'　+　result.tFDListShow[i].no +　'</td>'
						+ '<td>' +　result.tFDListShow[i].product_type_name + '</td>'
						+ '<td>' +　result.tFDListShow[i].ladder_type + '</td>'
						+ '<td>' +　result.tFDListShow[i].region + '</td>'
						+ '<td>' +　result.tFDListShow[i].discount + '</td>'
						+ '<td>' +　result.tFDListShow[i].insurance_contain + '</td>'
						+ '<td><a onclick="del_tFD(' + result.tFDListShow[i].id +')" >删除</a></td>'
						+'</tr>';
				}
				$("#tFDList tbody").append(tFD);
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error :　function(result) {
			alert(result.result_content);
		}
	});
}

function del_pricing_formula(id){
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	$.ajax({
		url : root + "/control/expressContractController/delPricingFormula.do",
		type : "post",
		data : {
			"id" : id,
			"con_id" : con_id,
			"belong_to" :　$("#contractOwner").val()
		},
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnPF();
				$("#pfList tbody").empty();
				if(result.result_null_pf =="false") {
					var pf = "";
					for(var i =0;i < result.pfListShow.length;i++){
						pf += 
							'<tr><td>'　+　result.pfListShow[i].no +　'</td>'
							+ '<td>' +　result.pfListShow[i].pricing_formula_name + '</td>'
							+ '<td><a onclick="get_pricing_formula(' + result.pfListShow[i].id +')" >详细信息</a></td>'
							+ '<td><a onclick="del_pricing_formula(' + result.pfListShow[i].id +')" >删除</a></td>'
							+'</tr>';
					}
					$("#pfList tbody").append(pf);
				} 
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error : function(result) {
			alert(result.result_content);
		}
	});
}

function get_pricing_formula(id){
	if ($("#id").val() == 0 || $("#id").val() == null){
		alert("无对应合同主信息！");
		return false;
	} 
	$.ajax({
		url: root +　"/control/expressContractController/getPricingFormula.do",
		type : "post",
		data : {"id" : id},
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				returnPF();
				$("#pricingformula_ec_id").val(result.pf.id);
				var pricing_formula = result.pf.pricing_formula;
				$("#formula option:eq("+　pricing_formula　+")").prop("selected","selected");
				if(pricing_formula==1){
					$("#exact_decimal_1").val(result.pf.accurate_decimal_place);
				}
				if(pricing_formula==2){
					$("#exact_decimal_2").val(result.pf.accurate_decimal_place);
				}
				$("#ebfAdd").show();
				shiftPage();
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error : function(result) {
			alert(result.result_content);
		}
	});
}

function save_pricingFormula_ec(){
	var con_id = $("#id").val();
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	var pricing_formula = $("#formula").val();
	if (pricing_formula == 0){
		alert("请选择计费公式！");
		return false;
	}
	var json={
			"id" : $("#pricingformula_ec_id").val(), 
			"con_id" :　con_id, 
			"belong_to" :　$("#contractOwner").val(),
			"pricing_formula" :　pricing_formula};
	if(pricing_formula == 1) {
		var exact_decimal = $("#exact_decimal_1").val();
		if(exact_decimal == ""){
			alert("精确小数位未填写！");
			return false;
		}
		if(isNaN(exact_decimal) || exact_decimal < 0){
			alert("精确小数位输入不合法！");
			return false;
		}
		json["exact_decimal"] = exact_decimal;
	}
	if(pricing_formula == 2) {
		var exact_decimal = $("#exact_decimal_2").val();
		if(exact_decimal == ""){
			alert("精确小数位未填写！");
			return false;
		}
		if(isNaN(exact_decimal) || exact_decimal < 0){
			alert("精确小数位输入不合法！");
			return false;
		}
		json["exact_decimal"] = exact_decimal;
	} 
	$.ajax({
		url: root +　"/control/expressContractController/savePricingFormula.do",
		type : "post",
		data : json,
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnPF();
				if($("#pricingformula_ec_id").val() == ""){
					$("#pricingformula_ec_id").val(result.id);
				}
				$("#pfList tbody").empty();
				var pf = "";
				for(var i =0;i < result.pfListShow.length;i++){
					pf += 
						'<tr><td>'　+　result.pfListShow[i].no +　'</td>'
						+ '<td>' +　result.pfListShow[i].pricing_formula_name + '</td>'
						+ '<td><a onclick="get_pricing_formula(' + result.pfListShow[i].id +')" >详细信息</a></td>'
						+ '<td><a onclick="del_pricing_formula(' + result.pfListShow[i].id +')" >删除</a></td>'
						+'</tr>';
				}
				$("#pfList tbody").append(pf);
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error : function(result) {
			alert(result.result_content);
		}
	});
}

function del_jibanpao_condition(id){
	var con_id = $("#id").val();
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	$.ajax({
		url : root + "/control/expressContractController/delJBPCondition.do",
		type : "post",
		data : {
			"id" : id,
			"con_id" : con_id,
			"belong_to" :　$("#contractOwner").val()
		},
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				$("#jbpcList tbody").empty();
				if(result.result_null == "false") {
					var jbpc = "";
					for(var i =0;i < result.jbpcListShow.length;i++){
						jbpc += 
							'<tr><td>'　+　result.jbpcListShow[i].no +　'</td>'
							+ '<td>' +　result.jbpcListShow[i].content + '</td>'
							+ '<td><a onclick="del_jibanpao_condition(' + result.jbpcListShow[i].id +')" >删除</a></td>'
							+'</tr>';
					}
					$("#jbpcList tbody").append(jbpc);
				} 
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error : function(result) {
			alert(result.result_content);
		}
	});
}
function del_jipao_condition(id){
	var con_id = $("#id").val();
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	$.ajax({
		url : root + "/control/expressContractController/delJQPCondition.do",
		type : "post",
		data : {
			"id" : id,
			"con_id" : con_id,
			"belong_to" :　$("#contractOwner").val()
		},
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				$("#jqpcList tbody").empty();
				if(result.result_null == "false") {
					var jqpc = "";
					for(var i =0;i < result.jqpcListShow.length;i++){
						jqpc += 
							'<tr><td>'　+　result.jqpcListShow[i].no +　'</td>'
							+ '<td>' +　result.jqpcListShow[i].content + '</td>'
							+ '<td><a onclick="del_jipao_condition(' + result.jqpcListShow[i].id +')" >删除</a></td>'
							+'</tr>';
					}
					$("#jqpcList tbody").append(jqpc);
				} 
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error : function(result) {
			alert(result.result_content);
		}
	});
}
function add_jiquanpao_condition(){
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	var con = $("#con_qp").val();
	if(con=="" || con==null){
		alert("请填写计全抛条件！");
		return false;
	}
	if(isNaN(con) || con < 0){
		alert("计全抛条件输入不合法！");
		return false;
	}
	$.ajax({
		url : root + "/control/expressContractController/addJQPCondition.do",
		type : "post",
		data : {
			"con_id" : con_id,
			"belong_to" : $("#contractOwner").val(),
			"attr" : $("#attr_qp").val(),
			"compare_mark" : $("#mark_qp").val(),
			"con" : con,
			"uom" : $("#uom_qp").val()
		},
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnJBPC();
				$("#jqpcList tbody").empty();
				var jqpc = "";
				for(var i =0;i < result.jqpcListShow.length;i++){
					jqpc += 
						'<tr><td>'　+　result.jqpcListShow[i].no +　'</td>'
						+ '<td>' +　result.jqpcListShow[i].content + '</td>'
						+ '<td><a onclick="del_jipao_condition(' + result.jqpcListShow[i].id +')" >删除</a></td>'
						+'</tr>';
				}
				$("#jqpcList tbody").append(jqpc);
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error : function(result) {
			alert(result.result_content);
		}
	});
}
function add_jibanpao_condition(){
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	var con = $("#con").val();
	if(con=="" || con==null){
		alert("请填写计半抛条件！");
		return false;
	}
	if(isNaN(con) || con < 0){
		alert("计半抛条件输入不合法！");
		return false;
	}
	$.ajax({
		url : root + "/control/expressContractController/addJBPCondition.do",
		type : "post",
		data : {
			"con_id" : con_id,
			"belong_to" : $("#contractOwner").val(),
			"attr" : $("#attr").val(),
			"compare_mark" : $("#mark").val(),
			"con" : con,
			"uom" : $("#uom").val()
		},
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnJBPC();
				$("#jbpcList tbody").empty();
				var jbpc = "";
				for(var i =0;i < result.jbpcListShow.length;i++){
					jbpc += 
						'<tr><td>'　+　result.jbpcListShow[i].no +　'</td>'
						+ '<td>' +　result.jbpcListShow[i].content + '</td>'
						+ '<td><a onclick="del_jibanpao_condition(' + result.jbpcListShow[i].id +')" >删除</a></td>'
						+'</tr>';
				}
				$("#jbpcList tbody").append(jbpc);
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error : function(result) {
			alert(result.result_content);
		}
	});
}

function configureEC(type){
	var con_id = $("#id").val();
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	var json={"id" : $("#eCC_id").val(), "con_id" :　con_id};
	var num = "";
	if(type == 1){
		var weight = $("#weight").val();
		if(weight==0){
			alert("请选择计重方式！");
			return false;
		} else {
			if(weight==3){
				if($("#contractType").val()==1){
					$.ajax({
						url : root + "/control/expressContractController/judgeExist.do",
						type : "post",
						data : {
							"con_id" : con_id, 
							"belong_to" : $("#contractOwner").val(), 
							"type" : "JBPC"
								},
						dataType : "json",
						async: false,
						success : function(result){
							if(result.result_code == "FAILURE"){
								alert(result.result_content);
							} else {
								num = result.num;
							}
						},
						error : function(result){
							alert(result);
						}
					});
					if(num == 0){
						alert("至少维护一条计半抛条件！");
						return false;
					}
				}
				var percent = $("#percent").val();
				if(percent == ""){
					alert("百分比设置未填写！");
					return false;
				}
				if(isNaN(percent) || percent < 0){
					alert("百分比输入不合法！");
					return false;
				}
				json["percent"] = percent;
				json["percent_uom"] = $("#percent_uom").val();
			}
			$.ajax({
				url : root + "/control/expressContractController/judgeExist.do",
				type : "post",
				data : {
					"con_id" : con_id, 
					"belong_to" : $("#contractOwner").val(), 
					"type" : "PF"
						},
				dataType : "json",
				async: false,
				success : function(result){
					if(result.result_code == "FAILURE"){
						alert(result.result_content);
					} else {
						num = result.num;
					}
				},
				error : function(result){
					alert(result);
				}
			});
			if(num == 0){
				alert("至少维护一条计费公式！");
				return false;
			}
		}
		json["weight"] = weight;
		if(checkedOrNot("cb_waybill") == 0){
			$.ajax({
				url : root + "/control/expressContractController/judgeExist.do",
				type : "post",
				data : {
					"con_id" : con_id, 
					"belong_to" : $("#contractOwner").val(),
					"type" : "WD"
						},
				dataType : "json",
				async: false,
				success : function(result){
					if(result.result_code == "FAILURE"){
						alert(result.result_content);
					} else {
						num = result.num;
					}
				},
				error : function(result){
					alert(result);
				}
			});
			if(num == 0){
				alert("至少维护一条运费折扣启用条件记录！");
				return false;
			}
		}
		json["waybill_discount"] = isChecked("cb_waybill");
		if(checkedOrNot("cb_0") == 0){
			$.ajax({
				url : root + "/control/expressContractController/judgeExist.do",
				type : "post",
				data : {
					"con_id" : con_id, 
					"belong_to" : $("#contractOwner").val(),
					"type" : "TFD"
						},
				dataType : "json",
				async: false,
				success : function(result){
					if(result.result_code == "FAILURE"){
						alert(result.result_content);
					} else {
						num = result.num;
					}
				},
				error : function(result){
					alert(result);
				}
			});
			if(num == 0){
				alert("至少维护一条总运费折扣规则！");
				return false;
			}
		}
		json["total_freight_discount"] = isChecked("cb_0");
	}
	if(type == 2){
		if(checkedOrNot("cb_1") == 0){
			$.ajax({
				url : root + "/control/expressContractController/judgeExist.do",
				type : "post",
				data : {
					"con_id" : con_id, 
					"belong_to" : $("#contractOwner").val(), 
					"type" : "Insurance"
						},
				dataType : "json",
				async: false,
				success : function(result){
					if(result.result_code == "FAILURE"){
						alert(result.result_content);
					} else {
						num = result.num;
					}
				},
				error : function(result){
					alert(result);
				}
			});
			if(num == 0){
				alert("至少维护一条保价费规则！");
				return false;
			}
		}
		json["insurance"] = isChecked("cb_1");
	}
	if(type == 3){
		json["belong_to"] = $("#contractOwner").val();
		var cod = isChecked("cb_3");
		var delegated_pickup_flag = isChecked("cb_4");
		json["cod"] = cod;
		json["delegated_pickup_flag"] = delegated_pickup_flag;
		if(cod || delegated_pickup_flag){
			json["sSE_id"]=$("#sSE_id").val();
		}
		if(cod){
			var cod_charge_method = $("#CODChargeMethod").val();
			if(cod_charge_method==0){
				alert("请选择COD收费方式！");
				return false;
			}
			json["cod_charge_method"] = cod_charge_method;
			var charge_min_flag = checkedOrNot("cb_5");
			json["charge_min_flag"] = charge_min_flag;
			if(charge_min_flag==0){
				var charge_min_cod = $("#charge_min_cod").val();
				if(charge_min_cod == ""){
					alert("最低收费未填写！");
					return false;
				}
				if(isNaN(charge_min_cod) || charge_min_cod < 0){
					alert("最低收费输入不合法！");
					return false;
				}
				json["charge_min"] = charge_min_cod;
				json["charge_min_uom"] = $("#charge_min_uom_cod").val();
			}
			var percent_cod = $("#percent_cod").val();
			if(percent_cod == ""){
				alert("百分比未填写！");
				return false;
			}
			if(isNaN(percent_cod) || percent_cod < 0){
				alert("百分比输入不合法！");
				return false;
			}
			json["percent"] = percent_cod;
			json["percent_uom"] = $("#percent_uom_cod").val();
			if(cod_charge_method==1){
				var accurate_decimal_place_cod = $("#accurate_decimal_place_cod").val();
				if(accurate_decimal_place_cod == ""){
					alert("精确小数位未填写！");
					return false;
				}
				if(isNaN(accurate_decimal_place_cod) || accurate_decimal_place_cod < 0){
					alert("精确小数位输入不合法！");
					return false;
				}
				json["accurate_decimal_place"] = accurate_decimal_place_cod;
				var param_1_cod = $("#param_1_cod").val();
				if(param_1_cod == ""){
					alert("参数1未填写！");
					return false;
				}
				if(isNaN(param_1_cod) || param_1_cod < 0){
					alert("参数1输入不合法！");
					return false;
				}
				json["param_1"] = param_1_cod;
				json["param_1_uom"] = $("#param_1_uom_cod").val();
				var charge_1_cod = $("#charge_1_cod").val();
				if(charge_1_cod == ""){
					alert("收费1未填写！");
					return false;
				}
				if(isNaN(charge_1_cod) || charge_1_cod < 0){
					alert("收费1输入不合法！");
					return false;
				}
				json["charge_1"] = charge_1_cod;
				json["charge_1_uom"] = $("#charge_1_uom_cod").val();
			}
		}
		if(delegated_pickup_flag){
			var delegated_pickup = $("#delegated_pickup").val();
			if($("#delegated_pickup").val() == ""){
				alert("委托取件费未填写！");
				return false;
			}
			if(isNaN(delegated_pickup) || delegated_pickup < 0){
				alert("委托取件费输入不合法！");
				return false;
			}
			json["delegated_pickup"] = delegated_pickup;
			json["delegated_pickup_uom"] = $("#delegated_pickup_uom").val();
		}
	}
	json["type"] = type;
	$.ajax({
		url : root + "/control/expressContractController/configureEC.do",
		type : "post",
		data : json,
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
//				if(result.type==2){
//					if(result.move == 0){
//						$("#insurance_man").prop("style", "display : inline;");
//						$("#insurance_man").show();
//					} else if(result.move == 1){
//						$("#insurance_man").hide();
//					}
//				}
//				if(result.type==3){
					if(result.sSE_id != null){
						$("#sSE_id").val(result.sSE_id);
					}
//					if(result.move1 == 1&&result.move2 == 1){
//						$("#specialService_man").hide();
//					} else {
//						$("#specialService_man").prop("style", "display : inline;");
//						$("#specialService_man").show();
//					}
//					if(result.move1 == 0){
//						$("#cod_man").prop("style", "display : inline;");
//						$("#cod_man").show();
//					} else if(result.move1 == 1){
//						$("#cod_man").hide();
//					}
//					if(result.move2 == 0){
//						$("#delegated_pickup_man").prop("style", "display : inline;");
//						$("#delegated_pickup_man").show();
//					} else if(result.move2 == 1){
//						$("#delegated_pickup_man").hide();
//					}
//				}
				alert(result.result_content);
			} else if(result.result_code=="FAILURE"){
				alert(result.result_content);
			}
		},
		error :　function(result) {
			alert(result.result_content);
		}
	});
}

function saveECM(){
	if($("#contractType").val()==0){
		alert("请选择合同类型！");
		return false;
	}
	if($("#contractCode").val() == ""){
		alert("合同编号未填写！");
		return false;
	}
	if($("#contractName").val() == ""){
		alert("合同名称未填写！");
		return false;
	}
	if($("#contractVersion").val() == ""){
		alert("合同版本未填写！");
		return false;
	}
	if($("#contact").val() == ""){
		alert("联系人未填写！");
		return false;
	}
	if($("#tel").val() == ""){
		alert("联系电话未填写！");
		return false;
	}
	if(checkUniqueValidity() == false){
		return false;
	}
	$.ajax({
		url : root + "/control/expressContractController/saveECM.do",
		type : "post",
		data : $(".registerform").serialize(),
		dataType : "json",
		success :　function(data){
			if(data.result_code=="SUCCESS"){
				alert(data.result_content);
				if ($("#id").val() == "" || $("#id").val() == null){
					openDiv(root +'control/expressContractController/form.do?id='+data.id);
				} else {
					$("#updateBy").val(data.updateBy);
					$("#updateTime").val(data.updateTime);
				}
			} else if(data.result_code=="FAILURE"){
				alert(data.result_content);
			}
		},
		error : function(data){
			alert(data.result_content);
		}
	});
}

function selBelongTo() {
	var contractType = $("#contractType").val();
	if(contractType==0){
		$('#wl').hide();
		$('#kd').hide();
		$("#myTab_kd").css("display","none");
		$("#myTab_wl").css("display","none");
		$("#kd_moudle_li").css("display","none");
		$("#wl_moudle_li").css("display","none");
	}else if(contractType==1){
		$('#wl').hide();
		$('#kd').show();
		$("#myTab_kd").css("display","block");
		$("#myTab_wl").css("display","none");
		$("#kd_moudle_li").css("display","block");
		$("#wl_moudle_li").css("display","none");
	}else {
		$('#kd').hide();
		$('#wl').show();
		$("#myTab_kd").css("display","none");
		$("#myTab_wl").css("display","block");
		$("#kd_moudle_li").css("display","none");
		$("#wl_moudle_li").css("display","block");
	}
}

function loadData(){
	// 切换快递/物流
	selBelongTo();
	// 计重方式
	shiftPage_1();
	// 运单折扣启用条件
	waybillDiscountShow();
	// 总运费折扣
	discountShow();
	// 保价费
	insuranceECShow();
	// COD
	codShow();
	// 委托取件费
	delegatedPickupShow();
}

function specialServiceSwitch(){
	if($("#cb_10").prop("checked")==true){
		$("#div_9").show();
	} else {
		$("#div_9").hide();
	}
}

function delegatedPickupShow(){
	if($("#cb_4").prop("checked")==true){
		$("#div_7").show();
		var con_id = $("#id").val();
		if(con_id != ""){
			$.ajax({
				url : root +　"/control/expressContractController/loadSSE.do",
				type :　"post",
				data : {
					"con_id" : con_id,
					"belong_to" : $("#contractOwner").val()
					},
				dataType : "json",
				success : function(result) {
					if(result.result_null_sSE=="false"){
						$("#delegated_pickup").val(result.sSE.delegated_pickup);
						$("#delegated_pickup_uom option:eq(" + result.sSE.delegated_pickup_uom +")").prop("selected", "selected");
					}
				},
				error : function(result) {
					alert(result.result_content);
				}
			});
		}
	} else {
		returnDelegatedPickup();
	}
}

function returnDelegatedPickup(){
	initializeDelegatedPickup();
	$("#div_7").hide();
}

function initializeDelegatedPickup(){
	$("#delegated_pickup").val("");
	$("#delegated_pickup_uom option:eq(0)").prop("selected", "selected");
}

function codShow(){
	if($("#cb_3").prop("checked") == true){
		$("#div_3").show();
		$("#div_4").show();
		var con_id = $("#id").val();
		if(con_id != ""){
			$.ajax({
				url : root +　"/control/expressContractController/loadSSE.do",
				type :　"post",
				data : {
					"con_id" : con_id,
					"belong_to" : $("#contractOwner").val()
					},
				dataType : "json",
				success : function(result) {
					if(result.result_null_sSE=="false"){
						$("#sSE_id").val(result.sSE.id);
						$("#CODChargeMethod option:eq(" + result.sSE.cod_charge_method +")").prop("selected","selected");
						if(result.sSE.charge_min_flag == 0){
							$("#cb_5").prop("checked", "checked");
							$("#charge_min_cod").val(result.sSE.charge_min);
							$("#charge_min_uom_cod option:eq(" + result.sSE.charge_min_uom +")").prop("selected","selected");
						}
						$("#percent_cod").val(result.sSE.percent);
						$("#percent_uom_cod option:eq(" + result.sSE.percent_uom +")").prop("selected","selected");
						if(result.sSE.cod_charge_method == 1){
							$("#accurate_decimal_place_cod").val(result.sSE.accurate_decimal_place);
							$("#param_1_cod").val(result.sSE.param_1);
							$("#param_1_uom_cod option:eq(" + result.sSE.param_1_uom +")").prop("selected","selected");
							$("#charge_1_cod").val(result.sSE.charge_1);
							$("#charge_1_uom_cod option:eq(" + result.sSE.charge_1_uom +")").prop("selected","selected");
						}
					}
					shiftPage_4();
				},
				error : function(result) {
					alert(result.result_content);
				}
			});
		}
	} else {
		returnCOD();
	}
}

function returnCOD(){
	initializeCOD2();
	$("#div_3").hide();
	$("#div_4").hide();
	$("#div_5").hide();
	$("#div_6").hide();
}

function initializeCOD2(){
	initializeCOD1();
	$("#CODChargeMethod option:eq(0)").prop("selected","selected");
}

function initializeCOD1(){
	$("#cb_5").attr("checked",false);
	$("#charge_min_cod").val("");
	$("#charge_min_uom_cod option:eq(0)").prop("selected","selected");
	$("#percent_cod").val("");
	$("#percent_uom_cod option:eq(0)").prop("selected","selected");
	$("#accurate_decimal_place_cod").val("");
	$("#param_1_cod").val("");
	$("#param_1_uom_cod option:eq(0)").prop("selected","selected");
	$("#charge_1_cod").val("");
	$("#charge_1_uom_cod option:eq(0)").prop("selected","selected");
}

function insuranceECShow(){
	if($("#cb_1").prop("checked")==true){
		$("#div_0").show();
		var con_id = $("#id").val();
		if(con_id != ""){
			$.ajax({
				url : root + "/control/expressContractController/loadInsuranceEC.do",
				type : "post",
				data : {
					"con_id":con_id,
					"belong_to" : $("#contractOwner").val()
					},
				dataType : "json",
				success : function(result) {
					if(result.result_null_iEC=="false"){
						$("#iECList tbody").empty();
						var iECRule = "";
						for(var i =0;i < result.iECListShow.length;i++){
							iECRule += 
								'<tr><td>'　+　result.iECListShow[i].no +　'</td>'
								+ '<td>' +　result.iECListShow[i].product_type_name + '</td>'
								+ '<td>' +　result.iECListShow[i].ladder_type + '</td>'
								+ '<td>' +　result.iECListShow[i].charge_min + '</td>'
								+ '<td>' +　result.iECListShow[i].region + '</td>'
								+ '<td>' +　result.iECListShow[i].charge_percent + '</td>'
								+ '<td>' +　result.iECListShow[i].charge + '</td>'
								+ '<td><a onclick="del_iEC(' + result.iECListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#iECList tbody").append(iECRule);
					} else if(result.result_null_iEC=="true") {
						$("#iECList tbody").empty();
					}
				},
				error :　function(result) {
					alert(result.result_content);
				}
			});
		}
	} else {
		returnIEC();
		$("#div_0").hide();
	}
}

function returnIEC(){
	$("#add_0").hide();
	$("#div_1").hide();
	$("#div_2").hide();
	initializeiEC();
}

function initializeiEC(){
	$("#radio0").prop("checked","checked");
	$("#charge_percent_iF").val("");
	$("#charge_percent_iF_uom option:eq(0)").prop("selected","selected");
	$("#radio1").prop("checked",false);
	$("#charge_iF").val("");
	$("#charge_iF option:eq(0)").prop("selected","selected");
	$("#charge_percent_iF_2").val("");
	$("#charge_percent_iF_2_uom option:eq(0)").prop("selected","selected");
	$("#cb_2").attr("checked",false);
	$("#charge_min_iF").val("");
	$("#charge_min_iF_uom option:eq(0)").prop("selected","selected");
	$("#compare_1_iF option:eq(0)").prop("selected","selected");
	$("#num_1_iF").val("");
	$("#num_1_iF_uom option:eq(0)").prop("selected","selected");
	$("#rel_iF option:eq(0)").prop("selected","selected");
	$("#compare_2_iF option:eq(0)").prop("selected","selected");
	$("#num_2_iF").val("");
	$("#num_2_iF_uom option:eq(0)").prop("selected","selected");
	$("#ladder_type_iF option:eq(0)").prop("selected","selected");
	$("#product_type_iF option:eq(0)").prop("selected","selected");
}

function returnDiscount(){
	$("#add").hide();
	$("#discount_0").hide();
	$("#discount_1").hide();
	initializeDiscount();
}

function discountShow(){
	if($("#cb_0").prop("checked")==true){
		$("#discount_div").show();
		var con_id = $("#id").val();
		if(con_id != ""){
			$.ajax({
				url : root + "/control/expressContractController/loadTotalFreightDiscount.do",
				type : "post",
				data : {
					"con_id":con_id,
					"belong_to" : $("#contractOwner").val()
					},
				dataType : "json",
				success : function(result) {
					if(result.result_null_tFD=="false"){
						$("#tFDList tbody").empty();
						var tFD = "";
						for(var i =0;i < result.tFDListShow.length;i++){
							tFD += 
								'<tr><td>'　+　result.tFDListShow[i].no +　'</td>'
								+ '<td>' +　result.tFDListShow[i].product_type_name + '</td>'
								+ '<td>' +　result.tFDListShow[i].ladder_type + '</td>'
								+ '<td>' +　result.tFDListShow[i].region + '</td>'
								+ '<td>' +　result.tFDListShow[i].discount + '</td>'
								+ '<td>' +　result.tFDListShow[i].insurance_contain + '</td>'
								+ '<td><a onclick="del_tFD(' + result.tFDListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#tFDList tbody").append(tFD);
					} else if(result.result_null_tFD=="true") {
						$("#tFDList tbody").empty();
					}
				},
				error :　function(result) {
					alert(result.result_content);
				}
			});
		}
	} else {
		$("#discount_div").hide();
		returnDiscount();
	}
}

function initializeDiscount(){
	$("#cb_insurance_contain").prop("checked",false);
	$("#mark_1 option:eq(0)").prop("selected","selected");
	$("#payAll").val("");
	$("#payAll_uom option:eq(0)").prop("selected","selected");
	$("#rel option:eq(0)").prop("selected","selected");
	$("#mark_2 option:eq(0)").prop("selected","selected");
	$("#payAll_0").val("");
	$("#payAll_uom_0 option:eq(0)").prop("selected","selected");
	$("#discount_percent").val("");
	$("#dp_uom option:eq(0)").prop("selected","selected");
	$("#discount_type").val("");
	$("#ladder_type_fr option:eq(0)").prop("selected","selected");
	$("#product_type_fr option:eq(0)").prop("selected","selected");
}

function returnJBPC(){
	$("#div_12").hide();
	initializeJBPC();
}

function initializeJBPC(){
	$("#attr option:eq(0)").prop("selected","selected");
	$("#mark option:eq(0)").prop("selected","selected");
	$("#con").val("");
	$("#uom option:eq(0)").prop("selected","selected");
}
function returnJQPC(){
	$("#div_JQP").hide();
	initializeJQPC();
}

function initializeJQPC(){
	$("#attr_qp option:eq(0)").prop("selected","selected");
	$("#mark_qp option:eq(0)").prop("selected","selected");
	$("#con_qp").val("");
	$("#uom_qp option:eq(0)").prop("selected","selected");
}

function returnPF(){
	$("#ebfAdd").hide();
	$("#formula_0").hide();
	$("#formula_1").hide();
	$("#formula_2").hide();
	$("#formula_3").hide();
	initializePF();
}

function initializePF(){
	$("#formula option:eq(0)").prop("selected","selected");
	$("#pricingformula_ec_id").val("");
	$("#multiple_1").val("");
	$("#exact_decimal_1").val("");
	$("#exact_decimal_2").val("");
	$("#weight_min").val("");
	$("#weight_min_uom option:eq(0)").prop("selected","selected");
}

function shiftPage_4() {
	
	if ($("#CODChargeMethod").val() == 0) {
		$("#div_5").hide();
		$("#div_6").hide();
	}
	
	if ($("#CODChargeMethod").val() == 1) {
		$("#div_5").show();
		$("#div_6").show();
		$("#CODFormula").text("计算公式 : 费用 = IF(ROUNDUP(代收货款金额 * 百分比, 精确小数位) <= 参数1, 收费1, ROUNDUP(货值 * 百分比, 精确小数位))");
		$("#CODFormulaExplain").text("公式说明 : 最低收费，百分比，精确小数位，参数1，收费1 需要在下方设置");
	}
	
	if ($("#CODChargeMethod").val() == 2) {
		$("#div_5").show();
		$("#div_6").hide();
		$("#CODFormula").text("计算公式 : 费用 = 订单金额 * 百分比");
		$("#CODFormulaExplain").text("公式说明 : 最低收费以及百分比需要在下方设置");
	}
	
	if ($("#CODChargeMethod").val() == 3) {
		$("#div_5").show();
		$("#div_6").hide();
		$("#CODFormula").text("计算公式 : 费用 = 代收货款金额 * 百分比");
		$("#CODFormulaExplain").text("公式说明 : 最低收费以及百分比需要在下方设置");
	}
	
}

function shiftPage_3() {
	
	if ($("#ladder_type_iF").val() == 0) {
		$("#div_1").hide();
		$("#cb_2").attr("checked",false);
		$("#div_2").hide();
		$("#radio0").prop("checked","checked");
		$("#radio1").prop("checked",false);
	}
	
	if ($("#ladder_type_iF").val() == 1) {
		$("#div_1").show();
		$("#cb_2").attr("checked",false);
		$("#div_2").hide();
		$("#radio0").prop("checked","checked");
		$("#radio1").prop("checked",false);
	}
	
	if ($("#ladder_type_iF").val() == 2) {
		$("#div_1").hide();
		$("#cb_2").attr("checked",false);
		$("#div_2").show();
		$("#radio0").prop("checked","checked");
		$("#radio1").prop("checked",false);
	}
	
	if ($("#ladder_type_iF").val() == 3) {
		$("#div_1").hide();
		$("#cb_2").attr("checked",false);
		$("#div_2").show();
		$("#radio0").prop("checked","checked");
		$("#radio1").prop("checked",false);
	}
	
}

function shiftPage_2() {
	
	if ($("#ladder_type_fr").val() == 0) {
		$("#discount_0").hide();
		$("#discount_1").hide();
		// alert(page_id);
	}
	
	if ($("#ladder_type_fr").val() == 1) {
		$("#discount_0").show();
		$("#discount_1").hide();
	}
	
	if ($("#ladder_type_fr").val() == 2) {
		$("#discount_0").show();
		$("#discount_1").show();
		$("#add_label0").text("---超过部分阶梯价---");
		$("#add_label1").text("总运费");
		$("#add_label2").text("总运费");
	}
	
	if ($("#ladder_type_fr").val() == 3) {
		$("#discount_0").show();
		$("#discount_1").show();
		$("#add_label0").text("---总折扣阶梯价---");
		$("#add_label1").text("总运费");
		$("#add_label2").text("总运费");
	}
	
	if ($("#ladder_type_fr").val() == 4) {
		$("#discount_0").show();
		$("#discount_1").show();
		$("#add_label0").text("---单量折扣阶梯价---");
		$("#add_label1").text("单量");
		$("#add_label2").text("单量");
	}
	
}

function shiftPage_1() {
	
	if ($("#weight").val() == 0) {
		$("#weight_0").hide();
		$("#weight_2").hide();
		$("#weight_3").hide();
		$("#div_12").hide();
		$("#ebfAdd").hide();
		$("#formula option:eq(0)").prop("selected","selected");
		$("#formula_0").hide();
		$("#formula_1").hide();
		$("#formula_2").hide();
		$("#formula_3").hide();
		$("#jbpcList tbody").empty();
		$("#jqpcList tbody").empty();
		$("#pfList tbody").empty();
	}
	
	if ($("#weight").val() != 0) {
		var con_id = $("#id").val();
		if(con_id!=""){
			$("#jbpcList tbody").empty();
			$("#jqpcList tbody").empty();
			$("#pfList tbody").empty();
			$.ajax({
				url : root + "/control/expressContractController/loadFreight.do",
				type : "get",
				data : {
					"con_id" : con_id,
					"belong_to" :　$("#contractOwner").val()
				},
				dataType : "json",
				success : function(result) {
					if(result.result_null_jbpc=="false"){
						var jbpc = "";
						for(var i =0;i < result.jbpcListShow.length;i++){
							jbpc += 
								'<tr><td>'　+　result.jbpcListShow[i].no +　'</td>'
								+ '<td>' +　result.jbpcListShow[i].content + '</td>'
								+ '<td><a onclick="del_jibanpao_condition(' + result.jbpcListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#jbpcList tbody").append(jbpc);
					}
					if(result.result_null_jqpc=="false"){
						var jqpc = "";
						for(var i =0;i < result.jqpcListShow.length;i++){
							jqpc += 
								'<tr><td>'　+　result.jqpcListShow[i].no +　'</td>'
								+ '<td>' +　result.jqpcListShow[i].content + '</td>'
								+ '<td><a onclick="del_jipao_condition(' + result.jqpcListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#jqpcList tbody").append(jqpc);
					}
					if(result.result_null_pf=="false"){
						var pf = "";
						for(var i =0;i < result.pfListShow.length;i++){
							pf += 
								'<tr><td>'　+　result.pfListShow[i].no +　'</td>'
								+ '<td>' +　result.pfListShow[i].pricing_formula_name + '</td>'
								+ '<td><a onclick="get_pricing_formula(' + result.pfListShow[i].id +')" >详细信息</a></td>'
								+ '<td><a onclick="del_pricing_formula(' + result.pfListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#pfList tbody").append(pf);
					}
				},
				error : function(result) {
					alert(result.result_content);
				}
			});
		}
	}
	
	if ($("#weight").val() == 1) {
		$("#weight_0").show();
		$("#weight_2").hide();
		$("#weight_3").hide();
		$("#div_12").hide();
		$("#ebfAdd").hide();
		$("#formula option:eq(0)").prop("selected","selected");
		$("#formula_0").hide();
		$("#formula_1").hide();
		$("#formula_2").hide();
		$("#formula_3").hide();
	}
	
	if ($("#weight").val() == 2) {
		$("#weight_0").show();
		$("#weight_2").show();
		$("#weight_3").hide();
		$("#div_12").hide();
		$("#ebfAdd").hide();
		$("#formula option:eq(0)").prop("selected","selected");
		$("#formula_0").hide();
		$("#formula_1").hide();
		$("#formula_2").hide();
		$("#formula_3").hide();
	}
	
	if ($("#weight").val() == 3) {
		$("#weight_0").show();
		$("#weight_2").hide();
		$("#weight_3").show();
		$("#div_12").hide();
		$("#ebfAdd").hide();
		$("#formula option:eq(0)").prop("selected","selected");
		$("#formula_0").hide();
		$("#formula_1").hide();
		$("#formula_2").hide();
		$("#formula_3").hide();
	}
}

function ebfAdd(){
	returnPF();
	$("#ebfAdd").show();
}

function shiftPage() {
	if ($("#formula").val() == 0) {
		$("#formula_0").hide();
		$("#formula_1").hide();
		$("#formula_2").hide();
		$("#formula_3").hide();
		// alert(page_id);
	}
	if ($("#formula").val() == 1) {
		$("#formula_0").show();
		$("#formula_1").hide();
		$("#formula_2").hide();
		$("#formula_3").hide();
	}
	if ($("#formula").val() == 2) {
		$("#formula_0").hide();
		$("#formula_1").show();
		$("#formula_2").hide();
		$("#formula_3").hide();
	}
	if ($("#formula").val() == 3) {
		$("#formula_0").hide();
		$("#formula_1").hide();
		$("#formula_2").show();
		$("#formula_3").hide();
	}
	if ($("#formula").val() == 4) {
		$("#formula_div").show();
		$("#formula_0").hide();
		$("#formula_1").hide();
		$("#formula_2").hide();
		$("#formula_3").show();
	}
}