$(function(){
//	showDBFJT();
//	loadLadder(1);
//	showDBFJTTH();
//	loadLadder(0);
////	discountShow();
//	discountShow_2();
//	managementShowOther();
	loadData();
});

//加载数据
function loadData(){
	var con_id = $("#id").val();
	if(con_id != ""){
		$.ajax({
			url : root +　"/control/expressContractController/loadManECOther.do",
			type :　"post",
			data : {
				"cb_id":con_id,
				"type" : $("#management_fee_type").val()
				},
			dataType : "json",
			success : function(result) {
				if(result.result_null_manEC=="false"){
					$("#manECList_other tbody").empty();
					var manEC = "";
					for(var i =0;i < result.manECListShow.length;i++){
						manEC += 
							'<tr><td>'　+　(i+1) +　'</td>'
							+ '<td>' +　result.manECListShow[i].ladderMethodName + '</td>'
							+ '<td>' +　result.manECListShow[i].bntInterval + '</td>'
							+ '<td>' +　result.manECListShow[i].bntIntervalUnit + '</td>'
							+ '<td>' +　result.manECListShow[i].rate + '</td>'
							+ '<td><a onclick="del_manEC_other(' + result.manECListShow[i].id +')" >删除</a></td>'
							+'</tr>';
					}
					$("#manECList_other tbody").append(manEC);
				} else if(result.result_null_manEC=="true") {
					$("#manECList_other tbody").empty();
				}
				//重置iframe高
				window.parent.initIframe(window,$("#management_fee_type").val());
			},
			error : function(result) {
				alert(result.result_content);
				//重置iframe高
				window.parent.initIframe(window,$("#management_fee_type").val());
			}
		});
	} else {
		//重置iframe高
		window.parent.initIframe(window,$("#management_fee_type").val());
	}
}


//删除管理费规则
function del_manEC_other(id){
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	if(!confirm("是否删除以下所选数据?")){
	  	return;
	  	
	}
	$.ajax({
		url : root + "/control/expressContractController/delManECOther.do",
		type : "post",
		data : {
			"id" : id,
			"cb_id" : con_id,
			"type" : $("#management_fee_type").val()
		},
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnManagementOther();
				$("#manECList_other tbody").empty();
				var manEC = "";
				for(var i =0;i < result.manECListShow.length;i++){
					manEC += 
						'<tr><td>'　+　(i+1) +　'</td>'
						+ '<td>' +　result.manECListShow[i].ladderMethodName + '</td>'
						+ '<td>' +　result.manECListShow[i].bntInterval + '</td>'
						+ '<td>' +　result.manECListShow[i].bntIntervalUnit + '</td>'
						+ '<td>' +　result.manECListShow[i].rate + '</td>'
						+ '<td><a onclick="del_manEC_other(' + result.manECListShow[i].id +')" >删除</a></td>'
						+'</tr>';
				}
				$("#manECList_other tbody").append(manEC);
				
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			} else if(result.result_null_manEC=="true") {
				$("#manECList_other tbody").empty();
			} 
		},
		error : function(result) {
			alert(result.result_content);
		}
	});
}

