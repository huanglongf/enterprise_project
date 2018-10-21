$(function(){
	$("#receive_deadline_input").timepicker({
		minuteStep: 1,
		showSeconds: true,
		showMeridian: false
	}).next().on(ace.click_event, function(){
		$(this).prev().focus();
	});
	if($("#node_province").val() == ""){
		$("#node_city").next().attr("disabled", "disabled");
	}
	if($("#node_city").val() == ""){
		$("#node_state").next().attr("disabled", "disabled");
	}
	if($("#node_state").val() == ""){
		$("#node_street").next().attr("disabled", "disabled");
	}
	if($("#id").val() != ""){
		$("#timeliness_detail_list").show();
	}
});

function move(id, action){
	var str = "";
	if(action == 'up'){
		str = "上移";
	} else if(action = 'down') {
		str = "下移";
	}
	if(!confirm("是否" + str + "所选时效明细节点?")){
	  	return;
  	}
	$.ajax({
		type: "POST",
       	url: root+"/controller/receiveDeadlineController/moveDetail.do?",
       	dataType: "json",
       	data:  {
       		"pid" : $("#id").val(),
       		"id" : id,
       		"action" : action
       		},
       	success: function (data) {
			if(data.result_code == 'SUCCESS'){
				alert(data.result_content);
				openIdDiv('timeliness_detail_list', 
						"/BT-LMIS/controller/receiveDeadlineController/divDetailList.do?pid=" 
						+ $("#id").val()
						+ "&startRow="
						+ $("#startRow").val()
						+ "&endRow=" 
						+ $("#startRow").val()
						+ "&page="
						+ $("#pageIndex").val() 
						+ "&pageSize="
						+ $("#pageSize").val()
						);
    	   	} else if (data.result_code == 'FAILURE'){
    			alert(result_content);
    	   	} else {
    			alert(result_content);
    	   	}
       	}  
	});
}

function shiftDetailStatus(id, status){
	var str = "";
	if(status == '0'){
		str = "停用";
	} else if(status = '1') {
		str = "启用";
	}
	if(!confirm("是否" + str + "所选时效明细?")){
	  	return;
  	}
	$.ajax({
		type: "POST",
       	url: root+"/controller/receiveDeadlineController/pauseOrStartDetail.do?",
       	dataType: "json",
       	data:  {
       		"id" : id,
       		"flag" : status
       		},
       	success: function (data) {
			if(data.result_code == 'SUCCESS'){
				alert(data.result_content);
				openIdDiv("timeliness_detail_list", 
						"/BT-LMIS/controller/receiveDeadlineController/divDetailList.do?pid=" 
						+ $("#id").val()
						+ "&startRow="
						+ $("#startRow").val()
						+ "&endRow=" 
						+ $("#startRow").val()
						+ "&page="
						+ $("#pageIndex").val() 
						+ "&pageSize="
						+ $("#pageSize").val()
						);
    	   	} else if (data.result_code == 'FAILURE'){
    			alert(result_content);
    	   	} else {
    			alert(result_content);
    	   	}
       	}  
	});
}

function saveDetail(pid){
	var province_code = $("#node_province").val();
	/*if(province_code == ""){
		alert("请选择到达省！");
		return false;
	}*/
	var efficiency = $("#efficiency").val();
	if(efficiency == ""){
		alert("请填写时效！");
		return false;
	}
	if(isNaN(efficiency) || efficiency < 0){
		alert("时效填写不合法！");
		return false;
	}
	var efficiency_unit = $("#efficiency_unit").val();
	if(efficiency_unit == 1 &&　parseInt(efficiency) != efficiency){
		// 单位为天不能填写小数
		alert("单位为天不能填写小数！请将单位改为小时！");
		return false;
	}
	var warningtype_code = $("#warningtype_code").val();
	if(warningtype_code == ""){
		alert("请填写预警类型！");
		return false;
	}
	$.ajax({
		url : root + "controller/receiveDeadlineController/saveDetail.do",	
		type : "post",
       	data : {
       		"id" : $("#detail_id").val(),
       		"pid" : pid,
	   		"province_code" :　province_code,
	   		"city_code" :　$("#node_city").val(),
	   		"state_code" :　$("#node_state").val(),
	   		"street_code" :　$("#node_street").val(),
	   		"efficiency" : efficiency,
	   		"efficiency_unit" : efficiency_unit,
	   		"warningtype_code" : warningtype_code
       	},
       	dataType : "json",
       	success: function (data) {
			if(data.result_code == 'SUCCESS'){
				alert(data.result_content);
				backDetail($("#id").val());
    	   	}else if(data.result_code == 'FAILURE'){
    			alert(data.result_content);
    	   	}else{
    			alert("保存异常!");
    	   	}
       	}  
	});
}

function backDetail(pid){
	openDiv("/BT-LMIS/controller/receiveDeadlineController/toForm.do?pid=" + pid);
	hidediv();
}

function openDetail(){
	showDialogPage(
			'500',
			root+'/controller/receiveDeadlineController/toDetailForm.do?pid=' + $("#id").val())
}

function delDetail(id){
	if(!confirm("是否删除所选时效明细？")){
	  	return;
  	}
	$.ajax({
		type: "POST",
       	url: root+"/controller/receiveDeadlineController/delDetail.do?",
       	dataType: "json",
       	data:  {"id" : id},
       	success: function (data) {
			if(data.result_code == 'SUCCESS'){
				alert("删除成功！");
				openIdDiv('timeliness_detail_list', 
						"/BT-LMIS/controller/receiveDeadlineController/divDetailList.do?pid=" 
						+ $("#id").val()
						+ "&startRow="
						+ $("#startRow").val()
						+ "&endRow=" 
						+ $("#startRow").val()
						+ "&page="
						+ $("#pageIndex").val() 
						+ "&pageSize="
						+ $("#pageSize").val()
						);
    	   	}else if(data.result_code == 'FAILURE'){
    			alert("删除错误!");
    	   	}else{
    			alert("删除异常!");
    	   	}
       	}  
	});
}

function save(){
	var express_code = $("#express").val();
	if(express_code == ""){
		alert("请选择物流服务商！")
		return false;
	}
	var producttype_code = $("#product_type").val();
	if(express_code == 'SF' && producttype_code == ""){
		alert("请选择产品类型！")
		return false;
	}
	var warehouse_code = $("#warehouse").val();
	if(warehouse_code == ""){
		alert("请选择揽件仓库！")
		return false;
	}
	var embrace_time = $("#receive_deadline_input").val();
	if(embrace_time == ""){
		alert("请选择揽件截止时间！")
		return false;
	}
	var province_code = $("#destination_province").val();
	if(province_code == ""){
		alert("请选择到达省！")
		return false;
	}
	$.ajax({
		url : root + "controller/receiveDeadlineController/save.do",	
		type : "post",
       	data : {
       		"id" : $("#id").val(),
	   		"express_code" :　express_code,
	   		"producttype_code" :　producttype_code,
	   		"warehouse_code" :　warehouse_code,
	   		"embrace_time" :　embrace_time,
	   		"province_code" :　province_code,
	   		"city_code" :　$("#destination_city").val(),
	   		"state_code" :　$("#destination_state").val(),
	   		"street_code" :　$("#destination_street").val()
       	},
       	dataType : "json",
       	success: function (data) {
			if(data.result_code == 'SUCCESS'){
				alert(data.result_content);
				if($("#id").val() == ""){
					$("#id").val(data.id);
					$("#timeliness_detail_list").show();
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
	openDiv('/BT-LMIS/controller/receiveDeadlineController/list.do');
}