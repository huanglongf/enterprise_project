$(function(){
	// 若工单类型存在值则解除冻结
	if($("#wo_type_display").children().eq(1)) {
		if($("#wo_type_display").next().attr("disabled") == true) {
			$("#wo_type_display").next().attr("disabled", false);
		} else {
			if($("#process_department").val() == "") {
				$("#wo_type_display").next().attr("disabled", true);
			}	
		}
	}
	// 若异常类型存在值则解除冻结
	if($("#error_type_display").children().eq(1)) {
		if($("#error_type_display").next().attr("disabled") == true) {
			$("#error_type_display").next().attr("disabled", false);
		} else {
			if($("#wo_type_display").val() == "") {
				$("#error_type_display").next().attr("disabled", true);
			}	
		}
	}
})
function shiftPlatform(thisnode, label) {
	var num=0;
	if(label==2){
		$("#finish_btn").show();
		$("#cancle_btn").show();
		$("#relay_btn").show();
	}else{
		$("#relay_btn").hide();
		$("#finish_btn").hide();
		$("#cancle_btn").hide();
	}
	if(label==2||label==6){
		$("#batch_reply_btn").show();
	}else{
		$("#batch_reply_btn").hide();
	}
	if($(".orange")){
		$(".orange").removeClass("orange");
	}
	var aa=$("#"+label+"_div").text().split("+");
	var str = aa[0].substring(aa[0].indexOf('(')+1,aa[0].indexOf(')'));
	if(aa.length>1){
		num=Number(str)+Number(aa[1]);
	}else{
		num=Number(str);
	}
	var s=aa[0].substring(0,aa[0].indexOf('(')+1);
	s=s+num+")";
	$("#"+label+"_div").text(s);
	thisnode.addClass("orange");
	$("#tabNo").val(label);
	if(label>4&&label!=9){
		shareFlag();
	}else{
	}
	if(label==10||label==11){
		$("#obtain_btn").show();
	}else{
		$("#obtain_btn").hide();
	}
	refresh();
}
//判断是否组内共享 是的话隐藏button
function shareFlag() {
    $.ajax({
       type: "POST",
       url: "/BT-LMIS/control/workOrderPlatformStoreController/judgeShareGroupOrNot.do",
       dataType: "json",
       data: {},
       success: function(result) {
          if(result.flag==false){
        	  $("#relay_btn").hide();
        	  $("#finish_btn").hide();
        	  $("#cancle_btn").hide();
          }else{
        	  $("#relay_btn").show();
        	  if($("#userType").val()==1){
        		  $("#finish_btn").hide();
        		  $("#cancle_btn").hide();
        	  }else{
        	 	$("#finish_btn").show();
        	 	$("#cancle_btn").show();
        	  }
          }
       },
       error: function() {
          alert("系统异常-error");
       }
    });
}

