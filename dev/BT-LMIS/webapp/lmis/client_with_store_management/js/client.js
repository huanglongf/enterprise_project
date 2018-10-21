function save(){
	if($("#client_code").val().match(/^[a-zA-Z0-9]{1,20}$/) == null) {
		alert("客户编码非法！");
		return;
		
	}
	if($("#client_name").val().length< 1) {
		alert("客户名称为空！");
		return;
		
	}
	if($("#client_name").val().length> 25) {
		alert("客户名称过长！");
		return;
		
	}
	if($("#client_type").val() == -1) {
		alert("请选择客户类型！")
		return;
		
	}
	if($("#settlement_method").val() == -1) {
		alert("请选择结算方式！")
		return;
		
	}
	if($("#contact").val().length< 1) {
		alert("联系人为空！");
		return;
		
	}
	if($("#contact").val().length> 10) {
		alert("联系人过长！");
		return;
		
	}
	if($("#phone").val().match(/^1(3|4|5|7|8)\d{9}$/) == null && $("#phone").val().match(/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/) == null) {
		alert("联系电话非法！");
		return;
		
	}
	if($("#address").val().length< 1) {
		alert("联系地址为空！");
		return;
		
	}
	if($("#address").val().length> 100) {
		alert("联系地址过长！");
		return;
		
	}
	if($("#company").val().length< 1) {
		alert("开票公司为空！");
		return;
		
	}
	if($("#company").val().length> 100) {
		alert("开票公司过长！");
		return;
		
	}
	if($("#invoice_type").val() == -1){
		alert("请选择发票类型！")
		return;
		
	}
	if($("#invoice_info").val().length< 1) {
		alert("发票信息为空！");
		return;
		
	}
	if($("#invoice_info").val().length> 200) {
		alert("发票信息过长！");
		return;
		
	}
	if($("#remark").val().length> 100) {
		alert("备注过长！");
		return;
		
	}
	$.ajax({  
		type: "POST",
		url: "/BT-LMIS/control/clientController/editClient.do",
		dataType: "json",   
		data: {
			"id": $("#id").val(),
			"client_code": $('#client_code').val(),
			"client_name": $('#client_name').val(),
			"client_type": $('#client_type').val(),
			"settlement_method": $('#settlement_method').val(),
			"contact": $('#contact').val(),
			"phone": $("#phone").val(),
			"address": $("#address").val(),
			"company": $("#company").val(),
			"invoice_type": $("#invoice_type").val(),
			"invoice_info": $("#invoice_info").val(),
			"remark": $("#remark").val(),
			"validity": isChecked("validity")
			
		},
		success: function (result) {  
			alert(result.result_content);
			if(result.result_code == "SUCCESS"){
				openDiv("/BT-LMIS/control/clientController/queryList.do");
				
			} else if(result.result_code == "ERROR") {
				
				
			}
			
		}
		
    });
	
}

function del(){
	if($("input[name='ckb']:checked").length == 0){
		alert("请选择一行!");
		return;
		
	}
	if(!confirm("是否删除所选客户?")){
	  	return;
	  	
	}
	var priv_ids= [];
	// 遍历每一个name为priv_id的复选框，其中选中的执行函数
  	$("input[name='ckb']:checked").each(function(){
  		// 将选中的值添加到数组priv_ids中
		priv_ids.push($.trim($(this).val()));
  		
  	});
  	$.ajax({
		type: "POST",
       	url: root+ "/control/clientController/del.do?",
       	dataType: "json",
       	data: "privIds=" + priv_ids,
       	success: function(result) {
			if(result.result_code == "ERROR"){
				alert(result.result_content);
				
    	   	} else {
    	   		if(parseInt(result.total) - parseInt(result.success) == 0){
    	   			alert("删除成功！");
    	   			
    	   		} else {
    	   			alert("删除成功：" + parseInt(result.success) + "；删除失败：" + (parseInt(result.total) - parseInt(result.success)) + "；失败原因：客户下存在店铺或客户存在合同！");
    	   			
    	   		}
    	   		
    	   	}
			find();
			
       	}
       	
	});
	
}

function pageJump() {
	find();
	
}

function find(){
	openDiv("/BT-LMIS/control/clientController/queryList.do?client_code="
		+ $("#client_code").val()
		+ "&client_name="
		+ $("#client_name").val()
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
