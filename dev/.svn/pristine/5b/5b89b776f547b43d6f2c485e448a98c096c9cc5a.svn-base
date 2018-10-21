// 操作界面固定高度
$(function() {
	//
	var clipboard=new Clipboard('.a-copy');
	clipboard.on('success', function(e) {
		console.log(e);
        alert("复制成功！")
        
	});
	clipboard.on('error', function(e) {
		console.log(e);
		alert("复制失败！请手动复制")
		
	});
	// 通过该方法来为每次弹出的模态框设置最新的zIndex值，从而使最新的modal显示在最前面
    $(document).on('show.bs.modal', '.modal', function (event) {$(this).css('z-index', 1040 + (10 * $('.modal:visible').length));});
	//
	$(".lmis-panel").height($(window).height() - 136);
	//
	$("#date_domain").daterangepicker(null, function(start, end, label) {});
	//
	$("#dateDomain").daterangepicker(null, function(start, end, label) {});
	//
	$("#contract_in_estimate").next().attr("disabled", "disabled");
	
})

// 初始化查询
function initCondition() {
	$("#batch_number").val("");
	initializeSelect("batch_status");
	$("#date_domain").val("");
	initializeSelect("estimate_type");
	shiftContractByType();
	
}

// 查询
function query() {
	loadingStyle();
	openIdDiv("data",
			"/BT-LMIS/control/estimateController/query.do?pageName=data&"
			+ $("#condition").serialize()
			+ "&loadingType=DATA&isQuery=true&startRow="
			+ $("#startRow").val()
			+ "&endRow="
			+ $("#startRow").val()
			+ "&page="
			+ $("#pageIndex").val()
			+ "&pageSize="
			+ $("#pageSize").val()
			
	);
	// 解除旋转
	setTimeout("cancelLoadingStyle()", 1000);
	
}

//分页跳转
function pageJump() {
	query();
	
}

function refreshCondition() {
	initCondition();
	query();
	
}

function shiftContractByType() {
	var type=$("#estimate_type").val();
	initializeSelect("contract_in_estimate");
	$("#contract_in_estimate").children(":first").siblings().remove();
	$("#contract_in_estimate").siblings("ul").children(":first").siblings().remove();
	if(type=="" && !$("#contract_in_estimate").next().attr("disabled")) {
		$("#contract_in_estimate").next().attr("disabled","disabled");
		
	}
	if(type!="") {
		$.ajax({
			type:"POST",
	        url:"/BT-LMIS/control/estimateController/shiftContractByType.do",
	        dataType:"json",
	        data: {"type":type },
	        success: function(result) {
	        	if(result.result_code=="ERROR") {
	        		alert(result.result_content);
	        		
	        	} else {
	        		var contract=result.contract;
	        		// 添加新值
	        		var content1="";
	            	var content2="";
	            	for(var i=0; i<contract.length; i++){
	    				content1+='<option value="'+contract[i].id+'" >'+contract[i].contract_name+'</option>';
	    				content2+='<li class="m-list-item" data-value="'+contract[i].id+'">'+contract[i].contract_name+'</li>';
	    				
	    			}
	        		// 装上之后的值
	    			$("#contract_in_estimate option:eq(0)").after(content1);
	    			$("#contract_in_estimate").siblings("ul").children(":first").after(content2);
	    			//
	    			$("#contract_in_estimate").next().attr("disabled",false);
	    			
	        	}
	        	
	        }
	        
		})
		
	}
	
}

function openAddForm() {
	var now = new Date();
	var month = (now.getMonth() + 1);
	if(month < 10) {
		month = "0" + month;
		
	}
	var day = now.getDate();
	if(day < 10) {
		day = "0" + day;
		
	}
	var date = now.getFullYear() + "-" + month + "-" + day;
	$("#dateDomain").val(date + " - " + date);
	$("#add_Form").modal({backdrop: "static", keyboard: false});
	
}