//跳转新增页面
function toAdd() {
	var type=$("#userType").val();
	openDiv('/BT-LMIS/control/workOrderPlatformStoreController/toForm.do?type='+type);
}
//附件上传
function imp() {
	if ($("#photoCover").val().length == 0) {
		alert("请先选择文件");
		return;
	}
	$("#imps").hide();
	$.ajaxFileUpload({
		//上传地址
		url : root + 'userController/1/upload.do',
		//是否需要安全协议，一般设置为false
		secureuri : false,
		//文件上传域的ID
		fileElementId : 'file1',
		type : 'post',
		contentType:'application/x-www-form-urlencoded;charset=UTF-8',
		//返回值类型 一般设置为json
		dataType : 'text',
		//服务器成功响应处理函数
		success : function(data) {
			$("#fileName").prop("value",$('input[id=file1]').val());
			alert($("#fileName").val());
		},
		error : function(msg) {
			alert(msg);
		}
	})
	$("#imps").show();
	return;
}
$(function() {
	$('input[id=file1]').change(function() {
		$('#photoCover').val($(this).val());
	});
});
function goBack(){
	//工单列表url
//	openDiv(root + 'control/workOrderPlatformStoreController/platform.do?pageName=main&tableName=wo_store_master&tabNo=1');
	openDiv(root+'control/workOrderPlatformStoreController/backToMain.do?pageName=main&tableName=wo_store_master');
	$("body").removeAttr("style");
	$("body").removeClass();
	
}
//查询 
function tablesearch(column, sort) {
	$("sort_column").val(column);
	$("sort").val(sort);
	$(".search-table").load("/BT-LMIS/control/workOrderPlatformStoreController/platform.do?pageName=table&tableName=wo_store_master&startRow="
		+ $("#startRow").val()
		+ "&endRow="
		+ $("#startRow").val()
		+ "&page="
		+ $("#pageIndex").val()
		+ "&pageSize="
		+ $("#pageSize").val()
		+ "&sortColumn="
		+ column
		+ "&sort="
		+ sort
		+ "&tabNo="
		+ $("#tabNo").val()
		+ "&"
		+ $(".search_form").serialize()
	);
	
}
//分页跳转
function pageJump() {
	tablesearch('','');
}
//mainList的table内容双击事件
function tableAction(obj,tableFunctionConfig) {
	if(tableFunctionConfig.dbclickTr == true) {
		openDiv('/BT-LMIS/control/workOrderPlatformStoreController/transJSP.do?id=' + obj.children(":first").children(":first").val());
	}
}
//重置查询条件
function refresh() {
	$("#wk_form input").each(function(){
		$(this).val("");
	});
	initializeSelect('wo_status_display');
	initializeSelect('wo_type_display');
	initializeSelect('error_type_display');
	initializeSelect('follow_up_flag');
	initializeSelect('process_department');
	initializeSelect('operation_system');
	initializeSelect('problem_store_display');
	initializeSelect('isProcing');
	$("#error_type_display").next().attr("disabled", true);
	
	tablesearch('','');
}
function endueDisplay(id) {
	var control= $("#" + id);
	var display= $("#" + id + "Display");
	if(control.val() == "") {
		display.val("");
		
	} else {
		display.val(control.next().prop("placeholder"));
	}
}
//切换处理部门查询对应的工单类型
function shiftProcessDepartment(pdSelector,param){
	var pd = $(pdSelector).val();
	$(param["woTypeSelector"]).children(":first").siblings().remove();
	$(param["woTypeSelector"]).siblings("ul").children(":first").siblings().remove();
	if(pd == "") {
		$(param["woTypeSelector"]).next().attr("disabled", "disabled");
	} else {
		var data = {"so_flag": pd };
		if("woTypeStatus" in param) {
			data["status"] = param["woTypeStatus"];
		}
		$.ajax({
			type: "POST",
	        url: "/BT-LMIS/control/workOrderPlatformStoreController/listWoTypeByDepartment.do",
	        dataType: "json",
	        data: data,
	        success: function(woType) {
        		var content1 = "";
        		var content2 = "";
				//
				for(var temp in woType){
					content1 += '<option value="' + woType[temp].code + '">' + woType[temp].name + '</option>';
					content2 += '<li class="m-list-item" data-value="' + woType[temp].code + '">' + woType[temp].name + '</li>';
				}
        		// 装上之后的值
				$(param["woTypeSelector"] + " option:eq(0)").after(content1);
				$(param["woTypeSelector"]).siblings("ul").children(":first").after(content2);
				$(param["woTypeSelector"]).next().attr("disabled", false);
	        }
		});
	}
	initSelectFilter(param["woTypeSelector"]);
	if($("#wo_type_display").val() == "") {
		initSelectFilter($("#error_type_display"));
		$("#error_type_display").next().attr("disabled", true);
	}
}
//切换工单类型显示异常原因
function shiftWoType(wotypeSelector, param) {
	var woType = $(wotypeSelector).val();
	// 去除之前的值
	// 异常类型
	$(param["errorTypeSelector"]).children(":first").siblings().remove();
	$(param["errorTypeSelector"]).siblings("ul").children(":first").siblings().remove();
	if(woType == "") {
		$(param["errorTypeSelector"]).next().attr("disabled", "disabled");
	} else {
		var data = {"woType": woType };
		if("errorTypeStatus" in param) {
			data["status"] = param["errorTypeStatus"];
		}
		$.ajax({
			type: "POST",
	        url: "/BT-LMIS/control/workOrderPlatformStoreController/listErrorTypeByWoType.do",
	        dataType: "json",
	        data: data,
	        success: function(errorType) {
        		var content1 = "";
        		var content2 = "";
				//
				for(var temp in errorType){
					content1 += '<option value="' + errorType[temp].id + '">' + errorType[temp].type_name + '</option>';
					content2 += '<li class="m-list-item" data-value="' + errorType[temp].id + '">' + errorType[temp].type_name + '</li>';
				}
        		// 装上之后的值
				$(param["errorTypeSelector"] + " option:eq(0)").after(content1);
				$(param["errorTypeSelector"]).siblings("ul").children(":first").after(content2);
				$(param["errorTypeSelector"]).next().attr("disabled", false);
	        }
		});
	}
	// 初始化显示内容
	initSelectFilter(param["errorTypeSelector"]);
//	if(!("check_flag" in param)) {
//		// 清空工单类型对应级别与异常类型校验信息
//		initCheckInfo("#"+ exceptionId);
//	}
}