//保存管理费(除运费类型)
function saveManagementECOther(obj){
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	var ladder_type = $("#ladder_type_man_other").val();
	
	if (ladder_type<0) {
		ladder_type = null;
	}
	
	var json = {
			"cb_id" : con_id, 
			"ladder_method" : ladder_type,
			"management_fee_type" : obj
			}
	if(ladder_type > 0) {
		var num_1 = $("#payAll_1_man_other").val();
		var num_2 = $("#payAll_2_man_other").val();
		if(num_1　==　"" && num_2　==　""){
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
			json["compare_1"]=$("#compare_1_man_other").val();
			json["num_1"]=num_1;
			num_2 = num_1 + 1;
			json["compare_2"]="2";
		} else {
			json["compare_1"]=$("#compare_1_man_other").val();
			json["num_1"]=num_1;
			json["compare_2"]=$("#compare_2_man_other").val();
			json["num_2"]=num_2;
		}
		if(parseInt(num_1) > parseInt(num_2)){
			alert("区间赋值异常：下限大于上限！");
			return false;
		}
		if(parseInt(num_1) == parseInt(num_2)){
			alert("区间赋值异常：下限等于上限！");
			return false;
		}
		json["uom_1"] = $("#payAll_1_uom_man_other").val();
		json["rel"] = $("#rel_man_other").val();
		json["uom_2"] = $("#payAll_2_uom_man_other").val();
	}
	var charge_percent = $("#charge_percent_man_other").val();
	if(charge_percent == ""){
		alert("收费率未填写！");
		return false;
	}
	if(isNaN(charge_percent) || charge_percent < 0){
		alert("收费率输入不合法！");
		return false;
	}
	json["charge_percent"] = charge_percent;
	json["charge_percent_uom"] = $("#charge_percent_uom_man_other").val();
	$.ajax({
		url: root +　"/control/expressContractController/saveManECOther.do",
		type:　"post",
		data: json,
		dataType:　"json",
		success:　function(result){
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnManagementOther();
				$("#manECList_other tbody").empty();
				var manEC = "";
				for(var i =0;i < result.manECListShow.length;i++){
					manEC += 
						'<tr><td>'　+　(i+1) +　'</td>'
						+ '<td>' +　result.manECListShow[i].ladderMethodName + '</td>'
						+ '<td>' +　result.manECListShow[i].bntInterval + '</td>'
						+ '<td>' +　result.manECListShow[i].bntIntervalUnit + '</td>'
						+ '<td>' +　result.manECListShow[i].rate + '</td>'
						+ '<td><a onclick="del_manEC_other(' + result.manECListShow[i].id +')" >删除</a></td>'
						+'</tr>';
				}
				$("#manECList_other tbody").append(manEC);
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error:　function(result){
			alert(result.result_content);
		}
	});
}

function returnManagementOther(){
	initializeManagementOther();
	$("#div_10_other").hide();
	$("#div_11_other").hide();
	$("#div_9_other").hide();
	$("#add_1_other").hide();
}

function initializeManagementOther(){
	$("#charge_percent_man_other").val("");
	$("#charge_percent_uom_man_other option:eq(0)").prop("selected", "selected");
	$("#compare_1_man_other option:eq(0)").prop("selected", "selected");
	$("#payAll_1_man_other").val("");
	$("#payAll_1_uom_man_other option:eq(0)").prop("selected", "selected");
	$("#rel_man_other option:eq(0)").prop("selected", "selected");
	$("#compare_2_man_other option:eq(0)").prop("selected", "selected");
	$("#payAll_2_man_other").val("");
	$("#payAll_2_uom_man_other option:eq(0)").prop("selected", "selected");
	$("#ladder_type_man_other option:eq(0)").prop("selected", "selected");
}

function shiftPage_5Other(){
	if ($("#ladder_type_man_other").val() == -1) {
		$("#div_10_other").hide();
		$("#div_11_other").hide();
	}
	if ($("#ladder_type_man_other").val() == 0) {
		$("#div_10_other").show();
		$("#div_11_other").hide();
	}
	if ($("#ladder_type_man_other").val() == 1) {
		$("#div_10_other").show();
		$("#div_11_other").show();
	}
	if ($("#ladder_type_man_other").val() == 2) {
		$("#div_10_other").show();
		$("#div_11_other").show();
	}
}

function discountShow_2(){
	if($("#cb_totalFreightDiscount").prop("checked")==true){
		$("#totalDiscountDiv").show();
		var con_id = $("#id").val();
		if(con_id != ""){
			$.ajax({
				url : root + "/control/expressContractController/loadTotalFreightDiscount.do",
				type : "post",
				data : {
					"con_id" : con_id,
					"belong_to" : $("#contractOwner").val()
					},
				dataType : "json",
				success : function(result) {
					$("#tFDList_2 tbody").empty();
					if(result.result_null_tFD == "false"){
						var tFD = "";
						for(var i =0;i < result.tFDListShow.length;i++){
							tFD += 
								'<tr><td>'　+　result.tFDListShow[i].no +　'</td>'
								+ '<td>' +　result.tFDListShow[i].ladder_type + '</td>'
								+ '<td>' +　result.tFDListShow[i].region + '</td>'
								+ '<td>' +　result.tFDListShow[i].discount + '</td>'
								+ '<td>' +　result.tFDListShow[i].insurance_contain + '</td>'
								+ '<td><a onclick="del_tFD_2(' + result.tFDListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#tFDList_2 tbody").append(tFD);
					}
				},
				error :　function(result) {
					alert(result.result_content);
				}
			});
		}
	} else {
		$("#totalDiscountDiv").hide();
		return_TotalDiscount();
	}
}

function del_tFD_2(id){
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	if(!confirm("是否删除以下所选数据?")){
	  	return;
	  	
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
			if(result.result_code == "SUCCESS"){
				alert(result.result_content);
				return_TotalDiscount();
				$("#tFDList_2 tbody").empty();
				if(result.result_null_tFD == "false") {
					var tFD = "";
					for(var i = 0;i < result.tFDListShow.length; i++){
						tFD += 
							'<tr><td>'　+　result.tFDListShow[i].no +　'</td>'
							+ '<td>' +　result.tFDListShow[i].ladder_type + '</td>'
							+ '<td>' +　result.tFDListShow[i].region + '</td>'
							+ '<td>' +　result.tFDListShow[i].discount + '</td>'
							+ '<td>' +　result.tFDListShow[i].insurance_contain + '</td>'
							+ '<td><a onclick="del_tFD_2(' + result.tFDListShow[i].id +')" >删除</a></td>'
							+'</tr>';
					}
					$("#tFDList_2 tbody").append(tFD);
				}
			} else if(result.result_code == "FAILURE") {
				alert(result.result_content);
			}
		},
		error : function(result) {
			alert(result.result_content);
		}
	});
}

function save_TotalDiscount(){
	var con_id = $("#id").val();
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	var ladder_type= $("#totalDiscount_ladderType").val();
	if(ladder_type == 0){
		alert("请选择阶梯类型！");
		return false;
	}
	var json = {
			"con_id" : con_id,
			"insurance_contain" : checkedOrNot("cb_tFD_insurance_contain"),
			"ladder_type" : ladder_type};
	if(ladder_type != 1){
		var num_1 = $("#ladderType_payAll").val();
		var num_2 = $("#ladderType_payAll_0").val();
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
			json["compare_1"]=$("#ladderType_mark_1").val();
			json["num_1"]=num_1;
			num_2 = num_1 + 1;
			json["compare_2"]="2";
		} else {
			json["compare_1"]=$("#ladderType_mark_1").val();
			json["num_1"]=num_1;
			json["compare_2"]=$("#ladderType_mark_2").val();
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
		json["uom_1"]=$("#ladderType_payAll_uom").val();
		json["rel"]=$("#ladderType_rel").val();
		json["uom_2"]=$("#ladderType_payAll_uom_0").val();
	}
	var discount_percent = $("#ladderType_discount_percent").val();
	if(discount_percent == ""){
		alert("折扣率未填写！");
		return false;
	}
	if(isNaN(discount_percent) || discount_percent < 0){
		alert("折扣率输入不合法！");
		return false;
	}
	json["discount"]=discount_percent;
	json["discount_uom"]=$("#ladderType_dp_uom").val();
	$.ajax({
		url : root + "/control/expressContractController/saveTotalFreightDiscount.do",
		type : "post",
		data : json,
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				return_TotalDiscount();
				$("#tFDList_2 tbody").empty();
				var tFD = "";
				for(var i =0;i < result.tFDListShow.length;i++){
					tFD += 
						'<tr><td>'　+　result.tFDListShow[i].no +　'</td>'
						+ '<td>' +　result.tFDListShow[i].ladder_type + '</td>'
						+ '<td>' +　result.tFDListShow[i].region + '</td>'
						+ '<td>' +　result.tFDListShow[i].discount + '</td>'
						+ '<td>' +　result.tFDListShow[i].insurance_contain + '</td>'
						+ '<td><a onclick="del_tFD_2(' + result.tFDListShow[i].id +')" >删除</a></td>'
						+'</tr>';
				}
				$("#tFDList_2 tbody").append(tFD);
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error :　function(result) {
			alert(result.result_content);
		}
	});
}

