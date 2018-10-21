<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>快递信息查询</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="<%=basePath %>/css/table.css" type="text/css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />
		<link type= "text/css" href= "<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
			<link type= "text/css" href= "<%=basePath %>daterangepicker/font-awesome.min.css" rel= "stylesheet" />
		<link type= "text/css" href= "<%=basePath %>daterangepicker/daterangepicker-bs3.css" rel="stylesheet" />
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>jquery/jquery-2.0.3.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type='text/javascript'>
		var newDate = new Date();
        $('#express_code').val('${queryParam.express_code}');
        $('#producttype_code').val('${queryParam.producttype_code}');
        //embrace_time
        $('#warehouse_code').val('${queryParam.warehouse_code}');
        $('#store_code').val('${queryParam.store_code}');
        $('#provice_code').val('${queryParam.provice_code}')
        $('#city_code').val('${queryParam.city_code}');
        $('#state_code').val('${queryParam.state_code}');
        $('#warningtype_code').val('${queryParam.warningtype_code}');
        $('#warning_level').val('${queryParam.warning_level}');
        $('#warining_status').val('${queryParam.warining_status}');
        $('#work_no').val('${queryParam.work_no}');
        $('#platform_no').val('${queryParam.platform_no}');
        
        
        $('#waybill').val('${queryParam.waybill}');$('#start_time').val('${queryParam.start_time}');$('#end_time').val('${queryParam.end_time}');
	function pageJump() {
		 var param='';
		 param=$('#form').serialize();
		 openDiv('${root}control/radar/expressinfoMasterController/query.do?'+param+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
    	}
	/**
	 * 查询
	 */
	 function goback(){
		var http_var='${root}';
		$(window).scrollTop(0);
		loadingStyle();
		openDiv(http_var+Global_var);
	}
	function find() {
	   var param='';
	   param=$('#form').serialize();
	   $(window).scrollTop(0);
	//   param='&transport_code='+$('#transport_code').val()+'&producttype_code='+$('#product_code').val()+'&warehouse_code='+$('#warehouse_code').val()+'&store_code='+$('#store_code').val()+'&provice_code='+$('#provice_code').val()+'&city_code='+$('#city_code').val()+'&state_code='+$('#status_code').val()+'&state_code='+$('#status_code').val();
		
	   openDiv('${root}/control/radar/expressinfoMasterController/query.do?'+param);
		}
	$(document).ready(function() {
		 cancelLoadingStyle();
	});
	 
	  function  toDetail(id){
		 $('#tbody').html("");
		 $("#myModal").modal("show");
		 //$(".modal-backdrop").remove();
		 var htmlStr='';
         $.post('${root}control/radar/waybillWarninginfoMasterController/toDetails.do?id='+id,function(data){
        	 if(data.toString()=='[object XMLDocument]'){
				  window.location='/BT-LMIS';
					return;
			  };
        	 $.each(data.data,function(index,item){ 
        		 var newDate = new Date();
        		 var newDate1=new Date();
        		 var newDate0=new Date();
        		 if(item.efficient_time!=null){
        			 newDate1.setTime(item.efficient_time);
        			 newDate1=newDate1.Format("yyyy-MM-dd hh:mm:ss");
        		 }else{
        			 newDate1='--'; 
        		 }
        		 if(item.happen_time!=null){
        			 newDate.setTime(item.happen_time);
        			 newDate=newDate.Format("yyyy-MM-dd hh:mm:ss");
        		 }else{
        			 newDate='--'; 
        		 }
        		 if(item.route_time!=null){
        			 newDate0.setTime(item.route_time);
        			 newDate0=newDate0.Format("yyyy-MM-dd hh:mm:ss");
        		 }else{
        			 newDate0='--'; 
        		 }	
        		 
        		/* 	 
        		 newDate1.setTime(item.efficient_time);
        		 newDate.setTime(item.happen_time);
        		 newDate0.setTime(item.route_time);
       	 
        		 */
        		
        		 
        		 htmlStr=htmlStr+'<tr><td>'+(index+1)+'</td><td>'+newDate+'</td><td>'+newDate0+'</td><td>'+newDate1+'</td><td>'+item.source+'</td><td>'+item.reason+'</td><td>'+item.warning_level+'</td></tr>'; 
        	  
          });
        	 $('#tbody').html(htmlStr);
         });
         
		// openDiv('${root}control/radar/waybillWarninginfoMasterController/toDetails.do?id='+id);
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
	 
	 function  addWarning(){
		var warning_level= $("#warn_type option:selected").attr("level");
	$.post('${root}control/radar/waybillWarninginfoMasterController/addWarning.do',{
		warning_level:warning_level,
		id:'${queryParam.id}',
		warningtype_code:$("#warn_type  option:selected").val(),
		waybill:'${queryParam.waybill}',
		warning_status:0,
		warning_category:0,
		remark:$("#remark").val(),
		express_code:'${queryParam.express_code}'
	},function(data){
		 if(data.toString()=='[object XMLDocument]'){
			  window.location='/BT-LMIS';
				return;
		  }
		if(data.code==1){
			alert('添加成功！');
			 //openDiv('${root}control/radar/expressinfoMasterController/toDetails.do?waybill=${queryParam.waybill}');	
			 var  list=data.data_list;
			  var  strHtml='';
			  $.each(list,function(index,item){
					var  warning='';var func='';var wk_flag='';
					wk_flag=item.wk_flag=='1'?'已生成工单':'未生成工单';
					 if(item.del_flag==0){
						  warning='预警中';
						  func='<button class=\"btn btn-xs btn-inverse\" onclick=\"del(\''+item.id+'\');\"><i class=\"icon-trash\"></i>取消预警 </button>';
					 }else{
						 warning='取消预警';
						 func='';
					 }
					 newDate.setTime(item.happen_time);
					  strHtml=strHtml+'<tr><td>'+(index+1)+'</td><td>'+newDate.Format("yyyy-MM-dd hh:mm:ss")+'</td>'+
						 '<td>'+item.warningtype_name+'</td>' +
						 '<td>'+warning+'  </td><td>'+wk_flag+'</td><td>工单状态</td>'+
						 '<td><button class=\"btn btn-xs btn-pink\"  data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"toDetail(\''+item.id+'\');\">'+
						 '<i class=\"icon-search icon-on-right bigger-110\"></i>详细</button></td><td>'+
						 func+'</td></tr>';
				  });
			
			  //document.getElementById("warn_tbody").innerHTML='';
			  $('#warn_tbody').html(strHtml);
			  
		
		}else{
			alert(data.msg);
		}
		
	});  
	 }
	 
	 function del(id,pid){
		 $.post('${root}control/radar/waybillWarninginfoMasterController/del.do?id='+id+'&waybill=${queryParam.waybill}',function(data){
			  if(data.toString()=='[object XMLDocument]'){
				  window.location='/BT-LMIS';
					return;
			  }
			  if(data.code==1){
				  alert('操作成功');
				  var  list=data.data_list;
				  var  strHtml='';
				  $.each(list,function(index,item){
						var  warning='';var func='';
						  if(item.del_flag==0){
							  warning='预警中';
							  func='<button class=\"btn btn-xs btn-inverse\" onclick=\"del(\''+item.id+'\');\"><i class=\"icon-trash\"></i>取消预警 </button>';
						 }else{
							 warning='取消预警';
							 func='';
						 }
						  newDate.setTime(item.happen_time);
						  strHtml=strHtml+'<tr><td>'+(index+1)+'</td><td>'+newDate.Format("yyyy-MM-dd hh:mm:ss")+'</td>'+
							 '<td>'+item.warningtype_name+'</td>' +
							 '<td>'+warning+'  </td><td>生成工单</td><td>工单状态</td>'+
							 '<td><button class=\"btn btn-xs btn-pink\"  data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"toDetail(\''+item.id+'\');\">'+
							 '<i class=\"icon-search icon-on-right bigger-110\"></i>详细</button></td><td>'+
							 func+'</td></tr>';
					  });
				 
				  $('#warn_tbody').html(strHtml);
				  
			  }else{
				  alert(data.msg);
			  }
			 /* if(data.documentURI.indexOf('BT-LMIS')>0){
					window.location='/BT-LMIS';
					return;
				}; */
			// openDiv('${root}control/radar/expressinfoMasterController/toDetails.do?waybill=${queryParam.waybill}');

		 });
		 
	 }
	
	 
		function toMain(){
			window.location='/BT-LMIS';
			
		}
    </script>
	</head>
	<body>
		<div class="page-header"><h1 style='margin-left:20px'>预警信息明细</h1></div>	
		<div style="margin-top: 20px;margin-left: 20px;margin-bottom: 20px;">
		<form id='form'>
			<table>
			   <tr>
			        <td align="left" width="10%"><label class="blue" >物流服务商&nbsp;:</label></td>
					<td width="20%">${queryParam.express_name}</td>	
					 <td align="left" width="10%"><label class="blue" >物流产品类型&nbsp;:</label></td>
					<td width="20%">${queryParam.producttype_name}</td>
					 <td align="left" width="10%"><label class="blue" >揽件件时间&nbsp;:</label></td>
					<td>${queryParam.embrance_time}</td>
		  		</tr>
		  		 <tr>
		  		 <td align="left" width="10%"><label class="blue" >揽件仓库&nbsp;:</label></td>
					<td width="20%">${queryParam.warehouse_name}</td>	
					<td align="left" width="10%"><label class="blue" >店铺&nbsp;:</label></td>
					<td width="20%">${queryParam.store_name}</td>
					<td align="left" width="10%"><label class="blue" >目的地省&nbsp;:</label></td>
					<td width="20%">${queryParam.provice_name}</td>
		  		</tr>
		  		 <tr>
		  		 <td align="left" width="10%"><label class="blue" >目的地市&nbsp;:</label></td>
					<td width="20%">${queryParam.city_name}</td>	
					<td align="left" width="10%"><label class="blue" >目的地区县&nbsp;:</label></td>
					<td width="20%">${queryParam.state_name}</td>
					<td align="left" width="10%"><label class="blue" >目的地街道&nbsp;:</label></td>
					<td width="20%">${queryParam.street_name}</td>
		  		</tr>
		  		 <tr>
		  		  <td align="left" width="10%"><label class="blue" >快递状态&nbsp;:</label></td>
					<td width="20%">${queryParam.routestatus_name}</td>
					 <td align="left" width="10%"><label class="blue" >理论签收时间&nbsp;:</label></td>
					<td width="20%">${queryParam.standard_receive_time}</td>
					 <td align="left" width="10%"><label class="blue" >实际签收时间&nbsp;:</label></td>
					<td width="20%">${queryParam.receive_time}</td>			
		  		</tr>

		  		<tr>
				 <td align="left" width="10%"><label class="blue" >称重时间&nbsp;:</label></td>
					<td width="20%">${queryParam.weight_time}</td>
		  		<!--<td align="left" width="10%"><label class="blue" >预警状态&nbsp;:</label></td>	
					 <td width="20%"><input type='text' readonly="readonly"  value='${queryParam.warining_status}'/>
					</td> -->	
					<td align="left" width="10%"><label class="blue" >作业单号&nbsp;:</label></td>
					<td width="20%">${queryParam.work_no}</td>
					<td align="left" width="10%"><label class="blue" >快递单号&nbsp;:</label></td>
					<td width="20%">${queryParam.waybill}</td>
		  		</tr>
		  		<tr>
		  		   <td align="left" width="10%"><label class="blue" >平台订单号&nbsp;:</label></td>
					<td width="20%">${queryParam.platform_no}</td>
				    <td align="left" width="10%"><label class="blue" >收件人&nbsp;:</label></td>
					<td width="20%">${queryParam.shiptoname}</td>
					<td align="left" width="10%"><label class="blue" >联系方式&nbsp;:</label></td>
					<td width="20%">${queryParam.phone}</td>
				</tr>
				<tr>

					
					
				<td align="left" width="10%"><label class="blue" >详细收货地址&nbsp;:</label></td>
					<td width="20%" colspan="3">${queryParam.address}</td>
			  </tr>
			</table>
			</form>
			<button class="btn btn-info" onclick='goback();' style='margin-left:73%'>
			    	       <i class=""></i>返回
			           </button>
			           
		</div>
		<!-- tab -->
		
<ul id="myTab" class="nav nav-tabs" style='height:44px'>
	<li class="active">
		<a href="#route" data-toggle="tab">
			路由信息
		</a>
	</li>
	<li><a href="#wrap" data-toggle="tab">包裹明细</a></li>
	<li><a href="#warning" data-toggle="tab">预警信息</a></li>
</ul>
<div id="myTabContent" class="tab-content">
	<div class="tab-pane fade in active" id="route">
		<div style="height : 400px; overflow : auto; overflow : scroll; border : solid #F2F2F2 1px;"  >
			<table class="table table-striped" overflow-x：show>
		   		<thead  align="center">
			  		<tr  class='table_head_line'>
			  			<td>序号</td>
			  			<td>发生时间</td>
			  			<td>路由状态代码</td>
			  			<td>路由状态</td>
			  			<td>运单号</td>
			  			<td>描述</td>
			  		</tr>  	
		  		</thead>
		  		<tbody  align="center">
		  	    <c:forEach items='${route}' var ='record' varStatus='status'>
		  	       <tr>
			  			<td>${status.count}</td>
			  			<td><fmt:formatDate value="${record.route_time}" type="both"/></td>
			  			<td>${record.routestatus_code }</td>
			  			<td>${record.status}</td>
			  			<td>${record.waybill }</td>
			  			<td>${record.description }</td>
			  		</tr>  
		  	    </c:forEach>
		  		</tbody>
			</table>	
	</div></div>
	<div class="tab-pane fade" id="wrap">
		<div class="tab-pane fade in active">
		<div style="height : 400px; overflow : auto; overflow : scroll; border : solid #F2F2F2 1px;" >
			<table class="table table-striped" overflow-x：show>
		   		<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td>序号</td>
			  			<td>SKU条码</td>
			  			<td>条形码</td>
			  			<td>商品名称</td>
			  			<td>扩展属性</td>
			  			<td>商品数量</td>
			  		</tr>  	
		  		</thead>
		  		<tbody  align="center">
		  		<c:forEach items='${wraps}' var='wrap'  varStatus="status">
		  		<tr>
		  		   <td>${status.count} </td>
		  		   <td>${wrap.sku_number}</td>
		  		   <td>${wrap.barcode}</td>
		  		   <td>${wrap.item_name}</td>
		  		   <td>${wrap.extend_pro}</td>
		  		   <td>${wrap.qty}</td>
		  		</tr>
		  		</c:forEach>	
		  		</tbody>
			</table>	
	</div></div>
	</div>
	<div class="tab-pane fade" id="warning">
	      <button class="btn btn-xs btn-yellow"  data-toggle="modal" data-target="#yourModal">
				<i class="icon-hdd"></i>增加事件预警
			</button>
		<div style="height : 400px; overflow : auto; overflow : scroll; border : solid #F2F2F2 1px;"  > 
			<table class="table table-striped" overflow-x：show>
		   		<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td>序号</td>
			  			<td>预警时间</td>
			  			<td>预警类型</td>
			  			<td>预警状态</td>
			  			<td>生成工单</td>
			  			<td>工单状态</td>
			  			<td>明细</td>
			  			<td>取消</td>
			  		</tr>  	
		  		</thead>
		  		<tbody  align="center" id='warn_tbody'>
		  		<c:forEach items='${alarms}'  var='alarm'  varStatus='status'>
		  			<tr>
		  			   <td>${status.count}</td>
		  			   <td>${alarm.happen_time}</td>
		  			   <td>${alarm.warningtype_name}</td>
		  			   <td>
		  			       <c:if test="${alarm.del_flag==1}">已取消</c:if>
		  			       <c:if test="${alarm.del_flag==0}">预警中</c:if>
		  			   </td>
		  			   <td>
		  			       <c:if test="${alarm.wk_flag==1}">已生成工单</c:if>
		  			       <c:if test="${alarm.wk_flag==0}">未生成工单</c:if></td>
		  			   <td>
		  			    <c:if test="${alarm.wo_process_status=='NEW'}">新建</c:if>
		  			    <c:if test="${alarm.wo_process_status=='PRO'}">处理中</c:if>
		  			    <c:if test="${alarm.wo_process_status=='PAU'}">挂起</c:if>
		  			    <c:if test="${alarm.wo_process_status=='CCD'}">已取消</c:if>
		  			    <c:if test="${alarm.wo_process_status=='FIN'}">已处理</c:if>
		  			   </td>
		  			   <td><button class="btn btn-xs btn-pink"  onclick='toDetail("${alarm.id}");'>
				       <i class="icon-search icon-on-right bigger-110"></i>详细
			          </button></td>
		  			   <td>
		  			   <c:if test="${alarm.del_flag==0}">
		  			    <button class="btn btn-xs btn-inverse" onclick='del("${alarm.id}");'>
			    	       <i class="icon-trash"></i>取消预警
			           </button></c:if>
		  			   </td>
		  			</tr></c:forEach>
		  		</tbody>
			</table>	
	</div></div>
	</div>
</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style='width:800px;height:450px '>
		<div class="modal-content" style='border:3px solid #394557'>
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">预警信息明细</h4>
			</div>
			<div class="modal-body">
			<table class="table table-striped" overflow-x：show>
		   		<thead  align="center">
			  		<tr>
			  			<td>序号</td>
			  			<td>发生时间</td>
			  			<td>路由时间</td>
			  			<td>时效时间</td>
			  			<td>预警来源</td>
			  			<td>预警原因</td>
			  			<td>预警级别</td>
			  		</tr>  	
		  		</thead>
		  		<tbody id='tbody' align="center">
		  		
		  		</tbody>
			</table>	

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default"  data-dismiss="modal">返回
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>	

	<div class="modal fade" id="yourModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style='width:600px;height:450px '>
		<div class="modal-content" style='border:3px solid #394557'>
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel" style='color: #2679b5;'>新增事件预警</h4>
			</div>
			<div class="modal-body">
			  <form id='addEventAlarm'>
			  <table>
			   <tr>
			         <td align="left" width="10%"><label class="blue" >预警类型&nbsp;:</label></td>
					<td width="20%"><select id="warn_type" name="warningtype_code"  class='select'  style='width:170px;height:30px'>
						<option value= ''>---请选择---</option>
						<c:forEach items="${warn_type}" var = "warn" >
							<option level='${warn.initial_level}' value="${warn.warningtype_code}">${warn.warningtype_name}</option>
						</c:forEach>	
					</select>
					</td>	
		  		</tr>
		  		<tr>
		  		<td align="left" width="10%"><label class="blue" >备注&nbsp;:</label></td>
		  		<td><textarea name='remark' id='remark' style='width:170px'></textarea>
		  		<input type='text' style='display:none' name='waybill' vaule='${queryParam.waybill}'/>
		  		</td></tr> 		
		  		</table>
		  		</form>
			</div>
			<div class="modal-footer">
			   <button type="button" class="btn btn-default"  data-dismiss="modal"  onclick='addWarning()'>添加
				</button>
			
				<button type="button" class="btn btn-default"  data-dismiss="modal">返回
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>	
	</body>
</html>
<style>
body {
	font-family: "Microsoft Yahei","open sans","Helvetica Neue",Helvetica,Arial,sans-serif
}
.select {
    padding: 3px 4px;
    height: 30px;
    width: 160px;
   text-align: enter;
}


#myModal.modal-content { width:600px;height:450px } 
</style>