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
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>plugin/daterangepicker/daterangepicker-bs3.css" />--%>
		<%--<script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/daterangepicker.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>base/base.js" ></script>
		<script type="text/javascript" src="<%=basePath %>/js/jquery.shCircleLoader.js"></script> --%>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<%-- <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>plugin/My97DatePicker/WdatePicker.js" ></script> --%>
		<script type='text/javascript'>
		$(function(){
			//固定表头
			commonFixedTableTitle();
		});
		
		function refresh(){
			var tb = document.getElementById("test_orgnization");
			 tb.name="query_test";
			 tb.value="";
			 document.getElementById("userid").value="";
			jumpToPage(1);
		}
		
		
		function pageJump() {
			var param='';
			 param=$('#all_logistics').serialize();
			 $.ajax({
					type: "POST",
		          	url:'${root}control/orderPlatform/waybillMasterController/alllogistics_page.do?'+
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
		/* function addlogistics() {
			 $.ajax({
					type: "POST",
		          	url:'${root}control/orderPlatform/waybillMasterController/addlogistics.do',
		          	dataType: "text",
		          	data:'',
		   		cache:false,
		   		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		          	success: function (data){
		             $("#addlogistics").html(data);
		             $('#addlogistics').modal('show');
		          	}
			  	});  
		} */
		function toUpdate(uuid) {
			loadingStyle();
			loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/updatelogistics.do?id='+uuid+'&time='+new Date().getTime());
			cancelLoadingStyle();
		}
		
		/* function print() {
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
	        	            				//window.open('${root }/control/orderPlatform/waybillMasterController/print.do?ids='+idsStr,'_blank','height=680,width=400,status=yes,toolbar=yes,fullscreen=no, menubar=no,location=no');
	        	            		 }
	        	            	}else{
	        	            		//window.open('${root }/control/orderPlatform/waybillMasterController/print.do?ids='+idsStr,'_blank','height=680,width=400,status=yes,toolbar=yes,fullscreen=no, menubar=no,location=no');
	        	            	}
	        	            	
	        	            }else if(data.data==2){
	        	            	alert("操作失败！");
	        	            	alert(data.msg);
	        	             }else if(data.data==0){
	        	            	alert("尚未下单，无法打印！");
	        	             }else if(data.data==3){
	        	            	alert("该运单无需打印！");
	        	             }
				    //find();
					}
					); 
					}
		}else{
			alert("请选择一行!");
		} 
		}
		 */
		/* function querylogistics() {
			 $.ajax({
					type: "POST",
		          	url:'${root}control/orderPlatform/waybillMasterController/querylogistics.do',
		          	dataType: "text",
		          	data:'',
		   		cache:false,
		   		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		          	success: function (data){
		             $("#querylogistics").html(data);
		             $('#querylogistics').modal('show');
		          	}
			  	});  
		} */
		
		

		
		
		function fromme(){
			 var tb = document.getElementById("test_orgnization");
			 tb.name="from_orgnization";
			 tb.value="${org.org_name}";
				 jumpToPage(1);
		}
		
		function tome(){
			 var tb = document.getElementById("test_orgnization");
			 tb.name="to_orgnization";
			 tb.value="${org.org_name}";
			jumpToPage(1);
		}
		
		function querylogisticsByobj(){
			 var tb = document.getElementById("test_orgnization");
			 tb.name="query_test";
			 tb.value=document.getElementById("userid").value;
			jumpToPage(1);
		}

		
		 $('#userid').on('keypress',function(event){ 
	          
	         if(event.keyCode == 13){  
	        	 var tb = document.getElementById("test_orgnization");
				 tb.name="query_test";
				 tb.value=document.getElementById("userid").value;
				jumpToPage(1);
	         }  
	     });
		
	</script>
	</head>
	<body>
		<div class="page-header"><h1 style='margin-left:20px'>运单快速查询</h1></div>
		<div>
			<div style="margin-left: 20px;margin-bottom: 20px;">
			 <form id="all_logistics">
			 	<table id="lg_table">
			 		<tr>
			 			<td><input id='test_orgnization'  type='text' style='display:none'></td>
			 		</tr>
			 	</table>
			 </form>
					<table id="lg_table_t">
				  		<tr>
				  			<td>
				  				<input type="text" value="${queryParam.query_test}" class="text2" name = "query" id = "userid"/>
				  			</td>
				  			<td align="left">
				  				<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; "  onclick="querylogisticsByobj();">
									<i class="icon-search icon-on-right bigger-110"></i>查询
								</button>
				  			</td>
				  			<td align="left">
								<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; "  onclick="refresh();">
									<i class="icon-hdd"></i>清空
								</button>
				  			</td>
				  			<td align="left">
								<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; "  onclick="fromme();">
									<i class="icon-hdd"></i>我发的
								</button>
				  			</td>
				  			<td align="left">
								<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; "  onclick="tome();">
									<i class="icon-hdd"></i>我收的
								</button>
				  			</td>
				  			<!-- <td align="left">
								<button class="btn btn-xs btn-info" style= "height: 30px; width: 120px; "  onclick="print();">
									<i class="icon-hdd"></i>打印
								</button>
				  			</td> -->
				  		</tr>
					</table>
			</div>
			<div style="margin-left: 20px;margin-bottom: 20px;">
				<form method="post" action="">
				 </form>
			</div>
		</div>
		
		
		<div class= "tree_div"  id="page_view">
		<div style= "height: 440px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;"  id= "sc_content">
			<table id= "table_content" title="运单列表" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  			<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td class="td_cs" style="min-width: 29px;width:29px;"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td class="td_cs" style="width: 50px">序号</td>
			  			<td class="td_cs" style="width: 140px">制单日期</td>
						<td class="td_cs" style="width: 100px">状态</td>
						<!-- <td class="td_cs" style="width: 140px">运单号</td> -->
						<td class="td_cs" style="width: 140px">客户单号</td>
						<td class="td_cs" style="width: 180px">发货机构</td>
						<td class="td_cs" style="width: 100px">快递公司</td>
						<td class="td_cs" style="width: 120px">快递业务类型</td>
						<td class="td_cs" style="width: 140px">快递单号</td>
						<td class="td_cs" style="width: 100px">支付方式</td>
						<td class="td_cs" style="width: 200px">备注</td>
						<td class="td_cs" style="width: 180px">发货联系人</td>
						<td class="td_cs" style="width: 150px">发货联系电话</td>
						<td class="td_cs" style="width: 300px">发货地址</td>
						<td class="td_cs" style="width: 180px">收货机构</td>
						<td class="td_cs" style="width: 100px">省</td>
						<td class="td_cs" style="width: 100px">市</td>
						<td class="td_cs" style="width: 100px">区</td>
						<td class="td_cs" style="width: 180px">收货联系人</td>
						<td class="td_cs" style="width: 150px">收货联系电话</td>
						<td class="td_cs" style="width: 300px">收货地址</td>
						<td class="td_cs" style="width: 100px">总件数</td>
						<td class="td_cs" style="width: 100px">总毛重</td>
						<td class="td_cs" style="width: 100px">总体积</td>
						<td class="td_cs" style="width: 100px">总金额</td>
						<td class="td_cs" style="width: 140px">下单日期</td>
			  		</tr>  	
				</thead>
				<tbody  align="center">
			  		<c:forEach items= "${pageView.records }" var= "records" varStatus='status'>
				  		<tr ondblclick='toUpdate("${records.id}")'>
					  		<td class="td_cs" style="min-width: 29px;width:29px;"><input id="ckb" name="ckb" type="checkbox"
								value="${records.id}"></td>
							<td class="td_cs" style="width: 50px;height: 36px" >${status.count }</td>
							<td class="td_cs" style="width: 150px;height: 36px" >
							<fmt:formatDate value="${records.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="td_cs" style="width: 100px;height: 36px" >
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
							<%-- <td class="td_cs" style="width: 140px;height: 36px"
								>${records.order_id }</td> --%>
							<td class="td_cs" style="width: 140px;height: 36px"
								t>${records.customer_number }</td>
							<td class="td_cs" style="width: 180px;height: 36px"
								>${records.from_orgnization }</td>
							<td class="td_cs" style="width: 200px;height: 36px"
								>${records.express_name }</td>
							<td class="td_cs" style="width: 140px;height: 36px"
								>${records.producttype_name }</td>
							<td class="td_cs" style="width: 140px;height: 36px"
								>${records.waybill }</td>
							<td class="td_cs" style="width: 140px;height: 36px">
								<c:if test="${records.pay_path=='1'}">到付</c:if>
	   							<c:if test="${records.pay_path=='2'}">寄付</c:if>
	   							<c:if test="${records.pay_path=='4'}">寄付月结</c:if>
	   							<c:if test="${records.pay_path=='3'}">第三方付</c:if>
							</td>
							<td class="td_cs" style="width: 200px;height: 36px"
								>${records.memo }</td>
							<td class="td_cs" style="width: 180px;height: 36px"
								>${records.from_contacts }</td>
							<td class="td_cs" style="width: 140px;height: 36px"
								>${records.from_phone }</td>
							<td class="td_cs" style="width: 300px;height: 36px"
								>${records.from_address }</td>
							<td class="td_cs" style="width: 180px;height: 36px"
								>${records.to_orgnization }</td>
							<td class="td_cs" style="width: 100px;height: 36px"
								>${records.to_province }</td>
							<td class="td_cs" style="width: 100px;height: 36px"
								>${records.to_city }</td>
							<td class="td_cs" style="width: 100px;height: 36px"
								>${records.to_state }</td>
							<%-- <td class="td_cs" style="width: 200px;height: 36px"
								>${records.to_street }</td> --%>
							<td class="td_cs" style="width: 100px;height: 36px"
								>${records.to_contacts }</td>
							<td class="td_cs" style="width: 100px;height: 36px"
								>${records.to_phone }</td>
							<td class="td_cs" style="width: 300px;height: 36px"
								>${records.to_address }</td>
							<td class="td_cs" style="width: 100px;height: 36px"
								>${records.total_qty }</td>
							<td class="td_cs" style="width: 100px;height: 36px"
								>${records.total_weight }</td>
							<td class="td_cs" style="width: 100px;height: 36px"
								>${records.total_volumn }</td>
							<td class="td_cs" style="width: 100px;height: 36px"
								>${records.total_amount }</td>
							<td class="td_cs" style="width: 140px;height: 36px" title="${records.order_time }">
							<fmt:formatDate value="${records.order_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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
</style>
