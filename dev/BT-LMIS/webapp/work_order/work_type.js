function Add(){
	if($("#mainId").val()==""){
		var url=root+"/control/OrderTypeController/add_type_main.do?opType=1";  
	}else{
		var url=root+"/control/OrderTypeController/add_type_main.do?opType=2&id="+$("#mainId").val();
	}
	if(!text_long_check("typeKid",50,"工单类型")){
		return;
	}
	if(!checkForm("typeKid","工单类型")){
		return;
	}		
	if(!text_long_check("remark",50,"备注")){
		return;
	}
	var if_share=0;
	if ($("input[id='if_share']").is(':checked')) {
		if_share = 1;
	} 
	var so_flag=0;
	if ($("input[id='so_flag']").is(':checked')) {
		so_flag = 1;
	} 
	var type=$("#typeKid").val();
	var status=$("#status").val();
	var remark=$("#remark").val();
	var data="type="+type+"&status="+status+"&remark="+remark+"&if_share="+if_share+"&so_flag="+so_flag;
	$.ajax({
		type : "POST",
		url: url,  
		data:data,
		dataType: "json",  
		success : function(jsonStr) {
			alert(jsonStr.out_result_reason);
			if(jsonStr.out_result==1){
				$("#typeKid").css("background-color","#d9d2e9");
				$("#typeKid").attr("disabled","disabled");
			}
           if($("#mainId").val()==""){
        	   $("#mainId").val(jsonStr.main_id);
        	   $("#mainCode").val(jsonStr.main_id);
           }
		}
	});
 }


function getTypeInfo(wkId){
	var url=root+"/control/OrderTypeController/getTypeTab.do?wkCode="+wkId;  
	var htm_td="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				var text="<table id='sample-table-1' class='table table-striped table-bordered table-hover'>"
					   +"<thead class='center'>"
				       +"<tr>"
				       +"<th class='center'>序号</td><th class='center'>工单级别</th><th class='center'>标准工时(min)</th>"
				       +"<th class='center'>计划完成工时(min)</th><th class='center'>超时升级工时(min)</th><th class='center'>操作</th></tr></thead>"
				       +"<tbody>";
				for(i=0;i<jsonStr.length;i++){
				  htm_td=htm_td+"<tr class='center'><td  class='center'>"+(i+1)+"</td><td>"+jsonStr[i].level+"</td><td  align='center'>"+jsonStr[i].wk_standard+"</td><td>"+jsonStr[i].wk_plan+"</td><td>"+jsonStr[i].wk_timeout+"</td><td><a href='javaScript:;' onclick='del_tab_type(\""+jsonStr[i].id+"\")'>删除</a>|<a href='javaScript:;' onclick='up_tab_type(\""+jsonStr[i].id+"\",\""+jsonStr[i].level+"\",\""+jsonStr[i].wk_standard+"\",\""+jsonStr[i].wk_plan+"\",\""+jsonStr[i].wk_timeout+"\")'>修改</a></td></tr>"	
				}
				$("workType").html(text+htm_td+"</tbody></table>");
			}
		});
}


function up_tab_type(id,level,wk_standard,wk_plan,wk_timeout){
	getAllLevel(level);
	$("#type_form").modal('show');
	$("#btn_type_text").text("确认修改");
	$("#upTypeId").val(id);
	$("#standard_time_form").val(wk_standard);
	$("#plan_time_form").val(wk_plan);
	$("#timeout_time_form").val(wk_timeout);
	
}





