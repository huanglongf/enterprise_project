<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%@ include file="/lmis/yuriy.jsp" %>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script>
		<link href="<%=basePath %>/css/table.css" type="text/css" rel="stylesheet" />
	<body>
		<div class= "title_div" id= "sc_title">
			<table class= "table table-striped" style= "table-layout: fixed;overflow-x:show;" >
		   		<thead>
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
			  			<td >实际时效</td>
			  			<td class='td_cs'>操作</td>-->
			  		</tr>  	
				</thead>
			</table>
		</div>
		<div class= "tree_div" ></div>
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;" id= "sc_content" onscroll= "init_td('sc_title', 'sc_content')" >
			<table id= "table_content" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  		<tbody align= "center">
			  		<c:forEach items="${pageView.records}" var="power" varStatus='status'>
			  			<tr  ondblclick='toDetail("${power.waybill}");'>
			  			<td class= "td_cs" nowrap="nowrap">${status.count }</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.waybill}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.express_name} </td>
			  			<td class= "td_cs" nowrap="nowrap">${power.producttype_name}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.work_no}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.platform_no}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.store_name}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.warehouse_name}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.systemwh_name}</td>
			  			<td  class= "td_cs" nowrap="nowrap">${power.weight_time}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.provice_name}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.city_name}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.state_name}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.street_name}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.standard_receive_time}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.receive_time}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.embrance_time}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.delivery_failure_time}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.delivery_failure_reason}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.new_waybill}</td>
			  			<td class= "td_cs" nowrap="nowrap">${power.run_time}</td>
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
      	 
	</body>
</html>
<script>

$("#pageCountNo").val('${pageView.totalrecord}');
</script>
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

