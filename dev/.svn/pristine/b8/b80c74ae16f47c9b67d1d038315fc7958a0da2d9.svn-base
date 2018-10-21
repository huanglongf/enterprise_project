/**
 * 获取公式Table
 */
function getFormula_tab(contract_id){
	var url=root+"/control/transOrderController/getFormula_tab.do?contract_id="+contract_id+"&belong_to="+$("#contractOwner").val();
	var htm_td="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				var text="<table class='with-border' border='1'>"
				       +"<tr class='title'>"
				       +"<td>序号</td><td>公式</td><td>详细信息</td>"
				       +"<td>操作</td></tr>";
				$("#Formula_tab_data").val(JSON.stringify(jsonStr));
				for(i=0;i<jsonStr.length;i++){
					htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+jsonStr[i].formula+"</td><td><a href='javaScript:;' onclick='up_tab(\""+i+"\",\""+jsonStr[i].cat_type+"\")'>详细信息</a></td><td><a href='javaScript:;' onclick='up_tab(\""+i+"\",\""+jsonStr[i].cat_type+"\")'>编辑</a>/<a href='javaScript:;' onclick='del_tab(\""+jsonStr[i].id+"\")'>删除</a></td></tr>"
				}
				$("dt1").html(text+htm_td+"</table>");
			}
		});
}

function getcarriageInfo(contract_id){
	var url=root+"/control/transOrderController/getCarriageInfo.do?contract_id="+contract_id+"&belong_to="+$("#contractOwner").val();
	var htm_td="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				if(jsonStr.if_disount=='1'){
					$("#carriage_discount_switch").attr("checked","checked");
					$("#disDiv").css("display","block");
				}else{
					$("#carriage_discount_switch").attr("checked",false);
					$("#disDiv").css("display","none");	
				}
			}
		});
}


/**
 * 获取折扣Table
 */
function getDiscountTd(contract_id,cat_id,dtx){
	var url=root+"/control/transOrderController/getDiscount_tab.do?contract_id="+contract_id+"&cat_id="+cat_id+"&belong_to="+$("#contractOwner").val();
	var htm_td="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				var text="<table class='with-border' border='1'>"
				       +"<tr class='title'>"
				       +"<td>序号</td><td>产品类型</td><td>阶梯类型</td>"
				       +"<td>区间</td><td>折扣率</td><td>收费</td><td>操作</td></tr>";
				for(i=0;i<jsonStr.length;i++){
					if(jsonStr[i].discount_type=="2"){
						htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+jsonStr[i].good_type+"</td><td>"+jsonStr[i].interval_type_name+"</td><td>"+jsonStr[i].section_native+"</td><td></td><td>"+jsonStr[i].discount+"</td><td><a href='javaScript:;' onclick='del_tab_dic(\""+jsonStr[i].id+"\")'>删除</a></td></tr>"	
					}else{
						htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+jsonStr[i].good_type+"</td><td>"+jsonStr[i].interval_type_name+"</td><td>"+jsonStr[i].section_native+"</td><td>"+jsonStr[i].discount+"%</td><td></td><td><a href='javaScript:;' onclick='del_tab_dic(\""+jsonStr[i].id+"\")'>删除</a></td></tr>"	
					}
				}
				$(dtx).html(text+htm_td+"</table>");
			}
		});
}


/**
 * 获取折扣Table
 */
function getDiscountTd_for_offer(contract_id,cat_id,dtx){
	var url=root+"/control/transOrderController/getDiscount_tab.do?contract_id="+contract_id+"&cat_id="+cat_id+"&belong_to="+$("#contractOwner").val();
	var htm_td="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				var text="<table class='with-border' border='1'>"
				       +"<tr class='title'>"
				       +"<td>序号</td><td>产品类型</td><td>阶梯类型</td>"
				       +"<td>最低收费</td><td>区间</td><td>收费率</td><td>收费金额</td><td>操作</td></tr>";
				for(i=0;i<jsonStr.length;i++){
					if(jsonStr[i].discount_type=="1"){
						htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+jsonStr[i].good_type+"</td><td>"+jsonStr[i].interval_type_name+"</td><td>"+jsonStr[i].low_price+"</td><td>"+jsonStr[i].section_native+"</td><td>"+jsonStr[i].discount+"%</td><td>-</td><td><a href='javaScript:;' onclick='del_tab_dic(\""+jsonStr[i].id+"\")'>删除</a></td></tr>"	
					}else{
						htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+jsonStr[i].good_type+"</td><td>"+jsonStr[i].interval_type_name+"</td><td>"+jsonStr[i].low_price+"</td><td>"+jsonStr[i].section_native+"</td><td>-</td><td>"+jsonStr[i].discount+"元</td><td><a href='javaScript:;' onclick='del_tab_dic(\""+jsonStr[i].id+"\")'>删除</a></td></tr>"
					}
				}
				$(dtx).html(text+htm_td+"</table>");
			}
		});
}

