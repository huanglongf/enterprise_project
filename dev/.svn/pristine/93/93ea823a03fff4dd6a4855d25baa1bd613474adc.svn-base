// 工单管理与工单处理通用方法
$(function(){
	// 通过该方法来为每次弹出的模态框设置最新的zIndex值，从而使最新的modal显示在最前面
    $(document).on('show.bs.modal', '.modal', function (event) {$(this).css('z-index', 1040 + (10 * $('.modal:visible').length));});
	// NGINX配置地址赋值
	$.ajax({
		type: "POST",
      	url: "/BT-LMIS/control/commonController/nginxURL.do",
        dataType: "json",
        success: function (result) {
        	FileUpload= result.FileUpload;
        	FileDown= result.FileDown;
        	
        }
        
	});
	if($("#isCollapse").val()=="true") {
		$(".senior-search-shift").click();
	}

})

function endueDisplay(id) {
	var control= $("#" + id);
	var display= $("#" + id + "Display");
	if(control.val() == "") {
		display.val("");
		
	} else {
		display.val(control.next().prop("placeholder"));
		
	}
	
}

// 切换工单类型显示工单级别及异常原因
function shiftType(typeId, param) {
	var type = $("#" + typeId).val();
	var levelId= param["levelId"];
	var exceptionId= param["exceptionId"];
	// 去除之前的值
	// 工单级别
	$("#" +　levelId).children(":first").siblings().remove();
	$("#" +　levelId).siblings("ul").children(":first").siblings().remove();
	// 异常类型
	$("#" +　exceptionId).children(":first").siblings().remove();
	$("#" +　exceptionId).siblings("ul").children(":first").siblings().remove();
	if(type == "") {
		$("#" +　levelId).next().attr("disabled", "disabled");
		$("#" +　exceptionId).next().attr("disabled", "disabled");
		
	} else {
		$.ajax({
			type: "POST",
	        url: "/BT-LMIS/control/workOrderController/getLevelAndException.do",
	        dataType: "json",
	        data: {"wo_type": type },
	        success: function(result) {
	        	if(result.result_code == "ERROR") {
	        		alert(result.result_content);
	        		
	        	} else {
	        		var content1 = "";
	        		var content2 = "";
	        		// 工单级别
	        		var wo_level=result.wo_level;
					if("wo_level" in param) {
						$("#"+levelId).val(param["wo_level"]);
						for(var i=0; i<wo_level.length; i++) {
							content1+='<option value="'+wo_level[i].code+'" ';
							content2+='<li class="m-list-item';
							if(wo_level[i].code == param["wo_level"]) {
								content1+= 'selected= "selected" ';
								content2+= ' m-list-item-active';
								$("#"+levelId).next().val(wo_level[i].name);
								$("#"+levelId).next().attr("placeholder", wo_level[i].name);
								
							}
							content1+='>'+wo_level[i].name+'</option>';
							content2+='" data-value="'+wo_level[i].code+'">'+wo_level[i].name+'</li>';
							
						}
		        		// 装上之后的值
						$("#"+levelId+" option:eq(0)").after(content1);
						$("#"+levelId).siblings("ul").children(":first").after(content2);
						
					} else {
		        		for(var i=0; i<wo_level.length; i++) {
							content1+='<option value="'+ wo_level[i].code+'">'+wo_level[i].name+'</option>';
							content2+='<li class="m-list-item" data-value="'+wo_level[i].code+'">'+wo_level[i].name+'</li>';
							
						}
		        		// 装上之后的值
						$("#"+levelId+" option:eq(0)").after(content1);
						$("#"+levelId).siblings("ul").children(":first").after(content2);
						
					}
					$("#"+levelId).next().attr("disabled", false);
					//
					content1 = "";
	        		content2 = "";
					// 异常类型
					var exception_type=result.exception_type;
					if("exception_type" in param) {
						$("#"+exceptionId).val(param["exception_type"]);
						for(var i=0; i<exception_type.length; i++) {
							content1+='<option value="'+exception_type[i].type_name+'" ';
							content2+='<li class="m-list-item';
							if(exception_type[i].type_name == param["exception_type"]) {
								content1+= 'selected= "selected" ';
								content2+= ' m-list-item-active';
								$("#"+exceptionId).next().val(exception_type[i].type_name);
								$("#"+exceptionId).next().attr("placeholder", exception_type[i].type_name);
								
							}
							content1+='>'+exception_type[i].type_name+'</option>';
							content2+='" data-value="'+exception_type[i].type_name+'">'+exception_type[i].type_name+'</li>';
							
						}
		        		// 装上之后的值
						$("#"+exceptionId+" option:eq(0)").after(content1);
						$("#"+exceptionId).siblings("ul").children(":first").after(content2);
						
					} else {
						for(var i=0; i<exception_type.length; i++){
							content1+='<option value="'+exception_type[i].type_name + '">'+exception_type[i].type_name+'</option>';
							content2+='<li class="m-list-item" data-value="'+exception_type[i].type_name+'">'+exception_type[i].type_name+'</li>';
							
						}
		        		// 装上之后的值
						$("#"+exceptionId+" option:eq(0)").after(content1);
						$("#"+exceptionId).siblings("ul").children(":first").after(content2);
						
					}
					$("#"+exceptionId).next().attr("disabled", false);
	        		
	        	}
	        	
	        }
	        
		});
		
	}
	// 初始化显示内容
	initializeSelect(levelId);
	initializeSelect(exceptionId);
	if(!("check_flag" in param)) {
		// 清空工单类型对应级别与异常类型校验信息
		initCheckInfo("#"+ levelId);
		initCheckInfo("#"+ exceptionId);
		
	}
	
}

