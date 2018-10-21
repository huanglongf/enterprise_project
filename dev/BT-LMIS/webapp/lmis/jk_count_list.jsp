<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<script type="text/javascript" src="<%=basePath %>validator/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common_view.js"></script>
		<link rel="stylesheet" type="text/css" media="all" href="<%=basePath %>css/table.css" />
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>My97DatePicker/WdatePicker.js" ></script>
		
	</head>
	<body>
		<div class="page-header">
			<table>
			  		<tr>
			  			<td align= "right" width= "10%"><label class= "blue" for= "client_code">异常数据&nbsp;:</label></td>
			  			<td width= "20%">
			  			   <select id="isError" data-edit-select="1">
			  			      <option value="">全部</option>
			  			      <option value="1">是</option>
			  			      <option value="0">否</option>
			  			   </select>
			  			</td>
			  			<td align= "right" width= "10%"><label class= "blue" for= "client_name">接口名称&nbsp;:</label></td>
			  			<td width= "20%">
			  			   <select id="jkCode" data-edit-select="1">
			  			      <option value="">全部</option>
			  			      <option value="getOperator">操作费</option>
			  			      <option value="getOrderData">快递运费</option>
			  			      <option value="getUseMaterial">耗材使用量</option>
			  			      <option value="getInvitation">耗材采购量</option>
			  			   </select>
			  			</td>
			  		</tr>
				</table>
			<div style="margin-top: 10px;margin-left: 10px;">
				<button class="btn btn-xs btn-pink" onclick="pageJump();">
					<i class="icon-search icon-on-right bigger-110"></i>查询
				</button>
				
				<button id="wmsButton" class="btn btn-xs btn-pink" onclick="wmsSearch();">
					<i class="icon-search icon-on-right bigger-110"></i>Wms数据更新
				</button>
				
				<button id="lmisButton" class="btn btn-xs btn-pink" onclick="lmisSearch();">
					<i class="icon-search icon-on-right bigger-110"></i>Lmis数据更新
				</button>				
			</div>
			
		</div>

		
		
<div  class="title_div" id="sc_title">		
<table class="table table-striped" style="table-layout: fixed;">
		   		<thead>
			  		<tr>
			  			<td class="td_ch"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/></td>
			  			<td class="td_cs">wms数据总数</td>
			  			<td class="td_cs">lmis数据总数</td>
			  			<td class="td_cs">接口名称</td>
			  			<td class="td_cs">是否重新获取</td>
			  			<td class="td_cs">数据日期</td>
			  			<td class="td_cs">lmis汇总时间</td>
			  			<td class="td_cs">wms汇总时间</td>
			  			<td class="td_cs">操作</td>
			  		</tr>  	
		  		</thead>

</table>
</div>
<div class="tree_div"></div>

<div style="height:400px;overflow:auto;overflow:scroll; border:solid #F2F2F2 1px;" id="sc_content" onscroll="init_td('sc_title','sc_content')">
<table class="table table-striped" style="table-layout: fixed;">
		  		<tbody  align="center">
		  		<c:forEach items="${pageView.records}" var="res">
			  		<tr>
			  			<td class="td_ch"><input id="ckb" name="ckb" type="checkbox" value="${res.count_time_param}"></td>
			  			<td class="td_cs">${res.wms_count}</td>
			  			<td class="td_cs">${res.lmis_count}</td>
			  			<td class="td_cs">${res.jk_name}</td>
			  			<td class="td_cs">${res.newly_link_flag}</td>
			  			<td class="td_cs">${res.count_time}</td>
			  			<td class="td_cs">${res.lmis_update_time}</td>
			  			<td class="td_cs">${res.wms_update_time}</td>
			  			<td class="td_cs">
			  			<c:if test="${res.newly_link_flag=='0'}">
			  			   <button  id="refreshGet_${res.count_time_param}" class="btn btn-xs btn-info" onclick="upStatus('${res.count_time_param}','1');">重新获取</button>
			  			</c:if>
			  			<c:if test="${res.newly_link_flag=='1'}">
							<button   id="refreshGet"  class="btn btn-xs btn-danger" onclick="upStatus('${res.count_time_param}','0');">
							<i class="icon-arrow-left icon-on-left"></i>
								 撤销获取
							</button>
			  			</c:if>
			  			<c:if test="${res.newly_link_flag=='2'}">
							<button disabled="disabled"  class="btn btn-xs btn-danger">
							<i class="icon-arrow-left icon-on-left"></i>
								 获取中..
							</button>
			  			</c:if>			  			
			  			</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
