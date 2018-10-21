
// 工单处理平台页面方法
$(function(){
	// 查询条件
	// 工单类型联动
	var param={'levelId': 'wo_level', 'exceptionId': 'exception_type', 'check_flag': '0' };
	if(!isNull($("#wo_level_temp").val())) {
		param["wo_level"]=$("#wo_level_temp").val();
		
	}
	if(!isNull($("#exception_type_temp").val())) {
		param["exception_type"]=$("#exception_type_temp").val();
		
	}
	shiftType('wo_type', param);
	// 新增界面
	$("#woLevel").next().attr("disabled", "disabled");
	$("#exceptionType").next().attr("disabled", "disabled");
	$("#carriers").next().attr("disabled", "disabled");
	$("#warehouses").next().attr("disabled", "disabled");
	$("#stores").next().attr("disabled", "disabled");
	//
	monitoringStatus();
	
});

// 轮询工单处理状态
var pollingFlag= false;
function monitoringStatus() {
	$.ajax({
		url: root + "/control/workOrderManagementController/monitoringStatus.do",
		type: "post",
		cache: false,
		data: {},
		dataType: "json",
		success: function(result) {
			statusChange(pollingFlag, result, "");
			statusChange(pollingFlag, result, "NEW");
			statusChange(pollingFlag, result, "PRO");
			statusChange(pollingFlag, result, "PAU");
			statusChange(pollingFlag, result, "CCD");
			statusChange(pollingFlag, result, "FIN");
			if(!pollingFlag) {
				pollingFlag= true;
				
			}
			if($("#pageTitle").text() == "工单处理平台") {
				setTimeout("monitoringStatus()", 20000);
				
			}
			
		},
		error: function(result) {
			alert("监控异常！");
			
		}
	
	});
	
}

// 工单处理状态显示切换
function statusChange(flag, result, status) {
	var cur;
	if(status in result) {
		cur= parseInt(result[status]);
		
	} else {
		cur= 0;
		
	}
	// 获取页面当前数值
	var currentId= "#current_" + status;
	var sub=  cur- parseInt($(currentId).text());
	if(sub != 0) {
		// 当前值变化，就要替换
		$(currentId).text(cur);
		
	}
	if(flag) {
		var newsId= "#news_" + status;
		if(sub > 0) {
			// 新增值替换
			// 获取新增值
			var news= parseInt($(newsId).text().substring(1));
			// 非初始值则显示新增
			//
			var num= 0;
			if(news == 0) {
				// 之前无新增
				num= sub;
				
			} else {
				// 之前有新增
				num= news + sub;
				
			}
			if(num >= 99) {
				num= 99;
				
			}
			$(newsId).text("+" + num);
			if($(newsId).css("display") == "none") {
				$(newsId).show();
				
			}
			
		} else {
			if($("#triggerMode_" + status).val() != "") {
				$(newsId).text("+" + 0);
				if($(newsId).css("display") != "none") {
					$(newsId).hide();
					
				}
				$("#triggerMode_" + status).val("");
				
			}
			
		}
		
	}
	
}

// 列表查询
function find() {
	loadingStyle();
	openIdDiv("wo_management_platform_list",
			"/BT-LMIS/control/workOrderManagementController/query.do?loadingType=DATA&isCollapse="
			+ $(".widget-box").hasClass("collapsed")
			+ "&isQuery=true&"
			+ $("form").serialize()
			+ "&startRow="
			+ $("#startRow").val()
			+ "&endRow="
			+ $("#startRow").val()
			+ "&page="
			+ $("#pageIndex").val()
			+ "&pageSize="
			+ $("#pageSize").val()
			
	);
	
}

function download() {
	window.location.href="/BT-LMIS/control/workOrderManagementController/exportWorkOrder.do?"
		+ $("form").serialize();
}

// 分页跳转
function pageJump() {
	find();
	
}

// 切换不同处理状态页面
function shiftStatus(wo_process_status, button) {
	button.removeClass("btn-light");
	button.addClass("btn-primary");
	$("#wo_process_status").val(wo_process_status);
	button.siblings().each(function(){
		if($(this).hasClass("btn-primary")) {
			$(this).removeClass("btn-primary");
			$(this).addClass("btn-light");
			
		}
		
	});
	//
	$("#triggerMode_"+wo_process_status).val("click");
	//
	find();
	
}