// 清除运单相关数据
function clearNoExpressNumber() {
	$("#tableTest").empty();
	//
	$("#expressNumber").parent().prev().children().eq(1).remove();
	// 物流服务商
	initializeSelect("carriers");
	$("#carriers").children(":first").siblings().remove();
	$("#carriers").siblings("ul").children(":first").siblings().remove();
	$("#carriers").next().attr("disabled", "disabled");
	initCheckInfo("#carriers");
	// 发货仓库
	initializeSelect("warehouses");
	$("#warehouses").children(":first").siblings().remove();
	$("#warehouses").siblings("ul").children(":first").siblings().remove();
	$("#warehouses").next().attr("disabled", "disabled");
	initCheckInfo("#warehouses");
	// 出库时间
	$("#transportTime").val("");
	$("#transportTime").prop("readonly", "readonly");
	initCheckInfo("#transportTime");
	// 平台订单号
	$("#platformNumber").val("");
	$("#platformNumber").prop("readonly", "readonly");
	initCheckInfo("#platformNumber");
	// 相关单据号
	$("#relatedNumber").val("");
	$("#relatedNumber").prop("readonly", "readonly");
	initCheckInfo("#relatedNumber");
	// 店铺
	initializeSelect("stores");
	$("#stores").children(":first").siblings().remove();
	$("#stores").siblings("ul").children(":first").siblings().remove();
	$("#stores").next().attr("disabled", "disabled");
	initCheckInfo("#stores");
	// 订单金额
	$("#orderAmount").val("");
	$("#orderAmount").prop("readonly", "readonly");
	initCheckInfo("#orderAmount");
	// 收件人
	$("#recipient").val("");
	$("#recipient").prop("readonly", "readonly");
	initCheckInfo("#recipient");
	// 联系电话
	$("#phone").val("");
	$("#phone").prop("readonly", "readonly");
	initCheckInfo("#phone");
	// 联系地址
	$("#address").val("");
	$("#address").prop("readonly", "readonly");
	initCheckInfo("#address");
	
}

// 初始化新增工单表单页面
function initalAddForm() {
	// 清空工单数量
	var num_node=$("#expressNumber").parent().prev().children(":first").next();
	if(num_node.length>0) {
		num_node.remove();
		
	}
	// 值还原
	// 附件名清空
	$("#fileName_common").val("");
	// 附件清空
	// 队列清空
	$("#fileQueue").children().each(function(){
		javascript:$('#myFile').uploadify('cancel', $(this).prop("id"));
		
	});
	// 已上传清空
	$("ims").empty();
	// 工单号
	$("#woNo").val("");
	// 是否自行处理
	if(!isChecked("bySelf")) {
		$("#bySelf").prop("checked", true);
		$("#realTime").prop("disabled", false);
		
	} else {
		if($("#realTime").prop("checked")) {
			$("#realTime").prop("checked", false);
			
		}
		
	}
	// 工单类型
	initializeSelect("woType");
	$("#woType").children(":first").siblings().remove();
	$("#woType").siblings("ul").children(":first").siblings().remove();
	// 工单级别
	initializeSelect("woLevel");
	$("#woLevel").children(":first").siblings().remove();
	$("#woLevel").siblings("ul").children(":first").siblings().remove();
	$("#woLevel").next().attr("disabled", "disabled");
	// 异常类型
	initializeSelect("exceptionType");
	$("#exceptionType").children(":first").siblings().remove();
	$("#exceptionType").siblings("ul").children(":first").siblings().remove();
	$("#exceptionType").next().attr("disabled", "disabled");
	// 查询人
	$("#queryPerson").val("");
	// 快递单号
	$("#expressNumber").val("");
	clearNoExpressNumber();
	// 主要处理内容说明
    $("#mistakeProductCount").val("");
    $("#mistakeBarcode").val("");
    $("#supplementExplain").val("");
	// 校验信息还原
	initCheckInfo(".addCheckInfo");
	
}

// 加载新增工单表单页面
function toWOAddForm() {
	$.ajax({
		type: "POST",
      	url: "/BT-LMIS/control/workOrderController/toAddForm.do",
        dataType: "json",
        data: {},
        success: function (result) {
        	if(result.result_code == "ERROR") {
        		alert(result.result_content);
        		
        	} else {
        		// 工单号
        		$("#woNo").val(result.woNo);
        		var content1 = "";
        		var content2 = "";
        		// 工单类型
        		for(var i= 0; i< result.wo_type.length; i++){
        			if(result.wo_type[i].code != "OMSC") {
        				content1+= '<option value="' + result.wo_type[i].code + '">'　+　result.wo_type[i].name +　'</option>';
    					content2+= '<li class="m-list-item" data-value="' + result.wo_type[i].code + '">' + result.wo_type[i].name + '</li>';
        				
        			}
					
				}
        		// 装上之后的值
				$("#woType option:eq(0)").after(content1);
				$("#woType").siblings("ul").children(":first").after(content2);
        		
        	}
        	
       	}
  	
   	});
	$("#woAdd_form").modal({backdrop: "static", keyboard: false});
	// 隐藏遮罩
	$(".modal-backdrop").hide();
}