function shift_totalDiscount_ladderType() {
	
	if ($("#totalDiscount_ladderType").val() == 0) {
		$("#ladderType_div1").hide();
		$("#ladderType_div2").hide();
		// alert(page_id);
	}
	
	if ($("#totalDiscount_ladderType").val() == 1) {
		$("#ladderType_div1").show();
		$("#ladderType_div2").hide();
	}
	
	if ($("#totalDiscount_ladderType").val() == 2) {
		$("#ladderType_div1").show();
		$("#ladderType_div2").show();
		$("#add_ladderType_label0").text("---超过部分阶梯价---");
		$("#add_ladderType_label1").text("总运费");
		$("#add_ladderType_label2").text("总运费");
	}
	
	if ($("#totalDiscount_ladderType").val() == 3) {
		$("#ladderType_div1").show();
		$("#ladderType_div2").show();
		$("#add_ladderType_label0").text("---总折扣阶梯价---");
		$("#add_ladderType_label1").text("总运费");
		$("#add_ladderType_label2").text("总运费");
	}
	
	if ($("#totalDiscount_ladderType").val() == 4) {
		$("#ladderType_div1").show();
		$("#ladderType_div2").show();
		$("#add_ladderType_label0").text("---单量折扣阶梯价---");
		$("#add_ladderType_label1").text("单量");
		$("#add_ladderType_label2").text("单量");
	}
	
}

function return_TotalDiscount(){
	$("#addTotalDiscount").hide();
	$("#ladderType_div1").hide();
	$("#ladderType_div2").hide();
	initializeDiscount();
}

function initializeDiscount(){
	$("#cb_tFD_insurance_contain").prop("checked",false);
	$("#ladderType_mark_1 option:eq(0)").prop("selected","selected");
	$("#ladderType_payAll").val("");
	$("#ladderType_payAll_uom option:eq(0)").prop("selected","selected");
	$("#ladderType_rel option:eq(0)").prop("selected","selected");
	$("#ladderType_mark_2 option:eq(0)").prop("selected","selected");
	$("#ladderType_payAll_0").val("");
	$("#ladderType_payAll_uom_0 option:eq(0)").prop("selected","selected");
	$("#ladderType_discount_percent").val("");
	$("#ladderType_dp_uom option:eq(0)").prop("selected","selected");
	$("#totalDiscount_ladderType option:eq(0)").prop("selected","selected");
}

