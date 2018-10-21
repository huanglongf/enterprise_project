<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/base/yuriy.jsp" %>
		<title>OP</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		
		
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type='text/javascript'>
		
		
			$(document).ready(function() {
			
				$('#provice_code').bind('change',function(){
					if(this.value==''){
						$('#city_code').html('<option value="">---请选择---</option>');
						$('#state_code').html('<option value="">---请选择---</option>');	
					}
					$.post('${root}control/orderPlatform/waybillMasterController/getArea.do?area_code='+this.value,function(data){	
						if(data.toString()=='[object XMLDocument]'){
							  window.location='/BT-OP';
								return;
						  };
				    if(data.nodeName=='#document')toMain();		
					var  htmlStr='<option value="">---请选择---</option>';
					$.each(data.area,function(index,item){
						htmlStr=htmlStr+'<option value='+this.area_code+'>'+this.area_name+'</option>';		
					});
					$('#city_code').html(htmlStr);
					$('#state_code').html('<option value="">---请选择---</option>');
					});
					
					var city_code=document.getElementById("city_code").value;
					if(city_code=="" ||city_code==null ||city_code==undefined){
						$("#span_city_code").show();
					}else{
						$("#span_city_code").hide();
					}
					var state_code=document.getElementById("state_code").value;
					if(state_code=="" ||state_code==null ||state_code==undefined){
						$("#span_state_code").show();
					}else{
						$("#span_state_code").hide();
					}
					
					var provice_code=document.getElementById("provice_code").value;
					if(provice_code=="" ||provice_code==null ||provice_code==undefined){
						$("#span_provice_code").show();
					}else{
						$("#span_provice_code").hide();
					}
				});
				$('#city_code').bind('change',function(){
					if(this.value==''){
						$('#state_code').html('<option value="">---请选择---</option>');	
					}
					$.post('${root}control/orderPlatform/waybillMasterController/getArea.do?area_code='+this.value,function(data){
						if(data.toString()=='[object XMLDocument]'){
							  window.location='/BT-OP';
								return;
						  };
					if(data.nodeName=='#document')toMain();
					var  htmlStr='<option value="">---请选择---</option>';
					$.each(data.area,function(index,item){
						htmlStr=htmlStr+'<option value='+this.area_code+'>'+this.area_name+'</option>';		
					});
					$('#state_code').html(htmlStr);
					});
					
					
					var city_code=document.getElementById("city_code").value;
					if(city_code=="" ||city_code==null ||city_code==undefined){
						$("#span_city_code").show();
					}else{
						$("#span_city_code").hide();
					}
					var state_code=document.getElementById("state_code").value;
					if(state_code=="" ||state_code==null ||state_code==undefined){
						$("#span_state_code").show();
					}else{
						$("#span_state_code").hide();
					}
				});
				
				
				$('#state_code').bind('change',function(){
					var state_code=document.getElementById("state_code").value;
					if(state_code=="" ||state_code==null ||state_code==undefined){
						$("#span_state_code").show();
					}else{
						$("#span_state_code").hide();
					}
				
				});
				
				$('#org_provice_code').bind('change',function(){
					if(this.value==''){
						$('#org_city_code').html('<option value="">---请选择---</option>');
						$('#org_state_code').html('<option value="">---请选择---</option>');	
					}
					$.post('${root}control/orderPlatform/waybillMasterController/getArea.do?area_code='+this.value,function(data){	
						if(data.toString()=='[object XMLDocument]'){
							  window.location='/BT-OP';
								return;
						  };
				    if(data.nodeName=='#document')toMain();		
					var  htmlStr='<option value="">---请选择---</option>';
					$.each(data.area,function(index,item){
						htmlStr=htmlStr+'<option value='+this.area_code+'>'+this.area_name+'</option>';		
					});
					$('#org_city_code').html(htmlStr);
					$('#org_state_code').html('<option value="">---请选择---</option>');
					});
					
					
					
					var org_state_code=document.getElementById("org_state_code").value;
					if(org_state_code=="" ||org_state_code==null ||org_state_code==undefined){
						$("#span_org_state_code").show();
					}else{
						$("#span_org_state_code").hide();
					}
					var org_city_code=document.getElementById("org_city_code").value;
					if(org_city_code=="" ||org_city_code==null ||org_city_code==undefined){
						$("#span_org_city_code").show();
					}else{
						$("#span_org_city_code").hide();
					}
					var org_provice_code=document.getElementById("org_provice_code").value;
					if(org_provice_code=="" ||org_provice_code==null ||org_provice_code==undefined){
						$("#span_org_provice_code").show();
					}else{
						$("#span_org_provice_code").hide();
					}
				});
				$('#org_city_code').bind('change',function(){
					if(this.value==''){
						$('#org_state_code').html('<option value="">---请选择---</option>');	
					}
					$.post('${root}control/orderPlatform/waybillMasterController/getArea.do?area_code='+this.value,function(data){
						if(data.toString()=='[object XMLDocument]'){
							  window.location='/BT-OP';
								return;
						  };
					if(data.nodeName=='#document')toMain();
					var  htmlStr='<option value="">---请选择---</option>';
					$.each(data.area,function(index,item){
						htmlStr=htmlStr+'<option value='+this.area_code+'>'+this.area_name+'</option>';		
					});
					$('#org_state_code').html(htmlStr);
					});
					
					var org_state_code=document.getElementById("org_state_code").value;
					if(org_state_code=="" ||org_state_code==null ||org_state_code==undefined){
						$("#span_org_state_code").show();
					}else{
						$("#span_org_state_code").hide();
					}
					var org_city_code=document.getElementById("org_city_code").value;
					if(org_city_code=="" ||org_city_code==null ||org_city_code==undefined){
						$("#span_org_city_code").show();
					}else{
						$("#span_org_city_code").hide();
					}
				});
				$('#org_state_code').bind('change',function(){
					var org_state_code=document.getElementById("org_state_code").value;
					if(org_state_code=="" ||org_state_code==null ||org_state_code==undefined){
						$("#span_org_state_code").show();
					}else{
						$("#span_org_state_code").hide();
					}
				});
				$('#expressCode').bind('change',function(){
					ExpressCodeChange("expressCode","producttype_name");
					var producttype_name=document.getElementById("producttype_name").value;
					if(producttype_name=="" ||producttype_name==null ||producttype_name==undefined){
						$("#span_producttype_name").show();
					}else{
						$("#span_producttype_name").hide();
					}
					var expressCode=document.getElementById("expressCode").value;
					if(expressCode=="" ||expressCode==null ||expressCode==undefined){
						$("#span_expressCode").show();
					}else{
						$("#span_expressCode").hide();
					}
					if(expressCode=="BSC"){
						var sendcode=document.getElementById("sendcode").value;
						if(sendcode=="" ||sendcode==null ||sendcode==undefined){
							$("#span_sendcode").show();
						}else{
							$("#span_sendcode").hide();
						}
					}else{
						$("#span_sendcode").hide();
					}
				});
				$('#producttype_name').bind('change',function(){
					var producttype_name=document.getElementById("producttype_name").value;
					if(producttype_name=="" ||producttype_name==null ||producttype_name==undefined){
						$("#span_producttype_name").show();
					}else{
						$("#span_producttype_name").hide();
					}
				});
				
				
				$('#to_orgnization').bind('change',function(){
					ToOrgnizationChange("to_orgnization","org_provice_code","org_city_code","org_state_code","to_street","to_contacts","to_phone","to_address");
					var org_state_code=document.getElementById("org_state_code").value;
					if(org_state_code=="" ||org_state_code==null ||org_state_code==undefined){
						$("#span_org_state_code").show();
					}else{
						$("#span_org_state_code").hide();
					}
					var org_city_code=document.getElementById("org_city_code").value;
					if(org_city_code=="" ||org_city_code==null ||org_city_code==undefined){
						$("#span_org_city_code").show();
					}else{
						$("#span_org_city_code").hide();
					}
					var org_provice_code=document.getElementById("org_provice_code").value;
					if(org_provice_code=="" ||org_provice_code==null ||org_provice_code==undefined){
						$("#span_org_provice_code").show();
					}else{
						$("#span_org_provice_code").hide();
					}
					
					var to_address=document.getElementById("to_address").value;
					if(to_address=="" ||to_address==null ||to_address==undefined){
						$("#to_address").next().show();
					}else{
						$("#to_address").next().hide();
					}
					var to_phone=document.getElementById("to_phone").value;
					if(to_phone=="" ||to_phone==null ||to_phone==undefined){
						$("#span_to_phone").show();
					}else{
						$("#span_to_phone").hide();
					}
					var to_contacts=document.getElementById("to_contacts").value;
					if(to_contacts=="" ||to_contacts==null ||to_contacts==undefined){
						$("#to_contacts").next().show();
					}else{
						$("#to_contacts").next().hide();
					}
				});
				
				
			});
			
			function ToOrgnizationChange(to_orgnization, org_provice_code, org_city_code, org_state_code,to_street ,to_contacts ,to_phone, to_address){
				to_orgnization= "#" +to_orgnization;
				org_provice_code= "#" +org_provice_code;
				org_city_code= "#" +org_city_code;
				org_state_code= "#" +org_state_code;
				to_street= "#" +to_street;
				to_contacts= "#" +to_contacts;
				to_phone= "#" +to_phone;
				to_address= "#" +to_address;
				var to_orgnization = document.getElementById('to_orgnization').value;
				if(to_orgnization==""){
					$(org_provice_code).val("");
					$(org_city_code).val("");
					$(org_state_code).val("");
					$(to_street).val("");
					$(to_contacts).val("");
					$(to_phone).val("");
					$(to_address).val("");
					$(org_provice_code).next().val("");
					$(org_provice_code).next().attr("placeholder", "---请选择---");
					$(org_city_code).next().val("");
					$(org_city_code).next().attr("placeholder", "---请选择---");
					$(org_city_code).next().attr("disabled", "disabled");
					$(org_state_code).next().val("");
					$(org_state_code).next().attr("placeholder", "---请选择---");
					$(org_state_code).next().attr("disabled", "disabled");
				}
				else{
					$.ajax({
						url : "${root}control/orderPlatform/organizationInformationController/toOrgnization.do",
						type : "post",
						data : {
							"org_name" : to_orgnization
						},
						dataType : "json",
						async : false, 
						success : function(result) {
							$(org_provice_code).next().attr("disabled", false);
							$(org_provice_code).children(":first").siblings().remove();
							$(org_provice_code).siblings("ul").children(":first").siblings().remove();
							$(org_city_code).next().attr("disabled", false);
							$(org_city_code).children(":first").siblings().remove();
							$(org_city_code).siblings("ul").children(":first").siblings().remove();
							$(org_state_code).next().attr("disabled", false);
							$(org_state_code).children(":first").siblings().remove();
							$(org_state_code).siblings("ul").children(":first").siblings().remove();
							var content1 = '';
							var content2 = '';
							var content3 = '';
							var content4 = '';
							var content5 = '';
							var content6 = '';
							for(var i =0; i < result.areas.length; i++){
								if(result.areas[i].area_code==result.organizationInformation.org_province_code ){
									var t =i;
								}
								content1 += 
									'<option value="' 
									+ result.areas[i].area_code 
									+ '">'
									+result.areas[i].area_name 
									+'</option>'
								content2 += 
									'<li class="m-list-item" data-value="'
									+ result.areas[i].area_code
									+ '">'
									+ result.areas[i].area_name
									+ '</li>'
								}
						 	for(var x =0; x < result.city.length; x++){
						 		if(result.city[x].area_code==result.organizationInformation.org_city_code ){
									var c =x;
								}
								content3 += 
									'<option value="' 
									+ result.city[x].area_code 
									+ '">'
									+result.city[x].area_name 
									+'</option>'
								content4 += 
									'<li class="m-list-item" data-value="'
									+ result.city[x].area_code
									+ '">'
									+ result.city[x].area_name
									+ '</li>'
								}
							for(var y =0; y < result.state.length; y++){
								if(result.state[y].area_code==result.organizationInformation.org_state_code ){
									var s =y;
								}
								content5 += 
									'<option value="' 
									+ result.state[y].area_code 
									+ '">'
									+result.state[y].area_name 
									+'</option>'
								content6 += 
									'<li class="m-list-item" data-value="'
									+ result.state[y].area_code
									+ '">'
									+ result.state[y].area_name
									+ '</li>'
								} 
								/* content3 += 
									'<option value="' 
									+ result.organizationInformation.org_city_code 
									+ '">'
									+result.organizationInformation.org_city
									+'</option>'
								content4 += 
									'<li class="m-list-item" data-value="'
									+ result.organizationInformation.org_city_code
									+ '">'
									+ result.organizationInformation.org_city
									+ '</li>'*/
							
							 $(org_provice_code).next().val(result.areas[t].area_name );
							$(org_provice_code).next().attr("placeholder", result.areas[t].area_name );
							$(org_city_code).next().val(result.city[c].area_name);
							$(org_city_code).next().attr("placeholder", result.city[c].area_name);
							$(org_state_code).next().val(result.state[s].area_name);
							$(org_state_code).next().attr("placeholder", result.state[s].area_name);
							
							
							$(org_provice_code + " option:eq("+0+")").after(content1);
							$(org_provice_code + " option:eq("+(t+1)+")").attr("selected", true);
							$(org_provice_code).siblings("ul").children().eq(0).after(content2);
							$(org_provice_code).siblings("ul").children().eq(t+1).addClass("m-list-item-active");
							
							
						 	$(org_city_code + " option:eq(0)").after(content3);
							$(org_city_code + " option:eq("+(c+1)+")").attr("selected", true);
							$(org_city_code).siblings("ul").children().eq(0).after(content4);
							$(org_city_code).siblings("ul").children().eq(c+1).addClass("m-list-item-active"); 
							/* $(org_provice_code + " option:eq("+result.organizationInformation.org_province_code+")").after(content1);
							$(org_provice_code + " option:eq("+result.organizationInformation.org_province_code+")").attr("selected", true);
							$(org_provice_code).siblings("ul").children(":first").after(content2);
							$(org_provice_code).siblings("ul").children(":first").addClass("m-list-item-active");*/
							$(org_state_code + " option:eq(0)").after(content5);
							$(org_state_code + " option:eq("+(s+1)+")").attr("selected", true);
							$(org_state_code).siblings("ul").children().eq(0).after(content6);
							$(org_state_code).siblings("ul").children().eq(s+1).addClass("m-list-item-active"); 
							document.getElementById("to_street").value=result.organizationInformation.org_street;
							document.getElementById("to_contacts").value=result.organizationInformation.org_contacts;
							document.getElementById("to_phone").value=result.organizationInformation.org_phone;
							document.getElementById("to_address").value=result.organizationInformation.org_address;
						}
				});
			}
			}
			
			
			
			
			function deleteRow(obj) 
			{ 
			$(obj).parent().parent().remove(); 
			/* var objs=
				$(".qty_obj");
			console.log(objs);
			$.each(objs,function(index,item){
				
				alert(item.value);
			});*/
			setStyleqty();
			setStyleweight();
			setStylevolumn();
			setStyleamount();
			}
			
			
		 	$(function(){ 
			      $("#org_city_code").next().attr("disabled", "disabled");
			      $("#org_state_code").next().attr("disabled", "disabled");
			      $("#producttype_name").next().attr("disabled", "disabled");
			}); 
			
			
			
			
			function ExpressCodeChange(upper, lower){
				upper = "#" + upper;
				lower = "#" +lower;
				var express_code = $(upper).val();
				if(express_code == ""){
					$(lower).children(":first").siblings().remove();
					$(lower).val("");
					$(lower).next().val("");
					$(lower).next().attr("placeholder", "---请选择---");
					$(lower).next().attr("disabled", "disabled");
				} else {
					$.ajax({
						url : "${root}control/orderPlatform/transportProductTypeController/getProductTypeCode.do",
						type : "post",
						data : {
							"express_code" : express_code
						},
						dataType : "json",
						async : false, 
						success : function(result) {
							$(lower).next().attr("disabled", false);
							$(lower).children(":first").siblings().remove();
							$(lower).siblings("ul").children(":first").siblings().remove();
							var content1 = '';
							var content2 = '';
							for(var i =0; i < result.product.length; i++){
								content1 += 
									'<option value="' 
									+ result.product[i].product_type_code 
									+ '">'
									+result.product[i].product_type_name 
									+'</option>'
								content2 += 
									'<li class="m-list-item" data-value="'
									+ result.product[i].product_type_code
									+ '">'
									+ result.product[i].product_type_name
									+ '</li>'
							}
							$(lower).next().val("");
							$(lower).next().attr("placeholder", "---请选择---");
							$(lower + " option:eq(0)").after(content1);
							$(lower + " option:eq(0)").attr("selected", true);
							$(lower).siblings("ul").children(":first").after(content2);
							$(lower).siblings("ul").children(":first").addClass("m-list-item-active");
							
						},
						error : function(result) {
							alert(result);
						}
					});
				}
			}
			
			function setStyleqty()
			{
				 var sum = document.getElementById("total_qty").value=0*1;
				 var objs= $(".qty_obj");
					$.each(objs,function(index,item){
						var qty = item.value;
						sum = sum*1 + qty*1;
					});
				document.getElementById("total_qty").value = sum*1;
			}
			function setStyleweight()
			{
				 var sum = document.getElementById("total_weight").value=0*1;
				 var objs= $(".weight_obj");
					$.each(objs,function(index,item){
						var weight = item.value;
						sum = sum*1 + weight*1;
					});
				document.getElementById("total_weight").value = sum*1;
			}
			function setStylevolumn()
			{
				 var sum = document.getElementById("total_volumn").value=0*1;
				 var objs= $(".volumn_obj");
					$.each(objs,function(index,item){
						var volumn = item.value;
						sum = sum*1 + volumn*1;
					});
				document.getElementById("total_volumn").value = sum*1;
			}
			function setStyleamount()
			{
				var sum = document.getElementById("total_amount").value=0*1;
				var objs= $(".amount_obj");
				$.each(objs,function(index,item){
					var amount = item.value;
					sum = sum*1 + amount*1;
				});
				document.getElementById("total_amount").value = sum*1;
			}
			
			
			function onchecks(){
			       var tb = document.getElementById("tableId");
			       var tr1 = tb.insertRow(tb.rows.length);
			       index= tb.rows.length;
			       var sku_code="sku_code"+(index-1);
			       var sku_name="sku_name"+(index-1);
			       var qty="qty"+(index-1);
			       var weight="weight"+(index-1);
			       var volumn="volumn"+(index-1);
			       var amount="amount"+(index-1);  
			       var td1 = tr1.insertCell();
			       td1.align = "center";
			       td1.innerHTML="<input id='ckb' name='ckb' type='checkbox' style='width: 30px;'>";    
			       var td2 = tr1.insertCell();
			      td2.innerHTML="<input type='text' style='width: 100px;'  name='sku_code' id='"+sku_code+"' class='sku_code_obj' />";
			       var td3 = tr1.insertCell();
			      td3.innerHTML="<input type='text' style='width: 100px;' name='sku_name' id='"+sku_name+"' class='sku_name_obj' />";
			       var td4 = tr1.insertCell();
			      td4.innerHTML="<input type='text' style='width: 100px;' onafterpaste='value=value.replace(/\D/g,'')' name='qty' id='"+qty+"' class='qty_obj' onblur='setStyleqty()'/>";
			       var td5 = tr1.insertCell();
			      td5.innerHTML="<input type='text' style='width: 100px;' onkeyup='value=value.replace(/[^\d\.]/g,'')' name='weight' id='"+weight+"' class='weight_obj' onblur='setStyleweight()'/>";
			      var td6 = tr1.insertCell();
			      td6.innerHTML="<input type='text' style='width: 100px;' onkeyup='value=value.replace(/[^\d\.]/g,'')' name='volumn' id='"+volumn+"' class='volumn_obj' onblur='setStylevolumn()'/>";
			      var td7 = tr1.insertCell();
			      td7.innerHTML="<input type='text' style='width: 100px;' onkeyup='value=value.replace(/[^\d\.]/g,'')' name='amount' id='"+amount+"' class='amount_obj' onblur='setStyleamount()'/>";
			      var td8 = tr1.insertCell();
			      td8.innerHTML="<input type='button' value='删除行' onclick='deleteRow(this);' style='width: 100px;'>";
			      index++;
			}
			function addLogistics(){
				var from_orgnization=document.getElementById("from_orgnization").value;
				var expressCode=document.getElementById("expressCode").value;
				var producttype_name=document.getElementById("producttype_name").value;
				var to_orgnization=document.getElementById("to_orgnization").value;
				var org_provice_code=document.getElementById("org_provice_code").value;
				var org_city_code=document.getElementById("org_city_code").value;
				var org_state_code=document.getElementById("org_state_code").value;
				
				var to_contacts=document.getElementById("to_contacts").value;
				var to_phone=document.getElementById("to_phone").value;
				var to_address=document.getElementById("to_address").value;
				var provice_code=document.getElementById("provice_code").value;
				var city_code=document.getElementById("city_code").value;
				var state_code=document.getElementById("state_code").value;
				var from_contacts=document.getElementById("from_contacts").value;
				var from_phone=document.getElementById("from_phone").value;
				var from_address=document.getElementById("from_address").value;
				
				
				if(to_contacts.length>20){
					alert("收货人联系人名称长度过长，请重新输入");
					return false;
				}
				if(from_contacts.length>20){
					alert("发货人联系人名称长度过长，请重新输入");
					return false;
				}
				if(to_address.length>35){
					alert("收货人地址过长，请重新输入");
					return false;
				}
				if(from_address.length>35){
					alert("发货人地址过长，请重新输入");
					return false;
				}
				if(from_orgnization=="" ||from_orgnization==null ||from_orgnization==undefined){
					alert("发货机构为必填项，请重新输入");
					return false;
				}
				if(expressCode=="" ||expressCode==null ||expressCode==undefined){
					alert("快递公司必填项，请重新输入");
					return false;
				}
				if(to_address==from_address){
					alert("收货机构地址和发货机构地址不能相同，请重新输入");
					return false;
				}
				
				if(producttype_name=="" ||producttype_name==null ||producttype_name==undefined){
					alert("快递业务类型必填项，请重新输入");
					return false;
				}
				if(org_provice_code=="" ||org_provice_code==null ||org_provice_code==undefined){
					alert("收货省必填项，请重新输入");
					return false;
				}
				if(org_city_code=="" ||org_city_code==null ||org_city_code==undefined){
					alert("收货市必填项，请重新输入");
					return false;
				}
				if(org_state_code=="" ||org_state_code==null ||org_state_code==undefined){
					alert("收货区必填项，请重新输入");
					return false;
				}
				if(to_contacts=="" ||to_contacts==null ||to_contacts==undefined){
					alert("收货人必填项，请重新输入");
					return false;
				}
				if(to_phone=="" ||to_phone==null ||to_phone==undefined){
					alert("收货人电话必填项，请重新输入");
					return false;
				}
				if(to_address=="" ||to_address==null ||to_address==undefined){
					alert("收货人地址必填项，请重新输入");
					return false;
				}
				if(provice_code=="" ||provice_code==null ||provice_code==undefined){
					alert("发货人省必填项，请重新输入");
					return false;
				}
				if(city_code=="" ||city_code==null ||city_code==undefined){
					alert("发货人市必填项，请重新输入");
					return false;
				}
				if(state_code=="" ||state_code==null ||state_code==undefined){
					alert("发货人区必填项，请重新输入");
					return false;
				}
				if(from_contacts=="" ||from_contacts==null ||from_contacts==undefined){
					alert("发货人必填项，请重新输入");
					return false;
				}
				if(from_phone=="" ||from_phone==null ||from_phone==undefined){
					alert("发货人电话必填项，请重新输入");
					return false;
				}
				if(from_address=="" ||from_address==null ||from_address==undefined){
					alert("发货人地址必填项，请重新输入");
					return false;
				} 
				if(expressCode=="BSC"){
					var sendcode=document.getElementById("sendcode").value;
					if(sendcode=="" ||sendcode==null ||sendcode==undefined){
						alert("当前快递寄件码必填，请输入");
						return false;
					}
				}
				 var tb = document.getElementsByClassName("sku_code_obj"); //根据id找到这个表格
				 var myRows = null;
				 var jsonArray= new Array();
				 var amount_flag = 0;
				 for(var i=0;i<tb.length;i++)//循环遍历所有的tr行 
				 { 
					 var cell1 =  document.getElementsByClassName("sku_code_obj")[i].value;
				 	 var cell2 =  document.getElementsByClassName("sku_name_obj")[i].value;
				 	 var cell3 =  document.getElementsByClassName("qty_obj")[i].value;
				 	if(cell3=="" ||cell3==null ||cell3==undefined||cell3==0){
						alert("件数必须大于0，请重新输入");
						return false;
					}  
					 var cell4 =  document.getElementsByClassName("weight_obj")[i].value;
					 var cell5 =  document.getElementsByClassName("volumn_obj")[i].value;
					 var cell6 =  document.getElementsByClassName("amount_obj")[i].value; 
					 if($("input[name='placenorder']:checked").length==1){
						 if(cell6=="" ||cell6==null ||cell6==undefined||cell6==0){
								alert("金额必须大于0，请重新输入");
								return false;
							} 
						 amount_flag = 1;
						}
					 myRows=eval("([{'sku_code': '"+cell1+"','sku_name': '"+cell2+"', 'serial_number': '"+i+"','qty': '"+cell3+"', 'weight': '"+cell4+"','volumn': '"+cell5+"', 'amount': '"+cell6+"', 'amount_flag': '"+amount_flag+"'}])"); 
					 jsonArray.push(myRows);
				 }
				 var param = "";
				 var param =$("#addForm").serialize();
				$.ajax({
					type: "POST",
				  	url: "${root}control/orderPlatform/waybillMasterController/add.do?"+param,
					dataType: "json",
					data: 'json='+JSON.stringify(jsonArray),
					success: function (data) {  
					    if(data.data==0){
		   	            	alert("操作成功！");
		   	            	loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/backToMain.do?time='+new Date().getTime());
		   	            }else if(data.data!=0){
		   	            	alert("操作失败！");
		   	            	alert(data.msg);
		   	             }
			    //find();
		                   }
				});	 
			}
			
			function keyPress() {    
			     var keyCode = event.keyCode;    
			     if ((keyCode >= 48 && keyCode <= 57))    
			    {    
			         event.returnValue = true;    
			     } else {    
			           event.returnValue = false;    
			    }    
			 } 
			function  returntostart(){
				var result = confirm("您还未保存，确定离开吗？");
				if(result){
					loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/backToMain.do?time='+new Date().getTime());
				}
				
			}
			
			function onclk(nowEle){
				var to_contacts=document.getElementById("to_contacts").value;
				if(to_contacts=="" ||to_contacts==null ||to_contacts==undefined){
					$(nowEle).next().show();
				}else{
					$(nowEle).next().hide();
				}
			}
			
			function onclkto_phone(nowEle){
				var to_phone=document.getElementById("to_phone").value;
				if(to_phone=="" ||to_phone==null ||to_phone==undefined){
					$("#span_to_phone").show();
				}else{
					$("#span_to_phone").hide();
				}
			}
			function onclk_sendcode(nowEle){
				var expressCode=document.getElementById("expressCode").value;
				if(expressCode=="BSC"){
					var sendcode=document.getElementById("sendcode").value;
					if(sendcode=="" ||sendcode==null ||sendcode==undefined){
						$("#span_sendcode").show();
					}else{
						$("#span_sendcode").hide();
					}
				}
			}
			function onclkto_address(nowEle){
				var to_address=document.getElementById("to_address").value;
				if(to_address=="" ||to_address==null ||to_address==undefined){
					$(nowEle).next().show();
				}else{
					$(nowEle).next().hide();
				}
			}
			function onclkfrom_contacts(nowEle){
				var from_contacts=document.getElementById("from_contacts").value;
				if(from_contacts=="" ||from_contacts==null ||from_contacts==undefined){
					$(nowEle).next().show();
				}else{
					$(nowEle).next().hide();
				}
			}
			function onclkfrom_phone(nowEle){
				var from_phone=document.getElementById("from_phone").value;
				if(from_phone=="" ||from_phone==null ||from_phone==undefined){
					$("#span_from_phone").show();
				}else{
					$("#span_from_phone").hide();
				}
			}
			function onclkfrom_address(nowEle){
				var from_address=document.getElementById("from_address").value;
				if(from_address=="" ||from_address==null ||from_address==undefined){
					$(nowEle).next().show();
				}else{
					$(nowEle).next().hide();
				}
			}
			
			$(function() { 
				$("#span_sendcode").hide();
				
				var from_address=document.getElementById("from_address").value;
				if(from_address=="" ||from_address==null ||from_address==undefined){
					$("#span_from_address").show();
				}else{
					$("#span_from_address").hide();
				}
				var from_phone=document.getElementById("from_phone").value;
				if(from_phone=="" ||from_phone==null ||from_phone==undefined){
					$("#span_from_phone").show();
				}else{
					$("#span_from_phone").hide();
				}
				var from_contacts=document.getElementById("from_contacts").value;
				if(from_contacts=="" ||from_contacts==null ||from_contacts==undefined){
					$("#from_contacts").next().show();
				}else{
					$("#from_contacts").next().hide();
				}
				var to_address=document.getElementById("to_address").value;
				if(to_address=="" ||to_address==null ||to_address==undefined){
					$("#span_to_address").show();
				}else{
					$("#span_to_address").hide();
				}
				var to_phone=document.getElementById("to_phone").value;
				if(to_phone=="" ||to_phone==null ||to_phone==undefined){
					$("#to_phone").next().show();
				}else{
					$("#to_phone").next().hide();
				}
				var to_contacts=document.getElementById("to_contacts").value;
				if(to_contacts=="" ||to_contacts==null ||to_contacts==undefined){
					$("#to_contacts").next().show();
				}else{
					$("#to_contacts").next().hide();
				}
				
				var producttype_name=document.getElementById("producttype_name").value;
				if(producttype_name=="" ||producttype_name==null ||producttype_name==undefined){
					$("#span_producttype_name").show();
				}else{
					$("#span_producttype_name").hide();
				}
				var expressCode=document.getElementById("expressCode").value;
				if(expressCode=="" ||expressCode==null ||expressCode==undefined){
					$("#span_expressCode").show();
				}else{
					$("#span_expressCode").hide();
				}
				var org_state_code=document.getElementById("org_state_code").value;
				if(org_state_code=="" ||org_state_code==null ||org_state_code==undefined){
					$("#span_org_state_code").show();
				}else{
					$("#span_org_state_code").hide();
				}
				var org_city_code=document.getElementById("org_city_code").value;
				if(org_city_code=="" ||org_city_code==null ||org_city_code==undefined){
					$("#span_org_city_code").show();
				}else{
					$("#span_org_city_code").hide();
				}
				var org_provice_code=document.getElementById("org_provice_code").value;
				if(org_provice_code=="" ||org_provice_code==null ||org_provice_code==undefined){
					$("#span_org_provice_code").show();
				}else{
					$("#span_org_provice_code").hide();
				}
				var city_code=document.getElementById("city_code").value;
				if(city_code=="" ||city_code==null ||city_code==undefined){
					$("#span_city_code").show();
				}else{
					$("#span_city_code").hide();
				}
				var state_code=document.getElementById("state_code").value;
				if(state_code=="" ||state_code==null ||state_code==undefined){
					$("#span_state_code").show();
				}else{
					$("#span_state_code").hide();
				}
				
				var provice_code=document.getElementById("provice_code").value;
				if(provice_code=="" ||provice_code==null ||provice_code==undefined){
					$("#span_provice_code").show();
				}else{
					$("#span_provice_code").hide();
				}
			});
	
	</script>
	</head>
	<body>
		<div class="page-header">
			<h1 style='margin-left: 20px'>新增运单</h1>
		</div>
		<!-- 新增页面 -->
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
			<form id="addForm">
				<table> 
			   		<tr>
		  				<td width="160px" align="left"><label>发货机构</label></td>
		  				<td width="170px" align="left">
			  				<select id="from_orgnization" name="from_orgnization" data-edit-select="1" style="width: 168px;">
				  				<option value="${org.org_name}" selected>${org.org_name}</option>
							</select>
		  				</td>
		  				<td width="60px"></td>
		  				<td width="160px" align="left"><label style="width: 95px;">状态</label></td>
		  				<td width="170px" align="left">
		  					<input type='text' id='status' name='status'  value="已录单"  disabled="true" style="width: 160px;">
		  				</td>
		  				<td width="60px"></td>
		  				<td width="160px" align="left"><label style="width: 95px;">客户单号</label></td>
		  				<td width="170px" align="left">
		  					<input type='text' id='customer_number' name='customer_number' style="width: 160px;">
		  				</td>
		  				<td></td>
		  			</tr>
		  		<tr>
		  			<td align="left"><label style="width: 70px;">快递公司</label></td>
		  			<td  align="left">
		  				<select id="expressCode" name="expressCode" data-edit-select="1" style="width: 168px;">
			  				<option value= "">---请选择---</option>
			  				  <c:forEach items= "${venders}" var= "street" >
			  					    <option value="${street.express_code}"  >${street.express_name}</option>
							 </c:forEach> 
						</select><span id="span_expressCode" style="color:red;">*</span>
		  			</td>
		  			<td ></td>
		  			<td align="left"><label style="width: 95px;">快递业务类型</label></td>
		  			<td align="left">
		  				<select id="producttype_name" name="producttype_code" data-edit-select="1" style="width: 168px;">
			  				<option value= "">---请选择---</option>
							 <c:forEach items="${product}" var = "product" >
									<option selected='selected' value="${product.product_type_code}">${product.product_type_name}</option>
							</c:forEach>
						</select><span id="span_producttype_name" style="color:red;">*</span>
		  			</td>
		  			<td></td>
		  			<td align="left"><label style="width: 95px;">快递单号</label></td>
		  			<td align="left">
		  				<input type='text' id='waybill' name='waybill' style="width: 160px;">
		  			</td>
		  			<td></td>
		  		</tr>
		  		
		  		<tr>
		  			<td  align="left"><label style="width: 70px;">支付方式</label></td>
		  			<td  align="left">
		  				<select id="group_code" name="payment" data-edit-select="1" style="width: 168px;">
			  				<option value= "月付">月付</option>
						</select>
		  			</td>
		  			<td></td>
		  			<td  align="left"><label style="width: 95px;">备注</label></td>
		  			<td  align="left">
		  				<input type='text' id='memo' name='memo' style="width: 160px;">
		  			</td>
		  			<td></td>
		  			<td align="left">寄件码</td>
		  			<td align="left">
		  				<input type='text' id='sendcode' onblur="onclk_sendcode(this)" name='sendcode' style="width: 160px;">
		  			</td>
		  			<td><span id="span_sendcode" style="color:red;">*</span></td>
		  		</tr>
		  		<tr>
		  			<td  align="left" colspan="5"><label style="width: 70px;">收货信息</label></td>
		  			<td></td>
		  			<td  align="left"><label style="width: 95px;">收货机构</label></td>
		  			<td  align="left">
		  				<select id="to_orgnization" name="to_orgnization" data-edit-select="1" style="width: 168px;">
			  				<option value= "">---请选择---</option>
			  				  <c:forEach items= "${orgs}" var= "street" >
			  					    <option value="${street.org_name}">${street.org_name}</option>
							 </c:forEach>  
						</select>
		  			</td>
		  			<td></td>
		  		</tr>
		  		<tr>
		  		
		  			<td  align="left"><label style="width: 70px;">省</label></td>
		  			<td  align="left">
		  				<select id="org_provice_code" name="to_province_code"  data-edit-select="1" style="width: 168px;" onchange='shiftArea(1,"org_provice_code","org_city_code","org_state_code","")'>
			  				<option value= "">---请选择---</option>
			  				<c:forEach items="${areas}" var = "area" >
							    <c:if test='${queryOrg.org_province_code eq area.area_code }'> 
									<option selected='selected' value="${area.area_code}">${area.area_name}</option>
								 </c:if>
								 <c:if test='${queryOrg.org_province_code ne area.area_code }'> 
									<option  value="${area.area_code}">${area.area_name}</option>
								 </c:if>
							</c:forEach>
						</select><span id="span_org_provice_code" style="color:red;">*</span>
		  			</td>
		  			<td ></td>
		  			<td  align="left"><label>市</label></td>
		  			<td  align="left">
		  				<select id="org_city_code" name="to_city_code" data-edit-select="1" style="width: 168px;" onchange='shiftArea(2,"org_provice_code","org_city_code","org_state_code","")'>
			  				<option value= "">---请选择---</option>
							<c:forEach items="${city}" var = "cityVar" >
							    <c:if test='${queryOrg.org_city_code eq cityVar.area_code}'>
									<option selected='selected'  value="${cityVar.area_code}">${cityVar.area_name}</option>
								</c:if>
								<c:if test='${queryOrg.org_city_code ne cityVar.area_code}'>
									<option  value="${cityVar.area_code}">${cityVar.area_name}</option>
								</c:if>
							</c:forEach>
						</select><span id="span_org_city_code" style="color:red;">*</span>
		  			</td>
		  			<td align="left"></td>
		  			<td  align="left"><label style="width: 95px;">区</label></td>
		  			<td  align="left">
		  				<select id="org_state_code" name="to_state_code"  data-edit-select="1" style="width: 168px;" >
			  				<option value= "">---请选择---</option>
							<c:forEach items="${state}" var = "stateVar" >
						      <c:if test='${stateVar.area_code eq queryOrg.org_state_code }'> 
								<option selected='selected' value="${stateVar.area_code}">${stateVar.area_name}</option>
							  </c:if>
							  <c:if test='${stateVar.area_code ne queryOrg.org_state_code }'> 
								<option  value="${stateVar.area_code}">${stateVar.area_name}</option>
							  </c:if>	
							</c:forEach>
						</select>
		  			</td>
		  			<td >
		  			<span id="span_org_state_code" style="color:red;">*</span>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td  align="left"><label style="width: 70px;">街道</label></td>
		  			<td  align="left">
		  				<input type='text' id='to_street' name='to_street'  value="${queryOrg.org_street}" style="width: 160px;">
		  			</td>
		  			<td></td>
		  			<td  align="left"><label style="width: 95px;">收货联系人</label></td>
		  			<td  align="left">
		  				<input type='text' id='to_contacts' onblur="onclk(this)" name='to_contacts' value="${queryOrg.org_contacts}" style="width: 160px;"><span style="color:red;">*</span>
		  			</td>
		  			<td>
		  			
		  			</td>
		  			<td  align="left"><label style="width: 95px;">收货联系电话</label></td>
		  			<td  align="left">
		  				<input type='text' onkeyup="value=value.replace(/[^\* \- \d.]/g,'')" onblur="onclkto_phone(this)" id='to_phone' name='to_phone' value="${queryOrg.org_phone}" style="width: 160px;">
		  			</td>
		  			<td><span id="span_to_phone" style="color:red;">*</span></td>
		  		</tr>
		  		<tr> 
		  			<td  align="left"><label style="width: 70px;">收货地址</label></td>
		  			<td  align="left" colspan="4">
		  				<input type='text' id='to_address' name='to_address'  style="width: 550px;"  onblur="onclkto_address(this)" value="${queryOrg.org_address}" ><span class="span_to_address" style="color:red;">*</span>
		  			</td>
		  			<td></td>
		  			<td  align="right" >
		  				<input type="checkbox" name="placenorder" /> </td>
		  			<td  align="left"><label style="width: 95px;">是否保价</label>
		  			</td>
		  			<td></td>
		  		</tr>