// 按运单获取相关数据
function getData() {
	clearNoExpressNumber();
	
	if($("#expressNumber").val() != "") {
		$.ajax({
			type: "POST",
	      	url: "/BT-LMIS/control/workOrderController/getData.do",
	        dataType: "json",
	        data: {"express_number": $("#expressNumber").val()},
	        success: function (result) {
	        	if(result.result_code == "ERROR") {
	        		alert(result.result_content);
	        		
	        	} else {
	        		$("#carriers").next().prop("disabled", false);
	        		$("#warehouses").next().prop("disabled", false);
	        		$("#transportTime").prop("readonly", false);
	        		$("#platformNumber").prop("readonly", false);
	        		$("#relatedNumber").prop("readonly", false);
	        		$("#stores").next().prop("disabled", false);
	        		$("#orderAmount").prop("readonly", false);
	        		$("#recipient").prop("readonly", false);
	        		$("#phone").prop("readonly", false);
	        		$("#address").prop("readonly", false);
                    $("#mistakeProductCount").prop("readonly", false);
                    $("#mistakeBarcode").prop("readonly", false);
                    $("#supplementExplain").prop("readonly", false);
                    debugger
                  if(result.storebj=="true"){
                      $("#store_bj").prop("checked",true);
					}else{
                      $("#store_bj").prop("checked",false);
					}
                    if(result.tansportbj=="true"){
                        $("#transport_bj").prop("checked",true);
                    }else{
                        $("#transport_bj").prop("checked",false);
                    }
	        		if(result.wo_num != 0) {
	        			$("#expressNumber").parent().prev().children(":first").after(
	        					"<a onclick='ensureWorkOrderDetail(\""+$("#expressNumber").val()+"\");'><label class='no-padding-right red block' style='white-space:nowrap;cursor:pointer;'>当前运单已生成"+result.wo_num+"条工单</label></a>"
	        			
	        			);
	        			
	        		}
					if(!isNull(result.data)) {
						var info= result.data;
						// 物流服务商
						var content1 = "";
		        		var content2 = "";
						var carrier= result.carrier;
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
						var warehouse= result.warehouse;
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
						// 平台订单号
						if(!isNull(info.delivery_order)) {
							$("#platformNumber").val(info.delivery_order);
							
						}
						//
						checkValue($("#platformNumber"));
						// 相关单据号
						if(!isNull(info.epistatic_order)) {
							$("#relatedNumber").val(info.epistatic_order);
							
						}
						//
						checkValue($("#relatedNumber"));
						// 店铺
						content1 = "";
		        		content2 = "";
		        		var store= result.store;
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
						if(!isNull(result.packages)) {
							console.log(result.packages);
							createTable(result.packages);
						}
					} else {
						// 承运商
						var content1 = "";
		        		var content2 = "";
						var carrier= result.carrier;
						for(var i= 0; i< carrier.length; i++){
							content1+= '<option value="' + carrier[i].transport_code + '">'　+　carrier[i].transport_name +　'</option>';
							content2+= '<li class="m-list-item" data-value="' + carrier[i].transport_code + '">' + carrier[i].transport_name + '</li>';
							
						}
						$("#carriers option:eq(0)").after(content1);
						$("#carriers").siblings("ul").children(":first").after(content2);
						// 仓库
						content1 = "";
		        		content2 = "";
						var warehouse= result.warehouse;
						for(var i= 0; i< warehouse.length; i++){
							content1+= '<option value="' + warehouse[i].warehouse_code + '">'　+　warehouse[i].warehouse_name +　'</option>';
							content2+= '<li class="m-list-item" data-value="' + warehouse[i].warehouse_code + '">' + warehouse[i].warehouse_name + '</li>';
							
						}
						$("#warehouses option:eq(0)").after(content1);
						$("#warehouses").siblings("ul").children(":first").after(content2);
						// 店铺
						content1 = "";
		        		content2 = "";
		        		var store= result.store;
		        		for(var i= 0; i< store.length; i++){
							content1+= '<option value="' + store[i].store_code + '">'　+　store[i].store_name +　'</option>';
							content2+= '<li class="m-list-item" data-value="' + store[i].store_code + '">' + store[i].store_name + '</li>';
							
						}
						$("#stores option:eq(0)").after(content1);
						$("#stores").siblings("ul").children(":first").after(content2);
						
					}
	        		
	        	}
	        	
	       	}
	  	
	   	});
		
	}
	
}

// ENTER减调用方法
function getDataByEnter(event) {
	if(event.keyCode == 13){
		getData();
		
	}
	
}

