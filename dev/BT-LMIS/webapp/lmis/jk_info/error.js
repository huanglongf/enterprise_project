// 操作界面固定高度
$(".lmis-panel").height($(window).height() - 136);


function toUpdates() {
	if($("input[name='ckb']:checked").length>=1){
		if($("input[name='ckb']:checked").length>1){
			alert("只能选择一行!");
		}else{
			openDiv('${root}control/addressController/toUpdate.do?id='+$("input[name='ckb']:checked").val());
		}
	}else{
		alert("请选择一行!");
	}
}

function upStatus(id,status){
	if(status==0){
		$("#ip1_"+id).attr("readonly",false);
		$("#ip2_"+id).attr("readonly",false);
		$("#ip3_"+id).attr("readonly",false);
		$("#ip4_"+id).attr("readonly",false);
		$("#ip5_"+id).attr("readonly",false);
	}else{
		$("#ip1_"+id).attr("readonly",true);
		$("#ip2_"+id).attr("readonly",true);
		$("#ip3_"+id).attr("readonly",true);
		$("#ip4_"+id).attr("readonly",true);
		$("#ip5_"+id).attr("readonly",true);
		var delivery_address=$("#ip1_"+id).val();
		var province=$("#ip2_"+id).val();
		var city=$("#ip3_"+id).val();
		var state=$("#ip4_"+id).val();
		var detail_address=$("#ip5_"+id).val();
		var url=root+"/control/addressController/updateErrornData.do?delivery_address="+delivery_address+"&province="+province+"&city="+city+"&state="+state+"&detail_address="+detail_address+"&id="+id;
		$.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				alert(jsonStr.out_result_reason);
				pageJump('load','1');
			}
		});
	}
}


function 	anysis_data(){
	$("#but_2").attr("disabled","disabled");
	var url=root+"/control/addressController/anysis.do";
	$.ajax({
		type : "POST",
		url: url,  
		data:"",
		dataType: "json",  
		success : function(jsonStr) {
			alert(jsonStr.result_reason);
			pageJump('load','1');
		}
	});
	$("#but_2").attr("disabled",false);
}

function export_data(){
	var param=$("#search_form").serialize();
	var url=root+"control/addressController/exportExcel.do?"+param;
	window.location.href=url;
}

function export_data(){
	var param=$("#search_form").serialize();
	var url=root+"control/addressController/exportExcel.do?"+param;
	window.location.href=url;
}

function exportData(){
	var param=$("#search_form").serialize();
    var url=root+"control/addressController/exportData.do?"+param;
	window.location.href=url;
 }


function to_import_data(){
	openDiv(root+'control/addressController/import_main.do');
}

function pageJump(type) {
	var op=$("#page_type").val();
	loadingStyle();
	var param=$("#search_form").serialize();
	if (type=="load"){
	}else{
	 param=param+"&startRow="+$("#startRow").val()+"&endRow="+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val();
	}
	if(op=="1"){
	openIdDiv("data","/BT-LMIS/control/addressController/errorlist.do?"+param);
    }
	else if(op=="2"){
	openIdDiv("data","/BT-LMIS/control/addressController/toUpload.do?"+param+"&bat_id="+$("#bat_id_param").val());	
	}
	setTimeout(cancelLoadingStyle(), 4000);
}		 

function back_to_import(){
	to_import_data();
}

function refreshCondition(){
	document.getElementById("search_form").reset();
}


//function add_error_address(id){
//	$("#address_form").modal('show');
//	var delivery_address=$("#ip1_"+id).val();
//	var province=$("#ip2_"+id).val();
//	var city=$("#ip3_"+id).val();
//	var state=$("#ip4_"+id).val();
//	var detail_address=$("#ip5_"+id).val();
//	$("#province_lable").html(province);
//	$("#city_lable").html(city);
//	$("#state_lable").html(state);
//	$("#detail_lable").html(detail_address);
//	$("#detail_input").val(detail_address);
//	$("#id_param").val(id);
//    //校验市是否存在
//}


function add_error_address(){
	$("#address_form").modal('show');
}

function change_select(){
	var province_id=$("#province_select").val();
}

function change_level(){
	var add_level=$("#add_level").val();
	if(add_level=="1"){
		$("#real_province").css("display","block");
		$("#real_city").css("display","none");
		$("#real_state").css("display","none");
	}
	else if(add_level=="2"){
		$("#real_province").css("display","block");
		$("#real_city").css("display","block");
		$("#real_state").css("display","none");
	}
	else if(add_level=="3"){
		$("#real_city").css("display","block");
		$("#real_state").css("display","block");
		$("#real_province").css("display","block");
	}else{
		$("#real_city").css("display","none");
		$("#real_state").css("display","none");
		$("#real_province").css("display","none");		
	}
}


function save_address(){
//	var error_province=$("#province_lable").html();
//	var error_city=$("#city_lable").html();
//	var error_state=$("#state_lable").html();
//	var real_province=$("#province_input").val();
//	var real_city=$("#city_input").val();
//	var real_state=$("#state_input").val();
	var real_address="";
	var error_address=$("#error_address").val();
	var leval=$("#add_level").val();
	
	if(error_address.length==0){
		alert("异常地址不能为空");
		return;
	}
	if(leval==1){
		real_address=$("#provice_code").find("option:selected").text();
	}else if(leval==2){
		real_address=$("#city_code").find("option:selected").text();
//		real_address=$("#city_code").val();
	}else if(leval==3){
		real_address=$("#state_code").find("option:selected").text();
//		real_address=$("#state_code").val();
	}else{
		alert("请先选择解析层级");
		return;
	}
	var data="error_address="+error_address+"&real_address="+real_address+"&level="+leval;
	$("#btn_submit").attr("disabled","disabled");
	var url=root+"/control/addressController/add_address.do";
	$.ajax({
		type : "POST",
		url: url,  
		data:data,
		dataType: "json",  
		success : function(jsonStr) {
			alert(jsonStr.result_reason);
			if(jsonStr.result_flag==1){
				$("#btn_back").click();
				pageJump('load','1');
			}
		}
	});
	$("#btn_submit").attr("disabled",false);
}


function if_exist_address(){
	var url=root+"/control/addressController/if_exist_address.do";
	$.ajax({
		type : "POST",
		url: url,  
		data:data,
		dataType: "json",  
		success : function(jsonStr) {
			if(jsonStr.result_flag){
			}
		}
	});
}

function refresh_tranaction(){
	var id='';
	if($("input[name='ckb']:checked").length>=1){
		var priv_ids =[];
		$("input[name='ckb']:checked").each(function(){
			priv_ids.push($.trim($(this).val()));
		});
			if(!confirm("确定修改吗?")){
				return;
			}
			var url=root+"/control/addressController/refresh_tranaction.do?priv_ids="+priv_ids;
			$.ajax({
				type: "POST",
				url:url,
				dataType: "json",
				data:"",
				success: function (data) {
					alert(data.result_reason);
					pageJump('load','1');
				}  
			});
	}else{
		alert("请选择一行!");
	}
}

function get_system_code(){
	var url=root+"/control/addressController/get_code.do";
	$.ajax({
		type : "POST",
		url: url,  
		data:'',
		dataType: "json",  
		success : function(jsonStr) {
			alert(jsonStr.serial_code);
		}
	});
}