// 操作界面固定高度
$(".lmis-panel").height($(window).height() - 136);

// 初始化查询
function initCondition() {
	$("#condition input").each(function(){
		$(this).val("");
	});
//	$("#claim_flag").val("B");
//	$("#claim_flag").find("option[text='未处理']").attr("selected",true);
}

// 查询
function query() {
//	loadingStyle();
	openIdDiv("data",
			"/BT-LMIS/control/omsInterfaceExcpeitonHandlingController/load.do?"
			+ $("#condition").serialize()
			+ "&loadingType=DATA&isQuery=true&pageName=data&tableName=jk_claim_data&startRow="
			+ $("#startRow").val()
			+ "&endRow="
			+ $("#startRow").val()
			+ "&page="
			+ $("#pageIndex").val()
			+ "&pageSize="
			+ $("#pageSize").val()
			
	);
	// 解除旋转
//	setTimeout("cancelLoadingStyle()", 1000);
}

//分页跳转
function pageJump() {
	query();
}

function refreshCondition() {
	initCondition();
	query();
}

function endueDisplay(id) {
	var control= $("#" + id);
	var display= $("#" + id + "Display");
	if(control.val() == "") {
		display.val("");
	} else {
		display.val(control.next().prop("placeholder"));
	}
}

function emptyProcess() {
	$("#claimId").val("");
	$("#pn").text("");
	$("#platformNumber").val("")
	$("#rn").text("");
	$("#relatedNumber").val("")
	$("#en").text("");
	$("#expressNumber").val("")
	emptyData();
}

function emptyData() {
	// 物流服务商
	initializeSelect("carriers");
	$("#carriers").children(":first").siblings().remove();
	$("#carriers").siblings("ul").children(":first").siblings().remove();
	// 发货仓库
	initializeSelect("warehouses");
	$("#warehouses").children(":first").siblings().remove();
	$("#warehouses").siblings("ul").children(":first").siblings().remove();
	// 出库时间
	$("#transportTime").val("");
	// 店铺
	initializeSelect("stores");
	$("#stores").children(":first").siblings().remove();
	$("#stores").siblings("ul").children(":first").siblings().remove();
	// 订单金额
	$("#orderAmount").val("");
	// 收件人
	$("#recipient").val("");
	// 联系电话
	$("#phone").val("");
	// 联系地址
	$("#address").val("");
	//
	initCheckInfo(".oiehCheckInfo");
	
}