// 新增工单
function add(formId) {
	debugger
   if(!checkValues(formId)) {
		return;
		
	}
	if(!confirm("是否提交数据新增工单？")) {
		return;
		
	}
	var order_id= [];
  	$("input[name='packageIds']:checked").each(function(){
  		// 将选中的值添加到数组priv_ids中
  		order_id.push($.trim($(this).val()));
  	});
	
	loadingStyle();
	$.ajax({
		type: "POST",
      	url: "/BT-LMIS/control/workOrderController/add.do?packageId="+order_id,
        dataType: "json",
        data: $("#" + formId).serialize(),
        success: function (result) {
        	alert(result.result_content);
        	// 解除旋转
        	cancelLoadingStyle();
    		//
        	if(result.result_code == "SUCCESS") {
        		// 隐藏弹窗
        		$("#woAdd_form").modal("hide");
        		//
        		if(!$("#realTime").prop("disabled") && $("#realTime").prop("checked")) {
        			toProcessForm2("process", result.woId, "div_view", "wo_process_form");
        			
        		} else {
        			// 初始化弹窗
            		initalAddForm();
        			// 刷新页面
            		find();
        			
        		}
        		
        	}
        	
        },
        error: function() {
        	// 解除旋转
        	cancelLoadingStyle();
        	
        }
        
	});
	
}

// 切换组别对应员工账号
// flag-0不排除自己 flag-1排除自己
function shiftGroup(groupId, empId, flag) {
	// 去除之前的值
	$("#" +　empId).children(":first").siblings().remove();
	$("#" +　empId).siblings("ul").children(":first").siblings().remove();
	var group= $("#" + groupId).val();
	if(group == "") {
		// 请选择
		$("#" +　empId).next().attr("disabled", "disabled");
		
	} else {
		$.ajax({
			type: "POST",
	        url: "/BT-LMIS/control/workOrderController/getEmployeeInGroup.do",
	        dataType: "json",
	        data: {"group": group, "flag": flag },
	        success: function(result) {
	        	if(result.result_code == "ERROR") {
	        		alert(result.result_content);
	        		
	        	} else {
	        		var employee= result.employee;
	        		var content1 = "";
	        		var content2 = "";
	        		for(var i= 0; i< employee.length; i++){
						content1+= '<option value="' + employee[i].id + '">'　+　employee[i].name +　'</option>';
						content2+= '<li class="m-list-item" data-value="' + employee[i].id + '">' + employee[i].name + '</li>';
						
					}
	        		// 装上之后的值
					$("#" + empId + " option:eq(0)").after(content1);
					$("#" + empId).siblings("ul").children(":first").after(content2);
					$("#" + empId).next().attr("disabled", false);
	        		
	        	}
	        	
	        }
	        
		});
		
	}
	// 初始化显示内容
	initializeSelect(empId);
	// 清空工单类型对应级别校验信息
	initCheckInfo("#" + empId);
	
}

// 初始化挂起表单
function initalPauseForm() {
	$("#pause_reason").val("");
	//
//	$("input[name='ckb']:checked").prop("checked", false);
	// 校验信息还原
	initCheckInfo(".pauseCheckInfo");
	
}

// 对工单状态进行操作的统一方法入口，处理界面与查询界面通用
function operateStatus(batch_flag, pageNum, operate) {
	var priv_ids= [];
	if(batch_flag == "process") {
		// 工单处理界面单独操作
		priv_ids.push($.trim($("#wo_id").val()));
		
	} else if(batch_flag == "query") {
		// 工单查询界面批量操作
		if($("input[name='ckb']:checked").length == 0){
			alert("请选择需要操作的工单！");
			return;
			
		}
		var error= false;
		$("input[name='ckb']:checked").each(function(i) {
			var index= i + 1;
			var allocStatus= "";
			var processStatus= "";
			// 处理状态
			processStatus= $.trim($(this).parent().siblings().eq(10).text());
			if(pageNum == "0") {
				// 工单管理页面
				// 分配状态
				allocStatus= $.trim($(this).parent().siblings().eq(11).text());
				
				
			}
			if(processStatus == "已取消") {
				alert("[第" + index + "项]已取消工单无法进行操作！");
				$(this).prop("checked", false);
				error= true;
				alert("异常选项已取消！");
				return false;
				
			}
			if(processStatus == "已处理") {
				alert("[第" + index + "项]已处理工单无法进行操作！");
				$(this).prop("checked", false);
				error= true;
				alert("异常选项已取消！");
				return false;
				
			}
			if(operate == "CANCEL_ALLOC" && allocStatus != "已分配") {
				alert("[第" + index + "项]工单分配状态非已分配，无法取消分配！");
				$(this).prop("checked", false);
				error= true;
				alert("异常选项已取消！");
				return false;
				
			}
			if(operate == "PAUSE" && processStatus != "处理中") {
				alert("[第" + index + "项]工单处理状态非处理中，无法挂起！");
				$(this).prop("checked", false);
				error= true;
				alert("异常选项已取消！");
				return false;
				
			}
			if(operate == "RECOVER" && processStatus != "挂起待确认") {
				alert("[第" + index + "项]工单处理状态非挂起待确认，无法取消挂起！");
				$(this).prop("checked", false);
				error= true;
				alert("异常选项已取消！");
				return false;
				
			}
			priv_ids.push($.trim($(this).val()));
			
		});
		if(error) {
			$("#checkAll").prop("checked", false);
			
		}
		if($("input[name='ckb']:checked").length == 0){
			alert("请选择需要操作的工单！");
			return;
			
		}
		
	}
	if(!confirm("是否确定对所选工单进行指定操作？")) {
		return;
		
	}
	if(operate == "PAUSE" && !checkValues("pauseForm")) {
		return;
		
	}
	loadingStyle();
	$.ajax({
		type: "POST",
      	url: "/BT-LMIS/control/workOrderController/shiftStatus.do?operate=" + operate,
        dataType: "json",
        data: "privIds=" + priv_ids + "&remark=" + $("#pause_reason").val() + "&batch_flag=" + batch_flag,
        success: function (result) {
	   		alert(result.result_content);
	   		// 解除旋转
	   		cancelLoadingStyle();
	   		if(operate == "PAUSE") {
	   			// 初始化弹窗
	   			initalPauseForm();
	   			// 隐藏弹窗
        		$("#woPause_form").modal("hide");
    			// 隐藏遮罩
    			$(".modal-backdrop").hide();
	   			
	   		}
	   		if(operate == "FIN") {
	   			$("#" + $("#wt").val() + "_form").modal("hide");
    			// 隐藏遮罩
    			$(".modal-backdrop").hide();
	   		}
	   		if(batch_flag == "process") {
				$("#btn_undo").click();
   				
   			} else if(batch_flag == "query") {
   				find();
   				
   			}
    	   		
        },
        error: function() {
        	// 解除旋转
        	cancelLoadingStyle();
        	
        }
	
	});
	
}

