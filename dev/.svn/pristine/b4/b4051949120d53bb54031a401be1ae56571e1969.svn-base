var ui1 =document.getElementById("div_carrier_sr_settlemented");  
var ui2 =document.getElementById("div_carrier_sr_unsettlement");  
var ui3 =document.getElementById("div_carrier_sr_error"); 
var zc1 =document.getElementById("div_carrier_zc_settlemented");  
var zc2 =document.getElementById("div_carrier_zc_unsettlement");  
var zc3 =document.getElementById("div_carrier_zc_error"); 

//$("#order_sku_tr").empty();
//$("input[type='text']").val("");
function query_sr_settlemented_tab(){
	ui1.style.display="";
	ui2.style.display="none";
	ui3.style.display="none";
}
function query_sr_settlemented(obj) {
	//alert("query_sr_settlemented");
	ui1.style.display="";
	ui2.style.display="none";
	ui3.style.display="none";
	var cost_center = $("#cost_center1").val(); 
	var store_name = $("#store_name1").val(); 
	var warehouse = $("#warehouse1").val(); 
	var transport_name = $("#transport_name1").val(); 
	var itemtype_name = $("#itemtype_name1").val(); 
	var express_number = $("#express_number1").val(); 
	var epistatic_order = $("#epistatic_order1").val(); 
	var delivery_address = $("#delivery_address1").val(); 
	var province = $("#province1").val(); 
	var city = $("#city1").val(); 
	var state = $("#state1").val(); 
	var start_time = encodeURI($("#s_time1").val());
	var end_time = encodeURI($("#e_time1").val());
//	var error_type = '4';
	if(obj==1){
		var param="&startRow="+$("#startRow").val()+"&endRow="+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val();
	}else{
		param="";
	}
	param="cost_center="+cost_center
	+"&store_name="+store_name
	+"&warehouse="+warehouse
	+"&transport_name="+transport_name
	+"&itemtype_name="+itemtype_name
	+"&express_number="+express_number
	+"&epistatic_order="+epistatic_order
	+"&delivery_address="+delivery_address
	+"&province="+province
	+"&city="+city
	+"&state="+state
//	+"&error_type="+error_type
	+"&endTime1="+end_time
	+"&startTime1="+start_time
	+param;
	var url = "/BT-LMIS/control/estimateController/querySRSettle.do?"+param;
	//alert(url);
	openIdDiv("carrier_sr_settlemented",url);
}
function query_sr_unsettlement_tab() {
	ui1.style.display="none";
	ui2.style.display="";
	ui3.style.display="none";
}
function query_sr_unsettlement(obj) {
	//alert("query_sr_unsettlement");
	ui1.style.display="none";
	ui2.style.display="";
	ui3.style.display="none";
	var cost_center = $("#cost_center1").val(); 
	var store_name = $("#store_name1").val(); 
	var warehouse = $("#warehouse1").val(); 
	var transport_name = $("#transport_name1").val(); 
	var itemtype_name = $("#itemtype_name1").val(); 
	var express_number = $("#express_number1").val(); 
	var epistatic_order = $("#epistatic_order1").val(); 
	var delivery_address = $("#delivery_address1").val(); 
	var province = $("#province1").val(); 
	var city = $("#city1").val(); 
	var state = $("#state1").val(); 
	var start_time = encodeURI($("#s_time1").val());
	var end_time = encodeURI($("#e_time1").val());
	var error_type = '4';
	if(obj==1){
		var param="&startRow="+$("#startRow").val()+"&endRow="+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val();
	}else{
		param="";
	}
	param="costCenter="+cost_center
	+"&storeName="+store_name
	+"&warehouse="+warehouse
	+"&transportName="+transport_name
	+"&itemtypeName="+itemtype_name
	+"&expressNumber="+express_number
	+"&epistaticOrder="+epistatic_order
	+"&deliveryAddress="+delivery_address
	+"&province="+province
	+"&city="+city
	+"&state="+state
	+"&errorType="+error_type
	+"&startTime="+start_time
	+"&endTime="+end_time
	+param;
	//alert(param);
	openIdDiv("carrier_sr_unsettlement",
			"/BT-LMIS/control/estimateController/queryUnsett.do?"+param
	);
}
function query_sr_error_tab() {
	ui1.style.display="none";
	ui2.style.display="none";
    ui3.style.display="";
}
function query_sr_error(obj) {
	//alert("query_sr_error");
	ui1.style.display="none";
	ui2.style.display="none";
    ui3.style.display="";
    var cost_center = $("#cost_center1").val(); 
	var store_name = $("#store_name1").val(); 
	var warehouse = $("#warehouse1").val(); 
	var transport_name = $("#transport_name1").val(); 
	var itemtype_name = $("#itemtype_name1").val(); 
	var express_number = $("#express_number1").val(); 
	var epistatic_order = $("#epistatic_order1").val(); 
	var delivery_address = $("#delivery_address1").val(); 
	var province = $("#province1").val(); 
	var city = $("#city1").val(); 
	var state = $("#state1").val(); 
	var start_time = encodeURI($("#s_time1").val());
	var end_time = encodeURI($("#e_time1").val());
	var error_type = '4';
	if(obj==1){
		var param="&startRow="+$("#startRow").val()+"&endRow="+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val();
	}else{
		param="";
	}
	param="cost_center="+cost_center
	+"&store_name="+store_name
	+"&warehouse="+warehouse
	+"&transport_name="+transport_name
	+"&itemtype_name="+itemtype_name
	+"&express_number="+express_number
	+"&epistatic_order="+epistatic_order
	+"&delivery_address="+delivery_address
	+"&province="+province
	+"&city="+city
	+"&state="+state
	+"&error_type="+error_type
	+"&startTime="+start_time
	+"&endTime="+end_time
	+param;
	//alert(param);
	openIdDiv("carrier_sr_error",
			"/BT-LMIS/control/estimateController/queryError.do?" +param
	);
}

