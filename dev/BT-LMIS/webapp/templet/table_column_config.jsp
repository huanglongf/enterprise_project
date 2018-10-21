<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fns" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
	$(function(){
		//
		var temp = $("#table_column_config_content")[0].offsetWidth - $("#table_column_config_content")[0].clientWidth;
		if(temp != 0) {
			$("#table_column_config_content").width($("#table_column_config_content_parent").width() + temp);
		}
		// 后刷新所有的字段
		if($("#table_name").val() != "") {
			openIdDiv("table_column_content","/BT-LMIS/control/templetController/listTableColumn.do?tableName="+$("#table_name").val());
		}
	})
</script>

<table class="table table-striped table-bordered table-hover table-fixed no-margin">
	<tbody>
		<c:forEach items="${tableColumnConfig }" var="tableColumnConfig">
			<tr class="pointer">
				<td style="display:none;"><input name="table_column_config" value="${tableColumnConfig.table_column_id}" /></td>
				<td class="text-center table-text col-md" title="${tableColumnConfig.column_name }" onclick="removeColumn($(this));">
					${tableColumnConfig.column_name  }
				</td>
				<td class="text-center table-text col-md" title="${tableColumnConfig.sort }">
					<a class="pointer" title="上移" onclick="moveColumn('up',$(this));"><i class="fa fa-angle-double-up fa-lg" aria-hidden="true"></i></a>${tableColumnConfig.sort  }<a class="pointer" title="下移" onclick="moveColumn('down',$(this));"><i class="fa fa-angle-double-down fa-lg" aria-hidden="true"></i></a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>