function save_xz_main(id, type) {
	var data;
	if(type == "xz") {
	    if(not_num_or_empty("xz_discount", "首重续重折扣")) {
	    	return;
	    	
	    }
	    if(not_num_or_empty("xz_sh_weight", "首重")) {
	    	return;
	    	
	    }   
	    if(not_num_or_empty("sz_price", "首重价格")) {
	    	return;
	    	
	    } 
	    if(not_num_or_empty("jp_js", "计抛基数")) {
	    	return;
	    	
	    }
	    if(not_num_or_empty("jf_wight", "计费重量")) {
	    	return;
	    	
	    }
	    data= "szxz_discount="+ $("#xz_discount").val()+ "&szxz_sz="+ $("#xz_sh_weight").val()+ "&szxz_price="+ $("#sz_price").val()+ "&szxz_jpnum="+ $("#jp_js").val()+ "&jf_weight="+ $("#jf_wight").val()+ "&id="+ id;
    
	}
	if(type == "ld"){
		if(not_num_or_empty("ld_discount_price", "零担折扣")) {
			return;
			
		}
		data= "ld_discount="+ $("#ld_discount_price").val()+ "&id="+ id;
		
	}
	if(type == "gt") {
		if(not_num_or_empty("gt_discount_price", "商品类型折扣")){
			return;
			
		}
		if(not_num_or_empty("gt_ps_peice", "派送费")) {
			return;
			
		}
		data="good_type_discount="+ $("#gt_discount_price").val()+ "&ps_price="+ $("#gt_ps_peice").val()+ "&id="+ id+"&ps_unit="+ $("#ps_unit").val();
	
	}
	var url= root+ "/control/addressController/saveXzMain.do";
	$.ajax({
		type: "POST",
		url: url,  
		data: data,
		dataType: "json",  
		success: function(jsonStr) {
			alert(jsonStr.result_reason);
			
		}
	
	});
	
}

function save_xz(){
	var sx_id=$("#sx_tab_id").val();
	var result=getSection("xz_mark1","xz_mark2","xz_mark3","xz_agrs1","xz_agrs2");
	if(!result){
		if(not_num_or_empty("xz_price", "区间收费")){
    	   return;
    	   
       }
		
	}
	var data= "internal="+ result+ "&internal_unit=KG"+ "&price="+ $("#xz_price").val()+ "&price_unit=元"+ "&id="+ $("#xz_id_tab").val()+ "&tabType=1";
	var url= root+ "/control/addressController/updateTable.do";
	$.ajax({
		type: "POST",
		url: url,  
		data: data,
		dataType: "json",  
		success: function(jsonStr) {
			alert(jsonStr.result_reason);
			 get_tab(sx_id,'sx_tab',1);
			 
		}
	
	});
	
}

function save_gj() {
	var sx_id= $("#gj_tab_id").val();
	var result= getSection("gj_mark1", "gj_mark2", "gj_mark3", "gj_agrs1", "gj_agrs2");
	if(!result) {
		if(not_num_or_empty("gj_price","区间收费")) {
			return;
			
		}
		
	}
	var data= "internal="+ result+ "&internal_unit=KG"+ "&price="+ $("#gj_price").val()+ "&price_unit=元"+ "&id="+ $("#gj_id_tab").val()+ "&tabType=1";
	var url= root+ "/control/addressController/updateTable.do";
	$.ajax({
		type: "POST",
		url: url,  
		data: data,
		dataType: "json",  
		success: function(jsonStr) {
			alert(jsonStr.result_reason);
			 get_tab(sx_id,'gj_tab',1);
		}
	
	});
	
}
  
function save_lf(){
	var sx_id=$("#lf_tab_id").val();
	var result=getSection("lf_mark1","lf_mark2","lf_mark3","lf_agrs1","lf_agrs2");
	if(!result){
		if(not_num_or_empty("lf_price","区间收费")){
			return;
			
		}
		
	}
	var data= "internal="+ result+ "&internal_unit=KG"+ "&price="+ $("#lf_price").val()+ "&price_unit=元"+ "&id="+ $("#lf_id_tab").val()+ "&tabType=1";
	var url= root+ "/control/addressController/updateTable.do";
	$.ajax({
		type: "POST",
		url: url,  
		data: data,
		dataType: "json",  
		success: function(jsonStr) {
			alert(jsonStr.result_reason);
			 get_tab(sx_id,'lf_tab',1);
			 
		}
	
	});
	
}