function getReasonInfo(wkId){
	var url=root+"/control/OrderTypeController/getReasonTab.do?wkCode="+wkId;
	var htm_td="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				var text="<table id='sample-table-1' class='table table-striped table-bordered table-hover'>"
					   +"<thead>"
				       +"<tr>"
				       +"<th class='center'>序号</th><th class='center'>原因</th><th class='center'>说明</th><th class='center'>创建时间</th>"
				       +"<th class='center'>操作</th></tr></thead>"
				       +"<tbody>";
				for(i=0;i<jsonStr.length;i++){
				  htm_td=htm_td+"<tr><td class='td_cs'>"+(i+1)+"</td><td class='td_cs'>"+jsonStr[i].reason+"</td><td class='td_cs'>"+jsonStr[i].remark+"</td><td class='td_cs'>"+jsonStr[i].create_time+"</td><td class='td_cs'><a href='javaScript:;' onclick='del_tab_reason(\""+jsonStr[i].id+"\")'>删除</a>|<a href='javaScript:;' onclick='up_tab_reason(\""+jsonStr[i].id+"\",\""+jsonStr[i].reason+"\",\""+jsonStr[i].remark+"\")'>修改</a></td></tr>"	
				}
				$("workReason").html(text+htm_td+"</tbody></table>");
			}
		});
}


function getErrorInfo(wkId){
	var url=root+"/control/OrderTypeController/getErrorTab.do?wkCode="+wkId;
	var htm_td="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				var text="<table id='sample-table-1' class='table table-striped table-bordered table-hover'>"
					   +"<thead>"
				       +"<tr>"
				       +"<th class='center'>序号</th><th class='center'>异常类型</th>"
				       +"<th class='center'>操作</th></tr></thead>"
				       +"<tbody>";
				for(i=0;i<jsonStr.length;i++){
				  htm_td=htm_td+"<tr><td class='td_cs'>"+(i+1)+"</td><td class='td_cs'>"+jsonStr[i].type_name+"</td><td class='td_cs'><a href='javaScript:;' onclick='del_tab_error(\""+jsonStr[i].id+"\")'>删除</a>|<a href='javaScript:;' onclick='up_tab_error(\""+jsonStr[i].id+"\",\""+jsonStr[i].type_name+"\")'>修改</a></td></tr>"	
				}
				$("errorType").html(text+htm_td+"</tbody></table>");
			}
		});
}


function up_tab_reason(id,reason,remark){
	$("#reason_form").modal('show');
	$("#btn_reason_text").text("确认修改");
	$("#upReasonId").val(id);
    $("#reasons_form").val(reason);
    $("#remark_form").val(remark);
}

function up_tab_error(id,reason,remark){
	$("#error_form").modal('show');
	$("#btn_error_text").text("确认修改");
	$("#upErrorId").val(id);
    $("#error_type").val(reason);
}

function toAddType(){
	up_tab_type("","1","","","");
	$("#type_form").modal('show');
	$("#btn_type_text").text("提交");
//	getAllLevel();
}

function toAddReason(){
	up_tab_reason("","","");
	$("#reason_form").modal('show');
	$("#btn_reason_text").text("提交");
}

function toAddError(){
	up_tab_error("","","");
	$("#error_form").modal('show');
	$("#btn_error_text").text("提交");
}

function saveType(){
	if($("#mainId").val()==""){
		alert("请先添加主信息");
		return;
	}
	if(!text_long_check("standard_time_form",5,"标准工时")){
		return;
	}

	if(!text_long_check("plan_time_form",5,"计划完成工时")){
		return;
	}
	
	if(!text_long_check("timeout_time_form",5,"超时升级工时")){
		return;
	}
	if(not_num_or_empty("standard_time_form","标准工时")){
		return false;
    }	
	if(not_num_or_empty("plan_time_form","计划完成工时")){
		return false;
    }		
	if(not_num_or_empty("timeout_time_form","超时升级工时")){
		return false;
    }		
	
	var level=$("#level").val();
	var standard_time_form=$("#standard_time_form").val();
	var plan_time_form=$("#plan_time_form").val();
	var timeout_time_form=$("#timeout_time_form").val();
	var wkId=$("#mainId").val();
	var wkCode=$("#mainCode").val();
	var sort=$("#"+level).attr("sort");
	var url=root+"/control/OrderTypeController/add_type_kid.do";  
	var data="level="+level+"&standard_time_form="+standard_time_form+"&plan_time_form="+plan_time_form+"&timeout_time_form="+timeout_time_form+"&wkId="+wkId+"&sort="+sort+"&wkCode="+wkCode;
	if($("#btn_type_text").text()=="确认修改"){
		data=data+"&opType=2&id="+$("#upTypeId").val();
	}else{
		data=data+"&opType=1"
	}
	$.ajax({
		type : "POST",
		url: url,  
		data:data,
		dataType: "json",  
		success : function(jsonStr) {
			alert(jsonStr.out_result_reason);
			if(jsonStr.out_result==1){
				getTypeInfo(wkCode);
				$("#btn_back").click();
			}
			
		}
	});
}