<!-- 		  		</table>
		  		
		  		
		  		
		  		
		 	<div id="faq-list-2" class="panel-group accordion-style1 accordion-style2">
												<div class="panel panel-default">
													<div class="panel-heading">
														<a href="#faq-2-1" data-parent="#faq-list-2" data-toggle="collapse" class="accordion-toggle collapsed">
															<i class="icon-chevron-right " data-icon-hide="icon-chevron-down align-top" data-icon-show="icon-chevron-right"></i>
															&nbsp;发货信息
 
							</a>
						</div>
		  		
		  		<div class="panel-collapse collapse" id="faq-2-1">
														<div >
		  		<table > -->
		  		<tr>
		  			<td align="left" colspan="9"> 
		  			<!-- <div id="faq-list-2" class="panel-group accordion-style1 accordion-style2">
												<div class="panel panel-default">
													<div class="panel-heading">
														<a href="#faq-2-1" data-parent="#faq-list-2" data-toggle="collapse" class="accordion-toggle collapsed">
															<i class="icon-chevron-right " data-icon-hide="icon-chevron-down align-top" data-icon-show="icon-chevron-right"></i>
															&nbsp; -->发货信息
		  			<!-- </a></div></div></div> -->
		  			</td>
		  		</tr>
		  		<tr>
		  			<td align="left" >省</td>
		  			<td  align="left" >
		  				<select id="provice_code" name="from_province_code" data-edit-select="1" style="width: 168px;" onchange='shiftArea(1,"provice_code","city_code","state_code","")'>
			  				<option value= "">---请选择---</option>
			  				<c:forEach items="${areas}" var = "area" >
							    <c:if test='${org.org_province_code eq area.area_code }'> 
									<option selected='selected' value="${area.area_code}">${area.area_name}</option>
								 </c:if>
								 <c:if test='${org.org_province_code ne area.area_code }'> 
									<option  value="${area.area_code}">${area.area_name}</option>
								 </c:if>
							</c:forEach>
						</select><span id="span_provice_code" style="color:red;">*</span>
		  			</td>
		  			<td></td>
		  			<td align="left" >市</td>
		  			<td align="left">
		  				<select id="city_code" name="from_city_code" data-edit-select="1" style="width: 168px;" onchange='shiftArea(2,"provice_code","city_code","state_code","")'>
			  				<option value= "">---请选择---</option>
							<c:forEach items="${city}" var = "cityVar" >
							    <c:if test='${org.org_city_code eq cityVar.area_code}'>
									<option selected='selected'  value="${cityVar.area_code}">${cityVar.area_name}</option>
								</c:if>
								<c:if test='${org.org_city_code ne cityVar.area_code}'>
									<option  value="${cityVar.area_code}">${cityVar.area_name}</option>
								</c:if>
							</c:forEach>
						</select><span id="span_city_code" style="color:red;">*</span>
		  			</td>
		  			<td>
		  			
		  			</td>
		  			<td align="left"><label style="width: 95px;" >区</label></td>
		  			<td align="left">
		  				<select id="state_code" name="from_state_code" data-edit-select="1" style="width: 168px;" >
			  				<option value= "">---请选择---</option>
							<c:forEach items="${state}" var = "stateVar" >
						      <c:if test='${stateVar.area_code eq org.org_state_code }'> 
								<option selected='selected' value="${stateVar.area_code}">${stateVar.area_name}</option>
							  </c:if>
							  <c:if test='${stateVar.area_code ne org.org_state_code }'> 
								<option  value="${stateVar.area_code}">${stateVar.area_name}</option>
							  </c:if>	
							</c:forEach>
						</select>
		  			</td>
		  			<td>
		  			<span id="span_state_code" style="color:red;">*</span>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td align="left" >街道</td>
		  			<td align="left" >
			  				<input type='text' id='from_street'  name='from_street' value="${org.org_street}" style="width: 160px;">
		  			</td>
		  			<td></td>
		  			<td align="left" >发货联系人</td>
		  			<td align="left">
		  				<input type='text' id='from_contacts' name='from_contacts' onblur="onclkfrom_contacts(this)" value="${org.org_contacts}" style="width: 160px;"><span style="color:red;">*</span>
		  			</td>
		  			<td>
		  			
		  			</td>
		  			<td  align="left"><label style="width: 95px;">发货联系电话</label></td>
		  			<td align="left">
		  				<input type='text' id='from_phone'  onkeyup="value=value.replace(/[^\* \- \d.]/g,'')" onblur="onclkfrom_phone(this)" name='from_phone' value="${org.org_phone}" style="width: 160px;">
		  			</td>
		  			<td><span id='span_from_phone' style="color:red;">*</span></td>
		  		</tr>
		  		<tr>
		  			<td align="left"  >发货地址</td>
		  			<td align="left" colspan="6">
		  				<input type='text' id='from_address'  style="width: 550px;"  name='from_address'onblur="onclkfrom_address(this)" value="${org.org_address}" >
		  			</td>
		  			<td><span  id='span_from_address' style="color:red;">*</span></td>
		  			<td></td>
		  			<td></td>
		  		</tr>
			</table>
			</form>
			 </div>
		<!-- </div> 
			</div> -->
			
				<div border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;"  class="divclass">
				<input type="button" value="添加行" onclick="onchecks();"> 
			 <table border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;" id="tableId" >
				<tr>
					<td><label style="width: 30px;">选择</label></td>
					<td><label style="width: 100px;">商品条码</label></td>
					<td><label style="width: 100px;">商品名称</label></td>
					<td><label style="width: 100px;">件数</label></td>
					<td><label style="width: 100px;">毛重</label></td>
					<td><label style="width: 100px;">体积</label></td>
					<td><label style="width: 100px;">金额</label></td>
					<td><label style="width: 100px;"></label></td>
				</tr>
				
				<tr>
					<td align = "center"><input id="ckb" name="ckb" type="checkbox"></td>
					<td><label><input style="width: 100px;" class="sku_code_obj" id="sku_code1" name="sku_code"></<input></label></td>
					<td><label ><input style="width: 100px;" class="sku_name_obj" id="sku_name1" name="sku_name"></<input></label></td>
					<td><label ><input style="width: 100px;" class="qty_obj" onkeyup="value=value.replace(/[^\d]/g,'')" id="qty1" name="qty" onblur="setStyleqty()" value="1"></<input></label></td>
					<td><label ><input style="width: 100px;" class="weight_obj" onkeyup="value=value.replace(/[^\d\.]/g,'')" id="weight1" name="weight" onblur="setStyleweight()"></<input></label></td>
					<td><label ><input style="width: 100px;" class="volumn_obj" onkeyup="value=value.replace(/[^\d\.]/g,'')" id="volumn1" name="volumn" onblur="setStylevolumn()"></<input></label></td>
					<td><label ><input style="width: 100px;" class="amount_obj" onkeyup="value=value.replace(/[^\d\.]/g,'')" id="amount1" name="amount" onblur="setStyleamount()"></<input></label></td>
					<td><label style="width: 100px;"></label></td>
				</tr>
				</table>
				<table border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;" >
				<tr>
					<td><label style="width: 30px;">合计</label></td>
					<td><label style="width: 100px;"></label></td>
					<td><label style="width: 100px;"></label></td>
					<td><label  ><input type="text"   readonly id="total_qty" name="total_qty" style="width: 100px;" /></label></td>
					<td><label ><input type="text"   readonly id="total_weight" name="total_weight" style="width: 100px;"/></label></td>
					<td><label ><input type="text"  readonly id="total_volumn" name="total_volumn" style="width: 100px;"/></label></td>
					<td><label><input type="text"  readonly id="total_amount" name="total_amount" style="width: 100px;"/></label></td>
					<td><label><input type="text" readonly  style="width: 100px;"/></label></td>
				</tr>
			</table>
			</div>
			<div>
			<table width='95%'> 
			   <tr> 
		  			<td width="10%" align="middle"><label>制单人</label></td>
		  			<td width="25%" align="middle">
		  				<input type='text' id='create_user' disabled="true" name='create_user' value="${org.org_name}" style="width: 260px;"/>
		  			</td>
		  			<td width="10%" align="middle"><label>修改人</label></td>
		  			<td width="25%" align="middle">
		  				<input type='text' id='update_user' disabled="true" name='update_user' value="${org.org_name}" style="width: 260px;"/>
		  			</td>
		  		</tr>
		  	</table>
			</div>
					<div class= "modal-footer" >
						<button id= "btn_back" type= "button" class= "btn btn-default"  onclick="returntostart()">
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="addLogistics();" >
		     				<i class= "icon-save" aria-hidden= "true" ></i>保存
		     			</button>
					</div>
			<script type="text/javascript">
			jQuery(function($) {
				$('.accordion').on('hide', function (e) {
					$(e.target).prev().children(0).addClass('collapsed');
				})
				$('.accordion').on('show', function (e) {
					$(e.target).prev().children(0).removeClass('collapsed');
				})
			});
		</script>		
	</body>
</html>
<style>

.divclass{
border:5px solid #E0EEEE} 

.select {
    padding: 3px 4px;
    height: 30px;
    width: 160px;
   text-align: enter;
}

.table_head_line td {
font-weight: bold;
font-size: 16px
}

.m-input-select {
    display: inline-block;
    position: relative;
    -webkit-user-select: none;
    width: 160px;
    }
   .accordion-style2.panel-group .panel-heading .accordion-toggle {
   background-color: #edf3f7;
   border: 2px solid #6eaed1;
   border-width: 0 0 0 2px;
}
    
</style>
