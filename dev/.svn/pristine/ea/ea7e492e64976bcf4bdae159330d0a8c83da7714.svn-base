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
			<table>
		  		<tr>
		  			<td width="15%" align="center">
						<button class="btn btn-xs btn-primary" onclick="$('input[id=file1]').click();">
							<i class="icon-edit"></i>选择文件
						</button>
						<input id="projectCategorySizeId" type="hidden" name="id" value=""> 
					</td>
		  			<td width="30%" align="center" >
		  				<form action='' enctype="multipart/form-data" method="post" id="fileForm">
							<input id="file1" type="file" onchange="fileChange()" name='file' style="display:none;">  
							<input id="photoCover" class="input-large" type="text" style="height:30px;width: 100%;" readonly="readonly">  
						</form>
	  				</td>
	  				<td align="center" width="10%">
						<button id="imps" name="imps" class="btn btn-sm btn-white btn-warn btn-bold btn-round btn-width" 
							onclick="importProduct();">
							<i class="ace-icon fa upload blue bigger-120"></i>导入
						</button>
					</td>
					<td align="center" width="10%">
						<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
							onclick="downloadTemplete()">
							<i class="ace-icon fa download blue bigger-120"></i>下载模版
						</button>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div style="margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">
		<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width"
			onclick="del();">
			<i class="ace-icon fa fa-trash-o red bigger-120"> 删除 </i>
		</button>
		<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"
			onclick="back();">
			<i class="ace-icon fa fa-angle-double-left blue bigger-120"> 返回 </i>
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
				  				<th class="text-center table-text col-lg" title="创建时间">
				  					创建时间
					  			</th>
				  				<th class="text-center table-text col-lg" title="文件名">
				  					文件名
					  			</th>
				  				<th class="text-center table-text col-lg" title="总行数">
				  					总行数
					  			</th>
				  				<th class="text-center table-text col-lg" title="状态">
				  					状态
					  			</th>
				  				<th class="text-center table-text col-lg" title="操作">
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
					  				<td class="text-center table-text col-lg" title="${res.createTime }">
						  				<fmt:formatDate value="${res.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.fileName }">
						  				${res.fileName }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.totalCount }">
						  				${res.totalCount }
						  			</td>
					  				<td class="text-center table-text col-lg" title="${res.status }">
						  				<c:if test="${res.status == '-1' }">任务进行中</c:if>
						  				<c:if test="${res.status == '0' }">部分成功</c:if>
						  				<c:if test="${res.status == '1' }">全部成功</c:if>
						  			</td>
					  				<td class="text-center table-text col-lg" title="操作">
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
		openDivs(root+"/control/wareParkController/toShowImportTaskDetail.do?twitId=" + id);
	}

	function back(){
		openDivs(root+"/control/wareParkController/list.do");
	}

	//跳转
	function pageJump() {
		openDivs(root+"/control/wareParkController/toImportTaskList.do?startRow="+$("#startRow").val()+"&endRow="+$("#startRow").val()+"&page="+$("#pageIndex").val()+"&pageSize="+$("#pageSize").val() + "&" + $("#searchForm").serialize());
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
				url:root+"control/wareParkController/delImportTaskByIds.do",
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
	

	function importProduct(){
		$("#error_mesg").text("");
		var order_number_file=document.getElementById("file1");
		if(order_number_file.length==0){
			$("#error_mesg").text("请上传文件");
			return;
		}
		if(confirm("确定要上传吗？")){
			loadingStyle();
		    var formData = new FormData(document.getElementById("fileForm"));
		    $.ajax({    
		        contentType:"multipart/form-data",  
		        url:root+"/control/wareParkController/importExcel.do",    
		        type:"post",  
		        data:formData,  
		        dataType:"json",  
		        processData: false,  // 告诉jQuery不要去处理发送的数据  
		        contentType: false,   // 告诉jQuery不要去设置Content-Type请求头  
		        success: function(jsonStr){  
					alert(jsonStr.out_result_reason);
					cancelLoadingStyle();
					if(jsonStr.out_result==1){
						$('#file1').val("");
						$('#photoCover').val("");
						pageJump();
					}else{
						$("#error_mesg").text(jsonStr.out_result_reason);
					}
		        }  
		    });  
		}
	}

	//下载模板
	function downloadTemplete(){
		window.location.href = root+"control/wareParkController/downloadTemplete.do?downloadTemplete=wareParkRelation";
	}

	function fileChange(){
		$('#photoCover').val($('#file1').val());
	};

</script>
</html>
