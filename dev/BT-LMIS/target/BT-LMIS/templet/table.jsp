<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	$(function() {
		if(totalHeight == 0) {
			// 查询操作工具栏高度 + 表头高度（40）+ 表内容高度（窗口高度-420） + 表格边框（2）+ 分页/配置栏（58）
			totalHeight = $(".search-toolbar").height() + $(window).height() - 320;
		}
		//
		$(".table-content-free").height(totalHeight - $(".search-toolbar").height() - 100);
		//
		hideYScroll("#templet_test_content", $("#templet_test_title").width());
		//
		$(window).resize(function(){
			hideYScroll("#templet_test_content", $("#templet_test_title").width());
	    });
		// 内容宽度，隐藏scroll
	    $("#templet_test_title").resize(function(){
	    	hideYScroll("#templet_test_content", $("#templet_test_title").width());
	    })
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
								<input type="checkbox" id="checkAllTable" onclick="inverseCkb('ckb')" />
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
							<tr class="pointer" ondblclick='tableAction($(this), ${tableFunctionConfig });'>
								<c:if test="${not empty tableColumnConfig }">
					  				<td class="text-center table-text col-sm">
										<input type="checkbox" id="ckb" name="ckb" value="${res.id }" data-version="${res.version }" />
									</td>
					  			</c:if>
					  			<c:forEach items="${tableColumnConfig }" var="tableColumnConfig">
					  				<td class="text-center table-text col-lg" title="${res[tableColumnConfig.column_code] }">
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
			<a class="pointer" title="列表配置" onclick='tableconfig(${tableFunctionConfig });'><i class="fa fa-cog fa-2x "></i></a>
			
			${pageView.pageView }
		</div>
	</div>
</div>