function getData(claimId) {
	loadingStyle();
	emptyData();
	$.ajax({
		type: "POST",
      	url: "/BT-LMIS/control/omsInterfaceExcpeitonHandlingController/getData.do",
        dataType: "json",
        data: {"express_number": $("#expressNumber").val().trim(),"id":$("#claimId").val().trim()},
        success: function (result) {
        	if(result.result_code == "ERROR") {
        		alert(result.result_content);
        		
        	} else {
				// 平台订单号
				$("#pn").html(result.claim.erpOrderCode);
				$("#platformNumber").val(result.claim.erpOrderCode);
				// 相关单据号
				$("#rn").html(result.claim.omsOrderCode);
				$("#relatedNumber").val(result.claim.omsOrderCode);
				// 快递单号
				$("#en").html(result.claim.transNumber);
				$("#expressNumber").val(result.claim.transNumber);
        		if(!isNull(result.data)) {
					var info= result.data;
					
					// 物流服务商
					var content1 = "";
	        		var content2 = "";
					var carrier= result.carriers;
					if(isNull(info.transport_code)) {
						for(var i= 0; i< carrier.length; i++){
							content1+= '<option value="' + carrier[i].transport_code + '">'　+　carrier[i].transport_name +　'</option>';
							content2+= '<li class="m-list-item" data-value="' + carrier[i].transport_code + '">' + carrier[i].transport_name + '</li>';
							
						}
						$("#carriers option:eq(0)").after(content1);
						$("#carriers").siblings("ul").children(":first").after(content2);
						
					} else {
						$("#carriers").val(info.transport_code);
						for(var i= 0; i< carrier.length; i++){
							content1+= '<option value="' + carrier[i].transport_code + '" ';
							content2+= '<li class="m-list-item';
							if(carrier[i].transport_code == info.transport_code) {
								content1+= 'selected= "selected" ';
								content2+= ' m-list-item-active'
								$("#carriers").next().val(info.transport_name);
								$("#carriers").next().attr("placeholder", info.transport_name);
								
							}
							content1+= '>'　+　carrier[i].transport_name +　'</option>';
							content2+= '" data-value="' + carrier[i].transport_code + '">' + carrier[i].transport_name + '</li>';
							
						}
		        		// 装上之后的值
						$("#carriers option:eq(0)").after(content1);
						$("#carriers").siblings("ul").children(":first").after(content2);
						
					}
					//
					endueDisplay("carriers");
					//
					checkValue($("#carriers"));
					// 仓库
					content1 = "";
	        		content2 = "";
					var warehouse= result.warehouses;
					if(isNull(info.warehouse_code)) {
						for(var i= 0; i< warehouse.length; i++){
							content1+= '<option value="' + warehouse[i].warehouse_code + '">'　+　warehouse[i].warehouse_name +　'</option>';
							content2+= '<li class="m-list-item" data-value="' + warehouse[i].warehouse_code + '">' + warehouse[i].warehouse_name + '</li>';
							
						}
						$("#warehouses option:eq(0)").after(content1);
						$("#warehouses").siblings("ul").children(":first").after(content2);
						
					} else {
						$("#warehouses").val(info.warehouse_code);
						for(var i= 0; i< warehouse.length; i++){
							content1+= '<option value="' + warehouse[i].warehouse_code + '" ';
							content2+= '<li class="m-list-item';
							if(warehouse[i].warehouse_code == info.warehouse_code) {
								content1+= 'selected= "selected" ';
								content2+= ' m-list-item-active'
								$("#warehouses").next().val(info.warehouse_name);
								$("#warehouses").next().attr("placeholder", info.warehouse_name);
								
							}
							content1+= '>'　+　warehouse[i].warehouse_name +　'</option>';
							content2+= '" data-value="' + warehouse[i].warehouse_code + '">' + warehouse[i].warehouse_name + '</li>';
							
						}
		        		// 装上之后的值
						$("#warehouses option:eq(0)").after(content1);
						$("#warehouses").siblings("ul").children(":first").after(content2);
						
					}
					//
					endueDisplay("warehouses");
					//
					checkValue($("#warehouses"));
					// 出库时间
					if(!isNull(info.transport_time)) {
						$("#transportTime").val(info.transport_time);
						
					}
					//
					checkValue($("#transportTime"));
					// 店铺
					content1 = "";
	        		content2 = "";
	        		var store= result.stores;
					if(isNull(info.store_code)) {
		        		for(var i= 0; i< store.length; i++){
							content1+= '<option value="' + store[i].store_code + '">'　+　store[i].store_name +　'</option>';
							content2+= '<li class="m-list-item" data-value="' + store[i].store_code + '">' + store[i].store_name + '</li>';
							
						}
						$("#stores option:eq(0)").after(content1);
						$("#stores").siblings("ul").children(":first").after(content2);
						
					} else {
						$("#stores").val(info.store_code);
						for(var i= 0; i< store.length; i++){
							content1+= '<option value="' + store[i].store_code + '" ';
							content2+= '<li class="m-list-item';
							if(store[i].store_code == info.store_code) {
								content1+= 'selected= "selected" ';
								content2+= ' m-list-item-active'
								$("#stores").next().val(info.store_name);
								$("#stores").next().attr("placeholder", info.store_name);
								
							}
							content1+= '>'　+　store[i].store_name +　'</option>';
							content2+= '" data-value="' + store[i].store_code + '">' + store[i].store_name + '</li>';
							
						}
		        		// 装上之后的值
						$("#stores option:eq(0)").after(content1);
						$("#stores").siblings("ul").children(":first").after(content2);
						
					}
					//
					endueDisplay("stores");
					//
					checkValue($("#stores"));
					// 订单金额
					if(!isNull(info.order_amount)) {
						$("#orderAmount").val(info.order_amount);
					}
					//
					checkValue($("#orderAmount"));
					// 收件人
					if(!isNull(info.shiptoname)) {
						$("#recipient").val(info.shiptoname);
					}
					//
					checkValue($("#recipient"));
					// 联系电话
					if(!isNull(info.phone)) {
						$("#phone").val(info.phone);
					}
					//
					checkValue($("#phone"));
					// 联系地址
					if(!isNull(info.detail_address)) {
						$("#address").val(info.detail_address);
					}
					//
					checkValue($("#address"));
				} else {
					// 承运商
					var content1 = "";
	        		var content2 = "";
					var carrier= result.carriers;
					for(var i= 0; i< carrier.length; i++){
						content1+= '<option value="' + carrier[i].transport_code + '">'　+　carrier[i].transport_name +　'</option>';
						content2+= '<li class="m-list-item" data-value="' + carrier[i].transport_code + '">' + carrier[i].transport_name + '</li>';
					}
					$("#carriers option:eq(0)").after(content1);
					$("#carriers").siblings("ul").children(":first").after(content2);
					// 仓库
					content1 = "";
	        		content2 = "";
					var warehouse= result.warehouses;
					for(var i= 0; i< warehouse.length; i++){
						content1+= '<option value="' + warehouse[i].warehouse_code + '">'　+　warehouse[i].warehouse_name +　'</option>';
						content2+= '<li class="m-list-item" data-value="' + warehouse[i].warehouse_code + '">' + warehouse[i].warehouse_name + '</li>';
					}
					$("#warehouses option:eq(0)").after(content1);
					$("#warehouses").siblings("ul").children(":first").after(content2);
					// 店铺
					content1 = "";
	        		content2 = "";
	        		var store= result.stores;
	        		for(var i= 0; i< store.length; i++){
						content1+= '<option value="' + store[i].store_code + '">'　+　store[i].store_name +　'</option>';
						content2+= '<li class="m-list-item" data-value="' + store[i].store_code + '">' + store[i].store_name + '</li>';
					}
					$("#stores option:eq(0)").after(content1);
					$("#stores").siblings("ul").children(":first").after(content2);
				}
        	}
        	// 解除旋转
        	cancelLoadingStyle();
       	}
   	});
}

