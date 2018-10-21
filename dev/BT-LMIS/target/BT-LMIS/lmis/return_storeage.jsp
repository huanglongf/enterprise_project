<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<title>LMIS</title>
		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link type="text/css" href="<%=basePath %>css/table.css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>shCircleLoader/css/jquery.shCircleLoader.css" rel="stylesheet" media="all" />	
		<link type= "text/css" href= "<%=basePath %>daterangepicker/font-awesome.min.css" rel= "stylesheet" />
		<link type= "text/css" href= "<%=basePath %>daterangepicker/daterangepicker-bs3.css" rel="stylesheet" />
		<link type= "text/css" href= "<%=basePath %>css/loadingStyle.css" rel= "stylesheet" media="all" />
		<script type="text/javascript" src="<%=basePath %>My97DatePicker/WdatePicker.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/common_view.js" ></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<script type= "text/javascript" src= "<%=basePath %>js/selectFilter.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>js/bootstrap.min.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader.js" ></script>
		<script type= "text/javascript" src= "<%=basePath %>shCircleLoader/js/jquery.shCircleLoader-min.js" ></script>
        <style type="text/css">
         label{
          margin-right: 15px;
          font-size: 14px;
         }
        </style>
	</head>
<body>
		<div class="page-header" style="margin-left : 20px;">
		<form id= "main_search">
			<table >
		  		<tr>
		  			<td width="10%" align="right"><label>系统仓:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="warehouse_code" name="warehouse_code"  class='select' data-edit-select="1" >
						<option value=''>---请选择---</option>
						<c:forEach items="${warehouses}" var = "warehouse" >
							<option  value="${warehouse.warehouse_code}">${warehouse.warehouse_name}</option>
						</c:forEach>	
					</select>
		  			</td>
		  			<td width="10%" align="right"><label>店铺:</label></td>
		  			<td width="20%" align="left">
		  			   <select id="store_code" name="store_code"  class='select' data-edit-select="1" >
						<option value=''>---请选择---</option>
						<c:forEach items="${stores}" var = "store" >
							<option  value="${store.store_code}">${store.store_name}</option>
						</c:forEach>	
					</select>
		  			</td>
		  			<td width="10%" align="right"><label>出库前置单据号:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="re_epistatic_order" name="re_epistatic_order"  style='width:100%;height:34px' >
					</input>
		  			</td>
		  		</tr>
		  		<tr align="center">
		  			<td width="10%" align="right"><label>快递公司:</label></td>
		  			<td width="20%" align="left">
		  			  <select id="transport_code" name="transport_code"  class='select'  data-edit-select="1" >
						<option value=''>---请选择---</option>
						<c:forEach items="${transportVenders}" var = "transportVender" >
							<option  value="${transportVender.transport_code}">${transportVender.transport_name}</option>
						</c:forEach>
					</select>
		  			</td>
		  		<td width="10%" align="right" ><label>产品类型:</label></td>
		  			<td width="20%" align="left">
		  			  <select id="itemtype_code"  name="itemtype_code" style="width: 60%;" class='select' data-edit-select="1">
		  			     <option value="">---请先选择快递公司---</option>
		  			  </select>
		  		</td>
		  		<td width="10%" align="right" ><label>快递单号:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="waybill"  name="waybill"  style='width:100%;height:34px'>
		  			  </input>
		  		</td>
		  		</tr>
		  		<tr align="center">
		  			<td width="10%" align="right"><label>退货相关单据号:</label></td>
		  			<td width="20%" align="left">
		  			  <input id="related_no" name="related_no"  style='width:100%;height:34px'>
					  </input>
		  			</td>
		  		<td width="10%" align="right" ><label>完成时间(开始):</label></td>
		  			<td width="20%" align="left">
		  			 <div style="width:100%;" >
		                 <input id= "start_time" name= "start_finish_time" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>		
		  		</td>
		  		<td width="10%" align="right" ><label>完成时间(结束):</label></td>
		  			<td width="20%" align="left">
		  			 <div style="width:100%;" >
		                 <input id= "end_time" name= "end_finish_time" type="text" id="d412" style= "width:100%; height: 34px;" class= "Wdate" placeholder= "" onfocus= "WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
			         </div>		
		  		</td>
		  		</tr>
			</table>
		</form>
		</div>
		<div class="div_margin" style="margin-left : 20px;">
			<button class="btn btn-xs btn-pink" onclick="returnStorageQuery();">
				<i class="icon-search icon-on-right bigger-110"></i>查询
			</button>
			<button class= "btn btn-xs btn-primary" onclick= "download();" >
				<i class= "icon-hdd" ></i>导出
			</button>
		</div>