//查询运单
function searchWaybill(event){
	if(event == null || event.keyCode == 13) {
		var orderNo = $("#platform_id").val();
		if(orderNo == null || orderNo == "") {
			alert("请输入查询条件");
		}else{
			$("#add_list").load(root+"control/workOrderPlatformStoreController/getOrder.do?orderNo=" + orderNo);
			$("#sel_flag").val("1");
		}
	}
}

function setChecked(num){
	var obj = "#operation_system"+num;
	var name = "[name='operation_system"+num+"']";
	alert($(name));
	$(name).attr("checked","true")
	alert(name);
	alert($(name).val());
	alert(document.getElementsByName(name).checked);
}

//提交和暂存
function saveTemporary(type){
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
	//标题
	var title = $("#title").val();
	if(title==''){
		alert("请填写标题！");
		return;
	}
	//问题所属店铺
	var problem_store = $("#problem_store_display").val();
	if(problem_store==''){
		alert("请选择问题所属店铺！");
		return;
	}
	//操作系统
	var operation_system = $("#operation_system").val();
	if(operation_system==''){
		alert("请选择操作系统！");
		return;
	}
	
	var woNoStr = $("#woNoLOG").val();
	
	//所属组别
	var createGroup = $("#create_group").val();
	if(createGroup==""){
		alert("请填写所属组别！");
		return;
	}
	//备注
	var remark = $("#remark").val();
	if(remark==""){
		alert("请填写问题描述！");
		return;
	}
	//ID集合
	var order_id= [];
  	$("input[name='ckbs']:checked").each(function(){
  		// 将选中的值添加到数组priv_ids中
  		order_id.push($.trim($(this).val()));
  	});
  	//平台订单号 弹窗 去掉
	/*if(order_id.length==0&&type==0){
 		alert("请输入平台订单号！");
 		$("#platformId_form").modal();
		// 隐藏遮罩
		$(".modal-backdrop").hide();
  		return;
  	}*/
	var action = ''; 
	var version = $("#version").val();
	var expectation_process_time = $("#expectation_process_time").val();
	if(type==2||type==0){
		if(!confirm("是否提交工单？")) {
			return;
		}
		var time1 = new Date(); 
		var time2 = new Date(expectation_process_time);
		var time3 = (time2-time1)/3600000;
		if(time3<1){
			alert("期望处理时间要至少大于当前时间1小时");
			return;
		}
		action='SUBMIT';
	}
	if(type==1){
		if(!confirm("是否暂存工单？")) {
			return;
		}
		action='SAVE';
	}
	//异常类型
	var error_type = $("#error_type_display").val();
	var platformNumber = $("#platform_id_add").val();
	if(platformNumber == ""&&order_id.length==0){
		platformNumber = -99999;
	}
	//工单id
	var wo_id = $("#wo_id_form").val();
	//文件
	var fileName_common = $("#fileName_common").val();
	//orderFlag
	var orderFlag = $("#orderFlag").val();
	//运单
	var waybill = $("#waybill_add").val();
	var related_number = $("#related_number").val();
//	var problem_store_display = "";
//	if(problem_store!=""){
//		problem_store_display = $("#problem_store_display").next().prop("placeholder");
//	}
	
	$("#saveTemporary").hide();
	loadingStyle();
	
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/workOrderPlatformStoreController/workOrderAction.do",
        dataType: "json",
        data: {action:action,woId:wo_id,woType:wo_type,errorType:error_type,orderId:order_id,issueDescription:remark,
        	accessory:fileName_common,version:version,orderFlag:orderFlag,waybill:waybill,platformNumber:platformNumber,
        	process_department:process_department,related_number:related_number,
        	/*problem_store_display:problem_store_display,*/
        	title:title,operation_system:operation_system,problem_store:problem_store,
        	expectation_process_time:expectation_process_time,createGroup:createGroup,woNoStr:woNoStr},
        success: function(result) {
        	alert(result.msg);
        	if(result.flag) {
        		backDress();
    		}
    	},
        error: function() {
        	alert("系统异常-error");
        }
	});
}
//返回main
function backDress(){
	openDiv(root+'control/workOrderPlatformStoreController/backToMain.do?pageName=main&tableName=wo_store_master');
	$("body").removeAttr("style");
	$("body").removeClass();
}
//取消绑定
function cancelBan(){
	$("#orderFlag").val('1');
	$("#sc_content").children(":first").children(":first").empty();
	$('#search_btn').css('display','');
	$('#cancelBan_btn').css('display','none');
}