function getSpecial(contract_id){
	var url=root+"/control/transOrderController/getSpecial.do?contract_id="+contract_id+"&belong_to="+$("#contractOwner").val();
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
					if(jsonStr.if_dispatch=='1'){
						$("#if_dispatch").attr("checked","checked");
					}else{
						$("#if_dispatch").attr("checked",false);
					}
				
			
					if(jsonStr.if_transfer=='1'){
						$("#if_transfer").attr("checked","checked");
					}else{
						$("#if_transfer").attr("checked",false);
					}
					
				
				if(jsonStr.if_unloading=='1'){
				        ediate('cat2_01');
						$("#if_unloading").attr("checked","checked");
						$("#choseCat").find("option[value="+jsonStr.unloading_cat+"]").attr("selected",true);
						if(jsonStr.unloading_cat=='1'){
							ediate('ts_cat1');
							$("#unloading_price").val(jsonStr.unloading_price);
						}
						if(jsonStr.unloading_cat=='2'){
							ediate('ts_cat2');
							$("#unloading_price2").val(jsonStr.unloading_price);
							$("#unloading_price3").val(jsonStr.unloading_price2);
						}
					}else{
						$("#if_unloading").attr("checked",false);
					}
					
				if(jsonStr.if_take=='1'){
				   $("#if_take").attr("checked","checked");
				   ediate('cat2_002');
				   $("#cat_type").find("checkbox[value="+jsonStr.take_type+"]").attr("selected",true);
				   $("#take_by_time_price").val(jsonStr.take_by_time_price);
				   $("#take_by_num_price").val(jsonStr.take_by_num_price);
					if(jsonStr.if_take_free=='1'){
						ediate('freeModel_div');
						$('#if_take_free').attr('checked','checked');
						if(jsonStr.if_take_free_by_weight=='1'){
							$("#if_take_free_by_weight").attr('checked','checked')
							$("#every_time_take_weight").val(jsonStr.every_time_take_weight);
						}
						if(jsonStr.if_take_free_by_volume='=1'){
							$("#if_take_free_by_volume").attr('checked','checked')
							$("#every_time_take_volume").val(jsonStr.every_time_take_weight);
						}
					}
					
					}else{
						$("#if_take").attr("checked",false);
					}
				

				

				
				if(jsonStr.if_upstairs=='1'){
					ediate('upFlooerDiv');
					$("#if_upstairs").attr('checked','checked');
					$("#upstairs_price").val(jsonStr.upstairs_price);		
				}
				if(jsonStr.if_send=='1'){
					$("#if_send").attr('checked','checked');
					ediate('freeReasonDiv');
					$("#send_price").val(jsonStr.send_price);	
					if(jsonStr.if_send_free=='1'){
						$("#if_send_free").attr('checked','checked');
						ediate('freeReasonDetailDiv');
						$("#send_free_weight").val(jsonStr.send_free_weight);	
						$("#send_free_volum").val(jsonStr.send_free_volum);
					}
				}		

				
			}
		});
}


function up_tab(idNum,catId){
	$("#add_text_yf").text("取消编辑");
	
	var obj=JSON.parse($("#Formula_tab_data").val());
	var id=obj[idNum].id;
	var formula=obj[idNum].formula;
	var cat_type=obj[idNum].cat_type;
	var low_weight=obj[idNum].low_weight;
	var standard_args=obj[idNum].standard_args;
	var contract_id=obj[idNum].contract_id;
	var status=obj[idNum].status;
	var if_disount=obj[idNum].if_disount;
	ediate('catDiv');
	$("#cat_type").find("option[value="+cat_type+"]").attr("selected",true);
	changeCat_edit(catId);
	if(catId=="0"){
		$("#standard_args").val(standard_args);
		$("#low_weight").val(low_weight);
	}
	if(catId=="1"){
		$("#low_weight2").val(low_weight);
	}
//	if(if_disount=="1"){
//		$("#id-button-borders").attr("checked","checked")
//		ediate('disDiv');
//	}
//	$("#but_text_"+idNum).text("确认修改");
    $("#Formula_tab_data_id").val(id);
}