<div id="page_view">
		<div class= "title_div" id= "sc_title">
			<table class= "table table-striped" style= "table-layout: fixed;overflow-x:show;" >
		   		<thead>
			  		<tr class= "table_head_line"  >
			  				<td class="td_ch"><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/></td>
                        <td class="td_cs">序号</td> 
			  			<td class="td_cs">物流商</td>
			  			<td class="td_cs">产品类型</td>
			  			<td class="td_cs">系统仓</td>
			  			<td class="td_cs">店铺名称</td>
			  			<td class="td_cs">运单号</td>
			  			<td class="td_cs">退货相关单号</td>
			  			<td class="td_cs">作业单创建时间</td>
			  			<td class="td_cs">作业单完成时间</td>
			  			<td class="td_cs">计划执行量</td>
			  			<td class="td_cs">实际执行量</td>
			  			<td class="td_cs">登记物理仓名称</td>
			  			<td class="td_cs">创建人</td>
			  			<td class="td_cs">解锁人</td>
			  			<td class="td_cs">备注</td>
			  			<td class="td_cs">出库前置单据号</td>
			  			<td class="td_cs">出库目的省</td>
			  			<td class="td_cs">出库目的市</td>
			  			<td class="td_cs">出库目的区县</td>
			  			<td class="td_cs">出库重量</td>
			  			<td class="td_cs">出库长</td>
			  			<td class="td_cs">出库宽</td>
			  			<td class="td_cs">出库高</td>
			  			<td class="td_cs">出库体积</td>
			  			<td class="td_cs">出库订单金额</td>
			  		</tr>  	
				</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  		<tbody align= "center">
			  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  		<tr >
			  		<td><input id="ckb" name="ckb" type="checkbox" value="${power.bat_id}"></td>
			  			<td class= "td_cs" nowrap="nowrap">${status.count }</td>
			  			<td class= "td_cs" nowrap="nowrap" title='${power.bat_id}'>${power.bat_id}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.total_num} </td>
			  			<td class= "td_cs" nowrap="nowrap">${power.fail_num}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.success_num}</td>
			  			
			  			<td class= "td_cs" nowrap="nowrap">
			  			<c:if test="${empty power.wrong_path}">---</c:if>
			  			<c:if test="${not empty power.wrong_path}"><a href='/BT-LMIS/DownloadFile/${power.wrong_path}'>点击下载原因</a></c:if>
			  			</td>
			  			<td class= "td_cs" nowrap="nowrap"><fmt:formatDate value="${power.create_time}"  type="both" pattern=" yyyy-MM-dd HH:mm:ss"/> </td>
			  			<td class= "td_cs" nowrap="nowrap">${power.username}</td>
			  			<td class= "td_cs">
			  			<c:if test='${power.flag==0}'>已上传</c:if>
			  			<c:if test='${power.flag==1}'>已转换</c:if>
			  			</td>
			  		</tr>
		  		</c:forEach>
		  		</tbody>
			</table>
		</div>
		<!-- 分页添加 -->
     <div id='botton_page' style="margin-right: 30px;margin-top:20px;">
<div style="margin-right: 30px;margin-top:20px;">${pageView.pageView}</div>	
      	 </div>
      	 </div> 		
	</body>
	<script type="text/javascript">
	  
		$(document).ready(function() {
			if('${queryParam.transport_code}'==''){
				$("#producttype_code").next().attr("disabled", "disabled");
		 }
			 $("#transport_code").bind("change",function(){
					ExpressCodeChange("transport_code","itemtype_code","");
				 
			 });
			
		});
		
		
		function ExpressCodeChange(upper, lower,third){
			upper = "#" + upper;
			lower = "#" +lower;
			var transport_code = $(upper).val();
			if(transport_code == ""){
				$(lower).children(":first").siblings().remove();
				$(lower).val("");
				$(lower).next().val("");
				$(lower).next().attr("placeholder", "---请选择---");
				$(lower).next().attr("disabled", "disabled");
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
   						var content1 = '';
						var content2 = '';
						for(var i =0; i < result.products.length; i++){
							content1 += 
								'<option value="' 
								+ result.products[i].product_type_code 
								+ '">'
								+result.products[i].product_type_name 
								+'</option>'
							content2 += 
								'<li class="m-list-item" data-value="'
								+ result.products[i].product_type_code
								+ '">'
								+ result.products[i].product_type_name
								+ '</li>'
						}
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
		
		function  returnStorageQuery(){
			$(window).scrollTop(0);
			pageJump();
			
		}
		
		function pageJump() {
			 var param='';
			loadingStyle();
			 param=$('#main_search').serialize();
		 	$.ajax({
					type: "POST",
		           	url:'${root}/control/expressReturnStorageController/query.do?'+param,
		           	dataType: "text",
		           	data:'',
		    		cache:false,
		    		contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		           	success: function (data){
		              $("#page_view").html(data);
		              cancelLoadingStyle();
		           	}
			  	});  
			  	
			  	//$.post('${root}/control/expressReturnStorageController/query.do?storeCode=tst&warehouseCode=erreer',function(){});
		    }
		
		function download(){
			$(window).scrollTop(0);
			loadingStyle();
			param=$('#main_search').serialize();
			$.ajax({
				type: "POST",
	           	url:'${root}/control/expressReturnStorageController/download.do?'+param,
	           	dataType: "json",
	           	data:'',
	           	success: function (data){
	           	 cancelLoadingStyle();
				 window.open('${root}/DownloadFile/'+data.data);
	           //   $("#load_load").css("display","none");
	           	}
		  	});  
			
			
		}
		
	</script>	
</html>
<style>

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
.modal-header{
height:50px;

}
</style>