// 进入处理界面（外）
function toProcessForm(type, tr) {
	toProcessForm2(type, tr.children(":first").children(":first").val(), "div_view", "wo_process_form");
	
}

// 进入查询页面（里）
function toProcessForm2(type, wo_id, loadDivId, return_page) {
	var flag= true;
	loadingStyle();
	if(type == "process") {
		$.ajax({
			type: "POST",
	      	url: "/BT-LMIS/control/workOrderController/startWorkOrder.do",
	        dataType: "json",
	        data: {"wo_id": wo_id },
	        async: false,
	        success: function (result) {
	        	if(!(result.result_code == "SUCCESS")) {
	        		// 解除旋转
	        		cancelLoadingStyle();
	        		// 显示异常
	        		alert(result.result_content);
	        		// 刷新页面
	        		find();
	        		// 标志更新
	        		flag= false;
	        		
	        	}
	        	
	        }
	        
		})
		
	}
	if(flag) {
		openIdDiv(loadDivId,
				"/BT-LMIS/control/workOrderController/toProcessForm.do?type="
				+ type
				+ "&wo_id="
				+ wo_id
				+ "&return_page="
				+ return_page
				+ "&uuid="
				+ $("#uuid").val());
		
	}
	
}

// 返回查询界面
function back(type, uuid) {
	var url="";
	debugger
	if(type == "query") {
//		url="/BT-LMIS/control/workOrderController/back.do?uuid="+uuid;
		url="/BT-LMIS/control/workOrderController2/back.do?uuid="+uuid;
		
	} else if(type == "process") {
		url="/BT-LMIS/control/workOrderManagementController/back.do?uuid="+uuid;
	
	}
	openDiv(url);
	
}

// 展现OMS索赔明细
function showOMSClaimDetail(claimDetail) {
	// 刷新列表
	$("#claim_detail_OMSC").children().remove();
	var tr= "";
	for(var i= 0; i< claimDetail.length; i++) {
		tr+=
			'<tr id= "' + claimDetail[i].claimDetailId + '" >'
			+ '<td class= "td_cs" title= "' + claimDetail[i].sku_number + '" >' +　claimDetail[i].sku_number + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].sku_name + '" >' +　claimDetail[i].sku_name + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].sku_num + '" >' +　claimDetail[i].sku_num + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].platform_price + '" >' +　claimDetail[i].platform_price + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].platform_price_all + '" >' +　claimDetail[i].platform_price_all + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].claim_num_applied + '" >' +　claimDetail[i].claim_num_applied + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].claim_price_applied + '" >' +　claimDetail[i].claim_price_applied + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].claim_value_applied + '" >' +　claimDetail[i].claim_value_applied + '</td>'
			+ '</tr>';
		
	}
	$("#claim_detail_OMSC").append(tr);
	
}

//展现非OMS索赔明细
function showNOMSClaimDetail(claimDetail, claim_status) {
	// 刷新列表
	$("#claim_detail_NOMSC").children().remove();
	var tr= "";
	for(var i= 0; i< claimDetail.length; i++) {
		tr+=
			'<tr id= "' + claimDetail[i].claimDetailId + '" >'
			+ '<td class= "td_cs" title= "' + claimDetail[i].sku_number + '" >' +　claimDetail[i].sku_number + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].sku_name + '" >' +　claimDetail[i].sku_name + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].sku_num + '" >' +　claimDetail[i].sku_num + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].platform_price + '" >' +　0 + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].platform_price_all + '" >' + 0 + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].claim_num_applied + '" >' +　claimDetail[i].claim_num_applied + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].claim_price_applied + '" >' +　claimDetail[i].claim_price_applied + '</td>'
			+ '<td class= "td_cs" title= "' + claimDetail[i].claim_value_applied + '" >' +　claimDetail[i].claim_value_applied + '</td>';
		if($("#judgeType").val() == "process" && $.trim($("#woProcessStatus_woDisplay").text()) == "处理中" && claim_status == "-1" && $("#waybillGenerated").children().length==0) {
			tr += '<td class= "td_cs" style= "white-space: nowrap;"><button class= "btn btn-xs btn-info" onclick= "claimDetailFormSe(this);" >编辑</button> | <button class= "btn btn-xs btn-pink" onclick= "delClaimDetail(this);" >删除</button></td>';
			
		}
		tr+= '</tr>';
		
	}
	$("#claim_detail_NOMSC").append(tr);
	
}

