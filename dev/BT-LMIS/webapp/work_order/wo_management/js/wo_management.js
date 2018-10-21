// 工单管理页面方法
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
	$("#account").next().prop("disabled", "disabled");
	
})

// 列表查询
function find() {
//	loadingStyle();
	tablesearch("","");
//	openIdDiv("wo_management_platform_list",
//			"/BT-LMIS/control/workOrderController/query.do?loadingType=DATA&isCollapse="
//			+ $(".widget-box").hasClass("collapsed")
//			+ "&isQuery=true&"
//			+ $("form").serialize()
//			+ "&startRow="
//			+ $("#startRow").val()
//			+ "&endRow="
//			+ $("#startRow").val()
//			+ "&page="
//			+ $("#pageIndex").val()
//			+ "&pageSize="
//			+ $("#pageSize").val()
//	);
}

// 分页跳转
function pageJump() {
	tablesearch('','');
}

// 刷新查询条件列表
function refreshCondition() {
	// 工单分配状态
	initializeSelect("wo_alloc_status");
	// 工单处理状态
	initializeSelect("wo_process_status");
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
	// 处理者
	$("#processor").val("");
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

// 初始化手动分配工单页面
function initalAllocForm() {
	//
	if(isChecked("outManhours")) {
		$("#outManhours").prop("checked", false);
		
	}
	// 具有处理所选工单权限组别
	initializeSelect("groups");
	$("#groups").children(":first").siblings().remove();
	$("#groups").siblings("ul").children(":first").siblings().remove();
	// 组别所属员工
	initializeSelect("account");
	$("#account").children(":first").siblings().remove();
	$("#account").siblings("ul").children(":first").siblings().remove();
	$("#account").next().attr("disabled", "disabled");
	//
	// $("input[name='ckb']:checked").prop("checked", false);
	// 校验信息还原
	initCheckInfo(".allocCheckInfo");
	
}

// 加载手动分配工单页面
function toAllocForm() {
	if($("input[name='ckb']:checked").length == 0){
		alert("请选择需要分配的工单！");
		return;
		
	}
	var allocated=0;
	var unAllocated=0;
	var ids=[];
	$("input[name='ckb']:checked").each(function() {
		if($(this).parent().siblings().eq("11").text() == "已分配") {
			$(this).prop("checked", false);
			allocated+=1;
			
		} else {
			ids.push($(this).val());
			unAllocated+=1;
			
		}
		
	});
	if(allocated!=0) {
		alert("已分配工单自动取消选择");
		
	}
	if(unAllocated==0) {
		alert("无需要分配工单");
		return;
		
	}
	loadingStyle();
	$.ajax({
		type: "POST",
      	url: "/BT-LMIS/control/workOrderController/toAllocForm.do",
        dataType: "json",
        data: {ids:ids },
        success: function (result) {
        	var groups = result.groups;
        	var content1 = "";
        	var content2 = "";
        	for(var i= 0; i< groups.length; i++){
				try {
					content1+= '<option value="' + groups[i].id + '" >'　+　groups[i].group_name +　'</option>';
					content2+= '<li class="m-list-item" data-value="' + groups[i].id + '">' + groups[i].group_name + '</li>';
				} catch (e) {
					console.error(e);
				}
			}
    		// 装上之后的值
			$("#groups option:eq(0)").after(content1);
			$("#groups").siblings("ul").children(":first").after(content2);
			// 解除旋转
        	cancelLoadingStyle();
        	
        },
        error: function() {
        	// 解除旋转
        	cancelLoadingStyle();
        	
        }
	
	});
	$("#woAlloc_form").modal({backdrop: "static", keyboard: false});
	// 隐藏遮罩
	$(".modal-backdrop").hide();
	
}

// 分配工单
function alloc(formId) {
	if(!confirm("确认分配？")) {
		return;
		
	}
	if(!checkValues(formId)) {
		return;
		
	}
	var ids=[];
	$("input[name='ckb']:checked").each(function(){
		ids.push($(this).val());
		
	})
	loadingStyle();
	$.ajax({
		type: "POST",
      	url: "/BT-LMIS/control/workOrderController/alloc.do?" + $("#" + formId).serialize(),
        dataType: "json",
        data: {ids:ids},
        success: function (result) {
        	alert(result.result_content);
    		// 初始化弹窗
    		initalAllocForm();
    		// 隐藏弹窗
    		$("#woAlloc_form").modal("hide");
    		// 刷新页面
    		find();
        	// 解除旋转
        	cancelLoadingStyle();
        	
        },
        error: function() {
        	// 解除旋转
        	cancelLoadingStyle();
        	
        }
        
	});
	
}
//双击事件
function tableAction(currentRow, tableFunctionConfig) {
	if(tableFunctionConfig.dbclickTr == true) {
		toProcessForm("query", currentRow);
	}
}
//查询
function tablesearch(column, sort) {
	$(".search-table").load("/BT-LMIS/control/workOrderController2/query.do?&isCollapse=true"
		+ "&isQuery=true&pageName=table&tableName=wo_master&startRow="
		+ $("#startRow").val()
		+ "&endRow="
		+ $("#startRow").val()
		+ "&page="
		+ $("#pageIndex").val()
		+ "&pageSize="
		+ $("#pageSize").val()
		+ "&sortColumn="
		+ column
		+ "&sort="
		+ sort
		+ "&"
		+ $(".search_form").serialize()
	);
}