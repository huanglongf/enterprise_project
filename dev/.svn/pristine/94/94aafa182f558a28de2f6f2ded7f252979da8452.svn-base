function toStorePowerForm(row) {
	$.ajax({  
        type: "POST",  
        url: root+"/control/groupController/getStorePower.do",  
        dataType: "json",   
        data: {id: row.children(":first").text()},
        success : function (result) {
        	var sp= result.storePower;
        	var store= result.store;
        	$("#sp_id").val(sp.id);
        	if(sp.selfwarehouse == 1) {
        		$("#selfwarehouse").prop("checked", true);
        		
        	}
        	if(sp.outsourcedwarehouse == 1) {
        		$("#outsourcedwarehouse").prop("checked", true);
        		
        	}
        	//
        	initializeSelect("stores");
        	$("#store").children(":first").siblings().remove();
        	$("#store").siblings("ul").children(":first").siblings().remove();
        	//
        	$("#store").val(sp.store);
        	var content1= "";
        	var content2= "";
        	for(var i= 0; i< store.length; i++){
				content1+= '<option value="' + store[i].store_code + '" ';
				content2+= '<li class="m-list-item';
				if(store[i].store_code == sp.store) {
					content1+= 'selected= "selected" ';
					content2+= ' m-list-item-active'
					$("#store").next().val(store[i].store_name);
					$("#store").next().attr("placeholder", store[i].store_name);
					
				}
				content1+= '>'　+　store[i].store_name +　'</option>';
				content2+= '" data-value="' + store[i].store_code + '">' + store[i].store_name + '</li>';
				
			}
    		// 装上之后的值
			$("#store option:eq(0)").after(content1);
			$("#store").siblings("ul").children(":first").after(content2);
        	// 弹窗
        	$("#storePower_form").modal();
            
        }
        
	});
	
}
function saveStorePower() {
	if(!confirm("是否保存店铺权限？")) {
		return;
		
	}
	if(!isChecked("selfwarehouse") && !isChecked("outsourcedwarehouse")) {
		alert("至少选择一种仓库类型！");
		return;
		
	}
	if($("#store").val() == "") {
		alert("请选择店铺！");
		return;
		
	}
	$.ajax({
		type: "POST",  
        url: root+"/control/groupController/saveStorePower.do",  
        dataType: "json",   
        data: {
        	id: $("#sp_id").val(),
        	group: $("#id").val(),
        	selfwarehouse: isChecked("selfwarehouse"),
        	outsourcedwarehouse: isChecked("outsourcedwarehouse"),
        	store: $("#store").val()
     	  
        },
        success : function (result) {  
            alert(result.result_content);
            if(result.result_code == "SUCCESS") {
            	// 初始化弹窗
            	initStorePower();
            	// 隐藏弹窗
            	$("#storePower_form").modal("hide");
            	// 刷新列表
            	openIdDiv("store_power", "/BT-LMIS/control/groupController/qeuryStorePower.do?id=" + $("#id").val());
         	   
            }
            
        }
        
	});
	
}
function initStorePower() {
	$("#selfwarehouse").prop("checked", false);
	$("#outsourcedwarehouse").prop("checked", false);
	initializeSelect("store");
	
}
function addPower(type) {
	$("#" + type + "Power_form").modal();
	
}
function delPower(type, id) {
	if(!confirm("是否删除？")) {
		return;
		
	}
	if(type == "sp") {
		$.ajax({  
	        type: "POST",  
	        url: root+"/control/groupController/delStorePower.do",  
	        dataType: "json",   
	        data: {id: id, dFlag: false},
	        success : function (result) {  
	            alert(result.result_content);
	            if(result.result_code == "SUCCESS") {
	         	   openIdDiv("store_power", "/BT-LMIS/control/groupController/qeuryStorePower.do?id=" + $("#id").val());
	         	   
	            }
	            
	        }
	        
		});
		
	} else if(type == "wp") {
		$.ajax({  
	        type: "POST",  
	        url: root+"/control/groupController/delWorkPower.do",  
	        dataType: "json",   
	        data: {id: id, dFlag: false},
	        success : function (result) {  
	            alert(result.result_content);
	            if(result.result_code == "SUCCESS") {
	         	   openIdDiv("work_power", "/BT-LMIS/control/groupController/qeuryWorkPower.do?id=" + $("#id").val());
	         	   
	            }
	            
	        }
	        
		});
		
	}
	
}
function save() {
	if(!confirm("是否保存当前数据？")) {
		return;
		
	}
	if($('#group_code').val() == "") {
		alert("组别编码不能为空！");
		return;
		
	}
	if($("#group_name").val() == "") {
		alert("组别名称不能为空！");
		return;
		
	}
	if($("#remark").val() > 200) {
		alert("说明过长！");
		return;
		
	}
	$.ajax({  
           type: "POST",  
           url: root+"/control/groupController/saveGroup.do",  
           dataType: "json",   
           data: {
        	   id: $("#id").val(),
        	   group_code: $("#group_code").val(),
        	   group_name: $("#group_name").val(),
        	   superior: $("#superior").val(),
        	   autoAllocFlag: isChecked("autoAllocFlag"),
        	   autoAllocFlag: isChecked("status"),
        	   remark: $("#remark").val(),
        	   
           },
           success : function (result) {  
               alert(result.result_content);
               if(result.result_code == "SUCCESS") {
            	   openDiv("/BT-LMIS/control/groupController/list.do?queryType=init");
            	   
               }
               
           }
	
    });
	
}

/**
* 跳转修改页面Form
*/
function up(row){
	openDiv("/BT-LMIS/control/groupController/toForm.do?id=" + row.children(":first").children(":first").val());
	
}
  
/**
* 批量删除
*/
function del(toid, ret) {
	if(!confirm("是否删除所选组别?")) {
	  	return;
	  	
  	}
	if($("input[name='ckb']:checked").length >= 1) {
		var priv_ids =[];
		// 遍历每一个name为priv_id的复选框，其中选中的执行函数
	  	$("input[name='ckb']:checked").each(function(){
	  		// 将选中的值添加到数组priv_ids中
			priv_ids.push($.trim($(this).val()));	   
	  	});
	  	$.ajax({
			type: "POST",
           	url: root+"/control/groupController/delGroups.do?",
           	dataType: "json",
           	data:  "privIds=" + priv_ids,
           	success: function (result) {
           		alert(result.result_content)
           		find();
	      			
           	}
	  	
    	});
	  	
	} else {
		alert("请选择一行!");
		
	}
	
}
  
  	/**
* 修改组别状态
*/
function upStatus(id, status){
	var str= "";
	if(status == "false") {
		str= "停用";
		
	} else {
		str= "启用";
		
	}
	if(!confirm("是否" + str + "所选组别?")) {
	  	return;
	  	
  	}
	$.ajax({
		type: "POST",  
       	url: root+"/control/groupController/updateStatus.do?",
       	dataType: "json",
       	data:  "id=" + id + "&status=" + status,
       	success: function (result) {
       		alert(result.result_content)
       		if(result.result_code == "SUCCESS"){
       			find();
       			
       		}
       		
     	}
	
	});
	
}

function refreshCondition() {
	$("#group_code").val("");
	$("#group_name").val("");
	initializeSelect("status");
	initializeSelect("superior");
	initializeSelect("autoAllocFlag");
	find();
	
}

/**
* 查询
*/
function find(){
	openIdDiv("group_list", "/BT-LMIS/control/groupController/list.do?queryType=query&"
			+ $("form").serialize()
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