function query_zc_settlemented_tab() {
	zc1.style.display="";
	zc2.style.display="none";
	zc3.style.display="none";
}
function query_zc_settlemented(obj) {
	//alert("query_zc_settlemented");
	zc1.style.display="";
	zc2.style.display="none";
	zc3.style.display="none";
	var cost_center = $("#cost_center").val(); 
	var store_name = $("#store_name").val(); 
	var warehouse = $("#warehouse").val(); 
	var transport_name = $("#transport_name").val(); 
	var itemtype_name = $("#itemtype_name").val(); 
	var express_number = $("#express_number").val(); 
	var epistatic_order = $("#epistatic_order").val(); 
	var delivery_address = $("#delivery_address").val(); 
	var province = $("#province").val(); 
	var city = $("#city").val(); 
	var state = $("#state").val(); 
	var start_time = encodeURI($("#s_time").val());
	var end_time = encodeURI($("#e_time").val());
//	var error_type = '1';
	if(obj==1){
		var param="&startRow="+$("#startRow").val()+"&endRow="+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val();
	}else{
		param="";
	}
	param="costCenter="+cost_center
	+"&storeName="+store_name
	+"&warehouse="+warehouse
	+"&transportName="+transport_name
	+"&itemtypeName="+itemtype_name
	+"&expressNumber="+express_number
	+"&epistaticOrder="+epistatic_order
	+"&deliveryAddress="+delivery_address
	+"&province="+province
	+"&city="+city
	+"&state="+state
//	+"&errorType="+error_type
	+"&startTime="+start_time
	+"&endTime="+end_time
	+param;
	//alert(param);
	openIdDiv("carrier_zc_settlemented",
			"/BT-LMIS/control/estimateController/queryZCSettle.do?"+param
	);
}
function query_zc_unsettlement_tab() {
	zc1.style.display="none";
	zc2.style.display="";
	zc3.style.display="none";
}
function query_zc_unsettlement(obj) {
	//alert("query_zc_unsettlement");
	zc1.style.display="none";
	zc2.style.display="";
	zc3.style.display="none";
	var cost_center = $("#cost_center").val(); 
	var store_name = $("#store_name").val(); 
	var warehouse = $("#warehouse").val(); 
	var transport_name = $("#transport_name").val(); 
	var itemtype_name = $("#itemtype_name").val(); 
	var express_number = $("#express_number").val(); 
	var epistatic_order = $("#epistatic_order").val(); 
	var delivery_address = $("#delivery_address").val(); 
	var province = $("#province").val(); 
	var city = $("#city").val(); 
	var state = $("#state").val(); 
	var start_time = encodeURI($("#s_time").val());
	var end_time = encodeURI($("#e_time").val());
	var error_type = '1';
	
	if(obj==1){
		var param="&startRow="+$("#startRow").val()+"&endRow="+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val();
	}else{
		param="";
	}
	param="costCenter="+cost_center
	+"&storeName="+store_name
	+"&warehouse="+warehouse
	+"&transportName="+transport_name
	+"&itemtypeName="+itemtype_name
	+"&expressNumber="+express_number
	+"&epistaticOrder="+epistatic_order
	+"&deliveryAddress="+delivery_address
	+"&province="+province
	+"&city="+city
	+"&state="+state
	+"&errorType="+error_type
	+"&startTime="+start_time
	+"&endTime="+end_time
	+param;
	//alert(param);
	openIdDiv("carrier_zc_unsettlement",
			"/BT-LMIS/control/estimateController/queryUnsett.do?"+param
	);
	//alert("query_zc_unsettlement");
}
function query_zc_error_tab() {
	zc1.style.display="none";
	zc2.style.display="none";
	zc3.style.display="";
}
function query_zc_error(obj) {
	//alert("query_zc_error");
	zc1.style.display="none";
	zc2.style.display="none";
	zc3.style.display="";
	var cost_center = $("#cost_center").val(); 
	var store_name = $("#store_name").val(); 
	var warehouse = $("#warehouse").val(); 
	var transport_name = $("#transport_name").val(); 
	var itemtype_name = $("#itemtype_name").val(); 
	var express_number = $("#express_number").val(); 
	var epistatic_order = $("#epistatic_order").val(); 
	var delivery_address = $("#delivery_address").val(); 
	var province = $("#province").val(); 
	var city = $("#city").val(); 
	var state = $("#state").val(); 
	var start_time = encodeURI($("#s_time").val());
	var end_time = encodeURI($("#e_time").val());
	var error_type = '1';
	if(obj==1){
		var param="&startRow="+$("#startRow").val()+"&endRow="+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val();
	}else{
		param="";
	}
	param="cost_center="+cost_center
	+"&store_name="+store_name
	+"&warehouse="+warehouse
	+"&transport_name="+transport_name
	+"&itemtype_name="+itemtype_name
	+"&express_number="+express_number
	+"&epistatic_order="+epistatic_order
	+"&delivery_address="+delivery_address
	+"&province="+province
	+"&city="+city
	+"&state="+state
	+"&error_type="+error_type
	+"&startTime="+start_time
	+"&endTime="+end_time
	+param;
	//alert(param);
	openIdDiv("carrier_zc_error",
			"/BT-LMIS/control/estimateController/queryError.do?"+param
	);
}

