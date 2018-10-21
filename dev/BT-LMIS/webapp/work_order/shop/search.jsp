<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type= "text/javascript" src= "shop.js"></script>
<jsp:include page="/templet/common.jsp">
	<jsp:param name="title" value="查询界面"/>
</jsp:include>

<div class="row">
	<div class="col-xs-12">
		<div class="row">
			<div class="col-xs-12">
				<div class="search-toolbar">
					<div class="widget-box">
						<div class="widget-header widget-header-small">
							<h5 class="widget-title lighter">查询栏</h5>
							<a class="pointer" title="初始化" onclick="refresh();"><i class="fa fa-refresh"></i></a>
						</div>
						<div class="widget-body">
							<div class="widget-main">
								<form class="container search_form"  id="form">
									<div class="row form-group">
										<div class="col-md-1 no-padding text-center search-title">
											<label class="blue" for="form-field-1">组别编码:</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-md-11 no-padding">
											
											<input class="form-data search-data form-control"
												id="group_code" name="group_code"
												style="width: 100%" type="text"
												 />
												<%-- <select id="group_code" name="group_code" data-edit-select="1">
								  				<option value= "">---请选择---</option>
								  				 <c:forEach items= "${seniorQuery}" var= "street" >
								  					    <option value="${street.group_code}" <c:if test="${groupPar.group_code==street.group_code}">selected</c:if> >${street.group_code}</option>
												 </c:forEach>
											    </select> --%>
											</div>
										</div>
										<div class="col-md-1 no-padding text-center search-title">
											<label class="blue" for="form-field-2">组别名称:</label>
										</div>
										<div class="col-md-3 no-padding">
											<div class="col-md-11 no-padding">
											
												<input class="form-data search-data form-control"
												id="group_name" name="group_name"
												style="width: 100%" type="text"
												 />
											
									  			   <%--  <select id="group_name" name="group_name" style="width: 50%;" data-edit-select="1">
								  					<option value= "">---请选择---</option>
								  					<c:forEach items= "${seniorQuery}" var= "street" >
								  					    <option value="${street.group_name}" <c:if test="${groupPar.group_name==street.group_name}">selected</c:if> >${street.group_name}</option>
													</c:forEach>
											        </select> --%>
											</div>
										</div>
									</div>
<!-- 									<div class="row text-center"> -->
<!-- 										<a class="senior-search-shift pointer" title="高级查询"><i class="fa fa-angle-double-down fa-2x" aria-hidden="true"></i></a> -->
<!-- 									</div> -->
								</form>
							</div>
						</div>
					</div>
					<div class="dataTables_wrapper form-inline">
						<div class="row">
							<div class="col-md-12">
								<div class="dataTables_length">
									<button class="btn btn-sm btn-white btn-default btn-bold btn-round btn-width"  onclick="tablesearch('', '');">
										<i class="ace-icon fa fa-search grey bigger-120">
											查询
										</i>
									</button>
									<c:if test="${menu_role.menu_1002!=null && menu_role.menu_1002=='1002'}">
									<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"  onclick= "$('#div_view').load('/BT-LMIS/control/shopGroupController/newlyGroupWord.do')" >
										<i class="ace-icon fa fa-plus-circle blue bigger-120">
											新增
										</i>
									</button>
									</c:if>
									<c:if test="${menu_role.menu_1006!=null && menu_role.menu_1006=='1006'}">
									<button class="btn btn-sm btn-white btn-info btn-bold btn-round btn-width"  onclick= "del_group();" >
										<i class="ace-icon fa fa-plus-circle blue bigger-120">
											删除
										</i>
									</button>
									</c:if>									
<!-- 									<button class="btn btn-sm btn-white btn-warning btn-bold btn-round btn-width"> -->
<!-- 										<i class="ace-icon fa fa-pencil-square-o yellow bigger-120"> -->
<!-- 											编辑 -->
<!-- 										</i> -->
<!-- 									</button> -->
<!-- 									<button class="btn btn-sm btn-white btn-danger btn-bold btn-round btn-width"> -->
<!-- 										<i class="ace-icon fa fa-trash-o red bigger-120"> -->
<!-- 											删除 -->
<!-- 										</i> -->
<!-- 									</button> -->
								<button class="btn btn-sm btn-white btn-success btn-bold btn-round btn-width" onclick="isDisable(1)">
									<i class="ace-icon fa fa-unlock green bigger-120">
											启用
										</i>
 									</button>
								<button class="btn btn-sm btn-white btn-warning btn-bold btn-round btn-width" onclick="isDisable(0)">
								<i class="ace-icon fa fa-lock orange bigger-120">
										禁用
										</i>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="search-table">
					<jsp:include page="/templet/table.jsp" flush= "true" />
				</div>
			</div>
		</div>
	</div>
</div>

<script>

function pageJump() {
	tablesearch('', '');
}

function tablesearch(column, sort) {
	$(".search-table").load("/BT-LMIS/control/shopGroupController/search.do?pageName=table&tableName=tb_shop_group&startRow="
		+ $("#startRow").val()
		+ "&endRow="
		+ $("#startRow").val()
		+ "&page="
		+ $("#pageIndex").val()
		+ "&pageSize="
		+ $("#pageSize").val()
		+ "&sortColumn="
		+ column
		+ "&sort="
		+ sort
		+ "&"
		+ $(".search_form").serialize()
	);
}

function tableAction(currentRow, tableFunctionConfig) {
	if(tableFunctionConfig.dbclickTr == true) {
		var id=currentRow.children(":first").children(":first").val();
		$("#div_view").load('/BT-LMIS/control/shopGroupController/updateSgroup.do?id='+id);
	}
}

function refresh() {
	initializeSelect('group_code');
	initializeSelect('group_name');
}
function isDisable(flag) {
    if ($("input[name='ckb']:checked").length != 1) {
        alert("请选择一条数据!");
        return;
    }
    var id=$("input[name='ckb']:checked").val();

	$.ajax({
		type:"post",
		url: "/BT-LMIS/control/shopGroupController/switchShopGroup.do",
        dataType:"json",
		data:{"status":flag,"id":id},
		success:function (result) {
		    if(result==1&&flag==1){
		        alert("启用成功")
			}
            else if(result==1&&flag==0){
                alert("禁用成功")
            }else{
                alert("操作失败")
			}
            tablesearch('','');
        }
	});
}

</script>