function judgeFlag(id){
	$.ajax({
		type: "POST",
      	url: "/BT-LMIS/control/omsInterfaceExcpeitonHandlingController/judgeFlag.do",
        dataType: "json",
        data: {id:id},
        success: function (result) {
        	if(result.result_code != "unprocess") {
        		alert(result.result_content);
        	}else{
        		$("#claimId").val(id);
        		getData();
        		$("#omsInterfaceExceptionHandling_form").modal();
        		//隐藏遮罩
        		$(".modal-backdrop").hide();
        	}
        },
        error: function() {

        }
	});
}

function tablesearch(column, sort){
	query();
}

function tableAction(currentRow, tableFunctionConfig) {
	if(tableFunctionConfig.dbclickTr == true) {
		var id = currentRow.children().find("input[type='checkbox']").val().trim();
		judgeFlag(id);
	}
}

function save(formId) {
	if(!checkValues(formId)) {
		return;
	}
	if(!confirm("是否提交？")) {
		return;
	}
	loadingStyle();
	$.ajax({
		type: "POST",
      	url: "/BT-LMIS/control/omsInterfaceExcpeitonHandlingController/createOMSWorkOrder.do",
        dataType: "json",
        data: $("#" + formId).serialize(),
        success: function (result) {
        	alert(result.result_content);
        	if(result.result_code == "SUCCESS") {
        		// 刷新页面
        		query();
        		// 隐藏弹窗
        		$("#omsInterfaceExceptionHandling_form").modal("hide");
        		// 初始化弹窗
        		emptyProcess();
        	}
        	// 解除旋转
        	cancelLoadingStyle();
        },
        error: function() {
        	// 解除旋转
        	cancelLoadingStyle();
        }
	});
}

function update() {
	if ($(".table tbody tr input[type='checkbox']:checked").length == 0) {
		alert("请选择一行");
		return;
	}
	var ids = '';
	$(".table tbody tr input[type='checkbox']:checked").each(function() {
		ids = ids+","+$(this).val();
	});
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/omsInterfaceExcpeitonHandlingController/update.do",
        dataType: "json",
        data: {ids: ids},
        success: function(result) {
        	alert(result.result_content);
        	if(result.result_code=="SUCCESS") {
        		query();
        	}
        },
        error: function() {
        	alert("系统异常-error");
        }
	});
}