// 索赔特殊显示
function showClaim(wo_type, type, wo_process_status_display, claim_status, oms_retrun_flag, claim_detail) {
	//
	if(type == "process" && $("#waybillGenerated").children().length==0) {
		if(wo_process_status_display == "处理中" && $("#NOMSCadd").css("display") == "none") {
			$("#NOMSCadd").show();
			
		}
		if(wo_process_status_display == "处理中" && claim_status == "-1") {
			$("#function_buttion").show();
			// 按钮显示
			$("#save_" + wo_type).show();
			$("#process_" + wo_type).show();
				
		}
		if(wo_process_status_display == "已处理" && claim_status == "0" && ((wo_type == "NOMSC") || (wo_type == "OMSC" && oms_retrun_flag == "1"))) {
			$("#function_buttion").show();
			// 按钮显示
			$("#success_" + wo_type).show();
			$("#failure_" + wo_type).show();
				
		}
		
	} else {
		$("#function_buttion").hide();
		$("#save_" + wo_type).hide();
		$("#process_" + wo_type).hide();
		$("#success_" + wo_type).hide();
		$("#failure_" + wo_type).hide();
		
	}
	if($("#waybillGenerated").children().length!=0 && $("#NOMSCadd").css("display") != "none") {
		$("#NOMSCadd").hide();
		
	}
	// 索赔明细
	if(wo_type == "NOMSC") {
		showNOMSClaimDetail(claim_detail, claim_status);
		
	} else if(wo_type == "OMSC") {
		showOMSClaimDetail(claim_detail);
		
	}
	
}

//赔偿责任方切换
function shiftPayFlag(woType, code) {
	if(code == "logistics_department_claim_flag" || code == "transport_vendor_claim_flag") {
		var id;
		if(code == "logistics_department_claim_flag") {
			id= "logistics_department_claim_amount" + "_" + woType;
			
		} else if(code == "transport_vendor_claim_flag") {
			id= "transport_vendor_claim_amount" + "_" + woType;
			
		}
		if(isChecked(code + "_" + woType)) {
			if(($("#judgeType").val() == "process" && $("#woProcessStatus_woDisplay").text() == "处理中" && $("#waybillGenerated").children().length==0)) {
				$("#" + id).prop("disabled", false);
				
			}
			
		} else {
			$("#" + id).val('');
			$("#" + id).prop("disabled", true);
				
		}
		
	}
	
}

//非OMS索赔处理切换索赔类型
function shiftClaimType(woType) {
	var id= "#claim_reason_" + woType;
	// 清除选项
	$(id + " option:eq(0)").siblings().remove();
	// 获取索赔类型
	var claim_type= $("#claim_type_" + woType).val();
	if(claim_type != "") {
		var option= "";
		if(claim_type == "1") {
			// 遗失
			option= "<option value= '1'>无路由信息</option><option value= '2'>路由停滞24小时</option><option value= '3'>买家未收到货（路由显示签收）</option><option value= '4'>物流反馈丢件</option><option value= '5'>开箱少/无货</option>";
			
		} else if(claim_type == "2") {
			// 破损
			option= "<option value= '1'>物流反馈破损</option><option value= '2'>开箱后反馈破损</option>";
			
		} else if(claim_type == "3") {
			// 错发
			option= "<option value= '1'>开箱后反馈错发</option>";
			
		} else if(claim_type == "4") {
			// 补偿 
			option= "<option value= '1'>补偿金</option>";
			
		}
		$(id + " option:eq(0)").after(option);
		//
		if(($("#judgeType").val() == "process" && $("#woProcessStatus_woDisplay").text() == "处理中" && $("#waybillGenerated").children().length==0)) {
			$(id).prop("disabled", false);
			
		}
		
	} else {
		//
		$(id).prop("disabled", true);
		
	}
	
}