//附件列表加载
function getFileList(){
	var names= $("#fileName_common").val().split("_");
	var htm= "";
	for(var k= (names.length - 1);k > 0; k--) {
		var col;
		if(k % 2 == 0) {
			col='#F0F8FF';
				
		} else {
			col='#FFFFFF';
			
		}
		htm+= "<div style='width:100%;' id=file_" + k + "><div style='float:left;width:80%;border-bottom: 1px dashed gray;background-color:" + col + ";'>";
		if(names[k].indexOf("jpg") >= 0 || names[k].indexOf("png") >= 0 || names[k].indexOf("jpeg") >= 0) {
			htm+= "<a href='" + FileDown + names[k].split("#")[0] + "' target='_blank'><img alt='" + names[k].split("#")[1] + "' title=" + names[k].split("#")[1] + " src='" + FileDown + names[k].split("#")[0] + "' width='70' height='50'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + names[k].split("#")[1] + names[k].split("#")[2] + "</a>"
			
    	} else {
    		htm+= "<a href='" + FileDown + names[k].split("#")[0] + "?n=" + names[k].split("#")[1] + "' target='_blank'><img alt='" + names[k].split("#")[1] + "' title=" + names[k].split("#")[1] + " src='" + root + "uploadify/file.png' width='70' height='50'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + names[k].split("#")[1] + names[k].split("#")[2] + "</a>"
    	
    	}
		htm+= "</div><div style='float:right;text-align:right;width:20%;height:51px;border-bottom: 1px dashed gray;line-height:50px;background-color:" + col + ";'>"
		if($("#judgeType").val() == undefined || ($("#judgeType").val() == "process" && $("#woProcessStatus_woDisplay").text() == "处理中" && $("#waybillGenerated").children().length==0)) {
			htm+= "<a href='javascript:;' style='color:red;' onclick='removeFile(\"" + k + "\",\"" + names[k] + "\")'>删除</a>";
			
		}
		htm+= "</div></div>";
		
	}
	$("ims").html(htm);
	
}

