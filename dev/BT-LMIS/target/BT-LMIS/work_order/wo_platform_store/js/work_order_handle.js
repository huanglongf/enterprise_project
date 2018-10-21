var isloadUpd = false;
function loadUpdateItem(){
	if(!isloadUpd){
		isloadUpd=true;
		sendDataByUrl(
			'control/workOrderPlatformStoreController/getSOTeam.do',
			{},
			function(data){
				if (data.length > 0){
					$("#SOgroup").html("");
				}
				$("#SOgroup").append("<option value=''>" + "请选择" + "</option>");
				for (var i = 0; i < data.length; i++) {
					$("#SOgroup").append("<option value=' " + data[i].id + "'>" + data[i].group_name + "</option>");
				}
				$("#update-model").modal({backdrop: "static", keyboard: false});
				// 隐藏遮罩
				$(".modal-backdrop").hide();
			}
		);
	}else{
		$("#update-model").modal({backdrop: "static", keyboard: false});
		// 隐藏遮罩
		$(".modal-backdrop").hide();
	}
}
var isload = false;
function loadTeam(){
	if(!isload){
		isload=true;
		sendDataByUrl(
				'control/workOrderPlatformStoreController/getSOTeam.do',
				{},
				function(data){
					if (data.length > 0){
						$("#SOgroup").html("");
					}
					$("#SOgroup").append("<option value=''>" + "请选择" + "</option>");
					for (var i = 0; i < data.length; i++) {
						$("#SOgroup").append("<option value=' " + data[i].id + "'>" + data[i].group_name + "</option>");
					}
					$("#transmit-model").modal({backdrop: "static", keyboard: false});
					// 隐藏遮罩
					$(".modal-backdrop").hide();
				}
		);
	}else{
		$("#transmit-model").modal({backdrop: "static", keyboard: false});
		// 隐藏遮罩
		$(".modal-backdrop").hide();
	}
}
var isload_split = false;
function loadAllTeam(){
	if(!isload_split){
		isload_split=true;
		//TODO
		sendDataByUrl(
				'control/workOrderPlatformStoreController/getSOTeam.do?teamType=ALL',
				{},
				function(data){
					if (data.length > 0){
						$("#SOgroup_split").html("");
					}
					$("#SOgroup_split").append("<option value=''>" + "请选择" + "</option>");
					for (var i = 0; i < data.length; i++) {
						$("#SOgroup_split").append("<option value=' " + data[i].id + "'>" + data[i].group_name + "</option>");
					}
					$("#split-model").modal({backdrop: "static", keyboard: false});
					// 隐藏遮罩
					$(".modal-backdrop").hide();
				}
		);
	}else{
		$("#split-model").modal({backdrop: "static", keyboard: false});
		// 隐藏遮罩
		$(".modal-backdrop").hide();
	}
}
function loadSOUser(){
	sendDataByUrl(
			'control/workOrderPlatformStoreController/getUserByTeam.do?team_id='+$("#SOgroup").val(),
			{},
				function(data){
					/*if (data.length > 0){
					$("#employee").html("");
				}else{
					$("#employee").html("");
					$("#employee").append("<option value=''>" + "没有可选的人员" + "</option>");
				}*/
				$("#employee").html("");
				$("#employee").append("<option value='-1'>暂不转发至人员</option>");
				for (var i = 0; i < data.length; i++) {
					$("#employee").append("<option value=' " + data[i].id + "'>" + data[i].name + "</option>");
				}
			}
	);
}
function loadAllUser(){
	sendDataByUrl(
			'control/workOrderPlatformStoreController/getUserByTeam.do?team_id='+$("#SOgroup_split").val(),
			{},
			function(data){
				$("#employee_split").html("");
				$("#employee_split").append("<option value='-1'>暂不转发至人员</option>");
				for (var i = 0; i < data.length; i++) {
					$("#employee_split").append("<option value=' " + data[i].id + "'>" + data[i].name + "</option>");
				}
			}
	);
}
var isLoadedUsers = false;
function loadUsers(){
	if (!isLoadedUsers) {
		sendDataByUrl(
				"control/workOrderPlatformStoreController/getEmpByTeamId.do",
				{},
				function(data) {
					console.dir(data);
					if (data.length > 0){
						$("#employee").html("");
					}
					for (var i = 0; i < data.length; i++) {
						$("#employee").append("<option value='" + data[i].id + "'>" + data[i].name + "</option>");
					}
					$("#transmit-model").modal({backdrop: "static", keyboard: false});
					// 隐藏遮罩
					$(".modal-backdrop").hide();
				});
	} else {
		$("#transmit-model").modal({backdrop: "static", keyboard: false});
		// 隐藏遮罩
		$(".modal-backdrop").hide();
	}
}
// 返回
function goBack(){
	//工单列表url
//	openDiv(root + 'control/workOrderPlatformStoreController/platform.do?pageName=main&tableName=wo_store_master&tabNo=1');
	openDiv(root+'control/workOrderPlatformStoreController/backToMain.do?pageName=main&tableName=wo_store_master');
	$("body").removeAttr("style");
	$("body").removeClass();
	//cancelLoadingStyle();
}
// 获取
function empGetWorkOrder(){
	var woId=$("#woId").val();
	if (!confirm("确认获取吗？")) {
		return;
	}
	loadingStyle();
	sendDataByUrl(
		"control/workOrderPlatformStoreController/empGetWorkOrder.do",
		{
			woId: JSON.stringify([{"woId" : $("#woId").val(), "version" : $("#woId").data("version")}])
		},
		function(data){
			if (data.flag) {
				openDiv(root + "control/workOrderPlatformStoreController/workOrderHandle.do?woId=" + woId);
				alert(data.msg);
				cancelLoadingStyle();
			} else {
				cancelLoadingStyle();
				alert(data.msg);
			}
		}
	);
}
//修改工单类型
function updateWo(){
	//处理部门
	var process_department = $("#process_department").val();
	if(process_department==''){
		alert("请选择处理部门！");
		return;
	}
	//工单类型
	var wo_type = $("#wo_type_display").val();
	if(wo_type==''){
		alert("请选择工单类型！");
		return;
	}
	//异常类型
	var error_type = $("#error_type_display").val();
	var processDepartment = $("#processDepartment").val();
	var error_type_display = '';
	if(error_type!=''){
		error_type_display=$("#error_type_display").find("option:selected").text();
	}
	
	if($("#errorType").val()==error_type && $("#woType").val()==wo_type && processDepartment==process_department){
		alert("并没有做出任何修改");
		return;
	}
	
	var processInfo = '';
	if($("#errorType").val()!=error_type){
		processInfo=processInfo+"异常类型【"+$("#errorTypeDisplay").val()+"】修改成【"+$("#error_type_display").find("option:selected").text()+"】<br/>";
	}
	if($("#woType").val()!=wo_type){
		processInfo=processInfo+"工单类型【"+$("#woTypeDisplay").val()+"】修改成【"+$("#wo_type_display").find("option:selected").text()+"】<br/>";
	}
	
	if(processDepartment!=process_department){
		var processDepartment1='';
		if(processDepartment==0){
			processDepartment1='物流中心';
		}else{
			processDepartment1='销售运营部';
		}
		if(process_department==0){
			process_department1='物流中心';
		}else{
			process_department1='销售运营部';
		}
		processInfo=processInfo+"处理部门【"+processDepartment1+"】修改成【"+process_department1+"】<br/>";
	}
	
	loadingStyle();
	var data = {
		action: "UPDATE",
		woId: $("#woId").val(), 
		version : $("#woId").data("version"),
		processInfo :processInfo,
		process_department: process_department,
		wo_type: wo_type,
		wo_type_display: $("#wo_type_display").find("option:selected").text(),
		error_type:error_type,
		error_type_display:error_type_display
	};
	sendData(data, function(data){if (data.flag) {goBack();} else {alert(data.msg);cancelLoadingStyle();}});
}
//转发
function transmit(){
	if (!confirm("确认转发吗？")) {
		return;
	}
	/*if($("#SOgroup").val()==''){
		alert("必须选择组别！");
		return;
	}*/
	loadingStyle();
	var data = {
			action: "FORWARD",
			woId: JSON.stringify([{"woId" : $("#woId").val(), "version" : $("#woId").data("version")}]),
			// 处理意见
			processInfo: $("#processInfo").val(),
			// 附件
			accessory: $("#fileName_common").val(),
			
			employeeId : $("#employee").val(),//员工
			groupId : $("#SOgroup").val()//组别
	};
	// 工单类型确认
	if($("#woTypeRe").parent().parent().css("display") != "none") {
		data["woTypeRe"] = $("#woTypeRe").val();
		data["woTypeReDisplay"] = $("#woTypeRe").next().prop("placeholder");
	}
	if($("#errorTypeRe").parent().parent().css("display") != "none") {
		data["errorTypeRe"] = $("#errorTypeRe").val();
		data["errorTypeReDisplay"] = $("#errorTypeRe").next().prop("placeholder");
	}
	// 异常工单
	if($("#errorFlag").parent().parent().css("display") != "none"
		&& $("#errorFlag").prop("disabled") != "disabled"
			&& $("#errorFlag").prop("checked")) {
		data["errorFlag"] = 1;
	}
	sendData(data, function(data){if (data.flag) {goBack();} else {alert(data.msg);cancelLoadingStyle();}});
}
//拆分
function splitWO(){
	if (!confirm("确认拆分吗？")) {
		return;
	}
	if($("#SOgroup_split").val()==''){
		alert("必须选择组别！");
		return;
	}
	loadingStyle();
	var data = {
			action: "SPLIT",
			woId: $("#woId").val(),
			// 处理意见
			processInfo: $("#processInfo").val(),
			// 附件
			accessory: $("#fileName_common").val(),
			
			employeeId : $("#employee_split").val(),//员工
			groupId : $("#SOgroup_split").val()//组别
	};
	// 工单类型确认
	if($("#woTypeRe").parent().parent().css("display") != "none") {
		data["woTypeRe"] = $("#woTypeRe").val();
		data["woTypeReDisplay"] = $("#woTypeRe").next().prop("placeholder");
	}
	if($("#errorTypeRe").parent().parent().css("display") != "none") {
		data["errorTypeRe"] = $("#errorTypeRe").val();
		data["errorTypeReDisplay"] = $("#errorTypeRe").next().prop("placeholder");
	}
	// 异常工单
	if($("#errorFlag").parent().parent().css("display") != "none"
		&& $("#errorFlag").prop("disabled") != "disabled"
			&& $("#errorFlag").prop("checked")) {
		data["errorFlag"] = 1;
	}
	sendData(data, function(data){
		if (data.flag) {
			alert(data.msg);
    		openDiv(root + "control/workOrderPlatformStoreController/workOrderHandle.do?woId=" + $("#woId").val());
    		cancelLoadingStyle();
		} else {
			alert(data.msg);cancelLoadingStyle();
			}
		});
}
// 协助
function assist(){
	if ($("#processInfo").val().trim() == "") {
		alert("请填写处理意见");
		return;
	}
	if (!confirm("确认协助吗？")) {
		return;
	}
	loadingStyle();
	var data = {
		action: "ASSIST",
		woId: $("#woId").val(),
		version: $("#woId").data("version"),
		//处理意见
		processInfo: $("#processInfo").val(),
		//附件
		accessory: $("#fileName_common").val(),
	};
	// 工单类型确认
	if($("#woTypeRe").parent().parent().css("display") != "none") {
		data["woTypeRe"] = $("#woTypeRe").val();
		data["woTypeReDisplay"] = $("#woTypeRe").next().prop("placeholder");
	}
	if($("#errorTypeRe").parent().parent().css("display") != "none") {
		data["errorTypeRe"] = $("#errorTypeRe").val();
		data["errorTypeReDisplay"] = $("#errorTypeRe").next().prop("placeholder");
	}
	// 异常工单
	if($("#errorFlag").parent().parent().css("display") != "none"
		&& $("#errorFlag").prop("disabled") != "disabled"
			&& $("#errorFlag").prop("checked")) {
		data["errorFlag"] = 1;
	}
	sendData(data, function(data){if (data.flag) {goBack();} else {alert(data.msg);cancelLoadingStyle();}});
}
// 回复
function reply(){
	if ($("#processInfo").val().trim() == "") {
		alert("请填写处理意见");
		return;
	}
	if (!confirm("确认回复吗？")) {
		return;
	}
	loadingStyle();
	var data = {
		action: "REPLY",
		woId: $("#woId").val(),
		version: $("#woId").data("version"),
		// 处理意见
		processInfo: $("#processInfo").val(),
		// 附件
		accessory: $("#fileName_common").val(),
	};
	// 工单类型确认
	if($("#woTypeRe").parent().parent().css("display") != "none") {
		data["woTypeRe"] = $("#woTypeRe").val();
		data["woTypeReDisplay"] = $("#woTypeRe").next().prop("placeholder");
	}
	if($("#errorTypeRe").parent().parent().css("display") != "none") {
		data["errorTypeRe"] = $("#errorTypeRe").val();
		data["errorTypeReDisplay"] = $("#errorTypeRe").next().prop("placeholder");
	}
	// 异常工单
	if($("#errorFlag").parent().parent().css("display") != "none"
		&& $("#errorFlag").prop("disabled") != "disabled"
			&& $("#errorFlag").prop("checked")) {
		data["errorFlag"] = 1;
	}
	sendData(data, function(data){if (data.flag) {goBack();} else {alert(data.msg);cancelLoadingStyle();}});
}
//完结
function finish(){
	if ($("#processInfo").val().trim() == "") {
		alert("请填写处理意见");
		return;
	}
	if (!confirm("确认完结吗？")) {
		return;
	}
	loadingStyle();
	var data = {
		action: "OVER",
		woId: JSON.stringify([{"woId" : $("#woId").val(), "version" : $("#woId").data("version")}]),
		//处理意见
		processInfo: $("#processInfo").val(),
		//附件
		accessory: $("#fileName_common").val(),
	};
	// 工单类型确认
	if($("#woTypeRe").parent().parent().css("display") != "none") {
		data["woTypeRe"] = $("#woTypeRe").val();
		data["woTypeReDisplay"] = $("#woTypeRe").next().prop("placeholder");
	}
	if($("#errorTypeRe").parent().parent().css("display") != "none") {
		data["errorTypeRe"] = $("#errorTypeRe").val();
		data["errorTypeReDisplay"] = $("#errorTypeRe").next().prop("placeholder");
	}
	// 异常工单
	if($("#errorFlag").parent().parent().css("display") != "none"
		&& $("#errorFlag").prop("disabled") != "disabled"
			&& $("#errorFlag").prop("checked")) {
		data["errorFlag"] = 1;
	}
	sendData(data, function(data){if (data.flag) {goBack();} else {alert(data.msg);cancelLoadingStyle();}});
}

