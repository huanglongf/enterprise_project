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
		<script type="text/javascript" src="<%=basePath %>/js/jquery.shCircleLoader.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/selectFilter.js"></script>
		<script type="text/javascript" src="<%=basePath %>js/common.js"></script> --%>
		<script type="text/javascript" src="<%=basePath %>/js/selectFilter.js"></script>
		<%-- <script type= "text/javascript" src= "<%=basePath %>/plugin/My97DatePicker/WdatePicker.js" ></script> --%>
		<script type='text/javascript'>
		
		
		</script>
</head>
<body>
		<div class= "tree_div"  id="page_view">
		<div style= "height: 400px; overflow: auto; overflow: scroll; border: solid #F2F2F2 1px;"  id= "sc_content">
			<table id= "table_content" title="运单列表" class= "table table-hover table-striped" style= "table-layout: fixed;" >
		  			<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td><input type="checkbox" id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  			<td class="td_cs" style="width: 100px">序号</td>
						<td class="td_cs" style="width: 100px">运单状态</td>
						<td class="td_cs" style="width: 200px">运单号</td>
						<td class="td_cs" style="width: 200px">发货机构</td>
						<td class="td_cs" style="width: 200px">快递公司</td>
						<td class="td_cs" style="width: 200px">快递业务类型</td>
						<td class="td_cs" style="width: 200px">快递单号</td>
						<td class="td_cs" style="width: 200px">支付方式</td>
						<td class="td_cs" style="width: 200px">备注</td>
						<td class="td_cs" style="width: 200px">发货联系人</td>
						<td class="td_cs" style="width: 200px">发货联系电话</td>
						<td class="td_cs" style="width: 200px">发货地址</td>
						<td class="td_cs" style="width: 200px">收货机构</td>
						<td class="td_cs" style="width: 200px">省</td>
						<td class="td_cs" style="width: 200px">市</td>
						<td class="td_cs" style="width: 200px">区</td>
						<td class="td_cs" style="width: 200px">街道</td>
						<td class="td_cs" style="width: 200px">收货联系人</td>
						<td class="td_cs" style="width: 200px">收货联系电话</td>
						<td class="td_cs" style="width: 200px">收货地址</td>
						<td class="td_cs" style="width: 200px">总件数</td>
						<td class="td_cs" style="width: 200px">总毛重</td>
						<td class="td_cs" style="width: 200px">总体积</td>
						<td class="td_cs" style="width: 200px">总金额</td>
						<td class="td_cs" style="width: 200px">下单日期</td>
			  		</tr>  	
				</thead>
				<tbody  align="center">
			  		<c:forEach items= "${pageView.records }" var= "records" varStatus='status'>
				  		<tr ondblclick='toUpdate("${records.id}")'>
					  		<td><input id="ckb" name="ckb" type="checkbox" value="${records.id}"></td>
							<td class="td_cs" style="width: 100px" title="${status.count }">${status.count }</td>
							<td class="td_cs" style="width: 100px" title="${records.status }">
									<c:if test="${records.status=='1'}">已录单</c:if>
		   							<c:if test="${records.status=='0'}">已作废</c:if>
		   							<c:if test="${records.status=='2'}">已下单</c:if>
							</td>
							<td class="td_cs" style="width: 200px"
								title="${records.order_id }">${records.order_id }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.from_orgnization }">${records.from_orgnization }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.expressCode }">${records.expressCode }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.producttype_name }">${records.producttype_name }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.waybill }">${records.waybill }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.payment }">${records.payment }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.memo }">${records.memo }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.from_contacts }">${records.from_contacts }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.from_phone }">${records.from_phone }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.from_address }">${records.from_address }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_orgnization }">${records.to_orgnization }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_province }">${records.to_province }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_city }">${records.to_city }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_state }">${records.to_state }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_street }">${records.to_street }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_contacts }">${records.to_contacts }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_phone }">${records.to_phone }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.to_address }">${records.to_address }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.total_qty }">${records.total_qty }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.total_weight }">${records.total_weight }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.total_volumn }">${records.total_volumn }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.total_amount }">${records.total_amount }</td>
							<td class="td_cs" style="width: 200px"
								title="${records.order_time }">${records.order_time }</td>
						</tr>
		  			</c:forEach>
		  		</tbody>
			</table>
		</div>
		<div style= "margin-right: 1%; margin-top: 20px;" >${pageView.pageView }</div>
		</div>

</body>
</html>