function shiftContractListByType() {
	// 清除合同缓存部分
	$("#contractNameParam").val("");
	$("#choosenContract").empty();
	//
	var type=$("#estimateType").val();
	if(type=="" && !($("#contractList").css("display")=="none")) {
		$("#contractList").hide();
		
	}
	if(type!="") {
		// loadingStyle();
		openIdDiv("sc_content_contract","/BT-LMIS/control/estimateController/queryContract.do?type="+type);
		// 解除旋转
		// setTimeout("cancelLoadingStyle()", 500);
		$("#contractList").show();
		
	}
	
}

function queryContract(event) {
	if(event == null || (event != null && event.keyCode == 13)) {
		// blur查询或enter查询
		openIdDiv("sc_content_contract",
				"/BT-LMIS/control/estimateController/queryContract.do?type="+$("#estimateType").val()+"&contractName="+$("#contractNameParam").val());
		
	}
	
}

function chooseContract() {
	$("#checkAll").prop("checked", false);
	if($("input[name='ckb']:checked").length != 0) {
		var choosenContract = "";
		$("input[name='ckb']:checked").each(function() {
			if($(this).parent().parent().css("display") != "none") {
				choosenContract +=
					"<tr style='cursor:pointer;'><td class='td_ch'><input name='ckb2' type='checkbox' value='"
					+ $(this).val()
					+ "' /></td><td class='td_cs' title="
					+ $(this).parent().next().text()
					+ ">"
					+ $(this).parent().next().text()
					+ "</td></tr>";
				$(this).parent().parent().hide();
				
			}
			$(this).prop("checked",false);
			
		})
		$("#choosenContract").append(choosenContract);
		
	}
	
	
}

function removeContract() {
	$("#checkAll2").prop("checked", false);
	if($("input[name='ckb2']:checked").length != 0) {
		$("input[name='ckb2']:checked").each(function() {
			$(this).parent().parent().remove();
			$("#contract"+$(this).val()).show();
			
		})
		
	}
	
}

function cleanAddForm() {
	initializeSelect("estimateType");
	shiftContractListByType();
	//
	$("#remark").val("");
	// 校验信息还原
	initCheckInfo(".addCheckInfo");
	
}

function add() {
	if(!checkValues("addForm")) {
		return;
		
	}
	if($("input[name='ckb2']").length == 0){
		alert("请选择需要预估的合同");
		return;
		
	}
	if(!confirm("是否新建结算预估？")) {
		return;
		
	}
	var contractId=[];
	$("input[name='ckb2']").each(function() {
		contractId.push($.trim($(this).val()));
		
	})
	loadingStyle();
	$.ajax({
		type:"POST",
        url:"/BT-LMIS/control/estimateController/operateEstimate.do",
        dataType:"json",
        data:{
        	"order":"add",
        	"dateDomain":$("#dateDomain").val(),
        	"estimateType":$("#estimateType").val(),
        	"contractId":contractId,
        	"remark":$("#remark").val()
        	
        },
        success:function(result){
        	alert(result.result_content);
        	if(result.result_code=="SUCCESS") {
        		cleanAddForm();
        		$("#retrunBatchNumber").text(result.retrunBatchNumber);
        		$("#batchNumber_Form").modal({backdrop: "static", keyboard: false});
        		
        	}
        	// 解除旋转
        	cancelLoadingStyle();
        	
        }
        
	})
	
}

function closeWindows() {
	$("#add_Form").modal("hide");
	query();
	
}

function waitDevelop() {
	alert("待开发");
	
}

function download(batchNumber) {
	$.ajax({
		type:"POST",
		url:"/BT-LMIS/control/estimateController/download.do",  
		data:{"batchNumber":batchNumber},
		dataType:"",  
		success: function(jsonStr) {
			window.open(root+jsonStr);
			
		}
		
	});
	
}

function shiftEstimateStatus(order, batchNumber) {
	if(!confirm("是否执行当前操作？")) {
		return;
		
	}
	loadingStyle();
	$.ajax({
		type:"POST",
		url:"/BT-LMIS/control/estimateController/operateEstimate.do",  
		data:{
			"order":order,
			"batchNumber":batchNumber
			
		},
		dataType:"json",  
		success: function(result) {
			alert(result.result_content);
			if(result.result_code=="SUCCESS") {
				query();
				
			}
			// 解除旋转
        	cancelLoadingStyle();
			
		}
		
	});
	
}