function changeColor(obj){
	if(obj==1){
		document.getElementById("a1").style.color="black";
		document.getElementById("a2").style.color="red";
		document.getElementById("a3").style.color="red";
		document.getElementById("a4").style.color="red";
		document.getElementById("a5").style.color="red";
		document.getElementById("a6").style.color="red";
		document.getElementById("a7").style.color="red";
	}
	if(obj==2){
		document.getElementById("a1").style.color="red";
		document.getElementById("a2").style.color="black";
		document.getElementById("a3").style.color="red";
		document.getElementById("a4").style.color="red";
		document.getElementById("a5").style.color="red";
		document.getElementById("a6").style.color="red";
		document.getElementById("a7").style.color="red";
	}
	if(obj==3){
		document.getElementById("a1").style.color="red";
		document.getElementById("a2").style.color="red";
		document.getElementById("a3").style.color="black";
		document.getElementById("a4").style.color="red";
		document.getElementById("a5").style.color="red";
		document.getElementById("a6").style.color="red";
		document.getElementById("a7").style.color="red";
	}
	if(obj==4){
		document.getElementById("a1").style.color="red";
		document.getElementById("a2").style.color="red";
		document.getElementById("a3").style.color="red";
		document.getElementById("a4").style.color="black";
		document.getElementById("a5").style.color="red";
		document.getElementById("a6").style.color="red";
		document.getElementById("a7").style.color="red";
	}
	if(obj==5){
		document.getElementById("a1").style.color="red";
		document.getElementById("a2").style.color="red";
		document.getElementById("a3").style.color="red";
		document.getElementById("a4").style.color="red";
		document.getElementById("a5").style.color="black";
		document.getElementById("a6").style.color="red";
		document.getElementById("a7").style.color="red";
	}
	if(obj==6){
		document.getElementById("a1").style.color="red";
		document.getElementById("a2").style.color="red";
		document.getElementById("a3").style.color="red";
		document.getElementById("a4").style.color="red";
		document.getElementById("a5").style.color="red";
		document.getElementById("a6").style.color="black";
		document.getElementById("a7").style.color="red";
	}
	if(obj==7){
		document.getElementById("a1").style.color="red";
		document.getElementById("a2").style.color="red";
		document.getElementById("a3").style.color="red";
		document.getElementById("a4").style.color="red";
		document.getElementById("a5").style.color="red";
		document.getElementById("a6").style.color="red";
		document.getElementById("a7").style.color="black";
	}
}