/**
 * ajax提交通道
 * @param data
 * @param url
 * @param butId
 */
function ajax_save(data,url,butId){
	   $.ajax({
			type : "POST",
			url: url,  
			data:data,
			dataType: "json",  
			success : function(jsonStr) {
				if(jsonStr.result_flag==1){
					alert(jsonStr.result_reason);
	 				//$("#"+butId).attr("disabled",true);
					getFormula_tab($("#id").val());
					getDiscountTd($("#id").val(),'1','dt2');
	 				getDiscountTd_for_offer($("#id").val(),'2','td_offer');
				}else{
					alert(jsonStr.result_reason);
				}
			}
		});
}


function ajax_save_getId(data,url,butId){
	   $.ajax({
			type : "POST",
			url: url,  
			data:data,
			dataType: "json",  
			success : function(jsonStr) {
			alert(jsonStr.result_reason);
			if(jsonStr.id!=""){
			 $("#"+butId).val(jsonStr.id);
			 }
			}
		});
}



function show_div() {
	var divId = $("#disCont").val();
	var divObj = $("div[id^='discount']");
	if(divId==1){
	   $("#discount_1x").css("display", "block");
	   $("#discount_2").css("display", "none");
	 }else if(divId>1){
	   $("#discount_1x").css("display", "none");
	   $("#discount_2").css("display", "block");
	 }else{
	   $("#discount_1x").css("display", "none");
	   $("#discount_2").css("display", "none");	 
	 }
	if(divId==4){
		$("#text_arg1").text("单量");
		$("#text_arg2").text("单量");
	}else{
		$("#text_arg1").text("总运费");
		$("#text_arg2").text("总运费");	
	}
}


function show_divs(id) {
	var divObj = $("div[id^='discount']");
	if(id==1){
	   $("#discount_1x").css("display", "block");
	   $("#discount_2").css("display", "none");
	 }else if(id>1){
	   $("#discount_1x").css("display", "none");
	   $("#discount_2").css("display", "block");
	 }else{
	   $("#discount_1x").css("display", "none");
	   $("#discount_2").css("display", "none");	 
	 }
	if(id==4){
		$("#text_arg1").text("单量");
		$("#text_arg2").text("单量");
	}else{
		$("#text_arg1").text("总运费");
		$("#text_arg2").text("总运费");	
	}
}




function show_div2() {
	var divId = $("#disCont2").val();
	var divObj = $("div[id^='cont_dis']");
	if(divId==1){
		   $("#cont_dis_1").css("display", "block");
		   $("#cont_dis_2").css("display", "none");
		 }else if(divId>1){
		   $("#cont_dis_1").css("display", "none");
		   $("#cont_dis_2").css("display", "block");
		 }else{
		   $("#cont_dis_1").css("display", "none");
		   $("#cont_dis_2").css("display", "none");	 
		 }
	
}


	function changeCat() {
		var divId = $("#cat_type").val();
		var divObj = $("div[id^='cat']");
		for (i = 0; i < divObj.length; i++) {
			if (i == divId) {
				$("#cat" + i).css("display", "block");
			} else {
				$("#cat" + i).css("display", "none");
			}
		}
	}
	
	function changeCat_edit(id) {
		var divObj = $("div[id^='cat']");
		for (i = 0; i < divObj.length; i++) {
			if (i == id) {
				$("#cat" + i).css("display", "block");
			} else {
				$("#cat" + i).css("display", "none");
			}
		}
	}	
	
	

	function showDeatil() {
		var divId = $("#catDiv2").val();
		var divObj2 = $("div[id^='opDiv']");
		for (i = 1; i <= divObj2.length; i++) {
			if (i == divId) {
				$("#opDiv" + i).css("display", "block");
			} else {
				$("#opDiv" + i).css("display", "none");
			}
		}
	}

	function changeShow(div) {
		if($("#add_text_yf").text()=="取消编辑"){
			alert();
			document.getElementById("catDivForm").reset(); 
			$("#add_text_yf").text("新增");
			$("[id^=but_text]").text("保存");
		}
		if(div=="wl_ex_div"){
			if($("#add_text_wk").text()=="取消编辑"){
				$("#add_text_wk").text("新增");
			}
		}
		var ifshow = $("#" + div).css("display");
		if (ifshow == "none") {
			$("#" + div).css("display", "block");
		} else {
			$("#" + div).css("display", "none");
		}
		if(div=="add_customer"){
			$("#jz_type_div").css("display","none");
		}
	}
	
	
	
	function changeShow_yf(div) {
		if($("#add_text_yf").text()=="取消编辑"){
			document.getElementById("catDivForm").reset(); 
			$("#add_text_yf").text("新增");
			$("[id^=but_text]").text("保存");
		}
		if(div=="wl_ex_div"){
			if($("#add_text_wk").text()=="取消编辑"){
				$("#add_text_wk").text("新增");
			}
		}
		var ifshow = $("#" + div).css("display");
		if (ifshow == "none") {
			$("#" + div).css("display", "block");
		} else {
			$("#" + div).css("display", "none");
		}
		if(div=="add_customer"){
			$("#jz_type_div").css("display","none");
		}
	}
	
	
	
	function ediate(div) {
		$("#" + div).css("display", "block");
	}


	


