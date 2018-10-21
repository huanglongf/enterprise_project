// 操作界面固定高度
$(function() {
	//
	$(".lmis-panel").height($(window).height() - 136);
	
})

function dataView() {
	if($(".icon-chevron-up")) {
		$(".icon-chevron-up").click();
		
	}
	loadingStyle();
	openIdDiv("data",
			"/BT-LMIS/control/storeController/dataView.do?pageName=data&isQuery=true&"
			+ $("#condition").serialize()
			+ "&startRow="
			+ $("#startRow").val()
			+ "&endRow="
			+ $("#startRow").val()
			+ "&page="
			+ $("#pageIndex").val()
			+ "&pageSize="
			+ $("#pageSize").val()
			
	);
	// 解除旋转
	setTimeout("cancelLoadingStyle()", 500);
	
}

//分页跳转
function pageJump() {
	dataView();
	
}

function refreshDataView() {
	$("#costCenterQuery").val("");
	$("#storeCodeQuery").val("");
	$("#storeNameQuery").val("");
	initializeSelect("ownedCustomerQuery");
	initializeSelect("storeTypeQuery");
	initializeSelect("settlementMethodQuery");
	$("#contactQuery").val("");
	$("#phoneQuery").val("");
	$("#addressQuery").val("");
	$("#companyQuery").val("");
	initializeSelect("invoiceTypeQuery");
	$("#invoiceInfoQuery").val("");
	initializeSelect("woFlagQuery");
	initializeSelect("validityQuery");
	//
	dataView();
}

function cleanFormCache() {
	//
	initCheckInfo(".formCheckInfo");
	// 店铺id
	$("#id").val("");
	// 所属客户
	initializeSelect("clientId");
	$("#clientId").children(":first").siblings().remove();
	$("#clientId").siblings("ul").children(":first").siblings().remove();
	// 成本中心
	$("#costCenter").val("");
	// 店铺编码
	$("#storeCode").val("");
	// 店铺名称
	$("#storeName").val("");
	// 店铺类型
	$("#storeType option:eq(0)").prop("selected", "selected");
	// 结算方式
	$("#settlementMethod option:eq(0)").prop("selected", "selected");
	// 联系人
	$("#contact").val("");
	// 联系电话
	$("#phone").val("");
	// 联系地址
	$("#address").val("");
	// 开票公司
	$("#company").val("");
	// 发票类型
	$("#invoiceType option:eq(0)").prop("selected", "selected");
	// 发票信息
	$("#invoiceInfo").val("");
	// 备注
	$("#remark").val("");
	// 雷达监控
	if($("#woFlag").prop("checked")) {
		$("#woFlag").prop("checked", false);
		
	}
	// 是否有效
	if(!$("#validity").prop("checked")) {
		$("#validity").prop("checked", true);
		
	}
	
}

