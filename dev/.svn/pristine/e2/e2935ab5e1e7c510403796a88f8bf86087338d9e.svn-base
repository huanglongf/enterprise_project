function saveStore(){
	if ($("#cost_center_form").val().length< 1) {
		alert("成本中心为空！");
		return;
		
	}
	if ($("#cost_center_form").val().length> 100) {
		alert("成本中心过长！");
		return;
		
	}
	if ($("#store_code_form").val().length< 1) {
		alert("店铺编码为空！");
		return;
		
	}
	if ($("#store_code_form").val().length> 25) {
		alert("店铺编码过长！");
		return;
		
	}
	if ($("#store_name_form").val().length< 1) {
		alert("店铺名称为空！");
		return;
		
	}
	if ($("#store_name_form").val().length> 25) {
		alert("店铺名称过长！");
		return;
		
	}
	if($("#store_type_form").val() == -1){
		alert("请选择店铺类型！")
		return;
		
	}
	if($("#settlement_method_form").val() == -1){
		alert("请选择结算方式！")
		return;
		
	}
	if ($("#contact_form").val().length< 1) {
		alert("联系人为空！");
		return;
		
	}
	if ($("#contact_form").val().length> 10) {
		alert("联系人过长！");
		return;
		
	}
	if ($("#phone_form").val().match(/^1(3|4|5|7|8)\d{9}$/) == null && $("#phone_form").val().match(/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/) == null){
		alert("联系电话非法！");
		return;
		
	}
	if ($("#address_form").val().length< 1) {
		alert("联系地址为空！");
		return;
		
	}
	if ($("#address_form").val().length> 100) {
		alert("联系地址过长！");
		return;
		
	}
	if ($("#company_form").val().length< 1) {
		alert("开票公司为空！");
		return;
		
	}
	if ($("#company_form").val().length> 100) {
		alert("开票公司过长！");
		return;
		
	}
	if($("#invoice_type_form").val() == -1){
		alert("请选择发票类型！")
		return;
		
	}
	if ($("#invoice_info_form").val().length< 1) {
		alert("发票信息为空！");
		return;
		
	}
	if ($("#invoice_info_form").val().length> 200) {
		alert("发票信息过长！");
		return;
		
	}
	if ($("#remark_form").val().length> 100){
		alert("备注过长！");
		return;
		
	}
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/storeController/save2.do",
        dataType: "json",
        async: false,
        data: {
        	"id": $("#store_id").val(),
        	"client_id": $("#id").val(),
        	"cost_center": $("#cost_center_form").val(),
        	"store_code": $("#store_code_form").val(),
        	"store_name": $("#store_name_form").val(),
        	"store_type": $("#store_type_form").val(),
        	"settlement_method": $("#settlement_method_form").val(),
        	"contact": $("#contact_form").val(),
        	"phone": $("#phone_form").val(),
        	"address": $("#address_form").val(),
        	"company": $("#company_form").val(),
        	"invoice_type": $("#invoice_type_form").val(),
        	"invoice_info": $("#invoice_info_form").val(),
        	"remark": $("#remark_form").val(),
        	"validity": isChecked("validity_form")
        	
        },
        success: function(result) {  
        	alert(result.result_content);
        	if(result.result_code == "SUCCESS"){
        		$("#store_form").modal("hide");
        		find();
        		
			} else if(result.result_code == "ERROR") {
				
				
			}
        	
        }
        
	}); 
	
}

