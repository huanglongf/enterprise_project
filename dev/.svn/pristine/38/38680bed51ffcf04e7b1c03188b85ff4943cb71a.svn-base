<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<%@ include file="/templet/common.jsp"%>
<title>LMIS</title>
<meta name="description" content="overview &amp; stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link type="text/css" href="<%=basePath%>css/table.css"
	rel="stylesheet" />
</head>
<body>
	<form id="searchForm" action="#">
		<input type="hidden" name="twitId" value="${model.id}" id="twitId" />
	</form>
	<div class= "page-header">
		<table>
			<tr>
	  			<td align= "left" width= "10%">正确<span class= "blue">${model.totalCount - pageView.totalrecord}</span>行,错误<span class= "red">${pageView.totalrecord}</span>行</td>
	  		</tr>
		</table>
	</div>
	<div style="margin-left: 20px;margin-top:10px;">
		<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"  onclick= "back();" >
			<i class="ace-icon fa fa-angle-double-left blue bigger-120">
				返回
			</i>
		</button>	
	</div>
	<div class="table-main" style="margin-top:10px;">
		<div class="table-border" style="border:0px;">
			<div class="table-content-parent" style="overflow: auto;border:0px;border-left:1px solid #DDD;border-right:1px solid #DDD;min-height:250px;">
				<div class="table-content table-content-free" id="templet_test_content" style="overflow: visible;">
					<table class="table table-striped table-bordered table-hover table-fixed no-margin" style="overflow: visible;">
						<thead class="table-title">
							<tr>
				  				<th class="text-center table-text col-lg" title="序号">
				  					序号
					  			</th>
				  				<th class="text-center table-text col-lg" title="仓库编码">
				  					仓库编码
					  			</th>
				  				<th class="text-center table-text col-lg" title="店铺编码">
				  					店铺编码
					  			</th>
				  				<th class="text-center table-text col-lg" title="园区编码">
				  					园区编码
					  			</th>
				  				<th class="text-center table-text col-lg" title="错误信息">
				  					错误信息
					  			</th>
					  		</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.records }" var="res">
								<tr class="pointer">
					  				<td class="text-center table-text col-lg" title="${res.sort }">
						  				${res.sort }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.warehouseCode }">
						  				${res.warehouseCode }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.storeCode }">
						  				${res.storeCode }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.parkCode }">
						  				${res.parkCode }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.errorMessage }">
						  				${res.errorMessage }
						  			</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="dataTables_wrapper form-inline">
			<div class="row">
				<div class="col-md-12">
					${pageView.pageView }
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="<%=basePath%>js/common_view.js"></script>
<script type="text/javascript">
	function back(){
		openDivs(root+"/control/wareParkController/toImportTaskList.do");
	}
	//跳转
	function pageJump() {
		openDivs(root+"/control/wareParkController/toShowImportTaskDetail.do?startRow="+$("#startRow").val()+"&endRow="+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val() + "&" + $("#searchForm").serialize());
	}

</script>
</html>