function toForm(currentRow) {
	// 清除页面缓存
	cleanFormCache();
	//
	var content1 = "";
	var content2 = "";
	// 弹窗挑标题
	var formTitle = "店铺";
	if(currentRow == null) {
		formTitle += "新增";
		//
		$.ajax({
			type:"POST",
	      	url:"/BT-LMIS/control/consumerController/listValidConsumer.do",
	        dataType:"json",
	        data:{},
	        success:function (result) {
				// 工单类型
				for(var i=0; i<result.consumer.length; i++) {
					content1 += '<option value="' + result.consumer[i].id + '">'　+　result.consumer[i].clientName +　'</option>';
					content2 += '<li class="m-list-item" data-value="' + result.consumer[i].id + '">' + result.consumer[i].clientName + '</li>';
					
				}
				// 装上之后的值
				$("#clientId option:eq(0)").after(content1);
				$("#clientId").siblings("ul").children(":first").after(content2);
	        	
	       	}
	  	
	   	});
		
	}
	if(currentRow != null) {
		formTitle += "编辑";
		//
		$.ajax({
			type:"POST",
	      	url:"/BT-LMIS/control/storeController/getStore.do",
	        dataType:"json",
	        data:{"id":currentRow.children(':first').children(':first').val() },
	        success:function (result) {
	        	// 店铺ID
	        	$("#id").val(result.store.id);
	        	// 所属店铺
	        	$("#clientId").siblings("ul").children(":first").removeClass("m-list-item-active");
	        	for(var i= 0; i< result.consumer.length; i++){
					content1+= '<option value="' + result.consumer[i].id + '" ';
					content2+= '<li class="m-list-item';
					if(result.consumer[i].id == result.store.clientId) {
						content1 += 'selected= "selected" ';
						content2 += ' m-list-item-active'
						$("#clientId").next().val(result.consumer[i].clientName);
						$("#clientId").next().attr("placeholder", result.consumer[i].clientName);
						
					}
					content1 += '>'　+　result.consumer[i].clientName +　'</option>';
					content2 += '" data-value="' + result.consumer[i].id + '">' + result.consumer[i].clientName + '</li>';
					
				}
        		// 装上之后的值
				$("#clientId option:eq(0)").after(content1);
				$("#clientId").siblings("ul").children(":first").after(content2);
				// 成本中心
				$("#costCenter").val(result.store.costCenter);
				// 店铺编码
				$("#storeCode").val(result.store.storeCode);
				// 店铺名称
				$("#storeName").val(result.store.storeName);
				// 店铺类型
				$("#storeType option:eq(" +　(Number(result.store.storeType) + 1) + ")").prop("selected", "selected");
				// 结算方式
				if(result.store.settlementMethod != null) {
					$("#settlementMethod option:eq(" +　(result.store.settlementMethod + 1) + ")").prop("selected", "selected");
					
				}
				// 联系人
				$("#contact").val(result.store.contact);
				// 联系电话
				$("#phone").val(result.store.phone);
				// 联系地址
				$("#address").val(result.store.address);
				// 开票公司
				$("#company").val(result.store.company);
				// 发票类型
				$("#invoiceType option:eq(" +　(Number(result.store.invoiceType) + 1) + ")").prop("selected", "selected");
				// 发票信息
				$("#invoiceInfo").val(result.store.invoiceInfo);
				// 备注
				$("#remark").val(result.store.remark);
				// 雷达监控
				if(result.store.woFlag == "1") {
					$("#woFlag").prop("checked", true);
					
				}
				if(result.store.woFlag == "0") {
					$("#woFlag").prop("checked", false);
					
				}
				// 是否有效
				if(result.store.validity) {
					$("#validity").prop("checked", true);
					
				}
				if(!result.store.validity) {
					$("#validity").prop("checked", false);
					
				}
                if(result.store.storebj) {
					debugger
                    $("#storebj").prop("checked", true);

                }
	       	}
	  	
	   	});
		
	}
	$("#formTitle").text(formTitle);
	$("#form").modal({backdrop: "static", keyboard: false});
	
}

function save() {
	if(!checkValues("saveForm")) {
		return;
		
	}
	if(!confirm("确认保存？")) {
		return;
		
	}
	loadingStyle();
	$.ajax({
		type:"POST",
      	url:"/BT-LMIS/control/storeController/save.do",
        dataType:"json",
        data:$("#saveForm").serialize(),
        success: function (result) {
        	alert(result.message);
        	// 解除旋转
        	cancelLoadingStyle();
        	//
        	if(result.code == "200") {
        		// 隐藏弹窗
        		$("#form").modal("hide");
        		//
        		cleanFormCache();
        		//
        		dataView();
        		
        	}
        	
        },
        error: function() {
        	// 解除旋转
        	cancelLoadingStyle();
        	
        }
        
	});
	
}

function del() {
	if($("input[name='ckb']:checked").length == 0){
		alert("请选择一行!");
		return;
		
	}
	if(!confirm("是否删除所选店铺?")){
	  	return;
	  	
	}
	loadingStyle();
	var ids= [];
	// 遍历每一个name为priv_id的复选框，其中选中的执行函数
  	$("input[name='ckb']:checked").each(function(){
  		// 将选中的值添加到数组priv_ids中
		ids.push($.trim($(this).val()));
  		
  	});
  	$.ajax({
		type:"POST",
      	url:root+"/control/storeController/del.do?",
        dataType:"json",
        data:{"ids":ids},
        success:function (result) {
        	alert(result.message);
        	// 解除旋转
        	cancelLoadingStyle();
        	//
    		dataView();
			
       	}
  	
   	});
	
}

function download(){
	loadingStyle();
	$.post("/BT-LMIS/control/storeController/download.do?pageName=data&isQuery=true&"
			+ $("#condition").serialize(),
	         function (data){
				if(data.code==1){
					cancelLoadingStyle();
					alert('操作成功！');
					window.open(root +'/DownloadFile/' +data.path);
				}else{			
					cancelLoadingStyle();
					alert('操作失败！'+data.msg);
				}
			}		
	);
	// 解除旋转
	setTimeout("cancelLoadingStyle()", 500);	
}

