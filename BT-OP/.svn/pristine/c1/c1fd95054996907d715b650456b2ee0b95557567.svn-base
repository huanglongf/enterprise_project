<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
	<%@ include file="/base/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	</head>
	<link rel="stylesheet" href="<%=basePath %>css/tree.css" type="text/css">
	<link rel="stylesheet" href="<%=basePath %>css/metroStyle/metroStyle.css" type="text/css">
	<!--	<link rel="stylesheet" type="text/css" href="<%=basePath %>assets/css/default.css">-->
	    <%-- <script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/jquery-1.8.3.min.js"></script> --%>
		<%-- <script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script> --%>
		<script type='text/javascript'>
		$(document).ready(function(){$('input[name=query_test]:first').focus();});
		function fastprint() {
			var query_test =document.getElementById("query_test").value;
			if(query_test=="" ||query_test==null ||query_test==undefined){
				alert("请填写客户单号！");
				return false;
			}
						$.post('${root}/control/orderPlatform/waybillMasterController/fastprintOrder.do?query_test='+query_test,
							function(data){
		        	            if(data.data==1){
		        	            	if(data.code==4){
		        	            		alert("暂时无法接单，请稍后再试！");
		        	            	}else{
		        	            		var tb = document.getElementById("query_test");
			        	       			 tb.name="query_test";
			        	       			 tb.value="";
			        	       			window.open('${root }/control/orderPlatform/waybillMasterController/print.do?ids='+data.id,'_blank','height=590,width=430,status=yes,toolbar=yes,fullscreen=no, menubar=no,location=no');
			        	      /*  			 $.post('${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='+idsStr,
		         	     	            			function(data){
		         	     	            				 if(data.out_result==1){
		         	     	            					 window.open(root + data.path);
		         	     	            				 }else if(data.out_result==2){
		         	     	            					 alert("操作失败!");
		         	     	            				 }
		         	     	            			}); */
		        	            	}
		        	            }else if(data.data==2){
		        	            	alert("操作失败！");
		        	            	alert(data.msg);
		        	             }else if(data.data==3){
		        	            	 var result1=  	confirm('该运单已打印，是否打印！');
		        	           		 if(result1){
		        	           			var tb = document.getElementById("query_test");
			        	       			 tb.name="query_test";
			        	       			 tb.value="";
			        	       			window.open('${root }/control/orderPlatform/waybillMasterController/print.do?ids='+data.id,'_blank','height=590,width=430,status=yes,toolbar=yes,fullscreen=no, menubar=no,location=no');
			        	       			 /* $.post('${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='+idsStr,
		         	     	            			function(data){
		         	     	            				 if(data.out_result==1){
		         	     	            					 window.open(root + data.path);
		         	     	            				 }else if(data.out_result==2){
		         	     	            					 alert("操作失败!");
		         	     	            				 }
		         	     	            			}); */
		        	           		 }
		        	             }else if(data.data==5){
		        	            	alert("未找到运单！");
		        	             }else if(data.data==8){
		        	            	alert("下单失败！");
		        	             }else if(data.data==7){
		        	            	alert("运单不可操作！");
		        	             }else if(data.data==6){
		        	            	 $("#fastprintOrder").modal('show');
		        	            	 $.ajax({
		        	 					type: "POST",
		        	 		          	url:'${root}/control/orderPlatform/waybillMasterController/fastprintOrderByquery_test.do?query_test='+query_test,
		        	 		          	dataType: "text",
		        	 		          	data:'',
		        	 		   		cache:false,
		        	 		   		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		        	 		          	success: function (data){
		        	 		             $("#fastprintOrder").html(data);
		        	 		          	}
		        	 			  	}); 
		        	             }
						}); 
			}
		 $('#query_test').on('keypress',function(event){ 
			 var code = event.keyCode;
			if(code == 13){
				var query_test =document.getElementById("query_test").value;
				if(query_test=="" ||query_test==null ||query_test==undefined){
					alert("请填写客户单号！");
					return false;
				}
							$.post('${root}/control/orderPlatform/waybillMasterController/fastprintOrder.do?query_test='+query_test,
								function(data){
			        	            if(data.data==1){
			        	            	if(data.code==4){
			        	            		alert("暂时无法接单，请稍后！");
			        	            	}else{
			        	            		var tb = document.getElementById("query_test");
				        	       			 tb.name="query_test";
				        	       			 tb.value="";
				        	       			window.open('${root }/control/orderPlatform/waybillMasterController/print.do?ids='+data.id,'_blank','height=590,width=430,status=yes,toolbar=yes,fullscreen=no, menubar=no,location=no');
				        	       			/*  $.post('${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='+idsStr,
		         	     	            			function(data){
		         	     	            				 if(data.out_result==1){
		         	     	            					 window.open(root + data.path);
		         	     	            				 }else if(data.out_result==2){
		         	     	            					 alert("操作失败!");
		         	     	            				 }
		         	     	            			}); */
			        	            	}
			        	            }else if(data.data==2){
			        	            	alert("操作失败！");
			        	            	alert(data.msg);
			        	             }else if(data.data==3){
			        	            	 var result1=  	confirm('该运单已打印，是否打印！');
			        	           		 if(result1){
			        	           			var tb = document.getElementById("query_test");
				        	       			 tb.name="query_test";
				        	       			 tb.value="";
				        	       			window.open('${root }/control/orderPlatform/waybillMasterController/print.do?ids='+data.id,'_blank','height=590,width=430,status=yes,toolbar=yes,fullscreen=no, menubar=no,location=no');
				        	       		/* 	 $.post('${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='+idsStr,
			         	     	            			function(data){
			         	     	            				 if(data.out_result==1){
			         	     	            					 window.open(root + data.path);
			         	     	            				 }else if(data.out_result==2){
			         	     	            					 alert("操作失败!");
			         	     	            				 }
			         	     	            			}); */
			        	           		 }
			        	             }else if(data.data==7){
				        	            	alert("运单不可操作！");
			        	             }else if(data.data==8){
				        	            	alert("下单失败！");
			        	             }else if(data.data==5){
				        	            	alert("未找到运单！");
			        	             }else if(data.data==6){
			        	            	 $.ajax({
			        	 					type: "POST",
			        	 		          	url:'${root}/control/orderPlatform/waybillMasterController/fastprintOrderByquery_test.do?query_test='+query_test,
			        	 		          	dataType: "text",
			        	 		          	data:'',
				        	 		   		cache:false,
				        	 		   		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
			        	 		          	success: function (data){
			        	 		          		var tb = document.getElementById("query_test");
					        	       			 tb.name="query_test";
					        	       			 tb.value="";
				        	 		             $("#fastprintOrder").html(data);
					        	            	 $("#myModal").modal('show');
			        	 		          	},
			        	 		          	error:function(msg){
			        	 		          	 	$("#myModal").modal('show');
			        	 		          	}
			        	 			  	}); 
			        	             }
							}); 
			}
		});
		
	</script>
	<body>
		<div class="page-header"><h1 style='margin-left:20px'>快速打印面单</h1></div>	
			<div style="height:30px; "></div>
			<div  style="margin:0 auto; width:25%; height:200px; ">
					<div  style="text-align:center;">
					<a style=" font-size:22px; ">订单号   </a>
						<input type="text" name="query_test" id="query_test" style="height:30px;" id="query_test">
					</div>
					<div style="height:30px; "></div>
					<div>
					<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; " onclick="fastprint();" >
						<i class="icon-upload"></i>打印
					</button>
					<label style="float:right">
							<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; ">
								<i class="icon-upload"></i>返回
							</button>
					</label>
					</div>
			</div>
			
			
			<!-- <div id= "fastprintOrder" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" style="width: 200px;">
			-->
			
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			    <div id= "fastprintOrder" class="modal-dialog" style="width: 800px;">
			       <!--  <div class="modal-content" style="overflow:visible;">
			            <div class="modal-header">
			                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			                <h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
			            </div>
			            <div id= "fastprintOrder" class="modal-body">在这里添加一些文本</div>
			            <div class="modal-footer">
			                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			                <button type="button" class="btn btn-primary">提交更改</button>
			            </div>
			        </div>/.modal-content -->
			    </div><!-- /.modal -->
			</div>
			
		<!-- </div> -->
    </body>
</html>