// 刷新查询条件列表
function refreshCondition() {
	// 创建时间起始
	$("#createTime_from").val("");
	// 创建时间结束
	$("#createTime_to").val("");
	// 创建者
	$("#create_by").val("");
	// 工单号
	$("#wo_no").val("");
	//　工单类型
	initializeSelect("wo_type");
	// 工单类型切换
	shiftType("wo_type", {"levelId": "wo_level", "exceptionId": "exception_type", "check_flag": "0" });
	// 物流服务商
	initializeSelect("carrier");
	// 快递单号
	$("#express_number").val("");
	// 仓库
	initializeSelect("warehouse");
	// 出库时间起始
	$("#transportTime_from").val("");
	// 出库时间结束
	$("#transportTime_to").val("");
	// 店铺
	initializeSelect("store");
	// 平台订单号
	$("#platform_number").val("");
	// 相关单据号
	$("#related_number").val("");
	// 分配者
	$("#allocated_by").val("");
	// 索赔状态
	initializeSelect("claim_status");
	// 索赔编码
	$("#claim_number").val("");
	// 索赔分类
	initializeSelect("claim_type");
	// 后续店铺介入
	initializeSelect("nextToStoreOrNot");
	find();
	
}

// 切换级别
function shiftLevel(levelId, param) {
	// 工单级别节点
	var woLevel= $("#" + levelId);
	checkValue(woLevel);
	// 工单级别值
	var wl= woLevel.val();
	if(wl != "" && wl != $("#" + param["wlbId"]).val()) {
		$("#" + param["reasonDiveId"]).show();
		// 级别带动组别变化
		$.ajax({
			type: "POST",
	        url: "/BT-LMIS/control/workOrderManagementController/shiftGroups.do",
	        dataType: "json",
	        data: {"work_level": wl, "wo_id": $("#wo_id").val() },
	        success: function(result) {
	        	if(result.result_code == "ERROR") {
	        		alert(result.result_content);
	        		
	        	} else {
	        		var groups= result.groups;
	        		// 清除原有值
	        		initializeSelect(param["groupId"]);
	        		$("#" + param["groupId"]).children(":first").siblings().remove();
	        		$("#" + param["groupId"]).siblings("ul").children(":first").siblings().remove();
	        		// 添加新值
	        		var content1 = "";
	            	var content2 = "";
	            	for(var i= 0; i< groups.length; i++){
	    				content1+= '<option value="' + groups[i].id + '" >'+groups[i].group_name+'</option>';
	    				content2+= '<li class="m-list-item" data-value="' + groups[i].id + '">' + groups[i].group_name + '</li>';
	    				
	    			}
	        		// 装上之后的值
	    			$("#" + param["groupId"] + " option:eq(0)").after(content1);
	    			$("#" + param["groupId"]).siblings("ul").children(":first").after(content2);
	    			// 组切换人
	    			shiftGroup(param["groupId"], param["accountId"]);
	        		
	        	}
	        	
	        }
	        
		});
		
	} else {
		$("#" + param["reasonDiveId"]).hide();
		// 工单级别还原到初始值时，也需要还原到初始校验状态
		if(wl == $("#" + param["wlbId"]).val()) {
			woLevel.parent().parent().next().empty();
			
		}
		// 初始化升降级原因显示内容
		initializeSelect(param["reasonId"]);
		// 清空升降级原因校验信息
		initCheckInfo("#" + param["reasonId"]);
		// 初始化状态
		// 升降级原因校验信息
		var reasoncheckInfo= $("#" + reasonId).parent().parent().next();
		if(reasoncheckInfo.attr("validation") == "1") {
			reasoncheckInfo.attr("validation", "0");
			
		}
		
	}
	
}
// 切换原因
function shiftReason(exceptionId, exceptionBeforeId) {
	var exception= $("#" + exceptionId)
	checkValue(exception);
	if(exception.val() == $("#" + exceptionBeforeId).val()) {
		exception.parent().parent().next().empty();
		
	}
	
}

