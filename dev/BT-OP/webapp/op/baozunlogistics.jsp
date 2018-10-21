<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%-- <%@ include file="/base/yuriy.jsp" %> --%>
		 <%@ include file="/templet/common.jsp" %> 
		<title>OP</title>
		<script type='text/javascript'>
		$(function(){
			console.log('baozunlogistics.jsp');
			setTimeout("test();",200);
			//固定表头
			commonFixedTableTitle();
		});
		
		
		function test(){
			$(".m-list").attr('style','display:none');
		}
		
		function test1(){
			setTimeout("test();",200);
			$(".m-list").attr('style','display:none');
		}
		
		function refresh(){
			$('#query_state').next().val("");
			$('#query_state').next().attr("placeholder", "---请选择---");
			$('#expressCode').next().val("");
			$('#expressCode').next().attr("placeholder", "---请选择---");
			$('#producttype_name').next().val("");
			$('#producttype_name').next().attr("placeholder", "---请选择---");
			$('#query_to_orgnization').next().val("");
			$('#query_to_orgnization').next().attr("placeholder", "---请选择---");
			$('#query_org_provice_code').next().val("");
			$('#query_org_provice_code').next().attr("placeholder", "---请选择---");
			document.getElementById("customer_number").value=""; 
			document.getElementById("ess_time").value=""; 
			document.getElementById("ese_time").value=""; 
			document.getElementById("s_time").value=""; 
			document.getElementById("e_time").value=""; 
			document.getElementById("s_time").value=""; 
			document.getElementById("id").value=""; 
			document.getElementById("query_waybill").value=""; 
			test1();
		}
		
		
		function pageJump() {
			 var param='';
			 param=$('#query_logistics').serialize();
			 $.ajax({
					type: "POST",
		          	url:'${root}control/orderPlatform/waybillMasterController/baozunpage.do?'+
		          			param+
		          			'&startRow='+
		          			$("#startRow").val()+
		          			'&endRow='+
		          			$("#startRow").val()+
		          			"&page="+
		          			$("#pageIndex").val()+
		          			"&pageSize="+
		          			$("#pageSize").val(),
		          	dataType: "text",
		          	data:'',
		   		cache:false,
		   		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		          	success: function (data){
		             $("#page_view").html(data);
		          	}
			  	});  
		}
		function addlogistics() {
			loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/baozunaddlogistics.do?time='+new Date().getTime());
		}
		function addlogisticsa() {
			loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/baozunaddlogistics.do?time='+new Date().getTime());
		}
		function toUpdate(uuid) {
			//loadingStyle();
			loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/updatelogistics.do?id='+uuid+'&time='+new Date().getTime());
			//cancelLoadingStyle();
		}
		
		 $(document).on("change",'select#expressCode',function(){
			 ExpressCodeChange("expressCode","producttype_name");
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
		

		function queryLogisticsa(){
			jumpToPage(1);
		}

		
		
		
		function cancelOrder(){
			 if($("input[name='ckb']:checked").length==1){
			  		 var result=  	confirm('是否作废！'); 
						if(result){
							var ids=$("input[name='ckb']:checked");
							var idsStr='';
							$.each(ids,function(index,item){
								idsStr=idsStr+this.value+';';
							});
			  	 	$.post('${root}/control/orderPlatform/waybillMasterController/cancelOrder.do?ids='+idsStr,
			        		function(data){
			        	            if(data.data==1){
			        	            	alert("操作成功！");
			        	            	loadingCenterPanel("${root }/control/orderPlatform/waybillMasterController/backToMain.do?time="+new Date().getTime());
			        	            }else if(data.data==2){
			        	            	alert("操作失败！");
			        	            	alert(data.msg);
			        	             }else if(data.data==0){
			        	            	alert("状态必须为已录单才能作废！");
			        	             }
			                        }
			  		      ); 
			  		
						}
			  	
			}else{
				alert("请选择一行!");
			} 
		}
		
		
		function confirmOrders(){
			 if($("input[name='ckb']:checked").length>=1){
				 var sum = $("input[name='ckb']:checked").length;
			  		 var result=  	confirm('是否对选中'+sum+'进行确认操作！'); 
						if(result){
							var ids=$("input[name='ckb']:checked");
							var idsStr='';
							$.each(ids,function(index,item){
								idsStr=idsStr+this.value+';';
							});
			  	 	$.post('${root}/control/orderPlatform/waybillMasterController/confirmOrders.do?ids='+idsStr,function(data){
			        	            if(data.data==1){
			        	            	alert("操作成功！");
			        	            	loadingCenterPanel("${root }/control/orderPlatform/waybillMasterController/backToMain.do?time="+new Date().getTime());
			        	            }else if(data.data==2){
			        	            	alert("操作失败！");
			        	            	alert(data.msg);
			        	             }else if(data.data==0){
			        	            	alert("状态必须为已录单才能确认下单！");
			        	             }
			                        }
			  		      ); 
			  		
						}
			  	
			}else{
				alert("请勾选!");
			} 
		}
		
		
		function cancelOrderLogistics(){
			 if($("input[name='ckb']:checked").length==1){
				 var sum = $("input[name='ckb']:checked").length;
			  		 var result=  	confirm('是否对选中项进行取消操作！'); 
						if(result){
							var ids=$("input[name='ckb']:checked");
							var idsStr='';
							$.each(ids,function(index,item){
								idsStr=idsStr+this.value+';';
							});
			  	 	$.post('${root}/control/orderPlatform/waybillMasterController/cancelOrderLogistics.do?ids='+idsStr,function(data){
			        	            if(data.data==1){
			        	            	alert("操作成功！");
			        	            	loadingCenterPanel("${root }/control/orderPlatform/waybillMasterController/backToMain.do?time="+new Date().getTime());
			        	            }else if(data.data==2){
			        	            	alert("操作失败！");
			        	            	alert(data.msg);
			        	             }else if(data.data==0){
			        	            	alert("已经下单才能取消下单！");
			        	             }
			                        }
			  		      ); 
			  		
						}
			  	
			}else{
				alert("请勾选一项!");
			} 
		}
		
		function recoveryOrders(){
			 if($("input[name='ckb']:checked").length==1){
			  		 var result=  	confirm('是否恢复！'); 
						if(result){
							var ids=$("input[name='ckb']:checked");
							var idsStr='';
							$.each(ids,function(index,item){
								idsStr=idsStr+this.value+';';
							});
			  	 	$.post('${root}/control/orderPlatform/waybillMasterController/recoveryOrders.do?ids='+idsStr,
			        		function(data){
			        	            if(data.data==1){
			        	            	alert("操作成功！");
			        	            	loadingCenterPanel("${root }/control/orderPlatform/waybillMasterController/backToMain.do?time="+new Date().getTime());
			        	            }else if(data.data==2){
			        	            	alert("操作失败！");
			        	            	alert(data.msg);
			        	             }else if(data.data==0){
			        	            	alert("状态必须为已作废才能恢复！");
			        	             }
			                        }
			  		      ); 
			  		
						}
			  	
			}else{
				alert("请选择一行!");
			}  
		}
		
		function exportwmd(){
		  		 var result=  	confirm('是否导出！'); 
					if(result){
						param=$('#query_logistics').serialize();
						$.ajax({
							type: "POST",  
							url: root+"control/orderPlatform/waybillMasterController/exportwmd.do?"+param, 
							//json格式接收数据  
							dataType: "json",  
							success: function (jsonStr) {
							    if(jsonStr.out_result==1){
								    window.open(root + jsonStr.path);
							    }
							}  
						}); 
		  		
					}
		  	
			
		}
		function csvexportwmd(){
		  		 var result=  	confirm('是否导出！'); 
					if(result){
						param=$('#query_logistics').serialize();
						$.ajax({
							type: "POST",  
							url: root+"control/orderPlatform/waybillMasterController/csvexportwmd.do?"+param, 
							dataType: "json",  
							success: function (jsonStr) {
							    if(jsonStr.out_result==1){
								    window.open(root + jsonStr.path);
							    }else if(jsonStr.out_result==0){
							    	alert("运单不存在，或运单未下单！");
							    }
							}  
						}); 
		  		
					}
		  	
			
		}
		
		function batchprintPdf(){
		  		 var result=  	confirm('是否打印！'); 
					if(result){
						var ids=$("input[name='ckb']:checked");
						var idsStr='';
						$.each(ids,function(index,item){
							idsStr=idsStr+this.value+';';
						});
					$.post('${root}/control/orderPlatform/waybillMasterController/batchprintPdf.do?ids='+idsStr,
						function(data){
	        	            if(data.data==1){
	        	            	//window.open('${root}/control/orderPlatform/SFPrintController/print_rebook.do?ids='+idsStr);
	        	            	$.post('${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='+idsStr,
    	     	            			function(data){
    	     	            				 if(data.out_result==1){
    	     	            					 window.open(root + data.path);
    	     	            				 }else if(data.out_result==2){
    	     	            					 alert("操作失败!");
    	     	            				 }
    	     	            			});
	        	            }else if(data.data==2){
	        	            	alert("操作失败！");
	        	            	alert(data.msg);
	        	             }else{
	        	            	alert("请检查订单状态，存在无法打印的订单！");
	        	             }
					}); 
					}
	}
		
		function testprint(){
			var result=  	confirm('是否打印！'); 
			if(result){
				var ids=$("input[name='ckb']:checked");
				var idsStr='';
				$.each(ids,function(index,item){
					idsStr=idsStr+this.value+';';
				});
			$.post('${root}/control/orderPlatform/waybillMasterController/batchprintPdf.do?ids='+idsStr,
				function(data){
    	            if(data.data==1){
    	            	$.post('${root}/control/orderPlatform/SFPrintController/print_rebook2.do?ids='+idsStr,
    	            			function(data){
    	            				 if(data.out_result==1){
    	            					 window.open(root + data.path);
    	            				 }else if(data.out_result==2){
    	            					 alert("操作失败!");
    	            				 }
    	            			});
    	            }else if(data.data==2){
    	            	alert("操作失败！");
    	            	alert(data.msg);
    	             }else{
    	            	alert("请检查订单状态，存在无法打印的订单！");
    	             }
			}); 
			}
		}
		
		
		 
		 function print() {
				if($("input[name='ckb']:checked").length==1){
			  		 var result=  	confirm('是否打印！'); 
						if(result){
							var ids=$("input[name='ckb']:checked");
							var idsStr='';
							$.each(ids,function(index,item){
								idsStr=this.value;
							});
						$.post('${root}/control/orderPlatform/waybillMasterController/printOrder.do?ids='+idsStr,
							function(data){
		        	            if(data.data==1){
		        	            	if(data.code==4){
		        	            		 var result1=  	confirm('该运单已打印，是否打印！');
		        	            		 if(result1){
		        	            			// window.open('${root }/control/orderPlatform/waybillMasterController/print.do?ids='+idsStr,'_blank','height=680,width=400,status=yes,toolbar=yes,fullscreen=no, menubar=no,location=no');
		        	            			 $.post('${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='+idsStr,
		         	     	            			function(data){
		         	     	            				 if(data.out_result==1){
		         	     	            					 window.open(root + data.path);
		         	     	            				 }else if(data.out_result==2){
		         	     	            					 alert("操作失败!");
		         	     	            				 }
		         	     	            			});
		        	            		 }
		        	            	}else{
		        	            		//window.open('${root }/control/orderPlatform/waybillMasterController/print.do?ids='+idsStr,'_blank','height=680,width=400,status=yes,toolbar=yes,fullscreen=no, menubar=no,location=no');
		        	            		 $.post('${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='+idsStr,
		         	     	            			function(data){
		         	     	            				 if(data.out_result==1){
		         	     	            					 window.open(root + data.path);
		         	     	            				 }else if(data.out_result==2){
		         	     	            					 alert("操作失败!");
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
		        	             }
						}); 
						}
			}else{
				alert("请选择一行!");
			} 
			}
		
	</script>
	</head>
	<body>
			<div class="page-header"><h1 style='margin-left:20px'>宝尊运单</h1></div>	
			
				<div class="search-toolbar">
							<div class="widget-box">
								<div class="widget-header widget-header-small">
									<h5 class="widget-title lighter">查询栏</h5>
									<a class="pointer" title="初始化" onclick="refresh()"><i class="fa fa-refresh"></i></a>
								</div>
								<div class="widget-body">
									<div class="widget-main no-padding-bottom">
										<form id="query_logistics" name="query_logistics" class="container search_form">
											<div class="row form-group">
												<div class="col-md-3 no-padding" style="display: none">
													<div class="col-md-11 no-padding">
														<input class="form-control" id="tabNo" name="tabNo" type="text" value='1' hidden="true">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="wo_no">状态</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<select id="query_state" name="status" data-edit-select="1" style="width: 160px;height: 34px;">
															<option value="" ${""==queryParam.status?"selected='selected'":"" } >---请选择---</option>
															<option value="1" ${"1"==queryParam.status?"selected='selected'":"" }>已录单</option>
															<option value="4" ${"4"==queryParam.status?"selected='selected'":"" }>已下单</option>
															<option value="11" ${"11"==queryParam.status?"selected='selected'":"" }>下单失败</option>
															<option value="2" ${"2"==queryParam.status?"selected='selected'":"" }>待揽收</option>
															<option value="5" ${"5"==queryParam.status?"selected='selected'":"" }>已揽收</option>
															<option value="6" ${"6"==queryParam.status?"selected='selected'":"" }>已发运</option>
															<option value="7" ${"7"==queryParam.status?"selected='selected'":"" }>已签收</option>
															<option value="8" ${"8"==queryParam.status?"selected='selected'":"" }>签收失败</option>
															<option value="9" ${"9"==queryParam.status?"selected='selected'":"" }>已退回</option>
															<option value="10" ${"10"==queryParam.status?"selected='selected'":"" }>已取消</option>
															<option value="0" ${"0"==queryParam.status?"selected='selected'":"" }>已作废</option> 
														</select>
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue">收货门店</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<select id="query_to_orgnization" name="to_orgnization" data-edit-select="1" style="width: 160px;height: 34px;">
															<option value="">---请选择---</option>
															<c:forEach items= "${orgs}" var= "street" >
																<option value="${street.org_name}" ${street.org_name ==  queryParam.to_orgnization ? "selected = 'selected'" : "" }>${street.org_name}</option>
															</c:forEach>
														</select>
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="waybill">客户单号</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input type='text' id='customer_number' value="${queryParam.customer_number}" name='customer_number' style="width: 160px;height: 34px;">
													</div>
												</div>
											</div>
											 
											<div class="row form-group">
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="create_by_group_display">制单日期</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input type='text' id='ess_time' name='ess_time' value="${queryParam.ess_time}" style="width: 160px;height: 34px;"onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="current_processor_group_display">到</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input type='text' id='ese_time' name='ese_time' value="${queryParam.ese_time}" style="width: 160px;height: 34px;"onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="last_process_time">快递单号</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding input-group">
														<input type='text' id='query_waybill' name='waybill' value="${queryParam.waybill}" style="width: 160px;height: 34px;">
													</div>
												</div>
											</div>
											<div class="senior-search">
											<div class="row form-group">
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="create_by_display">下单日期</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input type='text' id='s_time' name='s_time' value="${queryParam.s_time}" style="width: 160px;height: 34px;" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="wo_status_display">到</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding">
														<input type='text'id='e_time' name='e_time' value="${queryParam.e_time}" style="width: 160px;height: 34px;"onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true})">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" for="last_process_time">运单号</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding input-group">
														<input type='text' id='id' name='order_id' value="${queryParam.order_id}" style="width: 160px;height: 34px;">
													</div>
												</div>
											</div>
											
											
											<div class="row form-group">
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" >收件联系人</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding input-group">
														<input type='text' id='to_contacts_like' name='to_contacts_like' value="${queryParam.to_contacts_like}" style="width: 160px;height: 34px;">
													</div>
												</div>
												<div class="col-md-1 no-padding text-center search-title">
													<label class="blue" >寄件联系人</label>
												</div>
												<div class="col-md-3 no-padding">
													<div class="col-md-11 no-padding input-group">
														<input type='text' id='from_contacts_like' name='from_contacts_like' value="${queryParam.from_contacts_like}"  style="width: 160px;height: 34px;">
													</div>
												</div>
											</div>
											
											
											
											
											</div>
											<div class="row text-center">
												<a class="senior-search-shift pointer" title="高级查询"><i class="fa fa-angle-double-down fa-2x" aria-hidden="true"></i></a>
											</div>
										</form>
									</div>
								</div>
							</div>
							<div class="dataTables_wrapper form-inline">
								<div class="row">
									<div class="col-md-12">
										<div class="dataTables_length">
											<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; " onclick="queryLogisticsa();">
												<i class="icon-search icon-on-right bigger-120"></i>查询
											</button> 
											<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; " onclick="addlogisticsa();">
												<i class="icon-hdd"></i>新增宝尊运单
											</button>
											<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; " onclick="loadingCenterPanel('${root}/control/orderPlatform/waybillMasterController/waybiluploade.do');">
												<i class="icon-hdd"></i>导入
											</button>
											<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; " onclick="confirmOrders();">
												<i class="icon-hdd"></i>确认
											</button>
											<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; " onclick="cancelOrderLogistics();">
												<i class="icon-hdd"></i>取消
											</button>
											<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; " onclick="cancelOrder();">
												<i class="icon-hdd"></i>作废
											</button>
												<!-- <button  class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; "  onclick="print();">
												<i class="icon-hdd"></i>打印
											</button> -->
											<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; "  onclick="batchprintPdf();">
												<i class="icon-hdd"></i>批量打印
											</button>
											<!-- <button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; "  onclick="testprint();">
												<i class="icon-hdd"></i>测试打印
											</button> -->
											<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; " onclick="exportwmd();">
												<i class="icon-hdd"></i>导出
											</button>									
										</div>
									</div>
								</div>
							</div>
						</div>
			
		
		
		<div class= "tree_div"  id="page_view">
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;"  id= "sc_content">
			<table id= "table_content" title="运单列表" class= "table table-hover table-striped" style= "table-layout: fixed;" >
	  			<thead align="center">
			  		<tr class='table_head_line'>
			  			<td class="td_cs" style="min-width: 29px;width:29px;" ><input type="checkbox"  id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td class="td_cs" style="width: 100px">序号</td>
			  			<td class="td_cs" style="width: 200px">制单日期</td>
						<td class="td_cs" style="width: 100px">状态</td>
						<td class="td_cs" style="width: 200px">快递公司</td>
						<td class="td_cs" style="width: 200px">快递产品类型</td>
						<td class="td_cs" style="width: 200px">快递单号</td>
						<td class="td_cs" style="width: 100px">支付方式</td>
						<td class="td_cs" style="width: 100px">成本中心</td>
						<td class="td_cs" style="width: 200px">发货联系人</td>
						<td class="td_cs" style="width: 200px">发货联系电话</td>
						<td class="td_cs" style="width: 200px">发货地址</td>
						<td class="td_cs" style="width: 200px">收货联系人</td>
						<td class="td_cs" style="width: 200px">收货联系电话</td>
						<td class="td_cs" style="width: 200px">收货地址</td>
						<td class="td_cs" style="width: 200px">总件数</td>               
						<td class="td_cs" style="width: 200px">总毛重</td>
						<td class="td_cs" style="width: 100px">是否保价</td>
						<td class="td_cs" style="width: 200px">保价金额</td>
						<!-- 序号、制单日期、状态、快递公司、快递业务类型、快递单号、支付方式、发货联系人、发货联系电话、发货地址、收货联系人、收货联系电话、收货地址、总件数、总毛重、是否保价、保价金额 -->
			  		</tr>  	
				</thead>
				<tbody align="center">
			  		<c:forEach items= "${pageView.records }" var= "records" varStatus='status'>
				  		<tr ondblclick='toUpdate("${records.id}")'>
					  		<td class="td_cs" style="min-width: 29px;width:29px;"><input id="ckb"  name="ckb" type="checkbox" value="${records.id}"/></td>
							<td class="td_cs" style="width: 100px;height: 72px" >${status.count }</td>
							<td class="td_cs" style="width: 200px;height: 72px" title="${records.order_time }">
							<fmt:formatDate value="${records.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="td_cs" style="width: 100px;height: 72px" >
									<c:if test="${records.status=='1'}">已录单</c:if>
		   							<c:if test="${records.status=='0'}">已作废</c:if>
		   							<c:if test="${records.status=='2'}">待揽收</c:if>
		   							<c:if test="${records.status=='4'}">已下单</c:if>
		   							<c:if test="${records.status=='5'}">已揽收</c:if>
		   							<c:if test="${records.status=='6'}">已发运</c:if>
		   							<c:if test="${records.status=='7'}">已签收</c:if>
		   							<c:if test="${records.status=='8'}">签收失败</c:if>
		   							<c:if test="${records.status=='9'}">已退回</c:if>
		   							<c:if test="${records.status=='10'}">已取消</c:if>
		   							<c:if test="${records.status=='11'}">下单失败</c:if>
							</td>
							<td class="td_cs" style="width: 200px;height: 72px"
								>${records.express_name }</td>
							<td class="td_cs" style="width: 200px;height: 72px"
								>${records.producttype_name }</td>
							<td class="td_cs" style="width: 200px;height: 72px"
								>${records.waybill }</td>
							<td class="td_cs" style="width: 100px;height: 72px">
									<c:if test="${records.pay_path=='1'}">寄方付</c:if>
		   							<c:if test="${records.pay_path=='2'}">收方付</c:if>
		   							<c:if test="${records.pay_path=='3'}">第三方付</c:if>
		   							<c:if test="${records.pay_path=='4'}">寄付月结</c:if>
		   					</td>
							<td class="td_cs" style="width: 200px;height: 72px"
								>${records.cost_center }</td>
							<td class="td_cs" style="width: 200px;height: 72px"
								>${records.from_contacts }</td>
							<td class="td_cs" style="width: 200px;height: 72px"
								>${records.from_phone }</td>
							<td class="td_cs" style="width: 200px;height: 72px"
								>${records.from_address }</td>
							<td class="td_cs" style="width: 200px;height: 72px"
								>${records.to_contacts }</td>
							<td class="td_cs" style="width: 200px;height: 72px"
								>${records.to_phone }</td>
							<td class="td_cs" style="width: 200px;height: 72px"
								>${records.to_address }</td>
							<td class="td_cs" style="width: 200px;height: 72px"
								>${records.total_qty }</td>
							<td class="td_cs" style="width: 200px;height: 72px"
								>${records.total_weight }</td>
							<td class="td_cs" style="width: 200px;height: 72px">
								<c:if test="${records.amount_flag=='1'}">是</c:if>
								<c:if test="${records.amount_flag=='0'}">否</c:if>
							</td>
							<td class="td_cs" style="width: 200px;height: 72px"
								>${records.insured }</td>
						</tr>
		  			</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style= "margin-right: 1%; margin-top: 20px;" >${pageView.pageView }</div>
		</div>
		
	</body>
</html>
<style>

.divclass{
border:5px solid #E0EEEE
} 

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
</style>
