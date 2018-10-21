function tablesearch(column, sort) {
	$(".search-table").load("/BT-LMIS/control/templetController/search.do?pageName=table&tableName=templet_test&startRow="
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
		alert(tableFunctionConfig.config.param1);
	}
}