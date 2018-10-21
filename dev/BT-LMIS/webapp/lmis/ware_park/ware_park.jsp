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
	<div class="page-header">
		<div style="margin-left: 20px;margin-bottom: 20px;">
			<form id="searchForm" action="#">
				<table>
			  		<tr>
			  			<td width="10%">园区名称:</td>
			  			<td width="20%"><input id="parkName" name="parkName" type="text" value="${queryParam.parkName}" /></td>
			  			<td width="10%">园区代码:</td>
			  			<td width="20%"><input id="parkCode" name="parkCode" type="text" value="${queryParam.parkCode}" /></td>
			  		</tr>
				</table>
			</form>
		</div>
	</div>
	<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
		<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"
			onclick="search();">
			<i class="ace-icon fa fa-search grey bigger-120"> 查询 </i>
		</button>
		<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width"
			onclick="toSaveForm('');">
			<i class="ace-icon fa fa-plus-circle red bigger-120"> 新增 </i>
		</button>
		<!-- <button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width"
			onclick="toSaveForm();">
			<i class="ace-icon fa icon-edit red bigger-120"> 修改 </i>
		</button> -->
		<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width"
			onclick="del();">
			<i class="ace-icon fa fa-trash-o red bigger-120"> 删除 </i>
		</button>
		<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
			onclick="exportExcel();">
			<i class="ace-icon fa icon-share blue bigger-120"> 导出 </i>
		</button>
		<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
			onclick="toImportTaskList();">
			<i class="ace-icon fa icon-upload-alt blue bigger-120"> 导入 </i>
		</button>
	</div>
	<div class="table-main">
		<div class="table-border" style="border:0px;">
			<div class="table-content-parent" style="overflow: auto;border:0px;border-left:1px solid #DDD;border-right:1px solid #DDD;min-height:250px;">
				<div class="table-content table-content-free" id="templet_test_content" style="overflow: visible;">
					<table class="table table-striped table-bordered table-hover table-fixed no-margin" style="overflow: visible;">
						<thead class="table-title">
							<tr>
				  				<th class="text-center table-text col-sm">
									<label class="pos-rel">
										<input class="ace" type="checkbox" id="checkAll_templetTest" onclick="inverseCkb('ckb')" />
										<span class="lbl"></span>
									</label>
								</th>
				  				<th class="text-center table-text col-lg" title="园区代码">
				  					园区代码
					  			</th>
				  				<th class="text-center table-text col-lg" title="园区名称">
				  					园区名称
					  			</th>
				  				<th class="text-center table-text col-lg" title="园区成本中心代码">
				  					园区成本中心代码
					  			</th>
				  				<!-- <th class="text-center table-text col-lg" title="仓">
				  					仓
					  			</th>
				  				<th class="text-center table-text col-lg" title="配">
				  					配
					  			</th>
				  				<th class="text-center table-text col-lg" title="是否由物流部出具账单">
				  					是否由物流部出具账单
					  			</th>
				  				<th class="text-center table-text col-lg" title="是否出具店铺收入">
				  					是否出具店铺收入
					  			</th> -->
				  				<th class="text-center table-text col-lg" title="备注">
				  					备注
					  			</th>
				  				<th class="text-center table-text col-lg" title="备注">
				  					操作
					  			</th>
					  		</tr>
						</thead>
						<tbody>
							<c:forEach items="${pageView.records }" var="res">
								<tr class="pointer" ondblclick="toSaveForm('${res.id}');">
					  				<td class="text-center table-text col-sm">
										<label class="pos-rel">
											<input class="ace" type="checkbox" id="ckb${res.id}" name="ckb" value="${res.id}" />
											<span class="lbl"></span>
										</label>
									</td>
					  				<td class="text-center table-text col-lg" title="${res.parkCode }">
						  				${res.parkCode }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.parkName }">
						  				${res.parkName }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.parkCostCenter }">
						  				${res.parkCostCenter }
						  			</td>
					  				<%-- <td class="text-center table-text col-lg" title="${res.wareFlag }">
						  				${res.wareFlag }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.disFlag }">
						  				${res.disFlag }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.showByCpFlag }">
						  				${res.showByCpFlag }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.showStReFlag }">
						  				${res.showStReFlag }
						  			</td> --%>
					  				<td class="text-center table-text col-lg" title="${res.remark }">
						  				${res.remark }
						  			</td>
					  				<td class="text-center table-text col-lg" title="操作">
						  				<a href="javascript:toSaveForm('${res.id}');">修改</a>&nbsp;&nbsp;
						  				<a href="javascript:toShowDetail('${res.id}');">查看详情</a>
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
	function inverseCkb(items){
		$('[name='+items+']:checkbox').each(function(){
			this.checked=!this.checked;
		});
	}

	function toShowDetail(id) {
		openDivs(root+"control/wareParkController/toShowDetail.do?id=" + id);
	}

	function toSaveForm(id) {
		openDivs(root+"control/wareParkController/toSaveForm.do?id=" + id);
	}
	
	//跳转
	function pageJump() {
		openDivs(root+"control/wareParkController/list.do?startRow="+$("#startRow").val()+"&endRow="+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val() + "&" + $("#searchForm").serialize());
	}

	function search(){
		openDivs(root+"control/wareParkController/list.do?" + $("#searchForm").serialize());
	}
	
	function del() {
		
		var checked = $("input[name='ckb']:checked");
		
		if(checked.length < 1){
			alert("请选择至少一行数据");
			return;
		}
		var ids = [];
		checked.each(function(){
			ids.push($(this).val());
		});
		if(confirm("你所勾选的数据会连带明细数据一同删除,请谨慎使用,确定要删除所选数据吗？")){
			loadingStyle();
			$.ajax({
				url:root+"control/wareParkController/delByIds.do",
				type:"post",
				data:{
					ids : ids.join(",")
				},
				dataType:"json",
				success:function(data){
					if(data.out_result == 1){
						alert("删除成功");
					} else {
						alert(data.out_result_reason);
					}
					pageJump();
					cancelLoadingStyle();
				}
			});
		}
	}

	function exportExcel() {
		window.location.href = root+'control/wareParkController/exportExcel.do?' + $("#searchForm").serialize();
	}

	function toImportTaskList() {
		openDiv(root+'control/wareParkController/toImportTaskList.do');
	}
</script>
</html>