// 加载操作数据
function loadingOperation(wo_id, wo_type) {
	loadingStyle();
	$.ajax({
		type: "POST",
      	url: "/BT-LMIS/control/workOrderController/toOperateForm.do",
        dataType: "json",
        data: {"wo_id":wo_id, "wo_type":wo_type },
        success: function (result) {
            $("#remark_new_"+wo_type).empty();
            var option = $("<option>").val("").text("请选择");
            $("#remark_new_"+wo_type).append(option);
            for(var s=0;s<result.remark_new.length;s++){
                var option = $("<option>").val(result.remark_new[s]).text(result.remark_new[s]);
                $("#remark_new_"+wo_type).append(option);
			}

            var claim_status= "-1";
        	var oms_retrun_flag= "";
        	var values= result.values;
        	for(var i= 0; i< values.length; i++) {
        		var value= values[i];
        		var code= value.code;
        		var title= value.title;
        		if(code == "claim_start_point") {
    				code= "days";
    				title= "预警天数";
    				
    			}
        		$("#"+ code + "_label_" + wo_type).text(title + " :");
        		// 控件填值
        		// 控件id
        		var id;
        		if(code == "fileName") {
        			id= "#fileName_common";
        			
        		} else {
        			id= "#"+ code + "_" + wo_type;
        			
        		}
        		// 控件类型
        		var control_type= value.control_type;
        		if(control_type == "TEXT") {
        			var val= value.value;
        			if(code == "claim_status") {
        				if(val != "")claim_status= val;
        				if(val == "0")val= "暂作理赔";
    					if(val == "1")val= "索赔成功";
        				if(val == "2")val= "索赔失败";
        				
        			}
        			if(code == "days") {
        				if(val != "")val= parseInt((new Date().getTime() - new Date(val)) / (1000 * 60 * 60 * 24));
        				
        			}
        			if(code == "oms_retrun_flag") {
        				oms_retrun_flag= val;
        				
        			} else {
        				$(id).text(val);
        				
        			}
        			
        		} else if(control_type == "INPUT" || control_type == "CALENDAR") {
        			$(id).val(value.value);
        			if(code == "fileName") {
        				getFileList();
        				
         			}
        			if($("#waybillGenerated").children().length == 0) {
        				if(!($("#judgeType").val() == "process" && $("#woProcessStatus_woDisplay").text() == "处理中") && !$(id).prop("disabled")) {
        					$(id).prop("disabled", true);
        					
        				}
        				if($("#judgeType").val() == "process" && $("#woProcessStatus_woDisplay").text() == "处理中" && $(id).prop("disabled")) {
        					$(id).prop("disabled", false);
        					
        				}
        				
        			} else {
        				$(id).prop("disabled", true);
        				
        			}
     			
        		} else if(control_type == "SWITCH") {
        			if(value.value == "true") {
        				$(id).prop("checked", true);
        				
        			} else {
        				$(id).prop("checked", false);
        				
        			}
    				shiftPayFlag(wo_type, code);
    				if($("#waybillGenerated").children().length == 0) {
        				if(!($("#judgeType").val() == "process" && $("#woProcessStatus_woDisplay").text() == "处理中") && !$(id).prop("disabled")) {
        					$(id).prop("disabled", true);
        					
        				}
        				if($("#judgeType").val() == "process" && $("#woProcessStatus_woDisplay").text() == "处理中" && $(id).prop("disabled")) {
        					$(id).prop("disabled", false);
        					
        				}
        				
        			} else {
        				$(id).prop("disabled", true);
        				
        			}
        				
        		} else if(control_type == "SELECT") {
        			var node;
        			if(value.value == "") {node= 0; } else {node= value.value; }
        			$(id+ " option:eq(" +　node + ")").prop("selected", "selected");
        			if(code == "claim_type") {
    					shiftClaimType(wo_type);
    					
    				}
        			if($("#waybillGenerated").children().length == 0) {
        				if(!($("#judgeType").val() == "process" && $("#woProcessStatus_woDisplay").text() == "处理中") && !$(id).prop("disabled")) {
        					$(id).prop("disabled", true);
        					
        				}
        				if($("#judgeType").val() == "process" && $("#woProcessStatus_woDisplay").text() == "处理中" && $(id).prop("disabled")) {
        					$(id).prop("disabled", false);
        					
        				}
        				
        			} else {
        				$(id).prop("disabled", true);
        				
        			}
        			
        		} else if(control_type == "A") {
        			var va= value.value;
        			$(id).text(va);
        			$(id).prop("href", va);
        			$(id).prop("download", va.substring(va.lastIndexOf("/"), va.lastIndexOf(".")));
        			
        		}
        		
        	}
        	if(wo_type == "NOMSC" || wo_type == "OMSC") {
        		showClaim(wo_type, $("#judgeType").val(), $("#woProcessStatus_woDisplay").text(), claim_status, oms_retrun_flag, result.claimDetail);
        		
        	}
        	// 解除旋转
        	cancelLoadingStyle();
        	
        }
        
	});
	
}

function toOperateForm(wo_id, wo_type) {
	// 加载附件列表页面
	if(wo_type == "NOMSC" || wo_type == "OMSC") {
		$(wo_type + "_UPLOAD").load("../upload.jsp");
		
	} else {
		$("COMMON_UPLOAD").load("../upload.jsp");
		
	}
	// 加载数据
	loadingOperation(wo_id, wo_type);
	/*$("#" + wo_type + "_form").draggable({   
	    handle: ".modal-header",   
	    cursor: 'move',   
	    refreshPositions: false  
	}); */ 
	// 弹窗
	$("#" + wo_type + "_form").modal({backdrop: "static", keyboard: false});
	
}

// 附件列表加载
function getFileList(){
	var names= $("#fileName_common").val().split("_");
	var htm= "";
	for(var k= (names.length - 1);k > 0; k--) {
		var col;
		if(k % 2 == 0) {
			col='#F0F8FF';
				
		} else {
			col='#FFFFFF';
			
		}
		htm+= "<div style='width:100%;' id=file_" + k + "><div style='float:left;width:80%;border-bottom: 1px dashed gray;background-color:" + col + ";'>";
		if(names[k].indexOf("jpg") >= 0 || names[k].indexOf("png") >= 0 || names[k].indexOf("jpeg") >= 0) {
			htm+= "<a href='" + FileDown + names[k].split("#")[0] + "' target='_blank'><img alt='" + names[k].split("#")[1] + "' title=" + names[k].split("#")[1] + " src='" + FileDown + names[k].split("#")[0] + "' width='70' height='50'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + names[k].split("#")[1] + names[k].split("#")[2] + "</a>"
			
    	} else {
    		htm+= "<a href='" + FileDown + names[k].split("#")[0] + "?n=" + names[k].split("#")[1] + "' target='_blank'><img alt='" + names[k].split("#")[1] + "' title=" + names[k].split("#")[1] + " src='" + root + "uploadify/file.png' width='70' height='50'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + names[k].split("#")[1] + names[k].split("#")[2] + "</a>"
    	
    	}
		htm+= "</div><div style='float:right;text-align:right;width:20%;height:51px;border-bottom: 1px dashed gray;line-height:50px;background-color:" + col + ";'>"
		if($("#judgeType").val() == undefined || ($("#judgeType").val() == "process" && $("#woProcessStatus_woDisplay").text() == "处理中" && $("#waybillGenerated").children().length==0)) {
			htm+= "<a href='javascript:;' style='color:red;' onclick='removeFile(\"" + k + "\",\"" + names[k] + "\")'>删除</a>";
			
		}
		htm+= "</div></div>";
		
	}
	$("ims").html(htm);
	
}