//取消
function cancle(){
	if ($("#processInfo").val().trim() == "") {
		alert("请填写处理意见");
		return;
	}
	if (!confirm("确认取消吗？")) {
		return;
	}
	loadingStyle();
	var data = {
			action: "CANCLE",
			woId: JSON.stringify([{"woId" : $("#woId").val(), "version" : $("#woId").data("version")}]),
			//处理意见
			processInfo: $("#processInfo").val(),
			//附件
			accessory: $("#fileName_common").val(),
	};
	// 工单类型确认
	if($("#woTypeRe").parent().parent().css("display") != "none") {
		data["woTypeRe"] = $("#woTypeRe").val();
		data["woTypeReDisplay"] = $("#woTypeRe").next().prop("placeholder");
	}
	if($("#errorTypeRe").parent().parent().css("display") != "none") {
		data["errorTypeRe"] = $("#errorTypeRe").val();
		data["errorTypeReDisplay"] = $("#errorTypeRe").next().prop("placeholder");
	}
	// 异常工单
	if($("#errorFlag").parent().parent().css("display") != "none"
		&& $("#errorFlag").prop("disabled") != "disabled"
			&& $("#errorFlag").prop("checked")) {
		data["errorFlag"] = 1;
	}
	sendData(data, function(data){if (data.flag) {goBack();} else {alert(data.msg);cancelLoadingStyle();}});
}
function followUpRecordList(){
	 $.ajax({
	 	url: root+'control/followUpRecordController/followUpRecordList.do',
		data: {woId:  $("#woId").val()},
		type: "post",
		dataType: "text",
 		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
    	success: function (data){
    		$("#followUpRecordList").html(data);
    		$("#followUpRecordList").modal({backdrop: 'static', keyboard: false});
    		$('.modal-backdrop').hide();
    	},
		 error: function (data){
			 alert("系统异常")
		 }
  	}); 
}