function delTempWorkOrder(woId) {
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/workOrderPlatformStoreController/delTempWorkOrder.do",
        dataType: "json",
        data: {woId: woId},
        success: function(result) {
        	alert(result.msg)
        	if(result.flag) {
        		openDiv(root+'control/workOrderPlatformStoreController/platform.do?pageName=main&tableName=wo_store_master');
        	}
        },
        error: function() {
        	alert("系统异常-error");
        }
	});
}

var isload = false;
function loadTeam(){
	if ($(".table tbody tr input[type='checkbox']:checked").length == 0) {
		alert("请选择一行");
		return;
	}
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
var isLoadedUsers = false;
function loadUsers(){
	if ($(".table tbody tr input[type='checkbox']:checked").length == 0) {
		alert("请选择一行");
		return;
	}
	if (!isLoadedUsers) {
		sendDataByUrl(
			'control/workOrderPlatformStoreController/getEmpByTeamId.do',
			{},
			function(data){
				isLoadedUsers = true;
				if (data.length > 0){
					$("#employee").html("");
				}
				for (var i = 0; i < data.length; i++) {
					$("#employee").append("<option value=' " + data[i].id + "'>" + data[i].name + "</option>");
				}
				$("#transmit-model").modal({backdrop: "static", keyboard: false});
				// 隐藏遮罩
				$(".modal-backdrop").hide();
			}
		);
	} else {
		$("#transmit-model").modal({backdrop: "static", keyboard: false});
		// 隐藏遮罩
		$(".modal-backdrop").hide();
	}
}
// 转发
function transmitMain(){
	if ($(".table tbody tr input[type='checkbox']:checked").length == 0) {
		alert("请选择一行");
		return;
	}
	if (!confirm("确认转发吗？")) {
		return;
	}
	loadingStyle();
	var wodIds = [];
	$(".table tbody tr input[type='checkbox']:checked").each(function() {
		wodIds.push({ "woId" : $(this).val(),"version" : $(this).data("version")});
	});
	sendData({
		action : 'FORWARD',
		woId : JSON.stringify(wodIds),
		accessory : '',            
		processInfo : '',
		employeeId : $("#employee").val(),//员工
		groupId : $("#SOgroup").val()
	},
	function(data){alert(data.msg);if (data.flag) {$("#transmit_close").click();goBack();}else{cancelLoadingStyle();}}
	);
}
// 完结
function finishMain(){
	if ($(".table tbody tr input[type='checkbox']:checked").length == 0) {
		alert("请选择一行");
		return;
	}
	if (!confirm("确认完结吗？")) {
		return;
	}
	loadingStyle();
	var wodIds = [];
	$(".table tbody tr input[type='checkbox']:checked").each(function() {
		wodIds.push({ "woId" : $(this).val(),"version" : $(this).data("version")});
	});
	sendData({
		action : 'OVER',
		woId : JSON.stringify(wodIds),
		accessory : '',           
		processInfo : ''
	},
	function(data){alert(data.msg);if (data.flag) {goBack();}else{cancelLoadingStyle();}}
	);
}
// 取消
function cancleMain(){
	if ($(".table tbody tr input[type='checkbox']:checked").length == 0) {
		alert("请选择一行");
		return;
	}
	if (!confirm("确认取消吗？")) {
		return;
	}
	loadingStyle();
	var wodIds = [];
	$(".table tbody tr input[type='checkbox']:checked").each(function() {
		wodIds.push({ "woId" : $(this).val(),"version" : $(this).data("version")});
	});
	sendData({
		action : 'CANCLE',
		woId : JSON.stringify(wodIds),
		accessory : '',           
		processInfo : ''
	},
	function(data){alert(data.msg);if (data.flag) {goBack();}else{cancelLoadingStyle();}}
	);
}
// 获取
function empGetWorkOrder(){
	if ($(".table tbody tr input[type='checkbox']:checked").length == 0) {
		alert("请选择一行");
		return;
	}
	if (!confirm("确认获取吗？")) {
		return;
	}
	loadingStyle();
	var wodIds = [];
	$(".table tbody tr input[type='checkbox']:checked").each(function() {
		wodIds.push({ "woId" : $(this).val(),"version" : $(this).data("version")});
	});
	sendDataByUrl(
			'control/workOrderPlatformStoreController/empGetWorkOrder.do',
			{woId: JSON.stringify(wodIds)},
			function(data){alert(data.msg);if (data.flag) {goBack();}else{cancelLoadingStyle();}}
	);
}

function sendData(data,callback){
	sendDataByUrl('control/workOrderPlatformStoreController/workOrderAction.do',data,callback);
}
function sendDataByUrl(url,data,callback){
	$.ajax({
		url : root + url,
		data : data,
		type : 'post',
		dataType : 'json',
		success : callback,
		error : callback
	});
}

function exportWorkOrder() {
	window.location.href="/BT-LMIS/control/workOrderPlatformStoreController/exportWorkOrder.do?"
		+ $(".search_form").serialize() + "&tableName=wo_store_master&tabNo=" + $("#tabNo").val() + "&sortColumn=" + $("#sort_column").val() + "&sort=" + $("#sort").val();
}

function removeFile(id, name){
	$("#file_"+id).empty();
	var nameS=$("#fileName_common").val();
	var names=nameS.split("_");
	var newName="";
	for(var s = 1; s < names.length; s++){
		if(names[s] != name) {
			newName= newName+"_"+names[s];
		}
	}
	$("#fileName_common").val(newName)
	
}

function clearText(){
	$("#waybill_add").val("");
	$("#platform_id_add").val("");
}
function details() {
	if ($(".table tbody tr input[type='checkbox']:checked").length == 0) {
		alert("请选择一行");
		return;
	}
	if ($(".table tbody tr input[type='checkbox']:checked").length > 1) {
		alert("请只选择一行");
		return;
	}
	var wodId = "";
	$(".table tbody tr input[type='checkbox']:checked").each(function() {
		wodId=$(this).val();
	});
	openDiv('/BT-LMIS/control/workOrderPlatformStoreController/transJSP.do?id=' + wodId);
}


function test(){
	alert(1);
}

//------------------------------------------------------------------------------------------------------

function toWoAssociationMain(){
	$("#waybillGeneratedSS").children().remove();
	$("#workOrderInfoSS").children().remove();
	$("#woWaybillGenerated_form2").modal();
	$(".modal-backdrop").hide();
}
function searchWoNoMain(){
	var woNo = $("#woNoStrSS").val();
	if(woNo==''){
		alert("请输入模糊查询的条件！");
		return;
	}
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/workOrderPlatformStoreController/getWorkOrderByWoNo.do",
        dataType: "json",
        data: {woNo: woNo},
        success: function(result) {
        	if(result.result_code="SUCCESS") {
        		// 刷新列表
        		$("#waybillGeneratedSS").children().remove();
        		var tr="";
        		for(var i=0; i<result.wo.length; i++) {
        			tr+="<tr><td title='"+result.wo[i].wo_no+"'><a style='cursor:pointer;' onclick='getWorkOrderInfo10(\""+result.wo[i].id+"\");focusWorkOrderMain($(this));'>"+result.wo[i].wo_no+"</a></td><td><a onclick='focusWorkOrderMain2($(this));giveWoNoMain(\""+result.wo[i].wo_no+"\");'>选择</a></td></tr>";
        		}
        		$("#waybillGeneratedSS").append(tr);
        	} else {
        		alert(result.result_content);
        	}
        },
        error: function() {
        	alert("系统异常-error");
        }
	});
	}
