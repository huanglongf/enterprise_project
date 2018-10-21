function toExport() {
	$("#btn_export").attr("disabled", true);
	$.ajax({
		type: "POST",
		url: "/BT-LMIS/control/addressController/export.do",  
		data: {
			"contract_id": $("#contract_id").val(),
			"carrier_code": $("#carrier_code").val(),
			"itemtype_code": $("#itemtype_code").val(),
			// "origination": $("#origination").val(),
            "province_destination": $("#province_destination").val() == ""? "": $("#province_destination").find("option:selected").text(),
            "city_destination": $("#city_destination").val() == ""? "": $("#city_destination").find("option:selected").text(),
            "district_destination": $("#district_destination").val() == ""? "": $("#district_destination").find("option:selected").text(),
			"province_origin": $("#province_origin").val() == ""? "": $("#province_origin").find("option:selected").text(),
			"city_origin": $("#city_origin").val() == ""? "": $("#city_origin").find("option:selected").text(),
			"state_origin": $("#state_origin").val() == ""? "": $("#state_origin").find("option:selected").text(),
			
		},
		dataType: "",  
		success: function(jsonStr) {
			window.open(root+ jsonStr);
			$("#btn_export").attr("disabled", false);
			
		}
	
	});
	
}

function toForm() {
	if($("input[name='ckb']:checked").length>= 1){
		if($("input[name='ckb']:checked").length> 1){
			alert("只能选择一行!");
			
	  	}else{
	  		openDiv("/BT-LMIS/control/addressController/toForm.do?id="+ $("input[name='ckb']:checked").val());
	  		
	  	}
		
	} else {
		alert("请选择一行!");
		
	}
	
}

function upStatus(id, status) {
	if(!confirm("是否确认此次操作?")){
		return;
		
	}
    $.ajax({
		type: "POST",
		url: root+ "/control/addressController/updateMainData.do?status="+ status+ "&id="+ id,  
		data: "",
		dataType: "json",  
		success: function(jsonStr) {
			alert(jsonStr.result_reason);
			query();
			
		}
    
    });
    
}

function refresh() {
	$("#contract_id").siblings("input").val("");
	$("#contract_id").siblings("input").prop("placeholder", "---请选择---");
	$("#contract_id").siblings("ul").children().removeClass("m-list-item-active");
	$("#contract_id").siblings("ul").children(":first").addClass("m-list-item-active");
	$("#contract_id option:eq(0)").prop("selected", "selected");
	
	$("#carrier_code").siblings("input").val("");
	$("#carrier_code").siblings("input").prop("placeholder", "---请选择---");
	$("#carrier_code").siblings("ul").children(":first").addClass("m-list-item-active");
	$("#carrier_code option:eq(0)").prop("selected", "selected");
	
	shiftCarrier();
	
	// $("#origination").siblings("input").val("");
	// $("#origination").siblings("input").prop("placeholder", "---请选择---");
	// $("#origination").siblings("ul").children(":first").addClass("m-list-item-active");
	// $("#origination option:eq(0)").prop("selected", "selected");
    $("#province_origin").siblings("input").val("");
    $("#province_origin").siblings("input").prop("placeholder", "---请选择---");
    $("#province_origin").siblings("ul").children(":first").addClass("m-list-item-active");
    $("#province_origin option:eq(0)").prop("selected", "selected");
    shiftAreaLocal(1, 'province_origin', 'city_origin', 'state_origin');

	$("#province_destination").siblings("input").val("");
	$("#province_destination").siblings("input").prop("placeholder", "---请选择---");
	$("#province_destination").siblings("ul").children(":first").addClass("m-list-item-active");
	$("#province_destination option:eq(0)").prop("selected", "selected");
	
	shiftAreaLocal(1, 'province_destination', 'city_destination', 'district_destination');
	query();
	
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
      	url: root+ "/control/addressController/del.do?",
        dataType: "json",
        data: "privIds=" + priv_ids,
        success: function (result) {
        	alert(result.result_content);
        	if(result.result_code == "SUCCESS"){
        		query();
				
    	   	}
			
       	}
  	
   	});

}