function saveReason(){
	if($("#mainId").val()==""){
		alert("请先添加主信息");
		return;
	}
	if($("#reasons_form").val().length==0){
		alert("原因不能为空");
		return;
	}
	
	if(!text_long_check("reasons_form",20,"原因")){
		return;
	}
	if(!text_long_check("remark_form",40,"说明")){
		return;
	}
	var reason=$("#reasons_form").val();
	var remark=$("#remark_form").val();
	var wkId=$("#mainId").val();
	var wkCode=$("#mainCode").val();
	var url=root+"/control/OrderTypeController/add_reason_kid.do";  
	var data="reason="+reason+"&remark="+remark+"&wkId="+wkId+"&wkCode="+wkCode;
	if($("#btn_reason_text").text()=="确认修改"){
		data=data+"&opType=2&id="+$("#upReasonId").val()
	}else{
		data=data+"&opType=1"
	}
	$.ajax({
		type : "POST",
		url: url,  
		data:data,
		dataType: "json",
		success : function(jsonStr) {
			alert(jsonStr.out_result_reason);
			if(jsonStr.out_result==1){
			getReasonInfo(wkCode);
			$("#btn_back3").click();
			}
		}
	});
}


function saveError(){
	if($("#mainId").val()==""){
		alert("请先添加主信息");
		return;
	}
	if($("#error_type").val().length==0){
		alert("异常类型不能为空");
		return;
	}
	
	if(!text_long_check("error_type",20,"异常类型")){
		return;
	}
//	if(!text_long_check("remark_form",40,"说明")){
//		return;
//	}
	var reason=$("#error_type").val();
	var remark=$("#err_form").val();
	var wkId=$("#mainId").val();
	var wkCode=$("#mainCode").val();
	var url=root+"/control/OrderTypeController/add_error_kid.do";  
	var data="type_name="+reason+"&remark="+remark+"&wkId="+wkId+"&wkCode="+wkCode;
	if($("#btn_error_text").text()=="确认修改"){
		data=data+"&opType=2&id="+$("#upErrorId").val()
	}else{
		data=data+"&opType=1"
	}
	$.ajax({
		type : "POST",
		url: url,  
		data:data,
		dataType: "json",
		success : function(jsonStr) {
			alert(jsonStr.out_result_reason);
			if(jsonStr.out_result==1){
			getErrorInfo(wkCode);
			$("#btn_back2").click();
			}
		}
	});
}


function del(){
		if($("input[name='ckb']:checked").length>=1){
		   var priv_ids =[];
		  	$("input[name='ckb']:checked").each(function(){
				priv_ids.push($.trim($(this).val()));
		  	});
		  	if(!confirm("确定删除吗?")){
			  	return;
		  	}
	  	$.ajax({
			type: "POST",
           	url: root+"/control/OrderTypeController/del.do?",
           	dataType: "json",
           	data:  "privIds="+priv_ids,
           	success: function (data) {
				if(data.out_result=='1'){
					pageJump();
				}else if(data.out_result=='0'){
        			alert(data.out_result_reason);
				}else{
        			alert("删除异常!");
				}
           	}  
	  	});
		}else{
		alert("请选择一行!");
		}
	}