// 重写提交校验
function overWriteCheckValues() {
	if($("#wl_before").val() == $("#wl").val() && $("#et_before").val() == $("#et").find("option:selected").text() && $("#groups").val() == "" &&  $("#wo_type_select_id").val()==$("#wt").val()) {
		alert("无异动需保存");
		return false;
		
	}
	var flag= 0;
	// 工单级别校验
	var node= $("#wl");
	if(node.parent().parent().next().attr("validation") == 0 && !checkValue(node)) {
		flag++;
		
	}
	// 异常类型校验
	node= $("#et");
	if(node.parent().parent().next().attr("validation") == 0 && !checkValue(node)) {
		flag++;
		
	}
	// 升降级原因校验
	if($("#wl").val() != $("#wl_before").val()) {
		node= $("#ar");
		if(node.parent().parent().next().attr("validation") == 0 && !checkValue(node)) {
			flag++;
			
		}
		
	}
	// 员工校验
	if($("#groups").val() != "") {
		node= $("#account");
		if(node.parent().parent().next().attr("validation") == 0 && !checkValue(node)) {
			flag++;
			
		}
		
	}
	if(flag == 0) {
		return true;
		
	} else {
		return false;
		
	}
	
}

//异动工单
function updateWorkOrder() {
	if(!confirm("是否确定异动工单？")) {
		return;
		
	}
	if(!overWriteCheckValues()) {
		return;
		
	}
	// 提交异动数据
	loadingStyle();
	var flag= true;
	if(($("#wl_before").val() != $("#wl").val() && $("#groups").val() == "")|| $("#wo_type_select_id").val()!="") {
		// 工单级别异动且不转发工单，则需校验工单级别移动后当前用户是否还能处理
		$.ajax({
			type: "POST",
			url: "/BT-LMIS/control/workOrderManagementController/judgePower.do",
	        dataType: "json",
	        data: {"wo_id": $("#wo_id").val(), "wo_level": $("#wl").val(),"wo_type":$("#wo_type_select_id").val() },
	        success: function(result) {
	        	if(result.result_code == "ERROR" || result.result_code == "FAILURE") {
	        		alert(result.result_content);
	        		// 解除旋转
		        	cancelLoadingStyle();
	        		flag= false;
	        		
	        	}
	        	
        	},
	        error: function() {
	        	// 解除旋转
	        	cancelLoadingStyle();
	        	flag= false;
	        	
	        }
        	
        });	
		
	}
	if(flag) {
		$.ajax({
			type: "POST",
			url: "/BT-LMIS/control/workOrderManagementController/updateWorkOrder.do",
	        dataType: "json",
	        data: {
	        	"event": "UPDATE",
	        	"wo_id": $("#wo_id").val(),
	        	"wo_level": $("#wl").val(),
	        	"wo_level_display": $("#wl").find("option:selected").text(),
	        	"exception_type":$("#et").find("option:selected").text(),
	        	"level_alter_reason" : $("#ar").val(),
	        	"account": $("#account").val(),
	        	"wo_type": $("#wo_type_select_id").val(),
	        	"wo_type_display":$("#wo_type_select_id").find("option:selected").text()
	        },
	        success: function(result) {
        		alert(result.result_content);
	        	if(result.result_code == "SUCCESS") {
	        		// 隐藏当前弹窗
	        		$("#alter_form").modal("hide");
	        		// 返回查询页面
	        		$("#btn_undo").click();
	        		
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
	
}

// 加载索赔明细编辑界面
function claimDetailFormSe(id){
	var butt=$(id);
	if(checkOk()==0&&butt.text()!='点击完成'){
		alert('您有索赔明细没有点击完成！！！');
		return;
	}
	var tr_obj=$(id).parent().parent();
	if(butt.text()=='点击完成'){
		var x1=tr_obj.children().eq(5).children().eq(0).val();
		var x2=tr_obj.children().eq(6).children().eq(0).val();	
		if(x1=='')x1=0;
		if(x2=='')x2=0;
		tr_obj.children().eq(5).html(x1);
		tr_obj.children().eq(6).html(x2);
		tr_obj.children().eq(8).children().eq(0).text('编辑');
		return;
	}
	var num= tr_obj.children().eq(5).html();
	var price= tr_obj.children().eq(6).html();
	tr_obj.children().eq(5).html("<input onblur=checkNum(this) onkeyup=\"value=value.replace(/[^\\-?\\d.]/g,\'\')\" class='numberbox' type='text' value=\""+num+"\"/>");
	tr_obj.children().eq(6).html("<input onblur=checkNum(this) onkeyup=\"value=value.replace(/[^\\-?\\d.]/g,\'\')\" type='text' value=\""+price+"\"/>");
	tr_obj.children().eq(8).children().eq(0).text('点击完成');
	
}

function checkNum(input) {
	var tr_obj= $(input).parent().parent();
	var x1= tr_obj.children().eq(5).children().eq(0).val();
	var x2= tr_obj.children().eq(6).children().eq(0).val();
	var xMax= tr_obj.children().eq(2).html();
	if(x1*1> xMax*1) {
		alert('理赔数量不能超过商品数量的最大值！ 已为您选择了最大值');
		tr_obj.children().eq(5).children().eq(0).val(xMax);
		x1=xMax;
		
	}
	tr_obj.children().eq(7).html(x1*x2);
	/*var num=  $('#'+id).children().eq(5).html();
	var price= $('#'+id).children().eq(6).html();
	$('#'+id).children().eq(7).html(num*price);*/
	calValueAll();
	
}

function calValueAll(){
	var add_value= $('#added_value_NOMSC').val();
	if(add_value == '') add_value= 0;
	var other_value= 0;
	for(var i=0;i<$('#claim_detail_NOMSC').children().length;i++){
		other_value= other_value*1 + $('#claim_detail_NOMSC').children().eq(i).children().eq(7).text() * 1;
		
	}
	$('#total_applied_claim_amount_NOMSC').val(other_value*1 + add_value*1);
	
}

function checkOk() {
	var flag= 1;
	var trs_body= $("#claim_detail_NOMSC").children();
	$.each(trs_body, function(index, item) {
		if(trs_body.eq(index).children().eq(8).children().eq(0).text() == "点击完成"){flag=0; return; }
		
	});
	return flag;
	
}

function claimDetailAddForm(waybill) {
	if(checkOk() == 0){
		alert("索赔明细未点击完成");
		return;
		
	}
	$.post("/BT-LMIS/control/workOrderManagementController/getWaybillDetailByWaybill.do?waybill=" + waybill, function(data) {
		var htmlStr= '';
		$.each(data.data, function(index, item) {
			var flag= 0;
			$.each($("#claim_detail_NOMSC").children(), function (index1, item1) {
				if($("#claim_detail_NOMSC").children().eq(index1).children().eq(0).text()==item.sku_number){
					flag=1;
					
				}; 
				
			});
			if(flag == 0) {
				htmlStr+=
					"<tr>"
					+ "<td class='td_ch'><input id='ckb' name='ckb' type='checkbox' value=" + item.id + "></td>"
					+ "<td>" + item.epistatic_order + "</td>"
					+ "<td>" + item.sku_number + "</td>"
					+ "<td>" + item.barcode + "</td>"
					+ "<td>" + item.item_name + "</td>"
					+ "<td>" + item.extend_pro + "</td>"
					+ "<td>" + item.qty + "</td>"
					+ "<td style= 'display: none' >" + item.order_amount + "</td>"
					+　"</tr>";
				
			}
			
		});
		$("#tbody_id").html(htmlStr);
			
	});
	$("#wo_claim_detail_add").modal({backdrop: 'static', keyboard: false});

}

// 保存索赔明细
function addClaimDetail(){
	if($("input[name='ckb']:checked").length==0){
		alert("请选择至少一项");
		return;
		
	}
	if(!confirm("是否提交索赔明细数据？")) {
		return;
		
	}
	var htmlStr= '';
	$.each($("input[name='ckb']:checked"), function() {	
		var p= $(this).parent().parent();
		htmlStr+=
			"<tr>"
			+ "<td class='td_cs' title='" + p.children().eq(2).text() + "'>" + p.children().eq(2).text() + "</td>"
			+ "<td class='td_cs' title='" + p.children().eq(4).text() + "'>" + p.children().eq(4).text() + "</td>"
			+ "<td class='td_cs' title='" + p.children().eq(6).text() + "'>" + p.children().eq(6).text() + "</td>"
			+ "<td class='td_cs' title='0'>" + 0 + "</td>"
			+ "<td class='td_cs' title='0'>" + 0 + "</td>"
			+ "<td class='td_cs' title='0'>" + 0 + "</td>"
			+ "<td class='td_cs' title='0'>" + 0 + "</td>"
			+ "<td class='td_cs' title='0'>" + 0 + "</td>"
			+ "<td class='td_cs'><button class='btn btn-xs btn-info' onclick='claimDetailFormSe(this);'>编辑</button> | <button class='btn btn-xs btn-pink' onclick='delClaimDetail(this);'>删除</button></td>"
			+ "</tr>";
		
		}
	
	);
	$("#claim_detail_NOMSC").html($('#claim_detail_NOMSC').html()+ htmlStr);
	$("#wo_claim_detail_add").modal("hide");

}

// 删除索赔明细
function delClaimDetail(id) {
	if(!confirm("确定删除？")) {
		return;
		
	}
	$(id).parent().parent().remove();
	
}

// 校验索赔
function checkClaim(wo_type) {
	if(wo_type == "NOMSC") {
		if($("#claim_type_NOMSC").val() == "") {
			alert("索赔类型为空");
			return false;
			
		}
		if($("#claim_reason_NOMSC").val() == "") {
			alert("索赔原因为空");
			return false;
			
		}
		if(isNaN($("#added_value_NOMSC").val()) || $("#added_value_NOMSC").val() < 0) {
			alert("附加金额不合法");
			return false;
			
		}
		
	}
	var logistics_department_claim_flag= isChecked("logistics_department_claim_flag_" + wo_type);
	var logistics_department_claim_amount= $("#logistics_department_claim_amount_" + wo_type).val();
	var transport_vendor_claim_flag= isChecked("transport_vendor_claim_flag_" + wo_type);
	var transport_vendor_claim_amount= $("#transport_vendor_claim_amount_" + wo_type).val();
	if(!logistics_department_claim_flag && !transport_vendor_claim_flag) {
		alert("无赔偿责任方");
		return false;
		
	} else {
		if(logistics_department_claim_flag) {
			if(logistics_department_claim_amount == "") {
				alert("物流部赔偿金额为空");
				return false;
				
			}
			if(isNaN(logistics_department_claim_amount) || logistics_department_claim_amount < 0) {
				alert("物流部赔偿金额不合法");
				return false;
				
			}
			
		} else {
			logistics_department_claim_amount= 0;
			
		}
		if(transport_vendor_claim_flag) {
			if(transport_vendor_claim_amount == "") {
				alert("物流服务商赔偿金额为空");
				return false;
				
			}
			if(isNaN(transport_vendor_claim_amount) || transport_vendor_claim_amount < 0) {
				alert("物流服务商赔偿金额不合法");
				return false;
				
			}
			
		} else {
			transport_vendor_claim_amount= 0;
			
		}
		
	}
	var total_applied_claim_amount_node = $("#total_applied_claim_amount_" + wo_type);
	var total_applied_claim_amount;
	if(wo_type == "NOMSC") {
		total_applied_claim_amount = total_applied_claim_amount_node.val();
		
	} else if(wo_type == "OMSC") {
		total_applied_claim_amount = total_applied_claim_amount_node.text().trim();
		
	}
	var sss = (logistics_department_claim_amount*1 + transport_vendor_claim_amount*1).toFixed(2);
	var ssss = (total_applied_claim_amount*1).toFixed(2);
	
	if(sss > ssss) {
		alert("责任方赔偿金额大于申请理赔总金额");
		return false;
		
	} else if((sss < ssss) && ($("#remark_" + wo_type).val() == "")) {
		alert("部分理赔需填写跟进结果");
		return false;
		
	}
	if(wo_type == "NOMSC") {
		if(!$('#claim_detail_NOMSC').children().length) {
			alert("索赔明细为空");
			return false;
			
		}
		if(checkOk() == 0) {
			alert("索赔明细未点击完成");
			return false;
			
		}
		
	}
	return true;
	
}

// 已处理工单显示
function completedWorkOrderDisplay(wo_type) {
	// 更新工单处理状态
	$("#woProcessStatus_woDisplay").text("已处理")
	// 工单异动隐藏
	$("#btn_alter").hide();
	// 隐藏操作工单按钮
	$("#btn_undo").siblings().each(function(){
		if($(this).css("display") != "none") {
			$(this).hide();
		}
	})
	// 隐藏附件上传按钮区域
	$("#btn_op").hide();
	// 保存按钮
	$("#save_"+wo_type).hide();
	// 处理按钮
	$("#process_"+wo_type).hide();
	//
	if(wo_type == "NOMSC") {
		// 隐藏非OMS索赔明细操作列
		$("#op_NOMSC").hide();
		// 隐藏工单明细新增按钮
		$("#NOMSCadd").hide();
	}
}

// 刷新工单日志
function refreshWorkOrderEventMonitor(woems) {
	// 清空旧数据
	$("#workOrderEventMonitor").empty();
	// 拼接新数据
	var tr="";
	for(var i=0; i<woems.length; i++) {
		tr+="<tr><td class='td_cs' title='";
		var td;
		if(isNull(woems[i].create_time)) {
			td="";
			
		} else {
			td=woems[i].create_time;
			
		}
		tr+=td+"' style='width:15%;' >"+td+"</td><td class='td_cs' title='";
		if(isNull(woems[i].create_by)) {
			td="";
			
		} else {
			td=woems[i].create_by;
			
		}
		tr+=td+"' style='width:10%;' >"+td+"</td><td class='td_cs' title='";
		if(isNull(woems[i].event_description)) {
			td="";
			
		} else {
			td=woems[i].event_description;
			
		}
		tr+=td+"' style='width:15%;' >"+td+"</td><td class='td_cs' title='";
		if(isNull(woems[i].remark)) {
			td="";
			
		} else {
			td=woems[i].remark;
			
		}
		tr+=td+"' style='width:60%;' >"+td+"</td></tr>";
		
	}
	$("#workOrderEventMonitor").append(tr);
	
}

// 操作工单
function operation(wo_type, operation_type) {
//	if(!confirm("是否执行当前操作？")) {
//		return;
//		
//	}
	// 工单操作-0保存-1处理查件-2处理索赔-3索赔成功-4索赔失败
	var json= {"wo_id":$("#wo_id").val(),"wo_type":wo_type,"operation_type":operation_type,failure_reason:$("#failure_reason").val()};
	if(wo_type == "NOMSC" || wo_type == "OMSC") {
		// 索赔工单
		if(operation_type<3) {
			// 保存或处理索赔需要校验
			if(!checkClaim(wo_type)) {
				return;
				
			}
			var jsonArray= eval(
					// 外箱破损
					"([{'code': 'outer_damaged_flag', 'value': isChecked('outer_damaged_flag_"
					+ wo_type
					// 二次分箱
					+ "')},{'code': 'repeat_box_flag', 'value': isChecked('repeat_box_flag_"
					+ wo_type
					// 产品退回
					+ "')},{'code': 'return_flag', 'value': isChecked('return_flag_"
					+ wo_type
					// 箱内填充充分
					+ "')},{'code': 'filling_in_box_fully_flag', 'value': isChecked('filling_in_box_fully_flag_"
					+ wo_type
					// 产品包装破损
					+ "')},{'code': 'package_damaged_flag', 'value': isChecked('package_damaged_flag_"
					+ wo_type
				//是否保价
                    + "')},{'code': 'isbj', 'value': isChecked('isbj_"
                    + wo_type
                // 判定结果
                + "').val()},{'code': 'result', 'value': $('#result_"
                + wo_type
                // 结果细分
                + "').val()},{'code': 'resultxf', 'value':$('#resultxf_"
                + wo_type
                // 责任归属
                + "').val()},{'code': 'zrgs', 'value': $('#zrgs_"
                + wo_type
                // 备注
                + "').val()},{'code': 'ps', 'value': $('#ps_"
                + wo_type
					// 跟进结果
					+ "').val()},{'code': 'remark', 'value': $('#remark_"
					+ wo_type
					// 附件列表
					+ "').val()},{'code': 'fileName', 'value': $('#fileName_common').val()}])"
					
			);

			if(operation_type == 2) {
				var claim_status="0";
				var total_claim_amount=$("#logistics_department_claim_amount_" + wo_type).val()*1 + $("#transport_vendor_claim_amount_" + wo_type).val()*1;
				if(total_claim_amount == 0) {
					// 不索赔，直接索赔失败
					claim_status="2";
					
				} else {
					if((wo_type == "OMSC") && (total_claim_amount == $("#total_applied_claim_amount_"+wo_type).text().trim())) {
						// OMS索赔如为全额索赔，无需等待OMS反馈，直接通过
						jsonArray.push({"code": "oms_retrun_flag", "value": "1"});
						
					}
					// 非OMS索赔部分索赔部分索赔与全额索赔无区别
					
				}
				jsonArray.push({"code": "claim_status", "value": claim_status});
				
			}
			// 物流部赔偿
			var logistics_department_claim_flag= isChecked("logistics_department_claim_flag_" + wo_type);
			jsonArray.push({"code": "logistics_department_claim_flag", "value": logistics_department_claim_flag});
			if(logistics_department_claim_flag) {
				// 赔偿金额
				jsonArray.push({"code": "logistics_department_claim_amount", "value": $("#logistics_department_claim_amount_" + wo_type).val()});
				
			}
			// 物流服务商赔偿
			var transport_vendor_claim_flag= isChecked("transport_vendor_claim_flag_" + wo_type);
			jsonArray.push({"code": "transport_vendor_claim_flag", "value": transport_vendor_claim_flag});
			if(transport_vendor_claim_flag) {
				// 赔偿金额
				jsonArray.push({"code":  "transport_vendor_claim_amount", "value": $("#transport_vendor_claim_amount_" + wo_type).val()});
				
			}
			if(wo_type == "NOMSC") {
				// 索赔分类
				jsonArray.push({"code": "claim_type", "value": $("#claim_type_NOMSC").val()});
				// 索赔原因
				jsonArray.push({"code": "claim_reason", "value": $("#claim_reason_NOMSC").val()});
				// 附加金额
				jsonArray.push({"code": "added_value", "value": $("#added_value_NOMSC").val()});
				// 申请理赔总金额
				jsonArray.push({"code": "total_applied_claim_amount", "value": $("#total_applied_claim_amount_NOMSC").val()});
				// 店铺备注
				jsonArray.push({"code": "store_remark", "value": $("#store_remark_NOMSC").val()});
				// 索赔明细
                jsonArray.push({"code": "isbj", "value": $("#isbj_NOMSC").val()});
                // 是否保价

				var jsonArray2Str="[";
				var details= $('#claim_detail_NOMSC').children();
				$.each(details, function(index,item) {
					jsonArray2Str+=
						"{'wo_id':'"
						+ $("#wo_id").val()
						+ "','sku_number':'"
						+ $(this).children().eq(0).text()
						+ "','sku_name':'"
						+ $(this).children().eq(1).text()
						+ "','id':'"
						+ $(this).attr('id')
						+ "','sku_num':'"
						+ $(this).children().eq(2).text()
						+ "','platform_price':'"
						+ $(this).children().eq(3).text()
						+ "','platform_price_all':'"
						+ $(this).children().eq(4).text()
						+ "','claim_num_applied':'"
						+ $(this).children().eq(5).text()
						+ "','claim_price_applied':'"
						+ $(this).children().eq(6).text()
						+ "','claim_value_applied':'"
						+ $(this).children().eq(7).text()
						+ "'}";
					if(index != details.length-1) jsonArray2Str+= ",";	 
			      
				});
				jsonArray2Str+= "]";
				// 封装明细
				json["detail"]= encodeURI(jsonArray2Str);
				
			}
			// 封装主表信息
			json["values"]= jsonArray;
			
		} else {
			var claim_status;
			if(operation_type == 3) {
				claim_status=1;
				
			}
			if(operation_type == 4) {
				claim_status=2;
				
			}
			json["values"]=[{"code":"claim_status", "value":claim_status}];
			
		}
		
	} else {
		// 查件工单
		json["values"]=[{"code":"fileName", "value":$("#fileName_common").val()},
			{"code":"orderNum", "value":$("#orderNum_" + wo_type).val()},
			{"code":"sendTime", "value":$("#sendTime_" + wo_type).val()},
			{"code":"resultInfo", "value": $("#resultInfo_" + wo_type).val()},
            {"code":"result", "value": $("#result_" + wo_type).val()},
            {"code":"resultPart", "value": $("#resultPart_" + wo_type).val()},
            {"code":"dutyBelong", "value": $("#dutyBelong_" + wo_type).val()},
            {"code":"remark", "value": $("#remark_" + wo_type).val()}
            /*,
            {"code":"resultInfoNew", "value": $("#resultInfoNew_" + wo_type).val()},*/
			];
		
	}
	loadingStyle();
	$.ajax({
		type: "POST",
	  	url: "/BT-LMIS/control/workOrderManagementController/saveOperation.do",
	    dataType: "json",
	    data: "json=" + encodeURI(JSON.stringify(json)),
	    success: function (result) {
	    	alert(result.result_content);
	    	// 解除旋转
			cancelLoadingStyle();
			if(result.result_code == "SUCCESS" && operation_type != 0) {
//				if(operation_type == 1 || operation_type == 2) {
//					// 已处理工单显示
//					completedWorkOrderDisplay(wo_type);
//					// 日志刷新
//					refreshWorkOrderEventMonitor(result.woems);
//				} else if(operation_type == 3 || operation_type == 4) {
//					// 索赔成功
//					$("#success_" + wo_type).hide();
//					// 索赔失败
//					$("#failure_" + wo_type).hide();
//				}
//				// 加载操作数据
//				loadingOperation($("#wo_id").val(), wo_type);
				if(operation_type == 2) {
					// 处理索赔
					// 已处理工单显示
					completedWorkOrderDisplay(wo_type);
					// 日志刷新
					refreshWorkOrderEventMonitor(result.woems);
					// 加载操作数据
					loadingOperation($("#wo_id").val(), wo_type);
				} else {
					// 处理查件/索赔成功/索赔失败
					$("#btn_undo").click();
					// 隐藏遮罩
					$(".modal-backdrop").hide();
				}
			}				
	    }
	});
}

function change_wo_type(){
	var obj1=document.getElementById('wl');
	for(var i=obj1.options.length-1;i>=0;i--){
		obj1.options.remove(i); 
	}
	var url="/BT-LMIS/control/workOrderManagementController/get_wo_level.do?wo_type="+$("#wo_type_select_id").val();
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				$("#wl").next().prop("disabled", false);
				var obj1=document.getElementById('wl');
					for(i=0;i<jsonStr.length;i++){
						if($("#wl_before").val()==jsonStr[i].code){
							obj1.add(new Option(jsonStr[i].name,jsonStr[i].code,true,true)); 
						}else{
							obj1.add(new Option(jsonStr[i].name,jsonStr[i].code,false)); 
						}
					}
			}
		});
	   change_error_type();
	   change_reason_info();
}


function change_error_type(){
	var obj2=document.getElementById('et');
	for(var i=obj2.options.length-1;i>=0;i--){
		obj2.options.remove(i); 
	}
	var url="/BT-LMIS/control/workOrderManagementController/get_error_type.do?wo_type="+$("#wo_type_select_id").val();
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				$("#et").next().prop("disabled", false);
				var obj=document.getElementById('et');
					for(i=0;i<jsonStr.length;i++){
						if($("#et_before").val()==jsonStr[i].code){
							obj2.add(new Option(jsonStr[i].type_name,jsonStr[i].id,true,true)); 
						}else{
							obj2.add(new Option(jsonStr[i].type_name,jsonStr[i].id,false)); 
						}
					}
			}
		});
	 
}


function change_reason_info(){
	var obj3=document.getElementById('ar');
	for(var i=obj3.options.length-1;i>=0;i--){
		obj3.options.remove(i); 
	}
	var url="/BT-LMIS/control/workOrderManagementController/get_reason_info.do?wo_type="+$("#wo_type_select_id").val();
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				$("#ar").next().prop("disabled", false);
				var obj=document.getElementById('ar');
					for(i=0;i<jsonStr.length;i++){
						if($("#ar_before").val()==jsonStr[i].id){
							obj3.add(new Option(jsonStr[i].reason,jsonStr[i].id,true,true)); 
						}else{
							obj3.add(new Option(jsonStr[i].reason,jsonStr[i].id,false)); 
						}
					}
			}
		});
	 
}

function wo_level_change(){
	shiftLevel('wl', {'wlbId': 'wl_before', 'reasonDiveId': 'levelAlter', 'reasonId': 'ar', 'groupId': 'groups', 'accountId': 'account'});
	endueDisplay('wl');
}