</table>
</div>
		<!-- 分页添加 -->
      	<div style="margin-right: 10%;margin-top:20px;margin-bottom: 10%;">${pageView.pageView}</div>		
	</body>



<!-- 异常类型编辑页面弹窗 -->
		<div id= "time_form" class= "modal fade" tabindex= "-1" role= "dialog" aria-labelledby= "formLabel" aria-hidden= "true" >
			<div class= "modal-dialog modal-lg" role= "document" >
				<div class= "modal-content" style= "border: 3px solid #394557;" >
					<div class= "modal-header" >
						<button type= "button" class= "close" data-dismiss= "modal" aria-label= "Close" ><span aria-hidden= "true" >×</span></button>
						<h4 id= "formLabel" class= "modal-title" >选择起始时间</h4>
					</div>
					<div class= "modal-body" >
						<div class= "form-group" >
				    		<label class= "blue" for= "reason_form" >起始时间&nbsp;:</label>
			                <input id= "start_time" name= "start_time" type= "text" style= "width: 50%; height: 34px;" class= "Wdate" placeholder= "" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH'})" />
				    	</div>
						
						
						<div class= "form-group">
				    		<label class= "blue" for= "reason_form" >结束时间&nbsp;:</label>
			                <input id= "end_time" name= "end_time" type= "text" style= "width: 50%; height: 34px;" class= "Wdate" placeholder= "" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH'})" />
				    	</div>
					</div>
					<div class= "modal-footer" >
						<button id= "btn_back2" type= "button" class= "btn btn-default" data-dismiss="modal" >
							<i class= "icon-undo" aria-hidden= "true" ></i>返回
						</button>
		     			<button id= "btn_submit" type= "button" class= "btn btn-primary" onclick="getData();">
		     				<i class="icon-save" aria-hidden= "true" ></i></i><span id="btn_error_text">确定</span>
		     			</button>
					</div>
				</div>
			</div>
</div>		
			
	<script type="text/javascript">

		function wmsSearch(){
			var param="";
			var startDate="";
			var flag=0;
			var url=root+"/control/JkErrorController/wmsSearch.do";
            if($("input[name='ckb']:checked").length==0){
            	$("#time_form").modal('show');
            	return;
            }
            if($("input[name='ckb']:checked").length>1){
              alert("请选择单条数据调用");
              return;
            }
            if($("input[name='ckb']:checked").length==1){
            	startDate=$("input[name='ckb']:checked").val();
              }
            param="startDate="+startDate+"&flag="+flag;	
            $("#wmsButton").attr("disabled","disabled");
		    $.ajax({
				type : "POST",
				url: url,  
				data:param,
				dataType: "json",  
				success : function(jsonStr) {
					alert(jsonStr.out_result_reason);
					$("#wmsButton").removeAttr("disabled");
					pageJump();
				}
			});
		}

		function lmisSearch(){
			$("#lmisButton").attr("disabled","disabled");
			var url=root+"/control/JkErrorController/updateJkData.do";
		    $.ajax({
				type:"POST",
				url: url,  
				data:'',
				dataType: "json",  
				success : function(jsonStr) {
				 alert(jsonStr.out_result_reason);
				 $("#lmisButton").removeAttr("disabled");
				 pageJump();
				}
			});
		}

		function upStatus(count_time_param,status){
			$("#refreshGet_"+count_time_param).attr("disabled","disabled");
			var url=root+"/control/JkErrorController/getNew.do";
		    $.ajax({
				type:"POST",
				url: url,  
				data:'countTimeParam='+count_time_param+"&status="+status,
				dataType: "json",  
				success : function(jsonStr) {
				 alert(jsonStr.out_result_reason);
				 $("#refreshGet_"+count_time_param).removeAttr("disabled");
				 pageJump();
				}
			});
		}		

		function getData(){
          var startData=$("#start_time").val();
          var endData=$("#end_time").val();
          $("#wmsButton").attr("disabled","disabled");
          var url=root+"/control/JkErrorController/wmsSearch.do";
		    $.ajax({
				type:"POST",
				url: url,  
				data:'startData='+startData+"&endData="+endData+"&flag=2",
				dataType: "json",  
				success : function(jsonStr) {
				 alert(jsonStr.out_result_reason);
				 $("#wmsButton").removeAttr("disabled");
				 $("#btn_back2").click();
				 pageJump();
				}
			});
		}

		 function pageJump() {
			 var data="isError="+$("#isError").val()+"&jkCode="+$("#jkCode").val();
			 openDiv('/BT-LMIS/control/JkErrorController/JKlist.do?'+data+'&startRow='+$("#startRow").val()+'&endRow='+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val());
	 }		 
	</script>
</html>