/**
 * 删除表格
 * @param tabId
 */
function del_tab(tabId){
	if(!confirm("是否删除以下所选数据?")){
	  	return;
	  	
	}
	var url=root+"/control/transOrderController/del_formula.do?id="+tabId;
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				alert(jsonStr.result_reason);
				 if(jsonStr.result_flag==1){
						getFormula_tab($("#id").val());
				}
			}
		});
}

/**
 * 表格删除
 * @param tabId
 */
function del_tab_dic(tabId){
	if(!confirm("是否删除以下所选数据?")){
	  	return;
	  	
	}
	var url=root+"/control/transOrderController/del_discount.do?id="+tabId;
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				alert(jsonStr.result_reason);	
				if(jsonStr.result_flag==1){
					getDiscountTd($("#id").val(),'1','dt2');
	 				getDiscountTd_for_offer($("#id").val(),'2','td_offer');							
				}
			}
		});
}



/**
 * 表单验证
 * @param formId
 * @returns {Boolean}
 */
function checkInp(formId){
	   if(formId=="catDivForm"){
			 if(not_num_or_empty("standard_args","抛货计算标准格")){
					return false;
			 }
			
			 if(not_num_or_empty("low_weight","最低一票重量")){
					return false;
			 }
		}
	   
	   if(formId=="cat1Form"){
			if(not_num_or_empty("low_weight2","最低一票重量")){
					return false;
			 }
	   }
	   
	if(formId=="disContForm"){
			 if(not_num_or_empty("dicount_lv","折扣率")){
					return false;
			 }	
		 }
		  return true;
	}

function getGood_type(owerId){
	if(owerId==undefined){
		var type_id=$("#physicalDistribution").val();
		
	}else{
		var type_id=owerId;
	}
	var url=root+"/control/transOrderController/get_goodType.do?typeId="+type_id;
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
					var op_html="<select name='good_type' class='selectpicker' id='good_type' style='width: 30%'>";
//					var op_html=op_html+"<option value='0' >所有类型</option>";
					var op_html2="<select name='good_type' class='selectpicker' id='good_type_ofer' style='width: 30%'>";
//					var op_html2=op_html2+"<option value='0' >所有类型</option>";
					for(i=0;i<jsonStr.length;i++){
						op_html=op_html+"<option value='"+jsonStr[i].product_type_code+"'>"+jsonStr[i].product_type_name+"</option>"
						op_html2=op_html2+"<option value='"+jsonStr[i].product_type_code+"'>"+jsonStr[i].product_type_name+"</option>"
					}
					$("gt").html(op_html+"</select>");
					$("gt_ofer").html(op_html2+"</select>");
			}
		});
}

function getExpress(){
	var url=root+"/control/transOrderController/getExpress.do";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
					var op_html="<label class='control-label blue'>所用快递&nbsp;:</label><select name='wl_express' class='selectpicker' id='wl_express' style='width: 30%'>";
					for(i=0;i<jsonStr.length;i++){
						op_html=op_html+"<option value='"+jsonStr[i].transport_code+"'>"+jsonStr[i].transport_name+"</option>"
					}
					$("express").html(op_html+"</select>");
			}
		});
}

function getExpressTab(contract_id){
	var url=root+"/control/transOrderController/getExpressTab.do?contract_id="+contract_id;
	var htm_td="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				var text="<table class='with-border' border='1'>"
				       +"<tr class='title'>"
				       +"<td>序号</td><td>快递名称</td>"
				       +"<td>操作</td></tr>";
				for(i=0;i<jsonStr.length;i++){
					htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+jsonStr[i].transport_name+"</td><td><a href='javaScript:;' onclick='del_tabs(\""+jsonStr[i].id+"\")'>删除</a>|<a href='javaScript:;' onclick='edit_tabs(\""+jsonStr[i].id+"\",\""+jsonStr[i].transport_code+"\")'>编辑</a></td></tr>"
				}
				$("tab_express").html(text+htm_td+"</table>");
			}
		});
}