function focusWorkOrderMain(a) {
	a.parent().parent().siblings().each(function(){
		$(this).children(":first").children(":first").removeClass("red");
		
	})
	if(!a.hasClass("red")) {
		a.addClass("red");
	}
}
function focusWorkOrderMain2(a) {
	a.hide();
}

function getWorkOrderInfo10(obj) {
	$("#workOrderInfoSS").children().remove();
	openIdDiv("workOrderInfoSS",
			"/BT-LMIS/control/workOrderPlatformStoreController/workOrderHandle.do?woId="+obj+"&readonly=1");
}

function giveWoNoMain(obj){
	var a = $("#woNoLOG").val();
	if(a==''){
		$("input[name='woNoLOG']").val(obj);
	}else{
		$("input[name='woNoLOG']").val(a+","+obj);
	}
}

function clearInputMain(){
	$("input[name='woNoLOG']").val('');
	searchWoNoMain();
}

function selectUploadModel(){
	   $("#batch_store").modal('show');
	   $(".modal-backdrop").hide();
} 

/**
* 批量回复
*/
function imp(){
    if($("#photoCover").val().length==0){
       alert("请先选择文件");
       return;
    }
    loadingStyle();
		$("#imps").hide();
	$.ajaxFileUpload({
		//上传地址
		url: root+'control/workOrderPlatformStoreController/batchProcess.do',
		//是否需要安全协议，一般设置为false
        secureuri: false, 
      	//文件上传域的ID
		fileElementId: 'file1', 
		contentType:'application/x-www-form-urlencoded;charset=UTF-8',
		//返回值类型 一般设置为json
		dataType: 'json', 
		//服务器成功响应处理函数
		success: function (data, status){
			alert(data.msg);
			goBack();
		},error: function (data, status, e){
			//服务器响应失败处理函数
//				openDiv(root+'control/shopGroupController/import.do','');
       	}
	})
	cancelLoadingStyle();
	 $("#batch_store").modal('hide');
	$("#imps").show();
    return;
}

