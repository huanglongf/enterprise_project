<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/base/yuriy.jsp" %>
		<title>OP</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<%-- <link href="<%=basePath %>plugin/daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>plugin/daterangepicker/daterangepicker-bs3.css" /> --%>
		<%-- <script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/daterangepicker.js"></script>--%>
		<%-- <script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script> 
		 <script type= "text/javascript" src= "<%=basePath %>base/base.js" ></script> --%>
		<%--<script type="text/javascript" src="<%=basePath %>/js/jquery.shCircleLoader.js"></script> --%>
		<%-- <script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/jquery-1.8.3.min.js"></script> --%>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<%-- <script type="text/javascript" src="<%=basePath %>js/common.js"></script>  --%>
		<%-- <script type= "text/javascript" src= "<%=basePath %>plugin/My97DatePicker/WdatePicker.js" ></script> --%>
		<script type='text/javascript'>
	
		$(document).ready(function(){$('input[name=to_orgnization]:first').focus();
		$('#to_orgnization').bind('change',function(){
			ToOrgnizationChange("to_orgnization","org_provice_code","org_city_code","org_state_code","to_contacts","to_phone","to_address");
			document.getElementById("btn_confirmOrders").disabled=true; 
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
		$('#to_phone').bind('change',function(){
			ToAddressChange("to_orgnization");
		});
		$('#total_qty').bind('change',function(){
			document.getElementById("btn_confirmOrders").disabled=true; 
		});
		$('#customer_number').bind('change',function(){
			document.getElementById("btn_confirmOrders").disabled=true; 
		});
	});
	
	function ToAddressChange(to_orgnization){
		document.getElementById("btn_confirmOrders").disabled=true; 
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
		
		function updateLogistics(){
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
			 var param =$("#updateForm").serialize();
			$.ajax({
				type: "POST",
			  	url: "${root}control/orderPlatform/waybillMasterController/updateLogisticsa.do?"+param,
				dataType: "",
				data: '',
				success: function (data) {  
				    if(data.data==0){
    	            	alert("操作成功！");
    	            	document.getElementById("id").value=data.id;
	   	            	document.getElementById("btn_confirmOrders").disabled=false; 
    	            }else if(data.data!=0){
    	            	alert("操作失败！");
    	            	alert(data.msg);
    	             }
		    //find();
                    }
			});	 
		}
		
		
		
		function  returntostart(){
			var result = confirm("您还未保存，确定离开吗？");
			if(result){
				loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/backToMain.do?time='+new Date().getTime());
			}
			
		}
		function  no_returntostart(){
				loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/backToMain.do?time='+new Date().getTime());
		}
		
		
		/* function  onclkto_phone(){
			document.getElementById("btn_confirmOrders").disabled=true; 
		} */
		
		$(function() { 
			var total_qty=document.getElementById("total_qty").value;
			if(total_qty=="" ||total_qty==null ||total_qty==undefined){
				$("#span_total_qty").show();
			}else{
				$("#span_total_qty").hide();
			}
			var to_orgnization=document.getElementById("to_orgnization").value;
			if(to_orgnization=="" ||to_orgnization==null ||to_orgnization==undefined){
				$("#span_to_orgnization").show();
			}else{
				$("#span_to_orgnization").hide();
			}
		});
		function change(){
			var mydiv1 = document.getElementById("mydiv1").value;
	           if(mydiv1==""){
	        	   document.getElementById("mydiv1").value="1";
	        	   slideDown();
	            }else{
	            	document.getElementById("mydiv1").value="";
	        	   slideUp();
	           }
			 }
		function slideDown(){
			$("#mydiv1").hide(400);
		 }

		 function slideUp(){
			 $("#mydiv1").show(400);
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
		 
		 function print() {
			 var idsStr = document.getElementById("id").value;
				$.post('${root}/control/orderPlatform/waybillMasterController/printOrder.do?ids='+idsStr,
					function(data){
        	            if(data.data==1){
        	            	if(data.code==4){
        	            		 var result1=  	confirm('该运单已打印，是否打印！');
        	            		 if(result1){
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
        	            		 }
        	            	}else{
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
        	            	}
        	            	
        	            }else if(data.data==2){
        	            	alert("操作失败！");
        	            	alert(data.msg);
        	             }else if(data.data==0){
        	            	alert("尚未下单，无法打印！");
        	             }else if(data.data==3){
        	            	alert("该运单无需打印！");
        	             }else if(data.data==5){
        	            	alert("该运单已取消，无法打印！");
        	             }else if(data.data==6){
        	            	alert("该运单下单失败，无法打印！");
        	             }else if(data.data==7){
        	            	alert("该运单正在下单，无法打印！");
        	             }else if(data.data==8){
        	            	alert("该运单已发运，无法打印！");
        	             }else if(data.data==9){
        	            	alert("该运单已签收，无法打印！");
        	             }else if(data.data==10){
        	            	alert("该运单签收失败，无法打印！");
        	             }else if(data.data==11){
        	            	alert("该运单已退回，无法打印！");
        	             }
			    //find();
				}); 
			}
	</script>
	</head>
	<body>
	<div class="page-header">
		<h1 style='margin-left: 20px'>修改门店运单</h1>
	</div>
	<input type='text' id='order_id' name='order_id' style="display:none" value="${queryParam.order_id}">
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
		<div class= "widget-header header-color-blue">
					<h4>运单路由信息</h4>
				<input id="chcdk" onchange="change()" type="checkbox" class="ace ace-switch ace-switch-5" />
				<span class="lbl"></span>
	       	</div>
			<div id="mydiv1" style="display: none;">
		  <div class="panel-body">
		  <form id="query_logistics" >			
			<table id="query_logistics_inf" align="center"> 
				<!-- <tr>
					<td class="td_cs" style="width: 100px">时间:</td>
					<td class="td_cs" style="width: 100px">运单状态:</td>
				</tr> -->
				<tr >
					<td class="td_cs" style="width: 200px"
						title="${queryParam.update_time }">
						<fmt:formatDate value="${queryParam.update_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td class="td_cs" style="width: 200px"
						title="${queryParam.status }">
							<c:if test="${queryParam.status=='0'}">已作废</c:if>
							<c:if test="${queryParam.status=='1'}">已录单</c:if>
							<c:if test="${queryParam.status=='2'}">待揽收</c:if>
 							<c:if test="${queryParam.status=='4'}">已下单</c:if>
 							<c:if test="${queryParam.status=='5'}">已揽收</c:if>
 							<c:if test="${queryParam.status=='6'}">已发运</c:if>
 							<c:if test="${queryParam.status=='7'}">已签收</c:if>
 							<c:if test="${queryParam.status=='8'}">签收失败</c:if>
 							<c:if test="${queryParam.status=='9'}">已退回</c:if>
 							<c:if test="${queryParam.status=='10'}">已取消</c:if>
						</td>
				</tr>
				<tbody>
			  		<c:forEach items= "${interfaceRouteinfo }" var= "records" varStatus='status'>
				  		<tr>
				  			<td class="td_cs" style="width: 200px"
								title="${records.route_time }">
								<fmt:formatDate value="${records.route_time }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="td_cs" style="width: 200px"
								title="${records.route_remark }">${records.route_remark }</td>
				  		</tr>
		  			</c:forEach>
		  		</tbody>
			</table>
			</form>
		  
		  </div>
		  </div>
		
		<div style="height: 30px;"></div>
		
		 <form id="updateForm">			
			<table  width="95%"> 
			   <tr> 
		  			<td width="10%" align="middle"><label>发货门店</label></td>
		  				<td width="25%" align="middle">
		  					<select id="from_orgnization" name="from_orgnization" data-edit-select="1" style="width: 168px;" disabled="true">
				  				<option value="${queryParam.from_orgnization}" selected>${queryParam.from_orgnization}</option>
							</select>
		  				</td>
		  				<td  width="10%" align="middle"><label style="width: 95px;">收货门店</label></td>
				  		<td  width="25%" align="middle">
		  				<select id="to_orgnization" name="to_orgnization" onblur="onclkto_orgnization(this)" data-edit-select="1" style="width: 260px;">
			  				<option value= "">---请选择---</option>
			  				  <c:forEach items= "${orgs}" var= "street" >
			  					    <c:if test='${street.org_name eq queryParam.to_orgnization }'> 
									<option selected='selected' value="${street.org_name}">${street.org_name}</option>
								 </c:if>
								 <c:if test='${street.org_name ne queryParam.to_orgnization }'> 
									<option  value="${street.org_name}">${street.org_name}</option>
								 </c:if>
							 </c:forEach>  
						</select>
				  		</td>
		  			</tr>
		  		<tr>
		  			<td width="10%" align="middle"><label style="width: 95px;">客户单号</label></td>
	  				<td width="25%" align="middle">
	  					<input type='text' id='customer_number' value="${queryParam.customer_number}" name='customer_number' style="width: 260px;">
	  				</td>
		  			<td width="10%" align="middle">件数</td>
		  			<td width="25%" align="middle">
		  				<input type='text' id='total_qty' onblur="onclk_total_qty(this)" name='total_qty' style="width: 260px;" value="${queryParam.total_qty}">
		  			</td>
		  		</tr>
		  		<tr>
		  			<td  width="10%" align="middle" style="display:none">收货省</td>
		  			<td  width="25%" align="middle" style="display:none">
		  				<select id="org_provice_code" name="to_province_code"  data-edit-select="1" style="width: 260px;" onchange='shiftArea(1,"org_provice_code","org_city_code","org_state_code","")'>
			  				<option value= "">---请选择---</option>
			  				<c:forEach items="${areas}" var = "area" >
							    <c:if test='${queryParam.to_province_code eq area.area_code }'> 
									<option selected='selected' value="${area.area_code}">${area.area_name}</option>
								 </c:if>
								 <c:if test='${queryParam.to_province_code ne area.area_code }'> 
									<option  value="${area.area_code}">${area.area_name}</option>
								 </c:if>
							</c:forEach>
						</select>
		  			</td>
		  			<td width="10%" align="middle" style="display:none">收货市</td>
		  			<td width="25%" align="middle" style="display:none">
		  				<select id="org_city_code" name="to_city_code" data-edit-select="1" style="width: 260px;" onchange='shiftArea(2,"org_provice_code","org_city_code","org_state_code","")'>
			  				<option value= "">---请选择---</option>
							<c:forEach items="${tocity}" var = "cityVar" >
							    <c:if test='${queryParam.to_city_code eq cityVar.area_code}'>
									<option selected='selected'  value="${cityVar.area_code}">${cityVar.area_name}</option>
								</c:if>
								<c:if test='${queryParam.to_city_code ne cityVar.area_code}'>
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
							<c:forEach items="${tostate}" var = "stateVar" >
						      <c:if test='${stateVar.area_code eq queryParam.to_state_code }'> 
								<option selected='selected' value="${stateVar.area_code}">${stateVar.area_name}</option>
							  </c:if>
							  <c:if test='${stateVar.area_code ne queryParam.to_state_code }'> 
								<option  value="${stateVar.area_code}">${stateVar.area_name}</option>
							  </c:if>	
							</c:forEach>
						</select>
		  			</td>
		  			  <td width="10%" align="middle" style="display:none" >收货联系人</td>
		  			<td width="25%" align="middle" style="display:none">
		  				<input type='text' id='to_contacts' onblur="onclk(this)" name='to_contacts' value="${queryParam.to_contacts}" style="width: 260px;" disabled="disabled">
		  			</td>
		  			
		  		
		  		</tr>
		  		<tr>
		  			<td width="10%" align="middle" >发货联系电话</td>
		  			<td width="25%" align="middle">
		  				<input type='text' id='from_phone' onblur="onclk(this)" name='from_phone' value="${queryParam.from_phone}" style="width: 260px;" disabled="disabled">
		  			</td>
		  		
		  			<td  width="10%" align="middle" >收货联系电话</td>
		  			<td  width="25%" align="middle" >
		  				<input type='text' onkeyup="value=value.replace(/[^\* \- \d.]/g,'')" ONBLUR="onclkto_phone(this)" id='to_phone' name='to_phone' value="${queryParam.to_phone}" style="width: 260px;" disabled="disabled">
		  			</td>
		  		</tr>
		  		<tr>
		  					  			<td width="10%" align="middle" >发货地址</</td>
		  			<td width="25%" align="middle">
		  				<input type='text' id='from_address'  title="${queryParam.from_address}" onblur="onclk(this)" name='from_address' value="${queryParam.from_address}" style="width: 260px;" disabled="disabled"   >
		  			</td>
		  			
		  			<td width="10%" align="middle" >收货地址</td>
		  			<td width="25%" align="middle" >
		  			<input type='text' id='to_address' name='to_address'   title="${queryParam.to_address}" style="width: 260px;"  onfocus="onclkto_address(this)" value="${queryParam.to_address}" disabled="disabled" >
		  				
		  			</td>
		  		</tr>
		  		<tr>
		  			<input id="id" type=text name="id" style="display:none" value="${queryParam.id}">
		  		</tr>
			</table>
			</form>
			</div>
			<div style="height: 30px;"></div>
			
			<div border-top="1" border-top="#a0c6e5" style="border-top:collapse;margin-left: 8px;"  class="divclass">
			<table width='95%'> 
			   <tr> 
		  			<td width="10%" align="middle"><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;制单人:</label></td>
		  			<td width="25%" align="middle">
		  				<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type='text' id='create_user' disabled="true" name='create_user' value="${queryParam.create_user}" style="width: 260px;"/>
		  				</label>
		  			</td>
		  			<td width="10%" align="middle"><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;修改人:</label></td>
		  			<td width="25%" align="middle">
		  				<label>&nbsp;&nbsp;&nbsp;<input type='text' id='update_user' disabled="true" name='update_user' value="${queryParam.update_user}" style="width: 260px;"/>
		  				</label>
		  			</td>
		  		</tr>
		  	</table>
		  	</div>
		  	
			
					<div class= "modal-footer" >
						<c:if test="${queryParam.status=='1'}">
							<button id= "btn_back" type= "button" class= "btn btn-default"  onclick="returntostart()">
								<i class= "icon-undo" aria-hidden= "true" ></i>返回
							</button>
			     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="updateLogistics();" >
			     				<i class= "icon-save" aria-hidden= "true" ></i>保存
			     			</button>
			     			<button id= "btn_confirmOrders" type= "button" class= "btn btn-primary"  onclick="confirmOrders();" >
			     				<i class= "icon-save" aria-hidden= "true" ></i>下单打印
			     			</button>
		     			</c:if>
						
						<c:if test="${queryParam.status!='1'&&queryParam.status!='2'}">
							<button id= "btn_back" type= "button" class= "btn btn-default"  onclick="no_returntostart()">
								<i class= "icon-undo" aria-hidden= "true" ></i>返回
							</button>
		     			</c:if>
		     			<c:if test="${queryParam.status=='2'}">
							<button id= "btn_back" type= "button" class= "btn btn-default"  onclick="no_returntostart()">
								<i class= "icon-undo" aria-hidden= "true" ></i>返回
							</button>
							<button id= "btn_confirmOrders" type= "button" class= "btn btn-primary"  onclick="print();" >
			     				<i class= "icon-save" aria-hidden= "true" ></i>打印
			     			</button>
		     			</c:if>
					</div>
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
</style>