//导出
function exportZCYFExcel(tabMark){
	//alert("exportZCYFExcel");
	var cost_center = $("#cost_center").val(); 
	var store_name = $("#store_name").val(); 
	var warehouse = $("#warehouse").val(); 
	var transport_name = $("#transport_name").val(); 
	var itemtype_name = $("#itemtype_name").val(); 
	var express_number = $("#express_number").val(); 
	var epistatic_order = $("#epistatic_order").val(); 
	var delivery_address = $("#delivery_address").val(); 
	var province = $("#province").val(); 
	var city = $("#city").val(); 
	var state = $("#state").val(); 
	var start_time = encodeURI($("#s_time").val());
	var end_time = encodeURI($("#e_time").val());
	var error_type = '1';
	
	param="costCenter="+cost_center
	+"&storeName="+store_name
	+"&warehouse="+warehouse
	+"&transportName="+transport_name
	+"&itemtypeName="+itemtype_name
	+"&expressNumber="+express_number
	+"&epistaticOrder="+epistatic_order
	+"&deliveryAddress="+delivery_address
	+"&province="+province
	+"&city="+city
	+"&state="+state
	+"&errorType="+error_type
	+"&startTime="+start_time
	+"&endTime="+end_time
	+"&tabMark="+tabMark;
	//alert(param);
	
	var url="/BT-LMIS/control/estimateController/exportZCYFExcel.do?";
	window.location.href=url+param;
}
//导出
function exportSRYFExcel(tabMark){
	//alert("exportSRYFExcel");
    var cost_center = $("#cost_center1").val(); 
	var store_name = $("#store_name1").val(); 
	var warehouse = $("#warehouse1").val(); 
	var transport_name = $("#transport_name1").val(); 
	var itemtype_name = $("#itemtype_name1").val(); 
	var express_number = $("#express_number1").val(); 
	var epistatic_order = $("#epistatic_order1").val(); 
	var delivery_address = $("#delivery_address1").val(); 
	var province = $("#province1").val(); 
	var city = $("#city1").val(); 
	var state = $("#state1").val(); 
	var start_time = encodeURI($("#s_time1").val());
	var end_time = encodeURI($("#e_time1").val());
	var error_type = '4';
	
	param="costCenter="+cost_center
	+"&storeName="+store_name
	+"&warehouse="+warehouse
	+"&transportName="+transport_name
	+"&itemtypeName="+itemtype_name
	+"&expressNumber="+express_number
	+"&epistaticOrder="+epistatic_order
	+"&deliveryAddress="+delivery_address
	+"&province="+province
	+"&city="+city
	+"&state="+state
	+"&errorType="+error_type
	+"&startTime="+start_time
	+"&endTime="+end_time
	+"&tabMark="+tabMark;
	//alert(param);
	
	var url="/BT-LMIS/control/estimateController/exportSRYFExcel.do?";
	window.location.href=url+param;
}