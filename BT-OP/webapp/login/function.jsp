<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head lang="en">
		<meta charset="UTF-8">
		<%-- <%@ include file="/base/yuriy.jsp" %> --%>
		 <%@ include file="/templet/common.jsp" %> 
		<title>OP</title>
		<script type='text/javascript'>
		
		
	</script>
	</head>
	<body>
<div class="page-header"><h1 style='margin-left:20px'>功能菜单</h1></div>	

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
									<label class="blue" for="waybill">名称</label>
								</div>
								<div class="col-md-3 no-padding">
									<div class="col-md-11 no-padding">
										<input type='text' id='customer_number' value="${queryParam.customer_number}" name='customer_number' style="width: 160px;height: 34px;">
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
								<i class="icon-hdd"></i>新增
							</button>
							<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; " onclick="confirmOrders();">
								<i class="icon-hdd"></i>修改
							</button>
							<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; " onclick="cancelOrderLogistics();">
								<i class="icon-hdd"></i>删除
							</button>
							<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; " onclick="cancelOrder();">
								<i class="icon-hdd"></i>密码设置
							</button>
							<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; " onclick="loadingCenterPanel('${root}/control/orderPlatform/waybillMasterController/waybiluploade.do');">
								<i class="icon-hdd"></i>角色设置
							</button>
							<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; "  onclick="print();">
								<i class="icon-hdd"></i>数据权限设置
							</button>
							<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; "  onclick="batchprintPdf();">
								<i class="icon-hdd"></i>启用
							</button>
							<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width" style= "height: 30px; width: 120px; " onclick="exportwmd();">
								<i class="icon-hdd"></i>停用
							</button>									
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
		  			<thead  align="center">
			  		<tr class='table_head_line'>
			  			<td><input type="checkbox"  id="checkAll" onclick="inverseCkb('ckb')"/> </td>
			  		</tr>  	
				</thead>
				<tbody  align="center">
			  		<c:forEach items= "${pageView.records }" var= "records" varStatus='status'>
				  		<tr ondblclick='toUpdate("${records.id}")'>
					  		<td><input id="ckb"  name="ckb" type="checkbox" value="${records.id}"></td>
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