function pageJump() {
	query();
	
}

function query() {
	var query=
		"/BT-LMIS/control/addressController/queryList.do?contract_id="
		+ $("#contract_id").val()
		+ "&carrier_code="
		+ $("#carrier_code").val()
		+ "&itemtype_code="
		+ $("#itemtype_code").val()
		// + "&origination="
		// + $("#origination").val()
		+ "&province_destination=";
	if($("#province_destination").val() != "") {
		query+= $("#province_destination").find("option:selected").text();
		
	}
	query+= "&city_destination=";
	if($("#city_destination").val() != "") {
		query+= $("#city_destination").find("option:selected").text();
		
	}
	query+= "&district_destination="
	if($("#district_destination").val() != "") {
		query+= $("#district_destination").find("option:selected").text();
	}
    query+= "&province_origin="
    if($("#province_origin").val() != "") {
        query+= $("#province_origin").find("option:selected").text();
    }
    query+= "&city_origin=";
    if($("#city_origin").val() != "") {
        query+= $("#city_origin").find("option:selected").text();
    }
    query+= "&state_origin="
    if($("#state_origin").val() != "") {
        query+= $("#state_origin").find("option:selected").text();
    }
	query+=
		"&startRow="
		+ $("#startRow").val()
		+ "&endRow="
		+ $("#startRow").val()
		+ "&page="
		+ $("#pageIndex").val()
		+ "&pageSize="
		+ $("#pageSize").val()
	openIdDiv("list", query);
  	
}

function shiftAreaLocal(level, provinceId, cityId, stateId){
	if(level == 1){
		pCSSCascadeFuzzyQuery(provinceId, cityId);
		
	}
	pCSSCascadeFuzzyQuery(cityId, stateId);
	
}

function shiftCarrier() {
	var vendor_code= $("#carrier_code").val();
	if(vendor_code == ""){
		$("#itemtype_code").children(":first").siblings().remove();
		$("#itemtype_code").next().attr("disabled", "disabled");
	} else {
		$.ajax({
			url: "/BT-LMIS/control/transportProductTypeController/getProductTypeByTranportVendor.do",
			type: "post",
			data: {"vendor_code" : vendor_code },
			dataType: "json",
			success: function(result) {
				$("#itemtype_code").next().attr("disabled", false);
				$("#itemtype_code").children(":first").siblings().remove();
				$("#itemtype_code").siblings("ul").children(":first").siblings().remove();
				var content1= '';
				var content2= '';
				for(var i= 0; i< result.product_type.length; i++){
					content1+= 
						'<option value="' 
						+ result.product_type[i].product_type_code 
						+ '">'　
						+　result.product_type[i].product_type_name 
						+　'</option>'
					content2+= 
						'<li class="m-list-item" data-value="'
						+ result.product_type[i].product_type_code
						+ '">'
						+ result.product_type[i].product_type_name
						+ '</li>'
						
				}
				$("#itemtype_code option:eq(0)").after(content1);
				$("#itemtype_code").siblings("ul").children(":first").after(content2);
				
			},
			error: function(result) {
				alert(result);
				
			}
			
		});
		
	}
	$("#itemtype_code").next().val("");
	$("#itemtype_code").next().attr("placeholder", "---请选择---");
	$("#itemtype_code").siblings("ul").children(":first").addClass("m-list-item-active");
	
}

$(function(){
	if($("#carrier_code").val() == ""){
		$("#itemtype_code").next().attr("disabled", "disabled");
		
	}
	if($("#province_destination").val() == ""){
		$("#city_destination").next().attr("disabled", "disabled");
		
	}
	if($("#city_destination").val() == ""){
		$("#district_destination").next().attr("disabled", "disabled");
		
	}
    if($("#province_origin").val() == ""){
        $("#city_origin").next().attr("disabled", "disabled");

    }
    if($("#city_origin").val() == ""){
        $("#state_origin").next().attr("disabled", "disabled");

    }
});