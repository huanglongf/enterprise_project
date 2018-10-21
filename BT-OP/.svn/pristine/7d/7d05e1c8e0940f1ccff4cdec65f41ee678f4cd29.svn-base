<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	$(function(){
		//
		var temp = $("#table_column_content")[0].offsetWidth - $("#table_column_content")[0].clientWidth;
		if(temp != 0) {
			$("#table_column_content").width($("#table_column_content_parent").width() + temp);
		}
		//
		if($("input[name='table_column_config']").length != 0) {
			$("input[name='table_column_config']").each(function() {
				$("#"+$(this).val()).hide();
			})
		}
		// 计算字段量
		countTableColumn();
	})
</script>
	
<table class="table table-striped table-bordered table-hover no-margin">
	<tbody>
		<c:forEach items="${tableColumn }" var="tableColumn">
			<tr class="pointer" id="${tableColumn.id }" onclick="addColumn($(this));">
				<td class="text-center table-text col-sm" style="display:none;">
					<label class="pos-rel">
						<input class="ace" type="checkbox" id="ckb" name="ckb" value="${tableColumn.id }" />
						<span class="lbl"></span>
					</label>
				</td>
				<td class="text-center table-text col-lg" title="${tableColumn.column_name }">${tableColumn.column_name  }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>