function addFollowUpRecord() {
	var followuprecordval = $("#followuprecord").val();
	if(followuprecordval == null || followuprecordval == "" || followuprecordval == undefined) {
		alert("请填写跟进内容");
		return;
	}
	if (!confirm("确认跟进吗？")) {
		return;
	}
	loadingStyle();
	sendDataByUrl(
		"control/followUpRecordController/addfollowUpRecord.do",
		{
			woId:  $("#woId").val(),
			version : $("#woId").data("version"),
			//跟进意见
			followuprecord: followuprecordval
		},
		function(data){if (data.flag) {
			alert(data.msg);
			openDiv(root + "control/workOrderPlatformStoreController/workOrderHandle.do?woId=" + $("#woId").val());
			cancelLoadingStyle();
		} else {alert(data.msg);cancelLoadingStyle();}}	
	);
}
//
function sendData(data, callback){
	sendDataByUrl("control/workOrderPlatformStoreController/workOrderAction.do", data, callback);
}
//
function sendDataByUrl(url, data, callback){
	$.ajax({
		url: root + url,
		data: data,
		type: "post",
		dataType: "json",
		success: callback,
		error: callback
	});
}


function aKeyExport(){
	window.open("/BT-LMIS/control/workOrderPlatformStoreController/aKeyExport.do?"+"woId=" + $("#woId").val());
}
function downloadZip(){
	window.open("/BT-LMIS/control/workOrderPlatformStoreController/downloadZip.do?"+"woId=" + $("#woId").val());
}

