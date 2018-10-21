
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>快递信息查询</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type= "text/css" href= "<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
		<link href="<%=basePath %>My97DatePicker/skin/WdatePicker.css" rel="stylesheet">
		<link href="<%=basePath %>daterangepicker/font-awesome.min.css" rel="stylesheet">
		<link href="<%=basePath %>/css/table.css" type="text/css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath %>daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>daterangepicker/moment.js"></script>
<%-- <script type="text/javascript" src="<%=basePath %>daterangepicker/daterangepicker.js"></script> --%>
		<script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type='text/javascript'>
		var page='${queryParam.page}';
		// $("#load_load").show();
		loadingStyle();
		$('#warning_level').val('${queryParam.warning_level}');
      // $('#producttype_code').val('${queryParam.producttype_code}');
      //  $('#warehouse_code').val('${queryParam.warehouse_code}');
      //  $('#store_code').val('${queryParam.store_code}');
      //  $('#provice_code').val('${queryParam.provice_code}');
   // 	$('#city_code').val('${queryParam.city_code}');
    //    $('#state_code').val('${queryParam.state_code}');
    //    $('#warningtype_code').val('${queryParam.warningtype_code}');
    //    $('#warning_level').val('${queryParam.warning_level}');
   //     $('#del_flag').val('${queryParam.del_flag}');
       // $('#work_no').val('${queryParam.work_no}');
      //  $('#platform_no').val('${queryParam.platform_no}');
       // $('#waybill').val('${queryParam.waybill}');
        $('#start_time').val('${queryParam.start_time}');
        $('#end_time').val('${queryParam.end_time}');
        $('#ebc_timestart').val('${queryParam.ebc_timestart}');
        $('#ebc_timeend').val('${queryParam.ebc_timeend}');
        $('#r_timestart').val('${queryParam.r_timestart}');
        $('#r_timeend').val('${queryParam.r_timeend}');
        $('#sr_timestart').val('${queryParam.sr_timestart}');
        $('#sr_timeend').val('${queryParam.sr_timeend}'); 
        $("#pageCountNo").val('${pageView.totalrecord}');
        
	function pageJump() {
		 var param='';
		 loadingStyle();
		// $("#load_load").show();
		 param=$('#form_table').serialize();
		  $.ajax({
				type: "POST",
	           	url:'${root}control/radar/expressinfoMasterController/page.do?'+param+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val(),
	           	dataType: "text",
	           	data:'',
	    		cache:false,
	    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
	           	success: function (data){
	              $("#page_view").html(data);
	              cancelLoadingStyle();
	           	}
		  	});  

		  	
	    }
	/**
	 * 查询
	 */
	 
	 
	 function downLoad(){
		 if(
			    	$("#start_time").val()!='' && $("#end_time").val()!=''
			    	||$("#waybill").val()!=''){
		 $(window).scrollTop(0);
		 loadingStyle();
		 var param='';
		  param=$('#form_table').serialize();
		 $.post('${root}control/radar/expressinfoMasterController/download.do?'+param,function(data){
			 cancelLoadingStyle();
			 window.open('${root}/DownloadFile/'+data.data);	 
		 });
		 }else{
			 alert('称重时间或运单号不能为空！！！');
			 
		 }
	}

	
    function  beforePage(){
    	var param='';
    	if(page==1){
    		alert('已经是首页！');
    		return;
    	}
    	loadingStyle();
    	//$("#load_load").show();
 	   param=$('#form_table').serialize();
 	   param=param+'&page='+(page-1);
 	  $.post('/BT-LMIS/control/radar/expressinfoMasterController/queryData.do?'+param,function(data){
 		     cancelLoadingStyle();
 		    //$("#load_load").hide();
 			//console.log(data);
 			var objdata=data.qr.resultlist;
 			var htmlStr='';
 			$.each(objdata,function(index,item){
 				$('#tbody').html('');
 				page=data.page;
 				item.transport_name=item.transport_name==undefined?'':item.transport_name;
 				item.product_type_name=item.product_type_name==undefined?'':item.product_type_name;
 				item.work_no=item.work_no==undefined?'':item.work_no;
 				item.platform_no=item.platform_no==undefined?'':item.platform_no;
 				item.store_name=item.store_name==undefined?'':item.store_name;
 				item.warehouse_name=item.warehouse_name==undefined?'':item.warehouse_name;
 				item.weight_time=item.weight_time==undefined?'':item.weight_time;
 				item.provice_name=item.provice_name==undefined?'':item.provice_name;
 				item.city_name=item.city_name==undefined?'':item.city_name;
 				item.state_name=item.state_name==undefined?'':item.state_name;
 				item.street_name=item.street_name==undefined?'':item.street_name;
 				htmlStr=htmlStr+'<tr><td>'+(index+1)+'</td><td>'+item.waybill+'</td><td>'+item.transport_name+'</td><td>'+item.product_type_name+'</td><td>'+item.work_no+'</td><td>'+item.platform_no+'</td><td>'
 				+item.store_name+'</td><td>'+item.warehouse_name+'</td><td>'+item.weight_time
 				+'</td><td>'+item.provice_name+'</td><td>'+item.city_name+'</td><td>'+item.state_name+'</td><td>'+item.street_name+'</td><td>'+item.standard_receive_time+'</td><td>'+item.receive_time+'</td><td>'+item.embrance_time+'</td><td>'+item.run_time+'</td> <td><button class="btn btn-xs btn-pink" onclick="toDetail(\''+item.waybill+'\');"><i class="icon-search icon-on-right bigger-110"></i>详细</button></td></tr>';
 			});
 			$('#tbody').html(htmlStr);
 		});
 	   
    }
    function  nextPage(){
    	loadingStyle();
    	//$("#load_load").show();	
    	var param='';
 	   param=$('#form_table').serialize();
 	   param=param+'&page='+(page-1+2);
 	  $.post('/BT-LMIS/control/radar/expressinfoMasterController/queryData.do?'+param,function(data){
 		     cancelLoadingStyle();
 			//$("#load_load").hide();
 			var objdata=data.qr.resultlist;
 			if(objdata.length==0){
 				alert('已经是最后一页！');
 				return;
 			};
 			var htmlStr='';
 			$.each(objdata,function(index,item){
 				page=data.page;
 				$('#tbody').html('');
 				item.transport_name=item.transport_name==undefined?'':item.transport_name;
 				item.product_type_name=item.product_type_name==undefined?'':item.product_type_name;
 				item.work_no=item.work_no==undefined?'':item.work_no;
 				item.platform_no=item.platform_no==undefined?'':item.platform_no;
 				item.store_name=item.store_name==undefined?'':item.store_name;
 				item.warehouse_name=item.warehouse_name==undefined?'':item.warehouse_name;
 				item.weight_time=item.weight_time==undefined?'':item.weight_time;
 				item.provice_name=item.provice_name==undefined?'':item.provice_name;
 				item.city_name=item.city_name==undefined?'':item.city_name;
 				item.state_name=item.state_name==undefined?'':item.state_name;
 				item.street_name=item.street_name==undefined?'':item.street_name;
 				var newDate=new Date();
 				newDate.setTime(item.weight_time);
 				//var qs_standard_Date=new Date();
 				//qs_standard_Date.setTime(item.standard_receive_time);
 				htmlStr=htmlStr+'<tr><td>'+(index+1)+'</td><td>'+item.waybill+'</td><td>'+item.transport_name+'</td><td>'+item.product_type_name+'</td><td>'+item.work_no+'</td><td>'+item.platform_no+'</td><td>'
 				+item.store_name+'</td><td>'+item.warehouse_name+'</td><td>'+item.weight_time
 				+'</td><td>'+item.provice_name+'</td><td>'+item.city_name+'</td><td>'+item.state_name+'</td><td>'+item.street_name+'</td><td>'+item.standard_receive_time+'</td><td>'+item.receive_time+'</td><td>'+item.embrance_time+'</td><td>'+item.run_time+'</td><td><button class="btn btn-xs btn-pink" onclick="toDetail(\''+item.waybill+'\');"><i class="icon-search icon-on-right bigger-110"></i>详细</button></td></tr>';
 			});
 			$('#tbody').html(htmlStr);
 			
 		});
 	   
    }
    function find(){
    	
    	$(window).scrollTop(0);
    	//查询前的前端判断
    	if(
    	$("#start_time").val()!='' && $("#end_time").val()!=''
    	||$("#waybill").val()!=''){
    		loadingStyle();
        	jumpToPage(1);
    	}else{
    		alert('称重时间或运单是必填条件！！！');
    	}
    }

	/* function find() {
	    $("#load_load").show();
	   var param='';
	   param=$('#form_table').serialize();
	//   param='&transport_code='+$('#transport_code').val()+'&producttype_code='+$('#product_code').val()+'&warehouse_code='+$('#warehouse_code').val()+'&store_code='+$('#store_code').val()+'&provice_code='+$('#provice_code').val()+'&city_code='+$('#city_code').val()+'&state_code='+$('#status_code').val()+'&state_code='+$('#status_code').val();
	//   openDiv('/BT-LMIS/control/radar/expressinfoMasterController/query.do?'+param);
	$.post('/BT-LMIS/control/radar/expressinfoMasterController/queryData.do?'+param,function(data){
		$("#load_load").hide();
		var objdata=data.qr.resultlist;
		var htmlStr='';
		$.each(objdata,function(index,item){
			$('#tbody').html('');
			item.express_name=item.express_name==undefined?'':item.express_name;
			item.producttype_name=item.producttype_name==undefined?'':item.producttype_name;
			item.work_no=item.work_no==undefined?'':item.work_no;
			item.platform_no=item.platform_no==undefined?'':item.platform_no;
			item.store_name=item.store_name==undefined?'':item.store_name;
			item.warehouse_name=item.warehouse_name==undefined?'':item.warehouse_name;
			item.weight_time=item.weight_time==undefined?'':item.weight_time;
			item.provice_name=item.provice_name==undefined?'':item.provice_name;
			item.city_name=item.city_name==undefined?'':item.city_name;
			item.state_name=item.state_name==undefined?'':item.state_name;
			item.street_name=item.street_name==undefined?'':item.street_name;
			htmlStr=htmlStr+'<tr><td>'+(index+1)+'</td><td>'+item.waybill+'</td><td>'+item.express_name+'</td><td>'+item.producttype_name+'</td><td>'+item.work_no+'</td><td>'+item.platform_no+'</td><td>'
			+item.store_name+'</td><td>'+item.warehouse_name+'</td><td>'+item.weight_time
			+'</td><td>'+item.provice_name+'</td><td>'+item.city_name+'</td><td>'+item.state_name+'</td><td>'+item.street_name+'</td><td>'+item.standard_receive_time+'</td><td>'+item.receive_time+'</td><td>'+item.embrance_time+'</td><td>'+item.run_time+'</td><td><button class="btn btn-xs btn-pink" onclick="toDetail(\''+item.waybill+'\');"><i class="icon-search icon-on-right bigger-110"></i>详细</button></td></tr>';
		});
		$('#tbody').html(htmlStr);

	});
		} */
	$(document).ready(function() {
		 if('${queryParam.del_flag}'==''){
				$("#warningtype_code").next().attr("disabled", "disabled");
			 
		 }
		 $("#del_flag").bind("change",function(){
			 if(this.value==''){
				  $("#warningtype_code").next().attr("placeholder", "---请选择---");
				  $("#warningtype_code").next().val("---请选择---");
				  $("#warningtype_code").next().attr("disabled", "disabled");
			 }else{
				 $("#warningtype_code").next().attr("placeholder", "");
				 $("#warningtype_code").next().attr("disabled", false);
			 }
			 
		 });
		 
		$('#provice_code').bind('change',function(){
			if(this.value==''){
				$('#city_code').html('<option value="">---请选择---</option>');
				$('#state_code').html('<option value="">---请选择---</option>');	
			}
			$.post('${root}/control/radar/expressinfoMasterController/getArea.do?area_code='+this.value,function(data){	
				if(data.toString()=='[object XMLDocument]'){
					  window.location='/BT-LMIS';
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
		});
		$('#city_code').bind('change',function(){
			if(this.value==''){
				$('#state_code').html('<option value="">---请选择---</option>');	
			}
			$.post('${root}/control/radar/expressinfoMasterController/getArea.do?area_code='+this.value,function(data){
				if(data.toString()=='[object XMLDocument]'){
					  window.location='/BT-LMIS';
						return;
				  };
			if(data.nodeName=='#document')toMain();
			var  htmlStr='<option value="">---请选择---</option>';
			$.each(data.area,function(index,item){
				htmlStr=htmlStr+'<option value='+this.area_code+'>'+this.area_name+'</option>';		
			});
			$('#state_code').html(htmlStr);
			});
		});
		$('#express_code').bind('change',function(data){
			/* $.post("${root}/control/radar/expressinfoMasterController/getRouteStatus.do?express_code="+this.value,function(data){
				if(data.toString()=='[object XMLDocument]'){
					  window.location='/BT-LMIS';
						return;
				  };
		    if(data.nodeName=='#document')toMain();		
			var  htmlStr='<option value="">---请选择---</option>';
			$.each(data.data,function(index,item){
				console.log(item.status_code);
				htmlStr=htmlStr+'<option value='+item.status_code+'>'+item.status+'</option>';		
			});
			alert(htmlStr);
			$('#status_code').html(htmlStr);
			})
			 */
			ExpressCodeChange("express_code","routestatus_code","producttype_code");
		});
		cancelLoadingStyle();
	});

		function ExpressCodeChange(upper, lower,third){
			upper = "#" + upper;
			lower = "#" +lower;
			third ="#"+third;
			var transport_code = $(upper).val();
			if(transport_code == ""){
				$(lower).children(":first").siblings().remove();
				$(lower).val("");
				$(third).val("");
				$(lower).next().val("");
				$(lower).next().attr("placeholder", "---请选择---");
				$(third).next().val("");
				$(third).next().attr("placeholder", "---请选择---");
				$(lower).next().attr("disabled", "disabled");
				$(third).next().attr("disabled", "disabled");
			} else {
				$.ajax({
					url : root + "/control/radar/expressinfoMasterController/getRouteStatus.do",
					type : "post",
					data : {
						"transport_code" : transport_code
					},
					dataType : "json",
					async : false, 
					success : function(result) {
						$(lower).next().attr("disabled", false);
						$(lower).children(":first").siblings().remove();
						$(lower).siblings("ul").children(":first").siblings().remove();
						$(third).next().attr("disabled", false);
						$(third).children(":first").siblings().remove();
						$(third).siblings("ul").children(":first").siblings().remove();
						var content1 = '';
						var content2 = '';
						var content3 = '';
						var content4 = '';
						for(var i =0; i < result.data.length; i++){
							content1 += 
								'<option value="' 
								+ result.data[i].status_code 
								+ '">'
								+result.data[i].status 
								+'</option>'
							content2 += 
								'<li class="m-list-item" data-value="'
								+ result.data[i].status_code
								+ '">'
								+ result.data[i].status
								+ '</li>'
						}
					
						for(var i =0; i < result.products.length; i++){
							content3 += 
								'<option value="' 
								+ result.products[i].product_type_code 
								+ '">'
								+result.products[i].product_type_name 
								+'</option>'
							content4 += 
								'<li class="m-list-item" data-value="'
								+ result.products[i].product_type_code
								+ '">'
								+ result.products[i].product_type_name
								+ '</li>'
						}
						$(lower).next().val("");
						$(lower).next().attr("placeholder", "---请选择---");
						$(third).next().val("");
						$(third).next().attr("placeholder", "---请选择---");
						
						$(third + " option:eq(0)").after(content3);
						$(third + " option:eq(0)").attr("selected", true);
						$(third).siblings("ul").children(":first").after(content4);
						$(third).siblings("ul").children(":first").addClass("m-list-item-active"); 
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
	 function  toDetail(id){
		 $(window).scrollTop(0);
		 loadingStyle();
		 param=$('#form_table').serialize();
	 	 param=param+'&page='+(page);
	 	 Global_var="control/radar/expressinfoMasterController/query.do?"+param;
		 openDiv('${root}control/radar/expressinfoMasterController/toDetails.do?waybill='+id);
	 }
	function toMain(){
		window.location='/BT-LMIS';
		
	}
	 Date.prototype.Format = function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "h+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}


	 function change(){
           if($("#chcdk").attr("checked")=="checked"){
        	   slideDown();
            }
           else{
        	   slideUp();
           }
		 }
	 
	function slideDown(){
		$("#mydiv").slideDown();
//         $("#mydiv").css("display","block");
	 }

	 function slideUp(){
		 $("#mydiv").slideUp();
// 		 $("#mydiv").css("display","none");
	 }
    </script>
	</head>
	<body>
		<div class="page-header"><h1 style='margin-left:20px'>快递信息查询</h1></div>	
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
				<div class= "widget-header header-color-blue">
					<h4>查询列表</h4>
					
<!-- 				<button class="btn btn-xs btn-pink" onclick="slideDown();"> -->
<!-- 				  <i class="icon-search icon-on-right bigger-110"></i>展开 -->
<!-- 			    </button> -->

<!-- 				<button class="btn btn-xs btn-pink" onclick="slideUp();"> -->
<!-- 				  <i class="icon-search icon-on-right bigger-110"></i>收起 -->
<!-- 			    </button> -->
				<input id="chcdk" onchange="change()" type="checkbox" class="ace ace-switch ace-switch-5" />
				<span class="lbl"></span>
	       	</div>
				
				
		<div id="mydiv" style="display: none;">
		  <div class="panel-body">
		  <form id='form_table'>
		   <table>
			   <tr>
			   <td align="left" width="10%"><label class="blue">物流服务商&nbsp;:</label></td>
		  			<!-- <td width="10%"> 物流服务商:</td>-->
					<td width="20%"><select id="express_code"  name="express_code"  class='select'  data-edit-select="1" style='width:160px;height:30px'>
						<option value= ''>---请选择---</option>
						<c:forEach items="${trans_names}" var = "trans_names" >
						<c:if test='${trans_names.transport_code eq queryParam.express_code}'>
							<option selected='selected' value="${trans_names.transport_code}">${trans_names.transport_name}</option></c:if>
							<c:if test='${trans_names.transport_code ne queryParam.express_code}'>
							<option   value="${trans_names.transport_code}">${trans_names.transport_name}</option>
							</c:if>
						</c:forEach>	
					</select> 
					</td>	
					 <td align="left" width="10%"><label class="blue" >物流产品类型&nbsp;:</label></td>
					<td width="20%"><select id="producttype_code" name="producttype_code"  data-edit-select="1">
						<option value= ''>---请选择---</option>
						<c:forEach items='${prodeuct_type}' var='prodeuct_type'>
						<c:if test='${queryParam.producttype_code eq prodeuct_type.product_type_code}'>
							<option selected='selected' value="${prodeuct_type.product_type_code}">${prodeuct_type.product_type_name}</option>
						</c:if>
						<c:if test='${queryParam.producttype_code ne prodeuct_type.product_type_code}'>
							<option  value="${prodeuct_type.product_type_code}">${prodeuct_type.product_type_name}</option>
						</c:if>
						</c:forEach>
					</select></td>
					 <td width="10%"><label class="blue" >快递状态&nbsp;:</label> </td> 
					<td width="20%"><select id="routestatus_code" name="routestatus_code" data-edit-select="1"  class='select' value='${queryParam.status}'>
						<option value= ''>---请选择---</option>
					</select></td>
		  		</tr>
		  		 <tr>
		  		 <td align="left" width="10%"><label class="blue" >系统仓库&nbsp;:</label></td>
		  		<!-- <td width="10%"> :</td> -->
					<td width="20%"><select id='warehouse_code' name="warehouse_code"  data-edit-select="1">
						<option value= ''>---请选择---</option>
						<c:forEach items="${warehouses}" var = "warehouse" >
					       <c:if test="${warehouse.warehouse_code eq queryParam.warehouse_code}">
						<option  value="${warehouse.warehouse_code}">${warehouse.warehouse_name}</option>
					      </c:if>
						  <c:if test="${warehouse.warehouse_code ne queryParam.warehouse_code}">
						<option  value="${warehouse.warehouse_code}">${warehouse.warehouse_name}</option>
					      </c:if>
						</c:forEach>	
					</select></td>	
					 <td align="left" width="10%"><label class="blue" >物理仓库&nbsp;:</label></td>
					<td width="20%"><select id='physical_warehouse_code' name="physical_warehouse_code"   data-edit-select="1">
						<option value= ''>---请选择---</option>
						<c:forEach items="${physical_warehouses}" var = "physical_warehouses" >
						    <c:if test="${physical_warehouses.warehouse_code eq queryParam.warehouse_code}">
							<option selected='selected' value="${physical_warehouses.warehouse_code}">${physical_warehouses.warehouse_name}</option>
							</c:if>
							<c:if test="${physical_warehouses.warehouse_code ne queryParam.warehouse_code}">
							<option  value="${physical_warehouses.warehouse_code}">${physical_warehouses.warehouse_name}</option>
							</c:if>
						</c:forEach>	
					</select></td>
					<td align="left" width="10%"><label class="blue" >店铺&nbsp;:</label></td>
					<td width="20%">
					<select id="store_code" name="store_code"   data-edit-select="1">
						<option value= ''>---请选择---</option>
						<c:forEach items="${stores}" var = "store" >
						   <c:if test="${queryParam.store_code eq store.store_code }">
							<option selected='selected' value="${store.store_code}">${store.store_name}</option>
						   </c:if>
						    <c:if test="${queryParam.store_code ne store.store_code }">
							<option  value="${store.store_code}">${store.store_name}</option>
						   </c:if>
						</c:forEach>	
					</select>
					</td>
		  		</tr>
		  		 <tr>
                <td align="left" width="10%"><label class="blue" >目的地省&nbsp;:</label></td>
					<td width="20%"><select id="provice_code" name="provice_code"  data-edit-select="1" onchange='shiftArea(1,"provice_code","city_code","state_code","")'>
						<option value= ''>---请选择---</option>
						<c:forEach items="${areas}" var = "area" >
						    <c:if test='${queryParam.provice_code eq area.area_code }'> 
							<option selected='selected' value="${area.area_code}">${area.area_name}</option>
							 </c:if>
							 <c:if test='${queryParam.provice_code ne area.area_code }'> 
							<option  value="${area.area_code}">${area.area_name}</option>
							 </c:if>
						</c:forEach>
					</select></td>
		  		 <td align="left" width="10%"><label class="blue" >目的地市&nbsp;:</label></td>
					<td width="20%"><select id="city_code" name="city_code"   data-edit-select="1"  onchange='shiftArea(2,"provice_code","city_code","state_code","")'>
						<option value=''>---请选择---</option>
						<c:forEach items="${city}" var = "cityVar" >
						    <c:if test='${queryParam.city_code eq cityVar.area_code}'>
							<option selected='selected'  value="${cityVar.area_code}">${cityVar.area_name}</option>
							</c:if>
							<c:if test='${queryParam.city_code ne cityVar.area_code}'>
							<option  value="${cityVar.area_code}">${cityVar.area_name}</option>
							</c:if>
						</c:forEach>
					</select></td>
					<td align="left" width="10%"><label class="blue" >目的地区县&nbsp;:</label></td>	
					<td width="20%"><select id="state_code" name="state_code"   data-edit-select="1"  >
					<option value=''>---请选择---</option>
					<c:forEach items="${state}" var = "stateVar" >
					      <c:if test='${stateVar.area_code eq queryParam.state_code }'> 
							<option selected='selected' value="${stateVar.area_code}">${stateVar.area_name}</option>
						  </c:if>
						  <c:if test='${stateVar.area_code ne queryParam.state_code }'> 
							<option  value="${stateVar.area_code}">${stateVar.area_name}</option>
						  </c:if>	
						</c:forEach>
					</select></td>
		  		</tr>
		  		 <tr>
					<td align="left" width="10%"><label class="blue" >目的地街道&nbsp;:</label></td>	
					<td width="20%"><select id="" name=""  class='select'>
						<option value= ''>---请选择---</option>
					</select></td>
					 <td align="left" width="10%"><label class="blue" >预警状态&nbsp;:</label></td>
					<td width="20%"><select id="del_flag" name="del_flag"  class='select'  data-edit-select="1"  value='${queryParam.del_flag}'>
						<option value=''>---请选择---</option>
						<c:if test='${queryParam.del_flag eq "0"}'>
						<option selected='selected' value= 0>---预警中---</option>
						<option  value= 1>---已取消---</option>
						</c:if>
						<c:if test='${queryParam.del_flag eq "1"}'>
						<option  value= 0>---预警中---</option>
					     <option selected='selected' value= 1>---已取消---</option>
						</c:if>
						<c:if test='${queryParam.del_flag ne "1" and queryParam.del_flag ne "0" }'>
					     <option  value= 0>---预警中---</option>
					     <option value= 1>---已取消---</option>
						</c:if>
					</select>
				 </td>	
				  <td align="left" width="10%"><label class="blue" >预警类型&nbsp;:</label></td>	
					<td width="20%">
					<select id="warningtype_code" name="warningtype_code"   data-edit-select="1">
						<option value= ''>---请选择---</option>
					   <c:forEach items="${wms}" var = "wms" >
					       <c:if test='${queryParam.warningtype_code eq wms.warningtype_code}'>
							<option selected='selected' value="${wms.warningtype_code}">${wms.warningtype_name	}</option>
							</c:if>
							<c:if test='${queryParam.warningtype_code ne wms.warningtype_code}'>
							<option  value="${wms.warningtype_code}">${wms.warningtype_name	}</option>
							</c:if>
						</c:forEach>	
					</select>
				 </td>	
		  		</tr>
		  		<tr>
		  			 <td align="left" width="10%"><label class="blue" >签收时间点(开始)&nbsp;:</label></td>
					<td>
				      <div style="width:160px;" >
		                 <input id= "r_timestart" name= "r_timestart" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>
			         
					</td>
		  			 <td align="left" width="10%"><label class="blue" >签收时间点(结束)&nbsp;:</label></td>
					<td>
				      <div style="width:160px;" >
		                   <input id= "r_timeend" name= "r_timeend" type="text" id="d412" style= "width: 100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>
					</td>
					<td width="10%"><label class="blue" >最高预警级别&nbsp;:</label></td> 
					 <td width="20%"><select id="warning_level" name="warning_level"  data-edit-select="1"  class='select'>
						<option value= ''>---请选择---</option>
						<c:forEach varStatus="abc" begin='1' end ='10'> 
						<c:if test="${queryParam.warning_level eq abc.index }">
						<option value= '${abc.index} ' selected='selected'>${abc.index} 级</option>
						</c:if>
						<c:if test="${queryParam.warning_level ne abc.index}">
						<option value= '${abc.index} '>${abc.index} 级</option>
						</c:if>
						</c:forEach>
					</select></td>	 
					<tr>	
				  <td align="left" width="14%"><label class="blue" >理论签收时间点(开始)&nbsp;:</label></td>
					<td>
				      <div style="width:160px;" >
		                 <input id= "sr_timestart" name= "sr_timestart" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>					
				  </td>
				  <td align="left" width="14%"><label class="blue" >理论签收时间点(结束)&nbsp;:</label></td>
					<td>
				      <div style="width:160px;" >
		                 <input id= "sr_timeend" name= "sr_timeend" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>					
				  </td>
	              <td align="left" width="10%"><label class="blue" >平台订单号&nbsp;:</label></td>
					<td width="20%"><input id='platform_no' name='platform_no' type='text' style='height: 30px;width:160px'></td>		  	  
					</tr>					
					<tr>
				  <td align="left" width="10%"><label class="blue" >揽件时间(开始)&nbsp;:</label></td>
					<td>
				      <div style="width:160px;" >
		                 <input id= "ebc_timestart" name= "ebc_timestart" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>					
				  </td>
				  <td align="left" width="14%"><label class="blue" >揽件时间(结束)&nbsp;:</label></td>
					<td>
				      <div style="width:160px;" >
		                 <input id= "ebc_timeend" name= "ebc_timeend" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>					
				  </td>
				<td align="left" width="10%"><label class="blue" >快递单号&nbsp;:</label></td>
				<td width="20%"><input id='waybill'  name='waybill' value='${queryParam.waybill}' type='text' style='height: 30px;width:160px'></td>			  
			</tr>
					<tr>
				  <td align="left" width="10%"><label class="red" >称重时间(开始)&nbsp;:</label></td>
					<td>
				      <div style="width:160px;" >
		                 <input id= "start_time" name= "start_time" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>					
				  </td>
				  <td align="left" width="14%"><label class="red" >称重时间(结束)&nbsp;:</label></td>
					<td>
				      <div style="width:160px;" >
		                 <input id= "end_time" name= "end_time" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>					
				  </td>	
				   <td align="left" width="10%"><label class="blue" >作业单号&nbsp;:</label></td>
					<td width="20%"><input id='work_no' value ='${queryParam.work_no}' name='work_no' type='text' style='height: 30px;width:160px' ></td>   
					</tr>
				<tr>
				  <td align="left" width="10%"><label class="blue" >派送失败时间(开始)&nbsp;:</label></td>
					<td>
				      <div style="width:160px;" >
		                 <input id= "delivery_failure_start_time" name= "delivery_failure_start_time" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>					
				  </td>
				  <td align="left" width="14%"><label class="blue" >派送失败时间(结束)&nbsp;:</label></td>
					<td>
				      <div style="width:160px;" >
		                 <input id= "delivery_failure_end_time" name= "delivery_failure_end_time" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>					
				  </td>	   
				  <td align="left" width="10%"><label class="blue" >派送失败原因&nbsp;:</label></td>
					<td width="20%"><input id='delivery_failure_reason' value ='${queryParam.delivery_failure_reason}' name='delivery_failure_reason' type='text' style='height: 30px;width:160px' ></td>
				  	</tr>
				<tr>
				  <td align="left" width="10%"><label class="blue" >新运单号&nbsp;:</label></td>
		                 <td width="20%"><input id='new_waybill'  name='new_waybill' value='${queryParam.new_waybill}' type='text' style='height: 30px;width:160px'></td>			  
				 </tr>
	
			        <tr>
				       <td width="20%"><input id='pageCountNo' name='pageCountNo' type='text'  value='${pageView.totalrecord}' style='height: 30px;width:160px;display:none' >
					</td>
					 <tr>
				       <td width="20%"><input id='lastPageNo' name='lastPageNo' type='text'  value='${pageView.maxresult}' style='height: 30px;width:160px;display:none' >
					</td>
			</tr>	
			</table>
			</form>
		</div>
		</div></div>
		<div style="margin-top: 10px;margin-left: 20px;margin-bottom: 10px;">
			<button class="btn btn-xs btn-pink" onclick="find();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
 			&nbsp; 
			<button class="btn btn-xs btn-primary" onclick="downLoad();"> 
				<i class="icon-edit"></i>导出
			</button>
			&nbsp;
				<!-- <button class="btn btn-xs btn-primary" onclick="toUpdate();">
				<i class="icon-edit"></i>修改
			</button>&nbsp; -->
				
		</div>
		 <!-- <div id='load_load' style='position:absolute;left:50%;z-index=9999999' ><image src='<%=basePath %>css/metroStyle/img/blueload.gif'></div>  -->
		
		<div id="page_view">
		<div class='title_div' style="height : 520px; overflow : auto; overflow : scroll; border : solid #F2F2F2 1px;"  >
			<table class="table table-striped" overflow-x：show>
		   		<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td class='td_cs'>序号</td>
			  			<td class='td_cs'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;快递单号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;物流服务商&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;物流产品类型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;作业单号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;平台订单号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;店铺&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;揽件仓库&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;物理仓库&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称重时间&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;目的省&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;目的市&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;目的区县&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;目的街道&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;理论签收时间&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;实际签收时间&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;揽件时间&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;派件失败时间&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;派件失败原因&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;新运单号&nbsp;&nbsp;</td>
			  			<td class='td_cs'>&nbsp;&nbsp;系统调用路由接口时间&nbsp;&nbsp;</td>
			  			<!-- <td>快递状态</td> -->
			  			<!-- <td>预警类型</td>
			  			<td>预警级别</td>
			  			<td>预警状态</td>
			  			<td>时效计算起点</td>
			  			<td >标准时效</td>
			  			<td >实际时效</td> -->
			  			<!-- <td class='td_cs'>操作</td> -->
			  		</tr>  	
			  		<!-- 此功能暂时省略
			  		<tr>
			  		     <td></td>
			  		  
<%-- 			  	<c:forEach items="${power}" var="power"> --%>
                        <td nowrap="nowrap">
						  <span class="input-icon input-icon-right">
						    <select style="text-align:center;">
						    <option value="0">≈</option>
						    <option value="1">=</option>
						    <option value="2">></option>
						    <option value="3">>=</option>
						    <option value="4"><</option>
						    <option value="5"><=</option>
						    </select>
							<input type="text" id="form-field-icon-2" style="width:80px;"/>
							<i class="icon-search green" onclick="opSearchDialog('测试数据')"></i>
						 </span>
						</td>
					
<%-- </c:forEach> --%>
			  		</tr>  	 -->			  			
		  		</thead>
		  		<tbody id='tbody' align="center">
		  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr ondblclick='toDetail("${power.waybill}");' value="${power.waybill}" >
			  			<td nowrap="nowrap">${status.count }</td>
			  			<td nowrap="nowrap">${power.waybill}</td>
			  			<td nowrap="nowrap">${power.express_name} </td>
			  			<td nowrap="nowrap">${power.producttype_name}</td>
			  			<td nowrap="nowrap">${power.work_no}</td>
			  			<td nowrap="nowrap">${power.platform_no}</td>
			  			<td nowrap="nowrap">${power.store_name}</td>
			  			<td nowrap="nowrap">${power.warehouse_name}</td>
			  			<td nowrap="nowrap">${power.systemwh_name}</td>
			  			<td nowrap="nowrap">${power.weight_time}</td>
			  			<td nowrap="nowrap">${power.provice_name}</td>
			  			<td nowrap="nowrap">${power.city_name}</td>
			  			<td nowrap="nowrap">${power.state_name}</td>
			  			<td nowrap="nowrap">${power.street_name}</td>
			  			<td nowrap="nowrap">${power.standard_receive_time}</td>
			  			<td nowrap="nowrap">${power.receive_time}</td>
			  			<td nowrap="nowrap">${power.embrance_time}</td>
			  			<td nowrap="nowrap">${power.delivery_failure_time}</td>
			  			<td nowrap="nowrap">${power.delivery_failure_reason}</td>
			  			<td nowrap="nowrap">${power.new_waybill}</td>
			  			<td nowrap="nowrap">${power.run_time}</td>
			  			<!-- <td>
			  			<button class="btn btn-xs btn-pink" onclick='toDetail("${power.waybill}");'>
				       <i class="icon-search icon-on-right bigger-110"></i>详细
			          </button></td> -->
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
     <div id='botton_page' style="margin-right: 30px;margin-top:20px;">
 <!-- <button class="btn  btn-success" onclick="find();"  style='margin-left:80%'>
				<i class="icon-search icon-on-right bigger-110"></i>第 2 页
			</button>
			<button class="btn btn-info" onclick="find();"  style='margin-left:3px'>
				<i class="icon-search icon-on-right bigger-110"></i>第 3 页
			</button>
      <button  style='margin-left:80%' onclick='beforePage()'>上 页</button><button style='margin-left:3px' onclick='nextPage()'>下 页</button></div> -->
<div style="margin-right: 30px;margin-top:20px;">${pageView.pageView}</div>	
      	 </div>
      	 </div> 
	</body>
</html>
<style>
.select {
    padding: 3px 4px;
    height: 30px;
    width: 160px;
   text-align: enter;
}
.title_div td{
	font-size: 15px;
}

.m-input-select {
    display: inline-block;
    position: relative;
    -webkit-user-select: none;
    width: 160px;
    }
</style>