function toForm(current_row) {
	initalForm();
	var title= "店铺";
	if(current_row == ""){
		title += "新增";
		
	} else {
		title += "编辑";
		$.ajax({
			type: "POST",
          	url: "/BT-LMIS/control/storeController/getStore2.do",
	        dataType: "json",
	        data: {"store_id": current_row.children(":first").children(":first").val()},
	        async: false,
	        success: function (result) {
	        	var store= result.store;
	        	$("#store_id").val(store.id);
	        	$("#cost_center_form").val(store.cost_center);
	        	$("#store_code_form").val(store.store_code);
	        	$("#store_name_form").val(store.store_name);
	        	$("#store_type_form option:eq(" + (parseInt(store.store_type) + 1) +")").prop("selected", "selected");
	        	$("#settlement_method_form option:eq(" + (parseInt(store.settlement_method) + 1) +")").prop("selected", "selected");
	        	$("#contact_form").val(store.contact);
	        	$("#phone_form").val(store.phone);
	        	$("#address_form").val(store.address);
	        	$("#company_form").val(store.company);
	        	$("#invoice_type_form option:eq(" + (parseInt(store.invoice_type) + 1) +")").prop("selected", "selected");
	        	$("#invoice_info_form").text(store.invoice_info);
	        	$("#remark_form").val(store.remark);
	        	if(store.validity == true){
	        		$("#validity_form").prop("checked", "checked");
	        		
	        	}
	        	
           	}
	  	
	   	});
		
	}
	$("#formLabel").text(title);
	$("#store_form").modal();
	
}

function initalForm() {
	$("#store_id").val("");
	$("#cost_center_form").val("");
	$("#store_code_form").val("");
	$("#store_name_form").val("");
	$("#store_type_form option:eq(0)").prop("selected", "selected");
	$("#settlement_method_form option:eq(0)").prop("selected", "selected");
	$("#contact_form").val("");
	$("#phone_form").val("");
	$("#address_form").val("");
	$("#company_form").val("");
	$("#invoice_type_form option:eq(0)").prop("selected", "selected");
	$("#invoice_info_form").text("");
	$("#remark_form").val("");
	$("#validity_form").prop("checked", "");
	
}

function refresh() {
	$("#cost_center_query").val("");
	$("#store_code_query").val("");
	$("#store_name_query").val("");
	$("#store_type_query option:eq(0)").prop("selected", "selected");
	$("#store_settlement_method_query option:eq(0)").prop("selected", "selected");
	$("#store_invoice_type_query option:eq(0)").prop("selected", "selected");
	$("#store_validity_query option:eq(0)").prop("selected", "selected");
	find();
	
}

function del() {
	if($("input[name='ckb']:checked").length == 0){
		alert("请选择一行!");
		return;
		
	}
	if(!confirm("是否删除所选店铺?")){
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
      	url: root+ "/control/storeController/del2.do?",
        dataType: "json",
        data: "privIds=" + priv_ids,
        success: function (result) {
        	if(result.result_code == "ERROR"){
				alert(result.result_content);
				
    	   	} else {
    	   		if(parseInt(result.total) - parseInt(result.success) == 0){
    	   			alert("删除成功！");
    	   			
    	   		} else {
    	   			alert("删除成功：" + parseInt(result.success) + "；删除失败：" + (parseInt(result.total) - parseInt(result.success)) + "；失败原因：店铺存在合同！");
    	   			
    	   		}
    	   		
    	   	}
			find();
			
       	}
  	
   	});

}

function keydown_find(event) {
	if(event.keyCode == 13){
		find();
		
	}
	
}

function pageJump() {
	find();
	
}

function find() {
	openIdDiv("store_list", 
		"/BT-LMIS/control/storeController/query2.do?client_id="
		+ $("#id").val()
		+ "&cost_center="
		+ $("#cost_center_query").val()
		+ "&store_code="
		+ $("#store_code_query").val()
		+ "&store_name="
		+ encodeURI($("#store_name_query").val())
		+ "&store_type="
		+ $("#store_type_query").val()
		+ "&settlement_method="
		+ $("#store_settlement_method_query").val()
		+ "&invoice_type="
		+ $("#store_invoice_type_query").val()
		+ "&validity="
		+ $("#store_validity_query").val()
		+ "&startRow="
		+ $("#startRow").val()
		+ "&endRow"
		+ $("#startRow").val()
		+ "&page="
		+ $("#pageIndex").val()
		+ "&pageSize="
		+ $("#pageSize").val()
			
	);
	
}