//--------------------------------------------------------------------------------------------------------

function toWoAssociation(){
	$("#waybillGeneratedSS4").children().remove();
	$("#workOrderInfoSS4").children().remove();
	$("#woWaybillGenerated_form4").modal();
	$(".modal-backdrop").hide();
}
function searchWoNo(){
	var woId = $("#woId").val();
	var woNo = $("#woNoStrSS").val();
	if(woNo==''){
		alert("请输入模糊查询的条件！");
		return;
	}
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/workOrderPlatformStoreController/getWorkOrderByWoNo.do",
        dataType: "json",
        data: {woNo: woNo,woId:woId},
        success: function(result) {
        	if(result.result_code="SUCCESS") {
        		// 刷新列表
        		$("#waybillGeneratedSS4").children().remove();
        		var tr="";
        		for(var i=0; i<result.wo.length; i++) {
        			tr+="<tr><td title='"+result.wo[i].wo_no+"'><a style='cursor:pointer;' onclick='focusWorkOrder3($(this));getWorkOrderInfo2(\""+result.wo[i].id+"\");'>"+result.wo[i].wo_no+"</a></td><td><a onclick='focusWorkOrder2($(this));giveWoNo(\""+result.wo[i].wo_no+"\");'>选择</a></td></tr>";
        		}
        		$("#waybillGeneratedSS4").append(tr);
        	} else {
        		alert(result.result_content);
        	}
        },
        error: function() {
        	alert("系统异常-error");
        }
	});
	}
