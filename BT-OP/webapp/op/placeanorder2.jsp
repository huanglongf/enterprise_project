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
		
		$(document).ready(function(){$('input[name=to_orgnization]:first').focus();
			$('#to_orgnization').bind('change',function(){
				ToOrgnizationChange("to_orgnization","org_provice_code","org_city_code","org_state_code","to_contacts","to_phone","to_address");
			});
			$('#to_address').bind('change',function(){
				ToAddressChange("to_orgnization");
			});
			$('#org_provice_code').bind('change',function(){
				ToAddressChange("to_orgnization");
			});
			$('#org_city_code').bind('change',function(){
				ToAddressChange("to_orgnization");
			});
			$('#org_state_code').bind('change',function(){
				ToAddressChange("to_orgnization");
			});
		});
		
		function ToAddressChange(to_orgnization){
			to_orgnization= "#" +to_orgnization;
			var to_orgnization = document.getElementById('to_orgnization').value;
			if(to_orgnization!=""){
				$("#to_orgnization").val("");
				$("#to_orgnization").next().val("");
				$("#to_orgnization").next().attr("placeholder", "---请选择---");
			}
		}
			
			
		
		function ToOrgnizationChange(to_orgnization, org_provice_code, org_city_code, org_state_code ,to_contacts ,to_phone, to_address){
			to_orgnization= "#" +to_orgnization;
			org_provice_code= "#" +org_provice_code;
			org_city_code= "#" +org_city_code;
			org_state_code= "#" +org_state_code;
			to_contacts= "#" +to_contacts;
			to_phone= "#" +to_phone;
			to_address= "#" +to_address;
			var to_orgnization = document.getElementById('to_orgnization').value;
			if(to_orgnization==""){
				$(org_provice_code).val("");
				$(org_city_code).val("");
				$(org_state_code).val("");
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
						document.getElementById("to_contacts").value=result.organizationInformation.org_contacts;
						document.getElementById("to_phone").value=result.organizationInformation.org_phone;
						document.getElementById("to_address").value=result.organizationInformation.org_address;
						document.getElementById("to_address").title=result.organizationInformation.org_address;
					}
			});
		}
		}
			
			function confirmOrders(){
				$.post('${root}/control/orderPlatform/waybillMasterController/interceptingTime.do?',function(data){
		            if(data.data==1){
						document.getElementById("btn_confirmOrders").disabled=true;
						loadingStyle();
						var idsStr = document.getElementById("id").value;
				  	 	$.post('${root}/control/orderPlatform/waybillMasterController/confirmOrders.do?ids='+idsStr,
			  	 			function(data){
			        	            if(data.data==1){
			        	            	 //window.open('${root}/control/orderPlatform/SFPrintController/print_rebook.do?ids='+idsStr);
			        	            	 $.post('${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='+idsStr,
			    	     	            			function(data){
			    	     	            				 if(data.out_result==1){
			    	     	            					 window.open(root + data.path);
			    	     	            					loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/backToMain.do?time='+new Date().getTime());
			    	     	            				 }else if(data.out_result==2){
			    	     	            					 alert("操作失败!");
			    	     	            					loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/backToMain.do?time='+new Date().getTime());
			    	     	            				 }
			    	     	            			});
			        	            	//loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/backToMain.do');
			        	            	cancelLoadingStyle();
			        	            }else if(data.data==2){
			        	            	cancelLoadingStyle();
			        	            	alert("操作失败！");
			        	            	alert(data.msg);
			        	             }
			          			});
		            }else{
		            	alert("目前已过截单时间，请明天下单，谢谢！");
		             }
	                });
			}
			
			
			
			function addLogistics(){
				console.log(123);
				var from_orgnization=document.getElementById("from_orgnization").value;
				var to_orgnization=document.getElementById("to_orgnization").value;
				var total_qty=document.getElementById("total_qty").value;
				var org_provice_code=document.getElementById("org_provice_code").value;
				var org_city_code=document.getElementById("org_city_code").value;
				var org_state_code=document.getElementById("org_state_code").value;
				var to_contacts=document.getElementById("to_contacts").value;
				var to_phone=document.getElementById("to_phone").value;
				var to_address=document.getElementById("to_address").value;
 				
				if(to_orgnization==from_orgnization){
					alert("收货门店和发货门店不能相同，请重新输入");
					return false;
				}
 				if(to_orgnization=="" ||to_orgnization==null ||to_orgnization==undefined){
					alert("收货机构为必填项，请重新输入");
					return false;
				}
				/* if(org_provice_code=="" ||org_provice_code==null ||org_provice_code==undefined){
					alert("收货省为必填项，请重新输入");
					return false;
				}
				if(org_city_code=="" ||org_city_code==null ||org_city_code==undefined){
					alert("收货市为必填项，请重新输入");
					return false;
				}
				if(org_state_code=="" ||org_state_code==null ||org_state_code==undefined){
					alert("收货区为必填项，请重新输入");
					return false;
				}
				if(to_contacts=="" ||to_contacts==null ||to_contacts==undefined){
					alert("收货人为必填项，请重新输入");
					return false;
				}
				if(to_phone=="" ||to_phone==null ||to_phone==undefined){
					alert("收货电话为必填项，请重新输入");
					return false;
				}
				if(to_address=="" ||to_address==null ||to_address==undefined){
					alert("收货地址为必填项，请重新输入");
					return false;
				} */
				 var param = "";
				 var param =$("#addForm").serialize();
				$.ajax({
					type: "POST",
				  	url: "${root}control/orderPlatform/waybillMasterController/adda.do?"+param,
					dataType: "",
					data: '',
					success: function (data) {  
					    if(data.data==0){
		   	            	alert("操作成功！");
		   	            	document.getElementById("id").value=data.id;
		   	            	document.getElementById("btn_confirmOrders").disabled=false;
		   	            	document.getElementById("btn_submit").disabled=true;
		   	            }else if(data.data!=0){
		   	            	alert("操作失败！");
		   	            	alert(data.msg);
		   	             }
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
				var id =document.getElementById("id").value;
				if(id==null){
					var result = confirm("您还未保存，确定离开吗？");
					if(result){
						/* var t =null;
						 var arrStr = document.cookie.split("; ");
						    for(var i = 0;i < arrStr.length;i ++){
						        var temp = arrStr[i].split("=");
						        if(temp[0] == 'url'){
						        	t=temp[1];
						        };
						    }
						if(t!=null){ 
							loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController'+t);
						}else{*/
							loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/backToMain.do?time='+new Date().getTime());
						/* } */
					}
				}else{
					var result = confirm("您已保存，确定离开吗？");
					loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/backToMain.do?time='+new Date().getTime());
				}
				
				
			}
	
	</script>
	</head>
	<body>
		<div class="page-header">
			<h1 style='margin-left: 20px'>新增门店运单</h1>
		</div>
		<input id="id" type=text name="id" style="display:none" value="">
		<!-- 新增页面 -->
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
			<form id="addForm">
				<table width='95%'> 
			   		<tr>
		  				<td width="10%" align="middle"><label>发货门店</label></td>
		  				<td width="25%" align="middle">
		  					<select id="from_orgnization" name="from_orgnization" data-edit-select="1" style="width: 168px;" disabled="true">
				  				<option value="${org.org_name}" selected>${org.org_name}</option>
							</select>
		  				</td>
		  				<td  width="10%" align="middle"><label style="width: 95px;">收货门店</label></td>
				  		<td  width="25%" align="middle">
		  				<select id="to_orgnization" name="to_orgnization" onblur="onclkto_orgnization(this)" data-edit-select="1" style="width: 260px;">
			  				<option value= "">---请选择---</option>
			  				  <c:forEach items= "${orgs}" var= "street" >
			  					    <option value="${street.org_name}">${street.org_name}</option>
							 </c:forEach>  
						</select>
				  		</td>
		  			</tr>
		  		<tr>
		  			<td width="10%" align="middle"><label style="width: 95px;">客户单号</label></td>
	  				<td width="25%" align="middle">
	  					<input type='text' id='customer_number' name='customer_number' style="width: 260px;">
	  				</td>
		  			<td width="10%" align="middle">件数</td>
		  			<td width="25%" align="middle">
		  				<input type='text' id='total_qty' onblur="onclk_total_qty(this)" name='total_qty' style="width: 260px;" value="1">
		  			</td>
		  		</tr>
		  		<tr>
		  			<td  width="10%" align="middle" style="display:none">收货省</td>
		  			<td  width="25%" align="middle" style="display:none">
		  				<select id="org_provice_code" name="to_province_code"  data-edit-select="1" style="width: 260px;" onchange='shiftArea(1,"org_provice_code","org_city_code","org_state_code","")'>
			  				<option value= "">---请选择---</option>
			  				<c:forEach items="${areas}" var = "area" >
							    <c:if test='${queryOrg.org_province_code eq area.area_code }'> 
									<option selected='selected' value="${area.area_code}">${area.area_name}</option>
								 </c:if>
								 <c:if test='${queryOrg.org_province_code ne area.area_code }'> 
									<option  value="${area.area_code}">${area.area_name}</option>
								 </c:if>
							</c:forEach>
						</select>
		  			</td>
		  			<td width="10%" align="middle" style="display:none">收货市</td>
		  			<td width="25%" align="middle" style="display:none">
		  				<select id="org_city_code" name="to_city_code" data-edit-select="1" style="width: 260px;" onchange='shiftArea(2,"org_provice_code","org_city_code","org_state_code","")'>
			  				<option value= "">---请选择---</option>
							<c:forEach items="${city}" var = "cityVar" >
							    <c:if test='${queryOrg.org_city_code eq cityVar.area_code}'>
									<option selected='selected'  value="${cityVar.area_code}">${cityVar.area_name}</option>
								</c:if>
								<c:if test='${queryOrg.org_city_code ne cityVar.area_code}'>
									<option  value="${cityVar.area_code}">${cityVar.area_name}</option>
								</c:if>
							</c:forEach>
						</select>
		  			</td>
		  		</tr>
		  		<tr>
		  			<td  width="10%" align="middle" style="display:none">收货区</td>
		  			<td  width="25%" align="middle" style="display:none">
		  				<select id="org_state_code" name="to_state_code"  data-edit-select="1" style="width: 260px;" >
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
		  			<td width="10%" align="middle"style="display:none" >收货联系人</td>
				  			<td width="25%" align="middle" style="display:none">
				  				<input type='text' id='to_contacts' onblur="onclk(this)" name='to_contacts' value="${queryOrg.org_contacts}" style="width: 260px;" disabled="disabled">
				  			</td>
		  			
		  		</tr>
		  		<tr>
		  			<td width="10%" align="middle" >发货联系电话</td>
		  			<td width="25%" align="middle">
		  				<input type='text' id='org_phone' onblur="onclk(this)" name='org_phone' value="${org.org_phone}" style="width: 260px;" disabled="disabled">
		  			</td>
		  		
		  			<td  width="10%" align="middle" >收货联系电话</td>
		  			<td  width="25%" align="middle" >
		  				<input type='text' onkeyup="value=value.replace(/[^\* \- \d.]/g,'')" onblur="onclkto_phone(this)" id='to_phone' name='to_phone' value="${queryOrg.org_phone}" style="width: 260px;" disabled="disabled">
		  			</td>
		  		</tr>
		  		<tr>		
		  			<td width="10%" align="middle" >发货地址</</td>
		  			<td width="25%" align="middle">
		  				<input type='text' title="${org.org_address}" id='org_address' onblur="onclk(this)" name='org_address' value="${org.org_address}" style="width: 260px;" disabled="disabled"  onmouseover="this.title=this.value" >
		  				
		  			</td>
		  			<td width="10%" align="middle" >收货地址</td>
		  			<td width="25%" align="middle" >
		  				<input type='text' id='to_address' name='to_address'  title="${queryOrg.org_address}" style="width: 260px;"  onblur="onclkto_address(this)" value="${queryOrg.org_address}" disabled="disabled"  >
		  			</td>
		  		</tr>
		  		<tr>
		  			
		  		</tr>
			</table>
			</form>
			 </div>
			
			<div border-top="1" border-top="#a0c6e5" style="border-top:collapse;"  class="divclass">
			<table width='95%'> 
			   <tr> 
		  			<td width="10%" align="middle"><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;制单人</label></td>
		  			<td width="25%" align="middle">
		  				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='text' id='create_user' disabled="true" name='create_user' value="${org.org_name}" style="width: 260px;"/>
		  				</label>
		  			</td>
		  			<td width="10%" align="middle"><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;修改人</label></td>
		  			<td width="25%" align="middle">
		  				<label>&nbsp;&nbsp;&nbsp;<input type='text' id='update_user' disabled="true" name='update_user' value="${org.org_name}" style="width: 260px;"/>
		  				</label>
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
		     			<button id= "btn_confirmOrders" type= "button" class= "btn btn-primary" disabled="disabled" onclick="confirmOrders();" >
		     				<i class= "icon-save" aria-hidden= "true" ></i>下单打印
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
table, input{
	font-size: 14px
}
.divclass{
border-top:5px solid #E0EEEE} 

.select {
    padding: 3px 4px;
    height: 30px;
    width: 260px;
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
    width: 260px;
    }
   .accordion-style2.panel-group .panel-heading .accordion-toggle {
   background-color: #edf3f7;
   border: 2px solid #6eaed1;
   border-width: 0 0 0 2px;
}

    
</style>