function updateStatus(type,status){
	var id='';
	var wk_type="";
	var remark="";
	if($("input[name='ckb']:checked").length>=1){
		   var priv_ids ='';
		  	$("input[name='ckb']:checked").each(function(){
				priv_ids=priv_ids+','+$(this).val();
		  	});
		if(type=="1"){
			var message = '确定修改吗?';
			
			if (status == '1') {
				message = '确定启用吗？';
			} else {
				message = '确定禁用吗？';
			}
			
			if(!confirm(message)){
				return;
			}
//			alert(status);
//			alert(priv_ids);
			var url=root+"/control/OrderTypeController/updateStatus.do?status="+status+"&priv_ids="+priv_ids;
			$.ajax({
				type: "POST",
				url:url,
				dataType: "text",
				data:"",
				success: function (data) {
					if(data=='true'){
						pageJump();
					}else if(data=='false'){
						alert("成功!");
					}else{
						alert("失败!");
					}
				}  
			});
		}
		if(type=="2"){
			var url=root+"/control/OrderTypeController/updateInfo.do?id="+id+"&remark="+remark+"&wk_type="+wk_type+"&status="+status;
			openDiv(url);
		}

	}else{
		alert("请至少选择一行!");
	}
}

//双击事件
function updateInfo(id,remark,wk_type,status,code,if_share,so_flag){
	var url=root+"/control/OrderTypeController/updateInfo.do?id="+id+"&remark="+remark+"&wk_type="+wk_type+"&status="+status+"&code="+code+"&if_share="+if_share+"&so_flag="+so_flag;
	openDiv(url);
}

function del_tab_type(id){
	var url=root+"/control/OrderTypeController/delType.do?id="+id;
	$.ajax({
		type: "POST",
		url:url,
		dataType: "json",
		data:"",
		success: function (jsonStr) {
			 alert(jsonStr.out_result_reason);
			 getTypeInfo($("#mainCode").val());
		}  
	});
}

function del_tab_reason(id){
	var url=root+"/control/OrderTypeController/delReason.do?id="+id;
	$.ajax({
		type: "POST",
		url:url,
		dataType: "json",
		data:"",
		success: function (jsonStr) {
			 alert(jsonStr.out_result_reason);
			getReasonInfo($("#mainCode").val());
		}  
	});
}


function del_tab_error(id){
	var url=root+"/control/OrderTypeController/delError.do?id="+id;
	$.ajax({
		type: "POST",
		url:url,
		dataType: "json",
		data:"",
		success: function (jsonStr) {
			 alert(jsonStr.out_result_reason);
			 getErrorInfo($("#mainCode").val());
		}  
	});
}

function inverseCkb(items) {
	var checklist = document.getElementsByName (items);
	if(document.getElementById("checkAll_templetTest").checked){
	   	for(var i=0;i<checklist.length;i++){
	      checklist[i].checked = 1;
	  } 
	}else{
	  for(var j=0;j<checklist.length;j++){
	     checklist[j].checked = 0;
	  }
	}
	
	/*$('[name=' + items + ']:checkbox').each(function() {
		this.checked = !this.checked;
	});*/
}

function getAllLevel(leg){
	var url=root+"/control/OrderTypeController/getAllLevel.do";
	 var html="";
	$.ajax({
		type: "POST",
		url:url,
		dataType: "json",
		data:"",
		success: function (jsonStr) {
		 for(var i=0;i<jsonStr.length;i++){
			 if(leg==jsonStr[i].wk_level){
				 html=html+"<option value='"+jsonStr[i].wk_code+"' id='"+jsonStr[i].wk_code+"' selected=selected  sort='"+jsonStr[i].sort+"'>"+jsonStr[i].wk_level+"</option>";
			 }else{
				 html=html+"<option value='"+jsonStr[i].wk_code+"' id='"+jsonStr[i].wk_code+"'  sort='"+jsonStr[i].sort+"'>"+jsonStr[i].wk_level+"</option>";
			 }
			
		 }
		 $("#level").html(html);
		}  
	});
}

	function backDress(){
			openDiv(root+'control/OrderTypeController2/typelist.do?pageName=work_type_list&tableName=wk_type');
	}

function upBg(id){
	$("*[id*=tr_]").css("background","#ffffff");
	$("#tr_"+id).css("background","#C6E2FF");
}