function ajaxFileUpload() {
   loadingStyle();
	$.ajaxFileUpload({
		url: '${root}/control/groupMessageController/upload.do', //用于文件上传的服务器端请求地址
     secureuri: false, //是否需要安全协议，一般设置为false
		fileElementId: 'file1', //文件上传域的ID
		dataType: 'json', //返回值类型 一般设置为json
		data:{'group':$('#gId').val()},
			//服务器成功响应处理函数
			success: function (data, status){
				//
			if(data.code==1){alert('操作成功');cancelLoadingStyle();
			queryStore($("#gId").val());
			}else{alert('操作失败');cancelLoadingStyle();}
			 $("#batch_store").modal('hide');
			 window.open('${root}/DownloadFile/'+data.path);
			},error: function (data, status, e){
				//服务器响应失败处理函数
				alert('操作失败');
				cancelLoadingStyle();
				 $("#batch_store").modal('hide');
           	}
		})
        return false;
	}
$(function () {
    $("#upload").click(function () {
    	//loadingStyle();
        ajaxFileUpload();
    });

	$('input[id=file1]').change(function() { 
        $('#photoCover').val($(this).val());
    }); 
})

 function resize(obj){ 
  var ifrm=obj.contentWindow.document.body; 
  ifrm.style.cssText="margin:0px;padding:0px;overflow:hidden"; 
  var div=document.createElement("img"); 
  div.src=obj.src; 
  obj.height=div.height; 
  obj.width=div.width; 
  } 

function showNewer(){
	$("#newer-model").modal({backdrop: "static", keyboard: false});
	// 隐藏遮罩
	$(".modal-backdrop").hide();
}
function bigerPic(){
	$("#newer-model").modal('hide');
	$("#newer_iframe").style.height="700px";
	$("#newer_width").style.width="1600px";
	showNewer();
}
function smallPic(){
	$("#newer_iframe").style.height="500px";
	$("#newer_width").style.width="1200px";
}