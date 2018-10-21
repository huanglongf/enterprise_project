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
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>plugin/daterangepicker/daterangepicker-bs3.css" />
		<script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/moment.js"></script>
		<script type="text/javascript" src="<%=basePath %>plugin/daterangepicker/daterangepicker.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>base/base.js" ></script>
		<script type="text/javascript" src="<%=basePath %>/js/jquery.shCircleLoader.js"></script> --%>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<%-- <script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>/plugin/My97DatePicker/WdatePicker.js" ></script> --%>
		<script type='text/javascript'>

		$(function(){
			//固定表头
			commonFixedTableTitle();
		});
		
		
		function pageJump() {
			 $.ajax({
					type: "POST",
		          	url:'${root}/control/orderPlatform/expressinfoMasterInputlistController/page.do?'+
		          			'startRow='+
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
		

		function deleteWaybilMasterDetail(){
			 if($("input[name='ckb']:checked").length==1){
				 var sum = $("input[name='ckb']:checked").length;
			  		 var result=  	confirm('是否确认进行删除！'); 
						if(result){
							var ids=$("input[name='ckb']:checked");
							var idsStr='';
							$.each(ids,function(index,item){
								idsStr=idsStr+this.value+';';
							});
			  	 	$.post('${root}/control/orderPlatform/expressinfoMasterInputlistController/deleteWaybilMasterDetail.do?ids='+idsStr,function(data){
			        	            if(data.data==1){
			        	            	alert("操作成功！");
			        	            	loadingCenterPanel("${root }/control/orderPlatform/expressinfoMasterInputlistController/uploadresult.do");
			        	            }else if(data.data==2){
			        	            	alert("操作失败！");
			        	            	alert(data.msg);
			        	             }
						    //find();
			                        }
			  		      ); 
			  		
						}
			  	
			}else{
				alert("请勾选一行!");
			} 
		}
		function placeWaybilMaster(){
			 if($("input[name='ckb']:checked").length==1){
				 var sum = $("input[name='ckb']:checked").length;
			  		 var result=  	confirm('是否确认进行下单！'); 
						if(result){
							var ids=$("input[name='ckb']:checked");
							var idsStr='';
							$.each(ids,function(index,item){
								idsStr=idsStr+this.value+';';
							});
			  	 	$.post('${root}/control/orderPlatform/expressinfoMasterInputlistController/placeWaybilMaster.do?ids='+idsStr,function(data){
			        	            if(data.data==1){
			        	            	$.post('${root}/control/orderPlatform/waybillMasterController/placeBaozunWaybilMaster.do?ids='+idsStr,function(data){
			        	            		 if(data.data==1){
			        	            			 alert("操作成功！");
			        	            		 }else if(data.data==0){
			        	            			// window.open('${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='+idsStr);
			        	            			 $.post('${root}/control/orderPlatform/SFPrintController/print_rebook.do?ids='+idsStr,
			        	     	            			function(data){
			        	     	            				 if(data.out_result==1){
			        	     	            					 window.open(root + data.path);
			        	     	            				 }else if(data.out_result==2){
			        	     	            					 alert("操作失败!");
			        	     	            				 }
			        	     	            			});
			        	            		 }
			        	            	 }); 
			        	            }else if(data.data==2){
			        	            	alert("数据有误，无法操作！");
			        	            }else if(data.data==3){
			        	            	//window.open('${root}/control/orderPlatform/SFPrintController/printBaozunWaybilMaster.do?ids='+idsStr);
			        	            	$.post('${root}/control/orderPlatform/SFPrintController/print_rebook.do?ids='+idsStr,
	        	     	            			function(data){
	        	     	            				 if(data.out_result==1){
	        	     	            					 window.open(root + data.path);
	        	     	            				 }else if(data.out_result==2){
	        	     	            					 alert("操作失败!");
	        	     	            				 }
	        	     	            			});
				        	        }
			                        }); 
			  		
						}
			  	
			}else{
				alert("请勾选一行!");
			} 
		}
		
		
		
		function selectResult(){
			 if($("input[name='ckb']:checked").length==1){
				 var sum = $("input[name='ckb']:checked").length;
			  		 var result=  	confirm('是否查看下单结果！'); 
						if(result){
							var ids=$("input[name='ckb']:checked");
							var idsStr='';
							$.each(ids,function(index,item){
								idsStr=idsStr+this.value+';';
							});
			  	 	$.post('${root}/control/orderPlatform/expressinfoMasterInputlistController/selectResult.do?ids='+idsStr,function(data){
			        	            if(data.data==1){
			        	            	loadingCenterPanel('${root }/control/orderPlatform/waybillMasterController/baozunuploade.do?bat_id='+idsStr);
			        	            }else if(data.data==2){
			        	            	alert("数据有误，无法操作！");
			        	            }else if(data.data==3){
			        	            	alert("尚未下单，无法操作！");
				        	        }
			                        }); 
			  		
						}
			  	
			}else{
				alert("请勾选一行!");
			} 
		}
		
		
		function transformationWaybilMasterDetail(){
			 if($("input[name='ckb']:checked").length==1){
				 var sum = $("input[name='ckb']:checked").length;
			  		 var result=  	confirm('是否确认进行转换！'); 
						if(result){
							var ids=$("input[name='ckb']:checked");
							var idsStr='';
							$.each(ids,function(index,item){
								idsStr=idsStr+this.value+';';
							});
			  	 	$.post('${root}/control/orderPlatform/expressinfoMasterInputlistController/transformationWaybilMasterDetail.do?ids='+idsStr,function(data){
			        	            if(data.data==1){
			        	            	alert("操作成功！");
			        	            	loadingCenterPanel("${root }/control/orderPlatform/expressinfoMasterInputlistController/uploadresult.do");
			        	            }else if(data.data==2){
			        	            	alert("操作失败！");
			        	            	alert(data.msg);
			        	             }else if(data.data==0){
			        	            	alert("状态必须为未转换才能进行转换！");
			        	             }else if(data.data==4){
			        	            	alert("客户单号已存在不能进行转换！");
			        	             }else if(data.data==3){
			        	            	alert("该批次号已转不能进行转换！");
			        	             }
						    //find();
			                        }
			  		      ); 
			  		
						}
			  	
			}else{
				alert("请勾选一行!");
			} 
		}
		
		
		
		function toUpdate(uuid) {
			loadingCenterPanel('${root }/control/orderPlatform/waybilMasterDetailController/updatewaybilMasterDetail.do?bat_id='+uuid);
		}
		
		

	</script>
	</head>
	<body>
			<div class="page-header"><h1 style='margin-left:20px'>运单结果</h1></div>	
			<table id="lg_table">
			  		<tr>
			  			<!-- <td  align="left">
							<button class="btn btn-xs btn-success " style= "height: 30px; width: 120px; " onclick="transformationWaybilMasterDetail();">
								<i class="icon-hdd"></i>转换
							</button>
			  			</td> -->
			  			<td  align="left">
							<button class="btn btn-xs btn-success " style= "height: 30px; width: 120px; " onclick="placeWaybilMaster();">
								<i class="icon-hdd"></i>下单打印
							</button>
			  			</td>
			  			<td  align="left">
							<button class="btn btn-xs btn-success " style= "height: 30px; width: 120px; " onclick="selectResult();">
								<i class="icon-hdd"></i>下单结果查询
							</button>
			  			</td>
			  			<td  align="left">
							<button class="btn btn-xs btn-success " style= "height: 30px; width: 120px; " onclick="deleteWaybilMasterDetail();">
								<i class="icon-hdd"></i>删除
							</button>
			  			</td>
			  		</tr>
				</table>
			<div style="margin-left: 20px;margin-bottom: 20px;">
				<form method="post" action="">
				 </form>
			</div>
		
		<div class= "tree_div"  id="page_view">
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;"  id= "sc_content">
			<table id= "table_content" title="运单列表" class= "table table-hover table-striped" style= "table-layout: fixed;" >
  			<thead align="center">
				<tr class='table_head_line'>
			  			<td style="width: 13px"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')" /> </td>
					<td class="td_cs" style="min-width: 29px;width:29px;">序号</td>
						<td class="td_cs" style="width: 200px">导入批次号</td>
						<td class="td_cs" style="width: 100px">转换成功条数</td>
						<td class="td_cs" style="width: 100px">导入总条数</td> 
						<td class="td_cs" style="width: 100px">导入日期</td>
						<td class="td_cs" style="width: 100px">当前转换状态</td>
				</tr>
			</thead>
			<tbody id='tbody' align="center">
				<c:forEach items="${pageView.records}" var="records"
					varStatus='status'>
					<tr ondblclick='toUpdate("${records.bat_id}")'>
						<td class="td_cs" style="min-width: 29px;width:29px;" ><input id="ckb" name="ckb" type="checkbox"
							value="${records.bat_id}"></td>
						<td class="td_cs" style="width: 50px" title="${status.count }">${status.count }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.bat_id }">${records.bat_id }</td>
							<td class="td_cs" style="width: 100px"
								title="${records.success_num }">${records.success_num }</td>
							<td class="td_cs" style="width: 100px"
								title="${records.total_num }">${records.total_num }</td>
							<td class="td_cs" style="width: 100px" title="${records.create_time }">
							<fmt:formatDate value="${records.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td class="td_cs" style="width: 100px"title="${records.total_num }">
									<c:if test="${records.flag=='2'}">数据有误，未转换</c:if>
		   							<c:if test="${records.flag=='0'}">未转换</c:if>
		   							<c:if test="${records.flag=='4'}">已转换</c:if>
		   							<c:if test="${records.flag=='3'}">正在转</c:if>
		   							<c:if test="${records.flag=='5'}">下单完毕</c:if>
							</td>
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
