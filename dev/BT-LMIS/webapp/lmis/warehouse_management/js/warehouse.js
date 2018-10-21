function save(){
	var msg = "";
	var id = $("#id").val();
	if(id == ""){
		msg = "创建";
	} else {
		msg = "更新";
	}
	var warehouse_code = $("#warehouse_code").val();
	if(warehouse_code == ""){
		alert("逻辑仓代码不能为空！");
		return false;
	}
	var warehouse_name = $("#warehouse_name").val();
	if(warehouse_name == ""){
		alert("逻辑仓名称不能为空！");
		return false;
	}
	var province = $("#province").val();
	if(province == ""){
		alert("所在地省不能为空！");
		return false;
	}
	var need_balance = $("#need_balance").val();
	if(need_balance == ""){
		alert("是否需要结算不能为空！");
		return false;
	}
	var province_name =  $("#province option[value='" + province + "']").text();

	var city = $("#city").val();
	var state = $("#state").val();
	var street = $("#street").val();
	var city_name =  '';
	var state_name =  '';
	var street_name =  '';
	if(city != null && city != ''){
		city_name =  $("#city option[value='" + city + "']").text();
	}
	if(state != null && state != ''){
		state_name =  $("#state option[value='" + state + "']").text();
	}
	if(street != null && street != ''){
		street_name =  $("#street option[value='" + street + "']").text();
	}
	$.ajax({
		type : "post",
		url: root + "/control/warehouseController/save.do",
		data: {
			id: id,
			warehouse_code: warehouse_code,
			warehouse_name: warehouse_name,
			province: province,
			city: city,
			state: state,
			street: street,
			province_name: province_name,
			city_name: city_name,
			state_name: state_name,
			street_name: street_name,
			address: $("#address").val(),
			contact: $("#contact").val(),
			phone: $("#phone").val(),
			warehouse_type: $("#warehouse_type").val(),
			need_balance : $("#need_balance").val()
		},
		dataType: "json",  
		success: function(result) {
			if(result.result_code == 'SUCCESS'){
				alert(msg + "成功！");
				back();
			} else if(result.result_code == 'FAILURE'){
				alert(msg + "失败！失败原因：" + result.failure_reason);
			}
		},
		error: function(result) {
			alert(result);
		}
	});
}
function back(){
	openDiv('/BT-LMIS/control/warehouseController/list.do');
}
function toForm(){
	if($("input[name='ckb']:checked").length >= 1){
		if($("input[name='ckb']:checked").length>1){
			alert("只能选择一行!");
	  	}else{
	  		// 修改
	  		openDiv('/BT-LMIS/control/warehouseController/form.do?id='+$("input[name='ckb']:checked").val());
	  	}
	}else{
		// 新增
		openDiv('/BT-LMIS/control/warehouseController/form.do');
	}
}

function toForm_add(){
	openDiv('/BT-LMIS/control/warehouseController/form.do');
}

function toForm_update(){
	if($("input[name='ckb']:checked").length >= 1){
		if($("input[name='ckb']:checked").length>1){
			alert("只能选择一行!");
	  	}else{
	  		// 修改
	  		openDiv('/BT-LMIS/control/warehouseController/form.do?id='+$("input[name='ckb']:checked").val());
	  	}
	}else{
		alert("请选择一行记录");
	}
}


function shiftStatus(id, validity){
	var msg = '';
	if(validity == '0'){
		msg = '停用';
	} else {
		msg = '启用';
	}
	if(!confirm("是否" + msg + "？")){
		return false;
	} else {
		$.ajax({
			type : "POST",
			url: "/BT-LMIS/control/warehouseController/save.do",
			data: {
				"id" : id,
				"validity" : validity
				},
			dataType: "json",  
			success: function(result) {
				if(result.result_code == 'SUCCESS'){
					alert(msg + "成功！");
					query();
				} else if(result.result_code == 'FAILURE'){
					alert(msg + "失败！失败原因：" + result.failure_reason);
				}
			},
			error: function(result) {
				alert(result);
			}
		});
	}
}
function pageJump() {
	query();
}
function query(){
	  openDiv("/BT-LMIS/control/warehouseController/list.do?startRow="
		  + $("#startRow").val()
		  + "&endRow="
		  + $("#startRow").val()
		  + "&page="
		  + $("#pageIndex").val()
		  + "&pageSize="
		  + $("#pageSize").val()
		  + "&warehouse_name="
		  + $("#warehouse_name").val());
}
function toForm_download(){
	loadingStyle();
	$.post("/BT-LMIS/control/warehouseController/download.do?warehouse_name="+$("#warehouse_name").val(),function(data){
     if(data.code==1){
    	 alert('操作成功！');
    	 cancelLoadingStyle();
    	 window.open(root +'/DownloadFile/'+ data.path);
     }else{
    	 cancelLoadingStyle();
    	 alert('操作失败！'+data.msg);
     }		
	});  
	
	
}

