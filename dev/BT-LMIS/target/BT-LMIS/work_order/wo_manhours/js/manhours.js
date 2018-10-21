$(function(){
	$("#date").daterangepicker({
        applyClass: 'btn-sm btn-success',
        cancelClass: 'btn-sm btn-default',
        locale: {
            applyLabel: '确认',
            cancelLabel: '取消',
            fromLabel: '起始时间',
            toLabel: '结束时间',
            customRangeLabel: '自定义',
            firstDay: 1
        },
        ranges: {
            // '最近1小时': [moment().subtract('hours',1), moment()],
            '今日': [moment().startOf('day'), moment()],
            '昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
            '最近7日': [moment().subtract('days', 6), moment()],
            '最近30日': [moment().subtract('days', 29), moment()],
            '本月': [moment().startOf("month"),moment().endOf("month")],
            '上个月': [moment().subtract(1,"month").startOf("month"),moment().subtract(1,"month").endOf("month")],
            '清空': ['0000-01-01','0000-01-01']
        },
        opens: 'left', // 日期选择框的弹出位置
    }, function(start, end, label) {
    	if (label == '清空') {
    		$("#date").val("");
    	}
    	
    	
       	console.log(start.toISOString(), end.toISOString(), label);
       	
	});
	$("#table_content tbody tr").dblclick(function(){
		$("#manhours_id").val($(this).children(":first").children(":first").val());
		$("#express_number_form").val($(this).children().eq(2).text());
		$("#name_form").val($(this).children().eq(3).text());
		$("#date_form").val($(this).children().eq(1).text());
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
		if(new Date($(this).children().eq(1).text()) < new Date(date)) {
			$(".modal-footer").hide();
			$("#manhours").prop("readonly", true);
			$("#formLabel").text("历史工时");
			
		} else {
			$(".modal-footer").show();
			$("#manhours").prop("readonly", false);
			$("#formLabel").text("工时编辑");
			
		}
		$("#manhours").val($(this).children().eq(4).text());
		$("#allocated").val($(this).children().eq(5).text());
		$("#manhours_form").modal();
		// 隐藏遮罩
		$(".modal-backdrop").hide();
		
	});
	
	
});

function update() {
	if(!confirm("确认保存？")) {
		return;
		
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
	if(manhours - $("#allocated").val() < 0) {
		alert("维护工时必须大于等于已分配工时！");
		return;
		
	}
	$.ajax({
		type: "POST",
        url: "/BT-LMIS/control/manhoursController/update.do",
        dataType: "json",
        data: {
        	"id": $("#manhours_id").val(),
        	"manhours": manhours
        	
        },
        success: function(result) {
        	if(result.result_code == "ERROR") {
        		
        	} else {
            	alert(result.result_content);
            	if(result.result_code == "SUCCESS"){
            		$("#manhours_form").modal("hide");
            		find();
            		
            	}
            		
        	}
        	
        }
        
	});
	
}
var isSubmit = true;
function del(){
	if($("input[name='ckb']:checked").length == 0){
		alert("请选择一行!");
		return;
		
	}
	if(!confirm("确定删除所选工时?")){
	  	return;
	  	
	}
	var priv_ids= [];
  	$("input[name='ckb']:checked").each(function(){
  		
  		if ($.trim($(this).data("status")) == '启用') {
  			alert("已启用工时禁止删除");
  			isSubmit = false;
  			return;
  		}
  		
  		// 将选中的值添加到数组priv_ids中
		priv_ids.push($.trim($(this).val()));
		
  	});
  	
  	if (isSubmit) {

  	  	$.ajax({
  			type: "POST",
  	       	url: root+ "/control/manhoursController/del.do?",
  	       	dataType: "json",
  	       	data: "privIds="+ priv_ids,
  	       	success: function(result){
  	       		if(result.result_code == "ERROR"){
  					alert(result.result_content);
  					
  	    	   	} else {
  	    	   		if(parseInt(result.total) - parseInt(result.success) == 0){
  	    	   			alert("删除成功！");
  	    	   			
  	    	   		} else {
  	    	   			alert("删除成功：" + parseInt(result.success) + "；删除失败：" + (parseInt(result.total) - parseInt(result.success)) + "；失败原因：历史工时及当前使用工时无法删除！（可禁用）");
  	    	   			
  	    	   		}
  	    	   		
  	    	   	}
  				find();
  				
  	       	}
  	  	
  		});
  	}
	
}

function submitShiftStatus(ids, status) {
	$.ajax({
		type: "POST",  
       	url: "/BT-LMIS/control/manhoursController/shiftStatus.do",
       	dataType: "json",
       	data:  {
       		"ids": ids,
       		"status": status
       		
       	},
       	success: function (result) {
       		alert(result.result_content)
       		if(result.result_code == "SUCCESS"){
       			find();
       			
       		}
       		
     	}
       	
	});
	
}

function batchShiftStatus(status) {
	if($("input[name='ckb']:checked").length == 0){
		alert("请选择一行!");
		return;
		
	}
	var text= "";
	if(status == "false") {
		text= "禁用";
		
	} else {
		text= "启用";
		
	}
	if(!confirm("是否" + text + "所选工时？")) {
		return;
		
	}
	var ids= [];
  	$("input[name='ckb']:checked").each(function(){
  		// 将选中的值添加到数组priv_ids中
  		ids.push($.trim($(this).val()));
		
  	});
  	submitShiftStatus(ids, status);
  	
}

function upStatus(id, status) {
	var text= "";
	if(status == "false") {
		text= "禁用";
		
	} else {
		text= "启用";
		
	}
	if(!confirm("是否" + text + "所选工时？")) {
		return;
		
	}
	var ids= [];
	ids.push(id);
	submitShiftStatus(ids, status);
	
}

function pageJump() {
	find();
	
}
	
function find() {
	openDiv("/BT-LMIS/control/manhoursController/query.do?employee_number="
		+ $("#employee_number").val()
		+ "&employee_name="
		+ $("#employee_name").val()
		+ "&date="
		+ encodeURIComponent($("#date").val())
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

function refresh() {
	$("#mh_form input").each(function(){
		$(this).val("");
		
	})
	find();
}