$(function(){
	
});

function toForm(){
	openDiv('/BT-LMIS/controller/physicalWarehouseController/toForm.do');
}

function pageJump() {
	find();
};

function del(){
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
	  	if(!confirm("是否删除所选物理仓记录?")){
		  	return;
	  	}
	  	$.ajax({
			type: "POST",
           	url: root+"/controller/physicalWarehouseController/del.do?",
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

function find(){
	openIdDiv(
			"partDiv",
			"/BT-LMIS/controller/physicalWarehouseController/list.do?warehouse_code=" 
					+ $("#physicalWarehouse_code").val()
					+ "&warehouse_name=" 
					+ $("#physicalWarehouse_name").val()
					+ "&flag='1'&startRow="
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
	
	$.post('/BT-LMIS/controller/physicalWarehouseController/refresh.do',function (data){
		if(data.code==1){
			alert('提交成功！！');
		}else{
			alert('提交失败！！');
		}
		
	});
	
	
}