function removeFile(id, name){
	$("#file_"+id).empty();
	var nameS=$("#fileName_common").val();
	var names=nameS.split("_");
	var newName="";
	for(var s = 1; s < names.length; s++){
		if(names[s] != name) {
			newName= newName+"_"+names[s];
		
		}
	
	}
	$("#fileName_common").val(newName)
	
}

function sort(control, event, sort_by) {
	// 排序字段
	$("#sort_by").val(sort_by);
	// 获取点击位置相对值
	var position=mouseCoords(event).y-control.offset().top;
	// 元素高度
	var height=control.height();
	// 元素高度中间值
	var median=height/2;
	// 分上下段
	if(position>0 && position<=median) {
		$("#sort").val("ASC");
		
	} else if(position>median && position<=height) {
		$("#sort").val("DESC");
		
	}
	// 查询
	find();
	// 清空缓存
	$("#sort_by").val("");
	$("#sort").val("");
	
}

function isRealTimeProcess(isBySelf) {
	if(isBySelf.prop("checked")) {
		$("#realTime").prop('disabled', false);
		
	} else {
		$("#realTime").prop('disabled', true);
		$('#realTime').prop('checked', false);
		
	}
	
}

function ensureWorkOrderDetailSE(wo_num, express_number) {
	if(wo_num>1 && $("#waybillGenerated").children().length==0) {
		ensureWorkOrderDetail(express_number);
		
	}
	
}

function ensureWorkOrderDetail(expressNumber) {
	$.ajax({
		type:"POST",
      	url:"/BT-LMIS/control/workOrderController/ensureWorkOrderDetail.do",
        dataType:"json",
        data:{"expressNumber":expressNumber },
        success:function (result) {
        	if(result.result_code="SUCCESS") {
        		// 刷新列表
        		$("#waybillGenerated").children().remove();
        		var tr="";
        		for(var i=0; i<result.wo.length; i++) {
        			tr+="<tr><td title='"+result.wo[i].wo_no+"'><a style='cursor:pointer;' onclick='focusWorkOrder($(this));getWorkOrderInfo(\""+result.wo[i].id+"\");'>"+result.wo[i].wo_no+"</a></td></tr>";
        		}
        		$("#waybillGenerated").append(tr);
        		//
        		$("#woWaybillGenerated_form").modal({backdrop: "static", keyboard: false});
        		$(".modal-backdrop").hide();
        	} else {
        		alert(result.result_content);
        		
        	}
        	
        }
        
	})
	
}

function focusWorkOrder(a) {
	a.parent().parent().siblings().each(function(){
		$(this).children(":first").children(":first").removeClass("red");
		
	})
	if(!a.hasClass("red")) {
		a.addClass("red");
		
	}
	
}

function getWorkOrderInfo(wo_id) {
	$("#workOrderInfo").children().remove();
	toProcessForm2("query", wo_id, "workOrderInfo", "wo_process_info");
	
}

function cleanWaybillGenerated() {
	$("#waybillGenerated").children().remove();
	$("#workOrderInfo").children().remove();
	
}

var str = "选择,条形码,SKU条码,商品名称,数量,扩展属性".split(",");
function createTable(data){
    var table = $("#tableTest");
    var tbody = document.createElement("tbody");
    table.append(tbody);
    var tr = tbody.insertRow (0);
    
    for (var i = 0; i < str.length; i++) {
    	var th = document.createElement("th");
	    th.innerHTML = str[i];
        tr.appendChild (th);
    }
    for (var i = 0; i < data.length; i++){
        var tr = tbody.insertRow (tbody.rows.length);
	        var obj = data[i];
	        for (var j = 0; j < str.length; j++){
	            var td = tr.insertCell (tr.cells.length);
	            if(j==0){
	            	td.innerHTML = "<input name='packageIds' type='checkBox' value="+obj.id+"/>";
	            }
	            if(j==1){
	            	td.innerHTML = obj.barcode;
	            }
	            if(j==2){
	            	if(obj.skuNumber==undefined){
	            		td.innerHTML = "";
	            	}else{
	            	td.innerHTML = obj.skuNumber;
	            	}
	            }
	            if(j==3){
	            	td.innerHTML = obj.itemName;
	            }
	            if(j==4){
	            	td.innerHTML = obj.qty;
	            	
	            }
	            if(j==5){
	            	if(obj.extendPro==undefined){
	            		td.innerHTML = "";
	            	}else{
	            	td.innerHTML = obj.extendPro;
	            	}
	            }
	            
	        }
    }
}