function edit_tabs(id,code){
	$("#add_customer").css("display","none");
	$("#jz_type_div").css("display","block");
	$("#wk_transport_code").val(code);
	init_wk();
}

function init_wk(){
	var url=root+"/control/transOrderController/getWkData.do?contract_id="+$("#id").val()+"&transport_code="+$("#wk_transport_code").val();
	var htm_td="";
	  $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				var text="<table class='with-border' border='1'>"
				       +"<tr class='title'>"
				       +"<td>序号</td><td>公式</td>"
				       +"<td>操作</td></tr>";
				for(i=0;i<jsonStr.length;i++){
					htm_td=htm_td+"<tr><td>"+(i+1)+"</td><td>"+jsonStr[i].cat_name+"</td><td><a href='javaScript:;' onclick='del_wk(\""+jsonStr[i].id+"\")'>删除</a>|<a href='javaScript:;' onclick='edit_wk(\""+jsonStr[i].id+"\",\""+jsonStr[i].cat_code+"\",\""+jsonStr[i].decimal_num+"\",\""+jsonStr[i].transport_code+"\")'>编辑</a></td></tr>"
				}
				$("wk_express").html(text+htm_td+"</table>");
			}
		});
	
}


function edit_wk(id,cat_code,decimal_num,transport_code){
	$("#wk_formula").find("option[value="+cat_code+"]").attr("selected",true);
	if(cat_code=="1"){
		$("#wk1").css("display","block");
		$("#wk2").css("display","none")
		$("#wk_decimal_1").val(decimal_num);
	}
	else if(cat_code=="2"){
		$("#wk2").css("display","block");
		$("#wk1").css("display","none");
		$("#wk_decimal_2").val(decimal_num);
	}
	else{
		$("#wk1").css("display","none");
		$("#wk2").css("display","none");
	}
	$("#wl_ex_div").css("display","block");
	
	
	$("#add_text_wk").text("取消编辑");
	$("#ch_id").val(id);
}

function del_wk(id){
	if(!confirm("是否删除以下所选数据?")){
	  	return;
	  	
	}
	var url=root+"/control/transOrderController/del_wk.do?id="+id;
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				alert(jsonStr.result_reason);
				if(jsonStr.result_flag==1){
					init_wk();
				}
			}
		});	
}

function del_tabs(id){
	if(!confirm("是否删除以下所选数据?")){
	  	return;
	  	
	}
	var url=root+"/control/transOrderController/delExpressTab.do?id="+id;
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				alert(jsonStr.result_reason);
				if(jsonStr.result_flag==1){
					getExpressTab($("#id").val());
				}
			}
		});
} 

function initData(contract_id){
	var url=root+"/control/transOrderController/init_bj_data.do?contract_id="+contract_id+"&belong_to="+$("#contractOwner").val()+"&type=1";
	var htm_td="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				if(jsonStr.if_baojia=='1'){
					$("#if_baojia").attr("checked","checked");
					ediate("disDiv2");
				}
			}
		});
}


function changeSwitch(){
	var ischeck;
	if(isChecked("if_baojia")){
		ischeck='1';
	}else{
		ischeck='0';
	}
	var url=root+"/control/transOrderController/init_bj_data.do?contract_id="+$("#id").val()+"&belong_to="+$("#contractOwner").val()+"&type=2"+"&if_baojia="+ischeck;
	var htm_td="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				alert(jsonStr.result_reason);
			}
		});
}


function initData_glf(contract_id){
	var url=root+"/control/transOrderController/initData_glf.do?contract_id="+contract_id+"&belong_to="+$("#contractOwner").val()+"&type=1";
	var htm_td="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				if(jsonStr.if_manager=='1'){
					$("#if_manager").attr("checked","checked");
					ediate("tab6Div");
					$("#manager_price").val(jsonStr.manager_price);
				}
			}
		});
}


function changeSwitch_glf(){
	var ischeck;
	if(isChecked("if_manager")){
		ischeck='1';
	}else{
		ischeck='0';
	}
	var url=root+"/control/transOrderController/initData_glf.do?contract_id="+$("#id").val()+"&belong_to="+$("#contractOwner").val()+"&type=2"+"&if_manager="+ischeck;
	var htm_td="";
	   $.ajax({
			type : "POST",
			url: url,  
			data:"",
			dataType: "json",  
			success : function(jsonStr) {
				alert(jsonStr.result_reason);
			}
		});
}