function save_carrierFeeFlag(){
	var con_id = $("#id").val();
	if (con_id == "" || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	var json={"id" : $("#carrierFeeFlagId").val(), "con_id" :　con_id};
	if(checkedOrNot("cb_totalFreightDiscount") == 0){
		$.ajax({
			url : root + "/control/expressContractController/judgeExist.do",
			type : "post",
			data : {
				"con_id" : con_id, 
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
	json["totalFreightDiscount_flag"] = isChecked("cb_totalFreightDiscount");
	if(checkedOrNot("cb_managementFee") == 0){
		$.ajax({
			url : root + "/control/expressContractController/judgeExist.do",
			type : "post",
			data : {
				"con_id" : con_id, 
				"type" : "Man"
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
			alert("至少维护一条管理费规则！");
			return false;
		}
	}
	json["managementFee_flag"] = isChecked("cb_managementFee");
	$.ajax({
		url : root + "/control/carrierFeeController/saveCarrierFeeFlag.do",
		type : "post",
		data : json,
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
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

function freight_config(carrier_type, carrier_code){
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	returnCarrier();
	// 物流
	if(carrier_type == 0){
		$('#kd').hide();
		$('#wl').show();
		$("#myTab_kd").css("display","none");
		$("#myTab_wl").css("display","block");
		$("#kd_moudle_li").css("display","none");
		$("#wl_moudle_li").css("display","block");
		$("#yf_tab_sosp").show();
		$("#contractOwner").val(carrier_code);
		getGood_type(carrier_code);
		getFormula_tab($("#id").val());
		getDiscountTd($("#id").val(),'1','dt2');	
		getDiscountTd_for_offer($("#id").val(),'2','td_offer');	
		getcarriageInfo($("#id").val())		
		
		initData($("#id").val());	
		
		initData_glf($("#id").val());	
		
		getSpecial($("#id").val());	
		
	}
	// 快递
	if(carrier_type == 1){
		$('#wl').hide();
		$('#kd').show();
		$("#myTab_kd").css("display","block");
		$("#myTab_wl").css("display","none");
		$("#kd_moudle_li").css("display","block");
		$("#wl_moudle_li").css("display","none");
		// 初始化
		$("#sSE_id").val("");
		returnJBPC();
		returnPF();
		returnIEC();
		returnCOD();
		returnDelegatedPickup();
		$.ajax({
			url : root + "/control/FreightSospController/loadConfigure.do",
			type : "post",
			data : {"con_id": con_id, "carrier_code":　carrier_code},
			dataType : "json",
			success : function(result) {
				// 快递配置ID
				$("#eCC_id").val(result.config.id);
				$("#contractOwner").val(carrier_code);
				// 计重方式
				$("#weight option:eq("+ result.config.weight_method +")").prop("selected", "selected");
				if(result.config.weight_method==3){
					$("#percent").val(result.config.percent);
					$("#percent_uom option:eq("+ result.config.percent_uom +")").prop("selected", "selected");
				} else {
					$("#percent").val("");
					$("#percent_uom option:eq(0)").prop("selected", "selected");
				}
				shiftPage_1();
				// 总运费折扣
				$("#totalFreightDiscountDiv").hide();
//				$("#product_type_fr").empty();
//				var productType = '';
//				if(result.config.belong_to == 'SF'){
//					productType = '<option selected="selected" value="ALL">所有类型</option>';
//					for(var i =0;i < result.productType.length;i++){
//						productType += 
//							'<option value="' + result.productType[i].product_type_code + '">'　
//							+　result.productType[i].product_type_name +　'</option>'
//					}
//				} else {
//					productType = '<option selected="selected" value="">无</option>';
//				}
//				$("#product_type_fr").append(productType);
//				if(result.config.total_freight_discount == true){
//					$("#cb_0").prop("checked", "checked");
//				} else if(result.config.total_freight_discount == false){
//					$("#cb_0").prop("checked", false);
//				}
//				discountShow();
				// 保价费
				var productType = '';
				if(result.config.belong_to == 'SF'){
					productType = '<option selected="selected" value="ALL">所有类型</option>';
					for(var i =0;i < result.productType.length;i++){
						productType += 
							'<option value="' + result.productType[i].product_type_code + '">'　
							+　result.productType[i].product_type_name +　'</option>'
					}
				} else {
					productType = '<option selected="selected" value="">无</option>';
				}
				$("#product_type_iF").empty();
				$("#product_type_iF").append(productType);
				if(result.config.insurance == true){
					$("#cb_1").prop("checked", "checked");
				} else if(result.config.total_freight_discount == false){
					$("#cb_1").prop("checked", false);
				}
				insuranceECShow();
				//　COD手续费
				if(result.config.cod == true){
					$("#cb_3").prop("checked", "checked");
					codShow();
				} else if(result.config.total_freight_discount == false){
					$("#cb_3").prop("checked", false);
				}
				// 委托取件费
				if(result.config.delegated_pickup == true){
					$("#cb_4").prop("checked", "checked");
					delegatedPickupShow();
				} else if(result.config.total_freight_discount == false){
					$("#cb_4").prop("checked", false);
				}
				// 管理费
//				if(result.config.management==0){
//					$("#cb_7").prop("checked", "checked");
//				} else if(result.config.total_freight_discount==1){
//					$("#cb_7").prop("checked", false);
//				}
//				managementShow();
			},
			error : function(result) {
				alert(result);
			}
		});
	}
	$("#carrier_config").show();
}

function switchShow(id){
	if($("#"+id).css("display") == "none"){
		$("#"+id).show();
	} else {
		$("#"+id).hide();
	}
}

function cancelActive(id){
	if($("#"+id).hasClass("active")){
		$("#"+id).removeClass("active")
	} else if($("#"+id).hasClass("tab-pane in active")){
		$("#"+id).removeClass();
		$("#"+id).attr("class", "tab-pane");
	}
}

function del_carrier(id){
	if(!confirm("是否删除所选快递？")){
		return false;
	}
	returnCarrier();
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	$.ajax({
		url : root + "/control/FreightSospController/delCarrier.do",
		type : "post",
		data : {
			"id" : id,
			"con_id" : con_id
		},
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnCarrier();
				// 清空原有数据
				$("#carrierList tbody").empty();
				$("#carrierUsed_man").empty();
				if(result.result_null_ca == "false"){
					// 删除后还有数据
					// 对应承运商列表刷新
					var carrier = "";
					for(var i =0;i < result.carrierList.length;i++){
						carrier += 
							'<tr><td>'　+　result.carrierList[i].carrier_type_name +　'</td>'
							+ '<td>' +　result.carrierList[i].carrier + '</td>'
							+ '<td>'
							+ '<a onclick= "freight_config(' 
							+ result.carrierList[i].carrier_type
							+ ','
							+ result.carrierList[i].carrier_code  + ')" >配置</a>/'
							+ '<a onclick="del_carrier(\''+ result.carrierList[i].id +'\')" >删除</a></td>'
							+'</tr>';
					}
					$("#carrierList tbody").append(carrier);
					// 管理费中承运商下拉框刷新
					carrier= "<option selected= 'selected' value= 'ALL'>所有已维护承运商</option>";
					for(var i=0; i< result.carrierList.length; i++){
						carrier += "<option value='" + result.carrierList[i].carrier_code + "'>" + result.carrierList[i].carrier + "</option>"
					}
					$("#carrierUsed_man").append(carrier);
				} else {
					$("#carrierUsed_man").append("<option selected= 'selected' value= ''>无</option>");
				}
				//
				$("#div_1_fr").hide();
				$("#carrier_config").hide();
				$("#eCC_id").val("");
			} else if(result.result_code == "FAILURE") {
				alert(result.result_content);
			}
		},
		error : function(result) {
			alert(result.result_content);
		}
	});
}

function add_carrier(){
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	var carrier_type = $("#carrier_type_fr").val();
	if(carrier_type==0){
		alert("请选择承运商类型！");
		return false;
	}
	var carrier = $("#carrier_fr").val();
	if(carrier==0){
		alert("请选择承运商！");
		return false;
	}
	$.ajax({
		url : root + "/control/FreightSospController/addCarrier.do",
		type : "post",
		data : {
			"con_id" : con_id,
			"carrier_type" : carrier_type,
			"carrier" : carrier
		},
		dataType : "json",
		success : function(result) {
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				returnCarrier();
				// 对应承运商列表刷新
				$("#carrierList tbody").empty();
				var carrier = "";
				for(var i= 0; i< result.carrierList.length; i++){
					carrier += 
						'<tr><td>'　+　result.carrierList[i].carrier_type_name +　'</td>'
						+ '<td>' +　result.carrierList[i].carrier + '</td>'
						+ '<td>'
						+ '<a onclick= "freight_config(' 
						+ "'" + result.carrierList[i].carrier_type + "'"
						+ ','
						+ "'" + result.carrierList[i].carrier_code + "'" + ')" >配置</a>/'
						+ '<a onclick="del_carrier(' + result.carrierList[i].id +')" >删除</a></td>'
						+'</tr>';
				}
				$("#carrierList tbody").append(carrier);
				// 管理费中承运商下拉框刷新
				$("#carrierUsed_man").empty();
				carrier = "<option selected= 'selected' value= 'ALL'>所有已维护承运商</option>";
				for(var i= 0; i< result.carrierList.length; i++){
					carrier += "<option value='" + result.carrierList[i].carrier_code + "'>" + result.carrierList[i].carrier + "</option>"
				}
				$("#carrierUsed_man").append(carrier);
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error : function(result) {
			alert(result);
		}
	});
}

function search_carrier(){
	var carrier_type = $("#carrier_type_fr").val();
	if(carrier_type==0){
		$("#div_2_fr").hide();
	} else {
		$("#div_2_fr").show();
		$.ajax({
			url : root + "/control/FreightSospController/searchCarrier.do",
			type : "post",
			data : {
				"carrier_type" : carrier_type
			},
			dataType : "json",
			success : function(result) {
				$("#carrier_fr").empty();
				var carrier = '<option value="0">请选择</option>';
				for(var i =0;i < result.carrierList.length;i++){
					carrier += 
						'<option value="' + result.carrierList[i].transport_code + '">'　
						+　result.carrierList[i].transport_name +　'</option>'
				}
				$("#carrier_fr").append(carrier);
			},
			error : function(result) {
				alert(result);
			}
		});
	}
}

function returnCarrier(){
	initializeCarrier();
	$("#div_2_fr").hide();
	$("#div_1_fr").hide();
}

function initializeCarrier(){
	$("#carrier_type_fr option:eq(0)").prop("selected", "selected");
	$("#carrier_type_fr option:eq(0)").prop("selected", "selected");
}

function del_ladder(id){
	var con_id = $("#id").val(); 
	if (con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	if(!confirm("是否删除以下所选数据?")){
	  	return;
	  	
	}
	$.ajax({
		url : root + "/control/packageChargeController/delLadder.do",
		type : "post",
		data : {
			"id" : id
		},
		dataType : "json",
		success : function(result) {
			if(result.path.return_flag == 1){
				if(result.path.insurance == 2){
					if(result.result_code=="SUCCESS"){
						alert(result.result_content);
						$("#ladder_region_1 tbody").empty();
						var ladder_region = "";
						for(var i =0;i < result.regionListShow.length;i++){
							ladder_region += 
								'<tr><td>'　+　result.regionListShow[i].region +　'</td>'
								+ '<td>' +　result.regionListShow[i].charge_content + '</td>'
								+ '<td><a onclick="del_ladder(' + result.regionListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#ladder_region_1 tbody").append(ladder_region);
					} else if(result.result_code=="FAILURE") {
						alert(result.result_content);
					} else if(result.result_null_region=="true") {
						$("#ladder_region_1 tbody").empty();
					} 
				} else if(result.path.insurance == 3){
					if(result.result_code=="SUCCESS"){
						alert(result.result_content);
						$("#ladder_region_2 tbody").empty();
						var ladder_region = "";
						for(var i =0;i < result.regionListShow.length;i++){
							ladder_region += 
								'<tr><td>'　+　result.regionListShow[i].region +　'</td>'
								+ '<td>' +　result.regionListShow[i].charge_content + '</td>'
								+ '<td><a onclick="del_ladder(' + result.regionListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#ladder_region_2 tbody").append(ladder_region);
					} else if(result.result_code=="FAILURE") {
						alert(result.result_content);
					} else if(result.result_null_region=="true") {
						$("#ladder_region_2 tbody").empty();
					} 
				}
			} else {
				if(result.path.insurance == 2){
					if(result.result_code=="SUCCESS"){
						alert(result.result_content);
						$("#ladder_region_3 tbody").empty();
						var ladder_region = "";
						for(var i =0;i < result.regionListShow.length;i++){
							ladder_region += 
								'<tr><td>'　+　result.regionListShow[i].region +　'</td>'
								+ '<td>' +　result.regionListShow[i].charge_content + '</td>'
								+ '<td><a onclick="del_ladder(' + result.regionListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#ladder_region_3 tbody").append(ladder_region);
					} else if(result.result_code=="FAILURE") {
						alert(result.result_content);
					} else if(result.result_null_region=="true") {
						$("#ladder_region_3 tbody").empty();
					} 
				} else if(result.path.insurance == 3){
					if(result.result_code=="SUCCESS"){
						alert(result.result_content);
						$("#ladder_region_4 tbody").empty();
						var ladder_region = "";
						for(var i =0;i < result.regionListShow.length;i++){
							ladder_region += 
								'<tr><td>'　+　result.regionListShow[i].region +　'</td>'
								+ '<td>' +　result.regionListShow[i].charge_content + '</td>'
								+ '<td><a onclick="del_ladder(' + result.regionListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#ladder_region_4 tbody").append(ladder_region);
					} else if(result.result_code=="FAILURE") {
						alert(result.result_content);
					} else if(result.result_null_region=="true") {
						$("#ladder_region_4 tbody").empty();
					} 
				}
			}
		},
		error : function(result) {
			alert(result.result_content);
		}
	});
}
function loadLadder(return_flag){
	var con_id = $("#id").val();
	if(con_id != ""){
		var json = {"con_id" : con_id, "return_flag" : return_flag};
		var insurance = "";
		if(return_flag == 1){
			insurance =  $("#DBFJT").val();
		} else {
			insurance =  $("#DBFJTTH").val();
		}
		json["insurance"] = insurance;
		$.ajax({
			url : root +　"/control/packageChargeController/loadLadder.do",
			type :　"post",
			data : json,
			dataType : "json",
			success : function(result) {
				if(return_flag == 1){
					if(insurance == 1){
						if(result.result_null_region=="false"){
							$("#dbf_ladder_id_1").val(result.ladder.id);
							$("#percent_dbf_1").val(result.ladder.charge_percent);
							$("#percent_uom_dbf_1 option:eq(" + result.ladder.charge_percent_uom +")").prop("selected","selected");
							if(result.ladder.charge_min_flag == 0){
								$("#charge_min_flag_dbf_1").prop("checked", "checked");
								$("#charge_min_dbf_1").val(result.ladder.charge_min);
								$("#charge_min_uom_dbf_1 option:eq(" + result.ladder.charge_min_uom +")").prop("selected","selected");
							}
						} else if(result.result_null_region=="true") {
							$("#dbf_ladder_id_1").val("");
							$("#percent_dbf_1").val("");
							$("#percent_uom_dbf_1 option:eq(0)").prop("selected","selected");
							$("#charge_min_flag_dbf_1").prop("checked", false);
							$("#charge_min_dbf_1").val("");
							$("#charge_min_uom_dbf_1 option:eq(0)").prop("selected","selected");
						}
					} else if(insurance == 2) {
						if(result.result_null_region=="false"){
							$("#ladder_region_1 tbody").empty();
							var ladder_region = "";
							for(var i =0;i < result.regionListShow.length;i++){
								ladder_region += 
									'<tr><td>'　+　result.regionListShow[i].region +　'</td>'
									+ '<td>' +　result.regionListShow[i].charge_content + '</td>'
									+ '<td><a onclick="del_ladder(' + result.regionListShow[i].id +')" >删除</a></td>'
									+'</tr>';
							}
							$("#ladder_region_1 tbody").append(ladder_region);
						} else if(result.result_null_region=="true") {
							$("#ladder_region_1 tbody").empty();
						}
					} else if(insurance == 3) {
						if(result.result_null_region=="false"){
							$("#ladder_region_2 tbody").empty();
							var ladder_region = "";
							for(var i =0;i < result.regionListShow.length;i++){
								ladder_region += 
									'<tr><td>'　+　result.regionListShow[i].region +　'</td>'
									+ '<td>' +　result.regionListShow[i].charge_content + '</td>'
									+ '<td><a onclick="del_ladder(' + result.regionListShow[i].id +')" >删除</a></td>'
									+'</tr>';
							}
							$("#ladder_region_2 tbody").append(ladder_region);
						} else if(result.result_null_region=="true") {
							$("#ladder_region_2 tbody").empty();
						}
					}
				} else {
					if(insurance == 1){
						if(result.result_null_region=="false"){
							$("#dbf_ladder_id_2").val(result.ladder.id);
							$("#percent_dbf_2").val(result.ladder.charge_percent);
							$("#percent_uom_dbf_2 option:eq(" + result.ladder.charge_percent_uom +")").prop("selected","selected");
							if(result.ladder.charge_min_flag == 0){
								$("#charge_min_flag_dbf_2").prop("checked", "checked");
								$("#charge_min_dbf_2").val(result.ladder.charge_min);
								$("#charge_min_uom_dbf_2 option:eq(" + result.ladder.charge_min_uom +")").prop("selected","selected");
							}
						} else if(result.result_null_region=="true") {
							$("#dbf_ladder_id_2").val("");
							$("#percent_dbf_2").val("");
							$("#percent_uom_dbf_2 option:eq(0)").prop("selected","selected");
							$("#charge_min_flag_dbf_2").prop("checked", false);
							$("#charge_min_dbf_2").val("");
							$("#charge_min_uom_dbf_2 option:eq(0)").prop("selected","selected");
						}
					} else if(insurance == 2) {
						if(result.result_null_region=="false"){
							$("#ladder_region_3 tbody").empty();
							var ladder_region = "";
							for(var i =0;i < result.regionListShow.length;i++){
								ladder_region += 
									'<tr><td>'　+　result.regionListShow[i].region +　'</td>'
									+ '<td>' +　result.regionListShow[i].charge_content + '</td>'
									+ '<td><a onclick="del_ladder(' + result.regionListShow[i].id +')" >删除</a></td>'
									+'</tr>';
							}
							$("#ladder_region_3 tbody").append(ladder_region);
						} else if(result.result_null_region=="true") {
							$("#ladder_region_3 tbody").empty();
						}
					} else if(insurance == 3) {
						if(result.result_null_region=="false"){
							$("#ladder_region_4 tbody").empty();
							var ladder_region = "";
							for(var i =0;i < result.regionListShow.length;i++){
								ladder_region += 
									'<tr><td>'　+　result.regionListShow[i].region +　'</td>'
									+ '<td>' +　result.regionListShow[i].charge_content + '</td>'
									+ '<td><a onclick="del_ladder(' + result.regionListShow[i].id +')" >删除</a></td>'
									+'</tr>';
							}
							$("#ladder_region_4 tbody").append(ladder_region);
						} else if(result.result_null_region=="true") {
							$("#ladder_region_4 tbody").empty();
						}
					}
				}
				
			},
			error : function(result) {
				alert(result.result_content);
			}
		});
	}
}
function save_package_price_ladder(return_flag){
	var con_id = $("#id").val();
	if(con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	var json = {"con_id" : con_id, "return_flag" : return_flag};
	var insurance = "";
	if(return_flag==1){
		json["id"] = $("#dbf_ladder_id_1").val();
		var insurance =  $("#DBFJT").val();
		json["insurance"] = insurance;
		if(insurance == 1){
			if($("#percent_dbf_1").val()==""){
				alert("订单金额百分比未填写！");
				return false;
			} else {
				json["charge_percent"] = $("#percent_dbf_1").val();
				json["charge_percent_uom"] = $("#percent_uom_dbf_1").val();
			}
			var charge_min_flag = checkedOrNot("charge_min_flag_dbf_1");
			json["charge_min_flag"] = charge_min_flag;
			if(charge_min_flag == 0){
				if($("#charge_min_dbf_1").val()==""){
					alert("最低金额未填写！");
					return false;
				} else　{
					json["charge_min"] = $("#charge_min_dbf_1").val();
					json["charge_min_uom"] = $("#charge_min_uom_dbf_1").val();
				}
			}
		} else if(insurance == 2){
			var num_1 = $("#num_1_dbf_1").val();
			var num_2 = $("#num_2_dbf_1").val();
			if(num_1　==　"" && num_2　==　""){
				alert("请设定区间！");
				return false;
			}
			if(num_1 < 0){
				alert("区间下限为负！");
				return false;
			}
			if(num_1 == ""){
				alert("区间下限未填写！");
				return false;
			} else if(num_2 == ""){
				json["compare_1"]=$("#compare_1_dbf_1").val();
				json["num_1"]=num_1;
				num_2 = num_1 + 1;
				json["compare_2"]="2";
			} else {
				json["compare_1"]=$("#compare_1_dbf_1").val();
				json["num_1"]=num_1;
				json["compare_2"]=$("#compare_2_dbf_1").val();
				json["num_2"]=num_2;
			}
			if(parseInt(num_1) > parseInt(num_2)){
				alert("区间赋值异常：下限大于上限！");
				return false;
			}
			json["uom_1"] = $("#uom_1_dbf_1").val();
			json["rel"] = $("#rel_dbf_1").val();
			json["uom_2"] = $("#uom_2_dbf_1").val();
			if($("#radio_0").prop("checked") == true){
				if($("#charge_percent_dbf_1").val()==""){
					alert("收费比率未填写！");
					return false;
				} else {
					json["charge_percent"] = $("#charge_percent_dbf_1").val();
					json["charge_percent_uom"] = $("#charge_percent_uom_dbf_1").val();
				}
			} else if($("#radio_1").prop("checked") == true){
				if($("#charge_dbf_1").val()==""){
					alert("收费未填写！");
					return false;
				} else {
					json["charge"] = $("#charge_dbf_1").val();
					json["charge_uom"] = $("#charge_uom_dbf_1").val();
				}
			}
		} else if(insurance == 3){
			var num_1 = $("#num_1_dbf_2").val();
			var num_2 = $("#num_2_dbf_2").val();
			if(num_1　==　"" && num_2　==　""){
				alert("请设定区间！");
				return false;
			}
			if(num_1 < 0){
				alert("区间下限为负！");
				return false;
			}
			if(num_1 == ""){
				alert("区间下限未填写！");
				return false;
			} else if(num_2 == ""){
				json["compare_1"]=$("#compare_1_dbf_2").val();
				json["num_1"]=num_1;
				num_2 = num_1 + 1;
				json["compare_2"]="2";
			} else {
				json["compare_1"]=$("#compare_1_dbf_2").val();
				json["num_1"]=num_1;
				json["compare_2"]=$("#compare_2_dbf_2").val();
				json["num_2"]=num_2;
			}
			if(parseInt(num_1) > parseInt(num_2)){
				alert("区间赋值异常：下限大于上限！");
				return false;
			}
			json["uom_1"] = $("#uom_1_dbf_2").val();
			json["rel"] = $("#rel_dbf_2").val();
			json["uom_2"] = $("#uom_2_dbf_2").val();
			if($("#radio_2").prop("checked") == true){
				if($("#charge_percent_dbf_2").val()==""){
					alert("收费比率未填写！");
					return false;
				} else {
					json["charge_percent"] = $("#charge_percent_dbf_2").val();
					json["charge_percent_uom"] = $("#charge_percent_uom_dbf_2").val();
				}
			} else if($("#radio_3").prop("checked") == true){
				if($("#charge_dbf_2").val()==""){
					alert("收费未填写！");
					return false;
				} else {
					json["charge"] = $("#charge_dbf_2").val();
					json["charge_uom"] = $("#charge_uom_dbf_2").val();
				}
			}
		}
	} else {
		json["id"] = $("#dbf_ladder_id_2").val();
		var insurance =  $("#DBFJTTH").val();
		json["insurance"] = insurance;
		if(insurance == 1){
			if($("#percent_dbf_2").val()==""){
				alert("订单金额百分比未填写！");
				return false;
			} else {
				json["charge_percent"] = $("#percent_dbf_2").val();
				json["charge_percent_uom"] = $("#percent_uom_dbf_2").val();
			}
			var charge_min_flag = checkedOrNot("charge_min_flag_dbf_2");
			json["charge_min_flag"] = charge_min_flag;
			if(charge_min_flag == 0){
				if($("#charge_min_dbf_2").val()==""){
					alert("最低金额未填写！");
					return false;
				} else　{
					json["charge_min"] = $("#charge_min_dbf_2").val();
					json["charge_min_uom"] = $("#charge_min_uom_dbf_2").val();
				}
			}
		} else if(insurance == 2){
			var num_1 = $("#num_1_dbf_3").val();
			var num_2 = $("#num_2_dbf_3").val();
			if(num_1　==　"" && num_2　==　""){
				alert("请设定区间！");
				return false;
			}
			if(num_1 < 0){
				alert("区间下限为负！");
				return false;
			}
			if(num_1 == ""){
				alert("区间下限未填写！");
				return false;
			} else if(num_2 == ""){
				json["compare_1"]=$("#compare_1_dbf_3").val();
				json["num_1"]=num_1;
				num_2 = num_1 + 1;
				json["compare_2"]="2";
			} else {
				json["compare_1"]=$("#compare_1_dbf_3").val();
				json["num_1"]=num_1;
				json["compare_2"]=$("#compare_2_dbf_3").val();
				json["num_2"]=num_2;
			}
			if(parseInt(num_1) > parseInt(num_2)){
				alert("区间赋值异常：下限大于上限！");
				return false;
			}
			json["uom_1"] = $("#uom_1_dbf_3").val();
			json["rel"] = $("#rel_dbf_3").val();
			json["uom_2"] = $("#uom_2_dbf_3").val();
			if($("#radio_4").prop("checked") == true){
				if($("#charge_percent_dbf_3").val()==""){
					alert("收费比率未填写！");
					return false;
				} else {
					json["charge_percent"] = $("#charge_percent_dbf_3").val();
					json["charge_percent_uom"] = $("#charge_percent_uom_dbf_3").val();
				}
			} else if($("#radio_5").prop("checked") == true){
				if($("#charge_dbf_3").val()==""){
					alert("收费未填写！");
					return false;
				} else {
					json["charge"] = $("#charge_dbf_3").val();
					json["charge_uom"] = $("#charge_uom_dbf_3").val();
				}
			}
		} else if(insurance == 3){
			var num_1 = $("#num_1_dbf_4").val();
			var num_2 = $("#num_2_dbf_4").val();
			if(num_1　==　"" && num_2　==　""){
				alert("请设定区间！");
				return false;
			}
			if(num_1 < 0){
				alert("区间下限为负！");
				return false;
			}
			if(num_1 == ""){
				alert("区间下限未填写！");
				return false;
			} else if(num_2 == ""){
				json["compare_1"]=$("#compare_1_dbf_4").val();
				json["num_1"]=num_1;
				num_2 = num_1 + 1;
				json["compare_2"]="2";
			} else {
				json["compare_1"]=$("#compare_1_dbf_4").val();
				json["num_1"]=num_1;
				json["compare_2"]=$("#compare_2_dbf_4").val();
				json["num_2"]=num_2;
			}
			if(parseInt(num_1) > parseInt(num_2)){
				alert("区间赋值异常：下限大于上限！");
				return false;
			}
			json["uom_1"] = $("#uom_1_dbf_4").val();
			json["rel"] = $("#rel_dbf_4").val();
			json["uom_2"] = $("#uom_2_dbf_4").val();
			if($("#radio_6").prop("checked") == true){
				if($("#charge_percent_dbf_4").val()==""){
					alert("收费比率未填写！");
					return false;
				} else {
					json["charge_percent"] = $("#charge_percent_dbf_4").val();
					json["charge_percent_uom"] = $("#charge_percent_uom_dbf_4").val();
				}
			} else if($("#radio_7").prop("checked") == true){
				if($("#charge_dbf_4").val()==""){
					alert("收费未填写！");
					return false;
				} else {
					json["charge"] = $("#charge_dbf_4").val();
					json["charge_uom"] = $("#charge_uom_dbf_4").val();
				}
			}
		}
	}
	$.ajax({
		url : root + "/control/packageChargeController/savePackagePriceLadder.do",
		type : "post",
		data : json,
		dataType : "json",
		success : function(result){
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				if(return_flag == 1) {
					if(insurance == 1){
						if($("#dbf_ladder_id_1").val()==""){
							$("#dbf_ladder_id_1").val(result.id);
						}
					} else if(insurance == 2){
						$("#ladder_region_1 tbody").empty();
						var ladder_region = "";
						for(var i =0;i < result.regionListShow.length;i++){
							ladder_region += 
								'<tr><td>'　+　result.regionListShow[i].region +　'</td>'
								+ '<td>' +　result.regionListShow[i].charge_content + '</td>'
								+ '<td><a onclick="del_ladder(' + result.regionListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#ladder_region_1 tbody").append(ladder_region);
					} else if(insurance == 3){
						$("#ladder_region_2 tbody").empty();
						var ladder_region = "";
						for(var i =0;i < result.regionListShow.length;i++){
							ladder_region += 
								'<tr><td>'　+　result.regionListShow[i].region +　'</td>'
								+ '<td>' +　result.regionListShow[i].charge_content + '</td>'
								+ '<td><a onclick="del_ladder(' + result.regionListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#ladder_region_2 tbody").append(ladder_region);
					}
				} else {
					if(insurance == 1){
						if($("#dbf_ladder_id_2").val()==""){
							$("#dbf_ladder_id_2").val(result.id);
						}
					} else if(insurance == 2){
						$("#ladder_region_3 tbody").empty();
						var ladder_region = "";
						for(var i =0;i < result.regionListShow.length;i++){
							ladder_region += 
								'<tr><td>'　+　result.regionListShow[i].region +　'</td>'
								+ '<td>' +　result.regionListShow[i].charge_content + '</td>'
								+ '<td><a onclick="del_ladder(' + result.regionListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#ladder_region_3 tbody").append(ladder_region);
					} else if(insurance == 3){
						$("#ladder_region_4 tbody").empty();
						var ladder_region = "";
						for(var i =0;i < result.regionListShow.length;i++){
							ladder_region += 
								'<tr><td>'　+　result.regionListShow[i].region +　'</td>'
								+ '<td>' +　result.regionListShow[i].charge_content + '</td>'
								+ '<td><a onclick="del_ladder(' + result.regionListShow[i].id +')" >删除</a></td>'
								+'</tr>';
						}
						$("#ladder_region_4 tbody").append(ladder_region);
					}
				}
			} else if(result.result_code=="FAILURE") {
				alert(result.result_content);
			}
		},
		error : function(result){
			alert(result);
		}
		
	});
}
function save_package_price(){
	var con_id = $("#id").val();
	if(con_id == 0 || con_id == null){
		alert("无对应合同主信息！");
		return false;
	}
	var num=0;
	var carrier_charge = checkedOrNot("carrier_charge_switch");
	var storage = checkedOrNot("storage_switch");
	var operation = checkedOrNot("operation_switch");
	var consumable = checkedOrNot("consumable_switch");
	if(carrier_charge==0){
		num++;
	}
	if(storage==0){
		num++;
	}
	if(operation==0){
		num++;
	}
	if(consumable==0){
		num++;
	}
	var id = $("#dbf_id").val();
	if(id == "" && num < 2){
		alert("新增打包价至少含两项收费项！");
		return false;
	}
	var json = {"id" : id, "con_id" : con_id, "carrier_charge" : carrier_charge, 
			"storage" : storage, "operation" : operation, "consumable" : consumable};
	if($("#unit_price_dbf").val()==""){
		alert("打包费单价未填写！");
		return false;
	} else {
		json["unit_price"] = $("#unit_price_dbf").val();
		json["unit_price_uom"] = $("#unit_price_uom_dbf").val();
	}
	var insurance = $("#DBFJT").val();
	var return_flag = 1;
//	if(insurance == 0){
//		alert("请选择保价费阶梯！");
//		return false;
//	} else {
//		var num = "";
//		$.ajax({
//			url : root + "/control/packageChargeController/judgeExistRecord.do",
//			type : "post",
//			data : {"con_id" : con_id, "return_flag" : return_flag, "insurance" : insurance},
//			dataType : "json",
//			async: false,
//			success : function(result){
//				if(result.result_code == "FAILURE"){
//					alert(result.result_content);
//				} else {
//					num = result.num;
//				}
//			},
//			error : function(result){
//				alert(result);
//			}
//		});
//		if(num == 0){
//			alert("至少维护所选保价费阶梯类型一条内容！");
//			return false;
//		}
		json["insurance"] = insurance;
//	}
	if($("#return_unit_price_dbf").val()==""){
		alert("退货单价未填写！");
		return false;
	} else {
		json["return_unit_price"] = $("#return_unit_price_dbf").val();
		json["return_unit_price_uom"] = $("#return_unit_price_uom_dbf").val();
	}
	if($("#delegated_pickup_unit_price_dbf").val()==""){
		alert("委托取件服务费单价未填写！");
		return false;
	} else {
		json["delegated_pickup_unit_price"] = $("#delegated_pickup_unit_price_dbf").val();
		json["delegated_pickup_unit_price_uom"] = $("#delegated_pickup_unit_price_uom_dbf").val();
	}
	var return_insurance = $("#DBFJTTH").val();
	return_flag = 0;
//	if(return_insurance == 0){
//		alert("请选择退货保价费阶梯！");
//		return false;
//	} else {
//		var num = "";
//		$.ajax({
//			url : root + "/control/packageChargeController/judgeExistRecord.do",
//			type : "post",
//			data : {"con_id" : con_id, "return_flag" : return_flag, "insurance" : return_insurance},
//			dataType : "json",
//			async: false,
//			success : function(result){
//				if(result.result_code == "FAILURE"){
//					alert(result.result_content);
//				} else {
//					num = result.num;
//				}
//			},
//			error : function(result){
//				alert(result);
//			}
//		});
//		if(num == 0){
//			alert("至少维护所选退货保价费阶梯类型一条内容！");
//			return false;
//		}
		json["return_insurance"] = return_insurance;
//	}
	$.ajax({
		url : root + "/control/packageChargeController/savePackagePrice.do",
		type : "post",
		data : json,
		dataType : "json",
		success : function(result){
			if(result.result_code=="SUCCESS"){
				alert(result.result_content);
				if($("#dbf_id").val()==""){
					$("#dbf_id").val(result.id);
				}
			} else if(result.result_code=="FAILURE"){
				alert(result.result_content);
			}
		},
		error : function(result){
			alert(result);
		}
	});
	
}
//打包费－[计算公式]展示
function showDBFJT() {
	var divId = $("#DBFJT").val();
	var divObj2 = $("div[id^='DBFJT']");
	for (i = 1; i <= divObj2.length; i++) {
		if (i == divId) {
			$("#DBFJT" + i).css("display", "block");
		} else {
			$("#DBFJT" + i).css("display", "none");
		}
	}
};
function showDBFJTTH() {
	var divId = $("#DBFJTTH").val();
	var divObj2 = $("div[id^='DBFJTTH']");
	for (i = 1; i <= divObj2.length; i++) {
		if (i == divId) {
			$("#DBFJTTH" + i).css("display", "block");
		} else {
			$("#DBFJTTH" + i).css("display", "none");
		}
	}
};
//控制Div显示和隐藏
function changeShow(div) {
	var ifshow = $("#" + div).css("display");
	if (ifshow == "none") {
		$("#" + div).css("display", "block");
	} else {
		$("#" + div).css("display", "none");
	}
};