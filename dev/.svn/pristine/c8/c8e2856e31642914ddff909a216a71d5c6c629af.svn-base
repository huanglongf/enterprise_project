/**
 * @param tablename
 * @returns
 */
function tableconfig(tableName) {
	// 表名赋值
	$("#table_name").val(tableName)
	// 初始化配置
	initTableConfig(tableName);
	// 弹窗
	$("#table-config").modal({backdrop: "static", keyboard: false});
	// 隐藏遮罩
	$(".modal-backdrop").hide();
}
/**
 * @param tableName
 * @returns
 */
function initTableConfig(tableName) {
	// 清空字段名称查询条件
	$("#column_name_search").val("");
	// 先刷新配置的字段
	openIdDiv("table_column_config_content","/BT-LMIS/control/templetController/listTableColumnConfig.do?tableName="+tableName);
}

/**
 * @param title
 * @param content
 * @returns
 */
function syntable(title,content){
	title.scrollLeft(content.scrollLeft());
}

/**
 * @param event
 * @returns
 */
function searchColumn(event) {
	if(event == null || (event != null && event.keyCode == 13)) {
		// blur查询或enter查询
		openIdDiv("table_column_content","/BT-LMIS/control/templetController/listTableColumn.do?tableName="+$("#table_name").val()
				+"&columnName="+$("#column_name_search").val());
		
	}
}

function addColumn(current) {
	// 隐藏当前节点
	current.hide();
	// 拼接节点
	$("#table_column_config_content table tbody").append(
		"<tr class='pointer'><td style='display:none;'><input name='table_column_config' value='"
		+ current.children(":first").children(":first").children(":first").val()
		+ "' /></td><td class='text-center table-text col-md' title='"
		+ current.children().eq(1).text()
		+ "' onclick='removeColumn($(this));'>"
		+ current.children().eq(1).text()
		+ "</td><td class='text-center table-text col-md' title='"
		+ ($("#table_column_config_content table tbody").children().length+1)
		+ "'><a class='pointer' title='上移' onclick='moveColumn(\"up\",$(this));'><i class='fa fa-angle-double-up fa-lg' aria-hidden='true'></i></a>"
		+ ($("#table_column_config_content table tbody").children().length+1)
		+ "<a class='pointer' title='下移' onclick='moveColumn(\"down\",$(this));'><i class='fa fa-angle-double-down fa-lg' aria-hidden='true'></i></a></td>"
	)
	//
	countTableColumn();
}

function removeColumn(current) {
	// 显示对应id的字段
	$("#"+current.prev().children(":first").val()).show();
	// 移除当前节点
	current.parent().remove();
	//
	countTableColumn();
}

function addAllColumn() {
	$("#table_column_content table tbody tr").each(function() {
		if($(this).css("display") != "none") {
			addColumn($(this));
		}
	})
	
}

function removeAllColumn() {
	$("#table_column_config_content table tbody tr").each(function() {
		removeColumn($(this).children().eq(1));
	})
}

function countTableColumn() {
	var choosen=0;
	$("#table_column_content table tbody tr").each(function(){
		if($(this).css("display") == "none") {
			choosen++;
		}
	})
	$("#table_column_content").parent().next().text("字段总量"+$("#table_column_content table tbody").children().length+"条，已选"+choosen+"条");
}

function moveColumn(move,current) {
	var currentSort=Number(current.parent().text());
	if(move=="up") {
		if(currentSort == 1) {
			return;
		}
		// 注意取节点的先后顺序
		current.parent().parent().prev().children().eq(2).html(
				"<a class='pointer' title='上移' onclick='moveColumn(\"up\",$(this));'><i class='fa fa-angle-double-up fa-lg' aria-hidden='true'></i></a>" +
				+currentSort
				+"<a class='pointer' title='下移' onclick='moveColumn(\"down\",$(this));'><i class='fa fa-angle-double-down fa-lg' aria-hidden='true'></i></a>"
		
		);
		current.parent().parent().after(current.parent().parent().prev().prop("outerHTML"));
		current.parent().parent().prev().remove();
		current.parent().html(
				"<a class='pointer' title='上移' onclick='moveColumn(\"up\",$(this));'><i class='fa fa-angle-double-up fa-lg' aria-hidden='true'></i></a>" +
				+(currentSort-1)
				+"<a class='pointer' title='下移' onclick='moveColumn(\"down\",$(this));'><i class='fa fa-angle-double-down fa-lg' aria-hidden='true'></i></a>"
		);
	}
	if(move=="down") {
		if(currentSort == $("#table_column_config_content table tbody").children().length) {
			return;
		}
		current.parent().parent().next().children().eq(2).html(
				"<a class='pointer' title='上移' onclick='moveColumn(\"up\",$(this));'><i class='fa fa-angle-double-up fa-lg' aria-hidden='true'></i></a>" +
				+currentSort
				+"<a class='pointer' title='下移' onclick='moveColumn(\"down\",$(this));'><i class='fa fa-angle-double-down fa-lg' aria-hidden='true'></i></a>"
		);
		current.parent().parent().before(current.parent().parent().next().prop("outerHTML"));
		current.parent().parent().next().remove();
		current.parent().html(
				"<a class='pointer' title='上移' onclick='moveColumn(\"up\",$(this));'><i class='fa fa-angle-double-up fa-lg' aria-hidden='true'></i></a>" +
				+(currentSort+1)
				+"<a class='pointer' title='下移' onclick='moveColumn(\"down\",$(this));'><i class='fa fa-angle-double-down fa-lg' aria-hidden='true'></i></a>"
		);
	}
}

function saveTableColumnConfig() {
	if(!confirm("确认提交？")) {
		return;
	}
	loadingStyle();
	//
	var tableColumnConfig=[];
	$("input[name='table_column_config']").each(function() {
		tableColumnConfig.push($.trim($(this).val()));
	})
	$.ajax({
		type:"POST",
        url:"/BT-LMIS/control/templetController/saveTableColumnConfig.do",
        dataType:"json",
        data: {
        	"tableName": $("#table_name").val(),
        	"tableColumnConfig": tableColumnConfig
        },
        success: function(result) {
        	alert(result.msg);
        	// 解除旋转
        	cancelLoadingStyle();
        	if(result.flag) {
        		// 隐藏弹窗
        		$("#table-config").modal("hide");
        		// 刷新界面
        		searchTempletTest();
        		
        	}
        	
        },
        error: function(result) {
        	// 解除旋转
        	cancelLoadingStyle();
        }
	})
}

function searchTempletTest() {
	$(".search-table").load("/BT-LMIS/control/templetController/search.do?pageName=table&tableName=templet_test&startRow="
		+ $("#startRow").val()
		+ "&endRow="
		+ $("#startRow").val()
		+ "&page="
		+ $("#pageIndex").val()
		+ "&pageSize="
		+ $("#pageSize").val());
	
}