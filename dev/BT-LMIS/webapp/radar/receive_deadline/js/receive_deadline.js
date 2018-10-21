$(function(){
	if($("#express").val() == ""){
		$("#product_type").next().attr("disabled", "disabled");
	}
	if($("#destination_province").val() == ""){
		$("#destination_city").next().attr("disabled", "disabled");
	}
	if($("#destination_city").val() == ""){
		$("#destination_state").next().attr("disabled", "disabled");
	}
	if($("#destination_state").val() == ""){
		$("#destination_street").next().attr("disabled", "disabled");
	}
});

function toForm(){
	openDiv('/BT-LMIS/controller/receiveDeadlineController/toForm.do');
}

function del(){
	if($("input[name='ckb']:checked").length >= 1){
		var priv_ids =[];
		// 遍历每一个name为priv_id的复选框，其中选中的执行函数
	  	$("input[name='ckb']:checked").each(function(){
	  		// 将选中的值添加到数组priv_ids中
			priv_ids.push($.trim($(this).val()));	   
	  	});
	  	if(!confirm("是否删除所选揽件截止时间?")){
		  	return;
	  	}
	  	$.ajax({
			type: "POST",
           	url: root+"/controller/receiveDeadlineController/del.do?",
           	dataType: "json",
           	data:  "privIds=" + priv_ids,
           	success: function (data) {
				if(data.result_code == 'SUCCESS'){
					alert("删除成功!");
					find();
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

function pageJump() {
	find();
};

function find(){
	openIdDiv(
			"partDiv",
			"/BT-LMIS/controller/receiveDeadlineController/divList.do?express_code=" 
					+ $("#express").val()
					+ "&producttype_code=" 
					+ $("#product_type").val() 
					+ "&warehouse_code="
					+ $("#warehouse").val()
					+ "&province_code=" 
					+ $("#destination_province").val()
					+ "&city_code="
					+ $("#destination_city").val() 
					+ "&state_code="
					+ $("#destination_state").val()
					+ "&street_code="
					+ $("#destination_street").val()
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

function submitBatch(){
	
	$.post('/BT-LMIS/controller/receiveDeadlineController/reFresh.do',function(data){
		if(data.code==1){
			alert('提交成功');
		}else{
			alert('提交失败');
		}
		
		
	})
	
}


function shiftExpress(){
	var express_code= $("#express").val();
	if(express_code == ""){
		$("#product_type").children(":first").siblings().remove();
		$("#product_type").next().attr("disabled", "disabled");
	} else {
		$.ajax({
			url: root+ "/controller/receiveDeadlineController/getProductType.do",
			type: "post",
			data: {"express_code": express_code },
			dataType: "json",
			success: function(result) {
				$("#product_type").next().attr("disabled", false);
				$("#product_type").children(":first").siblings().remove();
				$("#product_type").siblings("ul").children(":first").siblings().remove();
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
				$("#product_type option:eq(0)").after(content1);
				$("#product_type").siblings("ul").children(":first").after(content2);
				
			},
			error: function(result) {
				alert(result);
				
			}
			
		});
		
	}
	$("#product_type").next().val("");
	$("#product_type").next().attr("placeholder", "---请选择---");
	$("#product_type").siblings("ul").children(":first").addClass("m-list-item-active");
}