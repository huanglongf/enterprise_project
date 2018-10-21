function saveProductType(){
	if ($("#product_type_code_form").val().length< 1) {
		alert("产品类型编码为空！");
		return;
		
	}
	if ($("#product_type_code_form").val().length> 25) {
		alert("产品类型编码过长！");
		return;
		
	}
	if ($("#product_type_name_form").val().length< 1) {
		alert("产品类型名称为空！");
		return;
		
	}
	if ($("#product_type_name_form").val().length> 25) {
		alert("产品类型名称过长！");
		return;
		
	}
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/transportProductTypeController/save.do",
        dataType: "json",
        async: false,
        data: {
        	"id": $("#product_type_id").val(),
        	"vendor_code": $("#transport_code").val(),
        	"product_type_code": $("#product_type_code_form").val(),
        	"product_type_name": $("#product_type_name_form").val(),
        	"status": isChecked($("#status_form").val())
        	
        },
        success: function(result) {  
        	alert(result.result_content);
        	if(result.result_code == "SUCCESS"){
        		$("#product_type_form").modal("hide");
        		find();
        		
			} else if(result.result_code == "ERROR") {
				
				
			}
        	
        }
        
	}); 
	
}

function toProductTypeForm(current_row) {
	initalProductTypeForm();
	var title= "店铺";
	if(current_row == ""){
		title += "新增";
		
	} else {
		title += "编辑";
		$.ajax({
			type: "POST",
          	url: "/BT-LMIS/control/transportProductTypeController/getProductType.do",
	        dataType: "json",
	        data: {"product_type_id": current_row.children(":first").children(":first").val()},
	        async: false,
	        success: function (result) {
	        	var product_type= result.product_type;
	        	$("#product_type_id").val(product_type.id);
	        	$("#product_type_code_form").val(product_type.product_type_code);
	        	$("#product_type_name_form").val(product_type.product_type_name);
	        	if(product_type.status == true){
	        		$("#status_form").prop("checked", "checked");
	        		
	        	}
	        	
           	}
	  	
	   	});
		
	}
	$("#product_type_formLabel").text(title);
	$("#product_type_form").modal();
	
}

function initalProductTypeForm() {
	$("#product_type_id").val("");
	$("#product_type_code_form").val("");
	$("#product_type_name_form").val("");
	$("#status_form").prop("checked", "");
	
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
      	url: "/BT-LMIS/control/transportProductTypeController/del.do?",
        dataType: "json",
        data: "privIds=" + priv_ids,
        success: function (result) {
        	alert(result.result_content);
			find();
			
       	}
  	
   	});

}

function refresh() {
	$("#product_type_code_query").val("");
	$("#product_type_name_query").val("");
	$("#status_query option:eq(0)").prop("selected", "selected");
	find();
	
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
	openIdDiv("product_type", 
		"/BT-LMIS/control/transportProductTypeController/query.do?vendor_code="
		+ $("#transport_code").val()
		+ "&product_type_code="
		+ $("#product_type_code_query").val()
		+ "&product_type_name="
		+ encodeURI($("#product_type_name_query").val())
		+ "&status="
		+ $("#status_query").val()
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