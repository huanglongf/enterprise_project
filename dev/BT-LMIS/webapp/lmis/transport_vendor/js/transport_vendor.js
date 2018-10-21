function saveTransportVendor(){
	if ($("#transport_code").val().match(/^[a-zA-Z0-9]{1,20}$/) == null){
		alert("物流商编码非法！");
		return;
		
	}
	if($("#transport_name").val().length< 1) {
		alert("物流商名称为空！");
		return;
		
	}
	if($("#transport_name").val().length> 25) {
		alert("物流商名称过长！");
		return;
		
	}
	if ($("#contact").val().length< 1) {
		alert("联系人为空！");
		return;
		
	}
	if ($("#contact").val().length> 10) {
		alert("联系人过长！");
		return;
		
	}
	if ($("#phone").val().match(/^1(3|4|5|7|8)\d{9}$/) == null && $("#phone").val().match(/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/) == null){
		alert("联系电话非法！");
		return;
		
	}
	if($("#transport_type").val() == -1){
		alert("请选择物流商类型！")
		return;
		
	}
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/transportVendorController/save.do",
        dataType: "json",
        async: false,
        data: {
        	"id": $("#transportVendor_id").val(),
        	"transport_code": $("#transport_code").val(),
        	"transport_name": $("#transport_name").val(),
        	"contact": $("#contact").val(),
        	"phone": $("#phone").val(),
        	"transport_type": $("#transport_type").val(),
        	"validity": isChecked("validity")
        	
        },
        success: function(result) {  
        	alert(result.result_content);
        	if(result.result_code == "SUCCESS"){
        		openDiv("/BT-LMIS/control/transportVendorController/query.do");
        		
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
	if(!confirm("确定删除所选物流商吗?")){
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
       	url: root+ "/control/transportVendorController/del.do?",
       	dataType: "json",
       	data: "privIds="+ priv_ids,
       	success: function(result){
       		if(result.result_code == "ERROR"){
				alert(result.result_content);
				
    	   	} else {
    	   		if(parseInt(result.total) - parseInt(result.success) == 0){
    	   			alert("删除成功！");
    	   			
    	   		} else {
    	   			alert("删除成功：" + parseInt(result.success) + "；删除失败：" + (parseInt(result.total) - parseInt(result.success)) + "；失败原因：物流商存在合同！");
    	   			
    	   		}
    	   		
    	   	}
			find();
			
       	}
  	
	});
	
}

function pageJump() {
	find();
	
}
	
function find() {
	openDiv("/BT-LMIS/control/transportVendorController/query.do?transport_code="
		+ $("#transport_code").val()
		+ "&transport_name="
		+ $("#transport_name").val()
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