function focusWorkOrder3(a) {
	a.parent().parent().siblings().each(function(){
		$(this).children(":first").children(":first").removeClass("red");
		
	})
	if(!a.hasClass("red")) {
		a.addClass("red");
	}
}
function focusWorkOrder2(a) {
	a.hide();
}

function getWorkOrderInfo2(obj) {
	$("#workOrderInfoSS4").children().remove();
	openIdDiv("workOrderInfoSS4",
			"/BT-LMIS/control/workOrderPlatformStoreController/workOrderHandle.do?woId="+obj+"&readonly=1");
}

function giveWoNo(obj){
	var a = $("#woNoLOG").val();
	if(a==''){
		$("input[name='woNoLOG']").val(obj);
	}else{
		$("input[name='woNoLOG']").val(a+","+obj);
	}
}
function woAssociation(){
	var a = $("#woNoLOG").val();
	var woId = $("#woId").val();
	if(a==''){
		alert("请先选择工单号！");
		return;
	}
	$.ajax({
	 	url: root+'control/workOrderPlatformStoreController/woAssociation.do',
		data: {woNo: a,woId:woId},
		type: "post",
		dataType: "json",
    	success: function (data){
    		alert(data.msg);
    		openDiv(root + "control/workOrderPlatformStoreController/workOrderHandle.do?woId=" + woId);
    	},
		 error: function (data){
			 alert("系统异常")
		 }
  	}); 
}
//---------------------------------------------------------------------------------
function toShowAssociationRelated(){
	var woNoStr = $("#relatedWoNo").val();
	$("#workOrderInfoSS3").children().remove();
	$("#woWaybillGenerated_form3").modal();
	$(".modal-backdrop").hide();
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/workOrderPlatformStoreController/getIdByWoNos.do",
        dataType: "json",
        data: {woNoStr: woNoStr},
        success: function(result) {
        	if(result.result_code="SUCCESS") {
        		// 刷新列表
        		$("#waybillGeneratedSS3").children().remove();
        		var tr="";
        		for(var i=0; i<result.wo.length; i++) {
        			tr+="<tr><td title='"+result.wo[i].wo_no+"'><a style='cursor:pointer;' onclick='focusWorkOrder3($(this));getWorkOrderInfo3(\""+result.wo[i].id+"\");'>"+result.wo[i].wo_no+"</a></td></tr>";
        		}
        		$("#waybillGeneratedSS3").append(tr);
        	} else {
        		alert(result.result_content);
        	}
        },
        error: function() {
        	alert("系统异常-error");
        }
	});
}
function toShowAssociationSplit(){
	var woNoMain=$("#woNoMain").val();
	$("#workOrderInfoSS3").children().remove();
	$("#woWaybillGenerated_form3").modal();
	$(".modal-backdrop").hide();
	$.ajax({
		type: "POST",
		url: "/BT-LMIS/control/workOrderPlatformStoreController/getSplitList.do",
		dataType: "json",
		data: {woNo: woNoMain},
		success: function(result) {
			if(result.result_code="SUCCESS") {
				// 刷新列表
				$("#waybillGeneratedSS3").children().remove();
				var tr="";
				for(var i=0; i<result.wo.length; i++) {
					tr+="<tr><td title='"+result.wo[i].wo_no+"'><a style='cursor:pointer;' onclick='focusWorkOrder3($(this));getWorkOrderInfo3(\""+result.wo[i].id+"\");'>"+result.wo[i].wo_no+"</a></td></tr>";
				}
				$("#waybillGeneratedSS3").append(tr);
			} else {
				alert(result.result_content);
			}
		},
		error: function() {
			alert("系统异常-error");
		}
	});
}
function getWorkOrderInfo3(obj) {
	$("#workOrderInfoSS3").children().remove();
	openIdDiv("workOrderInfoSS3",
			"/BT-LMIS/control/workOrderPlatformStoreController/workOrderHandle.do?woId="+obj+"&readonly=2");
}
function clearInput(){
	$("#woNoLOG").val('');
	searchWoNo();
}
//------------------------------------------------------------------------------------------------
function OneKeyDown(accessory_list){
	if(!accessory_list){
		alert("无附件！");
		return;
	}
	var str = encodeURI(accessory_list);
	
	var str2 = str.replace(/#/g, '1a1b1c1d3e');
	
	window.location.href="/BT-LMIS/control/workOrderManagementController/OneKeyDownload.do?"
			+"woId="
			+"&accessory_list="+str2;
}

function accessorySSDownload(serverName,originName){
	window.location.href="/BT-LMIS/control/workOrderPlatformStoreController/accessoryDownload.do?"
		+"serverName="+serverName
		+"&originName="+originName;
}