function save_gt(){
	var sx_id=$("#gt_tab_id").val();
	if(not_num_or_empty("yf_gt_price", "价格")) {
		return;
		
	}
	if(isEmptyInfo("yf_gt_type","商品类型")){
		return;
		
	}
	var data="item_type="+$("#yf_gt_type").val()+"&price="+$("#yf_gt_price").val()+"&unit=元"+"&id="+$("#gt_id_tab").val()+"&tabType=2";
	var url=root+"/control/addressController/updateTable.do";
	$.ajax({
		type: "POST",
		url: url,  
		data: data,
		dataType: "json",  
		success: function(jsonStr) {
			alert(jsonStr.result_reason);
			get_tab(sx_id,'gt_tab',2);
			 
		}
	
	});
	
}
 
function save_zf(){
	var sx_id=$("#zf_tab_id").val();
	if(not_num_or_empty("zf_gt_price","价格")) {
		return;
	}
	if(isEmptyInfo("zf_gt_type","商品类型")) {
		return;
	}	  
	var data= "item_type="+ $("#zf_gt_type").val()+ "&price="+ $("#zf_gt_price").val()+ "&unit=元"+ "&id="+ $("#zf_id_tab").val()+ "&tabType=2";
	var url= root+ "/control/addressController/updateTable.do";
	$.ajax({
		type: "POST",
		url: url,  
		data: data,
		dataType: "json",  
		success: function(jsonStr) {
			alert(jsonStr.result_reason);
			get_tab(sx_id,'zf_tab',2);
			
		}
	
	});
	
}

function save_zc() {
	var sx_id= $("#zc_tab_id").val();
	if(not_num_or_empty("car_price","价格")){
		return;
		
	}
	if(not_num_or_empty("car_volum","容量")){
		return;
		
	}	  
	if(isEmptyInfo("car_code","车型代码")){
		return;
		
	}
	if(isEmptyInfo("car_name","车型名称")){
		return;
		
	}	 	  
	var data= "bus_code="+ $("#car_code").val()+ "&bus_name="+ $("#car_name").val()+ "&price="+ $("#car_price").val()+ "&bus_volumn="+ $("#car_volum").val()+ "&unit=元&busvolumn_unit=立方米"+ "&id="+ $("#zc_id_tab").val()+ "&tabType=3";
	var url= root+ "/control/addressController/updateTable.do";
	$.ajax({
		type: "POST",
		url: url,
		data: data,
		dataType: "json",
		success: function(jsonStr) {
			alert(jsonStr.result_reason);
			get_tab(sx_id,'zc_tab',3);
			
		}
	
	});
	
}

function update_td(id, mark1, args1, mark2, args2, dtx, price) {
	if(dtx == "sx_tab"){
		ediate('xz_ad_td');
		var select= document.getElementById("xz_mark1");  
		var select2= document.getElementById("xz_mark3");  
		for(var i= 0; i< select.options.length; i++) {  
			if(select.options[i].innerHTML == chSection(mark1)){
				select.options[i].selected= true;
				break;
				
			}
			
		} 
		for(var k= 0; k< select2.options.length; k++) {  
			if(select2.options[k].innerHTML == chSection(mark2)) {
				select2.options[k].selected = true;
				break;
				
			}
			
		}  
		$("#xz_agrs1").val(args1);  
		$("#xz_agrs2").val(args2);    	
		$("#xz_price").val(price); 
		$("#xz_id_tab").val(id);
		
	}
	if(dtx == "gj_tab") {
		ediate('ld_ad_td');
		var select= document.getElementById("gj_mark1");
		var select2= document.getElementById("gj_mark3");
		for(var i= 0; i< select.options.length; i++){
			if(select.options[i].innerHTML == chSection(mark1)){
				select.options[i].selected= true;
				break;
				
			}
			
		}
		for(var k= 0; k< select2.options.length; k++) {
			if(select2.options[k].innerHTML == chSection(mark2)) {
				select2.options[k].selected= true;
				break;
				
			}
		} 
		$("#gj_agrs1").val(args1);
		$("#gj_agrs2").val(args2);
		$("#gj_price").val(price);
		$("#gj_id_tab").val(id);
		
	}
	if(dtx == "lf_tab"){
		ediate('lf_ad_td');
		var select= document.getElementById("lf_mark1");
		var select2 = document.getElementById("lf_mark3");
		for(var i= 0; i< select.options.length; i++) {
			if(select.options[i].innerHTML == chSection(mark1)){
				select.options[i].selected= true;
				break;
				
			}
			
		}
		for(var k= 0; k< select2.options.length; k++) {
			if(select2.options[k].innerHTML == chSection(mark2)){
				select2.options[k].selected= true;
				break;
				
			}
			
		}
		$("#lf_agrs1").val(args1);
		$("#lf_agrs2").val(args2);
		$("#lf_price").val(price);
		$("#lf_id_tab").val(id);
		
	}
	
}

