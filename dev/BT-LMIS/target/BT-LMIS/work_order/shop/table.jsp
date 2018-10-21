<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/templet/common.jsp"%>
<script type="text/javascript">
	$(function(){
		// 修改主表内容高度
		$(".table-content-free").height($(window).height() - 420);
		// 计算总高
		totalHeight = $(".search-toolbar").height() + $(".table-main").height();
		//
		$("#templet_test_content").width(mainTableWidth + ($("#templet_test_content")[0].offsetWidth - $("#templet_test_content")[0].clientWidth));
	})
</script>

<div class="table-main">
	<div class="table-border">
		<div class="table-title" id="templet_test_title">	
			<table class="table table-bordered no-margin table-fixed">
		   		<thead>
			  		<tr>
			  			<c:if test="${not empty tableColumnConfig }">
			  				<th class="text-center table-text col-sm">
								<label class="pos-rel">
									<input class="ace" type="checkbox" id="checkAll_templetTest" onclick="inverseCkb('ckb')" />
									<span class="lbl"></span>
								</label>
							</th>
			  			</c:if>
			  			<c:if test="${empty tableColumnConfig }">
			  				<th class="table-title-empty"/>
			  			</c:if>
			  			<c:forEach items="${tableColumnConfig }" var="tableColumnConfig">
			  				<th class="text-center table-text col-lg" title="${tableColumnConfig.column_name }">
		  						<i class="fa fa-sort-up fa-lg pointer" title="升序" onclick="tablesearch('${tableColumnConfig.column_code }', 'ASC')"></i>
			  					${tableColumnConfig.column_name }
		  						<i class="fa fa-sort-down fa-lg pointer" title="降序" onclick="tablesearch('${tableColumnConfig.column_code }', 'DESC')"></i>
				  			</th>
			  			</c:forEach>
			  		</tr>
				</thead>
			</table>
		</div>
		<div class="table-content-parent">
			<div class="table-content table-content-free" id="templet_test_content" onscroll="syntable($('#templet_test_title'),$('#templet_test_content'))">
				<table class="table table-striped table-bordered table-hover table-fixed no-margin">
					<tbody>
						<c:forEach items="${pageView.records }" var="res">
							<tr class="pointer">
								<c:if test="${not empty tableColumnConfig }">
					  				<td class="text-center table-text col-sm">
										<label class="pos-rel">
											<input class="ace" type="checkbox" id="ckb" name="ckb" value="${res.id} }" />
											<span class="lbl"></span>
										</label>
									</td>
					  			</c:if>
					  			<c:forEach items="${tableColumnConfig }" var="tableColumnConfig">
					  				<td class="text-center table-text col-lg" title="${res[tableColumnConfig.column_code] }"  ondblclick="updateSgroup('${res.id }','${res.group_code}','${res.group_name}','${res.superior}','${res.if_Share}','${res.instruction}','${res.status}')" >
						  				${res[tableColumnConfig.column_code] }
						  			</td>
					  			</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<div class="dataTables_wrapper form-inline">
	<div class="row">
		<div class="col-md-12">
			<a class="pointer" title="列表配置" onclick='tableconfig(${tableFunctionConfig });'><i class="fa fa-cog fa-2x"></i></a>
			${pageView.pageView }
		</div>
	</div>
</div>


<script>
//修改主表信息
function updateSgroup(id,group_code,group_name,superior,if_allot,instruction,status){
	var data = "id="+id+"&group_code="+group_code+"&group_name="+group_name+"&superior="+superior+"&if_allot="+if_allot+"&instruction="+instruction+"&status="+status;
	openDivs('${root}control/shopGroupController/updateSgroup.do',data);
}
</script>    