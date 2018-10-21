$(function(){
	if($("#province").val() == ""){
		$("#city").next().attr("disabled", "disabled");
	}
	if($("#city").val() == ""){
		$("#state").next().attr("disabled", "disabled");
	}
	if($("#state").val() == ""){
		$("#street").next().attr("disabled", "disabled");
	}
	
});

function save(){
	var warehouse_code = $("#physical_warehouse_code").val();
	if(warehouse_code == ""){
		alert("物理仓代码为空！")
		return false;
	}
	var warehouse_name = $("#physical_warehouse_name").val();
	if(warehouse_name == ""){
		alert("物理仓名称为空！")
		return false;
	}
	var province_code = $("#province").val();
	if(province_code == ""){
		alert("请选择到达省！")
		return false;
	}
	$.ajax({
		url : root + "controller/physicalWarehouseController/save.do",	
		type : "post",
       	data : {
       		"id" : $("#id").val(),
	   		"warehouse_code" :　warehouse_code,
	   		"warehouse_name" :　warehouse_name,
	   		"province_code" :　province_code,
	   		"city_code" :　$("#city").val(),
	   		"state_code" :　$("#state").val(),
	   		"street_code" :　$("#street").val()
       	},
       	dataType : "json",
       	success: function (data) {
			if(data.result_code == 'SUCCESS'){
				alert(data.result_content);
				if($("#id").val() == ""){
					$("#id").val(data.id);
					$("#logic_list").show();
				}
    	   	}else if(data.result_code == 'FAILURE'){
    			alert(data.result_content);
    	   	}else{
    			alert("保存异常!");
    	   	}
       	}  
	});
}

function back(){
	openDiv('/BT-LMIS/controller/physicalWarehouseController/list.do');
}

function findDetail(){
	openIdDiv('logic_list', 
		"/BT-LMIS/controller/physicalWarehouseController/divDetailList.do?physical_code=" 
		+ $("#physical_warehouse_code").val()
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

function pageJump() {
	findDetail();
	
}

function delDetail(){
	if(!confirm("是否删除以下所选数据?")){
	  	return;
	  	
	}
	if($("input[name='ckb']:checked").length >= 1){
		var priv_ids =[];
		// 遍历每一个name为priv_id的复选框，其中选中的执行函数
	  	$("input[name='ckb']:checked").each(function(){
	  		// 将选中的值添加到数组priv_ids中
			priv_ids.push($.trim($(this).val()));
			
	  	});
	  	if(!confirm("是否删除所选逻辑仓?")){
		  	return;
		  	
	  	}
	  	$.ajax({
			type: "POST",
           	url: root+"/controller/physicalWarehouseController/delDetail.do?",
           	dataType: "json",
           	data:  "privIds=" + priv_ids,
           	success: function (data) {
				if(data.result_code == 'SUCCESS'){
					alert("删除成功!");
					findDetail();
					
        	   	}else if(data.result_code == 'FAILURE'){
        			alert("删除错误!");
        			
        	   	}else{
        			alert("删除异常!");
        			
        	   	}
				
           	}
	  	
    	});
	  	
	}else{
		alert("请选择一行!");
		
	}
	
}

function initDetail() {
	// 明细ID还原
	$("#detail_id").val("");
	// 逻辑仓选项还原
	initializeSelect("logic_warehouse");
	$("#logic_warehouse").children(":first").siblings().remove();
	$("#logic_warehouse").siblings("ul").children(":first").siblings().remove();
	
}

function openDetail(currentRow){
	var title= "对应逻辑仓关系";
	var id= null;
	if(currentRow != "") {
		// 编辑
		id= currentRow.children(":first").children(":first").val();
		//
		title+= "编辑";
		
	} else {
		title+= "新增";
		
	}
	$.ajax({
		type: "POST",
      	url: "/BT-LMIS/controller/physicalWarehouseController/toDetailForm.do",
        dataType: "json",
        data: {"id": id },
        success: function (result) {
        	content1 = "";
    		content2 = "";
			var logicWarehouses= result.logicWarehouses;
			if(isNull(result.warehouseRelation)) {
				for(var i= 0; i< logicWarehouses.length; i++){
					content1+= '<option value="' + logicWarehouses[i].warehouse_code + '">'　+　logicWarehouses[i].warehouse_name +　'</option>';
					content2+= '<li class="m-list-item" data-value="' + logicWarehouses[i].warehouse_code + '">' + logicWarehouses[i].warehouse_name + '</li>';
					
				}
				$("#logic_warehouse option:eq(0)").after(content1);
				$("#logic_warehouse").siblings("ul").children(":first").after(content2);
				
			} else {
				// 清除已选择
				$("#logic_warehouse").siblings("ul").children(":first").removeClass("m-list-item-active");
				//
				$("#logic_warehouse").val(result.warehouseRelation.logic_code);
				for(var i= 0; i< logicWarehouses.length; i++){
					content1+= '<option value="' + logicWarehouses[i].warehouse_code + '" ';
					content2+= '<li class="m-list-item';
					if(logicWarehouses[i].warehouse_code == result.warehouseRelation.logic_code) {
						content1+= 'selected= "selected" ';
						content2+= ' m-list-item-active';
						$("#logic_warehouse").next().val(logicWarehouses[i].warehouse_name);
						$("#logic_warehouse").next().attr("placeholder", logicWarehouses[i].warehouse_name);
						
					}
					content1+= '>'　+　logicWarehouses[i].warehouse_name +　'</option>';
					content2+= '" data-value="' + logicWarehouses[i].warehouse_code + '">' + logicWarehouses[i].warehouse_name + '</li>';
					
				}
        		// 装上之后的值
				$("#logic_warehouse option:eq(0)").after(content1);
				$("#logic_warehouse").siblings("ul").children(":first").after(content2);
				//
				$("#detail_id").val(result.warehouseRelation.id);
				
			}
        	
        }
        
	});
	//
	$("#lwTitle").text(title);
	//
	$("#logic_warehosue_form").modal({backdrop: "static", keyboard: false})

}

function saveDetail(formId){
	if(!checkValues(formId)) {
		return;
		
	}
	if(!confirm("是否保存当前关系？")) {
		return;
		
	}
	$.ajax({
		url: root + "controller/physicalWarehouseController/saveDetail.do",	
		type: "post",
       	data: {
       		"id": $("#detail_id").val(),
       		"physical_code": $("#physical_warehouse_code").val(),
	   		"logic_code": $("#logic_warehouse").val()
       	},
       	dataType: "json",
       	success: function (data) {
       		if(data.result_code == 'SUCCESS'){
				alert(data.result_content);
				$("#logic_warehosue_form").modal("hide");
				initDetail();
				findDetail();
				
    	   	}else if(data.result_code == 'FAILURE'){
    			alert(data.result_content);
    			
    	   	}else{
    			alert("保存异常!");
    			
    	   	}
			
       	} 
       	
	});
	
}