function update_gtTab(id, type, price, dtx) {
	if(dtx == "gt_tab"){
		ediate('tp_ad_td');
		$("#yf_gt_type").val(type);
		$("#yf_gt_price").val(price);
		$("#gt_id_tab").val(id);
		
	}
	if(dtx == "zf_tab") {
		ediate('zf_ad_td');
		$("#zf_gt_type").val(type);
		$("#zf_gt_price").val(price);
		$("#zf_id_tab").val(id);
		
	}
	
}	

function update_BusTab(id, code, name, vlum, price){
	ediate('zc_ad_td');
	$("#car_code").val(code);
	$("#car_name").val(name);
	$("#car_volum").val(vlum);
	$("#car_price").val(price);
	$("#zc_id_tab").val(id);
	
}

function changeShow_add(div) {
	if(!changeStatus(div)) {
		return;
		
	}
	var ifshow= $("#"+ div).css("display");
	if (ifshow == "none") {
		$("#"+ div).css("display", "block");
		
	} else {
		$("#" + div).css("display", "none");
		
	}
	
}

function changeStatus(div){
	if(div == "sx_div"){
		if(confirm("确定改变首重续重的启用或停用状态吗？")){
			if(isChecked("if_szxz")){
				data="szxz_switch=1";
				
			}else{
				data="szxz_switch=0";
				
			}
			
		} else {
			if(isChecked("if_szxz")){
				$("#if_szxz").attr("checked", false);
				
			}else{
				$("#if_szxz").attr("checked", true);
				
			}
			return false;
			
		}
		
	}
	if(div == "ld_div"){
		if(confirm("确定改变零担的启用或停用状态吗？")){
			if(isChecked("if_ld")) {
				data= "ld_switch=1";
				
			} else {
				data= "ld_switch=0";
				
			}
			
		} else {
			if(isChecked("if_ld")) {
				$("#if_ld").attr("checked", false);
				
			} else {
				$("#if_ld").attr("checked", true);
				
			}
			return false;
			
		}
		
	}
	if(div == "gt_div"){
		if(confirm("确定改变商品类型的启用或停用状态吗？")) {
			if(isChecked("if_gt")){
				data="it_switch=1";
				
			} else {
				data= "it_switch=0";
				
			}
			
		} else {
			if(isChecked("if_gt")) {
				$("#if_gt").attr("checked", false);
				
			} else {
				$("#if_gt").attr("checked", true);
				
			}
			return false;
			
		}
		
	}
	if(div == "zc_div"){
		if(confirm("确定改变整车的启用或停用状态吗？")) {
			if(isChecked("if_zc")) {
				data= "zc_switch=1";
				
			} else {
				data= "zc_switch=0";
				
			}
			
		} else {
			if(isChecked("if_zc")){
				$("#if_zc").attr("checked", false);
				
			} else {
				$("#if_zc").attr("checked", true);
				
			}
			return false;
			
		}
		
	}
	changeStatusLink(data);
	return true;
	
}

function changeStatusLink(data) {
	data= data+ "&id="+ $("#mainId").val();
	var url= root+ "/control/addressController/saveXzMain.do";
	$.ajax({
		type: "POST",
		url: url,
		data: data,
		dataType: "json",
		success: function(jsonStr) {
		
		}
	
	});
	
}

/**
 * 删除
 * @param mak
 * @returns {String}
 */	
function del_tab_add(id, type, dtx, tabId){
    var url= root+ "/control/addressController/delTabData.do?type="+ type+ "&id="+ id;
    $.ajax({
    	type: "POST",
    	url: url,
    	data: "",
    	dataType: "json",
    	success: function(jsonStr) {
    		alert(jsonStr.result_reason);
    		get_tab(tabId,dtx,type);
    		
    	}
    
    });
    
}
	
function chSection(mak){
	if(mak == ">=") {
		return "&gt;=";
		
	}
	if(mak == ">") {
		return "&gt;";
		
	}
	if(mak == "<=") {
		return "&lt;=";
		
	}
	if(mak == "<"){
		return "&lt;";
		
	}
	
}
