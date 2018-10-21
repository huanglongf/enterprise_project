$(function(){
	$("input:radio").change(function() {
		if($("#oneAdd").prop("checked") == true) {
			$("#oneDate_div").show();
			$("#rangeDate_div").hide();
			
		} else {
			$("#rangeDate_div").show();
			$("#oneDate_div").hide();
			
		}
		
	});
	
	$(".date-picker").datepicker({
		language: 'zh-CN',
		autoclose: true,
		todayHighlight: true,
		todayBtn: 'linked',
		format: "yyyy-mm-dd"
		
	}).next().on(ace.click_event, function(){
		$(this).prev().focus();
		
	});
	$(".date-picker").datepicker("setDate", new Date());
	
	$("#rangeDate").daterangepicker(null, function(start, end, label) {});
	var now = new Date();
	var month = (now.getMonth() + 1);
	if(month < 10) {
		month = "0" + month;
		
	}
	var day = now.getDate();
	if(day < 10) {
		day = "0" + day;
		
	}
	var date = now.getFullYear() + "-" + month + "-" + day;
	$("#rangeDate").val(date + " - " + date);
	
});

function add() {
	if(!confirm("确认提交？")) {
		return;
		
	}
	
	if($("input[name='ckb2']").length == 0){
		alert("请选择账户!");
		return;
		
	}
	var date= "";
	if($("#oneAdd").prop("checked") == true) {
		if($("#oneDate").val() == "") {
			alert("请选择工时日期！");
			return;
			
		}
		date = $("#oneDate").val();
		
	} else {
		if($("#rangeDate").val() == "") {
			alert("请选择工时日期范围！");
			return;
			
		}
		date = $("#rangeDate").val();
		
	}
	var manhours= $("#manhours").val();
	if(manhours == "") {
		alert("请填写工时！");
		return;
		
	}
	if(isNaN(manhours) || manhours < 0 || manhours > 1440){
		alert("工时不合法！");
		return;
		
	}
	var accounts= [];
	$("input[name='ckb2']").each(function(){
		accounts.push($.trim($(this).val()));
		
  	});
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/manhoursController/add.do",
        dataType: "json",
        data: "accounts=" + accounts + "&date=" + encodeURIComponent(date) + "&manhours=" + manhours,
        success: function(result) {
        	if(result.result_code == "ERROR") {
        		
        	} else {
        		if(result.failure == 0) {
            		alert("新增工时成功！");
            		openDiv("/BT-LMIS/control/manhoursController/query.do");
            		
            	} else {
            		alert("工时新增成功数：" + result.success + "；失败数：" + result.failure + "；失败原因：单一账户单天仅存唯一工时记录！");
            		
            	}
        		
        	}
        	
        }
        
	});
	
}

function clearSubordinates() {
	if($("input[name='ckb2']:checked").length == 0){
		alert("请选择账户!");
		return;
		
	}
	// 遍历每一个name为priv_id的复选框，其中选中的执行函数
  	$("input[name='ckb2']:checked").each(function(){
  		$("#tr_" + $.trim($(this).val())).remove();
  		
  	});
  	if($("#checkAll2").prop("checked") == true) {
  		$("#checkAll2").prop("checked", false);
  		$("#checkAll2").prop("disabled", true);
  		
  	}
  	find();
	
}

function importSubordinates() {
	if($("input[name='ckb']:checked").length == 0){
		alert("请选择账户!");
		return;
		
	}
	// 遍历每一个name为priv_id的复选框，其中选中的执行函数
  	$("input[name='ckb']:checked").each(function(){
		var employee= $.trim($(this).val()).split(",");
		var tr= '<tr id= "tr_'
			+ employee[0]
			+ '"><td class= "td_ch no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 4%;" ><input name= "ckb2" type= "checkbox" value= "'
			+ employee[0]
			+ '" ></td><td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 32%;" title= "'
			+ employee[1]
			+ '" >'
			+ employee[1]
			+ '</td><td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 32%;" title= "'
			+ employee[2]
			+ '" >'
			+ employee[2]
			+ '</td><td class= "td_cs no-padding-top no-padding-bottom no-padding-left no-padding-right" style= "width: 32%;" title= "'
			+ employee[3]
			+ '" >'
			+ employee[3]
			+ '</td></tr>';
		$("#table_content_2").find("tbody").append(tr);
		$(this).prop("checked", false);
		$(this).prop("disabled", true);
		
  	});
  	if($("#checkAll").prop("checked") == true) {
  		$("#checkAll").prop("checked", false);
  		$("#checkAll").prop("disabled", true);
  		
  	}
  	
  	if($("#checkAll2").prop("disabled") == true) {
  		$("#checkAll2").prop("disabled", false);
  		
  	}
	
}

function inverseCkb_s(items) {
	$('[name=' + items + ']:checkbox').each(function() {
		if($(this).prop("disabled") == false) {
			this.checked = !this.checked;
			
		}
		
	});
	
}

function refreshSubordinates() {
	$("#group_name").val("");
	$("#employee_number").val("");
	$("#name").val("");
	find();
	
}

function keydown_find(event) {
	if(event.keyCode == 13){
		find();
		
	}
	
}

function pageJump() {
	find();
	
}

function find() {
	openIdDiv("subordinates", 
		"/BT-LMIS/control/manhoursController/toAddForm.do?query=true&group_name="
		+ encodeURI($("#group_name").val())
		+ "&employee_number="
		+ $("#employee_number").val()
		+ "&name="
		+ encodeURI($("#name").val())
		+ "&startRow="
		+ $("#startRow").val()
		+ "&endRow"
		+ $("#startRow").val()
		+ "&page="
		+ $("#pageIndex").val()
		+ "&pageSize="
		+ $("#pageSize").val()
			
	);
	
}